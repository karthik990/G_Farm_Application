package com.pierfrancescosoffritti.androidyoutubeplayer.p051ui;

import android.graphics.drawable.Drawable;
import android.view.View;
import android.view.View.OnClickListener;
import com.pierfrancescosoffritti.androidyoutubeplayer.p051ui.menu.YouTubePlayerMenu;

/* renamed from: com.pierfrancescosoffritti.androidyoutubeplayer.ui.PlayerUIController */
public interface PlayerUIController {
    void addView(View view);

    void enableLiveVideoUI(boolean z);

    YouTubePlayerMenu getMenu();

    void removeView(View view);

    void setCustomAction1(Drawable drawable, OnClickListener onClickListener);

    void setCustomAction2(Drawable drawable, OnClickListener onClickListener);

    void setFullScreenButtonClickListener(OnClickListener onClickListener);

    void setMenu(YouTubePlayerMenu youTubePlayerMenu);

    void setMenuButtonClickListener(OnClickListener onClickListener);

    void setVideoTitle(String str);

    void showBufferingProgress(boolean z);

    void showCurrentTime(boolean z);

    void showCustomAction1(boolean z);

    void showCustomAction2(boolean z);

    void showDuration(boolean z);

    void showFullscreenButton(boolean z);

    void showMenuButton(boolean z);

    void showPlayPauseButton(boolean z);

    void showSeekBar(boolean z);

    void showUI(boolean z);

    void showVideoTitle(boolean z);

    void showYouTubeButton(boolean z);
}
