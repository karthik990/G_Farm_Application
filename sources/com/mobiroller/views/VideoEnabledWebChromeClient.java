package com.mobiroller.views;

import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.media.MediaPlayer.OnErrorListener;
import android.media.MediaPlayer.OnPreparedListener;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.webkit.WebChromeClient;
import android.webkit.WebChromeClient.CustomViewCallback;
import android.widget.FrameLayout;
import android.widget.VideoView;

public class VideoEnabledWebChromeClient extends WebChromeClient implements OnPreparedListener, OnCompletionListener, OnErrorListener {
    private View activityNonVideoView;
    private ViewGroup activityVideoView;
    private boolean isVideoFullscreen;
    private View loadingView;
    private ToggledFullscreenCallback toggledFullscreenCallback;
    private CustomViewCallback videoViewCallback;
    private FrameLayout videoViewContainer;
    private VideoEnabledWebView webView;

    public interface ToggledFullscreenCallback {
        void toggledFullscreen(boolean z);
    }

    public boolean onError(MediaPlayer mediaPlayer, int i, int i2) {
        return false;
    }

    public VideoEnabledWebChromeClient() {
    }

    public VideoEnabledWebChromeClient(View view, ViewGroup viewGroup) {
        this.activityNonVideoView = view;
        this.activityVideoView = viewGroup;
        this.loadingView = null;
        this.webView = null;
        this.isVideoFullscreen = false;
    }

    public VideoEnabledWebChromeClient(View view, ViewGroup viewGroup, View view2) {
        this.activityNonVideoView = view;
        this.activityVideoView = viewGroup;
        this.loadingView = view2;
        this.webView = null;
        this.isVideoFullscreen = false;
    }

    public VideoEnabledWebChromeClient(View view, ViewGroup viewGroup, View view2, VideoEnabledWebView videoEnabledWebView) {
        this.activityNonVideoView = view;
        this.activityVideoView = viewGroup;
        this.loadingView = view2;
        this.webView = videoEnabledWebView;
        this.isVideoFullscreen = false;
    }

    public boolean isVideoFullscreen() {
        return this.isVideoFullscreen;
    }

    public void setOnToggledFullscreen(ToggledFullscreenCallback toggledFullscreenCallback2) {
        this.toggledFullscreenCallback = toggledFullscreenCallback2;
    }

    public void onShowCustomView(View view, CustomViewCallback customViewCallback) {
        if (view instanceof FrameLayout) {
            FrameLayout frameLayout = (FrameLayout) view;
            View focusedChild = frameLayout.getFocusedChild();
            this.isVideoFullscreen = true;
            this.videoViewContainer = frameLayout;
            this.videoViewCallback = customViewCallback;
            this.activityNonVideoView.setVisibility(8);
            this.activityVideoView.addView(this.videoViewContainer, new LayoutParams(-1, -1));
            this.activityVideoView.setVisibility(0);
            if (focusedChild instanceof VideoView) {
                VideoView videoView = (VideoView) focusedChild;
                videoView.setOnPreparedListener(this);
                videoView.setOnCompletionListener(this);
                videoView.setOnErrorListener(this);
            } else {
                VideoEnabledWebView videoEnabledWebView = this.webView;
                if (videoEnabledWebView != null && videoEnabledWebView.getSettings().getJavaScriptEnabled() && (focusedChild instanceof SurfaceView)) {
                    StringBuilder sb = new StringBuilder();
                    sb.append("javascript:");
                    sb.append("var _ytrp_html5_video_last;");
                    String sb2 = sb.toString();
                    StringBuilder sb3 = new StringBuilder();
                    sb3.append(sb2);
                    sb3.append("var _ytrp_html5_video = document.getElementsByTagName('video')[0];");
                    String sb4 = sb3.toString();
                    StringBuilder sb5 = new StringBuilder();
                    sb5.append(sb4);
                    sb5.append("if (_ytrp_html5_video != undefined && _ytrp_html5_video != _ytrp_html5_video_last) {");
                    String sb6 = sb5.toString();
                    StringBuilder sb7 = new StringBuilder();
                    sb7.append(sb6);
                    sb7.append("_ytrp_html5_video_last = _ytrp_html5_video;");
                    String sb8 = sb7.toString();
                    StringBuilder sb9 = new StringBuilder();
                    sb9.append(sb8);
                    sb9.append("function _ytrp_html5_video_ended() {");
                    String sb10 = sb9.toString();
                    StringBuilder sb11 = new StringBuilder();
                    sb11.append(sb10);
                    sb11.append("_VideoEnabledWebView.notifyVideoEnd();");
                    String sb12 = sb11.toString();
                    StringBuilder sb13 = new StringBuilder();
                    sb13.append(sb12);
                    String str = "}";
                    sb13.append(str);
                    String sb14 = sb13.toString();
                    StringBuilder sb15 = new StringBuilder();
                    sb15.append(sb14);
                    sb15.append("_ytrp_html5_video.addEventListener('ended', _ytrp_html5_video_ended);");
                    String sb16 = sb15.toString();
                    StringBuilder sb17 = new StringBuilder();
                    sb17.append(sb16);
                    sb17.append(str);
                    this.webView.loadUrl(sb17.toString());
                }
            }
            ToggledFullscreenCallback toggledFullscreenCallback2 = this.toggledFullscreenCallback;
            if (toggledFullscreenCallback2 != null) {
                toggledFullscreenCallback2.toggledFullscreen(true);
            }
        }
    }

    public void onShowCustomView(View view, int i, CustomViewCallback customViewCallback) {
        onShowCustomView(view, customViewCallback);
    }

    public void onHideCustomView() {
        if (this.isVideoFullscreen) {
            this.activityVideoView.setVisibility(8);
            this.activityVideoView.removeView(this.videoViewContainer);
            this.activityNonVideoView.setVisibility(0);
            CustomViewCallback customViewCallback = this.videoViewCallback;
            if (customViewCallback != null && !customViewCallback.getClass().getName().contains(".chromium.")) {
                this.videoViewCallback.onCustomViewHidden();
            }
            this.isVideoFullscreen = false;
            this.videoViewContainer = null;
            this.videoViewCallback = null;
            ToggledFullscreenCallback toggledFullscreenCallback2 = this.toggledFullscreenCallback;
            if (toggledFullscreenCallback2 != null) {
                toggledFullscreenCallback2.toggledFullscreen(false);
            }
        }
    }

    public View getVideoLoadingProgressView() {
        View view = this.loadingView;
        if (view == null) {
            return super.getVideoLoadingProgressView();
        }
        view.setVisibility(0);
        return this.loadingView;
    }

    public void onPrepared(MediaPlayer mediaPlayer) {
        View view = this.loadingView;
        if (view != null) {
            view.setVisibility(8);
        }
    }

    public void onCompletion(MediaPlayer mediaPlayer) {
        onHideCustomView();
    }

    public boolean onBackPressed() {
        if (!this.isVideoFullscreen) {
            return false;
        }
        onHideCustomView();
        return true;
    }
}
