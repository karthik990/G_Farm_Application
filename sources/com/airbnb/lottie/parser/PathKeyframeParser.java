package com.airbnb.lottie.parser;

import android.util.JsonReader;
import android.util.JsonToken;
import com.airbnb.lottie.LottieComposition;
import com.airbnb.lottie.animation.keyframe.PathKeyframe;
import com.airbnb.lottie.utils.C0894Utils;
import java.io.IOException;

class PathKeyframeParser {
    private PathKeyframeParser() {
    }

    static PathKeyframe parse(JsonReader jsonReader, LottieComposition lottieComposition) throws IOException {
        return new PathKeyframe(lottieComposition, KeyframeParser.parse(jsonReader, lottieComposition, C0894Utils.dpScale(), PathParser.INSTANCE, jsonReader.peek() == JsonToken.BEGIN_OBJECT));
    }
}
