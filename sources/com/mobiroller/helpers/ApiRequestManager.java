package com.mobiroller.helpers;

import com.mobiroller.DynamicConstants;
import com.mobiroller.constants.Constants;
import com.mobiroller.models.MainModel;
import com.mobiroller.models.NavigationModel;
import com.mobiroller.models.ScreenModel;
import com.mobiroller.serviceinterfaces.MobirollerServiceInterface;
import com.mobiroller.serviceinterfaces.MobirollerServicePreviewInterface;
import retrofit2.Call;

public class ApiRequestManager {
    private MobirollerServiceInterface mobirollerServiceInterface;
    private MobirollerServicePreviewInterface mobirollerServicePreviewInterface;
    private SharedPrefHelper sharedPrefHelper;

    public ApiRequestManager(SharedPrefHelper sharedPrefHelper2, RequestHelper requestHelper) {
        this.sharedPrefHelper = sharedPrefHelper2;
        if (DynamicConstants.MobiRoller_Stage) {
            this.mobirollerServicePreviewInterface = requestHelper.getPreviewAPIService();
        }
        this.mobirollerServiceInterface = requestHelper.getAPIService();
    }

    public ApiRequestManager() {
        this.sharedPrefHelper = UtilManager.sharedPrefHelper();
        RequestHelper requestHelper = new RequestHelper();
        if (DynamicConstants.MobiRoller_Stage) {
            this.mobirollerServicePreviewInterface = requestHelper.getPreviewAPIService();
        }
        this.mobirollerServiceInterface = requestHelper.getAPIService();
    }

    /* access modifiers changed from: 0000 */
    public MainModel getMainJSON(String str) {
        Call call;
        if (DynamicConstants.MobiRoller_Stage) {
            call = this.mobirollerServicePreviewInterface.getMainJSON(str);
        } else {
            call = this.mobirollerServiceInterface.getMainJSON(str);
        }
        try {
            return (MainModel) call.execute().body();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public Call<MainModel> getMainJSONAsync(String str) {
        if (DynamicConstants.MobiRoller_Stage) {
            return this.mobirollerServicePreviewInterface.getMainJSON(str);
        }
        return this.mobirollerServiceInterface.getMainJSON(str);
    }

    public NavigationModel getNavigationJSON(String str) {
        Call call;
        if (DynamicConstants.MobiRoller_Stage) {
            MobirollerServicePreviewInterface mobirollerServicePreviewInterface2 = this.mobirollerServicePreviewInterface;
            StringBuilder sb = new StringBuilder();
            sb.append(Constants.MobiRoller_Preferences_NAVUrl);
            sb.append(str);
            call = mobirollerServicePreviewInterface2.getNavigationJSON(sb.toString());
        } else {
            MobirollerServiceInterface mobirollerServiceInterface2 = this.mobirollerServiceInterface;
            StringBuilder sb2 = new StringBuilder();
            sb2.append(Constants.MobiRoller_Preferences_NAVUrl);
            sb2.append(str);
            call = mobirollerServiceInterface2.getNavigationJSON(sb2.toString());
        }
        try {
            NavigationModel navigationModel = (NavigationModel) call.execute().body();
            JSONStorage.putNavigationModel(navigationModel);
            return navigationModel;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public Call<NavigationModel> getNavigationJSONAsync(String str) {
        if (DynamicConstants.MobiRoller_Stage) {
            MobirollerServicePreviewInterface mobirollerServicePreviewInterface2 = this.mobirollerServicePreviewInterface;
            StringBuilder sb = new StringBuilder();
            sb.append(Constants.MobiRoller_Preferences_NAVUrl);
            sb.append(str);
            return mobirollerServicePreviewInterface2.getNavigationJSON(sb.toString());
        }
        MobirollerServiceInterface mobirollerServiceInterface2 = this.mobirollerServiceInterface;
        StringBuilder sb2 = new StringBuilder();
        sb2.append(Constants.MobiRoller_Preferences_NAVUrl);
        sb2.append(str);
        return mobirollerServiceInterface2.getNavigationJSON(sb2.toString());
    }

    public ScreenModel getScreenJSON(String str) {
        Call call;
        if (DynamicConstants.MobiRoller_Stage) {
            call = this.mobirollerServicePreviewInterface.getScreenJSON(str);
        } else {
            call = this.mobirollerServiceInterface.getScreenJSON(str);
        }
        try {
            ScreenModel screenModel = (ScreenModel) call.execute().body();
            JSONStorage.putScreenModel(str, screenModel);
            return screenModel;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public Call<ScreenModel> getScreenJSONAsync(String str) {
        if (DynamicConstants.MobiRoller_Stage) {
            return this.mobirollerServicePreviewInterface.getScreenJSON(str);
        }
        return this.mobirollerServiceInterface.getScreenJSON(str);
    }

    /* access modifiers changed from: 0000 */
    public void sendFeedback(String str, int i) {
        try {
            this.mobirollerServiceInterface.sendFeedback(this.sharedPrefHelper.getUsername(), str, i).execute().body();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String getIPAddress() {
        try {
            return (String) this.mobirollerServiceInterface.getIpAddress().execute().body();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
