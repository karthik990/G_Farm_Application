package com.google.android.material.transition;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.animation.ValueAnimator.AnimatorUpdateListener;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.graphics.PointF;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.transition.ArcMotion;
import android.transition.PathMotion;
import android.transition.Transition;
import android.transition.TransitionValues;
import android.view.View;
import android.view.ViewGroup;
import androidx.core.content.ContextCompat;
import androidx.core.util.Preconditions;
import androidx.core.view.InputDeviceCompat;
import androidx.core.view.ViewCompat;
import com.google.android.material.C2556R;
import com.google.android.material.animation.AnimationUtils;
import com.google.android.material.color.MaterialColors;
import com.google.android.material.shape.ShapeAppearanceModel;
import com.google.android.material.shape.Shapeable;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.Map;

public class MaterialContainerTransform extends Transition {
    private static final ProgressThresholdsGroup DEFAULT_ENTER_THRESHOLDS;
    private static final ProgressThresholdsGroup DEFAULT_ENTER_THRESHOLDS_ARC;
    private static final ProgressThresholdsGroup DEFAULT_RETURN_THRESHOLDS;
    private static final ProgressThresholdsGroup DEFAULT_RETURN_THRESHOLDS_ARC;
    public static final int FADE_MODE_CROSS = 2;
    public static final int FADE_MODE_IN = 0;
    public static final int FADE_MODE_OUT = 1;
    public static final int FADE_MODE_THROUGH = 3;
    public static final int FIT_MODE_AUTO = 0;
    public static final int FIT_MODE_HEIGHT = 2;
    public static final int FIT_MODE_WIDTH = 1;
    private static final String PROP_BOUNDS = "materialContainerTransition:bounds";
    private static final String PROP_SHAPE_APPEARANCE = "materialContainerTransition:shapeAppearance";
    public static final int TRANSITION_DIRECTION_AUTO = 0;
    public static final int TRANSITION_DIRECTION_ENTER = 1;
    public static final int TRANSITION_DIRECTION_RETURN = 2;
    private static final String[] TRANSITION_PROPS = {PROP_BOUNDS, PROP_SHAPE_APPEARANCE};
    private int containerColor = 0;
    private boolean drawDebugEnabled = false;
    private int drawingViewId = 16908290;
    private ShapeAppearanceModel endShapeAppearanceModel;
    private View endView;
    private int endViewId = -1;
    private int fadeMode = 0;
    private ProgressThresholds fadeProgressThresholds;
    private int fitMode = 0;
    /* access modifiers changed from: private */
    public boolean holdAtEndEnabled = false;
    private ProgressThresholds scaleMaskProgressThresholds;
    private ProgressThresholds scaleProgressThresholds;
    private int scrimColor = -1;
    private ProgressThresholds shapeMaskProgressThresholds;
    private ShapeAppearanceModel startShapeAppearanceModel;
    private View startView;
    private int startViewId = -1;
    private int transitionDirection = 0;

    @Retention(RetentionPolicy.SOURCE)
    public @interface FadeMode {
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface FitMode {
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface TransitionDirection {
    }

    public static class ProgressThresholds {
        final float end;
        final float start;

        public ProgressThresholds(float f, float f2) {
            this.start = f;
            this.end = f2;
        }
    }

    private static class ProgressThresholdsGroup {
        /* access modifiers changed from: private */
        public final ProgressThresholds fade;
        /* access modifiers changed from: private */
        public final ProgressThresholds scale;
        /* access modifiers changed from: private */
        public final ProgressThresholds scaleMask;
        /* access modifiers changed from: private */
        public final ProgressThresholds shapeMask;

        private ProgressThresholdsGroup(ProgressThresholds progressThresholds, ProgressThresholds progressThresholds2, ProgressThresholds progressThresholds3, ProgressThresholds progressThresholds4) {
            this.fade = progressThresholds;
            this.scale = progressThresholds2;
            this.scaleMask = progressThresholds3;
            this.shapeMask = progressThresholds4;
        }
    }

    private static final class TransitionDrawable extends Drawable {
        private final Paint containerPaint;
        private final RectF currentEndBounds;
        private final RectF currentEndBoundsMasked;
        private final RectF currentStartBounds;
        private final RectF currentStartBoundsMasked;
        private final Paint debugPaint;
        private final Path debugPath;
        private final boolean drawDebugEnabled;
        private final RectF endBounds;
        private final ShapeAppearanceModel endShapeAppearanceModel;
        /* access modifiers changed from: private */
        public final View endView;
        private final boolean entering;
        private final FadeModeEvaluator fadeModeEvaluator;
        private FadeModeResult fadeModeResult;
        private final FitModeEvaluator fitModeEvaluator;
        private FitModeResult fitModeResult;
        private final MaskEvaluator maskEvaluator;
        private final float motionPathLength;
        private final PathMeasure motionPathMeasure;
        private final float[] motionPathPosition;
        private float progress;
        private final ProgressThresholdsGroup progressThresholds;
        private final Paint scrimPaint;
        private final RectF startBounds;
        private final ShapeAppearanceModel startShapeAppearanceModel;
        /* access modifiers changed from: private */
        public final View startView;

        public int getOpacity() {
            return -3;
        }

        private TransitionDrawable(PathMotion pathMotion, View view, RectF rectF, ShapeAppearanceModel shapeAppearanceModel, View view2, RectF rectF2, ShapeAppearanceModel shapeAppearanceModel2, int i, int i2, boolean z, FadeModeEvaluator fadeModeEvaluator2, FitModeEvaluator fitModeEvaluator2, ProgressThresholdsGroup progressThresholdsGroup, boolean z2) {
            this.maskEvaluator = new MaskEvaluator();
            this.motionPathPosition = new float[2];
            this.containerPaint = new Paint();
            this.scrimPaint = new Paint();
            this.debugPaint = new Paint();
            this.debugPath = new Path();
            this.progress = 0.0f;
            this.startView = view;
            this.startBounds = rectF;
            this.startShapeAppearanceModel = shapeAppearanceModel;
            this.endView = view2;
            this.endBounds = rectF2;
            this.endShapeAppearanceModel = shapeAppearanceModel2;
            this.entering = z;
            this.fadeModeEvaluator = fadeModeEvaluator2;
            this.fitModeEvaluator = fitModeEvaluator2;
            this.progressThresholds = progressThresholdsGroup;
            this.drawDebugEnabled = z2;
            this.containerPaint.setColor(i);
            this.currentStartBounds = new RectF(rectF);
            this.currentStartBoundsMasked = new RectF(this.currentStartBounds);
            this.currentEndBounds = new RectF(this.currentStartBounds);
            this.currentEndBoundsMasked = new RectF(this.currentEndBounds);
            PointF motionPathPoint = getMotionPathPoint(rectF);
            PointF motionPathPoint2 = getMotionPathPoint(rectF2);
            this.motionPathMeasure = new PathMeasure(pathMotion.getPath(motionPathPoint.x, motionPathPoint.y, motionPathPoint2.x, motionPathPoint2.y), false);
            this.motionPathLength = this.motionPathMeasure.getLength();
            this.scrimPaint.setStyle(Style.FILL);
            this.scrimPaint.setShader(TransitionUtils.createColorShader(i2));
            this.debugPaint.setStyle(Style.STROKE);
            this.debugPaint.setStrokeWidth(10.0f);
            updateProgress(0.0f);
        }

        public void draw(Canvas canvas) {
            if (this.scrimPaint.getAlpha() > 0) {
                canvas.drawRect(getBounds(), this.scrimPaint);
            }
            int save = this.drawDebugEnabled ? canvas.save() : -1;
            this.maskEvaluator.clip(canvas);
            if (this.containerPaint.getColor() != 0) {
                canvas.drawRect(getBounds(), this.containerPaint);
            }
            if (this.fadeModeResult.endOnTop) {
                drawStartView(canvas);
                drawEndView(canvas);
            } else {
                drawEndView(canvas);
                drawStartView(canvas);
            }
            if (this.drawDebugEnabled) {
                canvas.restoreToCount(save);
                drawDebugCumulativePath(canvas, this.currentStartBounds, this.debugPath, -65281);
                drawDebugRect(canvas, this.currentStartBoundsMasked, InputDeviceCompat.SOURCE_ANY);
                drawDebugRect(canvas, this.currentStartBounds, -16711936);
                drawDebugRect(canvas, this.currentEndBoundsMasked, -16711681);
                drawDebugRect(canvas, this.currentEndBounds, -16776961);
            }
        }

        private void drawStartView(Canvas canvas) {
            TransitionUtils.transform(canvas, getBounds(), this.currentStartBounds.left, this.currentStartBounds.top, this.fitModeResult.startScale, this.fadeModeResult.startAlpha, new CanvasOperation() {
                public void run(Canvas canvas) {
                    TransitionDrawable.this.startView.draw(canvas);
                }
            });
        }

        private void drawEndView(Canvas canvas) {
            TransitionUtils.transform(canvas, getBounds(), this.currentEndBounds.left, this.currentEndBounds.top, this.fitModeResult.endScale, this.fadeModeResult.endAlpha, new CanvasOperation() {
                public void run(Canvas canvas) {
                    TransitionDrawable.this.endView.draw(canvas);
                }
            });
        }

        public void setAlpha(int i) {
            throw new UnsupportedOperationException("Setting alpha on is not supported");
        }

        public void setColorFilter(ColorFilter colorFilter) {
            throw new UnsupportedOperationException("Setting a color filter is not supported");
        }

        /* access modifiers changed from: private */
        public void setProgress(float f) {
            if (this.progress != f) {
                updateProgress(f);
            }
        }

        private void updateProgress(float f) {
            this.progress = f;
            this.scrimPaint.setAlpha((int) (this.entering ? TransitionUtils.lerp(0.0f, 255.0f, f) : TransitionUtils.lerp(255.0f, 0.0f, f)));
            this.motionPathMeasure.getPosTan(this.motionPathLength * f, this.motionPathPosition, null);
            float[] fArr = this.motionPathPosition;
            float f2 = fArr[0];
            float f3 = fArr[1];
            this.fitModeResult = this.fitModeEvaluator.evaluate(f, ((Float) Preconditions.checkNotNull(Float.valueOf(this.progressThresholds.scale.start))).floatValue(), ((Float) Preconditions.checkNotNull(Float.valueOf(this.progressThresholds.scale.end))).floatValue(), this.startBounds.width(), this.startBounds.height(), this.endBounds.width(), this.endBounds.height());
            this.currentStartBounds.set(f2 - (this.fitModeResult.currentStartWidth / 2.0f), f3, (this.fitModeResult.currentStartWidth / 2.0f) + f2, this.fitModeResult.currentStartHeight + f3);
            this.currentEndBounds.set(f2 - (this.fitModeResult.currentEndWidth / 2.0f), f3, f2 + (this.fitModeResult.currentEndWidth / 2.0f), this.fitModeResult.currentEndHeight + f3);
            this.currentStartBoundsMasked.set(this.currentStartBounds);
            this.currentEndBoundsMasked.set(this.currentEndBounds);
            float floatValue = ((Float) Preconditions.checkNotNull(Float.valueOf(this.progressThresholds.scaleMask.start))).floatValue();
            float floatValue2 = ((Float) Preconditions.checkNotNull(Float.valueOf(this.progressThresholds.scaleMask.end))).floatValue();
            boolean shouldMaskStartBounds = this.fitModeEvaluator.shouldMaskStartBounds(this.fitModeResult);
            RectF rectF = shouldMaskStartBounds ? this.currentStartBoundsMasked : this.currentEndBoundsMasked;
            float lerp = TransitionUtils.lerp(0.0f, 1.0f, floatValue, floatValue2, f);
            if (!shouldMaskStartBounds) {
                lerp = 1.0f - lerp;
            }
            this.fitModeEvaluator.applyMask(rectF, lerp, this.fitModeResult);
            this.maskEvaluator.evaluate(f, this.startShapeAppearanceModel, this.endShapeAppearanceModel, this.currentStartBounds, this.currentStartBoundsMasked, this.currentEndBoundsMasked, this.progressThresholds.shapeMask);
            this.fadeModeResult = this.fadeModeEvaluator.evaluate(f, ((Float) Preconditions.checkNotNull(Float.valueOf(this.progressThresholds.fade.start))).floatValue(), ((Float) Preconditions.checkNotNull(Float.valueOf(this.progressThresholds.fade.end))).floatValue());
            invalidateSelf();
        }

        private static PointF getMotionPathPoint(RectF rectF) {
            return new PointF(rectF.centerX(), rectF.top);
        }

        private void drawDebugCumulativePath(Canvas canvas, RectF rectF, Path path, int i) {
            PointF motionPathPoint = getMotionPathPoint(rectF);
            if (this.progress == 0.0f) {
                path.reset();
                path.moveTo(motionPathPoint.x, motionPathPoint.y);
                return;
            }
            path.lineTo(motionPathPoint.x, motionPathPoint.y);
            this.debugPaint.setColor(i);
            canvas.drawPath(path, this.debugPaint);
        }

        private void drawDebugRect(Canvas canvas, RectF rectF, int i) {
            this.debugPaint.setColor(i);
            canvas.drawRect(rectF, this.debugPaint);
        }
    }

    static {
        ProgressThresholdsGroup progressThresholdsGroup = new ProgressThresholdsGroup(new ProgressThresholds(0.0f, 0.25f), new ProgressThresholds(0.0f, 1.0f), new ProgressThresholds(0.0f, 1.0f), new ProgressThresholds(0.0f, 0.75f));
        DEFAULT_ENTER_THRESHOLDS = progressThresholdsGroup;
        ProgressThresholdsGroup progressThresholdsGroup2 = new ProgressThresholdsGroup(new ProgressThresholds(0.6f, 0.9f), new ProgressThresholds(0.0f, 1.0f), new ProgressThresholds(0.0f, 0.9f), new ProgressThresholds(0.3f, 0.9f));
        DEFAULT_RETURN_THRESHOLDS = progressThresholdsGroup2;
        ProgressThresholdsGroup progressThresholdsGroup3 = new ProgressThresholdsGroup(new ProgressThresholds(0.1f, 0.4f), new ProgressThresholds(0.1f, 1.0f), new ProgressThresholds(0.1f, 1.0f), new ProgressThresholds(0.1f, 0.9f));
        DEFAULT_ENTER_THRESHOLDS_ARC = progressThresholdsGroup3;
        ProgressThresholdsGroup progressThresholdsGroup4 = new ProgressThresholdsGroup(new ProgressThresholds(0.6f, 0.9f), new ProgressThresholds(0.0f, 0.9f), new ProgressThresholds(0.0f, 0.9f), new ProgressThresholds(0.2f, 0.9f));
        DEFAULT_RETURN_THRESHOLDS_ARC = progressThresholdsGroup4;
    }

    public MaterialContainerTransform() {
        setInterpolator(AnimationUtils.FAST_OUT_SLOW_IN_INTERPOLATOR);
    }

    public int getStartViewId() {
        return this.startViewId;
    }

    public void setStartViewId(int i) {
        this.startViewId = i;
    }

    public int getEndViewId() {
        return this.endViewId;
    }

    public void setEndViewId(int i) {
        this.endViewId = i;
    }

    public View getStartView() {
        return this.startView;
    }

    public void setStartView(View view) {
        this.startView = view;
    }

    public View getEndView() {
        return this.endView;
    }

    public void setEndView(View view) {
        this.endView = view;
    }

    public ShapeAppearanceModel getStartShapeAppearanceModel() {
        return this.startShapeAppearanceModel;
    }

    public void setStartShapeAppearanceModel(ShapeAppearanceModel shapeAppearanceModel) {
        this.startShapeAppearanceModel = shapeAppearanceModel;
    }

    public ShapeAppearanceModel getEndShapeAppearanceModel() {
        return this.endShapeAppearanceModel;
    }

    public void setEndShapeAppearanceModel(ShapeAppearanceModel shapeAppearanceModel) {
        this.endShapeAppearanceModel = shapeAppearanceModel;
    }

    public int getDrawingViewId() {
        return this.drawingViewId;
    }

    public void setDrawingViewId(int i) {
        this.drawingViewId = i;
    }

    public int getContainerColor() {
        return this.containerColor;
    }

    public void setContainerColor(int i) {
        this.containerColor = i;
    }

    public int getScrimColor() {
        return this.scrimColor;
    }

    public void setScrimColor(int i) {
        this.scrimColor = i;
    }

    public int getTransitionDirection() {
        return this.transitionDirection;
    }

    public void setTransitionDirection(int i) {
        this.transitionDirection = i;
    }

    public int getFadeMode() {
        return this.fadeMode;
    }

    public void setFadeMode(int i) {
        this.fadeMode = i;
    }

    public int getFitMode() {
        return this.fitMode;
    }

    public void setFitMode(int i) {
        this.fitMode = i;
    }

    public ProgressThresholds getFadeProgressThresholds() {
        return this.fadeProgressThresholds;
    }

    public void setFadeProgressThresholds(ProgressThresholds progressThresholds) {
        this.fadeProgressThresholds = progressThresholds;
    }

    public ProgressThresholds getScaleProgressThresholds() {
        return this.scaleProgressThresholds;
    }

    public void setScaleProgressThresholds(ProgressThresholds progressThresholds) {
        this.scaleProgressThresholds = progressThresholds;
    }

    public ProgressThresholds getScaleMaskProgressThresholds() {
        return this.scaleMaskProgressThresholds;
    }

    public void setScaleMaskProgressThresholds(ProgressThresholds progressThresholds) {
        this.scaleMaskProgressThresholds = progressThresholds;
    }

    public ProgressThresholds getShapeMaskProgressThresholds() {
        return this.shapeMaskProgressThresholds;
    }

    public void setShapeMaskProgressThresholds(ProgressThresholds progressThresholds) {
        this.shapeMaskProgressThresholds = progressThresholds;
    }

    public boolean isHoldAtEndEnabled() {
        return this.holdAtEndEnabled;
    }

    public void setHoldAtEndEnabled(boolean z) {
        this.holdAtEndEnabled = z;
    }

    public boolean isDrawDebugEnabled() {
        return this.drawDebugEnabled;
    }

    public void setDrawDebugEnabled(boolean z) {
        this.drawDebugEnabled = z;
    }

    public String[] getTransitionProperties() {
        return TRANSITION_PROPS;
    }

    public void captureStartValues(TransitionValues transitionValues) {
        captureValues(transitionValues, this.startView, this.startViewId, this.startShapeAppearanceModel);
    }

    public void captureEndValues(TransitionValues transitionValues) {
        captureValues(transitionValues, this.endView, this.endViewId, this.endShapeAppearanceModel);
    }

    private static void captureValues(TransitionValues transitionValues, View view, int i, ShapeAppearanceModel shapeAppearanceModel) {
        if (i != -1) {
            transitionValues.view = TransitionUtils.findDescendantOrAncestorById(transitionValues.view, i);
        } else if (view != null) {
            transitionValues.view = view;
        } else if (transitionValues.view.getTag() instanceof View) {
            View view2 = (View) transitionValues.view.getTag();
            transitionValues.view.setTag(null);
            transitionValues.view = view2;
        }
        View view3 = transitionValues.view;
        if (ViewCompat.isLaidOut(view3) || view3.getWidth() != 0 || view3.getHeight() != 0) {
            RectF relativeBounds = view3.getParent() == null ? TransitionUtils.getRelativeBounds(view3) : TransitionUtils.getLocationOnScreen(view3);
            transitionValues.values.put(PROP_BOUNDS, relativeBounds);
            transitionValues.values.put(PROP_SHAPE_APPEARANCE, captureShapeAppearance(view3, relativeBounds, shapeAppearanceModel));
        }
    }

    private static ShapeAppearanceModel captureShapeAppearance(View view, RectF rectF, ShapeAppearanceModel shapeAppearanceModel) {
        return TransitionUtils.convertToRelativeCornerSizes(getShapeAppearance(view, shapeAppearanceModel), rectF);
    }

    private static ShapeAppearanceModel getShapeAppearance(View view, ShapeAppearanceModel shapeAppearanceModel) {
        if (shapeAppearanceModel != null) {
            return shapeAppearanceModel;
        }
        if (view.getTag() instanceof ShapeAppearanceModel) {
            return (ShapeAppearanceModel) view.getTag();
        }
        Context context = view.getContext();
        int transitionShapeAppearanceResId = getTransitionShapeAppearanceResId(context);
        if (transitionShapeAppearanceResId != -1) {
            return ShapeAppearanceModel.builder(context, transitionShapeAppearanceResId, 0).build();
        }
        if (view instanceof Shapeable) {
            return ((Shapeable) view).getShapeAppearanceModel();
        }
        return ShapeAppearanceModel.builder().build();
    }

    private static int getTransitionShapeAppearanceResId(Context context) {
        TypedArray obtainStyledAttributes = context.obtainStyledAttributes(new int[]{C2556R.attr.transitionShapeAppearance});
        int resourceId = obtainStyledAttributes.getResourceId(0, -1);
        obtainStyledAttributes.recycle();
        return resourceId;
    }

    public Animator createAnimator(ViewGroup viewGroup, TransitionValues transitionValues, TransitionValues transitionValues2) {
        final View view;
        TransitionValues transitionValues3 = transitionValues;
        TransitionValues transitionValues4 = transitionValues2;
        if (transitionValues3 == null || transitionValues4 == null) {
            return null;
        }
        final View view2 = transitionValues3.view;
        final View view3 = transitionValues4.view;
        View view4 = view3.getParent() != null ? view3 : view2;
        if (this.drawingViewId == view4.getId()) {
            view = (View) view4.getParent();
        } else {
            View findAncestorById = TransitionUtils.findAncestorById(view4, this.drawingViewId);
            view4 = null;
            view = findAncestorById;
        }
        Map map = transitionValues3.values;
        String str = PROP_BOUNDS;
        RectF rectF = (RectF) Preconditions.checkNotNull(map.get(str));
        RectF rectF2 = (RectF) Preconditions.checkNotNull(transitionValues4.values.get(str));
        Map map2 = transitionValues3.values;
        String str2 = PROP_SHAPE_APPEARANCE;
        ShapeAppearanceModel shapeAppearanceModel = (ShapeAppearanceModel) map2.get(str2);
        ShapeAppearanceModel shapeAppearanceModel2 = (ShapeAppearanceModel) transitionValues4.values.get(str2);
        RectF locationOnScreen = TransitionUtils.getLocationOnScreen(view);
        float f = -locationOnScreen.left;
        float f2 = -locationOnScreen.top;
        RectF calculateDrawableBounds = calculateDrawableBounds(view, view4, f, f2);
        rectF.offset(f, f2);
        rectF2.offset(f, f2);
        boolean isEntering = isEntering(rectF, rectF2);
        final TransitionDrawable transitionDrawable = new TransitionDrawable(getPathMotion(), view2, rectF, shapeAppearanceModel, view3, rectF2, shapeAppearanceModel2, this.containerColor, getScrimColorOrDefault(view2.getContext()), isEntering, FadeModeEvaluators.get(this.fadeMode, isEntering), FitModeEvaluators.get(this.fitMode, isEntering, rectF, rectF2), buildThresholdsGroup(isEntering), this.drawDebugEnabled);
        transitionDrawable.setBounds(Math.round(calculateDrawableBounds.left), Math.round(calculateDrawableBounds.top), Math.round(calculateDrawableBounds.right), Math.round(calculateDrawableBounds.bottom));
        ValueAnimator ofFloat = ValueAnimator.ofFloat(new float[]{0.0f, 1.0f});
        ofFloat.addUpdateListener(new AnimatorUpdateListener() {
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                transitionDrawable.setProgress(valueAnimator.getAnimatedFraction());
            }
        });
        final TransitionDrawable transitionDrawable2 = transitionDrawable;
        C27552 r0 = new AnimatorListenerAdapter() {
            public void onAnimationStart(Animator animator) {
                view.getOverlay().add(transitionDrawable2);
                view2.setAlpha(0.0f);
                view3.setAlpha(0.0f);
            }

            public void onAnimationEnd(Animator animator) {
                if (!MaterialContainerTransform.this.holdAtEndEnabled) {
                    view2.setAlpha(1.0f);
                    view3.setAlpha(1.0f);
                    view.getOverlay().remove(transitionDrawable2);
                }
            }
        };
        ofFloat.addListener(r0);
        return ofFloat;
    }

    private int getScrimColorOrDefault(Context context) {
        int i = this.scrimColor;
        return i == -1 ? MaterialColors.getColor(context, C2556R.attr.scrimBackground, ContextCompat.getColor(context, C2556R.C2557color.mtrl_scrim_color)) : i;
    }

    private static RectF calculateDrawableBounds(View view, View view2, float f, float f2) {
        if (view2 == null) {
            return new RectF(0.0f, 0.0f, (float) view.getWidth(), (float) view.getHeight());
        }
        RectF locationOnScreen = TransitionUtils.getLocationOnScreen(view2);
        locationOnScreen.offset(f, f2);
        return locationOnScreen;
    }

    private boolean isEntering(RectF rectF, RectF rectF2) {
        int i = this.transitionDirection;
        boolean z = false;
        if (i == 0) {
            if (TransitionUtils.calculateArea(rectF2) > TransitionUtils.calculateArea(rectF)) {
                z = true;
            }
            return z;
        } else if (i == 1) {
            return true;
        } else {
            if (i == 2) {
                return false;
            }
            StringBuilder sb = new StringBuilder();
            sb.append("Invalid transition direction: ");
            sb.append(this.transitionDirection);
            throw new IllegalArgumentException(sb.toString());
        }
    }

    private ProgressThresholdsGroup buildThresholdsGroup(boolean z) {
        PathMotion pathMotion = getPathMotion();
        if ((pathMotion instanceof ArcMotion) || (pathMotion instanceof MaterialArcMotion)) {
            return getThresholdsOrDefault(z, DEFAULT_ENTER_THRESHOLDS_ARC, DEFAULT_RETURN_THRESHOLDS_ARC);
        }
        return getThresholdsOrDefault(z, DEFAULT_ENTER_THRESHOLDS, DEFAULT_RETURN_THRESHOLDS);
    }

    private ProgressThresholdsGroup getThresholdsOrDefault(boolean z, ProgressThresholdsGroup progressThresholdsGroup, ProgressThresholdsGroup progressThresholdsGroup2) {
        if (!z) {
            progressThresholdsGroup = progressThresholdsGroup2;
        }
        ProgressThresholdsGroup progressThresholdsGroup3 = new ProgressThresholdsGroup((ProgressThresholds) TransitionUtils.defaultIfNull(this.fadeProgressThresholds, progressThresholdsGroup.fade), (ProgressThresholds) TransitionUtils.defaultIfNull(this.scaleProgressThresholds, progressThresholdsGroup.scale), (ProgressThresholds) TransitionUtils.defaultIfNull(this.scaleMaskProgressThresholds, progressThresholdsGroup.scaleMask), (ProgressThresholds) TransitionUtils.defaultIfNull(this.shapeMaskProgressThresholds, progressThresholdsGroup.shapeMask));
        return progressThresholdsGroup3;
    }
}
