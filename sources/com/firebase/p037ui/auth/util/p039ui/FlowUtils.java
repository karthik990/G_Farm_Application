package com.firebase.p037ui.auth.util.p039ui;

import android.app.PendingIntent;
import android.content.IntentSender.SendIntentException;
import com.firebase.p037ui.auth.IdpResponse;
import com.firebase.p037ui.auth.data.model.IntentRequiredException;
import com.firebase.p037ui.auth.data.model.PendingIntentRequiredException;
import com.firebase.p037ui.auth.p038ui.FragmentBase;
import com.firebase.p037ui.auth.p038ui.HelperActivityBase;

/* renamed from: com.firebase.ui.auth.util.ui.FlowUtils */
public final class FlowUtils {
    private FlowUtils() {
        throw new AssertionError("No instance for you!");
    }

    public static boolean unhandled(HelperActivityBase helperActivityBase, Exception exc) {
        if (exc instanceof IntentRequiredException) {
            IntentRequiredException intentRequiredException = (IntentRequiredException) exc;
            helperActivityBase.startActivityForResult(intentRequiredException.getIntent(), intentRequiredException.getRequestCode());
            return false;
        } else if (!(exc instanceof PendingIntentRequiredException)) {
            return true;
        } else {
            PendingIntentRequiredException pendingIntentRequiredException = (PendingIntentRequiredException) exc;
            startIntentSenderForResult(helperActivityBase, pendingIntentRequiredException.getPendingIntent(), pendingIntentRequiredException.getRequestCode());
            return false;
        }
    }

    public static boolean unhandled(FragmentBase fragmentBase, Exception exc) {
        if (exc instanceof IntentRequiredException) {
            IntentRequiredException intentRequiredException = (IntentRequiredException) exc;
            fragmentBase.startActivityForResult(intentRequiredException.getIntent(), intentRequiredException.getRequestCode());
            return false;
        } else if (!(exc instanceof PendingIntentRequiredException)) {
            return true;
        } else {
            PendingIntentRequiredException pendingIntentRequiredException = (PendingIntentRequiredException) exc;
            startIntentSenderForResult(fragmentBase, pendingIntentRequiredException.getPendingIntent(), pendingIntentRequiredException.getRequestCode());
            return false;
        }
    }

    private static void startIntentSenderForResult(HelperActivityBase helperActivityBase, PendingIntent pendingIntent, int i) {
        try {
            helperActivityBase.startIntentSenderForResult(pendingIntent.getIntentSender(), i, null, 0, 0, 0);
        } catch (SendIntentException e) {
            helperActivityBase.finish(0, IdpResponse.getErrorIntent(e));
        }
    }

    private static void startIntentSenderForResult(FragmentBase fragmentBase, PendingIntent pendingIntent, int i) {
        try {
            fragmentBase.startIntentSenderForResult(pendingIntent.getIntentSender(), i, null, 0, 0, 0, null);
        } catch (SendIntentException e) {
            ((HelperActivityBase) fragmentBase.requireActivity()).finish(0, IdpResponse.getErrorIntent(e));
        }
    }
}
