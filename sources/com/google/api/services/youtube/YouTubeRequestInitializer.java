package com.google.api.services.youtube;

import com.google.api.client.googleapis.services.json.AbstractGoogleJsonClientRequest;
import com.google.api.client.googleapis.services.json.CommonGoogleJsonClientRequestInitializer;
import java.io.IOException;

public class YouTubeRequestInitializer extends CommonGoogleJsonClientRequestInitializer {
    /* access modifiers changed from: protected */
    public void initializeYouTubeRequest(YouTubeRequest<?> youTubeRequest) throws IOException {
    }

    public YouTubeRequestInitializer() {
    }

    public YouTubeRequestInitializer(String str) {
        super(str);
    }

    public YouTubeRequestInitializer(String str, String str2) {
        super(str, str2);
    }

    public final void initializeJsonRequest(AbstractGoogleJsonClientRequest<?> abstractGoogleJsonClientRequest) throws IOException {
        super.initializeJsonRequest(abstractGoogleJsonClientRequest);
        initializeYouTubeRequest((YouTubeRequest) abstractGoogleJsonClientRequest);
    }
}
