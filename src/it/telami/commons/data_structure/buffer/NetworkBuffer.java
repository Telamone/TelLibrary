package it.telami.commons.data_structure.buffer;

import it.telami.commons.data_structure.DataStructure;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.spi.AbstractInterruptibleChannel;

public final class NetworkBuffer implements DataStructure {
    public NetworkBuffer (final int fixedReadWriteBytes,
                          final int bufferSize,
                          final int buffersAmount) {
        //Hidden implementation...
    }

    public boolean isThreadSafe () {
        return true;
    }

    public InetSocketAddress read (final AbstractInterruptibleChannel c, final ByteBuffer container) {
        //Hidden implementation...
        return null;
    }

    public void write (final ByteBuffer data, final InetSocketAddress address) {
        //Hidden implementation...
    }
}
