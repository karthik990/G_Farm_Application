package com.google.android.gms.internal.firebase_auth;

import com.google.android.gms.internal.firebase_auth.zzhs.zzd;

final class zzip implements zzjr {
    private static final zziz zzacg = new zzis();
    private final zziz zzacf;

    public zzip() {
        this(new zzir(zzhq.zzib(), zzjh()));
    }

    private zzip(zziz zziz) {
        this.zzacf = (zziz) zzht.zza(zziz, "messageInfoFactory");
    }

    public final <T> zzjs<T> zze(Class<T> cls) {
        zzju.zzg(cls);
        zzja zzb = this.zzacf.zzb(cls);
        if (zzb.zzjp()) {
            if (zzhs.class.isAssignableFrom(cls)) {
                return zzjf.zza(zzju.zzkf(), zzhj.zzhv(), zzb.zzjq());
            }
            return zzjf.zza(zzju.zzkd(), zzhj.zzhw(), zzb.zzjq());
        } else if (zzhs.class.isAssignableFrom(cls)) {
            if (zza(zzb)) {
                return zzjg.zza(cls, zzb, zzjj.zzjs(), zzim.zzjg(), zzju.zzkf(), zzhj.zzhv(), zzix.zzjm());
            }
            return zzjg.zza(cls, zzb, zzjj.zzjs(), zzim.zzjg(), zzju.zzkf(), null, zzix.zzjm());
        } else if (zza(zzb)) {
            return zzjg.zza(cls, zzb, zzjj.zzjr(), zzim.zzjf(), zzju.zzkd(), zzhj.zzhw(), zzix.zzjl());
        } else {
            return zzjg.zza(cls, zzb, zzjj.zzjr(), zzim.zzjf(), zzju.zzke(), null, zzix.zzjl());
        }
    }

    private static boolean zza(zzja zzja) {
        return zzja.zzjo() == zzd.zzaav;
    }

    private static zziz zzjh() {
        try {
            return (zziz) Class.forName("com.google.protobuf.DescriptorMessageInfoFactory").getDeclaredMethod("getInstance", new Class[0]).invoke(null, new Object[0]);
        } catch (Exception unused) {
            return zzacg;
        }
    }
}
