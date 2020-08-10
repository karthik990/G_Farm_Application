package com.startapp.android.publish.common.metaData;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Build.VERSION;
import com.google.android.exoplayer2.extractor.p040ts.PsExtractor;
import com.startapp.android.publish.adsCommon.AdsConstants;
import com.startapp.android.publish.adsCommon.C5051k;
import com.startapp.android.publish.adsCommon.Utils.C4934a;
import com.startapp.android.publish.adsCommon.Utils.C4946i;
import com.startapp.android.publish.adsCommon.p082f.C5011a;
import com.startapp.android.publish.adsCommon.p082f.C5015d;
import com.startapp.android.publish.adsCommon.p082f.C5017f;
import com.startapp.android.publish.common.metaData.MetaDataRequest.C5109a;
import com.startapp.android.publish.common.model.AdPreferences;
import com.startapp.android.publish.common.model.AdPreferences.Placement;
import com.startapp.common.C5134a;
import com.startapp.common.C5134a.C5137a;
import com.startapp.common.Constants;
import com.startapp.common.p042c.C2362f;
import com.startapp.common.p042c.C5180b;
import com.startapp.common.p092a.C5146c;
import com.startapp.common.p092a.C5152e;
import com.startapp.common.p092a.C5155g;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import org.apache.http.HttpHost;

/* compiled from: StartAppSDK */
public class MetaData implements Serializable {
    public static final String DEFAULT_AD_PLATFORM_HOST = new String("https://req.startappservice.com/1.5/");
    public static final boolean DEFAULT_ALWAYS_SEND_TOKEN = true;
    public static final String DEFAULT_ASSETS_BASE_URL_SECURED = "";
    public static final boolean DEFAULT_BT_ENABLED = false;
    public static final boolean DEFAULT_COMPRESSION_ENABLED = false;
    public static final boolean DEFAULT_INAPPBROWSER = true;
    public static final Set<String> DEFAULT_INSTALLERS_LIST = new HashSet(Arrays.asList(new String[]{Constants.f3532a}));
    public static final Set<Integer> DEFAULT_INVALID_NETWORK_CODES_INFO_EVENTS = new HashSet(Arrays.asList(new Integer[]{Integer.valueOf(204)}));
    public static final long DEFAULT_LAST_KNOWN_LOCATION_THRESHOLD = 30;
    public static final String DEFAULT_LOCATION_SOURCE = "API";
    public static final String DEFAULT_METADATA_HOST = new String("https://init.startappservice.com/1.5/");
    public static final boolean DEFAULT_OM_SDK_STATE = false;
    public static final boolean DEFAULT_PERIODIC_INFOEVENT_ENABLED = false;
    public static final int DEFAULT_PERIODIC_INFOEVENT_INTERVAL = 360;
    public static final int[] DEFAULT_PERIODIC_INFOEVENT_INTERVALS = {60, 60, PsExtractor.VIDEO_STREAM_MASK};
    public static final boolean DEFAULT_PERIODIC_INFOEVENT_ON_RUN_TIME = false;
    public static final boolean DEFAULT_PERIODIC_METADATA_ENABLED = false;
    public static final int DEFAULT_PERIODIC_METADATA_INTERVAL = 360;
    public static final Set<String> DEFAULT_PRE_INSTALLED_PACKAGES = new HashSet(Arrays.asList(new String[]{new String("com.facebook.katana"), new String("com.yandex.browser")}));
    public static final String DEFAULT_PROFILE_ID = null;
    public static final int DEFAULT_SESSION_MAX_BACKGROUND_TIME = 1800;
    public static final boolean DEFAULT_SIMPLE_TOKEN_ENABLED = true;
    public static final int DEFAULT_STOP_AUTO_LOAD_AMOUNT = 3;
    public static final int DEFAULT_STOP_AUTO_LOAD_PRE_CAHE_AMOUNT = 3;
    public static final boolean DEFAULT_WF_SCAN_ENABLED = false;
    public static final String KEY_METADATA = "metaData";
    private static transient MetaData instance = new MetaData();
    private static transient Object lock = new Object();
    private static final long serialVersionUID = 1;
    private static transient C5113c task;
    private long IABDisplayImpressionDelayInSeconds = 1;
    private long IABVideoImpressionDelayInSeconds = 2;
    @C2362f(mo20785a = true)
    private C5122h SimpleToken = new C5122h();
    private boolean SupportIABViewability = true;
    private String adPlatformBannerHostSecured;
    public String adPlatformHostSecured = DEFAULT_AD_PLATFORM_HOST;
    private String adPlatformNativeHostSecured;
    private String adPlatformOverlayHostSecured;
    private String adPlatformReturnHostSecured;
    private String adPlatformSplashHostSecured;
    private boolean alwaysSendToken = true;
    @C2362f(mo20785a = true)
    public C5011a analytics = new C5011a();
    private String assetsBaseUrlSecured = "";
    @C2362f(mo20785a = true)
    private C5112b btConfig = new C5112b();
    private boolean btEnabled = false;
    private boolean chromeCustomeTabsExternal = true;
    private boolean chromeCustomeTabsInternal = true;
    private boolean compressionEnabled = false;
    private boolean dns = false;
    private boolean inAppBrowser = true;
    @C2362f(mo20786b = C5108b.class)
    private C5108b inAppBrowserPreLoad;
    @C2362f(mo20786b = HashSet.class)
    private Set<String> installersList = DEFAULT_INSTALLERS_LIST;
    @C2362f(mo20786b = HashSet.class)
    private Set<Integer> invalidForRetry = new HashSet();
    @C2362f(mo20786b = HashSet.class)
    private Set<Integer> invalidNetworkCodesInfoEvents = DEFAULT_INVALID_NETWORK_CODES_INFO_EVENTS;
    private boolean isToken1Mandatory = true;
    private transient boolean loading = false;
    @C2362f(mo20785a = true)
    private LocationConfig location = new LocationConfig();
    public String metaDataHostSecured = DEFAULT_METADATA_HOST;
    private transient List<C5116d> metaDataListeners = new ArrayList();
    private String metadataUpdateVersion = AdsConstants.f3024h;
    private boolean omSdkEnabled = false;
    private int[] periodicEventIntMin = DEFAULT_PERIODIC_INFOEVENT_INTERVALS;
    private boolean periodicInfoEventEnabled = false;
    private int periodicInfoEventIntervalInMinutes = 360;
    private boolean periodicInfoEventOnRunTimeEnabled = false;
    private boolean periodicMetaDataEnabled = false;
    private int periodicMetaDataIntervalInMinutes = 360;
    @C2362f(mo20786b = HashSet.class)
    private Set<String> preInstalledPackages = DEFAULT_PRE_INSTALLED_PACKAGES;
    private String profileId = DEFAULT_PROFILE_ID;
    private transient boolean ready = false;
    @C2362f(mo20785a = true)
    private C5121g sensorsConfig = new C5121g();
    private int sessionMaxBackgroundTime = DEFAULT_SESSION_MAX_BACKGROUND_TIME;
    private boolean simpleToken2 = true;
    private int stopAutoLoadAmount = 3;
    private int stopAutoLoadPreCacheAmount = 3;
    private boolean trueNetEnabled = false;
    private long userAgentDelayInSeconds = 5;
    private boolean userAgentEnabled = true;
    private boolean webViewSecured = true;
    private boolean wfScanEnabled = false;

    /* renamed from: com.startapp.android.publish.common.metaData.MetaData$1 */
    /* compiled from: StartAppSDK */
    static /* synthetic */ class C51061 {

        /* renamed from: a */
        static final /* synthetic */ int[] f3443a = new int[Placement.values().length];

        /* JADX WARNING: Can't wrap try/catch for region: R(12:0|1|2|3|4|5|6|7|8|9|10|12) */
        /* JADX WARNING: Code restructure failed: missing block: B:13:?, code lost:
            return;
         */
        /* JADX WARNING: Failed to process nested try/catch */
        /* JADX WARNING: Missing exception handler attribute for start block: B:3:0x0014 */
        /* JADX WARNING: Missing exception handler attribute for start block: B:5:0x001f */
        /* JADX WARNING: Missing exception handler attribute for start block: B:7:0x002a */
        /* JADX WARNING: Missing exception handler attribute for start block: B:9:0x0035 */
        static {
            /*
                com.startapp.android.publish.common.model.AdPreferences$Placement[] r0 = com.startapp.android.publish.common.model.AdPreferences.Placement.values()
                int r0 = r0.length
                int[] r0 = new int[r0]
                f3443a = r0
                int[] r0 = f3443a     // Catch:{ NoSuchFieldError -> 0x0014 }
                com.startapp.android.publish.common.model.AdPreferences$Placement r1 = com.startapp.android.publish.common.model.AdPreferences.Placement.INAPP_BANNER     // Catch:{ NoSuchFieldError -> 0x0014 }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x0014 }
                r2 = 1
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x0014 }
            L_0x0014:
                int[] r0 = f3443a     // Catch:{ NoSuchFieldError -> 0x001f }
                com.startapp.android.publish.common.model.AdPreferences$Placement r1 = com.startapp.android.publish.common.model.AdPreferences.Placement.INAPP_OVERLAY     // Catch:{ NoSuchFieldError -> 0x001f }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x001f }
                r2 = 2
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x001f }
            L_0x001f:
                int[] r0 = f3443a     // Catch:{ NoSuchFieldError -> 0x002a }
                com.startapp.android.publish.common.model.AdPreferences$Placement r1 = com.startapp.android.publish.common.model.AdPreferences.Placement.INAPP_NATIVE     // Catch:{ NoSuchFieldError -> 0x002a }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x002a }
                r2 = 3
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x002a }
            L_0x002a:
                int[] r0 = f3443a     // Catch:{ NoSuchFieldError -> 0x0035 }
                com.startapp.android.publish.common.model.AdPreferences$Placement r1 = com.startapp.android.publish.common.model.AdPreferences.Placement.INAPP_RETURN     // Catch:{ NoSuchFieldError -> 0x0035 }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x0035 }
                r2 = 4
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x0035 }
            L_0x0035:
                int[] r0 = f3443a     // Catch:{ NoSuchFieldError -> 0x0040 }
                com.startapp.android.publish.common.model.AdPreferences$Placement r1 = com.startapp.android.publish.common.model.AdPreferences.Placement.INAPP_SPLASH     // Catch:{ NoSuchFieldError -> 0x0040 }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x0040 }
                r2 = 5
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x0040 }
            L_0x0040:
                return
            */
            throw new UnsupportedOperationException("Method not decompiled: com.startapp.android.publish.common.metaData.MetaData.C51061.<clinit>():void");
        }
    }

    /* renamed from: com.startapp.android.publish.common.metaData.MetaData$a */
    /* compiled from: StartAppSDK */
    public static class C5107a implements C5137a {

        /* renamed from: a */
        Context f3444a;

        /* renamed from: b */
        String f3445b;

        public C5107a(Context context, String str) {
            this.f3444a = context;
            this.f3445b = str;
        }

        /* renamed from: a */
        public void mo61329a(Bitmap bitmap, int i) {
            if (bitmap != null) {
                C4934a.m2861a(this.f3444a, bitmap, this.f3445b, ".png");
            }
        }
    }

    /* renamed from: com.startapp.android.publish.common.metaData.MetaData$b */
    /* compiled from: StartAppSDK */
    public enum C5108b {
        DISABLED,
        CONTENT,
        FULL
    }

    private MetaData() {
    }

    public C5122h getSimpleTokenConfig() {
        return this.SimpleToken;
    }

    /* access modifiers changed from: protected */
    public void setSimpleTokenConfig(C5122h hVar) {
        this.SimpleToken = hVar;
    }

    public static void init(Context context) {
        MetaData metaData = (MetaData) C5152e.m3786a(context, "StartappMetadata", MetaData.class);
        MetaData metaData2 = new MetaData();
        if (metaData != null) {
            boolean a = C4946i.m2927a(metaData, metaData2);
            if (!metaData.isMetaDataVersionChanged() && a) {
                String str = "";
                C5017f.m3256a(context, C5015d.METADATA_NULL, "MetaData", str, str);
            }
            metaData.initTransientFields();
            instance = metaData;
        } else {
            instance = metaData2;
        }
        getInstance().applyAdPlatformProtocolToHosts();
    }

    public static void update(Context context, MetaData metaData) {
        synchronized (lock) {
            metaData.metaDataListeners = getInstance().metaDataListeners;
            instance = metaData;
            if (Constants.m3707a().booleanValue()) {
                C5155g.m3805a(3, "MetaData received:");
                C5155g.m3805a(3, C5180b.m3909a(metaData));
            }
            getInstance().applyAdPlatformProtocolToHosts();
            metaData.metadataUpdateVersion = AdsConstants.f3024h;
            C5152e.m3799b(context, "StartappMetadata", (Serializable) metaData);
            C5155g.m3805a(3, "MetaData saved:");
            getInstance().loading = false;
            getInstance().ready = true;
            if (getInstance().metaDataListeners != null) {
                ArrayList<C5116d> arrayList = new ArrayList<>(getInstance().metaDataListeners);
                getInstance().metaDataListeners.clear();
                for (C5116d a : arrayList) {
                    a.mo61600a();
                }
            }
            C5051k.m3344b(context, "totalSessions", Integer.valueOf(C5051k.m3337a(context, "totalSessions", Integer.valueOf(0)).intValue() + 1));
            task = null;
        }
    }

    public static void failedLoading() {
        ArrayList<C5116d> arrayList;
        synchronized (lock) {
            if (getInstance().metaDataListeners != null) {
                arrayList = new ArrayList<>(getInstance().metaDataListeners);
                getInstance().metaDataListeners.clear();
            } else {
                arrayList = null;
            }
            getInstance().loading = false;
        }
        if (arrayList != null) {
            for (C5116d b : arrayList) {
                b.mo61601b();
            }
        }
    }

    public static boolean isLoadedFromServer(Context context) {
        return context.getFileStreamPath("StartappMetadata").exists();
    }

    public void loadFromServer(Context context, AdPreferences adPreferences, C5109a aVar, boolean z, C5116d dVar) {
        loadFromServer(context, adPreferences, aVar, z, dVar, false);
    }

    /* JADX WARNING: Code restructure failed: missing block: B:11:0x0018, code lost:
        if (r6 == false) goto L_0x001f;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:12:0x001a, code lost:
        if (r7 == null) goto L_0x001f;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:13:0x001c, code lost:
        r7.mo61600a();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:14:0x001f, code lost:
        return;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:27:0x0053, code lost:
        return;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void loadFromServer(android.content.Context r3, com.startapp.android.publish.common.model.AdPreferences r4, com.startapp.android.publish.common.metaData.MetaDataRequest.C5109a r5, boolean r6, com.startapp.android.publish.common.metaData.C5116d r7, boolean r8) {
        /*
            r2 = this;
            if (r6 != 0) goto L_0x0007
            if (r7 == 0) goto L_0x0007
            r7.mo61600a()
        L_0x0007:
            java.lang.Object r0 = lock
            monitor-enter(r0)
            com.startapp.android.publish.common.metaData.MetaData r1 = getInstance()     // Catch:{ all -> 0x0054 }
            boolean r1 = r1.isReady()     // Catch:{ all -> 0x0054 }
            if (r1 == 0) goto L_0x0020
            if (r8 == 0) goto L_0x0017
            goto L_0x0020
        L_0x0017:
            monitor-exit(r0)     // Catch:{ all -> 0x0054 }
            if (r6 == 0) goto L_0x001f
            if (r7 == 0) goto L_0x001f
            r7.mo61600a()
        L_0x001f:
            return
        L_0x0020:
            com.startapp.android.publish.common.metaData.MetaData r1 = getInstance()     // Catch:{ all -> 0x0054 }
            boolean r1 = r1.isLoading()     // Catch:{ all -> 0x0054 }
            if (r1 == 0) goto L_0x002c
            if (r8 == 0) goto L_0x0047
        L_0x002c:
            r8 = 1
            r2.loading = r8     // Catch:{ all -> 0x0054 }
            r8 = 0
            r2.ready = r8     // Catch:{ all -> 0x0054 }
            com.startapp.android.publish.common.metaData.c r8 = task     // Catch:{ all -> 0x0054 }
            if (r8 == 0) goto L_0x003b
            com.startapp.android.publish.common.metaData.c r8 = task     // Catch:{ all -> 0x0054 }
            r8.mo62646b()     // Catch:{ all -> 0x0054 }
        L_0x003b:
            com.startapp.android.publish.common.metaData.c r8 = new com.startapp.android.publish.common.metaData.c     // Catch:{ all -> 0x0054 }
            r8.<init>(r3, r4, r5)     // Catch:{ all -> 0x0054 }
            task = r8     // Catch:{ all -> 0x0054 }
            com.startapp.android.publish.common.metaData.c r3 = task     // Catch:{ all -> 0x0054 }
            r3.mo62644a()     // Catch:{ all -> 0x0054 }
        L_0x0047:
            if (r6 == 0) goto L_0x0052
            if (r7 == 0) goto L_0x0052
            com.startapp.android.publish.common.metaData.MetaData r3 = getInstance()     // Catch:{ all -> 0x0054 }
            r3.addMetaDataListener(r7)     // Catch:{ all -> 0x0054 }
        L_0x0052:
            monitor-exit(r0)     // Catch:{ all -> 0x0054 }
            return
        L_0x0054:
            r3 = move-exception
            monitor-exit(r0)     // Catch:{ all -> 0x0054 }
            throw r3
        */
        throw new UnsupportedOperationException("Method not decompiled: com.startapp.android.publish.common.metaData.MetaData.loadFromServer(android.content.Context, com.startapp.android.publish.common.model.AdPreferences, com.startapp.android.publish.common.metaData.MetaDataRequest$a, boolean, com.startapp.android.publish.common.metaData.d, boolean):void");
    }

    public void addMetaDataListener(C5116d dVar) {
        synchronized (lock) {
            this.metaDataListeners.add(dVar);
        }
    }

    public void removeMetaDataListener(C5116d dVar) {
        synchronized (lock) {
            this.metaDataListeners.remove(dVar);
        }
    }

    public static Object getLock() {
        return lock;
    }

    public boolean isLoading() {
        return this.loading;
    }

    public boolean isReady() {
        return this.ready;
    }

    public void setReady(boolean z) {
        this.ready = z;
    }

    private boolean isMetaDataVersionChanged() {
        return !AdsConstants.f3024h.equals(this.metadataUpdateVersion);
    }

    public String getAssetsBaseUrl() {
        String str = this.assetsBaseUrlSecured;
        return str != null ? str : "";
    }

    public boolean isPeriodicMetaDataEnabled() {
        return this.periodicMetaDataEnabled;
    }

    public void setPeriodicMetaDataEnabled(boolean z) {
        this.periodicMetaDataEnabled = z;
    }

    public int getPeriodicMetaDataInterval() {
        return this.periodicMetaDataIntervalInMinutes;
    }

    public void setPeriodicMetaDataInterval(int i) {
        this.periodicMetaDataIntervalInMinutes = i;
    }

    public boolean isPeriodicInfoEventEnabled() {
        return this.periodicInfoEventEnabled;
    }

    public boolean isPeriodicInfoEventOnRunTimeEnabled() {
        return this.periodicInfoEventOnRunTimeEnabled;
    }

    public void setPeriodicInfoEventEnabled(boolean z) {
        this.periodicInfoEventEnabled = z;
    }

    public int getPeriodicInfoEventIntervalInMinutes(Context context) {
        int[] iArr = this.periodicEventIntMin;
        if (iArr == null || iArr.length < 3) {
            this.periodicEventIntMin = DEFAULT_PERIODIC_INFOEVENT_INTERVALS;
        }
        if (C5146c.m3760a(context, "android.permission.ACCESS_FINE_LOCATION")) {
            int i = this.periodicEventIntMin[0];
            if (i <= 0) {
                return DEFAULT_PERIODIC_INFOEVENT_INTERVALS[0];
            }
            return i;
        } else if (!C5146c.m3760a(context, "android.permission.ACCESS_COARSE_LOCATION")) {
            return this.periodicEventIntMin[2];
        } else {
            int i2 = this.periodicEventIntMin[1];
            if (i2 <= 0) {
                return DEFAULT_PERIODIC_INFOEVENT_INTERVALS[1];
            }
            return i2;
        }
    }

    public void setPeriodicInfoEventIntervalInMinutes(int i) {
        this.periodicInfoEventIntervalInMinutes = i;
    }

    public Set<Integer> getInvalidForRetry() {
        Set<Integer> set = this.invalidForRetry;
        if (set != null) {
            return set;
        }
        return new HashSet();
    }

    public Set<Integer> getInvalidNetworkCodesInfoEvents() {
        Set<Integer> set = this.invalidNetworkCodesInfoEvents;
        if (set != null) {
            return set;
        }
        return DEFAULT_INVALID_NETWORK_CODES_INFO_EVENTS;
    }

    public String getMetaDataHost() {
        if (AdsConstants.OVERRIDE_HOST != null) {
            return AdsConstants.OVERRIDE_HOST;
        }
        return this.metaDataHostSecured;
    }

    public String getAdPlatformHost() {
        if (AdsConstants.OVERRIDE_HOST != null) {
            return AdsConstants.OVERRIDE_HOST;
        }
        String str = this.adPlatformHostSecured;
        if (str == null) {
            str = DEFAULT_AD_PLATFORM_HOST;
        }
        return str;
    }

    public String getAdPlatformHost(Placement placement) {
        int i = C51061.f3443a[placement.ordinal()];
        if (i == 1) {
            String str = this.adPlatformBannerHostSecured;
            if (str == null) {
                str = getAdPlatformHost();
            }
            return str;
        } else if (i == 2) {
            String str2 = this.adPlatformOverlayHostSecured;
            if (str2 == null) {
                str2 = getAdPlatformHost();
            }
            return str2;
        } else if (i == 3) {
            String str3 = this.adPlatformNativeHostSecured;
            if (str3 == null) {
                str3 = getAdPlatformHost();
            }
            return str3;
        } else if (i == 4) {
            String str4 = this.adPlatformReturnHostSecured;
            if (str4 == null) {
                str4 = getAdPlatformHost();
            }
            return str4;
        } else if (i != 5) {
            return getAdPlatformHost();
        } else {
            String str5 = this.adPlatformSplashHostSecured;
            if (str5 == null) {
                str5 = getAdPlatformHost();
            }
            return str5;
        }
    }

    public String getHostForWebview() {
        return getHostForWebview(getInstance().getAdPlatformHost(), VERSION.SDK_INT, this.webViewSecured);
    }

    public static String getHostForWebview(String str, int i, boolean z) {
        String str2 = (i > 26 || z) ? "https" : HttpHost.DEFAULT_SCHEME_NAME;
        StringBuilder sb = new StringBuilder();
        sb.append(str2);
        sb.append("://");
        if (str.startsWith(sb.toString())) {
            return str;
        }
        int indexOf = str.indexOf(58);
        if (indexOf == -1) {
            return str;
        }
        StringBuilder sb2 = new StringBuilder();
        sb2.append(str2);
        sb2.append(str.substring(indexOf));
        return sb2.toString();
    }

    public long getSessionMaxBackgroundTime() {
        return TimeUnit.SECONDS.toMillis((long) this.sessionMaxBackgroundTime);
    }

    public Set<String> getInstallersList() {
        return this.installersList;
    }

    public void setInstallersList(Set<String> set) {
        this.installersList = set;
    }

    public Set<String> getPreInstalledPackages() {
        Set<String> set = this.preInstalledPackages;
        if (set == null) {
            set = DEFAULT_PRE_INSTALLED_PACKAGES;
        }
        return Collections.unmodifiableSet(set);
    }

    public void setPreInstalledPackages(Set<String> set) {
        this.preInstalledPackages = set;
    }

    public boolean isSimpleToken2() {
        return this.simpleToken2;
    }

    public void setSimpleToken2(boolean z) {
        this.simpleToken2 = z;
    }

    public boolean isAlwaysSendToken() {
        return this.alwaysSendToken;
    }

    public void setAlwaysSendToken(boolean z) {
        this.alwaysSendToken = z;
    }

    public boolean isToken1Mandatory() {
        return this.isToken1Mandatory;
    }

    public boolean isCompressionEnabled() {
        return this.compressionEnabled;
    }

    public void setCompressionEnabled(boolean z) {
        this.compressionEnabled = z;
    }

    public boolean isInAppBrowser() {
        return C4946i.m2923a(256) && this.inAppBrowser;
    }

    public void setInAppBrowser(boolean z) {
        this.inAppBrowser = z;
    }

    public C5108b getInAppBrowserPreLoad() {
        return this.inAppBrowserPreLoad;
    }

    public void setInAppBrowserPreLoad(C5108b bVar) {
        this.inAppBrowserPreLoad = bVar;
    }

    public String getProfileId() {
        return this.profileId;
    }

    public C5011a getAnalyticsConfig() {
        return this.analytics;
    }

    public C5121g getSensorsConfig() {
        return this.sensorsConfig;
    }

    public C5112b getBluetoothConfig() {
        return this.btConfig;
    }

    public LocationConfig getLocationConfig() {
        return this.location;
    }

    public boolean isWfScanEnabled() {
        return this.wfScanEnabled;
    }

    public static MetaData getInstance() {
        return instance;
    }

    public long getIABDisplayImpressionDelayInSeconds() {
        return this.IABDisplayImpressionDelayInSeconds;
    }

    public long getIABVideoImpressionDelayInSeconds() {
        return this.IABVideoImpressionDelayInSeconds;
    }

    public long getUserAgentDelayInSeconds() {
        return this.userAgentDelayInSeconds;
    }

    public boolean isUserAgentEnabled() {
        return this.userAgentEnabled;
    }

    public boolean isSupportIABViewability() {
        return this.SupportIABViewability;
    }

    public void applyAdPlatformProtocolToHosts() {
        this.adPlatformHostSecured = replaceAdProtocol(this.adPlatformHostSecured, DEFAULT_AD_PLATFORM_HOST);
        this.metaDataHostSecured = replaceAdProtocol(this.metaDataHostSecured, DEFAULT_METADATA_HOST);
        this.adPlatformBannerHostSecured = replaceAdProtocol(this.adPlatformBannerHostSecured, null);
        this.adPlatformSplashHostSecured = replaceAdProtocol(this.adPlatformSplashHostSecured, null);
        this.adPlatformReturnHostSecured = replaceAdProtocol(this.adPlatformReturnHostSecured, null);
        this.adPlatformOverlayHostSecured = replaceAdProtocol(this.adPlatformOverlayHostSecured, null);
        this.adPlatformNativeHostSecured = replaceAdProtocol(this.adPlatformNativeHostSecured, null);
    }

    public boolean canShowAd() {
        return !this.dns;
    }

    public int getStopAutoLoadAmount() {
        return this.stopAutoLoadAmount;
    }

    public int getStopAutoLoadPreCacheAmount() {
        return this.stopAutoLoadPreCacheAmount;
    }

    public boolean getTrueNetEnabled() {
        return this.trueNetEnabled;
    }

    public boolean getChromeCustomeTabsInternal() {
        return this.chromeCustomeTabsInternal;
    }

    public boolean getChromeCustomeTabsExternal() {
        return this.chromeCustomeTabsExternal;
    }

    private String replaceAdProtocol(String str, String str2) {
        return str != null ? str.replace("%AdPlatformProtocol%", "1.5") : str2;
    }

    private void initTransientFields() {
        this.loading = false;
        this.ready = false;
        this.metaDataListeners = new ArrayList();
    }

    public static void preCacheResources(Context context, String str) {
        String[] strArr;
        String[] strArr2;
        String[] strArr3;
        if (str != null && !str.equals("")) {
            String str2 = ".png";
            if (!C4934a.m2862a(context, "close_button.png", str2) && !C4946i.m2922a()) {
                StringBuilder sb = new StringBuilder();
                sb.append(str);
                String str3 = "close_button";
                sb.append(str3);
                sb.append(str2);
                new C5134a(sb.toString(), new C5107a(context, str3), 0).mo62837a();
            }
            if (C4946i.m2923a(256)) {
                for (String str4 : AdsConstants.f3027k) {
                    StringBuilder sb2 = new StringBuilder();
                    sb2.append(str4);
                    sb2.append(str2);
                    if (!C4934a.m2862a(context, sb2.toString(), str2)) {
                        StringBuilder sb3 = new StringBuilder();
                        sb3.append(str);
                        sb3.append(str4);
                        sb3.append(str2);
                        new C5134a(sb3.toString(), new C5107a(context, str4), 0).mo62837a();
                    }
                }
            }
            if (C4946i.m2923a(64)) {
                for (String str5 : AdsConstants.f3028l) {
                    StringBuilder sb4 = new StringBuilder();
                    sb4.append(str5);
                    sb4.append(str2);
                    if (!C4934a.m2862a(context, sb4.toString(), str2)) {
                        StringBuilder sb5 = new StringBuilder();
                        sb5.append(str);
                        sb5.append(str5);
                        sb5.append(str2);
                        new C5134a(sb5.toString(), new C5107a(context, str5), 0).mo62837a();
                    }
                }
                if (!C4934a.m2862a(context, "logo.png", str2)) {
                    StringBuilder sb6 = new StringBuilder();
                    sb6.append(str);
                    String str6 = "logo";
                    sb6.append(str6);
                    sb6.append(str2);
                    new C5134a(sb6.toString(), new C5107a(context, str6), 0).mo62837a();
                }
            } else if (C4946i.m2923a(32)) {
                for (String str7 : AdsConstants.f3028l) {
                    StringBuilder sb7 = new StringBuilder();
                    sb7.append(str7);
                    sb7.append(str2);
                    if (!C4934a.m2862a(context, sb7.toString(), str2)) {
                        StringBuilder sb8 = new StringBuilder();
                        sb8.append(str);
                        sb8.append(str7);
                        sb8.append(str2);
                        new C5134a(sb8.toString(), new C5107a(context, str7), 0).mo62837a();
                    }
                }
            }
        }
    }

    public boolean isOmsdkEnabled() {
        return this.omSdkEnabled;
    }
}
