package com.wdullaer.materialdatetimepicker.time;

import android.animation.Keyframe;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.animation.ValueAnimator;
import android.animation.ValueAnimator.AnimatorUpdateListener;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.Log;
import android.view.View;
import com.wdullaer.materialdatetimepicker.C5266R;
import com.wdullaer.materialdatetimepicker.time.TimePickerDialog.Version;

public class RadialSelectorView extends View {
    private static final int FULL_ALPHA = 255;
    private static final int SELECTED_ALPHA = 255;
    private static final int SELECTED_ALPHA_THEME_DARK = 255;
    private static final String TAG = "RadialSelectorView";
    private float mAmPmCircleRadiusMultiplier;
    private float mAnimationRadiusMultiplier;
    private int mCircleRadius;
    private float mCircleRadiusMultiplier;
    private boolean mDrawValuesReady;
    private boolean mForceDrawDot;
    private boolean mHasInnerCircle;
    private float mInnerNumbersRadiusMultiplier;
    private InvalidateUpdateListener mInvalidateUpdateListener;
    private boolean mIs24HourMode;
    private boolean mIsInitialized = false;
    private int mLineLength;
    private float mNumbersRadiusMultiplier;
    private float mOuterNumbersRadiusMultiplier;
    private final Paint mPaint = new Paint();
    private int mSelectionAlpha;
    private int mSelectionDegrees;
    private double mSelectionRadians;
    private int mSelectionRadius;
    private float mSelectionRadiusMultiplier;
    private float mTransitionEndRadiusMultiplier;
    private float mTransitionMidRadiusMultiplier;
    private int mXCenter;
    private int mYCenter;

    private class InvalidateUpdateListener implements AnimatorUpdateListener {
        private InvalidateUpdateListener() {
        }

        public void onAnimationUpdate(ValueAnimator valueAnimator) {
            RadialSelectorView.this.invalidate();
        }
    }

    public boolean hasOverlappingRendering() {
        return false;
    }

    public RadialSelectorView(Context context) {
        super(context);
    }

    public void initialize(Context context, TimePickerController timePickerController, boolean z, boolean z2, int i, boolean z3) {
        if (this.mIsInitialized) {
            Log.e(TAG, "This RadialSelectorView may only be initialized once.");
            return;
        }
        Resources resources = context.getResources();
        this.mPaint.setColor(timePickerController.getAccentColor());
        this.mPaint.setAntiAlias(true);
        boolean isThemeDark = timePickerController.isThemeDark();
        this.mSelectionAlpha = 255;
        this.mIs24HourMode = timePickerController.is24HourMode();
        if (this.mIs24HourMode || timePickerController.getVersion() != Version.VERSION_1) {
            this.mCircleRadiusMultiplier = Float.parseFloat(resources.getString(C5266R.C5270string.mdtp_circle_radius_multiplier_24HourMode));
        } else {
            this.mCircleRadiusMultiplier = Float.parseFloat(resources.getString(C5266R.C5270string.mdtp_circle_radius_multiplier));
            this.mAmPmCircleRadiusMultiplier = Float.parseFloat(resources.getString(C5266R.C5270string.mdtp_ampm_circle_radius_multiplier));
        }
        this.mHasInnerCircle = z;
        if (z) {
            this.mInnerNumbersRadiusMultiplier = Float.parseFloat(resources.getString(C5266R.C5270string.mdtp_numbers_radius_multiplier_inner));
            this.mOuterNumbersRadiusMultiplier = Float.parseFloat(resources.getString(C5266R.C5270string.mdtp_numbers_radius_multiplier_outer));
        } else {
            this.mNumbersRadiusMultiplier = Float.parseFloat(resources.getString(C5266R.C5270string.mdtp_numbers_radius_multiplier_normal));
        }
        this.mSelectionRadiusMultiplier = Float.parseFloat(resources.getString(C5266R.C5270string.mdtp_selection_radius_multiplier));
        this.mAnimationRadiusMultiplier = 1.0f;
        int i2 = -1;
        this.mTransitionMidRadiusMultiplier = (((float) (z2 ? -1 : 1)) * 0.05f) + 1.0f;
        if (z2) {
            i2 = 1;
        }
        this.mTransitionEndRadiusMultiplier = (((float) i2) * 0.3f) + 1.0f;
        this.mInvalidateUpdateListener = new InvalidateUpdateListener();
        setSelection(i, z3, false);
        this.mIsInitialized = true;
    }

    public void setSelection(int i, boolean z, boolean z2) {
        this.mSelectionDegrees = i;
        double d = (double) i;
        Double.isNaN(d);
        this.mSelectionRadians = (d * 3.141592653589793d) / 180.0d;
        this.mForceDrawDot = z2;
        if (!this.mHasInnerCircle) {
            return;
        }
        if (z) {
            this.mNumbersRadiusMultiplier = this.mInnerNumbersRadiusMultiplier;
        } else {
            this.mNumbersRadiusMultiplier = this.mOuterNumbersRadiusMultiplier;
        }
    }

    public void setAnimationRadiusMultiplier(float f) {
        this.mAnimationRadiusMultiplier = f;
    }

    public int getDegreesFromCoords(float f, float f2, boolean z, Boolean[] boolArr) {
        if (!this.mDrawValuesReady) {
            return -1;
        }
        int i = this.mYCenter;
        float f3 = (f2 - ((float) i)) * (f2 - ((float) i));
        int i2 = this.mXCenter;
        double sqrt = Math.sqrt((double) (f3 + ((f - ((float) i2)) * (f - ((float) i2)))));
        boolean z2 = true;
        if (this.mHasInnerCircle) {
            if (z) {
                double d = (double) ((int) (((float) this.mCircleRadius) * this.mInnerNumbersRadiusMultiplier));
                Double.isNaN(d);
                int abs = (int) Math.abs(sqrt - d);
                double d2 = (double) ((int) (((float) this.mCircleRadius) * this.mOuterNumbersRadiusMultiplier));
                Double.isNaN(d2);
                boolArr[0] = Boolean.valueOf(abs <= ((int) Math.abs(sqrt - d2)));
            } else {
                int i3 = this.mCircleRadius;
                float f4 = (float) i3;
                float f5 = this.mInnerNumbersRadiusMultiplier;
                int i4 = (int) (f4 * f5);
                int i5 = this.mSelectionRadius;
                int i6 = i4 - i5;
                float f6 = (float) i3;
                float f7 = this.mOuterNumbersRadiusMultiplier;
                int i7 = ((int) (f6 * f7)) + i5;
                int i8 = (int) (((float) i3) * ((f7 + f5) / 2.0f));
                if (sqrt >= ((double) i6) && sqrt <= ((double) i8)) {
                    boolArr[0] = Boolean.valueOf(true);
                } else if (sqrt > ((double) i7) || sqrt < ((double) i8)) {
                    return -1;
                } else {
                    boolArr[0] = Boolean.valueOf(false);
                }
            }
        } else if (!z) {
            double d3 = (double) this.mLineLength;
            Double.isNaN(d3);
            if (((int) Math.abs(sqrt - d3)) > ((int) (((float) this.mCircleRadius) * (1.0f - this.mNumbersRadiusMultiplier)))) {
                return -1;
            }
        }
        double abs2 = (double) Math.abs(f2 - ((float) this.mYCenter));
        Double.isNaN(abs2);
        int asin = (int) ((Math.asin(abs2 / sqrt) * 180.0d) / 3.141592653589793d);
        boolean z3 = f > ((float) this.mXCenter);
        if (f2 >= ((float) this.mYCenter)) {
            z2 = false;
        }
        if (z3 && z2) {
            asin = 90 - asin;
        } else if (z3 && !z2) {
            asin += 90;
        } else if (!z3 && !z2) {
            asin = 270 - asin;
        } else if (!z3 && z2) {
            asin += 270;
        }
        return asin;
    }

    public void onDraw(Canvas canvas) {
        if (getWidth() != 0 && this.mIsInitialized) {
            boolean z = true;
            if (!this.mDrawValuesReady) {
                this.mXCenter = getWidth() / 2;
                this.mYCenter = getHeight() / 2;
                this.mCircleRadius = (int) (((float) Math.min(this.mXCenter, this.mYCenter)) * this.mCircleRadiusMultiplier);
                if (!this.mIs24HourMode) {
                    double d = (double) this.mYCenter;
                    double d2 = (double) ((int) (((float) this.mCircleRadius) * this.mAmPmCircleRadiusMultiplier));
                    Double.isNaN(d2);
                    double d3 = d2 * 0.75d;
                    Double.isNaN(d);
                    this.mYCenter = (int) (d - d3);
                }
                this.mSelectionRadius = (int) (((float) this.mCircleRadius) * this.mSelectionRadiusMultiplier);
                this.mDrawValuesReady = true;
            }
            this.mLineLength = (int) (((float) this.mCircleRadius) * this.mNumbersRadiusMultiplier * this.mAnimationRadiusMultiplier);
            int i = this.mXCenter;
            double d4 = (double) this.mLineLength;
            double sin = Math.sin(this.mSelectionRadians);
            Double.isNaN(d4);
            int i2 = i + ((int) (d4 * sin));
            int i3 = this.mYCenter;
            double d5 = (double) this.mLineLength;
            double cos = Math.cos(this.mSelectionRadians);
            Double.isNaN(d5);
            int i4 = i3 - ((int) (d5 * cos));
            this.mPaint.setAlpha(this.mSelectionAlpha);
            float f = (float) i2;
            float f2 = (float) i4;
            canvas.drawCircle(f, f2, (float) this.mSelectionRadius, this.mPaint);
            boolean z2 = this.mForceDrawDot;
            if (this.mSelectionDegrees % 30 == 0) {
                z = false;
            }
            if (z || z2) {
                this.mPaint.setAlpha(255);
                canvas.drawCircle(f, f2, (float) ((this.mSelectionRadius * 2) / 7), this.mPaint);
            } else {
                int i5 = this.mLineLength - this.mSelectionRadius;
                int i6 = this.mXCenter;
                double d6 = (double) i5;
                double sin2 = Math.sin(this.mSelectionRadians);
                Double.isNaN(d6);
                i2 = ((int) (sin2 * d6)) + i6;
                int i7 = this.mYCenter;
                double cos2 = Math.cos(this.mSelectionRadians);
                Double.isNaN(d6);
                i4 = i7 - ((int) (d6 * cos2));
            }
            this.mPaint.setAlpha(255);
            this.mPaint.setStrokeWidth(3.0f);
            canvas.drawLine((float) this.mXCenter, (float) this.mYCenter, (float) i2, (float) i4, this.mPaint);
        }
    }

    public ObjectAnimator getDisappearAnimator() {
        if (!this.mIsInitialized || !this.mDrawValuesReady) {
            Log.e(TAG, "RadialSelectorView was not ready for animation.");
            return null;
        }
        ObjectAnimator duration = ObjectAnimator.ofPropertyValuesHolder(this, new PropertyValuesHolder[]{PropertyValuesHolder.ofKeyframe("animationRadiusMultiplier", new Keyframe[]{Keyframe.ofFloat(0.0f, 1.0f), Keyframe.ofFloat(0.2f, this.mTransitionMidRadiusMultiplier), Keyframe.ofFloat(1.0f, this.mTransitionEndRadiusMultiplier)}), PropertyValuesHolder.ofKeyframe("alpha", new Keyframe[]{Keyframe.ofFloat(0.0f, 1.0f), Keyframe.ofFloat(1.0f, 0.0f)})}).setDuration((long) 500);
        duration.addUpdateListener(this.mInvalidateUpdateListener);
        return duration;
    }

    public ObjectAnimator getReappearAnimator() {
        if (!this.mIsInitialized || !this.mDrawValuesReady) {
            Log.e(TAG, "RadialSelectorView was not ready for animation.");
            return null;
        }
        float f = (float) 500;
        int i = (int) (1.25f * f);
        float f2 = (f * 0.25f) / ((float) i);
        float f3 = 1.0f - ((1.0f - f2) * 0.2f);
        ObjectAnimator duration = ObjectAnimator.ofPropertyValuesHolder(this, new PropertyValuesHolder[]{PropertyValuesHolder.ofKeyframe("animationRadiusMultiplier", new Keyframe[]{Keyframe.ofFloat(0.0f, this.mTransitionEndRadiusMultiplier), Keyframe.ofFloat(f2, this.mTransitionEndRadiusMultiplier), Keyframe.ofFloat(f3, this.mTransitionMidRadiusMultiplier), Keyframe.ofFloat(1.0f, 1.0f)}), PropertyValuesHolder.ofKeyframe("alpha", new Keyframe[]{Keyframe.ofFloat(0.0f, 0.0f), Keyframe.ofFloat(f2, 0.0f), Keyframe.ofFloat(1.0f, 1.0f)})}).setDuration((long) i);
        duration.addUpdateListener(this.mInvalidateUpdateListener);
        return duration;
    }
}
