package com.google.common.math;

import com.google.common.base.Preconditions;
import com.google.errorprone.annotations.concurrent.LazyInit;
import com.google.firebase.remoteconfig.FirebaseRemoteConfig;

public abstract class LinearTransformation {

    public static final class LinearTransformationBuilder {

        /* renamed from: x1 */
        private final double f2003x1;

        /* renamed from: y1 */
        private final double f2004y1;

        private LinearTransformationBuilder(double d, double d2) {
            this.f2003x1 = d;
            this.f2004y1 = d2;
        }

        public LinearTransformation and(double d, double d2) {
            boolean z = true;
            Preconditions.checkArgument(DoubleUtils.isFinite(d) && DoubleUtils.isFinite(d2));
            double d3 = this.f2003x1;
            if (d != d3) {
                return withSlope((d2 - this.f2004y1) / (d - d3));
            }
            if (d2 == this.f2004y1) {
                z = false;
            }
            Preconditions.checkArgument(z);
            return new VerticalLinearTransformation(this.f2003x1);
        }

        public LinearTransformation withSlope(double d) {
            Preconditions.checkArgument(!Double.isNaN(d));
            if (DoubleUtils.isFinite(d)) {
                return new RegularLinearTransformation(d, this.f2004y1 - (this.f2003x1 * d));
            }
            return new VerticalLinearTransformation(this.f2003x1);
        }
    }

    private static final class NaNLinearTransformation extends LinearTransformation {
        static final NaNLinearTransformation INSTANCE = new NaNLinearTransformation();

        public LinearTransformation inverse() {
            return this;
        }

        public boolean isHorizontal() {
            return false;
        }

        public boolean isVertical() {
            return false;
        }

        public double slope() {
            return Double.NaN;
        }

        public String toString() {
            return "NaN";
        }

        public double transform(double d) {
            return Double.NaN;
        }

        private NaNLinearTransformation() {
        }
    }

    private static final class RegularLinearTransformation extends LinearTransformation {
        @LazyInit
        LinearTransformation inverse;
        final double slope;
        final double yIntercept;

        public boolean isVertical() {
            return false;
        }

        RegularLinearTransformation(double d, double d2) {
            this.slope = d;
            this.yIntercept = d2;
            this.inverse = null;
        }

        RegularLinearTransformation(double d, double d2, LinearTransformation linearTransformation) {
            this.slope = d;
            this.yIntercept = d2;
            this.inverse = linearTransformation;
        }

        public boolean isHorizontal() {
            return this.slope == FirebaseRemoteConfig.DEFAULT_VALUE_FOR_DOUBLE;
        }

        public double slope() {
            return this.slope;
        }

        public double transform(double d) {
            return (d * this.slope) + this.yIntercept;
        }

        public LinearTransformation inverse() {
            LinearTransformation linearTransformation = this.inverse;
            if (linearTransformation != null) {
                return linearTransformation;
            }
            LinearTransformation createInverse = createInverse();
            this.inverse = createInverse;
            return createInverse;
        }

        public String toString() {
            return String.format("y = %g * x + %g", new Object[]{Double.valueOf(this.slope), Double.valueOf(this.yIntercept)});
        }

        private LinearTransformation createInverse() {
            double d = this.slope;
            if (d == FirebaseRemoteConfig.DEFAULT_VALUE_FOR_DOUBLE) {
                return new VerticalLinearTransformation(this.yIntercept, this);
            }
            RegularLinearTransformation regularLinearTransformation = new RegularLinearTransformation(1.0d / d, (this.yIntercept * -1.0d) / d, this);
            return regularLinearTransformation;
        }
    }

    private static final class VerticalLinearTransformation extends LinearTransformation {
        @LazyInit
        LinearTransformation inverse;

        /* renamed from: x */
        final double f2005x;

        public boolean isHorizontal() {
            return false;
        }

        public boolean isVertical() {
            return true;
        }

        VerticalLinearTransformation(double d) {
            this.f2005x = d;
            this.inverse = null;
        }

        VerticalLinearTransformation(double d, LinearTransformation linearTransformation) {
            this.f2005x = d;
            this.inverse = linearTransformation;
        }

        public double slope() {
            throw new IllegalStateException();
        }

        public double transform(double d) {
            throw new IllegalStateException();
        }

        public LinearTransformation inverse() {
            LinearTransformation linearTransformation = this.inverse;
            if (linearTransformation != null) {
                return linearTransformation;
            }
            LinearTransformation createInverse = createInverse();
            this.inverse = createInverse;
            return createInverse;
        }

        public String toString() {
            return String.format("x = %g", new Object[]{Double.valueOf(this.f2005x)});
        }

        private LinearTransformation createInverse() {
            RegularLinearTransformation regularLinearTransformation = new RegularLinearTransformation(FirebaseRemoteConfig.DEFAULT_VALUE_FOR_DOUBLE, this.f2005x, this);
            return regularLinearTransformation;
        }
    }

    public abstract LinearTransformation inverse();

    public abstract boolean isHorizontal();

    public abstract boolean isVertical();

    public abstract double slope();

    public abstract double transform(double d);

    public static LinearTransformationBuilder mapping(double d, double d2) {
        Preconditions.checkArgument(DoubleUtils.isFinite(d) && DoubleUtils.isFinite(d2));
        LinearTransformationBuilder linearTransformationBuilder = new LinearTransformationBuilder(d, d2);
        return linearTransformationBuilder;
    }

    public static LinearTransformation vertical(double d) {
        Preconditions.checkArgument(DoubleUtils.isFinite(d));
        return new VerticalLinearTransformation(d);
    }

    public static LinearTransformation horizontal(double d) {
        Preconditions.checkArgument(DoubleUtils.isFinite(d));
        return new RegularLinearTransformation(FirebaseRemoteConfig.DEFAULT_VALUE_FOR_DOUBLE, d);
    }

    public static LinearTransformation forNaN() {
        return NaNLinearTransformation.INSTANCE;
    }
}
