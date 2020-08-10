package com.nightonke.boommenu;

import android.app.Activity;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.res.Resources;
import android.content.res.Resources.Theme;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.PointF;
import android.graphics.RectF;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.StateListDrawable;
import android.os.Build.VERSION;
import android.util.Log;
import android.util.StateSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.Random;

public class Util {
    private static final int[] colors;
    private static Util ourInstance = new Util();
    private static final ArrayList<Integer> usedColor = new ArrayList<>();

    static Activity scanForActivity(Context context) {
        String str = "scanForActivity: context is null!";
        String str2 = "BoomMenuButton";
        if (context == null) {
            Log.w(str2, str);
            return null;
        } else if (context instanceof Activity) {
            return (Activity) context;
        } else {
            if (context instanceof ContextWrapper) {
                return scanForActivity(((ContextWrapper) context).getBaseContext());
            }
            Log.w(str2, str);
            return null;
        }
    }

    static void setVisibility(int i, View... viewArr) {
        for (View view : viewArr) {
            if (view != null) {
                view.setVisibility(i);
            }
        }
    }

    public static int dp2px(float f) {
        return Math.round(f * (((float) Resources.getSystem().getDisplayMetrics().densityDpi) / 160.0f));
    }

    public static int getColor(View view, int i, Theme theme) {
        if (VERSION.SDK_INT >= 23) {
            return view.getResources().getColor(i, theme);
        }
        return view.getResources().getColor(i);
    }

    public static int getColor(TypedArray typedArray, int i, Theme theme) {
        if (VERSION.SDK_INT >= 23) {
            return typedArray.getResources().getColor(i, theme);
        }
        return typedArray.getResources().getColor(i);
    }

    public static int getColor(View view, int i) {
        return getColor(view, i, (Theme) null);
    }

    public static int getColor(TypedArray typedArray, int i) {
        return getColor(typedArray, i, (Theme) null);
    }

    public static Drawable getSystemDrawable(Context context, int i) {
        TypedArray obtainStyledAttributes = context.obtainStyledAttributes(new int[]{i});
        Drawable drawable = obtainStyledAttributes.getDrawable(0);
        obtainStyledAttributes.recycle();
        return drawable;
    }

    public static Drawable getDrawable(View view, int i, Theme theme) {
        if (VERSION.SDK_INT >= 21) {
            return view.getResources().getDrawable(i, theme);
        }
        return view.getResources().getDrawable(i);
    }

    public static void setDrawable(ImageView imageView, int i, Drawable drawable) {
        if (imageView != null) {
            if (i != 0) {
                imageView.setImageResource(i);
            } else if (drawable != null) {
                imageView.setImageDrawable(drawable);
            }
        }
    }

    public static void setText(TextView textView, int i, String str) {
        if (textView != null) {
            if (i == 0) {
                if (str != null && !str.equals(textView.getText())) {
                    textView.setText(str);
                }
            } else if (!textView.getContext().getResources().getText(i).equals(textView.getText())) {
                textView.setText(i);
            }
        }
    }

    public static void setTextColor(TextView textView, int i, int i2) {
        if (textView != null) {
            if (i == 0) {
                textView.setTextColor(i2);
            } else {
                textView.setTextColor(getColor(textView.getContext(), i));
            }
        }
    }

    public static Drawable getDrawable(View view, int i) {
        if (VERSION.SDK_INT >= 21) {
            return view.getResources().getDrawable(i, null);
        }
        return view.getResources().getDrawable(i);
    }

    public static GradientDrawable getOvalDrawable(View view, int i) {
        GradientDrawable gradientDrawable = (GradientDrawable) getDrawable(view, C4514R.C4516drawable.shape_oval_normal);
        gradientDrawable.setColor(i);
        return gradientDrawable;
    }

    public static BitmapDrawable getOvalBitmapDrawable(View view, int i, int i2) {
        if (i <= 0) {
            return null;
        }
        int i3 = i * 2;
        Bitmap createBitmap = Bitmap.createBitmap(i3, i3, Config.ARGB_8888);
        Canvas canvas = new Canvas(createBitmap);
        Paint paint = new Paint();
        paint.setAntiAlias(true);
        paint.setColor(i2);
        float f = (float) i;
        canvas.drawCircle(f, f, f, paint);
        return new BitmapDrawable(view.getResources(), createBitmap);
    }

    public static GradientDrawable getRectangleDrawable(View view, int i, int i2) {
        GradientDrawable gradientDrawable = (GradientDrawable) getDrawable(view, C4514R.C4516drawable.shape_rectangle_normal);
        gradientDrawable.setCornerRadius((float) i);
        gradientDrawable.setColor(i2);
        return gradientDrawable;
    }

    public static BitmapDrawable getRectangleBitmapDrawable(View view, int i, int i2, int i3, int i4) {
        if (i <= 0 || i2 <= 0) {
            return null;
        }
        Bitmap createBitmap = Bitmap.createBitmap(i, i2, Config.ARGB_8888);
        Canvas canvas = new Canvas(createBitmap);
        Paint paint = new Paint();
        paint.setAntiAlias(true);
        paint.setColor(i4);
        RectF rectF = new RectF(0.0f, 0.0f, (float) i, (float) i2);
        float f = (float) i3;
        canvas.drawRoundRect(rectF, f, f, paint);
        return new BitmapDrawable(view.getResources(), createBitmap);
    }

    public static float distance(Point point, Point point2) {
        return (float) Math.sqrt((double) (((point.x - point2.x) * (point.x - point2.x)) + ((point.y - point2.y) * (point.y - point2.y))));
    }

    public static StateListDrawable getOvalStateListBitmapDrawable(View view, int i, int i2, int i3, int i4) {
        StateListDrawable stateListDrawable = new StateListDrawable();
        stateListDrawable.addState(new int[]{16842919}, getOvalBitmapDrawable(view, i, i3));
        stateListDrawable.addState(new int[]{-16842910}, getOvalBitmapDrawable(view, i, i4));
        stateListDrawable.addState(StateSet.WILD_CARD, getOvalBitmapDrawable(view, i, i2));
        return stateListDrawable;
    }

    public static StateListDrawable getOvalStateListGradientDrawable(View view, int i, int i2, int i3) {
        StateListDrawable stateListDrawable = new StateListDrawable();
        stateListDrawable.addState(new int[]{16842919}, getOvalDrawable(view, i2));
        stateListDrawable.addState(new int[]{-16842910}, getOvalDrawable(view, i3));
        stateListDrawable.addState(StateSet.WILD_CARD, getOvalDrawable(view, i));
        return stateListDrawable;
    }

    public static StateListDrawable getRectangleStateListBitmapDrawable(View view, int i, int i2, int i3, int i4, int i5, int i6) {
        StateListDrawable stateListDrawable = new StateListDrawable();
        stateListDrawable.addState(new int[]{16842919}, getRectangleBitmapDrawable(view, i, i2, i3, i5));
        stateListDrawable.addState(new int[]{-16842910}, getRectangleBitmapDrawable(view, i, i2, i3, i6));
        stateListDrawable.addState(StateSet.WILD_CARD, getRectangleBitmapDrawable(view, i, i2, i3, i4));
        return stateListDrawable;
    }

    public static StateListDrawable getRectangleStateListGradientDrawable(View view, int i, int i2, int i3, int i4) {
        StateListDrawable stateListDrawable = new StateListDrawable();
        stateListDrawable.addState(new int[]{16842919}, getRectangleDrawable(view, i, i3));
        stateListDrawable.addState(new int[]{-16842910}, getRectangleDrawable(view, i, i4));
        stateListDrawable.addState(StateSet.WILD_CARD, getRectangleDrawable(view, i, i2));
        return stateListDrawable;
    }

    public static int getInt(TypedArray typedArray, int i, int i2) {
        return typedArray.getInt(i, typedArray.getResources().getInteger(i2));
    }

    public static boolean getBoolean(TypedArray typedArray, int i, int i2) {
        return typedArray.getBoolean(i, typedArray.getResources().getBoolean(i2));
    }

    public static int getDimenSize(TypedArray typedArray, int i, int i2) {
        return typedArray.getDimensionPixelSize(i, typedArray.getResources().getDimensionPixelSize(i2));
    }

    public static int getDimenOffset(TypedArray typedArray, int i, int i2) {
        return typedArray.getDimensionPixelOffset(i, typedArray.getResources().getDimensionPixelOffset(i2));
    }

    public static int getColor(TypedArray typedArray, int i, int i2) {
        return typedArray.getColor(i, getColor(typedArray, i2));
    }

    public static int getColor(Context context, int i) {
        if (VERSION.SDK_INT >= 23) {
            return context.getResources().getColor(i, null);
        }
        return context.getResources().getColor(i);
    }

    public static int getColor(Context context, int i, int i2) {
        return i == 0 ? i2 : getColor(context, i);
    }

    public static void setDrawable(View view, Drawable drawable) {
        if (VERSION.SDK_INT >= 16) {
            view.setBackground(drawable);
        } else {
            view.setBackgroundDrawable(drawable);
        }
    }

    public static int getDarkerColor(int i) {
        float[] fArr = new float[3];
        Color.colorToHSV(i, fArr);
        fArr[2] = fArr[2] * 0.9f;
        return Color.HSVToColor(fArr);
    }

    public static int getLighterColor(int i) {
        float[] fArr = new float[3];
        Color.colorToHSV(i, fArr);
        fArr[2] = fArr[2] * 1.1f;
        return Color.HSVToColor(fArr);
    }

    static {
        String str = "#009688";
        colors = new int[]{Color.parseColor("#F44336"), Color.parseColor("#E91E63"), Color.parseColor("#9C27B0"), Color.parseColor("#673AB7"), Color.parseColor("#3F51B5"), Color.parseColor("#2196F3"), Color.parseColor("#03A9F4"), Color.parseColor("#00BCD4"), Color.parseColor(str), Color.parseColor("#4CAF50"), Color.parseColor(str), Color.parseColor("#CDDC39"), Color.parseColor("#FFEB3B"), Color.parseColor("#FF9800"), Color.parseColor("#FF5722"), Color.parseColor("#795548"), Color.parseColor("#9E9E9E"), Color.parseColor("#607D8B")};
    }

    public static int getColor() {
        int nextInt;
        Random random = new Random();
        do {
            nextInt = random.nextInt(colors.length);
        } while (usedColor.contains(Integer.valueOf(nextInt)));
        usedColor.add(Integer.valueOf(nextInt));
        while (usedColor.size() > 6) {
            usedColor.remove(0);
        }
        return colors[nextInt];
    }

    public static boolean pointInView(PointF pointF, View view) {
        return ((float) view.getLeft()) <= pointF.x && pointF.x <= ((float) view.getRight()) && ((float) view.getTop()) <= pointF.y && pointF.y <= ((float) view.getBottom());
    }

    public static Util getInstance() {
        return ourInstance;
    }

    private Util() {
    }
}
