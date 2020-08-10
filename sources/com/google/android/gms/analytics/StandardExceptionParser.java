package com.google.android.gms.analytics;

import android.content.Context;
import androidx.core.p012os.EnvironmentCompat;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.TreeSet;

public class StandardExceptionParser implements ExceptionParser {
    private final TreeSet<String> zzta = new TreeSet<>();

    public StandardExceptionParser(Context context, Collection<String> collection) {
        setIncludedPackages(context, collection);
    }

    public void setIncludedPackages(Context context, Collection<String> collection) {
        this.zzta.clear();
        HashSet<String> hashSet = new HashSet<>();
        if (collection != null) {
            hashSet.addAll(collection);
        }
        if (context != null) {
            hashSet.add(context.getApplicationContext().getPackageName());
        }
        for (String str : hashSet) {
            boolean z = true;
            Iterator it = this.zzta.iterator();
            while (true) {
                if (!it.hasNext()) {
                    break;
                }
                String str2 = (String) it.next();
                if (str.startsWith(str2)) {
                    z = false;
                } else if (str2.startsWith(str)) {
                    this.zzta.remove(str2);
                }
            }
            if (z) {
                this.zzta.add(str);
            }
        }
    }

    /* access modifiers changed from: protected */
    public Throwable getCause(Throwable th) {
        while (th.getCause() != null) {
            th = th.getCause();
        }
        return th;
    }

    /* access modifiers changed from: protected */
    public StackTraceElement getBestStackTraceElement(Throwable th) {
        StackTraceElement[] stackTrace = th.getStackTrace();
        if (stackTrace == null || stackTrace.length == 0) {
            return null;
        }
        for (StackTraceElement stackTraceElement : stackTrace) {
            String className = stackTraceElement.getClassName();
            Iterator it = this.zzta.iterator();
            while (it.hasNext()) {
                if (className.startsWith((String) it.next())) {
                    return stackTraceElement;
                }
            }
        }
        return stackTrace[0];
    }

    /* access modifiers changed from: protected */
    public String getDescription(Throwable th, StackTraceElement stackTraceElement, String str) {
        StringBuilder sb = new StringBuilder();
        sb.append(th.getClass().getSimpleName());
        if (stackTraceElement != null) {
            String[] split = stackTraceElement.getClassName().split("\\.");
            sb.append(String.format(" (@%s:%s:%s)", new Object[]{(split == null || split.length <= 0) ? EnvironmentCompat.MEDIA_UNKNOWN : split[split.length - 1], stackTraceElement.getMethodName(), Integer.valueOf(stackTraceElement.getLineNumber())}));
        }
        if (str != null) {
            sb.append(String.format(" {%s}", new Object[]{str}));
        }
        return sb.toString();
    }

    public String getDescription(String str, Throwable th) {
        return getDescription(getCause(th), getBestStackTraceElement(getCause(th)), str);
    }
}
