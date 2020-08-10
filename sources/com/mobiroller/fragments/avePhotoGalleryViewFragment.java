package com.mobiroller.fragments;

import android.content.Intent;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityOptionsCompat;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.mobiroller.activities.ImagePagerActivity;
import com.mobiroller.adapters.ImageAdapter;
import com.mobiroller.adapters.ImageAdapter.ImageViewHolder;
import com.mobiroller.constants.Constants;
import com.mobiroller.mobi942763453128.R;
import com.mobiroller.models.GalleryModel;
import com.mobiroller.util.InterstitialAdsUtil;
import com.mobiroller.views.ItemClickSupport;
import com.mobiroller.views.ItemClickSupport.OnItemClickListener;
import com.mobiroller.views.twowayview.GridLayoutManager;
import com.mobiroller.views.twowayview.TwoWayLayoutManager.Orientation;
import com.mobiroller.views.twowayview.TwoWayView;
import java.util.ArrayList;

public class avePhotoGalleryViewFragment extends BaseModuleFragment {
    @BindView(2131362467)
    RelativeLayout galleryRelativeLayout;
    ImageAdapter imageAdapter = null;
    private InterstitialAdsUtil interstitialAdsUtil;
    private boolean isDownloadable = false;
    private ArrayList<GalleryModel> jsonArray;
    private int layoutType = 5;
    @BindView(2131362466)
    TwoWayView recyclerView;

    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        View inflate = layoutInflater.inflate(R.layout.gallery, viewGroup, false);
        this.unbinder = ButterKnife.bind((Object) this, inflate);
        this.interstitialAdsUtil = new InterstitialAdsUtil((AppCompatActivity) getActivity());
        hideToolbar((Toolbar) inflate.findViewById(R.id.toolbar_top));
        loadUI();
        return inflate;
    }

    /* access modifiers changed from: private */
    public void startImagePagerActivity(int i) {
        Intent intent = new Intent(getActivity(), ImagePagerActivity.class);
        intent.putExtra(ImagePagerActivity.TOOLBAR_TITLE, this.localizationHelper.getLocalizedTitle(this.screenModel.getTitle()));
        intent.putExtra("imageList", this.jsonArray);
        intent.putExtra("fromGalleryView", true);
        intent.putExtra("isDownloadable", this.isDownloadable);
        intent.putExtra(Constants.KEY_RSS_POSITION, i);
        if (VERSION.SDK_INT >= 22) {
            this.interstitialAdsUtil.checkInterstitialAds(intent, ActivityOptionsCompat.makeSceneTransitionAnimation(getActivity(), ((ImageViewHolder) this.recyclerView.findViewHolderForLayoutPosition(i)).imageView, "galleryImage").toBundle());
            return;
        }
        this.interstitialAdsUtil.checkInterstitialAds(intent);
    }

    public void onResume() {
        super.onResume();
        if (this.galleryRelativeLayout != null) {
            this.bannerHelper.addBannerAd(this.galleryRelativeLayout, this.recyclerView);
        }
    }

    public void loadUI() {
        if (this.networkHelper.isConnected()) {
            this.jsonArray = this.screenModel.getImages();
            if (this.screenModel.getType() != null) {
                try {
                    this.layoutType = Integer.parseInt(this.screenModel.getType());
                } catch (Exception unused) {
                }
            }
            this.isDownloadable = this.screenModel.isDownloadable();
            this.recyclerView.setItemAnimator(null);
            this.recyclerView.setHasFixedSize(true);
            double d = (double) getActivity().getResources().getDisplayMetrics().density;
            Double.isNaN(d);
            int i = (int) ((d * 1.35d) + 0.5d);
            int i2 = this.layoutType;
            if (i2 == 1) {
                this.recyclerView.setLayoutManager(new GridLayoutManager(Orientation.VERTICAL, 2, 1));
                ImageAdapter imageAdapter2 = new ImageAdapter(getActivity(), this.jsonArray, this.recyclerView, this.layoutType, this.localizationHelper);
                this.imageAdapter = imageAdapter2;
            } else if (i2 == 2) {
                this.recyclerView.setLayoutManager(new GridLayoutManager(Orientation.VERTICAL, 3, 1));
                this.recyclerView.setPadding(i, i, i, i);
                ImageAdapter imageAdapter3 = new ImageAdapter(getActivity(), this.jsonArray, this.recyclerView, this.layoutType, this.localizationHelper);
                this.imageAdapter = imageAdapter3;
            } else if (i2 == 3) {
                this.recyclerView.setLayoutManager(new GridLayoutManager(Orientation.VERTICAL, 1, 1));
                ImageAdapter imageAdapter4 = new ImageAdapter(getActivity(), this.jsonArray, this.recyclerView, this.layoutType, this.localizationHelper);
                this.imageAdapter = imageAdapter4;
            } else if (i2 == 4) {
                this.recyclerView.setLayoutManager(new GridLayoutManager(Orientation.VERTICAL, 1, 1));
                ImageAdapter imageAdapter5 = new ImageAdapter(getActivity(), this.jsonArray, this.recyclerView, this.layoutType, this.localizationHelper);
                this.imageAdapter = imageAdapter5;
            } else if (i2 == 5) {
                this.recyclerView.setPadding(i, i, i, i);
                ImageAdapter imageAdapter6 = new ImageAdapter(getActivity(), this.jsonArray, this.recyclerView, this.layoutType, this.localizationHelper);
                this.imageAdapter = imageAdapter6;
            }
            this.recyclerView.setAdapter(this.imageAdapter);
            ItemClickSupport.addTo(this.recyclerView).setOnItemClickListener(new OnItemClickListener() {
                public void onItemClicked(RecyclerView recyclerView, int i, View view) {
                    avePhotoGalleryViewFragment.this.startImagePagerActivity(i);
                }
            });
            return;
        }
        Toast.makeText(getActivity().getApplicationContext(), getResources().getString(R.string.please_check_your_internet_connection), 1).show();
    }
}
