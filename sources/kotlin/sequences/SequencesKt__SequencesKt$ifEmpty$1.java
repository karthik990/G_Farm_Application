package kotlin.sequences;

import java.util.Iterator;
import kotlin.Metadata;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.RestrictedSuspendLambda;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;

@Metadata(mo22060bv = {1, 0, 3}, mo22061d1 = {"\u0000\u0010\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\u0010\u0000\u001a\u00020\u0001\"\u0004\b\u0000\u0010\u0002*\b\u0012\u0004\u0012\u0002H\u00020\u0003H@¢\u0006\u0004\b\u0004\u0010\u0005"}, mo22062d2 = {"<anonymous>", "", "T", "Lkotlin/sequences/SequenceScope;", "invoke", "(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;"}, mo22063k = 3, mo22064mv = {1, 1, 15})
@DebugMetadata(mo22077c = "kotlin.sequences.SequencesKt__SequencesKt$ifEmpty$1", mo22078f = "Sequences.kt", mo22079i = {0, 0, 1, 1}, mo22080l = {67, 69}, mo22081m = "invokeSuspend", mo22082n = {"$this$sequence", "iterator", "$this$sequence", "iterator"}, mo22083s = {"L$0", "L$1", "L$0", "L$1"})
/* compiled from: Sequences.kt */
final class SequencesKt__SequencesKt$ifEmpty$1 extends RestrictedSuspendLambda implements Function2<SequenceScope<? super T>, Continuation<? super Unit>, Object> {
    final /* synthetic */ Function0 $defaultValue;
    final /* synthetic */ Sequence $this_ifEmpty;
    Object L$0;
    Object L$1;
    int label;

    /* renamed from: p$ */
    private SequenceScope f4204p$;

    SequencesKt__SequencesKt$ifEmpty$1(Sequence sequence, Function0 function0, Continuation continuation) {
        this.$this_ifEmpty = sequence;
        this.$defaultValue = function0;
        super(2, continuation);
    }

    public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
        Intrinsics.checkParameterIsNotNull(continuation, "completion");
        SequencesKt__SequencesKt$ifEmpty$1 sequencesKt__SequencesKt$ifEmpty$1 = new SequencesKt__SequencesKt$ifEmpty$1(this.$this_ifEmpty, this.$defaultValue, continuation);
        sequencesKt__SequencesKt$ifEmpty$1.f4204p$ = (SequenceScope) obj;
        return sequencesKt__SequencesKt$ifEmpty$1;
    }

    public final Object invoke(Object obj, Object obj2) {
        return ((SequencesKt__SequencesKt$ifEmpty$1) create(obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    public final Object invokeSuspend(Object obj) {
        Object coroutine_suspended = IntrinsicsKt.getCOROUTINE_SUSPENDED();
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            SequenceScope sequenceScope = this.f4204p$;
            Iterator it = this.$this_ifEmpty.iterator();
            if (it.hasNext()) {
                this.L$0 = sequenceScope;
                this.L$1 = it;
                this.label = 1;
                if (sequenceScope.yieldAll(it, (Continuation<? super Unit>) this) == coroutine_suspended) {
                    return coroutine_suspended;
                }
            } else {
                Sequence sequence = (Sequence) this.$defaultValue.invoke();
                this.L$0 = sequenceScope;
                this.L$1 = it;
                this.label = 2;
                if (sequenceScope.yieldAll(sequence, (Continuation<? super Unit>) this) == coroutine_suspended) {
                    return coroutine_suspended;
                }
            }
        } else if (i == 1 || i == 2) {
            Iterator it2 = (Iterator) this.L$1;
            SequenceScope sequenceScope2 = (SequenceScope) this.L$0;
            ResultKt.throwOnFailure(obj);
        } else {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        return Unit.INSTANCE;
    }
}
