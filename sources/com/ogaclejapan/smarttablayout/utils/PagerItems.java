package com.ogaclejapan.smarttablayout.utils;

import android.content.Context;
import com.ogaclejapan.smarttablayout.utils.PagerItem;
import java.util.ArrayList;

public abstract class PagerItems<T extends PagerItem> extends ArrayList<T> {
    private final Context context;

    protected PagerItems(Context context2) {
        this.context = context2;
    }

    public Context getContext() {
        return this.context;
    }
}
