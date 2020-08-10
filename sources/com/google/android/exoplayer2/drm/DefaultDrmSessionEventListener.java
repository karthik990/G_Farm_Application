package com.google.android.exoplayer2.drm;

public interface DefaultDrmSessionEventListener {

    /* renamed from: com.google.android.exoplayer2.drm.DefaultDrmSessionEventListener$-CC reason: invalid class name */
    public final /* synthetic */ class CC {
        public static void $default$onDrmKeysLoaded(DefaultDrmSessionEventListener defaultDrmSessionEventListener) {
        }

        public static void $default$onDrmKeysRemoved(DefaultDrmSessionEventListener defaultDrmSessionEventListener) {
        }

        public static void $default$onDrmKeysRestored(DefaultDrmSessionEventListener defaultDrmSessionEventListener) {
        }

        public static void $default$onDrmSessionAcquired(DefaultDrmSessionEventListener defaultDrmSessionEventListener) {
        }

        public static void $default$onDrmSessionManagerError(DefaultDrmSessionEventListener defaultDrmSessionEventListener, Exception exc) {
        }

        public static void $default$onDrmSessionReleased(DefaultDrmSessionEventListener defaultDrmSessionEventListener) {
        }
    }

    void onDrmKeysLoaded();

    void onDrmKeysRemoved();

    void onDrmKeysRestored();

    void onDrmSessionAcquired();

    void onDrmSessionManagerError(Exception exc);

    void onDrmSessionReleased();
}
