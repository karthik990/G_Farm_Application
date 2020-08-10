package com.firebase.p037ui.firestore.paging;

import androidx.lifecycle.GeneratedAdapter;
import androidx.lifecycle.Lifecycle.Event;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.MethodCallsLogger;

/* renamed from: com.firebase.ui.firestore.paging.FirestorePagingAdapter_LifecycleAdapter */
public class FirestorePagingAdapter_LifecycleAdapter implements GeneratedAdapter {
    final FirestorePagingAdapter mReceiver;

    FirestorePagingAdapter_LifecycleAdapter(FirestorePagingAdapter firestorePagingAdapter) {
        this.mReceiver = firestorePagingAdapter;
    }

    public void callMethods(LifecycleOwner lifecycleOwner, Event event, boolean z, MethodCallsLogger methodCallsLogger) {
        boolean z2 = methodCallsLogger != null;
        if (!z) {
            if (event == Event.ON_START) {
                if (!z2 || methodCallsLogger.approveCall("startListening", 1)) {
                    this.mReceiver.startListening();
                }
                return;
            }
            if (event == Event.ON_STOP && (!z2 || methodCallsLogger.approveCall("stopListening", 1))) {
                this.mReceiver.stopListening();
            }
        }
    }
}
