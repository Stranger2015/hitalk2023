package enumus.initializer;

import java.lang.annotation.*;

@Argument
@Repeatable(BooleanValues.class)
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface BooleanValue {
    String name ();
    boolean[] value ();
}

