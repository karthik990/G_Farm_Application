package com.google.android.youtube.player.internal;

import android.content.Context;
import android.view.View.MeasureSpec;
import android.widget.FrameLayout;
import android.widget.FrameLayout.LayoutParams;
import android.widget.ProgressBar;
import android.widget.TextView;
import androidx.core.view.ViewCompat;

/* renamed from: com.google.android.youtube.player.internal.n */
public final class C2808n extends FrameLayout {

    /* renamed from: a */
    private final ProgressBar f1683a;

    /* renamed from: b */
    private final TextView f1684b;

    public C2808n(Context context) {
        super(context, null, C2838z.m1756c(context));
        C2807m mVar = new C2807m(context);
        setBackgroundColor(ViewCompat.MEASURED_STATE_MASK);
        this.f1683a = new ProgressBar(context);
        this.f1683a.setVisibility(8);
        addView(this.f1683a, new LayoutParams(-2, -2, 17));
        int i = (int) ((context.getResources().getDisplayMetrics().density * 10.0f) + 0.5f);
        this.f1684b = new TextView(context);
        this.f1684b.setTextAppearance(context, 16973894);
        this.f1684b.setTextColor(-1);
        this.f1684b.setVisibility(8);
        this.f1684b.setPadding(i, i, i, i);
        this.f1684b.setGravity(17);
        this.f1684b.setText(mVar.f1673a);
        addView(this.f1684b, new LayoutParams(-2, -2, 17));
    }

    /* renamed from: a */
    public final void mo38158a() {
        this.f1683a.setVisibility(8);
        this.f1684b.setVisibility(8);
    }

    /* renamed from: b */
    public final void mo38159b() {
        this.f1683a.setVisibility(0);
        this.f1684b.setVisibility(8);
    }

    /* renamed from: c */
    public final void mo38160c() {
        this.f1683a.setVisibility(8);
        this.f1684b.setVisibility(0);
    }

    /* access modifiers changed from: protected */
    public final void onMeasure(int i, int i2) {
        float f;
        int mode = MeasureSpec.getMode(i);
        int mode2 = MeasureSpec.getMode(i2);
        int size = MeasureSpec.getSize(i);
        int size2 = MeasureSpec.getSize(i2);
        if (!(mode == 1073741824 && mode2 == 1073741824)) {
            if (mode == 1073741824 || (mode == Integer.MIN_VALUE && mode2 == 0)) {
                size2 = (int) (((float) size) / 1.777f);
            } else {
                if (mode2 == 1073741824 || (mode2 == Integer.MIN_VALUE && mode == 0)) {
                    f = (float) size2;
                } else if (mode == Integer.MIN_VALUE && mode2 == Integer.MIN_VALUE) {
                    f = (float) size2;
                    float f2 = ((float) size) / 1.777f;
                    if (f >= f2) {
                        size2 = (int) f2;
                    }
                } else {
                    size = 0;
                    size2 = 0;
                }
                size = (int) (f * 1.777f);
            }
        }
        super.onMeasure(MeasureSpec.makeMeasureSpec(resolveSize(size, i), 1073741824), MeasureSpec.makeMeasureSpec(resolveSize(size2, i2), 1073741824));
    }
}
