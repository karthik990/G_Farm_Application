package com.rometools.rome.p052io;

import com.rometools.rome.feed.WireFeed;
import com.rometools.rome.feed.impl.ConfigurableClassLoader;
import com.rometools.rome.p052io.impl.FeedGenerators;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.List;
import java.util.Map;
import java.util.WeakHashMap;
import org.jdom2.Document;
import org.jdom2.JDOMException;
import org.jdom2.output.DOMOutputter;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;

/* renamed from: com.rometools.rome.io.WireFeedOutput */
public class WireFeedOutput {
    private static Map<ClassLoader, FeedGenerators> clMap = new WeakHashMap();

    private static FeedGenerators getFeedGenerators() {
        FeedGenerators feedGenerators;
        synchronized (WireFeedOutput.class) {
            ClassLoader classLoader = ConfigurableClassLoader.INSTANCE.getClassLoader();
            feedGenerators = (FeedGenerators) clMap.get(classLoader);
            if (feedGenerators == null) {
                feedGenerators = new FeedGenerators();
                clMap.put(classLoader, feedGenerators);
            }
        }
        return feedGenerators;
    }

    public static List<String> getSupportedFeedTypes() {
        return getFeedGenerators().getSupportedFeedTypes();
    }

    public String outputString(WireFeed wireFeed) throws IllegalArgumentException, FeedException {
        return outputString(wireFeed, true);
    }

    public String outputString(WireFeed wireFeed, boolean z) throws IllegalArgumentException, FeedException {
        Format format;
        Document outputJDom = outputJDom(wireFeed);
        String encoding = wireFeed.getEncoding();
        if (z) {
            format = Format.getPrettyFormat();
        } else {
            format = Format.getCompactFormat();
        }
        if (encoding != null) {
            format.setEncoding(encoding);
        }
        return new XMLOutputter(format).outputString(outputJDom);
    }

    public void output(WireFeed wireFeed, File file) throws IllegalArgumentException, IOException, FeedException {
        output(wireFeed, file, true);
    }

    public void output(WireFeed wireFeed, File file, boolean z) throws IllegalArgumentException, IOException, FeedException {
        FileWriter fileWriter = new FileWriter(file);
        try {
            output(wireFeed, (Writer) fileWriter, z);
        } finally {
            fileWriter.close();
        }
    }

    public void output(WireFeed wireFeed, Writer writer) throws IllegalArgumentException, IOException, FeedException {
        output(wireFeed, writer, true);
    }

    public void output(WireFeed wireFeed, Writer writer, boolean z) throws IllegalArgumentException, IOException, FeedException {
        Format format;
        Document outputJDom = outputJDom(wireFeed);
        String encoding = wireFeed.getEncoding();
        if (z) {
            format = Format.getPrettyFormat();
        } else {
            format = Format.getCompactFormat();
        }
        if (encoding != null) {
            format.setEncoding(encoding);
        }
        new XMLOutputter(format).output(outputJDom, writer);
    }

    public org.w3c.dom.Document outputW3CDom(WireFeed wireFeed) throws IllegalArgumentException, FeedException {
        try {
            return new DOMOutputter().output(outputJDom(wireFeed));
        } catch (JDOMException e) {
            throw new FeedException("Could not create DOM", e);
        }
    }

    public Document outputJDom(WireFeed wireFeed) throws IllegalArgumentException, FeedException {
        String feedType = wireFeed.getFeedType();
        WireFeedGenerator generator = getFeedGenerators().getGenerator(feedType);
        if (generator == null) {
            StringBuilder sb = new StringBuilder();
            sb.append("Invalid feed type [");
            sb.append(feedType);
            sb.append("]");
            throw new IllegalArgumentException(sb.toString());
        } else if (generator.getType().equals(feedType)) {
            return generator.generate(wireFeed);
        } else {
            StringBuilder sb2 = new StringBuilder();
            sb2.append("WireFeedOutput type[");
            sb2.append(feedType);
            sb2.append("] and WireFeed type [");
            sb2.append(feedType);
            sb2.append("] don't match");
            throw new IllegalArgumentException(sb2.toString());
        }
    }
}
