package com.mobiroller.activities.chat;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import com.mobiroller.MobiRollerApplication;
import com.mobiroller.activities.SplashApp;
import com.mobiroller.activities.aveChatView;
import com.mobiroller.constants.ChatConstants;
import com.mobiroller.constants.Constants;
import com.mobiroller.fragments.chat.ChatMessageListFragment;
import com.mobiroller.fragments.chat.ChatUserListFragment;
import com.mobiroller.models.chat.ChatNotificationModel;

public class ChatNotificationHandlerActivity extends AppCompatActivity {
    private ChatNotificationModel chatNotificationModel;

    /* access modifiers changed from: protected */
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        getIntentValues();
        if (MobiRollerApplication.isAppBackgroundForChat()) {
            startActivity(getSplashIntent());
            finish();
        } else if (ChatUserListFragment.isVisible || ChatMessageListFragment.isVisible) {
            startActivity(getChatActivity());
            finish();
        } else {
            startActivity(getAveChatActivity());
            finish();
        }
    }

    private void getIntentValues() {
        this.chatNotificationModel = (ChatNotificationModel) getIntent().getSerializableExtra(ChatConstants.ARG_NOTIFICATION_MODEL);
    }

    private Intent getSplashIntent() {
        Intent intent = new Intent(this, SplashApp.class);
        intent.putExtra(ChatConstants.ARG_NOTIFICATION_MODEL, this.chatNotificationModel);
        return intent;
    }

    private Intent getAveChatActivity() {
        Intent intent = new Intent(this, aveChatView.class);
        intent.putExtra(Constants.KEY_SCREEN_ID, this.chatNotificationModel.getScreenId());
        intent.putExtra(ChatConstants.ARG_NOTIFICATION_MODEL, this.chatNotificationModel);
        return intent;
    }

    private Intent getChatActivity() {
        Intent intent = new Intent(this, ChatActivity.class);
        intent.putExtra(Constants.KEY_SCREEN_ID, this.chatNotificationModel.getScreenId());
        intent.putExtra(ChatConstants.ARG_FIREBASE_USER_UID, this.chatNotificationModel.getSenderUid());
        intent.addFlags(536870912);
        return intent;
    }
}
