package com.mobiroller.activities.user;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import com.afollestad.materialdialogs.MaterialDialog;
import com.afollestad.materialdialogs.MaterialDialog.Builder;
import com.mobiroller.MobiRollerApplication;
import com.mobiroller.activities.aveWebView;
import com.mobiroller.activities.base.AveActivity;
import com.mobiroller.helpers.LocaleHelper;
import com.mobiroller.helpers.LocalizationHelper;
import com.mobiroller.helpers.NetworkHelper;
import com.mobiroller.helpers.RequestHelper;
import com.mobiroller.helpers.SharedPrefHelper;
import com.mobiroller.helpers.SnackbarHelper;
import com.mobiroller.interfaces.ActivityComponent;
import com.mobiroller.mobi942763453128.R;
import com.mobiroller.models.WebContent;
import com.mobiroller.models.events.RegisterEvent;
import com.mobiroller.models.response.APIError;
import com.mobiroller.models.response.UserLoginResponse;
import com.mobiroller.models.response.UserProfileElement;
import com.mobiroller.serviceinterfaces.ApplyzeUserServiceInterface;
import com.mobiroller.util.ErrorUtils;
import com.mobiroller.util.ImageManager;
import com.mobiroller.util.MobirollerIntent;
import com.mobiroller.util.ValidationUtil;
import java.util.HashMap;
import java.util.List;
import javax.inject.Inject;
import org.greenrobot.eventbus.EventBus;
import p015br.com.simplepass.loading_button_lib.customViews.CircularProgressButton;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserRegisterActivity extends AveActivity implements OnClickListener {
    @Inject
    MobiRollerApplication app;
    @Inject
    LocalizationHelper localizationHelper;
    /* access modifiers changed from: private */
    public EditText mEmail;
    private EditText mName;
    private TextView mOrYouCanText;
    private EditText mPassword;
    private EditText mPasswordRepeat;
    /* access modifiers changed from: private */
    public CircularProgressButton mRegister;
    /* access modifiers changed from: private */
    public RelativeLayout mRegisterOverlay;
    private RelativeLayout mSignInButton;
    private Toolbar mToolbar;
    private TextView mUserAgreement;
    /* access modifiers changed from: private */
    public String nameKey;
    @Inject
    NetworkHelper networkHelper;
    @Inject
    SharedPrefHelper sharedPrefHelper;

    /* access modifiers changed from: protected */
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(getLayout());
        loadUi();
        if (this.sharedPrefHelper.getGoogleSignInActive()) {
            addGoogleSignIn();
        }
        setToolbar();
        this.mRegister.setOnClickListener(this);
    }

    public AppCompatActivity injectActivity(ActivityComponent activityComponent) {
        activityComponent.inject(this);
        return this;
    }

    private void addGoogleSignIn() {
        this.mSignInButton.setVisibility(0);
        this.mOrYouCanText.setVisibility(0);
        this.mSignInButton.setOnClickListener(this);
    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.forgot_password_text /*2131362425*/:
                startForgotPasswordActivity();
                return;
            case R.id.register_button /*2131363025*/:
                if (validateFields()) {
                    register();
                    return;
                }
                return;
            case R.id.sign_in_button /*2131363142*/:
                startActivityForResult(MobirollerIntent.getGoogleSignInIntent(this, ""), 133);
                return;
            case R.id.sign_up_text /*2131363145*/:
                startActivity(new Intent(this, UserRegisterActivity.class));
                return;
            default:
                return;
        }
    }

    public void hideToolbar(Toolbar toolbar) {
        toolbar.setVisibility(8);
    }

    public int getLayout() {
        return this.sharedPrefHelper.getLoginLayoutType() == 1 ? R.layout.activity_user_register_1 : R.layout.activity_user_register_2;
    }

    public void register() {
        if (this.networkHelper.isConnected()) {
            setButtonStatuses(false);
            this.mRegister.startAnimation();
            ApplyzeUserServiceInterface applyzeUserAPIService = new RequestHelper(this, this.sharedPrefHelper).getApplyzeUserAPIService();
            HashMap hashMap = new HashMap();
            hashMap.put("email", this.mEmail.getText().toString());
            hashMap.put("password", this.mPassword.getText().toString());
            hashMap.put("nameSurname", this.mName.getText().toString());
            hashMap.put("apiKey", getString(R.string.applyze_api_key));
            hashMap.put("appKey", getString(R.string.applyze_app_key));
            hashMap.put("udid", this.sharedPrefHelper.getDeviceId());
            hashMap.put("lang", LocaleHelper.getLocale().toUpperCase());
            applyzeUserAPIService.register(hashMap).enqueue(new Callback<UserLoginResponse>() {
                public void onResponse(Call<UserLoginResponse> call, Response<UserLoginResponse> response) {
                    UserRegisterActivity.this.mRegister.revertAnimation();
                    UserRegisterActivity.this.setButtonStatuses(true);
                    if (response.isSuccessful()) {
                        EventBus.getDefault().post(new RegisterEvent(UserRegisterActivity.this.mEmail.getText().toString()));
                        UserRegisterActivity.this.showSuccessDialog();
                        return;
                    }
                    APIError parseError = ErrorUtils.parseError(response);
                    UserRegisterActivity userRegisterActivity = UserRegisterActivity.this;
                    SnackbarHelper.displaySnackBarMsg(userRegisterActivity, userRegisterActivity.mRegisterOverlay, parseError.message(), false);
                }

                public void onFailure(Call<UserLoginResponse> call, Throwable th) {
                    UserRegisterActivity.this.mRegister.revertAnimation();
                    UserRegisterActivity.this.setButtonStatuses(true);
                    UserRegisterActivity userRegisterActivity = UserRegisterActivity.this;
                    SnackbarHelper.displaySnackBarMsg(userRegisterActivity, userRegisterActivity.mRegisterOverlay, UserRegisterActivity.this.getString(R.string.common_error), false);
                }
            });
            return;
        }
        SnackbarHelper.displaySnackBarMsg(this, this.mRegisterOverlay, getString(R.string.please_check_your_internet_connection), false);
    }

    public boolean validateFields() {
        closeKeyboard();
        if (!this.mName.getText().toString().matches("^[\\p{L} .'-]+$")) {
            SnackbarHelper.displaySnackBarMsg(this, this.mRegisterOverlay, getString(R.string.register_valid_name), false);
            return false;
        }
        String str = "";
        String str2 = "\\s+";
        if (this.mName.getText().toString().replaceAll(str2, str).length() < 1 || this.mName.getText().toString().replaceAll(str2, str).length() > 30) {
            SnackbarHelper.displaySnackBarMsg(this, this.mRegisterOverlay, getString(R.string.register_valid_name), false);
            return false;
        } else if (this.mEmail.getText().toString().isEmpty()) {
            SnackbarHelper.displaySnackBarMsg(this, this.mRegisterOverlay, getString(R.string.login_empty_email), false);
            return false;
        } else if (this.mPassword.getText().toString().isEmpty()) {
            SnackbarHelper.displaySnackBarMsg(this, this.mRegisterOverlay, getString(R.string.login_empty_password), false);
            return false;
        } else if (!ValidationUtil.isValidEmail(this.mEmail.getText().toString())) {
            SnackbarHelper.displaySnackBarMsg(this, this.mRegisterOverlay, getString(R.string.login_invalid_email), false);
            return false;
        } else if (!ValidationUtil.isValidPassword(this.mPassword.getText().toString())) {
            SnackbarHelper.displaySnackBarMsg(this, this.mRegisterOverlay, getString(R.string.login_invalid_password), false);
            return false;
        } else if (!ValidationUtil.isValidPassword(this.mPasswordRepeat.getText().toString())) {
            SnackbarHelper.displaySnackBarMsg(this, this.mRegisterOverlay, getString(R.string.login_invalid_password), false);
            return false;
        } else if (this.mPassword.getText().toString().equalsIgnoreCase(this.mPasswordRepeat.getText().toString())) {
            return true;
        } else {
            SnackbarHelper.displaySnackBarMsg(this, this.mRegisterOverlay, getString(R.string.passwords_not_match), false);
            return false;
        }
    }

    private void startForgotPasswordActivity() {
        startActivity(new Intent(this, UserResetPasswordActivity.class));
    }

    /* access modifiers changed from: private */
    public void setButtonStatuses(boolean z) {
        this.mEmail.setEnabled(z);
        this.mPassword.setEnabled(z);
    }

    private void getFormKeys() {
        if (this.networkHelper.isConnected()) {
            setButtonStatuses(false);
            this.mRegister.startAnimation();
            new RequestHelper(this, this.sharedPrefHelper).getApplyzeUserAPIService().getUserProfileElements(getString(R.string.applyze_api_key), getString(R.string.applyze_app_key)).enqueue(new Callback<List<UserProfileElement>>() {
                public void onResponse(Call<List<UserProfileElement>> call, Response<List<UserProfileElement>> response) {
                    if (response.isSuccessful()) {
                        List list = (List) response.body();
                        int i = 0;
                        while (true) {
                            if (i >= list.size()) {
                                break;
                            } else if (((UserProfileElement) list.get(i)).type.equalsIgnoreCase("nameSurname")) {
                                UserRegisterActivity.this.nameKey = ((UserProfileElement) list.get(i)).f2188id;
                                break;
                            } else {
                                i++;
                            }
                        }
                        if (UserRegisterActivity.this.nameKey != null) {
                            UserRegisterActivity.this.register();
                            return;
                        }
                        UserRegisterActivity.this.mRegister.revertAnimation();
                        UserRegisterActivity.this.setButtonStatuses(true);
                        UserRegisterActivity userRegisterActivity = UserRegisterActivity.this;
                        SnackbarHelper.displaySnackBarMsg(userRegisterActivity, userRegisterActivity.mRegisterOverlay, UserRegisterActivity.this.getString(R.string.common_error), false);
                        return;
                    }
                    UserRegisterActivity userRegisterActivity2 = UserRegisterActivity.this;
                    SnackbarHelper.displaySnackBarMsg(userRegisterActivity2, userRegisterActivity2.mRegisterOverlay, UserRegisterActivity.this.getString(R.string.common_error), false);
                }

                public void onFailure(Call<List<UserProfileElement>> call, Throwable th) {
                    UserRegisterActivity.this.mRegister.revertAnimation();
                    UserRegisterActivity.this.setButtonStatuses(true);
                    UserRegisterActivity userRegisterActivity = UserRegisterActivity.this;
                    SnackbarHelper.displaySnackBarMsg(userRegisterActivity, userRegisterActivity.mRegisterOverlay, UserRegisterActivity.this.getString(R.string.common_error), false);
                }
            });
            return;
        }
        SnackbarHelper.displaySnackBarMsg(this, this.mRegisterOverlay, getString(R.string.please_check_your_internet_connection), false);
    }

    public void loadUi() {
        this.mToolbar = (Toolbar) findViewById(R.id.toolbar_top);
        this.mEmail = (EditText) findViewById(R.id.email);
        this.mPassword = (EditText) findViewById(R.id.password);
        this.mName = (EditText) findViewById(R.id.name);
        this.mPasswordRepeat = (EditText) findViewById(R.id.password_repeat);
        this.mRegister = (CircularProgressButton) findViewById(R.id.register_button);
        ImageView imageView = (ImageView) findViewById(R.id.register_banner);
        this.mRegisterOverlay = (RelativeLayout) findViewById(R.id.register_overlay);
        this.mUserAgreement = (TextView) findViewById(R.id.user_agreement);
        this.mOrYouCanText = (TextView) findViewById(R.id.or_you_can);
        this.mSignInButton = (RelativeLayout) findViewById(R.id.sign_in_button);
        if (this.sharedPrefHelper.getIsUserAgremeentActive()) {
            this.mUserAgreement.setVisibility(0);
        }
        this.mPasswordRepeat.setOnEditorActionListener(new OnEditorActionListener() {
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                if (i != 6) {
                    return false;
                }
                UserRegisterActivity.this.mRegister.performClick();
                return true;
            }
        });
        this.mUserAgreement.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                try {
                    WebContent webContent = new WebContent(UserRegisterActivity.this.getString(R.string.user_agreement_title), String.valueOf(Uri.parse(UserRegisterActivity.this.localizationHelper.getLocalizedTitle(UserRegisterActivity.this.sharedPrefHelper.getUserAgremeent()))), true);
                    Intent intent = new Intent(UserRegisterActivity.this, aveWebView.class);
                    intent.putExtra("webContent", webContent);
                    UserRegisterActivity.this.startActivity(intent);
                    UserRegisterActivity.this.overridePendingTransition(R.anim.push_up_in, R.anim.push_up_out);
                } catch (Exception e) {
                    e.printStackTrace();
                    Intent intent2 = new Intent("android.intent.action.VIEW");
                    intent2.setData(Uri.parse(UserRegisterActivity.this.localizationHelper.getLocalizedTitle(UserRegisterActivity.this.sharedPrefHelper.getUserAgremeent())));
                    UserRegisterActivity.this.startActivity(intent2);
                }
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
            ImageManager.loadBackgroundImage(this.sharedPrefHelper.getLoginBackgroundURL(), this.mRegisterOverlay);
        } else {
            this.mRegisterOverlay.setBackgroundColor(this.sharedPrefHelper.getActionBarColor());
        }
    }

    public void setToolbar() {
        setSupportActionBar(this.mToolbar);
        this.mToolbar.setBackgroundColor(ContextCompat.getColor(this, R.color.transparent));
        this.mToolbar.setNavigationIcon((int) R.drawable.ic_arrow_back_white_24dp);
        if (VERSION.SDK_INT >= 19) {
            this.mToolbar.getNavigationIcon().setAutoMirrored(true);
        }
        String str = "";
        this.mToolbar.setTitle((CharSequence) str);
        this.mToolbar.setNavigationOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                UserRegisterActivity.this.finish();
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

    public void showSuccessDialog() {
        MaterialDialog build = new Builder(this).customView((int) R.layout.layout_success_custom_dialog, false).build();
        build.getWindow().setBackgroundDrawable(new ColorDrawable(0));
        build.setCancelable(false);
        build.show();
        View customView = build.getCustomView();
        Button button = (Button) customView.findViewById(R.id.layout_custom_close_button);
        ((TextView) customView.findViewById(R.id.message)).setText(getString(R.string.register_success));
        button.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putString("registered_email", UserRegisterActivity.this.mEmail.getText().toString());
                Intent intent = new Intent();
                intent.putExtras(bundle);
                UserRegisterActivity.this.setResult(-1, intent);
                UserRegisterActivity.this.finish();
            }
        });
    }

    /* access modifiers changed from: protected */
    public void onActivityResult(int i, int i2, Intent intent) {
        if (i == 133 && i2 == -1) {
            setResult(33, new Intent());
            finish();
        }
    }
}
