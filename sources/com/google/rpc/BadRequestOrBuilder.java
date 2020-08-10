package com.google.rpc;

import com.google.protobuf.MessageLiteOrBuilder;
import com.google.rpc.BadRequest.FieldViolation;
import java.util.List;

/* compiled from: com.google.firebase:protolite-well-known-types@@16.0.1 */
public interface BadRequestOrBuilder extends MessageLiteOrBuilder {
    FieldViolation getFieldViolations(int i);

    int getFieldViolationsCount();

    List<FieldViolation> getFieldViolationsList();
}
