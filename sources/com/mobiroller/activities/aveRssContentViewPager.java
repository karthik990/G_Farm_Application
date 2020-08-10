package com.mobiroller.activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.webkit.URLUtil;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.browser.customtabs.CustomTabsIntent.Builder;
import androidx.core.app.SharedElementCallback;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;
import androidx.viewpager.widget.ViewPager.OnPageChangeListener;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.afollestad.materialdialogs.MaterialDialog;
import com.afollestad.materialdialogs.MaterialDialog.ListCallbackSingleChoice;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdLoader;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.VideoOptions;
import com.google.android.gms.ads.formats.NativeAd.Image;
import com.google.android.gms.ads.formats.NativeAdOptions;
import com.google.android.gms.ads.formats.NativeContentAd;
import com.google.android.gms.ads.formats.NativeContentAd.OnContentAdLoadedListener;
import com.google.android.gms.ads.formats.NativeContentAdView;
import com.mobiroller.DynamicConstants;
import com.mobiroller.activities.base.AveActivity;
import com.mobiroller.constants.Constants;
import com.mobiroller.enums.FontStyle;
import com.mobiroller.helpers.FavoriteHelper;
import com.mobiroller.helpers.FontSizeHelper;
import com.mobiroller.helpers.NetworkHelper;
import com.mobiroller.helpers.SharedPrefHelper;
import com.mobiroller.helpers.ToolbarHelper;
import com.mobiroller.interfaces.ActivityComponent;
import com.mobiroller.interfaces.bottomsheet.ActionListener;
import com.mobiroller.mobi942763453128.R;
import com.mobiroller.models.RssFeaturedHeaderModel;
import com.mobiroller.models.RssModel;
import com.mobiroller.models.RssSliderHeaderModel;
import com.mobiroller.models.bottomsheet.ActionModel;
import com.mobiroller.util.ShareUtil;
import com.mobiroller.views.ActionPickerBottomSheet;
import com.mobiroller.views.CustomHorizontalScrollView;
import com.mobiroller.views.CustomHorizontalScrollView.OnScrollChangedListener;
import com.mobiroller.views.VideoEnabledWebViewWithTouch;
import com.mobiroller.views.custom.MobirollerToolbar;
import com.mobiroller.views.theme.Theme;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.inject.Inject;

public class aveRssContentViewPager extends AveActivity implements ActionListener {
    private static int FONT_ID = 13;
    private static int SHARE_ID = 12;
    private static RssContentPagerAdapter rssContentPagerAdapter;
    public static List<Object> rssModelList;
    private ActionPickerBottomSheet actionPickerBottomSheet;
    /* access modifiers changed from: private */
    public NativeContentAdView adView;
    /* access modifiers changed from: private */
    public int currentPosition;
    private FavoriteHelper favoriteHelper;
    private int layoutType = 9;
    private final SharedElementCallback mCallback = new SharedElementCallback() {
        public void onMapSharedElements(List<String> list, Map<String, View> map) {
            if (aveRssContentViewPager.this.mCurrentView != null) {
                ImageView imageView = (ImageView) aveRssContentViewPager.this.mCurrentView.findViewById(R.id.rss_content_image);
                String str = "rssImage";
                if (((String) list.get(0)).equalsIgnoreCase(str)) {
                    map.put(str, imageView);
                }
            }
        }
    };
    /* access modifiers changed from: private */
    public View mCurrentView = null;
    private int mLastDampedScroll;
    @Inject
    NetworkHelper networkHelper;
    @BindView(2131363066)
    RelativeLayout rss_content_rel_layout;
    @Inject
    SharedPrefHelper sharedPrefHelper;
    @Inject
    ToolbarHelper toolbarHelper;
    @BindView(2131363378)
    ViewPager viewPager;

    class RssContentPagerAdapter extends PagerAdapter {
        DateFormat dateFormat = new SimpleDateFormat("dd MMMM yyyy HH:mm:ss");
        LayoutInflater inflater;
        private List<Object> rssModelList;

        public boolean isViewFromObject(View view, Object obj) {
            return view == obj;
        }

        RssContentPagerAdapter(List<Object> list) {
            this.inflater = (LayoutInflater) aveRssContentViewPager.this.getSystemService("layout_inflater");
            this.rssModelList = list;
        }

        public int getCount() {
            return this.rssModelList.size();
        }

        public Object instantiateItem(ViewGroup viewGroup, int i) {
            if (this.rssModelList.get(i) instanceof RssModel) {
                final RssModel rssModel = (RssModel) this.rssModelList.get(i);
                View inflate = this.inflater.inflate(R.layout.rss_content, viewGroup, false);
                VideoEnabledWebViewWithTouch videoEnabledWebViewWithTouch = (VideoEnabledWebViewWithTouch) inflate.findViewById(R.id.rss_content_text);
                TextView textView = (TextView) inflate.findViewById(R.id.rss_content_time);
                TextView textView2 = (TextView) inflate.findViewById(R.id.rss_content_title);
                final ImageView imageView = (ImageView) inflate.findViewById(R.id.rss_content_image);
                if (aveRssContentViewPager.this.viewPager.getCurrentItem() != i && VERSION.SDK_INT >= 21) {
                    imageView.setTransitionName("dummy");
                }
                ((CustomHorizontalScrollView) inflate.findViewById(R.id.rss_content_scroll_view)).setOnScrollChangedListener(new OnScrollChangedListener() {
                    public void onScrollChanged(int i, int i2, int i3, int i4) {
                        try {
                            aveRssContentViewPager.this.updateParallaxEffect(i2, imageView);
                        } catch (Exception unused) {
                        }
                    }
                });
                aveRssContentViewPager.this.setEnterSharedElementCallback(new SharedElementCallback() {
                    public void onMapSharedElements(List<String> list, Map<String, View> map) {
                        String str = "businessImage";
                        if (((String) list.get(0)).equalsIgnoreCase(str)) {
                            map.put(str, imageView);
                        }
                    }
                });
                Button button = (Button) inflate.findViewById(R.id.rss_content_more_button);
                textView2.setText(Html.fromHtml(rssModel.getTitle()));
                String str = "-";
                if (rssModel.getPubDate() != null) {
                    try {
                        textView.setText(this.dateFormat.format(rssModel.getPubDate()));
                    } catch (Exception unused) {
                        textView.setText(str);
                    }
                }
                if (VERSION.SDK_INT >= 21) {
                    aveRssContentViewPager.this.setEnterSharedElementCallback(new SharedElementCallback() {
                        public void onMapSharedElements(List<String> list, Map<String, View> map) {
                            String str = "businessImage";
                            if (((String) list.get(0)).equalsIgnoreCase(str)) {
                                map.put(str, imageView);
                            }
                        }
                    });
                }
                if (rssModel.getImage() == null || rssModel.getImage().equalsIgnoreCase("")) {
                    imageView.setVisibility(8);
                } else {
                    Glide.with((FragmentActivity) aveRssContentViewPager.this).load(rssModel.getImage()).apply(new RequestOptions().placeholder((int) R.drawable.no_image)).listener(new RequestListener<Drawable>() {
                        public boolean onResourceReady(Drawable drawable, Object obj, Target<Drawable> target, DataSource dataSource, boolean z) {
                            return false;
                        }

                        public boolean onLoadFailed(GlideException glideException, Object obj, Target<Drawable> target, boolean z) {
                            imageView.setVisibility(8);
                            return false;
                        }
                    }).into(imageView);
                }
                textView2.setText(Html.fromHtml(rssModel.getTitle()));
                if (rssModel.getPubDate() != null) {
                    try {
                        textView.setText(this.dateFormat.format(rssModel.getPubDate()));
                    } catch (Exception unused2) {
                        textView.setText(str);
                    }
                } else {
                    textView.setText(str);
                }
                StringBuilder sb = new StringBuilder();
                sb.append("<html><style>img{display: inline;height: auto;max-width: 100%;} iframe{display: inline;height: auto;max-width: 100%;} body{ background-color: #ffffff; } </style><body>");
                sb.append(rssModel.getDescription());
                sb.append("</body></html>");
                String sb2 = sb.toString();
                videoEnabledWebViewWithTouch.setWebViewClient(new WebViewClient() {
                    public boolean shouldOverrideUrlLoading(WebView webView, String str) {
                        try {
                            if (URLUtil.isValidUrl(str)) {
                                Builder builder = new Builder();
                                builder.setToolbarColor(Theme.primaryColor);
                                builder.setShowTitle(true);
                                builder.addDefaultShareMenuItem();
                                builder.build().launchUrl(aveRssContentViewPager.this, Uri.parse(str));
                                aveRssContentViewPager.this.overridePendingTransition(R.anim.push_up_in, R.anim.push_up_out);
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                            if (URLUtil.isValidUrl(str)) {
                                Builder builder2 = new Builder();
                                builder2.setToolbarColor(Theme.primaryColor);
                                builder2.setShowTitle(true);
                                builder2.addDefaultShareMenuItem();
                                builder2.build().launchUrl(aveRssContentViewPager.this, Uri.parse(str));
                            }
                        }
                        return true;
                    }
                });
                videoEnabledWebViewWithTouch.getSettings().setLoadWithOverviewMode(true);
                videoEnabledWebViewWithTouch.getSettings().setBuiltInZoomControls(true);
                videoEnabledWebViewWithTouch.getSettings().setDisplayZoomControls(false);
                videoEnabledWebViewWithTouch.loadDataWithBaseURL("blarg://ignored", sb2, "text/html", "utf-8", "");
                button.setText(aveRssContentViewPager.this.getString(R.string.action_more));
                button.setAllCaps(true);
                button.setOnClickListener(new OnClickListener() {
                    public void onClick(View view) {
                        try {
                            Builder builder = new Builder();
                            builder.setToolbarColor(Theme.primaryColor);
                            builder.setShowTitle(true);
                            builder.addDefaultShareMenuItem();
                            builder.build().launchUrl(aveRssContentViewPager.this, Uri.parse(rssModel.getLink()));
                            aveRssContentViewPager.this.overridePendingTransition(R.anim.push_up_in, R.anim.push_up_out);
                        } catch (Exception e) {
                            e.printStackTrace();
                            if (URLUtil.isValidUrl(rssModel.getLink())) {
                                Builder builder2 = new Builder();
                                builder2.addDefaultShareMenuItem();
                                builder2.setToolbarColor(Theme.primaryColor);
                                builder2.build().launchUrl(aveRssContentViewPager.this, Uri.parse(rssModel.getLink()));
                            }
                        }
                    }
                });
                viewGroup.addView(inflate, -1, -1);
                return inflate;
            } else if (aveRssContentViewPager.this.sharedPrefHelper.getNativeAddUnitID().startsWith("ca-")) {
                viewGroup.addView(aveRssContentViewPager.this.adView, -1, -1);
                return aveRssContentViewPager.this.adView;
            } else {
                View inflate2 = this.inflater.inflate(R.layout.rss_content_facebook, viewGroup, false);
                viewGroup.addView(inflate2, -1, -1);
                return inflate2;
            }
        }

        public void destroyItem(ViewGroup viewGroup, int i, Object obj) {
            viewGroup.removeView((View) obj);
        }

        public void setPrimaryItem(ViewGroup viewGroup, int i, Object obj) {
            aveRssContentViewPager.this.invalidateOptionsMenu();
            aveRssContentViewPager.this.mCurrentView = (View) obj;
            aveRssContentViewPager.this.currentPosition = i;
        }
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        return true;
    }

    public static void setRssModelList(List<Object> list) {
        if (list != null && list.size() > 0) {
            ArrayList arrayList = new ArrayList(list);
            if (arrayList.get(0) instanceof RssSliderHeaderModel) {
                arrayList.addAll(0, ((RssSliderHeaderModel) arrayList.get(0)).getDataList());
                arrayList.remove(5);
            } else if (arrayList.get(0) instanceof RssFeaturedHeaderModel) {
                arrayList.add(0, ((RssFeaturedHeaderModel) arrayList.get(0)).getFeaturedHeader());
                arrayList.remove(1);
            }
            rssModelList = arrayList;
        }
    }

    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView((int) R.layout.rss_content_view_pager);
        ButterKnife.bind((Activity) this);
        this.favoriteHelper = new FavoriteHelper(this);
        if (this.sharedPrefHelper.getNativeAddUnitID().startsWith("ca-")) {
            loadAds();
        }
        int intExtra = getIntent().getIntExtra(Constants.KEY_RSS_POSITION, 0);
        if (VERSION.SDK_INT >= 21) {
            setEnterSharedElementCallback(this.mCallback);
        }
        setMobirollerToolbar((MobirollerToolbar) findViewById(R.id.toolbar_top));
        Intent intent = getIntent();
        String str = Constants.KEY_RSS_TITLE;
        String str2 = "";
        if (!intent.hasExtra(str)) {
            this.toolbarHelper.setToolbarTitle(this, str2);
        } else if (getIntent().getStringExtra(str) != null) {
            this.toolbarHelper.setToolbarTitle(this, Html.fromHtml(getIntent().getStringExtra(str)).toString());
        } else {
            this.toolbarHelper.setToolbarTitle(this, str2);
        }
        Intent intent2 = getIntent();
        String str3 = Constants.KEY_RSS_LAYOUT_TYPE;
        if (intent2.hasExtra(str3)) {
            this.layoutType = getIntent().getIntExtra(str3, 9);
        }
        rssContentPagerAdapter = new RssContentPagerAdapter(rssModelList);
        this.viewPager.setAdapter(rssContentPagerAdapter);
        this.viewPager.setCurrentItem(intExtra);
        this.viewPager.addOnPageChangeListener(new OnPageChangeListener() {
            public void onPageScrollStateChanged(int i) {
            }

            public void onPageScrolled(int i, float f, int i2) {
            }

            public void onPageSelected(int i) {
                if (aveRssContentViewPager.rssModelList.get(i) instanceof RssModel) {
                    aveRssContentViewPager.this.toolbarHelper.setToolbarTitle(aveRssContentViewPager.this, Html.fromHtml(((RssModel) aveRssContentViewPager.rssModelList.get(i)).getTitle()).toString());
                    return;
                }
                ToolbarHelper toolbarHelper = aveRssContentViewPager.this.toolbarHelper;
                aveRssContentViewPager aversscontentviewpager = aveRssContentViewPager.this;
                toolbarHelper.setToolbarTitle(aversscontentviewpager, aversscontentviewpager.getString(R.string.advertisement));
            }
        });
        this.mLastDampedScroll = 0;
        if (getSupportActionBar() != null) {
            ((Toolbar) findViewById(R.id.toolbar_top)).setNavigationOnClickListener(new OnClickListener() {
                public void onClick(View view) {
                    aveRssContentViewPager.this.onBackPressed();
                }
            });
        }
    }

    public AppCompatActivity injectActivity(ActivityComponent activityComponent) {
        activityComponent.inject(this);
        return this;
    }

    public boolean onPrepareOptionsMenu(Menu menu) {
        if (!(getToolbar() == null || getToolbar().getMenu() == null)) {
            getToolbar().getMenu().clear();
        }
        if (rssModelList.get(this.currentPosition) instanceof RssModel) {
            RssModel rssModel = (RssModel) rssModelList.get(this.currentPosition);
            if (rssModel.getLink() != null && !rssModel.getLink().equalsIgnoreCase("")) {
                getToolbar().inflateMenu(R.menu.rss_content_menu);
            }
            if (!this.sharedPrefHelper.isFavoriteActive()) {
                getToolbar().getMenu().findItem(R.id.action_favorite).setVisible(false);
            }
            if (this.favoriteHelper.isRssContentAddedToList(rssModel)) {
                getToolbar().getMenu().findItem(R.id.action_favorite).setIcon(R.drawable.ic_bookmark_white_24dp);
            }
        } else {
            getToolbar().inflateMenu(R.menu.empty_menu);
        }
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem menuItem) {
        if (menuItem.getItemId() == R.id.action_favorite) {
            RssModel rssModel = (RssModel) rssModelList.get(this.currentPosition);
            if (this.favoriteHelper.isRssContentAddedToList(rssModel)) {
                this.favoriteHelper.removeRssContent(rssModel);
            } else {
                this.favoriteHelper.addRssContentToList(rssModel);
            }
            invalidateOptionsMenu();
        } else if (menuItem.getItemId() == R.id.a_More) {
            this.actionPickerBottomSheet = new ActionPickerBottomSheet.Builder().setActionListener(this).setActions(getActionList()).show(getSupportFragmentManager(), "Tag");
        }
        return super.onOptionsItemSelected(menuItem);
    }

    private List<ActionModel> getActionList() {
        ArrayList arrayList = new ArrayList();
        arrayList.add(new ActionModel(SHARE_ID, R.drawable.ic_share_white_24dp, R.string.action_share, true));
        arrayList.add(new ActionModel(FONT_ID, R.drawable.ic_format_size_white_24dp, R.string.content_change_font_size, true));
        return arrayList;
    }

    /* access modifiers changed from: private */
    public void updateParallaxEffect(int i, ImageView imageView) {
        int i2 = (int) (((float) i) * 0.5f);
        imageView.offsetTopAndBottom(-(this.mLastDampedScroll - i2));
        this.mLastDampedScroll = i2;
    }

    public void onBackPressed() {
        if (VERSION.SDK_INT >= 21) {
            int i = this.layoutType;
            if (!(i == 10 || i == 8)) {
                supportFinishAfterTransition();
                return;
            }
        }
        finish();
    }

    public static void notifyAdapter() {
        RssContentPagerAdapter rssContentPagerAdapter2 = rssContentPagerAdapter;
        if (rssContentPagerAdapter2 != null) {
            rssContentPagerAdapter2.notifyDataSetChanged();
        }
    }

    public void bind(NativeContentAd nativeContentAd, NativeContentAdView nativeContentAdView) {
        TextView textView = (TextView) nativeContentAdView.findViewById(R.id.tvHeader);
        TextView textView2 = (TextView) nativeContentAdView.findViewById(R.id.tvDescription);
        ImageView imageView = (ImageView) nativeContentAdView.findViewById(R.id.ivLogo);
        Button button = (Button) nativeContentAdView.findViewById(R.id.btnAction);
        TextView textView3 = (TextView) nativeContentAdView.findViewById(R.id.tvAdvertiser);
        ImageView imageView2 = (ImageView) nativeContentAdView.findViewById(R.id.ivImage);
        textView.setText(nativeContentAd.getHeadline());
        nativeContentAdView.setHeadlineView(textView);
        textView2.setText(nativeContentAd.getBody());
        nativeContentAdView.setBodyView(textView2);
        if (nativeContentAd.getLogo() != null) {
            imageView.setImageDrawable(nativeContentAd.getLogo().getDrawable());
        }
        nativeContentAdView.setLogoView(imageView);
        button.setText(nativeContentAd.getCallToAction());
        nativeContentAdView.setCallToActionView(button);
        textView3.setText(nativeContentAd.getAdvertiser());
        nativeContentAdView.setAdvertiserView(textView3);
        if (nativeContentAd.getImages() == null || nativeContentAd.getImages().size() <= 0) {
            imageView2.setVisibility(8);
        } else {
            imageView2.setImageDrawable(((Image) nativeContentAd.getImages().get(0)).getDrawable());
            imageView2.setVisibility(0);
        }
        nativeContentAdView.setImageView(imageView2);
        nativeContentAdView.setNativeAd(nativeContentAd);
    }

    public void loadAds() {
        AdLoader.Builder builder = new AdLoader.Builder((Context) this, this.sharedPrefHelper.getNativeAddUnitID());
        this.adView = (NativeContentAdView) getLayoutInflater().inflate(R.layout.ad_content, null);
        builder.forContentAd(new OnContentAdLoadedListener() {
            public void onContentAdLoaded(NativeContentAd nativeContentAd) {
                aveRssContentViewPager aversscontentviewpager = aveRssContentViewPager.this;
                aversscontentviewpager.bind(nativeContentAd, aversscontentviewpager.adView);
            }
        });
        builder.withNativeAdOptions(new NativeAdOptions.Builder().setVideoOptions(new VideoOptions.Builder().build()).build());
        builder.withAdListener(new AdListener() {
            public void onAdFailedToLoad(int i) {
            }
        }).build().loadAd(new AdRequest.Builder().build());
    }

    public void contentTextSizeLayoutOnClick() {
        final int contentFontOrder = new FontSizeHelper(this).getContentFontOrder();
        if (contentFontOrder == -1) {
            contentFontOrder = 1;
        }
        new MaterialDialog.Builder(this).title((int) R.string.content_change_font_size).items((CharSequence[]) getFontSizeList()).itemsCallbackSingleChoice(contentFontOrder, new ListCallbackSingleChoice() {
            public boolean onSelection(MaterialDialog materialDialog, View view, int i, CharSequence charSequence) {
                if (i != contentFontOrder) {
                    if (!DynamicConstants.MobiRoller_Stage) {
                        aveRssContentViewPager.this.saveContentFontSelectedFontSize(i);
                        aveRssContentViewPager.this.restartActivity();
                    } else {
                        aveRssContentViewPager aversscontentviewpager = aveRssContentViewPager.this;
                        Toast.makeText(aversscontentviewpager, aversscontentviewpager.getString(R.string.not_supported_on_preview), 0).show();
                    }
                }
                return true;
            }
        }).negativeText((int) R.string.cancel).positiveText((int) R.string.OK).show();
    }

    public void saveContentFontSelectedFontSize(int i) {
        FontSizeHelper fontSizeHelper = new FontSizeHelper(this);
        if (i == 0) {
            fontSizeHelper.setContentFontStyle(FontStyle.Small);
        } else if (i == 1) {
            fontSizeHelper.setContentFontStyle(FontStyle.Medium);
        } else if (i == 2) {
            fontSizeHelper.setContentFontStyle(FontStyle.Large);
        } else if (i == 3) {
            fontSizeHelper.setContentFontStyle(FontStyle.XLarge);
        }
    }

    private String[] getFontSizeList() {
        return getResources().getStringArray(R.array.text_size_list);
    }

    /* access modifiers changed from: private */
    public void restartActivity() {
        getIntent().putExtra(Constants.KEY_RSS_POSITION, this.currentPosition);
        recreate();
    }

    public void actionSelected(int i, ActionModel actionModel) {
        if (actionModel.f2172id == SHARE_ID) {
            if (rssModelList.get(this.currentPosition) instanceof RssModel) {
                RssModel rssModel = (RssModel) rssModelList.get(this.currentPosition);
                ShareUtil.shareURL(this, Html.fromHtml(rssModel.getTitle()).toString(), rssModel.getLink());
            }
        } else if (actionModel.f2172id == FONT_ID) {
            contentTextSizeLayoutOnClick();
        }
        ActionPickerBottomSheet actionPickerBottomSheet2 = this.actionPickerBottomSheet;
        if (actionPickerBottomSheet2 != null) {
            actionPickerBottomSheet2.dismiss();
        }
    }
}
