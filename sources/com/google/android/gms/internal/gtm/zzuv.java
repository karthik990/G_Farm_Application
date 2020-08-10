package com.google.android.gms.internal.gtm;

import java.io.IOException;

public final class zzuv extends IOException {
    public zzuv(String str) {
        super(str);
    }

    public zzuv(String str, Exception exc) {
        super(str, exc);
    }

    static zzuv zzsa() {
        return new zzuv("While parsing a protocol message, the input ended unexpectedly in the middle of a field.  This could mean either than the input has been truncated or that an embedded message misreported its own length.");
    }

    static zzuv zzsb() {
        return new zzuv("CodedInputStream encountered an embedded string or message which claimed to have negative size.");
    }

    static zzuv zzsc() {
        return new zzuv("CodedInputStream encountered a malformed varint.");
    }

    static zzuv zzsd() {
        return new zzuv("Protocol message had too many levels of nesting.  May be malicious.  Use CodedInputStream.setRecursionLimit() to increase the depth limit.");
    }
}
