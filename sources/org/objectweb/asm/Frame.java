package org.objectweb.asm;

import com.google.api.client.googleapis.media.MediaHttpDownloader;

final class Frame {

    /* renamed from: a */
    static final int[] f4649a;

    /* renamed from: b */
    Label f4650b;

    /* renamed from: c */
    int[] f4651c;

    /* renamed from: d */
    int[] f4652d;

    /* renamed from: e */
    private int[] f4653e;

    /* renamed from: f */
    private int[] f4654f;

    /* renamed from: g */
    private int f4655g;

    /* renamed from: h */
    private int f4656h;

    /* renamed from: i */
    private int[] f4657i;

    static {
        _clinit_();
        int[] iArr = new int[202];
        for (int i = 0; i < iArr.length; i++) {
            iArr[i] = "EFFFFFFFFGGFFFGGFFFEEFGFGFEEEEEEEEEEEEEEEEEEEEDEDEDDDDDCDCDEEEEEEEEEEEEEEEEEEEEBABABBBBDCFFFGGGEDCDCDCDCDCDCDCDCDCDCEEEEDDDDDDDCDCDCEFEFDDEEFFDEDEEEBDDBBDDDDDDCCCCCCCCEFEDDDCDCDEEEEEEEEEEFEEEEEEDDEEDDEE".charAt(i) - 'E';
        }
        f4649a = iArr;
    }

    Frame() {
    }

    static /* synthetic */ void _clinit_() {
    }

    /* renamed from: a */
    private int m4195a() {
        int i = this.f4655g;
        if (i > 0) {
            int[] iArr = this.f4654f;
            int i2 = i - 1;
            this.f4655g = i2;
            return iArr[i2];
        }
        Label label = this.f4650b;
        int i3 = label.f4682f - 1;
        label.f4682f = i3;
        return 50331648 | (-i3);
    }

    /* renamed from: a */
    private int m4196a(int i) {
        int[] iArr = this.f4653e;
        if (iArr == null || i >= iArr.length) {
            return i | MediaHttpDownloader.MAXIMUM_CHUNK_SIZE;
        }
        int i2 = iArr[i];
        if (i2 == 0) {
            i2 = i | MediaHttpDownloader.MAXIMUM_CHUNK_SIZE;
            iArr[i] = i2;
        }
        return i2;
    }

    /* JADX WARNING: Removed duplicated region for block: B:19:0x004d A[LOOP:0: B:8:0x0022->B:19:0x004d, LOOP_END] */
    /* JADX WARNING: Removed duplicated region for block: B:22:0x004c A[SYNTHETIC] */
    /* renamed from: a */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private int m4197a(org.objectweb.asm.ClassWriter r7, int r8) {
        /*
            r6 = this;
            r0 = 24117248(0x1700000, float:4.4081038E-38)
            r1 = 16777222(0x1000006, float:2.3509904E-38)
            if (r8 != r1) goto L_0x000f
            java.lang.String r1 = r7.f4582I
        L_0x0009:
            int r7 = r7.mo76756c(r1)
            r7 = r7 | r0
            goto L_0x0021
        L_0x000f:
            r1 = -1048576(0xfffffffffff00000, float:NaN)
            r1 = r1 & r8
            r2 = 25165824(0x1800000, float:4.7019774E-38)
            if (r1 != r2) goto L_0x0050
            org.objectweb.asm.Item[] r1 = r7.f4581H
            r2 = 1048575(0xfffff, float:1.469367E-39)
            r2 = r2 & r8
            r1 = r1[r2]
            java.lang.String r1 = r1.f4672g
            goto L_0x0009
        L_0x0021:
            r0 = 0
        L_0x0022:
            int r1 = r6.f4656h
            if (r0 >= r1) goto L_0x0050
            int[] r1 = r6.f4657i
            r1 = r1[r0]
            r2 = -268435456(0xfffffffff0000000, float:-1.58456325E29)
            r2 = r2 & r1
            r3 = 251658240(0xf000000, float:6.3108872E-30)
            r3 = r3 & r1
            r4 = 33554432(0x2000000, float:9.403955E-38)
            r5 = 8388607(0x7fffff, float:1.1754942E-38)
            if (r3 != r4) goto L_0x003e
            int[] r3 = r6.f4651c
            r1 = r1 & r5
            r1 = r3[r1]
        L_0x003c:
            int r1 = r1 + r2
            goto L_0x004a
        L_0x003e:
            r4 = 50331648(0x3000000, float:3.761582E-37)
            if (r3 != r4) goto L_0x004a
            int[] r3 = r6.f4652d
            int r4 = r3.length
            r1 = r1 & r5
            int r4 = r4 - r1
            r1 = r3[r4]
            goto L_0x003c
        L_0x004a:
            if (r8 != r1) goto L_0x004d
            return r7
        L_0x004d:
            int r0 = r0 + 1
            goto L_0x0022
        L_0x0050:
            return r8
        */
        throw new UnsupportedOperationException("Method not decompiled: org.objectweb.asm.Frame.m4197a(org.objectweb.asm.ClassWriter, int):int");
    }

    /* renamed from: a */
    private void m4198a(int i, int i2) {
        if (this.f4653e == null) {
            this.f4653e = new int[10];
        }
        int length = this.f4653e.length;
        if (i >= length) {
            int[] iArr = new int[Math.max(i + 1, length * 2)];
            System.arraycopy(this.f4653e, 0, iArr, 0, length);
            this.f4653e = iArr;
        }
        this.f4653e[i] = i2;
    }

    /* renamed from: a */
    private void m4199a(String str) {
        char charAt = str.charAt(0);
        if (charAt == '(') {
            m4204c((Type.getArgumentsAndReturnSizes(str) >> 2) - 1);
        } else if (charAt == 'J' || charAt == 'D') {
            m4204c(2);
        } else {
            m4204c(1);
        }
    }

    /* renamed from: a */
    private void m4200a(ClassWriter classWriter, String str) {
        int b = m4202b(classWriter, str);
        if (b != 0) {
            m4203b(b);
            if (b == 16777220 || b == 16777219) {
                m4203b(16777216);
            }
        }
    }

    /* renamed from: a */
    private static boolean m4201a(ClassWriter classWriter, int i, int[] iArr, int i2) {
        int min;
        int i3 = iArr[i2];
        if (i3 == i) {
            return false;
        }
        if ((268435455 & i) == 16777221) {
            if (i3 == 16777221) {
                return false;
            }
            i = 16777221;
        }
        if (i3 == 0) {
            iArr[i2] = i;
            return true;
        }
        int i4 = i3 & 267386880;
        int i5 = 16777216;
        int i6 = -268435456;
        if (i4 == 24117248 || (i3 & -268435456) != 0) {
            if (i == 16777221) {
                return false;
            }
            String str = "java/lang/Object";
            if ((i & -1048576) != (-1048576 & i3)) {
                int i7 = i & 267386880;
                if (i7 == 24117248 || (i & -268435456) != 0) {
                    int i8 = i & -268435456;
                    int i9 = ((i8 == 0 || i7 == 24117248) ? 0 : -268435456) + i8;
                    int i10 = i3 & -268435456;
                    if (i10 == 0 || i4 == 24117248) {
                        i6 = 0;
                    }
                    min = Math.min(i9, i6 + i10);
                }
            } else if (i4 == 24117248) {
                i5 = (i & -268435456) | 24117248 | classWriter.mo76743a(i & 1048575, 1048575 & i3);
            } else {
                min = (i3 & -268435456) - 268435456;
            }
            i5 = min | 24117248 | classWriter.mo76756c(str);
        } else if (i3 == 16777221 && ((i & 267386880) == 24117248 || (i & -268435456) != 0)) {
            i5 = i;
        }
        if (i3 == i5) {
            return false;
        }
        iArr[i2] = i5;
        return true;
    }

    /* renamed from: b */
    private static int m4202b(ClassWriter classWriter, String str) {
        int indexOf = str.charAt(0) == '(' ? str.indexOf(41) + 1 : 0;
        char charAt = str.charAt(indexOf);
        int i = 16777218;
        if (charAt == 'F') {
            return 16777218;
        }
        if (charAt == 'L') {
            return classWriter.mo76756c(str.substring(indexOf + 1, str.length() - 1)) | 24117248;
        }
        if (charAt != 'S') {
            if (charAt == 'V') {
                return 0;
            }
            if (!(charAt == 'Z' || charAt == 'I')) {
                if (charAt == 'J') {
                    return 16777220;
                }
                switch (charAt) {
                    case 'B':
                    case 'C':
                        break;
                    case 'D':
                        return 16777219;
                    default:
                        int i2 = indexOf + 1;
                        while (str.charAt(i2) == '[') {
                            i2++;
                        }
                        char charAt2 = str.charAt(i2);
                        if (charAt2 != 'F') {
                            if (charAt2 == 'S') {
                                i = 16777228;
                            } else if (charAt2 == 'Z') {
                                i = 16777225;
                            } else if (charAt2 == 'I') {
                                i = 16777217;
                            } else if (charAt2 != 'J') {
                                switch (charAt2) {
                                    case 'B':
                                        i = 16777226;
                                        break;
                                    case 'C':
                                        i = 16777227;
                                        break;
                                    case 'D':
                                        i = 16777219;
                                        break;
                                    default:
                                        i = classWriter.mo76756c(str.substring(i2 + 1, str.length() - 1)) | 24117248;
                                        break;
                                }
                            } else {
                                i = 16777220;
                            }
                        }
                        return ((i2 - indexOf) << 28) | i;
                }
            }
        }
        return 16777217;
    }

    /* renamed from: b */
    private void m4203b(int i) {
        if (this.f4654f == null) {
            this.f4654f = new int[10];
        }
        int length = this.f4654f.length;
        int i2 = this.f4655g;
        if (i2 >= length) {
            int[] iArr = new int[Math.max(i2 + 1, length * 2)];
            System.arraycopy(this.f4654f, 0, iArr, 0, length);
            this.f4654f = iArr;
        }
        int[] iArr2 = this.f4654f;
        int i3 = this.f4655g;
        this.f4655g = i3 + 1;
        iArr2[i3] = i;
        int i4 = this.f4650b.f4682f + this.f4655g;
        if (i4 > this.f4650b.f4683g) {
            this.f4650b.f4683g = i4;
        }
    }

    /* renamed from: c */
    private void m4204c(int i) {
        int i2 = this.f4655g;
        if (i2 >= i) {
            this.f4655g = i2 - i;
            return;
        }
        this.f4650b.f4682f -= i - this.f4655g;
        this.f4655g = 0;
    }

    /* renamed from: d */
    private void m4205d(int i) {
        if (this.f4657i == null) {
            this.f4657i = new int[2];
        }
        int length = this.f4657i.length;
        int i2 = this.f4656h;
        if (i2 >= length) {
            int[] iArr = new int[Math.max(i2 + 1, length * 2)];
            System.arraycopy(this.f4657i, 0, iArr, 0, length);
            this.f4657i = iArr;
        }
        int[] iArr2 = this.f4657i;
        int i3 = this.f4656h;
        this.f4656h = i3 + 1;
        iArr2[i3] = i;
    }

    /* access modifiers changed from: 0000 */
    /* JADX WARNING: Code restructure failed: missing block: B:100:0x022b, code lost:
        m4203b(16777218);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:101:0x022f, code lost:
        m4203b(16777220);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:102:0x0232, code lost:
        m4203b(16777216);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:103:0x0236, code lost:
        m4203b(16777217);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:105:0x023d, code lost:
        m4203b(r1);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:110:?, code lost:
        return;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:122:?, code lost:
        return;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:123:?, code lost:
        return;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:124:?, code lost:
        return;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:125:?, code lost:
        return;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:126:?, code lost:
        return;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:127:?, code lost:
        return;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:12:0x003d, code lost:
        m4200a(r3, r1);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:14:0x004b, code lost:
        if (r1.charAt(0) == '[') goto L_0x003d;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:41:0x00e7, code lost:
        r1 = r4.f4674i;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:60:0x0178, code lost:
        m4203b(r1);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:61:0x017b, code lost:
        m4203b(r3);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:63:0x0187, code lost:
        m4203b(r1);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:64:0x018a, code lost:
        m4203b(r2);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:83:0x01e1, code lost:
        m4198a(r1, r2 | 8388608);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:84:0x01e7, code lost:
        m4198a(r1, 16777216);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:86:0x01f6, code lost:
        m4204c(2);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:87:0x01fa, code lost:
        m4204c(2);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:88:0x01ff, code lost:
        m4204c(2);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:89:0x0203, code lost:
        m4204c(2);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:95:0x0218, code lost:
        r1 = r3.mo76756c(r1) | 24117248;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:99:0x0227, code lost:
        m4203b(16777219);
     */
    /* renamed from: a */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void mo76774a(int r17, int r18, org.objectweb.asm.ClassWriter r19, org.objectweb.asm.Item r20) {
        /*
            r16 = this;
            r0 = r16
            r1 = r17
            r2 = r18
            r3 = r19
            r4 = r20
            r5 = 198(0xc6, float:2.77E-43)
            r6 = 1
            if (r1 == r5) goto L_0x0241
            r5 = 199(0xc7, float:2.79E-43)
            if (r1 == r5) goto L_0x0241
            r5 = 24117248(0x1700000, float:4.4081038E-38)
            r7 = 16777218(0x1000002, float:2.3509893E-38)
            r8 = 16777219(0x1000003, float:2.3509895E-38)
            r9 = 16777217(0x1000001, float:2.350989E-38)
            r10 = 16777220(0x1000004, float:2.3509898E-38)
            r11 = 16777216(0x1000000, float:2.3509887E-38)
            switch(r1) {
                case 0: goto L_0x0244;
                case 1: goto L_0x023a;
                case 2: goto L_0x0236;
                case 3: goto L_0x0236;
                case 4: goto L_0x0236;
                case 5: goto L_0x0236;
                case 6: goto L_0x0236;
                case 7: goto L_0x0236;
                case 8: goto L_0x0236;
                case 9: goto L_0x022f;
                case 10: goto L_0x022f;
                case 11: goto L_0x022b;
                case 12: goto L_0x022b;
                case 13: goto L_0x022b;
                case 14: goto L_0x0227;
                case 15: goto L_0x0227;
                case 16: goto L_0x0236;
                case 17: goto L_0x0236;
                case 18: goto L_0x020d;
                default: goto L_0x0026;
            }
        L_0x0026:
            switch(r1) {
                case 21: goto L_0x0236;
                case 22: goto L_0x022f;
                case 23: goto L_0x022b;
                case 24: goto L_0x0227;
                case 25: goto L_0x0208;
                default: goto L_0x0029;
            }
        L_0x0029:
            r12 = 8388608(0x800000, float:1.17549435E-38)
            r13 = 251658240(0xf000000, float:6.3108872E-30)
            switch(r1) {
                case 46: goto L_0x0203;
                case 47: goto L_0x00f8;
                case 48: goto L_0x01fa;
                case 49: goto L_0x0100;
                case 50: goto L_0x01eb;
                case 51: goto L_0x0203;
                case 52: goto L_0x0203;
                case 53: goto L_0x0203;
                case 54: goto L_0x01c9;
                case 55: goto L_0x01a8;
                case 56: goto L_0x01c9;
                case 57: goto L_0x01a8;
                case 58: goto L_0x01c9;
                default: goto L_0x0030;
            }
        L_0x0030:
            r12 = 3
            r13 = 91
            r15 = 0
            r14 = 4
            switch(r1) {
                case 79: goto L_0x01a3;
                case 80: goto L_0x019e;
                case 81: goto L_0x01a3;
                case 82: goto L_0x019e;
                case 83: goto L_0x01a3;
                case 84: goto L_0x01a3;
                case 85: goto L_0x01a3;
                case 86: goto L_0x01a3;
                case 87: goto L_0x0241;
                case 88: goto L_0x0198;
                case 89: goto L_0x018f;
                case 90: goto L_0x017f;
                case 91: goto L_0x016c;
                case 92: goto L_0x0160;
                case 93: goto L_0x0150;
                case 94: goto L_0x0136;
                case 95: goto L_0x0126;
                case 96: goto L_0x0203;
                case 97: goto L_0x0121;
                case 98: goto L_0x01fa;
                case 99: goto L_0x011c;
                case 100: goto L_0x0203;
                case 101: goto L_0x0121;
                case 102: goto L_0x01fa;
                case 103: goto L_0x011c;
                case 104: goto L_0x0203;
                case 105: goto L_0x0121;
                case 106: goto L_0x01fa;
                case 107: goto L_0x011c;
                case 108: goto L_0x0203;
                case 109: goto L_0x0121;
                case 110: goto L_0x01fa;
                case 111: goto L_0x011c;
                case 112: goto L_0x0203;
                case 113: goto L_0x0121;
                case 114: goto L_0x01fa;
                case 115: goto L_0x011c;
                case 116: goto L_0x0244;
                case 117: goto L_0x0244;
                case 118: goto L_0x0244;
                case 119: goto L_0x0244;
                case 120: goto L_0x0203;
                case 121: goto L_0x0117;
                case 122: goto L_0x0203;
                case 123: goto L_0x0117;
                case 124: goto L_0x0203;
                case 125: goto L_0x0117;
                case 126: goto L_0x0203;
                case 127: goto L_0x0121;
                case 128: goto L_0x0203;
                case 129: goto L_0x0121;
                case 130: goto L_0x0203;
                case 131: goto L_0x0121;
                case 132: goto L_0x0112;
                case 133: goto L_0x010d;
                case 134: goto L_0x0108;
                case 135: goto L_0x0103;
                case 136: goto L_0x0203;
                case 137: goto L_0x01fa;
                case 138: goto L_0x0100;
                case 139: goto L_0x00fb;
                case 140: goto L_0x010d;
                case 141: goto L_0x0103;
                case 142: goto L_0x0203;
                case 143: goto L_0x00f8;
                case 144: goto L_0x01fa;
                case 145: goto L_0x0244;
                case 146: goto L_0x0244;
                case 147: goto L_0x0244;
                case 148: goto L_0x00f3;
                case 149: goto L_0x0203;
                case 150: goto L_0x0203;
                case 151: goto L_0x00f3;
                case 152: goto L_0x00f3;
                case 153: goto L_0x0241;
                case 154: goto L_0x0241;
                case 155: goto L_0x0241;
                case 156: goto L_0x0241;
                case 157: goto L_0x0241;
                case 158: goto L_0x0241;
                case 159: goto L_0x0198;
                case 160: goto L_0x0198;
                case 161: goto L_0x0198;
                case 162: goto L_0x0198;
                case 163: goto L_0x0198;
                case 164: goto L_0x0198;
                case 165: goto L_0x0198;
                case 166: goto L_0x0198;
                case 167: goto L_0x0244;
                case 168: goto L_0x00eb;
                case 169: goto L_0x00eb;
                case 170: goto L_0x0241;
                case 171: goto L_0x0241;
                case 172: goto L_0x0241;
                case 173: goto L_0x0198;
                case 174: goto L_0x0241;
                case 175: goto L_0x0198;
                case 176: goto L_0x0241;
                case 177: goto L_0x0244;
                case 178: goto L_0x00e7;
                case 179: goto L_0x00e0;
                case 180: goto L_0x00dc;
                case 181: goto L_0x00d2;
                case 182: goto L_0x00b3;
                case 183: goto L_0x00b3;
                case 184: goto L_0x00b3;
                case 185: goto L_0x00b3;
                case 186: goto L_0x00ab;
                case 187: goto L_0x00a0;
                case 188: goto L_0x0072;
                case 189: goto L_0x004e;
                case 190: goto L_0x00fb;
                case 191: goto L_0x0241;
                case 192: goto L_0x0042;
                case 193: goto L_0x00fb;
                case 194: goto L_0x0241;
                case 195: goto L_0x0241;
                default: goto L_0x0038;
            }
        L_0x0038:
            r0.m4204c(r2)
            java.lang.String r1 = r4.f4672g
        L_0x003d:
            r0.m4200a(r3, r1)
            goto L_0x0244
        L_0x0042:
            java.lang.String r1 = r4.f4672g
            r16.m4195a()
            char r2 = r1.charAt(r15)
            if (r2 != r13) goto L_0x0218
            goto L_0x003d
        L_0x004e:
            java.lang.String r1 = r4.f4672g
            r16.m4195a()
            char r2 = r1.charAt(r15)
            if (r2 != r13) goto L_0x0069
            java.lang.StringBuffer r2 = new java.lang.StringBuffer
            r2.<init>()
            r2.append(r13)
            r2.append(r1)
            java.lang.String r1 = r2.toString()
            goto L_0x003d
        L_0x0069:
            r2 = 292552704(0x11700000, float:1.8932662E-28)
            int r1 = r3.mo76756c(r1)
            r1 = r1 | r2
            goto L_0x023d
        L_0x0072:
            r16.m4195a()
            switch(r2) {
                case 4: goto L_0x009b;
                case 5: goto L_0x0096;
                case 6: goto L_0x0091;
                case 7: goto L_0x008c;
                case 8: goto L_0x0087;
                case 9: goto L_0x0082;
                case 10: goto L_0x007d;
                default: goto L_0x0078;
            }
        L_0x0078:
            r1 = 285212676(0x11000004, float:1.0097424E-28)
            goto L_0x023d
        L_0x007d:
            r1 = 285212673(0x11000001, float:1.0097421E-28)
            goto L_0x023d
        L_0x0082:
            r1 = 285212684(0x1100000c, float:1.0097434E-28)
            goto L_0x023d
        L_0x0087:
            r1 = 285212682(0x1100000a, float:1.0097432E-28)
            goto L_0x023d
        L_0x008c:
            r1 = 285212675(0x11000003, float:1.0097423E-28)
            goto L_0x023d
        L_0x0091:
            r1 = 285212674(0x11000002, float:1.0097422E-28)
            goto L_0x023d
        L_0x0096:
            r1 = 285212683(0x1100000b, float:1.0097433E-28)
            goto L_0x023d
        L_0x009b:
            r1 = 285212681(0x11000009, float:1.009743E-28)
            goto L_0x023d
        L_0x00a0:
            r1 = 25165824(0x1800000, float:4.7019774E-38)
            java.lang.String r4 = r4.f4672g
            int r2 = r3.mo76744a(r4, r2)
            r1 = r1 | r2
            goto L_0x023d
        L_0x00ab:
            java.lang.String r1 = r4.f4673h
            r0.m4199a(r1)
            java.lang.String r1 = r4.f4673h
            goto L_0x003d
        L_0x00b3:
            java.lang.String r2 = r4.f4674i
            r0.m4199a(r2)
            r2 = 184(0xb8, float:2.58E-43)
            if (r1 == r2) goto L_0x00e7
            int r2 = r16.m4195a()
            r5 = 183(0xb7, float:2.56E-43)
            if (r1 != r5) goto L_0x00e7
            java.lang.String r1 = r4.f4673h
            char r1 = r1.charAt(r15)
            r5 = 60
            if (r1 != r5) goto L_0x00e7
            r0.m4205d(r2)
            goto L_0x00e7
        L_0x00d2:
            java.lang.String r1 = r4.f4674i
            r0.m4199a(r1)
            r16.m4195a()
            goto L_0x0244
        L_0x00dc:
            r0.m4204c(r6)
            goto L_0x00e7
        L_0x00e0:
            java.lang.String r1 = r4.f4674i
            r0.m4199a(r1)
            goto L_0x0244
        L_0x00e7:
            java.lang.String r1 = r4.f4674i
            goto L_0x003d
        L_0x00eb:
            java.lang.RuntimeException r1 = new java.lang.RuntimeException
            java.lang.String r2 = "JSR/RET are not supported with computeFrames option"
            r1.<init>(r2)
            throw r1
        L_0x00f3:
            r0.m4204c(r14)
            goto L_0x0236
        L_0x00f8:
            r1 = 2
            goto L_0x01ff
        L_0x00fb:
            r0.m4204c(r6)
            goto L_0x0236
        L_0x0100:
            r1 = 2
            goto L_0x01f6
        L_0x0103:
            r0.m4204c(r6)
            goto L_0x0227
        L_0x0108:
            r0.m4204c(r6)
            goto L_0x022b
        L_0x010d:
            r0.m4204c(r6)
            goto L_0x022f
        L_0x0112:
            r0.m4198a(r2, r9)
            goto L_0x0244
        L_0x0117:
            r0.m4204c(r12)
            goto L_0x022f
        L_0x011c:
            r0.m4204c(r14)
            goto L_0x0227
        L_0x0121:
            r0.m4204c(r14)
            goto L_0x022f
        L_0x0126:
            int r1 = r16.m4195a()
            int r2 = r16.m4195a()
            r0.m4203b(r1)
            r0.m4203b(r2)
            goto L_0x0244
        L_0x0136:
            int r1 = r16.m4195a()
            int r2 = r16.m4195a()
            int r3 = r16.m4195a()
            int r4 = r16.m4195a()
            r0.m4203b(r2)
            r0.m4203b(r1)
            r0.m4203b(r4)
            goto L_0x017b
        L_0x0150:
            int r1 = r16.m4195a()
            int r2 = r16.m4195a()
            int r3 = r16.m4195a()
            r0.m4203b(r2)
            goto L_0x0178
        L_0x0160:
            int r1 = r16.m4195a()
            int r2 = r16.m4195a()
            r0.m4203b(r2)
            goto L_0x0187
        L_0x016c:
            int r1 = r16.m4195a()
            int r2 = r16.m4195a()
            int r3 = r16.m4195a()
        L_0x0178:
            r0.m4203b(r1)
        L_0x017b:
            r0.m4203b(r3)
            goto L_0x018a
        L_0x017f:
            int r1 = r16.m4195a()
            int r2 = r16.m4195a()
        L_0x0187:
            r0.m4203b(r1)
        L_0x018a:
            r0.m4203b(r2)
            goto L_0x023d
        L_0x018f:
            int r1 = r16.m4195a()
            r0.m4203b(r1)
            goto L_0x023d
        L_0x0198:
            r1 = 2
            r0.m4204c(r1)
            goto L_0x0244
        L_0x019e:
            r0.m4204c(r14)
            goto L_0x0244
        L_0x01a3:
            r0.m4204c(r12)
            goto L_0x0244
        L_0x01a8:
            r0.m4204c(r6)
            int r1 = r16.m4195a()
            r0.m4198a(r2, r1)
            int r1 = r2 + 1
            r0.m4198a(r1, r11)
            if (r2 <= 0) goto L_0x0244
            int r1 = r2 + -1
            int r2 = r0.m4196a(r1)
            if (r2 == r10) goto L_0x01e7
            if (r2 != r8) goto L_0x01c4
            goto L_0x01e7
        L_0x01c4:
            r3 = r2 & r13
            if (r3 == r11) goto L_0x0244
            goto L_0x01e1
        L_0x01c9:
            int r1 = r16.m4195a()
            r0.m4198a(r2, r1)
            if (r2 <= 0) goto L_0x0244
            int r1 = r2 + -1
            int r2 = r0.m4196a(r1)
            if (r2 == r10) goto L_0x01e7
            if (r2 != r8) goto L_0x01dd
            goto L_0x01e7
        L_0x01dd:
            r3 = r2 & r13
            if (r3 == r11) goto L_0x0244
        L_0x01e1:
            r2 = r2 | r12
            r0.m4198a(r1, r2)
            goto L_0x0244
        L_0x01e7:
            r0.m4198a(r1, r11)
            goto L_0x0244
        L_0x01eb:
            r0.m4204c(r6)
            int r1 = r16.m4195a()
            r2 = -268435456(0xfffffffff0000000, float:-1.58456325E29)
            int r1 = r1 + r2
            goto L_0x023d
        L_0x01f6:
            r0.m4204c(r1)
            goto L_0x0227
        L_0x01fa:
            r1 = 2
            r0.m4204c(r1)
            goto L_0x022b
        L_0x01ff:
            r0.m4204c(r1)
            goto L_0x022f
        L_0x0203:
            r1 = 2
            r0.m4204c(r1)
            goto L_0x0236
        L_0x0208:
            int r1 = r0.m4196a(r2)
            goto L_0x023d
        L_0x020d:
            int r1 = r4.f4669b
            r2 = 16
            if (r1 == r2) goto L_0x0224
            switch(r1) {
                case 3: goto L_0x0236;
                case 4: goto L_0x022b;
                case 5: goto L_0x022f;
                case 6: goto L_0x0227;
                case 7: goto L_0x0221;
                case 8: goto L_0x021e;
                default: goto L_0x0216;
            }
        L_0x0216:
            java.lang.String r1 = "java/lang/invoke/MethodHandle"
        L_0x0218:
            int r1 = r3.mo76756c(r1)
            r1 = r1 | r5
            goto L_0x023d
        L_0x021e:
            java.lang.String r1 = "java/lang/String"
            goto L_0x0218
        L_0x0221:
            java.lang.String r1 = "java/lang/Class"
            goto L_0x0218
        L_0x0224:
            java.lang.String r1 = "java/lang/invoke/MethodType"
            goto L_0x0218
        L_0x0227:
            r0.m4203b(r8)
            goto L_0x0232
        L_0x022b:
            r0.m4203b(r7)
            goto L_0x0244
        L_0x022f:
            r0.m4203b(r10)
        L_0x0232:
            r0.m4203b(r11)
            goto L_0x0244
        L_0x0236:
            r0.m4203b(r9)
            goto L_0x0244
        L_0x023a:
            r1 = 16777221(0x1000005, float:2.35099E-38)
        L_0x023d:
            r0.m4203b(r1)
            goto L_0x0244
        L_0x0241:
            r0.m4204c(r6)
        L_0x0244:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: org.objectweb.asm.Frame.mo76774a(int, int, org.objectweb.asm.ClassWriter, org.objectweb.asm.Item):void");
    }

    /* access modifiers changed from: 0000 */
    /* renamed from: a */
    public void mo76775a(ClassWriter classWriter, int i, Type[] typeArr, int i2) {
        int i3;
        this.f4651c = new int[i2];
        this.f4652d = new int[0];
        int i4 = 1;
        if ((i & 8) != 0) {
            i4 = 0;
        } else if ((i & 524288) == 0) {
            this.f4651c[0] = 24117248 | classWriter.mo76756c(classWriter.f4582I);
        } else {
            this.f4651c[0] = 16777222;
        }
        for (Type descriptor : typeArr) {
            int b = m4202b(classWriter, descriptor.getDescriptor());
            int i5 = i3 + 1;
            this.f4651c[i3] = b;
            if (b == 16777220 || b == 16777219) {
                i3 = i5 + 1;
                this.f4651c[i5] = 16777216;
            } else {
                i3 = i5;
            }
        }
        while (i3 < i2) {
            int i6 = i3 + 1;
            this.f4651c[i3] = 16777216;
            i3 = i6;
        }
    }

    /* access modifiers changed from: 0000 */
    /* JADX WARNING: Removed duplicated region for block: B:68:0x0107  */
    /* JADX WARNING: Removed duplicated region for block: B:80:0x010b A[SYNTHETIC] */
    /* renamed from: a */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public boolean mo76776a(org.objectweb.asm.ClassWriter r19, org.objectweb.asm.Frame r20, int r21) {
        /*
            r18 = this;
            r0 = r18
            r1 = r19
            r2 = r20
            r3 = r21
            int[] r4 = r0.f4651c
            int r4 = r4.length
            int[] r5 = r0.f4652d
            int r5 = r5.length
            int[] r6 = r2.f4651c
            if (r6 != 0) goto L_0x0018
            int[] r6 = new int[r4]
            r2.f4651c = r6
            r6 = 1
            goto L_0x0019
        L_0x0018:
            r6 = 0
        L_0x0019:
            r9 = r6
            r6 = 0
        L_0x001b:
            r11 = 16777220(0x1000004, float:2.3509898E-38)
            r12 = 8388608(0x800000, float:1.17549435E-38)
            r13 = 33554432(0x2000000, float:9.403955E-38)
            r14 = 251658240(0xf000000, float:6.3108872E-30)
            r15 = -268435456(0xfffffffff0000000, float:-1.58456325E29)
            r16 = 8388607(0x7fffff, float:1.1754942E-38)
            r7 = 16777216(0x1000000, float:2.3509887E-38)
            if (r6 >= r4) goto L_0x0079
            int[] r8 = r0.f4653e
            if (r8 == 0) goto L_0x0063
            int r10 = r8.length
            if (r6 >= r10) goto L_0x0063
            r8 = r8[r6]
            if (r8 != 0) goto L_0x003d
            int[] r7 = r0.f4651c
            r7 = r7[r6]
            goto L_0x0067
        L_0x003d:
            r10 = r8 & r15
            r14 = r14 & r8
            if (r14 != r7) goto L_0x0044
            r7 = r8
            goto L_0x0067
        L_0x0044:
            if (r14 != r13) goto L_0x004d
            int[] r13 = r0.f4651c
            r14 = r8 & r16
            r13 = r13[r14]
            goto L_0x0055
        L_0x004d:
            int[] r13 = r0.f4652d
            r14 = r8 & r16
            int r14 = r5 - r14
            r13 = r13[r14]
        L_0x0055:
            int r10 = r10 + r13
            r8 = r8 & r12
            if (r8 == 0) goto L_0x0061
            if (r10 == r11) goto L_0x0067
            r8 = 16777219(0x1000003, float:2.3509895E-38)
            if (r10 != r8) goto L_0x0061
            goto L_0x0067
        L_0x0061:
            r7 = r10
            goto L_0x0067
        L_0x0063:
            int[] r7 = r0.f4651c
            r7 = r7[r6]
        L_0x0067:
            int[] r8 = r0.f4657i
            if (r8 == 0) goto L_0x006f
            int r7 = r0.m4197a(r1, r7)
        L_0x006f:
            int[] r8 = r2.f4651c
            boolean r7 = m4201a(r1, r7, r8, r6)
            r9 = r9 | r7
            int r6 = r6 + 1
            goto L_0x001b
        L_0x0079:
            if (r3 <= 0) goto L_0x00a1
            r8 = r9
            r5 = 0
        L_0x007d:
            if (r5 >= r4) goto L_0x008d
            int[] r6 = r0.f4651c
            r6 = r6[r5]
            int[] r7 = r2.f4651c
            boolean r6 = m4201a(r1, r6, r7, r5)
            r8 = r8 | r6
            int r5 = r5 + 1
            goto L_0x007d
        L_0x008d:
            int[] r4 = r2.f4652d
            if (r4 != 0) goto L_0x0097
            r4 = 1
            int[] r5 = new int[r4]
            r2.f4652d = r5
            goto L_0x0098
        L_0x0097:
            r4 = r8
        L_0x0098:
            int[] r2 = r2.f4652d
            r6 = 0
            boolean r1 = m4201a(r1, r3, r2, r6)
            r1 = r1 | r4
            return r1
        L_0x00a1:
            r4 = 1
            r6 = 0
            int[] r3 = r0.f4652d
            int r3 = r3.length
            org.objectweb.asm.Label r8 = r0.f4650b
            int r8 = r8.f4682f
            int r3 = r3 + r8
            int[] r8 = r2.f4652d
            if (r8 != 0) goto L_0x00b7
            int r8 = r0.f4655g
            int r8 = r8 + r3
            int[] r8 = new int[r8]
            r2.f4652d = r8
            goto L_0x00b8
        L_0x00b7:
            r4 = r9
        L_0x00b8:
            r8 = r4
            r4 = 0
        L_0x00ba:
            if (r4 >= r3) goto L_0x00d2
            int[] r9 = r0.f4652d
            r9 = r9[r4]
            int[] r10 = r0.f4657i
            if (r10 == 0) goto L_0x00c8
            int r9 = r0.m4197a(r1, r9)
        L_0x00c8:
            int[] r10 = r2.f4652d
            boolean r9 = m4201a(r1, r9, r10, r4)
            r8 = r8 | r9
            int r4 = r4 + 1
            goto L_0x00ba
        L_0x00d2:
            int r4 = r0.f4655g
            if (r6 >= r4) goto L_0x0117
            int[] r4 = r0.f4654f
            r4 = r4[r6]
            r9 = r4 & r15
            r10 = r4 & r14
            if (r10 != r7) goto L_0x00e5
            r9 = r4
        L_0x00e1:
            r4 = 16777219(0x1000003, float:2.3509895E-38)
            goto L_0x0103
        L_0x00e5:
            if (r10 != r13) goto L_0x00ee
            int[] r10 = r0.f4651c
            r17 = r4 & r16
            r10 = r10[r17]
            goto L_0x00f6
        L_0x00ee:
            int[] r10 = r0.f4652d
            r17 = r4 & r16
            int r17 = r5 - r17
            r10 = r10[r17]
        L_0x00f6:
            int r9 = r9 + r10
            r4 = r4 & r12
            if (r4 == 0) goto L_0x00e1
            r4 = 16777219(0x1000003, float:2.3509895E-38)
            if (r9 == r11) goto L_0x0101
            if (r9 != r4) goto L_0x0103
        L_0x0101:
            r9 = 16777216(0x1000000, float:2.3509887E-38)
        L_0x0103:
            int[] r10 = r0.f4657i
            if (r10 == 0) goto L_0x010b
            int r9 = r0.m4197a(r1, r9)
        L_0x010b:
            int[] r10 = r2.f4652d
            int r4 = r3 + r6
            boolean r4 = m4201a(r1, r9, r10, r4)
            r8 = r8 | r4
            int r6 = r6 + 1
            goto L_0x00d2
        L_0x0117:
            return r8
        */
        throw new UnsupportedOperationException("Method not decompiled: org.objectweb.asm.Frame.mo76776a(org.objectweb.asm.ClassWriter, org.objectweb.asm.Frame, int):boolean");
    }
}
