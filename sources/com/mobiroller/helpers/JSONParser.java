package com.mobiroller.helpers;

import android.content.Context;
import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;
import com.mobiroller.DynamicConstants;
import com.mobiroller.MobiRollerApplication;
import com.mobiroller.models.InAppPurchaseModel;
import com.mobiroller.models.MainModel;
import com.mobiroller.models.NavigationModel;
import com.mobiroller.models.ScreenModel;
import com.mobiroller.util.DateUtil;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import timber.log.Timber;

public class JSONParser {
    private FileDownloader fileDownloader;
    private ApiRequestManager requestManager;

    public JSONParser(FileDownloader fileDownloader2, ApiRequestManager apiRequestManager, NetworkHelper networkHelper) {
        this.fileDownloader = fileDownloader2;
        this.requestManager = apiRequestManager;
    }

    public JSONParser() {
        this.fileDownloader = new FileDownloader();
        this.requestManager = new ApiRequestManager();
    }

    public ScreenModel getScreenJSONFromLocalByID(Context context, String str, boolean z, boolean z2, boolean z3) {
        ScreenModel screenModel;
        if (DynamicConstants.MobiRoller_Stage) {
            return getPreviewJSON(str);
        }
        try {
            ScreenModel screenModel2 = JSONStorage.getScreenModel(str);
            if ((!z || (MobiRollerApplication.jsonIdList != null && !MobiRollerApplication.jsonIdList.contains(str))) && z2 && !z3) {
                screenModel = this.requestManager.getScreenJSON(str);
                if (screenModel != null && (screenModel2 == null || !z)) {
                    JSONStorage.putScreenModel(str, screenModel);
                }
                MobiRollerApplication.jsonIdList.add(str);
            } else {
                screenModel = null;
            }
            if (screenModel2 != null || z3) {
                ScreenModel screenModel3 = JSONStorage.getScreenModel(str);
                return (!z2 || screenModel == null || DateUtil.dateControlString(screenModel3.getUpdateDate(), screenModel.getUpdateDate())) ? screenModel3 : getScreenJSONFromLocalByID(context, str, false, true, false);
            }
            this.fileDownloader.copyMainLocalJSONFile(context, str);
            return getScreenJSONFromLocalByID(context, str, z, z2, true);
        } catch (Exception unused) {
            return null;
        }
    }

    public ScreenModel getScreenJSONFromLocalByID(Context context, String str, boolean z, String str2) {
        ScreenModel screenModel;
        String str3 = "JSON OP";
        Timber.tag(str3).mo23218d("================================", new Object[0]);
        Timber.tag(str3).mo23218d("getScreenJSONFromLocalByID Started", new Object[0]);
        Timber.tag(str3).mo23218d("ScreenId: %1s ", str);
        if (DynamicConstants.MobiRoller_Stage) {
            Timber.tag(str3).mo23218d("STAGE", new Object[0]);
            return getPreviewJSON(str);
        }
        try {
            ScreenModel screenModel2 = JSONStorage.getScreenModel(str);
            try {
                screenModel = isLocalJSONLatest(str2, context, str);
                if (screenModel != null) {
                    return screenModel;
                }
            } catch (Exception e) {
                e.printStackTrace();
                screenModel = null;
            }
            if (MobiRollerApplication.jsonIdList != null && !MobiRollerApplication.jsonIdList.contains(str) && z) {
                Timber.tag(str3).mo23218d("Device is Online", new Object[0]);
                Timber.tag(str3).mo23218d("Fetching json from online", new Object[0]);
                screenModel = this.requestManager.getScreenJSON(str);
                if (screenModel != null && screenModel2 == null) {
                    JSONStorage.putScreenModel(str, screenModel);
                    screenModel2 = screenModel;
                }
                MobiRollerApplication.jsonIdList.add(str);
            }
            if (screenModel2 == null) {
                Timber.tag(str3).mo23218d("Fetching json from assets", new Object[0]);
                this.fileDownloader.copyMainLocalJSONFile(context, str);
                return getScreenJSONFromLocalByID(context, str, true, z, true);
            }
            ScreenModel screenModel3 = JSONStorage.getScreenModel(str);
            return (!z || screenModel == null || DateUtil.dateControlString(screenModel3.getUpdateDate(), screenModel.getUpdateDate())) ? screenModel3 : getScreenJSONFromLocalByID(context, str, false, z, false);
        } catch (Exception unused) {
            return null;
        }
    }

    private ScreenModel isLocalJSONLatest(String str, Context context, String str2) {
        ScreenModel screenModel = JSONStorage.getScreenModel(str2);
        if (screenModel == null) {
            screenModel = this.fileDownloader.copyScreenLocalJSONFile(context, str2);
        }
        if (str == null) {
            return screenModel;
        }
        if (screenModel == null || !DateUtil.dateControlString(screenModel.getUpdateDate(), str)) {
            return null;
        }
        return screenModel;
    }

    public ScreenModel getLocalScreenModel(Context context, String str) {
        ScreenModel screenModel = JSONStorage.getScreenModel(str);
        if (screenModel != null) {
            return screenModel;
        }
        return this.fileDownloader.copyScreenLocalJSONFile(context, str);
    }

    public NavigationModel getLocalNavigationJSON(Context context, String str) {
        try {
            if (JSONStorage.getNavigationModel() == null) {
                this.fileDownloader.copyNavigationLocalJSONFile(context, str);
            }
            return JSONStorage.getNavigationModel();
        } catch (Exception unused) {
            return null;
        }
    }

    public InAppPurchaseModel getLocalInAppPurchaseJSON(Context context, String str) {
        try {
            if (JSONStorage.getInAppPurchaseModel() == null) {
                this.fileDownloader.copyInAppPurchaseLocalJSONFile(context, str);
            }
            return JSONStorage.getInAppPurchaseModel();
        } catch (Exception unused) {
            return null;
        }
    }

    public MainModel getJSONMainOffline(InputStream inputStream) {
        return (MainModel) new Gson().fromJson(new JsonReader(new BufferedReader(new InputStreamReader(inputStream))), (Type) MainModel.class);
    }

    /* JADX WARNING: Code restructure failed: missing block: B:11:0x002d, code lost:
        if (r2.contains(r3.toString()) == false) goto L_0x002f;
     */
    /* JADX WARNING: Removed duplicated region for block: B:22:0x005c A[ADDED_TO_REGION, Catch:{ Exception -> 0x0090 }] */
    /* JADX WARNING: Removed duplicated region for block: B:27:0x0074 A[ADDED_TO_REGION, Catch:{ Exception -> 0x0090 }] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public com.mobiroller.models.NavigationModel getNavigationJSONFromLocal(android.content.Context r8, java.lang.String r9, boolean r10, boolean r11, boolean r12) {
        /*
            r7 = this;
            boolean r0 = com.mobiroller.DynamicConstants.MobiRoller_Stage
            if (r0 == 0) goto L_0x000b
            com.mobiroller.helpers.ApiRequestManager r8 = r7.requestManager
            com.mobiroller.models.NavigationModel r8 = r8.getNavigationJSON(r9)
            return r8
        L_0x000b:
            r0 = 0
            com.mobiroller.models.NavigationModel r1 = com.mobiroller.helpers.JSONStorage.getNavigationModel()     // Catch:{ Exception -> 0x0090 }
            if (r10 == 0) goto L_0x002f
            java.util.List r2 = com.mobiroller.MobiRollerApplication.jsonIdList     // Catch:{ Exception -> 0x0090 }
            if (r2 == 0) goto L_0x0059
            java.util.List r2 = com.mobiroller.MobiRollerApplication.jsonIdList     // Catch:{ Exception -> 0x0090 }
            java.lang.StringBuilder r3 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x0090 }
            r3.<init>()     // Catch:{ Exception -> 0x0090 }
            java.lang.String r4 = com.mobiroller.constants.Constants.MobiRoller_Preferences_NAVUrl     // Catch:{ Exception -> 0x0090 }
            r3.append(r4)     // Catch:{ Exception -> 0x0090 }
            r3.append(r9)     // Catch:{ Exception -> 0x0090 }
            java.lang.String r3 = r3.toString()     // Catch:{ Exception -> 0x0090 }
            boolean r2 = r2.contains(r3)     // Catch:{ Exception -> 0x0090 }
            if (r2 != 0) goto L_0x0059
        L_0x002f:
            if (r11 == 0) goto L_0x0059
            if (r12 != 0) goto L_0x0059
            com.mobiroller.helpers.ApiRequestManager r2 = r7.requestManager     // Catch:{ Exception -> 0x0090 }
            com.mobiroller.models.NavigationModel r2 = r2.getNavigationJSON(r9)     // Catch:{ Exception -> 0x0090 }
            if (r2 == 0) goto L_0x0042
            if (r1 == 0) goto L_0x003f
            if (r10 != 0) goto L_0x0042
        L_0x003f:
            com.mobiroller.helpers.JSONStorage.putNavigationModel(r2)     // Catch:{ Exception -> 0x0090 }
        L_0x0042:
            java.util.List r3 = com.mobiroller.MobiRollerApplication.jsonIdList     // Catch:{ Exception -> 0x0090 }
            java.lang.StringBuilder r4 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x0090 }
            r4.<init>()     // Catch:{ Exception -> 0x0090 }
            java.lang.String r5 = com.mobiroller.constants.Constants.MobiRoller_Preferences_NAVUrl     // Catch:{ Exception -> 0x0090 }
            r4.append(r5)     // Catch:{ Exception -> 0x0090 }
            r4.append(r9)     // Catch:{ Exception -> 0x0090 }
            java.lang.String r4 = r4.toString()     // Catch:{ Exception -> 0x0090 }
            r3.add(r4)     // Catch:{ Exception -> 0x0090 }
            goto L_0x005a
        L_0x0059:
            r2 = r0
        L_0x005a:
            if (r1 != 0) goto L_0x006e
            if (r12 != 0) goto L_0x006e
            com.mobiroller.helpers.FileDownloader r12 = r7.fileDownloader     // Catch:{ Exception -> 0x0090 }
            r12.copyNavigationLocalJSONFile(r8, r9)     // Catch:{ Exception -> 0x0090 }
            r6 = 1
            r1 = r7
            r2 = r8
            r3 = r9
            r4 = r10
            r5 = r11
            com.mobiroller.models.NavigationModel r8 = r1.getNavigationJSONFromLocal(r2, r3, r4, r5, r6)     // Catch:{ Exception -> 0x0090 }
            return r8
        L_0x006e:
            com.mobiroller.models.NavigationModel r10 = com.mobiroller.helpers.JSONStorage.getNavigationModel()     // Catch:{ Exception -> 0x0090 }
            if (r11 == 0) goto L_0x008f
            if (r2 == 0) goto L_0x008f
            java.lang.String r12 = r10.getUpdateDate()     // Catch:{ Exception -> 0x0090 }
            java.lang.String r1 = r2.getUpdateDate()     // Catch:{ Exception -> 0x0090 }
            boolean r12 = com.mobiroller.util.DateUtil.dateControlString(r12, r1)     // Catch:{ Exception -> 0x0090 }
            if (r12 != 0) goto L_0x008f
            r4 = 0
            r6 = 0
            r1 = r7
            r2 = r8
            r3 = r9
            r5 = r11
            com.mobiroller.models.NavigationModel r8 = r1.getNavigationJSONFromLocal(r2, r3, r4, r5, r6)     // Catch:{ Exception -> 0x0090 }
            return r8
        L_0x008f:
            return r10
        L_0x0090:
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.mobiroller.helpers.JSONParser.getNavigationJSONFromLocal(android.content.Context, java.lang.String, boolean, boolean, boolean):com.mobiroller.models.NavigationModel");
    }

    private ScreenModel getPreviewJSON(String str) {
        return this.requestManager.getScreenJSON(str);
    }
}
