package enumus.initializer;

import java.lang.annotation.*;

@Argument
@Repeatable(IntValues.class)
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface IntValue {
    String name ();
    int[] value ();
}

