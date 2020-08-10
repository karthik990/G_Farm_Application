package com.firebase.p037ui.database;

import androidx.lifecycle.LifecycleObserver;
import com.google.firebase.database.DatabaseReference;

/* renamed from: com.firebase.ui.database.FirebaseAdapter */
interface FirebaseAdapter<T> extends ChangeEventListener, LifecycleObserver {
    T getItem(int i);

    DatabaseReference getRef(int i);

    ObservableSnapshotArray<T> getSnapshots();

    void startListening();

    void stopListening();
}
