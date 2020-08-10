package com.google.android.exoplayer2.source.dash.manifest;

import com.google.android.exoplayer2.util.Util;

public final class Descriptor {

    /* renamed from: id */
    public final String f1521id;
    public final String schemeIdUri;
    public final String value;

    public Descriptor(String str, String str2, String str3) {
        this.schemeIdUri = str;
        this.value = str2;
        this.f1521id = str3;
    }

    public boolean equals(Object obj) {
        boolean z = true;
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        Descriptor descriptor = (Descriptor) obj;
        if (!Util.areEqual(this.schemeIdUri, descriptor.schemeIdUri) || !Util.areEqual(this.value, descriptor.value) || !Util.areEqual(this.f1521id, descriptor.f1521id)) {
            z = false;
        }
        return z;
    }

    public int hashCode() {
        int hashCode = this.schemeIdUri.hashCode() * 31;
        String str = this.value;
        int i = 0;
        int hashCode2 = (hashCode + (str != null ? str.hashCode() : 0)) * 31;
        String str2 = this.f1521id;
        if (str2 != null) {
            i = str2.hashCode();
        }
        return hashCode2 + i;
    }
}
