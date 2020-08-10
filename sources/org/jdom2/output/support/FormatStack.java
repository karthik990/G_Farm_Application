package org.jdom2.output.support;

import org.jdom2.internal.ArrayCopy;
import org.jdom2.output.EscapeStrategy;
import org.jdom2.output.Format;
import org.jdom2.output.Format.TextMode;

public final class FormatStack {
    private int capacity = 16;
    private final TextMode defaultMode;
    private int depth = 0;
    private final String encoding;
    private boolean[] escapeOutput;
    private final EscapeStrategy escapeStrategy;
    private final boolean expandEmptyElements;
    private boolean[] ignoreTrAXEscapingPIs;
    private final String indent;
    private String[] levelEOL;
    private String[] levelEOLIndent;
    private String[] levelIndent;
    private final String lineSeparator;
    private TextMode[] mode;
    private final boolean omitDeclaration;
    private final boolean omitEncoding;
    private final boolean specifiedAttributesOnly;
    private String[] termEOLIndent;

    /* renamed from: org.jdom2.output.support.FormatStack$1 */
    static /* synthetic */ class C61451 {
        static final /* synthetic */ int[] $SwitchMap$org$jdom2$output$Format$TextMode = new int[TextMode.values().length];

        static {
            try {
                $SwitchMap$org$jdom2$output$Format$TextMode[TextMode.PRESERVE.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
        }
    }

    public FormatStack(Format format) {
        int i = this.capacity;
        this.levelIndent = new String[i];
        this.levelEOL = new String[i];
        this.levelEOLIndent = new String[i];
        this.termEOLIndent = new String[i];
        this.ignoreTrAXEscapingPIs = new boolean[i];
        this.mode = new TextMode[i];
        this.escapeOutput = new boolean[i];
        this.indent = format.getIndent();
        this.lineSeparator = format.getLineSeparator();
        this.encoding = format.getEncoding();
        this.omitDeclaration = format.getOmitDeclaration();
        this.omitEncoding = format.getOmitEncoding();
        this.expandEmptyElements = format.getExpandEmptyElements();
        this.escapeStrategy = format.getEscapeStrategy();
        this.defaultMode = format.getTextMode();
        this.specifiedAttributesOnly = format.isSpecifiedAttributesOnly();
        this.mode[this.depth] = format.getTextMode();
        String str = null;
        if (this.mode[this.depth] == TextMode.PRESERVE) {
            String[] strArr = this.levelIndent;
            int i2 = this.depth;
            strArr[i2] = null;
            this.levelEOL[i2] = null;
            this.levelEOLIndent[i2] = null;
            this.termEOLIndent[i2] = null;
        } else {
            this.levelIndent[this.depth] = format.getIndent() == null ? null : "";
            this.levelEOL[this.depth] = format.getLineSeparator();
            String[] strArr2 = this.levelEOLIndent;
            int i3 = this.depth;
            if (this.levelIndent[i3] != null) {
                str = this.levelEOL[i3];
            }
            strArr2[i3] = str;
            String[] strArr3 = this.termEOLIndent;
            int i4 = this.depth;
            strArr3[i4] = this.levelEOLIndent[i4];
        }
        this.ignoreTrAXEscapingPIs[this.depth] = format.getIgnoreTrAXEscapingPIs();
        this.escapeOutput[this.depth] = true;
    }

    private final void resetReusableIndents() {
        int i = this.depth;
        while (true) {
            i++;
            String[] strArr = this.levelIndent;
            if (i < strArr.length && strArr[i] != null) {
                strArr[i] = null;
            } else {
                return;
            }
        }
    }

    public String getIndent() {
        return this.indent;
    }

    public String getLineSeparator() {
        return this.lineSeparator;
    }

    public String getEncoding() {
        return this.encoding;
    }

    public boolean isOmitDeclaration() {
        return this.omitDeclaration;
    }

    public boolean isSpecifiedAttributesOnly() {
        return this.specifiedAttributesOnly;
    }

    public boolean isOmitEncoding() {
        return this.omitEncoding;
    }

    public boolean isExpandEmptyElements() {
        return this.expandEmptyElements;
    }

    public EscapeStrategy getEscapeStrategy() {
        return this.escapeStrategy;
    }

    public boolean isIgnoreTrAXEscapingPIs() {
        return this.ignoreTrAXEscapingPIs[this.depth];
    }

    public void setIgnoreTrAXEscapingPIs(boolean z) {
        this.ignoreTrAXEscapingPIs[this.depth] = z;
    }

    public boolean getEscapeOutput() {
        return this.escapeOutput[this.depth];
    }

    public void setEscapeOutput(boolean z) {
        this.escapeOutput[this.depth] = z;
    }

    public TextMode getDefaultMode() {
        return this.defaultMode;
    }

    public String getLevelIndent() {
        return this.levelIndent[this.depth];
    }

    public String getPadBetween() {
        return this.levelEOLIndent[this.depth];
    }

    public String getPadLast() {
        return this.termEOLIndent[this.depth];
    }

    public void setLevelIndent(String str) {
        String str2;
        String[] strArr = this.levelIndent;
        int i = this.depth;
        strArr[i] = str;
        String[] strArr2 = this.levelEOLIndent;
        if (str == null || this.levelEOL[i] == null) {
            str2 = null;
        } else {
            StringBuilder sb = new StringBuilder();
            sb.append(this.levelEOL[this.depth]);
            sb.append(str);
            str2 = sb.toString();
        }
        strArr2[i] = str2;
        resetReusableIndents();
    }

    public String getLevelEOL() {
        return this.levelEOL[this.depth];
    }

    public void setLevelEOL(String str) {
        this.levelEOL[this.depth] = str;
        resetReusableIndents();
    }

    public TextMode getTextMode() {
        return this.mode[this.depth];
    }

    public void setTextMode(TextMode textMode) {
        int i;
        TextMode[] textModeArr = this.mode;
        int i2 = this.depth;
        if (textModeArr[i2] != textMode) {
            textModeArr[i2] = textMode;
            int i3 = 1;
            if (C61451.$SwitchMap$org$jdom2$output$Format$TextMode[textMode.ordinal()] != 1) {
                String[] strArr = this.levelEOL;
                int i4 = this.depth;
                String str = this.lineSeparator;
                strArr[i4] = str;
                String str2 = this.indent;
                if (str2 == null || str == null) {
                    String[] strArr2 = this.levelEOLIndent;
                    int i5 = this.depth;
                    strArr2[i5] = null;
                    this.termEOLIndent[i5] = null;
                } else {
                    if (i4 > 0) {
                        StringBuilder sb = new StringBuilder(str2.length() * this.depth);
                        while (true) {
                            i = this.depth;
                            if (i3 >= i) {
                                break;
                            }
                            sb.append(this.indent);
                            i3++;
                        }
                        String[] strArr3 = this.termEOLIndent;
                        StringBuilder sb2 = new StringBuilder();
                        sb2.append(this.lineSeparator);
                        sb2.append(sb.toString());
                        strArr3[i] = sb2.toString();
                        sb.append(this.indent);
                        this.levelIndent[this.depth] = sb.toString();
                    } else {
                        this.termEOLIndent[i4] = str;
                        this.levelIndent[i4] = "";
                    }
                    String[] strArr4 = this.levelEOLIndent;
                    int i6 = this.depth;
                    StringBuilder sb3 = new StringBuilder();
                    sb3.append(this.lineSeparator);
                    sb3.append(this.levelIndent[this.depth]);
                    strArr4[i6] = sb3.toString();
                }
            } else {
                String[] strArr5 = this.levelEOL;
                int i7 = this.depth;
                strArr5[i7] = null;
                this.levelIndent[i7] = null;
                this.levelEOLIndent[i7] = null;
                this.termEOLIndent[i7] = null;
            }
            resetReusableIndents();
        }
    }

    public void push() {
        int i = this.depth;
        this.depth = i + 1;
        int i2 = this.depth;
        int i3 = this.capacity;
        if (i2 >= i3) {
            this.capacity = i3 * 2;
            this.levelIndent = (String[]) ArrayCopy.copyOf((E[]) this.levelIndent, this.capacity);
            this.levelEOL = (String[]) ArrayCopy.copyOf((E[]) this.levelEOL, this.capacity);
            this.levelEOLIndent = (String[]) ArrayCopy.copyOf((E[]) this.levelEOLIndent, this.capacity);
            this.termEOLIndent = (String[]) ArrayCopy.copyOf((E[]) this.termEOLIndent, this.capacity);
            this.ignoreTrAXEscapingPIs = ArrayCopy.copyOf(this.ignoreTrAXEscapingPIs, this.capacity);
            this.mode = (TextMode[]) ArrayCopy.copyOf((E[]) this.mode, this.capacity);
            this.escapeOutput = ArrayCopy.copyOf(this.escapeOutput, this.capacity);
        }
        boolean[] zArr = this.ignoreTrAXEscapingPIs;
        int i4 = this.depth;
        zArr[i4] = zArr[i];
        TextMode[] textModeArr = this.mode;
        textModeArr[i4] = textModeArr[i];
        boolean[] zArr2 = this.escapeOutput;
        zArr2[i4] = zArr2[i];
        String[] strArr = this.levelIndent;
        if (strArr[i] != null) {
            String[] strArr2 = this.levelEOL;
            if (strArr2[i] != null) {
                if (strArr[i4] == null) {
                    strArr2[i4] = strArr2[i];
                    String[] strArr3 = this.termEOLIndent;
                    StringBuilder sb = new StringBuilder();
                    sb.append(this.levelEOL[this.depth]);
                    sb.append(this.levelIndent[i]);
                    strArr3[i4] = sb.toString();
                    String[] strArr4 = this.levelIndent;
                    int i5 = this.depth;
                    StringBuilder sb2 = new StringBuilder();
                    sb2.append(this.levelIndent[i]);
                    sb2.append(this.indent);
                    strArr4[i5] = sb2.toString();
                    String[] strArr5 = this.levelEOLIndent;
                    int i6 = this.depth;
                    StringBuilder sb3 = new StringBuilder();
                    sb3.append(this.levelEOL[this.depth]);
                    sb3.append(this.levelIndent[this.depth]);
                    strArr5[i6] = sb3.toString();
                    return;
                }
                return;
            }
        }
        String[] strArr6 = this.levelIndent;
        int i7 = this.depth;
        strArr6[i7] = null;
        this.levelEOL[i7] = null;
        this.levelEOLIndent[i7] = null;
        this.termEOLIndent[i7] = null;
    }

    public void pop() {
        this.depth--;
    }
}
