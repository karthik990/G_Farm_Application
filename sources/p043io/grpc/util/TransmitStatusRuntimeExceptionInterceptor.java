package p043io.grpc.util;

import com.google.common.util.concurrent.MoreExecutors;
import com.google.common.util.concurrent.SettableFuture;
import java.util.concurrent.ExecutionException;
import javax.annotation.Nullable;
import p043io.grpc.Attributes;
import p043io.grpc.ForwardingServerCall.SimpleForwardingServerCall;
import p043io.grpc.ForwardingServerCallListener.SimpleForwardingServerCallListener;
import p043io.grpc.Metadata;
import p043io.grpc.ServerCall;
import p043io.grpc.ServerCall.Listener;
import p043io.grpc.ServerCallHandler;
import p043io.grpc.ServerInterceptor;
import p043io.grpc.Status;
import p043io.grpc.StatusRuntimeException;
import p043io.grpc.internal.SerializingExecutor;

/* renamed from: io.grpc.util.TransmitStatusRuntimeExceptionInterceptor */
public final class TransmitStatusRuntimeExceptionInterceptor implements ServerInterceptor {

    /* renamed from: io.grpc.util.TransmitStatusRuntimeExceptionInterceptor$SerializingServerCall */
    private static class SerializingServerCall<ReqT, RespT> extends SimpleForwardingServerCall<ReqT, RespT> {
        private static final String ERROR_MSG = "Encountered error during serialized access";
        /* access modifiers changed from: private */
        public boolean closeCalled = false;
        private final SerializingExecutor serializingExecutor = new SerializingExecutor(MoreExecutors.directExecutor());

        SerializingServerCall(ServerCall<ReqT, RespT> serverCall) {
            super(serverCall);
        }

        public void sendMessage(final RespT respt) {
            this.serializingExecutor.execute(new Runnable() {
                public void run() {
                    SerializingServerCall.super.sendMessage(respt);
                }
            });
        }

        public void request(final int i) {
            this.serializingExecutor.execute(new Runnable() {
                public void run() {
                    SerializingServerCall.super.request(i);
                }
            });
        }

        public void sendHeaders(final Metadata metadata) {
            this.serializingExecutor.execute(new Runnable() {
                public void run() {
                    SerializingServerCall.super.sendHeaders(metadata);
                }
            });
        }

        public void close(final Status status, final Metadata metadata) {
            this.serializingExecutor.execute(new Runnable() {
                public void run() {
                    if (!SerializingServerCall.this.closeCalled) {
                        SerializingServerCall.this.closeCalled = true;
                        SerializingServerCall.super.close(status, metadata);
                    }
                }
            });
        }

        public boolean isReady() {
            String str = ERROR_MSG;
            final SettableFuture create = SettableFuture.create();
            this.serializingExecutor.execute(new Runnable() {
                public void run() {
                    create.set(Boolean.valueOf(SerializingServerCall.super.isReady()));
                }
            });
            try {
                return ((Boolean) create.get()).booleanValue();
            } catch (InterruptedException e) {
                throw new RuntimeException(str, e);
            } catch (ExecutionException e2) {
                throw new RuntimeException(str, e2);
            }
        }

        public boolean isCancelled() {
            String str = ERROR_MSG;
            final SettableFuture create = SettableFuture.create();
            this.serializingExecutor.execute(new Runnable() {
                public void run() {
                    create.set(Boolean.valueOf(SerializingServerCall.super.isCancelled()));
                }
            });
            try {
                return ((Boolean) create.get()).booleanValue();
            } catch (InterruptedException e) {
                throw new RuntimeException(str, e);
            } catch (ExecutionException e2) {
                throw new RuntimeException(str, e2);
            }
        }

        public void setMessageCompression(final boolean z) {
            this.serializingExecutor.execute(new Runnable() {
                public void run() {
                    SerializingServerCall.super.setMessageCompression(z);
                }
            });
        }

        public void setCompression(final String str) {
            this.serializingExecutor.execute(new Runnable() {
                public void run() {
                    SerializingServerCall.super.setCompression(str);
                }
            });
        }

        public Attributes getAttributes() {
            String str = ERROR_MSG;
            final SettableFuture create = SettableFuture.create();
            this.serializingExecutor.execute(new Runnable() {
                public void run() {
                    create.set(SerializingServerCall.super.getAttributes());
                }
            });
            try {
                return (Attributes) create.get();
            } catch (InterruptedException e) {
                throw new RuntimeException(str, e);
            } catch (ExecutionException e2) {
                throw new RuntimeException(str, e2);
            }
        }

        @Nullable
        public String getAuthority() {
            String str = ERROR_MSG;
            final SettableFuture create = SettableFuture.create();
            this.serializingExecutor.execute(new Runnable() {
                public void run() {
                    create.set(SerializingServerCall.super.getAuthority());
                }
            });
            try {
                return (String) create.get();
            } catch (InterruptedException e) {
                throw new RuntimeException(str, e);
            } catch (ExecutionException e2) {
                throw new RuntimeException(str, e2);
            }
        }
    }

    private TransmitStatusRuntimeExceptionInterceptor() {
    }

    public static ServerInterceptor instance() {
        return new TransmitStatusRuntimeExceptionInterceptor();
    }

    public <ReqT, RespT> Listener<ReqT> interceptCall(ServerCall<ReqT, RespT> serverCall, Metadata metadata, ServerCallHandler<ReqT, RespT> serverCallHandler) {
        final SerializingServerCall serializingServerCall = new SerializingServerCall(serverCall);
        return new SimpleForwardingServerCallListener<ReqT>(serverCallHandler.startCall(serializingServerCall, metadata)) {
            public void onMessage(ReqT reqt) {
                try {
                    super.onMessage(reqt);
                } catch (StatusRuntimeException e) {
                    closeWithException(e);
                }
            }

            public void onHalfClose() {
                try {
                    super.onHalfClose();
                } catch (StatusRuntimeException e) {
                    closeWithException(e);
                }
            }

            public void onCancel() {
                try {
                    super.onCancel();
                } catch (StatusRuntimeException e) {
                    closeWithException(e);
                }
            }

            public void onComplete() {
                try {
                    super.onComplete();
                } catch (StatusRuntimeException e) {
                    closeWithException(e);
                }
            }

            public void onReady() {
                try {
                    super.onReady();
                } catch (StatusRuntimeException e) {
                    closeWithException(e);
                }
            }

            private void closeWithException(StatusRuntimeException statusRuntimeException) {
                Metadata trailers = statusRuntimeException.getTrailers();
                if (trailers == null) {
                    trailers = new Metadata();
                }
                serializingServerCall.close(statusRuntimeException.getStatus(), trailers);
            }
        };
    }
}
