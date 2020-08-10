package com.mobiroller.helpers;

import android.content.Context;
import com.mobiroller.constants.Constants;
import com.mobiroller.mobi942763453128.R;
import com.mobiroller.models.InAppPurchaseModel;
import com.mobiroller.models.MChatRoleModel;
import com.mobiroller.models.MobirollerBadgeModel;
import com.mobiroller.models.events.MobirollerBadgeEvent;
import com.mobiroller.models.response.CommunityRoleResponse;
import com.mobiroller.serviceinterfaces.MobirollerServiceInterface;
import com.mobiroller.util.ChatRolesUtil;
import java.util.ArrayList;
import java.util.List;
import org.greenrobot.eventbus.EventBus;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AsyncRequestHelper {
    public static JSONParser jParserNew;
    /* access modifiers changed from: private */
    public static ApiRequestManager mApiRequestHelper;
    public static Context mContext;
    /* access modifiers changed from: private */
    public static RequestHelper mRequestHelper;
    public static NetworkHelper networkHelper;
    public static SharedPrefHelper sharedPrefHelper;

    public static final class Builder {
        public Builder setContext(Context context) {
            AsyncRequestHelper.mContext = context;
            return this;
        }

        public void build() {
            if (AsyncRequestHelper.mContext != null) {
                AsyncRequestHelper.networkHelper = new NetworkHelper(AsyncRequestHelper.mContext);
                AsyncRequestHelper.sharedPrefHelper = UtilManager.sharedPrefHelper();
                AsyncRequestHelper.mRequestHelper = new RequestHelper(AsyncRequestHelper.mContext, AsyncRequestHelper.sharedPrefHelper);
                AsyncRequestHelper.mApiRequestHelper = new ApiRequestManager(AsyncRequestHelper.sharedPrefHelper, AsyncRequestHelper.mRequestHelper);
                AsyncRequestHelper.jParserNew = new JSONParser(new FileDownloader(), AsyncRequestHelper.mApiRequestHelper, AsyncRequestHelper.networkHelper);
                return;
            }
            throw new RuntimeException("Context not set, please set context before building the Prefs instance.");
        }
    }

    public static void getChatRoles() {
        new RequestHelper(mContext, sharedPrefHelper).getApplyzeUserAPIService().getCommunityRolesList(mContext.getString(R.string.applyze_api_key), mContext.getString(R.string.applyze_app_key)).enqueue(new Callback<List<CommunityRoleResponse>>() {
            public void onFailure(Call<List<CommunityRoleResponse>> call, Throwable th) {
            }

            public void onResponse(Call<List<CommunityRoleResponse>> call, Response<List<CommunityRoleResponse>> response) {
                if (response.isSuccessful()) {
                    ArrayList arrayList = new ArrayList();
                    List list = (List) response.body();
                    for (int i = 0; i < list.size(); i++) {
                        MChatRoleModel mChatRoleModel = new MChatRoleModel(((CommunityRoleResponse) list.get(i)).f2187id, ((CommunityRoleResponse) list.get(i)).permissionTypeId, ((CommunityRoleResponse) list.get(i)).title, ((CommunityRoleResponse) list.get(i)).description, ((CommunityRoleResponse) list.get(i)).ribbonImage, ((CommunityRoleResponse) list.get(i)).isIncognito);
                        arrayList.add(mChatRoleModel);
                    }
                    ChatRolesUtil.updateDb(arrayList, AsyncRequestHelper.mContext);
                }
            }
        });
    }

    public static void getInAppPurchaseItems() {
        if (networkHelper.isConnected()) {
            MobirollerServiceInterface aPIService = new RequestHelper(mContext, sharedPrefHelper).getAPIService();
            StringBuilder sb = new StringBuilder();
            sb.append("aveInAppPurchase_");
            sb.append(mContext.getString(R.string.mobiroller_username));
            aPIService.getInAppPurchaseProducts(sb.toString()).enqueue(new Callback<InAppPurchaseModel>() {
                public void onResponse(Call<InAppPurchaseModel> call, Response<InAppPurchaseModel> response) {
                    if (response.isSuccessful()) {
                        JSONStorage.putInAppPurchase((InAppPurchaseModel) response.body());
                    }
                }

                public void onFailure(Call<InAppPurchaseModel> call, Throwable th) {
                    JSONStorage.putInAppPurchase(AsyncRequestHelper.getInAppPurchaseModelFromLocal());
                }
            });
            return;
        }
        JSONStorage.putInAppPurchase(getInAppPurchaseModelFromLocal());
    }

    public static void getBadgeModel(String str) {
        if (networkHelper.isConnected()) {
            new RequestHelper(mContext, sharedPrefHelper).getAPIService().getBadgeInfo(str).enqueue(new Callback<MobirollerBadgeModel>() {
                public void onResponse(Call<MobirollerBadgeModel> call, Response<MobirollerBadgeModel> response) {
                    if (response.isSuccessful()) {
                        JSONStorage.putMobirollerBadgeModel((MobirollerBadgeModel) response.body());
                        UtilManager.sharedPrefHelper().put(Constants.MobiRoller_Preferences_MobiRoller_Badge_Check_Response, true);
                        EventBus.getDefault().post(new MobirollerBadgeEvent());
                    }
                }

                public void onFailure(Call<MobirollerBadgeModel> call, Throwable th) {
                    UtilManager.sharedPrefHelper().put(Constants.MobiRoller_Preferences_MobiRoller_Badge_Check_Response, true);
                    if (JSONStorage.getMobirollerBadgeModel() == null) {
                        UtilManager.sharedPrefHelper().put(Constants.MobiRoller_Preferences_Show_MobiRoller_Badge, false);
                    }
                    EventBus.getDefault().post(new MobirollerBadgeEvent());
                }
            });
            return;
        }
        UtilManager.sharedPrefHelper().put(Constants.MobiRoller_Preferences_MobiRoller_Badge_Check_Response, true);
        if (JSONStorage.getMobirollerBadgeModel() == null) {
            UtilManager.sharedPrefHelper().put(Constants.MobiRoller_Preferences_Show_MobiRoller_Badge, false);
        }
        EventBus.getDefault().post(new MobirollerBadgeEvent());
    }

    /* access modifiers changed from: private */
    public static InAppPurchaseModel getInAppPurchaseModelFromLocal() {
        InAppPurchaseModel inAppPurchaseModel = JSONStorage.getInAppPurchaseModel();
        return inAppPurchaseModel == null ? getInAppPurchaseModelFromAssets() : inAppPurchaseModel;
    }

    private static InAppPurchaseModel getInAppPurchaseModelFromAssets() {
        InAppPurchaseModel localInAppPurchaseJSON = jParserNew.getLocalInAppPurchaseJSON(mContext, sharedPrefHelper.getUsername());
        JSONStorage.putInAppPurchase(localInAppPurchaseJSON);
        return localInAppPurchaseJSON;
    }
}
