package com.rometools.rome.p052io.impl;

import com.rometools.rome.feed.module.Module;
import com.rometools.rome.p052io.ModuleGenerator;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.jdom2.Element;
import org.jdom2.Namespace;

/* renamed from: com.rometools.rome.io.impl.ModuleGenerators */
public class ModuleGenerators extends PluginManager<ModuleGenerator> {
    private Set<Namespace> allNamespaces;

    public ModuleGenerators(String str, BaseWireFeedGenerator baseWireFeedGenerator) {
        super(str, null, baseWireFeedGenerator);
    }

    public ModuleGenerator getGenerator(String str) {
        return (ModuleGenerator) getPlugin(str);
    }

    /* access modifiers changed from: protected */
    public String getKey(ModuleGenerator moduleGenerator) {
        return moduleGenerator.getNamespaceUri();
    }

    public List<String> getModuleNamespaces() {
        return getKeys();
    }

    public void generateModules(List<Module> list, Element element) {
        Map pluginMap = getPluginMap();
        for (Module module : list) {
            ModuleGenerator moduleGenerator = (ModuleGenerator) pluginMap.get(module.getUri());
            if (moduleGenerator != null) {
                moduleGenerator.generate(module, element);
            }
        }
    }

    public Set<Namespace> getAllNamespaces() {
        if (this.allNamespaces == null) {
            this.allNamespaces = new HashSet();
            for (String generator : getModuleNamespaces()) {
                this.allNamespaces.addAll(getGenerator(generator).getNamespaces());
            }
        }
        return this.allNamespaces;
    }
}
