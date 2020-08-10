package com.google.android.gms.internal.firebase_remote_config;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.objectweb.asm.Opcodes;

public abstract class zzgp extends zzfv {
    private static final Logger logger = Logger.getLogger(zzgp.class.getName());
    /* access modifiers changed from: private */
    public static final boolean zzpp = zzkh.zzjh();
    zzgr zzpq = this;

    static class zza extends zzgp {
        private final byte[] buffer;
        private final int limit;
        private final int offset;
        private int position;

        zza(byte[] bArr, int i, int i2) {
            super();
            if (bArr != null) {
                int i3 = i2 + 0;
                if ((i2 | 0 | (bArr.length - i3)) >= 0) {
                    this.buffer = bArr;
                    this.offset = 0;
                    this.position = 0;
                    this.limit = i3;
                    return;
                }
                throw new IllegalArgumentException(String.format("Array range is invalid. Buffer.length=%d, offset=%d, length=%d", new Object[]{Integer.valueOf(bArr.length), Integer.valueOf(0), Integer.valueOf(i2)}));
            }
            throw new NullPointerException("buffer");
        }

        public final void zzd(int i, int i2) throws IOException {
            zzao((i << 3) | i2);
        }

        public final void zze(int i, int i2) throws IOException {
            zzd(i, 0);
            zzan(i2);
        }

        public final void zzf(int i, int i2) throws IOException {
            zzd(i, 0);
            zzao(i2);
        }

        public final void zzh(int i, int i2) throws IOException {
            zzd(i, 5);
            zzaq(i2);
        }

        public final void zza(int i, long j) throws IOException {
            zzd(i, 0);
            zzf(j);
        }

        public final void zzc(int i, long j) throws IOException {
            zzd(i, 1);
            zzh(j);
        }

        public final void zzb(int i, boolean z) throws IOException {
            zzd(i, 0);
            zzc(z ? (byte) 1 : 0);
        }

        public final void zzb(int i, String str) throws IOException {
            zzd(i, 2);
            zzbm(str);
        }

        public final void zza(int i, zzfw zzfw) throws IOException {
            zzd(i, 2);
            zzb(zzfw);
        }

        public final void zzb(zzfw zzfw) throws IOException {
            zzao(zzfw.size());
            zzfw.zza((zzfv) this);
        }

        public final void zzd(byte[] bArr, int i, int i2) throws IOException {
            zzao(i2);
            write(bArr, 0, i2);
        }

        /* access modifiers changed from: 0000 */
        public final void zza(int i, zzio zzio, zzjj zzjj) throws IOException {
            zzd(i, 2);
            zzfn zzfn = (zzfn) zzio;
            int zzeo = zzfn.zzeo();
            if (zzeo == -1) {
                zzeo = zzjj.zzw(zzfn);
                zzfn.zzr(zzeo);
            }
            zzao(zzeo);
            zzjj.zza(zzio, this.zzpq);
        }

        public final void zza(int i, zzio zzio) throws IOException {
            zzd(1, 3);
            zzf(2, i);
            zzd(3, 2);
            zzb(zzio);
            zzd(1, 4);
        }

        public final void zzb(int i, zzfw zzfw) throws IOException {
            zzd(1, 3);
            zzf(2, i);
            zza(3, zzfw);
            zzd(1, 4);
        }

        public final void zzb(zzio zzio) throws IOException {
            zzao(zzio.zzgo());
            zzio.zzb(this);
        }

        public final void zzc(byte b) throws IOException {
            try {
                byte[] bArr = this.buffer;
                int i = this.position;
                this.position = i + 1;
                bArr[i] = b;
            } catch (IndexOutOfBoundsException e) {
                throw new zzb(String.format("Pos: %d, limit: %d, len: %d", new Object[]{Integer.valueOf(this.position), Integer.valueOf(this.limit), Integer.valueOf(1)}), e);
            }
        }

        public final void zzan(int i) throws IOException {
            if (i >= 0) {
                zzao(i);
            } else {
                zzf((long) i);
            }
        }

        public final void zzao(int i) throws IOException {
            if (!zzgp.zzpp || zzgd() < 10) {
                while ((i & -128) != 0) {
                    byte[] bArr = this.buffer;
                    int i2 = this.position;
                    this.position = i2 + 1;
                    bArr[i2] = (byte) ((i & Opcodes.LAND) | 128);
                    i >>>= 7;
                }
                try {
                    byte[] bArr2 = this.buffer;
                    int i3 = this.position;
                    this.position = i3 + 1;
                    bArr2[i3] = (byte) i;
                } catch (IndexOutOfBoundsException e) {
                    throw new zzb(String.format("Pos: %d, limit: %d, len: %d", new Object[]{Integer.valueOf(this.position), Integer.valueOf(this.limit), Integer.valueOf(1)}), e);
                }
            } else {
                while ((i & -128) != 0) {
                    byte[] bArr3 = this.buffer;
                    int i4 = this.position;
                    this.position = i4 + 1;
                    zzkh.zza(bArr3, (long) i4, (byte) ((i & Opcodes.LAND) | 128));
                    i >>>= 7;
                }
                byte[] bArr4 = this.buffer;
                int i5 = this.position;
                this.position = i5 + 1;
                zzkh.zza(bArr4, (long) i5, (byte) i);
            }
        }

        public final void zzaq(int i) throws IOException {
            try {
                byte[] bArr = this.buffer;
                int i2 = this.position;
                this.position = i2 + 1;
                bArr[i2] = (byte) i;
                byte[] bArr2 = this.buffer;
                int i3 = this.position;
                this.position = i3 + 1;
                bArr2[i3] = (byte) (i >> 8);
                byte[] bArr3 = this.buffer;
                int i4 = this.position;
                this.position = i4 + 1;
                bArr3[i4] = (byte) (i >> 16);
                byte[] bArr4 = this.buffer;
                int i5 = this.position;
                this.position = i5 + 1;
                bArr4[i5] = (byte) (i >>> 24);
            } catch (IndexOutOfBoundsException e) {
                throw new zzb(String.format("Pos: %d, limit: %d, len: %d", new Object[]{Integer.valueOf(this.position), Integer.valueOf(this.limit), Integer.valueOf(1)}), e);
            }
        }

        public final void zzf(long j) throws IOException {
            if (!zzgp.zzpp || zzgd() < 10) {
                while ((j & -128) != 0) {
                    byte[] bArr = this.buffer;
                    int i = this.position;
                    this.position = i + 1;
                    bArr[i] = (byte) ((((int) j) & Opcodes.LAND) | 128);
                    j >>>= 7;
                }
                try {
                    byte[] bArr2 = this.buffer;
                    int i2 = this.position;
                    this.position = i2 + 1;
                    bArr2[i2] = (byte) ((int) j);
                } catch (IndexOutOfBoundsException e) {
                    throw new zzb(String.format("Pos: %d, limit: %d, len: %d", new Object[]{Integer.valueOf(this.position), Integer.valueOf(this.limit), Integer.valueOf(1)}), e);
                }
            } else {
                while ((j & -128) != 0) {
                    byte[] bArr3 = this.buffer;
                    int i3 = this.position;
                    this.position = i3 + 1;
                    zzkh.zza(bArr3, (long) i3, (byte) ((((int) j) & Opcodes.LAND) | 128));
                    j >>>= 7;
                }
                byte[] bArr4 = this.buffer;
                int i4 = this.position;
                this.position = i4 + 1;
                zzkh.zza(bArr4, (long) i4, (byte) ((int) j));
            }
        }

        public final void zzh(long j) throws IOException {
            try {
                byte[] bArr = this.buffer;
                int i = this.position;
                this.position = i + 1;
                bArr[i] = (byte) ((int) j);
                byte[] bArr2 = this.buffer;
                int i2 = this.position;
                this.position = i2 + 1;
                bArr2[i2] = (byte) ((int) (j >> 8));
                byte[] bArr3 = this.buffer;
                int i3 = this.position;
                this.position = i3 + 1;
                bArr3[i3] = (byte) ((int) (j >> 16));
                byte[] bArr4 = this.buffer;
                int i4 = this.position;
                this.position = i4 + 1;
                bArr4[i4] = (byte) ((int) (j >> 24));
                byte[] bArr5 = this.buffer;
                int i5 = this.position;
                this.position = i5 + 1;
                bArr5[i5] = (byte) ((int) (j >> 32));
                byte[] bArr6 = this.buffer;
                int i6 = this.position;
                this.position = i6 + 1;
                bArr6[i6] = (byte) ((int) (j >> 40));
                byte[] bArr7 = this.buffer;
                int i7 = this.position;
                this.position = i7 + 1;
                bArr7[i7] = (byte) ((int) (j >> 48));
                byte[] bArr8 = this.buffer;
                int i8 = this.position;
                this.position = i8 + 1;
                bArr8[i8] = (byte) ((int) (j >> 56));
            } catch (IndexOutOfBoundsException e) {
                throw new zzb(String.format("Pos: %d, limit: %d, len: %d", new Object[]{Integer.valueOf(this.position), Integer.valueOf(this.limit), Integer.valueOf(1)}), e);
            }
        }

        private final void write(byte[] bArr, int i, int i2) throws IOException {
            try {
                System.arraycopy(bArr, i, this.buffer, this.position, i2);
                this.position += i2;
            } catch (IndexOutOfBoundsException e) {
                throw new zzb(String.format("Pos: %d, limit: %d, len: %d", new Object[]{Integer.valueOf(this.position), Integer.valueOf(this.limit), Integer.valueOf(i2)}), e);
            }
        }

        public final void zza(byte[] bArr, int i, int i2) throws IOException {
            write(bArr, i, i2);
        }

        public final void zzbm(String str) throws IOException {
            int i = this.position;
            try {
                int zzat = zzat(str.length() * 3);
                int zzat2 = zzat(str.length());
                if (zzat2 == zzat) {
                    this.position = i + zzat2;
                    int zza = zzkj.zza(str, this.buffer, this.position, zzgd());
                    this.position = i;
                    zzao((zza - i) - zzat2);
                    this.position = zza;
                    return;
                }
                zzao(zzkj.zzb(str));
                this.position = zzkj.zza(str, this.buffer, this.position, zzgd());
            } catch (zzkn e) {
                this.position = i;
                zza(str, e);
            } catch (IndexOutOfBoundsException e2) {
                throw new zzb(e2);
            }
        }

        public final int zzgd() {
            return this.limit - this.position;
        }
    }

    public static class zzb extends IOException {
        zzb() {
            super("CodedOutputStream was writing to a flat byte array and ran out of space.");
        }

        zzb(Throwable th) {
            super("CodedOutputStream was writing to a flat byte array and ran out of space.", th);
        }

        zzb(String str, Throwable th) {
            String valueOf = String.valueOf(str);
            String str2 = "CodedOutputStream was writing to a flat byte array and ran out of space.: ";
            super(valueOf.length() != 0 ? str2.concat(valueOf) : new String(str2), th);
        }
    }

    public static int zzat(int i) {
        if ((i & -128) == 0) {
            return 1;
        }
        if ((i & -16384) == 0) {
            return 2;
        }
        if ((-2097152 & i) == 0) {
            return 3;
        }
        return (i & -268435456) == 0 ? 4 : 5;
    }

    public static int zzav(int i) {
        return 4;
    }

    public static int zzaw(int i) {
        return 4;
    }

    private static int zzay(int i) {
        return (i >> 31) ^ (i << 1);
    }

    public static zzgp zzb(byte[] bArr) {
        return new zza(bArr, 0, bArr.length);
    }

    public static int zzc(float f) {
        return 4;
    }

    public static int zzd(double d) {
        return 8;
    }

    public static int zzf(boolean z) {
        return 1;
    }

    public static int zzj(long j) {
        int i;
        if ((-128 & j) == 0) {
            return 1;
        }
        if (j < 0) {
            return 10;
        }
        if ((-34359738368L & j) != 0) {
            i = 6;
            j >>>= 28;
        } else {
            i = 2;
        }
        if ((-2097152 & j) != 0) {
            i += 2;
            j >>>= 14;
        }
        if ((j & -16384) != 0) {
            i++;
        }
        return i;
    }

    public static int zzl(long j) {
        return 8;
    }

    public static int zzm(long j) {
        return 8;
    }

    private static long zzn(long j) {
        return (j >> 63) ^ (j << 1);
    }

    public abstract void zza(int i, long j) throws IOException;

    public abstract void zza(int i, zzfw zzfw) throws IOException;

    public abstract void zza(int i, zzio zzio) throws IOException;

    /* access modifiers changed from: 0000 */
    public abstract void zza(int i, zzio zzio, zzjj zzjj) throws IOException;

    public abstract void zzan(int i) throws IOException;

    public abstract void zzao(int i) throws IOException;

    public abstract void zzaq(int i) throws IOException;

    public abstract void zzb(int i, zzfw zzfw) throws IOException;

    public abstract void zzb(int i, String str) throws IOException;

    public abstract void zzb(int i, boolean z) throws IOException;

    public abstract void zzb(zzfw zzfw) throws IOException;

    public abstract void zzb(zzio zzio) throws IOException;

    public abstract void zzbm(String str) throws IOException;

    public abstract void zzc(byte b) throws IOException;

    public abstract void zzc(int i, long j) throws IOException;

    public abstract void zzd(int i, int i2) throws IOException;

    /* access modifiers changed from: 0000 */
    public abstract void zzd(byte[] bArr, int i, int i2) throws IOException;

    public abstract void zze(int i, int i2) throws IOException;

    public abstract void zzf(int i, int i2) throws IOException;

    public abstract void zzf(long j) throws IOException;

    public abstract int zzgd();

    public abstract void zzh(int i, int i2) throws IOException;

    public abstract void zzh(long j) throws IOException;

    private zzgp() {
    }

    public final void zzg(int i, int i2) throws IOException {
        zzf(i, zzay(i2));
    }

    public final void zzb(int i, long j) throws IOException {
        zza(i, zzn(j));
    }

    public final void zza(int i, float f) throws IOException {
        zzh(i, Float.floatToRawIntBits(f));
    }

    public final void zza(int i, double d) throws IOException {
        zzc(i, Double.doubleToRawLongBits(d));
    }

    public final void zzap(int i) throws IOException {
        zzao(zzay(i));
    }

    public final void zzg(long j) throws IOException {
        zzf(zzn(j));
    }

    public final void zzb(float f) throws IOException {
        zzaq(Float.floatToRawIntBits(f));
    }

    public final void zzc(double d) throws IOException {
        zzh(Double.doubleToRawLongBits(d));
    }

    public final void zze(boolean z) throws IOException {
        zzc(z ? (byte) 1 : 0);
    }

    public static int zzi(int i, int i2) {
        return zzar(i) + zzas(i2);
    }

    public static int zzj(int i, int i2) {
        return zzar(i) + zzat(i2);
    }

    public static int zzk(int i, int i2) {
        return zzar(i) + zzat(zzay(i2));
    }

    public static int zzl(int i, int i2) {
        return zzar(i) + 4;
    }

    public static int zzm(int i, int i2) {
        return zzar(i) + 4;
    }

    public static int zzd(int i, long j) {
        return zzar(i) + zzj(j);
    }

    public static int zze(int i, long j) {
        return zzar(i) + zzj(j);
    }

    public static int zzf(int i, long j) {
        return zzar(i) + zzj(zzn(j));
    }

    public static int zzg(int i, long j) {
        return zzar(i) + 8;
    }

    public static int zzh(int i, long j) {
        return zzar(i) + 8;
    }

    public static int zzb(int i, float f) {
        return zzar(i) + 4;
    }

    public static int zzb(int i, double d) {
        return zzar(i) + 8;
    }

    public static int zzc(int i, boolean z) {
        return zzar(i) + 1;
    }

    public static int zzn(int i, int i2) {
        return zzar(i) + zzas(i2);
    }

    public static int zzc(int i, String str) {
        return zzar(i) + zzbn(str);
    }

    public static int zzc(int i, zzfw zzfw) {
        int zzar = zzar(i);
        int size = zzfw.size();
        return zzar + zzat(size) + size;
    }

    public static int zza(int i, zzhv zzhv) {
        int zzar = zzar(i);
        int zzgo = zzhv.zzgo();
        return zzar + zzat(zzgo) + zzgo;
    }

    static int zzb(int i, zzio zzio, zzjj zzjj) {
        return zzar(i) + zza(zzio, zzjj);
    }

    public static int zzb(int i, zzio zzio) {
        return (zzar(1) << 1) + zzj(2, i) + zzar(3) + zzc(zzio);
    }

    public static int zzd(int i, zzfw zzfw) {
        return (zzar(1) << 1) + zzj(2, i) + zzc(3, zzfw);
    }

    public static int zzb(int i, zzhv zzhv) {
        return (zzar(1) << 1) + zzj(2, i) + zza(3, zzhv);
    }

    public static int zzar(int i) {
        return zzat(i << 3);
    }

    public static int zzas(int i) {
        if (i >= 0) {
            return zzat(i);
        }
        return 10;
    }

    public static int zzau(int i) {
        return zzat(zzay(i));
    }

    public static int zzi(long j) {
        return zzj(j);
    }

    public static int zzk(long j) {
        return zzj(zzn(j));
    }

    public static int zzax(int i) {
        return zzas(i);
    }

    public static int zzbn(String str) {
        int i;
        try {
            i = zzkj.zzb(str);
        } catch (zzkn unused) {
            i = str.getBytes(zzhk.UTF_8).length;
        }
        return zzat(i) + i;
    }

    public static int zza(zzhv zzhv) {
        int zzgo = zzhv.zzgo();
        return zzat(zzgo) + zzgo;
    }

    public static int zzc(zzfw zzfw) {
        int size = zzfw.size();
        return zzat(size) + size;
    }

    public static int zzc(byte[] bArr) {
        int length = bArr.length;
        return zzat(length) + length;
    }

    public static int zzc(zzio zzio) {
        int zzgo = zzio.zzgo();
        return zzat(zzgo) + zzgo;
    }

    static int zza(zzio zzio, zzjj zzjj) {
        zzfn zzfn = (zzfn) zzio;
        int zzeo = zzfn.zzeo();
        if (zzeo == -1) {
            zzeo = zzjj.zzw(zzfn);
            zzfn.zzr(zzeo);
        }
        return zzat(zzeo) + zzeo;
    }

    /* access modifiers changed from: 0000 */
    public final void zza(String str, zzkn zzkn) throws IOException {
        logger.logp(Level.WARNING, "com.google.protobuf.CodedOutputStream", "inefficientWriteStringNoTag", "Converting ill-formed UTF-16. Your Protocol Buffer will not round trip correctly!", zzkn);
        byte[] bytes = str.getBytes(zzhk.UTF_8);
        try {
            zzao(bytes.length);
            zza(bytes, 0, bytes.length);
        } catch (IndexOutOfBoundsException e) {
            throw new zzb(e);
        } catch (zzb e2) {
            throw e2;
        }
    }

    @Deprecated
    static int zzc(int i, zzio zzio, zzjj zzjj) {
        int zzar = zzar(i) << 1;
        zzfn zzfn = (zzfn) zzio;
        int zzeo = zzfn.zzeo();
        if (zzeo == -1) {
            zzeo = zzjj.zzw(zzfn);
            zzfn.zzr(zzeo);
        }
        return zzar + zzeo;
    }

    @Deprecated
    public static int zzd(zzio zzio) {
        return zzio.zzgo();
    }

    @Deprecated
    public static int zzaz(int i) {
        return zzat(i);
    }
}
