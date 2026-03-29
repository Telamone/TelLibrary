package it.telami.standalone;

import com.lmax.disruptor.RingBuffer;
import com.lmax.disruptor.WorkHandler;
import com.lmax.disruptor.YieldingWaitStrategy;
import com.lmax.disruptor.dsl.Disruptor;
import com.lmax.disruptor.dsl.ProducerType;
import it.telami.commons.concurrency.thread.ContentionHandler;
import it.telami.commons.concurrency.thread.ThreadSecondarySeedHandler;
import it.telami.commons.data_structure.buffer.NetworkBuffer;

import java.io.IOException;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.VarHandle;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.spi.AbstractInterruptibleChannel;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadLocalRandom;
import java.util.function.Consumer;
import java.util.stream.Stream;

final class NetworkBufferBenchmark implements Benchmark {
    private static final int DATA_SIZE = 9000;
    private static final int MAX_DATA_AMOUNT = 1024;
    private static final ThreadLocal<ByteBuffer> localBuffer = ThreadLocal.withInitial(() -> ByteBuffer.allocateDirect(DATA_SIZE));

    private static final ByteBuffer endBuffer = ByteBuffer
            .allocateDirect(DATA_SIZE)
            .put(0, (byte) -1);
    private static final InetSocketAddress commonAddress = new InetSocketAddress("127.0.0.1", 25565);

    private static final class Event {
        private final ByteBuffer offHeapBuffer;

        private Event () {
            this.offHeapBuffer = ByteBuffer.allocateDirect(DATA_SIZE);
        }

        private void writeFrom (final ByteBuffer src) {
            offHeapBuffer.clear()
                    .put(src)
                    .flip();
        }

        private void readTo (final ByteBuffer dst) {
            dst.clear().put(offHeapBuffer).flip();
        }
    }
    private static final class DisruptorBuffer implements AutoCloseable {
        private final Disruptor<Event> disruptor;
        private final RingBuffer<Event> ringBuffer;

        private DisruptorBuffer (final Consumer<Event> readHandler) {
            (disruptor = new Disruptor<>(
                    Event::new,
                    MAX_DATA_AMOUNT,
                    Executors.defaultThreadFactory(),
                    ProducerType.MULTI,
                    new YieldingWaitStrategy()))
                    .handleEventsWithWorkerPool(Stream
                            .generate(() -> (WorkHandler<Event>) readHandler::accept)
                            .limit(Runtime.getRuntime().availableProcessors())
                            .toArray(WorkHandler[]::new));
            disruptor.start();
            ringBuffer = disruptor.getRingBuffer();
        }

        private void write (final ByteBuffer src) {
            final long seq = ringBuffer.next();
            try {
                ringBuffer.get(seq).writeFrom(src);
            } finally {
                ringBuffer.publish(seq);
            }
        }

        public void close () {
            disruptor.shutdown();
        }
    }

    @SuppressWarnings("unused")
    private static int writes;
    @SuppressWarnings("unused")
    private static int readAll;
    private static final VarHandle writeVar;
    private static final VarHandle readVar;
    static {
        try {
            final MethodHandles.Lookup lookup = MethodHandles.lookup();
            writeVar = lookup.findStaticVarHandle(
                    NetworkBufferBenchmark.class,
                    "writes",
                    int.class);
            readVar = lookup.findStaticVarHandle(
                    NetworkBufferBenchmark.class,
                    "readAll",
                    int.class);
        } catch (final NoSuchFieldException |
                       IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    NetworkBufferBenchmark () {}

    public String toString () {
        return "NetworkBuffer";
    }

    public StateHolder run (final int wc,
                            final int mc) {
        int parallelism = Runtime.getRuntime().availableProcessors();
        final ByteBuffer[] preComputedData = Stream
                .generate(() -> ByteBuffer.allocateDirect(DATA_SIZE))
                .limit((long) parallelism * MAX_DATA_AMOUNT)
                .peek(buf -> {
                    final byte[] randomData = new byte[DATA_SIZE];
                    ThreadLocalRandom.current().nextBytes(randomData);
                    buf.put(randomData).rewind();
                    buf.put(0, (byte) 0);
                })
                .toArray(ByteBuffer[]::new);
        parallelism *= 4;
        //Divisible both for 'parallelism' and for '4'!
        final int warmupCycles = (wc - (wc % parallelism)) + parallelism;
        final int measurementCycles = (mc - (mc % parallelism)) + parallelism;
        final StateHolder state = new StateHolder();
        new Thread(() -> {
            final StringBuilder bob = new StringBuilder();
            final int realParallelism = Runtime.getRuntime().availableProcessors();
            try (final ExecutorService writersPool = Executors.newWorkStealingPool(realParallelism);
                 final ExecutorService readersPool = Executors.newWorkStealingPool(realParallelism);
                 final AbstractInterruptibleChannel c = new AbstractInterruptibleChannel() {protected void implCloseChannel(){}}) {
                try {
                    benchmarkLMAX(
                            warmupCycles,
                            measurementCycles,
                            preComputedData,
                            writersPool,
                            state,
                            bob);
                } catch (final Throwable t) {
                    if (t.getCause() instanceof UnsupportedOperationException)
                        bob.append("[LMAX Disruptor]         Cannot benchmark: 'Use of unsupported operations'");
                    else bob.append("[LMAX Disruptor]         Cannot benchmark: '")
                            .append(t.getMessage())
                            .append("'");
                    state.counter.getAndAdd((warmupCycles >>> 1) + (measurementCycles >>> 1));
                }
                bob.append('\n');
                benchmarkTelLib(
                        warmupCycles,
                        measurementCycles,
                        preComputedData,
                        writersPool,
                        c,
                        readersPool,
                        realParallelism,
                        state,
                        bob);
                Runtime.getRuntime().gc();
            } catch (final IOException e) {
                bob.append("\nUnexpected error: ").append(e.getMessage());
            }
            state.result.setOpaque(bob
                    .append("\n* No need of any ODP value for this benchmark *")
                    .toString());
        }).start();
        return state;
    }

    private static void benchmarkLMAX (final int warmupCycles,
                                       final int measurementCycles,
                                       final ByteBuffer[] preComputedData,
                                       final ExecutorService writersPool,
                                       final StateHolder state,
                                       final StringBuilder bob) {
        try (final DisruptorBuffer buffer = new DisruptorBuffer(event -> {
            final ByteBuffer buf = localBuffer.get();
            event.readTo(buf.clear());
            process(buf);
        })) {
            long writeTime, readTime;
            for (int i = 0; i < warmupCycles; ++i) {
                buffer.write(preComputedData[i % preComputedData.length].rewind());
                if ((i & 3) == 0)
                    state.counter.getAndIncrement();
            }
            buffer.write(endBuffer.rewind());
            ThreadSecondarySeedHandler.spinUntil(
                    ContentionHandler.SMART,
                    () -> (int) readVar.getAcquire() != 1);
            writeTime = readTime = System.nanoTime();
            for (int i = 0; i < measurementCycles; ++i) {
                buffer.write(preComputedData[i % preComputedData.length].rewind());
                if ((i & 3) == 0)
                    state.counter.getAndIncrement();
            }
            writeTime = System.nanoTime() - writeTime;
            buffer.write(endBuffer.rewind());
            ThreadSecondarySeedHandler.spinUntil(
                    ContentionHandler.SMART,
                    () -> (int) readVar.getAcquire() != 2);
            readTime = System.nanoTime() - readTime;
            final String threads = String.valueOf(Runtime.getRuntime().availableProcessors());
            bob.append("[LMAX Disruptor]        ")
                    .append(" Time per write (1-thread):   ")
                    .repeat(' ', threads.length())
                    .append(writeTime / (float) measurementCycles)
                    .append(" ns\n")
                    .append("[LMAX Disruptor]        ")
                    .append(" Time per read:               ")
                    .repeat(' ', threads.length())
                    .append(readTime / (float) measurementCycles)
                    .append(" ns\n");
            for (int i = 0; i < warmupCycles; ++i) {
                final int j = i;
                writersPool.execute(() -> {
                    final ByteBuffer buf = preComputedData[j % preComputedData.length];
                    //noinspection SynchronizationOnLocalVariableOrMethodParameter
                    synchronized (buf) {
                        buffer.write(buf.rewind());
                    }
                    writeVar.getAndAddRelease(1);
                });
                if ((i & 3) == 0)
                    state.counter.getAndIncrement();
            }
            ThreadSecondarySeedHandler.spinUntil(
                    ContentionHandler.LOW_LATENCY,
                    () -> (int) writeVar.getAcquire() != warmupCycles);
            buffer.write(endBuffer.rewind());
            writeVar.setOpaque(0);
            ThreadSecondarySeedHandler.spinUntil(
                    ContentionHandler.SMART,
                    () -> (int) readVar.getAcquire() != 3);
            writeTime = readTime = System.nanoTime();
            for (int i = 0; i < measurementCycles; ++i) {
                final int j = i;
                writersPool.execute(() -> {
                    final ByteBuffer buf = preComputedData[j % preComputedData.length];
                    //noinspection SynchronizationOnLocalVariableOrMethodParameter
                    synchronized (buf) {
                        buffer.write(buf.rewind());
                    }
                    writeVar.getAndAddRelease(1);
                });
                if ((i & 3) == 0)
                    state.counter.getAndIncrement();
            }
            ThreadSecondarySeedHandler.spinUntil(
                    ContentionHandler.LOW_LATENCY,
                    () -> (int) writeVar.getAcquire() != measurementCycles);
            writeTime = System.nanoTime() - writeTime;
            buffer.write(endBuffer.rewind());
            writeVar.setOpaque(0);
            ThreadSecondarySeedHandler.spinUntil(
                    ContentionHandler.SMART,
                    () -> (int) readVar.getAcquire() != 4);
            readTime = System.nanoTime() - readTime;
            readVar.setOpaque(0);
            VarHandle.fullFence();
            bob.append("[LMAX Disruptor]        ")
                    .append(" Time per write (")
                    .append(threads)
                    .append("-threads):   ")
                    .append(writeTime / (float) measurementCycles)
                    .append(" ns\n")
                    .append("[LMAX Disruptor]        ")
                    .append(" Time per read:               ")
                    .repeat(' ', threads.length())
                    .append(readTime / (float) measurementCycles)
                    .append(" ns\n");
        }
    }

    private static void benchmarkTelLib (final int warmupCycles,
                                         final int measurementCycles,
                                         final ByteBuffer[] preComputedData,
                                         final ExecutorService writersPool,
                                         final AbstractInterruptibleChannel c,
                                         final ExecutorService readersPool,
                                         final int readersSize,
                                         final StateHolder state,
                                         final StringBuilder bob) {
        final NetworkBuffer buffer = new NetworkBuffer(DATA_SIZE, DATA_SIZE * MAX_DATA_AMOUNT, 1);
        {
            for (int i = 0; i < readersSize; ++i)
                readersPool.execute(() -> {
                    final ByteBuffer buf = localBuffer.get();
                    while (buffer.read(c, buf.clear()) != null)
                        process(buf);
                });
        }
        long writeTime, readTime;
        for (int i = 0; i < warmupCycles; ++i) {
            buffer.write(preComputedData[i % preComputedData.length], commonAddress);
            if ((i & 3) == 0)
                state.counter.getAndIncrement();
        }
        buffer.write(endBuffer, commonAddress);
        ThreadSecondarySeedHandler.spinUntil(
                ContentionHandler.SMART,
                () -> (int) readVar.getAcquire() != 1);
        writeTime = readTime = System.nanoTime();
        for (int i = 0; i < measurementCycles; ++i) {
            buffer.write(preComputedData[i % preComputedData.length], commonAddress);
            if ((i & 3) == 0)
                state.counter.getAndIncrement();
        }
        writeTime = System.nanoTime() - writeTime;
        buffer.write(endBuffer, commonAddress);
        ThreadSecondarySeedHandler.spinUntil(
                ContentionHandler.SMART,
                () -> (int) readVar.getAcquire() != 2);
        readTime = System.nanoTime() - readTime;
        final String threads = String.valueOf(Runtime.getRuntime().availableProcessors());
        bob.append("[TelLib's NetworkBuffer]")
                .append(" Time per write (1-thread):   ")
                .repeat(' ', threads.length())
                .append(writeTime / (float) measurementCycles)
                .append(" ns\n")
                .append("[TelLib's NetworkBuffer]")
                .append(" Time per read:               ")
                .repeat(' ', threads.length())
                .append(readTime / (float) measurementCycles)
                .append(" ns\n");
        for (int i = 0; i < warmupCycles; ++i) {
            final int j = i;
            writersPool.execute(() -> {
                final ByteBuffer buf = preComputedData[j % preComputedData.length];
                //noinspection SynchronizationOnLocalVariableOrMethodParameter
                synchronized (buf) {
                    buffer.write(buf, commonAddress);
                }
                writeVar.getAndAddRelease(1);
            });
            if ((i & 3) == 0)
                state.counter.getAndIncrement();
        }
        ThreadSecondarySeedHandler.spinUntil(
                ContentionHandler.LOW_LATENCY,
                () -> (int) writeVar.getAcquire() != warmupCycles);
        buffer.write(endBuffer, commonAddress);
        writeVar.setOpaque(0);
        ThreadSecondarySeedHandler.spinUntil(
                ContentionHandler.SMART,
                () -> (int) readVar.getAcquire() != 3);
        writeTime = readTime = System.nanoTime();
        for (int i = 0; i < measurementCycles; ++i) {
            final int j = i;
            writersPool.execute(() -> {
                final ByteBuffer buf = preComputedData[j % preComputedData.length];
                //noinspection SynchronizationOnLocalVariableOrMethodParameter
                synchronized (buf) {
                    buffer.write(buf, commonAddress);
                }
                writeVar.getAndAddRelease(1);
            });
            if ((i & 3) == 0)
                state.counter.getAndIncrement();
        }
        ThreadSecondarySeedHandler.spinUntil(
                ContentionHandler.LOW_LATENCY,
                () -> (int) writeVar.getAcquire() != measurementCycles);
        writeTime = System.nanoTime() - writeTime;
        buffer.write(endBuffer, commonAddress);
        writeVar.setOpaque(0);
        ThreadSecondarySeedHandler.spinUntil(
                ContentionHandler.SMART,
                () -> (int) readVar.getAcquire() != 4);
        readTime = System.nanoTime() - readTime;
        readVar.setOpaque(0);
        bob.append("[TelLib's NetworkBuffer]")
                .append(" Time per write (")
                .append(threads)
                .append("-threads):   ")
                .append(writeTime / (float) measurementCycles)
                .append(" ns\n")
                .append("[TelLib's NetworkBuffer]")
                .append(" Time per read:               ")
                .repeat(' ', threads.length())
                .append(readTime / (float) measurementCycles)
                .append(" ns\n");
    }

    private static void process (final ByteBuffer buf) {
        if (buf.get(0) != (byte) 0)
            readVar.getAndAddRelease(1);
    }
}
