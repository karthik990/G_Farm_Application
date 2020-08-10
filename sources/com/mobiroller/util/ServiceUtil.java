package com.mobiroller.util;

import com.mobiroller.DynamicConstants;
import com.mobiroller.MobiRollerApplication;
import com.mobiroller.helpers.ApiRequestManager;
import com.mobiroller.helpers.JSONParser;
import com.mobiroller.helpers.MenuHelper;
import com.mobiroller.helpers.UtilManager;
import com.mobiroller.models.NavigationItemModel;
import com.mobiroller.models.ScreenModel;
import com.mobiroller.util.exceptions.IntentException;
import com.mobiroller.util.exceptions.UserFriendlyException;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ServiceUtil {
    private int itemPosition;
    /* access modifiers changed from: private */
    public ScreenModel screenModel;
    private ServiceInterface serviceInterface;

    public interface ServiceInterface {
        void onScreenModelFetched(ScreenModel screenModel, int i);

        void onThrowIntentException(IntentException intentException);

        void onThrowUserFriendlyException(UserFriendlyException userFriendlyException);
    }

    public void fetchScreenDetails(NavigationItemModel navigationItemModel, ServiceInterface serviceInterface2, int i) {
        this.screenModel = null;
        this.serviceInterface = serviceInterface2;
        this.itemPosition = i;
        if (!DynamicConstants.MobiRoller_Stage) {
            this.screenModel = getLocalScreenModel(navigationItemModel);
            if (UtilManager.networkHelper().isConnected()) {
                ScreenModel screenModel2 = this.screenModel;
                if (screenModel2 == null || screenModel2.getUpdateDate() == null || !DateUtil.dateControlString(this.screenModel.getUpdateDate(), navigationItemModel.getUpdateDate())) {
                    getLiveScreenModelAsync(navigationItemModel);
                } else {
                    handleResult(navigationItemModel, this.screenModel);
                }
            } else {
                handleResult(navigationItemModel, this.screenModel);
            }
        } else if (UtilManager.networkHelper().isConnected()) {
            getLiveScreenModelAsync(navigationItemModel);
        } else {
            serviceInterface2.onThrowIntentException(new IntentException(MobirollerIntent.getConnectionRequiredIntent(navigationItemModel)));
        }
    }

    private void getLiveScreenModelAsync(final NavigationItemModel navigationItemModel) {
        new ApiRequestManager().getScreenJSONAsync(navigationItemModel.getAccountScreenID()).enqueue(new Callback<ScreenModel>() {
            public void onResponse(Call<ScreenModel> call, Response<ScreenModel> response) {
                if (response.body() != null) {
                    ServiceUtil.this.screenModel = (ScreenModel) response.body();
                    ServiceUtil serviceUtil = ServiceUtil.this;
                    serviceUtil.handleResult(navigationItemModel, serviceUtil.screenModel);
                    return;
                }
                ServiceUtil.this.getLocalScreenModelAndDisplay(navigationItemModel);
            }

            public void onFailure(Call<ScreenModel> call, Throwable th) {
                ServiceUtil.this.getLocalScreenModelAndDisplay(navigationItemModel);
            }
        });
    }

    /* access modifiers changed from: private */
    public void getLocalScreenModelAndDisplay(NavigationItemModel navigationItemModel) {
        if (DynamicConstants.MobiRoller_Stage) {
            this.serviceInterface.onThrowIntentException(new IntentException(MobirollerIntent.getConnectionRequiredIntent(navigationItemModel)));
            return;
        }
        this.screenModel = getLocalScreenModel(navigationItemModel);
        handleResult(navigationItemModel, this.screenModel);
    }

    /* access modifiers changed from: private */
    public void handleResult(NavigationItemModel navigationItemModel, ScreenModel screenModel2) {
        try {
            this.serviceInterface.onScreenModelFetched(MenuHelper.getFragment(navigationItemModel, screenModel2), this.itemPosition);
        } catch (UserFriendlyException e) {
            this.serviceInterface.onThrowUserFriendlyException(e);
        } catch (IntentException e2) {
            this.serviceInterface.onThrowIntentException(e2);
        }
    }

    private ScreenModel getLocalScreenModel(NavigationItemModel navigationItemModel) {
        return new JSONParser().getLocalScreenModel(MobiRollerApplication.app, navigationItemModel.getAccountScreenID());
    }
}
