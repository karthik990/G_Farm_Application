package p043io.grpc;

import java.util.List;

/* renamed from: io.grpc.InternalServiceProviders */
public final class InternalServiceProviders {

    /* renamed from: io.grpc.InternalServiceProviders$PriorityAccessor */
    public interface PriorityAccessor<T> extends p043io.grpc.ServiceProviders.PriorityAccessor<T> {
    }

    private InternalServiceProviders() {
    }

    public static <T> T load(Class<T> cls, Iterable<Class<?>> iterable, ClassLoader classLoader, PriorityAccessor<T> priorityAccessor) {
        return ServiceProviders.load(cls, iterable, classLoader, priorityAccessor);
    }

    public static <T> List<T> loadAll(Class<T> cls, Iterable<Class<?>> iterable, ClassLoader classLoader, PriorityAccessor<T> priorityAccessor) {
        return ServiceProviders.loadAll(cls, iterable, classLoader, priorityAccessor);
    }

    public static <T> Iterable<T> getCandidatesViaServiceLoader(Class<T> cls, ClassLoader classLoader) {
        return ServiceProviders.getCandidatesViaServiceLoader(cls, classLoader);
    }

    public static <T> Iterable<T> getCandidatesViaHardCoded(Class<T> cls, Iterable<Class<?>> iterable) {
        return ServiceProviders.getCandidatesViaHardCoded(cls, iterable);
    }

    public static boolean isAndroid(ClassLoader classLoader) {
        return ServiceProviders.isAndroid(classLoader);
    }
}
