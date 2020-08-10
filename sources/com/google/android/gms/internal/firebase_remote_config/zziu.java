package com.google.android.gms.internal.firebase_remote_config;

import com.google.android.gms.internal.firebase_remote_config.zzhi.zzc;
import com.google.android.gms.internal.firebase_remote_config.zzhi.zzd;
import java.io.IOException;
import java.util.Iterator;
import java.util.Map.Entry;

final class zziu<T> implements zzjj<T> {
    private final zzio zzvo;
    private final boolean zzvp;
    private final zzkb<?, ?> zzvy;
    private final zzgx<?> zzvz;

    private zziu(zzkb<?, ?> zzkb, zzgx<?> zzgx, zzio zzio) {
        this.zzvy = zzkb;
        this.zzvp = zzgx.zze(zzio);
        this.zzvz = zzgx;
        this.zzvo = zzio;
    }

    static <T> zziu<T> zza(zzkb<?, ?> zzkb, zzgx<?> zzgx, zzio zzio) {
        return new zziu<>(zzkb, zzgx, zzio);
    }

    public final T newInstance() {
        return this.zzvo.zzgy().zzhc();
    }

    public final boolean equals(T t, T t2) {
        if (!this.zzvy.zzaa(t).equals(this.zzvy.zzaa(t2))) {
            return false;
        }
        if (this.zzvp) {
            return this.zzvz.zzk(t).equals(this.zzvz.zzk(t2));
        }
        return true;
    }

    public final int hashCode(T t) {
        int hashCode = this.zzvy.zzaa(t).hashCode();
        return this.zzvp ? (hashCode * 53) + this.zzvz.zzk(t).hashCode() : hashCode;
    }

    public final void zze(T t, T t2) {
        zzjl.zza(this.zzvy, t, t2);
        if (this.zzvp) {
            zzjl.zza(this.zzvz, t, t2);
        }
    }

    public final void zza(T t, zzkw zzkw) throws IOException {
        Iterator it = this.zzvz.zzk(t).iterator();
        while (it.hasNext()) {
            Entry entry = (Entry) it.next();
            zzhc zzhc = (zzhc) entry.getKey();
            if (zzhc.zzgs() != zzkv.MESSAGE || zzhc.zzgt() || zzhc.zzgu()) {
                throw new IllegalStateException("Found invalid MessageSet item.");
            } else if (entry instanceof zzht) {
                zzkw.zza(zzhc.zzgq(), (Object) ((zzht) entry).zzhr().zzen());
            } else {
                zzkw.zza(zzhc.zzgq(), entry.getValue());
            }
        }
        zzkb<?, ?> zzkb = this.zzvy;
        zzkb.zzc(zzkb.zzaa(t), zzkw);
    }

    public final void zza(T t, byte[] bArr, int i, int i2, zzft zzft) throws IOException {
        zzhi zzhi = (zzhi) t;
        zzkc zzkc = zzhi.zzsw;
        if (zzkc == zzkc.zzje()) {
            zzkc = zzkc.zzjf();
            zzhi.zzsw = zzkc;
        }
        ((zzc) t).zzhe();
        zzd zzd = null;
        while (i < i2) {
            int zza = zzfs.zza(bArr, i, zzft);
            int i3 = zzft.zzoj;
            if (i3 == 11) {
                int i4 = 0;
                Object obj = null;
                while (zza < i2) {
                    zza = zzfs.zza(bArr, zza, zzft);
                    int i5 = zzft.zzoj;
                    int i6 = i5 >>> 3;
                    int i7 = i5 & 7;
                    if (i6 != 2) {
                        if (i6 == 3) {
                            if (zzd != null) {
                                zzjb.zzik();
                                throw new NoSuchMethodError();
                            } else if (i7 == 2) {
                                zza = zzfs.zze(bArr, zza, zzft);
                                obj = (zzfw) zzft.zzol;
                            }
                        }
                    } else if (i7 == 0) {
                        zza = zzfs.zza(bArr, zza, zzft);
                        i4 = zzft.zzoj;
                        zzd = (zzd) this.zzvz.zza(zzft.zzom, this.zzvo, i4);
                    }
                    if (i5 == 12) {
                        break;
                    }
                    zza = zzfs.zza(i5, bArr, zza, i2, zzft);
                }
                if (obj != null) {
                    zzkc.zzb((i4 << 3) | 2, obj);
                }
                i = zza;
            } else if ((i3 & 7) == 2) {
                zzd = (zzd) this.zzvz.zza(zzft.zzom, this.zzvo, i3 >>> 3);
                if (zzd == null) {
                    i = zzfs.zza(i3, bArr, zza, i2, zzkc, zzft);
                } else {
                    zzjb.zzik();
                    throw new NoSuchMethodError();
                }
            } else {
                i = zzfs.zza(i3, bArr, zza, i2, zzft);
            }
        }
        if (i != i2) {
            throw zzho.zzhn();
        }
    }

    public final void zza(T t, zzje zzje, zzgv zzgv) throws IOException {
        boolean z;
        zzkb<?, ?> zzkb = this.zzvy;
        zzgx<?> zzgx = this.zzvz;
        Object zzab = zzkb.zzab(t);
        zzha zzl = zzgx.zzl(t);
        do {
            try {
                if (zzje.zzgb() == Integer.MAX_VALUE) {
                    zzkb.zzh(t, zzab);
                    return;
                }
                int tag = zzje.getTag();
                if (tag == 11) {
                    int i = 0;
                    Object obj = null;
                    zzfw zzfw = null;
                    while (zzje.zzgb() != Integer.MAX_VALUE) {
                        int tag2 = zzje.getTag();
                        if (tag2 == 16) {
                            i = zzje.zzfm();
                            obj = zzgx.zza(zzgv, this.zzvo, i);
                        } else if (tag2 == 26) {
                            if (obj != null) {
                                zzgx.zza(zzje, obj, zzgv, zzl);
                            } else {
                                zzfw = zzje.zzfl();
                            }
                        } else if (!zzje.zzgc()) {
                            break;
                        }
                    }
                    if (zzje.getTag() != 12) {
                        throw zzho.zzhk();
                    } else if (zzfw != null) {
                        if (obj != null) {
                            zzgx.zza(zzfw, obj, zzgv, zzl);
                        } else {
                            zzkb.zza(zzab, i, zzfw);
                        }
                    }
                } else if ((tag & 7) == 2) {
                    Object zza = zzgx.zza(zzgv, this.zzvo, tag >>> 3);
                    if (zza != null) {
                        zzgx.zza(zzje, zza, zzgv, zzl);
                    } else {
                        z = zzkb.zza(zzab, zzje);
                        continue;
                    }
                } else {
                    z = zzje.zzgc();
                    continue;
                }
                z = true;
                continue;
            } finally {
                zzkb.zzh(t, zzab);
            }
        } while (z);
    }

    public final void zzm(T t) {
        this.zzvy.zzm(t);
        this.zzvz.zzm(t);
    }

    public final boolean zzy(T t) {
        return this.zzvz.zzk(t).isInitialized();
    }

    public final int zzw(T t) {
        zzkb<?, ?> zzkb = this.zzvy;
        int zzac = zzkb.zzac(zzkb.zzaa(t)) + 0;
        return this.zzvp ? zzac + this.zzvz.zzk(t).zzgp() : zzac;
    }
}
