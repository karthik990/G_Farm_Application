package com.mobiroller.interfaces;

import android.app.Activity;
import androidx.fragment.app.Fragment;
import com.mobiroller.AppComponent;
import com.mobiroller.MobiRollerApplication;
import com.mobiroller.fragments.BaseModuleFragment;
import com.mobiroller.fragments.BaseModuleFragment_MembersInjector;
import com.mobiroller.fragments.UserLoginFragment;
import com.mobiroller.fragments.UserLoginFragment_MembersInjector;
import com.mobiroller.fragments.chat.ChatMessageListFragment;
import com.mobiroller.fragments.chat.ChatMessageListFragment_MembersInjector;
import com.mobiroller.fragments.chat.ChatUserListFragment;
import com.mobiroller.fragments.chat.ChatUserListFragment_MembersInjector;
import com.mobiroller.fragments.preview.NotSupportedFragment;
import com.mobiroller.fragments.user.UserAddressFragment;
import com.mobiroller.fragments.user.UserAddressFragment_MembersInjector;
import com.mobiroller.fragments.youtubeadvanced.YoutubePlaylistFragment;
import com.mobiroller.fragments.youtubeadvanced.YoutubePlaylistFragment_MembersInjector;
import com.mobiroller.fragments.youtubeadvanced.YoutubeVideosFragment;
import com.mobiroller.fragments.youtubeadvanced.YoutubeVideosFragment_MembersInjector;
import com.mobiroller.helpers.ApiRequestManager;
import com.mobiroller.helpers.BannerHelper;
import com.mobiroller.helpers.ComponentHelper;
import com.mobiroller.helpers.JSONParser;
import com.mobiroller.helpers.LayoutHelper;
import com.mobiroller.helpers.LocalizationHelper;
import com.mobiroller.helpers.MenuHelper;
import com.mobiroller.helpers.NetworkHelper;
import com.mobiroller.helpers.ScreenHelper;
import com.mobiroller.helpers.SharedPrefHelper;
import com.mobiroller.module.FragmentModule;
import com.mobiroller.module.FragmentModule_ProvidesAppCompatActivityFactory;
import com.mobiroller.module.FragmentModule_ProvidesLayoutHelperFactory;
import com.mobiroller.module.FragmentModule_ProvidesMenuHelperFactory;
import com.mobiroller.util.ImageManager;
import dagger.internal.DoubleCheck;
import dagger.internal.Preconditions;
import javax.inject.Provider;

public final class DaggerFragmentComponent implements FragmentComponent {
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
        private AppComponent appComponent;
        private FragmentModule fragmentModule;

        private Builder() {
        }

        public Builder fragmentModule(FragmentModule fragmentModule2) {
            this.fragmentModule = (FragmentModule) Preconditions.checkNotNull(fragmentModule2);
            return this;
        }

        public Builder appComponent(AppComponent appComponent2) {
            this.appComponent = (AppComponent) Preconditions.checkNotNull(appComponent2);
            return this;
        }

        public FragmentComponent build() {
            Preconditions.checkBuilderRequirement(this.fragmentModule, FragmentModule.class);
            Preconditions.checkBuilderRequirement(this.appComponent, AppComponent.class);
            return new DaggerFragmentComponent(this.fragmentModule, this.appComponent);
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

    public Fragment inject(Fragment fragment) {
        return fragment;
    }

    public void inject(NotSupportedFragment notSupportedFragment) {
    }

    private DaggerFragmentComponent(FragmentModule fragmentModule, AppComponent appComponent2) {
        this.appComponent = appComponent2;
        initialize(fragmentModule, appComponent2);
    }

    public static Builder builder() {
        return new Builder();
    }

    private void initialize(FragmentModule fragmentModule, AppComponent appComponent2) {
        this.getSharedPrefProvider = new com_mobiroller_AppComponent_getSharedPref(appComponent2);
        this.providesAppCompatActivityProvider = DoubleCheck.provider(FragmentModule_ProvidesAppCompatActivityFactory.create(fragmentModule));
        this.getApiRequestManagerProvider = new com_mobiroller_AppComponent_getApiRequestManager(appComponent2);
        this.providesLayoutHelperProvider = DoubleCheck.provider(FragmentModule_ProvidesLayoutHelperFactory.create(fragmentModule, this.getSharedPrefProvider, this.providesAppCompatActivityProvider, this.getApiRequestManagerProvider));
        this.getNetworkHelperProvider = new com_mobiroller_AppComponent_getNetworkHelper(appComponent2);
        this.getJsonParserProvider = new com_mobiroller_AppComponent_getJsonParser(appComponent2);
        this.getLocalizationHelperProvider = new com_mobiroller_AppComponent_getLocalizationHelper(appComponent2);
        this.getApplicationProvider = new com_mobiroller_AppComponent_getApplication(appComponent2);
        this.getScreenHelperProvider = new com_mobiroller_AppComponent_getScreenHelper(appComponent2);
        this.providesMenuHelperProvider = DoubleCheck.provider(FragmentModule_ProvidesMenuHelperFactory.create(fragmentModule, this.providesAppCompatActivityProvider, this.getNetworkHelperProvider, this.getJsonParserProvider, this.getSharedPrefProvider, this.getApiRequestManagerProvider, this.getLocalizationHelperProvider, this.getApplicationProvider, this.getScreenHelperProvider));
    }

    public void inject(UserAddressFragment userAddressFragment) {
        injectUserAddressFragment(userAddressFragment);
    }

    public void inject(YoutubeVideosFragment youtubeVideosFragment) {
        injectYoutubeVideosFragment(youtubeVideosFragment);
    }

    public void inject(YoutubePlaylistFragment youtubePlaylistFragment) {
        injectYoutubePlaylistFragment(youtubePlaylistFragment);
    }

    public void inject(UserLoginFragment userLoginFragment) {
        injectUserLoginFragment(userLoginFragment);
    }

    public void inject(BaseModuleFragment baseModuleFragment) {
        injectBaseModuleFragment(baseModuleFragment);
    }

    public void inject(ChatMessageListFragment chatMessageListFragment) {
        injectChatMessageListFragment(chatMessageListFragment);
    }

    public void inject(ChatUserListFragment chatUserListFragment) {
        injectChatUserListFragment(chatUserListFragment);
    }

    private UserAddressFragment injectUserAddressFragment(UserAddressFragment userAddressFragment) {
        UserAddressFragment_MembersInjector.injectSharedPrefHelper(userAddressFragment, (SharedPrefHelper) Preconditions.checkNotNull(this.appComponent.getSharedPref(), "Cannot return null from a non-@Nullable component method"));
        return userAddressFragment;
    }

    private YoutubeVideosFragment injectYoutubeVideosFragment(YoutubeVideosFragment youtubeVideosFragment) {
        YoutubeVideosFragment_MembersInjector.injectSharedPrefHelper(youtubeVideosFragment, (SharedPrefHelper) Preconditions.checkNotNull(this.appComponent.getSharedPref(), "Cannot return null from a non-@Nullable component method"));
        return youtubeVideosFragment;
    }

    private YoutubePlaylistFragment injectYoutubePlaylistFragment(YoutubePlaylistFragment youtubePlaylistFragment) {
        YoutubePlaylistFragment_MembersInjector.injectSharedPrefHelper(youtubePlaylistFragment, (SharedPrefHelper) Preconditions.checkNotNull(this.appComponent.getSharedPref(), "Cannot return null from a non-@Nullable component method"));
        return youtubePlaylistFragment;
    }

    private UserLoginFragment injectUserLoginFragment(UserLoginFragment userLoginFragment) {
        String str = "Cannot return null from a non-@Nullable component method";
        UserLoginFragment_MembersInjector.injectSharedPrefHelper(userLoginFragment, (SharedPrefHelper) Preconditions.checkNotNull(this.appComponent.getSharedPref(), str));
        UserLoginFragment_MembersInjector.injectNetworkHelper(userLoginFragment, (NetworkHelper) Preconditions.checkNotNull(this.appComponent.getNetworkHelper(), str));
        UserLoginFragment_MembersInjector.injectApp(userLoginFragment, (MobiRollerApplication) Preconditions.checkNotNull(this.appComponent.getApplication(), str));
        return userLoginFragment;
    }

    private BaseModuleFragment injectBaseModuleFragment(BaseModuleFragment baseModuleFragment) {
        String str = "Cannot return null from a non-@Nullable component method";
        BaseModuleFragment_MembersInjector.injectBannerHelper(baseModuleFragment, (BannerHelper) Preconditions.checkNotNull(this.appComponent.getBannerHelper(), str));
        BaseModuleFragment_MembersInjector.injectLayoutHelper(baseModuleFragment, (LayoutHelper) this.providesLayoutHelperProvider.get());
        BaseModuleFragment_MembersInjector.injectApp(baseModuleFragment, (MobiRollerApplication) Preconditions.checkNotNull(this.appComponent.getApplication(), str));
        BaseModuleFragment_MembersInjector.injectScreenHelper(baseModuleFragment, (ScreenHelper) Preconditions.checkNotNull(this.appComponent.getScreenHelper(), str));
        BaseModuleFragment_MembersInjector.injectApplication(baseModuleFragment, (MobiRollerApplication) Preconditions.checkNotNull(this.appComponent.getApplication(), str));
        BaseModuleFragment_MembersInjector.injectMSharedPrf(baseModuleFragment, (SharedPrefHelper) Preconditions.checkNotNull(this.appComponent.getSharedPref(), str));
        BaseModuleFragment_MembersInjector.injectMenuHelper(baseModuleFragment, (MenuHelper) this.providesMenuHelperProvider.get());
        BaseModuleFragment_MembersInjector.injectImageManager(baseModuleFragment, (ImageManager) Preconditions.checkNotNull(this.appComponent.getImageManager(), str));
        BaseModuleFragment_MembersInjector.injectJsonParser(baseModuleFragment, (JSONParser) Preconditions.checkNotNull(this.appComponent.getJsonParser(), str));
        BaseModuleFragment_MembersInjector.injectComponentHelper(baseModuleFragment, (ComponentHelper) Preconditions.checkNotNull(this.appComponent.getComponentHelper(), str));
        return baseModuleFragment;
    }

    private ChatMessageListFragment injectChatMessageListFragment(ChatMessageListFragment chatMessageListFragment) {
        ChatMessageListFragment_MembersInjector.injectBannerHelper(chatMessageListFragment, (BannerHelper) Preconditions.checkNotNull(this.appComponent.getBannerHelper(), "Cannot return null from a non-@Nullable component method"));
        return chatMessageListFragment;
    }

    private ChatUserListFragment injectChatUserListFragment(ChatUserListFragment chatUserListFragment) {
        ChatUserListFragment_MembersInjector.injectBannerHelper(chatUserListFragment, (BannerHelper) Preconditions.checkNotNull(this.appComponent.getBannerHelper(), "Cannot return null from a non-@Nullable component method"));
        return chatUserListFragment;
    }
}
