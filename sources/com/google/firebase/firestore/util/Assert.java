package com.google.firebase.firestore.util;

/* compiled from: com.google.firebase:firebase-firestore@@19.0.0 */
public class Assert {
    public static void hardAssert(boolean z, String str, Object... objArr) {
        if (!z) {
            throw fail(str, objArr);
        }
    }

    public static AssertionError fail(String str, Object... objArr) {
        throw new AssertionError(format(str, objArr));
    }

    public static AssertionError fail(Throwable th, String str, Object... objArr) {
        throw ApiUtil.newAssertionError(format(str, objArr), th);
    }

    private static String format(String str, Object... objArr) {
        StringBuilder sb = new StringBuilder();
        sb.append("INTERNAL ASSERTION FAILED: ");
        sb.append(String.format(str, objArr));
        return sb.toString();
    }
}
