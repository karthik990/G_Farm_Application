package com.wdullaer.materialdatetimepicker.time;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.Log;
import android.view.View;
import androidx.core.content.ContextCompat;
import com.wdullaer.materialdatetimepicker.C5266R;
import com.wdullaer.materialdatetimepicker.time.TimePickerDialog.Version;

public class CircleView extends View {
    private static final String TAG = "CircleView";
    private float mAmPmCircleRadiusMultiplier;
    private int mCircleColor;
    private int mCircleRadius;
    private float mCircleRadiusMultiplier;
    private int mDotColor;
    private boolean mDrawValuesReady;
    private boolean mIs24HourMode;
    private boolean mIsInitialized = false;
    private final Paint mPaint = new Paint();
    private int mXCenter;
    private int mYCenter;

    public CircleView(Context context) {
        super(context);
    }

    public void initialize(Context context, TimePickerController timePickerController) {
        if (this.mIsInitialized) {
            Log.e(TAG, "CircleView may only be initialized once.");
            return;
        }
        Resources resources = context.getResources();
        this.mCircleColor = ContextCompat.getColor(context, timePickerController.isThemeDark() ? C5266R.C5267color.mdtp_circle_background_dark_theme : C5266R.C5267color.mdtp_circle_color);
        this.mDotColor = timePickerController.getAccentColor();
        this.mPaint.setAntiAlias(true);
        this.mIs24HourMode = timePickerController.is24HourMode();
        if (this.mIs24HourMode || timePickerController.getVersion() != Version.VERSION_1) {
            this.mCircleRadiusMultiplier = Float.parseFloat(resources.getString(C5266R.C5270string.mdtp_circle_radius_multiplier_24HourMode));
        } else {
            this.mCircleRadiusMultiplier = Float.parseFloat(resources.getString(C5266R.C5270string.mdtp_circle_radius_multiplier));
            this.mAmPmCircleRadiusMultiplier = Float.parseFloat(resources.getString(C5266R.C5270string.mdtp_ampm_circle_radius_multiplier));
        }
        this.mIsInitialized = true;
    }

    public void onDraw(Canvas canvas) {
        if (getWidth() != 0 && this.mIsInitialized) {
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
                this.mDrawValuesReady = true;
            }
            this.mPaint.setColor(this.mCircleColor);
            canvas.drawCircle((float) this.mXCenter, (float) this.mYCenter, (float) this.mCircleRadius, this.mPaint);
            this.mPaint.setColor(this.mDotColor);
            canvas.drawCircle((float) this.mXCenter, (float) this.mYCenter, 8.0f, this.mPaint);
        }
    }
}
