package kotlin.sequences;

import java.util.Iterator;
import kotlin.Metadata;
import kotlin.jvm.internal.Ref.BooleanRef;

@Metadata(mo22060bv = {1, 0, 3}, mo22061d1 = {"\u0000\u0011\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010(\n\u0000*\u0001\u0000\b\n\u0018\u00002\b\u0012\u0004\u0012\u00028\u00000\u0001J\u000f\u0010\u0002\u001a\b\u0012\u0004\u0012\u00028\u00000\u0003H\u0002¨\u0006\u0004"}, mo22062d2 = {"kotlin/sequences/SequencesKt___SequencesKt$minus$1", "Lkotlin/sequences/Sequence;", "iterator", "", "kotlin-stdlib"}, mo22063k = 1, mo22064mv = {1, 1, 15})
/* compiled from: _Sequences.kt */
public final class SequencesKt___SequencesKt$minus$1 implements Sequence<T> {
    final /* synthetic */ Object $element;
    final /* synthetic */ Sequence $this_minus;

    SequencesKt___SequencesKt$minus$1(Sequence<? extends T> sequence, Object obj) {
        this.$this_minus = sequence;
        this.$element = obj;
    }

    public Iterator<T> iterator() {
        BooleanRef booleanRef = new BooleanRef();
        booleanRef.element = false;
        return SequencesKt.filter(this.$this_minus, new SequencesKt___SequencesKt$minus$1$iterator$1(this, booleanRef)).iterator();
    }
}
