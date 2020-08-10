package com.google.android.material.imageview;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Outline;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Path;
import android.graphics.Path.Direction;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.os.Build.VERSION;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewOutlineProvider;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.appcompat.widget.AppCompatImageView;
import com.google.android.material.C2556R;
import com.google.android.material.resources.MaterialResources;
import com.google.android.material.shape.ShapeAppearanceModel;
import com.google.android.material.shape.ShapeAppearancePathProvider;
import com.google.android.material.shape.Shapeable;
import com.google.android.material.theme.overlay.MaterialThemeOverlay;

public class ShapeableImageView extends AppCompatImageView implements Shapeable {
    private static final int DEF_STYLE_RES = C2556R.C2562style.Widget_MaterialComponents_ShapeableImageView;
    private final Paint borderPaint;
    private final Paint clearPaint;
    /* access modifiers changed from: private */
    public final RectF destination;
    private Path maskPath;
    private final RectF maskRect;
    private final Path path;
    private final ShapeAppearancePathProvider pathProvider;
    /* access modifiers changed from: private */
    public ShapeAppearanceModel shapeAppearanceModel;
    private ColorStateList strokeColor;
    private int strokeWidth;

    class OutlineProvider extends ViewOutlineProvider {
        private Rect rect = new Rect();

        OutlineProvider() {
        }

        public void getOutline(View view, Outline outline) {
            if (ShapeableImageView.this.shapeAppearanceModel != null && ShapeableImageView.this.shapeAppearanceModel.isRoundRect(ShapeableImageView.this.destination)) {
                ShapeableImageView.this.destination.round(this.rect);
                outline.setRoundRect(this.rect, ShapeableImageView.this.shapeAppearanceModel.getBottomLeftCornerSize().getCornerSize(ShapeableImageView.this.destination));
            }
        }
    }

    public ShapeableImageView(Context context) {
        this(context, null, 0);
    }

    public ShapeableImageView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public ShapeableImageView(Context context, AttributeSet attributeSet, int i) {
        super(MaterialThemeOverlay.wrap(context, attributeSet, i, DEF_STYLE_RES), attributeSet, i);
        this.pathProvider = new ShapeAppearancePathProvider();
        this.path = new Path();
        Context context2 = getContext();
        this.clearPaint = new Paint();
        this.clearPaint.setAntiAlias(true);
        this.clearPaint.setColor(-1);
        this.clearPaint.setXfermode(new PorterDuffXfermode(Mode.DST_OUT));
        this.destination = new RectF();
        this.maskRect = new RectF();
        this.maskPath = new Path();
        TypedArray obtainStyledAttributes = context2.obtainStyledAttributes(attributeSet, C2556R.styleable.ShapeableImageView, i, DEF_STYLE_RES);
        this.strokeColor = MaterialResources.getColorStateList(context2, obtainStyledAttributes, C2556R.styleable.ShapeableImageView_strokeColor);
        this.strokeWidth = obtainStyledAttributes.getDimensionPixelSize(C2556R.styleable.ShapeableImageView_strokeWidth, 0);
        this.borderPaint = new Paint();
        this.borderPaint.setStyle(Style.STROKE);
        this.borderPaint.setAntiAlias(true);
        this.shapeAppearanceModel = ShapeAppearanceModel.builder(context2, attributeSet, i, DEF_STYLE_RES).build();
        if (VERSION.SDK_INT >= 21) {
            setOutlineProvider(new OutlineProvider());
        }
    }

    /* access modifiers changed from: protected */
    public void onDetachedFromWindow() {
        setLayerType(0, null);
        super.onDetachedFromWindow();
    }

    /* access modifiers changed from: protected */
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        setLayerType(2, null);
    }

    /* access modifiers changed from: protected */
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawPath(this.maskPath, this.clearPaint);
        drawStroke(canvas);
    }

    /* access modifiers changed from: protected */
    public void onMeasure(int i, int i2) {
        super.onMeasure(i, i2);
        this.destination.set((float) getPaddingLeft(), (float) getPaddingTop(), (float) (getMeasuredWidth() - getPaddingRight()), (float) (getMeasuredHeight() - getPaddingBottom()));
        this.pathProvider.calculatePath(this.shapeAppearanceModel, 1.0f, this.destination, this.path);
        this.maskPath.rewind();
        this.maskPath.addPath(this.path);
        this.maskRect.set(0.0f, 0.0f, (float) getMeasuredWidth(), (float) getMeasuredHeight());
        this.maskPath.addRect(this.maskRect, Direction.CCW);
    }

    private void drawStroke(Canvas canvas) {
        this.borderPaint.setStrokeWidth((float) this.strokeWidth);
        int colorForState = this.strokeColor.getColorForState(getDrawableState(), this.strokeColor.getDefaultColor());
        if (this.strokeWidth > 0 && colorForState != 0) {
            this.borderPaint.setColor(colorForState);
            canvas.drawPath(this.path, this.borderPaint);
        }
    }

    public void setShapeAppearanceModel(ShapeAppearanceModel shapeAppearanceModel2) {
        this.shapeAppearanceModel = shapeAppearanceModel2;
        requestLayout();
    }

    public ShapeAppearanceModel getShapeAppearanceModel() {
        return this.shapeAppearanceModel;
    }

    public void setStrokeColorResource(int i) {
        setStrokeColor(AppCompatResources.getColorStateList(getContext(), i));
    }

    public ColorStateList getStrokeColor() {
        return this.strokeColor;
    }

    public void setStrokeWidth(int i) {
        if (this.strokeWidth != i) {
            this.strokeWidth = i;
            invalidate();
        }
    }

    public void setStrokeWidthResource(int i) {
        setStrokeWidth(getResources().getDimensionPixelSize(i));
    }

    public int getStrokeWidth() {
        return this.strokeWidth;
    }

    public void setStrokeColor(ColorStateList colorStateList) {
        this.strokeColor = colorStateList;
        invalidate();
    }
}
