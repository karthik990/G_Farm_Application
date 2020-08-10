package com.google.firebase.firestore.local;

import com.google.firebase.database.collection.ImmutableSortedMap;
import com.google.firebase.firestore.core.Filter;
import com.google.firebase.firestore.core.Filter.Operator;
import com.google.firebase.firestore.core.IndexRange;
import com.google.firebase.firestore.core.IndexRange.Builder;
import com.google.firebase.firestore.core.NaNFilter;
import com.google.firebase.firestore.core.NullFilter;
import com.google.firebase.firestore.core.OrderBy;
import com.google.firebase.firestore.core.Query;
import com.google.firebase.firestore.core.RelationFilter;
import com.google.firebase.firestore.model.Document;
import com.google.firebase.firestore.model.DocumentCollections;
import com.google.firebase.firestore.model.DocumentKey;
import com.google.firebase.firestore.model.FieldPath;
import com.google.firebase.firestore.model.MaybeDocument;
import com.google.firebase.firestore.model.value.ArrayValue;
import com.google.firebase.firestore.model.value.BooleanValue;
import com.google.firebase.firestore.model.value.DoubleValue;
import com.google.firebase.firestore.model.value.FieldValue;
import com.google.firebase.firestore.model.value.NullValue;
import com.google.firebase.firestore.model.value.ObjectValue;
import com.google.firebase.firestore.util.Assert;
import java.util.Arrays;
import java.util.List;

/* compiled from: com.google.firebase:firebase-firestore@@19.0.0 */
public class IndexedQueryEngine implements QueryEngine {
    private static final double HIGH_SELECTIVITY = 1.0d;
    private static final double LOW_SELECTIVITY = 0.5d;
    private static final List<Class> lowCardinalityTypes = Arrays.asList(new Class[]{BooleanValue.class, ArrayValue.class, ObjectValue.class});
    private final SQLiteCollectionIndex collectionIndex;
    private final LocalDocumentsView localDocuments;

    /* renamed from: com.google.firebase.firestore.local.IndexedQueryEngine$1 */
    /* compiled from: com.google.firebase:firebase-firestore@@19.0.0 */
    static /* synthetic */ class C36511 {
        static final /* synthetic */ int[] $SwitchMap$com$google$firebase$firestore$core$Filter$Operator = new int[Operator.values().length];

        /* JADX WARNING: Can't wrap try/catch for region: R(12:0|1|2|3|4|5|6|7|8|9|10|12) */
        /* JADX WARNING: Code restructure failed: missing block: B:13:?, code lost:
            return;
         */
        /* JADX WARNING: Failed to process nested try/catch */
        /* JADX WARNING: Missing exception handler attribute for start block: B:3:0x0014 */
        /* JADX WARNING: Missing exception handler attribute for start block: B:5:0x001f */
        /* JADX WARNING: Missing exception handler attribute for start block: B:7:0x002a */
        /* JADX WARNING: Missing exception handler attribute for start block: B:9:0x0035 */
        static {
            /*
                com.google.firebase.firestore.core.Filter$Operator[] r0 = com.google.firebase.firestore.core.Filter.Operator.values()
                int r0 = r0.length
                int[] r0 = new int[r0]
                $SwitchMap$com$google$firebase$firestore$core$Filter$Operator = r0
                int[] r0 = $SwitchMap$com$google$firebase$firestore$core$Filter$Operator     // Catch:{ NoSuchFieldError -> 0x0014 }
                com.google.firebase.firestore.core.Filter$Operator r1 = com.google.firebase.firestore.core.Filter.Operator.EQUAL     // Catch:{ NoSuchFieldError -> 0x0014 }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x0014 }
                r2 = 1
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x0014 }
            L_0x0014:
                int[] r0 = $SwitchMap$com$google$firebase$firestore$core$Filter$Operator     // Catch:{ NoSuchFieldError -> 0x001f }
                com.google.firebase.firestore.core.Filter$Operator r1 = com.google.firebase.firestore.core.Filter.Operator.LESS_THAN_OR_EQUAL     // Catch:{ NoSuchFieldError -> 0x001f }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x001f }
                r2 = 2
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x001f }
            L_0x001f:
                int[] r0 = $SwitchMap$com$google$firebase$firestore$core$Filter$Operator     // Catch:{ NoSuchFieldError -> 0x002a }
                com.google.firebase.firestore.core.Filter$Operator r1 = com.google.firebase.firestore.core.Filter.Operator.LESS_THAN     // Catch:{ NoSuchFieldError -> 0x002a }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x002a }
                r2 = 3
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x002a }
            L_0x002a:
                int[] r0 = $SwitchMap$com$google$firebase$firestore$core$Filter$Operator     // Catch:{ NoSuchFieldError -> 0x0035 }
                com.google.firebase.firestore.core.Filter$Operator r1 = com.google.firebase.firestore.core.Filter.Operator.GREATER_THAN     // Catch:{ NoSuchFieldError -> 0x0035 }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x0035 }
                r2 = 4
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x0035 }
            L_0x0035:
                int[] r0 = $SwitchMap$com$google$firebase$firestore$core$Filter$Operator     // Catch:{ NoSuchFieldError -> 0x0040 }
                com.google.firebase.firestore.core.Filter$Operator r1 = com.google.firebase.firestore.core.Filter.Operator.GREATER_THAN_OR_EQUAL     // Catch:{ NoSuchFieldError -> 0x0040 }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x0040 }
                r2 = 5
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x0040 }
            L_0x0040:
                return
            */
            throw new UnsupportedOperationException("Method not decompiled: com.google.firebase.firestore.local.IndexedQueryEngine.C36511.<clinit>():void");
        }
    }

    public IndexedQueryEngine(LocalDocumentsView localDocumentsView, SQLiteCollectionIndex sQLiteCollectionIndex) {
        this.localDocuments = localDocumentsView;
        this.collectionIndex = sQLiteCollectionIndex;
    }

    public ImmutableSortedMap<DocumentKey, Document> getDocumentsMatchingQuery(Query query) {
        if (query.isDocumentQuery()) {
            return this.localDocuments.getDocumentsMatchingQuery(query);
        }
        return performCollectionQuery(query);
    }

    private ImmutableSortedMap<DocumentKey, Document> performCollectionQuery(Query query) {
        Assert.hardAssert(!query.isDocumentQuery(), "matchesCollectionQuery() called with document query.", new Object[0]);
        IndexRange extractBestIndexRange = extractBestIndexRange(query);
        if (extractBestIndexRange != null) {
            return performQueryUsingIndex(query, extractBestIndexRange);
        }
        Assert.hardAssert(query.getFilters().isEmpty(), "If there are any filters, we should be able to use an index.", new Object[0]);
        return this.localDocuments.getDocumentsMatchingQuery(query);
    }

    private ImmutableSortedMap<DocumentKey, Document> performQueryUsingIndex(Query query, IndexRange indexRange) {
        ImmutableSortedMap<DocumentKey, Document> emptyDocumentMap = DocumentCollections.emptyDocumentMap();
        IndexCursor cursor = this.collectionIndex.getCursor(query.getPath(), indexRange);
        while (cursor.next()) {
            try {
                Document document = (Document) this.localDocuments.getDocument(cursor.getDocumentKey());
                if (query.matches(document)) {
                    emptyDocumentMap = emptyDocumentMap.insert(cursor.getDocumentKey(), document);
                }
            } finally {
                cursor.close();
            }
        }
        return emptyDocumentMap;
    }

    private static double estimateFilterSelectivity(Filter filter) {
        boolean z = filter instanceof NullFilter;
        double d = HIGH_SELECTIVITY;
        if (z || (filter instanceof NaNFilter)) {
            return HIGH_SELECTIVITY;
        }
        Assert.hardAssert(filter instanceof RelationFilter, "Filter type expected to be RelationFilter", new Object[0]);
        RelationFilter relationFilter = (RelationFilter) filter;
        double d2 = relationFilter.getOperator().equals(Operator.EQUAL) ? 1.0d : 0.5d;
        if (lowCardinalityTypes.contains(relationFilter.getValue().getClass())) {
            d = 0.5d;
        }
        return d * d2;
    }

    static IndexRange extractBestIndexRange(Query query) {
        Filter filter = null;
        if (!query.getFilters().isEmpty()) {
            double d = -1.0d;
            for (Filter filter2 : query.getFilters()) {
                double estimateFilterSelectivity = estimateFilterSelectivity(filter2);
                if (estimateFilterSelectivity > d) {
                    filter = filter2;
                    d = estimateFilterSelectivity;
                }
            }
            Assert.hardAssert(filter != null, "Filter should be defined", new Object[0]);
            return convertFilterToIndexRange(filter);
        } else if (!((OrderBy) query.getOrderBy().get(0)).getField().equals(FieldPath.KEY_PATH)) {
            return IndexRange.builder().setFieldPath(((OrderBy) query.getOrderBy().get(0)).getField()).build();
        } else {
            return null;
        }
    }

    private static IndexRange convertFilterToIndexRange(Filter filter) {
        Builder fieldPath = IndexRange.builder().setFieldPath(filter.getField());
        if (filter instanceof RelationFilter) {
            RelationFilter relationFilter = (RelationFilter) filter;
            FieldValue value = relationFilter.getValue();
            int i = C36511.$SwitchMap$com$google$firebase$firestore$core$Filter$Operator[relationFilter.getOperator().ordinal()];
            if (i == 1) {
                fieldPath.setStart(value).setEnd(value);
            } else if (i == 2 || i == 3) {
                fieldPath.setEnd(value);
            } else if (i == 4 || i == 5) {
                fieldPath.setStart(value);
            } else {
                throw Assert.fail("Unexpected operator in query filter", new Object[0]);
            }
        } else if (filter instanceof NaNFilter) {
            fieldPath.setStart(DoubleValue.NaN).setEnd(DoubleValue.NaN);
        } else if (filter instanceof NullFilter) {
            fieldPath.setStart(NullValue.nullValue()).setEnd(NullValue.nullValue());
        }
        return fieldPath.build();
    }

    public void handleDocumentChange(MaybeDocument maybeDocument, MaybeDocument maybeDocument2) {
        throw new RuntimeException("Not yet implemented.");
    }
}
