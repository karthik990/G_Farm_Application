package com.mobiroller.util.validation;

import android.content.Context;
import com.mobiroller.util.validation.annotations.LocalizedName;
import com.mobiroller.util.validation.annotations.Required;
import com.mobiroller.util.validation.exceptions.RequiredFieldException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;

public class Validator {
    public static boolean validateForNulls(Object obj, Context context) throws RequiredFieldException, ClassNotFoundException, NoSuchFieldException, IllegalAccessException {
        Field[] declaredFields;
        for (Field field : obj.getClass().getDeclaredFields()) {
            Annotation annotation = field.getAnnotation(Required.class);
            if (annotation != null && ((Required) annotation).value()) {
                field.setAccessible(true);
                if (field.get(obj) == null) {
                    Annotation annotation2 = field.getAnnotation(LocalizedName.class);
                    if (annotation2 != null) {
                        throw new RequiredFieldException(((LocalizedName) annotation2).value(), context);
                    }
                    throw new RequiredFieldException(field.getName());
                }
            }
        }
        return true;
    }
}
