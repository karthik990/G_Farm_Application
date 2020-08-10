package com.mobiroller.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.mobiroller.adapters.FaqAdapter;
import com.mobiroller.mobi942763453128.R;
import com.mobiroller.util.ImageManager;

public class aveFAQViewFragment extends BaseModuleFragment {
    @BindView(2131362391)
    RelativeLayout mainLayout;
    @BindView(2131362873)
    RelativeLayout overlayLayout;
    @BindView(2131362397)
    RecyclerView recyclerView;

    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        View inflate = layoutInflater.inflate(R.layout.layout_faq, viewGroup, false);
        this.unbinder = ButterKnife.bind((Object) this, inflate);
        loadUI();
        return inflate;
    }

    private void loadUI() {
        ImageManager.loadBackgroundImageFromImageModel(this.mainLayout, this.screenModel.getBackgroundImageName());
        FaqAdapter faqAdapter = new FaqAdapter(getActivity(), this.screenModel.getTableItems(), this.screenModel, this.screenHelper, this.sharedPrefHelper, this.localizationHelper);
        this.recyclerView.setAdapter(faqAdapter);
        this.recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
    }

    public void onResume() {
        super.onResume();
        if (this.mainLayout != null) {
            this.bannerHelper.addBannerAd(this.mainLayout, this.overlayLayout);
        }
    }
}
