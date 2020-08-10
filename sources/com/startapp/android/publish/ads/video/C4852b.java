package com.startapp.android.publish.ads.video;

import android.content.Context;
import com.google.android.exoplayer2.metadata.icy.IcyHeaders;
import com.startapp.android.publish.ads.video.C4869c.C4872a;
import com.startapp.android.publish.ads.video.C4920g.C4922a;
import com.startapp.android.publish.ads.video.C4923h.C4924a;
import com.startapp.android.publish.ads.video.p071a.C4851b;
import com.startapp.android.publish.ads.video.p073c.p074a.C4874a;
import com.startapp.android.publish.ads.video.p073c.p074a.C4881c;
import com.startapp.android.publish.ads.video.p073c.p074a.C4884d;
import com.startapp.android.publish.ads.video.p073c.p076b.C4888b;
import com.startapp.android.publish.ads.video.tracking.VideoTrackingLink;
import com.startapp.android.publish.ads.video.tracking.VideoTrackingParams;
import com.startapp.android.publish.adsCommon.C4925Ad;
import com.startapp.android.publish.adsCommon.C4925Ad.AdType;
import com.startapp.android.publish.adsCommon.C4983b;
import com.startapp.android.publish.adsCommon.C5003e;
import com.startapp.android.publish.adsCommon.C5021g;
import com.startapp.android.publish.adsCommon.StartAppAd;
import com.startapp.android.publish.adsCommon.Utils.C4946i;
import com.startapp.android.publish.adsCommon.adListeners.AdEventListener;
import com.startapp.android.publish.adsCommon.p082f.C5015d;
import com.startapp.android.publish.adsCommon.p082f.C5017f;
import com.startapp.android.publish.cache.C5071a;
import com.startapp.android.publish.cache.C5080c;
import com.startapp.android.publish.common.metaData.MetaData;
import com.startapp.android.publish.common.model.AdPreferences;
import com.startapp.android.publish.common.model.AdPreferences.Placement;
import com.startapp.android.publish.common.model.GetAdRequest;
import com.startapp.android.publish.html.C5124a;
import com.startapp.common.p042c.C5180b;
import com.startapp.common.p092a.C5155g;
import com.startapp.common.p092a.C5156h.C5157a;
import java.net.URL;
import java.util.ArrayList;

/* renamed from: com.startapp.android.publish.ads.video.b */
/* compiled from: StartAppSDK */
public class C4852b extends C5124a {

    /* renamed from: i */
    private long f2748i = System.currentTimeMillis();

    /* renamed from: j */
    private volatile C5080c f2749j;

    /* renamed from: k */
    private C4881c f2750k;

    /* renamed from: l */
    private int f2751l = 0;

    public C4852b(Context context, C4925Ad ad, AdPreferences adPreferences, AdEventListener adEventListener) {
        C4881c cVar;
        super(context, ad, adPreferences, adEventListener, Placement.INAPP_OVERLAY, true);
        if (C4983b.m3032a().mo62154H().mo62439r() == 0) {
            cVar = new C4881c(context);
        } else {
            cVar = new C4884d(context, C4983b.m3032a().mo62154H().mo62440s());
        }
        this.f2750k = cVar;
    }

    /* access modifiers changed from: protected */
    /* renamed from: a */
    public boolean mo61146a(Object obj) {
        boolean z;
        C5157a aVar = (C5157a) obj;
        String str = null;
        if (aVar == null || !aVar.mo62867b().toLowerCase().contains("json")) {
            if (aVar != null) {
                str = aVar.mo62865a();
            }
            if (C4983b.m3032a().mo62154H().mo62429h() && m2573b(str)) {
                m2572b(false);
            }
            return super.mo61146a(obj);
        }
        if (C4983b.m3032a().mo62154H().mo62429h() && !this.f3485h.hasCampaignExclude()) {
            m2572b(true);
        }
        try {
            VASTJson vASTJson = (VASTJson) C5180b.m3908a(aVar.mo62865a(), VASTJson.class);
            if (vASTJson == null || vASTJson.getVastTag() == null) {
                z = m2568a("no VAST wrapper in json", null, true);
            } else {
                C4888b bVar = new C4888b(C4983b.m3032a().mo62154H().mo62435n(), C4983b.m3032a().mo62154H().mo62436o());
                C4874a a = bVar.mo61762a(this.f3196a, vASTJson.getVastTag(), this.f2750k);
                ((C4893e) this.f3197b).mo61768a(bVar.mo61763a(), this.f3197b.getType() != AdType.REWARDED_VIDEO);
                if (vASTJson.getTtlSec() != null) {
                    ((C4893e) this.f3197b).mo62238d(vASTJson.getTtlSec());
                }
                if (a == C4874a.ErrorNone) {
                    m2567a(C4874a.SAProcessSuccess);
                    aVar.mo62866a(vASTJson.getAdmTag());
                    aVar.mo62868b("text/html");
                    z = super.mo61146a((Object) aVar);
                } else {
                    m2567a(a);
                    if (vASTJson.getCampaignId() != null) {
                        this.f3484g.add(vASTJson.getCampaignId());
                    }
                    this.f2751l++;
                    ((C4893e) this.f3197b).mo61771e();
                    if (System.currentTimeMillis() - this.f2748i >= ((long) C4983b.m3032a().mo62154H().mo62437p())) {
                        z = m2568a("VAST retry timeout", null, false);
                    } else if (this.f2751l > C4983b.m3032a().mo62154H().mo62438q()) {
                        z = m2568a("VAST too many excludes", null, false);
                    } else {
                        z = mo62217d().booleanValue();
                    }
                }
            }
            return z;
        } catch (Exception e) {
            return m2568a("VAST json parsing", e, true);
        }
    }

    /* access modifiers changed from: protected */
    /* renamed from: a */
    public void mo61145a(final Boolean bool) {
        super.mo61145a(bool);
        if (!bool.booleanValue() || !m2574h()) {
            mo62804a(bool.booleanValue());
            return;
        }
        if (C4983b.m3032a().mo62154H().mo62430i()) {
            super.mo61661b(bool);
        }
        mo61660b().setVideoMuted(this.f3198c.isVideoMuted());
        C4889d.m2729a().mo61765a(this.f3196a.getApplicationContext(), mo61660b().getVideoUrl(), new C4922a() {
            /* renamed from: a */
            public void mo61662a(String str) {
                if (str != null) {
                    if (!str.equals("downloadInterrupted")) {
                        C4852b.super.mo61661b(bool);
                        C4852b.this.mo61660b().setLocalVideoPath(str);
                    }
                    C4852b.this.mo62804a(bool.booleanValue());
                    return;
                }
                C4852b.this.mo62804a(false);
                C4852b.this.f3199d.onFailedToReceiveAd(C4852b.this.f3197b);
                C4852b.this.m2567a(C4874a.FileNotFound);
            }
        }, new C4872a() {
            /* renamed from: a */
            public void mo61663a(String str) {
                C4852b.this.mo61660b().setLocalVideoPath(str);
            }
        });
    }

    /* access modifiers changed from: protected */
    /* renamed from: a */
    public boolean mo61659a(GetAdRequest getAdRequest) {
        if (!super.mo61659a(getAdRequest)) {
            return false;
        }
        if (getAdRequest.isAdTypeVideo()) {
            C4924a a = C4923h.m2843a(this.f3196a);
            if (a != C4924a.ELIGIBLE) {
                this.f3201f = a.mo61817a();
                return false;
            }
        }
        return true;
    }

    /* access modifiers changed from: protected */
    /* renamed from: a */
    public GetAdRequest mo61353a() {
        return mo62215b((GetAdRequest) new C4849a());
    }

    /* renamed from: h */
    private boolean m2574h() {
        return mo61660b() != null;
    }

    /* access modifiers changed from: protected */
    /* renamed from: b */
    public void mo61661b(Boolean bool) {
        if (!m2574h()) {
            super.mo61661b(bool);
        }
    }

    /* access modifiers changed from: 0000 */
    /* renamed from: b */
    public VideoAdDetails mo61660b() {
        return ((C4893e) this.f3197b).mo61770d();
    }

    /* renamed from: b */
    private void m2572b(boolean z) {
        AdPreferences adPreferences;
        if ((this.f3197b.getType() != AdType.REWARDED_VIDEO && this.f3197b.getType() != AdType.VIDEO) || z) {
            if (this.f3198c == null) {
                adPreferences = new AdPreferences();
            } else {
                adPreferences = new AdPreferences(this.f3198c);
            }
            AdPreferences adPreferences2 = adPreferences;
            adPreferences2.setType((this.f3197b.getType() == AdType.REWARDED_VIDEO || this.f3197b.getType() == AdType.VIDEO) ? AdType.VIDEO_NO_VAST : AdType.NON_VIDEO);
            C5080c a = C5071a.m3485a().mo62454a(this.f3196a, (StartAppAd) null, this.f3200e, adPreferences2, (AdEventListener) null);
            if (z) {
                this.f2749j = a;
            }
        }
    }

    /* renamed from: b */
    private boolean m2573b(String str) {
        String str2 = "@videoJson@";
        return C4946i.m2908a(str, str2, str2) != null;
    }

    /* access modifiers changed from: private */
    /* renamed from: a */
    public void m2567a(C4874a aVar) {
        String str = "";
        VideoTrackingLink[] videoTrackingLinkArr = null;
        try {
            if (!(mo61660b() == null || mo61660b().getVideoTrackingDetails() == null)) {
                videoTrackingLinkArr = mo61660b().getVideoTrackingDetails().getInlineErrorTrackingUrls();
            }
            if (videoTrackingLinkArr != null && videoTrackingLinkArr.length > 0) {
                if (aVar == C4874a.SAShowBeforeVast || aVar == C4874a.SAProcessSuccess) {
                    try {
                        ArrayList arrayList = new ArrayList();
                        String lowerCase = new URL(MetaData.getInstance().getAdPlatformHost()).getHost().split("\\.")[1].toLowerCase();
                        for (VideoTrackingLink videoTrackingLink : videoTrackingLinkArr) {
                            if (videoTrackingLink.getTrackingUrl() != null && videoTrackingLink.getTrackingUrl().toLowerCase().contains(lowerCase)) {
                                arrayList.add(videoTrackingLink);
                            }
                        }
                        if (arrayList.size() > 0) {
                            videoTrackingLinkArr = (VideoTrackingLink[]) arrayList.toArray(new VideoTrackingLink[arrayList.size()]);
                        } else {
                            return;
                        }
                    } catch (Exception e) {
                        C5017f.m3256a(this.f3196a, C5015d.EXCEPTION, "GetVideoEnabledService.sendVideoErrorEvent filter sa links", e.getMessage(), str);
                    }
                }
                C4923h.m2846a(this.f3196a, new C4851b(videoTrackingLinkArr, new VideoTrackingParams(str, 0, 0, IcyHeaders.REQUEST_HEADER_ENABLE_METADATA_VALUE), mo61660b().getVideoUrl(), 0).mo61658a("error").mo61657a(aVar).mo61656a());
            }
        } catch (Exception e2) {
            C5017f.m3256a(this.f3196a, C5015d.EXCEPTION, "GetVideoEnabledService.sendVideoErrorEvent", e2.getMessage(), str);
        }
    }

    /* renamed from: a */
    private boolean m2568a(String str, Throwable th, boolean z) {
        C5155g.m3808a("GetVideoEnabledService", 6, str, th);
        if (z) {
            String str2 = "";
            C5017f.m3256a(this.f3196a, C5015d.EXCEPTION, str, th != null ? th.getMessage() : str2, str2);
        }
        C5021g a = C5071a.m3485a().mo62452a(this.f2749j);
        if (a instanceof C5003e) {
            C5157a aVar = new C5157a();
            aVar.mo62868b("text/html");
            aVar.mo62866a(((C5003e) a).mo62243f());
            return super.mo61146a((Object) aVar);
        }
        this.f3197b.setErrorMessage(this.f3201f);
        return false;
    }
}
