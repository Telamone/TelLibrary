package it.telami.commons.data_structure.buffer;

import it.telami.commons.data_structure.DataStructure;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.spi.AbstractInterruptibleChannel;

/**
 * Class representing an <b>Off-Heap Ring Buffer</b> implementation
 * designed for storing data from {@link AbstractInterruptibleChannel}. <br> <br>
 * The amount of data can exceed the limit of <i>2 GigaBytes</i> thanks
 * to a multi-buffer structure. <br>
 * <b>[--------------------Network Buffer--------------------]</b> <br>
 * <b>[-----Buffer-----] [------Buffer------] [-----Buffer-----]</b> <br>
 * <b>[-Slot--][--Slot-] | [--Slot--][--Slot--] | [-Slot--][--Slot-]</b> <br>
 * <b>[--------------------------------------------------------]</b> <br> <br>
 * This is a <b>FIFO</b> (First-In-First-Out) {@link DataStructure}, this
 * means that if the end of the buffer is reached, the first <i>non-read</i>
 * data are overwritten.
 * @see NetworkBuffer#NetworkBuffer(int, int, int) new NetworkBuffer(...)
 * @author Telami
 * @since 1.0.1
 */
public final class NetworkBuffer implements DataStructure {
    /**
     * Create a new {@link NetworkBuffer} using the given parameters.
     * @param fixedReadWriteBytes the number of bytes read or written
     * @param bufferSize the number of bytes per-buffer <b>(usually:</b> <i>fixedReadWriteBytes</i> <b>*</b> <i>slots-per-buffer</i><b>)</b>
     * @param buffersAmount the number of buffers
     * @author Telami
     * @since 1.0.1
     */
    public NetworkBuffer (final int fixedReadWriteBytes,
                          final int bufferSize,
                          final int buffersAmount) {
        //Hidden implementation...
    }

    public boolean isThreadSafe () {
        return true;
    }

    /**
     * Wait until the first available data are read and put in the
     * given {@link ByteBuffer container}. <br>
     * It is responsibility of the user to ensure that the given
     * container is the same size as the input since the input is
     * inserted with the same effects as
     * {@link ByteBuffer#put(int, byte)}. <br> <br>
     * In case the given {@link AbstractInterruptibleChannel channel}
     * is closed, then this method immediately returns {@code null}.
     * @param c the given channel
     * @param container the given container
     * @return the {@link InetSocketAddress sender's address} if the
     *         given channel is still open, otherwise {@code null}
     * @author Telami
     * @since 1.0.1
     */
    public InetSocketAddress read (final AbstractInterruptibleChannel c, final ByteBuffer container) {
        //Hidden implementation...
        return null;
    }

    /**
     * Write the data from the given {@link ByteBuffer buffer} and mark them
     * as sent by the given {@link InetSocketAddress sender's addess}. <br>
     * It is responsibility of the user to ensure that the given buffer is
     * the same size as this {@link NetworkBuffer}'s buffers since the data
     * are written with the same effects as {@link ByteBuffer#put(int, byte)}. <br>
     * Unlike {@link NetworkBuffer#read(AbstractInterruptibleChannel, ByteBuffer)
     * read(...)}, if the size assertion is not respected, the {@link NetworkBuffer}
     * gets inevitably corrupted and any action may lead to <i>undefined behaviour</i>.
     * @param data the given buffer
     * @param address the given sender's address
     * @author Telami
     * @since 1.0.1
     */
    public void write (final ByteBuffer data, final InetSocketAddress address) {
        //Hidden implementation...
    }
}
