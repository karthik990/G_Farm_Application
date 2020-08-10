package com.google.android.gms.internal.ads;

import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.os.Parcel;
import android.util.Base64;
import com.google.android.gms.ads.internal.zzbv;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.annotation.ParametersAreNonnullByDefault;

@zzadh
@ParametersAreNonnullByDefault
public final class zztw {
    private final Map<zztx, zzty> zzbok = new HashMap();
    private final LinkedList<zztx> zzbol = new LinkedList<>();
    private zzss zzbom;

    private static void zza(String str, zztx zztx) {
        if (zzakb.isLoggable(2)) {
            zzakb.m1419v(String.format(str, new Object[]{zztx}));
        }
    }

    private static String[] zzax(String str) {
        try {
            String[] split = str.split("\u0000");
            for (int i = 0; i < split.length; i++) {
                split[i] = new String(Base64.decode(split[i], 0), "UTF-8");
            }
            return split;
        } catch (UnsupportedEncodingException unused) {
            return new String[0];
        }
    }

    private static boolean zzay(String str) {
        try {
            return Pattern.matches((String) zzkb.zzik().zzd(zznk.zzazf), str);
        } catch (RuntimeException e) {
            zzbv.zzeo().zza(e, "InterstitialAdPool.isExcludedAdUnit");
            return false;
        }
    }

    private static String zzaz(String str) {
        try {
            Matcher matcher = Pattern.compile("([^/]+/[0-9]+).*").matcher(str);
            return matcher.matches() ? matcher.group(1) : str;
        } catch (RuntimeException unused) {
            return str;
        }
    }

    private static void zzb(Bundle bundle, String str) {
        while (true) {
            String[] split = str.split("/", 2);
            if (split.length != 0) {
                String str2 = split[0];
                if (split.length == 1) {
                    bundle.remove(str2);
                    return;
                }
                bundle = bundle.getBundle(str2);
                if (bundle != null) {
                    str = split[1];
                } else {
                    return;
                }
            } else {
                return;
            }
        }
    }

    static Set<String> zzh(zzjj zzjj) {
        HashSet hashSet = new HashSet();
        hashSet.addAll(zzjj.extras.keySet());
        Bundle bundle = zzjj.zzaqg.getBundle("com.google.ads.mediation.admob.AdMobAdapter");
        if (bundle != null) {
            hashSet.addAll(bundle.keySet());
        }
        return hashSet;
    }

    static zzjj zzi(zzjj zzjj) {
        zzjj zzk = zzk(zzjj);
        Bundle bundle = zzk.zzaqg.getBundle("com.google.ads.mediation.admob.AdMobAdapter");
        String str = "_skipMediation";
        if (bundle != null) {
            bundle.putBoolean(str, true);
        }
        zzk.extras.putBoolean(str, true);
        return zzk;
    }

    private static zzjj zzj(zzjj zzjj) {
        String[] split;
        zzjj zzk = zzk(zzjj);
        for (String str : ((String) zzkb.zzik().zzd(zznk.zzazb)).split(",")) {
            zzb(zzk.zzaqg, str);
            String str2 = "com.google.ads.mediation.admob.AdMobAdapter/";
            if (str.startsWith(str2)) {
                zzb(zzk.extras, str.replaceFirst(str2, ""));
            }
        }
        return zzk;
    }

    private static zzjj zzk(zzjj zzjj) {
        Parcel obtain = Parcel.obtain();
        zzjj.writeToParcel(obtain, 0);
        obtain.setDataPosition(0);
        zzjj zzjj2 = (zzjj) zzjj.CREATOR.createFromParcel(obtain);
        obtain.recycle();
        return ((Boolean) zzkb.zzik().zzd(zznk.zzayo)).booleanValue() ? zzjj2.zzhv() : zzjj2;
    }

    private final String zzle() {
        try {
            StringBuilder sb = new StringBuilder();
            Iterator it = this.zzbol.iterator();
            while (it.hasNext()) {
                sb.append(Base64.encodeToString(((zztx) it.next()).toString().getBytes("UTF-8"), 0));
                if (it.hasNext()) {
                    sb.append("\u0000");
                }
            }
            return sb.toString();
        } catch (UnsupportedEncodingException unused) {
            return "";
        }
    }

    /* access modifiers changed from: 0000 */
    public final zztz zza(zzjj zzjj, String str) {
        if (zzay(str)) {
            return null;
        }
        int i = new zzagb(this.zzbom.getApplicationContext()).zzoo().zzcjx;
        zzjj zzj = zzj(zzjj);
        String zzaz = zzaz(str);
        zztx zztx = new zztx(zzj, zzaz, i);
        zzty zzty = (zzty) this.zzbok.get(zztx);
        if (zzty == null) {
            zza("Interstitial pool created at %s.", zztx);
            zzty = new zzty(zzj, zzaz, i);
            this.zzbok.put(zztx, zzty);
        }
        this.zzbol.remove(zztx);
        this.zzbol.add(zztx);
        zzty.zzli();
        while (true) {
            if (this.zzbol.size() <= ((Integer) zzkb.zzik().zzd(zznk.zzazc)).intValue()) {
                break;
            }
            zztx zztx2 = (zztx) this.zzbol.remove();
            zzty zzty2 = (zzty) this.zzbok.get(zztx2);
            zza("Evicting interstitial queue for %s.", zztx2);
            while (zzty2.size() > 0) {
                zztz zzl = zzty2.zzl(null);
                if (zzl.zzwa) {
                    zzua.zzlk().zzlm();
                }
                zzl.zzbor.zzdj();
            }
            this.zzbok.remove(zztx2);
        }
        while (zzty.size() > 0) {
            zztz zzl2 = zzty.zzl(zzj);
            if (zzl2.zzwa) {
                if (zzbv.zzer().currentTimeMillis() - zzl2.zzbou > ((long) ((Integer) zzkb.zzik().zzd(zznk.zzaze)).intValue()) * 1000) {
                    zza("Expired interstitial at %s.", zztx);
                    zzua.zzlk().zzll();
                }
            }
            String str2 = zzl2.zzbos != null ? " (inline) " : " ";
            StringBuilder sb = new StringBuilder(str2.length() + 34);
            sb.append("Pooled interstitial");
            sb.append(str2);
            sb.append("returned at %s.");
            zza(sb.toString(), zztx);
            return zzl2;
        }
        return null;
    }

    /* access modifiers changed from: 0000 */
    public final void zza(zzss zzss) {
        String str;
        if (this.zzbom == null) {
            this.zzbom = zzss.zzlc();
            zzss zzss2 = this.zzbom;
            if (zzss2 != null) {
                SharedPreferences sharedPreferences = zzss2.getApplicationContext().getSharedPreferences("com.google.android.gms.ads.internal.interstitial.InterstitialAdPool", 0);
                while (this.zzbol.size() > 0) {
                    zztx zztx = (zztx) this.zzbol.remove();
                    zzty zzty = (zzty) this.zzbok.get(zztx);
                    zza("Flushing interstitial queue for %s.", zztx);
                    while (zzty.size() > 0) {
                        zzty.zzl(null).zzbor.zzdj();
                    }
                    this.zzbok.remove(zztx);
                }
                try {
                    HashMap hashMap = new HashMap();
                    Iterator it = sharedPreferences.getAll().entrySet().iterator();
                    while (true) {
                        str = "PoolKeys";
                        if (!it.hasNext()) {
                            break;
                        }
                        Entry entry = (Entry) it.next();
                        if (!((String) entry.getKey()).equals(str)) {
                            zzuc zzba = zzuc.zzba((String) entry.getValue());
                            zztx zztx2 = new zztx(zzba.zzaao, zzba.zzye, zzba.zzbop);
                            if (!this.zzbok.containsKey(zztx2)) {
                                this.zzbok.put(zztx2, new zzty(zzba.zzaao, zzba.zzye, zzba.zzbop));
                                hashMap.put(zztx2.toString(), zztx2);
                                zza("Restored interstitial queue for %s.", zztx2);
                            }
                        }
                    }
                    for (String str2 : zzax(sharedPreferences.getString(str, ""))) {
                        zztx zztx3 = (zztx) hashMap.get(str2);
                        if (this.zzbok.containsKey(zztx3)) {
                            this.zzbol.add(zztx3);
                        }
                    }
                } catch (IOException | RuntimeException e) {
                    zzbv.zzeo().zza(e, "InterstitialAdPool.restore");
                    zzakb.zzc("Malformed preferences value for InterstitialAdPool.", e);
                    this.zzbok.clear();
                    this.zzbol.clear();
                }
            }
        }
    }

    /* access modifiers changed from: 0000 */
    public final void zzb(zzjj zzjj, String str) {
        zzss zzss = this.zzbom;
        if (zzss != null) {
            int i = new zzagb(zzss.getApplicationContext()).zzoo().zzcjx;
            zzjj zzj = zzj(zzjj);
            String zzaz = zzaz(str);
            zztx zztx = new zztx(zzj, zzaz, i);
            zzty zzty = (zzty) this.zzbok.get(zztx);
            if (zzty == null) {
                zza("Interstitial pool created at %s.", zztx);
                zzty = new zzty(zzj, zzaz, i);
                this.zzbok.put(zztx, zzty);
            }
            zzty.zza(this.zzbom, zzjj);
            zzty.zzli();
            zza("Inline entry added to the queue at %s.", zztx);
        }
    }

    /* access modifiers changed from: 0000 */
    public final void zzld() {
        if (this.zzbom != null) {
            for (Entry entry : this.zzbok.entrySet()) {
                zztx zztx = (zztx) entry.getKey();
                zzty zzty = (zzty) entry.getValue();
                if (zzakb.isLoggable(2)) {
                    int size = zzty.size();
                    int zzlg = zzty.zzlg();
                    if (zzlg < size) {
                        zzakb.m1419v(String.format("Loading %s/%s pooled interstitials for %s.", new Object[]{Integer.valueOf(size - zzlg), Integer.valueOf(size), zztx}));
                    }
                }
                int zzlh = zzty.zzlh() + 0;
                while (true) {
                    if (zzty.size() >= ((Integer) zzkb.zzik().zzd(zznk.zzazd)).intValue()) {
                        break;
                    }
                    zza("Pooling and loading one new interstitial for %s.", zztx);
                    if (zzty.zzb(this.zzbom)) {
                        zzlh++;
                    }
                }
                zzua.zzlk().zzw(zzlh);
            }
            zzss zzss = this.zzbom;
            if (zzss != null) {
                Editor edit = zzss.getApplicationContext().getSharedPreferences("com.google.android.gms.ads.internal.interstitial.InterstitialAdPool", 0).edit();
                edit.clear();
                for (Entry entry2 : this.zzbok.entrySet()) {
                    zztx zztx2 = (zztx) entry2.getKey();
                    zzty zzty2 = (zzty) entry2.getValue();
                    if (zzty2.zzlj()) {
                        edit.putString(zztx2.toString(), new zzuc(zzty2).zzlu());
                        zza("Saved interstitial queue for %s.", zztx2);
                    }
                }
                edit.putString("PoolKeys", zzle());
                edit.apply();
            }
        }
    }
}
