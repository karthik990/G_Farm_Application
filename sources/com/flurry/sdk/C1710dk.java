package com.flurry.sdk;

import android.app.Activity;
import android.app.Application;
import android.app.Application.ActivityLifecycleCallbacks;
import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import java.util.ArrayList;
import java.util.List;

/* renamed from: com.flurry.sdk.dk */
public final class C1710dk {

    /* renamed from: b */
    private static C1710dk f959b;

    /* renamed from: a */
    final List<C1712a> f960a = new ArrayList();

    /* renamed from: c */
    private ActivityLifecycleCallbacks f961c;

    /* renamed from: com.flurry.sdk.dk$a */
    public interface C1712a {
        /* renamed from: a */
        void mo16411a();

        /* renamed from: a */
        void mo16412a(Activity activity);

        /* renamed from: b */
        void mo16413b(Activity activity);

        /* renamed from: c */
        void mo16414c(Activity activity);
    }

    private C1710dk() {
    }

    /* renamed from: a */
    public static synchronized C1710dk m827a() {
        C1710dk dkVar;
        synchronized (C1710dk.class) {
            if (f959b == null) {
                f959b = new C1710dk();
            }
            dkVar = f959b;
        }
        return dkVar;
    }

    /* renamed from: a */
    public final void mo16433a(C1712a aVar) {
        synchronized (this.f960a) {
            this.f960a.add(aVar);
        }
    }

    /* renamed from: a */
    public final void mo16432a(Context context, Cursor cursor) {
        if (this.f961c == null && context != null) {
            Context applicationContext = context.getApplicationContext();
            if (applicationContext instanceof Application) {
                C1703di.m811a().mo16408a(applicationContext, cursor);
                C1715dn.m835a().mo16441b();
                this.f961c = new ActivityLifecycleCallbacks() {
                    public final void onActivityCreated(Activity activity, Bundle bundle) {
                    }

                    public final void onActivityDestroyed(Activity activity) {
                    }

                    public final void onActivitySaveInstanceState(Activity activity, Bundle bundle) {
                    }

                    public final void onActivityStarted(Activity activity) {
                        StringBuilder sb = new StringBuilder("onActivityStarted for activity: ");
                        sb.append(activity.toString());
                        C1685cy.m754a(3, "PerformanceMonitor", sb.toString());
                        for (C1712a a : C1710dk.this.f960a) {
                            a.mo16412a(activity);
                        }
                    }

                    public final void onActivityResumed(Activity activity) {
                        StringBuilder sb = new StringBuilder("onActivityResumed for activity: ");
                        sb.append(activity.toString());
                        C1685cy.m754a(3, "PerformanceMonitor", sb.toString());
                        for (C1712a b : C1710dk.this.f960a) {
                            b.mo16413b(activity);
                        }
                    }

                    public final void onActivityPaused(Activity activity) {
                        StringBuilder sb = new StringBuilder("onActivityPaused for activity: ");
                        sb.append(activity.toString());
                        C1685cy.m754a(3, "PerformanceMonitor", sb.toString());
                        for (C1712a a : C1710dk.this.f960a) {
                            a.mo16411a();
                        }
                    }

                    public final void onActivityStopped(Activity activity) {
                        StringBuilder sb = new StringBuilder("onActivityStopped for activity: ");
                        sb.append(activity.toString());
                        C1685cy.m754a(3, "PerformanceMonitor", sb.toString());
                        for (C1712a c : C1710dk.this.f960a) {
                            c.mo16414c(activity);
                        }
                    }
                };
                ((Application) applicationContext).registerActivityLifecycleCallbacks(this.f961c);
            }
        }
    }
}
