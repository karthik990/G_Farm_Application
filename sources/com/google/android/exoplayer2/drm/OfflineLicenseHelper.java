package com.google.android.exoplayer2.drm;

import android.os.ConditionVariable;
import android.os.Handler;
import android.os.HandlerThread;
import android.util.Pair;
import com.google.android.exoplayer2.C1996C;
import com.google.android.exoplayer2.drm.DefaultDrmSessionEventListener.CC;
import com.google.android.exoplayer2.drm.DefaultDrmSessionManager.Builder;
import com.google.android.exoplayer2.drm.DrmInitData.SchemeData;
import com.google.android.exoplayer2.drm.DrmSession.DrmSessionException;
import com.google.android.exoplayer2.drm.ExoMediaCrypto;
import com.google.android.exoplayer2.drm.ExoMediaDrm.Provider;
import com.google.android.exoplayer2.upstream.HttpDataSource.Factory;
import com.google.android.exoplayer2.util.Assertions;
import java.util.Collections;
import java.util.Map;
import java.util.UUID;

public final class OfflineLicenseHelper<T extends ExoMediaCrypto> {
    private static final DrmInitData DUMMY_DRM_INIT_DATA = new DrmInitData(new SchemeData[0]);
    /* access modifiers changed from: private */
    public final ConditionVariable conditionVariable;
    private final DefaultDrmSessionManager<T> drmSessionManager;
    private final HandlerThread handlerThread = new HandlerThread("OfflineLicenseHelper");

    public static OfflineLicenseHelper<FrameworkMediaCrypto> newWidevineInstance(String str, Factory factory) throws UnsupportedDrmException {
        return newWidevineInstance(str, false, factory, null);
    }

    public static OfflineLicenseHelper<FrameworkMediaCrypto> newWidevineInstance(String str, boolean z, Factory factory) throws UnsupportedDrmException {
        return newWidevineInstance(str, z, factory, null);
    }

    public static OfflineLicenseHelper<FrameworkMediaCrypto> newWidevineInstance(String str, boolean z, Factory factory, Map<String, String> map) throws UnsupportedDrmException {
        return new OfflineLicenseHelper<>(C1996C.WIDEVINE_UUID, FrameworkMediaDrm.DEFAULT_PROVIDER, new HttpMediaDrmCallback(str, z, factory), map);
    }

    public OfflineLicenseHelper(UUID uuid, Provider<T> provider, MediaDrmCallback mediaDrmCallback, Map<String, String> map) {
        this.handlerThread.start();
        this.conditionVariable = new ConditionVariable();
        C20291 r0 = new DefaultDrmSessionEventListener() {
            public /* synthetic */ void onDrmSessionAcquired() {
                CC.$default$onDrmSessionAcquired(this);
            }

            public /* synthetic */ void onDrmSessionReleased() {
                CC.$default$onDrmSessionReleased(this);
            }

            public void onDrmKeysLoaded() {
                OfflineLicenseHelper.this.conditionVariable.open();
            }

            public void onDrmSessionManagerError(Exception exc) {
                OfflineLicenseHelper.this.conditionVariable.open();
            }

            public void onDrmKeysRestored() {
                OfflineLicenseHelper.this.conditionVariable.open();
            }

            public void onDrmKeysRemoved() {
                OfflineLicenseHelper.this.conditionVariable.open();
            }
        };
        if (map == null) {
            map = Collections.emptyMap();
        }
        this.drmSessionManager = new Builder().setUuidAndExoMediaDrmProvider(uuid, provider).setKeyRequestParameters(map).build(mediaDrmCallback);
        this.drmSessionManager.addListener(new Handler(this.handlerThread.getLooper()), r0);
    }

    public synchronized byte[] downloadLicense(DrmInitData drmInitData) throws DrmSessionException {
        Assertions.checkArgument(drmInitData != null);
        return blockingKeyRequest(2, null, drmInitData);
    }

    public synchronized byte[] renewLicense(byte[] bArr) throws DrmSessionException {
        Assertions.checkNotNull(bArr);
        return blockingKeyRequest(2, bArr, DUMMY_DRM_INIT_DATA);
    }

    public synchronized void releaseLicense(byte[] bArr) throws DrmSessionException {
        Assertions.checkNotNull(bArr);
        blockingKeyRequest(3, bArr, DUMMY_DRM_INIT_DATA);
    }

    public synchronized Pair<Long, Long> getLicenseDurationRemainingSec(byte[] bArr) throws DrmSessionException {
        Assertions.checkNotNull(bArr);
        this.drmSessionManager.prepare();
        DrmSession openBlockingKeyRequest = openBlockingKeyRequest(1, bArr, DUMMY_DRM_INIT_DATA);
        DrmSessionException error = openBlockingKeyRequest.getError();
        Pair licenseDurationRemainingSec = WidevineUtil.getLicenseDurationRemainingSec(openBlockingKeyRequest);
        openBlockingKeyRequest.release();
        this.drmSessionManager.release();
        if (error == null) {
            return (Pair) Assertions.checkNotNull(licenseDurationRemainingSec);
        } else if (error.getCause() instanceof KeysExpiredException) {
            return Pair.create(Long.valueOf(0), Long.valueOf(0));
        } else {
            throw error;
        }
    }

    public void release() {
        this.handlerThread.quit();
    }

    private byte[] blockingKeyRequest(int i, byte[] bArr, DrmInitData drmInitData) throws DrmSessionException {
        this.drmSessionManager.prepare();
        DrmSession openBlockingKeyRequest = openBlockingKeyRequest(i, bArr, drmInitData);
        DrmSessionException error = openBlockingKeyRequest.getError();
        byte[] offlineLicenseKeySetId = openBlockingKeyRequest.getOfflineLicenseKeySetId();
        openBlockingKeyRequest.release();
        this.drmSessionManager.release();
        if (error == null) {
            return (byte[]) Assertions.checkNotNull(offlineLicenseKeySetId);
        }
        throw error;
    }

    private DrmSession<T> openBlockingKeyRequest(int i, byte[] bArr, DrmInitData drmInitData) {
        this.drmSessionManager.setMode(i, bArr);
        this.conditionVariable.close();
        DrmSession<T> acquireSession = this.drmSessionManager.acquireSession(this.handlerThread.getLooper(), drmInitData);
        this.conditionVariable.block();
        return acquireSession;
    }
}
