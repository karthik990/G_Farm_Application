package p043io.fabric.sdk.android.services.common;

import android.content.Context;
import android.content.res.Resources.NotFoundException;
import android.os.Handler;
import android.os.Looper;
import android.widget.Toast;
import p043io.fabric.sdk.android.services.concurrency.PriorityRunnable;

/* renamed from: io.fabric.sdk.android.services.common.SafeToast */
public class SafeToast extends Toast {
    public SafeToast(Context context) {
        super(context);
    }

    public void show() {
        if (Looper.myLooper() == Looper.getMainLooper()) {
            super.show();
        } else {
            new Handler(Looper.getMainLooper()).post(new PriorityRunnable() {
                public void run() {
                    SafeToast.super.show();
                }
            });
        }
    }

    public static Toast makeText(Context context, CharSequence charSequence, int i) {
        Toast makeText = Toast.makeText(context, charSequence, i);
        SafeToast safeToast = new SafeToast(context);
        safeToast.setView(makeText.getView());
        safeToast.setDuration(makeText.getDuration());
        return safeToast;
    }

    public static Toast makeText(Context context, int i, int i2) throws NotFoundException {
        return makeText(context, context.getResources().getText(i), i2);
    }
}
