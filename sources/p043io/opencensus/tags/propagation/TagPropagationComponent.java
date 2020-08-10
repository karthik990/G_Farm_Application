package p043io.opencensus.tags.propagation;

/* renamed from: io.opencensus.tags.propagation.TagPropagationComponent */
public abstract class TagPropagationComponent {
    public abstract TagContextBinarySerializer getBinarySerializer();

    public abstract TagContextTextFormat getCorrelationContextFormat();
}
