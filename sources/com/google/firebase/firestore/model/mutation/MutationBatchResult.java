package com.google.firebase.firestore.model.mutation;

import com.google.firebase.database.collection.ImmutableSortedMap;
import com.google.firebase.firestore.model.DocumentCollections;
import com.google.firebase.firestore.model.DocumentKey;
import com.google.firebase.firestore.model.SnapshotVersion;
import com.google.firebase.firestore.util.Assert;
import com.google.protobuf.ByteString;
import java.util.List;

/* compiled from: com.google.firebase:firebase-firestore@@19.0.0 */
public final class MutationBatchResult {
    private final MutationBatch batch;
    private final SnapshotVersion commitVersion;
    private final ImmutableSortedMap<DocumentKey, SnapshotVersion> docVersions;
    private final List<MutationResult> mutationResults;
    private final ByteString streamToken;

    private MutationBatchResult(MutationBatch mutationBatch, SnapshotVersion snapshotVersion, List<MutationResult> list, ByteString byteString, ImmutableSortedMap<DocumentKey, SnapshotVersion> immutableSortedMap) {
        this.batch = mutationBatch;
        this.commitVersion = snapshotVersion;
        this.mutationResults = list;
        this.streamToken = byteString;
        this.docVersions = immutableSortedMap;
    }

    public static MutationBatchResult create(MutationBatch mutationBatch, SnapshotVersion snapshotVersion, List<MutationResult> list, ByteString byteString) {
        Assert.hardAssert(mutationBatch.getMutations().size() == list.size(), "Mutations sent %d must equal results received %d", Integer.valueOf(mutationBatch.getMutations().size()), Integer.valueOf(list.size()));
        ImmutableSortedMap emptyVersionMap = DocumentCollections.emptyVersionMap();
        List mutations = mutationBatch.getMutations();
        ImmutableSortedMap immutableSortedMap = emptyVersionMap;
        for (int i = 0; i < mutations.size(); i++) {
            immutableSortedMap = immutableSortedMap.insert(((Mutation) mutations.get(i)).getKey(), ((MutationResult) list.get(i)).getVersion());
        }
        MutationBatchResult mutationBatchResult = new MutationBatchResult(mutationBatch, snapshotVersion, list, byteString, immutableSortedMap);
        return mutationBatchResult;
    }

    public MutationBatch getBatch() {
        return this.batch;
    }

    public SnapshotVersion getCommitVersion() {
        return this.commitVersion;
    }

    public List<MutationResult> getMutationResults() {
        return this.mutationResults;
    }

    public ByteString getStreamToken() {
        return this.streamToken;
    }

    public ImmutableSortedMap<DocumentKey, SnapshotVersion> getDocVersions() {
        return this.docVersions;
    }
}
