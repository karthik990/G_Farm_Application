package com.google.api;

import com.google.protobuf.AbstractMessageLite;
import com.google.protobuf.ByteString;
import com.google.protobuf.CodedInputStream;
import com.google.protobuf.CodedOutputStream;
import com.google.protobuf.ExtensionRegistryLite;
import com.google.protobuf.GeneratedMessageLite;
import com.google.protobuf.GeneratedMessageLite.MethodToInvoke;
import com.google.protobuf.Internal.ProtobufList;
import com.google.protobuf.InvalidProtocolBufferException;
import com.google.protobuf.MessageLite;
import com.google.protobuf.MessageLiteOrBuilder;
import com.google.protobuf.Parser;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collections;
import java.util.List;

/* compiled from: com.google.firebase:protolite-well-known-types@@16.0.1 */
public final class Monitoring extends GeneratedMessageLite<Monitoring, Builder> implements MonitoringOrBuilder {
    public static final int CONSUMER_DESTINATIONS_FIELD_NUMBER = 2;
    /* access modifiers changed from: private */
    public static final Monitoring DEFAULT_INSTANCE = new Monitoring();
    private static volatile Parser<Monitoring> PARSER = null;
    public static final int PRODUCER_DESTINATIONS_FIELD_NUMBER = 1;
    private ProtobufList<MonitoringDestination> consumerDestinations_ = emptyProtobufList();
    private ProtobufList<MonitoringDestination> producerDestinations_ = emptyProtobufList();

    /* compiled from: com.google.firebase:protolite-well-known-types@@16.0.1 */
    public static final class Builder extends com.google.protobuf.GeneratedMessageLite.Builder<Monitoring, Builder> implements MonitoringOrBuilder {
        private Builder() {
            super(Monitoring.DEFAULT_INSTANCE);
        }

        public List<MonitoringDestination> getProducerDestinationsList() {
            return Collections.unmodifiableList(((Monitoring) this.instance).getProducerDestinationsList());
        }

        public int getProducerDestinationsCount() {
            return ((Monitoring) this.instance).getProducerDestinationsCount();
        }

        public MonitoringDestination getProducerDestinations(int i) {
            return ((Monitoring) this.instance).getProducerDestinations(i);
        }

        public Builder setProducerDestinations(int i, MonitoringDestination monitoringDestination) {
            copyOnWrite();
            ((Monitoring) this.instance).setProducerDestinations(i, monitoringDestination);
            return this;
        }

        public Builder setProducerDestinations(int i, Builder builder) {
            copyOnWrite();
            ((Monitoring) this.instance).setProducerDestinations(i, builder);
            return this;
        }

        public Builder addProducerDestinations(MonitoringDestination monitoringDestination) {
            copyOnWrite();
            ((Monitoring) this.instance).addProducerDestinations(monitoringDestination);
            return this;
        }

        public Builder addProducerDestinations(int i, MonitoringDestination monitoringDestination) {
            copyOnWrite();
            ((Monitoring) this.instance).addProducerDestinations(i, monitoringDestination);
            return this;
        }

        public Builder addProducerDestinations(Builder builder) {
            copyOnWrite();
            ((Monitoring) this.instance).addProducerDestinations(builder);
            return this;
        }

        public Builder addProducerDestinations(int i, Builder builder) {
            copyOnWrite();
            ((Monitoring) this.instance).addProducerDestinations(i, builder);
            return this;
        }

        public Builder addAllProducerDestinations(Iterable<? extends MonitoringDestination> iterable) {
            copyOnWrite();
            ((Monitoring) this.instance).addAllProducerDestinations(iterable);
            return this;
        }

        public Builder clearProducerDestinations() {
            copyOnWrite();
            ((Monitoring) this.instance).clearProducerDestinations();
            return this;
        }

        public Builder removeProducerDestinations(int i) {
            copyOnWrite();
            ((Monitoring) this.instance).removeProducerDestinations(i);
            return this;
        }

        public List<MonitoringDestination> getConsumerDestinationsList() {
            return Collections.unmodifiableList(((Monitoring) this.instance).getConsumerDestinationsList());
        }

        public int getConsumerDestinationsCount() {
            return ((Monitoring) this.instance).getConsumerDestinationsCount();
        }

        public MonitoringDestination getConsumerDestinations(int i) {
            return ((Monitoring) this.instance).getConsumerDestinations(i);
        }

        public Builder setConsumerDestinations(int i, MonitoringDestination monitoringDestination) {
            copyOnWrite();
            ((Monitoring) this.instance).setConsumerDestinations(i, monitoringDestination);
            return this;
        }

        public Builder setConsumerDestinations(int i, Builder builder) {
            copyOnWrite();
            ((Monitoring) this.instance).setConsumerDestinations(i, builder);
            return this;
        }

        public Builder addConsumerDestinations(MonitoringDestination monitoringDestination) {
            copyOnWrite();
            ((Monitoring) this.instance).addConsumerDestinations(monitoringDestination);
            return this;
        }

        public Builder addConsumerDestinations(int i, MonitoringDestination monitoringDestination) {
            copyOnWrite();
            ((Monitoring) this.instance).addConsumerDestinations(i, monitoringDestination);
            return this;
        }

        public Builder addConsumerDestinations(Builder builder) {
            copyOnWrite();
            ((Monitoring) this.instance).addConsumerDestinations(builder);
            return this;
        }

        public Builder addConsumerDestinations(int i, Builder builder) {
            copyOnWrite();
            ((Monitoring) this.instance).addConsumerDestinations(i, builder);
            return this;
        }

        public Builder addAllConsumerDestinations(Iterable<? extends MonitoringDestination> iterable) {
            copyOnWrite();
            ((Monitoring) this.instance).addAllConsumerDestinations(iterable);
            return this;
        }

        public Builder clearConsumerDestinations() {
            copyOnWrite();
            ((Monitoring) this.instance).clearConsumerDestinations();
            return this;
        }

        public Builder removeConsumerDestinations(int i) {
            copyOnWrite();
            ((Monitoring) this.instance).removeConsumerDestinations(i);
            return this;
        }
    }

    /* compiled from: com.google.firebase:protolite-well-known-types@@16.0.1 */
    public static final class MonitoringDestination extends GeneratedMessageLite<MonitoringDestination, Builder> implements MonitoringDestinationOrBuilder {
        /* access modifiers changed from: private */
        public static final MonitoringDestination DEFAULT_INSTANCE = new MonitoringDestination();
        public static final int METRICS_FIELD_NUMBER = 2;
        public static final int MONITORED_RESOURCE_FIELD_NUMBER = 1;
        private static volatile Parser<MonitoringDestination> PARSER;
        private int bitField0_;
        private ProtobufList<String> metrics_ = GeneratedMessageLite.emptyProtobufList();
        private String monitoredResource_ = "";

        /* compiled from: com.google.firebase:protolite-well-known-types@@16.0.1 */
        public static final class Builder extends com.google.protobuf.GeneratedMessageLite.Builder<MonitoringDestination, Builder> implements MonitoringDestinationOrBuilder {
            private Builder() {
                super(MonitoringDestination.DEFAULT_INSTANCE);
            }

            public String getMonitoredResource() {
                return ((MonitoringDestination) this.instance).getMonitoredResource();
            }

            public ByteString getMonitoredResourceBytes() {
                return ((MonitoringDestination) this.instance).getMonitoredResourceBytes();
            }

            public Builder setMonitoredResource(String str) {
                copyOnWrite();
                ((MonitoringDestination) this.instance).setMonitoredResource(str);
                return this;
            }

            public Builder clearMonitoredResource() {
                copyOnWrite();
                ((MonitoringDestination) this.instance).clearMonitoredResource();
                return this;
            }

            public Builder setMonitoredResourceBytes(ByteString byteString) {
                copyOnWrite();
                ((MonitoringDestination) this.instance).setMonitoredResourceBytes(byteString);
                return this;
            }

            public List<String> getMetricsList() {
                return Collections.unmodifiableList(((MonitoringDestination) this.instance).getMetricsList());
            }

            public int getMetricsCount() {
                return ((MonitoringDestination) this.instance).getMetricsCount();
            }

            public String getMetrics(int i) {
                return ((MonitoringDestination) this.instance).getMetrics(i);
            }

            public ByteString getMetricsBytes(int i) {
                return ((MonitoringDestination) this.instance).getMetricsBytes(i);
            }

            public Builder setMetrics(int i, String str) {
                copyOnWrite();
                ((MonitoringDestination) this.instance).setMetrics(i, str);
                return this;
            }

            public Builder addMetrics(String str) {
                copyOnWrite();
                ((MonitoringDestination) this.instance).addMetrics(str);
                return this;
            }

            public Builder addAllMetrics(Iterable<String> iterable) {
                copyOnWrite();
                ((MonitoringDestination) this.instance).addAllMetrics(iterable);
                return this;
            }

            public Builder clearMetrics() {
                copyOnWrite();
                ((MonitoringDestination) this.instance).clearMetrics();
                return this;
            }

            public Builder addMetricsBytes(ByteString byteString) {
                copyOnWrite();
                ((MonitoringDestination) this.instance).addMetricsBytes(byteString);
                return this;
            }
        }

        private MonitoringDestination() {
        }

        public String getMonitoredResource() {
            return this.monitoredResource_;
        }

        public ByteString getMonitoredResourceBytes() {
            return ByteString.copyFromUtf8(this.monitoredResource_);
        }

        /* access modifiers changed from: private */
        public void setMonitoredResource(String str) {
            if (str != null) {
                this.monitoredResource_ = str;
                return;
            }
            throw new NullPointerException();
        }

        /* access modifiers changed from: private */
        public void clearMonitoredResource() {
            this.monitoredResource_ = getDefaultInstance().getMonitoredResource();
        }

        /* access modifiers changed from: private */
        public void setMonitoredResourceBytes(ByteString byteString) {
            if (byteString != null) {
                checkByteStringIsUtf8(byteString);
                this.monitoredResource_ = byteString.toStringUtf8();
                return;
            }
            throw new NullPointerException();
        }

        public List<String> getMetricsList() {
            return this.metrics_;
        }

        public int getMetricsCount() {
            return this.metrics_.size();
        }

        public String getMetrics(int i) {
            return (String) this.metrics_.get(i);
        }

        public ByteString getMetricsBytes(int i) {
            return ByteString.copyFromUtf8((String) this.metrics_.get(i));
        }

        private void ensureMetricsIsMutable() {
            if (!this.metrics_.isModifiable()) {
                this.metrics_ = GeneratedMessageLite.mutableCopy(this.metrics_);
            }
        }

        /* access modifiers changed from: private */
        public void setMetrics(int i, String str) {
            if (str != null) {
                ensureMetricsIsMutable();
                this.metrics_.set(i, str);
                return;
            }
            throw new NullPointerException();
        }

        /* access modifiers changed from: private */
        public void addMetrics(String str) {
            if (str != null) {
                ensureMetricsIsMutable();
                this.metrics_.add(str);
                return;
            }
            throw new NullPointerException();
        }

        /* access modifiers changed from: private */
        public void addAllMetrics(Iterable<String> iterable) {
            ensureMetricsIsMutable();
            AbstractMessageLite.addAll(iterable, this.metrics_);
        }

        /* access modifiers changed from: private */
        public void clearMetrics() {
            this.metrics_ = GeneratedMessageLite.emptyProtobufList();
        }

        /* access modifiers changed from: private */
        public void addMetricsBytes(ByteString byteString) {
            if (byteString != null) {
                checkByteStringIsUtf8(byteString);
                ensureMetricsIsMutable();
                this.metrics_.add(byteString.toStringUtf8());
                return;
            }
            throw new NullPointerException();
        }

        public void writeTo(CodedOutputStream codedOutputStream) throws IOException {
            if (!this.monitoredResource_.isEmpty()) {
                codedOutputStream.writeString(1, getMonitoredResource());
            }
            for (int i = 0; i < this.metrics_.size(); i++) {
                codedOutputStream.writeString(2, (String) this.metrics_.get(i));
            }
        }

        public int getSerializedSize() {
            int i = this.memoizedSerializedSize;
            if (i != -1) {
                return i;
            }
            int computeStringSize = !this.monitoredResource_.isEmpty() ? CodedOutputStream.computeStringSize(1, getMonitoredResource()) + 0 : 0;
            int i2 = 0;
            for (int i3 = 0; i3 < this.metrics_.size(); i3++) {
                i2 += CodedOutputStream.computeStringSizeNoTag((String) this.metrics_.get(i3));
            }
            int size = computeStringSize + i2 + (getMetricsList().size() * 1);
            this.memoizedSerializedSize = size;
            return size;
        }

        public static MonitoringDestination parseFrom(ByteString byteString) throws InvalidProtocolBufferException {
            return (MonitoringDestination) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, byteString);
        }

        public static MonitoringDestination parseFrom(ByteString byteString, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
            return (MonitoringDestination) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, byteString, extensionRegistryLite);
        }

        public static MonitoringDestination parseFrom(byte[] bArr) throws InvalidProtocolBufferException {
            return (MonitoringDestination) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, bArr);
        }

        public static MonitoringDestination parseFrom(byte[] bArr, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
            return (MonitoringDestination) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, bArr, extensionRegistryLite);
        }

        public static MonitoringDestination parseFrom(InputStream inputStream) throws IOException {
            return (MonitoringDestination) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, inputStream);
        }

        public static MonitoringDestination parseFrom(InputStream inputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
            return (MonitoringDestination) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, inputStream, extensionRegistryLite);
        }

        public static MonitoringDestination parseDelimitedFrom(InputStream inputStream) throws IOException {
            return (MonitoringDestination) parseDelimitedFrom(DEFAULT_INSTANCE, inputStream);
        }

        public static MonitoringDestination parseDelimitedFrom(InputStream inputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
            return (MonitoringDestination) parseDelimitedFrom(DEFAULT_INSTANCE, inputStream, extensionRegistryLite);
        }

        public static MonitoringDestination parseFrom(CodedInputStream codedInputStream) throws IOException {
            return (MonitoringDestination) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, codedInputStream);
        }

        public static MonitoringDestination parseFrom(CodedInputStream codedInputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
            return (MonitoringDestination) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, codedInputStream, extensionRegistryLite);
        }

        public static Builder newBuilder() {
            return (Builder) DEFAULT_INSTANCE.toBuilder();
        }

        public static Builder newBuilder(MonitoringDestination monitoringDestination) {
            return (Builder) ((Builder) DEFAULT_INSTANCE.toBuilder()).mergeFrom(monitoringDestination);
        }

        /* access modifiers changed from: protected */
        public final Object dynamicMethod(MethodToInvoke methodToInvoke, Object obj, Object obj2) {
            switch (methodToInvoke) {
                case NEW_MUTABLE_INSTANCE:
                    return new MonitoringDestination();
                case IS_INITIALIZED:
                    return DEFAULT_INSTANCE;
                case MAKE_IMMUTABLE:
                    this.metrics_.makeImmutable();
                    return null;
                case NEW_BUILDER:
                    return new Builder();
                case VISIT:
                    Visitor visitor = (Visitor) obj;
                    MonitoringDestination monitoringDestination = (MonitoringDestination) obj2;
                    this.monitoredResource_ = visitor.visitString(!this.monitoredResource_.isEmpty(), this.monitoredResource_, true ^ monitoringDestination.monitoredResource_.isEmpty(), monitoringDestination.monitoredResource_);
                    this.metrics_ = visitor.visitList(this.metrics_, monitoringDestination.metrics_);
                    if (visitor == MergeFromVisitor.INSTANCE) {
                        this.bitField0_ |= monitoringDestination.bitField0_;
                    }
                    return this;
                case MERGE_FROM_STREAM:
                    CodedInputStream codedInputStream = (CodedInputStream) obj;
                    ExtensionRegistryLite extensionRegistryLite = (ExtensionRegistryLite) obj2;
                    boolean z = false;
                    while (!z) {
                        try {
                            int readTag = codedInputStream.readTag();
                            if (readTag != 0) {
                                if (readTag == 10) {
                                    this.monitoredResource_ = codedInputStream.readStringRequireUtf8();
                                } else if (readTag == 18) {
                                    String readStringRequireUtf8 = codedInputStream.readStringRequireUtf8();
                                    if (!this.metrics_.isModifiable()) {
                                        this.metrics_ = GeneratedMessageLite.mutableCopy(this.metrics_);
                                    }
                                    this.metrics_.add(readStringRequireUtf8);
                                } else if (!codedInputStream.skipField(readTag)) {
                                }
                            }
                            z = true;
                        } catch (InvalidProtocolBufferException e) {
                            throw new RuntimeException(e.setUnfinishedMessage(this));
                        } catch (IOException e2) {
                            throw new RuntimeException(new InvalidProtocolBufferException(e2.getMessage()).setUnfinishedMessage(this));
                        }
                    }
                    break;
                case GET_DEFAULT_INSTANCE:
                    break;
                case GET_PARSER:
                    if (PARSER == null) {
                        synchronized (MonitoringDestination.class) {
                            if (PARSER == null) {
                                PARSER = new DefaultInstanceBasedParser(DEFAULT_INSTANCE);
                            }
                        }
                    }
                    return PARSER;
                default:
                    throw new UnsupportedOperationException();
            }
            return DEFAULT_INSTANCE;
        }

        static {
            DEFAULT_INSTANCE.makeImmutable();
        }

        public static MonitoringDestination getDefaultInstance() {
            return DEFAULT_INSTANCE;
        }

        public static Parser<MonitoringDestination> parser() {
            return DEFAULT_INSTANCE.getParserForType();
        }
    }

    /* compiled from: com.google.firebase:protolite-well-known-types@@16.0.1 */
    public interface MonitoringDestinationOrBuilder extends MessageLiteOrBuilder {
        String getMetrics(int i);

        ByteString getMetricsBytes(int i);

        int getMetricsCount();

        List<String> getMetricsList();

        String getMonitoredResource();

        ByteString getMonitoredResourceBytes();
    }

    private Monitoring() {
    }

    public List<MonitoringDestination> getProducerDestinationsList() {
        return this.producerDestinations_;
    }

    public List<? extends MonitoringDestinationOrBuilder> getProducerDestinationsOrBuilderList() {
        return this.producerDestinations_;
    }

    public int getProducerDestinationsCount() {
        return this.producerDestinations_.size();
    }

    public MonitoringDestination getProducerDestinations(int i) {
        return (MonitoringDestination) this.producerDestinations_.get(i);
    }

    public MonitoringDestinationOrBuilder getProducerDestinationsOrBuilder(int i) {
        return (MonitoringDestinationOrBuilder) this.producerDestinations_.get(i);
    }

    private void ensureProducerDestinationsIsMutable() {
        if (!this.producerDestinations_.isModifiable()) {
            this.producerDestinations_ = GeneratedMessageLite.mutableCopy(this.producerDestinations_);
        }
    }

    /* access modifiers changed from: private */
    public void setProducerDestinations(int i, MonitoringDestination monitoringDestination) {
        if (monitoringDestination != null) {
            ensureProducerDestinationsIsMutable();
            this.producerDestinations_.set(i, monitoringDestination);
            return;
        }
        throw new NullPointerException();
    }

    /* access modifiers changed from: private */
    public void setProducerDestinations(int i, Builder builder) {
        ensureProducerDestinationsIsMutable();
        this.producerDestinations_.set(i, (MonitoringDestination) builder.build());
    }

    /* access modifiers changed from: private */
    public void addProducerDestinations(MonitoringDestination monitoringDestination) {
        if (monitoringDestination != null) {
            ensureProducerDestinationsIsMutable();
            this.producerDestinations_.add(monitoringDestination);
            return;
        }
        throw new NullPointerException();
    }

    /* access modifiers changed from: private */
    public void addProducerDestinations(int i, MonitoringDestination monitoringDestination) {
        if (monitoringDestination != null) {
            ensureProducerDestinationsIsMutable();
            this.producerDestinations_.add(i, monitoringDestination);
            return;
        }
        throw new NullPointerException();
    }

    /* access modifiers changed from: private */
    public void addProducerDestinations(Builder builder) {
        ensureProducerDestinationsIsMutable();
        this.producerDestinations_.add((MonitoringDestination) builder.build());
    }

    /* access modifiers changed from: private */
    public void addProducerDestinations(int i, Builder builder) {
        ensureProducerDestinationsIsMutable();
        this.producerDestinations_.add(i, (MonitoringDestination) builder.build());
    }

    /* access modifiers changed from: private */
    public void addAllProducerDestinations(Iterable<? extends MonitoringDestination> iterable) {
        ensureProducerDestinationsIsMutable();
        AbstractMessageLite.addAll(iterable, this.producerDestinations_);
    }

    /* access modifiers changed from: private */
    public void clearProducerDestinations() {
        this.producerDestinations_ = emptyProtobufList();
    }

    /* access modifiers changed from: private */
    public void removeProducerDestinations(int i) {
        ensureProducerDestinationsIsMutable();
        this.producerDestinations_.remove(i);
    }

    public List<MonitoringDestination> getConsumerDestinationsList() {
        return this.consumerDestinations_;
    }

    public List<? extends MonitoringDestinationOrBuilder> getConsumerDestinationsOrBuilderList() {
        return this.consumerDestinations_;
    }

    public int getConsumerDestinationsCount() {
        return this.consumerDestinations_.size();
    }

    public MonitoringDestination getConsumerDestinations(int i) {
        return (MonitoringDestination) this.consumerDestinations_.get(i);
    }

    public MonitoringDestinationOrBuilder getConsumerDestinationsOrBuilder(int i) {
        return (MonitoringDestinationOrBuilder) this.consumerDestinations_.get(i);
    }

    private void ensureConsumerDestinationsIsMutable() {
        if (!this.consumerDestinations_.isModifiable()) {
            this.consumerDestinations_ = GeneratedMessageLite.mutableCopy(this.consumerDestinations_);
        }
    }

    /* access modifiers changed from: private */
    public void setConsumerDestinations(int i, MonitoringDestination monitoringDestination) {
        if (monitoringDestination != null) {
            ensureConsumerDestinationsIsMutable();
            this.consumerDestinations_.set(i, monitoringDestination);
            return;
        }
        throw new NullPointerException();
    }

    /* access modifiers changed from: private */
    public void setConsumerDestinations(int i, Builder builder) {
        ensureConsumerDestinationsIsMutable();
        this.consumerDestinations_.set(i, (MonitoringDestination) builder.build());
    }

    /* access modifiers changed from: private */
    public void addConsumerDestinations(MonitoringDestination monitoringDestination) {
        if (monitoringDestination != null) {
            ensureConsumerDestinationsIsMutable();
            this.consumerDestinations_.add(monitoringDestination);
            return;
        }
        throw new NullPointerException();
    }

    /* access modifiers changed from: private */
    public void addConsumerDestinations(int i, MonitoringDestination monitoringDestination) {
        if (monitoringDestination != null) {
            ensureConsumerDestinationsIsMutable();
            this.consumerDestinations_.add(i, monitoringDestination);
            return;
        }
        throw new NullPointerException();
    }

    /* access modifiers changed from: private */
    public void addConsumerDestinations(Builder builder) {
        ensureConsumerDestinationsIsMutable();
        this.consumerDestinations_.add((MonitoringDestination) builder.build());
    }

    /* access modifiers changed from: private */
    public void addConsumerDestinations(int i, Builder builder) {
        ensureConsumerDestinationsIsMutable();
        this.consumerDestinations_.add(i, (MonitoringDestination) builder.build());
    }

    /* access modifiers changed from: private */
    public void addAllConsumerDestinations(Iterable<? extends MonitoringDestination> iterable) {
        ensureConsumerDestinationsIsMutable();
        AbstractMessageLite.addAll(iterable, this.consumerDestinations_);
    }

    /* access modifiers changed from: private */
    public void clearConsumerDestinations() {
        this.consumerDestinations_ = emptyProtobufList();
    }

    /* access modifiers changed from: private */
    public void removeConsumerDestinations(int i) {
        ensureConsumerDestinationsIsMutable();
        this.consumerDestinations_.remove(i);
    }

    public void writeTo(CodedOutputStream codedOutputStream) throws IOException {
        for (int i = 0; i < this.producerDestinations_.size(); i++) {
            codedOutputStream.writeMessage(1, (MessageLite) this.producerDestinations_.get(i));
        }
        for (int i2 = 0; i2 < this.consumerDestinations_.size(); i2++) {
            codedOutputStream.writeMessage(2, (MessageLite) this.consumerDestinations_.get(i2));
        }
    }

    public int getSerializedSize() {
        int i = this.memoizedSerializedSize;
        if (i != -1) {
            return i;
        }
        int i2 = 0;
        for (int i3 = 0; i3 < this.producerDestinations_.size(); i3++) {
            i2 += CodedOutputStream.computeMessageSize(1, (MessageLite) this.producerDestinations_.get(i3));
        }
        for (int i4 = 0; i4 < this.consumerDestinations_.size(); i4++) {
            i2 += CodedOutputStream.computeMessageSize(2, (MessageLite) this.consumerDestinations_.get(i4));
        }
        this.memoizedSerializedSize = i2;
        return i2;
    }

    public static Monitoring parseFrom(ByteString byteString) throws InvalidProtocolBufferException {
        return (Monitoring) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, byteString);
    }

    public static Monitoring parseFrom(ByteString byteString, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
        return (Monitoring) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, byteString, extensionRegistryLite);
    }

    public static Monitoring parseFrom(byte[] bArr) throws InvalidProtocolBufferException {
        return (Monitoring) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, bArr);
    }

    public static Monitoring parseFrom(byte[] bArr, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
        return (Monitoring) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, bArr, extensionRegistryLite);
    }

    public static Monitoring parseFrom(InputStream inputStream) throws IOException {
        return (Monitoring) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, inputStream);
    }

    public static Monitoring parseFrom(InputStream inputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
        return (Monitoring) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, inputStream, extensionRegistryLite);
    }

    public static Monitoring parseDelimitedFrom(InputStream inputStream) throws IOException {
        return (Monitoring) parseDelimitedFrom(DEFAULT_INSTANCE, inputStream);
    }

    public static Monitoring parseDelimitedFrom(InputStream inputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
        return (Monitoring) parseDelimitedFrom(DEFAULT_INSTANCE, inputStream, extensionRegistryLite);
    }

    public static Monitoring parseFrom(CodedInputStream codedInputStream) throws IOException {
        return (Monitoring) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, codedInputStream);
    }

    public static Monitoring parseFrom(CodedInputStream codedInputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
        return (Monitoring) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, codedInputStream, extensionRegistryLite);
    }

    public static Builder newBuilder() {
        return (Builder) DEFAULT_INSTANCE.toBuilder();
    }

    public static Builder newBuilder(Monitoring monitoring) {
        return (Builder) ((Builder) DEFAULT_INSTANCE.toBuilder()).mergeFrom(monitoring);
    }

    /* access modifiers changed from: protected */
    public final Object dynamicMethod(MethodToInvoke methodToInvoke, Object obj, Object obj2) {
        switch (methodToInvoke) {
            case NEW_MUTABLE_INSTANCE:
                return new Monitoring();
            case IS_INITIALIZED:
                return DEFAULT_INSTANCE;
            case MAKE_IMMUTABLE:
                this.producerDestinations_.makeImmutable();
                this.consumerDestinations_.makeImmutable();
                return null;
            case NEW_BUILDER:
                return new Builder();
            case VISIT:
                Visitor visitor = (Visitor) obj;
                Monitoring monitoring = (Monitoring) obj2;
                this.producerDestinations_ = visitor.visitList(this.producerDestinations_, monitoring.producerDestinations_);
                this.consumerDestinations_ = visitor.visitList(this.consumerDestinations_, monitoring.consumerDestinations_);
                MergeFromVisitor mergeFromVisitor = MergeFromVisitor.INSTANCE;
                return this;
            case MERGE_FROM_STREAM:
                CodedInputStream codedInputStream = (CodedInputStream) obj;
                ExtensionRegistryLite extensionRegistryLite = (ExtensionRegistryLite) obj2;
                boolean z = false;
                while (!z) {
                    try {
                        int readTag = codedInputStream.readTag();
                        if (readTag != 0) {
                            if (readTag == 10) {
                                if (!this.producerDestinations_.isModifiable()) {
                                    this.producerDestinations_ = GeneratedMessageLite.mutableCopy(this.producerDestinations_);
                                }
                                this.producerDestinations_.add((MonitoringDestination) codedInputStream.readMessage(MonitoringDestination.parser(), extensionRegistryLite));
                            } else if (readTag == 18) {
                                if (!this.consumerDestinations_.isModifiable()) {
                                    this.consumerDestinations_ = GeneratedMessageLite.mutableCopy(this.consumerDestinations_);
                                }
                                this.consumerDestinations_.add((MonitoringDestination) codedInputStream.readMessage(MonitoringDestination.parser(), extensionRegistryLite));
                            } else if (!codedInputStream.skipField(readTag)) {
                            }
                        }
                        z = true;
                    } catch (InvalidProtocolBufferException e) {
                        throw new RuntimeException(e.setUnfinishedMessage(this));
                    } catch (IOException e2) {
                        throw new RuntimeException(new InvalidProtocolBufferException(e2.getMessage()).setUnfinishedMessage(this));
                    }
                }
                break;
            case GET_DEFAULT_INSTANCE:
                break;
            case GET_PARSER:
                if (PARSER == null) {
                    synchronized (Monitoring.class) {
                        if (PARSER == null) {
                            PARSER = new DefaultInstanceBasedParser(DEFAULT_INSTANCE);
                        }
                    }
                }
                return PARSER;
            default:
                throw new UnsupportedOperationException();
        }
        return DEFAULT_INSTANCE;
    }

    static {
        DEFAULT_INSTANCE.makeImmutable();
    }

    public static Monitoring getDefaultInstance() {
        return DEFAULT_INSTANCE;
    }

    public static Parser<Monitoring> parser() {
        return DEFAULT_INSTANCE.getParserForType();
    }
}
