package p043io.opencensus.contrib.http;

import com.google.common.base.Preconditions;
import java.util.concurrent.atomic.AtomicLong;
import p043io.opencensus.tags.TagContext;
import p043io.opencensus.tags.TagMetadata;
import p043io.opencensus.tags.TagMetadata.TagTtl;
import p043io.opencensus.trace.Span;

/* renamed from: io.opencensus.contrib.http.HttpRequestContext */
public class HttpRequestContext {
    static final long INVALID_STARTTIME = -1;
    static final TagMetadata METADATA_NO_PROPAGATION = TagMetadata.create(TagTtl.NO_PROPAGATION);
    AtomicLong receiveMessageSize = new AtomicLong();
    AtomicLong receviedSeqId = new AtomicLong();
    final long requestStartTime;
    AtomicLong sentMessageSize = new AtomicLong();
    AtomicLong sentSeqId = new AtomicLong();
    final Span span;
    final TagContext tagContext;

    HttpRequestContext(Span span2, TagContext tagContext2) {
        Preconditions.checkNotNull(span2, TtmlNode.TAG_SPAN);
        Preconditions.checkNotNull(tagContext2, "tagContext");
        this.span = span2;
        this.tagContext = tagContext2;
        this.requestStartTime = System.nanoTime();
    }
}
