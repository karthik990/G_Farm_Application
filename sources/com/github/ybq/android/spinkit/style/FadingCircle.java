package com.github.ybq.android.spinkit.style;

import android.animation.ValueAnimator;
import android.os.Build.VERSION;
import com.github.ybq.android.spinkit.animation.SpriteAnimatorBuilder;
import com.github.ybq.android.spinkit.sprite.CircleLayoutContainer;
import com.github.ybq.android.spinkit.sprite.CircleSprite;
import com.github.ybq.android.spinkit.sprite.Sprite;

public class FadingCircle extends CircleLayoutContainer {

    private class Dot extends CircleSprite {
        Dot() {
            setAlpha(0);
        }

        public ValueAnimator onCreateAnimation() {
            float[] fArr = {0.0f, 0.39f, 0.4f, 1.0f};
            SpriteAnimatorBuilder spriteAnimatorBuilder = new SpriteAnimatorBuilder(this);
            Integer valueOf = Integer.valueOf(0);
            return spriteAnimatorBuilder.alpha(fArr, valueOf, valueOf, Integer.valueOf(255), valueOf).duration(1200).easeInOut(fArr).build();
        }
    }

    public Sprite[] onCreateChild() {
        Dot[] dotArr = new Dot[12];
        for (int i = 0; i < dotArr.length; i++) {
            dotArr[i] = new Dot();
            if (VERSION.SDK_INT >= 24) {
                dotArr[i].setAnimationDelay(i * 100);
            } else {
                dotArr[i].setAnimationDelay((i * 100) - 1200);
            }
        }
        return dotArr;
    }
}
