package p043io.fabric.sdk.android.services.events;

/* renamed from: io.fabric.sdk.android.services.events.EventsStrategy */
public interface EventsStrategy<T> extends FileRollOverManager, EventsManager<T> {
    FilesSender getFilesSender();
}
