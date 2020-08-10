package com.google.android.exoplayer2.drm;

import com.google.android.exoplayer2.drm.ExoMediaCrypto;
import java.io.IOException;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.Map;

public interface DrmSession<T extends ExoMediaCrypto> {
    public static final int STATE_ERROR = 1;
    public static final int STATE_OPENED = 3;
    public static final int STATE_OPENED_WITH_KEYS = 4;
    public static final int STATE_OPENING = 2;
    public static final int STATE_RELEASED = 0;

    /* renamed from: com.google.android.exoplayer2.drm.DrmSession$-CC reason: invalid class name */
    public final /* synthetic */ class CC {
        public static boolean $default$playClearSamplesWithoutKeys(DrmSession drmSession) {
            return false;
        }

        public static <T extends ExoMediaCrypto> void replaceSession(DrmSession<T> drmSession, DrmSession<T> drmSession2) {
            if (drmSession != drmSession2) {
                if (drmSession2 != null) {
                    drmSession2.acquire();
                }
                if (drmSession != null) {
                    drmSession.release();
                }
            }
        }
    }

    public static class DrmSessionException extends IOException {
        public DrmSessionException(Throwable th) {
            super(th);
        }
    }

    @Documented
    @Retention(RetentionPolicy.SOURCE)
    public @interface State {
    }

    void acquire();

    DrmSessionException getError();

    T getMediaCrypto();

    byte[] getOfflineLicenseKeySetId();

    int getState();

    boolean playClearSamplesWithoutKeys();

    Map<String, String> queryKeyStatus();

    void release();
}
