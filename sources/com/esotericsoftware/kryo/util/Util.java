package com.esotericsoftware.kryo.util;

import com.esotericsoftware.minlog.Log;
import java.util.concurrent.ConcurrentHashMap;
import org.objenesis.strategy.PlatformDescription;

public class Util {
    public static final boolean IS_ANDROID = PlatformDescription.DALVIK.equals(System.getProperty("java.vm.name"));
    private static final ConcurrentHashMap<String, Boolean> classAvailabilities = new ConcurrentHashMap<>();
    @Deprecated
    public static boolean isAndroid = IS_ANDROID;

    public static int swapInt(int i) {
        return ((i >> 24) & 255) | ((i & 255) << 24) | ((65280 & i) << 8) | ((16711680 & i) >> 8);
    }

    public static long swapLong(long j) {
        return (((j >> 56) & 255) << 0) | (((j >> 0) & 255) << 56) | (((j >> 8) & 255) << 48) | (((j >> 16) & 255) << 40) | (((j >> 24) & 255) << 32) | (((j >> 32) & 255) << 24) | (((j >> 40) & 255) << 16) | (((j >> 48) & 255) << 8);
    }

    public static boolean isClassAvailable(String str) {
        Boolean bool = (Boolean) classAvailabilities.get(str);
        if (bool == null) {
            try {
                Class.forName(str);
                bool = Boolean.valueOf(true);
            } catch (Exception unused) {
                StringBuilder sb = new StringBuilder();
                sb.append("Class not available: ");
                sb.append(str);
                Log.debug("kryo", sb.toString());
                bool = Boolean.valueOf(false);
            }
            classAvailabilities.put(str, bool);
        }
        return bool.booleanValue();
    }

    public static Class getWrapperClass(Class cls) {
        if (cls == Integer.TYPE) {
            return Integer.class;
        }
        if (cls == Float.TYPE) {
            return Float.class;
        }
        if (cls == Boolean.TYPE) {
            return Boolean.class;
        }
        if (cls == Long.TYPE) {
            return Long.class;
        }
        if (cls == Byte.TYPE) {
            return Byte.class;
        }
        if (cls == Character.TYPE) {
            return Character.class;
        }
        if (cls == Short.TYPE) {
            return Short.class;
        }
        if (cls == Double.TYPE) {
            return Double.class;
        }
        return Void.class;
    }

    public static Class getPrimitiveClass(Class cls) {
        if (cls == Integer.class) {
            return Integer.TYPE;
        }
        if (cls == Float.class) {
            return Float.TYPE;
        }
        if (cls == Boolean.class) {
            return Boolean.TYPE;
        }
        if (cls == Long.class) {
            return Long.TYPE;
        }
        if (cls == Byte.class) {
            return Byte.TYPE;
        }
        if (cls == Character.class) {
            return Character.TYPE;
        }
        if (cls == Short.class) {
            return Short.TYPE;
        }
        if (cls == Double.class) {
            return Double.TYPE;
        }
        if (cls == Void.class) {
            cls = Void.TYPE;
        }
        return cls;
    }

    public static boolean isWrapperClass(Class cls) {
        return cls == Integer.class || cls == Float.class || cls == Boolean.class || cls == Long.class || cls == Byte.class || cls == Character.class || cls == Short.class || cls == Double.class;
    }

    public static void log(String str, Object obj) {
        String str2 = "kryo";
        if (obj == null) {
            if (Log.TRACE) {
                StringBuilder sb = new StringBuilder();
                sb.append(str);
                sb.append(": null");
                Log.trace(str2, sb.toString());
            }
            return;
        }
        Class<String> cls = obj.getClass();
        String str3 = ": ";
        if (!cls.isPrimitive() && cls != Boolean.class && cls != Byte.class && cls != Character.class && cls != Short.class && cls != Integer.class && cls != Long.class && cls != Float.class && cls != Double.class && cls != String.class) {
            StringBuilder sb2 = new StringBuilder();
            sb2.append(str);
            sb2.append(str3);
            sb2.append(string(obj));
            Log.debug(str2, sb2.toString());
        } else if (Log.TRACE) {
            StringBuilder sb3 = new StringBuilder();
            sb3.append(str);
            sb3.append(str3);
            sb3.append(string(obj));
            Log.trace(str2, sb3.toString());
        }
    }

    /* JADX WARNING: Can't wrap try/catch for region: R(7:7|8|9|(3:11|(1:13)(1:14)|15)|16|17|18) */
    /* JADX WARNING: Code restructure failed: missing block: B:19:0x0039, code lost:
        r3 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:20:0x003a, code lost:
        r1 = new java.lang.StringBuilder();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:21:0x0041, code lost:
        if (com.esotericsoftware.minlog.Log.TRACE != false) goto L_0x0043;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:22:0x0043, code lost:
        r0 = className(r0);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:23:0x0048, code lost:
        r0 = r0.getSimpleName();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:24:0x004c, code lost:
        r1.append(r0);
        r1.append("(Exception ");
        r1.append(r3);
        r1.append(" in toString)");
     */
    /* JADX WARNING: Code restructure failed: missing block: B:25:0x0060, code lost:
        return r1.toString();
     */
    /* JADX WARNING: Failed to process nested try/catch */
    /* JADX WARNING: Missing exception handler attribute for start block: B:16:0x0034 */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static java.lang.String string(java.lang.Object r3) {
        /*
            if (r3 != 0) goto L_0x0005
            java.lang.String r3 = "null"
            return r3
        L_0x0005:
            java.lang.Class r0 = r3.getClass()
            boolean r1 = r0.isArray()
            if (r1 == 0) goto L_0x0014
            java.lang.String r3 = className(r0)
            return r3
        L_0x0014:
            java.lang.String r1 = "toString"
            r2 = 0
            java.lang.Class[] r2 = new java.lang.Class[r2]     // Catch:{ Exception -> 0x0034 }
            java.lang.reflect.Method r1 = r0.getMethod(r1, r2)     // Catch:{ Exception -> 0x0034 }
            java.lang.Class r1 = r1.getDeclaringClass()     // Catch:{ Exception -> 0x0034 }
            java.lang.Class<java.lang.Object> r2 = java.lang.Object.class
            if (r1 != r2) goto L_0x0034
            boolean r1 = com.esotericsoftware.minlog.Log.TRACE     // Catch:{ Exception -> 0x0034 }
            if (r1 == 0) goto L_0x002f
            java.lang.String r3 = className(r0)     // Catch:{ Exception -> 0x0034 }
            goto L_0x0033
        L_0x002f:
            java.lang.String r3 = r0.getSimpleName()     // Catch:{ Exception -> 0x0034 }
        L_0x0033:
            return r3
        L_0x0034:
            java.lang.String r3 = java.lang.String.valueOf(r3)     // Catch:{ all -> 0x0039 }
            return r3
        L_0x0039:
            r3 = move-exception
            java.lang.StringBuilder r1 = new java.lang.StringBuilder
            r1.<init>()
            boolean r2 = com.esotericsoftware.minlog.Log.TRACE
            if (r2 == 0) goto L_0x0048
            java.lang.String r0 = className(r0)
            goto L_0x004c
        L_0x0048:
            java.lang.String r0 = r0.getSimpleName()
        L_0x004c:
            r1.append(r0)
            java.lang.String r0 = "(Exception "
            r1.append(r0)
            r1.append(r3)
            java.lang.String r3 = " in toString)"
            r1.append(r3)
            java.lang.String r3 = r1.toString()
            return r3
        */
        throw new UnsupportedOperationException("Method not decompiled: com.esotericsoftware.kryo.util.Util.string(java.lang.Object):java.lang.String");
    }

    public static String className(Class cls) {
        if (cls.isArray()) {
            Class elementClass = getElementClass(cls);
            StringBuilder sb = new StringBuilder(16);
            int dimensionCount = getDimensionCount(cls);
            for (int i = 0; i < dimensionCount; i++) {
                sb.append("[]");
            }
            StringBuilder sb2 = new StringBuilder();
            sb2.append(className(elementClass));
            sb2.append(sb);
            return sb2.toString();
        } else if (cls.isPrimitive() || cls == Object.class || cls == Boolean.class || cls == Byte.class || cls == Character.class || cls == Short.class || cls == Integer.class || cls == Long.class || cls == Float.class || cls == Double.class || cls == String.class) {
            return cls.getSimpleName();
        } else {
            return cls.getName();
        }
    }

    public static int getDimensionCount(Class cls) {
        int i = 0;
        for (Class componentType = cls.getComponentType(); componentType != null; componentType = componentType.getComponentType()) {
            i++;
        }
        return i;
    }

    public static Class getElementClass(Class cls) {
        while (cls.getComponentType() != null) {
            cls = cls.getComponentType();
        }
        return cls;
    }
}
