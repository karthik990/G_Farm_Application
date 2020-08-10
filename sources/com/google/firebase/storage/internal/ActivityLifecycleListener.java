package com.google.firebase.storage.internal;

import android.app.Activity;
import android.util.Log;
import com.google.android.gms.common.api.internal.LifecycleActivity;
import com.google.android.gms.common.api.internal.LifecycleCallback;
import com.google.android.gms.common.api.internal.LifecycleFragment;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/* compiled from: com.google.firebase:firebase-storage@@17.0.0 */
public class ActivityLifecycleListener {
    private static final ActivityLifecycleListener instance = new ActivityLifecycleListener();
    private final Map<Object, LifecycleEntry> cookieMap = new HashMap();
    private final Object sync = new Object();

    /* compiled from: com.google.firebase:firebase-storage@@17.0.0 */
    private static class LifecycleEntry {
        private final Activity activity;
        private final Object cookie;
        private final Runnable runnable;

        public LifecycleEntry(Activity activity2, Runnable runnable2, Object obj) {
            this.activity = activity2;
            this.runnable = runnable2;
            this.cookie = obj;
        }

        public int hashCode() {
            return this.cookie.hashCode();
        }

        public boolean equals(Object obj) {
            boolean z = false;
            if (!(obj instanceof LifecycleEntry)) {
                return false;
            }
            LifecycleEntry lifecycleEntry = (LifecycleEntry) obj;
            if (lifecycleEntry.cookie.equals(this.cookie) && lifecycleEntry.runnable == this.runnable && lifecycleEntry.activity == this.activity) {
                z = true;
            }
            return z;
        }

        public Activity getActivity() {
            return this.activity;
        }

        public Runnable getRunnable() {
            return this.runnable;
        }

        public Object getCookie() {
            return this.cookie;
        }
    }

    /* compiled from: com.google.firebase:firebase-storage@@17.0.0 */
    private static class OnStopCallback extends LifecycleCallback {
        private static final String TAG = "StorageOnStopCallback";
        private final List<LifecycleEntry> listeners = new ArrayList();

        private OnStopCallback(LifecycleFragment lifecycleFragment) {
            super(lifecycleFragment);
            this.mLifecycleFragment.addCallback(TAG, this);
        }

        public static OnStopCallback getInstance(Activity activity) {
            LifecycleFragment fragment = getFragment(new LifecycleActivity(activity));
            OnStopCallback onStopCallback = (OnStopCallback) fragment.getCallbackOrNull(TAG, OnStopCallback.class);
            return onStopCallback == null ? new OnStopCallback(fragment) : onStopCallback;
        }

        public void addEntry(LifecycleEntry lifecycleEntry) {
            synchronized (this.listeners) {
                this.listeners.add(lifecycleEntry);
            }
        }

        public void removeEntry(LifecycleEntry lifecycleEntry) {
            synchronized (this.listeners) {
                this.listeners.remove(lifecycleEntry);
            }
        }

        public void onStop() {
            ArrayList arrayList;
            synchronized (this.listeners) {
                arrayList = new ArrayList(this.listeners);
                this.listeners.clear();
            }
            Iterator it = arrayList.iterator();
            while (it.hasNext()) {
                LifecycleEntry lifecycleEntry = (LifecycleEntry) it.next();
                if (lifecycleEntry != null) {
                    Log.d(TAG, "removing subscription from activity.");
                    lifecycleEntry.getRunnable().run();
                    ActivityLifecycleListener.getInstance().removeCookie(lifecycleEntry.getCookie());
                }
            }
        }
    }

    private ActivityLifecycleListener() {
    }

    public static ActivityLifecycleListener getInstance() {
        return instance;
    }

    public void runOnActivityStopped(Activity activity, Object obj, Runnable runnable) {
        synchronized (this.sync) {
            LifecycleEntry lifecycleEntry = new LifecycleEntry(activity, runnable, obj);
            OnStopCallback.getInstance(activity).addEntry(lifecycleEntry);
            this.cookieMap.put(obj, lifecycleEntry);
        }
    }

    public void removeCookie(Object obj) {
        synchronized (this.sync) {
            LifecycleEntry lifecycleEntry = (LifecycleEntry) this.cookieMap.get(obj);
            if (lifecycleEntry != null) {
                OnStopCallback.getInstance(lifecycleEntry.getActivity()).removeEntry(lifecycleEntry);
            }
        }
    }
}
