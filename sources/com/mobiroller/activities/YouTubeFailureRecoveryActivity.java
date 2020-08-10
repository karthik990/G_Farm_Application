package com.mobiroller.activities;

import android.content.Intent;
import android.widget.Toast;
import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer.OnInitializedListener;
import com.google.android.youtube.player.YouTubePlayer.Provider;
import com.mobiroller.mobi942763453128.R;

public abstract class YouTubeFailureRecoveryActivity extends YouTubeBaseActivity implements OnInitializedListener {
    private static final int RECOVERY_DIALOG_REQUEST = 1;

    /* access modifiers changed from: protected */
    public abstract Provider getYouTubePlayerProvider();

    public void onInitializationFailure(Provider provider, YouTubeInitializationResult youTubeInitializationResult) {
        if (youTubeInitializationResult.isUserRecoverableError()) {
            youTubeInitializationResult.getErrorDialog(this, 1).show();
        } else {
            Toast.makeText(this, getString(R.string.failed), 1).show();
        }
    }

    /* access modifiers changed from: protected */
    public void onActivityResult(int i, int i2, Intent intent) {
        if (i == 1) {
            getYouTubePlayerProvider().initialize(getString(R.string.youtube_key), this);
        }
    }
}
