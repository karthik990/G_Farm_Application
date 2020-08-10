package com.google.android.exoplayer2.drm;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import com.google.android.exoplayer2.C1996C;
import com.google.android.exoplayer2.drm.DefaultDrmSession.ProvisioningManager;
import com.google.android.exoplayer2.drm.DefaultDrmSession.ReleaseCallback;
import com.google.android.exoplayer2.drm.DefaultDrmSessionManager.MissingSchemeDataException;
import com.google.android.exoplayer2.drm.DrmInitData.SchemeData;
import com.google.android.exoplayer2.drm.DrmSession.DrmSessionException;
import com.google.android.exoplayer2.drm.ExoMediaCrypto;
import com.google.android.exoplayer2.drm.ExoMediaDrm.AppManagedProvider;
import com.google.android.exoplayer2.drm.ExoMediaDrm.OnEventListener;
import com.google.android.exoplayer2.drm.ExoMediaDrm.Provider;
import com.google.android.exoplayer2.upstream.DefaultLoadErrorHandlingPolicy;
import com.google.android.exoplayer2.upstream.LoadErrorHandlingPolicy;
import com.google.android.exoplayer2.util.Assertions;
import com.google.android.exoplayer2.util.EventDispatcher;
import com.google.android.exoplayer2.util.EventDispatcher.Event;
import com.google.android.exoplayer2.util.Log;
import com.google.android.exoplayer2.util.Util;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class DefaultDrmSessionManager<T extends ExoMediaCrypto> implements DrmSessionManager<T> {
    public static final int INITIAL_DRM_REQUEST_RETRY_COUNT = 3;
    public static final int MODE_DOWNLOAD = 2;
    public static final int MODE_PLAYBACK = 0;
    public static final int MODE_QUERY = 1;
    public static final int MODE_RELEASE = 3;
    public static final String PLAYREADY_CUSTOM_DATA_KEY = "PRCustomData";
    private static final String TAG = "DefaultDrmSessionMgr";
    private final MediaDrmCallback callback;
    private final EventDispatcher<DefaultDrmSessionEventListener> eventDispatcher;
    private ExoMediaDrm<T> exoMediaDrm;
    private final Provider<T> exoMediaDrmProvider;
    private final HashMap<String, String> keyRequestParameters;
    private final LoadErrorHandlingPolicy loadErrorHandlingPolicy;
    volatile MediaDrmHandler mediaDrmHandler;
    private int mode;
    private final boolean multiSession;
    private DefaultDrmSession<T> noMultiSessionDrmSession;
    private byte[] offlineLicenseKeySetId;
    private DefaultDrmSession<T> placeholderDrmSession;
    private final boolean playClearSamplesWithoutKeys;
    private Looper playbackLooper;
    private int prepareCallsCount;
    private final ProvisioningManagerImpl provisioningManagerImpl;
    /* access modifiers changed from: private */
    public final List<DefaultDrmSession<T>> provisioningSessions;
    /* access modifiers changed from: private */
    public final List<DefaultDrmSession<T>> sessions;
    private final int[] useDrmSessionsForClearContentTrackTypes;
    private final UUID uuid;

    public static final class Builder {
        private Provider<ExoMediaCrypto> exoMediaDrmProvider = FrameworkMediaDrm.DEFAULT_PROVIDER;
        private final HashMap<String, String> keyRequestParameters = new HashMap<>();
        private LoadErrorHandlingPolicy loadErrorHandlingPolicy = new DefaultLoadErrorHandlingPolicy();
        private boolean multiSession;
        private boolean playClearSamplesWithoutKeys;
        private int[] useDrmSessionsForClearContentTrackTypes = new int[0];
        private UUID uuid = C1996C.WIDEVINE_UUID;

        public Builder setKeyRequestParameters(Map<String, String> map) {
            this.keyRequestParameters.clear();
            this.keyRequestParameters.putAll((Map) Assertions.checkNotNull(map));
            return this;
        }

        public Builder setUuidAndExoMediaDrmProvider(UUID uuid2, Provider provider) {
            this.uuid = (UUID) Assertions.checkNotNull(uuid2);
            this.exoMediaDrmProvider = (Provider) Assertions.checkNotNull(provider);
            return this;
        }

        public Builder setMultiSession(boolean z) {
            this.multiSession = z;
            return this;
        }

        public Builder setUseDrmSessionsForClearContent(int... iArr) {
            for (int i : iArr) {
                boolean z = true;
                if (!(i == 2 || i == 1)) {
                    z = false;
                }
                Assertions.checkArgument(z);
            }
            this.useDrmSessionsForClearContentTrackTypes = (int[]) iArr.clone();
            return this;
        }

        public Builder setPlayClearSamplesWithoutKeys(boolean z) {
            this.playClearSamplesWithoutKeys = z;
            return this;
        }

        public Builder setLoadErrorHandlingPolicy(LoadErrorHandlingPolicy loadErrorHandlingPolicy2) {
            this.loadErrorHandlingPolicy = (LoadErrorHandlingPolicy) Assertions.checkNotNull(loadErrorHandlingPolicy2);
            return this;
        }

        public DefaultDrmSessionManager<ExoMediaCrypto> build(MediaDrmCallback mediaDrmCallback) {
            DefaultDrmSessionManager defaultDrmSessionManager = new DefaultDrmSessionManager(this.uuid, this.exoMediaDrmProvider, mediaDrmCallback, this.keyRequestParameters, this.multiSession, this.useDrmSessionsForClearContentTrackTypes, this.playClearSamplesWithoutKeys, this.loadErrorHandlingPolicy);
            return defaultDrmSessionManager;
        }
    }

    private class MediaDrmEventListener implements OnEventListener<T> {
        private MediaDrmEventListener() {
        }

        public void onEvent(ExoMediaDrm<? extends T> exoMediaDrm, byte[] bArr, int i, int i2, byte[] bArr2) {
            ((MediaDrmHandler) Assertions.checkNotNull(DefaultDrmSessionManager.this.mediaDrmHandler)).obtainMessage(i, bArr).sendToTarget();
        }
    }

    private class MediaDrmHandler extends Handler {
        public MediaDrmHandler(Looper looper) {
            super(looper);
        }

        public void handleMessage(Message message) {
            byte[] bArr = (byte[]) message.obj;
            if (bArr != null) {
                Iterator it = DefaultDrmSessionManager.this.sessions.iterator();
                while (true) {
                    if (!it.hasNext()) {
                        break;
                    }
                    DefaultDrmSession defaultDrmSession = (DefaultDrmSession) it.next();
                    if (defaultDrmSession.hasSessionId(bArr)) {
                        defaultDrmSession.onMediaDrmEvent(message.what);
                        break;
                    }
                }
            }
        }
    }

    public static final class MissingSchemeDataException extends Exception {
        private MissingSchemeDataException(UUID uuid) {
            StringBuilder sb = new StringBuilder();
            sb.append("Media does not support uuid: ");
            sb.append(uuid);
            super(sb.toString());
        }
    }

    @Documented
    @Retention(RetentionPolicy.SOURCE)
    public @interface Mode {
    }

    private class ProvisioningManagerImpl implements ProvisioningManager<T> {
        private ProvisioningManagerImpl() {
        }

        public void provisionRequired(DefaultDrmSession<T> defaultDrmSession) {
            if (!DefaultDrmSessionManager.this.provisioningSessions.contains(defaultDrmSession)) {
                DefaultDrmSessionManager.this.provisioningSessions.add(defaultDrmSession);
                if (DefaultDrmSessionManager.this.provisioningSessions.size() == 1) {
                    defaultDrmSession.provision();
                }
            }
        }

        public void onProvisionCompleted() {
            for (DefaultDrmSession onProvisionCompleted : DefaultDrmSessionManager.this.provisioningSessions) {
                onProvisionCompleted.onProvisionCompleted();
            }
            DefaultDrmSessionManager.this.provisioningSessions.clear();
        }

        public void onProvisionError(Exception exc) {
            for (DefaultDrmSession onProvisionError : DefaultDrmSessionManager.this.provisioningSessions) {
                onProvisionError.onProvisionError(exc);
            }
            DefaultDrmSessionManager.this.provisioningSessions.clear();
        }
    }

    @Deprecated
    public DefaultDrmSessionManager(UUID uuid2, ExoMediaDrm<T> exoMediaDrm2, MediaDrmCallback mediaDrmCallback, HashMap<String, String> hashMap) {
        if (hashMap == null) {
            hashMap = new HashMap<>();
        }
        this(uuid2, exoMediaDrm2, mediaDrmCallback, hashMap, false, 3);
    }

    @Deprecated
    public DefaultDrmSessionManager(UUID uuid2, ExoMediaDrm<T> exoMediaDrm2, MediaDrmCallback mediaDrmCallback, HashMap<String, String> hashMap, boolean z) {
        if (hashMap == null) {
            hashMap = new HashMap<>();
        }
        this(uuid2, exoMediaDrm2, mediaDrmCallback, hashMap, z, 3);
    }

    @Deprecated
    public DefaultDrmSessionManager(UUID uuid2, ExoMediaDrm<T> exoMediaDrm2, MediaDrmCallback mediaDrmCallback, HashMap<String, String> hashMap, boolean z, int i) {
        AppManagedProvider appManagedProvider = new AppManagedProvider(exoMediaDrm2);
        if (hashMap == null) {
            hashMap = new HashMap<>();
        }
        this(uuid2, appManagedProvider, mediaDrmCallback, hashMap, z, new int[0], false, new DefaultLoadErrorHandlingPolicy(i));
    }

    private DefaultDrmSessionManager(UUID uuid2, Provider<T> provider, MediaDrmCallback mediaDrmCallback, HashMap<String, String> hashMap, boolean z, int[] iArr, boolean z2, LoadErrorHandlingPolicy loadErrorHandlingPolicy2) {
        Assertions.checkNotNull(uuid2);
        Assertions.checkArgument(!C1996C.COMMON_PSSH_UUID.equals(uuid2), "Use C.CLEARKEY_UUID instead");
        this.uuid = uuid2;
        this.exoMediaDrmProvider = provider;
        this.callback = mediaDrmCallback;
        this.keyRequestParameters = hashMap;
        this.eventDispatcher = new EventDispatcher<>();
        this.multiSession = z;
        this.useDrmSessionsForClearContentTrackTypes = iArr;
        this.playClearSamplesWithoutKeys = z2;
        this.loadErrorHandlingPolicy = loadErrorHandlingPolicy2;
        this.provisioningManagerImpl = new ProvisioningManagerImpl<>();
        this.mode = 0;
        this.sessions = new ArrayList();
        this.provisioningSessions = new ArrayList();
    }

    public final void addListener(Handler handler, DefaultDrmSessionEventListener defaultDrmSessionEventListener) {
        this.eventDispatcher.addListener(handler, defaultDrmSessionEventListener);
    }

    public final void removeListener(DefaultDrmSessionEventListener defaultDrmSessionEventListener) {
        this.eventDispatcher.removeListener(defaultDrmSessionEventListener);
    }

    public void setMode(int i, byte[] bArr) {
        Assertions.checkState(this.sessions.isEmpty());
        if (i == 1 || i == 3) {
            Assertions.checkNotNull(bArr);
        }
        this.mode = i;
        this.offlineLicenseKeySetId = bArr;
    }

    public final void prepare() {
        int i = this.prepareCallsCount;
        this.prepareCallsCount = i + 1;
        if (i == 0) {
            Assertions.checkState(this.exoMediaDrm == null);
            this.exoMediaDrm = this.exoMediaDrmProvider.acquireExoMediaDrm(this.uuid);
            this.exoMediaDrm.setOnEventListener(new MediaDrmEventListener());
        }
    }

    public final void release() {
        int i = this.prepareCallsCount - 1;
        this.prepareCallsCount = i;
        if (i == 0) {
            ((ExoMediaDrm) Assertions.checkNotNull(this.exoMediaDrm)).release();
            this.exoMediaDrm = null;
        }
    }

    public boolean canAcquireSession(DrmInitData drmInitData) {
        boolean z = true;
        if (this.offlineLicenseKeySetId != null) {
            return true;
        }
        if (getSchemeDatas(drmInitData, this.uuid, true).isEmpty()) {
            if (drmInitData.schemeDataCount != 1 || !drmInitData.get(0).matches(C1996C.COMMON_PSSH_UUID)) {
                return false;
            }
            StringBuilder sb = new StringBuilder();
            sb.append("DrmInitData only contains common PSSH SchemeData. Assuming support for: ");
            sb.append(this.uuid);
            Log.m1396w(TAG, sb.toString());
        }
        String str = drmInitData.schemeType;
        if (str != null && !C1996C.CENC_TYPE_cenc.equals(str)) {
            if (!C1996C.CENC_TYPE_cbc1.equals(str) && !C1996C.CENC_TYPE_cbcs.equals(str) && !C1996C.CENC_TYPE_cens.equals(str)) {
                return true;
            }
            if (Util.SDK_INT < 25) {
                z = false;
            }
        }
        return z;
    }

    public DrmSession<T> acquirePlaceholderSession(Looper looper, int i) {
        assertExpectedPlaybackLooper(looper);
        ExoMediaDrm exoMediaDrm2 = (ExoMediaDrm) Assertions.checkNotNull(this.exoMediaDrm);
        if ((FrameworkMediaCrypto.class.equals(exoMediaDrm2.getExoMediaCryptoType()) && FrameworkMediaCrypto.WORKAROUND_DEVICE_NEEDS_KEYS_TO_CONFIGURE_CODEC) || Util.linearSearch(this.useDrmSessionsForClearContentTrackTypes, i) == -1 || exoMediaDrm2.getExoMediaCryptoType() == null) {
            return null;
        }
        maybeCreateMediaDrmHandler(looper);
        if (this.placeholderDrmSession == null) {
            DefaultDrmSession<T> createNewDefaultSession = createNewDefaultSession(Collections.emptyList(), true);
            this.sessions.add(createNewDefaultSession);
            this.placeholderDrmSession = createNewDefaultSession;
        }
        this.placeholderDrmSession.acquire();
        return this.placeholderDrmSession;
    }

    public DrmSession<T> acquireSession(Looper looper, DrmInitData drmInitData) {
        List list;
        assertExpectedPlaybackLooper(looper);
        maybeCreateMediaDrmHandler(looper);
        DefaultDrmSession<T> defaultDrmSession = null;
        if (this.offlineLicenseKeySetId == null) {
            list = getSchemeDatas(drmInitData, this.uuid, false);
            if (list.isEmpty()) {
                MissingSchemeDataException missingSchemeDataException = new MissingSchemeDataException(this.uuid);
                this.eventDispatcher.dispatch(new Event() {
                    public final void sendTo(Object obj) {
                        ((DefaultDrmSessionEventListener) obj).onDrmSessionManagerError(MissingSchemeDataException.this);
                    }
                });
                return new ErrorStateDrmSession(new DrmSessionException(missingSchemeDataException));
            }
        } else {
            list = null;
        }
        if (this.multiSession) {
            Iterator it = this.sessions.iterator();
            while (true) {
                if (!it.hasNext()) {
                    break;
                }
                DefaultDrmSession<T> defaultDrmSession2 = (DefaultDrmSession) it.next();
                if (Util.areEqual(defaultDrmSession2.schemeDatas, list)) {
                    defaultDrmSession = defaultDrmSession2;
                    break;
                }
            }
        } else {
            defaultDrmSession = this.noMultiSessionDrmSession;
        }
        if (defaultDrmSession == null) {
            defaultDrmSession = createNewDefaultSession(list, false);
            if (!this.multiSession) {
                this.noMultiSessionDrmSession = defaultDrmSession;
            }
            this.sessions.add(defaultDrmSession);
        }
        defaultDrmSession.acquire();
        return defaultDrmSession;
    }

    public Class<T> getExoMediaCryptoType(DrmInitData drmInitData) {
        if (canAcquireSession(drmInitData)) {
            return ((ExoMediaDrm) Assertions.checkNotNull(this.exoMediaDrm)).getExoMediaCryptoType();
        }
        return null;
    }

    private void assertExpectedPlaybackLooper(Looper looper) {
        Looper looper2 = this.playbackLooper;
        Assertions.checkState(looper2 == null || looper2 == looper);
        this.playbackLooper = looper;
    }

    private void maybeCreateMediaDrmHandler(Looper looper) {
        if (this.mediaDrmHandler == null) {
            this.mediaDrmHandler = new MediaDrmHandler<>(looper);
        }
    }

    private DefaultDrmSession<T> createNewDefaultSession(List<SchemeData> list, boolean z) {
        Assertions.checkNotNull(this.exoMediaDrm);
        boolean z2 = this.playClearSamplesWithoutKeys | z;
        DefaultDrmSession defaultDrmSession = new DefaultDrmSession(this.uuid, this.exoMediaDrm, this.provisioningManagerImpl, new ReleaseCallback() {
            public final void onSessionReleased(DefaultDrmSession defaultDrmSession) {
                DefaultDrmSessionManager.this.onSessionReleased(defaultDrmSession);
            }
        }, list, this.mode, z2, z, this.offlineLicenseKeySetId, this.keyRequestParameters, this.callback, (Looper) Assertions.checkNotNull(this.playbackLooper), this.eventDispatcher, this.loadErrorHandlingPolicy);
        return defaultDrmSession;
    }

    /* access modifiers changed from: private */
    public void onSessionReleased(DefaultDrmSession<T> defaultDrmSession) {
        this.sessions.remove(defaultDrmSession);
        if (this.placeholderDrmSession == defaultDrmSession) {
            this.placeholderDrmSession = null;
        }
        if (this.noMultiSessionDrmSession == defaultDrmSession) {
            this.noMultiSessionDrmSession = null;
        }
        if (this.provisioningSessions.size() > 1 && this.provisioningSessions.get(0) == defaultDrmSession) {
            ((DefaultDrmSession) this.provisioningSessions.get(1)).provision();
        }
        this.provisioningSessions.remove(defaultDrmSession);
    }

    private static List<SchemeData> getSchemeDatas(DrmInitData drmInitData, UUID uuid2, boolean z) {
        ArrayList arrayList = new ArrayList(drmInitData.schemeDataCount);
        for (int i = 0; i < drmInitData.schemeDataCount; i++) {
            SchemeData schemeData = drmInitData.get(i);
            if ((schemeData.matches(uuid2) || (C1996C.CLEARKEY_UUID.equals(uuid2) && schemeData.matches(C1996C.COMMON_PSSH_UUID))) && (schemeData.data != null || z)) {
                arrayList.add(schemeData);
            }
        }
        return arrayList;
    }
}
