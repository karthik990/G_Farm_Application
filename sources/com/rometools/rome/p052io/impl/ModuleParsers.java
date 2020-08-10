package com.rometools.rome.p052io.impl;

import com.rometools.rome.feed.module.Module;
import com.rometools.rome.p052io.ModuleParser;
import com.rometools.rome.p052io.WireFeedParser;
import com.rometools.utils.Lists;
import java.util.List;
import java.util.Locale;
import org.jdom2.Element;
import org.jdom2.Namespace;

/* renamed from: com.rometools.rome.io.impl.ModuleParsers */
public class ModuleParsers extends PluginManager<ModuleParser> {
    public ModuleParsers(String str, WireFeedParser wireFeedParser) {
        super(str, wireFeedParser, null);
    }

    public String getKey(ModuleParser moduleParser) {
        return moduleParser.getNamespaceUri();
    }

    public List<String> getModuleNamespaces() {
        return getKeys();
    }

    public List<Module> parseModules(Element element, Locale locale) {
        List<Module> list = null;
        for (ModuleParser moduleParser : getPlugins()) {
            if (hasElementsFrom(element, Namespace.getNamespace(moduleParser.getNamespaceUri()))) {
                Module parse = moduleParser.parse(element, locale);
                if (parse != null) {
                    list = Lists.createWhenNull(list);
                    list.add(parse);
                }
            }
        }
        return list;
    }

    private boolean hasElementsFrom(Element element, Namespace namespace) {
        for (Element namespace2 : element.getChildren()) {
            if (namespace.equals(namespace2.getNamespace())) {
                return true;
            }
        }
        return false;
    }
}
