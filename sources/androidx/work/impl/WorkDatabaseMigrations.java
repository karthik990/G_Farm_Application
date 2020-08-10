package androidx.work.impl;

import android.content.Context;
import android.os.Build.VERSION;
import androidx.room.migration.Migration;
import androidx.sqlite.p014db.SupportSQLiteDatabase;
import androidx.work.impl.utils.Preferences;

public class WorkDatabaseMigrations {
    private static final String CREATE_SYSTEM_ID_INFO = "CREATE TABLE IF NOT EXISTS `SystemIdInfo` (`work_spec_id` TEXT NOT NULL, `system_id` INTEGER NOT NULL, PRIMARY KEY(`work_spec_id`), FOREIGN KEY(`work_spec_id`) REFERENCES `WorkSpec`(`id`) ON UPDATE CASCADE ON DELETE CASCADE )";
    private static final String MIGRATE_ALARM_INFO_TO_SYSTEM_ID_INFO = "INSERT INTO SystemIdInfo(work_spec_id, system_id) SELECT work_spec_id, alarm_id AS system_id FROM alarmInfo";
    public static Migration MIGRATION_1_2 = new Migration(1, 2) {
        public void migrate(SupportSQLiteDatabase supportSQLiteDatabase) {
            supportSQLiteDatabase.execSQL(WorkDatabaseMigrations.CREATE_SYSTEM_ID_INFO);
            supportSQLiteDatabase.execSQL(WorkDatabaseMigrations.MIGRATE_ALARM_INFO_TO_SYSTEM_ID_INFO);
            supportSQLiteDatabase.execSQL(WorkDatabaseMigrations.REMOVE_ALARM_INFO);
            supportSQLiteDatabase.execSQL("INSERT OR IGNORE INTO worktag(tag, work_spec_id) SELECT worker_class_name AS tag, id AS work_spec_id FROM workspec");
        }
    };
    public static Migration MIGRATION_3_4 = new Migration(3, 4) {
        public void migrate(SupportSQLiteDatabase supportSQLiteDatabase) {
            if (VERSION.SDK_INT >= 23) {
                supportSQLiteDatabase.execSQL(WorkDatabaseMigrations.PERIODIC_WORK_SET_SCHEDULE_REQUESTED_AT);
            }
        }
    };
    public static Migration MIGRATION_4_5 = new Migration(4, 5) {
        public void migrate(SupportSQLiteDatabase supportSQLiteDatabase) {
            supportSQLiteDatabase.execSQL(WorkDatabaseMigrations.WORKSPEC_ADD_TRIGGER_UPDATE_DELAY);
            supportSQLiteDatabase.execSQL(WorkDatabaseMigrations.WORKSPEC_ADD_TRIGGER_MAX_CONTENT_DELAY);
        }
    };
    private static final String PERIODIC_WORK_SET_SCHEDULE_REQUESTED_AT = "UPDATE workspec SET schedule_requested_at=0 WHERE state NOT IN (2, 3, 5) AND schedule_requested_at=-1 AND interval_duration<>0";
    private static final String REMOVE_ALARM_INFO = "DROP TABLE IF EXISTS alarmInfo";
    public static final int VERSION_1 = 1;
    public static final int VERSION_2 = 2;
    public static final int VERSION_3 = 3;
    public static final int VERSION_4 = 4;
    public static final int VERSION_5 = 5;
    public static final int VERSION_6 = 6;
    private static final String WORKSPEC_ADD_TRIGGER_MAX_CONTENT_DELAY = "ALTER TABLE workspec ADD COLUMN `trigger_max_content_delay` INTEGER NOT NULL DEFAULT -1";
    private static final String WORKSPEC_ADD_TRIGGER_UPDATE_DELAY = "ALTER TABLE workspec ADD COLUMN `trigger_content_update_delay` INTEGER NOT NULL DEFAULT -1";

    public static class WorkMigration extends Migration {
        final Context mContext;

        public WorkMigration(Context context, int i, int i2) {
            super(i, i2);
            this.mContext = context;
        }

        public void migrate(SupportSQLiteDatabase supportSQLiteDatabase) {
            new Preferences(this.mContext).setNeedsReschedule(true);
        }
    }

    private WorkDatabaseMigrations() {
    }
}
