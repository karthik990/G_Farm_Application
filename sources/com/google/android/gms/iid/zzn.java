package com.google.android.gms.iid;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import androidx.core.content.ContextCompat;
import com.google.android.gms.internal.gcm.zzf;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

final class zzn {
    zzn() {
    }

    private static zzo zzd(SharedPreferences sharedPreferences, String str) throws zzp {
        String string = sharedPreferences.getString(zzak.zzh(str, "|P|"), null);
        String string2 = sharedPreferences.getString(zzak.zzh(str, "|K|"), null);
        if (string == null || string2 == null) {
            return null;
        }
        return new zzo(zzg(string, string2), zze(sharedPreferences, str));
    }

    /* JADX WARNING: Code restructure failed: missing block: B:23:0x0043, code lost:
        r1 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:24:0x0044, code lost:
        zzd(r6, r0);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:25:0x0047, code lost:
        throw r1;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private static com.google.android.gms.iid.zzo zzd(java.io.File r6) throws com.google.android.gms.iid.zzp, java.io.IOException {
        /*
            java.io.FileInputStream r0 = new java.io.FileInputStream
            r0.<init>(r6)
            java.util.Properties r6 = new java.util.Properties     // Catch:{ all -> 0x0041 }
            r6.<init>()     // Catch:{ all -> 0x0041 }
            r6.load(r0)     // Catch:{ all -> 0x0041 }
            java.lang.String r1 = "pub"
            java.lang.String r1 = r6.getProperty(r1)     // Catch:{ all -> 0x0041 }
            java.lang.String r2 = "pri"
            java.lang.String r2 = r6.getProperty(r2)     // Catch:{ all -> 0x0041 }
            r3 = 0
            if (r1 == 0) goto L_0x003d
            if (r2 != 0) goto L_0x001f
            goto L_0x003d
        L_0x001f:
            java.security.KeyPair r1 = zzg(r1, r2)     // Catch:{ all -> 0x0041 }
            java.lang.String r2 = "cre"
            java.lang.String r6 = r6.getProperty(r2)     // Catch:{ NumberFormatException -> 0x0036 }
            long r4 = java.lang.Long.parseLong(r6)     // Catch:{ NumberFormatException -> 0x0036 }
            com.google.android.gms.iid.zzo r6 = new com.google.android.gms.iid.zzo     // Catch:{ all -> 0x0041 }
            r6.<init>(r1, r4)     // Catch:{ all -> 0x0041 }
            zzd(r3, r0)
            return r6
        L_0x0036:
            r6 = move-exception
            com.google.android.gms.iid.zzp r1 = new com.google.android.gms.iid.zzp     // Catch:{ all -> 0x0041 }
            r1.<init>(r6)     // Catch:{ all -> 0x0041 }
            throw r1     // Catch:{ all -> 0x0041 }
        L_0x003d:
            zzd(r3, r0)
            return r3
        L_0x0041:
            r6 = move-exception
            throw r6     // Catch:{ all -> 0x0043 }
        L_0x0043:
            r1 = move-exception
            zzd(r6, r0)
            throw r1
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.iid.zzn.zzd(java.io.File):com.google.android.gms.iid.zzo");
    }

    /* JADX WARNING: Code restructure failed: missing block: B:15:0x0048, code lost:
        r5 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:17:?, code lost:
        zzd(r4, r6);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:18:0x004c, code lost:
        throw r5;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private static void zzd(android.content.Context r4, java.lang.String r5, com.google.android.gms.iid.zzo r6) {
        /*
            java.lang.String r0 = "InstanceID"
            r1 = 3
            boolean r1 = android.util.Log.isLoggable(r0, r1)     // Catch:{ IOException -> 0x004d }
            if (r1 == 0) goto L_0x000e
            java.lang.String r1 = "Writing key to properties file"
            android.util.Log.d(r0, r1)     // Catch:{ IOException -> 0x004d }
        L_0x000e:
            java.io.File r4 = zzj(r4, r5)     // Catch:{ IOException -> 0x004d }
            r4.createNewFile()     // Catch:{ IOException -> 0x004d }
            java.util.Properties r5 = new java.util.Properties     // Catch:{ IOException -> 0x004d }
            r5.<init>()     // Catch:{ IOException -> 0x004d }
            java.lang.String r1 = "pub"
            java.lang.String r2 = r6.zzo()     // Catch:{ IOException -> 0x004d }
            r5.setProperty(r1, r2)     // Catch:{ IOException -> 0x004d }
            java.lang.String r1 = "pri"
            java.lang.String r2 = r6.zzp()     // Catch:{ IOException -> 0x004d }
            r5.setProperty(r1, r2)     // Catch:{ IOException -> 0x004d }
            java.lang.String r1 = "cre"
            long r2 = r6.zzbx     // Catch:{ IOException -> 0x004d }
            java.lang.String r6 = java.lang.String.valueOf(r2)     // Catch:{ IOException -> 0x004d }
            r5.setProperty(r1, r6)     // Catch:{ IOException -> 0x004d }
            java.io.FileOutputStream r6 = new java.io.FileOutputStream     // Catch:{ IOException -> 0x004d }
            r6.<init>(r4)     // Catch:{ IOException -> 0x004d }
            r4 = 0
            r5.store(r6, r4)     // Catch:{ all -> 0x0046 }
            zzd(r4, r6)     // Catch:{ IOException -> 0x004d }
            return
        L_0x0046:
            r4 = move-exception
            throw r4     // Catch:{ all -> 0x0048 }
        L_0x0048:
            r5 = move-exception
            zzd(r4, r6)     // Catch:{ IOException -> 0x004d }
            throw r5     // Catch:{ IOException -> 0x004d }
        L_0x004d:
            r4 = move-exception
            java.lang.String r4 = java.lang.String.valueOf(r4)
            java.lang.String r5 = java.lang.String.valueOf(r4)
            int r5 = r5.length()
            int r5 = r5 + 21
            java.lang.StringBuilder r6 = new java.lang.StringBuilder
            r6.<init>(r5)
            java.lang.String r5 = "Failed to write key: "
            r6.append(r5)
            r6.append(r4)
            java.lang.String r4 = r6.toString()
            android.util.Log.w(r0, r4)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.iid.zzn.zzd(android.content.Context, java.lang.String, com.google.android.gms.iid.zzo):void");
    }

    private static /* synthetic */ void zzd(Throwable th, FileInputStream fileInputStream) {
        if (th != null) {
            try {
                fileInputStream.close();
            } catch (Throwable th2) {
                zzf.zzd(th, th2);
            }
        } else {
            fileInputStream.close();
        }
    }

    private static /* synthetic */ void zzd(Throwable th, FileOutputStream fileOutputStream) {
        if (th != null) {
            try {
                fileOutputStream.close();
            } catch (Throwable th2) {
                zzf.zzd(th, th2);
            }
        } else {
            fileOutputStream.close();
        }
    }

    private static long zze(SharedPreferences sharedPreferences, String str) {
        String string = sharedPreferences.getString(zzak.zzh(str, "cre"), null);
        if (string != null) {
            try {
                return Long.parseLong(string);
            } catch (NumberFormatException unused) {
            }
        }
        return 0;
    }

    private final void zze(Context context, String str, zzo zzo) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("com.google.android.gms.appid", 0);
        try {
            if (zzo.equals(zzd(sharedPreferences, str))) {
                return;
            }
        } catch (zzp unused) {
        }
        String str2 = "InstanceID";
        if (Log.isLoggable(str2, 3)) {
            Log.d(str2, "Writing key to shared preferences");
        }
        Editor edit = sharedPreferences.edit();
        edit.putString(zzak.zzh(str, "|P|"), zzo.zzo());
        edit.putString(zzak.zzh(str, "|K|"), zzo.zzp());
        edit.putString(zzak.zzh(str, "cre"), String.valueOf(zzo.zzbx));
        edit.commit();
    }

    private static KeyPair zzg(String str, String str2) throws zzp {
        try {
            byte[] decode = Base64.decode(str, 8);
            byte[] decode2 = Base64.decode(str2, 8);
            try {
                KeyFactory instance = KeyFactory.getInstance("RSA");
                return new KeyPair(instance.generatePublic(new X509EncodedKeySpec(decode)), instance.generatePrivate(new PKCS8EncodedKeySpec(decode2)));
            } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
                String valueOf = String.valueOf(e);
                StringBuilder sb = new StringBuilder(String.valueOf(valueOf).length() + 19);
                sb.append("Invalid key stored ");
                sb.append(valueOf);
                Log.w("InstanceID", sb.toString());
                throw new zzp(e);
            }
        } catch (IllegalArgumentException e2) {
            throw new zzp(e2);
        }
    }

    static void zzg(Context context, String str) {
        File zzj = zzj(context, str);
        if (zzj.exists()) {
            zzj.delete();
        }
    }

    private final zzo zzh(Context context, String str) throws zzp {
        try {
            zzo zzi = zzi(context, str);
            if (zzi != null) {
                zze(context, str, zzi);
                return zzi;
            }
            e = null;
            try {
                zzo zzd = zzd(context.getSharedPreferences("com.google.android.gms.appid", 0), str);
                if (zzd != null) {
                    zzd(context, str, zzd);
                    return zzd;
                }
            } catch (zzp e) {
                e = e;
            }
            if (e == null) {
                return null;
            }
            throw e;
        } catch (zzp e2) {
            e = e2;
        }
    }

    private final zzo zzi(Context context, String str) throws zzp {
        File zzj = zzj(context, str);
        if (!zzj.exists()) {
            return null;
        }
        try {
            return zzd(zzj);
        } catch (IOException e) {
            String str2 = "InstanceID";
            if (Log.isLoggable(str2, 3)) {
                String valueOf = String.valueOf(e);
                StringBuilder sb = new StringBuilder(String.valueOf(valueOf).length() + 40);
                sb.append("Failed to read key from file, retrying: ");
                sb.append(valueOf);
                Log.d(str2, sb.toString());
            }
            try {
                return zzd(zzj);
            } catch (IOException e2) {
                String valueOf2 = String.valueOf(e2);
                StringBuilder sb2 = new StringBuilder(String.valueOf(valueOf2).length() + 45);
                sb2.append("IID file exists, but failed to read from it: ");
                sb2.append(valueOf2);
                Log.w(str2, sb2.toString());
                throw new zzp(e2);
            }
        }
    }

    static void zzi(Context context) {
        File[] listFiles;
        for (File file : zzj(context).listFiles()) {
            if (file.getName().startsWith("com.google.InstanceId")) {
                file.delete();
            }
        }
    }

    private static File zzj(Context context) {
        File noBackupFilesDir = ContextCompat.getNoBackupFilesDir(context);
        if (noBackupFilesDir != null && noBackupFilesDir.isDirectory()) {
            return noBackupFilesDir;
        }
        Log.w("InstanceID", "noBackupFilesDir doesn't exist, using regular files directory instead");
        return context.getFilesDir();
    }

    private static File zzj(Context context, String str) {
        String str2;
        if (TextUtils.isEmpty(str)) {
            str2 = "com.google.InstanceId.properties";
        } else {
            try {
                String encodeToString = Base64.encodeToString(str.getBytes("UTF-8"), 11);
                StringBuilder sb = new StringBuilder(String.valueOf(encodeToString).length() + 33);
                sb.append("com.google.InstanceId_");
                sb.append(encodeToString);
                sb.append(".properties");
                str2 = sb.toString();
            } catch (UnsupportedEncodingException e) {
                throw new AssertionError(e);
            }
        }
        return new File(zzj(context), str2);
    }

    /* access modifiers changed from: 0000 */
    public final zzo zze(Context context, String str) throws zzp {
        zzo zzh = zzh(context, str);
        return zzh != null ? zzh : zzf(context, str);
    }

    /* access modifiers changed from: 0000 */
    public final zzo zzf(Context context, String str) {
        String str2 = "InstanceID";
        zzo zzo = new zzo(zzd.zzk(), System.currentTimeMillis());
        try {
            zzo zzh = zzh(context, str);
            if (zzh != null) {
                if (Log.isLoggable(str2, 3)) {
                    Log.d(str2, "Loaded key after generating new one, using loaded one");
                }
                return zzh;
            }
        } catch (zzp unused) {
        }
        if (Log.isLoggable(str2, 3)) {
            Log.d(str2, "Generated new key");
        }
        zzd(context, str, zzo);
        zze(context, str, zzo);
        return zzo;
    }
}
