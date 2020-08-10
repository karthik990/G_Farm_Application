package com.google.android.material.transition;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.transition.TransitionValues;
import android.transition.Visibility;
import android.view.View;
import android.view.ViewGroup;
import com.google.android.material.animation.AnimatorSetCompat;
import com.google.android.material.transition.VisibilityAnimatorProvider;
import java.util.ArrayList;

abstract class MaterialVisibility<P extends VisibilityAnimatorProvider> extends Visibility {
    private P primaryAnimatorProvider;
    private VisibilityAnimatorProvider secondaryAnimatorProvider;

    /* access modifiers changed from: 0000 */
    public abstract P getDefaultPrimaryAnimatorProvider();

    /* access modifiers changed from: 0000 */
    public abstract VisibilityAnimatorProvider getDefaultSecondaryAnimatorProvider();

    MaterialVisibility() {
    }

    /* access modifiers changed from: 0000 */
    public void initialize() {
        this.primaryAnimatorProvider = getDefaultPrimaryAnimatorProvider();
        this.secondaryAnimatorProvider = getDefaultSecondaryAnimatorProvider();
    }

    public P getPrimaryAnimatorProvider() {
        if (this.primaryAnimatorProvider == null) {
            this.primaryAnimatorProvider = getDefaultPrimaryAnimatorProvider();
        }
        return this.primaryAnimatorProvider;
    }

    public VisibilityAnimatorProvider getSecondaryAnimatorProvider() {
        return this.secondaryAnimatorProvider;
    }

    public void setSecondaryAnimatorProvider(VisibilityAnimatorProvider visibilityAnimatorProvider) {
        this.secondaryAnimatorProvider = visibilityAnimatorProvider;
    }

    public Animator onAppear(ViewGroup viewGroup, View view, TransitionValues transitionValues, TransitionValues transitionValues2) {
        return createAnimator(viewGroup, view, transitionValues, transitionValues2, true);
    }

    public Animator onDisappear(ViewGroup viewGroup, View view, TransitionValues transitionValues, TransitionValues transitionValues2) {
        return createAnimator(viewGroup, view, transitionValues, transitionValues2, false);
    }

    private Animator createAnimator(ViewGroup viewGroup, View view, TransitionValues transitionValues, TransitionValues transitionValues2, boolean z) {
        Animator animator;
        Animator animator2;
        AnimatorSet animatorSet = new AnimatorSet();
        ArrayList arrayList = new ArrayList();
        if (z) {
            animator = getPrimaryAnimatorProvider().createAppear(viewGroup, view, transitionValues, transitionValues2);
        } else {
            animator = getPrimaryAnimatorProvider().createDisappear(viewGroup, view, transitionValues, transitionValues2);
        }
        if (animator != null) {
            arrayList.add(animator);
        }
        VisibilityAnimatorProvider secondaryAnimatorProvider2 = getSecondaryAnimatorProvider();
        if (secondaryAnimatorProvider2 != null) {
            if (z) {
                animator2 = secondaryAnimatorProvider2.createAppear(viewGroup, view, transitionValues, transitionValues2);
            } else {
                animator2 = secondaryAnimatorProvider2.createDisappear(viewGroup, view, transitionValues, transitionValues2);
            }
            if (animator2 != null) {
                arrayList.add(animator2);
            }
        }
        AnimatorSetCompat.playTogether(animatorSet, arrayList);
        return animatorSet;
    }
}
