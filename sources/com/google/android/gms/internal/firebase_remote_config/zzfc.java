package com.google.android.gms.internal.firebase_remote_config;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import com.google.android.gms.internal.firebase_remote_config.zzff.zza;
import com.google.android.gms.internal.firebase_remote_config.zzff.zzd;
import com.google.android.gms.internal.firebase_remote_config.zzff.zze;
import com.google.android.gms.internal.firebase_remote_config.zzkx.zzb;
import com.google.firebase.auth.FirebaseAuthProvider;
import com.google.firebase.remoteconfig.zzg;
import java.nio.charset.Charset;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import org.json.JSONException;

public final class zzfc {
    private static final Charset zzlt = Charset.forName("UTF-8");
    private static final ThreadLocal<DateFormat> zzlu = new zzfd();
    private final String appId;
    private final Context zzja;
    private final SharedPreferences zzlv;

    public zzfc(Context context, String str) {
        this.zzja = context;
        this.appId = str;
        this.zzlv = context.getSharedPreferences("com.google.firebase.remoteconfig_legacy_settings", 0);
    }

    public final boolean zzde() {
        String str = "save_legacy_configs";
        if (!this.zzlv.getBoolean(str, true)) {
            return false;
        }
        zze zzdf = zzdf();
        HashMap hashMap = new HashMap();
        if (zzdf != null) {
            Map zza = zza(zzdf.zzdt());
            Map zza2 = zza(zzdf.zzds());
            Map zza3 = zza(zzdf.zzdu());
            HashSet<String> hashSet = new HashSet<>();
            hashSet.addAll(zza.keySet());
            hashSet.addAll(zza2.keySet());
            hashSet.addAll(zza3.keySet());
            for (String str2 : hashSet) {
                zzfe zzfe = new zzfe(null);
                if (zza.containsKey(str2)) {
                    zzfe.zzh((zzeo) zza.get(str2));
                }
                if (zza2.containsKey(str2)) {
                    zzfe.zzg((zzeo) zza2.get(str2));
                }
                if (zza3.containsKey(str2)) {
                    zzfe.zzi((zzeo) zza3.get(str2));
                }
                hashMap.put(str2, zzfe);
            }
        }
        for (Entry entry : hashMap.entrySet()) {
            String str3 = (String) entry.getKey();
            zzfe zzfe2 = (zzfe) entry.getValue();
            zzeh zzd = zzd(str3, "fetch");
            zzeh zzd2 = zzd(str3, "activate");
            zzeh zzd3 = zzd(str3, "defaults");
            if (zzfe2.zzdg() != null) {
                zzd.zzc(zzfe2.zzdg());
            }
            if (zzfe2.zzdh() != null) {
                zzd2.zzc(zzfe2.zzdh());
            }
            if (zzfe2.zzdi() != null) {
                zzd3.zzc(zzfe2.zzdi());
            }
        }
        this.zzlv.edit().putBoolean(str, false).commit();
        return true;
    }

    private final Map<String, zzeo> zza(zza zza) {
        HashMap hashMap = new HashMap();
        Date date = new Date(zza.getTimestamp());
        List<zzfw> zzdk = zza.zzdk();
        ArrayList arrayList = new ArrayList();
        for (zzfw zza2 : zzdk) {
            zzb zza3 = zza(zza2);
            if (zza3 != null) {
                zzde zzde = new zzde();
                zzde.zzan(zza3.zzjq());
                zzde.zzaq(zza3.zzjr());
                zzde.zzao(((DateFormat) zzlu.get()).format(new Date(zza3.zzjs())));
                zzde.zzap(zza3.zzjt());
                zzde.zzb(Long.valueOf(zza3.zzju()));
                zzde.zza(Long.valueOf(zza3.zzjv()));
                arrayList.add(zzde);
            }
        }
        for (zzd zzd : zza.zzdj()) {
            String namespace = zzd.getNamespace();
            if (namespace.startsWith("configns:")) {
                namespace = namespace.substring(9);
            }
            zzeq zzct = zzeo.zzct();
            List<zzff.zzb> zzdq = zzd.zzdq();
            HashMap hashMap2 = new HashMap();
            for (zzff.zzb zzb : zzdq) {
                hashMap2.put(zzb.getKey(), zzb.zzdn().zzb(zzlt));
            }
            zzeq zza4 = zzct.zzc(hashMap2).zza(date);
            if (namespace.equals(FirebaseAuthProvider.PROVIDER_ID)) {
                zza4.zzb(arrayList);
            }
            try {
                hashMap.put(namespace, zza4.zzcv());
            } catch (JSONException unused) {
                Log.i("FirebaseRemoteConfig", "A set of legacy configs could not be converted.");
            }
        }
        return hashMap;
    }

    private static zzb zza(zzfw zzfw) {
        try {
            zzgd zzgd = (zzgd) zzfw.iterator();
            byte[] bArr = new byte[zzfw.size()];
            for (int i = 0; i < bArr.length; i++) {
                bArr[i] = ((Byte) zzgd.next()).byteValue();
            }
            return zzb.zzf(bArr);
        } catch (zzho e) {
            Log.i("FirebaseRemoteConfig", "Payload was not defined or could not be deserialized.", e);
            return null;
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:24:0x0031 A[SYNTHETIC, Splitter:B:24:0x0031] */
    /* JADX WARNING: Removed duplicated region for block: B:35:0x0043 A[Catch:{ all -> 0x0053 }] */
    /* JADX WARNING: Removed duplicated region for block: B:37:0x004a A[SYNTHETIC, Splitter:B:37:0x004a] */
    /* JADX WARNING: Removed duplicated region for block: B:44:0x0056 A[SYNTHETIC, Splitter:B:44:0x0056] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private final com.google.android.gms.internal.firebase_remote_config.zzff.zze zzdf() {
        /*
            r7 = this;
            java.lang.String r0 = "Failed to close persisted config file."
            java.lang.String r1 = "FirebaseRemoteConfig"
            android.content.Context r2 = r7.zzja
            r3 = 0
            if (r2 != 0) goto L_0x000a
            return r3
        L_0x000a:
            java.lang.String r4 = "persisted_config"
            java.io.FileInputStream r2 = r2.openFileInput(r4)     // Catch:{ FileNotFoundException -> 0x003a, IOException -> 0x0028, all -> 0x0023 }
            com.google.android.gms.internal.firebase_remote_config.zzff$zze r3 = com.google.android.gms.internal.firebase_remote_config.zzff.zze.zzb(r2)     // Catch:{ FileNotFoundException -> 0x0021, IOException -> 0x001f }
            if (r2 == 0) goto L_0x001e
            r2.close()     // Catch:{ IOException -> 0x001a }
            goto L_0x001e
        L_0x001a:
            r2 = move-exception
            android.util.Log.e(r1, r0, r2)
        L_0x001e:
            return r3
        L_0x001f:
            r4 = move-exception
            goto L_0x002a
        L_0x0021:
            r4 = move-exception
            goto L_0x003c
        L_0x0023:
            r2 = move-exception
            r6 = r3
            r3 = r2
            r2 = r6
            goto L_0x0054
        L_0x0028:
            r4 = move-exception
            r2 = r3
        L_0x002a:
            java.lang.String r5 = "Cannot initialize from persisted config."
            android.util.Log.e(r1, r5, r4)     // Catch:{ all -> 0x0053 }
            if (r2 == 0) goto L_0x0039
            r2.close()     // Catch:{ IOException -> 0x0035 }
            goto L_0x0039
        L_0x0035:
            r2 = move-exception
            android.util.Log.e(r1, r0, r2)
        L_0x0039:
            return r3
        L_0x003a:
            r4 = move-exception
            r2 = r3
        L_0x003c:
            r5 = 3
            boolean r5 = android.util.Log.isLoggable(r1, r5)     // Catch:{ all -> 0x0053 }
            if (r5 == 0) goto L_0x0048
            java.lang.String r5 = "Persisted config file was not found."
            android.util.Log.d(r1, r5, r4)     // Catch:{ all -> 0x0053 }
        L_0x0048:
            if (r2 == 0) goto L_0x0052
            r2.close()     // Catch:{ IOException -> 0x004e }
            goto L_0x0052
        L_0x004e:
            r2 = move-exception
            android.util.Log.e(r1, r0, r2)
        L_0x0052:
            return r3
        L_0x0053:
            r3 = move-exception
        L_0x0054:
            if (r2 == 0) goto L_0x005e
            r2.close()     // Catch:{ IOException -> 0x005a }
            goto L_0x005e
        L_0x005a:
            r2 = move-exception
            android.util.Log.e(r1, r0, r2)
        L_0x005e:
            throw r3
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.firebase_remote_config.zzfc.zzdf():com.google.android.gms.internal.firebase_remote_config.zzff$zze");
    }

    private final zzeh zzd(String str, String str2) {
        return zzg.zza(this.zzja, this.appId, str, str2);
    }
}
