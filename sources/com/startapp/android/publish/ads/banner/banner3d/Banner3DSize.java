package com.startapp.android.publish.ads.banner.banner3d;

import android.app.Activity;
import android.content.Context;
import android.graphics.Point;
import android.view.View;
import android.view.ViewParent;
import android.view.WindowManager;
import com.startapp.android.publish.ads.banner.Banner;
import com.startapp.android.publish.ads.banner.BannerOptions;
import com.startapp.android.publish.ads.banner.C4787d;
import com.startapp.android.publish.adsCommon.Utils.C4945h;
import com.startapp.android.publish.adsCommon.p082f.C5015d;
import com.startapp.android.publish.adsCommon.p082f.C5017f;
import com.startapp.common.p092a.C5155g;

/* compiled from: StartAppSDK */
public class Banner3DSize {

    /* compiled from: StartAppSDK */
    public enum Size {
        XXSMALL(new C4787d(280, 50)),
        XSMALL(new C4787d(300, 50)),
        SMALL(new C4787d(320, 50)),
        MEDIUM(new C4787d(468, 60)),
        LARGE(new C4787d(728, 90)),
        XLARGE(new C4787d(1024, 90));
        
        private C4787d size;

        private Size(C4787d dVar) {
            this.size = dVar;
        }

        public C4787d getSize() {
            return this.size;
        }
    }

    /* renamed from: a */
    public static boolean m2337a(Context context, ViewParent viewParent, BannerOptions bannerOptions, Banner3D banner3D, C4787d dVar) {
        Size[] values;
        String str = "Banner3DSize";
        C5155g.m3807a(str, 3, "============== Optimize Size ==========");
        C4787d a = m2335a(context, viewParent, bannerOptions, banner3D);
        dVar.mo61373a(a.mo61371a(), a.mo61374b());
        boolean z = false;
        for (Size size : Size.values()) {
            if (size.getSize().mo61371a() <= a.mo61371a() && size.getSize().mo61374b() <= a.mo61374b()) {
                StringBuilder sb = new StringBuilder();
                sb.append("BannerSize [");
                sb.append(size.getSize().mo61371a());
                sb.append(",");
                sb.append(size.getSize().mo61374b());
                sb.append("]");
                C5155g.m3807a(str, 3, sb.toString());
                bannerOptions.mo61272a(size.getSize().mo61371a(), size.getSize().mo61374b());
                z = true;
            }
        }
        if (!z) {
            bannerOptions.mo61272a(0, 0);
        }
        StringBuilder sb2 = new StringBuilder();
        sb2.append("============== Optimize Size [");
        sb2.append(z);
        sb2.append("] ==========");
        C5155g.m3807a(str, 3, sb2.toString());
        return z;
    }

    /* renamed from: a */
    private static C4787d m2335a(Context context, ViewParent viewParent, BannerOptions bannerOptions, Banner3D banner3D) {
        Point point = new Point();
        point.x = bannerOptions.mo61275d();
        point.y = bannerOptions.mo61276e();
        String str = "Banner3DSize";
        C5155g.m3807a(str, 3, "=============== set Application Size ===========");
        if (banner3D.getLayoutParams() != null && banner3D.getLayoutParams().width > 0) {
            point.x = C4945h.m2900b(context, banner3D.getLayoutParams().width + 1);
        }
        if (banner3D.getLayoutParams() != null && banner3D.getLayoutParams().height > 0) {
            point.y = C4945h.m2900b(context, banner3D.getLayoutParams().height + 1);
        }
        if (banner3D.getLayoutParams() == null || banner3D.getLayoutParams().width <= 0 || banner3D.getLayoutParams().height <= 0) {
            if (context instanceof Activity) {
                C5155g.m3807a(str, 3, "Context is Activity");
                View decorView = ((Activity) context).getWindow().getDecorView();
                try {
                    View view = (View) viewParent;
                    if (view instanceof Banner) {
                        C5155g.m3807a(str, 3, "Parent is instance of Wrapper Banner");
                        view = (View) view.getParent();
                    }
                    boolean z = false;
                    boolean z2 = false;
                    while (view != null && (view.getMeasuredWidth() <= 0 || view.getMeasuredHeight() <= 0)) {
                        if (view.getMeasuredWidth() > 0 && !z) {
                            m2338b(context, point, view);
                            z = true;
                        }
                        if (view.getMeasuredHeight() > 0 && !z2) {
                            m2336a(context, point, view);
                            z2 = true;
                        }
                        view = (View) view.getParent();
                    }
                    if (view == null) {
                        m2339c(context, point, decorView);
                    } else {
                        if (!z) {
                            m2338b(context, point, view);
                        }
                        if (!z2) {
                            m2336a(context, point, view);
                        }
                    }
                } catch (Exception unused) {
                    m2339c(context, point, decorView);
                    C5155g.m3807a(str, 3, "Exception occoured");
                }
            } else {
                C5155g.m3807a(str, 3, "Context not Activity, get max win size");
                try {
                    WindowManager windowManager = (WindowManager) context.getSystemService("window");
                    if (windowManager != null) {
                        C4945h.m2897a(context, windowManager, point);
                    }
                } catch (Exception e) {
                    C5017f.m3256a(context, C5015d.EXCEPTION, "Banner3DSize.getApplicationSize - system service failed", e.getMessage(), "");
                }
            }
        }
        StringBuilder sb = new StringBuilder();
        sb.append("============ exit Application Size [");
        sb.append(point.x);
        sb.append(",");
        sb.append(point.y);
        sb.append("] =========");
        C5155g.m3807a(str, 3, sb.toString());
        return new C4787d(point.x, point.y);
    }

    /* renamed from: a */
    private static void m2336a(Context context, Point point, View view) {
        point.y = C4945h.m2900b(context, (view.getMeasuredHeight() - view.getPaddingBottom()) - view.getPaddingTop());
    }

    /* renamed from: b */
    private static void m2338b(Context context, Point point, View view) {
        point.x = C4945h.m2900b(context, (view.getMeasuredWidth() - view.getPaddingLeft()) - view.getPaddingRight());
    }

    /* renamed from: c */
    private static void m2339c(Context context, Point point, View view) {
        point.x = C4945h.m2900b(context, view.getMeasuredWidth());
        point.y = C4945h.m2900b(context, view.getMeasuredHeight());
    }
}
