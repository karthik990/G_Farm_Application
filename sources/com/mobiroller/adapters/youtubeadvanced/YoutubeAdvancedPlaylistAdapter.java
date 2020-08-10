package com.mobiroller.adapters.youtubeadvanced;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView.ViewHolder;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;
import com.google.api.services.youtube.model.Playlist;
import com.mobiroller.activities.youtubeadvanced.PlaylistDetailActivity;
import com.mobiroller.adapters.NativeAdsAdapter;
import com.mobiroller.adapters.NativeAdsAdapter.RecyclerViewFacebookAdItem;
import com.mobiroller.adapters.NativeAdsAdapter.RecyclerViewYoutubeAdMobAdItem;
import com.mobiroller.helpers.NetworkHelper;
import com.mobiroller.helpers.SharedPrefHelper;
import com.mobiroller.helpers.UtilManager;
import com.mobiroller.mobi942763453128.R;
import com.mobiroller.models.youtube.VideoFeaturedHeader;
import com.mobiroller.util.InterstitialAdsUtil;
import java.util.ArrayList;

public class YoutubeAdvancedPlaylistAdapter extends NativeAdsAdapter {
    private static final int ADMOB_VIEW = 0;
    private static final int YOUTUBE_FEATURED_VIEW = 2;
    private static final int YOUTUBE_VIEW = 1;
    /* access modifiers changed from: private */
    public Activity activity;
    private ArrayList<Object> data;
    private InterstitialAdsUtil interstitialAdsUtil;
    private SharedPrefHelper sharedPrefHelper = UtilManager.sharedPrefHelper();

    private class YoutubeAdvancedViewHolder extends ViewHolder {
        ImageView mImage;
        TextView mTitle;
        TextView mVideoCount;
        int position;
        View view;

        YoutubeAdvancedViewHolder(View view2) {
            super(view2);
            this.view = view2;
            this.mTitle = (TextView) view2.findViewById(R.id.video_title);
            this.mVideoCount = (TextView) view2.findViewById(R.id.video_count);
            this.mImage = (ImageView) view2.findViewById(R.id.video_image);
        }
    }

    public YoutubeAdvancedPlaylistAdapter(Activity activity2, ArrayList<Object> arrayList) {
        super(activity2);
        this.activity = activity2;
        this.data = arrayList;
        this.interstitialAdsUtil = new InterstitialAdsUtil((AppCompatActivity) activity2);
    }

    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        if (i == 1) {
            return new YoutubeAdvancedViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.layout_youtube_playlist_item, viewGroup, false));
        }
        if (i == 2) {
            YoutubeAdvancedViewHolder youtubeAdvancedViewHolder = new YoutubeAdvancedViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.video_list_item_type_3, viewGroup, false));
            youtubeAdvancedViewHolder.itemView.setPadding(0, 0, 0, (int) ((((float) 16) * this.activity.getResources().getDisplayMetrics().density) + 0.5f));
            return youtubeAdvancedViewHolder;
        } else if (!this.sharedPrefHelper.getNativeAddUnitID().startsWith("ca-")) {
            RecyclerViewFacebookAdItem recyclerViewFacebookAdItem = new RecyclerViewFacebookAdItem(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.empty_view, viewGroup, false), this.activity, 2, this.sharedPrefHelper);
            return recyclerViewFacebookAdItem;
        } else {
            RecyclerViewYoutubeAdMobAdItem recyclerViewYoutubeAdMobAdItem = new RecyclerViewYoutubeAdMobAdItem(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.empty_view, viewGroup, false), this.activity, 2, this.sharedPrefHelper);
            return recyclerViewYoutubeAdMobAdItem;
        }
    }

    public void onBindViewHolder(ViewHolder viewHolder, int i) {
        if (this.data.get(i) instanceof Playlist) {
            final YoutubeAdvancedViewHolder youtubeAdvancedViewHolder = (YoutubeAdvancedViewHolder) viewHolder;
            final Playlist playlist = (Playlist) this.data.get(i);
            youtubeAdvancedViewHolder.position = i;
            youtubeAdvancedViewHolder.mTitle.setText(playlist.getSnippet().getTitle());
            TextView textView = youtubeAdvancedViewHolder.mVideoCount;
            StringBuilder sb = new StringBuilder();
            sb.append(playlist.getContentDetails().getItemCount().toString());
            sb.append(" video");
            textView.setText(sb.toString());
            youtubeAdvancedViewHolder.mTitle.getViewTreeObserver().addOnGlobalLayoutListener(new OnGlobalLayoutListener() {
                public void onGlobalLayout() {
                    youtubeAdvancedViewHolder.mTitle.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                    int height = (int) (((float) youtubeAdvancedViewHolder.mTitle.getHeight()) / ((float) youtubeAdvancedViewHolder.mTitle.getLineHeight()));
                    if (youtubeAdvancedViewHolder.mTitle.getLineCount() != height) {
                        youtubeAdvancedViewHolder.mTitle.setLines(height);
                    }
                }
            });
            youtubeAdvancedViewHolder.view.setOnClickListener(new OnClickListener() {
                public void onClick(View view) {
                    if (!new NetworkHelper(YoutubeAdvancedPlaylistAdapter.this.activity).isConnected()) {
                        Toast.makeText(YoutubeAdvancedPlaylistAdapter.this.activity, R.string.please_check_your_internet_connection, 0).show();
                        return;
                    }
                    Intent intent = new Intent(YoutubeAdvancedPlaylistAdapter.this.activity, PlaylistDetailActivity.class);
                    intent.putExtra("title", playlist.getSnippet().getTitle());
                    intent.putExtra(TtmlNode.ATTR_ID, playlist.getId());
                    YoutubeAdvancedPlaylistAdapter.this.activity.startActivity(intent);
                }
            });
            Glide.with(this.activity).load(playlist.getSnippet().getThumbnails().getMedium().getUrl()).apply(new RequestOptions().placeholder((int) R.drawable.no_image)).addListener(new RequestListener<Drawable>() {
                public boolean onLoadFailed(GlideException glideException, Object obj, Target<Drawable> target, boolean z) {
                    youtubeAdvancedViewHolder.mImage.setImageDrawable(ContextCompat.getDrawable(YoutubeAdvancedPlaylistAdapter.this.activity, R.drawable.no_image));
                    youtubeAdvancedViewHolder.mImage.setVisibility(0);
                    return false;
                }

                public boolean onResourceReady(Drawable drawable, Object obj, Target<Drawable> target, DataSource dataSource, boolean z) {
                    youtubeAdvancedViewHolder.mImage.setVisibility(0);
                    return false;
                }
            }).into(youtubeAdvancedViewHolder.mImage);
        }
    }

    public int getItemCount() {
        return this.data.size();
    }

    public int getItemViewType(int i) {
        if (this.data.get(i) instanceof Playlist) {
            return 1;
        }
        return this.data.get(i) instanceof VideoFeaturedHeader ? 2 : 0;
    }

    public ArrayList<Object> getItems() {
        return this.data;
    }

    public void clearAll() {
        this.data = new ArrayList<>();
        notifyDataSetChanged();
    }
}
