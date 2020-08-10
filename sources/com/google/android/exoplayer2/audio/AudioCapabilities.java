package com.google.android.exoplayer2.audio;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.provider.Settings.Global;
import com.google.android.exoplayer2.util.Util;
import java.util.Arrays;

public final class AudioCapabilities {
    public static final AudioCapabilities DEFAULT_AUDIO_CAPABILITIES = new AudioCapabilities(new int[]{2}, 8);
    private static final int DEFAULT_MAX_CHANNEL_COUNT = 8;
    private static final AudioCapabilities EXTERNAL_SURROUND_SOUND_CAPABILITIES = new AudioCapabilities(new int[]{2, 5, 6}, 8);
    private static final String EXTERNAL_SURROUND_SOUND_KEY = "external_surround_sound_enabled";
    private final int maxChannelCount;
    private final int[] supportedEncodings;

    public static AudioCapabilities getCapabilities(Context context) {
        return getCapabilities(context, context.registerReceiver(null, new IntentFilter("android.media.action.HDMI_AUDIO_PLUG")));
    }

    static AudioCapabilities getCapabilities(Context context, Intent intent) {
        if (deviceMaySetExternalSurroundSoundGlobalSetting() && Global.getInt(context.getContentResolver(), EXTERNAL_SURROUND_SOUND_KEY, 0) == 1) {
            return EXTERNAL_SURROUND_SOUND_CAPABILITIES;
        }
        if (intent == null || intent.getIntExtra("android.media.extra.AUDIO_PLUG_STATE", 0) == 0) {
            return DEFAULT_AUDIO_CAPABILITIES;
        }
        return new AudioCapabilities(intent.getIntArrayExtra("android.media.extra.ENCODINGS"), intent.getIntExtra("android.media.extra.MAX_CHANNEL_COUNT", 8));
    }

    static Uri getExternalSurroundSoundGlobalSettingUri() {
        if (deviceMaySetExternalSurroundSoundGlobalSetting()) {
            return Global.getUriFor(EXTERNAL_SURROUND_SOUND_KEY);
        }
        return null;
    }

    public AudioCapabilities(int[] iArr, int i) {
        if (iArr != null) {
            this.supportedEncodings = Arrays.copyOf(iArr, iArr.length);
            Arrays.sort(this.supportedEncodings);
        } else {
            this.supportedEncodings = new int[0];
        }
        this.maxChannelCount = i;
    }

    public boolean supportsEncoding(int i) {
        return Arrays.binarySearch(this.supportedEncodings, i) >= 0;
    }

    public int getMaxChannelCount() {
        return this.maxChannelCount;
    }

    public boolean equals(Object obj) {
        boolean z = true;
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof AudioCapabilities)) {
            return false;
        }
        AudioCapabilities audioCapabilities = (AudioCapabilities) obj;
        if (!Arrays.equals(this.supportedEncodings, audioCapabilities.supportedEncodings) || this.maxChannelCount != audioCapabilities.maxChannelCount) {
            z = false;
        }
        return z;
    }

    public int hashCode() {
        return this.maxChannelCount + (Arrays.hashCode(this.supportedEncodings) * 31);
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("AudioCapabilities[maxChannelCount=");
        sb.append(this.maxChannelCount);
        sb.append(", supportedEncodings=");
        sb.append(Arrays.toString(this.supportedEncodings));
        sb.append("]");
        return sb.toString();
    }

    private static boolean deviceMaySetExternalSurroundSoundGlobalSetting() {
        if (Util.SDK_INT >= 17) {
            if ("Amazon".equals(Util.MANUFACTURER)) {
                return true;
            }
        }
        return false;
    }
}
