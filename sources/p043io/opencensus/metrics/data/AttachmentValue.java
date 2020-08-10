package p043io.opencensus.metrics.data;

/* renamed from: io.opencensus.metrics.data.AttachmentValue */
public abstract class AttachmentValue {

    /* renamed from: io.opencensus.metrics.data.AttachmentValue$AttachmentValueString */
    public static abstract class AttachmentValueString extends AttachmentValue {
        AttachmentValueString() {
        }

        public static AttachmentValueString create(String str) {
            return new AutoValue_AttachmentValue_AttachmentValueString(str);
        }
    }

    public abstract String getValue();
}
