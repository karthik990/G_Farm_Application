package p043io.grpc.internal;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.Nullable;
import p043io.grpc.HandlerRegistry;
import p043io.grpc.ServerMethodDefinition;
import p043io.grpc.ServerServiceDefinition;

/* renamed from: io.grpc.internal.InternalHandlerRegistry */
final class InternalHandlerRegistry extends HandlerRegistry {
    private final Map<String, ServerMethodDefinition<?, ?>> methods;
    private final List<ServerServiceDefinition> services;

    /* renamed from: io.grpc.internal.InternalHandlerRegistry$Builder */
    static final class Builder {
        private final HashMap<String, ServerServiceDefinition> services = new LinkedHashMap();

        Builder() {
        }

        /* access modifiers changed from: 0000 */
        public Builder addService(ServerServiceDefinition serverServiceDefinition) {
            this.services.put(serverServiceDefinition.getServiceDescriptor().getName(), serverServiceDefinition);
            return this;
        }

        /* access modifiers changed from: 0000 */
        public InternalHandlerRegistry build() {
            HashMap hashMap = new HashMap();
            for (ServerServiceDefinition methods : this.services.values()) {
                for (ServerMethodDefinition serverMethodDefinition : methods.getMethods()) {
                    hashMap.put(serverMethodDefinition.getMethodDescriptor().getFullMethodName(), serverMethodDefinition);
                }
            }
            return new InternalHandlerRegistry(Collections.unmodifiableList(new ArrayList(this.services.values())), Collections.unmodifiableMap(hashMap));
        }
    }

    private InternalHandlerRegistry(List<ServerServiceDefinition> list, Map<String, ServerMethodDefinition<?, ?>> map) {
        this.services = list;
        this.methods = map;
    }

    public List<ServerServiceDefinition> getServices() {
        return this.services;
    }

    @Nullable
    public ServerMethodDefinition<?, ?> lookupMethod(String str, @Nullable String str2) {
        return (ServerMethodDefinition) this.methods.get(str);
    }
}
