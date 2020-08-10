package p043io.grpc;

import com.google.common.base.Preconditions;
import java.io.BufferedInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import p043io.grpc.MethodDescriptor.Marshaller;
import p043io.grpc.ServerCall.Listener;
import p043io.grpc.ServerServiceDefinition.Builder;

/* renamed from: io.grpc.ServerInterceptors */
public final class ServerInterceptors {

    /* renamed from: io.grpc.ServerInterceptors$InterceptCallHandler */
    static final class InterceptCallHandler<ReqT, RespT> implements ServerCallHandler<ReqT, RespT> {
        private final ServerCallHandler<ReqT, RespT> callHandler;
        private final ServerInterceptor interceptor;

        public static <ReqT, RespT> InterceptCallHandler<ReqT, RespT> create(ServerInterceptor serverInterceptor, ServerCallHandler<ReqT, RespT> serverCallHandler) {
            return new InterceptCallHandler<>(serverInterceptor, serverCallHandler);
        }

        private InterceptCallHandler(ServerInterceptor serverInterceptor, ServerCallHandler<ReqT, RespT> serverCallHandler) {
            this.interceptor = (ServerInterceptor) Preconditions.checkNotNull(serverInterceptor, "interceptor");
            this.callHandler = serverCallHandler;
        }

        public Listener<ReqT> startCall(ServerCall<ReqT, RespT> serverCall, Metadata metadata) {
            return this.interceptor.interceptCall(serverCall, metadata, this.callHandler);
        }
    }

    private ServerInterceptors() {
    }

    public static ServerServiceDefinition interceptForward(ServerServiceDefinition serverServiceDefinition, ServerInterceptor... serverInterceptorArr) {
        return interceptForward(serverServiceDefinition, Arrays.asList(serverInterceptorArr));
    }

    public static ServerServiceDefinition interceptForward(BindableService bindableService, ServerInterceptor... serverInterceptorArr) {
        return interceptForward(bindableService.bindService(), Arrays.asList(serverInterceptorArr));
    }

    public static ServerServiceDefinition interceptForward(ServerServiceDefinition serverServiceDefinition, List<? extends ServerInterceptor> list) {
        ArrayList arrayList = new ArrayList(list);
        Collections.reverse(arrayList);
        return intercept(serverServiceDefinition, (List<? extends ServerInterceptor>) arrayList);
    }

    public static ServerServiceDefinition interceptForward(BindableService bindableService, List<? extends ServerInterceptor> list) {
        return interceptForward(bindableService.bindService(), list);
    }

    public static ServerServiceDefinition intercept(ServerServiceDefinition serverServiceDefinition, ServerInterceptor... serverInterceptorArr) {
        return intercept(serverServiceDefinition, Arrays.asList(serverInterceptorArr));
    }

    public static ServerServiceDefinition intercept(BindableService bindableService, ServerInterceptor... serverInterceptorArr) {
        Preconditions.checkNotNull(bindableService, "bindableService");
        return intercept(bindableService.bindService(), Arrays.asList(serverInterceptorArr));
    }

    public static ServerServiceDefinition intercept(ServerServiceDefinition serverServiceDefinition, List<? extends ServerInterceptor> list) {
        Preconditions.checkNotNull(serverServiceDefinition, "serviceDef");
        if (list.isEmpty()) {
            return serverServiceDefinition;
        }
        Builder builder = ServerServiceDefinition.builder(serverServiceDefinition.getServiceDescriptor());
        for (ServerMethodDefinition wrapAndAddMethod : serverServiceDefinition.getMethods()) {
            wrapAndAddMethod(builder, wrapAndAddMethod, list);
        }
        return builder.build();
    }

    public static ServerServiceDefinition intercept(BindableService bindableService, List<? extends ServerInterceptor> list) {
        Preconditions.checkNotNull(bindableService, "bindableService");
        return intercept(bindableService.bindService(), list);
    }

    public static ServerServiceDefinition useInputStreamMessages(ServerServiceDefinition serverServiceDefinition) {
        return useMarshalledMessages(serverServiceDefinition, new Marshaller<InputStream>() {
            public InputStream stream(InputStream inputStream) {
                return inputStream;
            }

            public InputStream parse(InputStream inputStream) {
                if (inputStream.markSupported()) {
                    return inputStream;
                }
                return new BufferedInputStream(inputStream);
            }
        });
    }

    public static <T> ServerServiceDefinition useMarshalledMessages(ServerServiceDefinition serverServiceDefinition, Marshaller<T> marshaller) {
        ArrayList<ServerMethodDefinition> arrayList = new ArrayList<>();
        ArrayList arrayList2 = new ArrayList();
        for (ServerMethodDefinition serverMethodDefinition : serverServiceDefinition.getMethods()) {
            MethodDescriptor build = serverMethodDefinition.getMethodDescriptor().toBuilder(marshaller, marshaller).build();
            arrayList2.add(build);
            arrayList.add(wrapMethod(serverMethodDefinition, build));
        }
        Builder builder = ServerServiceDefinition.builder(new ServiceDescriptor(serverServiceDefinition.getServiceDescriptor().getName(), (Collection<MethodDescriptor<?, ?>>) arrayList2));
        for (ServerMethodDefinition addMethod : arrayList) {
            builder.addMethod(addMethod);
        }
        return builder.build();
    }

    private static <ReqT, RespT> void wrapAndAddMethod(Builder builder, ServerMethodDefinition<ReqT, RespT> serverMethodDefinition, List<? extends ServerInterceptor> list) {
        ServerCallHandler serverCallHandler = serverMethodDefinition.getServerCallHandler();
        for (ServerInterceptor create : list) {
            serverCallHandler = InterceptCallHandler.create(create, serverCallHandler);
        }
        builder.addMethod(serverMethodDefinition.withServerCallHandler(serverCallHandler));
    }

    static <OReqT, ORespT, WReqT, WRespT> ServerMethodDefinition<WReqT, WRespT> wrapMethod(ServerMethodDefinition<OReqT, ORespT> serverMethodDefinition, MethodDescriptor<WReqT, WRespT> methodDescriptor) {
        return ServerMethodDefinition.create(methodDescriptor, wrapHandler(serverMethodDefinition.getServerCallHandler(), serverMethodDefinition.getMethodDescriptor(), methodDescriptor));
    }

    private static <OReqT, ORespT, WReqT, WRespT> ServerCallHandler<WReqT, WRespT> wrapHandler(final ServerCallHandler<OReqT, ORespT> serverCallHandler, final MethodDescriptor<OReqT, ORespT> methodDescriptor, final MethodDescriptor<WReqT, WRespT> methodDescriptor2) {
        return new ServerCallHandler<WReqT, WRespT>() {
            public Listener<WReqT> startCall(final ServerCall<WReqT, WRespT> serverCall, Metadata metadata) {
                final Listener startCall = serverCallHandler.startCall(new PartialForwardingServerCall<OReqT, ORespT>() {
                    /* access modifiers changed from: protected */
                    public ServerCall<WReqT, WRespT> delegate() {
                        return serverCall;
                    }

                    public void sendMessage(ORespT orespt) {
                        delegate().sendMessage(methodDescriptor2.parseResponse(MethodDescriptor.this.streamResponse(orespt)));
                    }

                    public MethodDescriptor<OReqT, ORespT> getMethodDescriptor() {
                        return MethodDescriptor.this;
                    }
                }, metadata);
                return new PartialForwardingServerCallListener<WReqT>() {
                    /* access modifiers changed from: protected */
                    public Listener<OReqT> delegate() {
                        return startCall;
                    }

                    public void onMessage(WReqT wreqt) {
                        delegate().onMessage(MethodDescriptor.this.parseRequest(methodDescriptor2.streamRequest(wreqt)));
                    }
                };
            }
        };
    }
}
