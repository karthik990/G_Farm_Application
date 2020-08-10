package kotlin.text;

import kotlin.Metadata;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Lambda;

@Metadata(mo22060bv = {1, 0, 3}, mo22061d1 = {"\u0000\n\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u0001H\n¢\u0006\u0002\b\u0003"}, mo22062d2 = {"<anonymous>", "", "line", "invoke"}, mo22063k = 3, mo22064mv = {1, 1, 15})
/* compiled from: Indent.kt */
final class StringsKt__IndentKt$getIndentFunction$2 extends Lambda implements Function1<String, String> {
    final /* synthetic */ String $indent;

    StringsKt__IndentKt$getIndentFunction$2(String str) {
        this.$indent = str;
        super(1);
    }

    public final String invoke(String str) {
        Intrinsics.checkParameterIsNotNull(str, "line");
        StringBuilder sb = new StringBuilder();
        sb.append(this.$indent);
        sb.append(str);
        return sb.toString();
    }
}
