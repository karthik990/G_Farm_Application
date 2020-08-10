package junit.framework;

public class ComparisonCompactor {
    private static final String DELTA_END = "]";
    private static final String DELTA_START = "[";
    private static final String ELLIPSIS = "...";
    private String fActual;
    private int fContextLength;
    private String fExpected;
    private int fPrefix;
    private int fSuffix;

    public ComparisonCompactor(int i, String str, String str2) {
        this.fContextLength = i;
        this.fExpected = str;
        this.fActual = str2;
    }

    public String compact(String str) {
        if (this.fExpected == null || this.fActual == null || areStringsEqual()) {
            return Assert.format(str, this.fExpected, this.fActual);
        }
        findCommonPrefix();
        findCommonSuffix();
        return Assert.format(str, compactString(this.fExpected), compactString(this.fActual));
    }

    private String compactString(String str) {
        StringBuilder sb = new StringBuilder();
        sb.append(DELTA_START);
        sb.append(str.substring(this.fPrefix, (str.length() - this.fSuffix) + 1));
        sb.append(DELTA_END);
        String sb2 = sb.toString();
        if (this.fPrefix > 0) {
            StringBuilder sb3 = new StringBuilder();
            sb3.append(computeCommonPrefix());
            sb3.append(sb2);
            sb2 = sb3.toString();
        }
        if (this.fSuffix <= 0) {
            return sb2;
        }
        StringBuilder sb4 = new StringBuilder();
        sb4.append(sb2);
        sb4.append(computeCommonSuffix());
        return sb4.toString();
    }

    private void findCommonPrefix() {
        this.fPrefix = 0;
        int min = Math.min(this.fExpected.length(), this.fActual.length());
        while (true) {
            int i = this.fPrefix;
            if (i < min && this.fExpected.charAt(i) == this.fActual.charAt(this.fPrefix)) {
                this.fPrefix++;
            } else {
                return;
            }
        }
    }

    private void findCommonSuffix() {
        int length = this.fExpected.length() - 1;
        int length2 = this.fActual.length() - 1;
        while (true) {
            int i = this.fPrefix;
            if (length2 < i || length < i || this.fExpected.charAt(length) != this.fActual.charAt(length2)) {
                this.fSuffix = this.fExpected.length() - length;
            } else {
                length2--;
                length--;
            }
        }
        this.fSuffix = this.fExpected.length() - length;
    }

    private String computeCommonPrefix() {
        StringBuilder sb = new StringBuilder();
        sb.append(this.fPrefix > this.fContextLength ? ELLIPSIS : "");
        sb.append(this.fExpected.substring(Math.max(0, this.fPrefix - this.fContextLength), this.fPrefix));
        return sb.toString();
    }

    private String computeCommonSuffix() {
        int min = Math.min((this.fExpected.length() - this.fSuffix) + 1 + this.fContextLength, this.fExpected.length());
        StringBuilder sb = new StringBuilder();
        String str = this.fExpected;
        sb.append(str.substring((str.length() - this.fSuffix) + 1, min));
        sb.append((this.fExpected.length() - this.fSuffix) + 1 < this.fExpected.length() - this.fContextLength ? ELLIPSIS : "");
        return sb.toString();
    }

    private boolean areStringsEqual() {
        return this.fExpected.equals(this.fActual);
    }
}
