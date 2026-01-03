package it.telami.commons.network.channel;

import java.nio.ByteBuffer;

/**
 * This {@link it.telami.commons.network.channel.ChannelNetworkIdentifier network identifier}
 * defines an exclusively local management of the data.
 * @author Telami
 * @since 1.0.1
 */
public final class LocalNetworkIdentifier extends ChannelNetworkIdentifier {
    public LocalNetworkIdentifier (final String identifier) {
        //Hidden implementation...
    }

    public String identifier () {
        //Hidden implementation...
        return null;
    }
    public boolean isExternal () {
        return false;
    }

    public byte[] externalRead (final byte[] container) {
        throw new UnsupportedOperationException();
    }
    public ByteBuffer externalRead (final ByteBuffer container) {
        throw new UnsupportedOperationException();
    }
    public boolean externalWrite (final byte[] data) {
        throw new UnsupportedOperationException();
    }
    public boolean externalWrite (final ByteBuffer data) {
        throw new UnsupportedOperationException();
    }
}
