package p043io.fabric.sdk.android.services.concurrency;

/* renamed from: io.fabric.sdk.android.services.concurrency.DelegateProvider */
public interface DelegateProvider {
    <T extends Dependency<Task> & PriorityProvider & Task> T getDelegate();
}
