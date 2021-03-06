package com.nineoldandroids.view;

import android.view.View;
import android.view.animation.Interpolator;
import com.nineoldandroids.animation.Animator;
import com.nineoldandroids.animation.Animator.AnimatorListener;
import com.nineoldandroids.animation.ValueAnimator;
import com.nineoldandroids.animation.ValueAnimator.AnimatorUpdateListener;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

class ViewPropertyAnimatorHC extends ViewPropertyAnimator {
    private static final int ALPHA = 512;
    private static final int NONE = 0;
    private static final int ROTATION = 16;
    private static final int ROTATION_X = 32;
    private static final int ROTATION_Y = 64;
    private static final int SCALE_X = 4;
    private static final int SCALE_Y = 8;
    private static final int TRANSFORM_MASK = 511;
    private static final int TRANSLATION_X = 1;
    private static final int TRANSLATION_Y = 2;

    /* renamed from: X */
    private static final int f2247X = 128;

    /* renamed from: Y */
    private static final int f2248Y = 256;
    private Runnable mAnimationStarter = new Runnable() {
        public void run() {
            ViewPropertyAnimatorHC.this.startAnimation();
        }
    };
    private AnimatorEventListener mAnimatorEventListener = new AnimatorEventListener();
    /* access modifiers changed from: private */
    public HashMap<Animator, PropertyBundle> mAnimatorMap = new HashMap<>();
    private long mDuration;
    private boolean mDurationSet = false;
    private Interpolator mInterpolator;
    private boolean mInterpolatorSet = false;
    /* access modifiers changed from: private */
    public AnimatorListener mListener = null;
    ArrayList<NameValuesHolder> mPendingAnimations = new ArrayList<>();
    private long mStartDelay = 0;
    private boolean mStartDelaySet = false;
    /* access modifiers changed from: private */
    public final WeakReference<View> mView;

    private class AnimatorEventListener implements AnimatorListener, AnimatorUpdateListener {
        private AnimatorEventListener() {
        }

        public void onAnimationStart(Animator animator) {
            if (ViewPropertyAnimatorHC.this.mListener != null) {
                ViewPropertyAnimatorHC.this.mListener.onAnimationStart(animator);
            }
        }

        public void onAnimationCancel(Animator animator) {
            if (ViewPropertyAnimatorHC.this.mListener != null) {
                ViewPropertyAnimatorHC.this.mListener.onAnimationCancel(animator);
            }
        }

        public void onAnimationRepeat(Animator animator) {
            if (ViewPropertyAnimatorHC.this.mListener != null) {
                ViewPropertyAnimatorHC.this.mListener.onAnimationRepeat(animator);
            }
        }

        public void onAnimationEnd(Animator animator) {
            if (ViewPropertyAnimatorHC.this.mListener != null) {
                ViewPropertyAnimatorHC.this.mListener.onAnimationEnd(animator);
            }
            ViewPropertyAnimatorHC.this.mAnimatorMap.remove(animator);
            if (ViewPropertyAnimatorHC.this.mAnimatorMap.isEmpty()) {
                ViewPropertyAnimatorHC.this.mListener = null;
            }
        }

        public void onAnimationUpdate(ValueAnimator valueAnimator) {
            float animatedFraction = valueAnimator.getAnimatedFraction();
            PropertyBundle propertyBundle = (PropertyBundle) ViewPropertyAnimatorHC.this.mAnimatorMap.get(valueAnimator);
            if ((propertyBundle.mPropertyMask & 511) != 0) {
                View view = (View) ViewPropertyAnimatorHC.this.mView.get();
                if (view != null) {
                    view.invalidate();
                }
            }
            ArrayList<NameValuesHolder> arrayList = propertyBundle.mNameValuesHolder;
            if (arrayList != null) {
                int size = arrayList.size();
                for (int i = 0; i < size; i++) {
                    NameValuesHolder nameValuesHolder = (NameValuesHolder) arrayList.get(i);
                    ViewPropertyAnimatorHC.this.setValue(nameValuesHolder.mNameConstant, nameValuesHolder.mFromValue + (nameValuesHolder.mDeltaValue * animatedFraction));
                }
            }
            View view2 = (View) ViewPropertyAnimatorHC.this.mView.get();
            if (view2 != null) {
                view2.invalidate();
            }
        }
    }

    private static class NameValuesHolder {
        float mDeltaValue;
        float mFromValue;
        int mNameConstant;

        NameValuesHolder(int i, float f, float f2) {
            this.mNameConstant = i;
            this.mFromValue = f;
            this.mDeltaValue = f2;
        }
    }

    private static class PropertyBundle {
        ArrayList<NameValuesHolder> mNameValuesHolder;
        int mPropertyMask;

        PropertyBundle(int i, ArrayList<NameValuesHolder> arrayList) {
            this.mPropertyMask = i;
            this.mNameValuesHolder = arrayList;
        }

        /* access modifiers changed from: 0000 */
        public boolean cancel(int i) {
            if ((this.mPropertyMask & i) != 0) {
                ArrayList<NameValuesHolder> arrayList = this.mNameValuesHolder;
                if (arrayList != null) {
                    int size = arrayList.size();
                    for (int i2 = 0; i2 < size; i2++) {
                        if (((NameValuesHolder) this.mNameValuesHolder.get(i2)).mNameConstant == i) {
                            this.mNameValuesHolder.remove(i2);
                            this.mPropertyMask = (i ^ -1) & this.mPropertyMask;
                            return true;
                        }
                    }
                }
            }
            return false;
        }
    }

    ViewPropertyAnimatorHC(View view) {
        this.mView = new WeakReference<>(view);
    }

    public ViewPropertyAnimator setDuration(long j) {
        if (j >= 0) {
            this.mDurationSet = true;
            this.mDuration = j;
            return this;
        }
        StringBuilder sb = new StringBuilder();
        sb.append("Animators cannot have negative duration: ");
        sb.append(j);
        throw new IllegalArgumentException(sb.toString());
    }

    public long getDuration() {
        if (this.mDurationSet) {
            return this.mDuration;
        }
        return new ValueAnimator().getDuration();
    }

    public long getStartDelay() {
        if (this.mStartDelaySet) {
            return this.mStartDelay;
        }
        return 0;
    }

    public ViewPropertyAnimator setStartDelay(long j) {
        if (j >= 0) {
            this.mStartDelaySet = true;
            this.mStartDelay = j;
            return this;
        }
        StringBuilder sb = new StringBuilder();
        sb.append("Animators cannot have negative duration: ");
        sb.append(j);
        throw new IllegalArgumentException(sb.toString());
    }

    public ViewPropertyAnimator setInterpolator(Interpolator interpolator) {
        this.mInterpolatorSet = true;
        this.mInterpolator = interpolator;
        return this;
    }

    public ViewPropertyAnimator setListener(AnimatorListener animatorListener) {
        this.mListener = animatorListener;
        return this;
    }

    public void start() {
        startAnimation();
    }

    public void cancel() {
        if (this.mAnimatorMap.size() > 0) {
            for (Animator cancel : ((HashMap) this.mAnimatorMap.clone()).keySet()) {
                cancel.cancel();
            }
        }
        this.mPendingAnimations.clear();
        View view = (View) this.mView.get();
        if (view != null) {
            view.removeCallbacks(this.mAnimationStarter);
        }
    }

    /* renamed from: x */
    public ViewPropertyAnimator mo58582x(float f) {
        animateProperty(128, f);
        return this;
    }

    public ViewPropertyAnimator xBy(float f) {
        animatePropertyBy(128, f);
        return this;
    }

    /* renamed from: y */
    public ViewPropertyAnimator mo58584y(float f) {
        animateProperty(256, f);
        return this;
    }

    public ViewPropertyAnimator yBy(float f) {
        animatePropertyBy(256, f);
        return this;
    }

    public ViewPropertyAnimator rotation(float f) {
        animateProperty(16, f);
        return this;
    }

    public ViewPropertyAnimator rotationBy(float f) {
        animatePropertyBy(16, f);
        return this;
    }

    public ViewPropertyAnimator rotationX(float f) {
        animateProperty(32, f);
        return this;
    }

    public ViewPropertyAnimator rotationXBy(float f) {
        animatePropertyBy(32, f);
        return this;
    }

    public ViewPropertyAnimator rotationY(float f) {
        animateProperty(64, f);
        return this;
    }

    public ViewPropertyAnimator rotationYBy(float f) {
        animatePropertyBy(64, f);
        return this;
    }

    public ViewPropertyAnimator translationX(float f) {
        animateProperty(1, f);
        return this;
    }

    public ViewPropertyAnimator translationXBy(float f) {
        animatePropertyBy(1, f);
        return this;
    }

    public ViewPropertyAnimator translationY(float f) {
        animateProperty(2, f);
        return this;
    }

    public ViewPropertyAnimator translationYBy(float f) {
        animatePropertyBy(2, f);
        return this;
    }

    public ViewPropertyAnimator scaleX(float f) {
        animateProperty(4, f);
        return this;
    }

    public ViewPropertyAnimator scaleXBy(float f) {
        animatePropertyBy(4, f);
        return this;
    }

    public ViewPropertyAnimator scaleY(float f) {
        animateProperty(8, f);
        return this;
    }

    public ViewPropertyAnimator scaleYBy(float f) {
        animatePropertyBy(8, f);
        return this;
    }

    public ViewPropertyAnimator alpha(float f) {
        animateProperty(512, f);
        return this;
    }

    public ViewPropertyAnimator alphaBy(float f) {
        animatePropertyBy(512, f);
        return this;
    }

    /* access modifiers changed from: private */
    public void startAnimation() {
        ValueAnimator ofFloat = ValueAnimator.ofFloat(1.0f);
        ArrayList arrayList = (ArrayList) this.mPendingAnimations.clone();
        this.mPendingAnimations.clear();
        int i = 0;
        for (int i2 = 0; i2 < arrayList.size(); i2++) {
            i |= ((NameValuesHolder) arrayList.get(i2)).mNameConstant;
        }
        this.mAnimatorMap.put(ofFloat, new PropertyBundle(i, arrayList));
        ofFloat.addUpdateListener(this.mAnimatorEventListener);
        ofFloat.addListener(this.mAnimatorEventListener);
        if (this.mStartDelaySet) {
            ofFloat.setStartDelay(this.mStartDelay);
        }
        if (this.mDurationSet) {
            ofFloat.setDuration(this.mDuration);
        }
        if (this.mInterpolatorSet) {
            ofFloat.setInterpolator(this.mInterpolator);
        }
        ofFloat.start();
    }

    private void animateProperty(int i, float f) {
        float value = getValue(i);
        animatePropertyBy(i, value, f - value);
    }

    private void animatePropertyBy(int i, float f) {
        animatePropertyBy(i, getValue(i), f);
    }

    private void animatePropertyBy(int i, float f, float f2) {
        if (this.mAnimatorMap.size() > 0) {
            Animator animator = null;
            Iterator it = this.mAnimatorMap.keySet().iterator();
            while (true) {
                if (!it.hasNext()) {
                    break;
                }
                Animator animator2 = (Animator) it.next();
                PropertyBundle propertyBundle = (PropertyBundle) this.mAnimatorMap.get(animator2);
                if (propertyBundle.cancel(i) && propertyBundle.mPropertyMask == 0) {
                    animator = animator2;
                    break;
                }
            }
            if (animator != null) {
                animator.cancel();
            }
        }
        this.mPendingAnimations.add(new NameValuesHolder(i, f, f2));
        View view = (View) this.mView.get();
        if (view != null) {
            view.removeCallbacks(this.mAnimationStarter);
            view.post(this.mAnimationStarter);
        }
    }

    /* access modifiers changed from: private */
    public void setValue(int i, float f) {
        View view = (View) this.mView.get();
        if (view == null) {
            return;
        }
        if (i == 1) {
            view.setTranslationX(f);
        } else if (i == 2) {
            view.setTranslationY(f);
        } else if (i == 4) {
            view.setScaleX(f);
        } else if (i == 8) {
            view.setScaleY(f);
        } else if (i == 16) {
            view.setRotation(f);
        } else if (i == 32) {
            view.setRotationX(f);
        } else if (i == 64) {
            view.setRotationY(f);
        } else if (i == 128) {
            view.setX(f);
        } else if (i == 256) {
            view.setY(f);
        } else if (i == 512) {
            view.setAlpha(f);
        }
    }

    private float getValue(int i) {
        View view = (View) this.mView.get();
        if (view != null) {
            if (i == 1) {
                return view.getTranslationX();
            }
            if (i == 2) {
                return view.getTranslationY();
            }
            if (i == 4) {
                return view.getScaleX();
            }
            if (i == 8) {
                return view.getScaleY();
            }
            if (i == 16) {
                return view.getRotation();
            }
            if (i == 32) {
                return view.getRotationX();
            }
            if (i == 64) {
                return view.getRotationY();
            }
            if (i == 128) {
                return view.getX();
            }
            if (i == 256) {
                return view.getY();
            }
            if (i == 512) {
                return view.getAlpha();
            }
        }
        return 0.0f;
    }
}
