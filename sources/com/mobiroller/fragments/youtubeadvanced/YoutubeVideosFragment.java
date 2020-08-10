package com.mobiroller.fragments.youtubeadvanced;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.github.ybq.android.spinkit.SpinKitView;
import com.google.api.services.youtube.YouTube;
import com.google.api.services.youtube.model.SearchListResponse;
import com.google.api.services.youtube.model.SearchResult;
import com.google.api.services.youtube.model.VideoListResponse;
import com.mobiroller.adapters.youtubeadvanced.YoutubeAdvancedAdapter;
import com.mobiroller.constants.YoutubeConstants;
import com.mobiroller.constants.YoutubeConstants.YoutubeRequestParams;
import com.mobiroller.fragments.BaseFragment;
import com.mobiroller.helpers.ProgressViewHelper;
import com.mobiroller.helpers.SharedPrefHelper;
import com.mobiroller.interfaces.FragmentComponent;
import com.mobiroller.mobi942763453128.R;
import com.mobiroller.util.YoutubeAdvanceUtil;
import com.mobiroller.views.EndlessRecyclerViewScrollListener;
import java.io.IOException;
import java.util.ArrayList;
import javax.inject.Inject;

public class YoutubeVideosFragment extends BaseFragment {
    /* access modifiers changed from: private */
    public String channelId;
    private GetChannelLatestVideos getChannelLatestVideos;
    /* access modifiers changed from: private */
    public YoutubeAdvancedAdapter mAdapter;
    /* access modifiers changed from: private */
    public RelativeLayout mEmptyView;
    /* access modifiers changed from: private */
    public ArrayList<Object> mList;
    /* access modifiers changed from: private */
    public String mNextPageToken = null;
    /* access modifiers changed from: private */
    public ProgressViewHelper mProgressViewHelper;
    /* access modifiers changed from: private */
    public RecyclerView mRecyclerView;
    /* access modifiers changed from: private */
    public YouTube mService;
    /* access modifiers changed from: private */
    public SpinKitView mSpinKitLoadMore;
    @Inject
    SharedPrefHelper sharedPrefHelper;

    private class GetChannelLatestVideos extends AsyncTask<Void, Void, Void> {
        private GetChannelLatestVideos() {
        }

        /* access modifiers changed from: protected */
        public Void doInBackground(Void... voidArr) {
            SearchListResponse searchListResponse;
            String str = "";
            try {
                String access$100 = YoutubeVideosFragment.this.mNextPageToken;
                String str2 = "video";
                String str3 = YoutubeRequestParams.req_search_order;
                String str4 = YoutubeRequestParams.req_search_parts;
                if (access$100 != null) {
                    searchListResponse = (SearchListResponse) YoutubeVideosFragment.this.mService.search().list(str4).setChannelId(YoutubeVideosFragment.this.channelId).setMaxResults(Long.valueOf(25)).setOrder(str3).setType(str2).setPageToken(YoutubeVideosFragment.this.mNextPageToken).setKey(YoutubeVideosFragment.this.getString(R.string.youtube_advanced_pro_api_key)).execute();
                } else {
                    searchListResponse = (SearchListResponse) YoutubeVideosFragment.this.mService.search().list(str4).setChannelId(YoutubeVideosFragment.this.channelId).setMaxResults(Long.valueOf(25)).setOrder(str3).setType(str2).setKey(YoutubeVideosFragment.this.getString(R.string.youtube_advanced_pro_api_key)).execute();
                }
                for (SearchResult searchResult : searchListResponse.getItems()) {
                    StringBuilder sb = new StringBuilder();
                    sb.append(str);
                    sb.append(searchResult.getId().getVideoId());
                    sb.append(",");
                    str = sb.toString();
                }
                YoutubeVideosFragment.this.mNextPageToken = searchListResponse.getNextPageToken();
            } catch (IOException e) {
                try {
                    e.printStackTrace();
                } catch (Exception unused) {
                    cancel(true);
                    return null;
                }
            }
            if (!str.isEmpty()) {
                try {
                    YoutubeVideosFragment.this.mList.addAll(((VideoListResponse) YoutubeVideosFragment.this.mService.videos().list("snippet,contentDetails,statistics").setId(str).setKey(YoutubeVideosFragment.this.getString(R.string.youtube_advanced_pro_api_key)).execute()).getItems());
                } catch (IOException e2) {
                    e2.printStackTrace();
                }
            }
            return null;
        }

        /* access modifiers changed from: protected */
        public void onPostExecute(Void voidR) {
            super.onPostExecute(voidR);
            YoutubeVideosFragment.this.mProgressViewHelper.dismiss();
            YoutubeVideosFragment.this.mSpinKitLoadMore.setVisibility(8);
            if (YoutubeVideosFragment.this.mList.size() == 0) {
                YoutubeVideosFragment.this.mRecyclerView.setVisibility(8);
                YoutubeVideosFragment.this.mEmptyView.setVisibility(0);
            } else if (YoutubeVideosFragment.this.mAdapter != null) {
                YoutubeVideosFragment.this.mAdapter.notifyDataSetChanged();
            } else {
                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(YoutubeVideosFragment.this.getActivity());
                YoutubeVideosFragment.this.mRecyclerView.setLayoutManager(linearLayoutManager);
                YoutubeVideosFragment youtubeVideosFragment = YoutubeVideosFragment.this;
                youtubeVideosFragment.mAdapter = new YoutubeAdvancedAdapter(youtubeVideosFragment.getActivity(), YoutubeVideosFragment.this.mList);
                YoutubeVideosFragment.this.mRecyclerView.setAdapter(YoutubeVideosFragment.this.mAdapter);
                YoutubeVideosFragment.this.mRecyclerView.addOnScrollListener(new EndlessRecyclerViewScrollListener(linearLayoutManager) {
                    public void onLoadMore(int i, int i2) {
                        if (YoutubeVideosFragment.this.mNextPageToken != null) {
                            new GetChannelLatestVideos().execute(new Void[0]);
                            YoutubeVideosFragment.this.mSpinKitLoadMore.setVisibility(0);
                        }
                    }
                });
            }
        }
    }

    public static YoutubeVideosFragment newInstance(String str) {
        YoutubeVideosFragment youtubeVideosFragment = new YoutubeVideosFragment();
        Bundle bundle = new Bundle();
        bundle.putString(YoutubeConstants.INTENT_EXTRA_CHANNEL_ID, str);
        youtubeVideosFragment.setArguments(bundle);
        return youtubeVideosFragment;
    }

    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        this.mService = YoutubeAdvanceUtil.getService(this.sharedPrefHelper, getActivity());
        this.mList = new ArrayList<>();
        this.channelId = getArguments().getString(YoutubeConstants.INTENT_EXTRA_CHANNEL_ID);
    }

    public Fragment injectFragment(FragmentComponent fragmentComponent) {
        fragmentComponent.inject(this);
        return this;
    }

    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        View inflate = layoutInflater.inflate(R.layout.layout_youtube_advanced_videos, viewGroup, false);
        this.mRecyclerView = (RecyclerView) inflate.findViewById(R.id.list);
        this.mEmptyView = (RelativeLayout) inflate.findViewById(R.id.empty_view);
        this.mProgressViewHelper = new ProgressViewHelper(getActivity());
        this.mSpinKitLoadMore = (SpinKitView) inflate.findViewById(R.id.load_more_progress_view);
        this.mSpinKitLoadMore.setColor(this.sharedPrefHelper.getActionBarColor());
        this.mSpinKitLoadMore.setVisibility(8);
        this.mProgressViewHelper.show();
        this.getChannelLatestVideos = new GetChannelLatestVideos();
        this.getChannelLatestVideos.execute(new Void[0]);
        return inflate;
    }

    public void onPause() {
        GetChannelLatestVideos getChannelLatestVideos2 = this.getChannelLatestVideos;
        if (getChannelLatestVideos2 != null) {
            getChannelLatestVideos2.cancel(true);
        }
        super.onPause();
    }
}
