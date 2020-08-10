package com.google.android.gms.internal.gtm;

import com.google.android.gms.internal.gtm.zzuq;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public final class zzur<M extends zzuq<M>, T> {
    public final int tag;
    private final int type;
    private final zzrc<?, ?> zzban;
    protected final Class<T> zzbhc;
    protected final boolean zzbhd;

    public static <M extends zzuq<M>, T extends zzuw> zzur<M, T> zza(int i, Class<T> cls, long j) {
        return new zzur<>(11, cls, 810, false);
    }

    private zzur(int i, Class<T> cls, int i2, boolean z) {
        this(11, cls, null, 810, false);
    }

    private zzur(int i, Class<T> cls, zzrc<?, ?> zzrc, int i2, boolean z) {
        this.type = i;
        this.zzbhc = cls;
        this.tag = i2;
        this.zzbhd = false;
        this.zzban = null;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof zzur)) {
            return false;
        }
        zzur zzur = (zzur) obj;
        return this.type == zzur.type && this.zzbhc == zzur.zzbhc && this.tag == zzur.tag && this.zzbhd == zzur.zzbhd;
    }

    public final int hashCode() {
        return ((((((this.type + 1147) * 31) + this.zzbhc.hashCode()) * 31) + this.tag) * 31) + (this.zzbhd ? 1 : 0);
    }

    /* access modifiers changed from: 0000 */
    public final T zzag(List<zzuy> list) {
        if (list == null) {
            return null;
        }
        if (this.zzbhd) {
            ArrayList arrayList = new ArrayList();
            for (int i = 0; i < list.size(); i++) {
                zzuy zzuy = (zzuy) list.get(i);
                if (zzuy.zzawe.length != 0) {
                    arrayList.add(zzc(zzun.zzk(zzuy.zzawe)));
                }
            }
            int size = arrayList.size();
            if (size == 0) {
                return null;
            }
            Class<T> cls = this.zzbhc;
            T cast = cls.cast(Array.newInstance(cls.getComponentType(), size));
            for (int i2 = 0; i2 < size; i2++) {
                Array.set(cast, i2, arrayList.get(i2));
            }
            return cast;
        } else if (list.isEmpty()) {
            return null;
        } else {
            return this.zzbhc.cast(zzc(zzun.zzk(((zzuy) list.get(list.size() - 1)).zzawe)));
        }
    }

    private final Object zzc(zzun zzun) {
        String str = "Error creating instance of class ";
        Class<T> componentType = this.zzbhd ? this.zzbhc.getComponentType() : this.zzbhc;
        try {
            int i = this.type;
            if (i == 10) {
                zzuw zzuw = (zzuw) componentType.newInstance();
                zzun.zza(zzuw, this.tag >>> 3);
                return zzuw;
            } else if (i == 11) {
                zzuw zzuw2 = (zzuw) componentType.newInstance();
                zzun.zza(zzuw2);
                return zzuw2;
            } else {
                int i2 = this.type;
                StringBuilder sb = new StringBuilder(24);
                sb.append("Unknown type ");
                sb.append(i2);
                throw new IllegalArgumentException(sb.toString());
            }
        } catch (InstantiationException e) {
            String valueOf = String.valueOf(componentType);
            StringBuilder sb2 = new StringBuilder(String.valueOf(valueOf).length() + 33);
            sb2.append(str);
            sb2.append(valueOf);
            throw new IllegalArgumentException(sb2.toString(), e);
        } catch (IllegalAccessException e2) {
            String valueOf2 = String.valueOf(componentType);
            StringBuilder sb3 = new StringBuilder(String.valueOf(valueOf2).length() + 33);
            sb3.append(str);
            sb3.append(valueOf2);
            throw new IllegalArgumentException(sb3.toString(), e2);
        } catch (IOException e3) {
            throw new IllegalArgumentException("Error reading extension field", e3);
        }
    }

    /* access modifiers changed from: protected */
    public final void zza(Object obj, zzuo zzuo) {
        try {
            zzuo.zzcb(this.tag);
            int i = this.type;
            if (i == 10) {
                int i2 = this.tag >>> 3;
                ((zzuw) obj).zza(zzuo);
                zzuo.zzd(i2, 4);
            } else if (i == 11) {
                zzuo.zzb((zzuw) obj);
            } else {
                int i3 = this.type;
                StringBuilder sb = new StringBuilder(24);
                sb.append("Unknown type ");
                sb.append(i3);
                throw new IllegalArgumentException(sb.toString());
            }
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
    }

    /* access modifiers changed from: protected */
    public final int zzaj(Object obj) {
        int i = this.tag >>> 3;
        int i2 = this.type;
        if (i2 == 10) {
            return (zzuo.zzbb(i) << 1) + ((zzuw) obj).zzpe();
        } else if (i2 == 11) {
            return zzuo.zzb(i, (zzuw) obj);
        } else {
            StringBuilder sb = new StringBuilder(24);
            sb.append("Unknown type ");
            sb.append(i2);
            throw new IllegalArgumentException(sb.toString());
        }
    }
}
