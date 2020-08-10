package com.google.firebase.firestore;

import android.content.Context;

/* compiled from: com.google.firebase:firebase-firestore@@19.0.0 */
final /* synthetic */ class FirebaseFirestore$$Lambda$1 implements Runnable {
    private final Context arg$1;

    private FirebaseFirestore$$Lambda$1(Context context) {
        this.arg$1 = context;
    }

    public static Runnable lambdaFactory$(Context context) {
        return new FirebaseFirestore$$Lambda$1(context);
    }

    public void run() {
        FirebaseFirestore.lambda$newInstance$0(this.arg$1);
    }
}
