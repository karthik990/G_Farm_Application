package com.mobiroller.activities.menu;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.os.StrictMode.ThreadPolicy.Builder;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import androidx.appcompat.app.AppCompatActivity;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.mobiroller.DynamicConstants;
import com.mobiroller.activities.aveChatView;
import com.mobiroller.activities.user.UserAddressActivity;
import com.mobiroller.activities.user.UserChangePasswordActivity;
import com.mobiroller.activities.user.UserLoginActivity;
import com.mobiroller.activities.user.UserOrderActivity;
import com.mobiroller.activities.user.UserUpdateActivity;
import com.mobiroller.adapters.PagedGridAdapter;
import com.mobiroller.constants.ChatConstants;
import com.mobiroller.constants.Constants;
import com.mobiroller.containers.OnPageChangedListener;
import com.mobiroller.fragments.BackHandledFragment;
import com.mobiroller.helpers.AppSettingsHelper;
import com.mobiroller.helpers.BannerHelper;
import com.mobiroller.helpers.JSONParser;
import com.mobiroller.helpers.LayoutHelper;
import com.mobiroller.helpers.ScreenHelper;
import com.mobiroller.interfaces.ActivityComponent;
import com.mobiroller.mobi942763453128.R;
import com.mobiroller.models.NavigationItemModel;
import com.mobiroller.models.PagedGridModel;
import com.mobiroller.models.ScreenModel;
import com.mobiroller.models.chat.ChatNotificationModel;
import com.mobiroller.models.events.LoginEvent;
import com.mobiroller.util.DialogUtil;
import com.mobiroller.util.DialogUtil.ExitDialog;
import com.mobiroller.util.DialogUtil.UserLogoutDialog;
import com.mobiroller.util.ImageManager;
import com.mobiroller.util.ScreenUtil;
import com.mobiroller.util.exceptions.IntentException;
import com.mobiroller.util.exceptions.UserFriendlyException;
import com.mobiroller.views.PagedDragDropGrid;
import com.mobiroller.views.custom.MobirollerToolbar;
import com.mobiroller.widget.PageIndicator;
import java.util.ArrayList;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

public class aveNavigationActivity extends MenuActivity implements ExitDialog, UserLogoutDialog {
    private PagedGridAdapter adapter;
    public String backgroundImageUrl = null;
    @BindView(2131362476)
    PagedDragDropGrid gridView;
    @BindView(2131362475)
    ImageView imgView;
    @BindView(2131362880)
    PageIndicator mPageIndicatorOther;
    @BindView(2131362790)
    RelativeLayout navLayout;
    public int standartParam = 0;
    public int textColor = 0;
    @BindView(2131363276)
    MobirollerToolbar toolbar;
    public int topSpace = 0;
    public ArrayList<NavigationItemModel> validNavItems;

    /* access modifiers changed from: 0000 */
    public int getLayoutResource() {
        return R.layout.paged_grid;
    }

    public void onClick(View view) {
    }

    public void onScreenModelFetched(ScreenModel screenModel, int i) {
    }

    public void onThrowIntentException(IntentException intentException) {
    }

    public void onThrowUserFriendlyException(UserFriendlyException userFriendlyException) {
    }

    /* access modifiers changed from: 0000 */
    public void setFragment(ScreenModel screenModel, int i, int i2) {
    }

    public void setSelectedFragment(BackHandledFragment backHandledFragment) {
    }

    public AppCompatActivity injectActivity(ActivityComponent activityComponent) {
        activityComponent.inject(this);
        return this;
    }

    public void onCreate(Bundle bundle) {
        StrictMode.setThreadPolicy(new Builder().permitAll().build());
        requestWindowFeature(1);
        super.onCreate(bundle);
        ButterKnife.bind((Activity) this);
        setMobirollerToolbar(this.toolbar);
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }
        loadUI();
    }

    /* access modifiers changed from: 0000 */
    public void loadUI() {
        super.loadUI();
        this.toolbarHelper.setStatusBar(this);
        loadValidNavigationItemsView();
        loadUserView();
    }

    /* access modifiers changed from: 0000 */
    public void loadValidNavigationItemsView() {
        if (this.navigationItemModels == null || this.navigationItemModels.size() == 0) {
            this.menuHelper.showErrorMessage(getString(R.string.empty_content_warning));
            return;
        }
        this.mPageIndicatorOther.setPadding(0, ScreenHelper.getDeviceHeight(this) - ScreenHelper.getHeightForDevice(15, this), 0, 0);
        try {
            ImageManager.loadBackgroundImageFromImageModel(this.navLayout, this.navigationModel.getBackgroundImage());
            this.standartParam = ScreenUtil.getParamForColumns(this.navigationModel.getNumberOfColumns());
            this.topSpace = this.menuHelper.getTopSpace(this.navigationModel.getNumberOfRows(), this.standartParam);
            this.textColor = ScreenHelper.setColorUnselected(this.navigationModel.getMenuTextColor());
            if (this.navigationModel.getItemBackgroundImage() != null) {
                this.backgroundImageUrl = this.navigationModel.getItemBackgroundImage().getImageURL();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        this.imgView.setMinimumHeight(this.topSpace);
        setMenuItems();
        this.menuHelper.showLanguageDialogAndCheckPushAndIntro();
        Intent intent = getIntent();
        String str = ChatConstants.ARG_NOTIFICATION_MODEL;
        if (intent.hasExtra(str)) {
            this.chatNotificationModel = (ChatNotificationModel) getIntent().getSerializableExtra(str);
            Intent intent2 = new Intent(this, aveChatView.class);
            intent2.putExtra(str, this.chatNotificationModel);
            startActivity(intent2);
        }
    }

    /* access modifiers changed from: 0000 */
    public void loadUserView() {
        if (this.sharedPrefHelper.getUserLoginActive()) {
            this.toolbar.setBackgroundColor(0);
            this.toolbar.setTitle((CharSequence) "");
            setSupportActionBar(this.toolbar);
            return;
        }
        this.toolbar.setVisibility(8);
    }

    /* access modifiers changed from: 0000 */
    public void setLogInStatus() {
        setMenuItems();
        invalidateOptionsMenu();
    }

    /* access modifiers changed from: 0000 */
    public void loadBanner() {
        if (this.navLayout != null && !this.sharedPrefHelper.getUserLoginActive()) {
            new BannerHelper().addBannerTop(this.navLayout);
        }
    }

    public void setMenuItems() {
        if (!(this.navigationItemModels == null || this.navigationItemModels.size() == 0)) {
            this.validNavItems = getValidItems();
            PagedGridModel pagedGridModel = new PagedGridModel(this.validNavItems, this.navigationModel.getNumberOfRows(), this.navigationModel.getNumberOfColumns(), this.standartParam, this.textColor);
            if (this.validNavItems.size() == 0) {
                startActivity(new Intent(this, UserLoginActivity.class).putExtra(Constants.INTENT_EXTRA_IS_BACK_AVAILABLE, false));
                return;
            }
            PagedGridAdapter pagedGridAdapter = new PagedGridAdapter(this, this.gridView, pagedGridModel, this.mPageIndicatorOther, this.backgroundImageUrl, new ScreenHelper(this), this.progressViewHelper, new JSONParser());
            this.adapter = pagedGridAdapter;
            this.gridView.setAdapter(this.adapter);
            this.gridView.notifyDataSetChanged();
            this.gridView.setBackgroundResource(R.drawable.transparent);
            this.gridView.setOnPageChangedListener(new OnPageChangedListener() {
                public void onPageChanged(PagedDragDropGrid pagedDragDropGrid, int i) {
                }
            });
        }
    }

    /* access modifiers changed from: protected */
    public void onResume() {
        super.onResume();
        if (DynamicConstants.MobiRoller_Stage) {
            new LayoutHelper(this).setPreviewButton(getIntent(), this);
        }
    }

    public boolean onPrepareOptionsMenu(Menu menu) {
        return this.sharedPrefHelper.getUserLoginActive();
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        if (this.sharedPrefHelper.getUserLoginActive()) {
            if (this.sharedPrefHelper.getUserLoginStatus()) {
                getToolbar().inflateMenu(R.menu.user_menu);
                Menu menu2 = getToolbar().getMenu();
                if (AppSettingsHelper.isECommerceActive()) {
                    menu2.findItem(R.id.action_my_addresses).setVisible(true);
                    menu2.findItem(R.id.action_my_orders).setVisible(true);
                }
            } else {
                getToolbar().inflateMenu(R.menu.user_login_menu);
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
        loadUI();
        invalidateOptionsMenu();
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
        setMenuItems();
        invalidateOptionsMenu();
    }
}
