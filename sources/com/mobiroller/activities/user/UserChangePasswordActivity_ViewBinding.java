package com.mobiroller.activities.user;

import android.view.View;
import androidx.appcompat.widget.Toolbar;
import butterknife.Unbinder;
import butterknife.internal.C0812Utils;
import com.mobiroller.mobi942763453128.R;
import com.rengwuxian.materialedittext.MaterialEditText;

public class UserChangePasswordActivity_ViewBinding implements Unbinder {
    private UserChangePasswordActivity target;

    public UserChangePasswordActivity_ViewBinding(UserChangePasswordActivity userChangePasswordActivity) {
        this(userChangePasswordActivity, userChangePasswordActivity.getWindow().getDecorView());
    }

    public UserChangePasswordActivity_ViewBinding(UserChangePasswordActivity userChangePasswordActivity, View view) {
        this.target = userChangePasswordActivity;
        userChangePasswordActivity.changeOldPassword = (MaterialEditText) C0812Utils.findRequiredViewAsType(view, R.id.change_old_password, "field 'changeOldPassword'", MaterialEditText.class);
        userChangePasswordActivity.changeNewPassword = (MaterialEditText) C0812Utils.findRequiredViewAsType(view, R.id.change_new_password, "field 'changeNewPassword'", MaterialEditText.class);
        userChangePasswordActivity.changeNewRepeatPassword = (MaterialEditText) C0812Utils.findRequiredViewAsType(view, R.id.change_new_repeat_password, "field 'changeNewRepeatPassword'", MaterialEditText.class);
        userChangePasswordActivity.toolbarTop = (Toolbar) C0812Utils.findRequiredViewAsType(view, R.id.toolbar_top, "field 'toolbarTop'", Toolbar.class);
    }

    public void unbind() {
        UserChangePasswordActivity userChangePasswordActivity = this.target;
        if (userChangePasswordActivity != null) {
            this.target = null;
            userChangePasswordActivity.changeOldPassword = null;
            userChangePasswordActivity.changeNewPassword = null;
            userChangePasswordActivity.changeNewRepeatPassword = null;
            userChangePasswordActivity.toolbarTop = null;
            return;
        }
        throw new IllegalStateException("Bindings already cleared.");
    }
}
