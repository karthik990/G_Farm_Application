package com.mobiroller.activities.base;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import com.anjlab.android.iab.p020v3.BillingProcessor;
import com.anjlab.android.iab.p020v3.BillingProcessor.IBillingHandler;
import com.anjlab.android.iab.p020v3.TransactionDetails;
import com.mobiroller.constants.ChatConstants;
import com.mobiroller.helpers.AppSettingsHelper;
import com.mobiroller.helpers.InAppPurchaseHelper;
import com.mobiroller.helpers.SharedPrefHelper;
import com.mobiroller.helpers.UtilManager;
import com.mobiroller.interfaces.ActivityComponent;
import com.mobiroller.models.NavigationItemModel;
import com.mobiroller.models.NavigationModel;
import com.mobiroller.models.chat.ChatNotificationModel;
import java.util.ArrayList;

public class AveMenuActivity extends AveActivity implements IBillingHandler {
    public ChatNotificationModel chatNotificationModel = null;
    public BillingProcessor mBillingProcessor;
    public ArrayList<NavigationItemModel> navigationItemModels = new ArrayList<>();
    public NavigationModel navigationModel;
    public boolean pushNotified = false;
    public boolean showIntro = false;

    public void onBillingError(int i, Throwable th) {
    }

    public void onBillingInitialized() {
    }

    public void onProductPurchased(String str, TransactionDetails transactionDetails) {
    }

    public void onPurchaseHistoryRestored() {
    }

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
        }
    }

    public AppCompatActivity injectActivity(ActivityComponent activityComponent) {
        activityComponent.inject((AppCompatActivity) this);
        return this;
    }

    public ArrayList<NavigationItemModel> getValidItems() {
        SharedPrefHelper sharedPrefHelper = UtilManager.sharedPrefHelper();
        String userRole = sharedPrefHelper.getUserRole();
        ArrayList<NavigationItemModel> arrayList = new ArrayList<>();
        try {
            if (this.navigationItemModels.size() > 0) {
                for (int i = 0; i < this.navigationItemModels.size(); i++) {
                    if (!((NavigationItemModel) this.navigationItemModels.get(i)).getScreenType().equalsIgnoreCase("aveECommerceView") || AppSettingsHelper.isECommerceActive()) {
                        if (!((NavigationItemModel) this.navigationItemModels.get(i)).getScreenType().equalsIgnoreCase("aveChatView") || UtilManager.sharedPrefHelper().getIsChatActive()) {
                            if (!((NavigationItemModel) this.navigationItemModels.get(i)).isLoginActive()) {
                                arrayList.add(this.navigationItemModels.get(i));
                            } else if (sharedPrefHelper.getUserLoginStatus()) {
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
