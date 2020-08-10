package com.startapp.android.publish.ads.splash;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.Typeface;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.GradientDrawable.Orientation;
import android.view.View;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.TextView;
import com.startapp.android.publish.adsCommon.AdsConstants;
import com.startapp.android.publish.adsCommon.Utils.C4945h;
import com.startapp.android.publish.adsCommon.p082f.C5015d;
import com.startapp.android.publish.adsCommon.p082f.C5017f;
import org.objectweb.asm.Opcodes;

/* renamed from: com.startapp.android.publish.ads.splash.i */
/* compiled from: StartAppSDK */
public class C4847i {
    /* renamed from: a */
    public static View m2542a(Context context, SplashConfig splashConfig) {
        switch (splashConfig.getTheme()) {
            case DEEP_BLUE:
                return m2548g(context, splashConfig);
            case SKY:
                return m2547f(context, splashConfig);
            case ASHEN_SKY:
                return m2546e(context, splashConfig);
            case BLAZE:
                return m2545d(context, splashConfig);
            case GLOOMY:
                return m2544c(context, splashConfig);
            case OCEAN:
                return m2543b(context, splashConfig);
            default:
                return null;
        }
    }

    /* renamed from: b */
    private static View m2543b(Context context, SplashConfig splashConfig) {
        View h = m2549h(context, splashConfig);
        h.setBackgroundDrawable(new GradientDrawable(Orientation.TOP_BOTTOM, new int[]{-14451558, -7876130}));
        ((TextView) h.findViewById(100)).setTextColor(Color.rgb(6, 61, 82));
        ((TextView) h.findViewById(105)).setTextColor(Color.rgb(6, 61, 82));
        return h;
    }

    /* renamed from: c */
    private static View m2544c(Context context, SplashConfig splashConfig) {
        View h = m2549h(context, splashConfig);
        h.setBackgroundColor(Color.rgb(47, 53, 63));
        ((TextView) h.findViewById(100)).setTextColor(Color.rgb(51, Opcodes.PUTFIELD, 229));
        ((TextView) h.findViewById(105)).setTextColor(Color.rgb(122, 130, 139));
        return h;
    }

    /* renamed from: d */
    private static View m2545d(Context context, SplashConfig splashConfig) {
        View h = m2549h(context, splashConfig);
        int i = context.getResources().getDisplayMetrics().widthPixels;
        GradientDrawable gradientDrawable = new GradientDrawable(Orientation.BL_TR, new int[]{-92376, -40960});
        gradientDrawable.setGradientType(1);
        gradientDrawable.setGradientRadius((float) (i / 2));
        h.setBackgroundDrawable(gradientDrawable);
        ((TextView) h.findViewById(100)).setTextColor(Color.rgb(255, 255, 255));
        ((TextView) h.findViewById(105)).setTextColor(Color.rgb(255, Opcodes.IFNULL, Opcodes.DCMPL));
        return h;
    }

    /* renamed from: e */
    private static View m2546e(Context context, SplashConfig splashConfig) {
        View h = m2549h(context, splashConfig);
        h.setBackgroundDrawable(new GradientDrawable(Orientation.TOP_BOTTOM, new int[]{-3881788, -1}));
        ((TextView) h.findViewById(100)).setTextColor(Color.rgb(51, 51, 51));
        ((TextView) h.findViewById(105)).setTextColor(Color.rgb(Opcodes.IFEQ, Opcodes.IFEQ, Opcodes.IFEQ));
        return h;
    }

    /* renamed from: f */
    private static View m2547f(Context context, SplashConfig splashConfig) {
        View h = m2549h(context, splashConfig);
        int i = context.getResources().getDisplayMetrics().widthPixels;
        GradientDrawable gradientDrawable = new GradientDrawable(Orientation.BL_TR, new int[]{-921103, -6040347});
        gradientDrawable.setGradientType(1);
        gradientDrawable.setGradientRadius((float) (i / 2));
        h.setBackgroundDrawable(gradientDrawable);
        ((TextView) h.findViewById(100)).setTextColor(Color.rgb(51, 51, 51));
        ((TextView) h.findViewById(105)).setTextColor(Color.rgb(Opcodes.IF_ICMPGE, 172, Opcodes.DRETURN));
        return h;
    }

    /* renamed from: g */
    private static View m2548g(Context context, SplashConfig splashConfig) {
        View h = m2549h(context, splashConfig);
        h.setBackgroundDrawable(new GradientDrawable(Orientation.TOP_BOTTOM, new int[]{-16356182, -15029533, -16356182}));
        ((TextView) h.findViewById(100)).setTextColor(Color.rgb(255, 255, 255));
        ((TextView) h.findViewById(105)).setTextColor(Color.rgb(208, 210, 210));
        return h;
    }

    /* renamed from: h */
    private static View m2549h(Context context, SplashConfig splashConfig) {
        int i;
        int i2;
        int i3;
        int i4;
        Context context2 = context;
        RelativeLayout relativeLayout = new RelativeLayout(context2);
        relativeLayout.setId(AdsConstants.SPLASH_NATIVE_MAIN_LAYOUT_ID);
        relativeLayout.setBackgroundColor(-1);
        RelativeLayout relativeLayout2 = new RelativeLayout(context2);
        LayoutParams layoutParams = new LayoutParams(-2, -2);
        layoutParams.addRule(13);
        relativeLayout.addView(relativeLayout2, layoutParams);
        Point point = new Point(C4945h.m2891a(context2, (int) Opcodes.FCMPG), C4945h.m2891a(context2, 28));
        if (splashConfig.getOrientation() == SplashConfig.Orientation.PORTRAIT) {
            i4 = C4945h.m2891a(context2, 5);
            i3 = C4945h.m2891a(context2, 8);
            i2 = C4945h.m2891a(context2, 75);
            i = C4945h.m2891a(context2, 130);
        } else {
            i4 = C4945h.m2891a(context2, 5);
            i3 = C4945h.m2891a(context2, 8);
            i2 = C4945h.m2891a(context2, 40);
            i = C4945h.m2891a(context2, 100);
        }
        ImageView imageView = new ImageView(context2);
        imageView.setImageDrawable(splashConfig.getLogo());
        imageView.setId(101);
        LayoutParams layoutParams2 = new LayoutParams(i, i);
        layoutParams2.addRule(10);
        layoutParams2.addRule(14);
        layoutParams2.setMargins(0, 0, 0, i4);
        relativeLayout2.addView(imageView, layoutParams2);
        TextView textView = new TextView(context2);
        textView.setTypeface(Typeface.DEFAULT);
        textView.setText(splashConfig.getAppName());
        textView.setId(100);
        textView.setTextSize(1, 26.0f);
        textView.setTextColor(Color.rgb(255, 255, 255));
        textView.setGravity(17);
        LayoutParams layoutParams3 = new LayoutParams(-2, -2);
        layoutParams3.addRule(14);
        layoutParams3.addRule(3, 101);
        layoutParams3.setMargins(0, 0, 0, i2);
        relativeLayout2.addView(textView, layoutParams3);
        try {
            WebView webView = new WebView(context2);
            webView.setId(102);
            LayoutParams layoutParams4 = new LayoutParams(point.x, point.y);
            layoutParams4.addRule(14);
            layoutParams4.addRule(3, 100);
            layoutParams4.setMargins(0, 0, 0, i3);
            webView.setBackgroundColor(Color.argb(0, 0, 0, 0));
            webView.setVerticalScrollBarEnabled(false);
            webView.setHorizontalScrollBarEnabled(false);
            webView.loadDataWithBaseURL(null, "<html>\n<style>\n#fountainG{\nposition:relative;\nwidth:141px;\nheight:17px}\n.fountainG{\nposition:absolute;\ntop:0;\nbackground-color:#000000;\nwidth:18px;\nheight:18px;\n-moz-animation-name:bounce_fountainG;\n-moz-animation-duration:2s;\n-moz-animation-iteration-count:infinite;\n-moz-animation-direction:linear;\n-moz-transform:scale(.3);\n-moz-border-radius:12px;\n-webkit-animation-name:bounce_fountainG;\n-webkit-animation-duration:2s;\n-webkit-animation-iteration-count:infinite;\n-webkit-animation-direction:linear;\n-webkit-transform:scale(.3);\n-webkit-border-radius:12px;\n-ms-animation-name:bounce_fountainG;\n-ms-animation-duration:2s;\n-ms-animation-iteration-count:infinite;\n-ms-animation-direction:linear;\n-ms-transform:scale(.3);\n-ms-border-radius:12px;\n-o-animation-name:bounce_fountainG;\n-o-animation-duration:2s;\n-o-animation-iteration-count:infinite;\n-o-animation-direction:linear;\n-o-transform:scale(.3);\n-o-border-radius:12px;\nanimation-name:bounce_fountainG;\nanimation-duration:2s;\nanimation-iteration-count:infinite;\nanimation-direction:linear;\ntransform:scale(.3);\nborder-radius:12px;\n}\n#fountainG_1{\nleft:0;\n-moz-animation-delay:0.8s;\n-webkit-animation-delay:0.8s;\n-ms-animation-delay:0.8s;\n-o-animation-delay:0.8s;\nanimation-delay:0.8s;\n}\n#fountainG_2{\nleft:18px;\n-moz-animation-delay:1s;\n-webkit-animation-delay:1s;\n-ms-animation-delay:1s;\n-o-animation-delay:1s;\nanimation-delay:1s;\n}\n#fountainG_3{\nleft:35px;\n-moz-animation-delay:1.2s;\n-webkit-animation-delay:1.2s;\n-ms-animation-delay:1.2s;\n-o-animation-delay:1.2s;\nanimation-delay:1.2s;\n}\n#fountainG_4{\nleft:53px;\n-moz-animation-delay:1.4s;\n-webkit-animation-delay:1.4s;\n-ms-animation-delay:1.4s;\n-o-animation-delay:1.4s;\nanimation-delay:1.4s;\n}\n#fountainG_5{\nleft:71px;\n-moz-animation-delay:1.6s;\n-webkit-animation-delay:1.6s;\n-ms-animation-delay:1.6s;\n-o-animation-delay:1.6s;\nanimation-delay:1.6s;\n}\n#fountainG_6{\nleft:88px;\n-moz-animation-delay:1.8s;\n-webkit-animation-delay:1.8s;\n-ms-animation-delay:1.8s;\n-o-animation-delay:1.8s;\nanimation-delay:1.8s;\n}\n#fountainG_7{\nleft:106px;\n-moz-animation-delay:2s;\n-webkit-animation-delay:2s;\n-ms-animation-delay:2s;\n-o-animation-delay:2s;\nanimation-delay:2s;\n}\n#fountainG_8{\nleft:123px;\n-moz-animation-delay:2.2s;\n-webkit-animation-delay:2.2s;\n-ms-animation-delay:2.2s;\n-o-animation-delay:2.2s;\nanimation-delay:2.2s;\n}\n@-moz-keyframes bounce_fountainG{\n0%{\n-moz-transform:scale(1);\nbackground-color:#000000;\n}\n100%{\n-moz-transform:scale(.3);\nbackground-color:rgba(255,255,255,0.2);\n}\n}\n@-webkit-keyframes bounce_fountainG{\n0%{\n-webkit-transform:scale(1);\nbackground-color:#000000;\n}\n100%{\n-webkit-transform:scale(.3);\nbackground-color:rgba(255,255,255,0.2);\n}\n}\n@-ms-keyframes bounce_fountainG{\n0%{\n-ms-transform:scale(1);\nbackground-color:#000000;\n}\n100%{\n-ms-transform:scale(.3);\nbackground-color:rgba(255,255,255,0.2);\n}\n}\n@-o-keyframes bounce_fountainG{\n0%{\n-o-transform:scale(1);\nbackground-color:#000000;\n}\n100%{\n-o-transform:scale(.3);\nbackground-color:rgba(255,255,255,0.2);\n}\n}\n@keyframes bounce_fountainG{\n0%{\ntransform:scale(1);\nbackground-color:#000000;\n}\n100%{\ntransform:scale(.3);\nbackground-color:rgba(255,255,255,0.2);\n}\n}\n</style>\n<body style=\"margin:0;padding:0\">\n<div id=\"fountainG\">\n<div id=\"fountainG_1\" class=\"fountainG\">\n</div>\n<div id=\"fountainG_2\" class=\"fountainG\">\n</div>\n<div id=\"fountainG_3\" class=\"fountainG\">\n</div>\n<div id=\"fountainG_4\" class=\"fountainG\">\n</div>\n<div id=\"fountainG_5\" class=\"fountainG\">\n</div>\n<div id=\"fountainG_6\" class=\"fountainG\">\n</div>\n<div id=\"fountainG_7\" class=\"fountainG\">\n</div>\n<div id=\"fountainG_8\" class=\"fountainG\">\n</div>\n</div>\n</body>\n</html>", "text/html", "utf-8", null);
            relativeLayout2.addView(webView, layoutParams4);
        } catch (Exception e) {
            C5017f.m3256a(context2, C5015d.EXCEPTION, "TemplatesLayout.getBaseTemplate - webview failed", e.getMessage(), "");
        }
        TextView textView2 = new TextView(context2);
        textView2.setText("Loading...");
        textView2.setId(105);
        textView2.setTextSize(1, 18.0f);
        textView2.setTextColor(Color.rgb(208, 210, 210));
        textView2.setGravity(17);
        LayoutParams layoutParams5 = new LayoutParams(-2, -2);
        layoutParams5.addRule(14);
        layoutParams5.addRule(3, 102);
        layoutParams5.setMargins(0, 0, 0, 0);
        relativeLayout2.addView(textView2, layoutParams5);
        return relativeLayout;
    }
}
