package com.mobiroller.util.validation.exceptions;

import android.content.Context;
import android.widget.Toast;
import com.mobiroller.mobi942763453128.R;

public class ShowableException extends Exception {
    public void notifyUserWithToast(Context context) {
        Toast.makeText(context, context.getString(R.string.field_required, new Object[]{toString()}), 0).show();
    }
}
