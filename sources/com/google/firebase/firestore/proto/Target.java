package com.google.firebase.firestore.proto;

import com.google.firestore.p050v1.Target.DocumentsTarget;
import com.google.firestore.p050v1.Target.QueryTarget;
import com.google.protobuf.ByteString;
import com.google.protobuf.CodedInputStream;
import com.google.protobuf.CodedOutputStream;
import com.google.protobuf.ExtensionRegistryLite;
import com.google.protobuf.GeneratedMessageLite;
import com.google.protobuf.GeneratedMessageLite.MethodToInvoke;
import com.google.protobuf.Internal.EnumLite;
import com.google.protobuf.InvalidProtocolBufferException;
import com.google.protobuf.Parser;
import com.google.protobuf.Timestamp;
import java.io.IOException;
import java.io.InputStream;

/* compiled from: com.google.firebase:firebase-firestore@@19.0.0 */
public final class Target extends GeneratedMessageLite<Target, Builder> implements TargetOrBuilder {
    /* access modifiers changed from: private */
    public static final Target DEFAULT_INSTANCE = new Target();
    public static final int DOCUMENTS_FIELD_NUMBER = 6;
    public static final int LAST_LISTEN_SEQUENCE_NUMBER_FIELD_NUMBER = 4;
    private static volatile Parser<Target> PARSER = null;
    public static final int QUERY_FIELD_NUMBER = 5;
    public static final int RESUME_TOKEN_FIELD_NUMBER = 3;
    public static final int SNAPSHOT_VERSION_FIELD_NUMBER = 2;
    public static final int TARGET_ID_FIELD_NUMBER = 1;
    private long lastListenSequenceNumber_;
    private ByteString resumeToken_ = ByteString.EMPTY;
    private Timestamp snapshotVersion_;
    private int targetId_;
    private int targetTypeCase_ = 0;
    private Object targetType_;

    /* renamed from: com.google.firebase.firestore.proto.Target$1 */
    /* compiled from: com.google.firebase:firebase-firestore@@19.0.0 */
    static /* synthetic */ class C36631 {

        /* renamed from: $SwitchMap$com$google$firebase$firestore$proto$Target$TargetTypeCase */
        static final /* synthetic */ int[] f2041x5167ea64 = new int[TargetTypeCase.values().length];

        /* JADX WARNING: Can't wrap try/catch for region: R(24:0|(2:1|2)|3|(2:5|6)|7|9|10|11|12|13|14|15|16|17|18|(2:19|20)|21|23|24|25|26|27|28|30) */
        /* JADX WARNING: Can't wrap try/catch for region: R(25:0|1|2|3|(2:5|6)|7|9|10|11|12|13|14|15|16|17|18|(2:19|20)|21|23|24|25|26|27|28|30) */
        /* JADX WARNING: Can't wrap try/catch for region: R(26:0|1|2|3|(2:5|6)|7|9|10|11|12|13|14|15|16|17|18|19|20|21|23|24|25|26|27|28|30) */
        /* JADX WARNING: Can't wrap try/catch for region: R(27:0|1|2|3|5|6|7|9|10|11|12|13|14|15|16|17|18|19|20|21|23|24|25|26|27|28|30) */
        /* JADX WARNING: Failed to process nested try/catch */
        /* JADX WARNING: Missing exception handler attribute for start block: B:11:0x002a */
        /* JADX WARNING: Missing exception handler attribute for start block: B:13:0x0035 */
        /* JADX WARNING: Missing exception handler attribute for start block: B:15:0x0040 */
        /* JADX WARNING: Missing exception handler attribute for start block: B:17:0x004b */
        /* JADX WARNING: Missing exception handler attribute for start block: B:19:0x0056 */
        /* JADX WARNING: Missing exception handler attribute for start block: B:25:0x0075 */
        /* JADX WARNING: Missing exception handler attribute for start block: B:27:0x007f */
        static {
            /*
                com.google.protobuf.GeneratedMessageLite$MethodToInvoke[] r0 = com.google.protobuf.GeneratedMessageLite.MethodToInvoke.values()
                int r0 = r0.length
                int[] r0 = new int[r0]
                f2042xa1df5c61 = r0
                r0 = 1
                int[] r1 = f2042xa1df5c61     // Catch:{ NoSuchFieldError -> 0x0014 }
                com.google.protobuf.GeneratedMessageLite$MethodToInvoke r2 = com.google.protobuf.GeneratedMessageLite.MethodToInvoke.NEW_MUTABLE_INSTANCE     // Catch:{ NoSuchFieldError -> 0x0014 }
                int r2 = r2.ordinal()     // Catch:{ NoSuchFieldError -> 0x0014 }
                r1[r2] = r0     // Catch:{ NoSuchFieldError -> 0x0014 }
            L_0x0014:
                r1 = 2
                int[] r2 = f2042xa1df5c61     // Catch:{ NoSuchFieldError -> 0x001f }
                com.google.protobuf.GeneratedMessageLite$MethodToInvoke r3 = com.google.protobuf.GeneratedMessageLite.MethodToInvoke.IS_INITIALIZED     // Catch:{ NoSuchFieldError -> 0x001f }
                int r3 = r3.ordinal()     // Catch:{ NoSuchFieldError -> 0x001f }
                r2[r3] = r1     // Catch:{ NoSuchFieldError -> 0x001f }
            L_0x001f:
                r2 = 3
                int[] r3 = f2042xa1df5c61     // Catch:{ NoSuchFieldError -> 0x002a }
                com.google.protobuf.GeneratedMessageLite$MethodToInvoke r4 = com.google.protobuf.GeneratedMessageLite.MethodToInvoke.MAKE_IMMUTABLE     // Catch:{ NoSuchFieldError -> 0x002a }
                int r4 = r4.ordinal()     // Catch:{ NoSuchFieldError -> 0x002a }
                r3[r4] = r2     // Catch:{ NoSuchFieldError -> 0x002a }
            L_0x002a:
                int[] r3 = f2042xa1df5c61     // Catch:{ NoSuchFieldError -> 0x0035 }
                com.google.protobuf.GeneratedMessageLite$MethodToInvoke r4 = com.google.protobuf.GeneratedMessageLite.MethodToInvoke.NEW_BUILDER     // Catch:{ NoSuchFieldError -> 0x0035 }
                int r4 = r4.ordinal()     // Catch:{ NoSuchFieldError -> 0x0035 }
                r5 = 4
                r3[r4] = r5     // Catch:{ NoSuchFieldError -> 0x0035 }
            L_0x0035:
                int[] r3 = f2042xa1df5c61     // Catch:{ NoSuchFieldError -> 0x0040 }
                com.google.protobuf.GeneratedMessageLite$MethodToInvoke r4 = com.google.protobuf.GeneratedMessageLite.MethodToInvoke.VISIT     // Catch:{ NoSuchFieldError -> 0x0040 }
                int r4 = r4.ordinal()     // Catch:{ NoSuchFieldError -> 0x0040 }
                r5 = 5
                r3[r4] = r5     // Catch:{ NoSuchFieldError -> 0x0040 }
            L_0x0040:
                int[] r3 = f2042xa1df5c61     // Catch:{ NoSuchFieldError -> 0x004b }
                com.google.protobuf.GeneratedMessageLite$MethodToInvoke r4 = com.google.protobuf.GeneratedMessageLite.MethodToInvoke.MERGE_FROM_STREAM     // Catch:{ NoSuchFieldError -> 0x004b }
                int r4 = r4.ordinal()     // Catch:{ NoSuchFieldError -> 0x004b }
                r5 = 6
                r3[r4] = r5     // Catch:{ NoSuchFieldError -> 0x004b }
            L_0x004b:
                int[] r3 = f2042xa1df5c61     // Catch:{ NoSuchFieldError -> 0x0056 }
                com.google.protobuf.GeneratedMessageLite$MethodToInvoke r4 = com.google.protobuf.GeneratedMessageLite.MethodToInvoke.GET_DEFAULT_INSTANCE     // Catch:{ NoSuchFieldError -> 0x0056 }
                int r4 = r4.ordinal()     // Catch:{ NoSuchFieldError -> 0x0056 }
                r5 = 7
                r3[r4] = r5     // Catch:{ NoSuchFieldError -> 0x0056 }
            L_0x0056:
                int[] r3 = f2042xa1df5c61     // Catch:{ NoSuchFieldError -> 0x0062 }
                com.google.protobuf.GeneratedMessageLite$MethodToInvoke r4 = com.google.protobuf.GeneratedMessageLite.MethodToInvoke.GET_PARSER     // Catch:{ NoSuchFieldError -> 0x0062 }
                int r4 = r4.ordinal()     // Catch:{ NoSuchFieldError -> 0x0062 }
                r5 = 8
                r3[r4] = r5     // Catch:{ NoSuchFieldError -> 0x0062 }
            L_0x0062:
                com.google.firebase.firestore.proto.Target$TargetTypeCase[] r3 = com.google.firebase.firestore.proto.Target.TargetTypeCase.values()
                int r3 = r3.length
                int[] r3 = new int[r3]
                f2041x5167ea64 = r3
                int[] r3 = f2041x5167ea64     // Catch:{ NoSuchFieldError -> 0x0075 }
                com.google.firebase.firestore.proto.Target$TargetTypeCase r4 = com.google.firebase.firestore.proto.Target.TargetTypeCase.QUERY     // Catch:{ NoSuchFieldError -> 0x0075 }
                int r4 = r4.ordinal()     // Catch:{ NoSuchFieldError -> 0x0075 }
                r3[r4] = r0     // Catch:{ NoSuchFieldError -> 0x0075 }
            L_0x0075:
                int[] r0 = f2041x5167ea64     // Catch:{ NoSuchFieldError -> 0x007f }
                com.google.firebase.firestore.proto.Target$TargetTypeCase r3 = com.google.firebase.firestore.proto.Target.TargetTypeCase.DOCUMENTS     // Catch:{ NoSuchFieldError -> 0x007f }
                int r3 = r3.ordinal()     // Catch:{ NoSuchFieldError -> 0x007f }
                r0[r3] = r1     // Catch:{ NoSuchFieldError -> 0x007f }
            L_0x007f:
                int[] r0 = f2041x5167ea64     // Catch:{ NoSuchFieldError -> 0x0089 }
                com.google.firebase.firestore.proto.Target$TargetTypeCase r1 = com.google.firebase.firestore.proto.Target.TargetTypeCase.TARGETTYPE_NOT_SET     // Catch:{ NoSuchFieldError -> 0x0089 }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x0089 }
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x0089 }
            L_0x0089:
                return
            */
            throw new UnsupportedOperationException("Method not decompiled: com.google.firebase.firestore.proto.Target.C36631.<clinit>():void");
        }
    }

    /* compiled from: com.google.firebase:firebase-firestore@@19.0.0 */
    public static final class Builder extends com.google.protobuf.GeneratedMessageLite.Builder<Target, Builder> implements TargetOrBuilder {
        /* synthetic */ Builder(C36631 r1) {
            this();
        }

        private Builder() {
            super(Target.DEFAULT_INSTANCE);
        }

        public TargetTypeCase getTargetTypeCase() {
            return ((Target) this.instance).getTargetTypeCase();
        }

        public Builder clearTargetType() {
            copyOnWrite();
            ((Target) this.instance).clearTargetType();
            return this;
        }

        public int getTargetId() {
            return ((Target) this.instance).getTargetId();
        }

        public Builder setTargetId(int i) {
            copyOnWrite();
            ((Target) this.instance).setTargetId(i);
            return this;
        }

        public Builder clearTargetId() {
            copyOnWrite();
            ((Target) this.instance).clearTargetId();
            return this;
        }

        public boolean hasSnapshotVersion() {
            return ((Target) this.instance).hasSnapshotVersion();
        }

        public Timestamp getSnapshotVersion() {
            return ((Target) this.instance).getSnapshotVersion();
        }

        public Builder setSnapshotVersion(Timestamp timestamp) {
            copyOnWrite();
            ((Target) this.instance).setSnapshotVersion(timestamp);
            return this;
        }

        public Builder setSnapshotVersion(com.google.protobuf.Timestamp.Builder builder) {
            copyOnWrite();
            ((Target) this.instance).setSnapshotVersion(builder);
            return this;
        }

        public Builder mergeSnapshotVersion(Timestamp timestamp) {
            copyOnWrite();
            ((Target) this.instance).mergeSnapshotVersion(timestamp);
            return this;
        }

        public Builder clearSnapshotVersion() {
            copyOnWrite();
            ((Target) this.instance).clearSnapshotVersion();
            return this;
        }

        public ByteString getResumeToken() {
            return ((Target) this.instance).getResumeToken();
        }

        public Builder setResumeToken(ByteString byteString) {
            copyOnWrite();
            ((Target) this.instance).setResumeToken(byteString);
            return this;
        }

        public Builder clearResumeToken() {
            copyOnWrite();
            ((Target) this.instance).clearResumeToken();
            return this;
        }

        public long getLastListenSequenceNumber() {
            return ((Target) this.instance).getLastListenSequenceNumber();
        }

        public Builder setLastListenSequenceNumber(long j) {
            copyOnWrite();
            ((Target) this.instance).setLastListenSequenceNumber(j);
            return this;
        }

        public Builder clearLastListenSequenceNumber() {
            copyOnWrite();
            ((Target) this.instance).clearLastListenSequenceNumber();
            return this;
        }

        public QueryTarget getQuery() {
            return ((Target) this.instance).getQuery();
        }

        public Builder setQuery(QueryTarget queryTarget) {
            copyOnWrite();
            ((Target) this.instance).setQuery(queryTarget);
            return this;
        }

        public Builder setQuery(com.google.firestore.p050v1.Target.QueryTarget.Builder builder) {
            copyOnWrite();
            ((Target) this.instance).setQuery(builder);
            return this;
        }

        public Builder mergeQuery(QueryTarget queryTarget) {
            copyOnWrite();
            ((Target) this.instance).mergeQuery(queryTarget);
            return this;
        }

        public Builder clearQuery() {
            copyOnWrite();
            ((Target) this.instance).clearQuery();
            return this;
        }

        public DocumentsTarget getDocuments() {
            return ((Target) this.instance).getDocuments();
        }

        public Builder setDocuments(DocumentsTarget documentsTarget) {
            copyOnWrite();
            ((Target) this.instance).setDocuments(documentsTarget);
            return this;
        }

        public Builder setDocuments(com.google.firestore.p050v1.Target.DocumentsTarget.Builder builder) {
            copyOnWrite();
            ((Target) this.instance).setDocuments(builder);
            return this;
        }

        public Builder mergeDocuments(DocumentsTarget documentsTarget) {
            copyOnWrite();
            ((Target) this.instance).mergeDocuments(documentsTarget);
            return this;
        }

        public Builder clearDocuments() {
            copyOnWrite();
            ((Target) this.instance).clearDocuments();
            return this;
        }
    }

    /* compiled from: com.google.firebase:firebase-firestore@@19.0.0 */
    public enum TargetTypeCase implements EnumLite {
        QUERY(5),
        DOCUMENTS(6),
        TARGETTYPE_NOT_SET(0);
        
        private final int value;

        private TargetTypeCase(int i) {
            this.value = i;
        }

        @Deprecated
        public static TargetTypeCase valueOf(int i) {
            return forNumber(i);
        }

        public static TargetTypeCase forNumber(int i) {
            if (i == 0) {
                return TARGETTYPE_NOT_SET;
            }
            if (i == 5) {
                return QUERY;
            }
            if (i != 6) {
                return null;
            }
            return DOCUMENTS;
        }

        public int getNumber() {
            return this.value;
        }
    }

    private Target() {
    }

    public TargetTypeCase getTargetTypeCase() {
        return TargetTypeCase.forNumber(this.targetTypeCase_);
    }

    /* access modifiers changed from: private */
    public void clearTargetType() {
        this.targetTypeCase_ = 0;
        this.targetType_ = null;
    }

    public int getTargetId() {
        return this.targetId_;
    }

    /* access modifiers changed from: private */
    public void setTargetId(int i) {
        this.targetId_ = i;
    }

    /* access modifiers changed from: private */
    public void clearTargetId() {
        this.targetId_ = 0;
    }

    public boolean hasSnapshotVersion() {
        return this.snapshotVersion_ != null;
    }

    public Timestamp getSnapshotVersion() {
        Timestamp timestamp = this.snapshotVersion_;
        return timestamp == null ? Timestamp.getDefaultInstance() : timestamp;
    }

    /* access modifiers changed from: private */
    public void setSnapshotVersion(Timestamp timestamp) {
        if (timestamp != null) {
            this.snapshotVersion_ = timestamp;
            return;
        }
        throw new NullPointerException();
    }

    /* access modifiers changed from: private */
    public void setSnapshotVersion(com.google.protobuf.Timestamp.Builder builder) {
        this.snapshotVersion_ = (Timestamp) builder.build();
    }

    /* access modifiers changed from: private */
    public void mergeSnapshotVersion(Timestamp timestamp) {
        Timestamp timestamp2 = this.snapshotVersion_;
        if (timestamp2 == null || timestamp2 == Timestamp.getDefaultInstance()) {
            this.snapshotVersion_ = timestamp;
        } else {
            this.snapshotVersion_ = (Timestamp) ((com.google.protobuf.Timestamp.Builder) Timestamp.newBuilder(this.snapshotVersion_).mergeFrom(timestamp)).buildPartial();
        }
    }

    /* access modifiers changed from: private */
    public void clearSnapshotVersion() {
        this.snapshotVersion_ = null;
    }

    public ByteString getResumeToken() {
        return this.resumeToken_;
    }

    /* access modifiers changed from: private */
    public void setResumeToken(ByteString byteString) {
        if (byteString != null) {
            this.resumeToken_ = byteString;
            return;
        }
        throw new NullPointerException();
    }

    /* access modifiers changed from: private */
    public void clearResumeToken() {
        this.resumeToken_ = getDefaultInstance().getResumeToken();
    }

    public long getLastListenSequenceNumber() {
        return this.lastListenSequenceNumber_;
    }

    /* access modifiers changed from: private */
    public void setLastListenSequenceNumber(long j) {
        this.lastListenSequenceNumber_ = j;
    }

    /* access modifiers changed from: private */
    public void clearLastListenSequenceNumber() {
        this.lastListenSequenceNumber_ = 0;
    }

    public QueryTarget getQuery() {
        if (this.targetTypeCase_ == 5) {
            return (QueryTarget) this.targetType_;
        }
        return QueryTarget.getDefaultInstance();
    }

    /* access modifiers changed from: private */
    public void setQuery(QueryTarget queryTarget) {
        if (queryTarget != null) {
            this.targetType_ = queryTarget;
            this.targetTypeCase_ = 5;
            return;
        }
        throw new NullPointerException();
    }

    /* access modifiers changed from: private */
    public void setQuery(com.google.firestore.p050v1.Target.QueryTarget.Builder builder) {
        this.targetType_ = builder.build();
        this.targetTypeCase_ = 5;
    }

    /* access modifiers changed from: private */
    public void mergeQuery(QueryTarget queryTarget) {
        if (this.targetTypeCase_ != 5 || this.targetType_ == QueryTarget.getDefaultInstance()) {
            this.targetType_ = queryTarget;
        } else {
            this.targetType_ = ((com.google.firestore.p050v1.Target.QueryTarget.Builder) QueryTarget.newBuilder((QueryTarget) this.targetType_).mergeFrom(queryTarget)).buildPartial();
        }
        this.targetTypeCase_ = 5;
    }

    /* access modifiers changed from: private */
    public void clearQuery() {
        if (this.targetTypeCase_ == 5) {
            this.targetTypeCase_ = 0;
            this.targetType_ = null;
        }
    }

    public DocumentsTarget getDocuments() {
        if (this.targetTypeCase_ == 6) {
            return (DocumentsTarget) this.targetType_;
        }
        return DocumentsTarget.getDefaultInstance();
    }

    /* access modifiers changed from: private */
    public void setDocuments(DocumentsTarget documentsTarget) {
        if (documentsTarget != null) {
            this.targetType_ = documentsTarget;
            this.targetTypeCase_ = 6;
            return;
        }
        throw new NullPointerException();
    }

    /* access modifiers changed from: private */
    public void setDocuments(com.google.firestore.p050v1.Target.DocumentsTarget.Builder builder) {
        this.targetType_ = builder.build();
        this.targetTypeCase_ = 6;
    }

    /* access modifiers changed from: private */
    public void mergeDocuments(DocumentsTarget documentsTarget) {
        if (this.targetTypeCase_ != 6 || this.targetType_ == DocumentsTarget.getDefaultInstance()) {
            this.targetType_ = documentsTarget;
        } else {
            this.targetType_ = ((com.google.firestore.p050v1.Target.DocumentsTarget.Builder) DocumentsTarget.newBuilder((DocumentsTarget) this.targetType_).mergeFrom(documentsTarget)).buildPartial();
        }
        this.targetTypeCase_ = 6;
    }

    /* access modifiers changed from: private */
    public void clearDocuments() {
        if (this.targetTypeCase_ == 6) {
            this.targetTypeCase_ = 0;
            this.targetType_ = null;
        }
    }

    public void writeTo(CodedOutputStream codedOutputStream) throws IOException {
        int i = this.targetId_;
        if (i != 0) {
            codedOutputStream.writeInt32(1, i);
        }
        if (this.snapshotVersion_ != null) {
            codedOutputStream.writeMessage(2, getSnapshotVersion());
        }
        if (!this.resumeToken_.isEmpty()) {
            codedOutputStream.writeBytes(3, this.resumeToken_);
        }
        long j = this.lastListenSequenceNumber_;
        if (j != 0) {
            codedOutputStream.writeInt64(4, j);
        }
        if (this.targetTypeCase_ == 5) {
            codedOutputStream.writeMessage(5, (QueryTarget) this.targetType_);
        }
        if (this.targetTypeCase_ == 6) {
            codedOutputStream.writeMessage(6, (DocumentsTarget) this.targetType_);
        }
    }

    public int getSerializedSize() {
        int i = this.memoizedSerializedSize;
        if (i != -1) {
            return i;
        }
        int i2 = 0;
        int i3 = this.targetId_;
        if (i3 != 0) {
            i2 = 0 + CodedOutputStream.computeInt32Size(1, i3);
        }
        if (this.snapshotVersion_ != null) {
            i2 += CodedOutputStream.computeMessageSize(2, getSnapshotVersion());
        }
        if (!this.resumeToken_.isEmpty()) {
            i2 += CodedOutputStream.computeBytesSize(3, this.resumeToken_);
        }
        long j = this.lastListenSequenceNumber_;
        if (j != 0) {
            i2 += CodedOutputStream.computeInt64Size(4, j);
        }
        if (this.targetTypeCase_ == 5) {
            i2 += CodedOutputStream.computeMessageSize(5, (QueryTarget) this.targetType_);
        }
        if (this.targetTypeCase_ == 6) {
            i2 += CodedOutputStream.computeMessageSize(6, (DocumentsTarget) this.targetType_);
        }
        this.memoizedSerializedSize = i2;
        return i2;
    }

    public static Target parseFrom(ByteString byteString) throws InvalidProtocolBufferException {
        return (Target) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, byteString);
    }

    public static Target parseFrom(ByteString byteString, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
        return (Target) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, byteString, extensionRegistryLite);
    }

    public static Target parseFrom(byte[] bArr) throws InvalidProtocolBufferException {
        return (Target) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, bArr);
    }

    public static Target parseFrom(byte[] bArr, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
        return (Target) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, bArr, extensionRegistryLite);
    }

    public static Target parseFrom(InputStream inputStream) throws IOException {
        return (Target) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, inputStream);
    }

    public static Target parseFrom(InputStream inputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
        return (Target) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, inputStream, extensionRegistryLite);
    }

    public static Target parseDelimitedFrom(InputStream inputStream) throws IOException {
        return (Target) parseDelimitedFrom(DEFAULT_INSTANCE, inputStream);
    }

    public static Target parseDelimitedFrom(InputStream inputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
        return (Target) parseDelimitedFrom(DEFAULT_INSTANCE, inputStream, extensionRegistryLite);
    }

    public static Target parseFrom(CodedInputStream codedInputStream) throws IOException {
        return (Target) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, codedInputStream);
    }

    public static Target parseFrom(CodedInputStream codedInputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
        return (Target) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, codedInputStream, extensionRegistryLite);
    }

    public static Builder newBuilder() {
        return (Builder) DEFAULT_INSTANCE.toBuilder();
    }

    public static Builder newBuilder(Target target) {
        return (Builder) ((Builder) DEFAULT_INSTANCE.toBuilder()).mergeFrom(target);
    }

    /* access modifiers changed from: protected */
    public final Object dynamicMethod(MethodToInvoke methodToInvoke, Object obj, Object obj2) {
        boolean z = false;
        switch (methodToInvoke) {
            case NEW_MUTABLE_INSTANCE:
                return new Target();
            case IS_INITIALIZED:
                return DEFAULT_INSTANCE;
            case MAKE_IMMUTABLE:
                return null;
            case NEW_BUILDER:
                return new Builder(null);
            case VISIT:
                Visitor visitor = (Visitor) obj;
                Target target = (Target) obj2;
                this.targetId_ = visitor.visitInt(this.targetId_ != 0, this.targetId_, target.targetId_ != 0, target.targetId_);
                this.snapshotVersion_ = (Timestamp) visitor.visitMessage(this.snapshotVersion_, target.snapshotVersion_);
                this.resumeToken_ = visitor.visitByteString(this.resumeToken_ != ByteString.EMPTY, this.resumeToken_, target.resumeToken_ != ByteString.EMPTY, target.resumeToken_);
                this.lastListenSequenceNumber_ = visitor.visitLong(this.lastListenSequenceNumber_ != 0, this.lastListenSequenceNumber_, target.lastListenSequenceNumber_ != 0, target.lastListenSequenceNumber_);
                int i = C36631.f2041x5167ea64[target.getTargetTypeCase().ordinal()];
                if (i == 1) {
                    if (this.targetTypeCase_ == 5) {
                        z = true;
                    }
                    this.targetType_ = visitor.visitOneofMessage(z, this.targetType_, target.targetType_);
                } else if (i == 2) {
                    if (this.targetTypeCase_ == 6) {
                        z = true;
                    }
                    this.targetType_ = visitor.visitOneofMessage(z, this.targetType_, target.targetType_);
                } else if (i == 3) {
                    if (this.targetTypeCase_ != 0) {
                        z = true;
                    }
                    visitor.visitOneofNotSet(z);
                }
                if (visitor == MergeFromVisitor.INSTANCE) {
                    int i2 = target.targetTypeCase_;
                    if (i2 != 0) {
                        this.targetTypeCase_ = i2;
                    }
                }
                return this;
            case MERGE_FROM_STREAM:
                CodedInputStream codedInputStream = (CodedInputStream) obj;
                ExtensionRegistryLite extensionRegistryLite = (ExtensionRegistryLite) obj2;
                while (!z) {
                    try {
                        int readTag = codedInputStream.readTag();
                        if (readTag != 0) {
                            if (readTag == 8) {
                                this.targetId_ = codedInputStream.readInt32();
                            } else if (readTag == 18) {
                                com.google.protobuf.Timestamp.Builder builder = this.snapshotVersion_ != null ? (com.google.protobuf.Timestamp.Builder) this.snapshotVersion_.toBuilder() : null;
                                this.snapshotVersion_ = (Timestamp) codedInputStream.readMessage(Timestamp.parser(), extensionRegistryLite);
                                if (builder != null) {
                                    builder.mergeFrom(this.snapshotVersion_);
                                    this.snapshotVersion_ = (Timestamp) builder.buildPartial();
                                }
                            } else if (readTag == 26) {
                                this.resumeToken_ = codedInputStream.readBytes();
                            } else if (readTag == 32) {
                                this.lastListenSequenceNumber_ = codedInputStream.readInt64();
                            } else if (readTag == 42) {
                                com.google.firestore.p050v1.Target.QueryTarget.Builder builder2 = this.targetTypeCase_ == 5 ? (com.google.firestore.p050v1.Target.QueryTarget.Builder) ((QueryTarget) this.targetType_).toBuilder() : null;
                                this.targetType_ = codedInputStream.readMessage(QueryTarget.parser(), extensionRegistryLite);
                                if (builder2 != null) {
                                    builder2.mergeFrom((QueryTarget) this.targetType_);
                                    this.targetType_ = builder2.buildPartial();
                                }
                                this.targetTypeCase_ = 5;
                            } else if (readTag == 50) {
                                com.google.firestore.p050v1.Target.DocumentsTarget.Builder builder3 = this.targetTypeCase_ == 6 ? (com.google.firestore.p050v1.Target.DocumentsTarget.Builder) ((DocumentsTarget) this.targetType_).toBuilder() : null;
                                this.targetType_ = codedInputStream.readMessage(DocumentsTarget.parser(), extensionRegistryLite);
                                if (builder3 != null) {
                                    builder3.mergeFrom((DocumentsTarget) this.targetType_);
                                    this.targetType_ = builder3.buildPartial();
                                }
                                this.targetTypeCase_ = 6;
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
                    synchronized (Target.class) {
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

    public static Target getDefaultInstance() {
        return DEFAULT_INSTANCE;
    }

    public static Parser<Target> parser() {
        return DEFAULT_INSTANCE.getParserForType();
    }
}
