package com.google.firebase.firestore.local;

import android.database.Cursor;
import com.google.firebase.firestore.model.DocumentKey;
import com.google.firebase.firestore.util.Consumer;

/* compiled from: com.google.firebase:firebase-firestore@@19.0.0 */
final /* synthetic */ class SQLiteQueryCache$$Lambda$5 implements Consumer {
    private final DocumentKeysHolder arg$1;

    private SQLiteQueryCache$$Lambda$5(DocumentKeysHolder documentKeysHolder) {
        this.arg$1 = documentKeysHolder;
    }

    public static Consumer lambdaFactory$(DocumentKeysHolder documentKeysHolder) {
        return new SQLiteQueryCache$$Lambda$5(documentKeysHolder);
    }

    public void accept(Object obj) {
        this.arg$1.keys = this.arg$1.keys.insert(DocumentKey.fromPath(EncodedPath.decodeResourcePath(((Cursor) obj).getString(0))));
    }
}
