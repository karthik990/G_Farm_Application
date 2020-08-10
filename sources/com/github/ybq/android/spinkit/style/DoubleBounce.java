package com.github.ybq.android.spinkit.style;

import android.animation.ValueAnimator;
import android.os.Build.VERSION;
import com.github.ybq.android.spinkit.animation.SpriteAnimatorBuilder;
import com.github.ybq.android.spinkit.sprite.CircleSprite;
import com.github.ybq.android.spinkit.sprite.Sprite;
import com.github.ybq.android.spinkit.sprite.SpriteContainer;
import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection;
import org.objectweb.asm.Opcodes;

public class DoubleBounce extends SpriteContainer {

    private class Bounce extends CircleSprite {
        Bounce() {
            setAlpha(Opcodes.IFEQ);
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
        return new Sprite[]{new Bounce(), new Bounce()};
    }

    public void onChildCreated(Sprite... spriteArr) {
        super.onChildCreated(spriteArr);
        if (VERSION.SDK_INT >= 24) {
            spriteArr[1].setAnimationDelay(1000);
        } else {
            spriteArr[1].setAnimationDelay(-1000);
        }
    }
}
