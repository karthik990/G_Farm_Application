package com.google.android.exoplayer2.text.ssa;

import android.text.Layout.Alignment;
import androidx.work.WorkRequest;
import com.google.android.exoplayer2.C1996C;
import com.google.android.exoplayer2.text.Cue;
import com.google.android.exoplayer2.text.SimpleSubtitleDecoder;
import com.google.android.exoplayer2.text.Subtitle;
import com.google.android.exoplayer2.util.Assertions;
import com.google.android.exoplayer2.util.Log;
import com.google.android.exoplayer2.util.ParsableByteArray;
import com.google.android.exoplayer2.util.Util;
import com.mobiroller.constants.Constants;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class SsaDecoder extends SimpleSubtitleDecoder {
    private static final float DEFAULT_MARGIN = 0.05f;
    private static final String DIALOGUE_LINE_PREFIX = "Dialogue:";
    static final String FORMAT_LINE_PREFIX = "Format:";
    private static final Pattern SSA_TIMECODE_PATTERN = Pattern.compile("(?:(\\d+):)?(\\d+):(\\d+)[:.](\\d+)");
    static final String STYLE_LINE_PREFIX = "Style:";
    private static final String TAG = "SsaDecoder";
    private final SsaDialogueFormat dialogueFormatFromInitializationData;
    private final boolean haveInitializationData;
    private float screenHeight;
    private float screenWidth;
    private Map<String, SsaStyle> styles;

    private static float computeDefaultLineOrPosition(int i) {
        if (i == 0) {
            return DEFAULT_MARGIN;
        }
        if (i != 1) {
            return i != 2 ? -3.4028235E38f : 0.95f;
        }
        return 0.5f;
    }

    public SsaDecoder() {
        this(null);
    }

    public SsaDecoder(List<byte[]> list) {
        super(TAG);
        this.screenWidth = -3.4028235E38f;
        this.screenHeight = -3.4028235E38f;
        if (list == null || list.isEmpty()) {
            this.haveInitializationData = false;
            this.dialogueFormatFromInitializationData = null;
            return;
        }
        this.haveInitializationData = true;
        String fromUtf8Bytes = Util.fromUtf8Bytes((byte[]) list.get(0));
        Assertions.checkArgument(fromUtf8Bytes.startsWith(FORMAT_LINE_PREFIX));
        this.dialogueFormatFromInitializationData = (SsaDialogueFormat) Assertions.checkNotNull(SsaDialogueFormat.fromFormatLine(fromUtf8Bytes));
        parseHeader(new ParsableByteArray((byte[]) list.get(1)));
    }

    /* access modifiers changed from: protected */
    public Subtitle decode(byte[] bArr, int i, boolean z) {
        ArrayList arrayList = new ArrayList();
        ArrayList arrayList2 = new ArrayList();
        ParsableByteArray parsableByteArray = new ParsableByteArray(bArr, i);
        if (!this.haveInitializationData) {
            parseHeader(parsableByteArray);
        }
        parseEventBody(parsableByteArray, arrayList, arrayList2);
        return new SsaSubtitle(arrayList, arrayList2);
    }

    private void parseHeader(ParsableByteArray parsableByteArray) {
        while (true) {
            String readLine = parsableByteArray.readLine();
            if (readLine == null) {
                return;
            }
            if ("[Script Info]".equalsIgnoreCase(readLine)) {
                parseScriptInfo(parsableByteArray);
            } else if ("[V4+ Styles]".equalsIgnoreCase(readLine)) {
                this.styles = parseStyles(parsableByteArray);
            } else if ("[V4 Styles]".equalsIgnoreCase(readLine)) {
                Log.m1394i(TAG, "[V4 Styles] are not supported");
            } else if ("[Events]".equalsIgnoreCase(readLine)) {
                return;
            }
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:14:0x0044, code lost:
        if (r2.equals("playresx") != false) goto L_0x0048;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private void parseScriptInfo(com.google.android.exoplayer2.util.ParsableByteArray r7) {
        /*
            r6 = this;
        L_0x0000:
            java.lang.String r0 = r7.readLine()
            if (r0 == 0) goto L_0x0069
            int r1 = r7.bytesLeft()
            if (r1 == 0) goto L_0x0014
            int r1 = r7.peekUnsignedByte()
            r2 = 91
            if (r1 == r2) goto L_0x0069
        L_0x0014:
            java.lang.String r1 = ":"
            java.lang.String[] r0 = r0.split(r1)
            int r1 = r0.length
            r2 = 2
            if (r1 == r2) goto L_0x001f
            goto L_0x0000
        L_0x001f:
            r1 = 0
            r2 = r0[r1]
            java.lang.String r2 = r2.trim()
            java.lang.String r2 = com.google.android.exoplayer2.util.Util.toLowerInvariant(r2)
            r3 = -1
            int r4 = r2.hashCode()
            r5 = 1
            switch(r4) {
                case 1879649548: goto L_0x003e;
                case 1879649549: goto L_0x0034;
                default: goto L_0x0033;
            }
        L_0x0033:
            goto L_0x0047
        L_0x0034:
            java.lang.String r1 = "playresy"
            boolean r1 = r2.equals(r1)
            if (r1 == 0) goto L_0x0047
            r1 = 1
            goto L_0x0048
        L_0x003e:
            java.lang.String r4 = "playresx"
            boolean r2 = r2.equals(r4)
            if (r2 == 0) goto L_0x0047
            goto L_0x0048
        L_0x0047:
            r1 = -1
        L_0x0048:
            if (r1 == 0) goto L_0x005c
            if (r1 == r5) goto L_0x004d
            goto L_0x0000
        L_0x004d:
            r0 = r0[r5]     // Catch:{ NumberFormatException -> 0x005a }
            java.lang.String r0 = r0.trim()     // Catch:{ NumberFormatException -> 0x005a }
            float r0 = java.lang.Float.parseFloat(r0)     // Catch:{ NumberFormatException -> 0x005a }
            r6.screenHeight = r0     // Catch:{ NumberFormatException -> 0x005a }
            goto L_0x0000
        L_0x005a:
            goto L_0x0000
        L_0x005c:
            r0 = r0[r5]     // Catch:{ NumberFormatException -> 0x005a }
            java.lang.String r0 = r0.trim()     // Catch:{ NumberFormatException -> 0x005a }
            float r0 = java.lang.Float.parseFloat(r0)     // Catch:{ NumberFormatException -> 0x005a }
            r6.screenWidth = r0     // Catch:{ NumberFormatException -> 0x005a }
            goto L_0x0000
        L_0x0069:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.exoplayer2.text.ssa.SsaDecoder.parseScriptInfo(com.google.android.exoplayer2.util.ParsableByteArray):void");
    }

    private static Map<String, SsaStyle> parseStyles(ParsableByteArray parsableByteArray) {
        LinkedHashMap linkedHashMap = new LinkedHashMap();
        Format format = null;
        while (true) {
            String readLine = parsableByteArray.readLine();
            if (readLine == null || (parsableByteArray.bytesLeft() != 0 && parsableByteArray.peekUnsignedByte() == 91)) {
                return linkedHashMap;
            }
            if (readLine.startsWith(FORMAT_LINE_PREFIX)) {
                format = Format.fromFormatLine(readLine);
            } else if (readLine.startsWith(STYLE_LINE_PREFIX)) {
                if (format == null) {
                    StringBuilder sb = new StringBuilder();
                    sb.append("Skipping 'Style:' line before 'Format:' line: ");
                    sb.append(readLine);
                    Log.m1396w(TAG, sb.toString());
                } else {
                    SsaStyle fromStyleLine = SsaStyle.fromStyleLine(readLine, format);
                    if (fromStyleLine != null) {
                        linkedHashMap.put(fromStyleLine.name, fromStyleLine);
                    }
                }
            }
        }
        return linkedHashMap;
    }

    private void parseEventBody(ParsableByteArray parsableByteArray, List<List<Cue>> list, List<Long> list2) {
        SsaDialogueFormat ssaDialogueFormat = this.haveInitializationData ? this.dialogueFormatFromInitializationData : null;
        while (true) {
            String readLine = parsableByteArray.readLine();
            if (readLine == null) {
                return;
            }
            if (readLine.startsWith(FORMAT_LINE_PREFIX)) {
                ssaDialogueFormat = SsaDialogueFormat.fromFormatLine(readLine);
            } else if (readLine.startsWith(DIALOGUE_LINE_PREFIX)) {
                if (ssaDialogueFormat == null) {
                    StringBuilder sb = new StringBuilder();
                    sb.append("Skipping dialogue line before complete format: ");
                    sb.append(readLine);
                    Log.m1396w(TAG, sb.toString());
                } else {
                    parseDialogueLine(readLine, ssaDialogueFormat, list, list2);
                }
            }
        }
    }

    private void parseDialogueLine(String str, SsaDialogueFormat ssaDialogueFormat, List<List<Cue>> list, List<Long> list2) {
        Assertions.checkArgument(str.startsWith(DIALOGUE_LINE_PREFIX));
        String[] split = str.substring(9).split(",", ssaDialogueFormat.length);
        int length = split.length;
        int i = ssaDialogueFormat.length;
        String str2 = TAG;
        if (length != i) {
            StringBuilder sb = new StringBuilder();
            sb.append("Skipping dialogue line with fewer columns than format: ");
            sb.append(str);
            Log.m1396w(str2, sb.toString());
            return;
        }
        long parseTimecodeUs = parseTimecodeUs(split[ssaDialogueFormat.startTimeIndex]);
        String str3 = "Skipping invalid timing: ";
        if (parseTimecodeUs == C1996C.TIME_UNSET) {
            StringBuilder sb2 = new StringBuilder();
            sb2.append(str3);
            sb2.append(str);
            Log.m1396w(str2, sb2.toString());
            return;
        }
        long parseTimecodeUs2 = parseTimecodeUs(split[ssaDialogueFormat.endTimeIndex]);
        if (parseTimecodeUs2 == C1996C.TIME_UNSET) {
            StringBuilder sb3 = new StringBuilder();
            sb3.append(str3);
            sb3.append(str);
            Log.m1396w(str2, sb3.toString());
            return;
        }
        SsaStyle ssaStyle = (this.styles == null || ssaDialogueFormat.styleIndex == -1) ? null : (SsaStyle) this.styles.get(split[ssaDialogueFormat.styleIndex].trim());
        String str4 = split[ssaDialogueFormat.textIndex];
        Overrides parseFromDialogue = Overrides.parseFromDialogue(str4);
        String stripStyleOverrides = Overrides.stripStyleOverrides(str4);
        String str5 = Constants.NEW_LINE;
        Cue createCue = createCue(stripStyleOverrides.replaceAll("\\\\N", str5).replaceAll("\\\\n", str5), ssaStyle, parseFromDialogue, this.screenWidth, this.screenHeight);
        int addCuePlacerholderByTime = addCuePlacerholderByTime(parseTimecodeUs2, list2, list);
        for (int addCuePlacerholderByTime2 = addCuePlacerholderByTime(parseTimecodeUs, list2, list); addCuePlacerholderByTime2 < addCuePlacerholderByTime; addCuePlacerholderByTime2++) {
            ((List) list.get(addCuePlacerholderByTime2)).add(createCue);
        }
    }

    private static long parseTimecodeUs(String str) {
        Matcher matcher = SSA_TIMECODE_PATTERN.matcher(str.trim());
        if (!matcher.matches()) {
            return C1996C.TIME_UNSET;
        }
        return (Long.parseLong((String) Util.castNonNull(matcher.group(1))) * 60 * 60 * 1000000) + (Long.parseLong((String) Util.castNonNull(matcher.group(2))) * 60 * 1000000) + (Long.parseLong((String) Util.castNonNull(matcher.group(3))) * 1000000) + (Long.parseLong((String) Util.castNonNull(matcher.group(4))) * WorkRequest.MIN_BACKOFF_MILLIS);
    }

    private static Cue createCue(String str, SsaStyle ssaStyle, Overrides overrides, float f, float f2) {
        float f3;
        float f4;
        int i = -1;
        if (overrides.alignment != -1) {
            i = overrides.alignment;
        } else if (ssaStyle != null) {
            i = ssaStyle.alignment;
        }
        int positionAnchor = toPositionAnchor(i);
        int lineAnchor = toLineAnchor(i);
        if (overrides.position == null || f2 == -3.4028235E38f || f == -3.4028235E38f) {
            f4 = computeDefaultLineOrPosition(positionAnchor);
            f3 = computeDefaultLineOrPosition(lineAnchor);
        } else {
            f4 = overrides.position.x / f;
            f3 = overrides.position.y / f2;
        }
        float f5 = f3;
        String str2 = str;
        Cue cue = new Cue(str2, toTextAlignment(i), f5, 0, lineAnchor, f4, positionAnchor, -3.4028235E38f);
        return cue;
    }

    private static Alignment toTextAlignment(int i) {
        switch (i) {
            case -1:
                return null;
            case 1:
            case 4:
            case 7:
                return Alignment.ALIGN_NORMAL;
            case 2:
            case 5:
            case 8:
                return Alignment.ALIGN_CENTER;
            case 3:
            case 6:
            case 9:
                return Alignment.ALIGN_OPPOSITE;
            default:
                StringBuilder sb = new StringBuilder();
                sb.append("Unknown alignment: ");
                sb.append(i);
                Log.m1396w(TAG, sb.toString());
                return null;
        }
    }

    private static int toLineAnchor(int i) {
        switch (i) {
            case -1:
                return Integer.MIN_VALUE;
            case 1:
            case 2:
            case 3:
                return 2;
            case 4:
            case 5:
            case 6:
                return 1;
            case 7:
            case 8:
            case 9:
                return 0;
            default:
                StringBuilder sb = new StringBuilder();
                sb.append("Unknown alignment: ");
                sb.append(i);
                Log.m1396w(TAG, sb.toString());
                return Integer.MIN_VALUE;
        }
    }

    private static int toPositionAnchor(int i) {
        switch (i) {
            case -1:
                return Integer.MIN_VALUE;
            case 1:
            case 4:
            case 7:
                return 0;
            case 2:
            case 5:
            case 8:
                return 1;
            case 3:
            case 6:
            case 9:
                return 2;
            default:
                StringBuilder sb = new StringBuilder();
                sb.append("Unknown alignment: ");
                sb.append(i);
                Log.m1396w(TAG, sb.toString());
                return Integer.MIN_VALUE;
        }
    }

    private static int addCuePlacerholderByTime(long j, List<Long> list, List<List<Cue>> list2) {
        int i;
        ArrayList arrayList;
        int size = list.size() - 1;
        while (true) {
            if (size < 0) {
                i = 0;
                break;
            } else if (((Long) list.get(size)).longValue() == j) {
                return size;
            } else {
                if (((Long) list.get(size)).longValue() < j) {
                    i = size + 1;
                    break;
                }
                size--;
            }
        }
        list.add(i, Long.valueOf(j));
        if (i == 0) {
            arrayList = new ArrayList();
        } else {
            arrayList = new ArrayList((Collection) list2.get(i - 1));
        }
        list2.add(i, arrayList);
        return i;
    }
}
