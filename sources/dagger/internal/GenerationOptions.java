package dagger.internal;

import java.lang.annotation.ElementType;
import java.lang.annotation.Target;

@Target({ElementType.TYPE})
public @interface GenerationOptions {
    boolean fastInit();
}
