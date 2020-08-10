package com.google.android.gms.internal.firebase_remote_config;

import java.lang.reflect.Field;
import java.nio.Buffer;
import java.nio.ByteOrder;
import java.security.AccessController;
import java.util.logging.Level;
import java.util.logging.Logger;
import sun.misc.Unsafe;

final class zzkh {
    private static final Logger logger = Logger.getLogger(zzkh.class.getName());
    private static final Class<?> zzoh = zzfr.zzeu();
    private static final boolean zzpp = zzjk();
    private static final Unsafe zzvj = zzjj();
    private static final boolean zzxs = zzp(Long.TYPE);
    private static final boolean zzxt = zzp(Integer.TYPE);
    private static final zzd zzxu;
    private static final boolean zzxv = zzjl();
    private static final long zzxw = ((long) zzn(byte[].class));
    private static final long zzxx;
    private static final long zzxy;
    private static final long zzxz;
    private static final long zzya;
    private static final long zzyb;
    private static final long zzyc;
    private static final long zzyd;
    private static final long zzye;
    private static final long zzyf;
    private static final long zzyg;
    private static final long zzyh = ((long) zzn(Object[].class));
    private static final long zzyi = ((long) zzo(Object[].class));
    private static final long zzyj;
    /* access modifiers changed from: private */
    public static final boolean zzyk = (ByteOrder.nativeOrder() != ByteOrder.BIG_ENDIAN);

    static final class zza extends zzd {
        zza(Unsafe unsafe) {
            super(unsafe);
        }

        public final byte zzy(Object obj, long j) {
            if (zzkh.zzyk) {
                return zzkh.zzq(obj, j);
            }
            return zzkh.zzr(obj, j);
        }

        public final void zze(Object obj, long j, byte b) {
            if (zzkh.zzyk) {
                zzkh.zza(obj, j, b);
            } else {
                zzkh.zzb(obj, j, b);
            }
        }

        public final boolean zzm(Object obj, long j) {
            if (zzkh.zzyk) {
                return zzkh.zzs(obj, j);
            }
            return zzkh.zzt(obj, j);
        }

        public final void zza(Object obj, long j, boolean z) {
            if (zzkh.zzyk) {
                zzkh.zzb(obj, j, z);
            } else {
                zzkh.zzc(obj, j, z);
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
            if (zzkh.zzyk) {
                return zzkh.zzq(obj, j);
            }
            return zzkh.zzr(obj, j);
        }

        public final void zze(Object obj, long j, byte b) {
            if (zzkh.zzyk) {
                zzkh.zza(obj, j, b);
            } else {
                zzkh.zzb(obj, j, b);
            }
        }

        public final boolean zzm(Object obj, long j) {
            if (zzkh.zzyk) {
                return zzkh.zzs(obj, j);
            }
            return zzkh.zzt(obj, j);
        }

        public final void zza(Object obj, long j, boolean z) {
            if (zzkh.zzyk) {
                zzkh.zzb(obj, j, z);
            } else {
                zzkh.zzc(obj, j, z);
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

    static final class zzc extends zzd {
        zzc(Unsafe unsafe) {
            super(unsafe);
        }

        public final byte zzy(Object obj, long j) {
            return this.zzyl.getByte(obj, j);
        }

        public final void zze(Object obj, long j, byte b) {
            this.zzyl.putByte(obj, j, b);
        }

        public final boolean zzm(Object obj, long j) {
            return this.zzyl.getBoolean(obj, j);
        }

        public final void zza(Object obj, long j, boolean z) {
            this.zzyl.putBoolean(obj, j, z);
        }

        public final float zzn(Object obj, long j) {
            return this.zzyl.getFloat(obj, j);
        }

        public final void zza(Object obj, long j, float f) {
            this.zzyl.putFloat(obj, j, f);
        }

        public final double zzo(Object obj, long j) {
            return this.zzyl.getDouble(obj, j);
        }

        public final void zza(Object obj, long j, double d) {
            this.zzyl.putDouble(obj, j, d);
        }
    }

    static abstract class zzd {
        Unsafe zzyl;

        zzd(Unsafe unsafe) {
            this.zzyl = unsafe;
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
            return this.zzyl.getInt(obj, j);
        }

        public final void zzb(Object obj, long j, int i) {
            this.zzyl.putInt(obj, j, i);
        }

        public final long zzl(Object obj, long j) {
            return this.zzyl.getLong(obj, j);
        }

        public final void zza(Object obj, long j, long j2) {
            this.zzyl.putLong(obj, j, j2);
        }
    }

    private zzkh() {
    }

    static boolean zzjh() {
        return zzpp;
    }

    static boolean zzji() {
        return zzxv;
    }

    static <T> T zzm(Class<T> cls) {
        try {
            return zzvj.allocateInstance(cls);
        } catch (InstantiationException e) {
            throw new IllegalStateException(e);
        }
    }

    private static int zzn(Class<?> cls) {
        if (zzpp) {
            return zzxu.zzyl.arrayBaseOffset(cls);
        }
        return -1;
    }

    private static int zzo(Class<?> cls) {
        if (zzpp) {
            return zzxu.zzyl.arrayIndexScale(cls);
        }
        return -1;
    }

    static int zzk(Object obj, long j) {
        return zzxu.zzk(obj, j);
    }

    static void zzb(Object obj, long j, int i) {
        zzxu.zzb(obj, j, i);
    }

    static long zzl(Object obj, long j) {
        return zzxu.zzl(obj, j);
    }

    static void zza(Object obj, long j, long j2) {
        zzxu.zza(obj, j, j2);
    }

    static boolean zzm(Object obj, long j) {
        return zzxu.zzm(obj, j);
    }

    static void zza(Object obj, long j, boolean z) {
        zzxu.zza(obj, j, z);
    }

    static float zzn(Object obj, long j) {
        return zzxu.zzn(obj, j);
    }

    static void zza(Object obj, long j, float f) {
        zzxu.zza(obj, j, f);
    }

    static double zzo(Object obj, long j) {
        return zzxu.zzo(obj, j);
    }

    static void zza(Object obj, long j, double d) {
        zzxu.zza(obj, j, d);
    }

    static Object zzp(Object obj, long j) {
        return zzxu.zzyl.getObject(obj, j);
    }

    static void zza(Object obj, long j, Object obj2) {
        zzxu.zzyl.putObject(obj, j, obj2);
    }

    static byte zza(byte[] bArr, long j) {
        return zzxu.zzy(bArr, zzxw + j);
    }

    static void zza(byte[] bArr, long j, byte b) {
        zzxu.zze(bArr, zzxw + j, b);
    }

    static Unsafe zzjj() {
        try {
            return (Unsafe) AccessController.doPrivileged(new zzki());
        } catch (Throwable unused) {
            return null;
        }
    }

    private static boolean zzjk() {
        Unsafe unsafe = zzvj;
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
            if (zzfr.zzet()) {
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

    private static boolean zzjl() {
        String str = "copyMemory";
        String str2 = "getLong";
        Unsafe unsafe = zzvj;
        if (unsafe == null) {
            return false;
        }
        try {
            Class cls = unsafe.getClass();
            cls.getMethod("objectFieldOffset", new Class[]{Field.class});
            cls.getMethod(str2, new Class[]{Object.class, Long.TYPE});
            if (zzjm() == null) {
                return false;
            }
            if (zzfr.zzet()) {
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

    private static boolean zzp(Class<?> cls) {
        Class<byte[]> cls2 = byte[].class;
        if (!zzfr.zzet()) {
            return false;
        }
        try {
            Class<?> cls3 = zzoh;
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

    private static Field zzjm() {
        if (zzfr.zzet()) {
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

    /* JADX WARNING: Removed duplicated region for block: B:19:0x00ea  */
    /* JADX WARNING: Removed duplicated region for block: B:20:0x00ec  */
    static {
        /*
            java.lang.Class<double[]> r0 = double[].class
            java.lang.Class<float[]> r1 = float[].class
            java.lang.Class<long[]> r2 = long[].class
            java.lang.Class<int[]> r3 = int[].class
            java.lang.Class<boolean[]> r4 = boolean[].class
            java.lang.Class<com.google.android.gms.internal.firebase_remote_config.zzkh> r5 = com.google.android.gms.internal.firebase_remote_config.zzkh.class
            java.lang.String r5 = r5.getName()
            java.util.logging.Logger r5 = java.util.logging.Logger.getLogger(r5)
            logger = r5
            sun.misc.Unsafe r5 = zzjj()
            zzvj = r5
            java.lang.Class r5 = com.google.android.gms.internal.firebase_remote_config.zzfr.zzeu()
            zzoh = r5
            java.lang.Class r5 = java.lang.Long.TYPE
            boolean r5 = zzp(r5)
            zzxs = r5
            java.lang.Class r5 = java.lang.Integer.TYPE
            boolean r5 = zzp(r5)
            zzxt = r5
            sun.misc.Unsafe r5 = zzvj
            r6 = 0
            if (r5 != 0) goto L_0x0038
            goto L_0x005d
        L_0x0038:
            boolean r5 = com.google.android.gms.internal.firebase_remote_config.zzfr.zzet()
            if (r5 == 0) goto L_0x0056
            boolean r5 = zzxs
            if (r5 == 0) goto L_0x004a
            com.google.android.gms.internal.firebase_remote_config.zzkh$zzb r6 = new com.google.android.gms.internal.firebase_remote_config.zzkh$zzb
            sun.misc.Unsafe r5 = zzvj
            r6.<init>(r5)
            goto L_0x005d
        L_0x004a:
            boolean r5 = zzxt
            if (r5 == 0) goto L_0x005d
            com.google.android.gms.internal.firebase_remote_config.zzkh$zza r6 = new com.google.android.gms.internal.firebase_remote_config.zzkh$zza
            sun.misc.Unsafe r5 = zzvj
            r6.<init>(r5)
            goto L_0x005d
        L_0x0056:
            com.google.android.gms.internal.firebase_remote_config.zzkh$zzc r6 = new com.google.android.gms.internal.firebase_remote_config.zzkh$zzc
            sun.misc.Unsafe r5 = zzvj
            r6.<init>(r5)
        L_0x005d:
            zzxu = r6
            boolean r5 = zzjl()
            zzxv = r5
            boolean r5 = zzjk()
            zzpp = r5
            java.lang.Class<byte[]> r5 = byte[].class
            int r5 = zzn(r5)
            long r5 = (long) r5
            zzxw = r5
            int r5 = zzn(r4)
            long r5 = (long) r5
            zzxx = r5
            int r4 = zzo(r4)
            long r4 = (long) r4
            zzxy = r4
            int r4 = zzn(r3)
            long r4 = (long) r4
            zzxz = r4
            int r3 = zzo(r3)
            long r3 = (long) r3
            zzya = r3
            int r3 = zzn(r2)
            long r3 = (long) r3
            zzyb = r3
            int r2 = zzo(r2)
            long r2 = (long) r2
            zzyc = r2
            int r2 = zzn(r1)
            long r2 = (long) r2
            zzyd = r2
            int r1 = zzo(r1)
            long r1 = (long) r1
            zzye = r1
            int r1 = zzn(r0)
            long r1 = (long) r1
            zzyf = r1
            int r0 = zzo(r0)
            long r0 = (long) r0
            zzyg = r0
            java.lang.Class<java.lang.Object[]> r0 = java.lang.Object[].class
            int r0 = zzn(r0)
            long r0 = (long) r0
            zzyh = r0
            java.lang.Class<java.lang.Object[]> r0 = java.lang.Object[].class
            int r0 = zzo(r0)
            long r0 = (long) r0
            zzyi = r0
            java.lang.reflect.Field r0 = zzjm()
            if (r0 == 0) goto L_0x00de
            com.google.android.gms.internal.firebase_remote_config.zzkh$zzd r1 = zzxu
            if (r1 != 0) goto L_0x00d7
            goto L_0x00de
        L_0x00d7:
            sun.misc.Unsafe r1 = r1.zzyl
            long r0 = r1.objectFieldOffset(r0)
            goto L_0x00e0
        L_0x00de:
            r0 = -1
        L_0x00e0:
            zzyj = r0
            java.nio.ByteOrder r0 = java.nio.ByteOrder.nativeOrder()
            java.nio.ByteOrder r1 = java.nio.ByteOrder.BIG_ENDIAN
            if (r0 != r1) goto L_0x00ec
            r0 = 1
            goto L_0x00ed
        L_0x00ec:
            r0 = 0
        L_0x00ed:
            zzyk = r0
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.firebase_remote_config.zzkh.<clinit>():void");
    }
}
