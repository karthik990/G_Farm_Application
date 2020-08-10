package com.startapp.android.publish.ads.video.p073c.p074a;

import android.content.Context;
import com.startapp.android.publish.ads.video.p073c.p074a.p075a.C4876b;
import java.util.Comparator;

/* renamed from: com.startapp.android.publish.ads.video.c.a.d */
/* compiled from: StartAppSDK */
public class C4884d extends C4881c {
    /* access modifiers changed from: private */

    /* renamed from: c */
    public final double f2890c;
    /* access modifiers changed from: private */

    /* renamed from: d */
    public final int f2891d = (this.f2886a * this.f2887b);
    /* access modifiers changed from: private */

    /* renamed from: e */
    public final int f2892e;

    /* access modifiers changed from: private */
    /* renamed from: b */
    public static int m2696b(int i, int i2) {
        if (i < i2) {
            return -1;
        }
        return i == i2 ? 0 : 1;
    }

    public C4884d(Context context, int i) {
        super(context);
        this.f2892e = i;
        double d = (double) this.f2886a;
        double d2 = (double) this.f2887b;
        Double.isNaN(d);
        Double.isNaN(d2);
        this.f2890c = d / d2;
    }

    /* access modifiers changed from: protected */
    /* renamed from: a */
    public Comparator<C4876b> mo61745a() {
        return new Comparator<C4876b>() {
            /* renamed from: a */
            public int compare(C4876b bVar, C4876b bVar2) {
                double doubleValue = C4884d.this.m2694a(bVar.mo61728d().intValue(), bVar.mo61730e().intValue(), C4884d.this.f2890c, C4884d.this.f2891d).doubleValue();
                double doubleValue2 = C4884d.this.m2694a(bVar2.mo61728d().intValue(), bVar2.mo61730e().intValue(), C4884d.this.f2890c, C4884d.this.f2891d).doubleValue();
                if (doubleValue < doubleValue2) {
                    return -1;
                }
                if (doubleValue > doubleValue2) {
                    return 1;
                }
                Integer c = bVar.mo61725c();
                Integer c2 = bVar2.mo61725c();
                if (c == null && c2 == null) {
                    return 0;
                }
                if (c == null) {
                    return 1;
                }
                if (c2 == null) {
                    return -1;
                }
                return C4884d.m2696b(Integer.valueOf(Math.abs(C4884d.this.f2892e - c.intValue())).intValue(), Integer.valueOf(Math.abs(C4884d.this.f2892e - c2.intValue())).intValue());
            }
        };
    }

    /* access modifiers changed from: private */
    /* renamed from: a */
    public Double m2694a(int i, int i2, double d, int i3) {
        double d2 = (double) i;
        double d3 = (double) i2;
        Double.isNaN(d2);
        Double.isNaN(d3);
        double d4 = (d2 / d3) / d;
        double d5 = (double) (i * i2);
        double d6 = (double) i3;
        Double.isNaN(d5);
        Double.isNaN(d6);
        return Double.valueOf((Math.abs(Math.log(d4)) * 40.0d) + (Math.abs(Math.log(d5 / d6)) * 60.0d));
    }
}
