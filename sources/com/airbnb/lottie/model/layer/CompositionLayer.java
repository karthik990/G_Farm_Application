package com.airbnb.lottie.model.layer;

import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.RectF;
import androidx.collection.LongSparseArray;
import com.airbnb.lottie.C0873L;
import com.airbnb.lottie.LottieComposition;
import com.airbnb.lottie.LottieDrawable;
import com.airbnb.lottie.LottieProperty;
import com.airbnb.lottie.animation.keyframe.BaseKeyframeAnimation;
import com.airbnb.lottie.animation.keyframe.ValueCallbackKeyframeAnimation;
import com.airbnb.lottie.model.KeyPath;
import com.airbnb.lottie.model.animatable.AnimatableFloatValue;
import com.airbnb.lottie.model.layer.Layer.MatteType;
import com.airbnb.lottie.value.LottieValueCallback;
import java.util.ArrayList;
import java.util.List;

public class CompositionLayer extends BaseLayer {
    private Boolean hasMasks;
    private Boolean hasMatte;
    private final List<BaseLayer> layers = new ArrayList();
    private final RectF newClipRect = new RectF();
    private final RectF rect = new RectF();
    private BaseKeyframeAnimation<Float, Float> timeRemapping;

    /* renamed from: com.airbnb.lottie.model.layer.CompositionLayer$1 */
    static /* synthetic */ class C08901 {
        static final /* synthetic */ int[] $SwitchMap$com$airbnb$lottie$model$layer$Layer$MatteType = new int[MatteType.values().length];

        /* JADX WARNING: Can't wrap try/catch for region: R(6:0|1|2|3|4|6) */
        /* JADX WARNING: Code restructure failed: missing block: B:7:?, code lost:
            return;
         */
        /* JADX WARNING: Failed to process nested try/catch */
        /* JADX WARNING: Missing exception handler attribute for start block: B:3:0x0014 */
        static {
            /*
                com.airbnb.lottie.model.layer.Layer$MatteType[] r0 = com.airbnb.lottie.model.layer.Layer.MatteType.values()
                int r0 = r0.length
                int[] r0 = new int[r0]
                $SwitchMap$com$airbnb$lottie$model$layer$Layer$MatteType = r0
                int[] r0 = $SwitchMap$com$airbnb$lottie$model$layer$Layer$MatteType     // Catch:{ NoSuchFieldError -> 0x0014 }
                com.airbnb.lottie.model.layer.Layer$MatteType r1 = com.airbnb.lottie.model.layer.Layer.MatteType.Add     // Catch:{ NoSuchFieldError -> 0x0014 }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x0014 }
                r2 = 1
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x0014 }
            L_0x0014:
                int[] r0 = $SwitchMap$com$airbnb$lottie$model$layer$Layer$MatteType     // Catch:{ NoSuchFieldError -> 0x001f }
                com.airbnb.lottie.model.layer.Layer$MatteType r1 = com.airbnb.lottie.model.layer.Layer.MatteType.Invert     // Catch:{ NoSuchFieldError -> 0x001f }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x001f }
                r2 = 2
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x001f }
            L_0x001f:
                return
            */
            throw new UnsupportedOperationException("Method not decompiled: com.airbnb.lottie.model.layer.CompositionLayer.C08901.<clinit>():void");
        }
    }

    public CompositionLayer(LottieDrawable lottieDrawable, Layer layer, List<Layer> list, LottieComposition lottieComposition) {
        int i;
        super(lottieDrawable, layer);
        AnimatableFloatValue timeRemapping2 = layer.getTimeRemapping();
        if (timeRemapping2 != null) {
            this.timeRemapping = timeRemapping2.createAnimation();
            addAnimation(this.timeRemapping);
            this.timeRemapping.addUpdateListener(this);
        } else {
            this.timeRemapping = null;
        }
        LongSparseArray longSparseArray = new LongSparseArray(lottieComposition.getLayers().size());
        int size = list.size() - 1;
        BaseLayer baseLayer = null;
        while (true) {
            if (size < 0) {
                break;
            }
            Layer layer2 = (Layer) list.get(size);
            BaseLayer forModel = BaseLayer.forModel(layer2, lottieDrawable, lottieComposition);
            if (forModel != null) {
                longSparseArray.put(forModel.getLayerModel().getId(), forModel);
                if (baseLayer != null) {
                    baseLayer.setMatteLayer(forModel);
                    baseLayer = null;
                } else {
                    this.layers.add(0, forModel);
                    int i2 = C08901.$SwitchMap$com$airbnb$lottie$model$layer$Layer$MatteType[layer2.getMatteType().ordinal()];
                    if (i2 == 1 || i2 == 2) {
                        baseLayer = forModel;
                    }
                }
            }
            size--;
        }
        for (i = 0; i < longSparseArray.size(); i++) {
            BaseLayer baseLayer2 = (BaseLayer) longSparseArray.get(longSparseArray.keyAt(i));
            if (baseLayer2 != null) {
                BaseLayer baseLayer3 = (BaseLayer) longSparseArray.get(baseLayer2.getLayerModel().getParentId());
                if (baseLayer3 != null) {
                    baseLayer2.setParentLayer(baseLayer3);
                }
            }
        }
    }

    /* access modifiers changed from: 0000 */
    public void drawLayer(Canvas canvas, Matrix matrix, int i) {
        String str = "CompositionLayer#draw";
        C0873L.beginSection(str);
        canvas.save();
        this.newClipRect.set(0.0f, 0.0f, (float) this.layerModel.getPreCompWidth(), (float) this.layerModel.getPreCompHeight());
        matrix.mapRect(this.newClipRect);
        for (int size = this.layers.size() - 1; size >= 0; size--) {
            if (!this.newClipRect.isEmpty() ? canvas.clipRect(this.newClipRect) : true) {
                ((BaseLayer) this.layers.get(size)).draw(canvas, matrix, i);
            }
        }
        canvas.restore();
        C0873L.endSection(str);
    }

    public void getBounds(RectF rectF, Matrix matrix) {
        super.getBounds(rectF, matrix);
        this.rect.set(0.0f, 0.0f, 0.0f, 0.0f);
        for (int size = this.layers.size() - 1; size >= 0; size--) {
            ((BaseLayer) this.layers.get(size)).getBounds(this.rect, this.boundsMatrix);
            if (rectF.isEmpty()) {
                rectF.set(this.rect);
            } else {
                rectF.set(Math.min(rectF.left, this.rect.left), Math.min(rectF.top, this.rect.top), Math.max(rectF.right, this.rect.right), Math.max(rectF.bottom, this.rect.bottom));
            }
        }
    }

    public void setProgress(float f) {
        super.setProgress(f);
        if (this.timeRemapping != null) {
            f = ((float) ((long) (((Float) this.timeRemapping.getValue()).floatValue() * 1000.0f))) / this.lottieDrawable.getComposition().getDuration();
        }
        if (this.layerModel.getTimeStretch() != 0.0f) {
            f /= this.layerModel.getTimeStretch();
        }
        float startProgress = f - this.layerModel.getStartProgress();
        for (int size = this.layers.size() - 1; size >= 0; size--) {
            ((BaseLayer) this.layers.get(size)).setProgress(startProgress);
        }
    }

    public boolean hasMasks() {
        if (this.hasMasks == null) {
            for (int size = this.layers.size() - 1; size >= 0; size--) {
                BaseLayer baseLayer = (BaseLayer) this.layers.get(size);
                if (baseLayer instanceof ShapeLayer) {
                    if (baseLayer.hasMasksOnThisLayer()) {
                        this.hasMasks = Boolean.valueOf(true);
                        return true;
                    }
                } else if ((baseLayer instanceof CompositionLayer) && ((CompositionLayer) baseLayer).hasMasks()) {
                    this.hasMasks = Boolean.valueOf(true);
                    return true;
                }
            }
            this.hasMasks = Boolean.valueOf(false);
        }
        return this.hasMasks.booleanValue();
    }

    public boolean hasMatte() {
        if (this.hasMatte == null) {
            if (hasMatteOnThisLayer()) {
                this.hasMatte = Boolean.valueOf(true);
                return true;
            }
            for (int size = this.layers.size() - 1; size >= 0; size--) {
                if (((BaseLayer) this.layers.get(size)).hasMatteOnThisLayer()) {
                    this.hasMatte = Boolean.valueOf(true);
                    return true;
                }
            }
            this.hasMatte = Boolean.valueOf(false);
        }
        return this.hasMatte.booleanValue();
    }

    /* access modifiers changed from: protected */
    public void resolveChildKeyPath(KeyPath keyPath, int i, List<KeyPath> list, KeyPath keyPath2) {
        for (int i2 = 0; i2 < this.layers.size(); i2++) {
            ((BaseLayer) this.layers.get(i2)).resolveKeyPath(keyPath, i, list, keyPath2);
        }
    }

    public <T> void addValueCallback(T t, LottieValueCallback<T> lottieValueCallback) {
        super.addValueCallback(t, lottieValueCallback);
        if (t != LottieProperty.TIME_REMAP) {
            return;
        }
        if (lottieValueCallback == null) {
            this.timeRemapping = null;
            return;
        }
        this.timeRemapping = new ValueCallbackKeyframeAnimation(lottieValueCallback);
        addAnimation(this.timeRemapping);
    }
}
