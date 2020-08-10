package com.firebase.p037ui.database;

import com.firebase.p037ui.common.BaseObservableSnapshotArray;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;

/* renamed from: com.firebase.ui.database.ObservableSnapshotArray */
public abstract class ObservableSnapshotArray<T> extends BaseObservableSnapshotArray<DataSnapshot, DatabaseError, ChangeEventListener, T> {
    public ObservableSnapshotArray(SnapshotParser<T> snapshotParser) {
        super(new CachingSnapshotParser(snapshotParser));
    }
}
