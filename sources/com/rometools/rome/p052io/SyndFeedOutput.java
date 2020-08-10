package com.rometools.rome.p052io;

import com.rometools.rome.feed.synd.SyndFeed;
import java.io.File;
import java.io.IOException;
import java.io.Writer;
import org.w3c.dom.Document;

/* renamed from: com.rometools.rome.io.SyndFeedOutput */
public class SyndFeedOutput {
    private final WireFeedOutput feedOutput = new WireFeedOutput();

    public String outputString(SyndFeed syndFeed) throws FeedException {
        return this.feedOutput.outputString(syndFeed.createWireFeed());
    }

    public String outputString(SyndFeed syndFeed, boolean z) throws FeedException {
        return this.feedOutput.outputString(syndFeed.createWireFeed(), z);
    }

    public void output(SyndFeed syndFeed, File file) throws IOException, FeedException {
        this.feedOutput.output(syndFeed.createWireFeed(), file);
    }

    public void output(SyndFeed syndFeed, File file, boolean z) throws IOException, FeedException {
        this.feedOutput.output(syndFeed.createWireFeed(), file, z);
    }

    public void output(SyndFeed syndFeed, Writer writer) throws IOException, FeedException {
        this.feedOutput.output(syndFeed.createWireFeed(), writer);
    }

    public void output(SyndFeed syndFeed, Writer writer, boolean z) throws IOException, FeedException {
        this.feedOutput.output(syndFeed.createWireFeed(), writer, z);
    }

    public Document outputW3CDom(SyndFeed syndFeed) throws FeedException {
        return this.feedOutput.outputW3CDom(syndFeed.createWireFeed());
    }

    public org.jdom2.Document outputJDom(SyndFeed syndFeed) throws FeedException {
        return this.feedOutput.outputJDom(syndFeed.createWireFeed());
    }
}
