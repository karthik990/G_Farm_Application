package com.google.firebase.firestore.local;

import com.google.firebase.firestore.auth.User;
import com.google.firebase.firestore.core.IndexRange;
import com.google.firebase.firestore.model.DocumentKey;
import com.google.firebase.firestore.model.FieldPath;
import com.google.firebase.firestore.model.ResourcePath;
import com.google.firebase.firestore.model.value.FieldValue;

/* compiled from: com.google.firebase:firebase-firestore@@19.0.0 */
public class SQLiteCollectionIndex {

    /* renamed from: db */
    private final SQLitePersistence f2026db;
    private final String uid;

    SQLiteCollectionIndex(SQLitePersistence sQLitePersistence, User user) {
        this.f2026db = sQLitePersistence;
        this.uid = user.isAuthenticated() ? user.getUid() : "";
    }

    public void addEntry(FieldPath fieldPath, FieldValue fieldValue, DocumentKey documentKey) {
        throw new RuntimeException("Not yet implemented.");
    }

    public void removeEntry(FieldPath fieldPath, FieldValue fieldValue, DocumentKey documentKey) {
        throw new RuntimeException("Not yet implemented.");
    }

    public IndexCursor getCursor(ResourcePath resourcePath, IndexRange indexRange) {
        throw new RuntimeException("Not yet implemented.");
    }
}
