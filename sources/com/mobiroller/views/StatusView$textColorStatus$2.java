package com.mobiroller.views;

import android.text.StaticLayout;
import android.text.TextPaint;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Lambda;

@Metadata(mo22060bv = {1, 0, 3}, mo22061d1 = {"\u0000\b\n\u0000\n\u0002\u0010\u0002\n\u0000\u0010\u0000\u001a\u00020\u0001H\n¢\u0006\u0002\b\u0002"}, mo22062d2 = {"<anonymous>", "", "invoke"}, mo22063k = 3, mo22064mv = {1, 1, 16})
/* compiled from: StatusView.kt */
final class StatusView$textColorStatus$2 extends Lambda implements Function0<Unit> {
    final /* synthetic */ StatusView this$0;

    StatusView$textColorStatus$2(StatusView statusView) {
        this.this$0 = statusView;
        super(0);
    }

    public final void invoke() {
        StatusView.access$getMTextPaintStatus$p(this.this$0).setColor(this.this$0.getTextColorStatus());
        int i = 0;
        for (Object next : this.this$0.getStatusData()) {
            int i2 = i + 1;
            if (i < 0) {
                CollectionsKt.throwIndexOverflow();
            }
            StatusInfo statusInfo = (StatusInfo) next;
            if (i <= this.this$0.currentCountIndex()) {
                StaticLayout staticLayout = statusInfo.getStaticLayout();
                if (staticLayout != null) {
                    TextPaint paint = staticLayout.getPaint();
                    if (paint != null) {
                        paint.setColor(this.this$0.getTextColorStatus());
                    }
                }
            } else {
                StaticLayout staticLayout2 = statusInfo.getStaticLayout();
                if (staticLayout2 != null) {
                    TextPaint paint2 = staticLayout2.getPaint();
                    if (paint2 != null) {
                        paint2.setColor(this.this$0.getTextColorStatusIncomplete());
                    }
                }
            }
            i = i2;
        }
    }
}
