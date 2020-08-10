package com.google.firebase.firestore.local;

import android.database.Cursor;
import com.google.firebase.firestore.core.Query;
import com.google.firebase.firestore.util.Consumer;
import java.util.Map;

/* compiled from: com.google.firebase:firebase-firestore@@19.0.0 */
final /* synthetic */ class SQLiteRemoteDocumentCache$$Lambda$3 implements Consumer {
    private final SQLiteRemoteDocumentCache arg$1;
    private final int arg$2;
    private final Query arg$3;
    private final Map arg$4;

    private SQLiteRemoteDocumentCache$$Lambda$3(SQLiteRemoteDocumentCache sQLiteRemoteDocumentCache, int i, Query query, Map map) {
        this.arg$1 = sQLiteRemoteDocumentCache;
        this.arg$2 = i;
        this.arg$3 = query;
        this.arg$4 = map;
    }

    public static Consumer lambdaFactory$(SQLiteRemoteDocumentCache sQLiteRemoteDocumentCache, int i, Query query, Map map) {
        return new SQLiteRemoteDocumentCache$$Lambda$3(sQLiteRemoteDocumentCache, i, query, map);
    }

    public void accept(Object obj) {
        SQLiteRemoteDocumentCache.lambda$getAllDocumentsMatchingQuery$2(this.arg$1, this.arg$2, this.arg$3, this.arg$4, (Cursor) obj);
    }
}
