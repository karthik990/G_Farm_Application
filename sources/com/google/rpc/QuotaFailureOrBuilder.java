package com.google.rpc;

import com.google.protobuf.MessageLiteOrBuilder;
import com.google.rpc.QuotaFailure.Violation;
import java.util.List;

/* compiled from: com.google.firebase:protolite-well-known-types@@16.0.1 */
public interface QuotaFailureOrBuilder extends MessageLiteOrBuilder {
    Violation getViolations(int i);

    int getViolationsCount();

    List<Violation> getViolationsList();
}
