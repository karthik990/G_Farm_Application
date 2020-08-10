package com.mobiroller.views;

import androidx.core.view.ViewCompat;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlin.properties.ObservableProperty;
import kotlin.reflect.KProperty;

@Metadata(mo22060bv = {1, 0, 3}, mo22061d1 = {"\u0000%\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\b\u0004\n\u0002\b\u0004\n\u0002\b\u0004*\u0001\u0000\b\n\u0018\u00002\b\u0012\u0004\u0012\u00028\u00000\u0001J)\u0010\u0002\u001a\u00020\u00032\n\u0010\u0004\u001a\u0006\u0012\u0002\b\u00030\u00052\u0006\u0010\u0006\u001a\u00028\u00002\u0006\u0010\u0007\u001a\u00028\u0000H\u0014¢\u0006\u0002\u0010\b¨\u0006\t¸\u0006\u0000"}, mo22062d2 = {"kotlin/properties/Delegates$observable$1", "Lkotlin/properties/ObservableProperty;", "afterChange", "", "property", "Lkotlin/reflect/KProperty;", "oldValue", "newValue", "(Lkotlin/reflect/KProperty;Ljava/lang/Object;Ljava/lang/Object;)V", "kotlin-stdlib"}, mo22063k = 1, mo22064mv = {1, 1, 16})
/* compiled from: Delegates.kt */
public final class StatusView$$special$$inlined$observable$1 extends ObservableProperty<Integer> {
    final /* synthetic */ Object $initialValue;
    final /* synthetic */ StatusView this$0;

    public StatusView$$special$$inlined$observable$1(Object obj, Object obj2, StatusView statusView) {
        this.$initialValue = obj;
        this.this$0 = statusView;
        super(obj2);
    }

    /* access modifiers changed from: protected */
    public void afterChange(KProperty<?> kProperty, Integer num, Integer num2) {
        Intrinsics.checkParameterIsNotNull(kProperty, "property");
        int intValue = num2.intValue();
        int intValue2 = num.intValue();
        if (ViewCompat.isLaidOut(this.this$0)) {
            this.this$0.initCirclePaints();
            boolean access$containsFlag = this.this$0.containsFlag(intValue2, 2);
            boolean access$containsFlag2 = this.this$0.containsFlag(intValue, 2);
            if ((!access$containsFlag || access$containsFlag2) && (access$containsFlag || !access$containsFlag2)) {
                this.this$0.setDrawingDimensions();
                this.this$0.invalidate();
                return;
            }
            this.this$0.requestLayout();
        }
    }
}
