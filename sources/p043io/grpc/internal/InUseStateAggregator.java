package p043io.grpc.internal;

import java.util.HashSet;

/* renamed from: io.grpc.internal.InUseStateAggregator */
abstract class InUseStateAggregator<T> {
    private final HashSet<T> inUseObjects = new HashSet<>();

    /* access modifiers changed from: 0000 */
    public abstract void handleInUse();

    /* access modifiers changed from: 0000 */
    public abstract void handleNotInUse();

    InUseStateAggregator() {
    }

    /* access modifiers changed from: 0000 */
    public final void updateObjectInUse(T t, boolean z) {
        int size = this.inUseObjects.size();
        if (z) {
            this.inUseObjects.add(t);
            if (size == 0) {
                handleInUse();
            }
        } else if (this.inUseObjects.remove(t) && size == 1) {
            handleNotInUse();
        }
    }

    /* access modifiers changed from: 0000 */
    public final boolean isInUse() {
        return !this.inUseObjects.isEmpty();
    }
}
