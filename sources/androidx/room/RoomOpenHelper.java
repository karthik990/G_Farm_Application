package androidx.room;

import android.database.Cursor;
import androidx.sqlite.p014db.SimpleSQLiteQuery;
import androidx.sqlite.p014db.SupportSQLiteDatabase;
import androidx.sqlite.p014db.SupportSQLiteOpenHelper.Callback;
import androidx.sqlite.p014db.SupportSQLiteQuery;

public class RoomOpenHelper extends Callback {
    private DatabaseConfiguration mConfiguration;
    private final Delegate mDelegate;
    private final String mIdentityHash;
    private final String mLegacyHash;

    public static abstract class Delegate {
        public final int version;

        /* access modifiers changed from: protected */
        public abstract void createAllTables(SupportSQLiteDatabase supportSQLiteDatabase);

        /* access modifiers changed from: protected */
        public abstract void dropAllTables(SupportSQLiteDatabase supportSQLiteDatabase);

        /* access modifiers changed from: protected */
        public abstract void onCreate(SupportSQLiteDatabase supportSQLiteDatabase);

        /* access modifiers changed from: protected */
        public abstract void onOpen(SupportSQLiteDatabase supportSQLiteDatabase);

        /* access modifiers changed from: protected */
        public void onPostMigrate(SupportSQLiteDatabase supportSQLiteDatabase) {
        }

        /* access modifiers changed from: protected */
        public void onPreMigrate(SupportSQLiteDatabase supportSQLiteDatabase) {
        }

        /* access modifiers changed from: protected */
        public abstract void validateMigration(SupportSQLiteDatabase supportSQLiteDatabase);

        public Delegate(int i) {
            this.version = i;
        }
    }

    public RoomOpenHelper(DatabaseConfiguration databaseConfiguration, Delegate delegate, String str, String str2) {
        super(delegate.version);
        this.mConfiguration = databaseConfiguration;
        this.mDelegate = delegate;
        this.mIdentityHash = str;
        this.mLegacyHash = str2;
    }

    public RoomOpenHelper(DatabaseConfiguration databaseConfiguration, Delegate delegate, String str) {
        this(databaseConfiguration, delegate, "", str);
    }

    public void onConfigure(SupportSQLiteDatabase supportSQLiteDatabase) {
        super.onConfigure(supportSQLiteDatabase);
    }

    public void onCreate(SupportSQLiteDatabase supportSQLiteDatabase) {
        updateIdentity(supportSQLiteDatabase);
        this.mDelegate.createAllTables(supportSQLiteDatabase);
        this.mDelegate.onCreate(supportSQLiteDatabase);
    }

    /* JADX WARNING: Removed duplicated region for block: B:11:0x0037  */
    /* JADX WARNING: Removed duplicated region for block: B:19:? A[ORIG_RETURN, RETURN, SYNTHETIC] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void onUpgrade(androidx.sqlite.p014db.SupportSQLiteDatabase r3, int r4, int r5) {
        /*
            r2 = this;
            androidx.room.DatabaseConfiguration r0 = r2.mConfiguration
            if (r0 == 0) goto L_0x0034
            androidx.room.RoomDatabase$MigrationContainer r0 = r0.migrationContainer
            java.util.List r0 = r0.findMigrationPath(r4, r5)
            if (r0 == 0) goto L_0x0034
            androidx.room.RoomOpenHelper$Delegate r1 = r2.mDelegate
            r1.onPreMigrate(r3)
            java.util.Iterator r0 = r0.iterator()
        L_0x0015:
            boolean r1 = r0.hasNext()
            if (r1 == 0) goto L_0x0025
            java.lang.Object r1 = r0.next()
            androidx.room.migration.Migration r1 = (androidx.room.migration.Migration) r1
            r1.migrate(r3)
            goto L_0x0015
        L_0x0025:
            androidx.room.RoomOpenHelper$Delegate r0 = r2.mDelegate
            r0.validateMigration(r3)
            androidx.room.RoomOpenHelper$Delegate r0 = r2.mDelegate
            r0.onPostMigrate(r3)
            r2.updateIdentity(r3)
            r0 = 1
            goto L_0x0035
        L_0x0034:
            r0 = 0
        L_0x0035:
            if (r0 != 0) goto L_0x0070
            androidx.room.DatabaseConfiguration r0 = r2.mConfiguration
            if (r0 == 0) goto L_0x004c
            boolean r0 = r0.isMigrationRequired(r4, r5)
            if (r0 != 0) goto L_0x004c
            androidx.room.RoomOpenHelper$Delegate r4 = r2.mDelegate
            r4.dropAllTables(r3)
            androidx.room.RoomOpenHelper$Delegate r4 = r2.mDelegate
            r4.createAllTables(r3)
            goto L_0x0070
        L_0x004c:
            java.lang.IllegalStateException r3 = new java.lang.IllegalStateException
            java.lang.StringBuilder r0 = new java.lang.StringBuilder
            r0.<init>()
            java.lang.String r1 = "A migration from "
            r0.append(r1)
            r0.append(r4)
            java.lang.String r4 = " to "
            r0.append(r4)
            r0.append(r5)
            java.lang.String r4 = " was required but not found. Please provide the necessary Migration path via RoomDatabase.Builder.addMigration(Migration ...) or allow for destructive migrations via one of the RoomDatabase.Builder.fallbackToDestructiveMigration* methods."
            r0.append(r4)
            java.lang.String r4 = r0.toString()
            r3.<init>(r4)
            throw r3
        L_0x0070:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.room.RoomOpenHelper.onUpgrade(androidx.sqlite.db.SupportSQLiteDatabase, int, int):void");
    }

    public void onDowngrade(SupportSQLiteDatabase supportSQLiteDatabase, int i, int i2) {
        onUpgrade(supportSQLiteDatabase, i, i2);
    }

    public void onOpen(SupportSQLiteDatabase supportSQLiteDatabase) {
        super.onOpen(supportSQLiteDatabase);
        checkIdentity(supportSQLiteDatabase);
        this.mDelegate.onOpen(supportSQLiteDatabase);
        this.mConfiguration = null;
    }

    private void checkIdentity(SupportSQLiteDatabase supportSQLiteDatabase) {
        Object obj = null;
        if (hasRoomMasterTable(supportSQLiteDatabase)) {
            Cursor query = supportSQLiteDatabase.query((SupportSQLiteQuery) new SimpleSQLiteQuery(RoomMasterTable.READ_QUERY));
            try {
                if (query.moveToFirst()) {
                    obj = query.getString(0);
                }
            } finally {
                query.close();
            }
        }
        if (!this.mIdentityHash.equals(obj) && !this.mLegacyHash.equals(obj)) {
            throw new IllegalStateException("Room cannot verify the data integrity. Looks like you've changed schema but forgot to update the version number. You can simply fix this by increasing the version number.");
        }
    }

    private void updateIdentity(SupportSQLiteDatabase supportSQLiteDatabase) {
        createMasterTableIfNotExists(supportSQLiteDatabase);
        supportSQLiteDatabase.execSQL(RoomMasterTable.createInsertQuery(this.mIdentityHash));
    }

    private void createMasterTableIfNotExists(SupportSQLiteDatabase supportSQLiteDatabase) {
        supportSQLiteDatabase.execSQL(RoomMasterTable.CREATE_QUERY);
    }

    private static boolean hasRoomMasterTable(SupportSQLiteDatabase supportSQLiteDatabase) {
        Cursor query = supportSQLiteDatabase.query("SELECT 1 FROM sqlite_master WHERE type = 'table' AND name='room_master_table'");
        try {
            boolean z = false;
            if (query.moveToFirst() && query.getInt(0) != 0) {
                z = true;
            }
            return z;
        } finally {
            query.close();
        }
    }
}
