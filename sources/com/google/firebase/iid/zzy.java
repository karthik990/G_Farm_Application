package com.google.firebase.iid;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import androidx.core.content.ContextCompat;
import com.google.android.gms.internal.firebase_messaging.zzc;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.io.UnsupportedEncodingException;
import java.nio.channels.Channels;
import java.nio.channels.FileChannel;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Properties;

final class zzy {
    zzy() {
    }

    /* access modifiers changed from: 0000 */
    public final zzz zzb(Context context, String str) throws zzaa {
        zzz zzd = zzd(context, str);
        if (zzd != null) {
            return zzd;
        }
        return zzc(context, str);
    }

    /* access modifiers changed from: 0000 */
    public final zzz zzc(Context context, String str) {
        zzz zzz = new zzz(zza.zzb(), System.currentTimeMillis());
        zzz zza = zza(context, str, zzz, true);
        String str2 = "FirebaseInstanceId";
        if (zza == null || zza.equals(zzz)) {
            if (Log.isLoggable(str2, 3)) {
                Log.d(str2, "Generated new key");
            }
            zza(context, str, zzz);
            return zzz;
        }
        if (Log.isLoggable(str2, 3)) {
            Log.d(str2, "Loaded key after generating new one, using loaded one");
        }
        return zza;
    }

    static void zza(Context context) {
        File[] listFiles;
        for (File file : zzb(context).listFiles()) {
            if (file.getName().startsWith("com.google.InstanceId")) {
                file.delete();
            }
        }
    }

    private final zzz zzd(Context context, String str) throws zzaa {
        try {
            zzz zze = zze(context, str);
            if (zze != null) {
                zza(context, str, zze);
                return zze;
            }
            e = null;
            try {
                zzz zza = zza(context.getSharedPreferences("com.google.android.gms.appid", 0), str);
                if (zza != null) {
                    zza(context, str, zza, false);
                    return zza;
                }
            } catch (zzaa e) {
                e = e;
            }
            if (e == null) {
                return null;
            }
            throw e;
        } catch (zzaa e2) {
            e = e2;
        }
    }

    private static KeyPair zzc(String str, String str2) throws zzaa {
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
                Log.w("FirebaseInstanceId", sb.toString());
                throw new zzaa((Exception) e);
            }
        } catch (IllegalArgumentException e2) {
            throw new zzaa((Exception) e2);
        }
    }

    private final zzz zze(Context context, String str) throws zzaa {
        File zzf = zzf(context, str);
        if (!zzf.exists()) {
            return null;
        }
        try {
            return zza(zzf);
        } catch (zzaa | IOException e) {
            String str2 = "FirebaseInstanceId";
            if (Log.isLoggable(str2, 3)) {
                String valueOf = String.valueOf(e);
                StringBuilder sb = new StringBuilder(String.valueOf(valueOf).length() + 40);
                sb.append("Failed to read key from file, retrying: ");
                sb.append(valueOf);
                Log.d(str2, sb.toString());
            }
            try {
                return zza(zzf);
            } catch (IOException e2) {
                String valueOf2 = String.valueOf(e2);
                StringBuilder sb2 = new StringBuilder(String.valueOf(valueOf2).length() + 45);
                sb2.append("IID file exists, but failed to read from it: ");
                sb2.append(valueOf2);
                Log.w(str2, sb2.toString());
                throw new zzaa((Exception) e2);
            }
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:37:0x00a4, code lost:
        r12 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:38:0x00a5, code lost:
        if (r9 != null) goto L_0x00a7;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:40:?, code lost:
        zza(r11, r9);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:41:0x00aa, code lost:
        throw r12;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:45:0x00ad, code lost:
        r11 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:47:?, code lost:
        zza(r9, r3);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:48:0x00b1, code lost:
        throw r11;
     */
    /* JADX WARNING: Unknown top exception splitter block from list: {B:19:0x0060=Splitter:B:19:0x0060, B:31:0x009e=Splitter:B:31:0x009e} */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private final com.google.firebase.iid.zzz zza(android.content.Context r9, java.lang.String r10, com.google.firebase.iid.zzz r11, boolean r12) {
        /*
            r8 = this;
            r0 = 3
            java.lang.String r1 = "FirebaseInstanceId"
            boolean r2 = android.util.Log.isLoggable(r1, r0)
            if (r2 == 0) goto L_0x000e
            java.lang.String r2 = "Writing key to properties file"
            android.util.Log.d(r1, r2)
        L_0x000e:
            java.util.Properties r2 = new java.util.Properties
            r2.<init>()
            java.lang.String r3 = r11.zzv()
            java.lang.String r4 = "pub"
            r2.setProperty(r4, r3)
            java.lang.String r3 = r11.zzw()
            java.lang.String r4 = "pri"
            r2.setProperty(r4, r3)
            long r3 = r11.zzbs
            java.lang.String r3 = java.lang.String.valueOf(r3)
            java.lang.String r4 = "cre"
            r2.setProperty(r4, r3)
            java.io.File r9 = zzf(r9, r10)
            r10 = 0
            r9.createNewFile()     // Catch:{ IOException -> 0x00b2 }
            java.io.RandomAccessFile r3 = new java.io.RandomAccessFile     // Catch:{ IOException -> 0x00b2 }
            java.lang.String r4 = "rw"
            r3.<init>(r9, r4)     // Catch:{ IOException -> 0x00b2 }
            java.nio.channels.FileChannel r9 = r3.getChannel()     // Catch:{ all -> 0x00ab }
            r9.lock()     // Catch:{ all -> 0x00a2 }
            r4 = 0
            if (r12 == 0) goto L_0x008f
            long r6 = r9.size()     // Catch:{ all -> 0x00a2 }
            int r12 = (r6 > r4 ? 1 : (r6 == r4 ? 0 : -1))
            if (r12 <= 0) goto L_0x008f
            r9.position(r4)     // Catch:{ IOException -> 0x0066, zzaa -> 0x0064 }
            com.google.firebase.iid.zzz r11 = zza(r9)     // Catch:{ IOException -> 0x0066, zzaa -> 0x0064 }
            if (r9 == 0) goto L_0x0060
            zza(r10, r9)     // Catch:{ all -> 0x00ab }
        L_0x0060:
            zza(r10, r3)     // Catch:{ IOException -> 0x00b2 }
            return r11
        L_0x0064:
            r12 = move-exception
            goto L_0x0067
        L_0x0066:
            r12 = move-exception
        L_0x0067:
            boolean r0 = android.util.Log.isLoggable(r1, r0)     // Catch:{ all -> 0x00a2 }
            if (r0 == 0) goto L_0x008f
            java.lang.String r12 = java.lang.String.valueOf(r12)     // Catch:{ all -> 0x00a2 }
            java.lang.String r0 = java.lang.String.valueOf(r12)     // Catch:{ all -> 0x00a2 }
            int r0 = r0.length()     // Catch:{ all -> 0x00a2 }
            int r0 = r0 + 64
            java.lang.StringBuilder r6 = new java.lang.StringBuilder     // Catch:{ all -> 0x00a2 }
            r6.<init>(r0)     // Catch:{ all -> 0x00a2 }
            java.lang.String r0 = "Tried reading key pair before writing new one, but failed with: "
            r6.append(r0)     // Catch:{ all -> 0x00a2 }
            r6.append(r12)     // Catch:{ all -> 0x00a2 }
            java.lang.String r12 = r6.toString()     // Catch:{ all -> 0x00a2 }
            android.util.Log.d(r1, r12)     // Catch:{ all -> 0x00a2 }
        L_0x008f:
            r9.position(r4)     // Catch:{ all -> 0x00a2 }
            java.io.OutputStream r12 = java.nio.channels.Channels.newOutputStream(r9)     // Catch:{ all -> 0x00a2 }
            r2.store(r12, r10)     // Catch:{ all -> 0x00a2 }
            if (r9 == 0) goto L_0x009e
            zza(r10, r9)     // Catch:{ all -> 0x00ab }
        L_0x009e:
            zza(r10, r3)     // Catch:{ IOException -> 0x00b2 }
            return r11
        L_0x00a2:
            r11 = move-exception
            throw r11     // Catch:{ all -> 0x00a4 }
        L_0x00a4:
            r12 = move-exception
            if (r9 == 0) goto L_0x00aa
            zza(r11, r9)     // Catch:{ all -> 0x00ab }
        L_0x00aa:
            throw r12     // Catch:{ all -> 0x00ab }
        L_0x00ab:
            r9 = move-exception
            throw r9     // Catch:{ all -> 0x00ad }
        L_0x00ad:
            r11 = move-exception
            zza(r9, r3)     // Catch:{ IOException -> 0x00b2 }
            throw r11     // Catch:{ IOException -> 0x00b2 }
        L_0x00b2:
            r9 = move-exception
            java.lang.String r9 = java.lang.String.valueOf(r9)
            java.lang.String r11 = java.lang.String.valueOf(r9)
            int r11 = r11.length()
            int r11 = r11 + 21
            java.lang.StringBuilder r12 = new java.lang.StringBuilder
            r12.<init>(r11)
            java.lang.String r11 = "Failed to write key: "
            r12.append(r11)
            r12.append(r9)
            java.lang.String r9 = r12.toString()
            android.util.Log.w(r1, r9)
            return r10
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.firebase.iid.zzy.zza(android.content.Context, java.lang.String, com.google.firebase.iid.zzz, boolean):com.google.firebase.iid.zzz");
    }

    private static File zzb(Context context) {
        File noBackupFilesDir = ContextCompat.getNoBackupFilesDir(context);
        if (noBackupFilesDir != null && noBackupFilesDir.isDirectory()) {
            return noBackupFilesDir;
        }
        Log.w("FirebaseInstanceId", "noBackupFilesDir doesn't exist, using regular files directory instead");
        return context.getFilesDir();
    }

    private static File zzf(Context context, String str) {
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
        return new File(zzb(context), str2);
    }

    /* JADX WARNING: Code restructure failed: missing block: B:15:0x0025, code lost:
        r2 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:16:0x0026, code lost:
        if (r8 != null) goto L_0x0028;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:18:?, code lost:
        zza(r1, r8);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:19:0x002b, code lost:
        throw r2;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:23:0x002e, code lost:
        r1 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:24:0x002f, code lost:
        zza(r8, r0);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:25:0x0032, code lost:
        throw r1;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private final com.google.firebase.iid.zzz zza(java.io.File r8) throws com.google.firebase.iid.zzaa, java.io.IOException {
        /*
            r7 = this;
            java.io.FileInputStream r0 = new java.io.FileInputStream
            r0.<init>(r8)
            java.nio.channels.FileChannel r8 = r0.getChannel()     // Catch:{ all -> 0x002c }
            r2 = 0
            r4 = 9223372036854775807(0x7fffffffffffffff, double:NaN)
            r6 = 1
            r1 = r8
            r1.lock(r2, r4, r6)     // Catch:{ all -> 0x0023 }
            com.google.firebase.iid.zzz r1 = zza(r8)     // Catch:{ all -> 0x0023 }
            r2 = 0
            if (r8 == 0) goto L_0x001f
            zza(r2, r8)     // Catch:{ all -> 0x002c }
        L_0x001f:
            zza(r2, r0)
            return r1
        L_0x0023:
            r1 = move-exception
            throw r1     // Catch:{ all -> 0x0025 }
        L_0x0025:
            r2 = move-exception
            if (r8 == 0) goto L_0x002b
            zza(r1, r8)     // Catch:{ all -> 0x002c }
        L_0x002b:
            throw r2     // Catch:{ all -> 0x002c }
        L_0x002c:
            r8 = move-exception
            throw r8     // Catch:{ all -> 0x002e }
        L_0x002e:
            r1 = move-exception
            zza(r8, r0)
            throw r1
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.firebase.iid.zzy.zza(java.io.File):com.google.firebase.iid.zzz");
    }

    private static zzz zza(FileChannel fileChannel) throws zzaa, IOException {
        Properties properties = new Properties();
        properties.load(Channels.newInputStream(fileChannel));
        String property = properties.getProperty("pub");
        String property2 = properties.getProperty("pri");
        if (property == null || property2 == null) {
            throw new zzaa("Invalid properties file");
        }
        try {
            return new zzz(zzc(property, property2), Long.parseLong(properties.getProperty("cre")));
        } catch (NumberFormatException e) {
            throw new zzaa((Exception) e);
        }
    }

    private static zzz zza(SharedPreferences sharedPreferences, String str) throws zzaa {
        String string = sharedPreferences.getString(zzaw.zzd(str, "|P|"), null);
        String string2 = sharedPreferences.getString(zzaw.zzd(str, "|K|"), null);
        if (string == null || string2 == null) {
            return null;
        }
        return new zzz(zzc(string, string2), zzb(sharedPreferences, str));
    }

    private final void zza(Context context, String str, zzz zzz) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("com.google.android.gms.appid", 0);
        try {
            if (zzz.equals(zza(sharedPreferences, str))) {
                return;
            }
        } catch (zzaa unused) {
        }
        String str2 = "FirebaseInstanceId";
        if (Log.isLoggable(str2, 3)) {
            Log.d(str2, "Writing key to shared preferences");
        }
        Editor edit = sharedPreferences.edit();
        edit.putString(zzaw.zzd(str, "|P|"), zzz.zzv());
        edit.putString(zzaw.zzd(str, "|K|"), zzz.zzw());
        edit.putString(zzaw.zzd(str, "cre"), String.valueOf(zzz.zzbs));
        edit.commit();
    }

    private static long zzb(SharedPreferences sharedPreferences, String str) {
        String string = sharedPreferences.getString(zzaw.zzd(str, "cre"), null);
        if (string != null) {
            try {
                return Long.parseLong(string);
            } catch (NumberFormatException unused) {
            }
        }
        return 0;
    }

    private static /* synthetic */ void zza(Throwable th, FileChannel fileChannel) {
        if (th != null) {
            try {
                fileChannel.close();
            } catch (Throwable th2) {
                zzc.zza(th, th2);
            }
        } else {
            fileChannel.close();
        }
    }

    private static /* synthetic */ void zza(Throwable th, RandomAccessFile randomAccessFile) {
        if (th != null) {
            try {
                randomAccessFile.close();
            } catch (Throwable th2) {
                zzc.zza(th, th2);
            }
        } else {
            randomAccessFile.close();
        }
    }

    private static /* synthetic */ void zza(Throwable th, FileInputStream fileInputStream) {
        if (th != null) {
            try {
                fileInputStream.close();
            } catch (Throwable th2) {
                zzc.zza(th, th2);
            }
        } else {
            fileInputStream.close();
        }
    }
}
