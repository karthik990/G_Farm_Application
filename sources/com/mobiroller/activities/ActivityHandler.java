package com.mobiroller.activities;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.anjlab.android.iab.p020v3.BillingProcessor;
import com.anjlab.android.iab.p020v3.BillingProcessor.IBillingHandler;
import com.anjlab.android.iab.p020v3.TransactionDetails;
import com.mobiroller.DynamicConstants;
import com.mobiroller.activities.inapppurchase.InAppPurchaseActivity;
import com.mobiroller.activities.preview.NotSupportedActivity;
import com.mobiroller.constants.Constants;
import com.mobiroller.constants.ModuleConstants;
import com.mobiroller.helpers.ApiRequestManager;
import com.mobiroller.helpers.FileDownloader;
import com.mobiroller.helpers.InAppPurchaseHelper;
import com.mobiroller.helpers.JSONParser;
import com.mobiroller.helpers.JSONStorage;
import com.mobiroller.helpers.LocalizationHelper;
import com.mobiroller.helpers.NetworkHelper;
import com.mobiroller.helpers.ProgressViewHelper;
import com.mobiroller.helpers.RequestHelper;
import com.mobiroller.helpers.SharedPrefHelper;
import com.mobiroller.helpers.ToolbarHelper;
import com.mobiroller.helpers.UtilManager;
import com.mobiroller.mobi942763453128.R;
import com.mobiroller.models.NavigationItemModel;
import com.mobiroller.models.ScreenModel;
import com.mobiroller.models.TableItemsModel;
import com.mobiroller.util.InterstitialAdsUtil;
import com.mobiroller.util.MobirollerIntent;
import java.util.ArrayList;
import org.apache.http.protocol.HTTP;

public class ActivityHandler extends AppCompatActivity implements IBillingHandler {
    public static final String INTENT_EXTRA_NAVIGATION_MODEL = "NavigationModel";
    public static final String INTENT_EXTRA_TABLE_ITEM_MODEL = "TableItemModel";
    /* access modifiers changed from: private */
    public ApiRequestManager apiRequestManager;
    private BillingProcessor mBillingProcessor;
    private InterstitialAdsUtil mInterstitialAdsUtil;
    /* access modifiers changed from: private */
    public JSONParser mJSONParser;
    private LocalizationHelper mLocalizationHelper;
    /* access modifiers changed from: private */
    public NetworkHelper mNetworkHelper;
    private ProgressViewHelper mProgressViewHelper;
    private String mRssPushTitle;
    /* access modifiers changed from: private */
    public String mScreenId;
    /* access modifiers changed from: private */
    public ScreenModel mScreenModel;
    private String mScreenType;
    public String mSubScreenType;
    private String mUpdateDate;
    private ArrayList<String> roles = new ArrayList<>();

    private class ScreenModelOperation extends AsyncTask<Void, Void, Void> {
        /* access modifiers changed from: protected */
        public void onPreExecute() {
        }

        private ScreenModelOperation() {
        }

        /* access modifiers changed from: protected */
        public void onPostExecute(Void voidR) {
            super.onPostExecute(voidR);
            ActivityHandler.this.startScreenModelActivity();
        }

        /* access modifiers changed from: protected */
        public Void doInBackground(Void... voidArr) {
            ActivityHandler.this.getScreenModel();
            if (ActivityHandler.this.mScreenModel == null) {
                if (!ActivityHandler.this.mNetworkHelper.isConnected()) {
                    ActivityHandler activityHandler = ActivityHandler.this;
                    JSONParser access$600 = activityHandler.mJSONParser;
                    ActivityHandler activityHandler2 = ActivityHandler.this;
                    activityHandler.mScreenModel = access$600.getLocalScreenModel(activityHandler2, String.valueOf(activityHandler2.mScreenId));
                } else {
                    ActivityHandler activityHandler3 = ActivityHandler.this;
                    activityHandler3.mScreenModel = activityHandler3.apiRequestManager.getScreenJSON(String.valueOf(ActivityHandler.this.mScreenId));
                }
            }
            return null;
        }
    }

    public void onBillingError(int i, Throwable th) {
    }

    public void onBillingInitialized() {
    }

    public void onProductPurchased(String str, TransactionDetails transactionDetails) {
    }

    public void onPurchaseHistoryRestored() {
    }

    public static void startActivity(Context context, NavigationItemModel navigationItemModel) {
        Intent intent = new Intent(context, ActivityHandler.class);
        intent.putExtra(INTENT_EXTRA_NAVIGATION_MODEL, navigationItemModel);
        context.startActivity(intent);
    }

    public static void startActivity(Context context, TableItemsModel tableItemsModel) {
        Intent intent = new Intent(context, ActivityHandler.class);
        intent.putExtra(INTENT_EXTRA_TABLE_ITEM_MODEL, tableItemsModel);
        context.startActivity(intent);
    }

    public static void startActivity(Context context, String str, String str2, String str3, String str4, ArrayList<String> arrayList) {
        Intent intent = new Intent(context, ActivityHandler.class);
        intent.putExtra(Constants.KEY_SCREEN_ID, str);
        intent.putExtra(Constants.KEY_SCREEN_TYPE, str2);
        intent.putExtra(Constants.KEY_SUB_SCREEN_TYPE, str3);
        intent.putExtra("roles", arrayList);
        intent.putExtra(Constants.KEY_UPDATE_DATE, str4);
        context.startActivity(intent);
    }

    public static void startActivity(Context context, String str, String str2, String str3, String str4, ArrayList<String> arrayList, String str5) {
        context.startActivity(getIntent(context, str, str2, str3, str4, arrayList, str5));
    }

    public static Intent getIntent(Context context, String str, String str2, String str3, String str4, ArrayList<String> arrayList, String str5) {
        Intent intent = new Intent(context, ActivityHandler.class);
        intent.putExtra(Constants.KEY_SCREEN_ID, str);
        intent.putExtra(Constants.KEY_SCREEN_TYPE, str2);
        intent.putExtra(Constants.KEY_SUB_SCREEN_TYPE, str3);
        intent.putExtra("roles", arrayList);
        intent.putExtra(Constants.KEY_UPDATE_DATE, str4);
        intent.putExtra(Constants.KEY_SCREEN_RSS_PUSH_TITLE, str5);
        return intent;
    }

    public static Intent getIntent(Context context, String str, String str2, String str3, String str4) {
        Intent intent = new Intent(context, ActivityHandler.class);
        intent.putExtra(Constants.KEY_SCREEN_ID, str);
        intent.putExtra(Constants.KEY_SCREEN_TYPE, str2);
        intent.putExtra(Constants.KEY_SUB_SCREEN_TYPE, str3);
        intent.putExtra(Constants.KEY_SCREEN_RSS_PUSH_TITLE, str4);
        return intent;
    }

    public static void startActivity(Context context, String str, String str2, String str3, String str4) {
        context.startActivity(getIntent(context, str, str2, str3, str4));
    }

    public static void startActivity(Context context, String str, String str2, String str3, String str4, ArrayList<String> arrayList, boolean z) {
        Intent intent = new Intent(context, ActivityHandler.class);
        intent.putExtra(Constants.KEY_SCREEN_ID, str);
        intent.putExtra(Constants.KEY_SCREEN_TYPE, str2);
        intent.putExtra(Constants.KEY_SUB_SCREEN_TYPE, str3);
        intent.putExtra("roles", arrayList);
        intent.putExtra(Constants.KEY_UPDATE_DATE, str4);
        intent.putExtra(Constants.KEY_FROM_NO_NETWORK, true);
        context.startActivity(intent);
    }

    /* access modifiers changed from: protected */
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        new ToolbarHelper().setStatusBar(this);
        if (VERSION.SDK_INT >= 19 && VERSION.SDK_INT < 21) {
            InAppPurchaseActivity.setWindowFlag(this, 67108864, true);
        }
        if (VERSION.SDK_INT >= 19) {
            getWindow().getDecorView().setSystemUiVisibility(1280);
        }
        if (VERSION.SDK_INT >= 21) {
            InAppPurchaseActivity.setWindowFlag(this, 67108864, false);
            getWindow().setStatusBarColor(0);
        }
        if (VERSION.SDK_INT >= 19) {
            getWindow().setFlags(512, 512);
        }
        Intent intent = getIntent();
        String str = INTENT_EXTRA_NAVIGATION_MODEL;
        boolean hasExtra = intent.hasExtra(str);
        String str2 = Constants.KEY_SCREEN_ID;
        String str3 = INTENT_EXTRA_TABLE_ITEM_MODEL;
        if (hasExtra || getIntent().hasExtra(str3) || getIntent().hasExtra(str2)) {
            if (getIntent().hasExtra(str3)) {
                TableItemsModel tableItemsModel = (TableItemsModel) getIntent().getSerializableExtra(str3);
                this.mScreenType = tableItemsModel.getScreenType();
                this.mSubScreenType = tableItemsModel.screenSubType;
                this.mScreenId = tableItemsModel.getAccountScreenID();
                this.mUpdateDate = tableItemsModel.getUpdateDate();
                if (tableItemsModel.getRoles() != null) {
                    this.roles = tableItemsModel.getRoles();
                }
            } else if (getIntent().hasExtra(str)) {
                NavigationItemModel navigationItemModel = (NavigationItemModel) getIntent().getSerializableExtra(str);
                this.mScreenType = navigationItemModel.getScreenType();
                this.mSubScreenType = navigationItemModel.screenSubtype;
                this.mScreenId = navigationItemModel.getAccountScreenID();
                this.mUpdateDate = navigationItemModel.getUpdateDate();
                if (navigationItemModel.getRoles() != null) {
                    this.roles = navigationItemModel.getRoles();
                }
            } else {
                this.mScreenId = getIntent().getStringExtra(str2);
                this.mScreenType = getIntent().getStringExtra(Constants.KEY_SCREEN_TYPE);
                Intent intent2 = getIntent();
                String str4 = Constants.KEY_SUB_SCREEN_TYPE;
                if (intent2.hasExtra(str4)) {
                    this.mSubScreenType = getIntent().getStringExtra(str4);
                }
                Intent intent3 = getIntent();
                String str5 = Constants.KEY_UPDATE_DATE;
                if (intent3.hasExtra(str5)) {
                    this.mUpdateDate = getIntent().getStringExtra(str5);
                }
                String str6 = "roles";
                if (getIntent().hasExtra(str6)) {
                    this.roles = getIntent().getStringArrayListExtra(str6);
                }
                Intent intent4 = getIntent();
                String str7 = Constants.KEY_SCREEN_RSS_PUSH_TITLE;
                if (intent4.hasExtra(str7)) {
                    this.mRssPushTitle = getIntent().getStringExtra(str7);
                }
            }
            SharedPrefHelper sharedPrefHelper = UtilManager.sharedPrefHelper();
            this.apiRequestManager = new ApiRequestManager(sharedPrefHelper, new RequestHelper(this, sharedPrefHelper));
            this.mNetworkHelper = UtilManager.networkHelper();
            this.mJSONParser = new JSONParser(new FileDownloader(), this.apiRequestManager, this.mNetworkHelper);
            this.mInterstitialAdsUtil = new InterstitialAdsUtil((AppCompatActivity) this);
            this.mProgressViewHelper = new ProgressViewHelper((AppCompatActivity) this);
            this.mLocalizationHelper = UtilManager.localizationHelper();
            this.mProgressViewHelper.show();
            if (InAppPurchaseHelper.checkIsInAppPurchaseValid(this)) {
                this.mBillingProcessor = new BillingProcessor(this, InAppPurchaseHelper.getInAppPurchaseLicenseKey(), "02196105938331679389", this);
                this.mBillingProcessor.initialize();
                this.mBillingProcessor.loadOwnedPurchasesFromGoogle();
            }
            new ScreenModelOperation().execute(new Void[0]);
            return;
        }
        Toast.makeText(this, getString(R.string.common_error), 0).show();
        finish();
    }

    /* access modifiers changed from: private */
    public void getScreenModel() {
        this.mScreenModel = this.mJSONParser.getScreenJSONFromLocalByID(this, String.valueOf(this.mScreenId), this.mNetworkHelper.isConnected(), this.mUpdateDate);
    }

    /* access modifiers changed from: private */
    public void startScreenModelActivity() {
        if (!this.mNetworkHelper.isConnected() && NetworkHelper.connectionRequiredModule(this.mScreenType, this)) {
            if (!isFinishing()) {
                this.mProgressViewHelper.dismiss();
            }
            startConnectionRequiredActivity();
        } else if (this.mScreenModel == null) {
            if (!isFinishing()) {
                this.mProgressViewHelper.dismiss();
            }
            startConnectionRequiredActivity();
        } else if (!InAppPurchaseHelper.checkScreenIsInPurchaseList(String.valueOf(this.mScreenId))) {
            startAction();
        } else if (!InAppPurchaseHelper.checkIsInAppPurchaseValid(this)) {
            if (!isFinishing()) {
                this.mProgressViewHelper.dismiss();
            }
            startConnectionRequiredActivity();
        } else if (InAppPurchaseHelper.isScreenPurchased(this.mBillingProcessor, this.mScreenId)) {
            startAction();
        } else {
            MobirollerIntent.startInAppPurchaseActivity(this, String.valueOf(this.mScreenId), this.mScreenType, true);
            if (!isFinishing()) {
                this.mProgressViewHelper.dismiss();
            }
            finish();
        }
    }

    private void startAction() {
        Intent intent;
        if (!DynamicConstants.MobiRoller_Stage || isSupportedForPreview()) {
            if (isCallAction()) {
                if (!isFinishing()) {
                    this.mProgressViewHelper.dismiss();
                }
                startCallActionActivity();
            } else if (isShareAction()) {
                if (!isFinishing()) {
                    this.mProgressViewHelper.dismiss();
                }
                startShareActionActivity();
            } else {
                if (isCustomActionRequired()) {
                    intent = getCustomActionIntent();
                } else {
                    intent = new Intent(this, GenericActivity.class);
                }
                setIntentProperties(intent);
                if (!isFinishing()) {
                    this.mProgressViewHelper.dismiss();
                }
                this.mInterstitialAdsUtil.checkInterstitialAds(intent);
                finish();
            }
            return;
        }
        startActivity(getPreviewNotSupportedIntent().putExtra("title", this.mScreenModel.getTitle()).putExtra(Constants.KEY_SCREEN_TYPE, this.mScreenType));
        finish();
    }

    private void startConnectionRequiredActivity() {
        Intent intent = new Intent(this, ConnectionRequired.class);
        intent.putExtra(Constants.KEY_SCREEN_ID, this.mScreenId);
        intent.putExtra(Constants.KEY_SCREEN_TYPE, this.mScreenType);
        intent.putExtra(Constants.KEY_UPDATE_DATE, this.mUpdateDate);
        intent.putExtra("roles", this.roles);
        String str = this.mRssPushTitle;
        if (str != null) {
            intent.putExtra(Constants.KEY_SCREEN_RSS_PUSH_TITLE, str);
        }
        startActivity(intent);
        finish();
    }

    private void setIntentProperties(Intent intent) {
        intent.setFlags(134217728);
        intent.setFlags(268435456);
        intent.putExtra(Constants.KEY_SCREEN_ID, this.mScreenId);
        intent.putExtra(Constants.KEY_UPDATE_DATE, this.mUpdateDate);
        ArrayList<String> arrayList = this.roles;
        if (arrayList != null) {
            intent.putExtra("roles", arrayList);
        }
        String str = this.mRssPushTitle;
        if (str != null) {
            intent.putExtra(Constants.KEY_SCREEN_RSS_PUSH_TITLE, str);
        }
        boolean isExtendedView = isExtendedView();
        String str2 = Constants.KEY_SUB_SCREEN_TYPE;
        String str3 = Constants.KEY_SCREEN_TYPE;
        if (isExtendedView) {
            intent.putExtra(str3, getExtendedViewScreenType());
            intent.putExtra(str2, this.mSubScreenType);
        } else {
            intent.putExtra(str3, this.mScreenType);
            intent.putExtra(str2, this.mSubScreenType);
        }
        JSONStorage.putScreenModel(this.mScreenId, this.mScreenModel);
    }

    private void startCallActionActivity() {
        if (this.mScreenModel.getPhoneNumber() != null) {
            Intent intent = new Intent("android.intent.action.DIAL");
            StringBuilder sb = new StringBuilder();
            sb.append("tel:");
            sb.append(this.mScreenModel.getPhoneNumber());
            intent.setData(Uri.parse(sb.toString()));
            startActivity(intent);
            finish();
        }
    }

    private void startShareActionActivity() {
        Intent intent = new Intent();
        intent.setAction("android.intent.action.SEND");
        String localizedTitle = this.mLocalizationHelper.getLocalizedTitle(this.mScreenModel.getContentText());
        String googlePlayLink = this.mScreenModel.getGooglePlayLink();
        String str = "";
        if (googlePlayLink == null) {
            googlePlayLink = str;
        }
        if (this.mLocalizationHelper.getLocalizedTitle(this.mScreenModel.getContentText()) == null) {
            localizedTitle = str;
        }
        StringBuilder sb = new StringBuilder();
        sb.append(localizedTitle);
        sb.append("  ");
        sb.append(googlePlayLink);
        intent.putExtra("android.intent.extra.TEXT", sb.toString());
        intent.setType(HTTP.PLAIN_TEXT_TYPE);
        startActivity(Intent.createChooser(intent, getString(R.string.action_share)));
        finish();
    }

    private boolean isCallAction() {
        return this.mScreenType.equalsIgnoreCase("aveCallNowView");
    }

    private boolean isSupportedForPreview() {
        for (String equalsIgnoreCase : getResources().getStringArray(R.array.preview_not_supported_modules)) {
            if (this.mScreenType.equalsIgnoreCase(equalsIgnoreCase)) {
                return false;
            }
        }
        return true;
    }

    private boolean isShareAction() {
        return this.mScreenType.equalsIgnoreCase("aveShareView");
    }

    private boolean isExtendedView() {
        return this.mScreenType.equalsIgnoreCase(ModuleConstants.HTML_MODULE) && (this.mScreenModel.getScreenType().equalsIgnoreCase("aveFAQView") || this.mScreenModel.getScreenType().equalsIgnoreCase("aveEmergencyCallView") || this.mScreenModel.getScreenType().equalsIgnoreCase("aveCatalogView") || this.mScreenModel.getScreenType().equalsIgnoreCase("aveAboutUsView"));
    }

    private String getExtendedViewScreenType() {
        return this.mScreenModel.getScreenType();
    }

    private boolean isCustomActionRequired() {
        boolean z = true;
        if (this.mScreenType.equalsIgnoreCase("aveShareView") || this.mScreenType.equalsIgnoreCase("aveChatView") || this.mScreenType.equalsIgnoreCase("aveRSSView") || this.mScreenType.equalsIgnoreCase("aveYoutubeAdvancedView")) {
            return true;
        }
        if (!this.mScreenType.equalsIgnoreCase("aveWebView")) {
            return false;
        }
        String str = this.mSubScreenType;
        if (str != null && str.equalsIgnoreCase("avePDFView")) {
            z = false;
        }
        return z;
    }

    private Intent getCustomActionIntent() {
        Class cls;
        try {
            StringBuilder sb = new StringBuilder();
            sb.append(Constants.Mobiroller_Preferences_PackageName_Activities);
            sb.append(".");
            sb.append(this.mScreenType);
            cls = Class.forName(sb.toString());
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            cls = null;
        }
        return new Intent(this, cls);
    }

    private Intent getPreviewNotSupportedIntent() {
        return new Intent(this, NotSupportedActivity.class);
    }

    /* access modifiers changed from: protected */
    public void onDestroy() {
        super.onDestroy();
        this.mProgressViewHelper.dismiss();
    }
}
