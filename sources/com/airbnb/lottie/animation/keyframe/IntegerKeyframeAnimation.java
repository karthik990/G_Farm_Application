package com.airbnb.lottie.animation.keyframe;

import com.airbnb.lottie.utils.MiscUtils;
import com.airbnb.lottie.value.Keyframe;
import java.util.List;

public class IntegerKeyframeAnimation extends KeyframeAnimation<Integer> {
    public IntegerKeyframeAnimation(List<Keyframe<Integer>> list) {
        super(list);
    }

    /* access modifiers changed from: 0000 */
    public Integer getValue(Keyframe<Integer> keyframe, float f) {
        if (keyframe.startValue == null || keyframe.endValue == null) {
            throw new IllegalStateException("Missing values for keyframe.");
        } else if (this.valueCallback == null) {
            return Integer.valueOf(MiscUtils.lerp(((Integer) keyframe.startValue).intValue(), ((Integer) keyframe.endValue).intValue(), f));
        } else {
            return (Integer) this.valueCallback.getValueInternal(keyframe.startFrame, keyframe.endFrame.floatValue(), keyframe.startValue, keyframe.endValue, f, getLinearCurrentKeyframeProgress(), getProgress());
        }
    }
}
