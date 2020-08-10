package com.mobiroller.fragments;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import androidx.appcompat.widget.Toolbar;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.mobiroller.activities.aveFullScreenVideo;
import com.mobiroller.constants.Constants;
import com.mobiroller.helpers.JSONStorage;
import com.mobiroller.helpers.ProgressViewHelper;
import com.mobiroller.mobi942763453128.R;
import com.mobiroller.util.InterstitialAdsUtil;

public class aveTVBroadcastViewFragment extends BaseModuleFragment {
    /* access modifiers changed from: private */
    public InterstitialAdsUtil interstitialAdsUtil;
    @BindView(2131362970)
    ImageButton playButton;
    ProgressViewHelper progressViewHelper;
    @BindView(2131362784)
    ProgressBar spinnerView;
    @BindView(2131363361)
    RelativeLayout videoBroadcastLayout;

    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        View inflate = layoutInflater.inflate(R.layout.video_broadcast_view_fullscreen, viewGroup, false);
        this.unbinder = ButterKnife.bind((Object) this, inflate);
        this.interstitialAdsUtil = new InterstitialAdsUtil((Activity) getActivity());
        this.progressViewHelper = new ProgressViewHelper(getActivity());
        hideToolbar((Toolbar) inflate.findViewById(R.id.toolbar_top));
        this.progressViewHelper.dismiss();
        loadUI();
        return inflate;
    }

    public void loadUI() {
        this.playButton.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                Intent intent = new Intent(aveTVBroadcastViewFragment.this.getActivity(), aveFullScreenVideo.class);
                intent.putExtra(Constants.KEY_SCREEN_ID, aveTVBroadcastViewFragment.this.screenId);
                intent.setFlags(268435456);
                JSONStorage.putScreenModel(aveTVBroadcastViewFragment.this.screenId, aveTVBroadcastViewFragment.this.screenModel);
                aveTVBroadcastViewFragment.this.interstitialAdsUtil.checkInterstitialAds(intent);
            }
        });
    }
}
