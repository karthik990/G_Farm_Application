package com.google.android.exoplayer2.video;

import android.content.Context;
import android.graphics.Point;
import android.media.MediaCodec;
import android.media.MediaCodec.OnFrameRenderedListener;
import android.media.MediaCrypto;
import android.media.MediaFormat;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.util.Pair;
import android.view.Surface;
import androidx.work.WorkRequest;
import com.google.android.exoplayer2.C1996C;
import com.google.android.exoplayer2.ExoPlaybackException;
import com.google.android.exoplayer2.Format;
import com.google.android.exoplayer2.FormatHolder;
import com.google.android.exoplayer2.RendererCapabilities.CC;
import com.google.android.exoplayer2.decoder.DecoderInputBuffer;
import com.google.android.exoplayer2.drm.DrmInitData;
import com.google.android.exoplayer2.drm.DrmSessionManager;
import com.google.android.exoplayer2.drm.FrameworkMediaCrypto;
import com.google.android.exoplayer2.mediacodec.MediaCodecInfo;
import com.google.android.exoplayer2.mediacodec.MediaCodecRenderer;
import com.google.android.exoplayer2.mediacodec.MediaCodecRenderer.DecoderException;
import com.google.android.exoplayer2.mediacodec.MediaCodecSelector;
import com.google.android.exoplayer2.mediacodec.MediaCodecUtil;
import com.google.android.exoplayer2.mediacodec.MediaCodecUtil.DecoderQueryException;
import com.google.android.exoplayer2.mediacodec.MediaFormatUtil;
import com.google.android.exoplayer2.util.Assertions;
import com.google.android.exoplayer2.util.Log;
import com.google.android.exoplayer2.util.MimeTypes;
import com.google.android.exoplayer2.util.TraceUtil;
import com.google.android.exoplayer2.util.Util;
import com.google.android.exoplayer2.video.VideoRendererEventListener.EventDispatcher;
import com.google.android.gms.common.Scopes;
import java.nio.ByteBuffer;
import java.util.Collections;
import java.util.List;
import org.apache.commons.logging.LogFactory;
import p043io.fabric.sdk.android.services.settings.SettingsJsonConstants;

public class MediaCodecVideoRenderer extends MediaCodecRenderer {
    private static final float INITIAL_FORMAT_MAX_INPUT_SIZE_SCALE_FACTOR = 1.5f;
    private static final String KEY_CROP_BOTTOM = "crop-bottom";
    private static final String KEY_CROP_LEFT = "crop-left";
    private static final String KEY_CROP_RIGHT = "crop-right";
    private static final String KEY_CROP_TOP = "crop-top";
    private static final int MAX_PENDING_OUTPUT_STREAM_OFFSET_COUNT = 10;
    private static final int[] STANDARD_LONG_EDGE_VIDEO_PX = {1920, 1600, 1440, 1280, 960, 854, 640, 540, 480};
    private static final String TAG = "MediaCodecVideoRenderer";
    private static final long TUNNELING_EOS_PRESENTATION_TIME_US = Long.MAX_VALUE;
    private static boolean deviceNeedsSetOutputSurfaceWorkaround;
    private static boolean evaluatedDeviceNeedsSetOutputSurfaceWorkaround;
    private final long allowedJoiningTimeMs;
    private int buffersInCodecCount;
    private boolean codecHandlesHdr10PlusOutOfBandMetadata;
    private CodecMaxValues codecMaxValues;
    private boolean codecNeedsSetOutputSurfaceWorkaround;
    private int consecutiveDroppedFrameCount;
    private final Context context;
    private int currentHeight;
    private MediaFormat currentMediaFormat;
    private float currentPixelWidthHeightRatio;
    private int currentUnappliedRotationDegrees;
    private int currentWidth;
    private final boolean deviceNeedsNoPostProcessWorkaround;
    private long droppedFrameAccumulationStartTimeMs;
    private int droppedFrames;
    private Surface dummySurface;
    private final EventDispatcher eventDispatcher;
    private VideoFrameMetadataListener frameMetadataListener;
    private final VideoFrameReleaseTimeHelper frameReleaseTimeHelper;
    private long initialPositionUs;
    private long joiningDeadlineMs;
    private long lastInputTimeUs;
    private long lastRenderTimeUs;
    private final int maxDroppedFramesToNotify;
    private long outputStreamOffsetUs;
    private int pendingOutputStreamOffsetCount;
    private final long[] pendingOutputStreamOffsetsUs;
    private final long[] pendingOutputStreamSwitchTimesUs;
    private float pendingPixelWidthHeightRatio;
    private int pendingRotationDegrees;
    private boolean renderedFirstFrame;
    private int reportedHeight;
    private float reportedPixelWidthHeightRatio;
    private int reportedUnappliedRotationDegrees;
    private int reportedWidth;
    private int scalingMode;
    private Surface surface;
    private boolean tunneling;
    private int tunnelingAudioSessionId;
    OnFrameRenderedListenerV23 tunnelingOnFrameRenderedListener;

    protected static final class CodecMaxValues {
        public final int height;
        public final int inputSize;
        public final int width;

        public CodecMaxValues(int i, int i2, int i3) {
            this.width = i;
            this.height = i2;
            this.inputSize = i3;
        }
    }

    private final class OnFrameRenderedListenerV23 implements OnFrameRenderedListener {
        private OnFrameRenderedListenerV23(MediaCodec mediaCodec) {
            mediaCodec.setOnFrameRenderedListener(this, new Handler());
        }

        public void onFrameRendered(MediaCodec mediaCodec, long j, long j2) {
            if (this == MediaCodecVideoRenderer.this.tunnelingOnFrameRenderedListener) {
                if (j == Long.MAX_VALUE) {
                    MediaCodecVideoRenderer.this.onProcessedTunneledEndOfStream();
                } else {
                    MediaCodecVideoRenderer.this.onProcessedTunneledBuffer(j);
                }
            }
        }
    }

    public static final class VideoDecoderException extends DecoderException {
        public final boolean isSurfaceValid;
        public final int surfaceIdentityHashCode;

        public VideoDecoderException(Throwable th, MediaCodecInfo mediaCodecInfo, Surface surface) {
            super(th, mediaCodecInfo);
            this.surfaceIdentityHashCode = System.identityHashCode(surface);
            this.isSurfaceValid = surface == null || surface.isValid();
        }
    }

    private static boolean isBufferLate(long j) {
        return j < -30000;
    }

    private static boolean isBufferVeryLate(long j) {
        return j < -500000;
    }

    public MediaCodecVideoRenderer(Context context2, MediaCodecSelector mediaCodecSelector) {
        this(context2, mediaCodecSelector, 0);
    }

    public MediaCodecVideoRenderer(Context context2, MediaCodecSelector mediaCodecSelector, long j) {
        this(context2, mediaCodecSelector, j, null, null, -1);
    }

    public MediaCodecVideoRenderer(Context context2, MediaCodecSelector mediaCodecSelector, long j, Handler handler, VideoRendererEventListener videoRendererEventListener, int i) {
        this(context2, mediaCodecSelector, j, null, false, handler, videoRendererEventListener, i);
    }

    @Deprecated
    public MediaCodecVideoRenderer(Context context2, MediaCodecSelector mediaCodecSelector, long j, DrmSessionManager<FrameworkMediaCrypto> drmSessionManager, boolean z, Handler handler, VideoRendererEventListener videoRendererEventListener, int i) {
        this(context2, mediaCodecSelector, j, drmSessionManager, z, false, handler, videoRendererEventListener, i);
    }

    public MediaCodecVideoRenderer(Context context2, MediaCodecSelector mediaCodecSelector, long j, boolean z, Handler handler, VideoRendererEventListener videoRendererEventListener, int i) {
        this(context2, mediaCodecSelector, j, null, false, z, handler, videoRendererEventListener, i);
    }

    @Deprecated
    public MediaCodecVideoRenderer(Context context2, MediaCodecSelector mediaCodecSelector, long j, DrmSessionManager<FrameworkMediaCrypto> drmSessionManager, boolean z, boolean z2, Handler handler, VideoRendererEventListener videoRendererEventListener, int i) {
        super(2, mediaCodecSelector, drmSessionManager, z, z2, 30.0f);
        this.allowedJoiningTimeMs = j;
        this.maxDroppedFramesToNotify = i;
        this.context = context2.getApplicationContext();
        this.frameReleaseTimeHelper = new VideoFrameReleaseTimeHelper(this.context);
        this.eventDispatcher = new EventDispatcher(handler, videoRendererEventListener);
        this.deviceNeedsNoPostProcessWorkaround = deviceNeedsNoPostProcessWorkaround();
        this.pendingOutputStreamOffsetsUs = new long[10];
        this.pendingOutputStreamSwitchTimesUs = new long[10];
        this.outputStreamOffsetUs = C1996C.TIME_UNSET;
        this.lastInputTimeUs = C1996C.TIME_UNSET;
        this.joiningDeadlineMs = C1996C.TIME_UNSET;
        this.currentWidth = -1;
        this.currentHeight = -1;
        this.currentPixelWidthHeightRatio = -1.0f;
        this.pendingPixelWidthHeightRatio = -1.0f;
        this.scalingMode = 1;
        clearReportedVideoSize();
    }

    /* access modifiers changed from: protected */
    public int supportsFormat(MediaCodecSelector mediaCodecSelector, DrmSessionManager<FrameworkMediaCrypto> drmSessionManager, Format format) throws DecoderQueryException {
        int i = 0;
        if (!MimeTypes.isVideo(format.sampleMimeType)) {
            return CC.create(0);
        }
        DrmInitData drmInitData = format.drmInitData;
        boolean z = drmInitData != null;
        List decoderInfos = getDecoderInfos(mediaCodecSelector, format, z, false);
        if (z && decoderInfos.isEmpty()) {
            decoderInfos = getDecoderInfos(mediaCodecSelector, format, false, false);
        }
        if (decoderInfos.isEmpty()) {
            return CC.create(1);
        }
        if (!(drmInitData == null || FrameworkMediaCrypto.class.equals(format.exoMediaCryptoType) || (format.exoMediaCryptoType == null && supportsFormatDrm(drmSessionManager, drmInitData)))) {
            return CC.create(2);
        }
        MediaCodecInfo mediaCodecInfo = (MediaCodecInfo) decoderInfos.get(0);
        boolean isFormatSupported = mediaCodecInfo.isFormatSupported(format);
        int i2 = mediaCodecInfo.isSeamlessAdaptationSupported(format) ? 16 : 8;
        if (isFormatSupported) {
            List decoderInfos2 = getDecoderInfos(mediaCodecSelector, format, z, true);
            if (!decoderInfos2.isEmpty()) {
                MediaCodecInfo mediaCodecInfo2 = (MediaCodecInfo) decoderInfos2.get(0);
                if (mediaCodecInfo2.isFormatSupported(format) && mediaCodecInfo2.isSeamlessAdaptationSupported(format)) {
                    i = 32;
                }
            }
        }
        return CC.create(isFormatSupported ? 4 : 3, i2, i);
    }

    /* access modifiers changed from: protected */
    public List<MediaCodecInfo> getDecoderInfos(MediaCodecSelector mediaCodecSelector, Format format, boolean z) throws DecoderQueryException {
        return getDecoderInfos(mediaCodecSelector, format, z, this.tunneling);
    }

    private static List<MediaCodecInfo> getDecoderInfos(MediaCodecSelector mediaCodecSelector, Format format, boolean z, boolean z2) throws DecoderQueryException {
        String str = format.sampleMimeType;
        if (str == null) {
            return Collections.emptyList();
        }
        List decoderInfosSortedByFormatSupport = MediaCodecUtil.getDecoderInfosSortedByFormatSupport(mediaCodecSelector.getDecoderInfos(str, z, z2), format);
        if (MimeTypes.VIDEO_DOLBY_VISION.equals(str)) {
            Pair codecProfileAndLevel = MediaCodecUtil.getCodecProfileAndLevel(format);
            if (codecProfileAndLevel != null) {
                int intValue = ((Integer) codecProfileAndLevel.first).intValue();
                if (intValue == 16 || intValue == 256) {
                    decoderInfosSortedByFormatSupport.addAll(mediaCodecSelector.getDecoderInfos(MimeTypes.VIDEO_H265, z, z2));
                } else if (intValue == 512) {
                    decoderInfosSortedByFormatSupport.addAll(mediaCodecSelector.getDecoderInfos(MimeTypes.VIDEO_H264, z, z2));
                }
            }
        }
        return Collections.unmodifiableList(decoderInfosSortedByFormatSupport);
    }

    /* access modifiers changed from: protected */
    public void onEnabled(boolean z) throws ExoPlaybackException {
        super.onEnabled(z);
        int i = this.tunnelingAudioSessionId;
        this.tunnelingAudioSessionId = getConfiguration().tunnelingAudioSessionId;
        this.tunneling = this.tunnelingAudioSessionId != 0;
        if (this.tunnelingAudioSessionId != i) {
            releaseCodec();
        }
        this.eventDispatcher.enabled(this.decoderCounters);
        this.frameReleaseTimeHelper.enable();
    }

    /* access modifiers changed from: protected */
    public void onStreamChanged(Format[] formatArr, long j) throws ExoPlaybackException {
        if (this.outputStreamOffsetUs == C1996C.TIME_UNSET) {
            this.outputStreamOffsetUs = j;
        } else {
            int i = this.pendingOutputStreamOffsetCount;
            if (i == this.pendingOutputStreamOffsetsUs.length) {
                StringBuilder sb = new StringBuilder();
                sb.append("Too many stream changes, so dropping offset: ");
                sb.append(this.pendingOutputStreamOffsetsUs[this.pendingOutputStreamOffsetCount - 1]);
                Log.m1396w(TAG, sb.toString());
            } else {
                this.pendingOutputStreamOffsetCount = i + 1;
            }
            long[] jArr = this.pendingOutputStreamOffsetsUs;
            int i2 = this.pendingOutputStreamOffsetCount;
            jArr[i2 - 1] = j;
            this.pendingOutputStreamSwitchTimesUs[i2 - 1] = this.lastInputTimeUs;
        }
        super.onStreamChanged(formatArr, j);
    }

    /* access modifiers changed from: protected */
    public void onPositionReset(long j, boolean z) throws ExoPlaybackException {
        super.onPositionReset(j, z);
        clearRenderedFirstFrame();
        this.initialPositionUs = C1996C.TIME_UNSET;
        this.consecutiveDroppedFrameCount = 0;
        this.lastInputTimeUs = C1996C.TIME_UNSET;
        int i = this.pendingOutputStreamOffsetCount;
        if (i != 0) {
            this.outputStreamOffsetUs = this.pendingOutputStreamOffsetsUs[i - 1];
            this.pendingOutputStreamOffsetCount = 0;
        }
        if (z) {
            setJoiningDeadlineMs();
        } else {
            this.joiningDeadlineMs = C1996C.TIME_UNSET;
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:11:0x0020, code lost:
        if (r9.tunneling != false) goto L_0x0022;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:7:0x0016, code lost:
        if (r9.surface == r0) goto L_0x0022;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public boolean isReady() {
        /*
            r9 = this;
            boolean r0 = super.isReady()
            r1 = 1
            r2 = -9223372036854775807(0x8000000000000001, double:-4.9E-324)
            if (r0 == 0) goto L_0x0025
            boolean r0 = r9.renderedFirstFrame
            if (r0 != 0) goto L_0x0022
            android.view.Surface r0 = r9.dummySurface
            if (r0 == 0) goto L_0x0018
            android.view.Surface r4 = r9.surface
            if (r4 == r0) goto L_0x0022
        L_0x0018:
            android.media.MediaCodec r0 = r9.getCodec()
            if (r0 == 0) goto L_0x0022
            boolean r0 = r9.tunneling
            if (r0 == 0) goto L_0x0025
        L_0x0022:
            r9.joiningDeadlineMs = r2
            return r1
        L_0x0025:
            long r4 = r9.joiningDeadlineMs
            r0 = 0
            int r6 = (r4 > r2 ? 1 : (r4 == r2 ? 0 : -1))
            if (r6 != 0) goto L_0x002d
            return r0
        L_0x002d:
            long r4 = android.os.SystemClock.elapsedRealtime()
            long r6 = r9.joiningDeadlineMs
            int r8 = (r4 > r6 ? 1 : (r4 == r6 ? 0 : -1))
            if (r8 >= 0) goto L_0x0038
            return r1
        L_0x0038:
            r9.joiningDeadlineMs = r2
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.exoplayer2.video.MediaCodecVideoRenderer.isReady():boolean");
    }

    /* access modifiers changed from: protected */
    public void onStarted() {
        super.onStarted();
        this.droppedFrames = 0;
        this.droppedFrameAccumulationStartTimeMs = SystemClock.elapsedRealtime();
        this.lastRenderTimeUs = SystemClock.elapsedRealtime() * 1000;
    }

    /* access modifiers changed from: protected */
    public void onStopped() {
        this.joiningDeadlineMs = C1996C.TIME_UNSET;
        maybeNotifyDroppedFrames();
        super.onStopped();
    }

    /* access modifiers changed from: protected */
    public void onDisabled() {
        this.lastInputTimeUs = C1996C.TIME_UNSET;
        this.outputStreamOffsetUs = C1996C.TIME_UNSET;
        this.pendingOutputStreamOffsetCount = 0;
        this.currentMediaFormat = null;
        clearReportedVideoSize();
        clearRenderedFirstFrame();
        this.frameReleaseTimeHelper.disable();
        this.tunnelingOnFrameRenderedListener = null;
        try {
            super.onDisabled();
        } finally {
            this.eventDispatcher.disabled(this.decoderCounters);
        }
    }

    /* access modifiers changed from: protected */
    public void onReset() {
        try {
            super.onReset();
        } finally {
            Surface surface2 = this.dummySurface;
            if (surface2 != null) {
                if (this.surface == surface2) {
                    this.surface = null;
                }
                this.dummySurface.release();
                this.dummySurface = null;
            }
        }
    }

    public void handleMessage(int i, Object obj) throws ExoPlaybackException {
        if (i == 1) {
            setSurface((Surface) obj);
        } else if (i == 4) {
            this.scalingMode = ((Integer) obj).intValue();
            MediaCodec codec = getCodec();
            if (codec != null) {
                codec.setVideoScalingMode(this.scalingMode);
            }
        } else if (i == 6) {
            this.frameMetadataListener = (VideoFrameMetadataListener) obj;
        } else {
            super.handleMessage(i, obj);
        }
    }

    private void setSurface(Surface surface2) throws ExoPlaybackException {
        if (surface2 == null) {
            Surface surface3 = this.dummySurface;
            if (surface3 != null) {
                surface2 = surface3;
            } else {
                MediaCodecInfo codecInfo = getCodecInfo();
                if (codecInfo != null && shouldUseDummySurface(codecInfo)) {
                    this.dummySurface = DummySurface.newInstanceV17(this.context, codecInfo.secure);
                    surface2 = this.dummySurface;
                }
            }
        }
        if (this.surface != surface2) {
            this.surface = surface2;
            int state = getState();
            MediaCodec codec = getCodec();
            if (codec != null) {
                if (Util.SDK_INT < 23 || surface2 == null || this.codecNeedsSetOutputSurfaceWorkaround) {
                    releaseCodec();
                    maybeInitCodec();
                } else {
                    setOutputSurfaceV23(codec, surface2);
                }
            }
            if (surface2 == null || surface2 == this.dummySurface) {
                clearReportedVideoSize();
                clearRenderedFirstFrame();
                return;
            }
            maybeRenotifyVideoSizeChanged();
            clearRenderedFirstFrame();
            if (state == 2) {
                setJoiningDeadlineMs();
            }
        } else if (surface2 != null && surface2 != this.dummySurface) {
            maybeRenotifyVideoSizeChanged();
            maybeRenotifyRenderedFirstFrame();
        }
    }

    /* access modifiers changed from: protected */
    public boolean shouldInitCodec(MediaCodecInfo mediaCodecInfo) {
        return this.surface != null || shouldUseDummySurface(mediaCodecInfo);
    }

    /* access modifiers changed from: protected */
    public boolean getCodecNeedsEosPropagation() {
        return this.tunneling && Util.SDK_INT < 23;
    }

    /* access modifiers changed from: protected */
    public void configureCodec(MediaCodecInfo mediaCodecInfo, MediaCodec mediaCodec, Format format, MediaCrypto mediaCrypto, float f) {
        String str = mediaCodecInfo.codecMimeType;
        this.codecMaxValues = getCodecMaxValues(mediaCodecInfo, format, getStreamFormats());
        MediaFormat mediaFormat = getMediaFormat(format, str, this.codecMaxValues, f, this.deviceNeedsNoPostProcessWorkaround, this.tunnelingAudioSessionId);
        if (this.surface == null) {
            Assertions.checkState(shouldUseDummySurface(mediaCodecInfo));
            if (this.dummySurface == null) {
                this.dummySurface = DummySurface.newInstanceV17(this.context, mediaCodecInfo.secure);
            }
            this.surface = this.dummySurface;
        }
        mediaCodec.configure(mediaFormat, this.surface, mediaCrypto, 0);
        if (Util.SDK_INT >= 23 && this.tunneling) {
            this.tunnelingOnFrameRenderedListener = new OnFrameRenderedListenerV23(mediaCodec);
        }
    }

    /* access modifiers changed from: protected */
    public int canKeepCodec(MediaCodec mediaCodec, MediaCodecInfo mediaCodecInfo, Format format, Format format2) {
        if (!mediaCodecInfo.isSeamlessAdaptationSupported(format, format2, true) || format2.width > this.codecMaxValues.width || format2.height > this.codecMaxValues.height || getMaxInputSize(mediaCodecInfo, format2) > this.codecMaxValues.inputSize) {
            return 0;
        }
        return format.initializationDataEquals(format2) ? 3 : 2;
    }

    /* access modifiers changed from: protected */
    public void releaseCodec() {
        try {
            super.releaseCodec();
        } finally {
            this.buffersInCodecCount = 0;
        }
    }

    /* access modifiers changed from: protected */
    public boolean flushOrReleaseCodec() {
        try {
            return super.flushOrReleaseCodec();
        } finally {
            this.buffersInCodecCount = 0;
        }
    }

    /* access modifiers changed from: protected */
    public float getCodecOperatingRateV23(float f, Format format, Format[] formatArr) {
        float f2 = -1.0f;
        for (Format format2 : formatArr) {
            float f3 = format2.frameRate;
            if (f3 != -1.0f) {
                f2 = Math.max(f2, f3);
            }
        }
        if (f2 == -1.0f) {
            return -1.0f;
        }
        return f2 * f;
    }

    /* access modifiers changed from: protected */
    public void onCodecInitialized(String str, long j, long j2) {
        this.eventDispatcher.decoderInitialized(str, j, j2);
        this.codecNeedsSetOutputSurfaceWorkaround = codecNeedsSetOutputSurfaceWorkaround(str);
        this.codecHandlesHdr10PlusOutOfBandMetadata = ((MediaCodecInfo) Assertions.checkNotNull(getCodecInfo())).isHdr10PlusOutOfBandMetadataSupported();
    }

    /* access modifiers changed from: protected */
    public void onInputFormatChanged(FormatHolder formatHolder) throws ExoPlaybackException {
        super.onInputFormatChanged(formatHolder);
        Format format = formatHolder.format;
        this.eventDispatcher.inputFormatChanged(format);
        this.pendingPixelWidthHeightRatio = format.pixelWidthHeightRatio;
        this.pendingRotationDegrees = format.rotationDegrees;
    }

    /* access modifiers changed from: protected */
    public void onQueueInputBuffer(DecoderInputBuffer decoderInputBuffer) {
        this.buffersInCodecCount++;
        this.lastInputTimeUs = Math.max(decoderInputBuffer.timeUs, this.lastInputTimeUs);
        if (Util.SDK_INT < 23 && this.tunneling) {
            onProcessedTunneledBuffer(decoderInputBuffer.timeUs);
        }
    }

    /* access modifiers changed from: protected */
    public void onOutputFormatChanged(MediaCodec mediaCodec, MediaFormat mediaFormat) {
        int i;
        int i2;
        this.currentMediaFormat = mediaFormat;
        String str = KEY_CROP_RIGHT;
        boolean containsKey = mediaFormat.containsKey(str);
        String str2 = KEY_CROP_TOP;
        String str3 = KEY_CROP_BOTTOM;
        String str4 = KEY_CROP_LEFT;
        boolean z = containsKey && mediaFormat.containsKey(str4) && mediaFormat.containsKey(str3) && mediaFormat.containsKey(str2);
        if (z) {
            i = (mediaFormat.getInteger(str) - mediaFormat.getInteger(str4)) + 1;
        } else {
            i = mediaFormat.getInteger(SettingsJsonConstants.ICON_WIDTH_KEY);
        }
        if (z) {
            i2 = (mediaFormat.getInteger(str3) - mediaFormat.getInteger(str2)) + 1;
        } else {
            i2 = mediaFormat.getInteger(SettingsJsonConstants.ICON_HEIGHT_KEY);
        }
        processOutputFormat(mediaCodec, i, i2);
    }

    /* access modifiers changed from: protected */
    public void handleInputBufferSupplementalData(DecoderInputBuffer decoderInputBuffer) throws ExoPlaybackException {
        if (this.codecHandlesHdr10PlusOutOfBandMetadata) {
            ByteBuffer byteBuffer = (ByteBuffer) Assertions.checkNotNull(decoderInputBuffer.supplementalData);
            if (byteBuffer.remaining() >= 7) {
                byte b = byteBuffer.get();
                short s = byteBuffer.getShort();
                short s2 = byteBuffer.getShort();
                byte b2 = byteBuffer.get();
                byte b3 = byteBuffer.get();
                byteBuffer.position(0);
                if (b == -75 && s == 60 && s2 == 1 && b2 == 4 && b3 == 0) {
                    byte[] bArr = new byte[byteBuffer.remaining()];
                    byteBuffer.get(bArr);
                    byteBuffer.position(0);
                    setHdr10PlusInfoV29(getCodec(), bArr);
                }
            }
        }
    }

    /* access modifiers changed from: protected */
    public boolean processOutputBuffer(long j, long j2, MediaCodec mediaCodec, ByteBuffer byteBuffer, int i, int i2, long j3, boolean z, boolean z2, Format format) throws ExoPlaybackException {
        long j4 = j;
        MediaCodec mediaCodec2 = mediaCodec;
        int i3 = i;
        long j5 = j3;
        if (this.initialPositionUs == C1996C.TIME_UNSET) {
            this.initialPositionUs = j4;
        }
        long j6 = j5 - this.outputStreamOffsetUs;
        if (!z || z2) {
            long j7 = j5 - j4;
            if (this.surface != this.dummySurface) {
                long elapsedRealtime = SystemClock.elapsedRealtime() * 1000;
                long j8 = elapsedRealtime - this.lastRenderTimeUs;
                long j9 = elapsedRealtime;
                boolean z3 = getState() == 2;
                if (this.joiningDeadlineMs == C1996C.TIME_UNSET && j4 >= this.outputStreamOffsetUs && (!this.renderedFirstFrame || (z3 && shouldForceRenderOutputBuffer(j7, j8)))) {
                    long nanoTime = System.nanoTime();
                    notifyFrameMetadataListener(j6, nanoTime, format, this.currentMediaFormat);
                    if (Util.SDK_INT >= 21) {
                        renderOutputBufferV21(mediaCodec, i, j6, nanoTime);
                    } else {
                        renderOutputBuffer(mediaCodec2, i3, j6);
                    }
                    return true;
                }
                if (z3 && j4 != this.initialPositionUs) {
                    long j10 = j7 - (j9 - j2);
                    long nanoTime2 = System.nanoTime();
                    long adjustReleaseTime = this.frameReleaseTimeHelper.adjustReleaseTime(j3, (j10 * 1000) + nanoTime2);
                    long j11 = (adjustReleaseTime - nanoTime2) / 1000;
                    boolean z4 = this.joiningDeadlineMs != C1996C.TIME_UNSET;
                    if (shouldDropBuffersToKeyframe(j11, j2, z2) && maybeDropBuffersToKeyframe(mediaCodec, i, j6, j, z4)) {
                        return false;
                    }
                    if (shouldDropOutputBuffer(j11, j2, z2)) {
                        if (z4) {
                            skipOutputBuffer(mediaCodec2, i3, j6);
                        } else {
                            dropOutputBuffer(mediaCodec2, i3, j6);
                        }
                        return true;
                    }
                    if (Util.SDK_INT >= 21) {
                        if (j11 < 50000) {
                            notifyFrameMetadataListener(j6, adjustReleaseTime, format, this.currentMediaFormat);
                            renderOutputBufferV21(mediaCodec, i, j6, adjustReleaseTime);
                        }
                    } else if (j11 < 30000) {
                        if (j11 > 11000) {
                            try {
                                Thread.sleep((j11 - WorkRequest.MIN_BACKOFF_MILLIS) / 1000);
                            } catch (InterruptedException unused) {
                                Thread.currentThread().interrupt();
                                return false;
                            }
                        }
                        notifyFrameMetadataListener(j6, adjustReleaseTime, format, this.currentMediaFormat);
                        renderOutputBuffer(mediaCodec2, i3, j6);
                    }
                    return true;
                }
                return false;
            } else if (!isBufferLate(j7)) {
                return false;
            } else {
                skipOutputBuffer(mediaCodec2, i3, j6);
                return true;
            }
        } else {
            skipOutputBuffer(mediaCodec2, i3, j6);
            return true;
        }
    }

    private void processOutputFormat(MediaCodec mediaCodec, int i, int i2) {
        this.currentWidth = i;
        this.currentHeight = i2;
        this.currentPixelWidthHeightRatio = this.pendingPixelWidthHeightRatio;
        if (Util.SDK_INT >= 21) {
            int i3 = this.pendingRotationDegrees;
            if (i3 == 90 || i3 == 270) {
                int i4 = this.currentWidth;
                this.currentWidth = this.currentHeight;
                this.currentHeight = i4;
                this.currentPixelWidthHeightRatio = 1.0f / this.currentPixelWidthHeightRatio;
            }
        } else {
            this.currentUnappliedRotationDegrees = this.pendingRotationDegrees;
        }
        mediaCodec.setVideoScalingMode(this.scalingMode);
    }

    private void notifyFrameMetadataListener(long j, long j2, Format format, MediaFormat mediaFormat) {
        VideoFrameMetadataListener videoFrameMetadataListener = this.frameMetadataListener;
        if (videoFrameMetadataListener != null) {
            videoFrameMetadataListener.onVideoFrameAboutToBeRendered(j, j2, format, mediaFormat);
        }
    }

    /* access modifiers changed from: protected */
    public long getOutputStreamOffsetUs() {
        return this.outputStreamOffsetUs;
    }

    /* access modifiers changed from: protected */
    public void onProcessedTunneledBuffer(long j) {
        Format updateOutputFormatForTime = updateOutputFormatForTime(j);
        if (updateOutputFormatForTime != null) {
            processOutputFormat(getCodec(), updateOutputFormatForTime.width, updateOutputFormatForTime.height);
        }
        maybeNotifyVideoSizeChanged();
        maybeNotifyRenderedFirstFrame();
        onProcessedOutputBuffer(j);
    }

    /* access modifiers changed from: private */
    public void onProcessedTunneledEndOfStream() {
        setPendingOutputEndOfStream();
    }

    /* access modifiers changed from: protected */
    public void onProcessedOutputBuffer(long j) {
        this.buffersInCodecCount--;
        while (true) {
            int i = this.pendingOutputStreamOffsetCount;
            if (i != 0 && j >= this.pendingOutputStreamSwitchTimesUs[0]) {
                long[] jArr = this.pendingOutputStreamOffsetsUs;
                this.outputStreamOffsetUs = jArr[0];
                this.pendingOutputStreamOffsetCount = i - 1;
                System.arraycopy(jArr, 1, jArr, 0, this.pendingOutputStreamOffsetCount);
                long[] jArr2 = this.pendingOutputStreamSwitchTimesUs;
                System.arraycopy(jArr2, 1, jArr2, 0, this.pendingOutputStreamOffsetCount);
                clearRenderedFirstFrame();
            } else {
                return;
            }
        }
    }

    /* access modifiers changed from: protected */
    public boolean shouldDropOutputBuffer(long j, long j2, boolean z) {
        return isBufferLate(j) && !z;
    }

    /* access modifiers changed from: protected */
    public boolean shouldDropBuffersToKeyframe(long j, long j2, boolean z) {
        return isBufferVeryLate(j) && !z;
    }

    /* access modifiers changed from: protected */
    public boolean shouldForceRenderOutputBuffer(long j, long j2) {
        return isBufferLate(j) && j2 > 100000;
    }

    /* access modifiers changed from: protected */
    public void skipOutputBuffer(MediaCodec mediaCodec, int i, long j) {
        TraceUtil.beginSection("skipVideoBuffer");
        mediaCodec.releaseOutputBuffer(i, false);
        TraceUtil.endSection();
        this.decoderCounters.skippedOutputBufferCount++;
    }

    /* access modifiers changed from: protected */
    public void dropOutputBuffer(MediaCodec mediaCodec, int i, long j) {
        TraceUtil.beginSection("dropVideoBuffer");
        mediaCodec.releaseOutputBuffer(i, false);
        TraceUtil.endSection();
        updateDroppedBufferCounters(1);
    }

    /* access modifiers changed from: protected */
    public boolean maybeDropBuffersToKeyframe(MediaCodec mediaCodec, int i, long j, long j2, boolean z) throws ExoPlaybackException {
        int skipSource = skipSource(j2);
        if (skipSource == 0) {
            return false;
        }
        this.decoderCounters.droppedToKeyframeCount++;
        int i2 = this.buffersInCodecCount + skipSource;
        if (z) {
            this.decoderCounters.skippedOutputBufferCount += i2;
        } else {
            updateDroppedBufferCounters(i2);
        }
        flushOrReinitializeCodec();
        return true;
    }

    /* access modifiers changed from: protected */
    public void updateDroppedBufferCounters(int i) {
        this.decoderCounters.droppedBufferCount += i;
        this.droppedFrames += i;
        this.consecutiveDroppedFrameCount += i;
        this.decoderCounters.maxConsecutiveDroppedBufferCount = Math.max(this.consecutiveDroppedFrameCount, this.decoderCounters.maxConsecutiveDroppedBufferCount);
        int i2 = this.maxDroppedFramesToNotify;
        if (i2 > 0 && this.droppedFrames >= i2) {
            maybeNotifyDroppedFrames();
        }
    }

    /* access modifiers changed from: protected */
    public void renderOutputBuffer(MediaCodec mediaCodec, int i, long j) {
        maybeNotifyVideoSizeChanged();
        TraceUtil.beginSection("releaseOutputBuffer");
        mediaCodec.releaseOutputBuffer(i, true);
        TraceUtil.endSection();
        this.lastRenderTimeUs = SystemClock.elapsedRealtime() * 1000;
        this.decoderCounters.renderedOutputBufferCount++;
        this.consecutiveDroppedFrameCount = 0;
        maybeNotifyRenderedFirstFrame();
    }

    /* access modifiers changed from: protected */
    public void renderOutputBufferV21(MediaCodec mediaCodec, int i, long j, long j2) {
        maybeNotifyVideoSizeChanged();
        TraceUtil.beginSection("releaseOutputBuffer");
        mediaCodec.releaseOutputBuffer(i, j2);
        TraceUtil.endSection();
        this.lastRenderTimeUs = SystemClock.elapsedRealtime() * 1000;
        this.decoderCounters.renderedOutputBufferCount++;
        this.consecutiveDroppedFrameCount = 0;
        maybeNotifyRenderedFirstFrame();
    }

    private boolean shouldUseDummySurface(MediaCodecInfo mediaCodecInfo) {
        return Util.SDK_INT >= 23 && !this.tunneling && !codecNeedsSetOutputSurfaceWorkaround(mediaCodecInfo.name) && (!mediaCodecInfo.secure || DummySurface.isSecureSupported(this.context));
    }

    private void setJoiningDeadlineMs() {
        this.joiningDeadlineMs = this.allowedJoiningTimeMs > 0 ? SystemClock.elapsedRealtime() + this.allowedJoiningTimeMs : C1996C.TIME_UNSET;
    }

    private void clearRenderedFirstFrame() {
        this.renderedFirstFrame = false;
        if (Util.SDK_INT >= 23 && this.tunneling) {
            MediaCodec codec = getCodec();
            if (codec != null) {
                this.tunnelingOnFrameRenderedListener = new OnFrameRenderedListenerV23(codec);
            }
        }
    }

    /* access modifiers changed from: 0000 */
    public void maybeNotifyRenderedFirstFrame() {
        if (!this.renderedFirstFrame) {
            this.renderedFirstFrame = true;
            this.eventDispatcher.renderedFirstFrame(this.surface);
        }
    }

    private void maybeRenotifyRenderedFirstFrame() {
        if (this.renderedFirstFrame) {
            this.eventDispatcher.renderedFirstFrame(this.surface);
        }
    }

    private void clearReportedVideoSize() {
        this.reportedWidth = -1;
        this.reportedHeight = -1;
        this.reportedPixelWidthHeightRatio = -1.0f;
        this.reportedUnappliedRotationDegrees = -1;
    }

    private void maybeNotifyVideoSizeChanged() {
        if (this.currentWidth != -1 || this.currentHeight != -1) {
            if (this.reportedWidth != this.currentWidth || this.reportedHeight != this.currentHeight || this.reportedUnappliedRotationDegrees != this.currentUnappliedRotationDegrees || this.reportedPixelWidthHeightRatio != this.currentPixelWidthHeightRatio) {
                this.eventDispatcher.videoSizeChanged(this.currentWidth, this.currentHeight, this.currentUnappliedRotationDegrees, this.currentPixelWidthHeightRatio);
                this.reportedWidth = this.currentWidth;
                this.reportedHeight = this.currentHeight;
                this.reportedUnappliedRotationDegrees = this.currentUnappliedRotationDegrees;
                this.reportedPixelWidthHeightRatio = this.currentPixelWidthHeightRatio;
            }
        }
    }

    private void maybeRenotifyVideoSizeChanged() {
        if (this.reportedWidth != -1 || this.reportedHeight != -1) {
            this.eventDispatcher.videoSizeChanged(this.reportedWidth, this.reportedHeight, this.reportedUnappliedRotationDegrees, this.reportedPixelWidthHeightRatio);
        }
    }

    private void maybeNotifyDroppedFrames() {
        if (this.droppedFrames > 0) {
            long elapsedRealtime = SystemClock.elapsedRealtime();
            this.eventDispatcher.droppedFrames(this.droppedFrames, elapsedRealtime - this.droppedFrameAccumulationStartTimeMs);
            this.droppedFrames = 0;
            this.droppedFrameAccumulationStartTimeMs = elapsedRealtime;
        }
    }

    private static void setHdr10PlusInfoV29(MediaCodec mediaCodec, byte[] bArr) {
        Bundle bundle = new Bundle();
        bundle.putByteArray("hdr10-plus-info", bArr);
        mediaCodec.setParameters(bundle);
    }

    private static void setOutputSurfaceV23(MediaCodec mediaCodec, Surface surface2) {
        mediaCodec.setOutputSurface(surface2);
    }

    private static void configureTunnelingV21(MediaFormat mediaFormat, int i) {
        mediaFormat.setFeatureEnabled("tunneled-playback", true);
        mediaFormat.setInteger("audio-session-id", i);
    }

    /* access modifiers changed from: protected */
    public MediaFormat getMediaFormat(Format format, String str, CodecMaxValues codecMaxValues2, float f, boolean z, int i) {
        MediaFormat mediaFormat = new MediaFormat();
        mediaFormat.setString("mime", str);
        mediaFormat.setInteger(SettingsJsonConstants.ICON_WIDTH_KEY, format.width);
        mediaFormat.setInteger(SettingsJsonConstants.ICON_HEIGHT_KEY, format.height);
        MediaFormatUtil.setCsdBuffers(mediaFormat, format.initializationData);
        MediaFormatUtil.maybeSetFloat(mediaFormat, "frame-rate", format.frameRate);
        MediaFormatUtil.maybeSetInteger(mediaFormat, "rotation-degrees", format.rotationDegrees);
        MediaFormatUtil.maybeSetColorInfo(mediaFormat, format.colorInfo);
        if (MimeTypes.VIDEO_DOLBY_VISION.equals(format.sampleMimeType)) {
            Pair codecProfileAndLevel = MediaCodecUtil.getCodecProfileAndLevel(format);
            if (codecProfileAndLevel != null) {
                MediaFormatUtil.maybeSetInteger(mediaFormat, Scopes.PROFILE, ((Integer) codecProfileAndLevel.first).intValue());
            }
        }
        mediaFormat.setInteger("max-width", codecMaxValues2.width);
        mediaFormat.setInteger("max-height", codecMaxValues2.height);
        MediaFormatUtil.maybeSetInteger(mediaFormat, "max-input-size", codecMaxValues2.inputSize);
        if (Util.SDK_INT >= 23) {
            mediaFormat.setInteger(LogFactory.PRIORITY_KEY, 0);
            if (f != -1.0f) {
                mediaFormat.setFloat("operating-rate", f);
            }
        }
        if (z) {
            mediaFormat.setInteger("no-post-process", 1);
            mediaFormat.setInteger("auto-frc", 0);
        }
        if (i != 0) {
            configureTunnelingV21(mediaFormat, i);
        }
        return mediaFormat;
    }

    /* access modifiers changed from: protected */
    public CodecMaxValues getCodecMaxValues(MediaCodecInfo mediaCodecInfo, Format format, Format[] formatArr) {
        int i = format.width;
        int i2 = format.height;
        int maxInputSize = getMaxInputSize(mediaCodecInfo, format);
        if (formatArr.length == 1) {
            if (maxInputSize != -1) {
                int codecMaxInputSize = getCodecMaxInputSize(mediaCodecInfo, format.sampleMimeType, format.width, format.height);
                if (codecMaxInputSize != -1) {
                    maxInputSize = Math.min((int) (((float) maxInputSize) * INITIAL_FORMAT_MAX_INPUT_SIZE_SCALE_FACTOR), codecMaxInputSize);
                }
            }
            return new CodecMaxValues(i, i2, maxInputSize);
        }
        int i3 = i2;
        int i4 = maxInputSize;
        boolean z = false;
        int i5 = i;
        for (Format format2 : formatArr) {
            if (mediaCodecInfo.isSeamlessAdaptationSupported(format, format2, false)) {
                z |= format2.width == -1 || format2.height == -1;
                i5 = Math.max(i5, format2.width);
                i3 = Math.max(i3, format2.height);
                i4 = Math.max(i4, getMaxInputSize(mediaCodecInfo, format2));
            }
        }
        if (z) {
            StringBuilder sb = new StringBuilder();
            sb.append("Resolutions unknown. Codec max resolution: ");
            sb.append(i5);
            String str = "x";
            sb.append(str);
            sb.append(i3);
            String sb2 = sb.toString();
            String str2 = TAG;
            Log.m1396w(str2, sb2);
            Point codecMaxSize = getCodecMaxSize(mediaCodecInfo, format);
            if (codecMaxSize != null) {
                i5 = Math.max(i5, codecMaxSize.x);
                i3 = Math.max(i3, codecMaxSize.y);
                i4 = Math.max(i4, getCodecMaxInputSize(mediaCodecInfo, format.sampleMimeType, i5, i3));
                StringBuilder sb3 = new StringBuilder();
                sb3.append("Codec max resolution adjusted to: ");
                sb3.append(i5);
                sb3.append(str);
                sb3.append(i3);
                Log.m1396w(str2, sb3.toString());
            }
        }
        return new CodecMaxValues(i5, i3, i4);
    }

    /* access modifiers changed from: protected */
    public DecoderException createDecoderException(Throwable th, MediaCodecInfo mediaCodecInfo) {
        return new VideoDecoderException(th, mediaCodecInfo, this.surface);
    }

    private static Point getCodecMaxSize(MediaCodecInfo mediaCodecInfo, Format format) {
        int[] iArr;
        boolean z = format.height > format.width;
        int i = z ? format.height : format.width;
        int i2 = z ? format.width : format.height;
        float f = ((float) i2) / ((float) i);
        for (int i3 : STANDARD_LONG_EDGE_VIDEO_PX) {
            int i4 = (int) (((float) i3) * f);
            if (i3 <= i || i4 <= i2) {
                break;
            }
            if (Util.SDK_INT >= 21) {
                int i5 = z ? i4 : i3;
                if (!z) {
                    i3 = i4;
                }
                Point alignVideoSizeV21 = mediaCodecInfo.alignVideoSizeV21(i5, i3);
                if (mediaCodecInfo.isVideoSizeAndRateSupportedV21(alignVideoSizeV21.x, alignVideoSizeV21.y, (double) format.frameRate)) {
                    return alignVideoSizeV21;
                }
            } else {
                try {
                    int ceilDivide = Util.ceilDivide(i3, 16) * 16;
                    int ceilDivide2 = Util.ceilDivide(i4, 16) * 16;
                    if (ceilDivide * ceilDivide2 <= MediaCodecUtil.maxH264DecodableFrameSize()) {
                        int i6 = z ? ceilDivide2 : ceilDivide;
                        if (!z) {
                            ceilDivide = ceilDivide2;
                        }
                        return new Point(i6, ceilDivide);
                    }
                } catch (DecoderQueryException unused) {
                }
            }
        }
        return null;
    }

    private static int getMaxInputSize(MediaCodecInfo mediaCodecInfo, Format format) {
        if (format.maxInputSize == -1) {
            return getCodecMaxInputSize(mediaCodecInfo, format.sampleMimeType, format.width, format.height);
        }
        int i = 0;
        for (int i2 = 0; i2 < format.initializationData.size(); i2++) {
            i += ((byte[]) format.initializationData.get(i2)).length;
        }
        return format.maxInputSize + i;
    }

    /* JADX WARNING: Code restructure failed: missing block: B:41:0x008b, code lost:
        if (r7.secure != false) goto L_0x009f;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private static int getCodecMaxInputSize(com.google.android.exoplayer2.mediacodec.MediaCodecInfo r7, java.lang.String r8, int r9, int r10) {
        /*
            r0 = -1
            if (r9 == r0) goto L_0x00a9
            if (r10 != r0) goto L_0x0007
            goto L_0x00a9
        L_0x0007:
            int r1 = r8.hashCode()
            r2 = 5
            r3 = 1
            r4 = 4
            r5 = 3
            r6 = 2
            switch(r1) {
                case -1664118616: goto L_0x0046;
                case -1662541442: goto L_0x003c;
                case 1187890754: goto L_0x0032;
                case 1331836730: goto L_0x0028;
                case 1599127256: goto L_0x001e;
                case 1599127257: goto L_0x0014;
                default: goto L_0x0013;
            }
        L_0x0013:
            goto L_0x0050
        L_0x0014:
            java.lang.String r1 = "video/x-vnd.on2.vp9"
            boolean r8 = r8.equals(r1)
            if (r8 == 0) goto L_0x0050
            r8 = 5
            goto L_0x0051
        L_0x001e:
            java.lang.String r1 = "video/x-vnd.on2.vp8"
            boolean r8 = r8.equals(r1)
            if (r8 == 0) goto L_0x0050
            r8 = 3
            goto L_0x0051
        L_0x0028:
            java.lang.String r1 = "video/avc"
            boolean r8 = r8.equals(r1)
            if (r8 == 0) goto L_0x0050
            r8 = 2
            goto L_0x0051
        L_0x0032:
            java.lang.String r1 = "video/mp4v-es"
            boolean r8 = r8.equals(r1)
            if (r8 == 0) goto L_0x0050
            r8 = 1
            goto L_0x0051
        L_0x003c:
            java.lang.String r1 = "video/hevc"
            boolean r8 = r8.equals(r1)
            if (r8 == 0) goto L_0x0050
            r8 = 4
            goto L_0x0051
        L_0x0046:
            java.lang.String r1 = "video/3gpp"
            boolean r8 = r8.equals(r1)
            if (r8 == 0) goto L_0x0050
            r8 = 0
            goto L_0x0051
        L_0x0050:
            r8 = -1
        L_0x0051:
            if (r8 == 0) goto L_0x00a0
            if (r8 == r3) goto L_0x00a0
            if (r8 == r6) goto L_0x0061
            if (r8 == r5) goto L_0x00a0
            if (r8 == r4) goto L_0x005e
            if (r8 == r2) goto L_0x005e
            return r0
        L_0x005e:
            int r9 = r9 * r10
            goto L_0x00a3
        L_0x0061:
            java.lang.String r8 = com.google.android.exoplayer2.util.Util.MODEL
            java.lang.String r1 = "BRAVIA 4K 2015"
            boolean r8 = r1.equals(r8)
            if (r8 != 0) goto L_0x009f
            java.lang.String r8 = com.google.android.exoplayer2.util.Util.MANUFACTURER
            java.lang.String r1 = "Amazon"
            boolean r8 = r1.equals(r8)
            if (r8 == 0) goto L_0x008e
            java.lang.String r8 = com.google.android.exoplayer2.util.Util.MODEL
            java.lang.String r1 = "KFSOWI"
            boolean r8 = r1.equals(r8)
            if (r8 != 0) goto L_0x009f
            java.lang.String r8 = com.google.android.exoplayer2.util.Util.MODEL
            java.lang.String r1 = "AFTS"
            boolean r8 = r1.equals(r8)
            if (r8 == 0) goto L_0x008e
            boolean r7 = r7.secure
            if (r7 == 0) goto L_0x008e
            goto L_0x009f
        L_0x008e:
            r7 = 16
            int r8 = com.google.android.exoplayer2.util.Util.ceilDivide(r9, r7)
            int r9 = com.google.android.exoplayer2.util.Util.ceilDivide(r10, r7)
            int r8 = r8 * r9
            int r8 = r8 * 16
            int r9 = r8 * 16
            goto L_0x00a2
        L_0x009f:
            return r0
        L_0x00a0:
            int r9 = r9 * r10
        L_0x00a2:
            r4 = 2
        L_0x00a3:
            int r9 = r9 * 3
            int r4 = r4 * 2
            int r9 = r9 / r4
            return r9
        L_0x00a9:
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.exoplayer2.video.MediaCodecVideoRenderer.getCodecMaxInputSize(com.google.android.exoplayer2.mediacodec.MediaCodecInfo, java.lang.String, int, int):int");
    }

    private static boolean deviceNeedsNoPostProcessWorkaround() {
        return "NVIDIA".equals(Util.MANUFACTURER);
    }

    /* access modifiers changed from: protected */
    /* JADX WARNING: Code restructure failed: missing block: B:399:0x061a, code lost:
        r1 = 65535;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:400:0x061b, code lost:
        switch(r1) {
            case 0: goto L_0x061f;
            case 1: goto L_0x061f;
            case 2: goto L_0x061f;
            case 3: goto L_0x061f;
            case 4: goto L_0x061f;
            case 5: goto L_0x061f;
            case 6: goto L_0x061f;
            case 7: goto L_0x061f;
            case 8: goto L_0x061f;
            case 9: goto L_0x061f;
            case 10: goto L_0x061f;
            case 11: goto L_0x061f;
            case 12: goto L_0x061f;
            case 13: goto L_0x061f;
            case 14: goto L_0x061f;
            case 15: goto L_0x061f;
            case 16: goto L_0x061f;
            case 17: goto L_0x061f;
            case 18: goto L_0x061f;
            case 19: goto L_0x061f;
            case 20: goto L_0x061f;
            case 21: goto L_0x061f;
            case 22: goto L_0x061f;
            case 23: goto L_0x061f;
            case 24: goto L_0x061f;
            case 25: goto L_0x061f;
            case 26: goto L_0x061f;
            case 27: goto L_0x061f;
            case 28: goto L_0x061f;
            case 29: goto L_0x061f;
            case 30: goto L_0x061f;
            case 31: goto L_0x061f;
            case 32: goto L_0x061f;
            case 33: goto L_0x061f;
            case 34: goto L_0x061f;
            case 35: goto L_0x061f;
            case 36: goto L_0x061f;
            case 37: goto L_0x061f;
            case 38: goto L_0x061f;
            case 39: goto L_0x061f;
            case 40: goto L_0x061f;
            case 41: goto L_0x061f;
            case 42: goto L_0x061f;
            case 43: goto L_0x061f;
            case 44: goto L_0x061f;
            case 45: goto L_0x061f;
            case 46: goto L_0x061f;
            case 47: goto L_0x061f;
            case 48: goto L_0x061f;
            case 49: goto L_0x061f;
            case 50: goto L_0x061f;
            case 51: goto L_0x061f;
            case 52: goto L_0x061f;
            case 53: goto L_0x061f;
            case 54: goto L_0x061f;
            case 55: goto L_0x061f;
            case 56: goto L_0x061f;
            case 57: goto L_0x061f;
            case 58: goto L_0x061f;
            case 59: goto L_0x061f;
            case 60: goto L_0x061f;
            case 61: goto L_0x061f;
            case 62: goto L_0x061f;
            case 63: goto L_0x061f;
            case 64: goto L_0x061f;
            case 65: goto L_0x061f;
            case 66: goto L_0x061f;
            case 67: goto L_0x061f;
            case 68: goto L_0x061f;
            case 69: goto L_0x061f;
            case 70: goto L_0x061f;
            case 71: goto L_0x061f;
            case 72: goto L_0x061f;
            case 73: goto L_0x061f;
            case 74: goto L_0x061f;
            case 75: goto L_0x061f;
            case 76: goto L_0x061f;
            case 77: goto L_0x061f;
            case 78: goto L_0x061f;
            case 79: goto L_0x061f;
            case 80: goto L_0x061f;
            case 81: goto L_0x061f;
            case 82: goto L_0x061f;
            case 83: goto L_0x061f;
            case 84: goto L_0x061f;
            case 85: goto L_0x061f;
            case 86: goto L_0x061f;
            case 87: goto L_0x061f;
            case 88: goto L_0x061f;
            case 89: goto L_0x061f;
            case 90: goto L_0x061f;
            case 91: goto L_0x061f;
            case 92: goto L_0x061f;
            case 93: goto L_0x061f;
            case 94: goto L_0x061f;
            case 95: goto L_0x061f;
            case 96: goto L_0x061f;
            case 97: goto L_0x061f;
            case 98: goto L_0x061f;
            case 99: goto L_0x061f;
            case 100: goto L_0x061f;
            case 101: goto L_0x061f;
            case 102: goto L_0x061f;
            case 103: goto L_0x061f;
            case 104: goto L_0x061f;
            case 105: goto L_0x061f;
            case 106: goto L_0x061f;
            case 107: goto L_0x061f;
            case 108: goto L_0x061f;
            case 109: goto L_0x061f;
            case 110: goto L_0x061f;
            case 111: goto L_0x061f;
            case 112: goto L_0x061f;
            case 113: goto L_0x061f;
            case 114: goto L_0x061f;
            case 115: goto L_0x061f;
            case 116: goto L_0x061f;
            case 117: goto L_0x061f;
            case 118: goto L_0x061f;
            case 119: goto L_0x061f;
            case 120: goto L_0x061f;
            case 121: goto L_0x061f;
            case 122: goto L_0x061f;
            case 123: goto L_0x061f;
            case 124: goto L_0x061f;
            case org.objectweb.asm.Opcodes.LUSHR :int: goto L_0x061f;
            default: goto L_0x061e;
        };
     */
    /* JADX WARNING: Code restructure failed: missing block: B:402:0x061f, code lost:
        deviceNeedsSetOutputSurfaceWorkaround = true;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:403:0x0621, code lost:
        r1 = com.google.android.exoplayer2.util.Util.MODEL;
        r2 = r1.hashCode();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:404:0x062a, code lost:
        if (r2 == -594534941) goto L_0x064a;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:406:0x062f, code lost:
        if (r2 == 2006354) goto L_0x0641;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:408:0x0634, code lost:
        if (r2 == 2006367) goto L_0x0637;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:411:0x063d, code lost:
        if (r1.equals("AFTN") == false) goto L_0x0654;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:412:0x063f, code lost:
        r0 = 1;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:414:0x0647, code lost:
        if (r1.equals("AFTA") == false) goto L_0x0654;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:417:0x0650, code lost:
        if (r1.equals("JSN-L21") == false) goto L_0x0654;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:418:0x0652, code lost:
        r0 = 2;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:419:0x0654, code lost:
        r0 = 65535;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:420:0x0655, code lost:
        if (r0 == 0) goto L_0x065c;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:421:0x0657, code lost:
        if (r0 == 1) goto L_0x065c;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:422:0x0659, code lost:
        if (r0 == 2) goto L_0x065c;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:424:0x065c, code lost:
        deviceNeedsSetOutputSurfaceWorkaround = true;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public boolean codecNeedsSetOutputSurfaceWorkaround(java.lang.String r8) {
        /*
            r7 = this;
            java.lang.String r0 = "OMX.google"
            boolean r8 = r8.startsWith(r0)
            r0 = 0
            if (r8 == 0) goto L_0x000a
            return r0
        L_0x000a:
            java.lang.Class<com.google.android.exoplayer2.video.MediaCodecVideoRenderer> r8 = com.google.android.exoplayer2.video.MediaCodecVideoRenderer.class
            monitor-enter(r8)
            boolean r1 = evaluatedDeviceNeedsSetOutputSurfaceWorkaround     // Catch:{ all -> 0x0664 }
            if (r1 != 0) goto L_0x0660
            int r1 = com.google.android.exoplayer2.util.Util.SDK_INT     // Catch:{ all -> 0x0664 }
            r2 = 27
            r3 = 1
            if (r1 > r2) goto L_0x0030
            java.lang.String r1 = "dangal"
            java.lang.String r4 = com.google.android.exoplayer2.util.Util.DEVICE     // Catch:{ all -> 0x0664 }
            boolean r1 = r1.equals(r4)     // Catch:{ all -> 0x0664 }
            if (r1 != 0) goto L_0x002c
            java.lang.String r1 = "HWEML"
            java.lang.String r4 = com.google.android.exoplayer2.util.Util.DEVICE     // Catch:{ all -> 0x0664 }
            boolean r1 = r1.equals(r4)     // Catch:{ all -> 0x0664 }
            if (r1 == 0) goto L_0x0030
        L_0x002c:
            deviceNeedsSetOutputSurfaceWorkaround = r3     // Catch:{ all -> 0x0664 }
            goto L_0x065e
        L_0x0030:
            int r1 = com.google.android.exoplayer2.util.Util.SDK_INT     // Catch:{ all -> 0x0664 }
            if (r1 < r2) goto L_0x0036
            goto L_0x065e
        L_0x0036:
            java.lang.String r1 = com.google.android.exoplayer2.util.Util.DEVICE     // Catch:{ all -> 0x0664 }
            int r4 = r1.hashCode()     // Catch:{ all -> 0x0664 }
            r5 = -1
            r6 = 2
            switch(r4) {
                case -2144781245: goto L_0x060f;
                case -2144781185: goto L_0x0604;
                case -2144781160: goto L_0x05f9;
                case -2097309513: goto L_0x05ee;
                case -2022874474: goto L_0x05e3;
                case -1978993182: goto L_0x05d8;
                case -1978990237: goto L_0x05cd;
                case -1936688988: goto L_0x05c2;
                case -1936688066: goto L_0x05b7;
                case -1936688065: goto L_0x05ab;
                case -1931988508: goto L_0x059f;
                case -1696512866: goto L_0x0593;
                case -1680025915: goto L_0x0587;
                case -1615810839: goto L_0x057b;
                case -1554255044: goto L_0x056f;
                case -1481772737: goto L_0x0563;
                case -1481772730: goto L_0x0557;
                case -1481772729: goto L_0x054b;
                case -1320080169: goto L_0x053f;
                case -1217592143: goto L_0x0533;
                case -1180384755: goto L_0x0527;
                case -1139198265: goto L_0x051b;
                case -1052835013: goto L_0x050f;
                case -993250464: goto L_0x0504;
                case -993250458: goto L_0x04f9;
                case -965403638: goto L_0x04ed;
                case -958336948: goto L_0x04e1;
                case -879245230: goto L_0x04d5;
                case -842500323: goto L_0x04c9;
                case -821392978: goto L_0x04be;
                case -797483286: goto L_0x04b2;
                case -794946968: goto L_0x04a6;
                case -788334647: goto L_0x049a;
                case -782144577: goto L_0x048e;
                case -575125681: goto L_0x0482;
                case -521118391: goto L_0x0476;
                case -430914369: goto L_0x046a;
                case -290434366: goto L_0x045e;
                case -282781963: goto L_0x0452;
                case -277133239: goto L_0x0446;
                case -173639913: goto L_0x043a;
                case -56598463: goto L_0x042e;
                case 2126: goto L_0x0422;
                case 2564: goto L_0x0416;
                case 2715: goto L_0x040a;
                case 2719: goto L_0x03fe;
                case 3483: goto L_0x03f2;
                case 73405: goto L_0x03e6;
                case 75739: goto L_0x03da;
                case 76779: goto L_0x03ce;
                case 78669: goto L_0x03c2;
                case 79305: goto L_0x03b6;
                case 80618: goto L_0x03aa;
                case 88274: goto L_0x039e;
                case 98846: goto L_0x0392;
                case 98848: goto L_0x0386;
                case 99329: goto L_0x037a;
                case 101481: goto L_0x036e;
                case 1513190: goto L_0x0363;
                case 1514184: goto L_0x0358;
                case 1514185: goto L_0x034d;
                case 2436959: goto L_0x0341;
                case 2463773: goto L_0x0335;
                case 2464648: goto L_0x0329;
                case 2689555: goto L_0x031d;
                case 3154429: goto L_0x0311;
                case 3284551: goto L_0x0305;
                case 3351335: goto L_0x02f9;
                case 3386211: goto L_0x02ed;
                case 41325051: goto L_0x02e1;
                case 55178625: goto L_0x02d5;
                case 61542055: goto L_0x02ca;
                case 65355429: goto L_0x02be;
                case 66214468: goto L_0x02b2;
                case 66214470: goto L_0x02a6;
                case 66214473: goto L_0x029a;
                case 66215429: goto L_0x028e;
                case 66215431: goto L_0x0282;
                case 66215433: goto L_0x0276;
                case 66216390: goto L_0x026a;
                case 76402249: goto L_0x025e;
                case 76404105: goto L_0x0252;
                case 76404911: goto L_0x0246;
                case 80963634: goto L_0x023a;
                case 82882791: goto L_0x022e;
                case 98715550: goto L_0x0222;
                case 101370885: goto L_0x0216;
                case 102844228: goto L_0x020a;
                case 165221241: goto L_0x01ff;
                case 182191441: goto L_0x01f3;
                case 245388979: goto L_0x01e7;
                case 287431619: goto L_0x01db;
                case 307593612: goto L_0x01cf;
                case 308517133: goto L_0x01c3;
                case 316215098: goto L_0x01b7;
                case 316215116: goto L_0x01ab;
                case 316246811: goto L_0x019f;
                case 316246818: goto L_0x0193;
                case 407160593: goto L_0x0187;
                case 507412548: goto L_0x017b;
                case 793982701: goto L_0x016f;
                case 794038622: goto L_0x0163;
                case 794040393: goto L_0x0157;
                case 835649806: goto L_0x014b;
                case 917340916: goto L_0x013f;
                case 958008161: goto L_0x0133;
                case 1060579533: goto L_0x0127;
                case 1150207623: goto L_0x011b;
                case 1176899427: goto L_0x010f;
                case 1280332038: goto L_0x0103;
                case 1306947716: goto L_0x00f7;
                case 1349174697: goto L_0x00eb;
                case 1522194893: goto L_0x00df;
                case 1691543273: goto L_0x00d3;
                case 1709443163: goto L_0x00c7;
                case 1865889110: goto L_0x00bb;
                case 1906253259: goto L_0x00af;
                case 1977196784: goto L_0x00a3;
                case 2006372676: goto L_0x0097;
                case 2029784656: goto L_0x008b;
                case 2030379515: goto L_0x007f;
                case 2033393791: goto L_0x0073;
                case 2047190025: goto L_0x0067;
                case 2047252157: goto L_0x005b;
                case 2048319463: goto L_0x004f;
                case 2048855701: goto L_0x0043;
                default: goto L_0x0041;
            }     // Catch:{ all -> 0x0664 }
        L_0x0041:
            goto L_0x061a
        L_0x0043:
            java.lang.String r2 = "HWWAS-H"
            boolean r1 = r1.equals(r2)     // Catch:{ all -> 0x0664 }
            if (r1 == 0) goto L_0x061a
            r1 = 55
            goto L_0x061b
        L_0x004f:
            java.lang.String r2 = "HWVNS-H"
            boolean r1 = r1.equals(r2)     // Catch:{ all -> 0x0664 }
            if (r1 == 0) goto L_0x061a
            r1 = 54
            goto L_0x061b
        L_0x005b:
            java.lang.String r2 = "ELUGA_Prim"
            boolean r1 = r1.equals(r2)     // Catch:{ all -> 0x0664 }
            if (r1 == 0) goto L_0x061a
            r1 = 28
            goto L_0x061b
        L_0x0067:
            java.lang.String r4 = "ELUGA_Note"
            boolean r1 = r1.equals(r4)     // Catch:{ all -> 0x0664 }
            if (r1 == 0) goto L_0x061a
            r1 = 27
            goto L_0x061b
        L_0x0073:
            java.lang.String r2 = "ASUS_X00AD_2"
            boolean r1 = r1.equals(r2)     // Catch:{ all -> 0x0664 }
            if (r1 == 0) goto L_0x061a
            r1 = 12
            goto L_0x061b
        L_0x007f:
            java.lang.String r2 = "HWCAM-H"
            boolean r1 = r1.equals(r2)     // Catch:{ all -> 0x0664 }
            if (r1 == 0) goto L_0x061a
            r1 = 53
            goto L_0x061b
        L_0x008b:
            java.lang.String r2 = "HWBLN-H"
            boolean r1 = r1.equals(r2)     // Catch:{ all -> 0x0664 }
            if (r1 == 0) goto L_0x061a
            r1 = 52
            goto L_0x061b
        L_0x0097:
            java.lang.String r2 = "BRAVIA_ATV3_4K"
            boolean r1 = r1.equals(r2)     // Catch:{ all -> 0x0664 }
            if (r1 == 0) goto L_0x061a
            r1 = 16
            goto L_0x061b
        L_0x00a3:
            java.lang.String r2 = "Infinix-X572"
            boolean r1 = r1.equals(r2)     // Catch:{ all -> 0x0664 }
            if (r1 == 0) goto L_0x061a
            r1 = 58
            goto L_0x061b
        L_0x00af:
            java.lang.String r2 = "PB2-670M"
            boolean r1 = r1.equals(r2)     // Catch:{ all -> 0x0664 }
            if (r1 == 0) goto L_0x061a
            r1 = 87
            goto L_0x061b
        L_0x00bb:
            java.lang.String r2 = "santoni"
            boolean r1 = r1.equals(r2)     // Catch:{ all -> 0x0664 }
            if (r1 == 0) goto L_0x061a
            r1 = 103(0x67, float:1.44E-43)
            goto L_0x061b
        L_0x00c7:
            java.lang.String r2 = "iball8735_9806"
            boolean r1 = r1.equals(r2)     // Catch:{ all -> 0x0664 }
            if (r1 == 0) goto L_0x061a
            r1 = 57
            goto L_0x061b
        L_0x00d3:
            java.lang.String r2 = "CPH1609"
            boolean r1 = r1.equals(r2)     // Catch:{ all -> 0x0664 }
            if (r1 == 0) goto L_0x061a
            r1 = 20
            goto L_0x061b
        L_0x00df:
            java.lang.String r2 = "woods_f"
            boolean r1 = r1.equals(r2)     // Catch:{ all -> 0x0664 }
            if (r1 == 0) goto L_0x061a
            r1 = 119(0x77, float:1.67E-43)
            goto L_0x061b
        L_0x00eb:
            java.lang.String r2 = "htc_e56ml_dtul"
            boolean r1 = r1.equals(r2)     // Catch:{ all -> 0x0664 }
            if (r1 == 0) goto L_0x061a
            r1 = 50
            goto L_0x061b
        L_0x00f7:
            java.lang.String r2 = "EverStar_S"
            boolean r1 = r1.equals(r2)     // Catch:{ all -> 0x0664 }
            if (r1 == 0) goto L_0x061a
            r1 = 30
            goto L_0x061b
        L_0x0103:
            java.lang.String r2 = "hwALE-H"
            boolean r1 = r1.equals(r2)     // Catch:{ all -> 0x0664 }
            if (r1 == 0) goto L_0x061a
            r1 = 51
            goto L_0x061b
        L_0x010f:
            java.lang.String r2 = "itel_S41"
            boolean r1 = r1.equals(r2)     // Catch:{ all -> 0x0664 }
            if (r1 == 0) goto L_0x061a
            r1 = 60
            goto L_0x061b
        L_0x011b:
            java.lang.String r2 = "LS-5017"
            boolean r1 = r1.equals(r2)     // Catch:{ all -> 0x0664 }
            if (r1 == 0) goto L_0x061a
            r1 = 67
            goto L_0x061b
        L_0x0127:
            java.lang.String r2 = "panell_d"
            boolean r1 = r1.equals(r2)     // Catch:{ all -> 0x0664 }
            if (r1 == 0) goto L_0x061a
            r1 = 83
            goto L_0x061b
        L_0x0133:
            java.lang.String r2 = "j2xlteins"
            boolean r1 = r1.equals(r2)     // Catch:{ all -> 0x0664 }
            if (r1 == 0) goto L_0x061a
            r1 = 61
            goto L_0x061b
        L_0x013f:
            java.lang.String r2 = "A7000plus"
            boolean r1 = r1.equals(r2)     // Catch:{ all -> 0x0664 }
            if (r1 == 0) goto L_0x061a
            r1 = 8
            goto L_0x061b
        L_0x014b:
            java.lang.String r2 = "manning"
            boolean r1 = r1.equals(r2)     // Catch:{ all -> 0x0664 }
            if (r1 == 0) goto L_0x061a
            r1 = 69
            goto L_0x061b
        L_0x0157:
            java.lang.String r2 = "GIONEE_WBL7519"
            boolean r1 = r1.equals(r2)     // Catch:{ all -> 0x0664 }
            if (r1 == 0) goto L_0x061a
            r1 = 48
            goto L_0x061b
        L_0x0163:
            java.lang.String r2 = "GIONEE_WBL7365"
            boolean r1 = r1.equals(r2)     // Catch:{ all -> 0x0664 }
            if (r1 == 0) goto L_0x061a
            r1 = 47
            goto L_0x061b
        L_0x016f:
            java.lang.String r2 = "GIONEE_WBL5708"
            boolean r1 = r1.equals(r2)     // Catch:{ all -> 0x0664 }
            if (r1 == 0) goto L_0x061a
            r1 = 46
            goto L_0x061b
        L_0x017b:
            java.lang.String r2 = "QM16XE_U"
            boolean r1 = r1.equals(r2)     // Catch:{ all -> 0x0664 }
            if (r1 == 0) goto L_0x061a
            r1 = 101(0x65, float:1.42E-43)
            goto L_0x061b
        L_0x0187:
            java.lang.String r2 = "Pixi5-10_4G"
            boolean r1 = r1.equals(r2)     // Catch:{ all -> 0x0664 }
            if (r1 == 0) goto L_0x061a
            r1 = 93
            goto L_0x061b
        L_0x0193:
            java.lang.String r2 = "TB3-850M"
            boolean r1 = r1.equals(r2)     // Catch:{ all -> 0x0664 }
            if (r1 == 0) goto L_0x061a
            r1 = 111(0x6f, float:1.56E-43)
            goto L_0x061b
        L_0x019f:
            java.lang.String r2 = "TB3-850F"
            boolean r1 = r1.equals(r2)     // Catch:{ all -> 0x0664 }
            if (r1 == 0) goto L_0x061a
            r1 = 110(0x6e, float:1.54E-43)
            goto L_0x061b
        L_0x01ab:
            java.lang.String r2 = "TB3-730X"
            boolean r1 = r1.equals(r2)     // Catch:{ all -> 0x0664 }
            if (r1 == 0) goto L_0x061a
            r1 = 109(0x6d, float:1.53E-43)
            goto L_0x061b
        L_0x01b7:
            java.lang.String r2 = "TB3-730F"
            boolean r1 = r1.equals(r2)     // Catch:{ all -> 0x0664 }
            if (r1 == 0) goto L_0x061a
            r1 = 108(0x6c, float:1.51E-43)
            goto L_0x061b
        L_0x01c3:
            java.lang.String r2 = "A7020a48"
            boolean r1 = r1.equals(r2)     // Catch:{ all -> 0x0664 }
            if (r1 == 0) goto L_0x061a
            r1 = 10
            goto L_0x061b
        L_0x01cf:
            java.lang.String r2 = "A7010a48"
            boolean r1 = r1.equals(r2)     // Catch:{ all -> 0x0664 }
            if (r1 == 0) goto L_0x061a
            r1 = 9
            goto L_0x061b
        L_0x01db:
            java.lang.String r2 = "griffin"
            boolean r1 = r1.equals(r2)     // Catch:{ all -> 0x0664 }
            if (r1 == 0) goto L_0x061a
            r1 = 49
            goto L_0x061b
        L_0x01e7:
            java.lang.String r2 = "marino_f"
            boolean r1 = r1.equals(r2)     // Catch:{ all -> 0x0664 }
            if (r1 == 0) goto L_0x061a
            r1 = 70
            goto L_0x061b
        L_0x01f3:
            java.lang.String r2 = "CPY83_I00"
            boolean r1 = r1.equals(r2)     // Catch:{ all -> 0x0664 }
            if (r1 == 0) goto L_0x061a
            r1 = 21
            goto L_0x061b
        L_0x01ff:
            java.lang.String r2 = "A2016a40"
            boolean r1 = r1.equals(r2)     // Catch:{ all -> 0x0664 }
            if (r1 == 0) goto L_0x061a
            r1 = 6
            goto L_0x061b
        L_0x020a:
            java.lang.String r2 = "le_x6"
            boolean r1 = r1.equals(r2)     // Catch:{ all -> 0x0664 }
            if (r1 == 0) goto L_0x061a
            r1 = 66
            goto L_0x061b
        L_0x0216:
            java.lang.String r2 = "l5460"
            boolean r1 = r1.equals(r2)     // Catch:{ all -> 0x0664 }
            if (r1 == 0) goto L_0x061a
            r1 = 65
            goto L_0x061b
        L_0x0222:
            java.lang.String r2 = "i9031"
            boolean r1 = r1.equals(r2)     // Catch:{ all -> 0x0664 }
            if (r1 == 0) goto L_0x061a
            r1 = 56
            goto L_0x061b
        L_0x022e:
            java.lang.String r2 = "X3_HK"
            boolean r1 = r1.equals(r2)     // Catch:{ all -> 0x0664 }
            if (r1 == 0) goto L_0x061a
            r1 = 121(0x79, float:1.7E-43)
            goto L_0x061b
        L_0x023a:
            java.lang.String r2 = "V23GB"
            boolean r1 = r1.equals(r2)     // Catch:{ all -> 0x0664 }
            if (r1 == 0) goto L_0x061a
            r1 = 114(0x72, float:1.6E-43)
            goto L_0x061b
        L_0x0246:
            java.lang.String r2 = "Q4310"
            boolean r1 = r1.equals(r2)     // Catch:{ all -> 0x0664 }
            if (r1 == 0) goto L_0x061a
            r1 = 99
            goto L_0x061b
        L_0x0252:
            java.lang.String r2 = "Q4260"
            boolean r1 = r1.equals(r2)     // Catch:{ all -> 0x0664 }
            if (r1 == 0) goto L_0x061a
            r1 = 97
            goto L_0x061b
        L_0x025e:
            java.lang.String r2 = "PRO7S"
            boolean r1 = r1.equals(r2)     // Catch:{ all -> 0x0664 }
            if (r1 == 0) goto L_0x061a
            r1 = 95
            goto L_0x061b
        L_0x026a:
            java.lang.String r2 = "F3311"
            boolean r1 = r1.equals(r2)     // Catch:{ all -> 0x0664 }
            if (r1 == 0) goto L_0x061a
            r1 = 37
            goto L_0x061b
        L_0x0276:
            java.lang.String r2 = "F3215"
            boolean r1 = r1.equals(r2)     // Catch:{ all -> 0x0664 }
            if (r1 == 0) goto L_0x061a
            r1 = 36
            goto L_0x061b
        L_0x0282:
            java.lang.String r2 = "F3213"
            boolean r1 = r1.equals(r2)     // Catch:{ all -> 0x0664 }
            if (r1 == 0) goto L_0x061a
            r1 = 35
            goto L_0x061b
        L_0x028e:
            java.lang.String r2 = "F3211"
            boolean r1 = r1.equals(r2)     // Catch:{ all -> 0x0664 }
            if (r1 == 0) goto L_0x061a
            r1 = 34
            goto L_0x061b
        L_0x029a:
            java.lang.String r2 = "F3116"
            boolean r1 = r1.equals(r2)     // Catch:{ all -> 0x0664 }
            if (r1 == 0) goto L_0x061a
            r1 = 33
            goto L_0x061b
        L_0x02a6:
            java.lang.String r2 = "F3113"
            boolean r1 = r1.equals(r2)     // Catch:{ all -> 0x0664 }
            if (r1 == 0) goto L_0x061a
            r1 = 32
            goto L_0x061b
        L_0x02b2:
            java.lang.String r2 = "F3111"
            boolean r1 = r1.equals(r2)     // Catch:{ all -> 0x0664 }
            if (r1 == 0) goto L_0x061a
            r1 = 31
            goto L_0x061b
        L_0x02be:
            java.lang.String r2 = "E5643"
            boolean r1 = r1.equals(r2)     // Catch:{ all -> 0x0664 }
            if (r1 == 0) goto L_0x061a
            r1 = 25
            goto L_0x061b
        L_0x02ca:
            java.lang.String r2 = "A1601"
            boolean r1 = r1.equals(r2)     // Catch:{ all -> 0x0664 }
            if (r1 == 0) goto L_0x061a
            r1 = 5
            goto L_0x061b
        L_0x02d5:
            java.lang.String r2 = "Aura_Note_2"
            boolean r1 = r1.equals(r2)     // Catch:{ all -> 0x0664 }
            if (r1 == 0) goto L_0x061a
            r1 = 13
            goto L_0x061b
        L_0x02e1:
            java.lang.String r2 = "MEIZU_M5"
            boolean r1 = r1.equals(r2)     // Catch:{ all -> 0x0664 }
            if (r1 == 0) goto L_0x061a
            r1 = 71
            goto L_0x061b
        L_0x02ed:
            java.lang.String r2 = "p212"
            boolean r1 = r1.equals(r2)     // Catch:{ all -> 0x0664 }
            if (r1 == 0) goto L_0x061a
            r1 = 80
            goto L_0x061b
        L_0x02f9:
            java.lang.String r2 = "mido"
            boolean r1 = r1.equals(r2)     // Catch:{ all -> 0x0664 }
            if (r1 == 0) goto L_0x061a
            r1 = 73
            goto L_0x061b
        L_0x0305:
            java.lang.String r2 = "kate"
            boolean r1 = r1.equals(r2)     // Catch:{ all -> 0x0664 }
            if (r1 == 0) goto L_0x061a
            r1 = 64
            goto L_0x061b
        L_0x0311:
            java.lang.String r2 = "fugu"
            boolean r1 = r1.equals(r2)     // Catch:{ all -> 0x0664 }
            if (r1 == 0) goto L_0x061a
            r1 = 39
            goto L_0x061b
        L_0x031d:
            java.lang.String r2 = "XE2X"
            boolean r1 = r1.equals(r2)     // Catch:{ all -> 0x0664 }
            if (r1 == 0) goto L_0x061a
            r1 = 122(0x7a, float:1.71E-43)
            goto L_0x061b
        L_0x0329:
            java.lang.String r2 = "Q427"
            boolean r1 = r1.equals(r2)     // Catch:{ all -> 0x0664 }
            if (r1 == 0) goto L_0x061a
            r1 = 98
            goto L_0x061b
        L_0x0335:
            java.lang.String r2 = "Q350"
            boolean r1 = r1.equals(r2)     // Catch:{ all -> 0x0664 }
            if (r1 == 0) goto L_0x061a
            r1 = 96
            goto L_0x061b
        L_0x0341:
            java.lang.String r2 = "P681"
            boolean r1 = r1.equals(r2)     // Catch:{ all -> 0x0664 }
            if (r1 == 0) goto L_0x061a
            r1 = 81
            goto L_0x061b
        L_0x034d:
            java.lang.String r2 = "1714"
            boolean r1 = r1.equals(r2)     // Catch:{ all -> 0x0664 }
            if (r1 == 0) goto L_0x061a
            r1 = 2
            goto L_0x061b
        L_0x0358:
            java.lang.String r2 = "1713"
            boolean r1 = r1.equals(r2)     // Catch:{ all -> 0x0664 }
            if (r1 == 0) goto L_0x061a
            r1 = 1
            goto L_0x061b
        L_0x0363:
            java.lang.String r2 = "1601"
            boolean r1 = r1.equals(r2)     // Catch:{ all -> 0x0664 }
            if (r1 == 0) goto L_0x061a
            r1 = 0
            goto L_0x061b
        L_0x036e:
            java.lang.String r2 = "flo"
            boolean r1 = r1.equals(r2)     // Catch:{ all -> 0x0664 }
            if (r1 == 0) goto L_0x061a
            r1 = 38
            goto L_0x061b
        L_0x037a:
            java.lang.String r2 = "deb"
            boolean r1 = r1.equals(r2)     // Catch:{ all -> 0x0664 }
            if (r1 == 0) goto L_0x061a
            r1 = 24
            goto L_0x061b
        L_0x0386:
            java.lang.String r2 = "cv3"
            boolean r1 = r1.equals(r2)     // Catch:{ all -> 0x0664 }
            if (r1 == 0) goto L_0x061a
            r1 = 23
            goto L_0x061b
        L_0x0392:
            java.lang.String r2 = "cv1"
            boolean r1 = r1.equals(r2)     // Catch:{ all -> 0x0664 }
            if (r1 == 0) goto L_0x061a
            r1 = 22
            goto L_0x061b
        L_0x039e:
            java.lang.String r2 = "Z80"
            boolean r1 = r1.equals(r2)     // Catch:{ all -> 0x0664 }
            if (r1 == 0) goto L_0x061a
            r1 = 125(0x7d, float:1.75E-43)
            goto L_0x061b
        L_0x03aa:
            java.lang.String r2 = "QX1"
            boolean r1 = r1.equals(r2)     // Catch:{ all -> 0x0664 }
            if (r1 == 0) goto L_0x061a
            r1 = 102(0x66, float:1.43E-43)
            goto L_0x061b
        L_0x03b6:
            java.lang.String r2 = "PLE"
            boolean r1 = r1.equals(r2)     // Catch:{ all -> 0x0664 }
            if (r1 == 0) goto L_0x061a
            r1 = 94
            goto L_0x061b
        L_0x03c2:
            java.lang.String r2 = "P85"
            boolean r1 = r1.equals(r2)     // Catch:{ all -> 0x0664 }
            if (r1 == 0) goto L_0x061a
            r1 = 82
            goto L_0x061b
        L_0x03ce:
            java.lang.String r2 = "MX6"
            boolean r1 = r1.equals(r2)     // Catch:{ all -> 0x0664 }
            if (r1 == 0) goto L_0x061a
            r1 = 74
            goto L_0x061b
        L_0x03da:
            java.lang.String r2 = "M5c"
            boolean r1 = r1.equals(r2)     // Catch:{ all -> 0x0664 }
            if (r1 == 0) goto L_0x061a
            r1 = 68
            goto L_0x061b
        L_0x03e6:
            java.lang.String r2 = "JGZ"
            boolean r1 = r1.equals(r2)     // Catch:{ all -> 0x0664 }
            if (r1 == 0) goto L_0x061a
            r1 = 62
            goto L_0x061b
        L_0x03f2:
            java.lang.String r2 = "mh"
            boolean r1 = r1.equals(r2)     // Catch:{ all -> 0x0664 }
            if (r1 == 0) goto L_0x061a
            r1 = 72
            goto L_0x061b
        L_0x03fe:
            java.lang.String r2 = "V5"
            boolean r1 = r1.equals(r2)     // Catch:{ all -> 0x0664 }
            if (r1 == 0) goto L_0x061a
            r1 = 115(0x73, float:1.61E-43)
            goto L_0x061b
        L_0x040a:
            java.lang.String r2 = "V1"
            boolean r1 = r1.equals(r2)     // Catch:{ all -> 0x0664 }
            if (r1 == 0) goto L_0x061a
            r1 = 113(0x71, float:1.58E-43)
            goto L_0x061b
        L_0x0416:
            java.lang.String r2 = "Q5"
            boolean r1 = r1.equals(r2)     // Catch:{ all -> 0x0664 }
            if (r1 == 0) goto L_0x061a
            r1 = 100
            goto L_0x061b
        L_0x0422:
            java.lang.String r2 = "C1"
            boolean r1 = r1.equals(r2)     // Catch:{ all -> 0x0664 }
            if (r1 == 0) goto L_0x061a
            r1 = 17
            goto L_0x061b
        L_0x042e:
            java.lang.String r2 = "woods_fn"
            boolean r1 = r1.equals(r2)     // Catch:{ all -> 0x0664 }
            if (r1 == 0) goto L_0x061a
            r1 = 120(0x78, float:1.68E-43)
            goto L_0x061b
        L_0x043a:
            java.lang.String r2 = "ELUGA_A3_Pro"
            boolean r1 = r1.equals(r2)     // Catch:{ all -> 0x0664 }
            if (r1 == 0) goto L_0x061a
            r1 = 26
            goto L_0x061b
        L_0x0446:
            java.lang.String r2 = "Z12_PRO"
            boolean r1 = r1.equals(r2)     // Catch:{ all -> 0x0664 }
            if (r1 == 0) goto L_0x061a
            r1 = 124(0x7c, float:1.74E-43)
            goto L_0x061b
        L_0x0452:
            java.lang.String r2 = "BLACK-1X"
            boolean r1 = r1.equals(r2)     // Catch:{ all -> 0x0664 }
            if (r1 == 0) goto L_0x061a
            r1 = 14
            goto L_0x061b
        L_0x045e:
            java.lang.String r2 = "taido_row"
            boolean r1 = r1.equals(r2)     // Catch:{ all -> 0x0664 }
            if (r1 == 0) goto L_0x061a
            r1 = 107(0x6b, float:1.5E-43)
            goto L_0x061b
        L_0x046a:
            java.lang.String r2 = "Pixi4-7_3G"
            boolean r1 = r1.equals(r2)     // Catch:{ all -> 0x0664 }
            if (r1 == 0) goto L_0x061a
            r1 = 92
            goto L_0x061b
        L_0x0476:
            java.lang.String r2 = "GIONEE_GBL7360"
            boolean r1 = r1.equals(r2)     // Catch:{ all -> 0x0664 }
            if (r1 == 0) goto L_0x061a
            r1 = 42
            goto L_0x061b
        L_0x0482:
            java.lang.String r2 = "GiONEE_CBL7513"
            boolean r1 = r1.equals(r2)     // Catch:{ all -> 0x0664 }
            if (r1 == 0) goto L_0x061a
            r1 = 40
            goto L_0x061b
        L_0x048e:
            java.lang.String r2 = "OnePlus5T"
            boolean r1 = r1.equals(r2)     // Catch:{ all -> 0x0664 }
            if (r1 == 0) goto L_0x061a
            r1 = 79
            goto L_0x061b
        L_0x049a:
            java.lang.String r2 = "whyred"
            boolean r1 = r1.equals(r2)     // Catch:{ all -> 0x0664 }
            if (r1 == 0) goto L_0x061a
            r1 = 118(0x76, float:1.65E-43)
            goto L_0x061b
        L_0x04a6:
            java.lang.String r2 = "watson"
            boolean r1 = r1.equals(r2)     // Catch:{ all -> 0x0664 }
            if (r1 == 0) goto L_0x061a
            r1 = 117(0x75, float:1.64E-43)
            goto L_0x061b
        L_0x04b2:
            java.lang.String r2 = "SVP-DTV15"
            boolean r1 = r1.equals(r2)     // Catch:{ all -> 0x0664 }
            if (r1 == 0) goto L_0x061a
            r1 = 105(0x69, float:1.47E-43)
            goto L_0x061b
        L_0x04be:
            java.lang.String r2 = "A7000-a"
            boolean r1 = r1.equals(r2)     // Catch:{ all -> 0x0664 }
            if (r1 == 0) goto L_0x061a
            r1 = 7
            goto L_0x061b
        L_0x04c9:
            java.lang.String r2 = "nicklaus_f"
            boolean r1 = r1.equals(r2)     // Catch:{ all -> 0x0664 }
            if (r1 == 0) goto L_0x061a
            r1 = 76
            goto L_0x061b
        L_0x04d5:
            java.lang.String r2 = "tcl_eu"
            boolean r1 = r1.equals(r2)     // Catch:{ all -> 0x0664 }
            if (r1 == 0) goto L_0x061a
            r1 = 112(0x70, float:1.57E-43)
            goto L_0x061b
        L_0x04e1:
            java.lang.String r2 = "ELUGA_Ray_X"
            boolean r1 = r1.equals(r2)     // Catch:{ all -> 0x0664 }
            if (r1 == 0) goto L_0x061a
            r1 = 29
            goto L_0x061b
        L_0x04ed:
            java.lang.String r2 = "s905x018"
            boolean r1 = r1.equals(r2)     // Catch:{ all -> 0x0664 }
            if (r1 == 0) goto L_0x061a
            r1 = 106(0x6a, float:1.49E-43)
            goto L_0x061b
        L_0x04f9:
            java.lang.String r2 = "A10-70L"
            boolean r1 = r1.equals(r2)     // Catch:{ all -> 0x0664 }
            if (r1 == 0) goto L_0x061a
            r1 = 4
            goto L_0x061b
        L_0x0504:
            java.lang.String r2 = "A10-70F"
            boolean r1 = r1.equals(r2)     // Catch:{ all -> 0x0664 }
            if (r1 == 0) goto L_0x061a
            r1 = 3
            goto L_0x061b
        L_0x050f:
            java.lang.String r2 = "namath"
            boolean r1 = r1.equals(r2)     // Catch:{ all -> 0x0664 }
            if (r1 == 0) goto L_0x061a
            r1 = 75
            goto L_0x061b
        L_0x051b:
            java.lang.String r2 = "Slate_Pro"
            boolean r1 = r1.equals(r2)     // Catch:{ all -> 0x0664 }
            if (r1 == 0) goto L_0x061a
            r1 = 104(0x68, float:1.46E-43)
            goto L_0x061b
        L_0x0527:
            java.lang.String r2 = "iris60"
            boolean r1 = r1.equals(r2)     // Catch:{ all -> 0x0664 }
            if (r1 == 0) goto L_0x061a
            r1 = 59
            goto L_0x061b
        L_0x0533:
            java.lang.String r2 = "BRAVIA_ATV2"
            boolean r1 = r1.equals(r2)     // Catch:{ all -> 0x0664 }
            if (r1 == 0) goto L_0x061a
            r1 = 15
            goto L_0x061b
        L_0x053f:
            java.lang.String r2 = "GiONEE_GBL7319"
            boolean r1 = r1.equals(r2)     // Catch:{ all -> 0x0664 }
            if (r1 == 0) goto L_0x061a
            r1 = 41
            goto L_0x061b
        L_0x054b:
            java.lang.String r2 = "panell_dt"
            boolean r1 = r1.equals(r2)     // Catch:{ all -> 0x0664 }
            if (r1 == 0) goto L_0x061a
            r1 = 86
            goto L_0x061b
        L_0x0557:
            java.lang.String r2 = "panell_ds"
            boolean r1 = r1.equals(r2)     // Catch:{ all -> 0x0664 }
            if (r1 == 0) goto L_0x061a
            r1 = 85
            goto L_0x061b
        L_0x0563:
            java.lang.String r2 = "panell_dl"
            boolean r1 = r1.equals(r2)     // Catch:{ all -> 0x0664 }
            if (r1 == 0) goto L_0x061a
            r1 = 84
            goto L_0x061b
        L_0x056f:
            java.lang.String r2 = "vernee_M5"
            boolean r1 = r1.equals(r2)     // Catch:{ all -> 0x0664 }
            if (r1 == 0) goto L_0x061a
            r1 = 116(0x74, float:1.63E-43)
            goto L_0x061b
        L_0x057b:
            java.lang.String r2 = "Phantom6"
            boolean r1 = r1.equals(r2)     // Catch:{ all -> 0x0664 }
            if (r1 == 0) goto L_0x061a
            r1 = 91
            goto L_0x061b
        L_0x0587:
            java.lang.String r2 = "ComioS1"
            boolean r1 = r1.equals(r2)     // Catch:{ all -> 0x0664 }
            if (r1 == 0) goto L_0x061a
            r1 = 18
            goto L_0x061b
        L_0x0593:
            java.lang.String r2 = "XT1663"
            boolean r1 = r1.equals(r2)     // Catch:{ all -> 0x0664 }
            if (r1 == 0) goto L_0x061a
            r1 = 123(0x7b, float:1.72E-43)
            goto L_0x061b
        L_0x059f:
            java.lang.String r2 = "AquaPowerM"
            boolean r1 = r1.equals(r2)     // Catch:{ all -> 0x0664 }
            if (r1 == 0) goto L_0x061a
            r1 = 11
            goto L_0x061b
        L_0x05ab:
            java.lang.String r2 = "PGN611"
            boolean r1 = r1.equals(r2)     // Catch:{ all -> 0x0664 }
            if (r1 == 0) goto L_0x061a
            r1 = 90
            goto L_0x061b
        L_0x05b7:
            java.lang.String r2 = "PGN610"
            boolean r1 = r1.equals(r2)     // Catch:{ all -> 0x0664 }
            if (r1 == 0) goto L_0x061a
            r1 = 89
            goto L_0x061b
        L_0x05c2:
            java.lang.String r2 = "PGN528"
            boolean r1 = r1.equals(r2)     // Catch:{ all -> 0x0664 }
            if (r1 == 0) goto L_0x061a
            r1 = 88
            goto L_0x061b
        L_0x05cd:
            java.lang.String r2 = "NX573J"
            boolean r1 = r1.equals(r2)     // Catch:{ all -> 0x0664 }
            if (r1 == 0) goto L_0x061a
            r1 = 78
            goto L_0x061b
        L_0x05d8:
            java.lang.String r2 = "NX541J"
            boolean r1 = r1.equals(r2)     // Catch:{ all -> 0x0664 }
            if (r1 == 0) goto L_0x061a
            r1 = 77
            goto L_0x061b
        L_0x05e3:
            java.lang.String r2 = "CP8676_I02"
            boolean r1 = r1.equals(r2)     // Catch:{ all -> 0x0664 }
            if (r1 == 0) goto L_0x061a
            r1 = 19
            goto L_0x061b
        L_0x05ee:
            java.lang.String r2 = "K50a40"
            boolean r1 = r1.equals(r2)     // Catch:{ all -> 0x0664 }
            if (r1 == 0) goto L_0x061a
            r1 = 63
            goto L_0x061b
        L_0x05f9:
            java.lang.String r2 = "GIONEE_SWW1631"
            boolean r1 = r1.equals(r2)     // Catch:{ all -> 0x0664 }
            if (r1 == 0) goto L_0x061a
            r1 = 45
            goto L_0x061b
        L_0x0604:
            java.lang.String r2 = "GIONEE_SWW1627"
            boolean r1 = r1.equals(r2)     // Catch:{ all -> 0x0664 }
            if (r1 == 0) goto L_0x061a
            r1 = 44
            goto L_0x061b
        L_0x060f:
            java.lang.String r2 = "GIONEE_SWW1609"
            boolean r1 = r1.equals(r2)     // Catch:{ all -> 0x0664 }
            if (r1 == 0) goto L_0x061a
            r1 = 43
            goto L_0x061b
        L_0x061a:
            r1 = -1
        L_0x061b:
            switch(r1) {
                case 0: goto L_0x061f;
                case 1: goto L_0x061f;
                case 2: goto L_0x061f;
                case 3: goto L_0x061f;
                case 4: goto L_0x061f;
                case 5: goto L_0x061f;
                case 6: goto L_0x061f;
                case 7: goto L_0x061f;
                case 8: goto L_0x061f;
                case 9: goto L_0x061f;
                case 10: goto L_0x061f;
                case 11: goto L_0x061f;
                case 12: goto L_0x061f;
                case 13: goto L_0x061f;
                case 14: goto L_0x061f;
                case 15: goto L_0x061f;
                case 16: goto L_0x061f;
                case 17: goto L_0x061f;
                case 18: goto L_0x061f;
                case 19: goto L_0x061f;
                case 20: goto L_0x061f;
                case 21: goto L_0x061f;
                case 22: goto L_0x061f;
                case 23: goto L_0x061f;
                case 24: goto L_0x061f;
                case 25: goto L_0x061f;
                case 26: goto L_0x061f;
                case 27: goto L_0x061f;
                case 28: goto L_0x061f;
                case 29: goto L_0x061f;
                case 30: goto L_0x061f;
                case 31: goto L_0x061f;
                case 32: goto L_0x061f;
                case 33: goto L_0x061f;
                case 34: goto L_0x061f;
                case 35: goto L_0x061f;
                case 36: goto L_0x061f;
                case 37: goto L_0x061f;
                case 38: goto L_0x061f;
                case 39: goto L_0x061f;
                case 40: goto L_0x061f;
                case 41: goto L_0x061f;
                case 42: goto L_0x061f;
                case 43: goto L_0x061f;
                case 44: goto L_0x061f;
                case 45: goto L_0x061f;
                case 46: goto L_0x061f;
                case 47: goto L_0x061f;
                case 48: goto L_0x061f;
                case 49: goto L_0x061f;
                case 50: goto L_0x061f;
                case 51: goto L_0x061f;
                case 52: goto L_0x061f;
                case 53: goto L_0x061f;
                case 54: goto L_0x061f;
                case 55: goto L_0x061f;
                case 56: goto L_0x061f;
                case 57: goto L_0x061f;
                case 58: goto L_0x061f;
                case 59: goto L_0x061f;
                case 60: goto L_0x061f;
                case 61: goto L_0x061f;
                case 62: goto L_0x061f;
                case 63: goto L_0x061f;
                case 64: goto L_0x061f;
                case 65: goto L_0x061f;
                case 66: goto L_0x061f;
                case 67: goto L_0x061f;
                case 68: goto L_0x061f;
                case 69: goto L_0x061f;
                case 70: goto L_0x061f;
                case 71: goto L_0x061f;
                case 72: goto L_0x061f;
                case 73: goto L_0x061f;
                case 74: goto L_0x061f;
                case 75: goto L_0x061f;
                case 76: goto L_0x061f;
                case 77: goto L_0x061f;
                case 78: goto L_0x061f;
                case 79: goto L_0x061f;
                case 80: goto L_0x061f;
                case 81: goto L_0x061f;
                case 82: goto L_0x061f;
                case 83: goto L_0x061f;
                case 84: goto L_0x061f;
                case 85: goto L_0x061f;
                case 86: goto L_0x061f;
                case 87: goto L_0x061f;
                case 88: goto L_0x061f;
                case 89: goto L_0x061f;
                case 90: goto L_0x061f;
                case 91: goto L_0x061f;
                case 92: goto L_0x061f;
                case 93: goto L_0x061f;
                case 94: goto L_0x061f;
                case 95: goto L_0x061f;
                case 96: goto L_0x061f;
                case 97: goto L_0x061f;
                case 98: goto L_0x061f;
                case 99: goto L_0x061f;
                case 100: goto L_0x061f;
                case 101: goto L_0x061f;
                case 102: goto L_0x061f;
                case 103: goto L_0x061f;
                case 104: goto L_0x061f;
                case 105: goto L_0x061f;
                case 106: goto L_0x061f;
                case 107: goto L_0x061f;
                case 108: goto L_0x061f;
                case 109: goto L_0x061f;
                case 110: goto L_0x061f;
                case 111: goto L_0x061f;
                case 112: goto L_0x061f;
                case 113: goto L_0x061f;
                case 114: goto L_0x061f;
                case 115: goto L_0x061f;
                case 116: goto L_0x061f;
                case 117: goto L_0x061f;
                case 118: goto L_0x061f;
                case 119: goto L_0x061f;
                case 120: goto L_0x061f;
                case 121: goto L_0x061f;
                case 122: goto L_0x061f;
                case 123: goto L_0x061f;
                case 124: goto L_0x061f;
                case 125: goto L_0x061f;
                default: goto L_0x061e;
            }     // Catch:{ all -> 0x0664 }
        L_0x061e:
            goto L_0x0621
        L_0x061f:
            deviceNeedsSetOutputSurfaceWorkaround = r3     // Catch:{ all -> 0x0664 }
        L_0x0621:
            java.lang.String r1 = com.google.android.exoplayer2.util.Util.MODEL     // Catch:{ all -> 0x0664 }
            int r2 = r1.hashCode()     // Catch:{ all -> 0x0664 }
            r4 = -594534941(0xffffffffdc901de3, float:-3.2452206E17)
            if (r2 == r4) goto L_0x064a
            r4 = 2006354(0x1e9d52, float:2.811501E-39)
            if (r2 == r4) goto L_0x0641
            r0 = 2006367(0x1e9d5f, float:2.811519E-39)
            if (r2 == r0) goto L_0x0637
            goto L_0x0654
        L_0x0637:
            java.lang.String r0 = "AFTN"
            boolean r0 = r1.equals(r0)     // Catch:{ all -> 0x0664 }
            if (r0 == 0) goto L_0x0654
            r0 = 1
            goto L_0x0655
        L_0x0641:
            java.lang.String r2 = "AFTA"
            boolean r1 = r1.equals(r2)     // Catch:{ all -> 0x0664 }
            if (r1 == 0) goto L_0x0654
            goto L_0x0655
        L_0x064a:
            java.lang.String r0 = "JSN-L21"
            boolean r0 = r1.equals(r0)     // Catch:{ all -> 0x0664 }
            if (r0 == 0) goto L_0x0654
            r0 = 2
            goto L_0x0655
        L_0x0654:
            r0 = -1
        L_0x0655:
            if (r0 == 0) goto L_0x065c
            if (r0 == r3) goto L_0x065c
            if (r0 == r6) goto L_0x065c
            goto L_0x065e
        L_0x065c:
            deviceNeedsSetOutputSurfaceWorkaround = r3     // Catch:{ all -> 0x0664 }
        L_0x065e:
            evaluatedDeviceNeedsSetOutputSurfaceWorkaround = r3     // Catch:{ all -> 0x0664 }
        L_0x0660:
            monitor-exit(r8)     // Catch:{ all -> 0x0664 }
            boolean r8 = deviceNeedsSetOutputSurfaceWorkaround
            return r8
        L_0x0664:
            r0 = move-exception
            monitor-exit(r8)     // Catch:{ all -> 0x0664 }
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.exoplayer2.video.MediaCodecVideoRenderer.codecNeedsSetOutputSurfaceWorkaround(java.lang.String):boolean");
    }

    /* access modifiers changed from: protected */
    public Surface getSurface() {
        return this.surface;
    }
}
