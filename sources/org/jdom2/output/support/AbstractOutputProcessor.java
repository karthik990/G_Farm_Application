package org.jdom2.output.support;

import java.util.List;
import org.jdom2.Content;
import org.jdom2.output.Format.TextMode;

public abstract class AbstractOutputProcessor {

    /* renamed from: org.jdom2.output.support.AbstractOutputProcessor$1 */
    static /* synthetic */ class C61401 {
        static final /* synthetic */ int[] $SwitchMap$org$jdom2$output$Format$TextMode = new int[TextMode.values().length];

        /* JADX WARNING: Can't wrap try/catch for region: R(10:0|1|2|3|4|5|6|7|8|10) */
        /* JADX WARNING: Can't wrap try/catch for region: R(8:0|1|2|3|4|5|6|(3:7|8|10)) */
        /* JADX WARNING: Failed to process nested try/catch */
        /* JADX WARNING: Missing exception handler attribute for start block: B:3:0x0014 */
        /* JADX WARNING: Missing exception handler attribute for start block: B:5:0x001f */
        /* JADX WARNING: Missing exception handler attribute for start block: B:7:0x002a */
        static {
            /*
                org.jdom2.output.Format$TextMode[] r0 = org.jdom2.output.Format.TextMode.values()
                int r0 = r0.length
                int[] r0 = new int[r0]
                $SwitchMap$org$jdom2$output$Format$TextMode = r0
                int[] r0 = $SwitchMap$org$jdom2$output$Format$TextMode     // Catch:{ NoSuchFieldError -> 0x0014 }
                org.jdom2.output.Format$TextMode r1 = org.jdom2.output.Format.TextMode.PRESERVE     // Catch:{ NoSuchFieldError -> 0x0014 }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x0014 }
                r2 = 1
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x0014 }
            L_0x0014:
                int[] r0 = $SwitchMap$org$jdom2$output$Format$TextMode     // Catch:{ NoSuchFieldError -> 0x001f }
                org.jdom2.output.Format$TextMode r1 = org.jdom2.output.Format.TextMode.NORMALIZE     // Catch:{ NoSuchFieldError -> 0x001f }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x001f }
                r2 = 2
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x001f }
            L_0x001f:
                int[] r0 = $SwitchMap$org$jdom2$output$Format$TextMode     // Catch:{ NoSuchFieldError -> 0x002a }
                org.jdom2.output.Format$TextMode r1 = org.jdom2.output.Format.TextMode.TRIM     // Catch:{ NoSuchFieldError -> 0x002a }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x002a }
                r2 = 3
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x002a }
            L_0x002a:
                int[] r0 = $SwitchMap$org$jdom2$output$Format$TextMode     // Catch:{ NoSuchFieldError -> 0x0035 }
                org.jdom2.output.Format$TextMode r1 = org.jdom2.output.Format.TextMode.TRIM_FULL_WHITE     // Catch:{ NoSuchFieldError -> 0x0035 }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x0035 }
                r2 = 4
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x0035 }
            L_0x0035:
                return
            */
            throw new UnsupportedOperationException("Method not decompiled: org.jdom2.output.support.AbstractOutputProcessor.C61401.<clinit>():void");
        }
    }

    /* access modifiers changed from: protected */
    public Walker buildWalker(FormatStack formatStack, List<? extends Content> list, boolean z) {
        int i = C61401.$SwitchMap$org$jdom2$output$Format$TextMode[formatStack.getTextMode().ordinal()];
        if (i == 1) {
            return new WalkerPRESERVE(list);
        }
        if (i == 2) {
            return new WalkerNORMALIZE(list, formatStack, z);
        }
        if (i != 3) {
            return i != 4 ? new WalkerPRESERVE(list) : new WalkerTRIM_FULL_WHITE(list, formatStack, z);
        }
        return new WalkerTRIM(list, formatStack, z);
    }
}
