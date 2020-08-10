package com.google.android.exoplayer2.drm;

import android.media.DeniedByServerException;
import android.media.MediaCryptoException;
import android.media.MediaDrm;
import android.media.MediaDrmException;
import android.media.NotProvisionedException;
import android.media.UnsupportedSchemeException;
import android.os.PersistableBundle;
import android.text.TextUtils;
import com.google.android.exoplayer2.C1996C;
import com.google.android.exoplayer2.drm.DrmInitData.SchemeData;
import com.google.android.exoplayer2.drm.ExoMediaDrm.KeyRequest;
import com.google.android.exoplayer2.drm.ExoMediaDrm.OnEventListener;
import com.google.android.exoplayer2.drm.ExoMediaDrm.OnKeyStatusChangeListener;
import com.google.android.exoplayer2.drm.ExoMediaDrm.Provider;
import com.google.android.exoplayer2.drm.ExoMediaDrm.ProvisionRequest;
import com.google.android.exoplayer2.extractor.mp4.PsshAtomUtil;
import com.google.android.exoplayer2.util.Assertions;
import com.google.android.exoplayer2.util.Log;
import com.google.android.exoplayer2.util.MimeTypes;
import com.google.android.exoplayer2.util.ParsableByteArray;
import com.google.android.exoplayer2.util.Util;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public final class FrameworkMediaDrm implements ExoMediaDrm<FrameworkMediaCrypto> {
    private static final String CENC_SCHEME_MIME_TYPE = "cenc";
    public static final Provider<FrameworkMediaCrypto> DEFAULT_PROVIDER = $$Lambda$FrameworkMediaDrm$fyEvjdXZtw4JXHhoyBbC_yZqs.INSTANCE;
    private static final String MOCK_LA_URL = "<LA_URL>https://x</LA_URL>";
    private static final String MOCK_LA_URL_VALUE = "https://x";
    private static final String TAG = "FrameworkMediaDrm";
    private static final int UTF_16_BYTES_PER_CHARACTER = 2;
    private final MediaDrm mediaDrm;
    private int referenceCount = 1;
    private final UUID uuid;

    static /* synthetic */ ExoMediaDrm lambda$static$0(UUID uuid2) {
        try {
            return newInstance(uuid2);
        } catch (UnsupportedDrmException unused) {
            StringBuilder sb = new StringBuilder();
            sb.append("Failed to instantiate a FrameworkMediaDrm for uuid: ");
            sb.append(uuid2);
            sb.append(".");
            Log.m1392e(TAG, sb.toString());
            return new DummyExoMediaDrm();
        }
    }

    public static FrameworkMediaDrm newInstance(UUID uuid2) throws UnsupportedDrmException {
        try {
            return new FrameworkMediaDrm(uuid2);
        } catch (UnsupportedSchemeException e) {
            throw new UnsupportedDrmException(1, e);
        } catch (Exception e2) {
            throw new UnsupportedDrmException(2, e2);
        }
    }

    private FrameworkMediaDrm(UUID uuid2) throws UnsupportedSchemeException {
        Assertions.checkNotNull(uuid2);
        Assertions.checkArgument(!C1996C.COMMON_PSSH_UUID.equals(uuid2), "Use C.CLEARKEY_UUID instead");
        this.uuid = uuid2;
        this.mediaDrm = new MediaDrm(adjustUuid(uuid2));
        if (C1996C.WIDEVINE_UUID.equals(uuid2) && needsForceWidevineL3Workaround()) {
            forceWidevineL3(this.mediaDrm);
        }
    }

    public void setOnEventListener(OnEventListener<? super FrameworkMediaCrypto> onEventListener) {
        this.mediaDrm.setOnEventListener(onEventListener == null ? null : new MediaDrm.OnEventListener(onEventListener) {
            private final /* synthetic */ OnEventListener f$1;

            {
                this.f$1 = r2;
            }

            public final void onEvent(MediaDrm mediaDrm, byte[] bArr, int i, int i2, byte[] bArr2) {
                FrameworkMediaDrm.this.lambda$setOnEventListener$1$FrameworkMediaDrm(this.f$1, mediaDrm, bArr, i, i2, bArr2);
            }
        });
    }

    public /* synthetic */ void lambda$setOnEventListener$1$FrameworkMediaDrm(OnEventListener onEventListener, MediaDrm mediaDrm2, byte[] bArr, int i, int i2, byte[] bArr2) {
        onEventListener.onEvent(this, bArr, i, i2, bArr2);
    }

    public void setOnKeyStatusChangeListener(OnKeyStatusChangeListener<? super FrameworkMediaCrypto> onKeyStatusChangeListener) {
        if (Util.SDK_INT >= 23) {
            this.mediaDrm.setOnKeyStatusChangeListener(onKeyStatusChangeListener == null ? null : new MediaDrm.OnKeyStatusChangeListener(onKeyStatusChangeListener) {
                private final /* synthetic */ OnKeyStatusChangeListener f$1;

                {
                    this.f$1 = r2;
                }

                public final void onKeyStatusChange(MediaDrm mediaDrm, byte[] bArr, List list, boolean z) {
                    FrameworkMediaDrm.this.lambda$setOnKeyStatusChangeListener$2$FrameworkMediaDrm(this.f$1, mediaDrm, bArr, list, z);
                }
            }, null);
            return;
        }
        throw new UnsupportedOperationException();
    }

    /* JADX WARNING: Incorrect type for immutable var: ssa=java.util.List, code=java.util.List<android.media.MediaDrm$KeyStatus>, for r7v0, types: [java.util.List, java.util.List<android.media.MediaDrm$KeyStatus>] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public /* synthetic */ void lambda$setOnKeyStatusChangeListener$2$FrameworkMediaDrm(com.google.android.exoplayer2.drm.ExoMediaDrm.OnKeyStatusChangeListener r4, android.media.MediaDrm r5, byte[] r6, java.util.List<android.media.MediaDrm.KeyStatus> r7, boolean r8) {
        /*
            r3 = this;
            java.util.ArrayList r5 = new java.util.ArrayList
            r5.<init>()
            java.util.Iterator r7 = r7.iterator()
        L_0x0009:
            boolean r0 = r7.hasNext()
            if (r0 == 0) goto L_0x0026
            java.lang.Object r0 = r7.next()
            android.media.MediaDrm$KeyStatus r0 = (android.media.MediaDrm.KeyStatus) r0
            com.google.android.exoplayer2.drm.ExoMediaDrm$KeyStatus r1 = new com.google.android.exoplayer2.drm.ExoMediaDrm$KeyStatus
            int r2 = r0.getStatusCode()
            byte[] r0 = r0.getKeyId()
            r1.<init>(r2, r0)
            r5.add(r1)
            goto L_0x0009
        L_0x0026:
            r4.onKeyStatusChange(r3, r6, r5, r8)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.exoplayer2.drm.FrameworkMediaDrm.lambda$setOnKeyStatusChangeListener$2$FrameworkMediaDrm(com.google.android.exoplayer2.drm.ExoMediaDrm$OnKeyStatusChangeListener, android.media.MediaDrm, byte[], java.util.List, boolean):void");
    }

    public byte[] openSession() throws MediaDrmException {
        return this.mediaDrm.openSession();
    }

    public void closeSession(byte[] bArr) {
        this.mediaDrm.closeSession(bArr);
    }

    public KeyRequest getKeyRequest(byte[] bArr, List<SchemeData> list, int i, HashMap<String, String> hashMap) throws NotProvisionedException {
        String str;
        byte[] bArr2;
        SchemeData schemeData = null;
        if (list != null) {
            schemeData = getSchemeData(this.uuid, list);
            bArr2 = adjustRequestInitData(this.uuid, (byte[]) Assertions.checkNotNull(schemeData.data));
            str = adjustRequestMimeType(this.uuid, schemeData.mimeType);
        } else {
            bArr2 = null;
            str = null;
        }
        MediaDrm.KeyRequest keyRequest = this.mediaDrm.getKeyRequest(bArr, bArr2, str, i, hashMap);
        byte[] adjustRequestData = adjustRequestData(this.uuid, keyRequest.getData());
        String defaultUrl = keyRequest.getDefaultUrl();
        if (MOCK_LA_URL_VALUE.equals(defaultUrl)) {
            defaultUrl = "";
        }
        if (TextUtils.isEmpty(defaultUrl) && schemeData != null && !TextUtils.isEmpty(schemeData.licenseServerUrl)) {
            defaultUrl = schemeData.licenseServerUrl;
        }
        return new KeyRequest(adjustRequestData, defaultUrl);
    }

    public byte[] provideKeyResponse(byte[] bArr, byte[] bArr2) throws NotProvisionedException, DeniedByServerException {
        if (C1996C.CLEARKEY_UUID.equals(this.uuid)) {
            bArr2 = ClearKeyUtil.adjustResponseData(bArr2);
        }
        return this.mediaDrm.provideKeyResponse(bArr, bArr2);
    }

    public ProvisionRequest getProvisionRequest() {
        MediaDrm.ProvisionRequest provisionRequest = this.mediaDrm.getProvisionRequest();
        return new ProvisionRequest(provisionRequest.getData(), provisionRequest.getDefaultUrl());
    }

    public void provideProvisionResponse(byte[] bArr) throws DeniedByServerException {
        this.mediaDrm.provideProvisionResponse(bArr);
    }

    public Map<String, String> queryKeyStatus(byte[] bArr) {
        return this.mediaDrm.queryKeyStatus(bArr);
    }

    public synchronized void acquire() {
        Assertions.checkState(this.referenceCount > 0);
        this.referenceCount++;
    }

    public synchronized void release() {
        int i = this.referenceCount - 1;
        this.referenceCount = i;
        if (i == 0) {
            this.mediaDrm.release();
        }
    }

    public void restoreKeys(byte[] bArr, byte[] bArr2) {
        this.mediaDrm.restoreKeys(bArr, bArr2);
    }

    public PersistableBundle getMetrics() {
        if (Util.SDK_INT < 28) {
            return null;
        }
        return this.mediaDrm.getMetrics();
    }

    public String getPropertyString(String str) {
        return this.mediaDrm.getPropertyString(str);
    }

    public byte[] getPropertyByteArray(String str) {
        return this.mediaDrm.getPropertyByteArray(str);
    }

    public void setPropertyString(String str, String str2) {
        this.mediaDrm.setPropertyString(str, str2);
    }

    public void setPropertyByteArray(String str, byte[] bArr) {
        this.mediaDrm.setPropertyByteArray(str, bArr);
    }

    public FrameworkMediaCrypto createMediaCrypto(byte[] bArr) throws MediaCryptoException {
        boolean z;
        if (Util.SDK_INT < 21 && C1996C.WIDEVINE_UUID.equals(this.uuid)) {
            if ("L3".equals(getPropertyString("securityLevel"))) {
                z = true;
                return new FrameworkMediaCrypto(adjustUuid(this.uuid), bArr, z);
            }
        }
        z = false;
        return new FrameworkMediaCrypto(adjustUuid(this.uuid), bArr, z);
    }

    public Class<FrameworkMediaCrypto> getExoMediaCryptoType() {
        return FrameworkMediaCrypto.class;
    }

    private static SchemeData getSchemeData(UUID uuid2, List<SchemeData> list) {
        boolean z;
        if (!C1996C.WIDEVINE_UUID.equals(uuid2)) {
            return (SchemeData) list.get(0);
        }
        if (Util.SDK_INT >= 28 && list.size() > 1) {
            SchemeData schemeData = (SchemeData) list.get(0);
            int i = 0;
            int i2 = 0;
            while (true) {
                if (i >= list.size()) {
                    z = true;
                    break;
                }
                SchemeData schemeData2 = (SchemeData) list.get(i);
                byte[] bArr = (byte[]) Util.castNonNull(schemeData2.data);
                if (!Util.areEqual(schemeData2.mimeType, schemeData.mimeType) || !Util.areEqual(schemeData2.licenseServerUrl, schemeData.licenseServerUrl) || !PsshAtomUtil.isPsshAtom(bArr)) {
                    z = false;
                } else {
                    i2 += bArr.length;
                    i++;
                }
            }
            z = false;
            if (z) {
                byte[] bArr2 = new byte[i2];
                int i3 = 0;
                for (int i4 = 0; i4 < list.size(); i4++) {
                    byte[] bArr3 = (byte[]) Util.castNonNull(((SchemeData) list.get(i4)).data);
                    int length = bArr3.length;
                    System.arraycopy(bArr3, 0, bArr2, i3, length);
                    i3 += length;
                }
                return schemeData.copyWithData(bArr2);
            }
        }
        for (int i5 = 0; i5 < list.size(); i5++) {
            SchemeData schemeData3 = (SchemeData) list.get(i5);
            int parseVersion = PsshAtomUtil.parseVersion((byte[]) Util.castNonNull(schemeData3.data));
            if (Util.SDK_INT < 23 && parseVersion == 0) {
                return schemeData3;
            }
            if (Util.SDK_INT >= 23 && parseVersion == 1) {
                return schemeData3;
            }
        }
        return (SchemeData) list.get(0);
    }

    private static UUID adjustUuid(UUID uuid2) {
        return (Util.SDK_INT >= 27 || !C1996C.CLEARKEY_UUID.equals(uuid2)) ? uuid2 : C1996C.COMMON_PSSH_UUID;
    }

    /* JADX WARNING: Code restructure failed: missing block: B:19:0x0056, code lost:
        if ("AFTM".equals(com.google.android.exoplayer2.util.Util.MODEL) != false) goto L_0x0058;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private static byte[] adjustRequestInitData(java.util.UUID r2, byte[] r3) {
        /*
            java.util.UUID r0 = com.google.android.exoplayer2.C1996C.PLAYREADY_UUID
            boolean r0 = r0.equals(r2)
            if (r0 == 0) goto L_0x001a
            byte[] r0 = com.google.android.exoplayer2.extractor.mp4.PsshAtomUtil.parseSchemeSpecificData(r3, r2)
            if (r0 != 0) goto L_0x000f
            goto L_0x0010
        L_0x000f:
            r3 = r0
        L_0x0010:
            java.util.UUID r0 = com.google.android.exoplayer2.C1996C.PLAYREADY_UUID
            byte[] r3 = addLaUrlAttributeIfMissing(r3)
            byte[] r3 = com.google.android.exoplayer2.extractor.mp4.PsshAtomUtil.buildPsshAtom(r0, r3)
        L_0x001a:
            int r0 = com.google.android.exoplayer2.util.Util.SDK_INT
            r1 = 21
            if (r0 >= r1) goto L_0x0028
            java.util.UUID r0 = com.google.android.exoplayer2.C1996C.WIDEVINE_UUID
            boolean r0 = r0.equals(r2)
            if (r0 != 0) goto L_0x0058
        L_0x0028:
            java.util.UUID r0 = com.google.android.exoplayer2.C1996C.PLAYREADY_UUID
            boolean r0 = r0.equals(r2)
            if (r0 == 0) goto L_0x005f
            java.lang.String r0 = com.google.android.exoplayer2.util.Util.MANUFACTURER
            java.lang.String r1 = "Amazon"
            boolean r0 = r1.equals(r0)
            if (r0 == 0) goto L_0x005f
            java.lang.String r0 = com.google.android.exoplayer2.util.Util.MODEL
            java.lang.String r1 = "AFTB"
            boolean r0 = r1.equals(r0)
            if (r0 != 0) goto L_0x0058
            java.lang.String r0 = com.google.android.exoplayer2.util.Util.MODEL
            java.lang.String r1 = "AFTS"
            boolean r0 = r1.equals(r0)
            if (r0 != 0) goto L_0x0058
            java.lang.String r0 = com.google.android.exoplayer2.util.Util.MODEL
            java.lang.String r1 = "AFTM"
            boolean r0 = r1.equals(r0)
            if (r0 == 0) goto L_0x005f
        L_0x0058:
            byte[] r2 = com.google.android.exoplayer2.extractor.mp4.PsshAtomUtil.parseSchemeSpecificData(r3, r2)
            if (r2 == 0) goto L_0x005f
            return r2
        L_0x005f:
            return r3
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.exoplayer2.drm.FrameworkMediaDrm.adjustRequestInitData(java.util.UUID, byte[]):byte[]");
    }

    private static String adjustRequestMimeType(UUID uuid2, String str) {
        return (Util.SDK_INT >= 26 || !C1996C.CLEARKEY_UUID.equals(uuid2) || (!MimeTypes.VIDEO_MP4.equals(str) && !MimeTypes.AUDIO_MP4.equals(str))) ? str : "cenc";
    }

    private static byte[] adjustRequestData(UUID uuid2, byte[] bArr) {
        return C1996C.CLEARKEY_UUID.equals(uuid2) ? ClearKeyUtil.adjustRequestData(bArr) : bArr;
    }

    private static void forceWidevineL3(MediaDrm mediaDrm2) {
        mediaDrm2.setPropertyString("securityLevel", "L3");
    }

    private static boolean needsForceWidevineL3Workaround() {
        return "ASUS_Z00AD".equals(Util.MODEL);
    }

    private static byte[] addLaUrlAttributeIfMissing(byte[] bArr) {
        ParsableByteArray parsableByteArray = new ParsableByteArray(bArr);
        int readLittleEndianInt = parsableByteArray.readLittleEndianInt();
        short readLittleEndianShort = parsableByteArray.readLittleEndianShort();
        short readLittleEndianShort2 = parsableByteArray.readLittleEndianShort();
        String str = TAG;
        if (readLittleEndianShort == 1 && readLittleEndianShort2 == 1) {
            String str2 = "UTF-16LE";
            String readString = parsableByteArray.readString(parsableByteArray.readLittleEndianShort(), Charset.forName(str2));
            if (readString.contains("<LA_URL>")) {
                return bArr;
            }
            int indexOf = readString.indexOf("</DATA>");
            if (indexOf == -1) {
                Log.m1396w(str, "Could not find the </DATA> tag. Skipping LA_URL workaround.");
            }
            StringBuilder sb = new StringBuilder();
            sb.append(readString.substring(0, indexOf));
            sb.append(MOCK_LA_URL);
            sb.append(readString.substring(indexOf));
            String sb2 = sb.toString();
            int i = readLittleEndianInt + 52;
            ByteBuffer allocate = ByteBuffer.allocate(i);
            allocate.order(ByteOrder.LITTLE_ENDIAN);
            allocate.putInt(i);
            allocate.putShort((short) readLittleEndianShort);
            allocate.putShort((short) readLittleEndianShort2);
            allocate.putShort((short) (sb2.length() * 2));
            allocate.put(sb2.getBytes(Charset.forName(str2)));
            return allocate.array();
        }
        Log.m1394i(str, "Unexpected record count or type. Skipping LA_URL workaround.");
        return bArr;
    }
}
