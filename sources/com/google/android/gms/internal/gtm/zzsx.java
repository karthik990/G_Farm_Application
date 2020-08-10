package com.google.android.gms.internal.gtm;

import com.google.android.gms.internal.gtm.zzrc.zze;

final class zzsx implements zzsi {
    private final int flags;
    private final String info;
    private final Object[] zzbcz;
    private final zzsk zzbdc;

    zzsx(zzsk zzsk, String str, Object[] objArr) {
        this.zzbdc = zzsk;
        this.info = str;
        this.zzbcz = objArr;
        char charAt = str.charAt(0);
        if (charAt < 55296) {
            this.flags = charAt;
            return;
        }
        char c = charAt & 8191;
        int i = 13;
        int i2 = 1;
        while (true) {
            int i3 = i2 + 1;
            char charAt2 = str.charAt(i2);
            if (charAt2 >= 55296) {
                c |= (charAt2 & 8191) << i;
                i += 13;
                i2 = i3;
            } else {
                this.flags = c | (charAt2 << i);
                return;
            }
        }
    }

    /* access modifiers changed from: 0000 */
    public final String zzqt() {
        return this.info;
    }

    /* access modifiers changed from: 0000 */
    public final Object[] zzqu() {
        return this.zzbcz;
    }

    public final zzsk zzqn() {
        return this.zzbdc;
    }

    public final int zzql() {
        return (this.flags & 1) == 1 ? zze.zzbaz : zze.zzbba;
    }

    public final boolean zzqm() {
        return (this.flags & 2) == 2;
    }
}
