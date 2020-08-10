package com.mobiroller.adapters;

import android.app.Activity;
import android.content.Intent;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffColorFilter;
import android.graphics.drawable.Drawable;
import android.os.Build.VERSION;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityOptionsCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView.ViewHolder;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;
import androidx.viewpager.widget.ViewPager.OnPageChangeListener;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;
import com.mobiroller.activities.aveRssContentViewPager;
import com.mobiroller.constants.Constants;
import com.mobiroller.containers.ClickableViewPager;
import com.mobiroller.containers.ClickableViewPager.OnItemClickListener;
import com.mobiroller.helpers.SharedPrefHelper;
import com.mobiroller.helpers.StringSimilarityHelper;
import com.mobiroller.helpers.UtilManager;
import com.mobiroller.mobi942763453128.R;
import com.mobiroller.models.AdMobModel;
import com.mobiroller.models.RssFeaturedHeaderModel;
import com.mobiroller.models.RssModel;
import com.mobiroller.models.RssSliderHeaderModel;
import com.mobiroller.models.ScreenModel;
import com.mobiroller.util.InterstitialAdsUtil;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class RssAdapter extends NativeAdsAdapter {
    private final int AD_TYPE = 1;
    private final int RSS_FEATURED_HEADER_TYPE = 2;
    private final int RSS_SLIDER_HEADER_TYPE = 3;
    private final int RSS_TYPE = 0;
    /* access modifiers changed from: private */
    public Activity activity;
    /* access modifiers changed from: private */
    public ArrayList<Object> dataList;
    /* access modifiers changed from: private */
    public DateFormat dateFormat = new SimpleDateFormat("dd MMMM yyyy");
    /* access modifiers changed from: private */
    public InterstitialAdsUtil interstitialAdsUtil;
    /* access modifiers changed from: private */
    public int layoutType;
    /* access modifiers changed from: private */
    public String screenId;
    public ScreenModel screenModel;
    /* access modifiers changed from: private */
    public SharedPrefHelper sharedPrefHelper;
    /* access modifiers changed from: private */
    public boolean staggered;
    /* access modifiers changed from: private */
    public int type = 0;

    private class RecyclerViewRssFeaturedHeaderItem extends ViewHolder {
        RelativeLayout mainLayout;
        ImageView rssAvatar;
        TextView rssDate;
        RssModel rssModelHeader;
        TextView rssTitle;

        RecyclerViewRssFeaturedHeaderItem(View view) {
            super(view);
            this.rssModelHeader = ((RssFeaturedHeaderModel) RssAdapter.this.dataList.get(0)).getFeaturedHeader();
            this.rssTitle = (TextView) view.findViewById(R.id.rss_list_title);
            this.mainLayout = (RelativeLayout) view.findViewById(R.id.main_layout);
            this.rssDate = (TextView) view.findViewById(R.id.rss_list_date);
            this.rssAvatar = (ImageView) view.findViewById(R.id.rss_list_image);
            bind((RssFeaturedHeaderModel) RssAdapter.this.dataList.get(0));
        }

        /* access modifiers changed from: 0000 */
        public void bind(RssFeaturedHeaderModel rssFeaturedHeaderModel) {
            final RssModel featuredHeader = rssFeaturedHeaderModel.getFeaturedHeader();
            this.mainLayout.setOnClickListener(new OnClickListener() {
                public void onClick(View view) {
                    if (RssAdapter.this.screenId != null) {
                        Intent intent = new Intent(RssAdapter.this.activity, aveRssContentViewPager.class);
                        intent.putExtra(Constants.KEY_RSS_POSITION, 0);
                        intent.putExtra(Constants.KEY_SCREEN_ID, RssAdapter.this.screenId);
                        intent.putExtra(Constants.KEY_RSS_LAYOUT_TYPE, RssAdapter.this.layoutType);
                        String title = featuredHeader.getTitle();
                        String str = "";
                        String str2 = Constants.KEY_RSS_TITLE;
                        if (title != null) {
                            intent.putExtra(str2, Html.fromHtml(featuredHeader.getTitle()).toString());
                        } else {
                            intent.putExtra(str2, str);
                        }
                        if (RssAdapter.this.layoutType == 10 || RssAdapter.this.layoutType == 8 || VERSION.SDK_INT < 21 || featuredHeader.getImage() == null || featuredHeader.getImage().equalsIgnoreCase(str)) {
                            RssAdapter.this.interstitialAdsUtil.checkInterstitialAds(intent);
                            return;
                        }
                        RssAdapter.this.interstitialAdsUtil.checkInterstitialAds(intent, ActivityOptionsCompat.makeSceneTransitionAnimation(RssAdapter.this.activity, RecyclerViewRssFeaturedHeaderItem.this.rssAvatar, "rssImage").toBundle());
                    }
                }
            });
            if (this.rssDate.getVisibility() == 0) {
                String str = "-";
                if (featuredHeader.getPubDate() != null) {
                    try {
                        this.rssDate.setText(RssAdapter.this.dateFormat.format(featuredHeader.getPubDate()));
                    } catch (Exception unused) {
                        this.rssDate.setText(str);
                    }
                } else {
                    this.rssDate.setText(str);
                }
            }
            this.rssTitle.setText(Html.fromHtml(featuredHeader.getTitle()));
            if (featuredHeader.getImage() == null || featuredHeader.getImage().equalsIgnoreCase("")) {
                Glide.with(RssAdapter.this.activity).load(Integer.valueOf(R.drawable.no_image)).into(this.rssAvatar);
            } else {
                Glide.with(RssAdapter.this.activity).load(featuredHeader.getImage()).apply(new RequestOptions().placeholder((int) R.drawable.no_image)).addListener(new RequestListener<Drawable>() {
                    public boolean onResourceReady(Drawable drawable, Object obj, Target<Drawable> target, DataSource dataSource, boolean z) {
                        return false;
                    }

                    public boolean onLoadFailed(GlideException glideException, Object obj, Target<Drawable> target, boolean z) {
                        RecyclerViewRssFeaturedHeaderItem.this.rssAvatar.setImageDrawable(ContextCompat.getDrawable(RssAdapter.this.activity, R.drawable.no_image));
                        return false;
                    }
                }).into(this.rssAvatar);
            }
            this.rssTitle.getViewTreeObserver().addOnGlobalLayoutListener(new OnGlobalLayoutListener() {
                public void onGlobalLayout() {
                    RecyclerViewRssFeaturedHeaderItem.this.rssTitle.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                    int height = (int) (((float) RecyclerViewRssFeaturedHeaderItem.this.rssTitle.getHeight()) / ((float) RecyclerViewRssFeaturedHeaderItem.this.rssTitle.getLineHeight()));
                    if (RecyclerViewRssFeaturedHeaderItem.this.rssTitle.getLineCount() != height) {
                        RecyclerViewRssFeaturedHeaderItem.this.rssTitle.setLines(height);
                    }
                }
            });
        }
    }

    private class RecyclerViewRssItem extends ViewHolder {
        ViewGroup mainLayout;
        ViewGroup parent;
        int position;
        ImageView rssAvatar;
        TextView rssDate;
        TextView rssDescription;
        TextView rssTitle;

        RecyclerViewRssItem(View view) {
            super(view);
            this.rssTitle = (TextView) view.findViewById(R.id.rss_list_title);
            this.rssAvatar = (ImageView) view.findViewById(R.id.rss_list_image);
            this.rssDate = (TextView) view.findViewById(R.id.rss_list_date);
            this.rssDescription = (TextView) view.findViewById(R.id.rss_list_description);
            this.mainLayout = (ViewGroup) view.findViewById(R.id.main_layout);
            if (RssAdapter.this.type == 5) {
                this.rssTitle.setTextSize(12.0f);
                this.rssTitle.setMaxLines(1);
                this.mainLayout.setLayoutParams(new LayoutParams(-1, (int) ((((float) RssAdapter.this.activity.getResources().getInteger(R.integer.rss_layout_5)) * RssAdapter.this.activity.getResources().getDisplayMetrics().density) + 0.5f)));
            }
            if (RssAdapter.this.staggered) {
                StaggeredGridLayoutManager.LayoutParams layoutParams = (StaggeredGridLayoutManager.LayoutParams) this.mainLayout.getLayoutParams();
                layoutParams.setMargins(2, 2, 2, 2);
                this.mainLayout.setLayoutParams(layoutParams);
            }
            if (RssAdapter.this.type == 10 || RssAdapter.this.type == 8) {
                this.rssAvatar.setVisibility(8);
            }
        }

        /* access modifiers changed from: 0000 */
        public void bind(final RssModel rssModel) {
            this.mainLayout.setOnClickListener(new OnClickListener() {
                public void onClick(View view) {
                    if (RssAdapter.this.screenId != null) {
                        Intent intent = new Intent(RssAdapter.this.activity, aveRssContentViewPager.class);
                        int indexOf = RssAdapter.this.dataList.indexOf(rssModel);
                        if (RssAdapter.this.dataList.get(0) instanceof RssSliderHeaderModel) {
                            indexOf += 4;
                        }
                        intent.putExtra(Constants.KEY_RSS_POSITION, indexOf);
                        intent.putExtra(Constants.KEY_SCREEN_ID, RssAdapter.this.screenId);
                        intent.putExtra(Constants.KEY_RSS_TITLE, rssModel.getTitle());
                        intent.putExtra(Constants.KEY_RSS_LAYOUT_TYPE, RssAdapter.this.type);
                        if (RssAdapter.this.type == 10 || RssAdapter.this.type == 8 || VERSION.SDK_INT < 21 || rssModel.getImage() == null || rssModel.getImage().equalsIgnoreCase("")) {
                            RssAdapter.this.interstitialAdsUtil.checkInterstitialAds(intent);
                            return;
                        }
                        RssAdapter.this.interstitialAdsUtil.checkInterstitialAds(intent, ActivityOptionsCompat.makeSceneTransitionAnimation(RssAdapter.this.activity, RecyclerViewRssItem.this.rssAvatar, "rssImage").toBundle());
                    }
                }
            });
            String str = "";
            if (this.rssDescription.getVisibility() == 0) {
                this.rssDescription.setText(Html.fromHtml(rssModel.getDescription().replaceAll("\\<[^>]*>", str).trim()));
            }
            if (this.rssDate.getVisibility() == 0) {
                String str2 = "-";
                if (rssModel.getPubDate() != null) {
                    try {
                        this.rssDate.setText(RssAdapter.this.dateFormat.format(rssModel.getPubDate()));
                    } catch (Exception unused) {
                        this.rssDate.setText(str2);
                    }
                } else {
                    this.rssDate.setText(str2);
                }
            }
            this.rssTitle.setText(Html.fromHtml(rssModel.getTitle()));
            if (rssModel.getImage() == null || rssModel.getImage().equalsIgnoreCase(str)) {
                Glide.with(RssAdapter.this.activity).load(Integer.valueOf(R.drawable.no_image)).into(this.rssAvatar);
            } else {
                Glide.with(RssAdapter.this.activity).load(rssModel.getImage()).apply(new RequestOptions().placeholder((int) R.drawable.no_image)).addListener(new RequestListener<Drawable>() {
                    public boolean onResourceReady(Drawable drawable, Object obj, Target<Drawable> target, DataSource dataSource, boolean z) {
                        return false;
                    }

                    public boolean onLoadFailed(GlideException glideException, Object obj, Target<Drawable> target, boolean z) {
                        RecyclerViewRssItem.this.rssAvatar.setImageDrawable(ContextCompat.getDrawable(RssAdapter.this.activity, R.drawable.no_image));
                        return false;
                    }
                }).into(this.rssAvatar);
            }
            if (RssAdapter.this.type != 10) {
                this.rssTitle.getViewTreeObserver().addOnGlobalLayoutListener(new OnGlobalLayoutListener() {
                    public void onGlobalLayout() {
                        RecyclerViewRssItem.this.rssTitle.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                        int height = (int) (((float) RecyclerViewRssItem.this.rssTitle.getHeight()) / ((float) RecyclerViewRssItem.this.rssTitle.getLineHeight()));
                        if (RecyclerViewRssItem.this.rssTitle.getLineCount() != height) {
                            RecyclerViewRssItem.this.rssTitle.setLines(height);
                        }
                    }
                });
            }
        }
    }

    private class RecyclerViewRssSliderHeaderItem extends ViewHolder {
        /* access modifiers changed from: private */
        public ImageView[] dots;
        /* access modifiers changed from: private */
        public int dotsCount;
        private ViewPagerAdapter mAdapter;
        private LinearLayout pager_indicator;
        /* access modifiers changed from: private */
        public ClickableViewPager viewPager;

        RecyclerViewRssSliderHeaderItem(View view) {
            super(view);
            this.viewPager = (ClickableViewPager) view.findViewById(R.id.viewPager);
            this.pager_indicator = (LinearLayout) view.findViewById(R.id.viewPagerCountDots);
            bind((RssSliderHeaderModel) RssAdapter.this.dataList.get(0));
        }

        /* access modifiers changed from: 0000 */
        public void bind(final RssSliderHeaderModel rssSliderHeaderModel) {
            this.mAdapter = new ViewPagerAdapter(RssAdapter.this.activity, rssSliderHeaderModel.getDataList(), RssAdapter.this.screenId);
            this.viewPager.setAdapter(this.mAdapter);
            this.viewPager.setOffscreenPageLimit(5);
            this.viewPager.setOnItemClickListener(new OnItemClickListener() {
                public void onItemClick(int i) {
                    Intent intent = new Intent(RssAdapter.this.activity, aveRssContentViewPager.class);
                    intent.putExtra(Constants.KEY_RSS_POSITION, i);
                    intent.putExtra(Constants.KEY_SCREEN_ID, RssAdapter.this.screenId);
                    intent.putExtra(Constants.KEY_RSS_LAYOUT_TYPE, RssAdapter.this.layoutType);
                    String title = ((RssModel) rssSliderHeaderModel.getDataList().get(i)).getTitle();
                    String str = "";
                    String str2 = Constants.KEY_RSS_TITLE;
                    if (title != null) {
                        intent.putExtra(str2, Html.fromHtml(((RssModel) rssSliderHeaderModel.getDataList().get(i)).getTitle()).toString());
                    } else {
                        intent.putExtra(str2, str);
                    }
                    if (RssAdapter.this.layoutType == 10 || RssAdapter.this.layoutType == 8 || VERSION.SDK_INT < 21 || ((RssModel) rssSliderHeaderModel.getDataList().get(i)).getImage() == null || ((RssModel) rssSliderHeaderModel.getDataList().get(i)).getImage().equalsIgnoreCase(str)) {
                        RssAdapter.this.interstitialAdsUtil.checkInterstitialAds(intent);
                        return;
                    }
                    Activity access$100 = RssAdapter.this.activity;
                    ClickableViewPager access$800 = RecyclerViewRssSliderHeaderItem.this.viewPager;
                    StringBuilder sb = new StringBuilder();
                    sb.append("viewpager");
                    sb.append(i);
                    RssAdapter.this.interstitialAdsUtil.checkInterstitialAds(intent, ActivityOptionsCompat.makeSceneTransitionAnimation(access$100, access$800.findViewWithTag(sb.toString()), "rssImage").toBundle());
                }
            });
            this.viewPager.addOnPageChangeListener(new OnPageChangeListener() {
                public void onPageScrollStateChanged(int i) {
                }

                public void onPageScrolled(int i, float f, int i2) {
                }

                public void onPageSelected(int i) {
                    for (int i2 = 0; i2 < RecyclerViewRssSliderHeaderItem.this.dotsCount; i2++) {
                        RecyclerViewRssSliderHeaderItem.this.dots[i2].setImageDrawable(ContextCompat.getDrawable(RssAdapter.this.activity, R.drawable.nonselecteditem_dot));
                    }
                    Drawable drawable = ContextCompat.getDrawable(RssAdapter.this.activity, R.drawable.selecteditem_dot);
                    drawable.setColorFilter(new PorterDuffColorFilter(RssAdapter.this.sharedPrefHelper.getActionBarColor(), Mode.MULTIPLY));
                    RecyclerViewRssSliderHeaderItem.this.dots[i].setImageDrawable(drawable);
                }
            });
            this.dotsCount = this.mAdapter.getCount();
            this.dots = new ImageView[this.dotsCount];
            for (int i = 0; i < this.dotsCount; i++) {
                this.dots[i] = new ImageView(RssAdapter.this.activity);
                this.dots[i].setImageDrawable(ContextCompat.getDrawable(RssAdapter.this.activity, R.drawable.nonselecteditem_dot));
                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(-2, -2);
                layoutParams.setMargins(4, 0, 4, 0);
                this.pager_indicator.addView(this.dots[i], layoutParams);
            }
            Drawable drawable = ContextCompat.getDrawable(RssAdapter.this.activity, R.drawable.selecteditem_dot);
            drawable.setColorFilter(new PorterDuffColorFilter(RssAdapter.this.sharedPrefHelper.getActionBarColor(), Mode.MULTIPLY));
            this.dots[0].setImageDrawable(drawable);
        }
    }

    public RssAdapter(Activity activity2, ArrayList<Object> arrayList, ScreenModel screenModel2, int i, String str, boolean z, int i2) {
        super(activity2);
        this.activity = activity2;
        this.layoutType = i;
        this.type = i2;
        this.staggered = z;
        this.dataList = arrayList;
        this.screenModel = screenModel2;
        this.screenId = str;
        this.sharedPrefHelper = UtilManager.sharedPrefHelper();
        this.interstitialAdsUtil = new InterstitialAdsUtil((AppCompatActivity) activity2);
    }

    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        if (i == 0) {
            return new RecyclerViewRssItem(LayoutInflater.from(viewGroup.getContext()).inflate(this.layoutType, viewGroup, false));
        }
        if (i != 1) {
            if (i == 2) {
                return new RecyclerViewRssFeaturedHeaderItem(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.rss_list_item_square_featured, viewGroup, false));
            }
            if (i != 3) {
                return null;
            }
            return new RecyclerViewRssSliderHeaderItem(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.rss_header_slider, viewGroup, false));
        } else if (!this.sharedPrefHelper.getNativeAddUnitID().startsWith("ca-")) {
            RecyclerViewRssFacebookAdItem recyclerViewRssFacebookAdItem = new RecyclerViewRssFacebookAdItem(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.empty_view, viewGroup, false), this.activity, this.type, this.sharedPrefHelper);
            return recyclerViewRssFacebookAdItem;
        } else {
            RecyclerViewRssAdMobAdItem recyclerViewRssAdMobAdItem = new RecyclerViewRssAdMobAdItem(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.empty_view, viewGroup, false), this.activity, this.type, this.sharedPrefHelper);
            return recyclerViewRssAdMobAdItem;
        }
    }

    public void onBindViewHolder(ViewHolder viewHolder, int i) {
        if (viewHolder.getItemViewType() == 0) {
            ((RecyclerViewRssItem) viewHolder).bind((RssModel) this.dataList.get(i));
        } else if (viewHolder.getItemViewType() == 2 || viewHolder.getItemViewType() == 3) {
            ((StaggeredGridLayoutManager.LayoutParams) viewHolder.itemView.getLayoutParams()).setFullSpan(true);
        }
    }

    public int getItemCount() {
        return this.dataList.size();
    }

    public int getItemViewType(int i) {
        if (this.dataList.get(i) instanceof RssModel) {
            return 0;
        }
        if (this.dataList.get(i) instanceof AdMobModel) {
            return 1;
        }
        return this.dataList.get(i) instanceof RssFeaturedHeaderModel ? 2 : 3;
    }

    public List<Object> getAllData() {
        return this.dataList;
    }

    public void add(Object obj) {
        this.dataList.add(obj);
    }

    public void findAndOpenContent(String str) {
        boolean z;
        for (int i = 0; i < this.dataList.size(); i++) {
            if (this.dataList.get(i) instanceof RssFeaturedHeaderModel) {
                if (checkSimilarity(str, ((RssFeaturedHeaderModel) this.dataList.get(i)).getFeaturedHeader().getTitle())) {
                    startRssContentActivity(((RssFeaturedHeaderModel) this.dataList.get(i)).getFeaturedHeader(), 0);
                    return;
                }
            } else if (this.dataList.get(i) instanceof RssSliderHeaderModel) {
                int i2 = 0;
                while (true) {
                    if (i2 >= ((RssSliderHeaderModel) this.dataList.get(i)).getDataList().size()) {
                        z = false;
                        break;
                    } else if (checkSimilarity(str, ((RssModel) ((RssSliderHeaderModel) this.dataList.get(i)).getDataList().get(i2)).getTitle())) {
                        startRssContentActivity((RssModel) ((RssSliderHeaderModel) this.dataList.get(i)).getDataList().get(i2), i2);
                        z = true;
                        break;
                    } else {
                        i2++;
                    }
                }
                if (z) {
                    return;
                }
            } else if ((this.dataList.get(i) instanceof RssModel) && checkSimilarity(str, ((RssModel) this.dataList.get(i)).getTitle())) {
                ArrayList<Object> arrayList = this.dataList;
                int indexOf = arrayList.indexOf(arrayList.get(i));
                if (this.dataList.get(0) instanceof RssSliderHeaderModel) {
                    indexOf += 4;
                }
                startRssContentActivity((RssModel) this.dataList.get(i), indexOf);
                return;
            }
        }
    }

    public void startRssContentActivity(RssModel rssModel, int i) {
        Intent intent = new Intent(this.activity, aveRssContentViewPager.class);
        intent.putExtra(Constants.KEY_RSS_POSITION, i);
        intent.putExtra(Constants.KEY_SCREEN_ID, this.screenId);
        intent.putExtra(Constants.KEY_RSS_TITLE, rssModel.getTitle());
        intent.putExtra(Constants.KEY_RSS_LAYOUT_TYPE, this.type);
        this.interstitialAdsUtil.checkInterstitialAds(intent);
    }

    private boolean checkSimilarity(String str, String str2) {
        return similarity(str, str2) > 0.75d;
    }

    private double similarity(String str, String str2) {
        if (str.length() < str2.length()) {
            String str3 = str2;
            str2 = str;
            str = str3;
        }
        int length = str.length();
        if (length == 0) {
            return 1.0d;
        }
        double editDistance = (double) (length - StringSimilarityHelper.editDistance(str, str2));
        double d = (double) length;
        Double.isNaN(editDistance);
        Double.isNaN(d);
        return editDistance / d;
    }

    public ArrayList<RssModel> getAllRssModels() {
        ArrayList<RssModel> arrayList = new ArrayList<>();
        for (int i = 0; i < this.dataList.size(); i++) {
            if (this.dataList.get(i) instanceof RssFeaturedHeaderModel) {
                arrayList.add(((RssFeaturedHeaderModel) this.dataList.get(i)).getFeaturedHeader());
            } else if (this.dataList.get(i) instanceof RssSliderHeaderModel) {
                for (int i2 = 0; i2 < ((RssSliderHeaderModel) this.dataList.get(i)).getDataList().size(); i2++) {
                    arrayList.add((RssModel) ((RssSliderHeaderModel) this.dataList.get(i)).getDataList().get(i2));
                }
            } else if (this.dataList.get(i) instanceof RssModel) {
                arrayList.add((RssModel) this.dataList.get(i));
            }
        }
        return arrayList;
    }

    public ArrayList<Object> getAllModels() {
        return this.dataList;
    }
}
