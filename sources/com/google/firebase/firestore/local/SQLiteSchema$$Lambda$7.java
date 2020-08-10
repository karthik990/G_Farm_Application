package com.google.firebase.firestore.local;

import android.database.Cursor;
import android.database.sqlite.SQLiteStatement;
import com.google.firebase.firestore.util.Consumer;

/* compiled from: com.google.firebase:firebase-firestore@@19.0.0 */
final /* synthetic */ class SQLiteSchema$$Lambda$7 implements Consumer {
    private final SQLiteStatement arg$1;
    private final long arg$2;

    private SQLiteSchema$$Lambda$7(SQLiteStatement sQLiteStatement, long j) {
        this.arg$1 = sQLiteStatement;
        this.arg$2 = j;
    }

    public static Consumer lambdaFactory$(SQLiteStatement sQLiteStatement, long j) {
        return new SQLiteSchema$$Lambda$7(sQLiteStatement, j);
    }

    public void accept(Object obj) {
        SQLiteSchema.lambda$ensureSequenceNumbers$7(this.arg$1, this.arg$2, (Cursor) obj);
    }
}
