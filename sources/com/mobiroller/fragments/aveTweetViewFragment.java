package com.mobiroller.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.Toast;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.mobiroller.helpers.ProgressViewHelper;
import com.mobiroller.mobi942763453128.R;
import com.mobiroller.util.ImageManager;
import com.mobiroller.views.SimpleDividerItemDecoration;
import com.twitter.sdk.android.core.Callback;
import com.twitter.sdk.android.core.Result;
import com.twitter.sdk.android.core.TwitterException;
import com.twitter.sdk.android.core.models.Tweet;
import com.twitter.sdk.android.tweetui.TweetTimelineRecyclerViewAdapter.Builder;
import com.twitter.sdk.android.tweetui.UserTimeline;

public class aveTweetViewFragment extends BaseModuleFragment {
    @BindView(2131363329)
    RecyclerView list;
    ProgressViewHelper progressViewHelper;
    @BindView(2131363328)
    RelativeLayout twitterLayout;
    @BindView(2131363330)
    RelativeLayout twitter_relative_layout;

    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        View inflate = layoutInflater.inflate(R.layout.twitter_list, viewGroup, false);
        this.unbinder = ButterKnife.bind((Object) this, inflate);
        this.progressViewHelper = new ProgressViewHelper(getActivity());
        hideToolbar((Toolbar) inflate.findViewById(R.id.toolbar_top));
        setHasOptionsMenu(true);
        if (this.twitter_relative_layout != null) {
            this.bannerHelper.addBannerAd(this.twitter_relative_layout, this.list);
        }
        if (this.networkHelper.isConnected()) {
            try {
                this.progressViewHelper.show();
                ImageManager.loadBackgroundImageFromImageModel(this.twitterLayout, this.screenModel.getBackgroundImageName());
                this.list.setAdapter(new Builder(getActivity()).setTimeline(new UserTimeline.Builder().maxItemsPerRequest(Integer.valueOf(20)).screenName(this.screenModel.getUserName()).build()).setViewStyle(R.style.tw__TweetLightWithActionsStyle).setOnActionCallback(new Callback<Tweet>() {
                    public void success(Result<Tweet> result) {
                        aveTweetViewFragment.this.progressViewHelper.dismiss();
                    }

                    public void failure(TwitterException twitterException) {
                        aveTweetViewFragment.this.progressViewHelper.dismiss();
                        Toast.makeText(aveTweetViewFragment.this.getActivity(), R.string.common_error, 0).show();
                    }
                }).build());
                this.list.setLayoutManager(new LinearLayoutManager(getActivity()));
                this.list.addItemDecoration(new SimpleDividerItemDecoration(getActivity(), R.drawable.line_drawer));
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            Toast.makeText(getActivity().getApplicationContext(), getResources().getString(R.string.please_check_your_internet_connection), 1).show();
            getActivity().finish();
        }
        return inflate;
    }
}
