package com.google.firebase.database.core.view;

import com.google.firebase.database.core.Context;
import com.google.firebase.database.core.EventTarget;
import com.google.firebase.database.logging.LogWrapper;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/* compiled from: com.google.firebase:firebase-database@@17.0.0 */
public class EventRaiser {
    private final EventTarget eventTarget;
    /* access modifiers changed from: private */
    public final LogWrapper logger;

    public EventRaiser(Context context) {
        this.eventTarget = context.getEventTarget();
        this.logger = context.getLogger("EventRaiser");
    }

    public void raiseEvents(List<? extends Event> list) {
        if (this.logger.logsDebug()) {
            LogWrapper logWrapper = this.logger;
            StringBuilder sb = new StringBuilder();
            sb.append("Raising ");
            sb.append(list.size());
            sb.append(" event(s)");
            logWrapper.debug(sb.toString(), new Object[0]);
        }
        final ArrayList arrayList = new ArrayList(list);
        this.eventTarget.postEvent(new Runnable() {
            public void run() {
                Iterator it = arrayList.iterator();
                while (it.hasNext()) {
                    Event event = (Event) it.next();
                    if (EventRaiser.this.logger.logsDebug()) {
                        LogWrapper access$000 = EventRaiser.this.logger;
                        StringBuilder sb = new StringBuilder();
                        sb.append("Raising ");
                        sb.append(event.toString());
                        access$000.debug(sb.toString(), new Object[0]);
                    }
                    event.fire();
                }
            }
        });
    }
}
