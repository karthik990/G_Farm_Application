package p043io.opencensus.tags;

import p043io.opencensus.tags.propagation.TagPropagationComponent;

/* renamed from: io.opencensus.tags.TagsComponent */
public abstract class TagsComponent {
    public abstract TaggingState getState();

    public abstract TagPropagationComponent getTagPropagationComponent();

    public abstract Tagger getTagger();

    @Deprecated
    public abstract void setState(TaggingState taggingState);
}
