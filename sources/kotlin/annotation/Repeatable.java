package kotlin.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import kotlin.Metadata;

@Target({ElementType.ANNOTATION_TYPE})
@Target(allowedTargets = {AnnotationTarget.ANNOTATION_CLASS})
@Metadata(mo22060bv = {1, 0, 3}, mo22061d1 = {"\u0000\n\n\u0002\u0018\u0002\n\u0002\u0010\u001b\n\u0000\b\u0002\u0018\u00002\u00020\u0001B\u0000¨\u0006\u0002"}, mo22062d2 = {"Lkotlin/annotation/Repeatable;", "", "kotlin-stdlib"}, mo22063k = 1, mo22064mv = {1, 1, 15})
@Retention(RetentionPolicy.RUNTIME)
/* compiled from: Annotations.kt */
public @interface Repeatable {
}
