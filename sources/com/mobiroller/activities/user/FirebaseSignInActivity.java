package com.mobiroller.activities.user;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.SignInMethodQueryResult;
import com.mobiroller.helpers.FirebaseChatHelper;
import com.mobiroller.helpers.FirebaseUserHelper;
import com.mobiroller.helpers.SharedPrefHelper;
import com.mobiroller.helpers.UserHelper;
import com.mobiroller.helpers.UtilManager;
import com.mobiroller.mobi942763453128.R;
import com.mobiroller.models.events.ChatLoginEvent;
import com.mobiroller.models.response.UserLoginResponse;
import org.greenrobot.eventbus.EventBus;

public class FirebaseSignInActivity extends AppCompatActivity {
    public static final String INTENT_EXTRA_FROM_GOOGLE_SIGN_IN = "fromGoogleSignIn";
    public static final String INTENT_EXTRA_USER_LOGIN_MODEL = "userLoginModel";
    private static final String TAG = "FirebaseSignInActivity";
    private boolean fromGoogleSignIn = false;
    /* access modifiers changed from: private */
    public FirebaseChatHelper mFirebaseChatHelper;
    private String mPassword = null;
    /* access modifiers changed from: private */
    public SharedPrefHelper mSharedPref;
    /* access modifiers changed from: private */
    public UserLoginResponse mUserLoginResponse;

    /* access modifiers changed from: protected */
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView((int) R.layout.layout_transparent);
        this.mFirebaseChatHelper = new FirebaseChatHelper(this);
        this.mSharedPref = UtilManager.sharedPrefHelper();
        Intent intent = getIntent();
        String str = INTENT_EXTRA_USER_LOGIN_MODEL;
        if (intent.hasExtra(str)) {
            this.mUserLoginResponse = (UserLoginResponse) getIntent().getSerializableExtra(str);
            if (getIntent().hasExtra(INTENT_EXTRA_FROM_GOOGLE_SIGN_IN)) {
                this.fromGoogleSignIn = true;
            }
            checkFirebaseUser();
            return;
        }
        Toast.makeText(this, "Couldn't find the user", 0).show();
        finishActivityWithFailedResult();
    }

    private void checkFirebaseUser() {
        FirebaseAuth.getInstance().fetchSignInMethodsForEmail(this.mUserLoginResponse.email).addOnCompleteListener(new OnCompleteListener<SignInMethodQueryResult>() {
            public void onComplete(Task<SignInMethodQueryResult> task) {
                try {
                    if (((SignInMethodQueryResult) task.getResult()).getSignInMethods().isEmpty()) {
                        FirebaseAuth.getInstance().createUserWithEmailAndPassword(FirebaseSignInActivity.this.mUserLoginResponse.email, UserHelper.getUserId()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            public void onComplete(Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    FirebaseSignInActivity.this.mSharedPref.setUserIsAvailableForChat(true);
                                    FirebaseUserHelper.updateUserProfile(UserHelper.getUserName(), Uri.parse(UserHelper.getUserImageUrl()), true, FirebaseSignInActivity.this);
                                    FirebaseSignInActivity.this.mFirebaseChatHelper.addUser(UserHelper.getUserLoginResponse());
                                    FirebaseSignInActivity.this.mFirebaseChatHelper.checkDatabaseVersionExist();
                                    FirebaseSignInActivity.this.finishActivityWithSuccessResult();
                                    return;
                                }
                                FirebaseSignInActivity.this.mSharedPref.setUserIsAvailableForChat(false);
                                FirebaseSignInActivity.this.finishActivityWithFailedResult();
                            }
                        });
                    } else {
                        FirebaseSignInActivity.this.loginFirebase();
                    }
                } catch (Exception e) {
                    FirebaseSignInActivity.this.mSharedPref.setChatValidated(false);
                    e.printStackTrace();
                    FirebaseSignInActivity.this.finishActivityWithFailedResult();
                }
            }
        });
    }

    /* access modifiers changed from: private */
    public void finishActivityWithSuccessResult() {
        setResult(-1, new Intent());
        EventBus.getDefault().post(new ChatLoginEvent());
        finish();
    }

    /* access modifiers changed from: private */
    public void finishActivityWithFailedResult() {
        setResult(0, new Intent());
        EventBus.getDefault().post(new ChatLoginEvent());
        finish();
    }

    /* access modifiers changed from: private */
    public void loginFirebase() {
        try {
            final String userImageUrl = UserHelper.getUserImageUrl();
            this.mFirebaseChatHelper.checkDatabaseVersionExist();
            FirebaseAuth.getInstance().signInWithEmailAndPassword(this.mUserLoginResponse.email, getUserFirebasePassword()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                public void onComplete(Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        FirebaseSignInActivity.this.mSharedPref.setUserIsAvailableForChat(true);
                        FirebaseUserHelper.updateUserProfile(UserHelper.getUserName(), Uri.parse(userImageUrl), false, FirebaseSignInActivity.this);
                        FirebaseSignInActivity.this.mFirebaseChatHelper.updateUser(FirebaseSignInActivity.this.mUserLoginResponse);
                        FirebaseSignInActivity.this.finishActivityWithSuccessResult();
                        return;
                    }
                    FirebaseSignInActivity.this.mSharedPref.setUserIsAvailableForChat(false);
                    try {
                        throw task.getException();
                    } catch (Exception e) {
                        Log.e(FirebaseSignInActivity.TAG, e.getMessage());
                        FirebaseSignInActivity.this.finishActivityWithFailedResult();
                    } catch (Throwable th) {
                        FirebaseSignInActivity.this.finishActivityWithFailedResult();
                        throw th;
                    }
                }
            });
            this.mSharedPref.setChatValidated(true);
        } catch (Exception unused) {
            this.mSharedPref.setChatValidated(false);
            finishActivityWithFailedResult();
        }
    }

    private void loginFirebaseWithOldPassword() {
        try {
            final String userImageUrl = UserHelper.getUserImageUrl();
            this.mFirebaseChatHelper.checkDatabaseVersionExist();
            FirebaseAuth.getInstance().signInWithEmailAndPassword(this.mUserLoginResponse.email, this.mPassword).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                public void onComplete(Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        FirebaseSignInActivity.this.mSharedPref.setUserIsAvailableForChat(true);
                        FirebaseUserHelper.updateUserProfile(UserHelper.getUserName(), Uri.parse(userImageUrl), false, FirebaseSignInActivity.this);
                        FirebaseSignInActivity.this.mFirebaseChatHelper.updateUser(FirebaseSignInActivity.this.mUserLoginResponse);
                        FirebaseSignInActivity.this.changeFirebasePassword();
                        return;
                    }
                    FirebaseSignInActivity.this.mSharedPref.setUserIsAvailableForChat(false);
                    FirebaseSignInActivity.this.finishActivityWithFailedResult();
                }
            });
            this.mSharedPref.setChatValidated(true);
        } catch (Exception unused) {
            this.mSharedPref.setChatValidated(false);
        }
    }

    /* access modifiers changed from: private */
    public void changeFirebasePassword() {
        try {
            FirebaseAuth.getInstance().getCurrentUser().updatePassword(getUserFirebasePassword()).addOnCompleteListener(new OnCompleteListener<Void>() {
                public void onComplete(Task<Void> task) {
                    if (task.isSuccessful()) {
                        FirebaseSignInActivity.this.finishActivityWithSuccessResult();
                    } else {
                        FirebaseSignInActivity.this.finishActivityWithFailedResult();
                    }
                }
            });
        } catch (Exception unused) {
            finishActivityWithFailedResult();
        }
    }

    private String getUserFirebasePassword() throws Exception {
        if (UserHelper.getUserId() == null) {
            throw new Exception("User id is NULL");
        } else if (UserHelper.getUserId().length() >= 6) {
            return UserHelper.getUserId();
        } else {
            StringBuilder sb = new StringBuilder();
            int length = 6 / UserHelper.getUserId().length();
            for (int i = 0; i < length; i++) {
                sb.append(UserHelper.getUserId());
            }
            return sb.toString();
        }
    }
}
