package com.google.android.gms.internal.gtm;

import com.google.common.base.Ascii;
import java.io.IOException;

public final class zzun {
    private final byte[] buffer;
    private int zzawf;
    private int zzawg = 64;
    private int zzawh = 67108864;
    private int zzawl;
    private int zzawn;
    private int zzawo = Integer.MAX_VALUE;
    private final int zzbgu;
    private final int zzbgv;
    private int zzbgw;
    private int zzbgx;
    private zzqe zzbgy;

    public static zzun zzk(byte[] bArr) {
        return zzj(bArr, 0, bArr.length);
    }

    public static zzun zzj(byte[] bArr, int i, int i2) {
        return new zzun(bArr, 0, i2);
    }

    public final int zzni() throws IOException {
        if (this.zzbgx == this.zzbgw) {
            this.zzawn = 0;
            return 0;
        }
        this.zzawn = zzoa();
        int i = this.zzawn;
        if (i != 0) {
            return i;
        }
        throw new zzuv("Protocol message contained an invalid tag (zero).");
    }

    public final void zzan(int i) throws zzuv {
        if (this.zzawn != i) {
            throw new zzuv("Protocol message end-group tag did not match expected tag.");
        }
    }

    public final boolean zzao(int i) throws IOException {
        int zzni;
        int i2 = i & 7;
        if (i2 == 0) {
            zzoa();
            return true;
        } else if (i2 == 1) {
            zzof();
            zzof();
            zzof();
            zzof();
            zzof();
            zzof();
            zzof();
            zzof();
            return true;
        } else if (i2 == 2) {
            zzas(zzoa());
            return true;
        } else if (i2 == 3) {
            do {
                zzni = zzni();
                if (zzni == 0) {
                    break;
                }
            } while (zzao(zzni));
            zzan(((i >>> 3) << 3) | 4);
            return true;
        } else if (i2 == 4) {
            return false;
        } else {
            if (i2 == 5) {
                zzoc();
                return true;
            }
            throw new zzuv("Protocol message tag had invalid wire type.");
        }
    }

    public final boolean zzno() throws IOException {
        return zzoa() != 0;
    }

    public final String readString() throws IOException {
        int zzoa = zzoa();
        if (zzoa >= 0) {
            int i = this.zzbgw;
            int i2 = this.zzbgx;
            if (zzoa <= i - i2) {
                String str = new String(this.buffer, i2, zzoa, zzuu.UTF_8);
                this.zzbgx += zzoa;
                return str;
            }
            throw zzuv.zzsa();
        }
        throw zzuv.zzsb();
    }

    public final void zza(zzuw zzuw, int i) throws IOException {
        int i2 = this.zzawf;
        if (i2 < this.zzawg) {
            this.zzawf = i2 + 1;
            zzuw.zza(this);
            zzan((i << 3) | 4);
            this.zzawf--;
            return;
        }
        throw zzuv.zzsd();
    }

    public final void zza(zzuw zzuw) throws IOException {
        int zzoa = zzoa();
        if (this.zzawf < this.zzawg) {
            int zzaq = zzaq(zzoa);
            this.zzawf++;
            zzuw.zza(this);
            zzan(0);
            this.zzawf--;
            zzar(zzaq);
            return;
        }
        throw zzuv.zzsd();
    }

    public final int zzoa() throws IOException {
        byte b;
        int i;
        byte zzof = zzof();
        if (zzof >= 0) {
            return zzof;
        }
        byte b2 = zzof & Byte.MAX_VALUE;
        byte zzof2 = zzof();
        if (zzof2 >= 0) {
            i = zzof2 << 7;
        } else {
            b2 |= (zzof2 & Byte.MAX_VALUE) << 7;
            byte zzof3 = zzof();
            if (zzof3 >= 0) {
                i = zzof3 << Ascii.f1876SO;
            } else {
                b2 |= (zzof3 & Byte.MAX_VALUE) << Ascii.f1876SO;
                byte zzof4 = zzof();
                if (zzof4 >= 0) {
                    i = zzof4 << Ascii.NAK;
                } else {
                    byte b3 = b2 | ((zzof4 & Byte.MAX_VALUE) << Ascii.NAK);
                    byte zzof5 = zzof();
                    b = b3 | (zzof5 << Ascii.f1869FS);
                    if (zzof5 < 0) {
                        for (int i2 = 0; i2 < 5; i2++) {
                            if (zzof() >= 0) {
                                return b;
                            }
                        }
                        throw zzuv.zzsc();
                    }
                    return b;
                }
            }
        }
        b = b2 | i;
        return b;
    }

    public final long zzob() throws IOException {
        long j = 0;
        for (int i = 0; i < 64; i += 7) {
            byte zzof = zzof();
            j |= ((long) (zzof & Byte.MAX_VALUE)) << i;
            if ((zzof & 128) == 0) {
                return j;
            }
        }
        throw zzuv.zzsc();
    }

    public final int zzoc() throws IOException {
        return (zzof() & 255) | ((zzof() & 255) << 8) | ((zzof() & 255) << Ascii.DLE) | ((zzof() & 255) << Ascii.CAN);
    }

    private zzun(byte[] bArr, int i, int i2) {
        this.buffer = bArr;
        this.zzbgu = i;
        int i3 = i2 + i;
        this.zzbgw = i3;
        this.zzbgv = i3;
        this.zzbgx = i;
    }

    private final zzqe zzru() throws IOException {
        if (this.zzbgy == null) {
            this.zzbgy = zzqe.zzd(this.buffer, this.zzbgu, this.zzbgv);
        }
        int zznz = this.zzbgy.zznz();
        int i = this.zzbgx - this.zzbgu;
        if (zznz <= i) {
            this.zzbgy.zzas(i - zznz);
            this.zzbgy.zzap(this.zzawg - this.zzawf);
            return this.zzbgy;
        }
        throw new IOException(String.format("CodedInputStream read ahead of CodedInputByteBufferNano: %s > %s", new Object[]{Integer.valueOf(zznz), Integer.valueOf(i)}));
    }

    public final <T extends zzrc<T, ?>> T zza(zzsu<T> zzsu) throws IOException {
        try {
            T t = (zzrc) zzru().zza(zzsu, zzqp.zzor());
            zzao(this.zzawn);
            return t;
        } catch (zzrk e) {
            throw new zzuv("", e);
        }
    }

    public final int zzaq(int i) throws zzuv {
        if (i >= 0) {
            int i2 = i + this.zzbgx;
            int i3 = this.zzawo;
            if (i2 <= i3) {
                this.zzawo = i2;
                zzoe();
                return i3;
            }
            throw zzuv.zzsa();
        }
        throw zzuv.zzsb();
    }

    private final void zzoe() {
        this.zzbgw += this.zzawl;
        int i = this.zzbgw;
        int i2 = this.zzawo;
        if (i > i2) {
            this.zzawl = i - i2;
            this.zzbgw = i - this.zzawl;
            return;
        }
        this.zzawl = 0;
    }

    public final void zzar(int i) {
        this.zzawo = i;
        zzoe();
    }

    public final int zzrv() {
        int i = this.zzawo;
        if (i == Integer.MAX_VALUE) {
            return -1;
        }
        return i - this.zzbgx;
    }

    public final int getPosition() {
        return this.zzbgx - this.zzbgu;
    }

    public final byte[] zzt(int i, int i2) {
        if (i2 == 0) {
            return zzuz.zzbhw;
        }
        byte[] bArr = new byte[i2];
        System.arraycopy(this.buffer, this.zzbgu + i, bArr, 0, i2);
        return bArr;
    }

    public final void zzbz(int i) {
        zzu(i, this.zzawn);
    }

    /* access modifiers changed from: 0000 */
    public final void zzu(int i, int i2) {
        int i3 = this.zzbgx;
        int i4 = this.zzbgu;
        if (i > i3 - i4) {
            int i5 = i3 - i4;
            StringBuilder sb = new StringBuilder(50);
            sb.append("Position ");
            sb.append(i);
            sb.append(" is beyond current ");
            sb.append(i5);
            throw new IllegalArgumentException(sb.toString());
        } else if (i >= 0) {
            this.zzbgx = i4 + i;
            this.zzawn = i2;
        } else {
            StringBuilder sb2 = new StringBuilder(24);
            sb2.append("Bad position ");
            sb2.append(i);
            throw new IllegalArgumentException(sb2.toString());
        }
    }

    private final byte zzof() throws IOException {
        int i = this.zzbgx;
        if (i != this.zzbgw) {
            byte[] bArr = this.buffer;
            this.zzbgx = i + 1;
            return bArr[i];
        }
        throw zzuv.zzsa();
    }

    private final void zzas(int i) throws IOException {
        if (i >= 0) {
            int i2 = this.zzbgx;
            int i3 = i2 + i;
            int i4 = this.zzawo;
            if (i3 > i4) {
                zzas(i4 - i2);
                throw zzuv.zzsa();
            } else if (i <= this.zzbgw - i2) {
                this.zzbgx = i2 + i;
            } else {
                throw zzuv.zzsa();
            }
        } else {
            throw zzuv.zzsb();
        }
    }
}
