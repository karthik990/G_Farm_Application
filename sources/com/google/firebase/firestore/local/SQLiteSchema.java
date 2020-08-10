package com.google.firebase.firestore.local;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.text.TextUtils;
import android.util.Log;
import com.google.common.base.Preconditions;
import com.google.firebase.firestore.model.ResourcePath;
import com.google.firebase.firestore.util.Assert;
import com.google.firebase.firestore.util.Consumer;

/* compiled from: com.google.firebase:firebase-firestore@@19.0.0 */
class SQLiteSchema {
    static final int INDEXING_SUPPORT_VERSION = 9;
    static final int VERSION = 8;

    /* renamed from: db */
    private final SQLiteDatabase f2034db;

    SQLiteSchema(SQLiteDatabase sQLiteDatabase) {
        this.f2034db = sQLiteDatabase;
    }

    /* access modifiers changed from: 0000 */
    public void runMigrations() {
        runMigrations(0, 8);
    }

    /* access modifiers changed from: 0000 */
    public void runMigrations(int i) {
        runMigrations(i, 8);
    }

    /* access modifiers changed from: 0000 */
    public void runMigrations(int i, int i2) {
        if (i < 1 && i2 >= 1) {
            createV1MutationQueue();
            createV1QueryCache();
            createV1RemoteDocumentCache();
        }
        if (i < 3 && i2 >= 3 && i != 0) {
            dropV1QueryCache();
            createV1QueryCache();
        }
        if (i < 4 && i2 >= 4) {
            ensureTargetGlobal();
            addTargetCount();
        }
        if (i < 5 && i2 >= 5) {
            addSequenceNumber();
        }
        if (i < 6 && i2 >= 6) {
            removeAcknowledgedMutations();
        }
        if (i < 7 && i2 >= 7) {
            ensureSequenceNumbers();
        }
        if (i < 8 && i2 >= 8) {
            createV8CollectionParentsIndex();
        }
        if (i < 9 && i2 >= 9) {
            Preconditions.checkState(Persistence.INDEXING_SUPPORT_ENABLED);
            createLocalDocumentsCollectionIndex();
        }
    }

    private void ifTablesDontExist(String[] strArr, Runnable runnable) {
        String str;
        StringBuilder sb = new StringBuilder();
        sb.append("[");
        sb.append(TextUtils.join(", ", strArr));
        sb.append("]");
        String sb2 = sb.toString();
        boolean z = false;
        for (int i = 0; i < strArr.length; i++) {
            String str2 = strArr[i];
            boolean tableExists = tableExists(str2);
            if (i == 0) {
                z = tableExists;
            } else if (tableExists != z) {
                StringBuilder sb3 = new StringBuilder();
                sb3.append("Expected all of ");
                sb3.append(sb2);
                sb3.append(" to either exist or not, but ");
                String sb4 = sb3.toString();
                if (z) {
                    StringBuilder sb5 = new StringBuilder();
                    sb5.append(sb4);
                    sb5.append(strArr[0]);
                    sb5.append(" exists and ");
                    sb5.append(str2);
                    sb5.append(" does not");
                    str = sb5.toString();
                } else {
                    StringBuilder sb6 = new StringBuilder();
                    sb6.append(sb4);
                    sb6.append(strArr[0]);
                    sb6.append(" does not exist and ");
                    sb6.append(str2);
                    sb6.append(" does");
                    str = sb6.toString();
                }
                throw new IllegalStateException(str);
            }
        }
        if (!z) {
            runnable.run();
            return;
        }
        StringBuilder sb7 = new StringBuilder();
        sb7.append("Skipping migration because all of ");
        sb7.append(sb2);
        sb7.append(" already exist");
        Log.d("SQLiteSchema", sb7.toString());
    }

    private void createV1MutationQueue() {
        ifTablesDontExist(new String[]{"mutation_queues", "mutations", "document_mutations"}, SQLiteSchema$$Lambda$1.lambdaFactory$(this));
    }

    static /* synthetic */ void lambda$createV1MutationQueue$0(SQLiteSchema sQLiteSchema) {
        sQLiteSchema.f2034db.execSQL("CREATE TABLE mutation_queues (uid TEXT PRIMARY KEY, last_acknowledged_batch_id INTEGER, last_stream_token BLOB)");
        sQLiteSchema.f2034db.execSQL("CREATE TABLE mutations (uid TEXT, batch_id INTEGER, mutations BLOB, PRIMARY KEY (uid, batch_id))");
        sQLiteSchema.f2034db.execSQL("CREATE TABLE document_mutations (uid TEXT, path TEXT, batch_id INTEGER, PRIMARY KEY (uid, path, batch_id))");
    }

    private void removeAcknowledgedMutations() {
        new Query(this.f2034db, "SELECT uid, last_acknowledged_batch_id FROM mutation_queues").forEach(SQLiteSchema$$Lambda$2.lambdaFactory$(this));
    }

    static /* synthetic */ void lambda$removeAcknowledgedMutations$2(SQLiteSchema sQLiteSchema, Cursor cursor) {
        String string = cursor.getString(0);
        long j = cursor.getLong(1);
        new Query(sQLiteSchema.f2034db, "SELECT batch_id FROM mutations WHERE uid = ? AND batch_id <= ?").binding(string, Long.valueOf(j)).forEach(SQLiteSchema$$Lambda$12.lambdaFactory$(sQLiteSchema, string));
    }

    /* access modifiers changed from: private */
    public void removeMutationBatch(String str, int i) {
        SQLiteStatement compileStatement = this.f2034db.compileStatement("DELETE FROM mutations WHERE uid = ? AND batch_id = ?");
        compileStatement.bindString(1, str);
        compileStatement.bindLong(2, (long) i);
        Assert.hardAssert(compileStatement.executeUpdateDelete() != 0, "Mutatiohn batch (%s, %d) did not exist", str, Integer.valueOf(i));
        this.f2034db.execSQL("DELETE FROM document_mutations WHERE uid = ? AND batch_id = ?", new Object[]{str, Integer.valueOf(i)});
    }

    private void createV1QueryCache() {
        ifTablesDontExist(new String[]{"targets", "target_globals", "target_documents"}, SQLiteSchema$$Lambda$3.lambdaFactory$(this));
    }

    static /* synthetic */ void lambda$createV1QueryCache$3(SQLiteSchema sQLiteSchema) {
        sQLiteSchema.f2034db.execSQL("CREATE TABLE targets (target_id INTEGER PRIMARY KEY, canonical_id TEXT, snapshot_version_seconds INTEGER, snapshot_version_nanos INTEGER, resume_token BLOB, last_listen_sequence_number INTEGER,target_proto BLOB)");
        sQLiteSchema.f2034db.execSQL("CREATE INDEX query_targets ON targets (canonical_id, target_id)");
        sQLiteSchema.f2034db.execSQL("CREATE TABLE target_globals (highest_target_id INTEGER, highest_listen_sequence_number INTEGER, last_remote_snapshot_version_seconds INTEGER, last_remote_snapshot_version_nanos INTEGER)");
        sQLiteSchema.f2034db.execSQL("CREATE TABLE target_documents (target_id INTEGER, path TEXT, PRIMARY KEY (target_id, path))");
        sQLiteSchema.f2034db.execSQL("CREATE INDEX document_targets ON target_documents (path, target_id)");
    }

    private void dropV1QueryCache() {
        if (tableExists("targets")) {
            this.f2034db.execSQL("DROP TABLE targets");
        }
        if (tableExists("target_globals")) {
            this.f2034db.execSQL("DROP TABLE target_globals");
        }
        if (tableExists("target_documents")) {
            this.f2034db.execSQL("DROP TABLE target_documents");
        }
    }

    private void createV1RemoteDocumentCache() {
        ifTablesDontExist(new String[]{"remote_documents"}, SQLiteSchema$$Lambda$4.lambdaFactory$(this));
    }

    private void createLocalDocumentsCollectionIndex() {
        ifTablesDontExist(new String[]{"collection_index"}, SQLiteSchema$$Lambda$5.lambdaFactory$(this));
    }

    private void ensureTargetGlobal() {
        if (!(DatabaseUtils.queryNumEntries(this.f2034db, "target_globals") == 1)) {
            String str = "0";
            this.f2034db.execSQL("INSERT INTO target_globals (highest_target_id, highest_listen_sequence_number, last_remote_snapshot_version_seconds, last_remote_snapshot_version_nanos) VALUES (?, ?, ?, ?)", new String[]{str, str, str, str});
        }
    }

    private void addTargetCount() {
        String str = "target_count";
        String str2 = "target_globals";
        if (!tableContainsColumn(str2, str)) {
            this.f2034db.execSQL("ALTER TABLE target_globals ADD COLUMN target_count INTEGER");
        }
        long queryNumEntries = DatabaseUtils.queryNumEntries(this.f2034db, "targets");
        ContentValues contentValues = new ContentValues();
        contentValues.put(str, Long.valueOf(queryNumEntries));
        this.f2034db.update(str2, contentValues, null, null);
    }

    private void addSequenceNumber() {
        if (!tableContainsColumn("target_documents", "sequence_number")) {
            this.f2034db.execSQL("ALTER TABLE target_documents ADD COLUMN sequence_number INTEGER");
        }
    }

    private void ensureSequenceNumbers() {
        Long l = (Long) new Query(this.f2034db, "SELECT highest_listen_sequence_number FROM target_globals LIMIT 1").firstValue(SQLiteSchema$$Lambda$6.lambdaFactory$());
        Assert.hardAssert(l != null, "Missing highest sequence number", new Object[0]);
        new Query(this.f2034db, "SELECT RD.path FROM remote_documents AS RD WHERE NOT EXISTS (SELECT TD.path FROM target_documents AS TD WHERE RD.path = TD.path AND TD.target_id = 0)").forEach(SQLiteSchema$$Lambda$7.lambdaFactory$(this.f2034db.compileStatement("INSERT INTO target_documents (target_id, path, sequence_number) VALUES (0, ?, ?)"), l.longValue()));
    }

    static /* synthetic */ void lambda$ensureSequenceNumbers$7(SQLiteStatement sQLiteStatement, long j, Cursor cursor) {
        sQLiteStatement.clearBindings();
        boolean z = true;
        sQLiteStatement.bindString(1, cursor.getString(0));
        sQLiteStatement.bindLong(2, j);
        if (sQLiteStatement.executeInsert() == -1) {
            z = false;
        }
        Assert.hardAssert(z, "Failed to insert a sentinel row", new Object[0]);
    }

    private void createV8CollectionParentsIndex() {
        ifTablesDontExist(new String[]{"collection_parents"}, SQLiteSchema$$Lambda$8.lambdaFactory$(this));
        Consumer lambdaFactory$ = SQLiteSchema$$Lambda$9.lambdaFactory$(new MemoryCollectionParentIndex(), this.f2034db.compileStatement("INSERT OR REPLACE INTO collection_parents (collection_id, parent) VALUES (?, ?)"));
        new Query(this.f2034db, "SELECT path FROM remote_documents").forEach(SQLiteSchema$$Lambda$10.lambdaFactory$(lambdaFactory$));
        new Query(this.f2034db, "SELECT path FROM document_mutations").forEach(SQLiteSchema$$Lambda$11.lambdaFactory$(lambdaFactory$));
    }

    static /* synthetic */ void lambda$createV8CollectionParentsIndex$9(MemoryCollectionParentIndex memoryCollectionParentIndex, SQLiteStatement sQLiteStatement, ResourcePath resourcePath) {
        if (memoryCollectionParentIndex.add(resourcePath)) {
            String lastSegment = resourcePath.getLastSegment();
            ResourcePath resourcePath2 = (ResourcePath) resourcePath.popLast();
            sQLiteStatement.clearBindings();
            sQLiteStatement.bindString(1, lastSegment);
            sQLiteStatement.bindString(2, EncodedPath.encode(resourcePath2));
            sQLiteStatement.execute();
        }
    }

    private boolean tableContainsColumn(String str, String str2) {
        return getTableColumns(str).indexOf(str2) != -1;
    }

    /* JADX INFO: finally extract failed */
    /* JADX WARNING: type inference failed for: r1v0, types: [java.lang.String[], android.database.Cursor] */
    /* access modifiers changed from: 0000 */
    /* JADX WARNING: Multi-variable type inference failed. Error: jadx.core.utils.exceptions.JadxRuntimeException: No candidate types for var: r1v0, types: [java.lang.String[], android.database.Cursor]
      assigns: [?[int, float, boolean, short, byte, char, OBJECT, ARRAY]]
      uses: [?[int, boolean, OBJECT, ARRAY, byte, short, char], android.database.Cursor, java.lang.String[]]
      mth insns count: 24
    	at jadx.core.dex.visitors.typeinference.TypeSearch.fillTypeCandidates(TypeSearch.java:237)
    	at java.base/java.util.ArrayList.forEach(ArrayList.java:1540)
    	at jadx.core.dex.visitors.typeinference.TypeSearch.run(TypeSearch.java:53)
    	at jadx.core.dex.visitors.typeinference.TypeInferenceVisitor.runMultiVariableSearch(TypeInferenceVisitor.java:99)
    	at jadx.core.dex.visitors.typeinference.TypeInferenceVisitor.visit(TypeInferenceVisitor.java:92)
    	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:27)
    	at jadx.core.dex.visitors.DepthTraversal.lambda$visit$1(DepthTraversal.java:14)
    	at java.base/java.util.ArrayList.forEach(ArrayList.java:1540)
    	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:14)
    	at jadx.core.ProcessClass.process(ProcessClass.java:30)
    	at jadx.core.ProcessClass.lambda$processDependencies$0(ProcessClass.java:49)
    	at java.base/java.util.ArrayList.forEach(ArrayList.java:1540)
    	at jadx.core.ProcessClass.processDependencies(ProcessClass.java:49)
    	at jadx.core.ProcessClass.process(ProcessClass.java:35)
    	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:311)
    	at jadx.api.JavaClass.decompile(JavaClass.java:62)
    	at jadx.api.JadxDecompiler.lambda$appendSourcesSave$0(JadxDecompiler.java:217)
     */
    /* JADX WARNING: Unknown variable types count: 1 */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public java.util.List<java.lang.String> getTableColumns(java.lang.String r6) {
        /*
            r5 = this;
            java.util.ArrayList r0 = new java.util.ArrayList
            r0.<init>()
            r1 = 0
            android.database.sqlite.SQLiteDatabase r2 = r5.f2034db     // Catch:{ all -> 0x003c }
            java.lang.StringBuilder r3 = new java.lang.StringBuilder     // Catch:{ all -> 0x003c }
            r3.<init>()     // Catch:{ all -> 0x003c }
            java.lang.String r4 = "PRAGMA table_info("
            r3.append(r4)     // Catch:{ all -> 0x003c }
            r3.append(r6)     // Catch:{ all -> 0x003c }
            java.lang.String r6 = ")"
            r3.append(r6)     // Catch:{ all -> 0x003c }
            java.lang.String r6 = r3.toString()     // Catch:{ all -> 0x003c }
            android.database.Cursor r1 = r2.rawQuery(r6, r1)     // Catch:{ all -> 0x003c }
            java.lang.String r6 = "name"
            int r6 = r1.getColumnIndex(r6)     // Catch:{ all -> 0x003c }
        L_0x0028:
            boolean r2 = r1.moveToNext()     // Catch:{ all -> 0x003c }
            if (r2 == 0) goto L_0x0036
            java.lang.String r2 = r1.getString(r6)     // Catch:{ all -> 0x003c }
            r0.add(r2)     // Catch:{ all -> 0x003c }
            goto L_0x0028
        L_0x0036:
            if (r1 == 0) goto L_0x003b
            r1.close()
        L_0x003b:
            return r0
        L_0x003c:
            r6 = move-exception
            if (r1 == 0) goto L_0x0042
            r1.close()
        L_0x0042:
            goto L_0x0044
        L_0x0043:
            throw r6
        L_0x0044:
            goto L_0x0043
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.firebase.firestore.local.SQLiteSchema.getTableColumns(java.lang.String):java.util.List");
    }

    private boolean tableExists(String str) {
        return !new Query(this.f2034db, "SELECT 1=1 FROM sqlite_master WHERE tbl_name = ?").binding(str).isEmpty();
    }
}
