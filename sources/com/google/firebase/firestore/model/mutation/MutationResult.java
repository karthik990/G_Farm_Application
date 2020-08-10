package com.google.firebase.firestore.model.mutation;

import com.google.common.base.Preconditions;
import com.google.firebase.firestore.model.SnapshotVersion;
import com.google.firebase.firestore.model.value.FieldValue;
import java.util.List;

/* compiled from: com.google.firebase:firebase-firestore@@19.0.0 */
public final class MutationResult {
    private final List<FieldValue> transformResults;
    private final SnapshotVersion version;

    public MutationResult(SnapshotVersion snapshotVersion, List<FieldValue> list) {
        this.version = (SnapshotVersion) Preconditions.checkNotNull(snapshotVersion);
        this.transformResults = list;
    }

    public SnapshotVersion getVersion() {
        return this.version;
    }

    public List<FieldValue> getTransformResults() {
        return this.transformResults;
    }
}
