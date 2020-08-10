package com.pierfrancescosoffritti.androidyoutubeplayer.player.playerUtils;

import android.view.View;
import android.view.ViewGroup.LayoutParams;
import com.pierfrancescosoffritti.androidyoutubeplayer.player.listeners.YouTubePlayerFullScreenListener;
import java.util.HashSet;
import java.util.Set;

public class FullScreenHelper {

    /* renamed from: a */
    private boolean f2298a = false;

    /* renamed from: b */
    private final Set<YouTubePlayerFullScreenListener> f2299b = new HashSet();

    public boolean addFullScreenListener(YouTubePlayerFullScreenListener youTubePlayerFullScreenListener) {
        return this.f2299b.add(youTubePlayerFullScreenListener);
    }

    public void enterFullScreen(View view) {
        if (!this.f2298a) {
            LayoutParams layoutParams = view.getLayoutParams();
            layoutParams.height = -1;
            layoutParams.width = -1;
            view.setLayoutParams(layoutParams);
            this.f2298a = true;
            for (YouTubePlayerFullScreenListener onYouTubePlayerEnterFullScreen : this.f2299b) {
                onYouTubePlayerEnterFullScreen.onYouTubePlayerEnterFullScreen();
            }
        }
    }

    public void exitFullScreen(View view) {
        if (this.f2298a) {
            LayoutParams layoutParams = view.getLayoutParams();
            layoutParams.height = -2;
            layoutParams.width = -1;
            view.setLayoutParams(layoutParams);
            this.f2298a = false;
            for (YouTubePlayerFullScreenListener onYouTubePlayerExitFullScreen : this.f2299b) {
                onYouTubePlayerExitFullScreen.onYouTubePlayerExitFullScreen();
            }
        }
    }

    public boolean isFullScreen() {
        return this.f2298a;
    }

    public boolean removeFullScreenListener(YouTubePlayerFullScreenListener youTubePlayerFullScreenListener) {
        return this.f2299b.remove(youTubePlayerFullScreenListener);
    }

    public void toggleFullScreen(View view) {
        if (this.f2298a) {
            exitFullScreen(view);
        } else {
            enterFullScreen(view);
        }
    }
}
