package org.jdom2.output.support;

import java.util.List;
import org.jdom2.Content;
import org.jdom2.Content.CType;
import org.jdom2.Verifier;

public class WalkerNORMALIZE extends AbstractFormattedWalker {

    /* renamed from: org.jdom2.output.support.WalkerNORMALIZE$1 */
    static /* synthetic */ class C61461 {
        static final /* synthetic */ int[] $SwitchMap$org$jdom2$Content$CType = new int[CType.values().length];

        /* JADX WARNING: Can't wrap try/catch for region: R(8:0|1|2|3|4|5|6|8) */
        /* JADX WARNING: Failed to process nested try/catch */
        /* JADX WARNING: Missing exception handler attribute for start block: B:3:0x0014 */
        /* JADX WARNING: Missing exception handler attribute for start block: B:5:0x001f */
        static {
            /*
                org.jdom2.Content$CType[] r0 = org.jdom2.Content.CType.values()
                int r0 = r0.length
                int[] r0 = new int[r0]
                $SwitchMap$org$jdom2$Content$CType = r0
                int[] r0 = $SwitchMap$org$jdom2$Content$CType     // Catch:{ NoSuchFieldError -> 0x0014 }
                org.jdom2.Content$CType r1 = org.jdom2.Content.CType.Text     // Catch:{ NoSuchFieldError -> 0x0014 }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x0014 }
                r2 = 1
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x0014 }
            L_0x0014:
                int[] r0 = $SwitchMap$org$jdom2$Content$CType     // Catch:{ NoSuchFieldError -> 0x001f }
                org.jdom2.Content$CType r1 = org.jdom2.Content.CType.CDATA     // Catch:{ NoSuchFieldError -> 0x001f }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x001f }
                r2 = 2
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x001f }
            L_0x001f:
                int[] r0 = $SwitchMap$org$jdom2$Content$CType     // Catch:{ NoSuchFieldError -> 0x002a }
                org.jdom2.Content$CType r1 = org.jdom2.Content.CType.EntityRef     // Catch:{ NoSuchFieldError -> 0x002a }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x002a }
                r2 = 3
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x002a }
            L_0x002a:
                return
            */
            throw new UnsupportedOperationException("Method not decompiled: org.jdom2.output.support.WalkerNORMALIZE.C61461.<clinit>():void");
        }
    }

    public WalkerNORMALIZE(List<? extends Content> list, FormatStack formatStack, boolean z) {
        super(list, formatStack, z);
    }

    private boolean isSpaceFirst(String str) {
        if (str.length() > 0) {
            return Verifier.isXMLWhitespace(str.charAt(0));
        }
        return false;
    }

    private boolean isSpaceLast(String str) {
        int length = str.length();
        if (length <= 0 || !Verifier.isXMLWhitespace(str.charAt(length - 1))) {
            return false;
        }
        return true;
    }

    /* access modifiers changed from: protected */
    public void analyzeMultiText(MultiText multiText, int i, int i2) {
        boolean z;
        boolean z2 = false;
        boolean z3 = false;
        for (int i3 = 0; i3 < i2; i3++) {
            Content content = get(i + i3);
            int i4 = C61461.$SwitchMap$org$jdom2$Content$CType[content.getCType().ordinal()];
            String str = " ";
            if (i4 != 1) {
                if (i4 != 2) {
                    if (z2 && z3) {
                        multiText.appendText(Trim.NONE, str);
                    }
                    multiText.appendRaw(content);
                    z2 = true;
                    z3 = false;
                } else {
                    String value = content.getValue();
                    if (!Verifier.isAllXMLWhitespace(value)) {
                        if (z2 && (z3 || isSpaceFirst(value))) {
                            multiText.appendText(Trim.NONE, str);
                        }
                        multiText.appendCDATA(Trim.COMPACT, value);
                        z = isSpaceLast(value);
                        z3 = z;
                        z2 = true;
                    } else if (z2) {
                        if (value.length() <= 0) {
                        }
                    }
                }
            } else {
                String value2 = content.getValue();
                if (Verifier.isAllXMLWhitespace(value2)) {
                    if (z2) {
                        if (value2.length() <= 0) {
                        }
                    }
                } else {
                    if (z2 && (z3 || isSpaceFirst(value2))) {
                        multiText.appendText(Trim.NONE, str);
                    }
                    multiText.appendText(Trim.COMPACT, value2);
                    z = isSpaceLast(value2);
                    z3 = z;
                    z2 = true;
                }
            }
            z3 = true;
        }
    }
}
