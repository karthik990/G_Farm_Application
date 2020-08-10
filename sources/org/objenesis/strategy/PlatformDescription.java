package org.objenesis.strategy;

import org.objenesis.ObjenesisException;

public final class PlatformDescription {
    public static final int ANDROID_VERSION = getAndroidVersion();
    public static final String DALVIK = "Dalvik";
    public static final String GAE_VERSION = getGaeRuntimeVersion();
    public static final String GNU = "GNU libgcj";
    public static final String HOTSPOT = "Java HotSpot";
    public static final boolean IS_ANDROID_OPENJDK = getIsAndroidOpenJDK();
    public static final String JROCKIT = "BEA";
    public static final String JVM_NAME = System.getProperty("java.vm.name");
    public static final String OPENJDK = "OpenJDK";
    public static final String PERC = "PERC";
    public static final String SPECIFICATION_VERSION = System.getProperty("java.specification.version");
    @Deprecated
    public static final String SUN = "Java HotSpot";
    public static final String VENDOR = System.getProperty("java.vm.vendor");
    public static final String VENDOR_VERSION = System.getProperty("java.vm.version");
    public static final String VM_INFO = System.getProperty("java.vm.info");
    public static final String VM_VERSION = System.getProperty("java.runtime.version");

    public static String describePlatform() {
        StringBuilder sb = new StringBuilder();
        sb.append("Java ");
        sb.append(SPECIFICATION_VERSION);
        sb.append(" (VM vendor name=\"");
        sb.append(VENDOR);
        sb.append("\", VM vendor version=");
        sb.append(VENDOR_VERSION);
        sb.append(", JVM name=\"");
        sb.append(JVM_NAME);
        sb.append("\", JVM version=");
        sb.append(VM_VERSION);
        sb.append(", JVM info=");
        sb.append(VM_INFO);
        String sb2 = sb.toString();
        if (ANDROID_VERSION != 0) {
            StringBuilder sb3 = new StringBuilder();
            sb3.append(sb2);
            sb3.append(", API level=");
            sb3.append(ANDROID_VERSION);
            sb2 = sb3.toString();
        }
        StringBuilder sb4 = new StringBuilder();
        sb4.append(sb2);
        sb4.append(")");
        return sb4.toString();
    }

    public static boolean isThisJVM(String str) {
        return JVM_NAME.startsWith(str);
    }

    public static boolean isAndroidOpenJDK() {
        return IS_ANDROID_OPENJDK;
    }

    private static boolean getIsAndroidOpenJDK() {
        boolean z = false;
        if (getAndroidVersion() == 0) {
            return false;
        }
        String property = System.getProperty("java.boot.class.path");
        if (property != null && property.toLowerCase().contains("core-oj.jar")) {
            z = true;
        }
        return z;
    }

    public static boolean isGoogleAppEngine() {
        return GAE_VERSION != null;
    }

    private static String getGaeRuntimeVersion() {
        return System.getProperty("com.google.appengine.runtime.version");
    }

    private static int getAndroidVersion() {
        if (!isThisJVM(DALVIK)) {
            return 0;
        }
        return getAndroidVersion0();
    }

    private static int getAndroidVersion0() {
        try {
            Class cls = Class.forName("android.os.Build$VERSION");
            try {
                try {
                    return ((Integer) cls.getField("SDK_INT").get(null)).intValue();
                } catch (IllegalAccessException e) {
                    throw new RuntimeException(e);
                }
            } catch (NoSuchFieldException unused) {
                return getOldAndroidVersion(cls);
            }
        } catch (ClassNotFoundException e2) {
            throw new ObjenesisException((Throwable) e2);
        }
    }

    private static int getOldAndroidVersion(Class<?> cls) {
        try {
            try {
                return Integer.parseInt((String) cls.getField("SDK").get(null));
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            }
        } catch (NoSuchFieldException e2) {
            throw new ObjenesisException((Throwable) e2);
        }
    }

    private PlatformDescription() {
    }
}
