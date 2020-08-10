package com.braintreepayments.api;

import com.google.android.gms.measurement.AppMeasurement;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.lang.Thread.UncaughtExceptionHandler;

class CrashReporter implements UncaughtExceptionHandler {
    private BraintreeFragment mBraintreeFragment;
    private UncaughtExceptionHandler mDefaultExceptionHandler = Thread.getDefaultUncaughtExceptionHandler();

    static CrashReporter setup(BraintreeFragment braintreeFragment) {
        return new CrashReporter(braintreeFragment);
    }

    private CrashReporter(BraintreeFragment braintreeFragment) {
        this.mBraintreeFragment = braintreeFragment;
        Thread.setDefaultUncaughtExceptionHandler(this);
    }

    /* access modifiers changed from: 0000 */
    public void tearDown() {
        Thread.setDefaultUncaughtExceptionHandler(this.mDefaultExceptionHandler);
    }

    public void uncaughtException(Thread thread, Throwable th) {
        StringWriter stringWriter = new StringWriter();
        th.printStackTrace(new PrintWriter(stringWriter));
        if (stringWriter.toString().contains("com.braintreepayments") || stringWriter.toString().contains("com.paypal")) {
            this.mBraintreeFragment.sendAnalyticsEvent(AppMeasurement.CRASH_ORIGIN);
        }
        UncaughtExceptionHandler uncaughtExceptionHandler = this.mDefaultExceptionHandler;
        if (uncaughtExceptionHandler != null) {
            uncaughtExceptionHandler.uncaughtException(thread, th);
        }
    }
}
