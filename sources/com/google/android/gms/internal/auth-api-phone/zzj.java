package com.google.android.gms.internal.auth-api-phone;

import android.app.Activity;
import android.content.Context;
import com.google.android.gms.auth.api.phone.SmsRetrieverClient;
import com.google.android.gms.common.api.internal.TaskApiCall;
import com.google.android.gms.tasks.Task;

public final class zzj extends SmsRetrieverClient {
    public zzj(Activity activity) {
        super(activity);
    }

    public zzj(Context context) {
        super(context);
    }

    public final Task<Void> startSmsRetriever() {
        return doWrite((TaskApiCall<A, TResult>) new zzk<A,TResult>(this));
    }
}
