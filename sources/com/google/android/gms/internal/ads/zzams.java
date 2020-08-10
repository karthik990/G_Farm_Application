package com.google.android.gms.internal.ads;

import android.media.MediaCodecInfo;
import android.media.MediaCodecInfo.CodecCapabilities;
import android.media.MediaCodecInfo.CodecProfileLevel;
import android.media.MediaCodecInfo.VideoCapabilities;
import android.media.MediaCodecList;
import android.os.Build.VERSION;
import android.util.Range;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@zzadh
public final class zzams {
    private static Map<String, List<Map<String, Object>>> zzcug = new HashMap();
    private static List<MediaCodecInfo> zzcuh;
    private static final Object zzcui = new Object();

    private static Integer[] zza(Range<Integer> range) {
        return new Integer[]{(Integer) range.getLower(), (Integer) range.getUpper()};
    }

    public static List<Map<String, Object>> zzdd(String str) {
        List<MediaCodecInfo> emptyList;
        CodecProfileLevel[] codecProfileLevelArr;
        synchronized (zzcui) {
            if (zzcug.containsKey(str)) {
                List<Map<String, Object>> list = (List) zzcug.get(str);
                return list;
            }
            try {
                synchronized (zzcui) {
                    if (zzcuh == null) {
                        if (VERSION.SDK_INT >= 21) {
                            emptyList = Arrays.asList(new MediaCodecList(0).getCodecInfos());
                        } else if (VERSION.SDK_INT >= 16) {
                            int codecCount = MediaCodecList.getCodecCount();
                            zzcuh = new ArrayList(codecCount);
                            for (int i = 0; i < codecCount; i++) {
                                zzcuh.add(MediaCodecList.getCodecInfoAt(i));
                            }
                        } else {
                            emptyList = Collections.emptyList();
                        }
                        zzcuh = emptyList;
                    }
                }
                ArrayList arrayList = new ArrayList();
                for (MediaCodecInfo mediaCodecInfo : zzcuh) {
                    if (!mediaCodecInfo.isEncoder() && Arrays.asList(mediaCodecInfo.getSupportedTypes()).contains(str)) {
                        HashMap hashMap = new HashMap();
                        hashMap.put("codecName", mediaCodecInfo.getName());
                        CodecCapabilities capabilitiesForType = mediaCodecInfo.getCapabilitiesForType(str);
                        ArrayList arrayList2 = new ArrayList();
                        for (CodecProfileLevel codecProfileLevel : capabilitiesForType.profileLevels) {
                            arrayList2.add(new Integer[]{Integer.valueOf(codecProfileLevel.profile), Integer.valueOf(codecProfileLevel.level)});
                        }
                        hashMap.put("profileLevels", arrayList2);
                        if (VERSION.SDK_INT >= 21) {
                            VideoCapabilities videoCapabilities = capabilitiesForType.getVideoCapabilities();
                            hashMap.put("bitRatesBps", zza(videoCapabilities.getBitrateRange()));
                            hashMap.put("widthAlignment", Integer.valueOf(videoCapabilities.getWidthAlignment()));
                            hashMap.put("heightAlignment", Integer.valueOf(videoCapabilities.getHeightAlignment()));
                            hashMap.put("frameRates", zza(videoCapabilities.getSupportedFrameRates()));
                            hashMap.put("widths", zza(videoCapabilities.getSupportedWidths()));
                            hashMap.put("heights", zza(videoCapabilities.getSupportedHeights()));
                        }
                        if (VERSION.SDK_INT >= 23) {
                            hashMap.put("instancesLimit", Integer.valueOf(capabilitiesForType.getMaxSupportedInstances()));
                        }
                        arrayList.add(hashMap);
                    }
                }
                zzcug.put(str, arrayList);
                return arrayList;
            } catch (LinkageError | RuntimeException e) {
                HashMap hashMap2 = new HashMap();
                hashMap2.put("error", e.getClass().getSimpleName());
                ArrayList arrayList3 = new ArrayList();
                arrayList3.add(hashMap2);
                zzcug.put(str, arrayList3);
                return arrayList3;
            }
        }
    }
}
