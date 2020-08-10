package com.mobiroller.helpers;

import android.app.Activity;
import android.view.View;
import androidx.core.content.ContextCompat;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.snackbar.Snackbar.Callback;
import com.mobiroller.mobi942763453128.R;
import com.mobiroller.util.ScreenUtil;

public class SnackbarHelper {
    public static void displaySnackBarMsg(Activity activity, View view, String str, boolean z) {
        ScreenUtil.closeKeyboard(activity);
        Snackbar make = Snackbar.make(view, (CharSequence) "", 0);
        if (z) {
            make.getView().setBackgroundColor(ContextCompat.getColor(activity, R.color.snackbar_success));
        } else {
            make.getView().setBackgroundColor(ContextCompat.getColor(activity, R.color.snackbar_fail));
        }
        make.setText((CharSequence) str);
        make.show();
    }

    public static void displaySnackBarMsgFinishActivity(final Activity activity, View view, String str, boolean z) {
        ScreenUtil.closeKeyboard(activity);
        Snackbar make = Snackbar.make(view, (CharSequence) "", 0);
        make.addCallback(new Callback() {
            public void onDismissed(Snackbar snackbar, int i) {
                super.onDismissed(snackbar, i);
                activity.finish();
            }
        });
        if (z) {
            make.getView().setBackgroundColor(ContextCompat.getColor(activity, R.color.snackbar_success));
        } else {
            make.getView().setBackgroundColor(ContextCompat.getColor(activity, R.color.snackbar_fail));
        }
        make.setText((CharSequence) str);
        make.show();
    }
}
