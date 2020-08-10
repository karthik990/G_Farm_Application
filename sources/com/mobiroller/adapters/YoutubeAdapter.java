package com.mobiroller.adapters;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView.ViewHolder;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;
import com.google.android.youtube.player.YouTubeStandalonePlayer;
import com.mobiroller.activities.VideoActivity;
import com.mobiroller.adapters.NativeAdsAdapter.RecyclerViewFacebookAdItem;
import com.mobiroller.adapters.NativeAdsAdapter.RecyclerViewYoutubeAdMobAdItem;
import com.mobiroller.helpers.SharedPrefHelper;
import com.mobiroller.helpers.TimeHelper;
import com.mobiroller.helpers.UtilManager;
import com.mobiroller.mobi942763453128.R;
import com.mobiroller.models.ScreenModel;
import com.mobiroller.models.youtube.ItemDetail;
import com.mobiroller.models.youtube.VideoFeaturedHeader;
import com.mobiroller.util.InterstitialAdsUtil;
import java.util.ArrayList;

public class YoutubeAdapter extends NativeAdsAdapter {
    private static final int ADMOB_VIEW = 0;
    private static final int YOUTUBE_FEATURED_VIEW = 2;
    private static final int YOUTUBE_VIEW = 1;
    /* access modifiers changed from: private */
    public Activity activity;
    private ArrayList<Object> data;
    /* access modifiers changed from: private */
    public InterstitialAdsUtil interstitialAdsUtil;
    private int layoutRes;
    private int layoutType;
    public ScreenModel screenModel;
    private SharedPrefHelper sharedPrefHelper = UtilManager.sharedPrefHelper();

    private class YoutubeViewHolder extends ViewHolder {
        TextView mDuration;
        ImageView mImage;
        ProgressBar mProgress;
        TextView mTitle;
        int position;
        View view;

        YoutubeViewHolder(View view2) {
            super(view2);
            this.view = view2;
            this.mTitle = (TextView) view2.findViewById(R.id.video_title);
            this.mDuration = (TextView) view2.findViewById(R.id.video_duration);
            this.mImage = (ImageView) view2.findViewById(R.id.video_image);
            this.mProgress = (ProgressBar) view2.findViewById(R.id.video_progress);
        }
    }

    public YoutubeAdapter(Activity activity2, ArrayList<Object> arrayList, ScreenModel screenModel2, int i, int i2) {
        super(activity2);
        this.activity = activity2;
        this.data = arrayList;
        this.screenModel = screenModel2;
        this.layoutRes = i;
        this.layoutType = i2;
        this.interstitialAdsUtil = new InterstitialAdsUtil((AppCompatActivity) activity2);
    }

    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        if (i == 1) {
            return new YoutubeViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(this.layoutRes, viewGroup, false));
        }
        if (i == 2) {
            YoutubeViewHolder youtubeViewHolder = new YoutubeViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.video_list_item_type_3, viewGroup, false));
            youtubeViewHolder.itemView.setPadding(0, 0, 0, (int) ((((float) 16) * this.activity.getResources().getDisplayMetrics().density) + 0.5f));
            return youtubeViewHolder;
        } else if (!this.sharedPrefHelper.getNativeAddUnitID().startsWith("ca-")) {
            RecyclerViewFacebookAdItem recyclerViewFacebookAdItem = new RecyclerViewFacebookAdItem(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.empty_view, viewGroup, false), this.activity, this.layoutType, this.sharedPrefHelper);
            return recyclerViewFacebookAdItem;
        } else {
            RecyclerViewYoutubeAdMobAdItem recyclerViewYoutubeAdMobAdItem = new RecyclerViewYoutubeAdMobAdItem(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.empty_view, viewGroup, false), this.activity, this.layoutType, this.sharedPrefHelper);
            return recyclerViewYoutubeAdMobAdItem;
        }
    }

    public void onBindViewHolder(ViewHolder viewHolder, int i) {
        if (this.data.get(i) instanceof ItemDetail) {
            final YoutubeViewHolder youtubeViewHolder = (YoutubeViewHolder) viewHolder;
            final ItemDetail itemDetail = (ItemDetail) this.data.get(i);
            youtubeViewHolder.position = i;
            if (itemDetail != null) {
                if (!(itemDetail.getContentDetails() == null || itemDetail.getContentDetails().getDuration() == null)) {
                    youtubeViewHolder.mDuration.setText(TimeHelper.getDuration(itemDetail.getContentDetails().getDuration()));
                }
                if (!(itemDetail.getSnippet() == null || itemDetail.getSnippet().getTitle() == null)) {
                    youtubeViewHolder.mTitle.setText(itemDetail.getSnippet().getTitle());
                }
                youtubeViewHolder.mTitle.getViewTreeObserver().addOnGlobalLayoutListener(new OnGlobalLayoutListener() {
                    public void onGlobalLayout() {
                        youtubeViewHolder.mTitle.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                        int height = (int) (((float) youtubeViewHolder.mTitle.getHeight()) / ((float) youtubeViewHolder.mTitle.getLineHeight()));
                        if (youtubeViewHolder.mTitle.getLineCount() != height) {
                            youtubeViewHolder.mTitle.setLines(height);
                        }
                    }
                });
                youtubeViewHolder.view.setOnClickListener(new OnClickListener() {
                    public void onClick(View view) {
                        String id = itemDetail.getId();
                        if (itemDetail.getId() != null) {
                            YouTubeStandalonePlayer.createVideoIntent(YoutubeAdapter.this.activity, YoutubeAdapter.this.activity.getString(R.string.youtube_key), itemDetail.getId());
                            Intent intent = new Intent(YoutubeAdapter.this.activity, VideoActivity.class);
                            intent.putExtra("web_url", id);
                            intent.setFlags(67108864);
                            YoutubeAdapter.this.interstitialAdsUtil.checkInterstitialAds(intent);
                        }
                    }
                });
                if (itemDetail.getSnippet() != null && itemDetail.getSnippet().getThumbnails() != null && itemDetail.getSnippet().getThumbnails().getAvailableImage() != null && itemDetail.getSnippet().getThumbnails().getAvailableImage().getUrl() != null) {
                    Glide.with(this.activity).load(itemDetail.getSnippet().getThumbnails().getAvailableImage().getUrl()).apply(new RequestOptions().placeholder((int) R.drawable.no_image)).addListener(new RequestListener<Drawable>() {
                        public boolean onLoadFailed(GlideException glideException, Object obj, Target<Drawable> target, boolean z) {
                            youtubeViewHolder.mImage.setImageDrawable(ContextCompat.getDrawable(YoutubeAdapter.this.activity, R.drawable.no_image));
                            youtubeViewHolder.mProgress.setVisibility(8);
                            youtubeViewHolder.mImage.setVisibility(0);
                            return false;
                        }

                        public boolean onResourceReady(Drawable drawable, Object obj, Target<Drawable> target, DataSource dataSource, boolean z) {
                            youtubeViewHolder.mProgress.setVisibility(8);
                            youtubeViewHolder.mImage.setVisibility(0);
                            return false;
                        }
                    }).into(youtubeViewHolder.mImage);
                }
            }
        } else if (this.data.get(i) instanceof VideoFeaturedHeader) {
            final YoutubeViewHolder youtubeViewHolder2 = (YoutubeViewHolder) viewHolder;
            final ItemDetail itemDetail2 = ((VideoFeaturedHeader) this.data.get(i)).getItemDetail();
            youtubeViewHolder2.position = i;
            if (!(itemDetail2.getContentDetails() == null || itemDetail2.getContentDetails().getDuration() == null)) {
                youtubeViewHolder2.mDuration.setText(TimeHelper.getDuration(itemDetail2.getContentDetails().getDuration()));
            }
            if (!(itemDetail2.getSnippet() == null || itemDetail2.getSnippet().getTitle() == null)) {
                youtubeViewHolder2.mTitle.setText(itemDetail2.getSnippet().getTitle());
            }
            youtubeViewHolder2.view.setOnClickListener(new OnClickListener() {
                public void onClick(View view) {
                    if (itemDetail2.getId() != null) {
                        String id = itemDetail2.getId();
                        Intent intent = new Intent(YoutubeAdapter.this.activity, VideoActivity.class);
                        intent.putExtra("web_url", id);
                        intent.setFlags(67108864);
                        YoutubeAdapter.this.interstitialAdsUtil.checkInterstitialAds(intent);
                    }
                }
            });
            youtubeViewHolder2.mTitle.getViewTreeObserver().addOnGlobalLayoutListener(new OnGlobalLayoutListener() {
                public void onGlobalLayout() {
                    youtubeViewHolder2.mTitle.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                    int height = (int) (((float) youtubeViewHolder2.mTitle.getHeight()) / ((float) youtubeViewHolder2.mTitle.getLineHeight()));
                    if (youtubeViewHolder2.mTitle.getLineCount() != height) {
                        youtubeViewHolder2.mTitle.setLines(height);
                    }
                }
            });
            if (itemDetail2.getSnippet() != null && itemDetail2.getSnippet().getThumbnails() != null && itemDetail2.getSnippet().getThumbnails().getAvailableImage() != null && itemDetail2.getSnippet().getThumbnails().getAvailableImage().getUrl() != null) {
                Glide.with(this.activity).load(itemDetail2.getSnippet().getThumbnails().getAvailableImage().getUrl()).apply(new RequestOptions().placeholder((int) R.drawable.no_image)).addListener(new RequestListener<Drawable>() {
                    public boolean onLoadFailed(GlideException glideException, Object obj, Target<Drawable> target, boolean z) {
                        youtubeViewHolder2.mImage.setImageDrawable(ContextCompat.getDrawable(YoutubeAdapter.this.activity, R.drawable.no_image));
                        youtubeViewHolder2.mProgress.setVisibility(8);
                        youtubeViewHolder2.mImage.setVisibility(0);
                        return false;
                    }

                    public boolean onResourceReady(Drawable drawable, Object obj, Target<Drawable> target, DataSource dataSource, boolean z) {
                        youtubeViewHolder2.mProgress.setVisibility(8);
                        youtubeViewHolder2.mImage.setVisibility(0);
                        return false;
                    }
                }).into(youtubeViewHolder2.mImage);
            }
        }
    }

    public int getItemCount() {
        return this.data.size();
    }

    public int getItemViewType(int i) {
        if (this.data.get(i) instanceof ItemDetail) {
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
