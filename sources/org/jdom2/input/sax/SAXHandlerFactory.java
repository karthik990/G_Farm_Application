package org.jdom2.input.sax;

import org.jdom2.JDOMFactory;

public interface SAXHandlerFactory {
    SAXHandler createSAXHandler(JDOMFactory jDOMFactory);
}
