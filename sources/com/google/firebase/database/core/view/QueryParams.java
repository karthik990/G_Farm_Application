package com.google.firebase.database.core.view;

import com.google.firebase.database.core.utilities.Utilities;
import com.google.firebase.database.core.view.filter.IndexedFilter;
import com.google.firebase.database.core.view.filter.LimitedFilter;
import com.google.firebase.database.core.view.filter.NodeFilter;
import com.google.firebase.database.core.view.filter.RangedFilter;
import com.google.firebase.database.snapshot.BooleanNode;
import com.google.firebase.database.snapshot.ChildKey;
import com.google.firebase.database.snapshot.DoubleNode;
import com.google.firebase.database.snapshot.EmptyNode;
import com.google.firebase.database.snapshot.Index;
import com.google.firebase.database.snapshot.LongNode;
import com.google.firebase.database.snapshot.Node;
import com.google.firebase.database.snapshot.NodeUtilities;
import com.google.firebase.database.snapshot.PriorityIndex;
import com.google.firebase.database.snapshot.PriorityUtilities;
import com.google.firebase.database.snapshot.StringNode;
import com.google.firebase.database.util.JsonMapper;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/* compiled from: com.google.firebase:firebase-database@@17.0.0 */
public class QueryParams {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    public static final QueryParams DEFAULT_PARAMS = new QueryParams();
    private static final String INDEX = "i";
    private static final String INDEX_END_NAME = "en";
    private static final String INDEX_END_VALUE = "ep";
    private static final String INDEX_START_NAME = "sn";
    private static final String INDEX_START_VALUE = "sp";
    private static final String LIMIT = "l";
    private static final String VIEW_FROM = "vf";
    private Index index = PriorityIndex.getInstance();
    private ChildKey indexEndName = null;
    private Node indexEndValue = null;
    private ChildKey indexStartName = null;
    private Node indexStartValue = null;
    private String jsonSerialization = null;
    private Integer limit;
    private ViewFrom viewFrom;

    /* renamed from: com.google.firebase.database.core.view.QueryParams$1 */
    /* compiled from: com.google.firebase:firebase-database@@17.0.0 */
    static /* synthetic */ class C36191 {

        /* renamed from: $SwitchMap$com$google$firebase$database$core$view$QueryParams$ViewFrom */
        static final /* synthetic */ int[] f2012xa04b6c33 = new int[ViewFrom.values().length];

        /* JADX WARNING: Can't wrap try/catch for region: R(6:0|1|2|3|4|6) */
        /* JADX WARNING: Code restructure failed: missing block: B:7:?, code lost:
            return;
         */
        /* JADX WARNING: Failed to process nested try/catch */
        /* JADX WARNING: Missing exception handler attribute for start block: B:3:0x0014 */
        static {
            /*
                com.google.firebase.database.core.view.QueryParams$ViewFrom[] r0 = com.google.firebase.database.core.view.QueryParams.ViewFrom.values()
                int r0 = r0.length
                int[] r0 = new int[r0]
                f2012xa04b6c33 = r0
                int[] r0 = f2012xa04b6c33     // Catch:{ NoSuchFieldError -> 0x0014 }
                com.google.firebase.database.core.view.QueryParams$ViewFrom r1 = com.google.firebase.database.core.view.QueryParams.ViewFrom.LEFT     // Catch:{ NoSuchFieldError -> 0x0014 }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x0014 }
                r2 = 1
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x0014 }
            L_0x0014:
                int[] r0 = f2012xa04b6c33     // Catch:{ NoSuchFieldError -> 0x001f }
                com.google.firebase.database.core.view.QueryParams$ViewFrom r1 = com.google.firebase.database.core.view.QueryParams.ViewFrom.RIGHT     // Catch:{ NoSuchFieldError -> 0x001f }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x001f }
                r2 = 2
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x001f }
            L_0x001f:
                return
            */
            throw new UnsupportedOperationException("Method not decompiled: com.google.firebase.database.core.view.QueryParams.C36191.<clinit>():void");
        }
    }

    /* compiled from: com.google.firebase:firebase-database@@17.0.0 */
    private enum ViewFrom {
        LEFT,
        RIGHT
    }

    public boolean hasStart() {
        return this.indexStartValue != null;
    }

    public Node getIndexStartValue() {
        if (hasStart()) {
            return this.indexStartValue;
        }
        throw new IllegalArgumentException("Cannot get index start value if start has not been set");
    }

    public ChildKey getIndexStartName() {
        if (hasStart()) {
            ChildKey childKey = this.indexStartName;
            if (childKey != null) {
                return childKey;
            }
            return ChildKey.getMinName();
        }
        throw new IllegalArgumentException("Cannot get index start name if start has not been set");
    }

    public boolean hasEnd() {
        return this.indexEndValue != null;
    }

    public Node getIndexEndValue() {
        if (hasEnd()) {
            return this.indexEndValue;
        }
        throw new IllegalArgumentException("Cannot get index end value if start has not been set");
    }

    public ChildKey getIndexEndName() {
        if (hasEnd()) {
            ChildKey childKey = this.indexEndName;
            if (childKey != null) {
                return childKey;
            }
            return ChildKey.getMaxName();
        }
        throw new IllegalArgumentException("Cannot get index end name if start has not been set");
    }

    public boolean hasLimit() {
        return this.limit != null;
    }

    public boolean hasAnchoredLimit() {
        return hasLimit() && this.viewFrom != null;
    }

    public int getLimit() {
        if (hasLimit()) {
            return this.limit.intValue();
        }
        throw new IllegalArgumentException("Cannot get limit if limit has not been set");
    }

    public Index getIndex() {
        return this.index;
    }

    private QueryParams copy() {
        QueryParams queryParams = new QueryParams();
        queryParams.limit = this.limit;
        queryParams.indexStartValue = this.indexStartValue;
        queryParams.indexStartName = this.indexStartName;
        queryParams.indexEndValue = this.indexEndValue;
        queryParams.indexEndName = this.indexEndName;
        queryParams.viewFrom = this.viewFrom;
        queryParams.index = this.index;
        return queryParams;
    }

    public QueryParams limitToFirst(int i) {
        QueryParams copy = copy();
        copy.limit = Integer.valueOf(i);
        copy.viewFrom = ViewFrom.LEFT;
        return copy;
    }

    public QueryParams limitToLast(int i) {
        QueryParams copy = copy();
        copy.limit = Integer.valueOf(i);
        copy.viewFrom = ViewFrom.RIGHT;
        return copy;
    }

    public QueryParams startAt(Node node, ChildKey childKey) {
        Utilities.hardAssert(!(node instanceof LongNode));
        QueryParams copy = copy();
        copy.indexStartValue = node;
        copy.indexStartName = childKey;
        return copy;
    }

    public QueryParams endAt(Node node, ChildKey childKey) {
        Utilities.hardAssert(!(node instanceof LongNode));
        QueryParams copy = copy();
        copy.indexEndValue = node;
        copy.indexEndName = childKey;
        return copy;
    }

    public QueryParams orderBy(Index index2) {
        QueryParams copy = copy();
        copy.index = index2;
        return copy;
    }

    public boolean isViewFromLeft() {
        ViewFrom viewFrom2 = this.viewFrom;
        if (viewFrom2 != null) {
            return viewFrom2 == ViewFrom.LEFT;
        }
        return hasStart();
    }

    public Map<String, Object> getWireProtocolParams() {
        HashMap hashMap = new HashMap();
        if (hasStart()) {
            hashMap.put(INDEX_START_VALUE, this.indexStartValue.getValue());
            ChildKey childKey = this.indexStartName;
            if (childKey != null) {
                hashMap.put(INDEX_START_NAME, childKey.asString());
            }
        }
        if (hasEnd()) {
            hashMap.put(INDEX_END_VALUE, this.indexEndValue.getValue());
            ChildKey childKey2 = this.indexEndName;
            if (childKey2 != null) {
                hashMap.put(INDEX_END_NAME, childKey2.asString());
            }
        }
        Integer num = this.limit;
        if (num != null) {
            String str = LIMIT;
            hashMap.put(str, num);
            ViewFrom viewFrom2 = this.viewFrom;
            if (viewFrom2 == null) {
                if (hasStart()) {
                    viewFrom2 = ViewFrom.LEFT;
                } else {
                    viewFrom2 = ViewFrom.RIGHT;
                }
            }
            int i = C36191.f2012xa04b6c33[viewFrom2.ordinal()];
            String str2 = VIEW_FROM;
            if (i == 1) {
                hashMap.put(str2, str);
            } else if (i == 2) {
                hashMap.put(str2, "r");
            }
        }
        if (!this.index.equals(PriorityIndex.getInstance())) {
            hashMap.put("i", this.index.getQueryDefinition());
        }
        return hashMap;
    }

    public boolean loadsAllData() {
        return !hasStart() && !hasEnd() && !hasLimit();
    }

    public boolean isDefault() {
        return loadsAllData() && this.index.equals(PriorityIndex.getInstance());
    }

    public boolean isValid() {
        return !hasStart() || !hasEnd() || !hasLimit() || hasAnchoredLimit();
    }

    public String toJSON() {
        if (this.jsonSerialization == null) {
            try {
                this.jsonSerialization = JsonMapper.serializeJson(getWireProtocolParams());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        return this.jsonSerialization;
    }

    public static QueryParams fromQueryObject(Map<String, Object> map) {
        QueryParams queryParams = new QueryParams();
        String str = LIMIT;
        queryParams.limit = (Integer) map.get(str);
        String str2 = INDEX_START_VALUE;
        if (map.containsKey(str2)) {
            queryParams.indexStartValue = normalizeValue(NodeUtilities.NodeFromJSON(map.get(str2)));
            String str3 = (String) map.get(INDEX_START_NAME);
            if (str3 != null) {
                queryParams.indexStartName = ChildKey.fromString(str3);
            }
        }
        String str4 = INDEX_END_VALUE;
        if (map.containsKey(str4)) {
            queryParams.indexEndValue = normalizeValue(NodeUtilities.NodeFromJSON(map.get(str4)));
            String str5 = (String) map.get(INDEX_END_NAME);
            if (str5 != null) {
                queryParams.indexEndName = ChildKey.fromString(str5);
            }
        }
        String str6 = (String) map.get(VIEW_FROM);
        if (str6 != null) {
            queryParams.viewFrom = str6.equals(str) ? ViewFrom.LEFT : ViewFrom.RIGHT;
        }
        String str7 = (String) map.get("i");
        if (str7 != null) {
            queryParams.index = Index.fromQueryDefinition(str7);
        }
        return queryParams;
    }

    public NodeFilter getNodeFilter() {
        if (loadsAllData()) {
            return new IndexedFilter(getIndex());
        }
        if (hasLimit()) {
            return new LimitedFilter(this);
        }
        return new RangedFilter(this);
    }

    public String toString() {
        return getWireProtocolParams().toString();
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        QueryParams queryParams = (QueryParams) obj;
        Integer num = this.limit;
        if (num == null ? queryParams.limit != null : !num.equals(queryParams.limit)) {
            return false;
        }
        Index index2 = this.index;
        if (index2 == null ? queryParams.index != null : !index2.equals(queryParams.index)) {
            return false;
        }
        ChildKey childKey = this.indexEndName;
        if (childKey == null ? queryParams.indexEndName != null : !childKey.equals(queryParams.indexEndName)) {
            return false;
        }
        Node node = this.indexEndValue;
        if (node == null ? queryParams.indexEndValue != null : !node.equals(queryParams.indexEndValue)) {
            return false;
        }
        ChildKey childKey2 = this.indexStartName;
        if (childKey2 == null ? queryParams.indexStartName != null : !childKey2.equals(queryParams.indexStartName)) {
            return false;
        }
        Node node2 = this.indexStartValue;
        if (node2 == null ? queryParams.indexStartValue == null : node2.equals(queryParams.indexStartValue)) {
            return isViewFromLeft() == queryParams.isViewFromLeft();
        }
        return false;
    }

    public int hashCode() {
        Integer num = this.limit;
        int i = 0;
        int intValue = (((num != null ? num.intValue() : 0) * 31) + (isViewFromLeft() ? 1231 : 1237)) * 31;
        Node node = this.indexStartValue;
        int hashCode = (intValue + (node != null ? node.hashCode() : 0)) * 31;
        ChildKey childKey = this.indexStartName;
        int hashCode2 = (hashCode + (childKey != null ? childKey.hashCode() : 0)) * 31;
        Node node2 = this.indexEndValue;
        int hashCode3 = (hashCode2 + (node2 != null ? node2.hashCode() : 0)) * 31;
        ChildKey childKey2 = this.indexEndName;
        int hashCode4 = (hashCode3 + (childKey2 != null ? childKey2.hashCode() : 0)) * 31;
        Index index2 = this.index;
        if (index2 != null) {
            i = index2.hashCode();
        }
        return hashCode4 + i;
    }

    private static Node normalizeValue(Node node) {
        if ((node instanceof StringNode) || (node instanceof BooleanNode) || (node instanceof DoubleNode) || (node instanceof EmptyNode)) {
            return node;
        }
        if (node instanceof LongNode) {
            return new DoubleNode(Double.valueOf(((Long) node.getValue()).doubleValue()), PriorityUtilities.NullPriority());
        }
        StringBuilder sb = new StringBuilder();
        sb.append("Unexpected value passed to normalizeValue: ");
        sb.append(node.getValue());
        throw new IllegalStateException(sb.toString());
    }
}
