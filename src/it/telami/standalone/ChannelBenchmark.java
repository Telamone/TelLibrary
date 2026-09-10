package it.telami.standalone;

import it.telami.commons.concurrency.thread.ContentionHandler;
import it.telami.commons.network.channel.ChannelNetworkIdentifier;
import it.telami.commons.network.channel.CrossJVMChannel;
import it.telami.commons.network.channel.LocalNetworkIdentifier;

import java.nio.ByteBuffer;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.LockSupport;
import java.util.stream.IntStream;

final class ChannelBenchmark implements Benchmark {
    ChannelBenchmark () {
        Runtime.getRuntime().addShutdownHook(new Thread(retransmissionHandlers::shutdown));
    }

    public String toString () {
        return "CrossJVMChannel Server/Client §6(must start on another local TelLib instance too!)";
    }

    private static final int BANDWIDTH = 1500 + 4 /* alignment */;
    private static final String[] points = new String[]{"   ", ".  ", ".. ", "..."};

    /* Used when one or multiple servers/clients enter the channel:
     * Server  ->  'scs'  ->  Client
     * Server  <-  'scs'  <-  Client
     *
     * If the server's 'scs' is received from an already started client,
     * then it's re-sent from the same client to itself expecting another
     * free client to respond!
     * Same for the server!
     */
    private static final byte[] serverClientStart = new byte[0];
    private static final byte[] clientResponse = new byte[]{(byte) 1};

    private final ExecutorService retransmissionHandlers = Executors.newVirtualThreadPerTaskExecutor();

    @Override
    public StateHolder run (final int wc, final int mc) {
        final int warmupCycles = (wc - (wc & 1)) + 2;
        final int measurementCycles = (mc - (mc & 1)) + 2;
        final StateHolder state = new StateHolder();
        final CountDownLatch cdl = new CountDownLatch(1);
        new Thread(() -> {
            final StringBuilder bob = new StringBuilder();
            try {
                final AtomicBoolean sent = new AtomicBoolean();
                final byte[][] cases;
                if ((cases = trySmartServerAndReturnCases(
                        warmupCycles,
                        measurementCycles,
                        state,
                        cdl,
                        bob,
                        sent))
                        != null) {
                    if (cases.length == 0) {
                        state.counter.setOpaque(warmupCycles + measurementCycles);
                        state.result.setOpaque(bob + "Timed-out\n");
                    } else if (!tryLowLatencyServer(
                            warmupCycles,
                            measurementCycles,
                            state,
                            cases,
                            bob)) {
                        state.counter.setOpaque(warmupCycles + measurementCycles);
                        state.result.setOpaque(bob + "Low-Latency benchmark failed\n");
                    }
                } else if (trySmartClientAndReturnCases(
                        warmupCycles,
                        measurementCycles,
                        state,
                        cdl,
                        bob,
                        sent)
                        != null) {
                    if (!tryLowLatencyClient(
                            warmupCycles,
                            measurementCycles,
                            state,
                            bob)) {
                        state.counter.setOpaque(warmupCycles + measurementCycles);
                        state.result.setOpaque(bob + "Low-Latency benchmark failed\n");
                    }
                } else {
                    cdl.countDown();
                    state.counter.setOpaque(warmupCycles + measurementCycles);
                    state.result.setOpaque("Benchmark failed\n");
                }
            } catch (final UnsupportedOperationException | ExceptionInInitializerError | NoClassDefFoundError ex) {
                cdl.countDown();
                state.counter.setOpaque(warmupCycles + measurementCycles);
                bob.append("Cannot benchmark CrossJVMChannel: ")
                        .append(Benchmark.extractMessage(ex))
                        .append('\n');
                state.result.setOpaque(bob.toString());
            }
        }).start();
        do try {
            cdl.await();
        } catch (final InterruptedException _) {
        } while (cdl.getCount() != 0L);
        return state;
    }

    private byte[][] trySmartServerAndReturnCases (final int warmupCycles,
                                                   final int measurementCycles,
                                                   final StateHolder state,
                                                   final CountDownLatch cdl,
                                                   final StringBuilder bob,
                                                   final AtomicBoolean externalSent) {
        final ChannelNetworkIdentifier serverID;
        try (final CrossJVMChannel channel = CrossJVMChannel.registerOrJoin(
                System.getProperty("java.io.tmpdir") + "/channelBenchmark.tel",
                serverID = new LocalNetworkIdentifier("Server"),
                1,
                (1 + 16 /* header */ + 7 /* alignment */)
                        + (BANDWIDTH + 16 /* header */),
                ContentionHandler.SMART)) {
            final ChannelNetworkIdentifier clientID = new LocalNetworkIdentifier("Client");
            Main.logInfo("§3Starting a §65 to 10 seconds§3 client search");
            /* If contains:
             * 0 -> 'scs'
             * 1 -> 'response'
             */
            final ByteBuffer container = ByteBuffer.allocateDirect(1);
            boolean sent = false;
            int i = -1;
            while (++i < 20) {
                Main.logInfo("\r§bSearching for a client" + points[i & 3]);
                if (sent || (sent = channel.send(clientID, serverClientStart, 250, TimeUnit.MILLISECONDS))) {
                    if (channel.receive(container.clear(), 250, TimeUnit.MILLISECONDS) != null) {
                        if (container.get(0) == (byte) 0)
                            break;
                        retransmissionHandlers.execute(() -> channel.send(serverID, clientResponse, 1, TimeUnit.SECONDS));
                        container.put(0, (byte) 0);
                        LockSupport.parkNanos(250_000_000L);
                    }
                } else LockSupport.parkNanos(250_000_000L);
            }
            externalSent.setPlain(sent);
            if (i == 20) {
                Main.logInfo("\r§bSearching for a client: §cFailed\n\r");
                Main.logInfo("§eSwitching to client mode");
                return null;
            }
            //Safe not to add '\r' at the end since the next 'logInfo(...)' starts with '\r' as well!
            Main.logInfo("\r§bSearching for a client: §aDone\n\n");
            cdl.countDown();
            final int totalCycles;
            long sendTime = 0, receiveTime = 0, totalTime = 0;
            final byte[][] cases = generateCases(totalCycles
                    = warmupCycles
                    + measurementCycles);
            final long[] times = new long[3];
            //Cold run
            int c;
            for (c = 0; c < warmupCycles;) {
                serverAction(
                        channel,
                        clientID,
                        serverID,
                        cases[c],
                        container,
                        times);
                if ((++c & 1) != 0)
                    continue;
                state.counter.getAndIncrement();
            }
            while (c < totalCycles) {
                serverAction(
                        channel,
                        clientID,
                        serverID,
                        cases[c],
                        container,
                        times);
                sendTime += times[0];
                receiveTime += times[1];
                totalTime += times[2];
                if ((++c & 1) != 0)
                    continue;
                state.counter.getAndIncrement();
            }
            bob.append("[CrossJVMChannel]{SMART} Time per 'send(...)' op:              ")
                    .append(sendTime / (float) measurementCycles)
                    .append(" ns\n")
                    .append("[CrossJVMChannel]{SMART} Time per 'receive(...)' op:           ")
                    .append(receiveTime / (float) measurementCycles)
                    .append(" ns\n")
                    .append("[CrossJVMChannel]{SMART} Time per full request/response:       ")
                    .append(totalTime / (float) measurementCycles)
                    .append(" ns\n");
            return cases;
        } catch (final TimeoutException _) {
            return new byte[0][0];
        }
    }
    private boolean tryLowLatencyServer (final int warmupCycles,
                                         final int measurementCycles,
                                         final StateHolder state,
                                         final byte[][] cases,
                                         final StringBuilder bob) {
        final ChannelNetworkIdentifier serverID;
        try (final CrossJVMChannel channel = CrossJVMChannel.registerOrJoin(
                System.getProperty("java.io.tmpdir") + "/channelBenchmark.tel",
                serverID = new LocalNetworkIdentifier("Server"),
                1,
                (1 + 16 /* header */ + 7 /* alignment */)
                        + (BANDWIDTH + 16 /* header */),
                ContentionHandler.LOW_LATENCY)) {
            final ChannelNetworkIdentifier clientID = new LocalNetworkIdentifier("Client");
            final int totalCycles = warmupCycles + measurementCycles;
            long sendTime = 0, receiveTime = 0, totalTime = 0;
            final ByteBuffer container = ByteBuffer.allocateDirect(1);
            final long[] times = new long[3];
            int c;
            for (c = 0; c < warmupCycles;) {
                serverAction(
                        channel,
                        clientID,
                        serverID,
                        cases[c],
                        container,
                        times);
                if ((++c & 1) != 0)
                    continue;
                state.counter.getAndIncrement();
            }
            while (c < totalCycles) {
                serverAction(
                        channel,
                        clientID,
                        serverID,
                        cases[c],
                        container,
                        times);
                sendTime += times[0];
                receiveTime += times[1];
                totalTime += times[2];
                if ((++c & 1) != 0)
                    continue;
                state.counter.getAndIncrement();
            }
            state.result.setOpaque(bob
                    .append("[CrossJVMChannel]{LOW_LATENCY} Time per 'send(...)' op:        ")
                    .append(sendTime / (float) measurementCycles)
                    .append(" ns\n")
                    .append("[CrossJVMChannel]{LOW_LATENCY} Time per 'receive(...)' op:     ")
                    .append(receiveTime / (float) measurementCycles)
                    .append(" ns\n")
                    .append("[CrossJVMChannel]{LOW_LATENCY} Time per full request/response: ")
                    .append(totalTime / (float) measurementCycles)
                    .append(" ns\n")
                    .toString());
            return true;
        } catch (final TimeoutException _) {
            return false;
        }
    }
    private void serverAction (final CrossJVMChannel channel,
                               final ChannelNetworkIdentifier clientID,
                               final ChannelNetworkIdentifier serverID,
                               final byte[] data,
                               final ByteBuffer container,
                               final long[] timeData) throws TimeoutException {
        final long start = System.nanoTime(), start2;
        if (!channel.send(clientID, data, 1, TimeUnit.SECONDS))
            throw new TimeoutException();
        timeData[0] = System.nanoTime() - start;
        start2 = System.nanoTime();
        for (;;) {
            channel.receive(container.clear(), 1, TimeUnit.SECONDS);
            if ((timeData[1] = System.nanoTime() - start2) >= 1_000_000_000L)
                throw new TimeoutException();
            if (container.get(0) != (byte) 0) {
                container.put(0, (byte) 0);
                break;
            }
            retransmissionHandlers.execute(() -> channel.send(serverID, serverClientStart, 1, TimeUnit.SECONDS));
        }
        //Total time: 'request' + 'response' + 'analysis'
        timeData[2] = System.nanoTime() - start;
    }

    private byte[][] trySmartClientAndReturnCases (final int warmupCycles,
                                                   final int measurementCycles,
                                                   final StateHolder state,
                                                   final CountDownLatch cdl,
                                                   final StringBuilder bob,
                                                   final AtomicBoolean sent) {
        final ChannelNetworkIdentifier clientID;
        try (final CrossJVMChannel channel = CrossJVMChannel.registerOrJoin(
                System.getProperty("java.io.tmpdir") + "/channelBenchmark.tel",
                clientID = new LocalNetworkIdentifier("Client"),
                BANDWIDTH,
                (1 + 16 /* header */ + 7 /* alignment */)
                        + (BANDWIDTH + 16 /* header */),
                ContentionHandler.SMART)) {
            final ChannelNetworkIdentifier serverID = new LocalNetworkIdentifier("Server");
            final ByteBuffer container = ByteBuffer.allocateDirect(BANDWIDTH << 2);
            try {
                Main.logInfo("§3Starting a §65 to 10 seconds§3 server search");
                /* If contains:
                 * 0 -> 'scs'
                 * 1 -> 'response'
                 */
                int i = -1;
                while (++i < 20) {
                    Main.logInfo("\r§bSearching for a server" + points[i & 3]);
                    if (channel.receive(container.clear(), 250, TimeUnit.MILLISECONDS) != null) {
                        if (container.get(0) == (byte) 0) {
                            //If sent, consumes and forget the first 'scs' since it's the one sent before!
                            if (sent.getPlain()) {
                                sent.setPlain(false);
                                LockSupport.parkNanos(250_000_000L);
                                continue;
                            } else {
                                retransmissionHandlers.execute(() -> channel.send(serverID, serverClientStart, 1, TimeUnit.SECONDS));
                                break;
                            }
                        }
                        channel.send(clientID, container.rewind());
                        container.put(0, (byte) 0);
                        LockSupport.parkNanos(250_000_000L);
                    }
                }
                if (i == 20) {
                    Main.logInfo("\r§bSearching for a server: §cFailed\n\n");
                    return null;
                }
                Main.logInfo("\r§bSearching for a server: §aDone\n\n");
            } finally {
                cdl.countDown();
            }
            final byte[][] cases = new byte[0][0];
            final long totalCycles = warmupCycles + measurementCycles;
            long sendTime = 0, receiveTime = 0, totalTime = 0;
            final long[] times = new long[3];
            int c;
            for (c = 0; c < warmupCycles;) {
                clientAction(
                        channel,
                        serverID,
                        clientID,
                        container,
                        times);
                if ((++c & 1) != 0)
                    continue;
                state.counter.getAndIncrement();
            }
            while (c < totalCycles) {
                clientAction(
                        channel,
                        serverID,
                        clientID,
                        container,
                        times);
                sendTime += times[0];
                receiveTime += times[1];
                totalTime += times[2];
                if ((++c & 1) != 0)
                    continue;
                state.counter.getAndIncrement();
            }
            bob.append("[CrossJVMChannel]{SMART} Time per 'send(...)' op:              ")
                    .append(sendTime / (float) measurementCycles)
                    .append(" ns\n")
                    .append("[CrossJVMChannel]{SMART} Time per 'receive(...)' op:           ")
                    .append(receiveTime / (float) measurementCycles)
                    .append(" ns\n")
                    .append("[CrossJVMChannel]{SMART} Time per full request/response:       ")
                    .append(totalTime / (float) measurementCycles)
                    .append(" ns\n");
            return cases;
        } catch (final TimeoutException _) {
            return null;
        }
    }
    private boolean tryLowLatencyClient (final int warmupCycles,
                                         final int measurementCycles,
                                         final StateHolder state,
                                         final StringBuilder bob) {
        final ChannelNetworkIdentifier clientID;
        try (final CrossJVMChannel channel = CrossJVMChannel.registerOrJoin(
                System.getProperty("java.io.tmpdir") + "/channelBenchmark.tel",
                clientID = new LocalNetworkIdentifier("Client"),
                BANDWIDTH,
                (1 + 16 /* header */ + 7 /* alignment */)
                        + (BANDWIDTH + 16 /* header */),
                ContentionHandler.LOW_LATENCY)) {
            final ChannelNetworkIdentifier serverID = new LocalNetworkIdentifier("Server");
            final long totalCycles = warmupCycles + measurementCycles;
            long sendTime = 0, receiveTime = 0, totalTime = 0;
            final ByteBuffer container = ByteBuffer.allocateDirect(BANDWIDTH << 2);
            final long[] times = new long[3];
            int c;
            for (c = 0; c < warmupCycles;) {
                clientAction(
                        channel,
                        serverID,
                        clientID,
                        container,
                        times);
                if ((++c & 1) != 0)
                    continue;
                state.counter.getAndIncrement();
            }
            while (c < totalCycles) {
                clientAction(
                        channel,
                        serverID,
                        clientID,
                        container,
                        times);
                sendTime += times[0];
                receiveTime += times[1];
                totalTime += times[2];
                if ((++c & 1) != 0)
                    continue;
                state.counter.getAndIncrement();
            }
            state.result.setOpaque(bob
                    .append("[CrossJVMChannel]{LOW_LATENCY} Time per 'send(...)' op:        ")
                    .append(sendTime / (float) measurementCycles)
                    .append(" ns\n")
                    .append("[CrossJVMChannel]{LOW_LATENCY} Time per 'receive(...)' op:     ")
                    .append(receiveTime / (float) measurementCycles)
                    .append(" ns\n")
                    .append("[CrossJVMChannel]{LOW_LATENCY} Time per full request/response: ")
                    .append(totalTime / (float) measurementCycles)
                    .append(" ns\n")
                    .toString());
            return true;
        } catch (final TimeoutException _) {
            return false;
        }
    }
    private void clientAction (final CrossJVMChannel channel,
                               final ChannelNetworkIdentifier serverID,
                               final ChannelNetworkIdentifier clientID,
                               final ByteBuffer container,
                               final long[] timeData) throws TimeoutException {
        final long start2 = System.nanoTime(), start;
        for (;;) {
            channel.receive(container.clear(), 1, TimeUnit.SECONDS);
            if ((timeData[1] = System.nanoTime() - start2) >= 1_000_000_000L)
                throw new TimeoutException();
            if (container.get(0) != (byte) 0) {
                container.put(0, (byte) 0);
                break;
            }
            retransmissionHandlers.execute(() -> channel.send(clientID, serverClientStart, 1, TimeUnit.SECONDS));
        }
        start = System.nanoTime();
        if (!channel.send(serverID, clientResponse, 1, TimeUnit.SECONDS))
            throw new TimeoutException();
        timeData[0] = System.nanoTime() - start;
        //Total time: 'request' + 'response' + 'analysis'
        timeData[2] = System.nanoTime() - start2;
    }


    private static byte[][] generateCases (final int totalCycles) {
        return IntStream
                .range(0, totalCycles)
                .mapToObj(_ -> {
                    final int l;
                    final double r;
                    if ((r = ThreadLocalRandom
                            .current()
                            .nextDouble())
                            < 0.75)
                        l = BANDWIDTH;
                    else if (r < 0.95)
                        l = BANDWIDTH << 1;
                    else l = BANDWIDTH << 2;
                    final byte[] data;
                    ThreadLocalRandom
                            .current()
                            .nextBytes(data = new byte[l]);
                    data[0] = (byte) 1;
                    return data;
                })
                .parallel()
                .toArray(byte[][]::new);
    }
}
