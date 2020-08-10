package p099im.delight.android.webview;

import android.app.Activity;
import android.app.Fragment;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.graphics.Bitmap;
import android.net.Uri;
import android.net.http.SslError;
import android.os.Build.VERSION;
import android.os.Message;
import android.util.AttributeSet;
import android.util.Base64;
import android.view.InputEvent;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.ClientCertRequest;
import android.webkit.ConsoleMessage;
import android.webkit.CookieManager;
import android.webkit.DownloadListener;
import android.webkit.GeolocationPermissions.Callback;
import android.webkit.HttpAuthHandler;
import android.webkit.JsPromptResult;
import android.webkit.JsResult;
import android.webkit.PermissionRequest;
import android.webkit.SslErrorHandler;
import android.webkit.URLUtil;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebChromeClient.CustomViewCallback;
import android.webkit.WebChromeClient.FileChooserParams;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebSettings;
import android.webkit.WebSettings.RenderPriority;
import android.webkit.WebStorage.QuotaUpdater;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import com.fasterxml.jackson.core.JsonPointer;
import java.io.UnsupportedEncodingException;
import java.lang.ref.WeakReference;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.MissingResourceException;
import kotlin.text.Typography;
import org.objectweb.asm.signature.SignatureVisitor;

/* renamed from: im.delight.android.webview.AdvancedWebView */
public class AdvancedWebView extends WebView {
    protected static final String[] ALTERNATIVE_BROWSERS = {"org.mozilla.firefox", "com.android.chrome", "com.opera.browser", "org.mozilla.firefox_beta", "com.chrome.beta", "com.opera.browser.beta"};
    protected static final String CHARSET_DEFAULT = "UTF-8";
    protected static final String DATABASES_SUB_FOLDER = "/databases";
    protected static final String LANGUAGE_DEFAULT_ISO3 = "eng";
    public static final String PACKAGE_NAME_DOWNLOAD_MANAGER = "com.android.providers.downloads";
    protected static final int REQUEST_CODE_FILE_PICKER = 51426;
    protected WeakReference<Activity> mActivity;
    protected WebChromeClient mCustomWebChromeClient;
    protected WebViewClient mCustomWebViewClient;
    protected ValueCallback<Uri> mFileUploadCallbackFirst;
    protected ValueCallback<Uri[]> mFileUploadCallbackSecond;
    protected WeakReference<Fragment> mFragment;
    protected boolean mGeolocationEnabled;
    protected final Map<String, String> mHttpHeaders = new HashMap();
    protected String mLanguageIso3;
    protected long mLastError;
    protected Listener mListener;
    protected final List<String> mPermittedHostnames = new LinkedList();
    protected int mRequestCodeFilePicker = REQUEST_CODE_FILE_PICKER;
    protected String mUploadableFileTypes = "*/*";

    /* renamed from: im.delight.android.webview.AdvancedWebView$Browsers */
    public static class Browsers {
        private static String mAlternativePackage;

        public static boolean hasAlternative(Context context) {
            return getAlternative(context) != null;
        }

        public static String getAlternative(Context context) {
            String str = mAlternativePackage;
            if (str != null) {
                return str;
            }
            List asList = Arrays.asList(AdvancedWebView.ALTERNATIVE_BROWSERS);
            for (ApplicationInfo applicationInfo : context.getPackageManager().getInstalledApplications(128)) {
                if (applicationInfo.enabled && asList.contains(applicationInfo.packageName)) {
                    mAlternativePackage = applicationInfo.packageName;
                    return applicationInfo.packageName;
                }
            }
            return null;
        }

        public static void openUrl(Activity activity, String str) {
            openUrl(activity, str, false);
        }

        public static void openUrl(Activity activity, String str, boolean z) {
            Intent intent = new Intent("android.intent.action.VIEW", Uri.parse(str));
            intent.setPackage(getAlternative(activity));
            intent.addFlags(268435456);
            activity.startActivity(intent);
            if (z) {
                activity.overridePendingTransition(0, 0);
            }
        }
    }

    /* renamed from: im.delight.android.webview.AdvancedWebView$Listener */
    public interface Listener {
        void onDownloadRequested(String str, String str2, String str3, long j, String str4, String str5);

        void onExternalPageRequest(String str);

        void onPageError(int i, String str, String str2);

        void onPageFinished(String str);

        void onPageStarted(String str, Bitmap bitmap);
    }

    public AdvancedWebView(Context context) {
        super(context);
        init(context);
    }

    public AdvancedWebView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        init(context);
    }

    public AdvancedWebView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        init(context);
    }

    public void setListener(Activity activity, Listener listener) {
        setListener(activity, listener, (int) REQUEST_CODE_FILE_PICKER);
    }

    public void setListener(Activity activity, Listener listener, int i) {
        if (activity != null) {
            this.mActivity = new WeakReference<>(activity);
        } else {
            this.mActivity = null;
        }
        setListener(listener, i);
    }

    public void setListener(Fragment fragment, Listener listener) {
        setListener(fragment, listener, (int) REQUEST_CODE_FILE_PICKER);
    }

    public void setListener(Fragment fragment, Listener listener, int i) {
        if (fragment != null) {
            this.mFragment = new WeakReference<>(fragment);
        } else {
            this.mFragment = null;
        }
        setListener(listener, i);
    }

    /* access modifiers changed from: protected */
    public void setListener(Listener listener, int i) {
        this.mListener = listener;
        this.mRequestCodeFilePicker = i;
    }

    public void setWebViewClient(WebViewClient webViewClient) {
        this.mCustomWebViewClient = webViewClient;
    }

    public void setWebChromeClient(WebChromeClient webChromeClient) {
        this.mCustomWebChromeClient = webChromeClient;
    }

    public void setGeolocationEnabled(boolean z) {
        if (z) {
            getSettings().setJavaScriptEnabled(true);
            getSettings().setGeolocationEnabled(true);
            setGeolocationDatabasePath();
        }
        this.mGeolocationEnabled = z;
    }

    /* access modifiers changed from: protected */
    public void setGeolocationDatabasePath() {
        Activity activity;
        WeakReference<Fragment> weakReference = this.mFragment;
        if (weakReference == null || weakReference.get() == null || VERSION.SDK_INT < 11 || ((Fragment) this.mFragment.get()).getActivity() == null) {
            WeakReference<Activity> weakReference2 = this.mActivity;
            if (weakReference2 != null && weakReference2.get() != null) {
                activity = (Activity) this.mActivity.get();
            } else {
                return;
            }
        } else {
            activity = ((Fragment) this.mFragment.get()).getActivity();
        }
        getSettings().setGeolocationDatabasePath(activity.getFilesDir().getPath());
    }

    public void setUploadableFileTypes(String str) {
        this.mUploadableFileTypes = str;
    }

    public void loadHtml(String str) {
        loadHtml(str, null);
    }

    public void loadHtml(String str, String str2) {
        loadHtml(str, str2, null);
    }

    public void loadHtml(String str, String str2, String str3) {
        loadHtml(str, str2, str3, "utf-8");
    }

    public void loadHtml(String str, String str2, String str3, String str4) {
        loadDataWithBaseURL(str2, str, "text/html", str4, str3);
    }

    public void onResume() {
        if (VERSION.SDK_INT >= 11) {
            super.onResume();
        }
        resumeTimers();
    }

    public void onPause() {
        pauseTimers();
        if (VERSION.SDK_INT >= 11) {
            super.onPause();
        }
    }

    /* JADX WARNING: Can't wrap try/catch for region: R(6:0|1|2|3|4|6) */
    /* JADX WARNING: Failed to process nested try/catch */
    /* JADX WARNING: Missing exception handler attribute for start block: B:2:0x0009 */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void onDestroy() {
        /*
            r1 = this;
            android.view.ViewParent r0 = r1.getParent()     // Catch:{ Exception -> 0x0009 }
            android.view.ViewGroup r0 = (android.view.ViewGroup) r0     // Catch:{ Exception -> 0x0009 }
            r0.removeView(r1)     // Catch:{ Exception -> 0x0009 }
        L_0x0009:
            r1.removeAllViews()     // Catch:{ Exception -> 0x000c }
        L_0x000c:
            r1.destroy()
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: p099im.delight.android.webview.AdvancedWebView.onDestroy():void");
    }

    public void onActivityResult(int i, int i2, Intent intent) {
        Uri[] uriArr;
        if (i != this.mRequestCodeFilePicker) {
            return;
        }
        if (i2 != -1) {
            ValueCallback<Uri> valueCallback = this.mFileUploadCallbackFirst;
            if (valueCallback != null) {
                valueCallback.onReceiveValue(null);
                this.mFileUploadCallbackFirst = null;
                return;
            }
            ValueCallback<Uri[]> valueCallback2 = this.mFileUploadCallbackSecond;
            if (valueCallback2 != null) {
                valueCallback2.onReceiveValue(null);
                this.mFileUploadCallbackSecond = null;
            }
        } else if (intent != null) {
            ValueCallback<Uri> valueCallback3 = this.mFileUploadCallbackFirst;
            if (valueCallback3 != null) {
                valueCallback3.onReceiveValue(intent.getData());
                this.mFileUploadCallbackFirst = null;
            } else if (this.mFileUploadCallbackSecond != null) {
                try {
                    int i3 = 0;
                    if (intent.getDataString() != null) {
                        uriArr = new Uri[]{Uri.parse(intent.getDataString())};
                    } else {
                        if (VERSION.SDK_INT >= 16 && intent.getClipData() != null) {
                            int itemCount = intent.getClipData().getItemCount();
                            Uri[] uriArr2 = new Uri[itemCount];
                            while (i3 < itemCount) {
                                try {
                                    uriArr2[i3] = intent.getClipData().getItemAt(i3).getUri();
                                    i3++;
                                } catch (Exception unused) {
                                }
                            }
                            uriArr = uriArr2;
                        }
                        uriArr = null;
                    }
                } catch (Exception unused2) {
                }
                this.mFileUploadCallbackSecond.onReceiveValue(uriArr);
                this.mFileUploadCallbackSecond = null;
            }
        }
    }

    public void addHttpHeader(String str, String str2) {
        this.mHttpHeaders.put(str, str2);
    }

    public void removeHttpHeader(String str) {
        this.mHttpHeaders.remove(str);
    }

    public void addPermittedHostname(String str) {
        this.mPermittedHostnames.add(str);
    }

    public void addPermittedHostnames(Collection<? extends String> collection) {
        this.mPermittedHostnames.addAll(collection);
    }

    public List<String> getPermittedHostnames() {
        return this.mPermittedHostnames;
    }

    public void removePermittedHostname(String str) {
        this.mPermittedHostnames.remove(str);
    }

    public void clearPermittedHostnames() {
        this.mPermittedHostnames.clear();
    }

    public boolean onBackPressed() {
        if (!canGoBack()) {
            return true;
        }
        goBack();
        return false;
    }

    protected static void setAllowAccessFromFileUrls(WebSettings webSettings, boolean z) {
        if (VERSION.SDK_INT >= 16) {
            webSettings.setAllowFileAccessFromFileURLs(z);
            webSettings.setAllowUniversalAccessFromFileURLs(z);
        }
    }

    public void setCookiesEnabled(boolean z) {
        CookieManager.getInstance().setAcceptCookie(z);
    }

    public void setThirdPartyCookiesEnabled(boolean z) {
        if (VERSION.SDK_INT >= 21) {
            CookieManager.getInstance().setAcceptThirdPartyCookies(this, z);
        }
    }

    public void setMixedContentAllowed(boolean z) {
        setMixedContentAllowed(getSettings(), z);
    }

    /* access modifiers changed from: protected */
    public void setMixedContentAllowed(WebSettings webSettings, boolean z) {
        if (VERSION.SDK_INT >= 21) {
            webSettings.setMixedContentMode(z ^ true ? 1 : 0);
        }
    }

    public void setDesktopMode(boolean z) {
        String str;
        WebSettings settings = getSettings();
        String str2 = "Android";
        String str3 = "diordnA";
        String str4 = "Mobile";
        String str5 = "eliboM";
        if (z) {
            str = settings.getUserAgentString().replace(str4, str5).replace(str2, str3);
        } else {
            str = settings.getUserAgentString().replace(str5, str4).replace(str3, str2);
        }
        settings.setUserAgentString(str);
        settings.setUseWideViewPort(z);
        settings.setLoadWithOverviewMode(z);
        settings.setSupportZoom(z);
        settings.setBuiltInZoomControls(z);
    }

    /* access modifiers changed from: protected */
    public void init(Context context) {
        if (!isInEditMode()) {
            if (context instanceof Activity) {
                this.mActivity = new WeakReference<>((Activity) context);
            }
            this.mLanguageIso3 = getLanguageIso3();
            setFocusable(true);
            setFocusableInTouchMode(true);
            setSaveEnabled(true);
            String path = context.getFilesDir().getPath();
            StringBuilder sb = new StringBuilder();
            sb.append(path.substring(0, path.lastIndexOf("/")));
            sb.append(DATABASES_SUB_FOLDER);
            String sb2 = sb.toString();
            WebSettings settings = getSettings();
            settings.setAllowFileAccess(false);
            setAllowAccessFromFileUrls(settings, false);
            settings.setBuiltInZoomControls(false);
            settings.setJavaScriptEnabled(true);
            settings.setDomStorageEnabled(true);
            if (VERSION.SDK_INT < 18) {
                settings.setRenderPriority(RenderPriority.HIGH);
            }
            settings.setDatabaseEnabled(true);
            if (VERSION.SDK_INT < 19) {
                settings.setDatabasePath(sb2);
            }
            setMixedContentAllowed(settings, true);
            setThirdPartyCookiesEnabled(true);
            super.setWebViewClient(new WebViewClient() {
                public void onPageStarted(WebView webView, String str, Bitmap bitmap) {
                    if (!AdvancedWebView.this.hasError() && AdvancedWebView.this.mListener != null) {
                        AdvancedWebView.this.mListener.onPageStarted(str, bitmap);
                    }
                    if (AdvancedWebView.this.mCustomWebViewClient != null) {
                        AdvancedWebView.this.mCustomWebViewClient.onPageStarted(webView, str, bitmap);
                    }
                }

                public void onPageFinished(WebView webView, String str) {
                    if (!AdvancedWebView.this.hasError() && AdvancedWebView.this.mListener != null) {
                        AdvancedWebView.this.mListener.onPageFinished(str);
                    }
                    if (AdvancedWebView.this.mCustomWebViewClient != null) {
                        AdvancedWebView.this.mCustomWebViewClient.onPageFinished(webView, str);
                    }
                }

                public void onReceivedError(WebView webView, int i, String str, String str2) {
                    AdvancedWebView.this.setLastError();
                    if (AdvancedWebView.this.mListener != null) {
                        AdvancedWebView.this.mListener.onPageError(i, str, str2);
                    }
                    if (AdvancedWebView.this.mCustomWebViewClient != null) {
                        AdvancedWebView.this.mCustomWebViewClient.onReceivedError(webView, i, str, str2);
                    }
                }

                public boolean shouldOverrideUrlLoading(WebView webView, String str) {
                    Intent intent;
                    if (!AdvancedWebView.this.isPermittedUrl(str)) {
                        if (AdvancedWebView.this.mListener != null) {
                            AdvancedWebView.this.mListener.onExternalPageRequest(str);
                        }
                        return true;
                    } else if (AdvancedWebView.this.mCustomWebViewClient != null && AdvancedWebView.this.mCustomWebViewClient.shouldOverrideUrlLoading(webView, str)) {
                        return true;
                    } else {
                        Uri parse = Uri.parse(str);
                        String scheme = parse.getScheme();
                        if (scheme != null) {
                            if (scheme.equals("tel")) {
                                intent = new Intent("android.intent.action.DIAL", parse);
                            } else {
                                String str2 = "android.intent.action.SENDTO";
                                if (scheme.equals("sms")) {
                                    intent = new Intent(str2, parse);
                                } else if (scheme.equals("mailto")) {
                                    intent = new Intent(str2, parse);
                                } else if (scheme.equals("whatsapp")) {
                                    intent = new Intent(str2, parse);
                                    intent.setPackage("com.whatsapp");
                                } else {
                                    intent = null;
                                }
                            }
                            if (intent != null) {
                                intent.addFlags(268435456);
                                try {
                                    if (AdvancedWebView.this.mActivity == null || AdvancedWebView.this.mActivity.get() == null) {
                                        AdvancedWebView.this.getContext().startActivity(intent);
                                    } else {
                                        ((Activity) AdvancedWebView.this.mActivity.get()).startActivity(intent);
                                    }
                                } catch (ActivityNotFoundException unused) {
                                }
                                return true;
                            }
                        }
                        webView.loadUrl(str);
                        return true;
                    }
                }

                public void onLoadResource(WebView webView, String str) {
                    if (AdvancedWebView.this.mCustomWebViewClient != null) {
                        AdvancedWebView.this.mCustomWebViewClient.onLoadResource(webView, str);
                    } else {
                        super.onLoadResource(webView, str);
                    }
                }

                public WebResourceResponse shouldInterceptRequest(WebView webView, String str) {
                    if (VERSION.SDK_INT < 11) {
                        return null;
                    }
                    if (AdvancedWebView.this.mCustomWebViewClient != null) {
                        return AdvancedWebView.this.mCustomWebViewClient.shouldInterceptRequest(webView, str);
                    }
                    return super.shouldInterceptRequest(webView, str);
                }

                public WebResourceResponse shouldInterceptRequest(WebView webView, WebResourceRequest webResourceRequest) {
                    if (VERSION.SDK_INT < 21) {
                        return null;
                    }
                    if (AdvancedWebView.this.mCustomWebViewClient != null) {
                        return AdvancedWebView.this.mCustomWebViewClient.shouldInterceptRequest(webView, webResourceRequest);
                    }
                    return super.shouldInterceptRequest(webView, webResourceRequest);
                }

                public void onFormResubmission(WebView webView, Message message, Message message2) {
                    if (AdvancedWebView.this.mCustomWebViewClient != null) {
                        AdvancedWebView.this.mCustomWebViewClient.onFormResubmission(webView, message, message2);
                    } else {
                        super.onFormResubmission(webView, message, message2);
                    }
                }

                public void doUpdateVisitedHistory(WebView webView, String str, boolean z) {
                    if (AdvancedWebView.this.mCustomWebViewClient != null) {
                        AdvancedWebView.this.mCustomWebViewClient.doUpdateVisitedHistory(webView, str, z);
                    } else {
                        super.doUpdateVisitedHistory(webView, str, z);
                    }
                }

                public void onReceivedSslError(WebView webView, SslErrorHandler sslErrorHandler, SslError sslError) {
                    if (AdvancedWebView.this.mCustomWebViewClient != null) {
                        AdvancedWebView.this.mCustomWebViewClient.onReceivedSslError(webView, sslErrorHandler, sslError);
                    } else {
                        super.onReceivedSslError(webView, sslErrorHandler, sslError);
                    }
                }

                public void onReceivedClientCertRequest(WebView webView, ClientCertRequest clientCertRequest) {
                    if (VERSION.SDK_INT < 21) {
                        return;
                    }
                    if (AdvancedWebView.this.mCustomWebViewClient != null) {
                        AdvancedWebView.this.mCustomWebViewClient.onReceivedClientCertRequest(webView, clientCertRequest);
                    } else {
                        super.onReceivedClientCertRequest(webView, clientCertRequest);
                    }
                }

                public void onReceivedHttpAuthRequest(WebView webView, HttpAuthHandler httpAuthHandler, String str, String str2) {
                    if (AdvancedWebView.this.mCustomWebViewClient != null) {
                        AdvancedWebView.this.mCustomWebViewClient.onReceivedHttpAuthRequest(webView, httpAuthHandler, str, str2);
                    } else {
                        super.onReceivedHttpAuthRequest(webView, httpAuthHandler, str, str2);
                    }
                }

                public boolean shouldOverrideKeyEvent(WebView webView, KeyEvent keyEvent) {
                    if (AdvancedWebView.this.mCustomWebViewClient != null) {
                        return AdvancedWebView.this.mCustomWebViewClient.shouldOverrideKeyEvent(webView, keyEvent);
                    }
                    return super.shouldOverrideKeyEvent(webView, keyEvent);
                }

                public void onUnhandledKeyEvent(WebView webView, KeyEvent keyEvent) {
                    if (AdvancedWebView.this.mCustomWebViewClient != null) {
                        AdvancedWebView.this.mCustomWebViewClient.onUnhandledKeyEvent(webView, keyEvent);
                    } else {
                        super.onUnhandledKeyEvent(webView, keyEvent);
                    }
                }

                public void onUnhandledInputEvent(WebView webView, InputEvent inputEvent) {
                    if (VERSION.SDK_INT < 21) {
                        return;
                    }
                    if (AdvancedWebView.this.mCustomWebViewClient != null) {
                        AdvancedWebView.this.mCustomWebViewClient.onUnhandledInputEvent(webView, inputEvent);
                    } else {
                        super.onUnhandledInputEvent(webView, inputEvent);
                    }
                }

                public void onScaleChanged(WebView webView, float f, float f2) {
                    if (AdvancedWebView.this.mCustomWebViewClient != null) {
                        AdvancedWebView.this.mCustomWebViewClient.onScaleChanged(webView, f, f2);
                    } else {
                        super.onScaleChanged(webView, f, f2);
                    }
                }

                public void onReceivedLoginRequest(WebView webView, String str, String str2, String str3) {
                    if (VERSION.SDK_INT < 12) {
                        return;
                    }
                    if (AdvancedWebView.this.mCustomWebViewClient != null) {
                        AdvancedWebView.this.mCustomWebViewClient.onReceivedLoginRequest(webView, str, str2, str3);
                    } else {
                        super.onReceivedLoginRequest(webView, str, str2, str3);
                    }
                }
            });
            super.setWebChromeClient(new WebChromeClient() {
                public void openFileChooser(ValueCallback<Uri> valueCallback) {
                    openFileChooser(valueCallback, null);
                }

                public void openFileChooser(ValueCallback<Uri> valueCallback, String str) {
                    openFileChooser(valueCallback, str, null);
                }

                public void openFileChooser(ValueCallback<Uri> valueCallback, String str, String str2) {
                    AdvancedWebView.this.openFileInput(valueCallback, null, false);
                }

                public boolean onShowFileChooser(WebView webView, ValueCallback<Uri[]> valueCallback, FileChooserParams fileChooserParams) {
                    boolean z = false;
                    if (VERSION.SDK_INT < 21) {
                        return false;
                    }
                    if (fileChooserParams.getMode() == 1) {
                        z = true;
                    }
                    AdvancedWebView.this.openFileInput(null, valueCallback, z);
                    return true;
                }

                public void onProgressChanged(WebView webView, int i) {
                    if (AdvancedWebView.this.mCustomWebChromeClient != null) {
                        AdvancedWebView.this.mCustomWebChromeClient.onProgressChanged(webView, i);
                    } else {
                        super.onProgressChanged(webView, i);
                    }
                }

                public void onReceivedTitle(WebView webView, String str) {
                    if (AdvancedWebView.this.mCustomWebChromeClient != null) {
                        AdvancedWebView.this.mCustomWebChromeClient.onReceivedTitle(webView, str);
                    } else {
                        super.onReceivedTitle(webView, str);
                    }
                }

                public void onReceivedIcon(WebView webView, Bitmap bitmap) {
                    if (AdvancedWebView.this.mCustomWebChromeClient != null) {
                        AdvancedWebView.this.mCustomWebChromeClient.onReceivedIcon(webView, bitmap);
                    } else {
                        super.onReceivedIcon(webView, bitmap);
                    }
                }

                public void onReceivedTouchIconUrl(WebView webView, String str, boolean z) {
                    if (AdvancedWebView.this.mCustomWebChromeClient != null) {
                        AdvancedWebView.this.mCustomWebChromeClient.onReceivedTouchIconUrl(webView, str, z);
                    } else {
                        super.onReceivedTouchIconUrl(webView, str, z);
                    }
                }

                public void onShowCustomView(View view, CustomViewCallback customViewCallback) {
                    if (AdvancedWebView.this.mCustomWebChromeClient != null) {
                        AdvancedWebView.this.mCustomWebChromeClient.onShowCustomView(view, customViewCallback);
                    } else {
                        super.onShowCustomView(view, customViewCallback);
                    }
                }

                public void onShowCustomView(View view, int i, CustomViewCallback customViewCallback) {
                    if (VERSION.SDK_INT < 14) {
                        return;
                    }
                    if (AdvancedWebView.this.mCustomWebChromeClient != null) {
                        AdvancedWebView.this.mCustomWebChromeClient.onShowCustomView(view, i, customViewCallback);
                    } else {
                        super.onShowCustomView(view, i, customViewCallback);
                    }
                }

                public void onHideCustomView() {
                    if (AdvancedWebView.this.mCustomWebChromeClient != null) {
                        AdvancedWebView.this.mCustomWebChromeClient.onHideCustomView();
                    } else {
                        super.onHideCustomView();
                    }
                }

                public boolean onCreateWindow(WebView webView, boolean z, boolean z2, Message message) {
                    if (AdvancedWebView.this.mCustomWebChromeClient != null) {
                        return AdvancedWebView.this.mCustomWebChromeClient.onCreateWindow(webView, z, z2, message);
                    }
                    return super.onCreateWindow(webView, z, z2, message);
                }

                public void onRequestFocus(WebView webView) {
                    if (AdvancedWebView.this.mCustomWebChromeClient != null) {
                        AdvancedWebView.this.mCustomWebChromeClient.onRequestFocus(webView);
                    } else {
                        super.onRequestFocus(webView);
                    }
                }

                public void onCloseWindow(WebView webView) {
                    if (AdvancedWebView.this.mCustomWebChromeClient != null) {
                        AdvancedWebView.this.mCustomWebChromeClient.onCloseWindow(webView);
                    } else {
                        super.onCloseWindow(webView);
                    }
                }

                public boolean onJsAlert(WebView webView, String str, String str2, JsResult jsResult) {
                    if (AdvancedWebView.this.mCustomWebChromeClient != null) {
                        return AdvancedWebView.this.mCustomWebChromeClient.onJsAlert(webView, str, str2, jsResult);
                    }
                    return super.onJsAlert(webView, str, str2, jsResult);
                }

                public boolean onJsConfirm(WebView webView, String str, String str2, JsResult jsResult) {
                    if (AdvancedWebView.this.mCustomWebChromeClient != null) {
                        return AdvancedWebView.this.mCustomWebChromeClient.onJsConfirm(webView, str, str2, jsResult);
                    }
                    return super.onJsConfirm(webView, str, str2, jsResult);
                }

                public boolean onJsPrompt(WebView webView, String str, String str2, String str3, JsPromptResult jsPromptResult) {
                    if (AdvancedWebView.this.mCustomWebChromeClient != null) {
                        return AdvancedWebView.this.mCustomWebChromeClient.onJsPrompt(webView, str, str2, str3, jsPromptResult);
                    }
                    return super.onJsPrompt(webView, str, str2, str3, jsPromptResult);
                }

                public boolean onJsBeforeUnload(WebView webView, String str, String str2, JsResult jsResult) {
                    if (AdvancedWebView.this.mCustomWebChromeClient != null) {
                        return AdvancedWebView.this.mCustomWebChromeClient.onJsBeforeUnload(webView, str, str2, jsResult);
                    }
                    return super.onJsBeforeUnload(webView, str, str2, jsResult);
                }

                public void onGeolocationPermissionsShowPrompt(String str, Callback callback) {
                    if (AdvancedWebView.this.mGeolocationEnabled) {
                        callback.invoke(str, true, false);
                    } else if (AdvancedWebView.this.mCustomWebChromeClient != null) {
                        AdvancedWebView.this.mCustomWebChromeClient.onGeolocationPermissionsShowPrompt(str, callback);
                    } else {
                        super.onGeolocationPermissionsShowPrompt(str, callback);
                    }
                }

                public void onGeolocationPermissionsHidePrompt() {
                    if (AdvancedWebView.this.mCustomWebChromeClient != null) {
                        AdvancedWebView.this.mCustomWebChromeClient.onGeolocationPermissionsHidePrompt();
                    } else {
                        super.onGeolocationPermissionsHidePrompt();
                    }
                }

                public void onPermissionRequest(PermissionRequest permissionRequest) {
                    if (VERSION.SDK_INT < 21) {
                        return;
                    }
                    if (AdvancedWebView.this.mCustomWebChromeClient != null) {
                        AdvancedWebView.this.mCustomWebChromeClient.onPermissionRequest(permissionRequest);
                    } else {
                        super.onPermissionRequest(permissionRequest);
                    }
                }

                public void onPermissionRequestCanceled(PermissionRequest permissionRequest) {
                    if (VERSION.SDK_INT < 21) {
                        return;
                    }
                    if (AdvancedWebView.this.mCustomWebChromeClient != null) {
                        AdvancedWebView.this.mCustomWebChromeClient.onPermissionRequestCanceled(permissionRequest);
                    } else {
                        super.onPermissionRequestCanceled(permissionRequest);
                    }
                }

                public boolean onJsTimeout() {
                    if (AdvancedWebView.this.mCustomWebChromeClient != null) {
                        return AdvancedWebView.this.mCustomWebChromeClient.onJsTimeout();
                    }
                    return super.onJsTimeout();
                }

                public void onConsoleMessage(String str, int i, String str2) {
                    if (AdvancedWebView.this.mCustomWebChromeClient != null) {
                        AdvancedWebView.this.mCustomWebChromeClient.onConsoleMessage(str, i, str2);
                    } else {
                        super.onConsoleMessage(str, i, str2);
                    }
                }

                public boolean onConsoleMessage(ConsoleMessage consoleMessage) {
                    if (AdvancedWebView.this.mCustomWebChromeClient != null) {
                        return AdvancedWebView.this.mCustomWebChromeClient.onConsoleMessage(consoleMessage);
                    }
                    return super.onConsoleMessage(consoleMessage);
                }

                public Bitmap getDefaultVideoPoster() {
                    if (AdvancedWebView.this.mCustomWebChromeClient != null) {
                        return AdvancedWebView.this.mCustomWebChromeClient.getDefaultVideoPoster();
                    }
                    return super.getDefaultVideoPoster();
                }

                public View getVideoLoadingProgressView() {
                    if (AdvancedWebView.this.mCustomWebChromeClient != null) {
                        return AdvancedWebView.this.mCustomWebChromeClient.getVideoLoadingProgressView();
                    }
                    return super.getVideoLoadingProgressView();
                }

                public void getVisitedHistory(ValueCallback<String[]> valueCallback) {
                    if (AdvancedWebView.this.mCustomWebChromeClient != null) {
                        AdvancedWebView.this.mCustomWebChromeClient.getVisitedHistory(valueCallback);
                    } else {
                        super.getVisitedHistory(valueCallback);
                    }
                }

                public void onExceededDatabaseQuota(String str, String str2, long j, long j2, long j3, QuotaUpdater quotaUpdater) {
                    if (AdvancedWebView.this.mCustomWebChromeClient != null) {
                        AdvancedWebView.this.mCustomWebChromeClient.onExceededDatabaseQuota(str, str2, j, j2, j3, quotaUpdater);
                    } else {
                        super.onExceededDatabaseQuota(str, str2, j, j2, j3, quotaUpdater);
                    }
                }

                public void onReachedMaxAppCacheSize(long j, long j2, QuotaUpdater quotaUpdater) {
                    if (AdvancedWebView.this.mCustomWebChromeClient != null) {
                        AdvancedWebView.this.mCustomWebChromeClient.onReachedMaxAppCacheSize(j, j2, quotaUpdater);
                    } else {
                        super.onReachedMaxAppCacheSize(j, j2, quotaUpdater);
                    }
                }
            });
            setDownloadListener(new DownloadListener() {
                public void onDownloadStart(String str, String str2, String str3, String str4, long j) {
                    String guessFileName = URLUtil.guessFileName(str, str3, str4);
                    if (AdvancedWebView.this.mListener != null) {
                        AdvancedWebView.this.mListener.onDownloadRequested(str, guessFileName, str4, j, str3, str2);
                    }
                }
            });
        }
    }

    public void loadUrl(String str, Map<String, String> map) {
        if (map == null) {
            map = this.mHttpHeaders;
        } else if (this.mHttpHeaders.size() > 0) {
            map.putAll(this.mHttpHeaders);
        }
        super.loadUrl(str, map);
    }

    public void loadUrl(String str) {
        if (this.mHttpHeaders.size() > 0) {
            super.loadUrl(str, this.mHttpHeaders);
        } else {
            super.loadUrl(str);
        }
    }

    public void loadUrl(String str, boolean z) {
        if (z) {
            str = makeUrlUnique(str);
        }
        loadUrl(str);
    }

    public void loadUrl(String str, boolean z, Map<String, String> map) {
        if (z) {
            str = makeUrlUnique(str);
        }
        loadUrl(str, map);
    }

    protected static String makeUrlUnique(String str) {
        StringBuilder sb = new StringBuilder();
        sb.append(str);
        if (str.contains("?")) {
            sb.append(Typography.amp);
        } else {
            if (str.lastIndexOf(47) <= 7) {
                sb.append(JsonPointer.SEPARATOR);
            }
            sb.append('?');
        }
        sb.append(System.currentTimeMillis());
        sb.append(SignatureVisitor.INSTANCEOF);
        sb.append(1);
        return sb.toString();
    }

    public boolean isPermittedUrl(String str) {
        if (this.mPermittedHostnames.size() == 0) {
            return true;
        }
        Uri parse = Uri.parse(str);
        String host = parse.getHost();
        if (host == null || !host.matches("^[a-zA-Z0-9._!~*')(;:&=+$,%\\[\\]-]*$")) {
            return false;
        }
        String userInfo = parse.getUserInfo();
        if (userInfo != null && !userInfo.matches("^[a-zA-Z0-9._!~*')(;:&=+$,%-]*$")) {
            return false;
        }
        for (String str2 : this.mPermittedHostnames) {
            if (!host.equals(str2)) {
                StringBuilder sb = new StringBuilder();
                sb.append(".");
                sb.append(str2);
                if (host.endsWith(sb.toString())) {
                }
            }
            return true;
        }
        return false;
    }

    /* access modifiers changed from: protected */
    public boolean isHostnameAllowed(String str) {
        return isPermittedUrl(str);
    }

    /* access modifiers changed from: protected */
    public void setLastError() {
        this.mLastError = System.currentTimeMillis();
    }

    /* access modifiers changed from: protected */
    public boolean hasError() {
        return this.mLastError + 500 >= System.currentTimeMillis();
    }

    protected static String getLanguageIso3() {
        try {
            return Locale.getDefault().getISO3Language().toLowerCase(Locale.US);
        } catch (MissingResourceException unused) {
            return LANGUAGE_DEFAULT_ISO3;
        }
    }

    /* access modifiers changed from: protected */
    public String getFileUploadPromptLabel() {
        try {
            if (this.mLanguageIso3.equals("zho")) {
                return decodeBase64("6YCJ5oup5LiA5Liq5paH5Lu2");
            }
            if (this.mLanguageIso3.equals("spa")) {
                return decodeBase64("RWxpamEgdW4gYXJjaGl2bw==");
            }
            if (this.mLanguageIso3.equals("hin")) {
                return decodeBase64("4KSP4KSVIOCkq+CkvOCkvuCkh+CksiDgpJrgpYHgpKjgpYfgpII=");
            }
            if (this.mLanguageIso3.equals("ben")) {
                return decodeBase64("4KaP4KaV4Kaf4Ka/IOCmq+CmvuCmh+CmsiDgpqjgpr/gprDgp43gpqzgpr7gpprgpqg=");
            }
            if (this.mLanguageIso3.equals("ara")) {
                return decodeBase64("2KfYrtiq2YrYp9ixINmF2YTZgSDZiNin2K3Yrw==");
            }
            if (this.mLanguageIso3.equals("por")) {
                return decodeBase64("RXNjb2xoYSB1bSBhcnF1aXZv");
            }
            if (this.mLanguageIso3.equals("rus")) {
                return decodeBase64("0JLRi9Cx0LXRgNC40YLQtSDQvtC00LjQvSDRhNCw0LnQuw==");
            }
            if (this.mLanguageIso3.equals("jpn")) {
                return decodeBase64("MeODleOCoeOCpOODq+OCkumBuOaKnuOBl+OBpuOBj+OBoOOBleOBhA==");
            }
            if (this.mLanguageIso3.equals("pan")) {
                return decodeBase64("4KiH4Kmx4KiVIOCoq+CovuCoh+CosiDgqJrgqYHgqKPgqYs=");
            }
            if (this.mLanguageIso3.equals("deu")) {
                return decodeBase64("V8OkaGxlIGVpbmUgRGF0ZWk=");
            }
            if (this.mLanguageIso3.equals("jav")) {
                return decodeBase64("UGlsaWggc2lqaSBiZXJrYXM=");
            }
            if (this.mLanguageIso3.equals("msa")) {
                return decodeBase64("UGlsaWggc2F0dSBmYWls");
            }
            if (this.mLanguageIso3.equals("tel")) {
                return decodeBase64("4LCS4LCVIOCwq+CxhuCxluCwsuCxjeCwqOCxgSDgsI7gsILgsJrgsYHgsJXgsYvgsILgsKHgsL8=");
            }
            if (this.mLanguageIso3.equals("vie")) {
                return decodeBase64("Q2jhu41uIG3hu5l0IHThuq1wIHRpbg==");
            }
            if (this.mLanguageIso3.equals("kor")) {
                return decodeBase64("7ZWY64KY7J2YIO2MjOydvOydhCDshKDtg50=");
            }
            if (this.mLanguageIso3.equals("fra")) {
                return decodeBase64("Q2hvaXNpc3NleiB1biBmaWNoaWVy");
            }
            if (this.mLanguageIso3.equals("mar")) {
                return decodeBase64("4KSr4KS+4KSH4KSyIOCkqOCkv+CkteCkoeCkvg==");
            }
            if (this.mLanguageIso3.equals("tam")) {
                return decodeBase64("4K6S4K6w4K+BIOCuleCvh+CuvuCuquCvjeCuquCviCDgrqTgr4fgrrDgr43grrXgr4E=");
            }
            if (this.mLanguageIso3.equals("urd")) {
                return decodeBase64("2KfbjNqpINmB2KfYptmEINmF24zauiDYs9uSINin2YbYqtiu2KfYqCDaqdix24zaug==");
            }
            if (this.mLanguageIso3.equals("fas")) {
                return decodeBase64("2LHYpyDYp9mG2KrYrtin2Kgg2qnZhtuM2K8g24zaqSDZgdin24zZhA==");
            }
            if (this.mLanguageIso3.equals("tur")) {
                return decodeBase64("QmlyIGRvc3lhIHNlw6dpbg==");
            }
            if (this.mLanguageIso3.equals("ita")) {
                return decodeBase64("U2NlZ2xpIHVuIGZpbGU=");
            }
            if (this.mLanguageIso3.equals("tha")) {
                return decodeBase64("4LmA4Lil4Li34Lit4LiB4LmE4Lif4Lil4LmM4Lir4LiZ4Li24LmI4LiH");
            }
            if (this.mLanguageIso3.equals("guj")) {
                return decodeBase64("4KqP4KqVIOCqq+CqvuCqh+CqsuCqqOCrhyDgqqrgqrjgqoLgqqY=");
            }
            return "Choose a file";
        } catch (Exception unused) {
        }
    }

    protected static String decodeBase64(String str) throws IllegalArgumentException, UnsupportedEncodingException {
        return new String(Base64.decode(str, 0), "UTF-8");
    }

    /* access modifiers changed from: protected */
    public void openFileInput(ValueCallback<Uri> valueCallback, ValueCallback<Uri[]> valueCallback2, boolean z) {
        ValueCallback<Uri> valueCallback3 = this.mFileUploadCallbackFirst;
        if (valueCallback3 != null) {
            valueCallback3.onReceiveValue(null);
        }
        this.mFileUploadCallbackFirst = valueCallback;
        ValueCallback<Uri[]> valueCallback4 = this.mFileUploadCallbackSecond;
        if (valueCallback4 != null) {
            valueCallback4.onReceiveValue(null);
        }
        this.mFileUploadCallbackSecond = valueCallback2;
        Intent intent = new Intent("android.intent.action.GET_CONTENT");
        intent.addCategory("android.intent.category.OPENABLE");
        if (z && VERSION.SDK_INT >= 18) {
            intent.putExtra("android.intent.extra.ALLOW_MULTIPLE", true);
        }
        intent.setType(this.mUploadableFileTypes);
        WeakReference<Fragment> weakReference = this.mFragment;
        if (weakReference == null || weakReference.get() == null || VERSION.SDK_INT < 11) {
            WeakReference<Activity> weakReference2 = this.mActivity;
            if (weakReference2 != null && weakReference2.get() != null) {
                ((Activity) this.mActivity.get()).startActivityForResult(Intent.createChooser(intent, getFileUploadPromptLabel()), this.mRequestCodeFilePicker);
                return;
            }
            return;
        }
        ((Fragment) this.mFragment.get()).startActivityForResult(Intent.createChooser(intent, getFileUploadPromptLabel()), this.mRequestCodeFilePicker);
    }

    public static boolean isFileUploadAvailable() {
        return isFileUploadAvailable(false);
    }

    public static boolean isFileUploadAvailable(boolean z) {
        if (VERSION.SDK_INT != 19) {
            return true;
        }
        String str = VERSION.RELEASE == null ? "" : VERSION.RELEASE;
        if (z || (!str.startsWith("4.4.3") && !str.startsWith("4.4.4"))) {
            return false;
        }
        return true;
    }

    /* JADX WARNING: Failed to process nested try/catch */
    /* JADX WARNING: Missing exception handler attribute for start block: B:8:0x002e */
    /* JADX WARNING: Removed duplicated region for block: B:11:0x0032 A[Catch:{ IllegalArgumentException -> 0x0039 }] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static boolean handleDownload(android.content.Context r4, java.lang.String r5, java.lang.String r6) {
        /*
            int r0 = android.os.Build.VERSION.SDK_INT
            r1 = 9
            if (r0 < r1) goto L_0x003f
            android.app.DownloadManager$Request r0 = new android.app.DownloadManager$Request
            android.net.Uri r5 = android.net.Uri.parse(r5)
            r0.<init>(r5)
            int r5 = android.os.Build.VERSION.SDK_INT
            r1 = 1
            r2 = 11
            if (r5 < r2) goto L_0x001c
            r0.allowScanningByMediaScanner()
            r0.setNotificationVisibility(r1)
        L_0x001c:
            java.lang.String r5 = android.os.Environment.DIRECTORY_DOWNLOADS
            r0.setDestinationInExternalPublicDir(r5, r6)
            java.lang.String r5 = "download"
            java.lang.Object r5 = r4.getSystemService(r5)
            android.app.DownloadManager r5 = (android.app.DownloadManager) r5
            r6 = 0
            r5.enqueue(r0)     // Catch:{ SecurityException -> 0x002e }
            goto L_0x0038
        L_0x002e:
            int r3 = android.os.Build.VERSION.SDK_INT     // Catch:{ IllegalArgumentException -> 0x0039 }
            if (r3 < r2) goto L_0x0035
            r0.setNotificationVisibility(r6)     // Catch:{ IllegalArgumentException -> 0x0039 }
        L_0x0035:
            r5.enqueue(r0)     // Catch:{ IllegalArgumentException -> 0x0039 }
        L_0x0038:
            return r1
        L_0x0039:
            java.lang.String r5 = "com.android.providers.downloads"
            openAppSettings(r4, r5)
            return r6
        L_0x003f:
            java.lang.RuntimeException r4 = new java.lang.RuntimeException
            java.lang.String r5 = "Method requires API level 9 or above"
            r4.<init>(r5)
            throw r4
        */
        throw new UnsupportedOperationException("Method not decompiled: p099im.delight.android.webview.AdvancedWebView.handleDownload(android.content.Context, java.lang.String, java.lang.String):boolean");
    }

    private static boolean openAppSettings(Context context, String str) {
        if (VERSION.SDK_INT >= 9) {
            try {
                Intent intent = new Intent("android.settings.APPLICATION_DETAILS_SETTINGS");
                StringBuilder sb = new StringBuilder();
                sb.append("package:");
                sb.append(str);
                intent.setData(Uri.parse(sb.toString()));
                intent.setFlags(268435456);
                context.startActivity(intent);
                return true;
            } catch (Exception unused) {
                return false;
            }
        } else {
            throw new RuntimeException("Method requires API level 9 or above");
        }
    }
}
