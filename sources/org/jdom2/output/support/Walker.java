package org.jdom2.output.support;

import org.jdom2.Content;

public interface Walker {
    boolean hasNext();

    boolean isAllText();

    boolean isAllWhitespace();

    boolean isCDATA();

    Content next();

    String text();
}
