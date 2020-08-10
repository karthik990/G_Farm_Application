package p043io.grpc.stub.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import p043io.grpc.MethodDescriptor.MethodType;

@Target({ElementType.METHOD})
@Retention(RetentionPolicy.CLASS)
/* renamed from: io.grpc.stub.annotations.RpcMethod */
public @interface RpcMethod {
    String fullMethodName();

    MethodType methodType();

    Class<?> requestType();

    Class<?> responseType();
}
