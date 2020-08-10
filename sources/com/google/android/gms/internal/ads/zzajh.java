package com.google.android.gms.internal.ads;

import java.util.Collections;
import java.util.List;
import javax.annotation.ParametersAreNonnullByDefault;
import org.json.JSONObject;

@zzadh
@ParametersAreNonnullByDefault
public final class zzajh {
    public final int errorCode;
    public final int orientation;
    public final List<String> zzbsn;
    public final List<String> zzbso;
    public final List<String> zzbsp;
    public final List<String> zzbsr;
    public final long zzbsu;
    public final zzwx zzbtw;
    public final zzxq zzbtx;
    public final String zzbty;
    public final zzxa zzbtz;
    public final zzaqw zzbyo;
    public final zzjj zzccv;
    public final String zzccy;
    private final long zzcep;
    public final boolean zzceq;
    private final long zzcer;
    public final List<String> zzces;
    public final String zzcev;
    public final zzaig zzcfe;
    public final List<String> zzcfg;
    public final boolean zzcfh;
    private final zzael zzcfi;
    public final String zzcfl;
    public final boolean zzcfp;
    private final String zzcfq;
    public final JSONObject zzcob;
    public boolean zzcoc;
    public final zzwy zzcod;
    public final String zzcoe;
    public final zzjn zzcof;
    public final List<String> zzcog;
    public final long zzcoh;
    public final long zzcoi;
    public final zzpb zzcoj;
    public boolean zzcok;
    public boolean zzcol;
    public boolean zzcom;
    public boolean zzcon;
    public boolean zzcoo;
    public boolean zzcop;
    public final zzhs zzcoq;
    public final boolean zzcor;
    public final boolean zzzl;
    public final boolean zzzm;

    public zzajh(zzaji zzaji, zzaqw zzaqw, zzwx zzwx, zzxq zzxq, String str, zzxa zzxa, zzpb zzpb, String str2) {
        zzaji zzaji2 = zzaji;
        this(zzaji2.zzcgs.zzccv, null, zzaji2.zzcos.zzbsn, zzaji2.errorCode, zzaji2.zzcos.zzbso, zzaji2.zzcos.zzces, zzaji2.zzcos.orientation, zzaji2.zzcos.zzbsu, zzaji2.zzcgs.zzccy, zzaji2.zzcos.zzceq, null, null, null, zzaji2.zzcod, null, zzaji2.zzcos.zzcer, zzaji2.zzacv, zzaji2.zzcos.zzcep, zzaji2.zzcoh, zzaji2.zzcoi, zzaji2.zzcos.zzcev, zzaji2.zzcob, null, zzaji2.zzcos.zzcfe, zzaji2.zzcos.zzcff, zzaji2.zzcos.zzcff, zzaji2.zzcos.zzcfh, zzaji2.zzcos.zzcfi, null, zzaji2.zzcos.zzbsr, zzaji2.zzcos.zzcfl, zzaji2.zzcoq, zzaji2.zzcos.zzzl, zzaji2.zzcor, zzaji2.zzcos.zzcfp, zzaji2.zzcos.zzbsp, zzaji2.zzcos.zzzm, zzaji2.zzcos.zzcfq);
    }

    public zzajh(zzjj zzjj, zzaqw zzaqw, List<String> list, int i, List<String> list2, List<String> list3, int i2, long j, String str, boolean z, zzwx zzwx, zzxq zzxq, String str2, zzwy zzwy, zzxa zzxa, long j2, zzjn zzjn, long j3, long j4, long j5, String str3, JSONObject jSONObject, zzpb zzpb, zzaig zzaig, List<String> list4, List<String> list5, boolean z2, zzael zzael, String str4, List<String> list6, String str5, zzhs zzhs, boolean z3, boolean z4, boolean z5, List<String> list7, boolean z6, String str6) {
        this.zzcok = false;
        this.zzcol = false;
        this.zzcom = false;
        this.zzcon = false;
        this.zzcoo = false;
        this.zzcop = false;
        this.zzccv = zzjj;
        this.zzbyo = zzaqw;
        this.zzbsn = zzn(list);
        this.errorCode = i;
        this.zzbso = zzn(list2);
        this.zzces = zzn(list3);
        this.orientation = i2;
        this.zzbsu = j;
        this.zzccy = str;
        this.zzceq = z;
        this.zzbtw = zzwx;
        this.zzbtx = zzxq;
        this.zzbty = str2;
        this.zzcod = zzwy;
        this.zzbtz = zzxa;
        this.zzcer = j2;
        this.zzcof = zzjn;
        this.zzcep = j3;
        this.zzcoh = j4;
        this.zzcoi = j5;
        this.zzcev = str3;
        this.zzcob = jSONObject;
        this.zzcoj = zzpb;
        this.zzcfe = zzaig;
        this.zzcog = zzn(list4);
        this.zzcfg = zzn(list5);
        this.zzcfh = z2;
        this.zzcfi = zzael;
        this.zzcoe = str4;
        this.zzbsr = zzn(list6);
        this.zzcfl = str5;
        this.zzcoq = zzhs;
        this.zzzl = z3;
        this.zzcor = z4;
        this.zzcfp = z5;
        this.zzbsp = zzn(list7);
        this.zzzm = z6;
        this.zzcfq = str6;
    }

    private static <T> List<T> zzn(List<T> list) {
        if (list == null) {
            return null;
        }
        return Collections.unmodifiableList(list);
    }

    public final boolean zzfz() {
        zzaqw zzaqw = this.zzbyo;
        if (zzaqw == null || zzaqw.zzuf() == null) {
            return false;
        }
        return this.zzbyo.zzuf().zzfz();
    }
}
