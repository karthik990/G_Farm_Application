package com.rometools.rome.p052io;

import com.rometools.rome.feed.synd.SyndFeed;
import com.rometools.rome.feed.synd.SyndFeedImpl;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.Reader;
import java.util.Locale;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;

/* renamed from: com.rometools.rome.io.SyndFeedInput */
public class SyndFeedInput {
    private final WireFeedInput feedInput;
    private boolean preserveWireFeed;

    public SyndFeedInput() {
        this(false, Locale.US);
    }

    public SyndFeedInput(boolean z, Locale locale) {
        this.preserveWireFeed = false;
        this.feedInput = new WireFeedInput(z, locale);
    }

    public void setXmlHealerOn(boolean z) {
        this.feedInput.setXmlHealerOn(z);
    }

    public boolean getXmlHealerOn() {
        return this.feedInput.getXmlHealerOn();
    }

    public boolean isAllowDoctypes() {
        return this.feedInput.isAllowDoctypes();
    }

    public void setAllowDoctypes(boolean z) {
        this.feedInput.setAllowDoctypes(z);
    }

    public SyndFeed build(File file) throws FileNotFoundException, IOException, IllegalArgumentException, FeedException {
        return new SyndFeedImpl(this.feedInput.build(file), this.preserveWireFeed);
    }

    public SyndFeed build(Reader reader) throws IllegalArgumentException, FeedException {
        return new SyndFeedImpl(this.feedInput.build(reader), this.preserveWireFeed);
    }

    public SyndFeed build(InputSource inputSource) throws IllegalArgumentException, FeedException {
        return new SyndFeedImpl(this.feedInput.build(inputSource), this.preserveWireFeed);
    }

    public SyndFeed build(Document document) throws IllegalArgumentException, FeedException {
        return new SyndFeedImpl(this.feedInput.build(document), this.preserveWireFeed);
    }

    public SyndFeed build(org.jdom2.Document document) throws IllegalArgumentException, FeedException {
        return new SyndFeedImpl(this.feedInput.build(document), this.preserveWireFeed);
    }

    public boolean isPreserveWireFeed() {
        return this.preserveWireFeed;
    }

    public void setPreserveWireFeed(boolean z) {
        this.preserveWireFeed = z;
    }
}
