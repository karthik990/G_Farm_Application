package com.startapp.android.publish.ads.list3d;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Camera;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LightingColorFilter;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup.LayoutParams;
import android.view.animation.AnimationUtils;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;
import com.startapp.common.C5160b;
import com.startapp.common.p092a.C5146c;
import com.startapp.common.p092a.C5155g;
import java.util.LinkedList;

/* renamed from: com.startapp.android.publish.ads.list3d.c */
/* compiled from: StartAppSDK */
public class C4803c extends AdapterView<Adapter> {

    /* renamed from: A */
    private int f2619A = Integer.MIN_VALUE;

    /* renamed from: B */
    private boolean f2620B = false;

    /* renamed from: C */
    private boolean f2621C = false;

    /* renamed from: D */
    private boolean f2622D = false;

    /* renamed from: a */
    protected int f2623a = 0;

    /* renamed from: b */
    protected int f2624b;

    /* renamed from: c */
    protected int f2625c;

    /* renamed from: d */
    protected int f2626d;

    /* renamed from: e */
    protected int f2627e;

    /* renamed from: f */
    protected int f2628f;

    /* renamed from: g */
    protected int f2629g;

    /* renamed from: h */
    protected int f2630h;

    /* renamed from: i */
    protected int f2631i;

    /* renamed from: j */
    protected Dynamics f2632j;

    /* renamed from: k */
    protected float f2633k = 0.0f;

    /* renamed from: l */
    protected boolean f2634l = false;

    /* renamed from: m */
    protected boolean f2635m = false;

    /* renamed from: n */
    protected String f2636n;

    /* renamed from: o */
    protected String f2637o;

    /* renamed from: p */
    public BroadcastReceiver f2638p = new BroadcastReceiver() {
        public void onReceive(Context context, Intent intent) {
            int intExtra = intent.getIntExtra("getHeight", C4803c.this.getHeight());
            double height = (double) C4803c.this.getHeight();
            double d = (double) intExtra;
            Double.isNaN(height);
            Double.isNaN(d);
            double d2 = height / d;
            StringBuilder sb = new StringBuilder();
            sb.append(C4803c.this.f2639q);
            sb.append("Updating Position with Ratio: [");
            sb.append(d2);
            sb.append("]");
            C5155g.m3805a(3, sb.toString());
            C4803c cVar = C4803c.this;
            cVar.f2623a = intent.getIntExtra("mTouchState", cVar.f2623a);
            C4803c cVar2 = C4803c.this;
            cVar2.f2624b = intent.getIntExtra("mTouchStartX", cVar2.f2624b);
            C4803c cVar3 = C4803c.this;
            cVar3.f2625c = intent.getIntExtra("mTouchStartY", cVar3.f2625c);
            C4803c cVar4 = C4803c.this;
            cVar4.f2629g = intent.getIntExtra("mListRotation", cVar4.f2629g);
            C4803c cVar5 = C4803c.this;
            double intExtra2 = (double) intent.getIntExtra("mFirstItemPosition", cVar5.f2630h);
            Double.isNaN(intExtra2);
            cVar5.f2630h = (int) (intExtra2 * d2);
            C4803c.this.f2630h--;
            C4803c cVar6 = C4803c.this;
            double intExtra3 = (double) intent.getIntExtra("mLastItemPosition", cVar6.f2631i);
            Double.isNaN(intExtra3);
            cVar6.f2631i = (int) (intExtra3 * d2);
            C4803c.this.f2631i--;
            C4803c cVar7 = C4803c.this;
            double intExtra4 = (double) intent.getIntExtra("mListTop", cVar7.f2627e);
            Double.isNaN(intExtra4);
            cVar7.f2627e = (int) (intExtra4 * d2);
            C4803c cVar8 = C4803c.this;
            double intExtra5 = (double) intent.getIntExtra("mListTopStart", cVar8.f2626d);
            Double.isNaN(intExtra5);
            cVar8.f2626d = (int) (intExtra5 * d2);
            C4803c cVar9 = C4803c.this;
            double intExtra6 = (double) intent.getIntExtra("mListTopOffset", cVar9.f2628f);
            Double.isNaN(intExtra6);
            cVar9.f2628f = (int) (intExtra6 * d2);
            C4803c.this.f2632j = (Dynamics) intent.getParcelableExtra("mDynamics");
            C4803c cVar10 = C4803c.this;
            cVar10.f2633k = intent.getFloatExtra("mLastVelocity", cVar10.f2633k);
            C4803c.this.f2632j.mo61377a(d2);
            C4802b bVar = new C4802b(C4803c.this.getContext(), intent.getParcelableArrayListExtra("list"), "home", C4803c.this.f2636n, C4803c.this.f2637o);
            C4803c.this.setAdapter(bVar);
            C4803c cVar11 = C4803c.this;
            cVar11.f2634l = true;
            cVar11.f2635m = true;
            cVar11.mo61450a(cVar11.f2633k, true);
            C5160b.m3831a(context).mo62878a((BroadcastReceiver) this);
        }
    };
    /* access modifiers changed from: private */

    /* renamed from: q */
    public String f2639q = "List3DView";

    /* renamed from: r */
    private Adapter f2640r;

    /* renamed from: s */
    private VelocityTracker f2641s;

    /* renamed from: t */
    private Runnable f2642t;

    /* renamed from: u */
    private final LinkedList<View> f2643u = new LinkedList<>();

    /* renamed from: v */
    private Runnable f2644v;

    /* renamed from: w */
    private Rect f2645w;

    /* renamed from: x */
    private Camera f2646x;

    /* renamed from: y */
    private Matrix f2647y;

    /* renamed from: z */
    private Paint f2648z;

    public View getSelectedView() {
        return null;
    }

    public C4803c(Context context, AttributeSet attributeSet, String str, String str2) {
        super(context, attributeSet);
        this.f2636n = str;
        this.f2637o = str2;
    }

    public void setTag(String str) {
        this.f2639q = str;
    }

    /* renamed from: a */
    public void mo61449a() {
        this.f2634l = true;
    }

    public void setHint(boolean z) {
        this.f2621C = z;
    }

    /* renamed from: b */
    public boolean mo61453b() {
        return this.f2621C;
    }

    /* renamed from: c */
    public boolean mo61454c() {
        return this.f2620B;
    }

    public void setFade(boolean z) {
        this.f2620B = z;
    }

    public void setAdapter(Adapter adapter) {
        if (m2442d() && mo61454c()) {
            C5146c.m3752a((View) this, 0.0f);
        }
        this.f2640r = adapter;
        removeAllViewsInLayout();
        requestLayout();
    }

    public Adapter getAdapter() {
        return this.f2640r;
    }

    public void setSelection(int i) {
        throw new UnsupportedOperationException("Not supported");
    }

    /* renamed from: d */
    private boolean m2442d() {
        return C5146c.m3758a();
    }

    public void setDynamics(Dynamics dynamics) {
        Dynamics dynamics2 = this.f2632j;
        if (dynamics2 != null) {
            dynamics.mo61379a(dynamics2.mo61376a(), this.f2632j.mo61383b(), AnimationUtils.currentAnimationTimeMillis());
        }
        this.f2632j = dynamics;
    }

    /* renamed from: e */
    private void m2443e() {
        if (!this.f2622D) {
            this.f2622D = true;
            dispatchTouchEvent(MotionEvent.obtain(System.currentTimeMillis(), System.currentTimeMillis(), 0, 0.0f, 0.0f, 0));
            postDelayed(new Runnable() {
                public void run() {
                    C4803c.this.dispatchTouchEvent(MotionEvent.obtain(System.currentTimeMillis(), System.currentTimeMillis(), 2, 0.0f, -20.0f, 0));
                    C4803c.this.dispatchTouchEvent(MotionEvent.obtain(System.currentTimeMillis(), System.currentTimeMillis(), 1, 0.0f, -20.0f, 0));
                }
            }, 5);
        }
    }

    public boolean onTouchEvent(MotionEvent motionEvent) {
        if (getChildCount() == 0) {
            return false;
        }
        int action = motionEvent.getAction();
        if (action != 0) {
            float f = 0.0f;
            if (action == 1) {
                int i = this.f2623a;
                if (i == 1) {
                    m2434b((int) motionEvent.getX(), (int) motionEvent.getY());
                } else if (i == 2) {
                    this.f2641s.addMovement(motionEvent);
                    this.f2641s.computeCurrentVelocity(1000);
                    f = this.f2641s.getYVelocity();
                    this.f2633k = f;
                }
                m2433b(f);
            } else if (action != 2) {
                m2433b(0.0f);
            } else {
                if (this.f2623a == 1) {
                    m2435b(motionEvent);
                }
                if (this.f2623a == 2) {
                    this.f2641s.addMovement(motionEvent);
                    mo61451a(((int) motionEvent.getY()) - this.f2625c);
                }
            }
        } else {
            if (m2442d()) {
                C5146c.m3753a((View) this, 1500);
            }
            m2430a(motionEvent);
        }
        return true;
    }

    /* access modifiers changed from: protected */
    public void onLayout(boolean z, int i, int i2, int i3, int i4) {
        super.onLayout(z, i, i2, i3, i4);
        if (this.f2634l && this.f2640r != null) {
            if (getChildCount() == 0) {
                if (mo61453b()) {
                    this.f2627e = getHeight() / 3;
                }
                if (!this.f2635m) {
                    this.f2631i = -1;
                } else {
                    int i5 = this.f2630h;
                    this.f2631i = i5;
                    this.f2630h = i5 + 1;
                }
                m2438c(this.f2627e, 0);
            } else {
                int a = (this.f2627e + this.f2628f) - mo61448a(getChildAt(0));
                m2437c(a);
                m2440d(a);
            }
            m2446h();
            if (mo61453b()) {
                m2443e();
            }
            invalidate();
        }
    }

    /* access modifiers changed from: protected */
    public boolean drawChild(Canvas canvas, View view, long j) {
        Bitmap drawingCache = view.getDrawingCache();
        if (drawingCache == null) {
            return super.drawChild(canvas, view, j);
        }
        int top = view.getTop();
        int left = view.getLeft();
        int width = view.getWidth() / 2;
        int height = view.getHeight() / 2;
        float height2 = (float) (getHeight() / 2);
        float f = (((float) (top + height)) - height2) / height2;
        float cos = (float) (1.0d - ((1.0d - Math.cos((double) f)) * 0.15000000596046448d));
        float f2 = (((float) this.f2629g) - (f * 20.0f)) % 90.0f;
        if (f2 < 0.0f) {
            f2 += 90.0f;
        }
        float f3 = f2;
        if (f3 < 45.0f) {
            Canvas canvas2 = canvas;
            Bitmap bitmap = drawingCache;
            int i = top;
            int i2 = left;
            int i3 = width;
            int i4 = height;
            float f4 = cos;
            m2429a(canvas2, bitmap, i, i2, i3, i4, f4, f3 - 90.0f);
            m2429a(canvas2, bitmap, i, i2, i3, i4, f4, f3);
        } else {
            Canvas canvas3 = canvas;
            Bitmap bitmap2 = drawingCache;
            int i5 = top;
            int i6 = left;
            int i7 = width;
            int i8 = height;
            float f5 = cos;
            m2429a(canvas3, bitmap2, i5, i6, i7, i8, f5, f3);
            m2429a(canvas3, bitmap2, i5, i6, i7, i8, f5, f3 - 90.0f);
        }
        return false;
    }

    /* renamed from: a */
    private void m2429a(Canvas canvas, Bitmap bitmap, int i, int i2, int i3, int i4, float f, float f2) {
        if (this.f2646x == null) {
            this.f2646x = new Camera();
        }
        this.f2646x.save();
        this.f2646x.translate(0.0f, 0.0f, (float) i4);
        this.f2646x.rotateX(f2);
        float f3 = (float) (-i4);
        this.f2646x.translate(0.0f, 0.0f, f3);
        if (this.f2647y == null) {
            this.f2647y = new Matrix();
        }
        this.f2646x.getMatrix(this.f2647y);
        this.f2646x.restore();
        this.f2647y.preTranslate((float) (-i3), f3);
        this.f2647y.postScale(f, f);
        this.f2647y.postTranslate((float) (i2 + i3), (float) (i + i4));
        if (this.f2648z == null) {
            this.f2648z = new Paint();
            this.f2648z.setAntiAlias(true);
            this.f2648z.setFilterBitmap(true);
        }
        this.f2648z.setColorFilter(m2427a(f2));
        canvas.drawBitmap(bitmap, this.f2647y, this.f2648z);
    }

    /* renamed from: a */
    private LightingColorFilter m2427a(float f) {
        double d = (double) f;
        Double.isNaN(d);
        double cos = Math.cos((d * 3.141592653589793d) / 180.0d);
        int i = ((int) (cos * 200.0d)) + 55;
        int pow = (int) (Math.pow(cos, 200.0d) * 70.0d);
        if (i > 255) {
            i = 255;
        }
        if (pow > 255) {
            pow = 255;
        }
        return new LightingColorFilter(Color.rgb(i, i, i), Color.rgb(pow, pow, pow));
    }

    /* renamed from: a */
    private void m2430a(MotionEvent motionEvent) {
        removeCallbacks(this.f2642t);
        this.f2624b = (int) motionEvent.getX();
        this.f2625c = (int) motionEvent.getY();
        this.f2626d = mo61448a(getChildAt(0)) - this.f2628f;
        m2445g();
        this.f2641s = VelocityTracker.obtain();
        this.f2641s.addMovement(motionEvent);
        this.f2623a = 1;
    }

    /* renamed from: b */
    private void m2433b(float f) {
        mo61450a(f, false);
    }

    /* access modifiers changed from: protected */
    /* renamed from: a */
    public void mo61450a(float f, boolean z) {
        if (this.f2641s != null || z) {
            VelocityTracker velocityTracker = this.f2641s;
            if (velocityTracker != null) {
                velocityTracker.recycle();
            }
            this.f2641s = null;
            removeCallbacks(this.f2644v);
            if (this.f2642t == null) {
                this.f2642t = new Runnable() {
                    public void run() {
                        if (C4803c.this.f2632j != null) {
                            View childAt = C4803c.this.getChildAt(0);
                            if (childAt != null) {
                                C4803c cVar = C4803c.this;
                                cVar.f2626d = cVar.mo61448a(childAt) - C4803c.this.f2628f;
                                C4803c.this.f2632j.mo61381a(AnimationUtils.currentAnimationTimeMillis());
                                C4803c cVar2 = C4803c.this;
                                cVar2.mo61451a(((int) cVar2.f2632j.mo61376a()) - C4803c.this.f2626d);
                            }
                            if (!C4803c.this.f2632j.mo61382a(0.5f, 0.4f)) {
                                C4803c.this.postDelayed(this, 16);
                            }
                        }
                    }
                };
            }
            Dynamics dynamics = this.f2632j;
            if (dynamics != null) {
                if (!z) {
                    dynamics.mo61379a((float) this.f2627e, f, AnimationUtils.currentAnimationTimeMillis());
                }
                post(this.f2642t);
            }
            this.f2623a = 0;
        }
    }

    /* access modifiers changed from: protected */
    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        removeCallbacks(this.f2642t);
    }

    /* access modifiers changed from: protected */
    /* renamed from: a */
    public void mo61451a(int i) {
        this.f2627e = this.f2626d + i;
        this.f2629g = (-(this.f2627e * 270)) / getHeight();
        m2444f();
        requestLayout();
    }

    /* renamed from: f */
    private void m2444f() {
        int i;
        int i2 = this.f2629g;
        int i3 = i2 % 90;
        if (i3 < 45) {
            i = ((-(i2 - i3)) * getHeight()) / 270;
        } else {
            i = ((-((i2 + 90) - i3)) * getHeight()) / 270;
        }
        if (this.f2619A == Integer.MIN_VALUE && this.f2631i == this.f2640r.getCount() - 1 && m2436c(getChildAt(getChildCount() - 1)) < getHeight()) {
            this.f2619A = i;
        }
        if (i > 0) {
            i = 0;
        } else {
            int i4 = this.f2619A;
            if (i < i4) {
                i = i4;
            }
        }
        float f = (float) i;
        this.f2632j.mo61378a(f);
        this.f2632j.mo61384b(f);
    }

    /* renamed from: g */
    private void m2445g() {
        if (this.f2644v == null) {
            this.f2644v = new Runnable() {
                public void run() {
                    if (C4803c.this.f2623a == 1) {
                        C4803c cVar = C4803c.this;
                        int a = cVar.mo61447a(cVar.f2624b, C4803c.this.f2625c);
                        if (a != -1) {
                            C4803c.this.mo61452b(a);
                        }
                    }
                }
            };
        }
        postDelayed(this.f2644v, (long) ViewConfiguration.getLongPressTimeout());
    }

    /* renamed from: b */
    private boolean m2435b(MotionEvent motionEvent) {
        int x = (int) motionEvent.getX();
        int y = (int) motionEvent.getY();
        int i = this.f2624b;
        if (x >= i - 10 && x <= i + 10) {
            int i2 = this.f2625c;
            if (y >= i2 - 10 && y <= i2 + 10) {
                return false;
            }
        }
        removeCallbacks(this.f2644v);
        this.f2623a = 2;
        return true;
    }

    /* access modifiers changed from: protected */
    /* renamed from: a */
    public int mo61447a(int i, int i2) {
        if (this.f2645w == null) {
            this.f2645w = new Rect();
        }
        for (int i3 = 0; i3 < getChildCount(); i3++) {
            getChildAt(i3).getHitRect(this.f2645w);
            if (this.f2645w.contains(i, i2)) {
                return i3;
            }
        }
        return -1;
    }

    /* renamed from: b */
    private void m2434b(int i, int i2) {
        int a = mo61447a(i, i2);
        if (a != -1) {
            int i3 = this.f2630h + a;
            performItemClick(getChildAt(a), i3, this.f2640r.getItemId(i3));
        }
    }

    /* access modifiers changed from: protected */
    /* renamed from: b */
    public void mo61452b(int i) {
        View childAt = getChildAt(i);
        int i2 = this.f2630h + i;
        long itemId = this.f2640r.getItemId(i2);
        OnItemLongClickListener onItemLongClickListener = getOnItemLongClickListener();
        if (onItemLongClickListener != null) {
            onItemLongClickListener.onItemLongClick(this, childAt, i2, itemId);
        }
    }

    /* renamed from: c */
    private void m2437c(int i) {
        int childCount = getChildCount();
        if (this.f2631i != this.f2640r.getCount() - 1 && childCount > 1) {
            View childAt = getChildAt(0);
            while (childAt != null && m2436c(childAt) + i < 0) {
                removeViewInLayout(childAt);
                childCount--;
                this.f2643u.addLast(childAt);
                this.f2630h++;
                this.f2628f += m2439d(childAt);
                childAt = childCount > 1 ? getChildAt(0) : null;
            }
        }
        if (this.f2630h != 0 && childCount > 1) {
            View childAt2 = getChildAt(childCount - 1);
            while (childAt2 != null && mo61448a(childAt2) + i > getHeight()) {
                removeViewInLayout(childAt2);
                childCount--;
                this.f2643u.addLast(childAt2);
                this.f2631i--;
                childAt2 = childCount > 1 ? getChildAt(childCount - 1) : null;
            }
        }
    }

    /* renamed from: d */
    private void m2440d(int i) {
        m2438c(m2436c(getChildAt(getChildCount() - 1)), i);
        m2441d(mo61448a(getChildAt(0)), i);
    }

    /* renamed from: c */
    private void m2438c(int i, int i2) {
        while (i + i2 < getHeight() && this.f2631i < this.f2640r.getCount() - 1) {
            this.f2631i++;
            View view = this.f2640r.getView(this.f2631i, getCachedView(), this);
            m2431a(view, 0);
            i += m2439d(view);
        }
    }

    /* renamed from: d */
    private void m2441d(int i, int i2) {
        while (i + i2 > 0) {
            int i3 = this.f2630h;
            if (i3 > 0) {
                this.f2630h = i3 - 1;
                View view = this.f2640r.getView(this.f2630h, getCachedView(), this);
                m2431a(view, 1);
                int d = m2439d(view);
                i -= d;
                this.f2628f -= d;
            } else {
                return;
            }
        }
    }

    /* renamed from: a */
    private void m2431a(View view, int i) {
        LayoutParams layoutParams = view.getLayoutParams();
        if (layoutParams == null) {
            layoutParams = new LayoutParams(-2, -2);
        }
        int i2 = i == 1 ? 0 : -1;
        view.setDrawingCacheEnabled(true);
        addViewInLayout(view, i2, layoutParams, true);
        view.measure(((int) (((float) getWidth()) * 0.85f)) | 1073741824, 0);
    }

    /* renamed from: h */
    private void m2446h() {
        int i = this.f2627e + this.f2628f;
        float width = ((float) getWidth()) * 0.0f;
        float height = 1.0f / (((float) getHeight()) * 0.9f);
        for (int i2 = 0; i2 < getChildCount(); i2++) {
            View childAt = getChildAt(i2);
            double d = (double) width;
            double d2 = (double) height;
            Double.isNaN(d2);
            double d3 = d2 * 6.283185307179586d;
            double d4 = (double) i;
            Double.isNaN(d4);
            double sin = Math.sin(d3 * d4);
            Double.isNaN(d);
            int i3 = (int) (d * sin);
            int measuredWidth = childAt.getMeasuredWidth();
            int measuredHeight = childAt.getMeasuredHeight();
            int width2 = i3 + ((getWidth() - measuredWidth) / 2);
            int b = m2432b(childAt);
            int i4 = i + b;
            childAt.layout(width2, i4, measuredWidth + width2, i4 + measuredHeight);
            i += measuredHeight + (b * 2);
        }
    }

    private View getCachedView() {
        if (this.f2643u.size() != 0) {
            return (View) this.f2643u.removeFirst();
        }
        return null;
    }

    /* renamed from: b */
    private int m2432b(View view) {
        return (int) ((((float) view.getMeasuredHeight()) * 0.35000002f) / 2.0f);
    }

    /* access modifiers changed from: protected */
    /* renamed from: a */
    public int mo61448a(View view) {
        return view.getTop() - m2432b(view);
    }

    /* renamed from: c */
    private int m2436c(View view) {
        return view.getBottom() + m2432b(view);
    }

    /* renamed from: d */
    private int m2439d(View view) {
        return view.getMeasuredHeight() + (m2432b(view) * 2);
    }

    public int getFirstItemPosition() {
        return this.f2630h;
    }

    public int getLastItemPosition() {
        return this.f2631i;
    }

    public boolean dispatchKeyShortcutEvent(KeyEvent keyEvent) {
        return super.dispatchKeyShortcutEvent(keyEvent);
    }
}
