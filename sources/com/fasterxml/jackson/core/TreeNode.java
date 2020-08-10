package com.fasterxml.jackson.core;

import com.fasterxml.jackson.core.JsonParser.NumberType;
import java.util.Iterator;

public interface TreeNode {
    JsonToken asToken();

    /* renamed from: at */
    TreeNode mo15089at(JsonPointer jsonPointer);

    /* renamed from: at */
    TreeNode mo15090at(String str) throws IllegalArgumentException;

    Iterator<String> fieldNames();

    TreeNode get(int i);

    TreeNode get(String str);

    boolean isArray();

    boolean isContainerNode();

    boolean isMissingNode();

    boolean isObject();

    boolean isValueNode();

    NumberType numberType();

    TreeNode path(int i);

    TreeNode path(String str);

    int size();

    JsonParser traverse();

    JsonParser traverse(ObjectCodec objectCodec);
}
