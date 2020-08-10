package p015br.com.simplepass.loading_button_lib;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Build.VERSION;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

@Metadata(mo22060bv = {1, 0, 1}, mo22061d1 = {"\u0000 \n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\u0018\u0000 \t2\u00020\u0001:\u0001\tB\u0005¢\u0006\u0002\u0010\u0002J\u0016\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\b¨\u0006\n"}, mo22062d2 = {"Lbr/com/simplepass/loading_button_lib/Utils;", "", "()V", "getDrawable", "Landroid/graphics/drawable/Drawable;", "context", "Landroid/content/Context;", "id", "", "Companion", "loading-button-android_release"}, mo22063k = 1, mo22064mv = {1, 1, 5})
/* renamed from: br.com.simplepass.loading_button_lib.Utils */
/* compiled from: Utils.kt */
public final class C0773Utils {
    public static final Companion Companion = new Companion(null);

    @Metadata(mo22060bv = {1, 0, 1}, mo22061d1 = {"\u0000\u001a\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0016\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\u0004¨\u0006\b"}, mo22062d2 = {"Lbr/com/simplepass/loading_button_lib/Utils$Companion;", "", "()V", "getColorWrapper", "", "context", "Landroid/content/Context;", "id", "loading-button-android_release"}, mo22063k = 1, mo22064mv = {1, 1, 5})
    /* renamed from: br.com.simplepass.loading_button_lib.Utils$Companion */
    /* compiled from: Utils.kt */
    public static final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        public final int getColorWrapper(Context context, int i) {
            Intrinsics.checkParameterIsNotNull(context, "context");
            if (VERSION.SDK_INT >= 23) {
                return context.getColor(i);
            }
            return context.getResources().getColor(i);
        }
    }

    public final Drawable getDrawable(Context context, int i) {
        Intrinsics.checkParameterIsNotNull(context, "context");
        if (VERSION.SDK_INT >= 21) {
            Drawable drawable = context.getDrawable(i);
            Intrinsics.checkExpressionValueIsNotNull(drawable, "context.getDrawable(id)");
            return drawable;
        }
        Drawable drawable2 = context.getResources().getDrawable(i);
        Intrinsics.checkExpressionValueIsNotNull(drawable2, "context.getResources().getDrawable(id)");
        return drawable2;
    }
}
