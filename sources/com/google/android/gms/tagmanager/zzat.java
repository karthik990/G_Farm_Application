package com.google.android.gms.tagmanager;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.text.TextUtils;
import com.google.android.gms.common.util.Clock;
import com.google.android.gms.common.util.DefaultClock;
import com.google.android.gms.internal.gtm.zzdf;
import com.google.android.gms.internal.gtm.zzdi;
import com.google.firebase.analytics.FirebaseAnalytics.Param;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Executor;
import org.apache.http.cookie.ClientCookie;

final class zzat implements zzc {
    /* access modifiers changed from: private */
    public static final String zzafx = String.format("CREATE TABLE IF NOT EXISTS %s ( '%s' INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, '%s' STRING NOT NULL, '%s' BLOB NOT NULL, '%s' INTEGER NOT NULL);", new Object[]{"datalayer", "ID", "key", Param.VALUE, ClientCookie.EXPIRES_ATTR});
    private final Executor zzafy;
    private zzax zzafz;
    private int zzaga;
    /* access modifiers changed from: private */
    public final Context zzrm;
    private Clock zzsd;

    public zzat(Context context) {
        Context context2 = context;
        this(context2, DefaultClock.getInstance(), "google_tagmanager.db", 2000, zzdf.zzgp().zzr(zzdi.zzadg));
    }

    private zzat(Context context, Clock clock, String str, int i, Executor executor) {
        this.zzrm = context;
        this.zzsd = clock;
        this.zzaga = 2000;
        this.zzafy = executor;
        this.zzafz = new zzax(this, this.zzrm, str);
    }

    public final void zza(List<zza> list, long j) {
        ArrayList arrayList = new ArrayList();
        for (zza zza : list) {
            arrayList.add(new zzay(zza.mKey, zzf(zza.mValue)));
        }
        this.zzafy.execute(new zzau(this, arrayList, j));
    }

    public final void zza(zzaq zzaq) {
        this.zzafy.execute(new zzav(this, zzaq));
    }

    public final void zzas(String str) {
        this.zzafy.execute(new zzaw(this, str));
    }

    /* access modifiers changed from: private */
    public final List<zza> zzht() {
        try {
            zzl(this.zzsd.currentTimeMillis());
            List<zzay> zzhu = zzhu();
            ArrayList arrayList = new ArrayList();
            for (zzay zzay : zzhu) {
                arrayList.add(new zza(zzay.zzagg, zza(zzay.zzagh)));
            }
            return arrayList;
        } finally {
            zzhw();
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:13:0x001e A[SYNTHETIC, Splitter:B:13:0x001e] */
    /* JADX WARNING: Removed duplicated region for block: B:22:0x0028 A[SYNTHETIC, Splitter:B:22:0x0028] */
    /* JADX WARNING: Removed duplicated region for block: B:31:0x0032 A[SYNTHETIC, Splitter:B:31:0x0032] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private static java.lang.Object zza(byte[] r3) {
        /*
            java.io.ByteArrayInputStream r0 = new java.io.ByteArrayInputStream
            r0.<init>(r3)
            r3 = 0
            java.io.ObjectInputStream r1 = new java.io.ObjectInputStream     // Catch:{ IOException -> 0x002f, ClassNotFoundException -> 0x0025, all -> 0x001b }
            r1.<init>(r0)     // Catch:{ IOException -> 0x002f, ClassNotFoundException -> 0x0025, all -> 0x001b }
            java.lang.Object r3 = r1.readObject()     // Catch:{ IOException -> 0x0030, ClassNotFoundException -> 0x0026, all -> 0x0016 }
            r1.close()     // Catch:{ IOException -> 0x0015 }
            r0.close()     // Catch:{ IOException -> 0x0015 }
        L_0x0015:
            return r3
        L_0x0016:
            r3 = move-exception
            r2 = r1
            r1 = r3
            r3 = r2
            goto L_0x001c
        L_0x001b:
            r1 = move-exception
        L_0x001c:
            if (r3 == 0) goto L_0x0021
            r3.close()     // Catch:{ IOException -> 0x0024 }
        L_0x0021:
            r0.close()     // Catch:{ IOException -> 0x0024 }
        L_0x0024:
            throw r1
        L_0x0025:
            r1 = r3
        L_0x0026:
            if (r1 == 0) goto L_0x002b
            r1.close()     // Catch:{ IOException -> 0x002e }
        L_0x002b:
            r0.close()     // Catch:{ IOException -> 0x002e }
        L_0x002e:
            return r3
        L_0x002f:
            r1 = r3
        L_0x0030:
            if (r1 == 0) goto L_0x0035
            r1.close()     // Catch:{ IOException -> 0x0038 }
        L_0x0035:
            r0.close()     // Catch:{ IOException -> 0x0038 }
        L_0x0038:
            return r3
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.tagmanager.zzat.zza(byte[]):java.lang.Object");
    }

    /* JADX WARNING: Removed duplicated region for block: B:13:0x001f A[SYNTHETIC, Splitter:B:13:0x001f] */
    /* JADX WARNING: Removed duplicated region for block: B:22:0x0029 A[SYNTHETIC, Splitter:B:22:0x0029] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private static byte[] zzf(java.lang.Object r3) {
        /*
            java.io.ByteArrayOutputStream r0 = new java.io.ByteArrayOutputStream
            r0.<init>()
            r1 = 0
            java.io.ObjectOutputStream r2 = new java.io.ObjectOutputStream     // Catch:{ IOException -> 0x0026, all -> 0x001c }
            r2.<init>(r0)     // Catch:{ IOException -> 0x0026, all -> 0x001c }
            r2.writeObject(r3)     // Catch:{ IOException -> 0x0027, all -> 0x0019 }
            byte[] r3 = r0.toByteArray()     // Catch:{ IOException -> 0x0027, all -> 0x0019 }
            r2.close()     // Catch:{ IOException -> 0x0018 }
            r0.close()     // Catch:{ IOException -> 0x0018 }
        L_0x0018:
            return r3
        L_0x0019:
            r3 = move-exception
            r1 = r2
            goto L_0x001d
        L_0x001c:
            r3 = move-exception
        L_0x001d:
            if (r1 == 0) goto L_0x0022
            r1.close()     // Catch:{ IOException -> 0x0025 }
        L_0x0022:
            r0.close()     // Catch:{ IOException -> 0x0025 }
        L_0x0025:
            throw r3
        L_0x0026:
            r2 = r1
        L_0x0027:
            if (r2 == 0) goto L_0x002c
            r2.close()     // Catch:{ IOException -> 0x002f }
        L_0x002c:
            r0.close()     // Catch:{ IOException -> 0x002f }
        L_0x002f:
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.tagmanager.zzat.zzf(java.lang.Object):byte[]");
    }

    /* access modifiers changed from: private */
    public final synchronized void zzb(List<zzay> list, long j) {
        long currentTimeMillis;
        String[] strArr;
        try {
            currentTimeMillis = this.zzsd.currentTimeMillis();
            zzl(currentTimeMillis);
            int zzhv = (zzhv() - this.zzaga) + list.size();
            if (zzhv > 0) {
                List zzu = zzu(zzhv);
                int size = zzu.size();
                StringBuilder sb = new StringBuilder(64);
                sb.append("DataLayer store full, deleting ");
                sb.append(size);
                sb.append(" entries to make room.");
                zzdi.zzaw(sb.toString());
                strArr = (String[]) zzu.toArray(new String[0]);
                if (strArr != null) {
                    if (strArr.length != 0) {
                        SQLiteDatabase zzau = zzau("Error opening database for deleteEntries.");
                        if (zzau != null) {
                            zzau.delete("datalayer", String.format("%s in (%s)", new Object[]{"ID", TextUtils.join(",", Collections.nCopies(strArr.length, "?"))}), strArr);
                        }
                    }
                }
            }
        } catch (SQLiteException unused) {
            String str = "Error deleting entries ";
            String valueOf = String.valueOf(Arrays.toString(strArr));
            zzdi.zzac(valueOf.length() != 0 ? str.concat(valueOf) : new String(str));
        } catch (Throwable th) {
            zzhw();
            throw th;
        }
        long j2 = currentTimeMillis + j;
        SQLiteDatabase zzau2 = zzau("Error opening database for writeEntryToDatabase.");
        if (zzau2 != null) {
            for (zzay zzay : list) {
                ContentValues contentValues = new ContentValues();
                contentValues.put(ClientCookie.EXPIRES_ATTR, Long.valueOf(j2));
                contentValues.put("key", zzay.zzagg);
                contentValues.put(Param.VALUE, zzay.zzagh);
                zzau2.insert("datalayer", null, contentValues);
            }
        }
        zzhw();
    }

    private final List<zzay> zzhu() {
        SQLiteDatabase zzau = zzau("Error opening database for loadSerialized.");
        ArrayList arrayList = new ArrayList();
        if (zzau == null) {
            return arrayList;
        }
        Cursor query = zzau.query("datalayer", new String[]{"key", Param.VALUE}, null, null, null, null, "ID", null);
        while (query.moveToNext()) {
            try {
                arrayList.add(new zzay(query.getString(0), query.getBlob(1)));
            } finally {
                query.close();
            }
        }
        return arrayList;
    }

    /* access modifiers changed from: private */
    public final void zzat(String str) {
        SQLiteDatabase zzau = zzau("Error opening database for clearKeysWithPrefix.");
        if (zzau != null) {
            try {
                int delete = zzau.delete("datalayer", "key = ? OR key LIKE ?", new String[]{str, String.valueOf(str).concat(".%")});
                StringBuilder sb = new StringBuilder(25);
                sb.append("Cleared ");
                sb.append(delete);
                sb.append(" items");
                zzdi.zzab(sb.toString());
            } catch (SQLiteException e) {
                String valueOf = String.valueOf(e);
                StringBuilder sb2 = new StringBuilder(String.valueOf(str).length() + 44 + String.valueOf(valueOf).length());
                sb2.append("Error deleting entries with key prefix: ");
                sb2.append(str);
                sb2.append(" (");
                sb2.append(valueOf);
                sb2.append(").");
                zzdi.zzac(sb2.toString());
            } finally {
                zzhw();
            }
        }
    }

    private final void zzl(long j) {
        SQLiteDatabase zzau = zzau("Error opening database for deleteOlderThan.");
        if (zzau != null) {
            try {
                int delete = zzau.delete("datalayer", "expires <= ?", new String[]{Long.toString(j)});
                StringBuilder sb = new StringBuilder(33);
                sb.append("Deleted ");
                sb.append(delete);
                sb.append(" expired items");
                zzdi.zzab(sb.toString());
            } catch (SQLiteException unused) {
                zzdi.zzac("Error deleting old entries.");
            }
        }
    }

    private final List<String> zzu(int i) {
        String str = "ID";
        ArrayList arrayList = new ArrayList();
        if (i <= 0) {
            zzdi.zzac("Invalid maxEntries specified. Skipping.");
            return arrayList;
        }
        SQLiteDatabase zzau = zzau("Error opening database for peekEntryIds.");
        if (zzau == null) {
            return arrayList;
        }
        Cursor cursor = null;
        String str2 = "datalayer";
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
            String str3 = "Error in peekEntries fetching entryIds: ";
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
    private final int zzhv() {
        /*
            r4 = this;
            java.lang.String r0 = "Error opening database for getNumStoredEntries."
            android.database.sqlite.SQLiteDatabase r0 = r4.zzau(r0)
            r1 = 0
            if (r0 != 0) goto L_0x000a
            return r1
        L_0x000a:
            r2 = 0
            java.lang.String r3 = "SELECT COUNT(*) from datalayer"
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
            java.lang.String r0 = "Error getting numStoredEntries"
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
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.tagmanager.zzat.zzhv():int");
    }

    private final SQLiteDatabase zzau(String str) {
        try {
            return this.zzafz.getWritableDatabase();
        } catch (SQLiteException unused) {
            zzdi.zzac(str);
            return null;
        }
    }

    private final void zzhw() {
        try {
            this.zzafz.close();
        } catch (SQLiteException unused) {
        }
    }
}
