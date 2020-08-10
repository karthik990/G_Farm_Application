package com.mobiroller.util;

import android.content.Context;
import android.graphics.Color;
import com.mobiroller.constants.ChatConstants;
import com.mobiroller.constants.ECommerceConstant;
import com.mobiroller.helpers.AppSettingsHelper;
import com.mobiroller.helpers.ECommerceRequestHelper;
import com.mobiroller.helpers.ECommerceRequestHelper.ECommerceCallBack;
import com.mobiroller.helpers.UserHelper;
import com.mobiroller.models.ecommerce.ECommerceErrorResponse;
import com.mobiroller.models.events.ShoppingCartCountEvent;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import org.greenrobot.eventbus.EventBus;
import p043io.paperdb.Book;
import p043io.paperdb.Paper;

public class ECommerceUtil {
    public static int badgeCount;
    public static DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'hh:mm:ss");
    public static DateFormat dateFormatOrderDate = new SimpleDateFormat("dd MMMM yyyy");
    public static DateFormat dateFormatOrderDetail = new SimpleDateFormat("dd MMMM yyyy EEEE, HH:mm");
    public static DateFormat dateFormatOrderTime = new SimpleDateFormat("EEEE, HH:mm");
    public static DateFormat dateFormatSimple = new SimpleDateFormat(ChatConstants.CHAT_LOG_DATE_FORMAT);
    private static DecimalFormat formatter;

    public static String getCurrencySymbol(String str) {
        if (str == null) {
            return "";
        }
        if (str.equalsIgnoreCase("TRY")) {
            return "₺";
        }
        if (str.equalsIgnoreCase("EUR")) {
            return "€";
        }
        if (str.equalsIgnoreCase("USD")) {
            return "$";
        }
        if (str.equalsIgnoreCase("GBP")) {
            return "£";
        }
        if (str.equalsIgnoreCase("IRR")) {
            return "﷼";
        }
        if (str.equalsIgnoreCase("NOK")) {
            return "kr";
        }
        if (str.equalsIgnoreCase("RUB")) {
            str = "₽";
        }
        return str;
    }

    public static String getCurrency(String str) {
        if (str == null) {
            return "";
        }
        if (str.equalsIgnoreCase("TRY")) {
            str = "TL";
        }
        return str;
    }

    public static String getOrderStatus(String str, Context context) {
        return ECommerceConstant.ORDER_STATUS.containsKey(str) ? context.getString(((Integer) ECommerceConstant.ORDER_STATUS.get(str)).intValue()) : str;
    }

    public static int getOrderStatusIcon(String str) {
        if (ECommerceConstant.ORDER_STATUS_ICONS.containsKey(str)) {
            return ((Integer) ECommerceConstant.ORDER_STATUS_ICONS.get(str)).intValue();
        }
        return 0;
    }

    public static int getOrderStatusColor(String str, Context context) {
        if (ECommerceConstant.ORDER_STATUS_RED.containsKey(str)) {
            return ((Integer) ECommerceConstant.ORDER_STATUS_RED.get(str)).intValue();
        }
        if (ECommerceConstant.DELIVERED.equalsIgnoreCase(str)) {
            return Color.parseColor("#4bbf71");
        }
        return Color.parseColor("#464646");
    }

    public static String getPriceString(double d) {
        setDecimalFormat();
        return formatter.format(d);
    }

    private static void setDecimalFormat() {
        if (formatter == null) {
            formatter = (DecimalFormat) NumberFormat.getCurrencyInstance();
            DecimalFormatSymbols decimalFormatSymbols = formatter.getDecimalFormatSymbols();
            decimalFormatSymbols.setCurrencySymbol("");
            formatter.setDecimalFormatSymbols(decimalFormatSymbols);
        }
    }

    public static String[] getSearchSuggestions() {
        Book book = Paper.book(AppSettingsHelper.ECOMMERCE_BOOK_KEY);
        String str = ECommerceConstant.SEARCH_SUGGESTIONS;
        if (!book.contains(str)) {
            return new String[0];
        }
        List list = (List) Paper.book(AppSettingsHelper.ECOMMERCE_BOOK_KEY).read(str, new ArrayList());
        return (String[]) list.toArray(new String[list.size()]);
    }

    public static List<String> getSearchSuggestionList() {
        Book book = Paper.book(AppSettingsHelper.ECOMMERCE_BOOK_KEY);
        String str = ECommerceConstant.SEARCH_SUGGESTIONS;
        if (book.contains(str)) {
            return (List) Paper.book(AppSettingsHelper.ECOMMERCE_BOOK_KEY).read(str, new ArrayList());
        }
        return new ArrayList();
    }

    public static void addSearchSuggestion(String str) {
        List arrayList = new ArrayList();
        Book book = Paper.book(AppSettingsHelper.ECOMMERCE_BOOK_KEY);
        String str2 = ECommerceConstant.SEARCH_SUGGESTIONS;
        if (book.contains(str2)) {
            arrayList = (List) Paper.book(AppSettingsHelper.ECOMMERCE_BOOK_KEY).read(str2, new ArrayList());
        }
        if (!arrayList.contains(str)) {
            arrayList.add(str);
            Paper.book(AppSettingsHelper.ECOMMERCE_BOOK_KEY).write(str2, arrayList);
        }
    }

    public static void removeSearchSuggestion(String str) {
        List arrayList = new ArrayList();
        Book book = Paper.book(AppSettingsHelper.ECOMMERCE_BOOK_KEY);
        String str2 = ECommerceConstant.SEARCH_SUGGESTIONS;
        if (book.contains(str2)) {
            arrayList = (List) Paper.book(AppSettingsHelper.ECOMMERCE_BOOK_KEY).read(str2, new ArrayList());
        }
        if (arrayList.contains(str)) {
            arrayList.remove(str);
            Paper.book(AppSettingsHelper.ECOMMERCE_BOOK_KEY).write(str2, arrayList);
        }
    }

    public static boolean validateCreditCardNumber(String str) {
        int[] iArr = new int[str.length()];
        int i = 0;
        while (i < str.length()) {
            int i2 = i + 1;
            iArr[i] = Integer.parseInt(str.substring(i, i2));
            i = i2;
        }
        int length = iArr.length;
        while (true) {
            length -= 2;
            if (length < 0) {
                break;
            }
            int i3 = iArr[length] * 2;
            if (i3 > 9) {
                i3 = (i3 % 10) + 1;
            }
            iArr[length] = i3;
        }
        int i4 = 0;
        for (int i5 : iArr) {
            i4 += i5;
        }
        if (i4 % 10 == 0) {
            return true;
        }
        return false;
    }

    public void getBadgeCount() {
        if (UserHelper.getUserId() != null) {
            ECommerceRequestHelper eCommerceRequestHelper = new ECommerceRequestHelper();
            eCommerceRequestHelper.enqueue(eCommerceRequestHelper.getAPIService().getShoppingCartCount(UserHelper.getUserId()), new ECommerceCallBack<Integer>() {
                public void done() {
                }

                public void onFailure(ECommerceErrorResponse eCommerceErrorResponse) {
                }

                public void onNetworkError(String str) {
                }

                public void onSuccess(Integer num) {
                    if (num == null) {
                        num = Integer.valueOf(0);
                    }
                    ECommerceUtil.badgeCount = num.intValue();
                    EventBus.getDefault().post(new ShoppingCartCountEvent(num.intValue()));
                }
            });
        }
    }
}
