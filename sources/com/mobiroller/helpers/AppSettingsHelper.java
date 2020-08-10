package com.mobiroller.helpers;

import p043io.paperdb.Paper;

public class AppSettingsHelper {
    public static String ECOMMERCE_BOOK_KEY = "eCommerceBook";
    private static String isECommerceActiveKey = "isECommerceActiveKey";

    public static boolean isECommerceActive() {
        if (Paper.book(ECOMMERCE_BOOK_KEY).contains(isECommerceActiveKey)) {
            return ((Boolean) Paper.book(ECOMMERCE_BOOK_KEY).read(isECommerceActiveKey, Boolean.valueOf(false))).booleanValue();
        }
        return false;
    }

    public static void setIsECommerceActive(boolean z) {
        Paper.book(ECOMMERCE_BOOK_KEY).write(isECommerceActiveKey, Boolean.valueOf(z));
    }
}
