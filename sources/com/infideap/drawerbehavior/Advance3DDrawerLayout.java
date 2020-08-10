package com.infideap.drawerbehavior;

import android.content.Context;
import android.util.AttributeSet;
import androidx.cardview.widget.CardView;
import androidx.core.view.ViewCompat;

public class Advance3DDrawerLayout extends AdvanceDrawerLayout {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    private static final String TAG = Advance3DDrawerLayout.class.getSimpleName();

    class Setting extends Setting {
        float degree;

        Setting() {
            super();
        }
    }

    public Advance3DDrawerLayout(Context context) {
        super(context);
    }

    public Advance3DDrawerLayout(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    public Advance3DDrawerLayout(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
    }

    /* access modifiers changed from: 0000 */
    public void updateSlideOffset(CardView cardView, Setting setting, float f, float f2, boolean z) {
        updateSlideOffset(cardView, (Setting) setting, f, f2, z);
    }

    /* access modifiers changed from: 0000 */
    public void updateSlideOffset(CardView cardView, Setting setting, float f, float f2, boolean z) {
        if (setting.degree > 0.0f) {
            ViewCompat.setX(cardView, (f * f2) - ((((float) (cardView.getWidth() / 2)) * (setting.degree / 90.0f)) * f2));
            ViewCompat.setRotationY(cardView, ((float) (z ? -1 : 1)) * setting.degree * f2);
            return;
        }
        super.updateSlideOffset(cardView, setting, f, f2, z);
    }

    /* access modifiers changed from: 0000 */
    public Setting createSetting() {
        return new Setting();
    }

    public void setViewRotation(int i, float f) {
        Setting setting;
        int drawerViewAbsoluteGravity = getDrawerViewAbsoluteGravity(i);
        if (!this.settings.containsKey(Integer.valueOf(drawerViewAbsoluteGravity))) {
            setting = (Setting) createSetting();
            this.settings.put(Integer.valueOf(drawerViewAbsoluteGravity), setting);
        } else {
            setting = (Setting) this.settings.get(Integer.valueOf(drawerViewAbsoluteGravity));
        }
        float f2 = 45.0f;
        if (f <= 45.0f) {
            f2 = f;
        }
        setting.degree = f2;
        setting.scrimColor = 0;
        setting.drawerElevation = 0.0f;
    }
}
