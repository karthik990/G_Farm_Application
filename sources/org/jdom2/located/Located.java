package org.jdom2.located;

public interface Located {
    int getColumn();

    int getLine();

    void setColumn(int i);

    void setLine(int i);
}
