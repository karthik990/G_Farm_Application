package p043io.opencensus.tags;

import p043io.opencensus.common.Scope;

/* renamed from: io.opencensus.tags.Tagger */
public abstract class Tagger {
    public abstract TagContextBuilder currentBuilder();

    public abstract TagContext empty();

    public abstract TagContextBuilder emptyBuilder();

    public abstract TagContext getCurrentTagContext();

    public abstract TagContextBuilder toBuilder(TagContext tagContext);

    public abstract Scope withTagContext(TagContext tagContext);
}
