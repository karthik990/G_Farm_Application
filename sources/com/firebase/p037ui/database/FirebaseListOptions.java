package com.firebase.p037ui.database;

import androidx.lifecycle.LifecycleOwner;
import com.firebase.p037ui.common.Preconditions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;

/* renamed from: com.firebase.ui.database.FirebaseListOptions */
public final class FirebaseListOptions<T> {
    private static final String ERR_SNAPSHOTS_SET = "Snapshot array already set. Call only one of setSnapshotArray, setQuery, or setIndexedQuery.";
    private final int mLayout;
    private final LifecycleOwner mOwner;
    private final ObservableSnapshotArray<T> mSnapshots;

    /* renamed from: com.firebase.ui.database.FirebaseListOptions$Builder */
    public static final class Builder<T> {
        private Integer mLayout;
        private LifecycleOwner mOwner;
        private ObservableSnapshotArray<T> mSnapshots;

        public Builder<T> setSnapshotArray(ObservableSnapshotArray<T> observableSnapshotArray) {
            Preconditions.assertNull(this.mSnapshots, FirebaseListOptions.ERR_SNAPSHOTS_SET);
            this.mSnapshots = observableSnapshotArray;
            return this;
        }

        public Builder<T> setQuery(Query query, SnapshotParser<T> snapshotParser) {
            Preconditions.assertNull(this.mSnapshots, FirebaseListOptions.ERR_SNAPSHOTS_SET);
            this.mSnapshots = new FirebaseArray(query, snapshotParser);
            return this;
        }

        public Builder<T> setQuery(Query query, Class<T> cls) {
            return setQuery(query, (SnapshotParser<T>) new ClassSnapshotParser<T>(cls));
        }

        public Builder<T> setIndexedQuery(Query query, DatabaseReference databaseReference, SnapshotParser<T> snapshotParser) {
            Preconditions.assertNull(this.mSnapshots, FirebaseListOptions.ERR_SNAPSHOTS_SET);
            this.mSnapshots = new FirebaseIndexArray(query, databaseReference, snapshotParser);
            return this;
        }

        public Builder<T> setIndexedQuery(Query query, DatabaseReference databaseReference, Class<T> cls) {
            return setIndexedQuery(query, databaseReference, (SnapshotParser<T>) new ClassSnapshotParser<T>(cls));
        }

        public Builder<T> setLayout(int i) {
            this.mLayout = Integer.valueOf(i);
            return this;
        }

        public Builder<T> setLifecycleOwner(LifecycleOwner lifecycleOwner) {
            this.mOwner = lifecycleOwner;
            return this;
        }

        public FirebaseListOptions<T> build() {
            Preconditions.assertNonNull(this.mSnapshots, "Snapshot array cannot be null. Call setQuery or setSnapshotArray.");
            Preconditions.assertNonNull(this.mLayout, "Layout cannot be null. Call setLayout.");
            return new FirebaseListOptions<>(this.mSnapshots, this.mLayout.intValue(), this.mOwner);
        }
    }

    private FirebaseListOptions(ObservableSnapshotArray<T> observableSnapshotArray, int i, LifecycleOwner lifecycleOwner) {
        this.mSnapshots = observableSnapshotArray;
        this.mLayout = i;
        this.mOwner = lifecycleOwner;
    }

    public ObservableSnapshotArray<T> getSnapshots() {
        return this.mSnapshots;
    }

    public int getLayout() {
        return this.mLayout;
    }

    public LifecycleOwner getOwner() {
        return this.mOwner;
    }
}
