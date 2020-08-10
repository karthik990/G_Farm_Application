package com.nightonke.boommenu.Animation;

import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.animation.TimeInterpolator;
import android.animation.TypeEvaluator;
import android.graphics.PointF;
import android.view.View;
import com.nightonke.boommenu.BoomButtons.BoomButton;
import com.nightonke.boommenu.ButtonEnum;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

public class AnimationManager {

    /* renamed from: com.nightonke.boommenu.Animation.AnimationManager$1 */
    static /* synthetic */ class C44861 {
        static final /* synthetic */ int[] $SwitchMap$com$nightonke$boommenu$Animation$OrderEnum = new int[OrderEnum.values().length];

        /* JADX WARNING: Can't wrap try/catch for region: R(27:0|(2:1|2)|3|(2:5|6)|7|9|10|11|12|13|14|15|16|17|18|19|20|21|22|23|25|26|27|28|29|30|32) */
        /* JADX WARNING: Can't wrap try/catch for region: R(28:0|1|2|3|(2:5|6)|7|9|10|11|12|13|14|15|16|17|18|19|20|21|22|23|25|26|27|28|29|30|32) */
        /* JADX WARNING: Can't wrap try/catch for region: R(29:0|1|2|3|5|6|7|9|10|11|12|13|14|15|16|17|18|19|20|21|22|23|25|26|27|28|29|30|32) */
        /* JADX WARNING: Failed to process nested try/catch */
        /* JADX WARNING: Missing exception handler attribute for start block: B:11:0x002a */
        /* JADX WARNING: Missing exception handler attribute for start block: B:13:0x0035 */
        /* JADX WARNING: Missing exception handler attribute for start block: B:15:0x0040 */
        /* JADX WARNING: Missing exception handler attribute for start block: B:17:0x004b */
        /* JADX WARNING: Missing exception handler attribute for start block: B:19:0x0056 */
        /* JADX WARNING: Missing exception handler attribute for start block: B:21:0x0062 */
        /* JADX WARNING: Missing exception handler attribute for start block: B:27:0x0081 */
        /* JADX WARNING: Missing exception handler attribute for start block: B:29:0x008b */
        static {
            /*
                com.nightonke.boommenu.Animation.BoomEnum[] r0 = com.nightonke.boommenu.Animation.BoomEnum.values()
                int r0 = r0.length
                int[] r0 = new int[r0]
                $SwitchMap$com$nightonke$boommenu$Animation$BoomEnum = r0
                r0 = 1
                int[] r1 = $SwitchMap$com$nightonke$boommenu$Animation$BoomEnum     // Catch:{ NoSuchFieldError -> 0x0014 }
                com.nightonke.boommenu.Animation.BoomEnum r2 = com.nightonke.boommenu.Animation.BoomEnum.LINE     // Catch:{ NoSuchFieldError -> 0x0014 }
                int r2 = r2.ordinal()     // Catch:{ NoSuchFieldError -> 0x0014 }
                r1[r2] = r0     // Catch:{ NoSuchFieldError -> 0x0014 }
            L_0x0014:
                r1 = 2
                int[] r2 = $SwitchMap$com$nightonke$boommenu$Animation$BoomEnum     // Catch:{ NoSuchFieldError -> 0x001f }
                com.nightonke.boommenu.Animation.BoomEnum r3 = com.nightonke.boommenu.Animation.BoomEnum.PARABOLA_1     // Catch:{ NoSuchFieldError -> 0x001f }
                int r3 = r3.ordinal()     // Catch:{ NoSuchFieldError -> 0x001f }
                r2[r3] = r1     // Catch:{ NoSuchFieldError -> 0x001f }
            L_0x001f:
                r2 = 3
                int[] r3 = $SwitchMap$com$nightonke$boommenu$Animation$BoomEnum     // Catch:{ NoSuchFieldError -> 0x002a }
                com.nightonke.boommenu.Animation.BoomEnum r4 = com.nightonke.boommenu.Animation.BoomEnum.PARABOLA_2     // Catch:{ NoSuchFieldError -> 0x002a }
                int r4 = r4.ordinal()     // Catch:{ NoSuchFieldError -> 0x002a }
                r3[r4] = r2     // Catch:{ NoSuchFieldError -> 0x002a }
            L_0x002a:
                int[] r3 = $SwitchMap$com$nightonke$boommenu$Animation$BoomEnum     // Catch:{ NoSuchFieldError -> 0x0035 }
                com.nightonke.boommenu.Animation.BoomEnum r4 = com.nightonke.boommenu.Animation.BoomEnum.PARABOLA_3     // Catch:{ NoSuchFieldError -> 0x0035 }
                int r4 = r4.ordinal()     // Catch:{ NoSuchFieldError -> 0x0035 }
                r5 = 4
                r3[r4] = r5     // Catch:{ NoSuchFieldError -> 0x0035 }
            L_0x0035:
                int[] r3 = $SwitchMap$com$nightonke$boommenu$Animation$BoomEnum     // Catch:{ NoSuchFieldError -> 0x0040 }
                com.nightonke.boommenu.Animation.BoomEnum r4 = com.nightonke.boommenu.Animation.BoomEnum.PARABOLA_4     // Catch:{ NoSuchFieldError -> 0x0040 }
                int r4 = r4.ordinal()     // Catch:{ NoSuchFieldError -> 0x0040 }
                r5 = 5
                r3[r4] = r5     // Catch:{ NoSuchFieldError -> 0x0040 }
            L_0x0040:
                int[] r3 = $SwitchMap$com$nightonke$boommenu$Animation$BoomEnum     // Catch:{ NoSuchFieldError -> 0x004b }
                com.nightonke.boommenu.Animation.BoomEnum r4 = com.nightonke.boommenu.Animation.BoomEnum.HORIZONTAL_THROW_1     // Catch:{ NoSuchFieldError -> 0x004b }
                int r4 = r4.ordinal()     // Catch:{ NoSuchFieldError -> 0x004b }
                r5 = 6
                r3[r4] = r5     // Catch:{ NoSuchFieldError -> 0x004b }
            L_0x004b:
                int[] r3 = $SwitchMap$com$nightonke$boommenu$Animation$BoomEnum     // Catch:{ NoSuchFieldError -> 0x0056 }
                com.nightonke.boommenu.Animation.BoomEnum r4 = com.nightonke.boommenu.Animation.BoomEnum.HORIZONTAL_THROW_2     // Catch:{ NoSuchFieldError -> 0x0056 }
                int r4 = r4.ordinal()     // Catch:{ NoSuchFieldError -> 0x0056 }
                r5 = 7
                r3[r4] = r5     // Catch:{ NoSuchFieldError -> 0x0056 }
            L_0x0056:
                int[] r3 = $SwitchMap$com$nightonke$boommenu$Animation$BoomEnum     // Catch:{ NoSuchFieldError -> 0x0062 }
                com.nightonke.boommenu.Animation.BoomEnum r4 = com.nightonke.boommenu.Animation.BoomEnum.RANDOM     // Catch:{ NoSuchFieldError -> 0x0062 }
                int r4 = r4.ordinal()     // Catch:{ NoSuchFieldError -> 0x0062 }
                r5 = 8
                r3[r4] = r5     // Catch:{ NoSuchFieldError -> 0x0062 }
            L_0x0062:
                int[] r3 = $SwitchMap$com$nightonke$boommenu$Animation$BoomEnum     // Catch:{ NoSuchFieldError -> 0x006e }
                com.nightonke.boommenu.Animation.BoomEnum r4 = com.nightonke.boommenu.Animation.BoomEnum.Unknown     // Catch:{ NoSuchFieldError -> 0x006e }
                int r4 = r4.ordinal()     // Catch:{ NoSuchFieldError -> 0x006e }
                r5 = 9
                r3[r4] = r5     // Catch:{ NoSuchFieldError -> 0x006e }
            L_0x006e:
                com.nightonke.boommenu.Animation.OrderEnum[] r3 = com.nightonke.boommenu.Animation.OrderEnum.values()
                int r3 = r3.length
                int[] r3 = new int[r3]
                $SwitchMap$com$nightonke$boommenu$Animation$OrderEnum = r3
                int[] r3 = $SwitchMap$com$nightonke$boommenu$Animation$OrderEnum     // Catch:{ NoSuchFieldError -> 0x0081 }
                com.nightonke.boommenu.Animation.OrderEnum r4 = com.nightonke.boommenu.Animation.OrderEnum.DEFAULT     // Catch:{ NoSuchFieldError -> 0x0081 }
                int r4 = r4.ordinal()     // Catch:{ NoSuchFieldError -> 0x0081 }
                r3[r4] = r0     // Catch:{ NoSuchFieldError -> 0x0081 }
            L_0x0081:
                int[] r0 = $SwitchMap$com$nightonke$boommenu$Animation$OrderEnum     // Catch:{ NoSuchFieldError -> 0x008b }
                com.nightonke.boommenu.Animation.OrderEnum r3 = com.nightonke.boommenu.Animation.OrderEnum.REVERSE     // Catch:{ NoSuchFieldError -> 0x008b }
                int r3 = r3.ordinal()     // Catch:{ NoSuchFieldError -> 0x008b }
                r0[r3] = r1     // Catch:{ NoSuchFieldError -> 0x008b }
            L_0x008b:
                int[] r0 = $SwitchMap$com$nightonke$boommenu$Animation$OrderEnum     // Catch:{ NoSuchFieldError -> 0x0095 }
                com.nightonke.boommenu.Animation.OrderEnum r1 = com.nightonke.boommenu.Animation.OrderEnum.RANDOM     // Catch:{ NoSuchFieldError -> 0x0095 }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x0095 }
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x0095 }
            L_0x0095:
                return
            */
            throw new UnsupportedOperationException("Method not decompiled: com.nightonke.boommenu.Animation.AnimationManager.C44861.<clinit>():void");
        }
    }

    private static float clamp(float f, float f2, float f3) {
        return f < f2 ? f2 : f > f3 ? f3 : f;
    }

    public static ObjectAnimator animate(Object obj, String str, long j, long j2, TimeInterpolator timeInterpolator, AnimatorListenerAdapter animatorListenerAdapter, float... fArr) {
        ObjectAnimator ofFloat = ObjectAnimator.ofFloat(obj, str, fArr);
        ofFloat.setStartDelay(j);
        ofFloat.setDuration(j2);
        if (timeInterpolator != null) {
            ofFloat.setInterpolator(timeInterpolator);
        }
        if (animatorListenerAdapter != null) {
            ofFloat.addListener(animatorListenerAdapter);
        }
        ofFloat.start();
        return ofFloat;
    }

    public static ObjectAnimator animate(Object obj, String str, long j, long j2, TimeInterpolator timeInterpolator, float... fArr) {
        return animate(obj, str, j, j2, timeInterpolator, (AnimatorListenerAdapter) null, fArr);
    }

    public static void animate(String str, long j, long j2, float[] fArr, TimeInterpolator timeInterpolator, ArrayList<View> arrayList) {
        Iterator it = arrayList.iterator();
        while (it.hasNext()) {
            animate(it.next(), str, j, j2, timeInterpolator, (AnimatorListenerAdapter) null, fArr);
        }
    }

    public static void rotate(BoomButton boomButton, long j, long j2, TimeInterpolator timeInterpolator, float... fArr) {
        boomButton.setRotateAnchorPoints();
        for (int i = 0; i < boomButton.rotateViews().size(); i++) {
            animate((Object) (View) boomButton.rotateViews().get(i), "rotation", j, j2, timeInterpolator, (AnimatorListenerAdapter) null, fArr);
        }
    }

    public static ObjectAnimator animate(Object obj, String str, long j, long j2, TypeEvaluator typeEvaluator, int... iArr) {
        return animate(obj, str, j, j2, typeEvaluator, (AnimatorListenerAdapter) null, iArr);
    }

    public static ObjectAnimator animate(Object obj, String str, long j, long j2, TypeEvaluator typeEvaluator, AnimatorListenerAdapter animatorListenerAdapter, int... iArr) {
        ObjectAnimator ofInt = ObjectAnimator.ofInt(obj, str, iArr);
        ofInt.setStartDelay(j);
        ofInt.setDuration(j2);
        ofInt.setEvaluator(typeEvaluator);
        if (animatorListenerAdapter != null) {
            ofInt.addListener(animatorListenerAdapter);
        }
        ofInt.start();
        return ofInt;
    }

    public static ArrayList<Integer> getOrderIndex(OrderEnum orderEnum, int i) {
        ArrayList<Integer> arrayList = new ArrayList<>();
        int i2 = C44861.$SwitchMap$com$nightonke$boommenu$Animation$OrderEnum[orderEnum.ordinal()];
        int i3 = 0;
        if (i2 == 1) {
            while (i3 < i) {
                arrayList.add(Integer.valueOf(i3));
                i3++;
            }
        } else if (i2 == 2) {
            while (i3 < i) {
                arrayList.add(Integer.valueOf((i - i3) - 1));
                i3++;
            }
        } else if (i2 == 3) {
            boolean[] zArr = new boolean[i];
            for (int i4 = 0; i4 < zArr.length; i4++) {
                zArr[i4] = false;
            }
            Random random = new Random();
            while (i3 < i) {
                int nextInt = random.nextInt(i);
                if (!zArr[nextInt]) {
                    zArr[nextInt] = true;
                    arrayList.add(Integer.valueOf(nextInt));
                    i3++;
                }
            }
        }
        return arrayList;
    }

    public static void calculateShowXY(BoomEnum boomEnum, PointF pointF, Ease ease, int i, PointF pointF2, PointF pointF3, float[] fArr, float[] fArr2) {
        PointF pointF4 = pointF;
        Ease ease2 = ease;
        int i2 = i;
        PointF pointF5 = pointF2;
        PointF pointF6 = pointF3;
        BoomEnum boomEnum2 = Math.abs(pointF5.x - pointF6.x) < 1.0f ? BoomEnum.LINE : boomEnum;
        float f = pointF5.x;
        float f2 = pointF5.y;
        float f3 = pointF6.x;
        float f4 = pointF6.y;
        float f5 = 1.0f / ((float) i2);
        float f6 = f3 - f;
        float f7 = f4 - f2;
        float f8 = 0.0f;
        int i3 = 0;
        switch (boomEnum2) {
            case LINE:
                break;
            case PARABOLA_1:
                float f9 = f + f3;
                float f10 = f9 / 2.0f;
                float f11 = f3 - f10;
                float f12 = f10 - f;
                float f13 = f - f3;
                float f14 = f * f;
                float min = (((f2 * f11) + (f4 * f12)) + (((Math.min(f2, f4) * 3.0f) / 4.0f) * f13)) / (((f11 * f14) + ((f3 * f3) * f12)) + ((f10 * f10) * f13));
                float f15 = ((f2 - f4) / f13) - (f9 * min);
                float f16 = (f2 - (f14 * min)) - (f * f15);
                while (i3 <= i2) {
                    float interpolation = (ease2.getInterpolation(f8) * f6) + f;
                    fArr[i3] = interpolation;
                    fArr2[i3] = (min * interpolation * interpolation) + (interpolation * f15) + f16;
                    f8 += f5;
                    i3++;
                }
                return;
            case PARABOLA_2:
                float f17 = f + f3;
                float f18 = f17 / 2.0f;
                float f19 = f3 - f18;
                float f20 = f18 - f;
                float f21 = f - f3;
                float max = (f2 * f19) + (f4 * f20) + (((pointF4.y + Math.max(f2, f4)) / 2.0f) * f21);
                float f22 = f * f;
                float f23 = max / (((f19 * f22) + ((f3 * f3) * f20)) + ((f18 * f18) * f21));
                float f24 = ((f2 - f4) / f21) - (f17 * f23);
                float f25 = (f2 - (f22 * f23)) - (f * f24);
                while (i3 <= i2) {
                    float interpolation2 = (ease2.getInterpolation(f8) * f6) + f;
                    fArr[i3] = interpolation2;
                    fArr2[i3] = (f23 * interpolation2 * interpolation2) + (interpolation2 * f24) + f25;
                    f8 += f5;
                    i3++;
                }
                return;
            case PARABOLA_3:
                float f26 = f2 + f4;
                float f27 = f26 / 2.0f;
                float f28 = f4 - f27;
                float f29 = f27 - f2;
                float f30 = f2 - f4;
                float f31 = f2 * f2;
                float min2 = (((f * f28) + (f3 * f29)) + ((Math.min(f, f3) / 2.0f) * f30)) / (((f28 * f31) + ((f4 * f4) * f29)) + ((f27 * f27) * f30));
                float f32 = ((f - f3) / f30) - (f26 * min2);
                float f33 = (f - (f31 * min2)) - (f2 * f32);
                while (i3 <= i2) {
                    float interpolation3 = (ease2.getInterpolation(f8) * f7) + f2;
                    fArr2[i3] = interpolation3;
                    fArr[i3] = (min2 * interpolation3 * interpolation3) + (interpolation3 * f32) + f33;
                    f8 += f5;
                    i3++;
                }
                return;
            case PARABOLA_4:
                float f34 = f2 + f4;
                float f35 = f34 / 2.0f;
                float f36 = f4 - f35;
                float f37 = f35 - f2;
                float f38 = f2 - f4;
                float max2 = (f * f36) + (f3 * f37) + (((pointF4.x + Math.max(f, f3)) / 2.0f) * f38);
                float f39 = f2 * f2;
                float f40 = max2 / (((f36 * f39) + ((f4 * f4) * f37)) + ((f35 * f35) * f38));
                float f41 = ((f - f3) / f38) - (f34 * f40);
                float f42 = (f - (f39 * f40)) - (f2 * f41);
                while (i3 <= i2) {
                    float interpolation4 = (ease2.getInterpolation(f8) * f7) + f2;
                    fArr2[i3] = interpolation4;
                    fArr[i3] = (f40 * interpolation4 * interpolation4) + (interpolation4 * f41) + f42;
                    f8 += f5;
                    i3++;
                }
                return;
            case HORIZONTAL_THROW_1:
                float f43 = (2.0f * f3) - f;
                float f44 = f43 - f3;
                float f45 = f - f43;
                float f46 = f * f;
                float f47 = (((f2 * f44) + (f2 * f6)) + (f4 * f45)) / (((f44 * f46) + ((f43 * f43) * f6)) + ((f3 * f3) * f45));
                float f48 = ((f2 - f2) / f45) - ((f43 + f) * f47);
                float f49 = (f2 - (f46 * f47)) - (f * f48);
                while (i3 <= i2) {
                    float interpolation5 = (ease2.getInterpolation(f8) * f6) + f;
                    fArr[i3] = interpolation5;
                    fArr2[i3] = (f47 * interpolation5 * interpolation5) + (interpolation5 * f48) + f49;
                    f8 += f5;
                    i3++;
                }
                return;
            case HORIZONTAL_THROW_2:
                float f50 = pointF5.x;
                float f51 = pointF5.y;
                float f52 = pointF6.x;
                float f53 = pointF6.y;
                float f54 = (2.0f * f50) - f52;
                float f55 = f54 - f50;
                float f56 = f50 - f52;
                float f57 = f52 - f54;
                float f58 = (f53 * f55) + (f53 * f56) + (f51 * f57);
                float f59 = f52 * f52;
                float f60 = f58 / (((f55 * f59) + ((f54 * f54) * f56)) + ((f50 * f50) * f57));
                float f61 = ((f53 - f53) / f57) - ((f54 + f52) * f60);
                float f62 = (f53 - (f59 * f60)) - (f52 * f61);
                while (i3 <= i2) {
                    float interpolation6 = (ease2.getInterpolation(f8) * f6) + f50;
                    fArr[i3] = interpolation6;
                    fArr2[i3] = (f60 * interpolation6 * interpolation6) + (interpolation6 * f61) + f62;
                    f8 += f5;
                    i3++;
                }
                return;
            case RANDOM:
                calculateShowXY(BoomEnum.values()[new Random().nextInt(BoomEnum.RANDOM.getValue())], pointF, ease, i, pointF2, pointF3, fArr, fArr2);
                return;
            case Unknown:
                throw new RuntimeException("Unknown boom-enum!");
            default:
                return;
        }
        while (i3 <= i2) {
            float interpolation7 = ease2.getInterpolation(f8);
            fArr[i3] = (interpolation7 * f6) + f;
            fArr2[i3] = (interpolation7 * f7) + f2;
            f8 += f5;
            i3++;
        }
    }

    public static void calculateHideXY(BoomEnum boomEnum, PointF pointF, Ease ease, int i, PointF pointF2, PointF pointF3, float[] fArr, float[] fArr2) {
        Ease ease2 = ease;
        int i2 = i;
        PointF pointF4 = pointF2;
        PointF pointF5 = pointF3;
        BoomEnum boomEnum2 = Math.abs(pointF4.x - pointF5.x) < 1.0f ? BoomEnum.LINE : boomEnum;
        float f = pointF4.x;
        float f2 = pointF4.y;
        float f3 = pointF5.x;
        float f4 = pointF5.y;
        float f5 = 1.0f / ((float) i2);
        float f6 = f3 - f;
        float f7 = 0.0f;
        int i3 = 0;
        switch (boomEnum2) {
            case LINE:
            case PARABOLA_1:
            case PARABOLA_2:
            case PARABOLA_3:
            case PARABOLA_4:
            case RANDOM:
            case Unknown:
                calculateShowXY(boomEnum2, pointF, ease, i, pointF2, pointF3, fArr, fArr2);
                return;
            case HORIZONTAL_THROW_1:
                float f8 = pointF4.x;
                float f9 = pointF4.y;
                float f10 = pointF5.x;
                float f11 = pointF5.y;
                float f12 = (2.0f * f8) - f10;
                float f13 = f12 - f8;
                float f14 = f8 - f10;
                float f15 = f10 - f12;
                float f16 = (f11 * f13) + (f11 * f14) + (f9 * f15);
                float f17 = f10 * f10;
                float f18 = f16 / (((f13 * f17) + ((f12 * f12) * f14)) + ((f8 * f8) * f15));
                float f19 = ((f11 - f11) / f15) - ((f12 + f10) * f18);
                float f20 = (f11 - (f17 * f18)) - (f10 * f19);
                while (i3 <= i2) {
                    float interpolation = (ease2.getInterpolation(f7) * f6) + f8;
                    fArr[i3] = interpolation;
                    fArr2[i3] = (f18 * interpolation * interpolation) + (interpolation * f19) + f20;
                    f7 += f5;
                    i3++;
                }
                return;
            case HORIZONTAL_THROW_2:
                float f21 = (2.0f * f3) - f;
                float f22 = f21 - f3;
                float f23 = f - f21;
                float f24 = (f2 * f22) + (f2 * f6) + (f4 * f23);
                float f25 = f * f;
                float f26 = f24 / (((f22 * f25) + ((f21 * f21) * f6)) + ((f3 * f3) * f23));
                float f27 = ((f2 - f2) / f23) - ((f21 + f) * f26);
                float f28 = (f2 - (f25 * f26)) - (f * f27);
                while (i3 <= i2) {
                    float interpolation2 = (ease2.getInterpolation(f7) * f6) + f;
                    fArr[i3] = interpolation2;
                    fArr2[i3] = (f26 * interpolation2 * interpolation2) + (interpolation2 * f27) + f28;
                    f7 += f5;
                    i3++;
                }
                return;
            default:
                return;
        }
    }

    public static Rotate3DAnimation getRotate3DAnimation(float[] fArr, float[] fArr2, long j, long j2, BoomButton boomButton) {
        Rotate3DAnimation rotate3DAnimation = new Rotate3DAnimation((float) (boomButton.trueWidth() / 2), (float) (boomButton.trueHeight() / 2), getRotateXs(fArr2, boomButton.type()), getRotateYs(fArr, boomButton.type()));
        rotate3DAnimation.setStartOffset(j);
        rotate3DAnimation.setDuration(j2);
        return rotate3DAnimation;
    }

    private static ArrayList<Float> getRotateXs(float[] fArr, ButtonEnum buttonEnum) {
        ArrayList<Float> arrayList = new ArrayList<>(fArr.length);
        float f = 0.0f;
        arrayList.add(Float.valueOf(0.0f));
        int i = buttonEnum.equals(ButtonEnum.Ham) ? 60 : 30;
        for (int i2 = 0; i2 < fArr.length; i2++) {
            if (i2 != 0) {
                double clamp = (double) (clamp((-(fArr[i2] - f)) / ((float) i), -0.7853982f, 0.7853982f) * 180.0f);
                Double.isNaN(clamp);
                arrayList.add(Float.valueOf((float) (clamp / 3.141592653589793d)));
            }
            f = fArr[i2];
        }
        addBufferValues(arrayList);
        return arrayList;
    }

    private static ArrayList<Float> getRotateYs(float[] fArr, ButtonEnum buttonEnum) {
        ArrayList<Float> arrayList = new ArrayList<>(fArr.length);
        float f = 0.0f;
        arrayList.add(Float.valueOf(0.0f));
        int i = buttonEnum.equals(ButtonEnum.Ham) ? 60 : 30;
        for (int i2 = 0; i2 < fArr.length; i2++) {
            if (i2 != 0) {
                double clamp = (double) (clamp((fArr[i2] - f) / ((float) i), -0.7853982f, 0.7853982f) * 180.0f);
                Double.isNaN(clamp);
                arrayList.add(Float.valueOf((float) (clamp / 3.141592653589793d)));
            }
            f = fArr[i2];
        }
        addBufferValues(arrayList);
        return arrayList;
    }

    private static void addBufferValues(ArrayList<Float> arrayList) {
        if (((Float) arrayList.get(arrayList.size() - 1)).floatValue() != 0.0f) {
            arrayList.add(Float.valueOf(((Float) arrayList.get(arrayList.size() - 1)).floatValue() * 0.5f));
            arrayList.add(Float.valueOf(((Float) arrayList.get(arrayList.size() - 1)).floatValue() * 0.5f));
            arrayList.add(Float.valueOf(((Float) arrayList.get(arrayList.size() - 1)).floatValue() * 0.5f));
            arrayList.add(Float.valueOf(0.0f));
        }
    }
}
