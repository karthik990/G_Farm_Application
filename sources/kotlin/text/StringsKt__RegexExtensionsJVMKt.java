package kotlin.text;

import java.util.regex.Pattern;
import kotlin.Metadata;

@Metadata(mo22060bv = {1, 0, 3}, mo22061d1 = {"\u0000\f\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\u001a\r\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\b¨\u0006\u0003"}, mo22062d2 = {"toRegex", "Lkotlin/text/Regex;", "Ljava/util/regex/Pattern;", "kotlin-stdlib"}, mo22063k = 5, mo22064mv = {1, 1, 15}, mo22066xi = 1, mo22067xs = "kotlin/text/StringsKt")
/* compiled from: RegexExtensionsJVM.kt */
class StringsKt__RegexExtensionsJVMKt extends StringsKt__IndentKt {
    private static final Regex toRegex(Pattern pattern) {
        return new Regex(pattern);
    }
}
