package com.firebase.p037ui.database;

import androidx.lifecycle.GeneratedAdapter;
import androidx.lifecycle.Lifecycle.Event;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.MethodCallsLogger;

/* renamed from: com.firebase.ui.database.FirebaseListAdapter_LifecycleAdapter */
public class FirebaseListAdapter_LifecycleAdapter implements GeneratedAdapter {
    final FirebaseListAdapter mReceiver;

    FirebaseListAdapter_LifecycleAdapter(FirebaseListAdapter firebaseListAdapter) {
        this.mReceiver = firebaseListAdapter;
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
