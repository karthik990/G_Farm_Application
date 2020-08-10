package com.google.firebase.firestore.remote;

import com.google.android.gms.tasks.OnFailureListener;
import p043io.grpc.CallCredentials2.MetadataApplier;

/* compiled from: com.google.firebase:firebase-firestore@@19.0.0 */
final /* synthetic */ class FirestoreCallCredentials$$Lambda$2 implements OnFailureListener {
    private final MetadataApplier arg$1;

    private FirestoreCallCredentials$$Lambda$2(MetadataApplier metadataApplier) {
        this.arg$1 = metadataApplier;
    }

    public static OnFailureListener lambdaFactory$(MetadataApplier metadataApplier) {
        return new FirestoreCallCredentials$$Lambda$2(metadataApplier);
    }

    public void onFailure(Exception exc) {
        FirestoreCallCredentials.lambda$applyRequestMetadata$1(this.arg$1, exc);
    }
}
