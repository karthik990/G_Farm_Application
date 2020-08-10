package com.mobiroller.activities.menu;

import android.app.Activity;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.content.res.Configuration;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffColorFilter;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.core.view.ViewCompat;
import androidx.core.widget.ImageViewCompat;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.internal.NavigationMenuView;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.navigation.NavigationView.OnNavigationItemSelectedListener;
import com.infideap.drawerbehavior.Advance3DDrawerLayout;
import com.mobiroller.DynamicConstants;
import com.mobiroller.activities.aveChatView;
import com.mobiroller.activities.user.UserAddressActivity;
import com.mobiroller.activities.user.UserChangePasswordActivity;
import com.mobiroller.activities.user.UserLoginActivity;
import com.mobiroller.activities.user.UserOrderActivity;
import com.mobiroller.activities.user.UserUpdateActivity;
import com.mobiroller.constants.ChatConstants;
import com.mobiroller.constants.Constants;
import com.mobiroller.fragments.BackHandledFragment;
import com.mobiroller.fragments.aveWebViewFragment;
import com.mobiroller.fragments.aveYoutubeAdvancedViewFragment;
import com.mobiroller.helpers.AppSettingsHelper;
import com.mobiroller.helpers.InAppPurchaseHelper;
import com.mobiroller.helpers.ScreenHelper;
import com.mobiroller.helpers.ToolbarHelper;
import com.mobiroller.helpers.UserHelper;
import com.mobiroller.helpers.UtilManager;
import com.mobiroller.interfaces.ActivityComponent;
import com.mobiroller.mobi942763453128.R;
import com.mobiroller.models.NavigationItemModel;
import com.mobiroller.models.ScreenModel;
import com.mobiroller.models.chat.ChatNotificationModel;
import com.mobiroller.models.events.InAppPurchaseSuccessEvent;
import com.mobiroller.models.events.LoginEvent;
import com.mobiroller.models.events.OpenMenuModel;
import com.mobiroller.models.events.ProfileUpdateEvent;
import com.mobiroller.models.events.ShowToolbarEvent;
import com.mobiroller.util.ColorUtil;
import com.mobiroller.util.DialogUtil;
import com.mobiroller.util.ImageManager;
import com.mobiroller.util.MobirollerIntent;
import com.mobiroller.util.ServiceUtil.ServiceInterface;
import com.mobiroller.util.exceptions.IntentException;
import com.mobiroller.util.exceptions.UserFriendlyException;
import com.mobiroller.views.CircleImageView;
import com.mobiroller.views.custom.MobirollerToolbar;
import com.mobiroller.views.theme.Theme;
import java.util.ArrayList;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

public class SlidingMenu extends MenuActivity implements OnNavigationItemSelectedListener {
    public static final String SCALED_MENU = "scale";
    private static final float SCALE_PERCENTAGE = 0.8f;
    private int antiColor;
    /* access modifiers changed from: private */
    public boolean isItemClicked = false;
    private boolean isScaled = false;
    boolean isUserMenuLoaded = false;
    private String loginEventScreenId;
    @BindView(2131362303)
    Advance3DDrawerLayout mDrawerLayout;
    @BindView(2131362625)
    NavigationView mDrawerList;
    private ImageView mDropDownImage;
    private View mHeaderView;
    private ImageView mLoginImage;
    private Menu mNavigationMenu;
    /* access modifiers changed from: private */
    public CharSequence mTitle;
    private TextView mUserEmail;
    private CircleImageView mUserImageView;
    private TextView mUserName;
    /* access modifiers changed from: private */
    public int order = 0;
    @BindView(2131363270)
    MobirollerToolbar toolbar;
    private ImageView userDividerView;

    /* access modifiers changed from: 0000 */
    public int getLayoutResource() {
        return R.layout.menu_slider;
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        return true;
    }

    public void setSelectedFragment(BackHandledFragment backHandledFragment) {
    }

    /* access modifiers changed from: protected */
    public void onCreate(Bundle bundle) {
        this.toolbarHelper = new ToolbarHelper();
        this.toolbarHelper.setStatusBar(this);
        super.onCreate(bundle);
        ButterKnife.bind((Activity) this);
        this.isScaled = getIntent().hasExtra(SCALED_MENU);
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }
        loadUI();
        this.menuHelper.showLanguageDialogAndCheckPushAndIntro();
    }

    /* access modifiers changed from: 0000 */
    public void loadUI() {
        super.loadUI();
        if (this.isScaled) {
            if (getResources().getInteger(R.integer.locale_mirror_flip) == 180) {
                this.mDrawerLayout.setViewScale(GravityCompat.END, SCALE_PERCENTAGE);
            } else {
                this.mDrawerLayout.setViewScale(GravityCompat.START, SCALE_PERCENTAGE);
            }
        }
        disableNavigationViewScrollbars(this.mDrawerList);
        this.mDrawerList.inflateMenu(R.menu.sliding_drawer_menu);
        this.toolbarHelper.setStatusBar(this);
        setSupportActionBar(this.toolbar);
        this.toolbar.setNavigationOnClickListener(this);
        setMobirollerToolbar(this.toolbar);
        this.mAppBarLayout = (AppBarLayout) findViewById(R.id.app_bar_layout);
        this.mNavigationMenu = this.mDrawerList.getMenu();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        C22531 r2 = new ActionBarDrawerToggle(this, this.mDrawerLayout, this.toolbar, R.string.app_name, R.string.app_name) {
            public void onDrawerClosed(View view) {
                if (SlidingMenu.this.isItemClicked) {
                    if (SlidingMenu.this.progressViewHelper != null && !SlidingMenu.this.isFinishing()) {
                        SlidingMenu.this.progressViewHelper.show();
                    }
                    SlidingMenu slidingMenu = SlidingMenu.this;
                    slidingMenu.fetchScreenDetails(slidingMenu, slidingMenu.order);
                    SlidingMenu.this.isItemClicked = false;
                }
                if (!(SlidingMenu.this.fragment instanceof aveYoutubeAdvancedViewFragment)) {
                    SlidingMenu.this.toolbar.setTitle(SlidingMenu.this.mTitle);
                }
                SlidingMenu.this.invalidateOptionsMenu();
            }

            public void onDrawerOpened(View view) {
                if (!(SlidingMenu.this.fragment instanceof aveYoutubeAdvancedViewFragment)) {
                    SlidingMenu.this.toolbar.setTitle(SlidingMenu.this.mTitle);
                }
                SlidingMenu.this.invalidateOptionsMenu();
            }
        };
        this.mDrawerToggle = r2;
        int i = -1;
        this.mDrawerToggle.getDrawerArrowDrawable().setColor(ColorUtil.isColorDark(Theme.primaryColor) ? -1 : ViewCompat.MEASURED_STATE_MASK);
        this.mDrawerLayout.addDrawerListener(this.mDrawerToggle);
        this.mDrawerToggle.syncState();
        int i2 = Theme.primaryColor;
        this.antiColor = ColorUtil.isColorDark(Theme.primaryColor) ? -1 : ViewCompat.MEASURED_STATE_MASK;
        if (this.navigationModel.getMenuBackgroundColor() != null) {
            i2 = ScreenHelper.setColorWithNoAlpha(this.navigationModel.getMenuBackgroundColor());
            if (!ColorUtil.isColorDark(i2)) {
                i = ViewCompat.MEASURED_STATE_MASK;
            }
            this.antiColor = i;
        }
        this.mDrawerList.setBackgroundColor(i2);
        this.mDrawerList.setItemIconTintList(null);
        this.mDrawerList.setNavigationItemSelectedListener(this);
        this.mDrawerList.setItemTextColor(ColorStateList.valueOf(this.antiColor));
        this.mDrawerList.getMenu().setGroupCheckable(R.id.group_menu_items, true, true);
        Intent intent = getIntent();
        String str = ChatConstants.ARG_NOTIFICATION_MODEL;
        if (intent.hasExtra(str)) {
            this.chatNotificationModel = (ChatNotificationModel) getIntent().getSerializableExtra(str);
            Intent intent2 = new Intent(this, aveChatView.class);
            intent2.putExtra(str, this.chatNotificationModel);
            startActivity(intent2);
        }
        loadUserView();
    }

    public void onBackPressed() {
        if (!this.mDrawerLayout.isDrawerOpen((int) GravityCompat.START)) {
            this.mDrawerLayout.openDrawer((int) GravityCompat.START);
        } else if (this.sharedPrefHelper.getAskBeforeExit()) {
            DialogUtil.showExitDialog(this, this);
        } else {
            super.onBackPressed();
        }
    }

    public boolean onOptionsItemSelected(MenuItem menuItem) {
        if (menuItem.getItemId() != 16908332) {
            return super.onOptionsItemSelected(menuItem);
        }
        this.mDrawerLayout.openDrawer((int) GravityCompat.START);
        return true;
    }

    public AppCompatActivity injectActivity(ActivityComponent activityComponent) {
        activityComponent.inject(this);
        return this;
    }

    public void setTitle(CharSequence charSequence) {
        if (!(this.fragment instanceof aveYoutubeAdvancedViewFragment)) {
            this.mTitle = charSequence;
            this.toolbar.setTitle(this.mTitle);
        }
    }

    /* access modifiers changed from: protected */
    public void onPostCreate(Bundle bundle) {
        super.onPostCreate(bundle);
        this.mDrawerToggle.syncState();
    }

    public void onConfigurationChanged(Configuration configuration) {
        super.onConfigurationChanged(configuration);
        this.mDrawerToggle.onConfigurationChanged(configuration);
    }

    private void resetUserMenu() {
        if (!this.isUserMenuLoaded) {
            this.mNavigationMenu.add(R.id.group_user_items, 1000, 1000, R.string.profile).setIcon(R.drawable.ic_person_white_24dp);
            if (AppSettingsHelper.isECommerceActive()) {
                this.mNavigationMenu.add(R.id.group_user_items, 1004, 1001, R.string.e_commerce_my_orders_title).setIcon(R.drawable.ic_local_shipping_white_24dp);
                this.mNavigationMenu.add(R.id.group_user_items, 1005, 1002, R.string.user_my_address_title).setIcon(R.drawable.ic_place_white_24dp);
                this.mNavigationMenu.add(R.id.group_user_items, 1001, 1003, R.string.change_password).setIcon(R.drawable.ic_lock_white_36dp);
                this.mNavigationMenu.add(R.id.group_user_items, 1002, 1004, R.string.logout).setIcon(R.drawable.ic_exit_to_app_white_36dp);
                this.mNavigationMenu.add(R.id.group_user_items, 1003, 1005, R.string.return_to_menu).setIcon(R.drawable.ic_reply_white_24dp);
            } else {
                this.mNavigationMenu.add(R.id.group_user_items, 1001, 1001, R.string.change_password).setIcon(R.drawable.ic_lock_white_36dp);
                this.mNavigationMenu.add(R.id.group_user_items, 1002, 1002, R.string.logout).setIcon(R.drawable.ic_exit_to_app_white_36dp);
                this.mNavigationMenu.add(R.id.group_user_items, 1003, 1003, R.string.return_to_menu).setIcon(R.drawable.ic_reply_white_24dp);
            }
            this.mNavigationMenu.setGroupVisible(R.id.group_menu_items, false);
            this.isUserMenuLoaded = true;
        }
    }

    /* access modifiers changed from: private */
    public void setUserMenu() {
        if (this.mDropDownImage.getRotation() != 0.0f || !this.sharedPrefHelper.getUserLoginStatus()) {
            this.mDrawerList.setItemIconTintList(null);
            this.mDropDownImage.setRotation(0.0f);
            this.mDrawerList.getMenu().setGroupVisible(R.id.group_user_items, false);
            this.mDrawerList.getMenu().setGroupVisible(R.id.group_menu_items, true);
            return;
        }
        this.mDrawerList.setItemIconTintList(ColorStateList.valueOf(this.antiColor));
        this.mDropDownImage.setRotation(180.0f);
        this.mDrawerList.getMenu().setGroupVisible(R.id.group_menu_items, false);
        this.mDrawerList.getMenu().setGroupVisible(R.id.group_user_items, true);
    }

    private void displayUserView(int i) {
        if (i == 1000) {
            startActivity(new Intent(this, UserUpdateActivity.class));
        }
        if (AppSettingsHelper.isECommerceActive()) {
            if (i == 1001) {
                startActivity(new Intent(this, UserOrderActivity.class));
            } else if (i == 1002) {
                startActivity(new Intent(this, UserAddressActivity.class));
            } else if (i == 1003) {
                startActivity(new Intent(this, UserChangePasswordActivity.class));
            } else if (i == 1004) {
                DialogUtil.showLogoutDialog(this, this);
            } else if (i == 1005) {
                new Handler().postDelayed(new Runnable() {
                    public void run() {
                        SlidingMenu.this.setUserMenu();
                    }
                }, 500);
            }
        } else if (i == 1001) {
            startActivity(new Intent(this, UserChangePasswordActivity.class));
        } else if (i == 1002) {
            DialogUtil.showLogoutDialog(this, this);
        } else if (i == 1003) {
            new Handler().postDelayed(new Runnable() {
                public void run() {
                    SlidingMenu.this.setUserMenu();
                }
            }, 500);
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void updateEvent(ProfileUpdateEvent profileUpdateEvent) {
        if (profileUpdateEvent.getProfileImageURL() != null) {
            ImageManager.loadUserImage(this.mUserImageView);
        }
        if (profileUpdateEvent.getUserName() != null) {
            this.mUserName.setText(profileUpdateEvent.getUserName());
        }
    }

    public void onActivityResult(int i, int i2, Intent intent) {
        super.onActivityResult(i, i2, intent);
        if (this.fragment != null) {
            this.fragment.onActivityResult(i, i2, intent);
        }
        if (i == 645 && i2 == -1) {
            loadUserView();
        }
    }

    /* access modifiers changed from: 0000 */
    public void fetchScreenDetails(ServiceInterface serviceInterface, int i) {
        if (this.progressViewHelper != null && !isFinishing() && !this.progressViewHelper.isShowing()) {
            this.progressViewHelper.show();
        }
        super.fetchScreenDetails(serviceInterface, i);
    }

    private void startAction(final int i) {
        getWindow().getDecorView().setSystemUiVisibility(0);
        this.toolbarHelper.setStatusBar(this);
        if (this.interstitialAdsUtil.checkInterstitialAds(this.screenModel.getFragment(), this.screenModel, (NavigationItemModel) this.validNavItems.get(i))) {
            this.fragment = this.screenModel.getFragment();
            if ((this.fragment instanceof aveYoutubeAdvancedViewFragment) || ((this.fragment instanceof aveWebViewFragment) && this.screenModel.isHideToolbar())) {
                this.toolbar.setVisibility(8);
            } else {
                setSupportActionBar(this.toolbar);
                this.toolbar.setNavigationOnClickListener(new OnClickListener() {
                    public void onClick(View view) {
                        SlidingMenu.this.onPostOpenMenuEvent(new OpenMenuModel());
                    }
                });
                this.toolbar.setVisibility(0);
            }
            runOnUiThread(new Runnable() {
                public void run() {
                    SlidingMenu.this.setTitle(UtilManager.localizationHelper().getLocalizedTitle(((NavigationItemModel) SlidingMenu.this.validNavItems.get(i)).getTitle()));
                    SlidingMenu.this.mDrawerList.setCheckedItem(i);
                    SlidingMenu.this.mDrawerList.getMenu().getItem(i).setChecked(true);
                    SlidingMenu.this.progressViewHelper.dismiss();
                }
            });
        }
    }

    @Subscribe
    public void onPostInAppPurchaseSuccessEvent(InAppPurchaseSuccessEvent inAppPurchaseSuccessEvent) {
        for (int i = 0; i < this.validNavItems.size(); i++) {
            if (((NavigationItemModel) this.validNavItems.get(i)).getAccountScreenID().equalsIgnoreCase(inAppPurchaseSuccessEvent.screenId)) {
                fetchScreenDetails(this, i);
                return;
            }
        }
    }

    @Subscribe
    public void onPostOpenMenuEvent(OpenMenuModel openMenuModel) {
        this.mDrawerLayout.openDrawer((int) GravityCompat.START);
    }

    private void dismissProgress() {
        if (this.progressViewHelper != null && !isFinishing() && this.progressViewHelper.isShowing()) {
            this.progressViewHelper.dismiss();
        }
    }

    /* access modifiers changed from: protected */
    public void onStop() {
        super.onStop();
        dismissProgress();
    }

    @Subscribe
    public void onPostShowToolbarEvent(ShowToolbarEvent showToolbarEvent) {
        this.toolbar.setVisibility(0);
        setSupportActionBar(this.toolbar);
        this.toolbarHelper.setStatusBar(this);
        this.toolbar.setNavigationOnClickListener(this);
    }

    public void onRequestPermissionsResult(int i, String[] strArr, int[] iArr) {
        super.onRequestPermissionsResult(i, strArr, iArr);
        this.fragment.onRequestPermissionsResult(i, strArr, iArr);
    }

    /* access modifiers changed from: 0000 */
    public void setLogInStatus() {
        if (this.sharedPrefHelper.getUserLoginActive()) {
            if (UtilManager.sharedPrefHelper().getUserLoginStatus()) {
                this.mUserEmail.setText(UserHelper.getUserEmail());
                this.mDropDownImage.setVisibility(0);
                this.mLoginImage.setVisibility(8);
                this.mUserName.setText(UserHelper.getUserName());
                ImageManager.loadUserImage(this.mUserImageView);
            } else {
                this.mDropDownImage.setVisibility(8);
                this.mLoginImage.setVisibility(0);
                this.mUserName.setText(getString(R.string.guest));
                this.mUserEmail.setText(getString(R.string.not_login));
                this.mUserImageView.setImageResource(R.drawable.ic_account_circle_black_36dp);
                this.mUserImageView.setColorFilter(new PorterDuffColorFilter(this.antiColor, Mode.MULTIPLY));
            }
            this.mUserEmail.setTextColor(this.antiColor);
            this.mUserName.setTextColor(this.antiColor);
            ImageViewCompat.setImageTintList(this.mDropDownImage, ColorStateList.valueOf(this.antiColor));
            this.userDividerView.setBackgroundColor(this.antiColor);
        }
    }

    /* access modifiers changed from: 0000 */
    public void loadBanner() {
        new Handler(Looper.getMainLooper()).post(new Runnable() {
            public void run() {
                SlidingMenu.this.sharedPrefHelper.setBannerAd(SlidingMenu.this.getApplicationContext());
            }
        });
    }

    private void disableNavigationViewScrollbars(NavigationView navigationView) {
        if (navigationView != null) {
            NavigationMenuView navigationMenuView = (NavigationMenuView) navigationView.getChildAt(0);
            if (navigationMenuView != null) {
                navigationMenuView.setVerticalScrollBarEnabled(false);
            }
        }
    }

    /* access modifiers changed from: 0000 */
    public void loadValidNavigationItemsView() {
        boolean z;
        ArrayList arrayList = new ArrayList();
        if (this.navigationItemModels == null || this.navigationItemModels.size() == 0) {
            this.menuHelper.showErrorMessage(getString(R.string.empty_content_warning));
            return;
        }
        this.validNavItems = getValidItems();
        if (this.validNavItems.size() == 0) {
            startActivityForResult(new Intent(this, UserLoginActivity.class).putExtra(Constants.INTENT_EXTRA_IS_BACK_AVAILABLE, false), UserLoginActivity.USER_LOGIN_REQUEST);
            return;
        }
        for (int i = 0; i < this.validNavItems.size(); i++) {
            MenuItem add = this.mNavigationMenu.add(R.id.group_menu_items, i, i, UtilManager.localizationHelper().getLocalizedTitle(((NavigationItemModel) this.validNavItems.get(i)).getTitle()));
            add.setCheckable(true);
            arrayList.add(add);
        }
        ImageManager.loadSlidingMenuIcons(this, arrayList, this.validNavItems);
        if (this.loginEventScreenId == null) {
            fetchScreenDetails(this, 0);
        } else {
            int i2 = 0;
            while (true) {
                if (i2 >= this.validNavItems.size()) {
                    z = false;
                    break;
                } else if (((NavigationItemModel) this.validNavItems.get(i2)).getAccountScreenID().equalsIgnoreCase(this.loginEventScreenId)) {
                    fetchScreenDetails(this, i2);
                    z = true;
                    break;
                } else {
                    i2++;
                }
            }
            if (!z) {
                fetchScreenDetails(this, 0);
            }
        }
        this.mDrawerList.setItemIconTintList(null);
        ImageView imageView = this.mDropDownImage;
        if (imageView != null) {
            imageView.setRotation(0.0f);
        }
        this.mDrawerList.getMenu().setGroupVisible(R.id.group_user_items, false);
        this.mDrawerList.getMenu().setGroupVisible(R.id.group_menu_items, true);
    }

    /* access modifiers changed from: 0000 */
    public void loadUserView() {
        if (UtilManager.sharedPrefHelper().getUserLoginActive() && this.mHeaderView == null) {
            this.mHeaderView = LayoutInflater.from(this).inflate(R.layout.layout_slider_menu_header, null);
            this.mDrawerList.addHeaderView(this.mHeaderView);
            this.mUserImageView = (CircleImageView) this.mHeaderView.findViewById(R.id.header_user_image);
            this.mHeaderView.findViewById(R.id.header_main_layout).setOnClickListener(this);
            this.mUserName = (TextView) this.mHeaderView.findViewById(R.id.header_user_name);
            this.mUserEmail = (TextView) this.mHeaderView.findViewById(R.id.header_user_email);
            this.mDropDownImage = (ImageView) this.mHeaderView.findViewById(R.id.header_drop_down);
            this.mLoginImage = (ImageView) this.mHeaderView.findViewById(R.id.header_login_icon);
            this.userDividerView = (ImageView) this.mHeaderView.findViewById(R.id.user_divider_view);
        }
        setLogInStatus();
        this.mDrawerList.getMenu().clear();
        this.isUserMenuLoaded = false;
        resetUserMenu();
        loadValidNavigationItemsView();
    }

    /* access modifiers changed from: 0000 */
    public void setFragment(ScreenModel screenModel, int i, int i2) {
        dismissProgress();
        if (screenModel != null && screenModel.getFragment() != null) {
            this.screenModel = screenModel;
            if (!InAppPurchaseHelper.checkIsInAppPurchaseValid(this) || !InAppPurchaseHelper.checkScreenIsInPurchaseList(String.valueOf(((NavigationItemModel) this.validNavItems.get(i)).getAccountScreenID()))) {
                startAction(i);
            } else if (isScreenPurchased(((NavigationItemModel) this.validNavItems.get(i)).getAccountScreenID())) {
                startAction(i);
            } else {
                MobirollerIntent.startInAppPurchaseActivity(this, String.valueOf(((NavigationItemModel) this.validNavItems.get(i)).getAccountScreenID()), ((NavigationItemModel) this.validNavItems.get(i)).getScreenType(), false);
            }
        } else if (i2 != 2 && UtilManager.networkHelper().isConnected() && screenModel == null) {
            setFragment(this.apiRequestManager.getScreenJSON(((NavigationItemModel) this.validNavItems.get(i)).getAccountScreenID()), i, i2 + 1);
        } else if (screenModel == null) {
            MobirollerIntent.startConnectionRequiredActivity(this, (NavigationItemModel) this.validNavItems.get(i));
        }
    }

    public void onClick(View view) {
        if (view.getId() == R.id.toolbar) {
            onPostOpenMenuEvent(new OpenMenuModel());
        } else if (view.getId() != R.id.header_main_layout) {
        } else {
            if (this.sharedPrefHelper.getUserLoginStatus()) {
                setUserMenu();
            } else {
                startActivityForResult(new Intent(this, UserLoginActivity.class), UserLoginActivity.USER_LOGIN_REQUEST);
            }
        }
    }

    public boolean onNavigationItemSelected(MenuItem menuItem) {
        ImageView imageView = this.mDropDownImage;
        if (imageView == null || imageView.getRotation() == 0.0f) {
            this.order = menuItem.getOrder();
            this.mDrawerLayout.closeDrawer((int) GravityCompat.START);
            this.isItemClicked = true;
        } else {
            displayUserView(menuItem.getOrder());
        }
        return false;
    }

    public void onUserLogout() {
        loadUserView();
        setUserMenu();
    }

    public void onScreenModelFetched(ScreenModel screenModel, int i) {
        setFragment(screenModel, i, 0);
    }

    public void onThrowUserFriendlyException(UserFriendlyException userFriendlyException) {
        userFriendlyException.showDialog(this);
    }

    public void onThrowIntentException(IntentException intentException) {
        startActivity(intentException.getIntent());
    }

    public void onExitApp() {
        if (!DynamicConstants.MobiRoller_Stage) {
            finishAffinity();
            System.exit(0);
            return;
        }
        super.onBackPressed();
    }

    @Subscribe
    public void loginEvent(LoginEvent loginEvent) {
        invalidateOptionsMenu();
        loadUserView();
        dismissProgress();
    }
}
