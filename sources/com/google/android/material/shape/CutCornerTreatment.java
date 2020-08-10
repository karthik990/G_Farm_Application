package com.google.android.material.shape;

public class CutCornerTreatment extends CornerTreatment {
    float size = -1.0f;

    public CutCornerTreatment() {
    }

    @Deprecated
    public CutCornerTreatment(float f) {
        this.size = f;
    }

    public void getCornerPath(ShapePath shapePath, float f, float f2, float f3) {
        shapePath.reset(0.0f, f3 * f2, 180.0f, 180.0f - f);
        double sin = Math.sin(Math.toRadians((double) f));
        double d = (double) f3;
        Double.isNaN(d);
        double d2 = sin * d;
        double d3 = (double) f2;
        Double.isNaN(d3);
        float f4 = (float) (d2 * d3);
        double sin2 = Math.sin(Math.toRadians((double) (90.0f - f)));
        Double.isNaN(d);
        double d4 = sin2 * d;
        Double.isNaN(d3);
        shapePath.lineTo(f4, (float) (d4 * d3));
    }
}
