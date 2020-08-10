package org.jdom2.transform;

import org.jdom2.JDOMException;

public class XSLTransformException extends JDOMException {
    private static final long serialVersionUID = 200;

    public XSLTransformException() {
    }

    public XSLTransformException(String str) {
        super(str);
    }

    public XSLTransformException(String str, Exception exc) {
        super(str, exc);
    }
}
