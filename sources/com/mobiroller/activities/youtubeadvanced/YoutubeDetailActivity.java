package com.mobiroller.activities.youtubeadvanced;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.bumptech.glide.Glide;
import com.github.ybq.android.spinkit.SpinKitView;
import com.google.android.exoplayer2.metadata.icy.IcyHeaders;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayer.ErrorReason;
import com.google.android.youtube.player.YouTubePlayer.OnInitializedListener;
import com.google.android.youtube.player.YouTubePlayer.PlayerStateChangeListener;
import com.google.android.youtube.player.YouTubePlayer.PlayerStyle;
import com.google.android.youtube.player.YouTubePlayer.Provider;
import com.google.android.youtube.player.YouTubePlayerView;
import com.google.api.services.youtube.YouTube;
import com.google.api.services.youtube.model.CommentThread;
import com.google.api.services.youtube.model.CommentThreadListResponse;
import com.google.api.services.youtube.model.ResourceId;
import com.google.api.services.youtube.model.Subscription;
import com.google.api.services.youtube.model.SubscriptionSnippet;
import com.google.api.services.youtube.model.VideoGetRatingResponse;
import com.google.api.services.youtube.model.VideoRating;
import com.mobiroller.DynamicConstants;
import com.mobiroller.adapters.youtubeadvanced.YoutubeAdvancedCommentAdapter;
import com.mobiroller.constants.YoutubeConstants;
import com.mobiroller.constants.YoutubeConstants.YoutubeRequestParams;
import com.mobiroller.fragments.aveYoutubeAdvancedViewFragment;
import com.mobiroller.helpers.NetworkHelper;
import com.mobiroller.helpers.ProgressViewHelper;
import com.mobiroller.helpers.SharedPrefHelper;
import com.mobiroller.helpers.UtilManager;
import com.mobiroller.mobi942763453128.R;
import com.mobiroller.models.youtube.SubscribeEvent;
import com.mobiroller.models.youtube.YoutubeVideoDetailModel;
import com.mobiroller.util.MobirollerIntent;
import com.mobiroller.util.PreviewUtil;
import com.mobiroller.util.YoutubeAdvanceUtil;
import com.mobiroller.util.YoutubeCacheManager;
import com.mobiroller.views.CircleImageView;
import com.mobiroller.views.EndlessRecyclerViewScrollListener;
import com.mobiroller.views.PullBackLayout;
import com.mobiroller.views.PullBackLayout.Callback;
import com.mobiroller.views.ReadMoreOptionView.Builder;
import com.sothree.slidinguppanel.SlidingUpPanelLayout;
import com.sothree.slidinguppanel.SlidingUpPanelLayout.PanelSlideListener;
import com.sothree.slidinguppanel.SlidingUpPanelLayout.PanelState;
import java.io.IOException;
import java.math.BigInteger;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Locale;
import org.apache.http.protocol.HTTP;
import org.greenrobot.eventbus.EventBus;
import p043io.reactivex.annotations.SchedulerSupport;

public class YoutubeDetailActivity extends YouTubeBaseActivity implements OnInitializedListener, Callback {
    YoutubeAdvancedCommentAdapter adapter;
    private boolean isSwipeFinish = false;
    ArrayList<Object> list = new ArrayList<>();
    SpinKitView loadMoreProgressView;
    /* access modifiers changed from: private */
    public CommentThreadListResponse mCommentThreadListResponse;
    private NetworkHelper mNetworkHelper;
    /* access modifiers changed from: private */
    public YouTube mService;
    /* access modifiers changed from: private */
    public YoutubeVideoDetailModel mVideo;
    @BindView(2131362649)
    RelativeLayout mainLayout;
    /* access modifiers changed from: private */
    public String myRating = SchedulerSupport.NONE;
    String nextPageToken = null;
    ProgressViewHelper progressViewHelper;
    @BindView(2131363006)
    PullBackLayout puller;
    private SharedPrefHelper sharedPrefHelper;
    @BindView(2131363152)
    SlidingUpPanelLayout slidingUpPanelLayout;
    @BindView(2131363436)
    YouTubePlayerView youTubePlayerView;
    @BindView(2131363416)
    CircleImageView youtubeChannelIcon;
    @BindView(2131363418)
    TextView youtubeChannelSubscriberCount;
    @BindView(2131363419)
    TextView youtubeChannelTitle;
    @BindView(2131363422)
    AppCompatImageView youtubeCommentIcon;
    @BindView(2131363424)
    RecyclerView youtubeCommentList;
    @BindView(2131363426)
    TextView youtubeCommentText;
    @BindView(2131363427)
    TextView youtubeCommentTextComment;
    @BindView(2131363429)
    AppCompatImageView youtubeDislikeIcon;
    @BindView(2131363431)
    TextView youtubeDislikeText;
    @BindView(2131363433)
    AppCompatImageView youtubeLikeIcon;
    @BindView(2131363435)
    TextView youtubeLikeText;
    @BindView(2131363437)
    AppCompatImageView youtubeShareIcon;
    @BindView(2131363442)
    ImageView youtubeSubscribeIcon;
    @BindView(2131363443)
    RelativeLayout youtubeSubscribeLayout;
    @BindView(2131363440)
    TextView youtubeSubscribeText;
    @BindView(2131363446)
    TextView youtubeVideoDescription;
    @BindView(2131363447)
    TextView youtubeVideoTitle;
    @BindView(2131363448)
    TextView youtubeVideoViewCount;

    private class GetCommentList extends AsyncTask<String, Void, CommentThreadListResponse> {
        private GetCommentList() {
        }

        /* access modifiers changed from: protected */
        public CommentThreadListResponse doInBackground(String... strArr) {
            try {
                if (YoutubeDetailActivity.this.mCommentThreadListResponse != null && YoutubeDetailActivity.this.mCommentThreadListResponse.getItems().size() < 20) {
                    return null;
                }
                String str = "snippet,replies";
                if (YoutubeDetailActivity.this.nextPageToken != null) {
                    YoutubeDetailActivity.this.mCommentThreadListResponse = (CommentThreadListResponse) YoutubeDetailActivity.this.mService.commentThreads().list(str).setVideoId(YoutubeDetailActivity.this.mVideo.getVideoId()).setMaxResults(Long.valueOf(20)).setPageToken(YoutubeDetailActivity.this.nextPageToken).setKey(YoutubeDetailActivity.this.getString(R.string.youtube_advanced_pro_api_key)).execute();
                } else {
                    YoutubeDetailActivity.this.mCommentThreadListResponse = (CommentThreadListResponse) YoutubeDetailActivity.this.mService.commentThreads().list(str).setVideoId(YoutubeDetailActivity.this.mVideo.getVideoId()).setMaxResults(Long.valueOf(20)).setKey(YoutubeDetailActivity.this.getString(R.string.youtube_advanced_pro_api_key)).execute();
                }
                YoutubeDetailActivity.this.nextPageToken = YoutubeDetailActivity.this.mCommentThreadListResponse.getNextPageToken();
                return YoutubeDetailActivity.this.mCommentThreadListResponse;
            } catch (Exception e) {
                e.printStackTrace();
                cancel(true);
                return null;
            }
        }

        /* access modifiers changed from: protected */
        public void onPostExecute(CommentThreadListResponse commentThreadListResponse) {
            super.onPostExecute(commentThreadListResponse);
            YoutubeDetailActivity.this.progressViewHelper.dismiss();
            YoutubeDetailActivity.this.loadMoreProgressView.setVisibility(8);
            if (commentThreadListResponse != null) {
                for (int i = 0; i < commentThreadListResponse.getItems().size(); i++) {
                    CommentThread commentThread = (CommentThread) commentThreadListResponse.getItems().get(i);
                    YoutubeDetailActivity.this.list.add(commentThread);
                    if (commentThread.getSnippet().getTotalReplyCount().longValue() != 0) {
                        YoutubeDetailActivity.this.list.addAll(commentThread.getReplies().getComments());
                    }
                }
                if (YoutubeDetailActivity.this.adapter != null) {
                    YoutubeDetailActivity.this.adapter.notifyDataSetChanged();
                } else {
                    YoutubeDetailActivity youtubeDetailActivity = YoutubeDetailActivity.this;
                    youtubeDetailActivity.adapter = new YoutubeAdvancedCommentAdapter(youtubeDetailActivity, youtubeDetailActivity.list);
                    LinearLayoutManager linearLayoutManager = new LinearLayoutManager(YoutubeDetailActivity.this);
                    YoutubeDetailActivity.this.youtubeCommentList.setLayoutManager(linearLayoutManager);
                    YoutubeDetailActivity.this.youtubeCommentList.setAdapter(YoutubeDetailActivity.this.adapter);
                    YoutubeDetailActivity.this.youtubeCommentList.addOnScrollListener(new EndlessRecyclerViewScrollListener(linearLayoutManager) {
                        public void onLoadMore(int i, int i2) {
                            YoutubeDetailActivity.this.loadMoreProgressView.setVisibility(0);
                            new GetCommentList().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, new String[0]);
                        }
                    });
                }
            }
        }
    }

    private class GetVideoRating extends AsyncTask<Void, Void, Void> {
        VideoGetRatingResponse videoGetRatingResponse;

        private GetVideoRating() {
        }

        /* access modifiers changed from: protected */
        public Void doInBackground(Void... voidArr) {
            try {
                this.videoGetRatingResponse = (VideoGetRatingResponse) YoutubeDetailActivity.this.mService.videos().getRating(YoutubeDetailActivity.this.mVideo.getVideoId()).execute();
                return null;
            } catch (Exception unused) {
                cancel(true);
                return null;
            }
        }

        /* access modifiers changed from: protected */
        public void onPostExecute(Void voidR) {
            super.onPostExecute(voidR);
            YoutubeDetailActivity.this.myRating = ((VideoRating) this.videoGetRatingResponse.getItems().get(0)).getRating();
            YoutubeCacheManager.putLikeStatus(YoutubeDetailActivity.this.mVideo.getVideoId(), YoutubeDetailActivity.this.myRating);
            YoutubeDetailActivity youtubeDetailActivity = YoutubeDetailActivity.this;
            youtubeDetailActivity.setLikeButtons(youtubeDetailActivity.myRating);
        }
    }

    private class SetVideoRating extends AsyncTask<String, Void, Void> {
        String action;

        private SetVideoRating() {
        }

        /* access modifiers changed from: protected */
        public Void doInBackground(String... strArr) {
            String str;
            try {
                this.action = strArr[0];
                YoutubeDetailActivity.this.mService.videos().rate(YoutubeDetailActivity.this.mVideo.getVideoId(), strArr[0]).execute();
                YoutubeCacheManager.putLikeStatus(YoutubeDetailActivity.this.mVideo.getVideoId(), this.action);
                if (strArr[0].equalsIgnoreCase(SchedulerSupport.NONE)) {
                    return null;
                }
                if (strArr[0].equalsIgnoreCase("like")) {
                    str = YoutubeDetailActivity.this.getString(R.string.youtube_action_like);
                } else {
                    str = YoutubeDetailActivity.this.getString(R.string.youtube_action_dislike);
                }
                Snackbar.make((View) YoutubeDetailActivity.this.puller, (CharSequence) str, -1).show();
                return null;
            } catch (Exception unused) {
                cancel(true);
                return null;
            }
        }

        /* access modifiers changed from: protected */
        public void onPostExecute(Void voidR) {
            super.onPostExecute(voidR);
            YoutubeDetailActivity.this.setLikeButtons(this.action);
        }
    }

    private class SubscribeToChannel extends AsyncTask<String, Void, Void> {
        Subscription response;

        private SubscribeToChannel() {
        }

        /* access modifiers changed from: protected */
        public Void doInBackground(String... strArr) {
            try {
                YouTube service = YoutubeAdvanceUtil.getService(UtilManager.sharedPrefHelper(), YoutubeDetailActivity.this);
                Subscription subscription = new Subscription();
                SubscriptionSnippet subscriptionSnippet = new SubscriptionSnippet();
                ResourceId resourceId = new ResourceId();
                resourceId.set(YoutubeConstants.INTENT_EXTRA_CHANNEL_ID, (Object) strArr[0]);
                resourceId.set("kind", (Object) "youtube#channel");
                subscriptionSnippet.setResourceId(resourceId);
                subscription.setSnippet(subscriptionSnippet);
                this.response = (Subscription) service.subscriptions().insert(YoutubeRequestParams.req_search_parts, subscription).execute();
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
            if (this.response != null) {
                EventBus.getDefault().post(new SubscribeEvent(aveYoutubeAdvancedViewFragment.channel.getId()));
                Snackbar.make((View) YoutubeDetailActivity.this.mainLayout, (CharSequence) YoutubeDetailActivity.this.getString(R.string.youtube_channel_subscribed, new Object[]{aveYoutubeAdvancedViewFragment.channel.getSnippet().getTitle()}), -1).show();
                YoutubeDetailActivity.this.setSubscribeButton();
            }
        }
    }

    public void onPull(float f) {
    }

    public void onPullCancel() {
    }

    public void onPullStart() {
    }

    /* access modifiers changed from: protected */
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_youtube_video_detail);
        ButterKnife.bind((Activity) this);
        this.sharedPrefHelper = UtilManager.sharedPrefHelper();
        this.mNetworkHelper = UtilManager.networkHelper();
        this.puller.setCallback(this);
        this.mService = YoutubeAdvanceUtil.getService(this.sharedPrefHelper, this);
        bindData();
    }

    public void onInitializationSuccess(Provider provider, final YouTubePlayer youTubePlayer, boolean z) {
        youTubePlayer.setPlayerStyle(PlayerStyle.DEFAULT);
        if (!z) {
            youTubePlayer.cueVideo(this.mVideo.getVideoId());
        }
        youTubePlayer.setPlayerStateChangeListener(new PlayerStateChangeListener() {
            public void onAdStarted() {
            }

            public void onError(ErrorReason errorReason) {
            }

            public void onLoading() {
            }

            public void onVideoEnded() {
            }

            public void onVideoStarted() {
            }

            public void onLoaded(String str) {
                youTubePlayer.play();
            }
        });
    }

    public void onInitializationFailure(Provider provider, YouTubeInitializationResult youTubeInitializationResult) {
        if (!youTubeInitializationResult.isUserRecoverableError()) {
            StringBuilder sb = new StringBuilder();
            sb.append("YouTubePlayer.onInitializationFailure(): ");
            sb.append(youTubeInitializationResult.toString());
            Toast.makeText(this, sb.toString(), 1).show();
        }
    }

    private void bindData() {
        String str = "youtubeVideo";
        if (getIntent().hasExtra(str)) {
            this.mVideo = (YoutubeVideoDetailModel) getIntent().getSerializableExtra(str);
        }
        this.youTubePlayerView.initialize(getString(R.string.youtube_advanced_pro_api_key), this);
        if (this.sharedPrefHelper.getGoogleSignInAccount() != null) {
            if (!this.mNetworkHelper.isConnected()) {
                Toast.makeText(this, R.string.please_check_your_internet_connection, 0).show();
                return;
            }
            String likeStatus = YoutubeCacheManager.getLikeStatus(this.mVideo.getVideoId());
            if (likeStatus.equalsIgnoreCase("null")) {
                new GetVideoRating().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, new Void[0]);
            } else {
                setLikeButtons(likeStatus);
            }
        }
        this.youtubeVideoTitle.setText(this.mVideo.getVideoTitle());
        this.youtubeVideoViewCount.setText(getString(R.string.youtube_video_view_count, new Object[]{NumberFormat.getIntegerInstance(Locale.GERMANY).format((long) this.mVideo.getVideoViewCount().intValue())}));
        String str2 = ",";
        String str3 = ".";
        this.youtubeLikeText.setText(YoutubeAdvanceUtil.getNumberWithExtension(this, this.mVideo.getLikeCount()).replace(str3, str2));
        this.youtubeDislikeText.setText(YoutubeAdvanceUtil.getNumberWithExtension(this, this.mVideo.getDislikeCount()).replace(str3, str2));
        this.youtubeCommentText.setText(YoutubeAdvanceUtil.getNumberWithExtension(this, this.mVideo.getCommentCount()).replace(str3, str2));
        this.youtubeCommentTextComment.setText(YoutubeAdvanceUtil.getNumberWithExtension(this, this.mVideo.getCommentCount()).replace(str3, str2));
        this.youtubeChannelTitle.setText(this.mVideo.getChannelName());
        if (aveYoutubeAdvancedViewFragment.isSubscribed) {
            setSubscribeButton();
        }
        Glide.with((Activity) this).load(aveYoutubeAdvancedViewFragment.channel.getSnippet().getThumbnails().getMedium().getUrl()).into((ImageView) this.youtubeChannelIcon);
        this.youtubeChannelSubscriberCount.setText(getString(R.string.youtube_subscriber_count, new Object[]{NumberFormat.getIntegerInstance(Locale.GERMANY).format((long) aveYoutubeAdvancedViewFragment.channel.getStatistics().getSubscriberCount().intValue())}));
        String str4 = "#b1b1b1";
        new Builder(this).textLength(8, 1).moreLabel(getString(R.string.youtube_action_show_more)).lessLabel(getString(R.string.youtube_action_show_less)).moreLabelColor(Color.parseColor(str4)).lessLabelColor(Color.parseColor(str4)).labelUnderLine(false).expandAnimation(true).build().addReadMoreTo(this.youtubeVideoDescription, this.mVideo.getVideoDescription());
    }

    @OnClick({2131363434})
    public void onClickLike() {
        if (DynamicConstants.MobiRoller_Stage) {
            PreviewUtil.showNotSupportedDialog(this);
        } else if (!this.mNetworkHelper.isConnected()) {
            Toast.makeText(this, R.string.please_check_your_internet_connection, 0).show();
        } else {
            if (!this.sharedPrefHelper.getGoogleSignInActive()) {
                Toast.makeText(this, R.string.login_google_not_activated, 0).show();
            } else if (this.sharedPrefHelper.getGoogleSignInAccount() != null) {
                String str = "like";
                boolean equalsIgnoreCase = this.myRating.equalsIgnoreCase(str);
                String str2 = "-1";
                String str3 = SchedulerSupport.NONE;
                String str4 = ",";
                String str5 = ".";
                if (equalsIgnoreCase) {
                    new SetVideoRating().execute(new String[]{str3});
                    this.youtubeLikeText.setText(YoutubeAdvanceUtil.getNumberWithExtension(this, this.mVideo.getLikeCount().add(new BigInteger(str2))).replace(str5, str4));
                } else {
                    new SetVideoRating().execute(new String[]{str});
                    boolean equalsIgnoreCase2 = this.myRating.equalsIgnoreCase(str3);
                    String str6 = IcyHeaders.REQUEST_HEADER_ENABLE_METADATA_VALUE;
                    if (equalsIgnoreCase2) {
                        BigInteger bigInteger = new BigInteger(str6);
                        YoutubeVideoDetailModel youtubeVideoDetailModel = this.mVideo;
                        youtubeVideoDetailModel.setLikeCount(youtubeVideoDetailModel.getLikeCount().add(bigInteger));
                        this.youtubeLikeText.setText(YoutubeAdvanceUtil.getNumberWithExtension(this, this.mVideo.getLikeCount()).replace(str5, str4));
                    } else {
                        BigInteger bigInteger2 = new BigInteger(str2);
                        YoutubeVideoDetailModel youtubeVideoDetailModel2 = this.mVideo;
                        youtubeVideoDetailModel2.setDislikeCount(youtubeVideoDetailModel2.getDislikeCount().add(bigInteger2));
                        this.youtubeDislikeText.setText(YoutubeAdvanceUtil.getNumberWithExtension(this, this.mVideo.getDislikeCount()).replace(str5, str4));
                        BigInteger bigInteger3 = new BigInteger(str6);
                        YoutubeVideoDetailModel youtubeVideoDetailModel3 = this.mVideo;
                        youtubeVideoDetailModel3.setLikeCount(youtubeVideoDetailModel3.getLikeCount().add(bigInteger3));
                        this.youtubeLikeText.setText(YoutubeAdvanceUtil.getNumberWithExtension(this, this.mVideo.getLikeCount()).replace(str5, str4));
                    }
                }
            } else {
                MobirollerIntent.startGoogleSignInActivity(this, aveYoutubeAdvancedViewFragment.youtubeScreenId);
            }
        }
    }

    @OnClick({2131363430})
    public void onClickDislike() {
        if (DynamicConstants.MobiRoller_Stage) {
            PreviewUtil.showNotSupportedDialog(this);
        } else if (!this.mNetworkHelper.isConnected()) {
            Toast.makeText(this, R.string.please_check_your_internet_connection, 0).show();
        } else {
            if (!this.sharedPrefHelper.getGoogleSignInActive()) {
                Toast.makeText(this, R.string.login_google_not_activated, 0).show();
            } else if (this.sharedPrefHelper.getGoogleSignInAccount() != null) {
                String str = "dislike";
                boolean equalsIgnoreCase = this.myRating.equalsIgnoreCase(str);
                String str2 = "-1";
                String str3 = SchedulerSupport.NONE;
                String str4 = ",";
                String str5 = ".";
                if (equalsIgnoreCase) {
                    new SetVideoRating().execute(new String[]{str3});
                    BigInteger bigInteger = new BigInteger(str2);
                    YoutubeVideoDetailModel youtubeVideoDetailModel = this.mVideo;
                    youtubeVideoDetailModel.setDislikeCount(youtubeVideoDetailModel.getDislikeCount().add(bigInteger));
                    this.youtubeDislikeText.setText(YoutubeAdvanceUtil.getNumberWithExtension(this, this.mVideo.getDislikeCount()).replace(str5, str4));
                } else {
                    new SetVideoRating().execute(new String[]{str});
                    boolean equalsIgnoreCase2 = this.myRating.equalsIgnoreCase(str3);
                    String str6 = IcyHeaders.REQUEST_HEADER_ENABLE_METADATA_VALUE;
                    if (equalsIgnoreCase2) {
                        BigInteger bigInteger2 = new BigInteger(str6);
                        YoutubeVideoDetailModel youtubeVideoDetailModel2 = this.mVideo;
                        youtubeVideoDetailModel2.setDislikeCount(youtubeVideoDetailModel2.getDislikeCount().add(bigInteger2));
                        this.youtubeDislikeText.setText(YoutubeAdvanceUtil.getNumberWithExtension(this, this.mVideo.getDislikeCount()).replace(str5, str4));
                    } else {
                        BigInteger bigInteger3 = new BigInteger(str6);
                        YoutubeVideoDetailModel youtubeVideoDetailModel3 = this.mVideo;
                        youtubeVideoDetailModel3.setDislikeCount(youtubeVideoDetailModel3.getDislikeCount().add(bigInteger3));
                        this.youtubeDislikeText.setText(YoutubeAdvanceUtil.getNumberWithExtension(this, this.mVideo.getDislikeCount()).replace(str5, str4));
                        BigInteger bigInteger4 = new BigInteger(str2);
                        YoutubeVideoDetailModel youtubeVideoDetailModel4 = this.mVideo;
                        youtubeVideoDetailModel4.setLikeCount(youtubeVideoDetailModel4.getLikeCount().add(bigInteger4));
                        this.youtubeLikeText.setText(YoutubeAdvanceUtil.getNumberWithExtension(this, this.mVideo.getLikeCount()).replace(str5, str4));
                    }
                }
            } else {
                MobirollerIntent.startGoogleSignInActivity(this, aveYoutubeAdvancedViewFragment.youtubeScreenId);
            }
        }
    }

    @OnClick({2131363423})
    public void onClickComment() {
        if (DynamicConstants.MobiRoller_Stage) {
            PreviewUtil.showNotSupportedDialog(this);
        } else if (!this.mNetworkHelper.isConnected()) {
            Toast.makeText(this, R.string.please_check_your_internet_connection, 0).show();
        } else {
            this.puller.setEnableSwipe(false);
            this.slidingUpPanelLayout.setPanelState(PanelState.EXPANDED);
            this.slidingUpPanelLayout.addPanelSlideListener(new PanelSlideListener() {
                public void onPanelSlide(View view, float f) {
                }

                public void onPanelStateChanged(View view, PanelState panelState, PanelState panelState2) {
                    if (panelState2 == PanelState.COLLAPSED) {
                        YoutubeDetailActivity.this.puller.setEnableSwipe(true);
                    }
                }
            });
            this.progressViewHelper = new ProgressViewHelper((Activity) this);
            this.loadMoreProgressView = (SpinKitView) findViewById(R.id.load_more_progress_view);
            this.loadMoreProgressView.setColor(this.sharedPrefHelper.getActionBarColor());
            this.loadMoreProgressView.setVisibility(8);
            this.progressViewHelper.show();
            new GetCommentList().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, new String[0]);
        }
    }

    public void onPullComplete() {
        this.isSwipeFinish = true;
        finish();
    }

    /* access modifiers changed from: protected */
    public void onPause() {
        super.onPause();
        if (this.isSwipeFinish) {
            overridePendingTransition(0, 0);
        }
    }

    @OnClick({2131363443})
    public void subscribeToChannel() {
        if (DynamicConstants.MobiRoller_Stage) {
            PreviewUtil.showNotSupportedDialog(this);
            return;
        }
        if (!this.sharedPrefHelper.getGoogleSignInActive()) {
            Toast.makeText(this, R.string.login_google_not_activated, 0).show();
        } else if (this.sharedPrefHelper.getGoogleSignInAccount() != null) {
            new SubscribeToChannel().execute(new String[]{aveYoutubeAdvancedViewFragment.channel.getId()});
        } else {
            MobirollerIntent.startGoogleSignInActivity(this, aveYoutubeAdvancedViewFragment.youtubeScreenId);
        }
    }

    /* access modifiers changed from: private */
    public void setSubscribeButton() {
        this.youtubeSubscribeLayout.setEnabled(false);
        this.youtubeSubscribeText.setText(getString(R.string.youtube_action_subscribed));
        this.youtubeSubscribeText.setTextColor(Color.parseColor("#959595"));
        this.youtubeSubscribeIcon.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.youtube_gray));
    }

    @OnClick({2131363438})
    public void shareLink() {
        Intent intent = new Intent();
        intent.setAction("android.intent.action.SEND");
        StringBuilder sb = new StringBuilder();
        sb.append("https://youtu.be/");
        sb.append(this.mVideo.getVideoId());
        intent.putExtra("android.intent.extra.TEXT", sb.toString());
        intent.setType(HTTP.PLAIN_TEXT_TYPE);
        startActivity(intent);
    }

    /* access modifiers changed from: private */
    public void setLikeButtons(String str) {
        this.myRating = str;
        if (str.equalsIgnoreCase("like")) {
            this.youtubeLikeIcon.setImageResource(R.drawable.ic_thumb_up_liked_24dp);
            this.youtubeDislikeIcon.setImageResource(R.drawable.ic_thumb_down_24dp);
        } else if (str.equalsIgnoreCase("dislike")) {
            this.youtubeLikeIcon.setImageResource(R.drawable.ic_thumb_up_black_24dp);
            this.youtubeDislikeIcon.setImageResource(R.drawable.ic_thumb_down_disliked_24dp);
        } else {
            this.youtubeLikeIcon.setImageResource(R.drawable.ic_thumb_up_black_24dp);
            this.youtubeDislikeIcon.setImageResource(R.drawable.ic_thumb_down_24dp);
        }
    }
}
