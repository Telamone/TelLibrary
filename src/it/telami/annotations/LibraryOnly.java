package it.telami.annotations;

import java.lang.annotation.*;

/**
 * This class or method is strictly reserved to library's usage. <br>
 * Using or abusing it can cause many functionalities to collapse. <br>
 * Use at your own risk.
 * @author Telami
 * @since 1.0.0
 */
@Documented
@Retention(RetentionPolicy.SOURCE)
@Target({ElementType.TYPE_USE, ElementType.CONSTRUCTOR, ElementType.METHOD})
public @interface LibraryOnly {}
