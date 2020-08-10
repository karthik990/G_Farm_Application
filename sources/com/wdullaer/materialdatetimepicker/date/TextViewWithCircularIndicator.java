package com.wdullaer.materialdatetimepicker.date;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.Align;
import android.graphics.Paint.Style;
import android.util.AttributeSet;
import android.widget.TextView;
import androidx.core.content.ContextCompat;
import androidx.core.view.ViewCompat;
import com.wdullaer.materialdatetimepicker.C5266R;

public class TextViewWithCircularIndicator extends TextView {
    private static final int SELECTED_CIRCLE_ALPHA = 255;
    private int mCircleColor;
    Paint mCirclePaint = new Paint();
    private boolean mDrawCircle;
    private final String mItemIsSelectedText;

    public TextViewWithCircularIndicator(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mCircleColor = ContextCompat.getColor(context, C5266R.C5267color.mdtp_accent_color);
        this.mItemIsSelectedText = context.getResources().getString(C5266R.C5270string.mdtp_item_is_selected);
        init();
    }

    private void init() {
        this.mCirclePaint.setFakeBoldText(true);
        this.mCirclePaint.setAntiAlias(true);
        this.mCirclePaint.setColor(this.mCircleColor);
        this.mCirclePaint.setTextAlign(Align.CENTER);
        this.mCirclePaint.setStyle(Style.FILL);
        this.mCirclePaint.setAlpha(255);
    }

    public void setAccentColor(int i, boolean z) {
        this.mCircleColor = i;
        this.mCirclePaint.setColor(this.mCircleColor);
        setTextColor(createTextColor(i, z));
    }

    private ColorStateList createTextColor(int i, boolean z) {
        int[][] iArr = {new int[]{16842919}, new int[]{16842913}, new int[0]};
        int[] iArr2 = new int[3];
        iArr2[0] = i;
        int i2 = -1;
        iArr2[1] = -1;
        if (!z) {
            i2 = ViewCompat.MEASURED_STATE_MASK;
        }
        iArr2[2] = i2;
        return new ColorStateList(iArr, iArr2);
    }

    public void drawIndicator(boolean z) {
        this.mDrawCircle = z;
    }

    public void onDraw(Canvas canvas) {
        if (this.mDrawCircle) {
            int width = getWidth();
            int height = getHeight();
            canvas.drawCircle((float) (width / 2), (float) (height / 2), (float) (Math.min(width, height) / 2), this.mCirclePaint);
        }
        setSelected(this.mDrawCircle);
        super.onDraw(canvas);
    }

    public CharSequence getContentDescription() {
        CharSequence text = getText();
        if (!this.mDrawCircle) {
            return text;
        }
        return String.format(this.mItemIsSelectedText, new Object[]{text});
    }
}
