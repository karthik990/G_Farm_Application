package com.mobiroller.fragments;

import android.os.Bundle;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.Toast;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout.OnRefreshListener;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import com.github.ybq.android.spinkit.SpinKitView;
import com.mobiroller.adapters.YoutubeAdapter;
import com.mobiroller.helpers.ProgressViewHelper;
import com.mobiroller.mobi942763453128.R;
import com.mobiroller.models.AdMobModel;
import com.mobiroller.models.ScreenModel;
import com.mobiroller.models.TableItemsModel;
import com.mobiroller.models.youtube.ItemDetail;
import com.mobiroller.models.youtube.VideoFeaturedHeader;
import com.mobiroller.models.youtube.YoutubeDetailModel;
import com.mobiroller.util.ImageManager;
import com.mobiroller.util.YoutubeUtil;
import com.mobiroller.views.EndlessRecyclerViewScrollListener;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

public class aveYoutubeViewFragment extends BaseModuleFragment {
    private static int ITEMS_PER_AD = 7;
    private static int START_AT = 3;
    private YoutubeAdapter adapter;
    /* access modifiers changed from: private */
    public boolean assignLinksManually = true;
    /* access modifiers changed from: private */
    public String channelId = null;
    private int layoutType = 1;
    private int[] layout_list = {0, R.layout.video_list_item_type_1, R.layout.video_list_item_type_1, R.layout.video_list_item_type_2, R.layout.video_list_item_type_3, R.layout.video_list_item_type_4, R.layout.video_list_item_type_5};
    @BindView(2131363370)
    RecyclerView list;
    @BindView(2131362631)
    SpinKitView loadMoreProgress;
    private Parcelable mListState;
    @BindView(2131362649)
    RelativeLayout mainLayout;
    /* access modifiers changed from: private */
    public int manuallyCount = 0;
    @BindView(2131362873)
    RelativeLayout overlayLayout;
    ProgressViewHelper progressViewHelper;
    @BindView(2131363206)
    SwipeRefreshLayout swipeRefreshLayout;
    Unbinder unbinder;
    /* access modifiers changed from: private */
    public ArrayList<Object> videoList = new ArrayList<>();
    /* access modifiers changed from: private */
    public YoutubeUtil youtubeUtil;

    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        View inflate = layoutInflater.inflate(R.layout.video_list, viewGroup, false);
        this.unbinder = ButterKnife.bind((Object) this, inflate);
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }
        this.progressViewHelper = new ProgressViewHelper(getActivity());
        this.loadMoreProgress.setColor(this.sharedPrefHelper.getActionBarColor());
        this.loadMoreProgress.setVisibility(8);
        this.swipeRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            public void onRefresh() {
                aveYoutubeViewFragment.this.videoList = new ArrayList();
                aveYoutubeViewFragment aveyoutubeviewfragment = aveYoutubeViewFragment.this;
                aveyoutubeviewfragment.youtubeUtil = new YoutubeUtil(aveyoutubeviewfragment.screenId);
                aveYoutubeViewFragment.this.setLayoutType();
                aveYoutubeViewFragment.this.manuallyCount = 0;
                aveYoutubeViewFragment.this.getVideoList();
            }
        });
        this.youtubeUtil = new YoutubeUtil(this.screenId);
        this.layoutType = this.screenModel.getLayoutType();
        if (this.screenModel.isAssignLinksManually() != null) {
            this.assignLinksManually = this.screenModel.isAssignLinksManually().booleanValue();
        }
        this.channelId = this.screenModel.getYoutubeChannelId();
        hideToolbar((Toolbar) inflate.findViewById(R.id.toolbar_top));
        ImageManager.loadBackgroundImageFromImageModel(this.overlayLayout, this.screenModel.getBackgroundImageName());
        setLayoutType();
        return inflate;
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onYoutubeSearchModelEvent(YoutubeDetailModel youtubeDetailModel) {
        if (this.progressViewHelper.isShowing()) {
            this.progressViewHelper.dismiss();
        }
        if (this.swipeRefreshLayout.isRefreshing()) {
            this.swipeRefreshLayout.setRefreshing(false);
        }
        this.loadMoreProgress.setVisibility(8);
        if (youtubeDetailModel.getItems() != null) {
            for (ItemDetail addVideoToAdapter : youtubeDetailModel.getItems()) {
                addVideoToAdapter(addVideoToAdapter);
            }
        }
    }

    private void addVideoToAdapter(Object obj) {
        boolean z = obj instanceof ItemDetail;
        if (z) {
            if (this.adapter.getItemCount() != 0 || this.layoutType != 1) {
                this.videoList.add(obj);
            } else if (z) {
                this.videoList.add(new VideoFeaturedHeader((ItemDetail) obj));
            } else if (obj instanceof VideoFeaturedHeader) {
                this.videoList.add(obj);
            }
        }
        this.adapter.notifyItemInserted(this.videoList.size());
        addAdMobModel(this.videoList.size());
    }

    private void setListAdapter(int i, List<Object> list2) {
        while (i < list2.size()) {
            this.videoList.add(list2.get(i));
            this.adapter.notifyItemInserted(this.videoList.size() - 1);
            addAdMobModel(this.videoList.size());
            i++;
        }
        if (list2.size() > START_AT && this.sharedPrefHelper.getIsNativeAdEnabled()) {
            this.videoList.add(START_AT - 1, new AdMobModel());
            this.adapter.notifyItemInserted(START_AT - 1);
        }
    }

    private void addAdMobModel(int i) {
        int i2 = ITEMS_PER_AD;
        if (i % i2 == START_AT && i > i2 && this.sharedPrefHelper.getIsNativeAdEnabled()) {
            this.videoList.add(new AdMobModel());
            this.adapter.notifyItemInserted(this.videoList.size() - 1);
        }
    }

    public void onStart() {
        super.onStart();
    }

    /* access modifiers changed from: private */
    public void setLayoutType() {
        StaggeredGridLayoutManager staggeredGridLayoutManager;
        FragmentActivity activity = getActivity();
        ArrayList<Object> arrayList = this.videoList;
        ScreenModel screenModel = this.screenModel;
        int[] iArr = this.layout_list;
        int i = this.layoutType;
        YoutubeAdapter youtubeAdapter = new YoutubeAdapter(activity, arrayList, screenModel, iArr[i], i);
        this.adapter = youtubeAdapter;
        this.list.setAdapter(this.adapter);
        if (this.layoutType == 6) {
            staggeredGridLayoutManager = new StaggeredGridLayoutManager(2, 1);
        } else {
            staggeredGridLayoutManager = new StaggeredGridLayoutManager(1, 1);
        }
        this.list.setLayoutManager(staggeredGridLayoutManager);
        this.list.addOnScrollListener(new EndlessRecyclerViewScrollListener(staggeredGridLayoutManager) {
            public void onLoadMore(int i, int i2) {
                aveYoutubeViewFragment.this.loadMoreProgress.setVisibility(0);
                if (!aveYoutubeViewFragment.this.assignLinksManually) {
                    aveYoutubeViewFragment.this.youtubeUtil.getVideoListId(aveYoutubeViewFragment.this.channelId);
                } else {
                    aveYoutubeViewFragment.this.youtubeUtil.getVideoListWithDetails(aveYoutubeViewFragment.this.getItemsManually());
                }
            }
        });
        int i2 = this.layoutType;
        if (i2 == 1 || i2 == 2 || i2 == 4) {
            int i3 = (int) ((((float) 10) * getResources().getDisplayMetrics().density) + 0.5f);
            this.overlayLayout.setPadding(i3, i3, i3, i3);
        } else if (i2 == 6) {
            int i4 = (int) ((((float) 6) * getResources().getDisplayMetrics().density) + 0.5f);
            this.overlayLayout.setPadding(i4, i4, i4, i4);
        }
    }

    public void onResume() {
        super.onResume();
        if (!this.sharedPrefHelper.getIsNativeAdEnabled() && this.mainLayout != null) {
            this.bannerHelper.addBannerAd(this.mainLayout, this.overlayLayout);
        }
    }

    public void setFeaturedHeader(List<Object> list2) {
        list2.add(0, new VideoFeaturedHeader((ItemDetail) list2.get(0)));
        list2.remove(1);
        setListAdapter(0, list2);
    }

    /* access modifiers changed from: private */
    public String getItemsManually() {
        List list2;
        int size = this.screenModel.getTableItems().size();
        int i = this.manuallyCount;
        if (size > (i + 1) * 20) {
            ArrayList tableItems = this.screenModel.getTableItems();
            int i2 = this.manuallyCount;
            list2 = tableItems.subList(i2 * 20, (i2 + 1) * 20);
        } else {
            list2 = i * 20 < this.screenModel.getTableItems().size() ? this.screenModel.getTableItems().subList(this.manuallyCount * 20, this.screenModel.getTableItems().size()) : null;
        }
        String str = "";
        if (list2 == null) {
            return str;
        }
        for (int i3 = 0; i3 < list2.size(); i3++) {
            StringBuilder sb = new StringBuilder();
            sb.append(str);
            sb.append(extractYTId(((TableItemsModel) list2.get(i3)).getYoutubeURL()));
            sb.append(",");
            str = sb.toString();
        }
        this.manuallyCount++;
        return str;
    }

    private static String extractYTId(String str) {
        Matcher matcher = Pattern.compile("http(?:s)?:\\/\\/(?:m.)?(?:www\\.)?youtu(?:\\.be\\/|be\\.com\\/(?:watch\\?(?:feature=youtu.be\\&)?v=|v\\/|embed\\/|user\\/(?:[\\w#]+\\/)+))([^&#?\\n]+)", 2).matcher(str);
        if (matcher.matches()) {
            return matcher.group(1);
        }
        return null;
    }

    public void onDestroyView() {
        super.onDestroyView();
        this.unbinder.unbind();
        EventBus.getDefault().unregister(this);
    }

    public void onSaveInstanceState(Bundle bundle) {
        super.onSaveInstanceState(bundle);
        StringBuilder sb = new StringBuilder();
        sb.append(this.screenId);
        sb.append("list");
        bundle.putSerializable(sb.toString(), this.adapter.getItems());
        StringBuilder sb2 = new StringBuilder();
        sb2.append(this.screenId);
        sb2.append("listManuallyCount");
        bundle.putInt(sb2.toString(), this.manuallyCount);
        this.mListState = this.list.getLayoutManager().onSaveInstanceState();
        bundle.putParcelable(String.valueOf(this.screenId), this.mListState);
    }

    public void onViewStateRestored(Bundle bundle) {
        super.onViewStateRestored(bundle);
        if (bundle != null) {
            StringBuilder sb = new StringBuilder();
            sb.append(this.screenId);
            sb.append("listManuallyCount");
            this.manuallyCount = bundle.getInt(sb.toString());
            this.mListState = bundle.getParcelable(String.valueOf(this.screenId));
            StringBuilder sb2 = new StringBuilder();
            sb2.append(String.valueOf(this.screenId));
            sb2.append("list");
            ArrayList arrayList = (ArrayList) bundle.getSerializable(sb2.toString());
            if (arrayList != null) {
                for (int i = 0; i < arrayList.size(); i++) {
                    addVideoToAdapter(arrayList.get(i));
                }
            } else {
                getVideoList();
            }
            this.list.getLayoutManager().onRestoreInstanceState(this.mListState);
        } else if (this.adapter.getItemCount() == 0) {
            getVideoList();
        }
    }

    /* access modifiers changed from: private */
    public void getVideoList() {
        if (!this.networkHelper.isConnected()) {
            Toast.makeText(getActivity().getApplicationContext(), getResources().getString(R.string.please_check_your_internet_connection), 1).show();
            getActivity().finish();
        } else if (this.assignLinksManually || this.channelId == null) {
            if (getUserVisibleHint()) {
                this.progressViewHelper.show();
            }
            this.youtubeUtil.getVideoListWithDetails(getItemsManually());
        } else {
            if (getUserVisibleHint()) {
                this.progressViewHelper.show();
            }
            this.youtubeUtil.getVideoListId(this.channelId);
        }
    }

    public void setMenuVisibility(boolean z) {
        super.setMenuVisibility(z);
        if (z && this.adapter.getItemCount() == 0) {
            this.progressViewHelper.show();
        }
    }
}
