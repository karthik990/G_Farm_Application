package com.google.android.exoplayer2.p041ui;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.os.Looper;
import android.os.SystemClock;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import com.google.android.exoplayer2.C1996C;
import com.google.android.exoplayer2.ControlDispatcher;
import com.google.android.exoplayer2.DefaultControlDispatcher;
import com.google.android.exoplayer2.ExoPlaybackException;
import com.google.android.exoplayer2.ExoPlayerLibraryInfo;
import com.google.android.exoplayer2.PlaybackParameters;
import com.google.android.exoplayer2.PlaybackPreparer;
import com.google.android.exoplayer2.Player;
import com.google.android.exoplayer2.Player.EventListener;
import com.google.android.exoplayer2.Player.EventListener.CC;
import com.google.android.exoplayer2.Timeline;
import com.google.android.exoplayer2.Timeline.Period;
import com.google.android.exoplayer2.Timeline.Window;
import com.google.android.exoplayer2.p041ui.TimeBar.OnScrubListener;
import com.google.android.exoplayer2.source.TrackGroupArray;
import com.google.android.exoplayer2.trackselection.TrackSelectionArray;
import com.google.android.exoplayer2.util.Assertions;
import com.google.android.exoplayer2.util.RepeatModeUtil;
import com.google.android.exoplayer2.util.Util;
import java.util.Arrays;
import java.util.Formatter;
import java.util.Iterator;
import java.util.Locale;
import java.util.concurrent.CopyOnWriteArrayList;

/* renamed from: com.google.android.exoplayer2.ui.PlayerControlView */
public class PlayerControlView extends FrameLayout {
    public static final int DEFAULT_FAST_FORWARD_MS = 15000;
    public static final int DEFAULT_REPEAT_TOGGLE_MODES = 0;
    public static final int DEFAULT_REWIND_MS = 5000;
    public static final int DEFAULT_SHOW_TIMEOUT_MS = 5000;
    public static final int DEFAULT_TIME_BAR_MIN_UPDATE_INTERVAL_MS = 200;
    private static final long MAX_POSITION_FOR_SEEK_TO_PREVIOUS = 3000;
    private static final int MAX_UPDATE_INTERVAL_MS = 1000;
    public static final int MAX_WINDOWS_FOR_MULTI_WINDOW_TIME_BAR = 100;
    private long[] adGroupTimesMs;
    private final float buttonAlphaDisabled;
    private final float buttonAlphaEnabled;
    private final ComponentListener componentListener;
    /* access modifiers changed from: private */
    public ControlDispatcher controlDispatcher;
    private long currentWindowOffset;
    private final TextView durationView;
    private long[] extraAdGroupTimesMs;
    private boolean[] extraPlayedAdGroups;
    /* access modifiers changed from: private */
    public final View fastForwardButton;
    private int fastForwardMs;
    /* access modifiers changed from: private */
    public final StringBuilder formatBuilder;
    /* access modifiers changed from: private */
    public final Formatter formatter;
    private final Runnable hideAction;
    private long hideAtMs;
    private boolean isAttachedToWindow;
    private boolean multiWindowTimeBar;
    /* access modifiers changed from: private */
    public final View nextButton;
    /* access modifiers changed from: private */
    public final View pauseButton;
    private final Period period;
    /* access modifiers changed from: private */
    public final View playButton;
    /* access modifiers changed from: private */
    public PlaybackPreparer playbackPreparer;
    private boolean[] playedAdGroups;
    /* access modifiers changed from: private */
    public Player player;
    /* access modifiers changed from: private */
    public final TextView positionView;
    /* access modifiers changed from: private */
    public final View previousButton;
    private ProgressUpdateListener progressUpdateListener;
    private final String repeatAllButtonContentDescription;
    private final Drawable repeatAllButtonDrawable;
    private final String repeatOffButtonContentDescription;
    private final Drawable repeatOffButtonDrawable;
    private final String repeatOneButtonContentDescription;
    private final Drawable repeatOneButtonDrawable;
    /* access modifiers changed from: private */
    public final ImageView repeatToggleButton;
    /* access modifiers changed from: private */
    public int repeatToggleModes;
    /* access modifiers changed from: private */
    public final View rewindButton;
    private int rewindMs;
    /* access modifiers changed from: private */
    public boolean scrubbing;
    private boolean showMultiWindowTimeBar;
    private boolean showShuffleButton;
    private int showTimeoutMs;
    /* access modifiers changed from: private */
    public final ImageView shuffleButton;
    private final Drawable shuffleOffButtonDrawable;
    private final String shuffleOffContentDescription;
    private final Drawable shuffleOnButtonDrawable;
    private final String shuffleOnContentDescription;
    private final TimeBar timeBar;
    private int timeBarMinUpdateIntervalMs;
    private final Runnable updateProgressAction;
    private final CopyOnWriteArrayList<VisibilityListener> visibilityListeners;
    private final View vrButton;
    private final Window window;

    /* renamed from: com.google.android.exoplayer2.ui.PlayerControlView$ComponentListener */
    private final class ComponentListener implements EventListener, OnScrubListener, OnClickListener {
        public /* synthetic */ void onLoadingChanged(boolean z) {
            CC.$default$onLoadingChanged(this, z);
        }

        public /* synthetic */ void onPlaybackParametersChanged(PlaybackParameters playbackParameters) {
            CC.$default$onPlaybackParametersChanged(this, playbackParameters);
        }

        public /* synthetic */ void onPlaybackSuppressionReasonChanged(int i) {
            CC.$default$onPlaybackSuppressionReasonChanged(this, i);
        }

        public /* synthetic */ void onPlayerError(ExoPlaybackException exoPlaybackException) {
            CC.$default$onPlayerError(this, exoPlaybackException);
        }

        public /* synthetic */ void onSeekProcessed() {
            CC.$default$onSeekProcessed(this);
        }

        @Deprecated
        public /* synthetic */ void onTimelineChanged(Timeline timeline, Object obj, int i) {
            CC.$default$onTimelineChanged(this, timeline, obj, i);
        }

        public /* synthetic */ void onTracksChanged(TrackGroupArray trackGroupArray, TrackSelectionArray trackSelectionArray) {
            CC.$default$onTracksChanged(this, trackGroupArray, trackSelectionArray);
        }

        private ComponentListener() {
        }

        public void onScrubStart(TimeBar timeBar, long j) {
            PlayerControlView.this.scrubbing = true;
            if (PlayerControlView.this.positionView != null) {
                PlayerControlView.this.positionView.setText(Util.getStringForTime(PlayerControlView.this.formatBuilder, PlayerControlView.this.formatter, j));
            }
        }

        public void onScrubMove(TimeBar timeBar, long j) {
            if (PlayerControlView.this.positionView != null) {
                PlayerControlView.this.positionView.setText(Util.getStringForTime(PlayerControlView.this.formatBuilder, PlayerControlView.this.formatter, j));
            }
        }

        public void onScrubStop(TimeBar timeBar, long j, boolean z) {
            PlayerControlView.this.scrubbing = false;
            if (!z && PlayerControlView.this.player != null) {
                PlayerControlView playerControlView = PlayerControlView.this;
                playerControlView.seekToTimeBarPosition(playerControlView.player, j);
            }
        }

        public void onPlayerStateChanged(boolean z, int i) {
            PlayerControlView.this.updatePlayPauseButton();
            PlayerControlView.this.updateProgress();
        }

        public void onIsPlayingChanged(boolean z) {
            PlayerControlView.this.updateProgress();
        }

        public void onRepeatModeChanged(int i) {
            PlayerControlView.this.updateRepeatModeButton();
            PlayerControlView.this.updateNavigation();
        }

        public void onShuffleModeEnabledChanged(boolean z) {
            PlayerControlView.this.updateShuffleButton();
            PlayerControlView.this.updateNavigation();
        }

        public void onPositionDiscontinuity(int i) {
            PlayerControlView.this.updateNavigation();
            PlayerControlView.this.updateTimeline();
        }

        public void onTimelineChanged(Timeline timeline, int i) {
            PlayerControlView.this.updateNavigation();
            PlayerControlView.this.updateTimeline();
        }

        public void onClick(View view) {
            Player access$500 = PlayerControlView.this.player;
            if (access$500 != null) {
                if (PlayerControlView.this.nextButton == view) {
                    PlayerControlView.this.next(access$500);
                } else if (PlayerControlView.this.previousButton == view) {
                    PlayerControlView.this.previous(access$500);
                } else if (PlayerControlView.this.fastForwardButton == view) {
                    PlayerControlView.this.fastForward(access$500);
                } else if (PlayerControlView.this.rewindButton == view) {
                    PlayerControlView.this.rewind(access$500);
                } else if (PlayerControlView.this.playButton == view) {
                    if (access$500.getPlaybackState() == 1) {
                        if (PlayerControlView.this.playbackPreparer != null) {
                            PlayerControlView.this.playbackPreparer.preparePlayback();
                        }
                    } else if (access$500.getPlaybackState() == 4) {
                        PlayerControlView.this.seekTo(access$500, access$500.getCurrentWindowIndex(), C1996C.TIME_UNSET);
                    }
                    PlayerControlView.this.controlDispatcher.dispatchSetPlayWhenReady(access$500, true);
                } else if (PlayerControlView.this.pauseButton == view) {
                    PlayerControlView.this.controlDispatcher.dispatchSetPlayWhenReady(access$500, false);
                } else if (PlayerControlView.this.repeatToggleButton == view) {
                    PlayerControlView.this.controlDispatcher.dispatchSetRepeatMode(access$500, RepeatModeUtil.getNextRepeatMode(access$500.getRepeatMode(), PlayerControlView.this.repeatToggleModes));
                } else if (PlayerControlView.this.shuffleButton == view) {
                    PlayerControlView.this.controlDispatcher.dispatchSetShuffleModeEnabled(access$500, !access$500.getShuffleModeEnabled());
                }
            }
        }
    }

    /* renamed from: com.google.android.exoplayer2.ui.PlayerControlView$ProgressUpdateListener */
    public interface ProgressUpdateListener {
        void onProgressUpdate(long j, long j2);
    }

    /* renamed from: com.google.android.exoplayer2.ui.PlayerControlView$VisibilityListener */
    public interface VisibilityListener {
        void onVisibilityChange(int i);
    }

    private static boolean isHandledMediaKey(int i) {
        return i == 90 || i == 89 || i == 85 || i == 126 || i == 127 || i == 87 || i == 88;
    }

    static {
        ExoPlayerLibraryInfo.registerModule("goog.exo.ui");
    }

    public PlayerControlView(Context context) {
        this(context, null);
    }

    public PlayerControlView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public PlayerControlView(Context context, AttributeSet attributeSet, int i) {
        this(context, attributeSet, i, attributeSet);
    }

    public PlayerControlView(Context context, AttributeSet attributeSet, int i, AttributeSet attributeSet2) {
        super(context, attributeSet, i);
        int i2 = C2489R.layout.exo_player_control_view;
        this.rewindMs = 5000;
        this.fastForwardMs = 15000;
        this.showTimeoutMs = 5000;
        this.repeatToggleModes = 0;
        this.timeBarMinUpdateIntervalMs = 200;
        this.hideAtMs = C1996C.TIME_UNSET;
        this.showShuffleButton = false;
        if (attributeSet2 != null) {
            TypedArray obtainStyledAttributes = context.getTheme().obtainStyledAttributes(attributeSet2, C2489R.styleable.PlayerControlView, 0, 0);
            try {
                this.rewindMs = obtainStyledAttributes.getInt(C2489R.styleable.PlayerControlView_rewind_increment, this.rewindMs);
                this.fastForwardMs = obtainStyledAttributes.getInt(C2489R.styleable.PlayerControlView_fastforward_increment, this.fastForwardMs);
                this.showTimeoutMs = obtainStyledAttributes.getInt(C2489R.styleable.PlayerControlView_show_timeout, this.showTimeoutMs);
                i2 = obtainStyledAttributes.getResourceId(C2489R.styleable.PlayerControlView_controller_layout_id, i2);
                this.repeatToggleModes = getRepeatToggleModes(obtainStyledAttributes, this.repeatToggleModes);
                this.showShuffleButton = obtainStyledAttributes.getBoolean(C2489R.styleable.PlayerControlView_show_shuffle_button, this.showShuffleButton);
                setTimeBarMinUpdateInterval(obtainStyledAttributes.getInt(C2489R.styleable.PlayerControlView_time_bar_min_update_interval, this.timeBarMinUpdateIntervalMs));
            } finally {
                obtainStyledAttributes.recycle();
            }
        }
        this.visibilityListeners = new CopyOnWriteArrayList<>();
        this.period = new Period();
        this.window = new Window();
        this.formatBuilder = new StringBuilder();
        this.formatter = new Formatter(this.formatBuilder, Locale.getDefault());
        this.adGroupTimesMs = new long[0];
        this.playedAdGroups = new boolean[0];
        this.extraAdGroupTimesMs = new long[0];
        this.extraPlayedAdGroups = new boolean[0];
        this.componentListener = new ComponentListener();
        this.controlDispatcher = new DefaultControlDispatcher();
        this.updateProgressAction = new Runnable() {
            public final void run() {
                PlayerControlView.this.updateProgress();
            }
        };
        this.hideAction = new Runnable() {
            public final void run() {
                PlayerControlView.this.hide();
            }
        };
        LayoutInflater.from(context).inflate(i2, this);
        setDescendantFocusability(262144);
        TimeBar timeBar2 = (TimeBar) findViewById(C2489R.C2492id.exo_progress);
        View findViewById = findViewById(C2489R.C2492id.exo_progress_placeholder);
        if (timeBar2 != null) {
            this.timeBar = timeBar2;
        } else if (findViewById != null) {
            DefaultTimeBar defaultTimeBar = new DefaultTimeBar(context, null, 0, attributeSet2);
            defaultTimeBar.setId(C2489R.C2492id.exo_progress);
            defaultTimeBar.setLayoutParams(findViewById.getLayoutParams());
            ViewGroup viewGroup = (ViewGroup) findViewById.getParent();
            int indexOfChild = viewGroup.indexOfChild(findViewById);
            viewGroup.removeView(findViewById);
            viewGroup.addView(defaultTimeBar, indexOfChild);
            this.timeBar = defaultTimeBar;
        } else {
            this.timeBar = null;
        }
        this.durationView = (TextView) findViewById(C2489R.C2492id.exo_duration);
        this.positionView = (TextView) findViewById(C2489R.C2492id.exo_position);
        TimeBar timeBar3 = this.timeBar;
        if (timeBar3 != null) {
            timeBar3.addListener(this.componentListener);
        }
        this.playButton = findViewById(C2489R.C2492id.exo_play);
        View view = this.playButton;
        if (view != null) {
            view.setOnClickListener(this.componentListener);
        }
        this.pauseButton = findViewById(C2489R.C2492id.exo_pause);
        View view2 = this.pauseButton;
        if (view2 != null) {
            view2.setOnClickListener(this.componentListener);
        }
        this.previousButton = findViewById(C2489R.C2492id.exo_prev);
        View view3 = this.previousButton;
        if (view3 != null) {
            view3.setOnClickListener(this.componentListener);
        }
        this.nextButton = findViewById(C2489R.C2492id.exo_next);
        View view4 = this.nextButton;
        if (view4 != null) {
            view4.setOnClickListener(this.componentListener);
        }
        this.rewindButton = findViewById(C2489R.C2492id.exo_rew);
        View view5 = this.rewindButton;
        if (view5 != null) {
            view5.setOnClickListener(this.componentListener);
        }
        this.fastForwardButton = findViewById(C2489R.C2492id.exo_ffwd);
        View view6 = this.fastForwardButton;
        if (view6 != null) {
            view6.setOnClickListener(this.componentListener);
        }
        this.repeatToggleButton = (ImageView) findViewById(C2489R.C2492id.exo_repeat_toggle);
        ImageView imageView = this.repeatToggleButton;
        if (imageView != null) {
            imageView.setOnClickListener(this.componentListener);
        }
        this.shuffleButton = (ImageView) findViewById(C2489R.C2492id.exo_shuffle);
        ImageView imageView2 = this.shuffleButton;
        if (imageView2 != null) {
            imageView2.setOnClickListener(this.componentListener);
        }
        this.vrButton = findViewById(C2489R.C2492id.exo_vr);
        setShowVrButton(false);
        Resources resources = context.getResources();
        this.buttonAlphaEnabled = ((float) resources.getInteger(C2489R.integer.exo_media_button_opacity_percentage_enabled)) / 100.0f;
        this.buttonAlphaDisabled = ((float) resources.getInteger(C2489R.integer.exo_media_button_opacity_percentage_disabled)) / 100.0f;
        this.repeatOffButtonDrawable = resources.getDrawable(C2489R.C2491drawable.exo_controls_repeat_off);
        this.repeatOneButtonDrawable = resources.getDrawable(C2489R.C2491drawable.exo_controls_repeat_one);
        this.repeatAllButtonDrawable = resources.getDrawable(C2489R.C2491drawable.exo_controls_repeat_all);
        this.shuffleOnButtonDrawable = resources.getDrawable(C2489R.C2491drawable.exo_controls_shuffle_on);
        this.shuffleOffButtonDrawable = resources.getDrawable(C2489R.C2491drawable.exo_controls_shuffle_off);
        this.repeatOffButtonContentDescription = resources.getString(C2489R.C2493string.exo_controls_repeat_off_description);
        this.repeatOneButtonContentDescription = resources.getString(C2489R.C2493string.exo_controls_repeat_one_description);
        this.repeatAllButtonContentDescription = resources.getString(C2489R.C2493string.exo_controls_repeat_all_description);
        this.shuffleOnContentDescription = resources.getString(C2489R.C2493string.exo_controls_shuffle_on_description);
        this.shuffleOffContentDescription = resources.getString(C2489R.C2493string.exo_controls_shuffle_off_description);
    }

    private static int getRepeatToggleModes(TypedArray typedArray, int i) {
        return typedArray.getInt(C2489R.styleable.PlayerControlView_repeat_toggle_modes, i);
    }

    public Player getPlayer() {
        return this.player;
    }

    public void setPlayer(Player player2) {
        boolean z = true;
        Assertions.checkState(Looper.myLooper() == Looper.getMainLooper());
        if (!(player2 == null || player2.getApplicationLooper() == Looper.getMainLooper())) {
            z = false;
        }
        Assertions.checkArgument(z);
        Player player3 = this.player;
        if (player3 != player2) {
            if (player3 != null) {
                player3.removeListener(this.componentListener);
            }
            this.player = player2;
            if (player2 != null) {
                player2.addListener(this.componentListener);
            }
            updateAll();
        }
    }

    public void setShowMultiWindowTimeBar(boolean z) {
        this.showMultiWindowTimeBar = z;
        updateTimeline();
    }

    public void setExtraAdGroupMarkers(long[] jArr, boolean[] zArr) {
        boolean z = false;
        if (jArr == null) {
            this.extraAdGroupTimesMs = new long[0];
            this.extraPlayedAdGroups = new boolean[0];
        } else {
            boolean[] zArr2 = (boolean[]) Assertions.checkNotNull(zArr);
            if (jArr.length == zArr2.length) {
                z = true;
            }
            Assertions.checkArgument(z);
            this.extraAdGroupTimesMs = jArr;
            this.extraPlayedAdGroups = zArr2;
        }
        updateTimeline();
    }

    public void addVisibilityListener(VisibilityListener visibilityListener) {
        this.visibilityListeners.add(visibilityListener);
    }

    public void removeVisibilityListener(VisibilityListener visibilityListener) {
        this.visibilityListeners.remove(visibilityListener);
    }

    public void setProgressUpdateListener(ProgressUpdateListener progressUpdateListener2) {
        this.progressUpdateListener = progressUpdateListener2;
    }

    public void setPlaybackPreparer(PlaybackPreparer playbackPreparer2) {
        this.playbackPreparer = playbackPreparer2;
    }

    public void setControlDispatcher(ControlDispatcher controlDispatcher2) {
        if (controlDispatcher2 == null) {
            controlDispatcher2 = new DefaultControlDispatcher();
        }
        this.controlDispatcher = controlDispatcher2;
    }

    public void setRewindIncrementMs(int i) {
        this.rewindMs = i;
        updateNavigation();
    }

    public void setFastForwardIncrementMs(int i) {
        this.fastForwardMs = i;
        updateNavigation();
    }

    public int getShowTimeoutMs() {
        return this.showTimeoutMs;
    }

    public void setShowTimeoutMs(int i) {
        this.showTimeoutMs = i;
        if (isVisible()) {
            hideAfterTimeout();
        }
    }

    public int getRepeatToggleModes() {
        return this.repeatToggleModes;
    }

    public void setRepeatToggleModes(int i) {
        this.repeatToggleModes = i;
        Player player2 = this.player;
        if (player2 != null) {
            int repeatMode = player2.getRepeatMode();
            if (i == 0 && repeatMode != 0) {
                this.controlDispatcher.dispatchSetRepeatMode(this.player, 0);
            } else if (i == 1 && repeatMode == 2) {
                this.controlDispatcher.dispatchSetRepeatMode(this.player, 1);
            } else if (i == 2 && repeatMode == 1) {
                this.controlDispatcher.dispatchSetRepeatMode(this.player, 2);
            }
        }
        updateRepeatModeButton();
    }

    public boolean getShowShuffleButton() {
        return this.showShuffleButton;
    }

    public void setShowShuffleButton(boolean z) {
        this.showShuffleButton = z;
        updateShuffleButton();
    }

    public boolean getShowVrButton() {
        View view = this.vrButton;
        return view != null && view.getVisibility() == 0;
    }

    public void setShowVrButton(boolean z) {
        View view = this.vrButton;
        if (view != null) {
            view.setVisibility(z ? 0 : 8);
        }
    }

    public void setVrButtonListener(OnClickListener onClickListener) {
        View view = this.vrButton;
        if (view != null) {
            view.setOnClickListener(onClickListener);
        }
    }

    public void setTimeBarMinUpdateInterval(int i) {
        this.timeBarMinUpdateIntervalMs = Util.constrainValue(i, 16, 1000);
    }

    public void show() {
        if (!isVisible()) {
            setVisibility(0);
            Iterator it = this.visibilityListeners.iterator();
            while (it.hasNext()) {
                ((VisibilityListener) it.next()).onVisibilityChange(getVisibility());
            }
            updateAll();
            requestPlayPauseFocus();
        }
        hideAfterTimeout();
    }

    public void hide() {
        if (isVisible()) {
            setVisibility(8);
            Iterator it = this.visibilityListeners.iterator();
            while (it.hasNext()) {
                ((VisibilityListener) it.next()).onVisibilityChange(getVisibility());
            }
            removeCallbacks(this.updateProgressAction);
            removeCallbacks(this.hideAction);
            this.hideAtMs = C1996C.TIME_UNSET;
        }
    }

    public boolean isVisible() {
        return getVisibility() == 0;
    }

    private void hideAfterTimeout() {
        removeCallbacks(this.hideAction);
        if (this.showTimeoutMs > 0) {
            long uptimeMillis = SystemClock.uptimeMillis();
            int i = this.showTimeoutMs;
            this.hideAtMs = uptimeMillis + ((long) i);
            if (this.isAttachedToWindow) {
                postDelayed(this.hideAction, (long) i);
                return;
            }
            return;
        }
        this.hideAtMs = C1996C.TIME_UNSET;
    }

    private void updateAll() {
        updatePlayPauseButton();
        updateNavigation();
        updateRepeatModeButton();
        updateShuffleButton();
        updateTimeline();
    }

    /* access modifiers changed from: private */
    public void updatePlayPauseButton() {
        boolean z;
        if (isVisible() && this.isAttachedToWindow) {
            boolean shouldShowPauseButton = shouldShowPauseButton();
            View view = this.playButton;
            int i = 8;
            boolean z2 = true;
            if (view != null) {
                z = (shouldShowPauseButton && view.isFocused()) | false;
                this.playButton.setVisibility(shouldShowPauseButton ? 8 : 0);
            } else {
                z = false;
            }
            View view2 = this.pauseButton;
            if (view2 != null) {
                if (shouldShowPauseButton || !view2.isFocused()) {
                    z2 = false;
                }
                z |= z2;
                View view3 = this.pauseButton;
                if (shouldShowPauseButton) {
                    i = 0;
                }
                view3.setVisibility(i);
            }
            if (z) {
                requestPlayPauseFocus();
            }
        }
    }

    /* access modifiers changed from: private */
    /* JADX WARNING: Removed duplicated region for block: B:37:0x007f  */
    /* JADX WARNING: Removed duplicated region for block: B:40:? A[RETURN, SYNTHETIC] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void updateNavigation() {
        /*
            r8 = this;
            boolean r0 = r8.isVisible()
            if (r0 == 0) goto L_0x0082
            boolean r0 = r8.isAttachedToWindow
            if (r0 != 0) goto L_0x000c
            goto L_0x0082
        L_0x000c:
            com.google.android.exoplayer2.Player r0 = r8.player
            r1 = 0
            if (r0 == 0) goto L_0x0063
            com.google.android.exoplayer2.Timeline r2 = r0.getCurrentTimeline()
            boolean r3 = r2.isEmpty()
            if (r3 != 0) goto L_0x0063
            boolean r3 = r0.isPlayingAd()
            if (r3 != 0) goto L_0x0063
            int r3 = r0.getCurrentWindowIndex()
            com.google.android.exoplayer2.Timeline$Window r4 = r8.window
            r2.getWindow(r3, r4)
            com.google.android.exoplayer2.Timeline$Window r2 = r8.window
            boolean r2 = r2.isSeekable
            r3 = 1
            if (r2 != 0) goto L_0x0040
            com.google.android.exoplayer2.Timeline$Window r4 = r8.window
            boolean r4 = r4.isDynamic
            if (r4 == 0) goto L_0x0040
            boolean r4 = r0.hasPrevious()
            if (r4 == 0) goto L_0x003e
            goto L_0x0040
        L_0x003e:
            r4 = 0
            goto L_0x0041
        L_0x0040:
            r4 = 1
        L_0x0041:
            if (r2 == 0) goto L_0x0049
            int r5 = r8.rewindMs
            if (r5 <= 0) goto L_0x0049
            r5 = 1
            goto L_0x004a
        L_0x0049:
            r5 = 0
        L_0x004a:
            if (r2 == 0) goto L_0x0052
            int r6 = r8.fastForwardMs
            if (r6 <= 0) goto L_0x0052
            r6 = 1
            goto L_0x0053
        L_0x0052:
            r6 = 0
        L_0x0053:
            com.google.android.exoplayer2.Timeline$Window r7 = r8.window
            boolean r7 = r7.isDynamic
            if (r7 != 0) goto L_0x005f
            boolean r0 = r0.hasNext()
            if (r0 == 0) goto L_0x0060
        L_0x005f:
            r1 = 1
        L_0x0060:
            r0 = r1
            r1 = r4
            goto L_0x0067
        L_0x0063:
            r0 = 0
            r2 = 0
            r5 = 0
            r6 = 0
        L_0x0067:
            android.view.View r3 = r8.previousButton
            r8.setButtonEnabled(r1, r3)
            android.view.View r1 = r8.rewindButton
            r8.setButtonEnabled(r5, r1)
            android.view.View r1 = r8.fastForwardButton
            r8.setButtonEnabled(r6, r1)
            android.view.View r1 = r8.nextButton
            r8.setButtonEnabled(r0, r1)
            com.google.android.exoplayer2.ui.TimeBar r0 = r8.timeBar
            if (r0 == 0) goto L_0x0082
            r0.setEnabled(r2)
        L_0x0082:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.exoplayer2.p041ui.PlayerControlView.updateNavigation():void");
    }

    /* access modifiers changed from: private */
    public void updateRepeatModeButton() {
        if (isVisible() && this.isAttachedToWindow) {
            ImageView imageView = this.repeatToggleButton;
            if (imageView != null) {
                if (this.repeatToggleModes == 0) {
                    imageView.setVisibility(8);
                    return;
                }
                Player player2 = this.player;
                if (player2 == null) {
                    setButtonEnabled(false, imageView);
                    this.repeatToggleButton.setImageDrawable(this.repeatOffButtonDrawable);
                    this.repeatToggleButton.setContentDescription(this.repeatOffButtonContentDescription);
                    return;
                }
                setButtonEnabled(true, imageView);
                int repeatMode = player2.getRepeatMode();
                if (repeatMode == 0) {
                    this.repeatToggleButton.setImageDrawable(this.repeatOffButtonDrawable);
                    this.repeatToggleButton.setContentDescription(this.repeatOffButtonContentDescription);
                } else if (repeatMode == 1) {
                    this.repeatToggleButton.setImageDrawable(this.repeatOneButtonDrawable);
                    this.repeatToggleButton.setContentDescription(this.repeatOneButtonContentDescription);
                } else if (repeatMode == 2) {
                    this.repeatToggleButton.setImageDrawable(this.repeatAllButtonDrawable);
                    this.repeatToggleButton.setContentDescription(this.repeatAllButtonContentDescription);
                }
                this.repeatToggleButton.setVisibility(0);
            }
        }
    }

    /* access modifiers changed from: private */
    public void updateShuffleButton() {
        if (isVisible() && this.isAttachedToWindow) {
            ImageView imageView = this.shuffleButton;
            if (imageView != null) {
                Player player2 = this.player;
                if (!this.showShuffleButton) {
                    imageView.setVisibility(8);
                } else if (player2 == null) {
                    setButtonEnabled(false, imageView);
                    this.shuffleButton.setImageDrawable(this.shuffleOffButtonDrawable);
                    this.shuffleButton.setContentDescription(this.shuffleOffContentDescription);
                } else {
                    setButtonEnabled(true, imageView);
                    this.shuffleButton.setImageDrawable(player2.getShuffleModeEnabled() ? this.shuffleOnButtonDrawable : this.shuffleOffButtonDrawable);
                    this.shuffleButton.setContentDescription(player2.getShuffleModeEnabled() ? this.shuffleOnContentDescription : this.shuffleOffContentDescription);
                }
            }
        }
    }

    /* access modifiers changed from: private */
    public void updateTimeline() {
        long j;
        int i;
        Player player2 = this.player;
        if (player2 != null) {
            boolean z = true;
            this.multiWindowTimeBar = this.showMultiWindowTimeBar && canShowMultiWindowTimeBar(player2.getCurrentTimeline(), this.window);
            this.currentWindowOffset = 0;
            Timeline currentTimeline = player2.getCurrentTimeline();
            if (!currentTimeline.isEmpty()) {
                int currentWindowIndex = player2.getCurrentWindowIndex();
                int i2 = this.multiWindowTimeBar ? 0 : currentWindowIndex;
                int windowCount = this.multiWindowTimeBar ? currentTimeline.getWindowCount() - 1 : currentWindowIndex;
                long j2 = 0;
                i = 0;
                while (true) {
                    if (i2 > windowCount) {
                        break;
                    }
                    if (i2 == currentWindowIndex) {
                        this.currentWindowOffset = C1996C.usToMs(j2);
                    }
                    currentTimeline.getWindow(i2, this.window);
                    if (this.window.durationUs == C1996C.TIME_UNSET) {
                        Assertions.checkState(this.multiWindowTimeBar ^ z);
                        break;
                    }
                    int i3 = this.window.firstPeriodIndex;
                    while (i3 <= this.window.lastPeriodIndex) {
                        currentTimeline.getPeriod(i3, this.period);
                        int adGroupCount = this.period.getAdGroupCount();
                        int i4 = i;
                        for (int i5 = 0; i5 < adGroupCount; i5++) {
                            long adGroupTimeUs = this.period.getAdGroupTimeUs(i5);
                            if (adGroupTimeUs == Long.MIN_VALUE) {
                                if (this.period.durationUs == C1996C.TIME_UNSET) {
                                } else {
                                    adGroupTimeUs = this.period.durationUs;
                                }
                            }
                            long positionInWindowUs = adGroupTimeUs + this.period.getPositionInWindowUs();
                            if (positionInWindowUs >= 0 && positionInWindowUs <= this.window.durationUs) {
                                long[] jArr = this.adGroupTimesMs;
                                if (i4 == jArr.length) {
                                    int length = jArr.length == 0 ? 1 : jArr.length * 2;
                                    this.adGroupTimesMs = Arrays.copyOf(this.adGroupTimesMs, length);
                                    this.playedAdGroups = Arrays.copyOf(this.playedAdGroups, length);
                                }
                                this.adGroupTimesMs[i4] = C1996C.usToMs(j2 + positionInWindowUs);
                                this.playedAdGroups[i4] = this.period.hasPlayedAdGroup(i5);
                                i4++;
                            }
                        }
                        i3++;
                        i = i4;
                    }
                    j2 += this.window.durationUs;
                    i2++;
                    z = true;
                }
                j = j2;
            } else {
                i = 0;
                j = 0;
            }
            long usToMs = C1996C.usToMs(j);
            TextView textView = this.durationView;
            if (textView != null) {
                textView.setText(Util.getStringForTime(this.formatBuilder, this.formatter, usToMs));
            }
            TimeBar timeBar2 = this.timeBar;
            if (timeBar2 != null) {
                timeBar2.setDuration(usToMs);
                int length2 = this.extraAdGroupTimesMs.length;
                int i6 = i + length2;
                long[] jArr2 = this.adGroupTimesMs;
                if (i6 > jArr2.length) {
                    this.adGroupTimesMs = Arrays.copyOf(jArr2, i6);
                    this.playedAdGroups = Arrays.copyOf(this.playedAdGroups, i6);
                }
                System.arraycopy(this.extraAdGroupTimesMs, 0, this.adGroupTimesMs, i, length2);
                System.arraycopy(this.extraPlayedAdGroups, 0, this.playedAdGroups, i, length2);
                this.timeBar.setAdGroupTimesMs(this.adGroupTimesMs, this.playedAdGroups, i6);
            }
            updateProgress();
        }
    }

    /* access modifiers changed from: private */
    public void updateProgress() {
        long j;
        int i;
        if (isVisible() && this.isAttachedToWindow) {
            Player player2 = this.player;
            long j2 = 0;
            if (player2 != null) {
                j2 = this.currentWindowOffset + player2.getContentPosition();
                j = this.currentWindowOffset + player2.getContentBufferedPosition();
            } else {
                j = 0;
            }
            TextView textView = this.positionView;
            if (textView != null && !this.scrubbing) {
                textView.setText(Util.getStringForTime(this.formatBuilder, this.formatter, j2));
            }
            TimeBar timeBar2 = this.timeBar;
            if (timeBar2 != null) {
                timeBar2.setPosition(j2);
                this.timeBar.setBufferedPosition(j);
            }
            ProgressUpdateListener progressUpdateListener2 = this.progressUpdateListener;
            if (progressUpdateListener2 != null) {
                progressUpdateListener2.onProgressUpdate(j2, j);
            }
            removeCallbacks(this.updateProgressAction);
            if (player2 == null) {
                i = 1;
            } else {
                i = player2.getPlaybackState();
            }
            long j3 = 1000;
            if (player2 != null && player2.isPlaying()) {
                TimeBar timeBar3 = this.timeBar;
                long min = Math.min(timeBar3 != null ? timeBar3.getPreferredUpdateDelay() : 1000, 1000 - (j2 % 1000));
                float f = player2.getPlaybackParameters().speed;
                if (f > 0.0f) {
                    j3 = (long) (((float) min) / f);
                }
                postDelayed(this.updateProgressAction, Util.constrainValue(j3, (long) this.timeBarMinUpdateIntervalMs, 1000));
            } else if (i != 4 && i != 1) {
                postDelayed(this.updateProgressAction, 1000);
            }
        }
    }

    private void requestPlayPauseFocus() {
        boolean shouldShowPauseButton = shouldShowPauseButton();
        if (!shouldShowPauseButton) {
            View view = this.playButton;
            if (view != null) {
                view.requestFocus();
                return;
            }
        }
        if (shouldShowPauseButton) {
            View view2 = this.pauseButton;
            if (view2 != null) {
                view2.requestFocus();
            }
        }
    }

    private void setButtonEnabled(boolean z, View view) {
        if (view != null) {
            view.setEnabled(z);
            view.setAlpha(z ? this.buttonAlphaEnabled : this.buttonAlphaDisabled);
            view.setVisibility(0);
        }
    }

    /* access modifiers changed from: private */
    public void previous(Player player2) {
        Timeline currentTimeline = player2.getCurrentTimeline();
        if (!currentTimeline.isEmpty() && !player2.isPlayingAd()) {
            int currentWindowIndex = player2.getCurrentWindowIndex();
            currentTimeline.getWindow(currentWindowIndex, this.window);
            int previousWindowIndex = player2.getPreviousWindowIndex();
            if (previousWindowIndex == -1 || (player2.getCurrentPosition() > MAX_POSITION_FOR_SEEK_TO_PREVIOUS && (!this.window.isDynamic || this.window.isSeekable))) {
                seekTo(player2, currentWindowIndex, 0);
            } else {
                seekTo(player2, previousWindowIndex, C1996C.TIME_UNSET);
            }
        }
    }

    /* access modifiers changed from: private */
    public void next(Player player2) {
        Timeline currentTimeline = player2.getCurrentTimeline();
        if (!currentTimeline.isEmpty() && !player2.isPlayingAd()) {
            int currentWindowIndex = player2.getCurrentWindowIndex();
            int nextWindowIndex = player2.getNextWindowIndex();
            if (nextWindowIndex != -1) {
                seekTo(player2, nextWindowIndex, C1996C.TIME_UNSET);
            } else if (currentTimeline.getWindow(currentWindowIndex, this.window).isDynamic) {
                seekTo(player2, currentWindowIndex, C1996C.TIME_UNSET);
            }
        }
    }

    /* access modifiers changed from: private */
    public void rewind(Player player2) {
        if (player2.isCurrentWindowSeekable()) {
            int i = this.rewindMs;
            if (i > 0) {
                seekToOffset(player2, (long) (-i));
            }
        }
    }

    /* access modifiers changed from: private */
    public void fastForward(Player player2) {
        if (player2.isCurrentWindowSeekable()) {
            int i = this.fastForwardMs;
            if (i > 0) {
                seekToOffset(player2, (long) i);
            }
        }
    }

    private void seekToOffset(Player player2, long j) {
        long currentPosition = player2.getCurrentPosition() + j;
        long duration = player2.getDuration();
        if (duration != C1996C.TIME_UNSET) {
            currentPosition = Math.min(currentPosition, duration);
        }
        seekTo(player2, player2.getCurrentWindowIndex(), Math.max(currentPosition, 0));
    }

    /* access modifiers changed from: private */
    public void seekToTimeBarPosition(Player player2, long j) {
        int i;
        Timeline currentTimeline = player2.getCurrentTimeline();
        if (this.multiWindowTimeBar && !currentTimeline.isEmpty()) {
            int windowCount = currentTimeline.getWindowCount();
            i = 0;
            while (true) {
                long durationMs = currentTimeline.getWindow(i, this.window).getDurationMs();
                if (j < durationMs) {
                    break;
                } else if (i == windowCount - 1) {
                    j = durationMs;
                    break;
                } else {
                    j -= durationMs;
                    i++;
                }
            }
        } else {
            i = player2.getCurrentWindowIndex();
        }
        if (!seekTo(player2, i, j)) {
            updateProgress();
        }
    }

    /* access modifiers changed from: private */
    public boolean seekTo(Player player2, int i, long j) {
        return this.controlDispatcher.dispatchSeekTo(player2, i, j);
    }

    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        this.isAttachedToWindow = true;
        long j = this.hideAtMs;
        if (j != C1996C.TIME_UNSET) {
            long uptimeMillis = j - SystemClock.uptimeMillis();
            if (uptimeMillis <= 0) {
                hide();
            } else {
                postDelayed(this.hideAction, uptimeMillis);
            }
        } else if (isVisible()) {
            hideAfterTimeout();
        }
        updateAll();
    }

    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        this.isAttachedToWindow = false;
        removeCallbacks(this.updateProgressAction);
        removeCallbacks(this.hideAction);
    }

    public final boolean dispatchTouchEvent(MotionEvent motionEvent) {
        if (motionEvent.getAction() == 0) {
            removeCallbacks(this.hideAction);
        } else if (motionEvent.getAction() == 1) {
            hideAfterTimeout();
        }
        return super.dispatchTouchEvent(motionEvent);
    }

    public boolean dispatchKeyEvent(KeyEvent keyEvent) {
        return dispatchMediaKeyEvent(keyEvent) || super.dispatchKeyEvent(keyEvent);
    }

    public boolean dispatchMediaKeyEvent(KeyEvent keyEvent) {
        int keyCode = keyEvent.getKeyCode();
        Player player2 = this.player;
        if (player2 == null || !isHandledMediaKey(keyCode)) {
            return false;
        }
        if (keyEvent.getAction() == 0) {
            if (keyCode == 90) {
                fastForward(player2);
            } else if (keyCode == 89) {
                rewind(player2);
            } else if (keyEvent.getRepeatCount() == 0) {
                if (keyCode == 85) {
                    this.controlDispatcher.dispatchSetPlayWhenReady(player2, !player2.getPlayWhenReady());
                } else if (keyCode == 87) {
                    next(player2);
                } else if (keyCode == 88) {
                    previous(player2);
                } else if (keyCode == 126) {
                    this.controlDispatcher.dispatchSetPlayWhenReady(player2, true);
                } else if (keyCode == 127) {
                    this.controlDispatcher.dispatchSetPlayWhenReady(player2, false);
                }
            }
        }
        return true;
    }

    private boolean shouldShowPauseButton() {
        Player player2 = this.player;
        if (player2 == null || player2.getPlaybackState() == 4 || this.player.getPlaybackState() == 1 || !this.player.getPlayWhenReady()) {
            return false;
        }
        return true;
    }

    private static boolean canShowMultiWindowTimeBar(Timeline timeline, Window window2) {
        if (timeline.getWindowCount() > 100) {
            return false;
        }
        int windowCount = timeline.getWindowCount();
        for (int i = 0; i < windowCount; i++) {
            if (timeline.getWindow(i, window2).durationUs == C1996C.TIME_UNSET) {
                return false;
            }
        }
        return true;
    }
}
