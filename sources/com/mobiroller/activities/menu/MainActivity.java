package com.mobiroller.activities.menu;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.os.StrictMode.ThreadPolicy.Builder;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.afollestad.materialdialogs.MaterialDialog;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.bottomnavigation.BottomNavigationView.OnNavigationItemSelectedListener;
import com.mobiroller.DynamicConstants;
import com.mobiroller.MobiRollerApplication;
import com.mobiroller.activities.ConnectionRequired;
import com.mobiroller.activities.aveChatView;
import com.mobiroller.activities.base.AveMenuActivity;
import com.mobiroller.activities.user.UserAddressActivity;
import com.mobiroller.activities.user.UserChangePasswordActivity;
import com.mobiroller.activities.user.UserLoginActivity;
import com.mobiroller.activities.user.UserOrderActivity;
import com.mobiroller.activities.user.UserUpdateActivity;
import com.mobiroller.adapters.MainListAdapter;
import com.mobiroller.constants.ChatConstants;
import com.mobiroller.constants.Constants;
import com.mobiroller.fragments.BackHandledFragment;
import com.mobiroller.fragments.BackHandledFragment.BackHandlerInterface;
import com.mobiroller.fragments.aveWebViewFragment;
import com.mobiroller.helpers.ApiRequestManager;
import com.mobiroller.helpers.AppSettingsHelper;
import com.mobiroller.helpers.BannerHelper;
import com.mobiroller.helpers.InAppPurchaseHelper;
import com.mobiroller.helpers.JSONParser;
import com.mobiroller.helpers.JSONStorage;
import com.mobiroller.helpers.LocalizationHelper;
import com.mobiroller.helpers.MenuHelper;
import com.mobiroller.helpers.NetworkHelper;
import com.mobiroller.helpers.ProgressViewHelper;
import com.mobiroller.helpers.RequestHelper;
import com.mobiroller.helpers.ScreenHelper;
import com.mobiroller.helpers.SharedPrefHelper;
import com.mobiroller.helpers.ToolbarHelper;
import com.mobiroller.helpers.UserHelper;
import com.mobiroller.interfaces.ActivityComponent;
import com.mobiroller.mobi942763453128.R;
import com.mobiroller.models.NavigationItemModel;
import com.mobiroller.models.NavigationModel;
import com.mobiroller.models.ScreenModel;
import com.mobiroller.models.chat.ChatNotificationModel;
import com.mobiroller.models.events.InAppPurchaseSuccessEvent;
import com.mobiroller.models.events.LoginEvent;
import com.mobiroller.models.events.ShowToolbarEvent;
import com.mobiroller.util.DateUtil;
import com.mobiroller.util.DialogUtil;
import com.mobiroller.util.DialogUtil.ExitDialog;
import com.mobiroller.util.ImageManager;
import com.mobiroller.util.InterstitialAdsUtil;
import com.mobiroller.util.MobirollerIntent;
import com.mobiroller.util.RssUtil;
import com.mobiroller.util.ScreenUtil;
import com.mobiroller.views.ItemClickSupport;
import com.mobiroller.views.ItemClickSupport.OnItemClickListener;
import com.mobiroller.views.NavDrawerItem;
import com.mobiroller.views.SimpleDividerItemDecoration;
import com.mobiroller.views.custom.MobirollerToolbar;
import com.mobiroller.views.theme.Theme;
import java.util.ArrayList;
import javax.inject.Inject;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AveMenuActivity implements BackHandlerInterface, ExitDialog {
    private MainListAdapter adapter;
    @Inject
    ApiRequestManager apiRequestManager;
    @Inject
    MobiRollerApplication app;
    @Inject
    BannerHelper bannerHelper;
    @BindView(2131362791)
    BottomNavigationView bottomNavigationView;
    private MaterialDialog dialog;
    private Fragment fragment = null;
    @BindView(2131362460)
    FrameLayout frameContainer;
    private boolean hasValidMenuItems = true;
    @Inject
    ImageManager imageManager;
    private InterstitialAdsUtil interstitialAdsUtil;
    private boolean isAdLoaded;
    @Inject
    JSONParser jsonParser;
    @Inject
    LocalizationHelper localizationHelper;
    @BindView(2131362649)
    LinearLayout mainLayout;
    @Inject
    MenuHelper menuHelper;
    private ArrayList<NavDrawerItem> menuItems = new ArrayList<>();
    @BindView(2131362646)
    RecyclerView menuList;
    @Inject
    NetworkHelper networkHelper;
    ProgressViewHelper progressViewHelper;
    @Inject
    ScreenHelper screenHelper;
    private BackHandledFragment selectedFragment;
    @Inject
    SharedPrefHelper sharedPrefHelper;
    /* access modifiers changed from: private */
    public MobirollerToolbar toolbar;
    @Inject
    ToolbarHelper toolbarHelper;
    private ArrayList<NavigationItemModel> validNavItems;

    public boolean onPrepareOptionsMenu(Menu menu) {
        return true;
    }

    public AppCompatActivity injectActivity(ActivityComponent activityComponent) {
        activityComponent.inject(this);
        return this;
    }

    /* access modifiers changed from: protected */
    public void onCreate(Bundle bundle) {
        StrictMode.setThreadPolicy(new Builder().permitAll().build());
        super.onCreate(bundle);
        setContentView((int) R.layout.activity_main);
        ButterKnife.bind((Activity) this);
        this.progressViewHelper = new ProgressViewHelper((AppCompatActivity) this);
        this.interstitialAdsUtil = new InterstitialAdsUtil((AppCompatActivity) this);
        if (this.networkHelper.isConnected()) {
            this.menuHelper.checkRateStatus();
        }
        this.toolbarHelper.setStatusBar(this);
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }
        this.toolbar = (MobirollerToolbar) findViewById(R.id.toolbar_top);
        this.mAppBarLayout = (AppBarLayout) findViewById(R.id.app_bar_layout);
        setMobirollerToolbar(this.toolbar);
        setSupportActionBar(this.toolbar);
        this.toolbar.setTitleMarginStart(ScreenUtil.getWidthForDevice(20));
        String str = "";
        this.toolbar.setTitle((CharSequence) str);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle((CharSequence) str);
        this.toolbarHelper.setStatusBar(this);
        this.progressViewHelper.show();
        this.progressViewHelper.setCancelableOnCancel();
        if (this.networkHelper.isConnected()) {
            this.menuHelper.sendToken();
        }
        this.navigationModel = (NavigationModel) getIntent().getSerializableExtra("localObj");
        this.showIntro = getIntent().getBooleanExtra(Constants.INTENT_EXTRA_INTRO_MESSAGE, false);
        this.navigationItemModels = this.navigationModel.getNavigationItems();
        this.pushNotified = getIntent().getBooleanExtra("pushNotified", false);
        try {
            ImageManager.loadBackgroundImage(this.navigationModel.getBackgroundImage().getImageURL(), this.menuList);
        } catch (Exception unused) {
        }
        if (this.navigationItemModels.size() == 0) {
            this.menuHelper.showErrorMessage(getString(R.string.empty_content_warning));
            return;
        }
        setMenuList();
        if (this.hasValidMenuItems) {
            if (bundle == null) {
                displayViewImprove(0);
            }
            Intent intent = getIntent();
            String str2 = ChatConstants.ARG_NOTIFICATION_MODEL;
            if (intent.hasExtra(str2)) {
                this.chatNotificationModel = (ChatNotificationModel) getIntent().getSerializableExtra(str2);
                Intent intent2 = new Intent(this, aveChatView.class);
                intent2.putExtra(str2, this.chatNotificationModel);
                startActivity(intent2);
            }
            this.menuHelper.showLanguageDialogAndCheckPushAndIntro();
        }
    }

    public void onBackPressed() {
        if (this.sharedPrefHelper.getAskBeforeExit()) {
            MaterialDialog materialDialog = this.dialog;
            if (materialDialog == null || !materialDialog.isShowing()) {
                this.dialog = DialogUtil.showExitDialog(this, this);
                return;
            }
            return;
        }
        super.onBackPressed();
    }

    private ScreenModel getLocalScreenModel(NavigationItemModel navigationItemModel, int i) {
        return this.jsonParser.getLocalScreenModel(this, navigationItemModel.getAccountScreenID());
    }

    public void displayViewImprove(int i) {
        NavigationItemModel navigationItemModel = (NavigationItemModel) this.validNavItems.get(i);
        if (this.progressViewHelper != null && !isFinishing() && !this.progressViewHelper.isShowing()) {
            this.progressViewHelper.show();
        }
        if (!DynamicConstants.MobiRoller_Stage) {
            this.screenModel = getLocalScreenModel(navigationItemModel, i);
            if (!this.networkHelper.isConnected()) {
                displayView(this.menuHelper.getFragment(navigationItemModel, this.screenModel, this), i);
            } else if (this.screenModel == null || this.screenModel.getUpdateDate() == null || !DateUtil.dateControlString(this.screenModel.getUpdateDate(), navigationItemModel.getUpdateDate())) {
                getLiveScreenModelAsync(navigationItemModel, i);
            } else {
                displayView(this.menuHelper.getFragment(navigationItemModel, this.screenModel, this), i);
            }
        } else if (this.networkHelper.isConnected()) {
            getLiveScreenModelAsync(navigationItemModel, i);
        } else {
            startNoConnectionActivity(navigationItemModel);
        }
    }

    private void startNoConnectionActivity(NavigationItemModel navigationItemModel) {
        runOnUiThread(new Runnable() {
            public void run() {
                MainActivity.this.progressViewHelper.dismiss();
            }
        });
        Intent intent = new Intent(this, ConnectionRequired.class);
        intent.putExtra(Constants.KEY_SCREEN_ID, navigationItemModel.getAccountScreenID());
        intent.putExtra(Constants.KEY_SCREEN_TYPE, navigationItemModel.getScreenType());
        intent.putExtra(Constants.KEY_UPDATE_DATE, navigationItemModel.getUpdateDate());
        startActivity(intent);
    }

    public void getLiveScreenModelAsync(final NavigationItemModel navigationItemModel, final int i) {
        SharedPrefHelper sharedPrefHelper2 = this.sharedPrefHelper;
        new ApiRequestManager(sharedPrefHelper2, new RequestHelper(this, sharedPrefHelper2)).getScreenJSONAsync(navigationItemModel.getAccountScreenID()).enqueue(new Callback<ScreenModel>() {
            public void onResponse(Call<ScreenModel> call, Response<ScreenModel> response) {
                if (response.body() != null) {
                    MainActivity.this.screenModel = (ScreenModel) response.body();
                    JSONStorage.putScreenModel(navigationItemModel.getAccountScreenID(), MainActivity.this.screenModel);
                    MainActivity mainActivity = MainActivity.this;
                    mainActivity.screenModel = mainActivity.menuHelper.getFragment(navigationItemModel, MainActivity.this.screenModel, MainActivity.this);
                    MainActivity mainActivity2 = MainActivity.this;
                    mainActivity2.displayView(mainActivity2.screenModel, i);
                } else {
                    MainActivity.this.getLocalScreenModelAndDisplay(navigationItemModel, i);
                }
                MainActivity.this.progressViewHelper.dismiss();
            }

            public void onFailure(Call<ScreenModel> call, Throwable th) {
                MainActivity.this.getLocalScreenModelAndDisplay(navigationItemModel, i);
                MainActivity.this.progressViewHelper.dismiss();
            }
        });
    }

    public void getLocalScreenModelAndDisplay(NavigationItemModel navigationItemModel, int i) {
        if (DynamicConstants.MobiRoller_Stage) {
            startNoConnectionActivity(navigationItemModel);
            return;
        }
        this.screenModel = getLocalScreenModel(navigationItemModel, i);
        this.screenModel = this.menuHelper.getFragment(navigationItemModel, this.screenModel, this);
        displayView(this.screenModel, i);
    }

    /* access modifiers changed from: private */
    public void displayView(ScreenModel screenModel, int i) {
        if (this.progressViewHelper != null && !isFinishing() && !this.progressViewHelper.isShowing()) {
            this.progressViewHelper.show();
        }
        ScreenModel fragment2 = this.menuHelper.getFragment((NavigationItemModel) this.validNavItems.get(i), screenModel, this);
        if (fragment2 != null && fragment2.getFragment() != null) {
            if (!InAppPurchaseHelper.checkIsInAppPurchaseValid(this) || !InAppPurchaseHelper.checkScreenIsInPurchaseList(String.valueOf(((NavigationItemModel) this.validNavItems.get(i)).getAccountScreenID()))) {
                startAction(i);
            } else if (isScreenPurchased(((NavigationItemModel) this.validNavItems.get(i)).getAccountScreenID())) {
                startAction(i);
            } else {
                MobirollerIntent.startInAppPurchaseActivity(this, String.valueOf(((NavigationItemModel) this.validNavItems.get(i)).getAccountScreenID()), ((NavigationItemModel) this.validNavItems.get(i)).getScreenType(), false);
            }
            this.progressViewHelper.dismiss();
        } else if (this.networkHelper.isConnected() && fragment2 == null) {
            ScreenModel screenJSON = this.apiRequestManager.getScreenJSON(((NavigationItemModel) this.validNavItems.get(i)).getAccountScreenID());
            this.progressViewHelper.dismiss();
            displayView(screenJSON, i);
        } else if (fragment2 == null) {
            this.progressViewHelper.dismiss();
            Intent intent = new Intent(this, ConnectionRequired.class);
            intent.putExtra(Constants.KEY_SCREEN_ID, ((NavigationItemModel) this.validNavItems.get(i)).getAccountScreenID());
            intent.putExtra(Constants.KEY_SCREEN_TYPE, ((NavigationItemModel) this.validNavItems.get(i)).getScreenType());
            intent.putExtra(Constants.KEY_UPDATE_DATE, ((NavigationItemModel) this.validNavItems.get(i)).getUpdateDate());
            startActivity(intent);
        }
    }

    private void startAction(int i) {
        setSupportActionBar(this.toolbar);
        invalidateOptionsMenu();
        this.fragment = this.screenModel.getFragment();
        Fragment fragment2 = this.fragment;
        if (fragment2 != null) {
            if (fragment2.getArguments() == null) {
                this.fragment.setArguments(new Bundle());
            }
            this.fragment.getArguments().putBoolean(Constants.KEY_IS_FROM_CLASSIC_MENU, true);
            getWindow().getDecorView().setSystemUiVisibility(0);
            this.toolbarHelper.setStatusBar(this);
            if (!(this.fragment instanceof aveWebViewFragment) || !this.screenModel.isHideToolbar()) {
                runOnUiThread(new Runnable() {
                    public void run() {
                        MainActivity.this.toolbar.setVisibility(0);
                    }
                });
            } else {
                runOnUiThread(new Runnable() {
                    public void run() {
                        MainActivity.this.toolbar.setVisibility(8);
                    }
                });
            }
            if (((NavigationItemModel) this.validNavItems.get(i)).getScreenType().equalsIgnoreCase("aveChatView")) {
                Bundle arguments = this.fragment.getArguments();
                arguments.putSerializable("roles", ((NavigationItemModel) this.validNavItems.get(i)).getRoles());
                this.fragment.setArguments(arguments);
            }
            if (this.interstitialAdsUtil.checkInterstitialAds(this.fragment, this.screenModel, ((NavigationItemModel) this.validNavItems.get(i)).getAccountScreenID(), ((NavigationItemModel) this.validNavItems.get(i)).getScreenType(), ((NavigationItemModel) this.validNavItems.get(i)).screenSubtype, ((NavigationItemModel) this.validNavItems.get(i)).getUpdateDate())) {
                getSupportActionBar().setTitle((CharSequence) ((NavDrawerItem) this.menuItems.get(i)).getTitle());
                if (this.validNavItems.size() != 1) {
                    MainListAdapter mainListAdapter = this.adapter;
                    if (mainListAdapter != null) {
                        mainListAdapter.setSelectedPos(i);
                    }
                }
            }
        }
    }

    /* access modifiers changed from: protected */
    public void onResume() {
        super.onResume();
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        if (this.sharedPrefHelper.getUserLoginActive()) {
            if (this.sharedPrefHelper.getUserLoginStatus()) {
                this.toolbar.inflateMenu(R.menu.user_menu);
                if (AppSettingsHelper.isECommerceActive()) {
                    this.toolbar.getMenu().findItem(R.id.action_my_addresses).setVisible(true);
                    this.toolbar.getMenu().findItem(R.id.action_my_orders).setVisible(true);
                }
            } else {
                this.toolbar.inflateMenu(R.menu.user_login_menu);
            }
        }
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case R.id.action_change_password /*2131361876*/:
                startActivity(new Intent(this, UserChangePasswordActivity.class));
                return true;
            case R.id.action_login /*2131361890*/:
                startActivity(new Intent(this, UserLoginActivity.class));
                return true;
            case R.id.action_logout /*2131361891*/:
                UserHelper.logout(this.sharedPrefHelper, this);
                return true;
            case R.id.action_my_addresses /*2131361897*/:
                startActivity(new Intent(this, UserAddressActivity.class));
                return true;
            case R.id.action_my_orders /*2131361898*/:
                startActivity(new Intent(this, UserOrderActivity.class));
                return true;
            case R.id.action_profile /*2131361899*/:
                startActivity(new Intent(this, UserUpdateActivity.class));
                return true;
            default:
                return super.onOptionsItemSelected(menuItem);
        }
    }

    @Subscribe
    public void loginEvent(LoginEvent loginEvent) {
        invalidateOptionsMenu();
        setMenuList();
        displayViewImprove(0);
    }

    public void setSelectedFragment(BackHandledFragment backHandledFragment) {
        this.selectedFragment = backHandledFragment;
    }

    public void setMenuList() {
        if (!(this.navigationItemModels == null || this.navigationItemModels.size() == 0)) {
            this.validNavItems = getValidItems();
            if (this.validNavItems.size() == 0) {
                startActivity(new Intent(this, UserLoginActivity.class).putExtra(Constants.INTENT_EXTRA_IS_BACK_AVAILABLE, false));
                this.hasValidMenuItems = false;
                return;
            }
            this.hasValidMenuItems = true;
            this.menuItems = new ArrayList<>();
            String[] strArr = new String[this.validNavItems.size()];
            for (int i = 0; i < this.validNavItems.size(); i++) {
                NavigationItemModel navigationItemModel = (NavigationItemModel) this.validNavItems.get(i);
                strArr[i] = this.localizationHelper.getLocalizedTitle(navigationItemModel.getTitle());
                this.menuItems.add(new NavDrawerItem(strArr[i], navigationItemModel.getIconImage() != null ? navigationItemModel.getIconImage().getImageURL() : "null", this.navigationModel.getMenuTextColor().getColor()));
            }
            if (this.menuItems.size() == 1) {
                getSupportActionBar().hide();
                this.menuList.setVisibility(8);
            } else {
                setBottomMenu();
            }
        }
    }

    public void onActivityResult(int i, int i2, Intent intent) {
        super.onActivityResult(i, i2, intent);
        Fragment fragment2 = this.fragment;
        if (fragment2 != null) {
            fragment2.onActivityResult(i, i2, intent);
        }
    }

    @Subscribe
    public void onPostInAppPurchaseSuccessEvent(InAppPurchaseSuccessEvent inAppPurchaseSuccessEvent) {
        for (int i = 0; i < this.validNavItems.size(); i++) {
            if (((NavigationItemModel) this.validNavItems.get(i)).getAccountScreenID().equalsIgnoreCase(inAppPurchaseSuccessEvent.screenId)) {
                displayViewImprove(i);
                return;
            }
        }
    }

    private void setBottomMenu() {
        if (this.validNavItems.size() < 5) {
            this.menuList.setVisibility(8);
            this.bottomNavigationView.setVisibility(0);
            setNavigationMenu();
            return;
        }
        this.menuList.setVisibility(0);
        this.bottomNavigationView.setVisibility(8);
        setListMenu();
    }

    private void setListMenu() {
        this.menuList.addItemDecoration(new SimpleDividerItemDecoration(getApplicationContext(), R.drawable.line_drawer));
        ItemClickSupport.addTo(this.menuList).setOnItemClickListener(new OnItemClickListener() {
            public void onItemClicked(RecyclerView recyclerView, int i, View view) {
                MainActivity.this.displayViewImprove(i);
            }
        });
        this.adapter = new MainListAdapter(this, this.menuItems);
        this.menuList.hasFixedSize();
        this.menuList.setItemViewCacheSize(30);
        this.menuList.setDrawingCacheEnabled(true);
        this.menuList.setDrawingCacheQuality(1048576);
        this.menuList.setAdapter(this.adapter);
        this.menuList.setLayoutManager(new LinearLayoutManager(this, 0, false));
        this.adapter.notifyDataSetChanged();
    }

    private void setNavigationMenu() {
        this.bottomNavigationView.getMenu().clear();
        if (this.navigationModel.getMenuBackgroundColor() == null || this.navigationModel.getMenuBackgroundColor().getColor() == 0) {
            this.bottomNavigationView.setBackgroundColor(Theme.primaryColor);
        } else {
            this.bottomNavigationView.setBackgroundColor(this.navigationModel.getMenuBackgroundColor().getColor());
        }
        for (int i = 0; i < this.menuItems.size(); i++) {
            this.bottomNavigationView.getMenu().add(0, i, 0, ((NavDrawerItem) this.menuItems.get(i)).getTitle());
        }
        this.bottomNavigationView.setItemIconTintList(null);
        ImageManager.loadMainMenuIcons(this, this.bottomNavigationView.getMenu(), this.validNavItems);
        for (int i2 = 0; i2 < this.bottomNavigationView.getMenu().size(); i2++) {
            MenuItem item = this.bottomNavigationView.getMenu().getItem(i2);
            SpannableString spannableString = new SpannableString(this.bottomNavigationView.getMenu().getItem(i2).getTitle().toString());
            spannableString.setSpan(new ForegroundColorSpan(this.navigationModel.getMenuTextColor().getColor()), 0, spannableString.length(), 0);
            item.setTitle(spannableString);
        }
        this.bottomNavigationView.setItemIconSize(RssUtil.convertDpToPixel(32.0f));
        this.bottomNavigationView.setOnNavigationItemSelectedListener(new OnNavigationItemSelectedListener() {
            public boolean onNavigationItemSelected(MenuItem menuItem) {
                MainActivity.this.displayViewImprove(menuItem.getItemId());
                return true;
            }
        });
    }

    @Subscribe
    public void onPostShowToolbarEvent(ShowToolbarEvent showToolbarEvent) {
        this.toolbar.setVisibility(0);
        setSupportActionBar(this.toolbar);
        this.toolbarHelper.setStatusBar(this);
    }

    public void onRequestPermissionsResult(int i, String[] strArr, int[] iArr) {
        super.onRequestPermissionsResult(i, strArr, iArr);
        this.fragment.onRequestPermissionsResult(i, strArr, iArr);
    }

    public void onExitApp() {
        if (!DynamicConstants.MobiRoller_Stage) {
            finishAffinity();
            System.exit(0);
            return;
        }
        super.onBackPressed();
    }
}
