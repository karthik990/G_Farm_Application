package androidx.core.p012os;

import android.os.Build.VERSION;
import android.os.Trace;
import android.util.Log;
import java.lang.reflect.Method;

/* renamed from: androidx.core.os.TraceCompat */
public final class TraceCompat {
    private static final String TAG = "TraceCompat";
    private static Method sAsyncTraceBeginMethod;
    private static Method sAsyncTraceEndMethod;
    private static Method sIsTagEnabledMethod;
    private static Method sTraceCounterMethod;
    private static long sTraceTagApp;

    static {
        if (VERSION.SDK_INT >= 18 && VERSION.SDK_INT < 29) {
            try {
                sTraceTagApp = Trace.class.getField("TRACE_TAG_APP").getLong(null);
                sIsTagEnabledMethod = Trace.class.getMethod("isTagEnabled", new Class[]{Long.TYPE});
                sAsyncTraceBeginMethod = Trace.class.getMethod("asyncTraceBegin", new Class[]{Long.TYPE, String.class, Integer.TYPE});
                sAsyncTraceEndMethod = Trace.class.getMethod("asyncTraceEnd", new Class[]{Long.TYPE, String.class, Integer.TYPE});
                sTraceCounterMethod = Trace.class.getMethod("traceCounter", new Class[]{Long.TYPE, String.class, Integer.TYPE});
            } catch (Exception e) {
                Log.i(TAG, "Unable to initialize via reflection.", e);
            }
        }
    }

    public static boolean isEnabled() {
        if (VERSION.SDK_INT >= 29) {
            return Trace.isEnabled();
        }
        if (VERSION.SDK_INT >= 18) {
            try {
                return ((Boolean) sIsTagEnabledMethod.invoke(null, new Object[]{Long.valueOf(sTraceTagApp)})).booleanValue();
            } catch (Exception unused) {
                Log.v(TAG, "Unable to invoke isTagEnabled() via reflection.");
            }
        }
        return false;
    }

    public static void beginSection(String str) {
        if (VERSION.SDK_INT >= 18) {
            Trace.beginSection(str);
        }
    }

    public static void endSection() {
        if (VERSION.SDK_INT >= 18) {
            Trace.endSection();
        }
    }

    public static void beginAsyncSection(String str, int i) {
        if (VERSION.SDK_INT >= 29) {
            Trace.beginAsyncSection(str, i);
        } else if (VERSION.SDK_INT >= 18) {
            try {
                sAsyncTraceBeginMethod.invoke(null, new Object[]{Long.valueOf(sTraceTagApp), str, Integer.valueOf(i)});
            } catch (Exception unused) {
                Log.v(TAG, "Unable to invoke asyncTraceBegin() via reflection.");
            }
        }
    }

    public static void endAsyncSection(String str, int i) {
        if (VERSION.SDK_INT >= 29) {
            Trace.endAsyncSection(str, i);
        } else if (VERSION.SDK_INT >= 18) {
            try {
                sAsyncTraceEndMethod.invoke(null, new Object[]{Long.valueOf(sTraceTagApp), str, Integer.valueOf(i)});
            } catch (Exception unused) {
                Log.v(TAG, "Unable to invoke endAsyncSection() via reflection.");
            }
        }
    }

    public static void setCounter(String str, int i) {
        if (VERSION.SDK_INT >= 29) {
            Trace.setCounter(str, (long) i);
        } else if (VERSION.SDK_INT >= 18) {
            try {
                sTraceCounterMethod.invoke(null, new Object[]{Long.valueOf(sTraceTagApp), str, Integer.valueOf(i)});
            } catch (Exception unused) {
                Log.v(TAG, "Unable to invoke traceCounter() via reflection.");
            }
        }
    }

    private TraceCompat() {
    }
}
