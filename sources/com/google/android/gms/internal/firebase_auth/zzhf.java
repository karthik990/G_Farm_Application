package com.google.android.gms.internal.firebase_auth;

import com.google.android.gms.internal.firebase_auth.zzhs.zze;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class zzhf {
    private static volatile boolean zzxb = false;
    private static final Class<?> zzxc = zzhp();
    private static volatile zzhf zzxd;
    private static volatile zzhf zzxe;
    static final zzhf zzxf = new zzhf(true);
    private final Map<zza, zze<?, ?>> zzxg;

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

    private static Class<?> zzhp() {
        try {
            return Class.forName("com.google.protobuf.Extension");
        } catch (ClassNotFoundException unused) {
            return null;
        }
    }

    public static zzhf zzhq() {
        zzhf zzhf = zzxd;
        if (zzhf == null) {
            synchronized (zzhf.class) {
                zzhf = zzxd;
                if (zzhf == null) {
                    zzhf = zzhd.zzhn();
                    zzxd = zzhf;
                }
            }
        }
        return zzhf;
    }

    public static zzhf zzhr() {
        zzhf zzhf = zzxe;
        if (zzhf == null) {
            synchronized (zzhf.class) {
                zzhf = zzxe;
                if (zzhf == null) {
                    zzhf = zzhd.zzho();
                    zzxe = zzhf;
                }
            }
        }
        return zzhf;
    }

    static zzhf zzho() {
        return zzhr.zzc(zzhf.class);
    }

    public final <ContainingType extends zzjc> zze<ContainingType, ?> zza(ContainingType containingtype, int i) {
        return (zze) this.zzxg.get(new zza(containingtype, i));
    }

    zzhf() {
        this.zzxg = new HashMap();
    }

    private zzhf(boolean z) {
        this.zzxg = Collections.emptyMap();
    }
}
