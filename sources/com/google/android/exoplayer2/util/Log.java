package com.google.android.exoplayer2.util;

import android.text.TextUtils;

public final class Log {
    public static final int LOG_LEVEL_ALL = 0;
    public static final int LOG_LEVEL_ERROR = 3;
    public static final int LOG_LEVEL_INFO = 1;
    public static final int LOG_LEVEL_OFF = Integer.MAX_VALUE;
    public static final int LOG_LEVEL_WARNING = 2;
    private static int logLevel = 0;
    private static boolean logStackTraces = true;

    private Log() {
    }

    public static int getLogLevel() {
        return logLevel;
    }

    public boolean getLogStackTraces() {
        return logStackTraces;
    }

    public static void setLogLevel(int i) {
        logLevel = i;
    }

    public static void setLogStackTraces(boolean z) {
        logStackTraces = z;
    }

    /* renamed from: d */
    public static void m1390d(String str, String str2) {
        if (logLevel == 0) {
            android.util.Log.d(str, str2);
        }
    }

    /* renamed from: d */
    public static void m1391d(String str, String str2, Throwable th) {
        if (!logStackTraces) {
            m1390d(str, appendThrowableMessage(str2, th));
        } else if (logLevel == 0) {
            android.util.Log.d(str, str2, th);
        }
    }

    /* renamed from: i */
    public static void m1394i(String str, String str2) {
        if (logLevel <= 1) {
            android.util.Log.i(str, str2);
        }
    }

    /* renamed from: i */
    public static void m1395i(String str, String str2, Throwable th) {
        if (!logStackTraces) {
            m1394i(str, appendThrowableMessage(str2, th));
        } else if (logLevel <= 1) {
            android.util.Log.i(str, str2, th);
        }
    }

    /* renamed from: w */
    public static void m1396w(String str, String str2) {
        if (logLevel <= 2) {
            android.util.Log.w(str, str2);
        }
    }

    /* renamed from: w */
    public static void m1397w(String str, String str2, Throwable th) {
        if (!logStackTraces) {
            m1396w(str, appendThrowableMessage(str2, th));
        } else if (logLevel <= 2) {
            android.util.Log.w(str, str2, th);
        }
    }

    /* renamed from: e */
    public static void m1392e(String str, String str2) {
        if (logLevel <= 3) {
            android.util.Log.e(str, str2);
        }
    }

    /* renamed from: e */
    public static void m1393e(String str, String str2, Throwable th) {
        if (!logStackTraces) {
            m1392e(str, appendThrowableMessage(str2, th));
        } else if (logLevel <= 3) {
            android.util.Log.e(str, str2, th);
        }
    }

    private static String appendThrowableMessage(String str, Throwable th) {
        if (th == null) {
            return str;
        }
        String message = th.getMessage();
        if (!TextUtils.isEmpty(message)) {
            StringBuilder sb = new StringBuilder();
            sb.append(str);
            sb.append(" - ");
            sb.append(message);
            str = sb.toString();
        }
        return str;
    }
}
