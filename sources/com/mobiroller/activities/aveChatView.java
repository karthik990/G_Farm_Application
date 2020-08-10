package com.mobiroller.activities;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.res.ColorStateList;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.MarginLayoutParams;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.ViewCompat;
import androidx.fragment.app.FragmentManager;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.afollestad.materialdialogs.MaterialDialog.Builder;
import com.google.android.gms.ads.AdSize;
import com.google.firebase.auth.FirebaseAuth;
import com.mobiroller.DynamicConstants;
import com.mobiroller.MobiRollerApplication;
import com.mobiroller.activities.base.AveActivity;
import com.mobiroller.activities.chat.ChatActivity;
import com.mobiroller.activities.chat.ChatAdminActivity;
import com.mobiroller.activities.chat.ChatUserListActivity;
import com.mobiroller.activities.chat.CreateGroupChat;
import com.mobiroller.activities.user.UserLoginActivity;
import com.mobiroller.activities.user.UserProfileActivity;
import com.mobiroller.activities.user.UserUpdateActivity;
import com.mobiroller.constants.ChatConstants;
import com.mobiroller.constants.Constants;
import com.mobiroller.fragments.RequiresMembershipFragment;
import com.mobiroller.fragments.chat.ChatMessageListFragment;
import com.mobiroller.helpers.BannerHelper;
import com.mobiroller.helpers.FirebaseChatHelper;
import com.mobiroller.helpers.JSONParser;
import com.mobiroller.helpers.JSONStorage;
import com.mobiroller.helpers.LocalizationHelper;
import com.mobiroller.helpers.NetworkHelper;
import com.mobiroller.helpers.SharedPrefHelper;
import com.mobiroller.helpers.ToolbarHelper;
import com.mobiroller.helpers.UserHelper;
import com.mobiroller.interfaces.ActivityComponent;
import com.mobiroller.mobi942763453128.R;
import com.mobiroller.models.chat.ChatNotificationModel;
import com.mobiroller.models.chat.ChatUserDetails;
import com.mobiroller.models.events.ChatAccountEvent;
import com.mobiroller.models.events.NewDetailsEvent;
import com.mobiroller.util.ColorUtil;
import com.mobiroller.util.DialogUtil;
import com.mobiroller.util.DialogUtil.DialogCallBack;
import com.mobiroller.util.InterstitialAdsUtil;
import com.mobiroller.util.RssUtil;
import com.mobiroller.views.custom.MobirollerFloatingActionButton;
import com.mobiroller.views.custom.MobirollerToolbar;
import com.mobiroller.views.theme.Theme;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Map.Entry;
import java.util.Set;
import javax.inject.Inject;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

public class aveChatView extends AveActivity {
    @BindView(2131362372)
    MobirollerFloatingActionButton fabButton;
    private FirebaseChatHelper firebaseChatHelper;
    @BindView(2131362142)
    FrameLayout frameContainer;
    InterstitialAdsUtil interstitialAdsUtil;
    private boolean isNotification;
    @Inject
    JSONParser jParserNew;
    @Inject
    LocalizationHelper localizationHelper;
    @BindView(2131362649)
    RelativeLayout mainLayout;
    @Inject
    NetworkHelper networkHelper;
    @BindView(2131362873)
    RelativeLayout overlayLayout;
    @Inject
    SharedPrefHelper sharedPrefHelper;
    @Inject
    ToolbarHelper toolbarHelper;
    private ChatUserDetails userModel;

    /* access modifiers changed from: protected */
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView((int) R.layout.activity_chat);
        ButterKnife.bind((Activity) this);
        setMobirollerToolbar((MobirollerToolbar) findViewById(R.id.toolbar_top));
        new ToolbarHelper().setStatusBar(this);
        this.interstitialAdsUtil = new InterstitialAdsUtil((AppCompatActivity) this);
        this.isNotification = getIntent().hasExtra(ChatConstants.ARG_NOTIFICATION_MODEL);
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }
        loadUI();
    }

    private void loadUI() {
        Intent intent = getIntent();
        String str = Constants.KEY_SCREEN_ID;
        if (intent.hasExtra(str)) {
            ChatConstants.setChatScreenId(getIntent().getStringExtra(str));
        } else {
            ChatConstants.setChatScreenId(null);
        }
        if (this.isNotification) {
            ChatConstants.setChatScreenId(((ChatNotificationModel) getIntent().getSerializableExtra(ChatConstants.ARG_NOTIFICATION_MODEL)).getScreenId());
        }
        this.fabButton.setBackgroundTintList(ColorStateList.valueOf(ColorUtil.isColorDark(Theme.primaryColor) ? Theme.primaryColor : ViewCompat.MEASURED_STATE_MASK));
        checkStats();
        if (DynamicConstants.MobiRoller_Stage) {
            DialogUtil.showDialogWithCallBack(this, getString(R.string.not_supported_on_preview), new DialogCallBack() {
                public void onClickPositive() {
                    aveChatView.this.finish();
                }
            });
        } else if (!this.sharedPrefHelper.getIsChatVersionSupported()) {
            DialogUtil.showDialogWithCallBack(this, getString(R.string.chat_force_update), new DialogCallBack() {
                public void onClickPositive() {
                    aveChatView.this.finish();
                }
            });
        } else {
            String str2 = "title";
            if (JSONStorage.containsScreen(this.screenId)) {
                this.screenModel = JSONStorage.getScreenModel(this.screenId);
                setToolbarTitle(this, this.localizationHelper.getLocalizedTitle(this.screenModel.getTitle()));
                Editor edit = this.sharedPrefHelper.getSharedPrefs(this).edit();
                StringBuilder sb = new StringBuilder();
                sb.append(ChatConstants.chatScreenId);
                sb.append(str2);
                edit.putString(sb.toString(), this.screenModel.getTitle()).apply();
            } else {
                SharedPreferences sharedPrefs = this.sharedPrefHelper.getSharedPrefs(this);
                StringBuilder sb2 = new StringBuilder();
                sb2.append(ChatConstants.chatScreenId);
                sb2.append(str2);
                if (sharedPrefs.contains(sb2.toString())) {
                    LocalizationHelper localizationHelper2 = this.localizationHelper;
                    SharedPreferences sharedPrefs2 = this.sharedPrefHelper.getSharedPrefs(this);
                    StringBuilder sb3 = new StringBuilder();
                    sb3.append(ChatConstants.chatScreenId);
                    sb3.append(str2);
                    setToolbarTitle(this, localizationHelper2.getLocalizedTitle(sharedPrefs2.getString(sb3.toString(), getString(R.string.app_name))));
                } else {
                    setToolbarTitle(this, getString(R.string.app_name));
                }
            }
            if (!this.sharedPrefHelper.getChatValidated() || !this.sharedPrefHelper.getUserLoginStatus() || !this.sharedPrefHelper.getUserIsAvailableForChat() || FirebaseAuth.getInstance().getCurrentUser() == null) {
                this.fabButton.setVisibility(8);
                this.fabButton.setClickable(false);
                if (!this.sharedPrefHelper.getChatValidated()) {
                    DialogUtil.showDialog(this, getString(R.string.firebase_validation_failed));
                    getSupportFragmentManager().beginTransaction().replace(R.id.chat_container, new RequiresMembershipFragment()).commit();
                } else if (!this.sharedPrefHelper.getUserLoginStatus()) {
                    DialogUtil.showDialogWithCallBack(this, getString(R.string.requires_membership), new DialogCallBack() {
                        public void onClickPositive() {
                            aveChatView avechatview = aveChatView.this;
                            avechatview.startActivity(new Intent(avechatview, UserLoginActivity.class));
                            aveChatView.this.finish();
                        }
                    });
                } else {
                    DialogUtil.showDialogWithCallBack(this, getString(R.string.chat_action_invalid_account), new DialogCallBack() {
                        public void onClickPositive() {
                            aveChatView.this.finish();
                        }
                    });
                }
            } else {
                initChatSettings();
            }
            String str3 = "roles";
            if (getIntent().hasExtra(str3)) {
                ArrayList stringArrayListExtra = getIntent().getStringArrayListExtra(str3);
                HashSet hashSet = new HashSet();
                for (int i = 0; i < stringArrayListExtra.size(); i++) {
                    hashSet.add(stringArrayListExtra.get(i));
                }
                this.sharedPrefHelper.getSharedPrefs(this).edit().putStringSet(ChatConstants.chatScreenId, hashSet).apply();
            } else if (!this.sharedPrefHelper.getSharedPrefs(this).contains(ChatConstants.chatScreenId)) {
                this.fabButton.setVisibility(8);
            }
        }
    }

    private void initChatSettings() {
        boolean z = this.isNotification;
        String str = Constants.KEY_SCREEN_ID;
        if (z) {
            ChatNotificationModel chatNotificationModel = (ChatNotificationModel) getIntent().getSerializableExtra(ChatConstants.ARG_NOTIFICATION_MODEL);
            Intent intent = new Intent(this, ChatActivity.class);
            intent.putExtra(str, chatNotificationModel.getScreenId());
            intent.putExtra(ChatConstants.ARG_FIREBASE_USER_UID, chatNotificationModel.getSenderUid());
            startActivity(intent);
            this.isNotification = false;
        }
        FragmentManager supportFragmentManager = getSupportFragmentManager();
        ChatMessageListFragment chatMessageListFragment = new ChatMessageListFragment();
        Bundle bundle = new Bundle();
        bundle.putString(str, ChatConstants.chatScreenId);
        chatMessageListFragment.setArguments(bundle);
        supportFragmentManager.beginTransaction().replace(R.id.chat_container, chatMessageListFragment).commit();
        this.firebaseChatHelper = new FirebaseChatHelper(getApplicationContext());
        this.firebaseChatHelper.getMe();
        invalidateOptionsMenu();
        init();
    }

    @OnClick({2131362372})
    public void fabOnClick() {
        openUserListActivity();
    }

    public void init() {
        this.firebaseChatHelper.setUserStatus();
    }

    public AppCompatActivity injectActivity(ActivityComponent activityComponent) {
        activityComponent.inject(this);
        return this;
    }

    /* access modifiers changed from: protected */
    public void onResume() {
        super.onResume();
        if (this.sharedPrefHelper.getIsAdmobBannerAdEnabled()) {
            int heightInPixels = AdSize.SMART_BANNER.getHeightInPixels(this);
            MarginLayoutParams marginLayoutParams = (MarginLayoutParams) this.fabButton.getLayoutParams();
            marginLayoutParams.rightMargin = RssUtil.convertDpToPixel(16.0f, this);
            marginLayoutParams.bottomMargin = heightInPixels + RssUtil.convertDpToPixel(16.0f, this);
        }
        if (this.mainLayout != null) {
            new BannerHelper().addBannerAd(this.mainLayout, this.overlayLayout);
        }
        MobiRollerApplication.isChatActivityOpen = true;
        MobiRollerApplication.aveChatViewId = getIntent().getStringExtra(Constants.KEY_SCREEN_ID);
    }

    /* access modifiers changed from: protected */
    public void onDestroy() {
        super.onDestroy();
        MobiRollerApplication.isChatActivityOpen = false;
        MobiRollerApplication.aveChatViewId = null;
        ChatConstants.setChatScreenId(null);
        FirebaseChatHelper firebaseChatHelper2 = this.firebaseChatHelper;
        if (firebaseChatHelper2 != null) {
            firebaseChatHelper2.removeUserStatusListener();
        }
        FirebaseChatHelper firebaseChatHelper3 = this.firebaseChatHelper;
        if (firebaseChatHelper3 != null) {
            firebaseChatHelper3.removeGetMe();
        }
    }

    public boolean onPrepareOptionsMenu(Menu menu) {
        return this.sharedPrefHelper.getUserLoginStatus() && this.sharedPrefHelper.getUserIsAvailableForChat();
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        if (this.sharedPrefHelper.getChatValidated() && this.sharedPrefHelper.getUserLoginStatus() && this.sharedPrefHelper.getUserIsAvailableForChat() && FirebaseAuth.getInstance().getCurrentUser() != null) {
            if (this.sharedPrefHelper.getUserIsChatAdmin() || this.sharedPrefHelper.getUserIsChatMod()) {
                getToolbar().inflateMenu(R.menu.chat_admin_main_actions_menu);
            } else {
                getToolbar().inflateMenu(R.menu.chat_main_actions_menu);
            }
        }
        return super.onCreateOptionsMenu(menu);
    }

    public boolean onOptionsItemSelected(MenuItem menuItem) {
        int itemId = menuItem.getItemId();
        if (itemId == R.id.action_add_group) {
            this.interstitialAdsUtil.checkInterstitialAds(new Intent(this, CreateGroupChat.class));
        } else if (itemId == R.id.action_admin) {
            startActivity(new Intent(this, ChatAdminActivity.class).putExtra("panel", ChatConstants.ADMIN_PANEL));
        } else if (itemId == R.id.action_profile_chat) {
            if (this.userModel != null) {
                this.interstitialAdsUtil.checkInterstitialAds(new Intent(this, UserProfileActivity.class).putExtra(ChatConstants.ARG_FIREBASE_USER_UID, this.userModel.userInfo.uid));
            } else {
                this.firebaseChatHelper.getMe();
            }
        }
        return true;
    }

    @Subscribe
    public void chatAccountEvent(ChatAccountEvent chatAccountEvent) {
        this.userModel = chatAccountEvent.getUserModel();
        EventBus.getDefault().post(new NewDetailsEvent());
        if (chatAccountEvent.isBanned()) {
            new Builder(this).cancelable(false).content((int) R.string.account_blocked).positiveText((int) R.string.OK).show();
            enableDisableView(this.frameContainer, false);
            enableDisableView(this.fabButton, false);
        } else {
            ArrayList arrayList = new ArrayList();
            for (Entry key : this.userModel.blockedByUserList.entrySet()) {
                arrayList.add(key.getKey());
            }
            ArrayList arrayList2 = new ArrayList();
            for (Entry key2 : this.userModel.blockedUserList.entrySet()) {
                arrayList2.add(key2.getKey());
            }
            UserHelper.setUserBlockedByUserList(arrayList);
            UserHelper.setUserBlockedUserList(arrayList2);
            if (this.userModel.isChatAdmin() || this.userModel.isChatMod()) {
                if (this.userModel.isChatMod()) {
                    this.sharedPrefHelper.setUserIsChatMod(true);
                } else if (this.userModel.isChatAdmin()) {
                    this.sharedPrefHelper.setUserIsChatAdmin(true);
                }
                if (this.userModel.isSuperUser()) {
                    this.sharedPrefHelper.setUserIsChatSuperUser(true);
                }
                invalidateOptionsMenu();
            } else {
                this.sharedPrefHelper.setUserIsChatAdmin(false);
                this.sharedPrefHelper.setUserIsChatMod(false);
                this.sharedPrefHelper.setUserIsChatSuperUser(false);
            }
            enableDisableView(this.frameContainer, true);
            enableDisableView(this.fabButton, true);
        }
        ChatUserDetails chatUserDetails = this.userModel;
        if (chatUserDetails != null && (chatUserDetails.getUserInfo().name == null || this.userModel.getUserInfo().name.isEmpty())) {
            if (UserHelper.getUserName() == null) {
                startActivity(new Intent(this, UserUpdateActivity.class).putExtra(UserUpdateActivity.FILL_NAME, true));
            } else {
                new FirebaseChatHelper(this).updateUserFullName();
            }
        }
        ChatUserDetails chatUserDetails2 = this.userModel;
        if (!(chatUserDetails2 == null || chatUserDetails2.getUserInfo() == null || this.userModel.getUserInfo().email != null)) {
            new FirebaseChatHelper(this).updateUserEmail();
        }
        if (this.userModel.getUserInfo().uid != null && this.userModel.getUserInfo().getUsername() == null) {
            this.firebaseChatHelper.generateUserName(this.userModel.getUserInfo().name, this.userModel.getUserInfo().uid);
        }
    }

    public void openUserListActivity() {
        Intent intent = new Intent(this, ChatUserListActivity.class);
        String str = "roles";
        if (getIntent().hasExtra(str)) {
            intent.putExtra(str, getIntent().getSerializableExtra(str));
        } else if (this.sharedPrefHelper.getSharedPrefs(this).contains(ChatConstants.chatScreenId)) {
            Set<String> stringSet = this.sharedPrefHelper.getSharedPrefs(this).getStringSet(ChatConstants.chatScreenId, null);
            if (stringSet != null) {
                ArrayList arrayList = new ArrayList();
                for (String valueOf : stringSet) {
                    arrayList.add(Integer.valueOf(valueOf));
                }
                intent.putExtra(str, arrayList);
            }
        }
        startActivity(intent);
    }

    public void setToolbarTitle(AppCompatActivity appCompatActivity, String str) {
        try {
            ToolbarHelper.setToolbar(appCompatActivity, (Toolbar) appCompatActivity.findViewById(R.id.toolbar_top));
            appCompatActivity.getSupportActionBar().setTitle((CharSequence) str);
        } catch (Exception unused) {
        }
    }

    public static void enableDisableView(View view, boolean z) {
        view.setEnabled(z);
        if (view instanceof ViewGroup) {
            ViewGroup viewGroup = (ViewGroup) view;
            for (int i = 0; i < viewGroup.getChildCount(); i++) {
                enableDisableView(viewGroup.getChildAt(i), z);
            }
        }
    }
}
