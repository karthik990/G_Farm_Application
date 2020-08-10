package com.google.android.gms.measurement.internal;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.os.Parcelable;
import android.text.TextUtils;
import android.util.Pair;
import com.braintreepayments.api.models.PostalAddressParser;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.internal.measurement.zzfi;
import com.google.android.gms.internal.measurement.zzfj;
import com.google.android.gms.internal.measurement.zzfm;
import com.google.android.gms.internal.measurement.zzft;
import com.google.android.gms.internal.measurement.zzfu;
import com.google.android.gms.internal.measurement.zzfw;
import com.google.android.gms.internal.measurement.zzxz;
import com.google.android.gms.internal.measurement.zzya;
import com.google.android.gms.measurement.AppMeasurement;
import com.google.firebase.analytics.FirebaseAnalytics.Param;
import java.io.IOException;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import lib.android.paypal.com.magnessdk.p100a.C5985b;
import org.objectweb.asm.Opcodes;
import org.slf4j.Marker;

final class zzt extends zzfm {
    /* access modifiers changed from: private */
    public static final String[] zzagz = {"last_bundled_timestamp", "ALTER TABLE events ADD COLUMN last_bundled_timestamp INTEGER;", "last_bundled_day", "ALTER TABLE events ADD COLUMN last_bundled_day INTEGER;", "last_sampled_complex_event_id", "ALTER TABLE events ADD COLUMN last_sampled_complex_event_id INTEGER;", "last_sampling_rate", "ALTER TABLE events ADD COLUMN last_sampling_rate INTEGER;", "last_exempt_from_sampling", "ALTER TABLE events ADD COLUMN last_exempt_from_sampling INTEGER;"};
    /* access modifiers changed from: private */
    public static final String[] zzaha = {"origin", "ALTER TABLE user_attributes ADD COLUMN origin TEXT;"};
    /* access modifiers changed from: private */
    public static final String[] zzahb = {"app_version", "ALTER TABLE apps ADD COLUMN app_version TEXT;", "app_store", "ALTER TABLE apps ADD COLUMN app_store TEXT;", "gmp_version", "ALTER TABLE apps ADD COLUMN gmp_version INTEGER;", "dev_cert_hash", "ALTER TABLE apps ADD COLUMN dev_cert_hash INTEGER;", "measurement_enabled", "ALTER TABLE apps ADD COLUMN measurement_enabled INTEGER;", "last_bundle_start_timestamp", "ALTER TABLE apps ADD COLUMN last_bundle_start_timestamp INTEGER;", "day", "ALTER TABLE apps ADD COLUMN day INTEGER;", "daily_public_events_count", "ALTER TABLE apps ADD COLUMN daily_public_events_count INTEGER;", "daily_events_count", "ALTER TABLE apps ADD COLUMN daily_events_count INTEGER;", "daily_conversions_count", "ALTER TABLE apps ADD COLUMN daily_conversions_count INTEGER;", "remote_config", "ALTER TABLE apps ADD COLUMN remote_config BLOB;", "config_fetched_time", "ALTER TABLE apps ADD COLUMN config_fetched_time INTEGER;", "failed_config_fetch_time", "ALTER TABLE apps ADD COLUMN failed_config_fetch_time INTEGER;", "app_version_int", "ALTER TABLE apps ADD COLUMN app_version_int INTEGER;", "firebase_instance_id", "ALTER TABLE apps ADD COLUMN firebase_instance_id TEXT;", "daily_error_events_count", "ALTER TABLE apps ADD COLUMN daily_error_events_count INTEGER;", "daily_realtime_events_count", "ALTER TABLE apps ADD COLUMN daily_realtime_events_count INTEGER;", "health_monitor_sample", "ALTER TABLE apps ADD COLUMN health_monitor_sample TEXT;", C5985b.f4233f, "ALTER TABLE apps ADD COLUMN android_id INTEGER;", "adid_reporting_enabled", "ALTER TABLE apps ADD COLUMN adid_reporting_enabled INTEGER;", "ssaid_reporting_enabled", "ALTER TABLE apps ADD COLUMN ssaid_reporting_enabled INTEGER;", "admob_app_id", "ALTER TABLE apps ADD COLUMN admob_app_id TEXT;", "linked_admob_app_id", "ALTER TABLE apps ADD COLUMN linked_admob_app_id TEXT;"};
    /* access modifiers changed from: private */
    public static final String[] zzahc = {"realtime", "ALTER TABLE raw_events ADD COLUMN realtime INTEGER;"};
    /* access modifiers changed from: private */
    public static final String[] zzahd = {"has_realtime", "ALTER TABLE queue ADD COLUMN has_realtime INTEGER;", "retry_count", "ALTER TABLE queue ADD COLUMN retry_count INTEGER;"};
    /* access modifiers changed from: private */
    public static final String[] zzahe = {"previous_install_count", "ALTER TABLE app2 ADD COLUMN previous_install_count INTEGER;"};
    private final zzw zzahf = new zzw(this, getContext(), "google_app_measurement.db");
    /* access modifiers changed from: private */
    public final zzfi zzahg = new zzfi(zzbx());

    zzt(zzfn zzfn) {
        super(zzfn);
    }

    /* access modifiers changed from: protected */
    public final boolean zzgy() {
        return false;
    }

    public final void beginTransaction() {
        zzcl();
        getWritableDatabase().beginTransaction();
    }

    public final void setTransactionSuccessful() {
        zzcl();
        getWritableDatabase().setTransactionSuccessful();
    }

    public final void endTransaction() {
        zzcl();
        getWritableDatabase().endTransaction();
    }

    private final long zza(String str, String[] strArr) {
        Cursor cursor = null;
        try {
            cursor = getWritableDatabase().rawQuery(str, strArr);
            if (cursor.moveToFirst()) {
                long j = cursor.getLong(0);
                if (cursor != null) {
                    cursor.close();
                }
                return j;
            }
            throw new SQLiteException("Database returned empty set");
        } catch (SQLiteException e) {
            zzgt().zzjg().zze("Database error", str, e);
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
            return j;
        } catch (SQLiteException e) {
            zzgt().zzjg().zze("Database error", str, e);
            throw e;
        } catch (Throwable th) {
            if (cursor != null) {
                cursor.close();
            }
            throw th;
        }
    }

    /* access modifiers changed from: 0000 */
    public final SQLiteDatabase getWritableDatabase() {
        zzaf();
        try {
            return this.zzahf.getWritableDatabase();
        } catch (SQLiteException e) {
            zzgt().zzjj().zzg("Error opening database", e);
            throw e;
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:55:0x012d  */
    /* JADX WARNING: Removed duplicated region for block: B:59:0x0134  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final com.google.android.gms.measurement.internal.zzac zzg(java.lang.String r23, java.lang.String r24) {
        /*
            r22 = this;
            r15 = r24
            com.google.android.gms.common.internal.Preconditions.checkNotEmpty(r23)
            com.google.android.gms.common.internal.Preconditions.checkNotEmpty(r24)
            r22.zzaf()
            r22.zzcl()
            r16 = 0
            android.database.sqlite.SQLiteDatabase r1 = r22.getWritableDatabase()     // Catch:{ SQLiteException -> 0x010d, all -> 0x0109 }
            java.lang.String r2 = "events"
            r0 = 8
            java.lang.String[] r3 = new java.lang.String[r0]     // Catch:{ SQLiteException -> 0x010d, all -> 0x0109 }
            java.lang.String r0 = "lifetime_count"
            r9 = 0
            r3[r9] = r0     // Catch:{ SQLiteException -> 0x010d, all -> 0x0109 }
            java.lang.String r0 = "current_bundle_count"
            r10 = 1
            r3[r10] = r0     // Catch:{ SQLiteException -> 0x010d, all -> 0x0109 }
            java.lang.String r0 = "last_fire_timestamp"
            r11 = 2
            r3[r11] = r0     // Catch:{ SQLiteException -> 0x010d, all -> 0x0109 }
            java.lang.String r0 = "last_bundled_timestamp"
            r12 = 3
            r3[r12] = r0     // Catch:{ SQLiteException -> 0x010d, all -> 0x0109 }
            java.lang.String r0 = "last_bundled_day"
            r13 = 4
            r3[r13] = r0     // Catch:{ SQLiteException -> 0x010d, all -> 0x0109 }
            java.lang.String r0 = "last_sampled_complex_event_id"
            r14 = 5
            r3[r14] = r0     // Catch:{ SQLiteException -> 0x010d, all -> 0x0109 }
            java.lang.String r0 = "last_sampling_rate"
            r8 = 6
            r3[r8] = r0     // Catch:{ SQLiteException -> 0x010d, all -> 0x0109 }
            java.lang.String r0 = "last_exempt_from_sampling"
            r7 = 7
            r3[r7] = r0     // Catch:{ SQLiteException -> 0x010d, all -> 0x0109 }
            java.lang.String r4 = "app_id=? and name=?"
            java.lang.String[] r5 = new java.lang.String[r11]     // Catch:{ SQLiteException -> 0x010d, all -> 0x0109 }
            r5[r9] = r23     // Catch:{ SQLiteException -> 0x010d, all -> 0x0109 }
            r5[r10] = r15     // Catch:{ SQLiteException -> 0x010d, all -> 0x0109 }
            r6 = 0
            r0 = 0
            r17 = 0
            r7 = r0
            r0 = 6
            r8 = r17
            android.database.Cursor r8 = r1.query(r2, r3, r4, r5, r6, r7, r8)     // Catch:{ SQLiteException -> 0x010d, all -> 0x0109 }
            boolean r1 = r8.moveToFirst()     // Catch:{ SQLiteException -> 0x0105, all -> 0x0101 }
            if (r1 != 0) goto L_0x0062
            if (r8 == 0) goto L_0x0061
            r8.close()
        L_0x0061:
            return r16
        L_0x0062:
            long r4 = r8.getLong(r9)     // Catch:{ SQLiteException -> 0x0105, all -> 0x0101 }
            long r6 = r8.getLong(r10)     // Catch:{ SQLiteException -> 0x0105, all -> 0x0101 }
            long r19 = r8.getLong(r11)     // Catch:{ SQLiteException -> 0x0105, all -> 0x0101 }
            boolean r1 = r8.isNull(r12)     // Catch:{ SQLiteException -> 0x0105, all -> 0x0101 }
            if (r1 == 0) goto L_0x0077
            r1 = 0
            goto L_0x007b
        L_0x0077:
            long r1 = r8.getLong(r12)     // Catch:{ SQLiteException -> 0x0105, all -> 0x0101 }
        L_0x007b:
            r11 = r1
            boolean r1 = r8.isNull(r13)     // Catch:{ SQLiteException -> 0x0105, all -> 0x0101 }
            if (r1 == 0) goto L_0x0085
            r13 = r16
            goto L_0x008e
        L_0x0085:
            long r1 = r8.getLong(r13)     // Catch:{ SQLiteException -> 0x0105, all -> 0x0101 }
            java.lang.Long r1 = java.lang.Long.valueOf(r1)     // Catch:{ SQLiteException -> 0x0105, all -> 0x0101 }
            r13 = r1
        L_0x008e:
            boolean r1 = r8.isNull(r14)     // Catch:{ SQLiteException -> 0x0105, all -> 0x0101 }
            if (r1 == 0) goto L_0x0097
            r14 = r16
            goto L_0x00a0
        L_0x0097:
            long r1 = r8.getLong(r14)     // Catch:{ SQLiteException -> 0x0105, all -> 0x0101 }
            java.lang.Long r1 = java.lang.Long.valueOf(r1)     // Catch:{ SQLiteException -> 0x0105, all -> 0x0101 }
            r14 = r1
        L_0x00a0:
            boolean r1 = r8.isNull(r0)     // Catch:{ SQLiteException -> 0x0105, all -> 0x0101 }
            if (r1 == 0) goto L_0x00aa
            r0 = r16
        L_0x00a8:
            r1 = 7
            goto L_0x00b3
        L_0x00aa:
            long r0 = r8.getLong(r0)     // Catch:{ SQLiteException -> 0x0105, all -> 0x0101 }
            java.lang.Long r0 = java.lang.Long.valueOf(r0)     // Catch:{ SQLiteException -> 0x0105, all -> 0x0101 }
            goto L_0x00a8
        L_0x00b3:
            boolean r2 = r8.isNull(r1)     // Catch:{ SQLiteException -> 0x0105, all -> 0x0101 }
            if (r2 != 0) goto L_0x00cb
            long r1 = r8.getLong(r1)     // Catch:{ SQLiteException -> 0x0105, all -> 0x0101 }
            r17 = 1
            int r3 = (r1 > r17 ? 1 : (r1 == r17 ? 0 : -1))
            if (r3 != 0) goto L_0x00c4
            r9 = 1
        L_0x00c4:
            java.lang.Boolean r1 = java.lang.Boolean.valueOf(r9)     // Catch:{ SQLiteException -> 0x0105, all -> 0x0101 }
            r17 = r1
            goto L_0x00cd
        L_0x00cb:
            r17 = r16
        L_0x00cd:
            com.google.android.gms.measurement.internal.zzac r18 = new com.google.android.gms.measurement.internal.zzac     // Catch:{ SQLiteException -> 0x0105, all -> 0x0101 }
            r1 = r18
            r2 = r23
            r3 = r24
            r21 = r8
            r8 = r19
            r10 = r11
            r12 = r13
            r13 = r14
            r14 = r0
            r15 = r17
            r1.<init>(r2, r3, r4, r6, r8, r10, r12, r13, r14, r15)     // Catch:{ SQLiteException -> 0x00ff }
            boolean r0 = r21.moveToNext()     // Catch:{ SQLiteException -> 0x00ff }
            if (r0 == 0) goto L_0x00f9
            com.google.android.gms.measurement.internal.zzas r0 = r22.zzgt()     // Catch:{ SQLiteException -> 0x00ff }
            com.google.android.gms.measurement.internal.zzau r0 = r0.zzjg()     // Catch:{ SQLiteException -> 0x00ff }
            java.lang.String r1 = "Got multiple records for event aggregates, expected one. appId"
            java.lang.Object r2 = com.google.android.gms.measurement.internal.zzas.zzbw(r23)     // Catch:{ SQLiteException -> 0x00ff }
            r0.zzg(r1, r2)     // Catch:{ SQLiteException -> 0x00ff }
        L_0x00f9:
            if (r21 == 0) goto L_0x00fe
            r21.close()
        L_0x00fe:
            return r18
        L_0x00ff:
            r0 = move-exception
            goto L_0x0110
        L_0x0101:
            r0 = move-exception
            r21 = r8
            goto L_0x0132
        L_0x0105:
            r0 = move-exception
            r21 = r8
            goto L_0x0110
        L_0x0109:
            r0 = move-exception
            r21 = r16
            goto L_0x0132
        L_0x010d:
            r0 = move-exception
            r21 = r16
        L_0x0110:
            com.google.android.gms.measurement.internal.zzas r1 = r22.zzgt()     // Catch:{ all -> 0x0131 }
            com.google.android.gms.measurement.internal.zzau r1 = r1.zzjg()     // Catch:{ all -> 0x0131 }
            java.lang.String r2 = "Error querying events. appId"
            java.lang.Object r3 = com.google.android.gms.measurement.internal.zzas.zzbw(r23)     // Catch:{ all -> 0x0131 }
            com.google.android.gms.measurement.internal.zzaq r4 = r22.zzgq()     // Catch:{ all -> 0x0131 }
            r5 = r24
            java.lang.String r4 = r4.zzbt(r5)     // Catch:{ all -> 0x0131 }
            r1.zzd(r2, r3, r4, r0)     // Catch:{ all -> 0x0131 }
            if (r21 == 0) goto L_0x0130
            r21.close()
        L_0x0130:
            return r16
        L_0x0131:
            r0 = move-exception
        L_0x0132:
            if (r21 == 0) goto L_0x0137
            r21.close()
        L_0x0137:
            goto L_0x0139
        L_0x0138:
            throw r0
        L_0x0139:
            goto L_0x0138
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.measurement.internal.zzt.zzg(java.lang.String, java.lang.String):com.google.android.gms.measurement.internal.zzac");
    }

    public final void zza(zzac zzac) {
        Preconditions.checkNotNull(zzac);
        zzaf();
        zzcl();
        ContentValues contentValues = new ContentValues();
        contentValues.put("app_id", zzac.zztt);
        contentValues.put(PostalAddressParser.USER_ADDRESS_NAME_KEY, zzac.name);
        contentValues.put("lifetime_count", Long.valueOf(zzac.zzahv));
        contentValues.put("current_bundle_count", Long.valueOf(zzac.zzahw));
        contentValues.put("last_fire_timestamp", Long.valueOf(zzac.zzahx));
        contentValues.put("last_bundled_timestamp", Long.valueOf(zzac.zzahy));
        contentValues.put("last_bundled_day", zzac.zzahz);
        contentValues.put("last_sampled_complex_event_id", zzac.zzaia);
        contentValues.put("last_sampling_rate", zzac.zzaib);
        contentValues.put("last_exempt_from_sampling", (zzac.zzaic == null || !zzac.zzaic.booleanValue()) ? null : Long.valueOf(1));
        try {
            if (getWritableDatabase().insertWithOnConflict("events", null, contentValues, 5) == -1) {
                zzgt().zzjg().zzg("Failed to insert/update event aggregates (got -1). appId", zzas.zzbw(zzac.zztt));
            }
        } catch (SQLiteException e) {
            zzgt().zzjg().zze("Error storing event aggregates. appId", zzas.zzbw(zzac.zztt), e);
        }
    }

    public final void zzh(String str, String str2) {
        Preconditions.checkNotEmpty(str);
        Preconditions.checkNotEmpty(str2);
        zzaf();
        zzcl();
        try {
            zzgt().zzjo().zzg("Deleted user attribute rows", Integer.valueOf(getWritableDatabase().delete("user_attributes", "app_id=? and name=?", new String[]{str, str2})));
        } catch (SQLiteException e) {
            zzgt().zzjg().zzd("Error deleting user attribute. appId", zzas.zzbw(str), zzgq().zzbv(str2), e);
        }
    }

    public final boolean zza(zzfw zzfw) {
        Preconditions.checkNotNull(zzfw);
        zzaf();
        zzcl();
        if (zzi(zzfw.zztt, zzfw.name) == null) {
            if (zzfx.zzct(zzfw.name)) {
                if (zza("select count(1) from user_attributes where app_id=? and name not like '!_%' escape '!'", new String[]{zzfw.zztt}) >= 25) {
                    return false;
                }
            } else {
                if (zza("select count(1) from user_attributes where app_id=? and origin=? AND name like '!_%' escape '!'", new String[]{zzfw.zztt, zzfw.origin}) >= 25) {
                    return false;
                }
            }
        }
        ContentValues contentValues = new ContentValues();
        contentValues.put("app_id", zzfw.zztt);
        contentValues.put("origin", zzfw.origin);
        contentValues.put(PostalAddressParser.USER_ADDRESS_NAME_KEY, zzfw.name);
        contentValues.put("set_timestamp", Long.valueOf(zzfw.zzaum));
        zza(contentValues, Param.VALUE, zzfw.value);
        try {
            if (getWritableDatabase().insertWithOnConflict("user_attributes", null, contentValues, 5) == -1) {
                zzgt().zzjg().zzg("Failed to insert/update user property (got -1). appId", zzas.zzbw(zzfw.zztt));
            }
        } catch (SQLiteException e) {
            zzgt().zzjg().zze("Error storing user property. appId", zzas.zzbw(zzfw.zztt), e);
        }
        return true;
    }

    /* JADX WARNING: Removed duplicated region for block: B:31:0x00a7  */
    /* JADX WARNING: Removed duplicated region for block: B:35:0x00ae  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final com.google.android.gms.measurement.internal.zzfw zzi(java.lang.String r19, java.lang.String r20) {
        /*
            r18 = this;
            r8 = r20
            com.google.android.gms.common.internal.Preconditions.checkNotEmpty(r19)
            com.google.android.gms.common.internal.Preconditions.checkNotEmpty(r20)
            r18.zzaf()
            r18.zzcl()
            r9 = 0
            android.database.sqlite.SQLiteDatabase r10 = r18.getWritableDatabase()     // Catch:{ SQLiteException -> 0x0088, all -> 0x0083 }
            java.lang.String r11 = "user_attributes"
            r0 = 3
            java.lang.String[] r12 = new java.lang.String[r0]     // Catch:{ SQLiteException -> 0x0088, all -> 0x0083 }
            java.lang.String r0 = "set_timestamp"
            r1 = 0
            r12[r1] = r0     // Catch:{ SQLiteException -> 0x0088, all -> 0x0083 }
            java.lang.String r0 = "value"
            r2 = 1
            r12[r2] = r0     // Catch:{ SQLiteException -> 0x0088, all -> 0x0083 }
            java.lang.String r0 = "origin"
            r3 = 2
            r12[r3] = r0     // Catch:{ SQLiteException -> 0x0088, all -> 0x0083 }
            java.lang.String r13 = "app_id=? and name=?"
            java.lang.String[] r14 = new java.lang.String[r3]     // Catch:{ SQLiteException -> 0x0088, all -> 0x0083 }
            r14[r1] = r19     // Catch:{ SQLiteException -> 0x0088, all -> 0x0083 }
            r14[r2] = r8     // Catch:{ SQLiteException -> 0x0088, all -> 0x0083 }
            r15 = 0
            r16 = 0
            r17 = 0
            android.database.Cursor r10 = r10.query(r11, r12, r13, r14, r15, r16, r17)     // Catch:{ SQLiteException -> 0x0088, all -> 0x0083 }
            boolean r0 = r10.moveToFirst()     // Catch:{ SQLiteException -> 0x007f, all -> 0x007b }
            if (r0 != 0) goto L_0x0044
            if (r10 == 0) goto L_0x0043
            r10.close()
        L_0x0043:
            return r9
        L_0x0044:
            long r5 = r10.getLong(r1)     // Catch:{ SQLiteException -> 0x007f, all -> 0x007b }
            r11 = r18
            java.lang.Object r7 = r11.zza(r10, r2)     // Catch:{ SQLiteException -> 0x0079 }
            java.lang.String r3 = r10.getString(r3)     // Catch:{ SQLiteException -> 0x0079 }
            com.google.android.gms.measurement.internal.zzfw r0 = new com.google.android.gms.measurement.internal.zzfw     // Catch:{ SQLiteException -> 0x0079 }
            r1 = r0
            r2 = r19
            r4 = r20
            r1.<init>(r2, r3, r4, r5, r7)     // Catch:{ SQLiteException -> 0x0079 }
            boolean r1 = r10.moveToNext()     // Catch:{ SQLiteException -> 0x0079 }
            if (r1 == 0) goto L_0x0073
            com.google.android.gms.measurement.internal.zzas r1 = r18.zzgt()     // Catch:{ SQLiteException -> 0x0079 }
            com.google.android.gms.measurement.internal.zzau r1 = r1.zzjg()     // Catch:{ SQLiteException -> 0x0079 }
            java.lang.String r2 = "Got multiple records for user property, expected one. appId"
            java.lang.Object r3 = com.google.android.gms.measurement.internal.zzas.zzbw(r19)     // Catch:{ SQLiteException -> 0x0079 }
            r1.zzg(r2, r3)     // Catch:{ SQLiteException -> 0x0079 }
        L_0x0073:
            if (r10 == 0) goto L_0x0078
            r10.close()
        L_0x0078:
            return r0
        L_0x0079:
            r0 = move-exception
            goto L_0x008c
        L_0x007b:
            r0 = move-exception
            r11 = r18
            goto L_0x00ac
        L_0x007f:
            r0 = move-exception
            r11 = r18
            goto L_0x008c
        L_0x0083:
            r0 = move-exception
            r11 = r18
            r10 = r9
            goto L_0x00ac
        L_0x0088:
            r0 = move-exception
            r11 = r18
            r10 = r9
        L_0x008c:
            com.google.android.gms.measurement.internal.zzas r1 = r18.zzgt()     // Catch:{ all -> 0x00ab }
            com.google.android.gms.measurement.internal.zzau r1 = r1.zzjg()     // Catch:{ all -> 0x00ab }
            java.lang.String r2 = "Error querying user property. appId"
            java.lang.Object r3 = com.google.android.gms.measurement.internal.zzas.zzbw(r19)     // Catch:{ all -> 0x00ab }
            com.google.android.gms.measurement.internal.zzaq r4 = r18.zzgq()     // Catch:{ all -> 0x00ab }
            java.lang.String r4 = r4.zzbv(r8)     // Catch:{ all -> 0x00ab }
            r1.zzd(r2, r3, r4, r0)     // Catch:{ all -> 0x00ab }
            if (r10 == 0) goto L_0x00aa
            r10.close()
        L_0x00aa:
            return r9
        L_0x00ab:
            r0 = move-exception
        L_0x00ac:
            if (r10 == 0) goto L_0x00b1
            r10.close()
        L_0x00b1:
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.measurement.internal.zzt.zzi(java.lang.String, java.lang.String):com.google.android.gms.measurement.internal.zzfw");
    }

    /* JADX WARNING: Removed duplicated region for block: B:37:0x00b1  */
    /* JADX WARNING: Removed duplicated region for block: B:41:0x00b8  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final java.util.List<com.google.android.gms.measurement.internal.zzfw> zzbl(java.lang.String r23) {
        /*
            r22 = this;
            com.google.android.gms.common.internal.Preconditions.checkNotEmpty(r23)
            r22.zzaf()
            r22.zzcl()
            java.util.ArrayList r0 = new java.util.ArrayList
            r0.<init>()
            r1 = 0
            android.database.sqlite.SQLiteDatabase r2 = r22.getWritableDatabase()     // Catch:{ SQLiteException -> 0x009a, all -> 0x0095 }
            java.lang.String r3 = "user_attributes"
            r4 = 4
            java.lang.String[] r4 = new java.lang.String[r4]     // Catch:{ SQLiteException -> 0x009a, all -> 0x0095 }
            java.lang.String r5 = "name"
            r11 = 0
            r4[r11] = r5     // Catch:{ SQLiteException -> 0x009a, all -> 0x0095 }
            java.lang.String r5 = "origin"
            r12 = 1
            r4[r12] = r5     // Catch:{ SQLiteException -> 0x009a, all -> 0x0095 }
            java.lang.String r5 = "set_timestamp"
            r13 = 2
            r4[r13] = r5     // Catch:{ SQLiteException -> 0x009a, all -> 0x0095 }
            java.lang.String r5 = "value"
            r14 = 3
            r4[r14] = r5     // Catch:{ SQLiteException -> 0x009a, all -> 0x0095 }
            java.lang.String r5 = "app_id=?"
            java.lang.String[] r6 = new java.lang.String[r12]     // Catch:{ SQLiteException -> 0x009a, all -> 0x0095 }
            r6[r11] = r23     // Catch:{ SQLiteException -> 0x009a, all -> 0x0095 }
            r7 = 0
            r8 = 0
            java.lang.String r9 = "rowid"
            java.lang.String r10 = "1000"
            android.database.Cursor r2 = r2.query(r3, r4, r5, r6, r7, r8, r9, r10)     // Catch:{ SQLiteException -> 0x009a, all -> 0x0095 }
            boolean r3 = r2.moveToFirst()     // Catch:{ SQLiteException -> 0x0091, all -> 0x008d }
            if (r3 != 0) goto L_0x0048
            if (r2 == 0) goto L_0x0047
            r2.close()
        L_0x0047:
            return r0
        L_0x0048:
            java.lang.String r18 = r2.getString(r11)     // Catch:{ SQLiteException -> 0x0091, all -> 0x008d }
            java.lang.String r3 = r2.getString(r12)     // Catch:{ SQLiteException -> 0x0091, all -> 0x008d }
            if (r3 != 0) goto L_0x0054
            java.lang.String r3 = ""
        L_0x0054:
            r17 = r3
            long r19 = r2.getLong(r13)     // Catch:{ SQLiteException -> 0x0091, all -> 0x008d }
            r3 = r22
            java.lang.Object r21 = r3.zza(r2, r14)     // Catch:{ SQLiteException -> 0x008b }
            if (r21 != 0) goto L_0x0074
            com.google.android.gms.measurement.internal.zzas r4 = r22.zzgt()     // Catch:{ SQLiteException -> 0x008b }
            com.google.android.gms.measurement.internal.zzau r4 = r4.zzjg()     // Catch:{ SQLiteException -> 0x008b }
            java.lang.String r5 = "Read invalid user property value, ignoring it. appId"
            java.lang.Object r6 = com.google.android.gms.measurement.internal.zzas.zzbw(r23)     // Catch:{ SQLiteException -> 0x008b }
            r4.zzg(r5, r6)     // Catch:{ SQLiteException -> 0x008b }
            goto L_0x007f
        L_0x0074:
            com.google.android.gms.measurement.internal.zzfw r4 = new com.google.android.gms.measurement.internal.zzfw     // Catch:{ SQLiteException -> 0x008b }
            r15 = r4
            r16 = r23
            r15.<init>(r16, r17, r18, r19, r21)     // Catch:{ SQLiteException -> 0x008b }
            r0.add(r4)     // Catch:{ SQLiteException -> 0x008b }
        L_0x007f:
            boolean r4 = r2.moveToNext()     // Catch:{ SQLiteException -> 0x008b }
            if (r4 != 0) goto L_0x0048
            if (r2 == 0) goto L_0x008a
            r2.close()
        L_0x008a:
            return r0
        L_0x008b:
            r0 = move-exception
            goto L_0x009e
        L_0x008d:
            r0 = move-exception
            r3 = r22
            goto L_0x00b6
        L_0x0091:
            r0 = move-exception
            r3 = r22
            goto L_0x009e
        L_0x0095:
            r0 = move-exception
            r3 = r22
            r2 = r1
            goto L_0x00b6
        L_0x009a:
            r0 = move-exception
            r3 = r22
            r2 = r1
        L_0x009e:
            com.google.android.gms.measurement.internal.zzas r4 = r22.zzgt()     // Catch:{ all -> 0x00b5 }
            com.google.android.gms.measurement.internal.zzau r4 = r4.zzjg()     // Catch:{ all -> 0x00b5 }
            java.lang.String r5 = "Error querying user properties. appId"
            java.lang.Object r6 = com.google.android.gms.measurement.internal.zzas.zzbw(r23)     // Catch:{ all -> 0x00b5 }
            r4.zze(r5, r6, r0)     // Catch:{ all -> 0x00b5 }
            if (r2 == 0) goto L_0x00b4
            r2.close()
        L_0x00b4:
            return r1
        L_0x00b5:
            r0 = move-exception
        L_0x00b6:
            if (r2 == 0) goto L_0x00bb
            r2.close()
        L_0x00bb:
            goto L_0x00bd
        L_0x00bc:
            throw r0
        L_0x00bd:
            goto L_0x00bc
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.measurement.internal.zzt.zzbl(java.lang.String):java.util.List");
    }

    /* JADX WARNING: Code restructure failed: missing block: B:55:0x011a, code lost:
        r0 = e;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:56:0x011b, code lost:
        r14 = r21;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:59:0x0122, code lost:
        r0 = th;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:60:0x0123, code lost:
        r14 = r21;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:61:0x0126, code lost:
        r0 = e;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:62:0x0127, code lost:
        r14 = r21;
        r11 = r22;
     */
    /* JADX WARNING: Failed to process nested try/catch */
    /* JADX WARNING: Removed duplicated region for block: B:59:0x0122 A[ExcHandler: all (th java.lang.Throwable), Splitter:B:1:0x000f] */
    /* JADX WARNING: Removed duplicated region for block: B:68:0x0141  */
    /* JADX WARNING: Removed duplicated region for block: B:73:0x0149  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final java.util.List<com.google.android.gms.measurement.internal.zzfw> zzb(java.lang.String r22, java.lang.String r23, java.lang.String r24) {
        /*
            r21 = this;
            com.google.android.gms.common.internal.Preconditions.checkNotEmpty(r22)
            r21.zzaf()
            r21.zzcl()
            java.util.ArrayList r0 = new java.util.ArrayList
            r0.<init>()
            r1 = 0
            java.util.ArrayList r2 = new java.util.ArrayList     // Catch:{ SQLiteException -> 0x0126, all -> 0x0122 }
            r3 = 3
            r2.<init>(r3)     // Catch:{ SQLiteException -> 0x0126, all -> 0x0122 }
            r11 = r22
            r2.add(r11)     // Catch:{ SQLiteException -> 0x011e, all -> 0x0122 }
            java.lang.StringBuilder r4 = new java.lang.StringBuilder     // Catch:{ SQLiteException -> 0x011e, all -> 0x0122 }
            java.lang.String r5 = "app_id=?"
            r4.<init>(r5)     // Catch:{ SQLiteException -> 0x011e, all -> 0x0122 }
            boolean r5 = android.text.TextUtils.isEmpty(r23)     // Catch:{ SQLiteException -> 0x011e, all -> 0x0122 }
            if (r5 != 0) goto L_0x0032
            r5 = r23
            r2.add(r5)     // Catch:{ SQLiteException -> 0x011a, all -> 0x0122 }
            java.lang.String r6 = " and origin=?"
            r4.append(r6)     // Catch:{ SQLiteException -> 0x011a, all -> 0x0122 }
            goto L_0x0034
        L_0x0032:
            r5 = r23
        L_0x0034:
            boolean r6 = android.text.TextUtils.isEmpty(r24)     // Catch:{ SQLiteException -> 0x011a, all -> 0x0122 }
            if (r6 != 0) goto L_0x004c
            java.lang.String r6 = java.lang.String.valueOf(r24)     // Catch:{ SQLiteException -> 0x011a, all -> 0x0122 }
            java.lang.String r7 = "*"
            java.lang.String r6 = r6.concat(r7)     // Catch:{ SQLiteException -> 0x011a, all -> 0x0122 }
            r2.add(r6)     // Catch:{ SQLiteException -> 0x011a, all -> 0x0122 }
            java.lang.String r6 = " and name glob ?"
            r4.append(r6)     // Catch:{ SQLiteException -> 0x011a, all -> 0x0122 }
        L_0x004c:
            int r6 = r2.size()     // Catch:{ SQLiteException -> 0x011a, all -> 0x0122 }
            java.lang.String[] r6 = new java.lang.String[r6]     // Catch:{ SQLiteException -> 0x011a, all -> 0x0122 }
            java.lang.Object[] r2 = r2.toArray(r6)     // Catch:{ SQLiteException -> 0x011a, all -> 0x0122 }
            r16 = r2
            java.lang.String[] r16 = (java.lang.String[]) r16     // Catch:{ SQLiteException -> 0x011a, all -> 0x0122 }
            android.database.sqlite.SQLiteDatabase r12 = r21.getWritableDatabase()     // Catch:{ SQLiteException -> 0x011a, all -> 0x0122 }
            java.lang.String r13 = "user_attributes"
            r2 = 4
            java.lang.String[] r14 = new java.lang.String[r2]     // Catch:{ SQLiteException -> 0x011a, all -> 0x0122 }
            java.lang.String r2 = "name"
            r10 = 0
            r14[r10] = r2     // Catch:{ SQLiteException -> 0x011a, all -> 0x0122 }
            java.lang.String r2 = "set_timestamp"
            r8 = 1
            r14[r8] = r2     // Catch:{ SQLiteException -> 0x011a, all -> 0x0122 }
            java.lang.String r2 = "value"
            r9 = 2
            r14[r9] = r2     // Catch:{ SQLiteException -> 0x011a, all -> 0x0122 }
            java.lang.String r2 = "origin"
            r14[r3] = r2     // Catch:{ SQLiteException -> 0x011a, all -> 0x0122 }
            java.lang.String r15 = r4.toString()     // Catch:{ SQLiteException -> 0x011a, all -> 0x0122 }
            r17 = 0
            r18 = 0
            java.lang.String r19 = "rowid"
            java.lang.String r20 = "1001"
            android.database.Cursor r2 = r12.query(r13, r14, r15, r16, r17, r18, r19, r20)     // Catch:{ SQLiteException -> 0x011a, all -> 0x0122 }
            boolean r4 = r2.moveToFirst()     // Catch:{ SQLiteException -> 0x0116, all -> 0x0112 }
            if (r4 != 0) goto L_0x0092
            if (r2 == 0) goto L_0x0091
            r2.close()
        L_0x0091:
            return r0
        L_0x0092:
            int r4 = r0.size()     // Catch:{ SQLiteException -> 0x0116, all -> 0x0112 }
            r6 = 1000(0x3e8, float:1.401E-42)
            if (r4 < r6) goto L_0x00ae
            com.google.android.gms.measurement.internal.zzas r3 = r21.zzgt()     // Catch:{ SQLiteException -> 0x0116, all -> 0x0112 }
            com.google.android.gms.measurement.internal.zzau r3 = r3.zzjg()     // Catch:{ SQLiteException -> 0x0116, all -> 0x0112 }
            java.lang.String r4 = "Read more than the max allowed user properties, ignoring excess"
            java.lang.Integer r6 = java.lang.Integer.valueOf(r6)     // Catch:{ SQLiteException -> 0x0116, all -> 0x0112 }
            r3.zzg(r4, r6)     // Catch:{ SQLiteException -> 0x0116, all -> 0x0112 }
            r14 = r21
            goto L_0x00fb
        L_0x00ae:
            java.lang.String r7 = r2.getString(r10)     // Catch:{ SQLiteException -> 0x0116, all -> 0x0112 }
            long r12 = r2.getLong(r8)     // Catch:{ SQLiteException -> 0x0116, all -> 0x0112 }
            r14 = r21
            java.lang.Object r15 = r14.zza(r2, r9)     // Catch:{ SQLiteException -> 0x0110 }
            java.lang.String r6 = r2.getString(r3)     // Catch:{ SQLiteException -> 0x0110 }
            if (r15 != 0) goto L_0x00e0
            com.google.android.gms.measurement.internal.zzas r4 = r21.zzgt()     // Catch:{ SQLiteException -> 0x00dd }
            com.google.android.gms.measurement.internal.zzau r4 = r4.zzjg()     // Catch:{ SQLiteException -> 0x00dd }
            java.lang.String r5 = "(2)Read invalid user property value, ignoring it"
            java.lang.Object r7 = com.google.android.gms.measurement.internal.zzas.zzbw(r22)     // Catch:{ SQLiteException -> 0x00dd }
            r12 = r24
            r4.zzd(r5, r7, r6, r12)     // Catch:{ SQLiteException -> 0x00dd }
            r17 = r6
            r12 = 0
            r18 = 2
            r19 = 1
            goto L_0x00f5
        L_0x00dd:
            r0 = move-exception
            r5 = r6
            goto L_0x012e
        L_0x00e0:
            com.google.android.gms.measurement.internal.zzfw r5 = new com.google.android.gms.measurement.internal.zzfw     // Catch:{ SQLiteException -> 0x010a }
            r4 = r5
            r3 = r5
            r5 = r22
            r17 = r6
            r18 = 2
            r19 = 1
            r8 = r12
            r12 = 0
            r10 = r15
            r4.<init>(r5, r6, r7, r8, r10)     // Catch:{ SQLiteException -> 0x0108 }
            r0.add(r3)     // Catch:{ SQLiteException -> 0x0108 }
        L_0x00f5:
            boolean r3 = r2.moveToNext()     // Catch:{ SQLiteException -> 0x0108 }
            if (r3 != 0) goto L_0x0101
        L_0x00fb:
            if (r2 == 0) goto L_0x0100
            r2.close()
        L_0x0100:
            return r0
        L_0x0101:
            r5 = r17
            r3 = 3
            r8 = 1
            r9 = 2
            r10 = 0
            goto L_0x0092
        L_0x0108:
            r0 = move-exception
            goto L_0x010d
        L_0x010a:
            r0 = move-exception
            r17 = r6
        L_0x010d:
            r5 = r17
            goto L_0x012e
        L_0x0110:
            r0 = move-exception
            goto L_0x012e
        L_0x0112:
            r0 = move-exception
            r14 = r21
            goto L_0x0146
        L_0x0116:
            r0 = move-exception
            r14 = r21
            goto L_0x012e
        L_0x011a:
            r0 = move-exception
            r14 = r21
            goto L_0x012d
        L_0x011e:
            r0 = move-exception
            r14 = r21
            goto L_0x012b
        L_0x0122:
            r0 = move-exception
            r14 = r21
            goto L_0x0147
        L_0x0126:
            r0 = move-exception
            r14 = r21
            r11 = r22
        L_0x012b:
            r5 = r23
        L_0x012d:
            r2 = r1
        L_0x012e:
            com.google.android.gms.measurement.internal.zzas r3 = r21.zzgt()     // Catch:{ all -> 0x0145 }
            com.google.android.gms.measurement.internal.zzau r3 = r3.zzjg()     // Catch:{ all -> 0x0145 }
            java.lang.String r4 = "(2)Error querying user properties"
            java.lang.Object r6 = com.google.android.gms.measurement.internal.zzas.zzbw(r22)     // Catch:{ all -> 0x0145 }
            r3.zzd(r4, r6, r5, r0)     // Catch:{ all -> 0x0145 }
            if (r2 == 0) goto L_0x0144
            r2.close()
        L_0x0144:
            return r1
        L_0x0145:
            r0 = move-exception
        L_0x0146:
            r1 = r2
        L_0x0147:
            if (r1 == 0) goto L_0x014c
            r1.close()
        L_0x014c:
            goto L_0x014e
        L_0x014d:
            throw r0
        L_0x014e:
            goto L_0x014d
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.measurement.internal.zzt.zzb(java.lang.String, java.lang.String, java.lang.String):java.util.List");
    }

    public final boolean zza(zzo zzo) {
        Preconditions.checkNotNull(zzo);
        zzaf();
        zzcl();
        if (zzi(zzo.packageName, zzo.zzags.name) == null) {
            if (zza("SELECT COUNT(1) FROM conditional_properties WHERE app_id=?", new String[]{zzo.packageName}) >= 1000) {
                return false;
            }
        }
        ContentValues contentValues = new ContentValues();
        contentValues.put("app_id", zzo.packageName);
        contentValues.put("origin", zzo.origin);
        contentValues.put(PostalAddressParser.USER_ADDRESS_NAME_KEY, zzo.zzags.name);
        zza(contentValues, Param.VALUE, zzo.zzags.getValue());
        contentValues.put("active", Boolean.valueOf(zzo.active));
        contentValues.put("trigger_event_name", zzo.triggerEventName);
        contentValues.put("trigger_timeout", Long.valueOf(zzo.triggerTimeout));
        zzgr();
        contentValues.put("timed_out_event", zzfx.zza((Parcelable) zzo.zzagt));
        contentValues.put("creation_timestamp", Long.valueOf(zzo.creationTimestamp));
        zzgr();
        contentValues.put("triggered_event", zzfx.zza((Parcelable) zzo.zzagu));
        contentValues.put("triggered_timestamp", Long.valueOf(zzo.zzags.zzaum));
        contentValues.put("time_to_live", Long.valueOf(zzo.timeToLive));
        zzgr();
        contentValues.put("expired_event", zzfx.zza((Parcelable) zzo.zzagv));
        try {
            if (getWritableDatabase().insertWithOnConflict("conditional_properties", null, contentValues, 5) == -1) {
                zzgt().zzjg().zzg("Failed to insert/update conditional user property (got -1)", zzas.zzbw(zzo.packageName));
            }
        } catch (SQLiteException e) {
            zzgt().zzjg().zze("Error storing conditional user property", zzas.zzbw(zzo.packageName), e);
        }
        return true;
    }

    /* JADX WARNING: Removed duplicated region for block: B:35:0x014e  */
    /* JADX WARNING: Removed duplicated region for block: B:39:0x0155  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final com.google.android.gms.measurement.internal.zzo zzj(java.lang.String r33, java.lang.String r34) {
        /*
            r32 = this;
            r7 = r34
            com.google.android.gms.common.internal.Preconditions.checkNotEmpty(r33)
            com.google.android.gms.common.internal.Preconditions.checkNotEmpty(r34)
            r32.zzaf()
            r32.zzcl()
            r8 = 0
            android.database.sqlite.SQLiteDatabase r9 = r32.getWritableDatabase()     // Catch:{ SQLiteException -> 0x012f, all -> 0x012a }
            java.lang.String r10 = "conditional_properties"
            r0 = 11
            java.lang.String[] r11 = new java.lang.String[r0]     // Catch:{ SQLiteException -> 0x012f, all -> 0x012a }
            java.lang.String r0 = "origin"
            r1 = 0
            r11[r1] = r0     // Catch:{ SQLiteException -> 0x012f, all -> 0x012a }
            java.lang.String r0 = "value"
            r2 = 1
            r11[r2] = r0     // Catch:{ SQLiteException -> 0x012f, all -> 0x012a }
            java.lang.String r0 = "active"
            r3 = 2
            r11[r3] = r0     // Catch:{ SQLiteException -> 0x012f, all -> 0x012a }
            java.lang.String r0 = "trigger_event_name"
            r4 = 3
            r11[r4] = r0     // Catch:{ SQLiteException -> 0x012f, all -> 0x012a }
            java.lang.String r0 = "trigger_timeout"
            r5 = 4
            r11[r5] = r0     // Catch:{ SQLiteException -> 0x012f, all -> 0x012a }
            java.lang.String r0 = "timed_out_event"
            r6 = 5
            r11[r6] = r0     // Catch:{ SQLiteException -> 0x012f, all -> 0x012a }
            java.lang.String r0 = "creation_timestamp"
            r15 = 6
            r11[r15] = r0     // Catch:{ SQLiteException -> 0x012f, all -> 0x012a }
            java.lang.String r0 = "triggered_event"
            r14 = 7
            r11[r14] = r0     // Catch:{ SQLiteException -> 0x012f, all -> 0x012a }
            java.lang.String r0 = "triggered_timestamp"
            r13 = 8
            r11[r13] = r0     // Catch:{ SQLiteException -> 0x012f, all -> 0x012a }
            java.lang.String r0 = "time_to_live"
            r12 = 9
            r11[r12] = r0     // Catch:{ SQLiteException -> 0x012f, all -> 0x012a }
            java.lang.String r0 = "expired_event"
            r6 = 10
            r11[r6] = r0     // Catch:{ SQLiteException -> 0x012f, all -> 0x012a }
            java.lang.String r0 = "app_id=? and name=?"
            java.lang.String[] r13 = new java.lang.String[r3]     // Catch:{ SQLiteException -> 0x012f, all -> 0x012a }
            r13[r1] = r33     // Catch:{ SQLiteException -> 0x012f, all -> 0x012a }
            r13[r2] = r7     // Catch:{ SQLiteException -> 0x012f, all -> 0x012a }
            r17 = 0
            r18 = 0
            r19 = 0
            r6 = 9
            r12 = r0
            r0 = 8
            r6 = 7
            r14 = r17
            r0 = 6
            r15 = r18
            r16 = r19
            android.database.Cursor r9 = r9.query(r10, r11, r12, r13, r14, r15, r16)     // Catch:{ SQLiteException -> 0x012f, all -> 0x012a }
            boolean r10 = r9.moveToFirst()     // Catch:{ SQLiteException -> 0x0126, all -> 0x0122 }
            if (r10 != 0) goto L_0x007e
            if (r9 == 0) goto L_0x007d
            r9.close()
        L_0x007d:
            return r8
        L_0x007e:
            java.lang.String r19 = r9.getString(r1)     // Catch:{ SQLiteException -> 0x0126, all -> 0x0122 }
            r10 = r32
            java.lang.Object r11 = r10.zza(r9, r2)     // Catch:{ SQLiteException -> 0x0120 }
            int r3 = r9.getInt(r3)     // Catch:{ SQLiteException -> 0x0120 }
            if (r3 == 0) goto L_0x0091
            r23 = 1
            goto L_0x0093
        L_0x0091:
            r23 = 0
        L_0x0093:
            java.lang.String r24 = r9.getString(r4)     // Catch:{ SQLiteException -> 0x0120 }
            long r26 = r9.getLong(r5)     // Catch:{ SQLiteException -> 0x0120 }
            com.google.android.gms.measurement.internal.zzft r1 = r32.zzjr()     // Catch:{ SQLiteException -> 0x0120 }
            r2 = 5
            byte[] r2 = r9.getBlob(r2)     // Catch:{ SQLiteException -> 0x0120 }
            android.os.Parcelable$Creator<com.google.android.gms.measurement.internal.zzag> r3 = com.google.android.gms.measurement.internal.zzag.CREATOR     // Catch:{ SQLiteException -> 0x0120 }
            android.os.Parcelable r1 = r1.zza(r2, r3)     // Catch:{ SQLiteException -> 0x0120 }
            r25 = r1
            com.google.android.gms.measurement.internal.zzag r25 = (com.google.android.gms.measurement.internal.zzag) r25     // Catch:{ SQLiteException -> 0x0120 }
            long r12 = r9.getLong(r0)     // Catch:{ SQLiteException -> 0x0120 }
            com.google.android.gms.measurement.internal.zzft r0 = r32.zzjr()     // Catch:{ SQLiteException -> 0x0120 }
            byte[] r1 = r9.getBlob(r6)     // Catch:{ SQLiteException -> 0x0120 }
            android.os.Parcelable$Creator<com.google.android.gms.measurement.internal.zzag> r2 = com.google.android.gms.measurement.internal.zzag.CREATOR     // Catch:{ SQLiteException -> 0x0120 }
            android.os.Parcelable r0 = r0.zza(r1, r2)     // Catch:{ SQLiteException -> 0x0120 }
            r28 = r0
            com.google.android.gms.measurement.internal.zzag r28 = (com.google.android.gms.measurement.internal.zzag) r28     // Catch:{ SQLiteException -> 0x0120 }
            r0 = 8
            long r3 = r9.getLong(r0)     // Catch:{ SQLiteException -> 0x0120 }
            r0 = 9
            long r29 = r9.getLong(r0)     // Catch:{ SQLiteException -> 0x0120 }
            com.google.android.gms.measurement.internal.zzft r0 = r32.zzjr()     // Catch:{ SQLiteException -> 0x0120 }
            r1 = 10
            byte[] r1 = r9.getBlob(r1)     // Catch:{ SQLiteException -> 0x0120 }
            android.os.Parcelable$Creator<com.google.android.gms.measurement.internal.zzag> r2 = com.google.android.gms.measurement.internal.zzag.CREATOR     // Catch:{ SQLiteException -> 0x0120 }
            android.os.Parcelable r0 = r0.zza(r1, r2)     // Catch:{ SQLiteException -> 0x0120 }
            r31 = r0
            com.google.android.gms.measurement.internal.zzag r31 = (com.google.android.gms.measurement.internal.zzag) r31     // Catch:{ SQLiteException -> 0x0120 }
            com.google.android.gms.measurement.internal.zzfu r20 = new com.google.android.gms.measurement.internal.zzfu     // Catch:{ SQLiteException -> 0x0120 }
            r1 = r20
            r2 = r34
            r5 = r11
            r6 = r19
            r1.<init>(r2, r3, r5, r6)     // Catch:{ SQLiteException -> 0x0120 }
            com.google.android.gms.measurement.internal.zzo r0 = new com.google.android.gms.measurement.internal.zzo     // Catch:{ SQLiteException -> 0x0120 }
            r17 = r0
            r18 = r33
            r21 = r12
            r17.<init>(r18, r19, r20, r21, r23, r24, r25, r26, r28, r29, r31)     // Catch:{ SQLiteException -> 0x0120 }
            boolean r1 = r9.moveToNext()     // Catch:{ SQLiteException -> 0x0120 }
            if (r1 == 0) goto L_0x011a
            com.google.android.gms.measurement.internal.zzas r1 = r32.zzgt()     // Catch:{ SQLiteException -> 0x0120 }
            com.google.android.gms.measurement.internal.zzau r1 = r1.zzjg()     // Catch:{ SQLiteException -> 0x0120 }
            java.lang.String r2 = "Got multiple records for conditional property, expected one"
            java.lang.Object r3 = com.google.android.gms.measurement.internal.zzas.zzbw(r33)     // Catch:{ SQLiteException -> 0x0120 }
            com.google.android.gms.measurement.internal.zzaq r4 = r32.zzgq()     // Catch:{ SQLiteException -> 0x0120 }
            java.lang.String r4 = r4.zzbv(r7)     // Catch:{ SQLiteException -> 0x0120 }
            r1.zze(r2, r3, r4)     // Catch:{ SQLiteException -> 0x0120 }
        L_0x011a:
            if (r9 == 0) goto L_0x011f
            r9.close()
        L_0x011f:
            return r0
        L_0x0120:
            r0 = move-exception
            goto L_0x0133
        L_0x0122:
            r0 = move-exception
            r10 = r32
            goto L_0x0153
        L_0x0126:
            r0 = move-exception
            r10 = r32
            goto L_0x0133
        L_0x012a:
            r0 = move-exception
            r10 = r32
            r9 = r8
            goto L_0x0153
        L_0x012f:
            r0 = move-exception
            r10 = r32
            r9 = r8
        L_0x0133:
            com.google.android.gms.measurement.internal.zzas r1 = r32.zzgt()     // Catch:{ all -> 0x0152 }
            com.google.android.gms.measurement.internal.zzau r1 = r1.zzjg()     // Catch:{ all -> 0x0152 }
            java.lang.String r2 = "Error querying conditional property"
            java.lang.Object r3 = com.google.android.gms.measurement.internal.zzas.zzbw(r33)     // Catch:{ all -> 0x0152 }
            com.google.android.gms.measurement.internal.zzaq r4 = r32.zzgq()     // Catch:{ all -> 0x0152 }
            java.lang.String r4 = r4.zzbv(r7)     // Catch:{ all -> 0x0152 }
            r1.zzd(r2, r3, r4, r0)     // Catch:{ all -> 0x0152 }
            if (r9 == 0) goto L_0x0151
            r9.close()
        L_0x0151:
            return r8
        L_0x0152:
            r0 = move-exception
        L_0x0153:
            if (r9 == 0) goto L_0x0158
            r9.close()
        L_0x0158:
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.measurement.internal.zzt.zzj(java.lang.String, java.lang.String):com.google.android.gms.measurement.internal.zzo");
    }

    public final int zzk(String str, String str2) {
        Preconditions.checkNotEmpty(str);
        Preconditions.checkNotEmpty(str2);
        zzaf();
        zzcl();
        try {
            return getWritableDatabase().delete("conditional_properties", "app_id=? and name=?", new String[]{str, str2});
        } catch (SQLiteException e) {
            zzgt().zzjg().zzd("Error deleting conditional property", zzas.zzbw(str), zzgq().zzbv(str2), e);
            return 0;
        }
    }

    public final List<zzo> zzc(String str, String str2, String str3) {
        Preconditions.checkNotEmpty(str);
        zzaf();
        zzcl();
        ArrayList arrayList = new ArrayList(3);
        arrayList.add(str);
        StringBuilder sb = new StringBuilder("app_id=?");
        if (!TextUtils.isEmpty(str2)) {
            arrayList.add(str2);
            sb.append(" and origin=?");
        }
        if (!TextUtils.isEmpty(str3)) {
            arrayList.add(String.valueOf(str3).concat(Marker.ANY_MARKER));
            sb.append(" and name glob ?");
        }
        return zzb(sb.toString(), (String[]) arrayList.toArray(new String[arrayList.size()]));
    }

    /* JADX WARNING: Removed duplicated region for block: B:33:0x016d  */
    /* JADX WARNING: Removed duplicated region for block: B:38:0x0175  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final java.util.List<com.google.android.gms.measurement.internal.zzo> zzb(java.lang.String r40, java.lang.String[] r41) {
        /*
            r39 = this;
            r39.zzaf()
            r39.zzcl()
            java.util.ArrayList r0 = new java.util.ArrayList
            r0.<init>()
            android.database.sqlite.SQLiteDatabase r2 = r39.getWritableDatabase()     // Catch:{ SQLiteException -> 0x0158, all -> 0x0155 }
            java.lang.String r3 = "conditional_properties"
            r4 = 13
            java.lang.String[] r4 = new java.lang.String[r4]     // Catch:{ SQLiteException -> 0x0158, all -> 0x0155 }
            java.lang.String r5 = "app_id"
            r11 = 0
            r4[r11] = r5     // Catch:{ SQLiteException -> 0x0158, all -> 0x0155 }
            java.lang.String r5 = "origin"
            r12 = 1
            r4[r12] = r5     // Catch:{ SQLiteException -> 0x0158, all -> 0x0155 }
            java.lang.String r5 = "name"
            r13 = 2
            r4[r13] = r5     // Catch:{ SQLiteException -> 0x0158, all -> 0x0155 }
            java.lang.String r5 = "value"
            r14 = 3
            r4[r14] = r5     // Catch:{ SQLiteException -> 0x0158, all -> 0x0155 }
            java.lang.String r5 = "active"
            r15 = 4
            r4[r15] = r5     // Catch:{ SQLiteException -> 0x0158, all -> 0x0155 }
            java.lang.String r5 = "trigger_event_name"
            r10 = 5
            r4[r10] = r5     // Catch:{ SQLiteException -> 0x0158, all -> 0x0155 }
            java.lang.String r5 = "trigger_timeout"
            r9 = 6
            r4[r9] = r5     // Catch:{ SQLiteException -> 0x0158, all -> 0x0155 }
            java.lang.String r5 = "timed_out_event"
            r8 = 7
            r4[r8] = r5     // Catch:{ SQLiteException -> 0x0158, all -> 0x0155 }
            java.lang.String r5 = "creation_timestamp"
            r7 = 8
            r4[r7] = r5     // Catch:{ SQLiteException -> 0x0158, all -> 0x0155 }
            java.lang.String r5 = "triggered_event"
            r6 = 9
            r4[r6] = r5     // Catch:{ SQLiteException -> 0x0158, all -> 0x0155 }
            java.lang.String r5 = "triggered_timestamp"
            r1 = 10
            r4[r1] = r5     // Catch:{ SQLiteException -> 0x0158, all -> 0x0155 }
            java.lang.String r5 = "time_to_live"
            r1 = 11
            r4[r1] = r5     // Catch:{ SQLiteException -> 0x0158, all -> 0x0155 }
            java.lang.String r5 = "expired_event"
            r1 = 12
            r4[r1] = r5     // Catch:{ SQLiteException -> 0x0158, all -> 0x0155 }
            r19 = 0
            r20 = 0
            java.lang.String r21 = "rowid"
            java.lang.String r22 = "1001"
            r5 = r40
            r1 = 9
            r6 = r41
            r1 = 8
            r7 = r19
            r1 = 7
            r8 = r20
            r1 = 6
            r9 = r21
            r1 = 5
            r10 = r22
            android.database.Cursor r2 = r2.query(r3, r4, r5, r6, r7, r8, r9, r10)     // Catch:{ SQLiteException -> 0x0158, all -> 0x0155 }
            boolean r3 = r2.moveToFirst()     // Catch:{ SQLiteException -> 0x0152, all -> 0x0150 }
            if (r3 != 0) goto L_0x0086
            if (r2 == 0) goto L_0x0085
            r2.close()
        L_0x0085:
            return r0
        L_0x0086:
            int r3 = r0.size()     // Catch:{ SQLiteException -> 0x0152, all -> 0x0150 }
            r4 = 1000(0x3e8, float:1.401E-42)
            if (r3 < r4) goto L_0x00a1
            com.google.android.gms.measurement.internal.zzas r1 = r39.zzgt()     // Catch:{ SQLiteException -> 0x0152, all -> 0x0150 }
            com.google.android.gms.measurement.internal.zzau r1 = r1.zzjg()     // Catch:{ SQLiteException -> 0x0152, all -> 0x0150 }
            java.lang.String r3 = "Read more than the max allowed conditional properties, ignoring extra"
            java.lang.Integer r4 = java.lang.Integer.valueOf(r4)     // Catch:{ SQLiteException -> 0x0152, all -> 0x0150 }
            r1.zzg(r3, r4)     // Catch:{ SQLiteException -> 0x0152, all -> 0x0150 }
            goto L_0x0146
        L_0x00a1:
            java.lang.String r3 = r2.getString(r11)     // Catch:{ SQLiteException -> 0x0152, all -> 0x0150 }
            java.lang.String r10 = r2.getString(r12)     // Catch:{ SQLiteException -> 0x0152, all -> 0x0150 }
            java.lang.String r5 = r2.getString(r13)     // Catch:{ SQLiteException -> 0x0152, all -> 0x0150 }
            r9 = r39
            java.lang.Object r8 = r9.zza(r2, r14)     // Catch:{ SQLiteException -> 0x0152, all -> 0x0150 }
            int r4 = r2.getInt(r15)     // Catch:{ SQLiteException -> 0x0152, all -> 0x0150 }
            if (r4 == 0) goto L_0x00bc
            r22 = 1
            goto L_0x00be
        L_0x00bc:
            r22 = 0
        L_0x00be:
            java.lang.String r24 = r2.getString(r1)     // Catch:{ SQLiteException -> 0x0152, all -> 0x0150 }
            r6 = 6
            long r25 = r2.getLong(r6)     // Catch:{ SQLiteException -> 0x0152, all -> 0x0150 }
            com.google.android.gms.measurement.internal.zzft r4 = r39.zzjr()     // Catch:{ SQLiteException -> 0x0152, all -> 0x0150 }
            r7 = 7
            byte[] r1 = r2.getBlob(r7)     // Catch:{ SQLiteException -> 0x0152, all -> 0x0150 }
            android.os.Parcelable$Creator<com.google.android.gms.measurement.internal.zzag> r6 = com.google.android.gms.measurement.internal.zzag.CREATOR     // Catch:{ SQLiteException -> 0x0152, all -> 0x0150 }
            android.os.Parcelable r1 = r4.zza(r1, r6)     // Catch:{ SQLiteException -> 0x0152, all -> 0x0150 }
            com.google.android.gms.measurement.internal.zzag r1 = (com.google.android.gms.measurement.internal.zzag) r1     // Catch:{ SQLiteException -> 0x0152, all -> 0x0150 }
            r6 = 8
            long r27 = r2.getLong(r6)     // Catch:{ SQLiteException -> 0x0152, all -> 0x0150 }
            com.google.android.gms.measurement.internal.zzft r4 = r39.zzjr()     // Catch:{ SQLiteException -> 0x0152, all -> 0x0150 }
            r11 = 9
            byte[] r6 = r2.getBlob(r11)     // Catch:{ SQLiteException -> 0x0152, all -> 0x0150 }
            android.os.Parcelable$Creator<com.google.android.gms.measurement.internal.zzag> r7 = com.google.android.gms.measurement.internal.zzag.CREATOR     // Catch:{ SQLiteException -> 0x0152, all -> 0x0150 }
            android.os.Parcelable r4 = r4.zza(r6, r7)     // Catch:{ SQLiteException -> 0x0152, all -> 0x0150 }
            r29 = r4
            com.google.android.gms.measurement.internal.zzag r29 = (com.google.android.gms.measurement.internal.zzag) r29     // Catch:{ SQLiteException -> 0x0152, all -> 0x0150 }
            r6 = 10
            long r16 = r2.getLong(r6)     // Catch:{ SQLiteException -> 0x0152, all -> 0x0150 }
            r7 = 11
            long r31 = r2.getLong(r7)     // Catch:{ SQLiteException -> 0x0152, all -> 0x0150 }
            com.google.android.gms.measurement.internal.zzft r4 = r39.zzjr()     // Catch:{ SQLiteException -> 0x0152, all -> 0x0150 }
            r11 = 12
            byte[] r6 = r2.getBlob(r11)     // Catch:{ SQLiteException -> 0x0152, all -> 0x0150 }
            android.os.Parcelable$Creator<com.google.android.gms.measurement.internal.zzag> r7 = com.google.android.gms.measurement.internal.zzag.CREATOR     // Catch:{ SQLiteException -> 0x0152, all -> 0x0150 }
            android.os.Parcelable r4 = r4.zza(r6, r7)     // Catch:{ SQLiteException -> 0x0152, all -> 0x0150 }
            r30 = r4
            com.google.android.gms.measurement.internal.zzag r30 = (com.google.android.gms.measurement.internal.zzag) r30     // Catch:{ SQLiteException -> 0x0152, all -> 0x0150 }
            com.google.android.gms.measurement.internal.zzfu r33 = new com.google.android.gms.measurement.internal.zzfu     // Catch:{ SQLiteException -> 0x0152, all -> 0x0150 }
            r4 = r33
            r34 = 11
            r35 = 10
            r36 = 8
            r37 = 7
            r38 = 6
            r6 = r16
            r9 = r10
            r4.<init>(r5, r6, r8, r9)     // Catch:{ SQLiteException -> 0x0152, all -> 0x0150 }
            com.google.android.gms.measurement.internal.zzo r4 = new com.google.android.gms.measurement.internal.zzo     // Catch:{ SQLiteException -> 0x0152, all -> 0x0150 }
            r16 = r4
            r17 = r3
            r18 = r10
            r19 = r33
            r20 = r27
            r23 = r24
            r24 = r1
            r27 = r29
            r28 = r31
            r16.<init>(r17, r18, r19, r20, r22, r23, r24, r25, r27, r28, r30)     // Catch:{ SQLiteException -> 0x0152, all -> 0x0150 }
            r0.add(r4)     // Catch:{ SQLiteException -> 0x0152, all -> 0x0150 }
            boolean r1 = r2.moveToNext()     // Catch:{ SQLiteException -> 0x0152, all -> 0x0150 }
            if (r1 != 0) goto L_0x014c
        L_0x0146:
            if (r2 == 0) goto L_0x014b
            r2.close()
        L_0x014b:
            return r0
        L_0x014c:
            r1 = 5
            r11 = 0
            goto L_0x0086
        L_0x0150:
            r0 = move-exception
            goto L_0x0173
        L_0x0152:
            r0 = move-exception
            r1 = r2
            goto L_0x015a
        L_0x0155:
            r0 = move-exception
            r2 = 0
            goto L_0x0173
        L_0x0158:
            r0 = move-exception
            r1 = 0
        L_0x015a:
            com.google.android.gms.measurement.internal.zzas r2 = r39.zzgt()     // Catch:{ all -> 0x0171 }
            com.google.android.gms.measurement.internal.zzau r2 = r2.zzjg()     // Catch:{ all -> 0x0171 }
            java.lang.String r3 = "Error querying conditional user property value"
            r2.zzg(r3, r0)     // Catch:{ all -> 0x0171 }
            java.util.List r0 = java.util.Collections.emptyList()     // Catch:{ all -> 0x0171 }
            if (r1 == 0) goto L_0x0170
            r1.close()
        L_0x0170:
            return r0
        L_0x0171:
            r0 = move-exception
            r2 = r1
        L_0x0173:
            if (r2 == 0) goto L_0x0178
            r2.close()
        L_0x0178:
            goto L_0x017a
        L_0x0179:
            throw r0
        L_0x017a:
            goto L_0x0179
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.measurement.internal.zzt.zzb(java.lang.String, java.lang.String[]):java.util.List");
    }

    /* JADX WARNING: Removed duplicated region for block: B:22:0x0180 A[Catch:{ SQLiteException -> 0x0212 }] */
    /* JADX WARNING: Removed duplicated region for block: B:23:0x0184 A[Catch:{ SQLiteException -> 0x0212 }] */
    /* JADX WARNING: Removed duplicated region for block: B:26:0x01b8 A[Catch:{ SQLiteException -> 0x0212 }] */
    /* JADX WARNING: Removed duplicated region for block: B:27:0x01bb A[Catch:{ SQLiteException -> 0x0212 }] */
    /* JADX WARNING: Removed duplicated region for block: B:30:0x01ca A[Catch:{ SQLiteException -> 0x0212 }] */
    /* JADX WARNING: Removed duplicated region for block: B:42:0x01fb A[Catch:{ SQLiteException -> 0x0212 }] */
    /* JADX WARNING: Removed duplicated region for block: B:44:0x020e  */
    /* JADX WARNING: Removed duplicated region for block: B:58:0x0238  */
    /* JADX WARNING: Removed duplicated region for block: B:62:0x023f  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final com.google.android.gms.measurement.internal.zzg zzbm(java.lang.String r23) {
        /*
            r22 = this;
            r1 = r23
            com.google.android.gms.common.internal.Preconditions.checkNotEmpty(r23)
            r22.zzaf()
            r22.zzcl()
            r2 = 0
            android.database.sqlite.SQLiteDatabase r3 = r22.getWritableDatabase()     // Catch:{ SQLiteException -> 0x0221, all -> 0x021c }
            java.lang.String r4 = "apps"
            r0 = 26
            java.lang.String[] r5 = new java.lang.String[r0]     // Catch:{ SQLiteException -> 0x0221, all -> 0x021c }
            java.lang.String r0 = "app_instance_id"
            r11 = 0
            r5[r11] = r0     // Catch:{ SQLiteException -> 0x0221, all -> 0x021c }
            java.lang.String r0 = "gmp_app_id"
            r12 = 1
            r5[r12] = r0     // Catch:{ SQLiteException -> 0x0221, all -> 0x021c }
            java.lang.String r0 = "resettable_device_id_hash"
            r13 = 2
            r5[r13] = r0     // Catch:{ SQLiteException -> 0x0221, all -> 0x021c }
            java.lang.String r0 = "last_bundle_index"
            r14 = 3
            r5[r14] = r0     // Catch:{ SQLiteException -> 0x0221, all -> 0x021c }
            java.lang.String r0 = "last_bundle_start_timestamp"
            r15 = 4
            r5[r15] = r0     // Catch:{ SQLiteException -> 0x0221, all -> 0x021c }
            java.lang.String r0 = "last_bundle_end_timestamp"
            r10 = 5
            r5[r10] = r0     // Catch:{ SQLiteException -> 0x0221, all -> 0x021c }
            java.lang.String r0 = "app_version"
            r9 = 6
            r5[r9] = r0     // Catch:{ SQLiteException -> 0x0221, all -> 0x021c }
            java.lang.String r0 = "app_store"
            r8 = 7
            r5[r8] = r0     // Catch:{ SQLiteException -> 0x0221, all -> 0x021c }
            java.lang.String r0 = "gmp_version"
            r7 = 8
            r5[r7] = r0     // Catch:{ SQLiteException -> 0x0221, all -> 0x021c }
            r0 = 9
            java.lang.String r6 = "dev_cert_hash"
            r5[r0] = r6     // Catch:{ SQLiteException -> 0x0221, all -> 0x021c }
            java.lang.String r0 = "measurement_enabled"
            r6 = 10
            r5[r6] = r0     // Catch:{ SQLiteException -> 0x0221, all -> 0x021c }
            r0 = 11
            java.lang.String r16 = "day"
            r5[r0] = r16     // Catch:{ SQLiteException -> 0x0221, all -> 0x021c }
            r0 = 12
            java.lang.String r16 = "daily_public_events_count"
            r5[r0] = r16     // Catch:{ SQLiteException -> 0x0221, all -> 0x021c }
            r0 = 13
            java.lang.String r16 = "daily_events_count"
            r5[r0] = r16     // Catch:{ SQLiteException -> 0x0221, all -> 0x021c }
            r0 = 14
            java.lang.String r16 = "daily_conversions_count"
            r5[r0] = r16     // Catch:{ SQLiteException -> 0x0221, all -> 0x021c }
            r0 = 15
            java.lang.String r16 = "config_fetched_time"
            r5[r0] = r16     // Catch:{ SQLiteException -> 0x0221, all -> 0x021c }
            r0 = 16
            java.lang.String r16 = "failed_config_fetch_time"
            r5[r0] = r16     // Catch:{ SQLiteException -> 0x0221, all -> 0x021c }
            java.lang.String r0 = "app_version_int"
            r15 = 17
            r5[r15] = r0     // Catch:{ SQLiteException -> 0x0221, all -> 0x021c }
            r0 = 18
            java.lang.String r17 = "firebase_instance_id"
            r5[r0] = r17     // Catch:{ SQLiteException -> 0x0221, all -> 0x021c }
            r0 = 19
            java.lang.String r17 = "daily_error_events_count"
            r5[r0] = r17     // Catch:{ SQLiteException -> 0x0221, all -> 0x021c }
            r0 = 20
            java.lang.String r17 = "daily_realtime_events_count"
            r5[r0] = r17     // Catch:{ SQLiteException -> 0x0221, all -> 0x021c }
            r0 = 21
            java.lang.String r17 = "health_monitor_sample"
            r5[r0] = r17     // Catch:{ SQLiteException -> 0x0221, all -> 0x021c }
            java.lang.String r0 = "android_id"
            r15 = 22
            r5[r15] = r0     // Catch:{ SQLiteException -> 0x0221, all -> 0x021c }
            java.lang.String r0 = "adid_reporting_enabled"
            r15 = 23
            r5[r15] = r0     // Catch:{ SQLiteException -> 0x0221, all -> 0x021c }
            java.lang.String r0 = "ssaid_reporting_enabled"
            r15 = 24
            r5[r15] = r0     // Catch:{ SQLiteException -> 0x0221, all -> 0x021c }
            r0 = 25
            java.lang.String r18 = "admob_app_id"
            r5[r0] = r18     // Catch:{ SQLiteException -> 0x0221, all -> 0x021c }
            java.lang.String r0 = "app_id=?"
            java.lang.String[] r7 = new java.lang.String[r12]     // Catch:{ SQLiteException -> 0x0221, all -> 0x021c }
            r7[r11] = r1     // Catch:{ SQLiteException -> 0x0221, all -> 0x021c }
            r19 = 0
            r20 = 0
            r21 = 0
            r15 = 10
            r6 = r0
            r0 = 8
            r15 = 7
            r8 = r19
            r0 = 6
            r9 = r20
            r15 = 5
            r10 = r21
            android.database.Cursor r3 = r3.query(r4, r5, r6, r7, r8, r9, r10)     // Catch:{ SQLiteException -> 0x0221, all -> 0x021c }
            boolean r4 = r3.moveToFirst()     // Catch:{ SQLiteException -> 0x0218, all -> 0x0214 }
            if (r4 != 0) goto L_0x00d4
            if (r3 == 0) goto L_0x00d3
            r3.close()
        L_0x00d3:
            return r2
        L_0x00d4:
            com.google.android.gms.measurement.internal.zzg r4 = new com.google.android.gms.measurement.internal.zzg     // Catch:{ SQLiteException -> 0x0218, all -> 0x0214 }
            r5 = r22
            com.google.android.gms.measurement.internal.zzfn r6 = r5.zzamx     // Catch:{ SQLiteException -> 0x0212 }
            com.google.android.gms.measurement.internal.zzbw r6 = r6.zzmh()     // Catch:{ SQLiteException -> 0x0212 }
            r4.<init>(r6, r1)     // Catch:{ SQLiteException -> 0x0212 }
            java.lang.String r6 = r3.getString(r11)     // Catch:{ SQLiteException -> 0x0212 }
            r4.zzaj(r6)     // Catch:{ SQLiteException -> 0x0212 }
            java.lang.String r6 = r3.getString(r12)     // Catch:{ SQLiteException -> 0x0212 }
            r4.zzak(r6)     // Catch:{ SQLiteException -> 0x0212 }
            java.lang.String r6 = r3.getString(r13)     // Catch:{ SQLiteException -> 0x0212 }
            r4.zzam(r6)     // Catch:{ SQLiteException -> 0x0212 }
            long r6 = r3.getLong(r14)     // Catch:{ SQLiteException -> 0x0212 }
            r4.zzt(r6)     // Catch:{ SQLiteException -> 0x0212 }
            r6 = 4
            long r6 = r3.getLong(r6)     // Catch:{ SQLiteException -> 0x0212 }
            r4.zzo(r6)     // Catch:{ SQLiteException -> 0x0212 }
            long r6 = r3.getLong(r15)     // Catch:{ SQLiteException -> 0x0212 }
            r4.zzp(r6)     // Catch:{ SQLiteException -> 0x0212 }
            java.lang.String r0 = r3.getString(r0)     // Catch:{ SQLiteException -> 0x0212 }
            r4.setAppVersion(r0)     // Catch:{ SQLiteException -> 0x0212 }
            r0 = 7
            java.lang.String r0 = r3.getString(r0)     // Catch:{ SQLiteException -> 0x0212 }
            r4.zzao(r0)     // Catch:{ SQLiteException -> 0x0212 }
            r0 = 8
            long r6 = r3.getLong(r0)     // Catch:{ SQLiteException -> 0x0212 }
            r4.zzr(r6)     // Catch:{ SQLiteException -> 0x0212 }
            r0 = 9
            long r6 = r3.getLong(r0)     // Catch:{ SQLiteException -> 0x0212 }
            r4.zzs(r6)     // Catch:{ SQLiteException -> 0x0212 }
            r0 = 10
            boolean r6 = r3.isNull(r0)     // Catch:{ SQLiteException -> 0x0212 }
            if (r6 != 0) goto L_0x013e
            int r0 = r3.getInt(r0)     // Catch:{ SQLiteException -> 0x0212 }
            if (r0 == 0) goto L_0x013c
            goto L_0x013e
        L_0x013c:
            r0 = 0
            goto L_0x013f
        L_0x013e:
            r0 = 1
        L_0x013f:
            r4.setMeasurementEnabled(r0)     // Catch:{ SQLiteException -> 0x0212 }
            r0 = 11
            long r6 = r3.getLong(r0)     // Catch:{ SQLiteException -> 0x0212 }
            r4.zzw(r6)     // Catch:{ SQLiteException -> 0x0212 }
            r0 = 12
            long r6 = r3.getLong(r0)     // Catch:{ SQLiteException -> 0x0212 }
            r4.zzx(r6)     // Catch:{ SQLiteException -> 0x0212 }
            r0 = 13
            long r6 = r3.getLong(r0)     // Catch:{ SQLiteException -> 0x0212 }
            r4.zzy(r6)     // Catch:{ SQLiteException -> 0x0212 }
            r0 = 14
            long r6 = r3.getLong(r0)     // Catch:{ SQLiteException -> 0x0212 }
            r4.zzz(r6)     // Catch:{ SQLiteException -> 0x0212 }
            r0 = 15
            long r6 = r3.getLong(r0)     // Catch:{ SQLiteException -> 0x0212 }
            r4.zzu(r6)     // Catch:{ SQLiteException -> 0x0212 }
            r0 = 16
            long r6 = r3.getLong(r0)     // Catch:{ SQLiteException -> 0x0212 }
            r4.zzv(r6)     // Catch:{ SQLiteException -> 0x0212 }
            r0 = 17
            boolean r6 = r3.isNull(r0)     // Catch:{ SQLiteException -> 0x0212 }
            if (r6 == 0) goto L_0x0184
            r6 = -2147483648(0xffffffff80000000, double:NaN)
            goto L_0x0189
        L_0x0184:
            int r0 = r3.getInt(r0)     // Catch:{ SQLiteException -> 0x0212 }
            long r6 = (long) r0     // Catch:{ SQLiteException -> 0x0212 }
        L_0x0189:
            r4.zzq(r6)     // Catch:{ SQLiteException -> 0x0212 }
            r0 = 18
            java.lang.String r0 = r3.getString(r0)     // Catch:{ SQLiteException -> 0x0212 }
            r4.zzan(r0)     // Catch:{ SQLiteException -> 0x0212 }
            r0 = 19
            long r6 = r3.getLong(r0)     // Catch:{ SQLiteException -> 0x0212 }
            r4.zzab(r6)     // Catch:{ SQLiteException -> 0x0212 }
            r0 = 20
            long r6 = r3.getLong(r0)     // Catch:{ SQLiteException -> 0x0212 }
            r4.zzaa(r6)     // Catch:{ SQLiteException -> 0x0212 }
            r0 = 21
            java.lang.String r0 = r3.getString(r0)     // Catch:{ SQLiteException -> 0x0212 }
            r4.zzap(r0)     // Catch:{ SQLiteException -> 0x0212 }
            r0 = 22
            boolean r6 = r3.isNull(r0)     // Catch:{ SQLiteException -> 0x0212 }
            if (r6 == 0) goto L_0x01bb
            r6 = 0
            goto L_0x01bf
        L_0x01bb:
            long r6 = r3.getLong(r0)     // Catch:{ SQLiteException -> 0x0212 }
        L_0x01bf:
            r4.zzac(r6)     // Catch:{ SQLiteException -> 0x0212 }
            r0 = 23
            boolean r6 = r3.isNull(r0)     // Catch:{ SQLiteException -> 0x0212 }
            if (r6 != 0) goto L_0x01d3
            int r0 = r3.getInt(r0)     // Catch:{ SQLiteException -> 0x0212 }
            if (r0 == 0) goto L_0x01d1
            goto L_0x01d3
        L_0x01d1:
            r0 = 0
            goto L_0x01d4
        L_0x01d3:
            r0 = 1
        L_0x01d4:
            r4.zze(r0)     // Catch:{ SQLiteException -> 0x0212 }
            r0 = 24
            boolean r6 = r3.isNull(r0)     // Catch:{ SQLiteException -> 0x0212 }
            if (r6 != 0) goto L_0x01e5
            int r0 = r3.getInt(r0)     // Catch:{ SQLiteException -> 0x0212 }
            if (r0 == 0) goto L_0x01e6
        L_0x01e5:
            r11 = 1
        L_0x01e6:
            r4.zzf(r11)     // Catch:{ SQLiteException -> 0x0212 }
            r0 = 25
            java.lang.String r0 = r3.getString(r0)     // Catch:{ SQLiteException -> 0x0212 }
            r4.zzal(r0)     // Catch:{ SQLiteException -> 0x0212 }
            r4.zzha()     // Catch:{ SQLiteException -> 0x0212 }
            boolean r0 = r3.moveToNext()     // Catch:{ SQLiteException -> 0x0212 }
            if (r0 == 0) goto L_0x020c
            com.google.android.gms.measurement.internal.zzas r0 = r22.zzgt()     // Catch:{ SQLiteException -> 0x0212 }
            com.google.android.gms.measurement.internal.zzau r0 = r0.zzjg()     // Catch:{ SQLiteException -> 0x0212 }
            java.lang.String r6 = "Got multiple records for app, expected one. appId"
            java.lang.Object r7 = com.google.android.gms.measurement.internal.zzas.zzbw(r23)     // Catch:{ SQLiteException -> 0x0212 }
            r0.zzg(r6, r7)     // Catch:{ SQLiteException -> 0x0212 }
        L_0x020c:
            if (r3 == 0) goto L_0x0211
            r3.close()
        L_0x0211:
            return r4
        L_0x0212:
            r0 = move-exception
            goto L_0x0225
        L_0x0214:
            r0 = move-exception
            r5 = r22
            goto L_0x023d
        L_0x0218:
            r0 = move-exception
            r5 = r22
            goto L_0x0225
        L_0x021c:
            r0 = move-exception
            r5 = r22
            r3 = r2
            goto L_0x023d
        L_0x0221:
            r0 = move-exception
            r5 = r22
            r3 = r2
        L_0x0225:
            com.google.android.gms.measurement.internal.zzas r4 = r22.zzgt()     // Catch:{ all -> 0x023c }
            com.google.android.gms.measurement.internal.zzau r4 = r4.zzjg()     // Catch:{ all -> 0x023c }
            java.lang.String r6 = "Error querying app. appId"
            java.lang.Object r1 = com.google.android.gms.measurement.internal.zzas.zzbw(r23)     // Catch:{ all -> 0x023c }
            r4.zze(r6, r1, r0)     // Catch:{ all -> 0x023c }
            if (r3 == 0) goto L_0x023b
            r3.close()
        L_0x023b:
            return r2
        L_0x023c:
            r0 = move-exception
        L_0x023d:
            if (r3 == 0) goto L_0x0242
            r3.close()
        L_0x0242:
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.measurement.internal.zzt.zzbm(java.lang.String):com.google.android.gms.measurement.internal.zzg");
    }

    public final void zza(zzg zzg) {
        String str = "apps";
        Preconditions.checkNotNull(zzg);
        zzaf();
        zzcl();
        ContentValues contentValues = new ContentValues();
        contentValues.put("app_id", zzg.zzal());
        contentValues.put("app_instance_id", zzg.getAppInstanceId());
        contentValues.put("gmp_app_id", zzg.getGmpAppId());
        contentValues.put("resettable_device_id_hash", zzg.zzhc());
        contentValues.put("last_bundle_index", Long.valueOf(zzg.zzhj()));
        contentValues.put("last_bundle_start_timestamp", Long.valueOf(zzg.zzhd()));
        contentValues.put("last_bundle_end_timestamp", Long.valueOf(zzg.zzhe()));
        contentValues.put("app_version", zzg.zzak());
        contentValues.put("app_store", zzg.zzhg());
        contentValues.put("gmp_version", Long.valueOf(zzg.zzhh()));
        contentValues.put("dev_cert_hash", Long.valueOf(zzg.zzhi()));
        contentValues.put("measurement_enabled", Boolean.valueOf(zzg.isMeasurementEnabled()));
        contentValues.put("day", Long.valueOf(zzg.zzhn()));
        contentValues.put("daily_public_events_count", Long.valueOf(zzg.zzho()));
        contentValues.put("daily_events_count", Long.valueOf(zzg.zzhp()));
        contentValues.put("daily_conversions_count", Long.valueOf(zzg.zzhq()));
        contentValues.put("config_fetched_time", Long.valueOf(zzg.zzhk()));
        contentValues.put("failed_config_fetch_time", Long.valueOf(zzg.zzhl()));
        contentValues.put("app_version_int", Long.valueOf(zzg.zzhf()));
        contentValues.put("firebase_instance_id", zzg.getFirebaseInstanceId());
        contentValues.put("daily_error_events_count", Long.valueOf(zzg.zzhs()));
        contentValues.put("daily_realtime_events_count", Long.valueOf(zzg.zzhr()));
        contentValues.put("health_monitor_sample", zzg.zzht());
        contentValues.put(C5985b.f4233f, Long.valueOf(zzg.zzhv()));
        contentValues.put("adid_reporting_enabled", Boolean.valueOf(zzg.zzhw()));
        contentValues.put("ssaid_reporting_enabled", Boolean.valueOf(zzg.zzhx()));
        contentValues.put("admob_app_id", zzg.zzhb());
        try {
            SQLiteDatabase writableDatabase = getWritableDatabase();
            if (((long) writableDatabase.update(str, contentValues, "app_id = ?", new String[]{zzg.zzal()})) == 0 && writableDatabase.insertWithOnConflict(str, null, contentValues, 5) == -1) {
                zzgt().zzjg().zzg("Failed to insert/update app (got -1). appId", zzas.zzbw(zzg.zzal()));
            }
        } catch (SQLiteException e) {
            zzgt().zzjg().zze("Error storing app. appId", zzas.zzbw(zzg.zzal()), e);
        }
    }

    public final long zzbn(String str) {
        Preconditions.checkNotEmpty(str);
        zzaf();
        zzcl();
        try {
            return (long) getWritableDatabase().delete("raw_events", "rowid in (select rowid from raw_events where app_id=? order by rowid desc limit -1 offset ?)", new String[]{str, String.valueOf(Math.max(0, Math.min(1000000, zzgv().zzb(str, zzai.zzajj))))});
        } catch (SQLiteException e) {
            zzgt().zzjg().zze("Error deleting over the limit events. appId", zzas.zzbw(str), e);
            return 0;
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:37:0x0123  */
    /* JADX WARNING: Removed duplicated region for block: B:41:0x012a  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final com.google.android.gms.measurement.internal.zzu zza(long r22, java.lang.String r24, boolean r25, boolean r26, boolean r27, boolean r28, boolean r29) {
        /*
            r21 = this;
            java.lang.String r0 = "daily_realtime_events_count"
            java.lang.String r1 = "daily_error_events_count"
            java.lang.String r2 = "daily_conversions_count"
            java.lang.String r3 = "daily_public_events_count"
            java.lang.String r4 = "daily_events_count"
            java.lang.String r5 = "day"
            com.google.android.gms.common.internal.Preconditions.checkNotEmpty(r24)
            r21.zzaf()
            r21.zzcl()
            r6 = 1
            java.lang.String[] r7 = new java.lang.String[r6]
            r8 = 0
            r7[r8] = r24
            com.google.android.gms.measurement.internal.zzu r9 = new com.google.android.gms.measurement.internal.zzu
            r9.<init>()
            android.database.sqlite.SQLiteDatabase r15 = r21.getWritableDatabase()     // Catch:{ SQLiteException -> 0x010e, all -> 0x010b }
            java.lang.String r12 = "apps"
            r11 = 6
            java.lang.String[] r13 = new java.lang.String[r11]     // Catch:{ SQLiteException -> 0x010e, all -> 0x010b }
            r13[r8] = r5     // Catch:{ SQLiteException -> 0x010e, all -> 0x010b }
            r13[r6] = r4     // Catch:{ SQLiteException -> 0x010e, all -> 0x010b }
            r14 = 2
            r13[r14] = r3     // Catch:{ SQLiteException -> 0x010e, all -> 0x010b }
            r11 = 3
            r13[r11] = r2     // Catch:{ SQLiteException -> 0x010e, all -> 0x010b }
            r10 = 4
            r13[r10] = r1     // Catch:{ SQLiteException -> 0x010e, all -> 0x010b }
            r10 = 5
            r13[r10] = r0     // Catch:{ SQLiteException -> 0x010e, all -> 0x010b }
            java.lang.String r16 = "app_id=?"
            java.lang.String[] r10 = new java.lang.String[r6]     // Catch:{ SQLiteException -> 0x010e, all -> 0x010b }
            r10[r8] = r24     // Catch:{ SQLiteException -> 0x010e, all -> 0x010b }
            r17 = 0
            r18 = 0
            r19 = 0
            r11 = r15
            r14 = r16
            r20 = r15
            r15 = r10
            r16 = r17
            r17 = r18
            r18 = r19
            android.database.Cursor r10 = r11.query(r12, r13, r14, r15, r16, r17, r18)     // Catch:{ SQLiteException -> 0x010e, all -> 0x010b }
            boolean r11 = r10.moveToFirst()     // Catch:{ SQLiteException -> 0x0109 }
            if (r11 != 0) goto L_0x0072
            com.google.android.gms.measurement.internal.zzas r0 = r21.zzgt()     // Catch:{ SQLiteException -> 0x0109 }
            com.google.android.gms.measurement.internal.zzau r0 = r0.zzjj()     // Catch:{ SQLiteException -> 0x0109 }
            java.lang.String r1 = "Not updating daily counts, app is not known. appId"
            java.lang.Object r2 = com.google.android.gms.measurement.internal.zzas.zzbw(r24)     // Catch:{ SQLiteException -> 0x0109 }
            r0.zzg(r1, r2)     // Catch:{ SQLiteException -> 0x0109 }
            if (r10 == 0) goto L_0x0071
            r10.close()
        L_0x0071:
            return r9
        L_0x0072:
            long r11 = r10.getLong(r8)     // Catch:{ SQLiteException -> 0x0109 }
            int r8 = (r11 > r22 ? 1 : (r11 == r22 ? 0 : -1))
            if (r8 != 0) goto L_0x009c
            long r11 = r10.getLong(r6)     // Catch:{ SQLiteException -> 0x0109 }
            r9.zzahi = r11     // Catch:{ SQLiteException -> 0x0109 }
            r6 = 2
            long r11 = r10.getLong(r6)     // Catch:{ SQLiteException -> 0x0109 }
            r9.zzahh = r11     // Catch:{ SQLiteException -> 0x0109 }
            r6 = 3
            long r11 = r10.getLong(r6)     // Catch:{ SQLiteException -> 0x0109 }
            r9.zzahj = r11     // Catch:{ SQLiteException -> 0x0109 }
            r6 = 4
            long r11 = r10.getLong(r6)     // Catch:{ SQLiteException -> 0x0109 }
            r9.zzahk = r11     // Catch:{ SQLiteException -> 0x0109 }
            r6 = 5
            long r11 = r10.getLong(r6)     // Catch:{ SQLiteException -> 0x0109 }
            r9.zzahl = r11     // Catch:{ SQLiteException -> 0x0109 }
        L_0x009c:
            r11 = 1
            if (r25 == 0) goto L_0x00a5
            long r13 = r9.zzahi     // Catch:{ SQLiteException -> 0x0109 }
            long r13 = r13 + r11
            r9.zzahi = r13     // Catch:{ SQLiteException -> 0x0109 }
        L_0x00a5:
            if (r26 == 0) goto L_0x00ac
            long r13 = r9.zzahh     // Catch:{ SQLiteException -> 0x0109 }
            long r13 = r13 + r11
            r9.zzahh = r13     // Catch:{ SQLiteException -> 0x0109 }
        L_0x00ac:
            if (r27 == 0) goto L_0x00b3
            long r13 = r9.zzahj     // Catch:{ SQLiteException -> 0x0109 }
            long r13 = r13 + r11
            r9.zzahj = r13     // Catch:{ SQLiteException -> 0x0109 }
        L_0x00b3:
            if (r28 == 0) goto L_0x00ba
            long r13 = r9.zzahk     // Catch:{ SQLiteException -> 0x0109 }
            long r13 = r13 + r11
            r9.zzahk = r13     // Catch:{ SQLiteException -> 0x0109 }
        L_0x00ba:
            if (r29 == 0) goto L_0x00c1
            long r13 = r9.zzahl     // Catch:{ SQLiteException -> 0x0109 }
            long r13 = r13 + r11
            r9.zzahl = r13     // Catch:{ SQLiteException -> 0x0109 }
        L_0x00c1:
            android.content.ContentValues r6 = new android.content.ContentValues     // Catch:{ SQLiteException -> 0x0109 }
            r6.<init>()     // Catch:{ SQLiteException -> 0x0109 }
            java.lang.Long r8 = java.lang.Long.valueOf(r22)     // Catch:{ SQLiteException -> 0x0109 }
            r6.put(r5, r8)     // Catch:{ SQLiteException -> 0x0109 }
            long r11 = r9.zzahh     // Catch:{ SQLiteException -> 0x0109 }
            java.lang.Long r5 = java.lang.Long.valueOf(r11)     // Catch:{ SQLiteException -> 0x0109 }
            r6.put(r3, r5)     // Catch:{ SQLiteException -> 0x0109 }
            long r11 = r9.zzahi     // Catch:{ SQLiteException -> 0x0109 }
            java.lang.Long r3 = java.lang.Long.valueOf(r11)     // Catch:{ SQLiteException -> 0x0109 }
            r6.put(r4, r3)     // Catch:{ SQLiteException -> 0x0109 }
            long r3 = r9.zzahj     // Catch:{ SQLiteException -> 0x0109 }
            java.lang.Long r3 = java.lang.Long.valueOf(r3)     // Catch:{ SQLiteException -> 0x0109 }
            r6.put(r2, r3)     // Catch:{ SQLiteException -> 0x0109 }
            long r2 = r9.zzahk     // Catch:{ SQLiteException -> 0x0109 }
            java.lang.Long r2 = java.lang.Long.valueOf(r2)     // Catch:{ SQLiteException -> 0x0109 }
            r6.put(r1, r2)     // Catch:{ SQLiteException -> 0x0109 }
            long r1 = r9.zzahl     // Catch:{ SQLiteException -> 0x0109 }
            java.lang.Long r1 = java.lang.Long.valueOf(r1)     // Catch:{ SQLiteException -> 0x0109 }
            r6.put(r0, r1)     // Catch:{ SQLiteException -> 0x0109 }
            java.lang.String r0 = "apps"
            java.lang.String r1 = "app_id=?"
            r2 = r20
            r2.update(r0, r6, r1, r7)     // Catch:{ SQLiteException -> 0x0109 }
            if (r10 == 0) goto L_0x0108
            r10.close()
        L_0x0108:
            return r9
        L_0x0109:
            r0 = move-exception
            goto L_0x0110
        L_0x010b:
            r0 = move-exception
            r10 = 0
            goto L_0x0128
        L_0x010e:
            r0 = move-exception
            r10 = 0
        L_0x0110:
            com.google.android.gms.measurement.internal.zzas r1 = r21.zzgt()     // Catch:{ all -> 0x0127 }
            com.google.android.gms.measurement.internal.zzau r1 = r1.zzjg()     // Catch:{ all -> 0x0127 }
            java.lang.String r2 = "Error updating daily counts. appId"
            java.lang.Object r3 = com.google.android.gms.measurement.internal.zzas.zzbw(r24)     // Catch:{ all -> 0x0127 }
            r1.zze(r2, r3, r0)     // Catch:{ all -> 0x0127 }
            if (r10 == 0) goto L_0x0126
            r10.close()
        L_0x0126:
            return r9
        L_0x0127:
            r0 = move-exception
        L_0x0128:
            if (r10 == 0) goto L_0x012d
            r10.close()
        L_0x012d:
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.measurement.internal.zzt.zza(long, java.lang.String, boolean, boolean, boolean, boolean, boolean):com.google.android.gms.measurement.internal.zzu");
    }

    /* JADX WARNING: Removed duplicated region for block: B:24:0x0072  */
    /* JADX WARNING: Removed duplicated region for block: B:28:0x0079  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final byte[] zzbo(java.lang.String r12) {
        /*
            r11 = this;
            com.google.android.gms.common.internal.Preconditions.checkNotEmpty(r12)
            r11.zzaf()
            r11.zzcl()
            r0 = 0
            android.database.sqlite.SQLiteDatabase r1 = r11.getWritableDatabase()     // Catch:{ SQLiteException -> 0x005d, all -> 0x005a }
            java.lang.String r2 = "apps"
            r3 = 1
            java.lang.String[] r4 = new java.lang.String[r3]     // Catch:{ SQLiteException -> 0x005d, all -> 0x005a }
            java.lang.String r5 = "remote_config"
            r9 = 0
            r4[r9] = r5     // Catch:{ SQLiteException -> 0x005d, all -> 0x005a }
            java.lang.String r5 = "app_id=?"
            java.lang.String[] r6 = new java.lang.String[r3]     // Catch:{ SQLiteException -> 0x005d, all -> 0x005a }
            r6[r9] = r12     // Catch:{ SQLiteException -> 0x005d, all -> 0x005a }
            r7 = 0
            r8 = 0
            r10 = 0
            r3 = r4
            r4 = r5
            r5 = r6
            r6 = r7
            r7 = r8
            r8 = r10
            android.database.Cursor r1 = r1.query(r2, r3, r4, r5, r6, r7, r8)     // Catch:{ SQLiteException -> 0x005d, all -> 0x005a }
            boolean r2 = r1.moveToFirst()     // Catch:{ SQLiteException -> 0x0058 }
            if (r2 != 0) goto L_0x0037
            if (r1 == 0) goto L_0x0036
            r1.close()
        L_0x0036:
            return r0
        L_0x0037:
            byte[] r2 = r1.getBlob(r9)     // Catch:{ SQLiteException -> 0x0058 }
            boolean r3 = r1.moveToNext()     // Catch:{ SQLiteException -> 0x0058 }
            if (r3 == 0) goto L_0x0052
            com.google.android.gms.measurement.internal.zzas r3 = r11.zzgt()     // Catch:{ SQLiteException -> 0x0058 }
            com.google.android.gms.measurement.internal.zzau r3 = r3.zzjg()     // Catch:{ SQLiteException -> 0x0058 }
            java.lang.String r4 = "Got multiple records for app config, expected one. appId"
            java.lang.Object r5 = com.google.android.gms.measurement.internal.zzas.zzbw(r12)     // Catch:{ SQLiteException -> 0x0058 }
            r3.zzg(r4, r5)     // Catch:{ SQLiteException -> 0x0058 }
        L_0x0052:
            if (r1 == 0) goto L_0x0057
            r1.close()
        L_0x0057:
            return r2
        L_0x0058:
            r2 = move-exception
            goto L_0x005f
        L_0x005a:
            r12 = move-exception
            r1 = r0
            goto L_0x0077
        L_0x005d:
            r2 = move-exception
            r1 = r0
        L_0x005f:
            com.google.android.gms.measurement.internal.zzas r3 = r11.zzgt()     // Catch:{ all -> 0x0076 }
            com.google.android.gms.measurement.internal.zzau r3 = r3.zzjg()     // Catch:{ all -> 0x0076 }
            java.lang.String r4 = "Error querying remote config. appId"
            java.lang.Object r12 = com.google.android.gms.measurement.internal.zzas.zzbw(r12)     // Catch:{ all -> 0x0076 }
            r3.zze(r4, r12, r2)     // Catch:{ all -> 0x0076 }
            if (r1 == 0) goto L_0x0075
            r1.close()
        L_0x0075:
            return r0
        L_0x0076:
            r12 = move-exception
        L_0x0077:
            if (r1 == 0) goto L_0x007c
            r1.close()
        L_0x007c:
            throw r12
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.measurement.internal.zzt.zzbo(java.lang.String):byte[]");
    }

    public final boolean zza(zzfw zzfw, boolean z) {
        zzaf();
        zzcl();
        Preconditions.checkNotNull(zzfw);
        Preconditions.checkNotEmpty(zzfw.zztt);
        Preconditions.checkNotNull(zzfw.zzaxo);
        zzij();
        long currentTimeMillis = zzbx().currentTimeMillis();
        if (zzfw.zzaxo.longValue() < currentTimeMillis - zzq.zzib() || zzfw.zzaxo.longValue() > zzq.zzib() + currentTimeMillis) {
            zzgt().zzjj().zzd("Storing bundle outside of the max uploading time span. appId, now, timestamp", zzas.zzbw(zzfw.zztt), Long.valueOf(currentTimeMillis), zzfw.zzaxo);
        }
        try {
            byte[] bArr = new byte[zzfw.zzvx()];
            zzya zzk = zzya.zzk(bArr, 0, bArr.length);
            zzfw.zza(zzk);
            zzk.zzza();
            byte[] zzb = zzjr().zzb(bArr);
            zzgt().zzjo().zzg("Saving bundle, size", Integer.valueOf(zzb.length));
            ContentValues contentValues = new ContentValues();
            contentValues.put("app_id", zzfw.zztt);
            contentValues.put("bundle_end_timestamp", zzfw.zzaxo);
            contentValues.put("data", zzb);
            contentValues.put("has_realtime", Integer.valueOf(z ? 1 : 0));
            if (zzfw.zzayl != null) {
                contentValues.put("retry_count", zzfw.zzayl);
            }
            try {
                if (getWritableDatabase().insert("queue", null, contentValues) != -1) {
                    return true;
                }
                zzgt().zzjg().zzg("Failed to insert bundle (got -1). appId", zzas.zzbw(zzfw.zztt));
                return false;
            } catch (SQLiteException e) {
                zzgt().zzjg().zze("Error storing bundle. appId", zzas.zzbw(zzfw.zztt), e);
                return false;
            }
        } catch (IOException e2) {
            zzgt().zzjg().zze("Data loss. Failed to serialize bundle. appId", zzas.zzbw(zzfw.zztt), e2);
            return false;
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:21:0x003a  */
    /* JADX WARNING: Removed duplicated region for block: B:25:0x0041  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final java.lang.String zzih() {
        /*
            r6 = this;
            android.database.sqlite.SQLiteDatabase r0 = r6.getWritableDatabase()
            r1 = 0
            java.lang.String r2 = "select app_id from queue order by has_realtime desc, rowid asc limit 1;"
            android.database.Cursor r0 = r0.rawQuery(r2, r1)     // Catch:{ SQLiteException -> 0x0029, all -> 0x0024 }
            boolean r2 = r0.moveToFirst()     // Catch:{ SQLiteException -> 0x0022 }
            if (r2 == 0) goto L_0x001c
            r2 = 0
            java.lang.String r1 = r0.getString(r2)     // Catch:{ SQLiteException -> 0x0022 }
            if (r0 == 0) goto L_0x001b
            r0.close()
        L_0x001b:
            return r1
        L_0x001c:
            if (r0 == 0) goto L_0x0021
            r0.close()
        L_0x0021:
            return r1
        L_0x0022:
            r2 = move-exception
            goto L_0x002b
        L_0x0024:
            r0 = move-exception
            r5 = r1
            r1 = r0
            r0 = r5
            goto L_0x003f
        L_0x0029:
            r2 = move-exception
            r0 = r1
        L_0x002b:
            com.google.android.gms.measurement.internal.zzas r3 = r6.zzgt()     // Catch:{ all -> 0x003e }
            com.google.android.gms.measurement.internal.zzau r3 = r3.zzjg()     // Catch:{ all -> 0x003e }
            java.lang.String r4 = "Database error getting next bundle app id"
            r3.zzg(r4, r2)     // Catch:{ all -> 0x003e }
            if (r0 == 0) goto L_0x003d
            r0.close()
        L_0x003d:
            return r1
        L_0x003e:
            r1 = move-exception
        L_0x003f:
            if (r0 == 0) goto L_0x0044
            r0.close()
        L_0x0044:
            throw r1
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.measurement.internal.zzt.zzih():java.lang.String");
    }

    public final boolean zzii() {
        return zza("select count(1) > 0 from queue where has_realtime = 1", (String[]) null) != 0;
    }

    public final List<Pair<zzfw, Long>> zzb(String str, int i, int i2) {
        int i3 = i2;
        zzaf();
        zzcl();
        Preconditions.checkArgument(i > 0);
        Preconditions.checkArgument(i3 > 0);
        Preconditions.checkNotEmpty(str);
        Cursor cursor = null;
        try {
            cursor = getWritableDatabase().query("queue", new String[]{"rowid", "data", "retry_count"}, "app_id=?", new String[]{str}, null, null, "rowid", String.valueOf(i));
            if (!cursor.moveToFirst()) {
                List<Pair<zzfw, Long>> emptyList = Collections.emptyList();
                if (cursor != null) {
                    cursor.close();
                }
                return emptyList;
            }
            ArrayList arrayList = new ArrayList();
            int i4 = 0;
            do {
                long j = cursor.getLong(0);
                try {
                    byte[] zza = zzjr().zza(cursor.getBlob(1));
                    if (!arrayList.isEmpty() && zza.length + i4 > i3) {
                        break;
                    }
                    zzxz zzj = zzxz.zzj(zza, 0, zza.length);
                    zzfw zzfw = new zzfw();
                    try {
                        zzfw.zza(zzj);
                        if (!cursor.isNull(2)) {
                            zzfw.zzayl = Integer.valueOf(cursor.getInt(2));
                        }
                        i4 += zza.length;
                        arrayList.add(Pair.create(zzfw, Long.valueOf(j)));
                    } catch (IOException e) {
                        zzgt().zzjg().zze("Failed to merge queued bundle. appId", zzas.zzbw(str), e);
                    }
                    if (!cursor.moveToNext()) {
                        break;
                    }
                } catch (IOException e2) {
                    zzgt().zzjg().zze("Failed to unzip queued bundle. appId", zzas.zzbw(str), e2);
                }
            } while (i4 <= i3);
            if (cursor != null) {
                cursor.close();
            }
            return arrayList;
        } catch (SQLiteException e3) {
            zzgt().zzjg().zze("Error querying bundles. appId", zzas.zzbw(str), e3);
            List<Pair<zzfw, Long>> emptyList2 = Collections.emptyList();
            if (cursor != null) {
                cursor.close();
            }
            return emptyList2;
        } catch (Throwable th) {
            if (cursor != null) {
                cursor.close();
            }
            throw th;
        }
    }

    /* access modifiers changed from: 0000 */
    public final void zzij() {
        zzaf();
        zzcl();
        if (zzip()) {
            long j = zzgu().zzanf.get();
            long elapsedRealtime = zzbx().elapsedRealtime();
            if (Math.abs(elapsedRealtime - j) > ((Long) zzai.zzajs.get()).longValue()) {
                zzgu().zzanf.set(elapsedRealtime);
                zzaf();
                zzcl();
                if (zzip()) {
                    int delete = getWritableDatabase().delete("queue", "abs(bundle_end_timestamp - ?) > cast(? as integer)", new String[]{String.valueOf(zzbx().currentTimeMillis()), String.valueOf(zzq.zzib())});
                    if (delete > 0) {
                        zzgt().zzjo().zzg("Deleted stale rows. rowsDeleted", Integer.valueOf(delete));
                    }
                }
            }
        }
    }

    /* access modifiers changed from: 0000 */
    public final void zzc(List<Long> list) {
        zzaf();
        zzcl();
        Preconditions.checkNotNull(list);
        Preconditions.checkNotZero(list.size());
        if (zzip()) {
            String join = TextUtils.join(",", list);
            StringBuilder sb = new StringBuilder(String.valueOf(join).length() + 2);
            sb.append("(");
            sb.append(join);
            sb.append(")");
            String sb2 = sb.toString();
            StringBuilder sb3 = new StringBuilder(String.valueOf(sb2).length() + 80);
            sb3.append("SELECT COUNT(1) FROM queue WHERE rowid IN ");
            sb3.append(sb2);
            sb3.append(" AND retry_count =  2147483647 LIMIT 1");
            if (zza(sb3.toString(), (String[]) null) > 0) {
                zzgt().zzjj().zzby("The number of upload retries exceeds the limit. Will remain unchanged.");
            }
            try {
                SQLiteDatabase writableDatabase = getWritableDatabase();
                StringBuilder sb4 = new StringBuilder(String.valueOf(sb2).length() + Opcodes.LAND);
                sb4.append("UPDATE queue SET retry_count = IFNULL(retry_count, 0) + 1 WHERE rowid IN ");
                sb4.append(sb2);
                sb4.append(" AND (retry_count IS NULL OR retry_count < 2147483647)");
                writableDatabase.execSQL(sb4.toString());
            } catch (SQLiteException e) {
                zzgt().zzjg().zzg("Error incrementing retry count. error", e);
            }
        }
    }

    /* access modifiers changed from: 0000 */
    public final void zza(String str, zzfi[] zzfiArr) {
        boolean z;
        String str2 = str;
        zzfi[] zzfiArr2 = zzfiArr;
        String str3 = "app_id=? and audience_id=?";
        String str4 = "event_filters";
        String str5 = "app_id=?";
        String str6 = "property_filters";
        zzcl();
        zzaf();
        Preconditions.checkNotEmpty(str);
        Preconditions.checkNotNull(zzfiArr);
        SQLiteDatabase writableDatabase = getWritableDatabase();
        writableDatabase.beginTransaction();
        try {
            zzcl();
            zzaf();
            Preconditions.checkNotEmpty(str);
            SQLiteDatabase writableDatabase2 = getWritableDatabase();
            writableDatabase2.delete(str6, str5, new String[]{str2});
            writableDatabase2.delete(str4, str5, new String[]{str2});
            for (zzfi zzfi : zzfiArr2) {
                zzcl();
                zzaf();
                Preconditions.checkNotEmpty(str);
                Preconditions.checkNotNull(zzfi);
                Preconditions.checkNotNull(zzfi.zzavi);
                Preconditions.checkNotNull(zzfi.zzavh);
                if (zzfi.zzavg == null) {
                    zzgt().zzjj().zzg("Audience with no ID. appId", zzas.zzbw(str));
                } else {
                    int intValue = zzfi.zzavg.intValue();
                    zzfj[] zzfjArr = zzfi.zzavi;
                    int length = zzfjArr.length;
                    int i = 0;
                    while (true) {
                        if (i >= length) {
                            zzfm[] zzfmArr = zzfi.zzavh;
                            int length2 = zzfmArr.length;
                            int i2 = 0;
                            while (true) {
                                if (i2 >= length2) {
                                    zzfj[] zzfjArr2 = zzfi.zzavi;
                                    int length3 = zzfjArr2.length;
                                    int i3 = 0;
                                    while (true) {
                                        if (i3 >= length3) {
                                            z = true;
                                            break;
                                        } else if (!zza(str2, intValue, zzfjArr2[i3])) {
                                            z = false;
                                            break;
                                        } else {
                                            i3++;
                                        }
                                    }
                                    if (z) {
                                        zzfm[] zzfmArr2 = zzfi.zzavh;
                                        int length4 = zzfmArr2.length;
                                        int i4 = 0;
                                        while (true) {
                                            if (i4 >= length4) {
                                                break;
                                            } else if (!zza(str2, intValue, zzfmArr2[i4])) {
                                                z = false;
                                                break;
                                            } else {
                                                i4++;
                                            }
                                        }
                                    }
                                    if (!z) {
                                        zzcl();
                                        zzaf();
                                        Preconditions.checkNotEmpty(str);
                                        SQLiteDatabase writableDatabase3 = getWritableDatabase();
                                        writableDatabase3.delete(str6, str3, new String[]{str2, String.valueOf(intValue)});
                                        writableDatabase3.delete(str4, str3, new String[]{str2, String.valueOf(intValue)});
                                    }
                                } else if (zzfmArr[i2].zzavm == null) {
                                    zzgt().zzjj().zze("Property filter with no ID. Audience definition ignored. appId, audienceId", zzas.zzbw(str), zzfi.zzavg);
                                    break;
                                } else {
                                    i2++;
                                }
                            }
                        } else if (zzfjArr[i].zzavm == null) {
                            zzgt().zzjj().zze("Event filter with no ID. Audience definition ignored. appId, audienceId", zzas.zzbw(str), zzfi.zzavg);
                            break;
                        } else {
                            i++;
                        }
                    }
                }
            }
            ArrayList arrayList = new ArrayList();
            for (zzfi zzfi2 : zzfiArr2) {
                arrayList.add(zzfi2.zzavg);
            }
            zza(str2, (List<Integer>) arrayList);
            writableDatabase.setTransactionSuccessful();
        } finally {
            writableDatabase.endTransaction();
        }
    }

    private final boolean zza(String str, int i, zzfj zzfj) {
        zzcl();
        zzaf();
        Preconditions.checkNotEmpty(str);
        Preconditions.checkNotNull(zzfj);
        if (TextUtils.isEmpty(zzfj.zzavn)) {
            zzgt().zzjj().zzd("Event filter had no event name. Audience definition ignored. appId, audienceId, filterId", zzas.zzbw(str), Integer.valueOf(i), String.valueOf(zzfj.zzavm));
            return false;
        }
        try {
            byte[] bArr = new byte[zzfj.zzvx()];
            zzya zzk = zzya.zzk(bArr, 0, bArr.length);
            zzfj.zza(zzk);
            zzk.zzza();
            ContentValues contentValues = new ContentValues();
            contentValues.put("app_id", str);
            contentValues.put("audience_id", Integer.valueOf(i));
            contentValues.put("filter_id", zzfj.zzavm);
            contentValues.put("event_name", zzfj.zzavn);
            contentValues.put("data", bArr);
            try {
                if (getWritableDatabase().insertWithOnConflict("event_filters", null, contentValues, 5) == -1) {
                    zzgt().zzjg().zzg("Failed to insert event filter (got -1). appId", zzas.zzbw(str));
                }
                return true;
            } catch (SQLiteException e) {
                zzgt().zzjg().zze("Error storing event filter. appId", zzas.zzbw(str), e);
                return false;
            }
        } catch (IOException e2) {
            zzgt().zzjg().zze("Configuration loss. Failed to serialize event filter. appId", zzas.zzbw(str), e2);
            return false;
        }
    }

    private final boolean zza(String str, int i, zzfm zzfm) {
        zzcl();
        zzaf();
        Preconditions.checkNotEmpty(str);
        Preconditions.checkNotNull(zzfm);
        if (TextUtils.isEmpty(zzfm.zzawc)) {
            zzgt().zzjj().zzd("Property filter had no property name. Audience definition ignored. appId, audienceId, filterId", zzas.zzbw(str), Integer.valueOf(i), String.valueOf(zzfm.zzavm));
            return false;
        }
        try {
            byte[] bArr = new byte[zzfm.zzvx()];
            zzya zzk = zzya.zzk(bArr, 0, bArr.length);
            zzfm.zza(zzk);
            zzk.zzza();
            ContentValues contentValues = new ContentValues();
            contentValues.put("app_id", str);
            contentValues.put("audience_id", Integer.valueOf(i));
            contentValues.put("filter_id", zzfm.zzavm);
            contentValues.put("property_name", zzfm.zzawc);
            contentValues.put("data", bArr);
            try {
                if (getWritableDatabase().insertWithOnConflict("property_filters", null, contentValues, 5) != -1) {
                    return true;
                }
                zzgt().zzjg().zzg("Failed to insert property filter (got -1). appId", zzas.zzbw(str));
                return false;
            } catch (SQLiteException e) {
                zzgt().zzjg().zze("Error storing property filter. appId", zzas.zzbw(str), e);
                return false;
            }
        } catch (IOException e2) {
            zzgt().zzjg().zze("Configuration loss. Failed to serialize property filter. appId", zzas.zzbw(str), e2);
            return false;
        }
    }

    /* access modifiers changed from: 0000 */
    /* JADX WARNING: Removed duplicated region for block: B:34:0x00b1  */
    /* JADX WARNING: Removed duplicated region for block: B:38:0x00b8  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final java.util.Map<java.lang.Integer, java.util.List<com.google.android.gms.internal.measurement.zzfj>> zzl(java.lang.String r13, java.lang.String r14) {
        /*
            r12 = this;
            r12.zzcl()
            r12.zzaf()
            com.google.android.gms.common.internal.Preconditions.checkNotEmpty(r13)
            com.google.android.gms.common.internal.Preconditions.checkNotEmpty(r14)
            androidx.collection.ArrayMap r0 = new androidx.collection.ArrayMap
            r0.<init>()
            android.database.sqlite.SQLiteDatabase r1 = r12.getWritableDatabase()
            r9 = 0
            java.lang.String r2 = "event_filters"
            r3 = 2
            java.lang.String[] r4 = new java.lang.String[r3]     // Catch:{ SQLiteException -> 0x009c, all -> 0x0099 }
            java.lang.String r5 = "audience_id"
            r10 = 0
            r4[r10] = r5     // Catch:{ SQLiteException -> 0x009c, all -> 0x0099 }
            java.lang.String r5 = "data"
            r11 = 1
            r4[r11] = r5     // Catch:{ SQLiteException -> 0x009c, all -> 0x0099 }
            java.lang.String r5 = "app_id=? AND event_name=?"
            java.lang.String[] r6 = new java.lang.String[r3]     // Catch:{ SQLiteException -> 0x009c, all -> 0x0099 }
            r6[r10] = r13     // Catch:{ SQLiteException -> 0x009c, all -> 0x0099 }
            r6[r11] = r14     // Catch:{ SQLiteException -> 0x009c, all -> 0x0099 }
            r14 = 0
            r7 = 0
            r8 = 0
            r3 = r4
            r4 = r5
            r5 = r6
            r6 = r14
            android.database.Cursor r14 = r1.query(r2, r3, r4, r5, r6, r7, r8)     // Catch:{ SQLiteException -> 0x009c, all -> 0x0099 }
            boolean r1 = r14.moveToFirst()     // Catch:{ SQLiteException -> 0x0097 }
            if (r1 != 0) goto L_0x0048
            java.util.Map r13 = java.util.Collections.emptyMap()     // Catch:{ SQLiteException -> 0x0097 }
            if (r14 == 0) goto L_0x0047
            r14.close()
        L_0x0047:
            return r13
        L_0x0048:
            byte[] r1 = r14.getBlob(r11)     // Catch:{ SQLiteException -> 0x0097 }
            int r2 = r1.length     // Catch:{ SQLiteException -> 0x0097 }
            com.google.android.gms.internal.measurement.zzxz r1 = com.google.android.gms.internal.measurement.zzxz.zzj(r1, r10, r2)     // Catch:{ SQLiteException -> 0x0097 }
            com.google.android.gms.internal.measurement.zzfj r2 = new com.google.android.gms.internal.measurement.zzfj     // Catch:{ SQLiteException -> 0x0097 }
            r2.<init>()     // Catch:{ SQLiteException -> 0x0097 }
            r2.zza(r1)     // Catch:{ IOException -> 0x0079 }
            int r1 = r14.getInt(r10)     // Catch:{ SQLiteException -> 0x0097 }
            java.lang.Integer r3 = java.lang.Integer.valueOf(r1)     // Catch:{ SQLiteException -> 0x0097 }
            java.lang.Object r3 = r0.get(r3)     // Catch:{ SQLiteException -> 0x0097 }
            java.util.List r3 = (java.util.List) r3     // Catch:{ SQLiteException -> 0x0097 }
            if (r3 != 0) goto L_0x0075
            java.util.ArrayList r3 = new java.util.ArrayList     // Catch:{ SQLiteException -> 0x0097 }
            r3.<init>()     // Catch:{ SQLiteException -> 0x0097 }
            java.lang.Integer r1 = java.lang.Integer.valueOf(r1)     // Catch:{ SQLiteException -> 0x0097 }
            r0.put(r1, r3)     // Catch:{ SQLiteException -> 0x0097 }
        L_0x0075:
            r3.add(r2)     // Catch:{ SQLiteException -> 0x0097 }
            goto L_0x008b
        L_0x0079:
            r1 = move-exception
            com.google.android.gms.measurement.internal.zzas r2 = r12.zzgt()     // Catch:{ SQLiteException -> 0x0097 }
            com.google.android.gms.measurement.internal.zzau r2 = r2.zzjg()     // Catch:{ SQLiteException -> 0x0097 }
            java.lang.String r3 = "Failed to merge filter. appId"
            java.lang.Object r4 = com.google.android.gms.measurement.internal.zzas.zzbw(r13)     // Catch:{ SQLiteException -> 0x0097 }
            r2.zze(r3, r4, r1)     // Catch:{ SQLiteException -> 0x0097 }
        L_0x008b:
            boolean r1 = r14.moveToNext()     // Catch:{ SQLiteException -> 0x0097 }
            if (r1 != 0) goto L_0x0048
            if (r14 == 0) goto L_0x0096
            r14.close()
        L_0x0096:
            return r0
        L_0x0097:
            r0 = move-exception
            goto L_0x009e
        L_0x0099:
            r13 = move-exception
            r14 = r9
            goto L_0x00b6
        L_0x009c:
            r0 = move-exception
            r14 = r9
        L_0x009e:
            com.google.android.gms.measurement.internal.zzas r1 = r12.zzgt()     // Catch:{ all -> 0x00b5 }
            com.google.android.gms.measurement.internal.zzau r1 = r1.zzjg()     // Catch:{ all -> 0x00b5 }
            java.lang.String r2 = "Database error querying filters. appId"
            java.lang.Object r13 = com.google.android.gms.measurement.internal.zzas.zzbw(r13)     // Catch:{ all -> 0x00b5 }
            r1.zze(r2, r13, r0)     // Catch:{ all -> 0x00b5 }
            if (r14 == 0) goto L_0x00b4
            r14.close()
        L_0x00b4:
            return r9
        L_0x00b5:
            r13 = move-exception
        L_0x00b6:
            if (r14 == 0) goto L_0x00bb
            r14.close()
        L_0x00bb:
            goto L_0x00bd
        L_0x00bc:
            throw r13
        L_0x00bd:
            goto L_0x00bc
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.measurement.internal.zzt.zzl(java.lang.String, java.lang.String):java.util.Map");
    }

    /* access modifiers changed from: 0000 */
    /* JADX WARNING: Removed duplicated region for block: B:34:0x00b1  */
    /* JADX WARNING: Removed duplicated region for block: B:38:0x00b8  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final java.util.Map<java.lang.Integer, java.util.List<com.google.android.gms.internal.measurement.zzfm>> zzm(java.lang.String r13, java.lang.String r14) {
        /*
            r12 = this;
            r12.zzcl()
            r12.zzaf()
            com.google.android.gms.common.internal.Preconditions.checkNotEmpty(r13)
            com.google.android.gms.common.internal.Preconditions.checkNotEmpty(r14)
            androidx.collection.ArrayMap r0 = new androidx.collection.ArrayMap
            r0.<init>()
            android.database.sqlite.SQLiteDatabase r1 = r12.getWritableDatabase()
            r9 = 0
            java.lang.String r2 = "property_filters"
            r3 = 2
            java.lang.String[] r4 = new java.lang.String[r3]     // Catch:{ SQLiteException -> 0x009c, all -> 0x0099 }
            java.lang.String r5 = "audience_id"
            r10 = 0
            r4[r10] = r5     // Catch:{ SQLiteException -> 0x009c, all -> 0x0099 }
            java.lang.String r5 = "data"
            r11 = 1
            r4[r11] = r5     // Catch:{ SQLiteException -> 0x009c, all -> 0x0099 }
            java.lang.String r5 = "app_id=? AND property_name=?"
            java.lang.String[] r6 = new java.lang.String[r3]     // Catch:{ SQLiteException -> 0x009c, all -> 0x0099 }
            r6[r10] = r13     // Catch:{ SQLiteException -> 0x009c, all -> 0x0099 }
            r6[r11] = r14     // Catch:{ SQLiteException -> 0x009c, all -> 0x0099 }
            r14 = 0
            r7 = 0
            r8 = 0
            r3 = r4
            r4 = r5
            r5 = r6
            r6 = r14
            android.database.Cursor r14 = r1.query(r2, r3, r4, r5, r6, r7, r8)     // Catch:{ SQLiteException -> 0x009c, all -> 0x0099 }
            boolean r1 = r14.moveToFirst()     // Catch:{ SQLiteException -> 0x0097 }
            if (r1 != 0) goto L_0x0048
            java.util.Map r13 = java.util.Collections.emptyMap()     // Catch:{ SQLiteException -> 0x0097 }
            if (r14 == 0) goto L_0x0047
            r14.close()
        L_0x0047:
            return r13
        L_0x0048:
            byte[] r1 = r14.getBlob(r11)     // Catch:{ SQLiteException -> 0x0097 }
            int r2 = r1.length     // Catch:{ SQLiteException -> 0x0097 }
            com.google.android.gms.internal.measurement.zzxz r1 = com.google.android.gms.internal.measurement.zzxz.zzj(r1, r10, r2)     // Catch:{ SQLiteException -> 0x0097 }
            com.google.android.gms.internal.measurement.zzfm r2 = new com.google.android.gms.internal.measurement.zzfm     // Catch:{ SQLiteException -> 0x0097 }
            r2.<init>()     // Catch:{ SQLiteException -> 0x0097 }
            r2.zza(r1)     // Catch:{ IOException -> 0x0079 }
            int r1 = r14.getInt(r10)     // Catch:{ SQLiteException -> 0x0097 }
            java.lang.Integer r3 = java.lang.Integer.valueOf(r1)     // Catch:{ SQLiteException -> 0x0097 }
            java.lang.Object r3 = r0.get(r3)     // Catch:{ SQLiteException -> 0x0097 }
            java.util.List r3 = (java.util.List) r3     // Catch:{ SQLiteException -> 0x0097 }
            if (r3 != 0) goto L_0x0075
            java.util.ArrayList r3 = new java.util.ArrayList     // Catch:{ SQLiteException -> 0x0097 }
            r3.<init>()     // Catch:{ SQLiteException -> 0x0097 }
            java.lang.Integer r1 = java.lang.Integer.valueOf(r1)     // Catch:{ SQLiteException -> 0x0097 }
            r0.put(r1, r3)     // Catch:{ SQLiteException -> 0x0097 }
        L_0x0075:
            r3.add(r2)     // Catch:{ SQLiteException -> 0x0097 }
            goto L_0x008b
        L_0x0079:
            r1 = move-exception
            com.google.android.gms.measurement.internal.zzas r2 = r12.zzgt()     // Catch:{ SQLiteException -> 0x0097 }
            com.google.android.gms.measurement.internal.zzau r2 = r2.zzjg()     // Catch:{ SQLiteException -> 0x0097 }
            java.lang.String r3 = "Failed to merge filter"
            java.lang.Object r4 = com.google.android.gms.measurement.internal.zzas.zzbw(r13)     // Catch:{ SQLiteException -> 0x0097 }
            r2.zze(r3, r4, r1)     // Catch:{ SQLiteException -> 0x0097 }
        L_0x008b:
            boolean r1 = r14.moveToNext()     // Catch:{ SQLiteException -> 0x0097 }
            if (r1 != 0) goto L_0x0048
            if (r14 == 0) goto L_0x0096
            r14.close()
        L_0x0096:
            return r0
        L_0x0097:
            r0 = move-exception
            goto L_0x009e
        L_0x0099:
            r13 = move-exception
            r14 = r9
            goto L_0x00b6
        L_0x009c:
            r0 = move-exception
            r14 = r9
        L_0x009e:
            com.google.android.gms.measurement.internal.zzas r1 = r12.zzgt()     // Catch:{ all -> 0x00b5 }
            com.google.android.gms.measurement.internal.zzau r1 = r1.zzjg()     // Catch:{ all -> 0x00b5 }
            java.lang.String r2 = "Database error querying filters. appId"
            java.lang.Object r13 = com.google.android.gms.measurement.internal.zzas.zzbw(r13)     // Catch:{ all -> 0x00b5 }
            r1.zze(r2, r13, r0)     // Catch:{ all -> 0x00b5 }
            if (r14 == 0) goto L_0x00b4
            r14.close()
        L_0x00b4:
            return r9
        L_0x00b5:
            r13 = move-exception
        L_0x00b6:
            if (r14 == 0) goto L_0x00bb
            r14.close()
        L_0x00bb:
            goto L_0x00bd
        L_0x00bc:
            throw r13
        L_0x00bd:
            goto L_0x00bc
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.measurement.internal.zzt.zzm(java.lang.String, java.lang.String):java.util.Map");
    }

    private final boolean zza(String str, List<Integer> list) {
        Preconditions.checkNotEmpty(str);
        zzcl();
        zzaf();
        SQLiteDatabase writableDatabase = getWritableDatabase();
        try {
            long zza = zza("select count(1) from audience_filter_values where app_id=?", new String[]{str});
            int max = Math.max(0, Math.min(2000, zzgv().zzb(str, zzai.zzajz)));
            if (zza <= ((long) max)) {
                return false;
            }
            ArrayList arrayList = new ArrayList();
            for (int i = 0; i < list.size(); i++) {
                Integer num = (Integer) list.get(i);
                if (num == null || !(num instanceof Integer)) {
                    return false;
                }
                arrayList.add(Integer.toString(num.intValue()));
            }
            String join = TextUtils.join(",", arrayList);
            StringBuilder sb = new StringBuilder(String.valueOf(join).length() + 2);
            sb.append("(");
            sb.append(join);
            sb.append(")");
            String sb2 = sb.toString();
            StringBuilder sb3 = new StringBuilder(String.valueOf(sb2).length() + 140);
            sb3.append("audience_id in (select audience_id from audience_filter_values where app_id=? and audience_id not in ");
            sb3.append(sb2);
            sb3.append(" order by rowid desc limit -1 offset ?)");
            return writableDatabase.delete("audience_filter_values", sb3.toString(), new String[]{str, Integer.toString(max)}) > 0;
        } catch (SQLiteException e) {
            zzgt().zzjg().zze("Database error querying filters. appId", zzas.zzbw(str), e);
            return false;
        }
    }

    /* access modifiers changed from: 0000 */
    /* JADX WARNING: Removed duplicated region for block: B:31:0x0094  */
    /* JADX WARNING: Removed duplicated region for block: B:35:0x009b  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final java.util.Map<java.lang.Integer, com.google.android.gms.internal.measurement.zzfx> zzbp(java.lang.String r12) {
        /*
            r11 = this;
            r11.zzcl()
            r11.zzaf()
            com.google.android.gms.common.internal.Preconditions.checkNotEmpty(r12)
            android.database.sqlite.SQLiteDatabase r0 = r11.getWritableDatabase()
            r8 = 0
            java.lang.String r1 = "audience_filter_values"
            r2 = 2
            java.lang.String[] r2 = new java.lang.String[r2]     // Catch:{ SQLiteException -> 0x007f, all -> 0x007c }
            java.lang.String r3 = "audience_id"
            r9 = 0
            r2[r9] = r3     // Catch:{ SQLiteException -> 0x007f, all -> 0x007c }
            java.lang.String r3 = "current_results"
            r10 = 1
            r2[r10] = r3     // Catch:{ SQLiteException -> 0x007f, all -> 0x007c }
            java.lang.String r3 = "app_id=?"
            java.lang.String[] r4 = new java.lang.String[r10]     // Catch:{ SQLiteException -> 0x007f, all -> 0x007c }
            r4[r9] = r12     // Catch:{ SQLiteException -> 0x007f, all -> 0x007c }
            r5 = 0
            r6 = 0
            r7 = 0
            android.database.Cursor r0 = r0.query(r1, r2, r3, r4, r5, r6, r7)     // Catch:{ SQLiteException -> 0x007f, all -> 0x007c }
            boolean r1 = r0.moveToFirst()     // Catch:{ SQLiteException -> 0x007a }
            if (r1 != 0) goto L_0x0036
            if (r0 == 0) goto L_0x0035
            r0.close()
        L_0x0035:
            return r8
        L_0x0036:
            androidx.collection.ArrayMap r1 = new androidx.collection.ArrayMap     // Catch:{ SQLiteException -> 0x007a }
            r1.<init>()     // Catch:{ SQLiteException -> 0x007a }
        L_0x003b:
            int r2 = r0.getInt(r9)     // Catch:{ SQLiteException -> 0x007a }
            byte[] r3 = r0.getBlob(r10)     // Catch:{ SQLiteException -> 0x007a }
            int r4 = r3.length     // Catch:{ SQLiteException -> 0x007a }
            com.google.android.gms.internal.measurement.zzxz r3 = com.google.android.gms.internal.measurement.zzxz.zzj(r3, r9, r4)     // Catch:{ SQLiteException -> 0x007a }
            com.google.android.gms.internal.measurement.zzfx r4 = new com.google.android.gms.internal.measurement.zzfx     // Catch:{ SQLiteException -> 0x007a }
            r4.<init>()     // Catch:{ SQLiteException -> 0x007a }
            r4.zza(r3)     // Catch:{ IOException -> 0x0058 }
            java.lang.Integer r2 = java.lang.Integer.valueOf(r2)     // Catch:{ SQLiteException -> 0x007a }
            r1.put(r2, r4)     // Catch:{ SQLiteException -> 0x007a }
            goto L_0x006e
        L_0x0058:
            r3 = move-exception
            com.google.android.gms.measurement.internal.zzas r4 = r11.zzgt()     // Catch:{ SQLiteException -> 0x007a }
            com.google.android.gms.measurement.internal.zzau r4 = r4.zzjg()     // Catch:{ SQLiteException -> 0x007a }
            java.lang.String r5 = "Failed to merge filter results. appId, audienceId, error"
            java.lang.Object r6 = com.google.android.gms.measurement.internal.zzas.zzbw(r12)     // Catch:{ SQLiteException -> 0x007a }
            java.lang.Integer r2 = java.lang.Integer.valueOf(r2)     // Catch:{ SQLiteException -> 0x007a }
            r4.zzd(r5, r6, r2, r3)     // Catch:{ SQLiteException -> 0x007a }
        L_0x006e:
            boolean r2 = r0.moveToNext()     // Catch:{ SQLiteException -> 0x007a }
            if (r2 != 0) goto L_0x003b
            if (r0 == 0) goto L_0x0079
            r0.close()
        L_0x0079:
            return r1
        L_0x007a:
            r1 = move-exception
            goto L_0x0081
        L_0x007c:
            r12 = move-exception
            r0 = r8
            goto L_0x0099
        L_0x007f:
            r1 = move-exception
            r0 = r8
        L_0x0081:
            com.google.android.gms.measurement.internal.zzas r2 = r11.zzgt()     // Catch:{ all -> 0x0098 }
            com.google.android.gms.measurement.internal.zzau r2 = r2.zzjg()     // Catch:{ all -> 0x0098 }
            java.lang.String r3 = "Database error querying filter results. appId"
            java.lang.Object r12 = com.google.android.gms.measurement.internal.zzas.zzbw(r12)     // Catch:{ all -> 0x0098 }
            r2.zze(r3, r12, r1)     // Catch:{ all -> 0x0098 }
            if (r0 == 0) goto L_0x0097
            r0.close()
        L_0x0097:
            return r8
        L_0x0098:
            r12 = move-exception
        L_0x0099:
            if (r0 == 0) goto L_0x009e
            r0.close()
        L_0x009e:
            goto L_0x00a0
        L_0x009f:
            throw r12
        L_0x00a0:
            goto L_0x009f
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.measurement.internal.zzt.zzbp(java.lang.String):java.util.Map");
    }

    private static void zza(ContentValues contentValues, String str, Object obj) {
        Preconditions.checkNotEmpty(str);
        Preconditions.checkNotNull(obj);
        if (obj instanceof String) {
            contentValues.put(str, (String) obj);
        } else if (obj instanceof Long) {
            contentValues.put(str, (Long) obj);
        } else if (obj instanceof Double) {
            contentValues.put(str, (Double) obj);
        } else {
            throw new IllegalArgumentException("Invalid value type");
        }
    }

    private final Object zza(Cursor cursor, int i) {
        int type = cursor.getType(i);
        if (type == 0) {
            zzgt().zzjg().zzby("Loaded invalid null value from database");
            return null;
        } else if (type == 1) {
            return Long.valueOf(cursor.getLong(i));
        } else {
            if (type == 2) {
                return Double.valueOf(cursor.getDouble(i));
            }
            if (type == 3) {
                return cursor.getString(i);
            }
            if (type != 4) {
                zzgt().zzjg().zzg("Loaded invalid unknown value type, ignoring it", Integer.valueOf(type));
                return null;
            }
            zzgt().zzjg().zzby("Loaded invalid blob type value, ignoring it");
            return null;
        }
    }

    public final long zzik() {
        return zza("select max(bundle_end_timestamp) from queue", (String[]) null, 0);
    }

    /* access modifiers changed from: protected */
    public final long zzn(String str, String str2) {
        long j;
        String str3;
        String str4;
        String str5 = str;
        String str6 = str2;
        Preconditions.checkNotEmpty(str);
        Preconditions.checkNotEmpty(str2);
        zzaf();
        zzcl();
        SQLiteDatabase writableDatabase = getWritableDatabase();
        writableDatabase.beginTransaction();
        try {
            StringBuilder sb = new StringBuilder(String.valueOf(str2).length() + 32);
            sb.append("select ");
            sb.append(str6);
            sb.append(" from app2 where app_id=?");
            try {
                j = zza(sb.toString(), new String[]{str5}, -1);
                str3 = "app2";
                str4 = "app_id";
                if (j == -1) {
                    ContentValues contentValues = new ContentValues();
                    contentValues.put(str4, str5);
                    contentValues.put("first_open_count", Integer.valueOf(0));
                    contentValues.put("previous_install_count", Integer.valueOf(0));
                    if (writableDatabase.insertWithOnConflict(str3, null, contentValues, 5) == -1) {
                        zzgt().zzjg().zze("Failed to insert column (got -1). appId", zzas.zzbw(str), str6);
                        writableDatabase.endTransaction();
                        return -1;
                    }
                    j = 0;
                }
            } catch (SQLiteException e) {
                e = e;
                j = 0;
                try {
                    zzgt().zzjg().zzd("Error inserting column. appId", zzas.zzbw(str), str6, e);
                    writableDatabase.endTransaction();
                    return j;
                } catch (Throwable th) {
                    th = th;
                    writableDatabase.endTransaction();
                    throw th;
                }
            }
            try {
                ContentValues contentValues2 = new ContentValues();
                contentValues2.put(str4, str5);
                contentValues2.put(str6, Long.valueOf(1 + j));
                if (((long) writableDatabase.update(str3, contentValues2, "app_id = ?", new String[]{str5})) == 0) {
                    zzgt().zzjg().zze("Failed to update column (got 0). appId", zzas.zzbw(str), str6);
                    writableDatabase.endTransaction();
                    return -1;
                }
                writableDatabase.setTransactionSuccessful();
                writableDatabase.endTransaction();
                return j;
            } catch (SQLiteException e2) {
                e = e2;
                zzgt().zzjg().zzd("Error inserting column. appId", zzas.zzbw(str), str6, e);
                writableDatabase.endTransaction();
                return j;
            }
        } catch (SQLiteException e3) {
            e = e3;
            j = 0;
            zzgt().zzjg().zzd("Error inserting column. appId", zzas.zzbw(str), str6, e);
            writableDatabase.endTransaction();
            return j;
        } catch (Throwable th2) {
            th = th2;
            writableDatabase.endTransaction();
            throw th;
        }
    }

    public final long zzil() {
        return zza("select max(timestamp) from raw_events", (String[]) null, 0);
    }

    public final long zza(zzfw zzfw) throws IOException {
        long j;
        zzaf();
        zzcl();
        Preconditions.checkNotNull(zzfw);
        Preconditions.checkNotEmpty(zzfw.zztt);
        try {
            byte[] bArr = new byte[zzfw.zzvx()];
            zzya zzk = zzya.zzk(bArr, 0, bArr.length);
            zzfw.zza(zzk);
            zzk.zzza();
            zzft zzjr = zzjr();
            Preconditions.checkNotNull(bArr);
            zzjr.zzgr().zzaf();
            MessageDigest messageDigest = zzfx.getMessageDigest();
            if (messageDigest == null) {
                zzjr.zzgt().zzjg().zzby("Failed to get MD5");
                j = 0;
            } else {
                j = zzfx.zzc(messageDigest.digest(bArr));
            }
            ContentValues contentValues = new ContentValues();
            contentValues.put("app_id", zzfw.zztt);
            contentValues.put("metadata_fingerprint", Long.valueOf(j));
            contentValues.put(TtmlNode.TAG_METADATA, bArr);
            try {
                getWritableDatabase().insertWithOnConflict("raw_events_metadata", null, contentValues, 4);
                return j;
            } catch (SQLiteException e) {
                zzgt().zzjg().zze("Error storing raw event metadata. appId", zzas.zzbw(zzfw.zztt), e);
                throw e;
            }
        } catch (IOException e2) {
            zzgt().zzjg().zze("Data loss. Failed to serialize event metadata. appId", zzas.zzbw(zzfw.zztt), e2);
            throw e2;
        }
    }

    public final boolean zzim() {
        return zza("select count(1) > 0 from raw_events", (String[]) null) != 0;
    }

    public final boolean zzin() {
        return zza("select count(1) > 0 from raw_events where realtime = 1", (String[]) null) != 0;
    }

    public final long zzbq(String str) {
        Preconditions.checkNotEmpty(str);
        return zza("select count(1) from events where app_id=? and name not like '!_%' escape '!'", new String[]{str}, 0);
    }

    /* JADX WARNING: Removed duplicated region for block: B:23:0x0054  */
    /* JADX WARNING: Removed duplicated region for block: B:27:0x005b  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final java.lang.String zzad(long r5) {
        /*
            r4 = this;
            r4.zzaf()
            r4.zzcl()
            r0 = 0
            android.database.sqlite.SQLiteDatabase r1 = r4.getWritableDatabase()     // Catch:{ SQLiteException -> 0x0043, all -> 0x0040 }
            java.lang.String r2 = "select app_id from apps where app_id in (select distinct app_id from raw_events) and config_fetched_time < ? order by failed_config_fetch_time limit 1;"
            r3 = 1
            java.lang.String[] r3 = new java.lang.String[r3]     // Catch:{ SQLiteException -> 0x0043, all -> 0x0040 }
            java.lang.String r5 = java.lang.String.valueOf(r5)     // Catch:{ SQLiteException -> 0x0043, all -> 0x0040 }
            r6 = 0
            r3[r6] = r5     // Catch:{ SQLiteException -> 0x0043, all -> 0x0040 }
            android.database.Cursor r5 = r1.rawQuery(r2, r3)     // Catch:{ SQLiteException -> 0x0043, all -> 0x0040 }
            boolean r1 = r5.moveToFirst()     // Catch:{ SQLiteException -> 0x003e }
            if (r1 != 0) goto L_0x0034
            com.google.android.gms.measurement.internal.zzas r6 = r4.zzgt()     // Catch:{ SQLiteException -> 0x003e }
            com.google.android.gms.measurement.internal.zzau r6 = r6.zzjo()     // Catch:{ SQLiteException -> 0x003e }
            java.lang.String r1 = "No expired configs for apps with pending events"
            r6.zzby(r1)     // Catch:{ SQLiteException -> 0x003e }
            if (r5 == 0) goto L_0x0033
            r5.close()
        L_0x0033:
            return r0
        L_0x0034:
            java.lang.String r6 = r5.getString(r6)     // Catch:{ SQLiteException -> 0x003e }
            if (r5 == 0) goto L_0x003d
            r5.close()
        L_0x003d:
            return r6
        L_0x003e:
            r6 = move-exception
            goto L_0x0045
        L_0x0040:
            r6 = move-exception
            r5 = r0
            goto L_0x0059
        L_0x0043:
            r6 = move-exception
            r5 = r0
        L_0x0045:
            com.google.android.gms.measurement.internal.zzas r1 = r4.zzgt()     // Catch:{ all -> 0x0058 }
            com.google.android.gms.measurement.internal.zzau r1 = r1.zzjg()     // Catch:{ all -> 0x0058 }
            java.lang.String r2 = "Error selecting expired configs"
            r1.zzg(r2, r6)     // Catch:{ all -> 0x0058 }
            if (r5 == 0) goto L_0x0057
            r5.close()
        L_0x0057:
            return r0
        L_0x0058:
            r6 = move-exception
        L_0x0059:
            if (r5 == 0) goto L_0x005e
            r5.close()
        L_0x005e:
            throw r6
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.measurement.internal.zzt.zzad(long):java.lang.String");
    }

    public final long zzio() {
        Cursor cursor = null;
        try {
            cursor = getWritableDatabase().rawQuery("select rowid from raw_events order by rowid desc limit 1;", null);
            if (!cursor.moveToFirst()) {
                if (cursor != null) {
                    cursor.close();
                }
                return -1;
            }
            long j = cursor.getLong(0);
            if (cursor != null) {
                cursor.close();
            }
            return j;
        } catch (SQLiteException e) {
            zzgt().zzjg().zzg("Error querying raw events", e);
            if (cursor != null) {
                cursor.close();
            }
            return -1;
        } catch (Throwable th) {
            if (cursor != null) {
                cursor.close();
            }
            throw th;
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:33:0x0088  */
    /* JADX WARNING: Removed duplicated region for block: B:37:0x008f  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final android.util.Pair<com.google.android.gms.internal.measurement.zzft, java.lang.Long> zza(java.lang.String r8, java.lang.Long r9) {
        /*
            r7 = this;
            r7.zzaf()
            r7.zzcl()
            r0 = 0
            android.database.sqlite.SQLiteDatabase r1 = r7.getWritableDatabase()     // Catch:{ SQLiteException -> 0x0077, all -> 0x0074 }
            java.lang.String r2 = "select main_event, children_to_process from main_event_params where app_id=? and event_id=?"
            r3 = 2
            java.lang.String[] r3 = new java.lang.String[r3]     // Catch:{ SQLiteException -> 0x0077, all -> 0x0074 }
            r4 = 0
            r3[r4] = r8     // Catch:{ SQLiteException -> 0x0077, all -> 0x0074 }
            java.lang.String r5 = java.lang.String.valueOf(r9)     // Catch:{ SQLiteException -> 0x0077, all -> 0x0074 }
            r6 = 1
            r3[r6] = r5     // Catch:{ SQLiteException -> 0x0077, all -> 0x0074 }
            android.database.Cursor r1 = r1.rawQuery(r2, r3)     // Catch:{ SQLiteException -> 0x0077, all -> 0x0074 }
            boolean r2 = r1.moveToFirst()     // Catch:{ SQLiteException -> 0x0072 }
            if (r2 != 0) goto L_0x0037
            com.google.android.gms.measurement.internal.zzas r8 = r7.zzgt()     // Catch:{ SQLiteException -> 0x0072 }
            com.google.android.gms.measurement.internal.zzau r8 = r8.zzjo()     // Catch:{ SQLiteException -> 0x0072 }
            java.lang.String r9 = "Main event not found"
            r8.zzby(r9)     // Catch:{ SQLiteException -> 0x0072 }
            if (r1 == 0) goto L_0x0036
            r1.close()
        L_0x0036:
            return r0
        L_0x0037:
            byte[] r2 = r1.getBlob(r4)     // Catch:{ SQLiteException -> 0x0072 }
            long r5 = r1.getLong(r6)     // Catch:{ SQLiteException -> 0x0072 }
            java.lang.Long r3 = java.lang.Long.valueOf(r5)     // Catch:{ SQLiteException -> 0x0072 }
            int r5 = r2.length     // Catch:{ SQLiteException -> 0x0072 }
            com.google.android.gms.internal.measurement.zzxz r2 = com.google.android.gms.internal.measurement.zzxz.zzj(r2, r4, r5)     // Catch:{ SQLiteException -> 0x0072 }
            com.google.android.gms.internal.measurement.zzft r4 = new com.google.android.gms.internal.measurement.zzft     // Catch:{ SQLiteException -> 0x0072 }
            r4.<init>()     // Catch:{ SQLiteException -> 0x0072 }
            r4.zza(r2)     // Catch:{ IOException -> 0x005a }
            android.util.Pair r8 = android.util.Pair.create(r4, r3)     // Catch:{ SQLiteException -> 0x0072 }
            if (r1 == 0) goto L_0x0059
            r1.close()
        L_0x0059:
            return r8
        L_0x005a:
            r2 = move-exception
            com.google.android.gms.measurement.internal.zzas r3 = r7.zzgt()     // Catch:{ SQLiteException -> 0x0072 }
            com.google.android.gms.measurement.internal.zzau r3 = r3.zzjg()     // Catch:{ SQLiteException -> 0x0072 }
            java.lang.String r4 = "Failed to merge main event. appId, eventId"
            java.lang.Object r8 = com.google.android.gms.measurement.internal.zzas.zzbw(r8)     // Catch:{ SQLiteException -> 0x0072 }
            r3.zzd(r4, r8, r9, r2)     // Catch:{ SQLiteException -> 0x0072 }
            if (r1 == 0) goto L_0x0071
            r1.close()
        L_0x0071:
            return r0
        L_0x0072:
            r8 = move-exception
            goto L_0x0079
        L_0x0074:
            r8 = move-exception
            r1 = r0
            goto L_0x008d
        L_0x0077:
            r8 = move-exception
            r1 = r0
        L_0x0079:
            com.google.android.gms.measurement.internal.zzas r9 = r7.zzgt()     // Catch:{ all -> 0x008c }
            com.google.android.gms.measurement.internal.zzau r9 = r9.zzjg()     // Catch:{ all -> 0x008c }
            java.lang.String r2 = "Error selecting main event"
            r9.zzg(r2, r8)     // Catch:{ all -> 0x008c }
            if (r1 == 0) goto L_0x008b
            r1.close()
        L_0x008b:
            return r0
        L_0x008c:
            r8 = move-exception
        L_0x008d:
            if (r1 == 0) goto L_0x0092
            r1.close()
        L_0x0092:
            throw r8
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.measurement.internal.zzt.zza(java.lang.String, java.lang.Long):android.util.Pair");
    }

    public final boolean zza(String str, Long l, long j, zzft zzft) {
        zzaf();
        zzcl();
        Preconditions.checkNotNull(zzft);
        Preconditions.checkNotEmpty(str);
        Preconditions.checkNotNull(l);
        try {
            byte[] bArr = new byte[zzft.zzvx()];
            zzya zzk = zzya.zzk(bArr, 0, bArr.length);
            zzft.zza(zzk);
            zzk.zzza();
            zzgt().zzjo().zze("Saving complex main event, appId, data size", zzgq().zzbt(str), Integer.valueOf(bArr.length));
            ContentValues contentValues = new ContentValues();
            contentValues.put("app_id", str);
            contentValues.put("event_id", l);
            contentValues.put("children_to_process", Long.valueOf(j));
            contentValues.put("main_event", bArr);
            try {
                if (getWritableDatabase().insertWithOnConflict("main_event_params", null, contentValues, 5) != -1) {
                    return true;
                }
                zzgt().zzjg().zzg("Failed to insert complex main event (got -1). appId", zzas.zzbw(str));
                return false;
            } catch (SQLiteException e) {
                zzgt().zzjg().zze("Error storing complex main event. appId", zzas.zzbw(str), e);
                return false;
            }
        } catch (IOException e2) {
            zzgt().zzjg().zzd("Data loss. Failed to serialize event params/data. appId, eventId", zzas.zzbw(str), l, e2);
            return false;
        }
    }

    public final boolean zza(zzab zzab, long j, boolean z) {
        zzaf();
        zzcl();
        Preconditions.checkNotNull(zzab);
        Preconditions.checkNotEmpty(zzab.zztt);
        zzft zzft = new zzft();
        zzft.zzaxe = Long.valueOf(zzab.zzaht);
        zzft.zzaxc = new zzfu[zzab.zzahu.size()];
        Iterator it = zzab.zzahu.iterator();
        int i = 0;
        while (it.hasNext()) {
            String str = (String) it.next();
            zzfu zzfu = new zzfu();
            int i2 = i + 1;
            zzft.zzaxc[i] = zzfu;
            zzfu.name = str;
            zzjr().zza(zzfu, zzab.zzahu.get(str));
            i = i2;
        }
        try {
            byte[] bArr = new byte[zzft.zzvx()];
            zzya zzk = zzya.zzk(bArr, 0, bArr.length);
            zzft.zza(zzk);
            zzk.zzza();
            zzgt().zzjo().zze("Saving event, name, data size", zzgq().zzbt(zzab.name), Integer.valueOf(bArr.length));
            ContentValues contentValues = new ContentValues();
            contentValues.put("app_id", zzab.zztt);
            contentValues.put(PostalAddressParser.USER_ADDRESS_NAME_KEY, zzab.name);
            contentValues.put(AppMeasurement.Param.TIMESTAMP, Long.valueOf(zzab.timestamp));
            contentValues.put("metadata_fingerprint", Long.valueOf(j));
            contentValues.put("data", bArr);
            contentValues.put("realtime", Integer.valueOf(z ? 1 : 0));
            try {
                if (getWritableDatabase().insert("raw_events", null, contentValues) != -1) {
                    return true;
                }
                zzgt().zzjg().zzg("Failed to insert raw event (got -1). appId", zzas.zzbw(zzab.zztt));
                return false;
            } catch (SQLiteException e) {
                zzgt().zzjg().zze("Error storing raw event. appId", zzas.zzbw(zzab.zztt), e);
                return false;
            }
        } catch (IOException e2) {
            zzgt().zzjg().zze("Data loss. Failed to serialize event params/data. appId", zzas.zzbw(zzab.zztt), e2);
            return false;
        }
    }

    private final boolean zzip() {
        return getContext().getDatabasePath("google_app_measurement.db").exists();
    }
}
