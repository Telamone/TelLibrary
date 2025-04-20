package it.telami.commons.network.channel;

final class SharedMemoryChannel implements CrossJVMChannel<String> {
    public String identifier () {
        return null;
    }

    public int bandwidth () {
        return 0;
    }

    //Try using the 'container', otherwise return a new 'container'!
    public byte[] receive (byte[] container) {
        return null;
    }

    public boolean send (final String senderID, final byte[] data) {
        return false;
    }

    public boolean isOpen () {
        return false;
    }

    public void close () {}
}
