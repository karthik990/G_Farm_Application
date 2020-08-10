package com.google.android.gms.internal.firebase_remote_config;

import java.io.IOException;
import java.nio.charset.Charset;

class zzgg extends zzgf {
    protected final byte[] zzow;

    zzgg(byte[] bArr) {
        if (bArr != null) {
            this.zzow = bArr;
            return;
        }
        throw new NullPointerException();
    }

    /* access modifiers changed from: protected */
    public int zzfa() {
        return 0;
    }

    public byte zzv(int i) {
        return this.zzow[i];
    }

    /* access modifiers changed from: 0000 */
    public byte zzw(int i) {
        return this.zzow[i];
    }

    public int size() {
        return this.zzow.length;
    }

    public final zzfw zzb(int i, int i2) {
        int zzc = zzc(i, i2, size());
        if (zzc == 0) {
            return zzfw.zzop;
        }
        return new zzgb(this.zzow, zzfa() + i, zzc);
    }

    /* access modifiers changed from: protected */
    public void zzb(byte[] bArr, int i, int i2, int i3) {
        System.arraycopy(this.zzow, i, bArr, i2, i3);
    }

    /* access modifiers changed from: 0000 */
    public final void zza(zzfv zzfv) throws IOException {
        zzfv.zza(this.zzow, zzfa(), size());
    }

    /* access modifiers changed from: protected */
    public final String zzc(Charset charset) {
        return new String(this.zzow, zzfa(), size(), charset);
    }

    public final boolean zzew() {
        int zzfa = zzfa();
        return zzkj.zze(this.zzow, zzfa, size() + zzfa);
    }

    /* access modifiers changed from: protected */
    public final int zza(int i, int i2, int i3) {
        int zzfa = zzfa() + i2;
        return zzkj.zzb(i, this.zzow, zzfa, i3 + zzfa);
    }

    public final boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof zzfw) || size() != ((zzfw) obj).size()) {
            return false;
        }
        if (size() == 0) {
            return true;
        }
        if (!(obj instanceof zzgg)) {
            return obj.equals(this);
        }
        zzgg zzgg = (zzgg) obj;
        int zzez = zzez();
        int zzez2 = zzgg.zzez();
        if (zzez == 0 || zzez2 == 0 || zzez == zzez2) {
            return zza(zzgg, 0, size());
        }
        return false;
    }

    /* access modifiers changed from: 0000 */
    public final boolean zza(zzfw zzfw, int i, int i2) {
        if (i2 <= zzfw.size()) {
            int i3 = i + i2;
            if (i3 > zzfw.size()) {
                int size = zzfw.size();
                StringBuilder sb = new StringBuilder(59);
                sb.append("Ran off end of other: ");
                sb.append(i);
                String str = ", ";
                sb.append(str);
                sb.append(i2);
                sb.append(str);
                sb.append(size);
                throw new IllegalArgumentException(sb.toString());
            } else if (!(zzfw instanceof zzgg)) {
                return zzfw.zzb(i, i3).equals(zzb(0, i2));
            } else {
                zzgg zzgg = (zzgg) zzfw;
                byte[] bArr = this.zzow;
                byte[] bArr2 = zzgg.zzow;
                int zzfa = zzfa() + i2;
                int zzfa2 = zzfa();
                int zzfa3 = zzgg.zzfa() + i;
                while (zzfa2 < zzfa) {
                    if (bArr[zzfa2] != bArr2[zzfa3]) {
                        return false;
                    }
                    zzfa2++;
                    zzfa3++;
                }
                return true;
            }
        } else {
            int size2 = size();
            StringBuilder sb2 = new StringBuilder(40);
            sb2.append("Length too large: ");
            sb2.append(i2);
            sb2.append(size2);
            throw new IllegalArgumentException(sb2.toString());
        }
    }

    /* access modifiers changed from: protected */
    public final int zzb(int i, int i2, int i3) {
        return zzhk.zza(i, this.zzow, zzfa() + i2, i3);
    }
}
