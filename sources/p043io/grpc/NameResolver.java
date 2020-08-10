package p043io.grpc;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.net.URI;
import java.util.List;
import javax.annotation.Nullable;
import p043io.grpc.Attributes.Key;

/* renamed from: io.grpc.NameResolver */
public abstract class NameResolver {

    @Documented
    @Retention(RetentionPolicy.SOURCE)
    /* renamed from: io.grpc.NameResolver$ResolutionResultAttr */
    public @interface ResolutionResultAttr {
    }

    /* renamed from: io.grpc.NameResolver$Factory */
    public static abstract class Factory {
        public static final Key<Integer> PARAMS_DEFAULT_PORT = Key.create("params-default-port");

        public abstract String getDefaultScheme();

        @Nullable
        public abstract NameResolver newNameResolver(URI uri, Attributes attributes);
    }

    /* renamed from: io.grpc.NameResolver$Listener */
    public interface Listener {
        void onAddresses(List<EquivalentAddressGroup> list, Attributes attributes);

        void onError(Status status);
    }

    public abstract String getServiceAuthority();

    public void refresh() {
    }

    public abstract void shutdown();

    public abstract void start(Listener listener);
}
