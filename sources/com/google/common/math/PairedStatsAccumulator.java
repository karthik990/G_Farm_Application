package com.google.common.math;

import com.google.common.base.Preconditions;
import com.google.common.primitives.Doubles;
import com.google.firebase.remoteconfig.FirebaseRemoteConfig;

public final class PairedStatsAccumulator {
    private double sumOfProductsOfDeltas = FirebaseRemoteConfig.DEFAULT_VALUE_FOR_DOUBLE;
    private final StatsAccumulator xStats = new StatsAccumulator();
    private final StatsAccumulator yStats = new StatsAccumulator();

    private static double ensureInUnitRange(double d) {
        if (d >= 1.0d) {
            return 1.0d;
        }
        if (d <= -1.0d) {
            return -1.0d;
        }
        return d;
    }

    private double ensurePositive(double d) {
        if (d > FirebaseRemoteConfig.DEFAULT_VALUE_FOR_DOUBLE) {
            return d;
        }
        return Double.MIN_VALUE;
    }

    public void add(double d, double d2) {
        this.xStats.add(d);
        if (!Doubles.isFinite(d) || !Doubles.isFinite(d2)) {
            this.sumOfProductsOfDeltas = Double.NaN;
        } else if (this.xStats.count() > 1) {
            this.sumOfProductsOfDeltas += (d - this.xStats.mean()) * (d2 - this.yStats.mean());
        }
        this.yStats.add(d2);
    }

    public void addAll(PairedStats pairedStats) {
        if (pairedStats.count() != 0) {
            this.xStats.addAll(pairedStats.xStats());
            if (this.yStats.count() == 0) {
                this.sumOfProductsOfDeltas = pairedStats.sumOfProductsOfDeltas();
            } else {
                double d = this.sumOfProductsOfDeltas;
                double sumOfProductsOfDeltas2 = pairedStats.sumOfProductsOfDeltas();
                double mean = (pairedStats.xStats().mean() - this.xStats.mean()) * (pairedStats.yStats().mean() - this.yStats.mean());
                double count = (double) pairedStats.count();
                Double.isNaN(count);
                this.sumOfProductsOfDeltas = d + sumOfProductsOfDeltas2 + (mean * count);
            }
            this.yStats.addAll(pairedStats.yStats());
        }
    }

    public PairedStats snapshot() {
        return new PairedStats(this.xStats.snapshot(), this.yStats.snapshot(), this.sumOfProductsOfDeltas);
    }

    public long count() {
        return this.xStats.count();
    }

    public Stats xStats() {
        return this.xStats.snapshot();
    }

    public Stats yStats() {
        return this.yStats.snapshot();
    }

    public double populationCovariance() {
        Preconditions.checkState(count() != 0);
        double d = this.sumOfProductsOfDeltas;
        double count = (double) count();
        Double.isNaN(count);
        return d / count;
    }

    public final double sampleCovariance() {
        Preconditions.checkState(count() > 1);
        double d = this.sumOfProductsOfDeltas;
        double count = (double) (count() - 1);
        Double.isNaN(count);
        return d / count;
    }

    public final double pearsonsCorrelationCoefficient() {
        boolean z = true;
        Preconditions.checkState(count() > 1);
        if (Double.isNaN(this.sumOfProductsOfDeltas)) {
            return Double.NaN;
        }
        double sumOfSquaresOfDeltas = this.xStats.sumOfSquaresOfDeltas();
        double sumOfSquaresOfDeltas2 = this.yStats.sumOfSquaresOfDeltas();
        Preconditions.checkState(sumOfSquaresOfDeltas > FirebaseRemoteConfig.DEFAULT_VALUE_FOR_DOUBLE);
        if (sumOfSquaresOfDeltas2 <= FirebaseRemoteConfig.DEFAULT_VALUE_FOR_DOUBLE) {
            z = false;
        }
        Preconditions.checkState(z);
        return ensureInUnitRange(this.sumOfProductsOfDeltas / Math.sqrt(ensurePositive(sumOfSquaresOfDeltas * sumOfSquaresOfDeltas2)));
    }

    public final LinearTransformation leastSquaresFit() {
        boolean z = true;
        Preconditions.checkState(count() > 1);
        if (Double.isNaN(this.sumOfProductsOfDeltas)) {
            return LinearTransformation.forNaN();
        }
        double sumOfSquaresOfDeltas = this.xStats.sumOfSquaresOfDeltas();
        if (sumOfSquaresOfDeltas <= FirebaseRemoteConfig.DEFAULT_VALUE_FOR_DOUBLE) {
            if (this.yStats.sumOfSquaresOfDeltas() <= FirebaseRemoteConfig.DEFAULT_VALUE_FOR_DOUBLE) {
                z = false;
            }
            Preconditions.checkState(z);
            return LinearTransformation.vertical(this.xStats.mean());
        } else if (this.yStats.sumOfSquaresOfDeltas() > FirebaseRemoteConfig.DEFAULT_VALUE_FOR_DOUBLE) {
            return LinearTransformation.mapping(this.xStats.mean(), this.yStats.mean()).withSlope(this.sumOfProductsOfDeltas / sumOfSquaresOfDeltas);
        } else {
            return LinearTransformation.horizontal(this.yStats.mean());
        }
    }
}