package com.google.firebase.database.core.persistence;

import com.google.firebase.database.core.view.QuerySpec;

/* compiled from: com.google.firebase:firebase-database@@17.0.0 */
public class TrackedQuery {
    public final boolean active;
    public final boolean complete;

    /* renamed from: id */
    public final long f2010id;
    public final long lastUse;
    public final QuerySpec querySpec;

    public TrackedQuery(long j, QuerySpec querySpec2, long j2, boolean z, boolean z2) {
        this.f2010id = j;
        if (!querySpec2.loadsAllData() || querySpec2.isDefault()) {
            this.querySpec = querySpec2;
            this.lastUse = j2;
            this.complete = z;
            this.active = z2;
            return;
        }
        throw new IllegalArgumentException("Can't create TrackedQuery for a non-default query that loads all data");
    }

    public TrackedQuery updateLastUse(long j) {
        TrackedQuery trackedQuery = new TrackedQuery(this.f2010id, this.querySpec, j, this.complete, this.active);
        return trackedQuery;
    }

    public TrackedQuery setComplete() {
        TrackedQuery trackedQuery = new TrackedQuery(this.f2010id, this.querySpec, this.lastUse, true, this.active);
        return trackedQuery;
    }

    public TrackedQuery setActiveState(boolean z) {
        TrackedQuery trackedQuery = new TrackedQuery(this.f2010id, this.querySpec, this.lastUse, this.complete, z);
        return trackedQuery;
    }

    public boolean equals(Object obj) {
        boolean z = true;
        if (obj == this) {
            return true;
        }
        if (obj == null || obj.getClass() != getClass()) {
            return false;
        }
        TrackedQuery trackedQuery = (TrackedQuery) obj;
        if (!(this.f2010id == trackedQuery.f2010id && this.querySpec.equals(trackedQuery.querySpec) && this.lastUse == trackedQuery.lastUse && this.complete == trackedQuery.complete && this.active == trackedQuery.active)) {
            z = false;
        }
        return z;
    }

    public int hashCode() {
        return (((((((Long.valueOf(this.f2010id).hashCode() * 31) + this.querySpec.hashCode()) * 31) + Long.valueOf(this.lastUse).hashCode()) * 31) + Boolean.valueOf(this.complete).hashCode()) * 31) + Boolean.valueOf(this.active).hashCode();
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("TrackedQuery{id=");
        sb.append(this.f2010id);
        sb.append(", querySpec=");
        sb.append(this.querySpec);
        sb.append(", lastUse=");
        sb.append(this.lastUse);
        sb.append(", complete=");
        sb.append(this.complete);
        sb.append(", active=");
        sb.append(this.active);
        sb.append("}");
        return sb.toString();
    }
}
