package com.nightonke.boommenu.Piece;

import android.content.Context;
import android.graphics.RectF;
import android.view.View;
import android.widget.FrameLayout.LayoutParams;

public abstract class BoomPiece extends View {
    private boolean requestLayoutNotFinish = false;

    public abstract void init(int i, float f);

    public abstract void setColor(int i);

    public abstract void setColorRes(int i);

    public BoomPiece(Context context) {
        super(context);
    }

    public void place(RectF rectF) {
        LayoutParams layoutParams = (LayoutParams) getLayoutParams();
        if (layoutParams != null) {
            layoutParams.leftMargin = (int) rectF.left;
            layoutParams.topMargin = (int) rectF.top;
            layoutParams.width = (int) rectF.right;
            layoutParams.height = (int) rectF.bottom;
            setLayoutParams(layoutParams);
        }
    }

    public void requestLayout() {
        if (!this.requestLayoutNotFinish) {
            this.requestLayoutNotFinish = true;
            super.requestLayout();
        }
    }

    /* access modifiers changed from: protected */
    public void onLayout(boolean z, int i, int i2, int i3, int i4) {
        super.onLayout(z, i, i2, i3, i4);
        this.requestLayoutNotFinish = false;
    }
}
