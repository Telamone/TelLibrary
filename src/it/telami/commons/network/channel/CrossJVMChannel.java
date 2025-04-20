package it.telami.commons.network.channel;

import java.nio.channels.Channel;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;

/**
 * Create a communication channel between different java processes. <br>
 * This could be done on the same machine or externally using a mixed mode channel. <br>
 * The advantage of this is the use of shared memory for the communication on the same machine. <br>
 * The synchronization is handled by the implementation, and it's all <b>thread safe</b>. <br>
 * Every channel has its own 'path' for being recognized and handled, every subscriber to the
 * channel has its own '{@link CrossJVMChannel#identifier() identifier}'. <br>
 * The {@link Channel#close() closure} signals to all the <b>KNOWN</b> subscribers, if not all
 * the subscribers are notified, then the channel remains open, if its necessary to simply quit
 * the channel, then don't close the channel and dereference the instance. <br>
 * The data have to be already serialized when they are {@link CrossJVMChannel#send(Object, byte[]) sent}
 * and {@link CrossJVMChannel#receive(byte[]) received}. <br>
 * The {@link CrossJVMChannel#bandwidth() bandwidth} doesn't seize the data sent or received.
 * @param <ID> the type that represent the channel identifier
 * @author Telami
 * @since 1.0.0
 */
public interface CrossJVMChannel<ID> extends Channel {
    /**
     * Return the identifier of this subscriber to the channel.
     * @return this subscriber's identifier
     * @author Telami
     * @since 1.0.0
     */
    ID identifier ();
    /**
     * Return the bandwidth of this subscriber to the channel.
     * @return this subscriber's bandwidth
     * @author Telami
     * @since 1.0.0
     */
    int bandwidth ();
    /**
     * Return the data received from an unknown subscriber. <br>
     * The data is stored in the given container if the size matches
     * exactly, otherwise a new container is returned.
     * @param container the given data container
     * @return the given data container or a new container
     * @author Telami
     * @since 1.0.0
     */
    byte[] receive (final byte[] container);
    /**
     * Return if the given data are successfully sent to the receiver. <br>
     * The operation may fail if the receiver isn't registered yet or if
     * the channel has been closed.
     * @param receiverID the given receiver's identifier
     * @param data the given data
     * @return {@code true} if the given data have been sent successfully,
     *         {@code false} otherwise
     * @author Telami
     * @since 1.0.0
     */
    boolean send (final ID receiverID, final byte[] data);

    /**
     * Return an instance of an internal channel.
     * @param path the given channel's path
     * @param identifier this subscriber's identifier
     * @param dataBandwidth this subscriber's bandwidth
     * @param totalSize the given total channel size
     * @return an internal channel instance
     * @author Telami
     * @since 1.0.0
     */
    static CrossJVMChannel<String> registerOrJoinInSharedMemory (final String path, final String identifier, final int dataBandwidth, final int totalSize) {
        return null;
    }
    /**
     * Return asynchronously an instance of an internal channel through a {@link CompletableFuture}.
     * @param path the given channel's path
     * @param identifier this subscriber's identifier
     * @param dataBandwidth this subscriber's bandwidth
     * @param totalSize the given total channel size
     * @return a {@link CompletableFuture} returning an internal channel instance
     * @author Telami
     * @since 1.0.0
     */
    //Suggest the use of 'handleAsync()'!
    static CompletableFuture<CrossJVMChannel<String>> registerOrJoinInSharedMemoryAsync (final String path, final String identifier, final int dataBandwidth, final int totalSize, final Executor executor) {
        return null;
    }
}
