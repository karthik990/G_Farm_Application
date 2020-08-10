package com.facebook.shimmer;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.RectF;
import android.util.AttributeSet;
import androidx.core.view.ViewCompat;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

public class Shimmer {
    private static final int COMPONENT_COUNT = 4;
    boolean alphaShimmer = true;
    long animationDuration = 1000;
    boolean autoStart = true;
    int baseColor = 1291845631;
    final RectF bounds = new RectF();
    boolean clipToChildren = true;
    final int[] colors = new int[4];
    int direction = 0;
    float dropoff = 0.5f;
    int fixedHeight = 0;
    int fixedWidth = 0;
    float heightRatio = 1.0f;
    int highlightColor = -1;
    float intensity = 0.0f;
    final float[] positions = new float[4];
    int repeatCount = -1;
    long repeatDelay;
    int repeatMode = 1;
    int shape = 0;
    float tilt = 20.0f;
    float widthRatio = 1.0f;

    public static class AlphaHighlightBuilder extends Builder<AlphaHighlightBuilder> {
        /* access modifiers changed from: protected */
        public AlphaHighlightBuilder getThis() {
            return this;
        }
    }

    public static abstract class Builder<T extends Builder<T>> {
        final Shimmer mShimmer = new Shimmer();

        /* access modifiers changed from: protected */
        public abstract T getThis();

        public T consumeAttributes(Context context, AttributeSet attributeSet) {
            return consumeAttributes(context.obtainStyledAttributes(attributeSet, C1306R.styleable.ShimmerFrameLayout, 0, 0));
        }

        /* access modifiers changed from: 0000 */
        public T consumeAttributes(TypedArray typedArray) {
            if (typedArray.hasValue(C1306R.styleable.ShimmerFrameLayout_shimmer_clip_to_children)) {
                setClipToChildren(typedArray.getBoolean(C1306R.styleable.ShimmerFrameLayout_shimmer_clip_to_children, this.mShimmer.clipToChildren));
            }
            if (typedArray.hasValue(C1306R.styleable.ShimmerFrameLayout_shimmer_auto_start)) {
                setAutoStart(typedArray.getBoolean(C1306R.styleable.ShimmerFrameLayout_shimmer_auto_start, this.mShimmer.autoStart));
            }
            if (typedArray.hasValue(C1306R.styleable.ShimmerFrameLayout_shimmer_base_alpha)) {
                setBaseAlpha(typedArray.getFloat(C1306R.styleable.ShimmerFrameLayout_shimmer_base_alpha, 0.3f));
            }
            if (typedArray.hasValue(C1306R.styleable.ShimmerFrameLayout_shimmer_highlight_alpha)) {
                setHighlightAlpha(typedArray.getFloat(C1306R.styleable.ShimmerFrameLayout_shimmer_highlight_alpha, 1.0f));
            }
            if (typedArray.hasValue(C1306R.styleable.ShimmerFrameLayout_shimmer_duration)) {
                setDuration((long) typedArray.getInt(C1306R.styleable.ShimmerFrameLayout_shimmer_duration, (int) this.mShimmer.animationDuration));
            }
            if (typedArray.hasValue(C1306R.styleable.ShimmerFrameLayout_shimmer_repeat_count)) {
                setRepeatCount(typedArray.getInt(C1306R.styleable.ShimmerFrameLayout_shimmer_repeat_count, this.mShimmer.repeatCount));
            }
            if (typedArray.hasValue(C1306R.styleable.ShimmerFrameLayout_shimmer_repeat_delay)) {
                setRepeatDelay((long) typedArray.getInt(C1306R.styleable.ShimmerFrameLayout_shimmer_repeat_delay, (int) this.mShimmer.repeatDelay));
            }
            if (typedArray.hasValue(C1306R.styleable.ShimmerFrameLayout_shimmer_repeat_mode)) {
                setRepeatMode(typedArray.getInt(C1306R.styleable.ShimmerFrameLayout_shimmer_repeat_mode, this.mShimmer.repeatMode));
            }
            if (typedArray.hasValue(C1306R.styleable.ShimmerFrameLayout_shimmer_direction)) {
                int i = typedArray.getInt(C1306R.styleable.ShimmerFrameLayout_shimmer_direction, this.mShimmer.direction);
                if (i == 1) {
                    setDirection(1);
                } else if (i == 2) {
                    setDirection(2);
                } else if (i != 3) {
                    setDirection(0);
                } else {
                    setDirection(3);
                }
            }
            if (typedArray.hasValue(C1306R.styleable.ShimmerFrameLayout_shimmer_shape)) {
                if (typedArray.getInt(C1306R.styleable.ShimmerFrameLayout_shimmer_shape, this.mShimmer.shape) != 1) {
                    setShape(0);
                } else {
                    setShape(1);
                }
            }
            if (typedArray.hasValue(C1306R.styleable.ShimmerFrameLayout_shimmer_dropoff)) {
                setDropoff(typedArray.getFloat(C1306R.styleable.ShimmerFrameLayout_shimmer_dropoff, this.mShimmer.dropoff));
            }
            if (typedArray.hasValue(C1306R.styleable.ShimmerFrameLayout_shimmer_fixed_width)) {
                setFixedWidth(typedArray.getDimensionPixelSize(C1306R.styleable.ShimmerFrameLayout_shimmer_fixed_width, this.mShimmer.fixedWidth));
            }
            if (typedArray.hasValue(C1306R.styleable.ShimmerFrameLayout_shimmer_fixed_height)) {
                setFixedHeight(typedArray.getDimensionPixelSize(C1306R.styleable.ShimmerFrameLayout_shimmer_fixed_height, this.mShimmer.fixedHeight));
            }
            if (typedArray.hasValue(C1306R.styleable.ShimmerFrameLayout_shimmer_intensity)) {
                setIntensity(typedArray.getFloat(C1306R.styleable.ShimmerFrameLayout_shimmer_intensity, this.mShimmer.intensity));
            }
            if (typedArray.hasValue(C1306R.styleable.ShimmerFrameLayout_shimmer_width_ratio)) {
                setWidthRatio(typedArray.getFloat(C1306R.styleable.ShimmerFrameLayout_shimmer_width_ratio, this.mShimmer.widthRatio));
            }
            if (typedArray.hasValue(C1306R.styleable.ShimmerFrameLayout_shimmer_height_ratio)) {
                setHeightRatio(typedArray.getFloat(C1306R.styleable.ShimmerFrameLayout_shimmer_height_ratio, this.mShimmer.heightRatio));
            }
            if (typedArray.hasValue(C1306R.styleable.ShimmerFrameLayout_shimmer_tilt)) {
                setTilt(typedArray.getFloat(C1306R.styleable.ShimmerFrameLayout_shimmer_tilt, this.mShimmer.tilt));
            }
            return getThis();
        }

        public T setDirection(int i) {
            this.mShimmer.direction = i;
            return getThis();
        }

        public T setShape(int i) {
            this.mShimmer.shape = i;
            return getThis();
        }

        public T setFixedWidth(int i) {
            if (i >= 0) {
                this.mShimmer.fixedWidth = i;
                return getThis();
            }
            StringBuilder sb = new StringBuilder();
            sb.append("Given invalid width: ");
            sb.append(i);
            throw new IllegalArgumentException(sb.toString());
        }

        public T setFixedHeight(int i) {
            if (i >= 0) {
                this.mShimmer.fixedHeight = i;
                return getThis();
            }
            StringBuilder sb = new StringBuilder();
            sb.append("Given invalid height: ");
            sb.append(i);
            throw new IllegalArgumentException(sb.toString());
        }

        public T setWidthRatio(float f) {
            if (f >= 0.0f) {
                this.mShimmer.widthRatio = f;
                return getThis();
            }
            StringBuilder sb = new StringBuilder();
            sb.append("Given invalid width ratio: ");
            sb.append(f);
            throw new IllegalArgumentException(sb.toString());
        }

        public T setHeightRatio(float f) {
            if (f >= 0.0f) {
                this.mShimmer.heightRatio = f;
                return getThis();
            }
            StringBuilder sb = new StringBuilder();
            sb.append("Given invalid height ratio: ");
            sb.append(f);
            throw new IllegalArgumentException(sb.toString());
        }

        public T setIntensity(float f) {
            if (f >= 0.0f) {
                this.mShimmer.intensity = f;
                return getThis();
            }
            StringBuilder sb = new StringBuilder();
            sb.append("Given invalid intensity value: ");
            sb.append(f);
            throw new IllegalArgumentException(sb.toString());
        }

        public T setDropoff(float f) {
            if (f >= 0.0f) {
                this.mShimmer.dropoff = f;
                return getThis();
            }
            StringBuilder sb = new StringBuilder();
            sb.append("Given invalid dropoff value: ");
            sb.append(f);
            throw new IllegalArgumentException(sb.toString());
        }

        public T setTilt(float f) {
            this.mShimmer.tilt = f;
            return getThis();
        }

        public T setBaseAlpha(float f) {
            int clamp = (int) (clamp(0.0f, 1.0f, f) * 255.0f);
            Shimmer shimmer = this.mShimmer;
            shimmer.baseColor = (clamp << 24) | (shimmer.baseColor & ViewCompat.MEASURED_SIZE_MASK);
            return getThis();
        }

        public T setHighlightAlpha(float f) {
            int clamp = (int) (clamp(0.0f, 1.0f, f) * 255.0f);
            Shimmer shimmer = this.mShimmer;
            shimmer.highlightColor = (clamp << 24) | (shimmer.highlightColor & ViewCompat.MEASURED_SIZE_MASK);
            return getThis();
        }

        public T setClipToChildren(boolean z) {
            this.mShimmer.clipToChildren = z;
            return getThis();
        }

        public T setAutoStart(boolean z) {
            this.mShimmer.autoStart = z;
            return getThis();
        }

        public T setRepeatCount(int i) {
            this.mShimmer.repeatCount = i;
            return getThis();
        }

        public T setRepeatMode(int i) {
            this.mShimmer.repeatMode = i;
            return getThis();
        }

        public T setRepeatDelay(long j) {
            if (j >= 0) {
                this.mShimmer.repeatDelay = j;
                return getThis();
            }
            StringBuilder sb = new StringBuilder();
            sb.append("Given a negative repeat delay: ");
            sb.append(j);
            throw new IllegalArgumentException(sb.toString());
        }

        public T setDuration(long j) {
            if (j >= 0) {
                this.mShimmer.animationDuration = j;
                return getThis();
            }
            StringBuilder sb = new StringBuilder();
            sb.append("Given a negative duration: ");
            sb.append(j);
            throw new IllegalArgumentException(sb.toString());
        }

        public Shimmer build() {
            this.mShimmer.updateColors();
            this.mShimmer.updatePositions();
            return this.mShimmer;
        }

        private static float clamp(float f, float f2, float f3) {
            return Math.min(f2, Math.max(f, f3));
        }
    }

    public static class ColorHighlightBuilder extends Builder<ColorHighlightBuilder> {
        /* access modifiers changed from: protected */
        public ColorHighlightBuilder getThis() {
            return this;
        }

        public ColorHighlightBuilder setHighlightColor(int i) {
            this.mShimmer.highlightColor = i;
            return getThis();
        }

        public ColorHighlightBuilder setBaseColor(int i) {
            this.mShimmer.baseColor = (i & ViewCompat.MEASURED_SIZE_MASK) | (this.mShimmer.baseColor & ViewCompat.MEASURED_STATE_MASK);
            return getThis();
        }

        /* access modifiers changed from: 0000 */
        public ColorHighlightBuilder consumeAttributes(TypedArray typedArray) {
            super.consumeAttributes(typedArray);
            if (typedArray.hasValue(C1306R.styleable.ShimmerFrameLayout_shimmer_base_color)) {
                setBaseColor(typedArray.getColor(C1306R.styleable.ShimmerFrameLayout_shimmer_base_color, this.mShimmer.baseColor));
            }
            if (typedArray.hasValue(C1306R.styleable.ShimmerFrameLayout_shimmer_highlight_color)) {
                setHighlightColor(typedArray.getColor(C1306R.styleable.ShimmerFrameLayout_shimmer_highlight_color, this.mShimmer.highlightColor));
            }
            return getThis();
        }
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface Direction {
        public static final int BOTTOM_TO_TOP = 3;
        public static final int LEFT_TO_RIGHT = 0;
        public static final int RIGHT_TO_LEFT = 2;
        public static final int TOP_TO_BOTTOM = 1;
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface Shape {
        public static final int LINEAR = 0;
        public static final int RADIAL = 1;
    }

    Shimmer() {
    }

    /* access modifiers changed from: 0000 */
    public int width(int i) {
        int i2 = this.fixedWidth;
        return i2 > 0 ? i2 : Math.round(this.widthRatio * ((float) i));
    }

    /* access modifiers changed from: 0000 */
    public int height(int i) {
        int i2 = this.fixedHeight;
        return i2 > 0 ? i2 : Math.round(this.heightRatio * ((float) i));
    }

    /* access modifiers changed from: 0000 */
    public void updateColors() {
        if (this.shape != 1) {
            int[] iArr = this.colors;
            int i = this.baseColor;
            iArr[0] = i;
            int i2 = this.highlightColor;
            iArr[1] = i2;
            iArr[2] = i2;
            iArr[3] = i;
            return;
        }
        int[] iArr2 = this.colors;
        int i3 = this.highlightColor;
        iArr2[0] = i3;
        iArr2[1] = i3;
        int i4 = this.baseColor;
        iArr2[2] = i4;
        iArr2[3] = i4;
    }

    /* access modifiers changed from: 0000 */
    public void updatePositions() {
        if (this.shape != 1) {
            this.positions[0] = Math.max(((1.0f - this.intensity) - this.dropoff) / 2.0f, 0.0f);
            this.positions[1] = Math.max(((1.0f - this.intensity) - 0.001f) / 2.0f, 0.0f);
            this.positions[2] = Math.min(((this.intensity + 1.0f) + 0.001f) / 2.0f, 1.0f);
            this.positions[3] = Math.min(((this.intensity + 1.0f) + this.dropoff) / 2.0f, 1.0f);
            return;
        }
        float[] fArr = this.positions;
        fArr[0] = 0.0f;
        fArr[1] = Math.min(this.intensity, 1.0f);
        this.positions[2] = Math.min(this.intensity + this.dropoff, 1.0f);
        this.positions[3] = 1.0f;
    }

    /* access modifiers changed from: 0000 */
    public void updateBounds(int i, int i2) {
        double max = (double) Math.max(i, i2);
        double sin = Math.sin(1.5707963267948966d - Math.toRadians((double) (this.tilt % 90.0f)));
        Double.isNaN(max);
        double d = max / sin;
        Double.isNaN(max);
        int round = Math.round(((float) (d - max)) / 2.0f) * 3;
        float f = (float) (-round);
        this.bounds.set(f, f, (float) (width(i) + round), (float) (height(i2) + round));
    }
}
