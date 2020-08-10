package com.google.common.math;

import com.google.common.base.Preconditions;
import com.google.common.primitives.Doubles;
import com.google.firebase.remoteconfig.FirebaseRemoteConfig;
import java.util.Iterator;

public final class StatsAccumulator {
    private long count = 0;
    private double max = Double.NaN;
    private double mean = FirebaseRemoteConfig.DEFAULT_VALUE_FOR_DOUBLE;
    private double min = Double.NaN;
    private double sumOfSquaresOfDeltas = FirebaseRemoteConfig.DEFAULT_VALUE_FOR_DOUBLE;

    public void add(double d) {
        long j = this.count;
        if (j == 0) {
            this.count = 1;
            this.mean = d;
            this.min = d;
            this.max = d;
            if (!Doubles.isFinite(d)) {
                this.sumOfSquaresOfDeltas = Double.NaN;
                return;
            }
            return;
        }
        this.count = j + 1;
        if (!Doubles.isFinite(d) || !Doubles.isFinite(this.mean)) {
            this.mean = calculateNewMeanNonFinite(this.mean, d);
            this.sumOfSquaresOfDeltas = Double.NaN;
        } else {
            double d2 = this.mean;
            double d3 = d - d2;
            double d4 = (double) this.count;
            Double.isNaN(d4);
            this.mean = d2 + (d3 / d4);
            this.sumOfSquaresOfDeltas += d3 * (d - this.mean);
        }
        this.min = Math.min(this.min, d);
        this.max = Math.max(this.max, d);
    }

    public void addAll(Iterable<? extends Number> iterable) {
        for (Number doubleValue : iterable) {
            add(doubleValue.doubleValue());
        }
    }

    public void addAll(Iterator<? extends Number> it) {
        while (it.hasNext()) {
            add(((Number) it.next()).doubleValue());
        }
    }

    public void addAll(double... dArr) {
        for (double add : dArr) {
            add(add);
        }
    }

    public void addAll(int... iArr) {
        for (int i : iArr) {
            add((double) i);
        }
    }

    public void addAll(long... jArr) {
        for (long j : jArr) {
            add((double) j);
        }
    }

    public void addAll(Stats stats) {
        if (stats.count() != 0) {
            long j = this.count;
            if (j == 0) {
                this.count = stats.count();
                this.mean = stats.mean();
                this.sumOfSquaresOfDeltas = stats.sumOfSquaresOfDeltas();
                this.min = stats.min();
                this.max = stats.max();
            } else {
                this.count = j + stats.count();
                if (!Doubles.isFinite(this.mean) || !Doubles.isFinite(stats.mean())) {
                    this.mean = calculateNewMeanNonFinite(this.mean, stats.mean());
                    this.sumOfSquaresOfDeltas = Double.NaN;
                } else {
                    double mean2 = stats.mean();
                    double d = this.mean;
                    double d2 = mean2 - d;
                    double count2 = (double) stats.count();
                    Double.isNaN(count2);
                    double d3 = count2 * d2;
                    double d4 = (double) this.count;
                    Double.isNaN(d4);
                    this.mean = d + (d3 / d4);
                    double d5 = this.sumOfSquaresOfDeltas;
                    double sumOfSquaresOfDeltas2 = stats.sumOfSquaresOfDeltas();
                    double mean3 = d2 * (stats.mean() - this.mean);
                    double count3 = (double) stats.count();
                    Double.isNaN(count3);
                    this.sumOfSquaresOfDeltas = d5 + sumOfSquaresOfDeltas2 + (mean3 * count3);
                }
                this.min = Math.min(this.min, stats.min());
                this.max = Math.max(this.max, stats.max());
            }
        }
    }

    public Stats snapshot() {
        Stats stats = new Stats(this.count, this.mean, this.sumOfSquaresOfDeltas, this.min, this.max);
        return stats;
    }

    public long count() {
        return this.count;
    }

    public double mean() {
        Preconditions.checkState(this.count != 0);
        return this.mean;
    }

    public final double sum() {
        double d = this.mean;
        double d2 = (double) this.count;
        Double.isNaN(d2);
        return d * d2;
    }

    public final double populationVariance() {
        Preconditions.checkState(this.count != 0);
        if (Double.isNaN(this.sumOfSquaresOfDeltas)) {
            return Double.NaN;
        }
        if (this.count == 1) {
            return FirebaseRemoteConfig.DEFAULT_VALUE_FOR_DOUBLE;
        }
        double ensureNonNegative = DoubleUtils.ensureNonNegative(this.sumOfSquaresOfDeltas);
        double d = (double) this.count;
        Double.isNaN(d);
        return ensureNonNegative / d;
    }

    public final double populationStandardDeviation() {
        return Math.sqrt(populationVariance());
    }

    public final double sampleVariance() {
        Preconditions.checkState(this.count > 1);
        if (Double.isNaN(this.sumOfSquaresOfDeltas)) {
            return Double.NaN;
        }
        double ensureNonNegative = DoubleUtils.ensureNonNegative(this.sumOfSquaresOfDeltas);
        double d = (double) (this.count - 1);
        Double.isNaN(d);
        return ensureNonNegative / d;
    }

    public final double sampleStandardDeviation() {
        return Math.sqrt(sampleVariance());
    }

    public double min() {
        Preconditions.checkState(this.count != 0);
        return this.min;
    }

    public double max() {
        Preconditions.checkState(this.count != 0);
        return this.max;
    }

    /* access modifiers changed from: 0000 */
    public double sumOfSquaresOfDeltas() {
        return this.sumOfSquaresOfDeltas;
    }

    static double calculateNewMeanNonFinite(double d, double d2) {
        if (Doubles.isFinite(d)) {
            return d2;
        }
        if (!Doubles.isFinite(d2) && d != d2) {
            d = Double.NaN;
        }
        return d;
    }
}
