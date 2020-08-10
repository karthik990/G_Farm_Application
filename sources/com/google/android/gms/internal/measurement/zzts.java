package com.google.android.gms.internal.measurement;

import com.google.common.base.Ascii;
import java.io.IOException;
import java.util.Arrays;

final class zzts extends zztq {
    private final byte[] buffer;
    private int limit;
    private int pos;
    private final boolean zzbuf;
    private int zzbug;
    private int zzbuh;
    private int zzbui;
    private int zzbuj;

    private zzts(byte[] bArr, int i, int i2, boolean z) {
        super();
        this.zzbuj = Integer.MAX_VALUE;
        this.buffer = bArr;
        this.limit = i2 + i;
        this.pos = i;
        this.zzbuh = this.pos;
        this.zzbuf = z;
    }

    public final int zzuj() throws IOException {
        if (zzuz()) {
            this.zzbui = 0;
            return 0;
        }
        this.zzbui = zzvb();
        int i = this.zzbui;
        if ((i >>> 3) != 0) {
            return i;
        }
        throw new zzuv("Protocol message contained an invalid tag (zero).");
    }

    public final void zzap(int i) throws zzuv {
        if (this.zzbui != i) {
            throw zzuv.zzwt();
        }
    }

    public final boolean zzaq(int i) throws IOException {
        int zzuj;
        int i2 = i & 7;
        int i3 = 0;
        if (i2 == 0) {
            if (this.limit - this.pos >= 10) {
                while (i3 < 10) {
                    byte[] bArr = this.buffer;
                    int i4 = this.pos;
                    this.pos = i4 + 1;
                    if (bArr[i4] < 0) {
                        i3++;
                    }
                }
                throw zzuv.zzws();
            }
            while (i3 < 10) {
                if (zzvg() < 0) {
                    i3++;
                }
            }
            throw zzuv.zzws();
            return true;
        } else if (i2 == 1) {
            zzau(8);
            return true;
        } else if (i2 == 2) {
            zzau(zzvb());
            return true;
        } else if (i2 == 3) {
            do {
                zzuj = zzuj();
                if (zzuj == 0) {
                    break;
                }
            } while (zzaq(zzuj));
            zzap(((i >>> 3) << 3) | 4);
            return true;
        } else if (i2 == 4) {
            return false;
        } else {
            if (i2 == 5) {
                zzau(4);
                return true;
            }
            throw zzuv.zzwu();
        }
    }

    public final double readDouble() throws IOException {
        return Double.longBitsToDouble(zzve());
    }

    public final float readFloat() throws IOException {
        return Float.intBitsToFloat(zzvd());
    }

    public final long zzuk() throws IOException {
        return zzvc();
    }

    public final long zzul() throws IOException {
        return zzvc();
    }

    public final int zzum() throws IOException {
        return zzvb();
    }

    public final long zzun() throws IOException {
        return zzve();
    }

    public final int zzuo() throws IOException {
        return zzvd();
    }

    public final boolean zzup() throws IOException {
        return zzvc() != 0;
    }

    public final String readString() throws IOException {
        int zzvb = zzvb();
        if (zzvb > 0) {
            int i = this.limit;
            int i2 = this.pos;
            if (zzvb <= i - i2) {
                String str = new String(this.buffer, i2, zzvb, zzuq.UTF_8);
                this.pos += zzvb;
                return str;
            }
        }
        if (zzvb == 0) {
            return "";
        }
        if (zzvb < 0) {
            throw zzuv.zzwr();
        }
        throw zzuv.zzwq();
    }

    public final String zzuq() throws IOException {
        int zzvb = zzvb();
        if (zzvb > 0) {
            int i = this.limit;
            int i2 = this.pos;
            if (zzvb <= i - i2) {
                String zzh = zzxl.zzh(this.buffer, i2, zzvb);
                this.pos += zzvb;
                return zzh;
            }
        }
        if (zzvb == 0) {
            return "";
        }
        if (zzvb <= 0) {
            throw zzuv.zzwr();
        }
        throw zzuv.zzwq();
    }

    public final <T extends zzvv> T zza(zzwf<T> zzwf, zzub zzub) throws IOException {
        int zzvb = zzvb();
        if (this.zzbua < this.zzbub) {
            int zzas = zzas(zzvb);
            this.zzbua++;
            T t = (zzvv) zzwf.zza(this, zzub);
            zzap(0);
            this.zzbua--;
            zzat(zzas);
            return t;
        }
        throw zzuv.zzwv();
    }

    public final zzte zzur() throws IOException {
        byte[] bArr;
        int zzvb = zzvb();
        if (zzvb > 0) {
            int i = this.limit;
            int i2 = this.pos;
            if (zzvb <= i - i2) {
                zzte zzb = zzte.zzb(this.buffer, i2, zzvb);
                this.pos += zzvb;
                return zzb;
            }
        }
        if (zzvb == 0) {
            return zzte.zzbts;
        }
        if (zzvb > 0) {
            int i3 = this.limit;
            int i4 = this.pos;
            if (zzvb <= i3 - i4) {
                this.pos = zzvb + i4;
                bArr = Arrays.copyOfRange(this.buffer, i4, this.pos);
                return zzte.zzi(bArr);
            }
        }
        if (zzvb > 0) {
            throw zzuv.zzwq();
        } else if (zzvb == 0) {
            bArr = zzuq.zzbzc;
            return zzte.zzi(bArr);
        } else {
            throw zzuv.zzwr();
        }
    }

    public final int zzus() throws IOException {
        return zzvb();
    }

    public final int zzut() throws IOException {
        return zzvb();
    }

    public final int zzuu() throws IOException {
        return zzvd();
    }

    public final long zzuv() throws IOException {
        return zzve();
    }

    public final int zzuw() throws IOException {
        int zzvb = zzvb();
        return (-(zzvb & 1)) ^ (zzvb >>> 1);
    }

    public final long zzux() throws IOException {
        long zzvc = zzvc();
        return (-(zzvc & 1)) ^ (zzvc >>> 1);
    }

    /* JADX WARNING: Code restructure failed: missing block: B:29:0x0066, code lost:
        if (r2[r3] >= 0) goto L_0x0068;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private final int zzvb() throws java.io.IOException {
        /*
            r5 = this;
            int r0 = r5.pos
            int r1 = r5.limit
            if (r1 == r0) goto L_0x006b
            byte[] r2 = r5.buffer
            int r3 = r0 + 1
            byte r0 = r2[r0]
            if (r0 < 0) goto L_0x0011
            r5.pos = r3
            return r0
        L_0x0011:
            int r1 = r1 - r3
            r4 = 9
            if (r1 < r4) goto L_0x006b
            int r1 = r3 + 1
            byte r3 = r2[r3]
            int r3 = r3 << 7
            r0 = r0 ^ r3
            if (r0 >= 0) goto L_0x0022
            r0 = r0 ^ -128(0xffffffffffffff80, float:NaN)
            goto L_0x0068
        L_0x0022:
            int r3 = r1 + 1
            byte r1 = r2[r1]
            int r1 = r1 << 14
            r0 = r0 ^ r1
            if (r0 < 0) goto L_0x002f
            r0 = r0 ^ 16256(0x3f80, float:2.278E-41)
        L_0x002d:
            r1 = r3
            goto L_0x0068
        L_0x002f:
            int r1 = r3 + 1
            byte r3 = r2[r3]
            int r3 = r3 << 21
            r0 = r0 ^ r3
            if (r0 >= 0) goto L_0x003d
            r2 = -2080896(0xffffffffffe03f80, float:NaN)
            r0 = r0 ^ r2
            goto L_0x0068
        L_0x003d:
            int r3 = r1 + 1
            byte r1 = r2[r1]
            int r4 = r1 << 28
            r0 = r0 ^ r4
            r4 = 266354560(0xfe03f80, float:2.2112565E-29)
            r0 = r0 ^ r4
            if (r1 >= 0) goto L_0x002d
            int r1 = r3 + 1
            byte r3 = r2[r3]
            if (r3 >= 0) goto L_0x0068
            int r3 = r1 + 1
            byte r1 = r2[r1]
            if (r1 >= 0) goto L_0x002d
            int r1 = r3 + 1
            byte r3 = r2[r3]
            if (r3 >= 0) goto L_0x0068
            int r3 = r1 + 1
            byte r1 = r2[r1]
            if (r1 >= 0) goto L_0x002d
            int r1 = r3 + 1
            byte r2 = r2[r3]
            if (r2 < 0) goto L_0x006b
        L_0x0068:
            r5.pos = r1
            return r0
        L_0x006b:
            long r0 = r5.zzuy()
            int r1 = (int) r0
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.measurement.zzts.zzvb():int");
    }

    /* JADX WARNING: Code restructure failed: missing block: B:36:0x00b0, code lost:
        if (((long) r2[r0]) >= 0) goto L_0x00b2;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private final long zzvc() throws java.io.IOException {
        /*
            r11 = this;
            int r0 = r11.pos
            int r1 = r11.limit
            if (r1 == r0) goto L_0x00b5
            byte[] r2 = r11.buffer
            int r3 = r0 + 1
            byte r0 = r2[r0]
            if (r0 < 0) goto L_0x0012
            r11.pos = r3
            long r0 = (long) r0
            return r0
        L_0x0012:
            int r1 = r1 - r3
            r4 = 9
            if (r1 < r4) goto L_0x00b5
            int r1 = r3 + 1
            byte r3 = r2[r3]
            int r3 = r3 << 7
            r0 = r0 ^ r3
            if (r0 >= 0) goto L_0x0026
            r0 = r0 ^ -128(0xffffffffffffff80, float:NaN)
        L_0x0022:
            long r2 = (long) r0
            r3 = r2
            goto L_0x00b2
        L_0x0026:
            int r3 = r1 + 1
            byte r1 = r2[r1]
            int r1 = r1 << 14
            r0 = r0 ^ r1
            if (r0 < 0) goto L_0x0037
            r0 = r0 ^ 16256(0x3f80, float:2.278E-41)
            long r0 = (long) r0
            r9 = r0
            r1 = r3
            r3 = r9
            goto L_0x00b2
        L_0x0037:
            int r1 = r3 + 1
            byte r3 = r2[r3]
            int r3 = r3 << 21
            r0 = r0 ^ r3
            if (r0 >= 0) goto L_0x0045
            r2 = -2080896(0xffffffffffe03f80, float:NaN)
            r0 = r0 ^ r2
            goto L_0x0022
        L_0x0045:
            long r3 = (long) r0
            int r0 = r1 + 1
            byte r1 = r2[r1]
            long r5 = (long) r1
            r1 = 28
            long r5 = r5 << r1
            long r3 = r3 ^ r5
            r5 = 0
            int r1 = (r3 > r5 ? 1 : (r3 == r5 ? 0 : -1))
            if (r1 < 0) goto L_0x005c
            r1 = 266354560(0xfe03f80, double:1.315966377E-315)
        L_0x0058:
            long r1 = r1 ^ r3
            r3 = r1
        L_0x005a:
            r1 = r0
            goto L_0x00b2
        L_0x005c:
            int r1 = r0 + 1
            byte r0 = r2[r0]
            long r7 = (long) r0
            r0 = 35
            long r7 = r7 << r0
            long r3 = r3 ^ r7
            int r0 = (r3 > r5 ? 1 : (r3 == r5 ? 0 : -1))
            if (r0 >= 0) goto L_0x0070
            r5 = -34093383808(0xfffffff80fe03f80, double:NaN)
        L_0x006e:
            long r3 = r3 ^ r5
            goto L_0x00b2
        L_0x0070:
            int r0 = r1 + 1
            byte r1 = r2[r1]
            long r7 = (long) r1
            r1 = 42
            long r7 = r7 << r1
            long r3 = r3 ^ r7
            int r1 = (r3 > r5 ? 1 : (r3 == r5 ? 0 : -1))
            if (r1 < 0) goto L_0x0083
            r1 = 4363953127296(0x3f80fe03f80, double:2.1560793202584E-311)
            goto L_0x0058
        L_0x0083:
            int r1 = r0 + 1
            byte r0 = r2[r0]
            long r7 = (long) r0
            r0 = 49
            long r7 = r7 << r0
            long r3 = r3 ^ r7
            int r0 = (r3 > r5 ? 1 : (r3 == r5 ? 0 : -1))
            if (r0 >= 0) goto L_0x0096
            r5 = -558586000294016(0xfffe03f80fe03f80, double:NaN)
            goto L_0x006e
        L_0x0096:
            int r0 = r1 + 1
            byte r1 = r2[r1]
            long r7 = (long) r1
            r1 = 56
            long r7 = r7 << r1
            long r3 = r3 ^ r7
            r7 = 71499008037633920(0xfe03f80fe03f80, double:6.838959413692434E-304)
            long r3 = r3 ^ r7
            int r1 = (r3 > r5 ? 1 : (r3 == r5 ? 0 : -1))
            if (r1 >= 0) goto L_0x005a
            int r1 = r0 + 1
            byte r0 = r2[r0]
            long r7 = (long) r0
            int r0 = (r7 > r5 ? 1 : (r7 == r5 ? 0 : -1))
            if (r0 < 0) goto L_0x00b5
        L_0x00b2:
            r11.pos = r1
            return r3
        L_0x00b5:
            long r0 = r11.zzuy()
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.measurement.zzts.zzvc():long");
    }

    /* access modifiers changed from: 0000 */
    public final long zzuy() throws IOException {
        long j = 0;
        for (int i = 0; i < 64; i += 7) {
            byte zzvg = zzvg();
            j |= ((long) (zzvg & Byte.MAX_VALUE)) << i;
            if ((zzvg & 128) == 0) {
                return j;
            }
        }
        throw zzuv.zzws();
    }

    private final int zzvd() throws IOException {
        int i = this.pos;
        if (this.limit - i >= 4) {
            byte[] bArr = this.buffer;
            this.pos = i + 4;
            return ((bArr[i + 3] & 255) << Ascii.CAN) | (bArr[i] & 255) | ((bArr[i + 1] & 255) << 8) | ((bArr[i + 2] & 255) << Ascii.DLE);
        }
        throw zzuv.zzwq();
    }

    private final long zzve() throws IOException {
        int i = this.pos;
        if (this.limit - i >= 8) {
            byte[] bArr = this.buffer;
            this.pos = i + 8;
            return ((((long) bArr[i + 7]) & 255) << 56) | (((long) bArr[i]) & 255) | ((((long) bArr[i + 1]) & 255) << 8) | ((((long) bArr[i + 2]) & 255) << 16) | ((((long) bArr[i + 3]) & 255) << 24) | ((((long) bArr[i + 4]) & 255) << 32) | ((((long) bArr[i + 5]) & 255) << 40) | ((((long) bArr[i + 6]) & 255) << 48);
        }
        throw zzuv.zzwq();
    }

    public final int zzas(int i) throws zzuv {
        if (i >= 0) {
            int zzva = i + zzva();
            int i2 = this.zzbuj;
            if (zzva <= i2) {
                this.zzbuj = zzva;
                zzvf();
                return i2;
            }
            throw zzuv.zzwq();
        }
        throw zzuv.zzwr();
    }

    private final void zzvf() {
        this.limit += this.zzbug;
        int i = this.limit;
        int i2 = i - this.zzbuh;
        int i3 = this.zzbuj;
        if (i2 > i3) {
            this.zzbug = i2 - i3;
            this.limit = i - this.zzbug;
            return;
        }
        this.zzbug = 0;
    }

    public final void zzat(int i) {
        this.zzbuj = i;
        zzvf();
    }

    public final boolean zzuz() throws IOException {
        return this.pos == this.limit;
    }

    public final int zzva() {
        return this.pos - this.zzbuh;
    }

    private final byte zzvg() throws IOException {
        int i = this.pos;
        if (i != this.limit) {
            byte[] bArr = this.buffer;
            this.pos = i + 1;
            return bArr[i];
        }
        throw zzuv.zzwq();
    }

    public final void zzau(int i) throws IOException {
        if (i >= 0) {
            int i2 = this.limit;
            int i3 = this.pos;
            if (i <= i2 - i3) {
                this.pos = i3 + i;
                return;
            }
        }
        if (i < 0) {
            throw zzuv.zzwr();
        }
        throw zzuv.zzwq();
    }
}
