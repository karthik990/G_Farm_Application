package androidx.work.impl;

import android.content.Context;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.RoomDatabase.Builder;
import androidx.room.RoomDatabase.Callback;
import androidx.sqlite.p014db.SupportSQLiteDatabase;
import androidx.work.impl.WorkDatabaseMigrations.WorkMigration;
import androidx.work.impl.model.DependencyDao;
import androidx.work.impl.model.SystemIdInfoDao;
import androidx.work.impl.model.WorkNameDao;
import androidx.work.impl.model.WorkSpecDao;
import androidx.work.impl.model.WorkTagDao;
import java.util.concurrent.Executor;
import java.util.concurrent.TimeUnit;

public abstract class WorkDatabase extends RoomDatabase {
    private static final String DB_NAME = "androidx.work.workdb";
    private static final String PRUNE_SQL_FORMAT_PREFIX = "DELETE FROM workspec WHERE state IN (2, 3, 5) AND (period_start_time + minimum_retention_duration) < ";
    private static final String PRUNE_SQL_FORMAT_SUFFIX = " AND (SELECT COUNT(*)=0 FROM dependency WHERE     prerequisite_id=id AND     work_spec_id NOT IN         (SELECT id FROM workspec WHERE state IN (2, 3, 5)))";
    private static final long PRUNE_THRESHOLD_MILLIS = TimeUnit.DAYS.toMillis(7);

    public abstract DependencyDao dependencyDao();

    public abstract SystemIdInfoDao systemIdInfoDao();

    public abstract WorkNameDao workNameDao();

    public abstract WorkSpecDao workSpecDao();

    public abstract WorkTagDao workTagDao();

    public static WorkDatabase create(Context context, Executor executor, boolean z) {
        Builder builder;
        Class<WorkDatabase> cls = WorkDatabase.class;
        if (z) {
            builder = Room.inMemoryDatabaseBuilder(context, cls).allowMainThreadQueries();
        } else {
            builder = Room.databaseBuilder(context, cls, DB_NAME).setQueryExecutor(executor);
        }
        return (WorkDatabase) builder.addCallback(generateCleanupCallback()).addMigrations(WorkDatabaseMigrations.MIGRATION_1_2).addMigrations(new WorkMigration(context, 2, 3)).addMigrations(WorkDatabaseMigrations.MIGRATION_3_4).addMigrations(WorkDatabaseMigrations.MIGRATION_4_5).addMigrations(new WorkMigration(context, 5, 6)).fallbackToDestructiveMigration().build();
    }

    static Callback generateCleanupCallback() {
        return new Callback() {
            public void onOpen(SupportSQLiteDatabase supportSQLiteDatabase) {
                super.onOpen(supportSQLiteDatabase);
                supportSQLiteDatabase.beginTransaction();
                try {
                    supportSQLiteDatabase.execSQL(WorkDatabase.getPruneSQL());
                    supportSQLiteDatabase.setTransactionSuccessful();
                } finally {
                    supportSQLiteDatabase.endTransaction();
                }
            }
        };
    }

    static String getPruneSQL() {
        StringBuilder sb = new StringBuilder();
        sb.append(PRUNE_SQL_FORMAT_PREFIX);
        sb.append(getPruneDate());
        sb.append(PRUNE_SQL_FORMAT_SUFFIX);
        return sb.toString();
    }

    static long getPruneDate() {
        return System.currentTimeMillis() - PRUNE_THRESHOLD_MILLIS;
    }
}
