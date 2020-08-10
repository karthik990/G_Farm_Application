package com.google.android.exoplayer2.metadata.flac;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.exoplayer2.Format;
import com.google.android.exoplayer2.metadata.Metadata.Entry;
import com.google.android.exoplayer2.metadata.Metadata.Entry.CC;
import com.google.android.exoplayer2.util.Util;
import java.util.Arrays;

public final class PictureFrame implements Entry {
    public static final Creator<PictureFrame> CREATOR = new Creator<PictureFrame>() {
        public PictureFrame createFromParcel(Parcel parcel) {
            return new PictureFrame(parcel);
        }

        public PictureFrame[] newArray(int i) {
            return new PictureFrame[i];
        }
    };
    public final int colors;
    public final int depth;
    public final String description;
    public final int height;
    public final String mimeType;
    public final byte[] pictureData;
    public final int pictureType;
    public final int width;

    public int describeContents() {
        return 0;
    }

    public /* synthetic */ byte[] getWrappedMetadataBytes() {
        return CC.$default$getWrappedMetadataBytes(this);
    }

    public /* synthetic */ Format getWrappedMetadataFormat() {
        return CC.$default$getWrappedMetadataFormat(this);
    }

    public PictureFrame(int i, String str, String str2, int i2, int i3, int i4, int i5, byte[] bArr) {
        this.pictureType = i;
        this.mimeType = str;
        this.description = str2;
        this.width = i2;
        this.height = i3;
        this.depth = i4;
        this.colors = i5;
        this.pictureData = bArr;
    }

    PictureFrame(Parcel parcel) {
        this.pictureType = parcel.readInt();
        this.mimeType = (String) Util.castNonNull(parcel.readString());
        this.description = (String) Util.castNonNull(parcel.readString());
        this.width = parcel.readInt();
        this.height = parcel.readInt();
        this.depth = parcel.readInt();
        this.colors = parcel.readInt();
        this.pictureData = (byte[]) Util.castNonNull(parcel.createByteArray());
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Picture: mimeType=");
        sb.append(this.mimeType);
        sb.append(", description=");
        sb.append(this.description);
        return sb.toString();
    }

    public boolean equals(Object obj) {
        boolean z = true;
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        PictureFrame pictureFrame = (PictureFrame) obj;
        if (!(this.pictureType == pictureFrame.pictureType && this.mimeType.equals(pictureFrame.mimeType) && this.description.equals(pictureFrame.description) && this.width == pictureFrame.width && this.height == pictureFrame.height && this.depth == pictureFrame.depth && this.colors == pictureFrame.colors && Arrays.equals(this.pictureData, pictureFrame.pictureData))) {
            z = false;
        }
        return z;
    }

    public int hashCode() {
        return ((((((((((((((527 + this.pictureType) * 31) + this.mimeType.hashCode()) * 31) + this.description.hashCode()) * 31) + this.width) * 31) + this.height) * 31) + this.depth) * 31) + this.colors) * 31) + Arrays.hashCode(this.pictureData);
    }

    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(this.pictureType);
        parcel.writeString(this.mimeType);
        parcel.writeString(this.description);
        parcel.writeInt(this.width);
        parcel.writeInt(this.height);
        parcel.writeInt(this.depth);
        parcel.writeInt(this.colors);
        parcel.writeByteArray(this.pictureData);
    }
}
