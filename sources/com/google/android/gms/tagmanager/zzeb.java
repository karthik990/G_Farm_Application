package com.google.android.gms.tagmanager;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.text.TextUtils;
import com.google.android.gms.common.util.Clock;
import com.google.android.gms.common.util.DefaultClock;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

final class zzeb implements zzcb {
    /* access modifiers changed from: private */
    public static final String zzxj = String.format("CREATE TABLE IF NOT EXISTS %s ( '%s' INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, '%s' INTEGER NOT NULL, '%s' TEXT NOT NULL,'%s' INTEGER NOT NULL);", new Object[]{"gtm_hits", "hit_id", "hit_time", "hit_url", "hit_first_send_time"});
    private final zzed zzaie;
    private volatile zzbe zzaif;
    private final zzcc zzaig;
    /* access modifiers changed from: private */
    public final String zzaih;
    private long zzaii;
    private final int zzaij;
    /* access modifiers changed from: private */
    public final Context zzrm;
    /* access modifiers changed from: private */
    public Clock zzsd;

    zzeb(zzcc zzcc, Context context) {
        this(zzcc, context, "gtm_urls.db", 2000);
    }

    private zzeb(zzcc zzcc, Context context, String str, int i) {
        this.zzrm = context.getApplicationContext();
        this.zzaih = str;
        this.zzaig = zzcc;
        this.zzsd = DefaultClock.getInstance();
        this.zzaie = new zzed(this, this.zzrm, this.zzaih);
        this.zzaif = new zzfu(this.zzrm, new zzec(this));
        this.zzaii = 0;
        this.zzaij = 2000;
    }

    public final void zzb(long j, String str) {
        long currentTimeMillis = this.zzsd.currentTimeMillis();
        String str2 = "gtm_hits";
        if (currentTimeMillis > this.zzaii + 86400000) {
            this.zzaii = currentTimeMillis;
            SQLiteDatabase zzau = zzau("Error opening database for deleteStaleHits.");
            if (zzau != null) {
                zzau.delete(str2, "HIT_TIME < ?", new String[]{Long.toString(this.zzsd.currentTimeMillis() - 2592000000L)});
                this.zzaig.zze(zziv() == 0);
            }
        }
        int zziv = (zziv() - this.zzaij) + 1;
        if (zziv > 0) {
            List zzz = zzz(zziv);
            int size = zzz.size();
            StringBuilder sb = new StringBuilder(51);
            sb.append("Store full, deleting ");
            sb.append(size);
            sb.append(" hits to make room.");
            zzdi.zzab(sb.toString());
            zza((String[]) zzz.toArray(new String[0]));
        }
        SQLiteDatabase zzau2 = zzau("Error opening database for putHit");
        if (zzau2 != null) {
            ContentValues contentValues = new ContentValues();
            contentValues.put("hit_time", Long.valueOf(j));
            contentValues.put("hit_url", str);
            contentValues.put("hit_first_send_time", Integer.valueOf(0));
            try {
                zzau2.insert(str2, null, contentValues);
                this.zzaig.zze(false);
            } catch (SQLiteException unused) {
                zzdi.zzac("Error storing hit");
            }
        }
    }

    private final List<String> zzz(int i) {
        String str = "hit_id";
        ArrayList arrayList = new ArrayList();
        if (i <= 0) {
            zzdi.zzac("Invalid maxHits specified. Skipping");
            return arrayList;
        }
        SQLiteDatabase zzau = zzau("Error opening database for peekHitIds.");
        if (zzau == null) {
            return arrayList;
        }
        Cursor cursor = null;
        String str2 = "gtm_hits";
        try {
            Cursor query = zzau.query(str2, new String[]{str}, null, null, null, null, String.format("%s ASC", new Object[]{str}), Integer.toString(i));
            if (query.moveToFirst()) {
                do {
                    arrayList.add(String.valueOf(query.getLong(0)));
                } while (query.moveToNext());
            }
            if (query != null) {
                query.close();
            }
        } catch (SQLiteException e) {
            String str3 = "Error in peekHits fetching hitIds: ";
            String valueOf = String.valueOf(e.getMessage());
            zzdi.zzac(valueOf.length() != 0 ? str3.concat(valueOf) : new String(str3));
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

    /* JADX WARNING: Code restructure failed: missing block: B:70:0x014b, code lost:
        r0 = th;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:71:0x014c, code lost:
        r14 = r13;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:72:0x014f, code lost:
        r0 = e;
     */
    /* JADX WARNING: Failed to process nested try/catch */
    /* JADX WARNING: Removed duplicated region for block: B:52:0x0106 A[Catch:{ all -> 0x0141 }] */
    /* JADX WARNING: Removed duplicated region for block: B:53:0x010b A[Catch:{ all -> 0x0141 }] */
    /* JADX WARNING: Removed duplicated region for block: B:56:0x0122 A[Catch:{ all -> 0x0141 }] */
    /* JADX WARNING: Removed duplicated region for block: B:62:0x013d  */
    /* JADX WARNING: Removed duplicated region for block: B:66:0x0144  */
    /* JADX WARNING: Removed duplicated region for block: B:70:0x014b A[ExcHandler: all (th java.lang.Throwable), Splitter:B:6:0x0043] */
    /* JADX WARNING: Removed duplicated region for block: B:82:0x0168 A[Catch:{ all -> 0x017b }] */
    /* JADX WARNING: Removed duplicated region for block: B:83:0x016d A[Catch:{ all -> 0x017b }] */
    /* JADX WARNING: Removed duplicated region for block: B:86:0x0177  */
    /* JADX WARNING: Removed duplicated region for block: B:90:0x017e  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private final java.util.List<com.google.android.gms.tagmanager.zzbw> zzaa(int r20) {
        /*
            r19 = this;
            java.lang.String r0 = "%s ASC"
            java.lang.String r1 = "hit_id"
            java.util.ArrayList r2 = new java.util.ArrayList
            r2.<init>()
            java.lang.String r3 = "Error opening database for peekHits"
            r4 = r19
            android.database.sqlite.SQLiteDatabase r3 = r4.zzau(r3)
            if (r3 != 0) goto L_0x0014
            return r2
        L_0x0014:
            java.lang.String r6 = "gtm_hits"
            r5 = 3
            java.lang.String[] r7 = new java.lang.String[r5]     // Catch:{ SQLiteException -> 0x0156, all -> 0x0153 }
            r15 = 0
            r7[r15] = r1     // Catch:{ SQLiteException -> 0x0156, all -> 0x0153 }
            java.lang.String r5 = "hit_time"
            r13 = 1
            r7[r13] = r5     // Catch:{ SQLiteException -> 0x0156, all -> 0x0153 }
            java.lang.String r5 = "hit_first_send_time"
            r12 = 2
            r7[r12] = r5     // Catch:{ SQLiteException -> 0x0156, all -> 0x0153 }
            r8 = 0
            r9 = 0
            r10 = 0
            r11 = 0
            java.lang.Object[] r5 = new java.lang.Object[r13]     // Catch:{ SQLiteException -> 0x0156, all -> 0x0153 }
            r5[r15] = r1     // Catch:{ SQLiteException -> 0x0156, all -> 0x0153 }
            java.lang.String r16 = java.lang.String.format(r0, r5)     // Catch:{ SQLiteException -> 0x0156, all -> 0x0153 }
            r17 = 40
            java.lang.String r18 = java.lang.Integer.toString(r17)     // Catch:{ SQLiteException -> 0x0156, all -> 0x0153 }
            r5 = r3
            r14 = 2
            r12 = r16
            r14 = 1
            r13 = r18
            android.database.Cursor r13 = r5.query(r6, r7, r8, r9, r10, r11, r12, r13)     // Catch:{ SQLiteException -> 0x0156, all -> 0x0153 }
            java.util.ArrayList r12 = new java.util.ArrayList     // Catch:{ SQLiteException -> 0x014f, all -> 0x014b }
            r12.<init>()     // Catch:{ SQLiteException -> 0x014f, all -> 0x014b }
            boolean r2 = r13.moveToFirst()     // Catch:{ SQLiteException -> 0x0148, all -> 0x014b }
            if (r2 == 0) goto L_0x0074
        L_0x004e:
            com.google.android.gms.tagmanager.zzbw r2 = new com.google.android.gms.tagmanager.zzbw     // Catch:{ SQLiteException -> 0x006f, all -> 0x006b }
            long r6 = r13.getLong(r15)     // Catch:{ SQLiteException -> 0x006f, all -> 0x006b }
            long r8 = r13.getLong(r14)     // Catch:{ SQLiteException -> 0x006f, all -> 0x006b }
            r5 = 2
            long r10 = r13.getLong(r5)     // Catch:{ SQLiteException -> 0x006f, all -> 0x006b }
            r5 = r2
            r5.<init>(r6, r8, r10)     // Catch:{ SQLiteException -> 0x006f, all -> 0x006b }
            r12.add(r2)     // Catch:{ SQLiteException -> 0x006f, all -> 0x006b }
            boolean r2 = r13.moveToNext()     // Catch:{ SQLiteException -> 0x006f, all -> 0x006b }
            if (r2 != 0) goto L_0x004e
            goto L_0x0074
        L_0x006b:
            r0 = move-exception
            r14 = r13
            goto L_0x017c
        L_0x006f:
            r0 = move-exception
            r2 = r12
            r14 = r13
            goto L_0x0158
        L_0x0074:
            if (r13 == 0) goto L_0x0079
            r13.close()
        L_0x0079:
            java.lang.String r6 = "gtm_hits"
            r2 = 2
            java.lang.String[] r7 = new java.lang.String[r2]     // Catch:{ SQLiteException -> 0x00f3, all -> 0x00f0 }
            r7[r15] = r1     // Catch:{ SQLiteException -> 0x00f3, all -> 0x00f0 }
            java.lang.String r2 = "hit_url"
            r7[r14] = r2     // Catch:{ SQLiteException -> 0x00f3, all -> 0x00f0 }
            r8 = 0
            r9 = 0
            r10 = 0
            r11 = 0
            java.lang.Object[] r2 = new java.lang.Object[r14]     // Catch:{ SQLiteException -> 0x00f3, all -> 0x00f0 }
            r2[r15] = r1     // Catch:{ SQLiteException -> 0x00f3, all -> 0x00f0 }
            java.lang.String r0 = java.lang.String.format(r0, r2)     // Catch:{ SQLiteException -> 0x00f3, all -> 0x00f0 }
            java.lang.String r1 = java.lang.Integer.toString(r17)     // Catch:{ SQLiteException -> 0x00f3, all -> 0x00f0 }
            r5 = r3
            r2 = r12
            r12 = r0
            r3 = r13
            r13 = r1
            android.database.Cursor r13 = r5.query(r6, r7, r8, r9, r10, r11, r12, r13)     // Catch:{ SQLiteException -> 0x00ed, all -> 0x00ea }
            boolean r0 = r13.moveToFirst()     // Catch:{ SQLiteException -> 0x00e8 }
            if (r0 == 0) goto L_0x00e2
            r0 = 0
        L_0x00a4:
            r1 = r13
            android.database.sqlite.SQLiteCursor r1 = (android.database.sqlite.SQLiteCursor) r1     // Catch:{ SQLiteException -> 0x00e8 }
            android.database.CursorWindow r1 = r1.getWindow()     // Catch:{ SQLiteException -> 0x00e8 }
            int r1 = r1.getNumRows()     // Catch:{ SQLiteException -> 0x00e8 }
            if (r1 <= 0) goto L_0x00bf
            java.lang.Object r1 = r2.get(r0)     // Catch:{ SQLiteException -> 0x00e8 }
            com.google.android.gms.tagmanager.zzbw r1 = (com.google.android.gms.tagmanager.zzbw) r1     // Catch:{ SQLiteException -> 0x00e8 }
            java.lang.String r3 = r13.getString(r14)     // Catch:{ SQLiteException -> 0x00e8 }
            r1.zzbc(r3)     // Catch:{ SQLiteException -> 0x00e8 }
            goto L_0x00da
        L_0x00bf:
            java.lang.String r1 = "HitString for hitId %d too large.  Hit will be deleted."
            java.lang.Object[] r3 = new java.lang.Object[r14]     // Catch:{ SQLiteException -> 0x00e8 }
            java.lang.Object r5 = r2.get(r0)     // Catch:{ SQLiteException -> 0x00e8 }
            com.google.android.gms.tagmanager.zzbw r5 = (com.google.android.gms.tagmanager.zzbw) r5     // Catch:{ SQLiteException -> 0x00e8 }
            long r5 = r5.zzih()     // Catch:{ SQLiteException -> 0x00e8 }
            java.lang.Long r5 = java.lang.Long.valueOf(r5)     // Catch:{ SQLiteException -> 0x00e8 }
            r3[r15] = r5     // Catch:{ SQLiteException -> 0x00e8 }
            java.lang.String r1 = java.lang.String.format(r1, r3)     // Catch:{ SQLiteException -> 0x00e8 }
            com.google.android.gms.tagmanager.zzdi.zzac(r1)     // Catch:{ SQLiteException -> 0x00e8 }
        L_0x00da:
            int r0 = r0 + 1
            boolean r1 = r13.moveToNext()     // Catch:{ SQLiteException -> 0x00e8 }
            if (r1 != 0) goto L_0x00a4
        L_0x00e2:
            if (r13 == 0) goto L_0x00e7
            r13.close()
        L_0x00e7:
            return r2
        L_0x00e8:
            r0 = move-exception
            goto L_0x00f6
        L_0x00ea:
            r0 = move-exception
            r13 = r3
            goto L_0x0142
        L_0x00ed:
            r0 = move-exception
            r13 = r3
            goto L_0x00f6
        L_0x00f0:
            r0 = move-exception
            r3 = r13
            goto L_0x0142
        L_0x00f3:
            r0 = move-exception
            r2 = r12
            r3 = r13
        L_0x00f6:
            java.lang.String r1 = "Error in peekHits fetching hit url: "
            java.lang.String r0 = r0.getMessage()     // Catch:{ all -> 0x0141 }
            java.lang.String r0 = java.lang.String.valueOf(r0)     // Catch:{ all -> 0x0141 }
            int r3 = r0.length()     // Catch:{ all -> 0x0141 }
            if (r3 == 0) goto L_0x010b
            java.lang.String r0 = r1.concat(r0)     // Catch:{ all -> 0x0141 }
            goto L_0x0110
        L_0x010b:
            java.lang.String r0 = new java.lang.String     // Catch:{ all -> 0x0141 }
            r0.<init>(r1)     // Catch:{ all -> 0x0141 }
        L_0x0110:
            com.google.android.gms.tagmanager.zzdi.zzac(r0)     // Catch:{ all -> 0x0141 }
            java.util.ArrayList r0 = new java.util.ArrayList     // Catch:{ all -> 0x0141 }
            r0.<init>()     // Catch:{ all -> 0x0141 }
            r12 = r2
            java.util.ArrayList r12 = (java.util.ArrayList) r12     // Catch:{ all -> 0x0141 }
            int r1 = r12.size()     // Catch:{ all -> 0x0141 }
            r2 = 0
        L_0x0120:
            if (r15 >= r1) goto L_0x013b
            java.lang.Object r3 = r12.get(r15)     // Catch:{ all -> 0x0141 }
            int r15 = r15 + 1
            com.google.android.gms.tagmanager.zzbw r3 = (com.google.android.gms.tagmanager.zzbw) r3     // Catch:{ all -> 0x0141 }
            java.lang.String r5 = r3.zzij()     // Catch:{ all -> 0x0141 }
            boolean r5 = android.text.TextUtils.isEmpty(r5)     // Catch:{ all -> 0x0141 }
            if (r5 == 0) goto L_0x0137
            if (r2 != 0) goto L_0x013b
            r2 = 1
        L_0x0137:
            r0.add(r3)     // Catch:{ all -> 0x0141 }
            goto L_0x0120
        L_0x013b:
            if (r13 == 0) goto L_0x0140
            r13.close()
        L_0x0140:
            return r0
        L_0x0141:
            r0 = move-exception
        L_0x0142:
            if (r13 == 0) goto L_0x0147
            r13.close()
        L_0x0147:
            throw r0
        L_0x0148:
            r0 = move-exception
            r2 = r12
            goto L_0x0150
        L_0x014b:
            r0 = move-exception
            r3 = r13
            r14 = r3
            goto L_0x017c
        L_0x014f:
            r0 = move-exception
        L_0x0150:
            r3 = r13
            r14 = r3
            goto L_0x0158
        L_0x0153:
            r0 = move-exception
            r14 = 0
            goto L_0x017c
        L_0x0156:
            r0 = move-exception
            r14 = 0
        L_0x0158:
            java.lang.String r1 = "Error in peekHits fetching hitIds: "
            java.lang.String r0 = r0.getMessage()     // Catch:{ all -> 0x017b }
            java.lang.String r0 = java.lang.String.valueOf(r0)     // Catch:{ all -> 0x017b }
            int r3 = r0.length()     // Catch:{ all -> 0x017b }
            if (r3 == 0) goto L_0x016d
            java.lang.String r0 = r1.concat(r0)     // Catch:{ all -> 0x017b }
            goto L_0x0172
        L_0x016d:
            java.lang.String r0 = new java.lang.String     // Catch:{ all -> 0x017b }
            r0.<init>(r1)     // Catch:{ all -> 0x017b }
        L_0x0172:
            com.google.android.gms.tagmanager.zzdi.zzac(r0)     // Catch:{ all -> 0x017b }
            if (r14 == 0) goto L_0x017a
            r14.close()
        L_0x017a:
            return r2
        L_0x017b:
            r0 = move-exception
        L_0x017c:
            if (r14 == 0) goto L_0x0181
            r14.close()
        L_0x0181:
            goto L_0x0183
        L_0x0182:
            throw r0
        L_0x0183:
            goto L_0x0182
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.tagmanager.zzeb.zzaa(int):java.util.List");
    }

    private final void zza(String[] strArr) {
        if (!(strArr == null || strArr.length == 0)) {
            SQLiteDatabase zzau = zzau("Error opening database for deleteHits.");
            if (zzau != null) {
                boolean z = true;
                try {
                    zzau.delete("gtm_hits", String.format("HIT_ID in (%s)", new Object[]{TextUtils.join(",", Collections.nCopies(strArr.length, "?"))}), strArr);
                    zzcc zzcc = this.zzaig;
                    if (zziv() != 0) {
                        z = false;
                    }
                    zzcc.zze(z);
                } catch (SQLiteException unused) {
                    zzdi.zzac("Error deleting hits");
                }
            }
        }
    }

    /* access modifiers changed from: private */
    public final void zze(long j) {
        zza(new String[]{String.valueOf(j)});
    }

    /* access modifiers changed from: private */
    public final void zzb(long j, long j2) {
        SQLiteDatabase zzau = zzau("Error opening database for getNumStoredHits.");
        if (zzau != null) {
            ContentValues contentValues = new ContentValues();
            contentValues.put("hit_first_send_time", Long.valueOf(j2));
            try {
                zzau.update("gtm_hits", contentValues, "hit_id=?", new String[]{String.valueOf(j)});
            } catch (SQLiteException unused) {
                StringBuilder sb = new StringBuilder(69);
                sb.append("Error setting HIT_FIRST_DISPATCH_TIME for hitId: ");
                sb.append(j);
                zzdi.zzac(sb.toString());
                zze(j);
            }
        }
    }

    /* JADX WARNING: type inference failed for: r2v0, types: [java.lang.String[], android.database.Cursor] */
    /* JADX WARNING: Multi-variable type inference failed. Error: jadx.core.utils.exceptions.JadxRuntimeException: No candidate types for var: r2v0, types: [java.lang.String[], android.database.Cursor]
      assigns: [?[int, float, boolean, short, byte, char, OBJECT, ARRAY]]
      uses: [?[int, boolean, OBJECT, ARRAY, byte, short, char], android.database.Cursor, java.lang.String[]]
      mth insns count: 24
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
    	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:311)
    	at jadx.api.JavaClass.decompile(JavaClass.java:62)
    	at jadx.api.JadxDecompiler.lambda$appendSourcesSave$0(JadxDecompiler.java:217)
     */
    /* JADX WARNING: Unknown variable types count: 1 */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private final int zziv() {
        /*
            r4 = this;
            java.lang.String r0 = "Error opening database for getNumStoredHits."
            android.database.sqlite.SQLiteDatabase r0 = r4.zzau(r0)
            r1 = 0
            if (r0 != 0) goto L_0x000a
            return r1
        L_0x000a:
            r2 = 0
            java.lang.String r3 = "SELECT COUNT(*) from gtm_hits"
            android.database.Cursor r2 = r0.rawQuery(r3, r2)     // Catch:{ SQLiteException -> 0x0024 }
            boolean r0 = r2.moveToFirst()     // Catch:{ SQLiteException -> 0x0024 }
            if (r0 == 0) goto L_0x001c
            long r0 = r2.getLong(r1)     // Catch:{ SQLiteException -> 0x0024 }
            int r1 = (int) r0
        L_0x001c:
            if (r2 == 0) goto L_0x002e
            r2.close()
            goto L_0x002e
        L_0x0022:
            r0 = move-exception
            goto L_0x002f
        L_0x0024:
            java.lang.String r0 = "Error getting numStoredHits"
            com.google.android.gms.tagmanager.zzdi.zzac(r0)     // Catch:{ all -> 0x0022 }
            if (r2 == 0) goto L_0x002e
            r2.close()
        L_0x002e:
            return r1
        L_0x002f:
            if (r2 == 0) goto L_0x0034
            r2.close()
        L_0x0034:
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.tagmanager.zzeb.zziv():int");
    }

    private final int zziw() {
        SQLiteDatabase zzau = zzau("Error opening database for getNumStoredHits.");
        int i = 0;
        if (zzau == null) {
            return 0;
        }
        Cursor cursor = null;
        try {
            Cursor query = zzau.query("gtm_hits", new String[]{"hit_id", "hit_first_send_time"}, "hit_first_send_time=0", null, null, null, null);
            i = query.getCount();
            if (query != null) {
                query.close();
            }
        } catch (SQLiteException unused) {
            zzdi.zzac("Error getting num untried hits");
            if (cursor != null) {
                cursor.close();
            }
        } catch (Throwable th) {
            if (cursor != null) {
                cursor.close();
            }
            throw th;
        }
        return i;
    }

    public final void dispatch() {
        zzdi.zzab("GTM Dispatch running...");
        if (this.zzaif.zzhy()) {
            List zzaa = zzaa(40);
            if (zzaa.isEmpty()) {
                zzdi.zzab("...nothing to dispatch");
                this.zzaig.zze(true);
                return;
            }
            this.zzaif.zzd(zzaa);
            if (zziw() > 0) {
                zzfn.zzjq().dispatch();
            }
        }
    }

    private final SQLiteDatabase zzau(String str) {
        try {
            return this.zzaie.getWritableDatabase();
        } catch (SQLiteException unused) {
            zzdi.zzac(str);
            return null;
        }
    }
}
