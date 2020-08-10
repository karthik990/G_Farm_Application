package com.github.ybq.android.spinkit.style;

import android.animation.ValueAnimator;
import android.graphics.Rect;
import android.os.Build.VERSION;
import android.view.animation.LinearInterpolator;
import com.github.ybq.android.spinkit.animation.SpriteAnimatorBuilder;
import com.github.ybq.android.spinkit.sprite.CircleSprite;
import com.github.ybq.android.spinkit.sprite.Sprite;
import com.github.ybq.android.spinkit.sprite.SpriteContainer;
import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection;

public class ChasingDots extends SpriteContainer {

    private class Dot extends CircleSprite {
        Dot() {
            setScale(0.0f);
        }

        public ValueAnimator onCreateAnimation() {
            float[] fArr = {0.0f, 0.5f, 1.0f};
            SpriteAnimatorBuilder spriteAnimatorBuilder = new SpriteAnimatorBuilder(this);
            Float valueOf = Float.valueOf(0.0f);
            return spriteAnimatorBuilder.scale(fArr, valueOf, Float.valueOf(1.0f), valueOf).duration(AdaptiveTrackSelection.DEFAULT_MIN_TIME_BETWEEN_BUFFER_REEVALUTATION_MS).easeInOut(fArr).build();
        }
    }

    public Sprite[] onCreateChild() {
        return new Sprite[]{new Dot(), new Dot()};
    }

    public void onChildCreated(Sprite... spriteArr) {
        super.onChildCreated(spriteArr);
        if (VERSION.SDK_INT >= 24) {
            spriteArr[1].setAnimationDelay(1000);
        } else {
            spriteArr[1].setAnimationDelay(-1000);
        }
    }

    public ValueAnimator onCreateAnimation() {
        float[] fArr = {0.0f, 1.0f};
        return new SpriteAnimatorBuilder(this).rotate(fArr, Integer.valueOf(0), Integer.valueOf(360)).duration(AdaptiveTrackSelection.DEFAULT_MIN_TIME_BETWEEN_BUFFER_REEVALUTATION_MS).interpolator(new LinearInterpolator()).build();
    }

    /* access modifiers changed from: protected */
    public void onBoundsChange(Rect rect) {
        super.onBoundsChange(rect);
        Rect clipSquare = clipSquare(rect);
        int width = (int) (((float) clipSquare.width()) * 0.6f);
        getChildAt(0).setDrawBounds(clipSquare.right - width, clipSquare.top, clipSquare.right, clipSquare.top + width);
        getChildAt(1).setDrawBounds(clipSquare.right - width, clipSquare.bottom - width, clipSquare.right, clipSquare.bottom);
    }
}
