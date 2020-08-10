package com.google.android.exoplayer2.mediacodec;

import android.media.MediaCodecInfo;
import android.media.MediaCodecInfo.CodecCapabilities;
import android.media.MediaCodecInfo.CodecProfileLevel;
import android.media.MediaCodecList;
import android.text.TextUtils;
import android.util.Pair;
import android.util.SparseIntArray;
import androidx.exifinterface.media.ExifInterface;
import com.google.android.exoplayer2.Format;
import com.google.android.exoplayer2.metadata.icy.IcyHeaders;
import com.google.android.exoplayer2.util.Log;
import com.google.android.exoplayer2.util.MimeTypes;
import com.google.android.exoplayer2.util.Util;
import com.google.android.exoplayer2.video.ColorInfo;
import com.google.api.client.googleapis.media.MediaHttpDownloader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.checkerframework.checker.nullness.qual.EnsuresNonNull;

public final class MediaCodecUtil {
    private static final SparseIntArray AV1_LEVEL_NUMBER_TO_CONST = new SparseIntArray();
    private static final SparseIntArray AVC_LEVEL_NUMBER_TO_CONST = new SparseIntArray();
    private static final SparseIntArray AVC_PROFILE_NUMBER_TO_CONST = new SparseIntArray();
    private static final String CODEC_ID_AV01 = "av01";
    private static final String CODEC_ID_AVC1 = "avc1";
    private static final String CODEC_ID_AVC2 = "avc2";
    private static final String CODEC_ID_HEV1 = "hev1";
    private static final String CODEC_ID_HVC1 = "hvc1";
    private static final String CODEC_ID_MP4A = "mp4a";
    private static final String CODEC_ID_VP09 = "vp09";
    private static final Map<String, Integer> DOLBY_VISION_STRING_TO_LEVEL = new HashMap();
    private static final Map<String, Integer> DOLBY_VISION_STRING_TO_PROFILE = new HashMap();
    private static final Map<String, Integer> HEVC_CODEC_STRING_TO_PROFILE_LEVEL = new HashMap();
    private static final SparseIntArray MP4A_AUDIO_OBJECT_TYPE_TO_PROFILE = new SparseIntArray();
    private static final Pattern PROFILE_PATTERN = Pattern.compile("^\\D?(\\d+)$");
    private static final String TAG = "MediaCodecUtil";
    private static final SparseIntArray VP9_LEVEL_NUMBER_TO_CONST = new SparseIntArray();
    private static final SparseIntArray VP9_PROFILE_NUMBER_TO_CONST = new SparseIntArray();
    private static final HashMap<CodecKey, List<MediaCodecInfo>> decoderInfosCache = new HashMap<>();
    private static int maxH264DecodableFrameSize = -1;

    private static final class CodecKey {
        public final String mimeType;
        public final boolean secure;
        public final boolean tunneling;

        public CodecKey(String str, boolean z, boolean z2) {
            this.mimeType = str;
            this.secure = z;
            this.tunneling = z2;
        }

        public int hashCode() {
            int i = 1231;
            int hashCode = (((this.mimeType.hashCode() + 31) * 31) + (this.secure ? 1231 : 1237)) * 31;
            if (!this.tunneling) {
                i = 1237;
            }
            return hashCode + i;
        }

        public boolean equals(Object obj) {
            boolean z = true;
            if (this == obj) {
                return true;
            }
            if (obj == null || obj.getClass() != CodecKey.class) {
                return false;
            }
            CodecKey codecKey = (CodecKey) obj;
            if (!(TextUtils.equals(this.mimeType, codecKey.mimeType) && this.secure == codecKey.secure && this.tunneling == codecKey.tunneling)) {
                z = false;
            }
            return z;
        }
    }

    public static class DecoderQueryException extends Exception {
        private DecoderQueryException(Throwable th) {
            super("Failed to query underlying media codecs", th);
        }
    }

    private interface MediaCodecListCompat {
        int getCodecCount();

        MediaCodecInfo getCodecInfoAt(int i);

        boolean isFeatureRequired(String str, String str2, CodecCapabilities codecCapabilities);

        boolean isFeatureSupported(String str, String str2, CodecCapabilities codecCapabilities);

        boolean secureDecodersExplicit();
    }

    private static final class MediaCodecListCompatV16 implements MediaCodecListCompat {
        public boolean isFeatureRequired(String str, String str2, CodecCapabilities codecCapabilities) {
            return false;
        }

        public boolean secureDecodersExplicit() {
            return false;
        }

        private MediaCodecListCompatV16() {
        }

        public int getCodecCount() {
            return MediaCodecList.getCodecCount();
        }

        public MediaCodecInfo getCodecInfoAt(int i) {
            return MediaCodecList.getCodecInfoAt(i);
        }

        public boolean isFeatureSupported(String str, String str2, CodecCapabilities codecCapabilities) {
            return "secure-playback".equals(str) && MimeTypes.VIDEO_H264.equals(str2);
        }
    }

    private static final class MediaCodecListCompatV21 implements MediaCodecListCompat {
        private final int codecKind;
        private MediaCodecInfo[] mediaCodecInfos;

        public boolean secureDecodersExplicit() {
            return true;
        }

        public MediaCodecListCompatV21(boolean z, boolean z2) {
            this.codecKind = (z || z2) ? 1 : 0;
        }

        public int getCodecCount() {
            ensureMediaCodecInfosInitialized();
            return this.mediaCodecInfos.length;
        }

        public MediaCodecInfo getCodecInfoAt(int i) {
            ensureMediaCodecInfosInitialized();
            return this.mediaCodecInfos[i];
        }

        public boolean isFeatureSupported(String str, String str2, CodecCapabilities codecCapabilities) {
            return codecCapabilities.isFeatureSupported(str);
        }

        public boolean isFeatureRequired(String str, String str2, CodecCapabilities codecCapabilities) {
            return codecCapabilities.isFeatureRequired(str);
        }

        @EnsuresNonNull({"mediaCodecInfos"})
        private void ensureMediaCodecInfosInitialized() {
            if (this.mediaCodecInfos == null) {
                this.mediaCodecInfos = new MediaCodecList(this.codecKind).getCodecInfos();
            }
        }
    }

    private interface ScoreProvider<T> {
        int getScore(T t);
    }

    private static int avcLevelToMaxFrameSize(int i) {
        if (i == 1 || i == 2) {
            return 25344;
        }
        switch (i) {
            case 8:
            case 16:
            case 32:
                return 101376;
            case 64:
                return 202752;
            case 128:
            case 256:
                return 414720;
            case 512:
                return 921600;
            case 1024:
                return 1310720;
            case 2048:
            case 4096:
                return 2097152;
            case 8192:
                return 2228224;
            case 16384:
                return 5652480;
            case 32768:
            case 65536:
                return 9437184;
            default:
                return -1;
        }
    }

    static {
        SparseIntArray sparseIntArray = AVC_PROFILE_NUMBER_TO_CONST;
        Integer valueOf = Integer.valueOf(1);
        sparseIntArray.put(66, 1);
        SparseIntArray sparseIntArray2 = AVC_PROFILE_NUMBER_TO_CONST;
        Integer valueOf2 = Integer.valueOf(2);
        sparseIntArray2.put(77, 2);
        SparseIntArray sparseIntArray3 = AVC_PROFILE_NUMBER_TO_CONST;
        Integer valueOf3 = Integer.valueOf(4);
        sparseIntArray3.put(88, 4);
        SparseIntArray sparseIntArray4 = AVC_PROFILE_NUMBER_TO_CONST;
        Integer valueOf4 = Integer.valueOf(8);
        sparseIntArray4.put(100, 8);
        SparseIntArray sparseIntArray5 = AVC_PROFILE_NUMBER_TO_CONST;
        Integer valueOf5 = Integer.valueOf(16);
        sparseIntArray5.put(110, 16);
        AVC_PROFILE_NUMBER_TO_CONST.put(122, 32);
        SparseIntArray sparseIntArray6 = AVC_PROFILE_NUMBER_TO_CONST;
        Integer valueOf6 = Integer.valueOf(64);
        sparseIntArray6.put(244, 64);
        AVC_LEVEL_NUMBER_TO_CONST.put(10, 1);
        AVC_LEVEL_NUMBER_TO_CONST.put(11, 4);
        AVC_LEVEL_NUMBER_TO_CONST.put(12, 8);
        AVC_LEVEL_NUMBER_TO_CONST.put(13, 16);
        AVC_LEVEL_NUMBER_TO_CONST.put(20, 32);
        AVC_LEVEL_NUMBER_TO_CONST.put(21, 64);
        AVC_LEVEL_NUMBER_TO_CONST.put(22, 128);
        SparseIntArray sparseIntArray7 = AVC_LEVEL_NUMBER_TO_CONST;
        Integer valueOf7 = Integer.valueOf(256);
        sparseIntArray7.put(30, 256);
        AVC_LEVEL_NUMBER_TO_CONST.put(31, 512);
        AVC_LEVEL_NUMBER_TO_CONST.put(32, 1024);
        AVC_LEVEL_NUMBER_TO_CONST.put(40, 2048);
        AVC_LEVEL_NUMBER_TO_CONST.put(41, 4096);
        AVC_LEVEL_NUMBER_TO_CONST.put(42, 8192);
        AVC_LEVEL_NUMBER_TO_CONST.put(50, 16384);
        AVC_LEVEL_NUMBER_TO_CONST.put(51, 32768);
        AVC_LEVEL_NUMBER_TO_CONST.put(52, 65536);
        VP9_PROFILE_NUMBER_TO_CONST.put(0, 1);
        VP9_PROFILE_NUMBER_TO_CONST.put(1, 2);
        VP9_PROFILE_NUMBER_TO_CONST.put(2, 4);
        VP9_PROFILE_NUMBER_TO_CONST.put(3, 8);
        VP9_LEVEL_NUMBER_TO_CONST.put(10, 1);
        VP9_LEVEL_NUMBER_TO_CONST.put(11, 2);
        VP9_LEVEL_NUMBER_TO_CONST.put(20, 4);
        VP9_LEVEL_NUMBER_TO_CONST.put(21, 8);
        VP9_LEVEL_NUMBER_TO_CONST.put(30, 16);
        VP9_LEVEL_NUMBER_TO_CONST.put(31, 32);
        VP9_LEVEL_NUMBER_TO_CONST.put(40, 64);
        VP9_LEVEL_NUMBER_TO_CONST.put(41, 128);
        VP9_LEVEL_NUMBER_TO_CONST.put(50, 256);
        VP9_LEVEL_NUMBER_TO_CONST.put(51, 512);
        VP9_LEVEL_NUMBER_TO_CONST.put(60, 2048);
        VP9_LEVEL_NUMBER_TO_CONST.put(61, 4096);
        VP9_LEVEL_NUMBER_TO_CONST.put(62, 8192);
        HEVC_CODEC_STRING_TO_PROFILE_LEVEL.put("L30", valueOf);
        HEVC_CODEC_STRING_TO_PROFILE_LEVEL.put("L60", valueOf3);
        HEVC_CODEC_STRING_TO_PROFILE_LEVEL.put("L63", valueOf5);
        HEVC_CODEC_STRING_TO_PROFILE_LEVEL.put("L90", valueOf6);
        HEVC_CODEC_STRING_TO_PROFILE_LEVEL.put("L93", valueOf7);
        HEVC_CODEC_STRING_TO_PROFILE_LEVEL.put("L120", Integer.valueOf(1024));
        HEVC_CODEC_STRING_TO_PROFILE_LEVEL.put("L123", Integer.valueOf(4096));
        HEVC_CODEC_STRING_TO_PROFILE_LEVEL.put("L150", Integer.valueOf(16384));
        HEVC_CODEC_STRING_TO_PROFILE_LEVEL.put("L153", Integer.valueOf(65536));
        HEVC_CODEC_STRING_TO_PROFILE_LEVEL.put("L156", Integer.valueOf(262144));
        HEVC_CODEC_STRING_TO_PROFILE_LEVEL.put("L180", Integer.valueOf(1048576));
        HEVC_CODEC_STRING_TO_PROFILE_LEVEL.put("L183", Integer.valueOf(4194304));
        HEVC_CODEC_STRING_TO_PROFILE_LEVEL.put("L186", Integer.valueOf(16777216));
        HEVC_CODEC_STRING_TO_PROFILE_LEVEL.put("H30", valueOf2);
        HEVC_CODEC_STRING_TO_PROFILE_LEVEL.put("H60", valueOf4);
        HEVC_CODEC_STRING_TO_PROFILE_LEVEL.put("H63", Integer.valueOf(32));
        HEVC_CODEC_STRING_TO_PROFILE_LEVEL.put("H90", Integer.valueOf(128));
        HEVC_CODEC_STRING_TO_PROFILE_LEVEL.put("H93", Integer.valueOf(512));
        HEVC_CODEC_STRING_TO_PROFILE_LEVEL.put("H120", Integer.valueOf(2048));
        HEVC_CODEC_STRING_TO_PROFILE_LEVEL.put("H123", Integer.valueOf(8192));
        HEVC_CODEC_STRING_TO_PROFILE_LEVEL.put("H150", Integer.valueOf(32768));
        HEVC_CODEC_STRING_TO_PROFILE_LEVEL.put("H153", Integer.valueOf(131072));
        HEVC_CODEC_STRING_TO_PROFILE_LEVEL.put("H156", Integer.valueOf(524288));
        HEVC_CODEC_STRING_TO_PROFILE_LEVEL.put("H180", Integer.valueOf(2097152));
        HEVC_CODEC_STRING_TO_PROFILE_LEVEL.put("H183", Integer.valueOf(8388608));
        HEVC_CODEC_STRING_TO_PROFILE_LEVEL.put("H186", Integer.valueOf(MediaHttpDownloader.MAXIMUM_CHUNK_SIZE));
        DOLBY_VISION_STRING_TO_PROFILE.put("00", valueOf);
        DOLBY_VISION_STRING_TO_PROFILE.put("01", valueOf2);
        DOLBY_VISION_STRING_TO_PROFILE.put("02", valueOf3);
        DOLBY_VISION_STRING_TO_PROFILE.put("03", valueOf4);
        DOLBY_VISION_STRING_TO_PROFILE.put("04", valueOf5);
        DOLBY_VISION_STRING_TO_PROFILE.put("05", Integer.valueOf(32));
        DOLBY_VISION_STRING_TO_PROFILE.put("06", valueOf6);
        DOLBY_VISION_STRING_TO_PROFILE.put("07", Integer.valueOf(128));
        DOLBY_VISION_STRING_TO_PROFILE.put("08", valueOf7);
        DOLBY_VISION_STRING_TO_PROFILE.put("09", Integer.valueOf(512));
        DOLBY_VISION_STRING_TO_LEVEL.put("01", valueOf);
        DOLBY_VISION_STRING_TO_LEVEL.put("02", valueOf2);
        DOLBY_VISION_STRING_TO_LEVEL.put("03", valueOf3);
        DOLBY_VISION_STRING_TO_LEVEL.put("04", valueOf4);
        DOLBY_VISION_STRING_TO_LEVEL.put("05", valueOf5);
        DOLBY_VISION_STRING_TO_LEVEL.put("06", Integer.valueOf(32));
        DOLBY_VISION_STRING_TO_LEVEL.put("07", valueOf6);
        DOLBY_VISION_STRING_TO_LEVEL.put("08", Integer.valueOf(128));
        DOLBY_VISION_STRING_TO_LEVEL.put("09", valueOf7);
        AV1_LEVEL_NUMBER_TO_CONST.put(0, 1);
        AV1_LEVEL_NUMBER_TO_CONST.put(1, 2);
        AV1_LEVEL_NUMBER_TO_CONST.put(2, 4);
        AV1_LEVEL_NUMBER_TO_CONST.put(3, 8);
        AV1_LEVEL_NUMBER_TO_CONST.put(4, 16);
        AV1_LEVEL_NUMBER_TO_CONST.put(5, 32);
        AV1_LEVEL_NUMBER_TO_CONST.put(6, 64);
        AV1_LEVEL_NUMBER_TO_CONST.put(7, 128);
        AV1_LEVEL_NUMBER_TO_CONST.put(8, 256);
        AV1_LEVEL_NUMBER_TO_CONST.put(9, 512);
        AV1_LEVEL_NUMBER_TO_CONST.put(10, 1024);
        AV1_LEVEL_NUMBER_TO_CONST.put(11, 2048);
        AV1_LEVEL_NUMBER_TO_CONST.put(12, 4096);
        AV1_LEVEL_NUMBER_TO_CONST.put(13, 8192);
        AV1_LEVEL_NUMBER_TO_CONST.put(14, 16384);
        AV1_LEVEL_NUMBER_TO_CONST.put(15, 32768);
        AV1_LEVEL_NUMBER_TO_CONST.put(16, 65536);
        AV1_LEVEL_NUMBER_TO_CONST.put(17, 131072);
        AV1_LEVEL_NUMBER_TO_CONST.put(18, 262144);
        AV1_LEVEL_NUMBER_TO_CONST.put(19, 524288);
        AV1_LEVEL_NUMBER_TO_CONST.put(20, 1048576);
        AV1_LEVEL_NUMBER_TO_CONST.put(21, 2097152);
        AV1_LEVEL_NUMBER_TO_CONST.put(22, 4194304);
        AV1_LEVEL_NUMBER_TO_CONST.put(23, 8388608);
        MP4A_AUDIO_OBJECT_TYPE_TO_PROFILE.put(1, 1);
        MP4A_AUDIO_OBJECT_TYPE_TO_PROFILE.put(2, 2);
        MP4A_AUDIO_OBJECT_TYPE_TO_PROFILE.put(3, 3);
        MP4A_AUDIO_OBJECT_TYPE_TO_PROFILE.put(4, 4);
        MP4A_AUDIO_OBJECT_TYPE_TO_PROFILE.put(5, 5);
        MP4A_AUDIO_OBJECT_TYPE_TO_PROFILE.put(6, 6);
        MP4A_AUDIO_OBJECT_TYPE_TO_PROFILE.put(17, 17);
        MP4A_AUDIO_OBJECT_TYPE_TO_PROFILE.put(20, 20);
        MP4A_AUDIO_OBJECT_TYPE_TO_PROFILE.put(23, 23);
        MP4A_AUDIO_OBJECT_TYPE_TO_PROFILE.put(29, 29);
        MP4A_AUDIO_OBJECT_TYPE_TO_PROFILE.put(39, 39);
        MP4A_AUDIO_OBJECT_TYPE_TO_PROFILE.put(42, 42);
    }

    private MediaCodecUtil() {
    }

    public static void warmDecoderInfoCache(String str, boolean z, boolean z2) {
        try {
            getDecoderInfos(str, z, z2);
        } catch (DecoderQueryException e) {
            Log.m1393e(TAG, "Codec warming failed", e);
        }
    }

    public static MediaCodecInfo getPassthroughDecoderInfo() throws DecoderQueryException {
        MediaCodecInfo decoderInfo = getDecoderInfo(MimeTypes.AUDIO_RAW, false, false);
        if (decoderInfo == null) {
            return null;
        }
        return MediaCodecInfo.newPassthroughInstance(decoderInfo.name);
    }

    public static MediaCodecInfo getDecoderInfo(String str, boolean z, boolean z2) throws DecoderQueryException {
        List decoderInfos = getDecoderInfos(str, z, z2);
        if (decoderInfos.isEmpty()) {
            return null;
        }
        return (MediaCodecInfo) decoderInfos.get(0);
    }

    public static synchronized List<MediaCodecInfo> getDecoderInfos(String str, boolean z, boolean z2) throws DecoderQueryException {
        synchronized (MediaCodecUtil.class) {
            CodecKey codecKey = new CodecKey(str, z, z2);
            List<MediaCodecInfo> list = (List) decoderInfosCache.get(codecKey);
            if (list != null) {
                return list;
            }
            ArrayList decoderInfosInternal = getDecoderInfosInternal(codecKey, Util.SDK_INT >= 21 ? new MediaCodecListCompatV21(z, z2) : new MediaCodecListCompatV16());
            if (z && decoderInfosInternal.isEmpty() && 21 <= Util.SDK_INT && Util.SDK_INT <= 23) {
                decoderInfosInternal = getDecoderInfosInternal(codecKey, new MediaCodecListCompatV16());
                if (!decoderInfosInternal.isEmpty()) {
                    String str2 = TAG;
                    StringBuilder sb = new StringBuilder();
                    sb.append("MediaCodecList API didn't list secure decoder for: ");
                    sb.append(str);
                    sb.append(". Assuming: ");
                    sb.append(((MediaCodecInfo) decoderInfosInternal.get(0)).name);
                    Log.m1396w(str2, sb.toString());
                }
            }
            applyWorkarounds(str, decoderInfosInternal);
            List<MediaCodecInfo> unmodifiableList = Collections.unmodifiableList(decoderInfosInternal);
            decoderInfosCache.put(codecKey, unmodifiableList);
            return unmodifiableList;
        }
    }

    public static List<MediaCodecInfo> getDecoderInfosSortedByFormatSupport(List<MediaCodecInfo> list, Format format) {
        ArrayList arrayList = new ArrayList(list);
        sortByScore(arrayList, new ScoreProvider() {
            public final int getScore(Object obj) {
                return MediaCodecUtil.lambda$getDecoderInfosSortedByFormatSupport$0(Format.this, (MediaCodecInfo) obj);
            }
        });
        return arrayList;
    }

    static /* synthetic */ int lambda$getDecoderInfosSortedByFormatSupport$0(Format format, MediaCodecInfo mediaCodecInfo) {
        try {
            return mediaCodecInfo.isFormatSupported(format) ? 1 : 0;
        } catch (DecoderQueryException unused) {
            return -1;
        }
    }

    public static int maxH264DecodableFrameSize() throws DecoderQueryException {
        if (maxH264DecodableFrameSize == -1) {
            int i = 0;
            MediaCodecInfo decoderInfo = getDecoderInfo(MimeTypes.VIDEO_H264, false, false);
            if (decoderInfo != null) {
                CodecProfileLevel[] profileLevels = decoderInfo.getProfileLevels();
                int length = profileLevels.length;
                int i2 = 0;
                while (i < length) {
                    i2 = Math.max(avcLevelToMaxFrameSize(profileLevels[i].level), i2);
                    i++;
                }
                i = Math.max(i2, Util.SDK_INT >= 21 ? 345600 : 172800);
            }
            maxH264DecodableFrameSize = i;
        }
        return maxH264DecodableFrameSize;
    }

    /* JADX WARNING: Code restructure failed: missing block: B:25:0x0065, code lost:
        if (r3.equals(CODEC_ID_AVC1) != false) goto L_0x0073;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static android.util.Pair<java.lang.Integer, java.lang.Integer> getCodecProfileAndLevel(com.google.android.exoplayer2.Format r6) {
        /*
            java.lang.String r0 = r6.codecs
            r1 = 0
            if (r0 != 0) goto L_0x0006
            return r1
        L_0x0006:
            java.lang.String r0 = r6.codecs
            java.lang.String r2 = "\\."
            java.lang.String[] r0 = r0.split(r2)
            java.lang.String r2 = r6.sampleMimeType
            java.lang.String r3 = "video/dolby-vision"
            boolean r2 = r3.equals(r2)
            if (r2 == 0) goto L_0x0020
            java.lang.String r6 = r6.codecs
            android.util.Pair r6 = getDolbyVisionProfileAndLevel(r6, r0)
            return r6
        L_0x0020:
            r2 = 0
            r3 = r0[r2]
            r4 = -1
            int r5 = r3.hashCode()
            switch(r5) {
                case 3004662: goto L_0x0068;
                case 3006243: goto L_0x005f;
                case 3006244: goto L_0x0055;
                case 3199032: goto L_0x004b;
                case 3214780: goto L_0x0041;
                case 3356560: goto L_0x0037;
                case 3624515: goto L_0x002c;
                default: goto L_0x002b;
            }
        L_0x002b:
            goto L_0x0072
        L_0x002c:
            java.lang.String r2 = "vp09"
            boolean r2 = r3.equals(r2)
            if (r2 == 0) goto L_0x0072
            r2 = 2
            goto L_0x0073
        L_0x0037:
            java.lang.String r2 = "mp4a"
            boolean r2 = r3.equals(r2)
            if (r2 == 0) goto L_0x0072
            r2 = 6
            goto L_0x0073
        L_0x0041:
            java.lang.String r2 = "hvc1"
            boolean r2 = r3.equals(r2)
            if (r2 == 0) goto L_0x0072
            r2 = 4
            goto L_0x0073
        L_0x004b:
            java.lang.String r2 = "hev1"
            boolean r2 = r3.equals(r2)
            if (r2 == 0) goto L_0x0072
            r2 = 3
            goto L_0x0073
        L_0x0055:
            java.lang.String r2 = "avc2"
            boolean r2 = r3.equals(r2)
            if (r2 == 0) goto L_0x0072
            r2 = 1
            goto L_0x0073
        L_0x005f:
            java.lang.String r5 = "avc1"
            boolean r3 = r3.equals(r5)
            if (r3 == 0) goto L_0x0072
            goto L_0x0073
        L_0x0068:
            java.lang.String r2 = "av01"
            boolean r2 = r3.equals(r2)
            if (r2 == 0) goto L_0x0072
            r2 = 5
            goto L_0x0073
        L_0x0072:
            r2 = -1
        L_0x0073:
            switch(r2) {
                case 0: goto L_0x0095;
                case 1: goto L_0x0095;
                case 2: goto L_0x008e;
                case 3: goto L_0x0087;
                case 4: goto L_0x0087;
                case 5: goto L_0x007e;
                case 6: goto L_0x0077;
                default: goto L_0x0076;
            }
        L_0x0076:
            return r1
        L_0x0077:
            java.lang.String r6 = r6.codecs
            android.util.Pair r6 = getAacCodecProfileAndLevel(r6, r0)
            return r6
        L_0x007e:
            java.lang.String r1 = r6.codecs
            com.google.android.exoplayer2.video.ColorInfo r6 = r6.colorInfo
            android.util.Pair r6 = getAv1ProfileAndLevel(r1, r0, r6)
            return r6
        L_0x0087:
            java.lang.String r6 = r6.codecs
            android.util.Pair r6 = getHevcProfileAndLevel(r6, r0)
            return r6
        L_0x008e:
            java.lang.String r6 = r6.codecs
            android.util.Pair r6 = getVp9ProfileAndLevel(r6, r0)
            return r6
        L_0x0095:
            java.lang.String r6 = r6.codecs
            android.util.Pair r6 = getAvcProfileAndLevel(r6, r0)
            return r6
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.exoplayer2.mediacodec.MediaCodecUtil.getCodecProfileAndLevel(com.google.android.exoplayer2.Format):android.util.Pair");
    }

    /* JADX WARNING: Code restructure failed: missing block: B:29:0x007a, code lost:
        if (r1.secure == false) goto L_0x007c;
     */
    /* JADX WARNING: Removed duplicated region for block: B:57:0x00fb A[SYNTHETIC, Splitter:B:57:0x00fb] */
    /* JADX WARNING: Removed duplicated region for block: B:70:0x0124 A[SYNTHETIC] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private static java.util.ArrayList<com.google.android.exoplayer2.mediacodec.MediaCodecInfo> getDecoderInfosInternal(com.google.android.exoplayer2.mediacodec.MediaCodecUtil.CodecKey r25, com.google.android.exoplayer2.mediacodec.MediaCodecUtil.MediaCodecListCompat r26) throws com.google.android.exoplayer2.mediacodec.MediaCodecUtil.DecoderQueryException {
        /*
            r1 = r25
            r2 = r26
            java.lang.String r3 = "secure-playback"
            java.lang.String r4 = "tunneled-playback"
            java.util.ArrayList r5 = new java.util.ArrayList     // Catch:{ Exception -> 0x0149 }
            r5.<init>()     // Catch:{ Exception -> 0x0149 }
            java.lang.String r15 = r1.mimeType     // Catch:{ Exception -> 0x0149 }
            int r14 = r26.getCodecCount()     // Catch:{ Exception -> 0x0149 }
            boolean r13 = r26.secureDecodersExplicit()     // Catch:{ Exception -> 0x0149 }
            r0 = 0
            r12 = 0
        L_0x001a:
            if (r12 >= r14) goto L_0x0148
            android.media.MediaCodecInfo r0 = r2.getCodecInfoAt(r12)     // Catch:{ Exception -> 0x0149 }
            java.lang.String r11 = r0.getName()     // Catch:{ Exception -> 0x0149 }
            java.lang.String r10 = getCodecMimeType(r0, r11, r13, r15)     // Catch:{ Exception -> 0x0149 }
            if (r10 != 0) goto L_0x0032
        L_0x002a:
            r22 = r12
            r23 = r13
            r24 = r14
            goto L_0x011a
        L_0x0032:
            android.media.MediaCodecInfo$CodecCapabilities r9 = r0.getCapabilitiesForType(r10)     // Catch:{ Exception -> 0x00e9 }
            boolean r6 = r2.isFeatureSupported(r4, r10, r9)     // Catch:{ Exception -> 0x00e9 }
            boolean r7 = r2.isFeatureRequired(r4, r10, r9)     // Catch:{ Exception -> 0x00e9 }
            boolean r8 = r1.tunneling     // Catch:{ Exception -> 0x00e9 }
            if (r8 != 0) goto L_0x0044
            if (r7 != 0) goto L_0x002a
        L_0x0044:
            boolean r7 = r1.tunneling     // Catch:{ Exception -> 0x00e9 }
            if (r7 == 0) goto L_0x004b
            if (r6 != 0) goto L_0x004b
            goto L_0x002a
        L_0x004b:
            boolean r6 = r2.isFeatureSupported(r3, r10, r9)     // Catch:{ Exception -> 0x00e9 }
            boolean r7 = r2.isFeatureRequired(r3, r10, r9)     // Catch:{ Exception -> 0x00e9 }
            boolean r8 = r1.secure     // Catch:{ Exception -> 0x00e9 }
            if (r8 != 0) goto L_0x0059
            if (r7 != 0) goto L_0x002a
        L_0x0059:
            boolean r7 = r1.secure     // Catch:{ Exception -> 0x00e9 }
            if (r7 == 0) goto L_0x0060
            if (r6 != 0) goto L_0x0060
            goto L_0x002a
        L_0x0060:
            boolean r16 = isHardwareAccelerated(r0)     // Catch:{ Exception -> 0x00e9 }
            boolean r17 = isSoftwareOnly(r0)     // Catch:{ Exception -> 0x00e9 }
            boolean r0 = isVendor(r0)     // Catch:{ Exception -> 0x00e9 }
            boolean r18 = codecNeedsDisableAdaptationWorkaround(r11)     // Catch:{ Exception -> 0x00e9 }
            if (r13 == 0) goto L_0x0076
            boolean r7 = r1.secure     // Catch:{ Exception -> 0x00e9 }
            if (r7 == r6) goto L_0x007c
        L_0x0076:
            if (r13 != 0) goto L_0x00ac
            boolean r7 = r1.secure     // Catch:{ Exception -> 0x00a1 }
            if (r7 != 0) goto L_0x00ac
        L_0x007c:
            r19 = 0
            r6 = r11
            r7 = r15
            r8 = r10
            r20 = r10
            r10 = r16
            r21 = r11
            r11 = r17
            r22 = r12
            r12 = r0
            r23 = r13
            r13 = r18
            r24 = r14
            r14 = r19
            com.google.android.exoplayer2.mediacodec.MediaCodecInfo r0 = com.google.android.exoplayer2.mediacodec.MediaCodecInfo.newInstance(r6, r7, r8, r9, r10, r11, r12, r13, r14)     // Catch:{ Exception -> 0x009d }
            r5.add(r0)     // Catch:{ Exception -> 0x009d }
            goto L_0x011a
        L_0x009d:
            r0 = move-exception
            r1 = r21
            goto L_0x00f3
        L_0x00a1:
            r0 = move-exception
            r20 = r10
            r22 = r12
            r23 = r13
            r24 = r14
            r1 = r11
            goto L_0x00f3
        L_0x00ac:
            r20 = r10
            r21 = r11
            r22 = r12
            r23 = r13
            r24 = r14
            if (r23 != 0) goto L_0x011a
            if (r6 == 0) goto L_0x011a
            java.lang.StringBuilder r6 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x009d }
            r6.<init>()     // Catch:{ Exception -> 0x009d }
            r14 = r21
            r6.append(r14)     // Catch:{ Exception -> 0x00e6 }
            java.lang.String r7 = ".secure"
            r6.append(r7)     // Catch:{ Exception -> 0x00e6 }
            java.lang.String r6 = r6.toString()     // Catch:{ Exception -> 0x00e6 }
            r19 = 1
            r7 = r15
            r8 = r20
            r10 = r16
            r11 = r17
            r12 = r0
            r13 = r18
            r1 = r14
            r14 = r19
            com.google.android.exoplayer2.mediacodec.MediaCodecInfo r0 = com.google.android.exoplayer2.mediacodec.MediaCodecInfo.newInstance(r6, r7, r8, r9, r10, r11, r12, r13, r14)     // Catch:{ Exception -> 0x00e4 }
            r5.add(r0)     // Catch:{ Exception -> 0x00e4 }
            return r5
        L_0x00e4:
            r0 = move-exception
            goto L_0x00f3
        L_0x00e6:
            r0 = move-exception
            r1 = r14
            goto L_0x00f3
        L_0x00e9:
            r0 = move-exception
            r20 = r10
            r1 = r11
            r22 = r12
            r23 = r13
            r24 = r14
        L_0x00f3:
            int r6 = com.google.android.exoplayer2.util.Util.SDK_INT     // Catch:{ Exception -> 0x0149 }
            r7 = 23
            java.lang.String r8 = "MediaCodecUtil"
            if (r6 > r7) goto L_0x0124
            boolean r6 = r5.isEmpty()     // Catch:{ Exception -> 0x0149 }
            if (r6 != 0) goto L_0x0124
            java.lang.StringBuilder r0 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x0149 }
            r0.<init>()     // Catch:{ Exception -> 0x0149 }
            java.lang.String r6 = "Skipping codec "
            r0.append(r6)     // Catch:{ Exception -> 0x0149 }
            r0.append(r1)     // Catch:{ Exception -> 0x0149 }
            java.lang.String r1 = " (failed to query capabilities)"
            r0.append(r1)     // Catch:{ Exception -> 0x0149 }
            java.lang.String r0 = r0.toString()     // Catch:{ Exception -> 0x0149 }
            com.google.android.exoplayer2.util.Log.m1392e(r8, r0)     // Catch:{ Exception -> 0x0149 }
        L_0x011a:
            int r12 = r22 + 1
            r1 = r25
            r13 = r23
            r14 = r24
            goto L_0x001a
        L_0x0124:
            java.lang.StringBuilder r2 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x0149 }
            r2.<init>()     // Catch:{ Exception -> 0x0149 }
            java.lang.String r3 = "Failed to query codec "
            r2.append(r3)     // Catch:{ Exception -> 0x0149 }
            r2.append(r1)     // Catch:{ Exception -> 0x0149 }
            java.lang.String r1 = " ("
            r2.append(r1)     // Catch:{ Exception -> 0x0149 }
            r1 = r20
            r2.append(r1)     // Catch:{ Exception -> 0x0149 }
            java.lang.String r1 = ")"
            r2.append(r1)     // Catch:{ Exception -> 0x0149 }
            java.lang.String r1 = r2.toString()     // Catch:{ Exception -> 0x0149 }
            com.google.android.exoplayer2.util.Log.m1392e(r8, r1)     // Catch:{ Exception -> 0x0149 }
            throw r0     // Catch:{ Exception -> 0x0149 }
        L_0x0148:
            return r5
        L_0x0149:
            r0 = move-exception
            com.google.android.exoplayer2.mediacodec.MediaCodecUtil$DecoderQueryException r1 = new com.google.android.exoplayer2.mediacodec.MediaCodecUtil$DecoderQueryException
            r2 = 0
            r1.<init>(r0)
            goto L_0x0152
        L_0x0151:
            throw r1
        L_0x0152:
            goto L_0x0151
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.exoplayer2.mediacodec.MediaCodecUtil.getDecoderInfosInternal(com.google.android.exoplayer2.mediacodec.MediaCodecUtil$CodecKey, com.google.android.exoplayer2.mediacodec.MediaCodecUtil$MediaCodecListCompat):java.util.ArrayList");
    }

    private static String getCodecMimeType(MediaCodecInfo mediaCodecInfo, String str, boolean z, String str2) {
        String[] supportedTypes;
        if (!isCodecUsableDecoder(mediaCodecInfo, str, z, str2)) {
            return null;
        }
        for (String str3 : mediaCodecInfo.getSupportedTypes()) {
            if (str3.equalsIgnoreCase(str2)) {
                return str3;
            }
        }
        if (str2.equals(MimeTypes.VIDEO_DOLBY_VISION)) {
            if ("OMX.MS.HEVCDV.Decoder".equals(str)) {
                return "video/hevcdv";
            }
            if ("OMX.RTK.video.decoder".equals(str) || "OMX.realtek.video.decoder.tunneled".equals(str)) {
                return "video/dv_hevc";
            }
        } else if (str2.equals(MimeTypes.AUDIO_ALAC) && "OMX.lge.alac.decoder".equals(str)) {
            return "audio/x-lg-alac";
        } else {
            if (str2.equals(MimeTypes.AUDIO_FLAC) && "OMX.lge.flac.decoder".equals(str)) {
                return "audio/x-lg-flac";
            }
        }
        return null;
    }

    /* JADX WARNING: Code restructure failed: missing block: B:29:0x0074, code lost:
        if (com.google.android.exoplayer2.util.Util.DEVICE.startsWith("HM") != false) goto L_0x0076;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:58:0x00fe, code lost:
        if ("SO-02E".equals(com.google.android.exoplayer2.util.Util.DEVICE) != false) goto L_0x0100;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:71:0x0133, code lost:
        if ("C1605".equals(com.google.android.exoplayer2.util.Util.DEVICE) != false) goto L_0x0135;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:96:0x01a7, code lost:
        if ("SCV31".equals(com.google.android.exoplayer2.util.Util.DEVICE) != false) goto L_0x01a9;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private static boolean isCodecUsableDecoder(android.media.MediaCodecInfo r3, java.lang.String r4, boolean r5, java.lang.String r6) {
        /*
            boolean r3 = r3.isEncoder()
            r0 = 0
            if (r3 != 0) goto L_0x021c
            if (r5 != 0) goto L_0x0013
            java.lang.String r3 = ".secure"
            boolean r3 = r4.endsWith(r3)
            if (r3 == 0) goto L_0x0013
            goto L_0x021c
        L_0x0013:
            int r3 = com.google.android.exoplayer2.util.Util.SDK_INT
            r5 = 21
            if (r3 >= r5) goto L_0x004a
            java.lang.String r3 = "CIPAACDecoder"
            boolean r3 = r3.equals(r4)
            if (r3 != 0) goto L_0x0049
            java.lang.String r3 = "CIPMP3Decoder"
            boolean r3 = r3.equals(r4)
            if (r3 != 0) goto L_0x0049
            java.lang.String r3 = "CIPVorbisDecoder"
            boolean r3 = r3.equals(r4)
            if (r3 != 0) goto L_0x0049
            java.lang.String r3 = "CIPAMRNBDecoder"
            boolean r3 = r3.equals(r4)
            if (r3 != 0) goto L_0x0049
            java.lang.String r3 = "AACDecoder"
            boolean r3 = r3.equals(r4)
            if (r3 != 0) goto L_0x0049
            java.lang.String r3 = "MP3Decoder"
            boolean r3 = r3.equals(r4)
            if (r3 == 0) goto L_0x004a
        L_0x0049:
            return r0
        L_0x004a:
            int r3 = com.google.android.exoplayer2.util.Util.SDK_INT
            r5 = 18
            if (r3 >= r5) goto L_0x0077
            java.lang.String r3 = "OMX.MTK.AUDIO.DECODER.AAC"
            boolean r3 = r3.equals(r4)
            if (r3 == 0) goto L_0x0077
            java.lang.String r3 = com.google.android.exoplayer2.util.Util.DEVICE
            java.lang.String r5 = "a70"
            boolean r3 = r5.equals(r3)
            if (r3 != 0) goto L_0x0076
            java.lang.String r3 = com.google.android.exoplayer2.util.Util.MANUFACTURER
            java.lang.String r5 = "Xiaomi"
            boolean r3 = r5.equals(r3)
            if (r3 == 0) goto L_0x0077
            java.lang.String r3 = com.google.android.exoplayer2.util.Util.DEVICE
            java.lang.String r5 = "HM"
            boolean r3 = r3.startsWith(r5)
            if (r3 == 0) goto L_0x0077
        L_0x0076:
            return r0
        L_0x0077:
            int r3 = com.google.android.exoplayer2.util.Util.SDK_INT
            r5 = 16
            if (r3 != r5) goto L_0x0101
            java.lang.String r3 = "OMX.qcom.audio.decoder.mp3"
            boolean r3 = r3.equals(r4)
            if (r3 == 0) goto L_0x0101
            java.lang.String r3 = com.google.android.exoplayer2.util.Util.DEVICE
            java.lang.String r1 = "dlxu"
            boolean r3 = r1.equals(r3)
            if (r3 != 0) goto L_0x0100
            java.lang.String r3 = com.google.android.exoplayer2.util.Util.DEVICE
            java.lang.String r1 = "protou"
            boolean r3 = r1.equals(r3)
            if (r3 != 0) goto L_0x0100
            java.lang.String r3 = com.google.android.exoplayer2.util.Util.DEVICE
            java.lang.String r1 = "ville"
            boolean r3 = r1.equals(r3)
            if (r3 != 0) goto L_0x0100
            java.lang.String r3 = com.google.android.exoplayer2.util.Util.DEVICE
            java.lang.String r1 = "villeplus"
            boolean r3 = r1.equals(r3)
            if (r3 != 0) goto L_0x0100
            java.lang.String r3 = com.google.android.exoplayer2.util.Util.DEVICE
            java.lang.String r1 = "villec2"
            boolean r3 = r1.equals(r3)
            if (r3 != 0) goto L_0x0100
            java.lang.String r3 = com.google.android.exoplayer2.util.Util.DEVICE
            java.lang.String r1 = "gee"
            boolean r3 = r3.startsWith(r1)
            if (r3 != 0) goto L_0x0100
            java.lang.String r3 = com.google.android.exoplayer2.util.Util.DEVICE
            java.lang.String r1 = "C6602"
            boolean r3 = r1.equals(r3)
            if (r3 != 0) goto L_0x0100
            java.lang.String r3 = com.google.android.exoplayer2.util.Util.DEVICE
            java.lang.String r1 = "C6603"
            boolean r3 = r1.equals(r3)
            if (r3 != 0) goto L_0x0100
            java.lang.String r3 = com.google.android.exoplayer2.util.Util.DEVICE
            java.lang.String r1 = "C6606"
            boolean r3 = r1.equals(r3)
            if (r3 != 0) goto L_0x0100
            java.lang.String r3 = com.google.android.exoplayer2.util.Util.DEVICE
            java.lang.String r1 = "C6616"
            boolean r3 = r1.equals(r3)
            if (r3 != 0) goto L_0x0100
            java.lang.String r3 = com.google.android.exoplayer2.util.Util.DEVICE
            java.lang.String r1 = "L36h"
            boolean r3 = r1.equals(r3)
            if (r3 != 0) goto L_0x0100
            java.lang.String r3 = com.google.android.exoplayer2.util.Util.DEVICE
            java.lang.String r1 = "SO-02E"
            boolean r3 = r1.equals(r3)
            if (r3 == 0) goto L_0x0101
        L_0x0100:
            return r0
        L_0x0101:
            int r3 = com.google.android.exoplayer2.util.Util.SDK_INT
            if (r3 != r5) goto L_0x0136
            java.lang.String r3 = "OMX.qcom.audio.decoder.aac"
            boolean r3 = r3.equals(r4)
            if (r3 == 0) goto L_0x0136
            java.lang.String r3 = com.google.android.exoplayer2.util.Util.DEVICE
            java.lang.String r5 = "C1504"
            boolean r3 = r5.equals(r3)
            if (r3 != 0) goto L_0x0135
            java.lang.String r3 = com.google.android.exoplayer2.util.Util.DEVICE
            java.lang.String r5 = "C1505"
            boolean r3 = r5.equals(r3)
            if (r3 != 0) goto L_0x0135
            java.lang.String r3 = com.google.android.exoplayer2.util.Util.DEVICE
            java.lang.String r5 = "C1604"
            boolean r3 = r5.equals(r3)
            if (r3 != 0) goto L_0x0135
            java.lang.String r3 = com.google.android.exoplayer2.util.Util.DEVICE
            java.lang.String r5 = "C1605"
            boolean r3 = r5.equals(r3)
            if (r3 == 0) goto L_0x0136
        L_0x0135:
            return r0
        L_0x0136:
            int r3 = com.google.android.exoplayer2.util.Util.SDK_INT
            r5 = 24
            java.lang.String r1 = "samsung"
            if (r3 >= r5) goto L_0x01aa
            java.lang.String r3 = "OMX.SEC.aac.dec"
            boolean r3 = r3.equals(r4)
            if (r3 != 0) goto L_0x014e
            java.lang.String r3 = "OMX.Exynos.AAC.Decoder"
            boolean r3 = r3.equals(r4)
            if (r3 == 0) goto L_0x01aa
        L_0x014e:
            java.lang.String r3 = com.google.android.exoplayer2.util.Util.MANUFACTURER
            boolean r3 = r1.equals(r3)
            if (r3 == 0) goto L_0x01aa
            java.lang.String r3 = com.google.android.exoplayer2.util.Util.DEVICE
            java.lang.String r5 = "zeroflte"
            boolean r3 = r3.startsWith(r5)
            if (r3 != 0) goto L_0x01a9
            java.lang.String r3 = com.google.android.exoplayer2.util.Util.DEVICE
            java.lang.String r5 = "zerolte"
            boolean r3 = r3.startsWith(r5)
            if (r3 != 0) goto L_0x01a9
            java.lang.String r3 = com.google.android.exoplayer2.util.Util.DEVICE
            java.lang.String r5 = "zenlte"
            boolean r3 = r3.startsWith(r5)
            if (r3 != 0) goto L_0x01a9
            java.lang.String r3 = com.google.android.exoplayer2.util.Util.DEVICE
            java.lang.String r5 = "SC-05G"
            boolean r3 = r5.equals(r3)
            if (r3 != 0) goto L_0x01a9
            java.lang.String r3 = com.google.android.exoplayer2.util.Util.DEVICE
            java.lang.String r5 = "marinelteatt"
            boolean r3 = r5.equals(r3)
            if (r3 != 0) goto L_0x01a9
            java.lang.String r3 = com.google.android.exoplayer2.util.Util.DEVICE
            java.lang.String r5 = "404SC"
            boolean r3 = r5.equals(r3)
            if (r3 != 0) goto L_0x01a9
            java.lang.String r3 = com.google.android.exoplayer2.util.Util.DEVICE
            java.lang.String r5 = "SC-04G"
            boolean r3 = r5.equals(r3)
            if (r3 != 0) goto L_0x01a9
            java.lang.String r3 = com.google.android.exoplayer2.util.Util.DEVICE
            java.lang.String r5 = "SCV31"
            boolean r3 = r5.equals(r3)
            if (r3 == 0) goto L_0x01aa
        L_0x01a9:
            return r0
        L_0x01aa:
            int r3 = com.google.android.exoplayer2.util.Util.SDK_INT
            java.lang.String r5 = "jflte"
            r2 = 19
            if (r3 > r2) goto L_0x01f4
            java.lang.String r3 = "OMX.SEC.vp8.dec"
            boolean r3 = r3.equals(r4)
            if (r3 == 0) goto L_0x01f4
            java.lang.String r3 = com.google.android.exoplayer2.util.Util.MANUFACTURER
            boolean r3 = r1.equals(r3)
            if (r3 == 0) goto L_0x01f4
            java.lang.String r3 = com.google.android.exoplayer2.util.Util.DEVICE
            java.lang.String r1 = "d2"
            boolean r3 = r3.startsWith(r1)
            if (r3 != 0) goto L_0x01f3
            java.lang.String r3 = com.google.android.exoplayer2.util.Util.DEVICE
            java.lang.String r1 = "serrano"
            boolean r3 = r3.startsWith(r1)
            if (r3 != 0) goto L_0x01f3
            java.lang.String r3 = com.google.android.exoplayer2.util.Util.DEVICE
            boolean r3 = r3.startsWith(r5)
            if (r3 != 0) goto L_0x01f3
            java.lang.String r3 = com.google.android.exoplayer2.util.Util.DEVICE
            java.lang.String r1 = "santos"
            boolean r3 = r3.startsWith(r1)
            if (r3 != 0) goto L_0x01f3
            java.lang.String r3 = com.google.android.exoplayer2.util.Util.DEVICE
            java.lang.String r1 = "t0"
            boolean r3 = r3.startsWith(r1)
            if (r3 == 0) goto L_0x01f4
        L_0x01f3:
            return r0
        L_0x01f4:
            int r3 = com.google.android.exoplayer2.util.Util.SDK_INT
            if (r3 > r2) goto L_0x0209
            java.lang.String r3 = com.google.android.exoplayer2.util.Util.DEVICE
            boolean r3 = r3.startsWith(r5)
            if (r3 == 0) goto L_0x0209
            java.lang.String r3 = "OMX.qcom.video.decoder.vp8"
            boolean r3 = r3.equals(r4)
            if (r3 == 0) goto L_0x0209
            return r0
        L_0x0209:
            java.lang.String r3 = "audio/eac3-joc"
            boolean r3 = r3.equals(r6)
            if (r3 == 0) goto L_0x021a
            java.lang.String r3 = "OMX.MTK.AUDIO.DECODER.DSPAC3"
            boolean r3 = r3.equals(r4)
            if (r3 == 0) goto L_0x021a
            return r0
        L_0x021a:
            r3 = 1
            return r3
        L_0x021c:
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.exoplayer2.mediacodec.MediaCodecUtil.isCodecUsableDecoder(android.media.MediaCodecInfo, java.lang.String, boolean, java.lang.String):boolean");
    }

    private static void applyWorkarounds(String str, List<MediaCodecInfo> list) {
        if (MimeTypes.AUDIO_RAW.equals(str)) {
            if (Util.SDK_INT < 26 && Util.DEVICE.equals("R9") && list.size() == 1 && ((MediaCodecInfo) list.get(0)).name.equals("OMX.MTK.AUDIO.DECODER.RAW")) {
                list.add(MediaCodecInfo.newInstance("OMX.google.raw.decoder", MimeTypes.AUDIO_RAW, MimeTypes.AUDIO_RAW, null, false, true, false, false, false));
            }
            sortByScore(list, $$Lambda$MediaCodecUtil$cCWO3tN34TxRUMGlkaLU13g9pw.INSTANCE);
        } else if (Util.SDK_INT < 21 && list.size() > 1) {
            String str2 = ((MediaCodecInfo) list.get(0)).name;
            if ("OMX.SEC.mp3.dec".equals(str2) || "OMX.SEC.MP3.Decoder".equals(str2) || "OMX.brcm.audio.mp3.decoder".equals(str2)) {
                sortByScore(list, $$Lambda$MediaCodecUtil$5ZWFpP5Ck4Hyp9KyuAYDjY5c2U.INSTANCE);
            }
        }
    }

    static /* synthetic */ int lambda$applyWorkarounds$1(MediaCodecInfo mediaCodecInfo) {
        String str = mediaCodecInfo.name;
        if (str.startsWith("OMX.google") || str.startsWith("c2.android")) {
            return 1;
        }
        return (Util.SDK_INT >= 26 || !str.equals("OMX.MTK.AUDIO.DECODER.RAW")) ? 0 : -1;
    }

    static /* synthetic */ int lambda$applyWorkarounds$2(MediaCodecInfo mediaCodecInfo) {
        return mediaCodecInfo.name.startsWith("OMX.google") ? 1 : 0;
    }

    private static boolean isHardwareAccelerated(MediaCodecInfo mediaCodecInfo) {
        if (Util.SDK_INT >= 29) {
            return isHardwareAcceleratedV29(mediaCodecInfo);
        }
        return !isSoftwareOnly(mediaCodecInfo);
    }

    private static boolean isHardwareAcceleratedV29(MediaCodecInfo mediaCodecInfo) {
        return mediaCodecInfo.isHardwareAccelerated();
    }

    private static boolean isSoftwareOnly(MediaCodecInfo mediaCodecInfo) {
        if (Util.SDK_INT >= 29) {
            return isSoftwareOnlyV29(mediaCodecInfo);
        }
        String lowerInvariant = Util.toLowerInvariant(mediaCodecInfo.getName());
        boolean z = false;
        if (lowerInvariant.startsWith("arc.")) {
            return false;
        }
        if (lowerInvariant.startsWith("omx.google.") || lowerInvariant.startsWith("omx.ffmpeg.") || ((lowerInvariant.startsWith("omx.sec.") && lowerInvariant.contains(".sw.")) || lowerInvariant.equals("omx.qcom.video.decoder.hevcswvdec") || lowerInvariant.startsWith("c2.android.") || lowerInvariant.startsWith("c2.google.") || (!lowerInvariant.startsWith("omx.") && !lowerInvariant.startsWith("c2.")))) {
            z = true;
        }
        return z;
    }

    private static boolean isSoftwareOnlyV29(MediaCodecInfo mediaCodecInfo) {
        return mediaCodecInfo.isSoftwareOnly();
    }

    private static boolean isVendor(MediaCodecInfo mediaCodecInfo) {
        if (Util.SDK_INT >= 29) {
            return isVendorV29(mediaCodecInfo);
        }
        String lowerInvariant = Util.toLowerInvariant(mediaCodecInfo.getName());
        return !lowerInvariant.startsWith("omx.google.") && !lowerInvariant.startsWith("c2.android.") && !lowerInvariant.startsWith("c2.google.");
    }

    private static boolean isVendorV29(MediaCodecInfo mediaCodecInfo) {
        return mediaCodecInfo.isVendor();
    }

    /* JADX WARNING: Code restructure failed: missing block: B:5:0x0018, code lost:
        if ("Nexus 10".equals(com.google.android.exoplayer2.util.Util.MODEL) != false) goto L_0x001a;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private static boolean codecNeedsDisableAdaptationWorkaround(java.lang.String r2) {
        /*
            int r0 = com.google.android.exoplayer2.util.Util.SDK_INT
            r1 = 22
            if (r0 > r1) goto L_0x002c
            java.lang.String r0 = com.google.android.exoplayer2.util.Util.MODEL
            java.lang.String r1 = "ODROID-XU3"
            boolean r0 = r1.equals(r0)
            if (r0 != 0) goto L_0x001a
            java.lang.String r0 = com.google.android.exoplayer2.util.Util.MODEL
            java.lang.String r1 = "Nexus 10"
            boolean r0 = r1.equals(r0)
            if (r0 == 0) goto L_0x002c
        L_0x001a:
            java.lang.String r0 = "OMX.Exynos.AVC.Decoder"
            boolean r0 = r0.equals(r2)
            if (r0 != 0) goto L_0x002a
            java.lang.String r0 = "OMX.Exynos.AVC.Decoder.secure"
            boolean r2 = r0.equals(r2)
            if (r2 == 0) goto L_0x002c
        L_0x002a:
            r2 = 1
            goto L_0x002d
        L_0x002c:
            r2 = 0
        L_0x002d:
            return r2
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.exoplayer2.mediacodec.MediaCodecUtil.codecNeedsDisableAdaptationWorkaround(java.lang.String):boolean");
    }

    private static Pair<Integer, Integer> getDolbyVisionProfileAndLevel(String str, String[] strArr) {
        int length = strArr.length;
        String str2 = "Ignoring malformed Dolby Vision codec string: ";
        String str3 = TAG;
        if (length < 3) {
            StringBuilder sb = new StringBuilder();
            sb.append(str2);
            sb.append(str);
            Log.m1396w(str3, sb.toString());
            return null;
        }
        Matcher matcher = PROFILE_PATTERN.matcher(strArr[1]);
        if (!matcher.matches()) {
            StringBuilder sb2 = new StringBuilder();
            sb2.append(str2);
            sb2.append(str);
            Log.m1396w(str3, sb2.toString());
            return null;
        }
        String group = matcher.group(1);
        Integer num = (Integer) DOLBY_VISION_STRING_TO_PROFILE.get(group);
        if (num == null) {
            StringBuilder sb3 = new StringBuilder();
            sb3.append("Unknown Dolby Vision profile string: ");
            sb3.append(group);
            Log.m1396w(str3, sb3.toString());
            return null;
        }
        String str4 = strArr[2];
        Integer num2 = (Integer) DOLBY_VISION_STRING_TO_LEVEL.get(str4);
        if (num2 != null) {
            return new Pair<>(num, num2);
        }
        StringBuilder sb4 = new StringBuilder();
        sb4.append("Unknown Dolby Vision level string: ");
        sb4.append(str4);
        Log.m1396w(str3, sb4.toString());
        return null;
    }

    private static Pair<Integer, Integer> getHevcProfileAndLevel(String str, String[] strArr) {
        int length = strArr.length;
        String str2 = "Ignoring malformed HEVC codec string: ";
        String str3 = TAG;
        if (length < 4) {
            StringBuilder sb = new StringBuilder();
            sb.append(str2);
            sb.append(str);
            Log.m1396w(str3, sb.toString());
            return null;
        }
        int i = 1;
        Matcher matcher = PROFILE_PATTERN.matcher(strArr[1]);
        if (!matcher.matches()) {
            StringBuilder sb2 = new StringBuilder();
            sb2.append(str2);
            sb2.append(str);
            Log.m1396w(str3, sb2.toString());
            return null;
        }
        String group = matcher.group(1);
        if (!IcyHeaders.REQUEST_HEADER_ENABLE_METADATA_VALUE.equals(group)) {
            if (ExifInterface.GPS_MEASUREMENT_2D.equals(group)) {
                i = 2;
            } else {
                StringBuilder sb3 = new StringBuilder();
                sb3.append("Unknown HEVC profile string: ");
                sb3.append(group);
                Log.m1396w(str3, sb3.toString());
                return null;
            }
        }
        String str4 = strArr[3];
        Integer num = (Integer) HEVC_CODEC_STRING_TO_PROFILE_LEVEL.get(str4);
        if (num != null) {
            return new Pair<>(Integer.valueOf(i), num);
        }
        StringBuilder sb4 = new StringBuilder();
        sb4.append("Unknown HEVC level string: ");
        sb4.append(str4);
        Log.m1396w(str3, sb4.toString());
        return null;
    }

    private static Pair<Integer, Integer> getAvcProfileAndLevel(String str, String[] strArr) {
        int i;
        int i2;
        int length = strArr.length;
        String str2 = "Ignoring malformed AVC codec string: ";
        String str3 = TAG;
        if (length < 2) {
            StringBuilder sb = new StringBuilder();
            sb.append(str2);
            sb.append(str);
            Log.m1396w(str3, sb.toString());
            return null;
        }
        try {
            if (strArr[1].length() == 6) {
                int parseInt = Integer.parseInt(strArr[1].substring(0, 2), 16);
                i = Integer.parseInt(strArr[1].substring(4), 16);
                i2 = parseInt;
            } else if (strArr.length >= 3) {
                i2 = Integer.parseInt(strArr[1]);
                i = Integer.parseInt(strArr[2]);
            } else {
                StringBuilder sb2 = new StringBuilder();
                sb2.append(str2);
                sb2.append(str);
                Log.m1396w(str3, sb2.toString());
                return null;
            }
            int i3 = AVC_PROFILE_NUMBER_TO_CONST.get(i2, -1);
            if (i3 == -1) {
                StringBuilder sb3 = new StringBuilder();
                sb3.append("Unknown AVC profile: ");
                sb3.append(i2);
                Log.m1396w(str3, sb3.toString());
                return null;
            }
            int i4 = AVC_LEVEL_NUMBER_TO_CONST.get(i, -1);
            if (i4 != -1) {
                return new Pair<>(Integer.valueOf(i3), Integer.valueOf(i4));
            }
            StringBuilder sb4 = new StringBuilder();
            sb4.append("Unknown AVC level: ");
            sb4.append(i);
            Log.m1396w(str3, sb4.toString());
            return null;
        } catch (NumberFormatException unused) {
            StringBuilder sb5 = new StringBuilder();
            sb5.append(str2);
            sb5.append(str);
            Log.m1396w(str3, sb5.toString());
            return null;
        }
    }

    private static Pair<Integer, Integer> getVp9ProfileAndLevel(String str, String[] strArr) {
        int length = strArr.length;
        String str2 = "Ignoring malformed VP9 codec string: ";
        String str3 = TAG;
        if (length < 3) {
            StringBuilder sb = new StringBuilder();
            sb.append(str2);
            sb.append(str);
            Log.m1396w(str3, sb.toString());
            return null;
        }
        try {
            int parseInt = Integer.parseInt(strArr[1]);
            int parseInt2 = Integer.parseInt(strArr[2]);
            int i = VP9_PROFILE_NUMBER_TO_CONST.get(parseInt, -1);
            if (i == -1) {
                StringBuilder sb2 = new StringBuilder();
                sb2.append("Unknown VP9 profile: ");
                sb2.append(parseInt);
                Log.m1396w(str3, sb2.toString());
                return null;
            }
            int i2 = VP9_LEVEL_NUMBER_TO_CONST.get(parseInt2, -1);
            if (i2 != -1) {
                return new Pair<>(Integer.valueOf(i), Integer.valueOf(i2));
            }
            StringBuilder sb3 = new StringBuilder();
            sb3.append("Unknown VP9 level: ");
            sb3.append(parseInt2);
            Log.m1396w(str3, sb3.toString());
            return null;
        } catch (NumberFormatException unused) {
            StringBuilder sb4 = new StringBuilder();
            sb4.append(str2);
            sb4.append(str);
            Log.m1396w(str3, sb4.toString());
            return null;
        }
    }

    private static Pair<Integer, Integer> getAv1ProfileAndLevel(String str, String[] strArr, ColorInfo colorInfo) {
        int length = strArr.length;
        String str2 = "Ignoring malformed AV1 codec string: ";
        String str3 = TAG;
        if (length < 4) {
            StringBuilder sb = new StringBuilder();
            sb.append(str2);
            sb.append(str);
            Log.m1396w(str3, sb.toString());
            return null;
        }
        int i = 1;
        try {
            int parseInt = Integer.parseInt(strArr[1]);
            int parseInt2 = Integer.parseInt(strArr[2].substring(0, 2));
            int parseInt3 = Integer.parseInt(strArr[3]);
            if (parseInt != 0) {
                StringBuilder sb2 = new StringBuilder();
                sb2.append("Unknown AV1 profile: ");
                sb2.append(parseInt);
                Log.m1396w(str3, sb2.toString());
                return null;
            } else if (parseInt3 == 8 || parseInt3 == 10) {
                if (parseInt3 != 8) {
                    i = (colorInfo == null || !(colorInfo.hdrStaticInfo != null || colorInfo.colorTransfer == 7 || colorInfo.colorTransfer == 6)) ? 2 : 4096;
                }
                int i2 = AV1_LEVEL_NUMBER_TO_CONST.get(parseInt2, -1);
                if (i2 != -1) {
                    return new Pair<>(Integer.valueOf(i), Integer.valueOf(i2));
                }
                StringBuilder sb3 = new StringBuilder();
                sb3.append("Unknown AV1 level: ");
                sb3.append(parseInt2);
                Log.m1396w(str3, sb3.toString());
                return null;
            } else {
                StringBuilder sb4 = new StringBuilder();
                sb4.append("Unknown AV1 bit depth: ");
                sb4.append(parseInt3);
                Log.m1396w(str3, sb4.toString());
                return null;
            }
        } catch (NumberFormatException unused) {
            StringBuilder sb5 = new StringBuilder();
            sb5.append(str2);
            sb5.append(str);
            Log.m1396w(str3, sb5.toString());
            return null;
        }
    }

    private static Pair<Integer, Integer> getAacCodecProfileAndLevel(String str, String[] strArr) {
        int length = strArr.length;
        String str2 = "Ignoring malformed MP4A codec string: ";
        String str3 = TAG;
        if (length != 3) {
            StringBuilder sb = new StringBuilder();
            sb.append(str2);
            sb.append(str);
            Log.m1396w(str3, sb.toString());
            return null;
        }
        try {
            if (MimeTypes.AUDIO_AAC.equals(MimeTypes.getMimeTypeFromMp4ObjectType(Integer.parseInt(strArr[1], 16)))) {
                int i = MP4A_AUDIO_OBJECT_TYPE_TO_PROFILE.get(Integer.parseInt(strArr[2]), -1);
                if (i != -1) {
                    return new Pair<>(Integer.valueOf(i), Integer.valueOf(0));
                }
            }
        } catch (NumberFormatException unused) {
            StringBuilder sb2 = new StringBuilder();
            sb2.append(str2);
            sb2.append(str);
            Log.m1396w(str3, sb2.toString());
        }
        return null;
    }

    static /* synthetic */ int lambda$sortByScore$3(ScoreProvider scoreProvider, Object obj, Object obj2) {
        return scoreProvider.getScore(obj2) - scoreProvider.getScore(obj);
    }

    private static <T> void sortByScore(List<T> list, ScoreProvider<T> scoreProvider) {
        Collections.sort(list, new Comparator() {
            public final int compare(Object obj, Object obj2) {
                return MediaCodecUtil.lambda$sortByScore$3(ScoreProvider.this, obj, obj2);
            }
        });
    }
}
