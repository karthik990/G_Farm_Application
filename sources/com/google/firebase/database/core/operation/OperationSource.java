package com.google.firebase.database.core.operation;

import com.google.firebase.database.core.view.QueryParams;

/* compiled from: com.google.firebase:firebase-database@@17.0.0 */
public class OperationSource {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    public static final OperationSource SERVER = new OperationSource(Source.Server, null, false);
    public static final OperationSource USER = new OperationSource(Source.User, null, false);
    private final QueryParams queryParams;
    private final Source source;
    private final boolean tagged;

    /* compiled from: com.google.firebase:firebase-database@@17.0.0 */
    private enum Source {
        User,
        Server
    }

    public static OperationSource forServerTaggedQuery(QueryParams queryParams2) {
        return new OperationSource(Source.Server, queryParams2, true);
    }

    public OperationSource(Source source2, QueryParams queryParams2, boolean z) {
        this.source = source2;
        this.queryParams = queryParams2;
        this.tagged = z;
    }

    public boolean isFromUser() {
        return this.source == Source.User;
    }

    public boolean isFromServer() {
        return this.source == Source.Server;
    }

    public boolean isTagged() {
        return this.tagged;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("OperationSource{source=");
        sb.append(this.source);
        sb.append(", queryParams=");
        sb.append(this.queryParams);
        sb.append(", tagged=");
        sb.append(this.tagged);
        sb.append('}');
        return sb.toString();
    }

    public QueryParams getQueryParams() {
        return this.queryParams;
    }
}
