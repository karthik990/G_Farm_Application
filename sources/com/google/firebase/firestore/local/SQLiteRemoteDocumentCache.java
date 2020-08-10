package com.google.firebase.firestore.local;

import android.database.Cursor;
import com.google.firebase.database.collection.ImmutableSortedMap;
import com.google.firebase.database.collection.ImmutableSortedMap.Builder;
import com.google.firebase.firestore.core.Query;
import com.google.firebase.firestore.model.Document;
import com.google.firebase.firestore.model.DocumentKey;
import com.google.firebase.firestore.model.MaybeDocument;
import com.google.firebase.firestore.model.ResourcePath;
import com.google.firebase.firestore.util.Assert;
import com.google.protobuf.InvalidProtocolBufferException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import javax.annotation.Nullable;

/* compiled from: com.google.firebase:firebase-firestore@@19.0.0 */
final class SQLiteRemoteDocumentCache implements RemoteDocumentCache {

    /* renamed from: db */
    private final SQLitePersistence f2033db;
    private final LocalSerializer serializer;

    SQLiteRemoteDocumentCache(SQLitePersistence sQLitePersistence, LocalSerializer localSerializer) {
        this.f2033db = sQLitePersistence;
        this.serializer = localSerializer;
    }

    public void add(MaybeDocument maybeDocument) {
        String pathForKey = pathForKey(maybeDocument.getKey());
        com.google.firebase.firestore.proto.MaybeDocument encodeMaybeDocument = this.serializer.encodeMaybeDocument(maybeDocument);
        this.f2033db.execute("INSERT OR REPLACE INTO remote_documents (path, contents) VALUES (?, ?)", pathForKey, encodeMaybeDocument.toByteArray());
        this.f2033db.getIndexManager().addToCollectionParentIndex((ResourcePath) maybeDocument.getKey().getPath().popLast());
    }

    public void remove(DocumentKey documentKey) {
        String pathForKey = pathForKey(documentKey);
        this.f2033db.execute("DELETE FROM remote_documents WHERE path = ?", pathForKey);
    }

    @Nullable
    public MaybeDocument get(DocumentKey documentKey) {
        String pathForKey = pathForKey(documentKey);
        return (MaybeDocument) this.f2033db.query("SELECT contents FROM remote_documents WHERE path = ?").binding(pathForKey).firstValue(SQLiteRemoteDocumentCache$$Lambda$1.lambdaFactory$(this));
    }

    public Map<DocumentKey, MaybeDocument> getAll(Iterable<DocumentKey> iterable) {
        ArrayList arrayList = new ArrayList();
        for (DocumentKey path : iterable) {
            arrayList.add(EncodedPath.encode(path.getPath()));
        }
        HashMap hashMap = new HashMap();
        for (DocumentKey put : iterable) {
            hashMap.put(put, null);
        }
        LongQuery longQuery = new LongQuery(this.f2033db, "SELECT contents FROM remote_documents WHERE path IN (", arrayList, ") ORDER BY path");
        while (longQuery.hasMoreSubqueries()) {
            longQuery.performNextSubquery().forEach(SQLiteRemoteDocumentCache$$Lambda$2.lambdaFactory$(this, hashMap));
        }
        return hashMap;
    }

    static /* synthetic */ void lambda$getAll$1(SQLiteRemoteDocumentCache sQLiteRemoteDocumentCache, Map map, Cursor cursor) {
        MaybeDocument decodeMaybeDocument = sQLiteRemoteDocumentCache.decodeMaybeDocument(cursor.getBlob(0));
        map.put(decodeMaybeDocument.getKey(), decodeMaybeDocument);
    }

    public ImmutableSortedMap<DocumentKey, Document> getAllDocumentsMatchingQuery(Query query) {
        Assert.hardAssert(!query.isCollectionGroupQuery(), "CollectionGroup queries should be handled in LocalDocumentsView", new Object[0]);
        ResourcePath path = query.getPath();
        int length = path.length() + 1;
        String encode = EncodedPath.encode(path);
        String prefixSuccessor = EncodedPath.prefixSuccessor(encode);
        HashMap hashMap = new HashMap();
        this.f2033db.query("SELECT path, contents FROM remote_documents WHERE path >= ? AND path < ?").binding(encode, prefixSuccessor).forEach(SQLiteRemoteDocumentCache$$Lambda$3.lambdaFactory$(this, length, query, hashMap));
        return Builder.fromMap(hashMap, DocumentKey.comparator());
    }

    static /* synthetic */ void lambda$getAllDocumentsMatchingQuery$2(SQLiteRemoteDocumentCache sQLiteRemoteDocumentCache, int i, Query query, Map map, Cursor cursor) {
        if (EncodedPath.decodeResourcePath(cursor.getString(0)).length() == i) {
            MaybeDocument decodeMaybeDocument = sQLiteRemoteDocumentCache.decodeMaybeDocument(cursor.getBlob(1));
            if (decodeMaybeDocument instanceof Document) {
                Document document = (Document) decodeMaybeDocument;
                if (query.matches(document)) {
                    map.put(document.getKey(), document);
                }
            }
        }
    }

    private String pathForKey(DocumentKey documentKey) {
        return EncodedPath.encode(documentKey.getPath());
    }

    /* access modifiers changed from: private */
    public MaybeDocument decodeMaybeDocument(byte[] bArr) {
        try {
            return this.serializer.decodeMaybeDocument(com.google.firebase.firestore.proto.MaybeDocument.parseFrom(bArr));
        } catch (InvalidProtocolBufferException e) {
            throw Assert.fail("MaybeDocument failed to parse: %s", e);
        }
    }
}
