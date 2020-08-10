package com.infideap.drawerbehavior;

import android.app.Activity;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.res.Configuration;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build.VERSION;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import android.widget.FrameLayout;
import androidx.cardview.widget.CardView;
import androidx.core.graphics.ColorUtils;
import androidx.core.view.GravityCompat;
import androidx.core.view.ViewCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.drawerlayout.widget.DrawerLayout.DrawerListener;
import com.google.android.material.navigation.NavigationView;
import java.util.HashMap;

public class AdvanceDrawerLayout extends DrawerLayout {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    private static final String TAG = AdvanceDrawerLayout.class.getSimpleName();
    private float contrastThreshold = 3.0f;
    /* access modifiers changed from: private */
    public float defaultDrawerElevation;
    private boolean defaultFitsSystemWindows;
    /* access modifiers changed from: private */
    public int defaultScrimColor = -1728053248;
    public View drawerView;
    private FrameLayout frameLayout;
    HashMap<Integer, Setting> settings = new HashMap<>();
    private int statusBarColor;

    class Setting {
        float drawerElevation = AdvanceDrawerLayout.this.defaultDrawerElevation;
        float elevation = 0.0f;
        boolean fitsSystemWindows;
        float percentage = 1.0f;
        float radius;
        int scrimColor = AdvanceDrawerLayout.this.defaultScrimColor;

        Setting() {
        }

        public float getDrawerElevation() {
            return this.drawerElevation;
        }

        public float getElevation() {
            return this.elevation;
        }

        public float getPercentage() {
            return this.percentage;
        }

        public float getRadius() {
            return this.radius;
        }

        public int getScrimColor() {
            return this.scrimColor;
        }
    }

    public AdvanceDrawerLayout(Context context) {
        super(context);
        init(context, null, 0);
    }

    public AdvanceDrawerLayout(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        init(context, attributeSet, 0);
    }

    public AdvanceDrawerLayout(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        init(context, attributeSet, i);
    }

    private void init(Context context, AttributeSet attributeSet, int i) {
        this.defaultDrawerElevation = getDrawerElevation();
        if (VERSION.SDK_INT >= 16) {
            this.defaultFitsSystemWindows = getFitsSystemWindows();
        }
        if (!isInEditMode() && VERSION.SDK_INT >= 21) {
            this.statusBarColor = getActivity().getWindow().getStatusBarColor();
        }
        addDrawerListener(new DrawerListener() {
            public void onDrawerClosed(View view) {
            }

            public void onDrawerOpened(View view) {
            }

            public void onDrawerStateChanged(int i) {
            }

            public void onDrawerSlide(View view, float f) {
                AdvanceDrawerLayout advanceDrawerLayout = AdvanceDrawerLayout.this;
                advanceDrawerLayout.drawerView = view;
                advanceDrawerLayout.updateSlideOffset(view, f);
            }
        });
        this.frameLayout = new FrameLayout(context);
        this.frameLayout.setPadding(0, 0, 0, 0);
        super.addView(this.frameLayout);
    }

    public void addView(View view, LayoutParams layoutParams) {
        view.setLayoutParams(layoutParams);
        addView(view);
    }

    public void addView(View view) {
        if (view instanceof NavigationView) {
            super.addView(view);
            return;
        }
        CardView cardView = new CardView(getContext());
        cardView.setRadius(0.0f);
        cardView.addView(view);
        cardView.setCardElevation(0.0f);
        if (VERSION.SDK_INT < 21) {
            cardView.setContentPadding(-6, -9, -6, -9);
        }
        this.frameLayout.addView(cardView);
    }

    public void setViewScale(int i, float f) {
        Setting setting;
        int drawerViewAbsoluteGravity = getDrawerViewAbsoluteGravity(i);
        if (!this.settings.containsKey(Integer.valueOf(drawerViewAbsoluteGravity))) {
            setting = createSetting();
            this.settings.put(Integer.valueOf(drawerViewAbsoluteGravity), setting);
        } else {
            setting = (Setting) this.settings.get(Integer.valueOf(drawerViewAbsoluteGravity));
        }
        setting.percentage = f;
        if (VERSION.SDK_INT >= 14 && f < 1.0f) {
            setStatusBarBackground((Drawable) null);
            setSystemUiVisibility(0);
        }
        setting.scrimColor = 0;
        setting.drawerElevation = 0.0f;
    }

    public void setViewElevation(int i, float f) {
        Setting setting;
        int drawerViewAbsoluteGravity = getDrawerViewAbsoluteGravity(i);
        if (!this.settings.containsKey(Integer.valueOf(drawerViewAbsoluteGravity))) {
            setting = createSetting();
            this.settings.put(Integer.valueOf(drawerViewAbsoluteGravity), setting);
        } else {
            setting = (Setting) this.settings.get(Integer.valueOf(drawerViewAbsoluteGravity));
        }
        setting.scrimColor = 0;
        setting.drawerElevation = 0.0f;
        setting.elevation = f;
    }

    public void setViewScrimColor(int i, int i2) {
        Setting setting;
        int drawerViewAbsoluteGravity = getDrawerViewAbsoluteGravity(i);
        if (!this.settings.containsKey(Integer.valueOf(drawerViewAbsoluteGravity))) {
            setting = createSetting();
            this.settings.put(Integer.valueOf(drawerViewAbsoluteGravity), setting);
        } else {
            setting = (Setting) this.settings.get(Integer.valueOf(drawerViewAbsoluteGravity));
        }
        setting.scrimColor = i2;
    }

    public void setDrawerElevation(int i, float f) {
        Setting setting;
        int drawerViewAbsoluteGravity = getDrawerViewAbsoluteGravity(i);
        if (!this.settings.containsKey(Integer.valueOf(drawerViewAbsoluteGravity))) {
            setting = createSetting();
            this.settings.put(Integer.valueOf(drawerViewAbsoluteGravity), setting);
        } else {
            setting = (Setting) this.settings.get(Integer.valueOf(drawerViewAbsoluteGravity));
        }
        setting.elevation = 0.0f;
        setting.drawerElevation = f;
    }

    public void setRadius(int i, float f) {
        Setting setting;
        int drawerViewAbsoluteGravity = getDrawerViewAbsoluteGravity(i);
        if (!this.settings.containsKey(Integer.valueOf(drawerViewAbsoluteGravity))) {
            setting = createSetting();
            this.settings.put(Integer.valueOf(drawerViewAbsoluteGravity), setting);
        } else {
            setting = (Setting) this.settings.get(Integer.valueOf(drawerViewAbsoluteGravity));
        }
        setting.radius = f;
    }

    public Setting getSetting(int i) {
        return (Setting) this.settings.get(Integer.valueOf(getDrawerViewAbsoluteGravity(i)));
    }

    public void setDrawerElevation(float f) {
        this.defaultDrawerElevation = f;
        super.setDrawerElevation(f);
    }

    public void setScrimColor(int i) {
        this.defaultScrimColor = i;
        super.setScrimColor(i);
    }

    public void useCustomBehavior(int i) {
        int drawerViewAbsoluteGravity = getDrawerViewAbsoluteGravity(i);
        if (!this.settings.containsKey(Integer.valueOf(drawerViewAbsoluteGravity))) {
            this.settings.put(Integer.valueOf(drawerViewAbsoluteGravity), createSetting());
        }
    }

    public void removeCustomBehavior(int i) {
        int drawerViewAbsoluteGravity = getDrawerViewAbsoluteGravity(i);
        if (this.settings.containsKey(Integer.valueOf(drawerViewAbsoluteGravity))) {
            this.settings.remove(Integer.valueOf(drawerViewAbsoluteGravity));
        }
    }

    public void openDrawer(final View view, boolean z) {
        super.openDrawer(view, z);
        post(new Runnable() {
            public void run() {
                AdvanceDrawerLayout advanceDrawerLayout = AdvanceDrawerLayout.this;
                View view = view;
                advanceDrawerLayout.updateSlideOffset(view, advanceDrawerLayout.isDrawerOpen(view) ? 1.0f : 0.0f);
            }
        });
    }

    /* access modifiers changed from: private */
    public void updateSlideOffset(View view, float f) {
        boolean z;
        float f2 = f;
        int drawerViewAbsoluteGravity = getDrawerViewAbsoluteGravity((int) GravityCompat.START);
        int drawerViewAbsoluteGravity2 = getDrawerViewAbsoluteGravity(view);
        Window window = getActivity().getWindow();
        if (VERSION.SDK_INT >= 17) {
            z = getLayoutDirection() == 1 || window.getDecorView().getLayoutDirection() == 1 || getResources().getConfiguration().getLayoutDirection() == 1;
        } else {
            z = false;
        }
        for (int i = 0; i < this.frameLayout.getChildCount(); i++) {
            CardView cardView = (CardView) this.frameLayout.getChildAt(i);
            Setting setting = (Setting) this.settings.get(Integer.valueOf(drawerViewAbsoluteGravity2));
            if (setting != null) {
                if (VERSION.SDK_INT >= 21 && ((double) setting.percentage) < 1.0d && (view.getBackground() instanceof ColorDrawable)) {
                    window.setStatusBarColor(ColorUtils.setAlphaComponent(this.statusBarColor, (int) (255.0f - (f2 * 255.0f))));
                    int color = ((ColorDrawable) view.getBackground()).getColor();
                    window.getDecorView().setBackgroundColor(color);
                    if (VERSION.SDK_INT >= 23) {
                        setSystemUiVisibility((ColorUtils.calculateContrast(-1, color) >= ((double) this.contrastThreshold) || ((double) f2) <= 0.4d) ? 0 : 8192);
                    }
                }
                cardView.setRadius((float) ((int) (setting.radius * f2)));
                super.setScrimColor(setting.scrimColor);
                super.setDrawerElevation(setting.drawerElevation);
                ViewCompat.setScaleY(cardView, 1.0f - ((1.0f - setting.percentage) * f2));
                cardView.setCardElevation(setting.elevation * f2);
                float f3 = setting.elevation;
                boolean z2 = !z ? drawerViewAbsoluteGravity2 == drawerViewAbsoluteGravity : drawerViewAbsoluteGravity2 != drawerViewAbsoluteGravity;
                updateSlideOffset(cardView, setting, z2 ? ((float) view.getWidth()) + f3 : ((float) (-view.getWidth())) - f3, f, z2);
            } else {
                super.setScrimColor(this.defaultScrimColor);
                super.setDrawerElevation(this.defaultDrawerElevation);
            }
        }
    }

    public void setContrastThreshold(float f) {
        this.contrastThreshold = f;
    }

    /* access modifiers changed from: 0000 */
    public Activity getActivity() {
        return getActivity(getContext());
    }

    /* access modifiers changed from: 0000 */
    public Activity getActivity(Context context) {
        if (context == null) {
            return null;
        }
        if (context instanceof Activity) {
            return (Activity) context;
        }
        if (context instanceof ContextWrapper) {
            return getActivity(((ContextWrapper) context).getBaseContext());
        }
        return null;
    }

    /* access modifiers changed from: 0000 */
    public void updateSlideOffset(CardView cardView, Setting setting, float f, float f2, boolean z) {
        ViewCompat.setX(cardView, f * f2);
    }

    /* access modifiers changed from: protected */
    public void onConfigurationChanged(Configuration configuration) {
        super.onConfigurationChanged(configuration);
        View view = this.drawerView;
        if (view != null) {
            updateSlideOffset(view, isDrawerOpen(view) ? 1.0f : 0.0f);
        }
    }

    /* access modifiers changed from: 0000 */
    public int getDrawerViewAbsoluteGravity(int i) {
        return GravityCompat.getAbsoluteGravity(i, ViewCompat.getLayoutDirection(this)) & 7;
    }

    /* access modifiers changed from: 0000 */
    public int getDrawerViewAbsoluteGravity(View view) {
        return getDrawerViewAbsoluteGravity(((DrawerLayout.LayoutParams) view.getLayoutParams()).gravity);
    }

    /* access modifiers changed from: 0000 */
    public Setting createSetting() {
        return new Setting();
    }
}
