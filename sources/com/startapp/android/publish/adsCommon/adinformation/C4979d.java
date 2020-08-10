package com.startapp.android.publish.adsCommon.adinformation;

import android.content.Context;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;
import com.mobiroller.constants.ChatConstants;
import com.startapp.android.publish.adsCommon.AdsConstants;
import com.startapp.android.publish.adsCommon.Utils.C4945h;
import com.startapp.android.publish.adsCommon.adinformation.AdInformationPositions.Position;
import com.startapp.android.publish.adsCommon.adinformation.C4969b.C4977b;
import com.startapp.android.publish.common.model.AdPreferences.Placement;

/* renamed from: com.startapp.android.publish.adsCommon.adinformation.d */
/* compiled from: StartAppSDK */
public class C4979d extends RelativeLayout {

    /* renamed from: a */
    private ImageView f3135a;

    /* renamed from: b */
    private RelativeLayout f3136b;

    /* renamed from: c */
    private OnClickListener f3137c = null;

    /* renamed from: d */
    private AdInformationConfig f3138d;

    /* renamed from: e */
    private C4981e f3139e;

    /* renamed from: f */
    private Placement f3140f;

    /* renamed from: g */
    private Position f3141g;

    public C4979d(Context context, C4977b bVar, Placement placement, C4978c cVar, final OnClickListener onClickListener) {
        super(context);
        this.f3140f = placement;
        this.f3137c = new OnClickListener() {
            public void onClick(View view) {
                onClickListener.onClick(view);
            }
        };
        mo62131a(bVar, cVar);
    }

    /* access modifiers changed from: protected */
    /* renamed from: a */
    public void mo62131a(C4977b bVar, C4978c cVar) {
        this.f3138d = C4969b.m2991a(getContext());
        if (this.f3138d == null) {
            this.f3138d = AdInformationConfig.m2966a();
        }
        this.f3139e = this.f3138d.mo62086a(bVar.mo62124a());
        if (cVar == null || !cVar.mo62129d()) {
            this.f3141g = this.f3138d.mo62085a(this.f3140f);
        } else {
            this.f3141g = cVar.mo62128c();
        }
        this.f3135a = new ImageView(getContext());
        this.f3135a.setContentDescription(ChatConstants.ARG_USER_INFO);
        this.f3135a.setId(AdsConstants.AD_INFORMATION_ID);
        this.f3135a.setImageBitmap(this.f3139e.mo62133a(getContext()));
        this.f3136b = new RelativeLayout(getContext());
        LayoutParams layoutParams = new LayoutParams(C4945h.m2891a(getContext(), (int) (((float) this.f3139e.mo62138b()) * this.f3138d.mo62093e())), C4945h.m2891a(getContext(), (int) (((float) this.f3139e.mo62142c()) * this.f3138d.mo62093e())));
        this.f3136b.setBackgroundColor(0);
        LayoutParams layoutParams2 = new LayoutParams(C4945h.m2891a(getContext(), this.f3139e.mo62138b()), C4945h.m2891a(getContext(), this.f3139e.mo62142c()));
        layoutParams2.setMargins(0, 0, 0, 0);
        this.f3135a.setPadding(0, 0, 0, 0);
        this.f3141g.addRules(layoutParams2);
        this.f3136b.addView(this.f3135a, layoutParams2);
        this.f3136b.setOnClickListener(this.f3137c);
        addView(this.f3136b, layoutParams);
    }
}
