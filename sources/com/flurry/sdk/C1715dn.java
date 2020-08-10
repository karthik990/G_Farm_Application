package com.flurry.sdk;

import android.app.Activity;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import com.flurry.android.FlurryAgent;
import com.flurry.sdk.C1710dk.C1712a;
import java.util.HashMap;
import java.util.Map;

/* renamed from: com.flurry.sdk.dn */
public final class C1715dn {

    /* renamed from: h */
    private static C1715dn f966h;

    /* renamed from: a */
    C1702dh f967a;

    /* renamed from: b */
    Map<String, C1702dh> f968b = new HashMap();

    /* renamed from: c */
    public boolean f969c = false;

    /* renamed from: d */
    long f970d = 0;

    /* renamed from: e */
    long f971e = 0;

    /* renamed from: f */
    int f972f = 0;

    /* renamed from: g */
    boolean f973g = false;

    /* renamed from: i */
    private C1712a f974i;

    private C1715dn() {
    }

    /* renamed from: a */
    public static synchronized C1715dn m835a() {
        C1715dn dnVar;
        synchronized (C1715dn.class) {
            if (f966h == null) {
                f966h = new C1715dn();
            }
            dnVar = f966h;
        }
        return dnVar;
    }

    /* renamed from: b */
    public final void mo16441b() {
        if (this.f974i == null) {
            C1685cy.m754a(3, "ScreenTimeMonitor", "Register Screen Time metrics.");
            long nanoTime = System.nanoTime();
            this.f971e = nanoTime;
            this.f970d = nanoTime;
            this.f974i = new C1712a() {
                /* renamed from: a */
                public final void mo16412a(final Activity activity) {
                    StringBuilder sb = new StringBuilder("onActivityStarted for activity: ");
                    sb.append(activity.toString());
                    String str = "ScreenTimeMonitor";
                    C1685cy.m754a(3, str, sb.toString());
                    C1715dn dnVar = C1715dn.this;
                    dnVar.f967a = new C1702dh(activity.getClass().getSimpleName(), dnVar.f967a == null ? null : dnVar.f967a.f928b);
                    C1715dn.this.f968b.put(activity.toString(), C1715dn.this.f967a);
                    C1715dn dnVar2 = C1715dn.this;
                    int i = dnVar2.f972f + 1;
                    dnVar2.f972f = i;
                    if (i == 1 && !C1715dn.this.f973g) {
                        StringBuilder sb2 = new StringBuilder("onForeground for activity: ");
                        sb2.append(activity.toString());
                        C1685cy.m754a(3, str, sb2.toString());
                        long nanoTime = System.nanoTime();
                        double d = (double) (nanoTime - C1715dn.this.f971e);
                        Double.isNaN(d);
                        long j = (long) (d / 1000000.0d);
                        C1715dn dnVar3 = C1715dn.this;
                        dnVar3.f971e = nanoTime;
                        dnVar3.f970d = nanoTime;
                        if (dnVar3.f969c) {
                            C1715dn.m836a("fl.background.time", activity.getClass().getSimpleName(), j);
                        }
                    }
                    activity.getWindow().getDecorView().getViewTreeObserver().addOnGlobalLayoutListener(new OnGlobalLayoutListener() {
                        public final void onGlobalLayout() {
                            activity.getWindow().getDecorView().getViewTreeObserver().removeOnGlobalLayoutListener(this);
                            if (C1715dn.this.f969c && C1715dn.this.f967a != null) {
                                C1702dh dhVar = C1715dn.this.f967a;
                                double nanoTime = (double) (System.nanoTime() - C1715dn.this.f970d);
                                Double.isNaN(nanoTime);
                                dhVar.f934h = (long) (nanoTime / 1000000.0d);
                                StringBuilder sb = new StringBuilder("Start timed event for activity: ");
                                sb.append(C1715dn.this.f967a.f928b);
                                C1685cy.m754a(3, "ScreenTimeMonitor", sb.toString());
                                C1702dh dhVar2 = C1715dn.this.f967a;
                                if (!dhVar2.f932f) {
                                    StringBuilder sb2 = new StringBuilder("Start timed activity event: ");
                                    sb2.append(dhVar2.f928b);
                                    C1685cy.m754a(4, "ActivityScreenData", sb2.toString());
                                    String str = dhVar2.f927a;
                                    if (dhVar2.f929c != null) {
                                        dhVar2.f931e.put("fl.previous.screen", dhVar2.f929c);
                                    }
                                    dhVar2.f931e.put("fl.current.screen", dhVar2.f928b);
                                    dhVar2.f931e.put("fl.resume.time", Long.toString(dhVar2.f933g));
                                    dhVar2.f931e.put("fl.layout.time", Long.toString(dhVar2.f934h));
                                    FlurryAgent.logEvent(str, dhVar2.f931e, true);
                                    dhVar2.f932f = true;
                                }
                            }
                        }
                    });
                }

                /* renamed from: b */
                public final void mo16413b(Activity activity) {
                    if (C1715dn.this.f969c && C1715dn.this.f967a != null) {
                        C1702dh dhVar = C1715dn.this.f967a;
                        double nanoTime = (double) (System.nanoTime() - C1715dn.this.f970d);
                        Double.isNaN(nanoTime);
                        dhVar.f933g = (long) (nanoTime / 1000000.0d);
                    }
                }

                /* renamed from: a */
                public final void mo16411a() {
                    C1715dn.this.f970d = System.nanoTime();
                }

                /* renamed from: c */
                public final void mo16414c(Activity activity) {
                    C1702dh dhVar = (C1702dh) C1715dn.this.f968b.remove(activity.toString());
                    C1715dn.this.f973g = activity.isChangingConfigurations();
                    C1715dn dnVar = C1715dn.this;
                    int i = dnVar.f972f - 1;
                    dnVar.f972f = i;
                    String str = "ScreenTimeMonitor";
                    if (i == 0 && !C1715dn.this.f973g) {
                        StringBuilder sb = new StringBuilder("onBackground for activity: ");
                        sb.append(activity.toString());
                        C1685cy.m754a(3, str, sb.toString());
                        long nanoTime = System.nanoTime();
                        double d = (double) (nanoTime - C1715dn.this.f971e);
                        Double.isNaN(d);
                        long j = (long) (d / 1000000.0d);
                        C1715dn dnVar2 = C1715dn.this;
                        dnVar2.f971e = nanoTime;
                        if (dnVar2.f969c) {
                            C1715dn.m836a("fl.foreground.time", activity.getClass().getSimpleName(), j);
                        }
                    }
                    if (C1715dn.this.f969c && dhVar != null) {
                        StringBuilder sb2 = new StringBuilder("End timed event: ");
                        sb2.append(dhVar.f928b);
                        C1685cy.m754a(3, str, sb2.toString());
                        if (dhVar.f932f) {
                            StringBuilder sb3 = new StringBuilder("End timed activity event: ");
                            sb3.append(dhVar.f928b);
                            C1685cy.m754a(4, "ActivityScreenData", sb3.toString());
                            String str2 = dhVar.f927a;
                            double nanoTime2 = (double) (System.nanoTime() - dhVar.f930d);
                            Double.isNaN(nanoTime2);
                            String str3 = "fl.duration";
                            dhVar.f931e.put(str3, Long.toString((long) (nanoTime2 / 1000000.0d)));
                            FlurryAgent.endTimedEvent(str2, dhVar.f931e);
                            dhVar.f932f = false;
                        }
                    }
                }
            };
            C1710dk.m827a().mo16433a(this.f974i);
        }
    }

    /* renamed from: a */
    static /* synthetic */ void m836a(String str, String str2, long j) {
        HashMap hashMap = new HashMap();
        hashMap.put("fl.current.screen", str2);
        hashMap.put(str, Long.toString(j));
        FlurryAgent.logEvent("Flurry.ForegroundTime", (Map<String, String>) hashMap);
    }
}
