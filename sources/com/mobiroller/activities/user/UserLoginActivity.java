package com.mobiroller.activities.user;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.afollestad.materialdialogs.MaterialDialog.Builder;
import com.afollestad.materialdialogs.MaterialDialog.SingleButtonCallback;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener;
import com.google.android.material.snackbar.Snackbar;
import com.mobiroller.DynamicConstants;
import com.mobiroller.MobiRollerApplication;
import com.mobiroller.activities.base.AveActivity;
import com.mobiroller.constants.Constants;
import com.mobiroller.helpers.AppSettingsHelper;
import com.mobiroller.helpers.FirebaseChatHelper;
import com.mobiroller.helpers.NetworkHelper;
import com.mobiroller.helpers.RequestHelper;
import com.mobiroller.helpers.SharedPrefHelper;
import com.mobiroller.helpers.UserHelper;
import com.mobiroller.helpers.UtilManager;
import com.mobiroller.interfaces.ActivityComponent;
import com.mobiroller.mobi942763453128.R;
import com.mobiroller.models.response.UserLoginResponse;
import com.mobiroller.serviceinterfaces.ApplyzeUserServiceInterface;
import com.mobiroller.util.DialogUtil;
import com.mobiroller.util.DialogUtil.ExitDialog;
import com.mobiroller.util.ECommerceUtil;
import com.mobiroller.util.ErrorUtils;
import com.mobiroller.util.ImageManager;
import com.mobiroller.util.MobirollerIntent;
import com.mobiroller.util.PreviewUtil;
import com.mobiroller.util.ValidationUtil;
import java.util.HashMap;
import javax.inject.Inject;
import p015br.com.simplepass.loading_button_lib.customViews.CircularProgressButton;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserLoginActivity extends AveActivity implements OnClickListener, OnConnectionFailedListener, ExitDialog {
    public static final int REGISTER_FIRST = 642;
    public static final int USER_LOGIN_REQUEST = 645;
    @Inject
    MobiRollerApplication app;
    FirebaseChatHelper firebaseChatHelper;
    private boolean isBackAvailable = true;
    private EditText mEmail;
    private TextView mForgotPassword;
    /* access modifiers changed from: private */
    public CircularProgressButton mLogin;
    private RelativeLayout mLoginOverlay;
    private TextView mOrYouCanText;
    private EditText mPassword;
    private TextView mRegister;
    private RelativeLayout mSignInButton;
    private Snackbar mSnackbar;
    private Toolbar mToolbar;
    @Inject
    NetworkHelper networkHelper;
    @Inject
    SharedPrefHelper sharedPrefHelper;

    public void onConnectionFailed(ConnectionResult connectionResult) {
    }

    /* access modifiers changed from: protected */
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(getLayout());
        if (getIntent().hasExtra("RegisterFirst")) {
            startActivityForResult(new Intent(this, UserRegisterActivity.class), REGISTER_FIRST);
        }
        loadUi();
        this.isBackAvailable = !getIntent().hasExtra(Constants.INTENT_EXTRA_IS_BACK_AVAILABLE);
        if (this.sharedPrefHelper.getGoogleSignInActive()) {
            addGoogleSignIn();
        }
        String str = "";
        if (this.isBackAvailable) {
            setToolbar();
        } else {
            setSupportActionBar(this.mToolbar);
            this.mToolbar.setBackgroundColor(ContextCompat.getColor(this, R.color.transparent));
            this.mToolbar.setNavigationIcon((int) R.drawable.ic_arrow_back_white_24dp);
            if (VERSION.SDK_INT >= 19) {
                this.mToolbar.getNavigationIcon().setAutoMirrored(true);
            }
            this.mToolbar.setTitle((CharSequence) str);
            this.mToolbar.setNavigationOnClickListener(new OnClickListener() {
                public void onClick(View view) {
                    if (UserLoginActivity.this.sharedPrefHelper.getAskBeforeExit()) {
                        new Builder(UserLoginActivity.this).title((int) R.string.app_name).content((int) R.string.action_close_app).positiveText((int) R.string.yes).negativeText((int) R.string.cancel).onPositive(new SingleButtonCallback() {
                            public void onClick(MaterialDialog materialDialog, DialogAction dialogAction) {
                                UserLoginActivity.this.finishAffinity();
                                System.exit(0);
                            }
                        }).show();
                        return;
                    }
                    UserLoginActivity.this.finishAffinity();
                    System.exit(0);
                }
            });
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setTitle((CharSequence) str);
            if (VERSION.SDK_INT >= 21) {
                getWindow().addFlags(Integer.MIN_VALUE);
                getWindow().clearFlags(1024);
                getWindow().clearFlags(67108864);
                getWindow().setStatusBarColor(ContextCompat.getColor(this, R.color.black));
            }
        }
        loadUserData();
        this.firebaseChatHelper = new FirebaseChatHelper(getApplicationContext());
        if (!this.sharedPrefHelper.getUserLoginRegistrationActive()) {
            this.mRegister.setVisibility(8);
        }
        this.mLogin.setOnClickListener(this);
        this.mForgotPassword.setOnClickListener(this);
        this.mRegister.setOnClickListener(this);
        this.mSnackbar = Snackbar.make((View) this.mLoginOverlay, (CharSequence) str, 0);
    }

    private void addGoogleSignIn() {
        this.mSignInButton.setVisibility(0);
        this.mOrYouCanText.setVisibility(0);
        this.mSignInButton.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                if (DynamicConstants.MobiRoller_Stage) {
                    PreviewUtil.showNotSupportedDialog(UserLoginActivity.this);
                } else if (!UtilManager.networkHelper().isConnected()) {
                    DialogUtil.showNoConnectionInfo(UserLoginActivity.this);
                } else {
                    UserLoginActivity.this.signIn();
                }
            }
        });
    }

    public AppCompatActivity injectActivity(ActivityComponent activityComponent) {
        activityComponent.inject(this);
        return this;
    }

    public void onClick(View view) {
        if (DynamicConstants.MobiRoller_Stage) {
            PreviewUtil.showNotSupportedDialog(this);
            return;
        }
        int id = view.getId();
        if (id == R.id.forgot_password_text) {
            startForgotPasswordActivity();
        } else if (id != R.id.login_button) {
            if (id == R.id.register_text) {
                startActivityForResult(new Intent(this, UserRegisterActivity.class), REGISTER_FIRST);
            }
        } else if (validateFields()) {
            login();
        }
    }

    public void hideToolbar(Toolbar toolbar) {
        toolbar.setVisibility(8);
    }

    public int getLayout() {
        return this.sharedPrefHelper.getLoginLayoutType() == 1 ? R.layout.activity_user_login_1 : R.layout.activity_user_login_2;
    }

    /* access modifiers changed from: protected */
    public void onActivityResult(int i, int i2, Intent intent) {
        if (i != 133) {
            if (i == 134) {
                setButtonStatuses(true);
                this.mLogin.revertAnimation();
                setResult(-1, intent);
                finish();
            } else if (i == 642) {
                if (i2 == -1) {
                    Bundle extras = intent.getExtras();
                    if (extras != null) {
                        this.mEmail.setText(extras.getString("registered_email"));
                        this.mPassword.setText("");
                    }
                } else if (i2 == 33) {
                    finish();
                }
            }
        } else if (i2 == -1) {
            setResult(-1, intent);
            finish();
        }
    }

    public void login() {
        if (this.networkHelper.isConnected()) {
            this.mLogin.startAnimation();
            ApplyzeUserServiceInterface applyzeUserAPIService = new RequestHelper(this, this.sharedPrefHelper).getApplyzeUserAPIService();
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
                    UserLoginActivity.this.setButtonStatuses(true);
                    if (response.isSuccessful()) {
                        UserLoginActivity.this.saveInfo((UserLoginResponse) response.body());
                        return;
                    }
                    UserLoginActivity.this.mLogin.revertAnimation();
                    UserLoginActivity.this.displaySnackBarMsg(ErrorUtils.parseError(response).message(), false);
                }

                public void onFailure(Call<UserLoginResponse> call, Throwable th) {
                    UserLoginActivity.this.mLogin.revertAnimation();
                    UserLoginActivity.this.setButtonStatuses(true);
                    UserLoginActivity userLoginActivity = UserLoginActivity.this;
                    userLoginActivity.displaySnackBarMsg(userLoginActivity.getString(R.string.common_error), false);
                }
            });
            return;
        }
        displaySnackBarMsg(getString(R.string.please_check_your_internet_connection), false);
    }

    public void saveInfo(UserLoginResponse userLoginResponse) {
        UserHelper.saveLoginCredentials(this, userLoginResponse);
        if (AppSettingsHelper.isECommerceActive()) {
            new ECommerceUtil().getBadgeCount();
        }
        if (!this.sharedPrefHelper.getChatValidated() || !this.sharedPrefHelper.getIsChatActive()) {
            this.mLogin.revertAnimation();
            setButtonStatuses(true);
            if (userLoginResponse.changePassword) {
                Intent intent = new Intent(this, UserChangePasswordActivity.class);
                intent.putExtra("forgotPassword", true);
                intent.putExtra("password", this.mPassword.getText().toString());
                startActivity(intent);
            }
            setResult(-1, new Intent());
            finish();
            return;
        }
        startActivityForResult(MobirollerIntent.getFirebaseSignInIntent(this, userLoginResponse), 134);
    }

    public boolean validateFields() {
        closeKeyboard();
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
        startActivity(new Intent(this, UserResetPasswordActivity.class));
    }

    public void displaySnackBarMsg(String str, boolean z) {
        closeKeyboard();
        Snackbar snackbar = this.mSnackbar;
        if (snackbar != null) {
            if (z) {
                snackbar.getView().setBackgroundColor(ContextCompat.getColor(this, R.color.snackbar_success));
            } else {
                snackbar.getView().setBackgroundColor(ContextCompat.getColor(this, R.color.snackbar_fail));
            }
            this.mSnackbar.setText((CharSequence) str);
            this.mSnackbar.show();
        }
    }

    /* access modifiers changed from: private */
    public void setButtonStatuses(boolean z) {
        this.mEmail.setEnabled(z);
        this.mPassword.setEnabled(z);
        this.mForgotPassword.setEnabled(z);
    }

    public void onBackPressed() {
        if (this.isBackAvailable) {
            super.onBackPressed();
        } else if (this.sharedPrefHelper.getAskBeforeExit()) {
            DialogUtil.showExitDialog(this, this);
        } else {
            finishAffinity();
            System.exit(0);
        }
    }

    public void loadUserData() {
        String str = "";
        if (!UserHelper.getUserEmail().equals(str)) {
            this.mEmail.setText(UserHelper.getUserEmail());
        }
        String str2 = "registered_email";
        if (getIntent().hasExtra(str2)) {
            this.mEmail.setText(getIntent().getStringExtra(str2));
            this.mPassword.setText(str);
        }
    }

    public void loadUi() {
        this.mToolbar = (Toolbar) findViewById(R.id.toolbar_top);
        this.mEmail = (EditText) findViewById(R.id.email);
        this.mPassword = (EditText) findViewById(R.id.password);
        this.mLogin = (CircularProgressButton) findViewById(R.id.login_button);
        this.mForgotPassword = (TextView) findViewById(R.id.forgot_password_text);
        this.mLoginOverlay = (RelativeLayout) findViewById(R.id.login_overlay);
        ImageView imageView = (ImageView) findViewById(R.id.login_banner);
        this.mRegister = (TextView) findViewById(R.id.register_text);
        this.mOrYouCanText = (TextView) findViewById(R.id.or_you_can);
        this.mSignInButton = (RelativeLayout) findViewById(R.id.sign_in_button);
        this.mPassword.setOnEditorActionListener(new OnEditorActionListener() {
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                if (i != 6) {
                    return false;
                }
                UserLoginActivity.this.mLogin.performClick();
                return true;
            }
        });
        String str = "";
        if (!this.sharedPrefHelper.getLogoURL().equals(str)) {
            Bitmap imageSync = ImageManager.getImageSync(this.sharedPrefHelper.getLogoURL());
            if (imageSync != null) {
                imageView.setImageBitmap(imageSync);
            }
        }
        if (!this.sharedPrefHelper.getLoginBackgroundURL().equals(str)) {
            ImageManager.loadBackgroundImage(this.sharedPrefHelper.getLoginBackgroundURL(), this.mLoginOverlay);
        } else {
            this.mLoginOverlay.setBackgroundColor(this.sharedPrefHelper.getActionBarColor());
        }
    }

    public void setToolbar() {
        setSupportActionBar(this.mToolbar);
        this.mToolbar.setBackgroundColor(ContextCompat.getColor(this, R.color.transparent));
        this.mToolbar.setNavigationIcon((int) R.drawable.ic_arrow_back_white_24dp);
        String str = "";
        this.mToolbar.setTitle((CharSequence) str);
        this.mToolbar.setNavigationOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                UserLoginActivity.this.finish();
            }
        });
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle((CharSequence) str);
        if (VERSION.SDK_INT >= 21) {
            getWindow().addFlags(Integer.MIN_VALUE);
            getWindow().clearFlags(1024);
            getWindow().clearFlags(67108864);
            getWindow().setStatusBarColor(ContextCompat.getColor(this, R.color.black));
        }
    }

    /* access modifiers changed from: private */
    public void signIn() {
        startActivityForResult(MobirollerIntent.getGoogleSignInIntent(this, ""), 133);
    }

    public void onExitApp() {
        finishAffinity();
        System.exit(0);
    }
}
