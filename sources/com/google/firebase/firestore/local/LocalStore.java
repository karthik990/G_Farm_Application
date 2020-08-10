package com.google.firebase.firestore.local;

import android.util.SparseArray;
import com.google.firebase.Timestamp;
import com.google.firebase.database.collection.ImmutableSortedMap;
import com.google.firebase.database.collection.ImmutableSortedSet;
import com.google.firebase.firestore.auth.User;
import com.google.firebase.firestore.core.Query;
import com.google.firebase.firestore.core.TargetIdGenerator;
import com.google.firebase.firestore.local.LruGarbageCollector.Results;
import com.google.firebase.firestore.model.Document;
import com.google.firebase.firestore.model.DocumentKey;
import com.google.firebase.firestore.model.MaybeDocument;
import com.google.firebase.firestore.model.SnapshotVersion;
import com.google.firebase.firestore.model.mutation.Mutation;
import com.google.firebase.firestore.model.mutation.MutationBatch;
import com.google.firebase.firestore.model.mutation.MutationBatchResult;
import com.google.firebase.firestore.remote.RemoteEvent;
import com.google.firebase.firestore.remote.TargetChange;
import com.google.firebase.firestore.util.Assert;
import com.google.firebase.firestore.util.Logger;
import com.google.protobuf.ByteString;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import javax.annotation.Nullable;

/* compiled from: com.google.firebase:firebase-firestore@@19.0.0 */
public final class LocalStore {
    private static final long RESUME_TOKEN_MAX_AGE_SECONDS = TimeUnit.MINUTES.toSeconds(5);
    private LocalDocumentsView localDocuments;
    private final ReferenceSet localViewReferences;
    private MutationQueue mutationQueue;
    private final Persistence persistence;
    private final QueryCache queryCache;
    private QueryEngine queryEngine;
    private final RemoteDocumentCache remoteDocuments;
    private final TargetIdGenerator targetIdGenerator = TargetIdGenerator.forQueryCache(this.queryCache.getHighestTargetId());
    private final SparseArray<QueryData> targetIds;

    /* compiled from: com.google.firebase:firebase-firestore@@19.0.0 */
    private static class AllocateQueryHolder {
        QueryData cached;
        int targetId;

        private AllocateQueryHolder() {
        }
    }

    public LocalStore(Persistence persistence2, User user) {
        Assert.hardAssert(persistence2.isStarted(), "LocalStore was passed an unstarted persistence implementation", new Object[0]);
        this.persistence = persistence2;
        this.queryCache = persistence2.getQueryCache();
        this.mutationQueue = persistence2.getMutationQueue(user);
        this.remoteDocuments = persistence2.getRemoteDocumentCache();
        this.localDocuments = new LocalDocumentsView(this.remoteDocuments, this.mutationQueue, persistence2.getIndexManager());
        this.queryEngine = new SimpleQueryEngine(this.localDocuments);
        this.localViewReferences = new ReferenceSet();
        persistence2.getReferenceDelegate().setInMemoryPins(this.localViewReferences);
        this.targetIds = new SparseArray<>();
    }

    public void start() {
        startMutationQueue();
    }

    private void startMutationQueue() {
        this.persistence.runTransaction("Start MutationQueue", LocalStore$$Lambda$1.lambdaFactory$(this));
    }

    public ImmutableSortedMap<DocumentKey, MaybeDocument> handleUserChange(User user) {
        List allMutationBatches = this.mutationQueue.getAllMutationBatches();
        this.mutationQueue = this.persistence.getMutationQueue(user);
        startMutationQueue();
        List allMutationBatches2 = this.mutationQueue.getAllMutationBatches();
        this.localDocuments = new LocalDocumentsView(this.remoteDocuments, this.mutationQueue, this.persistence.getIndexManager());
        this.queryEngine = new SimpleQueryEngine(this.localDocuments);
        ImmutableSortedSet emptyKeySet = DocumentKey.emptyKeySet();
        for (List<MutationBatch> it : Arrays.asList(new List[]{allMutationBatches, allMutationBatches2})) {
            for (MutationBatch mutations : it) {
                for (Mutation key : mutations.getMutations()) {
                    emptyKeySet = emptyKeySet.insert(key.getKey());
                }
            }
        }
        return this.localDocuments.getDocuments(emptyKeySet);
    }

    public LocalWriteResult writeLocally(List<Mutation> list) {
        Timestamp now = Timestamp.now();
        HashSet hashSet = new HashSet();
        for (Mutation key : list) {
            hashSet.add(key.getKey());
        }
        return (LocalWriteResult) this.persistence.runTransaction("Locally write mutations", LocalStore$$Lambda$2.lambdaFactory$(this, hashSet, list, now));
    }

    /* JADX WARNING: Incorrect type for immutable var: ssa=java.util.List, code=java.util.List<com.google.firebase.firestore.model.mutation.Mutation>, for r9v0, types: [java.util.List, java.util.List<com.google.firebase.firestore.model.mutation.Mutation>] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    static /* synthetic */ com.google.firebase.firestore.local.LocalWriteResult lambda$writeLocally$1(com.google.firebase.firestore.local.LocalStore r7, java.util.Set r8, java.util.List<com.google.firebase.firestore.model.mutation.Mutation> r9, com.google.firebase.Timestamp r10) {
        /*
            com.google.firebase.firestore.local.LocalDocumentsView r0 = r7.localDocuments
            com.google.firebase.database.collection.ImmutableSortedMap r8 = r0.getDocuments(r8)
            java.util.ArrayList r0 = new java.util.ArrayList
            r0.<init>()
            java.util.Iterator r1 = r9.iterator()
        L_0x000f:
            boolean r2 = r1.hasNext()
            if (r2 == 0) goto L_0x0056
            java.lang.Object r2 = r1.next()
            com.google.firebase.firestore.model.mutation.Mutation r2 = (com.google.firebase.firestore.model.mutation.Mutation) r2
            com.google.firebase.firestore.model.DocumentKey r3 = r2.getKey()
            java.lang.Object r3 = r8.get(r3)
            com.google.firebase.firestore.model.MaybeDocument r3 = (com.google.firebase.firestore.model.MaybeDocument) r3
            boolean r4 = r2.isIdempotent()
            if (r4 != 0) goto L_0x000f
            com.google.firebase.firestore.model.mutation.FieldMask r4 = r2.getFieldMask()
            if (r4 == 0) goto L_0x000f
            boolean r5 = r3 instanceof com.google.firebase.firestore.model.Document
            if (r5 == 0) goto L_0x0040
            com.google.firebase.firestore.model.Document r3 = (com.google.firebase.firestore.model.Document) r3
            com.google.firebase.firestore.model.value.ObjectValue r3 = r3.getData()
            com.google.firebase.firestore.model.value.ObjectValue r3 = r4.applyTo(r3)
            goto L_0x0044
        L_0x0040:
            com.google.firebase.firestore.model.value.ObjectValue r3 = com.google.firebase.firestore.model.value.ObjectValue.emptyObject()
        L_0x0044:
            com.google.firebase.firestore.model.mutation.PatchMutation r5 = new com.google.firebase.firestore.model.mutation.PatchMutation
            com.google.firebase.firestore.model.DocumentKey r2 = r2.getKey()
            r6 = 1
            com.google.firebase.firestore.model.mutation.Precondition r6 = com.google.firebase.firestore.model.mutation.Precondition.exists(r6)
            r5.<init>(r2, r3, r4, r6)
            r0.add(r5)
            goto L_0x000f
        L_0x0056:
            com.google.firebase.firestore.local.MutationQueue r7 = r7.mutationQueue
            com.google.firebase.firestore.model.mutation.MutationBatch r7 = r7.addMutationBatch(r10, r0, r9)
            com.google.firebase.database.collection.ImmutableSortedMap r8 = r7.applyToLocalDocumentSet(r8)
            com.google.firebase.firestore.local.LocalWriteResult r9 = new com.google.firebase.firestore.local.LocalWriteResult
            int r7 = r7.getBatchId()
            r9.<init>(r7, r8)
            return r9
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.firebase.firestore.local.LocalStore.lambda$writeLocally$1(com.google.firebase.firestore.local.LocalStore, java.util.Set, java.util.List, com.google.firebase.Timestamp):com.google.firebase.firestore.local.LocalWriteResult");
    }

    public ImmutableSortedMap<DocumentKey, MaybeDocument> acknowledgeBatch(MutationBatchResult mutationBatchResult) {
        return (ImmutableSortedMap) this.persistence.runTransaction("Acknowledge batch", LocalStore$$Lambda$3.lambdaFactory$(this, mutationBatchResult));
    }

    static /* synthetic */ ImmutableSortedMap lambda$acknowledgeBatch$2(LocalStore localStore, MutationBatchResult mutationBatchResult) {
        MutationBatch batch = mutationBatchResult.getBatch();
        localStore.mutationQueue.acknowledgeBatch(batch, mutationBatchResult.getStreamToken());
        localStore.applyWriteToRemoteDocuments(mutationBatchResult);
        localStore.mutationQueue.performConsistencyCheck();
        return localStore.localDocuments.getDocuments(batch.getKeys());
    }

    public ImmutableSortedMap<DocumentKey, MaybeDocument> rejectBatch(int i) {
        return (ImmutableSortedMap) this.persistence.runTransaction("Reject batch", LocalStore$$Lambda$4.lambdaFactory$(this, i));
    }

    static /* synthetic */ ImmutableSortedMap lambda$rejectBatch$3(LocalStore localStore, int i) {
        MutationBatch lookupMutationBatch = localStore.mutationQueue.lookupMutationBatch(i);
        Assert.hardAssert(lookupMutationBatch != null, "Attempt to reject nonexistent batch!", new Object[0]);
        localStore.mutationQueue.removeMutationBatch(lookupMutationBatch);
        localStore.mutationQueue.performConsistencyCheck();
        return localStore.localDocuments.getDocuments(lookupMutationBatch.getKeys());
    }

    public ByteString getLastStreamToken() {
        return this.mutationQueue.getLastStreamToken();
    }

    public void setLastStreamToken(ByteString byteString) {
        this.persistence.runTransaction("Set stream token", LocalStore$$Lambda$5.lambdaFactory$(this, byteString));
    }

    public SnapshotVersion getLastRemoteSnapshotVersion() {
        return this.queryCache.getLastRemoteSnapshotVersion();
    }

    public ImmutableSortedMap<DocumentKey, MaybeDocument> applyRemoteEvent(RemoteEvent remoteEvent) {
        return (ImmutableSortedMap) this.persistence.runTransaction("Apply remote event", LocalStore$$Lambda$6.lambdaFactory$(this, remoteEvent));
    }

    static /* synthetic */ ImmutableSortedMap lambda$applyRemoteEvent$5(LocalStore localStore, RemoteEvent remoteEvent) {
        long currentSequenceNumber = localStore.persistence.getReferenceDelegate().getCurrentSequenceNumber();
        HashSet hashSet = new HashSet();
        for (Entry entry : remoteEvent.getTargetChanges().entrySet()) {
            Integer num = (Integer) entry.getKey();
            int intValue = num.intValue();
            TargetChange targetChange = (TargetChange) entry.getValue();
            QueryData queryData = (QueryData) localStore.targetIds.get(intValue);
            if (queryData != null) {
                Iterator it = targetChange.getAddedDocuments().iterator();
                while (it.hasNext()) {
                    hashSet.add((DocumentKey) it.next());
                }
                Iterator it2 = targetChange.getModifiedDocuments().iterator();
                while (it2.hasNext()) {
                    hashSet.add((DocumentKey) it2.next());
                }
                localStore.queryCache.removeMatchingKeys(targetChange.getRemovedDocuments(), intValue);
                localStore.queryCache.addMatchingKeys(targetChange.getAddedDocuments(), intValue);
                ByteString resumeToken = targetChange.getResumeToken();
                if (!resumeToken.isEmpty()) {
                    QueryData copy = queryData.copy(remoteEvent.getSnapshotVersion(), resumeToken, currentSequenceNumber);
                    localStore.targetIds.put(num.intValue(), copy);
                    if (shouldPersistQueryData(queryData, copy, targetChange)) {
                        localStore.queryCache.updateQueryData(copy);
                    }
                }
            }
        }
        HashMap hashMap = new HashMap();
        Map documentUpdates = remoteEvent.getDocumentUpdates();
        Set resolvedLimboDocuments = remoteEvent.getResolvedLimboDocuments();
        Map all = localStore.remoteDocuments.getAll(documentUpdates.keySet());
        for (Entry entry2 : documentUpdates.entrySet()) {
            DocumentKey documentKey = (DocumentKey) entry2.getKey();
            MaybeDocument maybeDocument = (MaybeDocument) entry2.getValue();
            MaybeDocument maybeDocument2 = (MaybeDocument) all.get(documentKey);
            if (maybeDocument2 == null || maybeDocument.getVersion().equals(SnapshotVersion.NONE) || ((hashSet.contains(maybeDocument.getKey()) && !maybeDocument2.hasPendingWrites()) || maybeDocument.getVersion().compareTo(maybeDocument2.getVersion()) >= 0)) {
                localStore.remoteDocuments.add(maybeDocument);
                hashMap.put(documentKey, maybeDocument);
            } else {
                Logger.debug("LocalStore", "Ignoring outdated watch update for %s.Current version: %s  Watch version: %s", documentKey, maybeDocument2.getVersion(), maybeDocument.getVersion());
            }
            if (resolvedLimboDocuments.contains(documentKey)) {
                localStore.persistence.getReferenceDelegate().updateLimboDocument(documentKey);
            }
        }
        SnapshotVersion lastRemoteSnapshotVersion = localStore.queryCache.getLastRemoteSnapshotVersion();
        SnapshotVersion snapshotVersion = remoteEvent.getSnapshotVersion();
        if (!snapshotVersion.equals(SnapshotVersion.NONE)) {
            Assert.hardAssert(snapshotVersion.compareTo(lastRemoteSnapshotVersion) >= 0, "Watch stream reverted to previous snapshot?? (%s < %s)", snapshotVersion, lastRemoteSnapshotVersion);
            localStore.queryCache.setLastRemoteSnapshotVersion(snapshotVersion);
        }
        return localStore.localDocuments.getLocalViewOfDocuments(hashMap);
    }

    private static boolean shouldPersistQueryData(QueryData queryData, QueryData queryData2, TargetChange targetChange) {
        boolean z = false;
        if (queryData2.getResumeToken().isEmpty()) {
            return false;
        }
        if (queryData.getResumeToken().isEmpty() || queryData2.getSnapshotVersion().getTimestamp().getSeconds() - queryData.getSnapshotVersion().getTimestamp().getSeconds() >= RESUME_TOKEN_MAX_AGE_SECONDS) {
            return true;
        }
        if (targetChange.getAddedDocuments().size() + targetChange.getModifiedDocuments().size() + targetChange.getRemovedDocuments().size() > 0) {
            z = true;
        }
        return z;
    }

    public void notifyLocalViewChanges(List<LocalViewChanges> list) {
        this.persistence.runTransaction("notifyLocalViewChanges", LocalStore$$Lambda$7.lambdaFactory$(this, list));
    }

    /* JADX WARNING: Incorrect type for immutable var: ssa=java.util.List, code=java.util.List<com.google.firebase.firestore.local.LocalViewChanges>, for r6v0, types: [java.util.List, java.util.List<com.google.firebase.firestore.local.LocalViewChanges>] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    static /* synthetic */ void lambda$notifyLocalViewChanges$6(com.google.firebase.firestore.local.LocalStore r5, java.util.List<com.google.firebase.firestore.local.LocalViewChanges> r6) {
        /*
            java.util.Iterator r6 = r6.iterator()
        L_0x0004:
            boolean r0 = r6.hasNext()
            if (r0 == 0) goto L_0x0045
            java.lang.Object r0 = r6.next()
            com.google.firebase.firestore.local.LocalViewChanges r0 = (com.google.firebase.firestore.local.LocalViewChanges) r0
            com.google.firebase.firestore.local.ReferenceSet r1 = r5.localViewReferences
            com.google.firebase.database.collection.ImmutableSortedSet r2 = r0.getAdded()
            int r3 = r0.getTargetId()
            r1.addReferences(r2, r3)
            com.google.firebase.database.collection.ImmutableSortedSet r1 = r0.getRemoved()
            java.util.Iterator r2 = r1.iterator()
        L_0x0025:
            boolean r3 = r2.hasNext()
            if (r3 == 0) goto L_0x003b
            java.lang.Object r3 = r2.next()
            com.google.firebase.firestore.model.DocumentKey r3 = (com.google.firebase.firestore.model.DocumentKey) r3
            com.google.firebase.firestore.local.Persistence r4 = r5.persistence
            com.google.firebase.firestore.local.ReferenceDelegate r4 = r4.getReferenceDelegate()
            r4.removeReference(r3)
            goto L_0x0025
        L_0x003b:
            com.google.firebase.firestore.local.ReferenceSet r2 = r5.localViewReferences
            int r0 = r0.getTargetId()
            r2.removeReferences(r1, r0)
            goto L_0x0004
        L_0x0045:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.firebase.firestore.local.LocalStore.lambda$notifyLocalViewChanges$6(com.google.firebase.firestore.local.LocalStore, java.util.List):void");
    }

    @Nullable
    public MutationBatch getNextMutationBatch(int i) {
        return this.mutationQueue.getNextMutationBatchAfterBatchId(i);
    }

    @Nullable
    public MaybeDocument readDocument(DocumentKey documentKey) {
        return this.localDocuments.getDocument(documentKey);
    }

    public QueryData allocateQuery(Query query) {
        int i;
        QueryData queryData = this.queryCache.getQueryData(query);
        if (queryData != null) {
            i = queryData.getTargetId();
        } else {
            AllocateQueryHolder allocateQueryHolder = new AllocateQueryHolder();
            this.persistence.runTransaction("Allocate query", LocalStore$$Lambda$8.lambdaFactory$(this, allocateQueryHolder, query));
            i = allocateQueryHolder.targetId;
            queryData = allocateQueryHolder.cached;
        }
        Assert.hardAssert(this.targetIds.get(i) == null, "Tried to allocate an already allocated query: %s", query);
        this.targetIds.put(i, queryData);
        return queryData;
    }

    static /* synthetic */ void lambda$allocateQuery$7(LocalStore localStore, AllocateQueryHolder allocateQueryHolder, Query query) {
        allocateQueryHolder.targetId = localStore.targetIdGenerator.nextId();
        QueryData queryData = new QueryData(query, allocateQueryHolder.targetId, localStore.persistence.getReferenceDelegate().getCurrentSequenceNumber(), QueryPurpose.LISTEN);
        allocateQueryHolder.cached = queryData;
        localStore.queryCache.addQueryData(allocateQueryHolder.cached);
    }

    public void releaseQuery(Query query) {
        this.persistence.runTransaction("Release query", LocalStore$$Lambda$9.lambdaFactory$(this, query));
    }

    static /* synthetic */ void lambda$releaseQuery$8(LocalStore localStore, Query query) {
        QueryData queryData = localStore.queryCache.getQueryData(query);
        Assert.hardAssert(queryData != null, "Tried to release nonexistent query: %s", query);
        QueryData queryData2 = (QueryData) localStore.targetIds.get(queryData.getTargetId());
        if (queryData2.getSnapshotVersion().compareTo(queryData.getSnapshotVersion()) > 0) {
            localStore.queryCache.updateQueryData(queryData2);
        } else {
            queryData2 = queryData;
        }
        Iterator it = localStore.localViewReferences.removeReferencesForId(queryData2.getTargetId()).iterator();
        while (it.hasNext()) {
            localStore.persistence.getReferenceDelegate().removeReference((DocumentKey) it.next());
        }
        localStore.persistence.getReferenceDelegate().removeTarget(queryData2);
        localStore.targetIds.remove(queryData2.getTargetId());
    }

    public ImmutableSortedMap<DocumentKey, Document> executeQuery(Query query) {
        return this.queryEngine.getDocumentsMatchingQuery(query);
    }

    public ImmutableSortedSet<DocumentKey> getRemoteDocumentKeys(int i) {
        return this.queryCache.getMatchingKeysForTargetId(i);
    }

    private void applyWriteToRemoteDocuments(MutationBatchResult mutationBatchResult) {
        MutationBatch batch = mutationBatchResult.getBatch();
        for (DocumentKey documentKey : batch.getKeys()) {
            MaybeDocument maybeDocument = this.remoteDocuments.get(documentKey);
            SnapshotVersion snapshotVersion = (SnapshotVersion) mutationBatchResult.getDocVersions().get(documentKey);
            Assert.hardAssert(snapshotVersion != null, "docVersions should contain every doc in the write.", new Object[0]);
            if (maybeDocument == null || maybeDocument.getVersion().compareTo(snapshotVersion) < 0) {
                MaybeDocument applyToRemoteDocument = batch.applyToRemoteDocument(documentKey, maybeDocument, mutationBatchResult);
                if (applyToRemoteDocument == null) {
                    Assert.hardAssert(maybeDocument == null, "Mutation batch %s applied to document %s resulted in null.", batch, maybeDocument);
                } else {
                    this.remoteDocuments.add(applyToRemoteDocument);
                }
            }
        }
        this.mutationQueue.removeMutationBatch(batch);
    }

    public Results collectGarbage(LruGarbageCollector lruGarbageCollector) {
        return (Results) this.persistence.runTransaction("Collect garbage", LocalStore$$Lambda$10.lambdaFactory$(this, lruGarbageCollector));
    }
}
