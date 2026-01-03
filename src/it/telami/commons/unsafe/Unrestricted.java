package it.telami.commons.unsafe;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodType;
import java.lang.invoke.VarHandle;
import java.util.function.BiConsumer;
import java.util.stream.Stream;

public final class Unrestricted {
    public static final int ADDRESS_SIZE = 0;
    public static final int CACHE_LINE_SIZE = 0;

    private Unrestricted () {}

    public static Stream<VarHandle> extractVars (final Stream<Class<?>> classStream) {
        //Hidden implementation...
        return null;
    }
    public static Stream<VarHandle> extractDeclaredVars (final Stream<Class<?>> classStream) {
        //Hidden implementation...
        return null;
    }
    public static Stream<VarHandle> extractAllVars (final Stream<Class<?>> classStream) {
        //Hidden implementation...
        return null;
    }

    public static Stream<MethodHandle> extractAllMethods (final Stream<Class<?>> classStream) {
        //Hidden implementation...
        return null;
    }
    public static void executeForAllNamedMethods (final Stream<Class<?>> classStream, final BiConsumer<String, MethodHandle> function) {
        //Hidden implementation...
    }

    public static VarHandle findVarHandle (
            final Class<?> clazz,
            final String name,
            final Class<?> type) throws
            NoSuchFieldException,
            IllegalAccessException {
        //Hidden implementation...
        return null;
    }
    public static VarHandle findStaticVarHandle (
            final Class<?> clazz,
            final String name,
            final Class<?> type) throws
            NoSuchFieldException,
            IllegalAccessException {
        //Hidden implementation...
        return null;
    }

    public static MethodHandle findMethodHandle (
            final Class<?> clazz,
            final String name,
            final MethodType type) throws
            NoSuchMethodException,
            IllegalAccessException {
        //Hidden implementation...
        return null;
    }
    public static MethodHandle findStaticMethodHandle (
            final Class<?> clazz,
            final String name,
            final MethodType type) throws
            NoSuchMethodException,
            IllegalAccessException {
        //Hidden implementation...
        return null;
    }

    public static MethodHandle findConstructor (
            final Class<?> clazz,
            final MethodType type) throws
            NoSuchMethodException,
            IllegalAccessException {
        //Hidden implementation...
        return null;
    }

    public static Object getInternalUnsafe () {
        //Hidden implementation...
        return null;
    }

    public static String getCaller () {
        //Hidden implementation...
        return null;
    }
}
