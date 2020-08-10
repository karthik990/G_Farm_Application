package com.google.firebase.database.core;

import com.google.firebase.database.core.view.QuerySpec;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

/* compiled from: com.google.firebase:firebase-database@@17.0.0 */
public class ZombieEventManager implements EventRegistrationZombieListener {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    private static ZombieEventManager defaultInstance = new ZombieEventManager();
    final HashMap<EventRegistration, List<EventRegistration>> globalEventRegistrations = new HashMap<>();

    private ZombieEventManager() {
    }

    public static ZombieEventManager getInstance() {
        return defaultInstance;
    }

    public void recordEventRegistration(EventRegistration eventRegistration) {
        synchronized (this.globalEventRegistrations) {
            List list = (List) this.globalEventRegistrations.get(eventRegistration);
            if (list == null) {
                list = new ArrayList();
                this.globalEventRegistrations.put(eventRegistration, list);
            }
            list.add(eventRegistration);
            if (!eventRegistration.getQuerySpec().isDefault()) {
                EventRegistration clone = eventRegistration.clone(QuerySpec.defaultQueryAtPath(eventRegistration.getQuerySpec().getPath()));
                List list2 = (List) this.globalEventRegistrations.get(clone);
                if (list2 == null) {
                    list2 = new ArrayList();
                    this.globalEventRegistrations.put(clone, list2);
                }
                list2.add(eventRegistration);
            }
            eventRegistration.setIsUserInitiated(true);
            eventRegistration.setOnZombied(this);
        }
    }

    private void unRecordEventRegistration(EventRegistration eventRegistration) {
        synchronized (this.globalEventRegistrations) {
            List list = (List) this.globalEventRegistrations.get(eventRegistration);
            int i = 0;
            if (list != null) {
                int i2 = 0;
                while (true) {
                    if (i2 >= list.size()) {
                        break;
                    } else if (list.get(i2) == eventRegistration) {
                        list.remove(i2);
                        break;
                    } else {
                        i2++;
                    }
                }
                if (list.isEmpty()) {
                    this.globalEventRegistrations.remove(eventRegistration);
                }
            }
            if (!eventRegistration.getQuerySpec().isDefault()) {
                EventRegistration clone = eventRegistration.clone(QuerySpec.defaultQueryAtPath(eventRegistration.getQuerySpec().getPath()));
                List list2 = (List) this.globalEventRegistrations.get(clone);
                if (list2 != null) {
                    while (true) {
                        if (i >= list2.size()) {
                            break;
                        } else if (list2.get(i) == eventRegistration) {
                            list2.remove(i);
                            break;
                        } else {
                            i++;
                        }
                    }
                    if (list2.isEmpty()) {
                        this.globalEventRegistrations.remove(clone);
                    }
                }
            }
        }
    }

    public void zombifyForRemove(EventRegistration eventRegistration) {
        synchronized (this.globalEventRegistrations) {
            List list = (List) this.globalEventRegistrations.get(eventRegistration);
            if (list != null && !list.isEmpty()) {
                if (eventRegistration.getQuerySpec().isDefault()) {
                    HashSet hashSet = new HashSet();
                    for (int size = list.size() - 1; size >= 0; size--) {
                        EventRegistration eventRegistration2 = (EventRegistration) list.get(size);
                        if (!hashSet.contains(eventRegistration2.getQuerySpec())) {
                            hashSet.add(eventRegistration2.getQuerySpec());
                            eventRegistration2.zombify();
                        }
                    }
                } else {
                    ((EventRegistration) list.get(0)).zombify();
                }
            }
        }
    }

    public void onZombied(EventRegistration eventRegistration) {
        unRecordEventRegistration(eventRegistration);
    }
}
