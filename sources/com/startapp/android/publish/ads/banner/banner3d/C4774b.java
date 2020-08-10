package com.startapp.android.publish.ads.banner.banner3d;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.EmbossMaskFilter;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.GradientDrawable.Orientation;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.RoundRectShape;
import android.graphics.drawable.shapes.Shape;
import android.text.TextUtils.TruncateAt;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.core.view.GravityCompat;
import com.mobiroller.constants.Constants;
import com.startapp.android.publish.ads.banner.banner3d.Banner3DSize.Size;
import com.startapp.android.publish.adsCommon.C4983b;
import com.startapp.android.publish.adsCommon.Utils.C4945h;
import com.startapp.android.publish.p065a.C4733b;
import com.startapp.common.p092a.C5146c;

/* renamed from: com.startapp.android.publish.ads.banner.banner3d.b */
/* compiled from: StartAppSDK */
public class C4774b extends RelativeLayout {

    /* renamed from: a */
    private TextView f2539a;

    /* renamed from: b */
    private TextView f2540b;

    /* renamed from: c */
    private ImageView f2541c;

    /* renamed from: d */
    private C4733b f2542d;

    /* renamed from: e */
    private TextView f2543e;

    /* renamed from: f */
    private Point f2544f;

    /* renamed from: com.startapp.android.publish.ads.banner.banner3d.b$2 */
    /* compiled from: StartAppSDK */
    static /* synthetic */ class C47762 {

        /* renamed from: a */
        static final /* synthetic */ int[] f2546a = new int[C4777a.values().length];

        /* JADX WARNING: Can't wrap try/catch for region: R(12:0|1|2|3|4|5|6|7|8|9|10|12) */
        /* JADX WARNING: Code restructure failed: missing block: B:13:?, code lost:
            return;
         */
        /* JADX WARNING: Failed to process nested try/catch */
        /* JADX WARNING: Missing exception handler attribute for start block: B:3:0x0014 */
        /* JADX WARNING: Missing exception handler attribute for start block: B:5:0x001f */
        /* JADX WARNING: Missing exception handler attribute for start block: B:7:0x002a */
        /* JADX WARNING: Missing exception handler attribute for start block: B:9:0x0035 */
        static {
            /*
                com.startapp.android.publish.ads.banner.banner3d.b$a[] r0 = com.startapp.android.publish.ads.banner.banner3d.C4774b.C4777a.values()
                int r0 = r0.length
                int[] r0 = new int[r0]
                f2546a = r0
                int[] r0 = f2546a     // Catch:{ NoSuchFieldError -> 0x0014 }
                com.startapp.android.publish.ads.banner.banner3d.b$a r1 = com.startapp.android.publish.ads.banner.banner3d.C4774b.C4777a.XS     // Catch:{ NoSuchFieldError -> 0x0014 }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x0014 }
                r2 = 1
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x0014 }
            L_0x0014:
                int[] r0 = f2546a     // Catch:{ NoSuchFieldError -> 0x001f }
                com.startapp.android.publish.ads.banner.banner3d.b$a r1 = com.startapp.android.publish.ads.banner.banner3d.C4774b.C4777a.S     // Catch:{ NoSuchFieldError -> 0x001f }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x001f }
                r2 = 2
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x001f }
            L_0x001f:
                int[] r0 = f2546a     // Catch:{ NoSuchFieldError -> 0x002a }
                com.startapp.android.publish.ads.banner.banner3d.b$a r1 = com.startapp.android.publish.ads.banner.banner3d.C4774b.C4777a.M     // Catch:{ NoSuchFieldError -> 0x002a }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x002a }
                r2 = 3
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x002a }
            L_0x002a:
                int[] r0 = f2546a     // Catch:{ NoSuchFieldError -> 0x0035 }
                com.startapp.android.publish.ads.banner.banner3d.b$a r1 = com.startapp.android.publish.ads.banner.banner3d.C4774b.C4777a.L     // Catch:{ NoSuchFieldError -> 0x0035 }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x0035 }
                r2 = 4
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x0035 }
            L_0x0035:
                int[] r0 = f2546a     // Catch:{ NoSuchFieldError -> 0x0040 }
                com.startapp.android.publish.ads.banner.banner3d.b$a r1 = com.startapp.android.publish.ads.banner.banner3d.C4774b.C4777a.XL     // Catch:{ NoSuchFieldError -> 0x0040 }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x0040 }
                r2 = 5
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x0040 }
            L_0x0040:
                return
            */
            throw new UnsupportedOperationException("Method not decompiled: com.startapp.android.publish.ads.banner.banner3d.C4774b.C47762.<clinit>():void");
        }
    }

    /* renamed from: com.startapp.android.publish.ads.banner.banner3d.b$a */
    /* compiled from: StartAppSDK */
    private enum C4777a {
        XS,
        S,
        M,
        L,
        XL
    }

    public C4774b(Context context, Point point) {
        super(context);
        this.f2544f = point;
        m2343a();
    }

    /* renamed from: a */
    private void m2343a() {
        Context context = getContext();
        C4777a templateBySize = getTemplateBySize();
        setBackgroundDrawable(new GradientDrawable(Orientation.TOP_BOTTOM, new int[]{C4983b.m3032a().mo62174n(), C4983b.m3032a().mo62175o()}));
        setLayoutParams(new LayoutParams(-2, -2));
        int a = C4945h.m2891a(context, 2);
        int a2 = C4945h.m2891a(context, 3);
        C4945h.m2891a(context, 4);
        int a3 = C4945h.m2891a(context, 5);
        int a4 = C4945h.m2891a(context, 6);
        int a5 = C4945h.m2891a(context, 8);
        C4945h.m2891a(context, 10);
        int a6 = C4945h.m2891a(context, 20);
        C4945h.m2891a(context, 84);
        int a7 = C4945h.m2891a(context, 90);
        setPadding(a3, 0, a3, 0);
        setTag(this);
        this.f2541c = new ImageView(context);
        this.f2541c.setId(1);
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(a7, a7);
        layoutParams.addRule(15);
        this.f2541c.setLayoutParams(layoutParams);
        this.f2539a = new TextView(context);
        this.f2539a.setId(2);
        RelativeLayout.LayoutParams layoutParams2 = new RelativeLayout.LayoutParams(-2, -2);
        layoutParams2.addRule(C5146c.m3740a(17), 1);
        layoutParams2.addRule(14);
        this.f2539a.setLayoutParams(layoutParams2);
        this.f2539a.setTextColor(C4983b.m3032a().mo62177q().intValue());
        this.f2539a.setGravity(C5146c.m3740a((int) GravityCompat.START));
        this.f2539a.setBackgroundColor(0);
        int i = C47762.f2546a[templateBySize.ordinal()];
        if (i == 1 || i == 2) {
            this.f2539a.setTextSize(17.0f);
            this.f2539a.setPadding(a2, 0, 0, a);
            Context context2 = getContext();
            double d = (double) this.f2544f.x;
            Double.isNaN(d);
            layoutParams2.width = C4945h.m2891a(context2, (int) (d * 0.55d));
        } else if (i == 3) {
            this.f2539a.setTextSize(17.0f);
            this.f2539a.setPadding(a2, 0, 0, a);
            Context context3 = getContext();
            double d2 = (double) this.f2544f.x;
            Double.isNaN(d2);
            layoutParams2.width = C4945h.m2891a(context3, (int) (d2 * 0.65d));
        } else if (i == 4 || i == 5) {
            this.f2539a.setTextSize(22.0f);
            this.f2539a.setPadding(a2, 0, 0, a3);
        }
        this.f2539a.setSingleLine(true);
        this.f2539a.setEllipsize(TruncateAt.END);
        C4945h.m2899a(this.f2539a, C4983b.m3032a().mo62178r());
        this.f2540b = new TextView(context);
        this.f2540b.setId(3);
        RelativeLayout.LayoutParams layoutParams3 = new RelativeLayout.LayoutParams(-1, -2);
        layoutParams3.addRule(C5146c.m3740a(17), 1);
        layoutParams3.addRule(3, 2);
        layoutParams3.setMargins(0, 0, 0, a3);
        this.f2540b.setLayoutParams(layoutParams3);
        this.f2540b.setTextColor(C4983b.m3032a().mo62180t().intValue());
        this.f2540b.setTextSize(18.0f);
        this.f2540b.setMaxLines(2);
        this.f2540b.setLines(2);
        this.f2540b.setSingleLine(false);
        this.f2540b.setEllipsize(TruncateAt.MARQUEE);
        this.f2540b.setHorizontallyScrolling(true);
        this.f2540b.setPadding(a2, 0, 0, 0);
        this.f2542d = new C4733b(getContext());
        this.f2542d.setId(5);
        RelativeLayout.LayoutParams layoutParams4 = new RelativeLayout.LayoutParams(-2, -2);
        int i2 = C47762.f2546a[templateBySize.ordinal()];
        if (i2 == 1 || i2 == 2 || i2 == 3) {
            layoutParams4.addRule(C5146c.m3740a(17), 1);
            layoutParams4.addRule(8, 1);
        } else if (i2 == 4 || i2 == 5) {
            layoutParams4.addRule(C5146c.m3740a(17), 2);
            Context context4 = getContext();
            double d3 = (double) this.f2544f.x;
            Double.isNaN(d3);
            layoutParams3.width = C4945h.m2891a(context4, (int) (d3 * 0.6d));
        }
        layoutParams4.setMargins(a2, a5, a2, 0);
        this.f2542d.setLayoutParams(layoutParams4);
        this.f2543e = new TextView(context);
        RelativeLayout.LayoutParams layoutParams5 = new RelativeLayout.LayoutParams(-2, -2);
        int i3 = C47762.f2546a[templateBySize.ordinal()];
        if (i3 == 1 || i3 == 2 || i3 == 3) {
            this.f2543e.setTextSize(13.0f);
            layoutParams5.addRule(C5146c.m3740a(17), 2);
            layoutParams5.addRule(15);
        } else if (i3 == 4) {
            layoutParams5.addRule(C5146c.m3740a(17), 3);
            layoutParams5.addRule(15);
            layoutParams5.setMargins(a6, 0, 0, 0);
            this.f2543e.setTextSize(26.0f);
        } else if (i3 == 5) {
            layoutParams5.addRule(C5146c.m3740a(17), 3);
            layoutParams5.addRule(15);
            layoutParams5.setMargins(a6 * 7, 0, 0, 0);
            this.f2543e.setTextSize(26.0f);
        }
        this.f2543e.setPadding(a4, a4, a4, a4);
        this.f2543e.setLayoutParams(layoutParams5);
        setButtonText(false);
        this.f2543e.setTextColor(-1);
        this.f2543e.setTypeface(null, 1);
        this.f2543e.setId(4);
        this.f2543e.setShadowLayer(2.5f, -3.0f, 3.0f, -9013642);
        this.f2543e.setBackgroundDrawable(new ShapeDrawable(new RoundRectShape(new float[]{10.0f, 10.0f, 10.0f, 10.0f, 10.0f, 10.0f, 10.0f, 10.0f}, null, null)) {
            /* access modifiers changed from: protected */
            public void onDraw(Shape shape, Canvas canvas, Paint paint) {
                paint.setColor(-11363070);
                paint.setMaskFilter(new EmbossMaskFilter(new float[]{1.0f, 1.0f, 1.0f}, 0.4f, 5.0f, 3.0f));
                super.onDraw(shape, canvas, paint);
            }
        });
        addView(this.f2541c);
        addView(this.f2539a);
        int i4 = C47762.f2546a[templateBySize.ordinal()];
        if (i4 == 1 || i4 == 2 || i4 == 3) {
            addView(this.f2543e);
        } else if (i4 == 4 || i4 == 5) {
            addView(this.f2543e);
            addView(this.f2540b);
        }
        addView(this.f2542d);
    }

    public void setText(String str) {
        this.f2539a.setText(str);
    }

    public void setImage(Bitmap bitmap) {
        this.f2541c.setImageBitmap(bitmap);
    }

    /* renamed from: a */
    public void mo61345a(int i, int i2, int i3) {
        this.f2541c.setImageResource(i);
        LayoutParams layoutParams = this.f2541c.getLayoutParams();
        layoutParams.width = i2;
        layoutParams.height = i3;
        this.f2541c.setLayoutParams(layoutParams);
    }

    public void setRating(float f) {
        try {
            this.f2542d.setRating(f);
        } catch (NullPointerException unused) {
        }
    }

    /* renamed from: a */
    public void mo61346a(Bitmap bitmap, int i, int i2) {
        this.f2541c.setImageBitmap(bitmap);
        LayoutParams layoutParams = this.f2541c.getLayoutParams();
        layoutParams.width = i;
        layoutParams.height = i2;
        this.f2541c.setLayoutParams(layoutParams);
    }

    public void setDescription(String str) {
        if (str != null) {
            String str2 = "";
            if (str.compareTo(str2) != 0) {
                String[] a = m2344a(str);
                String str3 = a[0];
                if (a[1] != null) {
                    str2 = m2344a(a[1])[0];
                }
                if (str.length() >= 110) {
                    StringBuilder sb = new StringBuilder();
                    sb.append(str2);
                    sb.append("...");
                    str2 = sb.toString();
                }
                TextView textView = this.f2540b;
                StringBuilder sb2 = new StringBuilder();
                sb2.append(str3);
                sb2.append(Constants.NEW_LINE);
                sb2.append(str2);
                textView.setText(sb2.toString());
            }
        }
    }

    /* renamed from: a */
    private String[] m2344a(String str) {
        boolean z;
        String[] strArr = new String[2];
        int i = 55;
        if (str.length() > 55) {
            char[] charArray = str.substring(0, 55).toCharArray();
            int length = charArray.length - 1;
            int i2 = length - 1;
            while (true) {
                if (i2 <= 0) {
                    z = false;
                    break;
                } else if (charArray[i2] == ' ') {
                    length = i2;
                    z = true;
                    break;
                } else {
                    i2--;
                }
            }
            if (z) {
                i = length;
            }
            strArr[0] = str.substring(0, i);
            strArr[1] = str.substring(i + 1, str.length());
        } else {
            strArr[0] = str;
            strArr[1] = null;
        }
        return strArr;
    }

    private C4777a getTemplateBySize() {
        C4777a aVar = C4777a.S;
        if (this.f2544f.x > Size.SMALL.getSize().mo61371a() || this.f2544f.y > Size.SMALL.getSize().mo61374b()) {
            aVar = C4777a.M;
        }
        if (this.f2544f.x > Size.MEDIUM.getSize().mo61371a() || this.f2544f.y > Size.MEDIUM.getSize().mo61374b()) {
            aVar = C4777a.L;
        }
        return (this.f2544f.x > Size.LARGE.getSize().mo61371a() || this.f2544f.y > Size.LARGE.getSize().mo61374b()) ? C4777a.XL : aVar;
    }

    public void setButtonText(boolean z) {
        if (z) {
            this.f2543e.setText("OPEN");
        } else {
            this.f2543e.setText("DOWNLOAD");
        }
    }
}
