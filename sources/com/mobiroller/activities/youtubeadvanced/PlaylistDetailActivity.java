package com.mobiroller.activities.youtubeadvanced;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.github.ybq.android.spinkit.SpinKitView;
import com.google.api.services.youtube.YouTube;
import com.google.api.services.youtube.model.PlaylistItem;
import com.google.api.services.youtube.model.PlaylistItemListResponse;
import com.google.api.services.youtube.model.Video;
import com.google.api.services.youtube.model.VideoListResponse;
import com.mobiroller.adapters.youtubeadvanced.YoutubeAdvancedAdapter;
import com.mobiroller.constants.YoutubeConstants.YoutubeRequestParams;
import com.mobiroller.helpers.ProgressViewHelper;
import com.mobiroller.helpers.SharedPrefHelper;
import com.mobiroller.helpers.ToolbarHelper;
import com.mobiroller.helpers.UtilManager;
import com.mobiroller.mobi942763453128.R;
import com.mobiroller.util.YoutubeAdvanceUtil;
import com.mobiroller.views.EndlessRecyclerViewScrollListener;
import java.io.IOException;
import java.util.ArrayList;

public class PlaylistDetailActivity extends AppCompatActivity {
    /* access modifiers changed from: private */
    public YoutubeAdvancedAdapter mAdapter;
    /* access modifiers changed from: private */
    public ArrayList<Object> mList;
    /* access modifiers changed from: private */
    public SpinKitView mLoadMoreProgressView;
    /* access modifiers changed from: private */
    public String mNextPageToken = null;
    /* access modifiers changed from: private */
    public String mPlaylistId;
    /* access modifiers changed from: private */
    public ProgressViewHelper mProgressViewHelper;
    @BindView(2131362972)
    RecyclerView mRecyclerView;
    /* access modifiers changed from: private */
    public YouTube mService;
    private SharedPrefHelper mSharedPref;
    private String mTitle;

    private class GetPlaylistDetails extends AsyncTask<Void, Void, Void> {
        private GetPlaylistDetails() {
        }

        /* access modifiers changed from: protected */
        public Void doInBackground(Void... voidArr) {
            PlaylistItemListResponse playlistItemListResponse;
            String str = "";
            try {
                String access$100 = PlaylistDetailActivity.this.mNextPageToken;
                String str2 = YoutubeRequestParams.req_search_parts;
                if (access$100 != null) {
                    playlistItemListResponse = (PlaylistItemListResponse) PlaylistDetailActivity.this.mService.playlistItems().list(str2).setPlaylistId(PlaylistDetailActivity.this.mPlaylistId).setMaxResults(Long.valueOf(25)).setPageToken(PlaylistDetailActivity.this.mNextPageToken).setKey(PlaylistDetailActivity.this.getString(R.string.youtube_advanced_pro_api_key)).execute();
                } else {
                    playlistItemListResponse = (PlaylistItemListResponse) PlaylistDetailActivity.this.mService.playlistItems().list(str2).setPlaylistId(PlaylistDetailActivity.this.mPlaylistId).setMaxResults(Long.valueOf(25)).setKey(PlaylistDetailActivity.this.getString(R.string.youtube_advanced_pro_api_key)).execute();
                }
                for (PlaylistItem playlistItem : playlistItemListResponse.getItems()) {
                    StringBuilder sb = new StringBuilder();
                    sb.append(str);
                    sb.append(playlistItem.getSnippet().getResourceId().getVideoId());
                    sb.append(",");
                    str = sb.toString();
                }
                PlaylistDetailActivity.this.mNextPageToken = playlistItemListResponse.getNextPageToken();
            } catch (IOException e) {
                try {
                    e.printStackTrace();
                } catch (Exception e2) {
                    e2.printStackTrace();
                    cancel(true);
                    return null;
                }
            }
            if (!str.isEmpty()) {
                try {
                    for (Video add : ((VideoListResponse) PlaylistDetailActivity.this.mService.videos().list("snippet,contentDetails,statistics").setId(str).setKey(PlaylistDetailActivity.this.getString(R.string.youtube_advanced_pro_api_key)).execute()).getItems()) {
                        PlaylistDetailActivity.this.mList.add(add);
                    }
                } catch (IOException e3) {
                    e3.printStackTrace();
                }
            }
            return null;
        }

        /* access modifiers changed from: protected */
        public void onPostExecute(Void voidR) {
            super.onPostExecute(voidR);
            PlaylistDetailActivity.this.mProgressViewHelper.dismiss();
            PlaylistDetailActivity.this.mLoadMoreProgressView.setVisibility(8);
            if (PlaylistDetailActivity.this.mAdapter != null) {
                PlaylistDetailActivity.this.mAdapter.notifyDataSetChanged();
                return;
            }
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(PlaylistDetailActivity.this);
            PlaylistDetailActivity.this.mRecyclerView.setLayoutManager(linearLayoutManager);
            PlaylistDetailActivity playlistDetailActivity = PlaylistDetailActivity.this;
            playlistDetailActivity.mAdapter = new YoutubeAdvancedAdapter(playlistDetailActivity, playlistDetailActivity.mList);
            PlaylistDetailActivity.this.mRecyclerView.setAdapter(PlaylistDetailActivity.this.mAdapter);
            PlaylistDetailActivity.this.mRecyclerView.addOnScrollListener(new EndlessRecyclerViewScrollListener(linearLayoutManager) {
                public void onLoadMore(int i, int i2) {
                    if (PlaylistDetailActivity.this.mNextPageToken != null) {
                        new GetPlaylistDetails().execute(new Void[0]);
                        PlaylistDetailActivity.this.mLoadMoreProgressView.setVisibility(0);
                    }
                }
            });
        }
    }

    /* access modifiers changed from: protected */
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView((int) R.layout.activity_youtube_playlist);
        ButterKnife.bind((Activity) this);
        this.mList = new ArrayList<>();
        this.mProgressViewHelper = new ProgressViewHelper((AppCompatActivity) this);
        this.mLoadMoreProgressView = (SpinKitView) findViewById(R.id.load_more_progress_view);
        this.mSharedPref = UtilManager.sharedPrefHelper();
        this.mLoadMoreProgressView.setColor(this.mSharedPref.getActionBarColor());
        this.mLoadMoreProgressView.setVisibility(8);
        this.mProgressViewHelper.show();
        String str = "title";
        if (getIntent().hasExtra(str)) {
            Intent intent = getIntent();
            String str2 = TtmlNode.ATTR_ID;
            if (intent.hasExtra(str2)) {
                this.mTitle = getIntent().getStringExtra(str);
                this.mPlaylistId = getIntent().getStringExtra(str2);
                setToolbarContext(this.mTitle);
            }
        }
        this.mService = YoutubeAdvanceUtil.getService(this.mSharedPref, this);
        new GetPlaylistDetails().execute(new Void[0]);
    }

    public void setToolbarContext(String str) {
        new ToolbarHelper(this.mSharedPref);
        ToolbarHelper.setToolbar(this, (Toolbar) findViewById(R.id.toolbar_top));
        getSupportActionBar().setTitle((CharSequence) str);
    }
}
