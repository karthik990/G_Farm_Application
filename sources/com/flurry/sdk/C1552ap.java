package com.flurry.sdk;

import android.app.Activity;
import android.app.Application;
import android.app.Application.ActivityLifecycleCallbacks;
import android.content.ComponentCallbacks2;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import com.flurry.sdk.C1550ao.C1551a;
import java.util.HashSet;
import java.util.Set;

/* renamed from: com.flurry.sdk.ap */
public final class C1552ap extends C1942m<C1550ao> implements ActivityLifecycleCallbacks, ComponentCallbacks2 {

    /* renamed from: d */
    private static String f539d;

    /* renamed from: a */
    public int f540a = 0;

    /* renamed from: b */
    private final Set<String> f541b;

    public final void onActivityCreated(Activity activity, Bundle bundle) {
        m458a(activity, C1551a.CREATED);
        synchronized (this) {
            if (f539d == null) {
                f539d = activity.getClass().getName();
            }
        }
    }

    public final void onActivityStarted(Activity activity) {
        this.f541b.add(activity.toString());
        m458a(activity, C1551a.STARTED);
    }

    public final void onActivityResumed(Activity activity) {
        m458a(activity, C1551a.RESUMED);
    }

    public final void onActivityPaused(Activity activity) {
        m458a(activity, C1551a.PAUSED);
    }

    public final void onActivityStopped(Activity activity) {
        this.f541b.remove(activity.toString());
        m458a(activity, C1551a.STOPPED);
        if (this.f541b.isEmpty()) {
            m458a(activity, C1551a.APP_BACKGROUND);
        }
    }

    public final void onActivitySaveInstanceState(Activity activity, Bundle bundle) {
        m458a(activity, C1551a.SAVE_STATE);
    }

    public final void onActivityDestroyed(Activity activity) {
        m458a(activity, C1551a.DESTROYED);
    }

    public final void onTrimMemory(int i) {
        m457a(i);
    }

    public final void onConfigurationChanged(Configuration configuration) {
        if (this.f540a != configuration.orientation) {
            this.f540a = configuration.orientation;
            Bundle bundle = new Bundle();
            bundle.putInt("orientation_name", this.f540a);
            notifyObservers(new C1550ao(C1551a.APP_ORIENTATION_CHANGE, bundle));
        }
    }

    public final void onLowMemory() {
        m457a(80);
    }

    public C1552ap() {
        String str = "ApplicationLifecycleProvider";
        super(str);
        Application application = (Application) C1576b.m502a();
        if (application != null) {
            this.f540a = application.getResources().getConfiguration().orientation;
            application.registerActivityLifecycleCallbacks(this);
            application.registerComponentCallbacks(this);
        } else {
            C1685cy.m754a(6, str, "Context is null when initializing.");
        }
        this.f541b = new HashSet();
    }

    public final void destroy() {
        super.destroy();
        Application application = (Application) C1576b.m502a();
        application.unregisterActivityLifecycleCallbacks(this);
        application.unregisterComponentCallbacks(this);
    }

    /* renamed from: a */
    private void m458a(Activity activity, C1551a aVar) {
        Bundle bundle = new Bundle();
        bundle.putString("activity_name", activity.getLocalClassName());
        if (C1551a.CREATED.equals(aVar)) {
            Intent intent = activity.getIntent();
            if (intent != null) {
                Bundle extras = intent.getExtras();
                if (extras != null) {
                    bundle.putBundle("launch_options", extras);
                }
            }
        }
        notifyObservers(new C1550ao(aVar, bundle));
    }

    /* renamed from: a */
    private void m457a(int i) {
        Bundle bundle = new Bundle();
        bundle.putInt("trim_memory_level", i);
        notifyObservers(new C1550ao(C1551a.TRIM_MEMORY, bundle));
    }
}
