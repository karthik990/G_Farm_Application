package com.mobiroller.activities.menu;

import android.content.Intent;
import android.os.Bundle;
import android.view.View.OnClickListener;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import com.anjlab.android.iab.p020v3.BillingProcessor;
import com.anjlab.android.iab.p020v3.BillingProcessor.IBillingHandler;
import com.anjlab.android.iab.p020v3.TransactionDetails;
import com.mobiroller.activities.base.AveActivity;
import com.mobiroller.constants.ChatConstants;
import com.mobiroller.constants.Constants;
import com.mobiroller.fragments.BackHandledFragment.BackHandlerInterface;
import com.mobiroller.helpers.ApiRequestManager;
import com.mobiroller.helpers.AppSettingsHelper;
import com.mobiroller.helpers.InAppPurchaseHelper;
import com.mobiroller.helpers.MenuHelper;
import com.mobiroller.helpers.NotificationHelper;
import com.mobiroller.helpers.ProgressViewHelper;
import com.mobiroller.helpers.RatingDialogHelper;
import com.mobiroller.helpers.SharedPrefHelper;
import com.mobiroller.helpers.ToolbarHelper;
import com.mobiroller.helpers.UtilManager;
import com.mobiroller.models.NavigationItemModel;
import com.mobiroller.models.NavigationModel;
import com.mobiroller.models.ScreenModel;
import com.mobiroller.models.chat.ChatNotificationModel;
import com.mobiroller.util.DialogUtil.ExitDialog;
import com.mobiroller.util.DialogUtil.UserLogoutDialog;
import com.mobiroller.util.InterstitialAdsUtil;
import com.mobiroller.util.ServiceUtil;
import com.mobiroller.util.ServiceUtil.ServiceInterface;
import java.util.ArrayList;

public abstract class MenuActivity extends AveActivity implements IBillingHandler, ExitDialog, UserLogoutDialog, ServiceInterface, OnClickListener, BackHandlerInterface {
    ApiRequestManager apiRequestManager;
    ChatNotificationModel chatNotificationModel = null;
    Fragment fragment = null;
    InterstitialAdsUtil interstitialAdsUtil;
    BillingProcessor mBillingProcessor;
    MenuHelper menuHelper;
    ArrayList<NavigationItemModel> navigationItemModels = new ArrayList<>();
    NavigationModel navigationModel;
    boolean pushNotified = false;
    private ServiceUtil serviceUtil = new ServiceUtil();
    SharedPrefHelper sharedPrefHelper;
    boolean showIntro = false;
    ToolbarHelper toolbarHelper;
    ArrayList<NavigationItemModel> validNavItems;

    /* access modifiers changed from: 0000 */
    public abstract int getLayoutResource();

    /* access modifiers changed from: 0000 */
    public abstract void loadBanner();

    /* access modifiers changed from: 0000 */
    public abstract void loadUserView();

    /* access modifiers changed from: 0000 */
    public abstract void loadValidNavigationItemsView();

    public void onBillingError(int i, Throwable th) {
    }

    public void onBillingInitialized() {
    }

    public void onProductPurchased(String str, TransactionDetails transactionDetails) {
    }

    public void onPurchaseHistoryRestored() {
    }

    /* access modifiers changed from: 0000 */
    public abstract void setFragment(ScreenModel screenModel, int i, int i2);

    /* access modifiers changed from: 0000 */
    public abstract void setLogInStatus();

    /* access modifiers changed from: protected */
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        Intent intent = getIntent();
        String str = ChatConstants.ARG_NOTIFICATION_MODEL;
        if (intent.hasExtra(str)) {
            this.chatNotificationModel = (ChatNotificationModel) getIntent().getSerializableExtra(str);
        }
        if (InAppPurchaseHelper.checkIsInAppPurchaseValid(this)) {
            this.mBillingProcessor = new BillingProcessor(this, InAppPurchaseHelper.getInAppPurchaseLicenseKey(), this);
            this.mBillingProcessor.initialize();
            this.mBillingProcessor.loadOwnedPurchasesFromGoogle();
        }
        overridePendingTransition(0, 0);
        setContentView(getLayoutResource());
        this.sharedPrefHelper = UtilManager.sharedPrefHelper();
        this.progressViewHelper = new ProgressViewHelper((AppCompatActivity) this);
        this.interstitialAdsUtil = new InterstitialAdsUtil((AppCompatActivity) this);
        this.interstitialAdsUtil = new InterstitialAdsUtil((AppCompatActivity) this);
        this.apiRequestManager = new ApiRequestManager();
        this.menuHelper = new MenuHelper(this);
        this.toolbarHelper = new ToolbarHelper();
        this.toolbarHelper.setStatusBar(this);
        this.progressViewHelper.setCancelableOnCancel();
    }

    /* access modifiers changed from: 0000 */
    public void loadUI() {
        RatingDialogHelper.checkRatingStatus(this);
        NotificationHelper.sendToken(this);
        this.navigationModel = (NavigationModel) getIntent().getSerializableExtra("localObj");
        this.showIntro = getIntent().getBooleanExtra(Constants.KEY_SHOW_INTRO, false);
        this.pushNotified = getIntent().getBooleanExtra("pushNotified", false);
        this.navigationItemModels = this.navigationModel.getNavigationItems();
    }

    /* access modifiers changed from: protected */
    public void onStart() {
        super.onStart();
        if (this.sharedPrefHelper.getIsAdmobBannerAdEnabled() && this.sharedPrefHelper.getBannerAd() == null && !this.sharedPrefHelper.getBannerAdUnitID().isEmpty()) {
            loadBanner();
        }
    }

    /* access modifiers changed from: 0000 */
    public void fetchScreenDetails(ServiceInterface serviceInterface, int i) {
        this.serviceUtil.fetchScreenDetails((NavigationItemModel) this.validNavItems.get(i), serviceInterface, i);
    }

    /* access modifiers changed from: 0000 */
    public ArrayList<NavigationItemModel> getValidItems() {
        SharedPrefHelper sharedPrefHelper2 = UtilManager.sharedPrefHelper();
        String userRole = sharedPrefHelper2.getUserRole();
        ArrayList<NavigationItemModel> arrayList = new ArrayList<>();
        try {
            if (this.navigationItemModels.size() > 0) {
                for (int i = 0; i < this.navigationItemModels.size(); i++) {
                    if (!((NavigationItemModel) this.navigationItemModels.get(i)).getScreenType().equalsIgnoreCase("aveECommerceView") || AppSettingsHelper.isECommerceActive()) {
                        if (!((NavigationItemModel) this.navigationItemModels.get(i)).getScreenType().equalsIgnoreCase("aveChatView") || UtilManager.sharedPrefHelper().getIsChatActive()) {
                            if (!((NavigationItemModel) this.navigationItemModels.get(i)).isLoginActive()) {
                                arrayList.add(this.navigationItemModels.get(i));
                            } else if (sharedPrefHelper2.getUserLoginStatus()) {
                                if (((NavigationItemModel) this.navigationItemModels.get(i)).getRoles().size() > 0) {
                                    int i2 = 0;
                                    while (true) {
                                        if (i2 >= ((NavigationItemModel) this.navigationItemModels.get(i)).getRoles().size()) {
                                            break;
                                        } else if (((String) ((NavigationItemModel) this.navigationItemModels.get(i)).getRoles().get(i2)).equalsIgnoreCase(userRole)) {
                                            arrayList.add(this.navigationItemModels.get(i));
                                            break;
                                        } else {
                                            i2++;
                                        }
                                    }
                                } else {
                                    arrayList.add(this.navigationItemModels.get(i));
                                }
                            }
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return arrayList;
    }

    /* access modifiers changed from: protected */
    public void onActivityResult(int i, int i2, Intent intent) {
        BillingProcessor billingProcessor = this.mBillingProcessor;
        if (billingProcessor != null && !billingProcessor.handleActivityResult(i, i2, intent)) {
            super.onActivityResult(i, i2, intent);
        }
    }

    /* access modifiers changed from: 0000 */
    public boolean isScreenPurchased(String str) {
        if (this.mBillingProcessor == null && InAppPurchaseHelper.getInAppPurchaseLicenseKey() != null) {
            this.mBillingProcessor = new BillingProcessor(this, InAppPurchaseHelper.getInAppPurchaseLicenseKey(), this);
            this.mBillingProcessor.initialize();
        }
        BillingProcessor billingProcessor = this.mBillingProcessor;
        if (billingProcessor != null) {
            return InAppPurchaseHelper.isScreenPurchased(billingProcessor, str);
        }
        return false;
    }
}
