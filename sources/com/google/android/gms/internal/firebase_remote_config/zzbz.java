package com.google.android.gms.internal.firebase_remote_config;

import java.util.AbstractMap;
import java.util.AbstractSet;
import java.util.EnumSet;
import java.util.Iterator;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

public class zzbz extends AbstractMap<String, Object> implements Cloneable {
    final zzbq zzar;
    Map<String, Object> zzfu;

    final class zza implements Iterator<Entry<String, Object>> {
        private boolean zzfv;
        private final Iterator<Entry<String, Object>> zzfw;
        private final Iterator<Entry<String, Object>> zzfx;

        zza(zzbz zzbz, zzbw zzbw) {
            this.zzfw = (zzbv) zzbw.iterator();
            this.zzfx = zzbz.zzfu.entrySet().iterator();
        }

        public final boolean hasNext() {
            return this.zzfw.hasNext() || this.zzfx.hasNext();
        }

        public final void remove() {
            if (this.zzfv) {
                this.zzfx.remove();
            }
            this.zzfw.remove();
        }

        public final /* synthetic */ Object next() {
            if (!this.zzfv) {
                if (this.zzfw.hasNext()) {
                    return (Entry) this.zzfw.next();
                }
                this.zzfv = true;
            }
            return (Entry) this.zzfx.next();
        }
    }

    final class zzb extends AbstractSet<Entry<String, Object>> {
        private final zzbw zzfy;

        zzb() {
            this.zzfy = (zzbw) new zzbt(zzbz.this, zzbz.this.zzar.zzbw()).entrySet();
        }

        public final Iterator<Entry<String, Object>> iterator() {
            return new zza(zzbz.this, this.zzfy);
        }

        public final int size() {
            return zzbz.this.zzfu.size() + this.zzfy.size();
        }

        public final void clear() {
            zzbz.this.zzfu.clear();
            this.zzfy.clear();
        }
    }

    public enum zzc {
        IGNORE_CASE
    }

    public zzbz() {
        this(EnumSet.noneOf(zzc.class));
    }

    public zzbz(EnumSet<zzc> enumSet) {
        this.zzfu = new zzbl();
        this.zzar = zzbq.zza(getClass(), enumSet.contains(zzc.IGNORE_CASE));
    }

    public final Object get(Object obj) {
        if (!(obj instanceof String)) {
            return null;
        }
        String str = (String) obj;
        zzby zzae = this.zzar.zzae(str);
        if (zzae != null) {
            return zzae.zzh(this);
        }
        if (this.zzar.zzbw()) {
            str = str.toLowerCase(Locale.US);
        }
        return this.zzfu.get(str);
    }

    /* renamed from: zzf */
    public final Object put(String str, Object obj) {
        zzby zzae = this.zzar.zzae(str);
        if (zzae != null) {
            Object zzh = zzae.zzh(this);
            zzae.zzb(this, obj);
            return zzh;
        }
        if (this.zzar.zzbw()) {
            str = str.toLowerCase(Locale.US);
        }
        return this.zzfu.put(str, obj);
    }

    public zzbz zzb(String str, Object obj) {
        zzby zzae = this.zzar.zzae(str);
        if (zzae != null) {
            zzae.zzb(this, obj);
        } else {
            if (this.zzar.zzbw()) {
                str = str.toLowerCase(Locale.US);
            }
            this.zzfu.put(str, obj);
        }
        return this;
    }

    public final void putAll(Map<? extends String, ?> map) {
        for (Entry entry : map.entrySet()) {
            zzb((String) entry.getKey(), entry.getValue());
        }
    }

    public final Object remove(Object obj) {
        if (!(obj instanceof String)) {
            return null;
        }
        String str = (String) obj;
        if (this.zzar.zzae(str) == null) {
            if (this.zzar.zzbw()) {
                str = str.toLowerCase(Locale.US);
            }
            return this.zzfu.remove(str);
        }
        throw new UnsupportedOperationException();
    }

    public Set<Entry<String, Object>> entrySet() {
        return new zzb();
    }

    /* renamed from: zzb */
    public zzbz clone() {
        try {
            zzbz zzbz = (zzbz) super.clone();
            zzbs.zza((Object) this, (Object) zzbz);
            zzbz.zzfu = (Map) zzbs.clone(this.zzfu);
            return zzbz;
        } catch (CloneNotSupportedException e) {
            throw new IllegalStateException(e);
        }
    }

    public final zzbq zzcc() {
        return this.zzar;
    }
}
