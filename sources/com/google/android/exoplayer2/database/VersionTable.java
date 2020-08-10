package com.google.android.exoplayer2.database;

import android.content.ContentValues;
import android.database.DatabaseUtils;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

public final class VersionTable {
    private static final String COLUMN_FEATURE = "feature";
    private static final String COLUMN_INSTANCE_UID = "instance_uid";
    private static final String COLUMN_VERSION = "version";
    public static final int FEATURE_CACHE_CONTENT_METADATA = 1;
    public static final int FEATURE_CACHE_FILE_METADATA = 2;
    public static final int FEATURE_OFFLINE = 0;
    private static final String PRIMARY_KEY = "PRIMARY KEY (feature, instance_uid)";
    private static final String SQL_CREATE_TABLE_IF_NOT_EXISTS = "CREATE TABLE IF NOT EXISTS ExoPlayerVersions (feature INTEGER NOT NULL,instance_uid TEXT NOT NULL,version INTEGER NOT NULL,PRIMARY KEY (feature, instance_uid))";
    private static final String TABLE_NAME = "ExoPlayerVersions";
    public static final int VERSION_UNSET = -1;
    private static final String WHERE_FEATURE_AND_INSTANCE_UID_EQUALS = "feature = ? AND instance_uid = ?";

    private VersionTable() {
    }

    public static void setVersion(SQLiteDatabase sQLiteDatabase, int i, String str, int i2) throws DatabaseIOException {
        try {
            sQLiteDatabase.execSQL(SQL_CREATE_TABLE_IF_NOT_EXISTS);
            ContentValues contentValues = new ContentValues();
            contentValues.put(COLUMN_FEATURE, Integer.valueOf(i));
            contentValues.put(COLUMN_INSTANCE_UID, str);
            contentValues.put("version", Integer.valueOf(i2));
            sQLiteDatabase.replaceOrThrow(TABLE_NAME, null, contentValues);
        } catch (SQLException e) {
            throw new DatabaseIOException(e);
        }
    }

    public static void removeVersion(SQLiteDatabase sQLiteDatabase, int i, String str) throws DatabaseIOException {
        String str2 = TABLE_NAME;
        try {
            if (tableExists(sQLiteDatabase, str2)) {
                sQLiteDatabase.delete(str2, WHERE_FEATURE_AND_INSTANCE_UID_EQUALS, featureAndInstanceUidArguments(i, str));
            }
        } catch (SQLException e) {
            throw new DatabaseIOException(e);
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:22:0x003e, code lost:
        r12 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:23:0x003f, code lost:
        if (r11 != null) goto L_0x0041;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:25:?, code lost:
        r11.close();
     */
    /* JADX WARNING: Missing exception handler attribute for start block: B:26:0x0044 */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static int getVersion(android.database.sqlite.SQLiteDatabase r11, int r12, java.lang.String r13) throws com.google.android.exoplayer2.database.DatabaseIOException {
        /*
            java.lang.String r0 = "ExoPlayerVersions"
            boolean r0 = tableExists(r11, r0)     // Catch:{ SQLException -> 0x0045 }
            r1 = -1
            if (r0 != 0) goto L_0x000a
            return r1
        L_0x000a:
            java.lang.String r3 = "ExoPlayerVersions"
            r0 = 1
            java.lang.String[] r4 = new java.lang.String[r0]     // Catch:{ SQLException -> 0x0045 }
            java.lang.String r0 = "version"
            r10 = 0
            r4[r10] = r0     // Catch:{ SQLException -> 0x0045 }
            java.lang.String r5 = "feature = ? AND instance_uid = ?"
            java.lang.String[] r6 = featureAndInstanceUidArguments(r12, r13)     // Catch:{ SQLException -> 0x0045 }
            r7 = 0
            r8 = 0
            r9 = 0
            r2 = r11
            android.database.Cursor r11 = r2.query(r3, r4, r5, r6, r7, r8, r9)     // Catch:{ SQLException -> 0x0045 }
            int r12 = r11.getCount()     // Catch:{ all -> 0x003c }
            if (r12 != 0) goto L_0x002f
            if (r11 == 0) goto L_0x002e
            r11.close()     // Catch:{ SQLException -> 0x0045 }
        L_0x002e:
            return r1
        L_0x002f:
            r11.moveToNext()     // Catch:{ all -> 0x003c }
            int r12 = r11.getInt(r10)     // Catch:{ all -> 0x003c }
            if (r11 == 0) goto L_0x003b
            r11.close()     // Catch:{ SQLException -> 0x0045 }
        L_0x003b:
            return r12
        L_0x003c:
            r12 = move-exception
            throw r12     // Catch:{ all -> 0x003e }
        L_0x003e:
            r12 = move-exception
            if (r11 == 0) goto L_0x0044
            r11.close()     // Catch:{ all -> 0x0044 }
        L_0x0044:
            throw r12     // Catch:{ SQLException -> 0x0045 }
        L_0x0045:
            r11 = move-exception
            com.google.android.exoplayer2.database.DatabaseIOException r12 = new com.google.android.exoplayer2.database.DatabaseIOException
            r12.<init>(r11)
            throw r12
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.exoplayer2.database.VersionTable.getVersion(android.database.sqlite.SQLiteDatabase, int, java.lang.String):int");
    }

    static boolean tableExists(SQLiteDatabase sQLiteDatabase, String str) {
        return DatabaseUtils.queryNumEntries(sQLiteDatabase, "sqlite_master", "tbl_name = ?", new String[]{str}) > 0;
    }

    private static String[] featureAndInstanceUidArguments(int i, String str) {
        return new String[]{Integer.toString(i), str};
    }
}
