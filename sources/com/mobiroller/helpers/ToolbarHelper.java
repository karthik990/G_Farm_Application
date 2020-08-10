package com.mobiroller.helpers;

import android.content.Context;
import android.graphics.Color;
import android.os.Build.VERSION;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import com.mobiroller.mobi942763453128.R;
import com.mobiroller.util.ColorUtil;
import com.mobiroller.views.theme.Theme;

public class ToolbarHelper {
    public ToolbarHelper(SharedPrefHelper sharedPrefHelper) {
    }

    public ToolbarHelper() {
    }

    public static Toolbar setToolbar(final AppCompatActivity appCompatActivity, Toolbar toolbar) {
        appCompatActivity.setSupportActionBar(toolbar);
        toolbar.setNavigationIcon((int) R.drawable.ic_arrow_back_white_24dp);
        if (VERSION.SDK_INT >= 19) {
            toolbar.getNavigationIcon().setAutoMirrored(true);
        }
        String str = "";
        toolbar.setTitle((CharSequence) str);
        toolbar.setNavigationOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                appCompatActivity.finish();
                try {
                    View currentFocus = appCompatActivity.getCurrentFocus();
                    if (currentFocus != null) {
                        ((InputMethodManager) appCompatActivity.getSystemService("input_method")).hideSoftInputFromWindow(currentFocus.getWindowToken(), 0);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        appCompatActivity.getSupportActionBar().setDisplayShowHomeEnabled(true);
        appCompatActivity.getSupportActionBar().setTitle((CharSequence) str);
        if (VERSION.SDK_INT >= 21) {
            appCompatActivity.getWindow().addFlags(Integer.MIN_VALUE);
            appCompatActivity.getWindow().clearFlags(1024);
            appCompatActivity.getWindow().clearFlags(67108864);
            appCompatActivity.getWindow().setStatusBarColor(getStatusBarColor());
        }
        return toolbar;
    }

    private Toolbar setToolbarPermission(final AppCompatActivity appCompatActivity, Toolbar toolbar) {
        appCompatActivity.setSupportActionBar(toolbar);
        toolbar.setBackgroundColor(Color.parseColor("#517fff"));
        toolbar.setNavigationIcon((int) R.drawable.ic_arrow_back_white_24dp);
        toolbar.setTitleTextColor(ContextCompat.getColor(appCompatActivity, R.color.white));
        String str = "";
        toolbar.setTitle((CharSequence) str);
        if (VERSION.SDK_INT >= 19) {
            toolbar.getNavigationIcon().setAutoMirrored(true);
        }
        toolbar.setNavigationOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                appCompatActivity.finish();
            }
        });
        appCompatActivity.getSupportActionBar().setDisplayShowHomeEnabled(true);
        appCompatActivity.getSupportActionBar().setTitle((CharSequence) str);
        if (VERSION.SDK_INT >= 21) {
            appCompatActivity.getWindow().addFlags(Integer.MIN_VALUE);
            appCompatActivity.getWindow().clearFlags(1024);
            appCompatActivity.getWindow().clearFlags(67108864);
            appCompatActivity.getWindow().setStatusBarColor(getStatusBarColorPermission());
        }
        return toolbar;
    }

    public void setToolbarTitle(AppCompatActivity appCompatActivity, String str) {
        try {
            setToolbar(appCompatActivity, (Toolbar) appCompatActivity.findViewById(R.id.toolbar_top));
            appCompatActivity.getSupportActionBar().setTitle((CharSequence) str);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setToolbarTitlePermission(AppCompatActivity appCompatActivity, String str) {
        try {
            setToolbarPermission(appCompatActivity, (Toolbar) appCompatActivity.findViewById(R.id.toolbar_top));
            appCompatActivity.getSupportActionBar().setTitle((CharSequence) str);
        } catch (Exception unused) {
        }
    }

    public static int getStatusBarColor() {
        int i = Theme.primaryColor;
        return Color.argb(Color.alpha(i), Math.min(Math.round(((float) Color.red(i)) * 0.9f), 255), Math.min(Math.round(((float) Color.green(i)) * 0.9f), 255), Math.min(Math.round(((float) Color.blue(i)) * 0.9f), 255));
    }

    public static int getStatusBarColor(Context context) {
        int actionBarColor = UtilManager.sharedPrefHelper().getActionBarColor();
        return Color.argb(Color.alpha(actionBarColor), Math.min(Math.round(((float) Color.red(actionBarColor)) * 0.9f), 255), Math.min(Math.round(((float) Color.green(actionBarColor)) * 0.9f), 255), Math.min(Math.round(((float) Color.blue(actionBarColor)) * 0.9f), 255));
    }

    private int getStatusBarColorPermission() {
        int parseColor = Color.parseColor("#517fff");
        return Color.argb(Color.alpha(parseColor), Math.min(Math.round(((float) Color.red(parseColor)) * 0.9f), 255), Math.min(Math.round(((float) Color.green(parseColor)) * 0.9f), 255), Math.min(Math.round(((float) Color.blue(parseColor)) * 0.9f), 255));
    }

    public void setStatusBar(AppCompatActivity appCompatActivity) {
        if (VERSION.SDK_INT >= 21) {
            Window window = appCompatActivity.getWindow();
            window.addFlags(Integer.MIN_VALUE);
            window.clearFlags(1024);
            window.clearFlags(67108864);
            window.setStatusBarColor(getStatusBarColor());
        }
        if (VERSION.SDK_INT >= 23) {
            View decorView = appCompatActivity.getWindow().getDecorView();
            if ((ColorUtil.isColorDark(Theme.primaryColor) ? (char) 65535 : 0) == 65535) {
                decorView.setSystemUiVisibility(decorView.getSystemUiVisibility() & -8193);
            } else {
                decorView.setSystemUiVisibility(8192);
            }
        }
    }

    public void setStatusBarColor(AppCompatActivity appCompatActivity) {
        if (VERSION.SDK_INT >= 21) {
            appCompatActivity.getWindow().setStatusBarColor(getStatusBarColor());
        }
        if (VERSION.SDK_INT >= 23) {
            View decorView = appCompatActivity.getWindow().getDecorView();
            if ((ColorUtil.isColorDark(Theme.primaryColor) ? (char) 65535 : 0) == 65535) {
                decorView.setSystemUiVisibility(decorView.getSystemUiVisibility() & -8193);
            } else {
                decorView.setSystemUiVisibility(8192);
            }
        }
    }

    public void setStatusBarTransparent(AppCompatActivity appCompatActivity) {
        if (VERSION.SDK_INT >= 21) {
            appCompatActivity.getWindow().addFlags(67108864);
        }
    }
}
