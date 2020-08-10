package androidx.work.impl.constraints.controllers;

import android.content.Context;
import android.os.Build.VERSION;
import androidx.work.Logger;
import androidx.work.NetworkType;
import androidx.work.impl.constraints.NetworkState;
import androidx.work.impl.constraints.trackers.Trackers;
import androidx.work.impl.model.WorkSpec;
import androidx.work.impl.utils.taskexecutor.TaskExecutor;

public class NetworkNotRoamingController extends ConstraintController<NetworkState> {
    private static final String TAG = Logger.tagWithPrefix("NetworkNotRoamingCtrlr");

    public NetworkNotRoamingController(Context context, TaskExecutor taskExecutor) {
        super(Trackers.getInstance(context, taskExecutor).getNetworkStateTracker());
    }

    /* access modifiers changed from: 0000 */
    public boolean hasConstraint(WorkSpec workSpec) {
        return workSpec.constraints.getRequiredNetworkType() == NetworkType.NOT_ROAMING;
    }

    /* access modifiers changed from: 0000 */
    public boolean isConstrained(NetworkState networkState) {
        boolean z = true;
        if (VERSION.SDK_INT < 24) {
            Logger.get().debug(TAG, "Not-roaming network constraint is not supported before API 24, only checking for connected state.", new Throwable[0]);
            return !networkState.isConnected();
        }
        if (networkState.isConnected() && networkState.isNotRoaming()) {
            z = false;
        }
        return z;
    }
}
