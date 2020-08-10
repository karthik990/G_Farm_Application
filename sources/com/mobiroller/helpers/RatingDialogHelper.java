package com.mobiroller.helpers;

import android.content.Context;
import android.widget.Toast;
import com.mobiroller.mobi942763453128.R;
import com.mobiroller.views.dialogs.RatingDialog;
import com.mobiroller.views.dialogs.RatingDialog.Builder;
import com.mobiroller.views.dialogs.RatingDialog.Builder.RatingDialogFormListener;

public class RatingDialogHelper {
    private static int SESSION_COUNT = 3;
    private static int THRESHOLD = 3;

    public static void checkRatingStatus(final Context context) {
        if (UtilManager.networkHelper().isConnected() && UtilManager.sharedPrefHelper().getRateApp()) {
            RatingDialog build = new Builder(context).title(context.getString(R.string.rating_dialog_experience)).positiveButtonText(context.getString(R.string.rating_dialog_remind_later)).negativeButtonText(context.getString(R.string.rating_dialog_never)).formTitle(context.getString(R.string.rating_dialog_feedback_title)).formHint(context.getString(R.string.rating_dialog_suggestions)).formSubmitText(context.getString(R.string.rating_dialog_submit)).formCancelText(context.getString(R.string.rating_dialog_cancel)).threshold((float) THRESHOLD).session(SESSION_COUNT).onRatingBarFormSumbit(new RatingDialogFormListener() {
                public void onFormSubmitted(String str, int i) {
                    new ApiRequestManager().sendFeedback(str, i);
                    Context context = context;
                    Toast.makeText(context, context.getString(R.string.feedback_sent), 0).show();
                }
            }).build();
            build.setCancelable(false);
            build.setCanceledOnTouchOutside(false);
            build.show();
        }
    }
}
