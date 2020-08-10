package com.rometools.rome.p052io.impl;

import com.google.firebase.analytics.FirebaseAnalytics.Param;
import com.rometools.rome.feed.WireFeed;
import com.rometools.rome.feed.rss.Category;
import com.rometools.rome.feed.rss.Channel;
import com.rometools.rome.feed.rss.Cloud;
import com.rometools.rome.feed.rss.Description;
import com.rometools.rome.feed.rss.Enclosure;
import com.rometools.rome.feed.rss.Item;
import com.rometools.rome.feed.rss.Source;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import org.apache.http.cookie.ClientCookie;
import org.jdom2.Content;
import org.jdom2.Content.CType;
import org.jdom2.Element;
import org.jdom2.output.XMLOutputter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/* renamed from: com.rometools.rome.io.impl.RSS092Parser */
public class RSS092Parser extends RSS091UserlandParser {
    private static final Logger LOG = LoggerFactory.getLogger(RSS092Parser.class);

    /* renamed from: com.rometools.rome.io.impl.RSS092Parser$1 */
    static /* synthetic */ class C46201 {
        static final /* synthetic */ int[] $SwitchMap$org$jdom2$Content$CType = new int[CType.values().length];

        /* JADX WARNING: Can't wrap try/catch for region: R(10:0|1|2|3|4|5|6|7|8|10) */
        /* JADX WARNING: Can't wrap try/catch for region: R(8:0|1|2|3|4|5|6|(3:7|8|10)) */
        /* JADX WARNING: Failed to process nested try/catch */
        /* JADX WARNING: Missing exception handler attribute for start block: B:3:0x0014 */
        /* JADX WARNING: Missing exception handler attribute for start block: B:5:0x001f */
        /* JADX WARNING: Missing exception handler attribute for start block: B:7:0x002a */
        static {
            /*
                org.jdom2.Content$CType[] r0 = org.jdom2.Content.CType.values()
                int r0 = r0.length
                int[] r0 = new int[r0]
                $SwitchMap$org$jdom2$Content$CType = r0
                int[] r0 = $SwitchMap$org$jdom2$Content$CType     // Catch:{ NoSuchFieldError -> 0x0014 }
                org.jdom2.Content$CType r1 = org.jdom2.Content.CType.Text     // Catch:{ NoSuchFieldError -> 0x0014 }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x0014 }
                r2 = 1
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x0014 }
            L_0x0014:
                int[] r0 = $SwitchMap$org$jdom2$Content$CType     // Catch:{ NoSuchFieldError -> 0x001f }
                org.jdom2.Content$CType r1 = org.jdom2.Content.CType.CDATA     // Catch:{ NoSuchFieldError -> 0x001f }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x001f }
                r2 = 2
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x001f }
            L_0x001f:
                int[] r0 = $SwitchMap$org$jdom2$Content$CType     // Catch:{ NoSuchFieldError -> 0x002a }
                org.jdom2.Content$CType r1 = org.jdom2.Content.CType.EntityRef     // Catch:{ NoSuchFieldError -> 0x002a }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x002a }
                r2 = 3
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x002a }
            L_0x002a:
                int[] r0 = $SwitchMap$org$jdom2$Content$CType     // Catch:{ NoSuchFieldError -> 0x0035 }
                org.jdom2.Content$CType r1 = org.jdom2.Content.CType.Element     // Catch:{ NoSuchFieldError -> 0x0035 }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x0035 }
                r2 = 4
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x0035 }
            L_0x0035:
                return
            */
            throw new UnsupportedOperationException("Method not decompiled: com.rometools.rome.p052io.impl.RSS092Parser.C46201.<clinit>():void");
        }
    }

    /* access modifiers changed from: protected */
    public String getRSSVersion() {
        return "0.92";
    }

    public RSS092Parser() {
        this("rss_0.92");
    }

    protected RSS092Parser(String str) {
        super(str);
    }

    /* access modifiers changed from: protected */
    public WireFeed parseChannel(Element element, Locale locale) {
        Channel channel = (Channel) super.parseChannel(element, locale);
        String str = "cloud";
        Element child = element.getChild("channel", getRSSNamespace()).getChild(str, getRSSNamespace());
        if (child != null) {
            Cloud cloud = new Cloud();
            String attributeValue = child.getAttributeValue(ClientCookie.DOMAIN_ATTR);
            if (attributeValue != null) {
                cloud.setDomain(attributeValue);
            }
            String attributeValue2 = child.getAttributeValue(ClientCookie.PORT_ATTR);
            if (attributeValue2 != null) {
                cloud.setPort(Integer.parseInt(attributeValue2.trim()));
            }
            String attributeValue3 = child.getAttributeValue(ClientCookie.PATH_ATTR);
            if (attributeValue3 != null) {
                cloud.setPath(attributeValue3);
            }
            String attributeValue4 = child.getAttributeValue("registerProcedure");
            if (attributeValue4 != null) {
                cloud.setRegisterProcedure(attributeValue4);
            }
            String attributeValue5 = child.getAttributeValue("protocol");
            if (attributeValue5 != null) {
                cloud.setProtocol(attributeValue5);
            }
            channel.setCloud(cloud);
        }
        return channel;
    }

    /* access modifiers changed from: protected */
    public Item parseItem(Element element, Element element2, Locale locale) {
        Item parseItem = super.parseItem(element, element2, locale);
        Element child = element2.getChild(Param.SOURCE, getRSSNamespace());
        String str = "url";
        if (child != null) {
            Source source = new Source();
            source.setUrl(child.getAttributeValue(str));
            source.setValue(child.getText());
            parseItem.setSource(source);
        }
        List<Element> children = element2.getChildren("enclosure");
        if (!children.isEmpty()) {
            ArrayList arrayList = new ArrayList();
            for (Element element3 : children) {
                Enclosure enclosure = new Enclosure();
                String attributeValue = element3.getAttributeValue(str);
                if (attributeValue != null) {
                    enclosure.setUrl(attributeValue);
                }
                enclosure.setLength(NumberParser.parseLong(element3.getAttributeValue("length"), 0));
                String attributeValue2 = element3.getAttributeValue("type");
                if (attributeValue2 != null) {
                    enclosure.setType(attributeValue2);
                }
                arrayList.add(enclosure);
            }
            parseItem.setEnclosures(arrayList);
        }
        parseItem.setCategories(parseCategories(element2.getChildren("category")));
        return parseItem;
    }

    /* access modifiers changed from: protected */
    public List<Category> parseCategories(List<Element> list) {
        if (list.isEmpty()) {
            return null;
        }
        ArrayList arrayList = new ArrayList();
        for (Element element : list) {
            Category category = new Category();
            String attributeValue = element.getAttributeValue(ClientCookie.DOMAIN_ATTR);
            if (attributeValue != null) {
                category.setDomain(attributeValue);
            }
            category.setValue(element.getText());
            arrayList.add(category);
        }
        return arrayList;
    }

    /* access modifiers changed from: protected */
    public Description parseItemDescription(Element element, Element element2) {
        Description description = new Description();
        StringBuilder sb = new StringBuilder();
        XMLOutputter xMLOutputter = new XMLOutputter();
        for (Content content : element2.getContent()) {
            int i = C46201.$SwitchMap$org$jdom2$Content$CType[content.getCType().ordinal()];
            if (i == 1 || i == 2) {
                sb.append(content.getValue());
            } else if (i == 3) {
                LOG.debug("Entity: {}", (Object) content.getValue());
                sb.append(content.getValue());
            } else if (i == 4) {
                sb.append(xMLOutputter.outputString((Element) content));
            }
        }
        description.setValue(sb.toString());
        String attributeValue = element2.getAttributeValue("type");
        if (attributeValue == null) {
            attributeValue = "text/html";
        }
        description.setType(attributeValue);
        return description;
    }
}
