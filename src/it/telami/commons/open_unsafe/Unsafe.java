package it.telami.commons.open_unsafe;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.invoke.VarHandle;
import java.lang.reflect.Method;
import java.util.function.BiConsumer;
import java.util.stream.Stream;

/**
 * Utility class created for enabling illegal accesses
 * to resources that normally would be out of the bounds
 * of a normal {@link MethodHandles#lookup() lookup()}. <br>
 * Not all the resources are accessible for security reasons,
 * but the only ones that are not available are: <ul>
 * <li>{@link jdk.internal.misc.Unsafe}</li>
 * <li>{@link sun.misc.Unsafe}</li>
 * <li>{@link Class}</li>
 * <li>{@link ClassLoader}</li>
 * <li>{@link Module}</li>
 * <li>{@link MethodHandles}</li>
 * <li>{@link MethodHandles.Lookup}</li>
 * <li>most of the classes in the module <b>it.telami</b></li>
 * </ul>
 * @author Telami
 * @since 1.0.3
 */
public final class Unsafe {
    private Unsafe () {}

    /**
     * Return the size in bytes of an address.
     * @return the size in bytes of an address
     * @author Telami
     * @since 1.0.3
     */
    public static int getAddressSize () {
        //Hidden implementation...
        return 0;
    }
    /**
     * See {@link jdk.internal.misc.Unsafe#dataCacheLineFlushSize() dataCacheLineFlushSize()}.
     * @return the size in bytes of a cache line or {@code 0}
     * @author Telami
     * @since 1.0.3
     */
    public static int getDataCacheLineFlushSize () {
        //Hidden implementation...
        return 0;
    }

    /**
     * Return a {@link Stream} of {@link VarHandle} derived from the
     * {@link Class#getFields() non-declared fields} of the given
     * {@link Stream} of {@link Class classes}.
     * @param classStream the given {@link Stream} of {@link Class classes}
     * @return a {@link Stream} of {@link VarHandle}
     * @author Telami
     * @since 1.0.3
     */
    public static Stream<VarHandle> extractVars (final Stream<Class<?>> classStream) {
        //Hidden implementation...
        return null;
    }
    /**
     * Return a {@link Stream} of {@link VarHandle} derived from the
     * {@link Class#getDeclaredFields() declared fields} of the given
     * {@link Stream} of {@link Class classes}.
     * @param classStream the given {@link Stream} of {@link Class classes}
     * @return a {@link Stream} of {@link VarHandle}
     * @author Telami
     * @since 1.0.3
     */
    public static Stream<VarHandle> extractDeclaredVars (final Stream<Class<?>> classStream) {
        //Hidden implementation...
        return null;
    }
    /**
     * Return a {@link Stream} of {@link VarHandle} derived from all the
     * fields of the given {@link Stream} of {@link Class classes}. <br>
     * This method is the equivalent of: <pre>{@code
     * Stream.concat(extractVars(classStream), extractDeclaredVars(classStream));
     * }</pre>
     * @param classStream the given {@link Stream} of {@link Class classes}
     * @return a {@link Stream} of {@link VarHandle}
     * @author Telami
     * @since 1.0.3
     */
    public static Stream<VarHandle> extractAllVars (final Stream<Class<?>> classStream) {
        //Hidden implementation...
        return null;
    }

    /**
     * Return a {@link Stream} of {@link MethodHandle} derived from the
     * {@link Class#getMethods() non-declared-methods} and
     * {@link Class#getDeclaredMethods() declared-methods} of the given
     * {@link Stream} of {@link Class classes}.
     * @param classStream the given {@link Stream} of {@link Class classes}
     * @return a {@link Stream} of {@link MethodHandle}
     * @author Telami
     * @since 1.0.3
     */
    public static Stream<MethodHandle> extractAllMethods (final Stream<Class<?>> classStream) {
        //Hidden implementation...
        return null;
    }

    /**
     * During the {@link Unsafe#extractAllMethods(Stream) method extraction},
     * execute the given {@link BiConsumer function} on each of the methods
     * given the {@link Method#getName() method's name} and the
     * {@link MethodHandle}.
     * @param classStream the given {@link Stream} of {@link Class classes}
     * @param function the given {@link BiConsumer function}
     * @author Telami
     * @since 1.0.3
     */
    public static void executeForAllNamedMethods (
            final Stream<Class<?>> classStream,
            final BiConsumer<String, MethodHandle> function) {
        //Hidden implementation...
    }

    /**
     * See {@link java.lang.invoke.MethodHandles.Lookup#findClass(String)}.
     * @param name the given class name
     * @return the requested class
     * @throws LinkageError if the linkage fails
     * @throws ClassNotFoundException if the class cannot be loaded by the lookup class' loader
     * @throws IllegalAccessException if the class is not accessible
     * @throws NullPointerException if the given class name is null
     * @author Telami
     * @since 1.0.3
     */
    public static Class<?> findClass (
            final String name) throws
            ClassNotFoundException,
            IllegalAccessException {
        //Hidden implementation...
        return null;
    }

    /**
     * See {@link java.lang.invoke.MethodHandles.Lookup#findVarHandle(Class, String, Class)}.
     * @param clazz the field's declaring class
     * @param name the field's name
     * @param type the field's class type
     * @return a {@link VarHandle} giving access to a non-static field
     * @throws NoSuchFieldException if the field does not exist
     * @throws IllegalAccessException if access checking fails, or if the field is static
     * @throws NullPointerException if any argument is null
     * @author Telami
     * @since 1.0.3
     */
    public static VarHandle findVarHandle (
            final Class<?> clazz,
            final String name,
            final Class<?> type) throws
            NoSuchFieldException,
            IllegalAccessException {
        //Hidden implementation...
        return null;
    }
    /**
     * See {@link java.lang.invoke.MethodHandles.Lookup#findStaticVarHandle(Class, String, Class)}.
     * @param clazz the field's declaring class
     * @param name the field's name
     * @param type the field's class type
     * @return a {@link VarHandle} giving access to a static field
     * @throws NoSuchFieldException if the field does not exist
     * @throws IllegalAccessException if access checking fails, or if the field is not static
     * @throws NullPointerException if any argument is null
     * @author Telami
     * @since 1.0.3
     */
    public static VarHandle findStaticVarHandle (
            final Class<?> clazz,
            final String name,
            final Class<?> type) throws
            NoSuchFieldException,
            IllegalAccessException {
        //Hidden implementation...
        return null;
    }

    /**
     * See {@link java.lang.invoke.MethodHandles.Lookup#findVirtual(Class, String, MethodType)}.
     * @param clazz the class or interface from which the method is accessed
     * @param name the name of the method
     * @param type the type of the method, with the class argument omitted
     * @return the {@link MethodHandle}
     * @throws NoSuchMethodException if the method does not exist
     * @throws IllegalAccessException if access checking fails,
     *                                or if the method is static,
     *                                or if the method's variable arity modifier bit
     *                                is set and {@code asVarargsCollector} fails
     * @throws NullPointerException if any argument is null
     * @author Telami
     * @since 1.0.3
     */
    public static MethodHandle findMethodHandle (
            final Class<?> clazz,
            final String name,
            final MethodType type) throws
            NoSuchMethodException,
            IllegalAccessException {
        //Hidden implementation...
        return null;
    }
    /**
     * See {@link java.lang.invoke.MethodHandles.Lookup#findStatic(Class, String, MethodType)}.
     * @param clazz the class from which the method is accessed
     * @param name the name of the method
     * @param type the type of the method
     * @return the {@link MethodHandle}
     * @throws NoSuchMethodException if the method does not exist
     * @throws IllegalAccessException if access checking fails,
     *                                or if the method is not static,
     *                                or if the method's variable arity modifier bit
     *                                is set and {@code asVarargsCollector} fails
     * @throws NullPointerException if any argument is null
     * @author Telami
     * @since 1.0.3
     */
    public static MethodHandle findStaticMethodHandle (
            final Class<?> clazz,
            final String name,
            final MethodType type) throws
            NoSuchMethodException,
            IllegalAccessException {
        //Hidden implementation...
        return null;
    }
    /**
     * See {@link java.lang.invoke.MethodHandles.Lookup#findConstructor(Class, MethodType)}.
     * @param clazz the class or interface from which the method is accessed
     * @param type the type of the method, with the class argument omitted, and a void return type
     * @return the {@link MethodHandle}
     * @throws NoSuchMethodException if the constructor does not exist
     * @throws IllegalAccessException if access checking fails
     *                                or if the method's variable arity modifier bit
     *                                is set and {@code asVarargsCollector} fails
     * @throws NullPointerException if any argument is null
     * @author Telami
     * @since 1.0.3
     */
    public static MethodHandle findConstructor (
            final Class<?> clazz,
            final MethodType type) throws
            NoSuchMethodException,
            IllegalAccessException {
        //Hidden implementation...
        return null;
    }

    /**
     * Return the {@link Class#getName() name} of the {@link Class}
     * which called the method in which this method has been called.
     * @return the caller class' {@link Class#getName() name}
     * @author Telami
     * @since 1.0.3
     */
    public static String getCaller () {
        //Hidden implementation...
        return null;
    }
}
