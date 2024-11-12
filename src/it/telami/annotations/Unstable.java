package it.telami.annotations;

import java.lang.annotation.*;

/**
 * This implementation is not stable yet and may cause
 * stability problems described by {@link Unstable#problems()}. <br>
 * Use at your own risk!
 * @author Dr4aKy
 * @since 1.0.0
 * @see Unstable#version()
 */
@Documented
@Retention(RetentionPolicy.SOURCE)
@Target({ElementType.TYPE, ElementType.METHOD})
public @interface Unstable {
    /**
     * Defines the version that is planned to make this implementation stable.
     * @return The {@link String} representing the version in which this implementation is planned to be stable.
     * @author Dr4aKy
     * @since 1.0.0
     */
    String version() default "Unknown";

    /**
     * Defines the problems that make this implementation unstable.
     * @return The {@link String} representing a description of the problems that make this implementation unstable.
     * @author Dr4aKy
     * @since 1.0.0
     */
    String problems() default "Unknown";
}
