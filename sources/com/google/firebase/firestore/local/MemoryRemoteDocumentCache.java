package com.google.firebase.firestore.local;

import com.google.firebase.database.collection.ImmutableSortedMap;
import com.google.firebase.firestore.core.Query;
import com.google.firebase.firestore.model.Document;
import com.google.firebase.firestore.model.DocumentCollections;
import com.google.firebase.firestore.model.DocumentKey;
import com.google.firebase.firestore.model.MaybeDocument;
import com.google.firebase.firestore.model.ResourcePath;
import com.google.firebase.firestore.util.Assert;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import javax.annotation.Nullable;

/* compiled from: com.google.firebase:firebase-firestore@@19.0.0 */
final class MemoryRemoteDocumentCache implements RemoteDocumentCache {
    private ImmutableSortedMap<DocumentKey, MaybeDocument> docs = DocumentCollections.emptyMaybeDocumentMap();
    private final MemoryPersistence persistence;

    MemoryRemoteDocumentCache(MemoryPersistence memoryPersistence) {
        this.persistence = memoryPersistence;
    }

    public void add(MaybeDocument maybeDocument) {
        this.docs = this.docs.insert(maybeDocument.getKey(), maybeDocument);
        this.persistence.getIndexManager().addToCollectionParentIndex((ResourcePath) maybeDocument.getKey().getPath().popLast());
    }

    public void remove(DocumentKey documentKey) {
        this.docs = this.docs.remove(documentKey);
    }

    @Nullable
    public MaybeDocument get(DocumentKey documentKey) {
        return (MaybeDocument) this.docs.get(documentKey);
    }

    public Map<DocumentKey, MaybeDocument> getAll(Iterable<DocumentKey> iterable) {
        HashMap hashMap = new HashMap();
        for (DocumentKey documentKey : iterable) {
            hashMap.put(documentKey, get(documentKey));
        }
        return hashMap;
    }

    public ImmutableSortedMap<DocumentKey, Document> getAllDocumentsMatchingQuery(Query query) {
        Assert.hardAssert(!query.isCollectionGroupQuery(), "CollectionGroup queries should be handled in LocalDocumentsView", new Object[0]);
        ImmutableSortedMap<DocumentKey, Document> emptyDocumentMap = DocumentCollections.emptyDocumentMap();
        ResourcePath path = query.getPath();
        Iterator iteratorFrom = this.docs.iteratorFrom(DocumentKey.fromPath((ResourcePath) path.append("")));
        while (iteratorFrom.hasNext()) {
            Entry entry = (Entry) iteratorFrom.next();
            if (!path.isPrefixOf(((DocumentKey) entry.getKey()).getPath())) {
                break;
            }
            MaybeDocument maybeDocument = (MaybeDocument) entry.getValue();
            if (maybeDocument instanceof Document) {
                Document document = (Document) maybeDocument;
                if (query.matches(document)) {
                    emptyDocumentMap = emptyDocumentMap.insert(document.getKey(), document);
                }
            }
        }
        return emptyDocumentMap;
    }

    /* access modifiers changed from: 0000 */
    public ImmutableSortedMap<DocumentKey, MaybeDocument> getDocuments() {
        return this.docs;
    }

    private static long getKeySize(DocumentKey documentKey) {
        ResourcePath path = documentKey.getPath();
        long j = 0;
        for (int i = 0; i < path.length(); i++) {
            j += (long) (path.getSegment(i).length() * 2);
        }
        return j;
    }

    /* access modifiers changed from: 0000 */
    public long getByteSize(LocalSerializer localSerializer) {
        Iterator it = this.docs.iterator();
        long j = 0;
        while (it.hasNext()) {
            Entry entry = (Entry) it.next();
            j = j + getKeySize((DocumentKey) entry.getKey()) + ((long) localSerializer.encodeMaybeDocument((MaybeDocument) entry.getValue()).getSerializedSize());
        }
        return j;
    }
}
