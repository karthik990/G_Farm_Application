package com.google.android.gms.analytics;

import android.content.Context;
import com.google.android.gms.analytics.HitBuilders.ExceptionBuilder;
import com.google.android.gms.internal.gtm.zzch;
import java.lang.Thread.UncaughtExceptionHandler;
import java.util.ArrayList;

public class ExceptionReporter implements UncaughtExceptionHandler {
    private final UncaughtExceptionHandler zzrk;
    private final Tracker zzrl;
    private final Context zzrm;
    private ExceptionParser zzrn;
    private GoogleAnalytics zzro;

    public ExceptionReporter(Tracker tracker, UncaughtExceptionHandler uncaughtExceptionHandler, Context context) {
        String str;
        if (tracker == null) {
            throw new NullPointerException("tracker cannot be null");
        } else if (context != null) {
            this.zzrk = uncaughtExceptionHandler;
            this.zzrl = tracker;
            this.zzrn = new StandardExceptionParser(context, new ArrayList());
            this.zzrm = context.getApplicationContext();
            String str2 = "ExceptionReporter created, original handler is ";
            if (uncaughtExceptionHandler == null) {
                str = "null";
            } else {
                str = uncaughtExceptionHandler.getClass().getName();
            }
            String valueOf = String.valueOf(str);
            zzch.zzab(valueOf.length() != 0 ? str2.concat(valueOf) : new String(str2));
        } else {
            throw new NullPointerException("context cannot be null");
        }
    }

    public ExceptionParser getExceptionParser() {
        return this.zzrn;
    }

    public void setExceptionParser(ExceptionParser exceptionParser) {
        this.zzrn = exceptionParser;
    }

    public void uncaughtException(Thread thread, Throwable th) {
        String str;
        if (this.zzrn != null) {
            str = this.zzrn.getDescription(thread != null ? thread.getName() : null, th);
        } else {
            str = "UncaughtException";
        }
        String str2 = "Reporting uncaught exception: ";
        String valueOf = String.valueOf(str);
        zzch.zzab(valueOf.length() != 0 ? str2.concat(valueOf) : new String(str2));
        this.zzrl.send(new ExceptionBuilder().setDescription(str).setFatal(true).build());
        if (this.zzro == null) {
            this.zzro = GoogleAnalytics.getInstance(this.zzrm);
        }
        GoogleAnalytics googleAnalytics = this.zzro;
        googleAnalytics.dispatchLocalHits();
        googleAnalytics.zzab().zzcs().zzcj();
        if (this.zzrk != null) {
            zzch.zzab("Passing exception to the original handler");
            this.zzrk.uncaughtException(thread, th);
        }
    }

    /* access modifiers changed from: 0000 */
    public final UncaughtExceptionHandler zzaf() {
        return this.zzrk;
    }
}
