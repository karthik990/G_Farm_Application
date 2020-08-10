package kotlin.jvm.internal;

import kotlin.Metadata;
import kotlin.reflect.KDeclarationContainer;

@Metadata(mo22060bv = {1, 0, 3}, mo22061d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\bf\u0018\u00002\u00020\u0001R\u0016\u0010\u0002\u001a\u0006\u0012\u0002\b\u00030\u0003X¦\u0004¢\u0006\u0006\u001a\u0004\b\u0004\u0010\u0005¨\u0006\u0006"}, mo22062d2 = {"Lkotlin/jvm/internal/ClassBasedDeclarationContainer;", "Lkotlin/reflect/KDeclarationContainer;", "jClass", "Ljava/lang/Class;", "getJClass", "()Ljava/lang/Class;", "kotlin-stdlib"}, mo22063k = 1, mo22064mv = {1, 1, 15})
/* compiled from: ClassBasedDeclarationContainer.kt */
public interface ClassBasedDeclarationContainer extends KDeclarationContainer {
    Class<?> getJClass();
}
