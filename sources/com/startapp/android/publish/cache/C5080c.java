package com.startapp.android.publish.cache;

import com.braintreepayments.api.models.PostalAddressParser;
import com.startapp.android.publish.adsCommon.C4925Ad.AdType;
import com.startapp.android.publish.adsCommon.C4988c;
import com.startapp.android.publish.adsCommon.Utils.C4946i;
import com.startapp.android.publish.common.model.AdPreferences;
import com.startapp.android.publish.common.model.AdPreferences.Placement;
import java.io.Serializable;
import java.util.Set;

/* renamed from: com.startapp.android.publish.cache.c */
/* compiled from: StartAppSDK */
public class C5080c implements Serializable {
    private static final long serialVersionUID = 1;
    private String advertiserId = null;
    private Set<String> categories;
    private Set<String> categoriesExclude;
    private String country = null;
    private boolean forceFullpage = false;
    private boolean forceOfferWall2D = false;
    private boolean forceOfferWall3D = false;
    private boolean forceOverlay = false;
    private Double minCpm;
    private Placement placement;
    private String template = null;
    private boolean testMode = false;
    private AdType type = null;
    private boolean videoMuted = false;

    public C5080c(Placement placement2, AdPreferences adPreferences) {
        this.placement = placement2;
        this.categories = adPreferences.getCategories();
        this.categoriesExclude = adPreferences.getCategoriesExclude();
        this.videoMuted = adPreferences.isVideoMuted();
        this.minCpm = adPreferences.getMinCpm();
        this.forceOfferWall3D = C4946i.m2926a(adPreferences, "forceOfferWall3D");
        this.forceOfferWall2D = C4946i.m2926a(adPreferences, "forceOfferWall2D");
        this.forceFullpage = C4946i.m2926a(adPreferences, "forceFullpage");
        this.forceOverlay = C4946i.m2926a(adPreferences, "forceOverlay");
        this.testMode = C4946i.m2926a(adPreferences, "testMode");
        this.country = C4946i.m2930b(adPreferences, PostalAddressParser.COUNTRY_CODE_ALPHA_2_KEY);
        this.advertiserId = C4946i.m2930b(adPreferences, "advertiserId");
        this.template = C4946i.m2930b(adPreferences, "template");
        this.type = C4988c.m3094a(adPreferences, "type");
    }

    /* renamed from: a */
    public Placement mo62484a() {
        return this.placement;
    }

    public int hashCode() {
        String str = this.advertiserId;
        int i = 0;
        int hashCode = ((str == null ? 0 : str.hashCode()) + 31) * 31;
        Set<String> set = this.categories;
        int hashCode2 = (hashCode + (set == null ? 0 : set.hashCode())) * 31;
        Set<String> set2 = this.categoriesExclude;
        int hashCode3 = (hashCode2 + (set2 == null ? 0 : set2.hashCode())) * 31;
        String str2 = this.country;
        int hashCode4 = (hashCode3 + (str2 == null ? 0 : str2.hashCode())) * 31;
        Double d = this.minCpm;
        int i2 = 1231;
        int hashCode5 = (((((((((hashCode4 + (d == null ? 0 : d.hashCode())) * 31) + (this.forceFullpage ? 1231 : 1237)) * 31) + (this.forceOfferWall2D ? 1231 : 1237)) * 31) + (this.forceOfferWall3D ? 1231 : 1237)) * 31) + (this.forceOverlay ? 1231 : 1237)) * 31;
        Placement placement2 = this.placement;
        int hashCode6 = (hashCode5 + (placement2 == null ? 0 : placement2.hashCode())) * 31;
        String str3 = this.template;
        int hashCode7 = (hashCode6 + (str3 == null ? 0 : str3.hashCode())) * 31;
        if (!this.testMode) {
            i2 = 1237;
        }
        int i3 = (hashCode7 + i2) * 31;
        AdType adType = this.type;
        if (adType != null) {
            i = adType.hashCode();
        }
        return i3 + i;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        C5080c cVar = (C5080c) obj;
        String str = this.advertiserId;
        if (str == null) {
            if (cVar.advertiserId != null) {
                return false;
            }
        } else if (!str.equals(cVar.advertiserId)) {
            return false;
        }
        Set<String> set = this.categories;
        if (set == null) {
            if (cVar.categories != null) {
                return false;
            }
        } else if (!set.equals(cVar.categories)) {
            return false;
        }
        Set<String> set2 = this.categoriesExclude;
        if (set2 == null) {
            if (cVar.categoriesExclude != null) {
                return false;
            }
        } else if (!set2.equals(cVar.categoriesExclude)) {
            return false;
        }
        String str2 = this.country;
        if (str2 == null) {
            if (cVar.country != null) {
                return false;
            }
        } else if (!str2.equals(cVar.country)) {
            return false;
        }
        if (this.forceFullpage != cVar.forceFullpage || this.forceOfferWall2D != cVar.forceOfferWall2D || this.forceOfferWall3D != cVar.forceOfferWall3D || this.forceOverlay != cVar.forceOverlay || this.placement != cVar.placement) {
            return false;
        }
        String str3 = this.template;
        if (str3 == null) {
            if (cVar.template != null) {
                return false;
            }
        } else if (!str3.equals(cVar.template)) {
            return false;
        }
        if (this.testMode != cVar.testMode || this.videoMuted != cVar.videoMuted) {
            return false;
        }
        AdType adType = this.type;
        if (adType == null) {
            if (cVar.type != null) {
                return false;
            }
        } else if (!adType.equals(cVar.type)) {
            return false;
        }
        Double d = this.minCpm;
        if (d == null) {
            if (cVar.minCpm != null) {
                return false;
            }
        } else if (d != cVar.minCpm) {
            return false;
        }
        return true;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("CacheKey [placement=");
        sb.append(this.placement);
        sb.append(", categories=");
        sb.append(this.categories);
        sb.append(", categoriesExclude=");
        sb.append(this.categoriesExclude);
        sb.append(", forceOfferWall3D=");
        sb.append(this.forceOfferWall3D);
        sb.append(", forceOfferWall2D=");
        sb.append(this.forceOfferWall2D);
        sb.append(", forceFullpage=");
        sb.append(this.forceFullpage);
        sb.append(", forceOverlay=");
        sb.append(this.forceOverlay);
        sb.append(", testMode=");
        sb.append(this.testMode);
        sb.append(", minCpm=");
        sb.append(this.minCpm);
        sb.append(", country=");
        sb.append(this.country);
        sb.append(", advertiserId=");
        sb.append(this.advertiserId);
        sb.append(", template=");
        sb.append(this.template);
        sb.append(", type=");
        sb.append(this.type);
        sb.append("]");
        return sb.toString();
    }
}
