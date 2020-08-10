package com.google.firebase.auth.api.internal;

import android.content.Context;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.Api.ApiOptions;
import com.google.android.gms.common.api.GoogleApi;
import com.google.android.gms.common.api.internal.StatusExceptionMapper;

public final class zzak<O extends ApiOptions> extends GoogleApi<O> {
    public zzak(Context context, Api<O> api, O o, StatusExceptionMapper statusExceptionMapper) {
        super(context, api, o, statusExceptionMapper);
    }
}
