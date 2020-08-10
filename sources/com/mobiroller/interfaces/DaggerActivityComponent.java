package com.mobiroller.interfaces;

import android.app.Activity;
import androidx.appcompat.app.AppCompatActivity;
import com.mobiroller.AppComponent;
import com.mobiroller.MobiRollerApplication;
import com.mobiroller.activities.AuthorizationActivity;
import com.mobiroller.activities.AveAddNoteActivity;
import com.mobiroller.activities.ConnectionRequired;
import com.mobiroller.activities.ConnectionRequired_MembersInjector;
import com.mobiroller.activities.GenericActivity;
import com.mobiroller.activities.GenericActivity_MembersInjector;
import com.mobiroller.activities.ImagePagerActivity;
import com.mobiroller.activities.PermissionRequiredActivity;
import com.mobiroller.activities.PermissionRequiredActivity_MembersInjector;
import com.mobiroller.activities.SplashApp;
import com.mobiroller.activities.SplashApp_MembersInjector;
import com.mobiroller.activities.aveCallNowView;
import com.mobiroller.activities.aveCallNowView_MembersInjector;
import com.mobiroller.activities.aveChatView;
import com.mobiroller.activities.aveChatView_MembersInjector;
import com.mobiroller.activities.aveFullScreenVideo;
import com.mobiroller.activities.aveRSSView;
import com.mobiroller.activities.aveRSSView_MembersInjector;
import com.mobiroller.activities.aveRssContentViewPager;
import com.mobiroller.activities.aveRssContentViewPager_MembersInjector;
import com.mobiroller.activities.aveShareView;
import com.mobiroller.activities.aveShareView_MembersInjector;
import com.mobiroller.activities.aveWebView;
import com.mobiroller.activities.aveWebView_MembersInjector;
import com.mobiroller.activities.aveYoutubeAdvancedView;
import com.mobiroller.activities.aveYoutubeAdvancedView_MembersInjector;
import com.mobiroller.activities.chat.ChatActivity;
import com.mobiroller.activities.chat.ChatActivity_MembersInjector;
import com.mobiroller.activities.chat.ChatAdminActivity;
import com.mobiroller.activities.chat.ChatAdminActivity_MembersInjector;
import com.mobiroller.activities.chat.ChatArchivedDialogActivity;
import com.mobiroller.activities.chat.ChatArchivedDialogActivity_MembersInjector;
import com.mobiroller.activities.chat.ChatUserSearchResultActivity;
import com.mobiroller.activities.chat.ChatUserSearchResultActivity_MembersInjector;
import com.mobiroller.activities.chat.GroupChatActivity;
import com.mobiroller.activities.chat.GroupChatActivity_MembersInjector;
import com.mobiroller.activities.menu.ListMenu;
import com.mobiroller.activities.menu.ListMenu_MembersInjector;
import com.mobiroller.activities.menu.MainActivity;
import com.mobiroller.activities.menu.MainActivity_MembersInjector;
import com.mobiroller.activities.menu.SlidingMenu;
import com.mobiroller.activities.menu.SlidingPanelActivity;
import com.mobiroller.activities.menu.SlidingPanelActivity_MembersInjector;
import com.mobiroller.activities.menu.aveNavigationActivity;
import com.mobiroller.activities.user.UserAddressActivity;
import com.mobiroller.activities.user.UserAddressActivity_MembersInjector;
import com.mobiroller.activities.user.UserChangePasswordActivity;
import com.mobiroller.activities.user.UserChangePasswordActivity_MembersInjector;
import com.mobiroller.activities.user.UserLoginActivity;
import com.mobiroller.activities.user.UserLoginActivity_MembersInjector;
import com.mobiroller.activities.user.UserOrderActivity;
import com.mobiroller.activities.user.UserOrderActivity_MembersInjector;
import com.mobiroller.activities.user.UserProfileActivity;
import com.mobiroller.activities.user.UserProfileActivity_MembersInjector;
import com.mobiroller.activities.user.UserRegisterActivity;
import com.mobiroller.activities.user.UserRegisterActivity_MembersInjector;
import com.mobiroller.activities.user.UserResetPasswordActivity;
import com.mobiroller.activities.user.UserResetPasswordActivity_MembersInjector;
import com.mobiroller.activities.user.UserUpdateActivity;
import com.mobiroller.activities.user.UserUpdateActivity_MembersInjector;
import com.mobiroller.activities.youtubeadvanced.ChannelDetailActivity;
import com.mobiroller.activities.youtubeadvanced.ChannelDetailActivity_MembersInjector;
import com.mobiroller.helpers.ApiRequestManager;
import com.mobiroller.helpers.BannerHelper;
import com.mobiroller.helpers.FileDownloader;
import com.mobiroller.helpers.JSONParser;
import com.mobiroller.helpers.LayoutHelper;
import com.mobiroller.helpers.LocalizationHelper;
import com.mobiroller.helpers.MenuHelper;
import com.mobiroller.helpers.NetworkHelper;
import com.mobiroller.helpers.ScreenHelper;
import com.mobiroller.helpers.SharedPrefHelper;
import com.mobiroller.helpers.ToolbarHelper;
import com.mobiroller.module.ActivityModule;
import com.mobiroller.module.ActivityModule_ProvidesAppCompatActivityFactory;
import com.mobiroller.module.ActivityModule_ProvidesLayoutHelperFactory;
import com.mobiroller.module.ActivityModule_ProvidesMenuHelperFactory;
import com.mobiroller.services.FCMNotificationHandler;
import com.mobiroller.services.FCMNotificationHandler_MembersInjector;
import com.mobiroller.util.ImageManager;
import dagger.internal.DoubleCheck;
import dagger.internal.Preconditions;
import javax.inject.Provider;

public final class DaggerActivityComponent implements ActivityComponent {
    private final AppComponent appComponent;
    private Provider<ApiRequestManager> getApiRequestManagerProvider;
    private Provider<MobiRollerApplication> getApplicationProvider;
    private Provider<JSONParser> getJsonParserProvider;
    private Provider<LocalizationHelper> getLocalizationHelperProvider;
    private Provider<NetworkHelper> getNetworkHelperProvider;
    private Provider<ScreenHelper> getScreenHelperProvider;
    private Provider<SharedPrefHelper> getSharedPrefProvider;
    private Provider<Activity> providesAppCompatActivityProvider;
    private Provider<LayoutHelper> providesLayoutHelperProvider;
    private Provider<MenuHelper> providesMenuHelperProvider;

    public static final class Builder {
        private ActivityModule activityModule;
        private AppComponent appComponent;

        private Builder() {
        }

        public Builder activityModule(ActivityModule activityModule2) {
            this.activityModule = (ActivityModule) Preconditions.checkNotNull(activityModule2);
            return this;
        }

        public Builder appComponent(AppComponent appComponent2) {
            this.appComponent = (AppComponent) Preconditions.checkNotNull(appComponent2);
            return this;
        }

        public ActivityComponent build() {
            Preconditions.checkBuilderRequirement(this.activityModule, ActivityModule.class);
            Preconditions.checkBuilderRequirement(this.appComponent, AppComponent.class);
            return new DaggerActivityComponent(this.activityModule, this.appComponent);
        }
    }

    private static class com_mobiroller_AppComponent_getApiRequestManager implements Provider<ApiRequestManager> {
        private final AppComponent appComponent;

        com_mobiroller_AppComponent_getApiRequestManager(AppComponent appComponent2) {
            this.appComponent = appComponent2;
        }

        public ApiRequestManager get() {
            return (ApiRequestManager) Preconditions.checkNotNull(this.appComponent.getApiRequestManager(), "Cannot return null from a non-@Nullable component method");
        }
    }

    private static class com_mobiroller_AppComponent_getApplication implements Provider<MobiRollerApplication> {
        private final AppComponent appComponent;

        com_mobiroller_AppComponent_getApplication(AppComponent appComponent2) {
            this.appComponent = appComponent2;
        }

        public MobiRollerApplication get() {
            return (MobiRollerApplication) Preconditions.checkNotNull(this.appComponent.getApplication(), "Cannot return null from a non-@Nullable component method");
        }
    }

    private static class com_mobiroller_AppComponent_getJsonParser implements Provider<JSONParser> {
        private final AppComponent appComponent;

        com_mobiroller_AppComponent_getJsonParser(AppComponent appComponent2) {
            this.appComponent = appComponent2;
        }

        public JSONParser get() {
            return (JSONParser) Preconditions.checkNotNull(this.appComponent.getJsonParser(), "Cannot return null from a non-@Nullable component method");
        }
    }

    private static class com_mobiroller_AppComponent_getLocalizationHelper implements Provider<LocalizationHelper> {
        private final AppComponent appComponent;

        com_mobiroller_AppComponent_getLocalizationHelper(AppComponent appComponent2) {
            this.appComponent = appComponent2;
        }

        public LocalizationHelper get() {
            return (LocalizationHelper) Preconditions.checkNotNull(this.appComponent.getLocalizationHelper(), "Cannot return null from a non-@Nullable component method");
        }
    }

    private static class com_mobiroller_AppComponent_getNetworkHelper implements Provider<NetworkHelper> {
        private final AppComponent appComponent;

        com_mobiroller_AppComponent_getNetworkHelper(AppComponent appComponent2) {
            this.appComponent = appComponent2;
        }

        public NetworkHelper get() {
            return (NetworkHelper) Preconditions.checkNotNull(this.appComponent.getNetworkHelper(), "Cannot return null from a non-@Nullable component method");
        }
    }

    private static class com_mobiroller_AppComponent_getScreenHelper implements Provider<ScreenHelper> {
        private final AppComponent appComponent;

        com_mobiroller_AppComponent_getScreenHelper(AppComponent appComponent2) {
            this.appComponent = appComponent2;
        }

        public ScreenHelper get() {
            return (ScreenHelper) Preconditions.checkNotNull(this.appComponent.getScreenHelper(), "Cannot return null from a non-@Nullable component method");
        }
    }

    private static class com_mobiroller_AppComponent_getSharedPref implements Provider<SharedPrefHelper> {
        private final AppComponent appComponent;

        com_mobiroller_AppComponent_getSharedPref(AppComponent appComponent2) {
            this.appComponent = appComponent2;
        }

        public SharedPrefHelper get() {
            return (SharedPrefHelper) Preconditions.checkNotNull(this.appComponent.getSharedPref(), "Cannot return null from a non-@Nullable component method");
        }
    }

    public AppCompatActivity inject(AppCompatActivity appCompatActivity) {
        return appCompatActivity;
    }

    public void inject(AuthorizationActivity authorizationActivity) {
    }

    public void inject(AveAddNoteActivity aveAddNoteActivity) {
    }

    public void inject(ImagePagerActivity imagePagerActivity) {
    }

    public void inject(aveFullScreenVideo avefullscreenvideo) {
    }

    public void inject(SlidingMenu slidingMenu) {
    }

    public void inject(aveNavigationActivity avenavigationactivity) {
    }

    private DaggerActivityComponent(ActivityModule activityModule, AppComponent appComponent2) {
        this.appComponent = appComponent2;
        initialize(activityModule, appComponent2);
    }

    public static Builder builder() {
        return new Builder();
    }

    private void initialize(ActivityModule activityModule, AppComponent appComponent2) {
        this.providesAppCompatActivityProvider = DoubleCheck.provider(ActivityModule_ProvidesAppCompatActivityFactory.create(activityModule));
        this.getNetworkHelperProvider = new com_mobiroller_AppComponent_getNetworkHelper(appComponent2);
        this.getJsonParserProvider = new com_mobiroller_AppComponent_getJsonParser(appComponent2);
        this.getSharedPrefProvider = new com_mobiroller_AppComponent_getSharedPref(appComponent2);
        this.getApiRequestManagerProvider = new com_mobiroller_AppComponent_getApiRequestManager(appComponent2);
        this.getLocalizationHelperProvider = new com_mobiroller_AppComponent_getLocalizationHelper(appComponent2);
        this.getApplicationProvider = new com_mobiroller_AppComponent_getApplication(appComponent2);
        this.getScreenHelperProvider = new com_mobiroller_AppComponent_getScreenHelper(appComponent2);
        this.providesMenuHelperProvider = DoubleCheck.provider(ActivityModule_ProvidesMenuHelperFactory.create(activityModule, this.providesAppCompatActivityProvider, this.getNetworkHelperProvider, this.getJsonParserProvider, this.getSharedPrefProvider, this.getApiRequestManagerProvider, this.getLocalizationHelperProvider, this.getApplicationProvider, this.getScreenHelperProvider));
        this.providesLayoutHelperProvider = DoubleCheck.provider(ActivityModule_ProvidesLayoutHelperFactory.create(activityModule, this.getSharedPrefProvider, this.providesAppCompatActivityProvider, this.getApiRequestManagerProvider));
    }

    public void inject(SplashApp splashApp) {
        injectSplashApp(splashApp);
    }

    public void inject(UserAddressActivity userAddressActivity) {
        injectUserAddressActivity(userAddressActivity);
    }

    public void inject(UserOrderActivity userOrderActivity) {
        injectUserOrderActivity(userOrderActivity);
    }

    public void inject(aveYoutubeAdvancedView aveyoutubeadvancedview) {
        injectaveYoutubeAdvancedView(aveyoutubeadvancedview);
    }

    public void inject(ChannelDetailActivity channelDetailActivity) {
        injectChannelDetailActivity(channelDetailActivity);
    }

    public void inject(SlidingPanelActivity slidingPanelActivity) {
        injectSlidingPanelActivity(slidingPanelActivity);
    }

    public void inject(PermissionRequiredActivity permissionRequiredActivity) {
        injectPermissionRequiredActivity(permissionRequiredActivity);
    }

    public void inject(FCMNotificationHandler fCMNotificationHandler) {
        injectFCMNotificationHandler(fCMNotificationHandler);
    }

    public void inject(UserProfileActivity userProfileActivity) {
        injectUserProfileActivity(userProfileActivity);
    }

    public void inject(ChatArchivedDialogActivity chatArchivedDialogActivity) {
        injectChatArchivedDialogActivity(chatArchivedDialogActivity);
    }

    public void inject(ChatActivity chatActivity) {
        injectChatActivity(chatActivity);
    }

    public void inject(GroupChatActivity groupChatActivity) {
        injectGroupChatActivity(groupChatActivity);
    }

    public void inject(ChatAdminActivity chatAdminActivity) {
        injectChatAdminActivity(chatAdminActivity);
    }

    public void inject(aveChatView avechatview) {
        injectaveChatView(avechatview);
    }

    public void inject(UserResetPasswordActivity userResetPasswordActivity) {
        injectUserResetPasswordActivity(userResetPasswordActivity);
    }

    public void inject(UserLoginActivity userLoginActivity) {
        injectUserLoginActivity(userLoginActivity);
    }

    public void inject(UserRegisterActivity userRegisterActivity) {
        injectUserRegisterActivity(userRegisterActivity);
    }

    public void inject(UserUpdateActivity userUpdateActivity) {
        injectUserUpdateActivity(userUpdateActivity);
    }

    public void inject(UserChangePasswordActivity userChangePasswordActivity) {
        injectUserChangePasswordActivity(userChangePasswordActivity);
    }

    public void inject(aveCallNowView avecallnowview) {
        injectaveCallNowView(avecallnowview);
    }

    public void inject(ConnectionRequired connectionRequired) {
        injectConnectionRequired(connectionRequired);
    }

    public void inject(GenericActivity genericActivity) {
        injectGenericActivity(genericActivity);
    }

    public void inject(ChatUserSearchResultActivity chatUserSearchResultActivity) {
        injectChatUserSearchResultActivity(chatUserSearchResultActivity);
    }

    public void inject(aveRssContentViewPager aversscontentviewpager) {
        injectaveRssContentViewPager(aversscontentviewpager);
    }

    public void inject(aveRSSView averssview) {
        injectaveRSSView(averssview);
    }

    public void inject(aveShareView aveshareview) {
        injectaveShareView(aveshareview);
    }

    public void inject(aveWebView avewebview) {
        injectaveWebView(avewebview);
    }

    public void inject(ListMenu listMenu) {
        injectListMenu(listMenu);
    }

    public void inject(MainActivity mainActivity) {
        injectMainActivity(mainActivity);
    }

    private SplashApp injectSplashApp(SplashApp splashApp) {
        String str = "Cannot return null from a non-@Nullable component method";
        SplashApp_MembersInjector.injectJParserNew(splashApp, (JSONParser) Preconditions.checkNotNull(this.appComponent.getJsonParser(), str));
        SplashApp_MembersInjector.injectImageManager(splashApp, (ImageManager) Preconditions.checkNotNull(this.appComponent.getImageManager(), str));
        SplashApp_MembersInjector.injectFileDownloader(splashApp, (FileDownloader) Preconditions.checkNotNull(this.appComponent.getFileDownloader(), str));
        SplashApp_MembersInjector.injectApiRequestManager(splashApp, (ApiRequestManager) Preconditions.checkNotNull(this.appComponent.getApiRequestManager(), str));
        SplashApp_MembersInjector.injectScreenHelper(splashApp, (ScreenHelper) Preconditions.checkNotNull(this.appComponent.getScreenHelper(), str));
        SplashApp_MembersInjector.injectApp(splashApp, (MobiRollerApplication) Preconditions.checkNotNull(this.appComponent.getApplication(), str));
        SplashApp_MembersInjector.injectMenuHelper(splashApp, (MenuHelper) this.providesMenuHelperProvider.get());
        return splashApp;
    }

    private UserAddressActivity injectUserAddressActivity(UserAddressActivity userAddressActivity) {
        String str = "Cannot return null from a non-@Nullable component method";
        UserAddressActivity_MembersInjector.injectSharedPrefHelper(userAddressActivity, (SharedPrefHelper) Preconditions.checkNotNull(this.appComponent.getSharedPref(), str));
        UserAddressActivity_MembersInjector.injectToolbarHelper(userAddressActivity, (ToolbarHelper) Preconditions.checkNotNull(this.appComponent.getToolbarHelper(), str));
        return userAddressActivity;
    }

    private UserOrderActivity injectUserOrderActivity(UserOrderActivity userOrderActivity) {
        String str = "Cannot return null from a non-@Nullable component method";
        UserOrderActivity_MembersInjector.injectSharedPrefHelper(userOrderActivity, (SharedPrefHelper) Preconditions.checkNotNull(this.appComponent.getSharedPref(), str));
        UserOrderActivity_MembersInjector.injectToolbarHelper(userOrderActivity, (ToolbarHelper) Preconditions.checkNotNull(this.appComponent.getToolbarHelper(), str));
        return userOrderActivity;
    }

    private aveYoutubeAdvancedView injectaveYoutubeAdvancedView(aveYoutubeAdvancedView aveyoutubeadvancedview) {
        String str = "Cannot return null from a non-@Nullable component method";
        aveYoutubeAdvancedView_MembersInjector.injectToolbarHelper(aveyoutubeadvancedview, (ToolbarHelper) Preconditions.checkNotNull(this.appComponent.getToolbarHelper(), str));
        aveYoutubeAdvancedView_MembersInjector.injectLocalizationHelper(aveyoutubeadvancedview, (LocalizationHelper) Preconditions.checkNotNull(this.appComponent.getLocalizationHelper(), str));
        return aveyoutubeadvancedview;
    }

    private ChannelDetailActivity injectChannelDetailActivity(ChannelDetailActivity channelDetailActivity) {
        String str = "Cannot return null from a non-@Nullable component method";
        ChannelDetailActivity_MembersInjector.injectSharedPrefHelper(channelDetailActivity, (SharedPrefHelper) Preconditions.checkNotNull(this.appComponent.getSharedPref(), str));
        ChannelDetailActivity_MembersInjector.injectApp(channelDetailActivity, (MobiRollerApplication) Preconditions.checkNotNull(this.appComponent.getApplication(), str));
        ChannelDetailActivity_MembersInjector.injectNetworkHelper(channelDetailActivity, (NetworkHelper) Preconditions.checkNotNull(this.appComponent.getNetworkHelper(), str));
        ChannelDetailActivity_MembersInjector.injectJsonParser(channelDetailActivity, (JSONParser) Preconditions.checkNotNull(this.appComponent.getJsonParser(), str));
        ChannelDetailActivity_MembersInjector.injectScreenHelper(channelDetailActivity, (ScreenHelper) Preconditions.checkNotNull(this.appComponent.getScreenHelper(), str));
        ChannelDetailActivity_MembersInjector.injectToolbarHelper(channelDetailActivity, (ToolbarHelper) Preconditions.checkNotNull(this.appComponent.getToolbarHelper(), str));
        return channelDetailActivity;
    }

    private SlidingPanelActivity injectSlidingPanelActivity(SlidingPanelActivity slidingPanelActivity) {
        String str = "Cannot return null from a non-@Nullable component method";
        SlidingPanelActivity_MembersInjector.injectSharedPrefHelper(slidingPanelActivity, (SharedPrefHelper) Preconditions.checkNotNull(this.appComponent.getSharedPref(), str));
        SlidingPanelActivity_MembersInjector.injectApp(slidingPanelActivity, (MobiRollerApplication) Preconditions.checkNotNull(this.appComponent.getApplication(), str));
        SlidingPanelActivity_MembersInjector.injectNetworkHelper(slidingPanelActivity, (NetworkHelper) Preconditions.checkNotNull(this.appComponent.getNetworkHelper(), str));
        SlidingPanelActivity_MembersInjector.injectJsonParser(slidingPanelActivity, (JSONParser) Preconditions.checkNotNull(this.appComponent.getJsonParser(), str));
        SlidingPanelActivity_MembersInjector.injectScreenHelper(slidingPanelActivity, (ScreenHelper) Preconditions.checkNotNull(this.appComponent.getScreenHelper(), str));
        SlidingPanelActivity_MembersInjector.injectToolbarHelper(slidingPanelActivity, (ToolbarHelper) Preconditions.checkNotNull(this.appComponent.getToolbarHelper(), str));
        SlidingPanelActivity_MembersInjector.injectLocalizationHelper(slidingPanelActivity, (LocalizationHelper) Preconditions.checkNotNull(this.appComponent.getLocalizationHelper(), str));
        SlidingPanelActivity_MembersInjector.injectMenuHelper(slidingPanelActivity, (MenuHelper) this.providesMenuHelperProvider.get());
        SlidingPanelActivity_MembersInjector.injectApiRequestManager(slidingPanelActivity, (ApiRequestManager) Preconditions.checkNotNull(this.appComponent.getApiRequestManager(), str));
        return slidingPanelActivity;
    }

    private PermissionRequiredActivity injectPermissionRequiredActivity(PermissionRequiredActivity permissionRequiredActivity) {
        String str = "Cannot return null from a non-@Nullable component method";
        PermissionRequiredActivity_MembersInjector.injectToolbarHelper(permissionRequiredActivity, (ToolbarHelper) Preconditions.checkNotNull(this.appComponent.getToolbarHelper(), str));
        PermissionRequiredActivity_MembersInjector.injectSharedPrefHelper(permissionRequiredActivity, (SharedPrefHelper) Preconditions.checkNotNull(this.appComponent.getSharedPref(), str));
        PermissionRequiredActivity_MembersInjector.injectScreenHelper(permissionRequiredActivity, (ScreenHelper) Preconditions.checkNotNull(this.appComponent.getScreenHelper(), str));
        return permissionRequiredActivity;
    }

    private FCMNotificationHandler injectFCMNotificationHandler(FCMNotificationHandler fCMNotificationHandler) {
        FCMNotificationHandler_MembersInjector.injectMenuHelper(fCMNotificationHandler, (MenuHelper) this.providesMenuHelperProvider.get());
        FCMNotificationHandler_MembersInjector.injectSharedPrefHelper(fCMNotificationHandler, (SharedPrefHelper) Preconditions.checkNotNull(this.appComponent.getSharedPref(), "Cannot return null from a non-@Nullable component method"));
        return fCMNotificationHandler;
    }

    private UserProfileActivity injectUserProfileActivity(UserProfileActivity userProfileActivity) {
        String str = "Cannot return null from a non-@Nullable component method";
        UserProfileActivity_MembersInjector.injectBannerHelper(userProfileActivity, (BannerHelper) Preconditions.checkNotNull(this.appComponent.getBannerHelper(), str));
        UserProfileActivity_MembersInjector.injectToolbarHelper(userProfileActivity, (ToolbarHelper) Preconditions.checkNotNull(this.appComponent.getToolbarHelper(), str));
        UserProfileActivity_MembersInjector.injectSharedPrefHelper(userProfileActivity, (SharedPrefHelper) Preconditions.checkNotNull(this.appComponent.getSharedPref(), str));
        return userProfileActivity;
    }

    private ChatArchivedDialogActivity injectChatArchivedDialogActivity(ChatArchivedDialogActivity chatArchivedDialogActivity) {
        String str = "Cannot return null from a non-@Nullable component method";
        ChatArchivedDialogActivity_MembersInjector.injectBannerHelper(chatArchivedDialogActivity, (BannerHelper) Preconditions.checkNotNull(this.appComponent.getBannerHelper(), str));
        ChatArchivedDialogActivity_MembersInjector.injectSharedPrefHelper(chatArchivedDialogActivity, (SharedPrefHelper) Preconditions.checkNotNull(this.appComponent.getSharedPref(), str));
        return chatArchivedDialogActivity;
    }

    private ChatActivity injectChatActivity(ChatActivity chatActivity) {
        String str = "Cannot return null from a non-@Nullable component method";
        ChatActivity_MembersInjector.injectSharedPrefHelper(chatActivity, (SharedPrefHelper) Preconditions.checkNotNull(this.appComponent.getSharedPref(), str));
        ChatActivity_MembersInjector.injectNetworkHelper(chatActivity, (NetworkHelper) Preconditions.checkNotNull(this.appComponent.getNetworkHelper(), str));
        ChatActivity_MembersInjector.injectToolbarHelper(chatActivity, (ToolbarHelper) Preconditions.checkNotNull(this.appComponent.getToolbarHelper(), str));
        return chatActivity;
    }

    private GroupChatActivity injectGroupChatActivity(GroupChatActivity groupChatActivity) {
        String str = "Cannot return null from a non-@Nullable component method";
        GroupChatActivity_MembersInjector.injectSharedPrefHelper(groupChatActivity, (SharedPrefHelper) Preconditions.checkNotNull(this.appComponent.getSharedPref(), str));
        GroupChatActivity_MembersInjector.injectNetworkHelper(groupChatActivity, (NetworkHelper) Preconditions.checkNotNull(this.appComponent.getNetworkHelper(), str));
        GroupChatActivity_MembersInjector.injectToolbarHelper(groupChatActivity, (ToolbarHelper) Preconditions.checkNotNull(this.appComponent.getToolbarHelper(), str));
        return groupChatActivity;
    }

    private ChatAdminActivity injectChatAdminActivity(ChatAdminActivity chatAdminActivity) {
        String str = "Cannot return null from a non-@Nullable component method";
        ChatAdminActivity_MembersInjector.injectApiRequestManager(chatAdminActivity, (ApiRequestManager) Preconditions.checkNotNull(this.appComponent.getApiRequestManager(), str));
        ChatAdminActivity_MembersInjector.injectSharedPrefHelper(chatAdminActivity, (SharedPrefHelper) Preconditions.checkNotNull(this.appComponent.getSharedPref(), str));
        return chatAdminActivity;
    }

    private aveChatView injectaveChatView(aveChatView avechatview) {
        String str = "Cannot return null from a non-@Nullable component method";
        aveChatView_MembersInjector.injectSharedPrefHelper(avechatview, (SharedPrefHelper) Preconditions.checkNotNull(this.appComponent.getSharedPref(), str));
        aveChatView_MembersInjector.injectToolbarHelper(avechatview, (ToolbarHelper) Preconditions.checkNotNull(this.appComponent.getToolbarHelper(), str));
        aveChatView_MembersInjector.injectLocalizationHelper(avechatview, (LocalizationHelper) Preconditions.checkNotNull(this.appComponent.getLocalizationHelper(), str));
        aveChatView_MembersInjector.injectJParserNew(avechatview, (JSONParser) Preconditions.checkNotNull(this.appComponent.getJsonParser(), str));
        aveChatView_MembersInjector.injectNetworkHelper(avechatview, (NetworkHelper) Preconditions.checkNotNull(this.appComponent.getNetworkHelper(), str));
        return avechatview;
    }

    private UserResetPasswordActivity injectUserResetPasswordActivity(UserResetPasswordActivity userResetPasswordActivity) {
        String str = "Cannot return null from a non-@Nullable component method";
        UserResetPasswordActivity_MembersInjector.injectSharedPrefHelper(userResetPasswordActivity, (SharedPrefHelper) Preconditions.checkNotNull(this.appComponent.getSharedPref(), str));
        UserResetPasswordActivity_MembersInjector.injectNetworkHelper(userResetPasswordActivity, (NetworkHelper) Preconditions.checkNotNull(this.appComponent.getNetworkHelper(), str));
        return userResetPasswordActivity;
    }

    private UserLoginActivity injectUserLoginActivity(UserLoginActivity userLoginActivity) {
        String str = "Cannot return null from a non-@Nullable component method";
        UserLoginActivity_MembersInjector.injectSharedPrefHelper(userLoginActivity, (SharedPrefHelper) Preconditions.checkNotNull(this.appComponent.getSharedPref(), str));
        UserLoginActivity_MembersInjector.injectNetworkHelper(userLoginActivity, (NetworkHelper) Preconditions.checkNotNull(this.appComponent.getNetworkHelper(), str));
        UserLoginActivity_MembersInjector.injectApp(userLoginActivity, (MobiRollerApplication) Preconditions.checkNotNull(this.appComponent.getApplication(), str));
        return userLoginActivity;
    }

    private UserRegisterActivity injectUserRegisterActivity(UserRegisterActivity userRegisterActivity) {
        String str = "Cannot return null from a non-@Nullable component method";
        UserRegisterActivity_MembersInjector.injectSharedPrefHelper(userRegisterActivity, (SharedPrefHelper) Preconditions.checkNotNull(this.appComponent.getSharedPref(), str));
        UserRegisterActivity_MembersInjector.injectNetworkHelper(userRegisterActivity, (NetworkHelper) Preconditions.checkNotNull(this.appComponent.getNetworkHelper(), str));
        UserRegisterActivity_MembersInjector.injectApp(userRegisterActivity, (MobiRollerApplication) Preconditions.checkNotNull(this.appComponent.getApplication(), str));
        UserRegisterActivity_MembersInjector.injectLocalizationHelper(userRegisterActivity, (LocalizationHelper) Preconditions.checkNotNull(this.appComponent.getLocalizationHelper(), str));
        return userRegisterActivity;
    }

    private UserUpdateActivity injectUserUpdateActivity(UserUpdateActivity userUpdateActivity) {
        String str = "Cannot return null from a non-@Nullable component method";
        UserUpdateActivity_MembersInjector.injectSharedPrefHelper(userUpdateActivity, (SharedPrefHelper) Preconditions.checkNotNull(this.appComponent.getSharedPref(), str));
        UserUpdateActivity_MembersInjector.injectLocalizationHelper(userUpdateActivity, (LocalizationHelper) Preconditions.checkNotNull(this.appComponent.getLocalizationHelper(), str));
        UserUpdateActivity_MembersInjector.injectToolbarHelper(userUpdateActivity, (ToolbarHelper) Preconditions.checkNotNull(this.appComponent.getToolbarHelper(), str));
        UserUpdateActivity_MembersInjector.injectNetworkHelper(userUpdateActivity, (NetworkHelper) Preconditions.checkNotNull(this.appComponent.getNetworkHelper(), str));
        return userUpdateActivity;
    }

    private UserChangePasswordActivity injectUserChangePasswordActivity(UserChangePasswordActivity userChangePasswordActivity) {
        String str = "Cannot return null from a non-@Nullable component method";
        UserChangePasswordActivity_MembersInjector.injectSharedPrefHelper(userChangePasswordActivity, (SharedPrefHelper) Preconditions.checkNotNull(this.appComponent.getSharedPref(), str));
        UserChangePasswordActivity_MembersInjector.injectNetworkHelper(userChangePasswordActivity, (NetworkHelper) Preconditions.checkNotNull(this.appComponent.getNetworkHelper(), str));
        UserChangePasswordActivity_MembersInjector.injectToolbarHelper(userChangePasswordActivity, (ToolbarHelper) Preconditions.checkNotNull(this.appComponent.getToolbarHelper(), str));
        return userChangePasswordActivity;
    }

    private aveCallNowView injectaveCallNowView(aveCallNowView avecallnowview) {
        String str = "Cannot return null from a non-@Nullable component method";
        aveCallNowView_MembersInjector.injectToolbarHelper(avecallnowview, (ToolbarHelper) Preconditions.checkNotNull(this.appComponent.getToolbarHelper(), str));
        aveCallNowView_MembersInjector.injectLocalizationHelper(avecallnowview, (LocalizationHelper) Preconditions.checkNotNull(this.appComponent.getLocalizationHelper(), str));
        return avecallnowview;
    }

    private ConnectionRequired injectConnectionRequired(ConnectionRequired connectionRequired) {
        String str = "Cannot return null from a non-@Nullable component method";
        ConnectionRequired_MembersInjector.injectNetworkHelper(connectionRequired, (NetworkHelper) Preconditions.checkNotNull(this.appComponent.getNetworkHelper(), str));
        ConnectionRequired_MembersInjector.injectJsonParser(connectionRequired, (JSONParser) Preconditions.checkNotNull(this.appComponent.getJsonParser(), str));
        ConnectionRequired_MembersInjector.injectToolbarHelper(connectionRequired, (ToolbarHelper) Preconditions.checkNotNull(this.appComponent.getToolbarHelper(), str));
        ConnectionRequired_MembersInjector.injectSharedPrefHelper(connectionRequired, (SharedPrefHelper) Preconditions.checkNotNull(this.appComponent.getSharedPref(), str));
        ConnectionRequired_MembersInjector.injectScreenHelper(connectionRequired, (ScreenHelper) Preconditions.checkNotNull(this.appComponent.getScreenHelper(), str));
        return connectionRequired;
    }

    private GenericActivity injectGenericActivity(GenericActivity genericActivity) {
        String str = "Cannot return null from a non-@Nullable component method";
        GenericActivity_MembersInjector.injectToolbarHelper(genericActivity, (ToolbarHelper) Preconditions.checkNotNull(this.appComponent.getToolbarHelper(), str));
        GenericActivity_MembersInjector.injectLocalizationHelper(genericActivity, (LocalizationHelper) Preconditions.checkNotNull(this.appComponent.getLocalizationHelper(), str));
        return genericActivity;
    }

    private ChatUserSearchResultActivity injectChatUserSearchResultActivity(ChatUserSearchResultActivity chatUserSearchResultActivity) {
        ChatUserSearchResultActivity_MembersInjector.injectBannerHelper(chatUserSearchResultActivity, (BannerHelper) Preconditions.checkNotNull(this.appComponent.getBannerHelper(), "Cannot return null from a non-@Nullable component method"));
        return chatUserSearchResultActivity;
    }

    private aveRssContentViewPager injectaveRssContentViewPager(aveRssContentViewPager aversscontentviewpager) {
        String str = "Cannot return null from a non-@Nullable component method";
        aveRssContentViewPager_MembersInjector.injectNetworkHelper(aversscontentviewpager, (NetworkHelper) Preconditions.checkNotNull(this.appComponent.getNetworkHelper(), str));
        aveRssContentViewPager_MembersInjector.injectSharedPrefHelper(aversscontentviewpager, (SharedPrefHelper) Preconditions.checkNotNull(this.appComponent.getSharedPref(), str));
        aveRssContentViewPager_MembersInjector.injectToolbarHelper(aversscontentviewpager, (ToolbarHelper) Preconditions.checkNotNull(this.appComponent.getToolbarHelper(), str));
        return aversscontentviewpager;
    }

    private aveRSSView injectaveRSSView(aveRSSView averssview) {
        String str = "Cannot return null from a non-@Nullable component method";
        aveRSSView_MembersInjector.injectToolbarHelper(averssview, (ToolbarHelper) Preconditions.checkNotNull(this.appComponent.getToolbarHelper(), str));
        aveRSSView_MembersInjector.injectLocalizationHelper(averssview, (LocalizationHelper) Preconditions.checkNotNull(this.appComponent.getLocalizationHelper(), str));
        return averssview;
    }

    private aveShareView injectaveShareView(aveShareView aveshareview) {
        String str = "Cannot return null from a non-@Nullable component method";
        aveShareView_MembersInjector.injectToolbarHelper(aveshareview, (ToolbarHelper) Preconditions.checkNotNull(this.appComponent.getToolbarHelper(), str));
        aveShareView_MembersInjector.injectLocalizationHelper(aveshareview, (LocalizationHelper) Preconditions.checkNotNull(this.appComponent.getLocalizationHelper(), str));
        return aveshareview;
    }

    private aveWebView injectaveWebView(aveWebView avewebview) {
        String str = "Cannot return null from a non-@Nullable component method";
        aveWebView_MembersInjector.injectToolbarHelper(avewebview, (ToolbarHelper) Preconditions.checkNotNull(this.appComponent.getToolbarHelper(), str));
        aveWebView_MembersInjector.injectLocalizationHelper(avewebview, (LocalizationHelper) Preconditions.checkNotNull(this.appComponent.getLocalizationHelper(), str));
        return avewebview;
    }

    private ListMenu injectListMenu(ListMenu listMenu) {
        String str = "Cannot return null from a non-@Nullable component method";
        ListMenu_MembersInjector.injectSharedPrefHelper(listMenu, (SharedPrefHelper) Preconditions.checkNotNull(this.appComponent.getSharedPref(), str));
        ListMenu_MembersInjector.injectApp(listMenu, (MobiRollerApplication) Preconditions.checkNotNull(this.appComponent.getApplication(), str));
        ListMenu_MembersInjector.injectNetworkHelper(listMenu, (NetworkHelper) Preconditions.checkNotNull(this.appComponent.getNetworkHelper(), str));
        ListMenu_MembersInjector.injectJsonParser(listMenu, (JSONParser) Preconditions.checkNotNull(this.appComponent.getJsonParser(), str));
        ListMenu_MembersInjector.injectBannerHelper(listMenu, (BannerHelper) Preconditions.checkNotNull(this.appComponent.getBannerHelper(), str));
        ListMenu_MembersInjector.injectLayoutHelper(listMenu, (LayoutHelper) this.providesLayoutHelperProvider.get());
        ListMenu_MembersInjector.injectScreenHelper(listMenu, (ScreenHelper) Preconditions.checkNotNull(this.appComponent.getScreenHelper(), str));
        ListMenu_MembersInjector.injectImageManager(listMenu, (ImageManager) Preconditions.checkNotNull(this.appComponent.getImageManager(), str));
        ListMenu_MembersInjector.injectToolbarHelper(listMenu, (ToolbarHelper) Preconditions.checkNotNull(this.appComponent.getToolbarHelper(), str));
        ListMenu_MembersInjector.injectLocalizationHelper(listMenu, (LocalizationHelper) Preconditions.checkNotNull(this.appComponent.getLocalizationHelper(), str));
        ListMenu_MembersInjector.injectMenuHelper(listMenu, (MenuHelper) this.providesMenuHelperProvider.get());
        return listMenu;
    }

    private MainActivity injectMainActivity(MainActivity mainActivity) {
        String str = "Cannot return null from a non-@Nullable component method";
        MainActivity_MembersInjector.injectSharedPrefHelper(mainActivity, (SharedPrefHelper) Preconditions.checkNotNull(this.appComponent.getSharedPref(), str));
        MainActivity_MembersInjector.injectApp(mainActivity, (MobiRollerApplication) Preconditions.checkNotNull(this.appComponent.getApplication(), str));
        MainActivity_MembersInjector.injectNetworkHelper(mainActivity, (NetworkHelper) Preconditions.checkNotNull(this.appComponent.getNetworkHelper(), str));
        MainActivity_MembersInjector.injectJsonParser(mainActivity, (JSONParser) Preconditions.checkNotNull(this.appComponent.getJsonParser(), str));
        MainActivity_MembersInjector.injectBannerHelper(mainActivity, (BannerHelper) Preconditions.checkNotNull(this.appComponent.getBannerHelper(), str));
        MainActivity_MembersInjector.injectScreenHelper(mainActivity, (ScreenHelper) Preconditions.checkNotNull(this.appComponent.getScreenHelper(), str));
        MainActivity_MembersInjector.injectImageManager(mainActivity, (ImageManager) Preconditions.checkNotNull(this.appComponent.getImageManager(), str));
        MainActivity_MembersInjector.injectToolbarHelper(mainActivity, (ToolbarHelper) Preconditions.checkNotNull(this.appComponent.getToolbarHelper(), str));
        MainActivity_MembersInjector.injectLocalizationHelper(mainActivity, (LocalizationHelper) Preconditions.checkNotNull(this.appComponent.getLocalizationHelper(), str));
        MainActivity_MembersInjector.injectMenuHelper(mainActivity, (MenuHelper) this.providesMenuHelperProvider.get());
        MainActivity_MembersInjector.injectApiRequestManager(mainActivity, (ApiRequestManager) Preconditions.checkNotNull(this.appComponent.getApiRequestManager(), str));
        return mainActivity;
    }
}
