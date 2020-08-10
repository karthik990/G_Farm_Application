package com.rometools.rome.p052io.impl;

import com.anjlab.android.iab.p020v3.Constants;
import com.rometools.rome.feed.WireFeed;
import com.rometools.rome.feed.rss.Channel;
import com.rometools.rome.feed.rss.Content;
import com.rometools.rome.feed.rss.Description;
import com.rometools.rome.feed.rss.Image;
import com.rometools.rome.feed.rss.Item;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import org.apache.http.cookie.ClientCookie;
import org.apache.http.protocol.HTTP;
import org.jdom2.Attribute;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.Namespace;
import p043io.fabric.sdk.android.services.settings.SettingsJsonConstants;

/* renamed from: com.rometools.rome.io.impl.RSS091UserlandParser */
public class RSS091UserlandParser extends RSS090Parser {
    /* access modifiers changed from: protected */
    public String getRSSVersion() {
        return "0.91";
    }

    /* access modifiers changed from: protected */
    public String getTextInputLabel() {
        return "textInput";
    }

    /* access modifiers changed from: protected */
    public boolean isHourFormat24(Element element) {
        return true;
    }

    public RSS091UserlandParser() {
        this("rss_0.91U");
    }

    protected RSS091UserlandParser(String str) {
        super(str, null);
    }

    public boolean isMyType(Document document) {
        Element rootElement = document.getRootElement();
        Attribute attribute = rootElement.getAttribute(ClientCookie.VERSION_ATTR);
        return rootElement.getName().equals("rss") && attribute != null && attribute.getValue().equals(getRSSVersion());
    }

    /* access modifiers changed from: protected */
    public Namespace getRSSNamespace() {
        return Namespace.getNamespace("");
    }

    /* access modifiers changed from: protected */
    public WireFeed parseChannel(Element element, Locale locale) {
        Channel channel = (Channel) super.parseChannel(element, locale);
        Element child = element.getChild("channel", getRSSNamespace());
        Element child2 = child.getChild("language", getRSSNamespace());
        if (child2 != null) {
            channel.setLanguage(child2.getText());
        }
        Element child3 = child.getChild("rating", getRSSNamespace());
        if (child3 != null) {
            channel.setRating(child3.getText());
        }
        Element child4 = child.getChild("copyright", getRSSNamespace());
        if (child4 != null) {
            channel.setCopyright(child4.getText());
        }
        Element child5 = child.getChild("pubDate", getRSSNamespace());
        if (child5 != null) {
            channel.setPubDate(DateParser.parseDate(child5.getText(), locale));
        }
        Element child6 = child.getChild("lastBuildDate", getRSSNamespace());
        if (child6 != null) {
            channel.setLastBuildDate(DateParser.parseDate(child6.getText(), locale));
        }
        Element child7 = child.getChild("docs", getRSSNamespace());
        if (child7 != null) {
            channel.setDocs(child7.getText());
        }
        Element child8 = child.getChild("generator", getRSSNamespace());
        if (child8 != null) {
            channel.setGenerator(child8.getText());
        }
        Element child9 = child.getChild("managingEditor", getRSSNamespace());
        if (child9 != null) {
            channel.setManagingEditor(child9.getText());
        }
        Element child10 = child.getChild("webMaster", getRSSNamespace());
        if (child10 != null) {
            channel.setWebMaster(child10.getText());
        }
        Element child11 = child.getChild("skipHours");
        if (child11 != null) {
            ArrayList arrayList = new ArrayList();
            for (Element text : child11.getChildren("hour", getRSSNamespace())) {
                arrayList.add(new Integer(text.getText().trim()));
            }
            channel.setSkipHours(arrayList);
        }
        Element child12 = child.getChild("skipDays");
        if (child12 != null) {
            ArrayList arrayList2 = new ArrayList();
            for (Element text2 : child12.getChildren("day", getRSSNamespace())) {
                arrayList2.add(text2.getText().trim());
            }
            channel.setSkipDays(arrayList2);
        }
        return channel;
    }

    /* access modifiers changed from: protected */
    public Image parseImage(Element element) {
        Image parseImage = super.parseImage(element);
        if (parseImage != null) {
            Element image = getImage(element);
            Element child = image.getChild(SettingsJsonConstants.ICON_WIDTH_KEY, getRSSNamespace());
            if (child != null) {
                Integer parseInt = NumberParser.parseInt(child.getText());
                if (parseInt != null) {
                    parseImage.setWidth(parseInt);
                }
            }
            Element child2 = image.getChild(SettingsJsonConstants.ICON_HEIGHT_KEY, getRSSNamespace());
            if (child2 != null) {
                Integer parseInt2 = NumberParser.parseInt(child2.getText());
                if (parseInt2 != null) {
                    parseImage.setHeight(parseInt2);
                }
            }
            Element child3 = image.getChild(Constants.RESPONSE_DESCRIPTION, getRSSNamespace());
            if (child3 != null) {
                parseImage.setDescription(child3.getText());
            }
        }
        return parseImage;
    }

    /* access modifiers changed from: protected */
    public List<Element> getItems(Element element) {
        Element child = element.getChild("channel", getRSSNamespace());
        if (child == null) {
            return Collections.emptyList();
        }
        return child.getChildren("item", getRSSNamespace());
    }

    /* access modifiers changed from: protected */
    public Element getImage(Element element) {
        Element child = element.getChild("channel", getRSSNamespace());
        if (child == null) {
            return null;
        }
        return child.getChild(TtmlNode.TAG_IMAGE, getRSSNamespace());
    }

    /* access modifiers changed from: protected */
    public Element getTextInput(Element element) {
        String textInputLabel = getTextInputLabel();
        Element child = element.getChild("channel", getRSSNamespace());
        if (child != null) {
            return child.getChild(textInputLabel, getRSSNamespace());
        }
        return null;
    }

    /* access modifiers changed from: protected */
    public Item parseItem(Element element, Element element2, Locale locale) {
        Item parseItem = super.parseItem(element, element2, locale);
        Element child = element2.getChild(Constants.RESPONSE_DESCRIPTION, getRSSNamespace());
        if (child != null) {
            parseItem.setDescription(parseItemDescription(element, child));
        }
        Element child2 = element2.getChild("encoded", getContentNamespace());
        if (child2 != null) {
            Content content = new Content();
            content.setType("html");
            content.setValue(child2.getText());
            parseItem.setContent(content);
        }
        return parseItem;
    }

    /* access modifiers changed from: protected */
    public Description parseItemDescription(Element element, Element element2) {
        Description description = new Description();
        description.setType(HTTP.PLAIN_TEXT_TYPE);
        description.setValue(element2.getText());
        return description;
    }
}
