package org.jdom2.located;

import java.util.Map;
import org.jdom2.ProcessingInstruction;

public class LocatedProcessingInstruction extends ProcessingInstruction implements Located {
    private static final long serialVersionUID = 200;
    private int col;
    private int line;

    public LocatedProcessingInstruction(String str) {
        super(str);
    }

    public LocatedProcessingInstruction(String str, Map<String, String> map) {
        super(str, map);
    }

    public LocatedProcessingInstruction(String str, String str2) {
        super(str, str2);
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
