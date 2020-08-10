package com.rometools.rome.p052io.impl;

import com.rometools.rome.feed.module.Module;
import com.rometools.rome.p052io.WireFeedGenerator;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.jdom2.Content;
import org.jdom2.Element;
import org.jdom2.Namespace;
import org.jdom2.Parent;

/* renamed from: com.rometools.rome.io.impl.BaseWireFeedGenerator */
public abstract class BaseWireFeedGenerator implements WireFeedGenerator {
    private static final String FEED_MODULE_GENERATORS_POSFIX_KEY = ".feed.ModuleGenerator.classes";
    private static final String ITEM_MODULE_GENERATORS_POSFIX_KEY = ".item.ModuleGenerator.classes";
    private static final String PERSON_MODULE_GENERATORS_POSFIX_KEY = ".person.ModuleGenerator.classes";
    private final Namespace[] allModuleNamespaces;
    private final ModuleGenerators feedModuleGenerators;
    private final ModuleGenerators itemModuleGenerators;
    private final ModuleGenerators personModuleGenerators;
    private final String type;

    protected BaseWireFeedGenerator(String str) {
        this.type = str;
        StringBuilder sb = new StringBuilder();
        sb.append(str);
        sb.append(FEED_MODULE_GENERATORS_POSFIX_KEY);
        this.feedModuleGenerators = new ModuleGenerators(sb.toString(), this);
        StringBuilder sb2 = new StringBuilder();
        sb2.append(str);
        sb2.append(ITEM_MODULE_GENERATORS_POSFIX_KEY);
        this.itemModuleGenerators = new ModuleGenerators(sb2.toString(), this);
        StringBuilder sb3 = new StringBuilder();
        sb3.append(str);
        sb3.append(PERSON_MODULE_GENERATORS_POSFIX_KEY);
        this.personModuleGenerators = new ModuleGenerators(sb3.toString(), this);
        HashSet hashSet = new HashSet();
        for (Namespace add : this.feedModuleGenerators.getAllNamespaces()) {
            hashSet.add(add);
        }
        for (Namespace add2 : this.itemModuleGenerators.getAllNamespaces()) {
            hashSet.add(add2);
        }
        for (Namespace add3 : this.personModuleGenerators.getAllNamespaces()) {
            hashSet.add(add3);
        }
        this.allModuleNamespaces = new Namespace[hashSet.size()];
        hashSet.toArray(this.allModuleNamespaces);
    }

    public String getType() {
        return this.type;
    }

    /* access modifiers changed from: protected */
    public void generateModuleNamespaceDefs(Element element) {
        for (Namespace addNamespaceDeclaration : this.allModuleNamespaces) {
            element.addNamespaceDeclaration(addNamespaceDeclaration);
        }
    }

    /* access modifiers changed from: protected */
    public void generateFeedModules(List<Module> list, Element element) {
        this.feedModuleGenerators.generateModules(list, element);
    }

    public void generateItemModules(List<Module> list, Element element) {
        this.itemModuleGenerators.generateModules(list, element);
    }

    public void generatePersonModules(List<Module> list, Element element) {
        this.personModuleGenerators.generateModules(list, element);
    }

    /* access modifiers changed from: protected */
    public void generateForeignMarkup(Element element, List<Element> list) {
        if (list != null) {
            for (Element element2 : list) {
                Parent parent = element2.getParent();
                if (parent != null) {
                    parent.removeContent((Content) element2);
                }
                element.addContent((Content) element2);
            }
        }
    }

    protected static void purgeUnusedNamespaceDeclarations(Element element) {
        HashSet hashSet = new HashSet();
        collectUsedPrefixes(element, hashSet);
        List additionalNamespaces = element.getAdditionalNamespaces();
        ArrayList<Namespace> arrayList = new ArrayList<>();
        arrayList.addAll(additionalNamespaces);
        for (Namespace namespace : arrayList) {
            String prefix = namespace.getPrefix();
            if (prefix != null && prefix.length() > 0 && !hashSet.contains(prefix)) {
                element.removeNamespaceDeclaration(namespace);
            }
        }
    }

    private static void collectUsedPrefixes(Element element, Set<String> set) {
        String namespacePrefix = element.getNamespacePrefix();
        if (namespacePrefix != null && namespacePrefix.length() > 0 && !set.contains(namespacePrefix)) {
            set.add(namespacePrefix);
        }
        for (Element collectUsedPrefixes : element.getChildren()) {
            collectUsedPrefixes(collectUsedPrefixes, set);
        }
    }
}
