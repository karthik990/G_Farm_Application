package com.mobiroller.helpers;

import android.content.Context;
import com.mobiroller.constants.Constants;
import com.mobiroller.models.InAppPurchaseModel;
import com.mobiroller.models.MainModel;
import com.mobiroller.models.MobirollerBadgeModel;
import com.mobiroller.models.NavigationModel;
import com.mobiroller.models.ScreenModel;
import com.mobiroller.util.MenuUtil;
import p043io.paperdb.Book;
import p043io.paperdb.Paper;
import timber.log.Timber;

public class JSONStorage {
    private static final String IN_APP_PURCHASE_MODEL_KEY = "InAppPurchaseModel";
    private static final String MAIN_MODEL_KEY = "MainModel";
    private static final String MOBIROLLER_BADGE_MODEL_KEY = "MobirollerBadgeModel";
    private static final String NAVIGATION_MODEL_KEY = "NavigationModel";
    private static final String PAPER_SCREEN_BOOK_TAG = "screens";
    private static final String TAG = "JSONStorage";

    public static final class Builder {
        private Context mContext;

        public Builder setContext(Context context) {
            this.mContext = context;
            return this;
        }

        public void build() {
            Context context = this.mContext;
            if (context != null) {
                JSONStorage.initStorage(context);
                return;
            }
            throw new RuntimeException("Context not set, please set context before building the Prefs instance.");
        }
    }

    /* access modifiers changed from: private */
    public static void initStorage(Context context) {
        Paper.init(context);
        Paper.setLogLevel(1);
    }

    public static ScreenModel getScreenModel(String str) {
        String str2 = "JSON OP";
        Timber.tag(str2).mo23226i("================================", new Object[0]);
        Timber.tag(str2).mo23226i("JSONStorage getScreenModel Started", new Object[0]);
        if (str == null) {
            return null;
        }
        Timber.tag(str2).mo23226i("ScreenId : %1s", str);
        String str3 = PAPER_SCREEN_BOOK_TAG;
        if (Paper.book(str3).contains(str)) {
            Timber.tag(str2).mo23226i("%1s found", str);
            return (ScreenModel) Paper.book(str3).read(str);
        }
        Timber.tag(str2).mo23226i("%1s NOT found", str);
        return null;
    }

    public static boolean containsScreen(String str) {
        if (str == null) {
            return false;
        }
        return Paper.book(PAPER_SCREEN_BOOK_TAG).contains(str);
    }

    public static void putScreenModel(String str, ScreenModel screenModel) {
        String str2 = "JSON OP";
        Timber.tag(str2).mo23226i("================================", new Object[0]);
        Timber.tag(str2).mo23226i("JSONStorage putScreenModel Started", new Object[0]);
        Timber.tag(str2).mo23226i("ScreenId : %1s", str);
        if (str != null) {
            putObject(PAPER_SCREEN_BOOK_TAG, str, screenModel);
        }
    }

    public static MainModel getMainModel() {
        String str = PAPER_SCREEN_BOOK_TAG;
        Book book = Paper.book(str);
        String str2 = MAIN_MODEL_KEY;
        if (book.contains(str2)) {
            return (MainModel) Paper.book(str).read(str2);
        }
        return null;
    }

    public static void putMainModel(MainModel mainModel) {
        putObject(PAPER_SCREEN_BOOK_TAG, MAIN_MODEL_KEY, mainModel);
    }

    public static MobirollerBadgeModel getMobirollerBadgeModel() {
        String str = PAPER_SCREEN_BOOK_TAG;
        Book book = Paper.book(str);
        String str2 = MOBIROLLER_BADGE_MODEL_KEY;
        if (book.contains(str2)) {
            return (MobirollerBadgeModel) Paper.book(str).read(str2);
        }
        return null;
    }

    static void putMobirollerBadgeModel(MobirollerBadgeModel mobirollerBadgeModel) {
        if (mobirollerBadgeModel.displayRate == -1) {
            UtilManager.sharedPrefHelper().put(Constants.MobiRoller_Preferences_Show_MobiRoller_Badge, false);
        }
        if (!(mobirollerBadgeModel.displayRate == UtilManager.sharedPrefHelper().getInt(Constants.MobiRoller_Preferences_MobiRoller_Badge_Total_Count, -1) || mobirollerBadgeModel.displayRate == 0)) {
            UtilManager.sharedPrefHelper().put(Constants.MobiRoller_Preferences_MobiRoller_Badge_Count, 0);
            UtilManager.sharedPrefHelper().put(Constants.MobiRoller_Preferences_MobiRoller_Badge_Total_Count, mobirollerBadgeModel.displayRate);
        }
        Paper.book(PAPER_SCREEN_BOOK_TAG).write(MOBIROLLER_BADGE_MODEL_KEY, mobirollerBadgeModel);
    }

    public static NavigationModel getNavigationModel() {
        String str = PAPER_SCREEN_BOOK_TAG;
        String str2 = "NavigationModel";
        if (Paper.book(str).contains(str2)) {
            return (NavigationModel) Paper.book(str).read(str2);
        }
        return null;
    }

    public static void putNavigationModel(NavigationModel navigationModel) {
        if (navigationModel != null) {
            putObject(PAPER_SCREEN_BOOK_TAG, "NavigationModel", MenuUtil.setMenuTypeFromType(navigationModel));
            return;
        }
        Timber.tag(TAG).mo23218d("NavigationModel object is NULL", new Object[0]);
    }

    public static InAppPurchaseModel getInAppPurchaseModel() {
        String str = PAPER_SCREEN_BOOK_TAG;
        Book book = Paper.book(str);
        String str2 = IN_APP_PURCHASE_MODEL_KEY;
        if (book.contains(str2)) {
            return (InAppPurchaseModel) Paper.book(str).read(str2);
        }
        return null;
    }

    public static void putInAppPurchase(InAppPurchaseModel inAppPurchaseModel) {
        putObject(PAPER_SCREEN_BOOK_TAG, IN_APP_PURCHASE_MODEL_KEY, inAppPurchaseModel);
    }

    public static void putObject(String str, String str2, Object obj) {
        if (obj != null) {
            Paper.book(str).write(str2, obj);
            return;
        }
        Timber.tag(TAG).mo23218d(" %s object is NULL", str2);
    }

    public static Object getObject(String str, String str2, Object obj) {
        return (str == null || str2 == null) ? obj : Paper.book(str).read(str2, obj);
    }
}
