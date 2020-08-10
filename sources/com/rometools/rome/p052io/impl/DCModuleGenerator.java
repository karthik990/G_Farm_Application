package com.rometools.rome.p052io.impl;

import com.anjlab.android.iab.p020v3.Constants;
import com.google.firebase.analytics.FirebaseAnalytics.Param;
import com.mobiroller.constants.YoutubeConstants.YoutubeRequestParams;
import com.rometools.rome.feed.module.DCModule;
import com.rometools.rome.feed.module.DCSubject;
import com.rometools.rome.feed.module.Module;
import com.rometools.rome.p052io.ModuleGenerator;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import org.jdom2.Attribute;
import org.jdom2.Content;
import org.jdom2.Element;
import org.jdom2.Namespace;
import p043io.fabric.sdk.android.services.settings.SettingsJsonConstants;

/* renamed from: com.rometools.rome.io.impl.DCModuleGenerator */
public class DCModuleGenerator implements ModuleGenerator {
    private static final Namespace DC_NS = Namespace.getNamespace("dc", "http://purl.org/dc/elements/1.1/");
    private static final String DC_URI = "http://purl.org/dc/elements/1.1/";
    private static final Set<Namespace> NAMESPACES;
    private static final Namespace RDF_NS = Namespace.getNamespace("rdf", RDF_URI);
    private static final String RDF_URI = "http://www.w3.org/1999/02/22-rdf-syntax-ns#";
    private static final Namespace TAXO_NS = Namespace.getNamespace("taxo", TAXO_URI);
    private static final String TAXO_URI = "http://purl.org/rss/1.0/modules/taxonomy/";

    public final String getNamespaceUri() {
        return "http://purl.org/dc/elements/1.1/";
    }

    static {
        HashSet hashSet = new HashSet();
        hashSet.add(DC_NS);
        hashSet.add(TAXO_NS);
        hashSet.add(RDF_NS);
        NAMESPACES = Collections.unmodifiableSet(hashSet);
    }

    private final Namespace getDCNamespace() {
        return DC_NS;
    }

    private final Namespace getRDFNamespace() {
        return RDF_NS;
    }

    private final Namespace getTaxonomyNamespace() {
        return TAXO_NS;
    }

    public final Set<Namespace> getNamespaces() {
        return NAMESPACES;
    }

    public final void generate(Module module, Element element) {
        DCModule dCModule = (DCModule) module;
        if (dCModule.getTitle() != null) {
            element.addContent((Collection<? extends Content>) generateSimpleElementList("title", dCModule.getTitles()));
        }
        if (dCModule.getCreator() != null) {
            element.addContent((Collection<? extends Content>) generateSimpleElementList("creator", dCModule.getCreators()));
        }
        for (DCSubject generateSubjectElement : dCModule.getSubjects()) {
            element.addContent((Content) generateSubjectElement(generateSubjectElement));
        }
        if (dCModule.getDescription() != null) {
            element.addContent((Collection<? extends Content>) generateSimpleElementList(Constants.RESPONSE_DESCRIPTION, dCModule.getDescriptions()));
        }
        if (dCModule.getPublisher() != null) {
            element.addContent((Collection<? extends Content>) generateSimpleElementList("publisher", dCModule.getPublishers()));
        }
        List contributors = dCModule.getContributors();
        if (contributors != null) {
            element.addContent((Collection<? extends Content>) generateSimpleElementList("contributor", contributors));
        }
        if (dCModule.getDate() != null) {
            for (Date formatW3CDateTime : dCModule.getDates()) {
                element.addContent((Content) generateSimpleElement(YoutubeRequestParams.req_search_order, DateParser.formatW3CDateTime(formatW3CDateTime, Locale.US)));
            }
        }
        if (dCModule.getType() != null) {
            element.addContent((Collection<? extends Content>) generateSimpleElementList("type", dCModule.getTypes()));
        }
        if (dCModule.getFormat() != null) {
            element.addContent((Collection<? extends Content>) generateSimpleElementList("format", dCModule.getFormats()));
        }
        if (dCModule.getIdentifier() != null) {
            element.addContent((Collection<? extends Content>) generateSimpleElementList(SettingsJsonConstants.APP_IDENTIFIER_KEY, dCModule.getIdentifiers()));
        }
        if (dCModule.getSource() != null) {
            element.addContent((Collection<? extends Content>) generateSimpleElementList(Param.SOURCE, dCModule.getSources()));
        }
        if (dCModule.getLanguage() != null) {
            element.addContent((Collection<? extends Content>) generateSimpleElementList("language", dCModule.getLanguages()));
        }
        if (dCModule.getRelation() != null) {
            element.addContent((Collection<? extends Content>) generateSimpleElementList("relation", dCModule.getRelations()));
        }
        if (dCModule.getCoverage() != null) {
            element.addContent((Collection<? extends Content>) generateSimpleElementList("coverage", dCModule.getCoverages()));
        }
        if (dCModule.getRights() != null) {
            element.addContent((Collection<? extends Content>) generateSimpleElementList("rights", dCModule.getRightsList()));
        }
    }

    /* access modifiers changed from: protected */
    public final Element generateSubjectElement(DCSubject dCSubject) {
        Element element = new Element("subject", getDCNamespace());
        String taxonomyUri = dCSubject.getTaxonomyUri();
        String value = dCSubject.getValue();
        if (taxonomyUri != null) {
            Attribute attribute = new Attribute("resource", taxonomyUri, getRDFNamespace());
            Element element2 = new Element("topic", getTaxonomyNamespace());
            element2.setAttribute(attribute);
            Element element3 = new Element("Description", getRDFNamespace());
            element3.addContent((Content) element2);
            if (value != null) {
                Element element4 = new Element(Param.VALUE, getRDFNamespace());
                element4.addContent(value);
                element3.addContent((Content) element4);
            }
            element.addContent((Content) element3);
        } else {
            element.addContent(value);
        }
        return element;
    }

    /* access modifiers changed from: protected */
    public final Element generateSimpleElement(String str, String str2) {
        Element element = new Element(str, getDCNamespace());
        element.addContent(str2);
        return element;
    }

    /* access modifiers changed from: protected */
    public final List<Element> generateSimpleElementList(String str, List<String> list) {
        ArrayList arrayList = new ArrayList();
        for (String generateSimpleElement : list) {
            arrayList.add(generateSimpleElement(str, generateSimpleElement));
        }
        return arrayList;
    }
}
