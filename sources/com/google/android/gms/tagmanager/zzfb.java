package com.google.android.gms.tagmanager;

import android.content.Context;
import com.braintreepayments.api.models.BinData;
import com.google.android.gms.internal.gtm.zzb;
import com.google.android.gms.internal.gtm.zzc.C6219zzc;
import com.google.android.gms.internal.gtm.zzj;
import com.google.android.gms.internal.gtm.zzl;
import com.google.android.gms.internal.gtm.zzor;
import com.google.android.gms.internal.gtm.zzot;
import com.google.android.gms.internal.gtm.zzov;
import com.google.android.gms.internal.gtm.zzox;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

final class zzfb {
    private static final zzdz<zzl> zzajp = new zzdz<>(zzgj.zzkc(), true);
    private final DataLayer zzaed;
    private final zzov zzajq;
    private final zzbo zzajr;
    private final Map<String, zzbq> zzajs;
    private final Map<String, zzbq> zzajt;
    private final Map<String, zzbq> zzaju;
    private final zzp<zzot, zzdz<zzl>> zzajv;
    private final zzp<String, zzfh> zzajw;
    private final Set<zzox> zzajx;
    private final Map<String, zzfi> zzajy;
    private volatile String zzajz;
    private int zzaka;

    public zzfb(Context context, zzov zzov, DataLayer dataLayer, zzan zzan, zzan zzan2, zzbo zzbo) {
        String str;
        if (zzov != null) {
            this.zzajq = zzov;
            this.zzajx = new HashSet(zzov.zzls());
            this.zzaed = dataLayer;
            this.zzajr = zzbo;
            zzfc zzfc = new zzfc(this);
            new zzq();
            this.zzajv = zzq.zza(1048576, zzfc);
            zzfd zzfd = new zzfd(this);
            new zzq();
            this.zzajw = zzq.zza(1048576, zzfd);
            this.zzajs = new HashMap();
            zzb(new zzm(context));
            zzb(new zzam(zzan2));
            zzb(new zzaz(dataLayer));
            zzb(new zzgk(context, dataLayer));
            this.zzajt = new HashMap();
            zzc(new zzak());
            zzc(new zzbl());
            zzc(new zzbm());
            zzc(new zzbs());
            zzc(new zzbt());
            zzc(new zzde());
            zzc(new zzdf());
            zzc(new zzel());
            zzc(new zzfy());
            this.zzaju = new HashMap();
            zza((zzbq) new zze(context));
            zza((zzbq) new zzf(context));
            zza((zzbq) new zzh(context));
            zza((zzbq) new zzi(context));
            zza((zzbq) new zzj(context));
            zza((zzbq) new zzk(context));
            zza((zzbq) new zzl(context));
            zza((zzbq) new zzt());
            zza((zzbq) new zzaj(this.zzajq.getVersion()));
            zza((zzbq) new zzam(zzan));
            zza((zzbq) new zzas(dataLayer));
            zza((zzbq) new zzbc(context));
            zza((zzbq) new zzbd());
            zza((zzbq) new zzbk());
            zza((zzbq) new zzbp(this));
            zza((zzbq) new zzbu());
            zza((zzbq) new zzbv());
            zza((zzbq) new zzcv(context));
            zza((zzbq) new zzcx());
            zza((zzbq) new zzdd());
            zza((zzbq) new zzdk());
            zza((zzbq) new zzdm(context));
            zza((zzbq) new zzea());
            zza((zzbq) new zzee());
            zza((zzbq) new zzei());
            zza((zzbq) new zzek());
            zza((zzbq) new zzem(context));
            zza((zzbq) new zzfj());
            zza((zzbq) new zzfk());
            zza((zzbq) new zzge());
            zza((zzbq) new zzgl());
            this.zzajy = new HashMap();
            for (zzox zzox : this.zzajx) {
                int i = 0;
                while (true) {
                    int size = zzox.zzmq().size();
                    str = BinData.UNKNOWN;
                    if (i >= size) {
                        break;
                    }
                    zzot zzot = (zzot) zzox.zzmq().get(i);
                    zzfi zzb = zzb(this.zzajy, zza(zzot));
                    zzb.zza(zzox);
                    zzb.zza(zzox, zzot);
                    zzb.zza(zzox, str);
                    i++;
                }
                for (int i2 = 0; i2 < zzox.zzmr().size(); i2++) {
                    zzot zzot2 = (zzot) zzox.zzmr().get(i2);
                    zzfi zzb2 = zzb(this.zzajy, zza(zzot2));
                    zzb2.zza(zzox);
                    zzb2.zzb(zzox, zzot2);
                    zzb2.zzb(zzox, str);
                }
            }
            for (Entry entry : this.zzajq.zzmo().entrySet()) {
                for (zzot zzot3 : (List) entry.getValue()) {
                    if (!zzgj.zzg((zzl) zzot3.zzlu().get(zzb.NOT_DEFAULT_MACRO.toString())).booleanValue()) {
                        zzb(this.zzajy, (String) entry.getKey()).zzb(zzot3);
                    }
                }
            }
            return;
        }
        throw new NullPointerException("resource cannot be null");
    }

    public final synchronized void zze(List<zzj> list) {
        C6219zzc[] zzcArr;
        Long l;
        synchronized (this) {
            for (zzj zzj : list) {
                if (zzj.name != null) {
                    if (zzj.name.startsWith("gaExperiment:")) {
                        DataLayer dataLayer = this.zzaed;
                        if (zzj.zzqi == null) {
                            zzdi.zzac("supplemental missing experimentSupplemental");
                        } else {
                            for (zzl zzc : zzj.zzqi.zzpf) {
                                dataLayer.zzaq(zzgj.zzc(zzc));
                            }
                            zzl[] zzlArr = zzj.zzqi.zzpe;
                            int length = zzlArr.length;
                            int i = 0;
                            while (true) {
                                Map map = null;
                                if (i >= length) {
                                    break;
                                }
                                Object zzh = zzgj.zzh(zzlArr[i]);
                                if (!(zzh instanceof Map)) {
                                    String valueOf = String.valueOf(zzh);
                                    StringBuilder sb = new StringBuilder(String.valueOf(valueOf).length() + 36);
                                    sb.append("value: ");
                                    sb.append(valueOf);
                                    sb.append(" is not a map value, ignored.");
                                    zzdi.zzac(sb.toString());
                                } else {
                                    map = (Map) zzh;
                                }
                                if (map != null) {
                                    dataLayer.push(map);
                                }
                                i++;
                            }
                            for (C6219zzc zzc2 : zzj.zzqi.zzpg) {
                                if (!zzc2.hasKey()) {
                                    zzdi.zzac("GaExperimentRandom: No key");
                                } else {
                                    Object obj = dataLayer.get(zzc2.getKey());
                                    if (!(obj instanceof Number)) {
                                        l = null;
                                    } else {
                                        l = Long.valueOf(((Number) obj).longValue());
                                    }
                                    long zzg = zzc2.zzg();
                                    long zzh2 = zzc2.zzh();
                                    if (!zzc2.zzi() || l == null || l.longValue() < zzg || l.longValue() > zzh2) {
                                        if (zzg <= zzh2) {
                                            double random = Math.random();
                                            double d = (double) (zzh2 - zzg);
                                            Double.isNaN(d);
                                            double d2 = random * d;
                                            double d3 = (double) zzg;
                                            Double.isNaN(d3);
                                            obj = Long.valueOf(Math.round(d2 + d3));
                                        } else {
                                            zzdi.zzac("GaExperimentRandom: random range invalid");
                                        }
                                    }
                                    dataLayer.zzaq(zzc2.getKey());
                                    Map zzg2 = DataLayer.zzg(zzc2.getKey(), obj);
                                    if (zzc2.zzj() > 0) {
                                        if (!zzg2.containsKey("gtm")) {
                                            zzg2.put("gtm", DataLayer.mapOf("lifetime", Long.valueOf(zzc2.zzj())));
                                        } else {
                                            Object obj2 = zzg2.get("gtm");
                                            if (obj2 instanceof Map) {
                                                ((Map) obj2).put("lifetime", Long.valueOf(zzc2.zzj()));
                                            } else {
                                                zzdi.zzac("GaExperimentRandom: gtm not a map");
                                            }
                                        }
                                    }
                                    dataLayer.push(zzg2);
                                }
                            }
                            continue;
                        }
                    }
                }
                String valueOf2 = String.valueOf(zzj);
                StringBuilder sb2 = new StringBuilder(String.valueOf(valueOf2).length() + 22);
                sb2.append("Ignored supplemental: ");
                sb2.append(valueOf2);
                zzdi.zzab(sb2.toString());
            }
        }
    }

    public final synchronized void zzan(String str) {
        zzbk(str);
        zzar zzid = this.zzajr.zzba(str).zzid();
        for (zzot zza : (Set) zza(this.zzajx, (Set<String>) new HashSet<String>(), (zzfg) new zzff(this), zzid.zzhs()).getObject()) {
            zza(this.zzajs, zza, (Set<String>) new HashSet<String>(), zzid.zzhr());
        }
        zzbk(null);
    }

    public final zzdz<zzl> zzbj(String str) {
        this.zzaka = 0;
        return zza(str, (Set<String>) new HashSet<String>(), this.zzajr.zzaz(str).zzic());
    }

    private final synchronized void zzbk(String str) {
        this.zzajz = str;
    }

    /* access modifiers changed from: 0000 */
    public final synchronized String zzjf() {
        return this.zzajz;
    }

    private static zzfi zzb(Map<String, zzfi> map, String str) {
        zzfi zzfi = (zzfi) map.get(str);
        if (zzfi != null) {
            return zzfi;
        }
        zzfi zzfi2 = new zzfi();
        map.put(str, zzfi2);
        return zzfi2;
    }

    private final zzdz<Set<zzot>> zza(Set<zzox> set, Set<String> set2, zzfg zzfg, zzfa zzfa) {
        boolean z;
        zzdz zzdz;
        Set<String> set3 = set2;
        HashSet hashSet = new HashSet();
        HashSet hashSet2 = new HashSet();
        Iterator it = set.iterator();
        Boolean valueOf = Boolean.valueOf(true);
        while (true) {
            boolean z2 = true;
            while (true) {
                if (it.hasNext()) {
                    zzox zzox = (zzox) it.next();
                    zzeq zzis = zzfa.zzis();
                    Iterator it2 = zzox.zzlx().iterator();
                    while (true) {
                        boolean z3 = true;
                        while (true) {
                            if (!it2.hasNext()) {
                                Iterator it3 = zzox.zzlw().iterator();
                                while (true) {
                                    if (!it3.hasNext()) {
                                        zzgj.zzi(valueOf);
                                        zzdz = new zzdz(valueOf, z);
                                        break;
                                    }
                                    zzdz zza = zza((zzot) it3.next(), set3, zzis.zzin());
                                    if (!((Boolean) zza.getObject()).booleanValue()) {
                                        zzgj.zzi(Boolean.valueOf(false));
                                        zzdz = new zzdz(Boolean.valueOf(false), zza.zziu());
                                        break;
                                    }
                                    z = z && zza.zziu();
                                }
                            } else {
                                zzdz zza2 = zza((zzot) it2.next(), set3, zzis.zzim());
                                if (((Boolean) zza2.getObject()).booleanValue()) {
                                    zzgj.zzi(Boolean.valueOf(false));
                                    zzdz = new zzdz(Boolean.valueOf(false), zza2.zziu());
                                    break;
                                } else if (!z || !zza2.zziu()) {
                                    z3 = false;
                                }
                            }
                        }
                    }
                    if (((Boolean) zzdz.getObject()).booleanValue()) {
                        zzfg.zza(zzox, hashSet, hashSet2, zzis);
                    } else {
                        zzfg zzfg2 = zzfg;
                    }
                    if (!z2 || !zzdz.zziu()) {
                        z2 = false;
                    }
                } else {
                    hashSet.removeAll(hashSet2);
                    zzfa.zzb(hashSet);
                    return new zzdz<>(hashSet, z2);
                }
            }
        }
    }

    private static String zza(zzot zzot) {
        return zzgj.zzc((zzl) zzot.zzlu().get(zzb.INSTANCE_NAME.toString()));
    }

    private static void zza(Map<String, zzbq> map, zzbq zzbq) {
        if (map.containsKey(zzbq.zzif())) {
            String str = "Duplicate function type name: ";
            String valueOf = String.valueOf(zzbq.zzif());
            throw new IllegalArgumentException(valueOf.length() != 0 ? str.concat(valueOf) : new String(str));
        }
        map.put(zzbq.zzif(), zzbq);
    }

    private final void zza(zzbq zzbq) {
        zza(this.zzaju, zzbq);
    }

    private final void zzb(zzbq zzbq) {
        zza(this.zzajs, zzbq);
    }

    private final void zzc(zzbq zzbq) {
        zza(this.zzajt, zzbq);
    }

    private final zzdz<Boolean> zza(zzot zzot, Set<String> set, zzen zzen) {
        zzdz zza = zza(this.zzajt, zzot, set, zzen);
        Boolean zzg = zzgj.zzg((zzl) zza.getObject());
        zzen.zza(zzgj.zzi(zzg));
        return new zzdz<>(zzg, zza.zziu());
    }

    private final String zzjg() {
        if (this.zzaka <= 1) {
            return "";
        }
        StringBuilder sb = new StringBuilder();
        sb.append(Integer.toString(this.zzaka));
        for (int i = 2; i < this.zzaka; i++) {
            sb.append(' ');
        }
        sb.append(": ");
        return sb.toString();
    }

    private final zzdz<zzl> zza(String str, Set<String> set, zzdl zzdl) {
        zzot zzot;
        this.zzaka++;
        zzfh zzfh = (zzfh) this.zzajw.get(str);
        if (zzfh != null) {
            this.zzajr.zzie();
            zza(zzfh.zzji(), set);
            this.zzaka--;
            return zzfh.zzjh();
        }
        zzfi zzfi = (zzfi) this.zzajy.get(str);
        if (zzfi == null) {
            String zzjg = zzjg();
            StringBuilder sb = new StringBuilder(String.valueOf(zzjg).length() + 15 + String.valueOf(str).length());
            sb.append(zzjg);
            sb.append("Invalid macro: ");
            sb.append(str);
            zzdi.zzav(sb.toString());
            this.zzaka--;
            return zzajp;
        }
        Set zzjj = zzfi.zzjj();
        Map zzjk = zzfi.zzjk();
        Map zzjl = zzfi.zzjl();
        Map zzjn = zzfi.zzjn();
        Map zzjm = zzfi.zzjm();
        zzfa zzhs = zzdl.zzhs();
        zzfe zzfe = new zzfe(this, zzjk, zzjl, zzjn, zzjm);
        zzdz zza = zza(zzjj, set, (zzfg) zzfe, zzhs);
        if (((Set) zza.getObject()).isEmpty()) {
            zzot = zzfi.zzjo();
        } else {
            if (((Set) zza.getObject()).size() > 1) {
                String zzjg2 = zzjg();
                StringBuilder sb2 = new StringBuilder(String.valueOf(zzjg2).length() + 37 + String.valueOf(str).length());
                sb2.append(zzjg2);
                sb2.append("Multiple macros active for macroName ");
                sb2.append(str);
                zzdi.zzac(sb2.toString());
            }
            zzot = (zzot) ((Set) zza.getObject()).iterator().next();
        }
        if (zzot == null) {
            this.zzaka--;
            return zzajp;
        }
        zzdz<zzl> zza2 = zza(this.zzaju, zzot, set, zzdl.zzil());
        boolean z = zza.zziu() && zza2.zziu();
        zzdz<zzl> zzdz = zzajp;
        if (zza2 != zzdz) {
            zzdz = new zzdz<>((zzl) zza2.getObject(), z);
        }
        zzl zzji = zzot.zzji();
        if (zzdz.zziu()) {
            this.zzajw.zza(str, new zzfh(zzdz, zzji));
        }
        zza(zzji, set);
        this.zzaka--;
        return zzdz;
    }

    private final void zza(zzl zzl, Set<String> set) {
        if (zzl != null) {
            zzdz<zzl> zza = zza(zzl, set, (zzgm) new zzdx());
            if (zza != zzajp) {
                Object zzh = zzgj.zzh((zzl) zza.getObject());
                if (zzh instanceof Map) {
                    this.zzaed.push((Map) zzh);
                } else if (zzh instanceof List) {
                    for (Object next : (List) zzh) {
                        if (next instanceof Map) {
                            this.zzaed.push((Map) next);
                        } else {
                            zzdi.zzac("pushAfterEvaluate: value not a Map");
                        }
                    }
                } else {
                    zzdi.zzac("pushAfterEvaluate: value not a Map or List");
                }
            }
        }
    }

    private final zzdz<zzl> zza(zzl zzl, Set<String> set, zzgm zzgm) {
        if (!zzl.zzqw) {
            return new zzdz<>(zzl, true);
        }
        int i = zzl.type;
        if (i == 2) {
            zzl zzk = zzor.zzk(zzl);
            zzk.zzqn = new zzl[zzl.zzqn.length];
            for (int i2 = 0; i2 < zzl.zzqn.length; i2++) {
                zzdz<zzl> zza = zza(zzl.zzqn[i2], set, zzgm.zzv(i2));
                zzdz<zzl> zzdz = zzajp;
                if (zza == zzdz) {
                    return zzdz;
                }
                zzk.zzqn[i2] = (zzl) zza.getObject();
            }
            return new zzdz<>(zzk, false);
        } else if (i == 3) {
            zzl zzk2 = zzor.zzk(zzl);
            if (zzl.zzqo.length != zzl.zzqp.length) {
                String str = "Invalid serving value: ";
                String valueOf = String.valueOf(zzl.toString());
                zzdi.zzav(valueOf.length() != 0 ? str.concat(valueOf) : new String(str));
                return zzajp;
            }
            zzk2.zzqo = new zzl[zzl.zzqo.length];
            zzk2.zzqp = new zzl[zzl.zzqo.length];
            for (int i3 = 0; i3 < zzl.zzqo.length; i3++) {
                zzdz<zzl> zza2 = zza(zzl.zzqo[i3], set, zzgm.zzw(i3));
                zzdz<zzl> zza3 = zza(zzl.zzqp[i3], set, zzgm.zzx(i3));
                zzdz<zzl> zzdz2 = zzajp;
                if (zza2 == zzdz2 || zza3 == zzdz2) {
                    return zzajp;
                }
                zzk2.zzqo[i3] = (zzl) zza2.getObject();
                zzk2.zzqp[i3] = (zzl) zza3.getObject();
            }
            return new zzdz<>(zzk2, false);
        } else if (i != 4) {
            if (i != 7) {
                int i4 = zzl.type;
                StringBuilder sb = new StringBuilder(25);
                sb.append("Unknown type: ");
                sb.append(i4);
                zzdi.zzav(sb.toString());
                return zzajp;
            }
            zzl zzk3 = zzor.zzk(zzl);
            zzk3.zzqu = new zzl[zzl.zzqu.length];
            for (int i5 = 0; i5 < zzl.zzqu.length; i5++) {
                zzdz<zzl> zza4 = zza(zzl.zzqu[i5], set, zzgm.zzy(i5));
                zzdz<zzl> zzdz3 = zzajp;
                if (zza4 == zzdz3) {
                    return zzdz3;
                }
                zzk3.zzqu[i5] = (zzl) zza4.getObject();
            }
            return new zzdz<>(zzk3, false);
        } else if (set.contains(zzl.zzqq)) {
            String str2 = zzl.zzqq;
            String obj = set.toString();
            StringBuilder sb2 = new StringBuilder(String.valueOf(str2).length() + 79 + String.valueOf(obj).length());
            sb2.append("Macro cycle detected.  Current macro reference: ");
            sb2.append(str2);
            sb2.append(".  Previous macro references: ");
            sb2.append(obj);
            sb2.append(".");
            zzdi.zzav(sb2.toString());
            return zzajp;
        } else {
            set.add(zzl.zzqq);
            zzdz<zzl> zza5 = zzgn.zza(zza(zzl.zzqq, set, zzgm.zzit()), zzl.zzqv);
            set.remove(zzl.zzqq);
            return zza5;
        }
    }

    private final zzdz<zzl> zza(Map<String, zzbq> map, zzot zzot, Set<String> set, zzen zzen) {
        zzl zzl = (zzl) zzot.zzlu().get(zzb.FUNCTION.toString());
        if (zzl == null) {
            zzdi.zzav("No function id in properties");
            return zzajp;
        }
        String str = zzl.zzqr;
        zzbq zzbq = (zzbq) map.get(str);
        if (zzbq == null) {
            zzdi.zzav(String.valueOf(str).concat(" has no backing implementation."));
            return zzajp;
        }
        zzdz<zzl> zzdz = (zzdz) this.zzajv.get(zzot);
        if (zzdz != null) {
            this.zzajr.zzie();
            return zzdz;
        }
        HashMap hashMap = new HashMap();
        boolean z = true;
        boolean z2 = true;
        for (Entry entry : zzot.zzlu().entrySet()) {
            zzdz<zzl> zza = zza((zzl) entry.getValue(), set, zzen.zzbg((String) entry.getKey()).zzb((zzl) entry.getValue()));
            zzdz<zzl> zzdz2 = zzajp;
            if (zza == zzdz2) {
                return zzdz2;
            }
            if (zza.zziu()) {
                zzot.zza((String) entry.getKey(), (zzl) zza.getObject());
            } else {
                z2 = false;
            }
            hashMap.put((String) entry.getKey(), (zzl) zza.getObject());
        }
        if (!zzbq.zza(hashMap.keySet())) {
            String valueOf = String.valueOf(zzbq.zzig());
            String valueOf2 = String.valueOf(hashMap.keySet());
            StringBuilder sb = new StringBuilder(String.valueOf(str).length() + 43 + String.valueOf(valueOf).length() + String.valueOf(valueOf2).length());
            sb.append("Incorrect keys for function ");
            sb.append(str);
            sb.append(" required ");
            sb.append(valueOf);
            sb.append(" had ");
            sb.append(valueOf2);
            zzdi.zzav(sb.toString());
            return zzajp;
        }
        if (!z2 || !zzbq.zzgw()) {
            z = false;
        }
        zzdz<zzl> zzdz3 = new zzdz<>(zzbq.zzb(hashMap), z);
        if (z) {
            this.zzajv.zza(zzot, zzdz3);
        }
        zzen.zza((zzl) zzdz3.getObject());
        return zzdz3;
    }
}
