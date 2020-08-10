package com.google.android.exoplayer2.source.dash;

import android.net.Uri;
import android.os.Handler;
import android.os.SystemClock;
import android.text.TextUtils;
import android.util.SparseArray;
import com.google.android.exoplayer2.C1996C;
import com.google.android.exoplayer2.DefaultRenderersFactory;
import com.google.android.exoplayer2.ExoPlayerLibraryInfo;
import com.google.android.exoplayer2.ParserException;
import com.google.android.exoplayer2.Timeline;
import com.google.android.exoplayer2.Timeline.Period;
import com.google.android.exoplayer2.Timeline.Window;
import com.google.android.exoplayer2.drm.DrmSessionManager;
import com.google.android.exoplayer2.drm.DrmSessionManager.CC;
import com.google.android.exoplayer2.offline.FilteringManifestParser;
import com.google.android.exoplayer2.offline.StreamKey;
import com.google.android.exoplayer2.source.BaseMediaSource;
import com.google.android.exoplayer2.source.CompositeSequenceableLoaderFactory;
import com.google.android.exoplayer2.source.DefaultCompositeSequenceableLoaderFactory;
import com.google.android.exoplayer2.source.MediaPeriod;
import com.google.android.exoplayer2.source.MediaSource.MediaPeriodId;
import com.google.android.exoplayer2.source.MediaSourceEventListener;
import com.google.android.exoplayer2.source.MediaSourceEventListener.EventDispatcher;
import com.google.android.exoplayer2.source.MediaSourceFactory;
import com.google.android.exoplayer2.source.dash.PlayerEmsgHandler.PlayerEmsgCallback;
import com.google.android.exoplayer2.source.dash.manifest.AdaptationSet;
import com.google.android.exoplayer2.source.dash.manifest.DashManifest;
import com.google.android.exoplayer2.source.dash.manifest.DashManifestParser;
import com.google.android.exoplayer2.source.dash.manifest.Representation;
import com.google.android.exoplayer2.source.dash.manifest.UtcTimingElement;
import com.google.android.exoplayer2.upstream.Allocator;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DataSpec;
import com.google.android.exoplayer2.upstream.DefaultLoadErrorHandlingPolicy;
import com.google.android.exoplayer2.upstream.LoadErrorHandlingPolicy;
import com.google.android.exoplayer2.upstream.Loader;
import com.google.android.exoplayer2.upstream.Loader.Callback;
import com.google.android.exoplayer2.upstream.Loader.LoadErrorAction;
import com.google.android.exoplayer2.upstream.LoaderErrorThrower;
import com.google.android.exoplayer2.upstream.LoaderErrorThrower.Dummy;
import com.google.android.exoplayer2.upstream.ParsingLoadable;
import com.google.android.exoplayer2.upstream.ParsingLoadable.Parser;
import com.google.android.exoplayer2.upstream.TransferListener;
import com.google.android.exoplayer2.util.Assertions;
import com.google.android.exoplayer2.util.Log;
import com.google.android.exoplayer2.util.Util;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.TimeZone;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.slf4j.Marker;

public final class DashMediaSource extends BaseMediaSource {
    @Deprecated
    public static final long DEFAULT_LIVE_PRESENTATION_DELAY_FIXED_MS = 30000;
    public static final long DEFAULT_LIVE_PRESENTATION_DELAY_MS = 30000;
    @Deprecated
    public static final long DEFAULT_LIVE_PRESENTATION_DELAY_PREFER_MANIFEST_MS = -1;
    private static final long MIN_LIVE_DEFAULT_START_POSITION_US = 5000000;
    private static final int NOTIFY_MANIFEST_INTERVAL_MS = 5000;
    private static final String TAG = "DashMediaSource";
    private final com.google.android.exoplayer2.source.dash.DashChunkSource.Factory chunkSourceFactory;
    private final CompositeSequenceableLoaderFactory compositeSequenceableLoaderFactory;
    private DataSource dataSource;
    private final DrmSessionManager<?> drmSessionManager;
    private long elapsedRealtimeOffsetMs;
    private long expiredManifestPublishTimeUs;
    private int firstPeriodId;
    private Handler handler;
    private Uri initialManifestUri;
    private final long livePresentationDelayMs;
    private final boolean livePresentationDelayOverridesManifest;
    private final LoadErrorHandlingPolicy loadErrorHandlingPolicy;
    /* access modifiers changed from: private */
    public Loader loader;
    private DashManifest manifest;
    private final ManifestCallback manifestCallback;
    private final com.google.android.exoplayer2.upstream.DataSource.Factory manifestDataSourceFactory;
    private final EventDispatcher manifestEventDispatcher;
    /* access modifiers changed from: private */
    public IOException manifestFatalError;
    private long manifestLoadEndTimestampMs;
    private final LoaderErrorThrower manifestLoadErrorThrower;
    private boolean manifestLoadPending;
    private long manifestLoadStartTimestampMs;
    private final Parser<? extends DashManifest> manifestParser;
    private Uri manifestUri;
    private final Object manifestUriLock;
    private TransferListener mediaTransferListener;
    private final SparseArray<DashMediaPeriod> periodsById;
    private final PlayerEmsgCallback playerEmsgCallback;
    private final Runnable refreshManifestRunnable;
    private final boolean sideloadedManifest;
    private final Runnable simulateManifestRefreshRunnable;
    private int staleManifestReloadAttempt;
    private final Object tag;

    private static final class DashTimeline extends Timeline {
        private final int firstPeriodId;
        private final DashManifest manifest;
        private final long offsetInFirstPeriodUs;
        private final long presentationStartTimeMs;
        private final long windowDefaultStartPositionUs;
        private final long windowDurationUs;
        private final long windowStartTimeMs;
        private final Object windowTag;

        public int getWindowCount() {
            return 1;
        }

        public DashTimeline(long j, long j2, int i, long j3, long j4, long j5, DashManifest dashManifest, Object obj) {
            this.presentationStartTimeMs = j;
            this.windowStartTimeMs = j2;
            this.firstPeriodId = i;
            this.offsetInFirstPeriodUs = j3;
            this.windowDurationUs = j4;
            this.windowDefaultStartPositionUs = j5;
            this.manifest = dashManifest;
            this.windowTag = obj;
        }

        public int getPeriodCount() {
            return this.manifest.getPeriodCount();
        }

        public Period getPeriod(int i, Period period, boolean z) {
            Assertions.checkIndex(i, 0, getPeriodCount());
            Integer num = null;
            Object obj = z ? this.manifest.getPeriod(i).f1522id : null;
            if (z) {
                num = Integer.valueOf(this.firstPeriodId + i);
            }
            return period.set(obj, num, 0, this.manifest.getPeriodDurationUs(i), C1996C.msToUs(this.manifest.getPeriod(i).startMs - this.manifest.getPeriod(0).startMs) - this.offsetInFirstPeriodUs);
        }

        public Window getWindow(int i, Window window, long j) {
            Window window2 = window;
            Assertions.checkIndex(i, 0, 1);
            long adjustedWindowDefaultStartPositionUs = getAdjustedWindowDefaultStartPositionUs(j);
            Object obj = Window.SINGLE_WINDOW_UID;
            Object obj2 = this.windowTag;
            DashManifest dashManifest = this.manifest;
            return window.set(obj, obj2, dashManifest, this.presentationStartTimeMs, this.windowStartTimeMs, true, isMovingLiveWindow(dashManifest), this.manifest.dynamic, adjustedWindowDefaultStartPositionUs, this.windowDurationUs, 0, getPeriodCount() - 1, this.offsetInFirstPeriodUs);
        }

        public int getIndexOfPeriod(Object obj) {
            if (!(obj instanceof Integer)) {
                return -1;
            }
            int intValue = ((Integer) obj).intValue() - this.firstPeriodId;
            if (intValue < 0 || intValue >= getPeriodCount()) {
                intValue = -1;
            }
            return intValue;
        }

        private long getAdjustedWindowDefaultStartPositionUs(long j) {
            long j2 = this.windowDefaultStartPositionUs;
            if (!isMovingLiveWindow(this.manifest)) {
                return j2;
            }
            if (j > 0) {
                j2 += j;
                if (j2 > this.windowDurationUs) {
                    return C1996C.TIME_UNSET;
                }
            }
            long j3 = this.offsetInFirstPeriodUs + j2;
            long periodDurationUs = this.manifest.getPeriodDurationUs(0);
            long j4 = j3;
            int i = 0;
            while (i < this.manifest.getPeriodCount() - 1 && j4 >= periodDurationUs) {
                j4 -= periodDurationUs;
                i++;
                periodDurationUs = this.manifest.getPeriodDurationUs(i);
            }
            com.google.android.exoplayer2.source.dash.manifest.Period period = this.manifest.getPeriod(i);
            int adaptationSetIndex = period.getAdaptationSetIndex(2);
            if (adaptationSetIndex == -1) {
                return j2;
            }
            DashSegmentIndex index = ((Representation) ((AdaptationSet) period.adaptationSets.get(adaptationSetIndex)).representations.get(0)).getIndex();
            if (!(index == null || index.getSegmentCount(periodDurationUs) == 0)) {
                j2 = (j2 + index.getTimeUs(index.getSegmentNum(j4, periodDurationUs))) - j4;
            }
            return j2;
        }

        public Object getUidOfPeriod(int i) {
            Assertions.checkIndex(i, 0, getPeriodCount());
            return Integer.valueOf(this.firstPeriodId + i);
        }

        private static boolean isMovingLiveWindow(DashManifest dashManifest) {
            return dashManifest.dynamic && dashManifest.minUpdatePeriodMs != C1996C.TIME_UNSET && dashManifest.durationMs == C1996C.TIME_UNSET;
        }
    }

    private final class DefaultPlayerEmsgCallback implements PlayerEmsgCallback {
        private DefaultPlayerEmsgCallback() {
        }

        public void onDashManifestRefreshRequested() {
            DashMediaSource.this.onDashManifestRefreshRequested();
        }

        public void onDashManifestPublishTimeExpired(long j) {
            DashMediaSource.this.onDashManifestPublishTimeExpired(j);
        }
    }

    public static final class Factory implements MediaSourceFactory {
        private final com.google.android.exoplayer2.source.dash.DashChunkSource.Factory chunkSourceFactory;
        private CompositeSequenceableLoaderFactory compositeSequenceableLoaderFactory;
        private DrmSessionManager<?> drmSessionManager;
        private boolean isCreateCalled;
        private long livePresentationDelayMs;
        private boolean livePresentationDelayOverridesManifest;
        private LoadErrorHandlingPolicy loadErrorHandlingPolicy;
        private final com.google.android.exoplayer2.upstream.DataSource.Factory manifestDataSourceFactory;
        private Parser<? extends DashManifest> manifestParser;
        private List<StreamKey> streamKeys;
        private Object tag;

        public int[] getSupportedTypes() {
            return new int[]{0};
        }

        public Factory(com.google.android.exoplayer2.upstream.DataSource.Factory factory) {
            this(new com.google.android.exoplayer2.source.dash.DefaultDashChunkSource.Factory(factory), factory);
        }

        public Factory(com.google.android.exoplayer2.source.dash.DashChunkSource.Factory factory, com.google.android.exoplayer2.upstream.DataSource.Factory factory2) {
            this.chunkSourceFactory = (com.google.android.exoplayer2.source.dash.DashChunkSource.Factory) Assertions.checkNotNull(factory);
            this.manifestDataSourceFactory = factory2;
            this.drmSessionManager = CC.getDummyDrmSessionManager();
            this.loadErrorHandlingPolicy = new DefaultLoadErrorHandlingPolicy();
            this.livePresentationDelayMs = 30000;
            this.compositeSequenceableLoaderFactory = new DefaultCompositeSequenceableLoaderFactory();
        }

        public Factory setTag(Object obj) {
            Assertions.checkState(!this.isCreateCalled);
            this.tag = obj;
            return this;
        }

        public Factory setDrmSessionManager(DrmSessionManager<?> drmSessionManager2) {
            Assertions.checkState(!this.isCreateCalled);
            this.drmSessionManager = drmSessionManager2;
            return this;
        }

        @Deprecated
        public Factory setMinLoadableRetryCount(int i) {
            return setLoadErrorHandlingPolicy(new DefaultLoadErrorHandlingPolicy(i));
        }

        public Factory setLoadErrorHandlingPolicy(LoadErrorHandlingPolicy loadErrorHandlingPolicy2) {
            Assertions.checkState(!this.isCreateCalled);
            this.loadErrorHandlingPolicy = loadErrorHandlingPolicy2;
            return this;
        }

        @Deprecated
        public Factory setLivePresentationDelayMs(long j) {
            if (j == -1) {
                return setLivePresentationDelayMs(30000, false);
            }
            return setLivePresentationDelayMs(j, true);
        }

        public Factory setLivePresentationDelayMs(long j, boolean z) {
            Assertions.checkState(!this.isCreateCalled);
            this.livePresentationDelayMs = j;
            this.livePresentationDelayOverridesManifest = z;
            return this;
        }

        public Factory setManifestParser(Parser<? extends DashManifest> parser) {
            Assertions.checkState(!this.isCreateCalled);
            this.manifestParser = (Parser) Assertions.checkNotNull(parser);
            return this;
        }

        public Factory setCompositeSequenceableLoaderFactory(CompositeSequenceableLoaderFactory compositeSequenceableLoaderFactory2) {
            Assertions.checkState(!this.isCreateCalled);
            this.compositeSequenceableLoaderFactory = (CompositeSequenceableLoaderFactory) Assertions.checkNotNull(compositeSequenceableLoaderFactory2);
            return this;
        }

        public DashMediaSource createMediaSource(DashManifest dashManifest) {
            Assertions.checkArgument(!dashManifest.dynamic);
            this.isCreateCalled = true;
            List<StreamKey> list = this.streamKeys;
            if (list != null && !list.isEmpty()) {
                dashManifest = dashManifest.copy(this.streamKeys);
            }
            DashMediaSource dashMediaSource = new DashMediaSource(dashManifest, null, null, null, this.chunkSourceFactory, this.compositeSequenceableLoaderFactory, this.drmSessionManager, this.loadErrorHandlingPolicy, this.livePresentationDelayMs, this.livePresentationDelayOverridesManifest, this.tag);
            return dashMediaSource;
        }

        @Deprecated
        public DashMediaSource createMediaSource(DashManifest dashManifest, Handler handler, MediaSourceEventListener mediaSourceEventListener) {
            DashMediaSource createMediaSource = createMediaSource(dashManifest);
            if (!(handler == null || mediaSourceEventListener == null)) {
                createMediaSource.addEventListener(handler, mediaSourceEventListener);
            }
            return createMediaSource;
        }

        @Deprecated
        public DashMediaSource createMediaSource(Uri uri, Handler handler, MediaSourceEventListener mediaSourceEventListener) {
            DashMediaSource createMediaSource = createMediaSource(uri);
            if (!(handler == null || mediaSourceEventListener == null)) {
                createMediaSource.addEventListener(handler, mediaSourceEventListener);
            }
            return createMediaSource;
        }

        public DashMediaSource createMediaSource(Uri uri) {
            this.isCreateCalled = true;
            if (this.manifestParser == null) {
                this.manifestParser = new DashManifestParser();
            }
            List<StreamKey> list = this.streamKeys;
            if (list != null) {
                this.manifestParser = new FilteringManifestParser(this.manifestParser, list);
            }
            DashMediaSource dashMediaSource = new DashMediaSource(null, (Uri) Assertions.checkNotNull(uri), this.manifestDataSourceFactory, this.manifestParser, this.chunkSourceFactory, this.compositeSequenceableLoaderFactory, this.drmSessionManager, this.loadErrorHandlingPolicy, this.livePresentationDelayMs, this.livePresentationDelayOverridesManifest, this.tag);
            return dashMediaSource;
        }

        public Factory setStreamKeys(List<StreamKey> list) {
            Assertions.checkState(!this.isCreateCalled);
            this.streamKeys = list;
            return this;
        }
    }

    static final class Iso8601Parser implements Parser<Long> {
        private static final Pattern TIMESTAMP_WITH_TIMEZONE_PATTERN = Pattern.compile("(.+?)(Z|((\\+|-|−)(\\d\\d)(:?(\\d\\d))?))");

        Iso8601Parser() {
        }

        public Long parse(Uri uri, InputStream inputStream) throws IOException {
            String readLine = new BufferedReader(new InputStreamReader(inputStream, Charset.forName("UTF-8"))).readLine();
            try {
                Matcher matcher = TIMESTAMP_WITH_TIMEZONE_PATTERN.matcher(readLine);
                if (matcher.matches()) {
                    String group = matcher.group(1);
                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.US);
                    simpleDateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
                    long time = simpleDateFormat.parse(group).getTime();
                    if (!"Z".equals(matcher.group(2))) {
                        long j = Marker.ANY_NON_NULL_MARKER.equals(matcher.group(4)) ? 1 : -1;
                        long parseLong = Long.parseLong(matcher.group(5));
                        String group2 = matcher.group(7);
                        time -= j * ((((parseLong * 60) + (TextUtils.isEmpty(group2) ? 0 : Long.parseLong(group2))) * 60) * 1000);
                    }
                    return Long.valueOf(time);
                }
                StringBuilder sb = new StringBuilder();
                sb.append("Couldn't parse timestamp: ");
                sb.append(readLine);
                throw new ParserException(sb.toString());
            } catch (ParseException e) {
                throw new ParserException((Throwable) e);
            }
        }
    }

    private final class ManifestCallback implements Callback<ParsingLoadable<DashManifest>> {
        private ManifestCallback() {
        }

        public void onLoadCompleted(ParsingLoadable<DashManifest> parsingLoadable, long j, long j2) {
            DashMediaSource.this.onManifestLoadCompleted(parsingLoadable, j, j2);
        }

        public void onLoadCanceled(ParsingLoadable<DashManifest> parsingLoadable, long j, long j2, boolean z) {
            DashMediaSource.this.onLoadCanceled(parsingLoadable, j, j2);
        }

        public LoadErrorAction onLoadError(ParsingLoadable<DashManifest> parsingLoadable, long j, long j2, IOException iOException, int i) {
            return DashMediaSource.this.onManifestLoadError(parsingLoadable, j, j2, iOException, i);
        }
    }

    final class ManifestLoadErrorThrower implements LoaderErrorThrower {
        ManifestLoadErrorThrower() {
        }

        public void maybeThrowError() throws IOException {
            DashMediaSource.this.loader.maybeThrowError();
            maybeThrowManifestError();
        }

        public void maybeThrowError(int i) throws IOException {
            DashMediaSource.this.loader.maybeThrowError(i);
            maybeThrowManifestError();
        }

        private void maybeThrowManifestError() throws IOException {
            if (DashMediaSource.this.manifestFatalError != null) {
                throw DashMediaSource.this.manifestFatalError;
            }
        }
    }

    private static final class PeriodSeekInfo {
        public final long availableEndTimeUs;
        public final long availableStartTimeUs;
        public final boolean isIndexExplicit;

        public static PeriodSeekInfo createPeriodSeekInfo(com.google.android.exoplayer2.source.dash.manifest.Period period, long j) {
            boolean z;
            int i;
            boolean z2;
            com.google.android.exoplayer2.source.dash.manifest.Period period2 = period;
            long j2 = j;
            int size = period2.adaptationSets.size();
            int i2 = 0;
            int i3 = 0;
            while (true) {
                if (i3 >= size) {
                    z = false;
                    break;
                }
                int i4 = ((AdaptationSet) period2.adaptationSets.get(i3)).type;
                if (i4 == 1 || i4 == 2) {
                    z = true;
                } else {
                    i3++;
                }
            }
            long j3 = Long.MAX_VALUE;
            int i5 = 0;
            boolean z3 = false;
            boolean z4 = false;
            long j4 = 0;
            while (i5 < size) {
                AdaptationSet adaptationSet = (AdaptationSet) period2.adaptationSets.get(i5);
                if (!z || adaptationSet.type != 3) {
                    DashSegmentIndex index = ((Representation) adaptationSet.representations.get(i2)).getIndex();
                    if (index == null) {
                        PeriodSeekInfo periodSeekInfo = new PeriodSeekInfo(true, 0, j);
                        return periodSeekInfo;
                    }
                    boolean isExplicit = index.isExplicit() | z4;
                    int segmentCount = index.getSegmentCount(j2);
                    if (segmentCount == 0) {
                        i = size;
                        z2 = z;
                        z4 = isExplicit;
                        z3 = true;
                        j4 = 0;
                        j3 = 0;
                    } else {
                        if (!z3) {
                            z2 = z;
                            long firstSegmentNum = index.getFirstSegmentNum();
                            i = size;
                            long max = Math.max(j4, index.getTimeUs(firstSegmentNum));
                            if (segmentCount != -1) {
                                long j5 = (firstSegmentNum + ((long) segmentCount)) - 1;
                                j4 = max;
                                j3 = Math.min(j3, index.getTimeUs(j5) + index.getDurationUs(j5, j2));
                            } else {
                                j4 = max;
                            }
                        } else {
                            i = size;
                            z2 = z;
                        }
                        z4 = isExplicit;
                    }
                } else {
                    i = size;
                    z2 = z;
                }
                i5++;
                i2 = 0;
                period2 = period;
                z = z2;
                size = i;
            }
            PeriodSeekInfo periodSeekInfo2 = new PeriodSeekInfo(z4, j4, j3);
            return periodSeekInfo2;
        }

        private PeriodSeekInfo(boolean z, long j, long j2) {
            this.isIndexExplicit = z;
            this.availableStartTimeUs = j;
            this.availableEndTimeUs = j2;
        }
    }

    private final class UtcTimestampCallback implements Callback<ParsingLoadable<Long>> {
        private UtcTimestampCallback() {
        }

        public void onLoadCompleted(ParsingLoadable<Long> parsingLoadable, long j, long j2) {
            DashMediaSource.this.onUtcTimestampLoadCompleted(parsingLoadable, j, j2);
        }

        public void onLoadCanceled(ParsingLoadable<Long> parsingLoadable, long j, long j2, boolean z) {
            DashMediaSource.this.onLoadCanceled(parsingLoadable, j, j2);
        }

        public LoadErrorAction onLoadError(ParsingLoadable<Long> parsingLoadable, long j, long j2, IOException iOException, int i) {
            return DashMediaSource.this.onUtcTimestampLoadError(parsingLoadable, j, j2, iOException);
        }
    }

    private static final class XsDateTimeParser implements Parser<Long> {
        private XsDateTimeParser() {
        }

        public Long parse(Uri uri, InputStream inputStream) throws IOException {
            return Long.valueOf(Util.parseXsDateTime(new BufferedReader(new InputStreamReader(inputStream)).readLine()));
        }
    }

    static {
        ExoPlayerLibraryInfo.registerModule("goog.exo.dash");
    }

    @Deprecated
    public DashMediaSource(DashManifest dashManifest, com.google.android.exoplayer2.source.dash.DashChunkSource.Factory factory, Handler handler2, MediaSourceEventListener mediaSourceEventListener) {
        this(dashManifest, factory, 3, handler2, mediaSourceEventListener);
    }

    @Deprecated
    public DashMediaSource(DashManifest dashManifest, com.google.android.exoplayer2.source.dash.DashChunkSource.Factory factory, int i, Handler handler2, MediaSourceEventListener mediaSourceEventListener) {
        Handler handler3 = handler2;
        MediaSourceEventListener mediaSourceEventListener2 = mediaSourceEventListener;
        this(dashManifest, null, null, null, factory, new DefaultCompositeSequenceableLoaderFactory(), CC.getDummyDrmSessionManager(), new DefaultLoadErrorHandlingPolicy(i), 30000, false, null);
        if (handler3 == null || mediaSourceEventListener2 == null) {
            return;
        }
        addEventListener(handler3, mediaSourceEventListener2);
    }

    @Deprecated
    public DashMediaSource(Uri uri, com.google.android.exoplayer2.upstream.DataSource.Factory factory, com.google.android.exoplayer2.source.dash.DashChunkSource.Factory factory2, Handler handler2, MediaSourceEventListener mediaSourceEventListener) {
        this(uri, factory, factory2, 3, -1, handler2, mediaSourceEventListener);
    }

    @Deprecated
    public DashMediaSource(Uri uri, com.google.android.exoplayer2.upstream.DataSource.Factory factory, com.google.android.exoplayer2.source.dash.DashChunkSource.Factory factory2, int i, long j, Handler handler2, MediaSourceEventListener mediaSourceEventListener) {
        this(uri, factory, new DashManifestParser(), factory2, i, j, handler2, mediaSourceEventListener);
    }

    @Deprecated
    public DashMediaSource(Uri uri, com.google.android.exoplayer2.upstream.DataSource.Factory factory, Parser<? extends DashManifest> parser, com.google.android.exoplayer2.source.dash.DashChunkSource.Factory factory2, int i, long j, Handler handler2, MediaSourceEventListener mediaSourceEventListener) {
        Handler handler3 = handler2;
        MediaSourceEventListener mediaSourceEventListener2 = mediaSourceEventListener;
        this(null, uri, factory, parser, factory2, new DefaultCompositeSequenceableLoaderFactory(), CC.getDummyDrmSessionManager(), new DefaultLoadErrorHandlingPolicy(i), j == -1 ? 30000 : j, j != -1, null);
        if (handler3 == null || mediaSourceEventListener2 == null) {
            return;
        }
        addEventListener(handler3, mediaSourceEventListener2);
    }

    private DashMediaSource(DashManifest dashManifest, Uri uri, com.google.android.exoplayer2.upstream.DataSource.Factory factory, Parser<? extends DashManifest> parser, com.google.android.exoplayer2.source.dash.DashChunkSource.Factory factory2, CompositeSequenceableLoaderFactory compositeSequenceableLoaderFactory2, DrmSessionManager<?> drmSessionManager2, LoadErrorHandlingPolicy loadErrorHandlingPolicy2, long j, boolean z, Object obj) {
        this.initialManifestUri = uri;
        this.manifest = dashManifest;
        this.manifestUri = uri;
        this.manifestDataSourceFactory = factory;
        this.manifestParser = parser;
        this.chunkSourceFactory = factory2;
        this.drmSessionManager = drmSessionManager2;
        this.loadErrorHandlingPolicy = loadErrorHandlingPolicy2;
        this.livePresentationDelayMs = j;
        this.livePresentationDelayOverridesManifest = z;
        this.compositeSequenceableLoaderFactory = compositeSequenceableLoaderFactory2;
        this.tag = obj;
        this.sideloadedManifest = dashManifest != null;
        this.manifestEventDispatcher = createEventDispatcher(null);
        this.manifestUriLock = new Object();
        this.periodsById = new SparseArray<>();
        this.playerEmsgCallback = new DefaultPlayerEmsgCallback();
        this.expiredManifestPublishTimeUs = C1996C.TIME_UNSET;
        if (this.sideloadedManifest) {
            Assertions.checkState(!dashManifest.dynamic);
            this.manifestCallback = null;
            this.refreshManifestRunnable = null;
            this.simulateManifestRefreshRunnable = null;
            this.manifestLoadErrorThrower = new Dummy();
            return;
        }
        this.manifestCallback = new ManifestCallback();
        this.manifestLoadErrorThrower = new ManifestLoadErrorThrower();
        this.refreshManifestRunnable = new Runnable() {
            public final void run() {
                DashMediaSource.this.startLoadingManifest();
            }
        };
        this.simulateManifestRefreshRunnable = new Runnable() {
            public final void run() {
                DashMediaSource.this.lambda$new$0$DashMediaSource();
            }
        };
    }

    public /* synthetic */ void lambda$new$0$DashMediaSource() {
        processManifest(false);
    }

    public void replaceManifestUri(Uri uri) {
        synchronized (this.manifestUriLock) {
            this.manifestUri = uri;
            this.initialManifestUri = uri;
        }
    }

    public Object getTag() {
        return this.tag;
    }

    /* access modifiers changed from: protected */
    public void prepareSourceInternal(TransferListener transferListener) {
        this.mediaTransferListener = transferListener;
        this.drmSessionManager.prepare();
        if (this.sideloadedManifest) {
            processManifest(false);
            return;
        }
        this.dataSource = this.manifestDataSourceFactory.createDataSource();
        this.loader = new Loader("Loader:DashMediaSource");
        this.handler = new Handler();
        startLoadingManifest();
    }

    public void maybeThrowSourceInfoRefreshError() throws IOException {
        this.manifestLoadErrorThrower.maybeThrowError();
    }

    public MediaPeriod createPeriod(MediaPeriodId mediaPeriodId, Allocator allocator, long j) {
        MediaPeriodId mediaPeriodId2 = mediaPeriodId;
        int intValue = ((Integer) mediaPeriodId2.periodUid).intValue() - this.firstPeriodId;
        EventDispatcher createEventDispatcher = createEventDispatcher(mediaPeriodId2, this.manifest.getPeriod(intValue).startMs);
        DashMediaPeriod dashMediaPeriod = new DashMediaPeriod(this.firstPeriodId + intValue, this.manifest, intValue, this.chunkSourceFactory, this.mediaTransferListener, this.drmSessionManager, this.loadErrorHandlingPolicy, createEventDispatcher, this.elapsedRealtimeOffsetMs, this.manifestLoadErrorThrower, allocator, this.compositeSequenceableLoaderFactory, this.playerEmsgCallback);
        this.periodsById.put(dashMediaPeriod.f1519id, dashMediaPeriod);
        return dashMediaPeriod;
    }

    public void releasePeriod(MediaPeriod mediaPeriod) {
        DashMediaPeriod dashMediaPeriod = (DashMediaPeriod) mediaPeriod;
        dashMediaPeriod.release();
        this.periodsById.remove(dashMediaPeriod.f1519id);
    }

    /* access modifiers changed from: protected */
    public void releaseSourceInternal() {
        this.manifestLoadPending = false;
        this.dataSource = null;
        Loader loader2 = this.loader;
        if (loader2 != null) {
            loader2.release();
            this.loader = null;
        }
        this.manifestLoadStartTimestampMs = 0;
        this.manifestLoadEndTimestampMs = 0;
        this.manifest = this.sideloadedManifest ? this.manifest : null;
        this.manifestUri = this.initialManifestUri;
        this.manifestFatalError = null;
        Handler handler2 = this.handler;
        if (handler2 != null) {
            handler2.removeCallbacksAndMessages(null);
            this.handler = null;
        }
        this.elapsedRealtimeOffsetMs = 0;
        this.staleManifestReloadAttempt = 0;
        this.expiredManifestPublishTimeUs = C1996C.TIME_UNSET;
        this.firstPeriodId = 0;
        this.periodsById.clear();
        this.drmSessionManager.release();
    }

    /* access modifiers changed from: 0000 */
    public void onDashManifestRefreshRequested() {
        this.handler.removeCallbacks(this.simulateManifestRefreshRunnable);
        startLoadingManifest();
    }

    /* access modifiers changed from: 0000 */
    public void onDashManifestPublishTimeExpired(long j) {
        long j2 = this.expiredManifestPublishTimeUs;
        if (j2 == C1996C.TIME_UNSET || j2 < j) {
            this.expiredManifestPublishTimeUs = j;
        }
    }

    /* access modifiers changed from: 0000 */
    /* JADX WARNING: Removed duplicated region for block: B:22:0x009a  */
    /* JADX WARNING: Removed duplicated region for block: B:27:0x00ba  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void onManifestLoadCompleted(com.google.android.exoplayer2.upstream.ParsingLoadable<com.google.android.exoplayer2.source.dash.manifest.DashManifest> r16, long r17, long r19) {
        /*
            r15 = this;
            r1 = r15
            r0 = r16
            r13 = r17
            com.google.android.exoplayer2.source.MediaSourceEventListener$EventDispatcher r2 = r1.manifestEventDispatcher
            com.google.android.exoplayer2.upstream.DataSpec r3 = r0.dataSpec
            android.net.Uri r4 = r16.getUri()
            java.util.Map r5 = r16.getResponseHeaders()
            int r6 = r0.type
            long r11 = r16.bytesLoaded()
            r7 = r17
            r9 = r19
            r2.loadCompleted(r3, r4, r5, r6, r7, r9, r11)
            java.lang.Object r2 = r16.getResult()
            com.google.android.exoplayer2.source.dash.manifest.DashManifest r2 = (com.google.android.exoplayer2.source.dash.manifest.DashManifest) r2
            com.google.android.exoplayer2.source.dash.manifest.DashManifest r3 = r1.manifest
            r4 = 0
            if (r3 != 0) goto L_0x002b
            r3 = 0
            goto L_0x002f
        L_0x002b:
            int r3 = r3.getPeriodCount()
        L_0x002f:
            com.google.android.exoplayer2.source.dash.manifest.Period r5 = r2.getPeriod(r4)
            long r5 = r5.startMs
            r7 = 0
        L_0x0036:
            if (r7 >= r3) goto L_0x0047
            com.google.android.exoplayer2.source.dash.manifest.DashManifest r8 = r1.manifest
            com.google.android.exoplayer2.source.dash.manifest.Period r8 = r8.getPeriod(r7)
            long r8 = r8.startMs
            int r10 = (r8 > r5 ? 1 : (r8 == r5 ? 0 : -1))
            if (r10 >= 0) goto L_0x0047
            int r7 = r7 + 1
            goto L_0x0036
        L_0x0047:
            boolean r5 = r2.dynamic
            r6 = 1
            if (r5 == 0) goto L_0x00bc
            int r5 = r3 - r7
            int r8 = r2.getPeriodCount()
            if (r5 <= r8) goto L_0x005d
            java.lang.String r5 = "DashMediaSource"
            java.lang.String r8 = "Loaded out of sync manifest"
            com.google.android.exoplayer2.util.Log.m1396w(r5, r8)
        L_0x005b:
            r5 = 1
            goto L_0x0098
        L_0x005d:
            long r8 = r1.expiredManifestPublishTimeUs
            r10 = -9223372036854775807(0x8000000000000001, double:-4.9E-324)
            int r5 = (r8 > r10 ? 1 : (r8 == r10 ? 0 : -1))
            if (r5 == 0) goto L_0x0097
            long r8 = r2.publishTimeMs
            r10 = 1000(0x3e8, double:4.94E-321)
            long r8 = r8 * r10
            long r10 = r1.expiredManifestPublishTimeUs
            int r5 = (r8 > r10 ? 1 : (r8 == r10 ? 0 : -1))
            if (r5 > 0) goto L_0x0097
            java.lang.StringBuilder r5 = new java.lang.StringBuilder
            r5.<init>()
            java.lang.String r8 = "Loaded stale dynamic manifest: "
            r5.append(r8)
            long r8 = r2.publishTimeMs
            r5.append(r8)
            java.lang.String r8 = ", "
            r5.append(r8)
            long r8 = r1.expiredManifestPublishTimeUs
            r5.append(r8)
            java.lang.String r5 = r5.toString()
            java.lang.String r8 = "DashMediaSource"
            com.google.android.exoplayer2.util.Log.m1396w(r8, r5)
            goto L_0x005b
        L_0x0097:
            r5 = 0
        L_0x0098:
            if (r5 == 0) goto L_0x00ba
            int r2 = r1.staleManifestReloadAttempt
            int r3 = r2 + 1
            r1.staleManifestReloadAttempt = r3
            com.google.android.exoplayer2.upstream.LoadErrorHandlingPolicy r3 = r1.loadErrorHandlingPolicy
            int r0 = r0.type
            int r0 = r3.getMinimumLoadableRetryCount(r0)
            if (r2 >= r0) goto L_0x00b2
            long r2 = r15.getManifestLoadRetryDelayMillis()
            r15.scheduleManifestRefresh(r2)
            goto L_0x00b9
        L_0x00b2:
            com.google.android.exoplayer2.source.dash.DashManifestStaleException r0 = new com.google.android.exoplayer2.source.dash.DashManifestStaleException
            r0.<init>()
            r1.manifestFatalError = r0
        L_0x00b9:
            return
        L_0x00ba:
            r1.staleManifestReloadAttempt = r4
        L_0x00bc:
            r1.manifest = r2
            boolean r2 = r1.manifestLoadPending
            com.google.android.exoplayer2.source.dash.manifest.DashManifest r5 = r1.manifest
            boolean r5 = r5.dynamic
            r2 = r2 & r5
            r1.manifestLoadPending = r2
            long r8 = r13 - r19
            r1.manifestLoadStartTimestampMs = r8
            r1.manifestLoadEndTimestampMs = r13
            com.google.android.exoplayer2.source.dash.manifest.DashManifest r2 = r1.manifest
            android.net.Uri r2 = r2.location
            if (r2 == 0) goto L_0x00ec
            java.lang.Object r2 = r1.manifestUriLock
            monitor-enter(r2)
            com.google.android.exoplayer2.upstream.DataSpec r0 = r0.dataSpec     // Catch:{ all -> 0x00e9 }
            android.net.Uri r0 = r0.uri     // Catch:{ all -> 0x00e9 }
            android.net.Uri r5 = r1.manifestUri     // Catch:{ all -> 0x00e9 }
            if (r0 != r5) goto L_0x00df
            r4 = 1
        L_0x00df:
            if (r4 == 0) goto L_0x00e7
            com.google.android.exoplayer2.source.dash.manifest.DashManifest r0 = r1.manifest     // Catch:{ all -> 0x00e9 }
            android.net.Uri r0 = r0.location     // Catch:{ all -> 0x00e9 }
            r1.manifestUri = r0     // Catch:{ all -> 0x00e9 }
        L_0x00e7:
            monitor-exit(r2)     // Catch:{ all -> 0x00e9 }
            goto L_0x00ec
        L_0x00e9:
            r0 = move-exception
            monitor-exit(r2)     // Catch:{ all -> 0x00e9 }
            throw r0
        L_0x00ec:
            if (r3 != 0) goto L_0x0106
            com.google.android.exoplayer2.source.dash.manifest.DashManifest r0 = r1.manifest
            boolean r0 = r0.dynamic
            if (r0 == 0) goto L_0x0102
            com.google.android.exoplayer2.source.dash.manifest.DashManifest r0 = r1.manifest
            com.google.android.exoplayer2.source.dash.manifest.UtcTimingElement r0 = r0.utcTiming
            if (r0 == 0) goto L_0x0102
            com.google.android.exoplayer2.source.dash.manifest.DashManifest r0 = r1.manifest
            com.google.android.exoplayer2.source.dash.manifest.UtcTimingElement r0 = r0.utcTiming
            r15.resolveUtcTimingElement(r0)
            goto L_0x010e
        L_0x0102:
            r15.processManifest(r6)
            goto L_0x010e
        L_0x0106:
            int r0 = r1.firstPeriodId
            int r0 = r0 + r7
            r1.firstPeriodId = r0
            r15.processManifest(r6)
        L_0x010e:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.exoplayer2.source.dash.DashMediaSource.onManifestLoadCompleted(com.google.android.exoplayer2.upstream.ParsingLoadable, long, long):void");
    }

    /* access modifiers changed from: 0000 */
    public LoadErrorAction onManifestLoadError(ParsingLoadable<DashManifest> parsingLoadable, long j, long j2, IOException iOException, int i) {
        LoadErrorAction loadErrorAction;
        ParsingLoadable<DashManifest> parsingLoadable2 = parsingLoadable;
        long retryDelayMsFor = this.loadErrorHandlingPolicy.getRetryDelayMsFor(4, j2, iOException, i);
        if (retryDelayMsFor == C1996C.TIME_UNSET) {
            loadErrorAction = Loader.DONT_RETRY_FATAL;
        } else {
            loadErrorAction = Loader.createRetryAction(false, retryDelayMsFor);
        }
        this.manifestEventDispatcher.loadError(parsingLoadable2.dataSpec, parsingLoadable.getUri(), parsingLoadable.getResponseHeaders(), parsingLoadable2.type, j, j2, parsingLoadable.bytesLoaded(), iOException, !loadErrorAction.isRetry());
        return loadErrorAction;
    }

    /* access modifiers changed from: 0000 */
    public void onUtcTimestampLoadCompleted(ParsingLoadable<Long> parsingLoadable, long j, long j2) {
        ParsingLoadable<Long> parsingLoadable2 = parsingLoadable;
        this.manifestEventDispatcher.loadCompleted(parsingLoadable2.dataSpec, parsingLoadable.getUri(), parsingLoadable.getResponseHeaders(), parsingLoadable2.type, j, j2, parsingLoadable.bytesLoaded());
        onUtcTimestampResolved(((Long) parsingLoadable.getResult()).longValue() - j);
    }

    /* access modifiers changed from: 0000 */
    public LoadErrorAction onUtcTimestampLoadError(ParsingLoadable<Long> parsingLoadable, long j, long j2, IOException iOException) {
        ParsingLoadable<Long> parsingLoadable2 = parsingLoadable;
        EventDispatcher eventDispatcher = this.manifestEventDispatcher;
        DataSpec dataSpec = parsingLoadable2.dataSpec;
        Uri uri = parsingLoadable.getUri();
        Map responseHeaders = parsingLoadable.getResponseHeaders();
        int i = parsingLoadable2.type;
        eventDispatcher.loadError(dataSpec, uri, responseHeaders, i, j, j2, parsingLoadable.bytesLoaded(), iOException, true);
        onUtcTimestampResolutionError(iOException);
        return Loader.DONT_RETRY;
    }

    /* access modifiers changed from: 0000 */
    public void onLoadCanceled(ParsingLoadable<?> parsingLoadable, long j, long j2) {
        ParsingLoadable<?> parsingLoadable2 = parsingLoadable;
        this.manifestEventDispatcher.loadCanceled(parsingLoadable2.dataSpec, parsingLoadable.getUri(), parsingLoadable.getResponseHeaders(), parsingLoadable2.type, j, j2, parsingLoadable.bytesLoaded());
    }

    private void resolveUtcTimingElement(UtcTimingElement utcTimingElement) {
        String str = utcTimingElement.schemeIdUri;
        if (Util.areEqual(str, "urn:mpeg:dash:utc:direct:2014") || Util.areEqual(str, "urn:mpeg:dash:utc:direct:2012")) {
            resolveUtcTimingElementDirect(utcTimingElement);
        } else if (Util.areEqual(str, "urn:mpeg:dash:utc:http-iso:2014") || Util.areEqual(str, "urn:mpeg:dash:utc:http-iso:2012")) {
            resolveUtcTimingElementHttp(utcTimingElement, new Iso8601Parser());
        } else if (Util.areEqual(str, "urn:mpeg:dash:utc:http-xsdate:2014") || Util.areEqual(str, "urn:mpeg:dash:utc:http-xsdate:2012")) {
            resolveUtcTimingElementHttp(utcTimingElement, new XsDateTimeParser());
        } else {
            onUtcTimestampResolutionError(new IOException("Unsupported UTC timing scheme"));
        }
    }

    private void resolveUtcTimingElementDirect(UtcTimingElement utcTimingElement) {
        try {
            onUtcTimestampResolved(Util.parseXsDateTime(utcTimingElement.value) - this.manifestLoadEndTimestampMs);
        } catch (ParserException e) {
            onUtcTimestampResolutionError(e);
        }
    }

    private void resolveUtcTimingElementHttp(UtcTimingElement utcTimingElement, Parser<Long> parser) {
        startLoading(new ParsingLoadable(this.dataSource, Uri.parse(utcTimingElement.value), 5, parser), new UtcTimestampCallback(), 1);
    }

    private void onUtcTimestampResolved(long j) {
        this.elapsedRealtimeOffsetMs = j;
        processManifest(true);
    }

    private void onUtcTimestampResolutionError(IOException iOException) {
        Log.m1393e(TAG, "Failed to resolve UtcTiming element.", iOException);
        processManifest(true);
    }

    private void processManifest(boolean z) {
        long j;
        boolean z2;
        long j2;
        long j3;
        for (int i = 0; i < this.periodsById.size(); i++) {
            int keyAt = this.periodsById.keyAt(i);
            if (keyAt >= this.firstPeriodId) {
                ((DashMediaPeriod) this.periodsById.valueAt(i)).updateManifest(this.manifest, keyAt - this.firstPeriodId);
            }
        }
        int periodCount = this.manifest.getPeriodCount() - 1;
        PeriodSeekInfo createPeriodSeekInfo = PeriodSeekInfo.createPeriodSeekInfo(this.manifest.getPeriod(0), this.manifest.getPeriodDurationUs(0));
        PeriodSeekInfo createPeriodSeekInfo2 = PeriodSeekInfo.createPeriodSeekInfo(this.manifest.getPeriod(periodCount), this.manifest.getPeriodDurationUs(periodCount));
        long j4 = createPeriodSeekInfo.availableStartTimeUs;
        long j5 = createPeriodSeekInfo2.availableEndTimeUs;
        if (!this.manifest.dynamic || createPeriodSeekInfo2.isIndexExplicit) {
            j = j4;
            z2 = false;
        } else {
            j5 = Math.min((getNowUnixTimeUs() - C1996C.msToUs(this.manifest.availabilityStartTimeMs)) - C1996C.msToUs(this.manifest.getPeriod(periodCount).startMs), j5);
            if (this.manifest.timeShiftBufferDepthMs != C1996C.TIME_UNSET) {
                long msToUs = j5 - C1996C.msToUs(this.manifest.timeShiftBufferDepthMs);
                while (msToUs < 0 && periodCount > 0) {
                    periodCount--;
                    msToUs += this.manifest.getPeriodDurationUs(periodCount);
                }
                if (periodCount == 0) {
                    j3 = Math.max(j4, msToUs);
                } else {
                    j3 = this.manifest.getPeriodDurationUs(0);
                }
                j4 = j3;
            }
            j = j4;
            z2 = true;
        }
        long j6 = j5 - j;
        for (int i2 = 0; i2 < this.manifest.getPeriodCount() - 1; i2++) {
            j6 += this.manifest.getPeriodDurationUs(i2);
        }
        if (this.manifest.dynamic) {
            long j7 = this.livePresentationDelayMs;
            if (!this.livePresentationDelayOverridesManifest && this.manifest.suggestedPresentationDelayMs != C1996C.TIME_UNSET) {
                j7 = this.manifest.suggestedPresentationDelayMs;
            }
            long msToUs2 = j6 - C1996C.msToUs(j7);
            if (msToUs2 < MIN_LIVE_DEFAULT_START_POSITION_US) {
                msToUs2 = Math.min(MIN_LIVE_DEFAULT_START_POSITION_US, j6 / 2);
            }
            j2 = msToUs2;
        } else {
            j2 = 0;
        }
        long usToMs = this.manifest.availabilityStartTimeMs + this.manifest.getPeriod(0).startMs + C1996C.usToMs(j);
        DashTimeline dashTimeline = new DashTimeline(this.manifest.availabilityStartTimeMs, usToMs, this.firstPeriodId, j, j6, j2, this.manifest, this.tag);
        refreshSourceInfo(dashTimeline);
        if (!this.sideloadedManifest) {
            this.handler.removeCallbacks(this.simulateManifestRefreshRunnable);
            if (z2) {
                this.handler.postDelayed(this.simulateManifestRefreshRunnable, DefaultRenderersFactory.DEFAULT_ALLOWED_VIDEO_JOINING_TIME_MS);
            }
            if (this.manifestLoadPending) {
                startLoadingManifest();
            } else if (z && this.manifest.dynamic && this.manifest.minUpdatePeriodMs != C1996C.TIME_UNSET) {
                long j8 = this.manifest.minUpdatePeriodMs;
                if (j8 == 0) {
                    j8 = 5000;
                }
                scheduleManifestRefresh(Math.max(0, (this.manifestLoadStartTimestampMs + j8) - SystemClock.elapsedRealtime()));
            }
        }
    }

    private void scheduleManifestRefresh(long j) {
        this.handler.postDelayed(this.refreshManifestRunnable, j);
    }

    /* access modifiers changed from: private */
    public void startLoadingManifest() {
        Uri uri;
        this.handler.removeCallbacks(this.refreshManifestRunnable);
        if (!this.loader.hasFatalError()) {
            if (this.loader.isLoading()) {
                this.manifestLoadPending = true;
                return;
            }
            synchronized (this.manifestUriLock) {
                uri = this.manifestUri;
            }
            this.manifestLoadPending = false;
            startLoading(new ParsingLoadable(this.dataSource, uri, 4, this.manifestParser), this.manifestCallback, this.loadErrorHandlingPolicy.getMinimumLoadableRetryCount(4));
        }
    }

    private long getManifestLoadRetryDelayMillis() {
        return (long) Math.min((this.staleManifestReloadAttempt - 1) * 1000, 5000);
    }

    private <T> void startLoading(ParsingLoadable<T> parsingLoadable, Callback<ParsingLoadable<T>> callback, int i) {
        this.manifestEventDispatcher.loadStarted(parsingLoadable.dataSpec, parsingLoadable.type, this.loader.startLoading(parsingLoadable, callback, i));
    }

    private long getNowUnixTimeUs() {
        if (this.elapsedRealtimeOffsetMs != 0) {
            return C1996C.msToUs(SystemClock.elapsedRealtime() + this.elapsedRealtimeOffsetMs);
        }
        return C1996C.msToUs(System.currentTimeMillis());
    }
}
