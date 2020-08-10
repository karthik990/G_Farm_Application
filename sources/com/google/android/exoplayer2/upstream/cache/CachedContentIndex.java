package com.google.android.exoplayer2.upstream.cache;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.SparseArray;
import android.util.SparseBooleanArray;
import com.google.android.exoplayer2.database.DatabaseIOException;
import com.google.android.exoplayer2.database.DatabaseProvider;
import com.google.android.exoplayer2.database.VersionTable;
import com.google.android.exoplayer2.upstream.cache.ContentMetadata.CC;
import com.google.android.exoplayer2.util.Assertions;
import com.google.android.exoplayer2.util.AtomicFile;
import com.google.android.exoplayer2.util.ReusableBufferedOutputStream;
import com.google.android.exoplayer2.util.Util;
import java.io.ByteArrayOutputStream;
import java.io.Closeable;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.Random;
import java.util.Set;
import javax.crypto.Cipher;
import javax.crypto.CipherOutputStream;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

class CachedContentIndex {
    static final String FILE_NAME_ATOMIC = "cached_content_index.exi";
    private static final int INCREMENTAL_METADATA_READ_LENGTH = 10485760;
    private final SparseArray<String> idToKey;
    private final HashMap<String, CachedContent> keyToContent;
    private final SparseBooleanArray newIds;
    private Storage previousStorage;
    private final SparseBooleanArray removedIds;
    private Storage storage;

    private static final class DatabaseStorage implements Storage {
        private static final String[] COLUMNS = {"id", COLUMN_KEY, "metadata"};
        private static final String COLUMN_ID = "id";
        private static final int COLUMN_INDEX_ID = 0;
        private static final int COLUMN_INDEX_KEY = 1;
        private static final int COLUMN_INDEX_METADATA = 2;
        private static final String COLUMN_KEY = "key";
        private static final String COLUMN_METADATA = "metadata";
        private static final String TABLE_PREFIX = "ExoPlayerCacheIndex";
        private static final String TABLE_SCHEMA = "(id INTEGER PRIMARY KEY NOT NULL,key TEXT NOT NULL,metadata BLOB NOT NULL)";
        private static final int TABLE_VERSION = 1;
        private static final String WHERE_ID_EQUALS = "id = ?";
        private final DatabaseProvider databaseProvider;
        private String hexUid;
        private final SparseArray<CachedContent> pendingUpdates = new SparseArray<>();
        private String tableName;

        public static void delete(DatabaseProvider databaseProvider2, long j) throws DatabaseIOException {
            delete(databaseProvider2, Long.toHexString(j));
        }

        public DatabaseStorage(DatabaseProvider databaseProvider2) {
            this.databaseProvider = databaseProvider2;
        }

        public void initialize(long j) {
            this.hexUid = Long.toHexString(j);
            this.tableName = getTableName(this.hexUid);
        }

        public boolean exists() throws DatabaseIOException {
            if (VersionTable.getVersion(this.databaseProvider.getReadableDatabase(), 1, this.hexUid) != -1) {
                return true;
            }
            return false;
        }

        public void delete() throws DatabaseIOException {
            delete(this.databaseProvider, this.hexUid);
        }

        /* JADX WARNING: Code restructure failed: missing block: B:27:0x0075, code lost:
            r1 = move-exception;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:28:0x0076, code lost:
            if (r0 != null) goto L_0x0078;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:30:?, code lost:
            r0.close();
         */
        /* JADX WARNING: Missing exception handler attribute for start block: B:31:0x007b */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public void load(java.util.HashMap<java.lang.String, com.google.android.exoplayer2.upstream.cache.CachedContent> r8, android.util.SparseArray<java.lang.String> r9) throws java.io.IOException {
            /*
                r7 = this;
                android.util.SparseArray<com.google.android.exoplayer2.upstream.cache.CachedContent> r0 = r7.pendingUpdates
                int r0 = r0.size()
                r1 = 0
                r2 = 1
                if (r0 != 0) goto L_0x000c
                r0 = 1
                goto L_0x000d
            L_0x000c:
                r0 = 0
            L_0x000d:
                com.google.android.exoplayer2.util.Assertions.checkState(r0)
                com.google.android.exoplayer2.database.DatabaseProvider r0 = r7.databaseProvider     // Catch:{ SQLiteException -> 0x007c }
                android.database.sqlite.SQLiteDatabase r0 = r0.getReadableDatabase()     // Catch:{ SQLiteException -> 0x007c }
                java.lang.String r3 = r7.hexUid     // Catch:{ SQLiteException -> 0x007c }
                int r0 = com.google.android.exoplayer2.database.VersionTable.getVersion(r0, r2, r3)     // Catch:{ SQLiteException -> 0x007c }
                if (r0 == r2) goto L_0x0036
                com.google.android.exoplayer2.database.DatabaseProvider r0 = r7.databaseProvider     // Catch:{ SQLiteException -> 0x007c }
                android.database.sqlite.SQLiteDatabase r0 = r0.getWritableDatabase()     // Catch:{ SQLiteException -> 0x007c }
                r0.beginTransactionNonExclusive()     // Catch:{ SQLiteException -> 0x007c }
                r7.initializeTable(r0)     // Catch:{ all -> 0x0031 }
                r0.setTransactionSuccessful()     // Catch:{ all -> 0x0031 }
                r0.endTransaction()     // Catch:{ SQLiteException -> 0x007c }
                goto L_0x0036
            L_0x0031:
                r1 = move-exception
                r0.endTransaction()     // Catch:{ SQLiteException -> 0x007c }
                throw r1     // Catch:{ SQLiteException -> 0x007c }
            L_0x0036:
                android.database.Cursor r0 = r7.getCursor()     // Catch:{ SQLiteException -> 0x007c }
            L_0x003a:
                boolean r3 = r0.moveToNext()     // Catch:{ all -> 0x0073 }
                if (r3 == 0) goto L_0x006d
                int r3 = r0.getInt(r1)     // Catch:{ all -> 0x0073 }
                java.lang.String r4 = r0.getString(r2)     // Catch:{ all -> 0x0073 }
                r5 = 2
                byte[] r5 = r0.getBlob(r5)     // Catch:{ all -> 0x0073 }
                java.io.ByteArrayInputStream r6 = new java.io.ByteArrayInputStream     // Catch:{ all -> 0x0073 }
                r6.<init>(r5)     // Catch:{ all -> 0x0073 }
                java.io.DataInputStream r5 = new java.io.DataInputStream     // Catch:{ all -> 0x0073 }
                r5.<init>(r6)     // Catch:{ all -> 0x0073 }
                com.google.android.exoplayer2.upstream.cache.DefaultContentMetadata r5 = com.google.android.exoplayer2.upstream.cache.CachedContentIndex.readContentMetadata(r5)     // Catch:{ all -> 0x0073 }
                com.google.android.exoplayer2.upstream.cache.CachedContent r6 = new com.google.android.exoplayer2.upstream.cache.CachedContent     // Catch:{ all -> 0x0073 }
                r6.<init>(r3, r4, r5)     // Catch:{ all -> 0x0073 }
                java.lang.String r3 = r6.key     // Catch:{ all -> 0x0073 }
                r8.put(r3, r6)     // Catch:{ all -> 0x0073 }
                int r3 = r6.f1529id     // Catch:{ all -> 0x0073 }
                java.lang.String r4 = r6.key     // Catch:{ all -> 0x0073 }
                r9.put(r3, r4)     // Catch:{ all -> 0x0073 }
                goto L_0x003a
            L_0x006d:
                if (r0 == 0) goto L_0x0072
                r0.close()     // Catch:{ SQLiteException -> 0x007c }
            L_0x0072:
                return
            L_0x0073:
                r1 = move-exception
                throw r1     // Catch:{ all -> 0x0075 }
            L_0x0075:
                r1 = move-exception
                if (r0 == 0) goto L_0x007b
                r0.close()     // Catch:{ all -> 0x007b }
            L_0x007b:
                throw r1     // Catch:{ SQLiteException -> 0x007c }
            L_0x007c:
                r0 = move-exception
                r8.clear()
                r9.clear()
                com.google.android.exoplayer2.database.DatabaseIOException r8 = new com.google.android.exoplayer2.database.DatabaseIOException
                r8.<init>(r0)
                goto L_0x008a
            L_0x0089:
                throw r8
            L_0x008a:
                goto L_0x0089
            */
            throw new UnsupportedOperationException("Method not decompiled: com.google.android.exoplayer2.upstream.cache.CachedContentIndex.DatabaseStorage.load(java.util.HashMap, android.util.SparseArray):void");
        }

        public void storeFully(HashMap<String, CachedContent> hashMap) throws IOException {
            SQLiteDatabase writableDatabase;
            try {
                writableDatabase = this.databaseProvider.getWritableDatabase();
                writableDatabase.beginTransactionNonExclusive();
                initializeTable(writableDatabase);
                for (CachedContent addOrUpdateRow : hashMap.values()) {
                    addOrUpdateRow(writableDatabase, addOrUpdateRow);
                }
                writableDatabase.setTransactionSuccessful();
                this.pendingUpdates.clear();
                writableDatabase.endTransaction();
            } catch (SQLException e) {
                throw new DatabaseIOException(e);
            } catch (Throwable th) {
                writableDatabase.endTransaction();
                throw th;
            }
        }

        public void storeIncremental(HashMap<String, CachedContent> hashMap) throws IOException {
            SQLiteDatabase writableDatabase;
            if (this.pendingUpdates.size() != 0) {
                try {
                    writableDatabase = this.databaseProvider.getWritableDatabase();
                    writableDatabase.beginTransactionNonExclusive();
                    for (int i = 0; i < this.pendingUpdates.size(); i++) {
                        CachedContent cachedContent = (CachedContent) this.pendingUpdates.valueAt(i);
                        if (cachedContent == null) {
                            deleteRow(writableDatabase, this.pendingUpdates.keyAt(i));
                        } else {
                            addOrUpdateRow(writableDatabase, cachedContent);
                        }
                    }
                    writableDatabase.setTransactionSuccessful();
                    this.pendingUpdates.clear();
                    writableDatabase.endTransaction();
                } catch (SQLException e) {
                    throw new DatabaseIOException(e);
                } catch (Throwable th) {
                    writableDatabase.endTransaction();
                    throw th;
                }
            }
        }

        public void onUpdate(CachedContent cachedContent) {
            this.pendingUpdates.put(cachedContent.f1529id, cachedContent);
        }

        public void onRemove(CachedContent cachedContent, boolean z) {
            if (z) {
                this.pendingUpdates.delete(cachedContent.f1529id);
            } else {
                this.pendingUpdates.put(cachedContent.f1529id, null);
            }
        }

        private Cursor getCursor() {
            return this.databaseProvider.getReadableDatabase().query(this.tableName, COLUMNS, null, null, null, null, null);
        }

        private void initializeTable(SQLiteDatabase sQLiteDatabase) throws DatabaseIOException {
            VersionTable.setVersion(sQLiteDatabase, 1, this.hexUid, 1);
            dropTable(sQLiteDatabase, this.tableName);
            StringBuilder sb = new StringBuilder();
            sb.append("CREATE TABLE ");
            sb.append(this.tableName);
            sb.append(" ");
            sb.append(TABLE_SCHEMA);
            sQLiteDatabase.execSQL(sb.toString());
        }

        private void deleteRow(SQLiteDatabase sQLiteDatabase, int i) {
            sQLiteDatabase.delete(this.tableName, WHERE_ID_EQUALS, new String[]{Integer.toString(i)});
        }

        private void addOrUpdateRow(SQLiteDatabase sQLiteDatabase, CachedContent cachedContent) throws IOException {
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            CachedContentIndex.writeContentMetadata(cachedContent.getMetadata(), new DataOutputStream(byteArrayOutputStream));
            byte[] byteArray = byteArrayOutputStream.toByteArray();
            ContentValues contentValues = new ContentValues();
            contentValues.put("id", Integer.valueOf(cachedContent.f1529id));
            contentValues.put(COLUMN_KEY, cachedContent.key);
            contentValues.put("metadata", byteArray);
            sQLiteDatabase.replaceOrThrow(this.tableName, null, contentValues);
        }

        private static void delete(DatabaseProvider databaseProvider2, String str) throws DatabaseIOException {
            SQLiteDatabase writableDatabase;
            try {
                String tableName2 = getTableName(str);
                writableDatabase = databaseProvider2.getWritableDatabase();
                writableDatabase.beginTransactionNonExclusive();
                VersionTable.removeVersion(writableDatabase, 1, str);
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

    private static class LegacyStorage implements Storage {
        private static final int FLAG_ENCRYPTED_INDEX = 1;
        private static final int VERSION = 2;
        private static final int VERSION_METADATA_INTRODUCED = 2;
        private final AtomicFile atomicFile;
        private ReusableBufferedOutputStream bufferedOutputStream;
        private boolean changed;
        private final Cipher cipher;
        private final boolean encrypt;
        private final Random random;
        private final SecretKeySpec secretKeySpec;

        public void initialize(long j) {
        }

        public LegacyStorage(File file, byte[] bArr, boolean z) {
            SecretKeySpec secretKeySpec2;
            Cipher cipher2;
            Random random2 = null;
            if (bArr != null) {
                Assertions.checkArgument(bArr.length == 16);
                try {
                    cipher2 = CachedContentIndex.getCipher();
                    secretKeySpec2 = new SecretKeySpec(bArr, "AES");
                } catch (NoSuchAlgorithmException | NoSuchPaddingException e) {
                    throw new IllegalStateException(e);
                }
            } else {
                Assertions.checkArgument(!z);
                cipher2 = null;
                secretKeySpec2 = null;
            }
            this.encrypt = z;
            this.cipher = cipher2;
            this.secretKeySpec = secretKeySpec2;
            if (z) {
                random2 = new Random();
            }
            this.random = random2;
            this.atomicFile = new AtomicFile(file);
        }

        public boolean exists() {
            return this.atomicFile.exists();
        }

        public void delete() {
            this.atomicFile.delete();
        }

        public void load(HashMap<String, CachedContent> hashMap, SparseArray<String> sparseArray) {
            Assertions.checkState(!this.changed);
            if (!readFile(hashMap, sparseArray)) {
                hashMap.clear();
                sparseArray.clear();
                this.atomicFile.delete();
            }
        }

        public void storeFully(HashMap<String, CachedContent> hashMap) throws IOException {
            writeFile(hashMap);
            this.changed = false;
        }

        public void storeIncremental(HashMap<String, CachedContent> hashMap) throws IOException {
            if (this.changed) {
                storeFully(hashMap);
            }
        }

        public void onUpdate(CachedContent cachedContent) {
            this.changed = true;
        }

        public void onRemove(CachedContent cachedContent, boolean z) {
            this.changed = true;
        }

        /* JADX WARNING: Removed duplicated region for block: B:54:0x00ad  */
        /* JADX WARNING: Removed duplicated region for block: B:59:0x00b4  */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        private boolean readFile(java.util.HashMap<java.lang.String, com.google.android.exoplayer2.upstream.cache.CachedContent> r11, android.util.SparseArray<java.lang.String> r12) {
            /*
                r10 = this;
                com.google.android.exoplayer2.util.AtomicFile r0 = r10.atomicFile
                boolean r0 = r0.exists()
                r1 = 1
                if (r0 != 0) goto L_0x000a
                return r1
            L_0x000a:
                r0 = 0
                r2 = 0
                java.io.BufferedInputStream r3 = new java.io.BufferedInputStream     // Catch:{ IOException -> 0x00b1, all -> 0x00a9 }
                com.google.android.exoplayer2.util.AtomicFile r4 = r10.atomicFile     // Catch:{ IOException -> 0x00b1, all -> 0x00a9 }
                java.io.InputStream r4 = r4.openRead()     // Catch:{ IOException -> 0x00b1, all -> 0x00a9 }
                r3.<init>(r4)     // Catch:{ IOException -> 0x00b1, all -> 0x00a9 }
                java.io.DataInputStream r4 = new java.io.DataInputStream     // Catch:{ IOException -> 0x00b1, all -> 0x00a9 }
                r4.<init>(r3)     // Catch:{ IOException -> 0x00b1, all -> 0x00a9 }
                int r0 = r4.readInt()     // Catch:{ IOException -> 0x00a7, all -> 0x00a5 }
                if (r0 < 0) goto L_0x00a1
                r5 = 2
                if (r0 <= r5) goto L_0x0027
                goto L_0x00a1
            L_0x0027:
                int r6 = r4.readInt()     // Catch:{ IOException -> 0x00a7, all -> 0x00a5 }
                r6 = r6 & r1
                if (r6 == 0) goto L_0x0060
                javax.crypto.Cipher r6 = r10.cipher     // Catch:{ IOException -> 0x00a7, all -> 0x00a5 }
                if (r6 != 0) goto L_0x0036
                com.google.android.exoplayer2.util.Util.closeQuietly(r4)
                return r2
            L_0x0036:
                r6 = 16
                byte[] r6 = new byte[r6]     // Catch:{ IOException -> 0x00a7, all -> 0x00a5 }
                r4.readFully(r6)     // Catch:{ IOException -> 0x00a7, all -> 0x00a5 }
                javax.crypto.spec.IvParameterSpec r7 = new javax.crypto.spec.IvParameterSpec     // Catch:{ IOException -> 0x00a7, all -> 0x00a5 }
                r7.<init>(r6)     // Catch:{ IOException -> 0x00a7, all -> 0x00a5 }
                javax.crypto.Cipher r6 = r10.cipher     // Catch:{ InvalidKeyException -> 0x0059, InvalidAlgorithmParameterException -> 0x0057 }
                javax.crypto.spec.SecretKeySpec r8 = r10.secretKeySpec     // Catch:{ InvalidKeyException -> 0x0059, InvalidAlgorithmParameterException -> 0x0057 }
                r6.init(r5, r8, r7)     // Catch:{ InvalidKeyException -> 0x0059, InvalidAlgorithmParameterException -> 0x0057 }
                java.io.DataInputStream r5 = new java.io.DataInputStream     // Catch:{ IOException -> 0x00a7, all -> 0x00a5 }
                javax.crypto.CipherInputStream r6 = new javax.crypto.CipherInputStream     // Catch:{ IOException -> 0x00a7, all -> 0x00a5 }
                javax.crypto.Cipher r7 = r10.cipher     // Catch:{ IOException -> 0x00a7, all -> 0x00a5 }
                r6.<init>(r3, r7)     // Catch:{ IOException -> 0x00a7, all -> 0x00a5 }
                r5.<init>(r6)     // Catch:{ IOException -> 0x00a7, all -> 0x00a5 }
                r4 = r5
                goto L_0x0066
            L_0x0057:
                r11 = move-exception
                goto L_0x005a
            L_0x0059:
                r11 = move-exception
            L_0x005a:
                java.lang.IllegalStateException r12 = new java.lang.IllegalStateException     // Catch:{ IOException -> 0x00a7, all -> 0x00a5 }
                r12.<init>(r11)     // Catch:{ IOException -> 0x00a7, all -> 0x00a5 }
                throw r12     // Catch:{ IOException -> 0x00a7, all -> 0x00a5 }
            L_0x0060:
                boolean r3 = r10.encrypt     // Catch:{ IOException -> 0x00a7, all -> 0x00a5 }
                if (r3 == 0) goto L_0x0066
                r10.changed = r1     // Catch:{ IOException -> 0x00a7, all -> 0x00a5 }
            L_0x0066:
                int r3 = r4.readInt()     // Catch:{ IOException -> 0x00a7, all -> 0x00a5 }
                r5 = 0
                r6 = 0
            L_0x006c:
                if (r5 >= r3) goto L_0x0086
                com.google.android.exoplayer2.upstream.cache.CachedContent r7 = r10.readCachedContent(r0, r4)     // Catch:{ IOException -> 0x00a7, all -> 0x00a5 }
                java.lang.String r8 = r7.key     // Catch:{ IOException -> 0x00a7, all -> 0x00a5 }
                r11.put(r8, r7)     // Catch:{ IOException -> 0x00a7, all -> 0x00a5 }
                int r8 = r7.f1529id     // Catch:{ IOException -> 0x00a7, all -> 0x00a5 }
                java.lang.String r9 = r7.key     // Catch:{ IOException -> 0x00a7, all -> 0x00a5 }
                r12.put(r8, r9)     // Catch:{ IOException -> 0x00a7, all -> 0x00a5 }
                int r7 = r10.hashCachedContent(r7, r0)     // Catch:{ IOException -> 0x00a7, all -> 0x00a5 }
                int r6 = r6 + r7
                int r5 = r5 + 1
                goto L_0x006c
            L_0x0086:
                int r11 = r4.readInt()     // Catch:{ IOException -> 0x00a7, all -> 0x00a5 }
                int r12 = r4.read()     // Catch:{ IOException -> 0x00a7, all -> 0x00a5 }
                r0 = -1
                if (r12 != r0) goto L_0x0093
                r12 = 1
                goto L_0x0094
            L_0x0093:
                r12 = 0
            L_0x0094:
                if (r11 != r6) goto L_0x009d
                if (r12 != 0) goto L_0x0099
                goto L_0x009d
            L_0x0099:
                com.google.android.exoplayer2.util.Util.closeQuietly(r4)
                return r1
            L_0x009d:
                com.google.android.exoplayer2.util.Util.closeQuietly(r4)
                return r2
            L_0x00a1:
                com.google.android.exoplayer2.util.Util.closeQuietly(r4)
                return r2
            L_0x00a5:
                r11 = move-exception
                goto L_0x00ab
            L_0x00a7:
                goto L_0x00b2
            L_0x00a9:
                r11 = move-exception
                r4 = r0
            L_0x00ab:
                if (r4 == 0) goto L_0x00b0
                com.google.android.exoplayer2.util.Util.closeQuietly(r4)
            L_0x00b0:
                throw r11
            L_0x00b1:
                r4 = r0
            L_0x00b2:
                if (r4 == 0) goto L_0x00b7
                com.google.android.exoplayer2.util.Util.closeQuietly(r4)
            L_0x00b7:
                return r2
            */
            throw new UnsupportedOperationException("Method not decompiled: com.google.android.exoplayer2.upstream.cache.CachedContentIndex.LegacyStorage.readFile(java.util.HashMap, android.util.SparseArray):boolean");
        }

        private void writeFile(HashMap<String, CachedContent> hashMap) throws IOException {
            DataOutputStream dataOutputStream;
            try {
                OutputStream startWrite = this.atomicFile.startWrite();
                if (this.bufferedOutputStream == null) {
                    this.bufferedOutputStream = new ReusableBufferedOutputStream(startWrite);
                } else {
                    this.bufferedOutputStream.reset(startWrite);
                }
                dataOutputStream = new DataOutputStream(this.bufferedOutputStream);
                try {
                    dataOutputStream.writeInt(2);
                    int i = 0;
                    dataOutputStream.writeInt(this.encrypt ? 1 : 0);
                    if (this.encrypt) {
                        byte[] bArr = new byte[16];
                        this.random.nextBytes(bArr);
                        dataOutputStream.write(bArr);
                        this.cipher.init(1, this.secretKeySpec, new IvParameterSpec(bArr));
                        dataOutputStream.flush();
                        dataOutputStream = new DataOutputStream(new CipherOutputStream(this.bufferedOutputStream, this.cipher));
                    }
                    dataOutputStream.writeInt(hashMap.size());
                    for (CachedContent cachedContent : hashMap.values()) {
                        writeCachedContent(cachedContent, dataOutputStream);
                        i += hashCachedContent(cachedContent, 2);
                    }
                    dataOutputStream.writeInt(i);
                    this.atomicFile.endWrite(dataOutputStream);
                    Util.closeQuietly((Closeable) null);
                } catch (InvalidKeyException e) {
                    e = e;
                    throw new IllegalStateException(e);
                } catch (InvalidAlgorithmParameterException e2) {
                    e = e2;
                    throw new IllegalStateException(e);
                } catch (Throwable th) {
                    th = th;
                    Util.closeQuietly((Closeable) dataOutputStream);
                    throw th;
                }
            } catch (Throwable th2) {
                th = th2;
                dataOutputStream = null;
                Util.closeQuietly((Closeable) dataOutputStream);
                throw th;
            }
        }

        private int hashCachedContent(CachedContent cachedContent, int i) {
            int hashCode = (cachedContent.f1529id * 31) + cachedContent.key.hashCode();
            if (i >= 2) {
                return (hashCode * 31) + cachedContent.getMetadata().hashCode();
            }
            long contentLength = CC.getContentLength(cachedContent.getMetadata());
            return (hashCode * 31) + ((int) (contentLength ^ (contentLength >>> 32)));
        }

        private CachedContent readCachedContent(int i, DataInputStream dataInputStream) throws IOException {
            DefaultContentMetadata defaultContentMetadata;
            int readInt = dataInputStream.readInt();
            String readUTF = dataInputStream.readUTF();
            if (i < 2) {
                long readLong = dataInputStream.readLong();
                ContentMetadataMutations contentMetadataMutations = new ContentMetadataMutations();
                ContentMetadataMutations.setContentLength(contentMetadataMutations, readLong);
                defaultContentMetadata = DefaultContentMetadata.EMPTY.copyWithMutationsApplied(contentMetadataMutations);
            } else {
                defaultContentMetadata = CachedContentIndex.readContentMetadata(dataInputStream);
            }
            return new CachedContent(readInt, readUTF, defaultContentMetadata);
        }

        private void writeCachedContent(CachedContent cachedContent, DataOutputStream dataOutputStream) throws IOException {
            dataOutputStream.writeInt(cachedContent.f1529id);
            dataOutputStream.writeUTF(cachedContent.key);
            CachedContentIndex.writeContentMetadata(cachedContent.getMetadata(), dataOutputStream);
        }
    }

    private interface Storage {
        void delete() throws IOException;

        boolean exists() throws IOException;

        void initialize(long j);

        void load(HashMap<String, CachedContent> hashMap, SparseArray<String> sparseArray) throws IOException;

        void onRemove(CachedContent cachedContent, boolean z);

        void onUpdate(CachedContent cachedContent);

        void storeFully(HashMap<String, CachedContent> hashMap) throws IOException;

        void storeIncremental(HashMap<String, CachedContent> hashMap) throws IOException;
    }

    public static boolean isIndexFile(String str) {
        return str.startsWith(FILE_NAME_ATOMIC);
    }

    public static void delete(DatabaseProvider databaseProvider, long j) throws DatabaseIOException {
        DatabaseStorage.delete(databaseProvider, j);
    }

    public CachedContentIndex(DatabaseProvider databaseProvider) {
        this(databaseProvider, null, null, false, false);
    }

    public CachedContentIndex(DatabaseProvider databaseProvider, File file, byte[] bArr, boolean z, boolean z2) {
        Assertions.checkState((databaseProvider == null && file == null) ? false : true);
        this.keyToContent = new HashMap<>();
        this.idToKey = new SparseArray<>();
        this.removedIds = new SparseBooleanArray();
        this.newIds = new SparseBooleanArray();
        LegacyStorage legacyStorage = null;
        Storage databaseStorage = databaseProvider != null ? new DatabaseStorage(databaseProvider) : null;
        if (file != null) {
            legacyStorage = new LegacyStorage(new File(file, FILE_NAME_ATOMIC), bArr, z);
        }
        if (databaseStorage == null || (legacyStorage != null && z2)) {
            this.storage = legacyStorage;
            this.previousStorage = databaseStorage;
            return;
        }
        this.storage = databaseStorage;
        this.previousStorage = legacyStorage;
    }

    /* JADX WARNING: Removed duplicated region for block: B:13:0x003c  */
    /* JADX WARNING: Removed duplicated region for block: B:15:? A[RETURN, SYNTHETIC] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void initialize(long r2) throws java.io.IOException {
        /*
            r1 = this;
            com.google.android.exoplayer2.upstream.cache.CachedContentIndex$Storage r0 = r1.storage
            r0.initialize(r2)
            com.google.android.exoplayer2.upstream.cache.CachedContentIndex$Storage r0 = r1.previousStorage
            if (r0 == 0) goto L_0x000c
            r0.initialize(r2)
        L_0x000c:
            com.google.android.exoplayer2.upstream.cache.CachedContentIndex$Storage r2 = r1.storage
            boolean r2 = r2.exists()
            if (r2 != 0) goto L_0x002f
            com.google.android.exoplayer2.upstream.cache.CachedContentIndex$Storage r2 = r1.previousStorage
            if (r2 == 0) goto L_0x002f
            boolean r2 = r2.exists()
            if (r2 == 0) goto L_0x002f
            com.google.android.exoplayer2.upstream.cache.CachedContentIndex$Storage r2 = r1.previousStorage
            java.util.HashMap<java.lang.String, com.google.android.exoplayer2.upstream.cache.CachedContent> r3 = r1.keyToContent
            android.util.SparseArray<java.lang.String> r0 = r1.idToKey
            r2.load(r3, r0)
            com.google.android.exoplayer2.upstream.cache.CachedContentIndex$Storage r2 = r1.storage
            java.util.HashMap<java.lang.String, com.google.android.exoplayer2.upstream.cache.CachedContent> r3 = r1.keyToContent
            r2.storeFully(r3)
            goto L_0x0038
        L_0x002f:
            com.google.android.exoplayer2.upstream.cache.CachedContentIndex$Storage r2 = r1.storage
            java.util.HashMap<java.lang.String, com.google.android.exoplayer2.upstream.cache.CachedContent> r3 = r1.keyToContent
            android.util.SparseArray<java.lang.String> r0 = r1.idToKey
            r2.load(r3, r0)
        L_0x0038:
            com.google.android.exoplayer2.upstream.cache.CachedContentIndex$Storage r2 = r1.previousStorage
            if (r2 == 0) goto L_0x0042
            r2.delete()
            r2 = 0
            r1.previousStorage = r2
        L_0x0042:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.exoplayer2.upstream.cache.CachedContentIndex.initialize(long):void");
    }

    public void store() throws IOException {
        this.storage.storeIncremental(this.keyToContent);
        int size = this.removedIds.size();
        for (int i = 0; i < size; i++) {
            this.idToKey.remove(this.removedIds.keyAt(i));
        }
        this.removedIds.clear();
        this.newIds.clear();
    }

    public CachedContent getOrAdd(String str) {
        CachedContent cachedContent = (CachedContent) this.keyToContent.get(str);
        return cachedContent == null ? addNew(str) : cachedContent;
    }

    public CachedContent get(String str) {
        return (CachedContent) this.keyToContent.get(str);
    }

    public Collection<CachedContent> getAll() {
        return this.keyToContent.values();
    }

    public int assignIdForKey(String str) {
        return getOrAdd(str).f1529id;
    }

    public String getKeyForId(int i) {
        return (String) this.idToKey.get(i);
    }

    public void maybeRemove(String str) {
        CachedContent cachedContent = (CachedContent) this.keyToContent.get(str);
        if (cachedContent != null && cachedContent.isEmpty() && !cachedContent.isLocked()) {
            this.keyToContent.remove(str);
            int i = cachedContent.f1529id;
            boolean z = this.newIds.get(i);
            this.storage.onRemove(cachedContent, z);
            if (z) {
                this.idToKey.remove(i);
                this.newIds.delete(i);
                return;
            }
            this.idToKey.put(i, null);
            this.removedIds.put(i, true);
        }
    }

    public void removeEmpty() {
        String[] strArr = new String[this.keyToContent.size()];
        this.keyToContent.keySet().toArray(strArr);
        for (String maybeRemove : strArr) {
            maybeRemove(maybeRemove);
        }
    }

    public Set<String> getKeys() {
        return this.keyToContent.keySet();
    }

    public void applyContentMetadataMutations(String str, ContentMetadataMutations contentMetadataMutations) {
        CachedContent orAdd = getOrAdd(str);
        if (orAdd.applyMetadataMutations(contentMetadataMutations)) {
            this.storage.onUpdate(orAdd);
        }
    }

    public ContentMetadata getContentMetadata(String str) {
        CachedContent cachedContent = get(str);
        return cachedContent != null ? cachedContent.getMetadata() : DefaultContentMetadata.EMPTY;
    }

    private CachedContent addNew(String str) {
        int newId = getNewId(this.idToKey);
        CachedContent cachedContent = new CachedContent(newId, str);
        this.keyToContent.put(str, cachedContent);
        this.idToKey.put(newId, str);
        this.newIds.put(newId, true);
        this.storage.onUpdate(cachedContent);
        return cachedContent;
    }

    /* access modifiers changed from: private */
    public static Cipher getCipher() throws NoSuchPaddingException, NoSuchAlgorithmException {
        String str = "AES/CBC/PKCS5PADDING";
        if (Util.SDK_INT == 18) {
            try {
                return Cipher.getInstance(str, "BC");
            } catch (Throwable unused) {
            }
        }
        return Cipher.getInstance(str);
    }

    static int getNewId(SparseArray<String> sparseArray) {
        int i;
        int size = sparseArray.size();
        if (size == 0) {
            i = 0;
        } else {
            i = sparseArray.keyAt(size - 1) + 1;
        }
        if (i < 0) {
            int i2 = 0;
            while (i < size && i == sparseArray.keyAt(i)) {
                i2 = i + 1;
            }
        }
        return i;
    }

    /* access modifiers changed from: private */
    public static DefaultContentMetadata readContentMetadata(DataInputStream dataInputStream) throws IOException {
        int readInt = dataInputStream.readInt();
        HashMap hashMap = new HashMap();
        int i = 0;
        while (i < readInt) {
            String readUTF = dataInputStream.readUTF();
            int readInt2 = dataInputStream.readInt();
            if (readInt2 >= 0) {
                int min = Math.min(readInt2, 10485760);
                byte[] bArr = Util.EMPTY_BYTE_ARRAY;
                int i2 = min;
                int i3 = 0;
                while (i3 != readInt2) {
                    int i4 = i3 + i2;
                    bArr = Arrays.copyOf(bArr, i4);
                    dataInputStream.readFully(bArr, i3, i2);
                    i2 = Math.min(readInt2 - i4, 10485760);
                    i3 = i4;
                }
                hashMap.put(readUTF, bArr);
                i++;
            } else {
                StringBuilder sb = new StringBuilder();
                sb.append("Invalid value size: ");
                sb.append(readInt2);
                throw new IOException(sb.toString());
            }
        }
        return new DefaultContentMetadata(hashMap);
    }

    /* access modifiers changed from: private */
    public static void writeContentMetadata(DefaultContentMetadata defaultContentMetadata, DataOutputStream dataOutputStream) throws IOException {
        Set<Entry> entrySet = defaultContentMetadata.entrySet();
        dataOutputStream.writeInt(entrySet.size());
        for (Entry entry : entrySet) {
            dataOutputStream.writeUTF((String) entry.getKey());
            byte[] bArr = (byte[]) entry.getValue();
            dataOutputStream.writeInt(bArr.length);
            dataOutputStream.write(bArr);
        }
    }
}
