package com.google.firebase.storage;

import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.internal.InternalAuthProvider;
import com.google.firebase.inject.Provider;
import java.util.HashMap;
import java.util.Map;

/* compiled from: com.google.firebase:firebase-storage@@17.0.0 */
class FirebaseStorageComponent {
    private final FirebaseApp app;
    private final Provider<InternalAuthProvider> authProvider;
    private final Map<String, FirebaseStorage> instances = new HashMap();

    FirebaseStorageComponent(FirebaseApp firebaseApp, Provider<InternalAuthProvider> provider) {
        this.app = firebaseApp;
        this.authProvider = provider;
    }

    /* access modifiers changed from: 0000 */
    public synchronized FirebaseStorage get(String str) {
        FirebaseStorage firebaseStorage;
        firebaseStorage = (FirebaseStorage) this.instances.get(str);
        if (firebaseStorage == null) {
            firebaseStorage = new FirebaseStorage(str, this.app, this.authProvider);
            this.instances.put(str, firebaseStorage);
        }
        return firebaseStorage;
    }

    /* access modifiers changed from: 0000 */
    public synchronized void clearInstancesForTesting() {
        this.instances.clear();
    }
}
