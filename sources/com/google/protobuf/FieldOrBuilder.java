package com.google.protobuf;

import com.google.protobuf.Field.Cardinality;
import com.google.protobuf.Field.Kind;
import java.util.List;

/* compiled from: com.google.firebase:protolite-well-known-types@@16.0.1 */
public interface FieldOrBuilder extends MessageLiteOrBuilder {
    Cardinality getCardinality();

    int getCardinalityValue();

    String getDefaultValue();

    ByteString getDefaultValueBytes();

    String getJsonName();

    ByteString getJsonNameBytes();

    Kind getKind();

    int getKindValue();

    String getName();

    ByteString getNameBytes();

    int getNumber();

    int getOneofIndex();

    Option getOptions(int i);

    int getOptionsCount();

    List<Option> getOptionsList();

    boolean getPacked();

    String getTypeUrl();

    ByteString getTypeUrlBytes();
}
