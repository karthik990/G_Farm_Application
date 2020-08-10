package com.startapp.android.publish.ads.list3d;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.GradientDrawable.Orientation;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.text.TextUtils.TruncateAt;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebView;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.FrameLayout.LayoutParams;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.mobiroller.constants.Constants;
import com.startapp.android.publish.adsCommon.AdsConstants;
import com.startapp.android.publish.adsCommon.C4983b;
import com.startapp.android.publish.adsCommon.C4988c;
import com.startapp.android.publish.adsCommon.C5059m;
import com.startapp.android.publish.adsCommon.Utils.C4934a;
import com.startapp.android.publish.adsCommon.Utils.C4945h;
import com.startapp.android.publish.adsCommon.Utils.C4946i;
import com.startapp.android.publish.adsCommon.adinformation.C4969b;
import com.startapp.android.publish.adsCommon.adinformation.C4969b.C4977b;
import com.startapp.android.publish.adsCommon.adinformation.C4978c;
import com.startapp.android.publish.adsCommon.p080d.C5001a;
import com.startapp.android.publish.adsCommon.p080d.C5002b;
import com.startapp.android.publish.adsCommon.p082f.C5015d;
import com.startapp.android.publish.adsCommon.p082f.C5017f;
import com.startapp.android.publish.common.model.AdPreferences.Placement;
import com.startapp.common.C5160b;
import com.startapp.common.p092a.C5155g;
import java.util.List;

/* compiled from: StartAppSDK */
public class List3DActivity extends Activity implements C4812g {

    /* renamed from: a */
    String f2564a;

    /* renamed from: b */
    String f2565b;

    /* renamed from: c */
    List<ListItem> f2566c;

    /* renamed from: d */
    private C4803c f2567d;

    /* renamed from: e */
    private ProgressDialog f2568e = null;

    /* renamed from: f */
    private WebView f2569f = null;

    /* renamed from: g */
    private int f2570g;

    /* renamed from: h */
    private C4969b f2571h;

    /* renamed from: i */
    private Long f2572i;

    /* renamed from: j */
    private Long f2573j;

    /* renamed from: k */
    private String f2574k;

    /* renamed from: l */
    private long f2575l = 0;

    /* renamed from: m */
    private long f2576m = 0;

    /* renamed from: n */
    private BroadcastReceiver f2577n = new BroadcastReceiver() {
        public void onReceive(Context context, Intent intent) {
            List3DActivity.this.finish();
        }
    };

    public void onCreate(Bundle bundle) {
        String str;
        View view;
        Bundle bundle2 = bundle;
        String str2 = "";
        try {
            overridePendingTransition(0, 0);
            super.onCreate(bundle);
            if (getIntent().getBooleanExtra("fullscreen", false)) {
                requestWindowFeature(1);
                getWindow().setFlags(1024, 1024);
            }
            String str3 = "adCacheTtl";
            String str4 = "lastLoadTime";
            if (bundle2 == null) {
                C5160b.m3831a((Context) this).mo62880a(new Intent("com.startapp.android.ShowDisplayBroadcastListener"));
                this.f2572i = (Long) getIntent().getSerializableExtra(str4);
                this.f2573j = (Long) getIntent().getSerializableExtra(str3);
            } else {
                if (bundle2.containsKey(str4)) {
                    this.f2572i = (Long) bundle2.getSerializable(str4);
                }
                if (bundle2.containsKey(str3)) {
                    this.f2573j = (Long) bundle2.getSerializable(str3);
                }
            }
            this.f2574k = getIntent().getStringExtra(Constants.KEY_RSS_POSITION);
            this.f2564a = getIntent().getStringExtra("listModelUuid");
            C5160b.m3831a((Context) this).mo62879a(this.f2577n, new IntentFilter("com.startapp.android.CloseAdActivity"));
            this.f2570g = getResources().getConfiguration().orientation;
            C4946i.m2911a((Activity) this, true);
            boolean booleanExtra = getIntent().getBooleanExtra("overlay", false);
            requestWindowFeature(1);
            this.f2565b = getIntent().getStringExtra("adTag");
            int e = C4983b.m3032a().mo62165e();
            int f = C4983b.m3032a().mo62166f();
            this.f2567d = new C4803c(this, null, this.f2565b, this.f2564a);
            this.f2567d.setBackgroundDrawable(new GradientDrawable(Orientation.TOP_BOTTOM, new int[]{e, f}));
            this.f2566c = C4811f.m2473a().mo61493a(this.f2564a).mo61492e();
            if (this.f2566c == null) {
                finish();
                return;
            }
            if (booleanExtra) {
                C5160b.m3831a((Context) this).mo62879a(this.f2567d.f2638p, new IntentFilter("com.startapp.android.Activity3DGetValues"));
                str = str2;
            } else {
                this.f2567d.mo61449a();
                this.f2567d.setHint(true);
                this.f2567d.setFade(true);
                str = "back";
            }
            C4802b bVar = new C4802b(this, this.f2566c, str, this.f2565b, this.f2564a);
            C4811f.m2473a().mo61493a(this.f2564a).mo61485a(this, !booleanExtra);
            this.f2567d.setAdapter(bVar);
            this.f2567d.setDynamics(new SimpleDynamics(0.9f, 0.6f));
            this.f2567d.setOnItemClickListener(new OnItemClickListener() {
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long j) {
                    int i2 = i;
                    String b = ((ListItem) List3DActivity.this.f2566c.get(i2)).mo61405b();
                    String e = ((ListItem) List3DActivity.this.f2566c.get(i2)).mo61409e();
                    String f = ((ListItem) List3DActivity.this.f2566c.get(i2)).mo61410f();
                    boolean l = ((ListItem) List3DActivity.this.f2566c.get(i2)).mo61416l();
                    boolean m = ((ListItem) List3DActivity.this.f2566c.get(i2)).mo61417m();
                    String p = ((ListItem) List3DActivity.this.f2566c.get(i2)).mo61420p();
                    String o = ((ListItem) List3DActivity.this.f2566c.get(i2)).mo61419o();
                    Boolean s = ((ListItem) List3DActivity.this.f2566c.get(i2)).mo61423s();
                    C4811f.m2473a().mo61493a(List3DActivity.this.f2564a).mo61487a(((ListItem) List3DActivity.this.f2566c.get(i2)).mo61406c());
                    if (p != null && !TextUtils.isEmpty(p)) {
                        m2389a(p, o, b, e);
                    } else if (!TextUtils.isEmpty(b)) {
                        boolean a = C4988c.m3118a(List3DActivity.this.getApplicationContext(), Placement.INAPP_OFFER_WALL);
                        if (!l || a) {
                            List3DActivity list3DActivity = List3DActivity.this;
                            C4988c.m3105a(list3DActivity, b, e, list3DActivity.mo61389a(), m && !a, false);
                            List3DActivity.this.finish();
                            return;
                        }
                        List3DActivity list3DActivity2 = List3DActivity.this;
                        C4988c.m3108a(list3DActivity2, b, e, f, list3DActivity2.mo61389a(), C4983b.m3032a().mo62147A(), C4983b.m3032a().mo62148B(), m, s, false, new Runnable() {
                            public void run() {
                                List3DActivity.this.finish();
                            }
                        });
                    }
                }

                /* renamed from: a */
                private void m2389a(String str, String str2, String str3, String str4) {
                    List3DActivity list3DActivity = List3DActivity.this;
                    C4988c.m3114a(str, str2, str3, (Context) list3DActivity, new C5002b(list3DActivity.f2565b));
                    List3DActivity.this.finish();
                }
            });
            RelativeLayout relativeLayout = new RelativeLayout(this);
            relativeLayout.setContentDescription("StartApp Ad");
            relativeLayout.setId(AdsConstants.STARTAPP_AD_MAIN_LAYOUT_ID);
            LayoutParams layoutParams = new LayoutParams(-1, -1);
            RelativeLayout.LayoutParams layoutParams2 = new RelativeLayout.LayoutParams(-1, -1);
            LinearLayout linearLayout = new LinearLayout(this);
            linearLayout.setOrientation(1);
            relativeLayout.addView(linearLayout, layoutParams2);
            RelativeLayout relativeLayout2 = new RelativeLayout(this);
            relativeLayout2.setLayoutParams(new RelativeLayout.LayoutParams(-1, -2));
            relativeLayout2.setBackgroundColor(C4983b.m3032a().mo62168h().intValue());
            linearLayout.addView(relativeLayout2);
            TextView textView = new TextView(this);
            RelativeLayout.LayoutParams layoutParams3 = new RelativeLayout.LayoutParams(-2, -2);
            layoutParams3.addRule(13);
            textView.setLayoutParams(layoutParams3);
            textView.setPadding(0, C4945h.m2891a((Context) this, 2), 0, C4945h.m2891a((Context) this, 5));
            textView.setTextColor(C4983b.m3032a().mo62171k().intValue());
            textView.setTextSize((float) C4983b.m3032a().mo62170j().intValue());
            textView.setSingleLine(true);
            textView.setEllipsize(TruncateAt.END);
            textView.setText(C4983b.m3032a().mo62169i());
            textView.setShadowLayer(2.5f, -2.0f, 2.0f, -11513776);
            C4945h.m2899a(textView, C4983b.m3032a().mo62172l());
            relativeLayout2.addView(textView);
            RelativeLayout.LayoutParams layoutParams4 = new RelativeLayout.LayoutParams(-2, -2);
            layoutParams4.addRule(11);
            layoutParams4.addRule(15);
            Bitmap a = C4934a.m2858a(this, "close_button.png");
            if (a != null) {
                view = new ImageButton(this, null, 16973839);
                ((ImageButton) view).setImageBitmap(Bitmap.createScaledBitmap(a, C4945h.m2891a((Context) this, 36), C4945h.m2891a((Context) this, 36), true));
            } else {
                view = new TextView(this);
                ((TextView) view).setText("   x   ");
                ((TextView) view).setTextSize(20.0f);
            }
            view.setLayoutParams(layoutParams4);
            view.setOnClickListener(new OnClickListener() {
                public void onClick(View view) {
                    List3DActivity list3DActivity = List3DActivity.this;
                    C4988c.m3124b((Context) list3DActivity, list3DActivity.mo61391b(), List3DActivity.this.mo61389a());
                    List3DActivity.this.finish();
                }
            });
            view.setContentDescription("x");
            view.setId(AdsConstants.LIST_3D_CLOSE_BUTTON_ID);
            relativeLayout2.addView(view);
            View view2 = new View(this);
            view2.setLayoutParams(new LinearLayout.LayoutParams(-1, C4945h.m2891a((Context) this, 2)));
            view2.setBackgroundColor(C4983b.m3032a().mo62173m().intValue());
            linearLayout.addView(view2);
            LinearLayout.LayoutParams layoutParams5 = new LinearLayout.LayoutParams(-1, 0);
            layoutParams5.weight = 1.0f;
            this.f2567d.setLayoutParams(layoutParams5);
            linearLayout.addView(this.f2567d);
            LinearLayout linearLayout2 = new LinearLayout(this);
            LinearLayout.LayoutParams layoutParams6 = new LinearLayout.LayoutParams(-1, -2);
            layoutParams6.gravity = 80;
            linearLayout2.setLayoutParams(layoutParams6);
            linearLayout2.setBackgroundColor(C4983b.m3032a().mo62182v().intValue());
            linearLayout2.setGravity(17);
            linearLayout.addView(linearLayout2);
            TextView textView2 = new TextView(this);
            textView2.setTextColor(C4983b.m3032a().mo62183w().intValue());
            textView2.setPadding(0, C4945h.m2891a((Context) this, 2), 0, C4945h.m2891a((Context) this, 3));
            textView2.setText("Powered By ");
            textView2.setTextSize(16.0f);
            linearLayout2.addView(textView2);
            ImageView imageView = new ImageView(this);
            imageView.setImageBitmap(Bitmap.createScaledBitmap(C4934a.m2858a(this, "logo.png"), C4945h.m2891a((Context) this, 56), C4945h.m2891a((Context) this, 12), true));
            linearLayout2.addView(imageView);
            this.f2571h = new C4969b(this, C4977b.LARGE, Placement.INAPP_OFFER_WALL, (C4978c) getIntent().getSerializableExtra("adInfoOverride"));
            this.f2571h.mo62113a(relativeLayout);
            setContentView(relativeLayout, layoutParams);
            new Handler().postDelayed(new Runnable() {
                public void run() {
                    List3DActivity.this.sendBroadcast(new Intent("android.intent.action.CLOSE_SYSTEM_DIALOGS"));
                }
            }, 500);
        } catch (Throwable th) {
            String str5 = "List3DActivity.onCreate";
            C5155g.m3808a("List3DActivity", 6, str5, th);
            C5017f.m3256a(this, C5015d.EXCEPTION, str5, th.getMessage(), str2);
            finish();
        }
    }

    /* access modifiers changed from: protected */
    /* renamed from: a */
    public C5002b mo61389a() {
        this.f2575l = System.currentTimeMillis();
        double d = (double) (this.f2575l - this.f2576m);
        Double.isNaN(d);
        return new C5001a(String.valueOf(d / 1000.0d), this.f2565b);
    }

    /* access modifiers changed from: protected */
    /* renamed from: b */
    public String mo61391b() {
        List<ListItem> list = this.f2566c;
        String str = "";
        return (list == null || list.isEmpty() || ((ListItem) this.f2566c.get(0)).mo61407d() == null) ? str : ((ListItem) this.f2566c.get(0)).mo61407d();
    }

    /* access modifiers changed from: protected */
    public void onResume() {
        super.onResume();
        if (m2385d()) {
            C5155g.m3807a("List3DActivity", 3, "Cache TTL passed, finishing");
            finish();
            return;
        }
        C5059m.m3378a().mo62377a(true);
        this.f2576m = System.currentTimeMillis();
        C4811f.m2473a().mo61493a(this.f2564a).mo61490c();
    }

    public void onBackPressed() {
        C4811f.m2473a().mo61493a(this.f2564a).mo61491d();
        super.onBackPressed();
    }

    /* access modifiers changed from: protected */
    public void onDestroy() {
        ProgressDialog progressDialog = this.f2568e;
        if (progressDialog != null) {
            synchronized (progressDialog) {
                if (this.f2568e != null) {
                    this.f2568e.dismiss();
                    this.f2568e = null;
                }
            }
        }
        WebView webView = this.f2569f;
        if (webView != null) {
            webView.stopLoading();
        }
        C4946i.m2911a((Activity) this, false);
        super.onDestroy();
    }

    /* access modifiers changed from: protected */
    public void onPause() {
        super.onPause();
        C4811f.m2473a().mo61493a(this.f2564a).mo61488b();
        C4969b bVar = this.f2571h;
        if (bVar != null && bVar.mo62115b()) {
            this.f2571h.mo62117d();
        }
        overridePendingTransition(0, 0);
        String str = this.f2574k;
        if (str != null && str.equals("back")) {
            finish();
        }
    }

    /* access modifiers changed from: protected */
    public void onSaveInstanceState(Bundle bundle) {
        super.onSaveInstanceState(bundle);
        Long l = this.f2572i;
        if (l != null) {
            bundle.putSerializable("lastLoadTime", l);
        }
        Long l2 = this.f2573j;
        if (l2 != null) {
            bundle.putSerializable("adCacheTtl", l2);
        }
    }

    /* renamed from: a */
    public void mo61390a(int i) {
        View childAt = this.f2567d.getChildAt(i - this.f2567d.getFirstItemPosition());
        if (childAt != null) {
            C4808d dVar = (C4808d) childAt.getTag();
            C4810e a = C4811f.m2473a().mo61493a(this.f2564a);
            if (!(a == null || a.mo61492e() == null || i >= a.mo61492e().size())) {
                ListItem listItem = (ListItem) a.mo61492e().get(i);
                dVar.mo61477b().setImageBitmap(a.mo61482a(i, listItem.mo61404a(), listItem.mo61413i()));
                dVar.mo61477b().requestLayout();
                dVar.mo61476a(listItem.mo61421q());
            }
        }
    }

    public void finish() {
        C5155g.m3807a("List3DActivity", 2, "Finishing activity.");
        this.f2575l = System.currentTimeMillis();
        C4988c.m3124b((Context) this, mo61391b(), mo61389a());
        C5059m.m3378a().mo62377a(false);
        m2384c();
        synchronized (this) {
            if (this.f2577n != null) {
                C5160b.m3831a((Context) this).mo62878a(this.f2577n);
                this.f2577n = null;
            }
        }
        C4811f.m2473a().mo61493a(this.f2564a).mo61491d();
        if (!AdsConstants.OVERRIDE_NETWORK.booleanValue()) {
            C4811f.m2473a().mo61494b(this.f2564a);
        }
        super.finish();
    }

    /* renamed from: c */
    private void m2384c() {
        if (this.f2570g == getResources().getConfiguration().orientation) {
            C5160b.m3831a((Context) this).mo62880a(new Intent("com.startapp.android.HideDisplayBroadcastListener"));
        }
    }

    /* renamed from: d */
    private boolean m2385d() {
        if (this.f2572i == null || this.f2573j == null || System.currentTimeMillis() - this.f2572i.longValue() <= this.f2573j.longValue()) {
            return false;
        }
        return true;
    }
}
