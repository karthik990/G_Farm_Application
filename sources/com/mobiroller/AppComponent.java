package com.mobiroller;

import android.content.Context;
import com.mobiroller.helpers.ApiRequestManager;
import com.mobiroller.helpers.BannerHelper;
import com.mobiroller.helpers.ComponentHelper;
import com.mobiroller.helpers.FileDownloader;
import com.mobiroller.helpers.JSONParser;
import com.mobiroller.helpers.LocalizationHelper;
import com.mobiroller.helpers.NetworkHelper;
import com.mobiroller.helpers.ScreenHelper;
import com.mobiroller.helpers.SharedPrefHelper;
import com.mobiroller.helpers.ToolbarHelper;
import com.mobiroller.scopes.ApplicationLevel;
import com.mobiroller.util.ImageManager;
import dagger.Component;

@Component(modules = {AppModule.class})
@ApplicationLevel
public interface AppComponent {
    ApiRequestManager getApiRequestManager();

    MobiRollerApplication getApplication();

    BannerHelper getBannerHelper();

    ComponentHelper getComponentHelper();

    Context getContext();

    FileDownloader getFileDownloader();

    ImageManager getImageManager();

    JSONParser getJsonParser();

    LocalizationHelper getLocalizationHelper();

    NetworkHelper getNetworkHelper();

    ScreenHelper getScreenHelper();

    SharedPrefHelper getSharedPref();

    ToolbarHelper getToolbarHelper();

    void inject(MobiRollerApplication mobiRollerApplication);
}
