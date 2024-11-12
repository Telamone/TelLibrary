package it.telami.commons.unsafe;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodType;
import java.lang.invoke.VarHandle;
import java.util.stream.Stream;

public final class Unrestricted {
    private Unrestricted () {}

    public static Stream<VarHandle> extractVars (final Stream<Class<?>> classStream) {
        return null;
    }
    public static Stream<VarHandle> extractDeclaredVars (final Stream<Class<?>> classStream) {
        return null;
    }
    public static Stream<VarHandle> extractAllVars (final Stream<Class<?>> classStream) {
        return null;
    }

    public static VarHandle findVarHandle (
            final Class<?> clazz,
            final String name,
            final Class<?> type) throws
            NoSuchFieldException,
            IllegalAccessException {
        return null;
    }
    public static VarHandle findStaticVarHandle (
            final Class<?> clazz,
            final String name,
            final Class<?> type) throws
            NoSuchFieldException,
            IllegalAccessException {
        return null;
    }

    public static MethodHandle findMethodHandle (
            final Class<?> clazz,
            final String name,
            final MethodType type) throws
            NoSuchMethodException,
            IllegalAccessException {
        return null;
    }
    public static MethodHandle findStaticMethodHandle (
            final Class<?> clazz,
            final String name,
            final MethodType type) throws
            NoSuchMethodException,
            IllegalAccessException {
        return null;
    }
}
