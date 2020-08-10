package org.jdom2.internal;

public final class SystemProperty {
    public static final String get(String str, String str2) {
        try {
            return System.getProperty(str, str2);
        } catch (SecurityException unused) {
            return str2;
        }
    }
}
