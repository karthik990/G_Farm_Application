package com.airbnb.lottie.model.content;

import android.graphics.PointF;
import com.airbnb.lottie.model.CubicCurveData;
import com.airbnb.lottie.utils.MiscUtils;
import java.util.ArrayList;
import java.util.List;

public class ShapeData {
    private boolean closed;
    private final List<CubicCurveData> curves = new ArrayList();
    private PointF initialPoint;

    public ShapeData(PointF pointF, boolean z, List<CubicCurveData> list) {
        this.initialPoint = pointF;
        this.closed = z;
        this.curves.addAll(list);
    }

    public ShapeData() {
    }

    private void setInitialPoint(float f, float f2) {
        if (this.initialPoint == null) {
            this.initialPoint = new PointF();
        }
        this.initialPoint.set(f, f2);
    }

    public PointF getInitialPoint() {
        return this.initialPoint;
    }

    public boolean isClosed() {
        return this.closed;
    }

    public List<CubicCurveData> getCurves() {
        return this.curves;
    }

    public void interpolateBetween(ShapeData shapeData, ShapeData shapeData2, float f) {
        if (this.initialPoint == null) {
            this.initialPoint = new PointF();
        }
        this.closed = shapeData.isClosed() || shapeData2.isClosed();
        if (this.curves.isEmpty() || this.curves.size() == shapeData.getCurves().size() || this.curves.size() == shapeData2.getCurves().size()) {
            if (this.curves.isEmpty()) {
                for (int size = shapeData.getCurves().size() - 1; size >= 0; size--) {
                    this.curves.add(new CubicCurveData());
                }
            }
            PointF initialPoint2 = shapeData.getInitialPoint();
            PointF initialPoint3 = shapeData2.getInitialPoint();
            setInitialPoint(MiscUtils.lerp(initialPoint2.x, initialPoint3.x, f), MiscUtils.lerp(initialPoint2.y, initialPoint3.y, f));
            for (int size2 = this.curves.size() - 1; size2 >= 0; size2--) {
                CubicCurveData cubicCurveData = (CubicCurveData) shapeData.getCurves().get(size2);
                CubicCurveData cubicCurveData2 = (CubicCurveData) shapeData2.getCurves().get(size2);
                PointF controlPoint1 = cubicCurveData.getControlPoint1();
                PointF controlPoint2 = cubicCurveData.getControlPoint2();
                PointF vertex = cubicCurveData.getVertex();
                PointF controlPoint12 = cubicCurveData2.getControlPoint1();
                PointF controlPoint22 = cubicCurveData2.getControlPoint2();
                PointF vertex2 = cubicCurveData2.getVertex();
                ((CubicCurveData) this.curves.get(size2)).setControlPoint1(MiscUtils.lerp(controlPoint1.x, controlPoint12.x, f), MiscUtils.lerp(controlPoint1.y, controlPoint12.y, f));
                ((CubicCurveData) this.curves.get(size2)).setControlPoint2(MiscUtils.lerp(controlPoint2.x, controlPoint22.x, f), MiscUtils.lerp(controlPoint2.y, controlPoint22.y, f));
                ((CubicCurveData) this.curves.get(size2)).setVertex(MiscUtils.lerp(vertex.x, vertex2.x, f), MiscUtils.lerp(vertex.y, vertex2.y, f));
            }
            return;
        }
        StringBuilder sb = new StringBuilder();
        sb.append("Curves must have the same number of control points. This: ");
        sb.append(getCurves().size());
        sb.append("\tShape 1: ");
        sb.append(shapeData.getCurves().size());
        sb.append("\tShape 2: ");
        sb.append(shapeData2.getCurves().size());
        throw new IllegalStateException(sb.toString());
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("ShapeData{numCurves=");
        sb.append(this.curves.size());
        sb.append("closed=");
        sb.append(this.closed);
        sb.append('}');
        return sb.toString();
    }
}
