package p043io.opencensus.trace;

import com.anjlab.android.iab.p020v3.Constants;
import com.google.firebase.analytics.FirebaseAnalytics.Param;
import java.util.Map;
import p043io.opencensus.internal.C5887Utils;

/* renamed from: io.opencensus.trace.BlankSpan */
public final class BlankSpan extends Span {
    public static final BlankSpan INSTANCE = new BlankSpan();

    @Deprecated
    public void addNetworkEvent(NetworkEvent networkEvent) {
    }

    public String toString() {
        return "BlankSpan";
    }

    private BlankSpan() {
        super(SpanContext.INVALID, null);
    }

    public void putAttribute(String str, AttributeValue attributeValue) {
        C5887Utils.checkNotNull(str, "key");
        C5887Utils.checkNotNull(attributeValue, Param.VALUE);
    }

    public void putAttributes(Map<String, AttributeValue> map) {
        C5887Utils.checkNotNull(map, "attributes");
    }

    public void addAnnotation(String str, Map<String, AttributeValue> map) {
        C5887Utils.checkNotNull(str, Constants.RESPONSE_DESCRIPTION);
        C5887Utils.checkNotNull(map, "attributes");
    }

    public void addAnnotation(Annotation annotation) {
        C5887Utils.checkNotNull(annotation, "annotation");
    }

    public void addMessageEvent(MessageEvent messageEvent) {
        C5887Utils.checkNotNull(messageEvent, "messageEvent");
    }

    public void addLink(Link link) {
        C5887Utils.checkNotNull(link, "link");
    }

    public void setStatus(Status status) {
        C5887Utils.checkNotNull(status, "status");
    }

    public void end(EndSpanOptions endSpanOptions) {
        C5887Utils.checkNotNull(endSpanOptions, "options");
    }
}
