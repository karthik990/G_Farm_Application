package com.mobiroller.helpers;

import android.content.Context;
import android.net.Uri;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.UserProfileChangeRequest.Builder;
import com.mobiroller.models.events.LoginEvent;
import org.greenrobot.eventbus.EventBus;

public class FirebaseUserHelper {
    public static void updateUserProfile(String str, Uri uri, final boolean z, Context context) {
        Builder builder = new Builder();
        FirebaseChatHelper firebaseChatHelper = new FirebaseChatHelper(context);
        if (str != null && !str.isEmpty() && (getDisplayName() == null || !str.equalsIgnoreCase(getDisplayName()))) {
            builder.setDisplayName(str);
            firebaseChatHelper.updateUserFullName();
        }
        if (uri != null && (getPhotoUri() == null || !uri.toString().equalsIgnoreCase(getPhotoUri().toString()))) {
            builder.setPhotoUri(uri);
            firebaseChatHelper.updateUserProfilImage();
        }
        FirebaseAuth.getInstance().getCurrentUser().updateProfile(builder.build()).addOnCompleteListener(new OnCompleteListener<Void>() {
            public void onComplete(Task<Void> task) {
                if (task.isSuccessful() && z) {
                    EventBus.getDefault().post(new LoginEvent());
                }
            }
        });
    }

    private static String getDisplayName() {
        return FirebaseAuth.getInstance().getCurrentUser().getDisplayName();
    }

    private static Uri getPhotoUri() {
        return FirebaseAuth.getInstance().getCurrentUser().getPhotoUrl();
    }
}
