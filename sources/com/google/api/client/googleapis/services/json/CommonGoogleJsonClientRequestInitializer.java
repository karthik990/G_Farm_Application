package com.google.api.client.googleapis.services.json;

import com.google.api.client.googleapis.services.AbstractGoogleClientRequest;
import com.google.api.client.googleapis.services.CommonGoogleClientRequestInitializer;
import java.io.IOException;

public class CommonGoogleJsonClientRequestInitializer extends CommonGoogleClientRequestInitializer {

    public static class Builder extends com.google.api.client.googleapis.services.CommonGoogleClientRequestInitializer.Builder {
        /* access modifiers changed from: protected */
        public Builder self() {
            return this;
        }
    }

    /* access modifiers changed from: protected */
    public void initializeJsonRequest(AbstractGoogleJsonClientRequest<?> abstractGoogleJsonClientRequest) throws IOException {
    }

    @Deprecated
    public CommonGoogleJsonClientRequestInitializer() {
    }

    @Deprecated
    public CommonGoogleJsonClientRequestInitializer(String str) {
        super(str);
    }

    @Deprecated
    public CommonGoogleJsonClientRequestInitializer(String str, String str2) {
        super(str, str2);
    }

    public final void initialize(AbstractGoogleClientRequest<?> abstractGoogleClientRequest) throws IOException {
        super.initialize(abstractGoogleClientRequest);
        initializeJsonRequest((AbstractGoogleJsonClientRequest) abstractGoogleClientRequest);
    }
}
