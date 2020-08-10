package com.google.api;

import com.google.protobuf.DescriptorProtos.MethodOptions;
import com.google.protobuf.ExtensionRegistryLite;
import com.google.protobuf.GeneratedMessageLite;
import com.google.protobuf.GeneratedMessageLite.GeneratedExtension;
import com.google.protobuf.WireFormat.FieldType;

/* compiled from: com.google.firebase:protolite-well-known-types@@16.0.1 */
public final class AnnotationsProto {
    public static final int HTTP_FIELD_NUMBER = 72295728;
    public static final GeneratedExtension<MethodOptions, HttpRule> http = GeneratedMessageLite.newSingularGeneratedExtension(MethodOptions.getDefaultInstance(), HttpRule.getDefaultInstance(), HttpRule.getDefaultInstance(), null, HTTP_FIELD_NUMBER, FieldType.MESSAGE, HttpRule.class);

    private AnnotationsProto() {
    }

    public static void registerAllExtensions(ExtensionRegistryLite extensionRegistryLite) {
        extensionRegistryLite.add(http);
    }
}
