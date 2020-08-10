package com.google.android.exoplayer2.drm;

import android.media.MediaDrmException;
import android.os.PersistableBundle;
import com.google.android.exoplayer2.drm.DrmInitData.SchemeData;
import com.google.android.exoplayer2.drm.ExoMediaCrypto;
import com.google.android.exoplayer2.drm.ExoMediaDrm.KeyRequest;
import com.google.android.exoplayer2.drm.ExoMediaDrm.OnEventListener;
import com.google.android.exoplayer2.drm.ExoMediaDrm.OnKeyStatusChangeListener;
import com.google.android.exoplayer2.drm.ExoMediaDrm.ProvisionRequest;
import com.google.android.exoplayer2.util.Util;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class DummyExoMediaDrm<T extends ExoMediaCrypto> implements ExoMediaDrm<T> {
    public void acquire() {
    }

    public void closeSession(byte[] bArr) {
    }

    public Class<T> getExoMediaCryptoType() {
        return null;
    }

    public PersistableBundle getMetrics() {
        return null;
    }

    public String getPropertyString(String str) {
        return "";
    }

    public void release() {
    }

    public void setOnEventListener(OnEventListener<? super T> onEventListener) {
    }

    public void setOnKeyStatusChangeListener(OnKeyStatusChangeListener<? super T> onKeyStatusChangeListener) {
    }

    public void setPropertyByteArray(String str, byte[] bArr) {
    }

    public void setPropertyString(String str, String str2) {
    }

    public static <T extends ExoMediaCrypto> DummyExoMediaDrm<T> getInstance() {
        return new DummyExoMediaDrm<>();
    }

    public byte[] openSession() throws MediaDrmException {
        throw new MediaDrmException("Attempting to open a session using a dummy ExoMediaDrm.");
    }

    public KeyRequest getKeyRequest(byte[] bArr, List<SchemeData> list, int i, HashMap<String, String> hashMap) {
        throw new IllegalStateException();
    }

    public byte[] provideKeyResponse(byte[] bArr, byte[] bArr2) {
        throw new IllegalStateException();
    }

    public ProvisionRequest getProvisionRequest() {
        throw new IllegalStateException();
    }

    public void provideProvisionResponse(byte[] bArr) {
        throw new IllegalStateException();
    }

    public Map<String, String> queryKeyStatus(byte[] bArr) {
        throw new IllegalStateException();
    }

    public void restoreKeys(byte[] bArr, byte[] bArr2) {
        throw new IllegalStateException();
    }

    public byte[] getPropertyByteArray(String str) {
        return Util.EMPTY_BYTE_ARRAY;
    }

    public T createMediaCrypto(byte[] bArr) {
        throw new IllegalStateException();
    }
}
