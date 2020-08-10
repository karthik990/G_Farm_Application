package com.google.android.gms.internal.gtm;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.net.Uri.Builder;
import android.text.TextUtils;
import com.google.android.gms.analytics.zzk;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.common.util.HttpUtils;
import java.io.Closeable;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

final class zzay extends zzan implements Closeable {
    /* access modifiers changed from: private */
    public static final String zzxj;
    private static final String zzxk;
    private final zzaz zzxl;
    private final zzcv zzxm = new zzcv(zzcn());
    /* access modifiers changed from: private */
    public final zzcv zzxn = new zzcv(zzcn());

    zzay(zzap zzap) {
        super(zzap);
        this.zzxl = new zzaz(this, zzap.getContext(), "google_analytics_v4.db");
    }

    /* access modifiers changed from: private */
    public static String zzdt() {
        return "google_analytics_v4.db";
    }

    /* access modifiers changed from: protected */
    public final void zzaw() {
    }

    public final void beginTransaction() {
        zzdb();
        getWritableDatabase().beginTransaction();
    }

    public final void setTransactionSuccessful() {
        zzdb();
        getWritableDatabase().setTransactionSuccessful();
    }

    public final void endTransaction() {
        zzdb();
        getWritableDatabase().endTransaction();
    }

    public final void zzc(zzcd zzcd) {
        String str;
        Preconditions.checkNotNull(zzcd);
        zzk.zzav();
        zzdb();
        Preconditions.checkNotNull(zzcd);
        Builder builder = new Builder();
        for (Entry entry : zzcd.zzdm().entrySet()) {
            String str2 = (String) entry.getKey();
            if (!"ht".equals(str2) && !"qt".equals(str2) && !"AppUID".equals(str2)) {
                builder.appendQueryParameter(str2, (String) entry.getValue());
            }
        }
        String encodedQuery = builder.build().getEncodedQuery();
        if (encodedQuery == null) {
            encodedQuery = "";
        }
        if (encodedQuery.length() > 8192) {
            zzco().zza(zzcd, "Hit length exceeds the maximum allowed size");
            return;
        }
        int intValue = ((Integer) zzby.zzze.get()).intValue();
        long zzdl = zzdl();
        if (zzdl > ((long) (intValue - 1))) {
            List zzc = zzc((zzdl - ((long) intValue)) + 1);
            zzd("Store full, deleting hits to make room, count", Integer.valueOf(zzc.size()));
            zza(zzc);
        }
        SQLiteDatabase writableDatabase = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("hit_string", encodedQuery);
        contentValues.put("hit_time", Long.valueOf(zzcd.zzfh()));
        contentValues.put("hit_app_id", Integer.valueOf(zzcd.zzff()));
        if (zzcd.zzfj()) {
            str = zzbq.zzet();
        } else {
            str = zzbq.zzeu();
        }
        contentValues.put("hit_url", str);
        try {
            long insert = writableDatabase.insert("hits2", null, contentValues);
            if (insert == -1) {
                zzu("Failed to insert a hit (got -1)");
            } else {
                zzb("Hit saved to database. db-id, hit", Long.valueOf(insert), zzcd);
            }
        } catch (SQLiteException e) {
            zze("Error storing a hit", e);
        }
    }

    private final long zzdl() {
        zzk.zzav();
        zzdb();
        return zza("SELECT COUNT(*) FROM hits2", null);
    }

    /* access modifiers changed from: 0000 */
    public final boolean isEmpty() {
        return zzdl() == 0;
    }

    private final List<Long> zzc(long j) {
        String str = "hit_id";
        zzk.zzav();
        zzdb();
        if (j <= 0) {
            return Collections.emptyList();
        }
        SQLiteDatabase writableDatabase = getWritableDatabase();
        ArrayList arrayList = new ArrayList();
        Cursor cursor = null;
        String str2 = "hits2";
        try {
            Cursor query = writableDatabase.query(str2, new String[]{str}, null, null, null, null, String.format("%s ASC", new Object[]{str}), Long.toString(j));
            if (query.moveToFirst()) {
                do {
                    arrayList.add(Long.valueOf(query.getLong(0)));
                } while (query.moveToNext());
            }
            if (query != null) {
                query.close();
            }
        } catch (SQLiteException e) {
            zzd("Error selecting hit ids", e);
            if (cursor != null) {
                cursor.close();
            }
        } catch (Throwable th) {
            if (cursor != null) {
                cursor.close();
            }
            throw th;
        }
        return arrayList;
    }

    /* JADX WARNING: Removed duplicated region for block: B:27:0x00af  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final java.util.List<com.google.android.gms.internal.gtm.zzcd> zzd(long r23) {
        /*
            r22 = this;
            r10 = r22
            java.lang.String r0 = "hit_id"
            r11 = 1
            r12 = 0
            r1 = 0
            int r3 = (r23 > r1 ? 1 : (r23 == r1 ? 0 : -1))
            if (r3 < 0) goto L_0x000e
            r1 = 1
            goto L_0x000f
        L_0x000e:
            r1 = 0
        L_0x000f:
            com.google.android.gms.common.internal.Preconditions.checkArgument(r1)
            com.google.android.gms.analytics.zzk.zzav()
            r22.zzdb()
            android.database.sqlite.SQLiteDatabase r13 = r22.getWritableDatabase()
            r1 = 0
            java.lang.String r14 = "hits2"
            r2 = 5
            java.lang.String[] r15 = new java.lang.String[r2]     // Catch:{ SQLiteException -> 0x00a6 }
            r15[r12] = r0     // Catch:{ SQLiteException -> 0x00a6 }
            java.lang.String r2 = "hit_time"
            r15[r11] = r2     // Catch:{ SQLiteException -> 0x00a6 }
            java.lang.String r2 = "hit_string"
            r9 = 2
            r15[r9] = r2     // Catch:{ SQLiteException -> 0x00a6 }
            java.lang.String r2 = "hit_url"
            r7 = 3
            r15[r7] = r2     // Catch:{ SQLiteException -> 0x00a6 }
            java.lang.String r2 = "hit_app_id"
            r8 = 4
            r15[r8] = r2     // Catch:{ SQLiteException -> 0x00a6 }
            r16 = 0
            r17 = 0
            r18 = 0
            r19 = 0
            java.lang.String r2 = "%s ASC"
            java.lang.Object[] r3 = new java.lang.Object[r11]     // Catch:{ SQLiteException -> 0x00a6 }
            r3[r12] = r0     // Catch:{ SQLiteException -> 0x00a6 }
            java.lang.String r20 = java.lang.String.format(r2, r3)     // Catch:{ SQLiteException -> 0x00a6 }
            java.lang.String r21 = java.lang.Long.toString(r23)     // Catch:{ SQLiteException -> 0x00a6 }
            android.database.Cursor r13 = r13.query(r14, r15, r16, r17, r18, r19, r20, r21)     // Catch:{ SQLiteException -> 0x00a6 }
            java.util.ArrayList r0 = new java.util.ArrayList     // Catch:{ SQLiteException -> 0x00a1, all -> 0x009e }
            r0.<init>()     // Catch:{ SQLiteException -> 0x00a1, all -> 0x009e }
            boolean r1 = r13.moveToFirst()     // Catch:{ SQLiteException -> 0x00a1, all -> 0x009e }
            if (r1 == 0) goto L_0x0098
        L_0x005c:
            long r14 = r13.getLong(r12)     // Catch:{ SQLiteException -> 0x00a1, all -> 0x009e }
            long r4 = r13.getLong(r11)     // Catch:{ SQLiteException -> 0x00a1, all -> 0x009e }
            java.lang.String r1 = r13.getString(r9)     // Catch:{ SQLiteException -> 0x00a1, all -> 0x009e }
            java.lang.String r2 = r13.getString(r7)     // Catch:{ SQLiteException -> 0x00a1, all -> 0x009e }
            int r16 = r13.getInt(r8)     // Catch:{ SQLiteException -> 0x00a1, all -> 0x009e }
            java.util.Map r3 = r10.zzv(r1)     // Catch:{ SQLiteException -> 0x00a1, all -> 0x009e }
            boolean r6 = com.google.android.gms.internal.gtm.zzcz.zzaj(r2)     // Catch:{ SQLiteException -> 0x00a1, all -> 0x009e }
            com.google.android.gms.internal.gtm.zzcd r2 = new com.google.android.gms.internal.gtm.zzcd     // Catch:{ SQLiteException -> 0x00a1, all -> 0x009e }
            r1 = r2
            r11 = r2
            r2 = r22
            r18 = 4
            r19 = 3
            r7 = r14
            r14 = 2
            r9 = r16
            r1.<init>(r2, r3, r4, r6, r7, r9)     // Catch:{ SQLiteException -> 0x00a1, all -> 0x009e }
            r0.add(r11)     // Catch:{ SQLiteException -> 0x00a1, all -> 0x009e }
            boolean r1 = r13.moveToNext()     // Catch:{ SQLiteException -> 0x00a1, all -> 0x009e }
            if (r1 != 0) goto L_0x0093
            goto L_0x0098
        L_0x0093:
            r7 = 3
            r8 = 4
            r9 = 2
            r11 = 1
            goto L_0x005c
        L_0x0098:
            if (r13 == 0) goto L_0x009d
            r13.close()
        L_0x009d:
            return r0
        L_0x009e:
            r0 = move-exception
            r1 = r13
            goto L_0x00ad
        L_0x00a1:
            r0 = move-exception
            r1 = r13
            goto L_0x00a7
        L_0x00a4:
            r0 = move-exception
            goto L_0x00ad
        L_0x00a6:
            r0 = move-exception
        L_0x00a7:
            java.lang.String r2 = "Error loading hits from the database"
            r10.zze(r2, r0)     // Catch:{ all -> 0x00a4 }
            throw r0     // Catch:{ all -> 0x00a4 }
        L_0x00ad:
            if (r1 == 0) goto L_0x00b2
            r1.close()
        L_0x00b2:
            goto L_0x00b4
        L_0x00b3:
            throw r0
        L_0x00b4:
            goto L_0x00b3
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.gtm.zzay.zzd(long):java.util.List");
    }

    public final void zza(List<Long> list) {
        Preconditions.checkNotNull(list);
        zzk.zzav();
        zzdb();
        if (!list.isEmpty()) {
            StringBuilder sb = new StringBuilder("hit_id");
            sb.append(" in (");
            for (int i = 0; i < list.size(); i++) {
                Long l = (Long) list.get(i);
                if (l == null || l.longValue() == 0) {
                    throw new SQLiteException("Invalid hit id");
                }
                if (i > 0) {
                    sb.append(",");
                }
                sb.append(l);
            }
            sb.append(")");
            String sb2 = sb.toString();
            try {
                SQLiteDatabase writableDatabase = getWritableDatabase();
                zza("Deleting dispatched hits. count", Integer.valueOf(list.size()));
                int delete = writableDatabase.delete("hits2", sb2, null);
                if (delete != list.size()) {
                    zzb("Deleted fewer hits then expected", Integer.valueOf(list.size()), Integer.valueOf(delete), sb2);
                }
            } catch (SQLiteException e) {
                zze("Error deleting hits", e);
                throw e;
            }
        }
    }

    public final void zze(long j) {
        zzk.zzav();
        zzdb();
        ArrayList arrayList = new ArrayList(1);
        arrayList.add(Long.valueOf(j));
        zza("Deleting hit, id", Long.valueOf(j));
        zza((List<Long>) arrayList);
    }

    public final int zzdr() {
        zzk.zzav();
        zzdb();
        if (!this.zzxm.zzj(86400000)) {
            return 0;
        }
        this.zzxm.start();
        zzq("Deleting stale hits (if any)");
        int delete = getWritableDatabase().delete("hits2", "hit_time < ?", new String[]{Long.toString(zzcn().currentTimeMillis() - 2592000000L)});
        zza("Deleted stale hits, count", Integer.valueOf(delete));
        return delete;
    }

    public final long zzds() {
        zzk.zzav();
        zzdb();
        return zza(zzxk, (String[]) null, 0);
    }

    public final long zza(long j, String str, String str2) {
        Preconditions.checkNotEmpty(str);
        Preconditions.checkNotEmpty(str2);
        zzdb();
        zzk.zzav();
        return zza("SELECT hits_count FROM properties WHERE app_uid=? AND cid=? AND tid=?", new String[]{String.valueOf(j), str, str2}, 0);
    }

    /* JADX WARNING: Removed duplicated region for block: B:39:0x00c7  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final java.util.List<com.google.android.gms.internal.gtm.zzas> zzf(long r26) {
        /*
            r25 = this;
            r1 = r25
            r25.zzdb()
            com.google.android.gms.analytics.zzk.zzav()
            android.database.sqlite.SQLiteDatabase r2 = r25.getWritableDatabase()
            r0 = 5
            java.lang.String[] r4 = new java.lang.String[r0]     // Catch:{ SQLiteException -> 0x00bb, all -> 0x00b8 }
            java.lang.String r0 = "cid"
            r12 = 0
            r4[r12] = r0     // Catch:{ SQLiteException -> 0x00bb, all -> 0x00b8 }
            java.lang.String r0 = "tid"
            r13 = 1
            r4[r13] = r0     // Catch:{ SQLiteException -> 0x00bb, all -> 0x00b8 }
            java.lang.String r0 = "adid"
            r14 = 2
            r4[r14] = r0     // Catch:{ SQLiteException -> 0x00bb, all -> 0x00b8 }
            java.lang.String r0 = "hits_count"
            r15 = 3
            r4[r15] = r0     // Catch:{ SQLiteException -> 0x00bb, all -> 0x00b8 }
            java.lang.String r0 = "params"
            r10 = 4
            r4[r10] = r0     // Catch:{ SQLiteException -> 0x00bb, all -> 0x00b8 }
            com.google.android.gms.internal.gtm.zzbz<java.lang.Integer> r0 = com.google.android.gms.internal.gtm.zzby.zzzg     // Catch:{ SQLiteException -> 0x00bb, all -> 0x00b8 }
            java.lang.Object r0 = r0.get()     // Catch:{ SQLiteException -> 0x00bb, all -> 0x00b8 }
            java.lang.Integer r0 = (java.lang.Integer) r0     // Catch:{ SQLiteException -> 0x00bb, all -> 0x00b8 }
            int r0 = r0.intValue()     // Catch:{ SQLiteException -> 0x00bb, all -> 0x00b8 }
            java.lang.String r16 = java.lang.String.valueOf(r0)     // Catch:{ SQLiteException -> 0x00bb, all -> 0x00b8 }
            java.lang.String r5 = "app_uid=?"
            java.lang.String[] r6 = new java.lang.String[r13]     // Catch:{ SQLiteException -> 0x00bb, all -> 0x00b8 }
            java.lang.String r3 = "0"
            r6[r12] = r3     // Catch:{ SQLiteException -> 0x00bb, all -> 0x00b8 }
            java.lang.String r3 = "properties"
            r7 = 0
            r8 = 0
            r9 = 0
            r11 = 4
            r10 = r16
            android.database.Cursor r2 = r2.query(r3, r4, r5, r6, r7, r8, r9, r10)     // Catch:{ SQLiteException -> 0x00bb, all -> 0x00b8 }
            java.util.ArrayList r3 = new java.util.ArrayList     // Catch:{ SQLiteException -> 0x00b5, all -> 0x00b3 }
            r3.<init>()     // Catch:{ SQLiteException -> 0x00b5, all -> 0x00b3 }
            boolean r4 = r2.moveToFirst()     // Catch:{ SQLiteException -> 0x00b5, all -> 0x00b3 }
            if (r4 == 0) goto L_0x00a2
        L_0x0057:
            java.lang.String r4 = r2.getString(r12)     // Catch:{ SQLiteException -> 0x00b5, all -> 0x00b3 }
            java.lang.String r5 = r2.getString(r13)     // Catch:{ SQLiteException -> 0x00b5, all -> 0x00b3 }
            int r6 = r2.getInt(r14)     // Catch:{ SQLiteException -> 0x00b5, all -> 0x00b3 }
            if (r6 == 0) goto L_0x0068
            r21 = 1
            goto L_0x006a
        L_0x0068:
            r21 = 0
        L_0x006a:
            int r6 = r2.getInt(r15)     // Catch:{ SQLiteException -> 0x00b5, all -> 0x00b3 }
            long r6 = (long) r6     // Catch:{ SQLiteException -> 0x00b5, all -> 0x00b3 }
            java.lang.String r8 = r2.getString(r11)     // Catch:{ SQLiteException -> 0x00b5, all -> 0x00b3 }
            java.util.Map r24 = r1.zzw(r8)     // Catch:{ SQLiteException -> 0x00b5, all -> 0x00b3 }
            boolean r8 = android.text.TextUtils.isEmpty(r4)     // Catch:{ SQLiteException -> 0x00b5, all -> 0x00b3 }
            if (r8 != 0) goto L_0x0097
            boolean r8 = android.text.TextUtils.isEmpty(r5)     // Catch:{ SQLiteException -> 0x00b5, all -> 0x00b3 }
            if (r8 == 0) goto L_0x0084
            goto L_0x0097
        L_0x0084:
            com.google.android.gms.internal.gtm.zzas r8 = new com.google.android.gms.internal.gtm.zzas     // Catch:{ SQLiteException -> 0x00b5, all -> 0x00b3 }
            r17 = 0
            r16 = r8
            r19 = r4
            r20 = r5
            r22 = r6
            r16.<init>(r17, r19, r20, r21, r22, r24)     // Catch:{ SQLiteException -> 0x00b5, all -> 0x00b3 }
            r3.add(r8)     // Catch:{ SQLiteException -> 0x00b5, all -> 0x00b3 }
            goto L_0x009c
        L_0x0097:
            java.lang.String r6 = "Read property with empty client id or tracker id"
            r1.zzc(r6, r4, r5)     // Catch:{ SQLiteException -> 0x00b5, all -> 0x00b3 }
        L_0x009c:
            boolean r4 = r2.moveToNext()     // Catch:{ SQLiteException -> 0x00b5, all -> 0x00b3 }
            if (r4 != 0) goto L_0x0057
        L_0x00a2:
            int r4 = r3.size()     // Catch:{ SQLiteException -> 0x00b5, all -> 0x00b3 }
            if (r4 < r0) goto L_0x00ad
            java.lang.String r0 = "Sending hits to too many properties. Campaign report might be incorrect"
            r1.zzt(r0)     // Catch:{ SQLiteException -> 0x00b5, all -> 0x00b3 }
        L_0x00ad:
            if (r2 == 0) goto L_0x00b2
            r2.close()
        L_0x00b2:
            return r3
        L_0x00b3:
            r0 = move-exception
            goto L_0x00c5
        L_0x00b5:
            r0 = move-exception
            r11 = r2
            goto L_0x00bd
        L_0x00b8:
            r0 = move-exception
            r2 = 0
            goto L_0x00c5
        L_0x00bb:
            r0 = move-exception
            r11 = 0
        L_0x00bd:
            java.lang.String r2 = "Error loading hits from the database"
            r1.zze(r2, r0)     // Catch:{ all -> 0x00c3 }
            throw r0     // Catch:{ all -> 0x00c3 }
        L_0x00c3:
            r0 = move-exception
            r2 = r11
        L_0x00c5:
            if (r2 == 0) goto L_0x00ca
            r2.close()
        L_0x00ca:
            goto L_0x00cc
        L_0x00cb:
            throw r0
        L_0x00cc:
            goto L_0x00cb
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.gtm.zzay.zzf(long):java.util.List");
    }

    public final void close() {
        try {
            this.zzxl.close();
        } catch (SQLiteException e) {
            zze("Sql error closing database", e);
        } catch (IllegalStateException e2) {
            zze("Error closing database", e2);
        }
    }

    private final long zza(String str, String[] strArr) {
        Cursor cursor = null;
        try {
            cursor = getWritableDatabase().rawQuery(str, null);
            if (cursor.moveToFirst()) {
                long j = cursor.getLong(0);
                if (cursor != null) {
                    cursor.close();
                }
                return j;
            }
            throw new SQLiteException("Database returned empty set");
        } catch (SQLiteException e) {
            zzd("Database error", str, e);
            throw e;
        } catch (Throwable th) {
            if (cursor != null) {
                cursor.close();
            }
            throw th;
        }
    }

    private final long zza(String str, String[] strArr, long j) {
        Cursor cursor = null;
        try {
            Cursor rawQuery = getWritableDatabase().rawQuery(str, strArr);
            if (rawQuery.moveToFirst()) {
                long j2 = rawQuery.getLong(0);
                if (rawQuery != null) {
                    rawQuery.close();
                }
                return j2;
            }
            if (rawQuery != null) {
                rawQuery.close();
            }
            return 0;
        } catch (SQLiteException e) {
            zzd("Database error", str, e);
            throw e;
        } catch (Throwable th) {
            if (cursor != null) {
                cursor.close();
            }
            throw th;
        }
    }

    private final Map<String, String> zzv(String str) {
        String str2 = "?";
        if (TextUtils.isEmpty(str)) {
            return new HashMap(0);
        }
        try {
            if (!str.startsWith(str2)) {
                String valueOf = String.valueOf(str);
                str = valueOf.length() != 0 ? str2.concat(valueOf) : new String(str2);
            }
            return HttpUtils.parse(new URI(str), "UTF-8");
        } catch (URISyntaxException e) {
            zze("Error parsing hit parameters", e);
            return new HashMap(0);
        }
    }

    private final Map<String, String> zzw(String str) {
        if (TextUtils.isEmpty(str)) {
            return new HashMap(0);
        }
        String str2 = "?";
        try {
            String valueOf = String.valueOf(str);
            return HttpUtils.parse(new URI(valueOf.length() != 0 ? str2.concat(valueOf) : new String(str2)), "UTF-8");
        } catch (URISyntaxException e) {
            zze("Error parsing property parameters", e);
            return new HashMap(0);
        }
    }

    /* access modifiers changed from: 0000 */
    public final SQLiteDatabase getWritableDatabase() {
        try {
            return this.zzxl.getWritableDatabase();
        } catch (SQLiteException e) {
            zzd("Error opening database", e);
            throw e;
        }
    }

    static {
        String str = "hits2";
        String str2 = "hit_time";
        zzxj = String.format("CREATE TABLE IF NOT EXISTS %s ( '%s' INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, '%s' INTEGER NOT NULL, '%s' TEXT NOT NULL, '%s' TEXT NOT NULL, '%s' INTEGER);", new Object[]{str, "hit_id", str2, "hit_url", "hit_string", "hit_app_id"});
        zzxk = String.format("SELECT MAX(%s) FROM %s WHERE 1;", new Object[]{str2, str});
    }
}
