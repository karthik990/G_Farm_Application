package org.jdom2;

import java.util.List;

public interface NamespaceAware {
    List<Namespace> getNamespacesInScope();

    List<Namespace> getNamespacesInherited();

    List<Namespace> getNamespacesIntroduced();
}
