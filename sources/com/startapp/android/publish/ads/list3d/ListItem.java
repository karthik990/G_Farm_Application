package com.startapp.android.publish.ads.list3d;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.startapp.android.publish.common.model.AdDetails;

/* compiled from: StartAppSDK */
public class ListItem implements Parcelable {
    public static final Creator<ListItem> CREATOR = new Creator<ListItem>() {
        /* renamed from: a */
        public ListItem createFromParcel(Parcel parcel) {
            return new ListItem(parcel);
        }

        /* renamed from: a */
        public ListItem[] newArray(int i) {
            return new ListItem[i];
        }
    };

    /* renamed from: a */
    private String f2583a;

    /* renamed from: b */
    private String f2584b;

    /* renamed from: c */
    private String f2585c;

    /* renamed from: d */
    private String f2586d;

    /* renamed from: e */
    private String f2587e;

    /* renamed from: f */
    private String f2588f;

    /* renamed from: g */
    private String f2589g;

    /* renamed from: h */
    private String f2590h;

    /* renamed from: i */
    private String f2591i;

    /* renamed from: j */
    private float f2592j;

    /* renamed from: k */
    private boolean f2593k;

    /* renamed from: l */
    private boolean f2594l;

    /* renamed from: m */
    private Drawable f2595m;

    /* renamed from: n */
    private String f2596n;

    /* renamed from: o */
    private String f2597o;

    /* renamed from: p */
    private Long f2598p;

    /* renamed from: q */
    private Boolean f2599q;

    /* renamed from: r */
    private String f2600r;

    public int describeContents() {
        return 0;
    }

    public ListItem(AdDetails adDetails) {
        String str = "";
        this.f2583a = str;
        this.f2584b = str;
        this.f2585c = str;
        this.f2586d = str;
        this.f2587e = str;
        this.f2588f = str;
        this.f2589g = str;
        this.f2590h = str;
        this.f2591i = str;
        this.f2592j = 0.0f;
        this.f2593k = false;
        this.f2594l = true;
        this.f2595m = null;
        this.f2599q = null;
        this.f2600r = str;
        this.f2583a = adDetails.getAdId();
        this.f2584b = adDetails.getClickUrl();
        this.f2585c = adDetails.getTrackingUrl();
        this.f2586d = adDetails.getTrackingClickUrl();
        this.f2587e = adDetails.getTrackingCloseUrl();
        this.f2588f = adDetails.getPackageName();
        this.f2589g = adDetails.getTitle();
        this.f2590h = adDetails.getDescription();
        this.f2591i = adDetails.getImageUrl();
        this.f2592j = adDetails.getRating();
        this.f2593k = adDetails.isSmartRedirect();
        this.f2594l = adDetails.isStartappBrowserEnabled();
        this.f2595m = null;
        this.f2600r = adDetails.getTemplate();
        this.f2596n = adDetails.getIntentDetails();
        this.f2597o = adDetails.getIntentPackageName();
        this.f2598p = adDetails.getDelayImpressionInSeconds();
        this.f2599q = adDetails.shouldSendRedirectHops();
    }

    public ListItem(Parcel parcel) {
        String str = "";
        this.f2583a = str;
        this.f2584b = str;
        this.f2585c = str;
        this.f2586d = str;
        this.f2587e = str;
        this.f2588f = str;
        this.f2589g = str;
        this.f2590h = str;
        this.f2591i = str;
        this.f2592j = 0.0f;
        boolean z = false;
        this.f2593k = false;
        this.f2594l = true;
        this.f2595m = null;
        this.f2599q = null;
        this.f2600r = str;
        if (parcel.readInt() == 1) {
            this.f2595m = new BitmapDrawable((Bitmap) Bitmap.CREATOR.createFromParcel(parcel));
        } else {
            this.f2595m = null;
        }
        this.f2583a = parcel.readString();
        this.f2584b = parcel.readString();
        this.f2585c = parcel.readString();
        this.f2586d = parcel.readString();
        this.f2587e = parcel.readString();
        this.f2588f = parcel.readString();
        this.f2589g = parcel.readString();
        this.f2590h = parcel.readString();
        this.f2591i = parcel.readString();
        this.f2592j = parcel.readFloat();
        if (parcel.readInt() == 1) {
            this.f2593k = true;
        } else {
            this.f2593k = false;
        }
        if (parcel.readInt() == 0) {
            this.f2594l = false;
        } else {
            this.f2594l = true;
        }
        this.f2600r = parcel.readString();
        this.f2597o = parcel.readString();
        this.f2596n = parcel.readString();
        this.f2598p = Long.valueOf(parcel.readLong());
        if (this.f2598p.longValue() == -1) {
            this.f2598p = null;
        }
        int readInt = parcel.readInt();
        if (readInt == 0) {
            this.f2599q = null;
            return;
        }
        if (readInt == 1) {
            z = true;
        }
        this.f2599q = Boolean.valueOf(z);
    }

    /* renamed from: a */
    public String mo61404a() {
        return this.f2583a;
    }

    /* renamed from: b */
    public String mo61405b() {
        return this.f2584b;
    }

    /* renamed from: c */
    public String mo61406c() {
        return this.f2585c;
    }

    /* renamed from: d */
    public String mo61407d() {
        return this.f2587e;
    }

    /* renamed from: e */
    public String mo61409e() {
        return this.f2586d;
    }

    /* renamed from: f */
    public String mo61410f() {
        return this.f2588f;
    }

    /* renamed from: g */
    public String mo61411g() {
        return this.f2589g;
    }

    /* renamed from: h */
    public String mo61412h() {
        return this.f2590h;
    }

    /* renamed from: i */
    public String mo61413i() {
        return this.f2591i;
    }

    /* renamed from: j */
    public Drawable mo61414j() {
        return this.f2595m;
    }

    /* renamed from: k */
    public float mo61415k() {
        return this.f2592j;
    }

    /* renamed from: l */
    public boolean mo61416l() {
        return this.f2593k;
    }

    /* renamed from: m */
    public boolean mo61417m() {
        return this.f2594l;
    }

    /* renamed from: n */
    public String mo61418n() {
        return this.f2600r;
    }

    /* renamed from: o */
    public String mo61419o() {
        return this.f2596n;
    }

    /* renamed from: p */
    public String mo61420p() {
        return this.f2597o;
    }

    /* renamed from: q */
    public boolean mo61421q() {
        return this.f2597o != null;
    }

    /* renamed from: r */
    public Long mo61422r() {
        return this.f2598p;
    }

    /* renamed from: s */
    public Boolean mo61423s() {
        return this.f2599q;
    }

    public void writeToParcel(Parcel parcel, int i) {
        int i2 = 1;
        if (mo61414j() != null) {
            parcel.writeParcelable(((BitmapDrawable) mo61414j()).getBitmap(), i);
            parcel.writeInt(1);
        } else {
            parcel.writeInt(0);
        }
        parcel.writeString(this.f2583a);
        parcel.writeString(this.f2584b);
        parcel.writeString(this.f2585c);
        parcel.writeString(this.f2586d);
        parcel.writeString(this.f2587e);
        parcel.writeString(this.f2588f);
        parcel.writeString(this.f2589g);
        parcel.writeString(this.f2590h);
        parcel.writeString(this.f2591i);
        parcel.writeFloat(this.f2592j);
        parcel.writeInt(this.f2593k ? 1 : 0);
        parcel.writeInt(this.f2594l ? 1 : 0);
        parcel.writeString(this.f2600r);
        parcel.writeString(this.f2597o);
        parcel.writeString(this.f2596n);
        Long l = this.f2598p;
        if (l == null) {
            parcel.writeLong(-1);
        } else {
            parcel.writeLong(l.longValue());
        }
        Boolean bool = this.f2599q;
        if (bool == null) {
            parcel.writeInt(0);
            return;
        }
        if (!bool.booleanValue()) {
            i2 = -1;
        }
        parcel.writeInt(i2);
    }
}
