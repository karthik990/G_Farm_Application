package com.afollestad.materialdialogs;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnShowListener;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import com.afollestad.materialdialogs.internal.MDRootLayout;

class DialogBase extends Dialog implements OnShowListener {
    private OnShowListener showListener;
    protected MDRootLayout view;

    DialogBase(Context context, int i) {
        super(context, i);
    }

    public View findViewById(int i) {
        return this.view.findViewById(i);
    }

    public final void setOnShowListener(OnShowListener onShowListener) {
        this.showListener = onShowListener;
    }

    /* access modifiers changed from: 0000 */
    public final void setOnShowListenerInternal() {
        super.setOnShowListener(this);
    }

    /* access modifiers changed from: 0000 */
    public final void setViewInternal(View view2) {
        super.setContentView(view2);
    }

    public void onShow(DialogInterface dialogInterface) {
        OnShowListener onShowListener = this.showListener;
        if (onShowListener != null) {
            onShowListener.onShow(dialogInterface);
        }
    }

    @Deprecated
    public void setContentView(int i) throws IllegalAccessError {
        throw new IllegalAccessError("setContentView() is not supported in MaterialDialog. Specify a custom view in the Builder instead.");
    }

    @Deprecated
    public void setContentView(View view2) throws IllegalAccessError {
        throw new IllegalAccessError("setContentView() is not supported in MaterialDialog. Specify a custom view in the Builder instead.");
    }

    @Deprecated
    public void setContentView(View view2, LayoutParams layoutParams) throws IllegalAccessError {
        throw new IllegalAccessError("setContentView() is not supported in MaterialDialog. Specify a custom view in the Builder instead.");
    }
}
