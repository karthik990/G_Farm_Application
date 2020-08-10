package com.rometools.rome.p052io.impl;

import com.rometools.rome.feed.module.Module;
import com.rometools.rome.feed.module.SyModule;
import com.rometools.rome.feed.module.SyModuleImpl;
import com.rometools.rome.p052io.ModuleParser;
import java.util.Locale;
import org.jdom2.Element;
import org.jdom2.Namespace;

/* renamed from: com.rometools.rome.io.impl.SyModuleParser */
public class SyModuleParser implements ModuleParser {
    public String getNamespaceUri() {
        return SyModule.URI;
    }

    private Namespace getDCNamespace() {
        return Namespace.getNamespace(SyModule.URI);
    }

    public Module parse(Element element, Locale locale) {
        boolean z;
        SyModuleImpl syModuleImpl = new SyModuleImpl();
        Element child = element.getChild("updatePeriod", getDCNamespace());
        if (child != null) {
            syModuleImpl.setUpdatePeriod(child.getText().trim());
            z = true;
        } else {
            z = false;
        }
        Element child2 = element.getChild("updateFrequency", getDCNamespace());
        if (child2 != null) {
            syModuleImpl.setUpdateFrequency(Integer.parseInt(child2.getText().trim()));
            z = true;
        }
        Element child3 = element.getChild("updateBase", getDCNamespace());
        if (child3 != null) {
            syModuleImpl.setUpdateBase(DateParser.parseDate(child3.getText(), locale));
            z = true;
        }
        if (z) {
            return syModuleImpl;
        }
        return null;
    }
}
