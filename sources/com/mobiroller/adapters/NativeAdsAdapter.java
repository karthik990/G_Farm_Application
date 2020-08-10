package com.mobiroller.adapters;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView.ViewHolder;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdLoader.Builder;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.formats.NativeAd.Image;
import com.google.android.gms.ads.formats.NativeAdOptions;
import com.google.android.gms.ads.formats.NativeAppInstallAd;
import com.google.android.gms.ads.formats.NativeAppInstallAd.OnAppInstallAdLoadedListener;
import com.google.android.gms.ads.formats.NativeAppInstallAdView;
import com.google.android.gms.ads.formats.NativeContentAd;
import com.google.android.gms.ads.formats.NativeContentAd.OnContentAdLoadedListener;
import com.google.android.gms.ads.formats.NativeContentAdView;
import com.mobiroller.helpers.SharedPrefHelper;
import com.mobiroller.mobi942763453128.R;
import java.util.Arrays;
import java.util.List;

public class NativeAdsAdapter extends BaseAdapter {
    public static final String ADMOB_AD_UNIT_ID = "ca-app-pub-3940256099942544/2247696110";
    /* access modifiers changed from: private */
    public static List<Integer> layoutRssListHeight;
    /* access modifiers changed from: private */
    public static List<Integer> layoutYoutubeListHeight;

    public class RecyclerViewFacebookAdItem extends ViewHolder {
        public RecyclerViewFacebookAdItem(View view, Activity activity, int i, SharedPrefHelper sharedPrefHelper) {
            super(view);
            final NativeAdsAdapter nativeAdsAdapter = NativeAdsAdapter.this;
            final View view2 = view;
            final Activity activity2 = activity;
            final int i2 = i;
            C40261 r0 = new Runnable() {
                public void run() {
                    FrameLayout frameLayout = (FrameLayout) view2;
                    float f = activity2.getResources().getDisplayMetrics().density;
                    if (i2 != 10) {
                        frameLayout.setLayoutParams(new LayoutParams(-1, (int) ((((float) ((Integer) NativeAdsAdapter.layoutYoutubeListHeight.get(i2)).intValue()) * f) + 0.5f)));
                    } else {
                        frameLayout.setLayoutParams(new LayoutParams(-1, -2));
                    }
                }
            };
            activity.runOnUiThread(r0);
        }
    }

    class RecyclerViewRssAdMobAdItem extends ViewHolder {
        RecyclerViewRssAdMobAdItem(View view, Activity activity, int i, SharedPrefHelper sharedPrefHelper) {
            super(view);
            final NativeAdsAdapter nativeAdsAdapter = NativeAdsAdapter.this;
            final View view2 = view;
            final Activity activity2 = activity;
            final int i2 = i;
            final SharedPrefHelper sharedPrefHelper2 = sharedPrefHelper;
            C40271 r0 = new Runnable() {
                public void run() {
                    final FrameLayout frameLayout = (FrameLayout) view2;
                    float f = activity2.getResources().getDisplayMetrics().density;
                    if (i2 != 10) {
                        frameLayout.setLayoutParams(new LayoutParams(-1, (int) ((((float) ((Integer) NativeAdsAdapter.layoutRssListHeight.get(i2)).intValue()) * f) + 0.5f)));
                    } else {
                        frameLayout.setLayoutParams(new LayoutParams(-1, -2));
                    }
                    Builder builder = new Builder((Context) activity2, sharedPrefHelper2.getNativeAddUnitID());
                    View inflate = activity2.getLayoutInflater().inflate(NativeAdsAdapter.this.getRssAdsLayout(i2), null);
                    frameLayout.removeAllViews();
                    frameLayout.addView(inflate);
                    int i = i2;
                    if (i == 2 || i == 3 || i == 6 || i == 7 || i == 8 || i == 4) {
                        builder.forAppInstallAd(new OnAppInstallAdLoadedListener() {
                            public void onAppInstallAdLoaded(NativeAppInstallAd nativeAppInstallAd) {
                                View inflate = activity2.getLayoutInflater().inflate(NativeAdsAdapter.this.getRssAdsLayout(i2), null);
                                NativeAdsAdapter.this.bindAppInstall(nativeAppInstallAd, inflate);
                                frameLayout.setBackground(null);
                                frameLayout.removeAllViews();
                                frameLayout.addView(inflate);
                            }
                        });
                    } else {
                        builder.forContentAd(new OnContentAdLoadedListener() {
                            public void onContentAdLoaded(NativeContentAd nativeContentAd) {
                                View inflate = activity2.getLayoutInflater().inflate(NativeAdsAdapter.this.getRssAdsLayout(i2), null);
                                NativeAdsAdapter.this.bindContent(nativeContentAd, inflate);
                                frameLayout.setBackground(null);
                                frameLayout.removeAllViews();
                                frameLayout.addView(inflate);
                            }
                        });
                    }
                    builder.withNativeAdOptions(new NativeAdOptions.Builder().setAdChoicesPlacement(1).build());
                    builder.withAdListener(new AdListener() {
                        public void onAdFailedToLoad(int i) {
                        }
                    }).build().loadAd(new AdRequest.Builder().build());
                }
            };
            activity.runOnUiThread(r0);
        }
    }

    class RecyclerViewRssFacebookAdItem extends ViewHolder {
        RecyclerViewRssFacebookAdItem(View view, Activity activity, int i, SharedPrefHelper sharedPrefHelper) {
            super(view);
            final NativeAdsAdapter nativeAdsAdapter = NativeAdsAdapter.this;
            final View view2 = view;
            final Activity activity2 = activity;
            final int i2 = i;
            C40311 r0 = new Runnable() {
                public void run() {
                    FrameLayout frameLayout = (FrameLayout) view2;
                    float f = activity2.getResources().getDisplayMetrics().density;
                    if (i2 != 10) {
                        frameLayout.setLayoutParams(new LayoutParams(-1, (int) ((((float) ((Integer) NativeAdsAdapter.layoutRssListHeight.get(i2)).intValue()) * f) + 0.5f)));
                    } else {
                        frameLayout.setLayoutParams(new LayoutParams(-1, -2));
                    }
                }
            };
            activity.runOnUiThread(r0);
        }
    }

    public class RecyclerViewYoutubeAdMobAdItem extends ViewHolder {
        public RecyclerViewYoutubeAdMobAdItem(View view, Activity activity, int i, SharedPrefHelper sharedPrefHelper) {
            super(view);
            final NativeAdsAdapter nativeAdsAdapter = NativeAdsAdapter.this;
            final View view2 = view;
            final Activity activity2 = activity;
            final int i2 = i;
            final SharedPrefHelper sharedPrefHelper2 = sharedPrefHelper;
            C40321 r0 = new Runnable() {
                public void run() {
                    final FrameLayout frameLayout = (FrameLayout) view2;
                    frameLayout.setLayoutParams(new LayoutParams(-1, (int) ((((float) ((Integer) NativeAdsAdapter.layoutYoutubeListHeight.get(i2)).intValue()) * activity2.getResources().getDisplayMetrics().density) + 0.5f)));
                    Builder builder = new Builder((Context) activity2, sharedPrefHelper2.getNativeAddUnitID());
                    View inflate = activity2.getLayoutInflater().inflate(NativeAdsAdapter.this.getYoutubeAdsLayout(i2), null);
                    frameLayout.removeAllViews();
                    frameLayout.addView(inflate);
                    int i = i2;
                    if (i == 2 || i == 3 || i == 1) {
                        builder.forAppInstallAd(new OnAppInstallAdLoadedListener() {
                            public void onAppInstallAdLoaded(NativeAppInstallAd nativeAppInstallAd) {
                                View inflate = activity2.getLayoutInflater().inflate(NativeAdsAdapter.this.getYoutubeAdsLayout(i2), null);
                                NativeAdsAdapter.this.bindAppInstall(nativeAppInstallAd, inflate);
                                frameLayout.setBackground(null);
                                frameLayout.removeAllViews();
                                frameLayout.addView(inflate);
                            }
                        });
                    } else {
                        builder.forContentAd(new OnContentAdLoadedListener() {
                            public void onContentAdLoaded(NativeContentAd nativeContentAd) {
                                View inflate = activity2.getLayoutInflater().inflate(NativeAdsAdapter.this.getYoutubeAdsLayout(i2), null);
                                NativeAdsAdapter.this.bindContent(nativeContentAd, inflate);
                                frameLayout.setBackground(null);
                                frameLayout.removeAllViews();
                                frameLayout.addView(inflate);
                            }
                        });
                    }
                    builder.withNativeAdOptions(new NativeAdOptions.Builder().setAdChoicesPlacement(1).build());
                    builder.withAdListener(new AdListener() {
                        public void onAdFailedToLoad(int i) {
                        }
                    }).build().loadAd(new AdRequest.Builder().build());
                }
            };
            activity.runOnUiThread(r0);
        }
    }

    /* access modifiers changed from: private */
    public int getRssAdsLayout(int i) {
        switch (i) {
            case 1:
            case 9:
                return R.layout.rss_ads_content_list_item_full_image;
            case 2:
            case 3:
            case 6:
            case 7:
            case 8:
                return R.layout.rss_ads_app_install_list_item_classic;
            case 4:
                return R.layout.rss_ads_app_install_list_item_square_featured;
            case 5:
                return R.layout.rss_ads_content_list_item_square_featured;
            case 10:
                return R.layout.rss_ads_content_list_item_stragged;
            default:
                return 0;
        }
    }

    private int getRssFacebookAdsLayout(int i) {
        switch (i) {
            case 1:
            case 9:
                return R.layout.rss_ads_facebook_list_item_full_image;
            case 2:
            case 3:
            case 6:
            case 7:
            case 8:
                return R.layout.rss_ads_facebook_list_item_classic;
            case 4:
                return R.layout.rss_ads_facebook_app_install_list_item_square_featured;
            case 5:
                return R.layout.rss_ads_facebook_list_item_square_featured;
            case 10:
                return R.layout.rss_ads_facebook_list_item_stragged;
            default:
                return 0;
        }
    }

    /* access modifiers changed from: private */
    public int getYoutubeAdsLayout(int i) {
        switch (i) {
            case 1:
            case 2:
                return R.layout.video_ads_app_install_list_item_classic;
            case 3:
                return R.layout.rss_ads_app_install_list_item_square_featured;
            case 4:
                return R.layout.video_ads_content_list_item_type_3;
            case 5:
                return R.layout.video_ads_content_list_item_type_5;
            case 6:
                return R.layout.video_ads_content_list_item_full_image;
            default:
                return 0;
        }
    }

    private int getYoutubeFacebookAdsLayout(int i) {
        switch (i) {
            case 1:
            case 2:
                return R.layout.video_facebook_ads_app_install_list_item_classic;
            case 3:
                return R.layout.rss_ads_facebook_app_install_list_item_square_featured;
            case 4:
                return R.layout.video_facebook_ads_content_list_item_type_3;
            case 5:
                return R.layout.video_facebook_ads_content_list_item_type_5;
            case 6:
                return R.layout.video_facebook_ads_content_list_item_full_image;
            default:
                return 0;
        }
    }

    public /* bridge */ /* synthetic */ int getItemCount() {
        return super.getItemCount();
    }

    public /* bridge */ /* synthetic */ void onBindViewHolder(ViewHolder viewHolder, int i) {
        super.onBindViewHolder(viewHolder, i);
    }

    public /* bridge */ /* synthetic */ ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        return super.onCreateViewHolder(viewGroup, i);
    }

    public NativeAdsAdapter(Context context) {
        Integer valueOf = Integer.valueOf(0);
        layoutYoutubeListHeight = Arrays.asList(new Integer[]{valueOf, Integer.valueOf(context.getResources().getInteger(R.integer.youtube_layout_1)), Integer.valueOf(context.getResources().getInteger(R.integer.youtube_layout_1)), Integer.valueOf(context.getResources().getInteger(R.integer.youtube_layout_3)), Integer.valueOf(context.getResources().getInteger(R.integer.youtube_layout_4)), Integer.valueOf(context.getResources().getInteger(R.integer.youtube_layout_5)), Integer.valueOf(context.getResources().getInteger(R.integer.youtube_layout_6))});
        layoutRssListHeight = Arrays.asList(new Integer[]{valueOf, Integer.valueOf(context.getResources().getInteger(R.integer.rss_layout_1)), Integer.valueOf(context.getResources().getInteger(R.integer.rss_layout_2)), Integer.valueOf(context.getResources().getInteger(R.integer.rss_layout_2)), Integer.valueOf(context.getResources().getInteger(R.integer.rss_layout_4)), Integer.valueOf(context.getResources().getInteger(R.integer.rss_layout_5)), Integer.valueOf(context.getResources().getInteger(R.integer.rss_layout_2)), Integer.valueOf(context.getResources().getInteger(R.integer.rss_layout_2)), Integer.valueOf(context.getResources().getInteger(R.integer.rss_layout_2)), Integer.valueOf(context.getResources().getInteger(R.integer.rss_layout_1)), Integer.valueOf(context.getResources().getInteger(R.integer.rss_layout_2))});
    }

    /* access modifiers changed from: private */
    public void bindContent(NativeContentAd nativeContentAd, View view) {
        NativeContentAdView nativeContentAdView = (NativeContentAdView) view.findViewById(R.id.content_ad_view);
        view.findViewById(R.id.empty_image_view).setVisibility(8);
        TextView textView = (TextView) view.findViewById(R.id.tvHeader);
        TextView textView2 = (TextView) view.findViewById(R.id.tvDescription);
        ImageView imageView = (ImageView) view.findViewById(R.id.ivLogo);
        Button button = (Button) view.findViewById(R.id.btnAction);
        ImageView imageView2 = (ImageView) view.findViewById(R.id.ivImage);
        textView.setText(nativeContentAd.getHeadline());
        nativeContentAdView.setHeadlineView(textView);
        textView2.setText(nativeContentAd.getBody());
        nativeContentAdView.setBodyView(textView2);
        button.setText(nativeContentAd.getCallToAction());
        nativeContentAdView.setCallToActionView(button);
        if (nativeContentAd.getImages() == null || nativeContentAd.getImages().size() <= 0) {
            imageView2.setVisibility(8);
        } else {
            imageView2.setImageDrawable(((Image) nativeContentAd.getImages().get(0)).getDrawable());
            imageView2.setVisibility(0);
        }
        nativeContentAdView.setImageView(imageView2);
        nativeContentAdView.setNativeAd(nativeContentAd);
    }

    /* access modifiers changed from: private */
    public void bindAppInstall(NativeAppInstallAd nativeAppInstallAd, View view) {
        NativeAppInstallAdView nativeAppInstallAdView = (NativeAppInstallAdView) view.findViewById(R.id.app_install_ad_view);
        view.findViewById(R.id.empty_image_view).setVisibility(8);
        TextView textView = (TextView) view.findViewById(R.id.tvHeader);
        TextView textView2 = (TextView) view.findViewById(R.id.tvDescription);
        ImageView imageView = (ImageView) view.findViewById(R.id.ivLogo);
        Button button = (Button) view.findViewById(R.id.btnAction);
        ImageView imageView2 = (ImageView) view.findViewById(R.id.ivImage);
        textView.setText(nativeAppInstallAd.getHeadline());
        nativeAppInstallAdView.setHeadlineView(textView);
        textView2.setText(nativeAppInstallAd.getBody());
        nativeAppInstallAdView.setBodyView(textView2);
        button.setText(nativeAppInstallAd.getCallToAction());
        nativeAppInstallAdView.setCallToActionView(button);
        if (nativeAppInstallAd.getImages() == null || nativeAppInstallAd.getImages().size() <= 0) {
            imageView.setVisibility(8);
        } else {
            imageView.setImageDrawable(((Image) nativeAppInstallAd.getImages().get(0)).getDrawable());
            imageView.setVisibility(0);
        }
        nativeAppInstallAdView.setImageView(imageView);
        if (nativeAppInstallAd.getIcon() != null) {
            imageView2.setImageDrawable(nativeAppInstallAd.getIcon().getDrawable());
            imageView2.setVisibility(0);
        } else {
            imageView2.setVisibility(8);
        }
        nativeAppInstallAdView.setIconView(imageView2);
        nativeAppInstallAdView.setNativeAd(nativeAppInstallAd);
    }
}
