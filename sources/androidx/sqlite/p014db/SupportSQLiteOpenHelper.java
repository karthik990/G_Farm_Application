package androidx.sqlite.p014db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.os.Build.VERSION;
import android.util.Log;
import java.io.File;

/* renamed from: androidx.sqlite.db.SupportSQLiteOpenHelper */
public interface SupportSQLiteOpenHelper {

    /* renamed from: androidx.sqlite.db.SupportSQLiteOpenHelper$Callback */
    public static abstract class Callback {
        private static final String TAG = "SupportSQLite";
        public final int version;

        public void onConfigure(SupportSQLiteDatabase supportSQLiteDatabase) {
        }

        public abstract void onCreate(SupportSQLiteDatabase supportSQLiteDatabase);

        public void onOpen(SupportSQLiteDatabase supportSQLiteDatabase) {
        }

        public abstract void onUpgrade(SupportSQLiteDatabase supportSQLiteDatabase, int i, int i2);

        public Callback(int i) {
            this.version = i;
        }

        public void onDowngrade(SupportSQLiteDatabase supportSQLiteDatabase, int i, int i2) {
            StringBuilder sb = new StringBuilder();
            sb.append("Can't downgrade database from version ");
            sb.append(i);
            sb.append(" to ");
            sb.append(i2);
            throw new SQLiteException(sb.toString());
        }

        /* JADX WARNING: Code restructure failed: missing block: B:10:0x0034, code lost:
            if (r0 != null) goto L_0x0036;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:11:0x0036, code lost:
            r3 = r0.iterator();
         */
        /* JADX WARNING: Code restructure failed: missing block: B:13:0x003e, code lost:
            if (r3.hasNext() != false) goto L_0x0040;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:14:0x0040, code lost:
            deleteDatabaseFile((java.lang.String) ((android.util.Pair) r3.next()).second);
         */
        /* JADX WARNING: Code restructure failed: missing block: B:15:0x004e, code lost:
            deleteDatabaseFile(r3.getPath());
         */
        /* JADX WARNING: Code restructure failed: missing block: B:16:0x0055, code lost:
            throw r1;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:7:0x002e, code lost:
            r1 = move-exception;
         */
        /* JADX WARNING: Failed to process nested try/catch */
        /* JADX WARNING: Missing exception handler attribute for start block: B:8:0x0030 */
        /* JADX WARNING: Removed duplicated region for block: B:19:0x0059  */
        /* JADX WARNING: Removed duplicated region for block: B:23:0x0071  */
        /* JADX WARNING: Removed duplicated region for block: B:7:0x002e A[ExcHandler: all (r1v3 'th' java.lang.Throwable A[CUSTOM_DECLARE]), PHI: r0 
          PHI: (r0v10 java.util.List) = (r0v3 java.util.List), (r0v4 java.util.List), (r0v4 java.util.List) binds: [B:5:0x0029, B:8:0x0030, B:9:?] A[DONT_GENERATE, DONT_INLINE], Splitter:B:5:0x0029] */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public void onCorruption(androidx.sqlite.p014db.SupportSQLiteDatabase r3) {
            /*
                r2 = this;
                java.lang.StringBuilder r0 = new java.lang.StringBuilder
                r0.<init>()
                java.lang.String r1 = "Corruption reported by sqlite on database: "
                r0.append(r1)
                java.lang.String r1 = r3.getPath()
                r0.append(r1)
                java.lang.String r0 = r0.toString()
                java.lang.String r1 = "SupportSQLite"
                android.util.Log.e(r1, r0)
                boolean r0 = r3.isOpen()
                if (r0 != 0) goto L_0x0028
                java.lang.String r3 = r3.getPath()
                r2.deleteDatabaseFile(r3)
                return
            L_0x0028:
                r0 = 0
                java.util.List r0 = r3.getAttachedDbs()     // Catch:{ SQLiteException -> 0x0030, all -> 0x002e }
                goto L_0x0030
            L_0x002e:
                r1 = move-exception
                goto L_0x0034
            L_0x0030:
                r3.close()     // Catch:{ IOException -> 0x0056, all -> 0x002e }
                goto L_0x0057
            L_0x0034:
                if (r0 == 0) goto L_0x004e
                java.util.Iterator r3 = r0.iterator()
            L_0x003a:
                boolean r0 = r3.hasNext()
                if (r0 == 0) goto L_0x0055
                java.lang.Object r0 = r3.next()
                android.util.Pair r0 = (android.util.Pair) r0
                java.lang.Object r0 = r0.second
                java.lang.String r0 = (java.lang.String) r0
                r2.deleteDatabaseFile(r0)
                goto L_0x003a
            L_0x004e:
                java.lang.String r3 = r3.getPath()
                r2.deleteDatabaseFile(r3)
            L_0x0055:
                throw r1
            L_0x0056:
            L_0x0057:
                if (r0 == 0) goto L_0x0071
                java.util.Iterator r3 = r0.iterator()
            L_0x005d:
                boolean r0 = r3.hasNext()
                if (r0 == 0) goto L_0x0078
                java.lang.Object r0 = r3.next()
                android.util.Pair r0 = (android.util.Pair) r0
                java.lang.Object r0 = r0.second
                java.lang.String r0 = (java.lang.String) r0
                r2.deleteDatabaseFile(r0)
                goto L_0x005d
            L_0x0071:
                java.lang.String r3 = r3.getPath()
                r2.deleteDatabaseFile(r3)
            L_0x0078:
                return
            */
            throw new UnsupportedOperationException("Method not decompiled: androidx.sqlite.p014db.SupportSQLiteOpenHelper.Callback.onCorruption(androidx.sqlite.db.SupportSQLiteDatabase):void");
        }

        private void deleteDatabaseFile(String str) {
            if (!str.equalsIgnoreCase(":memory:") && str.trim().length() != 0) {
                StringBuilder sb = new StringBuilder();
                sb.append("deleting the database file: ");
                sb.append(str);
                String sb2 = sb.toString();
                String str2 = TAG;
                Log.w(str2, sb2);
                try {
                    if (VERSION.SDK_INT >= 16) {
                        SQLiteDatabase.deleteDatabase(new File(str));
                        return;
                    }
                    try {
                        if (!new File(str).delete()) {
                            StringBuilder sb3 = new StringBuilder();
                            sb3.append("Could not delete the database file ");
                            sb3.append(str);
                            Log.e(str2, sb3.toString());
                        }
                    } catch (Exception e) {
                        Log.e(str2, "error while deleting corrupted database file", e);
                    }
                } catch (Exception e2) {
                    Log.w(str2, "delete failed: ", e2);
                }
            }
        }
    }

    /* renamed from: androidx.sqlite.db.SupportSQLiteOpenHelper$Configuration */
    public static class Configuration {
        public final Callback callback;
        public final Context context;
        public final String name;

        /* renamed from: androidx.sqlite.db.SupportSQLiteOpenHelper$Configuration$Builder */
        public static class Builder {
            Callback mCallback;
            Context mContext;
            String mName;

            public Configuration build() {
                Callback callback = this.mCallback;
                if (callback != null) {
                    Context context = this.mContext;
                    if (context != null) {
                        return new Configuration(context, this.mName, callback);
                    }
                    throw new IllegalArgumentException("Must set a non-null context to create the configuration.");
                }
                throw new IllegalArgumentException("Must set a callback to create the configuration.");
            }

            Builder(Context context) {
                this.mContext = context;
            }

            public Builder name(String str) {
                this.mName = str;
                return this;
            }

            public Builder callback(Callback callback) {
                this.mCallback = callback;
                return this;
            }
        }

        Configuration(Context context2, String str, Callback callback2) {
            this.context = context2;
            this.name = str;
            this.callback = callback2;
        }

        public static Builder builder(Context context2) {
            return new Builder(context2);
        }
    }

    /* renamed from: androidx.sqlite.db.SupportSQLiteOpenHelper$Factory */
    public interface Factory {
        SupportSQLiteOpenHelper create(Configuration configuration);
    }

    void close();

    String getDatabaseName();

    SupportSQLiteDatabase getReadableDatabase();

    SupportSQLiteDatabase getWritableDatabase();

    void setWriteAheadLoggingEnabled(boolean z);
}