package p043io.opencensus.trace;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import p043io.opencensus.internal.C5887Utils;

/* renamed from: io.opencensus.trace.Annotation */
public abstract class Annotation {
    private static final Map<String, AttributeValue> EMPTY_ATTRIBUTES = Collections.unmodifiableMap(Collections.emptyMap());

    public abstract Map<String, AttributeValue> getAttributes();

    public abstract String getDescription();

    public static Annotation fromDescription(String str) {
        return new AutoValue_Annotation(str, EMPTY_ATTRIBUTES);
    }

    public static Annotation fromDescriptionAndAttributes(String str, Map<String, AttributeValue> map) {
        return new AutoValue_Annotation(str, Collections.unmodifiableMap(new HashMap((Map) C5887Utils.checkNotNull(map, "attributes"))));
    }

    Annotation() {
    }
}
