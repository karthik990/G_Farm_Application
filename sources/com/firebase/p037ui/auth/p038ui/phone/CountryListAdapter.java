package com.firebase.p037ui.auth.p038ui.phone;

import android.content.Context;
import android.widget.ArrayAdapter;
import android.widget.SectionIndexer;
import com.firebase.p037ui.auth.C1330R;
import com.firebase.p037ui.auth.data.model.CountryInfo;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;

/* renamed from: com.firebase.ui.auth.ui.phone.CountryListAdapter */
final class CountryListAdapter extends ArrayAdapter<CountryInfo> implements SectionIndexer {
    private final HashMap<String, Integer> alphaIndex = new LinkedHashMap();
    private final HashMap<String, Integer> countryPosition = new LinkedHashMap();
    private String[] sections;

    public CountryListAdapter(Context context) {
        super(context, C1330R.layout.fui_dgts_country_row, 16908308);
    }

    public void setData(List<CountryInfo> list) {
        int i = 0;
        for (CountryInfo countryInfo : list) {
            String upperCase = countryInfo.getLocale().getDisplayCountry().substring(0, 1).toUpperCase(Locale.getDefault());
            if (!this.alphaIndex.containsKey(upperCase)) {
                this.alphaIndex.put(upperCase, Integer.valueOf(i));
            }
            this.countryPosition.put(countryInfo.getLocale().getDisplayCountry(), Integer.valueOf(i));
            i++;
            add(countryInfo);
        }
        this.sections = new String[this.alphaIndex.size()];
        this.alphaIndex.keySet().toArray(this.sections);
        notifyDataSetChanged();
    }

    public int getCount() {
        return this.countryPosition.size();
    }

    public Object[] getSections() {
        return this.sections;
    }

    public int getPositionForSection(int i) {
        String[] strArr = this.sections;
        if (strArr == null || i <= 0) {
            return 0;
        }
        if (i >= strArr.length) {
            i = strArr.length - 1;
        }
        return ((Integer) this.alphaIndex.get(this.sections[i])).intValue();
    }

    public int getSectionForPosition(int i) {
        if (this.sections == null) {
            return 0;
        }
        for (int i2 = 0; i2 < this.sections.length; i2++) {
            if (getPositionForSection(i2) > i) {
                return i2 - 1;
            }
        }
        return 0;
    }

    public int getPositionForCountry(String str) {
        Integer num = (Integer) this.countryPosition.get(str);
        if (num == null) {
            return 0;
        }
        return num.intValue();
    }
}
