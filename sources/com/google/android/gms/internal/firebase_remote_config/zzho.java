package com.google.android.gms.internal.firebase_remote_config;

import java.io.IOException;

public class zzho extends IOException {
    private zzio zztw = null;

    public zzho(String str) {
        super(str);
    }

    public final zzho zzg(zzio zzio) {
        this.zztw = zzio;
        return this;
    }

    static zzho zzhg() {
        return new zzho("While parsing a protocol message, the input ended unexpectedly in the middle of a field.  This could mean either that the input has been truncated or that an embedded message misreported its own length.");
    }

    static zzho zzhh() {
        return new zzho("CodedInputStream encountered an embedded string or message which claimed to have negative size.");
    }

    static zzho zzhi() {
        return new zzho("CodedInputStream encountered a malformed varint.");
    }

    static zzho zzhj() {
        return new zzho("Protocol message contained an invalid tag (zero).");
    }

    static zzho zzhk() {
        return new zzho("Protocol message end-group tag did not match expected tag.");
    }

    static zzhp zzhl() {
        return new zzhp("Protocol message tag had invalid wire type.");
    }

    static zzho zzhm() {
        return new zzho("Protocol message was too large.  May be malicious.  Use CodedInputStream.setSizeLimit() to increase the size limit.");
    }

    static zzho zzhn() {
        return new zzho("Failed to parse the message.");
    }

    static zzho zzho() {
        return new zzho("Protocol message had invalid UTF-8.");
    }
}
