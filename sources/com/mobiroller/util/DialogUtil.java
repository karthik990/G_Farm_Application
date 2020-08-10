package com.mobiroller.util;

import android.app.Activity;
import android.content.Context;
import androidx.appcompat.app.AppCompatActivity;
import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.afollestad.materialdialogs.MaterialDialog.Builder;
import com.afollestad.materialdialogs.MaterialDialog.SingleButtonCallback;
import com.mobiroller.helpers.UserHelper;
import com.mobiroller.mobi942763453128.R;

public class DialogUtil {

    public interface ExitDialog {
        void onExitApp();
    }

    public interface UserLogoutDialog {
        void onUserLogout();
    }

    public interface DialogCallBack {
        void onClickPositive();
    }

    public static void showNoConnectionError(final Activity activity) {
        new Builder(activity).title((int) R.string.connection_required_error).content((int) R.string.please_check_your_internet_connection).cancelable(false).positiveText((int) R.string.OK).onPositive(new SingleButtonCallback() {
            public void onClick(MaterialDialog materialDialog, DialogAction dialogAction) {
                activity.finish();
            }
        }).show();
    }

    public static void showNoConnectionInfo(Context context) {
        new Builder(context).title((int) R.string.connection_required_error).content((int) R.string.please_check_your_internet_connection).positiveText((int) R.string.OK).show();
    }

    public static void showNoConnectionInfo(Context context, SingleButtonCallback singleButtonCallback) {
        new Builder(context).title((int) R.string.connection_required_error).content((int) R.string.please_check_your_internet_connection).positiveText((int) R.string.OK).onPositive(singleButtonCallback).show();
    }

    public static void showDialog(Context context, String str) {
        new Builder(context).content((CharSequence) str).positiveText((int) R.string.OK).show();
    }

    public static void showDialogWithCallBack(Context context, String str, final DialogCallBack dialogCallBack) {
        new Builder(context).content((CharSequence) str).positiveText((int) R.string.OK).onPositive(new SingleButtonCallback() {
            public void onClick(MaterialDialog materialDialog, DialogAction dialogAction) {
                dialogCallBack.onClickPositive();
            }
        }).show();
    }

    public static Builder getDialogWithCallBack(Context context, String str, final DialogCallBack dialogCallBack) {
        return new Builder(context).content((CharSequence) str).positiveText((int) R.string.OK).onPositive(new SingleButtonCallback() {
            public void onClick(MaterialDialog materialDialog, DialogAction dialogAction) {
                dialogCallBack.onClickPositive();
            }
        });
    }

    public static void showLogoutDialog(final AppCompatActivity appCompatActivity, final UserLogoutDialog userLogoutDialog) {
        new Builder(appCompatActivity).title((int) R.string.app_name).content((int) R.string.logout_dialog).positiveText((int) R.string.yes).negativeText((int) R.string.cancel).onPositive(new SingleButtonCallback() {
            public void onClick(MaterialDialog materialDialog, DialogAction dialogAction) {
                UserHelper.logout(appCompatActivity);
                userLogoutDialog.onUserLogout();
            }
        }).show();
    }

    public static MaterialDialog showExitDialog(AppCompatActivity appCompatActivity, final ExitDialog exitDialog) {
        return new Builder(appCompatActivity).title((int) R.string.app_name).content((int) R.string.action_close_app).positiveText((int) R.string.yes).negativeText((int) R.string.cancel).onPositive(new SingleButtonCallback() {
            public void onClick(MaterialDialog materialDialog, DialogAction dialogAction) {
                exitDialog.onExitApp();
            }
        }).show();
    }
}
