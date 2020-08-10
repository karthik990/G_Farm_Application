package p043io.netty.handler.codec.compression;

/* renamed from: io.netty.handler.codec.compression.Bzip2DivSufSort */
final class Bzip2DivSufSort {
    private static final int BUCKET_A_SIZE = 256;
    private static final int BUCKET_B_SIZE = 65536;
    private static final int INSERTIONSORT_THRESHOLD = 8;
    private static final int[] LOG_2_TABLE = {-1, 0, 1, 1, 2, 2, 2, 2, 3, 3, 3, 3, 3, 3, 3, 3, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7};
    private static final int SS_BLOCKSIZE = 1024;
    private static final int STACK_SIZE = 64;

    /* renamed from: SA */
    private final int[] f3714SA;

    /* renamed from: T */
    private final byte[] f3715T;

    /* renamed from: n */
    private final int f3716n;

    /* renamed from: io.netty.handler.codec.compression.Bzip2DivSufSort$PartitionResult */
    private static class PartitionResult {
        final int first;
        final int last;

        PartitionResult(int i, int i2) {
            this.first = i;
            this.last = i2;
        }
    }

    /* renamed from: io.netty.handler.codec.compression.Bzip2DivSufSort$StackEntry */
    private static class StackEntry {

        /* renamed from: a */
        final int f3717a;

        /* renamed from: b */
        final int f3718b;

        /* renamed from: c */
        final int f3719c;

        /* renamed from: d */
        final int f3720d;

        StackEntry(int i, int i2, int i3, int i4) {
            this.f3717a = i;
            this.f3718b = i2;
            this.f3719c = i3;
            this.f3720d = i4;
        }
    }

    /* renamed from: io.netty.handler.codec.compression.Bzip2DivSufSort$TRBudget */
    private static class TRBudget {
        int budget;
        int chance;

        TRBudget(int i, int i2) {
            this.budget = i;
            this.chance = i2;
        }

        /* access modifiers changed from: 0000 */
        public boolean update(int i, int i2) {
            this.budget -= i2;
            int i3 = this.budget;
            if (i3 <= 0) {
                int i4 = this.chance - 1;
                this.chance = i4;
                if (i4 == 0) {
                    return false;
                }
                this.budget = i3 + i;
            }
            return true;
        }
    }

    private static int BUCKET_B(int i, int i2) {
        return i | (i2 << 8);
    }

    private static int BUCKET_BSTAR(int i, int i2) {
        return (i << 8) | i2;
    }

    private static int getIDX(int i) {
        return i >= 0 ? i : i ^ -1;
    }

    Bzip2DivSufSort(byte[] bArr, int[] iArr, int i) {
        this.f3715T = bArr;
        this.f3714SA = iArr;
        this.f3716n = i;
    }

    private static void swapElements(int[] iArr, int i, int[] iArr2, int i2) {
        int i3 = iArr[i];
        iArr[i] = iArr2[i2];
        iArr2[i2] = i3;
    }

    private int ssCompare(int i, int i2, int i3) {
        int[] iArr = this.f3714SA;
        byte[] bArr = this.f3715T;
        int i4 = iArr[i + 1] + 2;
        int i5 = iArr[i2 + 1] + 2;
        int i6 = iArr[i] + i3;
        int i7 = i3 + iArr[i2];
        while (i6 < i4 && i7 < i5 && bArr[i6] == bArr[i7]) {
            i6++;
            i7++;
        }
        if (i6 >= i4) {
            return i7 < i5 ? -1 : 0;
        }
        if (i7 < i5) {
            return (bArr[i6] & 255) - (bArr[i7] & 255);
        }
        return 1;
    }

    private int ssCompareLast(int i, int i2, int i3, int i4, int i5) {
        int[] iArr = this.f3714SA;
        byte[] bArr = this.f3715T;
        int i6 = iArr[i2] + i4;
        int i7 = i4 + iArr[i3];
        int i8 = 1;
        int i9 = iArr[i3 + 1] + 2;
        while (i6 < i5 && i7 < i9 && bArr[i6] == bArr[i7]) {
            i6++;
            i7++;
        }
        if (i6 < i5) {
            if (i7 < i9) {
                i8 = (bArr[i6] & 255) - (bArr[i7] & 255);
            }
            return i8;
        } else if (i7 == i9) {
            return 1;
        } else {
            int i10 = i6 % i5;
            int i11 = iArr[i] + 2;
            while (i10 < i11 && i7 < i9 && bArr[i10] == bArr[i7]) {
                i10++;
                i7++;
            }
            if (i10 >= i11) {
                i8 = i7 < i9 ? -1 : 0;
            } else if (i7 < i9) {
                i8 = (bArr[i10] & 255) - (bArr[i7] & 255);
            }
            return i8;
        }
    }

    private void ssInsertionSort(int i, int i2, int i3, int i4) {
        int ssCompare;
        int[] iArr = this.f3714SA;
        for (int i5 = i3 - 2; i2 <= i5; i5--) {
            int i6 = iArr[i5];
            int i7 = i5 + 1;
            do {
                ssCompare = ssCompare(i + i6, iArr[i7] + i, i4);
                if (ssCompare <= 0) {
                    break;
                }
                do {
                    iArr[i7 - 1] = iArr[i7];
                    i7++;
                    if (i7 >= i3) {
                        break;
                    }
                } while (iArr[i7] < 0);
                continue;
            } while (i3 > i7);
            if (ssCompare == 0) {
                iArr[i7] = iArr[i7] ^ -1;
            }
            iArr[i7 - 1] = i6;
        }
    }

    private void ssFixdown(int i, int i2, int i3, int i4, int i5) {
        int[] iArr = this.f3714SA;
        byte[] bArr = this.f3715T;
        int i6 = iArr[i3 + i4];
        byte b = bArr[iArr[i2 + i6] + i] & 255;
        while (true) {
            int i7 = (i4 * 2) + 1;
            if (i7 >= i5) {
                break;
            }
            int i8 = i7 + 1;
            byte b2 = bArr[iArr[iArr[i3 + i7] + i2] + i] & 255;
            byte b3 = bArr[iArr[iArr[i3 + i8] + i2] + i] & 255;
            if (b2 < b3) {
                i7 = i8;
                b2 = b3;
            }
            if (b2 <= b) {
                break;
            }
            iArr[i4 + i3] = iArr[i3 + i7];
            i4 = i7;
        }
        iArr[i3 + i4] = i6;
    }

    private void ssHeapSort(int i, int i2, int i3, int i4) {
        int i5;
        int i6 = i3;
        int[] iArr = this.f3714SA;
        byte[] bArr = this.f3715T;
        int i7 = i4 % 2;
        if (i7 == 0) {
            int i8 = i4 - 1;
            int i9 = (i8 / 2) + i6;
            int i10 = i6 + i8;
            if ((bArr[iArr[iArr[i9] + i2] + i] & 255) < (bArr[iArr[iArr[i10] + i2] + i] & 255)) {
                swapElements(iArr, i10, iArr, i9);
            }
            i5 = i8;
        } else {
            i5 = i4;
        }
        for (int i11 = (i5 / 2) - 1; i11 >= 0; i11--) {
            ssFixdown(i, i2, i3, i11, i5);
        }
        if (i7 == 0) {
            swapElements(iArr, i3, iArr, i6 + i5);
            ssFixdown(i, i2, i3, 0, i5);
        }
        for (int i12 = i5 - 1; i12 > 0; i12--) {
            int i13 = iArr[i6];
            int i14 = i6 + i12;
            iArr[i6] = iArr[i14];
            ssFixdown(i, i2, i3, 0, i12);
            iArr[i14] = i13;
        }
    }

    private int ssMedian3(int i, int i2, int i3, int i4, int i5) {
        int[] iArr = this.f3714SA;
        byte[] bArr = this.f3715T;
        byte b = bArr[iArr[iArr[i3] + i2] + i] & 255;
        byte b2 = bArr[iArr[iArr[i4] + i2] + i] & 255;
        byte b3 = bArr[i + iArr[i2 + iArr[i5]]] & 255;
        if (b <= b2) {
            int i6 = i4;
            i4 = i3;
            i3 = i6;
            byte b4 = b2;
            b2 = b;
            b = b4;
        }
        if (b > b3) {
            return b2 > b3 ? i4 : i5;
        }
        return i3;
    }

    private int ssMedian5(int i, int i2, int i3, int i4, int i5, int i6, int i7) {
        byte b;
        int i8;
        int i9;
        int i10;
        byte b2;
        int i11;
        int i12;
        int[] iArr = this.f3714SA;
        byte[] bArr = this.f3715T;
        byte b3 = bArr[iArr[iArr[i3] + i2] + i] & 255;
        byte b4 = bArr[iArr[iArr[i4] + i2] + i] & 255;
        byte b5 = bArr[iArr[iArr[i5] + i2] + i] & 255;
        byte b6 = bArr[iArr[iArr[i6] + i2] + i] & 255;
        byte b7 = bArr[iArr[iArr[i7] + i2] + i] & 255;
        if (b4 > b5) {
            i9 = i5;
            b = b4;
            b4 = b5;
            i8 = i4;
        } else {
            i9 = i4;
            b = b5;
            i8 = i5;
        }
        if (b6 > b7) {
            i10 = i7;
            b2 = b6;
            i11 = i6;
        } else {
            i10 = i6;
            b2 = b7;
            b7 = b6;
            i11 = i7;
        }
        if (b4 > b7) {
            i10 = i9;
            b7 = b4;
            int i13 = i11;
            i11 = i8;
            i8 = i13;
        } else {
            byte b8 = b2;
            b2 = b;
            b = b8;
        }
        if (b3 > b2) {
            i12 = i3;
        } else {
            i12 = i8;
            i8 = i3;
            byte b9 = b2;
            b2 = b3;
            b3 = b9;
        }
        if (b2 > b7) {
            i10 = i8;
            i12 = i11;
            b3 = b;
        } else {
            b2 = b7;
        }
        return b3 > b2 ? i10 : i12;
    }

    private int ssPivot(int i, int i2, int i3, int i4) {
        int i5 = i4 - i3;
        int i6 = i3 + (i5 / 2);
        if (i5 > 512) {
            int i7 = i5 >> 3;
            int i8 = i7 << 1;
            int i9 = i;
            int i10 = i4 - 1;
            return ssMedian3(i, i2, ssMedian3(i9, i2, i3, i3 + i7, i3 + i8), ssMedian3(i, i2, i6 - i7, i6, i6 + i7), ssMedian3(i9, i2, i10 - i8, i10 - i7, i10));
        } else if (i5 <= 32) {
            return ssMedian3(i, i2, i3, i6, i4 - 1);
        } else {
            int i11 = i5 >> 2;
            int i12 = i4 - 1;
            return ssMedian5(i, i2, i3, i3 + i11, i6, i12 - i11, i12);
        }
    }

    private static int ssLog(int i) {
        return (65280 & i) != 0 ? LOG_2_TABLE[(i >> 8) & 255] + 8 : LOG_2_TABLE[i & 255];
    }

    private int ssSubstringPartition(int i, int i2, int i3, int i4) {
        int[] iArr = this.f3714SA;
        int i5 = i2 - 1;
        while (true) {
            i5++;
            if (i5 >= i3 || iArr[iArr[i5] + i] + i4 < iArr[iArr[i5] + i + 1] + 1) {
                do {
                    i3--;
                    if (i5 >= i3) {
                        break;
                    }
                } while (iArr[iArr[i3] + i] + i4 < iArr[iArr[i3] + i + 1] + 1);
                if (i3 <= i5) {
                    break;
                }
                int i6 = iArr[i3] ^ -1;
                iArr[i3] = iArr[i5];
                iArr[i5] = i6;
            } else {
                iArr[i5] = iArr[i5] ^ -1;
            }
        }
        if (i2 < i5) {
            iArr[i2] = iArr[i2] ^ -1;
        }
        return i5;
    }

    /*  JADX ERROR: JadxRuntimeException in pass: RegionMakerVisitor
        jadx.core.utils.exceptions.JadxRuntimeException: Regions count limit reached
        	at jadx.core.dex.visitors.regions.RegionMaker.makeRegion(RegionMaker.java:89)
        	at jadx.core.dex.visitors.regions.RegionMaker.makeEndlessLoop(RegionMaker.java:368)
        	at jadx.core.dex.visitors.regions.RegionMaker.processLoop(RegionMaker.java:172)
        	at jadx.core.dex.visitors.regions.RegionMaker.traverse(RegionMaker.java:106)
        	at jadx.core.dex.visitors.regions.RegionMaker.makeRegion(RegionMaker.java:86)
        	at jadx.core.dex.visitors.regions.RegionMaker.processIf(RegionMaker.java:695)
        	at jadx.core.dex.visitors.regions.RegionMaker.traverse(RegionMaker.java:123)
        	at jadx.core.dex.visitors.regions.RegionMaker.makeRegion(RegionMaker.java:86)
        	at jadx.core.dex.visitors.regions.RegionMaker.processIf(RegionMaker.java:695)
        	at jadx.core.dex.visitors.regions.RegionMaker.traverse(RegionMaker.java:123)
        	at jadx.core.dex.visitors.regions.RegionMaker.makeRegion(RegionMaker.java:86)
        	at jadx.core.dex.visitors.regions.RegionMaker.makeEndlessLoop(RegionMaker.java:368)
        	at jadx.core.dex.visitors.regions.RegionMaker.processLoop(RegionMaker.java:172)
        	at jadx.core.dex.visitors.regions.RegionMaker.traverse(RegionMaker.java:106)
        	at jadx.core.dex.visitors.regions.RegionMaker.makeRegion(RegionMaker.java:86)
        	at jadx.core.dex.visitors.regions.RegionMakerVisitor.visit(RegionMakerVisitor.java:49)
        	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:27)
        	at jadx.core.dex.visitors.DepthTraversal.lambda$visit$1(DepthTraversal.java:14)
        	at java.base/java.util.ArrayList.forEach(ArrayList.java:1540)
        	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:14)
        	at jadx.core.ProcessClass.process(ProcessClass.java:30)
        	at jadx.core.ProcessClass.lambda$processDependencies$0(ProcessClass.java:49)
        	at java.base/java.util.ArrayList.forEach(ArrayList.java:1540)
        	at jadx.core.ProcessClass.processDependencies(ProcessClass.java:49)
        	at jadx.core.ProcessClass.process(ProcessClass.java:35)
        	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:311)
        	at jadx.api.JavaClass.decompile(JavaClass.java:62)
        	at jadx.api.JadxDecompiler.lambda$appendSourcesSave$0(JadxDecompiler.java:217)
        */
    /* JADX WARNING: Removed duplicated region for block: B:117:0x0291  */
    /* JADX WARNING: Removed duplicated region for block: B:146:0x0183 A[SYNTHETIC] */
    /* JADX WARNING: Removed duplicated region for block: B:152:0x0164 A[SYNTHETIC] */
    /* JADX WARNING: Removed duplicated region for block: B:66:0x014a  */
    /* JADX WARNING: Removed duplicated region for block: B:72:0x015f  */
    /* JADX WARNING: Removed duplicated region for block: B:77:0x016d  */
    /* JADX WARNING: Removed duplicated region for block: B:82:0x0185  */
    private void ssMultiKeyIntroSort(int r21, int r22, int r23, int r24) {
        /*
            r20 = this;
            r0 = r20
            r1 = r21
            int[] r2 = r0.f3714SA
            byte[] r3 = r0.f3715T
            r4 = 64
            io.netty.handler.codec.compression.Bzip2DivSufSort$StackEntry[] r4 = new p043io.netty.handler.codec.compression.Bzip2DivSufSort.StackEntry[r4]
            int r5 = r23 - r22
            int r5 = ssLog(r5)
            r6 = 0
            r7 = -1
            r6 = r22
            r8 = r24
            r9 = r5
            r10 = 0
            r11 = 0
            r5 = r23
        L_0x001d:
            int r12 = r5 - r6
            r13 = 8
            r14 = 1
            if (r12 > r13) goto L_0x003f
            if (r14 >= r12) goto L_0x0029
            r0.ssInsertionSort(r1, r6, r5, r8)
        L_0x0029:
            if (r10 != 0) goto L_0x002c
            return
        L_0x002c:
            int r10 = r10 + -1
            r5 = r4[r10]
            int r6 = r5.f3717a
            int r8 = r5.f3718b
            int r9 = r5.f3719c
            int r5 = r5.f3720d
            r19 = r9
            r9 = r5
            r5 = r8
            r8 = r19
            goto L_0x001d
        L_0x003f:
            int r13 = r9 + -1
            if (r9 != 0) goto L_0x0046
            r0.ssHeapSort(r8, r1, r6, r12)
        L_0x0046:
            if (r13 >= 0) goto L_0x00be
            int r9 = r6 + 1
            r12 = r2[r6]
            int r12 = r12 + r1
            r12 = r2[r12]
            int r12 = r12 + r8
            byte r12 = r3[r12]
            r12 = r12 & 255(0xff, float:3.57E-43)
            r19 = r9
            r9 = r6
            r6 = r19
        L_0x0059:
            if (r6 >= r5) goto L_0x0071
            r11 = r2[r6]
            int r11 = r11 + r1
            r11 = r2[r11]
            int r11 = r11 + r8
            byte r11 = r3[r11]
            r11 = r11 & 255(0xff, float:3.57E-43)
            if (r11 == r12) goto L_0x006e
            int r13 = r6 - r9
            if (r14 >= r13) goto L_0x006c
            goto L_0x0071
        L_0x006c:
            r9 = r6
            r12 = r11
        L_0x006e:
            int r6 = r6 + 1
            goto L_0x0059
        L_0x0071:
            r13 = r2[r9]
            int r13 = r13 + r1
            r13 = r2[r13]
            int r13 = r13 + r8
            int r13 = r13 - r14
            byte r13 = r3[r13]
            r13 = r13 & 255(0xff, float:3.57E-43)
            if (r13 >= r12) goto L_0x0082
            int r9 = r0.ssSubstringPartition(r1, r9, r6, r8)
        L_0x0082:
            int r12 = r6 - r9
            int r13 = r5 - r6
            if (r12 > r13) goto L_0x009b
            if (r14 >= r12) goto L_0x00ad
            int r13 = r10 + 1
            io.netty.handler.codec.compression.Bzip2DivSufSort$StackEntry r14 = new io.netty.handler.codec.compression.Bzip2DivSufSort$StackEntry
            r14.<init>(r6, r5, r8, r7)
            r4[r10] = r14
            int r8 = r8 + 1
            int r5 = ssLog(r12)
            r10 = r13
            goto L_0x00b6
        L_0x009b:
            if (r14 >= r13) goto L_0x00b0
            int r13 = r10 + 1
            io.netty.handler.codec.compression.Bzip2DivSufSort$StackEntry r14 = new io.netty.handler.codec.compression.Bzip2DivSufSort$StackEntry
            int r15 = r8 + 1
            int r12 = ssLog(r12)
            r14.<init>(r9, r6, r15, r12)
            r4[r10] = r14
            r10 = r13
        L_0x00ad:
            r9 = -1
            goto L_0x001d
        L_0x00b0:
            int r8 = r8 + 1
            int r5 = ssLog(r12)
        L_0x00b6:
            r19 = r9
            r9 = r5
            r5 = r6
            r6 = r19
            goto L_0x001d
        L_0x00be:
            int r9 = r0.ssPivot(r8, r1, r6, r5)
            r12 = r2[r9]
            int r12 = r12 + r1
            r12 = r2[r12]
            int r12 = r12 + r8
            byte r12 = r3[r12]
            r12 = r12 & 255(0xff, float:3.57E-43)
            swapElements(r2, r6, r2, r9)
            int r9 = r6 + 1
        L_0x00d1:
            if (r9 >= r5) goto L_0x00e2
            r11 = r2[r9]
            int r11 = r11 + r1
            r11 = r2[r11]
            int r11 = r11 + r8
            byte r11 = r3[r11]
            r11 = r11 & 255(0xff, float:3.57E-43)
            if (r11 != r12) goto L_0x00e2
            int r9 = r9 + 1
            goto L_0x00d1
        L_0x00e2:
            if (r9 >= r5) goto L_0x00fe
            if (r11 >= r12) goto L_0x00fe
            r15 = r9
        L_0x00e7:
            int r9 = r9 + r14
            if (r9 >= r5) goto L_0x00ff
            r11 = r2[r9]
            int r11 = r11 + r1
            r11 = r2[r11]
            int r11 = r11 + r8
            byte r11 = r3[r11]
            r11 = r11 & 255(0xff, float:3.57E-43)
            if (r11 > r12) goto L_0x00ff
            if (r11 != r12) goto L_0x00e7
            swapElements(r2, r9, r2, r15)
            int r15 = r15 + 1
            goto L_0x00e7
        L_0x00fe:
            r15 = r9
        L_0x00ff:
            int r16 = r5 + -1
            r19 = r16
            r16 = r11
            r11 = r19
        L_0x0107:
            if (r9 >= r11) goto L_0x011d
            r16 = r2[r11]
            int r16 = r1 + r16
            r16 = r2[r16]
            int r16 = r8 + r16
            byte r14 = r3[r16]
            r14 = r14 & 255(0xff, float:3.57E-43)
            if (r14 != r12) goto L_0x011f
            int r11 = r11 + -1
            r16 = r14
            r14 = 1
            goto L_0x0107
        L_0x011d:
            r14 = r16
        L_0x011f:
            if (r9 >= r11) goto L_0x0145
            if (r14 <= r12) goto L_0x0145
            r16 = r14
            r14 = r11
        L_0x0126:
            int r11 = r11 + r7
            if (r9 >= r11) goto L_0x0148
            r16 = r2[r11]
            int r16 = r1 + r16
            r16 = r2[r16]
            int r16 = r8 + r16
            byte r7 = r3[r16]
            r7 = r7 & 255(0xff, float:3.57E-43)
            if (r7 < r12) goto L_0x0142
            if (r7 != r12) goto L_0x013e
            swapElements(r2, r11, r2, r14)
            int r14 = r14 + -1
        L_0x013e:
            r16 = r7
            r7 = -1
            goto L_0x0126
        L_0x0142:
            r16 = r7
            goto L_0x0148
        L_0x0145:
            r16 = r14
            r14 = r11
        L_0x0148:
            if (r9 >= r11) goto L_0x0183
            swapElements(r2, r9, r2, r11)
        L_0x014d:
            r7 = 1
            int r9 = r9 + r7
            if (r9 >= r11) goto L_0x0169
            r7 = r2[r9]
            int r7 = r7 + r1
            r7 = r2[r7]
            int r7 = r7 + r8
            byte r7 = r3[r7]
            r7 = r7 & 255(0xff, float:3.57E-43)
            if (r7 > r12) goto L_0x0167
            if (r7 != r12) goto L_0x0164
            swapElements(r2, r9, r2, r15)
            int r15 = r15 + 1
        L_0x0164:
            r16 = r7
            goto L_0x014d
        L_0x0167:
            r16 = r7
        L_0x0169:
            r7 = -1
            int r11 = r11 + r7
            if (r9 >= r11) goto L_0x0148
            r16 = r2[r11]
            int r16 = r1 + r16
            r16 = r2[r16]
            int r16 = r8 + r16
            byte r7 = r3[r16]
            r7 = r7 & 255(0xff, float:3.57E-43)
            if (r7 < r12) goto L_0x0142
            if (r7 != r12) goto L_0x0167
            swapElements(r2, r11, r2, r14)
            int r14 = r14 + -1
            goto L_0x0167
        L_0x0183:
            if (r15 > r14) goto L_0x0291
            int r7 = r9 + -1
            int r11 = r15 - r6
            int r15 = r9 - r15
            if (r11 <= r15) goto L_0x018e
            r11 = r15
        L_0x018e:
            int r17 = r9 - r11
            r23 = r13
            r13 = r17
            r17 = r9
            r9 = r6
        L_0x0197:
            if (r11 <= 0) goto L_0x01a5
            swapElements(r2, r9, r2, r13)
            int r11 = r11 + -1
            r18 = 1
            int r9 = r9 + 1
            int r13 = r13 + 1
            goto L_0x0197
        L_0x01a5:
            r18 = 1
            int r7 = r14 - r7
            int r9 = r5 - r14
            int r9 = r9 + -1
            if (r7 <= r9) goto L_0x01b0
            goto L_0x01b1
        L_0x01b0:
            r9 = r7
        L_0x01b1:
            int r11 = r5 - r9
            r13 = r11
            r11 = r17
        L_0x01b6:
            if (r9 <= 0) goto L_0x01c2
            swapElements(r2, r11, r2, r13)
            int r9 = r9 + -1
            int r11 = r11 + 1
            int r13 = r13 + 1
            goto L_0x01b6
        L_0x01c2:
            int r9 = r6 + r15
            int r7 = r5 - r7
            r11 = r2[r9]
            int r11 = r11 + r1
            r11 = r2[r11]
            int r11 = r11 + r8
            int r11 = r11 + -1
            byte r11 = r3[r11]
            r11 = r11 & 255(0xff, float:3.57E-43)
            if (r12 > r11) goto L_0x01d6
            r11 = r9
            goto L_0x01da
        L_0x01d6:
            int r11 = r0.ssSubstringPartition(r1, r9, r7, r8)
        L_0x01da:
            int r12 = r9 - r6
            int r13 = r5 - r7
            if (r12 > r13) goto L_0x0236
            int r14 = r7 - r11
            if (r13 > r14) goto L_0x0200
            int r12 = r10 + 1
            io.netty.handler.codec.compression.Bzip2DivSufSort$StackEntry r13 = new io.netty.handler.codec.compression.Bzip2DivSufSort$StackEntry
            int r15 = r8 + 1
            int r14 = ssLog(r14)
            r13.<init>(r11, r7, r15, r14)
            r4[r10] = r13
            int r10 = r12 + 1
            io.netty.handler.codec.compression.Bzip2DivSufSort$StackEntry r11 = new io.netty.handler.codec.compression.Bzip2DivSufSort$StackEntry
            r15 = r23
            r11.<init>(r7, r5, r8, r15)
            r4[r12] = r11
        L_0x01fe:
            r5 = r9
            goto L_0x0257
        L_0x0200:
            r15 = r23
            if (r12 > r14) goto L_0x021d
            int r12 = r10 + 1
            io.netty.handler.codec.compression.Bzip2DivSufSort$StackEntry r13 = new io.netty.handler.codec.compression.Bzip2DivSufSort$StackEntry
            r13.<init>(r7, r5, r8, r15)
            r4[r10] = r13
            int r10 = r12 + 1
            io.netty.handler.codec.compression.Bzip2DivSufSort$StackEntry r5 = new io.netty.handler.codec.compression.Bzip2DivSufSort$StackEntry
            int r13 = r8 + 1
            int r14 = ssLog(r14)
            r5.<init>(r11, r7, r13, r14)
            r4[r12] = r5
            goto L_0x01fe
        L_0x021d:
            int r12 = r10 + 1
            io.netty.handler.codec.compression.Bzip2DivSufSort$StackEntry r13 = new io.netty.handler.codec.compression.Bzip2DivSufSort$StackEntry
            r13.<init>(r7, r5, r8, r15)
            r4[r10] = r13
            int r10 = r12 + 1
            io.netty.handler.codec.compression.Bzip2DivSufSort$StackEntry r5 = new io.netty.handler.codec.compression.Bzip2DivSufSort$StackEntry
            r5.<init>(r6, r9, r8, r15)
            r4[r12] = r5
            int r8 = r8 + 1
            int r9 = ssLog(r14)
            goto L_0x028e
        L_0x0236:
            r15 = r23
            int r14 = r7 - r11
            if (r12 > r14) goto L_0x0259
            int r12 = r10 + 1
            io.netty.handler.codec.compression.Bzip2DivSufSort$StackEntry r13 = new io.netty.handler.codec.compression.Bzip2DivSufSort$StackEntry
            int r0 = r8 + 1
            int r14 = ssLog(r14)
            r13.<init>(r11, r7, r0, r14)
            r4[r10] = r13
            int r10 = r12 + 1
            io.netty.handler.codec.compression.Bzip2DivSufSort$StackEntry r0 = new io.netty.handler.codec.compression.Bzip2DivSufSort$StackEntry
            r0.<init>(r6, r9, r8, r15)
            r4[r12] = r0
        L_0x0254:
            r0 = r20
            r6 = r7
        L_0x0257:
            r9 = r15
            goto L_0x02b5
        L_0x0259:
            if (r13 > r14) goto L_0x0274
            int r0 = r10 + 1
            io.netty.handler.codec.compression.Bzip2DivSufSort$StackEntry r12 = new io.netty.handler.codec.compression.Bzip2DivSufSort$StackEntry
            r12.<init>(r6, r9, r8, r15)
            r4[r10] = r12
            int r10 = r0 + 1
            io.netty.handler.codec.compression.Bzip2DivSufSort$StackEntry r6 = new io.netty.handler.codec.compression.Bzip2DivSufSort$StackEntry
            int r9 = r8 + 1
            int r12 = ssLog(r14)
            r6.<init>(r11, r7, r9, r12)
            r4[r0] = r6
            goto L_0x0254
        L_0x0274:
            int r0 = r10 + 1
            io.netty.handler.codec.compression.Bzip2DivSufSort$StackEntry r12 = new io.netty.handler.codec.compression.Bzip2DivSufSort$StackEntry
            r12.<init>(r6, r9, r8, r15)
            r4[r10] = r12
            int r10 = r0 + 1
            io.netty.handler.codec.compression.Bzip2DivSufSort$StackEntry r6 = new io.netty.handler.codec.compression.Bzip2DivSufSort$StackEntry
            r6.<init>(r7, r5, r8, r15)
            r4[r0] = r6
            int r8 = r8 + 1
            int r9 = ssLog(r14)
            r0 = r20
        L_0x028e:
            r5 = r7
            r6 = r11
            goto L_0x02b5
        L_0x0291:
            r15 = r13
            int r13 = r15 + 1
            r0 = r2[r6]
            int r0 = r0 + r1
            r0 = r2[r0]
            int r0 = r0 + r8
            r7 = 1
            int r0 = r0 - r7
            byte r0 = r3[r0]
            r0 = r0 & 255(0xff, float:3.57E-43)
            if (r0 >= r12) goto L_0x02b0
            r0 = r20
            int r6 = r0.ssSubstringPartition(r1, r6, r5, r8)
            int r7 = r5 - r6
            int r7 = ssLog(r7)
            r9 = r7
            goto L_0x02b3
        L_0x02b0:
            r0 = r20
            r9 = r13
        L_0x02b3:
            int r8 = r8 + 1
        L_0x02b5:
            r11 = r16
            r7 = -1
            goto L_0x001d
        */
        throw new UnsupportedOperationException("Method not decompiled: p043io.netty.handler.codec.compression.Bzip2DivSufSort.ssMultiKeyIntroSort(int, int, int, int):void");
    }

    private static void ssBlockSwap(int[] iArr, int i, int[] iArr2, int i2, int i3) {
        while (i3 > 0) {
            swapElements(iArr, i, iArr2, i2);
            i3--;
            i++;
            i2++;
        }
    }

    private void ssMergeForward(int i, int[] iArr, int i2, int i3, int i4, int i5, int i6) {
        int i7;
        int[] iArr2 = this.f3714SA;
        int i8 = i4 - i3;
        int i9 = (i2 + i8) - 1;
        ssBlockSwap(iArr, i2, iArr2, i3, i8);
        int i10 = iArr2[i3];
        while (true) {
            int ssCompare = ssCompare(iArr[i2] + i, iArr2[i4] + i, i6);
            if (ssCompare < 0) {
                while (true) {
                    i7 = i3 + 1;
                    iArr2[i3] = iArr[i2];
                    if (i9 <= i2) {
                        iArr[i2] = i10;
                        return;
                    }
                    int i11 = i2 + 1;
                    iArr[i2] = iArr2[i7];
                    if (iArr[i11] >= 0) {
                        i2 = i11;
                        break;
                    } else {
                        i2 = i11;
                        i3 = i7;
                    }
                }
            } else if (ssCompare > 0) {
                while (true) {
                    i7 = i3 + 1;
                    iArr2[i3] = iArr2[i4];
                    int i12 = i4 + 1;
                    iArr2[i4] = iArr2[i7];
                    if (i5 <= i12) {
                        while (i2 < i9) {
                            int i13 = i7 + 1;
                            iArr2[i7] = iArr[i2];
                            int i14 = i2 + 1;
                            iArr[i2] = iArr2[i13];
                            i7 = i13;
                            i2 = i14;
                        }
                        iArr2[i7] = iArr[i2];
                        iArr[i2] = i10;
                        return;
                    } else if (iArr2[i12] >= 0) {
                        i4 = i12;
                        break;
                    } else {
                        i4 = i12;
                        i3 = i7;
                    }
                }
            } else {
                iArr2[i4] = iArr2[i4] ^ -1;
                while (true) {
                    int i15 = i3 + 1;
                    iArr2[i3] = iArr[i2];
                    if (i9 <= i2) {
                        iArr[i2] = i10;
                        return;
                    }
                    int i16 = i2 + 1;
                    iArr[i2] = iArr2[i15];
                    if (iArr[i16] >= 0) {
                        while (true) {
                            int i17 = i15 + 1;
                            iArr2[i15] = iArr2[i4];
                            int i18 = i4 + 1;
                            iArr2[i4] = iArr2[i17];
                            if (i5 <= i18) {
                                while (i16 < i9) {
                                    int i19 = i17 + 1;
                                    iArr2[i17] = iArr[i16];
                                    int i20 = i16 + 1;
                                    iArr[i16] = iArr2[i19];
                                    i16 = i20;
                                    i17 = i19;
                                }
                                iArr2[i17] = iArr[i16];
                                iArr[i16] = i10;
                                return;
                            } else if (iArr2[i18] >= 0) {
                                i4 = i18;
                                int i21 = i16;
                                i3 = i17;
                                i2 = i21;
                                break;
                            } else {
                                i4 = i18;
                                i15 = i17;
                            }
                        }
                    } else {
                        i2 = i16;
                        i3 = i15;
                    }
                }
            }
            i3 = i7;
        }
    }

    private void ssMergeBackward(int i, int[] iArr, int i2, int i3, int i4, int i5, int i6) {
        boolean z;
        int i7;
        int i8;
        int i9;
        int i10;
        int i11;
        int i12;
        int i13;
        int i14;
        int i15;
        int i16;
        int i17;
        int i18;
        int i19;
        int i20;
        int i21;
        int[] iArr2 = this.f3714SA;
        int i22 = i5 - i4;
        int i23 = i2 + i22;
        ssBlockSwap(iArr, i2, iArr2, i4, i22);
        int i24 = i23 - 1;
        if (iArr[i24] < 0) {
            i7 = (iArr[i24] ^ -1) + i;
            z = true;
        } else {
            i7 = iArr[i24] + i;
            z = false;
        }
        int i25 = i4 - 1;
        if (iArr2[i25] < 0) {
            z |= true;
            i8 = iArr2[i25] ^ -1;
        } else {
            i8 = iArr2[i25];
        }
        int i26 = i8 + i;
        int i27 = i5 - 1;
        int i28 = iArr2[i27];
        while (true) {
            int ssCompare = ssCompare(i7, i26, i6);
            if (ssCompare > 0) {
                if (z && true) {
                    while (true) {
                        i20 = i27 - 1;
                        iArr2[i27] = iArr[i24];
                        i21 = i24 - 1;
                        iArr[i24] = iArr2[i20];
                        if (iArr[i21] >= 0) {
                            break;
                        }
                        i24 = i21;
                        i27 = i20;
                    }
                    z = !z;
                    i24 = i21;
                    i27 = i20;
                }
                int i29 = i27 - 1;
                iArr2[i27] = iArr[i24];
                if (i24 <= i2) {
                    iArr[i24] = i28;
                    return;
                }
                int i30 = i24 - 1;
                iArr[i24] = iArr2[i29];
                if (iArr[i30] < 0) {
                    z |= true;
                    i19 = iArr[i30] ^ -1;
                } else {
                    i19 = iArr[i30];
                }
                int i31 = i19 + i;
                i24 = i30;
                i27 = i29;
                i7 = i31;
            } else {
                if (ssCompare < 0) {
                    if (z && true) {
                        while (true) {
                            i17 = i27 - 1;
                            iArr2[i27] = iArr2[i25];
                            i18 = i25 - 1;
                            iArr2[i25] = iArr2[i17];
                            if (iArr2[i18] >= 0) {
                                break;
                            }
                            i25 = i18;
                            i27 = i17;
                        }
                        z ^= true;
                        i25 = i18;
                        i27 = i17;
                    }
                    int i32 = i27 - 1;
                    iArr2[i27] = iArr2[i25];
                    int i33 = i25 - 1;
                    iArr2[i25] = iArr2[i32];
                    if (i33 < i3) {
                        while (i2 < i24) {
                            int i34 = i32 - 1;
                            iArr2[i32] = iArr[i24];
                            int i35 = i24 - 1;
                            iArr[i24] = iArr2[i34];
                            i32 = i34;
                            i24 = i35;
                        }
                        iArr2[i32] = iArr[i24];
                        iArr[i24] = i28;
                        return;
                    }
                    if (iArr2[i33] < 0) {
                        z |= true;
                        i16 = iArr2[i33] ^ -1;
                    } else {
                        i16 = iArr2[i33];
                    }
                    i12 = i32;
                    i26 = i16 + i;
                    i25 = i33;
                } else {
                    if (z && true) {
                        while (true) {
                            i14 = i27 - 1;
                            iArr2[i27] = iArr[i24];
                            i15 = i24 - 1;
                            iArr[i24] = iArr2[i14];
                            if (iArr[i15] >= 0) {
                                break;
                            }
                            i24 = i15;
                            i27 = i14;
                        }
                        z = !z;
                        i24 = i15;
                        i27 = i14;
                    }
                    int i36 = i27 - 1;
                    iArr2[i27] = iArr[i24] ^ -1;
                    if (i24 <= i2) {
                        iArr[i24] = i28;
                        return;
                    }
                    int i37 = i24 - 1;
                    iArr[i24] = iArr2[i36];
                    if (z && true) {
                        while (true) {
                            i9 = i36 - 1;
                            iArr2[i36] = iArr2[i25];
                            i13 = i25 - 1;
                            iArr2[i25] = iArr2[i9];
                            if (iArr2[i13] >= 0) {
                                break;
                            }
                            i25 = i13;
                            i36 = i9;
                        }
                        z ^= true;
                        i25 = i13;
                    } else {
                        i9 = i36;
                    }
                    int i38 = i9 - 1;
                    iArr2[i9] = iArr2[i25];
                    int i39 = i25 - 1;
                    iArr2[i25] = iArr2[i38];
                    if (i39 < i3) {
                        while (i2 < i37) {
                            int i40 = i38 - 1;
                            iArr2[i38] = iArr[i37];
                            int i41 = i37 - 1;
                            iArr[i37] = iArr2[i40];
                            i38 = i40;
                            i37 = i41;
                        }
                        iArr2[i38] = iArr[i37];
                        iArr[i37] = i28;
                        return;
                    }
                    if (iArr[i37] < 0) {
                        z |= true;
                        i10 = (iArr[i37] ^ -1) + i;
                    } else {
                        i10 = iArr[i37] + i;
                    }
                    if (iArr2[i39] < 0) {
                        z |= true;
                        i11 = iArr2[i39] ^ -1;
                    } else {
                        i11 = iArr2[i39];
                    }
                    i26 = i11 + i;
                    i12 = i38;
                    i7 = i10;
                    i25 = i39;
                    i24 = i37;
                }
                i27 = i12;
            }
        }
    }

    private void ssMergeCheckEqual(int i, int i2, int i3) {
        int[] iArr = this.f3714SA;
        if (iArr[i3] >= 0 && ssCompare(getIDX(iArr[i3 - 1]) + i, i + iArr[i3], i2) == 0) {
            iArr[i3] = iArr[i3] ^ -1;
        }
    }

    private void ssMerge(int i, int i2, int i3, int i4, int[] iArr, int i5, int i6, int i7) {
        int i8;
        int i9;
        int i10;
        int i11;
        int i12;
        int i13 = i;
        int i14 = i6;
        int i15 = i7;
        int[] iArr2 = this.f3714SA;
        StackEntry[] stackEntryArr = new StackEntry[64];
        int i16 = i2;
        int i17 = i3;
        int i18 = i4;
        int i19 = 0;
        int i20 = 0;
        while (true) {
            int i21 = i18 - i17;
            if (i21 <= i14) {
                if (i16 >= i17 || i17 >= i18) {
                    i12 = i16;
                } else {
                    i12 = i16;
                    ssMergeBackward(i, iArr, i5, i16, i17, i18, i7);
                }
                if ((i19 & 1) != 0) {
                    ssMergeCheckEqual(i13, i15, i12);
                }
                if ((i19 & 2) != 0) {
                    ssMergeCheckEqual(i13, i15, i18);
                }
                if (i20 != 0) {
                    i20--;
                    StackEntry stackEntry = stackEntryArr[i20];
                    i8 = stackEntry.f3717a;
                    i17 = stackEntry.f3718b;
                    i18 = stackEntry.f3719c;
                    i9 = stackEntry.f3720d;
                } else {
                    return;
                }
            } else {
                int i22 = i16;
                int i23 = i17 - i22;
                if (i23 <= i14) {
                    if (i22 < i17) {
                        ssMergeForward(i, iArr, i5, i22, i17, i18, i7);
                    }
                    if ((i19 & 1) != 0) {
                        ssMergeCheckEqual(i13, i15, i22);
                    }
                    if ((i19 & 2) != 0) {
                        ssMergeCheckEqual(i13, i15, i18);
                    }
                    if (i20 != 0) {
                        i20--;
                        StackEntry stackEntry2 = stackEntryArr[i20];
                        i8 = stackEntry2.f3717a;
                        i17 = stackEntry2.f3718b;
                        i18 = stackEntry2.f3719c;
                        i9 = stackEntry2.f3720d;
                    } else {
                        return;
                    }
                } else {
                    int min = Math.min(i23, i21);
                    int i24 = min >> 1;
                    int i25 = 0;
                    while (min > 0) {
                        if (ssCompare(getIDX(iArr2[i17 + i25 + i24]) + i13, getIDX(iArr2[((i17 - i25) - i24) - 1]) + i13, i15) < 0) {
                            i25 += i24 + 1;
                            i24 -= (min & 1) ^ 1;
                        }
                        min = i24;
                        i24 = min >> 1;
                    }
                    if (i25 > 0) {
                        int i26 = i17 - i25;
                        ssBlockSwap(iArr2, i26, iArr2, i17, i25);
                        int i27 = i25 + i17;
                        if (i27 < i18) {
                            if (iArr2[i27] < 0) {
                                i11 = i17;
                                while (iArr2[i11 - 1] < 0) {
                                    i11--;
                                }
                                iArr2[i27] = iArr2[i27] ^ -1;
                            } else {
                                i11 = i17;
                            }
                            int i28 = i17;
                            while (iArr2[i28] < 0) {
                                i28++;
                            }
                            i16 = i28;
                            i10 = 1;
                        } else {
                            i11 = i17;
                            i16 = i11;
                            i10 = 0;
                        }
                        if (i11 - i22 <= i18 - i16) {
                            int i29 = i20 + 1;
                            stackEntryArr[i20] = new StackEntry(i16, i27, i18, (i10 & 1) | (i19 & 2));
                            i19 &= 1;
                            i17 = i26;
                            i18 = i11;
                            i20 = i29;
                            i16 = i22;
                        } else {
                            if (i11 == i17 && i17 == i16) {
                                i10 <<= 1;
                            }
                            int i30 = i20 + 1;
                            stackEntryArr[i20] = new StackEntry(i22, i26, i11, (i19 & 1) | (i10 & 2));
                            i19 = (i19 & 2) | (1 & i10);
                            i17 = i27;
                            i20 = i30;
                        }
                    } else {
                        if ((i19 & 1) != 0) {
                            ssMergeCheckEqual(i13, i15, i22);
                        }
                        ssMergeCheckEqual(i13, i15, i17);
                        if ((i19 & 2) != 0) {
                            ssMergeCheckEqual(i13, i15, i18);
                        }
                        if (i20 != 0) {
                            i20--;
                            StackEntry stackEntry3 = stackEntryArr[i20];
                            i8 = stackEntry3.f3717a;
                            i17 = stackEntry3.f3718b;
                            i18 = stackEntry3.f3719c;
                            i9 = stackEntry3.f3720d;
                        } else {
                            return;
                        }
                    }
                }
            }
            i19 = i9;
        }
    }

    private void subStringSort(int i, int i2, int i3, int[] iArr, int i4, int i5, int i6, boolean z, int i7) {
        int[] iArr2;
        int i8;
        int i9;
        int i10 = i;
        int i11 = i3;
        int i12 = i6;
        int[] iArr3 = this.f3714SA;
        int i13 = z ? i2 + 1 : i2;
        int i14 = i13;
        int i15 = 0;
        while (true) {
            int i16 = i14 + 1024;
            if (i16 >= i11) {
                break;
            }
            ssMultiKeyIntroSort(i10, i14, i16, i12);
            int i17 = i11 - i16;
            int i18 = i5;
            if (i17 <= i18) {
                iArr2 = iArr;
                i9 = i4;
                i8 = i18;
            } else {
                i8 = i17;
                i9 = i16;
                iArr2 = iArr3;
            }
            int i19 = i14;
            int i20 = i15;
            int i21 = 1024;
            while ((i20 & 1) != 0) {
                int i22 = i19 - i21;
                int i23 = i16;
                ssMerge(i, i22, i19, i19 + i21, iArr2, i9, i8, i6);
                i21 <<= 1;
                i20 >>>= 1;
                int i24 = i5;
                i19 = i22;
                i16 = i23;
            }
            i15++;
            i14 = i16;
        }
        ssMultiKeyIntroSort(i10, i14, i11, i12);
        int i25 = i14;
        int i26 = 1024;
        while (i15 != 0) {
            if ((i15 & 1) != 0) {
                int i27 = i25 - i26;
                ssMerge(i, i27, i25, i3, iArr, i4, i5, i6);
                i25 = i27;
            }
            i26 <<= 1;
            i15 >>= 1;
        }
        if (z) {
            int i28 = iArr3[i13 - 1];
            int i29 = 1;
            while (i13 < i11) {
                if (iArr3[i13] >= 0) {
                    i29 = ssCompareLast(i, i10 + i28, i10 + iArr3[i13], i6, i7);
                    if (i29 <= 0) {
                        break;
                    }
                }
                iArr3[i13 - 1] = iArr3[i13];
                i13++;
            }
            if (i29 == 0) {
                iArr3[i13] = iArr3[i13] ^ -1;
            }
            iArr3[i13 - 1] = i28;
        }
    }

    private int trGetC(int i, int i2, int i3, int i4) {
        int i5 = i2 + i4;
        return i5 < i3 ? this.f3714SA[i5] : this.f3714SA[i + (((i2 - i) + i4) % (i3 - i))];
    }

    private void trFixdown(int i, int i2, int i3, int i4, int i5, int i6) {
        int[] iArr = this.f3714SA;
        int i7 = iArr[i4 + i5];
        int trGetC = trGetC(i, i2, i3, i7);
        while (true) {
            int i8 = (i5 * 2) + 1;
            if (i8 >= i6) {
                break;
            }
            int i9 = i8 + 1;
            int trGetC2 = trGetC(i, i2, i3, iArr[i4 + i8]);
            int trGetC3 = trGetC(i, i2, i3, iArr[i4 + i9]);
            if (trGetC2 < trGetC3) {
                i8 = i9;
                trGetC2 = trGetC3;
            }
            if (trGetC2 <= trGetC) {
                break;
            }
            iArr[i5 + i4] = iArr[i4 + i8];
            i5 = i8;
        }
        iArr[i4 + i5] = i7;
    }

    private void trHeapSort(int i, int i2, int i3, int i4, int i5) {
        int i6;
        int i7 = i;
        int i8 = i2;
        int i9 = i3;
        int i10 = i4;
        int[] iArr = this.f3714SA;
        int i11 = i5 % 2;
        if (i11 == 0) {
            int i12 = i5 - 1;
            int i13 = (i12 / 2) + i10;
            int i14 = i10 + i12;
            if (trGetC(i7, i8, i9, iArr[i13]) < trGetC(i7, i8, i9, iArr[i14])) {
                swapElements(iArr, i14, iArr, i13);
            }
            i6 = i12;
        } else {
            i6 = i5;
        }
        for (int i15 = (i6 / 2) - 1; i15 >= 0; i15--) {
            trFixdown(i, i2, i3, i4, i15, i6);
        }
        if (i11 == 0) {
            swapElements(iArr, i10, iArr, i10 + i6);
            trFixdown(i, i2, i3, i4, 0, i6);
        }
        for (int i16 = i6 - 1; i16 > 0; i16--) {
            int i17 = iArr[i10];
            int i18 = i10 + i16;
            iArr[i10] = iArr[i18];
            trFixdown(i, i2, i3, i4, 0, i16);
            iArr[i18] = i17;
        }
    }

    private void trInsertionSort(int i, int i2, int i3, int i4, int i5) {
        int trGetC;
        int[] iArr = this.f3714SA;
        for (int i6 = i4 + 1; i6 < i5; i6++) {
            int i7 = iArr[i6];
            int i8 = i6 - 1;
            do {
                trGetC = trGetC(i, i2, i3, i7) - trGetC(i, i2, i3, iArr[i8]);
                if (trGetC >= 0) {
                    break;
                }
                do {
                    iArr[i8 + 1] = iArr[i8];
                    i8--;
                    if (i4 > i8) {
                        break;
                    }
                } while (iArr[i8] < 0);
                continue;
            } while (i8 >= i4);
            if (trGetC == 0) {
                iArr[i8] = iArr[i8] ^ -1;
            }
            iArr[i8 + 1] = i7;
        }
    }

    private static int trLog(int i) {
        return (-65536 & i) != 0 ? (-16777216 & i) != 0 ? LOG_2_TABLE[(i >> 24) & 255] + 24 : LOG_2_TABLE[(i >> 16) & 271] : (65280 & i) != 0 ? LOG_2_TABLE[(i >> 8) & 255] + 8 : LOG_2_TABLE[i & 255];
    }

    private int trMedian3(int i, int i2, int i3, int i4, int i5, int i6) {
        int[] iArr = this.f3714SA;
        int trGetC = trGetC(i, i2, i3, iArr[i4]);
        int trGetC2 = trGetC(i, i2, i3, iArr[i5]);
        int trGetC3 = trGetC(i, i2, i3, iArr[i6]);
        if (trGetC <= trGetC2) {
            int i7 = i5;
            i5 = i4;
            i4 = i7;
            int i8 = trGetC2;
            trGetC2 = trGetC;
            trGetC = i8;
        }
        if (trGetC > trGetC3) {
            return trGetC2 > trGetC3 ? i5 : i6;
        }
        return i4;
    }

    private int trMedian5(int i, int i2, int i3, int i4, int i5, int i6, int i7, int i8) {
        int[] iArr = this.f3714SA;
        int trGetC = trGetC(i, i2, i3, iArr[i4]);
        int trGetC2 = trGetC(i, i2, i3, iArr[i5]);
        int trGetC3 = trGetC(i, i2, i3, iArr[i6]);
        int trGetC4 = trGetC(i, i2, i3, iArr[i7]);
        int trGetC5 = trGetC(i, i2, i3, iArr[i8]);
        if (trGetC2 > trGetC3) {
            int i9 = i6;
            i6 = i5;
            i5 = i9;
            int i10 = trGetC3;
            trGetC3 = trGetC2;
            trGetC2 = i10;
        }
        if (trGetC4 <= trGetC5) {
            int i11 = trGetC4;
            trGetC4 = trGetC5;
            trGetC5 = i11;
            int i12 = i8;
            i8 = i7;
            i7 = i12;
        }
        if (trGetC2 > trGetC5) {
            trGetC5 = trGetC2;
            int i13 = i7;
            i7 = i6;
            i6 = i13;
            int i14 = trGetC4;
            trGetC4 = trGetC3;
            trGetC3 = i14;
        } else {
            i5 = i8;
        }
        if (trGetC > trGetC3) {
            int i15 = i6;
            i6 = i4;
            i4 = i15;
            int i16 = trGetC3;
            trGetC3 = trGetC;
            trGetC = i16;
        }
        if (trGetC > trGetC5) {
            i6 = i7;
            trGetC5 = trGetC;
            trGetC3 = trGetC4;
        } else {
            i4 = i5;
        }
        return trGetC3 > trGetC5 ? i4 : i6;
    }

    private int trPivot(int i, int i2, int i3, int i4, int i5) {
        int i6 = i5 - i4;
        int i7 = i4 + (i6 / 2);
        if (i6 > 512) {
            int i8 = i6 >> 3;
            int i9 = i8 << 1;
            int i10 = i;
            int i11 = i5 - 1;
            return trMedian3(i, i2, i3, trMedian3(i10, i2, i3, i4, i4 + i8, i4 + i9), trMedian3(i, i2, i3, i7 - i8, i7, i7 + i8), trMedian3(i10, i2, i3, i11 - i9, i11 - i8, i11));
        } else if (i6 <= 32) {
            return trMedian3(i, i2, i3, i4, i7, i5 - 1);
        } else {
            int i12 = i6 >> 2;
            int i13 = i5 - 1;
            return trMedian5(i, i2, i3, i4, i4 + i12, i7, i13 - i12, i13);
        }
    }

    private void lsUpdateGroup(int i, int i2, int i3) {
        int[] iArr = this.f3714SA;
        while (i2 < i3) {
            if (iArr[i2] >= 0) {
                int i4 = i2;
                do {
                    iArr[iArr[i4] + i] = i4;
                    i4++;
                    if (i4 >= i3) {
                        break;
                    }
                } while (iArr[i4] >= 0);
                iArr[i2] = i2 - i4;
                if (i3 > i4) {
                    i2 = i4;
                } else {
                    return;
                }
            }
            int i5 = i2;
            do {
                iArr[i5] = iArr[i5] ^ -1;
                i5++;
            } while (iArr[i5] < 0);
            do {
                iArr[iArr[i2] + i] = i5;
                i2++;
            } while (i2 <= i5);
            i2 = i5 + 1;
        }
    }

    /*  JADX ERROR: JadxRuntimeException in pass: RegionMakerVisitor
        jadx.core.utils.exceptions.JadxRuntimeException: Regions count limit reached
        	at jadx.core.dex.visitors.regions.RegionMaker.makeRegion(RegionMaker.java:89)
        	at jadx.core.dex.visitors.regions.RegionMaker.makeEndlessLoop(RegionMaker.java:368)
        	at jadx.core.dex.visitors.regions.RegionMaker.processLoop(RegionMaker.java:172)
        	at jadx.core.dex.visitors.regions.RegionMaker.traverse(RegionMaker.java:106)
        	at jadx.core.dex.visitors.regions.RegionMaker.makeRegion(RegionMaker.java:86)
        	at jadx.core.dex.visitors.regions.RegionMaker.processIf(RegionMaker.java:695)
        	at jadx.core.dex.visitors.regions.RegionMaker.traverse(RegionMaker.java:123)
        	at jadx.core.dex.visitors.regions.RegionMaker.makeRegion(RegionMaker.java:86)
        	at jadx.core.dex.visitors.regions.RegionMaker.processIf(RegionMaker.java:695)
        	at jadx.core.dex.visitors.regions.RegionMaker.traverse(RegionMaker.java:123)
        	at jadx.core.dex.visitors.regions.RegionMaker.makeRegion(RegionMaker.java:86)
        	at jadx.core.dex.visitors.regions.RegionMaker.makeEndlessLoop(RegionMaker.java:368)
        	at jadx.core.dex.visitors.regions.RegionMaker.processLoop(RegionMaker.java:172)
        	at jadx.core.dex.visitors.regions.RegionMaker.traverse(RegionMaker.java:106)
        	at jadx.core.dex.visitors.regions.RegionMaker.makeRegion(RegionMaker.java:86)
        	at jadx.core.dex.visitors.regions.RegionMakerVisitor.visit(RegionMakerVisitor.java:49)
        	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:27)
        	at jadx.core.dex.visitors.DepthTraversal.lambda$visit$1(DepthTraversal.java:14)
        	at java.base/java.util.ArrayList.forEach(ArrayList.java:1540)
        	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:14)
        	at jadx.core.ProcessClass.process(ProcessClass.java:30)
        	at jadx.core.ProcessClass.lambda$processDependencies$0(ProcessClass.java:49)
        	at java.base/java.util.ArrayList.forEach(ArrayList.java:1540)
        	at jadx.core.ProcessClass.processDependencies(ProcessClass.java:49)
        	at jadx.core.ProcessClass.process(ProcessClass.java:35)
        	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:311)
        	at jadx.api.JavaClass.decompile(JavaClass.java:62)
        	at jadx.api.JadxDecompiler.lambda$appendSourcesSave$0(JadxDecompiler.java:217)
        */
    /* JADX WARNING: Removed duplicated region for block: B:106:0x01bf  */
    /* JADX WARNING: Removed duplicated region for block: B:135:0x0132 A[SYNTHETIC] */
    /* JADX WARNING: Removed duplicated region for block: B:141:0x0119 A[SYNTHETIC] */
    /* JADX WARNING: Removed duplicated region for block: B:57:0x0104  */
    /* JADX WARNING: Removed duplicated region for block: B:63:0x0114  */
    /* JADX WARNING: Removed duplicated region for block: B:68:0x0122  */
    /* JADX WARNING: Removed duplicated region for block: B:73:0x0134  */
    private void lsIntroSort(int r21, int r22, int r23, int r24, int r25) {
        /*
            r20 = this;
            r6 = r20
            r7 = r21
            r8 = r22
            r9 = r23
            int[] r10 = r6.f3714SA
            r0 = 64
            io.netty.handler.codec.compression.Bzip2DivSufSort$StackEntry[] r11 = new p043io.netty.handler.codec.compression.Bzip2DivSufSort.StackEntry[r0]
            int r0 = r25 - r24
            int r0 = trLog(r0)
            r14 = r24
            r13 = r25
            r15 = 0
            r16 = 0
        L_0x001b:
            int r5 = r13 - r14
            r1 = 8
            r17 = -1
            r4 = 1
            if (r5 > r1) goto L_0x0049
            if (r4 >= r5) goto L_0x0037
            r0 = r20
            r1 = r21
            r2 = r22
            r3 = r23
            r4 = r14
            r5 = r13
            r0.trInsertionSort(r1, r2, r3, r4, r5)
            r6.lsUpdateGroup(r7, r14, r13)
            goto L_0x003b
        L_0x0037:
            if (r5 != r4) goto L_0x003b
            r10[r14] = r17
        L_0x003b:
            if (r15 != 0) goto L_0x003e
            return
        L_0x003e:
            int r15 = r15 + -1
            r0 = r11[r15]
            int r14 = r0.f3717a
            int r13 = r0.f3718b
            int r0 = r0.f3719c
            goto L_0x001b
        L_0x0049:
            int r3 = r0 + -1
            if (r0 != 0) goto L_0x008c
            r0 = r20
            r1 = r21
            r2 = r22
            r3 = r23
            r4 = r14
            r0.trHeapSort(r1, r2, r3, r4, r5)
            int r0 = r13 + -1
        L_0x005b:
            if (r14 >= r0) goto L_0x007b
            r1 = r10[r0]
            int r1 = r6.trGetC(r7, r8, r9, r1)
            int r0 = r0 + -1
        L_0x0065:
            if (r14 > r0) goto L_0x0078
            r2 = r10[r0]
            int r2 = r6.trGetC(r7, r8, r9, r2)
            if (r2 != r1) goto L_0x0078
            r2 = r10[r0]
            r2 = r2 ^ -1
            r10[r0] = r2
            int r0 = r0 + -1
            goto L_0x0065
        L_0x0078:
            r16 = r1
            goto L_0x005b
        L_0x007b:
            r6.lsUpdateGroup(r7, r14, r13)
            if (r15 != 0) goto L_0x0081
            return
        L_0x0081:
            int r15 = r15 + -1
            r0 = r11[r15]
            int r14 = r0.f3717a
            int r13 = r0.f3718b
            int r0 = r0.f3719c
            goto L_0x001b
        L_0x008c:
            r0 = r20
            r1 = r21
            r2 = r22
            r5 = r3
            r3 = r23
            r12 = 1
            r4 = r14
            r18 = r5
            r5 = r13
            int r0 = r0.trPivot(r1, r2, r3, r4, r5)
            swapElements(r10, r14, r10, r0)
            r0 = r10[r14]
            int r0 = r6.trGetC(r7, r8, r9, r0)
            int r1 = r14 + 1
        L_0x00a9:
            if (r1 >= r13) goto L_0x00b8
            r2 = r10[r1]
            int r2 = r6.trGetC(r7, r8, r9, r2)
            if (r2 != r0) goto L_0x00ba
            int r1 = r1 + 1
            r16 = r2
            goto L_0x00a9
        L_0x00b8:
            r2 = r16
        L_0x00ba:
            if (r1 >= r13) goto L_0x00d2
            if (r2 >= r0) goto L_0x00d2
            r3 = r1
        L_0x00bf:
            int r1 = r1 + r12
            if (r1 >= r13) goto L_0x00d3
            r2 = r10[r1]
            int r2 = r6.trGetC(r7, r8, r9, r2)
            if (r2 > r0) goto L_0x00d3
            if (r2 != r0) goto L_0x00bf
            swapElements(r10, r1, r10, r3)
            int r3 = r3 + 1
            goto L_0x00bf
        L_0x00d2:
            r3 = r1
        L_0x00d3:
            int r4 = r13 + -1
        L_0x00d5:
            if (r1 >= r4) goto L_0x00e2
            r2 = r10[r4]
            int r2 = r6.trGetC(r7, r8, r9, r2)
            if (r2 != r0) goto L_0x00e2
            int r4 = r4 + -1
            goto L_0x00d5
        L_0x00e2:
            if (r1 >= r4) goto L_0x00ff
            if (r2 <= r0) goto L_0x00ff
            r5 = r2
            r2 = r4
        L_0x00e8:
            int r4 = r4 + -1
            if (r1 >= r4) goto L_0x00fc
            r5 = r10[r4]
            int r5 = r6.trGetC(r7, r8, r9, r5)
            if (r5 < r0) goto L_0x00fc
            if (r5 != r0) goto L_0x00e8
            swapElements(r10, r4, r10, r2)
            int r2 = r2 + -1
            goto L_0x00e8
        L_0x00fc:
            r16 = r5
            goto L_0x0102
        L_0x00ff:
            r16 = r2
            r2 = r4
        L_0x0102:
            if (r1 >= r4) goto L_0x0132
            swapElements(r10, r1, r10, r4)
        L_0x0107:
            int r1 = r1 + r12
            if (r1 >= r4) goto L_0x011e
            r5 = r10[r1]
            int r5 = r6.trGetC(r7, r8, r9, r5)
            if (r5 > r0) goto L_0x011c
            if (r5 != r0) goto L_0x0119
            swapElements(r10, r1, r10, r3)
            int r3 = r3 + 1
        L_0x0119:
            r16 = r5
            goto L_0x0107
        L_0x011c:
            r16 = r5
        L_0x011e:
            int r4 = r4 + -1
            if (r1 >= r4) goto L_0x0102
            r5 = r10[r4]
            int r5 = r6.trGetC(r7, r8, r9, r5)
            if (r5 < r0) goto L_0x00fc
            if (r5 != r0) goto L_0x011c
            swapElements(r10, r4, r10, r2)
            int r2 = r2 + -1
            goto L_0x011c
        L_0x0132:
            if (r3 > r2) goto L_0x01bf
            int r0 = r1 + -1
            int r4 = r3 - r14
            int r3 = r1 - r3
            if (r4 <= r3) goto L_0x013d
            r4 = r3
        L_0x013d:
            int r5 = r1 - r4
            r12 = r5
            r5 = r14
        L_0x0141:
            if (r4 <= 0) goto L_0x014f
            swapElements(r10, r5, r10, r12)
            int r4 = r4 + -1
            r19 = 1
            int r5 = r5 + 1
            int r12 = r12 + 1
            goto L_0x0141
        L_0x014f:
            r19 = 1
            int r0 = r2 - r0
            int r2 = r13 - r2
            int r2 = r2 + -1
            if (r0 <= r2) goto L_0x015a
            goto L_0x015b
        L_0x015a:
            r2 = r0
        L_0x015b:
            int r4 = r13 - r2
        L_0x015d:
            if (r2 <= 0) goto L_0x0169
            swapElements(r10, r1, r10, r4)
            int r2 = r2 + -1
            int r1 = r1 + 1
            int r4 = r4 + 1
            goto L_0x015d
        L_0x0169:
            int r1 = r14 + r3
            int r0 = r13 - r0
            int r2 = r1 + -1
            r3 = r14
        L_0x0170:
            if (r3 >= r1) goto L_0x017a
            r4 = r10[r3]
            int r4 = r4 + r7
            r10[r4] = r2
            int r3 = r3 + 1
            goto L_0x0170
        L_0x017a:
            if (r0 >= r13) goto L_0x0189
            int r2 = r0 + -1
            r3 = r1
        L_0x017f:
            if (r3 >= r0) goto L_0x0189
            r4 = r10[r3]
            int r4 = r4 + r7
            r10[r4] = r2
            int r3 = r3 + 1
            goto L_0x017f
        L_0x0189:
            int r2 = r0 - r1
            r3 = 1
            if (r2 != r3) goto L_0x0190
            r10[r1] = r17
        L_0x0190:
            int r2 = r1 - r14
            int r3 = r13 - r0
            if (r2 > r3) goto L_0x01aa
            if (r14 >= r1) goto L_0x01a6
            int r2 = r15 + 1
            io.netty.handler.codec.compression.Bzip2DivSufSort$StackEntry r3 = new io.netty.handler.codec.compression.Bzip2DivSufSort$StackEntry
            r4 = r18
            r5 = 0
            r3.<init>(r0, r13, r4, r5)
            r11[r15] = r3
            r13 = r1
            goto L_0x01b9
        L_0x01a6:
            r4 = r18
            r14 = r0
            goto L_0x01bc
        L_0x01aa:
            r4 = r18
            r5 = 0
            if (r0 >= r13) goto L_0x01bb
            int r2 = r15 + 1
            io.netty.handler.codec.compression.Bzip2DivSufSort$StackEntry r3 = new io.netty.handler.codec.compression.Bzip2DivSufSort$StackEntry
            r3.<init>(r14, r1, r4, r5)
            r11[r15] = r3
            r14 = r0
        L_0x01b9:
            r15 = r2
            goto L_0x01bc
        L_0x01bb:
            r13 = r1
        L_0x01bc:
            r0 = r4
            goto L_0x001b
        L_0x01bf:
            r5 = 0
            if (r15 != 0) goto L_0x01c3
            return
        L_0x01c3:
            int r15 = r15 + -1
            r0 = r11[r15]
            int r14 = r0.f3717a
            int r13 = r0.f3718b
            int r0 = r0.f3719c
            goto L_0x001b
        */
        throw new UnsupportedOperationException("Method not decompiled: p043io.netty.handler.codec.compression.Bzip2DivSufSort.lsIntroSort(int, int, int, int, int):void");
    }

    private void lsSort(int i, int i2, int i3) {
        int i4;
        int[] iArr = this.f3714SA;
        int i5 = i3 + i;
        while (true) {
            int i6 = 0;
            if ((-i2) < iArr[0]) {
                int i7 = 0;
                int i8 = 0;
                do {
                    int i9 = iArr[i8];
                    if (i9 < 0) {
                        i8 -= i9;
                        i7 += i9;
                        continue;
                    } else {
                        if (i7 != 0) {
                            iArr[i8 + i7] = i7;
                            i4 = 0;
                        } else {
                            i4 = i7;
                        }
                        int i10 = iArr[i9 + i] + 1;
                        lsIntroSort(i, i5, i + i2, i8, i10);
                        i7 = i4;
                        i8 = i10;
                        continue;
                    }
                } while (i8 < i2);
                if (i7 != 0) {
                    iArr[i8 + i7] = i7;
                }
                int i11 = i5 - i;
                if (i2 < i11) {
                    do {
                        int i12 = iArr[i6];
                        if (i12 < 0) {
                            i6 -= i12;
                            continue;
                        } else {
                            int i13 = iArr[i12 + i] + 1;
                            while (i6 < i13) {
                                iArr[iArr[i6] + i] = i6;
                                i6++;
                            }
                            i6 = i13;
                            continue;
                        }
                    } while (i6 < i2);
                    return;
                }
                i5 += i11;
            } else {
                return;
            }
        }
    }

    private PartitionResult trPartition(int i, int i2, int i3, int i4, int i5, int i6) {
        int i7;
        int i8;
        int[] iArr = this.f3714SA;
        int i9 = i4;
        int i10 = 0;
        while (i9 < i5) {
            i10 = trGetC(i, i2, i3, iArr[i9]);
            if (i10 != i6) {
                break;
            }
            i9++;
        }
        if (i9 < i5 && i10 < i6) {
            i7 = i9;
            while (true) {
                i9++;
                if (i9 >= i5) {
                    break;
                }
                i10 = trGetC(i, i2, i3, iArr[i9]);
                if (i10 > i6) {
                    break;
                } else if (i10 == i6) {
                    swapElements(iArr, i9, iArr, i7);
                    i7++;
                }
            }
        } else {
            i7 = i9;
        }
        int i11 = i5 - 1;
        while (i9 < i11) {
            i10 = trGetC(i, i2, i3, iArr[i11]);
            if (i10 != i6) {
                break;
            }
            i11--;
        }
        if (i9 < i11 && i10 > i6) {
            i8 = i11;
            while (true) {
                i11--;
                if (i9 >= i11) {
                    break;
                }
                int trGetC = trGetC(i, i2, i3, iArr[i11]);
                if (trGetC < i6) {
                    break;
                } else if (trGetC == i6) {
                    swapElements(iArr, i11, iArr, i8);
                    i8--;
                }
            }
        } else {
            i8 = i11;
        }
        while (i9 < i11) {
            swapElements(iArr, i9, iArr, i11);
            while (true) {
                i9++;
                if (i9 >= i11) {
                    break;
                }
                int trGetC2 = trGetC(i, i2, i3, iArr[i9]);
                if (trGetC2 > i6) {
                    break;
                } else if (trGetC2 == i6) {
                    swapElements(iArr, i9, iArr, i7);
                    i7++;
                }
            }
            while (true) {
                i11--;
                if (i9 >= i11) {
                    break;
                }
                int trGetC3 = trGetC(i, i2, i3, iArr[i11]);
                if (trGetC3 < i6) {
                    break;
                } else if (trGetC3 == i6) {
                    swapElements(iArr, i11, iArr, i8);
                    i8--;
                }
            }
        }
        if (i7 <= i8) {
            int i12 = i9 - 1;
            int i13 = i7 - i4;
            int i14 = i9 - i7;
            if (i13 > i14) {
                i13 = i14;
            }
            int i15 = i9 - i13;
            int i16 = i4;
            while (i13 > 0) {
                swapElements(iArr, i16, iArr, i15);
                i13--;
                i16++;
                i15++;
            }
            int i17 = i8 - i12;
            int i18 = (i5 - i8) - 1;
            if (i17 <= i18) {
                i18 = i17;
            }
            int i19 = i5 - i18;
            while (i18 > 0) {
                swapElements(iArr, i9, iArr, i19);
                i18--;
                i9++;
                i19++;
            }
            i4 += i14;
            i5 -= i17;
        }
        return new PartitionResult(i4, i5);
    }

    private void trCopy(int i, int i2, int i3, int i4, int i5, int i6, int i7) {
        int[] iArr = this.f3714SA;
        int i8 = i5 - 1;
        int i9 = i4 - 1;
        while (i3 <= i9) {
            int i10 = iArr[i3] - i7;
            if (i10 < 0) {
                i10 += i2 - i;
            }
            int i11 = i + i10;
            if (iArr[i11] == i8) {
                i9++;
                iArr[i9] = i10;
                iArr[i11] = i9;
            }
            i3++;
        }
        int i12 = i6 - 1;
        int i13 = i9 + 1;
        while (i13 < i5) {
            int i14 = iArr[i12] - i7;
            if (i14 < 0) {
                i14 += i2 - i;
            }
            int i15 = i + i14;
            if (iArr[i15] == i8) {
                i5--;
                iArr[i5] = i14;
                iArr[i15] = i5;
            }
            i12--;
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:134:0x02de  */
    /* JADX WARNING: Removed duplicated region for block: B:140:0x02ef  */
    /* JADX WARNING: Removed duplicated region for block: B:145:0x02fd  */
    /* JADX WARNING: Removed duplicated region for block: B:150:0x030f  */
    /* JADX WARNING: Removed duplicated region for block: B:230:0x04ec  */
    /* JADX WARNING: Removed duplicated region for block: B:289:0x02f4 A[SYNTHETIC] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private void trIntroSort(int r23, int r24, int r25, int r26, int r27, p043io.netty.handler.codec.compression.Bzip2DivSufSort.TRBudget r28, int r29) {
        /*
            r22 = this;
            r8 = r22
            r9 = r23
            r10 = r25
            r11 = r28
            r12 = r29
            int[] r13 = r8.f3714SA
            r0 = 64
            io.netty.handler.codec.compression.Bzip2DivSufSort$StackEntry[] r14 = new p043io.netty.handler.codec.compression.Bzip2DivSufSort.StackEntry[r0]
            int r0 = r27 - r26
            int r0 = trLog(r0)
            r4 = r24
            r5 = r26
            r6 = r27
            r3 = 0
            r16 = 0
        L_0x001f:
            r2 = -1
            r1 = 1
            if (r0 >= 0) goto L_0x01df
            if (r0 != r2) goto L_0x0121
            int r0 = r6 - r5
            boolean r0 = r11.update(r12, r0)
            if (r0 != 0) goto L_0x0032
            r15 = r3
            r18 = 0
            goto L_0x04f8
        L_0x0032:
            int r2 = r4 + -1
            int r17 = r6 + -1
            r0 = r22
            r1 = r23
            r24 = r2
            r15 = r3
            r3 = r25
            r19 = r4
            r4 = r5
            r7 = r5
            r5 = r6
            r8 = r6
            r6 = r17
            io.netty.handler.codec.compression.Bzip2DivSufSort$PartitionResult r0 = r0.trPartition(r1, r2, r3, r4, r5, r6)
            int r1 = r0.first
            int r0 = r0.last
            if (r7 < r1) goto L_0x0072
            if (r0 >= r8) goto L_0x0054
            goto L_0x0072
        L_0x0054:
            if (r7 >= r8) goto L_0x005e
            r0 = r13[r7]
            int r0 = r0 + r9
            r13[r0] = r7
            int r7 = r7 + 1
            goto L_0x0054
        L_0x005e:
            if (r15 != 0) goto L_0x0061
            return
        L_0x0061:
            int r3 = r15 + -1
            r0 = r14[r3]
            int r1 = r0.f3717a
            int r2 = r0.f3718b
            int r4 = r0.f3719c
            int r0 = r0.f3720d
            r5 = r2
            r6 = r4
            r4 = r1
            goto L_0x0207
        L_0x0072:
            if (r1 >= r8) goto L_0x0081
            int r2 = r1 + -1
            r3 = r7
        L_0x0077:
            if (r3 >= r1) goto L_0x0081
            r4 = r13[r3]
            int r4 = r4 + r9
            r13[r4] = r2
            int r3 = r3 + 1
            goto L_0x0077
        L_0x0081:
            if (r0 >= r8) goto L_0x0090
            int r2 = r0 + -1
            r3 = r1
        L_0x0086:
            if (r3 >= r0) goto L_0x0090
            r4 = r13[r3]
            int r4 = r4 + r9
            r13[r4] = r2
            int r3 = r3 + 1
            goto L_0x0086
        L_0x0090:
            int r3 = r15 + 1
            io.netty.handler.codec.compression.Bzip2DivSufSort$StackEntry r2 = new io.netty.handler.codec.compression.Bzip2DivSufSort$StackEntry
            r6 = 0
            r2.<init>(r6, r1, r0, r6)
            r14[r15] = r2
            int r2 = r3 + 1
            io.netty.handler.codec.compression.Bzip2DivSufSort$StackEntry r4 = new io.netty.handler.codec.compression.Bzip2DivSufSort$StackEntry
            r5 = r24
            r15 = -2
            r4.<init>(r5, r7, r8, r15)
            r14[r3] = r4
            int r3 = r1 - r7
            int r4 = r8 - r0
            if (r3 > r4) goto L_0x00e7
            r5 = 1
            if (r5 >= r3) goto L_0x00c8
            int r5 = r2 + 1
            io.netty.handler.codec.compression.Bzip2DivSufSort$StackEntry r15 = new io.netty.handler.codec.compression.Bzip2DivSufSort$StackEntry
            int r4 = trLog(r4)
            r6 = r19
            r15.<init>(r6, r0, r8, r4)
            r14[r2] = r15
            int r0 = trLog(r3)
            r3 = r5
        L_0x00c3:
            r4 = r6
            r5 = r7
            r6 = r1
            goto L_0x0207
        L_0x00c8:
            r6 = r19
            if (r5 >= r4) goto L_0x00d7
            int r1 = trLog(r4)
            r5 = r0
            r0 = r1
            r3 = r2
            r4 = r6
            r6 = r8
            goto L_0x0207
        L_0x00d7:
            if (r2 != 0) goto L_0x00da
            return
        L_0x00da:
            int r2 = r2 + -1
            r0 = r14[r2]
            int r1 = r0.f3717a
            int r3 = r0.f3718b
            int r4 = r0.f3719c
            int r0 = r0.f3720d
            goto L_0x011b
        L_0x00e7:
            r6 = r19
            r5 = 1
            if (r5 >= r4) goto L_0x0104
            int r5 = r2 + 1
            io.netty.handler.codec.compression.Bzip2DivSufSort$StackEntry r15 = new io.netty.handler.codec.compression.Bzip2DivSufSort$StackEntry
            int r3 = trLog(r3)
            r15.<init>(r6, r7, r1, r3)
            r14[r2] = r15
            int r1 = trLog(r4)
            r3 = r5
            r4 = r6
            r6 = r8
            r5 = r0
            r0 = r1
            goto L_0x0207
        L_0x0104:
            if (r5 >= r3) goto L_0x010c
            int r0 = trLog(r3)
            r3 = r2
            goto L_0x00c3
        L_0x010c:
            if (r2 != 0) goto L_0x010f
            return
        L_0x010f:
            int r2 = r2 + -1
            r0 = r14[r2]
            int r1 = r0.f3717a
            int r3 = r0.f3718b
            int r4 = r0.f3719c
            int r0 = r0.f3720d
        L_0x011b:
            r5 = r3
            r6 = r4
            r4 = r1
            r3 = r2
            goto L_0x0207
        L_0x0121:
            r15 = r3
            r7 = r5
            r8 = r6
            r1 = -2
            r5 = 1
            r6 = r4
            if (r0 != r1) goto L_0x0156
            int r15 = r15 + -1
            r0 = r14[r15]
            int r4 = r0.f3718b
            r0 = r14[r15]
            int r5 = r0.f3719c
            int r17 = r6 - r9
            r0 = r22
            r1 = r23
            r2 = r25
            r3 = r7
            r7 = 0
            r6 = r8
            r18 = 0
            r7 = r17
            r0.trCopy(r1, r2, r3, r4, r5, r6, r7)
            if (r15 != 0) goto L_0x0148
            return
        L_0x0148:
            int r3 = r15 + -1
            r0 = r14[r3]
            int r4 = r0.f3717a
            int r5 = r0.f3718b
            int r6 = r0.f3719c
            int r0 = r0.f3720d
            goto L_0x0207
        L_0x0156:
            r18 = 0
            r0 = r13[r7]
            if (r0 < 0) goto L_0x0168
        L_0x015c:
            r0 = r13[r7]
            int r0 = r0 + r9
            r13[r0] = r7
            int r7 = r7 + r5
            if (r7 >= r8) goto L_0x0168
            r0 = r13[r7]
            if (r0 >= 0) goto L_0x015c
        L_0x0168:
            if (r7 >= r8) goto L_0x01cf
            r0 = r7
        L_0x016b:
            r1 = r13[r0]
            r1 = r1 ^ r2
            r13[r0] = r1
            int r0 = r0 + r5
            r1 = r13[r0]
            if (r1 < 0) goto L_0x016b
            r1 = r13[r0]
            int r1 = r1 + r9
            r1 = r13[r1]
            r3 = r13[r0]
            int r4 = r6 + r3
            r3 = r13[r4]
            if (r1 == r3) goto L_0x018a
            int r1 = r0 - r7
            int r1 = r1 + r5
            int r1 = trLog(r1)
            goto L_0x018b
        L_0x018a:
            r1 = -1
        L_0x018b:
            int r0 = r0 + 1
            if (r0 >= r8) goto L_0x019c
            int r2 = r0 + -1
            r3 = r7
        L_0x0192:
            if (r3 >= r0) goto L_0x019c
            r4 = r13[r3]
            int r4 = r4 + r9
            r13[r4] = r2
            int r3 = r3 + 1
            goto L_0x0192
        L_0x019c:
            int r2 = r0 - r7
            int r3 = r8 - r0
            if (r2 > r3) goto L_0x01b5
            int r3 = r15 + 1
            io.netty.handler.codec.compression.Bzip2DivSufSort$StackEntry r2 = new io.netty.handler.codec.compression.Bzip2DivSufSort$StackEntry
            r4 = -3
            r2.<init>(r6, r0, r8, r4)
            r14[r15] = r2
            int r4 = r6 + 1
            r8 = r22
            r6 = r0
            r0 = r1
        L_0x01b2:
            r5 = r7
            goto L_0x001f
        L_0x01b5:
            if (r5 >= r3) goto L_0x01c7
            int r3 = r15 + 1
            io.netty.handler.codec.compression.Bzip2DivSufSort$StackEntry r2 = new io.netty.handler.codec.compression.Bzip2DivSufSort$StackEntry
            int r4 = r6 + 1
            r2.<init>(r4, r7, r0, r1)
            r14[r15] = r2
            r5 = r0
            r4 = r6
            r6 = r8
            r0 = -3
            goto L_0x0207
        L_0x01c7:
            int r4 = r6 + 1
            r8 = r22
            r6 = r0
            r0 = r1
            goto L_0x051d
        L_0x01cf:
            if (r15 != 0) goto L_0x01d2
            return
        L_0x01d2:
            int r3 = r15 + -1
            r0 = r14[r3]
            int r4 = r0.f3717a
            int r5 = r0.f3718b
            int r6 = r0.f3719c
            int r0 = r0.f3720d
            goto L_0x0207
        L_0x01df:
            r15 = r3
            r7 = r5
            r8 = r6
            r5 = 1
            r18 = 0
            r6 = r4
            int r4 = r8 - r7
            r1 = 8
            if (r4 > r1) goto L_0x020b
            boolean r0 = r11.update(r12, r4)
            if (r0 != 0) goto L_0x01f6
        L_0x01f2:
            r8 = r22
            goto L_0x04f8
        L_0x01f6:
            r0 = r22
            r1 = r23
            r2 = r6
            r3 = r25
            r4 = r7
            r5 = r8
            r0.trInsertionSort(r1, r2, r3, r4, r5)
            r0 = -3
            r4 = r6
            r5 = r7
            r6 = r8
            r3 = r15
        L_0x0207:
            r8 = r22
            goto L_0x001f
        L_0x020b:
            int r3 = r0 + -1
            if (r0 != 0) goto L_0x0257
            boolean r0 = r11.update(r12, r4)
            if (r0 != 0) goto L_0x0216
            goto L_0x01f2
        L_0x0216:
            r0 = r22
            r1 = r23
            r17 = -1
            r2 = r6
            r3 = r25
            r5 = r4
            r4 = r7
            r0.trHeapSort(r1, r2, r3, r4, r5)
            int r0 = r8 + -1
        L_0x0226:
            if (r7 >= r0) goto L_0x024a
            r1 = r13[r0]
            r4 = r8
            r8 = r22
            int r1 = r8.trGetC(r9, r6, r10, r1)
            int r0 = r0 + -1
        L_0x0233:
            if (r7 > r0) goto L_0x0246
            r2 = r13[r0]
            int r2 = r8.trGetC(r9, r6, r10, r2)
            if (r2 != r1) goto L_0x0246
            r2 = r13[r0]
            r2 = r2 ^ -1
            r13[r0] = r2
            int r0 = r0 + -1
            goto L_0x0233
        L_0x0246:
            r16 = r1
            r8 = r4
            goto L_0x0226
        L_0x024a:
            r4 = r8
            r0 = -3
            r8 = r22
            r5 = r7
            r3 = r15
        L_0x0250:
            r21 = r6
            r6 = r4
            r4 = r21
            goto L_0x001f
        L_0x0257:
            r2 = r4
            r4 = r8
            r17 = -1
            r8 = r22
            r0 = r22
            r1 = r23
            r11 = r2
            r2 = r6
            r24 = r11
            r11 = r3
            r3 = r25
            r26 = r4
            r4 = r7
            r12 = 1
            r5 = r26
            int r0 = r0.trPivot(r1, r2, r3, r4, r5)
            swapElements(r13, r7, r13, r0)
            r0 = r13[r7]
            int r0 = r8.trGetC(r9, r6, r10, r0)
            int r5 = r7 + 1
            r4 = r26
        L_0x027f:
            if (r5 >= r4) goto L_0x028e
            r1 = r13[r5]
            int r1 = r8.trGetC(r9, r6, r10, r1)
            if (r1 != r0) goto L_0x0290
            int r5 = r5 + 1
            r16 = r1
            goto L_0x027f
        L_0x028e:
            r1 = r16
        L_0x0290:
            if (r5 >= r4) goto L_0x02a8
            if (r1 >= r0) goto L_0x02a8
            r2 = r5
        L_0x0295:
            int r5 = r5 + r12
            if (r5 >= r4) goto L_0x02a9
            r1 = r13[r5]
            int r1 = r8.trGetC(r9, r6, r10, r1)
            if (r1 > r0) goto L_0x02a9
            if (r1 != r0) goto L_0x0295
            swapElements(r13, r5, r13, r2)
            int r2 = r2 + 1
            goto L_0x0295
        L_0x02a8:
            r2 = r5
        L_0x02a9:
            int r3 = r4 + -1
        L_0x02ab:
            if (r5 >= r3) goto L_0x02b8
            r1 = r13[r3]
            int r1 = r8.trGetC(r9, r6, r10, r1)
            if (r1 != r0) goto L_0x02b8
            int r3 = r3 + -1
            goto L_0x02ab
        L_0x02b8:
            if (r5 >= r3) goto L_0x02d9
            if (r1 <= r0) goto L_0x02d9
            r16 = r1
            r1 = r3
        L_0x02bf:
            int r3 = r3 + -1
            if (r5 >= r3) goto L_0x02dc
            r12 = r13[r3]
            int r12 = r8.trGetC(r9, r6, r10, r12)
            if (r12 < r0) goto L_0x02d6
            if (r12 != r0) goto L_0x02d2
            swapElements(r13, r3, r13, r1)
            int r1 = r1 + -1
        L_0x02d2:
            r16 = r12
            r12 = 1
            goto L_0x02bf
        L_0x02d6:
            r16 = r12
            goto L_0x02dc
        L_0x02d9:
            r16 = r1
            r1 = r3
        L_0x02dc:
            if (r5 >= r3) goto L_0x030d
            swapElements(r13, r5, r13, r3)
        L_0x02e1:
            r12 = 1
            int r5 = r5 + r12
            if (r5 >= r3) goto L_0x02f9
            r12 = r13[r5]
            int r12 = r8.trGetC(r9, r6, r10, r12)
            if (r12 > r0) goto L_0x02f7
            if (r12 != r0) goto L_0x02f4
            swapElements(r13, r5, r13, r2)
            int r2 = r2 + 1
        L_0x02f4:
            r16 = r12
            goto L_0x02e1
        L_0x02f7:
            r16 = r12
        L_0x02f9:
            int r3 = r3 + -1
            if (r5 >= r3) goto L_0x02dc
            r12 = r13[r3]
            int r12 = r8.trGetC(r9, r6, r10, r12)
            if (r12 < r0) goto L_0x02d6
            if (r12 != r0) goto L_0x02f7
            swapElements(r13, r3, r13, r1)
            int r1 = r1 + -1
            goto L_0x02f7
        L_0x030d:
            if (r2 > r1) goto L_0x04ec
            int r3 = r5 + -1
            int r12 = r2 - r7
            int r2 = r5 - r2
            if (r12 <= r2) goto L_0x0318
            r12 = r2
        L_0x0318:
            int r19 = r5 - r12
            r10 = r19
            r19 = r5
            r5 = r7
        L_0x031f:
            if (r12 <= 0) goto L_0x032d
            swapElements(r13, r5, r13, r10)
            int r12 = r12 + -1
            r20 = 1
            int r5 = r5 + 1
            int r10 = r10 + 1
            goto L_0x031f
        L_0x032d:
            r20 = 1
            int r3 = r1 - r3
            int r1 = r4 - r1
            int r1 = r1 + -1
            if (r3 <= r1) goto L_0x0338
            goto L_0x0339
        L_0x0338:
            r1 = r3
        L_0x0339:
            int r5 = r4 - r1
            r10 = r5
            r5 = r19
        L_0x033e:
            if (r1 <= 0) goto L_0x034a
            swapElements(r13, r5, r13, r10)
            int r1 = r1 + -1
            int r5 = r5 + 1
            int r10 = r10 + 1
            goto L_0x033e
        L_0x034a:
            int r5 = r7 + r2
            int r1 = r4 - r3
            r2 = r13[r5]
            int r2 = r2 + r9
            r2 = r13[r2]
            if (r2 == r0) goto L_0x035c
            int r0 = r1 - r5
            int r0 = trLog(r0)
            goto L_0x035d
        L_0x035c:
            r0 = -1
        L_0x035d:
            int r2 = r5 + -1
            r3 = r7
        L_0x0360:
            if (r3 >= r5) goto L_0x036a
            r10 = r13[r3]
            int r10 = r10 + r9
            r13[r10] = r2
            int r3 = r3 + 1
            goto L_0x0360
        L_0x036a:
            if (r1 >= r4) goto L_0x0379
            int r2 = r1 + -1
            r3 = r5
        L_0x036f:
            if (r3 >= r1) goto L_0x0379
            r10 = r13[r3]
            int r10 = r10 + r9
            r13[r10] = r2
            int r3 = r3 + 1
            goto L_0x036f
        L_0x0379:
            int r2 = r5 - r7
            int r3 = r4 - r1
            if (r2 > r3) goto L_0x0427
            int r10 = r1 - r5
            if (r3 > r10) goto L_0x03d1
            r12 = 1
            if (r12 >= r2) goto L_0x03a6
            int r3 = r15 + 1
            io.netty.handler.codec.compression.Bzip2DivSufSort$StackEntry r2 = new io.netty.handler.codec.compression.Bzip2DivSufSort$StackEntry
            int r10 = r6 + 1
            r2.<init>(r10, r5, r1, r0)
            r14[r15] = r2
            int r0 = r3 + 1
            io.netty.handler.codec.compression.Bzip2DivSufSort$StackEntry r2 = new io.netty.handler.codec.compression.Bzip2DivSufSort$StackEntry
            r2.<init>(r6, r1, r4, r11)
            r14[r3] = r2
            r10 = r25
            r12 = r29
            r3 = r0
        L_0x039f:
            r4 = r6
            r0 = r11
        L_0x03a1:
            r11 = r28
            r6 = r5
            goto L_0x01b2
        L_0x03a6:
            r2 = 1
            if (r2 >= r3) goto L_0x03b6
            int r3 = r15 + 1
            io.netty.handler.codec.compression.Bzip2DivSufSort$StackEntry r2 = new io.netty.handler.codec.compression.Bzip2DivSufSort$StackEntry
            int r7 = r6 + 1
            r2.<init>(r7, r5, r1, r0)
            r14[r15] = r2
            goto L_0x04e6
        L_0x03b6:
            if (r2 >= r10) goto L_0x03ba
            goto L_0x0462
        L_0x03ba:
            if (r15 != 0) goto L_0x03bd
            return
        L_0x03bd:
            int r3 = r15 + -1
            r0 = r14[r3]
            int r4 = r0.f3717a
            int r5 = r0.f3718b
            int r6 = r0.f3719c
            int r0 = r0.f3720d
            r10 = r25
            r11 = r28
            r12 = r29
            goto L_0x001f
        L_0x03d1:
            if (r2 > r10) goto L_0x0406
            r3 = 1
            if (r3 >= r2) goto L_0x03f0
            int r3 = r15 + 1
            io.netty.handler.codec.compression.Bzip2DivSufSort$StackEntry r2 = new io.netty.handler.codec.compression.Bzip2DivSufSort$StackEntry
            r2.<init>(r6, r1, r4, r11)
            r14[r15] = r2
            int r2 = r3 + 1
            io.netty.handler.codec.compression.Bzip2DivSufSort$StackEntry r4 = new io.netty.handler.codec.compression.Bzip2DivSufSort$StackEntry
            int r10 = r6 + 1
            r4.<init>(r10, r5, r1, r0)
            r14[r3] = r4
            r10 = r25
            r12 = r29
            r3 = r2
            goto L_0x039f
        L_0x03f0:
            r2 = 1
            if (r2 >= r10) goto L_0x03fe
            int r3 = r15 + 1
            io.netty.handler.codec.compression.Bzip2DivSufSort$StackEntry r2 = new io.netty.handler.codec.compression.Bzip2DivSufSort$StackEntry
            r2.<init>(r6, r1, r4, r11)
            r14[r15] = r2
            goto L_0x04a8
        L_0x03fe:
            r10 = r25
            r12 = r29
            r5 = r1
            r0 = r11
            r3 = r15
            goto L_0x0449
        L_0x0406:
            r2 = 1
            if (r2 >= r10) goto L_0x041d
            int r3 = r15 + 1
            io.netty.handler.codec.compression.Bzip2DivSufSort$StackEntry r2 = new io.netty.handler.codec.compression.Bzip2DivSufSort$StackEntry
            r2.<init>(r6, r1, r4, r11)
            r14[r15] = r2
            int r2 = r3 + 1
            io.netty.handler.codec.compression.Bzip2DivSufSort$StackEntry r4 = new io.netty.handler.codec.compression.Bzip2DivSufSort$StackEntry
            r4.<init>(r6, r7, r5, r11)
            r14[r3] = r4
            goto L_0x04d1
        L_0x041d:
            int r3 = r15 + 1
            io.netty.handler.codec.compression.Bzip2DivSufSort$StackEntry r0 = new io.netty.handler.codec.compression.Bzip2DivSufSort$StackEntry
            r0.<init>(r6, r1, r4, r11)
            r14[r15] = r0
            goto L_0x045a
        L_0x0427:
            int r10 = r1 - r5
            if (r2 > r10) goto L_0x047c
            r12 = 1
            if (r12 >= r3) goto L_0x044d
            int r3 = r15 + 1
            io.netty.handler.codec.compression.Bzip2DivSufSort$StackEntry r2 = new io.netty.handler.codec.compression.Bzip2DivSufSort$StackEntry
            int r10 = r6 + 1
            r2.<init>(r10, r5, r1, r0)
            r14[r15] = r2
            int r0 = r3 + 1
            io.netty.handler.codec.compression.Bzip2DivSufSort$StackEntry r2 = new io.netty.handler.codec.compression.Bzip2DivSufSort$StackEntry
            r2.<init>(r6, r7, r5, r11)
            r14[r3] = r2
            r10 = r25
            r12 = r29
            r3 = r0
        L_0x0447:
            r5 = r1
        L_0x0448:
            r0 = r11
        L_0x0449:
            r11 = r28
            goto L_0x0250
        L_0x044d:
            if (r12 >= r2) goto L_0x0460
            int r3 = r15 + 1
            io.netty.handler.codec.compression.Bzip2DivSufSort$StackEntry r2 = new io.netty.handler.codec.compression.Bzip2DivSufSort$StackEntry
            int r4 = r6 + 1
            r2.<init>(r4, r5, r1, r0)
            r14[r15] = r2
        L_0x045a:
            r10 = r25
            r12 = r29
            goto L_0x039f
        L_0x0460:
            if (r12 >= r10) goto L_0x046d
        L_0x0462:
            int r4 = r6 + 1
            r10 = r25
            r11 = r28
            r12 = r29
            r6 = r1
            goto L_0x051e
        L_0x046d:
            int r3 = r15 + 1
            io.netty.handler.codec.compression.Bzip2DivSufSort$StackEntry r0 = new io.netty.handler.codec.compression.Bzip2DivSufSort$StackEntry
            r0.<init>(r6, r7, r4, r11)
            r14[r15] = r0
            r10 = r25
            r12 = r29
            r5 = r7
            goto L_0x0448
        L_0x047c:
            r12 = 1
            if (r3 > r10) goto L_0x04bc
            if (r12 >= r3) goto L_0x049c
            int r3 = r15 + 1
            io.netty.handler.codec.compression.Bzip2DivSufSort$StackEntry r2 = new io.netty.handler.codec.compression.Bzip2DivSufSort$StackEntry
            r2.<init>(r6, r7, r5, r11)
            r14[r15] = r2
            int r2 = r3 + 1
            io.netty.handler.codec.compression.Bzip2DivSufSort$StackEntry r7 = new io.netty.handler.codec.compression.Bzip2DivSufSort$StackEntry
            int r10 = r6 + 1
            r7.<init>(r10, r5, r1, r0)
            r14[r3] = r7
            r10 = r25
            r12 = r29
            r5 = r1
            r3 = r2
            goto L_0x0448
        L_0x049c:
            r2 = 1
            if (r2 >= r10) goto L_0x04b3
            int r3 = r15 + 1
            io.netty.handler.codec.compression.Bzip2DivSufSort$StackEntry r2 = new io.netty.handler.codec.compression.Bzip2DivSufSort$StackEntry
            r2.<init>(r6, r7, r5, r11)
            r14[r15] = r2
        L_0x04a8:
            int r4 = r6 + 1
            r10 = r25
            r11 = r28
            r12 = r29
            r6 = r1
            goto L_0x001f
        L_0x04b3:
            r10 = r25
            r12 = r29
            r4 = r6
            r0 = r11
            r3 = r15
            goto L_0x03a1
        L_0x04bc:
            r2 = 1
            if (r2 >= r10) goto L_0x04dd
            int r3 = r15 + 1
            io.netty.handler.codec.compression.Bzip2DivSufSort$StackEntry r2 = new io.netty.handler.codec.compression.Bzip2DivSufSort$StackEntry
            r2.<init>(r6, r7, r5, r11)
            r14[r15] = r2
            int r2 = r3 + 1
            io.netty.handler.codec.compression.Bzip2DivSufSort$StackEntry r7 = new io.netty.handler.codec.compression.Bzip2DivSufSort$StackEntry
            r7.<init>(r6, r1, r4, r11)
            r14[r3] = r7
        L_0x04d1:
            int r4 = r6 + 1
            r10 = r25
            r11 = r28
            r12 = r29
            r6 = r1
            r3 = r2
            goto L_0x001f
        L_0x04dd:
            int r3 = r15 + 1
            io.netty.handler.codec.compression.Bzip2DivSufSort$StackEntry r0 = new io.netty.handler.codec.compression.Bzip2DivSufSort$StackEntry
            r0.<init>(r6, r7, r5, r11)
            r14[r15] = r0
        L_0x04e6:
            r10 = r25
            r12 = r29
            goto L_0x0447
        L_0x04ec:
            r2 = r24
            r0 = r28
            r1 = r29
            boolean r2 = r0.update(r1, r2)
            if (r2 != 0) goto L_0x0511
        L_0x04f8:
            r0 = 0
        L_0x04f9:
            if (r0 >= r15) goto L_0x0510
            r1 = r14[r0]
            int r1 = r1.f3720d
            r2 = -3
            if (r1 != r2) goto L_0x050d
            r1 = r14[r0]
            int r1 = r1.f3718b
            r3 = r14[r0]
            int r3 = r3.f3719c
            r8.lsUpdateGroup(r9, r1, r3)
        L_0x050d:
            int r0 = r0 + 1
            goto L_0x04f9
        L_0x0510:
            return
        L_0x0511:
            r2 = -3
            int r3 = r11 + 1
            int r5 = r6 + 1
            r10 = r25
            r11 = r0
            r12 = r1
            r0 = r3
            r6 = r4
            r4 = r5
        L_0x051d:
            r5 = r7
        L_0x051e:
            r3 = r15
            goto L_0x001f
        */
        throw new UnsupportedOperationException("Method not decompiled: p043io.netty.handler.codec.compression.Bzip2DivSufSort.trIntroSort(int, int, int, int, int, io.netty.handler.codec.compression.Bzip2DivSufSort$TRBudget, int):void");
    }

    private void trSort(int i, int i2, int i3) {
        int i4 = i2;
        int[] iArr = this.f3714SA;
        if ((-i4) < iArr[0]) {
            TRBudget tRBudget = new TRBudget(i4, ((trLog(i2) * 2) / 3) + 1);
            int i5 = 0;
            do {
                int i6 = iArr[i5];
                if (i6 < 0) {
                    i5 -= i6;
                    continue;
                } else {
                    int i7 = iArr[i + i6] + 1;
                    if (1 < i7 - i5) {
                        trIntroSort(i, i + i3, i + i4, i5, i7, tRBudget, i2);
                        if (tRBudget.chance == 0) {
                            if (i5 > 0) {
                                iArr[0] = -i5;
                            }
                            lsSort(i, i2, i3);
                            return;
                        }
                    }
                    i5 = i7;
                    continue;
                }
            } while (i5 < i4);
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:9:0x0029, code lost:
        r16 = true;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private int sortTypeBstar(int[] r30, int[] r31) {
        /*
            r29 = this;
            r10 = r29
            byte[] r11 = r10.f3715T
            int[] r12 = r10.f3714SA
            int r13 = r10.f3716n
            r0 = 256(0x100, float:3.59E-43)
            int[] r1 = new int[r0]
            r14 = 1
            r2 = 1
        L_0x000e:
            r15 = 0
            r9 = 255(0xff, float:3.57E-43)
            if (r2 >= r13) goto L_0x0029
            int r3 = r2 + -1
            byte r4 = r11[r3]
            byte r5 = r11[r2]
            if (r4 == r5) goto L_0x0026
            byte r3 = r11[r3]
            r3 = r3 & r9
            byte r2 = r11[r2]
            r2 = r2 & r9
            if (r3 <= r2) goto L_0x0029
            r16 = 0
            goto L_0x002b
        L_0x0026:
            int r2 = r2 + 1
            goto L_0x000e
        L_0x0029:
            r16 = 1
        L_0x002b:
            int r17 = r13 + -1
            byte r2 = r11[r17]
            r2 = r2 & r9
            byte r3 = r11[r15]
            r3 = r3 & r9
            if (r2 < r3) goto L_0x0042
            byte r4 = r11[r17]
            byte r5 = r11[r15]
            if (r4 != r5) goto L_0x003e
            if (r16 == 0) goto L_0x003e
            goto L_0x0042
        L_0x003e:
            r2 = r13
            r3 = r17
            goto L_0x0076
        L_0x0042:
            if (r16 != 0) goto L_0x0052
            int r2 = BUCKET_BSTAR(r2, r3)
            r3 = r31[r2]
            int r3 = r3 + r14
            r31[r2] = r3
            int r2 = r13 + -1
            r12[r2] = r17
            goto L_0x005c
        L_0x0052:
            int r2 = BUCKET_B(r2, r3)
            r3 = r31[r2]
            int r3 = r3 + r14
            r31[r2] = r3
            r2 = r13
        L_0x005c:
            int r3 = r17 + -1
        L_0x005e:
            if (r3 < 0) goto L_0x0076
            byte r4 = r11[r3]
            r4 = r4 & r9
            int r5 = r3 + 1
            byte r5 = r11[r5]
            r5 = r5 & r9
            if (r4 > r5) goto L_0x0076
            int r4 = BUCKET_B(r4, r5)
            r5 = r31[r4]
            int r5 = r5 + r14
            r31[r4] = r5
            int r3 = r3 + -1
            goto L_0x005e
        L_0x0076:
            r18 = -1
            if (r3 < 0) goto L_0x00bf
        L_0x007a:
            byte r4 = r11[r3]
            r4 = r4 & r9
            r5 = r30[r4]
            int r5 = r5 + r14
            r30[r4] = r5
            int r3 = r3 + -1
            if (r3 < 0) goto L_0x0090
            byte r4 = r11[r3]
            r4 = r4 & r9
            int r5 = r3 + 1
            byte r5 = r11[r5]
            r5 = r5 & r9
            if (r4 >= r5) goto L_0x007a
        L_0x0090:
            if (r3 < 0) goto L_0x0076
            byte r4 = r11[r3]
            r4 = r4 & r9
            int r5 = r3 + 1
            byte r5 = r11[r5]
            r5 = r5 & r9
            int r4 = BUCKET_BSTAR(r4, r5)
            r5 = r31[r4]
            int r5 = r5 + r14
            r31[r4] = r5
            int r2 = r2 + -1
            r12[r2] = r3
        L_0x00a7:
            int r3 = r3 + -1
            if (r3 < 0) goto L_0x0076
            byte r4 = r11[r3]
            r4 = r4 & r9
            int r5 = r3 + 1
            byte r5 = r11[r5]
            r5 = r5 & r9
            if (r4 > r5) goto L_0x0076
            int r4 = BUCKET_B(r4, r5)
            r5 = r31[r4]
            int r5 = r5 + r14
            r31[r4] = r5
            goto L_0x00a7
        L_0x00bf:
            int r8 = r13 - r2
            if (r8 != 0) goto L_0x00cc
            r0 = 0
        L_0x00c4:
            if (r0 >= r13) goto L_0x00cb
            r12[r0] = r0
            int r0 = r0 + 1
            goto L_0x00c4
        L_0x00cb:
            return r15
        L_0x00cc:
            r2 = 0
            r3 = -1
            r4 = 0
        L_0x00cf:
            if (r2 >= r0) goto L_0x00fe
            r5 = r30[r2]
            int r5 = r5 + r3
            int r3 = r3 + r4
            r30[r2] = r3
            int r3 = BUCKET_B(r2, r2)
            r3 = r31[r3]
            int r5 = r5 + r3
            int r3 = r2 + 1
            r6 = r4
            r4 = r3
        L_0x00e2:
            if (r4 >= r0) goto L_0x00fa
            int r7 = BUCKET_BSTAR(r2, r4)
            r7 = r31[r7]
            int r6 = r6 + r7
            int r7 = r2 << 8
            r7 = r7 | r4
            r31[r7] = r6
            int r7 = BUCKET_B(r2, r4)
            r7 = r31[r7]
            int r5 = r5 + r7
            int r4 = r4 + 1
            goto L_0x00e2
        L_0x00fa:
            r2 = r3
            r3 = r5
            r4 = r6
            goto L_0x00cf
        L_0x00fe:
            int r19 = r13 - r8
            int r2 = r8 + -2
        L_0x0102:
            if (r2 < 0) goto L_0x011d
            int r3 = r19 + r2
            r3 = r12[r3]
            byte r4 = r11[r3]
            r4 = r4 & r9
            int r3 = r3 + r14
            byte r3 = r11[r3]
            r3 = r3 & r9
            int r3 = BUCKET_BSTAR(r4, r3)
            r4 = r31[r3]
            int r4 = r4 - r14
            r31[r3] = r4
            r12[r4] = r2
            int r2 = r2 + -1
            goto L_0x0102
        L_0x011d:
            int r2 = r19 + r8
            int r2 = r2 - r14
            r2 = r12[r2]
            byte r3 = r11[r2]
            r3 = r3 & r9
            int r2 = r2 + r14
            byte r2 = r11[r2]
            r2 = r2 & r9
            int r2 = BUCKET_BSTAR(r3, r2)
            r3 = r31[r2]
            int r3 = r3 - r14
            r31[r2] = r3
            int r7 = r8 + -1
            r12[r3] = r7
            int r2 = r8 * 2
            int r2 = r13 - r2
            if (r2 > r0) goto L_0x0143
            r20 = r1
            r21 = 0
            r22 = 256(0x100, float:3.59E-43)
            goto L_0x0149
        L_0x0143:
            r22 = r2
            r21 = r8
            r20 = r12
        L_0x0149:
            r0 = r8
            r6 = 255(0xff, float:3.57E-43)
        L_0x014c:
            if (r0 <= 0) goto L_0x01a4
            r3 = r0
            r5 = 255(0xff, float:3.57E-43)
        L_0x0151:
            if (r6 >= r5) goto L_0x0198
            int r0 = BUCKET_BSTAR(r6, r5)
            r23 = r31[r0]
            int r0 = r3 - r23
            if (r14 >= r0) goto L_0x0184
            r24 = 2
            r0 = r12[r23]
            if (r0 != r7) goto L_0x0166
            r25 = 1
            goto L_0x0168
        L_0x0166:
            r25 = 0
        L_0x0168:
            r0 = r29
            r1 = r19
            r2 = r23
            r4 = r20
            r26 = r5
            r5 = r21
            r27 = r6
            r6 = r22
            r28 = r7
            r7 = r24
            r15 = r8
            r8 = r25
            r9 = r13
            r0.subStringSort(r1, r2, r3, r4, r5, r6, r7, r8, r9)
            goto L_0x018b
        L_0x0184:
            r26 = r5
            r27 = r6
            r28 = r7
            r15 = r8
        L_0x018b:
            int r5 = r26 + -1
            r8 = r15
            r3 = r23
            r6 = r27
            r7 = r28
            r9 = 255(0xff, float:3.57E-43)
            r15 = 0
            goto L_0x0151
        L_0x0198:
            r27 = r6
            r28 = r7
            r15 = r8
            int r6 = r27 + -1
            r0 = r3
            r9 = 255(0xff, float:3.57E-43)
            r15 = 0
            goto L_0x014c
        L_0x01a4:
            r28 = r7
            r15 = r8
            r0 = r28
        L_0x01a9:
            if (r0 < 0) goto L_0x01e2
            r1 = r12[r0]
            if (r1 < 0) goto L_0x01c8
            r1 = r0
        L_0x01b0:
            r2 = r12[r1]
            int r8 = r15 + r2
            r12[r8] = r1
            int r1 = r1 + -1
            if (r1 < 0) goto L_0x01be
            r2 = r12[r1]
            if (r2 >= 0) goto L_0x01b0
        L_0x01be:
            int r2 = r1 + 1
            int r0 = r1 - r0
            r12[r2] = r0
            if (r1 > 0) goto L_0x01c7
            goto L_0x01e2
        L_0x01c7:
            r0 = r1
        L_0x01c8:
            r1 = r0
        L_0x01c9:
            r2 = r12[r1]
            r2 = r2 ^ -1
            r12[r1] = r2
            int r8 = r15 + r2
            r12[r8] = r0
            int r1 = r1 + -1
            r2 = r12[r1]
            if (r2 < 0) goto L_0x01c9
            r2 = r12[r1]
            int r8 = r15 + r2
            r12[r8] = r0
            int r0 = r1 + -1
            goto L_0x01a9
        L_0x01e2:
            r10.trSort(r15, r15, r14)
            byte r0 = r11[r17]
            r1 = 255(0xff, float:3.57E-43)
            r0 = r0 & r1
            r2 = 0
            byte r3 = r11[r2]
            r3 = r3 & r1
            if (r0 < r3) goto L_0x01fd
            byte r0 = r11[r17]
            byte r2 = r11[r2]
            if (r0 != r2) goto L_0x01f9
            if (r16 == 0) goto L_0x01f9
            goto L_0x01fd
        L_0x01f9:
            r8 = r15
            r0 = r17
            goto L_0x021a
        L_0x01fd:
            if (r16 != 0) goto L_0x0208
            int r8 = r15 + -1
            int r0 = r15 + r8
            r0 = r12[r0]
            r12[r0] = r17
            goto L_0x0209
        L_0x0208:
            r8 = r15
        L_0x0209:
            int r0 = r17 + -1
        L_0x020b:
            if (r0 < 0) goto L_0x021a
            byte r2 = r11[r0]
            r2 = r2 & r1
            int r3 = r0 + 1
            byte r3 = r11[r3]
            r3 = r3 & r1
            if (r2 > r3) goto L_0x021a
            int r0 = r0 + -1
            goto L_0x020b
        L_0x021a:
            if (r0 < 0) goto L_0x0244
        L_0x021c:
            int r0 = r0 + -1
            if (r0 < 0) goto L_0x022b
            byte r2 = r11[r0]
            r2 = r2 & r1
            int r3 = r0 + 1
            byte r3 = r11[r3]
            r3 = r3 & r1
            if (r2 < r3) goto L_0x022b
            goto L_0x021c
        L_0x022b:
            if (r0 < 0) goto L_0x021a
            int r8 = r8 + -1
            int r2 = r15 + r8
            r2 = r12[r2]
            r12[r2] = r0
        L_0x0235:
            int r0 = r0 + -1
            if (r0 < 0) goto L_0x021a
            byte r2 = r11[r0]
            r2 = r2 & r1
            int r3 = r0 + 1
            byte r3 = r11[r3]
            r3 = r3 & r1
            if (r2 > r3) goto L_0x021a
            goto L_0x0235
        L_0x0244:
            r0 = 255(0xff, float:3.57E-43)
        L_0x0246:
            if (r0 < 0) goto L_0x0296
            r2 = 255(0xff, float:3.57E-43)
        L_0x024a:
            if (r0 >= r2) goto L_0x0276
            int r3 = BUCKET_B(r0, r2)
            r3 = r31[r3]
            int r3 = r17 - r3
            int r4 = BUCKET_B(r0, r2)
            int r17 = r17 + 1
            r31[r4] = r17
            int r4 = BUCKET_BSTAR(r0, r2)
            r4 = r31[r4]
            r17 = r3
            r3 = r28
        L_0x0266:
            if (r4 > r3) goto L_0x0271
            r5 = r12[r3]
            r12[r17] = r5
            int r17 = r17 + -1
            int r3 = r3 + -1
            goto L_0x0266
        L_0x0271:
            int r2 = r2 + -1
            r28 = r3
            goto L_0x024a
        L_0x0276:
            int r2 = BUCKET_B(r0, r0)
            r2 = r31[r2]
            int r2 = r17 - r2
            int r3 = BUCKET_B(r0, r0)
            int r17 = r17 + 1
            r31[r3] = r17
            if (r0 >= r1) goto L_0x0291
            int r3 = r0 + 1
            int r3 = BUCKET_BSTAR(r0, r3)
            int r2 = r2 + r14
            r31[r3] = r2
        L_0x0291:
            r17 = r30[r0]
            int r0 = r0 + -1
            goto L_0x0246
        L_0x0296:
            return r15
        */
        throw new UnsupportedOperationException("Method not decompiled: p043io.netty.handler.codec.compression.Bzip2DivSufSort.sortTypeBstar(int[], int[]):int");
    }

    /* JADX WARNING: type inference failed for: r0v0, types: [byte[]] */
    /* JADX WARNING: type inference failed for: r4v5, types: [byte] */
    /* JADX WARNING: type inference failed for: r14v5, types: [byte] */
    /* JADX WARNING: type inference failed for: r9v0, types: [byte] */
    /* JADX WARNING: type inference failed for: r10v1, types: [byte] */
    /* JADX WARNING: type inference failed for: r10v4, types: [byte] */
    /* JADX WARNING: type inference failed for: r11v0, types: [byte] */
    /* JADX WARNING: type inference failed for: r9v6, types: [byte] */
    /* JADX WARNING: Incorrect type for immutable var: ssa=byte, code=null, for r10v1, types: [byte] */
    /* JADX WARNING: Incorrect type for immutable var: ssa=byte, code=null, for r10v4, types: [byte] */
    /* JADX WARNING: Incorrect type for immutable var: ssa=byte, code=null, for r11v0, types: [byte] */
    /* JADX WARNING: Incorrect type for immutable var: ssa=byte, code=null, for r14v5, types: [byte] */
    /* JADX WARNING: Incorrect type for immutable var: ssa=byte, code=null, for r4v5, types: [byte] */
    /* JADX WARNING: Incorrect type for immutable var: ssa=byte, code=null, for r9v0, types: [byte] */
    /* JADX WARNING: Incorrect type for immutable var: ssa=byte, code=null, for r9v6, types: [byte] */
    /* JADX WARNING: Incorrect type for immutable var: ssa=byte[], code=null, for r0v0, types: [byte[]] */
    /* JADX WARNING: Unknown variable types count: 8 */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private int constructBWT(int[] r13, int[] r14) {
        /*
            r12 = this;
            byte[] r0 = r12.f3715T
            int[] r1 = r12.f3714SA
            int r2 = r12.f3716n
            r3 = 0
            r4 = 254(0xfe, float:3.56E-43)
            r5 = 0
            r6 = 0
        L_0x000b:
            r7 = -1
            if (r4 < 0) goto L_0x0063
            int r5 = r4 + 1
            int r6 = BUCKET_BSTAR(r4, r5)
            r6 = r14[r6]
            r5 = r13[r5]
            r7 = 0
            r8 = -1
        L_0x001a:
            if (r6 > r5) goto L_0x005e
            r9 = r1[r5]
            if (r9 < 0) goto L_0x0057
            int r10 = r9 + -1
            if (r10 >= 0) goto L_0x0026
            int r10 = r2 + -1
        L_0x0026:
            byte r11 = r0[r10]
            r11 = r11 & 255(0xff, float:3.57E-43)
            if (r11 > r4) goto L_0x005b
            r9 = r9 ^ -1
            r1[r5] = r9
            if (r10 <= 0) goto L_0x003c
            int r9 = r10 + -1
            byte r9 = r0[r9]
            r9 = r9 & 255(0xff, float:3.57E-43)
            if (r9 <= r11) goto L_0x003c
            r10 = r10 ^ -1
        L_0x003c:
            if (r8 != r11) goto L_0x0043
            int r7 = r7 + -1
            r1[r7] = r10
            goto L_0x005b
        L_0x0043:
            if (r8 < 0) goto L_0x004b
            int r8 = BUCKET_B(r8, r4)
            r14[r8] = r7
        L_0x004b:
            int r7 = BUCKET_B(r11, r4)
            r7 = r14[r7]
            int r7 = r7 + -1
            r1[r7] = r10
            r8 = r11
            goto L_0x005b
        L_0x0057:
            r9 = r9 ^ -1
            r1[r5] = r9
        L_0x005b:
            int r5 = r5 + -1
            goto L_0x001a
        L_0x005e:
            int r4 = r4 + -1
            r5 = r7
            r6 = r8
            goto L_0x000b
        L_0x0063:
            r14 = -1
        L_0x0064:
            if (r3 >= r2) goto L_0x00b0
            r4 = r1[r3]
            if (r4 < 0) goto L_0x009b
            int r8 = r4 + -1
            if (r8 >= 0) goto L_0x0070
            int r8 = r2 + -1
        L_0x0070:
            byte r9 = r0[r8]
            r9 = r9 & 255(0xff, float:3.57E-43)
            int r10 = r8 + 1
            byte r10 = r0[r10]
            r10 = r10 & 255(0xff, float:3.57E-43)
            if (r9 < r10) goto L_0x009d
            if (r8 <= 0) goto L_0x0088
            int r10 = r8 + -1
            byte r10 = r0[r10]
            r10 = r10 & 255(0xff, float:3.57E-43)
            if (r10 >= r9) goto L_0x0088
            r8 = r8 ^ -1
        L_0x0088:
            if (r9 != r6) goto L_0x008f
            int r5 = r5 + 1
            r1[r5] = r8
            goto L_0x009d
        L_0x008f:
            if (r6 == r7) goto L_0x0093
            r13[r6] = r5
        L_0x0093:
            r5 = r13[r9]
            int r5 = r5 + 1
            r1[r5] = r8
            r6 = r9
            goto L_0x009d
        L_0x009b:
            r4 = r4 ^ -1
        L_0x009d:
            if (r4 != 0) goto L_0x00a7
            int r14 = r2 + -1
            byte r14 = r0[r14]
            r1[r3] = r14
            r14 = r3
            goto L_0x00ad
        L_0x00a7:
            int r4 = r4 + -1
            byte r4 = r0[r4]
            r1[r3] = r4
        L_0x00ad:
            int r3 = r3 + 1
            goto L_0x0064
        L_0x00b0:
            return r14
        */
        throw new UnsupportedOperationException("Method not decompiled: p043io.netty.handler.codec.compression.Bzip2DivSufSort.constructBWT(int[], int[]):int");
    }

    public int bwt() {
        int[] iArr = this.f3714SA;
        byte[] bArr = this.f3715T;
        int i = this.f3716n;
        int[] iArr2 = new int[256];
        int[] iArr3 = new int[65536];
        if (i == 0) {
            return 0;
        }
        if (i == 1) {
            iArr[0] = bArr[0];
            return 0;
        } else if (sortTypeBstar(iArr2, iArr3) > 0) {
            return constructBWT(iArr2, iArr3);
        } else {
            return 0;
        }
    }
}
