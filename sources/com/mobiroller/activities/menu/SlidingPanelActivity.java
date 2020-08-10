package com.mobiroller.activities.menu;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.ListAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;
import androidx.viewpager.widget.ViewPager.OnPageChangeListener;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.afollestad.materialdialogs.MaterialDialog.Builder;
import com.afollestad.materialdialogs.MaterialDialog.SingleButtonCallback;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;
import com.google.android.material.appbar.AppBarLayout;
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
import com.mobiroller.adapters.SlidingPanelMenuAdapter;
import com.mobiroller.adapters.SlidingPanelUserMenuAdapter;
import com.mobiroller.constants.ChatConstants;
import com.mobiroller.constants.Constants;
import com.mobiroller.fragments.BackHandledFragment;
import com.mobiroller.fragments.BackHandledFragment.BackHandlerInterface;
import com.mobiroller.fragments.aveWebViewFragment;
import com.mobiroller.fragments.aveYoutubeAdvancedViewFragment;
import com.mobiroller.helpers.ApiRequestManager;
import com.mobiroller.helpers.AppSettingsHelper;
import com.mobiroller.helpers.InAppPurchaseHelper;
import com.mobiroller.helpers.JSONParser;
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
import com.mobiroller.models.UserMenuModel;
import com.mobiroller.models.chat.ChatNotificationModel;
import com.mobiroller.models.events.InAppPurchaseSuccessEvent;
import com.mobiroller.models.events.LoginEvent;
import com.mobiroller.models.events.OpenMenuModel;
import com.mobiroller.models.events.ProfileUpdateEvent;
import com.mobiroller.models.events.ShowToolbarEvent;
import com.mobiroller.util.DateUtil;
import com.mobiroller.util.InterstitialAdsUtil;
import com.mobiroller.util.MobirollerIntent;
import com.mobiroller.views.CircleImageView;
import com.mobiroller.views.EmptyFragment;
import com.mobiroller.views.custom.MobirollerToolbar;
import com.sothree.slidinguppanel.SlidingUpPanelLayout;
import com.sothree.slidinguppanel.SlidingUpPanelLayout.PanelSlideListener;
import com.sothree.slidinguppanel.SlidingUpPanelLayout.PanelState;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SlidingPanelActivity extends AveMenuActivity implements BackHandlerInterface {
    /* access modifiers changed from: private */
    public List<SlidingPanelMenuAdapter> adapterList = new ArrayList();
    @Inject
    ApiRequestManager apiRequestManager;
    @Inject
    MobiRollerApplication app;
    @BindView(2131362167)
    ImageView closeButton;
    ImageView[] dots;
    int dotsCount;
    private MaterialDialog exitDialog;
    private Fragment fragment = null;
    @BindView(2131362479)
    RelativeLayout gridViewLayout;
    private InterstitialAdsUtil interstitialAdsUtil;
    private boolean isItemClicked = false;
    private boolean isMenuLoaded = false;
    private boolean isUserMenu = false;
    @Inject
    JSONParser jsonParser;
    @Inject
    LocalizationHelper localizationHelper;
    private String loginEventScreenId;
    private ImageView mDropDownImage;
    @BindView(2131363349)
    View mHeaderView;
    private ImageView mLoginImage;
    private CharSequence mTitle;
    private TextView mUserEmail;
    /* access modifiers changed from: private */
    public CircleImageView mUserImageView;
    private TextView mUserName;
    @Inject
    MenuHelper menuHelper;
    private List<MenuItem> menuItemList = new ArrayList();
    @Inject
    NetworkHelper networkHelper;
    private int order = 0;
    @BindView(2131363379)
    LinearLayout pager_indicator;
    private ProgressViewHelper progressViewHelper;
    @Inject
    ScreenHelper screenHelper;
    private BackHandledFragment selectedFragment;
    @Inject
    SharedPrefHelper sharedPrefHelper;
    @BindView(2131363153)
    ViewPager slidingPanelViewPager;
    @BindView(2131362625)
    SlidingUpPanelLayout slidingUpPanelLayout;
    @BindView(2131363270)
    MobirollerToolbar toolbar;
    @Inject
    ToolbarHelper toolbarHelper;
    @BindView(2131363347)
    GridView userGrid;
    @BindView(2131363348)
    RelativeLayout userGridLayout;
    /* access modifiers changed from: private */
    public ArrayList<NavigationItemModel> validNavItems;

    public class SlidingPanelViewPagerAdapter extends PagerAdapter {
        public boolean isViewFromObject(View view, Object obj) {
            return view == obj;
        }

        public SlidingPanelViewPagerAdapter() {
        }

        public Object instantiateItem(ViewGroup viewGroup, final int i) {
            ViewGroup viewGroup2 = (ViewGroup) LayoutInflater.from(SlidingPanelActivity.this).inflate(R.layout.activity_sliding_panel_view_pager_item, viewGroup, false);
            GridView gridView = (GridView) viewGroup2.findViewById(R.id.gridview);
            if (i != 0) {
                List access$700 = SlidingPanelActivity.this.adapterList;
                SlidingPanelActivity slidingPanelActivity = SlidingPanelActivity.this;
                List subList = slidingPanelActivity.getSubList(i * 9, (i + 1) * 9);
                ScreenHelper screenHelper = SlidingPanelActivity.this.screenHelper;
                access$700.add(i, new SlidingPanelMenuAdapter(slidingPanelActivity, subList, ScreenHelper.setColorUnselected(SlidingPanelActivity.this.navigationModel.getMenuBackgroundColor())));
            }
            gridView.setAdapter((ListAdapter) SlidingPanelActivity.this.adapterList.get(i));
            gridView.setOnItemClickListener(new OnItemClickListener() {
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long j) {
                    SlidingPanelActivity.this.displayViewImprove((NavigationItemModel) SlidingPanelActivity.this.validNavItems.get((i * 9) + i), (i * 9) + i);
                    SlidingPanelActivity.this.slidingUpPanelLayout.setPanelState(PanelState.COLLAPSED);
                }
            });
            viewGroup.addView(viewGroup2);
            return viewGroup2;
        }

        public void destroyItem(ViewGroup viewGroup, int i, Object obj) {
            viewGroup.removeView((View) obj);
        }

        public int getCount() {
            double size = (double) SlidingPanelActivity.this.validNavItems.size();
            Double.isNaN(size);
            return (int) Math.ceil(size / 9.0d);
        }
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        return true;
    }

    @OnClick({2131362167})
    public void closeSlidingPanel() {
        this.slidingUpPanelLayout.setPanelState(PanelState.COLLAPSED);
    }

    /* access modifiers changed from: protected */
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        overridePendingTransition(0, 0);
        setContentView((int) R.layout.activity_sliding_panel_layout);
        ButterKnife.bind((Activity) this);
        this.progressViewHelper = new ProgressViewHelper((AppCompatActivity) this);
        this.progressViewHelper.setCancelableOnCancel();
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }
        this.toolbarHelper.setStatusBar(this);
        setSupportActionBar(this.toolbar);
        setMobirollerToolbar(this.toolbar);
        this.mAppBarLayout = (AppBarLayout) findViewById(R.id.app_bar_layout);
        this.toolbar.setNavigationOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                SlidingPanelActivity.this.onPostOpenMenuEvent(new OpenMenuModel());
            }
        });
        this.interstitialAdsUtil = new InterstitialAdsUtil((AppCompatActivity) this);
        this.slidingUpPanelLayout.getChildAt(1).setOnClickListener(null);
        if (this.networkHelper.isConnected()) {
            this.menuHelper.checkRateStatus();
        }
        checkUserLoginActive();
        this.menuHelper.sendToken();
        this.navigationModel = (NavigationModel) getIntent().getSerializableExtra("localObj");
        this.showIntro = getIntent().getBooleanExtra(Constants.INTENT_EXTRA_INTRO_MESSAGE, false);
        this.navigationItemModels = this.navigationModel.getNavigationItems();
        this.pushNotified = getIntent().getBooleanExtra("pushNotified", false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        this.slidingUpPanelLayout.addPanelSlideListener(new PanelSlideListener() {
            public void onPanelSlide(View view, float f) {
            }

            public void onPanelStateChanged(View view, PanelState panelState, PanelState panelState2) {
                if (!(panelState == PanelState.EXPANDED || panelState == PanelState.COLLAPSED || panelState == PanelState.ANCHORED || panelState == PanelState.HIDDEN)) {
                    PanelState panelState3 = PanelState.DRAGGING;
                }
                if (panelState2 != PanelState.EXPANDED && panelState2 != PanelState.COLLAPSED && panelState2 != PanelState.ANCHORED && panelState2 != PanelState.HIDDEN) {
                    PanelState panelState4 = PanelState.DRAGGING;
                }
            }
        });
        this.gridViewLayout.setBackgroundColor(this.screenHelper.getColorWithAlpha(ScreenHelper.setColorUnselected(this.navigationModel.getMenuBackgroundColor()), 0.95f));
        if (this.navigationModel != null) {
            this.navigationItemModels = this.navigationModel.getNavigationItems();
        }
        this.menuHelper.showLanguageDialogAndCheckPushAndIntro();
        if (this.isMenuLoaded) {
            return;
        }
        if (this.navigationItemModels == null || this.navigationItemModels.size() == 0) {
            this.menuHelper.showErrorMessage(getString(R.string.empty_content_warning));
            return;
        }
        setAppMenu();
        checkBanner();
        this.isMenuLoaded = true;
        Intent intent = getIntent();
        String str = ChatConstants.ARG_NOTIFICATION_MODEL;
        if (intent.hasExtra(str)) {
            this.chatNotificationModel = (ChatNotificationModel) getIntent().getSerializableExtra(str);
            Intent intent2 = new Intent(this, aveChatView.class);
            intent2.putExtra(str, this.chatNotificationModel);
            startActivity(intent2);
        }
    }

    public void onResume() {
        super.onResume();
    }

    public void onBackPressed() {
        if (this.slidingUpPanelLayout.getPanelState() == PanelState.COLLAPSED) {
            this.slidingUpPanelLayout.setPanelState(PanelState.EXPANDED);
            return;
        }
        if (this.sharedPrefHelper.getAskBeforeExit()) {
            MaterialDialog materialDialog = this.exitDialog;
            if (materialDialog == null) {
                this.exitDialog = new Builder(this).title((int) R.string.app_name).content((int) R.string.action_close_app).positiveText((int) R.string.yes).negativeText((int) R.string.cancel).onPositive(new SingleButtonCallback() {
                    public void onClick(MaterialDialog materialDialog, DialogAction dialogAction) {
                        if (!DynamicConstants.MobiRoller_Stage) {
                            SlidingPanelActivity.this.finishAffinity();
                            System.exit(0);
                            return;
                        }
                        SlidingPanelActivity.super.onBackPressed();
                    }
                }).show();
            } else if (!materialDialog.isShowing()) {
                this.exitDialog.show();
            }
        } else {
            super.onBackPressed();
        }
    }

    public boolean onOptionsItemSelected(MenuItem menuItem) {
        if (menuItem.getItemId() != 16908332) {
            return super.onOptionsItemSelected(menuItem);
        }
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

    public void setSelectedFragment(BackHandledFragment backHandledFragment) {
        this.selectedFragment = backHandledFragment;
    }

    private void setNavigationUserHeader() {
        this.mUserImageView = (CircleImageView) this.mHeaderView.findViewById(R.id.header_user_image);
        this.mUserName = (TextView) this.mHeaderView.findViewById(R.id.header_user_name);
        this.mUserEmail = (TextView) this.mHeaderView.findViewById(R.id.header_user_email);
        this.mDropDownImage = (ImageView) this.mHeaderView.findViewById(R.id.header_drop_down);
        this.mLoginImage = (ImageView) this.mHeaderView.findViewById(R.id.header_login_icon);
        if (this.sharedPrefHelper.getUserLoginStatus()) {
            this.mUserEmail.setText(UserHelper.getUserEmail());
            this.mHeaderView.setOnClickListener(new OnClickListener() {
                public void onClick(View view) {
                    SlidingPanelActivity.this.setUserMenu();
                }
            });
            this.mDropDownImage.setVisibility(0);
            this.mLoginImage.setVisibility(8);
            this.mUserName.setText(UserHelper.getUserName());
            if (!UserHelper.getUserImageUrl().equalsIgnoreCase("")) {
                Glide.with((FragmentActivity) this).load(UserHelper.getUserImageUrl()).apply(new RequestOptions().diskCacheStrategy(DiskCacheStrategy.ALL)).addListener(new RequestListener<Drawable>() {
                    public boolean onResourceReady(Drawable drawable, Object obj, Target<Drawable> target, DataSource dataSource, boolean z) {
                        return false;
                    }

                    public boolean onLoadFailed(GlideException glideException, Object obj, Target<Drawable> target, boolean z) {
                        SlidingPanelActivity.this.mUserImageView.setImageResource(R.drawable.ic_account_circle_black_36dp);
                        return false;
                    }
                }).into((ImageView) this.mUserImageView);
                return;
            }
            return;
        }
        this.mDropDownImage.setVisibility(8);
        this.mLoginImage.setVisibility(0);
        this.mUserName.setText(getString(R.string.guest));
        this.mUserEmail.setText(getString(R.string.not_login));
        this.mUserImageView.setImageResource(R.drawable.ic_account_circle_black_36dp);
        this.mHeaderView.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                SlidingPanelActivity slidingPanelActivity = SlidingPanelActivity.this;
                slidingPanelActivity.startActivity(new Intent(slidingPanelActivity, UserLoginActivity.class));
            }
        });
    }

    /* access modifiers changed from: private */
    public void setUserMenu() {
        if (this.slidingPanelViewPager.getVisibility() == 0) {
            this.slidingPanelViewPager.setVisibility(8);
            this.pager_indicator.setVisibility(8);
            this.userGridLayout.setVisibility(0);
            this.mDropDownImage.setRotation(180.0f);
            ArrayList arrayList = new ArrayList();
            arrayList.add(new UserMenuModel(getString(R.string.profile), R.drawable.ic_person_white_36dp));
            if (AppSettingsHelper.isECommerceActive()) {
                arrayList.add(new UserMenuModel(getString(R.string.e_commerce_my_orders_title), R.drawable.ic_local_shipping_white_24dp));
                arrayList.add(new UserMenuModel(getString(R.string.user_my_address_title), R.drawable.ic_place_white_24dp));
            }
            arrayList.add(new UserMenuModel(getString(R.string.change_password), R.drawable.ic_lock_white_36dp));
            arrayList.add(new UserMenuModel(getString(R.string.logout), R.drawable.ic_exit_to_app_white_36dp));
            arrayList.add(new UserMenuModel(getString(R.string.return_to_menu), R.drawable.ic_reply_white_36dp));
            ScreenHelper screenHelper2 = this.screenHelper;
            this.userGrid.setAdapter(new SlidingPanelUserMenuAdapter(this, arrayList, ScreenHelper.setColorUnselected(this.navigationModel.getMenuBackgroundColor())));
            this.userGrid.setOnItemClickListener(new OnItemClickListener() {
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long j) {
                    SlidingPanelActivity.this.displayUserView(i);
                }
            });
            return;
        }
        this.mDropDownImage.setRotation(0.0f);
        this.slidingPanelViewPager.setVisibility(0);
        this.pager_indicator.setVisibility(0);
        this.userGridLayout.setVisibility(8);
    }

    private void setAppMenu() {
        if (!(this.navigationItemModels == null || this.navigationItemModels.size() == 0)) {
            this.dotsCount = 0;
            this.validNavItems = getValidItems();
            if (this.validNavItems.size() == 0) {
                startActivity(new Intent(this, UserLoginActivity.class).putExtra(Constants.INTENT_EXTRA_IS_BACK_AVAILABLE, false));
                return;
            }
            SlidingPanelViewPagerAdapter slidingPanelViewPagerAdapter = new SlidingPanelViewPagerAdapter();
            List<SlidingPanelMenuAdapter> list = this.adapterList;
            List subList = getSubList(0, 9);
            ScreenHelper screenHelper2 = this.screenHelper;
            list.add(0, new SlidingPanelMenuAdapter(this, subList, ScreenHelper.setColorUnselected(this.navigationModel.getMenuBackgroundColor())));
            this.slidingPanelViewPager.setAdapter(new SlidingPanelViewPagerAdapter());
            this.slidingPanelViewPager.setOffscreenPageLimit(10);
            this.slidingPanelViewPager.addOnPageChangeListener(new OnPageChangeListener() {
                public void onPageScrollStateChanged(int i) {
                }

                public void onPageScrolled(int i, float f, int i2) {
                }

                public void onPageSelected(int i) {
                    if (SlidingPanelActivity.this.dotsCount > 1) {
                        for (int i2 = 0; i2 < SlidingPanelActivity.this.dotsCount; i2++) {
                            SlidingPanelActivity.this.dots[i2].setImageDrawable(ContextCompat.getDrawable(SlidingPanelActivity.this, R.drawable.nonselecteditem_dot));
                        }
                        SlidingPanelActivity.this.dots[i].setImageDrawable(ContextCompat.getDrawable(SlidingPanelActivity.this, R.drawable.selecteditem_dot));
                    }
                }
            });
            this.dotsCount = slidingPanelViewPagerAdapter.getCount();
            this.dots = new ImageView[this.dotsCount];
            this.pager_indicator.removeAllViews();
            boolean z = true;
            if (this.dotsCount > 1) {
                for (int i = 0; i < this.dotsCount; i++) {
                    this.dots[i] = new ImageView(this);
                    this.dots[i].setImageDrawable(ContextCompat.getDrawable(this, R.drawable.nonselecteditem_dot));
                    LayoutParams layoutParams = new LayoutParams(-2, -2);
                    layoutParams.setMargins(4, 0, 4, 0);
                    this.pager_indicator.addView(this.dots[i], layoutParams);
                }
                this.dots[0].setImageDrawable(ContextCompat.getDrawable(this, R.drawable.selecteditem_dot));
            }
            if (this.loginEventScreenId == null) {
                displayViewImprove((NavigationItemModel) this.validNavItems.get(0), 0);
            } else {
                int i2 = 0;
                while (true) {
                    if (i2 >= this.validNavItems.size()) {
                        z = false;
                        break;
                    } else if (((NavigationItemModel) this.validNavItems.get(i2)).getAccountScreenID().equalsIgnoreCase(String.valueOf(this.loginEventScreenId))) {
                        displayViewImprove((NavigationItemModel) this.validNavItems.get(0), 0);
                        break;
                    } else {
                        i2++;
                    }
                }
                if (!z) {
                    displayViewImprove((NavigationItemModel) this.validNavItems.get(0), 0);
                }
            }
        }
    }

    /* access modifiers changed from: private */
    public void displayUserView(int i) {
        if (i == 0) {
            startActivity(new Intent(this, UserUpdateActivity.class));
        }
        if (AppSettingsHelper.isECommerceActive()) {
            if (i == 1) {
                startActivity(new Intent(this, UserOrderActivity.class));
            } else if (i == 2) {
                startActivity(new Intent(this, UserAddressActivity.class));
            } else if (i == 3) {
                startActivity(new Intent(this, UserChangePasswordActivity.class));
            } else if (i == 4) {
                showLogoutDialog();
            } else if (i == 5) {
                setUserMenu();
            }
        } else if (i == 1) {
            startActivity(new Intent(this, UserChangePasswordActivity.class));
        } else if (i == 2) {
            showLogoutDialog();
        } else if (i == 3) {
            setUserMenu();
        }
    }

    @Subscribe
    public void loginEvent(LoginEvent loginEvent) {
        this.loginEventScreenId = loginEvent.screenId;
        getSupportFragmentManager().beginTransaction().replace(R.id.frame_container, new EmptyFragment()).commitAllowingStateLoss();
        this.progressViewHelper.show();
        setAppMenu();
        setUserMenu();
        setNavigationUserHeader();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void updateEvent(ProfileUpdateEvent profileUpdateEvent) {
        if (profileUpdateEvent.getProfileImageURL() != null) {
            Glide.with((FragmentActivity) this).load(profileUpdateEvent.getProfileImageURL()).into((ImageView) this.mUserImageView);
        }
        if (profileUpdateEvent.getUserName() != null) {
            this.mUserName.setText(profileUpdateEvent.getUserName());
        }
    }

    public void onActivityResult(int i, int i2, Intent intent) {
        super.onActivityResult(i, i2, intent);
        Fragment fragment2 = this.fragment;
        if (fragment2 != null) {
            fragment2.onActivityResult(i, i2, intent);
        }
    }

    private void checkUserLoginActive() {
        RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) this.userGridLayout.getLayoutParams();
        RelativeLayout.LayoutParams layoutParams2 = (RelativeLayout.LayoutParams) this.slidingPanelViewPager.getLayoutParams();
        if (this.sharedPrefHelper.getUserLoginActive()) {
            this.mHeaderView.setVisibility(0);
            layoutParams.addRule(3, R.id.user_header);
            layoutParams2.addRule(3, R.id.user_header);
            this.slidingPanelViewPager.setLayoutParams(layoutParams2);
            this.userGridLayout.setLayoutParams(layoutParams);
            setNavigationUserHeader();
            return;
        }
        layoutParams.addRule(13, -1);
        layoutParams2.addRule(13, -1);
        this.slidingPanelViewPager.setLayoutParams(layoutParams2);
        this.userGridLayout.setLayoutParams(layoutParams);
    }

    public void displayViewImprove(NavigationItemModel navigationItemModel, int i) {
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

    private ScreenModel getLocalScreenModel(NavigationItemModel navigationItemModel, int i) {
        return this.jsonParser.getLocalScreenModel(this, navigationItemModel.getAccountScreenID());
    }

    /* access modifiers changed from: private */
    public void displayView(ScreenModel screenModel, int i) {
        if (screenModel != null && screenModel.getFragment() != null) {
            this.screenModel = screenModel;
            if (!InAppPurchaseHelper.checkScreenIsInPurchaseList(String.valueOf(((NavigationItemModel) this.validNavItems.get(i)).getAccountScreenID()))) {
                startAction(i);
            } else if (!InAppPurchaseHelper.checkIsInAppPurchaseValid(this)) {
                startNoConnectionActivity((NavigationItemModel) this.validNavItems.get(i));
            } else if (isScreenPurchased(((NavigationItemModel) this.validNavItems.get(i)).getAccountScreenID())) {
                startAction(i);
            } else {
                dismissProgress();
                MobirollerIntent.startInAppPurchaseActivity(this, String.valueOf(((NavigationItemModel) this.validNavItems.get(i)).getAccountScreenID()), ((NavigationItemModel) this.validNavItems.get(i)).getScreenType(), false);
            }
        } else if (this.networkHelper.isConnected() && screenModel == null) {
            displayView(this.apiRequestManager.getScreenJSON(((NavigationItemModel) this.validNavItems.get(i)).getAccountScreenID()), i);
        } else if (screenModel == null) {
            startNoConnectionActivity((NavigationItemModel) this.validNavItems.get(i));
        }
    }

    private void startAction(final int i) {
        getWindow().getDecorView().setSystemUiVisibility(0);
        this.toolbarHelper.setStatusBar(this);
        this.fragment = this.screenModel.getFragment();
        int i2 = i / 9;
        ((SlidingPanelMenuAdapter) this.adapterList.get(i2)).setSelected(i % 9);
        for (int i3 = 0; i3 < this.adapterList.size(); i3++) {
            if (i2 != i3) {
                ((SlidingPanelMenuAdapter) this.adapterList.get(i3)).setSelected(-1);
            }
            ((SlidingPanelMenuAdapter) this.adapterList.get(i3)).notifyDataSetChanged();
        }
        Fragment fragment2 = this.fragment;
        if ((fragment2 instanceof aveYoutubeAdvancedViewFragment) || ((fragment2 instanceof aveWebViewFragment) && this.screenModel.isHideToolbar())) {
            runOnUiThread(new Runnable() {
                public void run() {
                    SlidingPanelActivity.this.toolbar.setVisibility(8);
                }
            });
        } else {
            setSupportActionBar(this.toolbar);
            this.toolbar.setNavigationOnClickListener(new OnClickListener() {
                public void onClick(View view) {
                    SlidingPanelActivity.this.onPostOpenMenuEvent(new OpenMenuModel());
                }
            });
            runOnUiThread(new Runnable() {
                public void run() {
                    SlidingPanelActivity.this.toolbar.setVisibility(0);
                }
            });
        }
        if (this.interstitialAdsUtil.checkInterstitialAds(this.screenModel.getFragment(), this.screenModel, ((NavigationItemModel) this.validNavItems.get(i)).getAccountScreenID(), ((NavigationItemModel) this.validNavItems.get(i)).getScreenType(), ((NavigationItemModel) this.validNavItems.get(i)).screenSubtype, ((NavigationItemModel) this.validNavItems.get(i)).getUpdateDate())) {
            runOnUiThread(new Runnable() {
                public void run() {
                    SlidingPanelActivity slidingPanelActivity = SlidingPanelActivity.this;
                    slidingPanelActivity.setTitle(slidingPanelActivity.localizationHelper.getLocalizedTitle(((NavigationItemModel) SlidingPanelActivity.this.validNavItems.get(i)).getTitle()));
                    SlidingPanelActivity.this.dismissProgress();
                }
            });
        }
    }

    private void startNoConnectionActivity(NavigationItemModel navigationItemModel) {
        dismissProgress();
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
                    SlidingPanelActivity.this.screenModel = (ScreenModel) response.body();
                    SlidingPanelActivity slidingPanelActivity = SlidingPanelActivity.this;
                    slidingPanelActivity.screenModel = slidingPanelActivity.menuHelper.getFragment(navigationItemModel, SlidingPanelActivity.this.screenModel, SlidingPanelActivity.this);
                    SlidingPanelActivity slidingPanelActivity2 = SlidingPanelActivity.this;
                    slidingPanelActivity2.displayView(slidingPanelActivity2.screenModel, i);
                } else {
                    SlidingPanelActivity.this.getLocalScreenModelAndDisplay(navigationItemModel, i);
                }
                SlidingPanelActivity.this.dismissProgress();
            }

            public void onFailure(Call<ScreenModel> call, Throwable th) {
                SlidingPanelActivity.this.getLocalScreenModelAndDisplay(navigationItemModel, i);
                SlidingPanelActivity.this.dismissProgress();
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

    private void checkBanner() {
        if (this.sharedPrefHelper.getIsAdmobBannerAdEnabled() && this.sharedPrefHelper.getBannerAd() == null && !this.sharedPrefHelper.getBannerAdUnitID().isEmpty()) {
            new Handler(Looper.getMainLooper()).post(new Runnable() {
                public void run() {
                    SlidingPanelActivity.this.sharedPrefHelper.setBannerAd(SlidingPanelActivity.this.getApplicationContext());
                }
            });
        }
    }

    private void showLogoutDialog() {
        new Builder(this).title((int) R.string.app_name).content((int) R.string.logout_dialog).positiveText((int) R.string.yes).negativeText((int) R.string.cancel).onPositive(new SingleButtonCallback() {
            public void onClick(MaterialDialog materialDialog, DialogAction dialogAction) {
                UserHelper.logout(SlidingPanelActivity.this.sharedPrefHelper, SlidingPanelActivity.this);
            }
        }).show();
    }

    @Subscribe
    public void onPostInAppPurchaseSuccessEvent(InAppPurchaseSuccessEvent inAppPurchaseSuccessEvent) {
        for (int i = 0; i < this.validNavItems.size(); i++) {
            if (((NavigationItemModel) this.validNavItems.get(i)).getAccountScreenID().equalsIgnoreCase(inAppPurchaseSuccessEvent.screenId)) {
                displayViewImprove((NavigationItemModel) this.validNavItems.get(i), i);
                return;
            }
        }
    }

    @Subscribe
    public void onPostOpenMenuEvent(OpenMenuModel openMenuModel) {
        this.slidingUpPanelLayout.setPanelState(PanelState.EXPANDED);
    }

    public List<NavigationItemModel> getSubList(int i, int i2) {
        if (this.validNavItems.size() >= i2) {
            return this.validNavItems.subList(i, i2);
        }
        ArrayList<NavigationItemModel> arrayList = this.validNavItems;
        return arrayList.subList(i, arrayList.size());
    }

    /* access modifiers changed from: protected */
    public void onStop() {
        super.onStop();
        dismissProgress();
    }

    /* access modifiers changed from: private */
    public void dismissProgress() {
        ProgressViewHelper progressViewHelper2 = this.progressViewHelper;
        if (progressViewHelper2 != null && progressViewHelper2.isShowing() && !isFinishing()) {
            this.progressViewHelper.dismiss();
        }
    }

    @Subscribe
    public void onPostShowToolbarEvent(ShowToolbarEvent showToolbarEvent) {
        this.toolbar.setVisibility(0);
        setSupportActionBar(this.toolbar);
        this.toolbarHelper.setStatusBar(this);
        this.toolbar.setNavigationOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                SlidingPanelActivity.this.onPostOpenMenuEvent(new OpenMenuModel());
            }
        });
    }

    public void onRequestPermissionsResult(int i, String[] strArr, int[] iArr) {
        super.onRequestPermissionsResult(i, strArr, iArr);
        this.fragment.onRequestPermissionsResult(i, strArr, iArr);
    }
}
