package com.twitter.sdk.android.tweetui;

import android.app.Activity;
import android.os.Bundle;
import com.twitter.sdk.android.tweetui.internal.SwipeToDismissTouchListener.Callback;
import java.io.Serializable;

public class PlayerActivity extends Activity {
    public static final String PLAYER_ITEM = "PLAYER_ITEM";
    PlayerController playerController;

    public static class PlayerItem implements Serializable {
        public final String callToActionText;
        public final String callToActionUrl;
        public final boolean looping;
        public final boolean showVideoControls;
        public final String url;

        public PlayerItem(String str, boolean z, boolean z2, String str2, String str3) {
            this.url = str;
            this.looping = z;
            this.showVideoControls = z2;
            this.callToActionText = str2;
            this.callToActionUrl = str3;
        }
    }

    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(C5234R.layout.tw__player_activity);
        PlayerItem playerItem = (PlayerItem) getIntent().getSerializableExtra(PLAYER_ITEM);
        this.playerController = new PlayerController(findViewById(16908290), new Callback() {
            public void onMove(float f) {
            }

            public void onDismiss() {
                PlayerActivity.this.finish();
                PlayerActivity.this.overridePendingTransition(0, C5234R.anim.tw__slide_out);
            }
        });
        this.playerController.prepare(playerItem);
    }

    /* access modifiers changed from: protected */
    public void onResume() {
        super.onResume();
        this.playerController.onResume();
    }

    /* access modifiers changed from: protected */
    public void onPause() {
        this.playerController.onPause();
        super.onPause();
    }

    public void onDestroy() {
        this.playerController.onDestroy();
        super.onDestroy();
    }

    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(0, C5234R.anim.tw__slide_out);
    }
}
