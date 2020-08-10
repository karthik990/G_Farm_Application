package com.mobiroller.helpers;

import android.net.Uri;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import java.lang.reflect.Type;

public class UriTypeHierarchyAdapter implements JsonDeserializer<Uri>, JsonSerializer<Uri> {
    public Uri deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
        return Uri.parse(jsonElement.getAsString());
    }

    public JsonElement serialize(Uri uri, Type type, JsonSerializationContext jsonSerializationContext) {
        return new JsonPrimitive(uri.toString());
    }
}
