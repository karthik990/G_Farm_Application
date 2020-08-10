package com.google.android.material.shape;

public final class MarkerEdgeTreatment extends EdgeTreatment {
    private final float radius;

    /* access modifiers changed from: 0000 */
    public boolean forceIntersection() {
        return true;
    }

    public MarkerEdgeTreatment(float f) {
        this.radius = f - 0.001f;
    }

    public void getEdgePath(float f, float f2, float f3, ShapePath shapePath) {
        double d = (double) this.radius;
        double sqrt = Math.sqrt(2.0d);
        Double.isNaN(d);
        float f4 = (float) ((d * sqrt) / 2.0d);
        float sqrt2 = (float) Math.sqrt(Math.pow((double) this.radius, 2.0d) - Math.pow((double) f4, 2.0d));
        float f5 = f2 - f4;
        double d2 = (double) this.radius;
        double sqrt3 = Math.sqrt(2.0d);
        Double.isNaN(d2);
        double d3 = d2 * sqrt3;
        double d4 = (double) this.radius;
        Double.isNaN(d4);
        shapePath.reset(f5, ((float) (-(d3 - d4))) + sqrt2);
        double d5 = (double) this.radius;
        double sqrt4 = Math.sqrt(2.0d);
        Double.isNaN(d5);
        double d6 = d5 * sqrt4;
        double d7 = (double) this.radius;
        Double.isNaN(d7);
        shapePath.lineTo(f2, (float) (-(d6 - d7)));
        float f6 = f2 + f4;
        double d8 = (double) this.radius;
        double sqrt5 = Math.sqrt(2.0d);
        Double.isNaN(d8);
        double d9 = d8 * sqrt5;
        double d10 = (double) this.radius;
        Double.isNaN(d10);
        shapePath.lineTo(f6, ((float) (-(d9 - d10))) + sqrt2);
    }
}
