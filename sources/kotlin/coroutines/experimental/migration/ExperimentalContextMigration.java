package kotlin.coroutines.experimental.migration;

import kotlin.Metadata;
import kotlin.coroutines.CoroutineContext;
import kotlin.coroutines.experimental.AbstractCoroutineContextElement;
import kotlin.jvm.internal.Intrinsics;

@Metadata(mo22060bv = {1, 0, 3}, mo22061d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\b\u0002\u0018\u0000 \u00072\u00020\u0001:\u0001\u0007B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006¨\u0006\b"}, mo22062d2 = {"Lkotlin/coroutines/experimental/migration/ExperimentalContextMigration;", "Lkotlin/coroutines/experimental/AbstractCoroutineContextElement;", "context", "Lkotlin/coroutines/CoroutineContext;", "(Lkotlin/coroutines/CoroutineContext;)V", "getContext", "()Lkotlin/coroutines/CoroutineContext;", "Key", "kotlin-stdlib-coroutines"}, mo22063k = 1, mo22064mv = {1, 1, 15})
/* compiled from: CoroutinesMigration.kt */
final class ExperimentalContextMigration extends AbstractCoroutineContextElement {
    public static final Key Key = new Key(null);
    private final CoroutineContext context;

    @Metadata(mo22060bv = {1, 0, 3}, mo22061d1 = {"\u0000\u0010\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u0003\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0003¨\u0006\u0004"}, mo22062d2 = {"Lkotlin/coroutines/experimental/migration/ExperimentalContextMigration$Key;", "Lkotlin/coroutines/experimental/CoroutineContext$Key;", "Lkotlin/coroutines/experimental/migration/ExperimentalContextMigration;", "()V", "kotlin-stdlib-coroutines"}, mo22063k = 1, mo22064mv = {1, 1, 15})
    /* compiled from: CoroutinesMigration.kt */
    public static final class Key implements kotlin.coroutines.experimental.CoroutineContext.Key<ExperimentalContextMigration> {
        private Key() {
        }

        public /* synthetic */ Key(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    public ExperimentalContextMigration(CoroutineContext coroutineContext) {
        Intrinsics.checkParameterIsNotNull(coroutineContext, "context");
        super(Key);
        this.context = coroutineContext;
    }

    public final CoroutineContext getContext() {
        return this.context;
    }
}
