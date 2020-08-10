package com.google.firebase.firestore.local;

import com.google.common.base.Preconditions;
import com.google.firebase.Timestamp;
import com.google.firebase.database.collection.ImmutableSortedSet;
import com.google.firebase.firestore.core.Query;
import com.google.firebase.firestore.model.DocumentKey;
import com.google.firebase.firestore.model.ResourcePath;
import com.google.firebase.firestore.model.mutation.Mutation;
import com.google.firebase.firestore.model.mutation.MutationBatch;
import com.google.firebase.firestore.remote.WriteStream;
import com.google.firebase.firestore.util.Assert;
import com.google.firebase.firestore.util.Util;
import com.google.protobuf.ByteString;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import javax.annotation.Nullable;

/* compiled from: com.google.firebase:firebase-firestore@@19.0.0 */
final class MemoryMutationQueue implements MutationQueue {
    private ImmutableSortedSet<DocumentReference> batchesByDocumentKey = new ImmutableSortedSet<>(Collections.emptyList(), DocumentReference.BY_KEY);
    private ByteString lastStreamToken = WriteStream.EMPTY_STREAM_TOKEN;
    private int nextBatchId = 1;
    private final MemoryPersistence persistence;
    private final List<MutationBatch> queue = new ArrayList();

    MemoryMutationQueue(MemoryPersistence memoryPersistence) {
        this.persistence = memoryPersistence;
    }

    public void start() {
        if (isEmpty()) {
            this.nextBatchId = 1;
        }
    }

    public boolean isEmpty() {
        return this.queue.isEmpty();
    }

    public void acknowledgeBatch(MutationBatch mutationBatch, ByteString byteString) {
        int batchId = mutationBatch.getBatchId();
        int indexOfExistingBatchId = indexOfExistingBatchId(batchId, "acknowledged");
        Assert.hardAssert(indexOfExistingBatchId == 0, "Can only acknowledge the first batch in the mutation queue", new Object[0]);
        MutationBatch mutationBatch2 = (MutationBatch) this.queue.get(indexOfExistingBatchId);
        Assert.hardAssert(batchId == mutationBatch2.getBatchId(), "Queue ordering failure: expected batch %d, got batch %d", Integer.valueOf(batchId), Integer.valueOf(mutationBatch2.getBatchId()));
        this.lastStreamToken = (ByteString) Preconditions.checkNotNull(byteString);
    }

    public ByteString getLastStreamToken() {
        return this.lastStreamToken;
    }

    public void setLastStreamToken(ByteString byteString) {
        this.lastStreamToken = (ByteString) Preconditions.checkNotNull(byteString);
    }

    public MutationBatch addMutationBatch(Timestamp timestamp, List<Mutation> list, List<Mutation> list2) {
        boolean z = true;
        Assert.hardAssert(!list2.isEmpty(), "Mutation batches should not be empty", new Object[0]);
        int i = this.nextBatchId;
        this.nextBatchId = i + 1;
        int size = this.queue.size();
        if (size > 0) {
            if (((MutationBatch) this.queue.get(size - 1)).getBatchId() >= i) {
                z = false;
            }
            Assert.hardAssert(z, "Mutation batchIds must be monotonically increasing order", new Object[0]);
        }
        MutationBatch mutationBatch = new MutationBatch(i, timestamp, list, list2);
        this.queue.add(mutationBatch);
        for (Mutation mutation : list2) {
            this.batchesByDocumentKey = this.batchesByDocumentKey.insert(new DocumentReference(mutation.getKey(), i));
            this.persistence.getIndexManager().addToCollectionParentIndex((ResourcePath) mutation.getKey().getPath().popLast());
        }
        return mutationBatch;
    }

    @Nullable
    public MutationBatch lookupMutationBatch(int i) {
        int indexOfBatchId = indexOfBatchId(i);
        if (indexOfBatchId < 0 || indexOfBatchId >= this.queue.size()) {
            return null;
        }
        MutationBatch mutationBatch = (MutationBatch) this.queue.get(indexOfBatchId);
        Assert.hardAssert(mutationBatch.getBatchId() == i, "If found batch must match", new Object[0]);
        return mutationBatch;
    }

    @Nullable
    public MutationBatch getNextMutationBatchAfterBatchId(int i) {
        int indexOfBatchId = indexOfBatchId(i + 1);
        if (indexOfBatchId < 0) {
            indexOfBatchId = 0;
        }
        if (this.queue.size() > indexOfBatchId) {
            return (MutationBatch) this.queue.get(indexOfBatchId);
        }
        return null;
    }

    public List<MutationBatch> getAllMutationBatches() {
        return Collections.unmodifiableList(this.queue);
    }

    public List<MutationBatch> getAllMutationBatchesAffectingDocumentKey(DocumentKey documentKey) {
        DocumentReference documentReference = new DocumentReference(documentKey, 0);
        ArrayList arrayList = new ArrayList();
        Iterator iteratorFrom = this.batchesByDocumentKey.iteratorFrom(documentReference);
        while (iteratorFrom.hasNext()) {
            DocumentReference documentReference2 = (DocumentReference) iteratorFrom.next();
            if (!documentKey.equals(documentReference2.getKey())) {
                break;
            }
            MutationBatch lookupMutationBatch = lookupMutationBatch(documentReference2.getId());
            Assert.hardAssert(lookupMutationBatch != null, "Batches in the index must exist in the main table", new Object[0]);
            arrayList.add(lookupMutationBatch);
        }
        return arrayList;
    }

    public List<MutationBatch> getAllMutationBatchesAffectingDocumentKeys(Iterable<DocumentKey> iterable) {
        ImmutableSortedSet immutableSortedSet = new ImmutableSortedSet(Collections.emptyList(), Util.comparator());
        for (DocumentKey documentKey : iterable) {
            Iterator iteratorFrom = this.batchesByDocumentKey.iteratorFrom(new DocumentReference(documentKey, 0));
            while (iteratorFrom.hasNext()) {
                DocumentReference documentReference = (DocumentReference) iteratorFrom.next();
                if (!documentKey.equals(documentReference.getKey())) {
                    break;
                }
                immutableSortedSet = immutableSortedSet.insert(Integer.valueOf(documentReference.getId()));
            }
        }
        return lookupMutationBatches(immutableSortedSet);
    }

    public List<MutationBatch> getAllMutationBatchesAffectingQuery(Query query) {
        Assert.hardAssert(!query.isCollectionGroupQuery(), "CollectionGroup queries should be handled in LocalDocumentsView", new Object[0]);
        ResourcePath path = query.getPath();
        int length = path.length() + 1;
        DocumentReference documentReference = new DocumentReference(DocumentKey.fromPath(!DocumentKey.isDocumentKey(path) ? (ResourcePath) path.append("") : path), 0);
        ImmutableSortedSet immutableSortedSet = new ImmutableSortedSet(Collections.emptyList(), Util.comparator());
        Iterator iteratorFrom = this.batchesByDocumentKey.iteratorFrom(documentReference);
        while (iteratorFrom.hasNext()) {
            DocumentReference documentReference2 = (DocumentReference) iteratorFrom.next();
            ResourcePath path2 = documentReference2.getKey().getPath();
            if (!path.isPrefixOf(path2)) {
                break;
            } else if (path2.length() == length) {
                immutableSortedSet = immutableSortedSet.insert(Integer.valueOf(documentReference2.getId()));
            }
        }
        return lookupMutationBatches(immutableSortedSet);
    }

    private List<MutationBatch> lookupMutationBatches(ImmutableSortedSet<Integer> immutableSortedSet) {
        ArrayList arrayList = new ArrayList();
        Iterator it = immutableSortedSet.iterator();
        while (it.hasNext()) {
            MutationBatch lookupMutationBatch = lookupMutationBatch(((Integer) it.next()).intValue());
            if (lookupMutationBatch != null) {
                arrayList.add(lookupMutationBatch);
            }
        }
        return arrayList;
    }

    public void removeMutationBatch(MutationBatch mutationBatch) {
        Assert.hardAssert(indexOfExistingBatchId(mutationBatch.getBatchId(), "removed") == 0, "Can only remove the first entry of the mutation queue", new Object[0]);
        this.queue.remove(0);
        ImmutableSortedSet<DocumentReference> immutableSortedSet = this.batchesByDocumentKey;
        for (Mutation key : mutationBatch.getMutations()) {
            DocumentKey key2 = key.getKey();
            this.persistence.getReferenceDelegate().removeMutationReference(key2);
            immutableSortedSet = immutableSortedSet.remove(new DocumentReference(key2, mutationBatch.getBatchId()));
        }
        this.batchesByDocumentKey = immutableSortedSet;
    }

    public void performConsistencyCheck() {
        if (this.queue.isEmpty()) {
            Assert.hardAssert(this.batchesByDocumentKey.isEmpty(), "Document leak -- detected dangling mutation references when queue is empty.", new Object[0]);
        }
    }

    /* access modifiers changed from: 0000 */
    public boolean containsKey(DocumentKey documentKey) {
        Iterator iteratorFrom = this.batchesByDocumentKey.iteratorFrom(new DocumentReference(documentKey, 0));
        if (!iteratorFrom.hasNext()) {
            return false;
        }
        return ((DocumentReference) iteratorFrom.next()).getKey().equals(documentKey);
    }

    private int indexOfBatchId(int i) {
        if (this.queue.isEmpty()) {
            return 0;
        }
        return i - ((MutationBatch) this.queue.get(0)).getBatchId();
    }

    private int indexOfExistingBatchId(int i, String str) {
        int indexOfBatchId = indexOfBatchId(i);
        Assert.hardAssert(indexOfBatchId >= 0 && indexOfBatchId < this.queue.size(), "Batches must exist to be %s", str);
        return indexOfBatchId;
    }

    /* access modifiers changed from: 0000 */
    public long getByteSize(LocalSerializer localSerializer) {
        long j = 0;
        for (MutationBatch encodeMutationBatch : this.queue) {
            j += (long) localSerializer.encodeMutationBatch(encodeMutationBatch).getSerializedSize();
        }
        return j;
    }
}
