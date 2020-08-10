package com.google.android.gms.internal.firebase_remote_config;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

public final class zzea {
    @NullableDecl
    private static final Object zziq;
    @NullableDecl
    private static final Method zzir;
    @NullableDecl
    private static final Method zzis;

    @Deprecated
    public static RuntimeException zza(Throwable th) {
        zzds.checkNotNull(th);
        if (th instanceof RuntimeException) {
            throw ((RuntimeException) th);
        } else if (th instanceof Error) {
            throw ((Error) th);
        } else {
            throw new RuntimeException(th);
        }
    }

    @NullableDecl
    private static Object zzck() {
        try {
            return Class.forName("sun.misc.SharedSecrets", false, null).getMethod("getJavaLangAccess", new Class[0]).invoke(null, new Object[0]);
        } catch (ThreadDeath e) {
            throw e;
        } catch (Throwable unused) {
            return null;
        }
    }

    @NullableDecl
    private static Method zzcl() {
        try {
            Method zza = zza("getStackTraceDepth", Throwable.class);
            if (zza == null) {
                return null;
            }
            zza.invoke(zzck(), new Object[]{new Throwable()});
            return zza;
        } catch (IllegalAccessException | UnsupportedOperationException | InvocationTargetException unused) {
            return null;
        }
    }

    @NullableDecl
    private static Method zza(String str, Class<?>... clsArr) throws ThreadDeath {
        try {
            return Class.forName("sun.misc.JavaLangAccess", false, null).getMethod(str, clsArr);
        } catch (ThreadDeath e) {
            throw e;
        } catch (Throwable unused) {
            return null;
        }
    }

    static {
        Method method;
        Object zzck = zzck();
        zziq = zzck;
        Method method2 = null;
        if (zzck == null) {
            method = null;
        } else {
            method = zza("getStackTraceElement", Throwable.class, Integer.TYPE);
        }
        zzir = method;
        if (zziq != null) {
            method2 = zzcl();
        }
        zzis = method2;
    }
}
