package com.google.firebase.database.core;

import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.InternalHelpers;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.core.view.Change;
import com.google.firebase.database.core.view.DataEvent;
import com.google.firebase.database.core.view.Event.EventType;
import com.google.firebase.database.core.view.QuerySpec;

/* compiled from: com.google.firebase:firebase-database@@17.0.0 */
public class ValueEventRegistration extends EventRegistration {
    private final ValueEventListener eventListener;
    private final Repo repo;
    private final QuerySpec spec;

    public String toString() {
        return "ValueEventRegistration";
    }

    public ValueEventRegistration(Repo repo2, ValueEventListener valueEventListener, QuerySpec querySpec) {
        this.repo = repo2;
        this.eventListener = valueEventListener;
        this.spec = querySpec;
    }

    public boolean respondsTo(EventType eventType) {
        return eventType == EventType.VALUE;
    }

    public boolean equals(Object obj) {
        if (obj instanceof ValueEventRegistration) {
            ValueEventRegistration valueEventRegistration = (ValueEventRegistration) obj;
            if (valueEventRegistration.eventListener.equals(this.eventListener) && valueEventRegistration.repo.equals(this.repo) && valueEventRegistration.spec.equals(this.spec)) {
                return true;
            }
        }
        return false;
    }

    public int hashCode() {
        return (((this.eventListener.hashCode() * 31) + this.repo.hashCode()) * 31) + this.spec.hashCode();
    }

    public DataEvent createEvent(Change change, QuerySpec querySpec) {
        return new DataEvent(EventType.VALUE, this, InternalHelpers.createDataSnapshot(InternalHelpers.createReference(this.repo, querySpec.getPath()), change.getIndexedNode()), null);
    }

    public void fireEvent(DataEvent dataEvent) {
        if (!isZombied()) {
            this.eventListener.onDataChange(dataEvent.getSnapshot());
        }
    }

    public void fireCancelEvent(DatabaseError databaseError) {
        this.eventListener.onCancelled(databaseError);
    }

    public EventRegistration clone(QuerySpec querySpec) {
        return new ValueEventRegistration(this.repo, this.eventListener, querySpec);
    }

    public boolean isSameListener(EventRegistration eventRegistration) {
        return (eventRegistration instanceof ValueEventRegistration) && ((ValueEventRegistration) eventRegistration).eventListener.equals(this.eventListener);
    }

    public QuerySpec getQuerySpec() {
        return this.spec;
    }

    /* access modifiers changed from: 0000 */
    public Repo getRepo() {
        return this.repo;
    }
}
