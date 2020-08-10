package com.mobiroller.fragments;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.media.AudioManager;
import android.media.AudioManager.OnAudioFocusChangeListener;
import android.net.Uri;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.os.Environment;
import android.util.TimingLogger;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnKeyListener;
import android.view.ViewGroup;
import android.view.WindowManager.LayoutParams;
import android.webkit.GeolocationPermissions.Callback;
import android.webkit.ValueCallback;
import android.webkit.WebSettings.RenderPriority;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.RelativeLayout;
import android.widget.Toast;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.crashlytics.android.Crashlytics;
import com.google.android.exoplayer2.util.MimeTypes;
import com.mobiroller.DynamicConstants;
import com.mobiroller.MobiRollerApplication;
import com.mobiroller.activities.GenericActivity;
import com.mobiroller.helpers.ProgressViewHelper;
import com.mobiroller.helpers.RxJavaRequestHelper;
import com.mobiroller.mobi942763453128.R;
import com.mobiroller.models.WebContent;
import com.mobiroller.util.AdManager;
import com.mobiroller.util.AdManager.InterstitialAdCallBack;
import com.mobiroller.views.VideoEnabledWebChromeClient;
import com.mobiroller.views.VideoEnabledWebChromeClient.ToggledFullscreenCallback;
import com.mobiroller.views.VideoEnabledWebView;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import p043io.fabric.sdk.android.services.events.EventsFilesManager;
import p099im.delight.android.webview.AdvancedWebView.Listener;

public class aveWebViewFragment extends BaseModuleFragment implements Listener {
    private static final int FCR = 1;
    private static final String PDF_MIME_TYPE = "application/pdf";
    private static final int mGeoLocationRequestCode = 1234;
    /* access modifiers changed from: private */
    public static String webScript;
    private boolean isCacheEnabled = false;
    /* access modifiers changed from: private */
    public boolean isPageLoading = false;
    /* access modifiers changed from: private */
    public boolean isProgressViewEnabled = true;
    /* access modifiers changed from: private */
    public String mCM;
    /* access modifiers changed from: private */
    public Callback mGeoLocationCallBack;
    /* access modifiers changed from: private */
    public String mGeoLocationRequestOrigin;
    /* access modifiers changed from: private */
    public ValueCallback<Uri> mUM;
    /* access modifiers changed from: private */
    public ValueCallback<Uri[]> mUMA;
    @BindView(2131363395)
    public VideoEnabledWebView mWebView;
    /* access modifiers changed from: private */
    public boolean multiple_files = false;
    ProgressViewHelper progressViewHelper;
    @BindView(2131363394)
    RelativeLayout relLayout;
    private ViewGroup videoLayout;
    private WebContent webContent;

    public void onExternalPageRequest(String str) {
    }

    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        View inflate = layoutInflater.inflate(R.layout.new_web_view, viewGroup, false);
        this.unbinder = ButterKnife.bind((Object) this, inflate);
        this.progressViewHelper = new ProgressViewHelper(getActivity());
        if (this.screenModel != null) {
            this.isProgressViewEnabled = this.screenModel.getShowProgress().booleanValue();
            if (this.screenModel.isCacheEnabled != null && this.screenModel.isCacheEnabled.booleanValue()) {
                this.isCacheEnabled = true;
            }
        } else if (getArguments() != null) {
            String str = "webContent";
            if (getArguments().containsKey(str)) {
                this.webContent = (WebContent) getArguments().getSerializable(str);
            }
        }
        this.videoLayout = (ViewGroup) inflate.findViewById(R.id.videoLayout);
        hideToolbar((Toolbar) inflate.findViewById(R.id.toolbar_top));
        if (!getActivity().isFinishing() && this.isProgressViewEnabled) {
            this.progressViewHelper.show();
        }
        if (bundle != null) {
            this.mWebView.restoreState(bundle);
            if (this.isProgressViewEnabled) {
                this.progressViewHelper.dismiss();
            }
        } else {
            loadUi();
        }
        return inflate;
    }

    public void loadUi() {
        String localizedTitle;
        if (this.networkHelper.isConnected()) {
            final TimingLogger timingLogger = new TimingLogger("WebView", "methodA");
            this.mWebView.getSettings().setRenderPriority(RenderPriority.HIGH);
            if (this.isCacheEnabled) {
                this.mWebView.getSettings().setCacheMode(1);
            }
            this.mWebView.setListener((Activity) getActivity(), (Listener) this);
            C41391 r2 = new WebViewClient() {
                public void onPageStarted(WebView webView, String str, Bitmap bitmap) {
                    aveWebViewFragment.this.isPageLoading = true;
                    timingLogger.addSplit("work A");
                    if (aveWebViewFragment.this.isProgressViewEnabled && aveWebViewFragment.this.getUserVisibleHint() && aveWebViewFragment.this.getActivity() != null && !aveWebViewFragment.this.getActivity().isFinishing()) {
                        aveWebViewFragment.this.progressViewHelper.show();
                    }
                }

                public void onPageFinished(WebView webView, String str) {
                    aveWebViewFragment.this.isPageLoading = false;
                    timingLogger.addSplit("work B");
                    if (aveWebViewFragment.webScript != null) {
                        StringBuilder sb = new StringBuilder();
                        sb.append("javascript:");
                        sb.append(aveWebViewFragment.webScript);
                        webView.loadUrl(sb.toString());
                    }
                    if (aveWebViewFragment.this.isProgressViewEnabled) {
                        aveWebViewFragment.this.progressViewHelper.dismiss();
                    }
                    timingLogger.dumpToLog();
                }

                public void onReceivedError(WebView webView, int i, String str, String str2) {
                    aveWebViewFragment.this.isPageLoading = false;
                    try {
                        aveWebViewFragment.this.mWebView.stopLoading();
                        if (aveWebViewFragment.this.isProgressViewEnabled) {
                            aveWebViewFragment.this.progressViewHelper.dismiss();
                        }
                    } catch (Exception unused) {
                    }
                    super.onReceivedError(webView, i, str, str2);
                }

                public boolean shouldOverrideUrlLoading(WebView webView, String str) {
                    try {
                        if (!str.startsWith("http:")) {
                            if (!str.startsWith("https:")) {
                                String str2 = "android.intent.action.VIEW";
                                if (str.startsWith("intent://")) {
                                    if (str.contains("scheme=http")) {
                                        str = Uri.decode(str);
                                        Matcher matcher = Pattern.compile("intent://(.*?)#").matcher(str);
                                        if (matcher.find()) {
                                            String group = matcher.group(1);
                                            StringBuilder sb = new StringBuilder();
                                            sb.append("http://");
                                            sb.append(group);
                                            aveWebViewFragment.this.startActivity(new Intent(str2, Uri.parse(sb.toString())));
                                            return true;
                                        }
                                    }
                                }
                                aveWebViewFragment.this.startActivity(new Intent(str2, Uri.parse(str)));
                                return true;
                            }
                        }
                        aveWebViewFragment.this.checkInterstitialAd();
                        return false;
                    } catch (Exception e) {
                        Crashlytics.logException(e);
                        return true;
                    }
                }
            };
            this.mWebView.getSettings().setUseWideViewPort(true);
            this.mWebView.getSettings().setLoadWithOverviewMode(true);
            this.mWebView.getSettings().setBuiltInZoomControls(true);
            this.mWebView.getSettings().setDisplayZoomControls(false);
            this.mWebView.getSettings().setJavaScriptEnabled(true);
            this.mWebView.getSettings().setAllowFileAccess(true);
            String str = null;
            if (VERSION.SDK_INT >= 21) {
                this.mWebView.getSettings().setMixedContentMode(0);
                this.mWebView.setLayerType(2, null);
            } else if (VERSION.SDK_INT >= 19) {
                this.mWebView.setLayerType(2, null);
            } else {
                this.mWebView.setLayerType(1, null);
            }
            this.mWebView.setWebViewClient(r2);
            C41402 r7 = new VideoEnabledWebChromeClient(this.relLayout, this.videoLayout, getLayoutInflater().inflate(R.layout.view_loading_video, null), this.mWebView) {
                public void onProgressChanged(WebView webView, int i) {
                }

                public void onGeolocationPermissionsShowPrompt(String str, Callback callback) {
                    aveWebViewFragment.this.mGeoLocationCallBack = callback;
                    aveWebViewFragment.this.mGeoLocationRequestOrigin = str;
                    if (ContextCompat.checkSelfPermission(aveWebViewFragment.this.getActivity(), "android.permission.ACCESS_COARSE_LOCATION") != 0) {
                        aveWebViewFragment.this.mGeoLocationCallBack = callback;
                        aveWebViewFragment.this.mGeoLocationRequestOrigin = str;
                        String str2 = "android.permission.ACCESS_FINE_LOCATION";
                        if (ActivityCompat.shouldShowRequestPermissionRationale(aveWebViewFragment.this.getActivity(), str2)) {
                            ActivityCompat.requestPermissions(aveWebViewFragment.this.getActivity(), new String[]{str2}, aveWebViewFragment.mGeoLocationRequestCode);
                            return;
                        }
                        Toast.makeText(aveWebViewFragment.this.getActivity(), R.string.permission_location_denied_webview_user_location_never, 0).show();
                        return;
                    }
                    callback.invoke(str, true, true);
                }

                public void openFileChooser(ValueCallback<Uri> valueCallback, String str, String str2) {
                    aveWebViewFragment.this.mUM = valueCallback;
                    Intent intent = new Intent("android.intent.action.GET_CONTENT");
                    intent.addCategory("android.intent.category.OPENABLE");
                    intent.setType("*/*");
                    if (aveWebViewFragment.this.multiple_files && VERSION.SDK_INT >= 18) {
                        intent.putExtra("android.intent.extra.ALLOW_MULTIPLE", true);
                    }
                    aveWebViewFragment.this.startActivityForResult(Intent.createChooser(intent, "File Chooser"), 1);
                }

                /* JADX WARNING: Removed duplicated region for block: B:25:0x00a1  */
                /* JADX WARNING: Removed duplicated region for block: B:26:0x00c5  */
                /* JADX WARNING: Removed duplicated region for block: B:29:0x00df  */
                /* JADX WARNING: Removed duplicated region for block: B:31:0x00e6  */
                /* JADX WARNING: Removed duplicated region for block: B:32:0x00eb  */
                /* Code decompiled incorrectly, please refer to instructions dump. */
                public boolean onShowFileChooser(android.webkit.WebView r7, android.webkit.ValueCallback<android.net.Uri[]> r8, android.webkit.WebChromeClient.FileChooserParams r9) {
                    /*
                        r6 = this;
                        com.mobiroller.fragments.aveWebViewFragment r7 = com.mobiroller.fragments.aveWebViewFragment.this
                        boolean r7 = r7.checkFilePermission()
                        r9 = 0
                        if (r7 == 0) goto L_0x010b
                        r7 = 2
                        java.lang.String[] r0 = new java.lang.String[r7]
                        java.lang.String r1 = "android.permission.WRITE_EXTERNAL_STORAGE"
                        r0[r9] = r1
                        java.lang.String r2 = "android.permission.READ_EXTERNAL_STORAGE"
                        r3 = 1
                        r0[r3] = r2
                        com.mobiroller.fragments.aveWebViewFragment r4 = com.mobiroller.fragments.aveWebViewFragment.this
                        androidx.fragment.app.FragmentActivity r4 = r4.getActivity()
                        int r4 = androidx.core.content.ContextCompat.checkSelfPermission(r4, r1)
                        if (r4 == 0) goto L_0x0039
                        com.mobiroller.fragments.aveWebViewFragment r4 = com.mobiroller.fragments.aveWebViewFragment.this
                        androidx.fragment.app.FragmentActivity r4 = r4.getActivity()
                        java.lang.String r5 = "android.permission.CAMERA"
                        int r4 = androidx.core.content.ContextCompat.checkSelfPermission(r4, r5)
                        if (r4 == 0) goto L_0x0039
                        com.mobiroller.fragments.aveWebViewFragment r7 = com.mobiroller.fragments.aveWebViewFragment.this
                        androidx.fragment.app.FragmentActivity r7 = r7.getActivity()
                        androidx.core.app.ActivityCompat.requestPermissions(r7, r0, r3)
                        goto L_0x0054
                    L_0x0039:
                        com.mobiroller.fragments.aveWebViewFragment r0 = com.mobiroller.fragments.aveWebViewFragment.this
                        androidx.fragment.app.FragmentActivity r0 = r0.getActivity()
                        int r0 = androidx.core.content.ContextCompat.checkSelfPermission(r0, r1)
                        if (r0 == 0) goto L_0x0054
                        com.mobiroller.fragments.aveWebViewFragment r0 = com.mobiroller.fragments.aveWebViewFragment.this
                        androidx.fragment.app.FragmentActivity r0 = r0.getActivity()
                        java.lang.String[] r7 = new java.lang.String[r7]
                        r7[r9] = r1
                        r7[r3] = r2
                        androidx.core.app.ActivityCompat.requestPermissions(r0, r7, r3)
                    L_0x0054:
                        com.mobiroller.fragments.aveWebViewFragment r7 = com.mobiroller.fragments.aveWebViewFragment.this
                        android.webkit.ValueCallback r7 = r7.mUMA
                        r0 = 0
                        if (r7 == 0) goto L_0x0066
                        com.mobiroller.fragments.aveWebViewFragment r7 = com.mobiroller.fragments.aveWebViewFragment.this
                        android.webkit.ValueCallback r7 = r7.mUMA
                        r7.onReceiveValue(r0)
                    L_0x0066:
                        com.mobiroller.fragments.aveWebViewFragment r7 = com.mobiroller.fragments.aveWebViewFragment.this
                        r7.mUMA = r8
                        android.content.Intent r7 = new android.content.Intent
                        java.lang.String r8 = "android.media.action.IMAGE_CAPTURE"
                        r7.<init>(r8)
                        com.mobiroller.fragments.aveWebViewFragment r8 = com.mobiroller.fragments.aveWebViewFragment.this
                        androidx.fragment.app.FragmentActivity r8 = r8.getActivity()
                        android.content.pm.PackageManager r8 = r8.getPackageManager()
                        android.content.ComponentName r8 = r7.resolveActivity(r8)
                        if (r8 == 0) goto L_0x00c6
                        com.mobiroller.fragments.aveWebViewFragment r8 = com.mobiroller.fragments.aveWebViewFragment.this     // Catch:{ IOException -> 0x0096 }
                        java.io.File r8 = r8.createImageFile()     // Catch:{ IOException -> 0x0096 }
                        java.lang.String r1 = "PhotoPath"
                        com.mobiroller.fragments.aveWebViewFragment r2 = com.mobiroller.fragments.aveWebViewFragment.this     // Catch:{ IOException -> 0x0094 }
                        java.lang.String r2 = r2.mCM     // Catch:{ IOException -> 0x0094 }
                        r7.putExtra(r1, r2)     // Catch:{ IOException -> 0x0094 }
                        goto L_0x009f
                    L_0x0094:
                        r1 = move-exception
                        goto L_0x0098
                    L_0x0096:
                        r1 = move-exception
                        r8 = r0
                    L_0x0098:
                        java.lang.String r2 = "Fail"
                        java.lang.String r4 = "Image file creation failed"
                        android.util.Log.e(r2, r4, r1)
                    L_0x009f:
                        if (r8 == 0) goto L_0x00c5
                        com.mobiroller.fragments.aveWebViewFragment r0 = com.mobiroller.fragments.aveWebViewFragment.this
                        java.lang.StringBuilder r1 = new java.lang.StringBuilder
                        r1.<init>()
                        java.lang.String r2 = "file:"
                        r1.append(r2)
                        java.lang.String r2 = r8.getAbsolutePath()
                        r1.append(r2)
                        java.lang.String r1 = r1.toString()
                        r0.mCM = r1
                        android.net.Uri r8 = android.net.Uri.fromFile(r8)
                        java.lang.String r0 = "output"
                        r7.putExtra(r0, r8)
                        goto L_0x00c6
                    L_0x00c5:
                        r7 = r0
                    L_0x00c6:
                        android.content.Intent r8 = new android.content.Intent
                        java.lang.String r0 = "android.intent.action.GET_CONTENT"
                        r8.<init>(r0)
                        java.lang.String r0 = "android.intent.category.OPENABLE"
                        r8.addCategory(r0)
                        java.lang.String r0 = "*/*"
                        r8.setType(r0)
                        com.mobiroller.fragments.aveWebViewFragment r0 = com.mobiroller.fragments.aveWebViewFragment.this
                        boolean r0 = r0.multiple_files
                        if (r0 == 0) goto L_0x00e4
                        java.lang.String r0 = "android.intent.extra.ALLOW_MULTIPLE"
                        r8.putExtra(r0, r3)
                    L_0x00e4:
                        if (r7 == 0) goto L_0x00eb
                        android.content.Intent[] r0 = new android.content.Intent[r3]
                        r0[r9] = r7
                        goto L_0x00ed
                    L_0x00eb:
                        android.content.Intent[] r0 = new android.content.Intent[r9]
                    L_0x00ed:
                        android.content.Intent r7 = new android.content.Intent
                        java.lang.String r9 = "android.intent.action.CHOOSER"
                        r7.<init>(r9)
                        java.lang.String r9 = "android.intent.extra.INTENT"
                        r7.putExtra(r9, r8)
                        java.lang.String r8 = "android.intent.extra.TITLE"
                        java.lang.String r9 = "File Chooser"
                        r7.putExtra(r8, r9)
                        java.lang.String r8 = "android.intent.extra.INITIAL_INTENTS"
                        r7.putExtra(r8, r0)
                        com.mobiroller.fragments.aveWebViewFragment r8 = com.mobiroller.fragments.aveWebViewFragment.this
                        r8.startActivityForResult(r7, r3)
                        return r3
                    L_0x010b:
                        return r9
                    */
                    throw new UnsupportedOperationException("Method not decompiled: com.mobiroller.fragments.aveWebViewFragment.C41402.onShowFileChooser(android.webkit.WebView, android.webkit.ValueCallback, android.webkit.WebChromeClient$FileChooserParams):boolean");
                }
            };
            r7.setOnToggledFullscreen(new ToggledFullscreenCallback() {
                public void toggledFullscreen(boolean z) {
                    if (z) {
                        LayoutParams attributes = aveWebViewFragment.this.getActivity().getWindow().getAttributes();
                        attributes.flags |= 1024;
                        attributes.flags |= 128;
                        aveWebViewFragment.this.getActivity().getWindow().setAttributes(attributes);
                        if (VERSION.SDK_INT >= 14) {
                            aveWebViewFragment.this.getActivity().getWindow().getDecorView().setSystemUiVisibility(1);
                            return;
                        }
                        return;
                    }
                    LayoutParams attributes2 = aveWebViewFragment.this.getActivity().getWindow().getAttributes();
                    attributes2.flags &= -1025;
                    attributes2.flags &= -129;
                    aveWebViewFragment.this.getActivity().getWindow().setAttributes(attributes2);
                    if (VERSION.SDK_INT >= 14) {
                        aveWebViewFragment.this.getActivity().getWindow().getDecorView().setSystemUiVisibility(0);
                    }
                }
            });
            this.mWebView.setWebChromeClient(r7);
            try {
                if (this.screenModel != null) {
                    if (this.screenModel.getLocalizedURL() == null) {
                        localizedTitle = this.screenModel.getURL();
                    } else {
                        localizedTitle = this.localizationHelper.getLocalizedTitle(this.screenModel.getLocalizedURL());
                        if (localizedTitle == null || localizedTitle.isEmpty()) {
                            localizedTitle = this.screenModel.getURL();
                        }
                    }
                    str = localizedTitle;
                    if (this.screenModel.getScriptPath() != null && !this.screenModel.getScriptPath().equalsIgnoreCase("")) {
                        webScript = (String) new RxJavaRequestHelper(getActivity(), this.sharedPrefHelper).getAPIService().getScript(this.screenModel.getScriptPath()).execute().body();
                    }
                } else if (this.webContent != null) {
                    str = this.webContent.url;
                }
                if (str != null) {
                    this.mWebView.loadUrl(str);
                }
            } catch (Exception e) {
                if (this.isProgressViewEnabled) {
                    this.progressViewHelper.dismiss();
                }
                Toast.makeText(getActivity(), getString(R.string.common_error), 0).show();
                e.printStackTrace();
            }
            this.mWebView.setOnKeyListener(new OnKeyListener() {
                public boolean onKey(View view, int i, KeyEvent keyEvent) {
                    if (i != 4 || keyEvent.getAction() != 0 || !aveWebViewFragment.this.mWebView.canGoBack()) {
                        return false;
                    }
                    aveWebViewFragment.this.mWebView.goBack();
                    return true;
                }
            });
            return;
        }
        Toast.makeText(getActivity().getApplicationContext(), getResources().getString(R.string.please_check_your_internet_connection), 1).show();
        getActivity().finish();
    }

    public void onResume() {
        super.onResume();
        if (this.relLayout != null) {
            this.bannerHelper.addBannerAd(this.relLayout, this.mWebView);
        }
        getView().setFocusableInTouchMode(true);
        getView().requestFocus();
        getView().setOnKeyListener(new OnKeyListener() {
            public boolean onKey(View view, int i, KeyEvent keyEvent) {
                if (i == 4) {
                    aveWebViewFragment.this.mWebView.stopLoading();
                    ((AudioManager) aveWebViewFragment.this.getActivity().getSystemService(MimeTypes.BASE_TYPE_AUDIO)).requestAudioFocus(new OnAudioFocusChangeListener() {
                        public void onAudioFocusChange(int i) {
                        }
                    }, 3, 2);
                    if (aveWebViewFragment.this.mWebView.canGoBack()) {
                        aveWebViewFragment.this.mWebView.goBack();
                    } else {
                        aveWebViewFragment.this.mWebView.removeAllViews();
                    }
                }
                return false;
            }
        });
    }

    public void onDestroyView() {
        if (this.isProgressViewEnabled) {
            ProgressViewHelper progressViewHelper2 = this.progressViewHelper;
            if (progressViewHelper2 != null) {
                progressViewHelper2.dismiss();
            }
        }
        super.onDestroyView();
    }

    public void onPageStarted(String str, Bitmap bitmap) {
        this.isPageLoading = true;
    }

    public void onPageFinished(String str) {
        this.isPageLoading = false;
        if (this.isProgressViewEnabled) {
            this.progressViewHelper.dismiss();
        }
    }

    public void onPageError(int i, String str, String str2) {
        this.isPageLoading = false;
        if (this.isProgressViewEnabled) {
            this.progressViewHelper.dismiss();
        }
    }

    public void onDownloadRequested(String str, String str2, String str3, long j, String str4, String str5) {
        if (!str3.equals(PDF_MIME_TYPE)) {
            Intent intent = new Intent("android.intent.action.VIEW");
            intent.setData(Uri.parse(str));
            startActivity(intent);
            Toast.makeText(getActivity().getApplicationContext(), getResources().getString(R.string.web_module_redirecting_for_download), 1).show();
            this.mWebView.goBack();
        } else if (getActivity() != null) {
            Intent intent2 = new Intent(getActivity(), GenericActivity.class);
            intent2.putExtra("pdf_url", str);
            startActivity(intent2);
            this.mWebView.goBack();
        }
    }

    public void onActivityResult(int i, int i2, Intent intent) {
        Uri[] uriArr;
        super.onActivityResult(i, i2, intent);
        if (i != 1) {
            this.mWebView.onActivityResult(i, i2, intent);
        } else if (VERSION.SDK_INT >= 21) {
            if (i2 == -1) {
                if (this.mUMA != null) {
                    if (intent == null || intent.getData() == null) {
                        String str = this.mCM;
                        if (str != null) {
                            uriArr = new Uri[]{Uri.parse(str)};
                            this.mUMA.onReceiveValue(uriArr);
                            this.mUMA = null;
                        }
                    } else {
                        String dataString = intent.getDataString();
                        if (dataString != null) {
                            uriArr = new Uri[]{Uri.parse(dataString)};
                        } else if (this.multiple_files && intent.getClipData() != null) {
                            int itemCount = intent.getClipData().getItemCount();
                            Uri[] uriArr2 = new Uri[itemCount];
                            for (int i3 = 0; i3 < itemCount; i3++) {
                                uriArr2[i3] = intent.getClipData().getItemAt(i3).getUri();
                            }
                            uriArr = uriArr2;
                        }
                        this.mUMA.onReceiveValue(uriArr);
                        this.mUMA = null;
                    }
                } else {
                    return;
                }
            }
            uriArr = null;
            this.mUMA.onReceiveValue(uriArr);
            this.mUMA = null;
        } else if (this.mUM != null) {
            this.mUM.onReceiveValue((intent == null || i2 != -1) ? null : intent.getData());
            this.mUM = null;
        }
    }

    public void onSaveInstanceState(Bundle bundle) {
        super.onSaveInstanceState(bundle);
        this.mWebView.saveState(bundle);
    }

    public void setMenuVisibility(boolean z) {
        super.setMenuVisibility(z);
        if (z && this.isPageLoading && this.isProgressViewEnabled) {
            this.progressViewHelper.show();
        }
    }

    /* access modifiers changed from: private */
    public void checkInterstitialAd() {
        if (AdManager.getInstance().isInterstitialAdReady() && !MobiRollerApplication.isInterstitialShown) {
            AdManager.getInstance().showInterstitialAd(new InterstitialAdCallBack() {
                public void onAdClosed() {
                }
            });
        } else if (this.networkHelper.isConnected() && !DynamicConstants.MobiRoller_Stage && this.sharedPrefHelper.getIsAdmobInterstitialAdEnabled() && this.sharedPrefHelper.getInterstitialClickCount() >= this.sharedPrefHelper.getInterstitialClickInterval() && this.sharedPrefHelper.getInterstitialTimer(new Date()) && this.sharedPrefHelper.getInterstitialMultipleDisplayEnabled() && AdManager.getInstance().isInterstitialAdReady()) {
            AdManager.getInstance().showInterstitialAd(new InterstitialAdCallBack() {
                public void onAdClosed() {
                }
            });
        }
    }

    public void onRequestPermissionsResult(int i, String[] strArr, int[] iArr) {
        if (i != mGeoLocationRequestCode) {
            return;
        }
        if (iArr.length <= 0 || iArr[0] != 0) {
            Callback callback = this.mGeoLocationCallBack;
            if (callback != null) {
                callback.invoke(this.mGeoLocationRequestOrigin, false, false);
                return;
            }
            return;
        }
        Callback callback2 = this.mGeoLocationCallBack;
        if (callback2 != null) {
            callback2.invoke(this.mGeoLocationRequestOrigin, true, true);
        }
    }

    /* access modifiers changed from: private */
    public File createImageFile() throws IOException {
        String format = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        StringBuilder sb = new StringBuilder();
        sb.append("img_");
        sb.append(format);
        sb.append(EventsFilesManager.ROLL_OVER_FILE_NAME_SEPARATOR);
        return File.createTempFile(sb.toString(), ".jpg", Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES));
    }

    /* access modifiers changed from: private */
    public boolean checkFilePermission() {
        if (VERSION.SDK_INT >= 23) {
            String str = "android.permission.WRITE_EXTERNAL_STORAGE";
            String str2 = "android.permission.CAMERA";
            if (!(ContextCompat.checkSelfPermission(getActivity(), str) == 0 && ContextCompat.checkSelfPermission(getActivity(), str2) == 0)) {
                ActivityCompat.requestPermissions(getActivity(), new String[]{str, str2}, 1);
                return false;
            }
        }
        return true;
    }
}
