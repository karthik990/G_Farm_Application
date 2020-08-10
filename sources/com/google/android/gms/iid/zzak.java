package com.google.android.gms.iid;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.util.Log;
import androidx.collection.ArrayMap;
import androidx.core.content.ContextCompat;
import java.io.File;
import java.io.IOException;
import java.util.Map;

public final class zzak {
    private SharedPreferences zzcz;
    private final zzn zzda;
    private final Map<String, zzo> zzdb;
    private Context zzk;

    public zzak(Context context) {
        this(context, new zzn());
    }

    private zzak(Context context, zzn zzn) {
        String str = "InstanceID/Store";
        this.zzdb = new ArrayMap();
        this.zzk = context;
        this.zzcz = context.getSharedPreferences("com.google.android.gms.appid", 0);
        this.zzda = zzn;
        File file = new File(ContextCompat.getNoBackupFilesDir(this.zzk), "com.google.android.gms.appid-no-backup");
        if (!file.exists()) {
            try {
                if (file.createNewFile() && !isEmpty()) {
                    Log.i(str, "App restored, clearing state");
                    InstanceIDListenerService.zzd(this.zzk, this);
                }
            } catch (IOException e) {
                if (Log.isLoggable(str, 3)) {
                    String str2 = "Error creating file in no backup dir: ";
                    String valueOf = String.valueOf(e.getMessage());
                    Log.d(str, valueOf.length() != 0 ? str2.concat(valueOf) : new String(str2));
                }
            }
        }
    }

    private static String zzd(String str, String str2, String str3) {
        StringBuilder sb = new StringBuilder(String.valueOf(str).length() + 4 + String.valueOf(str2).length() + String.valueOf(str3).length());
        sb.append(str);
        sb.append("|T|");
        sb.append(str2);
        sb.append("|");
        sb.append(str3);
        return sb.toString();
    }

    static String zzh(String str, String str2) {
        StringBuilder sb = new StringBuilder(String.valueOf(str).length() + 3 + String.valueOf(str2).length());
        sb.append(str);
        sb.append("|S|");
        sb.append(str2);
        return sb.toString();
    }

    /* access modifiers changed from: 0000 */
    public final synchronized String get(String str) {
        return this.zzcz.getString(str, null);
    }

    public final boolean isEmpty() {
        return this.zzcz.getAll().isEmpty();
    }

    public final synchronized void zzd(String str, String str2, String str3, String str4, String str5) {
        String zzd = zzd(str, str2, str3);
        Editor edit = this.zzcz.edit();
        edit.putString(zzd, str4);
        edit.putString("appVersion", str5);
        edit.putString("lastToken", Long.toString(System.currentTimeMillis() / 1000));
        edit.commit();
    }

    public final synchronized String zze(String str, String str2, String str3) {
        return this.zzcz.getString(zzd(str, str2, str3), null);
    }

    public final synchronized void zzf(String str, String str2, String str3) {
        String zzd = zzd(str, str2, str3);
        Editor edit = this.zzcz.edit();
        edit.remove(zzd);
        edit.commit();
    }

    public final synchronized void zzi(String str) {
        Editor edit = this.zzcz.edit();
        for (String str2 : this.zzcz.getAll().keySet()) {
            if (str2.startsWith(str)) {
                edit.remove(str2);
            }
        }
        edit.commit();
    }

    public final synchronized zzo zzj(String str) {
        zzo zzo;
        zzo zzo2 = (zzo) this.zzdb.get(str);
        if (zzo2 != null) {
            return zzo2;
        }
        try {
            zzo = this.zzda.zze(this.zzk, str);
        } catch (zzp unused) {
            Log.w("InstanceID/Store", "Stored data is corrupt, generating new identity");
            InstanceIDListenerService.zzd(this.zzk, this);
            zzo = this.zzda.zzf(this.zzk, str);
        }
        this.zzdb.put(str, zzo);
        return zzo;
    }

    /* access modifiers changed from: 0000 */
    public final void zzk(String str) {
        synchronized (this) {
            this.zzdb.remove(str);
        }
        zzn.zzg(this.zzk, str);
        zzi(String.valueOf(str).concat("|"));
    }

    public final synchronized void zzx() {
        this.zzdb.clear();
        zzn.zzi(this.zzk);
        this.zzcz.edit().clear().commit();
    }
}
