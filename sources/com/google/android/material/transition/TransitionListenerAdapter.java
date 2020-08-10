package com.google.android.material.transition;

import android.transition.Transition;
import android.transition.Transition.TransitionListener;

abstract class TransitionListenerAdapter implements TransitionListener {
    public void onTransitionCancel(Transition transition) {
    }

    public void onTransitionEnd(Transition transition) {
    }

    public void onTransitionPause(Transition transition) {
    }

    public void onTransitionResume(Transition transition) {
    }

    public void onTransitionStart(Transition transition) {
    }

    TransitionListenerAdapter() {
    }
}
