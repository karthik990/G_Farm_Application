package com.rometools.rome.p052io;

import com.rometools.rome.feed.module.Module;
import java.util.Locale;
import org.jdom2.Element;

/* renamed from: com.rometools.rome.io.ModuleParser */
public interface ModuleParser {
    String getNamespaceUri();

    Module parse(Element element, Locale locale);
}
