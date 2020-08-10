package com.mobiroller.activities.user;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.braintreepayments.api.models.PostalAddressParser;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.GoogleApiClient.Builder;
import com.google.android.gms.common.api.Scope;
import com.google.android.gms.tasks.Task;
import com.mobiroller.constants.Constants;
import com.mobiroller.helpers.NetworkHelper;
import com.mobiroller.helpers.RequestHelper;
import com.mobiroller.helpers.SharedPrefHelper;
import com.mobiroller.helpers.SnackbarHelper;
import com.mobiroller.helpers.UserHelper;
import com.mobiroller.helpers.UtilManager;
import com.mobiroller.mobi942763453128.R;
import com.mobiroller.models.events.LoginEvent;
import com.mobiroller.models.response.UserLoginResponse;
import com.mobiroller.serviceinterfaces.ApplyzeUserServiceInterface;
import com.mobiroller.util.ErrorUtils;
import com.mobiroller.util.MobirollerIntent;
import java.util.HashMap;
import org.greenrobot.eventbus.EventBus;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GoogleSignInActivity extends AppCompatActivity {
    public static final String INTENT_EXTRA_SCREEN_ID = "Screen_Id";
    private static final int RC_RECOVERABLE = 9002;
    private static final int RC_SIGN_IN = 9001;
    private GoogleApiClient mGoogleSignInClient;
    private NetworkHelper mNetworkHelper;
    private SharedPrefHelper mSharedPref;
    /* access modifiers changed from: private */
    public View mainLayout;
    private String screenId;

    /* access modifiers changed from: protected */
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView((int) R.layout.layout_transparent);
        this.mainLayout = findViewById(R.id.main_layout);
        this.mNetworkHelper = new NetworkHelper(this);
        this.mSharedPref = UtilManager.sharedPrefHelper();
        Intent intent = getIntent();
        String str = INTENT_EXTRA_SCREEN_ID;
        if (intent.hasExtra(str)) {
            this.screenId = getIntent().getStringExtra(str);
        }
        this.mGoogleSignInClient = new Builder(this).addApi(Auth.GOOGLE_SIGN_IN_API, new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().requestScopes(new Scope("https://www.googleapis.com/auth/youtube.readonly"), new Scope("https://www.googleapis.com/auth/youtube.force-ssl")).build()).build();
        startActivityForResult(Auth.GoogleSignInApi.getSignInIntent(this.mGoogleSignInClient), RC_SIGN_IN);
    }

    /* access modifiers changed from: protected */
    public void onActivityResult(int i, int i2, Intent intent) {
        if (i == 134) {
            String str = Constants.KEY_SCREEN_ID;
            if (i2 == -1) {
                EventBus.getDefault().post(new LoginEvent(this.screenId));
                Intent intent2 = new Intent();
                intent2.putExtra(str, this.screenId);
                setResult(-1, intent2);
                finish();
                return;
            }
            EventBus.getDefault().post(new LoginEvent(this.screenId));
            Intent intent3 = new Intent();
            intent3.putExtra(str, this.screenId);
            setResult(-1, intent3);
            finish();
            Toast.makeText(this, getString(R.string.common_error), 0).show();
        } else if (i == RC_SIGN_IN) {
            handleSignInResult(GoogleSignIn.getSignedInAccountFromIntent(intent));
        }
    }

    private void handleSignInResult(Task<GoogleSignInAccount> task) {
        try {
            GoogleSignInAccount googleSignInAccount = (GoogleSignInAccount) task.getResult(ApiException.class);
            this.mSharedPref.setGoogleSignInAccount(googleSignInAccount);
            if (this.mNetworkHelper.isConnected()) {
                ApplyzeUserServiceInterface applyzeUserAPIService = new RequestHelper(this, this.mSharedPref).getApplyzeUserAPIService();
                HashMap hashMap = new HashMap();
                hashMap.put("apiKey", getString(R.string.applyze_api_key));
                hashMap.put("appKey", getString(R.string.applyze_app_key));
                hashMap.put("udid", this.mSharedPref.getDeviceId());
                hashMap.put("device", this.mSharedPref.getDeviceModel());
                hashMap.put("email", googleSignInAccount.getEmail());
                hashMap.put("userTokenId", googleSignInAccount.getId());
                if (googleSignInAccount.getPhotoUrl() != null) {
                    hashMap.put("imageUrl", googleSignInAccount.getPhotoUrl().toString());
                }
                hashMap.put(PostalAddressParser.USER_ADDRESS_NAME_KEY, googleSignInAccount.getDisplayName());
                applyzeUserAPIService.loginWithGoogle(hashMap).enqueue(new Callback<UserLoginResponse>() {
                    public void onResponse(Call<UserLoginResponse> call, Response<UserLoginResponse> response) {
                        if (response.isSuccessful()) {
                            GoogleSignInActivity.this.saveInfo((UserLoginResponse) response.body());
                            return;
                        }
                        Log.d("error message", ErrorUtils.parseError(response).message());
                    }

                    public void onFailure(Call<UserLoginResponse> call, Throwable th) {
                        GoogleSignInActivity googleSignInActivity = GoogleSignInActivity.this;
                        SnackbarHelper.displaySnackBarMsgFinishActivity(googleSignInActivity, googleSignInActivity.mainLayout, GoogleSignInActivity.this.getString(R.string.common_error), false);
                    }
                });
                return;
            }
            SnackbarHelper.displaySnackBarMsgFinishActivity(this, this.mainLayout, getString(R.string.please_check_your_internet_connection), false);
        } catch (ApiException e) {
            if (e.getStatusCode() == 12500) {
                SnackbarHelper.displaySnackBarMsgFinishActivity(this, this.mainLayout, getString(R.string.invalid_google_sign_in_settings), false);
            } else {
                finish();
            }
        }
    }

    public void saveInfo(UserLoginResponse userLoginResponse) {
        UserHelper.saveLoginCredentials(this, userLoginResponse);
        if (!this.mSharedPref.getIsChatActive() || !this.mSharedPref.getChatValidated()) {
            EventBus.getDefault().post(new LoginEvent(this.screenId));
            Intent intent = new Intent();
            intent.putExtra(Constants.KEY_SCREEN_ID, this.screenId);
            setResult(-1, intent);
            finish();
            return;
        }
        startActivityForResult(MobirollerIntent.getFirebaseSignInIntent(this, userLoginResponse), 134);
    }
}
