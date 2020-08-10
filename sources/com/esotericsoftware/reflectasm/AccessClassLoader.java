package com.esotericsoftware.reflectasm;

import java.lang.ref.WeakReference;
import java.lang.reflect.Method;
import java.util.WeakHashMap;

class AccessClassLoader extends ClassLoader {
    private static final WeakHashMap<ClassLoader, WeakReference<AccessClassLoader>> accessClassLoaders = new WeakHashMap<>();
    private static volatile Method defineClassMethod;
    private static volatile AccessClassLoader selfContextAccessClassLoader = new AccessClassLoader(selfContextParentClassLoader);
    private static final ClassLoader selfContextParentClassLoader = getParentClassLoader(AccessClassLoader.class);

    static AccessClassLoader get(Class cls) {
        ClassLoader parentClassLoader = getParentClassLoader(cls);
        if (selfContextParentClassLoader.equals(parentClassLoader)) {
            if (selfContextAccessClassLoader == null) {
                synchronized (accessClassLoaders) {
                    if (selfContextAccessClassLoader == null) {
                        selfContextAccessClassLoader = new AccessClassLoader(selfContextParentClassLoader);
                    }
                }
            }
            return selfContextAccessClassLoader;
        }
        synchronized (accessClassLoaders) {
            WeakReference weakReference = (WeakReference) accessClassLoaders.get(parentClassLoader);
            if (weakReference != null) {
                AccessClassLoader accessClassLoader = (AccessClassLoader) weakReference.get();
                if (accessClassLoader != null) {
                    return accessClassLoader;
                }
                accessClassLoaders.remove(parentClassLoader);
            }
            AccessClassLoader accessClassLoader2 = new AccessClassLoader(parentClassLoader);
            accessClassLoaders.put(parentClassLoader, new WeakReference(accessClassLoader2));
            return accessClassLoader2;
        }
    }

    public static void remove(ClassLoader classLoader) {
        if (selfContextParentClassLoader.equals(classLoader)) {
            selfContextAccessClassLoader = null;
            return;
        }
        synchronized (accessClassLoaders) {
            accessClassLoaders.remove(classLoader);
        }
    }

    public static int activeAccessClassLoaders() {
        int size = accessClassLoaders.size();
        return selfContextAccessClassLoader != null ? size + 1 : size;
    }

    private AccessClassLoader(ClassLoader classLoader) {
        super(classLoader);
    }

    /* access modifiers changed from: protected */
    public Class<?> loadClass(String str, boolean z) throws ClassNotFoundException {
        if (str.equals(FieldAccess.class.getName())) {
            return FieldAccess.class;
        }
        if (str.equals(MethodAccess.class.getName())) {
            return MethodAccess.class;
        }
        if (str.equals(ConstructorAccess.class.getName())) {
            return ConstructorAccess.class;
        }
        if (str.equals(PublicConstructorAccess.class.getName())) {
            return PublicConstructorAccess.class;
        }
        return super.loadClass(str, z);
    }

    /* access modifiers changed from: 0000 */
    public Class<?> defineClass(String str, byte[] bArr) throws ClassFormatError {
        try {
            return (Class) getDefineClassMethod().invoke(getParent(), new Object[]{str, bArr, Integer.valueOf(0), Integer.valueOf(bArr.length), getClass().getProtectionDomain()});
        } catch (Exception unused) {
            return defineClass(str, bArr, 0, bArr.length, getClass().getProtectionDomain());
        }
    }

    static boolean areInSameRuntimeClassLoader(Class cls, Class cls2) {
        if (cls.getPackage() != cls2.getPackage()) {
            return false;
        }
        ClassLoader classLoader = cls.getClassLoader();
        ClassLoader classLoader2 = cls2.getClassLoader();
        ClassLoader systemClassLoader = ClassLoader.getSystemClassLoader();
        boolean z = true;
        if (classLoader == null) {
            if (!(classLoader2 == null || classLoader2 == systemClassLoader)) {
                z = false;
            }
            return z;
        } else if (classLoader2 == null) {
            if (classLoader != systemClassLoader) {
                z = false;
            }
            return z;
        } else {
            if (classLoader != classLoader2) {
                z = false;
            }
            return z;
        }
    }

    private static ClassLoader getParentClassLoader(Class cls) {
        ClassLoader classLoader = cls.getClassLoader();
        return classLoader == null ? ClassLoader.getSystemClassLoader() : classLoader;
    }

    /* JADX WARNING: Can't wrap try/catch for region: R(7:4|5|6|7|8|9|10) */
    /* JADX WARNING: Missing exception handler attribute for start block: B:9:0x0032 */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private static java.lang.reflect.Method getDefineClassMethod() throws java.lang.Exception {
        /*
            java.lang.reflect.Method r0 = defineClassMethod
            if (r0 != 0) goto L_0x0037
            java.util.WeakHashMap<java.lang.ClassLoader, java.lang.ref.WeakReference<com.esotericsoftware.reflectasm.AccessClassLoader>> r0 = accessClassLoaders
            monitor-enter(r0)
            java.lang.Class<java.lang.ClassLoader> r1 = java.lang.ClassLoader.class
            java.lang.String r2 = "defineClass"
            r3 = 5
            java.lang.Class[] r3 = new java.lang.Class[r3]     // Catch:{ all -> 0x0034 }
            r4 = 0
            java.lang.Class<java.lang.String> r5 = java.lang.String.class
            r3[r4] = r5     // Catch:{ all -> 0x0034 }
            java.lang.Class<byte[]> r4 = byte[].class
            r5 = 1
            r3[r5] = r4     // Catch:{ all -> 0x0034 }
            r4 = 2
            java.lang.Class r6 = java.lang.Integer.TYPE     // Catch:{ all -> 0x0034 }
            r3[r4] = r6     // Catch:{ all -> 0x0034 }
            r4 = 3
            java.lang.Class r6 = java.lang.Integer.TYPE     // Catch:{ all -> 0x0034 }
            r3[r4] = r6     // Catch:{ all -> 0x0034 }
            r4 = 4
            java.lang.Class<java.security.ProtectionDomain> r6 = java.security.ProtectionDomain.class
            r3[r4] = r6     // Catch:{ all -> 0x0034 }
            java.lang.reflect.Method r1 = r1.getDeclaredMethod(r2, r3)     // Catch:{ all -> 0x0034 }
            defineClassMethod = r1     // Catch:{ all -> 0x0034 }
            java.lang.reflect.Method r1 = defineClassMethod     // Catch:{ Exception -> 0x0032 }
            r1.setAccessible(r5)     // Catch:{ Exception -> 0x0032 }
        L_0x0032:
            monitor-exit(r0)     // Catch:{ all -> 0x0034 }
            goto L_0x0037
        L_0x0034:
            r1 = move-exception
            monitor-exit(r0)     // Catch:{ all -> 0x0034 }
            throw r1
        L_0x0037:
            java.lang.reflect.Method r0 = defineClassMethod
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.esotericsoftware.reflectasm.AccessClassLoader.getDefineClassMethod():java.lang.reflect.Method");
    }
}
