package com.google.api.services.youtube;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class YouTubeScopes {
    public static final String YOUTUBE = "https://www.googleapis.com/auth/youtube";
    public static final String YOUTUBEPARTNER = "https://www.googleapis.com/auth/youtubepartner";
    public static final String YOUTUBEPARTNER_CHANNEL_AUDIT = "https://www.googleapis.com/auth/youtubepartner-channel-audit";
    public static final String YOUTUBE_FORCE_SSL = "https://www.googleapis.com/auth/youtube.force-ssl";
    public static final String YOUTUBE_READONLY = "https://www.googleapis.com/auth/youtube.readonly";
    public static final String YOUTUBE_UPLOAD = "https://www.googleapis.com/auth/youtube.upload";

    public static Set<String> all() {
        HashSet hashSet = new HashSet();
        hashSet.add(YOUTUBE);
        hashSet.add("https://www.googleapis.com/auth/youtube.force-ssl");
        hashSet.add("https://www.googleapis.com/auth/youtube.readonly");
        hashSet.add(YOUTUBE_UPLOAD);
        hashSet.add(YOUTUBEPARTNER);
        hashSet.add(YOUTUBEPARTNER_CHANNEL_AUDIT);
        return Collections.unmodifiableSet(hashSet);
    }

    private YouTubeScopes() {
    }
}
