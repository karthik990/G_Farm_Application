package org.jdom2.input.stax;

import org.jdom2.Namespace;

public interface StAXFilter {
    String includeCDATA(int i, String str);

    String includeComment(int i, String str);

    boolean includeDocType();

    boolean includeElement(int i, String str, Namespace namespace);

    boolean includeEntityRef(int i, String str);

    boolean includeProcessingInstruction(int i, String str);

    String includeText(int i, String str);

    String pruneCDATA(int i, String str);

    String pruneComment(int i, String str);

    boolean pruneElement(int i, String str, Namespace namespace);

    boolean pruneEntityRef(int i, String str);

    boolean pruneProcessingInstruction(int i, String str);

    String pruneText(int i, String str);
}
