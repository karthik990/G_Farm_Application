package com.rometools.rome.p052io.impl;

import com.rometools.rome.p052io.WireFeedGenerator;
import java.util.List;

/* renamed from: com.rometools.rome.io.impl.FeedGenerators */
public class FeedGenerators extends PluginManager<WireFeedGenerator> {
    public static final String FEED_GENERATORS_KEY = "WireFeedGenerator.classes";

    public FeedGenerators() {
        super(FEED_GENERATORS_KEY);
    }

    public WireFeedGenerator getGenerator(String str) {
        return (WireFeedGenerator) getPlugin(str);
    }

    /* access modifiers changed from: protected */
    public String getKey(WireFeedGenerator wireFeedGenerator) {
        return wireFeedGenerator.getType();
    }

    public List<String> getSupportedFeedTypes() {
        return getKeys();
    }
}
