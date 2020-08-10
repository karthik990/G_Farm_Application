package com.flurry.sdk;

import android.app.Activity;
import android.app.ActivityManager.MemoryInfo;
import android.content.Context;
import android.database.Cursor;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import com.flurry.android.FlurryAgent;
import com.flurry.sdk.C1710dk.C1712a;
import java.util.HashMap;
import java.util.Map;

/* renamed from: com.flurry.sdk.di */
public final class C1703di {

    /* renamed from: d */
    private static C1703di f935d;

    /* renamed from: e */
    private static final long f936e = System.nanoTime();

    /* renamed from: a */
    public boolean f937a = false;

    /* renamed from: b */
    public boolean f938b = false;

    /* renamed from: c */
    public boolean f939c = false;

    /* renamed from: f */
    private long f940f;

    /* renamed from: g */
    private long f941g;

    /* renamed from: h */
    private long f942h;

    /* renamed from: i */
    private Map<String, String> f943i = new HashMap();

    /* renamed from: j */
    private C1712a f944j;

    private C1703di() {
    }

    /* renamed from: a */
    public static synchronized C1703di m811a() {
        C1703di diVar;
        synchronized (C1703di.class) {
            if (f935d == null) {
                f935d = new C1703di();
            }
            diVar = f935d;
        }
        return diVar;
    }

    /* renamed from: a */
    public final void mo16408a(Context context, Cursor cursor) {
        if (this.f944j == null) {
            boolean z = true;
            if (cursor != null) {
                cursor.moveToFirst();
                this.f940f = cursor.getLong(0);
                this.f941g = cursor.getLong(1);
                this.f942h = cursor.getLong(2);
                cursor.close();
            } else {
                Runtime runtime = Runtime.getRuntime();
                MemoryInfo a = C1713dl.m834a(context);
                this.f940f = f936e;
                this.f941g = runtime.totalMemory() - runtime.freeMemory();
                this.f942h = a.totalMem - a.availMem;
            }
            StringBuilder sb = new StringBuilder("Registered with Content Provider: ");
            if (cursor == null) {
                z = false;
            }
            sb.append(z);
            sb.append(", start time: ");
            sb.append(this.f940f);
            sb.append(", runtime memory: ");
            sb.append(this.f941g);
            sb.append(", system memory: ");
            sb.append(this.f942h);
            C1685cy.m754a(3, "ColdStartMonitor", sb.toString());
            this.f944j = new C1712a() {
                /* renamed from: a */
                public final void mo16411a() {
                }

                /* renamed from: c */
                public final void mo16414c(Activity activity) {
                }

                /* renamed from: a */
                public final void mo16412a(final Activity activity) {
                    activity.getWindow().getDecorView().getViewTreeObserver().addOnGlobalLayoutListener(new OnGlobalLayoutListener() {
                        public final void onGlobalLayout() {
                            activity.getWindow().getDecorView().getViewTreeObserver().removeOnGlobalLayoutListener(this);
                            C1703di diVar = C1703di.this;
                            activity.getApplication();
                            C1703di.m812a(diVar);
                            C1703di.this.mo16409a(activity, "onGlobalLayout", "fl.layout.time", "fl.layout.runtime.memory", "fl.layout.system.memory");
                            C1703di.this.f938b = true;
                            if (C1703di.this.f937a) {
                                C1703di.this.mo16410b();
                            }
                        }
                    });
                }

                /* renamed from: b */
                public final void mo16413b(Activity activity) {
                    C1703di.this.mo16409a(activity, "onActivityResumed", "fl.resume.time", "fl.resume.runtime.memory", "fl.resume.system.memory");
                }
            };
            C1710dk.m827a().mo16433a(this.f944j);
        }
    }

    /* renamed from: b */
    public final synchronized void mo16410b() {
        if (!this.f943i.isEmpty()) {
            String str = "ColdStartMonitor";
            StringBuilder sb = new StringBuilder("Log Cold Start time event: ");
            sb.append(this.f943i);
            C1685cy.m754a(4, str, sb.toString());
            FlurryAgent.logEvent("Flurry.ColdStartTime", this.f943i);
            this.f943i.clear();
        }
    }

    /* renamed from: a */
    public final void mo16409a(Context context, String str, String str2, String str3, String str4) {
        double nanoTime = (double) (System.nanoTime() - this.f940f);
        Double.isNaN(nanoTime);
        long j = (long) (nanoTime / 1000000.0d);
        Runtime runtime = Runtime.getRuntime();
        long freeMemory = runtime.totalMemory() - runtime.freeMemory();
        long j2 = freeMemory - this.f941g;
        if (j2 < 0) {
            j2 = 0;
        }
        MemoryInfo a = C1713dl.m834a(context);
        long j3 = a.totalMem - a.availMem;
        long j4 = j3 - this.f942h;
        if (j4 < 0) {
            j4 = 0;
        }
        StringBuilder sb = new StringBuilder();
        sb.append(str);
        sb.append(" time: ");
        sb.append(j);
        sb.append(", runtime memory usage: ");
        sb.append(freeMemory);
        sb.append(", system memory usage: ");
        sb.append(j3);
        C1685cy.m754a(3, "ColdStartMonitor", sb.toString());
        this.f943i.put(str2, Long.toString(j));
        this.f943i.put(str3, Long.toString(j2));
        this.f943i.put(str4, Long.toString(j4));
    }

    /* renamed from: a */
    static /* synthetic */ void m812a(C1703di diVar) {
        if (diVar.f944j != null) {
            C1710dk a = C1710dk.m827a();
            C1712a aVar = diVar.f944j;
            synchronized (a.f960a) {
                a.f960a.remove(aVar);
            }
            diVar.f944j = null;
        }
    }
}
