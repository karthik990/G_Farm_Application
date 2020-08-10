package com.mobiroller.util.exceptions;

import android.content.Context;
import com.mobiroller.util.DialogUtil;

public class UserFriendlyException extends MobirollerException {
    private int messageResource;

    public UserFriendlyException(String str, int i) {
        super(str);
        this.messageResource = i;
    }

    public UserFriendlyException(int i) {
        this.messageResource = i;
    }

    public int getMessageResource() {
        return this.messageResource;
    }

    public void setMessageResource(int i) {
        this.messageResource = i;
    }

    public void showDialog(Context context) {
        DialogUtil.showDialog(context, context.getString(this.messageResource));
    }
}
