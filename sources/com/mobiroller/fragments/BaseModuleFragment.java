package com.mobiroller.fragments;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import androidx.fragment.app.Fragment;
import butterknife.Unbinder;
import com.applyze.ApplyzeAnalytics;
import com.crashlytics.android.Crashlytics;
import com.google.android.material.appbar.AppBarLayout.LayoutParams;
import com.mobiroller.DynamicConstants;
import com.mobiroller.MobiRollerApplication;
import com.mobiroller.activities.base.AveActivity;
import com.mobiroller.constants.Constants;
import com.mobiroller.constants.ModuleConstants;
import com.mobiroller.helpers.BannerHelper;
import com.mobiroller.helpers.ComponentHelper;
import com.mobiroller.helpers.FavoriteHelper;
import com.mobiroller.helpers.JSONParser;
import com.mobiroller.helpers.JSONStorage;
import com.mobiroller.helpers.LayoutHelper;
import com.mobiroller.helpers.LocalizationHelper;
import com.mobiroller.helpers.MenuHelper;
import com.mobiroller.helpers.NetworkHelper;
import com.mobiroller.helpers.ScreenHelper;
import com.mobiroller.helpers.SharedPrefHelper;
import com.mobiroller.helpers.UtilManager;
import com.mobiroller.interfaces.FragmentComponent;
import com.mobiroller.mobi942763453128.R;
import com.mobiroller.models.ScreenModel;
import com.mobiroller.util.ImageManager;
import javax.inject.Inject;

public class BaseModuleFragment extends BaseFragment {
    @Inject
    MobiRollerApplication app;
    @Inject
    MobiRollerApplication application;
    @Inject
    BannerHelper bannerHelper;
    @Inject
    ComponentHelper componentHelper;
    FavoriteHelper favoriteHelper;
    @Inject
    ImageManager imageManager;
    @Inject
    JSONParser jsonParser;
    @Inject
    LayoutHelper layoutHelper;
    LocalizationHelper localizationHelper;
    @Inject
    SharedPrefHelper mSharedPrf;
    @Inject
    MenuHelper menuHelper;
    NetworkHelper networkHelper;
    @Inject
    ScreenHelper screenHelper;
    public String screenId;
    public ScreenModel screenModel;
    public String screenType;
    SharedPrefHelper sharedPrefHelper;
    public String subScreenType;
    Unbinder unbinder;

    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        this.sharedPrefHelper = UtilManager.sharedPrefHelper();
        this.networkHelper = UtilManager.networkHelper();
        this.localizationHelper = UtilManager.localizationHelper();
        checkScreenValues();
        this.favoriteHelper = new FavoriteHelper(getActivity());
        if (this.screenModel != null) {
            setHasOptionsMenu(true);
        }
        if (!DynamicConstants.MobiRoller_Stage) {
            checkStats();
        } else {
            setPreviewButton();
        }
        if (((AveActivity) getActivity()).getToolbar() != null) {
            ((LayoutParams) ((AveActivity) getActivity()).getToolbar().getLayoutParams()).setScrollFlags(0);
        }
    }

    public Fragment injectFragment(FragmentComponent fragmentComponent) {
        fragmentComponent.inject(this);
        return this;
    }

    /* access modifiers changed from: protected */
    public void setPreviewButton() {
        ScreenModel screenModel2 = this.screenModel;
        if (screenModel2 == null || !screenModel2.isRssContent()) {
            this.layoutHelper.setPreviewButton(getActivity().getIntent(), getActivity());
        }
    }

    private void checkScreenValues() {
        if (getArguments() != null) {
            Bundle arguments = getArguments();
            String str = Constants.KEY_SCREEN_ID;
            if (arguments.containsKey(str)) {
                this.screenId = getArguments().getString(str);
            }
            String str2 = this.screenId;
            if (str2 != null && JSONStorage.containsScreen(str2)) {
                this.screenModel = JSONStorage.getScreenModel(this.screenId);
            }
            Bundle arguments2 = getArguments();
            String str3 = Constants.KEY_SCREEN_TYPE;
            if (arguments2.containsKey(str3)) {
                this.screenType = getArguments().getString(str3);
            }
            Bundle arguments3 = getArguments();
            String str4 = Constants.KEY_SUB_SCREEN_TYPE;
            if (arguments3.containsKey(str4)) {
                this.subScreenType = getArguments().getString(str4);
            }
        }
    }

    public void onCreateOptionsMenu(Menu menu, MenuInflater menuInflater) {
        super.onCreateOptionsMenu(menu, menuInflater);
        if (this.mSharedPrf.isFavoriteActive() && this.favoriteHelper.isScreenAvailable(this.screenType)) {
            if (!this.screenType.equalsIgnoreCase(ModuleConstants.HTML_MODULE) || !this.screenModel.getScreenType().equalsIgnoreCase("aveCatalogView")) {
                ((AveActivity) getActivity()).getToolbar().inflateMenu(R.menu.favorite_menu);
                if (this.favoriteHelper.isScreenAddedToList(this.screenId)) {
                    ((AveActivity) getActivity()).getToolbar().getMenu().findItem(R.id.action_favorite).setIcon(R.drawable.ic_bookmark_white_24dp);
                }
            }
        }
    }

    public boolean onOptionsItemSelected(MenuItem menuItem) {
        if (menuItem.getItemId() != R.id.action_favorite) {
            super.onOptionsItemSelected(menuItem);
        } else {
            if (this.favoriteHelper.isScreenAddedToList(this.screenId)) {
                this.favoriteHelper.removeScreenContent(this.screenId);
            } else {
                this.favoriteHelper.addScreenToList(this.screenModel, this.screenType, this.subScreenType, this.screenId);
            }
            getActivity().invalidateOptionsMenu();
        }
        return true;
    }

    private void sendStats() {
        ApplyzeAnalytics.getInstance().sendScreenEvent(this.localizationHelper.getLocalizedTitle(this.screenModel.getTitle()));
    }

    private String getScreenType() {
        if (this.screenModel.getScreenType() != null) {
            return this.screenModel.getScreenType();
        }
        return this.screenType;
    }

    private String getScreenTypeAnalyticsName() {
        String screenType2 = getScreenType();
        String[] stringArray = getActivity().getResources().getStringArray(R.array.module_analytics_name);
        String[] stringArray2 = getActivity().getResources().getStringArray(R.array.module_analytics_value);
        for (int i = 0; i < stringArray.length; i++) {
            if (screenType2.equalsIgnoreCase(stringArray[i])) {
                return stringArray2[i];
            }
        }
        return screenType2;
    }

    private void checkStats() {
        try {
            if (this.screenModel != null) {
                sendStats();
            }
        } catch (Exception e) {
            Crashlytics.logException(e);
        }
    }

    public void onDestroyView() {
        super.onDestroyView();
        Unbinder unbinder2 = this.unbinder;
        if (unbinder2 != null) {
            unbinder2.unbind();
        }
    }
}
