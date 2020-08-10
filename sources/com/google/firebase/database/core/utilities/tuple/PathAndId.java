package com.google.firebase.database.core.utilities.tuple;

import com.google.firebase.database.core.Path;

/* compiled from: com.google.firebase:firebase-database@@17.0.0 */
public class PathAndId {

    /* renamed from: id */
    private long f2011id;
    private Path path;

    public PathAndId(Path path2, long j) {
        this.path = path2;
        this.f2011id = j;
    }

    public Path getPath() {
        return this.path;
    }

    public long getId() {
        return this.f2011id;
    }
}
