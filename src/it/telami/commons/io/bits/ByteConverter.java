package it.telami.commons.io.bits;

import java.io.Serializable;

public final class ByteConverter {
    private ByteConverter () {}

    public static byte[] toByteArray (final Serializable s) {
        return null;
    }

    public static <R extends Serializable> R toSerializable (final byte[] b) {
        return null;
    }

    public static void copyInto (final byte[] b, final int offset, final Serializable s) {}

    public static <R extends Serializable> R toSerializable (final byte[] b, final int offset) {
        return null;
    }
}