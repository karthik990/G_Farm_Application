package com.google.android.exoplayer2.metadata.icy;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.exoplayer2.Format;
import com.google.android.exoplayer2.metadata.Metadata.Entry;
import com.google.android.exoplayer2.metadata.Metadata.Entry.CC;
import com.google.android.exoplayer2.util.Assertions;
import com.google.android.exoplayer2.util.Util;

public final class IcyInfo implements Entry {
    public static final Creator<IcyInfo> CREATOR = new Creator<IcyInfo>() {
        public IcyInfo createFromParcel(Parcel parcel) {
            return new IcyInfo(parcel);
        }

        public IcyInfo[] newArray(int i) {
            return new IcyInfo[i];
        }
    };
    public final String rawMetadata;
    public final String title;
    public final String url;

    public int describeContents() {
        return 0;
    }

    public /* synthetic */ byte[] getWrappedMetadataBytes() {
        return CC.$default$getWrappedMetadataBytes(this);
    }

    public /* synthetic */ Format getWrappedMetadataFormat() {
        return CC.$default$getWrappedMetadataFormat(this);
    }

    public IcyInfo(String str, String str2, String str3) {
        this.rawMetadata = str;
        this.title = str2;
        this.url = str3;
    }

    IcyInfo(Parcel parcel) {
        this.rawMetadata = (String) Assertions.checkNotNull(parcel.readString());
        this.title = parcel.readString();
        this.url = parcel.readString();
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        return Util.areEqual(this.rawMetadata, ((IcyInfo) obj).rawMetadata);
    }

    public int hashCode() {
        return this.rawMetadata.hashCode();
    }

    public String toString() {
        return String.format("ICY: title=\"%s\", url=\"%s\", rawMetadata=\"%s\"", new Object[]{this.title, this.url, this.rawMetadata});
    }

    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.rawMetadata);
        parcel.writeString(this.title);
        parcel.writeString(this.url);
    }
}
