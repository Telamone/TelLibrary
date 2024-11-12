package it.telami.annotations;

import java.lang.annotation.*;

/**
 * This method has been already optimized. <br>
 * If a performance enhancement is needed, a total rewrite should be considered!
 * @author Dr4aKy
 * @since 1.0.0
 * @see Optimized#tested()
 */
@Documented
@Retention(RetentionPolicy.SOURCE)
@Target({ElementType.CONSTRUCTOR, ElementType.METHOD})
public @interface Optimized {
    /**
     * Defines if this method has been already tested.
     * @return true if tests on this method have been made, otherwise false.
     */
    boolean tested() default false;
}
