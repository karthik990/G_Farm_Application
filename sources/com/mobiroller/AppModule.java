package com.mobiroller;

import android.app.Application;
import android.content.Context;
import com.mobiroller.helpers.ApiRequestManager;
import com.mobiroller.helpers.BannerHelper;
import com.mobiroller.helpers.ComponentHelper;
import com.mobiroller.helpers.FileDownloader;
import com.mobiroller.helpers.JSONParser;
import com.mobiroller.helpers.LocalizationHelper;
import com.mobiroller.helpers.NetworkHelper;
import com.mobiroller.helpers.RequestHelper;
import com.mobiroller.helpers.ScreenHelper;
import com.mobiroller.helpers.SharedPrefHelper;
import com.mobiroller.helpers.ToolbarHelper;
import com.mobiroller.scopes.ApplicationLevel;
import com.mobiroller.util.ImageManager;
import dagger.Module;
import dagger.Provides;

@Module
public class AppModule {
    private Application mApplication;

    public AppModule(Application application) {
        this.mApplication = application;
    }

    /* access modifiers changed from: 0000 */
    @ApplicationLevel
    @Provides
    public Context providesApplicationContext() {
        return this.mApplication;
    }

    /* access modifiers changed from: 0000 */
    @ApplicationLevel
    @Provides
    public MobiRollerApplication providesApplication() {
        return (MobiRollerApplication) this.mApplication;
    }

    /* access modifiers changed from: 0000 */
    @ApplicationLevel
    @Provides
    public SharedPrefHelper providesSharedPrefHelper(Context context) {
        return new SharedPrefHelper(context);
    }

    /* access modifiers changed from: 0000 */
    @ApplicationLevel
    @Provides
    public NetworkHelper providesNetworkHelper(Context context) {
        return new NetworkHelper(context);
    }

    /* access modifiers changed from: 0000 */
    @ApplicationLevel
    @Provides
    public ScreenHelper providesScreenHelper(Context context) {
        return new ScreenHelper(context);
    }

    /* access modifiers changed from: 0000 */
    @ApplicationLevel
    @Provides
    public FileDownloader providesFileDownloader() {
        return new FileDownloader();
    }

    /* access modifiers changed from: 0000 */
    @ApplicationLevel
    @Provides
    public JSONParser providesJSONParser(FileDownloader fileDownloader, ApiRequestManager apiRequestManager, NetworkHelper networkHelper) {
        return new JSONParser(fileDownloader, apiRequestManager, networkHelper);
    }

    /* access modifiers changed from: 0000 */
    @ApplicationLevel
    @Provides
    public ImageManager providesImageManager() {
        return new ImageManager();
    }

    /* access modifiers changed from: 0000 */
    @ApplicationLevel
    @Provides
    public ApiRequestManager providesApiRequestManager(SharedPrefHelper sharedPrefHelper, RequestHelper requestHelper) {
        return new ApiRequestManager(sharedPrefHelper, requestHelper);
    }

    /* access modifiers changed from: 0000 */
    @ApplicationLevel
    @Provides
    public ToolbarHelper providesToolbarHelper(SharedPrefHelper sharedPrefHelper) {
        return new ToolbarHelper(sharedPrefHelper);
    }

    /* access modifiers changed from: 0000 */
    @ApplicationLevel
    @Provides
    public LocalizationHelper providesLocalizationHelper() {
        return new LocalizationHelper();
    }

    /* access modifiers changed from: 0000 */
    @ApplicationLevel
    @Provides
    public BannerHelper providesBannerHelper(SharedPrefHelper sharedPrefHelper, Context context, NetworkHelper networkHelper) {
        return new BannerHelper(sharedPrefHelper, context, networkHelper);
    }

    /* access modifiers changed from: 0000 */
    @ApplicationLevel
    @Provides
    public ComponentHelper providesComponentHelper(SharedPrefHelper sharedPrefHelper, ScreenHelper screenHelper) {
        return new ComponentHelper(screenHelper, sharedPrefHelper);
    }

    /* access modifiers changed from: 0000 */
    @ApplicationLevel
    @Provides
    public RequestHelper providesRequestHelper(Context context, SharedPrefHelper sharedPrefHelper) {
        return new RequestHelper(context, sharedPrefHelper);
    }
}
