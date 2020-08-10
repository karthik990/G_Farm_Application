package com.google.firebase.database.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.METHOD, ElementType.FIELD, ElementType.PARAMETER, ElementType.LOCAL_VARIABLE})
@Retention(RetentionPolicy.CLASS)
/* compiled from: com.google.firebase:firebase-database@@17.0.0 */
public @interface NotNull {
    String value() default "";
}
