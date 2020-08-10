package com.startapp.android.publish.ads.list3d;

import android.os.Parcel;
import android.os.Parcelable;
import android.view.animation.AnimationUtils;

/* compiled from: StartAppSDK */
public abstract class Dynamics implements Parcelable {

    /* renamed from: a */
    protected float f2559a;

    /* renamed from: b */
    protected float f2560b;

    /* renamed from: c */
    protected float f2561c = Float.MAX_VALUE;

    /* renamed from: d */
    protected float f2562d = -3.4028235E38f;

    /* renamed from: e */
    protected long f2563e = 0;

    /* access modifiers changed from: protected */
    /* renamed from: a */
    public abstract void mo61380a(int i);

    public int describeContents() {
        return 0;
    }

    public Dynamics() {
    }

    public Dynamics(Parcel parcel) {
        this.f2559a = parcel.readFloat();
        this.f2560b = parcel.readFloat();
        this.f2561c = parcel.readFloat();
        this.f2562d = parcel.readFloat();
        this.f2563e = AnimationUtils.currentAnimationTimeMillis();
    }

    /* renamed from: a */
    public void mo61379a(float f, float f2, long j) {
        this.f2560b = f2;
        this.f2559a = f;
        this.f2563e = j;
    }

    /* renamed from: a */
    public float mo61376a() {
        return this.f2559a;
    }

    /* renamed from: b */
    public float mo61383b() {
        return this.f2560b;
    }

    /* renamed from: a */
    public boolean mo61382a(float f, float f2) {
        boolean z = Math.abs(this.f2560b) < f;
        float f3 = this.f2559a;
        boolean z2 = f3 - f2 < this.f2561c && f3 + f2 > this.f2562d;
        if (!z || !z2) {
            return false;
        }
        return true;
    }

    /* renamed from: a */
    public void mo61378a(float f) {
        this.f2561c = f;
    }

    /* renamed from: b */
    public void mo61384b(float f) {
        this.f2562d = f;
    }

    /* renamed from: a */
    public void mo61381a(long j) {
        long j2 = this.f2563e;
        if (j2 != 0) {
            int i = (int) (j - j2);
            int i2 = 50;
            if (i <= 50) {
                i2 = i;
            }
            mo61380a(i2);
        }
        this.f2563e = j;
    }

    /* access modifiers changed from: protected */
    /* renamed from: c */
    public float mo61385c() {
        float f = this.f2559a;
        float f2 = this.f2561c;
        if (f <= f2) {
            f2 = this.f2562d;
            if (f >= f2) {
                return 0.0f;
            }
        }
        return f2 - f;
    }

    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeFloat(this.f2559a);
        parcel.writeFloat(this.f2560b);
        parcel.writeFloat(this.f2561c);
        parcel.writeFloat(this.f2562d);
    }

    /* renamed from: a */
    public void mo61377a(double d) {
        double d2 = (double) this.f2559a;
        Double.isNaN(d2);
        this.f2559a = (float) (d2 * d);
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Position: [");
        sb.append(this.f2559a);
        sb.append("], Velocity:[");
        sb.append(this.f2560b);
        sb.append("], MaxPos: [");
        sb.append(this.f2561c);
        sb.append("], mMinPos: [");
        sb.append(this.f2562d);
        sb.append("] LastTime:[");
        sb.append(this.f2563e);
        sb.append("]");
        return sb.toString();
    }
}
