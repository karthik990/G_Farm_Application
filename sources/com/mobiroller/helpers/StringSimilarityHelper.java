package com.mobiroller.helpers;

public class StringSimilarityHelper {
    public static double similarity(String str, String str2) {
        if (str.length() < str2.length()) {
            String str3 = str2;
            str2 = str;
            str = str3;
        }
        int length = str.length();
        if (length == 0) {
            return 1.0d;
        }
        double editDistance = (double) (length - editDistance(str, str2));
        double d = (double) length;
        Double.isNaN(editDistance);
        Double.isNaN(d);
        return editDistance / d;
    }

    public static int editDistance(String str, String str2) {
        String lowerCase = str.toLowerCase();
        String lowerCase2 = str2.toLowerCase();
        int[] iArr = new int[(lowerCase2.length() + 1)];
        for (int i = 0; i <= lowerCase.length(); i++) {
            int i2 = i;
            for (int i3 = 0; i3 <= lowerCase2.length(); i3++) {
                if (i == 0) {
                    iArr[i3] = i3;
                } else if (i3 > 0) {
                    int i4 = i3 - 1;
                    int i5 = iArr[i4];
                    if (lowerCase.charAt(i - 1) != lowerCase2.charAt(i4)) {
                        i5 = Math.min(Math.min(i5, i2), iArr[i3]) + 1;
                    }
                    iArr[i4] = i2;
                    i2 = i5;
                }
            }
            if (i > 0) {
                iArr[lowerCase2.length()] = i2;
            }
        }
        return iArr[lowerCase2.length()];
    }

    public static void printSimilarity(String str, String str2) {
        System.out.println(String.format("%.3f is the similarity between \"%s\" and \"%s\"", new Object[]{Double.valueOf(similarity(str, str2)), str, str2}));
    }
}
