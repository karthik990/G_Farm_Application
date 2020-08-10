package com.google.firebase.firestore.local;

import com.google.firebase.Timestamp;
import com.google.firebase.firestore.core.Query;
import com.google.firebase.firestore.model.Document;
import com.google.firebase.firestore.model.Document.DocumentState;
import com.google.firebase.firestore.model.DocumentKey;
import com.google.firebase.firestore.model.NoDocument;
import com.google.firebase.firestore.model.SnapshotVersion;
import com.google.firebase.firestore.model.UnknownDocument;
import com.google.firebase.firestore.model.mutation.Mutation;
import com.google.firebase.firestore.model.mutation.MutationBatch;
import com.google.firebase.firestore.model.value.FieldValue;
import com.google.firebase.firestore.model.value.ObjectValue;
import com.google.firebase.firestore.proto.MaybeDocument;
import com.google.firebase.firestore.proto.MaybeDocument.Builder;
import com.google.firebase.firestore.proto.MaybeDocument.DocumentTypeCase;
import com.google.firebase.firestore.proto.Target;
import com.google.firebase.firestore.proto.Target.TargetTypeCase;
import com.google.firebase.firestore.proto.WriteBatch;
import com.google.firebase.firestore.remote.RemoteSerializer;
import com.google.firebase.firestore.util.Assert;
import com.google.protobuf.ByteString;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map.Entry;

/* compiled from: com.google.firebase:firebase-firestore@@19.0.0 */
public final class LocalSerializer {
    private final RemoteSerializer rpcSerializer;

    /* renamed from: com.google.firebase.firestore.local.LocalSerializer$1 */
    /* compiled from: com.google.firebase:firebase-firestore@@19.0.0 */
    static /* synthetic */ class C36521 {

        /* renamed from: $SwitchMap$com$google$firebase$firestore$proto$MaybeDocument$DocumentTypeCase */
        static final /* synthetic */ int[] f2023xe45654f0 = new int[DocumentTypeCase.values().length];

        /* renamed from: $SwitchMap$com$google$firebase$firestore$proto$Target$TargetTypeCase */
        static final /* synthetic */ int[] f2024x5167ea64 = new int[TargetTypeCase.values().length];

        /* JADX WARNING: Can't wrap try/catch for region: R(14:0|1|2|3|5|6|7|9|10|11|12|13|14|16) */
        /* JADX WARNING: Failed to process nested try/catch */
        /* JADX WARNING: Missing exception handler attribute for start block: B:11:0x0032 */
        /* JADX WARNING: Missing exception handler attribute for start block: B:13:0x003c */
        static {
            /*
                com.google.firebase.firestore.proto.Target$TargetTypeCase[] r0 = com.google.firebase.firestore.proto.Target.TargetTypeCase.values()
                int r0 = r0.length
                int[] r0 = new int[r0]
                f2024x5167ea64 = r0
                r0 = 1
                int[] r1 = f2024x5167ea64     // Catch:{ NoSuchFieldError -> 0x0014 }
                com.google.firebase.firestore.proto.Target$TargetTypeCase r2 = com.google.firebase.firestore.proto.Target.TargetTypeCase.DOCUMENTS     // Catch:{ NoSuchFieldError -> 0x0014 }
                int r2 = r2.ordinal()     // Catch:{ NoSuchFieldError -> 0x0014 }
                r1[r2] = r0     // Catch:{ NoSuchFieldError -> 0x0014 }
            L_0x0014:
                r1 = 2
                int[] r2 = f2024x5167ea64     // Catch:{ NoSuchFieldError -> 0x001f }
                com.google.firebase.firestore.proto.Target$TargetTypeCase r3 = com.google.firebase.firestore.proto.Target.TargetTypeCase.QUERY     // Catch:{ NoSuchFieldError -> 0x001f }
                int r3 = r3.ordinal()     // Catch:{ NoSuchFieldError -> 0x001f }
                r2[r3] = r1     // Catch:{ NoSuchFieldError -> 0x001f }
            L_0x001f:
                com.google.firebase.firestore.proto.MaybeDocument$DocumentTypeCase[] r2 = com.google.firebase.firestore.proto.MaybeDocument.DocumentTypeCase.values()
                int r2 = r2.length
                int[] r2 = new int[r2]
                f2023xe45654f0 = r2
                int[] r2 = f2023xe45654f0     // Catch:{ NoSuchFieldError -> 0x0032 }
                com.google.firebase.firestore.proto.MaybeDocument$DocumentTypeCase r3 = com.google.firebase.firestore.proto.MaybeDocument.DocumentTypeCase.DOCUMENT     // Catch:{ NoSuchFieldError -> 0x0032 }
                int r3 = r3.ordinal()     // Catch:{ NoSuchFieldError -> 0x0032 }
                r2[r3] = r0     // Catch:{ NoSuchFieldError -> 0x0032 }
            L_0x0032:
                int[] r0 = f2023xe45654f0     // Catch:{ NoSuchFieldError -> 0x003c }
                com.google.firebase.firestore.proto.MaybeDocument$DocumentTypeCase r2 = com.google.firebase.firestore.proto.MaybeDocument.DocumentTypeCase.NO_DOCUMENT     // Catch:{ NoSuchFieldError -> 0x003c }
                int r2 = r2.ordinal()     // Catch:{ NoSuchFieldError -> 0x003c }
                r0[r2] = r1     // Catch:{ NoSuchFieldError -> 0x003c }
            L_0x003c:
                int[] r0 = f2023xe45654f0     // Catch:{ NoSuchFieldError -> 0x0047 }
                com.google.firebase.firestore.proto.MaybeDocument$DocumentTypeCase r1 = com.google.firebase.firestore.proto.MaybeDocument.DocumentTypeCase.UNKNOWN_DOCUMENT     // Catch:{ NoSuchFieldError -> 0x0047 }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x0047 }
                r2 = 3
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x0047 }
            L_0x0047:
                return
            */
            throw new UnsupportedOperationException("Method not decompiled: com.google.firebase.firestore.local.LocalSerializer.C36521.<clinit>():void");
        }
    }

    public LocalSerializer(RemoteSerializer remoteSerializer) {
        this.rpcSerializer = remoteSerializer;
    }

    /* access modifiers changed from: 0000 */
    public MaybeDocument encodeMaybeDocument(com.google.firebase.firestore.model.MaybeDocument maybeDocument) {
        Builder newBuilder = MaybeDocument.newBuilder();
        if (maybeDocument instanceof NoDocument) {
            NoDocument noDocument = (NoDocument) maybeDocument;
            newBuilder.setNoDocument(encodeNoDocument(noDocument));
            newBuilder.setHasCommittedMutations(noDocument.hasCommittedMutations());
        } else if (maybeDocument instanceof Document) {
            Document document = (Document) maybeDocument;
            if (document.getProto() != null) {
                newBuilder.setDocument(document.getProto());
            } else {
                newBuilder.setDocument(encodeDocument(document));
            }
            newBuilder.setHasCommittedMutations(document.hasCommittedMutations());
        } else if (maybeDocument instanceof UnknownDocument) {
            newBuilder.setUnknownDocument(encodeUnknownDocument((UnknownDocument) maybeDocument));
            newBuilder.setHasCommittedMutations(true);
        } else {
            throw Assert.fail("Unknown document type %s", maybeDocument.getClass().getCanonicalName());
        }
        return (MaybeDocument) newBuilder.build();
    }

    /* access modifiers changed from: 0000 */
    public com.google.firebase.firestore.model.MaybeDocument decodeMaybeDocument(MaybeDocument maybeDocument) {
        int i = C36521.f2023xe45654f0[maybeDocument.getDocumentTypeCase().ordinal()];
        if (i == 1) {
            return decodeDocument(maybeDocument.getDocument(), maybeDocument.getHasCommittedMutations());
        }
        if (i == 2) {
            return decodeNoDocument(maybeDocument.getNoDocument(), maybeDocument.getHasCommittedMutations());
        }
        if (i == 3) {
            return decodeUnknownDocument(maybeDocument.getUnknownDocument());
        }
        throw Assert.fail("Unknown MaybeDocument %s", maybeDocument);
    }

    private com.google.firestore.p050v1.Document encodeDocument(Document document) {
        com.google.firestore.p050v1.Document.Builder newBuilder = com.google.firestore.p050v1.Document.newBuilder();
        newBuilder.setName(this.rpcSerializer.encodeKey(document.getKey()));
        Iterator it = document.getData().getInternalValue().iterator();
        while (it.hasNext()) {
            Entry entry = (Entry) it.next();
            newBuilder.putFields((String) entry.getKey(), this.rpcSerializer.encodeValue((FieldValue) entry.getValue()));
        }
        newBuilder.setUpdateTime(this.rpcSerializer.encodeTimestamp(document.getVersion().getTimestamp()));
        return (com.google.firestore.p050v1.Document) newBuilder.build();
    }

    private Document decodeDocument(com.google.firestore.p050v1.Document document, boolean z) {
        DocumentState documentState;
        DocumentKey decodeKey = this.rpcSerializer.decodeKey(document.getName());
        ObjectValue decodeFields = this.rpcSerializer.decodeFields(document.getFieldsMap());
        SnapshotVersion decodeVersion = this.rpcSerializer.decodeVersion(document.getUpdateTime());
        if (z) {
            documentState = DocumentState.COMMITTED_MUTATIONS;
        } else {
            documentState = DocumentState.SYNCED;
        }
        return new Document(decodeKey, decodeVersion, decodeFields, documentState);
    }

    private com.google.firebase.firestore.proto.NoDocument encodeNoDocument(NoDocument noDocument) {
        com.google.firebase.firestore.proto.NoDocument.Builder newBuilder = com.google.firebase.firestore.proto.NoDocument.newBuilder();
        newBuilder.setName(this.rpcSerializer.encodeKey(noDocument.getKey()));
        newBuilder.setReadTime(this.rpcSerializer.encodeTimestamp(noDocument.getVersion().getTimestamp()));
        return (com.google.firebase.firestore.proto.NoDocument) newBuilder.build();
    }

    private NoDocument decodeNoDocument(com.google.firebase.firestore.proto.NoDocument noDocument, boolean z) {
        return new NoDocument(this.rpcSerializer.decodeKey(noDocument.getName()), this.rpcSerializer.decodeVersion(noDocument.getReadTime()), z);
    }

    private com.google.firebase.firestore.proto.UnknownDocument encodeUnknownDocument(UnknownDocument unknownDocument) {
        com.google.firebase.firestore.proto.UnknownDocument.Builder newBuilder = com.google.firebase.firestore.proto.UnknownDocument.newBuilder();
        newBuilder.setName(this.rpcSerializer.encodeKey(unknownDocument.getKey()));
        newBuilder.setVersion(this.rpcSerializer.encodeTimestamp(unknownDocument.getVersion().getTimestamp()));
        return (com.google.firebase.firestore.proto.UnknownDocument) newBuilder.build();
    }

    private UnknownDocument decodeUnknownDocument(com.google.firebase.firestore.proto.UnknownDocument unknownDocument) {
        return new UnknownDocument(this.rpcSerializer.decodeKey(unknownDocument.getName()), this.rpcSerializer.decodeVersion(unknownDocument.getVersion()));
    }

    /* access modifiers changed from: 0000 */
    public WriteBatch encodeMutationBatch(MutationBatch mutationBatch) {
        WriteBatch.Builder newBuilder = WriteBatch.newBuilder();
        newBuilder.setBatchId(mutationBatch.getBatchId());
        newBuilder.setLocalWriteTime(this.rpcSerializer.encodeTimestamp(mutationBatch.getLocalWriteTime()));
        for (Mutation encodeMutation : mutationBatch.getBaseMutations()) {
            newBuilder.addBaseWrites(this.rpcSerializer.encodeMutation(encodeMutation));
        }
        for (Mutation encodeMutation2 : mutationBatch.getMutations()) {
            newBuilder.addWrites(this.rpcSerializer.encodeMutation(encodeMutation2));
        }
        return (WriteBatch) newBuilder.build();
    }

    /* access modifiers changed from: 0000 */
    public MutationBatch decodeMutationBatch(WriteBatch writeBatch) {
        int batchId = writeBatch.getBatchId();
        Timestamp decodeTimestamp = this.rpcSerializer.decodeTimestamp(writeBatch.getLocalWriteTime());
        int baseWritesCount = writeBatch.getBaseWritesCount();
        ArrayList arrayList = new ArrayList(baseWritesCount);
        for (int i = 0; i < baseWritesCount; i++) {
            arrayList.add(this.rpcSerializer.decodeMutation(writeBatch.getBaseWrites(i)));
        }
        int writesCount = writeBatch.getWritesCount();
        ArrayList arrayList2 = new ArrayList(writesCount);
        for (int i2 = 0; i2 < writesCount; i2++) {
            arrayList2.add(this.rpcSerializer.decodeMutation(writeBatch.getWrites(i2)));
        }
        return new MutationBatch(batchId, decodeTimestamp, arrayList, arrayList2);
    }

    /* access modifiers changed from: 0000 */
    public Target encodeQueryData(QueryData queryData) {
        Assert.hardAssert(QueryPurpose.LISTEN.equals(queryData.getPurpose()), "Only queries with purpose %s may be stored, got %s", QueryPurpose.LISTEN, queryData.getPurpose());
        Target.Builder newBuilder = Target.newBuilder();
        newBuilder.setTargetId(queryData.getTargetId()).setLastListenSequenceNumber(queryData.getSequenceNumber()).setSnapshotVersion(this.rpcSerializer.encodeVersion(queryData.getSnapshotVersion())).setResumeToken(queryData.getResumeToken());
        Query query = queryData.getQuery();
        if (query.isDocumentQuery()) {
            newBuilder.setDocuments(this.rpcSerializer.encodeDocumentsTarget(query));
        } else {
            newBuilder.setQuery(this.rpcSerializer.encodeQueryTarget(query));
        }
        return (Target) newBuilder.build();
    }

    /* access modifiers changed from: 0000 */
    public QueryData decodeQueryData(Target target) {
        Query query;
        int targetId = target.getTargetId();
        SnapshotVersion decodeVersion = this.rpcSerializer.decodeVersion(target.getSnapshotVersion());
        ByteString resumeToken = target.getResumeToken();
        long lastListenSequenceNumber = target.getLastListenSequenceNumber();
        int i = C36521.f2024x5167ea64[target.getTargetTypeCase().ordinal()];
        if (i == 1) {
            query = this.rpcSerializer.decodeDocumentsTarget(target.getDocuments());
        } else if (i == 2) {
            query = this.rpcSerializer.decodeQueryTarget(target.getQuery());
        } else {
            throw Assert.fail("Unknown targetType %d", target.getTargetTypeCase());
        }
        QueryData queryData = new QueryData(query, targetId, lastListenSequenceNumber, QueryPurpose.LISTEN, decodeVersion, resumeToken);
        return queryData;
    }
}
