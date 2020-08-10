package com.google.android.gms.internal.firebase_auth;

import java.io.IOException;
import java.util.Iterator;
import java.util.Map.Entry;

final class zzjf<T> implements zzjs<T> {
    private final zzjc zzacr;
    private final zzkk<?, ?> zzacs;
    private final boolean zzact;
    private final zzhh<?> zzacu;

    private zzjf(zzkk<?, ?> zzkk, zzhh<?> zzhh, zzjc zzjc) {
        this.zzacs = zzkk;
        this.zzact = zzhh.zzf(zzjc);
        this.zzacu = zzhh;
        this.zzacr = zzjc;
    }

    static <T> zzjf<T> zza(zzkk<?, ?> zzkk, zzhh<?> zzhh, zzjc zzjc) {
        return new zzjf<>(zzkk, zzhh, zzjc);
    }

    public final T newInstance() {
        return this.zzacr.zzio().zzig();
    }

    public final boolean equals(T t, T t2) {
        if (!this.zzacs.zzs(t).equals(this.zzacs.zzs(t2))) {
            return false;
        }
        if (this.zzact) {
            return this.zzacu.zzd(t).equals(this.zzacu.zzd(t2));
        }
        return true;
    }

    public final int hashCode(T t) {
        int hashCode = this.zzacs.zzs(t).hashCode();
        return this.zzact ? (hashCode * 53) + this.zzacu.zzd(t).hashCode() : hashCode;
    }

    public final void zzd(T t, T t2) {
        zzju.zza(this.zzacs, t, t2);
        if (this.zzact) {
            zzju.zza(this.zzacu, t, t2);
        }
    }

    public final void zza(T t, zzlh zzlh) throws IOException {
        Iterator it = this.zzacu.zzd(t).iterator();
        while (it.hasNext()) {
            Entry entry = (Entry) it.next();
            zzhk zzhk = (zzhk) entry.getKey();
            if (zzhk.zzhy() != zzle.MESSAGE || zzhk.zzhz() || zzhk.zzia()) {
                throw new IllegalStateException("Found invalid MessageSet item.");
            } else if (entry instanceof zzif) {
                zzlh.zza(zzhk.zzbq(), (Object) ((zzif) entry).zzjc().zzft());
            } else {
                zzlh.zza(zzhk.zzbq(), entry.getValue());
            }
        }
        zzkk<?, ?> zzkk = this.zzacs;
        zzkk.zzc(zzkk.zzs(t), zzlh);
    }

    public final void zza(T t, zzjp zzjp, zzhf zzhf) throws IOException {
        boolean z;
        zzkk<?, ?> zzkk = this.zzacs;
        zzhh<?> zzhh = this.zzacu;
        Object zzt = zzkk.zzt(t);
        zzhi zze = zzhh.zze(t);
        do {
            try {
                if (zzjp.zzhg() == Integer.MAX_VALUE) {
                    zzkk.zzg(t, zzt);
                    return;
                }
                int tag = zzjp.getTag();
                if (tag == 11) {
                    int i = 0;
                    Object obj = null;
                    zzgf zzgf = null;
                    while (zzjp.zzhg() != Integer.MAX_VALUE) {
                        int tag2 = zzjp.getTag();
                        if (tag2 == 16) {
                            i = zzjp.zzgr();
                            obj = zzhh.zza(zzhf, this.zzacr, i);
                        } else if (tag2 == 26) {
                            if (obj != null) {
                                zzhh.zza(zzjp, obj, zzhf, zze);
                            } else {
                                zzgf = zzjp.zzgq();
                            }
                        } else if (!zzjp.zzhh()) {
                            break;
                        }
                    }
                    if (zzjp.getTag() != 12) {
                        throw zzic.zziv();
                    } else if (zzgf != null) {
                        if (obj != null) {
                            zzhh.zza(zzgf, obj, zzhf, zze);
                        } else {
                            zzkk.zza(zzt, i, zzgf);
                        }
                    }
                } else if ((tag & 7) == 2) {
                    Object zza = zzhh.zza(zzhf, this.zzacr, tag >>> 3);
                    if (zza != null) {
                        zzhh.zza(zzjp, zza, zzhf, zze);
                    } else {
                        z = zzkk.zza(zzt, zzjp);
                        continue;
                    }
                } else {
                    z = zzjp.zzhh();
                    continue;
                }
                z = true;
                continue;
            } finally {
                zzkk.zzg(t, zzt);
            }
        } while (z);
    }

    public final void zzf(T t) {
        this.zzacs.zzf(t);
        this.zzacu.zzf((Object) t);
    }

    public final boolean zzp(T t) {
        return this.zzacu.zzd(t).isInitialized();
    }

    public final int zzq(T t) {
        zzkk<?, ?> zzkk = this.zzacs;
        int zzu = zzkk.zzu(zzkk.zzs(t)) + 0;
        return this.zzact ? zzu + this.zzacu.zzd(t).zzht() : zzu;
    }
}
