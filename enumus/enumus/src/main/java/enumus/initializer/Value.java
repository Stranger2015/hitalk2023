package enumus.initializer;

import java.lang.annotation.*;

@Argument
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.ANNOTATION_TYPE, ElementType.FIELD})
@Repeatable(Values.class)
public @interface Value {
    String name ();
    String[] value ();
    Class type () default String.class;
    Class<?> factory () default String.class;
    String factoryMethod () default ""; // default for ConstructorFactory that implements Function

}
