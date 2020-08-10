package jahirfiquitiva.libs.fabsmenu;

import android.content.Context;

class DimensionUtils {
    DimensionUtils() {
    }

    static float convertDpToPixel(float f, Context context) {
        return f * (((float) context.getResources().getDisplayMetrics().densityDpi) / 160.0f);
    }

    static float convertPixelsToDp(float f, Context context) {
        return f / (((float) context.getResources().getDisplayMetrics().densityDpi) / 160.0f);
    }
}
