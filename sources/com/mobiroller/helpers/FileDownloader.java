package com.mobiroller.helpers;

import android.content.Context;
import android.content.res.AssetManager;
import com.google.gson.Gson;
import com.mobiroller.constants.Constants;
import com.mobiroller.models.InAppPurchaseModel;
import com.mobiroller.models.MainModel;
import com.mobiroller.models.NavigationModel;
import com.mobiroller.models.ScreenModel;
import java.io.File;
import java.io.InputStream;
import timber.log.Timber;

public class FileDownloader {
    /* access modifiers changed from: 0000 */
    public ScreenModel copyScreenLocalJSONFile(Context context, String str) {
        String str2 = "JSON OP";
        StringBuilder sb = new StringBuilder();
        sb.append(str);
        sb.append(".json");
        String sb2 = sb.toString();
        try {
            Timber.tag(str2).mo23218d("=========================", new Object[0]);
            Timber.tag(str2).mo23218d("Assets - Screen Json", new Object[0]);
            Timber.tag(str2).mo23218d("Fetching json from assets", new Object[0]);
            AssetManager assets = context.getAssets();
            StringBuilder sb3 = new StringBuilder();
            sb3.append(Constants.MobiRoller_Preferences_Assets_Directory_Path);
            sb3.append(sb2);
            InputStream open = assets.open(sb3.toString());
            byte[] bArr = new byte[open.available()];
            open.read(bArr);
            open.close();
            ScreenModel screenModel = (ScreenModel) new Gson().fromJson(new String(bArr, Constants.MobiRoller_Preferences_CharSet), ScreenModel.class);
            JSONStorage.putScreenModel(str, screenModel);
            return screenModel;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /* access modifiers changed from: 0000 */
    public void copyMainLocalJSONFile(Context context, String str) {
        String str2 = "JSON OP";
        StringBuilder sb = new StringBuilder();
        sb.append(str);
        sb.append(".json");
        String sb2 = sb.toString();
        try {
            Timber.tag(str2).mo23218d("=========================", new Object[0]);
            Timber.tag(str2).mo23218d("Assets - Main Json", new Object[0]);
            Timber.tag(str2).mo23218d("Fetching json from assets", new Object[0]);
            AssetManager assets = context.getAssets();
            StringBuilder sb3 = new StringBuilder();
            sb3.append(Constants.MobiRoller_Preferences_Assets_Directory_Path);
            sb3.append(sb2);
            InputStream open = assets.open(sb3.toString());
            byte[] bArr = new byte[open.available()];
            open.read(bArr);
            open.close();
            JSONStorage.putMainModel((MainModel) new Gson().fromJson(new String(bArr, Constants.MobiRoller_Preferences_CharSet), MainModel.class));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /* access modifiers changed from: 0000 */
    public void copyNavigationLocalJSONFile(Context context, String str) {
        String str2 = "JSON OP";
        StringBuilder sb = new StringBuilder();
        sb.append(Constants.MobiRoller_Preferences_NAVUrl);
        sb.append(str);
        sb.append(".json");
        String sb2 = sb.toString();
        try {
            Timber.tag(str2).mo23218d("=========================", new Object[0]);
            Timber.tag(str2).mo23218d("Assets - Navigation Json", new Object[0]);
            Timber.tag(str2).mo23218d("Fetching json from assets", new Object[0]);
            AssetManager assets = context.getAssets();
            StringBuilder sb3 = new StringBuilder();
            sb3.append(Constants.MobiRoller_Preferences_Assets_Directory_Path);
            sb3.append(sb2);
            InputStream open = assets.open(sb3.toString());
            byte[] bArr = new byte[open.available()];
            open.read(bArr);
            open.close();
            JSONStorage.putNavigationModel((NavigationModel) new Gson().fromJson(new String(bArr, Constants.MobiRoller_Preferences_CharSet), NavigationModel.class));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /* access modifiers changed from: 0000 */
    public void copyInAppPurchaseLocalJSONFile(Context context, String str) {
        String str2 = "Fetching json from assets";
        String str3 = "JSON OP";
        StringBuilder sb = new StringBuilder();
        sb.append(Constants.MobiRoller_Preferences_IN_APP_PURCHASE_URL);
        sb.append(str);
        sb.append(".json");
        String sb2 = sb.toString();
        try {
            Timber.tag(str3).mo23218d("=========================", new Object[0]);
            Timber.tag(str3).mo23218d("Assets - In App Purchase Json", new Object[0]);
            Timber.tag(str3).mo23218d(str2, new Object[0]);
            AssetManager assets = context.getAssets();
            StringBuilder sb3 = new StringBuilder();
            sb3.append(Constants.MobiRoller_Preferences_Assets_Directory_Path);
            sb3.append(sb2);
            InputStream open = assets.open(sb3.toString());
            byte[] bArr = new byte[open.available()];
            open.read(bArr);
            open.close();
            JSONStorage.putInAppPurchase((InAppPurchaseModel) new Gson().fromJson(new String(bArr, Constants.MobiRoller_Preferences_CharSet), InAppPurchaseModel.class));
        } catch (Exception e) {
            Timber.tag(str3).mo23218d(str2, new Object[0]);
            e.printStackTrace();
        }
    }

    public void clearLocalFiles(Context context) {
        StringBuilder sb = new StringBuilder();
        sb.append(Constants.Mobiroller_Preferences_FilePath);
        sb.append("/");
        File file = new File(sb.toString());
        if (file.isDirectory()) {
            String[] list = file.list();
            for (String file2 : list) {
                new File(file, file2).delete();
            }
        }
    }
}
