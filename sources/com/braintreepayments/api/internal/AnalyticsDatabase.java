package com.braintreepayments.api.internal;

import android.content.ContentValues;
import android.content.Context;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.AsyncTask;
import com.braintreepayments.api.interfaces.BraintreeResponseListener;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class AnalyticsDatabase extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "braintree-analytics.db";
    private static final int DATABASE_VERSION = 1;
    static final String EVENT = "event";

    /* renamed from: ID */
    static final String f228ID = "_id";
    static final String META_JSON = "meta_json";
    private static final String TABLE_NAME = "analytics";
    static final String TIMESTAMP = "timestamp";
    protected final Set<AsyncTask> mTaskSet = new HashSet();

    private static class DatabaseTask extends AsyncTask<Void, Void, Void> {
        private Runnable mDatabaseAction;
        private BraintreeResponseListener<Void> mFinishedCallback;

        public DatabaseTask(Runnable runnable) {
            this.mDatabaseAction = runnable;
        }

        /* access modifiers changed from: protected */
        public Void doInBackground(Void... voidArr) {
            this.mDatabaseAction.run();
            return null;
        }

        /* access modifiers changed from: protected */
        public void onPostExecute(Void voidR) {
            BraintreeResponseListener<Void> braintreeResponseListener = this.mFinishedCallback;
            if (braintreeResponseListener != null) {
                braintreeResponseListener.onResponse(null);
            }
        }

        /* access modifiers changed from: private */
        public void setFinishedCallback(BraintreeResponseListener<Void> braintreeResponseListener) {
            this.mFinishedCallback = braintreeResponseListener;
        }
    }

    public static AnalyticsDatabase getInstance(Context context) {
        return new AnalyticsDatabase(context, DATABASE_NAME, null, 1);
    }

    public AnalyticsDatabase(Context context, String str, CursorFactory cursorFactory, int i) {
        super(context, DATABASE_NAME, cursorFactory, 1);
    }

    public AnalyticsDatabase(Context context, String str, CursorFactory cursorFactory, int i, DatabaseErrorHandler databaseErrorHandler) {
        super(context, DATABASE_NAME, cursorFactory, 1, databaseErrorHandler);
    }

    public void onCreate(SQLiteDatabase sQLiteDatabase) {
        sQLiteDatabase.execSQL("create table analytics(_id integer primary key autoincrement, event text not null, timestamp long not null, meta_json text not null);");
    }

    public void onUpgrade(SQLiteDatabase sQLiteDatabase, int i, int i2) {
        sQLiteDatabase.execSQL("drop table if exists analytics");
        onCreate(sQLiteDatabase);
    }

    public void addEvent(AnalyticsEvent analyticsEvent) {
        final ContentValues contentValues = new ContentValues();
        contentValues.put("event", analyticsEvent.event);
        contentValues.put("timestamp", Long.valueOf(analyticsEvent.timestamp));
        contentValues.put(META_JSON, analyticsEvent.metadata.toString());
        queueTask(new DatabaseTask(new Runnable() {
            /* JADX WARNING: Removed duplicated region for block: B:12:0x001b  */
            /* JADX WARNING: Removed duplicated region for block: B:20:? A[RETURN, SYNTHETIC] */
            /* Code decompiled incorrectly, please refer to instructions dump. */
            public void run() {
                /*
                    r5 = this;
                    r0 = 0
                    com.braintreepayments.api.internal.AnalyticsDatabase r1 = com.braintreepayments.api.internal.AnalyticsDatabase.this     // Catch:{ SQLiteException -> 0x001f, all -> 0x0015 }
                    android.database.sqlite.SQLiteDatabase r1 = r1.getWritableDatabase()     // Catch:{ SQLiteException -> 0x001f, all -> 0x0015 }
                    java.lang.String r2 = "analytics"
                    android.content.ContentValues r3 = r0     // Catch:{ SQLiteException -> 0x0013, all -> 0x0011 }
                    r1.insert(r2, r0, r3)     // Catch:{ SQLiteException -> 0x0013, all -> 0x0011 }
                    if (r1 == 0) goto L_0x0025
                    goto L_0x0022
                L_0x0011:
                    r0 = move-exception
                    goto L_0x0019
                L_0x0013:
                    goto L_0x0020
                L_0x0015:
                    r1 = move-exception
                    r4 = r1
                    r1 = r0
                    r0 = r4
                L_0x0019:
                    if (r1 == 0) goto L_0x001e
                    r1.close()
                L_0x001e:
                    throw r0
                L_0x001f:
                    r1 = r0
                L_0x0020:
                    if (r1 == 0) goto L_0x0025
                L_0x0022:
                    r1.close()
                L_0x0025:
                    return
                */
                throw new UnsupportedOperationException("Method not decompiled: com.braintreepayments.api.internal.AnalyticsDatabase.C11091.run():void");
            }
        }));
    }

    public void removeEvents(List<AnalyticsEvent> list) {
        final StringBuilder sb = new StringBuilder(f228ID);
        sb.append(" in (");
        final String[] strArr = new String[list.size()];
        for (int i = 0; i < list.size(); i++) {
            strArr[i] = Integer.toString(((AnalyticsEvent) list.get(i)).f229id);
            sb.append("?");
            if (i < list.size() - 1) {
                sb.append(",");
            } else {
                sb.append(")");
            }
        }
        queueTask(new DatabaseTask(new Runnable() {
            public void run() {
                SQLiteDatabase sQLiteDatabase = null;
                try {
                    sQLiteDatabase = AnalyticsDatabase.this.getWritableDatabase();
                    sQLiteDatabase.delete("analytics", sb.toString(), strArr);
                    if (sQLiteDatabase == null) {
                        return;
                    }
                } catch (SQLiteException unused) {
                    if (sQLiteDatabase == null) {
                        return;
                    }
                } catch (Throwable th) {
                    if (sQLiteDatabase != null) {
                        sQLiteDatabase.close();
                    }
                    throw th;
                }
                sQLiteDatabase.close();
            }
        }));
    }

    /* JADX WARNING: Code restructure failed: missing block: B:15:0x009b, code lost:
        if (r3 != null) goto L_0x00a8;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:21:0x00a6, code lost:
        if (r3 == null) goto L_0x00ab;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:22:0x00a8, code lost:
        r3.close();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:23:0x00ab, code lost:
        return r2;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public java.util.List<java.util.List<com.braintreepayments.api.internal.AnalyticsEvent>> getPendingRequests() {
        /*
            r17 = this;
            java.lang.String r0 = "meta_json"
            java.lang.String r1 = ","
            java.util.ArrayList r2 = new java.util.ArrayList
            r2.<init>()
            r3 = 0
            android.database.sqlite.SQLiteDatabase r3 = r17.getReadableDatabase()     // Catch:{ SQLiteException -> 0x00a5, all -> 0x009e }
            r4 = 4
            java.lang.String[] r7 = new java.lang.String[r4]     // Catch:{ SQLiteException -> 0x00a5, all -> 0x009e }
            java.lang.String r4 = "group_concat(_id)"
            r14 = 0
            r7[r14] = r4     // Catch:{ SQLiteException -> 0x00a5, all -> 0x009e }
            java.lang.String r4 = "group_concat(event)"
            r15 = 1
            r7[r15] = r4     // Catch:{ SQLiteException -> 0x00a5, all -> 0x009e }
            java.lang.String r4 = "group_concat(timestamp)"
            r13 = 2
            r7[r13] = r4     // Catch:{ SQLiteException -> 0x00a5, all -> 0x009e }
            r4 = 3
            r7[r4] = r0     // Catch:{ SQLiteException -> 0x00a5, all -> 0x009e }
            r5 = 0
            java.lang.String r6 = "analytics"
            r8 = 0
            r9 = 0
            java.lang.String r10 = "meta_json"
            r11 = 0
            java.lang.String r12 = "_id asc"
            r16 = 0
            r4 = r3
            r13 = r16
            android.database.Cursor r4 = r4.query(r5, r6, r7, r8, r9, r10, r11, r12, r13)     // Catch:{ SQLiteException -> 0x00a5, all -> 0x009e }
        L_0x0036:
            boolean r5 = r4.moveToNext()     // Catch:{ SQLiteException -> 0x00a5, all -> 0x009e }
            if (r5 == 0) goto L_0x0098
            java.util.ArrayList r5 = new java.util.ArrayList     // Catch:{ SQLiteException -> 0x00a5, all -> 0x009e }
            r5.<init>()     // Catch:{ SQLiteException -> 0x00a5, all -> 0x009e }
            java.lang.String r6 = r4.getString(r14)     // Catch:{ SQLiteException -> 0x00a5, all -> 0x009e }
            java.lang.String[] r6 = r6.split(r1)     // Catch:{ SQLiteException -> 0x00a5, all -> 0x009e }
            java.lang.String r7 = r4.getString(r15)     // Catch:{ SQLiteException -> 0x00a5, all -> 0x009e }
            java.lang.String[] r7 = r7.split(r1)     // Catch:{ SQLiteException -> 0x00a5, all -> 0x009e }
            r8 = 2
            java.lang.String r9 = r4.getString(r8)     // Catch:{ SQLiteException -> 0x00a5, all -> 0x009e }
            java.lang.String[] r9 = r9.split(r1)     // Catch:{ SQLiteException -> 0x00a5, all -> 0x009e }
            r10 = 0
        L_0x005b:
            int r11 = r7.length     // Catch:{ SQLiteException -> 0x00a5, all -> 0x009e }
            if (r10 >= r11) goto L_0x0094
            com.braintreepayments.api.internal.AnalyticsEvent r11 = new com.braintreepayments.api.internal.AnalyticsEvent     // Catch:{ JSONException -> 0x0091 }
            r11.<init>()     // Catch:{ JSONException -> 0x0091 }
            r12 = r6[r10]     // Catch:{ JSONException -> 0x0091 }
            java.lang.Integer r12 = java.lang.Integer.valueOf(r12)     // Catch:{ JSONException -> 0x0091 }
            int r12 = r12.intValue()     // Catch:{ JSONException -> 0x0091 }
            r11.f229id = r12     // Catch:{ JSONException -> 0x0091 }
            r12 = r7[r10]     // Catch:{ JSONException -> 0x0091 }
            r11.event = r12     // Catch:{ JSONException -> 0x0091 }
            r12 = r9[r10]     // Catch:{ JSONException -> 0x0091 }
            java.lang.Long r12 = java.lang.Long.valueOf(r12)     // Catch:{ JSONException -> 0x0091 }
            long r12 = r12.longValue()     // Catch:{ JSONException -> 0x0091 }
            r11.timestamp = r12     // Catch:{ JSONException -> 0x0091 }
            org.json.JSONObject r12 = new org.json.JSONObject     // Catch:{ JSONException -> 0x0091 }
            int r13 = r4.getColumnIndex(r0)     // Catch:{ JSONException -> 0x0091 }
            java.lang.String r13 = r4.getString(r13)     // Catch:{ JSONException -> 0x0091 }
            r12.<init>(r13)     // Catch:{ JSONException -> 0x0091 }
            r11.metadata = r12     // Catch:{ JSONException -> 0x0091 }
            r5.add(r11)     // Catch:{ JSONException -> 0x0091 }
        L_0x0091:
            int r10 = r10 + 1
            goto L_0x005b
        L_0x0094:
            r2.add(r5)     // Catch:{ SQLiteException -> 0x00a5, all -> 0x009e }
            goto L_0x0036
        L_0x0098:
            r4.close()     // Catch:{ SQLiteException -> 0x00a5, all -> 0x009e }
            if (r3 == 0) goto L_0x00ab
            goto L_0x00a8
        L_0x009e:
            r0 = move-exception
            if (r3 == 0) goto L_0x00a4
            r3.close()
        L_0x00a4:
            throw r0
        L_0x00a5:
            if (r3 == 0) goto L_0x00ab
        L_0x00a8:
            r3.close()
        L_0x00ab:
            return r2
        */
        throw new UnsupportedOperationException("Method not decompiled: com.braintreepayments.api.internal.AnalyticsDatabase.getPendingRequests():java.util.List");
    }

    private void queueTask(final DatabaseTask databaseTask) {
        databaseTask.setFinishedCallback(new BraintreeResponseListener<Void>() {
            public void onResponse(Void voidR) {
                synchronized (AnalyticsDatabase.this.mTaskSet) {
                    AnalyticsDatabase.this.mTaskSet.remove(databaseTask);
                }
            }
        });
        synchronized (this.mTaskSet) {
            this.mTaskSet.add(databaseTask);
        }
        databaseTask.execute(new Void[0]);
    }
}
