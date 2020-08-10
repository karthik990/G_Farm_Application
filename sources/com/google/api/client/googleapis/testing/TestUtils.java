package com.google.api.client.googleapis.testing;

import com.google.common.base.Splitter;
import com.google.common.collect.Lists;
import java.io.IOException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import kotlin.text.Typography;
import org.objectweb.asm.signature.SignatureVisitor;

public final class TestUtils {
    private static final String UTF_8 = "UTF-8";

    public static Map<String, String> parseQuery(String str) throws IOException {
        HashMap hashMap = new HashMap();
        for (String split : Splitter.m1787on((char) Typography.amp).split(str)) {
            ArrayList newArrayList = Lists.newArrayList(Splitter.m1787on((char) SignatureVisitor.INSTANCEOF).split(split));
            if (newArrayList.size() == 2) {
                String str2 = "UTF-8";
                hashMap.put(URLDecoder.decode((String) newArrayList.get(0), str2), URLDecoder.decode((String) newArrayList.get(1), str2));
            } else {
                throw new IOException("Invalid Query String");
            }
        }
        return hashMap;
    }

    private TestUtils() {
    }
}
