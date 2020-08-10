package com.startapp.android.publish.ads.video;

import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.View;
import android.webkit.WebView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.TextView;
import android.widget.VideoView;
import androidx.core.view.ViewCompat;
import androidx.exifinterface.media.ExifInterface;
import com.google.android.exoplayer2.metadata.icy.IcyHeaders;
import com.p021b.p022a.p023a.p024a.p026b.C0961a;
import com.p021b.p022a.p023a.p024a.p026b.p027a.C0962a;
import com.startapp.android.publish.ads.p066a.C4740c;
import com.startapp.android.publish.ads.video.VideoAdDetails.PostRollType;
import com.startapp.android.publish.ads.video.p071a.C4851b;
import com.startapp.android.publish.ads.video.p072b.C4856b;
import com.startapp.android.publish.ads.video.p072b.C4856b.C4858a;
import com.startapp.android.publish.ads.video.p072b.C4860c;
import com.startapp.android.publish.ads.video.p072b.C4860c.C4861a;
import com.startapp.android.publish.ads.video.p072b.C4860c.C4862b;
import com.startapp.android.publish.ads.video.p072b.C4860c.C4863c;
import com.startapp.android.publish.ads.video.p072b.C4860c.C4864d;
import com.startapp.android.publish.ads.video.p072b.C4860c.C4865e;
import com.startapp.android.publish.ads.video.p072b.C4860c.C4866f;
import com.startapp.android.publish.ads.video.p072b.C4860c.C4867g;
import com.startapp.android.publish.ads.video.p072b.C4860c.C4868h;
import com.startapp.android.publish.ads.video.p073c.p074a.C4874a;
import com.startapp.android.publish.ads.video.tracking.AbsoluteTrackingLink;
import com.startapp.android.publish.ads.video.tracking.ActionTrackingLink;
import com.startapp.android.publish.ads.video.tracking.FractionTrackingLink;
import com.startapp.android.publish.ads.video.tracking.VideoClickedTrackingParams;
import com.startapp.android.publish.ads.video.tracking.VideoClickedTrackingParams.ClickOrigin;
import com.startapp.android.publish.ads.video.tracking.VideoPausedTrackingParams;
import com.startapp.android.publish.ads.video.tracking.VideoPausedTrackingParams.PauseOrigin;
import com.startapp.android.publish.ads.video.tracking.VideoProgressTrackingParams;
import com.startapp.android.publish.ads.video.tracking.VideoTrackingLink;
import com.startapp.android.publish.ads.video.tracking.VideoTrackingParams;
import com.startapp.android.publish.adsCommon.AdsConstants;
import com.startapp.android.publish.adsCommon.C4925Ad.AdType;
import com.startapp.android.publish.adsCommon.C4983b;
import com.startapp.android.publish.adsCommon.C4988c;
import com.startapp.android.publish.adsCommon.C5067n.C5068a;
import com.startapp.android.publish.adsCommon.Utils.C4945h;
import com.startapp.android.publish.adsCommon.adListeners.AdDisplayListener.NotDisplayedReason;
import com.startapp.android.publish.adsCommon.p080d.C5002b;
import com.startapp.android.publish.adsCommon.p082f.C5015d;
import com.startapp.android.publish.adsCommon.p082f.C5016e;
import com.startapp.android.publish.adsCommon.p082f.C5017f;
import com.startapp.android.publish.common.metaData.MetaData;
import com.startapp.android.publish.html.JsInterface;
import com.startapp.android.publish.omsdk.C5132a;
import com.startapp.common.C5160b;
import com.startapp.common.p092a.C5146c;
import com.startapp.common.p092a.C5146c.C5149a;
import com.startapp.common.p092a.C5155g;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import p043io.fabric.sdk.android.services.events.EventsFilesManager;

/* renamed from: com.startapp.android.publish.ads.video.f */
/* compiled from: StartAppSDK */
public class C4894f extends C4740c {

    /* renamed from: A */
    protected int f2923A;

    /* renamed from: B */
    protected String f2924B = null;

    /* renamed from: C */
    protected Handler f2925C = new Handler();

    /* renamed from: D */
    protected Handler f2926D = new Handler();

    /* renamed from: E */
    protected Handler f2927E = new Handler();

    /* renamed from: F */
    protected Handler f2928F = new Handler();

    /* renamed from: G */
    private RelativeLayout f2929G;

    /* renamed from: H */
    private RelativeLayout f2930H;

    /* renamed from: I */
    private int f2931I = 0;

    /* renamed from: J */
    private int f2932J = 0;

    /* renamed from: K */
    private boolean f2933K = false;

    /* renamed from: L */
    private HashMap<Integer, Boolean> f2934L = new HashMap<>();

    /* renamed from: M */
    private HashMap<Integer, Boolean> f2935M = new HashMap<>();

    /* renamed from: N */
    private int f2936N = 1;

    /* renamed from: O */
    private boolean f2937O = false;

    /* renamed from: P */
    private boolean f2938P = false;

    /* renamed from: Q */
    private Map<Integer, List<FractionTrackingLink>> f2939Q = new HashMap();

    /* renamed from: R */
    private Map<Integer, List<AbsoluteTrackingLink>> f2940R = new HashMap();

    /* renamed from: S */
    private long f2941S;

    /* renamed from: T */
    private ClickOrigin f2942T;

    /* renamed from: U */
    private long f2943U;

    /* renamed from: V */
    private long f2944V;
    /* access modifiers changed from: private */

    /* renamed from: W */
    public C0962a f2945W;

    /* renamed from: i */
    protected C4860c f2946i;

    /* renamed from: j */
    protected VideoView f2947j;

    /* renamed from: k */
    protected ProgressBar f2948k;

    /* renamed from: l */
    protected boolean f2949l = false;

    /* renamed from: m */
    protected int f2950m = 0;

    /* renamed from: n */
    protected int f2951n = 0;

    /* renamed from: o */
    protected boolean f2952o;

    /* renamed from: p */
    protected boolean f2953p = false;

    /* renamed from: q */
    protected boolean f2954q = false;

    /* renamed from: r */
    protected boolean f2955r = false;

    /* renamed from: s */
    protected boolean f2956s = false;

    /* renamed from: t */
    protected boolean f2957t = false;

    /* renamed from: u */
    protected int f2958u = 0;

    /* renamed from: v */
    protected boolean f2959v = false;

    /* renamed from: w */
    protected boolean f2960w = false;

    /* renamed from: x */
    protected boolean f2961x = false;

    /* renamed from: y */
    protected boolean f2962y = false;

    /* renamed from: z */
    protected int f2963z = 0;

    /* renamed from: com.startapp.android.publish.ads.video.f$13 */
    /* compiled from: StartAppSDK */
    static /* synthetic */ class C489913 {

        /* renamed from: a */
        static final /* synthetic */ int[] f2968a = new int[C4868h.values().length];

        /* JADX WARNING: Can't wrap try/catch for region: R(10:0|1|2|3|4|5|6|7|8|10) */
        /* JADX WARNING: Can't wrap try/catch for region: R(8:0|1|2|3|4|5|6|(3:7|8|10)) */
        /* JADX WARNING: Failed to process nested try/catch */
        /* JADX WARNING: Missing exception handler attribute for start block: B:3:0x0014 */
        /* JADX WARNING: Missing exception handler attribute for start block: B:5:0x001f */
        /* JADX WARNING: Missing exception handler attribute for start block: B:7:0x002a */
        static {
            /*
                com.startapp.android.publish.ads.video.b.c$h[] r0 = com.startapp.android.publish.ads.video.p072b.C4860c.C4868h.values()
                int r0 = r0.length
                int[] r0 = new int[r0]
                f2968a = r0
                int[] r0 = f2968a     // Catch:{ NoSuchFieldError -> 0x0014 }
                com.startapp.android.publish.ads.video.b.c$h r1 = com.startapp.android.publish.ads.video.p072b.C4860c.C4868h.SERVER_DIED     // Catch:{ NoSuchFieldError -> 0x0014 }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x0014 }
                r2 = 1
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x0014 }
            L_0x0014:
                int[] r0 = f2968a     // Catch:{ NoSuchFieldError -> 0x001f }
                com.startapp.android.publish.ads.video.b.c$h r1 = com.startapp.android.publish.ads.video.p072b.C4860c.C4868h.BUFFERING_TIMEOUT     // Catch:{ NoSuchFieldError -> 0x001f }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x001f }
                r2 = 2
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x001f }
            L_0x001f:
                int[] r0 = f2968a     // Catch:{ NoSuchFieldError -> 0x002a }
                com.startapp.android.publish.ads.video.b.c$h r1 = com.startapp.android.publish.ads.video.p072b.C4860c.C4868h.PLAYER_CREATION     // Catch:{ NoSuchFieldError -> 0x002a }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x002a }
                r2 = 3
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x002a }
            L_0x002a:
                int[] r0 = f2968a     // Catch:{ NoSuchFieldError -> 0x0035 }
                com.startapp.android.publish.ads.video.b.c$h r1 = com.startapp.android.publish.ads.video.p072b.C4860c.C4868h.UNKNOWN     // Catch:{ NoSuchFieldError -> 0x0035 }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x0035 }
                r2 = 4
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x0035 }
            L_0x0035:
                return
            */
            throw new UnsupportedOperationException("Method not decompiled: com.startapp.android.publish.ads.video.C4894f.C489913.<clinit>():void");
        }
    }

    /* renamed from: com.startapp.android.publish.ads.video.f$a */
    /* compiled from: StartAppSDK */
    private enum C4917a {
        PLAYER,
        POST_ROLL
    }

    /* renamed from: com.startapp.android.publish.ads.video.f$b */
    /* compiled from: StartAppSDK */
    private enum C4918b {
        ON,
        OFF
    }

    /* renamed from: com.startapp.android.publish.ads.video.f$c */
    /* compiled from: StartAppSDK */
    private enum C4919c {
        COMPLETE,
        CLICKED,
        SKIPPED
    }

    /* access modifiers changed from: protected */
    /* renamed from: z */
    public void mo61201z() {
    }

    /* renamed from: a */
    public void mo61151a(Bundle bundle) {
        String str = "currentPosition";
        super.mo61151a(bundle);
        try {
            this.f2941S = System.currentTimeMillis();
            this.f2923A = 100 / C4983b.m3032a().mo62154H().mo62431j();
            m2760ac();
            m2782ay();
            m2759ab();
            if (bundle != null && bundle.containsKey(str)) {
                this.f2951n = bundle.getInt(str);
                this.f2931I = bundle.getInt("latestPosition");
                this.f2934L = (HashMap) bundle.getSerializable("fractionProgressImpressionsSent");
                this.f2935M = (HashMap) bundle.getSerializable("absoluteProgressImpressionsSent");
                this.f2949l = bundle.getBoolean("isMuted");
                this.f2952o = bundle.getBoolean("shouldSetBg");
                this.f2950m = bundle.getInt("replayNum");
                this.f2933K = bundle.getBoolean("videoCompletedBroadcastSent", false);
                this.f2936N = bundle.getInt("pauseNum");
            }
        } catch (Exception unused) {
            m2778au();
            Context applicationContext = mo61159b().getApplicationContext();
            C5015d dVar = C5015d.EXCEPTION;
            StringBuilder sb = new StringBuilder();
            sb.append("packages : ");
            sb.append(mo61171j());
            C5017f.m3256a(applicationContext, dVar, "VideoMode.onCreate", sb.toString(), "");
            mo61177p();
        }
    }

    /* renamed from: a */
    public void mo61193a(WebView webView) {
        super.mo61193a(webView);
        webView.setBackgroundColor(33554431);
        C5146c.m3757a(webView, (Paint) null);
    }

    /* access modifiers changed from: protected */
    /* renamed from: A */
    public void mo61187A() {
        this.f2953p = true;
        if (this.f2954q && mo61789X()) {
            mo61772G();
        } else if (mo61784S()) {
            m2748a((View) this.f2489d);
        }
        if (mo61790Y()) {
            mo61776K();
        }
        if (mo61784S()) {
            m2762ae();
        }
        VideoAdDetails U = mo61786U();
        if (MetaData.getInstance().isOmsdkEnabled() && this.f2490e == null && U != null && U.getAdVerifications() != null && U.getAdVerifications().getAdVerification() != null) {
            this.f2490e = C5132a.m3699a(this.f2489d.getContext(), mo61786U().getAdVerifications());
            if (this.f2490e != null) {
                this.f2945W = C0962a.m105a(this.f2490e);
                View a = this.f2466a.mo62112a();
                if (a != null) {
                    this.f2490e.mo11478b(a);
                }
                this.f2490e.mo11478b(this.f2489d);
                this.f2490e.mo11478b(this.f2930H);
                this.f2490e.mo11476a(this.f2947j);
                this.f2490e.mo11475a();
                C0961a.m103a(this.f2490e).mo11464a();
            }
        }
    }

    /* access modifiers changed from: protected */
    /* renamed from: G */
    public void mo61772G() {
        if (this.f2955r) {
            m2748a((View) this.f2947j);
            if (!mo61784S()) {
                m2763af();
            }
        }
    }

    /* renamed from: u */
    public void mo61182u() {
        super.mo61182u();
        if (!mo61159b().isFinishing()) {
            m2758aa();
        }
    }

    /* renamed from: aa */
    private void m2758aa() {
        if (this.f2947j == null) {
            m2745a(mo61159b().getApplicationContext());
        }
        if (this.f2946i == null) {
            this.f2946i = new C4856b(this.f2947j);
        }
        this.f2954q = false;
        this.f2929G.setBackgroundColor(ViewCompat.MEASURED_STATE_MASK);
        mo61773H();
        if (mo61784S()) {
            this.f2466a.mo62112a().setVisibility(0);
            this.f2947j.setVisibility(4);
        } else {
            int i = this.f2951n;
            if (i != 0) {
                this.f2946i.mo61672a(i);
                m2787b(PauseOrigin.EXTERNAL);
            }
        }
        this.f2946i.mo61669a((C4866f) new C4866f() {
            /* renamed from: a */
            public void mo61687a() {
                C4894f fVar = C4894f.this;
                fVar.f2960w = true;
                if (fVar.f2953p && C4894f.this.f2954q) {
                    C4894f.this.mo61772G();
                }
                if (C4894f.this.mo61790Y()) {
                    C4894f.this.mo61776K();
                }
            }
        });
        this.f2946i.mo61667a((C4864d) new C4864d() {
            /* renamed from: a */
            public void mo61685a() {
                if (!C4894f.this.mo61784S()) {
                    C4894f.this.mo61793a(C4919c.COMPLETE);
                }
                C4894f.this.f2946i.mo61675c();
            }
        });
        C490014 r0 = new C4863c() {
            /* renamed from: a */
            public void mo61684a(int i) {
                if (C4894f.this.f2959v && C4894f.this.f2960w && C4894f.this.f2946i != null && C4894f.this.f2946i.mo61677e() != 0) {
                    StringBuilder sb = new StringBuilder();
                    sb.append("buffered percent = [");
                    sb.append(i);
                    sb.append("]");
                    C5155g.m3807a("VideoMode", 3, sb.toString());
                    C4894f fVar = C4894f.this;
                    fVar.f2958u = i;
                    int d = (fVar.f2946i.mo61676d() * 100) / C4894f.this.f2946i.mo61677e();
                    if (C4894f.this.mo61779N()) {
                        if (!C4894f.this.f2961x && C4894f.this.mo61790Y()) {
                            C4894f.this.mo61776K();
                        } else if (C4894f.this.f2958u == 100 || C4894f.this.f2958u - d > C4983b.m3032a().mo62154H().mo62431j()) {
                            C4894f.this.mo61774I();
                        }
                    } else if (C4894f.this.f2958u < 100 && C4894f.this.f2958u - d <= C4983b.m3032a().mo62154H().mo62432k()) {
                        C4894f.this.mo61775J();
                    }
                }
            }
        };
        this.f2946i.mo61668a((C4865e) new C4865e() {
            /* renamed from: a */
            public boolean mo61686a(C4867g gVar) {
                C4894f fVar = C4894f.this;
                fVar.f2960w = false;
                if (!fVar.f2959v || C4894f.this.f2963z > C4894f.this.f2923A || gVar.mo61690c() <= 0 || !gVar.mo61689b().equals(C4858a.MEDIA_ERROR_IO.toString())) {
                    C4894f.this.mo61792a(gVar);
                } else {
                    C4894f.this.f2963z++;
                    C4894f.this.mo61777L();
                    C4894f.this.f2946i.mo61670a(C4894f.this.mo61786U().getLocalVideoPath());
                    C4894f.this.f2946i.mo61672a(gVar.mo61690c());
                }
                return true;
            }
        });
        this.f2946i.mo61665a((C4862b) new C4862b() {
        });
        this.f2946i.mo61666a((C4863c) r0);
        this.f2946i.mo61664a((C4861a) new C4861a() {
        });
        C5146c.m3754a((View) this.f2947j, (C5149a) new C5149a() {
            /* renamed from: a */
            public void mo61802a(View view, int i, int i2, int i3, int i4, int i5, int i6, int i7, int i8) {
                C4894f fVar = C4894f.this;
                fVar.f2954q = true;
                if (fVar.f2953p && C4894f.this.mo61789X()) {
                    C4894f.this.mo61772G();
                }
            }
        });
    }

    /* access modifiers changed from: protected */
    /* renamed from: H */
    public void mo61773H() {
        boolean i = C4983b.m3032a().mo62154H().mo62430i();
        String localVideoPath = mo61786U().getLocalVideoPath();
        String str = "VideoMode";
        if (localVideoPath != null) {
            this.f2946i.mo61670a(localVideoPath);
            if (i && C4869c.m2627a().mo61694b(localVideoPath)) {
                C5155g.m3807a(str, 3, "progressive video from local file");
                this.f2959v = true;
                this.f2962y = true;
                this.f2958u = C4983b.m3032a().mo62154H().mo62432k();
            }
        } else if (i) {
            C5155g.m3807a(str, 3, "progressive video from url");
            String videoUrl = mo61786U().getVideoUrl();
            C4869c.m2627a().mo61693a(videoUrl);
            this.f2946i.mo61670a(videoUrl);
            this.f2959v = true;
            mo61777L();
        } else {
            mo61793a(C4919c.SKIPPED);
        }
        if (this.f2924B == null) {
            this.f2924B = this.f2959v ? ExifInterface.GPS_MEASUREMENT_2D : IcyHeaders.REQUEST_HEADER_ENABLE_METADATA_VALUE;
        }
    }

    /* access modifiers changed from: protected */
    /* renamed from: I */
    public void mo61774I() {
        StringBuilder sb = new StringBuilder();
        sb.append("progressive video resumed, buffered percent: [");
        sb.append(Integer.toString(this.f2958u));
        sb.append("]");
        C5155g.m3807a("VideoMode", 3, sb.toString());
        this.f2946i.mo61671a();
        mo61778M();
    }

    /* access modifiers changed from: protected */
    /* renamed from: J */
    public void mo61775J() {
        StringBuilder sb = new StringBuilder();
        sb.append("progressive video paused, buffered percent: [");
        sb.append(Integer.toString(this.f2958u));
        sb.append("]");
        C5155g.m3807a("VideoMode", 3, sb.toString());
        this.f2946i.mo61674b();
        mo61777L();
    }

    /* access modifiers changed from: protected */
    /* renamed from: K */
    public void mo61776K() {
        this.f2961x = true;
        m2764ag();
        if (mo61784S()) {
            this.f2946i.mo61674b();
            return;
        }
        new Handler().postDelayed(new Runnable() {
            public void run() {
                if (C4894f.this.f2946i != null) {
                    C4894f.this.f2946i.mo61671a();
                    if (C4894f.this.f2945W != null) {
                        C4894f.this.f2945W.mo11467a((float) C4894f.this.f2946i.mo61677e(), C4894f.this.f2949l ? 0.0f : 1.0f);
                    }
                    C4894f fVar = C4894f.this;
                    fVar.f2955r = true;
                    fVar.mo61778M();
                    new Handler().post(new Runnable() {
                        public void run() {
                            C4894f.this.mo61772G();
                        }
                    });
                }
            }
        }, m2761ad());
        if (this.f2951n == 0) {
            this.f2925C.postDelayed(new Runnable() {
                public void run() {
                    try {
                        if (C4894f.this.f2946i == null) {
                            return;
                        }
                        if (C4894f.this.f2946i.mo61676d() > 0) {
                            C4894f.this.mo61798f(0);
                            C4894f.this.mo61799g(0);
                            if (C4894f.this.f2950m == 0) {
                                C4894f.this.mo61791Z();
                                C5160b.m3831a((Context) C4894f.this.mo61159b()).mo62880a(new Intent("com.startapp.android.ShowDisplayBroadcastListener"));
                            }
                        } else if (!C4894f.this.f2956s) {
                            C4894f.this.f2925C.postDelayed(this, 100);
                        }
                    } catch (Exception e) {
                        C5017f.m3258a(C4894f.this.mo61159b().getApplicationContext(), new C5016e(C5015d.EXCEPTION, "VideoMode.startVideoPlayback", e.getMessage()), C4894f.this.m2781ax());
                        C4894f.this.mo61177p();
                    }
                }
            }, 100);
        }
        m2770am();
        m2773ap();
        m2765ah();
        m2766ai();
        this.f2466a.mo62112a().setVisibility(4);
        mo61788W();
    }

    /* access modifiers changed from: protected */
    /* renamed from: L */
    public void mo61777L() {
        if (!mo61779N()) {
            this.f2957t = false;
            this.f2928F.postDelayed(new Runnable() {
                public void run() {
                    try {
                        C4894f.this.f2948k.setVisibility(0);
                        if (C4894f.this.f2945W != null) {
                            C4894f.this.f2945W.mo11472f();
                        }
                        C4894f.this.f2928F.postDelayed(new Runnable() {
                            public void run() {
                                String str = "Buffering timeout reached";
                                C5155g.m3807a("VideoMode", 5, str);
                                try {
                                    C4894f.this.mo61778M();
                                    C4894f.this.f2957t = true;
                                    C4894f.this.mo61792a(new C4867g(C4868h.BUFFERING_TIMEOUT, str, C4894f.this.f2951n));
                                } catch (Exception e) {
                                    C5017f.m3258a(C4894f.this.mo61159b().getApplicationContext(), new C5016e(C5015d.EXCEPTION, "VideoMode.startBufferingIndicator", e.getMessage()), "");
                                }
                            }
                        }, C4983b.m3032a().mo62154H().mo62428g());
                    } catch (Exception e) {
                        C4894f.this.mo61778M();
                        C5017f.m3258a(C4894f.this.mo61159b().getApplicationContext(), new C5016e(C5015d.EXCEPTION, "VideoMode.startBufferingIndicator", e.getMessage()), C4894f.this.m2781ax());
                    }
                }
            }, C4983b.m3032a().mo62154H().mo62427f());
        }
    }

    /* access modifiers changed from: protected */
    /* renamed from: M */
    public void mo61778M() {
        this.f2928F.removeCallbacksAndMessages(null);
        if (mo61779N()) {
            this.f2948k.setVisibility(8);
            C0962a aVar = this.f2945W;
            if (aVar != null) {
                aVar.mo11473g();
            }
        }
    }

    /* access modifiers changed from: protected */
    /* renamed from: N */
    public boolean mo61779N() {
        ProgressBar progressBar = this.f2948k;
        return progressBar != null && progressBar.isShown();
    }

    /* renamed from: ab */
    private void m2759ab() {
        this.f2949l = mo61786U().isVideoMuted() || C4983b.m3032a().mo62154H().mo62434m().equals("muted");
    }

    /* renamed from: ac */
    private void m2760ac() {
        if (!mo61168g().equals("back")) {
            return;
        }
        if (C4983b.m3032a().mo62154H().mo62422a().equals(C5068a.BOTH)) {
            this.f2937O = true;
            this.f2938P = true;
        } else if (C4983b.m3032a().mo62154H().mo62422a().equals(C5068a.SKIP)) {
            this.f2937O = true;
            this.f2938P = false;
        } else if (C4983b.m3032a().mo62154H().mo62422a().equals(C5068a.CLOSE)) {
            this.f2937O = false;
            this.f2938P = true;
        } else if (C4983b.m3032a().mo62154H().mo62422a().equals(C5068a.DISABLED)) {
            this.f2937O = false;
            this.f2938P = false;
        } else {
            this.f2937O = false;
            this.f2938P = false;
        }
    }

    /* renamed from: ad */
    private long m2761ad() {
        long currentTimeMillis = System.currentTimeMillis() - this.f2941S;
        if (this.f2951n == 0 && this.f2950m == 0 && currentTimeMillis < 500) {
            return Math.max(200, 500 - currentTimeMillis);
        }
        return 0;
    }

    /* renamed from: ae */
    private void m2762ae() {
        Object[] objArr = new Object[1];
        objArr[0] = Boolean.valueOf(this.f2946i != null);
        mo61194a("videoApi.setReplayEnabled", objArr);
        StringBuilder sb = new StringBuilder();
        sb.append(C4917a.POST_ROLL);
        sb.append(EventsFilesManager.ROLL_OVER_FILE_NAME_SEPARATOR);
        sb.append(mo61786U().getPostRollType());
        mo61194a("videoApi.setMode", sb.toString());
        mo61194a("videoApi.setCloseable", Boolean.valueOf(true));
    }

    /* renamed from: af */
    private void m2763af() {
        mo61194a("videoApi.setClickableVideo", Boolean.valueOf(mo61786U().isClickable()));
        mo61194a("videoApi.setMode", C4917a.PLAYER.toString());
        Object[] objArr = new Object[1];
        objArr[0] = Boolean.valueOf(mo61786U().isCloseable() || this.f2938P);
        mo61194a("videoApi.setCloseable", objArr);
        mo61194a("videoApi.setSkippable", Boolean.valueOf(m2780aw()));
    }

    /* renamed from: ag */
    private void m2764ag() {
        mo61194a("videoApi.setVideoDuration", Integer.valueOf(this.f2946i.mo61677e() / 1000));
        mo61781P();
        m2767aj();
        mo61194a("videoApi.setVideoCurrentPosition", Integer.valueOf(this.f2951n / 1000));
    }

    /* access modifiers changed from: protected */
    /* renamed from: O */
    public void mo61780O() {
        Integer valueOf = Integer.valueOf(0);
        mo61194a("videoApi.setVideoCurrentPosition", valueOf);
        mo61194a("videoApi.setSkipTimer", valueOf);
    }

    /* renamed from: a */
    private void m2748a(View view) {
        mo61194a("videoApi.setVideoFrame", Integer.valueOf(C4945h.m2900b(mo61159b(), view.getLeft())), Integer.valueOf(C4945h.m2900b(mo61159b(), view.getTop())), Integer.valueOf(C4945h.m2900b(mo61159b(), view.getWidth())), Integer.valueOf(C4945h.m2900b(mo61159b(), view.getHeight())));
    }

    /* renamed from: ah */
    private void m2765ah() {
        this.f2926D.post(new Runnable() {
            public void run() {
                int P = C4894f.this.mo61781P();
                if (P >= 1000) {
                    C4894f.this.f2926D.postDelayed(this, C4894f.this.mo61795c(P));
                }
            }
        });
    }

    /* access modifiers changed from: protected */
    /* renamed from: P */
    public int mo61781P() {
        int ak = m2768ak();
        int i = ak / 1000;
        if (i > 0 && ak % 1000 < 100) {
            i--;
        }
        mo61194a("videoApi.setVideoRemainingTimer", Integer.valueOf(i));
        return ak;
    }

    /* renamed from: ai */
    private void m2766ai() {
        m2767aj();
        this.f2926D.post(new Runnable() {

            /* renamed from: b */
            private boolean f2981b;

            /* renamed from: c */
            private final int f2982c = C4894f.this.mo61797e(C4983b.m3032a().mo62154H().mo62425d());

            public void run() {
                try {
                    int d = C4894f.this.mo61796d(C4894f.this.f2946i.mo61676d() + 50);
                    if (d >= 0 && !this.f2981b) {
                        String str = "videoApi.setSkipTimer";
                        if (d != 0) {
                            if (C4894f.this.f2951n < C4894f.this.mo61786U().getSkippableAfter() * 1000) {
                                C4894f.this.mo61194a(str, Integer.valueOf(d));
                            }
                        }
                        this.f2981b = true;
                        C4894f.this.mo61194a(str, Integer.valueOf(0));
                    }
                    if (C4894f.this.f2959v && C4894f.this.f2946i.mo61676d() >= this.f2982c) {
                        C4894f.this.mo61785T();
                    }
                    int d2 = (C4894f.this.f2946i.mo61676d() + 50) / 1000;
                    C4894f.this.mo61194a("videoApi.setVideoCurrentPosition", Integer.valueOf(d2));
                    if (d2 < C4894f.this.f2946i.mo61677e() / 1000) {
                        C4894f.this.f2926D.postDelayed(this, C4894f.this.mo61782Q());
                    }
                } catch (Exception unused) {
                }
            }
        });
    }

    /* renamed from: aj */
    private void m2767aj() {
        mo61194a("videoApi.setSkipTimer", Integer.valueOf(mo61796d(this.f2951n + 50)));
    }

    /* renamed from: ak */
    private int m2768ak() {
        if (this.f2946i.mo61676d() != this.f2946i.mo61677e() || mo61784S()) {
            return this.f2946i.mo61677e() - this.f2946i.mo61676d();
        }
        return this.f2946i.mo61677e();
    }

    /* access modifiers changed from: protected */
    /* renamed from: c */
    public long mo61795c(int i) {
        int i2 = i % 1000;
        if (i2 == 0) {
            i2 = 1000;
        }
        return (long) (i2 + 50);
    }

    /* access modifiers changed from: protected */
    /* renamed from: Q */
    public long mo61782Q() {
        return (long) (1000 - (this.f2946i.mo61676d() % 1000));
    }

    /* access modifiers changed from: protected */
    /* renamed from: d */
    public int mo61796d(int i) {
        if (this.f2937O || this.f2950m > 0) {
            return 0;
        }
        int skippableAfter = (mo61786U().getSkippableAfter() * 1000) - i;
        if (skippableAfter <= 0) {
            return 0;
        }
        return (skippableAfter / 1000) + 1;
    }

    /* access modifiers changed from: protected */
    /* renamed from: a */
    public void mo61793a(C4919c cVar) {
        if (cVar == C4919c.COMPLETE) {
            C0962a aVar = this.f2945W;
            if (aVar != null) {
                aVar.mo11470d();
            }
        }
        if (cVar == C4919c.SKIPPED) {
            C0962a aVar2 = this.f2945W;
            if (aVar2 != null) {
                aVar2.mo11474h();
            }
        }
        if (cVar == C4919c.SKIPPED || cVar == C4919c.CLICKED) {
            this.f2925C.removeCallbacksAndMessages(null);
            this.f2927E.removeCallbacksAndMessages(null);
            C4860c cVar2 = this.f2946i;
            if (cVar2 != null) {
                this.f2931I = cVar2.mo61676d();
                this.f2946i.mo61674b();
            }
        } else {
            this.f2931I = this.f2932J;
            mo61785T();
        }
        this.f2926D.removeCallbacksAndMessages(null);
        this.f2934L.clear();
        this.f2935M.clear();
        if (cVar == C4919c.CLICKED) {
            m2769al();
            return;
        }
        if (mo61786U().getPostRollType() != PostRollType.NONE) {
            m2762ae();
            this.f2466a.mo62112a().setVisibility(0);
        }
        if (mo61786U().getPostRollType() == PostRollType.IMAGE) {
            new Handler().postDelayed(new Runnable() {
                public void run() {
                    if (!C4894f.this.f2957t) {
                        C4894f.this.f2947j.setVisibility(4);
                    }
                }
            }, 1000);
        } else if (mo61786U().getPostRollType() == PostRollType.NONE) {
            mo61177p();
        }
        m2769al();
        if (mo61786U().getPostRollType() != PostRollType.NONE) {
            m2783az();
        }
    }

    /* renamed from: al */
    private void m2769al() {
        this.f2951n = -1;
    }

    /* access modifiers changed from: protected */
    /* renamed from: R */
    public void mo61783R() {
        this.f2951n = 0;
    }

    /* access modifiers changed from: protected */
    /* renamed from: S */
    public boolean mo61784S() {
        return this.f2951n == -1;
    }

    /* renamed from: am */
    private void m2770am() {
        this.f2932J = this.f2946i.mo61677e();
        m2771an();
        m2772ao();
    }

    /* renamed from: an */
    private void m2771an() {
        for (Integer intValue : this.f2939Q.keySet()) {
            final int intValue2 = intValue.intValue();
            m2747a(mo61797e(intValue2), this.f2925C, (Runnable) new Runnable() {
                public void run() {
                    try {
                        C4894f.this.mo61798f(intValue2);
                    } catch (Exception e) {
                        C5017f.m3258a(C4894f.this.mo61159b().getApplicationContext(), new C5016e(C5015d.EXCEPTION, "VideoMode.scheduleFractionProgressEvents", e.getMessage()), C4894f.this.m2781ax());
                    }
                }
            });
        }
    }

    /* renamed from: ao */
    private void m2772ao() {
        for (Integer intValue : this.f2940R.keySet()) {
            final int intValue2 = intValue.intValue();
            m2747a(intValue2, this.f2925C, (Runnable) new Runnable() {
                public void run() {
                    try {
                        C4894f.this.mo61799g(intValue2);
                    } catch (Exception e) {
                        C5017f.m3258a(C4894f.this.mo61159b().getApplicationContext(), new C5016e(C5015d.EXCEPTION, "VideoMode.scheduleAbsoluteProgressEvents", e.getMessage()), C4894f.this.m2781ax());
                    }
                }
            });
        }
    }

    /* renamed from: ap */
    private void m2773ap() {
        if (!this.f2959v) {
            m2747a(mo61797e(C4983b.m3032a().mo62154H().mo62425d()), this.f2927E, (Runnable) new Runnable() {
                public void run() {
                    try {
                        C4894f.this.mo61785T();
                    } catch (Exception e) {
                        C5017f.m3258a(C4894f.this.mo61159b().getApplicationContext(), new C5016e(C5015d.EXCEPTION, "VideoMode.scheduleVideoListenerEvents", e.getMessage()), C4894f.this.m2781ax());
                    }
                }
            });
        }
    }

    /* renamed from: a */
    private void m2747a(int i, Handler handler, Runnable runnable) {
        int i2 = this.f2951n;
        if (i2 < i) {
            handler.postDelayed(runnable, (long) (i - i2));
        }
    }

    /* access modifiers changed from: protected */
    /* renamed from: e */
    public int mo61797e(int i) {
        return (this.f2932J * i) / 100;
    }

    /* access modifiers changed from: protected */
    /* renamed from: T */
    public void mo61785T() {
        if (m2774aq() && !this.f2933K && this.f2950m == 0) {
            this.f2933K = true;
            String str = "VideoMode";
            C5155g.m3807a(str, 3, "Sending rewarded video completion broadcast.");
            if (C5160b.m3831a((Context) mo61159b()).mo62880a(new Intent("com.startapp.android.OnVideoCompleted"))) {
                C5155g.m3807a(str, 3, "Rewarded video completion broadcast sent successfully.");
            }
            m2754aA();
        }
    }

    /* renamed from: aq */
    private boolean m2774aq() {
        return mo61184w().getType() == AdType.REWARDED_VIDEO;
    }

    /* renamed from: b */
    public void mo61161b(Bundle bundle) {
        super.mo61161b(bundle);
        bundle.putInt("currentPosition", this.f2951n);
        bundle.putInt("latestPosition", this.f2931I);
        bundle.putSerializable("fractionProgressImpressionsSent", this.f2934L);
        bundle.putSerializable("absoluteProgressImpressionsSent", this.f2935M);
        bundle.putBoolean("isMuted", this.f2949l);
        bundle.putBoolean("shouldSetBg", this.f2952o);
        bundle.putInt("replayNum", this.f2950m);
        bundle.putInt("pauseNum", this.f2936N);
        bundle.putBoolean("videoCompletedBroadcastSent", this.f2933K);
    }

    /* access modifiers changed from: protected */
    /* renamed from: U */
    public VideoAdDetails mo61786U() {
        return ((C4893e) mo61184w()).mo61770d();
    }

    /* renamed from: s */
    public void mo61180s() {
        if (!mo61784S() && !mo61159b().isFinishing() && !this.f2938P && !this.f2937O) {
            m2752a(PauseOrigin.EXTERNAL);
        }
        m2779av();
        this.f2925C.removeCallbacksAndMessages(null);
        this.f2926D.removeCallbacksAndMessages(null);
        this.f2927E.removeCallbacksAndMessages(null);
        mo61778M();
        this.f2952o = true;
        super.mo61180s();
    }

    /* renamed from: p */
    public void mo61177p() {
        super.mo61177p();
        if (this.f2962y) {
            C4869c.m2627a().mo61695c(mo61786U().getLocalVideoPath());
        }
    }

    /* access modifiers changed from: protected */
    /* renamed from: y */
    public JsInterface mo61200y() {
        VideoJsInterface videoJsInterface = new VideoJsInterface(mo61159b(), this.f2492g, this.f2492g, m2777at(), m2776as(), m2775ar(), new C5002b(mo61175n()), mo61157a(0));
        return videoJsInterface;
    }

    /* renamed from: ar */
    private Runnable m2775ar() {
        return new Runnable() {
            public void run() {
                C4894f fVar = C4894f.this;
                fVar.f2949l = !fVar.f2949l;
                C4894f.this.mo61788W();
                C4894f fVar2 = C4894f.this;
                fVar2.mo61794a(fVar2.f2949l);
            }
        };
    }

    /* renamed from: as */
    private Runnable m2776as() {
        return new Runnable() {
            public void run() {
                C4894f.this.mo61787V();
            }
        };
    }

    /* access modifiers changed from: protected */
    /* renamed from: V */
    public void mo61787V() {
        if (mo61779N()) {
            mo61778M();
        }
        mo61793a(C4919c.SKIPPED);
        m2755aB();
    }

    /* renamed from: at */
    private Runnable m2777at() {
        return new Runnable() {
            public void run() {
                C4894f.this.f2950m++;
                C4894f.this.f2947j.setVisibility(0);
                C4894f fVar = C4894f.this;
                fVar.f2952o = false;
                fVar.mo61783R();
                C4894f.this.mo61780O();
                C4894f.this.mo61773H();
            }
        };
    }

    /* renamed from: a */
    private RelativeLayout m2745a(Context context) {
        this.f2943U = System.currentTimeMillis();
        this.f2930H = (RelativeLayout) mo61159b().findViewById(AdsConstants.STARTAPP_AD_MAIN_LAYOUT_ID);
        LayoutParams layoutParams = new LayoutParams(-1, -1);
        this.f2947j = new VideoView(context);
        this.f2947j.setId(100);
        LayoutParams layoutParams2 = new LayoutParams(-1, -1);
        layoutParams2.addRule(13);
        this.f2948k = new ProgressBar(context, null, 16843399);
        this.f2948k.setVisibility(4);
        LayoutParams layoutParams3 = new LayoutParams(-2, -2);
        layoutParams3.addRule(14);
        layoutParams3.addRule(15);
        this.f2929G = new RelativeLayout(context);
        this.f2929G.setId(1475346436);
        mo61159b().setContentView(this.f2929G);
        this.f2929G.addView(this.f2947j, layoutParams2);
        this.f2929G.addView(this.f2930H, layoutParams);
        this.f2929G.addView(this.f2948k, layoutParams3);
        if (AdsConstants.m2854a().booleanValue()) {
            LayoutParams layoutParams4 = new LayoutParams(-2, -2);
            layoutParams4.addRule(12);
            layoutParams4.addRule(14);
            this.f2929G.addView(m2784b(context), layoutParams4);
        }
        this.f2466a.mo62112a().setVisibility(4);
        return this.f2929G;
    }

    /* renamed from: b */
    private View m2784b(Context context) {
        StringBuilder sb = new StringBuilder();
        StringBuilder sb2 = new StringBuilder();
        sb2.append("url=");
        sb2.append(mo61786U().getVideoUrl());
        sb.append(sb2.toString());
        TextView textView = new TextView(context);
        textView.setBackgroundColor(ViewCompat.MEASURED_STATE_MASK);
        C5146c.m3752a((View) textView, 0.5f);
        textView.setTextColor(-7829368);
        textView.setSingleLine(false);
        textView.setText(sb.toString());
        return textView;
    }

    /* access modifiers changed from: protected */
    /* renamed from: W */
    public void mo61788W() {
        String str;
        C4860c cVar = this.f2946i;
        if (cVar != null) {
            try {
                if (this.f2949l) {
                    cVar.mo61673a(true);
                } else {
                    cVar.mo61673a(false);
                }
            } catch (IllegalStateException unused) {
            }
        }
        Object[] objArr = new Object[1];
        if (this.f2949l) {
            str = C4918b.OFF.toString();
        } else {
            str = C4918b.ON.toString();
        }
        objArr[0] = str;
        mo61194a("videoApi.setSound", objArr);
    }

    /* renamed from: a */
    private void m2752a(PauseOrigin pauseOrigin) {
        C4860c cVar = this.f2946i;
        if (cVar != null) {
            int d = cVar.mo61676d();
            this.f2951n = d;
            this.f2931I = d;
            this.f2946i.mo61674b();
            C0962a aVar = this.f2945W;
            if (aVar != null) {
                aVar.mo11471e();
            }
        }
        m2789c(pauseOrigin);
    }

    /* renamed from: b */
    private void m2787b(PauseOrigin pauseOrigin) {
        m2790d(pauseOrigin);
        this.f2936N++;
    }

    /* access modifiers changed from: protected */
    /* renamed from: a */
    public void mo61792a(C4867g gVar) {
        C4874a aVar;
        C5017f.m3256a(mo61159b(), C5015d.VIDEO_MEDIA_PLAYER_ERROR, gVar.mo61688a().toString(), gVar.mo61689b(), m2781ax());
        int i = C489913.f2968a[gVar.mo61688a().ordinal()];
        if (i == 1) {
            aVar = C4874a.GeneralLinearError;
        } else if (i == 2) {
            aVar = C4874a.TimeoutMediaFileURI;
        } else if (i != 3) {
            aVar = C4874a.UndefinedError;
        } else {
            aVar = C4874a.MediaFileDisplayError;
        }
        m2749a(aVar);
        if ((this.f2959v ? this.f2946i.mo61676d() : this.f2951n) == 0) {
            C4988c.m3111a((Context) mo61159b(), mo61169h(), mo61175n(), this.f2950m, NotDisplayedReason.VIDEO_ERROR.toString());
            if (!this.f2959v) {
                C4923h.m2848b(mo61159b());
            } else if (!gVar.mo61688a().equals(C4868h.BUFFERING_TIMEOUT)) {
                C4923h.m2848b(mo61159b());
            }
        }
        if ((!m2774aq() || this.f2933K) && mo61786U().getPostRollType() != PostRollType.NONE) {
            mo61793a(C4919c.SKIPPED);
            return;
        }
        m2778au();
        mo61177p();
    }

    /* renamed from: au */
    private void m2778au() {
        Intent intent = new Intent("com.startapp.android.ShowFailedDisplayBroadcastListener");
        intent.putExtra("showFailedReason", NotDisplayedReason.VIDEO_ERROR);
        C5160b.m3831a((Context) mo61159b()).mo62880a(intent);
        this.f2956s = true;
    }

    /* access modifiers changed from: protected */
    /* renamed from: X */
    public boolean mo61789X() {
        C4860c cVar = this.f2946i;
        return cVar != null && cVar.mo61678f();
    }

    /* access modifiers changed from: protected */
    /* renamed from: Y */
    public boolean mo61790Y() {
        boolean z = true;
        if (!this.f2959v) {
            if (!mo61789X() || !this.f2953p) {
                z = false;
            }
            return z;
        }
        if (this.f2958u < C4983b.m3032a().mo62154H().mo62432k() || !mo61789X() || !this.f2953p) {
            z = false;
        }
        return z;
    }

    /* renamed from: av */
    private void m2779av() {
        C5155g.m3807a("VideoMode", 3, "Releasing video player");
        C4860c cVar = this.f2946i;
        if (cVar != null) {
            cVar.mo61679g();
            this.f2946i = null;
        }
    }

    /* renamed from: r */
    public boolean mo61179r() {
        if (mo61784S()) {
            mo61188B();
            return false;
        }
        int d = mo61796d(this.f2946i.mo61676d() + 50);
        if (m2780aw() && d == 0) {
            mo61787V();
            return true;
        } else if (!mo61786U().isCloseable() && !this.f2938P) {
            return true;
        } else {
            mo61188B();
            return false;
        }
    }

    /* renamed from: aw */
    private boolean m2780aw() {
        return this.f2950m > 0 || mo61786U().isSkippable() || this.f2937O;
    }

    /* renamed from: h */
    private int m2791h(int i) {
        int i2 = this.f2932J;
        if (i2 > 0) {
            return (i * 100) / i2;
        }
        return 0;
    }

    /* access modifiers changed from: private */
    /* renamed from: ax */
    public String m2781ax() {
        try {
            String[] h = mo61169h();
            if (h != null && h.length > 0) {
                return C4988c.m3133e(h[0]);
            }
            C5155g.m3807a("VideoMode", 5, "dParam is not available.");
            return "";
        } catch (Exception unused) {
        }
    }

    /* renamed from: q */
    public void mo61178q() {
        if (!this.f2956s) {
            super.mo61178q();
        }
    }

    /* access modifiers changed from: protected */
    /* renamed from: a */
    public boolean mo61195a(String str, boolean z) {
        if (!TextUtils.isEmpty(mo61786U().getClickUrl())) {
            str = mo61786U().getClickUrl();
            z = true;
        }
        this.f2942T = mo61784S() ? ClickOrigin.POSTROLL : ClickOrigin.VIDEO;
        StringBuilder sb = new StringBuilder();
        sb.append("Video clicked from: ");
        sb.append(this.f2942T);
        C5155g.m3807a("VideoMode", 3, sb.toString());
        if (this.f2942T == ClickOrigin.VIDEO) {
            mo61793a(C4919c.CLICKED);
        }
        m2751a(this.f2942T);
        return super.mo61195a(str, z);
    }

    /* access modifiers changed from: protected */
    /* renamed from: B */
    public void mo61188B() {
        if (this.f2956s) {
            C5155g.m3807a("VideoMode", 3, "Not sending close events due to media player error");
        } else if (mo61784S() || this.f2947j == null) {
            m2756aC();
            super.mo61188B();
        } else {
            m2757aD();
        }
    }

    /* access modifiers changed from: protected */
    /* renamed from: E */
    public String mo61191E() {
        this.f2944V = System.currentTimeMillis();
        double d = (double) (this.f2944V - this.f2943U);
        Double.isNaN(d);
        return String.valueOf(d / 1000.0d);
    }

    /* renamed from: ay */
    private void m2782ay() {
        FractionTrackingLink[] fractionTrackingUrls = mo61786U().getVideoTrackingDetails().getFractionTrackingUrls();
        if (fractionTrackingUrls != null) {
            for (FractionTrackingLink fractionTrackingLink : fractionTrackingUrls) {
                List list = (List) this.f2939Q.get(Integer.valueOf(fractionTrackingLink.getFraction()));
                if (list == null) {
                    list = new ArrayList();
                    this.f2939Q.put(Integer.valueOf(fractionTrackingLink.getFraction()), list);
                }
                list.add(fractionTrackingLink);
            }
        }
        AbsoluteTrackingLink[] absoluteTrackingUrls = mo61786U().getVideoTrackingDetails().getAbsoluteTrackingUrls();
        if (absoluteTrackingUrls != null) {
            for (AbsoluteTrackingLink absoluteTrackingLink : absoluteTrackingUrls) {
                List list2 = (List) this.f2940R.get(Integer.valueOf(absoluteTrackingLink.getVideoOffsetMillis()));
                if (list2 == null) {
                    list2 = new ArrayList();
                    this.f2940R.put(Integer.valueOf(absoluteTrackingLink.getVideoOffsetMillis()), list2);
                }
                list2.add(absoluteTrackingLink);
            }
        }
    }

    /* access modifiers changed from: protected */
    /* renamed from: D */
    public C5002b mo61190D() {
        return new VideoTrackingParams(mo61175n(), 0, this.f2950m, this.f2924B);
    }

    /* access modifiers changed from: protected */
    /* renamed from: F */
    public long mo61192F() {
        if (mo61176o() != null) {
            return TimeUnit.SECONDS.toMillis(mo61176o().longValue());
        }
        return TimeUnit.SECONDS.toMillis(MetaData.getInstance().getIABVideoImpressionDelayInSeconds());
    }

    /* access modifiers changed from: protected */
    /* renamed from: Z */
    public void mo61791Z() {
        super.mo61201z();
        m2753a(mo61786U().getVideoTrackingDetails().getImpressionUrls(), new VideoTrackingParams(mo61175n(), 0, this.f2950m, this.f2924B), 0, "impression");
        m2753a(mo61786U().getVideoTrackingDetails().getCreativeViewUrls(), new VideoTrackingParams(mo61175n(), 0, this.f2950m, this.f2924B), 0, "creativeView");
    }

    /* access modifiers changed from: protected */
    /* renamed from: f */
    public void mo61798f(int i) {
        String str = "VideoMode";
        if (this.f2934L.get(Integer.valueOf(i)) == null) {
            if (this.f2939Q.containsKey(Integer.valueOf(i))) {
                List list = (List) this.f2939Q.get(Integer.valueOf(i));
                StringBuilder sb = new StringBuilder();
                sb.append("Sending fraction progress event with fraction: ");
                sb.append(i);
                sb.append(", total: ");
                sb.append(list.size());
                C5155g.m3807a(str, 3, sb.toString());
                m2753a((VideoTrackingLink[]) list.toArray(new FractionTrackingLink[list.size()]), new VideoProgressTrackingParams(mo61175n(), i, this.f2950m, this.f2924B), mo61797e(i), "fraction");
                C0962a aVar = this.f2945W;
                if (aVar != null) {
                    if (i == 25) {
                        aVar.mo11465a();
                    } else if (i == 50) {
                        aVar.mo11468b();
                    } else if (i == 75) {
                        aVar.mo11469c();
                    }
                }
            }
            this.f2934L.put(Integer.valueOf(i), Boolean.valueOf(true));
            return;
        }
        StringBuilder sb2 = new StringBuilder();
        sb2.append("Fraction progress event already sent for fraction: ");
        sb2.append(i);
        C5155g.m3807a(str, 3, sb2.toString());
    }

    /* access modifiers changed from: protected */
    /* renamed from: g */
    public void mo61799g(int i) {
        String str = "VideoMode";
        if (this.f2935M.get(Integer.valueOf(i)) == null) {
            if (this.f2940R.containsKey(Integer.valueOf(i))) {
                List list = (List) this.f2940R.get(Integer.valueOf(i));
                StringBuilder sb = new StringBuilder();
                sb.append("Sending absolute progress event with video progress: ");
                sb.append(i);
                sb.append(", total: ");
                sb.append(list.size());
                C5155g.m3807a(str, 3, sb.toString());
                m2753a((VideoTrackingLink[]) list.toArray(new AbsoluteTrackingLink[list.size()]), new VideoProgressTrackingParams(mo61175n(), i, this.f2950m, this.f2924B), i, "absolute");
            }
            this.f2935M.put(Integer.valueOf(i), Boolean.valueOf(true));
            return;
        }
        StringBuilder sb2 = new StringBuilder();
        sb2.append("Absolute progress event already sent for video progress: ");
        sb2.append(i);
        C5155g.m3807a(str, 3, sb2.toString());
    }

    /* renamed from: az */
    private void m2783az() {
        C5155g.m3807a("VideoMode", 3, "Sending postroll impression event");
        m2753a(mo61786U().getVideoTrackingDetails().getVideoPostRollImpressionUrls(), new VideoTrackingParams(mo61175n(), m2791h(this.f2931I), this.f2950m, this.f2924B), this.f2931I, "postrollImression");
    }

    /* renamed from: aA */
    private void m2754aA() {
        C5155g.m3807a("VideoMode", 3, "Sending rewarded event");
        m2753a(mo61786U().getVideoTrackingDetails().getVideoRewardedUrls(), new VideoTrackingParams(mo61175n(), C4983b.m3032a().mo62154H().mo62425d(), this.f2950m, this.f2924B), mo61797e(C4983b.m3032a().mo62154H().mo62425d()), "rewarded");
    }

    /* access modifiers changed from: protected */
    /* renamed from: a */
    public void mo61794a(boolean z) {
        ActionTrackingLink[] actionTrackingLinkArr;
        StringBuilder sb = new StringBuilder();
        sb.append("Sending sound ");
        sb.append(z ? "muted " : "unmuted ");
        sb.append("event");
        C5155g.m3807a("VideoMode", 3, sb.toString());
        if (z) {
            actionTrackingLinkArr = mo61786U().getVideoTrackingDetails().getSoundMuteUrls();
        } else {
            actionTrackingLinkArr = mo61786U().getVideoTrackingDetails().getSoundUnmuteUrls();
        }
        m2753a(actionTrackingLinkArr, new VideoTrackingParams(mo61175n(), m2791h(this.f2946i.mo61676d()), this.f2950m, this.f2924B), this.f2946i.mo61676d(), "sound");
        C0962a aVar = this.f2945W;
        if (aVar != null) {
            aVar.mo11466a(z ? 0.0f : 1.0f);
        }
    }

    /* renamed from: aB */
    private void m2755aB() {
        C5155g.m3807a("VideoMode", 3, "Sending skip event");
        m2753a(mo61786U().getVideoTrackingDetails().getVideoSkippedUrls(), new VideoTrackingParams(mo61175n(), m2791h(this.f2931I), this.f2950m, this.f2924B), this.f2931I, "skipped");
    }

    /* renamed from: c */
    private void m2789c(PauseOrigin pauseOrigin) {
        StringBuilder sb = new StringBuilder();
        sb.append("Sending pause event with origin: ");
        sb.append(pauseOrigin);
        C5155g.m3807a("VideoMode", 3, sb.toString());
        ActionTrackingLink[] videoPausedUrls = mo61786U().getVideoTrackingDetails().getVideoPausedUrls();
        VideoPausedTrackingParams videoPausedTrackingParams = new VideoPausedTrackingParams(mo61175n(), m2791h(this.f2931I), this.f2950m, this.f2936N, pauseOrigin, this.f2924B);
        m2753a(videoPausedUrls, videoPausedTrackingParams, this.f2931I, "paused");
    }

    /* renamed from: d */
    private void m2790d(PauseOrigin pauseOrigin) {
        StringBuilder sb = new StringBuilder();
        sb.append("Sending resume event with pause origin: ");
        sb.append(pauseOrigin);
        C5155g.m3807a("VideoMode", 3, sb.toString());
        ActionTrackingLink[] videoResumedUrls = mo61786U().getVideoTrackingDetails().getVideoResumedUrls();
        VideoPausedTrackingParams videoPausedTrackingParams = new VideoPausedTrackingParams(mo61175n(), m2791h(this.f2931I), this.f2950m, this.f2936N, pauseOrigin, this.f2924B);
        m2753a(videoResumedUrls, videoPausedTrackingParams, this.f2931I, "resumed");
    }

    /* renamed from: aC */
    private void m2756aC() {
        C5155g.m3807a("VideoMode", 3, "Sending postroll closed event");
        m2753a(mo61786U().getVideoTrackingDetails().getVideoPostRollClosedUrls(), new VideoTrackingParams(mo61175n(), m2791h(this.f2931I), this.f2950m, this.f2924B), this.f2931I, "postrollClosed");
    }

    /* renamed from: aD */
    private void m2757aD() {
        C5155g.m3807a("VideoMode", 3, "Sending video closed event");
        m2753a(mo61786U().getVideoTrackingDetails().getVideoClosedUrls(), new VideoTrackingParams(mo61175n(), m2791h(this.f2946i.mo61676d()), this.f2950m, this.f2924B), this.f2946i.mo61676d(), "closed");
    }

    /* renamed from: a */
    private void m2751a(ClickOrigin clickOrigin) {
        StringBuilder sb = new StringBuilder();
        sb.append("Sending video clicked event with origin: ");
        sb.append(clickOrigin.toString());
        C5155g.m3807a("VideoMode", 3, sb.toString());
        ActionTrackingLink[] videoClickTrackingUrls = mo61786U().getVideoTrackingDetails().getVideoClickTrackingUrls();
        VideoClickedTrackingParams videoClickedTrackingParams = new VideoClickedTrackingParams(mo61175n(), m2791h(this.f2931I), this.f2950m, clickOrigin, this.f2924B);
        m2753a(videoClickTrackingUrls, videoClickedTrackingParams, this.f2931I, "clicked");
    }

    /* renamed from: a */
    private void m2753a(VideoTrackingLink[] videoTrackingLinkArr, VideoTrackingParams videoTrackingParams, int i, String str) {
        C4923h.m2846a((Context) mo61159b(), new C4851b(videoTrackingLinkArr, videoTrackingParams, mo61786U().getVideoUrl(), i).mo61658a(str).mo61656a());
    }

    /* renamed from: a */
    private void m2749a(C4874a aVar) {
        C5155g.m3807a("VideoMode", 3, "Sending internal video event");
        C4923h.m2846a((Context) mo61159b(), new C4851b(mo61786U().getVideoTrackingDetails().getInlineErrorTrackingUrls(), new VideoTrackingParams(mo61175n(), m2791h(this.f2931I), this.f2950m, this.f2924B), mo61786U().getVideoUrl(), this.f2931I).mo61657a(aVar).mo61658a("error").mo61656a());
    }
}
