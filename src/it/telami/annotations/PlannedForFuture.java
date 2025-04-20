package it.telami.annotations;

import java.lang.annotation.*;

/**
 * This class or method is not supported or implemented yet and will throw
 * an {@link UnsupportedOperationException} when called.
 * @author Telami
 * @since 1.0.0
 * @see PlannedForFuture#version()
 */
@Documented
@Retention(RetentionPolicy.SOURCE)
@Target({ElementType.TYPE, ElementType.METHOD})
public @interface PlannedForFuture {
    /**
     * Defines the release version.
     * @return The {@link String} representing the version this method is planned for.
     * @author Telami
     * @since 1.0.0
     */
    String version () default "Unknown";
}
