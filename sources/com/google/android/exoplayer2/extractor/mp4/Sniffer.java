package com.google.android.exoplayer2.extractor.mp4;

import android.support.p009v4.media.session.PlaybackStateCompat;
import com.google.android.exoplayer2.extractor.ExtractorInput;
import com.google.android.exoplayer2.util.ParsableByteArray;
import java.io.IOException;

final class Sniffer {
    private static final int[] COMPATIBLE_BRANDS = {1769172845, 1769172786, 1769172787, 1769172788, 1769172789, 1769172790, Atom.TYPE_avc1, Atom.TYPE_hvc1, Atom.TYPE_hev1, Atom.TYPE_av01, 1836069937, 1836069938, 862401121, 862401122, 862417462, 862417718, 862414134, 862414646, 1295275552, 1295270176, 1714714144, 1801741417, 1295275600, 1903435808, 1297305174, 1684175153};
    private static final int SEARCH_LENGTH = 4096;

    public static boolean sniffFragmented(ExtractorInput extractorInput) throws IOException, InterruptedException {
        return sniffInternal(extractorInput, true);
    }

    public static boolean sniffUnfragmented(ExtractorInput extractorInput) throws IOException, InterruptedException {
        return sniffInternal(extractorInput, false);
    }

    private static boolean sniffInternal(ExtractorInput extractorInput, boolean z) throws IOException, InterruptedException {
        boolean z2;
        boolean z3;
        boolean z4;
        ExtractorInput extractorInput2 = extractorInput;
        long length = extractorInput.getLength();
        long j = PlaybackStateCompat.ACTION_SKIP_TO_QUEUE_ITEM;
        long j2 = -1;
        if (length != -1 && length <= PlaybackStateCompat.ACTION_SKIP_TO_QUEUE_ITEM) {
            j = length;
        }
        int i = (int) j;
        ParsableByteArray parsableByteArray = new ParsableByteArray(64);
        boolean z5 = false;
        int i2 = i;
        int i3 = 0;
        boolean z6 = false;
        while (true) {
            if (i3 >= i2) {
                break;
            }
            parsableByteArray.reset(8);
            extractorInput2.peekFully(parsableByteArray.data, z5 ? 1 : 0, 8);
            long readUnsignedInt = parsableByteArray.readUnsignedInt();
            int readInt = parsableByteArray.readInt();
            int i4 = 16;
            if (readUnsignedInt == 1) {
                extractorInput2.peekFully(parsableByteArray.data, 8, 8);
                parsableByteArray.setLimit(16);
                readUnsignedInt = parsableByteArray.readLong();
            } else {
                if (readUnsignedInt == 0) {
                    long length2 = extractorInput.getLength();
                    if (length2 != j2) {
                        readUnsignedInt = ((long) 8) + (length2 - extractorInput.getPeekPosition());
                    }
                }
                i4 = 8;
            }
            if (length != j2 && ((long) i3) + readUnsignedInt > length) {
                return z5;
            }
            long j3 = (long) i4;
            if (readUnsignedInt < j3) {
                return z5;
            }
            i3 += i4;
            if (readInt == 1836019574) {
                i2 += (int) readUnsignedInt;
                if (length != -1 && ((long) i2) > length) {
                    i2 = (int) length;
                }
                j2 = -1;
            } else if (readInt == 1836019558 || readInt == 1836475768) {
                z2 = false;
                z3 = true;
            } else {
                long j4 = readUnsignedInt;
                int i5 = i2;
                if ((((long) i3) + readUnsignedInt) - j3 >= ((long) i5)) {
                    break;
                }
                int i6 = (int) (j4 - j3);
                i3 += i6;
                if (readInt == 1718909296) {
                    if (i6 < 8) {
                        return false;
                    }
                    parsableByteArray.reset(i6);
                    extractorInput2.peekFully(parsableByteArray.data, 0, i6);
                    int i7 = i6 / 4;
                    int i8 = 0;
                    while (true) {
                        if (i8 >= i7) {
                            z4 = z6;
                            break;
                        }
                        z4 = true;
                        if (i8 == 1) {
                            parsableByteArray.skipBytes(4);
                        } else if (isCompatibleBrand(parsableByteArray.readInt())) {
                            break;
                        }
                        i8++;
                    }
                    if (!z4) {
                        return false;
                    }
                    z6 = z4;
                } else if (i6 != 0) {
                    extractorInput2.advancePeekPosition(i6);
                }
                i2 = i5;
                j2 = -1;
                z5 = false;
            }
        }
        z2 = false;
        z3 = false;
        if (z6 && z == z3) {
            z2 = true;
        }
        return z2;
    }

    private static boolean isCompatibleBrand(int i) {
        if ((i >>> 8) == 3368816) {
            return true;
        }
        for (int i2 : COMPATIBLE_BRANDS) {
            if (i2 == i) {
                return true;
            }
        }
        return false;
    }

    private Sniffer() {
    }
}
