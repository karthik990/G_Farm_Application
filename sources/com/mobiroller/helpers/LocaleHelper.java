package com.mobiroller.helpers;

import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Build.VERSION;
import android.util.Log;
import com.mobiroller.constants.Constants;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

public class LocaleHelper {
    public static Context setAppLanguage(Context context, String str) {
        if (str.equals("us")) {
            str = "en";
        }
        String lowerCase = str.toLowerCase();
        StringBuilder sb = new StringBuilder();
        sb.append("setAppLanguage :");
        sb.append(lowerCase);
        Log.d("Locale", sb.toString());
        if (VERSION.SDK_INT >= 24) {
            return updateResources(context, lowerCase);
        }
        return updateResourcesLegacy(context, lowerCase);
    }

    public static Context setLocale(Context context) {
        return UtilManager.sharedPrefHelper().getLocaleCodes() != null ? setAppLanguage(context, getLocale()) : context;
    }

    private static Context updateResources(Context context, String str) {
        Locale locale = new Locale(str);
        Locale.setDefault(locale);
        Resources resources = context.getResources();
        Configuration configuration = context.getResources().getConfiguration();
        configuration.setLocale(locale);
        configuration.setLayoutDirection(locale);
        resources.updateConfiguration(configuration, resources.getDisplayMetrics());
        return context.createConfigurationContext(configuration);
    }

    private static Context updateResourcesLegacy(Context context, String str) {
        Locale locale = new Locale(str);
        Locale.setDefault(locale);
        Resources resources = context.getResources();
        Configuration configuration = resources.getConfiguration();
        configuration.locale = locale;
        if (VERSION.SDK_INT >= 17) {
            configuration.setLayoutDirection(locale);
        }
        resources.updateConfiguration(configuration, resources.getDisplayMetrics());
        return context;
    }

    public static Locale getLocale(Resources resources) {
        Configuration configuration = resources.getConfiguration();
        return VERSION.SDK_INT >= 24 ? configuration.getLocales().get(0) : configuration.locale;
    }

    public static String getLocale() {
        boolean languageSetByUser = UtilManager.sharedPrefHelper().getLanguageSetByUser();
        String str = Constants.DISPLAY_LANGUAGE;
        if (languageSetByUser && getLocaleList().contains(UtilManager.sharedPrefHelper().getString(str).toLowerCase())) {
            return UtilManager.sharedPrefHelper().getString(str).toLowerCase();
        }
        if (UtilManager.sharedPrefHelper().getLanguageSetByUser() || UtilManager.sharedPrefHelper().getLocaleCodes() == null) {
            return UtilManager.sharedPrefHelper().getDefaultLang().toLowerCase();
        }
        if (getLocaleList().contains(UtilManager.sharedPrefHelper().getString(str, "").toLowerCase())) {
            return UtilManager.sharedPrefHelper().getString(str).toLowerCase();
        }
        return UtilManager.sharedPrefHelper().getDefaultLang().toLowerCase();
    }

    public static List<String> getLocaleList() {
        return UtilManager.sharedPrefHelper().getLocaleCodes() != null ? Arrays.asList(UtilManager.sharedPrefHelper().getLocaleCodes().toLowerCase().split(",")) : new ArrayList();
    }
}
