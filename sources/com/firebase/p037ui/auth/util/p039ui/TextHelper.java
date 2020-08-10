package com.firebase.p037ui.auth.util.p039ui;

import android.text.SpannableStringBuilder;
import android.text.style.StyleSpan;

/* renamed from: com.firebase.ui.auth.util.ui.TextHelper */
public class TextHelper {
    public static void boldAllOccurencesOfText(SpannableStringBuilder spannableStringBuilder, String str, String str2) {
        int i = 0;
        while (i < str.length()) {
            int indexOf = str.indexOf(str2, i);
            int length = str2.length() + indexOf;
            if (indexOf != -1 && length < str.length()) {
                spannableStringBuilder.setSpan(new StyleSpan(1), indexOf, length, 17);
                i = length + 1;
            } else {
                return;
            }
        }
    }
}
