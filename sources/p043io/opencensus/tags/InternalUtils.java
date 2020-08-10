package p043io.opencensus.tags;

import java.util.Iterator;

/* renamed from: io.opencensus.tags.InternalUtils */
public final class InternalUtils {
    private InternalUtils() {
    }

    public static Iterator<Tag> getTags(TagContext tagContext) {
        return tagContext.getIterator();
    }
}
