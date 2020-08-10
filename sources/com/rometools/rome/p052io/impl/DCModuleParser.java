package com.rometools.rome.p052io.impl;

import com.anjlab.android.iab.p020v3.Constants;
import com.google.firebase.analytics.FirebaseAnalytics.Param;
import com.mobiroller.constants.YoutubeConstants.YoutubeRequestParams;
import com.rometools.rome.feed.module.DCModule;
import com.rometools.rome.feed.module.DCModuleImpl;
import com.rometools.rome.feed.module.DCSubject;
import com.rometools.rome.feed.module.DCSubjectImpl;
import com.rometools.rome.feed.module.Module;
import com.rometools.rome.p052io.ModuleParser;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import org.jdom2.Attribute;
import org.jdom2.Element;
import org.jdom2.Namespace;
import p043io.fabric.sdk.android.services.settings.SettingsJsonConstants;

/* renamed from: com.rometools.rome.io.impl.DCModuleParser */
public class DCModuleParser implements ModuleParser {
    private static final Namespace DC_NS = Namespace.getNamespace(DCModule.URI);
    private static final Namespace RDF_NS = Namespace.getNamespace(RDF_URI);
    private static final String RDF_URI = "http://www.w3.org/1999/02/22-rdf-syntax-ns#";
    private static final Namespace TAXO_NS = Namespace.getNamespace(TAXO_URI);
    private static final String TAXO_URI = "http://purl.org/rss/1.0/modules/taxonomy/";

    public final String getNamespaceUri() {
        return DCModule.URI;
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

    public Module parse(Element element, Locale locale) {
        boolean z;
        DCModuleImpl dCModuleImpl = new DCModuleImpl();
        List children = element.getChildren("title", getDCNamespace());
        if (!children.isEmpty()) {
            dCModuleImpl.setTitles(parseElementList(children));
            z = true;
        } else {
            z = false;
        }
        List children2 = element.getChildren("creator", getDCNamespace());
        if (!children2.isEmpty()) {
            dCModuleImpl.setCreators(parseElementList(children2));
            z = true;
        }
        List children3 = element.getChildren("subject", getDCNamespace());
        if (!children3.isEmpty()) {
            dCModuleImpl.setSubjects(parseSubjects(children3));
            z = true;
        }
        List children4 = element.getChildren(Constants.RESPONSE_DESCRIPTION, getDCNamespace());
        if (!children4.isEmpty()) {
            dCModuleImpl.setDescriptions(parseElementList(children4));
            z = true;
        }
        List children5 = element.getChildren("publisher", getDCNamespace());
        if (!children5.isEmpty()) {
            dCModuleImpl.setPublishers(parseElementList(children5));
            z = true;
        }
        List children6 = element.getChildren("contributor", getDCNamespace());
        if (!children6.isEmpty()) {
            dCModuleImpl.setContributors(parseElementList(children6));
            z = true;
        }
        List children7 = element.getChildren(YoutubeRequestParams.req_search_order, getDCNamespace());
        if (!children7.isEmpty()) {
            dCModuleImpl.setDates(parseElementListDate(children7, locale));
            z = true;
        }
        List children8 = element.getChildren("type", getDCNamespace());
        if (!children8.isEmpty()) {
            dCModuleImpl.setTypes(parseElementList(children8));
            z = true;
        }
        List children9 = element.getChildren("format", getDCNamespace());
        if (!children9.isEmpty()) {
            dCModuleImpl.setFormats(parseElementList(children9));
            z = true;
        }
        List children10 = element.getChildren(SettingsJsonConstants.APP_IDENTIFIER_KEY, getDCNamespace());
        if (!children10.isEmpty()) {
            dCModuleImpl.setIdentifiers(parseElementList(children10));
            z = true;
        }
        List children11 = element.getChildren(Param.SOURCE, getDCNamespace());
        if (!children11.isEmpty()) {
            dCModuleImpl.setSources(parseElementList(children11));
            z = true;
        }
        List children12 = element.getChildren("language", getDCNamespace());
        if (!children12.isEmpty()) {
            dCModuleImpl.setLanguages(parseElementList(children12));
            z = true;
        }
        List children13 = element.getChildren("relation", getDCNamespace());
        if (!children13.isEmpty()) {
            dCModuleImpl.setRelations(parseElementList(children13));
            z = true;
        }
        List children14 = element.getChildren("coverage", getDCNamespace());
        if (!children14.isEmpty()) {
            dCModuleImpl.setCoverages(parseElementList(children14));
            z = true;
        }
        List children15 = element.getChildren("rights", getDCNamespace());
        if (!children15.isEmpty()) {
            dCModuleImpl.setRightsList(parseElementList(children15));
            z = true;
        }
        if (z) {
            return dCModuleImpl;
        }
        return null;
    }

    /* access modifiers changed from: protected */
    public final String getTaxonomy(Element element) {
        Element child = element.getChild("topic", getTaxonomyNamespace());
        if (child != null) {
            Attribute attribute = child.getAttribute("resource", getRDFNamespace());
            if (attribute != null) {
                return attribute.getValue();
            }
        }
        return null;
    }

    /* access modifiers changed from: protected */
    public final List<DCSubject> parseSubjects(List<Element> list) {
        ArrayList arrayList = new ArrayList();
        for (Element element : list) {
            Element child = element.getChild("Description", getRDFNamespace());
            if (child != null) {
                String taxonomy = getTaxonomy(child);
                for (Element element2 : child.getChildren(Param.VALUE, getRDFNamespace())) {
                    DCSubjectImpl dCSubjectImpl = new DCSubjectImpl();
                    dCSubjectImpl.setTaxonomyUri(taxonomy);
                    dCSubjectImpl.setValue(element2.getText());
                    arrayList.add(dCSubjectImpl);
                }
            } else {
                DCSubjectImpl dCSubjectImpl2 = new DCSubjectImpl();
                dCSubjectImpl2.setValue(element.getText());
                arrayList.add(dCSubjectImpl2);
            }
        }
        return arrayList;
    }

    /* access modifiers changed from: protected */
    public final List<String> parseElementList(List<Element> list) {
        ArrayList arrayList = new ArrayList();
        for (Element text : list) {
            arrayList.add(text.getText());
        }
        return arrayList;
    }

    /* access modifiers changed from: protected */
    public final List<Date> parseElementListDate(List<Element> list, Locale locale) {
        ArrayList arrayList = new ArrayList();
        for (Element text : list) {
            arrayList.add(DateParser.parseDate(text.getText(), locale));
        }
        return arrayList;
    }
}
