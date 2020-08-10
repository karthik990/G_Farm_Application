package com.firebase.p037ui.auth.data.remote;

import android.net.Uri;
import android.text.TextUtils;
import com.firebase.p037ui.auth.IdpResponse;
import com.firebase.p037ui.auth.data.model.User;
import com.firebase.p037ui.auth.util.data.TaskFailureLogger;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest.Builder;

/* renamed from: com.firebase.ui.auth.data.remote.ProfileMerger */
public class ProfileMerger implements Continuation<AuthResult, Task<AuthResult>> {
    private static final String TAG = "ProfileMerger";
    private final IdpResponse mResponse;

    public ProfileMerger(IdpResponse idpResponse) {
        this.mResponse = idpResponse;
    }

    public Task<AuthResult> then(Task<AuthResult> task) {
        final AuthResult authResult = (AuthResult) task.getResult();
        FirebaseUser user = authResult.getUser();
        String displayName = user.getDisplayName();
        Uri photoUrl = user.getPhotoUrl();
        if (!TextUtils.isEmpty(displayName) && photoUrl != null) {
            return Tasks.forResult(authResult);
        }
        User user2 = this.mResponse.getUser();
        if (TextUtils.isEmpty(displayName)) {
            displayName = user2.getName();
        }
        if (photoUrl == null) {
            photoUrl = user2.getPhotoUri();
        }
        return user.updateProfile(new Builder().setDisplayName(displayName).setPhotoUri(photoUrl).build()).addOnFailureListener(new TaskFailureLogger(TAG, "Error updating profile")).continueWithTask(new Continuation<Void, Task<AuthResult>>() {
            public Task<AuthResult> then(Task<Void> task) {
                return Tasks.forResult(authResult);
            }
        });
    }
}
