package com.twitter.sdk.android.core.models;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.Set;

public class BindingValuesAdapter implements JsonSerializer<BindingValues>, JsonDeserializer<BindingValues> {
    private static final String BOOLEAN_MEMBER = "boolean_value";
    private static final String BOOLEAN_TYPE = "BOOLEAN";
    private static final String IMAGE_TYPE = "IMAGE";
    private static final String IMAGE_VALUE_MEMBER = "image_value";
    private static final String STRING_TYPE = "STRING";
    private static final String TYPE_MEMBER = "type";
    private static final String TYPE_VALUE_MEMBER = "string_value";
    private static final String USER_TYPE = "USER";
    private static final String USER_VALUE_MEMBER = "user_value";

    public JsonElement serialize(BindingValues bindingValues, Type type, JsonSerializationContext jsonSerializationContext) {
        return null;
    }

    public BindingValues deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
        if (!jsonElement.isJsonObject()) {
            return new BindingValues();
        }
        Set<Entry> entrySet = jsonElement.getAsJsonObject().entrySet();
        HashMap hashMap = new HashMap(32);
        for (Entry entry : entrySet) {
            hashMap.put((String) entry.getKey(), getValue(((JsonElement) entry.getValue()).getAsJsonObject(), jsonDeserializationContext));
        }
        return new BindingValues(hashMap);
    }

    /* access modifiers changed from: 0000 */
    public Object getValue(JsonObject jsonObject, JsonDeserializationContext jsonDeserializationContext) {
        JsonElement jsonElement = jsonObject.get("type");
        if (jsonElement == null || !jsonElement.isJsonPrimitive()) {
            return null;
        }
        String asString = jsonElement.getAsString();
        char c = 65535;
        switch (asString.hashCode()) {
            case -1838656495:
                if (asString.equals(STRING_TYPE)) {
                    c = 0;
                    break;
                }
                break;
            case 2614219:
                if (asString.equals(USER_TYPE)) {
                    c = 2;
                    break;
                }
                break;
            case 69775675:
                if (asString.equals(IMAGE_TYPE)) {
                    c = 1;
                    break;
                }
                break;
            case 782694408:
                if (asString.equals(BOOLEAN_TYPE)) {
                    c = 3;
                    break;
                }
                break;
        }
        if (c == 0) {
            return jsonDeserializationContext.deserialize(jsonObject.get(TYPE_VALUE_MEMBER), String.class);
        }
        if (c == 1) {
            return jsonDeserializationContext.deserialize(jsonObject.get(IMAGE_VALUE_MEMBER), ImageValue.class);
        }
        if (c == 2) {
            return jsonDeserializationContext.deserialize(jsonObject.get(USER_VALUE_MEMBER), UserValue.class);
        }
        if (c != 3) {
            return null;
        }
        return jsonDeserializationContext.deserialize(jsonObject.get(BOOLEAN_MEMBER), Boolean.class);
    }
}
