package com.mobiroller.fragments;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences.Editor;
import android.content.res.ColorStateList;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentTransaction;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.afollestad.materialdialogs.MaterialDialog.Builder;
import com.google.firebase.auth.FirebaseAuth;
import com.mobiroller.MobiRollerApplication;
import com.mobiroller.activities.base.AveActivity;
import com.mobiroller.activities.chat.ChatActivity;
import com.mobiroller.activities.chat.ChatAdminActivity;
import com.mobiroller.activities.chat.ChatUserListActivity;
import com.mobiroller.activities.chat.CreateGroupChat;
import com.mobiroller.activities.user.UserProfileActivity;
import com.mobiroller.activities.user.UserUpdateActivity;
import com.mobiroller.constants.ChatConstants;
import com.mobiroller.constants.Constants;
import com.mobiroller.fragments.chat.ChatMessageListFragment;
import com.mobiroller.helpers.FirebaseChatHelper;
import com.mobiroller.helpers.JSONStorage;
import com.mobiroller.helpers.UserHelper;
import com.mobiroller.mobi942763453128.R;
import com.mobiroller.models.ScreenModel;
import com.mobiroller.models.chat.ChatNotificationModel;
import com.mobiroller.models.chat.ChatUserDetails;
import com.mobiroller.models.events.ChatAccountEvent;
import com.mobiroller.models.events.NewDetailsEvent;
import com.mobiroller.util.InterstitialAdsUtil;
import com.mobiroller.views.custom.MobirollerFloatingActionButton;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Map.Entry;
import java.util.Set;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

public class aveChatViewFragment extends BaseModuleFragment {
    private boolean blockDialogShown = false;
    @BindView(2131362372)
    MobirollerFloatingActionButton fabButton;
    private FirebaseChatHelper firebaseChatHelper;
    @BindView(2131362143)
    FrameLayout frameContainer;
    private InterstitialAdsUtil interstitialAdsUtil;
    @BindView(2131362649)
    RelativeLayout mainLayout;
    @BindView(2131362873)
    RelativeLayout overlayLayout;
    private ScreenModel screenModel;
    @BindView(2131363276)
    Toolbar toolbarTop;
    private ChatUserDetails userModel;

    public void onPrepareOptionsMenu(Menu menu) {
    }

    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        View inflate = layoutInflater.inflate(R.layout.fragment_chat, viewGroup, false);
        ButterKnife.bind((Object) this, inflate);
        this.interstitialAdsUtil = new InterstitialAdsUtil((Activity) getActivity());
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }
        this.toolbarTop.setVisibility(8);
        this.fabButton.setBackgroundTintList(ColorStateList.valueOf(this.sharedPrefHelper.getActionBarColor()));
        if (getArguments() != null) {
            ChatConstants.setChatScreenId(getArguments().getString(Constants.KEY_SCREEN_ID));
        } else {
            ChatConstants.setChatScreenId(null);
        }
        if (JSONStorage.containsScreen(this.screenId)) {
            this.screenModel = JSONStorage.getScreenModel(this.screenId);
            Editor edit = this.sharedPrefHelper.getSharedPrefs(getActivity()).edit();
            StringBuilder sb = new StringBuilder();
            sb.append(ChatConstants.chatScreenId);
            sb.append("title");
            edit.putString(sb.toString(), this.screenModel.getTitle()).apply();
        }
        if (!this.sharedPrefHelper.getChatValidated() || !this.sharedPrefHelper.getUserLoginStatus() || !this.sharedPrefHelper.getUserIsAvailableForChat() || FirebaseAuth.getInstance().getCurrentUser() == null) {
            handleChatError();
        } else {
            initChatSettings();
        }
        if (getArguments() != null) {
            String str = "roles";
            if (getArguments().containsKey(str)) {
                ArrayList arrayList = (ArrayList) getArguments().getSerializable(str);
                HashSet hashSet = new HashSet();
                for (int i = 0; i < arrayList.size(); i++) {
                    hashSet.add(String.valueOf(arrayList.get(i)));
                }
                this.sharedPrefHelper.getSharedPrefs(getActivity()).edit().putStringSet(ChatConstants.chatScreenId, hashSet).apply();
            }
        }
        return inflate;
    }

    private void handleChatError() {
        this.fabButton.setVisibility(8);
        this.fabButton.setClickable(false);
        if (!this.sharedPrefHelper.getChatValidated()) {
            new Builder(getActivity()).content((int) R.string.firebase_validation_failed).positiveText((int) R.string.OK).show();
            getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.frame_container, new RequiresMembershipFragment()).commit();
        } else if (!this.sharedPrefHelper.getUserLoginStatus()) {
            UserLoginFragment userLoginFragment = new UserLoginFragment();
            Bundle arguments = getArguments();
            String str = Constants.KEY_SCREEN_ID;
            if (arguments.containsKey(str)) {
                Bundle bundle = new Bundle();
                bundle.putString(str, getArguments().getString(str));
                userLoginFragment.setArguments(bundle);
            }
            getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.frame_container, userLoginFragment).commit();
        } else {
            new Builder(getActivity()).cancelable(false).content((int) R.string.chat_action_invalid_account).positiveText((int) R.string.OK).show();
            RequiresMembershipFragment requiresMembershipFragment = new RequiresMembershipFragment();
            Bundle bundle2 = new Bundle();
            bundle2.putBoolean("invalidAccount", true);
            requiresMembershipFragment.setArguments(bundle2);
            getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.frame_container, requiresMembershipFragment).commit();
        }
    }

    private void initChatSettings() {
        Bundle arguments = getArguments();
        String str = Constants.KEY_SCREEN_ID;
        if (arguments != null) {
            Bundle arguments2 = getArguments();
            String str2 = ChatConstants.ARG_NOTIFICATION_MODEL;
            if (arguments2.containsKey(str2) && getArguments().getSerializable(str2) != null) {
                ChatNotificationModel chatNotificationModel = (ChatNotificationModel) getArguments().getSerializable(str2);
                Intent intent = new Intent(getActivity(), ChatActivity.class);
                intent.putExtra(str, getArguments().getString(str));
                intent.putExtra(ChatConstants.ARG_FIREBASE_USER_UID, chatNotificationModel.getReceiverUid());
                startActivity(intent);
            }
        }
        FragmentTransaction beginTransaction = getActivity().getSupportFragmentManager().beginTransaction();
        Log.e("chat", "messageFragment");
        ChatMessageListFragment chatMessageListFragment = new ChatMessageListFragment();
        Bundle bundle = new Bundle();
        bundle.putString(str, getArguments().getString(str));
        chatMessageListFragment.setArguments(bundle);
        beginTransaction.replace(R.id.chat_container_fragment, chatMessageListFragment).commit();
        this.firebaseChatHelper = new FirebaseChatHelper(getActivity().getApplicationContext());
        getActivity().invalidateOptionsMenu();
        setHasOptionsMenu(true);
        init();
        this.firebaseChatHelper.getMe();
    }

    public void init() {
        this.firebaseChatHelper.setUserStatus();
    }

    public void onResume() {
        super.onResume();
        if (this.mainLayout != null) {
            this.bannerHelper.addBannerAd(this.mainLayout, this.overlayLayout);
        }
    }

    public void onStart() {
        super.onStart();
        MobiRollerApplication.isChatActivityOpen = true;
        if (getArguments() != null) {
            Bundle arguments = getArguments();
            String str = Constants.KEY_SCREEN_ID;
            ChatConstants.setChatScreenId(String.valueOf(arguments.getString(str)));
            MobiRollerApplication.aveChatViewId = String.valueOf(getArguments().getString(str));
            return;
        }
        MobiRollerApplication.aveChatViewId = "0";
        ChatConstants.setChatScreenId(null);
    }

    public void onDestroyView() {
        super.onDestroyView();
        MobiRollerApplication.isChatActivityOpen = false;
        MobiRollerApplication.aveChatViewId = null;
        ChatConstants.setChatScreenId(null);
        try {
            if (this.firebaseChatHelper != null) {
                this.firebaseChatHelper.removeUserStatusListener();
            }
            if (this.firebaseChatHelper != null) {
                this.firebaseChatHelper.removeGetMe();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void onCreateOptionsMenu(Menu menu, MenuInflater menuInflater) {
        if (this.sharedPrefHelper.getUserLoginStatus() && this.sharedPrefHelper.getUserIsAvailableForChat()) {
            if (this.sharedPrefHelper.getUserIsChatAdmin() || this.sharedPrefHelper.getUserIsChatMod()) {
                ((AveActivity) getActivity()).getToolbar().inflateMenu(R.menu.chat_admin_main_actions_menu);
            } else {
                ((AveActivity) getActivity()).getToolbar().inflateMenu(R.menu.chat_main_actions_menu);
            }
        }
        super.onCreateOptionsMenu(menu, menuInflater);
    }

    public boolean onOptionsItemSelected(MenuItem menuItem) {
        int itemId = menuItem.getItemId();
        if (itemId == R.id.action_add_group) {
            this.interstitialAdsUtil.checkInterstitialAds(new Intent(getActivity(), CreateGroupChat.class));
        } else if (itemId == R.id.action_admin) {
            this.interstitialAdsUtil.checkInterstitialAds(new Intent(getActivity(), ChatAdminActivity.class).putExtra("panel", ChatConstants.ADMIN_PANEL));
        } else if (itemId == R.id.action_profile_chat) {
            if (this.userModel != null) {
                this.interstitialAdsUtil.checkInterstitialAds(new Intent(getActivity(), UserProfileActivity.class).putExtra(ChatConstants.ARG_FIREBASE_USER_UID, this.userModel.getUserInfo().uid));
            } else {
                this.firebaseChatHelper.getMe();
            }
        }
        return true;
    }

    @Subscribe
    public void chatAccountEvent(ChatAccountEvent chatAccountEvent) {
        if (getActivity() != null) {
            this.userModel = chatAccountEvent.getUserModel();
            EventBus.getDefault().post(new NewDetailsEvent());
            if (chatAccountEvent.isBanned()) {
                if (!this.blockDialogShown) {
                    new Builder(getActivity()).cancelable(false).content((int) R.string.account_blocked).positiveText((int) R.string.OK).show();
                    this.blockDialogShown = true;
                }
                enableDisableView(this.frameContainer, false);
                enableDisableView(this.fabButton, false);
                this.sharedPrefHelper.setUserIsChatAdmin(false);
                this.sharedPrefHelper.setUserIsChatMod(false);
                this.sharedPrefHelper.setUserIsChatSuperUser(false);
                setHasOptionsMenu(true);
                getActivity().invalidateOptionsMenu();
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
                        this.sharedPrefHelper.setUserIsChatAdmin(false);
                    } else if (this.userModel.isChatAdmin()) {
                        this.sharedPrefHelper.setUserIsChatAdmin(true);
                        this.sharedPrefHelper.setUserIsChatMod(false);
                    }
                    if (this.userModel.isSuperUser()) {
                        this.sharedPrefHelper.setUserIsChatSuperUser(true);
                    }
                    setHasOptionsMenu(true);
                    getActivity().invalidateOptionsMenu();
                } else {
                    this.sharedPrefHelper.setUserIsChatAdmin(false);
                    this.sharedPrefHelper.setUserIsChatMod(false);
                    this.sharedPrefHelper.setUserIsChatSuperUser(false);
                    setHasOptionsMenu(true);
                    getActivity().invalidateOptionsMenu();
                }
                this.blockDialogShown = false;
                enableDisableView(this.frameContainer, true);
                enableDisableView(this.fabButton, true);
            }
            ChatUserDetails chatUserDetails = this.userModel;
            if (chatUserDetails != null && (chatUserDetails.getUserInfo().name == null || this.userModel.getUserInfo().name.isEmpty())) {
                if (UserHelper.getUserName() == null) {
                    startActivity(new Intent(getActivity(), UserUpdateActivity.class).putExtra(UserUpdateActivity.FILL_NAME, true));
                } else {
                    new FirebaseChatHelper(getActivity()).updateUserFullName();
                }
            }
            ChatUserDetails chatUserDetails2 = this.userModel;
            if (!(chatUserDetails2 == null || chatUserDetails2.getUserInfo() == null || this.userModel.getUserInfo().email != null)) {
                new FirebaseChatHelper(getActivity()).updateUserEmail();
            }
            if (this.userModel.getUserInfo().uid != null && this.userModel.getUserInfo().getUsername() == null) {
                this.firebaseChatHelper.generateUserName(this.userModel.getUserInfo().name, this.userModel.getUserInfo().uid);
            }
        }
    }

    @OnClick({2131362372})
    public void openUserListActivity() {
        Intent intent = new Intent(getActivity(), ChatUserListActivity.class);
        String str = "roles";
        if (getArguments() != null && getArguments().containsKey(str)) {
            intent.putExtra(str, getArguments().getSerializable(str));
        } else if (this.sharedPrefHelper.getSharedPrefs(getActivity()).contains(ChatConstants.chatScreenId)) {
            Set<String> stringSet = this.sharedPrefHelper.getSharedPrefs(getActivity()).getStringSet(ChatConstants.chatScreenId, null);
            if (stringSet != null) {
                ArrayList arrayList = new ArrayList();
                for (String valueOf : stringSet) {
                    arrayList.add(Integer.valueOf(valueOf));
                }
                intent.putExtra(str, arrayList);
            }
        }
        this.interstitialAdsUtil.checkInterstitialAds(intent);
    }

    private static void enableDisableView(View view, boolean z) {
        view.setEnabled(z);
        view.setClickable(z);
        if (view instanceof ViewGroup) {
            ViewGroup viewGroup = (ViewGroup) view;
            for (int i = 0; i < viewGroup.getChildCount(); i++) {
                enableDisableView(viewGroup.getChildAt(i), z);
            }
        }
    }
}
