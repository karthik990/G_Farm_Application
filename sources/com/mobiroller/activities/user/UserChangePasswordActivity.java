package com.mobiroller.activities.user;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.internal.view.SupportMenu;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.afollestad.materialdialogs.MaterialDialog.Builder;
import com.mobiroller.activities.base.AveActivity;
import com.mobiroller.helpers.EditTextHelper;
import com.mobiroller.helpers.LocaleHelper;
import com.mobiroller.helpers.NetworkHelper;
import com.mobiroller.helpers.ProgressViewHelper;
import com.mobiroller.helpers.RequestHelper;
import com.mobiroller.helpers.SharedPrefHelper;
import com.mobiroller.helpers.ToolbarHelper;
import com.mobiroller.helpers.UserHelper;
import com.mobiroller.interfaces.ActivityComponent;
import com.mobiroller.mobi942763453128.R;
import com.mobiroller.models.response.UserLoginResponse;
import com.mobiroller.util.DialogUtil;
import com.mobiroller.util.DialogUtil.DialogCallBack;
import com.mobiroller.util.ErrorUtils;
import com.mobiroller.util.ValidationUtil;
import com.rengwuxian.materialedittext.MaterialEditText;
import java.util.HashMap;
import javax.inject.Inject;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserChangePasswordActivity extends AveActivity {
    @BindView(2131362134)
    MaterialEditText changeNewPassword;
    @BindView(2131362135)
    MaterialEditText changeNewRepeatPassword;
    @BindView(2131362136)
    MaterialEditText changeOldPassword;
    private boolean isFromForgotPassword;
    @Inject
    NetworkHelper networkHelper;
    /* access modifiers changed from: private */
    public ProgressViewHelper progressViewHelper;
    @Inject
    SharedPrefHelper sharedPrefHelper;
    @Inject
    ToolbarHelper toolbarHelper;
    @BindView(2131363276)
    Toolbar toolbarTop;

    public boolean onPrepareOptionsMenu(Menu menu) {
        return true;
    }

    /* access modifiers changed from: protected */
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView((int) R.layout.activity_user_change_password);
        ButterKnife.bind((Activity) this);
        this.isFromForgotPassword = getIntent().hasExtra("forgotPassword");
        if (this.isFromForgotPassword) {
            this.changeOldPassword.setText(getIntent().getStringExtra("password"));
        }
        if (this.sharedPrefHelper.getGoogleSignInAccount() != null) {
            this.changeOldPassword.setVisibility(8);
        }
        this.progressViewHelper = new ProgressViewHelper((AppCompatActivity) this);
        ToolbarHelper.setToolbar(this, this.toolbarTop);
        this.toolbarHelper.setToolbarTitle(this, getString(R.string.change_password));
        String str = "#505050";
        setEditTextColor(this.changeNewPassword, Color.parseColor(str));
        setEditTextColor(this.changeOldPassword, Color.parseColor(str));
        setEditTextColor(this.changeNewRepeatPassword, Color.parseColor(str));
    }

    public void setEditTextColor(MaterialEditText materialEditText, int i) {
        materialEditText.setBaseColor(i);
        materialEditText.setPrimaryColor(i);
        materialEditText.setUnderlineColor(i);
        materialEditText.setFloatingLabelTextColor(i);
        materialEditText.setMetTextColor(i);
        materialEditText.setMetHintTextColor(i);
        materialEditText.setErrorColor(SupportMenu.CATEGORY_MASK);
        EditTextHelper.setCursorColor(materialEditText, i);
    }

    public AppCompatActivity injectActivity(ActivityComponent activityComponent) {
        activityComponent.inject(this);
        return this;
    }

    public void changePassword() {
        HashMap hashMap = new HashMap();
        hashMap.put("apiKey", getString(R.string.applyze_api_key));
        hashMap.put("appKey", getString(R.string.applyze_app_key));
        hashMap.put("udid", this.sharedPrefHelper.getDeviceId());
        hashMap.put("device", this.sharedPrefHelper.getDeviceModel());
        hashMap.put(TtmlNode.ATTR_ID, UserHelper.getUserId());
        hashMap.put("sessionKey", UserHelper.getUserSessionToken());
        String str = "oldPassword";
        if (this.sharedPrefHelper.getGoogleSignInAccount() != null) {
            hashMap.put(str, "123456");
            hashMap.put("isSocialLogin", Boolean.valueOf(true));
        } else {
            hashMap.put(str, this.changeOldPassword.getText().toString());
        }
        hashMap.put("newPassword", this.changeNewPassword.getText().toString());
        hashMap.put("lang", LocaleHelper.getLocale().toUpperCase());
        this.progressViewHelper.show();
        closeKeyboard();
        new RequestHelper(this, this.sharedPrefHelper).getApplyzeUserAPIService().changePassword(hashMap).enqueue(new Callback<UserLoginResponse>() {
            public void onResponse(Call<UserLoginResponse> call, Response<UserLoginResponse> response) {
                UserChangePasswordActivity.this.progressViewHelper.dismiss();
                if (response.isSuccessful()) {
                    String str = "";
                    UserChangePasswordActivity.this.changeNewPassword.setText(str);
                    UserChangePasswordActivity.this.changeNewRepeatPassword.setText(str);
                    UserChangePasswordActivity.this.changeOldPassword.setText(str);
                    UserChangePasswordActivity userChangePasswordActivity = UserChangePasswordActivity.this;
                    DialogUtil.showDialogWithCallBack(userChangePasswordActivity, userChangePasswordActivity.getString(R.string.password_updated), new DialogCallBack() {
                        public void onClickPositive() {
                            UserChangePasswordActivity.this.finish();
                        }
                    });
                    return;
                }
                DialogUtil.showDialog(UserChangePasswordActivity.this, ErrorUtils.parseError(response).message());
            }

            public void onFailure(Call<UserLoginResponse> call, Throwable th) {
                UserChangePasswordActivity.this.progressViewHelper.dismiss();
                UserChangePasswordActivity userChangePasswordActivity = UserChangePasswordActivity.this;
                DialogUtil.showDialog(userChangePasswordActivity, userChangePasswordActivity.getString(R.string.common_error));
            }
        });
    }

    public boolean isValid() {
        if (this.sharedPrefHelper.getGoogleSignInAccount() == null) {
            if (this.changeOldPassword.getText().toString().isEmpty()) {
                this.changeOldPassword.setError(getString(R.string.login_empty_password));
                return false;
            } else if (!ValidationUtil.isValidPassword(this.changeOldPassword.getText().toString())) {
                this.changeOldPassword.setError(getString(R.string.invalid_password));
                return false;
            }
        }
        if (this.changeNewPassword.getText().toString().isEmpty()) {
            this.changeNewPassword.setError(getString(R.string.login_empty_password));
            return false;
        } else if (!ValidationUtil.isValidPassword(this.changeNewPassword.getText().toString())) {
            this.changeNewPassword.setError(getString(R.string.invalid_password));
            return false;
        } else if (this.changeNewRepeatPassword.getText().toString().isEmpty()) {
            this.changeNewRepeatPassword.setError(getString(R.string.login_empty_password));
            return false;
        } else if (!ValidationUtil.isValidPassword(this.changeNewRepeatPassword.getText().toString())) {
            this.changeNewRepeatPassword.setError(getString(R.string.invalid_password));
            return false;
        } else if (this.changeNewRepeatPassword.getText().toString().equalsIgnoreCase(this.changeNewPassword.getText().toString())) {
            return true;
        } else {
            this.changeNewRepeatPassword.setError(getString(R.string.passwords_not_match));
            this.changeNewPassword.setError(getString(R.string.passwords_not_match));
            return false;
        }
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.note_add_menu, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem menuItem) {
        if (menuItem.getItemId() != R.id.action_save) {
            return super.onOptionsItemSelected(menuItem);
        }
        if (isValid()) {
            if (this.networkHelper.isConnected()) {
                changePassword();
            } else {
                new Builder(this).content((int) R.string.please_check_your_internet_connection).positiveText((int) R.string.OK).show();
            }
        }
        return true;
    }
}
