package com.google.android.gms.internal.firebase_auth;

import java.lang.reflect.Field;
import java.nio.Buffer;
import java.nio.ByteOrder;
import java.security.AccessController;
import java.util.logging.Level;
import java.util.logging.Logger;
import sun.misc.Unsafe;

final class zzkq {
    private static final Logger logger = Logger.getLogger(zzkq.class.getName());
    private static final Unsafe zzacw = zzkt();
    private static final boolean zzaer = zzk(Long.TYPE);
    private static final boolean zzaes = zzk(Integer.TYPE);
    private static final zzd zzaet;
    private static final boolean zzaeu = zzkv();
    private static final long zzaev = ((long) zzi(byte[].class));
    private static final long zzaew;
    private static final long zzaex;
    private static final long zzaey;
    private static final long zzaez;
    private static final long zzafa;
    private static final long zzafb;
    private static final long zzafc;
    private static final long zzafd;
    private static final long zzafe;
    private static final long zzaff;
    private static final long zzafg = ((long) zzi(Object[].class));
    private static final long zzafh = ((long) zzj(Object[].class));
    private static final long zzafi;
    private static final int zzafj = ((int) (zzaev & 7));
    static final boolean zzafk = (ByteOrder.nativeOrder() != ByteOrder.BIG_ENDIAN);
    private static final Class<?> zzvt = zzge.zzgb();
    private static final boolean zzww = zzku();

    static final class zza extends zzd {
        zza(Unsafe unsafe) {
            super(unsafe);
        }

        public final byte zzy(Object obj, long j) {
            if (zzkq.zzafk) {
                return zzkq.zzq(obj, j);
            }
            return zzkq.zzr(obj, j);
        }

        public final void zze(Object obj, long j, byte b) {
            if (zzkq.zzafk) {
                zzkq.zza(obj, j, b);
            } else {
                zzkq.zzb(obj, j, b);
            }
        }

        public final boolean zzm(Object obj, long j) {
            if (zzkq.zzafk) {
                return zzkq.zzs(obj, j);
            }
            return zzkq.zzt(obj, j);
        }

        public final void zza(Object obj, long j, boolean z) {
            if (zzkq.zzafk) {
                zzkq.zzb(obj, j, z);
            } else {
                zzkq.zzc(obj, j, z);
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
    }

    static final class zzb extends zzd {
        zzb(Unsafe unsafe) {
            super(unsafe);
        }

        public final byte zzy(Object obj, long j) {
            return this.zzafn.getByte(obj, j);
        }

        public final void zze(Object obj, long j, byte b) {
            this.zzafn.putByte(obj, j, b);
        }

        public final boolean zzm(Object obj, long j) {
            return this.zzafn.getBoolean(obj, j);
        }

        public final void zza(Object obj, long j, boolean z) {
            this.zzafn.putBoolean(obj, j, z);
        }

        public final float zzn(Object obj, long j) {
            return this.zzafn.getFloat(obj, j);
        }

        public final void zza(Object obj, long j, float f) {
            this.zzafn.putFloat(obj, j, f);
        }

        public final double zzo(Object obj, long j) {
            return this.zzafn.getDouble(obj, j);
        }

        public final void zza(Object obj, long j, double d) {
            this.zzafn.putDouble(obj, j, d);
        }
    }

    static final class zzc extends zzd {
        zzc(Unsafe unsafe) {
            super(unsafe);
        }

        public final byte zzy(Object obj, long j) {
            if (zzkq.zzafk) {
                return zzkq.zzq(obj, j);
            }
            return zzkq.zzr(obj, j);
        }

        public final void zze(Object obj, long j, byte b) {
            if (zzkq.zzafk) {
                zzkq.zza(obj, j, b);
            } else {
                zzkq.zzb(obj, j, b);
            }
        }

        public final boolean zzm(Object obj, long j) {
            if (zzkq.zzafk) {
                return zzkq.zzs(obj, j);
            }
            return zzkq.zzt(obj, j);
        }

        public final void zza(Object obj, long j, boolean z) {
            if (zzkq.zzafk) {
                zzkq.zzb(obj, j, z);
            } else {
                zzkq.zzc(obj, j, z);
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
    }

    static abstract class zzd {
        Unsafe zzafn;

        zzd(Unsafe unsafe) {
            this.zzafn = unsafe;
        }

        public abstract void zza(Object obj, long j, double d);

        public abstract void zza(Object obj, long j, float f);

        public abstract void zza(Object obj, long j, boolean z);

        public abstract void zze(Object obj, long j, byte b);

        public abstract boolean zzm(Object obj, long j);

        public abstract float zzn(Object obj, long j);

        public abstract double zzo(Object obj, long j);

        public abstract byte zzy(Object obj, long j);

        public final int zzk(Object obj, long j) {
            return this.zzafn.getInt(obj, j);
        }

        public final void zzb(Object obj, long j, int i) {
            this.zzafn.putInt(obj, j, i);
        }

        public final long zzl(Object obj, long j) {
            return this.zzafn.getLong(obj, j);
        }

        public final void zza(Object obj, long j, long j2) {
            this.zzafn.putLong(obj, j, j2);
        }
    }

    private zzkq() {
    }

    static boolean zzkr() {
        return zzww;
    }

    static boolean zzks() {
        return zzaeu;
    }

    static <T> T zzh(Class<T> cls) {
        try {
            return zzacw.allocateInstance(cls);
        } catch (InstantiationException e) {
            throw new IllegalStateException(e);
        }
    }

    private static int zzi(Class<?> cls) {
        if (zzww) {
            return zzaet.zzafn.arrayBaseOffset(cls);
        }
        return -1;
    }

    private static int zzj(Class<?> cls) {
        if (zzww) {
            return zzaet.zzafn.arrayIndexScale(cls);
        }
        return -1;
    }

    static int zzk(Object obj, long j) {
        return zzaet.zzk(obj, j);
    }

    static void zzb(Object obj, long j, int i) {
        zzaet.zzb(obj, j, i);
    }

    static long zzl(Object obj, long j) {
        return zzaet.zzl(obj, j);
    }

    static void zza(Object obj, long j, long j2) {
        zzaet.zza(obj, j, j2);
    }

    static boolean zzm(Object obj, long j) {
        return zzaet.zzm(obj, j);
    }

    static void zza(Object obj, long j, boolean z) {
        zzaet.zza(obj, j, z);
    }

    static float zzn(Object obj, long j) {
        return zzaet.zzn(obj, j);
    }

    static void zza(Object obj, long j, float f) {
        zzaet.zza(obj, j, f);
    }

    static double zzo(Object obj, long j) {
        return zzaet.zzo(obj, j);
    }

    static void zza(Object obj, long j, double d) {
        zzaet.zza(obj, j, d);
    }

    static Object zzp(Object obj, long j) {
        return zzaet.zzafn.getObject(obj, j);
    }

    static void zza(Object obj, long j, Object obj2) {
        zzaet.zzafn.putObject(obj, j, obj2);
    }

    static byte zza(byte[] bArr, long j) {
        return zzaet.zzy(bArr, zzaev + j);
    }

    static void zza(byte[] bArr, long j, byte b) {
        zzaet.zze(bArr, zzaev + j, b);
    }

    static Unsafe zzkt() {
        try {
            return (Unsafe) AccessController.doPrivileged(new zzks());
        } catch (Throwable unused) {
            return null;
        }
    }

    private static boolean zzku() {
        Unsafe unsafe = zzacw;
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
            if (zzge.zzga()) {
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

    private static boolean zzkv() {
        String str = "copyMemory";
        String str2 = "getLong";
        Unsafe unsafe = zzacw;
        if (unsafe == null) {
            return false;
        }
        try {
            Class cls = unsafe.getClass();
            cls.getMethod("objectFieldOffset", new Class[]{Field.class});
            cls.getMethod(str2, new Class[]{Object.class, Long.TYPE});
            if (zzkw() == null) {
                return false;
            }
            if (zzge.zzga()) {
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

    private static boolean zzk(Class<?> cls) {
        Class<byte[]> cls2 = byte[].class;
        if (!zzge.zzga()) {
            return false;
        }
        try {
            Class<?> cls3 = zzvt;
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

    private static Field zzkw() {
        if (zzge.zzga()) {
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
            java.lang.Class<com.google.android.gms.internal.firebase_auth.zzkq> r5 = com.google.android.gms.internal.firebase_auth.zzkq.class
            java.lang.String r5 = r5.getName()
            java.util.logging.Logger r5 = java.util.logging.Logger.getLogger(r5)
            logger = r5
            sun.misc.Unsafe r5 = zzkt()
            zzacw = r5
            java.lang.Class r5 = com.google.android.gms.internal.firebase_auth.zzge.zzgb()
            zzvt = r5
            java.lang.Class r5 = java.lang.Long.TYPE
            boolean r5 = zzk(r5)
            zzaer = r5
            java.lang.Class r5 = java.lang.Integer.TYPE
            boolean r5 = zzk(r5)
            zzaes = r5
            sun.misc.Unsafe r5 = zzacw
            r6 = 0
            if (r5 != 0) goto L_0x0038
            goto L_0x005d
        L_0x0038:
            boolean r5 = com.google.android.gms.internal.firebase_auth.zzge.zzga()
            if (r5 == 0) goto L_0x0056
            boolean r5 = zzaer
            if (r5 == 0) goto L_0x004a
            com.google.android.gms.internal.firebase_auth.zzkq$zzc r6 = new com.google.android.gms.internal.firebase_auth.zzkq$zzc
            sun.misc.Unsafe r5 = zzacw
            r6.<init>(r5)
            goto L_0x005d
        L_0x004a:
            boolean r5 = zzaes
            if (r5 == 0) goto L_0x005d
            com.google.android.gms.internal.firebase_auth.zzkq$zza r6 = new com.google.android.gms.internal.firebase_auth.zzkq$zza
            sun.misc.Unsafe r5 = zzacw
            r6.<init>(r5)
            goto L_0x005d
        L_0x0056:
            com.google.android.gms.internal.firebase_auth.zzkq$zzb r6 = new com.google.android.gms.internal.firebase_auth.zzkq$zzb
            sun.misc.Unsafe r5 = zzacw
            r6.<init>(r5)
        L_0x005d:
            zzaet = r6
            boolean r5 = zzkv()
            zzaeu = r5
            boolean r5 = zzku()
            zzww = r5
            java.lang.Class<byte[]> r5 = byte[].class
            int r5 = zzi(r5)
            long r5 = (long) r5
            zzaev = r5
            int r5 = zzi(r4)
            long r5 = (long) r5
            zzaew = r5
            int r4 = zzj(r4)
            long r4 = (long) r4
            zzaex = r4
            int r4 = zzi(r3)
            long r4 = (long) r4
            zzaey = r4
            int r3 = zzj(r3)
            long r3 = (long) r3
            zzaez = r3
            int r3 = zzi(r2)
            long r3 = (long) r3
            zzafa = r3
            int r2 = zzj(r2)
            long r2 = (long) r2
            zzafb = r2
            int r2 = zzi(r1)
            long r2 = (long) r2
            zzafc = r2
            int r1 = zzj(r1)
            long r1 = (long) r1
            zzafd = r1
            int r1 = zzi(r0)
            long r1 = (long) r1
            zzafe = r1
            int r0 = zzj(r0)
            long r0 = (long) r0
            zzaff = r0
            java.lang.Class<java.lang.Object[]> r0 = java.lang.Object[].class
            int r0 = zzi(r0)
            long r0 = (long) r0
            zzafg = r0
            java.lang.Class<java.lang.Object[]> r0 = java.lang.Object[].class
            int r0 = zzj(r0)
            long r0 = (long) r0
            zzafh = r0
            java.lang.reflect.Field r0 = zzkw()
            if (r0 == 0) goto L_0x00de
            com.google.android.gms.internal.firebase_auth.zzkq$zzd r1 = zzaet
            if (r1 != 0) goto L_0x00d7
            goto L_0x00de
        L_0x00d7:
            sun.misc.Unsafe r1 = r1.zzafn
            long r0 = r1.objectFieldOffset(r0)
            goto L_0x00e0
        L_0x00de:
            r0 = -1
        L_0x00e0:
            zzafi = r0
            long r0 = zzaev
            r2 = 7
            long r0 = r0 & r2
            int r1 = (int) r0
            zzafj = r1
            java.nio.ByteOrder r0 = java.nio.ByteOrder.nativeOrder()
            java.nio.ByteOrder r1 = java.nio.ByteOrder.BIG_ENDIAN
            if (r0 != r1) goto L_0x00f4
            r0 = 1
            goto L_0x00f5
        L_0x00f4:
            r0 = 0
        L_0x00f5:
            zzafk = r0
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.firebase_auth.zzkq.<clinit>():void");
    }
}
