package kotlin.collections;

import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.RestrictedSuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import kotlin.sequences.SequenceScope;

@Metadata(mo22060bv = {1, 0, 3}, mo22061d1 = {"\u0000\u0014\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010 \n\u0002\b\u0002\u0010\u0000\u001a\u00020\u0001\"\u0004\b\u0000\u0010\u0002*\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u0002H\u00020\u00040\u0003H@¢\u0006\u0004\b\u0005\u0010\u0006"}, mo22062d2 = {"<anonymous>", "", "T", "Lkotlin/sequences/SequenceScope;", "", "invoke", "(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;"}, mo22063k = 3, mo22064mv = {1, 1, 15})
@DebugMetadata(mo22077c = "kotlin.collections.SlidingWindowKt$windowedIterator$1", mo22078f = "SlidingWindow.kt", mo22079i = {0, 0, 0, 0, 0, 0, 1, 1, 1, 1, 1, 2, 2, 2, 2, 2, 3, 3, 3, 3, 4, 4, 4, 4}, mo22080l = {34, 40, 49, 55, 58}, mo22081m = "invokeSuspend", mo22082n = {"$this$iterator", "bufferInitialCapacity", "gap", "buffer", "skip", "e", "$this$iterator", "bufferInitialCapacity", "gap", "buffer", "skip", "$this$iterator", "bufferInitialCapacity", "gap", "buffer", "e", "$this$iterator", "bufferInitialCapacity", "gap", "buffer", "$this$iterator", "bufferInitialCapacity", "gap", "buffer"}, mo22083s = {"L$0", "I$0", "I$1", "L$1", "I$2", "L$2", "L$0", "I$0", "I$1", "L$1", "I$2", "L$0", "I$0", "I$1", "L$1", "L$2", "L$0", "I$0", "I$1", "L$1", "L$0", "I$0", "I$1", "L$1"})
/* compiled from: SlidingWindow.kt */
final class SlidingWindowKt$windowedIterator$1 extends RestrictedSuspendLambda implements Function2<SequenceScope<? super List<? extends T>>, Continuation<? super Unit>, Object> {
    final /* synthetic */ Iterator $iterator;
    final /* synthetic */ boolean $partialWindows;
    final /* synthetic */ boolean $reuseBuffer;
    final /* synthetic */ int $size;
    final /* synthetic */ int $step;
    int I$0;
    int I$1;
    int I$2;
    Object L$0;
    Object L$1;
    Object L$2;
    Object L$3;
    int label;

    /* renamed from: p$ */
    private SequenceScope f4195p$;

    SlidingWindowKt$windowedIterator$1(int i, int i2, Iterator it, boolean z, boolean z2, Continuation continuation) {
        this.$size = i;
        this.$step = i2;
        this.$iterator = it;
        this.$reuseBuffer = z;
        this.$partialWindows = z2;
        super(2, continuation);
    }

    public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
        Intrinsics.checkParameterIsNotNull(continuation, "completion");
        SlidingWindowKt$windowedIterator$1 slidingWindowKt$windowedIterator$1 = new SlidingWindowKt$windowedIterator$1(this.$size, this.$step, this.$iterator, this.$reuseBuffer, this.$partialWindows, continuation);
        slidingWindowKt$windowedIterator$1.f4195p$ = (SequenceScope) obj;
        return slidingWindowKt$windowedIterator$1;
    }

    public final Object invoke(Object obj, Object obj2) {
        return ((SlidingWindowKt$windowedIterator$1) create(obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    /*  JADX ERROR: JadxRuntimeException in pass: RegionMakerVisitor
        jadx.core.utils.exceptions.JadxRuntimeException: Regions count limit reached
        	at jadx.core.dex.visitors.regions.RegionMaker.makeRegion(RegionMaker.java:89)
        	at jadx.core.dex.visitors.regions.RegionMaker.processIf(RegionMaker.java:695)
        	at jadx.core.dex.visitors.regions.RegionMaker.traverse(RegionMaker.java:123)
        	at jadx.core.dex.visitors.regions.RegionMaker.makeRegion(RegionMaker.java:86)
        	at jadx.core.dex.visitors.regions.RegionMaker.processIf(RegionMaker.java:690)
        	at jadx.core.dex.visitors.regions.RegionMaker.traverse(RegionMaker.java:123)
        	at jadx.core.dex.visitors.regions.RegionMaker.makeRegion(RegionMaker.java:86)
        	at jadx.core.dex.visitors.regions.RegionMaker.processIf(RegionMaker.java:690)
        	at jadx.core.dex.visitors.regions.RegionMaker.traverse(RegionMaker.java:123)
        	at jadx.core.dex.visitors.regions.RegionMaker.makeRegion(RegionMaker.java:86)
        	at jadx.core.dex.visitors.regions.RegionMaker.processIf(RegionMaker.java:690)
        	at jadx.core.dex.visitors.regions.RegionMaker.traverse(RegionMaker.java:123)
        	at jadx.core.dex.visitors.regions.RegionMaker.makeRegion(RegionMaker.java:86)
        	at jadx.core.dex.visitors.regions.RegionMakerVisitor.visit(RegionMakerVisitor.java:49)
        	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:27)
        	at jadx.core.dex.visitors.DepthTraversal.lambda$visit$1(DepthTraversal.java:14)
        	at java.base/java.util.ArrayList.forEach(ArrayList.java:1540)
        	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:14)
        	at jadx.core.ProcessClass.process(ProcessClass.java:30)
        	at jadx.core.ProcessClass.lambda$processDependencies$0(ProcessClass.java:49)
        	at java.base/java.util.ArrayList.forEach(ArrayList.java:1540)
        	at jadx.core.ProcessClass.processDependencies(ProcessClass.java:49)
        	at jadx.core.ProcessClass.process(ProcessClass.java:35)
        	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:311)
        	at jadx.api.JavaClass.decompile(JavaClass.java:62)
        	at jadx.api.JadxDecompiler.lambda$appendSourcesSave$0(JadxDecompiler.java:217)
        */
    /* JADX WARNING: Removed duplicated region for block: B:45:0x011c  */
    /* JADX WARNING: Removed duplicated region for block: B:60:0x0167  */
    /* JADX WARNING: Removed duplicated region for block: B:63:0x0173  */
    /* JADX WARNING: Removed duplicated region for block: B:85:0x0163 A[SYNTHETIC] */
    public final java.lang.Object invokeSuspend(java.lang.Object r15) {
        /*
            r14 = this;
            java.lang.Object r0 = kotlin.coroutines.intrinsics.IntrinsicsKt.getCOROUTINE_SUSPENDED()
            int r1 = r14.label
            r2 = 5
            r3 = 4
            r4 = 3
            r5 = 2
            r6 = 1
            if (r1 == 0) goto L_0x007c
            if (r1 == r6) goto L_0x0061
            if (r1 == r5) goto L_0x004e
            if (r1 == r4) goto L_0x0036
            if (r1 == r3) goto L_0x0024
            if (r1 != r2) goto L_0x001c
            java.lang.Object r0 = r14.L$1
            kotlin.collections.RingBuffer r0 = (kotlin.collections.RingBuffer) r0
            goto L_0x0054
        L_0x001c:
            java.lang.IllegalStateException r15 = new java.lang.IllegalStateException
            java.lang.String r0 = "call to 'resume' before 'invoke' with coroutine"
            r15.<init>(r0)
            throw r15
        L_0x0024:
            java.lang.Object r1 = r14.L$1
            kotlin.collections.RingBuffer r1 = (kotlin.collections.RingBuffer) r1
            int r4 = r14.I$1
            int r5 = r14.I$0
            java.lang.Object r7 = r14.L$0
            kotlin.sequences.SequenceScope r7 = (kotlin.sequences.SequenceScope) r7
            kotlin.ResultKt.throwOnFailure(r15)
            r15 = r14
            goto L_0x0196
        L_0x0036:
            java.lang.Object r1 = r14.L$3
            java.util.Iterator r1 = (java.util.Iterator) r1
            java.lang.Object r5 = r14.L$2
            java.lang.Object r5 = r14.L$1
            kotlin.collections.RingBuffer r5 = (kotlin.collections.RingBuffer) r5
            int r7 = r14.I$1
            int r8 = r14.I$0
            java.lang.Object r9 = r14.L$0
            kotlin.sequences.SequenceScope r9 = (kotlin.sequences.SequenceScope) r9
            kotlin.ResultKt.throwOnFailure(r15)
            r15 = r14
            goto L_0x015d
        L_0x004e:
            int r0 = r14.I$2
            java.lang.Object r0 = r14.L$1
            java.util.ArrayList r0 = (java.util.ArrayList) r0
        L_0x0054:
            int r0 = r14.I$1
            int r0 = r14.I$0
            java.lang.Object r0 = r14.L$0
            kotlin.sequences.SequenceScope r0 = (kotlin.sequences.SequenceScope) r0
            kotlin.ResultKt.throwOnFailure(r15)
            goto L_0x01b7
        L_0x0061:
            java.lang.Object r1 = r14.L$3
            java.util.Iterator r1 = (java.util.Iterator) r1
            java.lang.Object r2 = r14.L$2
            int r2 = r14.I$2
            java.lang.Object r2 = r14.L$1
            java.util.ArrayList r2 = (java.util.ArrayList) r2
            int r3 = r14.I$1
            int r4 = r14.I$0
            java.lang.Object r7 = r14.L$0
            kotlin.sequences.SequenceScope r7 = (kotlin.sequences.SequenceScope) r7
            kotlin.ResultKt.throwOnFailure(r15)
            r15 = r14
            r8 = r0
            r0 = r3
            goto L_0x00d0
        L_0x007c:
            kotlin.ResultKt.throwOnFailure(r15)
            kotlin.sequences.SequenceScope r15 = r14.f4195p$
            int r1 = r14.$size
            r7 = 1024(0x400, float:1.435E-42)
            int r1 = kotlin.ranges.RangesKt.coerceAtMost(r1, r7)
            int r7 = r14.$step
            int r8 = r14.$size
            int r7 = r7 - r8
            if (r7 < 0) goto L_0x010a
            java.util.ArrayList r2 = new java.util.ArrayList
            r2.<init>(r1)
            r3 = 0
            java.util.Iterator r4 = r14.$iterator
            r8 = r0
            r0 = r7
            r7 = r15
            r15 = r14
            r13 = r4
            r4 = r1
            r1 = r13
        L_0x009f:
            boolean r9 = r1.hasNext()
            if (r9 == 0) goto L_0x00e1
            java.lang.Object r9 = r1.next()
            if (r3 <= 0) goto L_0x00ae
            int r3 = r3 + -1
            goto L_0x009f
        L_0x00ae:
            r2.add(r9)
            int r10 = r2.size()
            int r11 = r15.$size
            if (r10 != r11) goto L_0x009f
            r15.L$0 = r7
            r15.I$0 = r4
            r15.I$1 = r0
            r15.L$1 = r2
            r15.I$2 = r3
            r15.L$2 = r9
            r15.L$3 = r1
            r15.label = r6
            java.lang.Object r3 = r7.yield(r2, r15)
            if (r3 != r8) goto L_0x00d0
            return r8
        L_0x00d0:
            boolean r3 = r15.$reuseBuffer
            if (r3 == 0) goto L_0x00d8
            r2.clear()
            goto L_0x00df
        L_0x00d8:
            java.util.ArrayList r2 = new java.util.ArrayList
            int r3 = r15.$size
            r2.<init>(r3)
        L_0x00df:
            r3 = r0
            goto L_0x009f
        L_0x00e1:
            r1 = r2
            java.util.Collection r1 = (java.util.Collection) r1
            boolean r1 = r1.isEmpty()
            r1 = r1 ^ r6
            if (r1 == 0) goto L_0x01b7
            boolean r1 = r15.$partialWindows
            if (r1 != 0) goto L_0x00f7
            int r1 = r2.size()
            int r6 = r15.$size
            if (r1 != r6) goto L_0x01b7
        L_0x00f7:
            r15.L$0 = r7
            r15.I$0 = r4
            r15.I$1 = r0
            r15.L$1 = r2
            r15.I$2 = r3
            r15.label = r5
            java.lang.Object r15 = r7.yield(r2, r15)
            if (r15 != r8) goto L_0x01b7
            return r8
        L_0x010a:
            kotlin.collections.RingBuffer r5 = new kotlin.collections.RingBuffer
            r5.<init>(r1)
            java.util.Iterator r8 = r14.$iterator
            r9 = r15
            r15 = r14
            r13 = r8
            r8 = r1
            r1 = r13
        L_0x0116:
            boolean r10 = r1.hasNext()
            if (r10 == 0) goto L_0x0163
            java.lang.Object r10 = r1.next()
            r5.add(r10)
            boolean r11 = r5.isFull()
            if (r11 == 0) goto L_0x0116
            int r11 = r5.size()
            int r12 = r15.$size
            if (r11 >= r12) goto L_0x0136
            kotlin.collections.RingBuffer r5 = r5.expanded(r12)
            goto L_0x0116
        L_0x0136:
            boolean r11 = r15.$reuseBuffer
            if (r11 == 0) goto L_0x013e
            r11 = r5
            java.util.List r11 = (java.util.List) r11
            goto L_0x0148
        L_0x013e:
            java.util.ArrayList r11 = new java.util.ArrayList
            r12 = r5
            java.util.Collection r12 = (java.util.Collection) r12
            r11.<init>(r12)
            java.util.List r11 = (java.util.List) r11
        L_0x0148:
            r15.L$0 = r9
            r15.I$0 = r8
            r15.I$1 = r7
            r15.L$1 = r5
            r15.L$2 = r10
            r15.L$3 = r1
            r15.label = r4
            java.lang.Object r10 = r9.yield(r11, r15)
            if (r10 != r0) goto L_0x015d
            return r0
        L_0x015d:
            int r10 = r15.$step
            r5.removeFirst(r10)
            goto L_0x0116
        L_0x0163:
            boolean r1 = r15.$partialWindows
            if (r1 == 0) goto L_0x01b7
            r1 = r5
            r4 = r7
            r5 = r8
            r7 = r9
        L_0x016b:
            int r8 = r1.size()
            int r9 = r15.$step
            if (r8 <= r9) goto L_0x019c
            boolean r8 = r15.$reuseBuffer
            if (r8 == 0) goto L_0x017b
            r8 = r1
            java.util.List r8 = (java.util.List) r8
            goto L_0x0185
        L_0x017b:
            java.util.ArrayList r8 = new java.util.ArrayList
            r9 = r1
            java.util.Collection r9 = (java.util.Collection) r9
            r8.<init>(r9)
            java.util.List r8 = (java.util.List) r8
        L_0x0185:
            r15.L$0 = r7
            r15.I$0 = r5
            r15.I$1 = r4
            r15.L$1 = r1
            r15.label = r3
            java.lang.Object r8 = r7.yield(r8, r15)
            if (r8 != r0) goto L_0x0196
            return r0
        L_0x0196:
            int r8 = r15.$step
            r1.removeFirst(r8)
            goto L_0x016b
        L_0x019c:
            r3 = r1
            java.util.Collection r3 = (java.util.Collection) r3
            boolean r3 = r3.isEmpty()
            r3 = r3 ^ r6
            if (r3 == 0) goto L_0x01b7
            r15.L$0 = r7
            r15.I$0 = r5
            r15.I$1 = r4
            r15.L$1 = r1
            r15.label = r2
            java.lang.Object r15 = r7.yield(r1, r15)
            if (r15 != r0) goto L_0x01b7
            return r0
        L_0x01b7:
            kotlin.Unit r15 = kotlin.Unit.INSTANCE
            return r15
        */
        throw new UnsupportedOperationException("Method not decompiled: kotlin.collections.SlidingWindowKt$windowedIterator$1.invokeSuspend(java.lang.Object):java.lang.Object");
    }
}
