package com.startapp.common.p042c;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
/* renamed from: com.startapp.common.c.f */
/* compiled from: StartAppSDK */
public @interface C2362f {
    /* renamed from: a */
    boolean mo20785a() default false;

    /* renamed from: b */
    Class mo20786b() default Object.class;

    /* renamed from: c */
    Class mo20787c() default String.class;

    /* renamed from: d */
    Class mo20788d() default String.class;

    /* renamed from: e */
    Class mo20789e() default String.class;

    /* renamed from: f */
    String mo20790f() default "";
}
