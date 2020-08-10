package com.braintreepayments.api.internal;

import android.content.Context;
import android.content.res.Resources.NotFoundException;
import java.io.IOException;
import java.io.InputStream;

public class GraphQLQueryHelper {
    public static String getQuery(Context context, int i) throws NotFoundException, IOException {
        InputStream inputStream = null;
        try {
            inputStream = context.getResources().openRawResource(i);
            return StreamHelper.getString(inputStream);
        } finally {
            if (inputStream != null) {
                inputStream.close();
            }
        }
    }
}
