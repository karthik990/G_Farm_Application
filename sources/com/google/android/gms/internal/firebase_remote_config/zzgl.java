package com.google.android.gms.internal.firebase_remote_config;

import com.braintreepayments.api.internal.GraphQLConstants.Keys;
import com.google.common.base.Ascii;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

final class zzgl extends zzgi {
    private final byte[] buffer;
    private int pos;
    private int zzpd;
    private int zzpf;
    private int zzpg;
    private final InputStream zzph;
    private int zzpi;
    private int zzpj;
    private zzgm zzpk;

    private zzgl(InputStream inputStream, int i) {
        super();
        this.zzpg = Integer.MAX_VALUE;
        this.zzpk = null;
        zzhk.zza(inputStream, Keys.INPUT);
        this.zzph = inputStream;
        this.buffer = new byte[i];
        this.zzpi = 0;
        this.pos = 0;
        this.zzpj = 0;
    }

    public final int zzfd() throws IOException {
        if (zzft()) {
            this.zzpf = 0;
            return 0;
        }
        this.zzpf = zzfv();
        int i = this.zzpf;
        if ((i >>> 3) != 0) {
            return i;
        }
        throw zzho.zzhj();
    }

    public final void zzy(int i) throws zzho {
        if (this.zzpf != i) {
            throw zzho.zzhk();
        }
    }

    public final boolean zzz(int i) throws IOException {
        int zzfd;
        int i2 = i & 7;
        int i3 = 0;
        if (i2 == 0) {
            if (this.zzpi - this.pos >= 10) {
                while (i3 < 10) {
                    byte[] bArr = this.buffer;
                    int i4 = this.pos;
                    this.pos = i4 + 1;
                    if (bArr[i4] < 0) {
                        i3++;
                    }
                }
                throw zzho.zzhi();
            }
            while (i3 < 10) {
                if (zzga() < 0) {
                    i3++;
                }
            }
            throw zzho.zzhi();
            return true;
        } else if (i2 == 1) {
            zzad(8);
            return true;
        } else if (i2 == 2) {
            zzad(zzfv());
            return true;
        } else if (i2 == 3) {
            do {
                zzfd = zzfd();
                if (zzfd == 0) {
                    break;
                }
            } while (zzz(zzfd));
            zzy(((i >>> 3) << 3) | 4);
            return true;
        } else if (i2 == 4) {
            return false;
        } else {
            if (i2 == 5) {
                zzad(4);
                return true;
            }
            throw zzho.zzhl();
        }
    }

    public final double readDouble() throws IOException {
        return Double.longBitsToDouble(zzfy());
    }

    public final float readFloat() throws IOException {
        return Float.intBitsToFloat(zzfx());
    }

    public final long zzfe() throws IOException {
        return zzfw();
    }

    public final long zzff() throws IOException {
        return zzfw();
    }

    public final int zzfg() throws IOException {
        return zzfv();
    }

    public final long zzfh() throws IOException {
        return zzfy();
    }

    public final int zzfi() throws IOException {
        return zzfx();
    }

    public final boolean zzfj() throws IOException {
        return zzfw() != 0;
    }

    public final String readString() throws IOException {
        int zzfv = zzfv();
        if (zzfv > 0) {
            int i = this.zzpi;
            int i2 = this.pos;
            if (zzfv <= i - i2) {
                String str = new String(this.buffer, i2, zzfv, zzhk.UTF_8);
                this.pos += zzfv;
                return str;
            }
        }
        if (zzfv == 0) {
            return "";
        }
        if (zzfv > this.zzpi) {
            return new String(zzag(zzfv), zzhk.UTF_8);
        }
        zzae(zzfv);
        String str2 = new String(this.buffer, this.pos, zzfv, zzhk.UTF_8);
        this.pos += zzfv;
        return str2;
    }

    public final String zzfk() throws IOException {
        byte[] bArr;
        int zzfv = zzfv();
        int i = this.pos;
        int i2 = 0;
        if (zzfv <= this.zzpi - i && zzfv > 0) {
            bArr = this.buffer;
            this.pos = i + zzfv;
            i2 = i;
        } else if (zzfv == 0) {
            return "";
        } else {
            if (zzfv <= this.zzpi) {
                zzae(zzfv);
                bArr = this.buffer;
                this.pos = zzfv;
            } else {
                bArr = zzag(zzfv);
            }
        }
        return zzkj.zzg(bArr, i2, zzfv);
    }

    public final zzfw zzfl() throws IOException {
        int zzfv = zzfv();
        int i = this.zzpi;
        int i2 = this.pos;
        if (zzfv <= i - i2 && zzfv > 0) {
            zzfw zzb = zzfw.zzb(this.buffer, i2, zzfv);
            this.pos += zzfv;
            return zzb;
        } else if (zzfv == 0) {
            return zzfw.zzop;
        } else {
            byte[] zzah = zzah(zzfv);
            if (zzah != null) {
                return zzfw.zza(zzah);
            }
            int i3 = this.pos;
            int i4 = this.zzpi;
            int i5 = i4 - i3;
            this.zzpj += i4;
            this.pos = 0;
            this.zzpi = 0;
            List<byte[]> zzai = zzai(zzfv - i5);
            ArrayList arrayList = new ArrayList(zzai.size() + 1);
            arrayList.add(zzfw.zzb(this.buffer, i3, i5));
            for (byte[] zza : zzai) {
                arrayList.add(zzfw.zza(zza));
            }
            return zzfw.zza((Iterable<zzfw>) arrayList);
        }
    }

    public final int zzfm() throws IOException {
        return zzfv();
    }

    public final int zzfn() throws IOException {
        return zzfv();
    }

    public final int zzfo() throws IOException {
        return zzfx();
    }

    public final long zzfp() throws IOException {
        return zzfy();
    }

    public final int zzfq() throws IOException {
        return zzac(zzfv());
    }

    public final long zzfr() throws IOException {
        return zze(zzfw());
    }

    /* JADX WARNING: Code restructure failed: missing block: B:29:0x0066, code lost:
        if (r2[r3] >= 0) goto L_0x0068;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private final int zzfv() throws java.io.IOException {
        /*
            r5 = this;
            int r0 = r5.pos
            int r1 = r5.zzpi
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
            long r0 = r5.zzfs()
            int r1 = (int) r0
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.firebase_remote_config.zzgl.zzfv():int");
    }

    /* JADX WARNING: Code restructure failed: missing block: B:36:0x00b0, code lost:
        if (((long) r2[r0]) >= 0) goto L_0x00b2;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private final long zzfw() throws java.io.IOException {
        /*
            r11 = this;
            int r0 = r11.pos
            int r1 = r11.zzpi
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
            long r0 = r11.zzfs()
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.firebase_remote_config.zzgl.zzfw():long");
    }

    /* access modifiers changed from: 0000 */
    public final long zzfs() throws IOException {
        long j = 0;
        for (int i = 0; i < 64; i += 7) {
            byte zzga = zzga();
            j |= ((long) (zzga & Byte.MAX_VALUE)) << i;
            if ((zzga & 128) == 0) {
                return j;
            }
        }
        throw zzho.zzhi();
    }

    private final int zzfx() throws IOException {
        int i = this.pos;
        if (this.zzpi - i < 4) {
            zzae(4);
            i = this.pos;
        }
        byte[] bArr = this.buffer;
        this.pos = i + 4;
        return ((bArr[i + 3] & 255) << Ascii.CAN) | (bArr[i] & 255) | ((bArr[i + 1] & 255) << 8) | ((bArr[i + 2] & 255) << Ascii.DLE);
    }

    private final long zzfy() throws IOException {
        int i = this.pos;
        if (this.zzpi - i < 8) {
            zzae(8);
            i = this.pos;
        }
        byte[] bArr = this.buffer;
        this.pos = i + 8;
        return ((((long) bArr[i + 7]) & 255) << 56) | (((long) bArr[i]) & 255) | ((((long) bArr[i + 1]) & 255) << 8) | ((((long) bArr[i + 2]) & 255) << 16) | ((((long) bArr[i + 3]) & 255) << 24) | ((((long) bArr[i + 4]) & 255) << 32) | ((((long) bArr[i + 5]) & 255) << 40) | ((((long) bArr[i + 6]) & 255) << 48);
    }

    public final int zzaa(int i) throws zzho {
        if (i >= 0) {
            int i2 = i + this.zzpj + this.pos;
            int i3 = this.zzpg;
            if (i2 <= i3) {
                this.zzpg = i2;
                zzfz();
                return i3;
            }
            throw zzho.zzhg();
        }
        throw zzho.zzhh();
    }

    private final void zzfz() {
        this.zzpi += this.zzpd;
        int i = this.zzpj;
        int i2 = this.zzpi;
        int i3 = i + i2;
        int i4 = this.zzpg;
        if (i3 > i4) {
            this.zzpd = i3 - i4;
            this.zzpi = i2 - this.zzpd;
            return;
        }
        this.zzpd = 0;
    }

    public final void zzab(int i) {
        this.zzpg = i;
        zzfz();
    }

    public final boolean zzft() throws IOException {
        return this.pos == this.zzpi && !zzaf(1);
    }

    public final int zzfu() {
        return this.zzpj + this.pos;
    }

    private final void zzae(int i) throws IOException {
        if (zzaf(i)) {
            return;
        }
        if (i > (this.zzoz - this.zzpj) - this.pos) {
            throw zzho.zzhm();
        }
        throw zzho.zzhg();
    }

    private final boolean zzaf(int i) throws IOException {
        while (this.pos + i > this.zzpi) {
            int i2 = this.zzoz;
            int i3 = this.zzpj;
            int i4 = i2 - i3;
            int i5 = this.pos;
            if (i > i4 - i5 || i3 + i5 + i > this.zzpg) {
                return false;
            }
            if (i5 > 0) {
                int i6 = this.zzpi;
                if (i6 > i5) {
                    byte[] bArr = this.buffer;
                    System.arraycopy(bArr, i5, bArr, 0, i6 - i5);
                }
                this.zzpj += i5;
                this.zzpi -= i5;
                this.pos = 0;
            }
            InputStream inputStream = this.zzph;
            byte[] bArr2 = this.buffer;
            int i7 = this.zzpi;
            int read = inputStream.read(bArr2, i7, Math.min(bArr2.length - i7, (this.zzoz - this.zzpj) - this.zzpi));
            if (read == 0 || read < -1 || read > this.buffer.length) {
                String valueOf = String.valueOf(this.zzph.getClass());
                StringBuilder sb = new StringBuilder(String.valueOf(valueOf).length() + 91);
                sb.append(valueOf);
                sb.append("#read(byte[]) returned invalid result: ");
                sb.append(read);
                sb.append("\nThe InputStream implementation is buggy.");
                throw new IllegalStateException(sb.toString());
            } else if (read <= 0) {
                return false;
            } else {
                this.zzpi += read;
                zzfz();
                if (this.zzpi >= i) {
                    return true;
                }
            }
        }
        StringBuilder sb2 = new StringBuilder(77);
        sb2.append("refillBuffer() called when ");
        sb2.append(i);
        sb2.append(" bytes were already available in buffer");
        throw new IllegalStateException(sb2.toString());
    }

    private final byte zzga() throws IOException {
        if (this.pos == this.zzpi) {
            zzae(1);
        }
        byte[] bArr = this.buffer;
        int i = this.pos;
        this.pos = i + 1;
        return bArr[i];
    }

    private final byte[] zzag(int i) throws IOException {
        byte[] zzah = zzah(i);
        if (zzah != null) {
            return zzah;
        }
        int i2 = this.pos;
        int i3 = this.zzpi;
        int i4 = i3 - i2;
        this.zzpj += i3;
        this.pos = 0;
        this.zzpi = 0;
        List<byte[]> zzai = zzai(i - i4);
        byte[] bArr = new byte[i];
        System.arraycopy(this.buffer, i2, bArr, 0, i4);
        for (byte[] bArr2 : zzai) {
            System.arraycopy(bArr2, 0, bArr, i4, bArr2.length);
            i4 += bArr2.length;
        }
        return bArr;
    }

    private final byte[] zzah(int i) throws IOException {
        if (i == 0) {
            return zzhk.zztt;
        }
        if (i >= 0) {
            int i2 = this.zzpj + this.pos + i;
            if (i2 - this.zzoz <= 0) {
                int i3 = this.zzpg;
                if (i2 <= i3) {
                    int i4 = this.zzpi - this.pos;
                    int i5 = i - i4;
                    if (i5 >= 4096 && i5 > this.zzph.available()) {
                        return null;
                    }
                    byte[] bArr = new byte[i];
                    System.arraycopy(this.buffer, this.pos, bArr, 0, i4);
                    this.zzpj += this.zzpi;
                    this.pos = 0;
                    this.zzpi = 0;
                    while (i4 < bArr.length) {
                        int read = this.zzph.read(bArr, i4, i - i4);
                        if (read != -1) {
                            this.zzpj += read;
                            i4 += read;
                        } else {
                            throw zzho.zzhg();
                        }
                    }
                    return bArr;
                }
                zzad((i3 - this.zzpj) - this.pos);
                throw zzho.zzhg();
            }
            throw zzho.zzhm();
        }
        throw zzho.zzhh();
    }

    private final List<byte[]> zzai(int i) throws IOException {
        ArrayList arrayList = new ArrayList();
        while (i > 0) {
            byte[] bArr = new byte[Math.min(i, 4096)];
            int i2 = 0;
            while (i2 < bArr.length) {
                int read = this.zzph.read(bArr, i2, bArr.length - i2);
                if (read != -1) {
                    this.zzpj += read;
                    i2 += read;
                } else {
                    throw zzho.zzhg();
                }
            }
            i -= bArr.length;
            arrayList.add(bArr);
        }
        return arrayList;
    }

    private final void zzad(int i) throws IOException {
        int i2 = this.zzpi;
        int i3 = this.pos;
        if (i <= i2 - i3 && i >= 0) {
            this.pos = i3 + i;
        } else if (i >= 0) {
            int i4 = this.zzpj;
            int i5 = this.pos;
            int i6 = i4 + i5 + i;
            int i7 = this.zzpg;
            if (i6 <= i7) {
                this.zzpj = i4 + i5;
                int i8 = this.zzpi - i5;
                this.zzpi = 0;
                this.pos = 0;
                while (i8 < i) {
                    try {
                        long j = (long) (i - i8);
                        long skip = this.zzph.skip(j);
                        if (skip < 0 || skip > j) {
                            String valueOf = String.valueOf(this.zzph.getClass());
                            StringBuilder sb = new StringBuilder(String.valueOf(valueOf).length() + 92);
                            sb.append(valueOf);
                            sb.append("#skip returned invalid result: ");
                            sb.append(skip);
                            sb.append("\nThe InputStream implementation is buggy.");
                            throw new IllegalStateException(sb.toString());
                        }
                        i8 += (int) skip;
                    } catch (Throwable th) {
                        this.zzpj += i8;
                        zzfz();
                        throw th;
                    }
                }
                this.zzpj += i8;
                zzfz();
                return;
            }
            zzad((i7 - i4) - i5);
            throw zzho.zzhg();
        } else {
            throw zzho.zzhh();
        }
    }
}
