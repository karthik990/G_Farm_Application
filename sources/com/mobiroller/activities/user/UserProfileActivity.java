package com.mobiroller.activities.user;

import android.app.Activity;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.view.ViewCompat;
import androidx.exifinterface.media.ExifInterface;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.afollestad.materialdialogs.MaterialDialog.Builder;
import com.afollestad.materialdialogs.MaterialDialog.InputCallback;
import com.afollestad.materialdialogs.MaterialDialog.SingleButtonCallback;
import com.bumptech.glide.Glide;
import com.google.android.exoplayer2.metadata.icy.IcyHeaders;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.appbar.AppBarLayout.OnOffsetChangedListener;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.mobiroller.MobiRollerApplication;
import com.mobiroller.activities.base.AveActivity;
import com.mobiroller.activities.chat.ChatBlockedUserListActivity;
import com.mobiroller.adapters.chat.MaterialRoleAdapter;
import com.mobiroller.adapters.chat.MaterialRoleAdapter.Callback;
import com.mobiroller.adapters.user.UserProfileAdapter;
import com.mobiroller.constants.ChatConstants;
import com.mobiroller.helpers.BannerHelper;
import com.mobiroller.helpers.FirebaseChatHelper;
import com.mobiroller.helpers.LocaleHelper;
import com.mobiroller.helpers.ProgressViewHelper;
import com.mobiroller.helpers.RequestHelper;
import com.mobiroller.helpers.SharedPrefHelper;
import com.mobiroller.helpers.ToolbarHelper;
import com.mobiroller.helpers.UserHelper;
import com.mobiroller.helpers.UtilManager;
import com.mobiroller.interfaces.ActivityComponent;
import com.mobiroller.mobi942763453128.R;
import com.mobiroller.models.chat.ChatMetaModel;
import com.mobiroller.models.chat.ChatRoleModel;
import com.mobiroller.models.chat.ChatUserDetails;
import com.mobiroller.models.chat.ChatUserProfileEvent;
import com.mobiroller.models.chat.LogModel;
import com.mobiroller.models.chat.MaterialRoleListItem;
import com.mobiroller.models.events.UserBlockEvent;
import com.mobiroller.models.response.UserLoginResponse;
import com.mobiroller.models.response.UserProfileElement;
import com.mobiroller.serviceinterfaces.ApplyzeUserServiceInterface;
import com.mobiroller.util.ColorUtil;
import com.mobiroller.util.DialogUtil;
import com.mobiroller.util.ErrorUtils;
import com.mobiroller.views.CircleImageView;
import com.mobiroller.views.HeaderView;
import com.mobiroller.views.custom.MobirollerToolbar;
import com.mobiroller.views.theme.Theme;
import jahirfiquitiva.libs.fabsmenu.FABsMenu;
import jahirfiquitiva.libs.fabsmenu.TitleFAB;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import javax.inject.Inject;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import retrofit2.Call;
import retrofit2.Response;

public class UserProfileActivity extends AveActivity implements OnOffsetChangedListener {
    @BindView(2131361947)
    protected AppBarLayout appBarLayout;
    @BindView(2131361965)
    protected RelativeLayout badgeLayout;
    @Inject
    BannerHelper bannerHelper;
    /* access modifiers changed from: private */
    public List<ChatRoleModel> chatRoleModelList;
    @BindView(2131362170)
    protected CollapsingToolbarLayout collapsingToolbarLayout;
    @BindView(2131362333)
    protected CardView emptyView;
    @BindView(2131362372)
    protected FABsMenu fab;
    @BindView(2131362374)
    protected TitleFAB fabBlock;
    @BindView(2131362381)
    protected TitleFAB fabRole;
    /* access modifiers changed from: private */
    public FirebaseChatHelper firebaseChatHelper;
    /* access modifiers changed from: private */
    public ChatUserDetails firebaseUser;
    @BindView(2131362420)
    protected HeaderView floatHeaderView;
    private boolean isHideToolbarView = false;
    /* access modifiers changed from: private */
    public String mUserUid;
    @BindView(2131363281)
    RelativeLayout mainLayout;
    @BindView(2131362873)
    RelativeLayout overlayLayout;
    @BindView(2131363351)
    protected RecyclerView personalDataRecyclerView;
    @BindView(2131362918)
    protected TextView personalTitle;
    @BindView(2131363053)
    protected ImageView roleBadge;
    @BindView(2131363054)
    protected TextView roleTitle;
    @Inject
    SharedPrefHelper sharedPrefHelper;
    @BindView(2131363270)
    protected MobirollerToolbar toolbar;
    @BindView(2131363272)
    protected HeaderView toolbarHeaderView;
    @Inject
    ToolbarHelper toolbarHelper;
    @BindView(2131362531)
    protected ImageView userImage;
    @BindView(2131362206)
    protected CircleImageView userLogo;
    private List<UserProfileElement> userProfileItems;

    public boolean onPrepareOptionsMenu(Menu menu) {
        return true;
    }

    /* access modifiers changed from: protected */
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView((int) R.layout.activity_user_profile);
        ButterKnife.bind((Activity) this);
        setMobirollerToolbar(this.toolbar);
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }
        if (!UtilManager.networkHelper().isConnected()) {
            DialogUtil.showNoConnectionError(this);
        }
        this.firebaseChatHelper = new FirebaseChatHelper(getApplicationContext());
        this.progressViewHelper = new ProgressViewHelper((AppCompatActivity) this);
        Intent intent = getIntent();
        String str = ChatConstants.ARG_FIREBASE_USER_UID;
        if (intent.hasExtra(str)) {
            this.mUserUid = getIntent().getStringExtra(str);
            this.firebaseChatHelper.getUserFromFirebaseProfile(this.mUserUid, true);
        } else {
            login();
        }
        this.personalTitle.setTextColor(ColorUtil.isColorDark(Theme.primaryColor) ? Theme.primaryColor : ViewCompat.MEASURED_STATE_MASK);
        this.toolbar.setNavigationIcon((int) R.drawable.ic_arrow_back_white_24dp);
        if (VERSION.SDK_INT >= 19) {
            this.toolbar.getNavigationIcon().setAutoMirrored(true);
        }
        setSupportActionBar(this.toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle((CharSequence) "");
        this.toolbar.setNavigationOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                UserProfileActivity.this.finish();
            }
        });
        this.toolbarHelper.setStatusBar(this);
        this.collapsingToolbarLayout.setContentScrimColor(this.sharedPrefHelper.getActionBarColor());
        initUi();
    }

    public void onResume() {
        super.onResume();
        RelativeLayout relativeLayout = this.mainLayout;
        if (relativeLayout != null) {
            this.bannerHelper.addBannerAd(relativeLayout, this.overlayLayout);
        }
    }

    @OnClick({2131362374})
    public void blockUser() {
        if (this.firebaseChatHelper.checkInternetConnection()) {
            String string = getString(R.string.block_user);
            if (this.firebaseUser.getUserInfo().isBanned) {
                string = getString(R.string.unblock_user);
            }
            new Builder(this).content((CharSequence) string).positiveText((int) R.string.OK).negativeText((int) R.string.cancel).onPositive(new SingleButtonCallback() {
                public void onClick(MaterialDialog materialDialog, DialogAction dialogAction) {
                    if (UserProfileActivity.this.firebaseUser.getUserInfo().isBanned) {
                        UserProfileActivity.this.unBanUser();
                    } else {
                        UserProfileActivity.this.banUser();
                    }
                }
            }).show();
        }
    }

    @OnClick({2131362381})
    public void changeUserRole() {
        if (this.firebaseChatHelper.checkInternetConnection()) {
            this.chatRoleModelList = new ArrayList();
            List chatRoleModels = MobiRollerApplication.getChatRoleModels();
            Collections.sort(chatRoleModels, new Comparator<ChatRoleModel>() {
                public int compare(ChatRoleModel chatRoleModel, ChatRoleModel chatRoleModel2) {
                    return Integer.valueOf(chatRoleModel.getPermissionTypeID()).intValue() >= Integer.valueOf(chatRoleModel2.getPermissionTypeID()).intValue() ? 1 : -1;
                }
            });
            String str = this.firebaseUser.getRoles().chatPermissionIdString;
            String str2 = IcyHeaders.REQUEST_HEADER_ENABLE_METADATA_VALUE;
            if (str == null || this.firebaseUser.getRoles().chatRoleIdString == null) {
                MaterialRoleAdapter materialRoleAdapter = new MaterialRoleAdapter(this, new Callback() {
                    public void onMaterialListItemSelected(final MaterialDialog materialDialog, final int i, MaterialRoleListItem materialRoleListItem) {
                        Builder title = new Builder(UserProfileActivity.this).title((int) R.string.chat_role_append_warning);
                        UserProfileActivity userProfileActivity = UserProfileActivity.this;
                        title.content((CharSequence) userProfileActivity.getString(R.string.chat_role_append_description, new Object[]{((ChatRoleModel) userProfileActivity.chatRoleModelList.get(i)).getChatRoleName()})).positiveText((int) R.string.OK).negativeText((int) R.string.cancel).onPositive(new SingleButtonCallback() {
                            public void onClick(MaterialDialog materialDialog, DialogAction dialogAction) {
                                UserProfileActivity.this.appendRoleToUser((ChatRoleModel) UserProfileActivity.this.chatRoleModelList.get(i));
                                materialDialog.dismiss();
                                Toast.makeText(UserProfileActivity.this, UserProfileActivity.this.getString(R.string.chat_role_appended, new Object[]{((ChatRoleModel) UserProfileActivity.this.chatRoleModelList.get(i)).getChatRoleName()}), 0).show();
                            }
                        }).show();
                    }
                });
                for (int i = 0; i < chatRoleModels.size(); i++) {
                    if (!this.sharedPrefHelper.getUserIsChatMod() || !((ChatRoleModel) chatRoleModels.get(i)).getPermissionTypeID().equalsIgnoreCase(str2)) {
                        this.chatRoleModelList.add(chatRoleModels.get(i));
                        if (((ChatRoleModel) chatRoleModels.get(i)).getRibbonImage() == null || ((ChatRoleModel) chatRoleModels.get(i)).getRibbonImage().isEmpty()) {
                            materialRoleAdapter.add(new MaterialRoleListItem.Builder(this).content((CharSequence) ((ChatRoleModel) chatRoleModels.get(i)).getChatRoleName()).icon((int) R.drawable.defaultuser).backgroundColor(-1).build());
                        } else {
                            materialRoleAdapter.add(new MaterialRoleListItem.Builder(this).content((CharSequence) ((ChatRoleModel) chatRoleModels.get(i)).getChatRoleName()).iconUrl(((ChatRoleModel) chatRoleModels.get(i)).getRibbonImage()).backgroundColor(-1).build());
                        }
                    }
                }
                new Builder(this).title((int) R.string.role_list).adapter(materialRoleAdapter, null).show();
                return;
            }
            String str3 = "";
            for (int i2 = 0; i2 < MobiRollerApplication.getChatRoleModels().size(); i2++) {
                if (this.firebaseUser.getRoles().chatRoleIdString.equalsIgnoreCase(((ChatRoleModel) MobiRollerApplication.getChatRoleModels().get(i2)).getId())) {
                    str3 = ((ChatRoleModel) MobiRollerApplication.getChatRoleModels().get(i2)).getChatRoleName();
                }
            }
            MaterialRoleAdapter materialRoleAdapter2 = new MaterialRoleAdapter(this, new Callback() {
                public void onMaterialListItemSelected(final MaterialDialog materialDialog, final int i, MaterialRoleListItem materialRoleListItem) {
                    Builder title = new Builder(UserProfileActivity.this).title((int) R.string.chat_role_append_warning);
                    UserProfileActivity userProfileActivity = UserProfileActivity.this;
                    title.content((CharSequence) userProfileActivity.getString(R.string.chat_role_append_description, new Object[]{((ChatRoleModel) userProfileActivity.chatRoleModelList.get(i)).getChatRoleName()})).positiveText((int) R.string.OK).negativeText((int) R.string.cancel).onPositive(new SingleButtonCallback() {
                        public void onClick(MaterialDialog materialDialog, DialogAction dialogAction) {
                            UserProfileActivity.this.appendRoleToUser((ChatRoleModel) UserProfileActivity.this.chatRoleModelList.get(i));
                            materialDialog.dismiss();
                            Toast.makeText(UserProfileActivity.this, UserProfileActivity.this.getString(R.string.chat_role_appended, new Object[]{((ChatRoleModel) UserProfileActivity.this.chatRoleModelList.get(i)).getChatRoleName()}), 0).show();
                        }
                    }).show();
                }
            });
            for (int i3 = 0; i3 < chatRoleModels.size(); i3++) {
                if (!this.sharedPrefHelper.getUserIsChatMod() || !((ChatRoleModel) chatRoleModels.get(i3)).getPermissionTypeID().equalsIgnoreCase(str2)) {
                    this.chatRoleModelList.add(chatRoleModels.get(i3));
                    if (((ChatRoleModel) chatRoleModels.get(i3)).getRibbonImage() == null || ((ChatRoleModel) chatRoleModels.get(i3)).getRibbonImage().isEmpty()) {
                        materialRoleAdapter2.add(new MaterialRoleListItem.Builder(this).content((CharSequence) ((ChatRoleModel) chatRoleModels.get(i3)).getChatRoleName()).icon((int) R.drawable.defaultuser).backgroundColor(-1).build());
                    } else {
                        materialRoleAdapter2.add(new MaterialRoleListItem.Builder(this).content((CharSequence) ((ChatRoleModel) chatRoleModels.get(i3)).getChatRoleName()).iconUrl(((ChatRoleModel) chatRoleModels.get(i3)).getRibbonImage()).backgroundColor(-1).build());
                    }
                }
            }
            new Builder(this).title((int) R.string.role_list).adapter(materialRoleAdapter2, null).neutralText((CharSequence) getString(R.string.chat_remove_user_role, new Object[]{str3})).onNeutral(new SingleButtonCallback() {
                public void onClick(MaterialDialog materialDialog, DialogAction dialogAction) {
                    new Builder(UserProfileActivity.this).content((int) R.string.remove_role_dialog).positiveText((int) R.string.OK).negativeText((int) R.string.cancel).onPositive(new SingleButtonCallback() {
                        public void onClick(MaterialDialog materialDialog, DialogAction dialogAction) {
                            UserProfileActivity.this.removeRole();
                        }
                    }).show();
                }
            }).show();
        }
    }

    public AppCompatActivity injectActivity(ActivityComponent activityComponent) {
        activityComponent.inject(this);
        return this;
    }

    private void initUi() {
        this.appBarLayout.addOnOffsetChangedListener((OnOffsetChangedListener) this);
    }

    public void onOffsetChanged(AppBarLayout appBarLayout2, int i) {
        float abs = ((float) Math.abs(i)) / ((float) appBarLayout2.getTotalScrollRange());
        if (abs == 1.0f && this.isHideToolbarView) {
            this.toolbarHeaderView.setVisibility(0);
            this.isHideToolbarView = !this.isHideToolbarView;
        } else if (abs < 1.0f && !this.isHideToolbarView) {
            this.toolbarHeaderView.setVisibility(8);
            this.isHideToolbarView = !this.isHideToolbarView;
        }
    }

    public void setFirebaseUserToAdapter() {
        ArrayList arrayList = new ArrayList();
        String str = "text";
        if (this.firebaseUser.getUserInfo().username != null) {
            arrayList.add(new UserProfileElement(getString(R.string.chat_username), "gender", str, this.firebaseUser.getUserInfo().username));
        }
        if (this.firebaseUser.getUserInfo().email != null && this.sharedPrefHelper.getUserIsChatAdmin()) {
            arrayList.add(new UserProfileElement(getString(R.string.email), "email", str, this.firebaseUser.getUserInfo().email));
        }
        if (this.firebaseUser.getMetaModelList() != null) {
            List metaModelList = this.firebaseUser.getMetaModelList();
            for (int i = 0; i < metaModelList.size(); i++) {
                if (!((ChatMetaModel) metaModelList.get(i)).type.equalsIgnoreCase(TtmlNode.TAG_IMAGE)) {
                    arrayList.add(new UserProfileElement(((ChatMetaModel) metaModelList.get(i)).title, ((ChatMetaModel) metaModelList.get(i)).type, str, ((ChatMetaModel) metaModelList.get(i)).value));
                }
            }
        }
        int i2 = 0;
        while (i2 < arrayList.size()) {
            if (((UserProfileElement) arrayList.get(i2)).value == null || ((UserProfileElement) arrayList.get(i2)).value.isEmpty()) {
                arrayList.remove(i2);
                i2--;
            }
            i2++;
        }
        if (this.firebaseUser.getRoles().chatRoleIdString != null) {
            for (int i3 = 0; i3 < MobiRollerApplication.getChatRoleModels().size(); i3++) {
                if (((ChatRoleModel) MobiRollerApplication.getChatRoleModels().get(i3)).getId().equalsIgnoreCase(this.firebaseUser.getRoles().chatRoleIdString)) {
                    this.badgeLayout.setVisibility(0);
                    ChatRoleModel chatRoleModel = (ChatRoleModel) MobiRollerApplication.getChatRoleModels().get(i3);
                    if (chatRoleModel.getRibbonImage() == null || chatRoleModel.getRibbonImage().isEmpty()) {
                        this.roleBadge.setVisibility(8);
                    } else {
                        Glide.with((FragmentActivity) this).load(chatRoleModel.getRibbonImage()).into(this.roleBadge);
                    }
                    this.roleTitle.setText(chatRoleModel.getChatRoleName());
                }
            }
        } else {
            this.badgeLayout.setVisibility(8);
        }
        if (arrayList.size() != 0) {
            this.personalDataRecyclerView.setAdapter(new UserProfileAdapter(arrayList, this));
            this.personalDataRecyclerView.setLayoutManager(new LinearLayoutManager(this));
            return;
        }
        this.personalTitle.setVisibility(8);
        this.personalDataRecyclerView.setVisibility(8);
        this.emptyView.setVisibility(0);
        this.emptyView.setCardBackgroundColor(this.sharedPrefHelper.getActionBarColor());
    }

    public void login() {
        ApplyzeUserServiceInterface applyzeUserAPIService = new RequestHelper(this, this.sharedPrefHelper).getApplyzeUserAPIService();
        HashMap hashMap = new HashMap();
        hashMap.put("sessionKey", UserHelper.getUserSessionToken());
        hashMap.put(TtmlNode.ATTR_ID, UserHelper.getUserId());
        hashMap.put("apiKey", getString(R.string.applyze_api_key));
        hashMap.put("appKey", getString(R.string.applyze_app_key));
        hashMap.put("udid", this.sharedPrefHelper.getDeviceId());
        hashMap.put("lang", LocaleHelper.getLocale().toUpperCase());
        applyzeUserAPIService.validateSession(hashMap).enqueue(new retrofit2.Callback<UserLoginResponse>() {
            public void onResponse(Call<UserLoginResponse> call, Response<UserLoginResponse> response) {
                if (response.isSuccessful()) {
                    UserProfileActivity.this.saveInfo((UserLoginResponse) response.body());
                    return;
                }
                Toast.makeText(UserProfileActivity.this, ErrorUtils.parseError(response).message(), 0).show();
                UserHelper.logout(UserProfileActivity.this.sharedPrefHelper, UserProfileActivity.this);
            }

            public void onFailure(Call<UserLoginResponse> call, Throwable th) {
                UserHelper.logout(UserProfileActivity.this.sharedPrefHelper, UserProfileActivity.this);
                Toast.makeText(UserProfileActivity.this, R.string.common_error, 0).show();
            }
        });
    }

    public void saveInfo(UserLoginResponse userLoginResponse) {
        this.userProfileItems = userLoginResponse.profileValues;
        String str = null;
        int i = 0;
        while (i < this.userProfileItems.size()) {
            String str2 = ((UserProfileElement) this.userProfileItems.get(i)).type;
            String str3 = "photo";
            if (str2.equalsIgnoreCase(str3)) {
                str = ((UserProfileElement) this.userProfileItems.get(i)).value;
            }
            if (str2.equalsIgnoreCase("userName") || str2.equalsIgnoreCase("nameSurname") || str2.equalsIgnoreCase("password") || str2.equalsIgnoreCase(str3)) {
                this.userProfileItems.remove(i);
                i--;
            }
            i++;
        }
        if (str == null || str.equals("")) {
            this.appBarLayout.setExpanded(false);
            this.userLogo.setVisibility(8);
        } else {
            Glide.with((FragmentActivity) this).load(str).into(this.userImage);
            Glide.with((FragmentActivity) this).load(str).into((ImageView) this.userLogo);
        }
        List<UserProfileElement> list = this.userProfileItems;
        if (list == null || list.size() == 0) {
            this.personalTitle.setVisibility(8);
            this.personalDataRecyclerView.setVisibility(8);
            this.emptyView.setVisibility(0);
        } else {
            this.personalDataRecyclerView.setAdapter(new UserProfileAdapter(this.userProfileItems, this));
            this.personalDataRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        }
        this.sharedPrefHelper.setUserLoginStatus(true);
        this.sharedPrefHelper.setUserRole(userLoginResponse.roleId);
        UserHelper.saveLoginCredentials(this, userLoginResponse);
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getToolbar().inflateMenu(R.menu.profile_menu);
        Menu menu2 = getToolbar().getMenu();
        ChatUserDetails chatUserDetails = this.firebaseUser;
        if (!(chatUserDetails == null || chatUserDetails.getUserInfo().email == null || this.firebaseUser.getUserInfo().email.equalsIgnoreCase(UserHelper.getUserEmail()))) {
            menu2.getItem(0).setVisible(false);
            menu2.getItem(1).setVisible(false);
        }
        ChatUserDetails chatUserDetails2 = this.firebaseUser;
        if (chatUserDetails2 != null && chatUserDetails2.getUserInfo().email != null && !this.firebaseUser.getUserInfo().email.equalsIgnoreCase(UserHelper.getUserEmail()) && !this.firebaseUser.isAuthorizedUser()) {
            menu2.getItem(2).setVisible(true);
            setBlockText(menu2.getItem(2));
        }
        ChatUserDetails chatUserDetails3 = this.firebaseUser;
        if (!(chatUserDetails3 == null || chatUserDetails3.getUserInfo().email == null || !this.firebaseUser.getUserInfo().email.equalsIgnoreCase(UserHelper.getUserEmail()))) {
            menu2.findItem(R.id.action_blocked_list).setVisible(true);
        }
        return super.onCreateOptionsMenu(menu2);
    }

    public boolean onOptionsItemSelected(MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case R.id.action_block /*2131361872*/:
                blockOrUnBlockUser();
                return true;
            case R.id.action_blocked_list /*2131361874*/:
                startActivity(new Intent(this, ChatBlockedUserListActivity.class));
                return true;
            case R.id.action_edit /*2131361885*/:
                startActivity(new Intent(this, UserUpdateActivity.class));
                return true;
            case R.id.action_edit_status /*2131361886*/:
                showStatusEditDialog();
                return true;
            default:
                return super.onOptionsItemSelected(menuItem);
        }
    }

    public void banUser() {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
        String str = ChatConstants.ARG_USERS;
        String str2 = "ib";
        DatabaseReference child = reference.child(str).child(this.firebaseUser.getUserInfo().uid).child(ChatConstants.ARG_USER_INFO).child(str2);
        Boolean valueOf = Boolean.valueOf(true);
        child.setValue(valueOf);
        String str3 = "roles";
        String str4 = "cris";
        String str5 = "";
        FirebaseDatabase.getInstance().getReference().child(str).child(this.firebaseUser.getUserInfo().uid).child(str3).child(str4).setValue(str5);
        FirebaseDatabase.getInstance().getReference().child(str).child(this.firebaseUser.getUserInfo().uid).child(str3).child(ChatConstants.ARG_USERS_ROLES_CHAT_PERMISSION_ID).setValue(str5).addOnSuccessListener(new OnSuccessListener<Void>() {
            public void onSuccess(Void voidR) {
                new Builder(UserProfileActivity.this).content((int) R.string.user_blocked).positiveText((int) R.string.OK).show();
                UserProfileActivity.this.setStatus();
                UserProfileActivity.this.firebaseChatHelper.logActivity(new LogModel(FirebaseAuth.getInstance().getUid(), UserProfileActivity.this.firebaseUser.getUserInfo().uid, 2));
                UserProfileActivity.this.firebaseChatHelper.sendPushNotificationInfo(UserProfileActivity.this.getString(R.string.ban_push), UserProfileActivity.this.firebaseUser.getUserInfo().uid, UserProfileActivity.this.firebaseUser.getFirebaseToken(), 2);
            }
        });
        FirebaseDatabase.getInstance().getReference().child(ChatConstants.ARG_BANNED_USERS).child(this.firebaseUser.getUserInfo().uid).setValue(valueOf);
        DatabaseReference reference2 = FirebaseDatabase.getInstance().getReference();
        String str6 = ChatConstants.ARG_USER_LIST;
        reference2.child(str6).child(this.firebaseUser.getUserInfo().uid).child(str2).setValue(valueOf);
        FirebaseDatabase.getInstance().getReference().child(str6).child(this.firebaseUser.getUserInfo().uid).child(str4).setValue(Integer.valueOf(0));
    }

    public void unBanUser() {
        String str = "ib";
        DatabaseReference child = FirebaseDatabase.getInstance().getReference().child(ChatConstants.ARG_USER_LIST).child(this.firebaseUser.getUserInfo().uid).child(str);
        Boolean valueOf = Boolean.valueOf(false);
        child.setValue(valueOf);
        FirebaseDatabase.getInstance().getReference().child(ChatConstants.ARG_USERS).child(this.firebaseUser.getUserInfo().uid).child(ChatConstants.ARG_USER_INFO).child(str).setValue(valueOf).addOnSuccessListener(new OnSuccessListener<Void>() {
            public void onSuccess(Void voidR) {
                new Builder(UserProfileActivity.this).content((int) R.string.user_unblocked).positiveText((int) R.string.OK).show();
                UserProfileActivity.this.setStatus();
                UserProfileActivity.this.firebaseChatHelper.logActivity(new LogModel(FirebaseAuth.getInstance().getUid(), UserProfileActivity.this.firebaseUser.getUserInfo().uid, 3));
                UserProfileActivity.this.firebaseChatHelper.sendPushNotificationInfo(UserProfileActivity.this.getString(R.string.unban_push), UserProfileActivity.this.firebaseUser.getUserInfo().uid, UserProfileActivity.this.firebaseUser.getFirebaseToken(), 3);
            }
        });
        FirebaseDatabase.getInstance().getReference().child(ChatConstants.ARG_BANNED_USERS).child(this.firebaseUser.getUserInfo().uid).removeValue();
    }

    public void showStatusEditDialog() {
        new Builder(this).title((int) R.string.user_status).negativeText((int) R.string.cancel).inputRangeRes(1, 140, R.color.red).inputType(1).input((CharSequence) getString(R.string.user_status_hint), (CharSequence) this.firebaseUser.getUserInfo().status != null ? this.firebaseUser.getUserInfo().status : "", (InputCallback) new InputCallback() {
            public void onInput(MaterialDialog materialDialog, CharSequence charSequence) {
                UserProfileActivity.this.updateUserStatus(charSequence.toString());
            }
        }).show();
    }

    public void updateUserStatus(final String str) {
        if (FirebaseAuth.getInstance().getCurrentUser().getUid() != null) {
            FirebaseDatabase.getInstance().getReference().child(ChatConstants.ARG_USERS).child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child(ChatConstants.ARG_USER_INFO).child("s").setValue(str).addOnCompleteListener(new OnCompleteListener<Void>() {
                public void onComplete(Task<Void> task) {
                    FirebaseDatabase.getInstance().getReference().child(ChatConstants.ARG_USER_LIST).child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("s").setValue(str);
                    UserProfileActivity.this.firebaseUser.getUserInfo().status = str;
                    UserProfileActivity.this.setStatus();
                    new Builder(UserProfileActivity.this).content((int) R.string.user_status_updated).positiveText((int) R.string.OK).show();
                }
            });
        }
    }

    public void appendRoleToUser(final ChatRoleModel chatRoleModel) {
        if (chatRoleModel.getPermissionTypeID().equalsIgnoreCase(IcyHeaders.REQUEST_HEADER_ENABLE_METADATA_VALUE) || chatRoleModel.getPermissionTypeID().equalsIgnoreCase(ExifInterface.GPS_MEASUREMENT_2D)) {
            FirebaseDatabase.getInstance().getReference().child(ChatConstants.ARG_AUTHORITIES_USERS).child(this.firebaseUser.getUserInfo().uid).setValue(this.firebaseUser.getFirebaseToken());
        }
        String str = "cris";
        FirebaseDatabase.getInstance().getReference().child(ChatConstants.ARG_USER_LIST).child(this.firebaseUser.getUserInfo().uid).child(str).setValue(chatRoleModel.getId());
        FirebaseDatabase.getInstance().getReference().child(ChatConstants.ARG_USERS).child(this.firebaseUser.getUserInfo().uid).child("roles").child(str).setValue(chatRoleModel.getId()).addOnCompleteListener(new OnCompleteListener<Void>() {
            public void onComplete(Task<Void> task) {
                FirebaseDatabase.getInstance().getReference().child(ChatConstants.ARG_USERS).child(UserProfileActivity.this.firebaseUser.getUserInfo().uid).child("roles").child(ChatConstants.ARG_USERS_ROLES_CHAT_PERMISSION_ID).setValue(chatRoleModel.getPermissionType()).addOnCompleteListener(new OnCompleteListener<Void>() {
                    public void onComplete(Task<Void> task) {
                        UserProfileActivity.this.firebaseChatHelper.logActivity(new LogModel(FirebaseAuth.getInstance().getUid(), UserProfileActivity.this.firebaseUser.getUserInfo().uid, 4, chatRoleModel.getChatRoleName()));
                        UserProfileActivity.this.firebaseChatHelper.sendPushNotificationInfo(UserProfileActivity.this.getString(R.string.authority_push), UserProfileActivity.this.firebaseUser.getUserInfo().uid, UserProfileActivity.this.firebaseUser.getFirebaseToken(), 4);
                    }
                });
            }
        });
    }

    public void removeRole() {
        String str = "cris";
        FirebaseDatabase.getInstance().getReference().child(ChatConstants.ARG_USER_LIST).child(this.firebaseUser.getUserInfo().uid).child(str).removeValue();
        FirebaseDatabase.getInstance().getReference().child(ChatConstants.ARG_AUTHORITIES_USERS).child(this.firebaseUser.getUserInfo().uid).removeValue();
        FirebaseDatabase.getInstance().getReference().child(ChatConstants.ARG_USERS).child(this.firebaseUser.getUserInfo().uid).child("roles").child(str).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
            public void onComplete(Task<Void> task) {
                FirebaseDatabase.getInstance().getReference().child(ChatConstants.ARG_USERS).child(UserProfileActivity.this.firebaseUser.getUserInfo().uid).child("roles").child(ChatConstants.ARG_USERS_ROLES_CHAT_PERMISSION_ID).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                    public void onComplete(Task<Void> task) {
                        UserProfileActivity.this.firebaseChatHelper.logActivity(new LogModel(FirebaseAuth.getInstance().getUid(), UserProfileActivity.this.firebaseUser.getUserInfo().uid, 5));
                        UserProfileActivity.this.firebaseChatHelper.sendPushNotificationInfo(UserProfileActivity.this.getString(R.string.authority_revoke_push), UserProfileActivity.this.firebaseUser.getUserInfo().uid, UserProfileActivity.this.firebaseUser.getFirebaseToken(), 5);
                    }
                });
            }
        });
    }

    /* access modifiers changed from: private */
    public void setStatus() {
        if (!this.firebaseUser.getUserInfo().isBanned) {
            this.toolbarHeaderView.bindTo(this.firebaseUser.getUserInfo().name, this.firebaseUser.getUserInfo().status);
            this.floatHeaderView.bindTo(this.firebaseUser.getUserInfo().name, this.firebaseUser.getUserInfo().status);
            this.fabBlock.setTitle(getString(R.string.action_block));
            return;
        }
        this.toolbarHeaderView.bindTo(this.firebaseUser.getUserInfo().name, getString(R.string.blocked_by_app_manager));
        this.floatHeaderView.bindTo(this.firebaseUser.getUserInfo().name, getString(R.string.blocked_by_app_manager));
        this.fabBlock.setTitle(getString(R.string.action_unblock));
    }

    @Subscribe
    public void notifyUser(ChatUserProfileEvent chatUserProfileEvent) {
        ChatUserDetails userModel = chatUserProfileEvent.getUserModel();
        if (userModel != null && userModel.getUserInfo().uid.equalsIgnoreCase(this.mUserUid)) {
            this.firebaseUser = userModel;
            setFirebaseUserToAdapter();
            boolean z = true;
            if (this.firebaseUser.getUserInfo().imageUrl == null || this.firebaseUser.getUserInfo().imageUrl.equalsIgnoreCase("")) {
                this.userLogo.setVisibility(8);
                this.appBarLayout.setExpanded(false);
                this.isHideToolbarView = true;
            } else {
                Glide.with((FragmentActivity) this).load(this.firebaseUser.getUserInfo().imageUrl).into(this.userImage);
                Glide.with((FragmentActivity) this).load(this.firebaseUser.getUserInfo().imageUrl).into((ImageView) this.userLogo);
            }
            boolean userIsChatAdmin = this.sharedPrefHelper.getUserIsChatAdmin();
            String str = IcyHeaders.REQUEST_HEADER_ENABLE_METADATA_VALUE;
            if ((!userIsChatAdmin || (this.firebaseUser.getRoles().chatPermissionIdString != null && (this.firebaseUser.getRoles().chatPermissionIdString == null || this.firebaseUser.getRoles().chatPermissionIdString.equalsIgnoreCase(str)))) && !this.sharedPrefHelper.getUserIsChatSuperUser() && (!this.sharedPrefHelper.getUserIsChatMod() || (this.firebaseUser.getRoles().chatPermissionIdString != null && (this.firebaseUser.getRoles().chatPermissionIdString == null || this.firebaseUser.getRoles().chatPermissionIdString.equalsIgnoreCase(ExifInterface.GPS_MEASUREMENT_2D) || this.firebaseUser.getRoles().chatPermissionIdString.equalsIgnoreCase(str))))) {
                z = false;
            }
            if (z && !FirebaseAuth.getInstance().getCurrentUser().getUid().equalsIgnoreCase(this.firebaseUser.getUserInfo().uid)) {
                this.fab.setVisibility(0);
                this.fabBlock.setVisibility(0);
                this.fabRole.setVisibility(0);
                this.fab.setMenuButtonColor(this.sharedPrefHelper.getActionBarColor());
                this.fabBlock.setBackgroundColor(this.sharedPrefHelper.getActionBarColor());
                this.fabRole.setBackgroundColor(this.sharedPrefHelper.getActionBarColor());
                this.fabRole.setBackgroundTintList(ColorStateList.valueOf(this.sharedPrefHelper.getActionBarColor()));
                this.fabBlock.setBackgroundTintList(ColorStateList.valueOf(this.sharedPrefHelper.getActionBarColor()));
                this.fabRole.setTitleBackgroundColor(-1);
                this.fabBlock.setTitleTextColor(this.sharedPrefHelper.getActionBarColor());
                this.fabRole.setTitleTextColor(this.sharedPrefHelper.getActionBarColor());
                if (userModel.getUserInfo().isBanned) {
                    this.fabBlock.setTitle(getString(R.string.action_unblock));
                }
            }
            setStatus();
            invalidateOptionsMenu();
        }
    }

    private void blockOrUnBlockUser() {
        if (UserHelper.getUserBlockedUserList().contains(this.mUserUid)) {
            new Builder(this).content((int) R.string.chat_action_unblock_info).positiveText((int) R.string.chat_action_unblock).onPositive(new SingleButtonCallback() {
                public void onClick(MaterialDialog materialDialog, DialogAction dialogAction) {
                    UserProfileActivity.this.firebaseChatHelper.unBlockUser(UserProfileActivity.this.mUserUid, 1);
                }
            }).show();
        } else {
            new Builder(this).content((int) R.string.chat_action_block_info).positiveText((int) R.string.chat_action_block).onPositive(new SingleButtonCallback() {
                public void onClick(MaterialDialog materialDialog, DialogAction dialogAction) {
                    UserProfileActivity.this.firebaseChatHelper.blockUser(UserProfileActivity.this.mUserUid, 1);
                }
            }).show();
        }
    }

    private void setBlockText(MenuItem menuItem) {
        if (UserHelper.getUserBlockedUserList().contains(this.mUserUid)) {
            menuItem.setTitle(getString(R.string.chat_action_unblock));
        } else {
            menuItem.setTitle(getString(R.string.chat_action_block));
        }
    }

    @Subscribe
    public void onPostUserBlockEvent(UserBlockEvent userBlockEvent) {
        if (userBlockEvent.uid.equalsIgnoreCase(this.mUserUid) && userBlockEvent.from == 1) {
            invalidateOptionsMenu();
            if (userBlockEvent.isBlocked) {
                new Builder(this).content((int) R.string.chat_action_blocked_user).positiveText((int) R.string.OK).show();
            } else {
                new Builder(this).content((int) R.string.chat_action_unblocked_user).positiveText((int) R.string.OK).show();
            }
        }
    }

    /* access modifiers changed from: protected */
    public void onDestroy() {
        super.onDestroy();
        if (EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);
        }
    }
}
