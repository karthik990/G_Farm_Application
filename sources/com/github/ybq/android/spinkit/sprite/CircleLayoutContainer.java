package com.github.ybq.android.spinkit.sprite;

import android.graphics.Canvas;
import android.graphics.Rect;

public abstract class CircleLayoutContainer extends SpriteContainer {
    public void drawChild(Canvas canvas) {
        for (int i = 0; i < getChildCount(); i++) {
            Sprite childAt = getChildAt(i);
            int save = canvas.save();
            canvas.rotate((float) ((i * 360) / getChildCount()), (float) getBounds().centerX(), (float) getBounds().centerY());
            childAt.draw(canvas);
            canvas.restoreToCount(save);
        }
    }

    /* access modifiers changed from: protected */
    public void onBoundsChange(Rect rect) {
        super.onBoundsChange(rect);
        Rect clipSquare = clipSquare(rect);
        double width = (double) clipSquare.width();
        Double.isNaN(width);
        double d = (width * 3.141592653589793d) / 3.5999999046325684d;
        double childCount = (double) getChildCount();
        Double.isNaN(childCount);
        int i = (int) (d / childCount);
        int centerX = clipSquare.centerX() - i;
        int centerX2 = clipSquare.centerX() + i;
        for (int i2 = 0; i2 < getChildCount(); i2++) {
            getChildAt(i2).setDrawBounds(centerX, clipSquare.top, centerX2, clipSquare.top + (i * 2));
        }
    }
}
