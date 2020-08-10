package com.mobiroller.fragments.youtubeadvanced;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.github.ybq.android.spinkit.SpinKitView;
import com.google.api.services.youtube.YouTube;
import com.google.api.services.youtube.model.PlaylistListResponse;
import com.mobiroller.adapters.youtubeadvanced.YoutubeAdvancedPlaylistAdapter;
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

public class YoutubePlaylistFragment extends BaseFragment {
    /* access modifiers changed from: private */
    public YoutubeAdvancedPlaylistAdapter adapter;
    /* access modifiers changed from: private */
    public String channelId;
    private GetChannelLatestPlayList getChannelLatestPlayList;
    /* access modifiers changed from: private */
    public ArrayList<Object> list;
    /* access modifiers changed from: private */
    public SpinKitView loadMoreProgressView;
    /* access modifiers changed from: private */
    public RelativeLayout mEmptyView;
    /* access modifiers changed from: private */
    public TextView mEmptyViewDescription;
    /* access modifiers changed from: private */
    public ImageView mEmptyViewImage;
    /* access modifiers changed from: private */
    public TextView mEmptyViewTitle;
    /* access modifiers changed from: private */
    public RecyclerView mRecyclerView;
    /* access modifiers changed from: private */
    public YouTube mService;
    /* access modifiers changed from: private */
    public String nextPageToken = null;
    /* access modifiers changed from: private */
    public ProgressViewHelper progressViewHelper;
    @Inject
    SharedPrefHelper sharedPrefHelper;

    private class GetChannelLatestPlayList extends AsyncTask<Void, Void, Void> {
        private GetChannelLatestPlayList() {
        }

        /* access modifiers changed from: protected */
        public Void doInBackground(Void... voidArr) {
            PlaylistListResponse playlistListResponse;
            try {
                String access$100 = YoutubePlaylistFragment.this.nextPageToken;
                String str = YoutubeRequestParams.req_detail_parts;
                if (access$100 != null) {
                    playlistListResponse = (PlaylistListResponse) YoutubePlaylistFragment.this.mService.playlists().list(str).setChannelId(YoutubePlaylistFragment.this.channelId).setMaxResults(Long.valueOf(25)).setPageToken(YoutubePlaylistFragment.this.nextPageToken).setKey(YoutubePlaylistFragment.this.getString(R.string.youtube_advanced_pro_api_key)).execute();
                } else {
                    playlistListResponse = (PlaylistListResponse) YoutubePlaylistFragment.this.mService.playlists().list(str).setChannelId(YoutubePlaylistFragment.this.channelId).setMaxResults(Long.valueOf(25)).setKey(YoutubePlaylistFragment.this.getString(R.string.youtube_advanced_pro_api_key)).execute();
                }
                YoutubePlaylistFragment.this.list.addAll(playlistListResponse.getItems());
                YoutubePlaylistFragment.this.nextPageToken = playlistListResponse.getNextPageToken();
            } catch (IOException e) {
                try {
                    e.printStackTrace();
                } catch (Exception unused) {
                    cancel(true);
                    return null;
                }
            }
            return null;
        }

        /* access modifiers changed from: protected */
        public void onPostExecute(Void voidR) {
            super.onPostExecute(voidR);
            YoutubePlaylistFragment.this.progressViewHelper.dismiss();
            YoutubePlaylistFragment.this.loadMoreProgressView.setVisibility(8);
            if (YoutubePlaylistFragment.this.list.size() == 0) {
                YoutubePlaylistFragment.this.mRecyclerView.setVisibility(8);
                YoutubePlaylistFragment.this.mEmptyView.setVisibility(0);
                YoutubePlaylistFragment.this.mEmptyViewImage.setImageDrawable(ContextCompat.getDrawable(YoutubePlaylistFragment.this.getActivity(), R.drawable.empty_playlist));
                YoutubePlaylistFragment.this.mEmptyViewTitle.setText(YoutubePlaylistFragment.this.getString(R.string.no_playlist));
                YoutubePlaylistFragment.this.mEmptyViewDescription.setText(YoutubePlaylistFragment.this.getString(R.string.no_playlist_description));
            } else if (YoutubePlaylistFragment.this.adapter != null) {
                YoutubePlaylistFragment.this.adapter.notifyDataSetChanged();
            } else {
                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(YoutubePlaylistFragment.this.getActivity());
                YoutubePlaylistFragment.this.mRecyclerView.setLayoutManager(linearLayoutManager);
                YoutubePlaylistFragment youtubePlaylistFragment = YoutubePlaylistFragment.this;
                youtubePlaylistFragment.adapter = new YoutubeAdvancedPlaylistAdapter(youtubePlaylistFragment.getActivity(), YoutubePlaylistFragment.this.list);
                YoutubePlaylistFragment.this.mRecyclerView.setAdapter(YoutubePlaylistFragment.this.adapter);
                YoutubePlaylistFragment.this.mRecyclerView.addOnScrollListener(new EndlessRecyclerViewScrollListener(linearLayoutManager) {
                    public void onLoadMore(int i, int i2) {
                        if (YoutubePlaylistFragment.this.nextPageToken != null) {
                            new GetChannelLatestPlayList().execute(new Void[0]);
                            YoutubePlaylistFragment.this.loadMoreProgressView.setVisibility(0);
                        }
                    }
                });
            }
        }
    }

    public static YoutubePlaylistFragment newInstance(String str) {
        YoutubePlaylistFragment youtubePlaylistFragment = new YoutubePlaylistFragment();
        Bundle bundle = new Bundle();
        bundle.putString(YoutubeConstants.INTENT_EXTRA_CHANNEL_ID, str);
        youtubePlaylistFragment.setArguments(bundle);
        return youtubePlaylistFragment;
    }

    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        this.mService = YoutubeAdvanceUtil.getService(this.sharedPrefHelper, getActivity());
        this.list = new ArrayList<>();
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
        this.mEmptyViewImage = (ImageView) inflate.findViewById(R.id.empty_image_view);
        this.mEmptyViewTitle = (TextView) inflate.findViewById(R.id.empty_header_view);
        this.mEmptyViewDescription = (TextView) inflate.findViewById(R.id.empty_description_view);
        this.progressViewHelper = new ProgressViewHelper(getActivity());
        this.loadMoreProgressView = (SpinKitView) inflate.findViewById(R.id.load_more_progress_view);
        this.loadMoreProgressView.setColor(this.sharedPrefHelper.getActionBarColor());
        this.loadMoreProgressView.setVisibility(8);
        this.progressViewHelper.show();
        this.getChannelLatestPlayList = new GetChannelLatestPlayList();
        this.getChannelLatestPlayList.execute(new Void[0]);
        return inflate;
    }

    public void onPause() {
        GetChannelLatestPlayList getChannelLatestPlayList2 = this.getChannelLatestPlayList;
        if (getChannelLatestPlayList2 != null) {
            getChannelLatestPlayList2.cancel(true);
        }
        super.onPause();
    }
}
