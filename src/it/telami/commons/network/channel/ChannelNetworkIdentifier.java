package it.telami.commons.network.channel;

import it.telami.commons.concurrency.thread.ContentionHandler;

import java.nio.ByteBuffer;

/**
 * Represent the data handler. <br>
 * The instance MUST NOT be reused for more than one {@link CrossJVMChannel}! <br>
 * An implementation of this class determine the sequent points:
 * <h2>Writing</h2>
 * It's used the identifier passed in {@link CrossJVMChannel#send(ChannelNetworkIdentifier,
 * ByteBuffer) send(...)} and {@link ChannelNetworkIdentifier#isExternal()} determines if 
 * the data have to be sent through the {@link ChannelNetworkIdentifier#externalWrite(ByteBuffer)
 * externalWrite(...)} or through the default shared memory operations.
 * <h2>Reading</h2>
 * It's used the identifier passed in {@link CrossJVMChannel#registerOrJoin(String, ChannelNetworkIdentifier,
 * int, int, ContentionHandler) registerOrJoin(...)} and {@link ChannelNetworkIdentifier#isExternal()} determines
 * whether to include the data received through the {@link ChannelNetworkIdentifier#externalRead(ByteBuffer)
 * externalRead(...)} or not.
 * @apiNote The implementation of {@link ChannelNetworkIdentifier#externalRead(ByteBuffer)}
 *          and {@link ChannelNetworkIdentifier#externalRead(byte[])} MUST be non-blocking.
 * @author Telami
 * @since 1.0.1
 */
public abstract class ChannelNetworkIdentifier {
    /**
     * Return a {@link String} representing how this identifier
     * is seen through the channels.
     * @return this network's identifier
     * @author Telami
     * @since 1.0.1
     */
    public abstract String identifier ();

    /**
     * Define if the external implementation have to be used for the
     * communication of process executing in different machines.
     * @return {@code true} if an external communication is required,
     *         {@code false} otherwise
     * @author Telami
     * @since 1.0.1
     */
    public abstract boolean isExternal ();

    /**
     * Return the data received from an unknown subscriber. <br>
     * The data is stored in the given container if the size matches
     * exactly, otherwise a new container is returned. <br>
     * If there aren't data immediately available, then {@code null}
     * is returned.
     * @param container the given container
     * @return the given container, a new container or {@code null}
     *         if there aren't data immediately available
     * @apiNote The implementation MUST be non-blocking.
     * @author Telami
     * @since 1.0.1
     */
    protected abstract byte[] externalRead (final byte[] container);
    /**
     * See {@link ChannelNetworkIdentifier#externalRead(byte[])}.
     * @param container the given container
     * @return the given container, a new container or {@code null}
     *         if there aren't data immediately available
     * @apiNote The implementation MUST be non-blocking.
     * @author Telami
     * @since 1.0.1
     */
    protected abstract ByteBuffer externalRead (final ByteBuffer container);

    /**
     * Return if the given data are successfully sent.
     * @param data the given data
     * @return {@code true} if the given data have been sent successfully,
     *         {@code false} otherwise
     * @author Telami
     * @since 1.0.1
     */
    protected abstract boolean externalWrite (final byte[] data);
    /**
     * See {@link ChannelNetworkIdentifier#externalWrite(byte[])}.
     * @param data the given data
     * @return {@code true} if the given data have been sent successfully,
     *         {@code false} otherwise
     * @author Telami
     * @since 1.0.1
     */
    protected abstract boolean externalWrite (final ByteBuffer data);
}
