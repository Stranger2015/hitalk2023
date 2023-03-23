package enumus.initializer;

import java.lang.annotation.*;

@Argument
@Repeatable(ShortValues.class)
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface ShortValue {
    String name ();
    short[] value ();
}

