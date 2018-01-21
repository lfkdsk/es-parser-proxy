
package dashbase.meta;


import java.lang.annotation.*;

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
public @interface Bind {

    /**
     * Grammar Mode
     */
    GrammarMode mode() default GrammarMode.PRIMARY;

    /**
     * Bind Method Name
     */
    String name();

    /**
     * Prefix Method Support
     */
    String[] prefix() default {};

    /**
     * Insert Position
     */
    String insert() default "";
}

