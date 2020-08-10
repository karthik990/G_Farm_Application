package com.nightonke.boommenu;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.RectF;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build.VERSION;
import android.util.AttributeSet;
import android.widget.FrameLayout;

public class BMBShadow extends FrameLayout {
    private int shadowColor;
    private int shadowCornerRadius;
    private boolean shadowEffect = true;
    private int shadowOffsetX;
    private int shadowOffsetY;
    private int shadowRadius;

    public BMBShadow(Context context) {
        super(context);
    }

    public BMBShadow(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    public BMBShadow(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
    }

    private void initPadding() {
        int abs = this.shadowRadius + Math.abs(this.shadowOffsetX);
        int abs2 = this.shadowRadius + Math.abs(this.shadowOffsetY);
        setPadding(abs, abs2, abs, abs2);
    }

    /* access modifiers changed from: protected */
    public void onSizeChanged(int i, int i2, int i3, int i4) {
        super.onSizeChanged(i, i2, i3, i4);
        if (i > 0 && i2 > 0) {
            createShadow();
        }
    }

    /* access modifiers changed from: protected */
    public void onLayout(boolean z, int i, int i2, int i3, int i4) {
        super.onLayout(z, i, i2, i3, i4);
        createShadow();
    }

    private void createShadow() {
        if (this.shadowEffect) {
            Bitmap createShadowBitmap = createShadowBitmap();
            if (createShadowBitmap != null) {
                BitmapDrawable bitmapDrawable = new BitmapDrawable(getResources(), createShadowBitmap);
                if (VERSION.SDK_INT <= 16) {
                    setBackgroundDrawable(bitmapDrawable);
                } else {
                    setBackground(bitmapDrawable);
                }
            }
        } else {
            clearShadow();
        }
    }

    private Bitmap createShadowBitmap() {
        if (getWidth() <= 0 || getHeight() <= 0) {
            return null;
        }
        Bitmap createBitmap = Bitmap.createBitmap(getWidth(), getHeight(), Config.ALPHA_8);
        Canvas canvas = new Canvas(createBitmap);
        RectF rectF = new RectF((float) (this.shadowRadius + Math.abs(this.shadowOffsetX)), (float) (this.shadowRadius + Math.abs(this.shadowOffsetY)), (float) ((getWidth() - this.shadowRadius) - Math.abs(this.shadowOffsetX)), (float) ((getHeight() - this.shadowRadius) - Math.abs(this.shadowOffsetY)));
        Paint paint = new Paint();
        paint.setAntiAlias(true);
        paint.setColor(0);
        paint.setStyle(Style.FILL);
        if (!isInEditMode()) {
            paint.setShadowLayer((float) this.shadowRadius, (float) this.shadowOffsetX, (float) this.shadowOffsetY, this.shadowColor);
        }
        int i = this.shadowCornerRadius;
        canvas.drawRoundRect(rectF, (float) i, (float) i, paint);
        return createBitmap;
    }

    public void setShadowOffsetX(int i) {
        this.shadowOffsetX = i;
        initPadding();
    }

    public void setShadowOffsetY(int i) {
        this.shadowOffsetY = i;
        initPadding();
    }

    public void setShadowRadius(int i) {
        this.shadowRadius = i;
        initPadding();
    }

    public void setShadowCornerRadius(int i) {
        this.shadowCornerRadius = i;
        initPadding();
    }

    public void setShadowColor(int i) {
        this.shadowColor = i;
    }

    public void setShadowEffect(boolean z) {
        this.shadowEffect = z;
    }

    public void clearShadow() {
        Util.setDrawable(this, null);
    }
}
