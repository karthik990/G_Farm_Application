package com.google.android.gms.common.api.internal;

import android.app.Activity;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

public final class zaa extends ActivityLifecycleObserver {
    private final WeakReference<C6211zaa> zacl;

    /* renamed from: com.google.android.gms.common.api.internal.zaa$zaa reason: collision with other inner class name */
    static class C6211zaa extends LifecycleCallback {
        private List<Runnable> zacm = new ArrayList();

        /* access modifiers changed from: private */
        public static C6211zaa zaa(Activity activity) {
            C6211zaa zaa;
            synchronized (activity) {
                LifecycleFragment fragment = getFragment(activity);
                zaa = (C6211zaa) fragment.getCallbackOrNull("LifecycleObserverOnStop", C6211zaa.class);
                if (zaa == null) {
                    zaa = new C6211zaa(fragment);
                }
            }
            return zaa;
        }

        private C6211zaa(LifecycleFragment lifecycleFragment) {
            super(lifecycleFragment);
            this.mLifecycleFragment.addCallback("LifecycleObserverOnStop", this);
        }

        /* access modifiers changed from: private */
        public final synchronized void zaa(Runnable runnable) {
            this.zacm.add(runnable);
        }

        public void onStop() {
            List<Runnable> list;
            synchronized (this) {
                list = this.zacm;
                this.zacm = new ArrayList();
            }
            for (Runnable run : list) {
                run.run();
            }
        }
    }

    public zaa(Activity activity) {
        this(C6211zaa.zaa(activity));
    }

    private zaa(C6211zaa zaa) {
        this.zacl = new WeakReference<>(zaa);
    }

    public final ActivityLifecycleObserver onStopCallOnce(Runnable runnable) {
        C6211zaa zaa = (C6211zaa) this.zacl.get();
        if (zaa != null) {
            zaa.zaa(runnable);
            return this;
        }
        throw new IllegalStateException("The target activity has already been GC'd");
    }
}
