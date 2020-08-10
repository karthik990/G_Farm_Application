package com.google.android.material.transition;

import android.animation.Animator;
import android.transition.TransitionValues;
import android.view.View;
import android.view.ViewGroup;
import com.google.android.material.animation.AnimationUtils;

public class MaterialFadeThrough extends MaterialVisibility<FadeThroughProvider> {
    private static final float DEFAULT_START_SCALE = 0.92f;

    public /* bridge */ /* synthetic */ VisibilityAnimatorProvider getSecondaryAnimatorProvider() {
        return super.getSecondaryAnimatorProvider();
    }

    public /* bridge */ /* synthetic */ Animator onAppear(ViewGroup viewGroup, View view, TransitionValues transitionValues, TransitionValues transitionValues2) {
        return super.onAppear(viewGroup, view, transitionValues, transitionValues2);
    }

    public /* bridge */ /* synthetic */ Animator onDisappear(ViewGroup viewGroup, View view, TransitionValues transitionValues, TransitionValues transitionValues2) {
        return super.onDisappear(viewGroup, view, transitionValues, transitionValues2);
    }

    public /* bridge */ /* synthetic */ void setSecondaryAnimatorProvider(VisibilityAnimatorProvider visibilityAnimatorProvider) {
        super.setSecondaryAnimatorProvider(visibilityAnimatorProvider);
    }

    public static MaterialFadeThrough create() {
        return new MaterialFadeThrough();
    }

    private MaterialFadeThrough() {
        setInterpolator(AnimationUtils.FAST_OUT_SLOW_IN_INTERPOLATOR);
        initialize();
    }

    /* access modifiers changed from: 0000 */
    public FadeThroughProvider getDefaultPrimaryAnimatorProvider() {
        return new FadeThroughProvider();
    }

    /* access modifiers changed from: 0000 */
    public VisibilityAnimatorProvider getDefaultSecondaryAnimatorProvider() {
        ScaleProvider scaleProvider = new ScaleProvider();
        scaleProvider.setScaleOnDisappear(false);
        scaleProvider.setIncomingStartScale(DEFAULT_START_SCALE);
        return scaleProvider;
    }
}
