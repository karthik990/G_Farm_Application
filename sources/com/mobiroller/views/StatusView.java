package com.mobiroller.views;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.Align;
import android.graphics.Paint.Style;
import android.graphics.PointF;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.text.Layout.Alignment;
import android.text.StaticLayout;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;
import androidx.core.content.res.ResourcesCompat;
import androidx.core.view.ViewCompat;
import com.mobiroller.preview.C4290R;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.MutablePropertyReference1Impl;
import kotlin.jvm.internal.Reflection;
import kotlin.properties.Delegates;
import kotlin.properties.ReadWriteProperty;
import kotlin.ranges.ClosedRange;
import kotlin.ranges.IntRange;
import kotlin.ranges.RangesKt;
import kotlin.reflect.KProperty;
import kotlin.text.StringsKt;

@Metadata(mo22060bv = {1, 0, 3}, mo22061d1 = {"\u0000\u0001\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u001a\n\u0002\u0010\u0007\n\u0002\b\u0017\n\u0002\u0018\u0002\n\u0002\b\u001b\n\u0002\u0010!\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b \n\u0002\u0018\u0002\n\u0002\b\t\n\u0002\u0018\u0002\n\u0002\b\n\n\u0002\u0018\u0002\n\u0002\b=\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0010 \n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0010\u0002\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\b\u001f\u0018\u0000 ÿ\u00012\u00020\u0001:\u0018þ\u0001ÿ\u0001\u0002\u0002\u0002\u0002\u0002\u0002\u0002\u0002\u0002\u0002B%\b\u0007\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\n\b\u0002\u0010\u0004\u001a\u0004\u0018\u00010\u0005\u0012\b\b\u0002\u0010\u0006\u001a\u00020\u0007¢\u0006\u0002\u0010\bJ\u001b\u0010Ì\u0001\u001a\u00020\n2\u0007\u0010Í\u0001\u001a\u00020\u00072\u0007\u0010Î\u0001\u001a\u00020\u0007H\u0002J\t\u0010Ï\u0001\u001a\u00020\u0007H\u0002J\u0010\u0010Ð\u0001\u001a\u00020\u00072\u0007\u0010Ñ\u0001\u001a\u00020\u0007J'\u0010Ò\u0001\u001a\u00030Ó\u00012\b\u0010Ô\u0001\u001a\u00030Õ\u00012\b\u0010Ö\u0001\u001a\u00030\u00012\u0007\u0010×\u0001\u001a\u00020%H\u0002J\u000f\u0010Ø\u0001\u001a\n\u0012\u0005\u0012\u00030Õ\u00010Ù\u0001J%\u0010Ú\u0001\u001a\u00030Û\u00012\u000f\u0010Ü\u0001\u001a\n\u0012\u0005\u0012\u00030Õ\u00010Ù\u00012\b\u0010Ý\u0001\u001a\u00030\u0001H\u0002J\u001d\u0010Þ\u0001\u001a\u00020%2\b\u0010Ô\u0001\u001a\u00030Õ\u00012\b\u0010Ý\u0001\u001a\u00030\u0001H\u0002J\t\u0010ß\u0001\u001a\u00020\u0007H\u0014J\t\u0010à\u0001\u001a\u00020\u0007H\u0014J\u001d\u0010á\u0001\u001a\u00020%2\b\u0010Ý\u0001\u001a\u00030\u00012\b\u0010Ô\u0001\u001a\u00030Õ\u0001H\u0002J\n\u0010â\u0001\u001a\u00030ã\u0001H\u0002J\t\u0010ä\u0001\u001a\u00020\nH\u0002J\t\u0010å\u0001\u001a\u00020\nH\u0002J\u0012\u0010æ\u0001\u001a\u00020%2\u0007\u0010ç\u0001\u001a\u00020\u0007H\u0002J\u0012\u0010è\u0001\u001a\u00020%2\u0007\u0010ç\u0001\u001a\u00020\u0007H\u0002J\u0016\u0010é\u0001\u001a\u00030ã\u00012\n\u0010ê\u0001\u001a\u0005\u0018\u00010ë\u0001H\u0014J7\u0010ì\u0001\u001a\u00030ã\u00012\u0007\u0010í\u0001\u001a\u00020\n2\u0007\u0010î\u0001\u001a\u00020\u00072\u0007\u0010ï\u0001\u001a\u00020\u00072\u0007\u0010ð\u0001\u001a\u00020\u00072\u0007\u0010ñ\u0001\u001a\u00020\u0007H\u0014J\u001c\u0010ò\u0001\u001a\u00030ã\u00012\u0007\u0010ó\u0001\u001a\u00020\u00072\u0007\u0010ô\u0001\u001a\u00020\u0007H\u0014J\n\u0010õ\u0001\u001a\u00030ã\u0001H\u0002J\u001d\u0010ö\u0001\u001a\u00020%2\b\u0010Ö\u0001\u001a\u00030\u00012\b\u0010÷\u0001\u001a\u00030\u0001H\u0002J\u0019\u0010ø\u0001\u001a\u00030ã\u00012\u000f\u0010Ü\u0001\u001a\n\u0012\u0005\u0012\u00030Õ\u00010Ù\u0001J\t\u0010ù\u0001\u001a\u00020%H\u0002J\t\u0010ú\u0001\u001a\u00020%H\u0002J\t\u0010û\u0001\u001a\u00020\u0007H\u0002J\u0018\u0010ü\u0001\u001a\u00020%*\u00020%2\t\b\u0002\u0010ý\u0001\u001a\u00020\u0007H\u0002R+\u0010\u000b\u001a\u00020\n2\u0006\u0010\t\u001a\u00020\n8F@FX\u0002¢\u0006\u0012\n\u0004\b\u0010\u0010\u0011\u001a\u0004\b\f\u0010\r\"\u0004\b\u000e\u0010\u000fR+\u0010\u0012\u001a\u00020\u00072\u0006\u0010\t\u001a\u00020\u00078F@FX\u0002¢\u0006\u0012\n\u0004\b\u0017\u0010\u0018\u001a\u0004\b\u0013\u0010\u0014\"\u0004\b\u0015\u0010\u0016R+\u0010\u0019\u001a\u00020\u00072\u0006\u0010\t\u001a\u00020\u00078F@FX\u0002¢\u0006\u0012\n\u0004\b\u001c\u0010\u0011\u001a\u0004\b\u001a\u0010\u0014\"\u0004\b\u001b\u0010\u0016R+\u0010\u001d\u001a\u00020\u00072\u0006\u0010\t\u001a\u00020\u00078F@FX\u0002¢\u0006\u0012\n\u0004\b \u0010\u0011\u001a\u0004\b\u001e\u0010\u0014\"\u0004\b\u001f\u0010\u0016R+\u0010!\u001a\u00020\u00072\u0006\u0010\t\u001a\u00020\u00078F@FX\u0002¢\u0006\u0012\n\u0004\b$\u0010\u0011\u001a\u0004\b\"\u0010\u0014\"\u0004\b#\u0010\u0016R+\u0010&\u001a\u00020%2\u0006\u0010\t\u001a\u00020%8F@FX\u0002¢\u0006\u0012\n\u0004\b+\u0010,\u001a\u0004\b'\u0010(\"\u0004\b)\u0010*R+\u0010-\u001a\u00020\u00072\u0006\u0010\t\u001a\u00020\u00078F@FX\u0002¢\u0006\u0012\n\u0004\b0\u0010\u0011\u001a\u0004\b.\u0010\u0014\"\u0004\b/\u0010\u0016R+\u00101\u001a\u00020\u00072\u0006\u0010\t\u001a\u00020\u00078F@FX\u0002¢\u0006\u0012\n\u0004\b4\u0010\u0011\u001a\u0004\b2\u0010\u0014\"\u0004\b3\u0010\u0016R+\u00105\u001a\u00020\u00072\u0006\u0010\t\u001a\u00020\u00078F@FX\u0002¢\u0006\u0012\n\u0004\b8\u0010\u0011\u001a\u0004\b6\u0010\u0014\"\u0004\b7\u0010\u0016R+\u00109\u001a\u00020%2\u0006\u0010\t\u001a\u00020%8F@FX\u0002¢\u0006\u0012\n\u0004\b<\u0010,\u001a\u0004\b:\u0010(\"\u0004\b;\u0010*R/\u0010>\u001a\u0004\u0018\u00010=2\b\u0010\t\u001a\u0004\u0018\u00010=8F@FX\u0002¢\u0006\u0012\n\u0004\bC\u0010\u0011\u001a\u0004\b?\u0010@\"\u0004\bA\u0010BR+\u0010D\u001a\u00020\u00072\u0006\u0010\t\u001a\u00020\u00078F@FX\u0002¢\u0006\u0012\n\u0004\bG\u0010,\u001a\u0004\bE\u0010\u0014\"\u0004\bF\u0010\u0016R/\u0010H\u001a\u0004\u0018\u00010=2\b\u0010\t\u001a\u0004\u0018\u00010=8F@FX\u0002¢\u0006\u0012\n\u0004\bK\u0010\u0011\u001a\u0004\bI\u0010@\"\u0004\bJ\u0010BR+\u0010L\u001a\u00020%2\u0006\u0010\t\u001a\u00020%8B@BX\u0002¢\u0006\u0012\n\u0004\bO\u0010,\u001a\u0004\bM\u0010(\"\u0004\bN\u0010*R&\u0010Q\u001a\u00020%2\b\b\u0001\u0010P\u001a\u00020%@FX\u000e¢\u0006\u000e\n\u0000\u001a\u0004\bR\u0010(\"\u0004\bS\u0010*R+\u0010T\u001a\u00020\n2\u0006\u0010\t\u001a\u00020\n8F@FX\u0002¢\u0006\u0012\n\u0004\bW\u0010\u0011\u001a\u0004\bU\u0010\r\"\u0004\bV\u0010\u000fR\u0014\u0010X\u001a\b\u0012\u0004\u0012\u00020Z0YX\u000e¢\u0006\u0002\n\u0000R/\u0010[\u001a\u0004\u0018\u00010=2\b\u0010\t\u001a\u0004\u0018\u00010=8F@FX\u0002¢\u0006\u0012\n\u0004\b^\u0010\u0011\u001a\u0004\b\\\u0010@\"\u0004\b]\u0010BR/\u0010`\u001a\u0004\u0018\u00010_2\b\u0010\t\u001a\u0004\u0018\u00010_8F@FX\u0002¢\u0006\u0012\n\u0004\be\u0010,\u001a\u0004\ba\u0010b\"\u0004\bc\u0010dR+\u0010f\u001a\u00020\u00072\u0006\u0010\t\u001a\u00020\u00078F@FX\u0002¢\u0006\u0012\n\u0004\bi\u0010\u0011\u001a\u0004\bg\u0010\u0014\"\u0004\bh\u0010\u0016R+\u0010j\u001a\u00020\u00072\u0006\u0010\t\u001a\u00020\u00078F@FX\u0002¢\u0006\u0012\n\u0004\bm\u0010\u0011\u001a\u0004\bk\u0010\u0014\"\u0004\bl\u0010\u0016R+\u0010n\u001a\u00020\u00072\u0006\u0010\t\u001a\u00020\u00078F@FX\u0002¢\u0006\u0012\n\u0004\bq\u0010\u0011\u001a\u0004\bo\u0010\u0014\"\u0004\bp\u0010\u0016R+\u0010r\u001a\u00020%2\u0006\u0010\t\u001a\u00020%8F@FX\u0002¢\u0006\u0012\n\u0004\bu\u0010,\u001a\u0004\bs\u0010(\"\u0004\bt\u0010*R+\u0010v\u001a\u00020%2\u0006\u0010\t\u001a\u00020%8F@FX\u0002¢\u0006\u0012\n\u0004\by\u0010,\u001a\u0004\bw\u0010(\"\u0004\bx\u0010*R\u000e\u0010z\u001a\u00020%X\u000e¢\u0006\u0002\n\u0000R+\u0010{\u001a\u00020%2\u0006\u0010\t\u001a\u00020%8F@FX\u0002¢\u0006\u0012\n\u0004\b~\u0010\u0011\u001a\u0004\b|\u0010(\"\u0004\b}\u0010*R\u0011\u0010\u001a\u0005\u0018\u00010\u0001X\u000e¢\u0006\u0002\n\u0000R\u0012\u0010\u0001\u001a\u0005\u0018\u00010\u0001X\u000e¢\u0006\u0002\n\u0000R\u0012\u0010\u0001\u001a\u0005\u0018\u00010\u0001X\u000e¢\u0006\u0002\n\u0000R\u0012\u0010\u0001\u001a\u0005\u0018\u00010\u0001X\u000e¢\u0006\u0002\n\u0000R\u0012\u0010\u0001\u001a\u0005\u0018\u00010\u0001X\u000e¢\u0006\u0002\n\u0000R\u0012\u0010\u0001\u001a\u0005\u0018\u00010\u0001X\u000e¢\u0006\u0002\n\u0000R\u0010\u0010\u0001\u001a\u00030\u0001X.¢\u0006\u0002\n\u0000R\u0010\u0010\u0001\u001a\u00030\u0001X.¢\u0006\u0002\n\u0000R\u0010\u0010\u0001\u001a\u00030\u0001X.¢\u0006\u0002\n\u0000R\u0010\u0010\u0001\u001a\u00030\u0001X.¢\u0006\u0002\n\u0000R\u0010\u0010\u0001\u001a\u00030\u0001X.¢\u0006\u0002\n\u0000R\u0010\u0010\u0001\u001a\u00030\u0001X.¢\u0006\u0002\n\u0000R\u0010\u0010\u0001\u001a\u00030\u0001X.¢\u0006\u0002\n\u0000R\u0010\u0010\u0001\u001a\u00030\u0001X.¢\u0006\u0002\n\u0000R\u0010\u0010\u0001\u001a\u00030\u0001X.¢\u0006\u0002\n\u0000R/\u0010\u0001\u001a\u00020%2\u0006\u0010\t\u001a\u00020%8F@FX\u0002¢\u0006\u0015\n\u0005\b\u0001\u0010,\u001a\u0005\b\u0001\u0010(\"\u0005\b\u0001\u0010*R\u000f\u0010\u0001\u001a\u00020\nX\u000e¢\u0006\u0002\n\u0000R?\u0010\u0001\u001a\t\u0012\u0005\u0012\u00030\u00010Y2\r\u0010\t\u001a\t\u0012\u0005\u0012\u00030\u00010Y8B@BX\u0002¢\u0006\u0017\n\u0005\b\u0001\u0010,\u001a\u0006\b\u0001\u0010\u0001\"\u0006\b\u0001\u0010\u0001R/\u0010\u0001\u001a\u00020%2\u0006\u0010\t\u001a\u00020%8F@FX\u0002¢\u0006\u0015\n\u0005\b\u0001\u0010,\u001a\u0005\b\u0001\u0010(\"\u0005\b\u0001\u0010*R3\u0010 \u0001\u001a\u0004\u0018\u00010_2\b\u0010\t\u001a\u0004\u0018\u00010_8F@FX\u0002¢\u0006\u0015\n\u0005\b£\u0001\u0010,\u001a\u0005\b¡\u0001\u0010b\"\u0005\b¢\u0001\u0010dR/\u0010¤\u0001\u001a\u00020\u00072\u0006\u0010\t\u001a\u00020\u00078F@FX\u0002¢\u0006\u0015\n\u0005\b§\u0001\u0010,\u001a\u0005\b¥\u0001\u0010\u0014\"\u0005\b¦\u0001\u0010\u0016R/\u0010¨\u0001\u001a\u00020\n2\u0006\u0010\t\u001a\u00020\n8F@FX\u0002¢\u0006\u0015\n\u0005\b«\u0001\u0010,\u001a\u0005\b©\u0001\u0010\r\"\u0005\bª\u0001\u0010\u000fR/\u0010¬\u0001\u001a\u00020\u00072\u0006\u0010\t\u001a\u00020\u00078F@FX\u0002¢\u0006\u0015\n\u0005\b¯\u0001\u0010\u0011\u001a\u0005\b­\u0001\u0010\u0014\"\u0005\b®\u0001\u0010\u0016R/\u0010°\u0001\u001a\u00020\u00072\u0006\u0010\t\u001a\u00020\u00078F@FX\u0002¢\u0006\u0015\n\u0005\b³\u0001\u0010\u0011\u001a\u0005\b±\u0001\u0010\u0014\"\u0005\b²\u0001\u0010\u0016R/\u0010´\u0001\u001a\u00020\u00072\u0006\u0010\t\u001a\u00020\u00078F@FX\u0002¢\u0006\u0015\n\u0005\b·\u0001\u0010\u0011\u001a\u0005\bµ\u0001\u0010\u0014\"\u0005\b¶\u0001\u0010\u0016R/\u0010¸\u0001\u001a\u00020\u00072\u0006\u0010\t\u001a\u00020\u00078F@FX\u0002¢\u0006\u0015\n\u0005\b»\u0001\u0010\u0011\u001a\u0005\b¹\u0001\u0010\u0014\"\u0005\bº\u0001\u0010\u0016R/\u0010¼\u0001\u001a\u00020\u00072\u0006\u0010\t\u001a\u00020\u00078F@FX\u0002¢\u0006\u0015\n\u0005\b¿\u0001\u0010\u0011\u001a\u0005\b½\u0001\u0010\u0014\"\u0005\b¾\u0001\u0010\u0016R/\u0010À\u0001\u001a\u00020\u00072\u0006\u0010\t\u001a\u00020\u00078F@FX\u0002¢\u0006\u0015\n\u0005\bÃ\u0001\u0010\u0011\u001a\u0005\bÁ\u0001\u0010\u0014\"\u0005\bÂ\u0001\u0010\u0016R/\u0010Ä\u0001\u001a\u00020%2\u0006\u0010\t\u001a\u00020%8F@FX\u0002¢\u0006\u0015\n\u0005\bÇ\u0001\u0010\u0011\u001a\u0005\bÅ\u0001\u0010(\"\u0005\bÆ\u0001\u0010*R/\u0010È\u0001\u001a\u00020%2\u0006\u0010\t\u001a\u00020%8F@FX\u0002¢\u0006\u0015\n\u0005\bË\u0001\u0010,\u001a\u0005\bÉ\u0001\u0010(\"\u0005\bÊ\u0001\u0010*¨\u0006\u0002"}, mo22062d2 = {"Lcom/mobiroller/views/StatusView;", "Landroid/view/View;", "context", "Landroid/content/Context;", "attrs", "Landroid/util/AttributeSet;", "defStyleAttr", "", "(Landroid/content/Context;Landroid/util/AttributeSet;I)V", "<set-?>", "", "alignStatusWithCurrent", "getAlignStatusWithCurrent", "()Z", "setAlignStatusWithCurrent", "(Z)V", "alignStatusWithCurrent$delegate", "Lcom/mobiroller/views/StatusView$OnValidateProp;", "circleColorType", "getCircleColorType", "()I", "setCircleColorType", "(I)V", "circleColorType$delegate", "Lkotlin/properties/ReadWriteProperty;", "circleFillColor", "getCircleFillColor", "setCircleFillColor", "circleFillColor$delegate", "circleFillColorCurrent", "getCircleFillColorCurrent", "setCircleFillColorCurrent", "circleFillColorCurrent$delegate", "circleFillColorIncomplete", "getCircleFillColorIncomplete", "setCircleFillColorIncomplete", "circleFillColorIncomplete$delegate", "", "circleRadius", "getCircleRadius", "()F", "setCircleRadius", "(F)V", "circleRadius$delegate", "Lcom/mobiroller/views/StatusView$OnLayoutProp;", "circleStrokeColor", "getCircleStrokeColor", "setCircleStrokeColor", "circleStrokeColor$delegate", "circleStrokeColorCurrent", "getCircleStrokeColorCurrent", "setCircleStrokeColorCurrent", "circleStrokeColorCurrent$delegate", "circleStrokeColorIncomplete", "getCircleStrokeColorIncomplete", "setCircleStrokeColorIncomplete", "circleStrokeColorIncomplete$delegate", "circleStrokeWidth", "getCircleStrokeWidth", "setCircleStrokeWidth", "circleStrokeWidth$delegate", "Landroid/graphics/drawable/Drawable;", "completeDrawable", "getCompleteDrawable", "()Landroid/graphics/drawable/Drawable;", "setCompleteDrawable", "(Landroid/graphics/drawable/Drawable;)V", "completeDrawable$delegate", "currentCount", "getCurrentCount", "setCurrentCount", "currentCount$delegate", "currentDrawable", "getCurrentDrawable", "setCurrentDrawable", "currentDrawable$delegate", "currentStatusRadius", "getCurrentStatusRadius", "setCurrentStatusRadius", "currentStatusRadius$delegate", "value", "currentStatusZoom", "getCurrentStatusZoom", "setCurrentStatusZoom", "drawLabels", "getDrawLabels", "setDrawLabels", "drawLabels$delegate", "drawingData", "", "Lcom/mobiroller/views/StatusView$Item;", "incompleteDrawable", "getIncompleteDrawable", "setIncompleteDrawable", "incompleteDrawable$delegate", "Landroid/graphics/Typeface;", "labelsTypeface", "getLabelsTypeface", "()Landroid/graphics/Typeface;", "setLabelsTypeface", "(Landroid/graphics/Typeface;)V", "labelsTypeface$delegate", "lineColor", "getLineColor", "setLineColor", "lineColor$delegate", "lineColorCurrent", "getLineColorCurrent", "setLineColorCurrent", "lineColorCurrent$delegate", "lineColorIncomplete", "getLineColorIncomplete", "setLineColorIncomplete", "lineColorIncomplete$delegate", "lineGap", "getLineGap", "setLineGap", "lineGap$delegate", "lineLength", "getLineLength", "setLineLength", "lineLength$delegate", "lineLengthComputed", "lineWidth", "getLineWidth", "setLineWidth", "lineWidth$delegate", "mCircleFillPaint", "Landroid/graphics/Paint;", "mCircleFillPaintCurrent", "mCircleFillPaintIncomplete", "mCircleStrokePaint", "mCircleStrokePaintCurrent", "mCircleStrokePaintIncomplete", "mLinePaint", "mLinePaintCurrent", "mLinePaintIncomplete", "mTextPaintLabelCurrent", "Landroid/text/TextPaint;", "mTextPaintLabels", "mTextPaintLabelsIncomplete", "mTextPaintStatus", "mTextPaintStatusCurrent", "mTextPaintStatusIncomplete", "minStatusAdjacentMargin", "getMinStatusAdjacentMargin", "setMinStatusAdjacentMargin", "minStatusAdjacentMargin$delegate", "propsIntialisedOnce", "Lcom/mobiroller/views/StatusView$StatusInfo;", "statusData", "getStatusData", "()Ljava/util/List;", "setStatusData", "(Ljava/util/List;)V", "statusData$delegate", "statusTopMargin", "getStatusTopMargin", "setStatusTopMargin", "statusTopMargin$delegate", "statusTypeface", "getStatusTypeface", "setStatusTypeface", "statusTypeface$delegate", "stepCount", "getStepCount", "setStepCount", "stepCount$delegate", "strictObeyLineLength", "getStrictObeyLineLength", "setStrictObeyLineLength", "strictObeyLineLength$delegate", "textColorLabelCurrent", "getTextColorLabelCurrent", "setTextColorLabelCurrent", "textColorLabelCurrent$delegate", "textColorLabels", "getTextColorLabels", "setTextColorLabels", "textColorLabels$delegate", "textColorLabelsIncomplete", "getTextColorLabelsIncomplete", "setTextColorLabelsIncomplete", "textColorLabelsIncomplete$delegate", "textColorStatus", "getTextColorStatus", "setTextColorStatus", "textColorStatus$delegate", "textColorStatusCurrent", "getTextColorStatusCurrent", "setTextColorStatusCurrent", "textColorStatusCurrent$delegate", "textColorStatusIncomplete", "getTextColorStatusIncomplete", "setTextColorStatusIncomplete", "textColorStatusIncomplete$delegate", "textSizeLabels", "getTextSizeLabels", "setTextSizeLabels", "textSizeLabels$delegate", "textSizeStatus", "getTextSizeStatus", "setTextSizeStatus", "textSizeStatus$delegate", "containsFlag", "flagSet", "flag", "currentCountIndex", "getScrollPosForStep", "count", "getStaticLayout", "Landroid/text/StaticLayout;", "text", "", "textPaint", "width", "getStatusList", "", "getStatusTextWidthInfo", "Lcom/mobiroller/views/StatusView$StatusTextWidthInfo;", "list", "paint", "getStatusWidth", "getSuggestedMinimumHeight", "getSuggestedMinimumWidth", "getTextWidth", "initCirclePaints", "", "isShowingCurrentStatus", "isShowingIncompleteStatus", "minStatusWidth", "pos", "minStatusWidthExtremes", "onDraw", "canvas", "Landroid/graphics/Canvas;", "onLayout", "changed", "left", "top", "right", "bottom", "onMeasure", "widthMeasureSpec", "heightMeasureSpec", "setDrawingDimensions", "setLabelsHeight", "labelInfo", "setStatusList", "setWidthDataForObeyingLineLength", "setWidthDataForObeyingStatusText", "stepCountIndex", "pxValue", "unit", "CircleItem", "Companion", "DrawableItem", "Item", "LabelItemText", "LineItem", "OnLayoutProp", "OnValidateProp", "StatusInfo", "StatusItemText", "StatusTextWidthInfo", "StatusWidth", "app_regularProductionRelease"}, mo22063k = 1, mo22064mv = {1, 1, 16})
/* compiled from: StatusView.kt */
public final class StatusView extends View {
    static final /* synthetic */ KProperty[] $$delegatedProperties;
    public static final int CIRCLE_COLOR_TYPE_FILL = 1;
    public static final int CIRCLE_COLOR_TYPE_STROKE = 2;
    public static final Companion Companion = new Companion(null);
    public static final int INVALID_STATUS_COUNT = -1;
    private final OnValidateProp alignStatusWithCurrent$delegate;
    private final ReadWriteProperty circleColorType$delegate;
    private final OnValidateProp circleFillColor$delegate;
    private final OnValidateProp circleFillColorCurrent$delegate;
    private final OnValidateProp circleFillColorIncomplete$delegate;
    private final OnLayoutProp circleRadius$delegate;
    private final OnValidateProp circleStrokeColor$delegate;
    private final OnValidateProp circleStrokeColorCurrent$delegate;
    private final OnValidateProp circleStrokeColorIncomplete$delegate;
    private final OnLayoutProp circleStrokeWidth$delegate;
    private final OnValidateProp completeDrawable$delegate;
    private final OnLayoutProp currentCount$delegate;
    private final OnValidateProp currentDrawable$delegate;
    private final OnLayoutProp currentStatusRadius$delegate;
    private float currentStatusZoom;
    private final OnValidateProp drawLabels$delegate;
    /* access modifiers changed from: private */
    public List<Item> drawingData;
    private final OnValidateProp incompleteDrawable$delegate;
    private final OnLayoutProp labelsTypeface$delegate;
    private final OnValidateProp lineColor$delegate;
    private final OnValidateProp lineColorCurrent$delegate;
    private final OnValidateProp lineColorIncomplete$delegate;
    private final OnLayoutProp lineGap$delegate;
    private final OnLayoutProp lineLength$delegate;
    private float lineLengthComputed;
    private final OnValidateProp lineWidth$delegate;
    /* access modifiers changed from: private */
    public Paint mCircleFillPaint;
    /* access modifiers changed from: private */
    public Paint mCircleFillPaintCurrent;
    /* access modifiers changed from: private */
    public Paint mCircleFillPaintIncomplete;
    /* access modifiers changed from: private */
    public Paint mCircleStrokePaint;
    /* access modifiers changed from: private */
    public Paint mCircleStrokePaintCurrent;
    /* access modifiers changed from: private */
    public Paint mCircleStrokePaintIncomplete;
    /* access modifiers changed from: private */
    public Paint mLinePaint;
    /* access modifiers changed from: private */
    public Paint mLinePaintCurrent;
    /* access modifiers changed from: private */
    public Paint mLinePaintIncomplete;
    /* access modifiers changed from: private */
    public TextPaint mTextPaintLabelCurrent;
    /* access modifiers changed from: private */
    public TextPaint mTextPaintLabels;
    /* access modifiers changed from: private */
    public TextPaint mTextPaintLabelsIncomplete;
    /* access modifiers changed from: private */
    public TextPaint mTextPaintStatus;
    /* access modifiers changed from: private */
    public TextPaint mTextPaintStatusCurrent;
    /* access modifiers changed from: private */
    public TextPaint mTextPaintStatusIncomplete;
    private final OnLayoutProp minStatusAdjacentMargin$delegate;
    /* access modifiers changed from: private */
    public boolean propsIntialisedOnce;
    private final OnLayoutProp statusData$delegate;
    private final OnLayoutProp statusTopMargin$delegate;
    private final OnLayoutProp statusTypeface$delegate;
    private final OnLayoutProp stepCount$delegate;
    private final OnLayoutProp strictObeyLineLength$delegate;
    private final OnValidateProp textColorLabelCurrent$delegate;
    private final OnValidateProp textColorLabels$delegate;
    private final OnValidateProp textColorLabelsIncomplete$delegate;
    private final OnValidateProp textColorStatus$delegate;
    private final OnValidateProp textColorStatusCurrent$delegate;
    private final OnValidateProp textColorStatusIncomplete$delegate;
    private final OnValidateProp textSizeLabels$delegate;
    private final OnLayoutProp textSizeStatus$delegate;

    @Metadata(mo22060bv = {1, 0, 3}, mo22061d1 = {"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0007\n\u0000\n\u0002\u0018\u0002\n\u0002\b\n\b\u0002\u0018\u00002\u00020\u0001B)\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\b\u0010\u0006\u001a\u0004\u0018\u00010\u0007\u0012\b\u0010\b\u001a\u0004\u0018\u00010\u0007¢\u0006\u0002\u0010\tR\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u000bR\u0013\u0010\b\u001a\u0004\u0018\u00010\u0007¢\u0006\b\n\u0000\u001a\u0004\b\f\u0010\rR\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\u000fR\u0013\u0010\u0006\u001a\u0004\u0018\u00010\u0007¢\u0006\b\n\u0000\u001a\u0004\b\u0010\u0010\r¨\u0006\u0011"}, mo22062d2 = {"Lcom/mobiroller/views/StatusView$CircleItem;", "", "center", "Landroid/graphics/PointF;", "radius", "", "strokePaint", "Landroid/graphics/Paint;", "fillPaint", "(Landroid/graphics/PointF;FLandroid/graphics/Paint;Landroid/graphics/Paint;)V", "getCenter", "()Landroid/graphics/PointF;", "getFillPaint", "()Landroid/graphics/Paint;", "getRadius", "()F", "getStrokePaint", "app_regularProductionRelease"}, mo22063k = 1, mo22064mv = {1, 1, 16})
    /* compiled from: StatusView.kt */
    private static final class CircleItem {
        private final PointF center;
        private final Paint fillPaint;
        private final float radius;
        private final Paint strokePaint;

        public CircleItem(PointF pointF, float f, Paint paint, Paint paint2) {
            Intrinsics.checkParameterIsNotNull(pointF, TtmlNode.CENTER);
            this.center = pointF;
            this.radius = f;
            this.strokePaint = paint;
            this.fillPaint = paint2;
        }

        public final PointF getCenter() {
            return this.center;
        }

        public final Paint getFillPaint() {
            return this.fillPaint;
        }

        public final float getRadius() {
            return this.radius;
        }

        public final Paint getStrokePaint() {
            return this.strokePaint;
        }
    }

    @Metadata(mo22060bv = {1, 0, 3}, mo22061d1 = {"\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0003\b\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002R\u000e\u0010\u0003\u001a\u00020\u0004XT¢\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0004XT¢\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0004XT¢\u0006\u0002\n\u0000¨\u0006\u0007"}, mo22062d2 = {"Lcom/mobiroller/views/StatusView$Companion;", "", "()V", "CIRCLE_COLOR_TYPE_FILL", "", "CIRCLE_COLOR_TYPE_STROKE", "INVALID_STATUS_COUNT", "app_regularProductionRelease"}, mo22063k = 1, mo22064mv = {1, 1, 16})
    /* compiled from: StatusView.kt */
    public static final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    @Metadata(mo22060bv = {1, 0, 3}, mo22061d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\b\u0002\u0018\u00002\u00020\u0001B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006R\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\bR\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\n¨\u0006\u000b"}, mo22062d2 = {"Lcom/mobiroller/views/StatusView$DrawableItem;", "", "rect", "Landroid/graphics/Rect;", "drawable", "Landroid/graphics/drawable/Drawable;", "(Landroid/graphics/Rect;Landroid/graphics/drawable/Drawable;)V", "getDrawable", "()Landroid/graphics/drawable/Drawable;", "getRect", "()Landroid/graphics/Rect;", "app_regularProductionRelease"}, mo22063k = 1, mo22064mv = {1, 1, 16})
    /* compiled from: StatusView.kt */
    private static final class DrawableItem {
        private final Drawable drawable;
        private final Rect rect;

        public DrawableItem(Rect rect2, Drawable drawable2) {
            Intrinsics.checkParameterIsNotNull(rect2, "rect");
            Intrinsics.checkParameterIsNotNull(drawable2, "drawable");
            this.rect = rect2;
            this.drawable = drawable2;
        }

        public final Drawable getDrawable() {
            return this.drawable;
        }

        public final Rect getRect() {
            return this.rect;
        }
    }

    @Metadata(mo22060bv = {1, 0, 3}, mo22061d1 = {"\u0000$\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\n\b\u0002\u0018\u00002\u00020\u0001B-\u0012\b\u0010\u0002\u001a\u0004\u0018\u00010\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\b\u0010\u0006\u001a\u0004\u0018\u00010\u0007\u0012\n\b\u0002\u0010\b\u001a\u0004\u0018\u00010\t¢\u0006\u0002\u0010\nR\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\fR\u0013\u0010\b\u001a\u0004\u0018\u00010\t¢\u0006\b\n\u0000\u001a\u0004\b\r\u0010\u000eR\u0013\u0010\u0006\u001a\u0004\u0018\u00010\u0007¢\u0006\b\n\u0000\u001a\u0004\b\u000f\u0010\u0010R\u0013\u0010\u0002\u001a\u0004\u0018\u00010\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0011\u0010\u0012¨\u0006\u0013"}, mo22062d2 = {"Lcom/mobiroller/views/StatusView$Item;", "", "textData", "Lcom/mobiroller/views/StatusView$LabelItemText;", "circleItem", "Lcom/mobiroller/views/StatusView$CircleItem;", "lineItem", "Lcom/mobiroller/views/StatusView$LineItem;", "labelData", "Lcom/mobiroller/views/StatusView$StatusItemText;", "(Lcom/mobiroller/views/StatusView$LabelItemText;Lcom/mobiroller/views/StatusView$CircleItem;Lcom/mobiroller/views/StatusView$LineItem;Lcom/mobiroller/views/StatusView$StatusItemText;)V", "getCircleItem", "()Lcom/mobiroller/views/StatusView$CircleItem;", "getLabelData", "()Lcom/mobiroller/views/StatusView$StatusItemText;", "getLineItem", "()Lcom/mobiroller/views/StatusView$LineItem;", "getTextData", "()Lcom/mobiroller/views/StatusView$LabelItemText;", "app_regularProductionRelease"}, mo22063k = 1, mo22064mv = {1, 1, 16})
    /* compiled from: StatusView.kt */
    private static final class Item {
        private final CircleItem circleItem;
        private final StatusItemText labelData;
        private final LineItem lineItem;
        private final LabelItemText textData;

        public Item(LabelItemText labelItemText, CircleItem circleItem2, LineItem lineItem2, StatusItemText statusItemText) {
            Intrinsics.checkParameterIsNotNull(circleItem2, "circleItem");
            this.textData = labelItemText;
            this.circleItem = circleItem2;
            this.lineItem = lineItem2;
            this.labelData = statusItemText;
        }

        public /* synthetic */ Item(LabelItemText labelItemText, CircleItem circleItem2, LineItem lineItem2, StatusItemText statusItemText, int i, DefaultConstructorMarker defaultConstructorMarker) {
            if ((i & 8) != 0) {
                statusItemText = null;
            }
            this(labelItemText, circleItem2, lineItem2, statusItemText);
        }

        public final CircleItem getCircleItem() {
            return this.circleItem;
        }

        public final StatusItemText getLabelData() {
            return this.labelData;
        }

        public final LineItem getLineItem() {
            return this.lineItem;
        }

        public final LabelItemText getTextData() {
            return this.textData;
        }
    }

    @Metadata(mo22060bv = {1, 0, 3}, mo22061d1 = {"\u0000&\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0007\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u000b\b\u0002\u0018\u00002\u00020\u0001B=\u0012\n\b\u0002\u0010\u0002\u001a\u0004\u0018\u00010\u0003\u0012\n\b\u0002\u0010\u0004\u001a\u0004\u0018\u00010\u0005\u0012\b\b\u0002\u0010\u0006\u001a\u00020\u0007\u0012\b\b\u0002\u0010\b\u001a\u00020\u0007\u0012\n\b\u0002\u0010\t\u001a\u0004\u0018\u00010\n¢\u0006\u0002\u0010\u000bR\u0013\u0010\t\u001a\u0004\u0018\u00010\n¢\u0006\b\n\u0000\u001a\u0004\b\f\u0010\rR\u0013\u0010\u0004\u001a\u0004\u0018\u00010\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\u000fR\u0013\u0010\u0002\u001a\u0004\u0018\u00010\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0010\u0010\u0011R\u0011\u0010\u0006\u001a\u00020\u0007¢\u0006\b\n\u0000\u001a\u0004\b\u0012\u0010\u0013R\u0011\u0010\b\u001a\u00020\u0007¢\u0006\b\n\u0000\u001a\u0004\b\u0014\u0010\u0013¨\u0006\u0015"}, mo22062d2 = {"Lcom/mobiroller/views/StatusView$LabelItemText;", "", "text", "", "paint", "Landroid/graphics/Paint;", "x", "", "y", "drawableItem", "Lcom/mobiroller/views/StatusView$DrawableItem;", "(Ljava/lang/String;Landroid/graphics/Paint;FFLcom/mobiroller/views/StatusView$DrawableItem;)V", "getDrawableItem", "()Lcom/mobiroller/views/StatusView$DrawableItem;", "getPaint", "()Landroid/graphics/Paint;", "getText", "()Ljava/lang/String;", "getX", "()F", "getY", "app_regularProductionRelease"}, mo22063k = 1, mo22064mv = {1, 1, 16})
    /* compiled from: StatusView.kt */
    private static final class LabelItemText {
        private final DrawableItem drawableItem;
        private final Paint paint;
        private final String text;

        /* renamed from: x */
        private final float f2227x;

        /* renamed from: y */
        private final float f2228y;

        public LabelItemText() {
            this(null, null, 0.0f, 0.0f, null, 31, null);
        }

        public LabelItemText(String str, Paint paint2, float f, float f2, DrawableItem drawableItem2) {
            this.text = str;
            this.paint = paint2;
            this.f2227x = f;
            this.f2228y = f2;
            this.drawableItem = drawableItem2;
        }

        public /* synthetic */ LabelItemText(String str, Paint paint2, float f, float f2, DrawableItem drawableItem2, int i, DefaultConstructorMarker defaultConstructorMarker) {
            if ((i & 1) != 0) {
                str = null;
            }
            if ((i & 2) != 0) {
                paint2 = null;
            }
            Paint paint3 = paint2;
            float f3 = 0.0f;
            float f4 = (i & 4) != 0 ? 0.0f : f;
            if ((i & 8) == 0) {
                f3 = f2;
            }
            if ((i & 16) != 0) {
                drawableItem2 = null;
            }
            this(str, paint3, f4, f3, drawableItem2);
        }

        public final DrawableItem getDrawableItem() {
            return this.drawableItem;
        }

        public final Paint getPaint() {
            return this.paint;
        }

        public final String getText() {
            return this.text;
        }

        public final float getX() {
            return this.f2227x;
        }

        public final float getY() {
            return this.f2228y;
        }
    }

    @Metadata(mo22060bv = {1, 0, 3}, mo22061d1 = {"\u0000\u001a\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0007\b\u0002\u0018\u00002\u00020\u0001B\u001d\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0003\u0012\u0006\u0010\u0005\u001a\u00020\u0006¢\u0006\u0002\u0010\u0007R\u0011\u0010\u0004\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\b\u0010\tR\u0011\u0010\u0005\u001a\u00020\u0006¢\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u000bR\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\f\u0010\t¨\u0006\r"}, mo22062d2 = {"Lcom/mobiroller/views/StatusView$LineItem;", "", "start", "Landroid/graphics/PointF;", "end", "paint", "Landroid/graphics/Paint;", "(Landroid/graphics/PointF;Landroid/graphics/PointF;Landroid/graphics/Paint;)V", "getEnd", "()Landroid/graphics/PointF;", "getPaint", "()Landroid/graphics/Paint;", "getStart", "app_regularProductionRelease"}, mo22063k = 1, mo22064mv = {1, 1, 16})
    /* compiled from: StatusView.kt */
    private static final class LineItem {
        private final PointF end;
        private final Paint paint;
        private final PointF start;

        public LineItem(PointF pointF, PointF pointF2, Paint paint2) {
            Intrinsics.checkParameterIsNotNull(pointF, TtmlNode.START);
            Intrinsics.checkParameterIsNotNull(pointF2, TtmlNode.END);
            Intrinsics.checkParameterIsNotNull(paint2, "paint");
            this.start = pointF;
            this.end = pointF2;
            this.paint = paint2;
        }

        public final PointF getEnd() {
            return this.end;
        }

        public final Paint getPaint() {
            return this.paint;
        }

        public final PointF getStart() {
            return this.start;
        }
    }

    @Metadata(mo22060bv = {1, 0, 3}, mo22061d1 = {"\u0000\"\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\b\u0004\u0018\u0000*\u0004\b\u0000\u0010\u00012\u00020\u0002B\u001d\u0012\u0006\u0010\u0003\u001a\u00028\u0000\u0012\u000e\b\u0002\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005¢\u0006\u0002\u0010\u0007J$\u0010\t\u001a\u00028\u00002\b\u0010\n\u001a\u0004\u0018\u00010\u00022\n\u0010\u000b\u001a\u0006\u0012\u0002\b\u00030\fH\u0002¢\u0006\u0002\u0010\rJ,\u0010\u000e\u001a\u00020\u00062\b\u0010\n\u001a\u0004\u0018\u00010\u00022\n\u0010\u000b\u001a\u0006\u0012\u0002\b\u00030\f2\u0006\u0010\u000f\u001a\u00028\u0000H\u0002¢\u0006\u0002\u0010\u0010R\u0010\u0010\u0003\u001a\u00028\u0000X\u000e¢\u0006\u0004\n\u0002\u0010\bR\u0014\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005X\u000e¢\u0006\u0002\n\u0000¨\u0006\u0011"}, mo22062d2 = {"Lcom/mobiroller/views/StatusView$OnLayoutProp;", "T", "", "field", "func", "Lkotlin/Function0;", "", "(Lcom/mobiroller/views/StatusView;Ljava/lang/Object;Lkotlin/jvm/functions/Function0;)V", "Ljava/lang/Object;", "getValue", "thisRef", "p", "Lkotlin/reflect/KProperty;", "(Ljava/lang/Object;Lkotlin/reflect/KProperty;)Ljava/lang/Object;", "setValue", "v", "(Ljava/lang/Object;Lkotlin/reflect/KProperty;Ljava/lang/Object;)V", "app_regularProductionRelease"}, mo22063k = 1, mo22064mv = {1, 1, 16})
    /* compiled from: StatusView.kt */
    public final class OnLayoutProp<T> {
        private T field;
        private Function0<Unit> func;
        final /* synthetic */ StatusView this$0;

        public OnLayoutProp(StatusView statusView, T t, Function0<Unit> function0) {
            Intrinsics.checkParameterIsNotNull(function0, "func");
            this.this$0 = statusView;
            this.field = t;
            this.func = function0;
        }

        public /* synthetic */ OnLayoutProp(StatusView statusView, Object obj, Function0 function0, int i, DefaultConstructorMarker defaultConstructorMarker) {
            if ((i & 2) != 0) {
                function0 = C44591.INSTANCE;
            }
            this(statusView, obj, function0);
        }

        public final void setValue(Object obj, KProperty<?> kProperty, T t) {
            Intrinsics.checkParameterIsNotNull(kProperty, TtmlNode.TAG_P);
            this.field = t;
            if (this.this$0.propsIntialisedOnce) {
                this.this$0.drawingData.clear();
                this.func.invoke();
                this.this$0.requestLayout();
            }
        }

        public final T getValue(Object obj, KProperty<?> kProperty) {
            Intrinsics.checkParameterIsNotNull(kProperty, TtmlNode.TAG_P);
            return this.field;
        }
    }

    @Metadata(mo22060bv = {1, 0, 3}, mo22061d1 = {"\u0000\"\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\b\u0004\u0018\u0000*\u0004\b\u0000\u0010\u00012\u00020\u0002B\u001d\u0012\u0006\u0010\u0003\u001a\u00028\u0000\u0012\u000e\b\u0002\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005¢\u0006\u0002\u0010\u0007J$\u0010\t\u001a\u00028\u00002\b\u0010\n\u001a\u0004\u0018\u00010\u00022\n\u0010\u000b\u001a\u0006\u0012\u0002\b\u00030\fH\u0002¢\u0006\u0002\u0010\rJ,\u0010\u000e\u001a\u00020\u00062\b\u0010\n\u001a\u0004\u0018\u00010\u00022\n\u0010\u000b\u001a\u0006\u0012\u0002\b\u00030\f2\u0006\u0010\u000f\u001a\u00028\u0000H\u0002¢\u0006\u0002\u0010\u0010R\u0010\u0010\u0003\u001a\u00028\u0000X\u000e¢\u0006\u0004\n\u0002\u0010\bR\u0014\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005X\u000e¢\u0006\u0002\n\u0000¨\u0006\u0011"}, mo22062d2 = {"Lcom/mobiroller/views/StatusView$OnValidateProp;", "T", "", "field", "func", "Lkotlin/Function0;", "", "(Lcom/mobiroller/views/StatusView;Ljava/lang/Object;Lkotlin/jvm/functions/Function0;)V", "Ljava/lang/Object;", "getValue", "thisRef", "p", "Lkotlin/reflect/KProperty;", "(Ljava/lang/Object;Lkotlin/reflect/KProperty;)Ljava/lang/Object;", "setValue", "v", "(Ljava/lang/Object;Lkotlin/reflect/KProperty;Ljava/lang/Object;)V", "app_regularProductionRelease"}, mo22063k = 1, mo22064mv = {1, 1, 16})
    /* compiled from: StatusView.kt */
    public final class OnValidateProp<T> {
        private T field;
        private Function0<Unit> func;
        final /* synthetic */ StatusView this$0;

        public OnValidateProp(StatusView statusView, T t, Function0<Unit> function0) {
            Intrinsics.checkParameterIsNotNull(function0, "func");
            this.this$0 = statusView;
            this.field = t;
            this.func = function0;
        }

        public /* synthetic */ OnValidateProp(StatusView statusView, Object obj, Function0 function0, int i, DefaultConstructorMarker defaultConstructorMarker) {
            if ((i & 2) != 0) {
                function0 = C44601.INSTANCE;
            }
            this(statusView, obj, function0);
        }

        public final void setValue(Object obj, KProperty<?> kProperty, T t) {
            Intrinsics.checkParameterIsNotNull(kProperty, TtmlNode.TAG_P);
            this.field = t;
            if (this.this$0.propsIntialisedOnce) {
                this.func.invoke();
                this.this$0.invalidate();
            }
        }

        public final T getValue(Object obj, KProperty<?> kProperty) {
            Intrinsics.checkParameterIsNotNull(kProperty, TtmlNode.TAG_P);
            return this.field;
        }
    }

    @Metadata(mo22060bv = {1, 0, 3}, mo22061d1 = {"\u0000 \n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u0007\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u000e\b\u0002\u0018\u00002\u00020\u0001B-\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u0004\u001a\u00020\u0005\u0012\b\b\u0002\u0010\u0006\u001a\u00020\u0005\u0012\n\b\u0002\u0010\u0007\u001a\u0004\u0018\u00010\b¢\u0006\u0002\u0010\tR\u001a\u0010\u0006\u001a\u00020\u0005X\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\n\u0010\u000b\"\u0004\b\f\u0010\rR\u001c\u0010\u0007\u001a\u0004\u0018\u00010\bX\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u000e\u0010\u000f\"\u0004\b\u0010\u0010\u0011R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0012\u0010\u0013R\u001a\u0010\u0004\u001a\u00020\u0005X\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0014\u0010\u000b\"\u0004\b\u0015\u0010\r¨\u0006\u0016"}, mo22062d2 = {"Lcom/mobiroller/views/StatusView$StatusInfo;", "", "text", "", "width", "", "height", "staticLayout", "Landroid/text/StaticLayout;", "(Ljava/lang/String;FFLandroid/text/StaticLayout;)V", "getHeight", "()F", "setHeight", "(F)V", "getStaticLayout", "()Landroid/text/StaticLayout;", "setStaticLayout", "(Landroid/text/StaticLayout;)V", "getText", "()Ljava/lang/String;", "getWidth", "setWidth", "app_regularProductionRelease"}, mo22063k = 1, mo22064mv = {1, 1, 16})
    /* compiled from: StatusView.kt */
    private static final class StatusInfo {
        private float height;
        private StaticLayout staticLayout;
        private final String text;
        private float width;

        public StatusInfo(String str, float f, float f2, StaticLayout staticLayout2) {
            Intrinsics.checkParameterIsNotNull(str, "text");
            this.text = str;
            this.width = f;
            this.height = f2;
            this.staticLayout = staticLayout2;
        }

        public /* synthetic */ StatusInfo(String str, float f, float f2, StaticLayout staticLayout2, int i, DefaultConstructorMarker defaultConstructorMarker) {
            if ((i & 2) != 0) {
                f = 0.0f;
            }
            if ((i & 4) != 0) {
                f2 = 0.0f;
            }
            if ((i & 8) != 0) {
                staticLayout2 = null;
            }
            this(str, f, f2, staticLayout2);
        }

        public final float getHeight() {
            return this.height;
        }

        public final StaticLayout getStaticLayout() {
            return this.staticLayout;
        }

        public final String getText() {
            return this.text;
        }

        public final float getWidth() {
            return this.width;
        }

        public final void setHeight(float f) {
            this.height = f;
        }

        public final void setStaticLayout(StaticLayout staticLayout2) {
            this.staticLayout = staticLayout2;
        }

        public final void setWidth(float f) {
            this.width = f;
        }
    }

    @Metadata(mo22060bv = {1, 0, 3}, mo22061d1 = {"\u0000 \n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0007\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\t\b\u0002\u0018\u00002\u00020\u0001B1\u0012\b\b\u0002\u0010\u0002\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u0004\u001a\u00020\u0003\u0012\n\b\u0002\u0010\u0005\u001a\u0004\u0018\u00010\u0006\u0012\n\b\u0002\u0010\u0007\u001a\u0004\u0018\u00010\b¢\u0006\u0002\u0010\tR\u0013\u0010\u0005\u001a\u0004\u0018\u00010\u0006¢\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u000bR\u0013\u0010\u0007\u001a\u0004\u0018\u00010\b¢\u0006\b\n\u0000\u001a\u0004\b\f\u0010\rR\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\u000fR\u0011\u0010\u0004\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0010\u0010\u000f¨\u0006\u0011"}, mo22062d2 = {"Lcom/mobiroller/views/StatusView$StatusItemText;", "", "x", "", "y", "paint", "Landroid/text/TextPaint;", "staticLayout", "Landroid/text/StaticLayout;", "(FFLandroid/text/TextPaint;Landroid/text/StaticLayout;)V", "getPaint", "()Landroid/text/TextPaint;", "getStaticLayout", "()Landroid/text/StaticLayout;", "getX", "()F", "getY", "app_regularProductionRelease"}, mo22063k = 1, mo22064mv = {1, 1, 16})
    /* compiled from: StatusView.kt */
    private static final class StatusItemText {
        private final TextPaint paint;
        private final StaticLayout staticLayout;

        /* renamed from: x */
        private final float f2229x;

        /* renamed from: y */
        private final float f2230y;

        public StatusItemText() {
            this(0.0f, 0.0f, null, null, 15, null);
        }

        public StatusItemText(float f, float f2, TextPaint textPaint, StaticLayout staticLayout2) {
            this.f2229x = f;
            this.f2230y = f2;
            this.paint = textPaint;
            this.staticLayout = staticLayout2;
        }

        public /* synthetic */ StatusItemText(float f, float f2, TextPaint textPaint, StaticLayout staticLayout2, int i, DefaultConstructorMarker defaultConstructorMarker) {
            if ((i & 1) != 0) {
                f = 0.0f;
            }
            if ((i & 2) != 0) {
                f2 = 0.0f;
            }
            if ((i & 4) != 0) {
                textPaint = null;
            }
            if ((i & 8) != 0) {
                staticLayout2 = null;
            }
            this(f, f2, textPaint, staticLayout2);
        }

        public final TextPaint getPaint() {
            return this.paint;
        }

        public final StaticLayout getStaticLayout() {
            return this.staticLayout;
        }

        public final float getX() {
            return this.f2229x;
        }

        public final float getY() {
            return this.f2230y;
        }
    }

    @Metadata(mo22060bv = {1, 0, 3}, mo22061d1 = {"\u0000\u001a\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0007\n\u0002\b\u000f\u0018\u00002\u00020\u0001B-\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\n\b\u0002\u0010\u0004\u001a\u0004\u0018\u00010\u0003\u0012\b\b\u0002\u0010\u0005\u001a\u00020\u0006\u0012\b\b\u0002\u0010\u0007\u001a\u00020\u0006¢\u0006\u0002\u0010\bR\u001a\u0010\u0005\u001a\u00020\u0006X\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\t\u0010\n\"\u0004\b\u000b\u0010\fR\u001a\u0010\u0007\u001a\u00020\u0006X\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\r\u0010\n\"\u0004\b\u000e\u0010\fR\u001c\u0010\u0004\u001a\u0004\u0018\u00010\u0003X\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u000f\u0010\u0010\"\u0004\b\u0011\u0010\u0012R\u001a\u0010\u0002\u001a\u00020\u0003X\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0013\u0010\u0010\"\u0004\b\u0014\u0010\u0012¨\u0006\u0015"}, mo22062d2 = {"Lcom/mobiroller/views/StatusView$StatusTextWidthInfo;", "", "widestStatus", "Lcom/mobiroller/views/StatusView$StatusWidth;", "subordinateWidestStatus", "extremeLeftStatusWidth", "", "extremeRightStatusWidth", "(Lcom/mobiroller/views/StatusView$StatusWidth;Lcom/mobiroller/views/StatusView$StatusWidth;FF)V", "getExtremeLeftStatusWidth", "()F", "setExtremeLeftStatusWidth", "(F)V", "getExtremeRightStatusWidth", "setExtremeRightStatusWidth", "getSubordinateWidestStatus", "()Lcom/mobiroller/views/StatusView$StatusWidth;", "setSubordinateWidestStatus", "(Lcom/mobiroller/views/StatusView$StatusWidth;)V", "getWidestStatus", "setWidestStatus", "app_regularProductionRelease"}, mo22063k = 1, mo22064mv = {1, 1, 16})
    /* compiled from: StatusView.kt */
    public static final class StatusTextWidthInfo {
        private float extremeLeftStatusWidth;
        private float extremeRightStatusWidth;
        private StatusWidth subordinateWidestStatus;
        private StatusWidth widestStatus;

        public StatusTextWidthInfo(StatusWidth statusWidth, StatusWidth statusWidth2, float f, float f2) {
            Intrinsics.checkParameterIsNotNull(statusWidth, "widestStatus");
            this.widestStatus = statusWidth;
            this.subordinateWidestStatus = statusWidth2;
            this.extremeLeftStatusWidth = f;
            this.extremeRightStatusWidth = f2;
        }

        public /* synthetic */ StatusTextWidthInfo(StatusWidth statusWidth, StatusWidth statusWidth2, float f, float f2, int i, DefaultConstructorMarker defaultConstructorMarker) {
            if ((i & 2) != 0) {
                statusWidth2 = null;
            }
            if ((i & 4) != 0) {
                f = 0.0f;
            }
            if ((i & 8) != 0) {
                f2 = 0.0f;
            }
            this(statusWidth, statusWidth2, f, f2);
        }

        public final StatusWidth getSubordinateWidestStatus() {
            return this.subordinateWidestStatus;
        }

        public final StatusWidth getWidestStatus() {
            return this.widestStatus;
        }

        public final void setSubordinateWidestStatus(StatusWidth statusWidth) {
            this.subordinateWidestStatus = statusWidth;
        }

        public final void setWidestStatus(StatusWidth statusWidth) {
            Intrinsics.checkParameterIsNotNull(statusWidth, "<set-?>");
            this.widestStatus = statusWidth;
        }

        public final float getExtremeLeftStatusWidth() {
            return this.extremeLeftStatusWidth;
        }

        public final float getExtremeRightStatusWidth() {
            return this.extremeRightStatusWidth;
        }

        public final void setExtremeLeftStatusWidth(float f) {
            this.extremeLeftStatusWidth = f;
        }

        public final void setExtremeRightStatusWidth(float f) {
            this.extremeRightStatusWidth = f;
        }
    }

    @Metadata(mo22060bv = {1, 0, 3}, mo22061d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0007\n\u0000\n\u0002\u0010\b\n\u0002\b\n\u0018\u00002\u00020\u0001B\u0019\u0012\b\b\u0002\u0010\u0002\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006R\u001a\u0010\u0004\u001a\u00020\u0005X\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0007\u0010\b\"\u0004\b\t\u0010\nR\u001a\u0010\u0002\u001a\u00020\u0003X\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u000b\u0010\f\"\u0004\b\r\u0010\u000e¨\u0006\u000f"}, mo22062d2 = {"Lcom/mobiroller/views/StatusView$StatusWidth;", "", "width", "", "pos", "", "(FI)V", "getPos", "()I", "setPos", "(I)V", "getWidth", "()F", "setWidth", "(F)V", "app_regularProductionRelease"}, mo22063k = 1, mo22064mv = {1, 1, 16})
    /* compiled from: StatusView.kt */
    public static final class StatusWidth {
        private int pos;
        private float width;

        public StatusWidth() {
            this(0.0f, 0, 3, null);
        }

        public StatusWidth(float f, int i) {
            this.width = f;
            this.pos = i;
        }

        public /* synthetic */ StatusWidth(float f, int i, int i2, DefaultConstructorMarker defaultConstructorMarker) {
            if ((i2 & 1) != 0) {
                f = 0.0f;
            }
            if ((i2 & 2) != 0) {
                i = -1;
            }
            this(f, i);
        }

        public final int getPos() {
            return this.pos;
        }

        public final float getWidth() {
            return this.width;
        }

        public final void setPos(int i) {
            this.pos = i;
        }

        public final void setWidth(float f) {
            this.width = f;
        }
    }

    static {
        Class<StatusView> cls = StatusView.class;
        $$delegatedProperties = new KProperty[]{Reflection.mutableProperty1(new MutablePropertyReference1Impl(Reflection.getOrCreateKotlinClass(cls), "minStatusAdjacentMargin", "getMinStatusAdjacentMargin()F")), Reflection.mutableProperty1(new MutablePropertyReference1Impl(Reflection.getOrCreateKotlinClass(cls), "strictObeyLineLength", "getStrictObeyLineLength()Z")), Reflection.mutableProperty1(new MutablePropertyReference1Impl(Reflection.getOrCreateKotlinClass(cls), "stepCount", "getStepCount()I")), Reflection.mutableProperty1(new MutablePropertyReference1Impl(Reflection.getOrCreateKotlinClass(cls), "currentCount", "getCurrentCount()I")), Reflection.mutableProperty1(new MutablePropertyReference1Impl(Reflection.getOrCreateKotlinClass(cls), "circleRadius", "getCircleRadius()F")), Reflection.mutableProperty1(new MutablePropertyReference1Impl(Reflection.getOrCreateKotlinClass(cls), "lineLength", "getLineLength()F")), Reflection.mutableProperty1(new MutablePropertyReference1Impl(Reflection.getOrCreateKotlinClass(cls), "circleStrokeWidth", "getCircleStrokeWidth()F")), Reflection.mutableProperty1(new MutablePropertyReference1Impl(Reflection.getOrCreateKotlinClass(cls), "lineGap", "getLineGap()F")), Reflection.mutableProperty1(new MutablePropertyReference1Impl(Reflection.getOrCreateKotlinClass(cls), "statusTopMargin", "getStatusTopMargin()F")), Reflection.mutableProperty1(new MutablePropertyReference1Impl(Reflection.getOrCreateKotlinClass(cls), "alignStatusWithCurrent", "getAlignStatusWithCurrent()Z")), Reflection.mutableProperty1(new MutablePropertyReference1Impl(Reflection.getOrCreateKotlinClass(cls), "lineWidth", "getLineWidth()F")), Reflection.mutableProperty1(new MutablePropertyReference1Impl(Reflection.getOrCreateKotlinClass(cls), "lineColor", "getLineColor()I")), Reflection.mutableProperty1(new MutablePropertyReference1Impl(Reflection.getOrCreateKotlinClass(cls), "lineColorIncomplete", "getLineColorIncomplete()I")), Reflection.mutableProperty1(new MutablePropertyReference1Impl(Reflection.getOrCreateKotlinClass(cls), "lineColorCurrent", "getLineColorCurrent()I")), Reflection.mutableProperty1(new MutablePropertyReference1Impl(Reflection.getOrCreateKotlinClass(cls), "circleFillColor", "getCircleFillColor()I")), Reflection.mutableProperty1(new MutablePropertyReference1Impl(Reflection.getOrCreateKotlinClass(cls), "circleFillColorIncomplete", "getCircleFillColorIncomplete()I")), Reflection.mutableProperty1(new MutablePropertyReference1Impl(Reflection.getOrCreateKotlinClass(cls), "circleFillColorCurrent", "getCircleFillColorCurrent()I")), Reflection.mutableProperty1(new MutablePropertyReference1Impl(Reflection.getOrCreateKotlinClass(cls), "circleStrokeColor", "getCircleStrokeColor()I")), Reflection.mutableProperty1(new MutablePropertyReference1Impl(Reflection.getOrCreateKotlinClass(cls), "circleStrokeColorIncomplete", "getCircleStrokeColorIncomplete()I")), Reflection.mutableProperty1(new MutablePropertyReference1Impl(Reflection.getOrCreateKotlinClass(cls), "circleStrokeColorCurrent", "getCircleStrokeColorCurrent()I")), Reflection.mutableProperty1(new MutablePropertyReference1Impl(Reflection.getOrCreateKotlinClass(cls), "textColorLabels", "getTextColorLabels()I")), Reflection.mutableProperty1(new MutablePropertyReference1Impl(Reflection.getOrCreateKotlinClass(cls), "textColorLabelsIncomplete", "getTextColorLabelsIncomplete()I")), Reflection.mutableProperty1(new MutablePropertyReference1Impl(Reflection.getOrCreateKotlinClass(cls), "textColorLabelCurrent", "getTextColorLabelCurrent()I")), Reflection.mutableProperty1(new MutablePropertyReference1Impl(Reflection.getOrCreateKotlinClass(cls), "textSizeLabels", "getTextSizeLabels()F")), Reflection.mutableProperty1(new MutablePropertyReference1Impl(Reflection.getOrCreateKotlinClass(cls), "textColorStatus", "getTextColorStatus()I")), Reflection.mutableProperty1(new MutablePropertyReference1Impl(Reflection.getOrCreateKotlinClass(cls), "textColorStatusIncomplete", "getTextColorStatusIncomplete()I")), Reflection.mutableProperty1(new MutablePropertyReference1Impl(Reflection.getOrCreateKotlinClass(cls), "textColorStatusCurrent", "getTextColorStatusCurrent()I")), Reflection.mutableProperty1(new MutablePropertyReference1Impl(Reflection.getOrCreateKotlinClass(cls), "textSizeStatus", "getTextSizeStatus()F")), Reflection.mutableProperty1(new MutablePropertyReference1Impl(Reflection.getOrCreateKotlinClass(cls), "statusTypeface", "getStatusTypeface()Landroid/graphics/Typeface;")), Reflection.mutableProperty1(new MutablePropertyReference1Impl(Reflection.getOrCreateKotlinClass(cls), "labelsTypeface", "getLabelsTypeface()Landroid/graphics/Typeface;")), Reflection.mutableProperty1(new MutablePropertyReference1Impl(Reflection.getOrCreateKotlinClass(cls), "drawLabels", "getDrawLabels()Z")), Reflection.mutableProperty1(new MutablePropertyReference1Impl(Reflection.getOrCreateKotlinClass(cls), "completeDrawable", "getCompleteDrawable()Landroid/graphics/drawable/Drawable;")), Reflection.mutableProperty1(new MutablePropertyReference1Impl(Reflection.getOrCreateKotlinClass(cls), "currentDrawable", "getCurrentDrawable()Landroid/graphics/drawable/Drawable;")), Reflection.mutableProperty1(new MutablePropertyReference1Impl(Reflection.getOrCreateKotlinClass(cls), "incompleteDrawable", "getIncompleteDrawable()Landroid/graphics/drawable/Drawable;")), Reflection.mutableProperty1(new MutablePropertyReference1Impl(Reflection.getOrCreateKotlinClass(cls), "circleColorType", "getCircleColorType()I")), Reflection.mutableProperty1(new MutablePropertyReference1Impl(Reflection.getOrCreateKotlinClass(cls), "statusData", "getStatusData()Ljava/util/List;")), Reflection.mutableProperty1(new MutablePropertyReference1Impl(Reflection.getOrCreateKotlinClass(cls), "currentStatusRadius", "getCurrentStatusRadius()F"))};
    }

    public StatusView(Context context) {
        this(context, null, 0, 6, null);
    }

    public StatusView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0, 4, null);
    }

    /* access modifiers changed from: private */
    public final boolean containsFlag(int i, int i2) {
        return (i2 | i) == i;
    }

    private final float getCurrentStatusRadius() {
        return ((Number) this.currentStatusRadius$delegate.getValue(this, $$delegatedProperties[36])).floatValue();
    }

    /* access modifiers changed from: private */
    public final List<StatusInfo> getStatusData() {
        return (List) this.statusData$delegate.getValue(this, $$delegatedProperties[35]);
    }

    private final void setCurrentStatusRadius(float f) {
        this.currentStatusRadius$delegate.setValue(this, $$delegatedProperties[36], Float.valueOf(f));
    }

    /* access modifiers changed from: private */
    public final void setStatusData(List<StatusInfo> list) {
        this.statusData$delegate.setValue(this, $$delegatedProperties[35], list);
    }

    public final boolean getAlignStatusWithCurrent() {
        return ((Boolean) this.alignStatusWithCurrent$delegate.getValue(this, $$delegatedProperties[9])).booleanValue();
    }

    public final int getCircleColorType() {
        return ((Number) this.circleColorType$delegate.getValue(this, $$delegatedProperties[34])).intValue();
    }

    public final int getCircleFillColor() {
        return ((Number) this.circleFillColor$delegate.getValue(this, $$delegatedProperties[14])).intValue();
    }

    public final int getCircleFillColorCurrent() {
        return ((Number) this.circleFillColorCurrent$delegate.getValue(this, $$delegatedProperties[16])).intValue();
    }

    public final int getCircleFillColorIncomplete() {
        return ((Number) this.circleFillColorIncomplete$delegate.getValue(this, $$delegatedProperties[15])).intValue();
    }

    public final float getCircleRadius() {
        return ((Number) this.circleRadius$delegate.getValue(this, $$delegatedProperties[4])).floatValue();
    }

    public final int getCircleStrokeColor() {
        return ((Number) this.circleStrokeColor$delegate.getValue(this, $$delegatedProperties[17])).intValue();
    }

    public final int getCircleStrokeColorCurrent() {
        return ((Number) this.circleStrokeColorCurrent$delegate.getValue(this, $$delegatedProperties[19])).intValue();
    }

    public final int getCircleStrokeColorIncomplete() {
        return ((Number) this.circleStrokeColorIncomplete$delegate.getValue(this, $$delegatedProperties[18])).intValue();
    }

    public final float getCircleStrokeWidth() {
        return ((Number) this.circleStrokeWidth$delegate.getValue(this, $$delegatedProperties[6])).floatValue();
    }

    public final Drawable getCompleteDrawable() {
        return (Drawable) this.completeDrawable$delegate.getValue(this, $$delegatedProperties[31]);
    }

    public final int getCurrentCount() {
        return ((Number) this.currentCount$delegate.getValue(this, $$delegatedProperties[3])).intValue();
    }

    public final Drawable getCurrentDrawable() {
        return (Drawable) this.currentDrawable$delegate.getValue(this, $$delegatedProperties[32]);
    }

    public final boolean getDrawLabels() {
        return ((Boolean) this.drawLabels$delegate.getValue(this, $$delegatedProperties[30])).booleanValue();
    }

    public final Drawable getIncompleteDrawable() {
        return (Drawable) this.incompleteDrawable$delegate.getValue(this, $$delegatedProperties[33]);
    }

    public final Typeface getLabelsTypeface() {
        return (Typeface) this.labelsTypeface$delegate.getValue(this, $$delegatedProperties[29]);
    }

    public final int getLineColor() {
        return ((Number) this.lineColor$delegate.getValue(this, $$delegatedProperties[11])).intValue();
    }

    public final int getLineColorCurrent() {
        return ((Number) this.lineColorCurrent$delegate.getValue(this, $$delegatedProperties[13])).intValue();
    }

    public final int getLineColorIncomplete() {
        return ((Number) this.lineColorIncomplete$delegate.getValue(this, $$delegatedProperties[12])).intValue();
    }

    public final float getLineGap() {
        return ((Number) this.lineGap$delegate.getValue(this, $$delegatedProperties[7])).floatValue();
    }

    public final float getLineLength() {
        return ((Number) this.lineLength$delegate.getValue(this, $$delegatedProperties[5])).floatValue();
    }

    public final float getLineWidth() {
        return ((Number) this.lineWidth$delegate.getValue(this, $$delegatedProperties[10])).floatValue();
    }

    public final float getMinStatusAdjacentMargin() {
        return ((Number) this.minStatusAdjacentMargin$delegate.getValue(this, $$delegatedProperties[0])).floatValue();
    }

    public final float getStatusTopMargin() {
        return ((Number) this.statusTopMargin$delegate.getValue(this, $$delegatedProperties[8])).floatValue();
    }

    public final Typeface getStatusTypeface() {
        return (Typeface) this.statusTypeface$delegate.getValue(this, $$delegatedProperties[28]);
    }

    public final int getStepCount() {
        return ((Number) this.stepCount$delegate.getValue(this, $$delegatedProperties[2])).intValue();
    }

    public final boolean getStrictObeyLineLength() {
        return ((Boolean) this.strictObeyLineLength$delegate.getValue(this, $$delegatedProperties[1])).booleanValue();
    }

    public final int getTextColorLabelCurrent() {
        return ((Number) this.textColorLabelCurrent$delegate.getValue(this, $$delegatedProperties[22])).intValue();
    }

    public final int getTextColorLabels() {
        return ((Number) this.textColorLabels$delegate.getValue(this, $$delegatedProperties[20])).intValue();
    }

    public final int getTextColorLabelsIncomplete() {
        return ((Number) this.textColorLabelsIncomplete$delegate.getValue(this, $$delegatedProperties[21])).intValue();
    }

    public final int getTextColorStatus() {
        return ((Number) this.textColorStatus$delegate.getValue(this, $$delegatedProperties[24])).intValue();
    }

    public final int getTextColorStatusCurrent() {
        return ((Number) this.textColorStatusCurrent$delegate.getValue(this, $$delegatedProperties[26])).intValue();
    }

    public final int getTextColorStatusIncomplete() {
        return ((Number) this.textColorStatusIncomplete$delegate.getValue(this, $$delegatedProperties[25])).intValue();
    }

    public final float getTextSizeLabels() {
        return ((Number) this.textSizeLabels$delegate.getValue(this, $$delegatedProperties[23])).floatValue();
    }

    public final float getTextSizeStatus() {
        return ((Number) this.textSizeStatus$delegate.getValue(this, $$delegatedProperties[27])).floatValue();
    }

    public final void setAlignStatusWithCurrent(boolean z) {
        this.alignStatusWithCurrent$delegate.setValue(this, $$delegatedProperties[9], Boolean.valueOf(z));
    }

    public final void setCircleColorType(int i) {
        this.circleColorType$delegate.setValue(this, $$delegatedProperties[34], Integer.valueOf(i));
    }

    public final void setCircleFillColor(int i) {
        this.circleFillColor$delegate.setValue(this, $$delegatedProperties[14], Integer.valueOf(i));
    }

    public final void setCircleFillColorCurrent(int i) {
        this.circleFillColorCurrent$delegate.setValue(this, $$delegatedProperties[16], Integer.valueOf(i));
    }

    public final void setCircleFillColorIncomplete(int i) {
        this.circleFillColorIncomplete$delegate.setValue(this, $$delegatedProperties[15], Integer.valueOf(i));
    }

    public final void setCircleRadius(float f) {
        this.circleRadius$delegate.setValue(this, $$delegatedProperties[4], Float.valueOf(f));
    }

    public final void setCircleStrokeColor(int i) {
        this.circleStrokeColor$delegate.setValue(this, $$delegatedProperties[17], Integer.valueOf(i));
    }

    public final void setCircleStrokeColorCurrent(int i) {
        this.circleStrokeColorCurrent$delegate.setValue(this, $$delegatedProperties[19], Integer.valueOf(i));
    }

    public final void setCircleStrokeColorIncomplete(int i) {
        this.circleStrokeColorIncomplete$delegate.setValue(this, $$delegatedProperties[18], Integer.valueOf(i));
    }

    public final void setCircleStrokeWidth(float f) {
        this.circleStrokeWidth$delegate.setValue(this, $$delegatedProperties[6], Float.valueOf(f));
    }

    public final void setCompleteDrawable(Drawable drawable) {
        this.completeDrawable$delegate.setValue(this, $$delegatedProperties[31], drawable);
    }

    public final void setCurrentCount(int i) {
        this.currentCount$delegate.setValue(this, $$delegatedProperties[3], Integer.valueOf(i));
    }

    public final void setCurrentDrawable(Drawable drawable) {
        this.currentDrawable$delegate.setValue(this, $$delegatedProperties[32], drawable);
    }

    public final void setDrawLabels(boolean z) {
        this.drawLabels$delegate.setValue(this, $$delegatedProperties[30], Boolean.valueOf(z));
    }

    public final void setIncompleteDrawable(Drawable drawable) {
        this.incompleteDrawable$delegate.setValue(this, $$delegatedProperties[33], drawable);
    }

    public final void setLabelsTypeface(Typeface typeface) {
        this.labelsTypeface$delegate.setValue(this, $$delegatedProperties[29], typeface);
    }

    public final void setLineColor(int i) {
        this.lineColor$delegate.setValue(this, $$delegatedProperties[11], Integer.valueOf(i));
    }

    public final void setLineColorCurrent(int i) {
        this.lineColorCurrent$delegate.setValue(this, $$delegatedProperties[13], Integer.valueOf(i));
    }

    public final void setLineColorIncomplete(int i) {
        this.lineColorIncomplete$delegate.setValue(this, $$delegatedProperties[12], Integer.valueOf(i));
    }

    public final void setLineGap(float f) {
        this.lineGap$delegate.setValue(this, $$delegatedProperties[7], Float.valueOf(f));
    }

    public final void setLineLength(float f) {
        this.lineLength$delegate.setValue(this, $$delegatedProperties[5], Float.valueOf(f));
    }

    public final void setLineWidth(float f) {
        this.lineWidth$delegate.setValue(this, $$delegatedProperties[10], Float.valueOf(f));
    }

    public final void setMinStatusAdjacentMargin(float f) {
        this.minStatusAdjacentMargin$delegate.setValue(this, $$delegatedProperties[0], Float.valueOf(f));
    }

    public final void setStatusTopMargin(float f) {
        this.statusTopMargin$delegate.setValue(this, $$delegatedProperties[8], Float.valueOf(f));
    }

    public final void setStatusTypeface(Typeface typeface) {
        this.statusTypeface$delegate.setValue(this, $$delegatedProperties[28], typeface);
    }

    public final void setStepCount(int i) {
        this.stepCount$delegate.setValue(this, $$delegatedProperties[2], Integer.valueOf(i));
    }

    public final void setStrictObeyLineLength(boolean z) {
        this.strictObeyLineLength$delegate.setValue(this, $$delegatedProperties[1], Boolean.valueOf(z));
    }

    public final void setTextColorLabelCurrent(int i) {
        this.textColorLabelCurrent$delegate.setValue(this, $$delegatedProperties[22], Integer.valueOf(i));
    }

    public final void setTextColorLabels(int i) {
        this.textColorLabels$delegate.setValue(this, $$delegatedProperties[20], Integer.valueOf(i));
    }

    public final void setTextColorLabelsIncomplete(int i) {
        this.textColorLabelsIncomplete$delegate.setValue(this, $$delegatedProperties[21], Integer.valueOf(i));
    }

    public final void setTextColorStatus(int i) {
        this.textColorStatus$delegate.setValue(this, $$delegatedProperties[24], Integer.valueOf(i));
    }

    public final void setTextColorStatusCurrent(int i) {
        this.textColorStatusCurrent$delegate.setValue(this, $$delegatedProperties[26], Integer.valueOf(i));
    }

    public final void setTextColorStatusIncomplete(int i) {
        this.textColorStatusIncomplete$delegate.setValue(this, $$delegatedProperties[25], Integer.valueOf(i));
    }

    public final void setTextSizeLabels(float f) {
        this.textSizeLabels$delegate.setValue(this, $$delegatedProperties[23], Float.valueOf(f));
    }

    public final void setTextSizeStatus(float f) {
        this.textSizeStatus$delegate.setValue(this, $$delegatedProperties[27], Float.valueOf(f));
    }

    public static final /* synthetic */ Paint access$getMLinePaint$p(StatusView statusView) {
        Paint paint = statusView.mLinePaint;
        if (paint == null) {
            Intrinsics.throwUninitializedPropertyAccessException("mLinePaint");
        }
        return paint;
    }

    public static final /* synthetic */ Paint access$getMLinePaintCurrent$p(StatusView statusView) {
        Paint paint = statusView.mLinePaintCurrent;
        if (paint == null) {
            Intrinsics.throwUninitializedPropertyAccessException("mLinePaintCurrent");
        }
        return paint;
    }

    public static final /* synthetic */ Paint access$getMLinePaintIncomplete$p(StatusView statusView) {
        Paint paint = statusView.mLinePaintIncomplete;
        if (paint == null) {
            Intrinsics.throwUninitializedPropertyAccessException("mLinePaintIncomplete");
        }
        return paint;
    }

    public static final /* synthetic */ TextPaint access$getMTextPaintLabelCurrent$p(StatusView statusView) {
        TextPaint textPaint = statusView.mTextPaintLabelCurrent;
        if (textPaint == null) {
            Intrinsics.throwUninitializedPropertyAccessException("mTextPaintLabelCurrent");
        }
        return textPaint;
    }

    public static final /* synthetic */ TextPaint access$getMTextPaintLabels$p(StatusView statusView) {
        TextPaint textPaint = statusView.mTextPaintLabels;
        if (textPaint == null) {
            Intrinsics.throwUninitializedPropertyAccessException("mTextPaintLabels");
        }
        return textPaint;
    }

    public static final /* synthetic */ TextPaint access$getMTextPaintLabelsIncomplete$p(StatusView statusView) {
        TextPaint textPaint = statusView.mTextPaintLabelsIncomplete;
        if (textPaint == null) {
            Intrinsics.throwUninitializedPropertyAccessException("mTextPaintLabelsIncomplete");
        }
        return textPaint;
    }

    public static final /* synthetic */ TextPaint access$getMTextPaintStatus$p(StatusView statusView) {
        TextPaint textPaint = statusView.mTextPaintStatus;
        if (textPaint == null) {
            Intrinsics.throwUninitializedPropertyAccessException("mTextPaintStatus");
        }
        return textPaint;
    }

    public static final /* synthetic */ TextPaint access$getMTextPaintStatusCurrent$p(StatusView statusView) {
        TextPaint textPaint = statusView.mTextPaintStatusCurrent;
        if (textPaint == null) {
            Intrinsics.throwUninitializedPropertyAccessException("mTextPaintStatusCurrent");
        }
        return textPaint;
    }

    public static final /* synthetic */ TextPaint access$getMTextPaintStatusIncomplete$p(StatusView statusView) {
        TextPaint textPaint = statusView.mTextPaintStatusIncomplete;
        if (textPaint == null) {
            Intrinsics.throwUninitializedPropertyAccessException("mTextPaintStatusIncomplete");
        }
        return textPaint;
    }

    public /* synthetic */ StatusView(Context context, AttributeSet attributeSet, int i, int i2, DefaultConstructorMarker defaultConstructorMarker) {
        if ((i2 & 2) != 0) {
            attributeSet = null;
        }
        if ((i2 & 4) != 0) {
            i = 0;
        }
        this(context, attributeSet, i);
    }

    public StatusView(Context context, AttributeSet attributeSet, int i) {
        Intrinsics.checkParameterIsNotNull(context, "context");
        super(context, attributeSet, i);
        Boolean valueOf = Boolean.valueOf(false);
        OnLayoutProp onLayoutProp = new OnLayoutProp(this, Float.valueOf(pxValue$default(this, 5.0f, 0, 1, null)), null, 2, null);
        this.minStatusAdjacentMargin$delegate = onLayoutProp;
        OnLayoutProp onLayoutProp2 = new OnLayoutProp(this, valueOf, null, 2, null);
        this.strictObeyLineLength$delegate = onLayoutProp2;
        OnLayoutProp onLayoutProp3 = new OnLayoutProp(this, Integer.valueOf(4), null, 2, null);
        this.stepCount$delegate = onLayoutProp3;
        this.currentCount$delegate = new OnLayoutProp(this, Integer.valueOf(-1), new StatusView$currentCount$2(this));
        OnLayoutProp onLayoutProp4 = new OnLayoutProp(this, Float.valueOf(pxValue$default(this, 20.0f, 0, 1, null)), null, 2, null);
        this.circleRadius$delegate = onLayoutProp4;
        OnLayoutProp onLayoutProp5 = new OnLayoutProp(this, Float.valueOf(pxValue$default(this, 30.0f, 0, 1, null)), null, 2, null);
        this.lineLength$delegate = onLayoutProp5;
        OnLayoutProp onLayoutProp6 = new OnLayoutProp(this, Float.valueOf(pxValue$default(this, 2.0f, 0, 1, null)), null, 2, null);
        this.circleStrokeWidth$delegate = onLayoutProp6;
        OnLayoutProp onLayoutProp7 = new OnLayoutProp(this, Float.valueOf(0.0f), null, 2, null);
        this.lineGap$delegate = onLayoutProp7;
        OnLayoutProp onLayoutProp8 = new OnLayoutProp(this, Float.valueOf(pxValue$default(this, 4.0f, 0, 1, null)), null, 2, null);
        this.statusTopMargin$delegate = onLayoutProp8;
        this.alignStatusWithCurrent$delegate = new OnValidateProp(this, valueOf, new StatusView$alignStatusWithCurrent$2(this));
        this.lineWidth$delegate = new OnValidateProp(this, Float.valueOf(pxValue$default(this, 1.5f, 0, 1, null)), new StatusView$lineWidth$2(this));
        Integer valueOf2 = Integer.valueOf(ViewCompat.MEASURED_STATE_MASK);
        this.lineColor$delegate = new OnValidateProp(this, valueOf2, new StatusView$lineColor$2(this));
        this.lineColorIncomplete$delegate = new OnValidateProp(this, valueOf2, new StatusView$lineColorIncomplete$2(this));
        this.lineColorCurrent$delegate = new OnValidateProp(this, valueOf2, new StatusView$lineColorCurrent$2(this));
        Integer valueOf3 = Integer.valueOf(-16711681);
        this.circleFillColor$delegate = new OnValidateProp(this, valueOf3, new StatusView$circleFillColor$2(this));
        this.circleFillColorIncomplete$delegate = new OnValidateProp(this, valueOf3, new StatusView$circleFillColorIncomplete$2(this));
        this.circleFillColorCurrent$delegate = new OnValidateProp(this, valueOf3, new StatusView$circleFillColorCurrent$2(this));
        this.circleStrokeColor$delegate = new OnValidateProp(this, valueOf2, new StatusView$circleStrokeColor$2(this));
        this.circleStrokeColorIncomplete$delegate = new OnValidateProp(this, valueOf2, new StatusView$circleStrokeColorIncomplete$2(this));
        this.circleStrokeColorCurrent$delegate = new OnValidateProp(this, valueOf2, new StatusView$circleStrokeColorCurrent$2(this));
        this.textColorLabels$delegate = new OnValidateProp(this, valueOf2, new StatusView$textColorLabels$2(this));
        this.textColorLabelsIncomplete$delegate = new OnValidateProp(this, valueOf2, new StatusView$textColorLabelsIncomplete$2(this));
        this.textColorLabelCurrent$delegate = new OnValidateProp(this, valueOf2, new StatusView$textColorLabelCurrent$2(this));
        this.textSizeLabels$delegate = new OnValidateProp(this, Float.valueOf(pxValue(15.0f, 2)), new StatusView$textSizeLabels$2(this));
        this.textColorStatus$delegate = new OnValidateProp(this, valueOf2, new StatusView$textColorStatus$2(this));
        this.textColorStatusIncomplete$delegate = new OnValidateProp(this, valueOf2, new StatusView$textColorStatusIncomplete$2(this));
        this.textColorStatusCurrent$delegate = new OnValidateProp(this, valueOf2, new StatusView$textColorStatusCurrent$2(this));
        this.textSizeStatus$delegate = new OnLayoutProp(this, Float.valueOf(pxValue(14.0f, 2)), new StatusView$textSizeStatus$2(this));
        this.statusTypeface$delegate = new OnLayoutProp(this, null, new StatusView$statusTypeface$2(this));
        this.labelsTypeface$delegate = new OnLayoutProp(this, null, new StatusView$labelsTypeface$2(this));
        this.drawLabels$delegate = new OnValidateProp(this, valueOf, new StatusView$drawLabels$2(this));
        this.completeDrawable$delegate = new OnValidateProp(this, null, new StatusView$completeDrawable$2(this));
        this.currentDrawable$delegate = new OnValidateProp(this, null, new StatusView$currentDrawable$2(this));
        this.incompleteDrawable$delegate = new OnValidateProp(this, null, new StatusView$incompleteDrawable$2(this));
        Delegates delegates = Delegates.INSTANCE;
        Integer valueOf4 = Integer.valueOf(1);
        this.circleColorType$delegate = new StatusView$$special$$inlined$observable$1(valueOf4, valueOf4, this);
        OnLayoutProp onLayoutProp9 = new OnLayoutProp(this, new ArrayList(), null, 2, null);
        this.statusData$delegate = onLayoutProp9;
        this.drawingData = new ArrayList();
        OnLayoutProp onLayoutProp10 = new OnLayoutProp(this, Float.valueOf(getCircleRadius()), null, 2, null);
        this.currentStatusRadius$delegate = onLayoutProp10;
        setId(ViewCompat.generateViewId());
        TypedArray obtainStyledAttributes = context.getTheme().obtainStyledAttributes(attributeSet, C4290R.styleable.StatusViewScroller, 0, 0);
        try {
            setStepCount(obtainStyledAttributes.getInt(27, getStepCount()));
            setCurrentCount(obtainStyledAttributes.getInt(12, -1));
            setCircleRadius(obtainStyledAttributes.getDimension(6, getCircleRadius()));
            setLineLength(obtainStyledAttributes.getDimension(22, getLineLength()));
            setCircleStrokeWidth(obtainStyledAttributes.getDimension(10, getCircleStrokeWidth()));
            setLineWidth(obtainStyledAttributes.getDimension(23, getLineWidth()));
            setCompleteDrawable(obtainStyledAttributes.getDrawable(11));
            setIncompleteDrawable(obtainStyledAttributes.getDrawable(16));
            setCurrentDrawable(obtainStyledAttributes.getDrawable(13));
            setDrawLabels(obtainStyledAttributes.getBoolean(15, getDrawLabels()));
            setStrictObeyLineLength(obtainStyledAttributes.getBoolean(28, getStrictObeyLineLength()));
            setLineGap(obtainStyledAttributes.getDimension(21, getLineGap()));
            setMinStatusAdjacentMargin(obtainStyledAttributes.getDimension(24, getMinStatusAdjacentMargin()));
            setStatusTopMargin(obtainStyledAttributes.getDimension(26, getStatusTopMargin()));
            setLineColor(obtainStyledAttributes.getColor(18, getLineColor()));
            setCircleFillColor(obtainStyledAttributes.getColor(2, getCircleFillColor()));
            setCircleStrokeColor(obtainStyledAttributes.getColor(7, getCircleStrokeColor()));
            setTextColorStatus(obtainStyledAttributes.getColor(32, getTextColorStatus()));
            setTextColorLabels(obtainStyledAttributes.getColor(29, getTextColorLabels()));
            setTextSizeStatus(obtainStyledAttributes.getDimension(36, getTextSizeStatus()));
            setTextSizeLabels(obtainStyledAttributes.getDimension(35, getTextSizeLabels()));
            setCircleColorType(obtainStyledAttributes.getInteger(5, getCircleColorType()));
            setTextColorLabelsIncomplete(obtainStyledAttributes.getColor(31, getTextColorLabels()));
            setTextColorLabelCurrent(obtainStyledAttributes.getColor(30, getTextColorLabelsIncomplete()));
            setTextColorStatusIncomplete(obtainStyledAttributes.getColor(34, getTextColorLabels()));
            setTextColorStatusCurrent(obtainStyledAttributes.getColor(33, getTextColorLabelsIncomplete()));
            setLineColorIncomplete(obtainStyledAttributes.getColor(20, getLineColor()));
            setLineColorCurrent(obtainStyledAttributes.getColor(19, getLineColorIncomplete()));
            setCircleFillColorIncomplete(obtainStyledAttributes.getColor(4, getCircleFillColor()));
            setCircleStrokeColorIncomplete(obtainStyledAttributes.getColor(9, getCircleStrokeColor()));
            setCircleStrokeColorCurrent(obtainStyledAttributes.getColor(8, getCircleStrokeColorIncomplete()));
            setCircleFillColorCurrent(obtainStyledAttributes.getColor(3, getCircleFillColorIncomplete()));
            setCurrentStatusZoom(obtainStyledAttributes.getFloat(14, this.currentStatusZoom));
            setAlignStatusWithCurrent(obtainStyledAttributes.getBoolean(1, getAlignStatusWithCurrent()));
            CharSequence[] textArray = obtainStyledAttributes.getTextArray(0);
            if (textArray != null) {
                for (CharSequence charSequence : textArray) {
                    List statusData = getStatusData();
                    StatusInfo statusInfo = new StatusInfo(charSequence.toString(), 0.0f, 0.0f, null, 14, null);
                    statusData.add(statusInfo);
                }
            }
            int resourceId = obtainStyledAttributes.getResourceId(25, -1);
            if (resourceId != -1) {
                setStatusTypeface(ResourcesCompat.getFont(getContext(), resourceId));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } catch (Throwable th) {
            obtainStyledAttributes.recycle();
            throw th;
        }
        try {
            int resourceId2 = obtainStyledAttributes.getResourceId(17, -1);
            if (resourceId2 != -1) {
                setLabelsTypeface(ResourcesCompat.getFont(getContext(), resourceId2));
            }
        } catch (Exception e2) {
            e2.printStackTrace();
        }
        obtainStyledAttributes.recycle();
        initCirclePaints();
        this.mLinePaint = new Paint(1);
        Paint paint = this.mLinePaint;
        String str = "mLinePaint";
        if (paint == null) {
            Intrinsics.throwUninitializedPropertyAccessException(str);
        }
        paint.setStyle(Style.STROKE);
        Paint paint2 = this.mLinePaint;
        if (paint2 == null) {
            Intrinsics.throwUninitializedPropertyAccessException(str);
        }
        paint2.setStrokeWidth(getLineWidth());
        Paint paint3 = this.mLinePaint;
        if (paint3 == null) {
            Intrinsics.throwUninitializedPropertyAccessException(str);
        }
        paint3.setColor(getLineColor());
        this.mTextPaintStatus = new TextPaint(1);
        TextPaint textPaint = this.mTextPaintStatus;
        String str2 = "mTextPaintStatus";
        if (textPaint == null) {
            Intrinsics.throwUninitializedPropertyAccessException(str2);
        }
        textPaint.setStyle(Style.FILL);
        TextPaint textPaint2 = this.mTextPaintStatus;
        if (textPaint2 == null) {
            Intrinsics.throwUninitializedPropertyAccessException(str2);
        }
        textPaint2.setTextAlign(Align.CENTER);
        TextPaint textPaint3 = this.mTextPaintStatus;
        if (textPaint3 == null) {
            Intrinsics.throwUninitializedPropertyAccessException(str2);
        }
        textPaint3.setColor(getTextColorStatus());
        TextPaint textPaint4 = this.mTextPaintStatus;
        if (textPaint4 == null) {
            Intrinsics.throwUninitializedPropertyAccessException(str2);
        }
        textPaint4.setTextSize(getTextSizeStatus());
        Typeface statusTypeface = getStatusTypeface();
        if (statusTypeface != null) {
            TextPaint textPaint5 = this.mTextPaintStatus;
            if (textPaint5 == null) {
                Intrinsics.throwUninitializedPropertyAccessException(str2);
            }
            textPaint5.setTypeface(statusTypeface);
            Unit unit = Unit.INSTANCE;
        }
        this.mTextPaintStatusCurrent = new TextPaint(1);
        TextPaint textPaint6 = this.mTextPaintStatusCurrent;
        String str3 = "mTextPaintStatusCurrent";
        if (textPaint6 == null) {
            Intrinsics.throwUninitializedPropertyAccessException(str3);
        }
        textPaint6.setStyle(Style.FILL);
        TextPaint textPaint7 = this.mTextPaintStatusCurrent;
        if (textPaint7 == null) {
            Intrinsics.throwUninitializedPropertyAccessException(str3);
        }
        textPaint7.setTextAlign(Align.CENTER);
        TextPaint textPaint8 = this.mTextPaintStatusCurrent;
        if (textPaint8 == null) {
            Intrinsics.throwUninitializedPropertyAccessException(str3);
        }
        textPaint8.setColor(getTextColorStatusCurrent());
        TextPaint textPaint9 = this.mTextPaintStatusCurrent;
        if (textPaint9 == null) {
            Intrinsics.throwUninitializedPropertyAccessException(str3);
        }
        textPaint9.setTextSize(getTextSizeStatus());
        Typeface statusTypeface2 = getStatusTypeface();
        if (statusTypeface2 != null) {
            TextPaint textPaint10 = this.mTextPaintStatusCurrent;
            if (textPaint10 == null) {
                Intrinsics.throwUninitializedPropertyAccessException(str3);
            }
            textPaint10.setTypeface(statusTypeface2);
            Unit unit2 = Unit.INSTANCE;
        }
        this.mTextPaintStatusIncomplete = new TextPaint(1);
        TextPaint textPaint11 = this.mTextPaintStatusIncomplete;
        String str4 = "mTextPaintStatusIncomplete";
        if (textPaint11 == null) {
            Intrinsics.throwUninitializedPropertyAccessException(str4);
        }
        textPaint11.setStyle(Style.FILL);
        TextPaint textPaint12 = this.mTextPaintStatusIncomplete;
        if (textPaint12 == null) {
            Intrinsics.throwUninitializedPropertyAccessException(str4);
        }
        textPaint12.setTextAlign(Align.CENTER);
        TextPaint textPaint13 = this.mTextPaintStatusIncomplete;
        if (textPaint13 == null) {
            Intrinsics.throwUninitializedPropertyAccessException(str4);
        }
        textPaint13.setColor(getTextColorStatusIncomplete());
        TextPaint textPaint14 = this.mTextPaintStatusIncomplete;
        if (textPaint14 == null) {
            Intrinsics.throwUninitializedPropertyAccessException(str4);
        }
        textPaint14.setTextSize(getTextSizeStatus());
        Typeface statusTypeface3 = getStatusTypeface();
        if (statusTypeface3 != null) {
            TextPaint textPaint15 = this.mTextPaintStatusIncomplete;
            if (textPaint15 == null) {
                Intrinsics.throwUninitializedPropertyAccessException(str4);
            }
            textPaint15.setTypeface(statusTypeface3);
            Unit unit3 = Unit.INSTANCE;
        }
        Paint paint4 = this.mLinePaint;
        if (paint4 == null) {
            Intrinsics.throwUninitializedPropertyAccessException(str);
        }
        this.mLinePaintIncomplete = new Paint(paint4);
        Paint paint5 = this.mLinePaintIncomplete;
        if (paint5 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("mLinePaintIncomplete");
        }
        paint5.setColor(getLineColorIncomplete());
        Paint paint6 = this.mLinePaint;
        if (paint6 == null) {
            Intrinsics.throwUninitializedPropertyAccessException(str);
        }
        this.mLinePaintCurrent = new Paint(paint6);
        Paint paint7 = this.mLinePaintCurrent;
        if (paint7 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("mLinePaintCurrent");
        }
        paint7.setColor(getLineColorCurrent());
        this.mTextPaintLabels = new TextPaint(1);
        TextPaint textPaint16 = this.mTextPaintLabels;
        String str5 = "mTextPaintLabels";
        if (textPaint16 == null) {
            Intrinsics.throwUninitializedPropertyAccessException(str5);
        }
        textPaint16.setStyle(Style.FILL);
        TextPaint textPaint17 = this.mTextPaintLabels;
        if (textPaint17 == null) {
            Intrinsics.throwUninitializedPropertyAccessException(str5);
        }
        textPaint17.setTextAlign(Align.CENTER);
        TextPaint textPaint18 = this.mTextPaintLabels;
        if (textPaint18 == null) {
            Intrinsics.throwUninitializedPropertyAccessException(str5);
        }
        textPaint18.setTextSize(getTextSizeLabels());
        TextPaint textPaint19 = this.mTextPaintLabels;
        if (textPaint19 == null) {
            Intrinsics.throwUninitializedPropertyAccessException(str5);
        }
        textPaint19.setColor(getTextColorLabels());
        TextPaint textPaint20 = this.mTextPaintLabels;
        if (textPaint20 == null) {
            Intrinsics.throwUninitializedPropertyAccessException(str5);
        }
        this.mTextPaintLabelsIncomplete = new TextPaint(textPaint20);
        TextPaint textPaint21 = this.mTextPaintLabelsIncomplete;
        String str6 = "mTextPaintLabelsIncomplete";
        if (textPaint21 == null) {
            Intrinsics.throwUninitializedPropertyAccessException(str6);
        }
        textPaint21.setColor(getTextColorLabelsIncomplete());
        TextPaint textPaint22 = this.mTextPaintLabels;
        if (textPaint22 == null) {
            Intrinsics.throwUninitializedPropertyAccessException(str5);
        }
        this.mTextPaintLabelCurrent = new TextPaint(textPaint22);
        TextPaint textPaint23 = this.mTextPaintLabelCurrent;
        String str7 = "mTextPaintLabelCurrent";
        if (textPaint23 == null) {
            Intrinsics.throwUninitializedPropertyAccessException(str7);
        }
        textPaint23.setColor(getTextColorLabelCurrent());
        Typeface labelsTypeface = getLabelsTypeface();
        if (labelsTypeface != null) {
            TextPaint textPaint24 = this.mTextPaintLabels;
            if (textPaint24 == null) {
                Intrinsics.throwUninitializedPropertyAccessException(str5);
            }
            textPaint24.setTypeface(labelsTypeface);
            TextPaint textPaint25 = this.mTextPaintLabelsIncomplete;
            if (textPaint25 == null) {
                Intrinsics.throwUninitializedPropertyAccessException(str6);
            }
            textPaint25.setTypeface(labelsTypeface);
            TextPaint textPaint26 = this.mTextPaintLabelCurrent;
            if (textPaint26 == null) {
                Intrinsics.throwUninitializedPropertyAccessException(str7);
            }
            textPaint26.setTypeface(labelsTypeface);
            Unit unit4 = Unit.INSTANCE;
        }
        this.propsIntialisedOnce = true;
    }

    public final float getCurrentStatusZoom() {
        return this.currentStatusZoom;
    }

    public final void setCurrentStatusZoom(float f) {
        if (RangesKt.intRangeContains((ClosedRange<Integer>) new IntRange<Integer>(0, 1), f)) {
            setCurrentStatusRadius(getCircleRadius() * (((float) 1) + f));
            return;
        }
        StringBuilder sb = new StringBuilder();
        sb.append("Zoom should be in between 0 to 1, but was ");
        sb.append(f);
        sb.append('.');
        throw new IllegalArgumentException(sb.toString().toString());
    }

    public final List<String> getStatusList() {
        Iterable<StatusInfo> statusData = getStatusData();
        Collection arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(statusData, 10));
        for (StatusInfo text : statusData) {
            arrayList.add(text.getText());
        }
        return (List) arrayList;
    }

    public final void setStatusList(List<String> list) {
        Intrinsics.checkParameterIsNotNull(list, "list");
        Iterable<String> dropLast = CollectionsKt.dropLast(CollectionsKt.toMutableList((Collection) list), getStepCount());
        Collection arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(dropLast, 10));
        for (String statusInfo : dropLast) {
            StatusInfo statusInfo2 = new StatusInfo(statusInfo, 0.0f, 0.0f, null, 14, null);
            arrayList.add(statusInfo2);
        }
        setStatusData(CollectionsKt.toMutableList((Collection) (List) arrayList));
    }

    /* access modifiers changed from: private */
    public final void initCirclePaints() {
        if (containsFlag(getCircleColorType(), 2)) {
            if (this.mCircleStrokePaint == null) {
                this.mCircleStrokePaint = new Paint(1);
                Paint paint = this.mCircleStrokePaint;
                if (paint != null) {
                    paint.setStyle(Style.STROKE);
                }
                Paint paint2 = this.mCircleStrokePaint;
                if (paint2 != null) {
                    paint2.setStrokeWidth(getCircleStrokeWidth());
                }
                Paint paint3 = this.mCircleStrokePaint;
                if (paint3 != null) {
                    paint3.setColor(getCircleStrokeColor());
                }
            }
            if (isShowingIncompleteStatus() && this.mCircleStrokePaintIncomplete == null) {
                this.mCircleStrokePaintIncomplete = new Paint(this.mCircleStrokePaint);
                Paint paint4 = this.mCircleStrokePaintIncomplete;
                if (paint4 != null) {
                    paint4.setColor(getCircleStrokeColorIncomplete());
                }
            }
            if (isShowingCurrentStatus() && this.mCircleStrokePaintCurrent == null) {
                this.mCircleStrokePaintCurrent = new Paint(this.mCircleStrokePaint);
                Paint paint5 = this.mCircleStrokePaintCurrent;
                if (paint5 != null) {
                    paint5.setColor(getCircleStrokeColorCurrent());
                }
            }
        } else {
            setCircleStrokeWidth(0.0f);
            Paint paint6 = null;
            this.mCircleStrokePaint = paint6;
            this.mCircleStrokePaintIncomplete = paint6;
            this.mCircleStrokePaintCurrent = paint6;
        }
        if (containsFlag(getCircleColorType(), 1)) {
            if (this.mCircleFillPaint == null) {
                this.mCircleFillPaint = new Paint(1);
                Paint paint7 = this.mCircleFillPaint;
                if (paint7 != null) {
                    paint7.setStyle(Style.FILL);
                }
                Paint paint8 = this.mCircleFillPaint;
                if (paint8 != null) {
                    paint8.setColor(getCircleFillColor());
                }
            }
            if (isShowingIncompleteStatus() && this.mCircleFillPaintIncomplete == null) {
                this.mCircleFillPaintIncomplete = new Paint(this.mCircleFillPaint);
                Paint paint9 = this.mCircleFillPaintIncomplete;
                if (paint9 != null) {
                    paint9.setColor(getCircleFillColorIncomplete());
                }
            }
            if (isShowingCurrentStatus() && this.mCircleFillPaintCurrent == null) {
                this.mCircleFillPaintCurrent = new Paint(this.mCircleFillPaint);
                Paint paint10 = this.mCircleFillPaintCurrent;
                if (paint10 != null) {
                    paint10.setColor(getCircleFillColorCurrent());
                    return;
                }
                return;
            }
            return;
        }
        Paint paint11 = null;
        this.mCircleFillPaint = paint11;
        this.mCircleFillPaintIncomplete = paint11;
        this.mCircleFillPaintCurrent = paint11;
    }

    private final boolean isShowingIncompleteStatus() {
        return getCurrentCount() > -1 && getCurrentCount() < getStepCount();
    }

    private final boolean isShowingCurrentStatus() {
        int stepCount = getStepCount();
        int currentCount = getCurrentCount();
        return 1 <= currentCount && stepCount >= currentCount;
    }

    /* access modifiers changed from: protected */
    public int getSuggestedMinimumWidth() {
        float f;
        if (getStepCount() <= 0) {
            return 0;
        }
        this.lineLengthComputed = getLineLength();
        if (getStrictObeyLineLength()) {
            f = setWidthDataForObeyingLineLength();
        } else {
            f = setWidthDataForObeyingStatusText();
        }
        if (getStepCount() == 1) {
            f *= (float) 2;
        }
        if (isShowingCurrentStatus()) {
            f += (getCurrentStatusRadius() - getCircleRadius()) * ((float) 2);
        }
        float f2 = (float) 2;
        return (int) ((((float) getStepCount()) * (getCircleRadius() + (getCircleStrokeWidth() / f2)) * f2) + (((float) (getStepCount() - 1)) * (this.lineLengthComputed + (getLineGap() * f2))) + f);
    }

    /* access modifiers changed from: protected */
    public int getSuggestedMinimumHeight() {
        float f;
        float f2;
        if (getStepCount() <= 0) {
            return 0;
        }
        float f3 = 0.0f;
        float f4 = 0.0f;
        for (StatusInfo statusInfo : getStatusData()) {
            TextPaint textPaint = this.mTextPaintStatus;
            if (textPaint == null) {
                Intrinsics.throwUninitializedPropertyAccessException("mTextPaintStatus");
            }
            f4 = Math.max(f4, setLabelsHeight(textPaint, statusInfo));
        }
        if (isShowingCurrentStatus()) {
            f = getCurrentStatusRadius();
            if (getStatusData().size() > 0) {
                int size = getStatusData().size();
                int currentCountIndex = currentCountIndex();
                if (currentCountIndex >= 0 && size >= currentCountIndex) {
                    f2 = ((StatusInfo) getStatusData().get(currentCountIndex())).getHeight();
                    f4 = Math.max(getCurrentStatusRadius() + f2, getCircleRadius() + f4);
                }
            }
            f2 = 0.0f;
            f4 = Math.max(getCurrentStatusRadius() + f2, getCircleRadius() + f4);
        } else {
            f = getCircleRadius() * ((float) 2);
        }
        float f5 = f + f4;
        if (getStatusData().size() > 0) {
            f3 = getStatusTopMargin();
        }
        return (int) (f5 + getCircleStrokeWidth() + f3);
    }

    /* access modifiers changed from: protected */
    public void onMeasure(int i, int i2) {
        setMeasuredDimension(View.resolveSize(getPaddingLeft() + getPaddingRight() + getSuggestedMinimumWidth(), i), View.resolveSize(getPaddingTop() + getPaddingBottom() + getSuggestedMinimumHeight(), i2));
    }

    /* access modifiers changed from: protected */
    public void onLayout(boolean z, int i, int i2, int i3, int i4) {
        setDrawingDimensions();
    }

    /* access modifiers changed from: protected */
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        for (Item item : this.drawingData) {
            if (!(item.getCircleItem().getFillPaint() == null || canvas == null)) {
                canvas.drawCircle(item.getCircleItem().getCenter().x, item.getCircleItem().getCenter().y, item.getCircleItem().getRadius(), item.getCircleItem().getFillPaint());
            }
            if (!(item.getCircleItem().getStrokePaint() == null || canvas == null)) {
                canvas.drawCircle(item.getCircleItem().getCenter().x, item.getCircleItem().getCenter().y, item.getCircleItem().getRadius(), item.getCircleItem().getStrokePaint());
            }
            if (item.getTextData() != null) {
                if (item.getTextData().getDrawableItem() != null) {
                    DrawableItem drawableItem = item.getTextData().getDrawableItem();
                    drawableItem.getDrawable().setBounds(drawableItem.getRect());
                    if (canvas != null) {
                        item.getTextData().getDrawableItem().getDrawable().draw(canvas);
                    }
                } else if (!(item.getTextData().getText() == null || item.getTextData().getPaint() == null || canvas == null)) {
                    canvas.drawText(item.getTextData().getText(), item.getTextData().getX(), item.getTextData().getY(), item.getTextData().getPaint());
                }
            }
            if (!(item.getLabelData() == null || item.getLabelData().getStaticLayout() == null)) {
                item.getLabelData().getStaticLayout().getPaint().set(item.getLabelData().getPaint());
                if (canvas != null) {
                    canvas.save();
                }
                if (canvas != null) {
                    canvas.translate(item.getLabelData().getX(), item.getLabelData().getY());
                }
                item.getLabelData().getStaticLayout().draw(canvas);
                if (canvas != null) {
                    canvas.restore();
                }
            }
            if (!(item.getLineItem() == null || canvas == null)) {
                canvas.drawLine(item.getLineItem().getStart().x, item.getLineItem().getStart().y, item.getLineItem().getEnd().x, item.getLineItem().getEnd().y, item.getLineItem().getPaint());
            }
        }
    }

    /* access modifiers changed from: private */
    public final void setDrawingDimensions() {
        Drawable drawable;
        TextPaint textPaint;
        TextPaint textPaint2;
        Paint paint;
        Paint paint2;
        Paint paint3;
        StatusItemText statusItemText;
        int i;
        StatusItemText statusItemText2;
        LabelItemText labelItemText;
        PointF pointF = new PointF();
        float f = (float) 2;
        pointF.x = ((float) getPaddingLeft()) + (getCircleStrokeWidth() / f);
        pointF.y = ((float) getPaddingTop()) + getCircleRadius() + (getCircleStrokeWidth() / f);
        if (isShowingCurrentStatus()) {
            pointF.y += getCurrentStatusRadius() - getCircleRadius();
        }
        int stepCount = getStepCount();
        int i2 = 0;
        int i3 = 0;
        while (i3 < stepCount) {
            float circleRadius = getCircleRadius();
            String str = "mTextPaintStatusCurrent";
            if (!isShowingCurrentStatus() || i3 != getCurrentCount() - 1) {
                if (isShowingIncompleteStatus()) {
                    int currentCount = getCurrentCount();
                    int stepCount2 = getStepCount();
                    if (currentCount <= i3 && stepCount2 >= i3) {
                        paint3 = this.mCircleStrokePaintIncomplete;
                        paint = this.mCircleFillPaintIncomplete;
                        textPaint2 = this.mTextPaintLabelsIncomplete;
                        if (textPaint2 == null) {
                            Intrinsics.throwUninitializedPropertyAccessException("mTextPaintLabelsIncomplete");
                        }
                        textPaint = this.mTextPaintStatusIncomplete;
                        if (textPaint == null) {
                            Intrinsics.throwUninitializedPropertyAccessException("mTextPaintStatusIncomplete");
                        }
                        paint2 = this.mLinePaintIncomplete;
                        if (paint2 == null) {
                            Intrinsics.throwUninitializedPropertyAccessException("mLinePaintIncomplete");
                        }
                        drawable = getIncompleteDrawable();
                    }
                }
                paint3 = this.mCircleStrokePaint;
                paint = this.mCircleFillPaint;
                textPaint2 = this.mTextPaintLabels;
                if (textPaint2 == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("mTextPaintLabels");
                }
                textPaint = this.mTextPaintStatusCurrent;
                if (textPaint == null) {
                    Intrinsics.throwUninitializedPropertyAccessException(str);
                }
                paint2 = this.mLinePaint;
                if (paint2 == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("mLinePaint");
                }
                drawable = getCompleteDrawable();
            } else {
                circleRadius = getCurrentStatusRadius();
                paint3 = this.mCircleStrokePaintCurrent;
                paint = this.mCircleFillPaintCurrent;
                textPaint2 = this.mTextPaintLabelCurrent;
                if (textPaint2 == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("mTextPaintLabelCurrent");
                }
                textPaint = this.mTextPaintStatusCurrent;
                if (textPaint == null) {
                    Intrinsics.throwUninitializedPropertyAccessException(str);
                }
                paint2 = this.mLinePaintCurrent;
                if (paint2 == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("mLinePaintCurrent");
                }
                drawable = getCurrentDrawable();
            }
            LineItem lineItem = null;
            LabelItemText labelItemText2 = null;
            StatusItemText statusItemText3 = null;
            if (i3 == 0) {
                if (getStatusData().size() > 0) {
                    pointF.x += Math.max(0.0f, (((StatusInfo) getStatusData().get(i2)).getWidth() - minStatusWidthExtremes(i3)) / f);
                }
                i = stepCount;
                statusItemText = statusItemText3;
            } else {
                pointF.x += getLineGap();
                i = stepCount;
                statusItemText = statusItemText3;
                lineItem = new LineItem(new PointF(pointF.x, pointF.y), new PointF(pointF.x + this.lineLengthComputed, pointF.y), paint2);
                pointF.x = lineItem.getEnd().x + getLineGap() + (getCircleStrokeWidth() / f);
            }
            CircleItem circleItem = new CircleItem(new PointF(pointF.x + circleRadius, pointF.y), circleRadius, paint3, paint);
            pointF.x += (2.0f * circleRadius) + (getCircleStrokeWidth() / f);
            if (i3 < getStatusData().size()) {
                if (isShowingCurrentStatus() && getAlignStatusWithCurrent()) {
                    circleRadius = getCurrentStatusRadius();
                }
                statusItemText2 = new StatusItemText(circleItem.getCenter().x, circleItem.getCenter().y + circleRadius + (getCircleStrokeWidth() / f) + getStatusTopMargin(), textPaint, ((StatusInfo) getStatusData().get(i3)).getStaticLayout());
            } else {
                statusItemText2 = statusItemText;
            }
            if (drawable != null) {
                int intrinsicWidth = drawable.getIntrinsicWidth();
                int i4 = (int) circleItem.getCenter().x;
                int i5 = (int) circleItem.getCenter().y;
                int i6 = intrinsicWidth / 2;
                int intrinsicHeight = drawable.getIntrinsicHeight() / 2;
                labelItemText = new LabelItemText(null, null, 0.0f, 0.0f, new DrawableItem(new Rect(i4 - i6, i5 - intrinsicHeight, i4 + i6, i5 + intrinsicHeight), drawable), 15, null);
            } else if (getDrawLabels()) {
                String valueOf = String.valueOf(i3 + 1);
                Rect rect = new Rect();
                textPaint2.getTextBounds(valueOf, 0, valueOf.length(), rect);
                String str2 = valueOf;
                LabelItemText labelItemText3 = new LabelItemText(str2, textPaint2, circleItem.getCenter().x, circleItem.getCenter().y - rect.exactCenterY(), null, 16, null);
                labelItemText = labelItemText3;
            } else {
                labelItemText = labelItemText2;
            }
            this.drawingData.add(new Item(labelItemText, circleItem, lineItem, statusItemText2));
            i3++;
            stepCount = i;
            i2 = 0;
        }
    }

    private final float setWidthDataForObeyingLineLength() {
        int size = getStatusData().size();
        float f = 0.0f;
        for (int i = 0; i < size; i++) {
            StatusInfo statusInfo = (StatusInfo) getStatusData().get(i);
            if (i == 0 || i == stepCountIndex()) {
                float minStatusWidthExtremes = minStatusWidthExtremes(i);
                float minStatusWidth = minStatusWidth(i);
                TextPaint textPaint = this.mTextPaintStatus;
                if (textPaint == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("mTextPaintStatus");
                }
                float textWidth = getTextWidth(textPaint, statusInfo.getText());
                float min = textWidth > minStatusWidthExtremes ? Math.min(minStatusWidth - minStatusWidthExtremes, textWidth - minStatusWidthExtremes) / ((float) 2) : 0.0f;
                statusInfo.setWidth(minStatusWidthExtremes + (((float) 2) * min));
                f += min;
            } else {
                statusInfo.setWidth(minStatusWidth(i));
            }
        }
        return f;
    }

    private final float setWidthDataForObeyingStatusText() {
        float f;
        if (getStatusData().size() == 0) {
            return 0.0f;
        }
        Iterable<StatusInfo> statusData = getStatusData();
        Collection arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(statusData, 10));
        for (StatusInfo text : statusData) {
            arrayList.add(text.getText());
        }
        List list = (List) arrayList;
        TextPaint textPaint = this.mTextPaintStatus;
        if (textPaint == null) {
            Intrinsics.throwUninitializedPropertyAccessException("mTextPaintStatus");
        }
        StatusTextWidthInfo statusTextWidthInfo = getStatusTextWidthInfo(list, textPaint);
        float minStatusWidth = minStatusWidth(statusTextWidthInfo.getWidestStatus().getPos());
        if (statusTextWidthInfo.getWidestStatus().getWidth() > minStatusWidth) {
            this.lineLengthComputed += statusTextWidthInfo.getWidestStatus().getWidth() - minStatusWidth;
        }
        StatusWidth subordinateWidestStatus = statusTextWidthInfo.getSubordinateWidestStatus();
        if (subordinateWidestStatus != null) {
            float minStatusWidth2 = minStatusWidth(subordinateWidestStatus.getPos());
            if (subordinateWidestStatus.getWidth() > minStatusWidth2) {
                this.lineLengthComputed += subordinateWidestStatus.getWidth() - minStatusWidth2;
            }
        }
        int size = getStatusData().size();
        boolean z = false;
        float f2 = 0.0f;
        for (int i = 0; i < size; i++) {
            StatusInfo statusInfo = (StatusInfo) getStatusData().get(i);
            if (i == 0 || i == stepCountIndex()) {
                if (i == 0) {
                    f = statusTextWidthInfo.getExtremeLeftStatusWidth();
                } else {
                    f = statusTextWidthInfo.getExtremeRightStatusWidth();
                }
                statusInfo.setWidth(f);
                float width = (statusInfo.getWidth() - minStatusWidthExtremes(i)) / ((float) 2);
                if (width > ((float) 0)) {
                    f2 += width;
                }
            } else {
                statusInfo.setWidth(statusTextWidthInfo.getWidestStatus().getWidth());
            }
            if (getMinStatusAdjacentMargin() > ((float) 0) && !z) {
                int size2 = getStatusData().size();
                if (1 <= i && size2 > i) {
                    int i2 = i - 1;
                    if ((minStatusWidth(i) + minStatusWidth(i2)) - (statusInfo.getWidth() + ((StatusInfo) getStatusData().get(i2)).getWidth()) < getMinStatusAdjacentMargin()) {
                        z = true;
                    }
                }
            }
        }
        if (z) {
            this.lineLengthComputed += getMinStatusAdjacentMargin();
        }
        return f2;
    }

    private final float minStatusWidth(int i) {
        float circleRadius = getCircleRadius();
        float f = (float) 2;
        float lineGap = this.lineLengthComputed + (getLineGap() * f);
        if (isShowingCurrentStatus() && i == currentCountIndex()) {
            circleRadius = getCurrentStatusRadius();
        }
        return (f * circleRadius) + getCircleStrokeWidth() + lineGap;
    }

    private final float minStatusWidthExtremes(int i) {
        float circleRadius = getCircleRadius();
        if (isShowingCurrentStatus() && i == currentCountIndex()) {
            circleRadius = getCurrentStatusRadius();
        }
        return (((float) 2) * circleRadius) + getCircleStrokeWidth();
    }

    private final float getTextWidth(Paint paint, String str) {
        return paint.measureText(str);
    }

    private final float setLabelsHeight(TextPaint textPaint, StatusInfo statusInfo) {
        StaticLayout staticLayout = getStaticLayout(statusInfo.getText(), textPaint, statusInfo.getWidth());
        statusInfo.setStaticLayout(staticLayout);
        statusInfo.setHeight((float) staticLayout.getHeight());
        return statusInfo.getHeight();
    }

    private final StaticLayout getStaticLayout(String str, TextPaint textPaint, float f) {
        TextPaint textPaint2 = textPaint;
        StaticLayout staticLayout = new StaticLayout(str, textPaint2, (int) f, Alignment.ALIGN_NORMAL, 1.0f, 0.0f, false);
        return staticLayout;
    }

    private final StatusTextWidthInfo getStatusTextWidthInfo(List<String> list, TextPaint textPaint) {
        StatusWidth statusWidth = new StatusWidth(0.0f, 0, 3, null);
        StatusWidth statusWidth2 = null;
        StatusTextWidthInfo statusTextWidthInfo = new StatusTextWidthInfo(statusWidth, null, 0.0f, 0.0f, 14, null);
        int size = list.size();
        StatusWidth statusWidth3 = statusWidth2;
        for (int i = 0; i < size; i++) {
            float statusWidth4 = getStatusWidth((String) list.get(i), textPaint);
            if (isShowingCurrentStatus() && i == currentCountIndex() && statusWidth4 > statusWidth.getWidth()) {
                statusWidth3 = new StatusWidth(statusWidth.getWidth(), statusWidth.getPos());
                statusWidth.setWidth(statusWidth4);
                statusWidth.setPos(i);
            } else if (statusWidth4 > statusWidth.getWidth()) {
                statusWidth.setWidth(statusWidth4);
                statusWidth.setPos(i);
                statusWidth3 = statusWidth2;
            } else if (statusWidth3 != null && statusWidth4 > statusWidth3.getWidth()) {
                statusWidth3.setWidth(statusWidth4);
                statusWidth3.setPos(i);
            }
            if (i == 0) {
                statusTextWidthInfo.setExtremeLeftStatusWidth(statusWidth4);
            } else if (i == stepCountIndex()) {
                statusTextWidthInfo.setExtremeRightStatusWidth(statusWidth4);
            }
        }
        statusTextWidthInfo.setWidestStatus(statusWidth);
        statusTextWidthInfo.setSubordinateWidestStatus(statusWidth3);
        return statusTextWidthInfo;
    }

    private final float getStatusWidth(String str, TextPaint textPaint) {
        float f = 0.0f;
        for (String measureText : StringsKt.split$default((CharSequence) str, new char[]{10}, false, 0, 6, (Object) null)) {
            float measureText2 = textPaint.measureText(measureText);
            if (measureText2 > f) {
                f = measureText2;
            }
        }
        return f;
    }

    /* access modifiers changed from: private */
    public final int currentCountIndex() {
        return getCurrentCount() - 1;
    }

    private final int stepCountIndex() {
        return getStepCount() - 1;
    }

    static /* synthetic */ float pxValue$default(StatusView statusView, float f, int i, int i2, Object obj) {
        if ((i2 & 1) != 0) {
            i = 1;
        }
        return statusView.pxValue(f, i);
    }

    private final float pxValue(float f, int i) {
        Resources resources = getResources();
        Intrinsics.checkExpressionValueIsNotNull(resources, "resources");
        return TypedValue.applyDimension(i, f, resources.getDisplayMetrics());
    }

    public final int getScrollPosForStep(int i) {
        int i2 = i - 1;
        if (i2 <= 0 || i2 >= this.drawingData.size()) {
            return 0;
        }
        return (int) ((Item) this.drawingData.get(i2)).getCircleItem().getCenter().x;
    }
}
