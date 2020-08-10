package org.jdom2.output.support;

import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import org.jdom2.Content;

public class WalkerPRESERVE implements Walker {
    private static final Iterator<Content> EMPTYIT = new Iterator<Content>() {
        public boolean hasNext() {
            return false;
        }

        public Content next() {
            throw new NoSuchElementException("Cannot call next() on an empty iterator.");
        }

        public void remove() {
            throw new UnsupportedOperationException("Cannot remove from an empty iterator.");
        }
    };
    private final boolean alltext;
    private final Iterator<? extends Content> iter;

    public boolean isCDATA() {
        return false;
    }

    public String text() {
        return null;
    }

    public WalkerPRESERVE(List<? extends Content> list) {
        if (list.isEmpty()) {
            this.alltext = true;
            this.iter = EMPTYIT;
            return;
        }
        this.iter = list.iterator();
        this.alltext = false;
    }

    public boolean isAllText() {
        return this.alltext;
    }

    public boolean hasNext() {
        return this.iter.hasNext();
    }

    public Content next() {
        return (Content) this.iter.next();
    }

    public boolean isAllWhitespace() {
        return this.alltext;
    }
}
