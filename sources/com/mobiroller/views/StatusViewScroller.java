package com.mobiroller.views;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.HorizontalScrollView;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

@Metadata(mo22060bv = {1, 0, 3}, mo22061d1 = {"\u0000.\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0002\b\u0003\u0018\u00002\u00020\u0001B%\b\u0007\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\n\b\u0002\u0010\u0004\u001a\u0004\u0018\u00010\u0005\u0012\b\b\u0002\u0010\u0006\u001a\u00020\u0007¢\u0006\u0002\u0010\bJ\u000e\u0010\u000f\u001a\u00020\u00102\u0006\u0010\u0011\u001a\u00020\u0007J\u000e\u0010\u0012\u001a\u00020\u00102\u0006\u0010\u0011\u001a\u00020\u0007R\u001a\u0010\t\u001a\u00020\nX\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u000b\u0010\f\"\u0004\b\r\u0010\u000e¨\u0006\u0013"}, mo22062d2 = {"Lcom/mobiroller/views/StatusViewScroller;", "Landroid/widget/HorizontalScrollView;", "context", "Landroid/content/Context;", "attrs", "Landroid/util/AttributeSet;", "defStyleAttr", "", "(Landroid/content/Context;Landroid/util/AttributeSet;I)V", "statusView", "Lcom/mobiroller/views/StatusView;", "getStatusView", "()Lcom/mobiroller/views/StatusView;", "setStatusView", "(Lcom/mobiroller/views/StatusView;)V", "scrollToStep", "", "stepCount", "smoothScrollToStep", "app_regularProductionRelease"}, mo22063k = 1, mo22064mv = {1, 1, 16})
/* compiled from: StatusView.kt */
public final class StatusViewScroller extends HorizontalScrollView {
    private StatusView statusView;

    public StatusViewScroller(Context context) {
        this(context, null, 0, 6, null);
    }

    public StatusViewScroller(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0, 4, null);
    }

    public /* synthetic */ StatusViewScroller(Context context, AttributeSet attributeSet, int i, int i2, DefaultConstructorMarker defaultConstructorMarker) {
        if ((i2 & 2) != 0) {
            attributeSet = null;
        }
        if ((i2 & 4) != 0) {
            i = 0;
        }
        this(context, attributeSet, i);
    }

    public StatusViewScroller(Context context, AttributeSet attributeSet, int i) {
        Intrinsics.checkParameterIsNotNull(context, "context");
        super(context, attributeSet, i);
        StatusView statusView2 = new StatusView(context, attributeSet, 0, 4, null);
        this.statusView = statusView2;
        addView(this.statusView);
    }

    public final StatusView getStatusView() {
        return this.statusView;
    }

    public final void setStatusView(StatusView statusView2) {
        Intrinsics.checkParameterIsNotNull(statusView2, "<set-?>");
        this.statusView = statusView2;
    }

    public final void scrollToStep(int i) {
        scrollTo(this.statusView.getScrollPosForStep(i), getScrollY());
    }

    public final void smoothScrollToStep(int i) {
        smoothScrollTo(this.statusView.getScrollPosForStep(i), getScrollY());
    }
}
