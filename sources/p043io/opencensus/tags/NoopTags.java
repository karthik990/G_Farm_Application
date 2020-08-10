package p043io.opencensus.tags;

import com.braintreepayments.api.models.PostalAddressParser;
import com.google.firebase.analytics.FirebaseAnalytics.Param;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import p043io.opencensus.common.Scope;
import p043io.opencensus.internal.C5887Utils;
import p043io.opencensus.internal.NoopScope;
import p043io.opencensus.tags.propagation.TagContextBinarySerializer;
import p043io.opencensus.tags.propagation.TagContextDeserializationException;
import p043io.opencensus.tags.propagation.TagContextSerializationException;
import p043io.opencensus.tags.propagation.TagContextTextFormat;
import p043io.opencensus.tags.propagation.TagContextTextFormat.Getter;
import p043io.opencensus.tags.propagation.TagContextTextFormat.Setter;
import p043io.opencensus.tags.propagation.TagPropagationComponent;

/* renamed from: io.opencensus.tags.NoopTags */
final class NoopTags {

    /* renamed from: io.opencensus.tags.NoopTags$NoopTagContext */
    private static final class NoopTagContext extends TagContext {
        static final TagContext INSTANCE = new NoopTagContext();

        private NoopTagContext() {
        }

        /* access modifiers changed from: protected */
        public Iterator<Tag> getIterator() {
            return Collections.emptySet().iterator();
        }
    }

    /* renamed from: io.opencensus.tags.NoopTags$NoopTagContextBinarySerializer */
    private static final class NoopTagContextBinarySerializer extends TagContextBinarySerializer {
        static final byte[] EMPTY_BYTE_ARRAY = new byte[0];
        static final TagContextBinarySerializer INSTANCE = new NoopTagContextBinarySerializer();

        private NoopTagContextBinarySerializer() {
        }

        public byte[] toByteArray(TagContext tagContext) {
            C5887Utils.checkNotNull(tagContext, "tags");
            return EMPTY_BYTE_ARRAY;
        }

        public TagContext fromByteArray(byte[] bArr) {
            C5887Utils.checkNotNull(bArr, "bytes");
            return NoopTags.getNoopTagContext();
        }
    }

    /* renamed from: io.opencensus.tags.NoopTags$NoopTagContextBuilder */
    private static final class NoopTagContextBuilder extends TagContextBuilder {
        static final TagContextBuilder INSTANCE = new NoopTagContextBuilder();

        private NoopTagContextBuilder() {
        }

        public TagContextBuilder put(TagKey tagKey, TagValue tagValue) {
            C5887Utils.checkNotNull(tagKey, "key");
            C5887Utils.checkNotNull(tagValue, Param.VALUE);
            return this;
        }

        public TagContextBuilder put(TagKey tagKey, TagValue tagValue, TagMetadata tagMetadata) {
            C5887Utils.checkNotNull(tagKey, "key");
            C5887Utils.checkNotNull(tagValue, Param.VALUE);
            C5887Utils.checkNotNull(tagMetadata, "tagMetadata");
            return this;
        }

        public TagContextBuilder remove(TagKey tagKey) {
            C5887Utils.checkNotNull(tagKey, "key");
            return this;
        }

        public TagContext build() {
            return NoopTags.getNoopTagContext();
        }

        public Scope buildScoped() {
            return NoopScope.getInstance();
        }
    }

    /* renamed from: io.opencensus.tags.NoopTags$NoopTagContextTextFormat */
    private static final class NoopTagContextTextFormat extends TagContextTextFormat {
        static final NoopTagContextTextFormat INSTANCE = new NoopTagContextTextFormat();

        private NoopTagContextTextFormat() {
        }

        public List<String> fields() {
            return Collections.emptyList();
        }

        public <C> void inject(TagContext tagContext, C c, Setter<C> setter) throws TagContextSerializationException {
            C5887Utils.checkNotNull(tagContext, "tagContext");
            C5887Utils.checkNotNull(c, "carrier");
            C5887Utils.checkNotNull(setter, "setter");
        }

        public <C> TagContext extract(C c, Getter<C> getter) throws TagContextDeserializationException {
            C5887Utils.checkNotNull(c, "carrier");
            C5887Utils.checkNotNull(getter, "getter");
            return NoopTags.getNoopTagContext();
        }
    }

    /* renamed from: io.opencensus.tags.NoopTags$NoopTagPropagationComponent */
    private static final class NoopTagPropagationComponent extends TagPropagationComponent {
        static final TagPropagationComponent INSTANCE = new NoopTagPropagationComponent();

        private NoopTagPropagationComponent() {
        }

        public TagContextBinarySerializer getBinarySerializer() {
            return NoopTags.getNoopTagContextBinarySerializer();
        }

        public TagContextTextFormat getCorrelationContextFormat() {
            return NoopTags.getNoopTagContextTextSerializer();
        }
    }

    /* renamed from: io.opencensus.tags.NoopTags$NoopTagger */
    private static final class NoopTagger extends Tagger {
        static final Tagger INSTANCE = new NoopTagger();

        private NoopTagger() {
        }

        public TagContext empty() {
            return NoopTags.getNoopTagContext();
        }

        public TagContext getCurrentTagContext() {
            return NoopTags.getNoopTagContext();
        }

        public TagContextBuilder emptyBuilder() {
            return NoopTags.getNoopTagContextBuilder();
        }

        public TagContextBuilder toBuilder(TagContext tagContext) {
            C5887Utils.checkNotNull(tagContext, "tags");
            return NoopTags.getNoopTagContextBuilder();
        }

        public TagContextBuilder currentBuilder() {
            return NoopTags.getNoopTagContextBuilder();
        }

        public Scope withTagContext(TagContext tagContext) {
            C5887Utils.checkNotNull(tagContext, "tags");
            return NoopScope.getInstance();
        }
    }

    /* renamed from: io.opencensus.tags.NoopTags$NoopTagsComponent */
    private static final class NoopTagsComponent extends TagsComponent {
        private volatile boolean isRead;

        private NoopTagsComponent() {
        }

        public Tagger getTagger() {
            return NoopTags.getNoopTagger();
        }

        public TagPropagationComponent getTagPropagationComponent() {
            return NoopTags.getNoopTagPropagationComponent();
        }

        public TaggingState getState() {
            this.isRead = true;
            return TaggingState.DISABLED;
        }

        @Deprecated
        public void setState(TaggingState taggingState) {
            C5887Utils.checkNotNull(taggingState, PostalAddressParser.REGION_KEY);
            C5887Utils.checkState(!this.isRead, "State was already read, cannot set state.");
        }
    }

    private NoopTags() {
    }

    static TagsComponent newNoopTagsComponent() {
        return new NoopTagsComponent();
    }

    static Tagger getNoopTagger() {
        return NoopTagger.INSTANCE;
    }

    static TagContextBuilder getNoopTagContextBuilder() {
        return NoopTagContextBuilder.INSTANCE;
    }

    static TagContext getNoopTagContext() {
        return NoopTagContext.INSTANCE;
    }

    static TagPropagationComponent getNoopTagPropagationComponent() {
        return NoopTagPropagationComponent.INSTANCE;
    }

    static TagContextBinarySerializer getNoopTagContextBinarySerializer() {
        return NoopTagContextBinarySerializer.INSTANCE;
    }

    static TagContextTextFormat getNoopTagContextTextSerializer() {
        return NoopTagContextTextFormat.INSTANCE;
    }
}
