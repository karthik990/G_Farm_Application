package com.mobiroller.util;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import com.mobiroller.MobiRollerApplication;
import com.mobiroller.activities.ConnectionRequired;
import com.mobiroller.activities.inapppurchase.InAppPurchaseActivity;
import com.mobiroller.activities.inapppurchase.InAppPurchaseDetailActivity;
import com.mobiroller.activities.user.FirebaseSignInActivity;
import com.mobiroller.activities.user.GoogleSignInActivity;
import com.mobiroller.constants.Constants;
import com.mobiroller.helpers.InAppPurchaseHelper;
import com.mobiroller.models.InAppPurchaseProduct;
import com.mobiroller.models.NavigationItemModel;
import com.mobiroller.models.response.UserLoginResponse;

public class MobirollerIntent {
    public static final int APP_SETTINGS_REQUEST_CODE = 5;
    public static final int FIREBASE_SIGN_IN_REQUEST_CODE = 134;
    public static final int GOOGLE_SIGN_IN_REQUEST_CODE = 133;

    public static void startGoogleSignInActivity(Context context, String str) {
        Intent intent = new Intent(context, GoogleSignInActivity.class);
        intent.putExtra(GoogleSignInActivity.INTENT_EXTRA_SCREEN_ID, str);
        context.startActivity(intent);
    }

    public static Intent getGoogleSignInIntent(Context context, String str) {
        Intent intent = new Intent(context, GoogleSignInActivity.class);
        intent.putExtra(GoogleSignInActivity.INTENT_EXTRA_SCREEN_ID, str);
        return intent;
    }

    public static Intent getFirebaseSignInIntent(Context context, UserLoginResponse userLoginResponse) {
        Intent intent = new Intent(context, FirebaseSignInActivity.class);
        intent.putExtra(FirebaseSignInActivity.INTENT_EXTRA_USER_LOGIN_MODEL, userLoginResponse);
        return intent;
    }

    public static Intent getConnectionRequiredIntent(NavigationItemModel navigationItemModel) {
        Intent intent = new Intent(MobiRollerApplication.app, ConnectionRequired.class);
        intent.putExtra(Constants.KEY_SCREEN_ID, navigationItemModel.getAccountScreenID());
        intent.putExtra(Constants.KEY_SCREEN_TYPE, navigationItemModel.getScreenType());
        intent.putExtra(Constants.KEY_UPDATE_DATE, navigationItemModel.getUpdateDate());
        return intent;
    }

    public static void startInAppPurchaseActivity(Context context, String str, String str2, boolean z) {
        if (InAppPurchaseHelper.getScreenProductList(str).size() == 1) {
            startInAppPurchaseDetailActivity(context, (InAppPurchaseProduct) InAppPurchaseHelper.getScreenProductList(str).get(0), str, str2, z);
            return;
        }
        Intent intent = new Intent(context, InAppPurchaseActivity.class);
        intent.putExtra(Constants.KEY_SCREEN_ID, str);
        intent.putExtra(Constants.KEY_SCREEN_TYPE, str2);
        if (z) {
            intent.putExtra(Constants.KEY_IS_FROM_ACTIVITY, true);
        }
        context.startActivity(intent);
    }

    public static void startInAppPurchaseDetailActivity(Context context, InAppPurchaseProduct inAppPurchaseProduct, String str, String str2, boolean z) {
        Intent intent = new Intent(context, InAppPurchaseDetailActivity.class);
        intent.putExtra(InAppPurchaseDetailActivity.INTENT_EXTRA_IN_APP_PURCHASE_PRODUCT, inAppPurchaseProduct);
        intent.putExtra(Constants.KEY_SCREEN_ID, str);
        intent.putExtra(Constants.KEY_SCREEN_TYPE, str2);
        if (z) {
            intent.putExtra(Constants.KEY_IS_FROM_ACTIVITY, true);
        }
        context.startActivity(intent);
    }

    public static void startConnectionRequiredActivity(Context context, NavigationItemModel navigationItemModel) {
        Intent intent = new Intent(MobiRollerApplication.app, ConnectionRequired.class);
        intent.putExtra(Constants.KEY_SCREEN_ID, navigationItemModel.getAccountScreenID());
        intent.putExtra(Constants.KEY_SCREEN_TYPE, navigationItemModel.getScreenType());
        intent.putExtra(Constants.KEY_UPDATE_DATE, navigationItemModel.getUpdateDate());
        context.startActivity(intent);
    }

    public static void startAppSettings(Activity activity) {
        StringBuilder sb = new StringBuilder();
        sb.append("package:");
        sb.append(activity.getPackageName());
        Intent intent = new Intent("android.settings.APPLICATION_DETAILS_SETTINGS", Uri.parse(sb.toString()));
        intent.addCategory("android.intent.category.DEFAULT");
        activity.startActivityForResult(intent, 5);
    }
}
