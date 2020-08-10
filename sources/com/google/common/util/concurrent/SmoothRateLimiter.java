package com.google.common.util.concurrent;

import com.google.common.math.LongMath;
import com.google.firebase.remoteconfig.FirebaseRemoteConfig;
import java.util.concurrent.TimeUnit;

abstract class SmoothRateLimiter extends RateLimiter {
    double maxPermits;
    private long nextFreeTicketMicros;
    double stableIntervalMicros;
    double storedPermits;

    static final class SmoothBursty extends SmoothRateLimiter {
        final double maxBurstSeconds;

        /* access modifiers changed from: 0000 */
        public long storedPermitsToWaitTime(double d, double d2) {
            return 0;
        }

        SmoothBursty(SleepingStopwatch sleepingStopwatch, double d) {
            super(sleepingStopwatch);
            this.maxBurstSeconds = d;
        }

        /* access modifiers changed from: 0000 */
        public void doSetRate(double d, double d2) {
            double d3 = this.maxPermits;
            this.maxPermits = this.maxBurstSeconds * d;
            if (d3 == Double.POSITIVE_INFINITY) {
                this.storedPermits = this.maxPermits;
                return;
            }
            double d4 = FirebaseRemoteConfig.DEFAULT_VALUE_FOR_DOUBLE;
            if (d3 != FirebaseRemoteConfig.DEFAULT_VALUE_FOR_DOUBLE) {
                d4 = (this.storedPermits * this.maxPermits) / d3;
            }
            this.storedPermits = d4;
        }

        /* access modifiers changed from: 0000 */
        public double coolDownIntervalMicros() {
            return this.stableIntervalMicros;
        }
    }

    static final class SmoothWarmingUp extends SmoothRateLimiter {
        private double coldFactor;
        private double slope;
        private double thresholdPermits;
        private final long warmupPeriodMicros;

        SmoothWarmingUp(SleepingStopwatch sleepingStopwatch, long j, TimeUnit timeUnit, double d) {
            super(sleepingStopwatch);
            this.warmupPeriodMicros = timeUnit.toMicros(j);
            this.coldFactor = d;
        }

        /* access modifiers changed from: 0000 */
        public void doSetRate(double d, double d2) {
            double d3 = this.maxPermits;
            double d4 = this.coldFactor * d2;
            long j = this.warmupPeriodMicros;
            double d5 = (double) j;
            Double.isNaN(d5);
            this.thresholdPermits = (d5 * 0.5d) / d2;
            double d6 = this.thresholdPermits;
            double d7 = (double) j;
            Double.isNaN(d7);
            this.maxPermits = d6 + ((d7 * 2.0d) / (d2 + d4));
            this.slope = (d4 - d2) / (this.maxPermits - this.thresholdPermits);
            if (d3 == Double.POSITIVE_INFINITY) {
                this.storedPermits = FirebaseRemoteConfig.DEFAULT_VALUE_FOR_DOUBLE;
            } else {
                this.storedPermits = d3 == FirebaseRemoteConfig.DEFAULT_VALUE_FOR_DOUBLE ? this.maxPermits : (this.storedPermits * this.maxPermits) / d3;
            }
        }

        /* access modifiers changed from: 0000 */
        public long storedPermitsToWaitTime(double d, double d2) {
            long j;
            double d3 = d - this.thresholdPermits;
            if (d3 > FirebaseRemoteConfig.DEFAULT_VALUE_FOR_DOUBLE) {
                double min = Math.min(d3, d2);
                j = (long) (((permitsToTime(d3) + permitsToTime(d3 - min)) * min) / 2.0d);
                d2 -= min;
            } else {
                j = 0;
            }
            return j + ((long) (this.stableIntervalMicros * d2));
        }

        private double permitsToTime(double d) {
            return this.stableIntervalMicros + (d * this.slope);
        }

        /* access modifiers changed from: 0000 */
        public double coolDownIntervalMicros() {
            double d = (double) this.warmupPeriodMicros;
            double d2 = this.maxPermits;
            Double.isNaN(d);
            return d / d2;
        }
    }

    /* access modifiers changed from: 0000 */
    public abstract double coolDownIntervalMicros();

    /* access modifiers changed from: 0000 */
    public abstract void doSetRate(double d, double d2);

    /* access modifiers changed from: 0000 */
    public abstract long storedPermitsToWaitTime(double d, double d2);

    private SmoothRateLimiter(SleepingStopwatch sleepingStopwatch) {
        super(sleepingStopwatch);
        this.nextFreeTicketMicros = 0;
    }

    /* access modifiers changed from: 0000 */
    public final void doSetRate(double d, long j) {
        resync(j);
        double micros = (double) TimeUnit.SECONDS.toMicros(1);
        Double.isNaN(micros);
        double d2 = micros / d;
        this.stableIntervalMicros = d2;
        doSetRate(d, d2);
    }

    /* access modifiers changed from: 0000 */
    public final double doGetRate() {
        double micros = (double) TimeUnit.SECONDS.toMicros(1);
        double d = this.stableIntervalMicros;
        Double.isNaN(micros);
        return micros / d;
    }

    /* access modifiers changed from: 0000 */
    public final long queryEarliestAvailable(long j) {
        return this.nextFreeTicketMicros;
    }

    /* access modifiers changed from: 0000 */
    public final long reserveEarliestAvailable(int i, long j) {
        resync(j);
        long j2 = this.nextFreeTicketMicros;
        double d = (double) i;
        double min = Math.min(d, this.storedPermits);
        Double.isNaN(d);
        this.nextFreeTicketMicros = LongMath.saturatedAdd(this.nextFreeTicketMicros, storedPermitsToWaitTime(this.storedPermits, min) + ((long) ((d - min) * this.stableIntervalMicros)));
        this.storedPermits -= min;
        return j2;
    }

    /* access modifiers changed from: 0000 */
    public void resync(long j) {
        long j2 = this.nextFreeTicketMicros;
        if (j > j2) {
            double d = (double) (j - j2);
            double coolDownIntervalMicros = coolDownIntervalMicros();
            Double.isNaN(d);
            this.storedPermits = Math.min(this.maxPermits, this.storedPermits + (d / coolDownIntervalMicros));
            this.nextFreeTicketMicros = j;
        }
    }
}
