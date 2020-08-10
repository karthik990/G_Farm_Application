package com.google.android.exoplayer2.upstream.cache;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import com.google.android.exoplayer2.database.DatabaseIOException;
import com.google.android.exoplayer2.database.DatabaseProvider;
import com.google.android.exoplayer2.database.VersionTable;
import com.google.android.exoplayer2.util.Assertions;
import java.util.Set;

final class CacheFileMetadataIndex {
    private static final String[] COLUMNS = {"name", COLUMN_LENGTH, COLUMN_LAST_TOUCH_TIMESTAMP};
    private static final int COLUMN_INDEX_LAST_TOUCH_TIMESTAMP = 2;
    private static final int COLUMN_INDEX_LENGTH = 1;
    private static final int COLUMN_INDEX_NAME = 0;
    private static final String COLUMN_LAST_TOUCH_TIMESTAMP = "last_touch_timestamp";
    private static final String COLUMN_LENGTH = "length";
    private static final String COLUMN_NAME = "name";
    private static final String TABLE_PREFIX = "ExoPlayerCacheFileMetadata";
    private static final String TABLE_SCHEMA = "(name TEXT PRIMARY KEY NOT NULL,length INTEGER NOT NULL,last_touch_timestamp INTEGER NOT NULL)";
    private static final int TABLE_VERSION = 1;
    private static final String WHERE_NAME_EQUALS = "name = ?";
    private final DatabaseProvider databaseProvider;
    private String tableName;

    public static void delete(DatabaseProvider databaseProvider2, long j) throws DatabaseIOException {
        SQLiteDatabase writableDatabase;
        String hexString = Long.toHexString(j);
        try {
            String tableName2 = getTableName(hexString);
            writableDatabase = databaseProvider2.getWritableDatabase();
            writableDatabase.beginTransactionNonExclusive();
            VersionTable.removeVersion(writableDatabase, 2, hexString);
            dropTable(writableDatabase, tableName2);
            writableDatabase.setTransactionSuccessful();
            writableDatabase.endTransaction();
        } catch (SQLException e) {
            throw new DatabaseIOException(e);
        } catch (Throwable th) {
            writableDatabase.endTransaction();
            throw th;
        }
    }

    public CacheFileMetadataIndex(DatabaseProvider databaseProvider2) {
        this.databaseProvider = databaseProvider2;
    }

    public void initialize(long j) throws DatabaseIOException {
        SQLiteDatabase writableDatabase;
        try {
            String hexString = Long.toHexString(j);
            this.tableName = getTableName(hexString);
            if (VersionTable.getVersion(this.databaseProvider.getReadableDatabase(), 2, hexString) != 1) {
                writableDatabase = this.databaseProvider.getWritableDatabase();
                writableDatabase.beginTransactionNonExclusive();
                VersionTable.setVersion(writableDatabase, 2, hexString, 1);
                dropTable(writableDatabase, this.tableName);
                StringBuilder sb = new StringBuilder();
                sb.append("CREATE TABLE ");
                sb.append(this.tableName);
                sb.append(" ");
                sb.append(TABLE_SCHEMA);
                writableDatabase.execSQL(sb.toString());
                writableDatabase.setTransactionSuccessful();
                writableDatabase.endTransaction();
            }
        } catch (SQLException e) {
            throw new DatabaseIOException(e);
        } catch (Throwable th) {
            writableDatabase.endTransaction();
            throw th;
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:14:0x0033, code lost:
        r1 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:15:0x0034, code lost:
        if (r0 != null) goto L_0x0036;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:17:?, code lost:
        r0.close();
     */
    /* JADX WARNING: Missing exception handler attribute for start block: B:18:0x0039 */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public java.util.Map<java.lang.String, com.google.android.exoplayer2.upstream.cache.CacheFileMetadata> getAll() throws com.google.android.exoplayer2.database.DatabaseIOException {
        /*
            r8 = this;
            android.database.Cursor r0 = r8.getCursor()     // Catch:{ SQLException -> 0x003a }
            java.util.HashMap r1 = new java.util.HashMap     // Catch:{ all -> 0x0031 }
            int r2 = r0.getCount()     // Catch:{ all -> 0x0031 }
            r1.<init>(r2)     // Catch:{ all -> 0x0031 }
        L_0x000d:
            boolean r2 = r0.moveToNext()     // Catch:{ all -> 0x0031 }
            if (r2 == 0) goto L_0x002b
            r2 = 0
            java.lang.String r2 = r0.getString(r2)     // Catch:{ all -> 0x0031 }
            r3 = 1
            long r3 = r0.getLong(r3)     // Catch:{ all -> 0x0031 }
            r5 = 2
            long r5 = r0.getLong(r5)     // Catch:{ all -> 0x0031 }
            com.google.android.exoplayer2.upstream.cache.CacheFileMetadata r7 = new com.google.android.exoplayer2.upstream.cache.CacheFileMetadata     // Catch:{ all -> 0x0031 }
            r7.<init>(r3, r5)     // Catch:{ all -> 0x0031 }
            r1.put(r2, r7)     // Catch:{ all -> 0x0031 }
            goto L_0x000d
        L_0x002b:
            if (r0 == 0) goto L_0x0030
            r0.close()     // Catch:{ SQLException -> 0x003a }
        L_0x0030:
            return r1
        L_0x0031:
            r1 = move-exception
            throw r1     // Catch:{ all -> 0x0033 }
        L_0x0033:
            r1 = move-exception
            if (r0 == 0) goto L_0x0039
            r0.close()     // Catch:{ all -> 0x0039 }
        L_0x0039:
            throw r1     // Catch:{ SQLException -> 0x003a }
        L_0x003a:
            r0 = move-exception
            com.google.android.exoplayer2.database.DatabaseIOException r1 = new com.google.android.exoplayer2.database.DatabaseIOException
            r1.<init>(r0)
            goto L_0x0042
        L_0x0041:
            throw r1
        L_0x0042:
            goto L_0x0041
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.exoplayer2.upstream.cache.CacheFileMetadataIndex.getAll():java.util.Map");
    }

    public void set(String str, long j, long j2) throws DatabaseIOException {
        Assertions.checkNotNull(this.tableName);
        try {
            SQLiteDatabase writableDatabase = this.databaseProvider.getWritableDatabase();
            ContentValues contentValues = new ContentValues();
            contentValues.put("name", str);
            contentValues.put(COLUMN_LENGTH, Long.valueOf(j));
            contentValues.put(COLUMN_LAST_TOUCH_TIMESTAMP, Long.valueOf(j2));
            writableDatabase.replaceOrThrow(this.tableName, null, contentValues);
        } catch (SQLException e) {
            throw new DatabaseIOException(e);
        }
    }

    public void remove(String str) throws DatabaseIOException {
        Assertions.checkNotNull(this.tableName);
        try {
            this.databaseProvider.getWritableDatabase().delete(this.tableName, WHERE_NAME_EQUALS, new String[]{str});
        } catch (SQLException e) {
            throw new DatabaseIOException(e);
        }
    }

    public void removeAll(Set<String> set) throws DatabaseIOException {
        SQLiteDatabase writableDatabase;
        Assertions.checkNotNull(this.tableName);
        try {
            writableDatabase = this.databaseProvider.getWritableDatabase();
            writableDatabase.beginTransactionNonExclusive();
            for (String str : set) {
                writableDatabase.delete(this.tableName, WHERE_NAME_EQUALS, new String[]{str});
            }
            writableDatabase.setTransactionSuccessful();
            writableDatabase.endTransaction();
        } catch (SQLException e) {
            throw new DatabaseIOException(e);
        } catch (Throwable th) {
            writableDatabase.endTransaction();
            throw th;
        }
    }

    private Cursor getCursor() {
        Assertions.checkNotNull(this.tableName);
        return this.databaseProvider.getReadableDatabase().query(this.tableName, COLUMNS, null, null, null, null, null);
    }

    private static void dropTable(SQLiteDatabase sQLiteDatabase, String str) {
        StringBuilder sb = new StringBuilder();
        sb.append("DROP TABLE IF EXISTS ");
        sb.append(str);
        sQLiteDatabase.execSQL(sb.toString());
    }

    private static String getTableName(String str) {
        StringBuilder sb = new StringBuilder();
        sb.append(TABLE_PREFIX);
        sb.append(str);
        return sb.toString();
    }
}
