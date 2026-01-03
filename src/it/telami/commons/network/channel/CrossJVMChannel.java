package it.telami.commons.network.channel;

import it.telami.commons.concurrency.thread.ContentionHandler;

import java.nio.ByteBuffer;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.function.BiFunction;

/**
 * Class representing a communication channel between different java processes. <br>
 * This could be done on the same machine or externally, as specified by the identifier. <br>
 * The advantage of the local communication is the use of shared memory on the same machine. <br> <br>
 * The synchronization is handled by the implementation, all the methods are <b>thread safe</b>. <br>
 * The local (non-external) implementation offers better performances if the data writing
 * is parallelized, but parallelizing the reading does not (theoretically) enhance performances. <br> <br>
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
     * @author Telami
     * @since 1.0.1
     */
    ByteBuffer receive (final ByteBuffer container);

    /**
     * Return if the given data are successfully sent to the receiver. <br>
     * The operation may fail if the receiver isn't registered yet or if
     * this channel has been closed.
     * @param receiverID the given receiver's identifier
     * @param data the given data
     * @return {@code true} if the given data have been sent successfully,
     *         {@code false} otherwise
     * @apiNote This method is <b>non-blocking</b> if
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
     * @apiNote This method is <b>non-blocking</b> if
     *          {@link ChannelNetworkIdentifier#isExternal() receiverID.isExternal()}
     *          returns {@code false}, otherwise it will be implementation dependent.
     * @author Telami
     * @since 1.0.1
     */
    boolean send (final ChannelNetworkIdentifier receiverID, final ByteBuffer data);

    /**
     * Return an instance of a {@link CrossJVMChannel channel}.
     * @param path the given channel's path
     * @param identifier this subscriber's {@link ChannelNetworkIdentifier identifier}
     * @param dataBandwidth this subscriber's bandwidth
     * @param totalSize the given total channel size
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
     * It's recommended to call on this method's result {@link java.util.concurrent.CompletionStage#handleAsync(BiFunction, Executor) handleAsync(...)}.
     * @param path the given channel's path
     * @param identifier this subscriber's {@link ChannelNetworkIdentifier identifier}
     * @param dataBandwidth this subscriber's bandwidth
     * @param totalSize the given total channel size
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
