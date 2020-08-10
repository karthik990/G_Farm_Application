package kotlin;

import kotlin.jvm.functions.Function0;

@Metadata(mo22060bv = {1, 0, 3}, mo22061d1 = {"\u0000\u0018\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\u001a\u0011\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u0003H\b\u001a\u001f\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u00032\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005H\b¨\u0006\u0007"}, mo22062d2 = {"assert", "", "value", "", "lazyMessage", "Lkotlin/Function0;", "", "kotlin-stdlib"}, mo22063k = 5, mo22064mv = {1, 1, 15}, mo22066xi = 1, mo22067xs = "kotlin/PreconditionsKt")
/* compiled from: AssertionsJVM.kt */
class PreconditionsKt__AssertionsJVMKt {
    /* renamed from: assert reason: not valid java name */
    private static final void m4277assert(boolean z) {
        if (_Assertions.ENABLED && !z) {
            throw new AssertionError("Assertion failed");
        }
    }

    /* renamed from: assert reason: not valid java name */
    private static final void m4278assert(boolean z, Function0<? extends Object> function0) {
        if (_Assertions.ENABLED && !z) {
            throw new AssertionError(function0.invoke());
        }
    }
}
