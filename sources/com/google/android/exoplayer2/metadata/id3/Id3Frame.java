package com.google.android.exoplayer2.metadata.id3;

import com.google.android.exoplayer2.Format;
import com.google.android.exoplayer2.metadata.Metadata.Entry;
import com.google.android.exoplayer2.metadata.Metadata.Entry.CC;

public abstract class Id3Frame implements Entry {

    /* renamed from: id */
    public final String f1487id;

    public int describeContents() {
        return 0;
    }

    public /* synthetic */ byte[] getWrappedMetadataBytes() {
        return CC.$default$getWrappedMetadataBytes(this);
    }

    public /* synthetic */ Format getWrappedMetadataFormat() {
        return CC.$default$getWrappedMetadataFormat(this);
    }

    public Id3Frame(String str) {
        this.f1487id = str;
    }

    public String toString() {
        return this.f1487id;
    }
}
