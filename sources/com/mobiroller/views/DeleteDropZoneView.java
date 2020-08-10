package com.mobiroller.views;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.LightingColorFilter;
import android.graphics.Paint;
import android.graphics.Paint.Align;
import android.graphics.Paint.Style;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.view.View;
import androidx.core.internal.view.SupportMenu;
import androidx.core.view.ViewCompat;
import com.mobiroller.mobi942763453128.R;

public class DeleteDropZoneView extends View {
    private Paint bitmapPaint;
    private Paint bitmapPaintRed;
    private Rect bounds = new Rect();
    private boolean straight = true;
    private Paint textPaintRed;
    private Paint textPaintStraight = createTextPaint();
    private Bitmap trash;

    public DeleteDropZoneView(Context context) {
        super(context);
        this.textPaintStraight.setColor(-1);
        this.textPaintRed = createTextPaint();
        this.textPaintRed.setColor(SupportMenu.CATEGORY_MASK);
        this.bitmapPaint = createBaseBitmapPaint();
        this.bitmapPaintRed = createBaseBitmapPaint();
        this.bitmapPaintRed.setColorFilter(new LightingColorFilter(SupportMenu.CATEGORY_MASK, 1));
        setBackgroundColor(ViewCompat.MEASURED_STATE_MASK);
        getBackground().setAlpha(200);
    }

    private Paint createTextPaint() {
        Paint paint = new Paint(1);
        paint.setStyle(Style.FILL);
        paint.setTextAlign(Align.CENTER);
        paint.setTypeface(Typeface.DEFAULT_BOLD);
        return paint;
    }

    private Paint createBaseBitmapPaint() {
        Paint paint = new Paint();
        paint.setAntiAlias(true);
        paint.setFilterBitmap(true);
        paint.setDither(true);
        return paint;
    }

    /* access modifiers changed from: protected */
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int measuredHeight = getMeasuredHeight();
        int measuredWidth = getMeasuredWidth();
        String string = getResources().getString(R.string.remove);
        initTrashIcon();
        try {
            this.textPaintStraight.getTextBounds(string, 0, 6, this.bounds);
        } catch (Exception e) {
            e.printStackTrace();
        }
        int i = (measuredHeight * 3) / 4;
        if (this.straight) {
            this.textPaintStraight.setTextSize((float) i);
            int i2 = measuredWidth / 2;
            canvas.drawText(string, (float) ((this.trash.getWidth() / 2) + i2 + 5), (float) (measuredHeight - ((measuredHeight - this.bounds.height()) / 2)), this.textPaintStraight);
            canvas.drawBitmap(this.trash, (float) (((i2 - (this.bounds.width() / 2)) - (this.trash.getWidth() / 2)) - 10), 0.0f, this.bitmapPaint);
            return;
        }
        this.textPaintRed.setTextSize((float) i);
        int i3 = measuredWidth / 2;
        canvas.drawText(string, (float) ((this.trash.getWidth() / 2) + i3 + 5), (float) (measuredHeight - ((measuredHeight - this.bounds.height()) / 2)), this.textPaintRed);
        canvas.drawBitmap(this.trash, (float) (((i3 - (this.bounds.width() / 2)) - (this.trash.getWidth() / 2)) - 10), 0.0f, this.bitmapPaintRed);
    }

    private void initTrashIcon() {
        if (this.trash == null) {
            this.trash = getImage(R.drawable.content_discard, getMeasuredHeight(), getMeasuredHeight());
        }
    }

    public void highlight() {
        this.straight = false;
        invalidate();
    }

    public void smother() {
        this.straight = true;
        invalidate();
    }

    private Bitmap getImage(int i, int i2, int i3) {
        Bitmap decodeResource = BitmapFactory.decodeResource(getResources(), i);
        Bitmap createScaledBitmap = Bitmap.createScaledBitmap(decodeResource, i2, i3, true);
        if (decodeResource != null && !isInEditMode()) {
            decodeResource.recycle();
        }
        invalidate();
        return createScaledBitmap;
    }
}
