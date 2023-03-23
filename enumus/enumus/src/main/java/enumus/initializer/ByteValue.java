package enumus.initializer;

import java.lang.annotation.*;

@Argument
@Repeatable(ByteValues.class)
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface ByteValue {
    String name ();
    byte[] value ();
}

