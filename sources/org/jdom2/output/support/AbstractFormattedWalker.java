package org.jdom2.output.support;

import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import org.jdom2.CDATA;
import org.jdom2.Content;
import org.jdom2.Content.CType;
import org.jdom2.internal.ArrayCopy;
import org.jdom2.output.EscapeStrategy;
import org.jdom2.output.Format;

public abstract class AbstractFormattedWalker implements Walker {
    /* access modifiers changed from: private */
    public static final CDATA CDATATOKEN = new CDATA("");
    private static final Iterator<Content> EMPTYIT = new Iterator<Content>() {
        public boolean hasNext() {
            return false;
        }

        public Content next() {
            throw new NoSuchElementException("Cannot call next() on an empty iterator.");
        }

        public void remove() {
            throw new UnsupportedOperationException("Cannot remove from an empty iterator.");
        }
    };
    private final boolean alltext;
    private final boolean allwhite;
    private final Iterator<? extends Content> content;
    /* access modifiers changed from: private */
    public final String endofline;
    /* access modifiers changed from: private */
    public final EscapeStrategy escape;
    /* access modifiers changed from: private */
    public final FormatStack fstack;
    private boolean hasnext;
    private final MultiText holdingmt;
    /* access modifiers changed from: private */
    public final StringBuilder mtbuffer;
    /* access modifiers changed from: private */
    public Content[] mtdata;
    /* access modifiers changed from: private */
    public boolean mtgottext;
    private int mtpos;
    /* access modifiers changed from: private */
    public boolean mtpostpad;
    /* access modifiers changed from: private */
    public int mtsize;
    private Content[] mtsource;
    private int mtsourcesize;
    /* access modifiers changed from: private */
    public String[] mttext;
    private Boolean mtwasescape;
    private MultiText multitext;
    /* access modifiers changed from: private */
    public final String newlineindent;
    private Content pending = null;
    private MultiText pendingmt;

    /* renamed from: org.jdom2.output.support.AbstractFormattedWalker$2 */
    static /* synthetic */ class C61392 {
        static final /* synthetic */ int[] $SwitchMap$org$jdom2$Content$CType = new int[CType.values().length];
        static final /* synthetic */ int[] $SwitchMap$org$jdom2$output$support$AbstractFormattedWalker$Trim = new int[Trim.values().length];

        /* JADX WARNING: Can't wrap try/catch for region: R(16:0|(2:1|2)|3|(2:5|6)|7|(2:9|10)|11|13|14|15|16|17|18|19|20|(3:21|22|24)) */
        /* JADX WARNING: Can't wrap try/catch for region: R(19:0|1|2|3|(2:5|6)|7|(2:9|10)|11|13|14|15|16|17|18|19|20|21|22|24) */
        /* JADX WARNING: Can't wrap try/catch for region: R(20:0|1|2|3|5|6|7|(2:9|10)|11|13|14|15|16|17|18|19|20|21|22|24) */
        /* JADX WARNING: Failed to process nested try/catch */
        /* JADX WARNING: Missing exception handler attribute for start block: B:15:0x003d */
        /* JADX WARNING: Missing exception handler attribute for start block: B:17:0x0047 */
        /* JADX WARNING: Missing exception handler attribute for start block: B:19:0x0051 */
        /* JADX WARNING: Missing exception handler attribute for start block: B:21:0x005c */
        static {
            /*
                org.jdom2.Content$CType[] r0 = org.jdom2.Content.CType.values()
                int r0 = r0.length
                int[] r0 = new int[r0]
                $SwitchMap$org$jdom2$Content$CType = r0
                r0 = 1
                int[] r1 = $SwitchMap$org$jdom2$Content$CType     // Catch:{ NoSuchFieldError -> 0x0014 }
                org.jdom2.Content$CType r2 = org.jdom2.Content.CType.Text     // Catch:{ NoSuchFieldError -> 0x0014 }
                int r2 = r2.ordinal()     // Catch:{ NoSuchFieldError -> 0x0014 }
                r1[r2] = r0     // Catch:{ NoSuchFieldError -> 0x0014 }
            L_0x0014:
                r1 = 2
                int[] r2 = $SwitchMap$org$jdom2$Content$CType     // Catch:{ NoSuchFieldError -> 0x001f }
                org.jdom2.Content$CType r3 = org.jdom2.Content.CType.CDATA     // Catch:{ NoSuchFieldError -> 0x001f }
                int r3 = r3.ordinal()     // Catch:{ NoSuchFieldError -> 0x001f }
                r2[r3] = r1     // Catch:{ NoSuchFieldError -> 0x001f }
            L_0x001f:
                r2 = 3
                int[] r3 = $SwitchMap$org$jdom2$Content$CType     // Catch:{ NoSuchFieldError -> 0x002a }
                org.jdom2.Content$CType r4 = org.jdom2.Content.CType.EntityRef     // Catch:{ NoSuchFieldError -> 0x002a }
                int r4 = r4.ordinal()     // Catch:{ NoSuchFieldError -> 0x002a }
                r3[r4] = r2     // Catch:{ NoSuchFieldError -> 0x002a }
            L_0x002a:
                org.jdom2.output.support.AbstractFormattedWalker$Trim[] r3 = org.jdom2.output.support.AbstractFormattedWalker.Trim.values()
                int r3 = r3.length
                int[] r3 = new int[r3]
                $SwitchMap$org$jdom2$output$support$AbstractFormattedWalker$Trim = r3
                int[] r3 = $SwitchMap$org$jdom2$output$support$AbstractFormattedWalker$Trim     // Catch:{ NoSuchFieldError -> 0x003d }
                org.jdom2.output.support.AbstractFormattedWalker$Trim r4 = org.jdom2.output.support.AbstractFormattedWalker.Trim.NONE     // Catch:{ NoSuchFieldError -> 0x003d }
                int r4 = r4.ordinal()     // Catch:{ NoSuchFieldError -> 0x003d }
                r3[r4] = r0     // Catch:{ NoSuchFieldError -> 0x003d }
            L_0x003d:
                int[] r0 = $SwitchMap$org$jdom2$output$support$AbstractFormattedWalker$Trim     // Catch:{ NoSuchFieldError -> 0x0047 }
                org.jdom2.output.support.AbstractFormattedWalker$Trim r3 = org.jdom2.output.support.AbstractFormattedWalker.Trim.BOTH     // Catch:{ NoSuchFieldError -> 0x0047 }
                int r3 = r3.ordinal()     // Catch:{ NoSuchFieldError -> 0x0047 }
                r0[r3] = r1     // Catch:{ NoSuchFieldError -> 0x0047 }
            L_0x0047:
                int[] r0 = $SwitchMap$org$jdom2$output$support$AbstractFormattedWalker$Trim     // Catch:{ NoSuchFieldError -> 0x0051 }
                org.jdom2.output.support.AbstractFormattedWalker$Trim r1 = org.jdom2.output.support.AbstractFormattedWalker.Trim.LEFT     // Catch:{ NoSuchFieldError -> 0x0051 }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x0051 }
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x0051 }
            L_0x0051:
                int[] r0 = $SwitchMap$org$jdom2$output$support$AbstractFormattedWalker$Trim     // Catch:{ NoSuchFieldError -> 0x005c }
                org.jdom2.output.support.AbstractFormattedWalker$Trim r1 = org.jdom2.output.support.AbstractFormattedWalker.Trim.RIGHT     // Catch:{ NoSuchFieldError -> 0x005c }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x005c }
                r2 = 4
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x005c }
            L_0x005c:
                int[] r0 = $SwitchMap$org$jdom2$output$support$AbstractFormattedWalker$Trim     // Catch:{ NoSuchFieldError -> 0x0067 }
                org.jdom2.output.support.AbstractFormattedWalker$Trim r1 = org.jdom2.output.support.AbstractFormattedWalker.Trim.COMPACT     // Catch:{ NoSuchFieldError -> 0x0067 }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x0067 }
                r2 = 5
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x0067 }
            L_0x0067:
                return
            */
            throw new UnsupportedOperationException("Method not decompiled: org.jdom2.output.support.AbstractFormattedWalker.C61392.<clinit>():void");
        }
    }

    protected final class MultiText {
        private MultiText() {
        }

        private void ensurespace() {
            if (AbstractFormattedWalker.this.mtsize >= AbstractFormattedWalker.this.mtdata.length) {
                AbstractFormattedWalker abstractFormattedWalker = AbstractFormattedWalker.this;
                abstractFormattedWalker.mtdata = (Content[]) ArrayCopy.copyOf((E[]) abstractFormattedWalker.mtdata, AbstractFormattedWalker.this.mtsize + 1 + (AbstractFormattedWalker.this.mtsize / 2));
                AbstractFormattedWalker abstractFormattedWalker2 = AbstractFormattedWalker.this;
                abstractFormattedWalker2.mttext = (String[]) ArrayCopy.copyOf((E[]) abstractFormattedWalker2.mttext, AbstractFormattedWalker.this.mtdata.length);
            }
        }

        private void closeText() {
            if (AbstractFormattedWalker.this.mtbuffer.length() != 0) {
                ensurespace();
                AbstractFormattedWalker.this.mtdata[AbstractFormattedWalker.this.mtsize] = null;
                AbstractFormattedWalker.this.mttext[AbstractFormattedWalker.this.mtsize = AbstractFormattedWalker.this.mtsize + 1] = AbstractFormattedWalker.this.mtbuffer.toString();
                AbstractFormattedWalker.this.mtbuffer.setLength(0);
            }
        }

        public void appendText(Trim trim, String str) {
            if (str.length() != 0) {
                int i = C61392.$SwitchMap$org$jdom2$output$support$AbstractFormattedWalker$Trim[trim.ordinal()];
                if (i != 1) {
                    str = i != 2 ? i != 3 ? i != 4 ? i != 5 ? null : Format.compact(str) : Format.trimRight(str) : Format.trimLeft(str) : Format.trimBoth(str);
                }
                if (str != null) {
                    AbstractFormattedWalker.this.mtbuffer.append(escapeText(str));
                    AbstractFormattedWalker.this.mtgottext = true;
                }
            }
        }

        private String escapeText(String str) {
            return (AbstractFormattedWalker.this.escape == null || !AbstractFormattedWalker.this.fstack.getEscapeOutput()) ? str : Format.escapeText(AbstractFormattedWalker.this.escape, AbstractFormattedWalker.this.endofline, str);
        }

        private String escapeCDATA(String str) {
            if (AbstractFormattedWalker.this.escape == null) {
            }
            return str;
        }

        public void appendCDATA(Trim trim, String str) {
            closeText();
            int i = C61392.$SwitchMap$org$jdom2$output$support$AbstractFormattedWalker$Trim[trim.ordinal()];
            if (i != 1) {
                str = i != 2 ? i != 3 ? i != 4 ? i != 5 ? null : Format.compact(str) : Format.trimRight(str) : Format.trimLeft(str) : Format.trimBoth(str);
            }
            String escapeCDATA = escapeCDATA(str);
            ensurespace();
            AbstractFormattedWalker.this.mtdata[AbstractFormattedWalker.this.mtsize] = AbstractFormattedWalker.CDATATOKEN;
            AbstractFormattedWalker.this.mttext[AbstractFormattedWalker.this.mtsize = AbstractFormattedWalker.this.mtsize + 1] = escapeCDATA;
            AbstractFormattedWalker.this.mtgottext = true;
        }

        /* access modifiers changed from: private */
        public void forceAppend(String str) {
            AbstractFormattedWalker.this.mtgottext = true;
            AbstractFormattedWalker.this.mtbuffer.append(str);
        }

        public void appendRaw(Content content) {
            closeText();
            ensurespace();
            AbstractFormattedWalker.this.mttext[AbstractFormattedWalker.this.mtsize] = null;
            AbstractFormattedWalker.this.mtdata[AbstractFormattedWalker.this.mtsize = AbstractFormattedWalker.this.mtsize + 1] = content;
            AbstractFormattedWalker.this.mtbuffer.setLength(0);
        }

        public void done() {
            if (AbstractFormattedWalker.this.mtpostpad && AbstractFormattedWalker.this.newlineindent != null) {
                AbstractFormattedWalker.this.mtbuffer.append(AbstractFormattedWalker.this.newlineindent);
            }
            if (AbstractFormattedWalker.this.mtgottext) {
                closeText();
            }
            AbstractFormattedWalker.this.mtbuffer.setLength(0);
        }
    }

    protected enum Trim {
        LEFT,
        RIGHT,
        BOTH,
        COMPACT,
        NONE
    }

    /* access modifiers changed from: protected */
    public abstract void analyzeMultiText(MultiText multiText, int i, int i2);

    public AbstractFormattedWalker(List<? extends Content> list, FormatStack formatStack, boolean z) {
        boolean z2;
        boolean z3;
        boolean z4 = true;
        this.hasnext = true;
        this.multitext = null;
        this.pendingmt = null;
        this.holdingmt = new MultiText();
        this.mtbuffer = new StringBuilder();
        this.mtgottext = false;
        this.mtsize = 0;
        this.mtsourcesize = 0;
        this.mtsource = new Content[8];
        this.mtdata = new Content[8];
        this.mttext = new String[8];
        this.mtpos = -1;
        this.fstack = formatStack;
        this.content = list.isEmpty() ? EMPTYIT : list.iterator();
        this.escape = z ? formatStack.getEscapeStrategy() : null;
        this.newlineindent = formatStack.getPadBetween();
        this.endofline = formatStack.getLevelEOL();
        if (!this.content.hasNext()) {
            this.alltext = true;
            this.allwhite = true;
        } else {
            this.pending = (Content) this.content.next();
            if (isTextLike(this.pending)) {
                this.pendingmt = buildMultiText(true);
                analyzeMultiText(this.pendingmt, 0, this.mtsourcesize);
                this.pendingmt.done();
                if (this.pending == null) {
                    z2 = this.mtsize == 0;
                    z3 = true;
                } else {
                    z3 = false;
                    z2 = false;
                }
                if (this.mtsize == 0) {
                    this.pendingmt = null;
                }
            } else {
                z3 = false;
                z2 = false;
            }
            this.alltext = z3;
            this.allwhite = z2;
        }
        if (this.pendingmt == null && this.pending == null) {
            z4 = false;
        }
        this.hasnext = z4;
    }

    public final Content next() {
        if (this.hasnext) {
            Content content2 = null;
            boolean z = true;
            if (this.multitext != null && this.mtpos + 1 >= this.mtsize) {
                this.multitext = null;
                resetMultiText();
            }
            if (this.pendingmt != null) {
                if (!(this.mtwasescape == null || this.fstack.getEscapeOutput() == this.mtwasescape.booleanValue())) {
                    this.mtsize = 0;
                    this.mtwasescape = Boolean.valueOf(this.fstack.getEscapeOutput());
                    analyzeMultiText(this.pendingmt, 0, this.mtsourcesize);
                    this.pendingmt.done();
                }
                this.multitext = this.pendingmt;
                this.pendingmt = null;
            }
            if (this.multitext != null) {
                this.mtpos++;
                String[] strArr = this.mttext;
                int i = this.mtpos;
                if (strArr[i] == null) {
                    content2 = this.mtdata[i];
                }
                if (this.mtpos + 1 >= this.mtsize && this.pending == null) {
                    z = false;
                }
                this.hasnext = z;
                return content2;
            }
            Content content3 = this.pending;
            this.pending = this.content.hasNext() ? (Content) this.content.next() : null;
            Content content4 = this.pending;
            if (content4 == null) {
                this.hasnext = false;
            } else if (isTextLike(content4)) {
                this.pendingmt = buildMultiText(false);
                analyzeMultiText(this.pendingmt, 0, this.mtsourcesize);
                this.pendingmt.done();
                if (this.mtsize > 0) {
                    this.hasnext = true;
                } else if (this.pending == null || this.newlineindent == null) {
                    this.pendingmt = null;
                    if (this.pending == null) {
                        z = false;
                    }
                    this.hasnext = z;
                } else {
                    resetMultiText();
                    this.pendingmt = this.holdingmt;
                    this.pendingmt.forceAppend(this.newlineindent);
                    this.pendingmt.done();
                    this.hasnext = true;
                }
            } else {
                if (this.newlineindent != null) {
                    resetMultiText();
                    this.pendingmt = this.holdingmt;
                    this.pendingmt.forceAppend(this.newlineindent);
                    this.pendingmt.done();
                }
                this.hasnext = true;
            }
            return content3;
        }
        throw new NoSuchElementException("Cannot walk off end of Content");
    }

    private void resetMultiText() {
        this.mtsourcesize = 0;
        this.mtpos = -1;
        this.mtsize = 0;
        this.mtgottext = false;
        this.mtpostpad = false;
        this.mtwasescape = null;
        this.mtbuffer.setLength(0);
    }

    /* access modifiers changed from: protected */
    public final Content get(int i) {
        return this.mtsource[i];
    }

    public final boolean isAllText() {
        return this.alltext;
    }

    public final boolean hasNext() {
        return this.hasnext;
    }

    private final MultiText buildMultiText(boolean z) {
        Content content2;
        if (!z) {
            String str = this.newlineindent;
            if (str != null) {
                this.mtbuffer.append(str);
            }
        }
        boolean z2 = false;
        this.mtsourcesize = 0;
        do {
            int i = this.mtsourcesize;
            Content[] contentArr = this.mtsource;
            if (i >= contentArr.length) {
                this.mtsource = (Content[]) ArrayCopy.copyOf((E[]) contentArr, contentArr.length * 2);
            }
            Content[] contentArr2 = this.mtsource;
            int i2 = this.mtsourcesize;
            this.mtsourcesize = i2 + 1;
            contentArr2[i2] = this.pending;
            this.pending = this.content.hasNext() ? (Content) this.content.next() : null;
            content2 = this.pending;
            if (content2 == null) {
                break;
            }
        } while (isTextLike(content2));
        if (this.pending != null) {
            z2 = true;
        }
        this.mtpostpad = z2;
        this.mtwasescape = Boolean.valueOf(this.fstack.getEscapeOutput());
        return this.holdingmt;
    }

    public final String text() {
        if (this.multitext != null) {
            int i = this.mtpos;
            if (i < this.mtsize) {
                return this.mttext[i];
            }
        }
        return null;
    }

    public final boolean isCDATA() {
        boolean z = false;
        if (this.multitext != null) {
            int i = this.mtpos;
            if (i < this.mtsize) {
                if (this.mttext[i] == null) {
                    return false;
                }
                if (this.mtdata[i] == CDATATOKEN) {
                    z = true;
                }
            }
        }
        return z;
    }

    public final boolean isAllWhitespace() {
        return this.allwhite;
    }

    private final boolean isTextLike(Content content2) {
        int i = C61392.$SwitchMap$org$jdom2$Content$CType[content2.getCType().ordinal()];
        return i == 1 || i == 2 || i == 3;
    }
}
