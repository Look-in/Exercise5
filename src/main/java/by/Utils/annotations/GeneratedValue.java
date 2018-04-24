package by.Utils.annotations;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target(value={FIELD})
@Retention(value=RUNTIME)
public @interface GeneratedValue {

    GenerationType strategy();

    enum GenerationType {
        IDENTITY,
        AUTO,
        SEQUENCE
    }
}
