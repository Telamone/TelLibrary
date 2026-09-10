package it.telami.commons.data_structure.buffer;

import it.telami.commons.concurrency.thread.ContentionHandler;
import it.telami.commons.data_structure.DataStructure;

import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.spi.AbstractInterruptibleChannel;

/**
 * Class representing an improved version of {@link NetworkBuffer}. <br>
 * This is an <b>Off-Heap Ring Buffer</b> implementation designed for
 * storing data from {@link AbstractInterruptibleChannel} too. <br>
 * The memory limits of the {@link NetworkBuffer} are removed and so
 * there is only a continuous memory segment (the buffer) handling the
 * data, no more multi-buffer structure. <br>
 * The number of bytes read and written is not fixed any more. <br>
 * This is a <b>FIFO</b> (First-In-First-Out) {@link DataStructure},
 * this means that if the end of the buffer is reached, the first
 * non-read data are overwritten. <br>
 * @apiNote The use of a continuous memory segment imposes the need to
 * {@link AutoCloseable#close() close} explicitly the buffer before
 * it gets collected (otherwise a memory leak will occur)! <br> And to
 * do so ONLY AFTER all the readers and writers have finished their
 * work on this buffer (otherwise a Use-After-Free will be triggered)!
 * @author Telami
 * @since 1.0.3
 */
public final class ContinuousNetworkBuffer implements DataStructure, AutoCloseable {
    /**
     * Create a new {@link ContinuousNetworkBuffer} using the given parameters.
     * @param maximumReadWriteBytes the maximum number of bytes read or written
     * @param bufferSize the total size of the buffer
     * @throws IllegalArgumentException if either the given maximum number of bytes
     *                                  read or written is negative or higher than
     *                                  the given total size of the buffer that has
     *                                  to be positive
     * @author Telami
     * @since 1.0.3
     */
    public ContinuousNetworkBuffer (final int maximumReadWriteBytes,
                                    final long bufferSize) {
        this(maximumReadWriteBytes, bufferSize, ContentionHandler.SMART);
    }
    /**
     * Create a new {@link ContinuousNetworkBuffer} using the given parameters.
     * @param maximumReadWriteBytes the maximum number of bytes read or written
     * @param bufferSize the total size of the buffer
     * @param handler the given {@link ContentionHandler handler}
     * @throws IllegalArgumentException if either the given handler is null or
     *                                  the given maximum number of bytes
     *                                  read or written is negative or higher than
     *                                  the given total size of the buffer that has
     *                                  to be positive
     * @author Telami
     * @since 1.0.3
     */
    public ContinuousNetworkBuffer (int maximumReadWriteBytes,
                                    long bufferSize,
                                    final ContentionHandler handler) {
        //Hidden implementation...
    }

    public boolean isThreadSafe () {
        return true;
    }

    public void close () {
        //Hidden implementation...
    }

    /**
     * Wait until the first available data are read and put in the
     * given {@link ByteBuffer buffer}. <br>
     * The given buffer's data are inserted with the same effects as
     * {@link ByteBuffer#put(byte)}. <br> <br>
     * In case the given {@link AbstractInterruptibleChannel channel}
     * is closed, then this method immediately returns {@code null}.
     * @param channel the given channel
     * @param buffer the given {@link ByteBuffer#isDirect() direct} buffer
     * @return the {@link SocketAddress sender's address} if the
     *         given channel and this buffer are still open, otherwise
     *         {@code null}
     * @throws IllegalArgumentException if the given channel is null or
     *                                  the given buffer is either null
     *                                  or non-{@link ByteBuffer#isDirect() direct}
     * @author Telami
     * @since 1.0.3
     */
    public SocketAddress read (final AbstractInterruptibleChannel channel, final ByteBuffer buffer) {
        //Hidden implementation...
        return null;
    }

    /**
     * Write the data from the given {@link ByteBuffer buffer} and mark them
     * as sent by the given {@link SocketAddress sender's addess}. <br>
     * The given buffer's data are inserted with the same effects as
     * {@link ByteBuffer#put(byte)}. <br>
     * Unlike {@link NetworkBuffer#read(AbstractInterruptibleChannel, ByteBuffer)
     * read(...)}, if the size assertion is not respected, the {@link NetworkBuffer}
     * gets inevitably corrupted and any action may lead to <i>undefined behaviour</i>.
     * @param buffer the given {@link ByteBuffer#isDirect() direct} buffer
     * @param address the given sender's address
     * @throws IllegalArgumentException if the given buffer is either null or
     *                                  non-{@link ByteBuffer#isDirect() direct}
     *                                  or the given sender's address is null
     * @author Telami
     * @since 1.0.3
     */
    public void write (final ByteBuffer buffer, final SocketAddress address) {
        //Hidden implementation...
    }
}
