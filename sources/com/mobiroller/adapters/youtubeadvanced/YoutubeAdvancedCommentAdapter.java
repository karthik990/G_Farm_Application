package com.mobiroller.adapters.youtubeadvanced;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView.ViewHolder;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;
import com.google.api.services.youtube.model.Comment;
import com.google.api.services.youtube.model.CommentThread;
import com.mobiroller.adapters.NativeAdsAdapter;
import com.mobiroller.helpers.TimeHelper;
import com.mobiroller.mobi942763453128.R;
import com.mobiroller.views.CircleImageView;
import java.util.ArrayList;

public class YoutubeAdvancedCommentAdapter extends NativeAdsAdapter {
    private static final int YOUTUBE_COMMENT = 1;
    private static final int YOUTUBE_COMMENT_REPLY = 2;
    private Activity activity;
    private ArrayList<Object> data;

    private class YoutubeCommentViewHolder extends ViewHolder {
        TextView mComment;
        TextView mCommentCount;
        RelativeLayout mCommentLayout;
        TextView mCommenterDate;
        CircleImageView mCommenterImage;
        TextView mCommenterName;
        TextView mLikeCount;
        RelativeLayout mLikeLayout;
        int position;
        View view;

        YoutubeCommentViewHolder(View view2) {
            super(view2);
            this.view = view2;
            this.mCommenterName = (TextView) view2.findViewById(R.id.commenter_name);
            this.mCommenterDate = (TextView) view2.findViewById(R.id.comment_date);
            this.mComment = (TextView) view2.findViewById(R.id.commenter_comment);
            this.mLikeCount = (TextView) view2.findViewById(R.id.youtube_like_text);
            try {
                this.mCommentCount = (TextView) view2.findViewById(R.id.youtube_comment_text);
            } catch (Exception unused) {
            }
            this.mCommenterImage = (CircleImageView) view2.findViewById(R.id.commenter_image);
            this.mLikeLayout = (RelativeLayout) view2.findViewById(R.id.youtube_like_layout);
            this.mCommentLayout = (RelativeLayout) view2.findViewById(R.id.youtube_comment_layout);
        }
    }

    public YoutubeAdvancedCommentAdapter(Activity activity2, ArrayList<Object> arrayList) {
        super(activity2);
        this.data = arrayList;
        this.activity = activity2;
    }

    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        if (i == 1) {
            return new YoutubeCommentViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.layout_youtube_comment_item, viewGroup, false));
        }
        return new YoutubeCommentViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.layout_youtube_comment_reply_item, viewGroup, false));
    }

    public void onBindViewHolder(ViewHolder viewHolder, int i) {
        if (this.data.get(i) instanceof CommentThread) {
            final YoutubeCommentViewHolder youtubeCommentViewHolder = (YoutubeCommentViewHolder) viewHolder;
            CommentThread commentThread = (CommentThread) this.data.get(i);
            youtubeCommentViewHolder.mComment.setText(Html.fromHtml(commentThread.getSnippet().getTopLevelComment().getSnippet().getTextDisplay()));
            youtubeCommentViewHolder.mCommentCount.setText(commentThread.getSnippet().getTotalReplyCount().toString());
            youtubeCommentViewHolder.mCommenterDate.setText(TimeHelper.getTimeAgo((Context) this.activity, commentThread.getSnippet().getTopLevelComment().getSnippet().getPublishedAt().getValue()));
            youtubeCommentViewHolder.mCommenterName.setText(commentThread.getSnippet().getTopLevelComment().getSnippet().getAuthorDisplayName());
            youtubeCommentViewHolder.mLikeCount.setText(commentThread.getSnippet().getTopLevelComment().getSnippet().getLikeCount().toString());
            Glide.with(this.activity).load(commentThread.getSnippet().getTopLevelComment().getSnippet().getAuthorProfileImageUrl()).apply(new RequestOptions().placeholder((int) R.drawable.no_image)).listener(new RequestListener<Drawable>() {
                public boolean onResourceReady(Drawable drawable, Object obj, Target<Drawable> target, DataSource dataSource, boolean z) {
                    return false;
                }

                public boolean onLoadFailed(GlideException glideException, Object obj, Target<Drawable> target, boolean z) {
                    youtubeCommentViewHolder.mCommenterImage.setImageResource(R.drawable.no_image);
                    return false;
                }
            }).into((ImageView) youtubeCommentViewHolder.mCommenterImage);
        } else if (this.data.get(i) instanceof Comment) {
            final YoutubeCommentViewHolder youtubeCommentViewHolder2 = (YoutubeCommentViewHolder) viewHolder;
            Comment comment = (Comment) this.data.get(i);
            youtubeCommentViewHolder2.mComment.setText(Html.fromHtml(comment.getSnippet().getTextDisplay()));
            youtubeCommentViewHolder2.mCommenterDate.setText(TimeHelper.getTimeAgo((Context) this.activity, comment.getSnippet().getPublishedAt().getValue()));
            youtubeCommentViewHolder2.mCommenterName.setText(comment.getSnippet().getAuthorDisplayName());
            youtubeCommentViewHolder2.mLikeCount.setText(comment.getSnippet().getLikeCount().toString());
            Glide.with(this.activity).load(comment.getSnippet().getAuthorProfileImageUrl()).apply(new RequestOptions().placeholder((int) R.drawable.no_image)).listener(new RequestListener<Drawable>() {
                public boolean onResourceReady(Drawable drawable, Object obj, Target<Drawable> target, DataSource dataSource, boolean z) {
                    return false;
                }

                public boolean onLoadFailed(GlideException glideException, Object obj, Target<Drawable> target, boolean z) {
                    youtubeCommentViewHolder2.mCommenterImage.setImageResource(R.drawable.no_image);
                    return false;
                }
            }).into((ImageView) youtubeCommentViewHolder2.mCommenterImage);
        }
    }

    public int getItemCount() {
        return this.data.size();
    }

    public int getItemViewType(int i) {
        return this.data.get(i) instanceof CommentThread ? 1 : 2;
    }

    public ArrayList<Object> getItems() {
        return this.data;
    }

    public void clearAll() {
        this.data = new ArrayList<>();
        notifyDataSetChanged();
    }
}
