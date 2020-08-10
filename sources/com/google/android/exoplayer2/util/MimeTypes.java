package com.google.android.exoplayer2.util;

import android.text.TextUtils;
import java.util.ArrayList;
import org.objectweb.asm.Opcodes;

public final class MimeTypes {
    public static final String APPLICATION_CAMERA_MOTION = "application/x-camera-motion";
    public static final String APPLICATION_CEA608 = "application/cea-608";
    public static final String APPLICATION_CEA708 = "application/cea-708";
    public static final String APPLICATION_DVBSUBS = "application/dvbsubs";
    public static final String APPLICATION_EMSG = "application/x-emsg";
    public static final String APPLICATION_EXIF = "application/x-exif";
    public static final String APPLICATION_ICY = "application/x-icy";
    public static final String APPLICATION_ID3 = "application/id3";
    public static final String APPLICATION_M3U8 = "application/x-mpegURL";
    public static final String APPLICATION_MP4 = "application/mp4";
    public static final String APPLICATION_MP4CEA608 = "application/x-mp4-cea-608";
    public static final String APPLICATION_MP4VTT = "application/x-mp4-vtt";
    public static final String APPLICATION_MPD = "application/dash+xml";
    public static final String APPLICATION_PGS = "application/pgs";
    public static final String APPLICATION_RAWCC = "application/x-rawcc";
    public static final String APPLICATION_SCTE35 = "application/x-scte35";
    public static final String APPLICATION_SS = "application/vnd.ms-sstr+xml";
    public static final String APPLICATION_SUBRIP = "application/x-subrip";
    public static final String APPLICATION_TTML = "application/ttml+xml";
    public static final String APPLICATION_TX3G = "application/x-quicktime-tx3g";
    public static final String APPLICATION_VOBSUB = "application/vobsub";
    public static final String APPLICATION_WEBM = "application/webm";
    public static final String AUDIO_AAC = "audio/mp4a-latm";
    public static final String AUDIO_AC3 = "audio/ac3";
    public static final String AUDIO_AC4 = "audio/ac4";
    public static final String AUDIO_ALAC = "audio/alac";
    public static final String AUDIO_ALAW = "audio/g711-alaw";
    public static final String AUDIO_AMR_NB = "audio/3gpp";
    public static final String AUDIO_AMR_WB = "audio/amr-wb";
    public static final String AUDIO_DTS = "audio/vnd.dts";
    public static final String AUDIO_DTS_EXPRESS = "audio/vnd.dts.hd;profile=lbr";
    public static final String AUDIO_DTS_HD = "audio/vnd.dts.hd";
    public static final String AUDIO_E_AC3 = "audio/eac3";
    public static final String AUDIO_E_AC3_JOC = "audio/eac3-joc";
    public static final String AUDIO_FLAC = "audio/flac";
    public static final String AUDIO_MLAW = "audio/g711-mlaw";
    public static final String AUDIO_MP4 = "audio/mp4";
    public static final String AUDIO_MPEG = "audio/mpeg";
    public static final String AUDIO_MPEG_L1 = "audio/mpeg-L1";
    public static final String AUDIO_MPEG_L2 = "audio/mpeg-L2";
    public static final String AUDIO_MSGSM = "audio/gsm";
    public static final String AUDIO_OPUS = "audio/opus";
    public static final String AUDIO_RAW = "audio/raw";
    public static final String AUDIO_TRUEHD = "audio/true-hd";
    public static final String AUDIO_UNKNOWN = "audio/x-unknown";
    public static final String AUDIO_VORBIS = "audio/vorbis";
    public static final String AUDIO_WEBM = "audio/webm";
    public static final String BASE_TYPE_APPLICATION = "application";
    public static final String BASE_TYPE_AUDIO = "audio";
    public static final String BASE_TYPE_TEXT = "text";
    public static final String BASE_TYPE_VIDEO = "video";
    public static final String TEXT_SSA = "text/x-ssa";
    public static final String TEXT_VTT = "text/vtt";
    public static final String VIDEO_AV1 = "video/av01";
    public static final String VIDEO_DIVX = "video/divx";
    public static final String VIDEO_DOLBY_VISION = "video/dolby-vision";
    public static final String VIDEO_H263 = "video/3gpp";
    public static final String VIDEO_H264 = "video/avc";
    public static final String VIDEO_H265 = "video/hevc";
    public static final String VIDEO_MP4 = "video/mp4";
    public static final String VIDEO_MP4V = "video/mp4v-es";
    public static final String VIDEO_MPEG = "video/mpeg";
    public static final String VIDEO_MPEG2 = "video/mpeg2";
    public static final String VIDEO_UNKNOWN = "video/x-unknown";
    public static final String VIDEO_VC1 = "video/wvc1";
    public static final String VIDEO_VP8 = "video/x-vnd.on2.vp8";
    public static final String VIDEO_VP9 = "video/x-vnd.on2.vp9";
    public static final String VIDEO_WEBM = "video/webm";
    private static final ArrayList<CustomMimeType> customMimeTypes = new ArrayList<>();

    private static final class CustomMimeType {
        public final String codecPrefix;
        public final String mimeType;
        public final int trackType;

        public CustomMimeType(String str, String str2, int i) {
            this.mimeType = str;
            this.codecPrefix = str2;
            this.trackType = i;
        }
    }

    public static String getMimeTypeFromMp4ObjectType(int i) {
        if (i == 32) {
            return VIDEO_MP4V;
        }
        if (i == 33) {
            return VIDEO_H264;
        }
        if (i == 35) {
            return VIDEO_H265;
        }
        if (i != 64) {
            if (i == 163) {
                return VIDEO_VC1;
            }
            if (i == 177) {
                return VIDEO_VP9;
            }
            if (i == 165) {
                return AUDIO_AC3;
            }
            if (i == 166) {
                return AUDIO_E_AC3;
            }
            switch (i) {
                case 96:
                case 97:
                case 98:
                case 99:
                case 100:
                case 101:
                    return VIDEO_MPEG2;
                case 102:
                case 103:
                case 104:
                    break;
                case 105:
                case 107:
                    return AUDIO_MPEG;
                case 106:
                    return VIDEO_MPEG;
                default:
                    switch (i) {
                        case Opcodes.RET /*169*/:
                        case 172:
                            return AUDIO_DTS;
                        case Opcodes.TABLESWITCH /*170*/:
                        case Opcodes.LOOKUPSWITCH /*171*/:
                            return AUDIO_DTS_HD;
                        case 173:
                            return AUDIO_OPUS;
                        case Opcodes.FRETURN /*174*/:
                            return AUDIO_AC4;
                        default:
                            return null;
                    }
            }
        }
        return AUDIO_AAC;
    }

    public static void registerCustomMimeType(String str, String str2, int i) {
        CustomMimeType customMimeType = new CustomMimeType(str, str2, i);
        int size = customMimeTypes.size();
        int i2 = 0;
        while (true) {
            if (i2 >= size) {
                break;
            } else if (str.equals(((CustomMimeType) customMimeTypes.get(i2)).mimeType)) {
                customMimeTypes.remove(i2);
                break;
            } else {
                i2++;
            }
        }
        customMimeTypes.add(customMimeType);
    }

    public static boolean isAudio(String str) {
        return BASE_TYPE_AUDIO.equals(getTopLevelType(str));
    }

    public static boolean isVideo(String str) {
        return "video".equals(getTopLevelType(str));
    }

    public static boolean isText(String str) {
        return "text".equals(getTopLevelType(str));
    }

    public static boolean isApplication(String str) {
        return BASE_TYPE_APPLICATION.equals(getTopLevelType(str));
    }

    public static String getVideoMediaMimeType(String str) {
        if (str == null) {
            return null;
        }
        for (String mediaMimeType : Util.splitCodecs(str)) {
            String mediaMimeType2 = getMediaMimeType(mediaMimeType);
            if (mediaMimeType2 != null && isVideo(mediaMimeType2)) {
                return mediaMimeType2;
            }
        }
        return null;
    }

    public static String getAudioMediaMimeType(String str) {
        if (str == null) {
            return null;
        }
        for (String mediaMimeType : Util.splitCodecs(str)) {
            String mediaMimeType2 = getMediaMimeType(mediaMimeType);
            if (mediaMimeType2 != null && isAudio(mediaMimeType2)) {
                return mediaMimeType2;
            }
        }
        return null;
    }

    public static String getMediaMimeType(String str) {
        String str2 = null;
        if (str == null) {
            return null;
        }
        String lowerInvariant = Util.toLowerInvariant(str.trim());
        if (lowerInvariant.startsWith("avc1") || lowerInvariant.startsWith("avc3")) {
            return VIDEO_H264;
        }
        if (lowerInvariant.startsWith("hev1") || lowerInvariant.startsWith("hvc1")) {
            return VIDEO_H265;
        }
        if (lowerInvariant.startsWith("dvav") || lowerInvariant.startsWith("dva1") || lowerInvariant.startsWith("dvhe") || lowerInvariant.startsWith("dvh1")) {
            return VIDEO_DOLBY_VISION;
        }
        if (lowerInvariant.startsWith("av01")) {
            return VIDEO_AV1;
        }
        if (lowerInvariant.startsWith("vp9") || lowerInvariant.startsWith("vp09")) {
            return VIDEO_VP9;
        }
        if (lowerInvariant.startsWith("vp8") || lowerInvariant.startsWith("vp08")) {
            return VIDEO_VP8;
        }
        if (lowerInvariant.startsWith("mp4a")) {
            if (lowerInvariant.startsWith("mp4a.")) {
                String substring = lowerInvariant.substring(5);
                if (substring.length() >= 2) {
                    try {
                        str2 = getMimeTypeFromMp4ObjectType(Integer.parseInt(Util.toUpperInvariant(substring.substring(0, 2)), 16));
                    } catch (NumberFormatException unused) {
                    }
                }
            }
            if (str2 == null) {
                str2 = AUDIO_AAC;
            }
            return str2;
        } else if (lowerInvariant.startsWith("ac-3") || lowerInvariant.startsWith("dac3")) {
            return AUDIO_AC3;
        } else {
            if (lowerInvariant.startsWith("ec-3") || lowerInvariant.startsWith("dec3")) {
                return AUDIO_E_AC3;
            }
            if (lowerInvariant.startsWith("ec+3")) {
                return AUDIO_E_AC3_JOC;
            }
            if (lowerInvariant.startsWith("ac-4") || lowerInvariant.startsWith("dac4")) {
                return AUDIO_AC4;
            }
            if (lowerInvariant.startsWith("dtsc") || lowerInvariant.startsWith("dtse")) {
                return AUDIO_DTS;
            }
            if (lowerInvariant.startsWith("dtsh") || lowerInvariant.startsWith("dtsl")) {
                return AUDIO_DTS_HD;
            }
            if (lowerInvariant.startsWith("opus")) {
                return AUDIO_OPUS;
            }
            if (lowerInvariant.startsWith("vorbis")) {
                return AUDIO_VORBIS;
            }
            if (lowerInvariant.startsWith("flac")) {
                return AUDIO_FLAC;
            }
            return getCustomMimeTypeForCodec(lowerInvariant);
        }
    }

    public static int getTrackType(String str) {
        if (TextUtils.isEmpty(str)) {
            return -1;
        }
        if (isAudio(str)) {
            return 1;
        }
        if (isVideo(str)) {
            return 2;
        }
        if (isText(str) || APPLICATION_CEA608.equals(str) || APPLICATION_CEA708.equals(str) || APPLICATION_MP4CEA608.equals(str) || APPLICATION_SUBRIP.equals(str) || APPLICATION_TTML.equals(str) || APPLICATION_TX3G.equals(str) || APPLICATION_MP4VTT.equals(str) || APPLICATION_RAWCC.equals(str) || APPLICATION_VOBSUB.equals(str) || APPLICATION_PGS.equals(str) || APPLICATION_DVBSUBS.equals(str)) {
            return 3;
        }
        if (APPLICATION_ID3.equals(str) || APPLICATION_EMSG.equals(str) || APPLICATION_SCTE35.equals(str)) {
            return 4;
        }
        if (APPLICATION_CAMERA_MOTION.equals(str)) {
            return 5;
        }
        return getTrackTypeForCustomMimeType(str);
    }

    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static int getEncoding(java.lang.String r4) {
        /*
            int r0 = r4.hashCode()
            r1 = 6
            r2 = 5
            r3 = 0
            switch(r0) {
                case -2123537834: goto L_0x0047;
                case -1095064472: goto L_0x003d;
                case 187078296: goto L_0x0033;
                case 187078297: goto L_0x0029;
                case 1504578661: goto L_0x001f;
                case 1505942594: goto L_0x0015;
                case 1556697186: goto L_0x000b;
                default: goto L_0x000a;
            }
        L_0x000a:
            goto L_0x0051
        L_0x000b:
            java.lang.String r0 = "audio/true-hd"
            boolean r4 = r4.equals(r0)
            if (r4 == 0) goto L_0x0051
            r4 = 6
            goto L_0x0052
        L_0x0015:
            java.lang.String r0 = "audio/vnd.dts.hd"
            boolean r4 = r4.equals(r0)
            if (r4 == 0) goto L_0x0051
            r4 = 5
            goto L_0x0052
        L_0x001f:
            java.lang.String r0 = "audio/eac3"
            boolean r4 = r4.equals(r0)
            if (r4 == 0) goto L_0x0051
            r4 = 1
            goto L_0x0052
        L_0x0029:
            java.lang.String r0 = "audio/ac4"
            boolean r4 = r4.equals(r0)
            if (r4 == 0) goto L_0x0051
            r4 = 3
            goto L_0x0052
        L_0x0033:
            java.lang.String r0 = "audio/ac3"
            boolean r4 = r4.equals(r0)
            if (r4 == 0) goto L_0x0051
            r4 = 0
            goto L_0x0052
        L_0x003d:
            java.lang.String r0 = "audio/vnd.dts"
            boolean r4 = r4.equals(r0)
            if (r4 == 0) goto L_0x0051
            r4 = 4
            goto L_0x0052
        L_0x0047:
            java.lang.String r0 = "audio/eac3-joc"
            boolean r4 = r4.equals(r0)
            if (r4 == 0) goto L_0x0051
            r4 = 2
            goto L_0x0052
        L_0x0051:
            r4 = -1
        L_0x0052:
            switch(r4) {
                case 0: goto L_0x0065;
                case 1: goto L_0x0064;
                case 2: goto L_0x0061;
                case 3: goto L_0x005e;
                case 4: goto L_0x005c;
                case 5: goto L_0x0059;
                case 6: goto L_0x0056;
                default: goto L_0x0055;
            }
        L_0x0055:
            return r3
        L_0x0056:
            r4 = 14
            return r4
        L_0x0059:
            r4 = 8
            return r4
        L_0x005c:
            r4 = 7
            return r4
        L_0x005e:
            r4 = 17
            return r4
        L_0x0061:
            r4 = 18
            return r4
        L_0x0064:
            return r1
        L_0x0065:
            return r2
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.exoplayer2.util.MimeTypes.getEncoding(java.lang.String):int");
    }

    public static int getTrackTypeOfCodec(String str) {
        return getTrackType(getMediaMimeType(str));
    }

    private static String getTopLevelType(String str) {
        if (str == null) {
            return null;
        }
        int indexOf = str.indexOf(47);
        if (indexOf == -1) {
            return null;
        }
        return str.substring(0, indexOf);
    }

    private static String getCustomMimeTypeForCodec(String str) {
        int size = customMimeTypes.size();
        for (int i = 0; i < size; i++) {
            CustomMimeType customMimeType = (CustomMimeType) customMimeTypes.get(i);
            if (str.startsWith(customMimeType.codecPrefix)) {
                return customMimeType.mimeType;
            }
        }
        return null;
    }

    private static int getTrackTypeForCustomMimeType(String str) {
        int size = customMimeTypes.size();
        for (int i = 0; i < size; i++) {
            CustomMimeType customMimeType = (CustomMimeType) customMimeTypes.get(i);
            if (str.equals(customMimeType.mimeType)) {
                return customMimeType.trackType;
            }
        }
        return -1;
    }

    private MimeTypes() {
    }
}
