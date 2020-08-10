package com.github.chrisbanes.photoview;

import android.os.Build.VERSION;
import android.view.View;

class Compat {
    private static final int SIXTY_FPS_INTERVAL = 16;

    Compat() {
    }

    public static void postOnAnimation(View view, Runnable runnable) {
        if (VERSION.SDK_INT >= 16) {
            postOnAnimationJellyBean(view, runnable);
        } else {
            view.postDelayed(runnable, 16);
        }
    }

    private static void postOnAnimationJellyBean(View view, Runnable runnable) {
        view.postOnAnimation(runnable);
    }
}
