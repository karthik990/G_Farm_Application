package com.google.android.exoplayer2.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public final class ExoDatabaseProvider extends SQLiteOpenHelper implements DatabaseProvider {
    public static final String DATABASE_NAME = "exoplayer_internal.db";
    private static final String TAG = "ExoDatabaseProvider";
    private static final int VERSION = 1;

    public void onCreate(SQLiteDatabase sQLiteDatabase) {
    }

    public void onUpgrade(SQLiteDatabase sQLiteDatabase, int i, int i2) {
    }

    public ExoDatabaseProvider(Context context) {
        super(context.getApplicationContext(), DATABASE_NAME, null, 1);
    }

    public void onDowngrade(SQLiteDatabase sQLiteDatabase, int i, int i2) {
        wipeDatabase(sQLiteDatabase);
    }

    /* JADX WARNING: Code restructure failed: missing block: B:18:0x006f, code lost:
        r10 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:19:0x0070, code lost:
        if (r1 != null) goto L_0x0072;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:21:?, code lost:
        r1.close();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:23:0x0076, code lost:
        throw r10;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private static void wipeDatabase(android.database.sqlite.SQLiteDatabase r10) {
        /*
            r0 = 2
            java.lang.String[] r3 = new java.lang.String[r0]
            r0 = 0
            java.lang.String r1 = "type"
            r3[r0] = r1
            r9 = 1
            java.lang.String r1 = "name"
            r3[r9] = r1
            java.lang.String r2 = "sqlite_master"
            r4 = 0
            r5 = 0
            r6 = 0
            r7 = 0
            r8 = 0
            r1 = r10
            android.database.Cursor r1 = r1.query(r2, r3, r4, r5, r6, r7, r8)
        L_0x001b:
            boolean r2 = r1.moveToNext()     // Catch:{ all -> 0x006d }
            if (r2 == 0) goto L_0x0067
            java.lang.String r2 = r1.getString(r0)     // Catch:{ all -> 0x006d }
            java.lang.String r3 = r1.getString(r9)     // Catch:{ all -> 0x006d }
            java.lang.String r4 = "sqlite_sequence"
            boolean r4 = r4.equals(r3)     // Catch:{ all -> 0x006d }
            if (r4 != 0) goto L_0x001b
            java.lang.StringBuilder r4 = new java.lang.StringBuilder     // Catch:{ all -> 0x006d }
            r4.<init>()     // Catch:{ all -> 0x006d }
            java.lang.String r5 = "DROP "
            r4.append(r5)     // Catch:{ all -> 0x006d }
            r4.append(r2)     // Catch:{ all -> 0x006d }
            java.lang.String r2 = " IF EXISTS "
            r4.append(r2)     // Catch:{ all -> 0x006d }
            r4.append(r3)     // Catch:{ all -> 0x006d }
            java.lang.String r2 = r4.toString()     // Catch:{ all -> 0x006d }
            r10.execSQL(r2)     // Catch:{ SQLException -> 0x004f }
            goto L_0x001b
        L_0x004f:
            r3 = move-exception
            java.lang.String r4 = "ExoDatabaseProvider"
            java.lang.StringBuilder r5 = new java.lang.StringBuilder     // Catch:{ all -> 0x006d }
            r5.<init>()     // Catch:{ all -> 0x006d }
            java.lang.String r6 = "Error executing "
            r5.append(r6)     // Catch:{ all -> 0x006d }
            r5.append(r2)     // Catch:{ all -> 0x006d }
            java.lang.String r2 = r5.toString()     // Catch:{ all -> 0x006d }
            com.google.android.exoplayer2.util.Log.m1393e(r4, r2, r3)     // Catch:{ all -> 0x006d }
            goto L_0x001b
        L_0x0067:
            if (r1 == 0) goto L_0x006c
            r1.close()
        L_0x006c:
            return
        L_0x006d:
            r10 = move-exception
            throw r10     // Catch:{ all -> 0x006f }
        L_0x006f:
            r10 = move-exception
            if (r1 == 0) goto L_0x0075
            r1.close()     // Catch:{ all -> 0x0075 }
        L_0x0075:
            goto L_0x0077
        L_0x0076:
            throw r10
        L_0x0077:
            goto L_0x0076
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.exoplayer2.database.ExoDatabaseProvider.wipeDatabase(android.database.sqlite.SQLiteDatabase):void");
    }
}
