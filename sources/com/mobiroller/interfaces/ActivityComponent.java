package com.mobiroller.interfaces;

import androidx.appcompat.app.AppCompatActivity;
import com.mobiroller.AppComponent;
import com.mobiroller.activities.AuthorizationActivity;
import com.mobiroller.activities.AveAddNoteActivity;
import com.mobiroller.activities.ConnectionRequired;
import com.mobiroller.activities.GenericActivity;
import com.mobiroller.activities.ImagePagerActivity;
import com.mobiroller.activities.PermissionRequiredActivity;
import com.mobiroller.activities.SplashApp;
import com.mobiroller.activities.aveCallNowView;
import com.mobiroller.activities.aveChatView;
import com.mobiroller.activities.aveFullScreenVideo;
import com.mobiroller.activities.aveRSSView;
import com.mobiroller.activities.aveRssContentViewPager;
import com.mobiroller.activities.aveShareView;
import com.mobiroller.activities.aveWebView;
import com.mobiroller.activities.aveYoutubeAdvancedView;
import com.mobiroller.activities.chat.ChatActivity;
import com.mobiroller.activities.chat.ChatAdminActivity;
import com.mobiroller.activities.chat.ChatArchivedDialogActivity;
import com.mobiroller.activities.chat.ChatUserSearchResultActivity;
import com.mobiroller.activities.chat.GroupChatActivity;
import com.mobiroller.activities.menu.ListMenu;
import com.mobiroller.activities.menu.MainActivity;
import com.mobiroller.activities.menu.SlidingMenu;
import com.mobiroller.activities.menu.SlidingPanelActivity;
import com.mobiroller.activities.menu.aveNavigationActivity;
import com.mobiroller.activities.user.UserAddressActivity;
import com.mobiroller.activities.user.UserChangePasswordActivity;
import com.mobiroller.activities.user.UserLoginActivity;
import com.mobiroller.activities.user.UserOrderActivity;
import com.mobiroller.activities.user.UserProfileActivity;
import com.mobiroller.activities.user.UserRegisterActivity;
import com.mobiroller.activities.user.UserResetPasswordActivity;
import com.mobiroller.activities.user.UserUpdateActivity;
import com.mobiroller.activities.youtubeadvanced.ChannelDetailActivity;
import com.mobiroller.module.ActivityModule;
import com.mobiroller.scopes.PerActivity;
import com.mobiroller.services.FCMNotificationHandler;
import dagger.Component;

@PerActivity
@Component(dependencies = {AppComponent.class}, modules = {ActivityModule.class})
public interface ActivityComponent {
    AppCompatActivity inject(AppCompatActivity appCompatActivity);

    void inject(AuthorizationActivity authorizationActivity);

    void inject(AveAddNoteActivity aveAddNoteActivity);

    void inject(ConnectionRequired connectionRequired);

    void inject(GenericActivity genericActivity);

    void inject(ImagePagerActivity imagePagerActivity);

    void inject(PermissionRequiredActivity permissionRequiredActivity);

    void inject(SplashApp splashApp);

    void inject(aveCallNowView avecallnowview);

    void inject(aveChatView avechatview);

    void inject(aveFullScreenVideo avefullscreenvideo);

    void inject(aveRSSView averssview);

    void inject(aveRssContentViewPager aversscontentviewpager);

    void inject(aveShareView aveshareview);

    void inject(aveWebView avewebview);

    void inject(aveYoutubeAdvancedView aveyoutubeadvancedview);

    void inject(ChatActivity chatActivity);

    void inject(ChatAdminActivity chatAdminActivity);

    void inject(ChatArchivedDialogActivity chatArchivedDialogActivity);

    void inject(ChatUserSearchResultActivity chatUserSearchResultActivity);

    void inject(GroupChatActivity groupChatActivity);

    void inject(ListMenu listMenu);

    void inject(MainActivity mainActivity);

    void inject(SlidingMenu slidingMenu);

    void inject(SlidingPanelActivity slidingPanelActivity);

    void inject(aveNavigationActivity avenavigationactivity);

    void inject(UserAddressActivity userAddressActivity);

    void inject(UserChangePasswordActivity userChangePasswordActivity);

    void inject(UserLoginActivity userLoginActivity);

    void inject(UserOrderActivity userOrderActivity);

    void inject(UserProfileActivity userProfileActivity);

    void inject(UserRegisterActivity userRegisterActivity);

    void inject(UserResetPasswordActivity userResetPasswordActivity);

    void inject(UserUpdateActivity userUpdateActivity);

    void inject(ChannelDetailActivity channelDetailActivity);

    void inject(FCMNotificationHandler fCMNotificationHandler);
}
