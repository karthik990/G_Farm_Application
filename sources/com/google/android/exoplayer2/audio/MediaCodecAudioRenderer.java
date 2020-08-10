package com.google.android.exoplayer2.audio;

import android.content.Context;
import android.media.MediaCodec;
import android.media.MediaCrypto;
import android.media.MediaFormat;
import android.os.Handler;
import com.google.android.exoplayer2.C1996C;
import com.google.android.exoplayer2.ExoPlaybackException;
import com.google.android.exoplayer2.Format;
import com.google.android.exoplayer2.FormatHolder;
import com.google.android.exoplayer2.PlaybackParameters;
import com.google.android.exoplayer2.RendererCapabilities.CC;
import com.google.android.exoplayer2.audio.AudioRendererEventListener.EventDispatcher;
import com.google.android.exoplayer2.audio.AudioSink.ConfigurationException;
import com.google.android.exoplayer2.audio.AudioSink.Listener;
import com.google.android.exoplayer2.audio.AudioSink.WriteException;
import com.google.android.exoplayer2.decoder.DecoderInputBuffer;
import com.google.android.exoplayer2.drm.DrmSessionManager;
import com.google.android.exoplayer2.drm.FrameworkMediaCrypto;
import com.google.android.exoplayer2.mediacodec.MediaCodecInfo;
import com.google.android.exoplayer2.mediacodec.MediaCodecRenderer;
import com.google.android.exoplayer2.mediacodec.MediaCodecSelector;
import com.google.android.exoplayer2.mediacodec.MediaCodecUtil;
import com.google.android.exoplayer2.mediacodec.MediaCodecUtil.DecoderQueryException;
import com.google.android.exoplayer2.mediacodec.MediaFormatUtil;
import com.google.android.exoplayer2.util.Log;
import com.google.android.exoplayer2.util.MediaClock;
import com.google.android.exoplayer2.util.MimeTypes;
import com.google.android.exoplayer2.util.Util;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.apache.commons.logging.LogFactory;

public class MediaCodecAudioRenderer extends MediaCodecRenderer implements MediaClock {
    private static final int MAX_PENDING_STREAM_CHANGE_COUNT = 10;
    private static final String TAG = "MediaCodecAudioRenderer";
    private boolean allowFirstBufferPositionDiscontinuity;
    /* access modifiers changed from: private */
    public boolean allowPositionDiscontinuity;
    private final AudioSink audioSink;
    private int codecMaxInputSize;
    private boolean codecNeedsDiscardChannelsWorkaround;
    private boolean codecNeedsEosBufferTimestampWorkaround;
    private final Context context;
    private long currentPositionUs;
    /* access modifiers changed from: private */
    public final EventDispatcher eventDispatcher;
    private Format inputFormat;
    private long lastInputTimeUs;
    private boolean passthroughEnabled;
    private MediaFormat passthroughMediaFormat;
    private int pendingStreamChangeCount;
    private final long[] pendingStreamChangeTimesUs;

    private final class AudioSinkListener implements Listener {
        private AudioSinkListener() {
        }

        public void onAudioSessionId(int i) {
            MediaCodecAudioRenderer.this.eventDispatcher.audioSessionId(i);
            MediaCodecAudioRenderer.this.onAudioSessionId(i);
        }

        public void onPositionDiscontinuity() {
            MediaCodecAudioRenderer.this.onAudioTrackPositionDiscontinuity();
            MediaCodecAudioRenderer.this.allowPositionDiscontinuity = true;
        }

        public void onUnderrun(int i, long j, long j2) {
            MediaCodecAudioRenderer.this.eventDispatcher.audioTrackUnderrun(i, j, j2);
            MediaCodecAudioRenderer.this.onAudioTrackUnderrun(i, j, j2);
        }
    }

    public MediaClock getMediaClock() {
        return this;
    }

    /* access modifiers changed from: protected */
    public void onAudioSessionId(int i) {
    }

    /* access modifiers changed from: protected */
    public void onAudioTrackPositionDiscontinuity() {
    }

    /* access modifiers changed from: protected */
    public void onAudioTrackUnderrun(int i, long j, long j2) {
    }

    public MediaCodecAudioRenderer(Context context2, MediaCodecSelector mediaCodecSelector) {
        this(context2, mediaCodecSelector, null, false);
    }

    @Deprecated
    public MediaCodecAudioRenderer(Context context2, MediaCodecSelector mediaCodecSelector, DrmSessionManager<FrameworkMediaCrypto> drmSessionManager, boolean z) {
        this(context2, mediaCodecSelector, drmSessionManager, z, (Handler) null, (AudioRendererEventListener) null);
    }

    public MediaCodecAudioRenderer(Context context2, MediaCodecSelector mediaCodecSelector, Handler handler, AudioRendererEventListener audioRendererEventListener) {
        this(context2, mediaCodecSelector, null, false, handler, audioRendererEventListener);
    }

    @Deprecated
    public MediaCodecAudioRenderer(Context context2, MediaCodecSelector mediaCodecSelector, DrmSessionManager<FrameworkMediaCrypto> drmSessionManager, boolean z, Handler handler, AudioRendererEventListener audioRendererEventListener) {
        this(context2, mediaCodecSelector, drmSessionManager, z, handler, audioRendererEventListener, (AudioCapabilities) null, new AudioProcessor[0]);
    }

    @Deprecated
    public MediaCodecAudioRenderer(Context context2, MediaCodecSelector mediaCodecSelector, DrmSessionManager<FrameworkMediaCrypto> drmSessionManager, boolean z, Handler handler, AudioRendererEventListener audioRendererEventListener, AudioCapabilities audioCapabilities, AudioProcessor... audioProcessorArr) {
        AudioCapabilities audioCapabilities2 = audioCapabilities;
        this(context2, mediaCodecSelector, drmSessionManager, z, handler, audioRendererEventListener, new DefaultAudioSink(audioCapabilities, audioProcessorArr));
    }

    @Deprecated
    public MediaCodecAudioRenderer(Context context2, MediaCodecSelector mediaCodecSelector, DrmSessionManager<FrameworkMediaCrypto> drmSessionManager, boolean z, Handler handler, AudioRendererEventListener audioRendererEventListener, AudioSink audioSink2) {
        this(context2, mediaCodecSelector, drmSessionManager, z, false, handler, audioRendererEventListener, audioSink2);
    }

    public MediaCodecAudioRenderer(Context context2, MediaCodecSelector mediaCodecSelector, boolean z, Handler handler, AudioRendererEventListener audioRendererEventListener, AudioSink audioSink2) {
        this(context2, mediaCodecSelector, null, false, z, handler, audioRendererEventListener, audioSink2);
    }

    @Deprecated
    public MediaCodecAudioRenderer(Context context2, MediaCodecSelector mediaCodecSelector, DrmSessionManager<FrameworkMediaCrypto> drmSessionManager, boolean z, boolean z2, Handler handler, AudioRendererEventListener audioRendererEventListener, AudioSink audioSink2) {
        super(1, mediaCodecSelector, drmSessionManager, z, z2, 44100.0f);
        this.context = context2.getApplicationContext();
        this.audioSink = audioSink2;
        this.lastInputTimeUs = C1996C.TIME_UNSET;
        this.pendingStreamChangeTimesUs = new long[10];
        this.eventDispatcher = new EventDispatcher(handler, audioRendererEventListener);
        audioSink2.setListener(new AudioSinkListener());
    }

    /* access modifiers changed from: protected */
    public int supportsFormat(MediaCodecSelector mediaCodecSelector, DrmSessionManager<FrameworkMediaCrypto> drmSessionManager, Format format) throws DecoderQueryException {
        String str = format.sampleMimeType;
        if (!MimeTypes.isAudio(str)) {
            return CC.create(0);
        }
        int i = Util.SDK_INT >= 21 ? 32 : 0;
        boolean z = format.drmInitData == null || FrameworkMediaCrypto.class.equals(format.exoMediaCryptoType) || (format.exoMediaCryptoType == null && supportsFormatDrm(drmSessionManager, format.drmInitData));
        int i2 = 8;
        int i3 = 4;
        if (z && allowPassthrough(format.channelCount, str) && mediaCodecSelector.getPassthroughDecoderInfo() != null) {
            return CC.create(4, 8, i);
        }
        if ((MimeTypes.AUDIO_RAW.equals(str) && !this.audioSink.supportsOutput(format.channelCount, format.pcmEncoding)) || !this.audioSink.supportsOutput(format.channelCount, 2)) {
            return CC.create(1);
        }
        List decoderInfos = getDecoderInfos(mediaCodecSelector, format, false);
        if (decoderInfos.isEmpty()) {
            return CC.create(1);
        }
        if (!z) {
            return CC.create(2);
        }
        MediaCodecInfo mediaCodecInfo = (MediaCodecInfo) decoderInfos.get(0);
        boolean isFormatSupported = mediaCodecInfo.isFormatSupported(format);
        if (isFormatSupported && mediaCodecInfo.isSeamlessAdaptationSupported(format)) {
            i2 = 16;
        }
        if (!isFormatSupported) {
            i3 = 3;
        }
        return CC.create(i3, i2, i);
    }

    /* access modifiers changed from: protected */
    public List<MediaCodecInfo> getDecoderInfos(MediaCodecSelector mediaCodecSelector, Format format, boolean z) throws DecoderQueryException {
        String str = format.sampleMimeType;
        if (str == null) {
            return Collections.emptyList();
        }
        if (allowPassthrough(format.channelCount, str)) {
            MediaCodecInfo passthroughDecoderInfo = mediaCodecSelector.getPassthroughDecoderInfo();
            if (passthroughDecoderInfo != null) {
                return Collections.singletonList(passthroughDecoderInfo);
            }
        }
        List decoderInfosSortedByFormatSupport = MediaCodecUtil.getDecoderInfosSortedByFormatSupport(mediaCodecSelector.getDecoderInfos(str, z, false), format);
        if (MimeTypes.AUDIO_E_AC3_JOC.equals(str)) {
            List arrayList = new ArrayList(decoderInfosSortedByFormatSupport);
            arrayList.addAll(mediaCodecSelector.getDecoderInfos(MimeTypes.AUDIO_E_AC3, z, false));
            decoderInfosSortedByFormatSupport = arrayList;
        }
        return Collections.unmodifiableList(decoderInfosSortedByFormatSupport);
    }

    /* access modifiers changed from: protected */
    public boolean allowPassthrough(int i, String str) {
        return getPassthroughEncoding(i, str) != 0;
    }

    /* access modifiers changed from: protected */
    public void configureCodec(MediaCodecInfo mediaCodecInfo, MediaCodec mediaCodec, Format format, MediaCrypto mediaCrypto, float f) {
        this.codecMaxInputSize = getCodecMaxInputSize(mediaCodecInfo, format, getStreamFormats());
        this.codecNeedsDiscardChannelsWorkaround = codecNeedsDiscardChannelsWorkaround(mediaCodecInfo.name);
        this.codecNeedsEosBufferTimestampWorkaround = codecNeedsEosBufferTimestampWorkaround(mediaCodecInfo.name);
        this.passthroughEnabled = mediaCodecInfo.passthrough;
        MediaFormat mediaFormat = getMediaFormat(format, this.passthroughEnabled ? MimeTypes.AUDIO_RAW : mediaCodecInfo.codecMimeType, this.codecMaxInputSize, f);
        mediaCodec.configure(mediaFormat, null, mediaCrypto, 0);
        if (this.passthroughEnabled) {
            this.passthroughMediaFormat = mediaFormat;
            this.passthroughMediaFormat.setString("mime", format.sampleMimeType);
            return;
        }
        this.passthroughMediaFormat = null;
    }

    /* access modifiers changed from: protected */
    public int canKeepCodec(MediaCodec mediaCodec, MediaCodecInfo mediaCodecInfo, Format format, Format format2) {
        if (getCodecMaxInputSize(mediaCodecInfo, format2) <= this.codecMaxInputSize && format.encoderDelay == 0 && format.encoderPadding == 0 && format2.encoderDelay == 0 && format2.encoderPadding == 0) {
            if (mediaCodecInfo.isSeamlessAdaptationSupported(format, format2, true)) {
                return 3;
            }
            if (canKeepCodecWithFlush(format, format2)) {
                return 1;
            }
        }
        return 0;
    }

    /* access modifiers changed from: protected */
    public boolean canKeepCodecWithFlush(Format format, Format format2) {
        if (Util.areEqual(format.sampleMimeType, format2.sampleMimeType) && format.channelCount == format2.channelCount && format.sampleRate == format2.sampleRate && format.pcmEncoding == format2.pcmEncoding && format.initializationDataEquals(format2)) {
            if (!MimeTypes.AUDIO_OPUS.equals(format.sampleMimeType)) {
                return true;
            }
        }
        return false;
    }

    /* access modifiers changed from: protected */
    public float getCodecOperatingRateV23(float f, Format format, Format[] formatArr) {
        int i = -1;
        for (Format format2 : formatArr) {
            int i2 = format2.sampleRate;
            if (i2 != -1) {
                i = Math.max(i, i2);
            }
        }
        if (i == -1) {
            return -1.0f;
        }
        return f * ((float) i);
    }

    /* access modifiers changed from: protected */
    public void onCodecInitialized(String str, long j, long j2) {
        this.eventDispatcher.decoderInitialized(str, j, j2);
    }

    /* access modifiers changed from: protected */
    public void onInputFormatChanged(FormatHolder formatHolder) throws ExoPlaybackException {
        super.onInputFormatChanged(formatHolder);
        this.inputFormat = formatHolder.format;
        this.eventDispatcher.inputFormatChanged(this.inputFormat);
    }

    /* access modifiers changed from: protected */
    public void onOutputFormatChanged(MediaCodec mediaCodec, MediaFormat mediaFormat) throws ExoPlaybackException {
        int i;
        int[] iArr;
        MediaFormat mediaFormat2 = this.passthroughMediaFormat;
        String str = "channel-count";
        if (mediaFormat2 != null) {
            i = getPassthroughEncoding(mediaFormat2.getInteger(str), mediaFormat2.getString("mime"));
        } else {
            i = getPcmEncoding(this.inputFormat);
            mediaFormat2 = mediaFormat;
        }
        int integer = mediaFormat2.getInteger(str);
        int integer2 = mediaFormat2.getInteger("sample-rate");
        if (!this.codecNeedsDiscardChannelsWorkaround || integer != 6 || this.inputFormat.channelCount >= 6) {
            iArr = null;
        } else {
            iArr = new int[this.inputFormat.channelCount];
            for (int i2 = 0; i2 < this.inputFormat.channelCount; i2++) {
                iArr[i2] = i2;
            }
        }
        try {
            this.audioSink.configure(i, integer, integer2, 0, iArr, this.inputFormat.encoderDelay, this.inputFormat.encoderPadding);
        } catch (ConfigurationException e) {
            throw createRendererException(e, this.inputFormat);
        }
    }

    /* access modifiers changed from: protected */
    public int getPassthroughEncoding(int i, String str) {
        String str2 = MimeTypes.AUDIO_E_AC3_JOC;
        if (str2.equals(str)) {
            if (this.audioSink.supportsOutput(-1, 18)) {
                return MimeTypes.getEncoding(str2);
            }
            str = MimeTypes.AUDIO_E_AC3;
        }
        int encoding = MimeTypes.getEncoding(str);
        if (this.audioSink.supportsOutput(i, encoding)) {
            return encoding;
        }
        return 0;
    }

    /* access modifiers changed from: protected */
    public void onEnabled(boolean z) throws ExoPlaybackException {
        super.onEnabled(z);
        this.eventDispatcher.enabled(this.decoderCounters);
        int i = getConfiguration().tunnelingAudioSessionId;
        if (i != 0) {
            this.audioSink.enableTunnelingV21(i);
        } else {
            this.audioSink.disableTunneling();
        }
    }

    /* access modifiers changed from: protected */
    public void onStreamChanged(Format[] formatArr, long j) throws ExoPlaybackException {
        super.onStreamChanged(formatArr, j);
        if (this.lastInputTimeUs != C1996C.TIME_UNSET) {
            int i = this.pendingStreamChangeCount;
            if (i == this.pendingStreamChangeTimesUs.length) {
                StringBuilder sb = new StringBuilder();
                sb.append("Too many stream changes, so dropping change at ");
                sb.append(this.pendingStreamChangeTimesUs[this.pendingStreamChangeCount - 1]);
                Log.m1396w(TAG, sb.toString());
            } else {
                this.pendingStreamChangeCount = i + 1;
            }
            this.pendingStreamChangeTimesUs[this.pendingStreamChangeCount - 1] = this.lastInputTimeUs;
        }
    }

    /* access modifiers changed from: protected */
    public void onPositionReset(long j, boolean z) throws ExoPlaybackException {
        super.onPositionReset(j, z);
        this.audioSink.flush();
        this.currentPositionUs = j;
        this.allowFirstBufferPositionDiscontinuity = true;
        this.allowPositionDiscontinuity = true;
        this.lastInputTimeUs = C1996C.TIME_UNSET;
        this.pendingStreamChangeCount = 0;
    }

    /* access modifiers changed from: protected */
    public void onStarted() {
        super.onStarted();
        this.audioSink.play();
    }

    /* access modifiers changed from: protected */
    public void onStopped() {
        updateCurrentPosition();
        this.audioSink.pause();
        super.onStopped();
    }

    /* access modifiers changed from: protected */
    public void onDisabled() {
        try {
            this.lastInputTimeUs = C1996C.TIME_UNSET;
            this.pendingStreamChangeCount = 0;
            this.audioSink.flush();
            try {
                super.onDisabled();
            } finally {
                this.eventDispatcher.disabled(this.decoderCounters);
            }
        } catch (Throwable th) {
            super.onDisabled();
            throw th;
        } finally {
            this.eventDispatcher.disabled(this.decoderCounters);
        }
    }

    /* access modifiers changed from: protected */
    public void onReset() {
        try {
            super.onReset();
        } finally {
            this.audioSink.reset();
        }
    }

    public boolean isEnded() {
        return super.isEnded() && this.audioSink.isEnded();
    }

    public boolean isReady() {
        return this.audioSink.hasPendingData() || super.isReady();
    }

    public long getPositionUs() {
        if (getState() == 2) {
            updateCurrentPosition();
        }
        return this.currentPositionUs;
    }

    public void setPlaybackParameters(PlaybackParameters playbackParameters) {
        this.audioSink.setPlaybackParameters(playbackParameters);
    }

    public PlaybackParameters getPlaybackParameters() {
        return this.audioSink.getPlaybackParameters();
    }

    /* access modifiers changed from: protected */
    public void onQueueInputBuffer(DecoderInputBuffer decoderInputBuffer) {
        if (this.allowFirstBufferPositionDiscontinuity && !decoderInputBuffer.isDecodeOnly()) {
            if (Math.abs(decoderInputBuffer.timeUs - this.currentPositionUs) > 500000) {
                this.currentPositionUs = decoderInputBuffer.timeUs;
            }
            this.allowFirstBufferPositionDiscontinuity = false;
        }
        this.lastInputTimeUs = Math.max(decoderInputBuffer.timeUs, this.lastInputTimeUs);
    }

    /* access modifiers changed from: protected */
    public void onProcessedOutputBuffer(long j) {
        while (this.pendingStreamChangeCount != 0 && j >= this.pendingStreamChangeTimesUs[0]) {
            this.audioSink.handleDiscontinuity();
            this.pendingStreamChangeCount--;
            long[] jArr = this.pendingStreamChangeTimesUs;
            System.arraycopy(jArr, 1, jArr, 0, this.pendingStreamChangeCount);
        }
    }

    /* access modifiers changed from: protected */
    /* JADX WARNING: Code restructure failed: missing block: B:7:0x0017, code lost:
        if (r1 != com.google.android.exoplayer2.C1996C.TIME_UNSET) goto L_0x001b;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public boolean processOutputBuffer(long r1, long r3, android.media.MediaCodec r5, java.nio.ByteBuffer r6, int r7, int r8, long r9, boolean r11, boolean r12, com.google.android.exoplayer2.Format r13) throws com.google.android.exoplayer2.ExoPlaybackException {
        /*
            r0 = this;
            boolean r1 = r0.codecNeedsEosBufferTimestampWorkaround
            if (r1 == 0) goto L_0x001a
            r1 = 0
            int r3 = (r9 > r1 ? 1 : (r9 == r1 ? 0 : -1))
            if (r3 != 0) goto L_0x001a
            r1 = r8 & 4
            if (r1 == 0) goto L_0x001a
            long r1 = r0.lastInputTimeUs
            r3 = -9223372036854775807(0x8000000000000001, double:-4.9E-324)
            int r12 = (r1 > r3 ? 1 : (r1 == r3 ? 0 : -1))
            if (r12 == 0) goto L_0x001a
            goto L_0x001b
        L_0x001a:
            r1 = r9
        L_0x001b:
            boolean r3 = r0.passthroughEnabled
            r4 = 0
            r9 = 1
            if (r3 == 0) goto L_0x0029
            r3 = r8 & 2
            if (r3 == 0) goto L_0x0029
            r5.releaseOutputBuffer(r7, r4)
            return r9
        L_0x0029:
            if (r11 == 0) goto L_0x003b
            r5.releaseOutputBuffer(r7, r4)
            com.google.android.exoplayer2.decoder.DecoderCounters r1 = r0.decoderCounters
            int r2 = r1.skippedOutputBufferCount
            int r2 = r2 + r9
            r1.skippedOutputBufferCount = r2
            com.google.android.exoplayer2.audio.AudioSink r1 = r0.audioSink
            r1.handleDiscontinuity()
            return r9
        L_0x003b:
            com.google.android.exoplayer2.audio.AudioSink r3 = r0.audioSink     // Catch:{ InitializationException -> 0x0051, WriteException -> 0x004f }
            boolean r1 = r3.handleBuffer(r6, r1)     // Catch:{ InitializationException -> 0x0051, WriteException -> 0x004f }
            if (r1 == 0) goto L_0x004e
            r5.releaseOutputBuffer(r7, r4)     // Catch:{ InitializationException -> 0x0051, WriteException -> 0x004f }
            com.google.android.exoplayer2.decoder.DecoderCounters r1 = r0.decoderCounters     // Catch:{ InitializationException -> 0x0051, WriteException -> 0x004f }
            int r2 = r1.renderedOutputBufferCount     // Catch:{ InitializationException -> 0x0051, WriteException -> 0x004f }
            int r2 = r2 + r9
            r1.renderedOutputBufferCount = r2     // Catch:{ InitializationException -> 0x0051, WriteException -> 0x004f }
            return r9
        L_0x004e:
            return r4
        L_0x004f:
            r1 = move-exception
            goto L_0x0052
        L_0x0051:
            r1 = move-exception
        L_0x0052:
            com.google.android.exoplayer2.Format r2 = r0.inputFormat
            com.google.android.exoplayer2.ExoPlaybackException r1 = r0.createRendererException(r1, r2)
            throw r1
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.exoplayer2.audio.MediaCodecAudioRenderer.processOutputBuffer(long, long, android.media.MediaCodec, java.nio.ByteBuffer, int, int, long, boolean, boolean, com.google.android.exoplayer2.Format):boolean");
    }

    /* access modifiers changed from: protected */
    public void renderToEndOfStream() throws ExoPlaybackException {
        try {
            this.audioSink.playToEndOfStream();
        } catch (WriteException e) {
            throw createRendererException(e, this.inputFormat);
        }
    }

    public void handleMessage(int i, Object obj) throws ExoPlaybackException {
        if (i == 2) {
            this.audioSink.setVolume(((Float) obj).floatValue());
        } else if (i == 3) {
            this.audioSink.setAudioAttributes((AudioAttributes) obj);
        } else if (i != 5) {
            super.handleMessage(i, obj);
        } else {
            this.audioSink.setAuxEffectInfo((AuxEffectInfo) obj);
        }
    }

    /* access modifiers changed from: protected */
    public int getCodecMaxInputSize(MediaCodecInfo mediaCodecInfo, Format format, Format[] formatArr) {
        int codecMaxInputSize2 = getCodecMaxInputSize(mediaCodecInfo, format);
        if (formatArr.length == 1) {
            return codecMaxInputSize2;
        }
        int i = codecMaxInputSize2;
        for (Format format2 : formatArr) {
            if (mediaCodecInfo.isSeamlessAdaptationSupported(format, format2, false)) {
                i = Math.max(i, getCodecMaxInputSize(mediaCodecInfo, format2));
            }
        }
        return i;
    }

    private int getCodecMaxInputSize(MediaCodecInfo mediaCodecInfo, Format format) {
        if (!"OMX.google.raw.decoder".equals(mediaCodecInfo.name) || Util.SDK_INT >= 24 || (Util.SDK_INT == 23 && Util.isTv(this.context))) {
            return format.maxInputSize;
        }
        return -1;
    }

    /* access modifiers changed from: protected */
    public MediaFormat getMediaFormat(Format format, String str, int i, float f) {
        MediaFormat mediaFormat = new MediaFormat();
        mediaFormat.setString("mime", str);
        mediaFormat.setInteger("channel-count", format.channelCount);
        mediaFormat.setInteger("sample-rate", format.sampleRate);
        MediaFormatUtil.setCsdBuffers(mediaFormat, format.initializationData);
        MediaFormatUtil.maybeSetInteger(mediaFormat, "max-input-size", i);
        if (Util.SDK_INT >= 23) {
            mediaFormat.setInteger(LogFactory.PRIORITY_KEY, 0);
            if (f != -1.0f && !deviceDoesntSupportOperatingRate()) {
                mediaFormat.setFloat("operating-rate", f);
            }
        }
        if (Util.SDK_INT <= 28) {
            if (MimeTypes.AUDIO_AC4.equals(format.sampleMimeType)) {
                mediaFormat.setInteger("ac4-is-sync", 1);
            }
        }
        return mediaFormat;
    }

    private void updateCurrentPosition() {
        long currentPositionUs2 = this.audioSink.getCurrentPositionUs(isEnded());
        if (currentPositionUs2 != Long.MIN_VALUE) {
            if (!this.allowPositionDiscontinuity) {
                currentPositionUs2 = Math.max(this.currentPositionUs, currentPositionUs2);
            }
            this.currentPositionUs = currentPositionUs2;
            this.allowPositionDiscontinuity = false;
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:5:0x0018, code lost:
        if ("AXON 7 mini".equals(com.google.android.exoplayer2.util.Util.MODEL) != false) goto L_0x001a;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private static boolean deviceDoesntSupportOperatingRate() {
        /*
            int r0 = com.google.android.exoplayer2.util.Util.SDK_INT
            r1 = 23
            if (r0 != r1) goto L_0x001c
            java.lang.String r0 = com.google.android.exoplayer2.util.Util.MODEL
            java.lang.String r1 = "ZTE B2017G"
            boolean r0 = r1.equals(r0)
            if (r0 != 0) goto L_0x001a
            java.lang.String r0 = com.google.android.exoplayer2.util.Util.MODEL
            java.lang.String r1 = "AXON 7 mini"
            boolean r0 = r1.equals(r0)
            if (r0 == 0) goto L_0x001c
        L_0x001a:
            r0 = 1
            goto L_0x001d
        L_0x001c:
            r0 = 0
        L_0x001d:
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.exoplayer2.audio.MediaCodecAudioRenderer.deviceDoesntSupportOperatingRate():boolean");
    }

    private static boolean codecNeedsDiscardChannelsWorkaround(String str) {
        if (Util.SDK_INT < 24 && "OMX.SEC.aac.dec".equals(str)) {
            if ("samsung".equals(Util.MANUFACTURER) && (Util.DEVICE.startsWith("zeroflte") || Util.DEVICE.startsWith("herolte") || Util.DEVICE.startsWith("heroqlte"))) {
                return true;
            }
        }
        return false;
    }

    private static boolean codecNeedsEosBufferTimestampWorkaround(String str) {
        if (Util.SDK_INT < 21 && "OMX.SEC.mp3.dec".equals(str)) {
            if ("samsung".equals(Util.MANUFACTURER) && (Util.DEVICE.startsWith("baffin") || Util.DEVICE.startsWith("grand") || Util.DEVICE.startsWith("fortuna") || Util.DEVICE.startsWith("gprimelte") || Util.DEVICE.startsWith("j2y18lte") || Util.DEVICE.startsWith("ms01"))) {
                return true;
            }
        }
        return false;
    }

    private static int getPcmEncoding(Format format) {
        if (MimeTypes.AUDIO_RAW.equals(format.sampleMimeType)) {
            return format.pcmEncoding;
        }
        return 2;
    }
}
