package com.google.android.gms.internal.gtm;

import com.google.android.gms.internal.gtm.zzrc.zzd;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class zzqp {
    private static volatile boolean zzaxh = false;
    private static final Class<?> zzaxi = zzop();
    private static volatile zzqp zzaxj;
    static final zzqp zzaxk = new zzqp(true);
    private final Map<zza, zzd<?, ?>> zzaxl;

    static final class zza {
        private final int number;
        private final Object object;

        zza(Object obj, int i) {
            this.object = obj;
            this.number = i;
        }

        public final int hashCode() {
            return (System.identityHashCode(this.object) * 65535) + this.number;
        }

        public final boolean equals(Object obj) {
            if (!(obj instanceof zza)) {
                return false;
            }
            zza zza = (zza) obj;
            if (this.object == zza.object && this.number == zza.number) {
                return true;
            }
            return false;
        }
    }

    private static Class<?> zzop() {
        try {
            return Class.forName("com.google.protobuf.Extension");
        } catch (ClassNotFoundException unused) {
            return null;
        }
    }

    public static zzqp zzoq() {
        return zzqo.zzon();
    }

    public static zzqp zzor() {
        zzqp zzqp = zzaxj;
        if (zzqp == null) {
            synchronized (zzqp.class) {
                zzqp = zzaxj;
                if (zzqp == null) {
                    zzqp = zzqo.zzoo();
                    zzaxj = zzqp;
                }
            }
        }
        return zzqp;
    }

    static zzqp zzoo() {
        return zzra.zzd(zzqp.class);
    }

    public final <ContainingType extends zzsk> zzd<ContainingType, ?> zza(ContainingType containingtype, int i) {
        return (zzd) this.zzaxl.get(new zza(containingtype, i));
    }

    zzqp() {
        this.zzaxl = new HashMap();
    }

    private zzqp(boolean z) {
        this.zzaxl = Collections.emptyMap();
    }
}
