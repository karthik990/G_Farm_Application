package org.hamcrest.internal;

import java.util.Iterator;
import org.hamcrest.SelfDescribing;

public class SelfDescribingValueIterator<T> implements Iterator<SelfDescribing> {
    private Iterator<T> values;

    public SelfDescribingValueIterator(Iterator<T> it) {
        this.values = it;
    }

    public boolean hasNext() {
        return this.values.hasNext();
    }

    public SelfDescribing next() {
        return new SelfDescribingValue(this.values.next());
    }

    public void remove() {
        this.values.remove();
    }
}
