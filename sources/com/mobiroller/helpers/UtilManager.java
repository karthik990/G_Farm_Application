package com.mobiroller.helpers;

import android.content.Context;
import com.mobiroller.MobiRollerApplication;
import com.mobiroller.constants.Constants;
import java.util.Locale;

public class UtilManager {
    private static ApiRequestManager apiRequestManager;
    private static LocalizationHelper localizationHelper;
    private static NetworkHelper networkHelper;
    private static RadioHelper radioHelper;
    private static SharedPrefHelper sharedPrefHelper;

    public static final class Builder {
        private Context mContext;

        public Builder setContext(Context context) {
            this.mContext = context;
            return this;
        }

        public void build() {
            Context context = this.mContext;
            if (context != null) {
                UtilManager.init(context);
                return;
            }
            throw new RuntimeException("Context not set, please set context before building the Prefs instance.");
        }
    }

    /* access modifiers changed from: private */
    public static void init(Context context) {
        sharedPrefHelper = new SharedPrefHelper(context);
        if (!sharedPrefHelper().getLanguageSetByUser()) {
            sharedPrefHelper().put(Constants.DISPLAY_LANGUAGE, Locale.getDefault().getLanguage().toLowerCase());
        }
        networkHelper = new NetworkHelper(context);
        localizationHelper = new LocalizationHelper();
        apiRequestManager = new ApiRequestManager();
        new com.mobiroller.helpers.JSONStorage.Builder().setContext(context).build();
        new com.mobiroller.helpers.AsyncRequestHelper.Builder().setContext(context).build();
        new com.mobiroller.helpers.InAppPurchaseHelper.Builder().setContext(context).build();
    }

    public static SharedPrefHelper sharedPrefHelper() {
        if (sharedPrefHelper == null) {
            sharedPrefHelper = new SharedPrefHelper(MobiRollerApplication.context);
        }
        return sharedPrefHelper;
    }

    public static NetworkHelper networkHelper() {
        if (networkHelper == null) {
            networkHelper = new NetworkHelper(MobiRollerApplication.context);
        }
        return networkHelper;
    }

    public static LocalizationHelper localizationHelper() {
        if (localizationHelper == null) {
            localizationHelper = new LocalizationHelper();
        }
        return localizationHelper;
    }

    public static ApiRequestManager apiRequestManager() {
        if (apiRequestManager == null) {
            apiRequestManager = new ApiRequestManager();
        }
        return apiRequestManager;
    }

    public static RadioHelper radioHelper() {
        if (radioHelper == null) {
            radioHelper = new RadioHelper(MobiRollerApplication.context);
        }
        return radioHelper;
    }
}
