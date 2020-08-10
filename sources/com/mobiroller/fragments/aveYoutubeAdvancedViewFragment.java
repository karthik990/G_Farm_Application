package com.mobiroller.fragments;

import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Space;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.content.ContextCompat;
import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.afollestad.materialdialogs.MaterialDialog.Builder;
import com.bumptech.glide.Glide;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.appbar.AppBarLayout.LayoutParams;
import com.google.android.material.appbar.AppBarLayout.OnOffsetChangedListener;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.tabs.TabLayout;
import com.google.api.client.googleapis.json.GoogleJsonError.ErrorInfo;
import com.google.api.client.googleapis.json.GoogleJsonResponseException;
import com.google.api.services.youtube.YouTube;
import com.google.api.services.youtube.model.Channel;
import com.google.api.services.youtube.model.ChannelListResponse;
import com.google.api.services.youtube.model.ResourceId;
import com.google.api.services.youtube.model.Subscription;
import com.google.api.services.youtube.model.SubscriptionListResponse;
import com.google.api.services.youtube.model.SubscriptionSnippet;
import com.mobiroller.DynamicConstants;
import com.mobiroller.MobiRollerApplication;
import com.mobiroller.activities.aveYoutubeAdvancedView;
import com.mobiroller.activities.base.AveActivity;
import com.mobiroller.activities.youtubeadvanced.ChannelDetailActivity;
import com.mobiroller.constants.YoutubeConstants;
import com.mobiroller.constants.YoutubeConstants.YoutubeRequestParams;
import com.mobiroller.fragments.youtubeadvanced.YoutubePlaylistFragment;
import com.mobiroller.fragments.youtubeadvanced.YoutubeVideosFragment;
import com.mobiroller.helpers.UserHelper;
import com.mobiroller.mobi942763453128.R;
import com.mobiroller.models.events.OpenMenuModel;
import com.mobiroller.models.youtube.ChannelDetailModel;
import com.mobiroller.models.youtube.ChannelInfoClickEvent;
import com.mobiroller.models.youtube.SubscribeEvent;
import com.mobiroller.models.youtube.YoutubeNoNetworkEvent;
import com.mobiroller.util.ImageManager;
import com.mobiroller.util.MobirollerIntent;
import com.mobiroller.util.PreviewUtil;
import com.mobiroller.util.YoutubeAdvanceUtil;
import com.mobiroller.views.CircleImageView;
import com.mobiroller.views.custom.MobirollerToolbar;
import java.io.IOException;
import java.text.NumberFormat;
import java.util.Locale;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

public class aveYoutubeAdvancedViewFragment extends BaseModuleFragment implements OnOffsetChangedListener {
    private static final int ALPHA_ANIMATIONS_DURATION = 200;
    private static final float PERCENTAGE_TO_HIDE_TITLE_DETAILS = 0.3f;
    private static final float PERCENTAGE_TO_SHOW_TITLE_AT_TOOLBAR = 0.9f;
    public static Channel channel;
    public static String channelId;
    public static boolean isSubscribed;
    public static String youtubeScreenId;
    private MyPagerAdapter adapterViewPager;
    @BindView(2131361939)
    AppBarLayout appBarLayout;
    ChannelDetailModel channelDetailModel;
    @BindView(2131362170)
    CollapsingToolbarLayout collapsingToolbarLayout;
    @BindView(2131362874)
    CoordinatorLayout coordinatorLayout;
    private boolean mIsTheTitleContainerVisible = true;
    private boolean mIsTheTitleVisible = false;
    YouTube mService;
    @BindView(2131362649)
    RelativeLayout mainLayout;
    @BindView(2131362794)
    NestedScrollView nestedScrollView;
    @BindView(2131362873)
    RelativeLayout overlayLayout;
    @BindView(2131363271)
    MobirollerToolbar toolbarAppBar;
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
    @BindView(2131362321)
    AppCompatImageView youtubeMenuSpace;
    @BindView(2131363440)
    TextView youtubeSubscribeButton;
    @BindView(2131363441)
    ImageView youtubeSubscribeButtonBackground;
    @BindView(2131363443)
    RelativeLayout youtubeSubscribeLayout;
    @BindView(2131363444)
    RelativeLayout youtubeTopLayout;

    private class GetChannelInfo extends AsyncTask<Void, Void, Void> {
        private GetChannelInfo() {
        }

        /* access modifiers changed from: protected */
        public Void doInBackground(Void... voidArr) {
            try {
                aveYoutubeAdvancedViewFragment.channel = (Channel) ((ChannelListResponse) aveYoutubeAdvancedViewFragment.this.mService.channels().list("snippet,contentDetails,statistics").setId(aveYoutubeAdvancedViewFragment.channelId).setKey(aveYoutubeAdvancedViewFragment.this.getString(R.string.youtube_advanced_pro_api_key)).execute()).getItems().get(0);
                aveYoutubeAdvancedViewFragment.this.channelDetailModel = new ChannelDetailModel();
                aveYoutubeAdvancedViewFragment.this.channelDetailModel.channelId = aveYoutubeAdvancedViewFragment.channel.getId();
                aveYoutubeAdvancedViewFragment.this.channelDetailModel.channelName = aveYoutubeAdvancedViewFragment.channel.getSnippet().getTitle();
                aveYoutubeAdvancedViewFragment.this.channelDetailModel.channelDescription = aveYoutubeAdvancedViewFragment.channel.getSnippet().getDescription();
                aveYoutubeAdvancedViewFragment.this.channelDetailModel.channelImageUrl = aveYoutubeAdvancedViewFragment.channel.getSnippet().getThumbnails().getMedium().getUrl();
                aveYoutubeAdvancedViewFragment.this.channelDetailModel.channelJoinDate = aveYoutubeAdvancedViewFragment.channel.getSnippet().getPublishedAt();
                aveYoutubeAdvancedViewFragment.this.channelDetailModel.channelSubscriberCount = aveYoutubeAdvancedViewFragment.channel.getStatistics().getSubscriberCount().toString();
                aveYoutubeAdvancedViewFragment.this.channelDetailModel.channelTotalViewCount = aveYoutubeAdvancedViewFragment.channel.getStatistics().getViewCount();
                aveYoutubeAdvancedViewFragment.this.onPostChannelDetail(aveYoutubeAdvancedViewFragment.this.channelDetailModel);
                EventBus.getDefault().post(aveYoutubeAdvancedViewFragment.channel);
                EventBus.getDefault().post(aveYoutubeAdvancedViewFragment.this.channelDetailModel);
                aveYoutubeAdvancedViewFragment.this.getUserYoutubeSubscribeStatus(aveYoutubeAdvancedViewFragment.channelId);
                return null;
            } catch (GoogleJsonResponseException e) {
                if (!(e.getDetails() == null || e.getDetails().getErrors() == null || e.getDetails().getErrors().get(0) == null || ((ErrorInfo) e.getDetails().getErrors().get(0)).getReason() == null)) {
                    ErrorInfo errorInfo = (ErrorInfo) e.getDetails().getErrors().get(0);
                    if (errorInfo.getReason().equals("forbidden")) {
                        aveYoutubeAdvancedViewFragment.this.getActivity().runOnUiThread(new Runnable() {
                            public void run() {
                                new Builder(aveYoutubeAdvancedViewFragment.this.getActivity()).content((int) R.string.info_youtube_invalid_credentials).cancelable(false).positiveText((int) R.string.OK).show();
                            }
                        });
                    } else if (errorInfo.getReason().equals("quotaExceeded")) {
                        aveYoutubeAdvancedViewFragment.this.getActivity().runOnUiThread(new Runnable() {
                            public void run() {
                                new Builder(aveYoutubeAdvancedViewFragment.this.getActivity()).content((int) R.string.info_youtube_invalid_credentials).cancelable(false).positiveText((int) R.string.OK).show();
                            }
                        });
                    }
                }
                return null;
            } catch (Exception unused) {
                cancel(true);
                return null;
            }
        }

        /* access modifiers changed from: protected */
        public void onPostExecute(Void voidR) {
            super.onPostExecute(voidR);
        }
    }

    private class GetChannelSubscribeStatus extends AsyncTask<String, Void, Void> {
        SubscriptionListResponse response;

        private GetChannelSubscribeStatus() {
        }

        /* access modifiers changed from: protected */
        public Void doInBackground(String... strArr) {
            try {
                this.response = (SubscriptionListResponse) YoutubeAdvanceUtil.getService(aveYoutubeAdvancedViewFragment.this.sharedPrefHelper, aveYoutubeAdvancedViewFragment.this.getActivity()).subscriptions().list(YoutubeRequestParams.req_detail_parts).setForChannelId(strArr[0]).setMine(Boolean.valueOf(true)).execute();
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
            SubscriptionListResponse subscriptionListResponse = this.response;
            if (subscriptionListResponse == null) {
                return;
            }
            if (subscriptionListResponse.getItems().size() == 1) {
                aveYoutubeAdvancedViewFragment.this.setSubscribeButton();
                aveYoutubeAdvancedViewFragment.isSubscribed = true;
                if (aveYoutubeAdvancedViewFragment.this.channelDetailModel != null) {
                    aveYoutubeAdvancedViewFragment.this.sharedPrefHelper.setYoutubeChannelSubscriptionStatus(aveYoutubeAdvancedViewFragment.this.channelDetailModel.channelId, aveYoutubeAdvancedViewFragment.this.sharedPrefHelper.getUserId(), true);
                    return;
                }
                return;
            }
            if (aveYoutubeAdvancedViewFragment.this.channelDetailModel != null && aveYoutubeAdvancedViewFragment.this.sharedPrefHelper.getYoutubeChannelSubscriptionStatus(aveYoutubeAdvancedViewFragment.this.channelDetailModel.channelId, aveYoutubeAdvancedViewFragment.this.sharedPrefHelper.getUserId())) {
                aveYoutubeAdvancedViewFragment.this.setNotSubscribedButton();
            }
            if (aveYoutubeAdvancedViewFragment.this.channelDetailModel != null) {
                aveYoutubeAdvancedViewFragment.this.sharedPrefHelper.setYoutubeChannelSubscriptionStatus(aveYoutubeAdvancedViewFragment.this.channelDetailModel.channelId, aveYoutubeAdvancedViewFragment.this.sharedPrefHelper.getUserId(), false);
            }
        }
    }

    public class MyPagerAdapter extends FragmentPagerAdapter {
        private int NUM_ITEMS = 2;

        MyPagerAdapter(FragmentManager fragmentManager) {
            super(fragmentManager);
        }

        public int getCount() {
            return this.NUM_ITEMS;
        }

        public Fragment getItem(int i) {
            if (i == 0) {
                return YoutubeVideosFragment.newInstance(aveYoutubeAdvancedViewFragment.channelId);
            }
            if (i != 1) {
                return null;
            }
            return YoutubePlaylistFragment.newInstance(aveYoutubeAdvancedViewFragment.channelId);
        }

        public CharSequence getPageTitle(int i) {
            if (i == 0) {
                return aveYoutubeAdvancedViewFragment.this.getString(R.string.youtube_pro_videos);
            }
            return aveYoutubeAdvancedViewFragment.this.getString(R.string.youtube_pro_playlists);
        }
    }

    private class SubscribeToChannel extends AsyncTask<String, Void, Void> {
        Subscription response;

        private SubscribeToChannel() {
        }

        /* access modifiers changed from: protected */
        public Void doInBackground(String... strArr) {
            try {
                YouTube service = YoutubeAdvanceUtil.getService(aveYoutubeAdvancedViewFragment.this.sharedPrefHelper, aveYoutubeAdvancedViewFragment.this.getActivity());
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
                EventBus.getDefault().post(new SubscribeEvent(aveYoutubeAdvancedViewFragment.this.channelDetailModel.channelId));
                CoordinatorLayout coordinatorLayout = aveYoutubeAdvancedViewFragment.this.coordinatorLayout;
                aveYoutubeAdvancedViewFragment aveyoutubeadvancedviewfragment = aveYoutubeAdvancedViewFragment.this;
                Snackbar.make((View) coordinatorLayout, (CharSequence) aveyoutubeadvancedviewfragment.getString(R.string.youtube_channel_subscribed, aveyoutubeadvancedviewfragment.channelDetailModel.channelName), -1).show();
                aveYoutubeAdvancedViewFragment.this.setSubscribeButton();
            }
        }
    }

    @OnClick({2131362321})
    public void onClickMenuEmptyMenu() {
    }

    public void onCreateOptionsMenu(Menu menu, MenuInflater menuInflater) {
        super.onCreateOptionsMenu(menu, menuInflater);
        MobirollerToolbar mobirollerToolbar = this.toolbarAppBar;
        if (mobirollerToolbar != null) {
            if (mobirollerToolbar.getMenu() != null) {
                this.toolbarAppBar.getMenu().clear();
            }
            this.toolbarAppBar.inflateMenu(R.menu.youtube_advanced_menu);
        }
    }

    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        View inflate = layoutInflater.inflate(R.layout.layout_youtube_advanced, viewGroup, false);
        ButterKnife.bind((Object) this, inflate);
        ((AppCompatActivity) getActivity()).setSupportActionBar(this.toolbarAppBar);
        if (getArguments().containsKey(aveYoutubeAdvancedView.KEY_FROM_YOUTUBE_ACTIVITY)) {
            this.toolbarAppBar.setNavigationIcon((int) R.drawable.ic_arrow_back_white_24dp);
            this.toolbarAppBar.setNavigationOnClickListener(new OnClickListener() {
                public void onClick(View view) {
                    aveYoutubeAdvancedViewFragment.this.getActivity().finish();
                }
            });
        } else {
            this.toolbarAppBar.setNavigationOnClickListener(new OnClickListener() {
                public void onClick(View view) {
                    EventBus.getDefault().post(new OpenMenuModel());
                }
            });
        }
        ViewPager viewPager = (ViewPager) inflate.findViewById(R.id.view_pager);
        TabLayout tabLayout = (TabLayout) inflate.findViewById(R.id.sliding_tabs);
        tabLayout.setTabTextColors(Color.parseColor("#BFFFFFFF"), Color.parseColor("#ffffff"));
        youtubeScreenId = this.screenId;
        this.appBarLayout.addOnOffsetChangedListener((OnOffsetChangedListener) this);
        setHasOptionsMenu(true);
        getActivity().invalidateOptionsMenu();
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }
        channelId = this.screenModel.youtubeChannelID;
        this.adapterViewPager = new MyPagerAdapter(getChildFragmentManager());
        viewPager.setAdapter(this.adapterViewPager);
        viewPager.setCurrentItem(0);
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.setBackgroundColor(getTransparentColor(this.sharedPrefHelper.getActionBarColor()));
        setHasOptionsMenu(true);
        if (this.screenModel.getMainImageName() != null) {
            ImageManager.loadImageView(getActivity(), this.screenModel.getMainImageName().getImageURL(), this.youtubeHeaderImage);
        }
        ChannelDetailModel youtubeChannelInfo = this.sharedPrefHelper.getYoutubeChannelInfo(this.screenModel.youtubeChannelID);
        if (youtubeChannelInfo != null) {
            setChannelDetail(youtubeChannelInfo);
        }
        this.mService = YoutubeAdvanceUtil.getService(this.sharedPrefHelper, getActivity());
        return inflate;
    }

    public void onResume() {
        super.onResume();
        if (this.mainLayout != null) {
            this.bannerHelper.addBannerAd(this.mainLayout, this.overlayLayout);
        }
        if (((AveActivity) getActivity()).getToolbar() != null) {
            ((LayoutParams) this.toolbarAppBar.getLayoutParams()).setScrollFlags(0);
        }
    }

    public void onViewCreated(View view, Bundle bundle) {
        super.onViewCreated(view, bundle);
        new GetChannelInfo().execute(new Void[0]);
    }

    private int getTransparentColor(int i) {
        int alpha = Color.alpha(i);
        int red = Color.red(i);
        int green = Color.green(i);
        int blue = Color.blue(i);
        double d = (double) alpha;
        Double.isNaN(d);
        return Color.argb((int) (d * 0.25d), red, green, blue);
    }

    public boolean onOptionsItemSelected(MenuItem menuItem) {
        if (menuItem.getItemId() == R.id.action_channel_info) {
            startChannelDetailActivity();
        }
        return true;
    }

    @Subscribe
    public void onPostChannelInfoClickEvent(ChannelInfoClickEvent channelInfoClickEvent) {
        if (this.channelDetailModel.channelId.equalsIgnoreCase(channelInfoClickEvent.channelId)) {
            startChannelDetailActivity();
        }
    }

    private void startChannelDetailActivity() {
        Intent intent = new Intent(getActivity(), ChannelDetailActivity.class);
        intent.putExtra("channelModel", this.channelDetailModel);
        intent.putExtra("imageUrl", this.screenModel.getMainImageName().getImageURL());
        startActivity(intent);
    }

    public void onOffsetChanged(AppBarLayout appBarLayout2, int i) {
        float abs = ((float) Math.abs(i)) / ((float) appBarLayout2.getTotalScrollRange());
        handleAlphaOnTitle(abs);
        handleToolbarTitleVisibility(abs);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void setChannel(Channel channel2) {
        getUserYoutubeSubscribeStatus(channel2.getId());
        if (getActivity() != null) {
            Glide.with(getActivity()).load(channel2.getSnippet().getThumbnails().getMedium().getUrl()).into((ImageView) this.youtubeChannelImage);
        }
        this.youtubeChannelName.setText(channel2.getSnippet().getTitle());
        this.youtubeChannelNameTop.setText(channel2.getSnippet().getTitle());
        this.youtubeChannelSubscriberCount.setText(getString(R.string.youtube_subscriber_count, NumberFormat.getIntegerInstance(Locale.GERMANY).format((long) channel2.getStatistics().getSubscriberCount().intValue())));
    }

    public void setChannelDetail(ChannelDetailModel channelDetailModel2) {
        if (this.sharedPrefHelper.getYoutubeChannelSubscriptionStatus(channelDetailModel2.channelId, this.sharedPrefHelper.getUserId())) {
            setSubscribeButton();
        }
        getUserYoutubeSubscribeStatus(channelDetailModel2.channelId);
        Glide.with((Fragment) this).load(channelDetailModel2.channelImageUrl).into((ImageView) this.youtubeChannelImage);
        this.youtubeChannelName.setText(channelDetailModel2.channelName);
        this.youtubeChannelNameTop.setText(channelDetailModel2.channelName);
        this.youtubeChannelSubscriberCount.setText(getString(R.string.youtube_subscriber_count, NumberFormat.getIntegerInstance(Locale.GERMANY).format(Integer.valueOf(channelDetailModel2.channelSubscriberCount))));
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

    private void subscribeToYoutubeChannel(String str) {
        if (!this.networkHelper.isConnected()) {
            Toast.makeText(getActivity(), R.string.please_check_your_internet_connection, 0).show();
            return;
        }
        new SubscribeToChannel().execute(new String[]{str});
    }

    /* access modifiers changed from: private */
    public void getUserYoutubeSubscribeStatus(String str) {
        if (UserHelper.getUserLoginStatusForGoogle(this.sharedPrefHelper)) {
            new GetChannelSubscribeStatus().execute(new String[]{str});
        }
    }

    @OnClick({2131363440})
    public void subscribeChannel() {
        if (DynamicConstants.MobiRoller_Stage) {
            PreviewUtil.showNotSupportedDialog(getActivity());
            return;
        }
        if (!this.sharedPrefHelper.getGoogleSignInActive()) {
            Toast.makeText(getActivity(), R.string.login_google_not_activated, 0).show();
        } else if (this.sharedPrefHelper.getGoogleSignInAccount() != null) {
            subscribeToYoutubeChannel(channelId);
        } else {
            MobirollerIntent.startGoogleSignInActivity(getActivity(), youtubeScreenId);
        }
    }

    /* access modifiers changed from: private */
    public void setSubscribeButton() {
        this.youtubeSubscribeButton.setVisibility(8);
        this.youtubeSubscribeButton.setEnabled(false);
        this.youtubeSubscribeLayout.setBackground(ContextCompat.getDrawable(MobiRollerApplication.context, R.drawable.youtube_subscribed));
        this.youtubeSubscribeButtonBackground.setImageDrawable(ContextCompat.getDrawable(MobiRollerApplication.context, R.drawable.ic_check_24dp));
        this.youtubeSubscribeButtonBackground.setVisibility(0);
    }

    /* access modifiers changed from: private */
    public void setNotSubscribedButton() {
        this.youtubeSubscribeButton.setVisibility(0);
        this.youtubeSubscribeButton.setEnabled(true);
        this.youtubeSubscribeLayout.setBackground(ContextCompat.getDrawable(MobiRollerApplication.context, R.drawable.youtube_subscribe));
        this.youtubeSubscribeButtonBackground.setVisibility(8);
    }

    @Subscribe
    public void onPostChannelDetail(ChannelDetailModel channelDetailModel2) {
        this.channelDetailModel = channelDetailModel2;
        this.sharedPrefHelper.setYoutubeChannelInfo(channelDetailModel2.channelId, channelDetailModel2);
    }

    @Subscribe
    public void onPostSubscribeEvent(SubscribeEvent subscribeEvent) {
        if (this.channelDetailModel.channelId.equalsIgnoreCase(subscribeEvent.channelId)) {
            setSubscribeButton();
        }
    }

    @Subscribe
    public void onPostYoutubeNoNetworkEvent(YoutubeNoNetworkEvent youtubeNoNetworkEvent) {
        youtubeScreenId = youtubeNoNetworkEvent.screenId;
        if (youtubeNoNetworkEvent.screenModel.getMainImageName() != null) {
            Glide.with((Fragment) this).load(youtubeNoNetworkEvent.screenModel.getMainImageName().getImageURL()).into(this.youtubeHeaderImage);
        }
        ChannelDetailModel youtubeChannelInfo = this.sharedPrefHelper.getYoutubeChannelInfo(youtubeNoNetworkEvent.screenModel.youtubeChannelID);
        if (youtubeChannelInfo != null) {
            setChannelDetail(youtubeChannelInfo);
        }
    }

    @OnClick({2131362330})
    public void onClickChannelInfo() {
        EventBus.getDefault().post(new ChannelInfoClickEvent(this.channelDetailModel.channelId));
    }
}
