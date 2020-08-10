package kotlin.sequences;

import java.util.Iterator;
import kotlin.Metadata;
import kotlin.jvm.functions.Function2;

@Metadata(mo22060bv = {1, 0, 3}, mo22061d1 = {"\u0000\u0011\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010(\n\u0000*\u0001\u0000\b\n\u0018\u00002\b\u0012\u0004\u0012\u00028\u00000\u0001J\u000f\u0010\u0002\u001a\b\u0012\u0004\u0012\u00028\u00000\u0003H\u0002¨\u0006\u0004¸\u0006\u0000"}, mo22062d2 = {"kotlin/sequences/SequencesKt__SequencesKt$Sequence$1", "Lkotlin/sequences/Sequence;", "iterator", "", "kotlin-stdlib"}, mo22063k = 1, mo22064mv = {1, 1, 15})
/* compiled from: Sequences.kt */
public final class SequencesKt__SequenceBuilderKt$buildSequence$$inlined$Sequence$1 implements Sequence<T> {
    final /* synthetic */ Function2 $builderAction$inlined;

    public SequencesKt__SequenceBuilderKt$buildSequence$$inlined$Sequence$1(Function2 function2) {
        this.$builderAction$inlined = function2;
    }

    public Iterator<T> iterator() {
        return SequencesKt.iterator(this.$builderAction$inlined);
    }
}