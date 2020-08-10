package p043io.fabric.sdk.android.services.events;

/* renamed from: io.fabric.sdk.android.services.events.DisabledEventsStrategy */
public class DisabledEventsStrategy<T> implements EventsStrategy<T> {
    public void cancelTimeBasedFileRollOver() {
    }

    public void deleteAllEvents() {
    }

    public FilesSender getFilesSender() {
        return null;
    }

    public void recordEvent(T t) {
    }

    public boolean rollFileOver() {
        return false;
    }

    public void scheduleTimeBasedRollOverIfNeeded() {
    }

    public void sendEvents() {
    }
}
