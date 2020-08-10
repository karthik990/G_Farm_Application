package org.jdom2.input.sax;

import org.jdom2.JDOMFactory;

public final class DefaultSAXHandlerFactory implements SAXHandlerFactory {

    private static final class DefaultSAXHandler extends SAXHandler {
        public DefaultSAXHandler(JDOMFactory jDOMFactory) {
            super(jDOMFactory);
        }
    }

    public SAXHandler createSAXHandler(JDOMFactory jDOMFactory) {
        return new DefaultSAXHandler(jDOMFactory);
    }
}
