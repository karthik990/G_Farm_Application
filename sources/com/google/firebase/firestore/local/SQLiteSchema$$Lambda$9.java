package com.google.firebase.firestore.local;

import android.database.sqlite.SQLiteStatement;
import com.google.firebase.firestore.model.ResourcePath;
import com.google.firebase.firestore.util.Consumer;

/* compiled from: com.google.firebase:firebase-firestore@@19.0.0 */
final /* synthetic */ class SQLiteSchema$$Lambda$9 implements Consumer {
    private final MemoryCollectionParentIndex arg$1;
    private final SQLiteStatement arg$2;

    private SQLiteSchema$$Lambda$9(MemoryCollectionParentIndex memoryCollectionParentIndex, SQLiteStatement sQLiteStatement) {
        this.arg$1 = memoryCollectionParentIndex;
        this.arg$2 = sQLiteStatement;
    }

    public static Consumer lambdaFactory$(MemoryCollectionParentIndex memoryCollectionParentIndex, SQLiteStatement sQLiteStatement) {
        return new SQLiteSchema$$Lambda$9(memoryCollectionParentIndex, sQLiteStatement);
    }

    public void accept(Object obj) {
        SQLiteSchema.lambda$createV8CollectionParentsIndex$9(this.arg$1, this.arg$2, (ResourcePath) obj);
    }
}
