package it.telami.annotations;

import java.lang.annotation.*;

/**
 * This method or class is strictly reserved to library's usage. <br>
 * Using or abusing it can cause many functionalities
 * to collapse.
 * @author Telami
 * @since 1.0.0
 */
@Documented
@Retention(RetentionPolicy.SOURCE)
@Target({ElementType.TYPE, ElementType.CONSTRUCTOR, ElementType.METHOD})
public @interface LibraryOnly {}
