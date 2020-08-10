package com.startapp.android.publish.adsCommon;

import android.content.Context;
import androidx.core.view.ViewCompat;
import com.startapp.android.publish.adsCommon.Utils.C4946i;
import com.startapp.android.publish.adsCommon.p077a.C4957e;
import com.startapp.android.publish.adsCommon.p082f.C5015d;
import com.startapp.android.publish.adsCommon.p082f.C5017f;
import com.startapp.android.publish.common.metaData.MetaDataStyle;
import com.startapp.common.p042c.C2362f;
import com.startapp.common.p092a.C5152e;
import java.io.Serializable;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/* renamed from: com.startapp.android.publish.adsCommon.b */
/* compiled from: StartAppSDK */
public class C4983b implements Serializable {

    /* renamed from: a */
    public static final Integer f3148a = Integer.valueOf(18);

    /* renamed from: b */
    public static final Integer f3149b;

    /* renamed from: c */
    public static final Set<String> f3150c = new HashSet(Arrays.asList(new String[]{"BOLD"}));

    /* renamed from: d */
    public static final Integer f3151d = Integer.valueOf(ViewCompat.MEASURED_STATE_MASK);

    /* renamed from: e */
    public static final Integer f3152e = Integer.valueOf(-14803426);

    /* renamed from: f */
    public static final Integer f3153f;

    /* renamed from: g */
    private static transient Object f3154g = new Object();

    /* renamed from: h */
    private static transient C4983b f3155h = new C4983b();
    private static final long serialVersionUID = 1;
    private String acMetadataUpdateVersion = AdsConstants.f3024h;
    @C2362f(mo20785a = true)
    private C4957e adRules;
    private boolean appPresence;
    private boolean autoInterstitialEnabled;
    private Integer backgroundGradientBottom;
    private Integer backgroundGradientTop;
    private int defaultActivitiesBetweenAds;
    private int defaultSecondsBetweenAds;
    private boolean disableInAppStore;
    private boolean disableReturnAd;
    private boolean disableSplashAd;
    private boolean disableTwoClicks;
    private boolean enableForceExternalBrowser;
    private boolean enableSmartRedirect;
    private boolean enforceForeground;
    private int forceExternalBrowserDaysInterval;
    private Integer fullpageOfferWallProbability;
    private Integer fullpageOverlayProbability;
    private Integer homeProbability3D;
    private Integer itemDescriptionTextColor;
    @C2362f(mo20786b = HashSet.class)
    private Set<String> itemDescriptionTextDecoration;
    private Integer itemDescriptionTextSize;
    private Integer itemGradientBottom;
    private Integer itemGradientTop;
    private Integer itemTitleTextColor;
    @C2362f(mo20786b = HashSet.class)
    private Set<String> itemTitleTextDecoration;
    private Integer itemTitleTextSize;
    private Integer maxAds;
    private Integer poweredByBackgroundColor;
    private Integer poweredByTextColor;
    private Integer probability3D;
    private long returnAdMinBackgroundTime;
    private long smartRedirectLoadedTimeout;
    private int smartRedirectTimeout;
    @C2362f(mo20786b = HashMap.class, mo20787c = MetaDataStyle.class)
    private HashMap<String, MetaDataStyle> templates;
    private Integer titleBackgroundColor;
    private String titleContent;
    private Integer titleLineColor;
    private Integer titleTextColor;
    @C2362f(mo20786b = HashSet.class)
    private Set<String> titleTextDecoration;
    private Integer titleTextSize;
    @C2362f(mo20785a = true)
    private C5067n video;

    static {
        Integer valueOf = Integer.valueOf(-1);
        f3149b = valueOf;
        f3153f = valueOf;
    }

    public C4983b() {
        Integer valueOf = Integer.valueOf(0);
        this.probability3D = valueOf;
        this.homeProbability3D = Integer.valueOf(80);
        this.fullpageOfferWallProbability = Integer.valueOf(100);
        this.fullpageOverlayProbability = valueOf;
        Integer valueOf2 = Integer.valueOf(-14606047);
        this.backgroundGradientTop = valueOf2;
        this.backgroundGradientBottom = valueOf2;
        this.maxAds = Integer.valueOf(10);
        this.titleBackgroundColor = Integer.valueOf(-14803426);
        this.titleContent = "Recommended for you";
        this.titleTextSize = f3148a;
        this.titleTextColor = f3149b;
        this.titleTextDecoration = f3150c;
        this.titleLineColor = f3151d;
        this.itemGradientTop = Integer.valueOf(MetaDataStyle.DEFAULT_ITEM_TOP);
        this.itemGradientBottom = Integer.valueOf(MetaDataStyle.DEFAULT_ITEM_BOTTOM);
        this.itemTitleTextSize = MetaDataStyle.DEFAULT_ITEM_TITLE_TEXT_SIZE;
        this.itemTitleTextColor = MetaDataStyle.DEFAULT_ITEM_TITLE_TEXT_COLOR;
        this.itemTitleTextDecoration = MetaDataStyle.DEFAULT_ITEM_TITLE_TEXT_DECORATION;
        this.itemDescriptionTextSize = MetaDataStyle.DEFAULT_ITEM_DESC_TEXT_SIZE;
        this.itemDescriptionTextColor = MetaDataStyle.DEFAULT_ITEM_DESC_TEXT_COLOR;
        this.itemDescriptionTextDecoration = MetaDataStyle.DEFAULT_ITEM_DESC_TEXT_DECORATION;
        this.templates = new HashMap<>();
        this.adRules = new C4957e();
        this.poweredByBackgroundColor = f3152e;
        this.poweredByTextColor = f3153f;
        this.returnAdMinBackgroundTime = 300;
        this.disableReturnAd = false;
        this.disableSplashAd = false;
        this.smartRedirectTimeout = 5;
        this.smartRedirectLoadedTimeout = 1000;
        this.enableSmartRedirect = true;
        this.autoInterstitialEnabled = true;
        this.defaultActivitiesBetweenAds = 1;
        this.defaultSecondsBetweenAds = 0;
        this.disableTwoClicks = false;
        this.appPresence = true;
        this.disableInAppStore = false;
        this.video = new C5067n();
        this.forceExternalBrowserDaysInterval = 7;
        this.enableForceExternalBrowser = true;
        this.enforceForeground = false;
    }

    /* renamed from: a */
    public static C4983b m3032a() {
        return f3155h;
    }

    /* renamed from: a */
    public static void m3033a(Context context) {
        C4983b bVar = (C4983b) C5152e.m3786a(context, "StartappAdsMetadata", C4983b.class);
        C4983b bVar2 = new C4983b();
        if (bVar != null) {
            boolean a = C4946i.m2927a(bVar, bVar2);
            if (!bVar.m3030O() && a) {
                String str = "";
                C5017f.m3256a(context, C5015d.METADATA_NULL, "AdsCommonMetaData", str, str);
            }
            bVar.m3031P();
            f3155h = bVar;
            return;
        }
        f3155h = bVar2;
    }

    /* renamed from: O */
    private boolean m3030O() {
        return !AdsConstants.f3024h.equals(this.acMetadataUpdateVersion);
    }

    /* renamed from: P */
    private void m3031P() {
        this.adRules.mo62063b();
    }

    /* renamed from: b */
    public int mo62162b() {
        return this.fullpageOfferWallProbability.intValue();
    }

    /* renamed from: c */
    public int mo62163c() {
        return this.fullpageOverlayProbability.intValue();
    }

    /* renamed from: d */
    public int mo62164d() {
        return this.probability3D.intValue();
    }

    /* renamed from: e */
    public int mo62165e() {
        return this.backgroundGradientTop.intValue();
    }

    /* renamed from: a */
    public MetaDataStyle mo62161a(String str) {
        return (MetaDataStyle) this.templates.get(str);
    }

    /* renamed from: f */
    public int mo62166f() {
        return this.backgroundGradientBottom.intValue();
    }

    /* renamed from: g */
    public int mo62167g() {
        return this.maxAds.intValue();
    }

    /* renamed from: h */
    public Integer mo62168h() {
        return this.titleBackgroundColor;
    }

    /* renamed from: i */
    public String mo62169i() {
        return this.titleContent;
    }

    /* renamed from: j */
    public Integer mo62170j() {
        return this.titleTextSize;
    }

    /* renamed from: k */
    public Integer mo62171k() {
        return this.titleTextColor;
    }

    /* renamed from: l */
    public Set<String> mo62172l() {
        return this.titleTextDecoration;
    }

    /* renamed from: m */
    public Integer mo62173m() {
        return this.titleLineColor;
    }

    /* renamed from: n */
    public int mo62174n() {
        return this.itemGradientTop.intValue();
    }

    /* renamed from: o */
    public int mo62175o() {
        return this.itemGradientBottom.intValue();
    }

    /* renamed from: p */
    public Integer mo62176p() {
        return this.itemTitleTextSize;
    }

    /* renamed from: q */
    public Integer mo62177q() {
        return this.itemTitleTextColor;
    }

    /* renamed from: r */
    public Set<String> mo62178r() {
        return this.itemTitleTextDecoration;
    }

    /* renamed from: s */
    public Integer mo62179s() {
        return this.itemDescriptionTextSize;
    }

    /* renamed from: t */
    public Integer mo62180t() {
        return this.itemDescriptionTextColor;
    }

    /* renamed from: u */
    public Set<String> mo62181u() {
        return this.itemDescriptionTextDecoration;
    }

    /* renamed from: v */
    public Integer mo62182v() {
        return this.poweredByBackgroundColor;
    }

    /* renamed from: w */
    public Integer mo62183w() {
        return this.poweredByTextColor;
    }

    /* renamed from: x */
    public long mo62184x() {
        return TimeUnit.SECONDS.toMillis(this.returnAdMinBackgroundTime);
    }

    /* renamed from: y */
    public boolean mo62185y() {
        return this.disableReturnAd;
    }

    /* renamed from: z */
    public boolean mo62186z() {
        return this.disableSplashAd;
    }

    /* renamed from: A */
    public long mo62147A() {
        return TimeUnit.SECONDS.toMillis((long) this.smartRedirectTimeout);
    }

    /* renamed from: B */
    public long mo62148B() {
        return this.smartRedirectLoadedTimeout;
    }

    /* renamed from: C */
    public boolean mo62149C() {
        return this.enableSmartRedirect;
    }

    /* renamed from: D */
    public boolean mo62150D() {
        return this.disableTwoClicks;
    }

    /* renamed from: E */
    public boolean mo62151E() {
        return this.appPresence;
    }

    /* renamed from: F */
    public C4957e mo62152F() {
        return this.adRules;
    }

    /* renamed from: G */
    public boolean mo62153G() {
        return this.disableInAppStore;
    }

    /* renamed from: H */
    public C5067n mo62154H() {
        return this.video;
    }

    /* renamed from: I */
    public boolean mo62155I() {
        return this.autoInterstitialEnabled;
    }

    /* renamed from: J */
    public int mo62156J() {
        return this.defaultActivitiesBetweenAds;
    }

    /* renamed from: K */
    public int mo62157K() {
        return this.defaultSecondsBetweenAds;
    }

    /* renamed from: L */
    public int mo62158L() {
        return this.forceExternalBrowserDaysInterval;
    }

    /* renamed from: M */
    public boolean mo62159M() {
        return this.enableForceExternalBrowser;
    }

    /* renamed from: N */
    public boolean mo62160N() {
        return this.enforceForeground;
    }

    /* renamed from: a */
    public static void m3034a(Context context, C4983b bVar) {
        synchronized (f3154g) {
            bVar.acMetadataUpdateVersion = AdsConstants.f3024h;
            f3155h = bVar;
            C5152e.m3790a(context, "StartappAdsMetadata", (Serializable) bVar);
        }
    }
}
