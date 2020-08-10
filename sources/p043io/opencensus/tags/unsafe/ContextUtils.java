package p043io.opencensus.tags.unsafe;

import java.util.Collections;
import java.util.Iterator;
import javax.annotation.Nullable;
import p043io.grpc.Context;
import p043io.grpc.Context.Key;
import p043io.opencensus.internal.C5887Utils;
import p043io.opencensus.tags.Tag;
import p043io.opencensus.tags.TagContext;

/* renamed from: io.opencensus.tags.unsafe.ContextUtils */
public final class ContextUtils {
    private static final TagContext EMPTY_TAG_CONTEXT = new EmptyTagContext();
    @Deprecated
    public static final Key<TagContext> TAG_CONTEXT_KEY = Context.keyWithDefault("opencensus-tag-context-key", EMPTY_TAG_CONTEXT);

    /* renamed from: io.opencensus.tags.unsafe.ContextUtils$EmptyTagContext */
    private static final class EmptyTagContext extends TagContext {
        private EmptyTagContext() {
        }

        /* access modifiers changed from: protected */
        public Iterator<Tag> getIterator() {
            return Collections.emptySet().iterator();
        }
    }

    private ContextUtils() {
    }

    public static Context withValue(Context context, @Nullable TagContext tagContext) {
        return ((Context) C5887Utils.checkNotNull(context, "context")).withValue(TAG_CONTEXT_KEY, tagContext);
    }

    public static TagContext getValue(Context context) {
        TagContext tagContext = (TagContext) TAG_CONTEXT_KEY.get(context);
        return tagContext == null ? EMPTY_TAG_CONTEXT : tagContext;
    }
}
