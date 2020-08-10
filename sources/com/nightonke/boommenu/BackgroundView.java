package com.nightonke.boommenu;

import android.animation.AnimatorListenerAdapter;
import android.animation.ArgbEvaluator;
import android.animation.TypeEvaluator;
import android.content.Context;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.FrameLayout;
import com.nightonke.boommenu.Animation.AnimationManager;

class BackgroundView extends FrameLayout {
    private int dimColor;

    protected BackgroundView(Context context, final BoomMenuButton boomMenuButton) {
        super(context);
        this.dimColor = boomMenuButton.getDimColor();
        ViewGroup parentView = boomMenuButton.getParentView();
        setLayoutParams(new LayoutParams(parentView.getWidth(), parentView.getHeight()));
        setBackgroundColor(0);
        setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                boomMenuButton.onBackgroundClicked();
            }
        });
        setMotionEventSplittingEnabled(false);
        parentView.addView(this);
    }

    /* access modifiers changed from: protected */
    public void reLayout(BoomMenuButton boomMenuButton) {
        ViewGroup parentView = boomMenuButton.getParentView();
        FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) getLayoutParams();
        layoutParams.width = parentView.getWidth();
        layoutParams.height = parentView.getHeight();
        setLayoutParams(layoutParams);
    }

    /* access modifiers changed from: protected */
    public void dim(long j, AnimatorListenerAdapter animatorListenerAdapter) {
        setVisibility(0);
        AnimationManager.animate((Object) this, TtmlNode.ATTR_TTS_BACKGROUND_COLOR, 0, j, (TypeEvaluator) new ArgbEvaluator(), animatorListenerAdapter, 0, this.dimColor);
    }

    /* access modifiers changed from: protected */
    public void light(long j, AnimatorListenerAdapter animatorListenerAdapter) {
        AnimationManager.animate((Object) this, TtmlNode.ATTR_TTS_BACKGROUND_COLOR, 0, j, (TypeEvaluator) new ArgbEvaluator(), animatorListenerAdapter, this.dimColor, 0);
    }
}
