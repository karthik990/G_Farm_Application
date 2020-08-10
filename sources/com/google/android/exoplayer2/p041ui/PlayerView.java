package com.google.android.exoplayer2.p041ui;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.RectF;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Looper;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.SurfaceView;
import android.view.TextureView;
import android.view.View;
import android.view.View.OnLayoutChangeListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.core.content.ContextCompat;
import com.google.android.exoplayer2.ControlDispatcher;
import com.google.android.exoplayer2.ExoPlaybackException;
import com.google.android.exoplayer2.PlaybackParameters;
import com.google.android.exoplayer2.PlaybackPreparer;
import com.google.android.exoplayer2.Player;
import com.google.android.exoplayer2.Player.EventListener;
import com.google.android.exoplayer2.Player.EventListener.CC;
import com.google.android.exoplayer2.Player.TextComponent;
import com.google.android.exoplayer2.Player.VideoComponent;
import com.google.android.exoplayer2.Timeline;
import com.google.android.exoplayer2.metadata.Metadata;
import com.google.android.exoplayer2.metadata.Metadata.Entry;
import com.google.android.exoplayer2.metadata.flac.PictureFrame;
import com.google.android.exoplayer2.metadata.id3.ApicFrame;
import com.google.android.exoplayer2.p041ui.AspectRatioFrameLayout.AspectRatioListener;
import com.google.android.exoplayer2.p041ui.PlayerControlView.VisibilityListener;
import com.google.android.exoplayer2.p041ui.spherical.SingleTapListener;
import com.google.android.exoplayer2.p041ui.spherical.SphericalGLSurfaceView;
import com.google.android.exoplayer2.source.TrackGroupArray;
import com.google.android.exoplayer2.source.ads.AdsLoader.AdViewProvider;
import com.google.android.exoplayer2.text.Cue;
import com.google.android.exoplayer2.text.TextOutput;
import com.google.android.exoplayer2.trackselection.TrackSelection;
import com.google.android.exoplayer2.trackselection.TrackSelectionArray;
import com.google.android.exoplayer2.util.Assertions;
import com.google.android.exoplayer2.util.ErrorMessageProvider;
import com.google.android.exoplayer2.util.Util;
import com.google.android.exoplayer2.video.VideoDecoderGLSurfaceView;
import com.google.android.exoplayer2.video.VideoListener;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;
import java.util.List;
import org.checkerframework.checker.nullness.qual.EnsuresNonNullIf;
import org.checkerframework.checker.nullness.qual.RequiresNonNull;

/* renamed from: com.google.android.exoplayer2.ui.PlayerView */
public class PlayerView extends FrameLayout implements AdViewProvider {
    private static final int PICTURE_TYPE_FRONT_COVER = 3;
    private static final int PICTURE_TYPE_NOT_SET = -1;
    public static final int SHOW_BUFFERING_ALWAYS = 2;
    public static final int SHOW_BUFFERING_NEVER = 0;
    public static final int SHOW_BUFFERING_WHEN_PLAYING = 1;
    private static final int SURFACE_TYPE_NONE = 0;
    private static final int SURFACE_TYPE_SPHERICAL_GL_SURFACE_VIEW = 3;
    private static final int SURFACE_TYPE_SURFACE_VIEW = 1;
    private static final int SURFACE_TYPE_TEXTURE_VIEW = 2;
    private static final int SURFACE_TYPE_VIDEO_DECODER_GL_SURFACE_VIEW = 4;
    private final FrameLayout adOverlayFrameLayout;
    private final ImageView artworkView;
    private final View bufferingView;
    private final ComponentListener componentListener;
    /* access modifiers changed from: private */
    public final AspectRatioFrameLayout contentFrame;
    private final PlayerControlView controller;
    private boolean controllerAutoShow;
    /* access modifiers changed from: private */
    public boolean controllerHideDuringAds;
    private boolean controllerHideOnTouch;
    private int controllerShowTimeoutMs;
    private VisibilityListener controllerVisibilityListener;
    private CharSequence customErrorMessage;
    private Drawable defaultArtwork;
    private ErrorMessageProvider<? super ExoPlaybackException> errorMessageProvider;
    private final TextView errorMessageView;
    private boolean isTouching;
    private boolean keepContentOnPlayerReset;
    private final FrameLayout overlayFrameLayout;
    private Player player;
    private int showBuffering;
    /* access modifiers changed from: private */
    public final View shutterView;
    /* access modifiers changed from: private */
    public final SubtitleView subtitleView;
    /* access modifiers changed from: private */
    public final View surfaceView;
    /* access modifiers changed from: private */
    public int textureViewRotation;
    private boolean useArtwork;
    private boolean useController;

    @Documented
    @Retention(RetentionPolicy.SOURCE)
    /* renamed from: com.google.android.exoplayer2.ui.PlayerView$ShowBuffering */
    public @interface ShowBuffering {
    }

    /* renamed from: com.google.android.exoplayer2.ui.PlayerView$ComponentListener */
    private final class ComponentListener implements EventListener, TextOutput, VideoListener, OnLayoutChangeListener, SingleTapListener, VisibilityListener {
        public /* synthetic */ void onIsPlayingChanged(boolean z) {
            CC.$default$onIsPlayingChanged(this, z);
        }

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

        public /* synthetic */ void onRepeatModeChanged(int i) {
            CC.$default$onRepeatModeChanged(this, i);
        }

        public /* synthetic */ void onSeekProcessed() {
            CC.$default$onSeekProcessed(this);
        }

        public /* synthetic */ void onShuffleModeEnabledChanged(boolean z) {
            CC.$default$onShuffleModeEnabledChanged(this, z);
        }

        public /* synthetic */ void onSurfaceSizeChanged(int i, int i2) {
            VideoListener.CC.$default$onSurfaceSizeChanged(this, i, i2);
        }

        public /* synthetic */ void onTimelineChanged(Timeline timeline, int i) {
            CC.$default$onTimelineChanged(this, timeline, i);
        }

        @Deprecated
        public /* synthetic */ void onTimelineChanged(Timeline timeline, Object obj, int i) {
            CC.$default$onTimelineChanged(this, timeline, obj, i);
        }

        private ComponentListener() {
        }

        public void onCues(List<Cue> list) {
            if (PlayerView.this.subtitleView != null) {
                PlayerView.this.subtitleView.onCues(list);
            }
        }

        public void onVideoSizeChanged(int i, int i2, int i3, float f) {
            float f2 = (i2 == 0 || i == 0) ? 1.0f : (((float) i) * f) / ((float) i2);
            if (PlayerView.this.surfaceView instanceof TextureView) {
                if (i3 == 90 || i3 == 270) {
                    f2 = 1.0f / f2;
                }
                if (PlayerView.this.textureViewRotation != 0) {
                    PlayerView.this.surfaceView.removeOnLayoutChangeListener(this);
                }
                PlayerView.this.textureViewRotation = i3;
                if (PlayerView.this.textureViewRotation != 0) {
                    PlayerView.this.surfaceView.addOnLayoutChangeListener(this);
                }
                PlayerView.applyTextureViewRotation((TextureView) PlayerView.this.surfaceView, PlayerView.this.textureViewRotation);
            }
            PlayerView playerView = PlayerView.this;
            playerView.onContentAspectRatioChanged(f2, playerView.contentFrame, PlayerView.this.surfaceView);
        }

        public void onRenderedFirstFrame() {
            if (PlayerView.this.shutterView != null) {
                PlayerView.this.shutterView.setVisibility(4);
            }
        }

        public void onTracksChanged(TrackGroupArray trackGroupArray, TrackSelectionArray trackSelectionArray) {
            PlayerView.this.updateForCurrentTrackSelections(false);
        }

        public void onPlayerStateChanged(boolean z, int i) {
            PlayerView.this.updateBuffering();
            PlayerView.this.updateErrorMessage();
            if (!PlayerView.this.isPlayingAd() || !PlayerView.this.controllerHideDuringAds) {
                PlayerView.this.maybeShowController(false);
            } else {
                PlayerView.this.hideController();
            }
        }

        public void onPositionDiscontinuity(int i) {
            if (PlayerView.this.isPlayingAd() && PlayerView.this.controllerHideDuringAds) {
                PlayerView.this.hideController();
            }
        }

        public void onLayoutChange(View view, int i, int i2, int i3, int i4, int i5, int i6, int i7, int i8) {
            PlayerView.applyTextureViewRotation((TextureView) view, PlayerView.this.textureViewRotation);
        }

        public boolean onSingleTapUp(MotionEvent motionEvent) {
            return PlayerView.this.toggleControllerVisibility();
        }

        public void onVisibilityChange(int i) {
            PlayerView.this.updateContentDescription();
        }
    }

    private boolean isDpadKey(int i) {
        return i == 19 || i == 270 || i == 22 || i == 271 || i == 20 || i == 269 || i == 21 || i == 268 || i == 23;
    }

    public PlayerView(Context context) {
        this(context, null);
    }

    public PlayerView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    /* JADX INFO: finally extract failed */
    public PlayerView(Context context, AttributeSet attributeSet, int i) {
        boolean z;
        int i2;
        int i3;
        boolean z2;
        int i4;
        boolean z3;
        int i5;
        boolean z4;
        boolean z5;
        int i6;
        int i7;
        boolean z6;
        boolean z7;
        Context context2 = context;
        AttributeSet attributeSet2 = attributeSet;
        super(context, attributeSet, i);
        this.componentListener = new ComponentListener();
        if (isInEditMode()) {
            this.contentFrame = null;
            this.shutterView = null;
            this.surfaceView = null;
            this.artworkView = null;
            this.subtitleView = null;
            this.bufferingView = null;
            this.errorMessageView = null;
            this.controller = null;
            this.adOverlayFrameLayout = null;
            this.overlayFrameLayout = null;
            ImageView imageView = new ImageView(context2);
            if (Util.SDK_INT >= 23) {
                configureEditModeLogoV23(getResources(), imageView);
            } else {
                configureEditModeLogo(getResources(), imageView);
            }
            addView(imageView);
            return;
        }
        int i8 = C2489R.layout.exo_player_view;
        if (attributeSet2 != null) {
            TypedArray obtainStyledAttributes = context.getTheme().obtainStyledAttributes(attributeSet2, C2489R.styleable.PlayerView, 0, 0);
            try {
                z4 = obtainStyledAttributes.hasValue(C2489R.styleable.PlayerView_shutter_background_color);
                i5 = obtainStyledAttributes.getColor(C2489R.styleable.PlayerView_shutter_background_color, 0);
                int resourceId = obtainStyledAttributes.getResourceId(C2489R.styleable.PlayerView_player_layout_id, i8);
                z3 = obtainStyledAttributes.getBoolean(C2489R.styleable.PlayerView_use_artwork, true);
                i4 = obtainStyledAttributes.getResourceId(C2489R.styleable.PlayerView_default_artwork, 0);
                boolean z8 = obtainStyledAttributes.getBoolean(C2489R.styleable.PlayerView_use_controller, true);
                i3 = obtainStyledAttributes.getInt(C2489R.styleable.PlayerView_surface_type, 1);
                i2 = obtainStyledAttributes.getInt(C2489R.styleable.PlayerView_resize_mode, 0);
                int i9 = obtainStyledAttributes.getInt(C2489R.styleable.PlayerView_show_timeout, 5000);
                boolean z9 = obtainStyledAttributes.getBoolean(C2489R.styleable.PlayerView_hide_on_touch, true);
                boolean z10 = obtainStyledAttributes.getBoolean(C2489R.styleable.PlayerView_auto_show, true);
                int i10 = resourceId;
                i7 = obtainStyledAttributes.getInteger(C2489R.styleable.PlayerView_show_buffering, 0);
                boolean z11 = z9;
                this.keepContentOnPlayerReset = obtainStyledAttributes.getBoolean(C2489R.styleable.PlayerView_keep_content_on_player_reset, this.keepContentOnPlayerReset);
                boolean z12 = obtainStyledAttributes.getBoolean(C2489R.styleable.PlayerView_hide_during_ads, true);
                obtainStyledAttributes.recycle();
                z5 = z10;
                z = z8;
                z2 = z12;
                i6 = i9;
                i8 = i10;
                z6 = z11;
            } catch (Throwable th) {
                obtainStyledAttributes.recycle();
                throw th;
            }
        } else {
            z6 = true;
            i7 = 0;
            i6 = 5000;
            z5 = true;
            z4 = false;
            i5 = 0;
            z3 = true;
            i4 = 0;
            z2 = true;
            i3 = 1;
            i2 = 0;
            z = true;
        }
        LayoutInflater.from(context).inflate(i8, this);
        setDescendantFocusability(262144);
        this.contentFrame = (AspectRatioFrameLayout) findViewById(C2489R.C2492id.exo_content_frame);
        AspectRatioFrameLayout aspectRatioFrameLayout = this.contentFrame;
        if (aspectRatioFrameLayout != null) {
            setResizeModeRaw(aspectRatioFrameLayout, i2);
        }
        this.shutterView = findViewById(C2489R.C2492id.exo_shutter);
        View view = this.shutterView;
        if (view != null && z4) {
            view.setBackgroundColor(i5);
        }
        if (this.contentFrame == null || i3 == 0) {
            this.surfaceView = null;
        } else {
            LayoutParams layoutParams = new LayoutParams(-1, -1);
            if (i3 == 2) {
                this.surfaceView = new TextureView(context2);
            } else if (i3 == 3) {
                SphericalGLSurfaceView sphericalGLSurfaceView = new SphericalGLSurfaceView(context2);
                sphericalGLSurfaceView.setSingleTapListener(this.componentListener);
                this.surfaceView = sphericalGLSurfaceView;
            } else if (i3 != 4) {
                this.surfaceView = new SurfaceView(context2);
            } else {
                this.surfaceView = new VideoDecoderGLSurfaceView(context2);
            }
            this.surfaceView.setLayoutParams(layoutParams);
            this.contentFrame.addView(this.surfaceView, 0);
        }
        this.adOverlayFrameLayout = (FrameLayout) findViewById(C2489R.C2492id.exo_ad_overlay);
        this.overlayFrameLayout = (FrameLayout) findViewById(C2489R.C2492id.exo_overlay);
        this.artworkView = (ImageView) findViewById(C2489R.C2492id.exo_artwork);
        this.useArtwork = z3 && this.artworkView != null;
        if (i4 != 0) {
            this.defaultArtwork = ContextCompat.getDrawable(getContext(), i4);
        }
        this.subtitleView = (SubtitleView) findViewById(C2489R.C2492id.exo_subtitles);
        SubtitleView subtitleView2 = this.subtitleView;
        if (subtitleView2 != null) {
            subtitleView2.setUserDefaultStyle();
            this.subtitleView.setUserDefaultTextSize();
        }
        this.bufferingView = findViewById(C2489R.C2492id.exo_buffering);
        View view2 = this.bufferingView;
        if (view2 != null) {
            view2.setVisibility(8);
        }
        this.showBuffering = i7;
        this.errorMessageView = (TextView) findViewById(C2489R.C2492id.exo_error_message);
        TextView textView = this.errorMessageView;
        if (textView != null) {
            textView.setVisibility(8);
        }
        PlayerControlView playerControlView = (PlayerControlView) findViewById(C2489R.C2492id.exo_controller);
        View findViewById = findViewById(C2489R.C2492id.exo_controller_placeholder);
        if (playerControlView != null) {
            this.controller = playerControlView;
            z7 = false;
        } else if (findViewById != null) {
            z7 = false;
            this.controller = new PlayerControlView(context2, null, 0, attributeSet2);
            this.controller.setId(C2489R.C2492id.exo_controller);
            this.controller.setLayoutParams(findViewById.getLayoutParams());
            ViewGroup viewGroup = (ViewGroup) findViewById.getParent();
            int indexOfChild = viewGroup.indexOfChild(findViewById);
            viewGroup.removeView(findViewById);
            viewGroup.addView(this.controller, indexOfChild);
        } else {
            z7 = false;
            this.controller = null;
        }
        if (this.controller == null) {
            i6 = 0;
        }
        this.controllerShowTimeoutMs = i6;
        this.controllerHideOnTouch = z6;
        this.controllerAutoShow = z5;
        this.controllerHideDuringAds = z2;
        if (z && this.controller != null) {
            z7 = true;
        }
        this.useController = z7;
        hideController();
        updateContentDescription();
        PlayerControlView playerControlView2 = this.controller;
        if (playerControlView2 != null) {
            playerControlView2.addVisibilityListener(this.componentListener);
        }
    }

    public static void switchTargetView(Player player2, PlayerView playerView, PlayerView playerView2) {
        if (playerView != playerView2) {
            if (playerView2 != null) {
                playerView2.setPlayer(player2);
            }
            if (playerView != null) {
                playerView.setPlayer(null);
            }
        }
    }

    public Player getPlayer() {
        return this.player;
    }

    public void setPlayer(Player player2) {
        Assertions.checkState(Looper.myLooper() == Looper.getMainLooper());
        Assertions.checkArgument(player2 == null || player2.getApplicationLooper() == Looper.getMainLooper());
        Player player3 = this.player;
        if (player3 != player2) {
            if (player3 != null) {
                player3.removeListener(this.componentListener);
                VideoComponent videoComponent = player3.getVideoComponent();
                if (videoComponent != null) {
                    videoComponent.removeVideoListener(this.componentListener);
                    View view = this.surfaceView;
                    if (view instanceof TextureView) {
                        videoComponent.clearVideoTextureView((TextureView) view);
                    } else if (view instanceof SphericalGLSurfaceView) {
                        ((SphericalGLSurfaceView) view).setVideoComponent(null);
                    } else if (view instanceof VideoDecoderGLSurfaceView) {
                        videoComponent.setVideoDecoderOutputBufferRenderer(null);
                    } else if (view instanceof SurfaceView) {
                        videoComponent.clearVideoSurfaceView((SurfaceView) view);
                    }
                }
                TextComponent textComponent = player3.getTextComponent();
                if (textComponent != null) {
                    textComponent.removeTextOutput(this.componentListener);
                }
            }
            this.player = player2;
            if (useController()) {
                this.controller.setPlayer(player2);
            }
            SubtitleView subtitleView2 = this.subtitleView;
            if (subtitleView2 != null) {
                subtitleView2.setCues(null);
            }
            updateBuffering();
            updateErrorMessage();
            updateForCurrentTrackSelections(true);
            if (player2 != null) {
                VideoComponent videoComponent2 = player2.getVideoComponent();
                if (videoComponent2 != null) {
                    View view2 = this.surfaceView;
                    if (view2 instanceof TextureView) {
                        videoComponent2.setVideoTextureView((TextureView) view2);
                    } else if (view2 instanceof SphericalGLSurfaceView) {
                        ((SphericalGLSurfaceView) view2).setVideoComponent(videoComponent2);
                    } else if (view2 instanceof VideoDecoderGLSurfaceView) {
                        videoComponent2.setVideoDecoderOutputBufferRenderer(((VideoDecoderGLSurfaceView) view2).getVideoDecoderOutputBufferRenderer());
                    } else if (view2 instanceof SurfaceView) {
                        videoComponent2.setVideoSurfaceView((SurfaceView) view2);
                    }
                    videoComponent2.addVideoListener(this.componentListener);
                }
                TextComponent textComponent2 = player2.getTextComponent();
                if (textComponent2 != null) {
                    textComponent2.addTextOutput(this.componentListener);
                }
                player2.addListener(this.componentListener);
                maybeShowController(false);
            } else {
                hideController();
            }
        }
    }

    public void setVisibility(int i) {
        super.setVisibility(i);
        View view = this.surfaceView;
        if (view instanceof SurfaceView) {
            view.setVisibility(i);
        }
    }

    public void setResizeMode(int i) {
        Assertions.checkStateNotNull(this.contentFrame);
        this.contentFrame.setResizeMode(i);
    }

    public int getResizeMode() {
        Assertions.checkStateNotNull(this.contentFrame);
        return this.contentFrame.getResizeMode();
    }

    public boolean getUseArtwork() {
        return this.useArtwork;
    }

    public void setUseArtwork(boolean z) {
        Assertions.checkState(!z || this.artworkView != null);
        if (this.useArtwork != z) {
            this.useArtwork = z;
            updateForCurrentTrackSelections(false);
        }
    }

    public Drawable getDefaultArtwork() {
        return this.defaultArtwork;
    }

    @Deprecated
    public void setDefaultArtwork(Bitmap bitmap) {
        Drawable drawable;
        if (bitmap == null) {
            drawable = null;
        } else {
            drawable = new BitmapDrawable(getResources(), bitmap);
        }
        setDefaultArtwork(drawable);
    }

    public void setDefaultArtwork(Drawable drawable) {
        if (this.defaultArtwork != drawable) {
            this.defaultArtwork = drawable;
            updateForCurrentTrackSelections(false);
        }
    }

    public boolean getUseController() {
        return this.useController;
    }

    public void setUseController(boolean z) {
        Assertions.checkState(!z || this.controller != null);
        if (this.useController != z) {
            this.useController = z;
            if (useController()) {
                this.controller.setPlayer(this.player);
            } else {
                PlayerControlView playerControlView = this.controller;
                if (playerControlView != null) {
                    playerControlView.hide();
                    this.controller.setPlayer(null);
                }
            }
            updateContentDescription();
        }
    }

    public void setShutterBackgroundColor(int i) {
        View view = this.shutterView;
        if (view != null) {
            view.setBackgroundColor(i);
        }
    }

    public void setKeepContentOnPlayerReset(boolean z) {
        if (this.keepContentOnPlayerReset != z) {
            this.keepContentOnPlayerReset = z;
            updateForCurrentTrackSelections(false);
        }
    }

    @Deprecated
    public void setShowBuffering(boolean z) {
        setShowBuffering(z ? 1 : 0);
    }

    public void setShowBuffering(int i) {
        if (this.showBuffering != i) {
            this.showBuffering = i;
            updateBuffering();
        }
    }

    public void setErrorMessageProvider(ErrorMessageProvider<? super ExoPlaybackException> errorMessageProvider2) {
        if (this.errorMessageProvider != errorMessageProvider2) {
            this.errorMessageProvider = errorMessageProvider2;
            updateErrorMessage();
        }
    }

    public void setCustomErrorMessage(CharSequence charSequence) {
        Assertions.checkState(this.errorMessageView != null);
        this.customErrorMessage = charSequence;
        updateErrorMessage();
    }

    public boolean dispatchKeyEvent(KeyEvent keyEvent) {
        Player player2 = this.player;
        if (player2 != null && player2.isPlayingAd()) {
            return super.dispatchKeyEvent(keyEvent);
        }
        boolean isDpadKey = isDpadKey(keyEvent.getKeyCode());
        boolean z = true;
        if (isDpadKey && useController() && !this.controller.isVisible()) {
            maybeShowController(true);
        } else if (dispatchMediaKeyEvent(keyEvent) || super.dispatchKeyEvent(keyEvent)) {
            maybeShowController(true);
        } else {
            if (isDpadKey && useController()) {
                maybeShowController(true);
            }
            z = false;
        }
        return z;
    }

    public boolean dispatchMediaKeyEvent(KeyEvent keyEvent) {
        return useController() && this.controller.dispatchMediaKeyEvent(keyEvent);
    }

    public boolean isControllerVisible() {
        PlayerControlView playerControlView = this.controller;
        return playerControlView != null && playerControlView.isVisible();
    }

    public void showController() {
        showController(shouldShowControllerIndefinitely());
    }

    public void hideController() {
        PlayerControlView playerControlView = this.controller;
        if (playerControlView != null) {
            playerControlView.hide();
        }
    }

    public int getControllerShowTimeoutMs() {
        return this.controllerShowTimeoutMs;
    }

    public void setControllerShowTimeoutMs(int i) {
        Assertions.checkStateNotNull(this.controller);
        this.controllerShowTimeoutMs = i;
        if (this.controller.isVisible()) {
            showController();
        }
    }

    public boolean getControllerHideOnTouch() {
        return this.controllerHideOnTouch;
    }

    public void setControllerHideOnTouch(boolean z) {
        Assertions.checkStateNotNull(this.controller);
        this.controllerHideOnTouch = z;
        updateContentDescription();
    }

    public boolean getControllerAutoShow() {
        return this.controllerAutoShow;
    }

    public void setControllerAutoShow(boolean z) {
        this.controllerAutoShow = z;
    }

    public void setControllerHideDuringAds(boolean z) {
        this.controllerHideDuringAds = z;
    }

    public void setControllerVisibilityListener(VisibilityListener visibilityListener) {
        Assertions.checkStateNotNull(this.controller);
        VisibilityListener visibilityListener2 = this.controllerVisibilityListener;
        if (visibilityListener2 != visibilityListener) {
            if (visibilityListener2 != null) {
                this.controller.removeVisibilityListener(visibilityListener2);
            }
            this.controllerVisibilityListener = visibilityListener;
            if (visibilityListener != null) {
                this.controller.addVisibilityListener(visibilityListener);
            }
        }
    }

    public void setPlaybackPreparer(PlaybackPreparer playbackPreparer) {
        Assertions.checkStateNotNull(this.controller);
        this.controller.setPlaybackPreparer(playbackPreparer);
    }

    public void setControlDispatcher(ControlDispatcher controlDispatcher) {
        Assertions.checkStateNotNull(this.controller);
        this.controller.setControlDispatcher(controlDispatcher);
    }

    public void setRewindIncrementMs(int i) {
        Assertions.checkStateNotNull(this.controller);
        this.controller.setRewindIncrementMs(i);
    }

    public void setFastForwardIncrementMs(int i) {
        Assertions.checkStateNotNull(this.controller);
        this.controller.setFastForwardIncrementMs(i);
    }

    public void setRepeatToggleModes(int i) {
        Assertions.checkStateNotNull(this.controller);
        this.controller.setRepeatToggleModes(i);
    }

    public void setShowShuffleButton(boolean z) {
        Assertions.checkStateNotNull(this.controller);
        this.controller.setShowShuffleButton(z);
    }

    public void setShowMultiWindowTimeBar(boolean z) {
        Assertions.checkStateNotNull(this.controller);
        this.controller.setShowMultiWindowTimeBar(z);
    }

    public void setExtraAdGroupMarkers(long[] jArr, boolean[] zArr) {
        Assertions.checkStateNotNull(this.controller);
        this.controller.setExtraAdGroupMarkers(jArr, zArr);
    }

    public void setAspectRatioListener(AspectRatioListener aspectRatioListener) {
        Assertions.checkStateNotNull(this.contentFrame);
        this.contentFrame.setAspectRatioListener(aspectRatioListener);
    }

    public View getVideoSurfaceView() {
        return this.surfaceView;
    }

    public FrameLayout getOverlayFrameLayout() {
        return this.overlayFrameLayout;
    }

    public SubtitleView getSubtitleView() {
        return this.subtitleView;
    }

    public boolean onTouchEvent(MotionEvent motionEvent) {
        if (!useController() || this.player == null) {
            return false;
        }
        int action = motionEvent.getAction();
        if (action == 0) {
            this.isTouching = true;
            return true;
        } else if (action != 1 || !this.isTouching) {
            return false;
        } else {
            this.isTouching = false;
            performClick();
            return true;
        }
    }

    public boolean performClick() {
        super.performClick();
        return toggleControllerVisibility();
    }

    public boolean onTrackballEvent(MotionEvent motionEvent) {
        if (!useController() || this.player == null) {
            return false;
        }
        maybeShowController(true);
        return true;
    }

    public void onResume() {
        View view = this.surfaceView;
        if (view instanceof SphericalGLSurfaceView) {
            ((SphericalGLSurfaceView) view).onResume();
        }
    }

    public void onPause() {
        View view = this.surfaceView;
        if (view instanceof SphericalGLSurfaceView) {
            ((SphericalGLSurfaceView) view).onPause();
        }
    }

    /* access modifiers changed from: protected */
    public void onContentAspectRatioChanged(float f, AspectRatioFrameLayout aspectRatioFrameLayout, View view) {
        if (aspectRatioFrameLayout != null) {
            if (view instanceof SphericalGLSurfaceView) {
                f = 0.0f;
            }
            aspectRatioFrameLayout.setAspectRatio(f);
        }
    }

    public ViewGroup getAdViewGroup() {
        return (ViewGroup) Assertions.checkStateNotNull(this.adOverlayFrameLayout, "exo_ad_overlay must be present for ad playback");
    }

    public View[] getAdOverlayViews() {
        ArrayList arrayList = new ArrayList();
        FrameLayout frameLayout = this.overlayFrameLayout;
        if (frameLayout != null) {
            arrayList.add(frameLayout);
        }
        PlayerControlView playerControlView = this.controller;
        if (playerControlView != null) {
            arrayList.add(playerControlView);
        }
        return (View[]) arrayList.toArray(new View[0]);
    }

    @EnsuresNonNullIf(expression = {"controller"}, result = true)
    private boolean useController() {
        if (!this.useController) {
            return false;
        }
        Assertions.checkStateNotNull(this.controller);
        return true;
    }

    @EnsuresNonNullIf(expression = {"artworkView"}, result = true)
    private boolean useArtwork() {
        if (!this.useArtwork) {
            return false;
        }
        Assertions.checkStateNotNull(this.artworkView);
        return true;
    }

    /* access modifiers changed from: private */
    public boolean toggleControllerVisibility() {
        if (!useController() || this.player == null) {
            return false;
        }
        if (!this.controller.isVisible()) {
            maybeShowController(true);
        } else if (this.controllerHideOnTouch) {
            this.controller.hide();
        }
        return true;
    }

    /* access modifiers changed from: private */
    public void maybeShowController(boolean z) {
        if ((!isPlayingAd() || !this.controllerHideDuringAds) && useController()) {
            boolean z2 = this.controller.isVisible() && this.controller.getShowTimeoutMs() <= 0;
            boolean shouldShowControllerIndefinitely = shouldShowControllerIndefinitely();
            if (z || z2 || shouldShowControllerIndefinitely) {
                showController(shouldShowControllerIndefinitely);
            }
        }
    }

    private boolean shouldShowControllerIndefinitely() {
        Player player2 = this.player;
        boolean z = true;
        if (player2 == null) {
            return true;
        }
        int playbackState = player2.getPlaybackState();
        if (!this.controllerAutoShow || !(playbackState == 1 || playbackState == 4 || !this.player.getPlayWhenReady())) {
            z = false;
        }
        return z;
    }

    private void showController(boolean z) {
        if (useController()) {
            this.controller.setShowTimeoutMs(z ? 0 : this.controllerShowTimeoutMs);
            this.controller.show();
        }
    }

    /* access modifiers changed from: private */
    public boolean isPlayingAd() {
        Player player2 = this.player;
        return player2 != null && player2.isPlayingAd() && this.player.getPlayWhenReady();
    }

    /* access modifiers changed from: private */
    public void updateForCurrentTrackSelections(boolean z) {
        Player player2 = this.player;
        if (player2 == null || player2.getCurrentTrackGroups().isEmpty()) {
            if (!this.keepContentOnPlayerReset) {
                hideArtwork();
                closeShutter();
            }
            return;
        }
        if (z && !this.keepContentOnPlayerReset) {
            closeShutter();
        }
        TrackSelectionArray currentTrackSelections = player2.getCurrentTrackSelections();
        int i = 0;
        while (i < currentTrackSelections.length) {
            if (player2.getRendererType(i) != 2 || currentTrackSelections.get(i) == null) {
                i++;
            } else {
                hideArtwork();
                return;
            }
        }
        closeShutter();
        if (useArtwork()) {
            for (int i2 = 0; i2 < currentTrackSelections.length; i2++) {
                TrackSelection trackSelection = currentTrackSelections.get(i2);
                if (trackSelection != null) {
                    int i3 = 0;
                    while (i3 < trackSelection.length()) {
                        Metadata metadata = trackSelection.getFormat(i3).metadata;
                        if (metadata == null || !setArtworkFromMetadata(metadata)) {
                            i3++;
                        } else {
                            return;
                        }
                    }
                    continue;
                }
            }
            if (setDrawableArtwork(this.defaultArtwork)) {
                return;
            }
        }
        hideArtwork();
    }

    @RequiresNonNull({"artworkView"})
    private boolean setArtworkFromMetadata(Metadata metadata) {
        byte[] bArr;
        int i;
        int i2 = 0;
        int i3 = -1;
        boolean z = false;
        while (i2 < metadata.length()) {
            Entry entry = metadata.get(i2);
            if (entry instanceof ApicFrame) {
                ApicFrame apicFrame = (ApicFrame) entry;
                bArr = apicFrame.pictureData;
                i = apicFrame.pictureType;
            } else if (entry instanceof PictureFrame) {
                PictureFrame pictureFrame = (PictureFrame) entry;
                bArr = pictureFrame.pictureData;
                i = pictureFrame.pictureType;
            } else {
                continue;
                i2++;
            }
            if (i3 == -1 || i == 3) {
                z = setDrawableArtwork(new BitmapDrawable(getResources(), BitmapFactory.decodeByteArray(bArr, 0, bArr.length)));
                if (i == 3) {
                    break;
                }
                i3 = i;
                i2++;
            } else {
                i2++;
            }
        }
        return z;
    }

    @RequiresNonNull({"artworkView"})
    private boolean setDrawableArtwork(Drawable drawable) {
        if (drawable != null) {
            int intrinsicWidth = drawable.getIntrinsicWidth();
            int intrinsicHeight = drawable.getIntrinsicHeight();
            if (intrinsicWidth > 0 && intrinsicHeight > 0) {
                onContentAspectRatioChanged(((float) intrinsicWidth) / ((float) intrinsicHeight), this.contentFrame, this.artworkView);
                this.artworkView.setImageDrawable(drawable);
                this.artworkView.setVisibility(0);
                return true;
            }
        }
        return false;
    }

    private void hideArtwork() {
        ImageView imageView = this.artworkView;
        if (imageView != null) {
            imageView.setImageResource(17170445);
            this.artworkView.setVisibility(4);
        }
    }

    private void closeShutter() {
        View view = this.shutterView;
        if (view != null) {
            view.setVisibility(0);
        }
    }

    /* access modifiers changed from: private */
    /* JADX WARNING: Code restructure failed: missing block: B:10:0x001d, code lost:
        if (r4.player.getPlayWhenReady() == false) goto L_0x0020;
     */
    /* JADX WARNING: Removed duplicated region for block: B:14:0x0026  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void updateBuffering() {
        /*
            r4 = this;
            android.view.View r0 = r4.bufferingView
            if (r0 == 0) goto L_0x002b
            com.google.android.exoplayer2.Player r0 = r4.player
            r1 = 1
            r2 = 0
            if (r0 == 0) goto L_0x0020
            int r0 = r0.getPlaybackState()
            r3 = 2
            if (r0 != r3) goto L_0x0020
            int r0 = r4.showBuffering
            if (r0 == r3) goto L_0x0021
            if (r0 != r1) goto L_0x0020
            com.google.android.exoplayer2.Player r0 = r4.player
            boolean r0 = r0.getPlayWhenReady()
            if (r0 == 0) goto L_0x0020
            goto L_0x0021
        L_0x0020:
            r1 = 0
        L_0x0021:
            android.view.View r0 = r4.bufferingView
            if (r1 == 0) goto L_0x0026
            goto L_0x0028
        L_0x0026:
            r2 = 8
        L_0x0028:
            r0.setVisibility(r2)
        L_0x002b:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.exoplayer2.p041ui.PlayerView.updateBuffering():void");
    }

    /* access modifiers changed from: private */
    public void updateErrorMessage() {
        TextView textView = this.errorMessageView;
        if (textView != null) {
            CharSequence charSequence = this.customErrorMessage;
            if (charSequence != null) {
                textView.setText(charSequence);
                this.errorMessageView.setVisibility(0);
                return;
            }
            Player player2 = this.player;
            ExoPlaybackException playbackError = player2 != null ? player2.getPlaybackError() : null;
            if (playbackError != null) {
                ErrorMessageProvider<? super ExoPlaybackException> errorMessageProvider2 = this.errorMessageProvider;
                if (errorMessageProvider2 != null) {
                    this.errorMessageView.setText((CharSequence) errorMessageProvider2.getErrorMessage(playbackError).second);
                    this.errorMessageView.setVisibility(0);
                }
            }
            this.errorMessageView.setVisibility(8);
        }
    }

    /* access modifiers changed from: private */
    public void updateContentDescription() {
        PlayerControlView playerControlView = this.controller;
        String str = null;
        if (playerControlView == null || !this.useController) {
            setContentDescription(null);
        } else if (playerControlView.getVisibility() == 0) {
            if (this.controllerHideOnTouch) {
                str = getResources().getString(C2489R.C2493string.exo_controls_hide);
            }
            setContentDescription(str);
        } else {
            setContentDescription(getResources().getString(C2489R.C2493string.exo_controls_show));
        }
    }

    private static void configureEditModeLogoV23(Resources resources, ImageView imageView) {
        imageView.setImageDrawable(resources.getDrawable(C2489R.C2491drawable.exo_edit_mode_logo, null));
        imageView.setBackgroundColor(resources.getColor(C2489R.C2490color.exo_edit_mode_background_color, null));
    }

    private static void configureEditModeLogo(Resources resources, ImageView imageView) {
        imageView.setImageDrawable(resources.getDrawable(C2489R.C2491drawable.exo_edit_mode_logo));
        imageView.setBackgroundColor(resources.getColor(C2489R.C2490color.exo_edit_mode_background_color));
    }

    private static void setResizeModeRaw(AspectRatioFrameLayout aspectRatioFrameLayout, int i) {
        aspectRatioFrameLayout.setResizeMode(i);
    }

    /* access modifiers changed from: private */
    public static void applyTextureViewRotation(TextureView textureView, int i) {
        Matrix matrix = new Matrix();
        float width = (float) textureView.getWidth();
        float height = (float) textureView.getHeight();
        if (!(width == 0.0f || height == 0.0f || i == 0)) {
            float f = width / 2.0f;
            float f2 = height / 2.0f;
            matrix.postRotate((float) i, f, f2);
            RectF rectF = new RectF(0.0f, 0.0f, width, height);
            RectF rectF2 = new RectF();
            matrix.mapRect(rectF2, rectF);
            matrix.postScale(width / rectF2.width(), height / rectF2.height(), f, f2);
        }
        textureView.setTransform(matrix);
    }
}
