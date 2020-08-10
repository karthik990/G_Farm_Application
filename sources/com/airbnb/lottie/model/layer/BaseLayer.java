package com.airbnb.lottie.model.layer;

import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.os.Build.VERSION;
import android.util.Log;
import com.airbnb.lottie.C0873L;
import com.airbnb.lottie.LottieComposition;
import com.airbnb.lottie.LottieDrawable;
import com.airbnb.lottie.animation.content.Content;
import com.airbnb.lottie.animation.content.DrawingContent;
import com.airbnb.lottie.animation.keyframe.BaseKeyframeAnimation;
import com.airbnb.lottie.animation.keyframe.BaseKeyframeAnimation.AnimationListener;
import com.airbnb.lottie.animation.keyframe.FloatKeyframeAnimation;
import com.airbnb.lottie.animation.keyframe.MaskKeyframeAnimation;
import com.airbnb.lottie.animation.keyframe.TransformKeyframeAnimation;
import com.airbnb.lottie.model.KeyPath;
import com.airbnb.lottie.model.KeyPathElement;
import com.airbnb.lottie.model.content.Mask;
import com.airbnb.lottie.model.content.Mask.MaskMode;
import com.airbnb.lottie.model.layer.Layer.MatteType;
import com.airbnb.lottie.value.LottieValueCallback;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public abstract class BaseLayer implements DrawingContent, AnimationListener, KeyPathElement {
    private static final int SAVE_FLAGS = 19;
    private static boolean hasLoggedIntersectMasks = false;
    private final Paint addMaskPaint = new Paint(1);
    private final List<BaseKeyframeAnimation<?, ?>> animations = new ArrayList();
    final Matrix boundsMatrix = new Matrix();
    private final Paint clearPaint = new Paint();
    private final Paint contentPaint = new Paint(1);
    private final String drawTraceName;
    final Layer layerModel;
    final LottieDrawable lottieDrawable;
    private MaskKeyframeAnimation mask;
    private final RectF maskBoundsRect = new RectF();
    private final Matrix matrix = new Matrix();
    private final RectF matteBoundsRect = new RectF();
    private BaseLayer matteLayer;
    private final Paint mattePaint = new Paint(1);
    private BaseLayer parentLayer;
    private List<BaseLayer> parentLayers;
    private final Path path = new Path();
    private final RectF rect = new RectF();
    private final Paint subtractMaskPaint = new Paint(1);
    private final RectF tempMaskBoundsRect = new RectF();
    final TransformKeyframeAnimation transform;
    private boolean visible = true;

    /* renamed from: com.airbnb.lottie.model.layer.BaseLayer$1 */
    static /* synthetic */ class C08891 {
        static final /* synthetic */ int[] $SwitchMap$com$airbnb$lottie$model$content$Mask$MaskMode = new int[MaskMode.values().length];

        /* JADX WARNING: Can't wrap try/catch for region: R(22:0|(2:1|2)|3|(2:5|6)|7|(2:9|10)|11|13|14|15|16|17|18|19|20|21|22|23|24|25|26|28) */
        /* JADX WARNING: Can't wrap try/catch for region: R(23:0|1|2|3|(2:5|6)|7|(2:9|10)|11|13|14|15|16|17|18|19|20|21|22|23|24|25|26|28) */
        /* JADX WARNING: Can't wrap try/catch for region: R(24:0|1|2|3|5|6|7|(2:9|10)|11|13|14|15|16|17|18|19|20|21|22|23|24|25|26|28) */
        /* JADX WARNING: Code restructure failed: missing block: B:29:?, code lost:
            return;
         */
        /* JADX WARNING: Failed to process nested try/catch */
        /* JADX WARNING: Missing exception handler attribute for start block: B:15:0x003d */
        /* JADX WARNING: Missing exception handler attribute for start block: B:17:0x0047 */
        /* JADX WARNING: Missing exception handler attribute for start block: B:19:0x0051 */
        /* JADX WARNING: Missing exception handler attribute for start block: B:21:0x005c */
        /* JADX WARNING: Missing exception handler attribute for start block: B:23:0x0067 */
        /* JADX WARNING: Missing exception handler attribute for start block: B:25:0x0072 */
        static {
            /*
                com.airbnb.lottie.model.content.Mask$MaskMode[] r0 = com.airbnb.lottie.model.content.Mask.MaskMode.values()
                int r0 = r0.length
                int[] r0 = new int[r0]
                $SwitchMap$com$airbnb$lottie$model$content$Mask$MaskMode = r0
                r0 = 1
                int[] r1 = $SwitchMap$com$airbnb$lottie$model$content$Mask$MaskMode     // Catch:{ NoSuchFieldError -> 0x0014 }
                com.airbnb.lottie.model.content.Mask$MaskMode r2 = com.airbnb.lottie.model.content.Mask.MaskMode.MaskModeSubtract     // Catch:{ NoSuchFieldError -> 0x0014 }
                int r2 = r2.ordinal()     // Catch:{ NoSuchFieldError -> 0x0014 }
                r1[r2] = r0     // Catch:{ NoSuchFieldError -> 0x0014 }
            L_0x0014:
                r1 = 2
                int[] r2 = $SwitchMap$com$airbnb$lottie$model$content$Mask$MaskMode     // Catch:{ NoSuchFieldError -> 0x001f }
                com.airbnb.lottie.model.content.Mask$MaskMode r3 = com.airbnb.lottie.model.content.Mask.MaskMode.MaskModeIntersect     // Catch:{ NoSuchFieldError -> 0x001f }
                int r3 = r3.ordinal()     // Catch:{ NoSuchFieldError -> 0x001f }
                r2[r3] = r1     // Catch:{ NoSuchFieldError -> 0x001f }
            L_0x001f:
                r2 = 3
                int[] r3 = $SwitchMap$com$airbnb$lottie$model$content$Mask$MaskMode     // Catch:{ NoSuchFieldError -> 0x002a }
                com.airbnb.lottie.model.content.Mask$MaskMode r4 = com.airbnb.lottie.model.content.Mask.MaskMode.MaskModeAdd     // Catch:{ NoSuchFieldError -> 0x002a }
                int r4 = r4.ordinal()     // Catch:{ NoSuchFieldError -> 0x002a }
                r3[r4] = r2     // Catch:{ NoSuchFieldError -> 0x002a }
            L_0x002a:
                com.airbnb.lottie.model.layer.Layer$LayerType[] r3 = com.airbnb.lottie.model.layer.Layer.LayerType.values()
                int r3 = r3.length
                int[] r3 = new int[r3]
                $SwitchMap$com$airbnb$lottie$model$layer$Layer$LayerType = r3
                int[] r3 = $SwitchMap$com$airbnb$lottie$model$layer$Layer$LayerType     // Catch:{ NoSuchFieldError -> 0x003d }
                com.airbnb.lottie.model.layer.Layer$LayerType r4 = com.airbnb.lottie.model.layer.Layer.LayerType.Shape     // Catch:{ NoSuchFieldError -> 0x003d }
                int r4 = r4.ordinal()     // Catch:{ NoSuchFieldError -> 0x003d }
                r3[r4] = r0     // Catch:{ NoSuchFieldError -> 0x003d }
            L_0x003d:
                int[] r0 = $SwitchMap$com$airbnb$lottie$model$layer$Layer$LayerType     // Catch:{ NoSuchFieldError -> 0x0047 }
                com.airbnb.lottie.model.layer.Layer$LayerType r3 = com.airbnb.lottie.model.layer.Layer.LayerType.PreComp     // Catch:{ NoSuchFieldError -> 0x0047 }
                int r3 = r3.ordinal()     // Catch:{ NoSuchFieldError -> 0x0047 }
                r0[r3] = r1     // Catch:{ NoSuchFieldError -> 0x0047 }
            L_0x0047:
                int[] r0 = $SwitchMap$com$airbnb$lottie$model$layer$Layer$LayerType     // Catch:{ NoSuchFieldError -> 0x0051 }
                com.airbnb.lottie.model.layer.Layer$LayerType r1 = com.airbnb.lottie.model.layer.Layer.LayerType.Solid     // Catch:{ NoSuchFieldError -> 0x0051 }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x0051 }
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x0051 }
            L_0x0051:
                int[] r0 = $SwitchMap$com$airbnb$lottie$model$layer$Layer$LayerType     // Catch:{ NoSuchFieldError -> 0x005c }
                com.airbnb.lottie.model.layer.Layer$LayerType r1 = com.airbnb.lottie.model.layer.Layer.LayerType.Image     // Catch:{ NoSuchFieldError -> 0x005c }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x005c }
                r2 = 4
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x005c }
            L_0x005c:
                int[] r0 = $SwitchMap$com$airbnb$lottie$model$layer$Layer$LayerType     // Catch:{ NoSuchFieldError -> 0x0067 }
                com.airbnb.lottie.model.layer.Layer$LayerType r1 = com.airbnb.lottie.model.layer.Layer.LayerType.Null     // Catch:{ NoSuchFieldError -> 0x0067 }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x0067 }
                r2 = 5
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x0067 }
            L_0x0067:
                int[] r0 = $SwitchMap$com$airbnb$lottie$model$layer$Layer$LayerType     // Catch:{ NoSuchFieldError -> 0x0072 }
                com.airbnb.lottie.model.layer.Layer$LayerType r1 = com.airbnb.lottie.model.layer.Layer.LayerType.Text     // Catch:{ NoSuchFieldError -> 0x0072 }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x0072 }
                r2 = 6
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x0072 }
            L_0x0072:
                int[] r0 = $SwitchMap$com$airbnb$lottie$model$layer$Layer$LayerType     // Catch:{ NoSuchFieldError -> 0x007d }
                com.airbnb.lottie.model.layer.Layer$LayerType r1 = com.airbnb.lottie.model.layer.Layer.LayerType.Unknown     // Catch:{ NoSuchFieldError -> 0x007d }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x007d }
                r2 = 7
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x007d }
            L_0x007d:
                return
            */
            throw new UnsupportedOperationException("Method not decompiled: com.airbnb.lottie.model.layer.BaseLayer.C08891.<clinit>():void");
        }
    }

    /* access modifiers changed from: 0000 */
    public abstract void drawLayer(Canvas canvas, Matrix matrix2, int i);

    /* access modifiers changed from: 0000 */
    public void resolveChildKeyPath(KeyPath keyPath, int i, List<KeyPath> list, KeyPath keyPath2) {
    }

    public void setContents(List<Content> list, List<Content> list2) {
    }

    static BaseLayer forModel(Layer layer, LottieDrawable lottieDrawable2, LottieComposition lottieComposition) {
        switch (layer.getLayerType()) {
            case Shape:
                return new ShapeLayer(lottieDrawable2, layer);
            case PreComp:
                return new CompositionLayer(lottieDrawable2, layer, lottieComposition.getPrecomps(layer.getRefId()), lottieComposition);
            case Solid:
                return new SolidLayer(lottieDrawable2, layer);
            case Image:
                return new ImageLayer(lottieDrawable2, layer);
            case Null:
                return new NullLayer(lottieDrawable2, layer);
            case Text:
                return new TextLayer(lottieDrawable2, layer);
            default:
                StringBuilder sb = new StringBuilder();
                sb.append("Unknown layer type ");
                sb.append(layer.getLayerType());
                C0873L.warn(sb.toString());
                return null;
        }
    }

    BaseLayer(LottieDrawable lottieDrawable2, Layer layer) {
        this.lottieDrawable = lottieDrawable2;
        this.layerModel = layer;
        StringBuilder sb = new StringBuilder();
        sb.append(layer.getName());
        sb.append("#draw");
        this.drawTraceName = sb.toString();
        this.clearPaint.setXfermode(new PorterDuffXfermode(Mode.CLEAR));
        this.addMaskPaint.setXfermode(new PorterDuffXfermode(Mode.DST_IN));
        this.subtractMaskPaint.setXfermode(new PorterDuffXfermode(Mode.DST_OUT));
        if (layer.getMatteType() == MatteType.Invert) {
            this.mattePaint.setXfermode(new PorterDuffXfermode(Mode.DST_OUT));
        } else {
            this.mattePaint.setXfermode(new PorterDuffXfermode(Mode.DST_IN));
        }
        this.transform = layer.getTransform().createAnimation();
        this.transform.addListener(this);
        if (layer.getMasks() != null && !layer.getMasks().isEmpty()) {
            this.mask = new MaskKeyframeAnimation(layer.getMasks());
            for (BaseKeyframeAnimation baseKeyframeAnimation : this.mask.getMaskAnimations()) {
                addAnimation(baseKeyframeAnimation);
                baseKeyframeAnimation.addUpdateListener(this);
            }
            for (BaseKeyframeAnimation baseKeyframeAnimation2 : this.mask.getOpacityAnimations()) {
                addAnimation(baseKeyframeAnimation2);
                baseKeyframeAnimation2.addUpdateListener(this);
            }
        }
        setupInOutAnimations();
    }

    public void onValueChanged() {
        invalidateSelf();
    }

    /* access modifiers changed from: 0000 */
    public Layer getLayerModel() {
        return this.layerModel;
    }

    /* access modifiers changed from: 0000 */
    public void setMatteLayer(BaseLayer baseLayer) {
        this.matteLayer = baseLayer;
    }

    /* access modifiers changed from: 0000 */
    public boolean hasMatteOnThisLayer() {
        return this.matteLayer != null;
    }

    /* access modifiers changed from: 0000 */
    public void setParentLayer(BaseLayer baseLayer) {
        this.parentLayer = baseLayer;
    }

    private void setupInOutAnimations() {
        boolean z = true;
        if (!this.layerModel.getInOutKeyframes().isEmpty()) {
            FloatKeyframeAnimation floatKeyframeAnimation = new FloatKeyframeAnimation(this.layerModel.getInOutKeyframes());
            floatKeyframeAnimation.setIsDiscrete();
            floatKeyframeAnimation.addUpdateListener(new AnimationListener(floatKeyframeAnimation) {
                private final /* synthetic */ FloatKeyframeAnimation f$1;

                {
                    this.f$1 = r2;
                }

                public final void onValueChanged() {
                    BaseLayer.this.lambda$setupInOutAnimations$0$BaseLayer(this.f$1);
                }
            });
            if (((Float) floatKeyframeAnimation.getValue()).floatValue() != 1.0f) {
                z = false;
            }
            setVisible(z);
            addAnimation(floatKeyframeAnimation);
            return;
        }
        setVisible(true);
    }

    public /* synthetic */ void lambda$setupInOutAnimations$0$BaseLayer(FloatKeyframeAnimation floatKeyframeAnimation) {
        setVisible(((Float) floatKeyframeAnimation.getValue()).floatValue() == 1.0f);
    }

    private void invalidateSelf() {
        this.lottieDrawable.invalidateSelf();
    }

    private void saveLayerCompat(Canvas canvas, RectF rectF, Paint paint) {
        if (VERSION.SDK_INT < 23) {
            canvas.saveLayer(rectF, paint, 19);
        } else {
            canvas.saveLayer(rectF, paint);
        }
    }

    public void addAnimation(BaseKeyframeAnimation<?, ?> baseKeyframeAnimation) {
        this.animations.add(baseKeyframeAnimation);
    }

    public void getBounds(RectF rectF, Matrix matrix2) {
        this.boundsMatrix.set(matrix2);
        this.boundsMatrix.preConcat(this.transform.getMatrix());
    }

    public void draw(Canvas canvas, Matrix matrix2, int i) {
        C0873L.beginSection(this.drawTraceName);
        if (!this.visible) {
            C0873L.endSection(this.drawTraceName);
            return;
        }
        buildParentLayerListIfNeeded();
        String str = "Layer#parentMatrix";
        C0873L.beginSection(str);
        this.matrix.reset();
        this.matrix.set(matrix2);
        for (int size = this.parentLayers.size() - 1; size >= 0; size--) {
            this.matrix.preConcat(((BaseLayer) this.parentLayers.get(size)).transform.getMatrix());
        }
        C0873L.endSection(str);
        int intValue = (int) ((((((float) i) / 255.0f) * ((float) ((Integer) this.transform.getOpacity().getValue()).intValue())) / 100.0f) * 255.0f);
        String str2 = "Layer#drawLayer";
        if (hasMatteOnThisLayer() || hasMasksOnThisLayer()) {
            String str3 = "Layer#computeBounds";
            C0873L.beginSection(str3);
            this.rect.set(0.0f, 0.0f, 0.0f, 0.0f);
            getBounds(this.rect, this.matrix);
            intersectBoundsWithMatte(this.rect, this.matrix);
            this.matrix.preConcat(this.transform.getMatrix());
            intersectBoundsWithMask(this.rect, this.matrix);
            this.rect.set(0.0f, 0.0f, (float) canvas.getWidth(), (float) canvas.getHeight());
            C0873L.endSection(str3);
            String str4 = "Layer#saveLayer";
            C0873L.beginSection(str4);
            saveLayerCompat(canvas, this.rect, this.contentPaint);
            C0873L.endSection(str4);
            clearCanvas(canvas);
            C0873L.beginSection(str2);
            drawLayer(canvas, this.matrix, intValue);
            C0873L.endSection(str2);
            if (hasMasksOnThisLayer()) {
                applyMasks(canvas, this.matrix);
            }
            String str5 = "Layer#restoreLayer";
            if (hasMatteOnThisLayer()) {
                String str6 = "Layer#drawMatte";
                C0873L.beginSection(str6);
                C0873L.beginSection(str4);
                saveLayerCompat(canvas, this.rect, this.mattePaint);
                C0873L.endSection(str4);
                clearCanvas(canvas);
                this.matteLayer.draw(canvas, matrix2, intValue);
                C0873L.beginSection(str5);
                canvas.restore();
                C0873L.endSection(str5);
                C0873L.endSection(str6);
            }
            C0873L.beginSection(str5);
            canvas.restore();
            C0873L.endSection(str5);
            recordRenderTime(C0873L.endSection(this.drawTraceName));
            return;
        }
        this.matrix.preConcat(this.transform.getMatrix());
        C0873L.beginSection(str2);
        drawLayer(canvas, this.matrix, intValue);
        C0873L.endSection(str2);
        recordRenderTime(C0873L.endSection(this.drawTraceName));
    }

    private void recordRenderTime(float f) {
        this.lottieDrawable.getComposition().getPerformanceTracker().recordRenderTime(this.layerModel.getName(), f);
    }

    private void clearCanvas(Canvas canvas) {
        String str = "Layer#clearLayer";
        C0873L.beginSection(str);
        canvas.drawRect(this.rect.left - 1.0f, this.rect.top - 1.0f, this.rect.right + 1.0f, this.rect.bottom + 1.0f, this.clearPaint);
        C0873L.endSection(str);
    }

    private void intersectBoundsWithMask(RectF rectF, Matrix matrix2) {
        this.maskBoundsRect.set(0.0f, 0.0f, 0.0f, 0.0f);
        if (hasMasksOnThisLayer()) {
            int size = this.mask.getMasks().size();
            int i = 0;
            while (i < size) {
                Mask mask2 = (Mask) this.mask.getMasks().get(i);
                this.path.set((Path) ((BaseKeyframeAnimation) this.mask.getMaskAnimations().get(i)).getValue());
                this.path.transform(matrix2);
                int i2 = C08891.$SwitchMap$com$airbnb$lottie$model$content$Mask$MaskMode[mask2.getMaskMode().ordinal()];
                if (i2 != 1 && i2 != 2) {
                    this.path.computeBounds(this.tempMaskBoundsRect, false);
                    if (i == 0) {
                        this.maskBoundsRect.set(this.tempMaskBoundsRect);
                    } else {
                        RectF rectF2 = this.maskBoundsRect;
                        rectF2.set(Math.min(rectF2.left, this.tempMaskBoundsRect.left), Math.min(this.maskBoundsRect.top, this.tempMaskBoundsRect.top), Math.max(this.maskBoundsRect.right, this.tempMaskBoundsRect.right), Math.max(this.maskBoundsRect.bottom, this.tempMaskBoundsRect.bottom));
                    }
                    i++;
                } else {
                    return;
                }
            }
            rectF.set(Math.max(rectF.left, this.maskBoundsRect.left), Math.max(rectF.top, this.maskBoundsRect.top), Math.min(rectF.right, this.maskBoundsRect.right), Math.min(rectF.bottom, this.maskBoundsRect.bottom));
        }
    }

    private void intersectBoundsWithMatte(RectF rectF, Matrix matrix2) {
        if (hasMatteOnThisLayer() && this.layerModel.getMatteType() != MatteType.Invert) {
            this.matteLayer.getBounds(this.matteBoundsRect, matrix2);
            rectF.set(Math.max(rectF.left, this.matteBoundsRect.left), Math.max(rectF.top, this.matteBoundsRect.top), Math.min(rectF.right, this.matteBoundsRect.right), Math.min(rectF.bottom, this.matteBoundsRect.bottom));
        }
    }

    private void applyMasks(Canvas canvas, Matrix matrix2) {
        applyMasks(canvas, matrix2, MaskMode.MaskModeAdd);
        applyMasks(canvas, matrix2, MaskMode.MaskModeIntersect);
        applyMasks(canvas, matrix2, MaskMode.MaskModeSubtract);
    }

    private void applyMasks(Canvas canvas, Matrix matrix2, MaskMode maskMode) {
        Paint paint;
        int i = C08891.$SwitchMap$com$airbnb$lottie$model$content$Mask$MaskMode[maskMode.ordinal()];
        boolean z = true;
        if (i != 1) {
            if (i == 2 && !hasLoggedIntersectMasks) {
                Log.w(C0873L.TAG, "Animation contains intersect masks. They are not supported but will be treated like add masks.");
                hasLoggedIntersectMasks = true;
            }
            paint = this.addMaskPaint;
        } else {
            paint = this.subtractMaskPaint;
        }
        int size = this.mask.getMasks().size();
        int i2 = 0;
        while (true) {
            if (i2 >= size) {
                z = false;
                break;
            } else if (((Mask) this.mask.getMasks().get(i2)).getMaskMode() == maskMode) {
                break;
            } else {
                i2++;
            }
        }
        if (z) {
            String str = "Layer#drawMask";
            C0873L.beginSection(str);
            String str2 = "Layer#saveLayer";
            C0873L.beginSection(str2);
            saveLayerCompat(canvas, this.rect, paint);
            C0873L.endSection(str2);
            clearCanvas(canvas);
            for (int i3 = 0; i3 < size; i3++) {
                if (((Mask) this.mask.getMasks().get(i3)).getMaskMode() == maskMode) {
                    this.path.set((Path) ((BaseKeyframeAnimation) this.mask.getMaskAnimations().get(i3)).getValue());
                    this.path.transform(matrix2);
                    BaseKeyframeAnimation baseKeyframeAnimation = (BaseKeyframeAnimation) this.mask.getOpacityAnimations().get(i3);
                    int alpha = this.contentPaint.getAlpha();
                    this.contentPaint.setAlpha((int) (((float) ((Integer) baseKeyframeAnimation.getValue()).intValue()) * 2.55f));
                    canvas.drawPath(this.path, this.contentPaint);
                    this.contentPaint.setAlpha(alpha);
                }
            }
            String str3 = "Layer#restoreLayer";
            C0873L.beginSection(str3);
            canvas.restore();
            C0873L.endSection(str3);
            C0873L.endSection(str);
        }
    }

    /* access modifiers changed from: 0000 */
    public boolean hasMasksOnThisLayer() {
        MaskKeyframeAnimation maskKeyframeAnimation = this.mask;
        return maskKeyframeAnimation != null && !maskKeyframeAnimation.getMaskAnimations().isEmpty();
    }

    private void setVisible(boolean z) {
        if (z != this.visible) {
            this.visible = z;
            invalidateSelf();
        }
    }

    /* access modifiers changed from: 0000 */
    public void setProgress(float f) {
        this.transform.setProgress(f);
        if (this.layerModel.getTimeStretch() != 0.0f) {
            f /= this.layerModel.getTimeStretch();
        }
        BaseLayer baseLayer = this.matteLayer;
        if (baseLayer != null) {
            this.matteLayer.setProgress(baseLayer.layerModel.getTimeStretch() * f);
        }
        for (int i = 0; i < this.animations.size(); i++) {
            ((BaseKeyframeAnimation) this.animations.get(i)).setProgress(f);
        }
    }

    private void buildParentLayerListIfNeeded() {
        if (this.parentLayers == null) {
            if (this.parentLayer == null) {
                this.parentLayers = Collections.emptyList();
                return;
            }
            this.parentLayers = new ArrayList();
            for (BaseLayer baseLayer = this.parentLayer; baseLayer != null; baseLayer = baseLayer.parentLayer) {
                this.parentLayers.add(baseLayer);
            }
        }
    }

    public String getName() {
        return this.layerModel.getName();
    }

    public void resolveKeyPath(KeyPath keyPath, int i, List<KeyPath> list, KeyPath keyPath2) {
        if (keyPath.matches(getName(), i)) {
            if (!"__container".equals(getName())) {
                keyPath2 = keyPath2.addKey(getName());
                if (keyPath.fullyResolvesTo(getName(), i)) {
                    list.add(keyPath2.resolve(this));
                }
            }
            if (keyPath.propagateToChildren(getName(), i)) {
                resolveChildKeyPath(keyPath, i + keyPath.incrementDepthBy(getName(), i), list, keyPath2);
            }
        }
    }

    public <T> void addValueCallback(T t, LottieValueCallback<T> lottieValueCallback) {
        this.transform.applyValueCallback(t, lottieValueCallback);
    }
}
