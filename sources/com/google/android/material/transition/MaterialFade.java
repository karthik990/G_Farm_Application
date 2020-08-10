package com.google.android.material.transition;

import android.animation.Animator;
import android.transition.TransitionValues;
import android.view.View;
import android.view.ViewGroup;
import com.google.android.material.animation.AnimationUtils;

public class MaterialFade extends MaterialVisibility<FadeProvider> {
    private static final long DEFAULT_DURATION_ENTER = 150;
    private static final long DEFAULT_DURATION_RETURN = 75;
    private static final float DEFAULT_FADE_END_THRESHOLD_ENTER = 0.3f;
    private static final float DEFAULT_START_SCALE = 0.8f;
    private boolean entering;

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

    public static MaterialFade create() {
        return create(true);
    }

    public static MaterialFade create(boolean z) {
        return new MaterialFade(z);
    }

    private MaterialFade(boolean z) {
        this.entering = z;
        setDuration(z ? 150 : 75);
        setInterpolator(AnimationUtils.FAST_OUT_SLOW_IN_INTERPOLATOR);
        initialize();
    }

    /* access modifiers changed from: 0000 */
    public FadeProvider getDefaultPrimaryAnimatorProvider() {
        FadeProvider fadeProvider = new FadeProvider();
        if (this.entering) {
            fadeProvider.setIncomingEndThreshold(DEFAULT_FADE_END_THRESHOLD_ENTER);
        }
        return fadeProvider;
    }

    /* access modifiers changed from: 0000 */
    public VisibilityAnimatorProvider getDefaultSecondaryAnimatorProvider() {
        ScaleProvider scaleProvider = new ScaleProvider();
        scaleProvider.setScaleOnDisappear(false);
        scaleProvider.setIncomingStartScale(DEFAULT_START_SCALE);
        return scaleProvider;
    }
}
