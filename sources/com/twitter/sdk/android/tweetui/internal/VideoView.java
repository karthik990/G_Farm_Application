package com.twitter.sdk.android.tweetui.internal;

import android.content.Context;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnBufferingUpdateListener;
import android.media.MediaPlayer.OnCompletionListener;
import android.media.MediaPlayer.OnErrorListener;
import android.media.MediaPlayer.OnInfoListener;
import android.media.MediaPlayer.OnPreparedListener;
import android.media.MediaPlayer.OnVideoSizeChangedListener;
import android.net.Uri;
import android.util.AttributeSet;
import android.util.Log;
import android.view.GestureDetector;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceHolder.Callback;
import android.view.SurfaceView;
import android.view.View.MeasureSpec;
import com.twitter.sdk.android.tweetui.internal.VideoControlView.MediaPlayerControl;

public class VideoView extends SurfaceView implements MediaPlayerControl {
    private static final int STATE_ERROR = -1;
    private static final int STATE_IDLE = 0;
    private static final int STATE_PAUSED = 4;
    private static final int STATE_PLAYBACK_COMPLETED = 5;
    private static final int STATE_PLAYING = 3;
    private static final int STATE_PREPARED = 2;
    private static final int STATE_PREPARING = 1;
    /* access modifiers changed from: private */
    public String TAG;
    private GestureDetector gestureDetector;
    private int mAudioSession;
    private OnBufferingUpdateListener mBufferingUpdateListener;
    private OnCompletionListener mCompletionListener;
    /* access modifiers changed from: private */
    public int mCurrentBufferPercentage;
    /* access modifiers changed from: private */
    public int mCurrentState;
    private OnErrorListener mErrorListener;
    private OnInfoListener mInfoListener;
    private boolean mLooping;
    /* access modifiers changed from: private */
    public VideoControlView mMediaController;
    /* access modifiers changed from: private */
    public MediaPlayer mMediaPlayer;
    /* access modifiers changed from: private */
    public OnCompletionListener mOnCompletionListener;
    /* access modifiers changed from: private */
    public OnErrorListener mOnErrorListener;
    /* access modifiers changed from: private */
    public OnInfoListener mOnInfoListener;
    /* access modifiers changed from: private */
    public OnPreparedListener mOnPreparedListener;
    OnPreparedListener mPreparedListener;
    Callback mSHCallback;
    /* access modifiers changed from: private */
    public int mSeekWhenPrepared;
    OnVideoSizeChangedListener mSizeChangedListener;
    /* access modifiers changed from: private */
    public int mSurfaceHeight;
    /* access modifiers changed from: private */
    public SurfaceHolder mSurfaceHolder;
    /* access modifiers changed from: private */
    public int mSurfaceWidth;
    /* access modifiers changed from: private */
    public int mTargetState;
    private Uri mUri;
    /* access modifiers changed from: private */
    public int mVideoHeight;
    /* access modifiers changed from: private */
    public int mVideoWidth;

    public VideoView(Context context) {
        super(context);
        this.TAG = "VideoView";
        this.mCurrentState = 0;
        this.mTargetState = 0;
        this.mSurfaceHolder = null;
        this.mMediaPlayer = null;
        this.mSizeChangedListener = new OnVideoSizeChangedListener() {
            public void onVideoSizeChanged(MediaPlayer mediaPlayer, int i, int i2) {
                VideoView.this.mVideoWidth = mediaPlayer.getVideoWidth();
                VideoView.this.mVideoHeight = mediaPlayer.getVideoHeight();
                if (VideoView.this.mVideoWidth != 0 && VideoView.this.mVideoHeight != 0) {
                    VideoView.this.getHolder().setFixedSize(VideoView.this.mVideoWidth, VideoView.this.mVideoHeight);
                    VideoView.this.requestLayout();
                }
            }
        };
        this.mPreparedListener = new OnPreparedListener() {
            public void onPrepared(MediaPlayer mediaPlayer) {
                VideoView.this.mCurrentState = 2;
                if (VideoView.this.mOnPreparedListener != null) {
                    VideoView.this.mOnPreparedListener.onPrepared(VideoView.this.mMediaPlayer);
                }
                if (VideoView.this.mMediaController != null) {
                    VideoView.this.mMediaController.setEnabled(true);
                }
                VideoView.this.mVideoWidth = mediaPlayer.getVideoWidth();
                VideoView.this.mVideoHeight = mediaPlayer.getVideoHeight();
                int access$600 = VideoView.this.mSeekWhenPrepared;
                if (access$600 != 0) {
                    VideoView.this.seekTo(access$600);
                }
                if (VideoView.this.mVideoWidth != 0 && VideoView.this.mVideoHeight != 0) {
                    VideoView.this.getHolder().setFixedSize(VideoView.this.mVideoWidth, VideoView.this.mVideoHeight);
                    if (VideoView.this.mSurfaceWidth != VideoView.this.mVideoWidth || VideoView.this.mSurfaceHeight != VideoView.this.mVideoHeight) {
                        return;
                    }
                    if (VideoView.this.mTargetState == 3) {
                        VideoView.this.start();
                        if (VideoView.this.mMediaController != null) {
                            VideoView.this.mMediaController.show();
                        }
                    } else if (VideoView.this.isPlaying()) {
                    } else {
                        if ((access$600 != 0 || VideoView.this.getCurrentPosition() > 0) && VideoView.this.mMediaController != null) {
                            VideoView.this.mMediaController.show();
                        }
                    }
                } else if (VideoView.this.mTargetState == 3) {
                    VideoView.this.start();
                }
            }
        };
        this.mCompletionListener = new OnCompletionListener() {
            public void onCompletion(MediaPlayer mediaPlayer) {
                VideoView.this.mCurrentState = 5;
                VideoView.this.mTargetState = 5;
                if (VideoView.this.mOnCompletionListener != null) {
                    VideoView.this.mOnCompletionListener.onCompletion(VideoView.this.mMediaPlayer);
                }
            }
        };
        this.mInfoListener = new OnInfoListener() {
            public boolean onInfo(MediaPlayer mediaPlayer, int i, int i2) {
                if (VideoView.this.mOnInfoListener != null) {
                    VideoView.this.mOnInfoListener.onInfo(mediaPlayer, i, i2);
                }
                return true;
            }
        };
        this.mErrorListener = new OnErrorListener() {
            public boolean onError(MediaPlayer mediaPlayer, int i, int i2) {
                String access$1200 = VideoView.this.TAG;
                StringBuilder sb = new StringBuilder();
                sb.append("Error: ");
                sb.append(i);
                sb.append(",");
                sb.append(i2);
                Log.d(access$1200, sb.toString());
                VideoView.this.mCurrentState = -1;
                VideoView.this.mTargetState = -1;
                if (VideoView.this.mMediaController != null) {
                    VideoView.this.mMediaController.hide();
                }
                if (VideoView.this.mOnErrorListener == null || VideoView.this.mOnErrorListener.onError(VideoView.this.mMediaPlayer, i, i2)) {
                }
                return true;
            }
        };
        this.mBufferingUpdateListener = new OnBufferingUpdateListener() {
            public void onBufferingUpdate(MediaPlayer mediaPlayer, int i) {
                VideoView.this.mCurrentBufferPercentage = i;
            }
        };
        this.gestureDetector = new GestureDetector(getContext(), new SimpleOnGestureListener() {
            public boolean onSingleTapUp(MotionEvent motionEvent) {
                if (VideoView.this.isInPlaybackState() && VideoView.this.mMediaController != null) {
                    VideoView.this.toggleMediaControlsVisiblity();
                }
                return false;
            }
        });
        this.mSHCallback = new Callback() {
            public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i2, int i3) {
                VideoView.this.mSurfaceWidth = i2;
                VideoView.this.mSurfaceHeight = i3;
                boolean z = true;
                boolean z2 = VideoView.this.mTargetState == 3;
                if (!(VideoView.this.mVideoWidth == i2 && VideoView.this.mVideoHeight == i3)) {
                    z = false;
                }
                if (VideoView.this.mMediaPlayer != null && z2 && z) {
                    if (VideoView.this.mSeekWhenPrepared != 0) {
                        VideoView videoView = VideoView.this;
                        videoView.seekTo(videoView.mSeekWhenPrepared);
                    }
                    VideoView.this.start();
                    if (VideoView.this.mMediaController != null) {
                        VideoView.this.mMediaController.show();
                    }
                }
            }

            public void surfaceCreated(SurfaceHolder surfaceHolder) {
                VideoView.this.mSurfaceHolder = surfaceHolder;
                VideoView.this.openVideo();
            }

            public void surfaceDestroyed(SurfaceHolder surfaceHolder) {
                VideoView.this.mSurfaceHolder = null;
                if (VideoView.this.mMediaController != null) {
                    VideoView.this.mMediaController.hide();
                }
                VideoView.this.release(true);
            }
        };
        initVideoView();
    }

    public VideoView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public VideoView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.TAG = "VideoView";
        this.mCurrentState = 0;
        this.mTargetState = 0;
        this.mSurfaceHolder = null;
        this.mMediaPlayer = null;
        this.mSizeChangedListener = new OnVideoSizeChangedListener() {
            public void onVideoSizeChanged(MediaPlayer mediaPlayer, int i, int i2) {
                VideoView.this.mVideoWidth = mediaPlayer.getVideoWidth();
                VideoView.this.mVideoHeight = mediaPlayer.getVideoHeight();
                if (VideoView.this.mVideoWidth != 0 && VideoView.this.mVideoHeight != 0) {
                    VideoView.this.getHolder().setFixedSize(VideoView.this.mVideoWidth, VideoView.this.mVideoHeight);
                    VideoView.this.requestLayout();
                }
            }
        };
        this.mPreparedListener = new OnPreparedListener() {
            public void onPrepared(MediaPlayer mediaPlayer) {
                VideoView.this.mCurrentState = 2;
                if (VideoView.this.mOnPreparedListener != null) {
                    VideoView.this.mOnPreparedListener.onPrepared(VideoView.this.mMediaPlayer);
                }
                if (VideoView.this.mMediaController != null) {
                    VideoView.this.mMediaController.setEnabled(true);
                }
                VideoView.this.mVideoWidth = mediaPlayer.getVideoWidth();
                VideoView.this.mVideoHeight = mediaPlayer.getVideoHeight();
                int access$600 = VideoView.this.mSeekWhenPrepared;
                if (access$600 != 0) {
                    VideoView.this.seekTo(access$600);
                }
                if (VideoView.this.mVideoWidth != 0 && VideoView.this.mVideoHeight != 0) {
                    VideoView.this.getHolder().setFixedSize(VideoView.this.mVideoWidth, VideoView.this.mVideoHeight);
                    if (VideoView.this.mSurfaceWidth != VideoView.this.mVideoWidth || VideoView.this.mSurfaceHeight != VideoView.this.mVideoHeight) {
                        return;
                    }
                    if (VideoView.this.mTargetState == 3) {
                        VideoView.this.start();
                        if (VideoView.this.mMediaController != null) {
                            VideoView.this.mMediaController.show();
                        }
                    } else if (VideoView.this.isPlaying()) {
                    } else {
                        if ((access$600 != 0 || VideoView.this.getCurrentPosition() > 0) && VideoView.this.mMediaController != null) {
                            VideoView.this.mMediaController.show();
                        }
                    }
                } else if (VideoView.this.mTargetState == 3) {
                    VideoView.this.start();
                }
            }
        };
        this.mCompletionListener = new OnCompletionListener() {
            public void onCompletion(MediaPlayer mediaPlayer) {
                VideoView.this.mCurrentState = 5;
                VideoView.this.mTargetState = 5;
                if (VideoView.this.mOnCompletionListener != null) {
                    VideoView.this.mOnCompletionListener.onCompletion(VideoView.this.mMediaPlayer);
                }
            }
        };
        this.mInfoListener = new OnInfoListener() {
            public boolean onInfo(MediaPlayer mediaPlayer, int i, int i2) {
                if (VideoView.this.mOnInfoListener != null) {
                    VideoView.this.mOnInfoListener.onInfo(mediaPlayer, i, i2);
                }
                return true;
            }
        };
        this.mErrorListener = new OnErrorListener() {
            public boolean onError(MediaPlayer mediaPlayer, int i, int i2) {
                String access$1200 = VideoView.this.TAG;
                StringBuilder sb = new StringBuilder();
                sb.append("Error: ");
                sb.append(i);
                sb.append(",");
                sb.append(i2);
                Log.d(access$1200, sb.toString());
                VideoView.this.mCurrentState = -1;
                VideoView.this.mTargetState = -1;
                if (VideoView.this.mMediaController != null) {
                    VideoView.this.mMediaController.hide();
                }
                if (VideoView.this.mOnErrorListener == null || VideoView.this.mOnErrorListener.onError(VideoView.this.mMediaPlayer, i, i2)) {
                }
                return true;
            }
        };
        this.mBufferingUpdateListener = new OnBufferingUpdateListener() {
            public void onBufferingUpdate(MediaPlayer mediaPlayer, int i) {
                VideoView.this.mCurrentBufferPercentage = i;
            }
        };
        this.gestureDetector = new GestureDetector(getContext(), new SimpleOnGestureListener() {
            public boolean onSingleTapUp(MotionEvent motionEvent) {
                if (VideoView.this.isInPlaybackState() && VideoView.this.mMediaController != null) {
                    VideoView.this.toggleMediaControlsVisiblity();
                }
                return false;
            }
        });
        this.mSHCallback = new Callback() {
            public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i2, int i3) {
                VideoView.this.mSurfaceWidth = i2;
                VideoView.this.mSurfaceHeight = i3;
                boolean z = true;
                boolean z2 = VideoView.this.mTargetState == 3;
                if (!(VideoView.this.mVideoWidth == i2 && VideoView.this.mVideoHeight == i3)) {
                    z = false;
                }
                if (VideoView.this.mMediaPlayer != null && z2 && z) {
                    if (VideoView.this.mSeekWhenPrepared != 0) {
                        VideoView videoView = VideoView.this;
                        videoView.seekTo(videoView.mSeekWhenPrepared);
                    }
                    VideoView.this.start();
                    if (VideoView.this.mMediaController != null) {
                        VideoView.this.mMediaController.show();
                    }
                }
            }

            public void surfaceCreated(SurfaceHolder surfaceHolder) {
                VideoView.this.mSurfaceHolder = surfaceHolder;
                VideoView.this.openVideo();
            }

            public void surfaceDestroyed(SurfaceHolder surfaceHolder) {
                VideoView.this.mSurfaceHolder = null;
                if (VideoView.this.mMediaController != null) {
                    VideoView.this.mMediaController.hide();
                }
                VideoView.this.release(true);
            }
        };
        initVideoView();
    }

    /* access modifiers changed from: protected */
    public void onMeasure(int i, int i2) {
        int i3;
        int i4;
        int defaultSize = getDefaultSize(this.mVideoWidth, i);
        int defaultSize2 = getDefaultSize(this.mVideoHeight, i2);
        if (this.mVideoWidth > 0 && this.mVideoHeight > 0) {
            int mode = MeasureSpec.getMode(i);
            i3 = MeasureSpec.getSize(i);
            int mode2 = MeasureSpec.getMode(i2);
            int size = MeasureSpec.getSize(i2);
            if (mode == 1073741824 && mode2 == 1073741824) {
                int i5 = this.mVideoWidth;
                int i6 = i5 * size;
                int i7 = this.mVideoHeight;
                if (i6 < i3 * i7) {
                    defaultSize = (i5 * size) / i7;
                    defaultSize2 = size;
                } else if (i5 * size > i3 * i7) {
                    i4 = (i7 * i3) / i5;
                    setMeasuredDimension(i3, i4);
                }
            } else if (mode == 1073741824) {
                int i8 = (this.mVideoHeight * i3) / this.mVideoWidth;
                if (mode2 != Integer.MIN_VALUE || i8 <= size) {
                    i4 = i8;
                    setMeasuredDimension(i3, i4);
                }
            } else if (mode2 == 1073741824) {
                int i9 = (this.mVideoWidth * size) / this.mVideoHeight;
                if (mode != Integer.MIN_VALUE || i9 <= i3) {
                    i3 = i9;
                }
            } else {
                int i10 = this.mVideoWidth;
                int i11 = this.mVideoHeight;
                if (mode2 != Integer.MIN_VALUE || i11 <= size) {
                    i4 = i11;
                } else {
                    i10 = (i10 * size) / i11;
                    i4 = size;
                }
                if (mode != Integer.MIN_VALUE || i10 <= i3) {
                    i3 = i10;
                } else {
                    i4 = (this.mVideoHeight * i3) / this.mVideoWidth;
                }
                setMeasuredDimension(i3, i4);
            }
            i4 = size;
            setMeasuredDimension(i3, i4);
        }
        i3 = defaultSize;
        setMeasuredDimension(i3, i4);
    }

    private void initVideoView() {
        this.mVideoWidth = 0;
        this.mVideoHeight = 0;
        getHolder().addCallback(this.mSHCallback);
        getHolder().setType(3);
        setFocusable(true);
        setFocusableInTouchMode(true);
        setClickable(true);
        requestFocus();
        this.mCurrentState = 0;
        this.mTargetState = 0;
    }

    public void setVideoURI(Uri uri, boolean z) {
        this.mUri = uri;
        this.mLooping = z;
        this.mSeekWhenPrepared = 0;
        openVideo();
        requestLayout();
        invalidate();
    }

    public void stopPlayback() {
        MediaPlayer mediaPlayer = this.mMediaPlayer;
        if (mediaPlayer != null) {
            mediaPlayer.stop();
            this.mMediaPlayer.release();
            this.mMediaPlayer = null;
            this.mCurrentState = 0;
            this.mTargetState = 0;
        }
    }

    /* access modifiers changed from: private */
    public void openVideo() {
        if (this.mUri != null && this.mSurfaceHolder != null) {
            release(false);
            try {
                this.mMediaPlayer = new MediaPlayer();
                if (this.mAudioSession != 0) {
                    this.mMediaPlayer.setAudioSessionId(this.mAudioSession);
                } else {
                    this.mAudioSession = this.mMediaPlayer.getAudioSessionId();
                }
                this.mMediaPlayer.setOnPreparedListener(this.mPreparedListener);
                this.mMediaPlayer.setOnVideoSizeChangedListener(this.mSizeChangedListener);
                this.mMediaPlayer.setOnCompletionListener(this.mCompletionListener);
                this.mMediaPlayer.setOnErrorListener(this.mErrorListener);
                this.mMediaPlayer.setOnInfoListener(this.mInfoListener);
                this.mMediaPlayer.setOnBufferingUpdateListener(this.mBufferingUpdateListener);
                this.mCurrentBufferPercentage = 0;
                this.mMediaPlayer.setLooping(this.mLooping);
                this.mMediaPlayer.setDataSource(getContext(), this.mUri);
                this.mMediaPlayer.setDisplay(this.mSurfaceHolder);
                this.mMediaPlayer.setAudioStreamType(3);
                this.mMediaPlayer.setScreenOnWhilePlaying(true);
                this.mMediaPlayer.prepareAsync();
                this.mCurrentState = 1;
                attachMediaController();
            } catch (Exception e) {
                String str = this.TAG;
                StringBuilder sb = new StringBuilder();
                sb.append("Unable to open content: ");
                sb.append(this.mUri);
                Log.w(str, sb.toString(), e);
                this.mCurrentState = -1;
                this.mTargetState = -1;
                this.mErrorListener.onError(this.mMediaPlayer, 1, 0);
            }
        }
    }

    public void setMediaController(VideoControlView videoControlView) {
        VideoControlView videoControlView2 = this.mMediaController;
        if (videoControlView2 != null) {
            videoControlView2.hide();
        }
        this.mMediaController = videoControlView;
        attachMediaController();
    }

    private void attachMediaController() {
        if (this.mMediaPlayer != null) {
            VideoControlView videoControlView = this.mMediaController;
            if (videoControlView != null) {
                videoControlView.setMediaPlayer(this);
                this.mMediaController.setEnabled(isInPlaybackState());
            }
        }
    }

    public boolean onTouchEvent(MotionEvent motionEvent) {
        return this.gestureDetector.onTouchEvent(motionEvent) || super.onTouchEvent(motionEvent);
    }

    public void setOnPreparedListener(OnPreparedListener onPreparedListener) {
        this.mOnPreparedListener = onPreparedListener;
    }

    public void setOnCompletionListener(OnCompletionListener onCompletionListener) {
        this.mOnCompletionListener = onCompletionListener;
    }

    public void setOnErrorListener(OnErrorListener onErrorListener) {
        this.mOnErrorListener = onErrorListener;
    }

    public void setOnInfoListener(OnInfoListener onInfoListener) {
        this.mOnInfoListener = onInfoListener;
    }

    /* access modifiers changed from: private */
    public void release(boolean z) {
        MediaPlayer mediaPlayer = this.mMediaPlayer;
        if (mediaPlayer != null) {
            mediaPlayer.reset();
            this.mMediaPlayer.release();
            this.mMediaPlayer = null;
            this.mCurrentState = 0;
            if (z) {
                this.mTargetState = 0;
            }
        }
    }

    public boolean onKeyDown(int i, KeyEvent keyEvent) {
        boolean z = (i == 4 || i == 24 || i == 25 || i == 82 || i == 5 || i == 6) ? false : true;
        if (isInPlaybackState() && z && this.mMediaController != null) {
            if (i == 79 || i == 85) {
                if (this.mMediaPlayer.isPlaying()) {
                    pause();
                    this.mMediaController.show();
                } else {
                    start();
                    this.mMediaController.hide();
                }
                return true;
            } else if (i == 126) {
                if (!this.mMediaPlayer.isPlaying()) {
                    start();
                    this.mMediaController.hide();
                }
                return true;
            } else if (i == 86 || i == 127) {
                if (this.mMediaPlayer.isPlaying()) {
                    pause();
                    this.mMediaController.show();
                }
                return true;
            } else {
                toggleMediaControlsVisiblity();
            }
        }
        return super.onKeyDown(i, keyEvent);
    }

    /* access modifiers changed from: private */
    public void toggleMediaControlsVisiblity() {
        if (this.mMediaController.isShowing()) {
            this.mMediaController.hide();
        } else {
            this.mMediaController.show();
        }
    }

    public void start() {
        if (isInPlaybackState()) {
            this.mMediaPlayer.start();
            this.mCurrentState = 3;
        }
        this.mTargetState = 3;
    }

    public void pause() {
        if (isInPlaybackState() && this.mMediaPlayer.isPlaying()) {
            this.mMediaPlayer.pause();
            this.mCurrentState = 4;
        }
        this.mTargetState = 4;
    }

    public int getDuration() {
        if (isInPlaybackState()) {
            return this.mMediaPlayer.getDuration();
        }
        return -1;
    }

    public int getCurrentPosition() {
        if (isInPlaybackState()) {
            return this.mMediaPlayer.getCurrentPosition();
        }
        return 0;
    }

    public void seekTo(int i) {
        if (isInPlaybackState()) {
            this.mMediaPlayer.seekTo(i);
            this.mSeekWhenPrepared = 0;
            return;
        }
        this.mSeekWhenPrepared = i;
    }

    public boolean isPlaying() {
        return isInPlaybackState() && this.mMediaPlayer.isPlaying();
    }

    public int getBufferPercentage() {
        if (this.mMediaPlayer != null) {
            return this.mCurrentBufferPercentage;
        }
        return 0;
    }

    /* access modifiers changed from: private */
    public boolean isInPlaybackState() {
        if (this.mMediaPlayer != null) {
            int i = this.mCurrentState;
            if (!(i == -1 || i == 0 || i == 1)) {
                return true;
            }
        }
        return false;
    }
}
