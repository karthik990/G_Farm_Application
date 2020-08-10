package com.google.firebase.database.core;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.InternalHelpers;
import com.google.firebase.database.core.view.Change;
import com.google.firebase.database.core.view.DataEvent;
import com.google.firebase.database.core.view.Event.EventType;
import com.google.firebase.database.core.view.QuerySpec;

/* compiled from: com.google.firebase:firebase-database@@17.0.0 */
public class ChildEventRegistration extends EventRegistration {
    private final ChildEventListener eventListener;
    private final Repo repo;
    private final QuerySpec spec;

    /* renamed from: com.google.firebase.database.core.ChildEventRegistration$1 */
    /* compiled from: com.google.firebase:firebase-database@@17.0.0 */
    static /* synthetic */ class C35401 {

        /* renamed from: $SwitchMap$com$google$firebase$database$core$view$Event$EventType */
        static final /* synthetic */ int[] f2009x916339dc = new int[EventType.values().length];

        /* JADX WARNING: Can't wrap try/catch for region: R(10:0|1|2|3|4|5|6|7|8|10) */
        /* JADX WARNING: Can't wrap try/catch for region: R(8:0|1|2|3|4|5|6|(3:7|8|10)) */
        /* JADX WARNING: Failed to process nested try/catch */
        /* JADX WARNING: Missing exception handler attribute for start block: B:3:0x0014 */
        /* JADX WARNING: Missing exception handler attribute for start block: B:5:0x001f */
        /* JADX WARNING: Missing exception handler attribute for start block: B:7:0x002a */
        static {
            /*
                com.google.firebase.database.core.view.Event$EventType[] r0 = com.google.firebase.database.core.view.Event.EventType.values()
                int r0 = r0.length
                int[] r0 = new int[r0]
                f2009x916339dc = r0
                int[] r0 = f2009x916339dc     // Catch:{ NoSuchFieldError -> 0x0014 }
                com.google.firebase.database.core.view.Event$EventType r1 = com.google.firebase.database.core.view.Event.EventType.CHILD_ADDED     // Catch:{ NoSuchFieldError -> 0x0014 }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x0014 }
                r2 = 1
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x0014 }
            L_0x0014:
                int[] r0 = f2009x916339dc     // Catch:{ NoSuchFieldError -> 0x001f }
                com.google.firebase.database.core.view.Event$EventType r1 = com.google.firebase.database.core.view.Event.EventType.CHILD_CHANGED     // Catch:{ NoSuchFieldError -> 0x001f }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x001f }
                r2 = 2
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x001f }
            L_0x001f:
                int[] r0 = f2009x916339dc     // Catch:{ NoSuchFieldError -> 0x002a }
                com.google.firebase.database.core.view.Event$EventType r1 = com.google.firebase.database.core.view.Event.EventType.CHILD_MOVED     // Catch:{ NoSuchFieldError -> 0x002a }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x002a }
                r2 = 3
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x002a }
            L_0x002a:
                int[] r0 = f2009x916339dc     // Catch:{ NoSuchFieldError -> 0x0035 }
                com.google.firebase.database.core.view.Event$EventType r1 = com.google.firebase.database.core.view.Event.EventType.CHILD_REMOVED     // Catch:{ NoSuchFieldError -> 0x0035 }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x0035 }
                r2 = 4
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x0035 }
            L_0x0035:
                return
            */
            throw new UnsupportedOperationException("Method not decompiled: com.google.firebase.database.core.ChildEventRegistration.C35401.<clinit>():void");
        }
    }

    public String toString() {
        return "ChildEventRegistration";
    }

    public ChildEventRegistration(Repo repo2, ChildEventListener childEventListener, QuerySpec querySpec) {
        this.repo = repo2;
        this.eventListener = childEventListener;
        this.spec = querySpec;
    }

    public boolean respondsTo(EventType eventType) {
        return eventType != EventType.VALUE;
    }

    public boolean equals(Object obj) {
        if (obj instanceof ChildEventRegistration) {
            ChildEventRegistration childEventRegistration = (ChildEventRegistration) obj;
            if (childEventRegistration.eventListener.equals(this.eventListener) && childEventRegistration.repo.equals(this.repo) && childEventRegistration.spec.equals(this.spec)) {
                return true;
            }
        }
        return false;
    }

    public int hashCode() {
        return (((this.eventListener.hashCode() * 31) + this.repo.hashCode()) * 31) + this.spec.hashCode();
    }

    public DataEvent createEvent(Change change, QuerySpec querySpec) {
        return new DataEvent(change.getEventType(), this, InternalHelpers.createDataSnapshot(InternalHelpers.createReference(this.repo, querySpec.getPath().child(change.getChildKey())), change.getIndexedNode()), change.getPrevName() != null ? change.getPrevName().asString() : null);
    }

    public void fireEvent(DataEvent dataEvent) {
        if (!isZombied()) {
            int i = C35401.f2009x916339dc[dataEvent.getEventType().ordinal()];
            if (i == 1) {
                this.eventListener.onChildAdded(dataEvent.getSnapshot(), dataEvent.getPreviousName());
            } else if (i == 2) {
                this.eventListener.onChildChanged(dataEvent.getSnapshot(), dataEvent.getPreviousName());
            } else if (i == 3) {
                this.eventListener.onChildMoved(dataEvent.getSnapshot(), dataEvent.getPreviousName());
            } else if (i == 4) {
                this.eventListener.onChildRemoved(dataEvent.getSnapshot());
            }
        }
    }

    public void fireCancelEvent(DatabaseError databaseError) {
        this.eventListener.onCancelled(databaseError);
    }

    public EventRegistration clone(QuerySpec querySpec) {
        return new ChildEventRegistration(this.repo, this.eventListener, querySpec);
    }

    public boolean isSameListener(EventRegistration eventRegistration) {
        return (eventRegistration instanceof ChildEventRegistration) && ((ChildEventRegistration) eventRegistration).eventListener.equals(this.eventListener);
    }

    public QuerySpec getQuerySpec() {
        return this.spec;
    }

    /* access modifiers changed from: 0000 */
    public Repo getRepo() {
        return this.repo;
    }
}
