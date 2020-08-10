package androidx.work.impl;

import android.os.Build.VERSION;
import androidx.room.DatabaseConfiguration;
import androidx.room.InvalidationTracker;
import androidx.room.RoomDatabase.Callback;
import androidx.room.RoomMasterTable;
import androidx.room.RoomOpenHelper;
import androidx.room.RoomOpenHelper.Delegate;
import androidx.room.util.DBUtil;
import androidx.room.util.TableInfo;
import androidx.room.util.TableInfo.Column;
import androidx.room.util.TableInfo.ForeignKey;
import androidx.room.util.TableInfo.Index;
import androidx.sqlite.p014db.SupportSQLiteDatabase;
import androidx.sqlite.p014db.SupportSQLiteOpenHelper;
import androidx.sqlite.p014db.SupportSQLiteOpenHelper.Configuration;
import androidx.work.impl.model.DependencyDao;
import androidx.work.impl.model.DependencyDao_Impl;
import androidx.work.impl.model.SystemIdInfoDao;
import androidx.work.impl.model.SystemIdInfoDao_Impl;
import androidx.work.impl.model.WorkNameDao;
import androidx.work.impl.model.WorkNameDao_Impl;
import androidx.work.impl.model.WorkSpecDao;
import androidx.work.impl.model.WorkSpecDao_Impl;
import androidx.work.impl.model.WorkTagDao;
import androidx.work.impl.model.WorkTagDao_Impl;
import com.braintreepayments.api.internal.GraphQLConstants.Keys;
import com.braintreepayments.api.models.PostalAddressParser;
import com.startapp.android.publish.common.model.AdPreferences;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

public final class WorkDatabase_Impl extends WorkDatabase {
    private volatile DependencyDao _dependencyDao;
    private volatile SystemIdInfoDao _systemIdInfoDao;
    private volatile WorkNameDao _workNameDao;
    private volatile WorkSpecDao _workSpecDao;
    private volatile WorkTagDao _workTagDao;

    /* access modifiers changed from: protected */
    public SupportSQLiteOpenHelper createOpenHelper(DatabaseConfiguration databaseConfiguration) {
        return databaseConfiguration.sqliteOpenHelperFactory.create(Configuration.builder(databaseConfiguration.context).name(databaseConfiguration.name).callback(new RoomOpenHelper(databaseConfiguration, new Delegate(6) {
            public void onPostMigrate(SupportSQLiteDatabase supportSQLiteDatabase) {
            }

            public void createAllTables(SupportSQLiteDatabase supportSQLiteDatabase) {
                supportSQLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS `Dependency` (`work_spec_id` TEXT NOT NULL, `prerequisite_id` TEXT NOT NULL, PRIMARY KEY(`work_spec_id`, `prerequisite_id`), FOREIGN KEY(`work_spec_id`) REFERENCES `WorkSpec`(`id`) ON UPDATE CASCADE ON DELETE CASCADE , FOREIGN KEY(`prerequisite_id`) REFERENCES `WorkSpec`(`id`) ON UPDATE CASCADE ON DELETE CASCADE )");
                supportSQLiteDatabase.execSQL("CREATE  INDEX `index_Dependency_work_spec_id` ON `Dependency` (`work_spec_id`)");
                supportSQLiteDatabase.execSQL("CREATE  INDEX `index_Dependency_prerequisite_id` ON `Dependency` (`prerequisite_id`)");
                supportSQLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS `WorkSpec` (`id` TEXT NOT NULL, `state` INTEGER NOT NULL, `worker_class_name` TEXT NOT NULL, `input_merger_class_name` TEXT, `input` BLOB NOT NULL, `output` BLOB NOT NULL, `initial_delay` INTEGER NOT NULL, `interval_duration` INTEGER NOT NULL, `flex_duration` INTEGER NOT NULL, `run_attempt_count` INTEGER NOT NULL, `backoff_policy` INTEGER NOT NULL, `backoff_delay_duration` INTEGER NOT NULL, `period_start_time` INTEGER NOT NULL, `minimum_retention_duration` INTEGER NOT NULL, `schedule_requested_at` INTEGER NOT NULL, `required_network_type` INTEGER, `requires_charging` INTEGER NOT NULL, `requires_device_idle` INTEGER NOT NULL, `requires_battery_not_low` INTEGER NOT NULL, `requires_storage_not_low` INTEGER NOT NULL, `trigger_content_update_delay` INTEGER NOT NULL, `trigger_max_content_delay` INTEGER NOT NULL, `content_uri_triggers` BLOB, PRIMARY KEY(`id`))");
                supportSQLiteDatabase.execSQL("CREATE  INDEX `index_WorkSpec_schedule_requested_at` ON `WorkSpec` (`schedule_requested_at`)");
                supportSQLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS `WorkTag` (`tag` TEXT NOT NULL, `work_spec_id` TEXT NOT NULL, PRIMARY KEY(`tag`, `work_spec_id`), FOREIGN KEY(`work_spec_id`) REFERENCES `WorkSpec`(`id`) ON UPDATE CASCADE ON DELETE CASCADE )");
                supportSQLiteDatabase.execSQL("CREATE  INDEX `index_WorkTag_work_spec_id` ON `WorkTag` (`work_spec_id`)");
                supportSQLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS `SystemIdInfo` (`work_spec_id` TEXT NOT NULL, `system_id` INTEGER NOT NULL, PRIMARY KEY(`work_spec_id`), FOREIGN KEY(`work_spec_id`) REFERENCES `WorkSpec`(`id`) ON UPDATE CASCADE ON DELETE CASCADE )");
                supportSQLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS `WorkName` (`name` TEXT NOT NULL, `work_spec_id` TEXT NOT NULL, PRIMARY KEY(`name`, `work_spec_id`), FOREIGN KEY(`work_spec_id`) REFERENCES `WorkSpec`(`id`) ON UPDATE CASCADE ON DELETE CASCADE )");
                supportSQLiteDatabase.execSQL("CREATE  INDEX `index_WorkName_work_spec_id` ON `WorkName` (`work_spec_id`)");
                supportSQLiteDatabase.execSQL(RoomMasterTable.CREATE_QUERY);
                supportSQLiteDatabase.execSQL("INSERT OR REPLACE INTO room_master_table (id,identity_hash) VALUES(42, 'c84d23ade98552f1cec71088c1f0794c')");
            }

            public void dropAllTables(SupportSQLiteDatabase supportSQLiteDatabase) {
                supportSQLiteDatabase.execSQL("DROP TABLE IF EXISTS `Dependency`");
                supportSQLiteDatabase.execSQL("DROP TABLE IF EXISTS `WorkSpec`");
                supportSQLiteDatabase.execSQL("DROP TABLE IF EXISTS `WorkTag`");
                supportSQLiteDatabase.execSQL("DROP TABLE IF EXISTS `SystemIdInfo`");
                supportSQLiteDatabase.execSQL("DROP TABLE IF EXISTS `WorkName`");
            }

            /* access modifiers changed from: protected */
            public void onCreate(SupportSQLiteDatabase supportSQLiteDatabase) {
                if (WorkDatabase_Impl.this.mCallbacks != null) {
                    int size = WorkDatabase_Impl.this.mCallbacks.size();
                    for (int i = 0; i < size; i++) {
                        ((Callback) WorkDatabase_Impl.this.mCallbacks.get(i)).onCreate(supportSQLiteDatabase);
                    }
                }
            }

            public void onOpen(SupportSQLiteDatabase supportSQLiteDatabase) {
                WorkDatabase_Impl.this.mDatabase = supportSQLiteDatabase;
                supportSQLiteDatabase.execSQL("PRAGMA foreign_keys = ON");
                WorkDatabase_Impl.this.internalInitInvalidationTracker(supportSQLiteDatabase);
                if (WorkDatabase_Impl.this.mCallbacks != null) {
                    int size = WorkDatabase_Impl.this.mCallbacks.size();
                    for (int i = 0; i < size; i++) {
                        ((Callback) WorkDatabase_Impl.this.mCallbacks.get(i)).onOpen(supportSQLiteDatabase);
                    }
                }
            }

            public void onPreMigrate(SupportSQLiteDatabase supportSQLiteDatabase) {
                DBUtil.dropFtsSyncTriggers(supportSQLiteDatabase);
            }

            /* access modifiers changed from: protected */
            public void validateMigration(SupportSQLiteDatabase supportSQLiteDatabase) {
                SupportSQLiteDatabase supportSQLiteDatabase2 = supportSQLiteDatabase;
                HashMap hashMap = new HashMap(2);
                String str = AdPreferences.TYPE_TEXT;
                String str2 = "work_spec_id";
                hashMap.put(str2, new Column(str2, str, true, 1));
                String str3 = "prerequisite_id";
                hashMap.put(str3, new Column(str3, str, true, 2));
                HashSet hashSet = new HashSet(2);
                List asList = Arrays.asList(new String[]{str2});
                String str4 = TtmlNode.ATTR_ID;
                String str5 = str4;
                ForeignKey foreignKey = new ForeignKey("WorkSpec", "CASCADE", "CASCADE", asList, Arrays.asList(new String[]{str4}));
                hashSet.add(foreignKey);
                String str6 = "WorkSpec";
                String str7 = "CASCADE";
                String str8 = "CASCADE";
                ForeignKey foreignKey2 = new ForeignKey(str6, str7, str8, Arrays.asList(new String[]{str3}), Arrays.asList(new String[]{str5}));
                hashSet.add(foreignKey2);
                HashSet hashSet2 = new HashSet(2);
                String str9 = "index_Dependency_work_spec_id";
                hashSet2.add(new Index(str9, false, Arrays.asList(new String[]{str2})));
                hashSet2.add(new Index("index_Dependency_prerequisite_id", false, Arrays.asList(new String[]{str3})));
                String str10 = "Dependency";
                TableInfo tableInfo = new TableInfo(str10, hashMap, hashSet, hashSet2);
                TableInfo read = TableInfo.read(supportSQLiteDatabase2, str10);
                String str11 = "\n Found:\n";
                if (tableInfo.equals(read)) {
                    HashMap hashMap2 = new HashMap(23);
                    hashMap2.put(str5, new Column(str5, str, true, 1));
                    String str12 = PostalAddressParser.REGION_KEY;
                    String str13 = "INTEGER";
                    hashMap2.put(str12, new Column(str12, str13, true, 0));
                    String str14 = "worker_class_name";
                    hashMap2.put(str14, new Column(str14, str, true, 0));
                    String str15 = "input_merger_class_name";
                    hashMap2.put(str15, new Column(str15, str, false, 0));
                    String str16 = "BLOB";
                    hashMap2.put(Keys.INPUT, new Column(Keys.INPUT, str16, true, 0));
                    hashMap2.put("output", new Column("output", str16, true, 0));
                    hashMap2.put("initial_delay", new Column("initial_delay", str13, true, 0));
                    hashMap2.put("interval_duration", new Column("interval_duration", str13, true, 0));
                    hashMap2.put("flex_duration", new Column("flex_duration", str13, true, 0));
                    hashMap2.put("run_attempt_count", new Column("run_attempt_count", str13, true, 0));
                    hashMap2.put("backoff_policy", new Column("backoff_policy", str13, true, 0));
                    hashMap2.put("backoff_delay_duration", new Column("backoff_delay_duration", str13, true, 0));
                    hashMap2.put("period_start_time", new Column("period_start_time", str13, true, 0));
                    hashMap2.put("minimum_retention_duration", new Column("minimum_retention_duration", str13, true, 0));
                    String str17 = "schedule_requested_at";
                    hashMap2.put(str17, new Column(str17, str13, true, 0));
                    hashMap2.put("required_network_type", new Column("required_network_type", str13, false, 0));
                    hashMap2.put("requires_charging", new Column("requires_charging", str13, true, 0));
                    hashMap2.put("requires_device_idle", new Column("requires_device_idle", str13, true, 0));
                    hashMap2.put("requires_battery_not_low", new Column("requires_battery_not_low", str13, true, 0));
                    hashMap2.put("requires_storage_not_low", new Column("requires_storage_not_low", str13, true, 0));
                    hashMap2.put("trigger_content_update_delay", new Column("trigger_content_update_delay", str13, true, 0));
                    hashMap2.put("trigger_max_content_delay", new Column("trigger_max_content_delay", str13, true, 0));
                    hashMap2.put("content_uri_triggers", new Column("content_uri_triggers", str16, false, 0));
                    HashSet hashSet3 = new HashSet(0);
                    HashSet hashSet4 = new HashSet(1);
                    hashSet4.add(new Index("index_WorkSpec_schedule_requested_at", false, Arrays.asList(new String[]{str17})));
                    TableInfo tableInfo2 = new TableInfo("WorkSpec", hashMap2, hashSet3, hashSet4);
                    TableInfo read2 = TableInfo.read(supportSQLiteDatabase2, "WorkSpec");
                    if (tableInfo2.equals(read2)) {
                        HashMap hashMap3 = new HashMap(2);
                        hashMap3.put("tag", new Column("tag", str, true, 1));
                        hashMap3.put(str2, new Column(str2, str, true, 2));
                        HashSet hashSet5 = new HashSet(1);
                        String str18 = "WorkSpec";
                        String str19 = "CASCADE";
                        String str20 = "CASCADE";
                        ForeignKey foreignKey3 = new ForeignKey(str18, str19, str20, Arrays.asList(new String[]{str2}), Arrays.asList(new String[]{str5}));
                        hashSet5.add(foreignKey3);
                        HashSet hashSet6 = new HashSet(1);
                        String str21 = "index_WorkTag_work_spec_id";
                        hashSet6.add(new Index(str21, false, Arrays.asList(new String[]{str2})));
                        TableInfo tableInfo3 = new TableInfo("WorkTag", hashMap3, hashSet5, hashSet6);
                        TableInfo read3 = TableInfo.read(supportSQLiteDatabase2, "WorkTag");
                        if (tableInfo3.equals(read3)) {
                            HashMap hashMap4 = new HashMap(2);
                            hashMap4.put(str2, new Column(str2, str, true, 1));
                            hashMap4.put("system_id", new Column("system_id", str13, true, 0));
                            HashSet hashSet7 = new HashSet(1);
                            String str22 = "WorkSpec";
                            String str23 = "CASCADE";
                            String str24 = "CASCADE";
                            ForeignKey foreignKey4 = new ForeignKey(str22, str23, str24, Arrays.asList(new String[]{str2}), Arrays.asList(new String[]{str5}));
                            hashSet7.add(foreignKey4);
                            TableInfo tableInfo4 = new TableInfo("SystemIdInfo", hashMap4, hashSet7, new HashSet(0));
                            TableInfo read4 = TableInfo.read(supportSQLiteDatabase2, "SystemIdInfo");
                            if (tableInfo4.equals(read4)) {
                                HashMap hashMap5 = new HashMap(2);
                                hashMap5.put(PostalAddressParser.USER_ADDRESS_NAME_KEY, new Column(PostalAddressParser.USER_ADDRESS_NAME_KEY, str, true, 1));
                                hashMap5.put(str2, new Column(str2, str, true, 2));
                                HashSet hashSet8 = new HashSet(1);
                                String str25 = "WorkSpec";
                                String str26 = "CASCADE";
                                String str27 = "CASCADE";
                                ForeignKey foreignKey5 = new ForeignKey(str25, str26, str27, Arrays.asList(new String[]{str2}), Arrays.asList(new String[]{str5}));
                                hashSet8.add(foreignKey5);
                                HashSet hashSet9 = new HashSet(1);
                                hashSet9.add(new Index("index_WorkName_work_spec_id", false, Arrays.asList(new String[]{str2})));
                                TableInfo tableInfo5 = new TableInfo("WorkName", hashMap5, hashSet8, hashSet9);
                                TableInfo read5 = TableInfo.read(supportSQLiteDatabase2, "WorkName");
                                if (!tableInfo5.equals(read5)) {
                                    StringBuilder sb = new StringBuilder();
                                    sb.append("Migration didn't properly handle WorkName(androidx.work.impl.model.WorkName).\n Expected:\n");
                                    sb.append(tableInfo5);
                                    sb.append(str11);
                                    sb.append(read5);
                                    throw new IllegalStateException(sb.toString());
                                }
                                return;
                            }
                            StringBuilder sb2 = new StringBuilder();
                            sb2.append("Migration didn't properly handle SystemIdInfo(androidx.work.impl.model.SystemIdInfo).\n Expected:\n");
                            sb2.append(tableInfo4);
                            sb2.append(str11);
                            sb2.append(read4);
                            throw new IllegalStateException(sb2.toString());
                        }
                        StringBuilder sb3 = new StringBuilder();
                        sb3.append("Migration didn't properly handle WorkTag(androidx.work.impl.model.WorkTag).\n Expected:\n");
                        sb3.append(tableInfo3);
                        sb3.append(str11);
                        sb3.append(read3);
                        throw new IllegalStateException(sb3.toString());
                    }
                    StringBuilder sb4 = new StringBuilder();
                    sb4.append("Migration didn't properly handle WorkSpec(androidx.work.impl.model.WorkSpec).\n Expected:\n");
                    sb4.append(tableInfo2);
                    sb4.append(str11);
                    sb4.append(read2);
                    throw new IllegalStateException(sb4.toString());
                }
                StringBuilder sb5 = new StringBuilder();
                sb5.append("Migration didn't properly handle Dependency(androidx.work.impl.model.Dependency).\n Expected:\n");
                sb5.append(tableInfo);
                sb5.append(str11);
                sb5.append(read);
                throw new IllegalStateException(sb5.toString());
            }
        }, "c84d23ade98552f1cec71088c1f0794c", "1db8206f0da6aa81bbdd2d99a82d9e14")).build());
    }

    /* access modifiers changed from: protected */
    public InvalidationTracker createInvalidationTracker() {
        return new InvalidationTracker(this, new HashMap(0), new HashMap(0), "Dependency", "WorkSpec", "WorkTag", "SystemIdInfo", "WorkName");
    }

    public void clearAllTables() {
        super.assertNotMainThread();
        SupportSQLiteDatabase writableDatabase = super.getOpenHelper().getWritableDatabase();
        boolean z = VERSION.SDK_INT >= 21;
        String str = "VACUUM";
        String str2 = "PRAGMA foreign_keys = TRUE";
        String str3 = "PRAGMA wal_checkpoint(FULL)";
        if (!z) {
            try {
                writableDatabase.execSQL("PRAGMA foreign_keys = FALSE");
            } catch (Throwable th) {
                super.endTransaction();
                if (!z) {
                    writableDatabase.execSQL(str2);
                }
                writableDatabase.query(str3).close();
                if (!writableDatabase.inTransaction()) {
                    writableDatabase.execSQL(str);
                }
                throw th;
            }
        }
        super.beginTransaction();
        if (z) {
            writableDatabase.execSQL("PRAGMA defer_foreign_keys = TRUE");
        }
        writableDatabase.execSQL("DELETE FROM `Dependency`");
        writableDatabase.execSQL("DELETE FROM `WorkSpec`");
        writableDatabase.execSQL("DELETE FROM `WorkTag`");
        writableDatabase.execSQL("DELETE FROM `SystemIdInfo`");
        writableDatabase.execSQL("DELETE FROM `WorkName`");
        super.setTransactionSuccessful();
        super.endTransaction();
        if (!z) {
            writableDatabase.execSQL(str2);
        }
        writableDatabase.query(str3).close();
        if (!writableDatabase.inTransaction()) {
            writableDatabase.execSQL(str);
        }
    }

    public WorkSpecDao workSpecDao() {
        WorkSpecDao workSpecDao;
        if (this._workSpecDao != null) {
            return this._workSpecDao;
        }
        synchronized (this) {
            if (this._workSpecDao == null) {
                this._workSpecDao = new WorkSpecDao_Impl(this);
            }
            workSpecDao = this._workSpecDao;
        }
        return workSpecDao;
    }

    public DependencyDao dependencyDao() {
        DependencyDao dependencyDao;
        if (this._dependencyDao != null) {
            return this._dependencyDao;
        }
        synchronized (this) {
            if (this._dependencyDao == null) {
                this._dependencyDao = new DependencyDao_Impl(this);
            }
            dependencyDao = this._dependencyDao;
        }
        return dependencyDao;
    }

    public WorkTagDao workTagDao() {
        WorkTagDao workTagDao;
        if (this._workTagDao != null) {
            return this._workTagDao;
        }
        synchronized (this) {
            if (this._workTagDao == null) {
                this._workTagDao = new WorkTagDao_Impl(this);
            }
            workTagDao = this._workTagDao;
        }
        return workTagDao;
    }

    public SystemIdInfoDao systemIdInfoDao() {
        SystemIdInfoDao systemIdInfoDao;
        if (this._systemIdInfoDao != null) {
            return this._systemIdInfoDao;
        }
        synchronized (this) {
            if (this._systemIdInfoDao == null) {
                this._systemIdInfoDao = new SystemIdInfoDao_Impl(this);
            }
            systemIdInfoDao = this._systemIdInfoDao;
        }
        return systemIdInfoDao;
    }

    public WorkNameDao workNameDao() {
        WorkNameDao workNameDao;
        if (this._workNameDao != null) {
            return this._workNameDao;
        }
        synchronized (this) {
            if (this._workNameDao == null) {
                this._workNameDao = new WorkNameDao_Impl(this);
            }
            workNameDao = this._workNameDao;
        }
        return workNameDao;
    }
}
