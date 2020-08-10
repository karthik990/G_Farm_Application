package com.google.firestore.p050v1;

import com.google.firestore.p050v1.StructuredQuery.CollectionSelector;
import com.google.firestore.p050v1.StructuredQuery.Filter;
import com.google.firestore.p050v1.StructuredQuery.Order;
import com.google.firestore.p050v1.StructuredQuery.Projection;
import com.google.protobuf.Int32Value;
import com.google.protobuf.MessageLiteOrBuilder;
import java.util.List;

/* renamed from: com.google.firestore.v1.StructuredQueryOrBuilder */
/* compiled from: com.google.firebase:firebase-firestore@@19.0.0 */
public interface StructuredQueryOrBuilder extends MessageLiteOrBuilder {
    Cursor getEndAt();

    CollectionSelector getFrom(int i);

    int getFromCount();

    List<CollectionSelector> getFromList();

    Int32Value getLimit();

    int getOffset();

    Order getOrderBy(int i);

    int getOrderByCount();

    List<Order> getOrderByList();

    Projection getSelect();

    Cursor getStartAt();

    Filter getWhere();

    boolean hasEndAt();

    boolean hasLimit();

    boolean hasSelect();

    boolean hasStartAt();

    boolean hasWhere();
}
