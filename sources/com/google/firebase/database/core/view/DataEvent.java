package com.google.firebase.database.core.view;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.core.EventRegistration;
import com.google.firebase.database.core.Path;
import com.google.firebase.database.core.view.Event.EventType;

/* compiled from: com.google.firebase:firebase-database@@17.0.0 */
public class DataEvent implements Event {
    private final EventRegistration eventRegistration;
    private final EventType eventType;
    private final String prevName;
    private final DataSnapshot snapshot;

    public DataEvent(EventType eventType2, EventRegistration eventRegistration2, DataSnapshot dataSnapshot, String str) {
        this.eventType = eventType2;
        this.eventRegistration = eventRegistration2;
        this.snapshot = dataSnapshot;
        this.prevName = str;
    }

    public Path getPath() {
        Path path = this.snapshot.getRef().getPath();
        if (this.eventType == EventType.VALUE) {
            return path;
        }
        return path.getParent();
    }

    public DataSnapshot getSnapshot() {
        return this.snapshot;
    }

    public String getPreviousName() {
        return this.prevName;
    }

    public EventType getEventType() {
        return this.eventType;
    }

    public void fire() {
        this.eventRegistration.fireEvent(this);
    }

    public String toString() {
        String str = ": ";
        if (this.eventType == EventType.VALUE) {
            StringBuilder sb = new StringBuilder();
            sb.append(getPath());
            sb.append(str);
            sb.append(this.eventType);
            sb.append(str);
            sb.append(this.snapshot.getValue(true));
            return sb.toString();
        }
        StringBuilder sb2 = new StringBuilder();
        sb2.append(getPath());
        sb2.append(str);
        sb2.append(this.eventType);
        sb2.append(": { ");
        sb2.append(this.snapshot.getKey());
        sb2.append(str);
        sb2.append(this.snapshot.getValue(true));
        sb2.append(" }");
        return sb2.toString();
    }
}
