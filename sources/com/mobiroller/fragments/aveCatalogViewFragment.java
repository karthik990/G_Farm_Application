package com.mobiroller.fragments;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnKeyListener;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import androidx.fragment.app.FragmentTransaction;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.mobiroller.constants.Constants;
import com.mobiroller.fragments.catalog.CategoryViewFragment;
import com.mobiroller.mobi942763453128.R;

public class aveCatalogViewFragment extends BaseModuleFragment {
    @BindView(2131362150)
    FrameLayout childContainer;
    @BindView(2131362649)
    RelativeLayout mainLayout;
    @BindView(2131362873)
    RelativeLayout overlayLayout;

    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        View inflate = layoutInflater.inflate(R.layout.catalog_view_fragment, viewGroup, false);
        this.unbinder = ButterKnife.bind((Object) this, inflate);
        return inflate;
    }

    public void onViewCreated(View view, Bundle bundle) {
        super.onViewCreated(view, bundle);
        loadUI();
        CategoryViewFragment categoryViewFragment = new CategoryViewFragment();
        Bundle bundle2 = new Bundle();
        bundle2.putString(Constants.KEY_SCREEN_ID, this.screenId);
        categoryViewFragment.setArguments(bundle2);
        FragmentTransaction beginTransaction = getChildFragmentManager().beginTransaction();
        beginTransaction.setCustomAnimations(R.anim.right_to_left_enter_300, R.anim.right_to_left_exit_300, R.anim.left_to_right_enter_300, R.anim.left_to_right_exit_300);
        beginTransaction.addToBackStack("categoryMain").replace(R.id.child_container, categoryViewFragment).commitAllowingStateLoss();
    }

    private void loadUI() {
        getView().setFocusableInTouchMode(true);
        getView().requestFocus();
        getView().setOnKeyListener(new OnKeyListener() {
            public boolean onKey(View view, int i, KeyEvent keyEvent) {
                if (i != 4 || keyEvent.getAction() != 1) {
                    return false;
                }
                if (aveCatalogViewFragment.this.getChildFragmentManager().getBackStackEntryCount() == 1) {
                    aveCatalogViewFragment.this.getActivity().onBackPressed();
                } else {
                    aveCatalogViewFragment.this.getChildFragmentManager().popBackStack();
                }
                return true;
            }
        });
    }

    public void onResume() {
        super.onResume();
        if (this.mainLayout != null) {
            this.bannerHelper.addBannerAd(this.mainLayout, this.overlayLayout);
        }
    }
}
