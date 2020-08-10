package com.firebase.p037ui.database.paging;

import androidx.lifecycle.GeneratedAdapter;
import androidx.lifecycle.Lifecycle.Event;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.MethodCallsLogger;

/* renamed from: com.firebase.ui.database.paging.FirebaseRecyclerPagingAdapter_LifecycleAdapter */
public class FirebaseRecyclerPagingAdapter_LifecycleAdapter implements GeneratedAdapter {
    final FirebaseRecyclerPagingAdapter mReceiver;

    FirebaseRecyclerPagingAdapter_LifecycleAdapter(FirebaseRecyclerPagingAdapter firebaseRecyclerPagingAdapter) {
        this.mReceiver = firebaseRecyclerPagingAdapter;
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
