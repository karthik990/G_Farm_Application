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
import com.mobiroller.adapters.EmergencyCallAdapter;
import com.mobiroller.mobi942763453128.R;
import com.mobiroller.models.TableItemsModel;
import com.mobiroller.util.ImageManager;
import java.util.ArrayList;

public class aveEmergencyCallViewFragment extends BaseModuleFragment {
    @BindView(2131362391)
    RelativeLayout mainLayout;
    @BindView(2131362873)
    RelativeLayout overlayLayout;
    @BindView(2131362397)
    RecyclerView recyclerView;

    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        View inflate = layoutInflater.inflate(R.layout.layout_faq, viewGroup, false);
        this.unbinder = ButterKnife.bind((Object) this, inflate);
        loadUI(this.screenModel.getTableItems());
        return inflate;
    }

    private void loadUI(ArrayList<TableItemsModel> arrayList) {
        ImageManager.loadBackgroundImageFromImageModel(this.mainLayout, this.screenModel.getBackgroundImageName());
        this.recyclerView.setAdapter(new EmergencyCallAdapter(getActivity(), arrayList, this.sharedPrefHelper, this.localizationHelper));
        this.recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
    }

    public void onResume() {
        super.onResume();
        if (this.mainLayout != null) {
            this.bannerHelper.addBannerAd(this.mainLayout, this.overlayLayout);
        }
    }
}
