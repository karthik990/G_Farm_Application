package com.mobiroller.fragments;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import com.braintreepayments.api.models.PostalAddressParser;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.GoogleApiClient.Builder;
import com.google.android.gms.common.api.Scope;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.mobiroller.MobiRollerApplication;
import com.mobiroller.activities.user.UserChangePasswordActivity;
import com.mobiroller.activities.user.UserRegisterActivity;
import com.mobiroller.activities.user.UserResetPasswordActivity;
import com.mobiroller.constants.Constants;
import com.mobiroller.helpers.NetworkHelper;
import com.mobiroller.helpers.RequestHelper;
import com.mobiroller.helpers.SharedPrefHelper;
import com.mobiroller.helpers.UserHelper;
import com.mobiroller.interfaces.FragmentComponent;
import com.mobiroller.mobi942763453128.R;
import com.mobiroller.models.events.ChatLoginEvent;
import com.mobiroller.models.events.LoginEvent;
import com.mobiroller.models.events.RegisterEvent;
import com.mobiroller.models.response.UserLoginResponse;
import com.mobiroller.serviceinterfaces.ApplyzeUserServiceInterface;
import com.mobiroller.util.ErrorUtils;
import com.mobiroller.util.ImageManager;
import com.mobiroller.util.MobirollerIntent;
import com.mobiroller.util.ValidationUtil;
import java.util.HashMap;
import javax.inject.Inject;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import p015br.com.simplepass.loading_button_lib.customViews.CircularProgressButton;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserLoginFragment extends BaseFragment implements OnClickListener {
    private static final int RC_RECOVERABLE = 9002;
    private static final int RC_SIGN_IN = 9001;
    @Inject
    MobiRollerApplication app;
    private int layoutType = 2;
    private EditText mEmail;
    private TextView mForgotPassword;
    /* access modifiers changed from: private */
    public CircularProgressButton mLogin;
    private TextView mOrYouCanText;
    private EditText mPassword;
    private TextView mRegister;
    private RelativeLayout mSignInButton;
    @Inject
    NetworkHelper networkHelper;
    private String screenId;
    @Inject
    SharedPrefHelper sharedPrefHelper;
    private Snackbar snackbar;

    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        View inflate = layoutInflater.inflate(getLayout(), viewGroup, false);
        hideToolbar((Toolbar) inflate.findViewById(R.id.toolbar_top));
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }
        Bundle arguments = getArguments();
        String str = Constants.KEY_SCREEN_ID;
        if (arguments.containsKey(str)) {
            this.screenId = getArguments().getString(str);
        }
        loadUi(inflate);
        loadUserData();
        if (this.sharedPrefHelper.getGoogleSignInActive()) {
            addGoogleSignIn();
        }
        if (!this.sharedPrefHelper.getUserLoginRegistrationActive()) {
            this.mRegister.setVisibility(8);
        }
        this.mLogin.setOnClickListener(this);
        this.mForgotPassword.setOnClickListener(this);
        this.mRegister.setOnClickListener(this);
        this.snackbar = Snackbar.make(getActivity().findViewById(16908290), (CharSequence) "", 0);
        return inflate;
    }

    public Fragment injectFragment(FragmentComponent fragmentComponent) {
        fragmentComponent.inject(this);
        return this;
    }

    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.forgot_password_text) {
            startForgotPasswordActivity();
        } else if (id != R.id.login_button) {
            if (id == R.id.register_text) {
                startActivityForResult(new Intent(getActivity(), UserRegisterActivity.class), 90);
            }
        } else if (validateFields()) {
            login();
        }
    }

    public void hideToolbar(Toolbar toolbar) {
        if (toolbar != null) {
            toolbar.setVisibility(8);
        }
    }

    private void addGoogleSignIn() {
        this.mSignInButton.setVisibility(0);
        this.mOrYouCanText.setVisibility(0);
        this.mSignInButton.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                UserLoginFragment.this.signIn();
            }
        });
        new Builder(getActivity()).addApi(Auth.GOOGLE_SIGN_IN_API, new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().requestScopes(new Scope("https://www.googleapis.com/auth/youtube.readonly"), new Scope("https://www.googleapis.com/auth/youtube.force-ssl")).build()).build();
    }

    public int getLayout() {
        this.layoutType = this.sharedPrefHelper.getLoginLayoutType();
        return this.layoutType == 1 ? R.layout.activity_user_login_1 : R.layout.activity_user_login_2;
    }

    public void onStart() {
        super.onStart();
        this.sharedPrefHelper.getUserEmail();
    }

    public void login() {
        if (this.networkHelper.isConnected()) {
            setButtonStatuses(false);
            this.mLogin.startAnimation();
            ApplyzeUserServiceInterface applyzeUserAPIService = new RequestHelper(getActivity(), this.sharedPrefHelper).getApplyzeUserAPIService();
            HashMap hashMap = new HashMap();
            hashMap.put("apiKey", getString(R.string.applyze_api_key));
            hashMap.put("appKey", getString(R.string.applyze_app_key));
            hashMap.put("udid", this.sharedPrefHelper.getDeviceId());
            hashMap.put("device", this.sharedPrefHelper.getDeviceModel());
            hashMap.put("email", this.mEmail.getText().toString());
            hashMap.put("password", this.mPassword.getText().toString());
            Call login = applyzeUserAPIService.login(hashMap);
            setButtonStatuses(false);
            login.enqueue(new Callback<UserLoginResponse>() {
                public void onResponse(Call<UserLoginResponse> call, Response<UserLoginResponse> response) {
                    UserLoginFragment.this.setButtonStatuses(true);
                    if (response.isSuccessful()) {
                        UserLoginFragment.this.saveInfo((UserLoginResponse) response.body());
                        return;
                    }
                    UserLoginFragment.this.mLogin.revertAnimation();
                    UserLoginFragment.this.displaySnackBarMsg(ErrorUtils.parseError(response).message(), false);
                }

                public void onFailure(Call<UserLoginResponse> call, Throwable th) {
                    UserLoginFragment.this.mLogin.revertAnimation();
                    UserLoginFragment.this.setButtonStatuses(true);
                    UserLoginFragment userLoginFragment = UserLoginFragment.this;
                    userLoginFragment.displaySnackBarMsg(userLoginFragment.getString(R.string.common_error), false);
                }
            });
            return;
        }
        displaySnackBarMsg(getString(R.string.please_check_your_internet_connection), false);
    }

    /* access modifiers changed from: private */
    public void saveInfo(UserLoginResponse userLoginResponse) {
        UserHelper.saveLoginCredentials((AppCompatActivity) getActivity(), userLoginResponse);
        if (!this.sharedPrefHelper.getIsChatActive() || !this.sharedPrefHelper.getChatValidated()) {
            EventBus.getDefault().post(new LoginEvent(this.screenId));
            this.mLogin.revertAnimation();
            setButtonStatuses(true);
        } else {
            startActivityForResult(MobirollerIntent.getFirebaseSignInIntent(getActivity(), userLoginResponse), 134);
        }
        if (userLoginResponse.changePassword) {
            Intent intent = new Intent(getActivity(), UserChangePasswordActivity.class);
            intent.putExtra("forgotPassword", true);
            intent.putExtra("password", this.mPassword.getText().toString());
            startActivity(intent);
        }
    }

    private boolean validateFields() {
        if (this.mEmail.getText().toString().isEmpty()) {
            displaySnackBarMsg(getString(R.string.login_empty_email), false);
            return false;
        } else if (this.mPassword.getText().toString().isEmpty()) {
            displaySnackBarMsg(getString(R.string.login_empty_password), false);
            return false;
        } else if (!ValidationUtil.isValidEmail(this.mEmail.getText().toString())) {
            displaySnackBarMsg(getString(R.string.login_invalid_email), false);
            return false;
        } else if (ValidationUtil.isValidPassword(this.mPassword.getText().toString())) {
            return true;
        } else {
            displaySnackBarMsg(getString(R.string.login_invalid_password), false);
            return false;
        }
    }

    private void startForgotPasswordActivity() {
        startActivity(new Intent(getActivity(), UserResetPasswordActivity.class));
    }

    /* access modifiers changed from: private */
    public void displaySnackBarMsg(String str, boolean z) {
        closeKeyboard();
        Snackbar snackbar2 = this.snackbar;
        if (snackbar2 != null) {
            if (z) {
                snackbar2.getView().setBackgroundColor(ContextCompat.getColor(getActivity(), R.color.snackbar_success));
            } else {
                snackbar2.getView().setBackgroundColor(ContextCompat.getColor(getActivity(), R.color.snackbar_fail));
            }
            this.snackbar.setText((CharSequence) str);
            this.snackbar.show();
        }
    }

    /* access modifiers changed from: private */
    public void setButtonStatuses(boolean z) {
        this.mEmail.setEnabled(z);
        this.mPassword.setEnabled(z);
        this.mForgotPassword.setEnabled(z);
    }

    private void loadUserData() {
        if (!UserHelper.getUserEmail().equals("")) {
            this.mEmail.setText(UserHelper.getUserEmail());
        }
    }

    public void loadUi(View view) {
        Toolbar toolbar = (Toolbar) view.findViewById(R.id.toolbar_top);
        this.mEmail = (EditText) view.findViewById(R.id.email);
        this.mPassword = (EditText) view.findViewById(R.id.password);
        this.mLogin = (CircularProgressButton) view.findViewById(R.id.login_button);
        this.mForgotPassword = (TextView) view.findViewById(R.id.forgot_password_text);
        RelativeLayout relativeLayout = (RelativeLayout) view.findViewById(R.id.login_overlay);
        ImageView imageView = (ImageView) view.findViewById(R.id.login_banner);
        this.mRegister = (TextView) view.findViewById(R.id.register_text);
        this.mOrYouCanText = (TextView) view.findViewById(R.id.or_you_can);
        this.mSignInButton = (RelativeLayout) view.findViewById(R.id.sign_in_button);
        String str = "";
        if (!this.sharedPrefHelper.getLogoURL().equals(str)) {
            Bitmap imageSync = ImageManager.getImageSync(this.sharedPrefHelper.getLogoURL());
            if (imageSync != null) {
                imageView.setImageBitmap(imageSync);
            }
        }
        if (!this.sharedPrefHelper.getLoginBackgroundURL().equals(str)) {
            Bitmap imageSync2 = ImageManager.getImageSync(this.sharedPrefHelper.getLoginBackgroundURL());
            if (imageSync2 != null) {
                relativeLayout.setBackground(new BitmapDrawable(getResources(), imageSync2));
                return;
            }
            return;
        }
        relativeLayout.setBackgroundColor(this.sharedPrefHelper.getActionBarColor());
    }

    @Subscribe
    public void onPostRegisterEvent(RegisterEvent registerEvent) {
        this.mPassword.setText("");
        this.mEmail.setText(registerEvent.email);
    }

    /* access modifiers changed from: private */
    public void signIn() {
        startActivityForResult(MobirollerIntent.getGoogleSignInIntent(getActivity(), ""), 133);
    }

    public void onActivityResult(int i, int i2, Intent intent) {
        if (i != 90) {
            if (i != 133) {
                if (i == 134) {
                    setButtonStatuses(true);
                    this.mLogin.revertAnimation();
                    EventBus.getDefault().post(new LoginEvent(this.screenId));
                } else if (i == RC_SIGN_IN) {
                    handleSignInResult(GoogleSignIn.getSignedInAccountFromIntent(intent), intent.getStringExtra("authAccount"));
                } else if (i == RC_RECOVERABLE) {
                    if (i2 == -1) {
                        Toast.makeText(getActivity(), "icerdesin", 0).show();
                    } else {
                        Toast.makeText(getActivity(), "failed", 0).show();
                    }
                }
            } else if (i2 == -1) {
                EventBus.getDefault().post(new LoginEvent(this.screenId));
            }
        } else if (i2 == -1) {
            Bundle extras = intent.getExtras();
            if (extras != null) {
                this.mEmail.setText(extras.getString("registered_email"));
                this.mPassword.setText("");
            }
        }
    }

    @Subscribe
    public void onPostChatLoginEvent(ChatLoginEvent chatLoginEvent) {
        setButtonStatuses(true);
        this.mLogin.revertAnimation();
        EventBus.getDefault().post(new LoginEvent(this.screenId));
    }

    private void handleSignInResult(Task<GoogleSignInAccount> task, String str) {
        try {
            GoogleSignInAccount googleSignInAccount = (GoogleSignInAccount) task.getResult(ApiException.class);
            this.sharedPrefHelper.setGoogleSignInAccount(googleSignInAccount);
            if (this.networkHelper.isConnected()) {
                setButtonStatuses(false);
                ApplyzeUserServiceInterface applyzeUserAPIService = new RequestHelper(getActivity(), this.sharedPrefHelper).getApplyzeUserAPIService();
                HashMap hashMap = new HashMap();
                hashMap.put("apiKey", getString(R.string.applyze_api_key));
                hashMap.put("appKey", getString(R.string.applyze_app_key));
                hashMap.put("udid", this.sharedPrefHelper.getDeviceId());
                hashMap.put("device", this.sharedPrefHelper.getDeviceModel());
                hashMap.put("email", googleSignInAccount.getEmail());
                hashMap.put("userTokenId", googleSignInAccount.getId());
                if (googleSignInAccount.getPhotoUrl() != null) {
                    hashMap.put("imageUrl", googleSignInAccount.getPhotoUrl().toString());
                }
                hashMap.put(PostalAddressParser.USER_ADDRESS_NAME_KEY, googleSignInAccount.getDisplayName());
                applyzeUserAPIService.loginWithGoogle(hashMap).enqueue(new Callback<UserLoginResponse>() {
                    public void onResponse(Call<UserLoginResponse> call, Response<UserLoginResponse> response) {
                        UserLoginFragment.this.setButtonStatuses(true);
                        if (response.isSuccessful()) {
                            UserLoginFragment.this.saveInfo((UserLoginResponse) response.body());
                            return;
                        }
                        UserLoginFragment.this.mLogin.revertAnimation();
                        UserLoginFragment.this.displaySnackBarMsg(ErrorUtils.parseError(response).message(), false);
                    }

                    public void onFailure(Call<UserLoginResponse> call, Throwable th) {
                        UserLoginFragment.this.mLogin.revertAnimation();
                        UserLoginFragment.this.setButtonStatuses(true);
                        UserLoginFragment userLoginFragment = UserLoginFragment.this;
                        userLoginFragment.displaySnackBarMsg(userLoginFragment.getString(R.string.common_error), false);
                    }
                });
                return;
            }
            displaySnackBarMsg(getString(R.string.please_check_your_internet_connection), false);
        } catch (ApiException unused) {
        }
    }
}
