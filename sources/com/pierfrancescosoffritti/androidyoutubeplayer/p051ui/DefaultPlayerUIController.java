package com.pierfrancescosoffritti.androidyoutubeplayer.p051ui;

import android.animation.Animator;
import android.animation.Animator.AnimatorListener;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;
import androidx.core.content.ContextCompat;
import com.pierfrancescosoffritti.androidyoutubeplayer.C4571R;
import com.pierfrancescosoffritti.androidyoutubeplayer.p051ui.menu.YouTubePlayerMenu;
import com.pierfrancescosoffritti.androidyoutubeplayer.p051ui.menu.defaultMenu.DefaultYouTubePlayerMenu;
import com.pierfrancescosoffritti.androidyoutubeplayer.player.PlayerConstants.PlaybackQuality;
import com.pierfrancescosoffritti.androidyoutubeplayer.player.PlayerConstants.PlaybackRate;
import com.pierfrancescosoffritti.androidyoutubeplayer.player.PlayerConstants.PlayerError;
import com.pierfrancescosoffritti.androidyoutubeplayer.player.PlayerConstants.PlayerState;
import com.pierfrancescosoffritti.androidyoutubeplayer.player.YouTubePlayer;
import com.pierfrancescosoffritti.androidyoutubeplayer.player.YouTubePlayerView;
import com.pierfrancescosoffritti.androidyoutubeplayer.player.listeners.YouTubePlayerFullScreenListener;
import com.pierfrancescosoffritti.androidyoutubeplayer.player.listeners.YouTubePlayerListener;
import com.pierfrancescosoffritti.androidyoutubeplayer.utils.C4603Utils;

/* renamed from: com.pierfrancescosoffritti.androidyoutubeplayer.ui.DefaultPlayerUIController */
public class DefaultPlayerUIController implements OnClickListener, OnSeekBarChangeListener, YouTubePlayerFullScreenListener, YouTubePlayerListener, PlayerUIController {

    /* renamed from: A */
    private final Handler f2305A = new Handler(Looper.getMainLooper());

    /* renamed from: B */
    private final Runnable f2306B = new Runnable() {
        public void run() {
            DefaultPlayerUIController.this.m2057a(0.0f);
        }
    };

    /* renamed from: C */
    private boolean f2307C = false;

    /* renamed from: D */
    private int f2308D = -1;

    /* renamed from: a */
    private final YouTubePlayerView f2309a;

    /* renamed from: b */
    private final YouTubePlayer f2310b;

    /* renamed from: c */
    private YouTubePlayerMenu f2311c;

    /* renamed from: d */
    private View f2312d;
    /* access modifiers changed from: private */

    /* renamed from: e */
    public View f2313e;

    /* renamed from: f */
    private LinearLayout f2314f;

    /* renamed from: g */
    private TextView f2315g;

    /* renamed from: h */
    private TextView f2316h;
    /* access modifiers changed from: private */

    /* renamed from: i */
    public TextView f2317i;

    /* renamed from: j */
    private TextView f2318j;

    /* renamed from: k */
    private ProgressBar f2319k;

    /* renamed from: l */
    private ImageView f2320l;

    /* renamed from: m */
    private ImageView f2321m;

    /* renamed from: n */
    private ImageView f2322n;

    /* renamed from: o */
    private ImageView f2323o;

    /* renamed from: p */
    private ImageView f2324p;

    /* renamed from: q */
    private ImageView f2325q;
    /* access modifiers changed from: private */

    /* renamed from: r */
    public SeekBar f2326r;

    /* renamed from: s */
    private OnClickListener f2327s;

    /* renamed from: t */
    private OnClickListener f2328t;

    /* renamed from: u */
    private boolean f2329u = false;

    /* renamed from: v */
    private boolean f2330v = true;

    /* renamed from: w */
    private boolean f2331w = false;

    /* renamed from: x */
    private boolean f2332x = true;

    /* renamed from: y */
    private boolean f2333y = true;

    /* renamed from: z */
    private boolean f2334z = true;

    /* renamed from: com.pierfrancescosoffritti.androidyoutubeplayer.ui.DefaultPlayerUIController$5 */
    static /* synthetic */ class C46005 {

        /* renamed from: a */
        static final /* synthetic */ int[] f2341a = new int[PlayerState.values().length];

        /* JADX WARNING: Can't wrap try/catch for region: R(10:0|1|2|3|4|5|6|7|8|10) */
        /* JADX WARNING: Can't wrap try/catch for region: R(8:0|1|2|3|4|5|6|(3:7|8|10)) */
        /* JADX WARNING: Failed to process nested try/catch */
        /* JADX WARNING: Missing exception handler attribute for start block: B:3:0x0014 */
        /* JADX WARNING: Missing exception handler attribute for start block: B:5:0x001f */
        /* JADX WARNING: Missing exception handler attribute for start block: B:7:0x002a */
        static {
            /*
                com.pierfrancescosoffritti.androidyoutubeplayer.player.PlayerConstants$PlayerState[] r0 = com.pierfrancescosoffritti.androidyoutubeplayer.player.PlayerConstants.PlayerState.values()
                int r0 = r0.length
                int[] r0 = new int[r0]
                f2341a = r0
                int[] r0 = f2341a     // Catch:{ NoSuchFieldError -> 0x0014 }
                com.pierfrancescosoffritti.androidyoutubeplayer.player.PlayerConstants$PlayerState r1 = com.pierfrancescosoffritti.androidyoutubeplayer.player.PlayerConstants.PlayerState.ENDED     // Catch:{ NoSuchFieldError -> 0x0014 }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x0014 }
                r2 = 1
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x0014 }
            L_0x0014:
                int[] r0 = f2341a     // Catch:{ NoSuchFieldError -> 0x001f }
                com.pierfrancescosoffritti.androidyoutubeplayer.player.PlayerConstants$PlayerState r1 = com.pierfrancescosoffritti.androidyoutubeplayer.player.PlayerConstants.PlayerState.PAUSED     // Catch:{ NoSuchFieldError -> 0x001f }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x001f }
                r2 = 2
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x001f }
            L_0x001f:
                int[] r0 = f2341a     // Catch:{ NoSuchFieldError -> 0x002a }
                com.pierfrancescosoffritti.androidyoutubeplayer.player.PlayerConstants$PlayerState r1 = com.pierfrancescosoffritti.androidyoutubeplayer.player.PlayerConstants.PlayerState.PLAYING     // Catch:{ NoSuchFieldError -> 0x002a }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x002a }
                r2 = 3
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x002a }
            L_0x002a:
                int[] r0 = f2341a     // Catch:{ NoSuchFieldError -> 0x0035 }
                com.pierfrancescosoffritti.androidyoutubeplayer.player.PlayerConstants$PlayerState r1 = com.pierfrancescosoffritti.androidyoutubeplayer.player.PlayerConstants.PlayerState.UNSTARTED     // Catch:{ NoSuchFieldError -> 0x0035 }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x0035 }
                r2 = 4
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x0035 }
            L_0x0035:
                return
            */
            throw new UnsupportedOperationException("Method not decompiled: com.pierfrancescosoffritti.androidyoutubeplayer.p051ui.DefaultPlayerUIController.C46005.<clinit>():void");
        }
    }

    public DefaultPlayerUIController(YouTubePlayerView youTubePlayerView, YouTubePlayer youTubePlayer) {
        this.f2309a = youTubePlayerView;
        this.f2310b = youTubePlayer;
        m2058a(View.inflate(youTubePlayerView.getContext(), C4571R.layout.default_player_ui, youTubePlayerView));
        this.f2311c = new DefaultYouTubePlayerMenu(youTubePlayerView.getContext());
    }

    /* renamed from: a */
    private void m2056a() {
        OnClickListener onClickListener = this.f2328t;
        if (onClickListener == null) {
            this.f2311c.show(this.f2320l);
        } else {
            onClickListener.onClick(this.f2320l);
        }
    }

    /* access modifiers changed from: private */
    /* renamed from: a */
    public void m2057a(final float f) {
        if (this.f2331w && this.f2332x) {
            this.f2330v = f != 0.0f;
            if (f != 1.0f || !this.f2329u) {
                this.f2305A.removeCallbacks(this.f2306B);
            } else {
                m2067e();
            }
            this.f2313e.animate().alpha(f).setDuration(300).setListener(new AnimatorListener() {
                public void onAnimationCancel(Animator animator) {
                }

                public void onAnimationEnd(Animator animator) {
                    if (f == 0.0f) {
                        DefaultPlayerUIController.this.f2313e.setVisibility(8);
                    }
                }

                public void onAnimationRepeat(Animator animator) {
                }

                public void onAnimationStart(Animator animator) {
                    if (f == 1.0f) {
                        DefaultPlayerUIController.this.f2313e.setVisibility(0);
                    }
                }
            }).start();
        }
    }

    /* renamed from: a */
    private void m2058a(View view) {
        this.f2312d = view.findViewById(C4571R.C4574id.panel);
        this.f2313e = view.findViewById(C4571R.C4574id.controls_root);
        this.f2314f = (LinearLayout) view.findViewById(C4571R.C4574id.extra_views_container);
        this.f2315g = (TextView) view.findViewById(C4571R.C4574id.video_title);
        this.f2316h = (TextView) view.findViewById(C4571R.C4574id.video_current_time);
        this.f2317i = (TextView) view.findViewById(C4571R.C4574id.video_duration);
        this.f2318j = (TextView) view.findViewById(C4571R.C4574id.live_video_indicator);
        this.f2319k = (ProgressBar) view.findViewById(C4571R.C4574id.progress);
        this.f2320l = (ImageView) view.findViewById(C4571R.C4574id.menu_button);
        this.f2321m = (ImageView) view.findViewById(C4571R.C4574id.play_pause_button);
        this.f2322n = (ImageView) view.findViewById(C4571R.C4574id.youtube_button);
        this.f2323o = (ImageView) view.findViewById(C4571R.C4574id.fullscreen_button);
        this.f2324p = (ImageView) view.findViewById(C4571R.C4574id.custom_action_left_button);
        this.f2325q = (ImageView) view.findViewById(C4571R.C4574id.custom_action_right_button);
        this.f2326r = (SeekBar) view.findViewById(C4571R.C4574id.seek_bar);
        this.f2326r.setOnSeekBarChangeListener(this);
        this.f2312d.setOnClickListener(this);
        this.f2321m.setOnClickListener(this);
        this.f2320l.setOnClickListener(this);
        this.f2323o.setOnClickListener(this);
    }

    /* renamed from: a */
    private void m2059a(PlayerState playerState) {
        int i = C46005.f2341a[playerState.ordinal()];
        if (i == 1 || i == 2) {
            this.f2329u = false;
        } else if (i == 3) {
            this.f2329u = true;
        } else if (i == 4) {
            m2068f();
        }
        m2061a(!this.f2329u);
    }

    /* renamed from: a */
    private void m2061a(boolean z) {
        this.f2321m.setImageResource(z ? C4571R.C4573drawable.ic_pause_36dp : C4571R.C4573drawable.ic_play_36dp);
    }

    /* renamed from: b */
    private void m2063b() {
        OnClickListener onClickListener = this.f2327s;
        if (onClickListener == null) {
            this.f2309a.toggleFullScreen();
        } else {
            onClickListener.onClick(this.f2323o);
        }
    }

    /* renamed from: c */
    private void m2065c() {
        if (this.f2329u) {
            this.f2310b.pause();
        } else {
            this.f2310b.play();
        }
    }

    /* renamed from: d */
    private void m2066d() {
        m2057a(this.f2330v ? 0.0f : 1.0f);
    }

    /* renamed from: e */
    private void m2067e() {
        this.f2305A.postDelayed(this.f2306B, 3000);
    }

    /* renamed from: f */
    private void m2068f() {
        this.f2326r.setProgress(0);
        this.f2326r.setMax(0);
        this.f2317i.post(new Runnable() {
            public void run() {
                DefaultPlayerUIController.this.f2317i.setText("");
            }
        });
    }

    public void addView(View view) {
        this.f2314f.addView(view, 0);
    }

    public void enableLiveVideoUI(boolean z) {
        TextView textView;
        int i = 0;
        if (z) {
            this.f2317i.setVisibility(4);
            this.f2326r.setVisibility(4);
            this.f2316h.setVisibility(4);
            textView = this.f2318j;
        } else {
            this.f2317i.setVisibility(0);
            this.f2326r.setVisibility(0);
            this.f2316h.setVisibility(0);
            textView = this.f2318j;
            i = 8;
        }
        textView.setVisibility(i);
    }

    public YouTubePlayerMenu getMenu() {
        return this.f2311c;
    }

    public void onApiChange() {
    }

    public void onClick(View view) {
        if (view == this.f2312d) {
            m2066d();
        } else if (view == this.f2321m) {
            m2065c();
        } else if (view == this.f2323o) {
            m2063b();
        } else if (view == this.f2320l) {
            m2056a();
        }
    }

    public void onCurrentSecond(float f) {
        if (!this.f2307C) {
            if (this.f2308D <= 0 || C4603Utils.formatTime(f).equals(C4603Utils.formatTime((float) this.f2308D))) {
                this.f2308D = -1;
                this.f2326r.setProgress((int) f);
            }
        }
    }

    public void onError(PlayerError playerError) {
    }

    public void onPlaybackQualityChange(PlaybackQuality playbackQuality) {
    }

    public void onPlaybackRateChange(PlaybackRate playbackRate) {
    }

    public void onProgressChanged(SeekBar seekBar, int i, boolean z) {
        this.f2316h.setText(C4603Utils.formatTime((float) i));
    }

    public void onReady() {
    }

    public void onStartTrackingTouch(SeekBar seekBar) {
        this.f2307C = true;
    }

    public void onStateChange(PlayerState playerState) {
        this.f2308D = -1;
        m2059a(playerState);
        if (playerState == PlayerState.PLAYING || playerState == PlayerState.PAUSED || playerState == PlayerState.VIDEO_CUED) {
            this.f2312d.setBackgroundColor(ContextCompat.getColor(this.f2309a.getContext(), 17170445));
            this.f2319k.setVisibility(8);
            if (this.f2333y) {
                this.f2321m.setVisibility(0);
            }
            boolean z = true;
            this.f2331w = true;
            if (playerState != PlayerState.PLAYING) {
                z = false;
            }
            m2061a(z);
            if (z) {
                m2067e();
            } else {
                this.f2305A.removeCallbacks(this.f2306B);
            }
        } else {
            m2061a(false);
            m2057a(1.0f);
            if (playerState == PlayerState.BUFFERING) {
                this.f2312d.setBackgroundColor(ContextCompat.getColor(this.f2309a.getContext(), 17170445));
                if (this.f2333y) {
                    this.f2321m.setVisibility(4);
                }
                this.f2324p.setVisibility(8);
                this.f2325q.setVisibility(8);
                this.f2331w = false;
            }
            if (playerState == PlayerState.UNSTARTED) {
                this.f2331w = false;
                this.f2319k.setVisibility(8);
                if (this.f2333y) {
                    this.f2321m.setVisibility(0);
                }
            }
        }
    }

    public void onStopTrackingTouch(SeekBar seekBar) {
        if (this.f2329u) {
            this.f2308D = seekBar.getProgress();
        }
        this.f2310b.seekTo((float) seekBar.getProgress());
        this.f2307C = false;
    }

    public void onVideoDuration(float f) {
        this.f2317i.setText(C4603Utils.formatTime(f));
        this.f2326r.setMax((int) f);
    }

    public void onVideoId(final String str) {
        this.f2322n.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                StringBuilder sb = new StringBuilder();
                sb.append("http://www.youtube.com/watch?v=");
                sb.append(str);
                sb.append("#t=");
                sb.append(DefaultPlayerUIController.this.f2326r.getProgress());
                DefaultPlayerUIController.this.f2313e.getContext().startActivity(new Intent("android.intent.action.VIEW", Uri.parse(sb.toString())));
            }
        });
    }

    public void onVideoLoadedFraction(float f) {
        if (this.f2334z) {
            SeekBar seekBar = this.f2326r;
            seekBar.setSecondaryProgress((int) (f * ((float) seekBar.getMax())));
            return;
        }
        this.f2326r.setSecondaryProgress(0);
    }

    public void onYouTubePlayerEnterFullScreen() {
        this.f2323o.setImageResource(C4571R.C4573drawable.ic_fullscreen_exit_24dp);
    }

    public void onYouTubePlayerExitFullScreen() {
        this.f2323o.setImageResource(C4571R.C4573drawable.ic_fullscreen_24dp);
    }

    public void removeView(View view) {
        this.f2314f.removeView(view);
    }

    public void setCustomAction1(Drawable drawable, OnClickListener onClickListener) {
        this.f2324p.setImageDrawable(drawable);
        this.f2324p.setOnClickListener(onClickListener);
        showCustomAction1(onClickListener != null);
    }

    public void setCustomAction2(Drawable drawable, OnClickListener onClickListener) {
        this.f2325q.setImageDrawable(drawable);
        this.f2325q.setOnClickListener(onClickListener);
        showCustomAction2(onClickListener != null);
    }

    public void setFullScreenButtonClickListener(OnClickListener onClickListener) {
        this.f2327s = onClickListener;
    }

    public void setMenu(YouTubePlayerMenu youTubePlayerMenu) {
        this.f2311c = youTubePlayerMenu;
    }

    public void setMenuButtonClickListener(OnClickListener onClickListener) {
        this.f2328t = onClickListener;
    }

    public void setVideoTitle(String str) {
        this.f2315g.setText(str);
    }

    public void showBufferingProgress(boolean z) {
        this.f2334z = z;
    }

    public void showCurrentTime(boolean z) {
        this.f2316h.setVisibility(z ? 0 : 8);
    }

    public void showCustomAction1(boolean z) {
        this.f2324p.setVisibility(z ? 0 : 8);
    }

    public void showCustomAction2(boolean z) {
        this.f2325q.setVisibility(z ? 0 : 8);
    }

    public void showDuration(boolean z) {
        this.f2317i.setVisibility(z ? 0 : 8);
    }

    public void showFullscreenButton(boolean z) {
        this.f2323o.setVisibility(z ? 0 : 8);
    }

    public void showMenuButton(boolean z) {
        this.f2320l.setVisibility(z ? 0 : 8);
    }

    public void showPlayPauseButton(boolean z) {
        this.f2321m.setVisibility(z ? 0 : 8);
        this.f2333y = z;
    }

    public void showSeekBar(boolean z) {
        this.f2326r.setVisibility(z ? 0 : 4);
    }

    public void showUI(boolean z) {
        this.f2313e.setVisibility(z ? 0 : 4);
        this.f2332x = z;
    }

    public void showVideoTitle(boolean z) {
        this.f2315g.setVisibility(z ? 0 : 8);
    }

    public void showYouTubeButton(boolean z) {
        this.f2322n.setVisibility(z ? 0 : 8);
    }
}
