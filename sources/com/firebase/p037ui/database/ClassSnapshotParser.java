package com.firebase.p037ui.database;

import com.firebase.p037ui.common.Preconditions;
import com.google.firebase.database.DataSnapshot;

/* renamed from: com.firebase.ui.database.ClassSnapshotParser */
public class ClassSnapshotParser<T> implements SnapshotParser<T> {
    private Class<T> mClass;

    public ClassSnapshotParser(Class<T> cls) {
        this.mClass = (Class) Preconditions.checkNotNull(cls);
    }

    public T parseSnapshot(DataSnapshot dataSnapshot) {
        return dataSnapshot.getValue(this.mClass);
    }
}
