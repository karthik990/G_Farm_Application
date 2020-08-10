package com.rometools.rome.p052io.impl;

import com.anjlab.android.iab.p020v3.Constants;
import com.braintreepayments.api.models.PostalAddressParser;
import com.google.firebase.analytics.FirebaseAnalytics.Param;
import com.rometools.rome.feed.rss.Category;
import com.rometools.rome.feed.rss.Channel;
import com.rometools.rome.feed.rss.Cloud;
import com.rometools.rome.feed.rss.Enclosure;
import com.rometools.rome.feed.rss.Item;
import com.rometools.rome.feed.rss.Source;
import com.rometools.rome.p052io.FeedException;
import java.util.List;
import org.apache.http.cookie.ClientCookie;
import org.jdom2.Attribute;
import org.jdom2.Content;
import org.jdom2.Element;

/* renamed from: com.rometools.rome.io.impl.RSS092Generator */
public class RSS092Generator extends RSS091UserlandGenerator {
    /* access modifiers changed from: protected */
    public void checkItemConstraints(Element element) throws FeedException {
    }

    /* access modifiers changed from: protected */
    public void checkItemsConstraints(Element element) throws FeedException {
    }

    public RSS092Generator() {
        this("rss_0.92", "0.92");
    }

    protected RSS092Generator(String str, String str2) {
        super(str, str2);
    }

    /* access modifiers changed from: protected */
    public void populateChannel(Channel channel, Element element) {
        super.populateChannel(channel, element);
        Cloud cloud = channel.getCloud();
        if (cloud != null) {
            element.addContent((Content) generateCloud(cloud));
        }
    }

    /* access modifiers changed from: protected */
    public Element generateCloud(Cloud cloud) {
        Element element = new Element("cloud", getFeedNamespace());
        String domain = cloud.getDomain();
        if (domain != null) {
            element.setAttribute(new Attribute(ClientCookie.DOMAIN_ATTR, domain));
        }
        int port = cloud.getPort();
        if (port != 0) {
            element.setAttribute(new Attribute(ClientCookie.PORT_ATTR, String.valueOf(port)));
        }
        String path = cloud.getPath();
        if (path != null) {
            element.setAttribute(new Attribute(ClientCookie.PATH_ATTR, path));
        }
        String registerProcedure = cloud.getRegisterProcedure();
        if (registerProcedure != null) {
            element.setAttribute(new Attribute("registerProcedure", registerProcedure));
        }
        String protocol = cloud.getProtocol();
        if (protocol != null) {
            element.setAttribute(new Attribute("protocol", protocol));
        }
        return element;
    }

    /* access modifiers changed from: protected */
    public int getNumberOfEnclosures(List<Enclosure> list) {
        return !list.isEmpty() ? 1 : 0;
    }

    /* access modifiers changed from: protected */
    public void populateItem(Item item, Element element, int i) {
        super.populateItem(item, element, i);
        Source source = item.getSource();
        if (source != null) {
            element.addContent((Content) generateSourceElement(source));
        }
        List enclosures = item.getEnclosures();
        for (int i2 = 0; i2 < getNumberOfEnclosures(enclosures); i2++) {
            element.addContent((Content) generateEnclosure((Enclosure) enclosures.get(i2)));
        }
        for (Category generateCategoryElement : item.getCategories()) {
            element.addContent((Content) generateCategoryElement(generateCategoryElement));
        }
    }

    /* access modifiers changed from: protected */
    public Element generateSourceElement(Source source) {
        Element element = new Element(Param.SOURCE, getFeedNamespace());
        String url = source.getUrl();
        if (url != null) {
            element.setAttribute(new Attribute("url", url));
        }
        element.addContent(source.getValue());
        return element;
    }

    /* access modifiers changed from: protected */
    public Element generateEnclosure(Enclosure enclosure) {
        Element element = new Element("enclosure", getFeedNamespace());
        String url = enclosure.getUrl();
        if (url != null) {
            element.setAttribute("url", url);
        }
        long length = enclosure.getLength();
        if (length != 0) {
            element.setAttribute("length", String.valueOf(length));
        }
        String type = enclosure.getType();
        if (type != null) {
            element.setAttribute("type", type);
        }
        return element;
    }

    /* access modifiers changed from: protected */
    public Element generateCategoryElement(Category category) {
        Element element = new Element("category", getFeedNamespace());
        String domain = category.getDomain();
        if (domain != null) {
            element.setAttribute(ClientCookie.DOMAIN_ATTR, domain);
        }
        element.addContent(category.getValue());
        return element;
    }

    /* access modifiers changed from: protected */
    public void checkChannelConstraints(Element element) throws FeedException {
        checkNotNullAndLength(element, "title", 0, -1);
        checkNotNullAndLength(element, Constants.RESPONSE_DESCRIPTION, 0, -1);
        checkNotNullAndLength(element, "link", 0, -1);
    }

    /* access modifiers changed from: protected */
    public void checkImageConstraints(Element element) throws FeedException {
        checkNotNullAndLength(element, "title", 0, -1);
        checkNotNullAndLength(element, "url", 0, -1);
    }

    /* access modifiers changed from: protected */
    public void checkTextInputConstraints(Element element) throws FeedException {
        checkNotNullAndLength(element, "title", 0, -1);
        checkNotNullAndLength(element, Constants.RESPONSE_DESCRIPTION, 0, -1);
        checkNotNullAndLength(element, PostalAddressParser.USER_ADDRESS_NAME_KEY, 0, -1);
        checkNotNullAndLength(element, "link", 0, -1);
    }
}
