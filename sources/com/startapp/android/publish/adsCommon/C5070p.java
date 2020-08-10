package com.startapp.android.publish.adsCommon;

import com.google.firebase.analytics.FirebaseAnalytics.Param;
import com.startapp.common.p092a.C5155g;
import java.io.StringReader;
import java.io.StringWriter;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.CharacterData;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

/* renamed from: com.startapp.android.publish.adsCommon.p */
/* compiled from: StartAppSDK */
public class C5070p {

    /* renamed from: a */
    private static String f3369a = "XmlTools";

    /* renamed from: a */
    public static String m3482a(Node node) {
        try {
            Transformer newTransformer = TransformerFactory.newInstance().newTransformer();
            newTransformer.setOutputProperty("omit-xml-declaration", "yes");
            newTransformer.setOutputProperty(Param.METHOD, "xml");
            newTransformer.setOutputProperty("indent", "no");
            newTransformer.setOutputProperty("encoding", "UTF-8");
            StringWriter stringWriter = new StringWriter();
            newTransformer.transform(new DOMSource(node), new StreamResult(stringWriter));
            return stringWriter.toString();
        } catch (Exception e) {
            C5155g.m3808a(f3369a, 6, "xmlDocumentToString", e);
            return null;
        }
    }

    /* renamed from: a */
    public static Document m3483a(String str) {
        try {
            DocumentBuilderFactory newInstance = DocumentBuilderFactory.newInstance();
            newInstance.setIgnoringElementContentWhitespace(true);
            newInstance.setIgnoringComments(true);
            DocumentBuilder newDocumentBuilder = newInstance.newDocumentBuilder();
            InputSource inputSource = new InputSource();
            inputSource.setCharacterStream(new StringReader(str));
            return newDocumentBuilder.parse(inputSource);
        } catch (Exception e) {
            C5155g.m3808a(f3369a, 6, "stringToDocument", e);
            return null;
        }
    }

    /* renamed from: b */
    public static String m3484b(Node node) {
        NodeList childNodes = node.getChildNodes();
        String str = null;
        for (int i = 0; i < childNodes.getLength(); i++) {
            str = ((CharacterData) childNodes.item(i)).getData().trim();
            if (str.length() != 0) {
                break;
            }
        }
        return str;
    }
}
