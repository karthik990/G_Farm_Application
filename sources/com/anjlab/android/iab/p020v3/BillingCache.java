package com.anjlab.android.iab.p020v3;

import android.content.Context;
import android.text.TextUtils;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Pattern;

/* renamed from: com.anjlab.android.iab.v3.BillingCache */
class BillingCache extends BillingBase {
    private static final String ENTRY_DELIMITER = "#####";
    private static final String LINE_DELIMITER = ">>>>>";
    private static final String VERSION_KEY = ".version";
    private String cacheKey;
    private HashMap<String, PurchaseInfo> data = new HashMap<>();
    private String version;

    BillingCache(Context context, String str) {
        super(context);
        this.cacheKey = str;
        load();
    }

    private String getPreferencesCacheKey() {
        StringBuilder sb = new StringBuilder();
        sb.append(getPreferencesBaseKey());
        sb.append(this.cacheKey);
        return sb.toString();
    }

    private String getPreferencesVersionKey() {
        StringBuilder sb = new StringBuilder();
        sb.append(getPreferencesCacheKey());
        sb.append(VERSION_KEY);
        return sb.toString();
    }

    private void load() {
        String[] split;
        for (String str : loadString(getPreferencesCacheKey(), "").split(Pattern.quote(ENTRY_DELIMITER))) {
            if (!TextUtils.isEmpty(str)) {
                String[] split2 = str.split(Pattern.quote(LINE_DELIMITER));
                if (split2.length > 2) {
                    this.data.put(split2[0], new PurchaseInfo(split2[1], split2[2]));
                } else if (split2.length > 1) {
                    this.data.put(split2[0], new PurchaseInfo(split2[1], null));
                }
            }
        }
        this.version = getCurrentVersion();
    }

    private void flush() {
        ArrayList arrayList = new ArrayList();
        for (String str : this.data.keySet()) {
            PurchaseInfo purchaseInfo = (PurchaseInfo) this.data.get(str);
            StringBuilder sb = new StringBuilder();
            sb.append(str);
            String str2 = LINE_DELIMITER;
            sb.append(str2);
            sb.append(purchaseInfo.responseData);
            sb.append(str2);
            sb.append(purchaseInfo.signature);
            arrayList.add(sb.toString());
        }
        saveString(getPreferencesCacheKey(), TextUtils.join(ENTRY_DELIMITER, arrayList));
        this.version = Long.toString(new Date().getTime());
        saveString(getPreferencesVersionKey(), this.version);
    }

    /* access modifiers changed from: 0000 */
    public boolean includesProduct(String str) {
        reloadDataIfNeeded();
        return this.data.containsKey(str);
    }

    /* access modifiers changed from: 0000 */
    public PurchaseInfo getDetails(String str) {
        reloadDataIfNeeded();
        if (this.data.containsKey(str)) {
            return (PurchaseInfo) this.data.get(str);
        }
        return null;
    }

    /* access modifiers changed from: 0000 */
    public void put(String str, String str2, String str3) {
        reloadDataIfNeeded();
        if (!this.data.containsKey(str)) {
            this.data.put(str, new PurchaseInfo(str2, str3));
            flush();
        }
    }

    /* access modifiers changed from: 0000 */
    public void remove(String str) {
        reloadDataIfNeeded();
        if (this.data.containsKey(str)) {
            this.data.remove(str);
            flush();
        }
    }

    /* access modifiers changed from: 0000 */
    public void clear() {
        reloadDataIfNeeded();
        this.data.clear();
        flush();
    }

    private String getCurrentVersion() {
        return loadString(getPreferencesVersionKey(), "0");
    }

    private void reloadDataIfNeeded() {
        if (!this.version.equalsIgnoreCase(getCurrentVersion())) {
            this.data.clear();
            load();
        }
    }

    /* access modifiers changed from: 0000 */
    public List<String> getContents() {
        return new ArrayList(this.data.keySet());
    }

    public String toString() {
        return TextUtils.join(", ", this.data.keySet());
    }
}
