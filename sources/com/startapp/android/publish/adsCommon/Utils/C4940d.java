package com.startapp.android.publish.adsCommon.Utils;

import com.startapp.common.C5186e;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import kotlin.text.Typography;
import org.objectweb.asm.signature.SignatureVisitor;
import org.slf4j.Marker;

/* renamed from: com.startapp.android.publish.adsCommon.Utils.d */
/* compiled from: StartAppSDK */
public class C4940d extends C4941e {

    /* renamed from: a */
    private List<NameValueObject> f3047a;

    public C4940d() {
        this.f3047a = null;
        this.f3047a = new ArrayList();
    }

    /* renamed from: a */
    public void mo62026a(String str, Object obj, boolean z, boolean z2) {
        if (z && obj == null) {
            StringBuilder sb = new StringBuilder();
            sb.append("Required key: [");
            sb.append(str);
            sb.append("] is missing");
            throw new C5186e(sb.toString(), null);
        } else if (obj != null && !obj.toString().equals("")) {
            try {
                NameValueObject nameValueObject = new NameValueObject();
                nameValueObject.setName(str);
                String obj2 = obj.toString();
                if (z2) {
                    obj2 = URLEncoder.encode(obj2, "UTF-8");
                }
                nameValueObject.setValue(obj2);
                this.f3047a.add(nameValueObject);
            } catch (UnsupportedEncodingException e) {
                if (z) {
                    StringBuilder sb2 = new StringBuilder();
                    sb2.append("failed encoding value: [");
                    sb2.append(obj);
                    sb2.append("]");
                    throw new C5186e(sb2.toString(), e);
                }
            }
        }
    }

    /* renamed from: a */
    public void mo62027a(String str, Set<String> set, boolean z, boolean z2) {
        if (z && set == null) {
            StringBuilder sb = new StringBuilder();
            sb.append("Required key: [");
            sb.append(str);
            sb.append("] is missing");
            throw new C5186e(sb.toString(), null);
        } else if (set != null) {
            NameValueObject nameValueObject = new NameValueObject();
            nameValueObject.setName(str);
            HashSet hashSet = new HashSet();
            for (String str2 : set) {
                if (z2) {
                    try {
                        str2 = URLEncoder.encode(str2, "UTF-8");
                    } catch (UnsupportedEncodingException unused) {
                    }
                }
                hashSet.add(str2);
            }
            if (!z || hashSet.size() != 0) {
                nameValueObject.setValueSet(hashSet);
                this.f3047a.add(nameValueObject);
                return;
            }
            StringBuilder sb2 = new StringBuilder();
            sb2.append("failed encoding value: [");
            sb2.append(set);
            sb2.append("]");
            throw new C5186e(sb2.toString(), null);
        }
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        if (this.f3047a == null) {
            return sb.toString();
        }
        sb.append('?');
        for (NameValueObject nameValueObject : this.f3047a) {
            if (nameValueObject.getValue() != null) {
                sb.append(nameValueObject.getName());
                sb.append(SignatureVisitor.INSTANCEOF);
                sb.append(nameValueObject.getValue());
                sb.append(Typography.amp);
            } else if (nameValueObject.getValueSet() != null) {
                Set<String> valueSet = nameValueObject.getValueSet();
                if (valueSet != null) {
                    for (String str : valueSet) {
                        sb.append(nameValueObject.getName());
                        sb.append(SignatureVisitor.INSTANCEOF);
                        sb.append(str);
                        sb.append(Typography.amp);
                    }
                }
            }
        }
        if (sb.length() != 0) {
            sb.deleteCharAt(sb.length() - 1);
        }
        return sb.toString().replace(Marker.ANY_NON_NULL_MARKER, "%20");
    }
}
