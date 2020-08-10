package com.google.firebase.firestore;

import android.content.Context;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.internal.InternalAuthProvider;
import java.util.HashMap;
import java.util.Map;

/* compiled from: com.google.firebase:firebase-firestore@@19.0.0 */
class FirestoreMultiDbComponent {
    private final FirebaseApp app;
    private final InternalAuthProvider authProvider;
    private final Context context;
    private final Map<String, FirebaseFirestore> instances = new HashMap();

    FirestoreMultiDbComponent(Context context2, FirebaseApp firebaseApp, InternalAuthProvider internalAuthProvider) {
        this.context = context2;
        this.app = firebaseApp;
        this.authProvider = internalAuthProvider;
    }

    /* access modifiers changed from: 0000 */
    public synchronized FirebaseFirestore get(String str) {
        FirebaseFirestore firebaseFirestore;
        firebaseFirestore = (FirebaseFirestore) this.instances.get(str);
        if (firebaseFirestore == null) {
            firebaseFirestore = FirebaseFirestore.newInstance(this.context, this.app, this.authProvider, str);
            this.instances.put(str, firebaseFirestore);
        }
        return firebaseFirestore;
    }
}
