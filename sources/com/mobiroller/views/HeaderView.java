package com.mobiroller.views;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.core.view.ViewCompat;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.mobiroller.util.ColorUtil;
import com.mobiroller.views.theme.Theme;

public class HeaderView extends LinearLayout {
    @BindView(2131362598)
    TextView lastSeen;
    @BindView(2131362785)
    TextView name;

    public HeaderView(Context context) {
        super(context);
    }

    public HeaderView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    public HeaderView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
    }

    public HeaderView(Context context, AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i, i2);
    }

    /* access modifiers changed from: protected */
    public void onFinishInflate() {
        super.onFinishInflate();
        ButterKnife.bind((View) this);
        int i = -1;
        this.name.setTextColor(ColorUtil.isColorDark(Theme.primaryColor) ? -1 : ViewCompat.MEASURED_STATE_MASK);
        TextView textView = this.lastSeen;
        if (!ColorUtil.isColorDark(Theme.primaryColor)) {
            i = ViewCompat.MEASURED_STATE_MASK;
        }
        textView.setTextColor(i);
    }

    public void bindTo(String str, String str2) {
        this.name.setText(str);
        this.lastSeen.setText(str2);
        if (str2 == null || str2.isEmpty()) {
            this.lastSeen.setVisibility(8);
        }
    }

    public void setTextSize(float f) {
        this.name.setTextSize(0, f);
    }
}
