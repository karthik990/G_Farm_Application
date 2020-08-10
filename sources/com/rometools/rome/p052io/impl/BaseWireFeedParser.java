package com.rometools.rome.p052io.impl;

import com.rometools.rome.feed.module.Extendable;
import com.rometools.rome.feed.module.Module;
import com.rometools.rome.p052io.WireFeedParser;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import org.jdom2.Attribute;
import org.jdom2.Content;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.Namespace;
import org.jdom2.ProcessingInstruction;
import org.jdom2.filter.ContentFilter;
import org.jdom2.filter.Filter;

/* renamed from: com.rometools.rome.io.impl.BaseWireFeedParser */
public abstract class BaseWireFeedParser implements WireFeedParser {
    private static final String FEED_MODULE_PARSERS_POSFIX_KEY = ".feed.ModuleParser.classes";
    private static final String ITEM_MODULE_PARSERS_POSFIX_KEY = ".item.ModuleParser.classes";
    private static final String PERSON_MODULE_PARSERS_POSFIX_KEY = ".person.ModuleParser.classes";
    private final ModuleParsers feedModuleParsers;
    private final ModuleParsers itemModuleParsers;
    private final Namespace namespace;
    private final ModuleParsers personModuleParsers;
    private final String type;

    protected BaseWireFeedParser(String str, Namespace namespace2) {
        this.type = str;
        this.namespace = namespace2;
        StringBuilder sb = new StringBuilder();
        sb.append(str);
        sb.append(FEED_MODULE_PARSERS_POSFIX_KEY);
        this.feedModuleParsers = new ModuleParsers(sb.toString(), this);
        StringBuilder sb2 = new StringBuilder();
        sb2.append(str);
        sb2.append(ITEM_MODULE_PARSERS_POSFIX_KEY);
        this.itemModuleParsers = new ModuleParsers(sb2.toString(), this);
        StringBuilder sb3 = new StringBuilder();
        sb3.append(str);
        sb3.append(PERSON_MODULE_PARSERS_POSFIX_KEY);
        this.personModuleParsers = new ModuleParsers(sb3.toString(), this);
    }

    public String getType() {
        return this.type;
    }

    /* access modifiers changed from: protected */
    public List<Module> parseFeedModules(Element element, Locale locale) {
        return this.feedModuleParsers.parseModules(element, locale);
    }

    /* access modifiers changed from: protected */
    public List<Module> parseItemModules(Element element, Locale locale) {
        return this.itemModuleParsers.parseModules(element, locale);
    }

    /* access modifiers changed from: protected */
    public List<Module> parsePersonModules(Element element, Locale locale) {
        return this.personModuleParsers.parseModules(element, locale);
    }

    /* access modifiers changed from: protected */
    public List<Element> extractForeignMarkup(Element element, Extendable extendable, Namespace namespace2) {
        ArrayList arrayList = new ArrayList();
        for (Element element2 : element.getChildren()) {
            if (!namespace2.equals(element2.getNamespace()) && extendable.getModule(element2.getNamespaceURI()) == null) {
                arrayList.add(element2.clone());
            }
        }
        Iterator it = arrayList.iterator();
        while (it.hasNext()) {
            ((Element) it.next()).detach();
        }
        return arrayList;
    }

    /* access modifiers changed from: protected */
    public Attribute getAttribute(Element element, String str) {
        Attribute attribute = element.getAttribute(str);
        return attribute == null ? element.getAttribute(str, this.namespace) : attribute;
    }

    /* access modifiers changed from: protected */
    public String getAttributeValue(Element element, String str) {
        Attribute attribute = getAttribute(element, str);
        if (attribute != null) {
            return attribute.getValue();
        }
        return null;
    }

    /* access modifiers changed from: protected */
    public String getStyleSheet(Document document) {
        for (Content content : document.getContent((Filter<F>) new ContentFilter<F>(16))) {
            ProcessingInstruction processingInstruction = (ProcessingInstruction) content;
            if ("text/xsl".equals(processingInstruction.getPseudoAttributeValue("type"))) {
                return processingInstruction.getPseudoAttributeValue("href");
            }
        }
        return null;
    }
}
