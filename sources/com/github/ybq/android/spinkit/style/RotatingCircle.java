package com.github.ybq.android.spinkit.style;

import android.animation.ValueAnimator;
import com.github.ybq.android.spinkit.animation.SpriteAnimatorBuilder;
import com.github.ybq.android.spinkit.sprite.CircleSprite;

public class RotatingCircle extends CircleSprite {
    public ValueAnimator onCreateAnimation() {
        float[] fArr = {0.0f, 0.5f, 1.0f};
        SpriteAnimatorBuilder spriteAnimatorBuilder = new SpriteAnimatorBuilder(this);
        Integer valueOf = Integer.valueOf(0);
        Integer valueOf2 = Integer.valueOf(-180);
        return spriteAnimatorBuilder.rotateX(fArr, valueOf, valueOf2, valueOf2).rotateY(fArr, valueOf, valueOf, valueOf2).duration(1200).easeInOut(fArr).build();
    }
}
