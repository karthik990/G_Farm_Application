package com.google.firebase.firestore.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/* compiled from: com.google.firebase:firebase-firestore@@19.0.0 */
public final class ResourcePath extends BasePath<ResourcePath> {
    public static final ResourcePath EMPTY = new ResourcePath(Collections.emptyList());

    private ResourcePath(List<String> list) {
        super(list);
    }

    /* access modifiers changed from: 0000 */
    public ResourcePath createPathWithSegments(List<String> list) {
        return new ResourcePath(list);
    }

    public static ResourcePath fromSegments(List<String> list) {
        return list.isEmpty() ? EMPTY : new ResourcePath(list);
    }

    public static ResourcePath fromString(String str) {
        if (!str.contains("//")) {
            String[] split = str.split("/");
            ArrayList arrayList = new ArrayList(split.length);
            for (String str2 : split) {
                if (!str2.isEmpty()) {
                    arrayList.add(str2);
                }
            }
            return new ResourcePath(arrayList);
        }
        StringBuilder sb = new StringBuilder();
        sb.append("Invalid path (");
        sb.append(str);
        sb.append("). Paths must not contain // in them.");
        throw new IllegalArgumentException(sb.toString());
    }

    public String canonicalString() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < this.segments.size(); i++) {
            if (i > 0) {
                sb.append("/");
            }
            sb.append((String) this.segments.get(i));
        }
        return sb.toString();
    }
}
