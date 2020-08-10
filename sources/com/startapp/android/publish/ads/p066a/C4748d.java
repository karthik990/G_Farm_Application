package com.startapp.android.publish.ads.p066a;

import android.app.Activity;
import android.content.Context;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.ConsoleMessage;
import android.webkit.ConsoleMessage.MessageLevel;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.widget.ImageButton;
import android.widget.ImageView.ScaleType;
import android.widget.RelativeLayout.LayoutParams;
import com.p021b.p022a.p023a.p024a.p026b.C0961a;
import com.startapp.android.publish.adsCommon.Utils.C4945h;
import com.startapp.android.publish.adsCommon.p082f.C5015d;
import com.startapp.android.publish.adsCommon.p082f.C5017f;
import com.startapp.android.publish.adsCommon.p083g.p084a.C5022a;
import com.startapp.android.publish.adsCommon.p083g.p084a.C5022a.C5023a;
import com.startapp.android.publish.adsCommon.p083g.p084a.C5024b;
import com.startapp.android.publish.adsCommon.p083g.p084a.C5025c;
import com.startapp.android.publish.adsCommon.p083g.p084a.C5026d;
import com.startapp.android.publish.adsCommon.p083g.p084a.C5027e;
import com.startapp.android.publish.adsCommon.p083g.p085b.C5028a;
import com.startapp.android.publish.adsCommon.p083g.p085b.C5029b;
import com.startapp.android.publish.adsCommon.p083g.p086c.C5030a;
import com.startapp.android.publish.common.metaData.MetaData;
import com.startapp.common.p092a.C5150d;
import com.startapp.common.p092a.C5155g;
import java.util.Map;

/* renamed from: com.startapp.android.publish.ads.a.d */
/* compiled from: StartAppSDK */
public class C4748d extends C4740c {
    /* access modifiers changed from: private */

    /* renamed from: i */
    public C5026d f2508i = C5026d.LOADING;

    /* renamed from: j */
    private DisplayMetrics f2509j;
    /* access modifiers changed from: private */

    /* renamed from: k */
    public C4753b f2510k;
    /* access modifiers changed from: private */

    /* renamed from: l */
    public C5029b f2511l;
    /* access modifiers changed from: private */

    /* renamed from: m */
    public C5030a f2512m;

    /* renamed from: n */
    private ImageButton f2513n;
    /* access modifiers changed from: private */

    /* renamed from: o */
    public boolean f2514o = false;
    /* access modifiers changed from: private */

    /* renamed from: p */
    public boolean f2515p = false;

    /* renamed from: com.startapp.android.publish.ads.a.d$a */
    /* compiled from: StartAppSDK */
    private class C4752a extends C5027e {
        public C4752a(C5024b bVar) {
            super(bVar);
        }

        public void onPageFinished(WebView webView, String str) {
            super.onPageFinished(webView, str);
            if (C4748d.this.f2508i == C5026d.LOADING) {
                C5025c.m3273a("interstitial", webView);
                C5028a.m3280a(C4748d.this.mo61159b(), webView, C4748d.this.f2511l);
                C4748d.this.m2259G();
                C4748d.this.m2260H();
                C4748d.this.f2508i = C5026d.DEFAULT;
                C5025c.m3272a(C4748d.this.f2508i, webView);
                C5025c.m3269a(webView);
                if (C4748d.this.f2515p) {
                    C4748d.this.f2510k.fireViewableChangeEvent();
                }
                if (MetaData.getInstance().isOmsdkEnabled()) {
                    C4748d dVar = C4748d.this;
                    dVar.f2490e = null;
                    if (dVar.f2490e != null) {
                        if (C4748d.this.f2466a != null) {
                            View a = C4748d.this.f2466a.mo62112a();
                            if (a != null) {
                                C4748d.this.f2490e.mo11478b(a);
                            }
                        }
                        C4748d.this.f2490e.mo11476a(webView);
                        C4748d.this.f2490e.mo11475a();
                        C0961a.m103a(C4748d.this.f2490e).mo11464a();
                    }
                }
            }
        }
    }

    /* renamed from: com.startapp.android.publish.ads.a.d$b */
    /* compiled from: StartAppSDK */
    private class C4753b extends C5022a {
        public C4753b(C5023a aVar) {
            super(aVar);
        }

        public void close() {
            C5155g.m3807a("MraidMode", 3, "close");
            C4748d.this.f2508i = C5026d.HIDDEN;
            C5025c.m3272a(C4748d.this.f2508i, C4748d.this.f2489d);
            C4748d.this.f2492g.run();
        }

        public void useCustomClose(String str) {
            StringBuilder sb = new StringBuilder();
            sb.append("useCustomClose: ");
            sb.append(str);
            C5155g.m3807a("MraidMode", 3, sb.toString());
            boolean parseBoolean = Boolean.parseBoolean(str);
            if (C4748d.this.f2514o != parseBoolean) {
                C4748d.this.f2514o = parseBoolean;
                if (parseBoolean) {
                    C4748d.this.m2262J();
                } else {
                    C4748d.this.m2261I();
                }
            }
        }

        public void setOrientationProperties(Map<String, String> map) {
            StringBuilder sb = new StringBuilder();
            sb.append("setOrientationProperties: ");
            sb.append(map);
            C5155g.m3807a("MraidMode", 3, sb.toString());
            boolean parseBoolean = Boolean.parseBoolean((String) map.get("allowOrientationChange"));
            String str = (String) map.get("forceOrientation");
            if (C4748d.this.f2512m.f3270a != parseBoolean || C4748d.this.f2512m.f3271b != C5030a.m3288a(str)) {
                C4748d.this.f2512m.f3270a = parseBoolean;
                C4748d.this.f2512m.f3271b = C5030a.m3288a(str);
                applyOrientationProperties(C4748d.this.mo61159b(), C4748d.this.f2512m);
            }
        }

        public boolean isFeatureSupported(String str) {
            return C4748d.this.f2511l.mo62329a(str);
        }

        public void fireViewableChangeEvent() {
            C5025c.m3271a(C4748d.this.f2489d, C4748d.this.f2515p);
        }
    }

    /* access modifiers changed from: protected */
    /* renamed from: b */
    public boolean mo61197b(String str) {
        return false;
    }

    /* renamed from: a */
    public void mo61151a(Bundle bundle) {
        super.mo61151a(bundle);
        if (this.f2509j == null) {
            this.f2509j = new DisplayMetrics();
        }
        if (this.f2511l == null) {
            this.f2511l = new C5029b(mo61159b());
        }
        if (this.f2512m == null) {
            this.f2512m = new C5030a();
        }
        if (this.f2510k == null) {
            this.f2510k = new C4753b(new C5023a() {
                /* renamed from: a */
                public boolean mo61210a(String str) {
                    return C4748d.this.mo61195a(str, true);
                }
            });
        }
    }

    /* renamed from: u */
    public void mo61182u() {
        super.mo61182u();
        this.f2515p = true;
        if (this.f2508i == C5026d.DEFAULT) {
            this.f2510k.fireViewableChangeEvent();
        }
    }

    /* renamed from: a */
    public void mo61150a(Configuration configuration) {
        m2259G();
    }

    /* renamed from: s */
    public void mo61180s() {
        this.f2515p = false;
        if (this.f2508i == C5026d.DEFAULT) {
            this.f2510k.fireViewableChangeEvent();
        }
        super.mo61180s();
    }

    /* access modifiers changed from: protected */
    /* renamed from: x */
    public void mo61199x() {
        this.f2489d.setWebViewClient(new C4752a(this.f2510k));
        this.f2489d.setWebChromeClient(new WebChromeClient() {
            public boolean onConsoleMessage(ConsoleMessage consoleMessage) {
                String str = "MraidMode";
                try {
                    if (consoleMessage.messageLevel() == MessageLevel.ERROR) {
                        StringBuilder sb = new StringBuilder();
                        sb.append("WebChromeClient console error: ");
                        sb.append(consoleMessage.message());
                        C5155g.m3807a(str, 6, sb.toString());
                        if (consoleMessage.message().contains("mraid")) {
                            C5017f.m3256a(C4748d.this.mo61159b(), C5015d.EXCEPTION, "MraidMode.ConsoleError", consoleMessage.message(), "");
                        }
                    } else {
                        StringBuilder sb2 = new StringBuilder();
                        sb2.append("WebChromeClient console log: ");
                        sb2.append(consoleMessage.message());
                        C5155g.m3807a(str, 3, sb2.toString());
                    }
                } catch (Exception e) {
                    StringBuilder sb3 = new StringBuilder();
                    sb3.append("WebChromeClient onConsoleMessage Exception: ");
                    sb3.append(e.getMessage());
                    C5155g.m3807a(str, 6, sb3.toString());
                }
                return super.onConsoleMessage(consoleMessage);
            }
        });
    }

    /* access modifiers changed from: protected */
    /* renamed from: a */
    public boolean mo61195a(String str, boolean z) {
        StringBuilder sb = new StringBuilder();
        sb.append("adClicked with url: ");
        sb.append(str);
        C5155g.m3807a("MraidMode", 3, sb.toString());
        this.f2508i = C5026d.HIDDEN;
        C5025c.m3272a(this.f2508i, this.f2489d);
        try {
            return super.mo61195a(str, z);
        } catch (Exception e) {
            Activity b = mo61159b();
            C5015d dVar = C5015d.EXCEPTION;
            StringBuilder sb2 = new StringBuilder();
            sb2.append("url = [");
            sb2.append(str);
            sb2.append("], ");
            sb2.append(e.getMessage());
            C5017f.m3256a(b, dVar, "MraidMode.adClicked", sb2.toString(), "");
            return false;
        }
    }

    /* access modifiers changed from: private */
    /* renamed from: G */
    public void m2259G() {
        try {
            mo61159b().getWindowManager().getDefaultDisplay().getMetrics(this.f2509j);
            C5025c.m3268a(mo61159b(), this.f2509j.widthPixels, this.f2509j.heightPixels, this.f2489d);
            C5025c.m3275b(mo61159b(), this.f2509j.widthPixels, this.f2509j.heightPixels, this.f2489d);
            C5025c.m3267a(mo61159b(), 0, 0, this.f2509j.widthPixels, this.f2509j.heightPixels, this.f2489d);
            C5025c.m3274b(mo61159b(), 0, 0, this.f2509j.widthPixels, this.f2509j.heightPixels, this.f2489d);
        } catch (Exception e) {
            C5017f.m3256a(mo61159b(), C5015d.EXCEPTION, "MraidMode.updateDisplayMetrics", e.getMessage(), "");
        }
    }

    /* access modifiers changed from: private */
    /* renamed from: H */
    public void m2260H() {
        try {
            this.f2513n = new ImageButton(mo61159b());
            this.f2513n.setBackgroundColor(0);
            this.f2513n.setOnClickListener(new OnClickListener() {
                public void onClick(View view) {
                    C4748d.this.f2510k.close();
                }
            });
            if (!this.f2514o) {
                m2261I();
            }
            int a = C4945h.m2891a((Context) mo61159b(), 50);
            LayoutParams layoutParams = new LayoutParams(a, a);
            layoutParams.addRule(10);
            layoutParams.addRule(11);
            this.f2491f.addView(this.f2513n, layoutParams);
        } catch (Exception e) {
            C5017f.m3256a(mo61159b(), C5015d.EXCEPTION, "MraidMode.addCloseRegion", e.getMessage(), "");
        }
    }

    /* access modifiers changed from: private */
    /* renamed from: I */
    public void m2261I() {
        try {
            if (this.f2513n != null) {
                this.f2513n.setImageDrawable(C5150d.m3784a(mo61159b().getResources(), "iVBORw0KGgoAAAANSUhEUgAAACAAAAAgCAYAAABzenr0AAAAGXRFWHRTb2Z0d2FyZQBBZG9iZSBJbWFnZVJlYWR5ccllPAAAA39pVFh0WE1MOmNvbS5hZG9iZS54bXAAAAAAADw/eHBhY2tldCBiZWdpbj0i77u/IiBpZD0iVzVNME1wQ2VoaUh6cmVTek5UY3prYzlkIj8+IDx4OnhtcG1ldGEgeG1sbnM6eD0iYWRvYmU6bnM6bWV0YS8iIHg6eG1wdGs9IkFkb2JlIFhNUCBDb3JlIDUuNi1jMDY3IDc5LjE1Nzc0NywgMjAxNS8wMy8zMC0yMzo0MDo0MiAgICAgICAgIj4gPHJkZjpSREYgeG1sbnM6cmRmPSJodHRwOi8vd3d3LnczLm9yZy8xOTk5LzAyLzIyLXJkZi1zeW50YXgtbnMjIj4gPHJkZjpEZXNjcmlwdGlvbiByZGY6YWJvdXQ9IiIgeG1sbnM6eG1wTU09Imh0dHA6Ly9ucy5hZG9iZS5jb20veGFwLzEuMC9tbS8iIHhtbG5zOnN0UmVmPSJodHRwOi8vbnMuYWRvYmUuY29tL3hhcC8xLjAvc1R5cGUvUmVzb3VyY2VSZWYjIiB4bWxuczp4bXA9Imh0dHA6Ly9ucy5hZG9iZS5jb20veGFwLzEuMC8iIHhtcE1NOk9yaWdpbmFsRG9jdW1lbnRJRD0ieG1wLmRpZDozODRkZTAxYi00OWRkLWM4NDYtYThkNC0wZWRiMDMwYTZlODAiIHhtcE1NOkRvY3VtZW50SUQ9InhtcC5kaWQ6QkE0Q0U2MUY2QzA0MTFFNUE3MkJGQjQ1MTkzOEYxQUUiIHhtcE1NOkluc3RhbmNlSUQ9InhtcC5paWQ6QkE0Q0U2MUU2QzA0MTFFNUE3MkJGQjQ1MTkzOEYxQUUiIHhtcDpDcmVhdG9yVG9vbD0iQWRvYmUgUGhvdG9zaG9wIENDIChXaW5kb3dzKSI+IDx4bXBNTTpEZXJpdmVkRnJvbSBzdFJlZjppbnN0YW5jZUlEPSJ4bXAuaWlkOjlkZjAyMGU0LTNlYmUtZTY0ZC04YjRiLWM5ZWY4MTU4ZjFhYyIgc3RSZWY6ZG9jdW1lbnRJRD0iYWRvYmU6ZG9jaWQ6cGhvdG9zaG9wOmU1MzEzNDdlLTZjMDEtMTFlNS1hZGZlLThmMTBjZWYxMGRiZSIvPiA8L3JkZjpEZXNjcmlwdGlvbj4gPC9yZGY6UkRGPiA8L3g6eG1wbWV0YT4gPD94cGFja2V0IGVuZD0iciI/PngNsEEAAANeSURBVHjatFfNS1tBEH+pUZOQ0B4i3sTSxHMRFNQoFBEP7dHgvyDiKWgguQra9F+oxqNiwOTQ+oFI1ZM3jSf1YK5FL41ooaKZzu+x+4gv2bx9Rgd+JNn5zO7s7IzH0CQiCvLHZ8YnxkfGe8ZbwS4zSowTxi/GT4/Hc2u8BLHjCOM745b06VboRJpx7GN8ZfyDxUqlQgcHB5RMJmloaIg6Ozupra3NBL5jDTzIQFYQdDOw5db5B8YxLDw+PtLKygr19PQQWDqIRqOUzWZNXUHH2rvBgr2M39C6uLig/v5+bcd2QLdUKskgYLNX57yvIL2zs0OhUOjZziU6Ojro8PBQBnGl3Alm+BknkMI54mybdS4BW3t7ezKIInzVCwDJYm4Zon4p5xLYzfPzcxlEpl7S3SNpmjlznZwQiXn/5CjEnTUzt5GBsbExamlpUfLBg0wjG8vLy3IXlqTzEAoH7m4kElEqTk1Nmfd7bW2tbhBYAw8ykFXZgQ9RJ1CsQghgEr/29/eVStPT09XFhdbX18nr9Vr81tZWyuVyFh+yMzMzSnvwJWjyDS+MYic2NzeV17O7u9vg2m79jsfjBv9bg7PbxOrqqjExMWHxIdvV1aW0V+VrFDtwhFCGh4cbnl0mk6kp+BsbGybsBNlGtkZGRqToEQK4xjfUc6csXlhYcHyFFhcXHe3Al6BrQz427e3tWldpfn5e6Rw83cIkHyvXAUAZb4SdsKZbPe0BaB+Bz+cjTiDlDmxtbZkybo9AKwn9fj9tb2875gBkINvIFnzJJMQ1PMV9GBgYUF6bQCBgFAoFY3x8/Ml6KpUy0un0kzXIQBY6KqrydapViPL5fM0/Rfcj+fhuJw5CqxBpleJYLEY3NzeW8dnZ2RoZrEmCLHQcSvGdWYrFe7CEFTwUqqjR85XLZUokEkoZ8CADWe3HqKoTcnyOdW5KI5m+vj56eHiQz3G0bkNyeXn5ag3J2dmZ/PffVC1Z8bVast3d3eqWLKDVlAaDwaadh8Nhvaa0XluOHg7n9lzn0MWRarfltp0oysEErRqGDTeDCbK9ajApuh7TxGiWERlrjWZzc3M0ODhYM5phDTzbaHb/rNHMFkhUNK13LobTv6K2RJ3se1yO519s4/k7wf5jG89/6I7n/wUYAGo3YtcprD4sAAAAAElFTkSuQmCC"));
                this.f2513n.setScaleType(ScaleType.FIT_CENTER);
            }
        } catch (Exception e) {
            C5017f.m3256a(mo61159b(), C5015d.EXCEPTION, "MraidMode.showDefaultCloseButton", e.getMessage(), "");
        }
    }

    /* access modifiers changed from: private */
    /* renamed from: J */
    public void m2262J() {
        try {
            if (this.f2513n != null) {
                this.f2513n.setImageResource(17170445);
            }
        } catch (Exception e) {
            C5017f.m3256a(mo61159b(), C5015d.EXCEPTION, "MraidMode.removeDefaultCloseButton", e.getMessage(), "");
        }
    }
}
