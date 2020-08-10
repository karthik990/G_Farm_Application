package com.google.android.exoplayer2.util;

import android.os.SystemClock;
import android.view.Surface;
import com.braintreepayments.api.models.PostalAddressParser;
import com.google.android.exoplayer2.C1996C;
import com.google.android.exoplayer2.ExoPlaybackException;
import com.google.android.exoplayer2.Format;
import com.google.android.exoplayer2.PlaybackParameters;
import com.google.android.exoplayer2.RendererCapabilities.CC;
import com.google.android.exoplayer2.Timeline.Period;
import com.google.android.exoplayer2.Timeline.Window;
import com.google.android.exoplayer2.analytics.AnalyticsListener;
import com.google.android.exoplayer2.analytics.AnalyticsListener.EventTime;
import com.google.android.exoplayer2.audio.AudioAttributes;
import com.google.android.exoplayer2.decoder.DecoderCounters;
import com.google.android.exoplayer2.metadata.Metadata;
import com.google.android.exoplayer2.source.MediaSourceEventListener.LoadEventInfo;
import com.google.android.exoplayer2.source.MediaSourceEventListener.MediaLoadData;
import com.google.android.exoplayer2.source.TrackGroup;
import com.google.android.exoplayer2.source.TrackGroupArray;
import com.google.android.exoplayer2.trackselection.MappingTrackSelector;
import com.google.android.exoplayer2.trackselection.MappingTrackSelector.MappedTrackInfo;
import com.google.android.exoplayer2.trackselection.TrackSelection;
import com.google.android.exoplayer2.trackselection.TrackSelectionArray;
import java.io.IOException;
import java.text.NumberFormat;
import java.util.Locale;
import org.apache.commons.codec.language.p102bm.Rule;

public class EventLogger implements AnalyticsListener {
    private static final String DEFAULT_TAG = "EventLogger";
    private static final int MAX_TIMELINE_ITEM_LINES = 3;
    private static final NumberFormat TIME_FORMAT = NumberFormat.getInstance(Locale.US);
    private final Period period;
    private final long startTimeMs;
    private final String tag;
    private final MappingTrackSelector trackSelector;
    private final Window window;

    private static String getDiscontinuityReasonString(int i) {
        return i != 0 ? i != 1 ? i != 2 ? i != 3 ? i != 4 ? "?" : "INTERNAL" : "AD_INSERTION" : "SEEK_ADJUSTMENT" : "SEEK" : "PERIOD_TRANSITION";
    }

    private static String getPlaybackSuppressionReasonString(int i) {
        return i != 0 ? i != 1 ? "?" : "TRANSIENT_AUDIO_FOCUS_LOSS" : "NONE";
    }

    private static String getRepeatModeString(int i) {
        return i != 0 ? i != 1 ? i != 2 ? "?" : Rule.ALL : "ONE" : "OFF";
    }

    private static String getStateString(int i) {
        return i != 1 ? i != 2 ? i != 3 ? i != 4 ? "?" : "ENDED" : "READY" : "BUFFERING" : "IDLE";
    }

    private static String getTimelineChangeReasonString(int i) {
        return i != 0 ? i != 1 ? i != 2 ? "?" : "DYNAMIC" : "RESET" : "PREPARED";
    }

    private static String getTrackStatusString(boolean z) {
        return z ? "[X]" : "[ ]";
    }

    public void onBandwidthEstimate(EventTime eventTime, int i, long j, long j2) {
    }

    public void onLoadCanceled(EventTime eventTime, LoadEventInfo loadEventInfo, MediaLoadData mediaLoadData) {
    }

    public void onLoadCompleted(EventTime eventTime, LoadEventInfo loadEventInfo, MediaLoadData mediaLoadData) {
    }

    public void onLoadStarted(EventTime eventTime, LoadEventInfo loadEventInfo, MediaLoadData mediaLoadData) {
    }

    static {
        TIME_FORMAT.setMinimumFractionDigits(2);
        TIME_FORMAT.setMaximumFractionDigits(2);
        TIME_FORMAT.setGroupingUsed(false);
    }

    public EventLogger(MappingTrackSelector mappingTrackSelector) {
        this(mappingTrackSelector, DEFAULT_TAG);
    }

    public EventLogger(MappingTrackSelector mappingTrackSelector, String str) {
        this.trackSelector = mappingTrackSelector;
        this.tag = str;
        this.window = new Window();
        this.period = new Period();
        this.startTimeMs = SystemClock.elapsedRealtime();
    }

    public void onLoadingChanged(EventTime eventTime, boolean z) {
        logd(eventTime, "loading", Boolean.toString(z));
    }

    public void onPlayerStateChanged(EventTime eventTime, boolean z, int i) {
        StringBuilder sb = new StringBuilder();
        sb.append(z);
        sb.append(", ");
        sb.append(getStateString(i));
        logd(eventTime, PostalAddressParser.REGION_KEY, sb.toString());
    }

    public void onPlaybackSuppressionReasonChanged(EventTime eventTime, int i) {
        logd(eventTime, "playbackSuppressionReason", getPlaybackSuppressionReasonString(i));
    }

    public void onIsPlayingChanged(EventTime eventTime, boolean z) {
        logd(eventTime, "isPlaying", Boolean.toString(z));
    }

    public void onRepeatModeChanged(EventTime eventTime, int i) {
        logd(eventTime, "repeatMode", getRepeatModeString(i));
    }

    public void onShuffleModeChanged(EventTime eventTime, boolean z) {
        logd(eventTime, "shuffleModeEnabled", Boolean.toString(z));
    }

    public void onPositionDiscontinuity(EventTime eventTime, int i) {
        logd(eventTime, "positionDiscontinuity", getDiscontinuityReasonString(i));
    }

    public void onSeekStarted(EventTime eventTime) {
        logd(eventTime, "seekStarted");
    }

    public void onPlaybackParametersChanged(EventTime eventTime, PlaybackParameters playbackParameters) {
        logd(eventTime, "playbackParameters", Util.formatInvariant("speed=%.2f, pitch=%.2f, skipSilence=%s", Float.valueOf(playbackParameters.speed), Float.valueOf(playbackParameters.pitch), Boolean.valueOf(playbackParameters.skipSilence)));
    }

    public void onTimelineChanged(EventTime eventTime, int i) {
        String str;
        int periodCount = eventTime.timeline.getPeriodCount();
        int windowCount = eventTime.timeline.getWindowCount();
        StringBuilder sb = new StringBuilder();
        sb.append("timeline [");
        sb.append(getEventTimeString(eventTime));
        sb.append(", periodCount=");
        sb.append(periodCount);
        sb.append(", windowCount=");
        sb.append(windowCount);
        sb.append(", reason=");
        sb.append(getTimelineChangeReasonString(i));
        logd(sb.toString());
        int i2 = 0;
        while (true) {
            str = "]";
            if (i2 >= Math.min(periodCount, 3)) {
                break;
            }
            eventTime.timeline.getPeriod(i2, this.period);
            StringBuilder sb2 = new StringBuilder();
            sb2.append("  period [");
            sb2.append(getTimeString(this.period.getDurationMs()));
            sb2.append(str);
            logd(sb2.toString());
            i2++;
        }
        String str2 = "  ...";
        if (periodCount > 3) {
            logd(str2);
        }
        for (int i3 = 0; i3 < Math.min(windowCount, 3); i3++) {
            eventTime.timeline.getWindow(i3, this.window);
            StringBuilder sb3 = new StringBuilder();
            sb3.append("  window [");
            sb3.append(getTimeString(this.window.getDurationMs()));
            String str3 = ", ";
            sb3.append(str3);
            sb3.append(this.window.isSeekable);
            sb3.append(str3);
            sb3.append(this.window.isDynamic);
            sb3.append(str);
            logd(sb3.toString());
        }
        if (windowCount > 3) {
            logd(str2);
        }
        logd(str);
    }

    public void onPlayerError(EventTime eventTime, ExoPlaybackException exoPlaybackException) {
        loge(eventTime, "playerFailed", exoPlaybackException);
    }

    public void onTracksChanged(EventTime eventTime, TrackGroupArray trackGroupArray, TrackSelectionArray trackSelectionArray) {
        String str;
        String str2;
        String str3;
        String str4;
        String str5;
        String str6;
        String str7;
        int i;
        MappingTrackSelector mappingTrackSelector = this.trackSelector;
        MappedTrackInfo currentMappedTrackInfo = mappingTrackSelector != null ? mappingTrackSelector.getCurrentMappedTrackInfo() : null;
        if (currentMappedTrackInfo == null) {
            logd(eventTime, "tracks", "[]");
            return;
        }
        EventTime eventTime2 = eventTime;
        StringBuilder sb = new StringBuilder();
        sb.append("tracks [");
        sb.append(getEventTimeString(eventTime));
        String str8 = ", ";
        sb.append(str8);
        logd(sb.toString());
        int rendererCount = currentMappedTrackInfo.getRendererCount();
        int i2 = 0;
        while (true) {
            str = ", supported=";
            str2 = " Track:";
            str3 = "    Group:";
            str4 = "  ]";
            str5 = "      ";
            str6 = "    ]";
            str7 = " [";
            if (i2 >= rendererCount) {
                break;
            }
            TrackGroupArray trackGroups = currentMappedTrackInfo.getTrackGroups(i2);
            TrackSelection trackSelection = trackSelectionArray.get(i2);
            if (trackGroups.length > 0) {
                StringBuilder sb2 = new StringBuilder();
                i = rendererCount;
                sb2.append("  Renderer:");
                sb2.append(i2);
                sb2.append(str7);
                logd(sb2.toString());
                int i3 = 0;
                while (i3 < trackGroups.length) {
                    TrackGroup trackGroup = trackGroups.get(i3);
                    TrackGroupArray trackGroupArray2 = trackGroups;
                    String str9 = str4;
                    String adaptiveSupportString = getAdaptiveSupportString(trackGroup.length, currentMappedTrackInfo.getAdaptiveSupport(i2, i3, false));
                    StringBuilder sb3 = new StringBuilder();
                    sb3.append(str3);
                    sb3.append(i3);
                    sb3.append(", adaptive_supported=");
                    sb3.append(adaptiveSupportString);
                    sb3.append(str7);
                    logd(sb3.toString());
                    int i4 = 0;
                    while (i4 < trackGroup.length) {
                        String trackStatusString = getTrackStatusString(trackSelection, trackGroup, i4);
                        String formatSupportString = CC.getFormatSupportString(currentMappedTrackInfo.getTrackSupport(i2, i3, i4));
                        String str10 = str7;
                        StringBuilder sb4 = new StringBuilder();
                        sb4.append(str5);
                        sb4.append(trackStatusString);
                        sb4.append(str2);
                        sb4.append(i4);
                        sb4.append(str8);
                        sb4.append(Format.toLogString(trackGroup.getFormat(i4)));
                        sb4.append(str);
                        sb4.append(formatSupportString);
                        logd(sb4.toString());
                        i4++;
                        str7 = str10;
                    }
                    String str11 = str7;
                    logd(str6);
                    i3++;
                    TrackSelectionArray trackSelectionArray2 = trackSelectionArray;
                    trackGroups = trackGroupArray2;
                    str4 = str9;
                }
                String str12 = str4;
                if (trackSelection != null) {
                    int i5 = 0;
                    while (true) {
                        if (i5 >= trackSelection.length()) {
                            break;
                        }
                        Metadata metadata = trackSelection.getFormat(i5).metadata;
                        if (metadata != null) {
                            logd("    Metadata [");
                            printMetadata(metadata, str5);
                            logd(str6);
                            break;
                        }
                        i5++;
                    }
                }
                logd(str12);
            } else {
                i = rendererCount;
            }
            i2++;
            rendererCount = i;
        }
        String str13 = str4;
        String str14 = str7;
        TrackGroupArray unmappedTrackGroups = currentMappedTrackInfo.getUnmappedTrackGroups();
        if (unmappedTrackGroups.length > 0) {
            logd("  Renderer:None [");
            int i6 = 0;
            while (i6 < unmappedTrackGroups.length) {
                StringBuilder sb5 = new StringBuilder();
                sb5.append(str3);
                sb5.append(i6);
                String str15 = str14;
                sb5.append(str15);
                logd(sb5.toString());
                TrackGroup trackGroup2 = unmappedTrackGroups.get(i6);
                for (int i7 = 0; i7 < trackGroup2.length; i7++) {
                    String trackStatusString2 = getTrackStatusString(false);
                    String formatSupportString2 = CC.getFormatSupportString(0);
                    StringBuilder sb6 = new StringBuilder();
                    sb6.append(str5);
                    sb6.append(trackStatusString2);
                    sb6.append(str2);
                    sb6.append(i7);
                    sb6.append(str8);
                    sb6.append(Format.toLogString(trackGroup2.getFormat(i7)));
                    sb6.append(str);
                    sb6.append(formatSupportString2);
                    logd(sb6.toString());
                }
                logd(str6);
                i6++;
                str14 = str15;
            }
            logd(str13);
        }
        logd("]");
    }

    public void onSeekProcessed(EventTime eventTime) {
        logd(eventTime, "seekProcessed");
    }

    public void onMetadata(EventTime eventTime, Metadata metadata) {
        StringBuilder sb = new StringBuilder();
        sb.append("metadata [");
        sb.append(getEventTimeString(eventTime));
        sb.append(", ");
        logd(sb.toString());
        printMetadata(metadata, "  ");
        logd("]");
    }

    public void onDecoderEnabled(EventTime eventTime, int i, DecoderCounters decoderCounters) {
        logd(eventTime, "decoderEnabled", Util.getTrackTypeString(i));
    }

    public void onAudioSessionId(EventTime eventTime, int i) {
        logd(eventTime, "audioSessionId", Integer.toString(i));
    }

    public void onAudioAttributesChanged(EventTime eventTime, AudioAttributes audioAttributes) {
        StringBuilder sb = new StringBuilder();
        sb.append(audioAttributes.contentType);
        String str = ",";
        sb.append(str);
        sb.append(audioAttributes.flags);
        sb.append(str);
        sb.append(audioAttributes.usage);
        sb.append(str);
        sb.append(audioAttributes.allowedCapturePolicy);
        logd(eventTime, "audioAttributes", sb.toString());
    }

    public void onVolumeChanged(EventTime eventTime, float f) {
        logd(eventTime, "volume", Float.toString(f));
    }

    public void onDecoderInitialized(EventTime eventTime, int i, String str, long j) {
        StringBuilder sb = new StringBuilder();
        sb.append(Util.getTrackTypeString(i));
        sb.append(", ");
        sb.append(str);
        logd(eventTime, "decoderInitialized", sb.toString());
    }

    public void onDecoderInputFormatChanged(EventTime eventTime, int i, Format format) {
        StringBuilder sb = new StringBuilder();
        sb.append(Util.getTrackTypeString(i));
        sb.append(", ");
        sb.append(Format.toLogString(format));
        logd(eventTime, "decoderInputFormat", sb.toString());
    }

    public void onDecoderDisabled(EventTime eventTime, int i, DecoderCounters decoderCounters) {
        logd(eventTime, "decoderDisabled", Util.getTrackTypeString(i));
    }

    public void onAudioUnderrun(EventTime eventTime, int i, long j, long j2) {
        StringBuilder sb = new StringBuilder();
        sb.append(i);
        String str = ", ";
        sb.append(str);
        sb.append(j);
        sb.append(str);
        sb.append(j2);
        sb.append("]");
        loge(eventTime, "audioTrackUnderrun", sb.toString(), null);
    }

    public void onDroppedVideoFrames(EventTime eventTime, int i, long j) {
        logd(eventTime, "droppedFrames", Integer.toString(i));
    }

    public void onVideoSizeChanged(EventTime eventTime, int i, int i2, int i3, float f) {
        StringBuilder sb = new StringBuilder();
        sb.append(i);
        sb.append(", ");
        sb.append(i2);
        logd(eventTime, "videoSize", sb.toString());
    }

    public void onRenderedFirstFrame(EventTime eventTime, Surface surface) {
        logd(eventTime, "renderedFirstFrame", String.valueOf(surface));
    }

    public void onMediaPeriodCreated(EventTime eventTime) {
        logd(eventTime, "mediaPeriodCreated");
    }

    public void onMediaPeriodReleased(EventTime eventTime) {
        logd(eventTime, "mediaPeriodReleased");
    }

    public void onLoadError(EventTime eventTime, LoadEventInfo loadEventInfo, MediaLoadData mediaLoadData, IOException iOException, boolean z) {
        printInternalError(eventTime, "loadError", iOException);
    }

    public void onReadingStarted(EventTime eventTime) {
        logd(eventTime, "mediaPeriodReadingStarted");
    }

    public void onSurfaceSizeChanged(EventTime eventTime, int i, int i2) {
        StringBuilder sb = new StringBuilder();
        sb.append(i);
        sb.append(", ");
        sb.append(i2);
        logd(eventTime, "surfaceSize", sb.toString());
    }

    public void onUpstreamDiscarded(EventTime eventTime, MediaLoadData mediaLoadData) {
        logd(eventTime, "upstreamDiscarded", Format.toLogString(mediaLoadData.trackFormat));
    }

    public void onDownstreamFormatChanged(EventTime eventTime, MediaLoadData mediaLoadData) {
        logd(eventTime, "downstreamFormat", Format.toLogString(mediaLoadData.trackFormat));
    }

    public void onDrmSessionAcquired(EventTime eventTime) {
        logd(eventTime, "drmSessionAcquired");
    }

    public void onDrmSessionManagerError(EventTime eventTime, Exception exc) {
        printInternalError(eventTime, "drmSessionManagerError", exc);
    }

    public void onDrmKeysRestored(EventTime eventTime) {
        logd(eventTime, "drmKeysRestored");
    }

    public void onDrmKeysRemoved(EventTime eventTime) {
        logd(eventTime, "drmKeysRemoved");
    }

    public void onDrmKeysLoaded(EventTime eventTime) {
        logd(eventTime, "drmKeysLoaded");
    }

    public void onDrmSessionReleased(EventTime eventTime) {
        logd(eventTime, "drmSessionReleased");
    }

    /* access modifiers changed from: protected */
    public void logd(String str) {
        Log.m1390d(this.tag, str);
    }

    /* access modifiers changed from: protected */
    public void loge(String str, Throwable th) {
        Log.m1393e(this.tag, str, th);
    }

    private void logd(EventTime eventTime, String str) {
        logd(getEventString(eventTime, str));
    }

    private void logd(EventTime eventTime, String str, String str2) {
        logd(getEventString(eventTime, str, str2));
    }

    private void loge(EventTime eventTime, String str, Throwable th) {
        loge(getEventString(eventTime, str), th);
    }

    private void loge(EventTime eventTime, String str, String str2, Throwable th) {
        loge(getEventString(eventTime, str, str2), th);
    }

    private void printInternalError(EventTime eventTime, String str, Exception exc) {
        loge(eventTime, "internalError", str, exc);
    }

    private void printMetadata(Metadata metadata, String str) {
        for (int i = 0; i < metadata.length(); i++) {
            StringBuilder sb = new StringBuilder();
            sb.append(str);
            sb.append(metadata.get(i));
            logd(sb.toString());
        }
    }

    private String getEventString(EventTime eventTime, String str) {
        StringBuilder sb = new StringBuilder();
        sb.append(str);
        sb.append(" [");
        sb.append(getEventTimeString(eventTime));
        sb.append("]");
        return sb.toString();
    }

    private String getEventString(EventTime eventTime, String str, String str2) {
        StringBuilder sb = new StringBuilder();
        sb.append(str);
        sb.append(" [");
        sb.append(getEventTimeString(eventTime));
        sb.append(", ");
        sb.append(str2);
        sb.append("]");
        return sb.toString();
    }

    private String getEventTimeString(EventTime eventTime) {
        StringBuilder sb = new StringBuilder();
        sb.append("window=");
        sb.append(eventTime.windowIndex);
        String sb2 = sb.toString();
        if (eventTime.mediaPeriodId != null) {
            StringBuilder sb3 = new StringBuilder();
            sb3.append(sb2);
            sb3.append(", period=");
            sb3.append(eventTime.timeline.getIndexOfPeriod(eventTime.mediaPeriodId.periodUid));
            sb2 = sb3.toString();
            if (eventTime.mediaPeriodId.isAd()) {
                StringBuilder sb4 = new StringBuilder();
                sb4.append(sb2);
                sb4.append(", adGroup=");
                sb4.append(eventTime.mediaPeriodId.adGroupIndex);
                String sb5 = sb4.toString();
                StringBuilder sb6 = new StringBuilder();
                sb6.append(sb5);
                sb6.append(", ad=");
                sb6.append(eventTime.mediaPeriodId.adIndexInAdGroup);
                sb2 = sb6.toString();
            }
        }
        StringBuilder sb7 = new StringBuilder();
        sb7.append("eventTime=");
        sb7.append(getTimeString(eventTime.realtimeMs - this.startTimeMs));
        sb7.append(", mediaPos=");
        sb7.append(getTimeString(eventTime.currentPlaybackPositionMs));
        sb7.append(", ");
        sb7.append(sb2);
        return sb7.toString();
    }

    private static String getTimeString(long j) {
        return j == C1996C.TIME_UNSET ? "?" : TIME_FORMAT.format((double) (((float) j) / 1000.0f));
    }

    private static String getAdaptiveSupportString(int i, int i2) {
        if (i < 2) {
            return "N/A";
        }
        if (i2 == 0) {
            return "NO";
        }
        if (i2 == 8) {
            return "YES_NOT_SEAMLESS";
        }
        if (i2 == 16) {
            return "YES";
        }
        throw new IllegalStateException();
    }

    private static String getTrackStatusString(TrackSelection trackSelection, TrackGroup trackGroup, int i) {
        return getTrackStatusString((trackSelection == null || trackSelection.getTrackGroup() != trackGroup || trackSelection.indexOf(i) == -1) ? false : true);
    }
}
