package com.google.android.material.transition;

import android.graphics.Canvas;
import android.graphics.Path;
import android.graphics.Path.Op;
import android.graphics.RectF;
import android.graphics.Region;
import android.os.Build.VERSION;
import androidx.core.util.Preconditions;
import com.google.android.material.shape.ShapeAppearanceModel;
import com.google.android.material.shape.ShapeAppearancePathProvider;
import com.google.android.material.transition.MaterialContainerTransform.ProgressThresholds;

class MaskEvaluator {
    private final Path endPath = new Path();
    private final Path path = new Path();
    private final ShapeAppearancePathProvider pathProvider = new ShapeAppearancePathProvider();
    private final Path startPath = new Path();

    MaskEvaluator() {
    }

    /* access modifiers changed from: 0000 */
    public void evaluate(float f, ShapeAppearanceModel shapeAppearanceModel, ShapeAppearanceModel shapeAppearanceModel2, RectF rectF, RectF rectF2, RectF rectF3, ProgressThresholds progressThresholds) {
        ShapeAppearanceModel lerp = TransitionUtils.lerp(shapeAppearanceModel, shapeAppearanceModel2, rectF, rectF3, ((Float) Preconditions.checkNotNull(Float.valueOf(progressThresholds.start))).floatValue(), ((Float) Preconditions.checkNotNull(Float.valueOf(progressThresholds.end))).floatValue(), f);
        this.pathProvider.calculatePath(lerp, 1.0f, rectF2, this.startPath);
        this.pathProvider.calculatePath(lerp, 1.0f, rectF3, this.endPath);
        if (VERSION.SDK_INT >= 23) {
            this.path.op(this.startPath, this.endPath, Op.UNION);
        }
    }

    /* access modifiers changed from: 0000 */
    public void clip(Canvas canvas) {
        if (VERSION.SDK_INT >= 23) {
            canvas.clipPath(this.path);
            return;
        }
        canvas.clipPath(this.startPath);
        canvas.clipPath(this.endPath, Region.Op.UNION);
    }
}
