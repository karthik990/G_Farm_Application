package com.google.android.material.transition;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.content.Context;
import android.transition.TransitionValues;
import android.view.View;
import android.view.ViewGroup;
import androidx.core.view.ViewCompat;
import com.google.android.material.C2556R;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

public class SlideDistanceProvider implements VisibilityAnimatorProvider {
    private static final int DEFAULT_DISTANCE = -1;
    private int slideDistance = -1;
    private int slideEdge;

    @Retention(RetentionPolicy.SOURCE)
    public @interface GravityFlag {
    }

    public SlideDistanceProvider(int i) {
        this.slideEdge = i;
    }

    public int getSlideEdge() {
        return this.slideEdge;
    }

    public void setSlideEdge(int i) {
        this.slideEdge = i;
    }

    public int getSlideDistance() {
        return this.slideDistance;
    }

    public void setSlideDistance(int i) {
        if (i >= 0) {
            this.slideDistance = i;
            return;
        }
        throw new IllegalArgumentException("Slide distance must be positive. If attempting to reverse the direction of the slide, use setSlideEdge(int) instead.");
    }

    public Animator createAppear(ViewGroup viewGroup, View view, TransitionValues transitionValues, TransitionValues transitionValues2) {
        return createTranslationAppearAnimator(viewGroup, view, this.slideEdge, getSlideDistanceOrDefault(view.getContext()));
    }

    public Animator createDisappear(ViewGroup viewGroup, View view, TransitionValues transitionValues, TransitionValues transitionValues2) {
        return createTranslationDisappearAnimator(viewGroup, view, this.slideEdge, getSlideDistanceOrDefault(view.getContext()));
    }

    private int getSlideDistanceOrDefault(Context context) {
        int i = this.slideDistance;
        if (i != -1) {
            return i;
        }
        return context.getResources().getDimensionPixelSize(C2556R.dimen.mtrl_transition_shared_axis_slide_distance);
    }

    private static Animator createTranslationAppearAnimator(View view, View view2, int i, int i2) {
        if (i == 3) {
            return createTranslationXAnimator(view2, (float) i2, 0.0f);
        }
        if (i == 5) {
            return createTranslationXAnimator(view2, (float) (-i2), 0.0f);
        }
        if (i == 48) {
            return createTranslationYAnimator(view2, (float) (-i2), 0.0f);
        }
        if (i == 80) {
            return createTranslationYAnimator(view2, (float) i2, 0.0f);
        }
        if (i == 8388611) {
            return createTranslationXAnimator(view2, isRtl(view) ? (float) i2 : (float) (-i2), 0.0f);
        } else if (i == 8388613) {
            return createTranslationXAnimator(view2, isRtl(view) ? (float) (-i2) : (float) i2, 0.0f);
        } else {
            StringBuilder sb = new StringBuilder();
            sb.append("Invalid slide direction: ");
            sb.append(i);
            throw new IllegalArgumentException(sb.toString());
        }
    }

    private static Animator createTranslationDisappearAnimator(View view, View view2, int i, int i2) {
        if (i == 3) {
            return createTranslationXAnimator(view2, 0.0f, (float) (-i2));
        }
        if (i == 5) {
            return createTranslationXAnimator(view2, 0.0f, (float) i2);
        }
        if (i == 48) {
            return createTranslationYAnimator(view2, 0.0f, (float) i2);
        }
        if (i == 80) {
            return createTranslationYAnimator(view2, 0.0f, (float) (-i2));
        }
        if (i == 8388611) {
            return createTranslationXAnimator(view2, 0.0f, isRtl(view) ? (float) (-i2) : (float) i2);
        } else if (i == 8388613) {
            return createTranslationXAnimator(view2, 0.0f, isRtl(view) ? (float) i2 : (float) (-i2));
        } else {
            StringBuilder sb = new StringBuilder();
            sb.append("Invalid slide direction: ");
            sb.append(i);
            throw new IllegalArgumentException(sb.toString());
        }
    }

    private static Animator createTranslationXAnimator(View view, float f, float f2) {
        return ObjectAnimator.ofPropertyValuesHolder(view, new PropertyValuesHolder[]{PropertyValuesHolder.ofFloat(View.TRANSLATION_X, new float[]{f, f2})});
    }

    private static Animator createTranslationYAnimator(View view, float f, float f2) {
        return ObjectAnimator.ofPropertyValuesHolder(view, new PropertyValuesHolder[]{PropertyValuesHolder.ofFloat(View.TRANSLATION_Y, new float[]{f, f2})});
    }

    private static boolean isRtl(View view) {
        return ViewCompat.getLayoutDirection(view) == 1;
    }
}
