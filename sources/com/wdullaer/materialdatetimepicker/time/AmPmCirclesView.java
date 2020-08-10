package com.wdullaer.materialdatetimepicker.time;

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
import com.wdullaer.materialdatetimepicker.C5272Utils;
import java.text.DateFormatSymbols;

public class AmPmCirclesView extends View {

    /* renamed from: AM */
    private static final int f3678AM = 0;

    /* renamed from: PM */
    private static final int f3679PM = 1;
    private static final int SELECTED_ALPHA = 255;
    private static final int SELECTED_ALPHA_THEME_DARK = 255;
    private static final String TAG = "AmPmCirclesView";
    private boolean mAmDisabled;
    private int mAmOrPm;
    private int mAmOrPmPressed;
    private int mAmPmCircleRadius;
    private float mAmPmCircleRadiusMultiplier;
    private int mAmPmDisabledTextColor;
    private int mAmPmSelectedTextColor;
    private int mAmPmTextColor;
    private int mAmPmYCenter;
    private String mAmText;
    private int mAmXCenter;
    private float mCircleRadiusMultiplier;
    private boolean mDrawValuesReady;
    private boolean mIsInitialized = false;
    private final Paint mPaint = new Paint();
    private boolean mPmDisabled;
    private String mPmText;
    private int mPmXCenter;
    private int mSelectedAlpha;
    private int mSelectedColor;
    private int mTouchedColor;
    private int mUnselectedColor;

    public AmPmCirclesView(Context context) {
        super(context);
    }

    public void initialize(Context context, TimePickerController timePickerController, int i) {
        if (this.mIsInitialized) {
            Log.e(TAG, "AmPmCirclesView may only be initialized once.");
            return;
        }
        Resources resources = context.getResources();
        if (timePickerController.isThemeDark()) {
            this.mUnselectedColor = ContextCompat.getColor(context, C5266R.C5267color.mdtp_circle_background_dark_theme);
            this.mAmPmTextColor = ContextCompat.getColor(context, C5266R.C5267color.mdtp_white);
            this.mAmPmDisabledTextColor = ContextCompat.getColor(context, C5266R.C5267color.mdtp_date_picker_text_disabled_dark_theme);
            this.mSelectedAlpha = 255;
        } else {
            this.mUnselectedColor = ContextCompat.getColor(context, C5266R.C5267color.mdtp_white);
            this.mAmPmTextColor = ContextCompat.getColor(context, C5266R.C5267color.mdtp_ampm_text_color);
            this.mAmPmDisabledTextColor = ContextCompat.getColor(context, C5266R.C5267color.mdtp_date_picker_text_disabled);
            this.mSelectedAlpha = 255;
        }
        this.mSelectedColor = timePickerController.getAccentColor();
        this.mTouchedColor = C5272Utils.darkenColor(this.mSelectedColor);
        this.mAmPmSelectedTextColor = ContextCompat.getColor(context, C5266R.C5267color.mdtp_white);
        this.mPaint.setTypeface(Typeface.create(resources.getString(C5266R.C5270string.mdtp_sans_serif), 0));
        this.mPaint.setAntiAlias(true);
        this.mPaint.setTextAlign(Align.CENTER);
        this.mCircleRadiusMultiplier = Float.parseFloat(resources.getString(C5266R.C5270string.mdtp_circle_radius_multiplier));
        this.mAmPmCircleRadiusMultiplier = Float.parseFloat(resources.getString(C5266R.C5270string.mdtp_ampm_circle_radius_multiplier));
        String[] amPmStrings = new DateFormatSymbols().getAmPmStrings();
        this.mAmText = amPmStrings[0];
        this.mPmText = amPmStrings[1];
        this.mAmDisabled = timePickerController.isAmDisabled();
        this.mPmDisabled = timePickerController.isPmDisabled();
        setAmOrPm(i);
        this.mAmOrPmPressed = -1;
        this.mIsInitialized = true;
    }

    public void setAmOrPm(int i) {
        this.mAmOrPm = i;
    }

    public void setAmOrPmPressed(int i) {
        this.mAmOrPmPressed = i;
    }

    public int getIsTouchingAmOrPm(float f, float f2) {
        if (!this.mDrawValuesReady) {
            return -1;
        }
        int i = this.mAmPmYCenter;
        int i2 = (int) ((f2 - ((float) i)) * (f2 - ((float) i)));
        int i3 = this.mAmXCenter;
        float f3 = (float) i2;
        if (((int) Math.sqrt((double) (((f - ((float) i3)) * (f - ((float) i3))) + f3))) <= this.mAmPmCircleRadius && !this.mAmDisabled) {
            return 0;
        }
        int i4 = this.mPmXCenter;
        if (((int) Math.sqrt((double) (((f - ((float) i4)) * (f - ((float) i4))) + f3))) > this.mAmPmCircleRadius || this.mPmDisabled) {
            return -1;
        }
        return 1;
    }

    public void onDraw(Canvas canvas) {
        int i;
        int i2;
        int i3;
        if (getWidth() != 0 && this.mIsInitialized) {
            if (!this.mDrawValuesReady) {
                int width = getWidth() / 2;
                int height = getHeight() / 2;
                int min = (int) (((float) Math.min(width, height)) * this.mCircleRadiusMultiplier);
                this.mAmPmCircleRadius = (int) (((float) min) * this.mAmPmCircleRadiusMultiplier);
                double d = (double) height;
                int i4 = this.mAmPmCircleRadius;
                double d2 = (double) i4;
                Double.isNaN(d2);
                double d3 = d2 * 0.75d;
                Double.isNaN(d);
                int i5 = (int) (d + d3);
                this.mPaint.setTextSize((float) ((i4 * 3) / 4));
                int i6 = this.mAmPmCircleRadius;
                this.mAmPmYCenter = (i5 - (i6 / 2)) + min;
                this.mAmXCenter = (width - min) + i6;
                this.mPmXCenter = (width + min) - i6;
                this.mDrawValuesReady = true;
            }
            int i7 = this.mUnselectedColor;
            int i8 = this.mAmPmTextColor;
            int i9 = this.mAmOrPm;
            int i10 = 255;
            if (i9 == 0) {
                i3 = this.mSelectedColor;
                i2 = this.mSelectedAlpha;
                i = this.mAmPmSelectedTextColor;
            } else if (i9 == 1) {
                int i11 = this.mSelectedColor;
                i10 = this.mSelectedAlpha;
                i2 = 255;
                int i12 = i11;
                i3 = i7;
                i7 = i12;
                i = i8;
                i8 = this.mAmPmSelectedTextColor;
            } else {
                i3 = i7;
                i = i8;
                i2 = 255;
            }
            int i13 = this.mAmOrPmPressed;
            if (i13 == 0) {
                i3 = this.mTouchedColor;
                i2 = this.mSelectedAlpha;
            } else if (i13 == 1) {
                i7 = this.mTouchedColor;
                i10 = this.mSelectedAlpha;
            }
            if (this.mAmDisabled) {
                i3 = this.mUnselectedColor;
                i = this.mAmPmDisabledTextColor;
            }
            if (this.mPmDisabled) {
                i7 = this.mUnselectedColor;
                i8 = this.mAmPmDisabledTextColor;
            }
            this.mPaint.setColor(i3);
            this.mPaint.setAlpha(i2);
            canvas.drawCircle((float) this.mAmXCenter, (float) this.mAmPmYCenter, (float) this.mAmPmCircleRadius, this.mPaint);
            this.mPaint.setColor(i7);
            this.mPaint.setAlpha(i10);
            canvas.drawCircle((float) this.mPmXCenter, (float) this.mAmPmYCenter, (float) this.mAmPmCircleRadius, this.mPaint);
            this.mPaint.setColor(i);
            float descent = (float) (this.mAmPmYCenter - (((int) (this.mPaint.descent() + this.mPaint.ascent())) / 2));
            canvas.drawText(this.mAmText, (float) this.mAmXCenter, descent, this.mPaint);
            this.mPaint.setColor(i8);
            canvas.drawText(this.mPmText, (float) this.mPmXCenter, descent, this.mPaint);
        }
    }
}
