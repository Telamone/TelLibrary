package it.telami.commons.network.channel;

import it.telami.commons.concurrency.thread.ContentionHandler;

import java.lang.invoke.VarHandle;
import java.nio.ByteBuffer;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.concurrent.TimeUnit;
import java.util.function.BiFunction;

/**
 * Class representing a communication channel between different java processes. <br>
 * This could be done on the same machine or externally, as specified by the identifier. <br>
 * The advantage of the local communication is the use of shared memory on the same machine. <br> <br>
 * The synchronization is handled by the implementation, all the methods are <b>thread safe</b>. <br> <br>
 * Every channel has its own 'path' for being recognized and handled, that is needed since some
 * {@link it.telami.commons.util.OperatingSystem Operating Systems} don't support directly the
 * use of shared memory, so the use of a mapped file (represented by the 'path') is needed. <br>
 * Every subscriber to the channel has its own '{@link CrossJVMChannel#identifier() identifier}',
 * that handles how the data are read and wrote. <br> <br>
 * The {@link java.nio.channels.Channel#close() closure} stops any further data to be read and
 * written. <br>
 * This means that if an external process had a queue of data to write through this channel,
 * the writing of those data will stalls until this channel re-open; otherwise, if, before the
 * re-opening, the external process exits its execution, those data will be lost. <br>
 * If it's needed to modify an existing channel bandwidth, it's necessary to close all the
 * existing ones and then restart them. <br> <br>
 * The data can be received and sent using both {@code byte[]} and {@link ByteBuffer}. <br> <br>
 * The {@link CrossJVMChannel#bandwidth() bandwidth} doesn't seize the data sent or received,
 * but more it's wider, less are the transactions required and more is the memory required.
 * @apiNote The queue used internally has no limit (it's not a ring buffer) so it's necessary
 *          to manually handle cases where the packets exceed a certain threshold.
 * @author Telami
 * @since 1.0.0
 */
public interface CrossJVMChannel extends java.nio.channels.Channel {
    /**
     * Return the {@link ChannelNetworkIdentifier identifier} of this subscriber to the {@link CrossJVMChannel channel}.
     * @return this subscriber's identifier
     * @author Telami
     * @since 1.0.0
     */
    ChannelNetworkIdentifier identifier ();

    /**
     * Return the bandwidth of this subscriber to the {@link CrossJVMChannel channel}.
     * @return this subscriber's bandwidth
     * @author Telami
     * @since 1.0.0
     */
    int bandwidth ();

    /**
     * Return the data received from an unknown subscriber. <br>
     * The data is stored in the given container if the size matches
     * exactly, otherwise a new container is returned. <br>
     * If the channel is closed while receiving, for avoiding a soft
     * lock, the current thread is awakened and consequently it will
     * return null.
     * @param container the given data container
     * @return the given data container, a new container or {@code
     *         null} if the channel is closed while receiving
     * @author Telami
     * @since 1.0.0
     */
    byte[] receive (final byte[] container);
    /**
     * See {@link CrossJVMChannel#receive(byte[])}.
     * @param container the given data container
     * @return the given data container, a new container or {@code
     *         null} if the channel is closed while receiving
     * @apiNote In case a new container is returned, the old one
     *          will have its position and limit set to the last
     *          byte written by the {@link CrossJVMChannel channel}.
     * @author Telami
     * @since 1.0.1
     */
    ByteBuffer receive (final ByteBuffer container);
    /**
     * Return the data received from an unknown subscriber. <br>
     * The data is stored in the given container if the size matches
     * exactly, otherwise a new container is returned. <br>
     * If the channel is closed while receiving, for avoiding a soft
     * lock, the current thread is awakened and consequently it will
     * return null. <br>
     * If the current {@link Thread} gets {@link Thread#interrupted()
     * interrupted}, then null is returned. <br>
     * While waiting, if the wait exceeds the given time, then null
     * is returned.
     * @param container the given data container
     * @param time the given time
     * @param unit the given time's unit
     * @return the given data container, a new container or {@code
     *         null} if the channel is closed while receiving, the
     *         current {@link Thread} have been {@link Thread#interrupted()
     *         interrupted} or the wait exceeded the given time
     * @author Telami
     * @since 1.0.3
     */
    byte[] receive (final byte[] container, final long time, final TimeUnit unit);
    /**
     * See {@link CrossJVMChannel#receive(byte[], long, TimeUnit)}.
     * @param container the given data container
     * @param time the given time
     * @param unit the given time's unit
     * @return the given data container, a new container or {@code
     *         null} if the channel is closed while receiving, the
     *         current {@link Thread} have been {@link Thread#interrupted()
     *         interrupted} or the wait exceeded the given time
     * @apiNote In case a new container is returned, the old one
     *          will have its position and limit set to the last
     *          byte written by the {@link CrossJVMChannel channel}.
     * @author Telami
     * @since 1.0.3
     */
    ByteBuffer receive (final ByteBuffer container, final long time, final TimeUnit unit);

    /**
     * Return if the given data are successfully sent to the receiver. <br>
     * The operation may fail if the receiver isn't registered yet or if
     * this channel has been closed.
     * @param receiverID the given receiver's identifier
     * @param data the given data
     * @return {@code true} if the given data have been sent successfully,
     *         {@code false} otherwise
     * @apiNote This method is <b>blocking</b> if
     *          {@link ChannelNetworkIdentifier#isExternal() receiverID.isExternal()}
     *          returns {@code false}, otherwise it will be implementation dependent.
     * @author Telami
     * @since 1.0.0
     */
    boolean send (final ChannelNetworkIdentifier receiverID, final byte[] data);
    /**
     * See {@link CrossJVMChannel#send(ChannelNetworkIdentifier, byte[])}.
     * @param receiverID the given receiver's identifier
     * @param data the given data
     * @return {@code true} if the given data have been sent successfully,
     *         {@code false} otherwise
     * @apiNote This method is <b>blocking</b> if
     *          {@link ChannelNetworkIdentifier#isExternal() receiverID.isExternal()}
     *          returns {@code false}, otherwise it will be implementation dependent.
     * @author Telami
     * @since 1.0.1
     */
    boolean send (final ChannelNetworkIdentifier receiverID, final ByteBuffer data);
    /**
     * Return if the given data are successfully sent to the receiver. <br>
     * The operation may fail if the receiver isn't registered yet,
     * this channel has been closed, the data have not been sent before
     * reaching the given time limit, or if the current {@link Thread}
     * has been {@link Thread#interrupted() interrupted}.
     * @param receiverID the given receiver's identifier
     * @param data the given data
     * @param time the given time
     * @param unit the given time's unit
     * @return {@code true} if the given data have been sent successfully,
     *         {@code false} otherwise
     * @apiNote This method is <b>blocking</b> if
     *          {@link ChannelNetworkIdentifier#isExternal() receiverID.isExternal()}
     *          returns {@code false}, otherwise it will be implementation dependent.
     * @author Telami
     * @since 1.0.3
     */
    boolean send (final ChannelNetworkIdentifier receiverID, final byte[] data, final long time, final TimeUnit unit);
    /**
     * See {@link CrossJVMChannel#send(ChannelNetworkIdentifier, byte[], long, TimeUnit)}.
     * @param receiverID the given receiver's identifier
     * @param data the given data
     * @param time the given time
     * @param unit the given time's unit
     * @return {@code true} if the given data have been sent successfully,
     *         {@code false} otherwise
     * @apiNote This method is <b>blocking</b> if
     *          {@link ChannelNetworkIdentifier#isExternal() receiverID.isExternal()}
     *          returns {@code false}, otherwise it will be implementation dependent.
     * @author Telami
     * @since 1.0.3
     */
    boolean send (final ChannelNetworkIdentifier receiverID, final ByteBuffer data, final long time, final TimeUnit unit);

    /**
     * Reset the state of this {@link CrossJVMChannel}. <br>
     * It's useful in case a JVM crash is detected and there is no other way
     * to recover from that. <br>
     * Using this method, the file substitution with a non-corrupted one may be avoided.
     * @apiNote This method has the same ordering effects as {@link VarHandle#releaseFence()}.
     * @author Telami
     * @since 1.0.3
     */
    void resetState ();

    /**
     * Return an instance of a {@link CrossJVMChannel channel}.
     * @param path the given channel's path
     * @param identifier this subscriber's {@link ChannelNetworkIdentifier identifier}
     * @param dataBandwidth this subscriber's bandwidth. <br>
     *                      The implementation will add a 16 byte header and align the
     *                      resulting bandwidth to 8 bytes
     * @param totalSize the given total channel size. <br>
     *                  The correct formula for calculating it is:
     *                  <pre>{@code (dataBandwidth + 16) * maximumNumberOfSubscribers}</pre>
     *                  assuming that all the subscribers have all the same bandwidth and
     *                  that it's already been aligned to 8 bytes
     * @param handler the given {@link ContentionHandler handler}
     * @return a channel instance
     * @apiNote The implementation can freely redefine the given 'dataBandwidth' for
     *          matching the internal layout.
     * @author Telami
     * @since 1.0.0
     */
    static CrossJVMChannel registerOrJoin (final String path, final ChannelNetworkIdentifier identifier, final int dataBandwidth, final int totalSize, final ContentionHandler handler) {
        //Hidden implementation...
        return null;
    }
    /**
     * Return asynchronously an instance of a {@link CrossJVMChannel channel} through a {@link CompletableFuture}. <br>
     * It's recommended to call on this method's result {@link java.util.concurrent.CompletionStage#handle(BiFunction) handle(...)}
     * or {@link java.util.concurrent.CompletionStage#handleAsync(BiFunction, Executor) handleAsync(...)}.
     * @param path the given channel's path
     * @param identifier this subscriber's {@link ChannelNetworkIdentifier identifier}
     * @param dataBandwidth this subscriber's bandwidth. <br>
     *                      The implementation will add a 16 byte header and align the
     *                      resulting bandwidth to 8 bytes.
     * @param totalSize the given total channel size. <br>
     *                  The correct formula for calculating it is:
     *                  <pre>{@code (dataBandwidth + 16) * maximumNumberOfSubscribers}</pre>
     *                  assuming that all the subscribers have all the same bandwidth and
     *                  that it's already been aligned to 8 bytes
     * @param handler the given {@link ContentionHandler handler}
     * @param executor the given {@link CompletableFuture}'s {@link Executor executor}
     * @return a CompletableFuture returning a channel instance
     * @apiNote The implementation can freely redefine the given 'dataBandwidth' for
     *          matching the internal layout.
     * @author Telami
     * @since 1.0.0
     */
    static CompletableFuture<CrossJVMChannel> registerOrJoinAsync (final String path, final ChannelNetworkIdentifier identifier, final int dataBandwidth, final int totalSize, final ContentionHandler handler, final Executor executor) {
        //Hidden implementation...
        return null;
    }

    default void close () {}
}
