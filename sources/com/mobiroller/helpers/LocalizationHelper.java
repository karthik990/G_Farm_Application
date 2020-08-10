package com.mobiroller.helpers;

import java.util.List;
import java.util.Locale;

public class LocalizationHelper {
    public String getLocalizedTitle(String str) {
        String str2 = "";
        if (str == null) {
            return str2;
        }
        String str3 = "<";
        if (!str.contains(str3)) {
            return str;
        }
        StringBuilder sb = new StringBuilder();
        sb.append(str3);
        sb.append(LocaleHelper.getLocale().toUpperCase());
        sb.append(">");
        String[] split = str.split(sb.toString());
        if (split.length <= 1) {
            return str2;
        }
        int length = split.length - 2;
        if (length > 0) {
            str = split[length];
        }
        return str;
    }

    public String getLocalizedTitlePreview(String str) {
        String str2 = "TR";
        if (!Locale.getDefault().getLanguage().toUpperCase().equalsIgnoreCase(str2)) {
            str2 = "EN";
        }
        String str3 = "";
        if (str == null) {
            return str3;
        }
        String str4 = "<";
        if (!str.contains(str4)) {
            return str;
        }
        List localeList = LocaleHelper.getLocaleList();
        StringBuilder sb = new StringBuilder();
        sb.append(str4);
        sb.append(str2.toUpperCase());
        String str5 = ">";
        sb.append(str5);
        String[] split = str.split(sb.toString());
        if (split.length <= 1) {
            if (localeList.contains(str2.toLowerCase())) {
                return str3;
            }
            StringBuilder sb2 = new StringBuilder();
            sb2.append(str4);
            sb2.append(str2.toUpperCase());
            sb2.append(str5);
            split = str.split(sb2.toString());
        }
        int length = split.length - 2;
        if (length > 0) {
            str = split[length];
        }
        return str;
    }
}
