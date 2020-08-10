package org.jdom2.output;

import org.xml.sax.Locator;

public interface JDOMLocator extends Locator {
    Object getNode();
}
