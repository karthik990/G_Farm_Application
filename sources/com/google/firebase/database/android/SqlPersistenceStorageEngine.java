package com.google.firebase.database.android;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabaseLockedException;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import com.google.firebase.database.DatabaseException;
import com.google.firebase.database.core.CompoundWrite;
import com.google.firebase.database.core.Path;
import com.google.firebase.database.core.UserWriteRecord;
import com.google.firebase.database.core.persistence.PersistenceStorageEngine;
import com.google.firebase.database.core.persistence.PruneForest;
import com.google.firebase.database.core.persistence.TrackedQuery;
import com.google.firebase.database.core.utilities.ImmutableTree;
import com.google.firebase.database.core.utilities.ImmutableTree.TreeVisitor;
import com.google.firebase.database.core.utilities.NodeSizeEstimator;
import com.google.firebase.database.core.utilities.Pair;
import com.google.firebase.database.core.utilities.Utilities;
import com.google.firebase.database.core.view.QuerySpec;
import com.google.firebase.database.logging.LogWrapper;
import com.google.firebase.database.snapshot.ChildKey;
import com.google.firebase.database.snapshot.EmptyNode;
import com.google.firebase.database.snapshot.Node;
import com.google.firebase.database.snapshot.NodeUtilities;
import com.google.firebase.database.util.JsonMapper;
import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import org.apache.http.cookie.ClientCookie;

/* compiled from: com.google.firebase:firebase-database@@17.0.0 */
public class SqlPersistenceStorageEngine implements PersistenceStorageEngine {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    private static final int CHILDREN_NODE_SPLIT_SIZE_THRESHOLD = 16384;
    private static final String CREATE_SERVER_CACHE = "CREATE TABLE serverCache (path TEXT PRIMARY KEY, value BLOB);";
    private static final String CREATE_TRACKED_KEYS = "CREATE TABLE trackedKeys (id INTEGER, key TEXT);";
    private static final String CREATE_TRACKED_QUERIES = "CREATE TABLE trackedQueries (id INTEGER PRIMARY KEY, path TEXT, queryParams TEXT, lastUse INTEGER, complete INTEGER, active INTEGER);";
    private static final String CREATE_WRITES = "CREATE TABLE writes (id INTEGER, path TEXT, type TEXT, part INTEGER, node BLOB, UNIQUE (id, part));";
    private static final String FIRST_PART_KEY = ".part-0000";
    private static final String LOGGER_COMPONENT = "Persistence";
    private static final String PART_KEY_FORMAT = ".part-%04d";
    private static final String PART_KEY_PREFIX = ".part-";
    private static final String PATH_COLUMN_NAME = "path";
    private static final String ROW_ID_COLUMN_NAME = "rowid";
    private static final int ROW_SPLIT_SIZE = 262144;
    private static final String SERVER_CACHE_TABLE = "serverCache";
    private static final String TRACKED_KEYS_ID_COLUMN_NAME = "id";
    private static final String TRACKED_KEYS_KEY_COLUMN_NAME = "key";
    private static final String TRACKED_KEYS_TABLE = "trackedKeys";
    private static final String TRACKED_QUERY_ACTIVE_COLUMN_NAME = "active";
    private static final String TRACKED_QUERY_COMPLETE_COLUMN_NAME = "complete";
    private static final String TRACKED_QUERY_ID_COLUMN_NAME = "id";
    private static final String TRACKED_QUERY_LAST_USE_COLUMN_NAME = "lastUse";
    private static final String TRACKED_QUERY_PARAMS_COLUMN_NAME = "queryParams";
    private static final String TRACKED_QUERY_PATH_COLUMN_NAME = "path";
    private static final String TRACKED_QUERY_TABLE = "trackedQueries";
    private static final Charset UTF8_CHARSET = Charset.forName("UTF-8");
    private static final String VALUE_COLUMN_NAME = "value";
    private static final String WRITES_TABLE = "writes";
    private static final String WRITE_ID_COLUMN_NAME = "id";
    private static final String WRITE_NODE_COLUMN_NAME = "node";
    private static final String WRITE_PART_COLUMN_NAME = "part";
    private static final String WRITE_TYPE_COLUMN_NAME = "type";
    private static final String WRITE_TYPE_MERGE = "m";
    private static final String WRITE_TYPE_OVERWRITE = "o";
    private final SQLiteDatabase database;
    private boolean insideTransaction;
    private final LogWrapper logger;
    private long transactionStart = 0;

    /* compiled from: com.google.firebase:firebase-database@@17.0.0 */
    private static class PersistentCacheOpenHelper extends SQLiteOpenHelper {
        static final /* synthetic */ boolean $assertionsDisabled = false;
        private static final int DATABASE_VERSION = 2;

        static {
            Class<SqlPersistenceStorageEngine> cls = SqlPersistenceStorageEngine.class;
        }

        public PersistentCacheOpenHelper(Context context, String str) {
            super(context, str, null, 2);
        }

        public void onCreate(SQLiteDatabase sQLiteDatabase) {
            sQLiteDatabase.execSQL(SqlPersistenceStorageEngine.CREATE_SERVER_CACHE);
            sQLiteDatabase.execSQL(SqlPersistenceStorageEngine.CREATE_WRITES);
            sQLiteDatabase.execSQL(SqlPersistenceStorageEngine.CREATE_TRACKED_QUERIES);
            sQLiteDatabase.execSQL(SqlPersistenceStorageEngine.CREATE_TRACKED_KEYS);
        }

        public void onUpgrade(SQLiteDatabase sQLiteDatabase, int i, int i2) {
            if (i <= 1) {
                dropTable(sQLiteDatabase, SqlPersistenceStorageEngine.SERVER_CACHE_TABLE);
                sQLiteDatabase.execSQL(SqlPersistenceStorageEngine.CREATE_SERVER_CACHE);
                dropTable(sQLiteDatabase, SqlPersistenceStorageEngine.TRACKED_QUERY_COMPLETE_COLUMN_NAME);
                sQLiteDatabase.execSQL(SqlPersistenceStorageEngine.CREATE_TRACKED_KEYS);
                sQLiteDatabase.execSQL(SqlPersistenceStorageEngine.CREATE_TRACKED_QUERIES);
                return;
            }
            StringBuilder sb = new StringBuilder();
            sb.append("We don't handle upgrading to ");
            sb.append(i2);
            throw new AssertionError(sb.toString());
        }

        private void dropTable(SQLiteDatabase sQLiteDatabase, String str) {
            StringBuilder sb = new StringBuilder();
            sb.append("DROP TABLE IF EXISTS ");
            sb.append(str);
            sQLiteDatabase.execSQL(sb.toString());
        }
    }

    public SqlPersistenceStorageEngine(Context context, com.google.firebase.database.core.Context context2, String str) {
        try {
            String encode = URLEncoder.encode(str, "utf-8");
            this.logger = context2.getLogger(LOGGER_COMPONENT);
            this.database = openDatabase(context, encode);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void saveUserOverwrite(Path path, Node node, long j) {
        verifyInsideTransaction();
        long currentTimeMillis = System.currentTimeMillis();
        Path path2 = path;
        long j2 = j;
        saveWrite(path2, j2, "o", serializeObject(node.getValue(true)));
        long currentTimeMillis2 = System.currentTimeMillis() - currentTimeMillis;
        if (this.logger.logsDebug()) {
            this.logger.debug(String.format("Persisted user overwrite in %dms", new Object[]{Long.valueOf(currentTimeMillis2)}), new Object[0]);
        }
    }

    public void saveUserMerge(Path path, CompoundWrite compoundWrite, long j) {
        verifyInsideTransaction();
        long currentTimeMillis = System.currentTimeMillis();
        Path path2 = path;
        long j2 = j;
        saveWrite(path2, j2, WRITE_TYPE_MERGE, serializeObject(compoundWrite.getValue(true)));
        long currentTimeMillis2 = System.currentTimeMillis() - currentTimeMillis;
        if (this.logger.logsDebug()) {
            this.logger.debug(String.format("Persisted user merge in %dms", new Object[]{Long.valueOf(currentTimeMillis2)}), new Object[0]);
        }
    }

    public void removeUserWrite(long j) {
        verifyInsideTransaction();
        long currentTimeMillis = System.currentTimeMillis();
        int delete = this.database.delete(WRITES_TABLE, "id = ?", new String[]{String.valueOf(j)});
        long currentTimeMillis2 = System.currentTimeMillis() - currentTimeMillis;
        if (this.logger.logsDebug()) {
            this.logger.debug(String.format("Deleted %d write(s) with writeId %d in %dms", new Object[]{Integer.valueOf(delete), Long.valueOf(j), Long.valueOf(currentTimeMillis2)}), new Object[0]);
        }
    }

    public List<UserWriteRecord> loadUserWrites() {
        byte[] bArr;
        UserWriteRecord userWriteRecord;
        int i = 3;
        String[] strArr = {TtmlNode.ATTR_ID, ClientCookie.PATH_ATTR, "type", WRITE_PART_COLUMN_NAME, WRITE_NODE_COLUMN_NAME};
        long currentTimeMillis = System.currentTimeMillis();
        Cursor query = this.database.query(WRITES_TABLE, strArr, null, null, null, null, "id, part");
        ArrayList arrayList = new ArrayList();
        while (query.moveToNext()) {
            try {
                long j = query.getLong(0);
                Path path = new Path(query.getString(1));
                String string = query.getString(2);
                if (query.isNull(i)) {
                    bArr = query.getBlob(4);
                } else {
                    ArrayList arrayList2 = new ArrayList();
                    do {
                        arrayList2.add(query.getBlob(4));
                        if (!query.moveToNext()) {
                            break;
                        }
                    } while (query.getLong(0) == j);
                    query.moveToPrevious();
                    bArr = joinBytes(arrayList2);
                }
                Object parseJsonValue = JsonMapper.parseJsonValue(new String(bArr, UTF8_CHARSET));
                if ("o".equals(string)) {
                    userWriteRecord = new UserWriteRecord(j, path, NodeUtilities.NodeFromJSON(parseJsonValue), true);
                } else if (WRITE_TYPE_MERGE.equals(string)) {
                    userWriteRecord = new UserWriteRecord(j, path, CompoundWrite.fromValue((Map) parseJsonValue));
                } else {
                    StringBuilder sb = new StringBuilder();
                    sb.append("Got invalid write type: ");
                    sb.append(string);
                    throw new IllegalStateException(sb.toString());
                }
                arrayList.add(userWriteRecord);
                i = 3;
            } catch (IOException e) {
                throw new RuntimeException("Failed to load writes", e);
            } catch (Throwable th) {
                query.close();
                throw th;
            }
        }
        long currentTimeMillis2 = System.currentTimeMillis() - currentTimeMillis;
        if (this.logger.logsDebug()) {
            this.logger.debug(String.format("Loaded %d writes in %dms", new Object[]{Integer.valueOf(arrayList.size()), Long.valueOf(currentTimeMillis2)}), new Object[0]);
        }
        query.close();
        return arrayList;
    }

    private void saveWrite(Path path, long j, String str, byte[] bArr) {
        String str2 = str;
        byte[] bArr2 = bArr;
        verifyInsideTransaction();
        SQLiteDatabase sQLiteDatabase = this.database;
        String[] strArr = {String.valueOf(j)};
        String str3 = WRITES_TABLE;
        sQLiteDatabase.delete(str3, "id = ?", strArr);
        int length = bArr2.length;
        String str4 = WRITE_NODE_COLUMN_NAME;
        String str5 = WRITE_PART_COLUMN_NAME;
        String str6 = "type";
        String str7 = ClientCookie.PATH_ATTR;
        String str8 = TtmlNode.ATTR_ID;
        if (length >= 262144) {
            List splitBytes = splitBytes(bArr2, 262144);
            for (int i = 0; i < splitBytes.size(); i++) {
                ContentValues contentValues = new ContentValues();
                contentValues.put(str8, Long.valueOf(j));
                contentValues.put(str7, pathToKey(path));
                contentValues.put(str6, str2);
                contentValues.put(str5, Integer.valueOf(i));
                contentValues.put(str4, (byte[]) splitBytes.get(i));
                this.database.insertWithOnConflict(str3, null, contentValues, 5);
            }
            return;
        }
        ContentValues contentValues2 = new ContentValues();
        contentValues2.put(str8, Long.valueOf(j));
        contentValues2.put(str7, pathToKey(path));
        contentValues2.put(str6, str2);
        contentValues2.put(str5, null);
        contentValues2.put(str4, bArr2);
        this.database.insertWithOnConflict(str3, null, contentValues2, 5);
    }

    public Node serverCache(Path path) {
        return loadNested(path);
    }

    public void overwriteServerCache(Path path, Node node) {
        verifyInsideTransaction();
        updateServerCache(path, node, false);
    }

    public void mergeIntoServerCache(Path path, Node node) {
        verifyInsideTransaction();
        updateServerCache(path, node, true);
    }

    /* JADX WARNING: Incorrect type for immutable var: ssa=com.google.firebase.database.snapshot.Node, code=com.google.firebase.database.snapshot.Node<com.google.firebase.database.snapshot.NamedNode>, for r9v0, types: [com.google.firebase.database.snapshot.Node<com.google.firebase.database.snapshot.NamedNode>, com.google.firebase.database.snapshot.Node] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private void updateServerCache(com.google.firebase.database.core.Path r8, com.google.firebase.database.snapshot.Node<com.google.firebase.database.snapshot.NamedNode> r9, boolean r10) {
        /*
            r7 = this;
            long r0 = java.lang.System.currentTimeMillis()
            java.lang.String r2 = "serverCache"
            r3 = 0
            if (r10 != 0) goto L_0x0012
            int r10 = r7.removeNested(r2, r8)
            int r9 = r7.saveNested(r8, r9)
            goto L_0x0044
        L_0x0012:
            java.util.Iterator r9 = r9.iterator()
            r10 = 0
            r4 = 0
        L_0x0018:
            boolean r5 = r9.hasNext()
            if (r5 == 0) goto L_0x0043
            java.lang.Object r5 = r9.next()
            com.google.firebase.database.snapshot.NamedNode r5 = (com.google.firebase.database.snapshot.NamedNode) r5
            com.google.firebase.database.snapshot.ChildKey r6 = r5.getName()
            com.google.firebase.database.core.Path r6 = r8.child(r6)
            int r6 = r7.removeNested(r2, r6)
            int r10 = r10 + r6
            com.google.firebase.database.snapshot.ChildKey r6 = r5.getName()
            com.google.firebase.database.core.Path r6 = r8.child(r6)
            com.google.firebase.database.snapshot.Node r5 = r5.getNode()
            int r5 = r7.saveNested(r6, r5)
            int r4 = r4 + r5
            goto L_0x0018
        L_0x0043:
            r9 = r4
        L_0x0044:
            long r4 = java.lang.System.currentTimeMillis()
            long r4 = r4 - r0
            com.google.firebase.database.logging.LogWrapper r0 = r7.logger
            boolean r0 = r0.logsDebug()
            if (r0 == 0) goto L_0x007c
            com.google.firebase.database.logging.LogWrapper r0 = r7.logger
            r1 = 4
            java.lang.Object[] r1 = new java.lang.Object[r1]
            java.lang.Integer r9 = java.lang.Integer.valueOf(r9)
            r1[r3] = r9
            r9 = 1
            java.lang.Integer r10 = java.lang.Integer.valueOf(r10)
            r1[r9] = r10
            r9 = 2
            java.lang.String r8 = r8.toString()
            r1[r9] = r8
            r8 = 3
            java.lang.Long r9 = java.lang.Long.valueOf(r4)
            r1[r8] = r9
            java.lang.String r8 = "Persisted a total of %d rows and deleted %d rows for a set at %s in %dms"
            java.lang.String r8 = java.lang.String.format(r8, r1)
            java.lang.Object[] r9 = new java.lang.Object[r3]
            r0.debug(r8, r9)
        L_0x007c:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.firebase.database.android.SqlPersistenceStorageEngine.updateServerCache(com.google.firebase.database.core.Path, com.google.firebase.database.snapshot.Node, boolean):void");
    }

    public void mergeIntoServerCache(Path path, CompoundWrite compoundWrite) {
        verifyInsideTransaction();
        long currentTimeMillis = System.currentTimeMillis();
        Iterator it = compoundWrite.iterator();
        int i = 0;
        int i2 = 0;
        while (it.hasNext()) {
            Entry entry = (Entry) it.next();
            i += removeNested(SERVER_CACHE_TABLE, path.child((Path) entry.getKey()));
            i2 += saveNested(path.child((Path) entry.getKey()), (Node) entry.getValue());
        }
        long currentTimeMillis2 = System.currentTimeMillis() - currentTimeMillis;
        if (this.logger.logsDebug()) {
            this.logger.debug(String.format("Persisted a total of %d rows and deleted %d rows for a merge at %s in %dms", new Object[]{Integer.valueOf(i2), Integer.valueOf(i), path.toString(), Long.valueOf(currentTimeMillis2)}), new Object[0]);
        }
    }

    public long serverCacheEstimatedSizeInBytes() {
        Cursor rawQuery = this.database.rawQuery(String.format("SELECT sum(length(%s) + length(%s)) FROM %s", new Object[]{"value", ClientCookie.PATH_ATTR, SERVER_CACHE_TABLE}), null);
        try {
            if (rawQuery.moveToFirst()) {
                return rawQuery.getLong(0);
            }
            throw new IllegalStateException("Couldn't read database result!");
        } finally {
            rawQuery.close();
        }
    }

    public void saveTrackedQuery(TrackedQuery trackedQuery) {
        verifyInsideTransaction();
        long currentTimeMillis = System.currentTimeMillis();
        ContentValues contentValues = new ContentValues();
        contentValues.put(TtmlNode.ATTR_ID, Long.valueOf(trackedQuery.f2010id));
        contentValues.put(ClientCookie.PATH_ATTR, pathToKey(trackedQuery.querySpec.getPath()));
        contentValues.put(TRACKED_QUERY_PARAMS_COLUMN_NAME, trackedQuery.querySpec.getParams().toJSON());
        contentValues.put(TRACKED_QUERY_LAST_USE_COLUMN_NAME, Long.valueOf(trackedQuery.lastUse));
        contentValues.put(TRACKED_QUERY_COMPLETE_COLUMN_NAME, Boolean.valueOf(trackedQuery.complete));
        contentValues.put(TRACKED_QUERY_ACTIVE_COLUMN_NAME, Boolean.valueOf(trackedQuery.active));
        this.database.insertWithOnConflict(TRACKED_QUERY_TABLE, null, contentValues, 5);
        long currentTimeMillis2 = System.currentTimeMillis() - currentTimeMillis;
        if (this.logger.logsDebug()) {
            this.logger.debug(String.format("Saved new tracked query in %dms", new Object[]{Long.valueOf(currentTimeMillis2)}), new Object[0]);
        }
    }

    public void deleteTrackedQuery(long j) {
        verifyInsideTransaction();
        String valueOf = String.valueOf(j);
        String str = "id = ?";
        this.database.delete(TRACKED_QUERY_TABLE, str, new String[]{valueOf});
        this.database.delete(TRACKED_KEYS_TABLE, str, new String[]{valueOf});
    }

    public List<TrackedQuery> loadTrackedQueries() {
        String[] strArr = {TtmlNode.ATTR_ID, ClientCookie.PATH_ATTR, TRACKED_QUERY_PARAMS_COLUMN_NAME, TRACKED_QUERY_LAST_USE_COLUMN_NAME, TRACKED_QUERY_COMPLETE_COLUMN_NAME, TRACKED_QUERY_ACTIVE_COLUMN_NAME};
        long currentTimeMillis = System.currentTimeMillis();
        Cursor query = this.database.query(TRACKED_QUERY_TABLE, strArr, null, null, null, null, TtmlNode.ATTR_ID);
        ArrayList arrayList = new ArrayList();
        while (query.moveToNext()) {
            try {
                TrackedQuery trackedQuery = new TrackedQuery(query.getLong(0), QuerySpec.fromPathAndQueryObject(new Path(query.getString(1)), JsonMapper.parseJson(query.getString(2))), query.getLong(3), query.getInt(4) != 0, query.getInt(5) != 0);
                arrayList.add(trackedQuery);
            } catch (IOException e) {
                throw new RuntimeException(e);
            } catch (Throwable th) {
                query.close();
                throw th;
            }
        }
        long currentTimeMillis2 = System.currentTimeMillis() - currentTimeMillis;
        if (this.logger.logsDebug()) {
            this.logger.debug(String.format("Loaded %d tracked queries in %dms", new Object[]{Integer.valueOf(arrayList.size()), Long.valueOf(currentTimeMillis2)}), new Object[0]);
        }
        query.close();
        return arrayList;
    }

    public void resetPreviouslyActiveTrackedQueries(long j) {
        verifyInsideTransaction();
        long currentTimeMillis = System.currentTimeMillis();
        ContentValues contentValues = new ContentValues();
        contentValues.put(TRACKED_QUERY_ACTIVE_COLUMN_NAME, Boolean.valueOf(false));
        contentValues.put(TRACKED_QUERY_LAST_USE_COLUMN_NAME, Long.valueOf(j));
        String[] strArr = new String[0];
        this.database.updateWithOnConflict(TRACKED_QUERY_TABLE, contentValues, "active = 1", strArr, 5);
        long currentTimeMillis2 = System.currentTimeMillis() - currentTimeMillis;
        if (this.logger.logsDebug()) {
            this.logger.debug(String.format("Reset active tracked queries in %dms", new Object[]{Long.valueOf(currentTimeMillis2)}), new Object[0]);
        }
    }

    public void saveTrackedQueryKeys(long j, Set<ChildKey> set) {
        verifyInsideTransaction();
        long currentTimeMillis = System.currentTimeMillis();
        String valueOf = String.valueOf(j);
        SQLiteDatabase sQLiteDatabase = this.database;
        String[] strArr = {valueOf};
        String str = TRACKED_KEYS_TABLE;
        sQLiteDatabase.delete(str, "id = ?", strArr);
        for (ChildKey childKey : set) {
            ContentValues contentValues = new ContentValues();
            contentValues.put(TtmlNode.ATTR_ID, Long.valueOf(j));
            contentValues.put(TRACKED_KEYS_KEY_COLUMN_NAME, childKey.asString());
            this.database.insertWithOnConflict(str, null, contentValues, 5);
        }
        long currentTimeMillis2 = System.currentTimeMillis() - currentTimeMillis;
        if (this.logger.logsDebug()) {
            this.logger.debug(String.format("Set %d tracked query keys for tracked query %d in %dms", new Object[]{Integer.valueOf(set.size()), Long.valueOf(j), Long.valueOf(currentTimeMillis2)}), new Object[0]);
        }
    }

    public void updateTrackedQueryKeys(long j, Set<ChildKey> set, Set<ChildKey> set2) {
        String str;
        verifyInsideTransaction();
        long currentTimeMillis = System.currentTimeMillis();
        String valueOf = String.valueOf(j);
        Iterator it = set2.iterator();
        while (true) {
            boolean hasNext = it.hasNext();
            str = TRACKED_KEYS_TABLE;
            if (!hasNext) {
                break;
            }
            ChildKey childKey = (ChildKey) it.next();
            this.database.delete(str, "id = ? AND key = ?", new String[]{valueOf, childKey.asString()});
        }
        for (ChildKey childKey2 : set) {
            ContentValues contentValues = new ContentValues();
            contentValues.put(TtmlNode.ATTR_ID, Long.valueOf(j));
            contentValues.put(TRACKED_KEYS_KEY_COLUMN_NAME, childKey2.asString());
            this.database.insertWithOnConflict(str, null, contentValues, 5);
        }
        long currentTimeMillis2 = System.currentTimeMillis() - currentTimeMillis;
        if (this.logger.logsDebug()) {
            this.logger.debug(String.format("Updated tracked query keys (%d added, %d removed) for tracked query id %d in %dms", new Object[]{Integer.valueOf(set.size()), Integer.valueOf(set2.size()), Long.valueOf(j), Long.valueOf(currentTimeMillis2)}), new Object[0]);
        }
    }

    public Set<ChildKey> loadTrackedQueryKeys(long j) {
        return loadTrackedQueryKeys(Collections.singleton(Long.valueOf(j)));
    }

    public Set<ChildKey> loadTrackedQueryKeys(Set<Long> set) {
        String[] strArr = {TRACKED_KEYS_KEY_COLUMN_NAME};
        long currentTimeMillis = System.currentTimeMillis();
        StringBuilder sb = new StringBuilder();
        sb.append("id IN (");
        sb.append(commaSeparatedList(set));
        sb.append(")");
        Cursor query = this.database.query(true, TRACKED_KEYS_TABLE, strArr, sb.toString(), null, null, null, null, null);
        HashSet hashSet = new HashSet();
        while (query.moveToNext()) {
            try {
                hashSet.add(ChildKey.fromString(query.getString(0)));
            } finally {
                query.close();
            }
        }
        long currentTimeMillis2 = System.currentTimeMillis() - currentTimeMillis;
        if (this.logger.logsDebug()) {
            this.logger.debug(String.format("Loaded %d tracked queries keys for tracked queries %s in %dms", new Object[]{Integer.valueOf(hashSet.size()), set.toString(), Long.valueOf(currentTimeMillis2)}), new Object[0]);
        }
        return hashSet;
    }

    public void pruneCache(Path path, PruneForest pruneForest) {
        int i;
        int i2;
        Path path2 = path;
        PruneForest pruneForest2 = pruneForest;
        if (pruneForest.prunesAnything()) {
            verifyInsideTransaction();
            long currentTimeMillis = System.currentTimeMillis();
            Cursor loadNestedQuery = loadNestedQuery(path2, new String[]{ROW_ID_COLUMN_NAME, ClientCookie.PATH_ATTR});
            ImmutableTree immutableTree = new ImmutableTree(null);
            ImmutableTree immutableTree2 = new ImmutableTree(null);
            while (loadNestedQuery.moveToNext()) {
                long j = loadNestedQuery.getLong(0);
                Path path3 = new Path(loadNestedQuery.getString(1));
                String str = "We are pruning at ";
                if (!path2.contains(path3)) {
                    LogWrapper logWrapper = this.logger;
                    StringBuilder sb = new StringBuilder();
                    sb.append(str);
                    sb.append(path2);
                    sb.append(" but we have data stored higher up at ");
                    sb.append(path3);
                    sb.append(". Ignoring.");
                    logWrapper.warn(sb.toString());
                } else {
                    Path relative = Path.getRelative(path2, path3);
                    if (pruneForest2.shouldPruneUnkeptDescendants(relative)) {
                        immutableTree = immutableTree.set(relative, Long.valueOf(j));
                    } else if (pruneForest2.shouldKeep(relative)) {
                        immutableTree2 = immutableTree2.set(relative, Long.valueOf(j));
                    } else {
                        LogWrapper logWrapper2 = this.logger;
                        StringBuilder sb2 = new StringBuilder();
                        sb2.append(str);
                        sb2.append(path2);
                        sb2.append(" and have data at ");
                        sb2.append(path3);
                        sb2.append(" that isn't marked for pruning or keeping. Ignoring.");
                        logWrapper2.warn(sb2.toString());
                    }
                }
            }
            if (!immutableTree.isEmpty()) {
                ArrayList<Pair> arrayList = new ArrayList<>();
                pruneTreeRecursive(path, Path.getEmptyPath(), immutableTree, immutableTree2, pruneForest, arrayList);
                Collection values = immutableTree.values();
                StringBuilder sb3 = new StringBuilder();
                sb3.append("rowid IN (");
                sb3.append(commaSeparatedList(values));
                sb3.append(")");
                this.database.delete(SERVER_CACHE_TABLE, sb3.toString(), null);
                for (Pair pair : arrayList) {
                    saveNested(path2.child((Path) pair.getFirst()), (Node) pair.getSecond());
                }
                i2 = values.size();
                i = arrayList.size();
            } else {
                i2 = 0;
                i = 0;
            }
            long currentTimeMillis2 = System.currentTimeMillis() - currentTimeMillis;
            if (this.logger.logsDebug()) {
                this.logger.debug(String.format("Pruned %d rows with %d nodes resaved in %dms", new Object[]{Integer.valueOf(i2), Integer.valueOf(i), Long.valueOf(currentTimeMillis2)}), new Object[0]);
            }
        }
    }

    private void pruneTreeRecursive(Path path, Path path2, ImmutableTree<Long> immutableTree, ImmutableTree<Long> immutableTree2, PruneForest pruneForest, List<Pair<Path, Node>> list) {
        final ImmutableTree<Long> immutableTree3 = immutableTree2;
        PruneForest pruneForest2 = pruneForest;
        if (immutableTree.getValue() != null) {
            int intValue = ((Integer) pruneForest2.foldKeptNodes(Integer.valueOf(0), new TreeVisitor<Void, Integer>() {
                public Integer onNodeValue(Path path, Void voidR, Integer num) {
                    return Integer.valueOf(immutableTree3.get(path) == null ? num.intValue() + 1 : num.intValue());
                }
            })).intValue();
            if (intValue > 0) {
                Path child = path.child(path2);
                if (this.logger.logsDebug()) {
                    this.logger.debug(String.format("Need to rewrite %d nodes below path %s", new Object[]{Integer.valueOf(intValue), child}), new Object[0]);
                }
                final Node loadNested = loadNested(child);
                final ImmutableTree<Long> immutableTree4 = immutableTree2;
                final List<Pair<Path, Node>> list2 = list;
                final Path path3 = path2;
                C35202 r0 = new TreeVisitor<Void, Void>() {
                    public Void onNodeValue(Path path, Void voidR, Void voidR2) {
                        if (immutableTree4.get(path) == null) {
                            list2.add(new Pair(path3.child(path), loadNested.getChild(path)));
                        }
                        return null;
                    }
                };
                pruneForest2.foldKeptNodes(null, r0);
                return;
            }
            return;
        }
        Iterator it = immutableTree.getChildren().iterator();
        while (it.hasNext()) {
            Entry entry = (Entry) it.next();
            ChildKey childKey = (ChildKey) entry.getKey();
            PruneForest child2 = pruneForest2.child((ChildKey) entry.getKey());
            Path path4 = path2;
            pruneTreeRecursive(path, path2.child(childKey), (ImmutableTree) entry.getValue(), immutableTree3.getChild(childKey), child2, list);
        }
    }

    public void removeAllUserWrites() {
        verifyInsideTransaction();
        long currentTimeMillis = System.currentTimeMillis();
        int delete = this.database.delete(WRITES_TABLE, null, null);
        long currentTimeMillis2 = System.currentTimeMillis() - currentTimeMillis;
        if (this.logger.logsDebug()) {
            this.logger.debug(String.format("Deleted %d (all) write(s) in %dms", new Object[]{Integer.valueOf(delete), Long.valueOf(currentTimeMillis2)}), new Object[0]);
        }
    }

    public void purgeCache() {
        verifyInsideTransaction();
        this.database.delete(SERVER_CACHE_TABLE, null, null);
        this.database.delete(WRITES_TABLE, null, null);
        this.database.delete(TRACKED_QUERY_TABLE, null, null);
        this.database.delete(TRACKED_KEYS_TABLE, null, null);
    }

    public void beginTransaction() {
        Utilities.hardAssert(!this.insideTransaction, "runInTransaction called when an existing transaction is already in progress.");
        if (this.logger.logsDebug()) {
            this.logger.debug("Starting transaction.", new Object[0]);
        }
        this.database.beginTransaction();
        this.insideTransaction = true;
        this.transactionStart = System.currentTimeMillis();
    }

    public void endTransaction() {
        this.database.endTransaction();
        this.insideTransaction = false;
        long currentTimeMillis = System.currentTimeMillis() - this.transactionStart;
        if (this.logger.logsDebug()) {
            this.logger.debug(String.format("Transaction completed. Elapsed: %dms", new Object[]{Long.valueOf(currentTimeMillis)}), new Object[0]);
        }
    }

    public void setTransactionSuccessful() {
        this.database.setTransactionSuccessful();
    }

    public void close() {
        this.database.close();
    }

    private SQLiteDatabase openDatabase(Context context, String str) {
        try {
            SQLiteDatabase writableDatabase = new PersistentCacheOpenHelper(context, str).getWritableDatabase();
            writableDatabase.rawQuery("PRAGMA locking_mode = EXCLUSIVE", null).close();
            writableDatabase.beginTransaction();
            writableDatabase.endTransaction();
            return writableDatabase;
        } catch (SQLiteException e) {
            if (e instanceof SQLiteDatabaseLockedException) {
                throw new DatabaseException("Failed to gain exclusive lock to Firebase Database's offline persistence. This generally means you are using Firebase Database from multiple processes in your app. Keep in mind that multi-process Android apps execute the code in your Application class in all processes, so you may need to avoid initializing FirebaseDatabase in your Application class. If you are intentionally using Firebase Database from multiple processes, you can only enable offline persistence (i.e. call setPersistenceEnabled(true)) in one of them.", e);
            }
            throw e;
        }
    }

    private void verifyInsideTransaction() {
        Utilities.hardAssert(this.insideTransaction, "Transaction expected to already be in progress.");
    }

    /* JADX WARNING: Incorrect type for immutable var: ssa=com.google.firebase.database.snapshot.Node, code=com.google.firebase.database.snapshot.Node<com.google.firebase.database.snapshot.NamedNode>, for r8v0, types: [com.google.firebase.database.snapshot.Node<com.google.firebase.database.snapshot.NamedNode>, com.google.firebase.database.snapshot.Node] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private int saveNested(com.google.firebase.database.core.Path r7, com.google.firebase.database.snapshot.Node<com.google.firebase.database.snapshot.NamedNode> r8) {
        /*
            r6 = this;
            long r0 = com.google.firebase.database.core.utilities.NodeSizeEstimator.estimateSerializedNodeSize(r8)
            boolean r2 = r8 instanceof com.google.firebase.database.snapshot.ChildrenNode
            r3 = 1
            if (r2 == 0) goto L_0x007f
            r4 = 16384(0x4000, double:8.0948E-320)
            int r2 = (r0 > r4 ? 1 : (r0 == r4 ? 0 : -1))
            if (r2 <= 0) goto L_0x007f
            com.google.firebase.database.logging.LogWrapper r2 = r6.logger
            boolean r2 = r2.logsDebug()
            r4 = 0
            if (r2 == 0) goto L_0x0039
            com.google.firebase.database.logging.LogWrapper r2 = r6.logger
            r5 = 3
            java.lang.Object[] r5 = new java.lang.Object[r5]
            r5[r4] = r7
            java.lang.Long r0 = java.lang.Long.valueOf(r0)
            r5[r3] = r0
            r0 = 2
            r1 = 16384(0x4000, float:2.2959E-41)
            java.lang.Integer r1 = java.lang.Integer.valueOf(r1)
            r5[r0] = r1
            java.lang.String r0 = "Node estimated serialized size at path %s of %d bytes exceeds limit of %d bytes. Splitting up."
            java.lang.String r0 = java.lang.String.format(r0, r5)
            java.lang.Object[] r1 = new java.lang.Object[r4]
            r2.debug(r0, r1)
        L_0x0039:
            java.util.Iterator r0 = r8.iterator()
        L_0x003d:
            boolean r1 = r0.hasNext()
            if (r1 == 0) goto L_0x005b
            java.lang.Object r1 = r0.next()
            com.google.firebase.database.snapshot.NamedNode r1 = (com.google.firebase.database.snapshot.NamedNode) r1
            com.google.firebase.database.snapshot.ChildKey r2 = r1.getName()
            com.google.firebase.database.core.Path r2 = r7.child(r2)
            com.google.firebase.database.snapshot.Node r1 = r1.getNode()
            int r1 = r6.saveNested(r2, r1)
            int r4 = r4 + r1
            goto L_0x003d
        L_0x005b:
            com.google.firebase.database.snapshot.Node r0 = r8.getPriority()
            boolean r0 = r0.isEmpty()
            if (r0 != 0) goto L_0x0076
            com.google.firebase.database.snapshot.ChildKey r0 = com.google.firebase.database.snapshot.ChildKey.getPriorityKey()
            com.google.firebase.database.core.Path r0 = r7.child(r0)
            com.google.firebase.database.snapshot.Node r8 = r8.getPriority()
            r6.saveNode(r0, r8)
            int r4 = r4 + 1
        L_0x0076:
            com.google.firebase.database.snapshot.EmptyNode r8 = com.google.firebase.database.snapshot.EmptyNode.Empty()
            r6.saveNode(r7, r8)
            int r4 = r4 + r3
            return r4
        L_0x007f:
            r6.saveNode(r7, r8)
            return r3
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.firebase.database.android.SqlPersistenceStorageEngine.saveNested(com.google.firebase.database.core.Path, com.google.firebase.database.snapshot.Node):int");
    }

    private String partKey(Path path, int i) {
        StringBuilder sb = new StringBuilder();
        sb.append(pathToKey(path));
        sb.append(String.format(PART_KEY_FORMAT, new Object[]{Integer.valueOf(i)}));
        return sb.toString();
    }

    private void saveNode(Path path, Node node) {
        byte[] serializeObject = serializeObject(node.getValue(true));
        int length = serializeObject.length;
        String str = SERVER_CACHE_TABLE;
        String str2 = "value";
        String str3 = ClientCookie.PATH_ATTR;
        if (length >= 262144) {
            List splitBytes = splitBytes(serializeObject, 262144);
            if (this.logger.logsDebug()) {
                LogWrapper logWrapper = this.logger;
                StringBuilder sb = new StringBuilder();
                sb.append("Saving huge leaf node with ");
                sb.append(splitBytes.size());
                sb.append(" parts.");
                logWrapper.debug(sb.toString(), new Object[0]);
            }
            for (int i = 0; i < splitBytes.size(); i++) {
                ContentValues contentValues = new ContentValues();
                contentValues.put(str3, partKey(path, i));
                contentValues.put(str2, (byte[]) splitBytes.get(i));
                this.database.insertWithOnConflict(str, null, contentValues, 5);
            }
            return;
        }
        ContentValues contentValues2 = new ContentValues();
        contentValues2.put(str3, pathToKey(path));
        contentValues2.put(str2, serializeObject);
        this.database.insertWithOnConflict(str, null, contentValues2, 5);
    }

    /* JADX INFO: finally extract failed */
    private Node loadNested(Path path) {
        long j;
        long j2;
        Path path2;
        int i;
        Node node;
        int i2;
        Path path3;
        Path path4 = path;
        ArrayList arrayList = new ArrayList();
        ArrayList arrayList2 = new ArrayList();
        long currentTimeMillis = System.currentTimeMillis();
        Cursor loadNestedQuery = loadNestedQuery(path4, new String[]{ClientCookie.PATH_ATTR, "value"});
        long currentTimeMillis2 = System.currentTimeMillis() - currentTimeMillis;
        long currentTimeMillis3 = System.currentTimeMillis();
        while (loadNestedQuery.moveToNext()) {
            try {
                arrayList.add(loadNestedQuery.getString(0));
                arrayList2.add(loadNestedQuery.getBlob(1));
            } catch (Throwable th) {
                loadNestedQuery.close();
                throw th;
            }
        }
        loadNestedQuery.close();
        long currentTimeMillis4 = System.currentTimeMillis() - currentTimeMillis3;
        long currentTimeMillis5 = System.currentTimeMillis();
        Node Empty = EmptyNode.Empty();
        HashMap hashMap = new HashMap();
        Node node2 = Empty;
        int i3 = 0;
        boolean z = false;
        while (i3 < arrayList2.size()) {
            if (((String) arrayList.get(i3)).endsWith(FIRST_PART_KEY)) {
                String str = (String) arrayList.get(i3);
                j2 = currentTimeMillis4;
                Path path5 = new Path(str.substring(0, str.length() - 10));
                int splitNodeRunLength = splitNodeRunLength(path5, arrayList, i3);
                if (this.logger.logsDebug()) {
                    LogWrapper logWrapper = this.logger;
                    StringBuilder sb = new StringBuilder();
                    path3 = path5;
                    sb.append("Loading split node with ");
                    sb.append(splitNodeRunLength);
                    sb.append(" parts.");
                    j = currentTimeMillis2;
                    logWrapper.debug(sb.toString(), new Object[0]);
                } else {
                    path3 = path5;
                    j = currentTimeMillis2;
                }
                int i4 = splitNodeRunLength + i3;
                node = deserializeNode(joinBytes(arrayList2.subList(i3, i4)));
                i = i4 - 1;
                path2 = path3;
            } else {
                j = currentTimeMillis2;
                j2 = currentTimeMillis4;
                Node deserializeNode = deserializeNode((byte[]) arrayList2.get(i3));
                path2 = new Path((String) arrayList.get(i3));
                Node node3 = deserializeNode;
                i = i3;
                node = node3;
            }
            if (path2.getBack() != null && path2.getBack().isPriorityChildName()) {
                hashMap.put(path2, node);
            } else if (path2.contains(path4)) {
                Utilities.hardAssert(!z, "Descendants of path must come after ancestors.");
                node2 = node.getChild(Path.getRelative(path2, path4));
            } else if (path4.contains(path2)) {
                node2 = node2.updateChild(Path.getRelative(path4, path2), node);
                i2 = 1;
                z = true;
                i3 = i + i2;
                currentTimeMillis4 = j2;
                currentTimeMillis2 = j;
            } else {
                throw new IllegalStateException(String.format("Loading an unrelated row with path %s for %s", new Object[]{path2, path4}));
            }
            i2 = 1;
            i3 = i + i2;
            currentTimeMillis4 = j2;
            currentTimeMillis2 = j;
        }
        long j3 = currentTimeMillis2;
        long j4 = currentTimeMillis4;
        Node node4 = node2;
        for (Entry entry : hashMap.entrySet()) {
            node4 = node4.updateChild(Path.getRelative(path4, (Path) entry.getKey()), (Node) entry.getValue());
        }
        long currentTimeMillis6 = System.currentTimeMillis() - currentTimeMillis5;
        long currentTimeMillis7 = System.currentTimeMillis() - currentTimeMillis;
        if (this.logger.logsDebug()) {
            this.logger.debug(String.format("Loaded a total of %d rows for a total of %d nodes at %s in %dms (Query: %dms, Loading: %dms, Serializing: %dms)", new Object[]{Integer.valueOf(arrayList2.size()), Integer.valueOf(NodeSizeEstimator.nodeCount(node4)), path4, Long.valueOf(currentTimeMillis7), Long.valueOf(j3), Long.valueOf(j4), Long.valueOf(currentTimeMillis6)}), new Object[0]);
        }
        return node4;
    }

    private int splitNodeRunLength(Path path, List<String> list, int i) {
        int i2 = i + 1;
        String pathToKey = pathToKey(path);
        if (((String) list.get(i)).startsWith(pathToKey)) {
            while (i2 < list.size() && ((String) list.get(i2)).equals(partKey(path, i2 - i))) {
                i2++;
            }
            if (i2 < list.size()) {
                String str = (String) list.get(i2);
                StringBuilder sb = new StringBuilder();
                sb.append(pathToKey);
                sb.append(PART_KEY_PREFIX);
                if (str.startsWith(sb.toString())) {
                    throw new IllegalStateException("Run did not finish with all parts");
                }
            }
            return i2 - i;
        }
        throw new IllegalStateException("Extracting split nodes needs to start with path prefix");
    }

    private Cursor loadNestedQuery(Path path, String[] strArr) {
        String pathToKey = pathToKey(path);
        String pathPrefixStartToPrefixEnd = pathPrefixStartToPrefixEnd(pathToKey);
        String[] strArr2 = new String[(path.size() + 3)];
        String buildAncestorWhereClause = buildAncestorWhereClause(path, strArr2);
        StringBuilder sb = new StringBuilder();
        sb.append(buildAncestorWhereClause);
        sb.append(" OR (path > ? AND path < ?)");
        String sb2 = sb.toString();
        strArr2[path.size() + 1] = pathToKey;
        strArr2[path.size() + 2] = pathPrefixStartToPrefixEnd;
        return this.database.query(SERVER_CACHE_TABLE, strArr, sb2, strArr2, null, null, ClientCookie.PATH_ATTR);
    }

    private static String pathToKey(Path path) {
        String str = "/";
        if (path.isEmpty()) {
            return str;
        }
        StringBuilder sb = new StringBuilder();
        sb.append(path.toString());
        sb.append(str);
        return sb.toString();
    }

    private static String pathPrefixStartToPrefixEnd(String str) {
        StringBuilder sb = new StringBuilder();
        sb.append(str.substring(0, str.length() - 1));
        sb.append('0');
        return sb.toString();
    }

    private static String buildAncestorWhereClause(Path path, String[] strArr) {
        int i = 0;
        StringBuilder sb = new StringBuilder("(");
        while (true) {
            boolean isEmpty = path.isEmpty();
            String str = ClientCookie.PATH_ATTR;
            if (!isEmpty) {
                sb.append(str);
                sb.append(" = ? OR ");
                strArr[i] = pathToKey(path);
                path = path.getParent();
                i++;
            } else {
                sb.append(str);
                sb.append(" = ?)");
                strArr[i] = pathToKey(Path.getEmptyPath());
                return sb.toString();
            }
        }
    }

    private int removeNested(String str, Path path) {
        String pathToKey = pathToKey(path);
        String pathPrefixStartToPrefixEnd = pathPrefixStartToPrefixEnd(pathToKey);
        return this.database.delete(str, "path >= ? AND path < ?", new String[]{pathToKey, pathPrefixStartToPrefixEnd});
    }

    private static List<byte[]> splitBytes(byte[] bArr, int i) {
        int length = ((bArr.length - 1) / i) + 1;
        ArrayList arrayList = new ArrayList(length);
        for (int i2 = 0; i2 < length; i2++) {
            int i3 = i2 * i;
            int min = Math.min(i, bArr.length - i3);
            byte[] bArr2 = new byte[min];
            System.arraycopy(bArr, i3, bArr2, 0, min);
            arrayList.add(bArr2);
        }
        return arrayList;
    }

    private byte[] joinBytes(List<byte[]> list) {
        int i = 0;
        for (byte[] length : list) {
            i += length.length;
        }
        byte[] bArr = new byte[i];
        int i2 = 0;
        for (byte[] bArr2 : list) {
            System.arraycopy(bArr2, 0, bArr, i2, bArr2.length);
            i2 += bArr2.length;
        }
        return bArr;
    }

    private byte[] serializeObject(Object obj) {
        try {
            return JsonMapper.serializeJsonValue(obj).getBytes(UTF8_CHARSET);
        } catch (IOException e) {
            throw new RuntimeException("Could not serialize leaf node", e);
        }
    }

    private Node deserializeNode(byte[] bArr) {
        try {
            return NodeUtilities.NodeFromJSON(JsonMapper.parseJsonValue(new String(bArr, UTF8_CHARSET)));
        } catch (IOException e) {
            String str = new String(bArr, UTF8_CHARSET);
            StringBuilder sb = new StringBuilder();
            sb.append("Could not deserialize node: ");
            sb.append(str);
            throw new RuntimeException(sb.toString(), e);
        }
    }

    private String commaSeparatedList(Collection<Long> collection) {
        StringBuilder sb = new StringBuilder();
        boolean z = true;
        for (Long longValue : collection) {
            long longValue2 = longValue.longValue();
            if (!z) {
                sb.append(",");
            }
            z = false;
            sb.append(longValue2);
        }
        return sb.toString();
    }
}
