package kotlin.collections;

import kotlin.Metadata;
import kotlin.UByteArray;
import kotlin.UIntArray;
import kotlin.ULongArray;
import kotlin.UShort;
import kotlin.UShortArray;
import kotlin.UnsignedKt;
import kotlin.jvm.internal.Intrinsics;

@Metadata(mo22060bv = {1, 0, 3}, mo22061d1 = {"\u00000\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0002\b\u0012\u001a*\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u00012\u0006\u0010\u0005\u001a\u00020\u0001H\u0003ø\u0001\u0000¢\u0006\u0004\b\u0006\u0010\u0007\u001a*\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\b2\u0006\u0010\u0004\u001a\u00020\u00012\u0006\u0010\u0005\u001a\u00020\u0001H\u0003ø\u0001\u0000¢\u0006\u0004\b\t\u0010\n\u001a*\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u000b2\u0006\u0010\u0004\u001a\u00020\u00012\u0006\u0010\u0005\u001a\u00020\u0001H\u0003ø\u0001\u0000¢\u0006\u0004\b\f\u0010\r\u001a*\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u000e2\u0006\u0010\u0004\u001a\u00020\u00012\u0006\u0010\u0005\u001a\u00020\u0001H\u0003ø\u0001\u0000¢\u0006\u0004\b\u000f\u0010\u0010\u001a*\u0010\u0011\u001a\u00020\u00122\u0006\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u00012\u0006\u0010\u0005\u001a\u00020\u0001H\u0003ø\u0001\u0000¢\u0006\u0004\b\u0013\u0010\u0014\u001a*\u0010\u0011\u001a\u00020\u00122\u0006\u0010\u0002\u001a\u00020\b2\u0006\u0010\u0004\u001a\u00020\u00012\u0006\u0010\u0005\u001a\u00020\u0001H\u0003ø\u0001\u0000¢\u0006\u0004\b\u0015\u0010\u0016\u001a*\u0010\u0011\u001a\u00020\u00122\u0006\u0010\u0002\u001a\u00020\u000b2\u0006\u0010\u0004\u001a\u00020\u00012\u0006\u0010\u0005\u001a\u00020\u0001H\u0003ø\u0001\u0000¢\u0006\u0004\b\u0017\u0010\u0018\u001a*\u0010\u0011\u001a\u00020\u00122\u0006\u0010\u0002\u001a\u00020\u000e2\u0006\u0010\u0004\u001a\u00020\u00012\u0006\u0010\u0005\u001a\u00020\u0001H\u0003ø\u0001\u0000¢\u0006\u0004\b\u0019\u0010\u001a\u001a\u001a\u0010\u001b\u001a\u00020\u00122\u0006\u0010\u0002\u001a\u00020\u0003H\u0001ø\u0001\u0000¢\u0006\u0004\b\u001c\u0010\u001d\u001a\u001a\u0010\u001b\u001a\u00020\u00122\u0006\u0010\u0002\u001a\u00020\bH\u0001ø\u0001\u0000¢\u0006\u0004\b\u001e\u0010\u001f\u001a\u001a\u0010\u001b\u001a\u00020\u00122\u0006\u0010\u0002\u001a\u00020\u000bH\u0001ø\u0001\u0000¢\u0006\u0004\b \u0010!\u001a\u001a\u0010\u001b\u001a\u00020\u00122\u0006\u0010\u0002\u001a\u00020\u000eH\u0001ø\u0001\u0000¢\u0006\u0004\b\"\u0010#\u0002\u0004\n\u0002\b\u0019¨\u0006$"}, mo22062d2 = {"partition", "", "array", "Lkotlin/UByteArray;", "left", "right", "partition-4UcCI2c", "([BII)I", "Lkotlin/UIntArray;", "partition-oBK06Vg", "([III)I", "Lkotlin/ULongArray;", "partition--nroSd4", "([JII)I", "Lkotlin/UShortArray;", "partition-Aa5vz7o", "([SII)I", "quickSort", "", "quickSort-4UcCI2c", "([BII)V", "quickSort-oBK06Vg", "([III)V", "quickSort--nroSd4", "([JII)V", "quickSort-Aa5vz7o", "([SII)V", "sortArray", "sortArray-GBYM_sE", "([B)V", "sortArray--ajY-9A", "([I)V", "sortArray-QwZRm1k", "([J)V", "sortArray-rL5Bavg", "([S)V", "kotlin-stdlib"}, mo22063k = 2, mo22064mv = {1, 1, 15})
/* compiled from: UArraySorting.kt */
public final class UArraySortingKt {
    /* renamed from: partition-4UcCI2c reason: not valid java name */
    private static final int m4600partition4UcCI2c(byte[] bArr, int i, int i2) {
        byte b;
        byte b2 = UByteArray.m4347getimpl(bArr, (i + i2) / 2);
        while (i <= i2) {
            while (true) {
                b = b2 & 255;
                if (Intrinsics.compare((int) UByteArray.m4347getimpl(bArr, i) & 255, (int) b) >= 0) {
                    break;
                }
                i++;
            }
            while (Intrinsics.compare((int) UByteArray.m4347getimpl(bArr, i2) & 255, (int) b) > 0) {
                i2--;
            }
            if (i <= i2) {
                byte b3 = UByteArray.m4347getimpl(bArr, i);
                UByteArray.m4352setVurrAj0(bArr, i, UByteArray.m4347getimpl(bArr, i2));
                UByteArray.m4352setVurrAj0(bArr, i2, b3);
                i++;
                i2--;
            }
        }
        return i;
    }

    /* renamed from: quickSort-4UcCI2c reason: not valid java name */
    private static final void m4604quickSort4UcCI2c(byte[] bArr, int i, int i2) {
        int r0 = m4600partition4UcCI2c(bArr, i, i2);
        int i3 = r0 - 1;
        if (i < i3) {
            m4604quickSort4UcCI2c(bArr, i, i3);
        }
        if (r0 < i2) {
            m4604quickSort4UcCI2c(bArr, r0, i2);
        }
    }

    /* renamed from: partition-Aa5vz7o reason: not valid java name */
    private static final int m4601partitionAa5vz7o(short[] sArr, int i, int i2) {
        short s;
        short s2 = UShortArray.m4580getimpl(sArr, (i + i2) / 2);
        while (i <= i2) {
            while (true) {
                s = s2 & UShort.MAX_VALUE;
                if (Intrinsics.compare((int) UShortArray.m4580getimpl(sArr, i) & UShort.MAX_VALUE, (int) s) >= 0) {
                    break;
                }
                i++;
            }
            while (Intrinsics.compare((int) UShortArray.m4580getimpl(sArr, i2) & UShort.MAX_VALUE, (int) s) > 0) {
                i2--;
            }
            if (i <= i2) {
                short s3 = UShortArray.m4580getimpl(sArr, i);
                UShortArray.m4585set01HTLdE(sArr, i, UShortArray.m4580getimpl(sArr, i2));
                UShortArray.m4585set01HTLdE(sArr, i2, s3);
                i++;
                i2--;
            }
        }
        return i;
    }

    /* renamed from: quickSort-Aa5vz7o reason: not valid java name */
    private static final void m4605quickSortAa5vz7o(short[] sArr, int i, int i2) {
        int r0 = m4601partitionAa5vz7o(sArr, i, i2);
        int i3 = r0 - 1;
        if (i < i3) {
            m4605quickSortAa5vz7o(sArr, i, i3);
        }
        if (r0 < i2) {
            m4605quickSortAa5vz7o(sArr, r0, i2);
        }
    }

    /* renamed from: partition-oBK06Vg reason: not valid java name */
    private static final int m4602partitionoBK06Vg(int[] iArr, int i, int i2) {
        int i3 = UIntArray.m4416getimpl(iArr, (i + i2) / 2);
        while (i <= i2) {
            while (UnsignedKt.uintCompare(UIntArray.m4416getimpl(iArr, i), i3) < 0) {
                i++;
            }
            while (UnsignedKt.uintCompare(UIntArray.m4416getimpl(iArr, i2), i3) > 0) {
                i2--;
            }
            if (i <= i2) {
                int i4 = UIntArray.m4416getimpl(iArr, i);
                UIntArray.m4421setVXSXFK8(iArr, i, UIntArray.m4416getimpl(iArr, i2));
                UIntArray.m4421setVXSXFK8(iArr, i2, i4);
                i++;
                i2--;
            }
        }
        return i;
    }

    /* renamed from: quickSort-oBK06Vg reason: not valid java name */
    private static final void m4606quickSortoBK06Vg(int[] iArr, int i, int i2) {
        int r0 = m4602partitionoBK06Vg(iArr, i, i2);
        int i3 = r0 - 1;
        if (i < i3) {
            m4606quickSortoBK06Vg(iArr, i, i3);
        }
        if (r0 < i2) {
            m4606quickSortoBK06Vg(iArr, r0, i2);
        }
    }

    /* renamed from: partition--nroSd4 reason: not valid java name */
    private static final int m4599partitionnroSd4(long[] jArr, int i, int i2) {
        long j = ULongArray.m4485getimpl(jArr, (i + i2) / 2);
        while (i <= i2) {
            while (UnsignedKt.ulongCompare(ULongArray.m4485getimpl(jArr, i), j) < 0) {
                i++;
            }
            while (UnsignedKt.ulongCompare(ULongArray.m4485getimpl(jArr, i2), j) > 0) {
                i2--;
            }
            if (i <= i2) {
                long j2 = ULongArray.m4485getimpl(jArr, i);
                ULongArray.m4490setk8EXiF4(jArr, i, ULongArray.m4485getimpl(jArr, i2));
                ULongArray.m4490setk8EXiF4(jArr, i2, j2);
                i++;
                i2--;
            }
        }
        return i;
    }

    /* renamed from: quickSort--nroSd4 reason: not valid java name */
    private static final void m4603quickSortnroSd4(long[] jArr, int i, int i2) {
        int r0 = m4599partitionnroSd4(jArr, i, i2);
        int i3 = r0 - 1;
        if (i < i3) {
            m4603quickSortnroSd4(jArr, i, i3);
        }
        if (r0 < i2) {
            m4603quickSortnroSd4(jArr, r0, i2);
        }
    }

    /* renamed from: sortArray-GBYM_sE reason: not valid java name */
    public static final void m4608sortArrayGBYM_sE(byte[] bArr) {
        Intrinsics.checkParameterIsNotNull(bArr, "array");
        m4604quickSort4UcCI2c(bArr, 0, UByteArray.m4348getSizeimpl(bArr) - 1);
    }

    /* renamed from: sortArray-rL5Bavg reason: not valid java name */
    public static final void m4610sortArrayrL5Bavg(short[] sArr) {
        Intrinsics.checkParameterIsNotNull(sArr, "array");
        m4605quickSortAa5vz7o(sArr, 0, UShortArray.m4581getSizeimpl(sArr) - 1);
    }

    /* renamed from: sortArray--ajY-9A reason: not valid java name */
    public static final void m4607sortArrayajY9A(int[] iArr) {
        Intrinsics.checkParameterIsNotNull(iArr, "array");
        m4606quickSortoBK06Vg(iArr, 0, UIntArray.m4417getSizeimpl(iArr) - 1);
    }

    /* renamed from: sortArray-QwZRm1k reason: not valid java name */
    public static final void m4609sortArrayQwZRm1k(long[] jArr) {
        Intrinsics.checkParameterIsNotNull(jArr, "array");
        m4603quickSortnroSd4(jArr, 0, ULongArray.m4486getSizeimpl(jArr) - 1);
    }
}
