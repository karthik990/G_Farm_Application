package com.github.ybq.android.spinkit.style;

import android.animation.ValueAnimator;
import android.graphics.Rect;
import com.github.ybq.android.spinkit.animation.SpriteAnimatorBuilder;
import com.github.ybq.android.spinkit.sprite.RectSprite;

public class RotatingPlane extends RectSprite {
    /* access modifiers changed from: protected */
    public void onBoundsChange(Rect rect) {
        setDrawBounds(clipSquare(rect));
    }

    public ValueAnimator onCreateAnimation() {
        float[] fArr = {0.0f, 0.5f, 1.0f};
        SpriteAnimatorBuilder spriteAnimatorBuilder = new SpriteAnimatorBuilder(this);
        Integer valueOf = Integer.valueOf(0);
        Integer valueOf2 = Integer.valueOf(-180);
        return spriteAnimatorBuilder.rotateX(fArr, valueOf, valueOf2, valueOf2).rotateY(fArr, valueOf, valueOf, valueOf2).duration(1200).easeInOut(fArr).build();
    }
}
