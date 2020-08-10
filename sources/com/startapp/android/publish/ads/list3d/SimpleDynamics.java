package com.startapp.android.publish.ads.list3d;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;

/* compiled from: StartAppSDK */
class SimpleDynamics extends Dynamics implements Parcelable {
    public static final Creator<SimpleDynamics> CREATOR = new Creator<SimpleDynamics>() {
        /* renamed from: a */
        public SimpleDynamics createFromParcel(Parcel parcel) {
            return new SimpleDynamics(parcel);
        }

        /* renamed from: a */
        public SimpleDynamics[] newArray(int i) {
            return new SimpleDynamics[i];
        }
    };

    /* renamed from: f */
    private float f2601f;

    /* renamed from: g */
    private float f2602g;

    public int describeContents() {
        return 0;
    }

    public SimpleDynamics(float f, float f2) {
        this.f2601f = f;
        this.f2602g = f2;
    }

    /* access modifiers changed from: protected */
    /* renamed from: a */
    public void mo61380a(int i) {
        this.f2560b += mo61385c() * this.f2602g;
        this.f2559a += (this.f2560b * ((float) i)) / 1000.0f;
        this.f2560b *= this.f2601f;
    }

    public void writeToParcel(Parcel parcel, int i) {
        super.writeToParcel(parcel, i);
        parcel.writeFloat(this.f2601f);
        parcel.writeFloat(this.f2602g);
    }

    public SimpleDynamics(Parcel parcel) {
        super(parcel);
        this.f2601f = parcel.readFloat();
        this.f2602g = parcel.readFloat();
    }

    /* renamed from: a */
    public void mo61377a(double d) {
        super.mo61377a(d);
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(super.toString());
        sb.append(", Friction: [");
        sb.append(this.f2601f);
        sb.append("], Snap:[");
        sb.append(this.f2602g);
        sb.append("]");
        return sb.toString();
    }
}
