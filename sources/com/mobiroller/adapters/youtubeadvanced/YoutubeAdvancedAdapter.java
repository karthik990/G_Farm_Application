package com.mobiroller.adapters.youtubeadvanced;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.widget.ImageView;
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
import com.google.api.services.youtube.model.Video;
import com.mobiroller.activities.youtubeadvanced.YoutubeDetailActivity;
import com.mobiroller.adapters.NativeAdsAdapter;
import com.mobiroller.adapters.NativeAdsAdapter.RecyclerViewFacebookAdItem;
import com.mobiroller.adapters.NativeAdsAdapter.RecyclerViewYoutubeAdMobAdItem;
import com.mobiroller.helpers.SharedPrefHelper;
import com.mobiroller.helpers.TimeHelper;
import com.mobiroller.helpers.UtilManager;
import com.mobiroller.mobi942763453128.R;
import com.mobiroller.models.youtube.YoutubeVideoDetailModel;
import com.mobiroller.util.InterstitialAdsUtil;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;
import java.util.ArrayList;

public class YoutubeAdvancedAdapter extends NativeAdsAdapter {
    private static final int ADMOB_VIEW = 0;
    private static final int YOUTUBE_FEATURED_VIEW = 2;
    private static final int YOUTUBE_VIEW = 1;
    /* access modifiers changed from: private */
    public Activity activity;
    private ArrayList<Object> data;
    private InterstitialAdsUtil interstitialAdsUtil;
    private SharedPrefHelper sharedPrefHelper = UtilManager.sharedPrefHelper();

    private class YoutubeAdvancedViewHolder extends ViewHolder {
        TextView mCount;
        TextView mDate;
        TextView mDuration;
        ImageView mImage;
        TextView mTitle;
        int position;
        View view;

        YoutubeAdvancedViewHolder(View view2) {
            super(view2);
            this.view = view2;
            this.mTitle = (TextView) view2.findViewById(R.id.video_title);
            this.mDuration = (TextView) view2.findViewById(R.id.video_duration);
            this.mDate = (TextView) view2.findViewById(R.id.video_date);
            this.mCount = (TextView) view2.findViewById(R.id.video_view_count);
            this.mImage = (ImageView) view2.findViewById(R.id.video_image);
        }
    }

    public int getItemViewType(int i) {
        return 1;
    }

    public YoutubeAdvancedAdapter(Activity activity2, ArrayList<Object> arrayList) {
        super(activity2);
        this.activity = activity2;
        this.data = arrayList;
        this.interstitialAdsUtil = new InterstitialAdsUtil((AppCompatActivity) activity2);
    }

    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        if (i == 1) {
            return new YoutubeAdvancedViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.layout_youtube_video_item, viewGroup, false));
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
        if (this.data.get(i) instanceof Video) {
            final YoutubeAdvancedViewHolder youtubeAdvancedViewHolder = (YoutubeAdvancedViewHolder) viewHolder;
            final Video video = (Video) this.data.get(i);
            youtubeAdvancedViewHolder.position = i;
            youtubeAdvancedViewHolder.mDuration.setText(TimeHelper.getDuration(video.getContentDetails().getDuration()));
            youtubeAdvancedViewHolder.mTitle.setText(video.getSnippet().getTitle());
            youtubeAdvancedViewHolder.mDate.setText(TimeHelper.getTimeAgo((Context) this.activity, video.getSnippet().getPublishedAt().getValue()));
            youtubeAdvancedViewHolder.mCount.setText(this.activity.getString(R.string.youtube_video_view_count, new Object[]{getNumberWithExtension(video.getStatistics().getViewCount()).replace(".", ",")}));
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
                    YoutubeVideoDetailModel youtubeVideoDetailModel = new YoutubeVideoDetailModel();
                    youtubeVideoDetailModel.setVideo(video);
                    Intent intent = new Intent(YoutubeAdvancedAdapter.this.activity, YoutubeDetailActivity.class);
                    intent.putExtra("youtubeVideo", youtubeVideoDetailModel);
                    YoutubeAdvancedAdapter.this.activity.startActivity(intent);
                    YoutubeAdvancedAdapter.this.activity.overridePendingTransition(R.anim.push_up_in, R.anim.push_up_out);
                }
            });
            Glide.with(this.activity).load(video.getSnippet().getThumbnails().getMedium().getUrl()).apply(new RequestOptions().placeholder((int) R.drawable.no_image)).addListener(new RequestListener<Drawable>() {
                public boolean onLoadFailed(GlideException glideException, Object obj, Target<Drawable> target, boolean z) {
                    youtubeAdvancedViewHolder.mImage.setImageDrawable(ContextCompat.getDrawable(YoutubeAdvancedAdapter.this.activity, R.drawable.no_image));
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

    public ArrayList<Object> getItems() {
        return this.data;
    }

    public void clearAll() {
        this.data = new ArrayList<>();
        notifyDataSetChanged();
    }

    private String getNumberWithExtension(BigInteger bigInteger) {
        if (BigInteger.valueOf(1000).compareTo(bigInteger) > 0) {
            return bigInteger.toString();
        }
        if (BigInteger.valueOf(1000000).compareTo(bigInteger) > 0) {
            BigDecimal divide = new BigDecimal(bigInteger).divide(new BigDecimal(1000), 1, RoundingMode.HALF_EVEN);
            return this.activity.getString(R.string.youtube_count_thousand, new Object[]{String.valueOf(divide.floatValue())});
        }
        BigDecimal divide2 = new BigDecimal(bigInteger).divide(new BigDecimal(1000000), 1, RoundingMode.HALF_EVEN);
        return this.activity.getString(R.string.youtube_count_million, new Object[]{String.valueOf(divide2.floatValue())});
    }
}
