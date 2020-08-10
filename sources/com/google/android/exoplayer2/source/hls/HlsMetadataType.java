package com.google.android.exoplayer2.source.hls;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Documented
@Retention(RetentionPolicy.SOURCE)
public @interface HlsMetadataType {
    public static final int EMSG = 3;
    public static final int ID3 = 1;
}
