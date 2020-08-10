package jahirfiquitiva.libs.fabsmenu;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewPropertyAnimator;
import android.widget.FrameLayout;
import android.widget.FrameLayout.LayoutParams;
import androidx.coordinatorlayout.widget.CoordinatorLayout.AttachedBehavior;
import androidx.coordinatorlayout.widget.CoordinatorLayout.Behavior;

public class FABsMenuLayout extends FrameLayout implements AttachedBehavior {
    private static final String TAG = FABsMenuLayout.class.getSimpleName();
    private int animationDuration = 500;
    private boolean clickableOverlay;
    private int overlayColor;
    /* access modifiers changed from: private */
    public View overlayView;

    public FABsMenuLayout(Context context) {
        super(context);
    }

    public FABsMenuLayout(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        init(context, attributeSet);
    }

    public FABsMenuLayout(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        init(context, attributeSet);
    }

    private void init(Context context, AttributeSet attributeSet) {
        TypedArray obtainStyledAttributes = context.getTheme().obtainStyledAttributes(attributeSet, C5959R.styleable.FABsMenuLayout, 0, 0);
        try {
            this.overlayColor = obtainStyledAttributes.getColor(C5959R.styleable.FABsMenuLayout_fabs_menu_overlayColor, Color.parseColor("#4d000000"));
            this.clickableOverlay = obtainStyledAttributes.getBoolean(C5959R.styleable.FABsMenuLayout_fabs_menu_clickableOverlay, true);
        } catch (Exception e) {
            Log.e(TAG, "Failure configuring FABsMenuLayout overlay", e);
        } catch (Throwable th) {
            obtainStyledAttributes.recycle();
            throw th;
        }
        obtainStyledAttributes.recycle();
        this.overlayView = new View(context);
        this.overlayView.setLayoutParams(new LayoutParams(-1, -1));
        this.overlayView.setBackgroundColor(this.overlayColor);
        this.overlayView.setVisibility(8);
        addView(this.overlayView);
    }

    public int getOverlayColor() {
        return this.overlayColor;
    }

    public void setOverlayColor(int i) {
        this.overlayView.setBackgroundColor(i);
        this.overlayColor = i;
    }

    public View getOverlayView() {
        return this.overlayView;
    }

    public void setOverlayView(View view) {
        this.overlayView = view;
    }

    public boolean hasClickableOverlay() {
        return this.clickableOverlay;
    }

    public void setClickableOverlay(boolean z) {
        this.clickableOverlay = z;
    }

    public void setAnimationDuration(int i) {
        this.animationDuration = i;
    }

    public void show() {
        toggle(true);
    }

    public void show(boolean z) {
        toggle(true, z);
    }

    public void hide() {
        toggle(false);
    }

    public void hide(boolean z) {
        toggle(false, z);
    }

    public void toggle(boolean z) {
        toggle(z, false);
    }

    public void toggle(boolean z, boolean z2) {
        toggle(z, z2, null);
    }

    public void toggle(final boolean z, boolean z2, final OnClickListener onClickListener) {
        float f = 0.0f;
        if (z) {
            this.overlayView.setAlpha(0.0f);
            this.overlayView.setVisibility(0);
        }
        ViewPropertyAnimator animate = this.overlayView.animate();
        if (z) {
            f = 1.0f;
        }
        animate.alpha(f).setDuration(z2 ? 0 : (long) this.animationDuration).setListener(new AnimatorListenerAdapter() {
            public void onAnimationEnd(Animator animator) {
                super.onAnimationEnd(animator);
                if (!z) {
                    FABsMenuLayout.this.overlayView.setVisibility(8);
                    FABsMenuLayout.this.overlayView.setOnClickListener(null);
                } else if (FABsMenuLayout.this.hasClickableOverlay()) {
                    FABsMenuLayout.this.overlayView.setOnClickListener(onClickListener);
                }
            }
        }).start();
    }

    public Behavior getBehavior() {
        return new FABSnackbarBehavior();
    }
}
