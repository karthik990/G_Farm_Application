package p043io.opencensus.tags.propagation;

import java.util.List;
import javax.annotation.Nullable;
import p043io.opencensus.tags.TagContext;

/* renamed from: io.opencensus.tags.propagation.TagContextTextFormat */
public abstract class TagContextTextFormat {

    /* renamed from: io.opencensus.tags.propagation.TagContextTextFormat$Getter */
    public static abstract class Getter<C> {
        @Nullable
        public abstract String get(C c, String str);
    }

    /* renamed from: io.opencensus.tags.propagation.TagContextTextFormat$Setter */
    public static abstract class Setter<C> {
        public abstract void put(C c, String str, String str2);
    }

    public abstract <C> TagContext extract(C c, Getter<C> getter) throws TagContextDeserializationException;

    public abstract List<String> fields();

    public abstract <C> void inject(TagContext tagContext, C c, Setter<C> setter) throws TagContextSerializationException;
}
