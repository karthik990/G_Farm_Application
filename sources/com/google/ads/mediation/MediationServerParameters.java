package com.google.ads.mediation;

import com.google.android.gms.internal.ads.zzane;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

@Deprecated
public class MediationServerParameters {

    public static final class MappingException extends Exception {
        public MappingException(String str) {
            super(str);
        }
    }

    @Target({ElementType.FIELD})
    @Retention(RetentionPolicy.RUNTIME)
    public @interface Parameter {
        String name();

        boolean required() default true;
    }

    public void load(Map<String, String> map) throws MappingException {
        Field[] fields;
        StringBuilder sb;
        String str;
        String str2 = "Server option \"";
        HashMap hashMap = new HashMap();
        for (Field field : getClass().getFields()) {
            Parameter parameter = (Parameter) field.getAnnotation(Parameter.class);
            if (parameter != null) {
                hashMap.put(parameter.name(), field);
            }
        }
        if (hashMap.isEmpty()) {
            zzane.zzdk("No server options fields detected. To suppress this message either add a field with the @Parameter annotation, or override the load() method.");
        }
        for (Entry entry : map.entrySet()) {
            Field field2 = (Field) hashMap.remove(entry.getKey());
            if (field2 != null) {
                try {
                    field2.set(this, entry.getValue());
                } catch (IllegalAccessException unused) {
                    String str3 = (String) entry.getKey();
                    sb = new StringBuilder(String.valueOf(str3).length() + 49);
                    sb.append(str2);
                    sb.append(str3);
                    str = "\" could not be set: Illegal Access";
                } catch (IllegalArgumentException unused2) {
                    String str4 = (String) entry.getKey();
                    sb = new StringBuilder(String.valueOf(str4).length() + 43);
                    sb.append(str2);
                    sb.append(str4);
                    str = "\" could not be set: Bad Type";
                }
            } else {
                String str5 = (String) entry.getKey();
                String str6 = (String) entry.getValue();
                StringBuilder sb2 = new StringBuilder(String.valueOf(str5).length() + 31 + String.valueOf(str6).length());
                sb2.append("Unexpected server option: ");
                sb2.append(str5);
                sb2.append(" = \"");
                sb2.append(str6);
                sb2.append("\"");
                zzane.zzck(sb2.toString());
            }
        }
        StringBuilder sb3 = new StringBuilder();
        for (Field field3 : hashMap.values()) {
            if (((Parameter) field3.getAnnotation(Parameter.class)).required()) {
                String str7 = "Required server option missing: ";
                String valueOf = String.valueOf(((Parameter) field3.getAnnotation(Parameter.class)).name());
                zzane.zzdk(valueOf.length() != 0 ? str7.concat(valueOf) : new String(str7));
                if (sb3.length() > 0) {
                    sb3.append(", ");
                }
                sb3.append(((Parameter) field3.getAnnotation(Parameter.class)).name());
            }
        }
        if (sb3.length() > 0) {
            String str8 = "Required server option(s) missing: ";
            String valueOf2 = String.valueOf(sb3.toString());
            throw new MappingException(valueOf2.length() != 0 ? str8.concat(valueOf2) : new String(str8));
        }
        return;
        sb.append(str);
        zzane.zzdk(sb.toString());
    }
}
