package com.mobiroller.activities.user;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
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
import com.mobiroller.activities.base.AveActivity;
import com.mobiroller.helpers.NetworkHelper;
import com.mobiroller.helpers.RequestHelper;
import com.mobiroller.helpers.SharedPrefHelper;
import com.mobiroller.helpers.SnackbarHelper;
import com.mobiroller.interfaces.ActivityComponent;
import com.mobiroller.mobi942763453128.R;
import com.mobiroller.serviceinterfaces.ApplyzeUserServiceInterface;
import com.mobiroller.util.ErrorUtils;
import com.mobiroller.util.ImageManager;
import com.mobiroller.util.ValidationUtil;
import java.util.HashMap;
import javax.inject.Inject;
import p015br.com.simplepass.loading_button_lib.customViews.CircularProgressButton;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserResetPasswordActivity extends AveActivity implements OnClickListener {
    private int layoutType = 2;
    /* access modifiers changed from: private */
    public EditText mEmail;
    private String mEmailKey;
    private ImageView mLogo;
    /* access modifiers changed from: private */
    public RelativeLayout mResetPasswordOverlay;
    /* access modifiers changed from: private */
    public CircularProgressButton mSendEmail;
    private Toolbar mToolbar;
    @Inject
    NetworkHelper networkHelper;
    @Inject
    SharedPrefHelper sharedPrefHelper;

    /* access modifiers changed from: protected */
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(getLayout());
        loadUi();
        String str = "";
        if (!this.sharedPrefHelper.getLogoURL().equals(str)) {
            ImageManager.loadImageView(this, this.sharedPrefHelper.getLogoURL(), this.mLogo);
        }
        if (!this.sharedPrefHelper.getLoginBackgroundURL().equals(str)) {
            ImageManager.loadBackgroundImage(this.sharedPrefHelper.getLoginBackgroundURL(), this.mResetPasswordOverlay);
        } else {
            this.mResetPasswordOverlay.setBackgroundColor(this.sharedPrefHelper.getActionBarColor());
        }
        setToolbar();
        this.mSendEmail.setOnClickListener(this);
    }

    public AppCompatActivity injectActivity(ActivityComponent activityComponent) {
        activityComponent.inject(this);
        return this;
    }

    public boolean validateFields() {
        if (this.mEmail.getText().toString().isEmpty()) {
            SnackbarHelper.displaySnackBarMsg(this, this.mResetPasswordOverlay, getString(R.string.login_empty_email), false);
            return false;
        } else if (ValidationUtil.isValidEmail(this.mEmail.getText().toString())) {
            return true;
        } else {
            SnackbarHelper.displaySnackBarMsg(this, this.mResetPasswordOverlay, getString(R.string.login_invalid_email), false);
            return false;
        }
    }

    public void sendEmail() {
        if (this.networkHelper.isConnected()) {
            this.mSendEmail.startAnimation();
            ApplyzeUserServiceInterface applyzeUserAPIService = new RequestHelper(this, this.sharedPrefHelper).getApplyzeUserAPIService();
            HashMap hashMap = new HashMap();
            hashMap.put("apiKey", getString(R.string.applyze_api_key));
            hashMap.put("appKey", getString(R.string.applyze_app_key));
            hashMap.put("udid", this.sharedPrefHelper.getDeviceId());
            hashMap.put("device", this.sharedPrefHelper.getDeviceModel());
            hashMap.put("email", this.mEmail.getText().toString());
            applyzeUserAPIService.forgotPassword(hashMap).enqueue(new Callback<Void>() {
                public void onResponse(Call<Void> call, Response<Void> response) {
                    UserResetPasswordActivity.this.mSendEmail.revertAnimation();
                    UserResetPasswordActivity.this.setButtonStatuses(true);
                    if (response.isSuccessful()) {
                        UserResetPasswordActivity.this.showSuccessDialog();
                        return;
                    }
                    ErrorUtils.parseError(response);
                    UserResetPasswordActivity userResetPasswordActivity = UserResetPasswordActivity.this;
                    SnackbarHelper.displaySnackBarMsg(userResetPasswordActivity, userResetPasswordActivity.mResetPasswordOverlay, UserResetPasswordActivity.this.getString(R.string.forgot_password_fail_message), false);
                }

                public void onFailure(Call<Void> call, Throwable th) {
                    UserResetPasswordActivity.this.mSendEmail.revertAnimation();
                    UserResetPasswordActivity.this.setButtonStatuses(true);
                    UserResetPasswordActivity userResetPasswordActivity = UserResetPasswordActivity.this;
                    SnackbarHelper.displaySnackBarMsg(userResetPasswordActivity, userResetPasswordActivity.mResetPasswordOverlay, UserResetPasswordActivity.this.getString(R.string.common_error), false);
                }
            });
            return;
        }
        SnackbarHelper.displaySnackBarMsg(this, this.mResetPasswordOverlay, getString(R.string.please_check_your_internet_connection), false);
    }

    public int getLayout() {
        this.layoutType = this.sharedPrefHelper.getLoginLayoutType();
        return this.layoutType == 1 ? R.layout.activity_user_reset_password_1 : R.layout.activity_user_reset_password_2;
    }

    public void onClick(View view) {
        if (view.getId() == R.id.reset_password_button && validateFields()) {
            sendEmail();
        }
    }

    /* access modifiers changed from: private */
    public void setButtonStatuses(boolean z) {
        this.mEmail.setEnabled(z);
    }

    private void loadUi() {
        this.mToolbar = (Toolbar) findViewById(R.id.toolbar_top);
        this.mEmail = (EditText) findViewById(R.id.email);
        this.mSendEmail = (CircularProgressButton) findViewById(R.id.reset_password_button);
        this.mLogo = (ImageView) findViewById(R.id.reset_password_banner);
        this.mResetPasswordOverlay = (RelativeLayout) findViewById(R.id.reset_password_overlay);
        this.mEmail.setOnEditorActionListener(new OnEditorActionListener() {
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                if (keyEvent != null && keyEvent.getAction() == 6) {
                    UserResetPasswordActivity.this.mSendEmail.performClick();
                }
                return false;
            }
        });
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
                UserResetPasswordActivity.this.finish();
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
        ((TextView) customView.findViewById(R.id.message)).setText(getString(R.string.password_success));
        button.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putString("registered_email", UserResetPasswordActivity.this.mEmail.getText().toString());
                Intent intent = new Intent();
                intent.putExtras(bundle);
                UserResetPasswordActivity.this.setResult(-1, intent);
                UserResetPasswordActivity.this.finish();
            }
        });
    }
}
