package com.mobiroller.fragments;

import android.os.AsyncTask;
import android.os.AsyncTask.Status;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout.OnRefreshListener;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import com.github.ybq.android.spinkit.SpinKitView;
import com.mobiroller.activities.aveRssContentViewPager;
import com.mobiroller.adapters.RssAdapter;
import com.mobiroller.constants.Constants;
import com.mobiroller.helpers.ProgressViewHelper;
import com.mobiroller.helpers.ScreenHelper;
import com.mobiroller.mobi942763453128.R;
import com.mobiroller.models.AdMobModel;
import com.mobiroller.models.RssFeaturedHeaderModel;
import com.mobiroller.models.RssModel;
import com.mobiroller.models.RssSliderHeaderModel;
import com.mobiroller.util.ImageManager;
import com.mobiroller.util.RssUtil;
import com.mobiroller.views.EndlessRecyclerViewScrollListener;
import com.rometools.rome.feed.synd.SyndEntry;
import com.rometools.rome.p052io.SyndFeedInput;
import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Executor;
import okhttp3.OkHttpClient;
import okhttp3.Request.Builder;

public class aveRSSViewFragment extends BaseModuleFragment implements OnRefreshListener {
    private static int ITEMS_PER_AD = 7;
    private static int START_AT = 3;
    public static List<Integer> layoutRssListHeight;
    /* access modifiers changed from: private */
    public boolean isFirst = true;
    /* access modifiers changed from: private */
    public boolean isLoadMore = false;
    /* access modifiers changed from: private */
    public boolean isRefreshLoading = false;
    private boolean isTaskCancelled = false;
    private List<Integer> layoutList;
    private int layoutType = 9;
    /* access modifiers changed from: private */
    public ArrayList<Object> listData;
    /* access modifiers changed from: private */
    public ArrayList<Object> listDataAdapter = new ArrayList<>();
    /* access modifiers changed from: private */
    public SpinKitView loadMoreProgress;
    @BindView(2131362631)
    SpinKitView loadMoreProgressView;
    private Parcelable mListState;
    @BindView(2131363206)
    SwipeRefreshLayout mSwipeRefreshLayout;
    /* access modifiers changed from: private */
    public RssDataController mTask;
    @BindView(2131362649)
    RelativeLayout mainLayout;
    private int pagination = 1;
    /* access modifiers changed from: private */
    public String paginationParameter;
    /* access modifiers changed from: private */
    public ArrayList<Object> postDataList = new ArrayList<>();
    ProgressViewHelper progressViewHelper;
    @BindView(2131362975)
    RecyclerView recyclerView;
    @BindView(2131363023)
    Button refreshButton;
    @BindView(2131363072)
    RelativeLayout rssLayout;
    @BindView(2131362873)
    RelativeLayout rssLayoutOverlay;
    /* access modifiers changed from: private */
    public RssAdapter rssListAdapter;
    @BindView(2131363076)
    LinearLayout rssListLayout;
    @BindView(2131363078)
    ImageView rssMainImg;
    private String rssPushTitle;
    Unbinder unbinder;
    /* access modifiers changed from: private */
    public String urlString;

    private class RssDataController extends AsyncTask<String, Integer, ArrayList<Object>> {
        private RssDataController() {
        }

        /* access modifiers changed from: protected */
        public void onPreExecute() {
            if ((!aveRSSViewFragment.this.isRefreshLoading && !aveRSSViewFragment.this.isLoadMore) || aveRSSViewFragment.this.isFirst) {
                if (aveRSSViewFragment.this.progressViewHelper == null) {
                    aveRSSViewFragment averssviewfragment = aveRSSViewFragment.this;
                    averssviewfragment.progressViewHelper = new ProgressViewHelper(averssviewfragment.getActivity());
                }
                if (aveRSSViewFragment.this.getUserVisibleHint()) {
                    aveRSSViewFragment.this.progressViewHelper.show();
                }
            }
        }

        /* access modifiers changed from: protected */
        public ArrayList<Object> doInBackground(String... strArr) {
            String str = strArr[0];
            aveRSSViewFragment.this.postDataList = new ArrayList();
            String str2 = "";
            try {
                try {
                    str2 = new OkHttpClient().newCall(new Builder().url(str).get().build()).execute().body().string();
                } catch (IOException unused) {
                    Log.e("FeedFetcher", "something went wrong on ");
                }
                for (SyndEntry access$500 : new SyndFeedInput().build((Reader) new StringReader(str2)).getEntries()) {
                    aveRSSViewFragment.this.setRssModel(access$500);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return aveRSSViewFragment.this.postDataList;
        }

        /* access modifiers changed from: protected */
        public void onPostExecute(ArrayList<Object> arrayList) {
            aveRSSViewFragment.this.onPostExecuted(arrayList, false);
        }
    }

    public aveRSSViewFragment() {
        Integer valueOf = Integer.valueOf(R.layout.rss_list_item_full_image);
        Integer valueOf2 = Integer.valueOf(R.layout.rss_list_item_full_image_above_title);
        Integer valueOf3 = Integer.valueOf(R.layout.rss_list_item_square_featured);
        this.layoutList = Arrays.asList(new Integer[]{Integer.valueOf(0), valueOf, Integer.valueOf(R.layout.rss_list_item_square_image), valueOf2, valueOf3, valueOf3, valueOf2, Integer.valueOf(R.layout.rss_list_item_classic), Integer.valueOf(R.layout.rss_list_item_classic_no_image), valueOf, Integer.valueOf(R.layout.rss_list_item_no_image_stragged)});
        this.paginationParameter = "?paged=";
    }

    static /* synthetic */ int access$1604(aveRSSViewFragment averssviewfragment) {
        int i = averssviewfragment.pagination + 1;
        averssviewfragment.pagination = i;
        return i;
    }

    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        View inflate = layoutInflater.inflate(R.layout.activity_postlist, viewGroup, false);
        this.unbinder = ButterKnife.bind((Object) this, inflate);
        Bundle arguments = getArguments();
        layoutRssListHeight = Arrays.asList(new Integer[]{Integer.valueOf(0), Integer.valueOf(getResources().getInteger(R.integer.rss_layout_1)), Integer.valueOf(getResources().getInteger(R.integer.rss_layout_2)), Integer.valueOf(getResources().getInteger(R.integer.rss_layout_2)), Integer.valueOf(getResources().getInteger(R.integer.rss_layout_4)), Integer.valueOf(getResources().getInteger(R.integer.rss_layout_5)), Integer.valueOf(getResources().getInteger(R.integer.rss_layout_2)), Integer.valueOf(getResources().getInteger(R.integer.rss_layout_2)), Integer.valueOf(getResources().getInteger(R.integer.rss_layout_2)), Integer.valueOf(getResources().getInteger(R.integer.rss_layout_1)), Integer.valueOf(getResources().getInteger(R.integer.rss_layout_2))});
        this.progressViewHelper = new ProgressViewHelper(getActivity());
        this.loadMoreProgress = (SpinKitView) inflate.findViewById(R.id.load_more_progress_view);
        this.loadMoreProgress.setColor(this.sharedPrefHelper.getActionBarColor());
        this.loadMoreProgress.setVisibility(8);
        this.rssLayout = (RelativeLayout) inflate.findViewById(R.id.rss_layout);
        String str = Constants.KEY_SCREEN_RSS_PUSH_TITLE;
        if (arguments.containsKey(str)) {
            this.rssPushTitle = arguments.getString(str);
        }
        this.listData = new ArrayList<>();
        loadUi();
        calculateAdCount();
        return inflate;
    }

    private void loadUi() {
        ImageManager.loadBackgroundImageFromImageModel(this.rssLayoutOverlay, this.screenModel.getBackgroundImageName());
        this.urlString = this.screenModel.getRssLink().trim();
        if (this.screenModel.getType() != null) {
            try {
                this.layoutType = Integer.parseInt(this.screenModel.getType());
            } catch (Exception unused) {
            }
        }
        this.mSwipeRefreshLayout.setOnRefreshListener(this);
        this.recyclerView.setVisibility(0);
        setLayoutType();
    }

    public void onRefresh() {
        this.isRefreshLoading = true;
        this.mTask = new RssDataController();
        this.mTask.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, new String[]{this.urlString});
    }

    public void onDestroyView() {
        super.onDestroyView();
        this.unbinder.unbind();
    }

    public void onResume() {
        super.onResume();
        if (this.mListState == null) {
            RssAdapter rssAdapter = this.rssListAdapter;
            if (rssAdapter == null || rssAdapter.getItemCount() == 0) {
                onRefresh();
            }
        }
        RssDataController rssDataController = this.mTask;
        if (rssDataController != null && rssDataController.isCancelled() && this.isTaskCancelled) {
            this.mTask = new RssDataController();
            this.mTask.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, new String[]{this.urlString});
        }
        if (!this.sharedPrefHelper.getIsNativeAdEnabled() && this.mainLayout != null) {
            this.bannerHelper.addBannerAd(this.mainLayout, this.rssLayoutOverlay);
        }
    }

    public void onPause() {
        super.onPause();
        RssDataController rssDataController = this.mTask;
        if (rssDataController == null) {
            return;
        }
        if (rssDataController.getStatus() == Status.RUNNING || this.mTask.getStatus() == Status.PENDING) {
            this.mTask.cancel(true);
            this.isTaskCancelled = true;
        }
    }

    /* access modifiers changed from: private */
    public void onPostExecuted(final ArrayList<Object> arrayList, final boolean z) {
        try {
            if (!this.isRefreshLoading) {
                if (!this.isFirst) {
                    if (this.isLoadMore) {
                        if (getActivity() != null) {
                            this.recyclerView.setItemAnimator(null);
                            getActivity().runOnUiThread(new Runnable() {
                                public void run() {
                                    for (int i = 0; i < arrayList.size(); i++) {
                                        if (!aveRSSViewFragment.this.isContains((RssModel) arrayList.get(i))) {
                                            aveRSSViewFragment.this.listData.add(arrayList.get(i));
                                            aveRSSViewFragment.this.listDataAdapter.add(arrayList.get(i));
                                            aveRSSViewFragment.this.rssListAdapter.notifyItemInserted(aveRSSViewFragment.this.listDataAdapter.size());
                                            if (!z) {
                                                aveRSSViewFragment averssviewfragment = aveRSSViewFragment.this;
                                                averssviewfragment.addAdMobModel(averssviewfragment.listDataAdapter.size());
                                            }
                                        }
                                    }
                                    aveRSSViewFragment.this.isLoadMore = false;
                                    aveRSSViewFragment.this.loadMoreProgress.setVisibility(8);
                                }
                            });
                        } else {
                            return;
                        }
                    }
                    if ((!this.isRefreshLoading && !this.isLoadMore) || this.isFirst) {
                        this.progressViewHelper.dismiss();
                        this.isFirst = false;
                    }
                    this.progressViewHelper.dismiss();
                    aveRssContentViewPager.setRssModelList(this.rssListAdapter.getAllData());
                    aveRssContentViewPager.notifyAdapter();
                }
            }
            this.listData = new ArrayList<>();
            this.listDataAdapter = new ArrayList<>();
            if (this.rssListAdapter != null) {
                this.rssListAdapter.notifyDataSetChanged();
            }
            for (int i = 0; i < arrayList.size(); i++) {
                this.listData.add(arrayList.get(i));
            }
            this.mSwipeRefreshLayout.setRefreshing(false);
            this.isRefreshLoading = false;
            setLayoutType();
            this.recyclerView.smoothScrollToPosition(0);
            this.progressViewHelper.dismiss();
            this.isFirst = false;
            this.progressViewHelper.dismiss();
            aveRssContentViewPager.setRssModelList(this.rssListAdapter.getAllData());
            aveRssContentViewPager.notifyAdapter();
        } catch (Exception e) {
            ProgressViewHelper progressViewHelper2 = this.progressViewHelper;
            if (progressViewHelper2 != null) {
                progressViewHelper2.dismiss();
            }
            e.printStackTrace();
        }
    }

    /* access modifiers changed from: private */
    public boolean isContains(RssModel rssModel) {
        if (this.listData.contains(rssModel)) {
            return true;
        }
        for (int i = 0; i < this.listData.size(); i++) {
            if ((this.listData.get(i) instanceof RssModel) && ((RssModel) this.listData.get(i)).getTitle().equalsIgnoreCase(rssModel.getTitle())) {
                return true;
            }
            if (this.listData.get(i) instanceof RssSliderHeaderModel) {
                for (int i2 = 0; i2 < ((RssSliderHeaderModel) this.listData.get(i)).getDataList().size(); i2++) {
                    if ((((RssSliderHeaderModel) this.listData.get(i)).getDataList().get(i2) instanceof RssModel) && ((RssModel) ((RssSliderHeaderModel) this.listData.get(i)).getDataList().get(i2)).getTitle().equalsIgnoreCase(rssModel.getTitle())) {
                        return true;
                    }
                }
            }
            if ((this.listData.get(i) instanceof RssFeaturedHeaderModel) && ((RssFeaturedHeaderModel) this.listData.get(i)).getFeaturedHeader().getTitle().equalsIgnoreCase(rssModel.getTitle())) {
                return true;
            }
        }
        return false;
    }

    /* access modifiers changed from: private */
    public void setRssModel(SyndEntry syndEntry) {
        ArrayList<Object> arrayList = this.postDataList;
        RssModel rssModel = new RssModel(syndEntry.getTitle(), RssUtil.rssGetImageUrl(syndEntry), RssUtil.rssGetDescription(syndEntry), syndEntry.getLink(), RssUtil.rssGetAuthor(), syndEntry.getPublishedDate());
        arrayList.add(rssModel);
    }

    private void setLayoutType() {
        StaggeredGridLayoutManager staggeredGridLayoutManager;
        int i = this.layoutType;
        RssAdapter rssAdapter = new RssAdapter(getActivity(), this.listDataAdapter, this.screenModel, ((Integer) this.layoutList.get(this.layoutType)).intValue(), this.screenId, i == 1 || i == 9, this.layoutType);
        this.rssListAdapter = rssAdapter;
        this.recyclerView.setAdapter(this.rssListAdapter);
        int i2 = this.layoutType;
        char c = (i2 == 1 || i2 == 5 || i2 == 9 || i2 == 10) ? (char) 2 : 1;
        int i3 = this.layoutType;
        if (i3 == 1 || i3 == 2 || i3 == 3) {
            if (this.listData.size() >= 5) {
                setSlider();
            } else {
                setListAdapter(0);
            }
        } else if (i3 != 5) {
            setListAdapter(0);
        } else if (this.listData.size() != 0) {
            setFeaturedHeader();
        }
        int i4 = this.layoutType;
        if (i4 == 6 || i4 == 7) {
            RelativeLayout relativeLayout = this.rssLayout;
            ScreenHelper screenHelper = this.screenHelper;
            relativeLayout.setPadding(0, ScreenHelper.getHeightForDevice(5, getActivity()), 0, 0);
        }
        if (c == 1) {
            staggeredGridLayoutManager = new StaggeredGridLayoutManager(1, 1);
        } else {
            staggeredGridLayoutManager = new StaggeredGridLayoutManager(2, 1);
        }
        this.recyclerView.setLayoutManager(staggeredGridLayoutManager);
        this.recyclerView.addOnScrollListener(new EndlessRecyclerViewScrollListener(staggeredGridLayoutManager) {
            public void onLoadMore(int i, int i2) {
                aveRSSViewFragment.this.isRefreshLoading = false;
                aveRSSViewFragment.this.isLoadMore = true;
                aveRSSViewFragment.this.loadMoreProgress.setVisibility(0);
                aveRSSViewFragment averssviewfragment = aveRSSViewFragment.this;
                averssviewfragment.mTask = new RssDataController();
                RssDataController access$1300 = aveRSSViewFragment.this.mTask;
                Executor executor = AsyncTask.THREAD_POOL_EXECUTOR;
                StringBuilder sb = new StringBuilder();
                sb.append(aveRSSViewFragment.this.urlString);
                sb.append(aveRSSViewFragment.this.paginationParameter);
                sb.append(aveRSSViewFragment.access$1604(aveRSSViewFragment.this));
                access$1300.executeOnExecutor(executor, new String[]{sb.toString()});
            }
        });
        this.recyclerView.setHasFixedSize(false);
    }

    private void setSlider() {
        if (this.listData.size() != 0) {
            ArrayList<Object> arrayList = this.listData;
            arrayList.add(0, new RssSliderHeaderModel(new ArrayList(arrayList.subList(0, 5))));
            this.listData.remove(1);
            this.listData.remove(1);
            this.listData.remove(1);
            this.listData.remove(1);
            this.listData.remove(1);
        }
        setListAdapter(0);
    }

    private void setFeaturedHeader() {
        ArrayList<Object> arrayList = this.listData;
        arrayList.add(0, new RssFeaturedHeaderModel((RssModel) arrayList.get(0)));
        this.listData.remove(1);
        setListAdapter(0);
    }

    private void setListAdapter(int i) {
        while (i < this.listData.size()) {
            this.listDataAdapter.add(this.listData.get(i));
            this.rssListAdapter.notifyItemInserted(this.listDataAdapter.size() - 1);
            addAdMobModel(this.listDataAdapter.size());
            i++;
        }
        if (this.listData.size() > START_AT && this.sharedPrefHelper.getIsNativeAdEnabled()) {
            int i2 = this.layoutType;
            if (i2 == 1 || i2 == 5) {
                this.listDataAdapter.add(START_AT, new AdMobModel());
                this.rssListAdapter.notifyItemInserted(START_AT);
            } else {
                this.listDataAdapter.add(START_AT - 1, new AdMobModel());
                this.rssListAdapter.notifyItemInserted(START_AT - 1);
            }
        }
        String str = this.rssPushTitle;
        if (str != null && this.isFirst) {
            this.rssListAdapter.findAndOpenContent(str);
        }
    }

    /* access modifiers changed from: private */
    public void addAdMobModel(int i) {
        if (this.sharedPrefHelper.getIsNativeAdEnabled()) {
            int i2 = this.layoutType;
            if (i2 == 1 || i2 == 5) {
                int i3 = i - 1;
                int i4 = ITEMS_PER_AD;
                if (i3 > i4 && i3 % i4 == START_AT) {
                    this.listDataAdapter.add(new AdMobModel());
                    this.rssListAdapter.notifyItemInserted(this.listDataAdapter.size() - 1);
                    return;
                }
                return;
            }
            int i5 = ITEMS_PER_AD;
            if (i % i5 == START_AT && i > i5) {
                this.listDataAdapter.add(new AdMobModel());
                this.rssListAdapter.notifyItemInserted(this.listDataAdapter.size() - 1);
            }
        }
    }

    private void calculateAdCount() {
        float intValue = (float) ((Integer) layoutRssListHeight.get(this.layoutType)).intValue();
        Display defaultDisplay = getActivity().getWindowManager().getDefaultDisplay();
        DisplayMetrics displayMetrics = new DisplayMetrics();
        defaultDisplay.getMetrics(displayMetrics);
        ITEMS_PER_AD = (int) ((((float) displayMetrics.heightPixels) / getResources().getDisplayMetrics().density) / intValue);
        ITEMS_PER_AD++;
        int i = this.layoutType;
        if (i == 1 || i == 5 || i == 9 || i == 10) {
            ITEMS_PER_AD *= 2;
        }
        if (ITEMS_PER_AD < 5) {
            ITEMS_PER_AD = 6;
        }
        int i2 = ITEMS_PER_AD;
        if (i2 % 2 == 1) {
            ITEMS_PER_AD = i2 + 1;
        }
    }

    public void onSaveInstanceState(Bundle bundle) {
        super.onSaveInstanceState(bundle);
        StringBuilder sb = new StringBuilder();
        sb.append(this.screenId);
        sb.append("list");
        bundle.putSerializable(sb.toString(), this.rssListAdapter.getAllRssModels());
        this.mListState = this.recyclerView.getLayoutManager().onSaveInstanceState();
        bundle.putParcelable(String.valueOf(this.screenId), this.mListState);
    }

    public void onViewStateRestored(Bundle bundle) {
        super.onViewStateRestored(bundle);
        if (bundle != null) {
            this.mListState = bundle.getParcelable(String.valueOf(this.screenId));
            StringBuilder sb = new StringBuilder();
            sb.append(this.screenId);
            sb.append("list");
            onPostExecuted((ArrayList) bundle.getSerializable(sb.toString()), true);
        }
    }
}
