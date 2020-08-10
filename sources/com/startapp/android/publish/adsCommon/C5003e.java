package com.startapp.android.publish.adsCommon;

import android.content.Context;
import com.p021b.p022a.p023a.p024a.C0960b;
import com.startapp.android.publish.ads.splash.SplashConfig.Orientation;
import com.startapp.android.publish.adsCommon.Utils.C4946i;
import com.startapp.android.publish.adsCommon.adinformation.AdInformationPositions.Position;
import com.startapp.android.publish.adsCommon.p078b.C4984a;
import com.startapp.android.publish.adsCommon.p082f.C5015d;
import com.startapp.android.publish.adsCommon.p082f.C5017f;
import com.startapp.android.publish.adsCommon.p083g.p087d.C5031a;
import com.startapp.android.publish.cache.C5071a;
import com.startapp.android.publish.common.metaData.MetaData;
import com.startapp.android.publish.common.model.AdPreferences.Placement;
import com.startapp.android.publish.omsdk.C5133b;
import com.startapp.common.p092a.C5155g;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

/* renamed from: com.startapp.android.publish.adsCommon.e */
/* compiled from: StartAppSDK */
public abstract class C5003e extends C4925Ad {

    /* renamed from: a */
    protected static String f3205a = null;
    private static final long serialVersionUID = 1;
    private String adId = null;
    private List<C4984a> apps;
    private String[] closingUrl;
    private Long delayImpressionInSeconds;
    private int height;
    private String htmlUuid;
    public boolean[] inAppBrowserEnabled;
    private boolean isMraidAd;
    private int orientation = 0;
    private String[] packageNames;
    private Boolean[] sendRedirectHops;
    public boolean[] smartRedirect;
    private String[] trackingClickUrls;
    public String[] trackingUrls;
    private int width;

    /* renamed from: f */
    public String mo62243f() {
        return C5071a.m3485a().mo62466b(this.htmlUuid);
    }

    /* renamed from: g */
    public String mo62244g() {
        return this.htmlUuid;
    }

    /* renamed from: b */
    public void mo62234b(int i) {
        this.width = i;
    }

    /* renamed from: h */
    public int mo62245h() {
        return this.width;
    }

    /* renamed from: c */
    public void mo62236c(int i) {
        this.height = i;
    }

    /* renamed from: c */
    public void mo62237c(String str) {
        this.closingUrl = str.split(",");
    }

    /* renamed from: i */
    public String[] mo62246i() {
        return this.closingUrl;
    }

    /* renamed from: j */
    public int mo62247j() {
        return this.height;
    }

    /* renamed from: a */
    public void mo62231a(int i, int i2) {
        mo62234b(i);
        mo62236c(i2);
    }

    public C5003e(Context context, Placement placement) {
        super(context, placement);
        String str = "";
        this.packageNames = new String[]{str};
        this.htmlUuid = str;
        this.trackingClickUrls = new String[]{str};
        this.closingUrl = new String[]{str};
        this.sendRedirectHops = null;
        this.smartRedirect = new boolean[]{false};
        this.trackingUrls = new String[]{str};
        this.inAppBrowserEnabled = new boolean[]{true};
        this.isMraidAd = false;
        if (f3205a == null) {
            mo61220a();
        }
    }

    /* renamed from: a */
    private void mo61220a() {
        f3205a = C4946i.m2932c(getContext());
    }

    /* renamed from: f */
    private String m3164f(String str) {
        try {
            return C0960b.m102a(C5133b.m3706a(), str);
        } catch (Exception e) {
            C5155g.m3805a(6, e.getMessage());
            C5017f.m3256a(this.context, C5015d.EXCEPTION, "OMSDK: Failed to inject js to html ad.", e.getMessage(), "");
            return str;
        }
    }

    /* renamed from: b */
    public void mo61769b(String str) {
        if (MetaData.getInstance().isOmsdkEnabled()) {
            str = m3164f(str);
        }
        if (C4946i.m2923a(512)) {
            this.htmlUuid = C5071a.m3485a().mo62459a(str);
        }
        String a = mo62230a(str, "@smartRedirect@");
        if (a != null) {
            m3167i(a);
        }
        String a2 = mo62230a(str, "@trackingClickUrl@");
        if (a2 != null) {
            m3169k(a2);
        }
        String a3 = mo62230a(str, "@closeUrl@");
        if (a3 != null) {
            mo62237c(a3);
        }
        String a4 = mo62230a(str, "@tracking@");
        if (a4 != null) {
            m3168j(a4);
        }
        String a5 = mo62230a(str, "@packageName@");
        if (a5 != null) {
            m3170l(a5);
        }
        String a6 = mo62230a(str, "@startappBrowserEnabled@");
        if (a6 != null) {
            m3166h(a6);
        }
        String a7 = mo62230a(str, "@orientation@");
        if (a7 != null && C4946i.m2923a(8)) {
            mo62232a(Orientation.getByName(a7));
        }
        String a8 = mo62230a(str, "@adInfoEnable@");
        if (a8 != null) {
            m3171m(a8);
        }
        String a9 = mo62230a(str, "@adInfoPosition@");
        if (a9 != null) {
            m3172n(a9);
        }
        String a10 = mo62230a(str, "@ttl@");
        if (a10 != null) {
            mo62238d(a10);
        }
        String a11 = mo62230a(str, "@belowMinCPM@");
        if (a11 != null) {
            m3165g(a11);
        }
        String a12 = mo62230a(str, "@delayImpressionInSeconds@");
        if (a12 != null) {
            m3173o(a12);
        }
        String a13 = mo62230a(str, "@sendRedirectHops@");
        if (a13 != null) {
            mo62240e(a13);
        }
        if (this.smartRedirect.length < this.trackingUrls.length) {
            C5155g.m3805a(6, "Error in smartRedirect array in HTML");
            boolean[] zArr = new boolean[this.trackingUrls.length];
            int i = 0;
            while (true) {
                boolean[] zArr2 = this.smartRedirect;
                if (i >= zArr2.length) {
                    break;
                }
                zArr[i] = zArr2[i];
                i++;
            }
            while (i < this.trackingUrls.length) {
                zArr[i] = false;
                i++;
            }
            this.smartRedirect = zArr;
        }
        mo62235b(C5031a.m3293b(str));
    }

    /* renamed from: g */
    private void m3165g(String str) {
        if (Arrays.asList(str.split(",")).contains("false")) {
            this.belowMinCPM = false;
        } else {
            this.belowMinCPM = true;
        }
    }

    /* renamed from: h */
    private void m3166h(String str) {
        String[] split = str.split(",");
        this.inAppBrowserEnabled = new boolean[split.length];
        for (int i = 0; i < split.length; i++) {
            if (split[i].compareTo("false") == 0) {
                this.inAppBrowserEnabled[i] = false;
            } else {
                this.inAppBrowserEnabled[i] = true;
            }
        }
    }

    /* access modifiers changed from: protected */
    /* renamed from: a */
    public String mo62230a(String str, String str2) {
        return C4946i.m2908a(str, str2, str2);
    }

    /* access modifiers changed from: protected */
    /* renamed from: a */
    public void mo62232a(Orientation orientation2) {
        this.orientation = 0;
        boolean a = C4946i.m2923a(8);
        if (orientation2 == null) {
            return;
        }
        if (a && orientation2.equals(Orientation.PORTRAIT)) {
            this.orientation = 1;
        } else if (a && orientation2.equals(Orientation.LANDSCAPE)) {
            this.orientation = 2;
        }
    }

    /* renamed from: i */
    private void m3167i(String str) {
        String[] split = str.split(",");
        this.smartRedirect = new boolean[split.length];
        for (int i = 0; i < split.length; i++) {
            if (split[i].compareTo("true") == 0) {
                this.smartRedirect[i] = true;
            } else {
                this.smartRedirect[i] = false;
            }
        }
    }

    /* renamed from: d */
    public boolean mo62239d(int i) {
        if (i >= 0) {
            boolean[] zArr = this.smartRedirect;
            if (i < zArr.length) {
                return zArr[i];
            }
        }
        return false;
    }

    /* renamed from: k */
    public boolean[] mo62248k() {
        return this.inAppBrowserEnabled;
    }

    /* renamed from: e */
    public boolean mo62241e(int i) {
        boolean[] zArr = this.inAppBrowserEnabled;
        if (zArr == null || i < 0 || i >= zArr.length) {
            return true;
        }
        return zArr[i];
    }

    /* renamed from: j */
    private void m3168j(String str) {
        this.trackingUrls = str.split(",");
    }

    /* renamed from: l */
    public String[] mo62249l() {
        return this.trackingUrls;
    }

    /* renamed from: m */
    public String[] mo62250m() {
        return this.trackingClickUrls;
    }

    /* renamed from: k */
    private void m3169k(String str) {
        this.trackingClickUrls = str.split(",");
    }

    /* renamed from: n */
    public int mo62251n() {
        return this.orientation;
    }

    /* renamed from: l */
    private void m3170l(String str) {
        this.packageNames = str.split(",");
    }

    /* renamed from: o */
    public String[] mo62252o() {
        return this.packageNames;
    }

    /* renamed from: a */
    public void mo62233a(List<C4984a> list) {
        this.apps = list;
    }

    /* renamed from: m */
    private void m3171m(String str) {
        getAdInfoOverride().mo62126a(Boolean.parseBoolean(str));
    }

    /* renamed from: n */
    private void m3172n(String str) {
        getAdInfoOverride().mo62125a(Position.getByName(str));
    }

    /* access modifiers changed from: protected */
    /* renamed from: a_ */
    public String mo61222a_() {
        return f3205a;
    }

    /* renamed from: d */
    public void mo62238d(String str) {
        String[] split;
        Long l = null;
        for (String str2 : str.split(",")) {
            if (!str2.equals("")) {
                try {
                    long parseLong = Long.parseLong(str2);
                    if (parseLong > 0 && (l == null || parseLong < l.longValue())) {
                        l = Long.valueOf(parseLong);
                    }
                } catch (NumberFormatException unused) {
                }
            }
        }
        if (l != null) {
            this.adCacheTtl = Long.valueOf(TimeUnit.SECONDS.toMillis(l.longValue()));
        }
    }

    /* renamed from: p */
    public Long mo62253p() {
        return this.delayImpressionInSeconds;
    }

    /* renamed from: o */
    private void m3173o(String str) {
        if (str != null && !str.equals("")) {
            try {
                this.delayImpressionInSeconds = Long.valueOf(Long.parseLong(str));
            } catch (NumberFormatException unused) {
            }
        }
    }

    /* renamed from: q */
    public Boolean[] mo62254q() {
        return this.sendRedirectHops;
    }

    /* renamed from: f */
    public Boolean mo62242f(int i) {
        Boolean[] boolArr = this.sendRedirectHops;
        if (boolArr == null || i < 0 || i >= boolArr.length) {
            return null;
        }
        return boolArr[i];
    }

    /* renamed from: e */
    public void mo62240e(String str) {
        if (str != null && !str.equals("")) {
            String[] split = str.split(",");
            this.sendRedirectHops = new Boolean[split.length];
            for (int i = 0; i < split.length; i++) {
                if (split[i].compareTo("true") == 0) {
                    this.sendRedirectHops[i] = Boolean.valueOf(true);
                } else if (split[i].compareTo("false") == 0) {
                    this.sendRedirectHops[i] = Boolean.valueOf(false);
                } else {
                    this.sendRedirectHops[i] = null;
                }
            }
        }
    }

    /* renamed from: r */
    public boolean mo62255r() {
        return this.isMraidAd;
    }

    /* renamed from: b */
    public void mo62235b(boolean z) {
        this.isMraidAd = z;
    }
}
