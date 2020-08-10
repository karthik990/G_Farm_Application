package org.jdom2.input.stax;

import org.jdom2.Namespace;

public class DefaultStAXFilter implements StAXFilter {
    public String includeCDATA(int i, String str) {
        return str;
    }

    public String includeComment(int i, String str) {
        return str;
    }

    public boolean includeDocType() {
        return true;
    }

    public boolean includeElement(int i, String str, Namespace namespace) {
        return true;
    }

    public boolean includeEntityRef(int i, String str) {
        return true;
    }

    public boolean includeProcessingInstruction(int i, String str) {
        return true;
    }

    public String includeText(int i, String str) {
        return str;
    }

    public String pruneCDATA(int i, String str) {
        return str;
    }

    public String pruneComment(int i, String str) {
        return str;
    }

    public boolean pruneElement(int i, String str, Namespace namespace) {
        return false;
    }

    public boolean pruneEntityRef(int i, String str) {
        return false;
    }

    public boolean pruneProcessingInstruction(int i, String str) {
        return false;
    }

    public String pruneText(int i, String str) {
        return str;
    }
}
