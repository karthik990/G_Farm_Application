package org.jdom2.filter;

import org.jdom2.CDATA;
import org.jdom2.Comment;
import org.jdom2.Content;
import org.jdom2.DocType;
import org.jdom2.Element;
import org.jdom2.EntityRef;
import org.jdom2.ProcessingInstruction;
import org.jdom2.Text;
import org.objectweb.asm.Opcodes;

public class ContentFilter extends AbstractFilter<Content> {
    public static final int CDATA = 2;
    public static final int COMMENT = 8;
    public static final int DOCTYPE = 128;
    public static final int DOCUMENT = 64;
    public static final int ELEMENT = 1;
    public static final int ENTITYREF = 32;

    /* renamed from: PI */
    public static final int f4552PI = 16;
    public static final int TEXT = 4;
    private static final long serialVersionUID = 200;
    private int filterMask;

    public ContentFilter() {
        setDefaultMask();
    }

    public ContentFilter(boolean z) {
        if (z) {
            setDefaultMask();
            return;
        }
        int i = this.filterMask;
        this.filterMask = i & (i ^ -1);
    }

    public ContentFilter(int i) {
        setFilterMask(i);
    }

    public int getFilterMask() {
        return this.filterMask;
    }

    public void setFilterMask(int i) {
        setDefaultMask();
        this.filterMask = i & this.filterMask;
    }

    public void setDefaultMask() {
        this.filterMask = 255;
    }

    public void setDocumentContent() {
        this.filterMask = Opcodes.IFEQ;
    }

    public void setElementContent() {
        this.filterMask = 63;
    }

    public void setElementVisible(boolean z) {
        if (z) {
            this.filterMask |= 1;
        } else {
            this.filterMask &= -2;
        }
    }

    public void setCDATAVisible(boolean z) {
        if (z) {
            this.filterMask |= 2;
        } else {
            this.filterMask &= -3;
        }
    }

    public void setTextVisible(boolean z) {
        if (z) {
            this.filterMask |= 4;
        } else {
            this.filterMask &= -5;
        }
    }

    public void setCommentVisible(boolean z) {
        if (z) {
            this.filterMask |= 8;
        } else {
            this.filterMask &= -9;
        }
    }

    public void setPIVisible(boolean z) {
        if (z) {
            this.filterMask |= 16;
        } else {
            this.filterMask &= -17;
        }
    }

    public void setEntityRefVisible(boolean z) {
        if (z) {
            this.filterMask |= 32;
        } else {
            this.filterMask &= -33;
        }
    }

    public void setDocTypeVisible(boolean z) {
        if (z) {
            this.filterMask |= 128;
        } else {
            this.filterMask &= -129;
        }
    }

    public Content filter(Object obj) {
        if (obj != null && Content.class.isInstance(obj)) {
            Content content = (Content) obj;
            if (content instanceof Element) {
                if ((this.filterMask & 1) == 0) {
                    content = null;
                }
                return content;
            } else if (content instanceof CDATA) {
                if ((this.filterMask & 2) == 0) {
                    content = null;
                }
                return content;
            } else if (content instanceof Text) {
                if ((this.filterMask & 4) == 0) {
                    content = null;
                }
                return content;
            } else if (content instanceof Comment) {
                if ((this.filterMask & 8) == 0) {
                    content = null;
                }
                return content;
            } else if (content instanceof ProcessingInstruction) {
                if ((this.filterMask & 16) == 0) {
                    content = null;
                }
                return content;
            } else if (content instanceof EntityRef) {
                if ((this.filterMask & 32) == 0) {
                    content = null;
                }
                return content;
            } else if (content instanceof DocType) {
                if ((this.filterMask & 128) == 0) {
                    content = null;
                }
                return content;
            }
        }
        return null;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof ContentFilter)) {
            return false;
        }
        return this.filterMask == ((ContentFilter) obj).filterMask;
    }

    public int hashCode() {
        return this.filterMask;
    }
}
