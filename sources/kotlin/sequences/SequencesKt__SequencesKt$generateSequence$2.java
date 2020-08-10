package kotlin.sequences;

import kotlin.Metadata;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Lambda;

@Metadata(mo22060bv = {1, 0, 3}, mo22061d1 = {"\u0000\f\n\u0002\b\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\u0010\u0000\u001a\u0004\u0018\u0001H\u0001\"\b\b\u0000\u0010\u0001*\u00020\u0002H\n¢\u0006\u0004\b\u0003\u0010\u0004"}, mo22062d2 = {"<anonymous>", "T", "", "invoke", "()Ljava/lang/Object;"}, mo22063k = 3, mo22064mv = {1, 1, 15})
/* compiled from: Sequences.kt */
final class SequencesKt__SequencesKt$generateSequence$2 extends Lambda implements Function0<T> {
    final /* synthetic */ Object $seed;

    SequencesKt__SequencesKt$generateSequence$2(Object obj) {
        this.$seed = obj;
        super(0);
    }

    public final T invoke() {
        return this.$seed;
    }
}
