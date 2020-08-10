package com.google.android.exoplayer2;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

public interface RendererCapabilities {
    public static final int ADAPTIVE_NOT_SEAMLESS = 8;
    public static final int ADAPTIVE_NOT_SUPPORTED = 0;
    public static final int ADAPTIVE_SEAMLESS = 16;
    public static final int ADAPTIVE_SUPPORT_MASK = 24;
    public static final int FORMAT_EXCEEDS_CAPABILITIES = 3;
    public static final int FORMAT_HANDLED = 4;
    public static final int FORMAT_SUPPORT_MASK = 7;
    public static final int FORMAT_UNSUPPORTED_DRM = 2;
    public static final int FORMAT_UNSUPPORTED_SUBTYPE = 1;
    public static final int FORMAT_UNSUPPORTED_TYPE = 0;
    public static final int TUNNELING_NOT_SUPPORTED = 0;
    public static final int TUNNELING_SUPPORTED = 32;
    public static final int TUNNELING_SUPPORT_MASK = 32;

    /* renamed from: com.google.android.exoplayer2.RendererCapabilities$-CC reason: invalid class name */
    public final /* synthetic */ class CC {
        public static int create(int i, int i2, int i3) {
            return i | i2 | i3;
        }

        public static int getAdaptiveSupport(int i) {
            return i & 24;
        }

        public static int getFormatSupport(int i) {
            return i & 7;
        }

        public static int getTunnelingSupport(int i) {
            return i & 32;
        }

        public static int create(int i) {
            return create(i, 0, 0);
        }

        public static String getFormatSupportString(int i) {
            if (i == 0) {
                return "NO";
            }
            if (i == 1) {
                return "NO_UNSUPPORTED_TYPE";
            }
            if (i == 2) {
                return "NO_UNSUPPORTED_DRM";
            }
            if (i == 3) {
                return "NO_EXCEEDS_CAPABILITIES";
            }
            if (i == 4) {
                return "YES";
            }
            throw new IllegalStateException();
        }
    }

    @Documented
    @Retention(RetentionPolicy.SOURCE)
    public @interface AdaptiveSupport {
    }

    @Documented
    @Retention(RetentionPolicy.SOURCE)
    public @interface Capabilities {
    }

    @Documented
    @Retention(RetentionPolicy.SOURCE)
    public @interface FormatSupport {
    }

    @Documented
    @Retention(RetentionPolicy.SOURCE)
    public @interface TunnelingSupport {
    }

    int getTrackType();

    int supportsFormat(Format format) throws ExoPlaybackException;

    int supportsMixedMimeTypeAdaptation() throws ExoPlaybackException;
}
