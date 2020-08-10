package com.airbnb.lottie.parser;

import com.airbnb.lottie.model.DocumentData;

public class DocumentDataParser implements ValueParser<DocumentData> {
    public static final DocumentDataParser INSTANCE = new DocumentDataParser();

    private DocumentDataParser() {
    }

    /* JADX WARNING: Removed duplicated region for block: B:60:0x00cd  */
    /* JADX WARNING: Removed duplicated region for block: B:61:0x00d2  */
    /* JADX WARNING: Removed duplicated region for block: B:62:0x00da  */
    /* JADX WARNING: Removed duplicated region for block: B:63:0x00e2  */
    /* JADX WARNING: Removed duplicated region for block: B:64:0x00ea  */
    /* JADX WARNING: Removed duplicated region for block: B:65:0x00f2  */
    /* JADX WARNING: Removed duplicated region for block: B:66:0x00f9  */
    /* JADX WARNING: Removed duplicated region for block: B:67:0x0100  */
    /* JADX WARNING: Removed duplicated region for block: B:68:0x0107  */
    /* JADX WARNING: Removed duplicated region for block: B:69:0x010e  */
    /* JADX WARNING: Removed duplicated region for block: B:70:0x0115  */
    /* JADX WARNING: Removed duplicated region for block: B:71:0x011c  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public com.airbnb.lottie.model.DocumentData parse(android.util.JsonReader r21, float r22) throws java.io.IOException {
        /*
            r20 = this;
            r21.beginObject()
            r0 = 1
            r1 = 0
            r2 = 0
            r4 = 0
            r6 = r1
            r7 = r6
            r8 = r2
            r12 = r8
            r14 = r12
            r10 = 0
            r11 = 0
            r16 = 0
            r17 = 0
            r18 = 0
            r19 = 1
        L_0x0017:
            boolean r1 = r21.hasNext()
            if (r1 == 0) goto L_0x0123
            java.lang.String r1 = r21.nextName()
            r2 = -1
            int r3 = r1.hashCode()
            r5 = 102(0x66, float:1.43E-43)
            if (r3 == r5) goto L_0x00bf
            r5 = 106(0x6a, float:1.49E-43)
            if (r3 == r5) goto L_0x00b5
            r5 = 3261(0xcbd, float:4.57E-42)
            if (r3 == r5) goto L_0x00ab
            r5 = 3452(0xd7c, float:4.837E-42)
            if (r3 == r5) goto L_0x00a1
            r5 = 3463(0xd87, float:4.853E-42)
            if (r3 == r5) goto L_0x0097
            r5 = 3543(0xdd7, float:4.965E-42)
            if (r3 == r5) goto L_0x008c
            r5 = 3664(0xe50, float:5.134E-42)
            if (r3 == r5) goto L_0x0081
            r5 = 3684(0xe64, float:5.162E-42)
            if (r3 == r5) goto L_0x0075
            r5 = 3710(0xe7e, float:5.199E-42)
            if (r3 == r5) goto L_0x006a
            r5 = 115(0x73, float:1.61E-43)
            if (r3 == r5) goto L_0x0060
            r5 = 116(0x74, float:1.63E-43)
            if (r3 == r5) goto L_0x0054
            goto L_0x00c9
        L_0x0054:
            java.lang.String r3 = "t"
            boolean r1 = r1.equals(r3)
            if (r1 == 0) goto L_0x00c9
            r1 = 0
            goto L_0x00ca
        L_0x0060:
            java.lang.String r3 = "s"
            boolean r1 = r1.equals(r3)
            if (r1 == 0) goto L_0x00c9
            r1 = 2
            goto L_0x00ca
        L_0x006a:
            java.lang.String r3 = "tr"
            boolean r1 = r1.equals(r3)
            if (r1 == 0) goto L_0x00c9
            r1 = 4
            goto L_0x00ca
        L_0x0075:
            java.lang.String r3 = "sw"
            boolean r1 = r1.equals(r3)
            if (r1 == 0) goto L_0x00c9
            r1 = 9
            goto L_0x00ca
        L_0x0081:
            java.lang.String r3 = "sc"
            boolean r1 = r1.equals(r3)
            if (r1 == 0) goto L_0x00c9
            r1 = 8
            goto L_0x00ca
        L_0x008c:
            java.lang.String r3 = "of"
            boolean r1 = r1.equals(r3)
            if (r1 == 0) goto L_0x00c9
            r1 = 10
            goto L_0x00ca
        L_0x0097:
            java.lang.String r3 = "ls"
            boolean r1 = r1.equals(r3)
            if (r1 == 0) goto L_0x00c9
            r1 = 6
            goto L_0x00ca
        L_0x00a1:
            java.lang.String r3 = "lh"
            boolean r1 = r1.equals(r3)
            if (r1 == 0) goto L_0x00c9
            r1 = 5
            goto L_0x00ca
        L_0x00ab:
            java.lang.String r3 = "fc"
            boolean r1 = r1.equals(r3)
            if (r1 == 0) goto L_0x00c9
            r1 = 7
            goto L_0x00ca
        L_0x00b5:
            java.lang.String r3 = "j"
            boolean r1 = r1.equals(r3)
            if (r1 == 0) goto L_0x00c9
            r1 = 3
            goto L_0x00ca
        L_0x00bf:
            java.lang.String r3 = "f"
            boolean r1 = r1.equals(r3)
            if (r1 == 0) goto L_0x00c9
            r1 = 1
            goto L_0x00ca
        L_0x00c9:
            r1 = -1
        L_0x00ca:
            switch(r1) {
                case 0: goto L_0x011c;
                case 1: goto L_0x0115;
                case 2: goto L_0x010e;
                case 3: goto L_0x0107;
                case 4: goto L_0x0100;
                case 5: goto L_0x00f9;
                case 6: goto L_0x00f2;
                case 7: goto L_0x00ea;
                case 8: goto L_0x00e2;
                case 9: goto L_0x00da;
                case 10: goto L_0x00d2;
                default: goto L_0x00cd;
            }
        L_0x00cd:
            r21.skipValue()
            goto L_0x0017
        L_0x00d2:
            boolean r1 = r21.nextBoolean()
            r19 = r1
            goto L_0x0017
        L_0x00da:
            int r1 = r21.nextInt()
            r18 = r1
            goto L_0x0017
        L_0x00e2:
            int r1 = com.airbnb.lottie.parser.JsonUtils.jsonToColor(r21)
            r17 = r1
            goto L_0x0017
        L_0x00ea:
            int r1 = com.airbnb.lottie.parser.JsonUtils.jsonToColor(r21)
            r16 = r1
            goto L_0x0017
        L_0x00f2:
            double r1 = r21.nextDouble()
            r14 = r1
            goto L_0x0017
        L_0x00f9:
            double r1 = r21.nextDouble()
            r12 = r1
            goto L_0x0017
        L_0x0100:
            int r1 = r21.nextInt()
            r11 = r1
            goto L_0x0017
        L_0x0107:
            int r1 = r21.nextInt()
            r10 = r1
            goto L_0x0017
        L_0x010e:
            double r1 = r21.nextDouble()
            r8 = r1
            goto L_0x0017
        L_0x0115:
            java.lang.String r1 = r21.nextString()
            r7 = r1
            goto L_0x0017
        L_0x011c:
            java.lang.String r1 = r21.nextString()
            r6 = r1
            goto L_0x0017
        L_0x0123:
            r21.endObject()
            com.airbnb.lottie.model.DocumentData r0 = new com.airbnb.lottie.model.DocumentData
            r5 = r0
            r5.<init>(r6, r7, r8, r10, r11, r12, r14, r16, r17, r18, r19)
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.airbnb.lottie.parser.DocumentDataParser.parse(android.util.JsonReader, float):com.airbnb.lottie.model.DocumentData");
    }
}
