package com.startapp.android.publish.ads.banner.banner3d;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.Point;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout.LayoutParams;
import com.startapp.android.publish.ads.banner.BannerOptions;
import com.startapp.android.publish.adsCommon.C4983b;
import com.startapp.android.publish.adsCommon.C4988c;
import com.startapp.android.publish.adsCommon.C5040i;
import com.startapp.android.publish.adsCommon.Utils.C4945h;
import com.startapp.android.publish.adsCommon.p080d.C5002b;
import com.startapp.android.publish.common.metaData.MetaData;
import com.startapp.android.publish.common.model.AdDetails;
import com.startapp.android.publish.common.model.AdPreferences.Placement;
import com.startapp.common.C5134a;
import com.startapp.common.C5134a.C5137a;
import com.startapp.common.p092a.C5155g;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

/* compiled from: StartAppSDK */
public class Banner3DFace implements Parcelable, C5137a {
    public static final Creator<Banner3DFace> CREATOR = new Creator<Banner3DFace>() {
        /* renamed from: a */
        public Banner3DFace createFromParcel(Parcel parcel) {
            return new Banner3DFace(parcel);
        }

        /* renamed from: a */
        public Banner3DFace[] newArray(int i) {
            return new Banner3DFace[i];
        }
    };

    /* renamed from: a */
    private AdDetails f2531a;

    /* renamed from: b */
    private Point f2532b;

    /* renamed from: c */
    private Bitmap f2533c = null;

    /* renamed from: d */
    private Bitmap f2534d = null;

    /* renamed from: e */
    private AtomicBoolean f2535e = new AtomicBoolean(false);

    /* renamed from: f */
    private C5002b f2536f;

    /* renamed from: g */
    private C5040i f2537g = null;

    /* renamed from: h */
    private C4774b f2538h = null;

    public int describeContents() {
        return 0;
    }

    public Banner3DFace(Context context, ViewGroup viewGroup, AdDetails adDetails, BannerOptions bannerOptions, C5002b bVar) {
        this.f2531a = adDetails;
        this.f2536f = bVar;
        mo61328a(context, bannerOptions, viewGroup);
    }

    /* renamed from: a */
    public AdDetails mo61327a() {
        return this.f2531a;
    }

    /* renamed from: b */
    public Bitmap mo61330b() {
        return this.f2534d;
    }

    /* renamed from: a */
    public void mo61328a(Context context, BannerOptions bannerOptions, ViewGroup viewGroup) {
        int a = C4945h.m2891a(context, bannerOptions.mo61276e() - 5);
        this.f2532b = new Point((int) (((float) C4945h.m2891a(context, bannerOptions.mo61275d())) * bannerOptions.mo61282j()), (int) (((float) C4945h.m2891a(context, bannerOptions.mo61276e())) * bannerOptions.mo61283k()));
        this.f2538h = new C4774b(context, new Point(bannerOptions.mo61275d(), bannerOptions.mo61276e()));
        this.f2538h.setText(mo61327a().getTitle());
        this.f2538h.setRating(mo61327a().getRating());
        this.f2538h.setDescription(mo61327a().getDescription());
        this.f2538h.setButtonText(this.f2531a.isCPE());
        Bitmap bitmap = this.f2533c;
        if (bitmap != null) {
            this.f2538h.mo61346a(bitmap, a, a);
        } else {
            this.f2538h.mo61345a(17301651, a, a);
            new C5134a(mo61327a().getImageUrl(), this, 0).mo62837a();
            StringBuilder sb = new StringBuilder();
            sb.append(" Banner Face Image Async Request: [");
            sb.append(mo61327a().getTitle());
            sb.append("]");
            C5155g.m3807a("Banner3DFace", 3, sb.toString());
        }
        LayoutParams layoutParams = new LayoutParams(this.f2532b.x, this.f2532b.y);
        layoutParams.addRule(13);
        viewGroup.addView(this.f2538h, layoutParams);
        this.f2538h.setVisibility(8);
        m2322f();
    }

    /* renamed from: f */
    private void m2322f() {
        this.f2534d = m2320a((View) this.f2538h);
        if (this.f2532b.x > 0 && this.f2532b.y > 0) {
            this.f2534d = Bitmap.createScaledBitmap(this.f2534d, this.f2532b.x, this.f2532b.y, false);
        }
    }

    /* renamed from: a */
    private Bitmap m2320a(View view) {
        view.measure(view.getMeasuredWidth(), view.getMeasuredHeight());
        Bitmap createBitmap = Bitmap.createBitmap(view.getMeasuredWidth(), view.getMeasuredHeight(), Config.ARGB_8888);
        Canvas canvas = new Canvas(createBitmap);
        view.layout(0, 0, view.getMeasuredWidth(), view.getMeasuredHeight());
        view.draw(canvas);
        return createBitmap;
    }

    /* renamed from: a */
    public void mo61329a(Bitmap bitmap, int i) {
        if (bitmap != null) {
            C4774b bVar = this.f2538h;
            if (bVar != null) {
                this.f2533c = bitmap;
                bVar.setImage(bitmap);
                m2322f();
            }
        }
    }

    /* renamed from: a */
    public C5040i mo61326a(Context context) {
        if (mo61327a().getTrackingUrl() == null || !this.f2535e.compareAndSet(false, true)) {
            return null;
        }
        Context context2 = context;
        C5040i iVar = new C5040i(context2, new String[]{mo61327a().getTrackingUrl()}, this.f2536f, m2323g());
        this.f2537g = iVar;
        return this.f2537g;
    }

    /* renamed from: c */
    public void mo61332c() {
        C5040i iVar = this.f2537g;
        if (iVar != null) {
            iVar.mo62345a(false);
        }
    }

    /* renamed from: b */
    public void mo61331b(Context context) {
        String intentPackageName = mo61327a().getIntentPackageName();
        boolean a = C4988c.m3118a(context, Placement.INAPP_BANNER);
        C5040i iVar = this.f2537g;
        if (iVar != null) {
            iVar.mo62345a(true);
        }
        if (intentPackageName != null && !"null".equals(intentPackageName) && !TextUtils.isEmpty(intentPackageName)) {
            C4988c.m3114a(intentPackageName, mo61327a().getIntentDetails(), mo61327a().getClickUrl(), context, this.f2536f);
        } else if (!mo61327a().isSmartRedirect() || a) {
            C4988c.m3105a(context, mo61327a().getClickUrl(), mo61327a().getTrackingClickUrl(), this.f2536f, mo61327a().isStartappBrowserEnabled() && !a, false);
        } else {
            C4988c.m3107a(context, mo61327a().getClickUrl(), mo61327a().getTrackingClickUrl(), mo61327a().getPackageName(), this.f2536f, C4983b.m3032a().mo62147A(), C4983b.m3032a().mo62148B(), mo61327a().isStartappBrowserEnabled(), mo61327a().shouldSendRedirectHops(), false);
        }
    }

    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeParcelable(mo61327a(), i);
        parcel.writeInt(this.f2532b.x);
        parcel.writeInt(this.f2532b.y);
        parcel.writeParcelable(this.f2533c, i);
        parcel.writeBooleanArray(new boolean[]{this.f2535e.get()});
        parcel.writeSerializable(this.f2536f);
    }

    public Banner3DFace(Parcel parcel) {
        this.f2531a = (AdDetails) parcel.readParcelable(AdDetails.class.getClassLoader());
        this.f2532b = new Point(1, 1);
        this.f2532b.x = parcel.readInt();
        this.f2532b.y = parcel.readInt();
        this.f2533c = (Bitmap) parcel.readParcelable(Bitmap.class.getClassLoader());
        boolean[] zArr = new boolean[1];
        parcel.readBooleanArray(zArr);
        this.f2535e.set(zArr[0]);
        this.f2536f = (C5002b) parcel.readSerializable();
    }

    /* renamed from: d */
    public void mo61333d() {
        m2321a(this.f2533c);
        m2321a(this.f2534d);
        this.f2533c = null;
        this.f2534d = null;
    }

    /* renamed from: e */
    public void mo61335e() {
        mo61333d();
        C5040i iVar = this.f2537g;
        if (iVar != null) {
            iVar.mo62345a(false);
        }
        C4774b bVar = this.f2538h;
        if (bVar != null) {
            bVar.removeAllViews();
            this.f2538h = null;
        }
    }

    /* renamed from: a */
    private void m2321a(Bitmap bitmap) {
        if (bitmap != null) {
            bitmap.recycle();
        }
    }

    /* renamed from: g */
    private long m2323g() {
        if (mo61327a().getDelayImpressionInSeconds() != null) {
            return TimeUnit.SECONDS.toMillis(mo61327a().getDelayImpressionInSeconds().longValue());
        }
        return TimeUnit.SECONDS.toMillis(MetaData.getInstance().getIABDisplayImpressionDelayInSeconds());
    }
}
