package com.rometools.rome.p052io;

import com.rometools.rome.feed.module.Module;
import java.util.Set;
import org.jdom2.Element;
import org.jdom2.Namespace;

/* renamed from: com.rometools.rome.io.ModuleGenerator */
public interface ModuleGenerator {
    void generate(Module module, Element element);

    String getNamespaceUri();

    Set<Namespace> getNamespaces();
}
