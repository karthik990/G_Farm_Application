package com.mobiroller.util;

public final class FakeCrashLibrary {
    public static void log(int i, String str, String str2) {
    }

    public static void logError(Throwable th) {
    }

    public static void logWarning(Throwable th) {
    }

    private FakeCrashLibrary() {
        throw new AssertionError("No instances.");
    }
}
