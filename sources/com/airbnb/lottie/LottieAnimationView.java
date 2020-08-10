package com.airbnb.lottie;

import android.animation.Animator.AnimatorListener;
import android.animation.ValueAnimator.AnimatorUpdateListener;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.JsonReader;
import android.util.SparseArray;
import android.view.View.BaseSavedState;
import androidx.appcompat.widget.AppCompatImageView;
import com.airbnb.lottie.LottieAnimationView.CacheStrategy;
import com.airbnb.lottie.LottieComposition.Factory;
import com.airbnb.lottie.model.KeyPath;
import com.airbnb.lottie.value.LottieFrameInfo;
import com.airbnb.lottie.value.LottieValueCallback;
import com.airbnb.lottie.value.SimpleLottieValueCallback;
import java.io.StringReader;
import java.lang.ref.WeakReference;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.json.JSONObject;

public class LottieAnimationView extends AppCompatImageView {
    private static final Map<String, LottieComposition> ASSET_STRONG_REF_CACHE = new HashMap();
    private static final Map<String, WeakReference<LottieComposition>> ASSET_WEAK_REF_CACHE = new HashMap();
    public static final CacheStrategy DEFAULT_CACHE_STRATEGY = CacheStrategy.Weak;
    private static final SparseArray<LottieComposition> RAW_RES_STRONG_REF_CACHE = new SparseArray<>();
    private static final SparseArray<WeakReference<LottieComposition>> RAW_RES_WEAK_REF_CACHE = new SparseArray<>();
    private static final String TAG = LottieAnimationView.class.getSimpleName();
    private String animationName;
    private int animationResId;
    private boolean autoPlay = false;
    private LottieComposition composition;
    /* access modifiers changed from: private */
    public Cancellable compositionLoader;
    private CacheStrategy defaultCacheStrategy;
    private final OnCompositionLoadedListener loadedListener = new OnCompositionLoadedListener() {
        public void onCompositionLoaded(LottieComposition lottieComposition) {
            if (lottieComposition != null) {
                LottieAnimationView.this.setComposition(lottieComposition);
            }
            LottieAnimationView.this.compositionLoader = null;
        }
    };
    private final LottieDrawable lottieDrawable = new LottieDrawable();
    private boolean useHardwareLayer = false;
    private boolean wasAnimatingWhenDetached = false;

    public enum CacheStrategy {
        None,
        Weak,
        Strong
    }

    private static class SavedState extends BaseSavedState {
        public static final Creator<SavedState> CREATOR = new Creator<SavedState>() {
            public SavedState createFromParcel(Parcel parcel) {
                return new SavedState(parcel);
            }

            public SavedState[] newArray(int i) {
                return new SavedState[i];
            }
        };
        String animationName;
        int animationResId;
        String imageAssetsFolder;
        boolean isAnimating;
        float progress;
        int repeatCount;
        int repeatMode;

        SavedState(Parcelable parcelable) {
            super(parcelable);
        }

        private SavedState(Parcel parcel) {
            super(parcel);
            this.animationName = parcel.readString();
            this.progress = parcel.readFloat();
            boolean z = true;
            if (parcel.readInt() != 1) {
                z = false;
            }
            this.isAnimating = z;
            this.imageAssetsFolder = parcel.readString();
            this.repeatMode = parcel.readInt();
            this.repeatCount = parcel.readInt();
        }

        public void writeToParcel(Parcel parcel, int i) {
            super.writeToParcel(parcel, i);
            parcel.writeString(this.animationName);
            parcel.writeFloat(this.progress);
            parcel.writeInt(this.isAnimating ? 1 : 0);
            parcel.writeString(this.imageAssetsFolder);
            parcel.writeInt(this.repeatMode);
            parcel.writeInt(this.repeatCount);
        }
    }

    public LottieAnimationView(Context context) {
        super(context);
        init(null);
    }

    public LottieAnimationView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        init(attributeSet);
    }

    public LottieAnimationView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        init(attributeSet);
    }

    private void init(AttributeSet attributeSet) {
        TypedArray obtainStyledAttributes = getContext().obtainStyledAttributes(attributeSet, C0879R.styleable.LottieAnimationView);
        this.defaultCacheStrategy = CacheStrategy.values()[obtainStyledAttributes.getInt(C0879R.styleable.LottieAnimationView_lottie_cacheStrategy, DEFAULT_CACHE_STRATEGY.ordinal())];
        if (!isInEditMode()) {
            boolean hasValue = obtainStyledAttributes.hasValue(C0879R.styleable.LottieAnimationView_lottie_rawRes);
            boolean hasValue2 = obtainStyledAttributes.hasValue(C0879R.styleable.LottieAnimationView_lottie_fileName);
            if (hasValue && hasValue2) {
                throw new IllegalArgumentException("lottie_rawRes and lottie_fileName cannot be used at the same time. Please use use only one at once.");
            } else if (hasValue) {
                int resourceId = obtainStyledAttributes.getResourceId(C0879R.styleable.LottieAnimationView_lottie_rawRes, 0);
                if (resourceId != 0) {
                    setAnimation(resourceId);
                }
            } else if (hasValue2) {
                String string = obtainStyledAttributes.getString(C0879R.styleable.LottieAnimationView_lottie_fileName);
                if (string != null) {
                    setAnimation(string);
                }
            }
        }
        if (obtainStyledAttributes.getBoolean(C0879R.styleable.LottieAnimationView_lottie_autoPlay, false)) {
            this.wasAnimatingWhenDetached = true;
            this.autoPlay = true;
        }
        if (obtainStyledAttributes.getBoolean(C0879R.styleable.LottieAnimationView_lottie_loop, false)) {
            this.lottieDrawable.setRepeatCount(-1);
        }
        if (obtainStyledAttributes.hasValue(C0879R.styleable.LottieAnimationView_lottie_repeatMode)) {
            setRepeatMode(obtainStyledAttributes.getInt(C0879R.styleable.LottieAnimationView_lottie_repeatMode, 1));
        }
        if (obtainStyledAttributes.hasValue(C0879R.styleable.LottieAnimationView_lottie_repeatCount)) {
            setRepeatCount(obtainStyledAttributes.getInt(C0879R.styleable.LottieAnimationView_lottie_repeatCount, -1));
        }
        setImageAssetsFolder(obtainStyledAttributes.getString(C0879R.styleable.LottieAnimationView_lottie_imageAssetsFolder));
        setProgress(obtainStyledAttributes.getFloat(C0879R.styleable.LottieAnimationView_lottie_progress, 0.0f));
        enableMergePathsForKitKatAndAbove(obtainStyledAttributes.getBoolean(C0879R.styleable.LottieAnimationView_lottie_enableMergePathsForKitKatAndAbove, false));
        if (obtainStyledAttributes.hasValue(C0879R.styleable.LottieAnimationView_lottie_colorFilter)) {
            SimpleColorFilter simpleColorFilter = new SimpleColorFilter(obtainStyledAttributes.getColor(C0879R.styleable.LottieAnimationView_lottie_colorFilter, 0));
            addValueCallback(new KeyPath("**"), (T) LottieProperty.COLOR_FILTER, new LottieValueCallback<>(simpleColorFilter));
        }
        if (obtainStyledAttributes.hasValue(C0879R.styleable.LottieAnimationView_lottie_scale)) {
            this.lottieDrawable.setScale(obtainStyledAttributes.getFloat(C0879R.styleable.LottieAnimationView_lottie_scale, 1.0f));
        }
        obtainStyledAttributes.recycle();
        enableOrDisableHardwareLayer();
    }

    public void setImageResource(int i) {
        recycleBitmaps();
        cancelLoaderTask();
        super.setImageResource(i);
    }

    public void setImageDrawable(Drawable drawable) {
        setImageDrawable(drawable, true);
    }

    private void setImageDrawable(Drawable drawable, boolean z) {
        if (z && drawable != this.lottieDrawable) {
            recycleBitmaps();
        }
        cancelLoaderTask();
        super.setImageDrawable(drawable);
    }

    public void setImageBitmap(Bitmap bitmap) {
        recycleBitmaps();
        cancelLoaderTask();
        super.setImageBitmap(bitmap);
    }

    public void invalidateDrawable(Drawable drawable) {
        Drawable drawable2 = getDrawable();
        LottieDrawable lottieDrawable2 = this.lottieDrawable;
        if (drawable2 == lottieDrawable2) {
            super.invalidateDrawable(lottieDrawable2);
        } else {
            super.invalidateDrawable(drawable);
        }
    }

    /* access modifiers changed from: protected */
    public Parcelable onSaveInstanceState() {
        SavedState savedState = new SavedState(super.onSaveInstanceState());
        savedState.animationName = this.animationName;
        savedState.animationResId = this.animationResId;
        savedState.progress = this.lottieDrawable.getProgress();
        savedState.isAnimating = this.lottieDrawable.isAnimating();
        savedState.imageAssetsFolder = this.lottieDrawable.getImageAssetsFolder();
        savedState.repeatMode = this.lottieDrawable.getRepeatMode();
        savedState.repeatCount = this.lottieDrawable.getRepeatCount();
        return savedState;
    }

    /* access modifiers changed from: protected */
    public void onRestoreInstanceState(Parcelable parcelable) {
        if (!(parcelable instanceof SavedState)) {
            super.onRestoreInstanceState(parcelable);
            return;
        }
        SavedState savedState = (SavedState) parcelable;
        super.onRestoreInstanceState(savedState.getSuperState());
        this.animationName = savedState.animationName;
        if (!TextUtils.isEmpty(this.animationName)) {
            setAnimation(this.animationName);
        }
        this.animationResId = savedState.animationResId;
        int i = this.animationResId;
        if (i != 0) {
            setAnimation(i);
        }
        setProgress(savedState.progress);
        if (savedState.isAnimating) {
            playAnimation();
        }
        this.lottieDrawable.setImagesAssetsFolder(savedState.imageAssetsFolder);
        setRepeatMode(savedState.repeatMode);
        setRepeatCount(savedState.repeatCount);
    }

    /* access modifiers changed from: protected */
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        if (this.autoPlay && this.wasAnimatingWhenDetached) {
            playAnimation();
        }
    }

    /* access modifiers changed from: protected */
    public void onDetachedFromWindow() {
        if (isAnimating()) {
            cancelAnimation();
            this.wasAnimatingWhenDetached = true;
        }
        recycleBitmaps();
        super.onDetachedFromWindow();
    }

    /* access modifiers changed from: 0000 */
    public void recycleBitmaps() {
        LottieDrawable lottieDrawable2 = this.lottieDrawable;
        if (lottieDrawable2 != null) {
            lottieDrawable2.recycleBitmaps();
        }
    }

    public void enableMergePathsForKitKatAndAbove(boolean z) {
        this.lottieDrawable.enableMergePathsForKitKatAndAbove(z);
    }

    public boolean isMergePathsEnabledForKitKatAndAbove() {
        return this.lottieDrawable.isMergePathsEnabledForKitKatAndAbove();
    }

    @Deprecated
    public void useExperimentalHardwareAcceleration() {
        useHardwareAcceleration(true);
    }

    @Deprecated
    public void useExperimentalHardwareAcceleration(boolean z) {
        useHardwareAcceleration(z);
    }

    public void useHardwareAcceleration() {
        useHardwareAcceleration(true);
    }

    public void useHardwareAcceleration(boolean z) {
        this.useHardwareLayer = z;
        enableOrDisableHardwareLayer();
    }

    public boolean getUseHardwareAcceleration() {
        return this.useHardwareLayer;
    }

    public void setAnimation(int i) {
        setAnimation(i, this.defaultCacheStrategy);
    }

    public void setAnimation(int i, CacheStrategy cacheStrategy) {
        this.animationResId = i;
        this.animationName = null;
        if (RAW_RES_WEAK_REF_CACHE.indexOfKey(i) > 0) {
            LottieComposition lottieComposition = (LottieComposition) ((WeakReference) RAW_RES_WEAK_REF_CACHE.get(i)).get();
            if (lottieComposition != null) {
                setComposition(lottieComposition);
                return;
            }
        } else if (RAW_RES_STRONG_REF_CACHE.indexOfKey(i) > 0) {
            setComposition((LottieComposition) RAW_RES_STRONG_REF_CACHE.get(i));
            return;
        }
        clearComposition();
        cancelLoaderTask();
        this.compositionLoader = Factory.fromRawFile(getContext(), i, new OnCompositionLoadedListener(cacheStrategy, i) {
            private final /* synthetic */ CacheStrategy f$1;
            private final /* synthetic */ int f$2;

            {
                this.f$1 = r2;
                this.f$2 = r3;
            }

            public final void onCompositionLoaded(LottieComposition lottieComposition) {
                LottieAnimationView.this.lambda$setAnimation$0$LottieAnimationView(this.f$1, this.f$2, lottieComposition);
            }
        });
    }

    public /* synthetic */ void lambda$setAnimation$0$LottieAnimationView(CacheStrategy cacheStrategy, int i, LottieComposition lottieComposition) {
        if (cacheStrategy == CacheStrategy.Strong) {
            RAW_RES_STRONG_REF_CACHE.put(i, lottieComposition);
        } else if (cacheStrategy == CacheStrategy.Weak) {
            RAW_RES_WEAK_REF_CACHE.put(i, new WeakReference(lottieComposition));
        }
        setComposition(lottieComposition);
    }

    public void setAnimation(String str) {
        setAnimation(str, this.defaultCacheStrategy);
    }

    public void setAnimation(String str, CacheStrategy cacheStrategy) {
        this.animationName = str;
        this.animationResId = 0;
        if (ASSET_WEAK_REF_CACHE.containsKey(str)) {
            LottieComposition lottieComposition = (LottieComposition) ((WeakReference) ASSET_WEAK_REF_CACHE.get(str)).get();
            if (lottieComposition != null) {
                setComposition(lottieComposition);
                return;
            }
        } else if (ASSET_STRONG_REF_CACHE.containsKey(str)) {
            setComposition((LottieComposition) ASSET_STRONG_REF_CACHE.get(str));
            return;
        }
        clearComposition();
        cancelLoaderTask();
        this.compositionLoader = Factory.fromAssetFileName(getContext(), str, new OnCompositionLoadedListener(cacheStrategy, str) {
            private final /* synthetic */ CacheStrategy f$1;
            private final /* synthetic */ String f$2;

            {
                this.f$1 = r2;
                this.f$2 = r3;
            }

            public final void onCompositionLoaded(LottieComposition lottieComposition) {
                LottieAnimationView.this.lambda$setAnimation$1$LottieAnimationView(this.f$1, this.f$2, lottieComposition);
            }
        });
    }

    public /* synthetic */ void lambda$setAnimation$1$LottieAnimationView(CacheStrategy cacheStrategy, String str, LottieComposition lottieComposition) {
        if (cacheStrategy == CacheStrategy.Strong) {
            ASSET_STRONG_REF_CACHE.put(str, lottieComposition);
        } else if (cacheStrategy == CacheStrategy.Weak) {
            ASSET_WEAK_REF_CACHE.put(str, new WeakReference(lottieComposition));
        }
        setComposition(lottieComposition);
    }

    @Deprecated
    public void setAnimation(JSONObject jSONObject) {
        setAnimation(new JsonReader(new StringReader(jSONObject.toString())));
    }

    public void setAnimationFromJson(String str) {
        setAnimation(new JsonReader(new StringReader(str)));
    }

    public void setAnimation(JsonReader jsonReader) {
        clearComposition();
        cancelLoaderTask();
        this.compositionLoader = Factory.fromJsonReader(jsonReader, this.loadedListener);
    }

    private void cancelLoaderTask() {
        Cancellable cancellable = this.compositionLoader;
        if (cancellable != null) {
            cancellable.cancel();
            this.compositionLoader = null;
        }
    }

    public void setComposition(LottieComposition lottieComposition) {
        this.lottieDrawable.setCallback(this);
        this.composition = lottieComposition;
        boolean composition2 = this.lottieDrawable.setComposition(lottieComposition);
        enableOrDisableHardwareLayer();
        if (getDrawable() != this.lottieDrawable || composition2) {
            setImageDrawable(null);
            setImageDrawable(this.lottieDrawable);
            requestLayout();
        }
    }

    public LottieComposition getComposition() {
        return this.composition;
    }

    public boolean hasMasks() {
        return this.lottieDrawable.hasMasks();
    }

    public boolean hasMatte() {
        return this.lottieDrawable.hasMatte();
    }

    public void playAnimation() {
        this.lottieDrawable.playAnimation();
        enableOrDisableHardwareLayer();
    }

    public void resumeAnimation() {
        this.lottieDrawable.resumeAnimation();
        enableOrDisableHardwareLayer();
    }

    public void setMinFrame(int i) {
        this.lottieDrawable.setMinFrame(i);
    }

    public float getMinFrame() {
        return this.lottieDrawable.getMinFrame();
    }

    public void setMinProgress(float f) {
        this.lottieDrawable.setMinProgress(f);
    }

    public void setMaxFrame(int i) {
        this.lottieDrawable.setMaxFrame(i);
    }

    public float getMaxFrame() {
        return this.lottieDrawable.getMaxFrame();
    }

    public void setMaxProgress(float f) {
        this.lottieDrawable.setMaxProgress(f);
    }

    public void setMinAndMaxFrame(int i, int i2) {
        this.lottieDrawable.setMinAndMaxFrame(i, i2);
    }

    public void setMinAndMaxProgress(float f, float f2) {
        this.lottieDrawable.setMinAndMaxProgress(f, f2);
    }

    public void reverseAnimationSpeed() {
        this.lottieDrawable.reverseAnimationSpeed();
    }

    public void setSpeed(float f) {
        this.lottieDrawable.setSpeed(f);
    }

    public float getSpeed() {
        return this.lottieDrawable.getSpeed();
    }

    public void addAnimatorUpdateListener(AnimatorUpdateListener animatorUpdateListener) {
        this.lottieDrawable.addAnimatorUpdateListener(animatorUpdateListener);
    }

    public void removeUpdateListener(AnimatorUpdateListener animatorUpdateListener) {
        this.lottieDrawable.removeAnimatorUpdateListener(animatorUpdateListener);
    }

    public void removeAllUpdateListeners() {
        this.lottieDrawable.removeAllUpdateListeners();
    }

    public void addAnimatorListener(AnimatorListener animatorListener) {
        this.lottieDrawable.addAnimatorListener(animatorListener);
    }

    public void removeAnimatorListener(AnimatorListener animatorListener) {
        this.lottieDrawable.removeAnimatorListener(animatorListener);
    }

    public void removeAllAnimatorListeners() {
        this.lottieDrawable.removeAllAnimatorListeners();
    }

    @Deprecated
    public void loop(boolean z) {
        this.lottieDrawable.setRepeatCount(z ? -1 : 0);
    }

    public void setRepeatMode(int i) {
        this.lottieDrawable.setRepeatMode(i);
    }

    public int getRepeatMode() {
        return this.lottieDrawable.getRepeatMode();
    }

    public void setRepeatCount(int i) {
        this.lottieDrawable.setRepeatCount(i);
    }

    public int getRepeatCount() {
        return this.lottieDrawable.getRepeatCount();
    }

    public boolean isAnimating() {
        return this.lottieDrawable.isAnimating();
    }

    public void setImageAssetsFolder(String str) {
        this.lottieDrawable.setImagesAssetsFolder(str);
    }

    public String getImageAssetsFolder() {
        return this.lottieDrawable.getImageAssetsFolder();
    }

    public Bitmap updateBitmap(String str, Bitmap bitmap) {
        return this.lottieDrawable.updateBitmap(str, bitmap);
    }

    public void setImageAssetDelegate(ImageAssetDelegate imageAssetDelegate) {
        this.lottieDrawable.setImageAssetDelegate(imageAssetDelegate);
    }

    public void setFontAssetDelegate(FontAssetDelegate fontAssetDelegate) {
        this.lottieDrawable.setFontAssetDelegate(fontAssetDelegate);
    }

    public void setTextDelegate(TextDelegate textDelegate) {
        this.lottieDrawable.setTextDelegate(textDelegate);
    }

    public List<KeyPath> resolveKeyPath(KeyPath keyPath) {
        return this.lottieDrawable.resolveKeyPath(keyPath);
    }

    public <T> void addValueCallback(KeyPath keyPath, T t, LottieValueCallback<T> lottieValueCallback) {
        this.lottieDrawable.addValueCallback(keyPath, t, lottieValueCallback);
    }

    public <T> void addValueCallback(KeyPath keyPath, T t, final SimpleLottieValueCallback<T> simpleLottieValueCallback) {
        this.lottieDrawable.addValueCallback(keyPath, t, (LottieValueCallback<T>) new LottieValueCallback<T>() {
            public T getValue(LottieFrameInfo<T> lottieFrameInfo) {
                return simpleLottieValueCallback.getValue(lottieFrameInfo);
            }
        });
    }

    public void setScale(float f) {
        this.lottieDrawable.setScale(f);
        if (getDrawable() == this.lottieDrawable) {
            setImageDrawable(null, false);
            setImageDrawable(this.lottieDrawable, false);
        }
    }

    public float getScale() {
        return this.lottieDrawable.getScale();
    }

    public void cancelAnimation() {
        this.lottieDrawable.cancelAnimation();
        enableOrDisableHardwareLayer();
    }

    public void pauseAnimation() {
        this.lottieDrawable.pauseAnimation();
        enableOrDisableHardwareLayer();
    }

    public void setFrame(int i) {
        this.lottieDrawable.setFrame(i);
    }

    public int getFrame() {
        return this.lottieDrawable.getFrame();
    }

    public void setProgress(float f) {
        this.lottieDrawable.setProgress(f);
    }

    public float getProgress() {
        return this.lottieDrawable.getProgress();
    }

    public long getDuration() {
        LottieComposition lottieComposition = this.composition;
        if (lottieComposition != null) {
            return (long) lottieComposition.getDuration();
        }
        return 0;
    }

    public void setPerformanceTrackingEnabled(boolean z) {
        this.lottieDrawable.setPerformanceTrackingEnabled(z);
    }

    public PerformanceTracker getPerformanceTracker() {
        return this.lottieDrawable.getPerformanceTracker();
    }

    private void clearComposition() {
        this.composition = null;
        this.lottieDrawable.clearComposition();
    }

    private void enableOrDisableHardwareLayer() {
        int i = 1;
        if (this.useHardwareLayer && this.lottieDrawable.isAnimating()) {
            i = 2;
        }
        setLayerType(i, null);
    }
}
