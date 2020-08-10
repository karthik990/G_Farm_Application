package com.firebase.p037ui.database;

import com.firebase.p037ui.common.ChangeEventType;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import java.util.ArrayList;
import java.util.List;

/* renamed from: com.firebase.ui.database.FirebaseArray */
public class FirebaseArray<T> extends ObservableSnapshotArray<T> implements ChildEventListener, ValueEventListener {
    private final Query mQuery;
    private final List<DataSnapshot> mSnapshots = new ArrayList();

    public FirebaseArray(Query query, SnapshotParser<T> snapshotParser) {
        super(snapshotParser);
        this.mQuery = query;
    }

    /* access modifiers changed from: protected */
    public void onCreate() {
        super.onCreate();
        this.mQuery.addChildEventListener(this);
        this.mQuery.addValueEventListener(this);
    }

    /* access modifiers changed from: protected */
    public void onDestroy() {
        super.onDestroy();
        this.mQuery.removeEventListener((ValueEventListener) this);
        this.mQuery.removeEventListener((ChildEventListener) this);
    }

    public void onChildAdded(DataSnapshot dataSnapshot, String str) {
        int indexForKey = str != null ? getIndexForKey(str) + 1 : 0;
        this.mSnapshots.add(indexForKey, dataSnapshot);
        notifyOnChildChanged(ChangeEventType.ADDED, dataSnapshot, indexForKey, -1);
    }

    public void onChildChanged(DataSnapshot dataSnapshot, String str) {
        int indexForKey = getIndexForKey(dataSnapshot.getKey());
        this.mSnapshots.set(indexForKey, dataSnapshot);
        notifyOnChildChanged(ChangeEventType.CHANGED, dataSnapshot, indexForKey, -1);
    }

    public void onChildRemoved(DataSnapshot dataSnapshot) {
        int indexForKey = getIndexForKey(dataSnapshot.getKey());
        this.mSnapshots.remove(indexForKey);
        notifyOnChildChanged(ChangeEventType.REMOVED, dataSnapshot, indexForKey, -1);
    }

    public void onChildMoved(DataSnapshot dataSnapshot, String str) {
        int i;
        int indexForKey = getIndexForKey(dataSnapshot.getKey());
        this.mSnapshots.remove(indexForKey);
        if (str == null) {
            i = 0;
        } else {
            i = getIndexForKey(str) + 1;
        }
        this.mSnapshots.add(i, dataSnapshot);
        notifyOnChildChanged(ChangeEventType.MOVED, dataSnapshot, i, indexForKey);
    }

    public void onDataChange(DataSnapshot dataSnapshot) {
        notifyOnDataChanged();
    }

    public void onCancelled(DatabaseError databaseError) {
        notifyOnError(databaseError);
    }

    private int getIndexForKey(String str) {
        int i = 0;
        for (DataSnapshot key : this.mSnapshots) {
            if (key.getKey().equals(str)) {
                return i;
            }
            i++;
        }
        throw new IllegalArgumentException("Key not found");
    }

    /* access modifiers changed from: protected */
    public List<DataSnapshot> getSnapshots() {
        return this.mSnapshots;
    }
}
