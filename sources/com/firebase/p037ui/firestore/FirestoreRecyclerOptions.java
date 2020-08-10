package com.firebase.p037ui.firestore;

import androidx.lifecycle.LifecycleOwner;
import com.firebase.p037ui.common.Preconditions;
import com.google.firebase.firestore.MetadataChanges;
import com.google.firebase.firestore.Query;

/* renamed from: com.firebase.ui.firestore.FirestoreRecyclerOptions */
public final class FirestoreRecyclerOptions<T> {
    private static final String ERR_SNAPSHOTS_NULL = "Snapshot array cannot be null. Call one of setSnapshotArray or setQuery";
    private static final String ERR_SNAPSHOTS_SET = "Snapshot array already set. Call only one of setSnapshotArray or setQuery";
    private LifecycleOwner mOwner;
    private ObservableSnapshotArray<T> mSnapshots;

    /* renamed from: com.firebase.ui.firestore.FirestoreRecyclerOptions$Builder */
    public static final class Builder<T> {
        private LifecycleOwner mOwner;
        private ObservableSnapshotArray<T> mSnapshots;

        public Builder<T> setSnapshotArray(ObservableSnapshotArray<T> observableSnapshotArray) {
            Preconditions.assertNull(this.mSnapshots, FirestoreRecyclerOptions.ERR_SNAPSHOTS_SET);
            this.mSnapshots = observableSnapshotArray;
            return this;
        }

        public Builder<T> setQuery(Query query, SnapshotParser<T> snapshotParser) {
            return setQuery(query, MetadataChanges.EXCLUDE, snapshotParser);
        }

        public Builder<T> setQuery(Query query, Class<T> cls) {
            return setQuery(query, MetadataChanges.EXCLUDE, cls);
        }

        public Builder<T> setQuery(Query query, MetadataChanges metadataChanges, Class<T> cls) {
            return setQuery(query, metadataChanges, (SnapshotParser<T>) new ClassSnapshotParser<T>(cls));
        }

        public Builder<T> setQuery(Query query, MetadataChanges metadataChanges, SnapshotParser<T> snapshotParser) {
            Preconditions.assertNull(this.mSnapshots, FirestoreRecyclerOptions.ERR_SNAPSHOTS_SET);
            this.mSnapshots = new FirestoreArray(query, metadataChanges, snapshotParser);
            return this;
        }

        public Builder<T> setLifecycleOwner(LifecycleOwner lifecycleOwner) {
            this.mOwner = lifecycleOwner;
            return this;
        }

        public FirestoreRecyclerOptions<T> build() {
            Preconditions.assertNonNull(this.mSnapshots, FirestoreRecyclerOptions.ERR_SNAPSHOTS_NULL);
            return new FirestoreRecyclerOptions<>(this.mSnapshots, this.mOwner);
        }
    }

    private FirestoreRecyclerOptions(ObservableSnapshotArray<T> observableSnapshotArray, LifecycleOwner lifecycleOwner) {
        this.mSnapshots = observableSnapshotArray;
        this.mOwner = lifecycleOwner;
    }

    public ObservableSnapshotArray<T> getSnapshots() {
        return this.mSnapshots;
    }

    public LifecycleOwner getOwner() {
        return this.mOwner;
    }
}
