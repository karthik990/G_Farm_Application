package com.google.android.gms.internal.gtm;

import java.lang.reflect.Field;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.security.AccessController;
import java.util.logging.Level;
import java.util.logging.Logger;
import libcore.io.Memory;
import sun.misc.Unsafe;

final class zztx {
    private static final Logger logger = Logger.getLogger(zztx.class.getName());
    private static final Class<?> zzavt = zzpp.zznb();
    private static final boolean zzawt = zzrp();
    private static final Unsafe zzbcx = zzro();
    private static final boolean zzbet = zzn(Long.TYPE);
    private static final boolean zzbeu = zzn(Integer.TYPE);
    private static final zzd zzbev;
    private static final boolean zzbew = zzrq();
    static final long zzbex = ((long) zzl(byte[].class));
    private static final long zzbey;
    private static final long zzbez;
    private static final long zzbfa;
    private static final long zzbfb;
    private static final long zzbfc;
    private static final long zzbfd;
    private static final long zzbfe;
    private static final long zzbff;
    private static final long zzbfg;
    private static final long zzbfh;
    private static final long zzbfi = ((long) zzl(Object[].class));
    private static final long zzbfj = ((long) zzm(Object[].class));
    private static final long zzbfk;
    private static final int zzbfl = ((int) (zzbex & 7));
    static final boolean zzbfm = (ByteOrder.nativeOrder() != ByteOrder.BIG_ENDIAN);

    static final class zza extends zzd {
        zza(Unsafe unsafe) {
            super(unsafe);
        }

        public final void zza(long j, byte b) {
            Memory.pokeByte((int) (j & -1), b);
        }

        public final byte zzy(Object obj, long j) {
            if (zztx.zzbfm) {
                return zztx.zzq(obj, j);
            }
            return zztx.zzr(obj, j);
        }

        public final void zze(Object obj, long j, byte b) {
            if (zztx.zzbfm) {
                zztx.zza(obj, j, b);
            } else {
                zztx.zzb(obj, j, b);
            }
        }

        public final boolean zzm(Object obj, long j) {
            if (zztx.zzbfm) {
                return zztx.zzs(obj, j);
            }
            return zztx.zzt(obj, j);
        }

        public final void zza(Object obj, long j, boolean z) {
            if (zztx.zzbfm) {
                zztx.zzb(obj, j, z);
            } else {
                zztx.zzc(obj, j, z);
            }
        }

        public final float zzn(Object obj, long j) {
            return Float.intBitsToFloat(zzk(obj, j));
        }

        public final void zza(Object obj, long j, float f) {
            zzb(obj, j, Float.floatToIntBits(f));
        }

        public final double zzo(Object obj, long j) {
            return Double.longBitsToDouble(zzl(obj, j));
        }

        public final void zza(Object obj, long j, double d) {
            zza(obj, j, Double.doubleToLongBits(d));
        }

        public final void zza(byte[] bArr, long j, long j2, long j3) {
            Memory.pokeByteArray((int) (j2 & -1), bArr, (int) j, (int) j3);
        }
    }

    static final class zzb extends zzd {
        zzb(Unsafe unsafe) {
            super(unsafe);
        }

        public final void zza(long j, byte b) {
            Memory.pokeByte(j, b);
        }

        public final byte zzy(Object obj, long j) {
            if (zztx.zzbfm) {
                return zztx.zzq(obj, j);
            }
            return zztx.zzr(obj, j);
        }

        public final void zze(Object obj, long j, byte b) {
            if (zztx.zzbfm) {
                zztx.zza(obj, j, b);
            } else {
                zztx.zzb(obj, j, b);
            }
        }

        public final boolean zzm(Object obj, long j) {
            if (zztx.zzbfm) {
                return zztx.zzs(obj, j);
            }
            return zztx.zzt(obj, j);
        }

        public final void zza(Object obj, long j, boolean z) {
            if (zztx.zzbfm) {
                zztx.zzb(obj, j, z);
            } else {
                zztx.zzc(obj, j, z);
            }
        }

        public final float zzn(Object obj, long j) {
            return Float.intBitsToFloat(zzk(obj, j));
        }

        public final void zza(Object obj, long j, float f) {
            zzb(obj, j, Float.floatToIntBits(f));
        }

        public final double zzo(Object obj, long j) {
            return Double.longBitsToDouble(zzl(obj, j));
        }

        public final void zza(Object obj, long j, double d) {
            zza(obj, j, Double.doubleToLongBits(d));
        }

        public final void zza(byte[] bArr, long j, long j2, long j3) {
            Memory.pokeByteArray(j2, bArr, (int) j, (int) j3);
        }
    }

    static final class zzc extends zzd {
        zzc(Unsafe unsafe) {
            super(unsafe);
        }

        public final void zza(long j, byte b) {
            this.zzbfn.putByte(j, b);
        }

        public final byte zzy(Object obj, long j) {
            return this.zzbfn.getByte(obj, j);
        }

        public final void zze(Object obj, long j, byte b) {
            this.zzbfn.putByte(obj, j, b);
        }

        public final boolean zzm(Object obj, long j) {
            return this.zzbfn.getBoolean(obj, j);
        }

        public final void zza(Object obj, long j, boolean z) {
            this.zzbfn.putBoolean(obj, j, z);
        }

        public final float zzn(Object obj, long j) {
            return this.zzbfn.getFloat(obj, j);
        }

        public final void zza(Object obj, long j, float f) {
            this.zzbfn.putFloat(obj, j, f);
        }

        public final double zzo(Object obj, long j) {
            return this.zzbfn.getDouble(obj, j);
        }

        public final void zza(Object obj, long j, double d) {
            this.zzbfn.putDouble(obj, j, d);
        }

        public final void zza(byte[] bArr, long j, long j2, long j3) {
            this.zzbfn.copyMemory(bArr, zztx.zzbex + j, null, j2, j3);
        }
    }

    static abstract class zzd {
        Unsafe zzbfn;

        zzd(Unsafe unsafe) {
            this.zzbfn = unsafe;
        }

        public abstract void zza(long j, byte b);

        public abstract void zza(Object obj, long j, double d);

        public abstract void zza(Object obj, long j, float f);

        public abstract void zza(Object obj, long j, boolean z);

        public abstract void zza(byte[] bArr, long j, long j2, long j3);

        public abstract void zze(Object obj, long j, byte b);

        public abstract boolean zzm(Object obj, long j);

        public abstract float zzn(Object obj, long j);

        public abstract double zzo(Object obj, long j);

        public abstract byte zzy(Object obj, long j);

        public final int zzk(Object obj, long j) {
            return this.zzbfn.getInt(obj, j);
        }

        public final void zzb(Object obj, long j, int i) {
            this.zzbfn.putInt(obj, j, i);
        }

        public final long zzl(Object obj, long j) {
            return this.zzbfn.getLong(obj, j);
        }

        public final void zza(Object obj, long j, long j2) {
            this.zzbfn.putLong(obj, j, j2);
        }
    }

    private zztx() {
    }

    static boolean zzrm() {
        return zzawt;
    }

    static boolean zzrn() {
        return zzbew;
    }

    static <T> T zzk(Class<T> cls) {
        try {
            return zzbcx.allocateInstance(cls);
        } catch (InstantiationException e) {
            throw new IllegalStateException(e);
        }
    }

    private static int zzl(Class<?> cls) {
        if (zzawt) {
            return zzbev.zzbfn.arrayBaseOffset(cls);
        }
        return -1;
    }

    private static int zzm(Class<?> cls) {
        if (zzawt) {
            return zzbev.zzbfn.arrayIndexScale(cls);
        }
        return -1;
    }

    static int zzk(Object obj, long j) {
        return zzbev.zzk(obj, j);
    }

    static void zzb(Object obj, long j, int i) {
        zzbev.zzb(obj, j, i);
    }

    static long zzl(Object obj, long j) {
        return zzbev.zzl(obj, j);
    }

    static void zza(Object obj, long j, long j2) {
        zzbev.zza(obj, j, j2);
    }

    static boolean zzm(Object obj, long j) {
        return zzbev.zzm(obj, j);
    }

    static void zza(Object obj, long j, boolean z) {
        zzbev.zza(obj, j, z);
    }

    static float zzn(Object obj, long j) {
        return zzbev.zzn(obj, j);
    }

    static void zza(Object obj, long j, float f) {
        zzbev.zza(obj, j, f);
    }

    static double zzo(Object obj, long j) {
        return zzbev.zzo(obj, j);
    }

    static void zza(Object obj, long j, double d) {
        zzbev.zza(obj, j, d);
    }

    static Object zzp(Object obj, long j) {
        return zzbev.zzbfn.getObject(obj, j);
    }

    static void zza(Object obj, long j, Object obj2) {
        zzbev.zzbfn.putObject(obj, j, obj2);
    }

    static byte zza(byte[] bArr, long j) {
        return zzbev.zzy(bArr, zzbex + j);
    }

    static void zza(byte[] bArr, long j, byte b) {
        zzbev.zze(bArr, zzbex + j, b);
    }

    static void zza(byte[] bArr, long j, long j2, long j3) {
        zzbev.zza(bArr, j, j2, j3);
    }

    static void zza(long j, byte b) {
        zzbev.zza(j, b);
    }

    static long zzb(ByteBuffer byteBuffer) {
        return zzbev.zzl(byteBuffer, zzbfk);
    }

    static Unsafe zzro() {
        try {
            return (Unsafe) AccessController.doPrivileged(new zzty());
        } catch (Throwable unused) {
            return null;
        }
    }

    private static boolean zzrp() {
        Unsafe unsafe = zzbcx;
        if (unsafe == null) {
            return false;
        }
        try {
            Class cls = unsafe.getClass();
            cls.getMethod("objectFieldOffset", new Class[]{Field.class});
            cls.getMethod("arrayBaseOffset", new Class[]{Class.class});
            cls.getMethod("arrayIndexScale", new Class[]{Class.class});
            cls.getMethod("getInt", new Class[]{Object.class, Long.TYPE});
            cls.getMethod("putInt", new Class[]{Object.class, Long.TYPE, Integer.TYPE});
            cls.getMethod("getLong", new Class[]{Object.class, Long.TYPE});
            cls.getMethod("putLong", new Class[]{Object.class, Long.TYPE, Long.TYPE});
            cls.getMethod("getObject", new Class[]{Object.class, Long.TYPE});
            cls.getMethod("putObject", new Class[]{Object.class, Long.TYPE, Object.class});
            if (zzpp.zzna()) {
                return true;
            }
            cls.getMethod("getByte", new Class[]{Object.class, Long.TYPE});
            cls.getMethod("putByte", new Class[]{Object.class, Long.TYPE, Byte.TYPE});
            cls.getMethod("getBoolean", new Class[]{Object.class, Long.TYPE});
            cls.getMethod("putBoolean", new Class[]{Object.class, Long.TYPE, Boolean.TYPE});
            cls.getMethod("getFloat", new Class[]{Object.class, Long.TYPE});
            cls.getMethod("putFloat", new Class[]{Object.class, Long.TYPE, Float.TYPE});
            cls.getMethod("getDouble", new Class[]{Object.class, Long.TYPE});
            cls.getMethod("putDouble", new Class[]{Object.class, Long.TYPE, Double.TYPE});
            return true;
        } catch (Throwable th) {
            Logger logger2 = logger;
            Level level = Level.WARNING;
            String valueOf = String.valueOf(th);
            StringBuilder sb = new StringBuilder(String.valueOf(valueOf).length() + 71);
            sb.append("platform method missing - proto runtime falling back to safer methods: ");
            sb.append(valueOf);
            logger2.logp(level, "com.google.protobuf.UnsafeUtil", "supportsUnsafeArrayOperations", sb.toString());
            return false;
        }
    }

    private static boolean zzrq() {
        String str = "copyMemory";
        String str2 = "getLong";
        Unsafe unsafe = zzbcx;
        if (unsafe == null) {
            return false;
        }
        try {
            Class cls = unsafe.getClass();
            cls.getMethod("objectFieldOffset", new Class[]{Field.class});
            cls.getMethod(str2, new Class[]{Object.class, Long.TYPE});
            if (zzrr() == null) {
                return false;
            }
            if (zzpp.zzna()) {
                return true;
            }
            cls.getMethod("getByte", new Class[]{Long.TYPE});
            cls.getMethod("putByte", new Class[]{Long.TYPE, Byte.TYPE});
            cls.getMethod("getInt", new Class[]{Long.TYPE});
            cls.getMethod("putInt", new Class[]{Long.TYPE, Integer.TYPE});
            cls.getMethod(str2, new Class[]{Long.TYPE});
            cls.getMethod("putLong", new Class[]{Long.TYPE, Long.TYPE});
            cls.getMethod(str, new Class[]{Long.TYPE, Long.TYPE, Long.TYPE});
            cls.getMethod(str, new Class[]{Object.class, Long.TYPE, Object.class, Long.TYPE, Long.TYPE});
            return true;
        } catch (Throwable th) {
            Logger logger2 = logger;
            Level level = Level.WARNING;
            String valueOf = String.valueOf(th);
            StringBuilder sb = new StringBuilder(String.valueOf(valueOf).length() + 71);
            sb.append("platform method missing - proto runtime falling back to safer methods: ");
            sb.append(valueOf);
            logger2.logp(level, "com.google.protobuf.UnsafeUtil", "supportsUnsafeByteBufferOperations", sb.toString());
            return false;
        }
    }

    private static boolean zzn(Class<?> cls) {
        Class<byte[]> cls2 = byte[].class;
        if (!zzpp.zzna()) {
            return false;
        }
        try {
            Class<?> cls3 = zzavt;
            cls3.getMethod("peekLong", new Class[]{cls, Boolean.TYPE});
            cls3.getMethod("pokeLong", new Class[]{cls, Long.TYPE, Boolean.TYPE});
            cls3.getMethod("pokeInt", new Class[]{cls, Integer.TYPE, Boolean.TYPE});
            cls3.getMethod("peekInt", new Class[]{cls, Boolean.TYPE});
            cls3.getMethod("pokeByte", new Class[]{cls, Byte.TYPE});
            cls3.getMethod("peekByte", new Class[]{cls});
            cls3.getMethod("pokeByteArray", new Class[]{cls, cls2, Integer.TYPE, Integer.TYPE});
            cls3.getMethod("peekByteArray", new Class[]{cls, cls2, Integer.TYPE, Integer.TYPE});
            return true;
        } catch (Throwable unused) {
            return false;
        }
    }

    private static Field zzrr() {
        if (zzpp.zzna()) {
            Field zzb2 = zzb(Buffer.class, "effectiveDirectAddress");
            if (zzb2 != null) {
                return zzb2;
            }
        }
        Field zzb3 = zzb(Buffer.class, "address");
        if (zzb3 == null || zzb3.getType() != Long.TYPE) {
            return null;
        }
        return zzb3;
    }

    static int zza(byte[] bArr, int i, byte[] bArr2, int i2, int i3) {
        int i4;
        if (i < 0 || i2 < 0 || i3 < 0 || i + i3 > bArr.length || i2 + i3 > bArr2.length) {
            throw new IndexOutOfBoundsException();
        }
        int i5 = 0;
        if (zzawt) {
            int i6 = (zzbfl + i) & 7;
            while (i5 < i3 && (i6 & 7) != 0) {
                if (bArr[i + i5] != bArr2[i2 + i5]) {
                    return i5;
                }
                i5++;
                i6++;
            }
            int i7 = ((i3 - i5) & -8) + i5;
            while (i5 < i7) {
                long j = (long) i5;
                long zzl = zzl(bArr, zzbex + ((long) i) + j);
                long zzl2 = zzl(bArr2, zzbex + ((long) i2) + j);
                if (zzl != zzl2) {
                    if (zzbfm) {
                        i4 = Long.numberOfLeadingZeros(zzl ^ zzl2);
                    } else {
                        i4 = Long.numberOfTrailingZeros(zzl ^ zzl2);
                    }
                    return i5 + (i4 >> 3);
                }
                i5 += 8;
            }
        }
        while (i5 < i3) {
            if (bArr[i + i5] != bArr2[i2 + i5]) {
                return i5;
            }
            i5++;
        }
        return -1;
    }

    private static Field zzb(Class<?> cls, String str) {
        try {
            return cls.getDeclaredField(str);
        } catch (Throwable unused) {
            return null;
        }
    }

    /* access modifiers changed from: private */
    public static byte zzq(Object obj, long j) {
        return (byte) (zzk(obj, -4 & j) >>> ((int) (((j ^ -1) & 3) << 3)));
    }

    /* access modifiers changed from: private */
    public static byte zzr(Object obj, long j) {
        return (byte) (zzk(obj, -4 & j) >>> ((int) ((j & 3) << 3)));
    }

    /* access modifiers changed from: private */
    public static void zza(Object obj, long j, byte b) {
        long j2 = -4 & j;
        int i = ((((int) j) ^ -1) & 3) << 3;
        zzb(obj, j2, ((255 & b) << i) | (zzk(obj, j2) & ((255 << i) ^ -1)));
    }

    /* access modifiers changed from: private */
    public static void zzb(Object obj, long j, byte b) {
        long j2 = -4 & j;
        int i = (((int) j) & 3) << 3;
        zzb(obj, j2, ((255 & b) << i) | (zzk(obj, j2) & ((255 << i) ^ -1)));
    }

    /* access modifiers changed from: private */
    public static boolean zzs(Object obj, long j) {
        return zzq(obj, j) != 0;
    }

    /* access modifiers changed from: private */
    public static boolean zzt(Object obj, long j) {
        return zzr(obj, j) != 0;
    }

    /* access modifiers changed from: private */
    public static void zzb(Object obj, long j, boolean z) {
        zza(obj, j, z ? (byte) 1 : 0);
    }

    /* access modifiers changed from: private */
    public static void zzc(Object obj, long j, boolean z) {
        zzb(obj, j, z ? (byte) 1 : 0);
    }

    /* JADX WARNING: Removed duplicated region for block: B:19:0x00f2  */
    /* JADX WARNING: Removed duplicated region for block: B:20:0x00f4  */
    static {
        /*
            java.lang.Class<double[]> r0 = double[].class
            java.lang.Class<float[]> r1 = float[].class
            java.lang.Class<long[]> r2 = long[].class
            java.lang.Class<int[]> r3 = int[].class
            java.lang.Class<boolean[]> r4 = boolean[].class
            java.lang.Class<com.google.android.gms.internal.gtm.zztx> r5 = com.google.android.gms.internal.gtm.zztx.class
            java.lang.String r5 = r5.getName()
            java.util.logging.Logger r5 = java.util.logging.Logger.getLogger(r5)
            logger = r5
            sun.misc.Unsafe r5 = zzro()
            zzbcx = r5
            java.lang.Class r5 = com.google.android.gms.internal.gtm.zzpp.zznb()
            zzavt = r5
            java.lang.Class r5 = java.lang.Long.TYPE
            boolean r5 = zzn(r5)
            zzbet = r5
            java.lang.Class r5 = java.lang.Integer.TYPE
            boolean r5 = zzn(r5)
            zzbeu = r5
            sun.misc.Unsafe r5 = zzbcx
            r6 = 0
            if (r5 != 0) goto L_0x0038
            goto L_0x005d
        L_0x0038:
            boolean r5 = com.google.android.gms.internal.gtm.zzpp.zzna()
            if (r5 == 0) goto L_0x0056
            boolean r5 = zzbet
            if (r5 == 0) goto L_0x004a
            com.google.android.gms.internal.gtm.zztx$zzb r6 = new com.google.android.gms.internal.gtm.zztx$zzb
            sun.misc.Unsafe r5 = zzbcx
            r6.<init>(r5)
            goto L_0x005d
        L_0x004a:
            boolean r5 = zzbeu
            if (r5 == 0) goto L_0x005d
            com.google.android.gms.internal.gtm.zztx$zza r6 = new com.google.android.gms.internal.gtm.zztx$zza
            sun.misc.Unsafe r5 = zzbcx
            r6.<init>(r5)
            goto L_0x005d
        L_0x0056:
            com.google.android.gms.internal.gtm.zztx$zzc r6 = new com.google.android.gms.internal.gtm.zztx$zzc
            sun.misc.Unsafe r5 = zzbcx
            r6.<init>(r5)
        L_0x005d:
            zzbev = r6
            boolean r5 = zzrq()
            zzbew = r5
            boolean r5 = zzrp()
            zzawt = r5
            java.lang.Class<byte[]> r5 = byte[].class
            int r5 = zzl(r5)
            long r5 = (long) r5
            zzbex = r5
            int r5 = zzl(r4)
            long r5 = (long) r5
            zzbey = r5
            int r4 = zzm(r4)
            long r4 = (long) r4
            zzbez = r4
            int r4 = zzl(r3)
            long r4 = (long) r4
            zzbfa = r4
            int r3 = zzm(r3)
            long r3 = (long) r3
            zzbfb = r3
            int r3 = zzl(r2)
            long r3 = (long) r3
            zzbfc = r3
            int r2 = zzm(r2)
            long r2 = (long) r2
            zzbfd = r2
            int r2 = zzl(r1)
            long r2 = (long) r2
            zzbfe = r2
            int r1 = zzm(r1)
            long r1 = (long) r1
            zzbff = r1
            int r1 = zzl(r0)
            long r1 = (long) r1
            zzbfg = r1
            int r0 = zzm(r0)
            long r0 = (long) r0
            zzbfh = r0
            java.lang.Class<java.lang.Object[]> r0 = java.lang.Object[].class
            int r0 = zzl(r0)
            long r0 = (long) r0
            zzbfi = r0
            java.lang.Class<java.lang.Object[]> r0 = java.lang.Object[].class
            int r0 = zzm(r0)
            long r0 = (long) r0
            zzbfj = r0
            java.lang.reflect.Field r0 = zzrr()
            if (r0 == 0) goto L_0x00de
            com.google.android.gms.internal.gtm.zztx$zzd r1 = zzbev
            if (r1 != 0) goto L_0x00d7
            goto L_0x00de
        L_0x00d7:
            sun.misc.Unsafe r1 = r1.zzbfn
            long r0 = r1.objectFieldOffset(r0)
            goto L_0x00e0
        L_0x00de:
            r0 = -1
        L_0x00e0:
            zzbfk = r0
            long r0 = zzbex
            r2 = 7
            long r0 = r0 & r2
            int r1 = (int) r0
            zzbfl = r1
            java.nio.ByteOrder r0 = java.nio.ByteOrder.nativeOrder()
            java.nio.ByteOrder r1 = java.nio.ByteOrder.BIG_ENDIAN
            if (r0 != r1) goto L_0x00f4
            r0 = 1
            goto L_0x00f5
        L_0x00f4:
            r0 = 0
        L_0x00f5:
            zzbfm = r0
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.gtm.zztx.<clinit>():void");
    }
}
