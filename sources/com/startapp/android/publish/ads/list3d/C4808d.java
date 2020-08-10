package com.startapp.android.publish.ads.list3d;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.EmbossMaskFilter;
import android.graphics.Paint;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.GradientDrawable.Orientation;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.RoundRectShape;
import android.graphics.drawable.shapes.Shape;
import android.text.TextUtils.TruncateAt;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.startapp.android.publish.adsCommon.C4983b;
import com.startapp.android.publish.adsCommon.Utils.C4945h;
import com.startapp.android.publish.common.metaData.MetaDataStyle;
import com.startapp.android.publish.p065a.C4733b;
import com.startapp.common.p092a.C5146c;

/* renamed from: com.startapp.android.publish.ads.list3d.d */
/* compiled from: StartAppSDK */
public class C4808d {

    /* renamed from: a */
    private RelativeLayout f2653a;

    /* renamed from: b */
    private ImageView f2654b;

    /* renamed from: c */
    private TextView f2655c;

    /* renamed from: d */
    private TextView f2656d;

    /* renamed from: e */
    private TextView f2657e;

    /* renamed from: f */
    private C4733b f2658f;

    /* renamed from: g */
    private MetaDataStyle f2659g = null;

    public C4808d(Context context) {
        Context context2 = context;
        context2.setTheme(16973829);
        LayoutParams layoutParams = new LayoutParams(-2, -2);
        this.f2653a = new RelativeLayout(context2);
        this.f2653a.setBackgroundDrawable(new GradientDrawable(Orientation.TOP_BOTTOM, new int[]{C4983b.m3032a().mo62174n(), C4983b.m3032a().mo62175o()}));
        this.f2653a.setLayoutParams(layoutParams);
        int a = C4945h.m2891a(context2, 3);
        int a2 = C4945h.m2891a(context2, 4);
        int a3 = C4945h.m2891a(context2, 5);
        int a4 = C4945h.m2891a(context2, 6);
        int a5 = C4945h.m2891a(context2, 10);
        int a6 = C4945h.m2891a(context2, 84);
        this.f2653a.setPadding(a5, a, a5, a);
        this.f2653a.setTag(this);
        this.f2654b = new ImageView(context2);
        this.f2654b.setId(1);
        RelativeLayout.LayoutParams layoutParams2 = new RelativeLayout.LayoutParams(a6, a6);
        layoutParams2.addRule(15);
        this.f2654b.setLayoutParams(layoutParams2);
        this.f2654b.setPadding(0, 0, a4, 0);
        this.f2655c = new TextView(context2);
        this.f2655c.setId(2);
        RelativeLayout.LayoutParams layoutParams3 = new RelativeLayout.LayoutParams(-1, -2);
        layoutParams3.addRule(C5146c.m3740a(17), 1);
        layoutParams3.addRule(6, 1);
        this.f2655c.setLayoutParams(layoutParams3);
        this.f2655c.setPadding(0, 0, 0, a3);
        this.f2655c.setTextColor(C4983b.m3032a().mo62177q().intValue());
        this.f2655c.setTextSize((float) C4983b.m3032a().mo62176p().intValue());
        this.f2655c.setSingleLine(true);
        this.f2655c.setEllipsize(TruncateAt.END);
        C4945h.m2899a(this.f2655c, C4983b.m3032a().mo62178r());
        this.f2656d = new TextView(context2);
        this.f2656d.setId(3);
        RelativeLayout.LayoutParams layoutParams4 = new RelativeLayout.LayoutParams(-1, -2);
        layoutParams4.addRule(C5146c.m3740a(17), 1);
        layoutParams4.addRule(3, 2);
        layoutParams4.setMargins(0, 0, 0, a3);
        this.f2656d.setLayoutParams(layoutParams4);
        this.f2656d.setTextColor(C4983b.m3032a().mo62180t().intValue());
        this.f2656d.setTextSize((float) C4983b.m3032a().mo62179s().intValue());
        this.f2656d.setSingleLine(true);
        this.f2656d.setEllipsize(TruncateAt.END);
        C4945h.m2899a(this.f2656d, C4983b.m3032a().mo62181u());
        this.f2658f = new C4733b(context2);
        RelativeLayout.LayoutParams layoutParams5 = new RelativeLayout.LayoutParams(-2, -2);
        layoutParams5.addRule(C5146c.m3740a(17), 1);
        layoutParams5.addRule(8, 1);
        layoutParams5.setMargins(0, 0, 0, -a3);
        this.f2658f.setLayoutParams(layoutParams5);
        this.f2658f.setPadding(0, 0, 0, a2);
        this.f2658f.setId(5);
        this.f2657e = new TextView(context2);
        RelativeLayout.LayoutParams layoutParams6 = new RelativeLayout.LayoutParams(-2, -2);
        layoutParams6.addRule(C5146c.m3740a(21));
        layoutParams6.addRule(8, 1);
        this.f2657e.setLayoutParams(layoutParams6);
        mo61476a(false);
        this.f2657e.setTextColor(-1);
        this.f2657e.setTextSize(12.0f);
        this.f2657e.setTypeface(null, 1);
        this.f2657e.setPadding(a5, a4, a5, a4);
        this.f2657e.setId(4);
        this.f2657e.setShadowLayer(2.5f, -3.0f, 3.0f, -9013642);
        this.f2657e.setBackgroundDrawable(new ShapeDrawable(new RoundRectShape(new float[]{10.0f, 10.0f, 10.0f, 10.0f, 10.0f, 10.0f, 10.0f, 10.0f}, null, null)) {
            /* access modifiers changed from: protected */
            public void onDraw(Shape shape, Canvas canvas, Paint paint) {
                paint.setColor(-11363070);
                paint.setMaskFilter(new EmbossMaskFilter(new float[]{1.0f, 1.0f, 1.0f}, 0.4f, 5.0f, 3.0f));
                super.onDraw(shape, canvas, paint);
            }
        });
        this.f2653a.addView(this.f2654b);
        this.f2653a.addView(this.f2655c);
        this.f2653a.addView(this.f2656d);
        this.f2653a.addView(this.f2658f);
        this.f2653a.addView(this.f2657e);
    }

    /* renamed from: a */
    public RelativeLayout mo61474a() {
        return this.f2653a;
    }

    /* renamed from: b */
    public ImageView mo61477b() {
        return this.f2654b;
    }

    /* renamed from: c */
    public TextView mo61478c() {
        return this.f2655c;
    }

    /* renamed from: d */
    public TextView mo61479d() {
        return this.f2656d;
    }

    /* renamed from: e */
    public C4733b mo61480e() {
        return this.f2658f;
    }

    /* renamed from: a */
    public void mo61476a(boolean z) {
        if (z) {
            this.f2657e.setText("Open");
        } else {
            this.f2657e.setText("Download");
        }
    }

    /* renamed from: a */
    public void mo61475a(MetaDataStyle metaDataStyle) {
        if (this.f2659g != metaDataStyle) {
            this.f2659g = metaDataStyle;
            this.f2653a.setBackgroundDrawable(new GradientDrawable(Orientation.TOP_BOTTOM, new int[]{metaDataStyle.getItemGradientTop().intValue(), metaDataStyle.getItemGradientBottom().intValue()}));
            this.f2655c.setTextSize((float) metaDataStyle.getItemTitleTextSize().intValue());
            this.f2655c.setTextColor(metaDataStyle.getItemTitleTextColor().intValue());
            C4945h.m2899a(this.f2655c, metaDataStyle.getItemTitleTextDecoration());
            this.f2656d.setTextSize((float) metaDataStyle.getItemDescriptionTextSize().intValue());
            this.f2656d.setTextColor(metaDataStyle.getItemDescriptionTextColor().intValue());
            C4945h.m2899a(this.f2656d, metaDataStyle.getItemDescriptionTextDecoration());
        }
    }
}
