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
import android.graphics.Paint.Align;
import android.graphics.Typeface;
import android.util.Log;
import android.view.View;
import androidx.core.content.ContextCompat;
import com.wdullaer.materialdatetimepicker.C5266R;
import com.wdullaer.materialdatetimepicker.time.TimePickerDialog.Version;

public class RadialTextsView extends View {
    private static final String TAG = "RadialTextsView";
    private float mAmPmCircleRadiusMultiplier;
    private float mAnimationRadiusMultiplier;
    private float mCircleRadius;
    private float mCircleRadiusMultiplier;
    ObjectAnimator mDisappearAnimator;
    private boolean mDrawValuesReady;
    private boolean mHasInnerCircle;
    private final Paint mInactivePaint = new Paint();
    private float mInnerNumbersRadiusMultiplier;
    private float[] mInnerTextGridHeights;
    private float[] mInnerTextGridWidths;
    private float mInnerTextSize;
    private float mInnerTextSizeMultiplier;
    private String[] mInnerTexts;
    private InvalidateUpdateListener mInvalidateUpdateListener;
    private boolean mIs24HourMode;
    private boolean mIsInitialized = false;
    private float mNumbersRadiusMultiplier;
    private final Paint mPaint = new Paint();
    ObjectAnimator mReappearAnimator;
    private final Paint mSelectedPaint = new Paint();
    private float[] mTextGridHeights;
    private boolean mTextGridValuesDirty;
    private float[] mTextGridWidths;
    private float mTextSize;
    private float mTextSizeMultiplier;
    private String[] mTexts;
    private float mTransitionEndRadiusMultiplier;
    private float mTransitionMidRadiusMultiplier;
    private Typeface mTypefaceLight;
    private Typeface mTypefaceRegular;
    private SelectionValidator mValidator;
    private int mXCenter;
    private int mYCenter;
    private int selection = -1;

    private class InvalidateUpdateListener implements AnimatorUpdateListener {
        private InvalidateUpdateListener() {
        }

        public void onAnimationUpdate(ValueAnimator valueAnimator) {
            RadialTextsView.this.invalidate();
        }
    }

    interface SelectionValidator {
        boolean isValidSelection(int i);
    }

    public boolean hasOverlappingRendering() {
        return false;
    }

    public RadialTextsView(Context context) {
        super(context);
    }

    public void initialize(Context context, String[] strArr, String[] strArr2, TimePickerController timePickerController, SelectionValidator selectionValidator, boolean z) {
        if (this.mIsInitialized) {
            Log.e(TAG, "This RadialTextsView may only be initialized once.");
            return;
        }
        Resources resources = context.getResources();
        this.mPaint.setColor(ContextCompat.getColor(context, timePickerController.isThemeDark() ? C5266R.C5267color.mdtp_white : C5266R.C5267color.mdtp_numbers_text_color));
        boolean z2 = false;
        this.mTypefaceLight = Typeface.create(resources.getString(C5266R.C5270string.mdtp_radial_numbers_typeface), 0);
        this.mTypefaceRegular = Typeface.create(resources.getString(C5266R.C5270string.mdtp_sans_serif), 0);
        this.mPaint.setAntiAlias(true);
        this.mPaint.setTextAlign(Align.CENTER);
        this.mSelectedPaint.setColor(ContextCompat.getColor(context, C5266R.C5267color.mdtp_white));
        this.mSelectedPaint.setAntiAlias(true);
        this.mSelectedPaint.setTextAlign(Align.CENTER);
        this.mInactivePaint.setColor(ContextCompat.getColor(context, timePickerController.isThemeDark() ? C5266R.C5267color.mdtp_date_picker_text_disabled_dark_theme : C5266R.C5267color.mdtp_date_picker_text_disabled));
        this.mInactivePaint.setAntiAlias(true);
        this.mInactivePaint.setTextAlign(Align.CENTER);
        this.mTexts = strArr;
        this.mInnerTexts = strArr2;
        this.mIs24HourMode = timePickerController.is24HourMode();
        if (strArr2 != null) {
            z2 = true;
        }
        this.mHasInnerCircle = z2;
        if (this.mIs24HourMode || timePickerController.getVersion() != Version.VERSION_1) {
            this.mCircleRadiusMultiplier = Float.parseFloat(resources.getString(C5266R.C5270string.mdtp_circle_radius_multiplier_24HourMode));
        } else {
            this.mCircleRadiusMultiplier = Float.parseFloat(resources.getString(C5266R.C5270string.mdtp_circle_radius_multiplier));
            this.mAmPmCircleRadiusMultiplier = Float.parseFloat(resources.getString(C5266R.C5270string.mdtp_ampm_circle_radius_multiplier));
        }
        this.mTextGridHeights = new float[7];
        this.mTextGridWidths = new float[7];
        if (this.mHasInnerCircle) {
            this.mNumbersRadiusMultiplier = Float.parseFloat(resources.getString(C5266R.C5270string.mdtp_numbers_radius_multiplier_outer));
            this.mTextSizeMultiplier = Float.parseFloat(resources.getString(C5266R.C5270string.mdtp_text_size_multiplier_outer));
            this.mInnerNumbersRadiusMultiplier = Float.parseFloat(resources.getString(C5266R.C5270string.mdtp_numbers_radius_multiplier_inner));
            this.mInnerTextSizeMultiplier = Float.parseFloat(resources.getString(C5266R.C5270string.mdtp_text_size_multiplier_inner));
            this.mInnerTextGridHeights = new float[7];
            this.mInnerTextGridWidths = new float[7];
        } else {
            this.mNumbersRadiusMultiplier = Float.parseFloat(resources.getString(C5266R.C5270string.mdtp_numbers_radius_multiplier_normal));
            this.mTextSizeMultiplier = Float.parseFloat(resources.getString(C5266R.C5270string.mdtp_text_size_multiplier_normal));
        }
        this.mAnimationRadiusMultiplier = 1.0f;
        int i = -1;
        this.mTransitionMidRadiusMultiplier = (((float) (z ? -1 : 1)) * 0.05f) + 1.0f;
        if (z) {
            i = 1;
        }
        this.mTransitionEndRadiusMultiplier = (((float) i) * 0.3f) + 1.0f;
        this.mInvalidateUpdateListener = new InvalidateUpdateListener();
        this.mValidator = selectionValidator;
        this.mTextGridValuesDirty = true;
        this.mIsInitialized = true;
    }

    /* access modifiers changed from: protected */
    public void setSelection(int i) {
        this.selection = i;
    }

    public void setAnimationRadiusMultiplier(float f) {
        this.mAnimationRadiusMultiplier = f;
        this.mTextGridValuesDirty = true;
    }

    public void onDraw(Canvas canvas) {
        if (getWidth() != 0 && this.mIsInitialized) {
            if (!this.mDrawValuesReady) {
                this.mXCenter = getWidth() / 2;
                this.mYCenter = getHeight() / 2;
                this.mCircleRadius = ((float) Math.min(this.mXCenter, this.mYCenter)) * this.mCircleRadiusMultiplier;
                if (!this.mIs24HourMode) {
                    double d = (double) this.mYCenter;
                    double d2 = (double) (this.mCircleRadius * this.mAmPmCircleRadiusMultiplier);
                    Double.isNaN(d2);
                    double d3 = d2 * 0.75d;
                    Double.isNaN(d);
                    this.mYCenter = (int) (d - d3);
                }
                float f = this.mCircleRadius;
                this.mTextSize = this.mTextSizeMultiplier * f;
                if (this.mHasInnerCircle) {
                    this.mInnerTextSize = f * this.mInnerTextSizeMultiplier;
                }
                renderAnimations();
                this.mTextGridValuesDirty = true;
                this.mDrawValuesReady = true;
            }
            if (this.mTextGridValuesDirty) {
                calculateGridSizes(this.mCircleRadius * this.mNumbersRadiusMultiplier * this.mAnimationRadiusMultiplier, (float) this.mXCenter, (float) this.mYCenter, this.mTextSize, this.mTextGridHeights, this.mTextGridWidths);
                if (this.mHasInnerCircle) {
                    calculateGridSizes(this.mCircleRadius * this.mInnerNumbersRadiusMultiplier * this.mAnimationRadiusMultiplier, (float) this.mXCenter, (float) this.mYCenter, this.mInnerTextSize, this.mInnerTextGridHeights, this.mInnerTextGridWidths);
                }
                this.mTextGridValuesDirty = false;
            }
            drawTexts(canvas, this.mTextSize, this.mTypefaceLight, this.mTexts, this.mTextGridWidths, this.mTextGridHeights);
            if (this.mHasInnerCircle) {
                drawTexts(canvas, this.mInnerTextSize, this.mTypefaceRegular, this.mInnerTexts, this.mInnerTextGridWidths, this.mInnerTextGridHeights);
            }
        }
    }

    private void calculateGridSizes(float f, float f2, float f3, float f4, float[] fArr, float[] fArr2) {
        float sqrt = (((float) Math.sqrt(3.0d)) * f) / 2.0f;
        float f5 = f / 2.0f;
        this.mPaint.setTextSize(f4);
        this.mSelectedPaint.setTextSize(f4);
        this.mInactivePaint.setTextSize(f4);
        float descent = f3 - ((this.mPaint.descent() + this.mPaint.ascent()) / 2.0f);
        fArr[0] = descent - f;
        fArr2[0] = f2 - f;
        fArr[1] = descent - sqrt;
        fArr2[1] = f2 - sqrt;
        fArr[2] = descent - f5;
        fArr2[2] = f2 - f5;
        fArr[3] = descent;
        fArr2[3] = f2;
        fArr[4] = descent + f5;
        fArr2[4] = f5 + f2;
        fArr[5] = descent + sqrt;
        fArr2[5] = sqrt + f2;
        fArr[6] = descent + f;
        fArr2[6] = f2 + f;
    }

    private Paint[] assignTextColors(String[] strArr) {
        Paint[] paintArr = new Paint[strArr.length];
        for (int i = 0; i < strArr.length; i++) {
            int parseInt = Integer.parseInt(strArr[i]);
            if (parseInt == this.selection) {
                paintArr[i] = this.mSelectedPaint;
            } else if (this.mValidator.isValidSelection(parseInt)) {
                paintArr[i] = this.mPaint;
            } else {
                paintArr[i] = this.mInactivePaint;
            }
        }
        return paintArr;
    }

    private void drawTexts(Canvas canvas, float f, Typeface typeface, String[] strArr, float[] fArr, float[] fArr2) {
        Canvas canvas2 = canvas;
        String[] strArr2 = strArr;
        this.mPaint.setTextSize(f);
        this.mPaint.setTypeface(typeface);
        Paint[] assignTextColors = assignTextColors(strArr2);
        canvas2.drawText(strArr2[0], fArr[3], fArr2[0], assignTextColors[0]);
        canvas2.drawText(strArr2[1], fArr[4], fArr2[1], assignTextColors[1]);
        canvas2.drawText(strArr2[2], fArr[5], fArr2[2], assignTextColors[2]);
        canvas2.drawText(strArr2[3], fArr[6], fArr2[3], assignTextColors[3]);
        canvas2.drawText(strArr2[4], fArr[5], fArr2[4], assignTextColors[4]);
        canvas2.drawText(strArr2[5], fArr[4], fArr2[5], assignTextColors[5]);
        canvas2.drawText(strArr2[6], fArr[3], fArr2[6], assignTextColors[6]);
        canvas2.drawText(strArr2[7], fArr[2], fArr2[5], assignTextColors[7]);
        canvas2.drawText(strArr2[8], fArr[1], fArr2[4], assignTextColors[8]);
        canvas2.drawText(strArr2[9], fArr[0], fArr2[3], assignTextColors[9]);
        canvas2.drawText(strArr2[10], fArr[1], fArr2[2], assignTextColors[10]);
        canvas2.drawText(strArr2[11], fArr[2], fArr2[1], assignTextColors[11]);
    }

    private void renderAnimations() {
        Keyframe[] keyframeArr = {Keyframe.ofFloat(0.0f, 1.0f), Keyframe.ofFloat(0.2f, this.mTransitionMidRadiusMultiplier), Keyframe.ofFloat(1.0f, this.mTransitionEndRadiusMultiplier)};
        String str = "animationRadiusMultiplier";
        PropertyValuesHolder ofKeyframe = PropertyValuesHolder.ofKeyframe(str, keyframeArr);
        Keyframe[] keyframeArr2 = {Keyframe.ofFloat(0.0f, 1.0f), Keyframe.ofFloat(1.0f, 0.0f)};
        String str2 = "alpha";
        this.mDisappearAnimator = ObjectAnimator.ofPropertyValuesHolder(this, new PropertyValuesHolder[]{ofKeyframe, PropertyValuesHolder.ofKeyframe(str2, keyframeArr2)}).setDuration((long) 500);
        this.mDisappearAnimator.addUpdateListener(this.mInvalidateUpdateListener);
        float f = (float) 500;
        int i = (int) (1.25f * f);
        float f2 = (f * 0.25f) / ((float) i);
        float f3 = 1.0f - ((1.0f - f2) * 0.2f);
        this.mReappearAnimator = ObjectAnimator.ofPropertyValuesHolder(this, new PropertyValuesHolder[]{PropertyValuesHolder.ofKeyframe(str, new Keyframe[]{Keyframe.ofFloat(0.0f, this.mTransitionEndRadiusMultiplier), Keyframe.ofFloat(f2, this.mTransitionEndRadiusMultiplier), Keyframe.ofFloat(f3, this.mTransitionMidRadiusMultiplier), Keyframe.ofFloat(1.0f, 1.0f)}), PropertyValuesHolder.ofKeyframe(str2, new Keyframe[]{Keyframe.ofFloat(0.0f, 0.0f), Keyframe.ofFloat(f2, 0.0f), Keyframe.ofFloat(1.0f, 1.0f)})}).setDuration((long) i);
        this.mReappearAnimator.addUpdateListener(this.mInvalidateUpdateListener);
    }

    public ObjectAnimator getDisappearAnimator() {
        if (this.mIsInitialized && this.mDrawValuesReady) {
            ObjectAnimator objectAnimator = this.mDisappearAnimator;
            if (objectAnimator != null) {
                return objectAnimator;
            }
        }
        Log.e(TAG, "RadialTextView was not ready for animation.");
        return null;
    }

    public ObjectAnimator getReappearAnimator() {
        if (this.mIsInitialized && this.mDrawValuesReady) {
            ObjectAnimator objectAnimator = this.mReappearAnimator;
            if (objectAnimator != null) {
                return objectAnimator;
            }
        }
        Log.e(TAG, "RadialTextView was not ready for animation.");
        return null;
    }
}
