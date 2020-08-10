package com.miguelcatalan.materialsearchview.utils;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewAnimationUtils;
import androidx.core.view.ViewCompat;
import androidx.core.view.ViewPropertyAnimatorListener;

public class AnimationUtil {
    public static int ANIMATION_DURATION_LONG = 800;
    public static int ANIMATION_DURATION_MEDIUM = 400;
    public static int ANIMATION_DURATION_SHORT = 150;

    public interface AnimationListener {
        boolean onAnimationCancel(View view);

        boolean onAnimationEnd(View view);

        boolean onAnimationStart(View view);
    }

    public static void crossFadeViews(View view, View view2) {
        crossFadeViews(view, view2, ANIMATION_DURATION_SHORT);
    }

    public static void crossFadeViews(View view, View view2, int i) {
        fadeInView(view, i);
        fadeOutView(view2, i);
    }

    public static void fadeInView(View view) {
        fadeInView(view, ANIMATION_DURATION_SHORT);
    }

    public static void fadeInView(View view, int i) {
        fadeInView(view, i, null);
    }

    public static void fadeInView(View view, int i, final AnimationListener animationListener) {
        view.setVisibility(0);
        view.setAlpha(0.0f);
        ViewCompat.animate(view).alpha(1.0f).setDuration((long) i).setListener(animationListener != null ? new ViewPropertyAnimatorListener() {
            public void onAnimationCancel(View view) {
            }

            public void onAnimationStart(View view) {
                if (!animationListener.onAnimationStart(view)) {
                    view.setDrawingCacheEnabled(true);
                }
            }

            public void onAnimationEnd(View view) {
                if (!animationListener.onAnimationEnd(view)) {
                    view.setDrawingCacheEnabled(false);
                }
            }
        } : null);
    }

    public static void reveal(final View view, final AnimationListener animationListener) {
        Animator createCircularReveal = ViewAnimationUtils.createCircularReveal(view, view.getWidth() - ((int) TypedValue.applyDimension(1, 24.0f, view.getResources().getDisplayMetrics())), view.getHeight() / 2, 0.0f, (float) Math.max(view.getWidth(), view.getHeight()));
        view.setVisibility(0);
        createCircularReveal.addListener(new AnimatorListenerAdapter() {
            public void onAnimationRepeat(Animator animator) {
            }

            public void onAnimationStart(Animator animator) {
                animationListener.onAnimationStart(view);
            }

            public void onAnimationEnd(Animator animator) {
                animationListener.onAnimationEnd(view);
            }

            public void onAnimationCancel(Animator animator) {
                animationListener.onAnimationCancel(view);
            }
        });
        createCircularReveal.start();
    }

    public static void fadeOutView(View view) {
        fadeOutView(view, ANIMATION_DURATION_SHORT);
    }

    public static void fadeOutView(View view, int i) {
        fadeOutView(view, i, null);
    }

    public static void fadeOutView(View view, int i, final AnimationListener animationListener) {
        ViewCompat.animate(view).alpha(0.0f).setDuration((long) i).setListener(new ViewPropertyAnimatorListener() {
            public void onAnimationCancel(View view) {
            }

            public void onAnimationStart(View view) {
                AnimationListener animationListener = animationListener;
                if (animationListener == null || !animationListener.onAnimationStart(view)) {
                    view.setDrawingCacheEnabled(true);
                }
            }

            public void onAnimationEnd(View view) {
                AnimationListener animationListener = animationListener;
                if (animationListener == null || !animationListener.onAnimationEnd(view)) {
                    view.setVisibility(8);
                    view.setDrawingCacheEnabled(false);
                }
            }
        });
    }
}
