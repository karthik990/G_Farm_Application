package com.firebase.p037ui.firestore;

import androidx.lifecycle.GeneratedAdapter;
import androidx.lifecycle.Lifecycle.Event;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.MethodCallsLogger;

/* renamed from: com.firebase.ui.firestore.FirestoreRecyclerAdapter_LifecycleAdapter */
public class FirestoreRecyclerAdapter_LifecycleAdapter implements GeneratedAdapter {
    final FirestoreRecyclerAdapter mReceiver;

    FirestoreRecyclerAdapter_LifecycleAdapter(FirestoreRecyclerAdapter firestoreRecyclerAdapter) {
        this.mReceiver = firestoreRecyclerAdapter;
    }

    public void callMethods(LifecycleOwner lifecycleOwner, Event event, boolean z, MethodCallsLogger methodCallsLogger) {
        boolean z2 = methodCallsLogger != null;
        if (!z) {
            if (event == Event.ON_START) {
                if (!z2 || methodCallsLogger.approveCall("startListening", 1)) {
                    this.mReceiver.startListening();
                }
            } else if (event == Event.ON_STOP) {
                if (!z2 || methodCallsLogger.approveCall("stopListening", 1)) {
                    this.mReceiver.stopListening();
                }
            } else {
                if (event == Event.ON_DESTROY && (!z2 || methodCallsLogger.approveCall("cleanup", 2))) {
                    this.mReceiver.cleanup(lifecycleOwner);
                }
            }
        }
    }
}
