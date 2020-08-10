package com.google.android.exoplayer2.text.cea;

import android.text.Layout.Alignment;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.text.style.StyleSpan;
import android.text.style.UnderlineSpan;
import androidx.core.internal.view.SupportMenu;
import androidx.core.view.InputDeviceCompat;
import com.google.android.exoplayer2.text.Cue;
import com.google.android.exoplayer2.text.Subtitle;
import com.google.android.exoplayer2.text.SubtitleDecoderException;
import com.google.android.exoplayer2.text.SubtitleInputBuffer;
import com.google.android.exoplayer2.text.SubtitleOutputBuffer;
import com.google.android.exoplayer2.util.Log;
import com.google.android.exoplayer2.util.MimeTypes;
import com.google.android.exoplayer2.util.ParsableByteArray;
import com.google.common.base.Ascii;
import com.twitter.sdk.android.core.internal.TwitterApiConstants.Errors;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.apache.http.HttpStatus;
import org.objectweb.asm.Opcodes;

public final class Cea608Decoder extends CeaDecoder {
    private static final int[] BASIC_CHARACTER_SET = {32, 33, 34, 35, 36, 37, 38, 39, 40, 41, 225, 43, 44, 45, 46, 47, 48, 49, 50, 51, 52, 53, 54, 55, 56, 57, 58, 59, 60, 61, 62, 63, 64, 65, 66, 67, 68, 69, 70, 71, 72, 73, 74, 75, 76, 77, 78, 79, 80, 81, 82, 83, 84, 85, 86, 87, 88, 89, 90, 91, 233, 93, 237, 243, 250, 97, 98, 99, 100, 101, 102, 103, 104, 105, 106, 107, 108, 109, 110, 111, 112, 113, 114, 115, 116, 117, 118, 119, 120, 121, 122, 231, 247, 209, 241, 9632};
    private static final int CC_FIELD_FLAG = 1;
    private static final byte CC_IMPLICIT_DATA_HEADER = -4;
    private static final int CC_MODE_PAINT_ON = 3;
    private static final int CC_MODE_POP_ON = 2;
    private static final int CC_MODE_ROLL_UP = 1;
    private static final int CC_MODE_UNKNOWN = 0;
    private static final int CC_TYPE_FLAG = 2;
    private static final int CC_VALID_FLAG = 4;
    private static final int[] COLUMN_INDICES = {0, 4, 8, 12, 16, 20, 24, 28};
    private static final byte CTRL_BACKSPACE = 33;
    private static final byte CTRL_CARRIAGE_RETURN = 45;
    private static final byte CTRL_DELETE_TO_END_OF_ROW = 36;
    private static final byte CTRL_END_OF_CAPTION = 47;
    private static final byte CTRL_ERASE_DISPLAYED_MEMORY = 44;
    private static final byte CTRL_ERASE_NON_DISPLAYED_MEMORY = 46;
    private static final byte CTRL_RESUME_CAPTION_LOADING = 32;
    private static final byte CTRL_RESUME_DIRECT_CAPTIONING = 41;
    private static final byte CTRL_RESUME_TEXT_DISPLAY = 43;
    private static final byte CTRL_ROLL_UP_CAPTIONS_2_ROWS = 37;
    private static final byte CTRL_ROLL_UP_CAPTIONS_3_ROWS = 38;
    private static final byte CTRL_ROLL_UP_CAPTIONS_4_ROWS = 39;
    private static final byte CTRL_TEXT_RESTART = 42;
    private static final int DEFAULT_CAPTIONS_ROW_COUNT = 4;
    private static final int NTSC_CC_CHANNEL_1 = 0;
    private static final int NTSC_CC_CHANNEL_2 = 1;
    private static final int NTSC_CC_FIELD_1 = 0;
    private static final int NTSC_CC_FIELD_2 = 1;
    private static final boolean[] ODD_PARITY_BYTE_TABLE = {false, true, true, false, true, false, false, true, true, false, false, true, false, true, true, false, true, false, false, true, false, true, true, false, false, true, true, false, true, false, false, true, true, false, false, true, false, true, true, false, false, true, true, false, true, false, false, true, false, true, true, false, true, false, false, true, true, false, false, true, false, true, true, false, true, false, false, true, false, true, true, false, false, true, true, false, true, false, false, true, false, true, true, false, true, false, false, true, true, false, false, true, false, true, true, false, false, true, true, false, true, false, false, true, true, false, false, true, false, true, true, false, true, false, false, true, false, true, true, false, false, true, true, false, true, false, false, true, true, false, false, true, false, true, true, false, false, true, true, false, true, false, false, true, false, true, true, false, true, false, false, true, true, false, false, true, false, true, true, false, false, true, true, false, true, false, false, true, true, false, false, true, false, true, true, false, true, false, false, true, false, true, true, false, false, true, true, false, true, false, false, true, false, true, true, false, true, false, false, true, true, false, false, true, false, true, true, false, true, false, false, true, false, true, true, false, false, true, true, false, true, false, false, true, true, false, false, true, false, true, true, false, false, true, true, false, true, false, false, true, false, true, true, false, true, false, false, true, true, false, false, true, false, true, true, false};
    private static final int[] ROW_INDICES = {11, 1, 3, 12, 14, 5, 7, 9};
    private static final int[] SPECIAL_CHARACTER_SET = {Opcodes.FRETURN, Opcodes.ARETURN, 189, Opcodes.ATHROW, 8482, Opcodes.IF_ICMPGE, Opcodes.IF_ICMPGT, 9834, 224, 32, 232, 226, 234, 238, 244, 251};
    private static final int[] SPECIAL_ES_FR_CHARACTER_SET = {Opcodes.INSTANCEOF, 201, 211, 218, 220, 252, 8216, Opcodes.IF_ICMPLT, 42, 39, 8212, Opcodes.RET, 8480, 8226, 8220, 8221, 192, Opcodes.MONITORENTER, Opcodes.IFNONNULL, 200, 202, 203, 235, HttpStatus.SC_PARTIAL_CONTENT, HttpStatus.SC_MULTI_STATUS, Errors.GUEST_AUTH_ERROR_CODE, 212, 217, 249, 219, Opcodes.LOOKUPSWITCH, Opcodes.NEW};
    private static final int[] SPECIAL_PT_DE_CHARACTER_SET = {Opcodes.MONITOREXIT, 227, HttpStatus.SC_RESET_CONTENT, 204, 236, 210, 242, 213, 245, 123, Opcodes.LUSHR, 92, 94, 95, 124, Opcodes.IAND, 196, 228, 214, 246, 223, Opcodes.IF_ACMPEQ, Opcodes.IF_ICMPLE, 9474, Opcodes.MULTIANEWARRAY, 229, 216, 248, 9484, 9488, 9492, 9496};
    /* access modifiers changed from: private */
    public static final int[] STYLE_COLORS = {-1, -16711936, -16776961, -16711681, SupportMenu.CATEGORY_MASK, InputDeviceCompat.SOURCE_ANY, -65281};
    private static final int STYLE_ITALICS = 7;
    private static final int STYLE_UNCHANGED = 8;
    private static final String TAG = "Cea608Decoder";
    private int captionMode;
    private int captionRowCount;
    private final ParsableByteArray ccData = new ParsableByteArray();
    private final ArrayList<CueBuilder> cueBuilders = new ArrayList<>();
    private List<Cue> cues;
    private int currentChannel = 0;
    private CueBuilder currentCueBuilder = new CueBuilder(0, 4);
    private boolean isCaptionValid;
    private boolean isInCaptionService;
    private List<Cue> lastCues;
    private final int packetLength;
    private byte repeatableControlCc1;
    private byte repeatableControlCc2;
    private boolean repeatableControlSet;
    private final int selectedChannel;
    private final int selectedField;

    private static class CueBuilder {
        private static final int BASE_ROW = 15;
        private static final int SCREEN_CHARWIDTH = 32;
        private int captionMode;
        private int captionRowCount;
        private final StringBuilder captionStringBuilder = new StringBuilder();
        private final List<CueStyle> cueStyles = new ArrayList();
        /* access modifiers changed from: private */
        public int indent;
        private final List<SpannableString> rolledUpCaptions = new ArrayList();
        /* access modifiers changed from: private */
        public int row;
        /* access modifiers changed from: private */
        public int tabOffset;

        private static class CueStyle {
            public int start;
            public final int style;
            public final boolean underline;

            public CueStyle(int i, boolean z, int i2) {
                this.style = i;
                this.underline = z;
                this.start = i2;
            }
        }

        public CueBuilder(int i, int i2) {
            reset(i);
            setCaptionRowCount(i2);
        }

        public void reset(int i) {
            this.captionMode = i;
            this.cueStyles.clear();
            this.rolledUpCaptions.clear();
            this.captionStringBuilder.setLength(0);
            this.row = 15;
            this.indent = 0;
            this.tabOffset = 0;
        }

        public boolean isEmpty() {
            return this.cueStyles.isEmpty() && this.rolledUpCaptions.isEmpty() && this.captionStringBuilder.length() == 0;
        }

        public void setCaptionMode(int i) {
            this.captionMode = i;
        }

        public void setCaptionRowCount(int i) {
            this.captionRowCount = i;
        }

        public void setStyle(int i, boolean z) {
            this.cueStyles.add(new CueStyle(i, z, this.captionStringBuilder.length()));
        }

        public void backspace() {
            int length = this.captionStringBuilder.length();
            if (length > 0) {
                this.captionStringBuilder.delete(length - 1, length);
                int size = this.cueStyles.size() - 1;
                while (size >= 0) {
                    CueStyle cueStyle = (CueStyle) this.cueStyles.get(size);
                    if (cueStyle.start == length) {
                        cueStyle.start--;
                        size--;
                    } else {
                        return;
                    }
                }
            }
        }

        public void append(char c) {
            this.captionStringBuilder.append(c);
        }

        public void rollUp() {
            this.rolledUpCaptions.add(buildCurrentLine());
            this.captionStringBuilder.setLength(0);
            this.cueStyles.clear();
            int min = Math.min(this.captionRowCount, this.row);
            while (this.rolledUpCaptions.size() >= min) {
                this.rolledUpCaptions.remove(0);
            }
        }

        public Cue build(int i) {
            float f;
            int i2;
            int i3;
            SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder();
            for (int i4 = 0; i4 < this.rolledUpCaptions.size(); i4++) {
                spannableStringBuilder.append((CharSequence) this.rolledUpCaptions.get(i4));
                spannableStringBuilder.append(10);
            }
            spannableStringBuilder.append(buildCurrentLine());
            if (spannableStringBuilder.length() == 0) {
                return null;
            }
            int i5 = this.indent + this.tabOffset;
            int length = (32 - i5) - spannableStringBuilder.length();
            int i6 = i5 - length;
            if (i == Integer.MIN_VALUE) {
                i = (this.captionMode != 2 || (Math.abs(i6) >= 3 && length >= 0)) ? (this.captionMode != 2 || i6 <= 0) ? 0 : 2 : 1;
            }
            if (i != 1) {
                if (i == 2) {
                    i5 = 32 - length;
                }
                f = ((((float) i5) / 32.0f) * 0.8f) + 0.1f;
            } else {
                f = 0.5f;
            }
            if (this.captionMode != 1) {
                i3 = this.row;
                if (i3 <= 7) {
                    i2 = 0;
                    Cue cue = new Cue(spannableStringBuilder, Alignment.ALIGN_NORMAL, (float) i3, 1, i2, f, i, -3.4028235E38f);
                    return cue;
                }
            }
            i3 = (this.row - 15) - 2;
            i2 = 2;
            Cue cue2 = new Cue(spannableStringBuilder, Alignment.ALIGN_NORMAL, (float) i3, 1, i2, f, i, -3.4028235E38f);
            return cue2;
        }

        private SpannableString buildCurrentLine() {
            SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder(this.captionStringBuilder);
            int length = spannableStringBuilder.length();
            int i = 0;
            int i2 = -1;
            int i3 = -1;
            int i4 = 0;
            int i5 = -1;
            int i6 = -1;
            boolean z = false;
            while (i < this.cueStyles.size()) {
                CueStyle cueStyle = (CueStyle) this.cueStyles.get(i);
                boolean z2 = cueStyle.underline;
                int i7 = cueStyle.style;
                if (i7 != 8) {
                    boolean z3 = i7 == 7;
                    if (i7 != 7) {
                        i6 = Cea608Decoder.STYLE_COLORS[i7];
                    }
                    z = z3;
                }
                int i8 = cueStyle.start;
                i++;
                if (i8 != (i < this.cueStyles.size() ? ((CueStyle) this.cueStyles.get(i)).start : length)) {
                    if (i2 != -1 && !z2) {
                        setUnderlineSpan(spannableStringBuilder, i2, i8);
                        i2 = -1;
                    } else if (i2 == -1 && z2) {
                        i2 = i8;
                    }
                    if (i3 != -1 && !z) {
                        setItalicSpan(spannableStringBuilder, i3, i8);
                        i3 = -1;
                    } else if (i3 == -1 && z) {
                        i3 = i8;
                    }
                    if (i6 != i5) {
                        setColorSpan(spannableStringBuilder, i4, i8, i5);
                        i5 = i6;
                        i4 = i8;
                    }
                }
            }
            if (!(i2 == -1 || i2 == length)) {
                setUnderlineSpan(spannableStringBuilder, i2, length);
            }
            if (!(i3 == -1 || i3 == length)) {
                setItalicSpan(spannableStringBuilder, i3, length);
            }
            if (i4 != length) {
                setColorSpan(spannableStringBuilder, i4, length, i5);
            }
            return new SpannableString(spannableStringBuilder);
        }

        private static void setUnderlineSpan(SpannableStringBuilder spannableStringBuilder, int i, int i2) {
            spannableStringBuilder.setSpan(new UnderlineSpan(), i, i2, 33);
        }

        private static void setItalicSpan(SpannableStringBuilder spannableStringBuilder, int i, int i2) {
            spannableStringBuilder.setSpan(new StyleSpan(2), i, i2, 33);
        }

        private static void setColorSpan(SpannableStringBuilder spannableStringBuilder, int i, int i2, int i3) {
            if (i3 != -1) {
                spannableStringBuilder.setSpan(new ForegroundColorSpan(i3), i, i2, 33);
            }
        }
    }

    private static int getChannel(byte b) {
        return (b >> 3) & 1;
    }

    private static boolean isCtrlCode(byte b) {
        return (b & 224) == 0;
    }

    private static boolean isExtendedWestEuropeanChar(byte b, byte b2) {
        return (b & 246) == 18 && (b2 & 224) == 32;
    }

    private static boolean isMidrowCtrlCode(byte b, byte b2) {
        return (b & 247) == 17 && (b2 & 240) == 32;
    }

    private static boolean isMiscCode(byte b, byte b2) {
        return (b & 246) == 20 && (b2 & 240) == 32;
    }

    private static boolean isPreambleAddressCode(byte b, byte b2) {
        return (b & 240) == 16 && (b2 & 192) == 64;
    }

    private static boolean isRepeatable(byte b) {
        return (b & 240) == 16;
    }

    private static boolean isServiceSwitchCommand(byte b) {
        return (b & 247) == 20;
    }

    private static boolean isSpecialNorthAmericanChar(byte b, byte b2) {
        return (b & 247) == 17 && (b2 & 240) == 48;
    }

    private static boolean isTabCtrlCode(byte b, byte b2) {
        return (b & 247) == 23 && b2 >= 33 && b2 <= 35;
    }

    private static boolean isXdsControlCode(byte b) {
        return 1 <= b && b <= 15;
    }

    public String getName() {
        return TAG;
    }

    public void release() {
    }

    public /* bridge */ /* synthetic */ SubtitleInputBuffer dequeueInputBuffer() throws SubtitleDecoderException {
        return super.dequeueInputBuffer();
    }

    public /* bridge */ /* synthetic */ SubtitleOutputBuffer dequeueOutputBuffer() throws SubtitleDecoderException {
        return super.dequeueOutputBuffer();
    }

    public /* bridge */ /* synthetic */ void queueInputBuffer(SubtitleInputBuffer subtitleInputBuffer) throws SubtitleDecoderException {
        super.queueInputBuffer(subtitleInputBuffer);
    }

    public /* bridge */ /* synthetic */ void setPositionUs(long j) {
        super.setPositionUs(j);
    }

    public Cea608Decoder(String str, int i) {
        this.packetLength = MimeTypes.APPLICATION_MP4CEA608.equals(str) ? 2 : 3;
        if (i == 1) {
            this.selectedChannel = 0;
            this.selectedField = 0;
        } else if (i == 2) {
            this.selectedChannel = 1;
            this.selectedField = 0;
        } else if (i == 3) {
            this.selectedChannel = 0;
            this.selectedField = 1;
        } else if (i != 4) {
            Log.m1396w(TAG, "Invalid channel. Defaulting to CC1.");
            this.selectedChannel = 0;
            this.selectedField = 0;
        } else {
            this.selectedChannel = 1;
            this.selectedField = 1;
        }
        setCaptionMode(0);
        resetCueBuilders();
        this.isInCaptionService = true;
    }

    public void flush() {
        super.flush();
        this.cues = null;
        this.lastCues = null;
        setCaptionMode(0);
        setCaptionRowCount(4);
        resetCueBuilders();
        this.isCaptionValid = false;
        this.repeatableControlSet = false;
        this.repeatableControlCc1 = 0;
        this.repeatableControlCc2 = 0;
        this.currentChannel = 0;
        this.isInCaptionService = true;
    }

    /* access modifiers changed from: protected */
    public boolean isNewSubtitleDataAvailable() {
        return this.cues != this.lastCues;
    }

    /* access modifiers changed from: protected */
    public Subtitle createSubtitle() {
        List<Cue> list = this.cues;
        this.lastCues = list;
        return new CeaSubtitle(list);
    }

    /* access modifiers changed from: protected */
    /* JADX WARNING: Removed duplicated region for block: B:24:0x006c  */
    /* JADX WARNING: Removed duplicated region for block: B:67:0x0014 A[SYNTHETIC] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void decode(com.google.android.exoplayer2.text.SubtitleInputBuffer r10) {
        /*
            r9 = this;
            com.google.android.exoplayer2.util.ParsableByteArray r0 = r9.ccData
            java.nio.ByteBuffer r1 = r10.data
            byte[] r1 = r1.array()
            java.nio.ByteBuffer r10 = r10.data
            int r10 = r10.limit()
            r0.reset(r1, r10)
            r10 = 0
            r0 = 1
            r1 = 0
        L_0x0014:
            com.google.android.exoplayer2.util.ParsableByteArray r2 = r9.ccData
            int r2 = r2.bytesLeft()
            int r3 = r9.packetLength
            if (r2 < r3) goto L_0x00f4
            r2 = 2
            if (r3 != r2) goto L_0x0023
            r2 = -4
            goto L_0x002a
        L_0x0023:
            com.google.android.exoplayer2.util.ParsableByteArray r2 = r9.ccData
            int r2 = r2.readUnsignedByte()
            byte r2 = (byte) r2
        L_0x002a:
            com.google.android.exoplayer2.util.ParsableByteArray r3 = r9.ccData
            int r3 = r3.readUnsignedByte()
            com.google.android.exoplayer2.util.ParsableByteArray r4 = r9.ccData
            int r4 = r4.readUnsignedByte()
            r5 = r2 & 2
            if (r5 == 0) goto L_0x003b
            goto L_0x0014
        L_0x003b:
            r5 = r2 & 1
            int r6 = r9.selectedField
            if (r5 == r6) goto L_0x0042
            goto L_0x0014
        L_0x0042:
            r5 = r3 & 127(0x7f, float:1.78E-43)
            byte r5 = (byte) r5
            r6 = r4 & 127(0x7f, float:1.78E-43)
            byte r6 = (byte) r6
            if (r5 != 0) goto L_0x004d
            if (r6 != 0) goto L_0x004d
            goto L_0x0014
        L_0x004d:
            boolean r7 = r9.isCaptionValid
            r2 = r2 & 4
            r8 = 4
            if (r2 != r8) goto L_0x0060
            boolean[] r2 = ODD_PARITY_BYTE_TABLE
            boolean r3 = r2[r3]
            if (r3 == 0) goto L_0x0060
            boolean r2 = r2[r4]
            if (r2 == 0) goto L_0x0060
            r2 = 1
            goto L_0x0061
        L_0x0060:
            r2 = 0
        L_0x0061:
            r9.isCaptionValid = r2
            boolean r2 = r9.isCaptionValid
            boolean r2 = r9.isRepeatedCommand(r2, r5, r6)
            if (r2 == 0) goto L_0x006c
            goto L_0x0014
        L_0x006c:
            boolean r2 = r9.isCaptionValid
            if (r2 != 0) goto L_0x0077
            if (r7 == 0) goto L_0x0014
            r9.resetCueBuilders()
        L_0x0075:
            r1 = 1
            goto L_0x0014
        L_0x0077:
            r9.maybeUpdateIsInCaptionService(r5, r6)
            boolean r2 = r9.isInCaptionService
            if (r2 != 0) goto L_0x007f
            goto L_0x0014
        L_0x007f:
            boolean r2 = r9.updateAndVerifyCurrentChannel(r5)
            if (r2 != 0) goto L_0x0086
            goto L_0x0014
        L_0x0086:
            boolean r1 = isCtrlCode(r5)
            if (r1 == 0) goto L_0x00dd
            boolean r1 = isSpecialNorthAmericanChar(r5, r6)
            if (r1 == 0) goto L_0x009c
            com.google.android.exoplayer2.text.cea.Cea608Decoder$CueBuilder r1 = r9.currentCueBuilder
            char r2 = getSpecialNorthAmericanChar(r6)
            r1.append(r2)
            goto L_0x0075
        L_0x009c:
            boolean r1 = isExtendedWestEuropeanChar(r5, r6)
            if (r1 == 0) goto L_0x00b1
            com.google.android.exoplayer2.text.cea.Cea608Decoder$CueBuilder r1 = r9.currentCueBuilder
            r1.backspace()
            com.google.android.exoplayer2.text.cea.Cea608Decoder$CueBuilder r1 = r9.currentCueBuilder
            char r2 = getExtendedWestEuropeanChar(r5, r6)
            r1.append(r2)
            goto L_0x0075
        L_0x00b1:
            boolean r1 = isMidrowCtrlCode(r5, r6)
            if (r1 == 0) goto L_0x00bb
            r9.handleMidrowCtrl(r6)
            goto L_0x0075
        L_0x00bb:
            boolean r1 = isPreambleAddressCode(r5, r6)
            if (r1 == 0) goto L_0x00c5
            r9.handlePreambleAddressCode(r5, r6)
            goto L_0x0075
        L_0x00c5:
            boolean r1 = isTabCtrlCode(r5, r6)
            if (r1 == 0) goto L_0x00d3
            com.google.android.exoplayer2.text.cea.Cea608Decoder$CueBuilder r1 = r9.currentCueBuilder
            int r6 = r6 + -32
            r1.tabOffset = r6
            goto L_0x0075
        L_0x00d3:
            boolean r1 = isMiscCode(r5, r6)
            if (r1 == 0) goto L_0x0075
            r9.handleMiscCode(r6)
            goto L_0x0075
        L_0x00dd:
            com.google.android.exoplayer2.text.cea.Cea608Decoder$CueBuilder r1 = r9.currentCueBuilder
            char r2 = getBasicChar(r5)
            r1.append(r2)
            r1 = r6 & 224(0xe0, float:3.14E-43)
            if (r1 == 0) goto L_0x0075
            com.google.android.exoplayer2.text.cea.Cea608Decoder$CueBuilder r1 = r9.currentCueBuilder
            char r2 = getBasicChar(r6)
            r1.append(r2)
            goto L_0x0075
        L_0x00f4:
            if (r1 == 0) goto L_0x0103
            int r10 = r9.captionMode
            if (r10 == r0) goto L_0x00fd
            r0 = 3
            if (r10 != r0) goto L_0x0103
        L_0x00fd:
            java.util.List r10 = r9.getDisplayCues()
            r9.cues = r10
        L_0x0103:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.exoplayer2.text.cea.Cea608Decoder.decode(com.google.android.exoplayer2.text.SubtitleInputBuffer):void");
    }

    private boolean updateAndVerifyCurrentChannel(byte b) {
        if (isCtrlCode(b)) {
            this.currentChannel = getChannel(b);
        }
        return this.currentChannel == this.selectedChannel;
    }

    private boolean isRepeatedCommand(boolean z, byte b, byte b2) {
        if (!z || !isRepeatable(b)) {
            this.repeatableControlSet = false;
        } else if (this.repeatableControlSet && this.repeatableControlCc1 == b && this.repeatableControlCc2 == b2) {
            this.repeatableControlSet = false;
            return true;
        } else {
            this.repeatableControlSet = true;
            this.repeatableControlCc1 = b;
            this.repeatableControlCc2 = b2;
        }
        return false;
    }

    private void handleMidrowCtrl(byte b) {
        this.currentCueBuilder.append(' ');
        this.currentCueBuilder.setStyle((b >> 1) & 7, (b & 1) == 1);
    }

    private void handlePreambleAddressCode(byte b, byte b2) {
        int i = ROW_INDICES[b & 7];
        boolean z = false;
        if ((b2 & 32) != 0) {
            i++;
        }
        if (i != this.currentCueBuilder.row) {
            if (this.captionMode != 1 && !this.currentCueBuilder.isEmpty()) {
                this.currentCueBuilder = new CueBuilder(this.captionMode, this.captionRowCount);
                this.cueBuilders.add(this.currentCueBuilder);
            }
            this.currentCueBuilder.row = i;
        }
        boolean z2 = (b2 & Ascii.DLE) == 16;
        if ((b2 & 1) == 1) {
            z = true;
        }
        int i2 = (b2 >> 1) & 7;
        this.currentCueBuilder.setStyle(z2 ? 8 : i2, z);
        if (z2) {
            this.currentCueBuilder.indent = COLUMN_INDICES[i2];
        }
    }

    private void handleMiscCode(byte b) {
        if (b == 32) {
            setCaptionMode(2);
        } else if (b != 41) {
            switch (b) {
                case 37:
                    setCaptionMode(1);
                    setCaptionRowCount(2);
                    return;
                case 38:
                    setCaptionMode(1);
                    setCaptionRowCount(3);
                    return;
                case 39:
                    setCaptionMode(1);
                    setCaptionRowCount(4);
                    return;
                default:
                    int i = this.captionMode;
                    if (i != 0) {
                        if (b != 33) {
                            if (b != 36) {
                                switch (b) {
                                    case 44:
                                        this.cues = Collections.emptyList();
                                        int i2 = this.captionMode;
                                        if (i2 == 1 || i2 == 3) {
                                            resetCueBuilders();
                                            break;
                                        }
                                    case 45:
                                        if (i == 1 && !this.currentCueBuilder.isEmpty()) {
                                            this.currentCueBuilder.rollUp();
                                            break;
                                        }
                                    case 46:
                                        resetCueBuilders();
                                        break;
                                    case 47:
                                        this.cues = getDisplayCues();
                                        resetCueBuilders();
                                        break;
                                }
                            }
                        } else {
                            this.currentCueBuilder.backspace();
                        }
                        return;
                    }
                    return;
            }
        } else {
            setCaptionMode(3);
        }
    }

    private List<Cue> getDisplayCues() {
        int size = this.cueBuilders.size();
        ArrayList arrayList = new ArrayList(size);
        int i = 2;
        for (int i2 = 0; i2 < size; i2++) {
            Cue build = ((CueBuilder) this.cueBuilders.get(i2)).build(Integer.MIN_VALUE);
            arrayList.add(build);
            if (build != null) {
                i = Math.min(i, build.positionAnchor);
            }
        }
        ArrayList arrayList2 = new ArrayList(size);
        for (int i3 = 0; i3 < size; i3++) {
            Cue cue = (Cue) arrayList.get(i3);
            if (cue != null) {
                if (cue.positionAnchor != i) {
                    cue = ((CueBuilder) this.cueBuilders.get(i3)).build(i);
                }
                arrayList2.add(cue);
            }
        }
        return arrayList2;
    }

    private void setCaptionMode(int i) {
        int i2 = this.captionMode;
        if (i2 != i) {
            this.captionMode = i;
            if (i == 3) {
                for (int i3 = 0; i3 < this.cueBuilders.size(); i3++) {
                    ((CueBuilder) this.cueBuilders.get(i3)).setCaptionMode(i);
                }
                return;
            }
            resetCueBuilders();
            if (i2 == 3 || i == 1 || i == 0) {
                this.cues = Collections.emptyList();
            }
        }
    }

    private void setCaptionRowCount(int i) {
        this.captionRowCount = i;
        this.currentCueBuilder.setCaptionRowCount(i);
    }

    private void resetCueBuilders() {
        this.currentCueBuilder.reset(this.captionMode);
        this.cueBuilders.clear();
        this.cueBuilders.add(this.currentCueBuilder);
    }

    private void maybeUpdateIsInCaptionService(byte b, byte b2) {
        if (isXdsControlCode(b)) {
            this.isInCaptionService = false;
        } else if (isServiceSwitchCommand(b)) {
            if (!(b2 == 32 || b2 == 47)) {
                switch (b2) {
                    case 37:
                    case 38:
                    case 39:
                        break;
                    default:
                        switch (b2) {
                            case 41:
                                break;
                            case 42:
                            case 43:
                                this.isInCaptionService = false;
                                return;
                            default:
                                return;
                        }
                }
            }
            this.isInCaptionService = true;
        }
    }

    private static char getBasicChar(byte b) {
        return (char) BASIC_CHARACTER_SET[(b & Byte.MAX_VALUE) - 32];
    }

    private static char getSpecialNorthAmericanChar(byte b) {
        return (char) SPECIAL_CHARACTER_SET[b & Ascii.f1875SI];
    }

    private static char getExtendedWestEuropeanChar(byte b, byte b2) {
        if ((b & 1) == 0) {
            return getExtendedEsFrChar(b2);
        }
        return getExtendedPtDeChar(b2);
    }

    private static char getExtendedEsFrChar(byte b) {
        return (char) SPECIAL_ES_FR_CHARACTER_SET[b & Ascii.f1878US];
    }

    private static char getExtendedPtDeChar(byte b) {
        return (char) SPECIAL_PT_DE_CHARACTER_SET[b & Ascii.f1878US];
    }
}
