package org.jdom2.located;

import org.jdom2.Element;
import org.jdom2.Namespace;

public class LocatedElement extends Element implements Located {
    private static final long serialVersionUID = 200;
    private int col;
    private int line;

    public LocatedElement(String str, Namespace namespace) {
        super(str, namespace);
    }

    public LocatedElement(String str) {
        super(str);
    }

    public LocatedElement(String str, String str2) {
        super(str, str2);
    }

    public LocatedElement(String str, String str2, String str3) {
        super(str, str2, str3);
    }

    public int getLine() {
        return this.line;
    }

    public int getColumn() {
        return this.col;
    }

    public void setLine(int i) {
        this.line = i;
    }

    public void setColumn(int i) {
        this.col = i;
    }
}
