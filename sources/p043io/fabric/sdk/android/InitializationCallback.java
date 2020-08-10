package p043io.fabric.sdk.android;

/* renamed from: io.fabric.sdk.android.InitializationCallback */
public interface InitializationCallback<T> {
    public static final InitializationCallback EMPTY = new Empty();

    /* renamed from: io.fabric.sdk.android.InitializationCallback$Empty */
    public static class Empty implements InitializationCallback<Object> {
        public void failure(Exception exc) {
        }

        public void success(Object obj) {
        }

        private Empty() {
        }
    }

    void failure(Exception exc);

    void success(T t);
}
