package com.google.firebase.database.core.view;

import com.google.firebase.database.core.Path;
import com.google.firebase.database.snapshot.Index;
import java.util.Map;

/* compiled from: com.google.firebase:firebase-database@@17.0.0 */
public class QuerySpec {
    private final QueryParams params;
    private final Path path;

    public static QuerySpec defaultQueryAtPath(Path path2) {
        return new QuerySpec(path2, QueryParams.DEFAULT_PARAMS);
    }

    public QuerySpec(Path path2, QueryParams queryParams) {
        this.path = path2;
        this.params = queryParams;
    }

    public Path getPath() {
        return this.path;
    }

    public QueryParams getParams() {
        return this.params;
    }

    public static QuerySpec fromPathAndQueryObject(Path path2, Map<String, Object> map) {
        return new QuerySpec(path2, QueryParams.fromQueryObject(map));
    }

    public Index getIndex() {
        return this.params.getIndex();
    }

    public boolean isDefault() {
        return this.params.isDefault();
    }

    public boolean loadsAllData() {
        return this.params.loadsAllData();
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(this.path);
        sb.append(":");
        sb.append(this.params);
        return sb.toString();
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        QuerySpec querySpec = (QuerySpec) obj;
        return this.path.equals(querySpec.path) && this.params.equals(querySpec.params);
    }

    public int hashCode() {
        return (this.path.hashCode() * 31) + this.params.hashCode();
    }
}
