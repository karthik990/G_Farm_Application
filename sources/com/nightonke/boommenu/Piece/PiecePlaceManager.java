package com.nightonke.boommenu.Piece;

import android.content.Context;
import android.graphics.Point;
import android.graphics.PointF;
import android.graphics.RectF;
import com.nightonke.boommenu.BoomButtons.BoomButtonBuilder;
import com.nightonke.boommenu.BoomMenuButton;
import com.nightonke.boommenu.ButtonEnum;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;

public class PiecePlaceManager {

    /* renamed from: com.nightonke.boommenu.Piece.PiecePlaceManager$2 */
    static /* synthetic */ class C45132 {
        static final /* synthetic */ int[] $SwitchMap$com$nightonke$boommenu$ButtonEnum = new int[ButtonEnum.values().length];

        /* JADX WARNING: Can't wrap try/catch for region: R(80:0|(2:1|2)|3|(2:5|6)|7|(2:9|10)|11|(2:13|14)|15|17|18|19|20|21|22|23|24|25|26|27|28|29|30|31|32|33|34|35|36|37|38|39|40|41|42|43|44|45|46|47|48|49|50|51|52|53|54|55|56|57|58|59|60|61|62|63|64|65|66|67|68|69|70|71|72|73|74|75|76|77|78|79|80|81|82|83|84|85|86|(3:87|88|90)) */
        /* JADX WARNING: Can't wrap try/catch for region: R(81:0|(2:1|2)|3|(2:5|6)|7|(2:9|10)|11|13|14|15|17|18|19|20|21|22|23|24|25|26|27|28|29|30|31|32|33|34|35|36|37|38|39|40|41|42|43|44|45|46|47|48|49|50|51|52|53|54|55|56|57|58|59|60|61|62|63|64|65|66|67|68|69|70|71|72|73|74|75|76|77|78|79|80|81|82|83|84|85|86|(3:87|88|90)) */
        /* JADX WARNING: Can't wrap try/catch for region: R(82:0|(2:1|2)|3|(2:5|6)|7|9|10|11|13|14|15|17|18|19|20|21|22|23|24|25|26|27|28|29|30|31|32|33|34|35|36|37|38|39|40|41|42|43|44|45|46|47|48|49|50|51|52|53|54|55|56|57|58|59|60|61|62|63|64|65|66|67|68|69|70|71|72|73|74|75|76|77|78|79|80|81|82|83|84|85|86|(3:87|88|90)) */
        /* JADX WARNING: Can't wrap try/catch for region: R(84:0|(2:1|2)|3|(2:5|6)|7|9|10|11|13|14|15|17|18|19|20|21|22|23|24|25|26|27|28|29|30|31|32|33|34|35|36|37|38|39|40|41|42|43|44|45|46|47|48|49|50|51|52|53|54|55|56|57|58|59|60|61|62|63|64|65|66|67|68|69|70|71|72|73|74|75|76|77|78|79|80|81|82|83|84|85|86|87|88|90) */
        /* JADX WARNING: Can't wrap try/catch for region: R(85:0|(2:1|2)|3|5|6|7|9|10|11|13|14|15|17|18|19|20|21|22|23|24|25|26|27|28|29|30|31|32|33|34|35|36|37|38|39|40|41|42|43|44|45|46|47|48|49|50|51|52|53|54|55|56|57|58|59|60|61|62|63|64|65|66|67|68|69|70|71|72|73|74|75|76|77|78|79|80|81|82|83|84|85|86|87|88|90) */
        /* JADX WARNING: Can't wrap try/catch for region: R(86:0|1|2|3|5|6|7|9|10|11|13|14|15|17|18|19|20|21|22|23|24|25|26|27|28|29|30|31|32|33|34|35|36|37|38|39|40|41|42|43|44|45|46|47|48|49|50|51|52|53|54|55|56|57|58|59|60|61|62|63|64|65|66|67|68|69|70|71|72|73|74|75|76|77|78|79|80|81|82|83|84|85|86|87|88|90) */
        /* JADX WARNING: Failed to process nested try/catch */
        /* JADX WARNING: Missing exception handler attribute for start block: B:19:0x0048 */
        /* JADX WARNING: Missing exception handler attribute for start block: B:21:0x0052 */
        /* JADX WARNING: Missing exception handler attribute for start block: B:23:0x005c */
        /* JADX WARNING: Missing exception handler attribute for start block: B:25:0x0066 */
        /* JADX WARNING: Missing exception handler attribute for start block: B:27:0x0071 */
        /* JADX WARNING: Missing exception handler attribute for start block: B:29:0x007c */
        /* JADX WARNING: Missing exception handler attribute for start block: B:31:0x0087 */
        /* JADX WARNING: Missing exception handler attribute for start block: B:33:0x0093 */
        /* JADX WARNING: Missing exception handler attribute for start block: B:35:0x009f */
        /* JADX WARNING: Missing exception handler attribute for start block: B:37:0x00ab */
        /* JADX WARNING: Missing exception handler attribute for start block: B:39:0x00b7 */
        /* JADX WARNING: Missing exception handler attribute for start block: B:41:0x00c3 */
        /* JADX WARNING: Missing exception handler attribute for start block: B:43:0x00cf */
        /* JADX WARNING: Missing exception handler attribute for start block: B:45:0x00db */
        /* JADX WARNING: Missing exception handler attribute for start block: B:47:0x00e7 */
        /* JADX WARNING: Missing exception handler attribute for start block: B:49:0x00f3 */
        /* JADX WARNING: Missing exception handler attribute for start block: B:51:0x00ff */
        /* JADX WARNING: Missing exception handler attribute for start block: B:53:0x010b */
        /* JADX WARNING: Missing exception handler attribute for start block: B:55:0x0117 */
        /* JADX WARNING: Missing exception handler attribute for start block: B:57:0x0123 */
        /* JADX WARNING: Missing exception handler attribute for start block: B:59:0x012f */
        /* JADX WARNING: Missing exception handler attribute for start block: B:61:0x013b */
        /* JADX WARNING: Missing exception handler attribute for start block: B:63:0x0147 */
        /* JADX WARNING: Missing exception handler attribute for start block: B:65:0x0153 */
        /* JADX WARNING: Missing exception handler attribute for start block: B:67:0x015f */
        /* JADX WARNING: Missing exception handler attribute for start block: B:69:0x016b */
        /* JADX WARNING: Missing exception handler attribute for start block: B:71:0x0177 */
        /* JADX WARNING: Missing exception handler attribute for start block: B:73:0x0183 */
        /* JADX WARNING: Missing exception handler attribute for start block: B:75:0x018f */
        /* JADX WARNING: Missing exception handler attribute for start block: B:77:0x019b */
        /* JADX WARNING: Missing exception handler attribute for start block: B:79:0x01a7 */
        /* JADX WARNING: Missing exception handler attribute for start block: B:81:0x01b3 */
        /* JADX WARNING: Missing exception handler attribute for start block: B:83:0x01bf */
        /* JADX WARNING: Missing exception handler attribute for start block: B:85:0x01cb */
        /* JADX WARNING: Missing exception handler attribute for start block: B:87:0x01d7 */
        static {
            /*
                com.nightonke.boommenu.ButtonEnum[] r0 = com.nightonke.boommenu.ButtonEnum.values()
                int r0 = r0.length
                int[] r0 = new int[r0]
                $SwitchMap$com$nightonke$boommenu$ButtonEnum = r0
                r0 = 1
                int[] r1 = $SwitchMap$com$nightonke$boommenu$ButtonEnum     // Catch:{ NoSuchFieldError -> 0x0014 }
                com.nightonke.boommenu.ButtonEnum r2 = com.nightonke.boommenu.ButtonEnum.SimpleCircle     // Catch:{ NoSuchFieldError -> 0x0014 }
                int r2 = r2.ordinal()     // Catch:{ NoSuchFieldError -> 0x0014 }
                r1[r2] = r0     // Catch:{ NoSuchFieldError -> 0x0014 }
            L_0x0014:
                r1 = 2
                int[] r2 = $SwitchMap$com$nightonke$boommenu$ButtonEnum     // Catch:{ NoSuchFieldError -> 0x001f }
                com.nightonke.boommenu.ButtonEnum r3 = com.nightonke.boommenu.ButtonEnum.TextInsideCircle     // Catch:{ NoSuchFieldError -> 0x001f }
                int r3 = r3.ordinal()     // Catch:{ NoSuchFieldError -> 0x001f }
                r2[r3] = r1     // Catch:{ NoSuchFieldError -> 0x001f }
            L_0x001f:
                r2 = 3
                int[] r3 = $SwitchMap$com$nightonke$boommenu$ButtonEnum     // Catch:{ NoSuchFieldError -> 0x002a }
                com.nightonke.boommenu.ButtonEnum r4 = com.nightonke.boommenu.ButtonEnum.TextOutsideCircle     // Catch:{ NoSuchFieldError -> 0x002a }
                int r4 = r4.ordinal()     // Catch:{ NoSuchFieldError -> 0x002a }
                r3[r4] = r2     // Catch:{ NoSuchFieldError -> 0x002a }
            L_0x002a:
                r3 = 4
                int[] r4 = $SwitchMap$com$nightonke$boommenu$ButtonEnum     // Catch:{ NoSuchFieldError -> 0x0035 }
                com.nightonke.boommenu.ButtonEnum r5 = com.nightonke.boommenu.ButtonEnum.Ham     // Catch:{ NoSuchFieldError -> 0x0035 }
                int r5 = r5.ordinal()     // Catch:{ NoSuchFieldError -> 0x0035 }
                r4[r5] = r3     // Catch:{ NoSuchFieldError -> 0x0035 }
            L_0x0035:
                com.nightonke.boommenu.Piece.PiecePlaceEnum[] r4 = com.nightonke.boommenu.Piece.PiecePlaceEnum.values()
                int r4 = r4.length
                int[] r4 = new int[r4]
                $SwitchMap$com$nightonke$boommenu$Piece$PiecePlaceEnum = r4
                int[] r4 = $SwitchMap$com$nightonke$boommenu$Piece$PiecePlaceEnum     // Catch:{ NoSuchFieldError -> 0x0048 }
                com.nightonke.boommenu.Piece.PiecePlaceEnum r5 = com.nightonke.boommenu.Piece.PiecePlaceEnum.DOT_4_2     // Catch:{ NoSuchFieldError -> 0x0048 }
                int r5 = r5.ordinal()     // Catch:{ NoSuchFieldError -> 0x0048 }
                r4[r5] = r0     // Catch:{ NoSuchFieldError -> 0x0048 }
            L_0x0048:
                int[] r0 = $SwitchMap$com$nightonke$boommenu$Piece$PiecePlaceEnum     // Catch:{ NoSuchFieldError -> 0x0052 }
                com.nightonke.boommenu.Piece.PiecePlaceEnum r4 = com.nightonke.boommenu.Piece.PiecePlaceEnum.DOT_5_4     // Catch:{ NoSuchFieldError -> 0x0052 }
                int r4 = r4.ordinal()     // Catch:{ NoSuchFieldError -> 0x0052 }
                r0[r4] = r1     // Catch:{ NoSuchFieldError -> 0x0052 }
            L_0x0052:
                int[] r0 = $SwitchMap$com$nightonke$boommenu$Piece$PiecePlaceEnum     // Catch:{ NoSuchFieldError -> 0x005c }
                com.nightonke.boommenu.Piece.PiecePlaceEnum r1 = com.nightonke.boommenu.Piece.PiecePlaceEnum.DOT_8_5     // Catch:{ NoSuchFieldError -> 0x005c }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x005c }
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x005c }
            L_0x005c:
                int[] r0 = $SwitchMap$com$nightonke$boommenu$Piece$PiecePlaceEnum     // Catch:{ NoSuchFieldError -> 0x0066 }
                com.nightonke.boommenu.Piece.PiecePlaceEnum r1 = com.nightonke.boommenu.Piece.PiecePlaceEnum.DOT_9_3     // Catch:{ NoSuchFieldError -> 0x0066 }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x0066 }
                r0[r1] = r3     // Catch:{ NoSuchFieldError -> 0x0066 }
            L_0x0066:
                int[] r0 = $SwitchMap$com$nightonke$boommenu$Piece$PiecePlaceEnum     // Catch:{ NoSuchFieldError -> 0x0071 }
                com.nightonke.boommenu.Piece.PiecePlaceEnum r1 = com.nightonke.boommenu.Piece.PiecePlaceEnum.DOT_8_2     // Catch:{ NoSuchFieldError -> 0x0071 }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x0071 }
                r2 = 5
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x0071 }
            L_0x0071:
                int[] r0 = $SwitchMap$com$nightonke$boommenu$Piece$PiecePlaceEnum     // Catch:{ NoSuchFieldError -> 0x007c }
                com.nightonke.boommenu.Piece.PiecePlaceEnum r1 = com.nightonke.boommenu.Piece.PiecePlaceEnum.DOT_1     // Catch:{ NoSuchFieldError -> 0x007c }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x007c }
                r2 = 6
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x007c }
            L_0x007c:
                int[] r0 = $SwitchMap$com$nightonke$boommenu$Piece$PiecePlaceEnum     // Catch:{ NoSuchFieldError -> 0x0087 }
                com.nightonke.boommenu.Piece.PiecePlaceEnum r1 = com.nightonke.boommenu.Piece.PiecePlaceEnum.DOT_2_1     // Catch:{ NoSuchFieldError -> 0x0087 }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x0087 }
                r2 = 7
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x0087 }
            L_0x0087:
                int[] r0 = $SwitchMap$com$nightonke$boommenu$Piece$PiecePlaceEnum     // Catch:{ NoSuchFieldError -> 0x0093 }
                com.nightonke.boommenu.Piece.PiecePlaceEnum r1 = com.nightonke.boommenu.Piece.PiecePlaceEnum.DOT_2_2     // Catch:{ NoSuchFieldError -> 0x0093 }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x0093 }
                r2 = 8
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x0093 }
            L_0x0093:
                int[] r0 = $SwitchMap$com$nightonke$boommenu$Piece$PiecePlaceEnum     // Catch:{ NoSuchFieldError -> 0x009f }
                com.nightonke.boommenu.Piece.PiecePlaceEnum r1 = com.nightonke.boommenu.Piece.PiecePlaceEnum.DOT_3_1     // Catch:{ NoSuchFieldError -> 0x009f }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x009f }
                r2 = 9
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x009f }
            L_0x009f:
                int[] r0 = $SwitchMap$com$nightonke$boommenu$Piece$PiecePlaceEnum     // Catch:{ NoSuchFieldError -> 0x00ab }
                com.nightonke.boommenu.Piece.PiecePlaceEnum r1 = com.nightonke.boommenu.Piece.PiecePlaceEnum.DOT_3_2     // Catch:{ NoSuchFieldError -> 0x00ab }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x00ab }
                r2 = 10
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x00ab }
            L_0x00ab:
                int[] r0 = $SwitchMap$com$nightonke$boommenu$Piece$PiecePlaceEnum     // Catch:{ NoSuchFieldError -> 0x00b7 }
                com.nightonke.boommenu.Piece.PiecePlaceEnum r1 = com.nightonke.boommenu.Piece.PiecePlaceEnum.DOT_3_3     // Catch:{ NoSuchFieldError -> 0x00b7 }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x00b7 }
                r2 = 11
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x00b7 }
            L_0x00b7:
                int[] r0 = $SwitchMap$com$nightonke$boommenu$Piece$PiecePlaceEnum     // Catch:{ NoSuchFieldError -> 0x00c3 }
                com.nightonke.boommenu.Piece.PiecePlaceEnum r1 = com.nightonke.boommenu.Piece.PiecePlaceEnum.DOT_3_4     // Catch:{ NoSuchFieldError -> 0x00c3 }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x00c3 }
                r2 = 12
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x00c3 }
            L_0x00c3:
                int[] r0 = $SwitchMap$com$nightonke$boommenu$Piece$PiecePlaceEnum     // Catch:{ NoSuchFieldError -> 0x00cf }
                com.nightonke.boommenu.Piece.PiecePlaceEnum r1 = com.nightonke.boommenu.Piece.PiecePlaceEnum.DOT_4_1     // Catch:{ NoSuchFieldError -> 0x00cf }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x00cf }
                r2 = 13
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x00cf }
            L_0x00cf:
                int[] r0 = $SwitchMap$com$nightonke$boommenu$Piece$PiecePlaceEnum     // Catch:{ NoSuchFieldError -> 0x00db }
                com.nightonke.boommenu.Piece.PiecePlaceEnum r1 = com.nightonke.boommenu.Piece.PiecePlaceEnum.DOT_5_1     // Catch:{ NoSuchFieldError -> 0x00db }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x00db }
                r2 = 14
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x00db }
            L_0x00db:
                int[] r0 = $SwitchMap$com$nightonke$boommenu$Piece$PiecePlaceEnum     // Catch:{ NoSuchFieldError -> 0x00e7 }
                com.nightonke.boommenu.Piece.PiecePlaceEnum r1 = com.nightonke.boommenu.Piece.PiecePlaceEnum.DOT_5_2     // Catch:{ NoSuchFieldError -> 0x00e7 }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x00e7 }
                r2 = 15
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x00e7 }
            L_0x00e7:
                int[] r0 = $SwitchMap$com$nightonke$boommenu$Piece$PiecePlaceEnum     // Catch:{ NoSuchFieldError -> 0x00f3 }
                com.nightonke.boommenu.Piece.PiecePlaceEnum r1 = com.nightonke.boommenu.Piece.PiecePlaceEnum.DOT_5_3     // Catch:{ NoSuchFieldError -> 0x00f3 }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x00f3 }
                r2 = 16
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x00f3 }
            L_0x00f3:
                int[] r0 = $SwitchMap$com$nightonke$boommenu$Piece$PiecePlaceEnum     // Catch:{ NoSuchFieldError -> 0x00ff }
                com.nightonke.boommenu.Piece.PiecePlaceEnum r1 = com.nightonke.boommenu.Piece.PiecePlaceEnum.DOT_6_1     // Catch:{ NoSuchFieldError -> 0x00ff }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x00ff }
                r2 = 17
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x00ff }
            L_0x00ff:
                int[] r0 = $SwitchMap$com$nightonke$boommenu$Piece$PiecePlaceEnum     // Catch:{ NoSuchFieldError -> 0x010b }
                com.nightonke.boommenu.Piece.PiecePlaceEnum r1 = com.nightonke.boommenu.Piece.PiecePlaceEnum.DOT_6_2     // Catch:{ NoSuchFieldError -> 0x010b }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x010b }
                r2 = 18
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x010b }
            L_0x010b:
                int[] r0 = $SwitchMap$com$nightonke$boommenu$Piece$PiecePlaceEnum     // Catch:{ NoSuchFieldError -> 0x0117 }
                com.nightonke.boommenu.Piece.PiecePlaceEnum r1 = com.nightonke.boommenu.Piece.PiecePlaceEnum.DOT_6_3     // Catch:{ NoSuchFieldError -> 0x0117 }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x0117 }
                r2 = 19
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x0117 }
            L_0x0117:
                int[] r0 = $SwitchMap$com$nightonke$boommenu$Piece$PiecePlaceEnum     // Catch:{ NoSuchFieldError -> 0x0123 }
                com.nightonke.boommenu.Piece.PiecePlaceEnum r1 = com.nightonke.boommenu.Piece.PiecePlaceEnum.DOT_6_4     // Catch:{ NoSuchFieldError -> 0x0123 }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x0123 }
                r2 = 20
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x0123 }
            L_0x0123:
                int[] r0 = $SwitchMap$com$nightonke$boommenu$Piece$PiecePlaceEnum     // Catch:{ NoSuchFieldError -> 0x012f }
                com.nightonke.boommenu.Piece.PiecePlaceEnum r1 = com.nightonke.boommenu.Piece.PiecePlaceEnum.DOT_6_5     // Catch:{ NoSuchFieldError -> 0x012f }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x012f }
                r2 = 21
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x012f }
            L_0x012f:
                int[] r0 = $SwitchMap$com$nightonke$boommenu$Piece$PiecePlaceEnum     // Catch:{ NoSuchFieldError -> 0x013b }
                com.nightonke.boommenu.Piece.PiecePlaceEnum r1 = com.nightonke.boommenu.Piece.PiecePlaceEnum.DOT_6_6     // Catch:{ NoSuchFieldError -> 0x013b }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x013b }
                r2 = 22
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x013b }
            L_0x013b:
                int[] r0 = $SwitchMap$com$nightonke$boommenu$Piece$PiecePlaceEnum     // Catch:{ NoSuchFieldError -> 0x0147 }
                com.nightonke.boommenu.Piece.PiecePlaceEnum r1 = com.nightonke.boommenu.Piece.PiecePlaceEnum.DOT_7_1     // Catch:{ NoSuchFieldError -> 0x0147 }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x0147 }
                r2 = 23
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x0147 }
            L_0x0147:
                int[] r0 = $SwitchMap$com$nightonke$boommenu$Piece$PiecePlaceEnum     // Catch:{ NoSuchFieldError -> 0x0153 }
                com.nightonke.boommenu.Piece.PiecePlaceEnum r1 = com.nightonke.boommenu.Piece.PiecePlaceEnum.DOT_7_2     // Catch:{ NoSuchFieldError -> 0x0153 }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x0153 }
                r2 = 24
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x0153 }
            L_0x0153:
                int[] r0 = $SwitchMap$com$nightonke$boommenu$Piece$PiecePlaceEnum     // Catch:{ NoSuchFieldError -> 0x015f }
                com.nightonke.boommenu.Piece.PiecePlaceEnum r1 = com.nightonke.boommenu.Piece.PiecePlaceEnum.DOT_7_3     // Catch:{ NoSuchFieldError -> 0x015f }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x015f }
                r2 = 25
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x015f }
            L_0x015f:
                int[] r0 = $SwitchMap$com$nightonke$boommenu$Piece$PiecePlaceEnum     // Catch:{ NoSuchFieldError -> 0x016b }
                com.nightonke.boommenu.Piece.PiecePlaceEnum r1 = com.nightonke.boommenu.Piece.PiecePlaceEnum.DOT_7_4     // Catch:{ NoSuchFieldError -> 0x016b }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x016b }
                r2 = 26
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x016b }
            L_0x016b:
                int[] r0 = $SwitchMap$com$nightonke$boommenu$Piece$PiecePlaceEnum     // Catch:{ NoSuchFieldError -> 0x0177 }
                com.nightonke.boommenu.Piece.PiecePlaceEnum r1 = com.nightonke.boommenu.Piece.PiecePlaceEnum.DOT_7_5     // Catch:{ NoSuchFieldError -> 0x0177 }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x0177 }
                r2 = 27
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x0177 }
            L_0x0177:
                int[] r0 = $SwitchMap$com$nightonke$boommenu$Piece$PiecePlaceEnum     // Catch:{ NoSuchFieldError -> 0x0183 }
                com.nightonke.boommenu.Piece.PiecePlaceEnum r1 = com.nightonke.boommenu.Piece.PiecePlaceEnum.DOT_7_6     // Catch:{ NoSuchFieldError -> 0x0183 }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x0183 }
                r2 = 28
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x0183 }
            L_0x0183:
                int[] r0 = $SwitchMap$com$nightonke$boommenu$Piece$PiecePlaceEnum     // Catch:{ NoSuchFieldError -> 0x018f }
                com.nightonke.boommenu.Piece.PiecePlaceEnum r1 = com.nightonke.boommenu.Piece.PiecePlaceEnum.DOT_8_1     // Catch:{ NoSuchFieldError -> 0x018f }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x018f }
                r2 = 29
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x018f }
            L_0x018f:
                int[] r0 = $SwitchMap$com$nightonke$boommenu$Piece$PiecePlaceEnum     // Catch:{ NoSuchFieldError -> 0x019b }
                com.nightonke.boommenu.Piece.PiecePlaceEnum r1 = com.nightonke.boommenu.Piece.PiecePlaceEnum.DOT_8_3     // Catch:{ NoSuchFieldError -> 0x019b }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x019b }
                r2 = 30
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x019b }
            L_0x019b:
                int[] r0 = $SwitchMap$com$nightonke$boommenu$Piece$PiecePlaceEnum     // Catch:{ NoSuchFieldError -> 0x01a7 }
                com.nightonke.boommenu.Piece.PiecePlaceEnum r1 = com.nightonke.boommenu.Piece.PiecePlaceEnum.DOT_8_4     // Catch:{ NoSuchFieldError -> 0x01a7 }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x01a7 }
                r2 = 31
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x01a7 }
            L_0x01a7:
                int[] r0 = $SwitchMap$com$nightonke$boommenu$Piece$PiecePlaceEnum     // Catch:{ NoSuchFieldError -> 0x01b3 }
                com.nightonke.boommenu.Piece.PiecePlaceEnum r1 = com.nightonke.boommenu.Piece.PiecePlaceEnum.DOT_8_6     // Catch:{ NoSuchFieldError -> 0x01b3 }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x01b3 }
                r2 = 32
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x01b3 }
            L_0x01b3:
                int[] r0 = $SwitchMap$com$nightonke$boommenu$Piece$PiecePlaceEnum     // Catch:{ NoSuchFieldError -> 0x01bf }
                com.nightonke.boommenu.Piece.PiecePlaceEnum r1 = com.nightonke.boommenu.Piece.PiecePlaceEnum.DOT_8_7     // Catch:{ NoSuchFieldError -> 0x01bf }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x01bf }
                r2 = 33
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x01bf }
            L_0x01bf:
                int[] r0 = $SwitchMap$com$nightonke$boommenu$Piece$PiecePlaceEnum     // Catch:{ NoSuchFieldError -> 0x01cb }
                com.nightonke.boommenu.Piece.PiecePlaceEnum r1 = com.nightonke.boommenu.Piece.PiecePlaceEnum.DOT_9_1     // Catch:{ NoSuchFieldError -> 0x01cb }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x01cb }
                r2 = 34
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x01cb }
            L_0x01cb:
                int[] r0 = $SwitchMap$com$nightonke$boommenu$Piece$PiecePlaceEnum     // Catch:{ NoSuchFieldError -> 0x01d7 }
                com.nightonke.boommenu.Piece.PiecePlaceEnum r1 = com.nightonke.boommenu.Piece.PiecePlaceEnum.DOT_9_2     // Catch:{ NoSuchFieldError -> 0x01d7 }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x01d7 }
                r2 = 35
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x01d7 }
            L_0x01d7:
                int[] r0 = $SwitchMap$com$nightonke$boommenu$Piece$PiecePlaceEnum     // Catch:{ NoSuchFieldError -> 0x01e3 }
                com.nightonke.boommenu.Piece.PiecePlaceEnum r1 = com.nightonke.boommenu.Piece.PiecePlaceEnum.Custom     // Catch:{ NoSuchFieldError -> 0x01e3 }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x01e3 }
                r2 = 36
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x01e3 }
            L_0x01e3:
                return
            */
            throw new UnsupportedOperationException("Method not decompiled: com.nightonke.boommenu.Piece.PiecePlaceManager.C45132.<clinit>():void");
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:15:0x00c4  */
    /* JADX WARNING: Removed duplicated region for block: B:17:0x00cc  */
    /* JADX WARNING: Removed duplicated region for block: B:21:0x00ec  */
    /* JADX WARNING: Removed duplicated region for block: B:22:0x0139  */
    /* JADX WARNING: Removed duplicated region for block: B:23:0x0181  */
    /* JADX WARNING: Removed duplicated region for block: B:24:0x01c5  */
    /* JADX WARNING: Removed duplicated region for block: B:25:0x0209  */
    /* JADX WARNING: Removed duplicated region for block: B:26:0x024e  */
    /* JADX WARNING: Removed duplicated region for block: B:27:0x028e  */
    /* JADX WARNING: Removed duplicated region for block: B:28:0x02ce  */
    /* JADX WARNING: Removed duplicated region for block: B:29:0x0307  */
    /* JADX WARNING: Removed duplicated region for block: B:30:0x0340  */
    /* JADX WARNING: Removed duplicated region for block: B:31:0x037b  */
    /* JADX WARNING: Removed duplicated region for block: B:32:0x03b6  */
    /* JADX WARNING: Removed duplicated region for block: B:33:0x03f0  */
    /* JADX WARNING: Removed duplicated region for block: B:34:0x042a  */
    /* JADX WARNING: Removed duplicated region for block: B:35:0x0462  */
    /* JADX WARNING: Removed duplicated region for block: B:36:0x0498  */
    /* JADX WARNING: Removed duplicated region for block: B:37:0x04cb  */
    /* JADX WARNING: Removed duplicated region for block: B:38:0x04fe  */
    /* JADX WARNING: Removed duplicated region for block: B:39:0x052f  */
    /* JADX WARNING: Removed duplicated region for block: B:40:0x0561  */
    /* JADX WARNING: Removed duplicated region for block: B:41:0x058d  */
    /* JADX WARNING: Removed duplicated region for block: B:42:0x05b6  */
    /* JADX WARNING: Removed duplicated region for block: B:43:0x05df  */
    /* JADX WARNING: Removed duplicated region for block: B:44:0x0602  */
    /* JADX WARNING: Removed duplicated region for block: B:45:0x061d  */
    /* JADX WARNING: Removed duplicated region for block: B:46:0x0638  */
    /* JADX WARNING: Removed duplicated region for block: B:47:0x0653  */
    /* JADX WARNING: Removed duplicated region for block: B:48:0x066e  */
    /* JADX WARNING: Removed duplicated region for block: B:49:0x0681  */
    /* JADX WARNING: Removed duplicated region for block: B:50:0x0693  */
    /* JADX WARNING: Removed duplicated region for block: B:51:0x069d  */
    /* JADX WARNING: Removed duplicated region for block: B:52:0x06de  */
    /* JADX WARNING: Removed duplicated region for block: B:53:0x0722  */
    /* JADX WARNING: Removed duplicated region for block: B:54:0x075d  */
    /* JADX WARNING: Removed duplicated region for block: B:55:0x0783  */
    /* JADX WARNING: Removed duplicated region for block: B:59:0x07aa A[LOOP:1: B:57:0x07a4->B:59:0x07aa, LOOP_END] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static java.util.ArrayList<android.graphics.RectF> getDotPositions(com.nightonke.boommenu.BoomMenuButton r25, android.graphics.Point r26) {
        /*
            r0 = r26
            float r1 = r25.getPieceHorizontalMargin()
            r2 = 1056964608(0x3f000000, float:0.5)
            float r3 = r1 * r2
            r4 = 1069547520(0x3fc00000, float:1.5)
            float r5 = r1 * r4
            float r6 = r25.getPieceVerticalMargin()
            float r2 = r2 * r6
            float r4 = r4 * r6
            float r7 = r25.getPieceInclinedMargin()
            float r8 = r25.getDotRadius()
            r9 = 1073741824(0x40000000, float:2.0)
            float r10 = r8 * r9
            r11 = 1077936128(0x40400000, float:3.0)
            float r12 = r8 * r11
            java.util.ArrayList r13 = new java.util.ArrayList
            r13.<init>()
            java.util.ArrayList r14 = new java.util.ArrayList
            r14.<init>()
            float r15 = r3 + r8
            r17 = r12
            double r11 = (double) r15
            r18 = 4613937818241073152(0x4008000000000000, double:3.0)
            double r20 = java.lang.Math.sqrt(r18)
            r22 = 4611686018427387904(0x4000000000000000, double:2.0)
            double r20 = r20 / r22
            java.lang.Double.isNaN(r11)
            double r11 = r11 / r20
            float r11 = (float) r11
            float r12 = r11 / r9
            float r20 = r11 - r12
            int[] r21 = com.nightonke.boommenu.Piece.PiecePlaceManager.C45132.$SwitchMap$com$nightonke$boommenu$Piece$PiecePlaceEnum
            com.nightonke.boommenu.Piece.PiecePlaceEnum r24 = r25.getPiecePlaceEnum()
            int r24 = r24.ordinal()
            r9 = r21[r24]
            r21 = r11
            r11 = 1
            r24 = r12
            r12 = 2
            if (r9 == r11) goto L_0x0090
            if (r9 == r12) goto L_0x0090
            r11 = 3
            if (r9 == r11) goto L_0x0090
            r11 = 4
            if (r9 == r11) goto L_0x0090
            r7 = 5
            if (r9 == r7) goto L_0x0074
            r7 = r15
            r11 = r20
            r12 = r24
            r19 = 1073741824(0x40000000, float:2.0)
            r20 = r13
            r13 = r21
            goto L_0x00a6
        L_0x0074:
            float r7 = r2 + r8
            r9 = r13
            double r12 = (double) r7
            double r18 = java.lang.Math.sqrt(r18)
            double r18 = r18 / r22
            java.lang.Double.isNaN(r12)
            double r12 = r12 / r18
            float r12 = (float) r12
            r13 = 1073741824(0x40000000, float:2.0)
            float r18 = r12 / r13
            float r20 = r12 - r18
            r13 = r12
            r12 = r18
            r11 = r20
            goto L_0x00a2
        L_0x0090:
            r9 = r13
            float r7 = r7 + r10
            double r12 = (double) r7
            double r18 = java.lang.Math.sqrt(r22)
            java.lang.Double.isNaN(r12)
            double r12 = r12 / r18
            float r12 = (float) r12
            r7 = r15
            r11 = r20
            r13 = r21
        L_0x00a2:
            r19 = 1073741824(0x40000000, float:2.0)
            r20 = r9
        L_0x00a6:
            float r9 = r12 * r19
            float r0 = r7 * r19
            r21 = r11
            r16 = 1077936128(0x40400000, float:3.0)
            float r11 = r7 * r16
            float r16 = r13 * r19
            int[] r19 = com.nightonke.boommenu.Piece.PiecePlaceManager.C45132.$SwitchMap$com$nightonke$boommenu$Piece$PiecePlaceEnum
            com.nightonke.boommenu.Piece.PiecePlaceEnum r22 = r25.getPiecePlaceEnum()
            int r22 = r22.ordinal()
            r19 = r19[r22]
            r22 = r7
            r7 = 0
            switch(r19) {
                case 1: goto L_0x0783;
                case 2: goto L_0x075d;
                case 3: goto L_0x0722;
                case 4: goto L_0x06de;
                case 5: goto L_0x069d;
                case 6: goto L_0x0693;
                case 7: goto L_0x0681;
                case 8: goto L_0x066e;
                case 9: goto L_0x0653;
                case 10: goto L_0x0638;
                case 11: goto L_0x061d;
                case 12: goto L_0x0602;
                case 13: goto L_0x05df;
                case 14: goto L_0x05b6;
                case 15: goto L_0x058d;
                case 16: goto L_0x0561;
                case 17: goto L_0x052f;
                case 18: goto L_0x04fe;
                case 19: goto L_0x04cb;
                case 20: goto L_0x0498;
                case 21: goto L_0x0462;
                case 22: goto L_0x042a;
                case 23: goto L_0x03f0;
                case 24: goto L_0x03b6;
                case 25: goto L_0x037b;
                case 26: goto L_0x0340;
                case 27: goto L_0x0307;
                case 28: goto L_0x02ce;
                case 29: goto L_0x028e;
                case 30: goto L_0x024e;
                case 31: goto L_0x0209;
                case 32: goto L_0x01c5;
                case 33: goto L_0x0181;
                case 34: goto L_0x0139;
                case 35: goto L_0x00ec;
                case 36: goto L_0x00cc;
                default: goto L_0x00c4;
            }
        L_0x00c4:
            java.lang.RuntimeException r0 = new java.lang.RuntimeException
            java.lang.String r1 = "Unknown piece-place-enum!"
            r0.<init>(r1)
            throw r0
        L_0x00cc:
            java.util.ArrayList r0 = r25.getCustomPiecePlacePositions()
            java.util.Iterator r0 = r0.iterator()
        L_0x00d4:
            boolean r1 = r0.hasNext()
            if (r1 == 0) goto L_0x07a0
            java.lang.Object r1 = r0.next()
            android.graphics.PointF r1 = (android.graphics.PointF) r1
            float r2 = r1.x
            float r1 = r1.y
            android.graphics.PointF r1 = point(r2, r1)
            r14.add(r1)
            goto L_0x00d4
        L_0x00ec:
            float r1 = -r9
            float r1 = r1 - r16
            android.graphics.PointF r1 = point(r7, r1)
            r14.add(r1)
            float r1 = -r3
            float r1 = r1 - r8
            float r2 = -r12
            float r2 = r2 - r13
            android.graphics.PointF r3 = point(r1, r2)
            r14.add(r3)
            android.graphics.PointF r2 = point(r15, r2)
            r14.add(r2)
            float r2 = -r0
            android.graphics.PointF r2 = point(r2, r7)
            r14.add(r2)
            r2 = 0
            android.graphics.PointF r2 = point(r2, r2)
            r14.add(r2)
            android.graphics.PointF r0 = point(r0, r7)
            r14.add(r0)
            float r12 = r12 + r13
            android.graphics.PointF r0 = point(r1, r12)
            r14.add(r0)
            android.graphics.PointF r0 = point(r15, r12)
            r14.add(r0)
            float r9 = r9 + r16
            android.graphics.PointF r0 = point(r7, r9)
            r14.add(r0)
            goto L_0x07a0
        L_0x0139:
            float r0 = -r1
            float r0 = r0 - r10
            float r2 = -r6
            float r2 = r2 - r10
            android.graphics.PointF r3 = point(r0, r2)
            r14.add(r3)
            android.graphics.PointF r3 = point(r7, r2)
            r14.add(r3)
            float r1 = r1 + r10
            android.graphics.PointF r2 = point(r1, r2)
            r14.add(r2)
            android.graphics.PointF r2 = point(r0, r7)
            r14.add(r2)
            r2 = 0
            android.graphics.PointF r2 = point(r2, r2)
            r14.add(r2)
            android.graphics.PointF r2 = point(r1, r7)
            r14.add(r2)
            float r6 = r6 + r10
            android.graphics.PointF r0 = point(r0, r6)
            r14.add(r0)
            android.graphics.PointF r0 = point(r7, r6)
            r14.add(r0)
            android.graphics.PointF r0 = point(r1, r6)
            r14.add(r0)
            goto L_0x07a0
        L_0x0181:
            float r0 = -r3
            float r0 = r0 - r8
            float r1 = -r4
            float r1 = r1 - r17
            android.graphics.PointF r3 = point(r0, r1)
            r14.add(r3)
            android.graphics.PointF r1 = point(r15, r1)
            r14.add(r1)
            float r1 = -r2
            float r1 = r1 - r8
            android.graphics.PointF r3 = point(r0, r1)
            r14.add(r3)
            android.graphics.PointF r1 = point(r15, r1)
            r14.add(r1)
            float r2 = r2 + r8
            android.graphics.PointF r1 = point(r0, r2)
            r14.add(r1)
            android.graphics.PointF r1 = point(r15, r2)
            r14.add(r1)
            float r4 = r4 + r17
            android.graphics.PointF r0 = point(r0, r4)
            r14.add(r0)
            android.graphics.PointF r0 = point(r15, r4)
            r14.add(r0)
            goto L_0x07a0
        L_0x01c5:
            float r0 = -r5
            float r0 = r0 - r17
            float r1 = -r2
            float r1 = r1 - r8
            android.graphics.PointF r4 = point(r0, r1)
            r14.add(r4)
            float r3 = -r3
            float r3 = r3 - r8
            android.graphics.PointF r4 = point(r3, r1)
            r14.add(r4)
            android.graphics.PointF r4 = point(r15, r1)
            r14.add(r4)
            float r5 = r5 + r17
            android.graphics.PointF r1 = point(r5, r1)
            r14.add(r1)
            float r2 = r2 + r8
            android.graphics.PointF r0 = point(r0, r2)
            r14.add(r0)
            android.graphics.PointF r0 = point(r3, r2)
            r14.add(r0)
            android.graphics.PointF r0 = point(r15, r2)
            r14.add(r0)
            android.graphics.PointF r0 = point(r5, r2)
            r14.add(r0)
            goto L_0x07a0
        L_0x0209:
            float r1 = -r9
            float r1 = r1 - r16
            android.graphics.PointF r1 = point(r7, r1)
            r14.add(r1)
            float r1 = -r3
            float r1 = r1 - r8
            float r2 = -r12
            float r2 = r2 - r13
            android.graphics.PointF r3 = point(r1, r2)
            r14.add(r3)
            android.graphics.PointF r2 = point(r15, r2)
            r14.add(r2)
            float r2 = -r0
            android.graphics.PointF r2 = point(r2, r7)
            r14.add(r2)
            android.graphics.PointF r0 = point(r0, r7)
            r14.add(r0)
            float r12 = r12 + r13
            android.graphics.PointF r0 = point(r1, r12)
            r14.add(r0)
            android.graphics.PointF r0 = point(r15, r12)
            r14.add(r0)
            float r9 = r9 + r16
            android.graphics.PointF r0 = point(r7, r9)
            r14.add(r0)
            goto L_0x07a0
        L_0x024e:
            float r0 = -r1
            float r0 = r0 - r10
            float r2 = -r6
            float r2 = r2 - r10
            android.graphics.PointF r3 = point(r0, r2)
            r14.add(r3)
            android.graphics.PointF r3 = point(r7, r2)
            r14.add(r3)
            float r1 = r1 + r10
            android.graphics.PointF r2 = point(r1, r2)
            r14.add(r2)
            android.graphics.PointF r2 = point(r0, r7)
            r14.add(r2)
            android.graphics.PointF r2 = point(r1, r7)
            r14.add(r2)
            float r6 = r6 + r10
            android.graphics.PointF r0 = point(r0, r6)
            r14.add(r0)
            android.graphics.PointF r0 = point(r7, r6)
            r14.add(r0)
            android.graphics.PointF r0 = point(r1, r6)
            r14.add(r0)
            goto L_0x07a0
        L_0x028e:
            float r1 = -r0
            float r2 = -r12
            float r2 = r2 - r13
            android.graphics.PointF r4 = point(r1, r2)
            r14.add(r4)
            android.graphics.PointF r4 = point(r7, r2)
            r14.add(r4)
            android.graphics.PointF r2 = point(r0, r2)
            r14.add(r2)
            float r2 = -r3
            float r2 = r2 - r8
            android.graphics.PointF r2 = point(r2, r7)
            r14.add(r2)
            android.graphics.PointF r2 = point(r15, r7)
            r14.add(r2)
            float r12 = r12 + r13
            android.graphics.PointF r1 = point(r1, r12)
            r14.add(r1)
            android.graphics.PointF r1 = point(r7, r12)
            r14.add(r1)
            android.graphics.PointF r0 = point(r0, r12)
            r14.add(r0)
            goto L_0x07a0
        L_0x02ce:
            float r1 = -r0
            float r2 = -r13
            android.graphics.PointF r1 = point(r1, r2)
            r14.add(r1)
            android.graphics.PointF r1 = point(r7, r2)
            r14.add(r1)
            android.graphics.PointF r0 = point(r0, r2)
            r14.add(r0)
            float r0 = -r11
            android.graphics.PointF r0 = point(r0, r12)
            r14.add(r0)
            r15 = r22
            float r0 = -r15
            android.graphics.PointF r0 = point(r0, r12)
            r14.add(r0)
            android.graphics.PointF r0 = point(r15, r12)
            r14.add(r0)
            android.graphics.PointF r0 = point(r11, r12)
            r14.add(r0)
            goto L_0x07a0
        L_0x0307:
            r15 = r22
            float r1 = -r11
            float r2 = -r12
            android.graphics.PointF r1 = point(r1, r2)
            r14.add(r1)
            float r1 = -r15
            android.graphics.PointF r1 = point(r1, r2)
            r14.add(r1)
            android.graphics.PointF r1 = point(r15, r2)
            r14.add(r1)
            android.graphics.PointF r1 = point(r11, r2)
            r14.add(r1)
            float r1 = -r0
            android.graphics.PointF r1 = point(r1, r13)
            r14.add(r1)
            android.graphics.PointF r1 = point(r7, r13)
            r14.add(r1)
            android.graphics.PointF r0 = point(r0, r13)
            r14.add(r0)
            goto L_0x07a0
        L_0x0340:
            r15 = r22
            float r1 = -r0
            android.graphics.PointF r1 = point(r7, r1)
            r14.add(r1)
            float r1 = -r12
            float r1 = r1 - r13
            float r2 = -r15
            android.graphics.PointF r3 = point(r1, r2)
            r14.add(r3)
            float r12 = r12 + r13
            android.graphics.PointF r2 = point(r12, r2)
            r14.add(r2)
            r2 = 0
            android.graphics.PointF r2 = point(r2, r2)
            r14.add(r2)
            android.graphics.PointF r1 = point(r1, r15)
            r14.add(r1)
            android.graphics.PointF r1 = point(r12, r15)
            r14.add(r1)
            android.graphics.PointF r0 = point(r7, r0)
            r14.add(r0)
            goto L_0x07a0
        L_0x037b:
            r15 = r22
            float r1 = -r15
            float r2 = -r12
            float r2 = r2 - r13
            android.graphics.PointF r3 = point(r1, r2)
            r14.add(r3)
            android.graphics.PointF r2 = point(r15, r2)
            r14.add(r2)
            float r2 = -r0
            android.graphics.PointF r2 = point(r2, r7)
            r14.add(r2)
            r2 = 0
            android.graphics.PointF r2 = point(r2, r2)
            r14.add(r2)
            android.graphics.PointF r0 = point(r0, r7)
            r14.add(r0)
            float r12 = r12 + r13
            android.graphics.PointF r0 = point(r1, r12)
            r14.add(r0)
            android.graphics.PointF r0 = point(r15, r12)
            r14.add(r0)
            goto L_0x07a0
        L_0x03b6:
            float r0 = -r6
            float r0 = r0 - r10
            android.graphics.PointF r0 = point(r7, r0)
            r14.add(r0)
            float r0 = -r1
            float r0 = r0 - r10
            android.graphics.PointF r2 = point(r0, r7)
            r14.add(r2)
            r2 = 0
            android.graphics.PointF r2 = point(r2, r2)
            r14.add(r2)
            float r1 = r1 + r10
            android.graphics.PointF r2 = point(r1, r7)
            r14.add(r2)
            float r6 = r6 + r10
            android.graphics.PointF r0 = point(r0, r6)
            r14.add(r0)
            android.graphics.PointF r0 = point(r7, r6)
            r14.add(r0)
            android.graphics.PointF r0 = point(r1, r6)
            r14.add(r0)
            goto L_0x07a0
        L_0x03f0:
            float r0 = -r1
            float r0 = r0 - r10
            float r2 = -r6
            float r2 = r2 - r10
            android.graphics.PointF r3 = point(r0, r2)
            r14.add(r3)
            android.graphics.PointF r3 = point(r7, r2)
            r14.add(r3)
            float r1 = r1 + r10
            android.graphics.PointF r2 = point(r1, r2)
            r14.add(r2)
            android.graphics.PointF r0 = point(r0, r7)
            r14.add(r0)
            r0 = 0
            android.graphics.PointF r0 = point(r0, r0)
            r14.add(r0)
            android.graphics.PointF r0 = point(r1, r7)
            r14.add(r0)
            float r6 = r6 + r10
            android.graphics.PointF r0 = point(r7, r6)
            r14.add(r0)
            goto L_0x07a0
        L_0x042a:
            float r1 = -r12
            float r1 = r1 - r13
            float r1 = r1 - r21
            android.graphics.PointF r1 = point(r7, r1)
            r14.add(r1)
            float r1 = -r3
            float r1 = r1 - r8
            r2 = r21
            float r3 = -r2
            android.graphics.PointF r1 = point(r1, r3)
            r14.add(r1)
            android.graphics.PointF r1 = point(r15, r3)
            r14.add(r1)
            float r1 = -r0
            float r12 = r12 + r13
            float r12 = r12 - r2
            android.graphics.PointF r1 = point(r1, r12)
            r14.add(r1)
            android.graphics.PointF r1 = point(r7, r12)
            r14.add(r1)
            android.graphics.PointF r0 = point(r0, r12)
            r14.add(r0)
            goto L_0x07a0
        L_0x0462:
            r2 = r21
            float r1 = -r0
            float r4 = -r12
            float r4 = r4 - r13
            float r4 = r4 + r2
            android.graphics.PointF r1 = point(r1, r4)
            r14.add(r1)
            android.graphics.PointF r1 = point(r7, r4)
            r14.add(r1)
            android.graphics.PointF r0 = point(r0, r4)
            r14.add(r0)
            float r0 = -r3
            float r0 = r0 - r8
            android.graphics.PointF r0 = point(r0, r2)
            r14.add(r0)
            android.graphics.PointF r0 = point(r15, r2)
            r14.add(r0)
            float r12 = r12 + r13
            float r12 = r12 + r2
            android.graphics.PointF r0 = point(r7, r12)
            r14.add(r0)
            goto L_0x07a0
        L_0x0498:
            r15 = r22
            float r1 = -r0
            android.graphics.PointF r1 = point(r7, r1)
            r14.add(r1)
            float r1 = -r12
            float r1 = r1 - r13
            float r2 = -r15
            android.graphics.PointF r3 = point(r1, r2)
            r14.add(r3)
            float r12 = r12 + r13
            android.graphics.PointF r2 = point(r12, r2)
            r14.add(r2)
            android.graphics.PointF r1 = point(r1, r15)
            r14.add(r1)
            android.graphics.PointF r1 = point(r12, r15)
            r14.add(r1)
            android.graphics.PointF r0 = point(r7, r0)
            r14.add(r0)
            goto L_0x07a0
        L_0x04cb:
            r15 = r22
            float r1 = -r15
            float r2 = -r12
            float r2 = r2 - r13
            android.graphics.PointF r3 = point(r1, r2)
            r14.add(r3)
            android.graphics.PointF r2 = point(r15, r2)
            r14.add(r2)
            float r2 = -r0
            android.graphics.PointF r2 = point(r2, r7)
            r14.add(r2)
            android.graphics.PointF r0 = point(r0, r7)
            r14.add(r0)
            float r12 = r12 + r13
            android.graphics.PointF r0 = point(r1, r12)
            r14.add(r0)
            android.graphics.PointF r0 = point(r15, r12)
            r14.add(r0)
            goto L_0x07a0
        L_0x04fe:
            float r0 = -r3
            float r0 = r0 - r8
            float r1 = -r6
            float r1 = r1 - r10
            android.graphics.PointF r2 = point(r0, r1)
            r14.add(r2)
            android.graphics.PointF r1 = point(r15, r1)
            r14.add(r1)
            android.graphics.PointF r1 = point(r0, r7)
            r14.add(r1)
            android.graphics.PointF r1 = point(r15, r7)
            r14.add(r1)
            float r6 = r6 + r10
            android.graphics.PointF r0 = point(r0, r6)
            r14.add(r0)
            android.graphics.PointF r0 = point(r15, r6)
            r14.add(r0)
            goto L_0x07a0
        L_0x052f:
            float r0 = -r1
            float r0 = r0 - r10
            float r3 = -r2
            float r3 = r3 - r8
            android.graphics.PointF r4 = point(r0, r3)
            r14.add(r4)
            android.graphics.PointF r4 = point(r7, r3)
            r14.add(r4)
            float r1 = r1 + r10
            android.graphics.PointF r3 = point(r1, r3)
            r14.add(r3)
            float r2 = r2 + r8
            android.graphics.PointF r0 = point(r0, r2)
            r14.add(r0)
            android.graphics.PointF r0 = point(r7, r2)
            r14.add(r0)
            android.graphics.PointF r0 = point(r1, r2)
            r14.add(r0)
            goto L_0x07a0
        L_0x0561:
            float r0 = -r6
            float r0 = r0 - r10
            android.graphics.PointF r0 = point(r7, r0)
            r14.add(r0)
            float r0 = -r1
            float r0 = r0 - r10
            android.graphics.PointF r0 = point(r0, r7)
            r14.add(r0)
            r0 = 0
            android.graphics.PointF r0 = point(r0, r0)
            r14.add(r0)
            float r1 = r1 + r10
            android.graphics.PointF r0 = point(r1, r7)
            r14.add(r0)
            float r6 = r6 + r10
            android.graphics.PointF r0 = point(r7, r6)
            r14.add(r0)
            goto L_0x07a0
        L_0x058d:
            float r1 = -r3
            float r1 = r1 - r8
            float r2 = -r13
            android.graphics.PointF r1 = point(r1, r2)
            r14.add(r1)
            android.graphics.PointF r1 = point(r15, r2)
            r14.add(r1)
            float r1 = -r0
            android.graphics.PointF r1 = point(r1, r12)
            r14.add(r1)
            android.graphics.PointF r1 = point(r7, r12)
            r14.add(r1)
            android.graphics.PointF r0 = point(r0, r12)
            r14.add(r0)
            goto L_0x07a0
        L_0x05b6:
            float r1 = -r0
            float r2 = -r12
            android.graphics.PointF r1 = point(r1, r2)
            r14.add(r1)
            android.graphics.PointF r1 = point(r7, r2)
            r14.add(r1)
            android.graphics.PointF r0 = point(r0, r2)
            r14.add(r0)
            float r0 = -r3
            float r0 = r0 - r8
            android.graphics.PointF r0 = point(r0, r13)
            r14.add(r0)
            android.graphics.PointF r0 = point(r15, r13)
            r14.add(r0)
            goto L_0x07a0
        L_0x05df:
            float r0 = -r3
            float r0 = r0 - r8
            float r1 = -r2
            float r1 = r1 - r8
            android.graphics.PointF r3 = point(r0, r1)
            r14.add(r3)
            android.graphics.PointF r1 = point(r15, r1)
            r14.add(r1)
            float r2 = r2 + r8
            android.graphics.PointF r0 = point(r0, r2)
            r14.add(r0)
            android.graphics.PointF r0 = point(r15, r2)
            r14.add(r0)
            goto L_0x07a0
        L_0x0602:
            r15 = r22
            float r0 = -r13
            android.graphics.PointF r0 = point(r7, r0)
            r14.add(r0)
            float r0 = -r15
            android.graphics.PointF r0 = point(r0, r12)
            r14.add(r0)
            android.graphics.PointF r0 = point(r15, r12)
            r14.add(r0)
            goto L_0x07a0
        L_0x061d:
            r15 = r22
            float r0 = -r15
            float r1 = -r12
            android.graphics.PointF r0 = point(r0, r1)
            r14.add(r0)
            android.graphics.PointF r0 = point(r15, r1)
            r14.add(r0)
            android.graphics.PointF r0 = point(r7, r13)
            r14.add(r0)
            goto L_0x07a0
        L_0x0638:
            float r0 = -r6
            float r0 = r0 - r10
            android.graphics.PointF r0 = point(r7, r0)
            r14.add(r0)
            r0 = 0
            android.graphics.PointF r0 = point(r0, r0)
            r14.add(r0)
            float r6 = r6 + r10
            android.graphics.PointF r0 = point(r7, r6)
            r14.add(r0)
            goto L_0x07a0
        L_0x0653:
            r0 = 0
            float r2 = -r1
            float r2 = r2 - r10
            android.graphics.PointF r2 = point(r2, r7)
            r14.add(r2)
            android.graphics.PointF r0 = point(r0, r0)
            r14.add(r0)
            float r1 = r1 + r10
            android.graphics.PointF r0 = point(r1, r7)
            r14.add(r0)
            goto L_0x07a0
        L_0x066e:
            float r0 = -r2
            float r0 = r0 - r8
            android.graphics.PointF r0 = point(r7, r0)
            r14.add(r0)
            float r2 = r2 + r8
            android.graphics.PointF r0 = point(r7, r2)
            r14.add(r0)
            goto L_0x07a0
        L_0x0681:
            float r0 = -r3
            float r0 = r0 - r8
            android.graphics.PointF r0 = point(r0, r7)
            r14.add(r0)
            android.graphics.PointF r0 = point(r15, r7)
            r14.add(r0)
            goto L_0x07a0
        L_0x0693:
            r0 = 0
            android.graphics.PointF r0 = point(r0, r0)
            r14.add(r0)
            goto L_0x07a0
        L_0x069d:
            float r1 = -r12
            float r1 = r1 - r13
            float r3 = -r0
            android.graphics.PointF r4 = point(r1, r3)
            r14.add(r4)
            float r12 = r12 + r13
            android.graphics.PointF r3 = point(r12, r3)
            r14.add(r3)
            float r3 = -r2
            float r3 = r3 - r8
            android.graphics.PointF r3 = point(r7, r3)
            r14.add(r3)
            android.graphics.PointF r3 = point(r1, r7)
            r14.add(r3)
            android.graphics.PointF r3 = point(r12, r7)
            r14.add(r3)
            float r2 = r2 + r8
            android.graphics.PointF r2 = point(r7, r2)
            r14.add(r2)
            android.graphics.PointF r1 = point(r1, r0)
            r14.add(r1)
            android.graphics.PointF r0 = point(r12, r0)
            r14.add(r0)
            goto L_0x07a0
        L_0x06de:
            float r0 = -r9
            android.graphics.PointF r1 = point(r7, r0)
            r14.add(r1)
            float r1 = -r12
            android.graphics.PointF r2 = point(r1, r1)
            r14.add(r2)
            android.graphics.PointF r2 = point(r12, r1)
            r14.add(r2)
            android.graphics.PointF r0 = point(r0, r7)
            r14.add(r0)
            r0 = 0
            android.graphics.PointF r0 = point(r0, r0)
            r14.add(r0)
            android.graphics.PointF r0 = point(r9, r7)
            r14.add(r0)
            android.graphics.PointF r0 = point(r1, r12)
            r14.add(r0)
            android.graphics.PointF r0 = point(r12, r12)
            r14.add(r0)
            android.graphics.PointF r0 = point(r7, r9)
            r14.add(r0)
            goto L_0x07a0
        L_0x0722:
            float r0 = -r9
            android.graphics.PointF r1 = point(r7, r0)
            r14.add(r1)
            float r1 = -r12
            android.graphics.PointF r2 = point(r1, r1)
            r14.add(r2)
            android.graphics.PointF r2 = point(r12, r1)
            r14.add(r2)
            android.graphics.PointF r0 = point(r0, r7)
            r14.add(r0)
            android.graphics.PointF r0 = point(r9, r7)
            r14.add(r0)
            android.graphics.PointF r0 = point(r1, r12)
            r14.add(r0)
            android.graphics.PointF r0 = point(r12, r12)
            r14.add(r0)
            android.graphics.PointF r0 = point(r7, r9)
            r14.add(r0)
            goto L_0x07a0
        L_0x075d:
            float r0 = -r12
            android.graphics.PointF r1 = point(r0, r0)
            r14.add(r1)
            android.graphics.PointF r1 = point(r12, r0)
            r14.add(r1)
            r1 = 0
            android.graphics.PointF r1 = point(r1, r1)
            r14.add(r1)
            android.graphics.PointF r0 = point(r0, r12)
            r14.add(r0)
            android.graphics.PointF r0 = point(r12, r12)
            r14.add(r0)
            goto L_0x07a0
        L_0x0783:
            float r0 = -r12
            android.graphics.PointF r1 = point(r7, r0)
            r14.add(r1)
            android.graphics.PointF r1 = point(r12, r7)
            r14.add(r1)
            android.graphics.PointF r1 = point(r7, r12)
            r14.add(r1)
            android.graphics.PointF r0 = point(r0, r7)
            r14.add(r0)
        L_0x07a0:
            java.util.Iterator r0 = r14.iterator()
        L_0x07a4:
            boolean r1 = r0.hasNext()
            if (r1 == 0) goto L_0x07ce
            java.lang.Object r1 = r0.next()
            android.graphics.PointF r1 = (android.graphics.PointF) r1
            android.graphics.RectF r2 = new android.graphics.RectF
            float r3 = r1.x
            r4 = r26
            int r5 = r4.x
            r6 = 2
            int r5 = r5 / r6
            float r5 = (float) r5
            float r3 = r3 + r5
            float r3 = r3 - r8
            float r1 = r1.y
            int r5 = r4.y
            int r5 = r5 / r6
            float r5 = (float) r5
            float r1 = r1 + r5
            float r1 = r1 - r8
            r2.<init>(r3, r1, r10, r10)
            r1 = r20
            r1.add(r2)
            goto L_0x07a4
        L_0x07ce:
            r1 = r20
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: com.nightonke.boommenu.Piece.PiecePlaceManager.getDotPositions(com.nightonke.boommenu.BoomMenuButton, android.graphics.Point):java.util.ArrayList");
    }

    public static ArrayList<RectF> getHamPositions(BoomMenuButton boomMenuButton, Point point) {
        ArrayList<RectF> arrayList = new ArrayList<>();
        ArrayList arrayList2 = new ArrayList();
        int pieceNumber = boomMenuButton.getPiecePlaceEnum().pieceNumber();
        int i = pieceNumber / 2;
        float hamWidth = boomMenuButton.getHamWidth();
        float f = hamWidth / 2.0f;
        float hamHeight = boomMenuButton.getHamHeight();
        float f2 = hamHeight / 2.0f;
        float pieceVerticalMargin = boomMenuButton.getPieceVerticalMargin();
        float f3 = pieceVerticalMargin / 2.0f;
        if (!boomMenuButton.getPiecePlaceEnum().equals(PiecePlaceEnum.Custom)) {
            int i2 = 0;
            if (pieceNumber % 2 == 0) {
                for (int i3 = i - 1; i3 >= 0; i3--) {
                    arrayList2.add(point(0.0f, ((-f2) - f3) - (((float) i3) * (hamHeight + pieceVerticalMargin))));
                }
                while (i2 < i) {
                    arrayList2.add(point(0.0f, f2 + f3 + (((float) i2) * (hamHeight + pieceVerticalMargin))));
                    i2++;
                }
            } else {
                for (int i4 = i - 1; i4 >= 0; i4--) {
                    arrayList2.add(point(0.0f, ((-hamHeight) - pieceVerticalMargin) - (((float) i4) * (hamHeight + pieceVerticalMargin))));
                }
                arrayList2.add(point(0, 0));
                while (i2 < i) {
                    float f4 = hamHeight + pieceVerticalMargin;
                    arrayList2.add(point(0.0f, f4 + (((float) i2) * f4)));
                    i2++;
                }
            }
        } else {
            arrayList2 = boomMenuButton.getCustomPiecePlacePositions();
        }
        Iterator it = arrayList2.iterator();
        while (it.hasNext()) {
            PointF pointF = (PointF) it.next();
            arrayList.add(new RectF((pointF.x + ((float) (point.x / 2))) - f, (pointF.y + ((float) (point.y / 2))) - f2, hamWidth, hamHeight));
        }
        return arrayList;
    }

    public static ArrayList<RectF> getShareDotPositions(BoomMenuButton boomMenuButton, Point point, int i) {
        ArrayList<RectF> arrayList = new ArrayList<>();
        float dotRadius = boomMenuButton.getDotRadius();
        double shareLineLength = (double) boomMenuButton.getShareLineLength();
        double sqrt = Math.sqrt(3.0d);
        Double.isNaN(shareLineLength);
        float f = (float) ((shareLineLength * sqrt) / 3.0d);
        float f2 = f / 2.0f;
        float shareLineLength2 = boomMenuButton.getShareLineLength() / 2.0f;
        for (int i2 = 0; i2 < i; i2++) {
            int i3 = i2 % 3;
            if (i3 == 0) {
                float f3 = dotRadius + dotRadius;
                arrayList.add(new RectF(f2, -shareLineLength2, f3, f3));
            } else if (i3 == 1) {
                float f4 = dotRadius + dotRadius;
                arrayList.add(new RectF(-f, 0.0f, f4, f4));
            } else if (i3 == 2) {
                float f5 = dotRadius + dotRadius;
                arrayList.add(new RectF(f2, shareLineLength2, f5, f5));
            }
        }
        Collections.sort(arrayList, new Comparator<RectF>() {
            public int compare(RectF rectF, RectF rectF2) {
                if (rectF.top < rectF2.top) {
                    return -1;
                }
                return rectF.top > rectF2.top ? 1 : 0;
            }
        });
        for (int i4 = 0; i4 < arrayList.size(); i4++) {
            RectF rectF = (RectF) arrayList.get(i4);
            rectF.left += ((float) (point.x / 2)) - dotRadius;
            RectF rectF2 = (RectF) arrayList.get(i4);
            rectF2.top += ((float) (point.y / 2)) - dotRadius;
        }
        return arrayList;
    }

    public static BoomPiece createPiece(BoomMenuButton boomMenuButton, BoomButtonBuilder boomButtonBuilder) {
        int i = C45132.$SwitchMap$com$nightonke$boommenu$ButtonEnum[boomMenuButton.getButtonEnum().ordinal()];
        if (i == 1 || i == 2 || i == 3) {
            return createDot(boomMenuButton.getContext(), boomButtonBuilder, boomMenuButton.getPieceCornerRadius());
        }
        if (i == 4) {
            return createHam(boomMenuButton.getContext(), boomButtonBuilder, boomMenuButton.getPieceCornerRadius());
        }
        throw new RuntimeException("Unknown button-enum!");
    }

    private static Dot createDot(Context context, BoomButtonBuilder boomButtonBuilder, float f) {
        Dot dot = new Dot(context);
        boomButtonBuilder.piece(dot);
        dot.init(boomButtonBuilder.pieceColor(context), f);
        return dot;
    }

    private static Ham createHam(Context context, BoomButtonBuilder boomButtonBuilder, float f) {
        Ham ham = new Ham(context);
        boomButtonBuilder.piece(ham);
        ham.init(boomButtonBuilder.pieceColor(context), f);
        return ham;
    }

    private static PointF point(float f, float f2) {
        return new PointF(f, f2);
    }

    private static PointF point(int i, int i2) {
        return new PointF((float) i, (float) i2);
    }
}
