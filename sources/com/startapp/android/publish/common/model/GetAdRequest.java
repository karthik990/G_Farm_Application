package com.startapp.android.publish.common.model;

import android.content.Context;
import android.telephony.NeighboringCellInfo;
import android.util.Pair;
import com.braintreepayments.api.models.PostalAddressParser;
import com.startapp.android.publish.adsCommon.BaseRequest;
import com.startapp.android.publish.adsCommon.C4925Ad.AdType;
import com.startapp.android.publish.adsCommon.C4983b;
import com.startapp.android.publish.adsCommon.SDKAdPreferences.Gender;
import com.startapp.android.publish.adsCommon.Utils.C4940d;
import com.startapp.android.publish.adsCommon.Utils.C4941e;
import com.startapp.android.publish.adsCommon.Utils.C4944g;
import com.startapp.android.publish.adsCommon.Utils.C4946i;
import com.startapp.android.publish.adsCommon.p077a.C4954b;
import com.startapp.android.publish.common.metaData.MetaData;
import com.startapp.android.publish.common.model.AdPreferences.Placement;
import com.startapp.common.p092a.C5138a;
import com.startapp.common.p092a.C5146c;
import java.util.HashSet;
import java.util.Set;

/* compiled from: StartAppSDK */
public class GetAdRequest extends BaseRequest {
    private int adsDisplayed;
    private int adsNumber = 1;
    private String advertiserId = null;
    private String age;

    /* renamed from: ai */
    private Boolean f3482ai;

    /* renamed from: as */
    private Boolean f3483as;
    private Set<String> campaignExclude = null;
    private Set<String> categories = null;
    private Set<String> categoriesExclude = null;
    private boolean contentAd;
    private String country = null;
    private boolean engInclude = true;
    private Gender gender;
    private Boolean isAutoDateTimeEnabled;
    private boolean isDefaultMetaData = true;
    private boolean isDisableTwoClicks = C4983b.m3032a().mo62150D();
    private boolean isHardwareAccelerated = true;
    private String keywords;
    private Double minCpm;
    private String moreImg;
    private int offset = 0;
    private Set<String> packageExclude = null;
    private Set<String> packageInclude = null;
    private Placement placement;
    private String primaryImg;
    private String profileId;
    private Pair<String, String> simpleToken;
    private String template;
    private boolean testMode;
    private long timeSinceSessionStart = (System.currentTimeMillis() - C4944g.m2886d().mo62035b());
    private AdType type = null;

    /* compiled from: StartAppSDK */
    static class CellScanResult {
        private static final char DELIMITER = ',';
        private NeighboringCellInfo cellInfo;

        public CellScanResult(NeighboringCellInfo neighboringCellInfo) {
            this.cellInfo = neighboringCellInfo;
        }

        public String toString() {
            StringBuilder sb = new StringBuilder();
            NeighboringCellInfo neighboringCellInfo = this.cellInfo;
            if (neighboringCellInfo != null) {
                sb.append(neighboringCellInfo.getCid());
                sb.append(',');
                sb.append(this.cellInfo.getRssi());
                sb.append(',');
                sb.append(this.cellInfo.getPsc());
                sb.append(',');
                sb.append(this.cellInfo.getNetworkType());
                sb.append(',');
                sb.append(this.cellInfo.getLac());
            }
            return sb.toString();
        }
    }

    /* compiled from: StartAppSDK */
    protected enum VideoRequestMode {
        INTERSTITIAL,
        REWARDED
    }

    /* compiled from: StartAppSDK */
    protected enum VideoRequestType {
        ENABLED,
        DISABLED,
        FORCED,
        FORCED_NONVAST
    }

    public GetAdRequest() {
        if (!C4946i.m2922a()) {
            this.adsDisplayed = C4954b.m2946a().mo62058d();
        }
        this.profileId = MetaData.getInstance().getProfileId();
    }

    public Placement getPlacement() {
        return this.placement;
    }

    public void setPlacement(Placement placement2) {
        this.placement = placement2;
    }

    public boolean isTestMode() {
        return this.testMode;
    }

    /* access modifiers changed from: protected */
    public boolean isDisableTwoClicks() {
        return this.isDisableTwoClicks;
    }

    public void setDisableTwoClicks(boolean z) {
        this.isDisableTwoClicks = z;
    }

    public void setTestMode(boolean z) {
        this.testMode = z;
    }

    public Gender getGender() {
        return this.gender;
    }

    public void setGender(Gender gender2) {
        this.gender = gender2;
    }

    public String getAge() {
        return this.age;
    }

    public void setAge(String str) {
        this.age = str;
    }

    public String getKeywords() {
        return this.keywords;
    }

    public void setKeywords(String str) {
        this.keywords = str;
    }

    public String getTemplate() {
        return this.template;
    }

    public void setTemplate(String str) {
        this.template = str;
    }

    public int getAdsNumber() {
        return this.adsNumber;
    }

    public void setAdsNumber(int i) {
        this.adsNumber = i;
    }

    public String getPrimaryImg() {
        return this.primaryImg;
    }

    public void setPrimaryImg(String str) {
        this.primaryImg = str;
    }

    public String getMoreImg() {
        return this.moreImg;
    }

    public void setMoreImg(String str) {
        this.moreImg = str;
    }

    public boolean isContentAd() {
        return this.contentAd;
    }

    public void setContentAd(boolean z) {
        this.contentAd = z;
    }

    public Set<String> getCategories() {
        return this.categories;
    }

    public void setCategories(Set<String> set) {
        this.categories = set;
    }

    public double getMinCpm() {
        return this.minCpm.doubleValue();
    }

    public void setMinCpm(double d) {
        this.minCpm = Double.valueOf(d);
    }

    public void addCategory(String str) {
        if (this.categories == null) {
            this.categories = new HashSet();
        }
        this.categories.add(str);
    }

    public Set<String> getCategoriesExclude() {
        return this.categoriesExclude;
    }

    public void setCategoriesExclude(Set<String> set) {
        this.categoriesExclude = set;
    }

    public void addCategoryExclude(String str) {
        if (this.categoriesExclude == null) {
            this.categoriesExclude = new HashSet();
        }
        this.categoriesExclude.add(str);
    }

    public Set<String> getPackageExclude() {
        return this.packageExclude;
    }

    public void setPackageExclude(Set<String> set) {
        this.packageExclude = set;
    }

    public Set<String> getPackageInclude() {
        return this.packageInclude;
    }

    public void setPackageInclude(Set<String> set) {
        this.packageInclude = set;
    }

    public Set<String> getCampaignExclude() {
        return this.campaignExclude;
    }

    public boolean hasCampaignExclude() {
        Set<String> set = this.campaignExclude;
        return set != null && set.size() > 0;
    }

    public void setCampaignExclude(Set<String> set) {
        this.campaignExclude = set;
    }

    public int getOffset() {
        return this.offset;
    }

    public void setOffset(int i) {
        this.offset = i;
    }

    public Pair<String, String> getSimpleToken() {
        return this.simpleToken;
    }

    public void setSimpleToken(Pair<String, String> pair) {
        this.simpleToken = pair;
    }

    public boolean isEngInclude() {
        return this.engInclude;
    }

    public void setEngInclude(boolean z) {
        this.engInclude = z;
    }

    public Boolean getAi() {
        return this.f3482ai;
    }

    public void setAi(Boolean bool) {
        this.f3482ai = bool;
    }

    public Boolean getAs() {
        return this.f3483as;
    }

    public void setAs(Boolean bool) {
        this.f3483as = bool;
    }

    public void setRetry(int i) {
        this.retry = Integer.valueOf(i);
    }

    public String toString() {
        StringBuilder sb = new StringBuilder("GetAdRequest [");
        StringBuilder sb2 = new StringBuilder();
        sb2.append("placement=");
        sb2.append(this.placement);
        sb.append(sb2.toString());
        StringBuilder sb3 = new StringBuilder();
        sb3.append(", testMode=");
        sb3.append(this.testMode);
        sb.append(sb3.toString());
        StringBuilder sb4 = new StringBuilder();
        sb4.append(", gender=");
        sb4.append(this.gender);
        sb.append(sb4.toString());
        StringBuilder sb5 = new StringBuilder();
        sb5.append(", age=");
        sb5.append(this.age);
        sb.append(sb5.toString());
        StringBuilder sb6 = new StringBuilder();
        sb6.append(", ai=");
        sb6.append(this.f3482ai);
        sb.append(sb6.toString());
        StringBuilder sb7 = new StringBuilder();
        sb7.append(", as=");
        sb7.append(this.f3483as);
        sb.append(sb7.toString());
        StringBuilder sb8 = new StringBuilder();
        sb8.append(", keywords=");
        sb8.append(this.keywords);
        sb.append(sb8.toString());
        StringBuilder sb9 = new StringBuilder();
        sb9.append(", template=");
        sb9.append(this.template);
        sb.append(sb9.toString());
        StringBuilder sb10 = new StringBuilder();
        sb10.append(", adsNumber=");
        sb10.append(this.adsNumber);
        sb.append(sb10.toString());
        StringBuilder sb11 = new StringBuilder();
        sb11.append(", offset=");
        sb11.append(this.offset);
        sb.append(sb11.toString());
        StringBuilder sb12 = new StringBuilder();
        sb12.append(", categories=");
        sb12.append(this.categories);
        sb.append(sb12.toString());
        StringBuilder sb13 = new StringBuilder();
        sb13.append(", categoriesExclude=");
        sb13.append(this.categoriesExclude);
        sb.append(sb13.toString());
        StringBuilder sb14 = new StringBuilder();
        sb14.append(", packageExclude=");
        sb14.append(this.packageExclude);
        sb.append(sb14.toString());
        StringBuilder sb15 = new StringBuilder();
        sb15.append(", packageInclude=");
        sb15.append(this.packageInclude);
        sb.append(sb15.toString());
        StringBuilder sb16 = new StringBuilder();
        sb16.append(", simpleToken=");
        sb16.append(this.simpleToken);
        sb.append(sb16.toString());
        StringBuilder sb17 = new StringBuilder();
        sb17.append(", engInclude=");
        sb17.append(this.engInclude);
        sb.append(sb17.toString());
        StringBuilder sb18 = new StringBuilder();
        sb18.append(", country=");
        sb18.append(this.country);
        sb.append(sb18.toString());
        StringBuilder sb19 = new StringBuilder();
        sb19.append(", advertiserId=");
        sb19.append(this.advertiserId);
        sb.append(sb19.toString());
        StringBuilder sb20 = new StringBuilder();
        sb20.append(", type=");
        sb20.append(this.type);
        sb.append(sb20.toString());
        StringBuilder sb21 = new StringBuilder();
        sb21.append(", minCpm=");
        sb21.append(this.minCpm);
        sb.append(sb21.toString());
        StringBuilder sb22 = new StringBuilder();
        sb22.append(", sessionStartTime=");
        sb22.append(this.timeSinceSessionStart);
        sb.append(sb22.toString());
        StringBuilder sb23 = new StringBuilder();
        sb23.append(", adsDisplayed=");
        sb23.append(this.adsDisplayed);
        sb.append(sb23.toString());
        StringBuilder sb24 = new StringBuilder();
        sb24.append(", profileId=");
        sb24.append(this.profileId);
        sb.append(sb24.toString());
        StringBuilder sb25 = new StringBuilder();
        sb25.append(", hardwareAccelerated=");
        sb25.append(this.isHardwareAccelerated);
        sb.append(sb25.toString());
        StringBuilder sb26 = new StringBuilder();
        sb26.append(", primaryImg=");
        sb26.append(this.primaryImg);
        sb.append(sb26.toString());
        StringBuilder sb27 = new StringBuilder();
        sb27.append(", moreImg=");
        sb27.append(this.moreImg);
        sb.append(sb27.toString());
        StringBuilder sb28 = new StringBuilder();
        sb28.append(", contentAd=");
        sb28.append(this.contentAd);
        sb.append(sb28.toString());
        StringBuilder sb29 = new StringBuilder();
        sb29.append(", defaultMetaData=");
        sb29.append(this.isDefaultMetaData);
        sb.append(sb29.toString());
        sb.append("]");
        return sb.toString();
    }

    public void fillAdPreferences(Context context, AdPreferences adPreferences, Placement placement2, Pair<String, String> pair) {
        this.placement = placement2;
        this.simpleToken = pair;
        this.f3482ai = adPreferences.getAi();
        this.f3483as = adPreferences.getAs();
        this.age = adPreferences.getAge(context);
        this.gender = adPreferences.getGender(context);
        this.keywords = adPreferences.getKeywords();
        this.testMode = adPreferences.isTestMode();
        this.categories = adPreferences.getCategories();
        this.categoriesExclude = adPreferences.getCategoriesExclude();
        this.isHardwareAccelerated = adPreferences.isHardwareAccelerated();
        this.isAutoDateTimeEnabled = Boolean.valueOf(C5146c.m3771c(context));
        this.minCpm = adPreferences.getMinCpm();
        this.isDefaultMetaData = !MetaData.isLoadedFromServer(context);
        fillLocationDetails(adPreferences, context);
        setCountry(adPreferences.country);
        setAdvertiser(adPreferences.advertiserId);
        setTemplate(adPreferences.template);
        setType(adPreferences.type);
        setPackageInclude(adPreferences.packageInclude);
    }

    public boolean isAdTypeVideo() {
        return getType() == AdType.VIDEO || getType() == AdType.REWARDED_VIDEO;
    }

    public C4941e getNameValueMap() {
        C4941e nameValueMap = super.getNameValueMap();
        if (nameValueMap == null) {
            nameValueMap = new C4940d();
        }
        addParams(nameValueMap);
        return nameValueMap;
    }

    private void addParams(C4941e eVar) {
        eVar.mo62030a("placement", (Object) this.placement.name(), true);
        eVar.mo62030a("testMode", (Object) Boolean.toString(this.testMode), false);
        eVar.mo62030a("gender", (Object) this.gender, false);
        eVar.mo62030a("age", (Object) this.age, false);
        eVar.mo62030a("keywords", (Object) this.keywords, false);
        eVar.mo62030a("template", (Object) this.template, false);
        eVar.mo62030a("adsNumber", (Object) Integer.toString(this.adsNumber), false);
        eVar.mo62031a("category", this.categories, false);
        eVar.mo62031a("categoryExclude", this.categoriesExclude, false);
        eVar.mo62031a("packageExclude", this.packageExclude, false);
        eVar.mo62031a("campaignExclude", this.campaignExclude, false);
        eVar.mo62030a("offset", (Object) Integer.toString(this.offset), false);
        eVar.mo62030a("ai", (Object) this.f3482ai, false);
        eVar.mo62030a("as", (Object) this.f3483as, false);
        eVar.mo62030a("minCPM", (Object) C4946i.m2907a(this.minCpm), false);
        eVar.mo62030a("twoClicks", (Object) Boolean.valueOf(!this.isDisableTwoClicks), false);
        eVar.mo62030a("engInclude", (Object) Boolean.toString(this.engInclude), false);
        if (getType() == AdType.INTERSTITIAL || getType() == AdType.RICH_TEXT) {
            eVar.mo62030a("type", (Object) this.type, false);
        }
        eVar.mo62030a("timeSinceSessionStart", (Object) Long.valueOf(this.timeSinceSessionStart), true);
        eVar.mo62030a("adsDisplayed", (Object) Integer.valueOf(this.adsDisplayed), true);
        eVar.mo62030a("profileId", (Object) this.profileId, false);
        eVar.mo62030a("hardwareAccelerated", (Object) Boolean.valueOf(this.isHardwareAccelerated), false);
        eVar.mo62030a("dts", (Object) this.isAutoDateTimeEnabled, false);
        eVar.mo62030a("downloadingMode", (Object) "CACHE", false);
        eVar.mo62030a("primaryImg", (Object) this.primaryImg, false);
        eVar.mo62030a("moreImg", (Object) this.moreImg, false);
        eVar.mo62030a("contentAd", (Object) Boolean.toString(this.contentAd), false);
        String d = C5138a.m3718d();
        eVar.mo62030a(C5138a.m3710a(), (Object) d, true);
        String c = C5138a.m3716c();
        StringBuilder sb = new StringBuilder();
        sb.append(getProductId());
        sb.append(this.placement.name());
        sb.append(getSessionId());
        sb.append(getSdkVersion());
        sb.append(d);
        eVar.mo62026a(c, (Object) C5138a.m3715b(sb.toString()), true, false);
        if (getCountry() != null) {
            eVar.mo62030a(PostalAddressParser.COUNTRY_CODE_ALPHA_2_KEY, (Object) getCountry(), false);
        }
        if (getAdvertiserId() != null) {
            eVar.mo62030a("advertiserId", (Object) getAdvertiserId(), false);
        }
        if (getPackageInclude() != null) {
            eVar.mo62031a("packageInclude", getPackageInclude(), false);
        }
        eVar.mo62030a("defaultMetaData", (Object) Boolean.valueOf(this.isDefaultMetaData), true);
        eVar.mo62030a((String) this.simpleToken.first, this.simpleToken.second, false);
    }

    public void setCountry(String str) {
        this.country = str;
    }

    public void setAdvertiser(String str) {
        this.advertiserId = str;
    }

    public String getCountry() {
        return this.country;
    }

    public String getAdvertiserId() {
        return this.advertiserId;
    }

    public AdType getType() {
        return this.type;
    }

    public void setType(AdType adType) {
        this.type = adType;
    }
}
