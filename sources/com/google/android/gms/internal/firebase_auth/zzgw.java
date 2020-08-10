package com.google.android.gms.internal.firebase_auth;

import com.braintreepayments.api.internal.GraphQLConstants.Keys;
import com.google.common.base.Ascii;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

final class zzgw extends zzgr {
    private final byte[] buffer;
    private int pos;
    private int zzwk;
    private int zzwm;
    private int zzwn;
    private final InputStream zzwo;
    private int zzwp;
    private int zzwq;
    private zzgv zzwr;

    private zzgw(InputStream inputStream, int i) {
        super();
        this.zzwn = Integer.MAX_VALUE;
        this.zzwr = null;
        zzht.zza(inputStream, Keys.INPUT);
        this.zzwo = inputStream;
        this.buffer = new byte[i];
        this.zzwp = 0;
        this.pos = 0;
        this.zzwq = 0;
    }

    public final int zzgi() throws IOException {
        if (zzgy()) {
            this.zzwm = 0;
            return 0;
        }
        this.zzwm = zzha();
        int i = this.zzwm;
        if ((i >>> 3) != 0) {
            return i;
        }
        throw zzic.zziu();
    }

    public final void zzs(int i) throws zzic {
        if (this.zzwm != i) {
            throw zzic.zziv();
        }
    }

    public final boolean zzt(int i) throws IOException {
        int zzgi;
        int i2 = i & 7;
        int i3 = 0;
        if (i2 == 0) {
            if (this.zzwp - this.pos >= 10) {
                while (i3 < 10) {
                    byte[] bArr = this.buffer;
                    int i4 = this.pos;
                    this.pos = i4 + 1;
                    if (bArr[i4] < 0) {
                        i3++;
                    }
                }
                throw zzic.zzit();
            }
            while (i3 < 10) {
                if (zzhf() < 0) {
                    i3++;
                }
            }
            throw zzic.zzit();
            return true;
        } else if (i2 == 1) {
            zzx(8);
            return true;
        } else if (i2 == 2) {
            zzx(zzha());
            return true;
        } else if (i2 == 3) {
            do {
                zzgi = zzgi();
                if (zzgi == 0) {
                    break;
                }
            } while (zzt(zzgi));
            zzs(((i >>> 3) << 3) | 4);
            return true;
        } else if (i2 == 4) {
            return false;
        } else {
            if (i2 == 5) {
                zzx(4);
                return true;
            }
            throw zzic.zziw();
        }
    }

    public final double readDouble() throws IOException {
        return Double.longBitsToDouble(zzhd());
    }

    public final float readFloat() throws IOException {
        return Float.intBitsToFloat(zzhc());
    }

    public final long zzgj() throws IOException {
        return zzhb();
    }

    public final long zzgk() throws IOException {
        return zzhb();
    }

    public final int zzgl() throws IOException {
        return zzha();
    }

    public final long zzgm() throws IOException {
        return zzhd();
    }

    public final int zzgn() throws IOException {
        return zzhc();
    }

    public final boolean zzgo() throws IOException {
        return zzhb() != 0;
    }

    public final String readString() throws IOException {
        int zzha = zzha();
        if (zzha > 0) {
            int i = this.zzwp;
            int i2 = this.pos;
            if (zzha <= i - i2) {
                String str = new String(this.buffer, i2, zzha, zzht.UTF_8);
                this.pos += zzha;
                return str;
            }
        }
        if (zzha == 0) {
            return "";
        }
        if (zzha > this.zzwp) {
            return new String(zzb(zzha, false), zzht.UTF_8);
        }
        zzy(zzha);
        String str2 = new String(this.buffer, this.pos, zzha, zzht.UTF_8);
        this.pos += zzha;
        return str2;
    }

    public final String zzgp() throws IOException {
        byte[] bArr;
        int zzha = zzha();
        int i = this.pos;
        int i2 = 0;
        if (zzha <= this.zzwp - i && zzha > 0) {
            bArr = this.buffer;
            this.pos = i + zzha;
            i2 = i;
        } else if (zzha == 0) {
            return "";
        } else {
            if (zzha <= this.zzwp) {
                zzy(zzha);
                bArr = this.buffer;
                this.pos = zzha;
            } else {
                bArr = zzb(zzha, false);
            }
        }
        return zzkt.zzg(bArr, i2, zzha);
    }

    public final zzgf zzgq() throws IOException {
        int zzha = zzha();
        int i = this.zzwp;
        int i2 = this.pos;
        if (zzha <= i - i2 && zzha > 0) {
            zzgf zza = zzgf.zza(this.buffer, i2, zzha);
            this.pos += zzha;
            return zza;
        } else if (zzha == 0) {
            return zzgf.zzvv;
        } else {
            byte[] zzaa = zzaa(zzha);
            if (zzaa != null) {
                return zzgf.zza(zzaa);
            }
            int i3 = this.pos;
            int i4 = this.zzwp;
            int i5 = i4 - i3;
            this.zzwq += i4;
            this.pos = 0;
            this.zzwp = 0;
            List<byte[]> zzab = zzab(zzha - i5);
            byte[] bArr = new byte[zzha];
            System.arraycopy(this.buffer, i3, bArr, 0, i5);
            for (byte[] bArr2 : zzab) {
                System.arraycopy(bArr2, 0, bArr, i5, bArr2.length);
                i5 += bArr2.length;
            }
            return zzgf.zzb(bArr);
        }
    }

    public final int zzgr() throws IOException {
        return zzha();
    }

    public final int zzgs() throws IOException {
        return zzha();
    }

    public final int zzgt() throws IOException {
        return zzhc();
    }

    public final long zzgu() throws IOException {
        return zzhd();
    }

    public final int zzgv() throws IOException {
        return zzw(zzha());
    }

    public final long zzgw() throws IOException {
        return zza(zzhb());
    }

    /* JADX WARNING: Code restructure failed: missing block: B:29:0x0066, code lost:
        if (r2[r3] >= 0) goto L_0x0068;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private final int zzha() throws java.io.IOException {
        /*
            r5 = this;
            int r0 = r5.pos
            int r1 = r5.zzwp
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
            long r0 = r5.zzgx()
            int r1 = (int) r0
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.firebase_auth.zzgw.zzha():int");
    }

    /* JADX WARNING: Code restructure failed: missing block: B:36:0x00b0, code lost:
        if (((long) r2[r0]) >= 0) goto L_0x00b2;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private final long zzhb() throws java.io.IOException {
        /*
            r11 = this;
            int r0 = r11.pos
            int r1 = r11.zzwp
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
            long r0 = r11.zzgx()
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.firebase_auth.zzgw.zzhb():long");
    }

    /* access modifiers changed from: 0000 */
    public final long zzgx() throws IOException {
        long j = 0;
        for (int i = 0; i < 64; i += 7) {
            byte zzhf = zzhf();
            j |= ((long) (zzhf & Byte.MAX_VALUE)) << i;
            if ((zzhf & 128) == 0) {
                return j;
            }
        }
        throw zzic.zzit();
    }

    private final int zzhc() throws IOException {
        int i = this.pos;
        if (this.zzwp - i < 4) {
            zzy(4);
            i = this.pos;
        }
        byte[] bArr = this.buffer;
        this.pos = i + 4;
        return ((bArr[i + 3] & 255) << Ascii.CAN) | (bArr[i] & 255) | ((bArr[i + 1] & 255) << 8) | ((bArr[i + 2] & 255) << Ascii.DLE);
    }

    private final long zzhd() throws IOException {
        int i = this.pos;
        if (this.zzwp - i < 8) {
            zzy(8);
            i = this.pos;
        }
        byte[] bArr = this.buffer;
        this.pos = i + 8;
        return ((((long) bArr[i + 7]) & 255) << 56) | (((long) bArr[i]) & 255) | ((((long) bArr[i + 1]) & 255) << 8) | ((((long) bArr[i + 2]) & 255) << 16) | ((((long) bArr[i + 3]) & 255) << 24) | ((((long) bArr[i + 4]) & 255) << 32) | ((((long) bArr[i + 5]) & 255) << 40) | ((((long) bArr[i + 6]) & 255) << 48);
    }

    public final int zzu(int i) throws zzic {
        if (i >= 0) {
            int i2 = i + this.zzwq + this.pos;
            int i3 = this.zzwn;
            if (i2 <= i3) {
                this.zzwn = i2;
                zzhe();
                return i3;
            }
            throw zzic.zzir();
        }
        throw zzic.zzis();
    }

    private final void zzhe() {
        this.zzwp += this.zzwk;
        int i = this.zzwq;
        int i2 = this.zzwp;
        int i3 = i + i2;
        int i4 = this.zzwn;
        if (i3 > i4) {
            this.zzwk = i3 - i4;
            this.zzwp = i2 - this.zzwk;
            return;
        }
        this.zzwk = 0;
    }

    public final void zzv(int i) {
        this.zzwn = i;
        zzhe();
    }

    public final boolean zzgy() throws IOException {
        return this.pos == this.zzwp && !zzz(1);
    }

    public final int zzgz() {
        return this.zzwq + this.pos;
    }

    private final void zzy(int i) throws IOException {
        if (zzz(i)) {
            return;
        }
        if (i > (this.zzwg - this.zzwq) - this.pos) {
            throw zzic.zzix();
        }
        throw zzic.zzir();
    }

    private final boolean zzz(int i) throws IOException {
        while (this.pos + i > this.zzwp) {
            int i2 = this.zzwg;
            int i3 = this.zzwq;
            int i4 = i2 - i3;
            int i5 = this.pos;
            if (i > i4 - i5 || i3 + i5 + i > this.zzwn) {
                return false;
            }
            if (i5 > 0) {
                int i6 = this.zzwp;
                if (i6 > i5) {
                    byte[] bArr = this.buffer;
                    System.arraycopy(bArr, i5, bArr, 0, i6 - i5);
                }
                this.zzwq += i5;
                this.zzwp -= i5;
                this.pos = 0;
            }
            InputStream inputStream = this.zzwo;
            byte[] bArr2 = this.buffer;
            int i7 = this.zzwp;
            int read = inputStream.read(bArr2, i7, Math.min(bArr2.length - i7, (this.zzwg - this.zzwq) - this.zzwp));
            if (read == 0 || read < -1 || read > this.buffer.length) {
                String valueOf = String.valueOf(this.zzwo.getClass());
                StringBuilder sb = new StringBuilder(String.valueOf(valueOf).length() + 91);
                sb.append(valueOf);
                sb.append("#read(byte[]) returned invalid result: ");
                sb.append(read);
                sb.append("\nThe InputStream implementation is buggy.");
                throw new IllegalStateException(sb.toString());
            } else if (read <= 0) {
                return false;
            } else {
                this.zzwp += read;
                zzhe();
                if (this.zzwp >= i) {
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

    private final byte zzhf() throws IOException {
        if (this.pos == this.zzwp) {
            zzy(1);
        }
        byte[] bArr = this.buffer;
        int i = this.pos;
        this.pos = i + 1;
        return bArr[i];
    }

    private final byte[] zzb(int i, boolean z) throws IOException {
        byte[] zzaa = zzaa(i);
        if (zzaa != null) {
            return zzaa;
        }
        int i2 = this.pos;
        int i3 = this.zzwp;
        int i4 = i3 - i2;
        this.zzwq += i3;
        this.pos = 0;
        this.zzwp = 0;
        List<byte[]> zzab = zzab(i - i4);
        byte[] bArr = new byte[i];
        System.arraycopy(this.buffer, i2, bArr, 0, i4);
        for (byte[] bArr2 : zzab) {
            System.arraycopy(bArr2, 0, bArr, i4, bArr2.length);
            i4 += bArr2.length;
        }
        return bArr;
    }

    private final byte[] zzaa(int i) throws IOException {
        if (i == 0) {
            return zzht.EMPTY_BYTE_ARRAY;
        }
        if (i >= 0) {
            int i2 = this.zzwq + this.pos + i;
            if (i2 - this.zzwg <= 0) {
                int i3 = this.zzwn;
                if (i2 <= i3) {
                    int i4 = this.zzwp - this.pos;
                    int i5 = i - i4;
                    if (i5 >= 4096 && i5 > this.zzwo.available()) {
                        return null;
                    }
                    byte[] bArr = new byte[i];
                    System.arraycopy(this.buffer, this.pos, bArr, 0, i4);
                    this.zzwq += this.zzwp;
                    this.pos = 0;
                    this.zzwp = 0;
                    while (i4 < bArr.length) {
                        int read = this.zzwo.read(bArr, i4, i - i4);
                        if (read != -1) {
                            this.zzwq += read;
                            i4 += read;
                        } else {
                            throw zzic.zzir();
                        }
                    }
                    return bArr;
                }
                zzx((i3 - this.zzwq) - this.pos);
                throw zzic.zzir();
            }
            throw zzic.zzix();
        }
        throw zzic.zzis();
    }

    private final List<byte[]> zzab(int i) throws IOException {
        ArrayList arrayList = new ArrayList();
        while (i > 0) {
            byte[] bArr = new byte[Math.min(i, 4096)];
            int i2 = 0;
            while (i2 < bArr.length) {
                int read = this.zzwo.read(bArr, i2, bArr.length - i2);
                if (read != -1) {
                    this.zzwq += read;
                    i2 += read;
                } else {
                    throw zzic.zzir();
                }
            }
            i -= bArr.length;
            arrayList.add(bArr);
        }
        return arrayList;
    }

    private final void zzx(int i) throws IOException {
        int i2;
        int i3 = this.zzwp;
        int i4 = this.pos;
        if (i <= i3 - i4 && i >= 0) {
            this.pos = i4 + i;
        } else if (i >= 0) {
            int i5 = this.zzwq;
            int i6 = this.pos;
            int i7 = i5 + i6 + i;
            int i8 = this.zzwn;
            if (i7 <= i8) {
                this.zzwq = i5 + i6;
                int i9 = this.zzwp - i6;
                this.zzwp = 0;
                this.pos = 0;
                while (i9 < i) {
                    try {
                        long j = (long) (i - i9);
                        long skip = this.zzwo.skip(j);
                        if (skip >= 0 && skip <= j) {
                            if (skip == 0) {
                                break;
                            }
                            i9 += (int) skip;
                        } else {
                            String valueOf = String.valueOf(this.zzwo.getClass());
                            StringBuilder sb = new StringBuilder(String.valueOf(valueOf).length() + 92);
                            sb.append(valueOf);
                            sb.append("#skip returned invalid result: ");
                            sb.append(skip);
                            sb.append("\nThe InputStream implementation is buggy.");
                            throw new IllegalStateException(sb.toString());
                        }
                    } catch (Throwable th) {
                        this.zzwq += i9;
                        zzhe();
                        throw th;
                    }
                }
                this.zzwq += i9;
                zzhe();
                if (i9 < i) {
                    int i10 = this.zzwp;
                    int i11 = i10 - this.pos;
                    this.pos = i10;
                    zzy(1);
                    while (true) {
                        i2 = i - i11;
                        int i12 = this.zzwp;
                        if (i2 <= i12) {
                            break;
                        }
                        i11 += i12;
                        this.pos = i12;
                        zzy(1);
                    }
                    this.pos = i2;
                }
                return;
            }
            zzx((i8 - i5) - i6);
            throw zzic.zzir();
        } else {
            throw zzic.zzis();
        }
    }
}
