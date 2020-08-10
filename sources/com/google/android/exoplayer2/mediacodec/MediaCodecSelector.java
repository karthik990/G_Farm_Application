package com.google.android.exoplayer2.mediacodec;

import com.google.android.exoplayer2.mediacodec.MediaCodecUtil.DecoderQueryException;
import java.util.List;

public interface MediaCodecSelector {
    public static final MediaCodecSelector DEFAULT = new MediaCodecSelector() {
        public List<MediaCodecInfo> getDecoderInfos(String str, boolean z, boolean z2) throws DecoderQueryException {
            return MediaCodecUtil.getDecoderInfos(str, z, z2);
        }

        public MediaCodecInfo getPassthroughDecoderInfo() throws DecoderQueryException {
            return MediaCodecUtil.getPassthroughDecoderInfo();
        }
    };

    List<MediaCodecInfo> getDecoderInfos(String str, boolean z, boolean z2) throws DecoderQueryException;

    MediaCodecInfo getPassthroughDecoderInfo() throws DecoderQueryException;
}
