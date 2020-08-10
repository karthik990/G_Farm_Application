package com.twitter.sdk.android.tweetui;

import android.content.Intent;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnInfoListener;
import android.media.MediaPlayer.OnPreparedListener;
import android.net.Uri;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ProgressBar;
import android.widget.TextView;
import com.twitter.sdk.android.core.IntentUtils;
import com.twitter.sdk.android.core.Twitter;
import com.twitter.sdk.android.tweetui.PlayerActivity.PlayerItem;
import com.twitter.sdk.android.tweetui.internal.SwipeToDismissTouchListener;
import com.twitter.sdk.android.tweetui.internal.SwipeToDismissTouchListener.Callback;
import com.twitter.sdk.android.tweetui.internal.VideoControlView;
import com.twitter.sdk.android.tweetui.internal.VideoView;

class PlayerController {
    private static final String TAG = "PlayerController";
    final TextView callToActionView;
    final Callback callback;
    boolean isPlaying = true;
    final View rootView;
    int seekPosition;
    final VideoControlView videoControlView;
    final ProgressBar videoProgressView;
    final VideoView videoView;

    PlayerController(View view, Callback callback2) {
        this.rootView = view;
        this.videoView = (VideoView) view.findViewById(C5234R.C5237id.video_view);
        this.videoControlView = (VideoControlView) view.findViewById(C5234R.C5237id.video_control_view);
        this.videoProgressView = (ProgressBar) view.findViewById(C5234R.C5237id.video_progress_view);
        this.callToActionView = (TextView) view.findViewById(C5234R.C5237id.call_to_action_view);
        this.callback = callback2;
    }

    PlayerController(View view, VideoView videoView2, VideoControlView videoControlView2, ProgressBar progressBar, TextView textView, Callback callback2) {
        this.rootView = view;
        this.videoView = videoView2;
        this.videoControlView = videoControlView2;
        this.videoProgressView = progressBar;
        this.callToActionView = textView;
        this.callback = callback2;
    }

    /* access modifiers changed from: 0000 */
    public void prepare(PlayerItem playerItem) {
        try {
            setUpCallToAction(playerItem);
            setUpMediaControl(playerItem.looping, playerItem.showVideoControls);
            this.videoView.setOnTouchListener(SwipeToDismissTouchListener.createFromView(this.videoView, this.callback));
            this.videoView.setOnPreparedListener(new OnPreparedListener() {
                public final void onPrepared(MediaPlayer mediaPlayer) {
                    PlayerController.this.lambda$prepare$0$PlayerController(mediaPlayer);
                }
            });
            this.videoView.setOnInfoListener(new OnInfoListener() {
                public final boolean onInfo(MediaPlayer mediaPlayer, int i, int i2) {
                    return PlayerController.this.lambda$prepare$1$PlayerController(mediaPlayer, i, i2);
                }
            });
            this.videoView.setVideoURI(Uri.parse(playerItem.url), playerItem.looping);
            this.videoView.requestFocus();
        } catch (Exception e) {
            Twitter.getLogger().mo20820e(TAG, "Error occurred during video playback", e);
        }
    }

    public /* synthetic */ void lambda$prepare$0$PlayerController(MediaPlayer mediaPlayer) {
        this.videoProgressView.setVisibility(8);
    }

    public /* synthetic */ boolean lambda$prepare$1$PlayerController(MediaPlayer mediaPlayer, int i, int i2) {
        if (i == 702) {
            this.videoProgressView.setVisibility(8);
            return true;
        } else if (i != 701) {
            return false;
        } else {
            this.videoProgressView.setVisibility(0);
            return true;
        }
    }

    /* access modifiers changed from: 0000 */
    public void onResume() {
        int i = this.seekPosition;
        if (i != 0) {
            this.videoView.seekTo(i);
        }
        if (this.isPlaying) {
            this.videoView.start();
            this.videoControlView.update();
        }
    }

    /* access modifiers changed from: 0000 */
    public void onPause() {
        this.isPlaying = this.videoView.isPlaying();
        this.seekPosition = this.videoView.getCurrentPosition();
        this.videoView.pause();
    }

    /* access modifiers changed from: 0000 */
    public void onDestroy() {
        this.videoView.stopPlayback();
    }

    /* access modifiers changed from: 0000 */
    public void setUpMediaControl(boolean z, boolean z2) {
        if (!z || z2) {
            setUpMediaControl();
        } else {
            setUpLoopControl();
        }
    }

    /* access modifiers changed from: 0000 */
    public void setUpLoopControl() {
        this.videoControlView.setVisibility(4);
        this.videoView.setOnClickListener(new OnClickListener() {
            public final void onClick(View view) {
                PlayerController.this.lambda$setUpLoopControl$2$PlayerController(view);
            }
        });
    }

    public /* synthetic */ void lambda$setUpLoopControl$2$PlayerController(View view) {
        if (this.videoView.isPlaying()) {
            this.videoView.pause();
        } else {
            this.videoView.start();
        }
    }

    /* access modifiers changed from: 0000 */
    public void setUpMediaControl() {
        this.videoView.setMediaController(this.videoControlView);
    }

    /* access modifiers changed from: 0000 */
    public void setUpCallToAction(PlayerItem playerItem) {
        if (playerItem.callToActionText != null && playerItem.callToActionUrl != null) {
            this.callToActionView.setVisibility(0);
            this.callToActionView.setText(playerItem.callToActionText);
            setUpCallToActionListener(playerItem.callToActionUrl);
            setUpRootViewOnClickListener();
        }
    }

    /* access modifiers changed from: 0000 */
    public void setUpCallToActionListener(String str) {
        this.callToActionView.setOnClickListener(new OnClickListener(str) {
            private final /* synthetic */ String f$1;

            {
                this.f$1 = r2;
            }

            public final void onClick(View view) {
                PlayerController.this.lambda$setUpCallToActionListener$3$PlayerController(this.f$1, view);
            }
        });
    }

    public /* synthetic */ void lambda$setUpCallToActionListener$3$PlayerController(String str, View view) {
        IntentUtils.safeStartActivity(this.callToActionView.getContext(), new Intent("android.intent.action.VIEW", Uri.parse(str)));
    }

    /* access modifiers changed from: 0000 */
    public void setUpRootViewOnClickListener() {
        this.rootView.setOnClickListener(new OnClickListener() {
            public final void onClick(View view) {
                PlayerController.this.lambda$setUpRootViewOnClickListener$4$PlayerController(view);
            }
        });
    }

    public /* synthetic */ void lambda$setUpRootViewOnClickListener$4$PlayerController(View view) {
        if (this.callToActionView.getVisibility() == 0) {
            this.callToActionView.setVisibility(8);
        } else {
            this.callToActionView.setVisibility(0);
        }
    }
}
