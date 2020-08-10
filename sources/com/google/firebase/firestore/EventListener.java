package com.google.firebase.firestore;

import javax.annotation.Nullable;

/* compiled from: com.google.firebase:firebase-firestore@@19.0.0 */
public interface EventListener<T> {
    void onEvent(@Nullable T t, @Nullable FirebaseFirestoreException firebaseFirestoreException);
}
