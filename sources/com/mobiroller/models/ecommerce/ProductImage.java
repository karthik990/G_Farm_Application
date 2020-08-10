package com.mobiroller.models.ecommerce;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import java.io.Serializable;

public class ProductImage implements Serializable, Parcelable {
    public static final Creator<ProductImage> CREATOR = new Creator<ProductImage>() {
        public ProductImage createFromParcel(Parcel parcel) {
            return new ProductImage(parcel);
        }

        public ProductImage[] newArray(int i) {
            return new ProductImage[i];
        }
    };

    /* renamed from: n */
    public String f2181n;

    /* renamed from: t */
    public String f2182t;

    public int describeContents() {
        return 0;
    }

    public ProductImage() {
    }

    protected ProductImage(Parcel parcel) {
        this.f2182t = parcel.readString();
        this.f2181n = parcel.readString();
    }

    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.f2182t);
        parcel.writeString(this.f2181n);
    }
}
