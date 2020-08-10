package com.google.android.exoplayer2.drm;

import android.media.NotProvisionedException;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;
import android.os.SystemClock;
import android.util.Pair;
import com.google.android.exoplayer2.C1996C;
import com.google.android.exoplayer2.drm.DrmInitData.SchemeData;
import com.google.android.exoplayer2.drm.DrmSession.DrmSessionException;
import com.google.android.exoplayer2.drm.ExoMediaCrypto;
import com.google.android.exoplayer2.drm.ExoMediaDrm.KeyRequest;
import com.google.android.exoplayer2.drm.ExoMediaDrm.ProvisionRequest;
import com.google.android.exoplayer2.upstream.LoadErrorHandlingPolicy;
import com.google.android.exoplayer2.util.Assertions;
import com.google.android.exoplayer2.util.EventDispatcher;
import com.google.android.exoplayer2.util.EventDispatcher.Event;
import com.google.android.exoplayer2.util.Log;
import com.google.android.exoplayer2.util.Util;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import org.checkerframework.checker.nullness.qual.EnsuresNonNullIf;
import org.checkerframework.checker.nullness.qual.RequiresNonNull;

class DefaultDrmSession<T extends ExoMediaCrypto> implements DrmSession<T> {
    private static final int MAX_LICENSE_DURATION_TO_RENEW_SECONDS = 60;
    private static final int MSG_KEYS = 1;
    private static final int MSG_PROVISION = 0;
    private static final String TAG = "DefaultDrmSession";
    final MediaDrmCallback callback;
    private KeyRequest currentKeyRequest;
    private ProvisionRequest currentProvisionRequest;
    private final EventDispatcher<DefaultDrmSessionEventListener> eventDispatcher;
    private final boolean isPlaceholderSession;
    private final HashMap<String, String> keyRequestParameters;
    private DrmSessionException lastException;
    /* access modifiers changed from: private */
    public final LoadErrorHandlingPolicy loadErrorHandlingPolicy;
    private T mediaCrypto;
    private final ExoMediaDrm<T> mediaDrm;
    private final int mode;
    private byte[] offlineLicenseKeySetId;
    private final boolean playClearSamplesWithoutKeys;
    private final ProvisioningManager<T> provisioningManager;
    private int referenceCount;
    private final ReleaseCallback<T> releaseCallback;
    private RequestHandler requestHandler;
    private HandlerThread requestHandlerThread;
    final ResponseHandler responseHandler;
    public final List<SchemeData> schemeDatas;
    private byte[] sessionId;
    private int state;
    final UUID uuid;

    public interface ProvisioningManager<T extends ExoMediaCrypto> {
        void onProvisionCompleted();

        void onProvisionError(Exception exc);

        void provisionRequired(DefaultDrmSession<T> defaultDrmSession);
    }

    public interface ReleaseCallback<T extends ExoMediaCrypto> {
        void onSessionReleased(DefaultDrmSession<T> defaultDrmSession);
    }

    private class RequestHandler extends Handler {
        public RequestHandler(Looper looper) {
            super(looper);
        }

        /* access modifiers changed from: 0000 */
        public void post(int i, Object obj, boolean z) {
            obtainMessage(i, new RequestTask(z, SystemClock.elapsedRealtime(), obj)).sendToTarget();
        }

        public void handleMessage(Message message) {
            RequestTask requestTask = (RequestTask) message.obj;
            try {
                int i = message.what;
                if (i == 0) {
                    e = DefaultDrmSession.this.callback.executeProvisionRequest(DefaultDrmSession.this.uuid, (ProvisionRequest) requestTask.request);
                } else if (i == 1) {
                    e = DefaultDrmSession.this.callback.executeKeyRequest(DefaultDrmSession.this.uuid, (KeyRequest) requestTask.request);
                } else {
                    throw new RuntimeException();
                }
            } catch (Exception e) {
                e = e;
                if (maybeRetryRequest(message, e)) {
                    return;
                }
            }
            DefaultDrmSession.this.responseHandler.obtainMessage(message.what, Pair.create(requestTask.request, e)).sendToTarget();
        }

        private boolean maybeRetryRequest(Message message, Exception exc) {
            RequestTask requestTask = (RequestTask) message.obj;
            if (!requestTask.allowRetry) {
                return false;
            }
            requestTask.errorCount++;
            if (requestTask.errorCount > DefaultDrmSession.this.loadErrorHandlingPolicy.getMinimumLoadableRetryCount(3)) {
                return false;
            }
            long retryDelayMsFor = DefaultDrmSession.this.loadErrorHandlingPolicy.getRetryDelayMsFor(3, SystemClock.elapsedRealtime() - requestTask.startTimeMs, exc instanceof IOException ? (IOException) exc : new UnexpectedDrmSessionException(exc), requestTask.errorCount);
            if (retryDelayMsFor == C1996C.TIME_UNSET) {
                return false;
            }
            sendMessageDelayed(Message.obtain(message), retryDelayMsFor);
            return true;
        }
    }

    private static final class RequestTask {
        public final boolean allowRetry;
        public int errorCount;
        public final Object request;
        public final long startTimeMs;

        public RequestTask(boolean z, long j, Object obj) {
            this.allowRetry = z;
            this.startTimeMs = j;
            this.request = obj;
        }
    }

    private class ResponseHandler extends Handler {
        public ResponseHandler(Looper looper) {
            super(looper);
        }

        public void handleMessage(Message message) {
            Pair pair = (Pair) message.obj;
            Object obj = pair.first;
            Object obj2 = pair.second;
            int i = message.what;
            if (i == 0) {
                DefaultDrmSession.this.onProvisionResponse(obj, obj2);
            } else if (i == 1) {
                DefaultDrmSession.this.onKeyResponse(obj, obj2);
            }
        }
    }

    public static final class UnexpectedDrmSessionException extends IOException {
        public UnexpectedDrmSessionException(Throwable th) {
            StringBuilder sb = new StringBuilder();
            sb.append("Unexpected ");
            sb.append(th.getClass().getSimpleName());
            sb.append(": ");
            sb.append(th.getMessage());
            super(sb.toString(), th);
        }
    }

    public DefaultDrmSession(UUID uuid2, ExoMediaDrm<T> exoMediaDrm, ProvisioningManager<T> provisioningManager2, ReleaseCallback<T> releaseCallback2, List<SchemeData> list, int i, boolean z, boolean z2, byte[] bArr, HashMap<String, String> hashMap, MediaDrmCallback mediaDrmCallback, Looper looper, EventDispatcher<DefaultDrmSessionEventListener> eventDispatcher2, LoadErrorHandlingPolicy loadErrorHandlingPolicy2) {
        if (i == 1 || i == 3) {
            Assertions.checkNotNull(bArr);
        }
        this.uuid = uuid2;
        this.provisioningManager = provisioningManager2;
        this.releaseCallback = releaseCallback2;
        this.mediaDrm = exoMediaDrm;
        this.mode = i;
        this.playClearSamplesWithoutKeys = z;
        this.isPlaceholderSession = z2;
        if (bArr != null) {
            this.offlineLicenseKeySetId = bArr;
            this.schemeDatas = null;
        } else {
            this.schemeDatas = Collections.unmodifiableList((List) Assertions.checkNotNull(list));
        }
        this.keyRequestParameters = hashMap;
        this.callback = mediaDrmCallback;
        this.eventDispatcher = eventDispatcher2;
        this.loadErrorHandlingPolicy = loadErrorHandlingPolicy2;
        this.state = 2;
        this.responseHandler = new ResponseHandler<>(looper);
    }

    public boolean hasSessionId(byte[] bArr) {
        return Arrays.equals(this.sessionId, bArr);
    }

    public void onMediaDrmEvent(int i) {
        if (i == 2) {
            onKeysRequired();
        }
    }

    public void provision() {
        this.currentProvisionRequest = this.mediaDrm.getProvisionRequest();
        ((RequestHandler) Util.castNonNull(this.requestHandler)).post(0, Assertions.checkNotNull(this.currentProvisionRequest), true);
    }

    public void onProvisionCompleted() {
        if (openInternal(false)) {
            doLicense(true);
        }
    }

    public void onProvisionError(Exception exc) {
        onError(exc);
    }

    public final int getState() {
        return this.state;
    }

    public boolean playClearSamplesWithoutKeys() {
        return this.playClearSamplesWithoutKeys;
    }

    public final DrmSessionException getError() {
        if (this.state == 1) {
            return this.lastException;
        }
        return null;
    }

    public final T getMediaCrypto() {
        return this.mediaCrypto;
    }

    public Map<String, String> queryKeyStatus() {
        byte[] bArr = this.sessionId;
        if (bArr == null) {
            return null;
        }
        return this.mediaDrm.queryKeyStatus(bArr);
    }

    public byte[] getOfflineLicenseKeySetId() {
        return this.offlineLicenseKeySetId;
    }

    public void acquire() {
        boolean z = false;
        Assertions.checkState(this.referenceCount >= 0);
        int i = this.referenceCount + 1;
        this.referenceCount = i;
        if (i == 1) {
            if (this.state == 2) {
                z = true;
            }
            Assertions.checkState(z);
            this.requestHandlerThread = new HandlerThread("DrmRequestHandler");
            this.requestHandlerThread.start();
            this.requestHandler = new RequestHandler<>(this.requestHandlerThread.getLooper());
            if (openInternal(true)) {
                doLicense(true);
            }
        }
    }

    public void release() {
        int i = this.referenceCount - 1;
        this.referenceCount = i;
        if (i == 0) {
            this.state = 0;
            ((ResponseHandler) Util.castNonNull(this.responseHandler)).removeCallbacksAndMessages(null);
            ((RequestHandler) Util.castNonNull(this.requestHandler)).removeCallbacksAndMessages(null);
            this.requestHandler = null;
            ((HandlerThread) Util.castNonNull(this.requestHandlerThread)).quit();
            this.requestHandlerThread = null;
            this.mediaCrypto = null;
            this.lastException = null;
            this.currentKeyRequest = null;
            this.currentProvisionRequest = null;
            byte[] bArr = this.sessionId;
            if (bArr != null) {
                this.mediaDrm.closeSession(bArr);
                this.sessionId = null;
                this.eventDispatcher.dispatch($$Lambda$1U2yJBSMBm8ESUSz9LUzNXtoVus.INSTANCE);
            }
            this.releaseCallback.onSessionReleased(this);
        }
    }

    @EnsuresNonNullIf(expression = {"sessionId"}, result = true)
    private boolean openInternal(boolean z) {
        if (isOpen()) {
            return true;
        }
        try {
            this.sessionId = this.mediaDrm.openSession();
            this.mediaCrypto = this.mediaDrm.createMediaCrypto(this.sessionId);
            this.eventDispatcher.dispatch($$Lambda$jFcVU4qXZB2nhSZWHXCB9S7MtRI.INSTANCE);
            this.state = 3;
            Assertions.checkNotNull(this.sessionId);
            return true;
        } catch (NotProvisionedException e) {
            if (z) {
                this.provisioningManager.provisionRequired(this);
            } else {
                onError(e);
            }
            return false;
        } catch (Exception e2) {
            onError(e2);
            return false;
        }
    }

    /* access modifiers changed from: private */
    public void onProvisionResponse(Object obj, Object obj2) {
        if (obj == this.currentProvisionRequest && (this.state == 2 || isOpen())) {
            this.currentProvisionRequest = null;
            if (obj2 instanceof Exception) {
                this.provisioningManager.onProvisionError((Exception) obj2);
                return;
            }
            try {
                this.mediaDrm.provideProvisionResponse((byte[]) obj2);
                this.provisioningManager.onProvisionCompleted();
            } catch (Exception e) {
                this.provisioningManager.onProvisionError(e);
            }
        }
    }

    @RequiresNonNull({"sessionId"})
    private void doLicense(boolean z) {
        if (!this.isPlaceholderSession) {
            byte[] bArr = (byte[]) Util.castNonNull(this.sessionId);
            int i = this.mode;
            if (i == 0 || i == 1) {
                if (this.offlineLicenseKeySetId == null) {
                    postKeyRequest(bArr, 1, z);
                } else if (this.state == 4 || restoreKeys()) {
                    long licenseDurationRemainingSec = getLicenseDurationRemainingSec();
                    if (this.mode == 0 && licenseDurationRemainingSec <= 60) {
                        StringBuilder sb = new StringBuilder();
                        sb.append("Offline license has expired or will expire soon. Remaining seconds: ");
                        sb.append(licenseDurationRemainingSec);
                        Log.m1390d(TAG, sb.toString());
                        postKeyRequest(bArr, 2, z);
                    } else if (licenseDurationRemainingSec <= 0) {
                        onError(new KeysExpiredException());
                    } else {
                        this.state = 4;
                        this.eventDispatcher.dispatch($$Lambda$tzysvANfjWo6mXRxYD2fQMdks_4.INSTANCE);
                    }
                }
            } else if (i != 2) {
                if (i == 3) {
                    Assertions.checkNotNull(this.offlineLicenseKeySetId);
                    Assertions.checkNotNull(this.sessionId);
                    if (restoreKeys()) {
                        postKeyRequest(this.offlineLicenseKeySetId, 3, z);
                    }
                }
            } else if (this.offlineLicenseKeySetId == null || restoreKeys()) {
                postKeyRequest(bArr, 2, z);
            }
        }
    }

    @RequiresNonNull({"sessionId", "offlineLicenseKeySetId"})
    private boolean restoreKeys() {
        try {
            this.mediaDrm.restoreKeys(this.sessionId, this.offlineLicenseKeySetId);
            return true;
        } catch (Exception e) {
            Log.m1393e(TAG, "Error trying to restore keys.", e);
            onError(e);
            return false;
        }
    }

    private long getLicenseDurationRemainingSec() {
        if (!C1996C.WIDEVINE_UUID.equals(this.uuid)) {
            return Long.MAX_VALUE;
        }
        Pair pair = (Pair) Assertions.checkNotNull(WidevineUtil.getLicenseDurationRemainingSec(this));
        return Math.min(((Long) pair.first).longValue(), ((Long) pair.second).longValue());
    }

    private void postKeyRequest(byte[] bArr, int i, boolean z) {
        try {
            this.currentKeyRequest = this.mediaDrm.getKeyRequest(bArr, this.schemeDatas, i, this.keyRequestParameters);
            ((RequestHandler) Util.castNonNull(this.requestHandler)).post(1, Assertions.checkNotNull(this.currentKeyRequest), z);
        } catch (Exception e) {
            onKeysError(e);
        }
    }

    /* access modifiers changed from: private */
    public void onKeyResponse(Object obj, Object obj2) {
        if (obj == this.currentKeyRequest && isOpen()) {
            this.currentKeyRequest = null;
            if (obj2 instanceof Exception) {
                onKeysError((Exception) obj2);
                return;
            }
            try {
                byte[] bArr = (byte[]) obj2;
                if (this.mode == 3) {
                    this.mediaDrm.provideKeyResponse((byte[]) Util.castNonNull(this.offlineLicenseKeySetId), bArr);
                    this.eventDispatcher.dispatch($$Lambda$tzysvANfjWo6mXRxYD2fQMdks_4.INSTANCE);
                } else {
                    byte[] provideKeyResponse = this.mediaDrm.provideKeyResponse(this.sessionId, bArr);
                    if (!((this.mode != 2 && (this.mode != 0 || this.offlineLicenseKeySetId == null)) || provideKeyResponse == null || provideKeyResponse.length == 0)) {
                        this.offlineLicenseKeySetId = provideKeyResponse;
                    }
                    this.state = 4;
                    this.eventDispatcher.dispatch($$Lambda$wyKVEWJALn1OyjwryLo2GUxlQ2M.INSTANCE);
                }
            } catch (Exception e) {
                onKeysError(e);
            }
        }
    }

    private void onKeysRequired() {
        if (this.mode == 0 && this.state == 4) {
            Util.castNonNull(this.sessionId);
            doLicense(false);
        }
    }

    private void onKeysError(Exception exc) {
        if (exc instanceof NotProvisionedException) {
            this.provisioningManager.provisionRequired(this);
        } else {
            onError(exc);
        }
    }

    private void onError(Exception exc) {
        this.lastException = new DrmSessionException(exc);
        this.eventDispatcher.dispatch(new Event(exc) {
            private final /* synthetic */ Exception f$0;

            {
                this.f$0 = r1;
            }

            public final void sendTo(Object obj) {
                ((DefaultDrmSessionEventListener) obj).onDrmSessionManagerError(this.f$0);
            }
        });
        if (this.state != 4) {
            this.state = 1;
        }
    }

    @EnsuresNonNullIf(expression = {"sessionId"}, result = true)
    private boolean isOpen() {
        int i = this.state;
        return i == 3 || i == 4;
    }
}
