package com.google.android.exoplayer2.metadata.scte35;

import com.google.android.exoplayer2.Format;
import com.google.android.exoplayer2.metadata.Metadata.Entry;
import com.google.android.exoplayer2.metadata.Metadata.Entry.CC;

public abstract class SpliceCommand implements Entry {
    public int describeContents() {
        return 0;
    }

    public /* synthetic */ byte[] getWrappedMetadataBytes() {
        return CC.$default$getWrappedMetadataBytes(this);
    }

    public /* synthetic */ Format getWrappedMetadataFormat() {
        return CC.$default$getWrappedMetadataFormat(this);
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("SCTE-35 splice command: type=");
        sb.append(getClass().getSimpleName());
        return sb.toString();
    }
}
