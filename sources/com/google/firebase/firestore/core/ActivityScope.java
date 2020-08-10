package com.google.firebase.firestore.core;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import androidx.fragment.app.FragmentActivity;
import com.google.firebase.firestore.ListenerRegistration;
import com.google.firebase.firestore.util.Assert;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Nullable;

/* compiled from: com.google.firebase:firebase-firestore@@19.0.0 */
public class ActivityScope {
    private static final String FRAGMENT_TAG = "FirestoreOnStopObserverFragment";
    private static final String SUPPORT_FRAGMENT_TAG = "FirestoreOnStopObserverSupportFragment";

    /* compiled from: com.google.firebase:firebase-firestore@@19.0.0 */
    private static class CallbackList {
        private final List<Runnable> callbacks;

        private CallbackList() {
            this.callbacks = new ArrayList();
        }

        /* access modifiers changed from: 0000 */
        public void run() {
            for (Runnable runnable : this.callbacks) {
                if (runnable != null) {
                    runnable.run();
                }
            }
        }

        /* access modifiers changed from: 0000 */
        public synchronized void add(Runnable runnable) {
            this.callbacks.add(runnable);
        }
    }

    /* compiled from: com.google.firebase:firebase-firestore@@19.0.0 */
    public static class StopListenerFragment extends Fragment {
        CallbackList callbacks = new CallbackList();

        public void onStop() {
            CallbackList callbackList;
            super.onStop();
            synchronized (this.callbacks) {
                callbackList = this.callbacks;
                this.callbacks = new CallbackList();
            }
            callbackList.run();
        }
    }

    /* compiled from: com.google.firebase:firebase-firestore@@19.0.0 */
    public static class StopListenerSupportFragment extends androidx.fragment.app.Fragment {
        CallbackList callbacks = new CallbackList();

        public void onStop() {
            CallbackList callbackList;
            super.onStop();
            synchronized (this.callbacks) {
                callbackList = this.callbacks;
                this.callbacks = new CallbackList();
            }
            callbackList.run();
        }
    }

    @Nullable
    private static <T> T castFragment(Class<T> cls, @Nullable Object obj, String str) {
        if (obj == null) {
            return null;
        }
        try {
            return cls.cast(obj);
        } catch (ClassCastException unused) {
            StringBuilder sb = new StringBuilder();
            sb.append("Fragment with tag '");
            sb.append(str);
            sb.append("' is a ");
            sb.append(obj.getClass().getName());
            sb.append(" but should be a ");
            sb.append(cls.getName());
            throw new IllegalStateException(sb.toString());
        }
    }

    private static void onActivityStopCallOnce(Activity activity, Runnable runnable) {
        Assert.hardAssert(!(activity instanceof FragmentActivity), "onActivityStopCallOnce must be called with a *non*-FragmentActivity Activity.", new Object[0]);
        activity.runOnUiThread(ActivityScope$$Lambda$1.lambdaFactory$(activity, runnable));
    }

    static /* synthetic */ void lambda$onActivityStopCallOnce$0(Activity activity, Runnable runnable) {
        FragmentManager fragmentManager = activity.getFragmentManager();
        String str = FRAGMENT_TAG;
        StopListenerFragment stopListenerFragment = (StopListenerFragment) castFragment(StopListenerFragment.class, fragmentManager.findFragmentByTag(str), str);
        if (stopListenerFragment == null || stopListenerFragment.isRemoving()) {
            stopListenerFragment = new StopListenerFragment();
            activity.getFragmentManager().beginTransaction().add(stopListenerFragment, str).commitAllowingStateLoss();
            activity.getFragmentManager().executePendingTransactions();
        }
        stopListenerFragment.callbacks.add(runnable);
    }

    private static void onFragmentActivityStopCallOnce(FragmentActivity fragmentActivity, Runnable runnable) {
        fragmentActivity.runOnUiThread(ActivityScope$$Lambda$2.lambdaFactory$(fragmentActivity, runnable));
    }

    static /* synthetic */ void lambda$onFragmentActivityStopCallOnce$1(FragmentActivity fragmentActivity, Runnable runnable) {
        androidx.fragment.app.FragmentManager supportFragmentManager = fragmentActivity.getSupportFragmentManager();
        String str = SUPPORT_FRAGMENT_TAG;
        StopListenerSupportFragment stopListenerSupportFragment = (StopListenerSupportFragment) castFragment(StopListenerSupportFragment.class, supportFragmentManager.findFragmentByTag(str), str);
        if (stopListenerSupportFragment == null || stopListenerSupportFragment.isRemoving()) {
            stopListenerSupportFragment = new StopListenerSupportFragment();
            fragmentActivity.getSupportFragmentManager().beginTransaction().add((androidx.fragment.app.Fragment) stopListenerSupportFragment, str).commitAllowingStateLoss();
            fragmentActivity.getSupportFragmentManager().executePendingTransactions();
        }
        stopListenerSupportFragment.callbacks.add(runnable);
    }

    public static ListenerRegistration bind(@Nullable Activity activity, ListenerRegistration listenerRegistration) {
        if (activity != null) {
            if (activity instanceof FragmentActivity) {
                FragmentActivity fragmentActivity = (FragmentActivity) activity;
                listenerRegistration.getClass();
                onFragmentActivityStopCallOnce(fragmentActivity, ActivityScope$$Lambda$3.lambdaFactory$(listenerRegistration));
            } else {
                listenerRegistration.getClass();
                onActivityStopCallOnce(activity, ActivityScope$$Lambda$4.lambdaFactory$(listenerRegistration));
            }
        }
        return listenerRegistration;
    }
}
