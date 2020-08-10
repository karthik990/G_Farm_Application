package com.firebase.p037ui.firestore.paging;

import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.Query;

/* renamed from: com.firebase.ui.firestore.paging.PageKey */
public class PageKey {
    private final DocumentSnapshot mEndBefore;
    private final DocumentSnapshot mStartAfter;

    public PageKey(DocumentSnapshot documentSnapshot, DocumentSnapshot documentSnapshot2) {
        this.mStartAfter = documentSnapshot;
        this.mEndBefore = documentSnapshot2;
    }

    public Query getPageQuery(Query query, int i) {
        DocumentSnapshot documentSnapshot = this.mStartAfter;
        if (documentSnapshot != null) {
            query = query.startAfter(documentSnapshot);
        }
        DocumentSnapshot documentSnapshot2 = this.mEndBefore;
        if (documentSnapshot2 != null) {
            return query.endBefore(documentSnapshot2);
        }
        return query.limit((long) i);
    }

    public String toString() {
        DocumentSnapshot documentSnapshot = this.mStartAfter;
        String str = null;
        String id = documentSnapshot == null ? null : documentSnapshot.getId();
        DocumentSnapshot documentSnapshot2 = this.mEndBefore;
        if (documentSnapshot2 != null) {
            str = documentSnapshot2.getId();
        }
        StringBuilder sb = new StringBuilder();
        sb.append("PageKey{StartAfter=");
        sb.append(id);
        sb.append(", EndBefore=");
        sb.append(str);
        sb.append('}');
        return sb.toString();
    }
}
