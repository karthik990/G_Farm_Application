package com.mobiroller.helpers;

import android.content.Context;
import android.graphics.Paint;
import android.text.TextUtils;
import android.util.TypedValue;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TextHelper {
    public static List<String> splitWordsIntoStringsThatFit(String str, float f, Paint paint) {
        String[] split;
        ArrayList arrayList = new ArrayList();
        ArrayList arrayList2 = new ArrayList();
        for (String str2 : str.split("\\s")) {
            if (paint.measureText(str2) < f) {
                processFitChunk(f, paint, arrayList, arrayList2, str2);
            } else {
                for (String processFitChunk : splitIntoStringsThatFit(str2, f, paint)) {
                    processFitChunk(f, paint, arrayList, arrayList2, processFitChunk);
                }
            }
        }
        if (!arrayList2.isEmpty()) {
            arrayList.add(TextUtils.join(" ", arrayList2));
        }
        return arrayList;
    }

    private static List<String> splitIntoStringsThatFit(String str, float f, Paint paint) {
        int i = 0;
        if (TextUtils.isEmpty(str) || paint.measureText(str) <= f) {
            return Arrays.asList(new String[]{str});
        }
        ArrayList arrayList = new ArrayList();
        for (int i2 = 1; i2 <= str.length(); i2++) {
            if (paint.measureText(str.substring(i, i2)) >= f) {
                int i3 = i2 - 1;
                arrayList.add(str.substring(i, i3));
                i = i3;
            }
            if (i2 == str.length()) {
                arrayList.add(str.substring(i, i2));
            }
        }
        return arrayList;
    }

    private static void processFitChunk(float f, Paint paint, ArrayList<String> arrayList, ArrayList<String> arrayList2, String str) {
        arrayList2.add(str);
        String str2 = " ";
        if (paint.measureText(TextUtils.join(str2, arrayList2)) >= f) {
            arrayList2.remove(arrayList2.size() - 1);
            arrayList.add(TextUtils.join(str2, arrayList2));
            arrayList2.clear();
            arrayList2.add(str);
        }
    }

    public static int convertSpToPixels(float f, Context context) {
        return (int) TypedValue.applyDimension(2, f, context.getResources().getDisplayMetrics());
    }

    public static int convertDpToPixels(float f, Context context) {
        return (int) TypedValue.applyDimension(1, f, context.getResources().getDisplayMetrics());
    }
}
