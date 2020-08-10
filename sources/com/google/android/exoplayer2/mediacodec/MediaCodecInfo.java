package com.google.android.exoplayer2.mediacodec;

import android.graphics.Point;
import android.media.MediaCodecInfo.AudioCapabilities;
import android.media.MediaCodecInfo.CodecCapabilities;
import android.media.MediaCodecInfo.CodecProfileLevel;
import android.media.MediaCodecInfo.VideoCapabilities;
import android.util.Pair;
import com.google.android.exoplayer2.Format;
import com.google.android.exoplayer2.mediacodec.MediaCodecUtil.DecoderQueryException;
import com.google.android.exoplayer2.util.Assertions;
import com.google.android.exoplayer2.util.Log;
import com.google.android.exoplayer2.util.MimeTypes;
import com.google.android.exoplayer2.util.Util;
import com.google.firebase.remoteconfig.FirebaseRemoteConfig;

public final class MediaCodecInfo {
    public static final int MAX_SUPPORTED_INSTANCES_UNKNOWN = -1;
    public static final String TAG = "MediaCodecInfo";
    public final boolean adaptive;
    public final CodecCapabilities capabilities;
    public final String codecMimeType;
    public final boolean hardwareAccelerated;
    private final boolean isVideo;
    public final String mimeType;
    public final String name;
    public final boolean passthrough;
    public final boolean secure;
    public final boolean softwareOnly;
    public final boolean tunneling;
    public final boolean vendor;

    public static MediaCodecInfo newPassthroughInstance(String str) {
        MediaCodecInfo mediaCodecInfo = new MediaCodecInfo(str, null, null, null, true, false, true, false, false, false);
        return mediaCodecInfo;
    }

    public static MediaCodecInfo newInstance(String str, String str2, String str3, CodecCapabilities codecCapabilities, boolean z, boolean z2, boolean z3, boolean z4, boolean z5) {
        MediaCodecInfo mediaCodecInfo = new MediaCodecInfo(str, str2, str3, codecCapabilities, false, z, z2, z3, z4, z5);
        return mediaCodecInfo;
    }

    private MediaCodecInfo(String str, String str2, String str3, CodecCapabilities codecCapabilities, boolean z, boolean z2, boolean z3, boolean z4, boolean z5, boolean z6) {
        this.name = (String) Assertions.checkNotNull(str);
        this.mimeType = str2;
        this.codecMimeType = str3;
        this.capabilities = codecCapabilities;
        this.passthrough = z;
        this.hardwareAccelerated = z2;
        this.softwareOnly = z3;
        this.vendor = z4;
        boolean z7 = true;
        this.adaptive = !z5 && codecCapabilities != null && isAdaptive(codecCapabilities);
        this.tunneling = codecCapabilities != null && isTunneling(codecCapabilities);
        if (!z6 && (codecCapabilities == null || !isSecure(codecCapabilities))) {
            z7 = false;
        }
        this.secure = z7;
        this.isVideo = MimeTypes.isVideo(str2);
    }

    public String toString() {
        return this.name;
    }

    public CodecProfileLevel[] getProfileLevels() {
        CodecCapabilities codecCapabilities = this.capabilities;
        return (codecCapabilities == null || codecCapabilities.profileLevels == null) ? new CodecProfileLevel[0] : this.capabilities.profileLevels;
    }

    public int getMaxSupportedInstances() {
        if (Util.SDK_INT >= 23) {
            CodecCapabilities codecCapabilities = this.capabilities;
            if (codecCapabilities != null) {
                return getMaxSupportedInstancesV23(codecCapabilities);
            }
        }
        return -1;
    }

    public boolean isFormatSupported(Format format) throws DecoderQueryException {
        boolean z = false;
        if (!isCodecSupported(format)) {
            return false;
        }
        if (!this.isVideo) {
            if (Util.SDK_INT < 21 || ((format.sampleRate == -1 || isAudioSampleRateSupportedV21(format.sampleRate)) && (format.channelCount == -1 || isAudioChannelCountSupportedV21(format.channelCount)))) {
                z = true;
            }
            return z;
        } else if (format.width <= 0 || format.height <= 0) {
            return true;
        } else {
            if (Util.SDK_INT >= 21) {
                return isVideoSizeAndRateSupportedV21(format.width, format.height, (double) format.frameRate);
            }
            if (format.width * format.height <= MediaCodecUtil.maxH264DecodableFrameSize()) {
                z = true;
            }
            if (!z) {
                StringBuilder sb = new StringBuilder();
                sb.append("legacyFrameSize, ");
                sb.append(format.width);
                sb.append("x");
                sb.append(format.height);
                logNoSupport(sb.toString());
            }
            return z;
        }
    }

    public boolean isCodecSupported(Format format) {
        CodecProfileLevel[] profileLevels;
        if (format.codecs == null || this.mimeType == null) {
            return true;
        }
        String mediaMimeType = MimeTypes.getMediaMimeType(format.codecs);
        if (mediaMimeType == null) {
            return true;
        }
        String str = ", ";
        if (!this.mimeType.equals(mediaMimeType)) {
            StringBuilder sb = new StringBuilder();
            sb.append("codec.mime ");
            sb.append(format.codecs);
            sb.append(str);
            sb.append(mediaMimeType);
            logNoSupport(sb.toString());
            return false;
        }
        Pair codecProfileAndLevel = MediaCodecUtil.getCodecProfileAndLevel(format);
        if (codecProfileAndLevel == null) {
            return true;
        }
        int intValue = ((Integer) codecProfileAndLevel.first).intValue();
        int intValue2 = ((Integer) codecProfileAndLevel.second).intValue();
        if (!this.isVideo && intValue != 42) {
            return true;
        }
        for (CodecProfileLevel codecProfileLevel : getProfileLevels()) {
            if (codecProfileLevel.profile == intValue && codecProfileLevel.level >= intValue2) {
                return true;
            }
        }
        StringBuilder sb2 = new StringBuilder();
        sb2.append("codec.profileLevel, ");
        sb2.append(format.codecs);
        sb2.append(str);
        sb2.append(mediaMimeType);
        logNoSupport(sb2.toString());
        return false;
    }

    public boolean isHdr10PlusOutOfBandMetadataSupported() {
        if (Util.SDK_INT >= 29) {
            if (MimeTypes.VIDEO_VP9.equals(this.mimeType)) {
                for (CodecProfileLevel codecProfileLevel : getProfileLevels()) {
                    if (codecProfileLevel.profile == 16384) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public boolean isSeamlessAdaptationSupported(Format format) {
        if (this.isVideo) {
            return this.adaptive;
        }
        Pair codecProfileAndLevel = MediaCodecUtil.getCodecProfileAndLevel(format);
        return codecProfileAndLevel != null && ((Integer) codecProfileAndLevel.first).intValue() == 42;
    }

    public boolean isSeamlessAdaptationSupported(Format format, Format format2, boolean z) {
        boolean z2 = true;
        if (this.isVideo) {
            if (!format.sampleMimeType.equals(format2.sampleMimeType) || format.rotationDegrees != format2.rotationDegrees || ((!this.adaptive && !(format.width == format2.width && format.height == format2.height)) || ((z || format2.colorInfo != null) && !Util.areEqual(format.colorInfo, format2.colorInfo)))) {
                z2 = false;
            }
            return z2;
        }
        if (MimeTypes.AUDIO_AAC.equals(this.mimeType) && format.sampleMimeType.equals(format2.sampleMimeType) && format.channelCount == format2.channelCount && format.sampleRate == format2.sampleRate) {
            Pair codecProfileAndLevel = MediaCodecUtil.getCodecProfileAndLevel(format);
            Pair codecProfileAndLevel2 = MediaCodecUtil.getCodecProfileAndLevel(format2);
            if (!(codecProfileAndLevel == null || codecProfileAndLevel2 == null)) {
                int intValue = ((Integer) codecProfileAndLevel.first).intValue();
                int intValue2 = ((Integer) codecProfileAndLevel2.first).intValue();
                if (!(intValue == 42 && intValue2 == 42)) {
                    z2 = false;
                }
                return z2;
            }
        }
        return false;
    }

    public boolean isVideoSizeAndRateSupportedV21(int i, int i2, double d) {
        CodecCapabilities codecCapabilities = this.capabilities;
        if (codecCapabilities == null) {
            logNoSupport("sizeAndRate.caps");
            return false;
        }
        VideoCapabilities videoCapabilities = codecCapabilities.getVideoCapabilities();
        if (videoCapabilities == null) {
            logNoSupport("sizeAndRate.vCaps");
            return false;
        }
        if (!areSizeAndRateSupportedV21(videoCapabilities, i, i2, d)) {
            String str = "x";
            if (i >= i2 || !enableRotatedVerticalResolutionWorkaround(this.name) || !areSizeAndRateSupportedV21(videoCapabilities, i2, i, d)) {
                StringBuilder sb = new StringBuilder();
                sb.append("sizeAndRate.support, ");
                sb.append(i);
                sb.append(str);
                sb.append(i2);
                sb.append(str);
                sb.append(d);
                logNoSupport(sb.toString());
                return false;
            }
            StringBuilder sb2 = new StringBuilder();
            sb2.append("sizeAndRate.rotated, ");
            sb2.append(i);
            sb2.append(str);
            sb2.append(i2);
            sb2.append(str);
            sb2.append(d);
            logAssumedSupport(sb2.toString());
        }
        return true;
    }

    public Point alignVideoSizeV21(int i, int i2) {
        CodecCapabilities codecCapabilities = this.capabilities;
        if (codecCapabilities == null) {
            return null;
        }
        VideoCapabilities videoCapabilities = codecCapabilities.getVideoCapabilities();
        if (videoCapabilities == null) {
            return null;
        }
        return alignVideoSizeV21(videoCapabilities, i, i2);
    }

    public boolean isAudioSampleRateSupportedV21(int i) {
        CodecCapabilities codecCapabilities = this.capabilities;
        if (codecCapabilities == null) {
            logNoSupport("sampleRate.caps");
            return false;
        }
        AudioCapabilities audioCapabilities = codecCapabilities.getAudioCapabilities();
        if (audioCapabilities == null) {
            logNoSupport("sampleRate.aCaps");
            return false;
        } else if (audioCapabilities.isSampleRateSupported(i)) {
            return true;
        } else {
            StringBuilder sb = new StringBuilder();
            sb.append("sampleRate.support, ");
            sb.append(i);
            logNoSupport(sb.toString());
            return false;
        }
    }

    public boolean isAudioChannelCountSupportedV21(int i) {
        CodecCapabilities codecCapabilities = this.capabilities;
        if (codecCapabilities == null) {
            logNoSupport("channelCount.caps");
            return false;
        }
        AudioCapabilities audioCapabilities = codecCapabilities.getAudioCapabilities();
        if (audioCapabilities == null) {
            logNoSupport("channelCount.aCaps");
            return false;
        } else if (adjustMaxInputChannelCount(this.name, this.mimeType, audioCapabilities.getMaxInputChannelCount()) >= i) {
            return true;
        } else {
            StringBuilder sb = new StringBuilder();
            sb.append("channelCount.support, ");
            sb.append(i);
            logNoSupport(sb.toString());
            return false;
        }
    }

    private void logNoSupport(String str) {
        StringBuilder sb = new StringBuilder();
        sb.append("NoSupport [");
        sb.append(str);
        String str2 = "] [";
        sb.append(str2);
        sb.append(this.name);
        sb.append(", ");
        sb.append(this.mimeType);
        sb.append(str2);
        sb.append(Util.DEVICE_DEBUG_INFO);
        sb.append("]");
        Log.m1390d(TAG, sb.toString());
    }

    private void logAssumedSupport(String str) {
        StringBuilder sb = new StringBuilder();
        sb.append("AssumedSupport [");
        sb.append(str);
        String str2 = "] [";
        sb.append(str2);
        sb.append(this.name);
        sb.append(", ");
        sb.append(this.mimeType);
        sb.append(str2);
        sb.append(Util.DEVICE_DEBUG_INFO);
        sb.append("]");
        Log.m1390d(TAG, sb.toString());
    }

    private static int adjustMaxInputChannelCount(String str, String str2, int i) {
        if (i > 1 || ((Util.SDK_INT >= 26 && i > 0) || MimeTypes.AUDIO_MPEG.equals(str2) || MimeTypes.AUDIO_AMR_NB.equals(str2) || MimeTypes.AUDIO_AMR_WB.equals(str2) || MimeTypes.AUDIO_AAC.equals(str2) || MimeTypes.AUDIO_VORBIS.equals(str2) || MimeTypes.AUDIO_OPUS.equals(str2) || MimeTypes.AUDIO_RAW.equals(str2) || MimeTypes.AUDIO_FLAC.equals(str2) || MimeTypes.AUDIO_ALAW.equals(str2) || MimeTypes.AUDIO_MLAW.equals(str2) || MimeTypes.AUDIO_MSGSM.equals(str2))) {
            return i;
        }
        int i2 = MimeTypes.AUDIO_AC3.equals(str2) ? 6 : MimeTypes.AUDIO_E_AC3.equals(str2) ? 16 : 30;
        StringBuilder sb = new StringBuilder();
        sb.append("AssumedMaxChannelAdjustment: ");
        sb.append(str);
        sb.append(", [");
        sb.append(i);
        sb.append(" to ");
        sb.append(i2);
        sb.append("]");
        Log.m1396w(TAG, sb.toString());
        return i2;
    }

    private static boolean isAdaptive(CodecCapabilities codecCapabilities) {
        return Util.SDK_INT >= 19 && isAdaptiveV19(codecCapabilities);
    }

    private static boolean isAdaptiveV19(CodecCapabilities codecCapabilities) {
        return codecCapabilities.isFeatureSupported("adaptive-playback");
    }

    private static boolean isTunneling(CodecCapabilities codecCapabilities) {
        return Util.SDK_INT >= 21 && isTunnelingV21(codecCapabilities);
    }

    private static boolean isTunnelingV21(CodecCapabilities codecCapabilities) {
        return codecCapabilities.isFeatureSupported("tunneled-playback");
    }

    private static boolean isSecure(CodecCapabilities codecCapabilities) {
        return Util.SDK_INT >= 21 && isSecureV21(codecCapabilities);
    }

    private static boolean isSecureV21(CodecCapabilities codecCapabilities) {
        return codecCapabilities.isFeatureSupported("secure-playback");
    }

    private static boolean areSizeAndRateSupportedV21(VideoCapabilities videoCapabilities, int i, int i2, double d) {
        Point alignVideoSizeV21 = alignVideoSizeV21(videoCapabilities, i, i2);
        int i3 = alignVideoSizeV21.x;
        int i4 = alignVideoSizeV21.y;
        if (d == -1.0d || d <= FirebaseRemoteConfig.DEFAULT_VALUE_FOR_DOUBLE) {
            return videoCapabilities.isSizeSupported(i3, i4);
        }
        return videoCapabilities.areSizeAndRateSupported(i3, i4, Math.floor(d));
    }

    private static Point alignVideoSizeV21(VideoCapabilities videoCapabilities, int i, int i2) {
        int widthAlignment = videoCapabilities.getWidthAlignment();
        int heightAlignment = videoCapabilities.getHeightAlignment();
        return new Point(Util.ceilDivide(i, widthAlignment) * widthAlignment, Util.ceilDivide(i2, heightAlignment) * heightAlignment);
    }

    private static int getMaxSupportedInstancesV23(CodecCapabilities codecCapabilities) {
        return codecCapabilities.getMaxSupportedInstances();
    }

    private static final boolean enableRotatedVerticalResolutionWorkaround(String str) {
        if ("OMX.MTK.VIDEO.DECODER.HEVC".equals(str)) {
            if ("mcv5a".equals(Util.DEVICE)) {
                return false;
            }
        }
        return true;
    }
}
