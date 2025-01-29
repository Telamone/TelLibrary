package it.telami.commons.io.bits;

import java.io.Serializable;

public final class ByteConverter {
    private ByteConverter () {}

    public static <S extends Serializable> byte[] toByteArray (final S s) {
        return null;
    }

    public static <R extends Serializable> R toSerializable (final byte[] b) {
        return null;
    }

    public static <S extends Serializable> void copyInto (final byte[] b, final int offset, final S s) {}

    public static <R extends Serializable> R toSerializable (final byte[] b, final int offset) {
        return null;
    }
}