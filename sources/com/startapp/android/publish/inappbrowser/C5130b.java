package com.startapp.android.publish.inappbrowser;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build.VERSION;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.TextView;
import com.startapp.android.publish.adsCommon.Utils.C4945h;
import com.startapp.common.p092a.C5155g;
import java.util.HashMap;
import java.util.Map;
import org.objectweb.asm.Opcodes;

/* renamed from: com.startapp.android.publish.inappbrowser.b */
/* compiled from: StartAppSDK */
public class C5130b extends RelativeLayout {

    /* renamed from: p */
    private static final int f3510p = Color.rgb(78, 86, 101);

    /* renamed from: q */
    private static final int f3511q = Color.rgb(Opcodes.LCMP, Opcodes.IFLT, Opcodes.IF_ACMPNE);

    /* renamed from: a */
    private RelativeLayout f3512a;

    /* renamed from: b */
    private ImageView f3513b;

    /* renamed from: c */
    private ImageView f3514c;

    /* renamed from: d */
    private ImageView f3515d;

    /* renamed from: e */
    private ImageView f3516e;

    /* renamed from: f */
    private Bitmap f3517f;

    /* renamed from: g */
    private Bitmap f3518g;

    /* renamed from: h */
    private Bitmap f3519h;

    /* renamed from: i */
    private Bitmap f3520i;

    /* renamed from: j */
    private Bitmap f3521j;

    /* renamed from: k */
    private Bitmap f3522k;

    /* renamed from: l */
    private TextView f3523l;

    /* renamed from: m */
    private TextView f3524m;

    /* renamed from: n */
    private Boolean f3525n = Boolean.valueOf(false);

    /* renamed from: o */
    private Map<String, C5131c> f3526o;

    public C5130b(Context context) {
        super(context);
    }

    /* renamed from: a */
    public void mo62820a() {
        setDescendantFocusability(262144);
        setBackgroundColor(Color.parseColor("#e9e9e9"));
        setLayoutParams(new LayoutParams(-1, C4945h.m2891a(getContext(), 60)));
        setId(2101);
        this.f3526o = m3686d();
    }

    /* renamed from: d */
    private Map<String, C5131c> m3686d() {
        HashMap hashMap = new HashMap();
        hashMap.put("BACK", new C5131c(this.f3519h, 14, 22, "back_.png"));
        hashMap.put("BACK_DARK", new C5131c(this.f3521j, 14, 22, "back_dark.png"));
        hashMap.put("FORWARD", new C5131c(this.f3520i, 14, 22, "forward_.png"));
        hashMap.put("FORWARD_DARK", new C5131c(this.f3522k, 14, 22, "forward_dark.png"));
        hashMap.put("X", new C5131c(this.f3518g, 23, 23, "x_dark.png"));
        hashMap.put("BROWSER", new C5131c(this.f3517f, 28, 28, "browser_icon_dark.png"));
        return hashMap;
    }

    /* renamed from: b */
    public void mo62822b() {
        Typeface typeface = Typeface.DEFAULT;
        this.f3523l = C4945h.m2896a(getContext(), this.f3523l, typeface, 1, 16.46f, f3510p, 2102);
        this.f3524m = C4945h.m2896a(getContext(), this.f3523l, typeface, 1, 12.12f, f3511q, 2107);
        this.f3523l.setText("Loading...");
        this.f3512a = new RelativeLayout(getContext());
        this.f3512a.setLayoutParams(new ViewGroup.LayoutParams(-1, -2));
        this.f3512a.addView(this.f3523l, C4945h.m2894a(getContext(), new int[]{0, 0, 0, 0}, new int[0]));
        this.f3512a.addView(this.f3524m, C4945h.m2895a(getContext(), new int[]{0, 0, 0, 0}, new int[0], 3, 2102));
        m3687e();
        this.f3513b = C4945h.m2893a(getContext(), this.f3513b, ((C5131c) this.f3526o.get("X")).mo62832d(), 2103);
        this.f3515d = C4945h.m2893a(getContext(), this.f3515d, ((C5131c) this.f3526o.get("BROWSER")).mo62832d(), 2104);
        this.f3516e = C4945h.m2893a(getContext(), this.f3516e, ((C5131c) this.f3526o.get("BACK")).mo62832d(), 2105);
        this.f3514c = C4945h.m2893a(getContext(), this.f3514c, ((C5131c) this.f3526o.get("FORWARD")).mo62832d(), 2106);
        int a = C4945h.m2891a(getContext(), 10);
        this.f3514c.setPadding(a, a, a, a);
        this.f3516e.setPadding(a, a, a, a);
        addView(this.f3513b, C4945h.m2894a(getContext(), new int[]{0, 0, 16, 0}, new int[]{15, 11}));
        addView(this.f3515d, C4945h.m2895a(getContext(), new int[]{0, 0, 17, 0}, new int[]{15}, 0, 2103));
        addView(this.f3512a, C4945h.m2895a(getContext(), new int[]{16, 6, 16, 0}, new int[]{9}, 0, 2104));
    }

    /* renamed from: e */
    private void m3687e() {
        for (C5131c cVar : this.f3526o.values()) {
            Bitmap a = C4945h.m2892a(getContext(), cVar.mo62831c());
            if (a == null) {
                StringBuilder sb = new StringBuilder();
                sb.append("Error getting navigation bar bitmap - ");
                sb.append(cVar.mo62831c());
                sb.append(" from resources ");
                C5155g.m3807a("NavigationBarLayout", 6, sb.toString());
            } else {
                cVar.mo62829a(Bitmap.createScaledBitmap(a, C4945h.m2891a(getContext(), cVar.mo62828a()), C4945h.m2891a(getContext(), cVar.mo62830b()), true));
            }
        }
    }

    /* renamed from: a */
    public void mo62821a(WebView webView) {
        if (this.f3525n.booleanValue()) {
            mo62823b(webView);
        } else if (webView.canGoBack()) {
            m3688f();
            this.f3525n = Boolean.valueOf(true);
        }
    }

    /* access modifiers changed from: 0000 */
    /* renamed from: b */
    public void mo62823b(WebView webView) {
        if (webView.canGoBack()) {
            this.f3516e.setImageBitmap(((C5131c) this.f3526o.get("BACK_DARK")).mo62832d());
        } else {
            this.f3516e.setImageBitmap(((C5131c) this.f3526o.get("BACK")).mo62832d());
        }
        if (webView.canGoForward()) {
            this.f3514c.setImageBitmap(((C5131c) this.f3526o.get("FORWARD_DARK")).mo62832d());
        } else {
            this.f3514c.setImageBitmap(((C5131c) this.f3526o.get("FORWARD")).mo62832d());
        }
        if (webView.getTitle() != null) {
            this.f3523l.setText(webView.getTitle());
        }
    }

    public TextView getUrlTxt() {
        return this.f3524m;
    }

    public TextView getTitleTxt() {
        return this.f3523l;
    }

    public void setButtonsListener(OnClickListener onClickListener) {
        this.f3513b.setOnClickListener(onClickListener);
        this.f3516e.setOnClickListener(onClickListener);
        this.f3514c.setOnClickListener(onClickListener);
        this.f3515d.setOnClickListener(onClickListener);
    }

    /* renamed from: f */
    private void m3688f() {
        this.f3516e.setImageBitmap(((C5131c) this.f3526o.get("BACK_DARK")).mo62832d());
        addView(this.f3516e, C4945h.m2894a(getContext(), new int[]{6, 0, 0, 0}, new int[]{15, 9}));
        addView(this.f3514c, C4945h.m2895a(getContext(), new int[]{9, 0, 0, 0}, new int[]{15}, 1, 2105));
        removeView(this.f3512a);
        this.f3512a.removeView(this.f3524m);
        this.f3512a.removeView(this.f3523l);
        this.f3512a.addView(this.f3523l, C4945h.m2894a(getContext(), new int[]{0, 0, 0, 0}, new int[]{14}));
        this.f3512a.addView(this.f3524m, C4945h.m2895a(getContext(), new int[]{0, 0, 0, 0}, new int[]{14}, 3, 2102));
        LayoutParams a = C4945h.m2895a(getContext(), new int[]{16, 0, 16, 0}, new int[]{15}, 1, 2106);
        a.addRule(0, 2104);
        addView(this.f3512a, a);
    }

    /* renamed from: c */
    public void mo62824c() {
        if (VERSION.SDK_INT < 11) {
            ((BitmapDrawable) this.f3513b.getDrawable()).getBitmap().recycle();
            ((BitmapDrawable) this.f3515d.getDrawable()).getBitmap().recycle();
            ((BitmapDrawable) this.f3516e.getDrawable()).getBitmap().recycle();
            ((BitmapDrawable) this.f3514c.getDrawable()).getBitmap().recycle();
        }
        this.f3526o = null;
        this.f3519h = null;
        this.f3521j = null;
        this.f3520i = null;
        this.f3522k = null;
    }
}
