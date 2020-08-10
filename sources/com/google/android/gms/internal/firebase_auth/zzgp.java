package com.google.android.gms.internal.firebase_auth;

import java.io.IOException;
import java.nio.charset.Charset;

class zzgp extends zzgq {
    protected final byte[] zzwd;

    zzgp(byte[] bArr) {
        if (bArr != null) {
            this.zzwd = bArr;
            return;
        }
        throw new NullPointerException();
    }

    /* access modifiers changed from: protected */
    public int zzgf() {
        return 0;
    }

    public byte zzp(int i) {
        return this.zzwd[i];
    }

    /* access modifiers changed from: 0000 */
    public byte zzq(int i) {
        return this.zzwd[i];
    }

    public int size() {
        return this.zzwd.length;
    }

    public final zzgf zzd(int i, int i2) {
        int zzc = zzc(0, i2, size());
        if (zzc == 0) {
            return zzgf.zzvv;
        }
        return new zzgm(this.zzwd, zzgf(), zzc);
    }

    /* access modifiers changed from: 0000 */
    public final void zza(zzgg zzgg) throws IOException {
        zzgg.zzb(this.zzwd, zzgf(), size());
    }

    /* access modifiers changed from: protected */
    public final String zza(Charset charset) {
        return new String(this.zzwd, zzgf(), size(), charset);
    }

    public final boolean zzgd() {
        int zzgf = zzgf();
        return zzkt.zze(this.zzwd, zzgf, size() + zzgf);
    }

    public final boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof zzgf) || size() != ((zzgf) obj).size()) {
            return false;
        }
        if (size() == 0) {
            return true;
        }
        if (!(obj instanceof zzgp)) {
            return obj.equals(this);
        }
        zzgp zzgp = (zzgp) obj;
        int zzge = zzge();
        int zzge2 = zzgp.zzge();
        if (zzge == 0 || zzge2 == 0 || zzge == zzge2) {
            return zza(zzgp, 0, size());
        }
        return false;
    }

    /* access modifiers changed from: 0000 */
    public final boolean zza(zzgf zzgf, int i, int i2) {
        if (i2 > zzgf.size()) {
            int size = size();
            StringBuilder sb = new StringBuilder(40);
            sb.append("Length too large: ");
            sb.append(i2);
            sb.append(size);
            throw new IllegalArgumentException(sb.toString());
        } else if (i2 > zzgf.size()) {
            int size2 = zzgf.size();
            StringBuilder sb2 = new StringBuilder(59);
            sb2.append("Ran off end of other: 0, ");
            sb2.append(i2);
            sb2.append(", ");
            sb2.append(size2);
            throw new IllegalArgumentException(sb2.toString());
        } else if (!(zzgf instanceof zzgp)) {
            return zzgf.zzd(0, i2).equals(zzd(0, i2));
        } else {
            zzgp zzgp = (zzgp) zzgf;
            byte[] bArr = this.zzwd;
            byte[] bArr2 = zzgp.zzwd;
            int zzgf2 = zzgf() + i2;
            int zzgf3 = zzgf();
            int zzgf4 = zzgp.zzgf();
            while (zzgf3 < zzgf2) {
                if (bArr[zzgf3] != bArr2[zzgf4]) {
                    return false;
                }
                zzgf3++;
                zzgf4++;
            }
            return true;
        }
    }

    /* access modifiers changed from: protected */
    public final int zzb(int i, int i2, int i3) {
        return zzht.zza(i, this.zzwd, zzgf(), i3);
    }
}
