package p043io.opencensus.tags;

import p043io.opencensus.internal.C5887Utils;
import p043io.opencensus.internal.StringUtils;

/* renamed from: io.opencensus.tags.TagValue */
public abstract class TagValue {
    public static final int MAX_LENGTH = 255;

    public abstract String asString();

    TagValue() {
    }

    public static TagValue create(String str) {
        C5887Utils.checkArgument(isValid(str), "Invalid TagValue: %s", str);
        return new AutoValue_TagValue(str);
    }

    private static boolean isValid(String str) {
        return str.length() <= 255 && StringUtils.isPrintableString(str);
    }
}
