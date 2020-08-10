package com.google.android.gms.internal.firebase_remote_config;

import android.content.Context;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.HashMap;
import java.util.Map;

public final class zzew {
    private static final Map<String, zzew> zzjy = new HashMap();
    private final Context zzja;
    private final String zzll;

    private zzew(Context context, String str) {
        this.zzja = context;
        this.zzll = str;
    }

    /* JADX WARNING: Code restructure failed: missing block: B:14:0x0021, code lost:
        r1 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:15:0x0022, code lost:
        if (r0 != null) goto L_0x0024;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:17:?, code lost:
        zza(r4, r0);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:18:0x0027, code lost:
        throw r1;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final synchronized java.lang.Void zzf(com.google.android.gms.internal.firebase_remote_config.zzeo r4) throws java.io.IOException {
        /*
            r3 = this;
            monitor-enter(r3)
            android.content.Context r0 = r3.zzja     // Catch:{ all -> 0x0028 }
            java.lang.String r1 = r3.zzll     // Catch:{ all -> 0x0028 }
            r2 = 0
            java.io.FileOutputStream r0 = r0.openFileOutput(r1, r2)     // Catch:{ all -> 0x0028 }
            java.lang.String r4 = r4.toString()     // Catch:{ all -> 0x001f }
            java.lang.String r1 = "UTF-8"
            byte[] r4 = r4.getBytes(r1)     // Catch:{ all -> 0x001f }
            r0.write(r4)     // Catch:{ all -> 0x001f }
            r4 = 0
            if (r0 == 0) goto L_0x001d
            zza(r4, r0)     // Catch:{ all -> 0x0028 }
        L_0x001d:
            monitor-exit(r3)
            return r4
        L_0x001f:
            r4 = move-exception
            throw r4     // Catch:{ all -> 0x0021 }
        L_0x0021:
            r1 = move-exception
            if (r0 == 0) goto L_0x0027
            zza(r4, r0)     // Catch:{ all -> 0x0028 }
        L_0x0027:
            throw r1     // Catch:{ all -> 0x0028 }
        L_0x0028:
            r4 = move-exception
            monitor-exit(r3)
            throw r4
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.firebase_remote_config.zzew.zzf(com.google.android.gms.internal.firebase_remote_config.zzeo):java.lang.Void");
    }

    /* JADX WARNING: Code restructure failed: missing block: B:14:0x002e, code lost:
        r3 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:15:0x002f, code lost:
        if (r1 != null) goto L_0x0031;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:17:?, code lost:
        zza(r2, r1);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:18:0x0034, code lost:
        throw r3;
     */
    @javax.annotation.Nullable
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final synchronized com.google.android.gms.internal.firebase_remote_config.zzeo zzdb() throws java.io.IOException {
        /*
            r5 = this;
            monitor-enter(r5)
            r0 = 0
            android.content.Context r1 = r5.zzja     // Catch:{ FileNotFoundException | JSONException -> 0x0038, all -> 0x0035 }
            java.lang.String r2 = r5.zzll     // Catch:{ FileNotFoundException | JSONException -> 0x0038, all -> 0x0035 }
            java.io.FileInputStream r1 = r1.openFileInput(r2)     // Catch:{ FileNotFoundException | JSONException -> 0x0038, all -> 0x0035 }
            int r2 = r1.available()     // Catch:{ all -> 0x002c }
            byte[] r2 = new byte[r2]     // Catch:{ all -> 0x002c }
            r3 = 0
            int r4 = r2.length     // Catch:{ all -> 0x002c }
            r1.read(r2, r3, r4)     // Catch:{ all -> 0x002c }
            java.lang.String r3 = new java.lang.String     // Catch:{ all -> 0x002c }
            java.lang.String r4 = "UTF-8"
            r3.<init>(r2, r4)     // Catch:{ all -> 0x002c }
            org.json.JSONObject r2 = new org.json.JSONObject     // Catch:{ all -> 0x002c }
            r2.<init>(r3)     // Catch:{ all -> 0x002c }
            com.google.android.gms.internal.firebase_remote_config.zzeo r2 = com.google.android.gms.internal.firebase_remote_config.zzeo.zza(r2)     // Catch:{ all -> 0x002c }
            if (r1 == 0) goto L_0x002a
            zza(r0, r1)     // Catch:{ FileNotFoundException | JSONException -> 0x0038, all -> 0x0035 }
        L_0x002a:
            monitor-exit(r5)
            return r2
        L_0x002c:
            r2 = move-exception
            throw r2     // Catch:{ all -> 0x002e }
        L_0x002e:
            r3 = move-exception
            if (r1 == 0) goto L_0x0034
            zza(r2, r1)     // Catch:{ FileNotFoundException | JSONException -> 0x0038, all -> 0x0035 }
        L_0x0034:
            throw r3     // Catch:{ FileNotFoundException | JSONException -> 0x0038, all -> 0x0035 }
        L_0x0035:
            r0 = move-exception
            monitor-exit(r5)
            throw r0
        L_0x0038:
            monitor-exit(r5)
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.firebase_remote_config.zzew.zzdb():com.google.android.gms.internal.firebase_remote_config.zzeo");
    }

    public final synchronized Void zzdc() {
        this.zzja.deleteFile(this.zzll);
        return null;
    }

    public static synchronized zzew zza(Context context, String str) {
        zzew zzew;
        synchronized (zzew.class) {
            if (!zzjy.containsKey(str)) {
                zzjy.put(str, new zzew(context, str));
            }
            zzew = (zzew) zzjy.get(str);
        }
        return zzew;
    }

    /* access modifiers changed from: 0000 */
    public final String getFileName() {
        return this.zzll;
    }

    private static /* synthetic */ void zza(Throwable th, FileOutputStream fileOutputStream) {
        if (th != null) {
            try {
                fileOutputStream.close();
            } catch (Throwable th2) {
                zzeb.zza(th, th2);
            }
        } else {
            fileOutputStream.close();
        }
    }

    private static /* synthetic */ void zza(Throwable th, FileInputStream fileInputStream) {
        if (th != null) {
            try {
                fileInputStream.close();
            } catch (Throwable th2) {
                zzeb.zza(th, th2);
            }
        } else {
            fileInputStream.close();
        }
    }
}
