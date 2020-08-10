package com.rometools.rome.p052io.impl;

import com.rometools.rome.feed.module.Module;
import com.rometools.rome.feed.module.SyModule;
import com.rometools.rome.p052io.ModuleGenerator;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.Locale;
import java.util.Set;
import org.jdom2.Content;
import org.jdom2.Element;
import org.jdom2.Namespace;

/* renamed from: com.rometools.rome.io.impl.SyModuleGenerator */
public class SyModuleGenerator implements ModuleGenerator {
    private static final Set<Namespace> NAMESPACES;
    private static final Namespace SY_NS = Namespace.getNamespace("sy", "http://purl.org/rss/1.0/modules/syndication/");
    private static final String SY_URI = "http://purl.org/rss/1.0/modules/syndication/";

    public String getNamespaceUri() {
        return "http://purl.org/rss/1.0/modules/syndication/";
    }

    static {
        HashSet hashSet = new HashSet();
        hashSet.add(SY_NS);
        NAMESPACES = Collections.unmodifiableSet(hashSet);
    }

    public Set<Namespace> getNamespaces() {
        return NAMESPACES;
    }

    public void generate(Module module, Element element) {
        SyModule syModule = (SyModule) module;
        String updatePeriod = syModule.getUpdatePeriod();
        if (updatePeriod != null) {
            Element element2 = new Element("updatePeriod", SY_NS);
            element2.addContent(updatePeriod);
            element.addContent((Content) element2);
        }
        Element element3 = new Element("updateFrequency", SY_NS);
        element3.addContent(String.valueOf(syModule.getUpdateFrequency()));
        element.addContent((Content) element3);
        Date updateBase = syModule.getUpdateBase();
        if (updateBase != null) {
            Element element4 = new Element("updateBase", SY_NS);
            element4.addContent(DateParser.formatW3CDateTime(updateBase, Locale.US));
            element.addContent((Content) element4);
        }
    }
}
