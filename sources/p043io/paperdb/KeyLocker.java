package p043io.paperdb;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.Semaphore;

/* renamed from: io.paperdb.KeyLocker */
class KeyLocker {
    private ConcurrentMap<String, Semaphore> semaphoreMap = new ConcurrentHashMap();

    KeyLocker() {
    }

    /* access modifiers changed from: 0000 */
    public void acquire(String str) {
        if (str != null) {
            if (!this.semaphoreMap.containsKey(str)) {
                this.semaphoreMap.put(str, new Semaphore(1, true));
            }
            ((Semaphore) this.semaphoreMap.get(str)).acquireUninterruptibly();
            return;
        }
        throw new IllegalArgumentException("Key couldn't be null");
    }

    /* access modifiers changed from: 0000 */
    public void release(String str) {
        if (str != null) {
            Semaphore semaphore = (Semaphore) this.semaphoreMap.get(str);
            if (semaphore != null) {
                semaphore.release();
                return;
            }
            StringBuilder sb = new StringBuilder();
            sb.append("Couldn't release semaphore. The acquire() with the same key '");
            sb.append(str);
            sb.append("' has to be called prior to calling release()");
            throw new IllegalStateException(sb.toString());
        }
        throw new IllegalArgumentException("Key couldn't be null");
    }
}
