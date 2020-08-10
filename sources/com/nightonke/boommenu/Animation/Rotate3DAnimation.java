package com.nightonke.boommenu.Animation;

import android.graphics.Camera;
import android.graphics.Matrix;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.Transformation;
import java.util.ArrayList;

public class Rotate3DAnimation extends Animation {
    private Camera camera;
    private float centerX;
    private float centerY;
    private float startX;
    private float startY;
    private View view;

    /* renamed from: xs */
    private ArrayList<Float> f2237xs;

    /* renamed from: ys */
    private ArrayList<Float> f2238ys;

    public Rotate3DAnimation(float f, float f2, ArrayList<Float> arrayList, ArrayList<Float> arrayList2) {
        this.centerX = f;
        this.centerY = f2;
        this.f2237xs = arrayList;
        this.f2238ys = arrayList2;
    }

    public void initialize(int i, int i2, int i3, int i4) {
        super.initialize(i, i2, i3, i4);
        this.camera = new Camera();
    }

    /* access modifiers changed from: protected */
    public void applyTransformation(float f, Transformation transformation) {
        float f2;
        Camera camera2 = this.camera;
        Matrix matrix = transformation.getMatrix();
        float f3 = 0.0f;
        if (f != 1.0f) {
            float size = ((float) this.f2237xs.size()) * f;
            int i = (int) size;
            int i2 = i + 1;
            if (i2 >= this.f2237xs.size()) {
                i2 = this.f2237xs.size() - 1;
            }
            f3 = ((Float) this.f2237xs.get(i)).floatValue() + ((((Float) this.f2237xs.get(i2)).floatValue() - ((Float) this.f2237xs.get(i)).floatValue()) * (size - ((float) i)));
            float size2 = f * ((float) this.f2238ys.size());
            int i3 = (int) size2;
            int i4 = i3 + 1;
            if (i4 >= this.f2238ys.size()) {
                i4 = this.f2238ys.size() - 1;
            }
            f2 = ((Float) this.f2238ys.get(i3)).floatValue() + ((((Float) this.f2238ys.get(i4)).floatValue() - ((Float) this.f2238ys.get(i3)).floatValue()) * (size2 - ((float) i3)));
        } else {
            f2 = 0.0f;
        }
        camera2.save();
        camera2.rotateX(f3);
        camera2.rotateY(f2);
        camera2.getMatrix(matrix);
        camera2.restore();
        float x = this.view.getX() - this.startX;
        float y = this.view.getY() - this.startY;
        matrix.preTranslate((-x) - this.centerX, (-y) - this.centerY);
        matrix.postTranslate(x + this.centerX, y + this.centerY);
    }

    public void set(View view2, float f, float f2) {
        this.view = view2;
        this.startX = f;
        this.startY = f2;
    }
}
