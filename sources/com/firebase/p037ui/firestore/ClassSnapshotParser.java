package com.firebase.p037ui.firestore;

import com.firebase.p037ui.common.Preconditions;
import com.google.firebase.firestore.DocumentSnapshot;

/* renamed from: com.firebase.ui.firestore.ClassSnapshotParser */
public class ClassSnapshotParser<T> implements SnapshotParser<T> {
    private final Class<T> mModelClass;

    public ClassSnapshotParser(Class<T> cls) {
        this.mModelClass = (Class) Preconditions.checkNotNull(cls);
    }

    public T parseSnapshot(DocumentSnapshot documentSnapshot) {
        return documentSnapshot.toObject(this.mModelClass);
    }
}
