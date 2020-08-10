package com.mobiroller.views.twowayview;

import android.content.Context;
import android.content.res.TypedArray;
import android.text.TextUtils;
import android.util.AttributeSet;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.RecyclerView.LayoutManager;
import com.mobiroller.preview.C4290R;
import com.mobiroller.views.twowayview.TwoWayLayoutManager.Orientation;
import java.lang.reflect.Constructor;

public class TwoWayView extends RecyclerView {
    private static final String LOGTAG = "TwoWayView";
    private static final Class<?>[] sConstructorSignature = {Context.class, AttributeSet.class};
    final Object[] sConstructorArgs;

    public TwoWayView(Context context) {
        this(context, null);
    }

    public TwoWayView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public TwoWayView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.sConstructorArgs = new Object[2];
        TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, C4290R.styleable.twowayview_TwoWayView, i, 0);
        String string = obtainStyledAttributes.getString(0);
        if (!TextUtils.isEmpty(string)) {
            loadLayoutManagerFromName(context, attributeSet, string);
        }
        obtainStyledAttributes.recycle();
    }

    private void loadLayoutManagerFromName(Context context, AttributeSet attributeSet, String str) {
        try {
            int indexOf = str.indexOf(46);
            String str2 = ".";
            if (indexOf == -1) {
                StringBuilder sb = new StringBuilder();
                sb.append(getClass().getCanonicalName().substring(0, getClass().getCanonicalName().lastIndexOf(str2)));
                sb.append(str2);
                sb.append(str);
                str = sb.toString();
            } else if (indexOf == 0) {
                String packageName = context.getPackageName();
                StringBuilder sb2 = new StringBuilder();
                sb2.append(packageName);
                sb2.append(str2);
                sb2.append(str);
                str = sb2.toString();
            }
            Constructor constructor = context.getClassLoader().loadClass(str).asSubclass(TwoWayLayoutManager.class).getConstructor(sConstructorSignature);
            this.sConstructorArgs[0] = context;
            this.sConstructorArgs[1] = attributeSet;
            setLayoutManager((LayoutManager) constructor.newInstance(this.sConstructorArgs));
        } catch (Exception e) {
            StringBuilder sb3 = new StringBuilder();
            sb3.append("Could not load TwoWayLayoutManager from class: ");
            sb3.append(str);
            throw new IllegalStateException(sb3.toString(), e);
        }
    }

    public void setLayoutManager(LayoutManager layoutManager) {
        if (layoutManager instanceof TwoWayLayoutManager) {
            super.setLayoutManager(layoutManager);
            return;
        }
        throw new IllegalArgumentException("TwoWayView can only use TwoWayLayoutManager subclasses as its layout manager");
    }

    public Orientation getOrientation() {
        return ((TwoWayLayoutManager) getLayoutManager()).getOrientation();
    }

    public void setOrientation(Orientation orientation) {
        ((TwoWayLayoutManager) getLayoutManager()).setOrientation(orientation);
    }

    public int getFirstVisiblePosition() {
        return ((TwoWayLayoutManager) getLayoutManager()).getFirstVisiblePosition();
    }

    public int getLastVisiblePosition() {
        return ((TwoWayLayoutManager) getLayoutManager()).getLastVisiblePosition();
    }
}
