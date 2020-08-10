package com.rometools.rome.p052io.impl;

import com.rometools.rome.p052io.WireFeedParser;
import java.util.List;
import org.jdom2.Document;

/* renamed from: com.rometools.rome.io.impl.FeedParsers */
public class FeedParsers extends PluginManager<WireFeedParser> {
    public static final String FEED_PARSERS_KEY = "WireFeedParser.classes";

    public FeedParsers() {
        super(FEED_PARSERS_KEY);
    }

    public List<String> getSupportedFeedTypes() {
        return getKeys();
    }

    public WireFeedParser getParserFor(Document document) {
        for (WireFeedParser wireFeedParser : getPlugins()) {
            if (wireFeedParser.isMyType(document)) {
                return wireFeedParser;
            }
        }
        return null;
    }

    /* access modifiers changed from: protected */
    public String getKey(WireFeedParser wireFeedParser) {
        return wireFeedParser.getType();
    }
}
