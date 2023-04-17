package enumus.initializer;

import java.lang.annotation.*;

@Argument
@Repeatable(LongValues.class)
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface LongValue {
    String name ();
    long[] value ();
}

