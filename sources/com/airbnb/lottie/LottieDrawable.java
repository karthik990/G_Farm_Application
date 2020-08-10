package com.airbnb.lottie;

import android.animation.Animator.AnimatorListener;
import android.animation.ValueAnimator;
import android.animation.ValueAnimator.AnimatorUpdateListener;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Matrix;
import android.graphics.Typeface;
import android.graphics.drawable.Animatable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.Drawable.Callback;
import android.os.Build.VERSION;
import android.util.Log;
import android.view.View;
import com.airbnb.lottie.manager.FontAssetManager;
import com.airbnb.lottie.manager.ImageAssetManager;
import com.airbnb.lottie.model.KeyPath;
import com.airbnb.lottie.model.layer.CompositionLayer;
import com.airbnb.lottie.parser.LayerParser;
import com.airbnb.lottie.utils.LottieValueAnimator;
import com.airbnb.lottie.utils.MiscUtils;
import com.airbnb.lottie.value.LottieFrameInfo;
import com.airbnb.lottie.value.LottieValueCallback;
import com.airbnb.lottie.value.SimpleLottieValueCallback;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

public class LottieDrawable extends Drawable implements Callback, Animatable {
    public static final int INFINITE = -1;
    public static final int RESTART = 1;
    public static final int REVERSE = 2;
    private static final String TAG = LottieDrawable.class.getSimpleName();
    private int alpha = 255;
    /* access modifiers changed from: private */
    public final LottieValueAnimator animator = new LottieValueAnimator();
    private final Set<ColorFilterData> colorFilterData = new HashSet();
    private LottieComposition composition;
    /* access modifiers changed from: private */
    public CompositionLayer compositionLayer;
    private boolean enableMergePaths;
    FontAssetDelegate fontAssetDelegate;
    private FontAssetManager fontAssetManager;
    private ImageAssetDelegate imageAssetDelegate;
    private ImageAssetManager imageAssetManager;
    private String imageAssetsFolder;
    private final ArrayList<LazyCompositionTask> lazyCompositionTasks = new ArrayList<>();
    private final Matrix matrix = new Matrix();
    private boolean performanceTrackingEnabled;
    private float scale = 1.0f;
    TextDelegate textDelegate;

    private static class ColorFilterData {
        final ColorFilter colorFilter;
        final String contentName;
        final String layerName;

        ColorFilterData(String str, String str2, ColorFilter colorFilter2) {
            this.layerName = str;
            this.contentName = str2;
            this.colorFilter = colorFilter2;
        }

        public int hashCode() {
            String str = this.layerName;
            int hashCode = str != null ? 527 * str.hashCode() : 17;
            String str2 = this.contentName;
            return str2 != null ? hashCode * 31 * str2.hashCode() : hashCode;
        }

        public boolean equals(Object obj) {
            boolean z = true;
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof ColorFilterData)) {
                return false;
            }
            ColorFilterData colorFilterData = (ColorFilterData) obj;
            if (!(hashCode() == colorFilterData.hashCode() && this.colorFilter == colorFilterData.colorFilter)) {
                z = false;
            }
            return z;
        }
    }

    private interface LazyCompositionTask {
        void run(LottieComposition lottieComposition);
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface RepeatMode {
    }

    public int getOpacity() {
        return -3;
    }

    public LottieDrawable() {
        this.animator.addUpdateListener(new AnimatorUpdateListener() {
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                if (LottieDrawable.this.compositionLayer != null) {
                    LottieDrawable.this.compositionLayer.setProgress(LottieDrawable.this.animator.getAnimatedValueAbsolute());
                }
            }
        });
    }

    public boolean hasMasks() {
        CompositionLayer compositionLayer2 = this.compositionLayer;
        return compositionLayer2 != null && compositionLayer2.hasMasks();
    }

    public boolean hasMatte() {
        CompositionLayer compositionLayer2 = this.compositionLayer;
        return compositionLayer2 != null && compositionLayer2.hasMatte();
    }

    public boolean enableMergePathsForKitKatAndAbove() {
        return this.enableMergePaths;
    }

    public void enableMergePathsForKitKatAndAbove(boolean z) {
        if (VERSION.SDK_INT < 19) {
            Log.w(TAG, "Merge paths are not supported pre-Kit Kat.");
            return;
        }
        this.enableMergePaths = z;
        if (this.composition != null) {
            buildCompositionLayer();
        }
    }

    public boolean isMergePathsEnabledForKitKatAndAbove() {
        return this.enableMergePaths;
    }

    public void setImagesAssetsFolder(String str) {
        this.imageAssetsFolder = str;
    }

    public String getImageAssetsFolder() {
        return this.imageAssetsFolder;
    }

    public void recycleBitmaps() {
        ImageAssetManager imageAssetManager2 = this.imageAssetManager;
        if (imageAssetManager2 != null) {
            imageAssetManager2.recycleBitmaps();
        }
    }

    public boolean setComposition(LottieComposition lottieComposition) {
        if (this.composition == lottieComposition) {
            return false;
        }
        clearComposition();
        this.composition = lottieComposition;
        buildCompositionLayer();
        this.animator.setComposition(lottieComposition);
        setProgress(this.animator.getAnimatedFraction());
        setScale(this.scale);
        updateBounds();
        Iterator it = new ArrayList(this.lazyCompositionTasks).iterator();
        while (it.hasNext()) {
            ((LazyCompositionTask) it.next()).run(lottieComposition);
            it.remove();
        }
        this.lazyCompositionTasks.clear();
        lottieComposition.setPerformanceTrackingEnabled(this.performanceTrackingEnabled);
        return true;
    }

    public void setPerformanceTrackingEnabled(boolean z) {
        this.performanceTrackingEnabled = z;
        LottieComposition lottieComposition = this.composition;
        if (lottieComposition != null) {
            lottieComposition.setPerformanceTrackingEnabled(z);
        }
    }

    public PerformanceTracker getPerformanceTracker() {
        LottieComposition lottieComposition = this.composition;
        if (lottieComposition != null) {
            return lottieComposition.getPerformanceTracker();
        }
        return null;
    }

    private void buildCompositionLayer() {
        this.compositionLayer = new CompositionLayer(this, LayerParser.parse(this.composition), this.composition.getLayers(), this.composition);
    }

    public void clearComposition() {
        recycleBitmaps();
        if (this.animator.isRunning()) {
            this.animator.cancel();
        }
        this.composition = null;
        this.compositionLayer = null;
        this.imageAssetManager = null;
        this.animator.clearComposition();
        invalidateSelf();
    }

    public void invalidateSelf() {
        Callback callback = getCallback();
        if (callback != null) {
            callback.invalidateDrawable(this);
        }
    }

    public void setAlpha(int i) {
        this.alpha = i;
    }

    public int getAlpha() {
        return this.alpha;
    }

    public void setColorFilter(ColorFilter colorFilter) {
        Log.w(C0873L.TAG, "Use addColorFilter instead.");
    }

    public void draw(Canvas canvas) {
        float f;
        String str = "Drawable#draw";
        C0873L.beginSection(str);
        if (this.compositionLayer != null) {
            float f2 = this.scale;
            float maxScale = getMaxScale(canvas);
            if (f2 > maxScale) {
                f = this.scale / maxScale;
            } else {
                maxScale = f2;
                f = 1.0f;
            }
            if (f > 1.0f) {
                canvas.save();
                float width = ((float) this.composition.getBounds().width()) / 2.0f;
                float height = ((float) this.composition.getBounds().height()) / 2.0f;
                float f3 = width * maxScale;
                float f4 = height * maxScale;
                canvas.translate((getScale() * width) - f3, (getScale() * height) - f4);
                canvas.scale(f, f, f3, f4);
            }
            this.matrix.reset();
            this.matrix.preScale(maxScale, maxScale);
            this.compositionLayer.draw(canvas, this.matrix, this.alpha);
            C0873L.endSection(str);
            if (f > 1.0f) {
                canvas.restore();
            }
        }
    }

    public void start() {
        playAnimation();
    }

    public void stop() {
        endAnimation();
    }

    public boolean isRunning() {
        return isAnimating();
    }

    public void playAnimation() {
        if (this.compositionLayer == null) {
            this.lazyCompositionTasks.add(new LazyCompositionTask() {
                public final void run(LottieComposition lottieComposition) {
                    LottieDrawable.this.lambda$playAnimation$0$LottieDrawable(lottieComposition);
                }
            });
        } else {
            this.animator.playAnimation();
        }
    }

    public /* synthetic */ void lambda$playAnimation$0$LottieDrawable(LottieComposition lottieComposition) {
        playAnimation();
    }

    public void endAnimation() {
        this.lazyCompositionTasks.clear();
        this.animator.endAnimation();
    }

    public void resumeAnimation() {
        if (this.compositionLayer == null) {
            this.lazyCompositionTasks.add(new LazyCompositionTask() {
                public final void run(LottieComposition lottieComposition) {
                    LottieDrawable.this.lambda$resumeAnimation$1$LottieDrawable(lottieComposition);
                }
            });
        } else {
            this.animator.resumeAnimation();
        }
    }

    public /* synthetic */ void lambda$resumeAnimation$1$LottieDrawable(LottieComposition lottieComposition) {
        resumeAnimation();
    }

    public void setMinFrame(int i) {
        this.animator.setMinFrame(i);
    }

    public float getMinFrame() {
        return this.animator.getMinFrame();
    }

    public void setMinProgress(float f) {
        LottieComposition lottieComposition = this.composition;
        if (lottieComposition == null) {
            this.lazyCompositionTasks.add(new LazyCompositionTask(f) {
                private final /* synthetic */ float f$1;

                {
                    this.f$1 = r2;
                }

                public final void run(LottieComposition lottieComposition) {
                    LottieDrawable.this.lambda$setMinProgress$2$LottieDrawable(this.f$1, lottieComposition);
                }
            });
        } else {
            setMinFrame((int) MiscUtils.lerp(lottieComposition.getStartFrame(), this.composition.getEndFrame(), f));
        }
    }

    public /* synthetic */ void lambda$setMinProgress$2$LottieDrawable(float f, LottieComposition lottieComposition) {
        setMinProgress(f);
    }

    public void setMaxFrame(int i) {
        this.animator.setMaxFrame(i);
    }

    public float getMaxFrame() {
        return this.animator.getMaxFrame();
    }

    public void setMaxProgress(float f) {
        LottieComposition lottieComposition = this.composition;
        if (lottieComposition == null) {
            this.lazyCompositionTasks.add(new LazyCompositionTask(f) {
                private final /* synthetic */ float f$1;

                {
                    this.f$1 = r2;
                }

                public final void run(LottieComposition lottieComposition) {
                    LottieDrawable.this.lambda$setMaxProgress$3$LottieDrawable(this.f$1, lottieComposition);
                }
            });
        } else {
            setMaxFrame((int) MiscUtils.lerp(lottieComposition.getStartFrame(), this.composition.getEndFrame(), f));
        }
    }

    public /* synthetic */ void lambda$setMaxProgress$3$LottieDrawable(float f, LottieComposition lottieComposition) {
        setMaxProgress(f);
    }

    public void setMinAndMaxFrame(int i, int i2) {
        this.animator.setMinAndMaxFrames(i, i2);
    }

    public void setMinAndMaxProgress(float f, float f2) {
        LottieComposition lottieComposition = this.composition;
        if (lottieComposition == null) {
            this.lazyCompositionTasks.add(new LazyCompositionTask(f, f2) {
                private final /* synthetic */ float f$1;
                private final /* synthetic */ float f$2;

                {
                    this.f$1 = r2;
                    this.f$2 = r3;
                }

                public final void run(LottieComposition lottieComposition) {
                    LottieDrawable.this.lambda$setMinAndMaxProgress$4$LottieDrawable(this.f$1, this.f$2, lottieComposition);
                }
            });
        } else {
            setMinAndMaxFrame((int) MiscUtils.lerp(lottieComposition.getStartFrame(), this.composition.getEndFrame(), f), (int) MiscUtils.lerp(this.composition.getStartFrame(), this.composition.getEndFrame(), f2));
        }
    }

    public /* synthetic */ void lambda$setMinAndMaxProgress$4$LottieDrawable(float f, float f2, LottieComposition lottieComposition) {
        setMinAndMaxProgress(f, f2);
    }

    public void reverseAnimationSpeed() {
        this.animator.reverseAnimationSpeed();
    }

    public void setSpeed(float f) {
        this.animator.setSpeed(f);
    }

    public float getSpeed() {
        return this.animator.getSpeed();
    }

    public void addAnimatorUpdateListener(AnimatorUpdateListener animatorUpdateListener) {
        this.animator.addUpdateListener(animatorUpdateListener);
    }

    public void removeAnimatorUpdateListener(AnimatorUpdateListener animatorUpdateListener) {
        this.animator.removeUpdateListener(animatorUpdateListener);
    }

    public void removeAllUpdateListeners() {
        this.animator.removeAllUpdateListeners();
    }

    public void addAnimatorListener(AnimatorListener animatorListener) {
        this.animator.addListener(animatorListener);
    }

    public void removeAnimatorListener(AnimatorListener animatorListener) {
        this.animator.removeListener(animatorListener);
    }

    public void removeAllAnimatorListeners() {
        this.animator.removeAllListeners();
    }

    public void setFrame(int i) {
        if (this.composition == null) {
            this.lazyCompositionTasks.add(new LazyCompositionTask(i) {
                private final /* synthetic */ int f$1;

                {
                    this.f$1 = r2;
                }

                public final void run(LottieComposition lottieComposition) {
                    LottieDrawable.this.lambda$setFrame$5$LottieDrawable(this.f$1, lottieComposition);
                }
            });
        } else {
            this.animator.setFrame(i);
        }
    }

    public /* synthetic */ void lambda$setFrame$5$LottieDrawable(int i, LottieComposition lottieComposition) {
        setFrame(i);
    }

    public int getFrame() {
        return (int) this.animator.getFrame();
    }

    public void setProgress(float f) {
        LottieComposition lottieComposition = this.composition;
        if (lottieComposition == null) {
            this.lazyCompositionTasks.add(new LazyCompositionTask(f) {
                private final /* synthetic */ float f$1;

                {
                    this.f$1 = r2;
                }

                public final void run(LottieComposition lottieComposition) {
                    LottieDrawable.this.lambda$setProgress$6$LottieDrawable(this.f$1, lottieComposition);
                }
            });
        } else {
            setFrame((int) MiscUtils.lerp(lottieComposition.getStartFrame(), this.composition.getEndFrame(), f));
        }
    }

    public /* synthetic */ void lambda$setProgress$6$LottieDrawable(float f, LottieComposition lottieComposition) {
        setProgress(f);
    }

    @Deprecated
    public void loop(boolean z) {
        this.animator.setRepeatCount(z ? -1 : 0);
    }

    public void setRepeatMode(int i) {
        this.animator.setRepeatMode(i);
    }

    public int getRepeatMode() {
        return this.animator.getRepeatMode();
    }

    public void setRepeatCount(int i) {
        this.animator.setRepeatCount(i);
    }

    public int getRepeatCount() {
        return this.animator.getRepeatCount();
    }

    public boolean isLooping() {
        return this.animator.getRepeatCount() == -1;
    }

    public boolean isAnimating() {
        return this.animator.isRunning();
    }

    public void setScale(float f) {
        this.scale = f;
        updateBounds();
    }

    public void setImageAssetDelegate(ImageAssetDelegate imageAssetDelegate2) {
        this.imageAssetDelegate = imageAssetDelegate2;
        ImageAssetManager imageAssetManager2 = this.imageAssetManager;
        if (imageAssetManager2 != null) {
            imageAssetManager2.setDelegate(imageAssetDelegate2);
        }
    }

    public void setFontAssetDelegate(FontAssetDelegate fontAssetDelegate2) {
        this.fontAssetDelegate = fontAssetDelegate2;
        FontAssetManager fontAssetManager2 = this.fontAssetManager;
        if (fontAssetManager2 != null) {
            fontAssetManager2.setDelegate(fontAssetDelegate2);
        }
    }

    public void setTextDelegate(TextDelegate textDelegate2) {
        this.textDelegate = textDelegate2;
    }

    public TextDelegate getTextDelegate() {
        return this.textDelegate;
    }

    public boolean useTextGlyphs() {
        return this.textDelegate == null && this.composition.getCharacters().size() > 0;
    }

    public float getScale() {
        return this.scale;
    }

    public LottieComposition getComposition() {
        return this.composition;
    }

    private void updateBounds() {
        if (this.composition != null) {
            float scale2 = getScale();
            setBounds(0, 0, (int) (((float) this.composition.getBounds().width()) * scale2), (int) (((float) this.composition.getBounds().height()) * scale2));
        }
    }

    public void cancelAnimation() {
        this.lazyCompositionTasks.clear();
        this.animator.cancel();
    }

    public void pauseAnimation() {
        this.lazyCompositionTasks.clear();
        this.animator.pauseAnimation();
    }

    public float getProgress() {
        return this.animator.getAnimatedValueAbsolute();
    }

    public int getIntrinsicWidth() {
        LottieComposition lottieComposition = this.composition;
        if (lottieComposition == null) {
            return -1;
        }
        return (int) (((float) lottieComposition.getBounds().width()) * getScale());
    }

    public int getIntrinsicHeight() {
        LottieComposition lottieComposition = this.composition;
        if (lottieComposition == null) {
            return -1;
        }
        return (int) (((float) lottieComposition.getBounds().height()) * getScale());
    }

    public List<KeyPath> resolveKeyPath(KeyPath keyPath) {
        if (this.compositionLayer == null) {
            Log.w(C0873L.TAG, "Cannot resolve KeyPath. Composition is not set yet.");
            return Collections.emptyList();
        }
        ArrayList arrayList = new ArrayList();
        this.compositionLayer.resolveKeyPath(keyPath, 0, arrayList, new KeyPath(new String[0]));
        return arrayList;
    }

    public <T> void addValueCallback(KeyPath keyPath, T t, LottieValueCallback<T> lottieValueCallback) {
        if (this.compositionLayer == null) {
            this.lazyCompositionTasks.add(new LazyCompositionTask(keyPath, t, lottieValueCallback) {
                private final /* synthetic */ KeyPath f$1;
                private final /* synthetic */ Object f$2;
                private final /* synthetic */ LottieValueCallback f$3;

                {
                    this.f$1 = r2;
                    this.f$2 = r3;
                    this.f$3 = r4;
                }

                public final void run(LottieComposition lottieComposition) {
                    LottieDrawable.this.lambda$addValueCallback$7$LottieDrawable(this.f$1, this.f$2, this.f$3, lottieComposition);
                }
            });
            return;
        }
        boolean z = true;
        if (keyPath.getResolvedElement() != null) {
            keyPath.getResolvedElement().addValueCallback(t, lottieValueCallback);
        } else {
            List resolveKeyPath = resolveKeyPath(keyPath);
            for (int i = 0; i < resolveKeyPath.size(); i++) {
                ((KeyPath) resolveKeyPath.get(i)).getResolvedElement().addValueCallback(t, lottieValueCallback);
            }
            z = true ^ resolveKeyPath.isEmpty();
        }
        if (z) {
            invalidateSelf();
            if (t == LottieProperty.TIME_REMAP) {
                setProgress(getProgress());
            }
        }
    }

    public /* synthetic */ void lambda$addValueCallback$7$LottieDrawable(KeyPath keyPath, Object obj, LottieValueCallback lottieValueCallback, LottieComposition lottieComposition) {
        addValueCallback(keyPath, (T) obj, lottieValueCallback);
    }

    public <T> void addValueCallback(KeyPath keyPath, T t, final SimpleLottieValueCallback<T> simpleLottieValueCallback) {
        addValueCallback(keyPath, t, (LottieValueCallback<T>) new LottieValueCallback<T>() {
            public T getValue(LottieFrameInfo<T> lottieFrameInfo) {
                return simpleLottieValueCallback.getValue(lottieFrameInfo);
            }
        });
    }

    public Bitmap updateBitmap(String str, Bitmap bitmap) {
        ImageAssetManager imageAssetManager2 = getImageAssetManager();
        if (imageAssetManager2 == null) {
            Log.w(C0873L.TAG, "Cannot update bitmap. Most likely the drawable is not added to a View which prevents Lottie from getting a Context.");
            return null;
        }
        Bitmap updateBitmap = imageAssetManager2.updateBitmap(str, bitmap);
        invalidateSelf();
        return updateBitmap;
    }

    public Bitmap getImageAsset(String str) {
        ImageAssetManager imageAssetManager2 = getImageAssetManager();
        if (imageAssetManager2 != null) {
            return imageAssetManager2.bitmapForId(str);
        }
        return null;
    }

    private ImageAssetManager getImageAssetManager() {
        if (getCallback() == null) {
            return null;
        }
        ImageAssetManager imageAssetManager2 = this.imageAssetManager;
        if (imageAssetManager2 != null && !imageAssetManager2.hasSameContext(getContext())) {
            this.imageAssetManager.recycleBitmaps();
            this.imageAssetManager = null;
        }
        if (this.imageAssetManager == null) {
            this.imageAssetManager = new ImageAssetManager(getCallback(), this.imageAssetsFolder, this.imageAssetDelegate, this.composition.getImages());
        }
        return this.imageAssetManager;
    }

    public Typeface getTypeface(String str, String str2) {
        FontAssetManager fontAssetManager2 = getFontAssetManager();
        if (fontAssetManager2 != null) {
            return fontAssetManager2.getTypeface(str, str2);
        }
        return null;
    }

    private FontAssetManager getFontAssetManager() {
        if (getCallback() == null) {
            return null;
        }
        if (this.fontAssetManager == null) {
            this.fontAssetManager = new FontAssetManager(getCallback(), this.fontAssetDelegate);
        }
        return this.fontAssetManager;
    }

    private Context getContext() {
        Callback callback = getCallback();
        if (callback != null && (callback instanceof View)) {
            return ((View) callback).getContext();
        }
        return null;
    }

    public void invalidateDrawable(Drawable drawable) {
        Callback callback = getCallback();
        if (callback != null) {
            callback.invalidateDrawable(this);
        }
    }

    public void scheduleDrawable(Drawable drawable, Runnable runnable, long j) {
        Callback callback = getCallback();
        if (callback != null) {
            callback.scheduleDrawable(this, runnable, j);
        }
    }

    public void unscheduleDrawable(Drawable drawable, Runnable runnable) {
        Callback callback = getCallback();
        if (callback != null) {
            callback.unscheduleDrawable(this, runnable);
        }
    }

    private float getMaxScale(Canvas canvas) {
        return Math.min(((float) canvas.getWidth()) / ((float) this.composition.getBounds().width()), ((float) canvas.getHeight()) / ((float) this.composition.getBounds().height()));
    }
}
