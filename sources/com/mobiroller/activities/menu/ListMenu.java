package com.mobiroller.activities.menu;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.os.StrictMode.ThreadPolicy.Builder;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.mobiroller.DynamicConstants;
import com.mobiroller.MobiRollerApplication;
import com.mobiroller.activities.ActivityHandler;
import com.mobiroller.activities.aveChatView;
import com.mobiroller.activities.base.AveMenuActivity;
import com.mobiroller.activities.user.UserAddressActivity;
import com.mobiroller.activities.user.UserChangePasswordActivity;
import com.mobiroller.activities.user.UserLoginActivity;
import com.mobiroller.activities.user.UserOrderActivity;
import com.mobiroller.activities.user.UserUpdateActivity;
import com.mobiroller.adapters.ContentRecyclerAdapter;
import com.mobiroller.constants.ChatConstants;
import com.mobiroller.constants.Constants;
import com.mobiroller.helpers.AppSettingsHelper;
import com.mobiroller.helpers.BannerHelper;
import com.mobiroller.helpers.JSONParser;
import com.mobiroller.helpers.LayoutHelper;
import com.mobiroller.helpers.LocalizationHelper;
import com.mobiroller.helpers.MenuHelper;
import com.mobiroller.helpers.NetworkHelper;
import com.mobiroller.helpers.ProgressViewHelper;
import com.mobiroller.helpers.ScreenHelper;
import com.mobiroller.helpers.SharedPrefHelper;
import com.mobiroller.helpers.ToolbarHelper;
import com.mobiroller.interfaces.ActivityComponent;
import com.mobiroller.mobi942763453128.R;
import com.mobiroller.models.NavigationItemModel;
import com.mobiroller.models.NavigationModel;
import com.mobiroller.models.chat.ChatNotificationModel;
import com.mobiroller.models.events.LoginEvent;
import com.mobiroller.util.DialogUtil;
import com.mobiroller.util.DialogUtil.ExitDialog;
import com.mobiroller.util.DialogUtil.UserLogoutDialog;
import com.mobiroller.util.ImageManager;
import com.mobiroller.util.InterstitialAdsUtil;
import com.mobiroller.views.ItemClickSupport;
import com.mobiroller.views.ItemClickSupport.OnItemClickListener;
import com.mobiroller.views.custom.MobirollerToolbar;
import java.util.ArrayList;
import java.util.HashMap;
import javax.inject.Inject;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

public class ListMenu extends AveMenuActivity implements ExitDialog, UserLogoutDialog {
    public static final String KEY_IMAGE_URL = "img_url";
    public static final String KEY_SCREEN_ID = "screen_id";
    public static final String KEY_SCREEN_TYPE = "screen_type";
    public static final String KEY_TITLE = "title";
    @Inject
    MobiRollerApplication app;
    @Inject
    BannerHelper bannerHelper;
    private ArrayList<HashMap<String, String>> contentList = new ArrayList<>();
    @BindView(2131362417)
    FrameLayout flAdplaceholder;
    @Inject
    ImageManager imageManager;
    @BindView(2131362620)
    ImageView imgView;
    private InterstitialAdsUtil interstitialAdsUtil;
    private boolean isAdLoaded;
    @Inject
    JSONParser jsonParser;
    @Inject
    LayoutHelper layoutHelper;
    @BindView(2131362724)
    RecyclerView list;
    @BindView(2131362621)
    RelativeLayout listMenuLayout;
    @BindView(2131362622)
    RelativeLayout listMenuOverlay;
    @Inject
    LocalizationHelper localizationHelper;
    @Inject
    MenuHelper menuHelper;
    @BindView(2131362725)
    LinearLayout menuListLayout;
    @Inject
    NetworkHelper networkHelper;
    private ProgressViewHelper progressViewHelper;
    @Inject
    ScreenHelper screenHelper;
    @Inject
    SharedPrefHelper sharedPrefHelper;
    @BindView(2131363276)
    Toolbar toolbar;
    @Inject
    ToolbarHelper toolbarHelper;
    private int totalHeight = 0;
    /* access modifiers changed from: private */
    public ArrayList<NavigationItemModel> valiNavItems = new ArrayList<>();

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
        this.interstitialAdsUtil = new InterstitialAdsUtil((AppCompatActivity) this);
        setContentView((int) R.layout.list_menu_recycler);
        ButterKnife.bind((Activity) this);
        this.progressViewHelper = new ProgressViewHelper((AppCompatActivity) this);
        this.progressViewHelper.setCancelableOnCancel();
        this.toolbar.setBackgroundColor(0);
        this.toolbar.setTitle((CharSequence) "");
        setSupportActionBar(this.toolbar);
        new ToolbarHelper().setStatusBar(this);
        setMobirollerToolbar((MobirollerToolbar) findViewById(R.id.toolbar_top));
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }
        if (this.networkHelper.isConnected()) {
            this.menuHelper.checkRateStatus();
        }
        this.progressViewHelper.dismiss();
        this.menuHelper.sendToken();
        this.navigationModel = (NavigationModel) getIntent().getSerializableExtra("localObj");
        this.showIntro = getIntent().getBooleanExtra(Constants.INTENT_EXTRA_INTRO_MESSAGE, false);
        this.navigationItemModels = this.navigationModel.getNavigationItems();
        this.pushNotified = getIntent().getBooleanExtra("pushNotified", false);
        Intent intent = getIntent();
        String str = ChatConstants.ARG_NOTIFICATION_MODEL;
        if (intent.hasExtra(str)) {
            this.chatNotificationModel = (ChatNotificationModel) getIntent().getSerializableExtra(str);
            Intent intent2 = new Intent(this, aveChatView.class);
            intent2.putExtra(str, this.chatNotificationModel);
            startActivity(intent2);
        }
        this.menuHelper.showLanguageDialogAndCheckPushAndIntro();
        if (this.navigationItemModels.size() == 0) {
            this.menuHelper.showErrorMessage(getString(R.string.empty_content_warning));
        } else {
            loadUiProperties();
        }
    }

    /* access modifiers changed from: protected */
    public void onResume() {
        super.onResume();
        RelativeLayout relativeLayout = this.listMenuLayout;
        if (relativeLayout != null) {
            this.bannerHelper.addBannerAd(relativeLayout, this.list);
        }
        if (DynamicConstants.MobiRoller_Stage) {
            this.layoutHelper.setPreviewButton(getIntent(), this);
        }
    }

    public void loadUiProperties() {
        if (this.networkHelper.isConnected()) {
            try {
                ImageManager.loadBackgroundImage(this.navigationModel.getBackgroundImage().getImageURL(), this.listMenuLayout);
                checkNumberOfRows();
                loadAdapterItems();
            } catch (Exception unused) {
            }
            setListAdapter();
            ItemClickSupport.addTo(this.list).setOnItemClickListener(new OnItemClickListener() {
                public void onItemClicked(RecyclerView recyclerView, int i, View view) {
                    if (Integer.valueOf(((NavigationItemModel) ListMenu.this.valiNavItems.get(i)).getAccountScreenID()).intValue() != -1) {
                        ListMenu listMenu = ListMenu.this;
                        ActivityHandler.startActivity((Context) listMenu, (NavigationItemModel) listMenu.valiNavItems.get(i));
                        return;
                    }
                    view.setEnabled(false);
                }
            });
            return;
        }
        try {
            ImageManager.loadBackgroundImage(this.navigationModel.getBackgroundImage().getImageURL(), this.listMenuLayout);
            checkNumberOfRows();
            loadAdapterItems();
        } catch (Exception unused2) {
        }
        setListAdapter();
        ItemClickSupport.addTo(this.list).setOnItemClickListener(new OnItemClickListener() {
            public void onItemClicked(RecyclerView recyclerView, int i, View view) {
                ListMenu listMenu = ListMenu.this;
                ActivityHandler.startActivity((Context) listMenu, (NavigationItemModel) listMenu.valiNavItems.get(i));
            }
        });
    }

    public void checkNumberOfRows() {
        if (this.navigationModel.getNumberOfRows() == 5) {
            this.navigationModel.subMenuType = 1;
        }
        if (this.navigationModel.subMenuType == 1) {
            LayoutParams layoutParams = new LayoutParams(this.imgView.getLayoutParams());
            ScreenHelper screenHelper2 = this.screenHelper;
            int heightForDevice = ScreenHelper.getHeightForDevice(10, this);
            ScreenHelper screenHelper3 = this.screenHelper;
            layoutParams.setMargins(heightForDevice, 0, ScreenHelper.getHeightForDevice(10, this), 0);
            this.imgView.setLayoutParams(layoutParams);
            this.imgView.setImageResource(R.drawable.transparent);
            ImageView imageView = this.imgView;
            ScreenHelper screenHelper4 = this.screenHelper;
            imageView.setMinimumHeight(ScreenHelper.getDeviceHeight(this) / 2);
            int i = this.totalHeight;
            ScreenHelper screenHelper5 = this.screenHelper;
            this.totalHeight = i + (ScreenHelper.getDeviceHeight(this) / 2);
            this.imgView.setScaleType(ScaleType.FIT_XY);
            this.imgView.setVisibility(0);
        }
    }

    public void loadAdapterItems() {
        this.contentList = new ArrayList<>();
        if (!(this.navigationItemModels == null || this.navigationItemModels.size() == 0)) {
            this.valiNavItems = getValidItems();
            if (this.valiNavItems.size() == 0) {
                startActivity(new Intent(this, UserLoginActivity.class).putExtra(Constants.INTENT_EXTRA_IS_BACK_AVAILABLE, false));
                return;
            }
            for (int i = 0; i < this.valiNavItems.size(); i++) {
                NavigationItemModel navigationItemModel = (NavigationItemModel) this.valiNavItems.get(i);
                String localizedTitle = this.localizationHelper.getLocalizedTitle(navigationItemModel.getTitle());
                String str = null;
                if (navigationItemModel.getIconImage() != null) {
                    str = navigationItemModel.getIconImage().getImageURL();
                }
                String screenType = navigationItemModel.getScreenType();
                String accountScreenID = navigationItemModel.getAccountScreenID();
                HashMap hashMap = new HashMap();
                hashMap.put("title", localizedTitle);
                hashMap.put("img_url", str);
                hashMap.put("screen_type", screenType);
                hashMap.put("screen_id", accountScreenID);
                this.contentList.add(hashMap);
            }
        }
    }

    public void setListAdapter() {
        ContentRecyclerAdapter contentRecyclerAdapter = new ContentRecyclerAdapter(this, this.contentList, this.navigationModel, this.screenHelper);
        this.list.setAdapter(contentRecyclerAdapter);
        contentRecyclerAdapter.notifyDataSetChanged();
        this.list.setLayoutManager(new LinearLayoutManager(this));
        this.list.setVisibility(0);
        contentRecyclerAdapter.notifyDataSetChanged();
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        if (!this.sharedPrefHelper.getUserLoginActive()) {
            this.toolbar.setVisibility(8);
        } else if (this.sharedPrefHelper.getUserLoginStatus()) {
            getToolbar().inflateMenu(R.menu.user_menu);
            Menu menu2 = getToolbar().getMenu();
            if (AppSettingsHelper.isECommerceActive()) {
                menu2.findItem(R.id.action_my_addresses).setVisible(true);
                menu2.findItem(R.id.action_my_orders).setVisible(true);
            }
        } else {
            getToolbar().inflateMenu(R.menu.user_login_menu);
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
                DialogUtil.showLogoutDialog(this, this);
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
        loadAdapterItems();
        setListAdapter();
    }

    public void onBackPressed() {
        if (this.sharedPrefHelper.getAskBeforeExit()) {
            DialogUtil.showExitDialog(this, this);
        } else {
            super.onBackPressed();
        }
    }

    public void onExitApp() {
        if (!DynamicConstants.MobiRoller_Stage) {
            finishAffinity();
            System.exit(0);
            return;
        }
        super.onBackPressed();
    }

    public void onUserLogout() {
        invalidateOptionsMenu();
        loadAdapterItems();
        setListAdapter();
    }
}
