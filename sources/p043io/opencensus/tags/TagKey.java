package p043io.opencensus.tags;

import p043io.opencensus.internal.C5887Utils;
import p043io.opencensus.internal.StringUtils;

/* renamed from: io.opencensus.tags.TagKey */
public abstract class TagKey {
    public static final int MAX_LENGTH = 255;

    public abstract String getName();

    TagKey() {
    }

    public static TagKey create(String str) {
        C5887Utils.checkArgument(isValid(str), "Invalid TagKey name: %s", str);
        return new AutoValue_TagKey(str);
    }

    private static boolean isValid(String str) {
        return !str.isEmpty() && str.length() <= 255 && StringUtils.isPrintableString(str);
    }
}
