package com.ogaclejapan.smarttablayout;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;
import android.widget.LinearLayout;
import com.ogaclejapan.smarttablayout.SmartTabLayout.TabColorizer;

class SmartTabStrip extends LinearLayout {
    private static final int AUTO_WIDTH = -1;
    private static final byte DEFAULT_BOTTOM_BORDER_COLOR_ALPHA = 38;
    private static final int DEFAULT_BOTTOM_BORDER_THICKNESS_DIPS = 2;
    private static final byte DEFAULT_DIVIDER_COLOR_ALPHA = 32;
    private static final float DEFAULT_DIVIDER_HEIGHT = 0.5f;
    private static final int DEFAULT_DIVIDER_THICKNESS_DIPS = 1;
    private static final boolean DEFAULT_DRAW_DECORATION_AFTER_TAB = false;
    private static final float DEFAULT_INDICATOR_CORNER_RADIUS = 0.0f;
    private static final int DEFAULT_INDICATOR_GRAVITY = 0;
    private static final boolean DEFAULT_INDICATOR_IN_CENTER = false;
    private static final boolean DEFAULT_INDICATOR_IN_FRONT = false;
    private static final boolean DEFAULT_INDICATOR_WITHOUT_PADDING = false;
    private static final int DEFAULT_SELECTED_INDICATOR_COLOR = -13388315;
    private static final byte DEFAULT_TOP_BORDER_COLOR_ALPHA = 38;
    private static final int DEFAULT_TOP_BORDER_THICKNESS_DIPS = 0;
    private static final int GRAVITY_BOTTOM = 0;
    private static final int GRAVITY_CENTER = 2;
    private static final int GRAVITY_TOP = 1;
    private static final int SELECTED_INDICATOR_THICKNESS_DIPS = 8;
    private final Paint borderPaint;
    private final int bottomBorderColor;
    private final int bottomBorderThickness;
    private TabColorizer customTabColorizer;
    private final SimpleTabColorizer defaultTabColorizer;
    private final float dividerHeight;
    private final Paint dividerPaint;
    private final int dividerThickness;
    private final boolean drawDecorationAfterTab;
    private SmartTabIndicationInterpolator indicationInterpolator;
    private final boolean indicatorAlwaysInCenter;
    private final float indicatorCornerRadius;
    private final int indicatorGravity;
    private final boolean indicatorInFront;
    private final Paint indicatorPaint;
    private final RectF indicatorRectF = new RectF();
    private final int indicatorThickness;
    private final int indicatorWidth;
    private final boolean indicatorWithoutPadding;
    private int lastPosition;
    private int selectedPosition;
    private float selectionOffset;
    private final int topBorderColor;
    private final int topBorderThickness;

    private static class SimpleTabColorizer implements TabColorizer {
        private int[] dividerColors;
        private int[] indicatorColors;

        private SimpleTabColorizer() {
        }

        public final int getIndicatorColor(int i) {
            int[] iArr = this.indicatorColors;
            return iArr[i % iArr.length];
        }

        public final int getDividerColor(int i) {
            int[] iArr = this.dividerColors;
            return iArr[i % iArr.length];
        }

        /* access modifiers changed from: 0000 */
        public void setIndicatorColors(int... iArr) {
            this.indicatorColors = iArr;
        }

        /* access modifiers changed from: 0000 */
        public void setDividerColors(int... iArr) {
            this.dividerColors = iArr;
        }
    }

    SmartTabStrip(Context context, AttributeSet attributeSet) {
        int[] iArr;
        int i;
        int[] iArr2;
        super(context);
        setWillNotDraw(false);
        float f = getResources().getDisplayMetrics().density;
        TypedValue typedValue = new TypedValue();
        context.getTheme().resolveAttribute(16842800, typedValue, true);
        int i2 = typedValue.data;
        int i3 = (int) (8.0f * f);
        float f2 = 0.0f * f;
        int colorAlpha = setColorAlpha(i2, 38);
        int i4 = (int) f2;
        int colorAlpha2 = setColorAlpha(i2, 38);
        int i5 = (int) (2.0f * f);
        int colorAlpha3 = setColorAlpha(i2, 32);
        int i6 = (int) (f * 1.0f);
        TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, C4556R.styleable.stl_SmartTabLayout);
        boolean z = obtainStyledAttributes.getBoolean(C4556R.styleable.stl_SmartTabLayout_stl_indicatorAlwaysInCenter, false);
        boolean z2 = obtainStyledAttributes.getBoolean(C4556R.styleable.stl_SmartTabLayout_stl_indicatorWithoutPadding, false);
        boolean z3 = obtainStyledAttributes.getBoolean(C4556R.styleable.stl_SmartTabLayout_stl_indicatorInFront, false);
        int i7 = obtainStyledAttributes.getInt(C4556R.styleable.stl_SmartTabLayout_stl_indicatorInterpolation, 0);
        int i8 = obtainStyledAttributes.getInt(C4556R.styleable.stl_SmartTabLayout_stl_indicatorGravity, 0);
        int i9 = i7;
        int color = obtainStyledAttributes.getColor(C4556R.styleable.stl_SmartTabLayout_stl_indicatorColor, DEFAULT_SELECTED_INDICATOR_COLOR);
        int i10 = i8;
        int resourceId = obtainStyledAttributes.getResourceId(C4556R.styleable.stl_SmartTabLayout_stl_indicatorColors, -1);
        int dimensionPixelSize = obtainStyledAttributes.getDimensionPixelSize(C4556R.styleable.stl_SmartTabLayout_stl_indicatorThickness, i3);
        int layoutDimension = obtainStyledAttributes.getLayoutDimension(C4556R.styleable.stl_SmartTabLayout_stl_indicatorWidth, -1);
        float dimension = obtainStyledAttributes.getDimension(C4556R.styleable.stl_SmartTabLayout_stl_indicatorCornerRadius, f2);
        int color2 = obtainStyledAttributes.getColor(C4556R.styleable.stl_SmartTabLayout_stl_overlineColor, colorAlpha);
        int dimensionPixelSize2 = obtainStyledAttributes.getDimensionPixelSize(C4556R.styleable.stl_SmartTabLayout_stl_overlineThickness, i4);
        int color3 = obtainStyledAttributes.getColor(C4556R.styleable.stl_SmartTabLayout_stl_underlineColor, colorAlpha2);
        int dimensionPixelSize3 = obtainStyledAttributes.getDimensionPixelSize(C4556R.styleable.stl_SmartTabLayout_stl_underlineThickness, i5);
        int color4 = obtainStyledAttributes.getColor(C4556R.styleable.stl_SmartTabLayout_stl_dividerColor, colorAlpha3);
        float f3 = dimension;
        int resourceId2 = obtainStyledAttributes.getResourceId(C4556R.styleable.stl_SmartTabLayout_stl_dividerColors, -1);
        int dimensionPixelSize4 = obtainStyledAttributes.getDimensionPixelSize(C4556R.styleable.stl_SmartTabLayout_stl_dividerThickness, i6);
        boolean z4 = obtainStyledAttributes.getBoolean(C4556R.styleable.stl_SmartTabLayout_stl_drawDecorationAfterTab, false);
        obtainStyledAttributes.recycle();
        if (resourceId == -1) {
            i = 1;
            iArr = new int[]{color};
        } else {
            i = 1;
            iArr = getResources().getIntArray(resourceId);
        }
        if (resourceId2 == -1) {
            iArr2 = new int[i];
            iArr2[0] = color4;
        } else {
            iArr2 = getResources().getIntArray(resourceId2);
        }
        this.defaultTabColorizer = new SimpleTabColorizer();
        this.defaultTabColorizer.setIndicatorColors(iArr);
        this.defaultTabColorizer.setDividerColors(iArr2);
        this.topBorderThickness = dimensionPixelSize2;
        this.topBorderColor = color2;
        this.bottomBorderThickness = dimensionPixelSize3;
        this.bottomBorderColor = color3;
        this.borderPaint = new Paint(1);
        this.indicatorAlwaysInCenter = z;
        this.indicatorWithoutPadding = z2;
        this.indicatorInFront = z3;
        this.indicatorThickness = dimensionPixelSize;
        this.indicatorWidth = layoutDimension;
        this.indicatorPaint = new Paint(1);
        this.indicatorCornerRadius = f3;
        this.indicatorGravity = i10;
        this.dividerHeight = 0.5f;
        this.dividerPaint = new Paint(1);
        int i11 = dimensionPixelSize4;
        this.dividerPaint.setStrokeWidth((float) i11);
        this.dividerThickness = i11;
        this.drawDecorationAfterTab = z4;
        this.indicationInterpolator = SmartTabIndicationInterpolator.m2039of(i9);
    }

    private static int setColorAlpha(int i, byte b) {
        return Color.argb(b, Color.red(i), Color.green(i), Color.blue(i));
    }

    private static int blendColors(int i, int i2, float f) {
        float f2 = 1.0f - f;
        return Color.rgb((int) ((((float) Color.red(i)) * f) + (((float) Color.red(i2)) * f2)), (int) ((((float) Color.green(i)) * f) + (((float) Color.green(i2)) * f2)), (int) ((((float) Color.blue(i)) * f) + (((float) Color.blue(i2)) * f2)));
    }

    /* access modifiers changed from: 0000 */
    public void setIndicationInterpolator(SmartTabIndicationInterpolator smartTabIndicationInterpolator) {
        this.indicationInterpolator = smartTabIndicationInterpolator;
        invalidate();
    }

    /* access modifiers changed from: 0000 */
    public void setCustomTabColorizer(TabColorizer tabColorizer) {
        this.customTabColorizer = tabColorizer;
        invalidate();
    }

    /* access modifiers changed from: 0000 */
    public void setSelectedIndicatorColors(int... iArr) {
        this.customTabColorizer = null;
        this.defaultTabColorizer.setIndicatorColors(iArr);
        invalidate();
    }

    /* access modifiers changed from: 0000 */
    public void setDividerColors(int... iArr) {
        this.customTabColorizer = null;
        this.defaultTabColorizer.setDividerColors(iArr);
        invalidate();
    }

    /* access modifiers changed from: 0000 */
    public void onViewPagerPageChanged(int i, float f) {
        this.selectedPosition = i;
        this.selectionOffset = f;
        if (f == 0.0f) {
            int i2 = this.lastPosition;
            int i3 = this.selectedPosition;
            if (i2 != i3) {
                this.lastPosition = i3;
            }
        }
        invalidate();
    }

    /* access modifiers changed from: 0000 */
    public boolean isIndicatorAlwaysInCenter() {
        return this.indicatorAlwaysInCenter;
    }

    /* access modifiers changed from: 0000 */
    public TabColorizer getTabColorizer() {
        TabColorizer tabColorizer = this.customTabColorizer;
        return tabColorizer != null ? tabColorizer : this.defaultTabColorizer;
    }

    /* access modifiers changed from: protected */
    public void onDraw(Canvas canvas) {
        if (!this.drawDecorationAfterTab) {
            drawDecoration(canvas);
        }
    }

    /* access modifiers changed from: protected */
    public void dispatchDraw(Canvas canvas) {
        super.dispatchDraw(canvas);
        if (this.drawDecorationAfterTab) {
            drawDecoration(canvas);
        }
    }

    private void drawDecoration(Canvas canvas) {
        int i;
        int i2;
        Canvas canvas2 = canvas;
        int height = getHeight();
        int width = getWidth();
        int childCount = getChildCount();
        TabColorizer tabColorizer = getTabColorizer();
        boolean isLayoutRtl = C4560Utils.isLayoutRtl(this);
        if (this.indicatorInFront) {
            drawOverline(canvas2, 0, width);
            drawUnderline(canvas2, 0, width, height);
        }
        if (childCount > 0) {
            View childAt = getChildAt(this.selectedPosition);
            int start = C4560Utils.getStart(childAt, this.indicatorWithoutPadding);
            int end = C4560Utils.getEnd(childAt, this.indicatorWithoutPadding);
            if (!isLayoutRtl) {
                int i3 = start;
                start = end;
                end = i3;
            }
            int indicatorColor = tabColorizer.getIndicatorColor(this.selectedPosition);
            float f = (float) this.indicatorThickness;
            if (this.selectionOffset > 0.0f && this.selectedPosition < getChildCount() - 1) {
                int indicatorColor2 = tabColorizer.getIndicatorColor(this.selectedPosition + 1);
                if (indicatorColor != indicatorColor2) {
                    indicatorColor = blendColors(indicatorColor2, indicatorColor, this.selectionOffset);
                }
                float leftEdge = this.indicationInterpolator.getLeftEdge(this.selectionOffset);
                float rightEdge = this.indicationInterpolator.getRightEdge(this.selectionOffset);
                float thickness = this.indicationInterpolator.getThickness(this.selectionOffset);
                View childAt2 = getChildAt(this.selectedPosition + 1);
                int start2 = C4560Utils.getStart(childAt2, this.indicatorWithoutPadding);
                int end2 = C4560Utils.getEnd(childAt2, this.indicatorWithoutPadding);
                if (isLayoutRtl) {
                    i = (int) ((((float) end2) * rightEdge) + ((1.0f - rightEdge) * ((float) end)));
                    i2 = (int) ((((float) start2) * leftEdge) + ((1.0f - leftEdge) * ((float) start)));
                } else {
                    i = (int) ((((float) start2) * leftEdge) + ((1.0f - leftEdge) * ((float) end)));
                    i2 = (int) ((((float) end2) * rightEdge) + ((1.0f - rightEdge) * ((float) start)));
                }
                f *= thickness;
                start = i2;
                end = i;
            }
            drawIndicator(canvas, end, start, height, f, indicatorColor);
        }
        if (!this.indicatorInFront) {
            drawOverline(canvas2, 0, width);
            drawUnderline(canvas2, 0, getWidth(), height);
        }
        drawSeparator(canvas2, height, childCount);
    }

    private void drawSeparator(Canvas canvas, int i, int i2) {
        int i3 = i;
        if (this.dividerThickness > 0) {
            int min = (int) (Math.min(Math.max(0.0f, this.dividerHeight), 1.0f) * ((float) i3));
            TabColorizer tabColorizer = getTabColorizer();
            int i4 = (i3 - min) / 2;
            int i5 = min + i4;
            boolean isLayoutRtl = C4560Utils.isLayoutRtl(this);
            for (int i6 = 0; i6 < i2 - 1; i6++) {
                View childAt = getChildAt(i6);
                int end = C4560Utils.getEnd(childAt);
                int marginEnd = C4560Utils.getMarginEnd(childAt);
                int i7 = isLayoutRtl ? end - marginEnd : end + marginEnd;
                this.dividerPaint.setColor(tabColorizer.getDividerColor(i6));
                float f = (float) i7;
                canvas.drawLine(f, (float) i4, f, (float) i5, this.dividerPaint);
            }
        }
    }

    private void drawIndicator(Canvas canvas, int i, int i2, int i3, float f, int i4) {
        float f2;
        float f3;
        int i5 = this.indicatorThickness;
        if (i5 > 0 && this.indicatorWidth != 0) {
            int i6 = this.indicatorGravity;
            if (i6 == 1) {
                f2 = (float) i5;
                f3 = f2 / 2.0f;
            } else if (i6 != 2) {
                f3 = ((float) i3) - (((float) i5) / 2.0f);
            } else {
                f2 = (float) i3;
                f3 = f2 / 2.0f;
            }
            float f4 = f / 2.0f;
            float f5 = f3 - f4;
            float f6 = f3 + f4;
            this.indicatorPaint.setColor(i4);
            if (this.indicatorWidth == -1) {
                this.indicatorRectF.set((float) i, f5, (float) i2, f6);
            } else {
                float abs = ((float) (Math.abs(i - i2) - this.indicatorWidth)) / 2.0f;
                this.indicatorRectF.set(((float) i) + abs, f5, ((float) i2) - abs, f6);
            }
            float f7 = this.indicatorCornerRadius;
            if (f7 > 0.0f) {
                canvas.drawRoundRect(this.indicatorRectF, f7, f7, this.indicatorPaint);
            } else {
                canvas.drawRect(this.indicatorRectF, this.indicatorPaint);
            }
        }
    }

    private void drawOverline(Canvas canvas, int i, int i2) {
        if (this.topBorderThickness > 0) {
            this.borderPaint.setColor(this.topBorderColor);
            canvas.drawRect((float) i, 0.0f, (float) i2, (float) this.topBorderThickness, this.borderPaint);
        }
    }

    private void drawUnderline(Canvas canvas, int i, int i2, int i3) {
        if (this.bottomBorderThickness > 0) {
            this.borderPaint.setColor(this.bottomBorderColor);
            canvas.drawRect((float) i, (float) (i3 - this.bottomBorderThickness), (float) i2, (float) i3, this.borderPaint);
        }
    }
}
