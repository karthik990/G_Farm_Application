package org.jdom2.xpath;

import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import org.jdom2.Attribute;
import org.jdom2.Comment;
import org.jdom2.Content;
import org.jdom2.Element;
import org.jdom2.Namespace;
import org.jdom2.NamespaceAware;
import org.jdom2.Parent;
import org.jdom2.ProcessingInstruction;
import org.jdom2.Text;
import org.jdom2.filter.Filters;

public final class XPathHelper {
    private XPathHelper() {
    }

    private static StringBuilder getPositionPath(Object obj, List<?> list, String str, StringBuilder sb) {
        sb.append(str);
        if (list != null) {
            int i = 0;
            Iterator it = list.iterator();
            while (it.hasNext()) {
                i++;
                if (it.next() == obj) {
                    break;
                }
            }
            if (i > 1 || it.hasNext()) {
                sb.append('[');
                sb.append(i);
                sb.append(']');
            }
        }
        return sb;
    }

    private static final StringBuilder getSingleStep(NamespaceAware namespaceAware, StringBuilder sb) {
        String str = "']";
        String str2 = "' and namespace-uri() = '";
        if (namespaceAware instanceof Content) {
            Content content = (Content) namespaceAware;
            Parent parent = content.getParent();
            List list = null;
            if (content instanceof Text) {
                if (parent != null) {
                    list = parent.getContent(Filters.text());
                }
                return getPositionPath(content, list, "text()", sb);
            } else if (content instanceof Comment) {
                if (parent != null) {
                    list = parent.getContent(Filters.comment());
                }
                return getPositionPath(content, list, "comment()", sb);
            } else if (content instanceof ProcessingInstruction) {
                if (parent != null) {
                    list = parent.getContent(Filters.processinginstruction());
                }
                return getPositionPath(content, list, "processing-instruction()", sb);
            } else {
                boolean z = content instanceof Element;
                if (z) {
                    Element element = (Element) content;
                    if (element.getNamespace() == Namespace.NO_NAMESPACE) {
                        String name = element.getName();
                        if (parent instanceof Element) {
                            list = ((Element) parent).getChildren(name);
                        }
                        return getPositionPath(content, list, name, sb);
                    }
                }
                if (z) {
                    Element element2 = (Element) content;
                    if (parent instanceof Element) {
                        list = ((Element) parent).getChildren(element2.getName(), element2.getNamespace());
                    }
                    StringBuilder sb2 = new StringBuilder();
                    sb2.append("*[local-name() = '");
                    sb2.append(element2.getName());
                    sb2.append(str2);
                    sb2.append(element2.getNamespaceURI());
                    sb2.append(str);
                    return getPositionPath(content, list, sb2.toString(), sb);
                }
                return getPositionPath(content, parent == null ? Collections.singletonList(namespaceAware) : parent.getContent(), "node()", sb);
            }
        } else {
            if (namespaceAware instanceof Attribute) {
                Attribute attribute = (Attribute) namespaceAware;
                if (attribute.getNamespace() == Namespace.NO_NAMESPACE) {
                    sb.append("@");
                    sb.append(attribute.getName());
                } else {
                    sb.append("@*[local-name() = '");
                    sb.append(attribute.getName());
                    sb.append(str2);
                    sb.append(attribute.getNamespaceURI());
                    sb.append(str);
                }
            }
            return sb;
        }
    }

    /* JADX WARNING: Incorrect type for immutable var: ssa=org.jdom2.Element, code=org.jdom2.Parent, for r2v0, types: [org.jdom2.Parent, org.jdom2.Element] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private static java.lang.StringBuilder getRelativeElementPath(org.jdom2.Parent r2, org.jdom2.Parent r3, java.lang.StringBuilder r4) {
        /*
            if (r2 != r3) goto L_0x0008
            java.lang.String r2 = "."
            r4.append(r2)
            return r4
        L_0x0008:
            java.util.ArrayList r0 = new java.util.ArrayList
            r0.<init>()
        L_0x000d:
            if (r3 == 0) goto L_0x0019
            if (r3 == r2) goto L_0x0019
            r0.add(r3)
            org.jdom2.Parent r3 = r3.getParent()
            goto L_0x000d
        L_0x0019:
            int r1 = r0.size()
            if (r3 == r2) goto L_0x0043
            r3 = 0
        L_0x0020:
            if (r2 == 0) goto L_0x002f
            int r1 = locate(r2, r0)
            if (r1 >= 0) goto L_0x002f
            int r3 = r3 + 1
            org.jdom2.Parent r2 = r2.getParent()
            goto L_0x0020
        L_0x002f:
            if (r2 == 0) goto L_0x003b
        L_0x0031:
            int r3 = r3 + -1
            if (r3 < 0) goto L_0x0043
            java.lang.String r2 = "../"
            r4.append(r2)
            goto L_0x0031
        L_0x003b:
            java.lang.IllegalArgumentException r2 = new java.lang.IllegalArgumentException
            java.lang.String r3 = "The 'from' and 'to' Element have no common ancestor."
            r2.<init>(r3)
            throw r2
        L_0x0043:
            int r1 = r1 + -1
            if (r1 < 0) goto L_0x0056
            java.lang.Object r2 = r0.get(r1)
            org.jdom2.NamespaceAware r2 = (org.jdom2.NamespaceAware) r2
            getSingleStep(r2, r4)
            java.lang.String r2 = "/"
            r4.append(r2)
            goto L_0x0043
        L_0x0056:
            int r2 = r4.length()
            int r2 = r2 + -1
            r4.setLength(r2)
            return r4
        */
        throw new UnsupportedOperationException("Method not decompiled: org.jdom2.xpath.XPathHelper.getRelativeElementPath(org.jdom2.Element, org.jdom2.Parent, java.lang.StringBuilder):java.lang.StringBuilder");
    }

    private static int locate(Parent parent, List<Parent> list) {
        int size = list.size();
        do {
            size--;
            if (size < 0) {
                return -1;
            }
        } while (parent != list.get(size));
        return size;
    }

    public static String getRelativePath(Content content, Content content2) {
        if (content == null) {
            throw new NullPointerException("Cannot create a path from a null target");
        } else if (content2 != null) {
            StringBuilder sb = new StringBuilder();
            if (content == content2) {
                return ".";
            }
            Element parentElement = content instanceof Element ? (Element) content : content.getParentElement();
            if (content != parentElement) {
                sb.append("../");
            }
            if (content2 instanceof Element) {
                getRelativeElementPath(parentElement, (Element) content2, sb);
            } else {
                Parent parent = content2.getParent();
                if (parent != null) {
                    getRelativeElementPath(parentElement, parent, sb);
                    sb.append("/");
                    getSingleStep(content2, sb);
                } else {
                    throw new IllegalArgumentException("Cannot get a relative XPath to detached content.");
                }
            }
            return sb.toString();
        } else {
            throw new NullPointerException("Cannot create a path to a null target");
        }
    }

    public static String getRelativePath(Content content, Attribute attribute) {
        if (content == null) {
            throw new NullPointerException("Cannot create a path from a null Content");
        } else if (attribute != null) {
            Element parent = attribute.getParent();
            if (parent != null) {
                StringBuilder sb = new StringBuilder(getRelativePath(content, (Content) parent));
                sb.append("/");
                getSingleStep(attribute, sb);
                return sb.toString();
            }
            throw new IllegalArgumentException("Cannot create a path to detached Attribute");
        } else {
            throw new NullPointerException("Cannot create a path to a null Attribute");
        }
    }

    public static String getRelativePath(Attribute attribute, Attribute attribute2) {
        if (attribute == null) {
            throw new NullPointerException("Cannot create a path from a null 'from'");
        } else if (attribute2 == null) {
            throw new NullPointerException("Cannot create a path to a null target");
        } else if (attribute == attribute2) {
            return ".";
        } else {
            Element parent = attribute.getParent();
            if (parent != null) {
                StringBuilder sb = new StringBuilder();
                sb.append("../");
                sb.append(getRelativePath((Content) parent, attribute2));
                return sb.toString();
            }
            throw new IllegalArgumentException("Cannot create a path from a detached attrbibute");
        }
    }

    public static String getRelativePath(Attribute attribute, Content content) {
        if (attribute == null) {
            throw new NullPointerException("Cannot create a path from a null 'from'");
        } else if (content != null) {
            Element parent = attribute.getParent();
            if (parent == null) {
                throw new IllegalArgumentException("Cannot create a path from a detached attrbibute");
            } else if (parent == content) {
                return "..";
            } else {
                StringBuilder sb = new StringBuilder();
                sb.append("../");
                sb.append(getRelativePath((Content) parent, content));
                return sb.toString();
            }
        } else {
            throw new NullPointerException("Cannot create a path to a null target");
        }
    }

    public static String getAbsolutePath(Content content) {
        if (content != null) {
            StringBuilder sb = new StringBuilder();
            Element parentElement = content instanceof Element ? (Element) content : content.getParentElement();
            String str = "/";
            if (parentElement != null) {
                Element element = parentElement;
                while (element.getParentElement() != null) {
                    element = element.getParentElement();
                }
                sb.append(str);
                getSingleStep(element, sb);
                if (element != parentElement) {
                    sb.append(str);
                    getRelativeElementPath(element, parentElement, sb);
                }
                if (parentElement != content) {
                    sb.append(str);
                    getSingleStep(content, sb);
                }
                return sb.toString();
            } else if (content.getParent() != null) {
                sb.append(str);
                getSingleStep(content, sb);
                return sb.toString();
            } else {
                throw new IllegalArgumentException("Cannot create a path to detached target");
            }
        } else {
            throw new NullPointerException("Cannot create a path to a null target");
        }
    }

    public static String getAbsolutePath(Attribute attribute) {
        if (attribute != null) {
            Element parent = attribute.getParent();
            if (parent != null) {
                Element element = parent;
                while (element.getParentElement() != null) {
                    element = element.getParentElement();
                }
                StringBuilder sb = new StringBuilder();
                String str = "/";
                sb.append(str);
                getSingleStep(element, sb);
                if (parent != element) {
                    sb.append(str);
                    getRelativeElementPath(element, parent, sb);
                }
                sb.append(str);
                getSingleStep(attribute, sb);
                return sb.toString();
            }
            throw new IllegalArgumentException("Cannot create a path to detached target");
        }
        throw new NullPointerException("Cannot create a path to a null target");
    }
}
