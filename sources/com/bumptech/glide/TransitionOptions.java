package com.bumptech.glide;

import com.bumptech.glide.TransitionOptions;
import com.bumptech.glide.request.transition.NoTransition;
import com.bumptech.glide.request.transition.TransitionFactory;
import com.bumptech.glide.request.transition.ViewAnimationFactory;
import com.bumptech.glide.request.transition.ViewPropertyAnimationFactory;
import com.bumptech.glide.request.transition.ViewPropertyTransition.Animator;
import com.bumptech.glide.util.Preconditions;

public abstract class TransitionOptions<CHILD extends TransitionOptions<CHILD, TranscodeType>, TranscodeType> implements Cloneable {
    private TransitionFactory<? super TranscodeType> transitionFactory = NoTransition.getFactory();

    private CHILD self() {
        return this;
    }

    public final CHILD dontTransition() {
        return transition(NoTransition.getFactory());
    }

    public final CHILD transition(int i) {
        return transition((TransitionFactory<? super TranscodeType>) new ViewAnimationFactory<Object>(i));
    }

    public final CHILD transition(Animator animator) {
        return transition((TransitionFactory<? super TranscodeType>) new ViewPropertyAnimationFactory<Object>(animator));
    }

    public final CHILD transition(TransitionFactory<? super TranscodeType> transitionFactory2) {
        this.transitionFactory = (TransitionFactory) Preconditions.checkNotNull(transitionFactory2);
        return self();
    }

    public final CHILD clone() {
        try {
            return (TransitionOptions) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new RuntimeException(e);
        }
    }

    /* access modifiers changed from: 0000 */
    public final TransitionFactory<? super TranscodeType> getTransitionFactory() {
        return this.transitionFactory;
    }
}
