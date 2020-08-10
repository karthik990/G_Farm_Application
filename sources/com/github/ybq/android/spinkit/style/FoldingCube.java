package com.github.ybq.android.spinkit.style;

import android.animation.ValueAnimator;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.os.Build.VERSION;
import android.view.animation.LinearInterpolator;
import com.github.ybq.android.spinkit.animation.SpriteAnimatorBuilder;
import com.github.ybq.android.spinkit.sprite.RectSprite;
import com.github.ybq.android.spinkit.sprite.Sprite;
import com.github.ybq.android.spinkit.sprite.SpriteContainer;
import org.objectweb.asm.Opcodes;

public class FoldingCube extends SpriteContainer {
    private boolean wrapContent = false;

    private class Cube extends RectSprite {
        Cube() {
            setAlpha(0);
            setRotateX(-180);
        }

        public ValueAnimator onCreateAnimation() {
            float[] fArr = {0.0f, 0.1f, 0.25f, 0.75f, 0.9f, 1.0f};
            SpriteAnimatorBuilder spriteAnimatorBuilder = new SpriteAnimatorBuilder(this);
            Integer valueOf = Integer.valueOf(0);
            Integer valueOf2 = Integer.valueOf(255);
            SpriteAnimatorBuilder alpha = spriteAnimatorBuilder.alpha(fArr, valueOf, valueOf, valueOf2, valueOf2, valueOf, valueOf);
            Integer valueOf3 = Integer.valueOf(-180);
            SpriteAnimatorBuilder rotateX = alpha.rotateX(fArr, valueOf3, valueOf3, valueOf, valueOf, valueOf, valueOf);
            Integer valueOf4 = Integer.valueOf(Opcodes.GETFIELD);
            return rotateX.rotateY(fArr, valueOf, valueOf, valueOf, valueOf, valueOf4, valueOf4).duration(2400).interpolator(new LinearInterpolator()).build();
        }
    }

    public Sprite[] onCreateChild() {
        Cube[] cubeArr = new Cube[4];
        for (int i = 0; i < cubeArr.length; i++) {
            cubeArr[i] = new Cube();
            if (VERSION.SDK_INT >= 24) {
                cubeArr[i].setAnimationDelay(i * 300);
            } else {
                cubeArr[i].setAnimationDelay((i * 300) - 1200);
            }
        }
        return cubeArr;
    }

    /* access modifiers changed from: protected */
    public void onBoundsChange(Rect rect) {
        super.onBoundsChange(rect);
        Rect clipSquare = clipSquare(rect);
        int min = Math.min(clipSquare.width(), clipSquare.height());
        if (this.wrapContent) {
            min = (int) Math.sqrt((double) ((min * min) / 2));
            int width = (clipSquare.width() - min) / 2;
            int height = (clipSquare.height() - min) / 2;
            clipSquare = new Rect(clipSquare.left + width, clipSquare.top + height, clipSquare.right - width, clipSquare.bottom - height);
        }
        int i = min / 2;
        int i2 = clipSquare.left + i + 1;
        int i3 = clipSquare.top + i + 1;
        for (int i4 = 0; i4 < getChildCount(); i4++) {
            Sprite childAt = getChildAt(i4);
            childAt.setDrawBounds(clipSquare.left, clipSquare.top, i2, i3);
            childAt.setPivotX((float) childAt.getDrawBounds().right);
            childAt.setPivotY((float) childAt.getDrawBounds().bottom);
        }
    }

    public void drawChild(Canvas canvas) {
        Rect clipSquare = clipSquare(getBounds());
        for (int i = 0; i < getChildCount(); i++) {
            int save = canvas.save();
            canvas.rotate((float) ((i * 90) + 45), (float) clipSquare.centerX(), (float) clipSquare.centerY());
            getChildAt(i).draw(canvas);
            canvas.restoreToCount(save);
        }
    }
}
