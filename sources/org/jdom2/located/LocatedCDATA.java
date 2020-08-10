package org.jdom2.located;

import org.jdom2.CDATA;

public class LocatedCDATA extends CDATA implements Located {
    private static final long serialVersionUID = 200;
    private int col;
    private int line;

    public LocatedCDATA(String str) {
        super(str);
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
