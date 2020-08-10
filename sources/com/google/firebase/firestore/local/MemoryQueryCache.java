package com.google.firebase.firestore.local;

import android.util.SparseArray;
import com.google.firebase.database.collection.ImmutableSortedSet;
import com.google.firebase.firestore.core.Query;
import com.google.firebase.firestore.model.DocumentKey;
import com.google.firebase.firestore.model.SnapshotVersion;
import com.google.firebase.firestore.util.Consumer;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import javax.annotation.Nullable;

/* compiled from: com.google.firebase:firebase-firestore@@19.0.0 */
final class MemoryQueryCache implements QueryCache {
    private long highestSequenceNumber = 0;
    private int highestTargetId;
    private SnapshotVersion lastRemoteSnapshotVersion = SnapshotVersion.NONE;
    private final MemoryPersistence persistence;
    private final Map<Query, QueryData> queries = new HashMap();
    private final ReferenceSet references = new ReferenceSet();

    MemoryQueryCache(MemoryPersistence memoryPersistence) {
        this.persistence = memoryPersistence;
    }

    public int getHighestTargetId() {
        return this.highestTargetId;
    }

    public long getTargetCount() {
        return (long) this.queries.size();
    }

    public void forEachTarget(Consumer<QueryData> consumer) {
        for (QueryData accept : this.queries.values()) {
            consumer.accept(accept);
        }
    }

    public long getHighestListenSequenceNumber() {
        return this.highestSequenceNumber;
    }

    public SnapshotVersion getLastRemoteSnapshotVersion() {
        return this.lastRemoteSnapshotVersion;
    }

    public void setLastRemoteSnapshotVersion(SnapshotVersion snapshotVersion) {
        this.lastRemoteSnapshotVersion = snapshotVersion;
    }

    public void addQueryData(QueryData queryData) {
        this.queries.put(queryData.getQuery(), queryData);
        int targetId = queryData.getTargetId();
        if (targetId > this.highestTargetId) {
            this.highestTargetId = targetId;
        }
        if (queryData.getSequenceNumber() > this.highestSequenceNumber) {
            this.highestSequenceNumber = queryData.getSequenceNumber();
        }
    }

    public void updateQueryData(QueryData queryData) {
        addQueryData(queryData);
    }

    public void removeQueryData(QueryData queryData) {
        this.queries.remove(queryData.getQuery());
        this.references.removeReferencesForId(queryData.getTargetId());
    }

    /* access modifiers changed from: 0000 */
    public int removeQueries(long j, SparseArray<?> sparseArray) {
        Iterator it = this.queries.entrySet().iterator();
        int i = 0;
        while (it.hasNext()) {
            Entry entry = (Entry) it.next();
            int targetId = ((QueryData) entry.getValue()).getTargetId();
            if (((QueryData) entry.getValue()).getSequenceNumber() <= j && sparseArray.get(targetId) == null) {
                it.remove();
                removeMatchingKeysForTargetId(targetId);
                i++;
            }
        }
        return i;
    }

    @Nullable
    public QueryData getQueryData(Query query) {
        return (QueryData) this.queries.get(query);
    }

    public void addMatchingKeys(ImmutableSortedSet<DocumentKey> immutableSortedSet, int i) {
        this.references.addReferences(immutableSortedSet, i);
        ReferenceDelegate referenceDelegate = this.persistence.getReferenceDelegate();
        Iterator it = immutableSortedSet.iterator();
        while (it.hasNext()) {
            referenceDelegate.addReference((DocumentKey) it.next());
        }
    }

    public void removeMatchingKeys(ImmutableSortedSet<DocumentKey> immutableSortedSet, int i) {
        this.references.removeReferences(immutableSortedSet, i);
        ReferenceDelegate referenceDelegate = this.persistence.getReferenceDelegate();
        Iterator it = immutableSortedSet.iterator();
        while (it.hasNext()) {
            referenceDelegate.removeReference((DocumentKey) it.next());
        }
    }

    private void removeMatchingKeysForTargetId(int i) {
        this.references.removeReferencesForId(i);
    }

    public ImmutableSortedSet<DocumentKey> getMatchingKeysForTargetId(int i) {
        return this.references.referencesForId(i);
    }

    public boolean containsKey(DocumentKey documentKey) {
        return this.references.containsKey(documentKey);
    }

    /* access modifiers changed from: 0000 */
    public long getByteSize(LocalSerializer localSerializer) {
        long j = 0;
        for (Entry value : this.queries.entrySet()) {
            j += (long) localSerializer.encodeQueryData((QueryData) value.getValue()).getSerializedSize();
        }
        return j;
    }
}
