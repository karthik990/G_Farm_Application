package com.google.android.gms.measurement.internal;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.util.Clock;
import com.mobiroller.constants.ChatConstants;

public final class zzao extends zzf {
    private final zzap zzalo = new zzap(this, getContext(), "google_app_measurement_local.db");
    private boolean zzalp;

    zzao(zzbw zzbw) {
        super(zzbw);
    }

    /* access modifiers changed from: protected */
    public final boolean zzgy() {
        return false;
    }

    public final void resetAnalyticsData() {
        zzgg();
        zzaf();
        try {
            int delete = getWritableDatabase().delete(ChatConstants.ARG_CHAT_MESSAGES, null, null) + 0;
            if (delete > 0) {
                zzgt().zzjo().zzg("Reset local analytics data. records", Integer.valueOf(delete));
            }
        } catch (SQLiteException e) {
            zzgt().zzjg().zzg("Error resetting local analytics data. error", e);
        }
    }

    /* JADX WARNING: type inference failed for: r3v0 */
    /* JADX WARNING: type inference failed for: r3v1, types: [int, boolean] */
    /* JADX WARNING: type inference failed for: r8v0 */
    /* JADX WARNING: type inference failed for: r13v0, types: [android.database.Cursor] */
    /* JADX WARNING: type inference failed for: r10v0, types: [android.database.sqlite.SQLiteDatabase] */
    /* JADX WARNING: type inference failed for: r10v1 */
    /* JADX WARNING: type inference failed for: r8v1 */
    /* JADX WARNING: type inference failed for: r13v1 */
    /* JADX WARNING: type inference failed for: r3v2 */
    /* JADX WARNING: type inference failed for: r10v2, types: [android.database.sqlite.SQLiteDatabase] */
    /* JADX WARNING: type inference failed for: r8v2, types: [android.database.Cursor] */
    /* JADX WARNING: type inference failed for: r10v3 */
    /* JADX WARNING: type inference failed for: r10v4, types: [android.database.sqlite.SQLiteDatabase] */
    /* JADX WARNING: type inference failed for: r8v3, types: [android.database.Cursor] */
    /* JADX WARNING: type inference failed for: r10v5 */
    /* JADX WARNING: type inference failed for: r13v2, types: [android.database.Cursor] */
    /* JADX WARNING: type inference failed for: r8v4, types: [android.database.sqlite.SQLiteDatabase] */
    /* JADX WARNING: type inference failed for: r10v6 */
    /* JADX WARNING: type inference failed for: r13v3 */
    /* JADX WARNING: type inference failed for: r10v7 */
    /* JADX WARNING: type inference failed for: r13v4 */
    /* JADX WARNING: type inference failed for: r10v8, types: [android.database.sqlite.SQLiteDatabase] */
    /* JADX WARNING: type inference failed for: r8v5 */
    /* JADX WARNING: type inference failed for: r13v5 */
    /* JADX WARNING: type inference failed for: r8v6 */
    /* JADX WARNING: type inference failed for: r13v6 */
    /* JADX WARNING: type inference failed for: r13v7 */
    /* JADX WARNING: type inference failed for: r13v8, types: [android.database.Cursor] */
    /* JADX WARNING: type inference failed for: r8v7 */
    /* JADX WARNING: type inference failed for: r8v11 */
    /* JADX WARNING: type inference failed for: r13v9 */
    /* JADX WARNING: type inference failed for: r3v20 */
    /* JADX WARNING: type inference failed for: r8v12 */
    /* JADX WARNING: type inference failed for: r8v13 */
    /* JADX WARNING: type inference failed for: r8v14 */
    /* JADX WARNING: type inference failed for: r8v15 */
    /* JADX WARNING: type inference failed for: r8v16 */
    /* JADX WARNING: type inference failed for: r10v9 */
    /* JADX WARNING: type inference failed for: r3v21 */
    /* JADX WARNING: type inference failed for: r10v10 */
    /* JADX WARNING: type inference failed for: r10v11 */
    /* JADX WARNING: type inference failed for: r8v17 */
    /* JADX WARNING: type inference failed for: r8v18 */
    /* JADX WARNING: type inference failed for: r10v12 */
    /* JADX WARNING: type inference failed for: r10v13 */
    /* JADX WARNING: type inference failed for: r8v19 */
    /* JADX WARNING: type inference failed for: r8v20 */
    /* JADX WARNING: type inference failed for: r13v10 */
    /* JADX WARNING: type inference failed for: r10v14 */
    /* JADX WARNING: type inference failed for: r10v15 */
    /* JADX WARNING: type inference failed for: r10v16 */
    /* JADX WARNING: type inference failed for: r10v17 */
    /* JADX WARNING: type inference failed for: r10v18 */
    /* JADX WARNING: type inference failed for: r10v19 */
    /* JADX WARNING: type inference failed for: r10v20 */
    /* JADX WARNING: type inference failed for: r13v11 */
    /* JADX WARNING: type inference failed for: r13v12 */
    /* JADX WARNING: type inference failed for: r13v13 */
    /* JADX WARNING: Code restructure failed: missing block: B:14:0x003a, code lost:
        r0 = e;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:15:0x003b, code lost:
        r13 = 0;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:16:0x003e, code lost:
        r0 = e;
        r8 = r8;
        r10 = r10;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:48:0x00cc, code lost:
        r8 = 0;
        r10 = r10;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:59:0x00e1, code lost:
        if (r8.inTransaction() != false) goto L_0x00e3;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:60:0x00e3, code lost:
        r8.endTransaction();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:63:0x00f6, code lost:
        r13.close();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:65:0x00fb, code lost:
        r8.close();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:66:0x00ff, code lost:
        r0 = th;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:67:0x0100, code lost:
        r10 = r8;
        r13 = r13;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:75:0x010c, code lost:
        r8.close();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:77:0x0111, code lost:
        r10.close();
     */
    /* JADX WARNING: Failed to process nested try/catch */
    /* JADX WARNING: Multi-variable type inference failed. Error: jadx.core.utils.exceptions.JadxRuntimeException: No candidate types for var: r3v1, types: [int, boolean]
      assigns: []
      uses: [?[int, short, byte, char], int, boolean]
      mth insns count: 164
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
    /* JADX WARNING: Removed duplicated region for block: B:100:0x0130 A[SYNTHETIC] */
    /* JADX WARNING: Removed duplicated region for block: B:102:0x0130 A[SYNTHETIC] */
    /* JADX WARNING: Removed duplicated region for block: B:104:0x0130 A[SYNTHETIC] */
    /* JADX WARNING: Removed duplicated region for block: B:49:? A[ExcHandler: SQLiteDatabaseLockedException (unused android.database.sqlite.SQLiteDatabaseLockedException), SYNTHETIC, Splitter:B:9:0x0032] */
    /* JADX WARNING: Removed duplicated region for block: B:57:0x00dd A[SYNTHETIC, Splitter:B:57:0x00dd] */
    /* JADX WARNING: Removed duplicated region for block: B:63:0x00f6  */
    /* JADX WARNING: Removed duplicated region for block: B:65:0x00fb  */
    /* JADX WARNING: Removed duplicated region for block: B:75:0x010c  */
    /* JADX WARNING: Removed duplicated region for block: B:77:0x0111  */
    /* JADX WARNING: Removed duplicated region for block: B:83:0x0128  */
    /* JADX WARNING: Removed duplicated region for block: B:85:0x012d  */
    /* JADX WARNING: Removed duplicated region for block: B:90:0x013a  */
    /* JADX WARNING: Removed duplicated region for block: B:92:0x013f  */
    /* JADX WARNING: Unknown variable types count: 19 */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private final boolean zza(int r18, byte[] r19) {
        /*
            r17 = this;
            r1 = r17
            java.lang.String r2 = "Error writing entry to local database"
            r17.zzgg()
            r17.zzaf()
            boolean r0 = r1.zzalp
            r3 = 0
            if (r0 == 0) goto L_0x0010
            return r3
        L_0x0010:
            android.content.ContentValues r4 = new android.content.ContentValues
            r4.<init>()
            java.lang.Integer r0 = java.lang.Integer.valueOf(r18)
            java.lang.String r5 = "type"
            r4.put(r5, r0)
            java.lang.String r0 = "entry"
            r5 = r19
            r4.put(r0, r5)
            r5 = 5
            r6 = 0
            r7 = 5
        L_0x0028:
            if (r6 >= r5) goto L_0x0143
            r8 = 0
            r9 = 1
            android.database.sqlite.SQLiteDatabase r10 = r17.getWritableDatabase()     // Catch:{ SQLiteFullException -> 0x0115, SQLiteDatabaseLockedException -> 0x0102, SQLiteException -> 0x00d8, all -> 0x00d2 }
            if (r10 != 0) goto L_0x0041
            r1.zzalp = r9     // Catch:{ SQLiteFullException -> 0x003e, SQLiteDatabaseLockedException -> 0x00cc, SQLiteException -> 0x003a }
            if (r10 == 0) goto L_0x0039
            r10.close()
        L_0x0039:
            return r3
        L_0x003a:
            r0 = move-exception
            r13 = r8
            goto L_0x00ca
        L_0x003e:
            r0 = move-exception
            goto L_0x0118
        L_0x0041:
            r10.beginTransaction()     // Catch:{ SQLiteFullException -> 0x00cf, SQLiteDatabaseLockedException -> 0x00cc, SQLiteException -> 0x00c7, all -> 0x00c2 }
            r11 = 0
            java.lang.String r0 = "select count(1) from messages"
            android.database.Cursor r13 = r10.rawQuery(r0, r8)     // Catch:{ SQLiteFullException -> 0x00cf, SQLiteDatabaseLockedException -> 0x00cc, SQLiteException -> 0x00c7, all -> 0x00c2 }
            if (r13 == 0) goto L_0x0063
            boolean r0 = r13.moveToFirst()     // Catch:{ SQLiteFullException -> 0x005f, SQLiteDatabaseLockedException -> 0x00c0, SQLiteException -> 0x005c, all -> 0x0059 }
            if (r0 == 0) goto L_0x0063
            long r11 = r13.getLong(r3)     // Catch:{ SQLiteFullException -> 0x005f, SQLiteDatabaseLockedException -> 0x00c0, SQLiteException -> 0x005c, all -> 0x0059 }
            goto L_0x0063
        L_0x0059:
            r0 = move-exception
            goto L_0x0138
        L_0x005c:
            r0 = move-exception
            goto L_0x00ca
        L_0x005f:
            r0 = move-exception
            r8 = r13
            goto L_0x0118
        L_0x0063:
            java.lang.String r0 = "messages"
            r14 = 100000(0x186a0, double:4.94066E-319)
            int r16 = (r11 > r14 ? 1 : (r11 == r14 ? 0 : -1))
            if (r16 < 0) goto L_0x00aa
            com.google.android.gms.measurement.internal.zzas r16 = r17.zzgt()     // Catch:{ SQLiteFullException -> 0x005f, SQLiteDatabaseLockedException -> 0x00c0, SQLiteException -> 0x005c, all -> 0x0059 }
            com.google.android.gms.measurement.internal.zzau r5 = r16.zzjg()     // Catch:{ SQLiteFullException -> 0x005f, SQLiteDatabaseLockedException -> 0x00c0, SQLiteException -> 0x005c, all -> 0x0059 }
            java.lang.String r8 = "Data loss, local db full"
            r5.zzby(r8)     // Catch:{ SQLiteFullException -> 0x005f, SQLiteDatabaseLockedException -> 0x00c0, SQLiteException -> 0x005c, all -> 0x0059 }
            long r14 = r14 - r11
            r11 = 1
            long r14 = r14 + r11
            java.lang.String r5 = "rowid in (select rowid from messages order by rowid asc limit ?)"
            java.lang.String[] r8 = new java.lang.String[r9]     // Catch:{ SQLiteFullException -> 0x005f, SQLiteDatabaseLockedException -> 0x00c0, SQLiteException -> 0x005c, all -> 0x0059 }
            java.lang.String r11 = java.lang.Long.toString(r14)     // Catch:{ SQLiteFullException -> 0x005f, SQLiteDatabaseLockedException -> 0x00c0, SQLiteException -> 0x005c, all -> 0x0059 }
            r8[r3] = r11     // Catch:{ SQLiteFullException -> 0x005f, SQLiteDatabaseLockedException -> 0x00c0, SQLiteException -> 0x005c, all -> 0x0059 }
            int r5 = r10.delete(r0, r5, r8)     // Catch:{ SQLiteFullException -> 0x005f, SQLiteDatabaseLockedException -> 0x00c0, SQLiteException -> 0x005c, all -> 0x0059 }
            long r11 = (long) r5     // Catch:{ SQLiteFullException -> 0x005f, SQLiteDatabaseLockedException -> 0x00c0, SQLiteException -> 0x005c, all -> 0x0059 }
            int r5 = (r11 > r14 ? 1 : (r11 == r14 ? 0 : -1))
            if (r5 == 0) goto L_0x00aa
            com.google.android.gms.measurement.internal.zzas r5 = r17.zzgt()     // Catch:{ SQLiteFullException -> 0x005f, SQLiteDatabaseLockedException -> 0x00c0, SQLiteException -> 0x005c, all -> 0x0059 }
            com.google.android.gms.measurement.internal.zzau r5 = r5.zzjg()     // Catch:{ SQLiteFullException -> 0x005f, SQLiteDatabaseLockedException -> 0x00c0, SQLiteException -> 0x005c, all -> 0x0059 }
            java.lang.String r8 = "Different delete count than expected in local db. expected, received, difference"
            java.lang.Long r3 = java.lang.Long.valueOf(r14)     // Catch:{ SQLiteFullException -> 0x005f, SQLiteDatabaseLockedException -> 0x00c0, SQLiteException -> 0x005c, all -> 0x0059 }
            java.lang.Long r9 = java.lang.Long.valueOf(r11)     // Catch:{ SQLiteFullException -> 0x005f, SQLiteDatabaseLockedException -> 0x00c0, SQLiteException -> 0x005c, all -> 0x0059 }
            long r14 = r14 - r11
            java.lang.Long r11 = java.lang.Long.valueOf(r14)     // Catch:{ SQLiteFullException -> 0x005f, SQLiteDatabaseLockedException -> 0x00c0, SQLiteException -> 0x005c, all -> 0x0059 }
            r5.zzd(r8, r3, r9, r11)     // Catch:{ SQLiteFullException -> 0x005f, SQLiteDatabaseLockedException -> 0x00c0, SQLiteException -> 0x005c, all -> 0x0059 }
        L_0x00aa:
            r3 = 0
            r10.insertOrThrow(r0, r3, r4)     // Catch:{ SQLiteFullException -> 0x005f, SQLiteDatabaseLockedException -> 0x00c0, SQLiteException -> 0x005c, all -> 0x0059 }
            r10.setTransactionSuccessful()     // Catch:{ SQLiteFullException -> 0x005f, SQLiteDatabaseLockedException -> 0x00c0, SQLiteException -> 0x005c, all -> 0x0059 }
            r10.endTransaction()     // Catch:{ SQLiteFullException -> 0x005f, SQLiteDatabaseLockedException -> 0x00c0, SQLiteException -> 0x005c, all -> 0x0059 }
            if (r13 == 0) goto L_0x00b9
            r13.close()
        L_0x00b9:
            if (r10 == 0) goto L_0x00be
            r10.close()
        L_0x00be:
            r2 = 1
            return r2
        L_0x00c0:
            r8 = r13
            goto L_0x0104
        L_0x00c2:
            r0 = move-exception
            r3 = r8
            r13 = r3
            goto L_0x0138
        L_0x00c7:
            r0 = move-exception
            r3 = r8
            r13 = r3
        L_0x00ca:
            r8 = r10
            goto L_0x00db
        L_0x00cc:
            r3 = r8
            r8 = r3
            goto L_0x0104
        L_0x00cf:
            r0 = move-exception
            r3 = r8
            goto L_0x0118
        L_0x00d2:
            r0 = move-exception
            r3 = r8
            r10 = r3
            r13 = r10
            goto L_0x0138
        L_0x00d8:
            r0 = move-exception
            r3 = r8
            r13 = r8
        L_0x00db:
            if (r8 == 0) goto L_0x00e6
            boolean r3 = r8.inTransaction()     // Catch:{ all -> 0x00ff }
            if (r3 == 0) goto L_0x00e6
            r8.endTransaction()     // Catch:{ all -> 0x00ff }
        L_0x00e6:
            com.google.android.gms.measurement.internal.zzas r3 = r17.zzgt()     // Catch:{ all -> 0x00ff }
            com.google.android.gms.measurement.internal.zzau r3 = r3.zzjg()     // Catch:{ all -> 0x00ff }
            r3.zzg(r2, r0)     // Catch:{ all -> 0x00ff }
            r3 = 1
            r1.zzalp = r3     // Catch:{ all -> 0x00ff }
            if (r13 == 0) goto L_0x00f9
            r13.close()
        L_0x00f9:
            if (r8 == 0) goto L_0x0130
            r8.close()
            goto L_0x0130
        L_0x00ff:
            r0 = move-exception
            r10 = r8
            goto L_0x0138
        L_0x0102:
            r3 = r8
            r10 = r8
        L_0x0104:
            long r11 = (long) r7
            android.os.SystemClock.sleep(r11)     // Catch:{ all -> 0x0136 }
            int r7 = r7 + 20
            if (r8 == 0) goto L_0x010f
            r8.close()
        L_0x010f:
            if (r10 == 0) goto L_0x0130
            r10.close()
            goto L_0x0130
        L_0x0115:
            r0 = move-exception
            r3 = r8
            r10 = r8
        L_0x0118:
            com.google.android.gms.measurement.internal.zzas r3 = r17.zzgt()     // Catch:{ all -> 0x0136 }
            com.google.android.gms.measurement.internal.zzau r3 = r3.zzjg()     // Catch:{ all -> 0x0136 }
            r3.zzg(r2, r0)     // Catch:{ all -> 0x0136 }
            r3 = 1
            r1.zzalp = r3     // Catch:{ all -> 0x0136 }
            if (r8 == 0) goto L_0x012b
            r8.close()
        L_0x012b:
            if (r10 == 0) goto L_0x0130
            r10.close()
        L_0x0130:
            int r6 = r6 + 1
            r3 = 0
            r5 = 5
            goto L_0x0028
        L_0x0136:
            r0 = move-exception
            r13 = r8
        L_0x0138:
            if (r13 == 0) goto L_0x013d
            r13.close()
        L_0x013d:
            if (r10 == 0) goto L_0x0142
            r10.close()
        L_0x0142:
            throw r0
        L_0x0143:
            com.google.android.gms.measurement.internal.zzas r0 = r17.zzgt()
            com.google.android.gms.measurement.internal.zzau r0 = r0.zzjj()
            java.lang.String r2 = "Failed to write entry to local database"
            r0.zzby(r2)
            r2 = 0
            return r2
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.measurement.internal.zzao.zza(int, byte[]):boolean");
    }

    public final boolean zza(zzag zzag) {
        Parcel obtain = Parcel.obtain();
        zzag.writeToParcel(obtain, 0);
        byte[] marshall = obtain.marshall();
        obtain.recycle();
        if (marshall.length <= 131072) {
            return zza(0, marshall);
        }
        zzgt().zzjj().zzby("Event is too long for local database. Sending event directly to service");
        return false;
    }

    public final boolean zza(zzfu zzfu) {
        Parcel obtain = Parcel.obtain();
        zzfu.writeToParcel(obtain, 0);
        byte[] marshall = obtain.marshall();
        obtain.recycle();
        if (marshall.length <= 131072) {
            return zza(1, marshall);
        }
        zzgt().zzjj().zzby("User property too long for local database. Sending directly to service");
        return false;
    }

    public final boolean zzc(zzo zzo) {
        zzgr();
        byte[] zza = zzfx.zza((Parcelable) zzo);
        if (zza.length <= 131072) {
            return zza(2, zza);
        }
        zzgt().zzjj().zzby("Conditional user property too long for local database. Sending directly to service");
        return false;
    }

    /* JADX WARNING: type inference failed for: r3v0 */
    /* JADX WARNING: type inference failed for: r3v1, types: [java.util.List<com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable>] */
    /* JADX WARNING: type inference failed for: r10v0, types: [android.database.Cursor] */
    /* JADX WARNING: type inference failed for: r3v3 */
    /* JADX WARNING: type inference failed for: r3v4, types: [android.database.Cursor] */
    /* JADX WARNING: type inference failed for: r10v1 */
    /* JADX WARNING: type inference failed for: r3v6 */
    /* JADX WARNING: type inference failed for: r3v7, types: [android.database.Cursor] */
    /* JADX WARNING: type inference failed for: r10v2 */
    /* JADX WARNING: type inference failed for: r3v9 */
    /* JADX WARNING: type inference failed for: r10v3, types: [android.database.Cursor] */
    /* JADX WARNING: type inference failed for: r10v4 */
    /* JADX WARNING: type inference failed for: r10v5 */
    /* JADX WARNING: type inference failed for: r3v16 */
    /* JADX WARNING: type inference failed for: r3v18 */
    /* JADX WARNING: type inference failed for: r3v23 */
    /* JADX WARNING: type inference failed for: r3v24 */
    /* JADX WARNING: type inference failed for: r10v9 */
    /* JADX WARNING: type inference failed for: r3v26 */
    /* JADX WARNING: type inference failed for: r3v27 */
    /* JADX WARNING: type inference failed for: r3v28 */
    /* JADX WARNING: type inference failed for: r3v29 */
    /* JADX WARNING: type inference failed for: r3v30 */
    /* JADX WARNING: type inference failed for: r10v10 */
    /* JADX WARNING: type inference failed for: r10v11 */
    /* JADX WARNING: type inference failed for: r10v12 */
    /* JADX WARNING: type inference failed for: r3v31 */
    /* JADX WARNING: type inference failed for: r3v32 */
    /* JADX WARNING: type inference failed for: r10v13 */
    /* JADX WARNING: Can't wrap try/catch for region: R(4:54|55|56|57) */
    /* JADX WARNING: Can't wrap try/catch for region: R(4:69|70|71|72) */
    /* JADX WARNING: Can't wrap try/catch for region: R(5:39|40|41|42|166) */
    /* JADX WARNING: Code restructure failed: missing block: B:100:0x0184, code lost:
        r3 = r15;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:104:0x0188, code lost:
        r0 = e;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:105:0x0189, code lost:
        r3 = r15;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:40:?, code lost:
        zzgt().zzjg().zzby("Failed to load event from local database");
     */
    /* JADX WARNING: Code restructure failed: missing block: B:42:?, code lost:
        r14.recycle();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:55:?, code lost:
        zzgt().zzjg().zzby(r14);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:57:?, code lost:
        r15.recycle();
        r0 = null;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:70:?, code lost:
        zzgt().zzjg().zzby(r14);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:72:?, code lost:
        r15.recycle();
        r0 = null;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:96:0x017e, code lost:
        r0 = th;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:97:0x017f, code lost:
        r3 = r15;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:98:0x0181, code lost:
        r0 = e;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:99:0x0182, code lost:
        r3 = r15;
     */
    /* JADX WARNING: Failed to process nested try/catch */
    /* JADX WARNING: Missing exception handler attribute for start block: B:39:0x00aa */
    /* JADX WARNING: Missing exception handler attribute for start block: B:54:0x00dc */
    /* JADX WARNING: Missing exception handler attribute for start block: B:69:0x0110 */
    /* JADX WARNING: Multi-variable type inference failed */
    /* JADX WARNING: Removed duplicated region for block: B:101:? A[ExcHandler: SQLiteDatabaseLockedException (unused android.database.sqlite.SQLiteDatabaseLockedException), SYNTHETIC, Splitter:B:12:0x0033] */
    /* JADX WARNING: Removed duplicated region for block: B:114:0x0195 A[SYNTHETIC, Splitter:B:114:0x0195] */
    /* JADX WARNING: Removed duplicated region for block: B:120:0x01ad  */
    /* JADX WARNING: Removed duplicated region for block: B:122:0x01b2  */
    /* JADX WARNING: Removed duplicated region for block: B:131:0x01c2  */
    /* JADX WARNING: Removed duplicated region for block: B:133:0x01c7  */
    /* JADX WARNING: Removed duplicated region for block: B:141:0x01e1  */
    /* JADX WARNING: Removed duplicated region for block: B:143:0x01e6  */
    /* JADX WARNING: Removed duplicated region for block: B:148:0x01f4  */
    /* JADX WARNING: Removed duplicated region for block: B:150:0x01f9  */
    /* JADX WARNING: Removed duplicated region for block: B:158:0x01e9 A[SYNTHETIC] */
    /* JADX WARNING: Removed duplicated region for block: B:160:0x01e9 A[SYNTHETIC] */
    /* JADX WARNING: Removed duplicated region for block: B:162:0x01e9 A[SYNTHETIC] */
    /* JADX WARNING: Unknown variable types count: 13 */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final java.util.List<com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable> zzr(int r21) {
        /*
            r20 = this;
            r1 = r20
            java.lang.String r2 = "Error reading entries from local database"
            r20.zzaf()
            r20.zzgg()
            boolean r0 = r1.zzalp
            r3 = 0
            if (r0 == 0) goto L_0x0010
            return r3
        L_0x0010:
            java.util.ArrayList r4 = new java.util.ArrayList
            r4.<init>()
            android.content.Context r0 = r20.getContext()
            java.lang.String r5 = "google_app_measurement_local.db"
            java.io.File r0 = r0.getDatabasePath(r5)
            boolean r0 = r0.exists()
            if (r0 != 0) goto L_0x0026
            return r4
        L_0x0026:
            r5 = 5
            r6 = 0
            r7 = 0
            r8 = 5
        L_0x002a:
            if (r7 >= r5) goto L_0x01fd
            r9 = 1
            android.database.sqlite.SQLiteDatabase r15 = r20.getWritableDatabase()     // Catch:{ SQLiteFullException -> 0x01cf, SQLiteDatabaseLockedException -> 0x01b8, SQLiteException -> 0x0190, all -> 0x018c }
            if (r15 != 0) goto L_0x0043
            r1.zzalp = r9     // Catch:{ SQLiteFullException -> 0x0040, SQLiteDatabaseLockedException -> 0x0184, SQLiteException -> 0x003b }
            if (r15 == 0) goto L_0x003a
            r15.close()
        L_0x003a:
            return r3
        L_0x003b:
            r0 = move-exception
            r10 = r3
            r3 = r15
            goto L_0x0193
        L_0x0040:
            r0 = move-exception
            goto L_0x01d2
        L_0x0043:
            r15.beginTransaction()     // Catch:{ SQLiteFullException -> 0x0188, SQLiteDatabaseLockedException -> 0x0184, SQLiteException -> 0x0181, all -> 0x017e }
            java.lang.String r11 = "messages"
            r0 = 3
            java.lang.String[] r12 = new java.lang.String[r0]     // Catch:{ SQLiteFullException -> 0x0188, SQLiteDatabaseLockedException -> 0x0184, SQLiteException -> 0x0181, all -> 0x017e }
            java.lang.String r0 = "rowid"
            r12[r6] = r0     // Catch:{ SQLiteFullException -> 0x0188, SQLiteDatabaseLockedException -> 0x0184, SQLiteException -> 0x0181, all -> 0x017e }
            java.lang.String r0 = "type"
            r12[r9] = r0     // Catch:{ SQLiteFullException -> 0x0188, SQLiteDatabaseLockedException -> 0x0184, SQLiteException -> 0x0181, all -> 0x017e }
            java.lang.String r0 = "entry"
            r14 = 2
            r12[r14] = r0     // Catch:{ SQLiteFullException -> 0x0188, SQLiteDatabaseLockedException -> 0x0184, SQLiteException -> 0x0181, all -> 0x017e }
            r13 = 0
            r0 = 0
            r16 = 0
            r17 = 0
            java.lang.String r18 = "rowid asc"
            r10 = 100
            java.lang.String r19 = java.lang.Integer.toString(r10)     // Catch:{ SQLiteFullException -> 0x0188, SQLiteDatabaseLockedException -> 0x0184, SQLiteException -> 0x0181, all -> 0x017e }
            r10 = r15
            r5 = 2
            r14 = r0
            r3 = r15
            r15 = r16
            r16 = r17
            r17 = r18
            r18 = r19
            android.database.Cursor r10 = r10.query(r11, r12, r13, r14, r15, r16, r17, r18)     // Catch:{ SQLiteFullException -> 0x017b, SQLiteDatabaseLockedException -> 0x0185, SQLiteException -> 0x0179, all -> 0x0177 }
            r11 = -1
        L_0x0078:
            boolean r0 = r10.moveToNext()     // Catch:{ SQLiteFullException -> 0x0172, SQLiteDatabaseLockedException -> 0x016f, SQLiteException -> 0x016d }
            if (r0 == 0) goto L_0x0139
            long r11 = r10.getLong(r6)     // Catch:{ SQLiteFullException -> 0x0172, SQLiteDatabaseLockedException -> 0x016f, SQLiteException -> 0x016d }
            int r0 = r10.getInt(r9)     // Catch:{ SQLiteFullException -> 0x0172, SQLiteDatabaseLockedException -> 0x016f, SQLiteException -> 0x016d }
            byte[] r13 = r10.getBlob(r5)     // Catch:{ SQLiteFullException -> 0x0172, SQLiteDatabaseLockedException -> 0x016f, SQLiteException -> 0x016d }
            if (r0 != 0) goto L_0x00bf
            android.os.Parcel r14 = android.os.Parcel.obtain()     // Catch:{ SQLiteFullException -> 0x0172, SQLiteDatabaseLockedException -> 0x016f, SQLiteException -> 0x016d }
            int r0 = r13.length     // Catch:{ ParseException -> 0x00aa }
            r14.unmarshall(r13, r6, r0)     // Catch:{ ParseException -> 0x00aa }
            r14.setDataPosition(r6)     // Catch:{ ParseException -> 0x00aa }
            android.os.Parcelable$Creator<com.google.android.gms.measurement.internal.zzag> r0 = com.google.android.gms.measurement.internal.zzag.CREATOR     // Catch:{ ParseException -> 0x00aa }
            java.lang.Object r0 = r0.createFromParcel(r14)     // Catch:{ ParseException -> 0x00aa }
            com.google.android.gms.measurement.internal.zzag r0 = (com.google.android.gms.measurement.internal.zzag) r0     // Catch:{ ParseException -> 0x00aa }
            r14.recycle()     // Catch:{ SQLiteFullException -> 0x0172, SQLiteDatabaseLockedException -> 0x016f, SQLiteException -> 0x016d }
            if (r0 == 0) goto L_0x0078
            r4.add(r0)     // Catch:{ SQLiteFullException -> 0x0172, SQLiteDatabaseLockedException -> 0x016f, SQLiteException -> 0x016d }
            goto L_0x0078
        L_0x00a8:
            r0 = move-exception
            goto L_0x00bb
        L_0x00aa:
            com.google.android.gms.measurement.internal.zzas r0 = r20.zzgt()     // Catch:{ all -> 0x00a8 }
            com.google.android.gms.measurement.internal.zzau r0 = r0.zzjg()     // Catch:{ all -> 0x00a8 }
            java.lang.String r13 = "Failed to load event from local database"
            r0.zzby(r13)     // Catch:{ all -> 0x00a8 }
            r14.recycle()     // Catch:{ SQLiteFullException -> 0x0172, SQLiteDatabaseLockedException -> 0x016f, SQLiteException -> 0x016d }
            goto L_0x0078
        L_0x00bb:
            r14.recycle()     // Catch:{ SQLiteFullException -> 0x0172, SQLiteDatabaseLockedException -> 0x016f, SQLiteException -> 0x016d }
            throw r0     // Catch:{ SQLiteFullException -> 0x0172, SQLiteDatabaseLockedException -> 0x016f, SQLiteException -> 0x016d }
        L_0x00bf:
            java.lang.String r14 = "Failed to load user property from local database"
            if (r0 != r9) goto L_0x00f5
            android.os.Parcel r15 = android.os.Parcel.obtain()     // Catch:{ SQLiteFullException -> 0x0172, SQLiteDatabaseLockedException -> 0x016f, SQLiteException -> 0x016d }
            int r0 = r13.length     // Catch:{ ParseException -> 0x00dc }
            r15.unmarshall(r13, r6, r0)     // Catch:{ ParseException -> 0x00dc }
            r15.setDataPosition(r6)     // Catch:{ ParseException -> 0x00dc }
            android.os.Parcelable$Creator<com.google.android.gms.measurement.internal.zzfu> r0 = com.google.android.gms.measurement.internal.zzfu.CREATOR     // Catch:{ ParseException -> 0x00dc }
            java.lang.Object r0 = r0.createFromParcel(r15)     // Catch:{ ParseException -> 0x00dc }
            com.google.android.gms.measurement.internal.zzfu r0 = (com.google.android.gms.measurement.internal.zzfu) r0     // Catch:{ ParseException -> 0x00dc }
            r15.recycle()     // Catch:{ SQLiteFullException -> 0x0172, SQLiteDatabaseLockedException -> 0x016f, SQLiteException -> 0x016d }
            goto L_0x00eb
        L_0x00da:
            r0 = move-exception
            goto L_0x00f1
        L_0x00dc:
            com.google.android.gms.measurement.internal.zzas r0 = r20.zzgt()     // Catch:{ all -> 0x00da }
            com.google.android.gms.measurement.internal.zzau r0 = r0.zzjg()     // Catch:{ all -> 0x00da }
            r0.zzby(r14)     // Catch:{ all -> 0x00da }
            r15.recycle()     // Catch:{ SQLiteFullException -> 0x0172, SQLiteDatabaseLockedException -> 0x016f, SQLiteException -> 0x016d }
            r0 = 0
        L_0x00eb:
            if (r0 == 0) goto L_0x0078
            r4.add(r0)     // Catch:{ SQLiteFullException -> 0x0172, SQLiteDatabaseLockedException -> 0x016f, SQLiteException -> 0x016d }
            goto L_0x0078
        L_0x00f1:
            r15.recycle()     // Catch:{ SQLiteFullException -> 0x0172, SQLiteDatabaseLockedException -> 0x016f, SQLiteException -> 0x016d }
            throw r0     // Catch:{ SQLiteFullException -> 0x0172, SQLiteDatabaseLockedException -> 0x016f, SQLiteException -> 0x016d }
        L_0x00f5:
            if (r0 != r5) goto L_0x012a
            android.os.Parcel r15 = android.os.Parcel.obtain()     // Catch:{ SQLiteFullException -> 0x0172, SQLiteDatabaseLockedException -> 0x016f, SQLiteException -> 0x016d }
            int r0 = r13.length     // Catch:{ ParseException -> 0x0110 }
            r15.unmarshall(r13, r6, r0)     // Catch:{ ParseException -> 0x0110 }
            r15.setDataPosition(r6)     // Catch:{ ParseException -> 0x0110 }
            android.os.Parcelable$Creator<com.google.android.gms.measurement.internal.zzo> r0 = com.google.android.gms.measurement.internal.zzo.CREATOR     // Catch:{ ParseException -> 0x0110 }
            java.lang.Object r0 = r0.createFromParcel(r15)     // Catch:{ ParseException -> 0x0110 }
            com.google.android.gms.measurement.internal.zzo r0 = (com.google.android.gms.measurement.internal.zzo) r0     // Catch:{ ParseException -> 0x0110 }
            r15.recycle()     // Catch:{ SQLiteFullException -> 0x0172, SQLiteDatabaseLockedException -> 0x016f, SQLiteException -> 0x016d }
            goto L_0x011f
        L_0x010e:
            r0 = move-exception
            goto L_0x0126
        L_0x0110:
            com.google.android.gms.measurement.internal.zzas r0 = r20.zzgt()     // Catch:{ all -> 0x010e }
            com.google.android.gms.measurement.internal.zzau r0 = r0.zzjg()     // Catch:{ all -> 0x010e }
            r0.zzby(r14)     // Catch:{ all -> 0x010e }
            r15.recycle()     // Catch:{ SQLiteFullException -> 0x0172, SQLiteDatabaseLockedException -> 0x016f, SQLiteException -> 0x016d }
            r0 = 0
        L_0x011f:
            if (r0 == 0) goto L_0x0078
            r4.add(r0)     // Catch:{ SQLiteFullException -> 0x0172, SQLiteDatabaseLockedException -> 0x016f, SQLiteException -> 0x016d }
            goto L_0x0078
        L_0x0126:
            r15.recycle()     // Catch:{ SQLiteFullException -> 0x0172, SQLiteDatabaseLockedException -> 0x016f, SQLiteException -> 0x016d }
            throw r0     // Catch:{ SQLiteFullException -> 0x0172, SQLiteDatabaseLockedException -> 0x016f, SQLiteException -> 0x016d }
        L_0x012a:
            com.google.android.gms.measurement.internal.zzas r0 = r20.zzgt()     // Catch:{ SQLiteFullException -> 0x0172, SQLiteDatabaseLockedException -> 0x016f, SQLiteException -> 0x016d }
            com.google.android.gms.measurement.internal.zzau r0 = r0.zzjg()     // Catch:{ SQLiteFullException -> 0x0172, SQLiteDatabaseLockedException -> 0x016f, SQLiteException -> 0x016d }
            java.lang.String r13 = "Unknown record type in local database"
            r0.zzby(r13)     // Catch:{ SQLiteFullException -> 0x0172, SQLiteDatabaseLockedException -> 0x016f, SQLiteException -> 0x016d }
            goto L_0x0078
        L_0x0139:
            java.lang.String r0 = "messages"
            java.lang.String r5 = "rowid <= ?"
            java.lang.String[] r13 = new java.lang.String[r9]     // Catch:{ SQLiteFullException -> 0x0172, SQLiteDatabaseLockedException -> 0x016f, SQLiteException -> 0x016d }
            java.lang.String r11 = java.lang.Long.toString(r11)     // Catch:{ SQLiteFullException -> 0x0172, SQLiteDatabaseLockedException -> 0x016f, SQLiteException -> 0x016d }
            r13[r6] = r11     // Catch:{ SQLiteFullException -> 0x0172, SQLiteDatabaseLockedException -> 0x016f, SQLiteException -> 0x016d }
            int r0 = r3.delete(r0, r5, r13)     // Catch:{ SQLiteFullException -> 0x0172, SQLiteDatabaseLockedException -> 0x016f, SQLiteException -> 0x016d }
            int r5 = r4.size()     // Catch:{ SQLiteFullException -> 0x0172, SQLiteDatabaseLockedException -> 0x016f, SQLiteException -> 0x016d }
            if (r0 >= r5) goto L_0x015c
            com.google.android.gms.measurement.internal.zzas r0 = r20.zzgt()     // Catch:{ SQLiteFullException -> 0x0172, SQLiteDatabaseLockedException -> 0x016f, SQLiteException -> 0x016d }
            com.google.android.gms.measurement.internal.zzau r0 = r0.zzjg()     // Catch:{ SQLiteFullException -> 0x0172, SQLiteDatabaseLockedException -> 0x016f, SQLiteException -> 0x016d }
            java.lang.String r5 = "Fewer entries removed from local database than expected"
            r0.zzby(r5)     // Catch:{ SQLiteFullException -> 0x0172, SQLiteDatabaseLockedException -> 0x016f, SQLiteException -> 0x016d }
        L_0x015c:
            r3.setTransactionSuccessful()     // Catch:{ SQLiteFullException -> 0x0172, SQLiteDatabaseLockedException -> 0x016f, SQLiteException -> 0x016d }
            r3.endTransaction()     // Catch:{ SQLiteFullException -> 0x0172, SQLiteDatabaseLockedException -> 0x016f, SQLiteException -> 0x016d }
            if (r10 == 0) goto L_0x0167
            r10.close()
        L_0x0167:
            if (r3 == 0) goto L_0x016c
            r3.close()
        L_0x016c:
            return r4
        L_0x016d:
            r0 = move-exception
            goto L_0x0193
        L_0x016f:
            r5 = r3
            r3 = r10
            goto L_0x01ba
        L_0x0172:
            r0 = move-exception
            r15 = r3
            r3 = r10
            goto L_0x01d2
        L_0x0177:
            r0 = move-exception
            goto L_0x018e
        L_0x0179:
            r0 = move-exception
            goto L_0x0192
        L_0x017b:
            r0 = move-exception
            r15 = r3
            goto L_0x018a
        L_0x017e:
            r0 = move-exception
            r3 = r15
            goto L_0x018e
        L_0x0181:
            r0 = move-exception
            r3 = r15
            goto L_0x0192
        L_0x0184:
            r3 = r15
        L_0x0185:
            r5 = r3
            r3 = 0
            goto L_0x01ba
        L_0x0188:
            r0 = move-exception
            r3 = r15
        L_0x018a:
            r3 = 0
            goto L_0x01d2
        L_0x018c:
            r0 = move-exception
            r3 = 0
        L_0x018e:
            r10 = 0
            goto L_0x01f2
        L_0x0190:
            r0 = move-exception
            r3 = 0
        L_0x0192:
            r10 = 0
        L_0x0193:
            if (r3 == 0) goto L_0x019e
            boolean r5 = r3.inTransaction()     // Catch:{ all -> 0x01b6 }
            if (r5 == 0) goto L_0x019e
            r3.endTransaction()     // Catch:{ all -> 0x01b6 }
        L_0x019e:
            com.google.android.gms.measurement.internal.zzas r5 = r20.zzgt()     // Catch:{ all -> 0x01b6 }
            com.google.android.gms.measurement.internal.zzau r5 = r5.zzjg()     // Catch:{ all -> 0x01b6 }
            r5.zzg(r2, r0)     // Catch:{ all -> 0x01b6 }
            r1.zzalp = r9     // Catch:{ all -> 0x01b6 }
            if (r10 == 0) goto L_0x01b0
            r10.close()
        L_0x01b0:
            if (r3 == 0) goto L_0x01e9
            r3.close()
            goto L_0x01e9
        L_0x01b6:
            r0 = move-exception
            goto L_0x01f2
        L_0x01b8:
            r3 = 0
            r5 = 0
        L_0x01ba:
            long r9 = (long) r8
            android.os.SystemClock.sleep(r9)     // Catch:{ all -> 0x01cb }
            int r8 = r8 + 20
            if (r3 == 0) goto L_0x01c5
            r3.close()
        L_0x01c5:
            if (r5 == 0) goto L_0x01e9
            r5.close()
            goto L_0x01e9
        L_0x01cb:
            r0 = move-exception
            r10 = r3
            r3 = r5
            goto L_0x01f2
        L_0x01cf:
            r0 = move-exception
            r3 = 0
            r15 = 0
        L_0x01d2:
            com.google.android.gms.measurement.internal.zzas r5 = r20.zzgt()     // Catch:{ all -> 0x01ef }
            com.google.android.gms.measurement.internal.zzau r5 = r5.zzjg()     // Catch:{ all -> 0x01ef }
            r5.zzg(r2, r0)     // Catch:{ all -> 0x01ef }
            r1.zzalp = r9     // Catch:{ all -> 0x01ef }
            if (r3 == 0) goto L_0x01e4
            r3.close()
        L_0x01e4:
            if (r15 == 0) goto L_0x01e9
            r15.close()
        L_0x01e9:
            int r7 = r7 + 1
            r3 = 0
            r5 = 5
            goto L_0x002a
        L_0x01ef:
            r0 = move-exception
            r10 = r3
            r3 = r15
        L_0x01f2:
            if (r10 == 0) goto L_0x01f7
            r10.close()
        L_0x01f7:
            if (r3 == 0) goto L_0x01fc
            r3.close()
        L_0x01fc:
            throw r0
        L_0x01fd:
            com.google.android.gms.measurement.internal.zzas r0 = r20.zzgt()
            com.google.android.gms.measurement.internal.zzau r0 = r0.zzjj()
            java.lang.String r2 = "Failed to read events from database in reasonable time"
            r0.zzby(r2)
            r2 = 0
            return r2
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.measurement.internal.zzao.zzr(int):java.util.List");
    }

    private final SQLiteDatabase getWritableDatabase() throws SQLiteException {
        if (this.zzalp) {
            return null;
        }
        SQLiteDatabase writableDatabase = this.zzalo.getWritableDatabase();
        if (writableDatabase != null) {
            return writableDatabase;
        }
        this.zzalp = true;
        return null;
    }

    public final /* bridge */ /* synthetic */ void zzgf() {
        super.zzgf();
    }

    public final /* bridge */ /* synthetic */ void zzgg() {
        super.zzgg();
    }

    public final /* bridge */ /* synthetic */ void zzgh() {
        super.zzgh();
    }

    public final /* bridge */ /* synthetic */ void zzaf() {
        super.zzaf();
    }

    public final /* bridge */ /* synthetic */ zza zzgi() {
        return super.zzgi();
    }

    public final /* bridge */ /* synthetic */ zzda zzgj() {
        return super.zzgj();
    }

    public final /* bridge */ /* synthetic */ zzam zzgk() {
        return super.zzgk();
    }

    public final /* bridge */ /* synthetic */ zzeb zzgl() {
        return super.zzgl();
    }

    public final /* bridge */ /* synthetic */ zzdy zzgm() {
        return super.zzgm();
    }

    public final /* bridge */ /* synthetic */ zzao zzgn() {
        return super.zzgn();
    }

    public final /* bridge */ /* synthetic */ zzfd zzgo() {
        return super.zzgo();
    }

    public final /* bridge */ /* synthetic */ zzaa zzgp() {
        return super.zzgp();
    }

    public final /* bridge */ /* synthetic */ Clock zzbx() {
        return super.zzbx();
    }

    public final /* bridge */ /* synthetic */ Context getContext() {
        return super.getContext();
    }

    public final /* bridge */ /* synthetic */ zzaq zzgq() {
        return super.zzgq();
    }

    public final /* bridge */ /* synthetic */ zzfx zzgr() {
        return super.zzgr();
    }

    public final /* bridge */ /* synthetic */ zzbr zzgs() {
        return super.zzgs();
    }

    public final /* bridge */ /* synthetic */ zzas zzgt() {
        return super.zzgt();
    }

    public final /* bridge */ /* synthetic */ zzbd zzgu() {
        return super.zzgu();
    }

    public final /* bridge */ /* synthetic */ zzq zzgv() {
        return super.zzgv();
    }

    public final /* bridge */ /* synthetic */ zzn zzgw() {
        return super.zzgw();
    }
}
