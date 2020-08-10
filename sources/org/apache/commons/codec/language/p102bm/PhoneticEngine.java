package org.apache.commons.codec.language.p102bm;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.EnumMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import org.apache.commons.codec.language.p102bm.Languages.LanguageSet;
import org.apache.commons.codec.language.p102bm.Rule.Phoneme;
import org.apache.commons.codec.language.p102bm.Rule.PhonemeExpr;

/* renamed from: org.apache.commons.codec.language.bm.PhoneticEngine */
public class PhoneticEngine {
    private static final int DEFAULT_MAX_PHONEMES = 20;
    private static final Map<NameType, Set<String>> NAME_PREFIXES = new EnumMap(NameType.class);
    private final boolean concat;
    private final Lang lang;
    private final int maxPhonemes;
    private final NameType nameType;
    private final RuleType ruleType;

    /* renamed from: org.apache.commons.codec.language.bm.PhoneticEngine$1 */
    static /* synthetic */ class C60531 {
        static final /* synthetic */ int[] $SwitchMap$org$apache$commons$codec$language$bm$NameType = new int[NameType.values().length];

        /* JADX WARNING: Can't wrap try/catch for region: R(8:0|1|2|3|4|5|6|8) */
        /* JADX WARNING: Failed to process nested try/catch */
        /* JADX WARNING: Missing exception handler attribute for start block: B:3:0x0014 */
        /* JADX WARNING: Missing exception handler attribute for start block: B:5:0x001f */
        static {
            /*
                org.apache.commons.codec.language.bm.NameType[] r0 = org.apache.commons.codec.language.p102bm.NameType.values()
                int r0 = r0.length
                int[] r0 = new int[r0]
                $SwitchMap$org$apache$commons$codec$language$bm$NameType = r0
                int[] r0 = $SwitchMap$org$apache$commons$codec$language$bm$NameType     // Catch:{ NoSuchFieldError -> 0x0014 }
                org.apache.commons.codec.language.bm.NameType r1 = org.apache.commons.codec.language.p102bm.NameType.SEPHARDIC     // Catch:{ NoSuchFieldError -> 0x0014 }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x0014 }
                r2 = 1
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x0014 }
            L_0x0014:
                int[] r0 = $SwitchMap$org$apache$commons$codec$language$bm$NameType     // Catch:{ NoSuchFieldError -> 0x001f }
                org.apache.commons.codec.language.bm.NameType r1 = org.apache.commons.codec.language.p102bm.NameType.ASHKENAZI     // Catch:{ NoSuchFieldError -> 0x001f }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x001f }
                r2 = 2
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x001f }
            L_0x001f:
                int[] r0 = $SwitchMap$org$apache$commons$codec$language$bm$NameType     // Catch:{ NoSuchFieldError -> 0x002a }
                org.apache.commons.codec.language.bm.NameType r1 = org.apache.commons.codec.language.p102bm.NameType.GENERIC     // Catch:{ NoSuchFieldError -> 0x002a }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x002a }
                r2 = 3
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x002a }
            L_0x002a:
                return
            */
            throw new UnsupportedOperationException("Method not decompiled: org.apache.commons.codec.language.p102bm.PhoneticEngine.C60531.<clinit>():void");
        }
    }

    /* renamed from: org.apache.commons.codec.language.bm.PhoneticEngine$PhonemeBuilder */
    static final class PhonemeBuilder {
        private final Set<Phoneme> phonemes;

        /* synthetic */ PhonemeBuilder(Set set, C60531 r2) {
            this(set);
        }

        public static PhonemeBuilder empty(LanguageSet languageSet) {
            return new PhonemeBuilder(new Phoneme((CharSequence) "", languageSet));
        }

        private PhonemeBuilder(Phoneme phoneme) {
            this.phonemes = new LinkedHashSet();
            this.phonemes.add(phoneme);
        }

        private PhonemeBuilder(Set<Phoneme> set) {
            this.phonemes = set;
        }

        public void append(CharSequence charSequence) {
            for (Phoneme append : this.phonemes) {
                append.append(charSequence);
            }
        }

        public void apply(PhonemeExpr phonemeExpr, int i) {
            LinkedHashSet linkedHashSet = new LinkedHashSet(i);
            loop0:
            for (Phoneme phoneme : this.phonemes) {
                Iterator it = phonemeExpr.getPhonemes().iterator();
                while (true) {
                    if (it.hasNext()) {
                        Phoneme phoneme2 = (Phoneme) it.next();
                        LanguageSet restrictTo = phoneme.getLanguages().restrictTo(phoneme2.getLanguages());
                        if (!restrictTo.isEmpty()) {
                            Phoneme phoneme3 = new Phoneme(phoneme, phoneme2, restrictTo);
                            if (linkedHashSet.size() < i) {
                                linkedHashSet.add(phoneme3);
                                if (linkedHashSet.size() >= i) {
                                    break loop0;
                                }
                            } else {
                                continue;
                            }
                        }
                    }
                }
            }
            this.phonemes.clear();
            this.phonemes.addAll(linkedHashSet);
        }

        public Set<Phoneme> getPhonemes() {
            return this.phonemes;
        }

        public String makeString() {
            StringBuilder sb = new StringBuilder();
            for (Phoneme phoneme : this.phonemes) {
                if (sb.length() > 0) {
                    sb.append("|");
                }
                sb.append(phoneme.getPhonemeText());
            }
            return sb.toString();
        }
    }

    /* renamed from: org.apache.commons.codec.language.bm.PhoneticEngine$RulesApplication */
    private static final class RulesApplication {
        private final Map<String, List<Rule>> finalRules;
        private boolean found;

        /* renamed from: i */
        private int f4515i;
        private final CharSequence input;
        private final int maxPhonemes;
        private final PhonemeBuilder phonemeBuilder;

        public RulesApplication(Map<String, List<Rule>> map, CharSequence charSequence, PhonemeBuilder phonemeBuilder2, int i, int i2) {
            if (map != null) {
                this.finalRules = map;
                this.phonemeBuilder = phonemeBuilder2;
                this.input = charSequence;
                this.f4515i = i;
                this.maxPhonemes = i2;
                return;
            }
            throw new NullPointerException("The finalRules argument must not be null");
        }

        public int getI() {
            return this.f4515i;
        }

        public PhonemeBuilder getPhonemeBuilder() {
            return this.phonemeBuilder;
        }

        public RulesApplication invoke() {
            int i;
            this.found = false;
            Map<String, List<Rule>> map = this.finalRules;
            CharSequence charSequence = this.input;
            int i2 = this.f4515i;
            List list = (List) map.get(charSequence.subSequence(i2, i2 + 1));
            int i3 = 1;
            if (list != null) {
                Iterator it = list.iterator();
                i = 1;
                while (true) {
                    if (!it.hasNext()) {
                        break;
                    }
                    Rule rule = (Rule) it.next();
                    int length = rule.getPattern().length();
                    if (rule.patternAndContextMatches(this.input, this.f4515i)) {
                        this.phonemeBuilder.apply(rule.getPhoneme(), this.maxPhonemes);
                        this.found = true;
                        i = length;
                        break;
                    }
                    i = length;
                }
            } else {
                i = 1;
            }
            if (this.found) {
                i3 = i;
            }
            this.f4515i += i3;
            return this;
        }

        public boolean isFound() {
            return this.found;
        }
    }

    static {
        String str = "da";
        String str2 = "de";
        String str3 = "van";
        String str4 = "von";
        NAME_PREFIXES.put(NameType.ASHKENAZI, Collections.unmodifiableSet(new HashSet(Arrays.asList(new String[]{"bar", "ben", str, str2, str3, str4}))));
        String str5 = "dal";
        String str6 = "del";
        String str7 = "dela";
        NAME_PREFIXES.put(NameType.SEPHARDIC, Collections.unmodifiableSet(new HashSet(Arrays.asList(new String[]{"al", "el", str, str5, str2, str6, str7, "de la", "della", "des", "di", "do", "dos", "du", str3, str4}))));
        NAME_PREFIXES.put(NameType.GENERIC, Collections.unmodifiableSet(new HashSet(Arrays.asList(new String[]{str, str5, str2, str6, str7, "de la", "della", "des", "di", "do", "dos", "du", str3, str4}))));
    }

    private static String join(Iterable<String> iterable, String str) {
        StringBuilder sb = new StringBuilder();
        Iterator it = iterable.iterator();
        if (it.hasNext()) {
            sb.append((String) it.next());
        }
        while (it.hasNext()) {
            sb.append(str);
            sb.append((String) it.next());
        }
        return sb.toString();
    }

    public PhoneticEngine(NameType nameType2, RuleType ruleType2, boolean z) {
        this(nameType2, ruleType2, z, 20);
    }

    public PhoneticEngine(NameType nameType2, RuleType ruleType2, boolean z, int i) {
        if (ruleType2 != RuleType.RULES) {
            this.nameType = nameType2;
            this.ruleType = ruleType2;
            this.concat = z;
            this.lang = Lang.instance(nameType2);
            this.maxPhonemes = i;
            return;
        }
        StringBuilder sb = new StringBuilder();
        sb.append("ruleType must not be ");
        sb.append(RuleType.RULES);
        throw new IllegalArgumentException(sb.toString());
    }

    private PhonemeBuilder applyFinalRules(PhonemeBuilder phonemeBuilder, Map<String, List<Rule>> map) {
        if (map == null) {
            throw new NullPointerException("finalRules can not be null");
        } else if (map.isEmpty()) {
            return phonemeBuilder;
        } else {
            TreeMap treeMap = new TreeMap(Phoneme.COMPARATOR);
            for (Phoneme phoneme : phonemeBuilder.getPhonemes()) {
                PhonemeBuilder empty = PhonemeBuilder.empty(phoneme.getLanguages());
                String charSequence = phoneme.getPhonemeText().toString();
                PhonemeBuilder phonemeBuilder2 = empty;
                int i = 0;
                while (i < charSequence.length()) {
                    RulesApplication rulesApplication = new RulesApplication(map, charSequence, phonemeBuilder2, i, this.maxPhonemes);
                    RulesApplication invoke = rulesApplication.invoke();
                    boolean isFound = invoke.isFound();
                    phonemeBuilder2 = invoke.getPhonemeBuilder();
                    if (!isFound) {
                        phonemeBuilder2.append(charSequence.subSequence(i, i + 1));
                    }
                    i = invoke.getI();
                }
                for (Phoneme phoneme2 : phonemeBuilder2.getPhonemes()) {
                    if (treeMap.containsKey(phoneme2)) {
                        Phoneme mergeWithLanguage = ((Phoneme) treeMap.remove(phoneme2)).mergeWithLanguage(phoneme2.getLanguages());
                        treeMap.put(mergeWithLanguage, mergeWithLanguage);
                    } else {
                        treeMap.put(phoneme2, phoneme2);
                    }
                }
            }
            return new PhonemeBuilder(treeMap.keySet(), null);
        }
    }

    public String encode(String str) {
        return encode(str, this.lang.guessLanguages(str));
    }

    public String encode(String str, LanguageSet languageSet) {
        String str2;
        Map instanceMap = Rule.getInstanceMap(this.nameType, RuleType.RULES, languageSet);
        Map instanceMap2 = Rule.getInstanceMap(this.nameType, this.ruleType, "common");
        Map instanceMap3 = Rule.getInstanceMap(this.nameType, this.ruleType, languageSet);
        String trim = str.toLowerCase(Locale.ENGLISH).replace('-', ' ').trim();
        String str3 = " ";
        if (this.nameType == NameType.GENERIC) {
            String str4 = ")";
            String str5 = ")-(";
            String str6 = "(";
            if (trim.length() < 2 || !trim.substring(0, 2).equals("d'")) {
                for (String str7 : (Set) NAME_PREFIXES.get(this.nameType)) {
                    StringBuilder sb = new StringBuilder();
                    sb.append(str7);
                    sb.append(str3);
                    if (trim.startsWith(sb.toString())) {
                        String substring = trim.substring(str7.length() + 1);
                        StringBuilder sb2 = new StringBuilder();
                        sb2.append(str7);
                        sb2.append(substring);
                        String sb3 = sb2.toString();
                        StringBuilder sb4 = new StringBuilder();
                        sb4.append(str6);
                        sb4.append(encode(substring));
                        sb4.append(str5);
                        sb4.append(encode(sb3));
                        sb4.append(str4);
                        return sb4.toString();
                    }
                }
            } else {
                String substring2 = trim.substring(2);
                StringBuilder sb5 = new StringBuilder();
                sb5.append("d");
                sb5.append(substring2);
                String sb6 = sb5.toString();
                StringBuilder sb7 = new StringBuilder();
                sb7.append(str6);
                sb7.append(encode(substring2));
                sb7.append(str5);
                sb7.append(encode(sb6));
                sb7.append(str4);
                return sb7.toString();
            }
        }
        List<String> asList = Arrays.asList(trim.split("\\s+"));
        ArrayList<String> arrayList = new ArrayList<>();
        int i = C60531.$SwitchMap$org$apache$commons$codec$language$bm$NameType[this.nameType.ordinal()];
        if (i == 1) {
            for (String split : asList) {
                String[] split2 = split.split("'");
                arrayList.add(split2[split2.length - 1]);
            }
            arrayList.removeAll((Collection) NAME_PREFIXES.get(this.nameType));
        } else if (i == 2) {
            arrayList.addAll(asList);
            arrayList.removeAll((Collection) NAME_PREFIXES.get(this.nameType));
        } else if (i == 3) {
            arrayList.addAll(asList);
        } else {
            StringBuilder sb8 = new StringBuilder();
            sb8.append("Unreachable case: ");
            sb8.append(this.nameType);
            throw new IllegalStateException(sb8.toString());
        }
        if (this.concat) {
            str2 = join(arrayList, str3);
        } else if (arrayList.size() == 1) {
            str2 = (String) asList.iterator().next();
        } else {
            StringBuilder sb9 = new StringBuilder();
            for (String str8 : arrayList) {
                sb9.append("-");
                sb9.append(encode(str8));
            }
            return sb9.substring(1);
        }
        PhonemeBuilder empty = PhonemeBuilder.empty(languageSet);
        int i2 = 0;
        while (i2 < str2.length()) {
            RulesApplication rulesApplication = new RulesApplication(instanceMap, str2, empty, i2, this.maxPhonemes);
            RulesApplication invoke = rulesApplication.invoke();
            i2 = invoke.getI();
            empty = invoke.getPhonemeBuilder();
        }
        return applyFinalRules(applyFinalRules(empty, instanceMap2), instanceMap3).makeString();
    }

    public Lang getLang() {
        return this.lang;
    }

    public NameType getNameType() {
        return this.nameType;
    }

    public RuleType getRuleType() {
        return this.ruleType;
    }

    public boolean isConcat() {
        return this.concat;
    }

    public int getMaxPhonemes() {
        return this.maxPhonemes;
    }
}
