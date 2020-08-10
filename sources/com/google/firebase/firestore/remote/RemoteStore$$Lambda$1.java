package com.google.firebase.firestore.remote;

import com.google.firebase.firestore.core.OnlineState;
import com.google.firebase.firestore.remote.RemoteStore.RemoteStoreCallback;

/* compiled from: com.google.firebase:firebase-firestore@@19.0.0 */
final /* synthetic */ class RemoteStore$$Lambda$1 implements OnlineStateCallback {
    private final RemoteStoreCallback arg$1;

    private RemoteStore$$Lambda$1(RemoteStoreCallback remoteStoreCallback) {
        this.arg$1 = remoteStoreCallback;
    }

    public static OnlineStateCallback lambdaFactory$(RemoteStoreCallback remoteStoreCallback) {
        return new RemoteStore$$Lambda$1(remoteStoreCallback);
    }

    public void handleOnlineStateChange(OnlineState onlineState) {
        this.arg$1.handleOnlineStateChange(onlineState);
    }
}
