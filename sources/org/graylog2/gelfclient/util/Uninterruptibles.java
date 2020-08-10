package org.graylog2.gelfclient.util;

import java.util.concurrent.TimeUnit;

public class Uninterruptibles {
    public static void sleepUninterruptibly(long j, TimeUnit timeUnit) {
        long nanos;
        long nanoTime;
        boolean z = false;
        try {
            nanos = timeUnit.toNanos(j);
            nanoTime = System.nanoTime() + nanos;
            while (true) {
                TimeUnit.NANOSECONDS.sleep(nanos);
                break;
            }
            if (z) {
                Thread.currentThread().interrupt();
            }
        } catch (InterruptedException unused) {
            z = true;
            nanos = nanoTime - System.nanoTime();
        } catch (Throwable th) {
            if (z) {
                Thread.currentThread().interrupt();
            }
            throw th;
        }
    }
}
