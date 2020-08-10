package com.crashlytics.android.core;

import android.os.AsyncTask;
import androidx.exifinterface.media.ExifInterface;
import com.google.android.exoplayer2.metadata.icy.IcyHeaders;
import p043io.fabric.sdk.android.Fabric;
import p043io.fabric.sdk.android.Logger;

public class CrashTest {
    public void throwRuntimeException(String str) {
        throw new RuntimeException(str);
    }

    public int stackOverflow() {
        return stackOverflow() + ((int) Math.random());
    }

    public void indexOutOfBounds() {
        int i = new int[2][10];
        Logger logger = Fabric.getLogger();
        StringBuilder sb = new StringBuilder();
        sb.append("Out of bounds value: ");
        sb.append(i);
        logger.mo64074d(CrashlyticsCore.TAG, sb.toString());
    }

    public void crashAsyncTask(final long j) {
        new AsyncTask<Void, Void, Void>() {
            /* access modifiers changed from: protected */
            public Void doInBackground(Void... voidArr) {
                try {
                    Thread.sleep(j);
                } catch (InterruptedException unused) {
                }
                CrashTest.this.throwRuntimeException("Background thread crash");
                return null;
            }
        }.execute(new Void[]{null});
    }

    public void throwFiveChainedExceptions() {
        try {
            privateMethodThatThrowsException(IcyHeaders.REQUEST_HEADER_ENABLE_METADATA_VALUE);
        } catch (Exception e) {
            throw new RuntimeException(ExifInterface.GPS_MEASUREMENT_2D, e);
        } catch (Exception e2) {
            try {
                throw new RuntimeException(ExifInterface.GPS_MEASUREMENT_3D, e2);
            } catch (Exception e3) {
                try {
                    throw new RuntimeException("4", e3);
                } catch (Exception e4) {
                    throw new RuntimeException("5", e4);
                }
            }
        }
    }

    private void privateMethodThatThrowsException(String str) {
        throw new RuntimeException(str);
    }
}
