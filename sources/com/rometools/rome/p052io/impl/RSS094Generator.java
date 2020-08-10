package com.rometools.rome.p052io.impl;

import com.anjlab.android.iab.p020v3.Constants;
import com.rometools.rome.feed.rss.Description;
import com.rometools.rome.feed.rss.Item;
import org.jdom2.Attribute;
import org.jdom2.Element;

/* renamed from: com.rometools.rome.io.impl.RSS094Generator */
public class RSS094Generator extends RSS093Generator {
    public RSS094Generator() {
        this("rss_0.94", "0.94");
    }

    protected RSS094Generator(String str, String str2) {
        super(str, str2);
    }

    /* access modifiers changed from: protected */
    public void populateItem(Item item, Element element, int i) {
        super.populateItem(item, element, i);
        Description description = item.getDescription();
        if (!(description == null || description.getType() == null)) {
            element.getChild(Constants.RESPONSE_DESCRIPTION, getFeedNamespace()).setAttribute(new Attribute("type", description.getType()));
        }
        element.removeChild("expirationDate", getFeedNamespace());
    }
}
