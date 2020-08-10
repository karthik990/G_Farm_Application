package com.mobiroller.activities.youtubeadvanced;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.format.DateUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.AlphaAnimation;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Space;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.content.ContextCompat;
import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.FragmentActivity;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.bumptech.glide.Glide;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.appbar.AppBarLayout.OnOffsetChangedListener;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.snackbar.Snackbar;
import com.google.api.services.youtube.YouTube;
import com.google.api.services.youtube.model.ResourceId;
import com.google.api.services.youtube.model.Subscription;
import com.google.api.services.youtube.model.SubscriptionSnippet;
import com.mobiroller.DynamicConstants;
import com.mobiroller.MobiRollerApplication;
import com.mobiroller.activities.base.AveActivity;
import com.mobiroller.constants.YoutubeConstants;
import com.mobiroller.constants.YoutubeConstants.YoutubeRequestParams;
import com.mobiroller.fragments.aveYoutubeAdvancedViewFragment;
import com.mobiroller.helpers.JSONParser;
import com.mobiroller.helpers.NetworkHelper;
import com.mobiroller.helpers.ScreenHelper;
import com.mobiroller.helpers.SharedPrefHelper;
import com.mobiroller.helpers.ToolbarHelper;
import com.mobiroller.interfaces.ActivityComponent;
import com.mobiroller.mobi942763453128.R;
import com.mobiroller.models.youtube.ChannelDetailModel;
import com.mobiroller.models.youtube.SubscribeEvent;
import com.mobiroller.util.MobirollerIntent;
import com.mobiroller.util.PreviewUtil;
import com.mobiroller.util.YoutubeAdvanceUtil;
import com.mobiroller.views.CircleImageView;
import java.io.IOException;
import java.text.NumberFormat;
import java.util.Locale;
import javax.inject.Inject;
import org.greenrobot.eventbus.EventBus;

public class ChannelDetailActivity extends AveActivity implements OnOffsetChangedListener {
    private static final int ALPHA_ANIMATIONS_DURATION = 200;
    private static final float PERCENTAGE_TO_HIDE_TITLE_DETAILS = 0.3f;
    private static final float PERCENTAGE_TO_SHOW_TITLE_AT_TOOLBAR = 0.9f;
    @Inject
    MobiRollerApplication app;
    @BindView(2131361939)
    AppBarLayout appBarLayout;
    /* access modifiers changed from: private */
    public ChannelDetailModel channelDetailModel;
    @BindView(2131362170)
    CollapsingToolbarLayout collapsingToolbarLayout;
    @BindView(2131362874)
    CoordinatorLayout coordinatorLayout;
    @BindView(2131362254)
    TextView description;
    @BindView(2131363180)
    TextView joinDate;
    @Inject
    JSONParser jsonParser;
    private boolean mIsTheTitleContainerVisible = true;
    private boolean mIsTheTitleVisible = false;
    @BindView(2131362194)
    RelativeLayout mainLayout;
    @BindView(2131362794)
    NestedScrollView nestedScrollView;
    @Inject
    NetworkHelper networkHelper;
    @Inject
    ScreenHelper screenHelper;
    @Inject
    SharedPrefHelper sharedPrefHelper;
    @BindView(2131363179)
    TextView statistics;
    @BindView(2131363270)
    Toolbar toolbar;
    @Inject
    ToolbarHelper toolbarHelper;
    @BindView(2131362157)
    Space youtubeAvatarSpace;
    @BindView(2131363415)
    CircleImageView youtubeChannelImage;
    @BindView(2131363417)
    TextView youtubeChannelName;
    @BindView(2131363420)
    TextView youtubeChannelNameTop;
    @BindView(2131363418)
    TextView youtubeChannelSubscriberCount;
    @BindView(2131363432)
    ImageView youtubeHeaderImage;
    @BindView(2131363411)
    RelativeLayout youtubeHeaderTopLayout;
    @BindView(2131363440)
    TextView youtubeSubscribeButton;
    @BindView(2131363441)
    ImageView youtubeSubscribeButtonBackground;
    @BindView(2131363443)
    RelativeLayout youtubeSubscribeLayout;
    @BindView(2131363444)
    RelativeLayout youtubeTopLayout;

    private class SubscribeToChannel extends AsyncTask<String, Void, Void> {
        Subscription response;

        private SubscribeToChannel() {
        }

        /* access modifiers changed from: protected */
        public Void doInBackground(String... strArr) {
            try {
                YouTube service = YoutubeAdvanceUtil.getService(ChannelDetailActivity.this.sharedPrefHelper, ChannelDetailActivity.this);
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
                EventBus.getDefault().post(new SubscribeEvent(ChannelDetailActivity.this.channelDetailModel.channelId));
                CoordinatorLayout coordinatorLayout = ChannelDetailActivity.this.coordinatorLayout;
                ChannelDetailActivity channelDetailActivity = ChannelDetailActivity.this;
                Snackbar.make((View) coordinatorLayout, (CharSequence) channelDetailActivity.getString(R.string.youtube_channel_subscribed, new Object[]{channelDetailActivity.channelDetailModel.channelName}), -1).show();
                ChannelDetailActivity.this.setSubscribeButton();
            }
        }
    }

    /* access modifiers changed from: protected */
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView((int) R.layout.activity_youtube_channel_detail);
        ButterKnife.bind((Activity) this);
        this.toolbarHelper.setStatusBar(this);
        setSupportActionBar(this.toolbar);
        this.toolbar.setNavigationIcon((int) R.drawable.ic_arrow_back_white_24dp);
        this.toolbar.setNavigationOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                ChannelDetailActivity.this.finish();
            }
        });
        String str = "";
        setTitle(str);
        this.toolbar.setTitle((CharSequence) str);
        this.appBarLayout.addOnOffsetChangedListener((OnOffsetChangedListener) this);
        String str2 = "channelModel";
        if (getIntent().hasExtra(str2)) {
            Glide.with((FragmentActivity) this).load(getIntent().getStringExtra("imageUrl")).into(this.youtubeHeaderImage);
            this.youtubeAvatarSpace.setVisibility(0);
            this.youtubeHeaderTopLayout.setVisibility(0);
            this.channelDetailModel = (ChannelDetailModel) getIntent().getSerializableExtra(str2);
            ChannelDetailModel channelDetailModel2 = this.channelDetailModel;
            if (channelDetailModel2 != null) {
                String formatDateTime = DateUtils.formatDateTime(this, channelDetailModel2.channelJoinDate.getValue(), 65536);
                this.description.setText(this.channelDetailModel.channelDescription);
                this.joinDate.setText(getString(R.string.youtube_join_date, new Object[]{formatDateTime}));
                this.statistics.setText(getString(R.string.youtube_video_view_count, new Object[]{NumberFormat.getIntegerInstance(Locale.GERMANY).format(this.channelDetailModel.channelTotalViewCount)}));
                this.youtubeChannelSubscriberCount.setText(getString(R.string.youtube_subscriber_count, new Object[]{NumberFormat.getIntegerInstance(Locale.GERMANY).format(Integer.valueOf(this.channelDetailModel.channelSubscriberCount))}));
                this.youtubeChannelName.setText(this.channelDetailModel.channelName);
                this.youtubeChannelNameTop.setText(this.channelDetailModel.channelName);
                Glide.with((FragmentActivity) this).load(this.channelDetailModel.channelImageUrl).into((ImageView) this.youtubeChannelImage);
                if (aveYoutubeAdvancedViewFragment.isSubscribed) {
                    setSubscribeButton();
                    return;
                }
                return;
            }
            Toast.makeText(this, getString(R.string.common_error), 0).show();
            finish();
        }
    }

    public AppCompatActivity injectActivity(ActivityComponent activityComponent) {
        activityComponent.inject(this);
        return this;
    }

    private void subscribeToYoutubeChannel(String str) {
        new SubscribeToChannel().execute(new String[]{str});
    }

    @OnClick({2131363440})
    public void subscribeChannel() {
        if (DynamicConstants.MobiRoller_Stage) {
            PreviewUtil.showNotSupportedDialog(this);
        } else if (!this.networkHelper.isConnected()) {
            Toast.makeText(this, R.string.please_check_your_internet_connection, 0).show();
        } else {
            if (!this.sharedPrefHelper.getGoogleSignInActive()) {
                Toast.makeText(this, R.string.login_google_not_activated, 0).show();
            } else if (this.sharedPrefHelper.getGoogleSignInAccount() != null) {
                subscribeToYoutubeChannel(this.channelDetailModel.channelId);
            } else {
                MobirollerIntent.startGoogleSignInActivity(this, aveYoutubeAdvancedViewFragment.youtubeScreenId);
            }
        }
    }

    /* access modifiers changed from: private */
    public void setSubscribeButton() {
        this.youtubeSubscribeButton.setText("");
        this.youtubeSubscribeButton.setVisibility(8);
        this.youtubeSubscribeButton.setEnabled(false);
        this.youtubeSubscribeLayout.setBackground(ContextCompat.getDrawable(this, R.drawable.youtube_subscribed));
        this.youtubeSubscribeButtonBackground.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.ic_check_24dp));
        this.youtubeSubscribeButtonBackground.setVisibility(0);
    }

    public void onOffsetChanged(AppBarLayout appBarLayout2, int i) {
        float abs = ((float) Math.abs(i)) / ((float) appBarLayout2.getTotalScrollRange());
        handleAlphaOnTitle(abs);
        handleToolbarTitleVisibility(abs);
    }

    private void handleToolbarTitleVisibility(float f) {
        if (f >= PERCENTAGE_TO_SHOW_TITLE_AT_TOOLBAR) {
            if (!this.mIsTheTitleVisible) {
                startAlphaAnimation(this.youtubeChannelNameTop, 200, 0);
                this.mIsTheTitleVisible = true;
                this.youtubeChannelNameTop.setVisibility(0);
            }
        } else if (this.mIsTheTitleVisible) {
            startAlphaAnimation(this.youtubeChannelNameTop, 200, 4);
            this.mIsTheTitleVisible = false;
            this.youtubeChannelNameTop.setVisibility(8);
        }
    }

    private void handleAlphaOnTitle(float f) {
        if (f >= PERCENTAGE_TO_HIDE_TITLE_DETAILS) {
            if (this.mIsTheTitleContainerVisible) {
                startAlphaAnimation(this.youtubeHeaderTopLayout, 200, 4);
                this.mIsTheTitleContainerVisible = false;
            }
        } else if (!this.mIsTheTitleContainerVisible) {
            startAlphaAnimation(this.youtubeHeaderTopLayout, 200, 0);
            this.mIsTheTitleContainerVisible = true;
        }
    }

    public static void startAlphaAnimation(View view, long j, int i) {
        AlphaAnimation alphaAnimation = i == 0 ? new AlphaAnimation(0.0f, 1.0f) : new AlphaAnimation(1.0f, 0.0f);
        alphaAnimation.setDuration(j);
        alphaAnimation.setFillAfter(true);
        view.startAnimation(alphaAnimation);
    }
}
