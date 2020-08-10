package com.nightonke.boommenu.Animation;

import android.animation.TimeInterpolator;
import android.graphics.PointF;
import com.google.firebase.remoteconfig.FirebaseRemoteConfig;
import java.util.ArrayList;

public class Ease implements TimeInterpolator {
    private static ArrayList<Ease> eases;

    /* renamed from: a */
    private PointF f2234a = new PointF();
    private Boolean ableToDefineWithControlPoints = Boolean.valueOf(true);

    /* renamed from: b */
    private PointF f2235b = new PointF();

    /* renamed from: c */
    private PointF f2236c = new PointF();
    private EaseEnum easeEnum;
    private PointF end = new PointF(1.0f, 1.0f);
    private PointF start = new PointF(0.0f, 0.0f);

    public static Ease getInstance(EaseEnum easeEnum2) {
        if (eases == null) {
            eases = new ArrayList<>(EaseEnum.values().length);
            for (int length = EaseEnum.values().length; length > 0; length--) {
                eases.add(null);
            }
        }
        Ease ease = (Ease) eases.get(easeEnum2.getValue());
        if (ease != null) {
            return ease;
        }
        Ease ease2 = new Ease(easeEnum2);
        eases.set(easeEnum2.getValue(), ease2);
        return ease2;
    }

    private Ease(EaseEnum easeEnum2) {
        switch (easeEnum2) {
            case Linear:
                init((double) FirebaseRemoteConfig.DEFAULT_VALUE_FOR_DOUBLE, (double) FirebaseRemoteConfig.DEFAULT_VALUE_FOR_DOUBLE, 1.0d, 1.0d);
                break;
            case EaseInSine:
                init(0.47d, (double) FirebaseRemoteConfig.DEFAULT_VALUE_FOR_DOUBLE, 0.745d, 0.715d);
                break;
            case EaseOutSine:
                init(0.39d, 0.575d, 0.565d, 1.0d);
                break;
            case EaseInOutSine:
                init(0.445d, 0.05d, 0.55d, 0.95d);
                break;
            case EaseInQuad:
                init(0.55d, 0.085d, 0.68d, 0.53d);
                break;
            case EaseOutQuad:
                init(0.25d, 0.46d, 0.45d, 0.94d);
                break;
            case EaseInOutQuad:
                init(0.455d, 0.03d, 0.515d, 0.955d);
                break;
            case EaseInCubic:
                init(0.55d, 0.055d, 0.675d, 0.19d);
                break;
            case EaseOutCubic:
                init(0.215d, 0.61d, 0.355d, 1.0d);
                break;
            case EaseInOutCubic:
                init(0.645d, 0.045d, 0.335d, 1.0d);
                break;
            case EaseInQuart:
                init(0.895d, 0.03d, 0.685d, 0.22d);
                break;
            case EaseOutQuart:
                init(0.165d, 0.84d, 0.44d, 1.0d);
                break;
            case EaseInOutQuart:
                init(0.77d, (double) FirebaseRemoteConfig.DEFAULT_VALUE_FOR_DOUBLE, 0.175d, 1.0d);
                break;
            case EaseInQuint:
                init(0.755d, 0.05d, 0.855d, 0.06d);
                break;
            case EaseOutQuint:
                init(0.23d, 1.0d, 0.32d, 1.0d);
                break;
            case EaseInOutQuint:
                init(0.86d, (double) FirebaseRemoteConfig.DEFAULT_VALUE_FOR_DOUBLE, 0.07d, 1.0d);
                break;
            case EaseInCirc:
                init(0.6d, 0.04d, 0.98d, 0.335d);
                break;
            case EaseOutCirc:
                init(0.075d, 0.82d, 0.165d, 1.0d);
                break;
            case EaseInOutCirc:
                init(0.785d, 0.135d, 0.15d, 0.86d);
                break;
            case EaseInExpo:
                init(0.95d, 0.05d, 0.795d, 0.035d);
                break;
            case EaseOutExpo:
                init(0.19d, 1.0d, 0.22d, 1.0d);
                break;
            case EaseInOutExpo:
                init(1.0d, (double) FirebaseRemoteConfig.DEFAULT_VALUE_FOR_DOUBLE, (double) FirebaseRemoteConfig.DEFAULT_VALUE_FOR_DOUBLE, 1.0d);
                break;
            case EaseInBack:
                init(0.6d, -0.2d, 0.735d, 0.045d);
                break;
            case EaseOutBack:
                init(0.174d, 0.885d, 0.32d, 1.275d);
                break;
            case EaseInOutBack:
                init(0.68d, -0.55d, 0.265d, 1.55d);
                break;
            case EaseInBounce:
            case EaseOutBounce:
            case EaseInOutBounce:
            case EaseInElastic:
            case EaseOutElastic:
            case EaseInOutElastic:
                this.ableToDefineWithControlPoints = Boolean.valueOf(false);
                break;
            default:
                throw new RuntimeException("Ease-enum not found!");
        }
        this.easeEnum = easeEnum2;
    }

    private void init(float f, float f2, float f3, float f4) {
        this.ableToDefineWithControlPoints = Boolean.valueOf(true);
        this.start = new PointF(f, f2);
        this.end = new PointF(f3, f4);
    }

    private void init(double d, double d2, double d3, double d4) {
        init((float) d, (float) d2, (float) d3, (float) d4);
    }

    public float getInterpolation(float f) {
        if (this.ableToDefineWithControlPoints.booleanValue()) {
            return getBezierCoordinateY(f);
        }
        switch (this.easeEnum) {
            case EaseInBounce:
                return getEaseInBounceOffset(f);
            case EaseOutBounce:
                return getEaseOutBounceOffset(f);
            case EaseInOutBounce:
                return getEaseInOutBounceOffset(f);
            case EaseInElastic:
                return getEaseInElasticOffset(f);
            case EaseOutElastic:
                return getEaseOutElasticOffset(f);
            case EaseInOutElastic:
                return getEaseInOutElasticOffset(f);
            default:
                throw new RuntimeException("Wrong ease-enum initialize method.");
        }
    }

    private float getBezierCoordinateY(float f) {
        if (this.start.x == 0.0f && this.start.y == 0.0f && this.end.x == 1.0f && this.end.y == 1.0f) {
            return f;
        }
        this.f2236c.y = this.start.y * 3.0f;
        this.f2235b.y = ((this.end.y - this.start.y) * 3.0f) - this.f2236c.y;
        this.f2234a.y = (1.0f - this.f2236c.y) - this.f2235b.y;
        return f * (this.f2236c.y + ((this.f2235b.y + (this.f2234a.y * f)) * f));
    }

    private float getEaseInBounceOffset(float f) {
        return (1.0f - getEaseBounceOffsetHelper2(1.0f - f, 0.0f, 1.0f, 1.0f)) + 0.0f;
    }

    private float getEaseBounceOffsetHelper1(float f, float f2, float f3, float f4) {
        return (f3 - getEaseBounceOffsetHelper2(f4 - f, 0.0f, f3, f4)) + f2;
    }

    private float getEaseBounceOffsetHelper2(float f, float f2, float f3, float f4) {
        float f5;
        float f6;
        float f7;
        float f8 = f / f4;
        double d = (double) f8;
        if (d < 0.36363636363636365d) {
            f7 = 7.5625f * f8 * f8;
        } else {
            if (d < 0.7272727272727273d) {
                Double.isNaN(d);
                float f9 = (float) (d - 0.5454545454545454d);
                f5 = 7.5625f * f9 * f9;
                f6 = 0.75f;
            } else if (d < 0.7272727272727273d) {
                Double.isNaN(d);
                float f10 = (float) (d - 0.5454545454545454d);
                f5 = 7.5625f * f10 * f10;
                f6 = 0.9375f;
            } else {
                Double.isNaN(d);
                float f11 = (float) (d - 0.9545454545454546d);
                f5 = 7.5625f * f11 * f11;
                f6 = 0.984375f;
            }
            f7 = f5 + f6;
        }
        return (f3 * f7) + f2;
    }

    private float getEaseOutBounceOffset(float f) {
        float f2;
        float f3;
        float f4;
        float f5 = f / 1.0f;
        double d = (double) f5;
        if (d < 0.36363636363636365d) {
            f4 = 7.5625f * f5 * f5;
        } else {
            if (d < 0.7272727272727273d) {
                Double.isNaN(d);
                float f6 = (float) (d - 0.5454545454545454d);
                f2 = 7.5625f * f6 * f6;
                f3 = 0.75f;
            } else if (d < 0.9090909090909091d) {
                Double.isNaN(d);
                float f7 = (float) (d - 0.8181818181818182d);
                f2 = 7.5625f * f7 * f7;
                f3 = 0.9375f;
            } else {
                Double.isNaN(d);
                float f8 = (float) (d - 0.9545454545454546d);
                f2 = 7.5625f * f8 * f8;
                f3 = 0.984375f;
            }
            f4 = f2 + f3;
        }
        return (f4 * 1.0f) + 0.0f;
    }

    private float getEaseInOutBounceOffset(float f) {
        float easeBounceOffsetHelper2;
        if (f < 0.5f) {
            easeBounceOffsetHelper2 = getEaseBounceOffsetHelper1(f * 2.0f, 0.0f, 1.0f, 1.0f) * 0.5f;
        } else {
            easeBounceOffsetHelper2 = (getEaseBounceOffsetHelper2(f * 2.0f, 0.0f, 1.0f, 1.0f) * 0.5f) + 0.5f;
        }
        return easeBounceOffsetHelper2 + 0.0f;
    }

    private float getEaseInElasticOffset(float f) {
        if (f == 0.0f) {
            return 0.0f;
        }
        float f2 = f / 1.0f;
        if (f2 == 1.0f) {
            return 1.0f;
        }
        float f3 = f2 - 1.0f;
        return (-(((float) Math.pow(2.0d, (double) (10.0f * f3))) * 1.0f * ((float) Math.sin((double) ((((f3 * 1.0f) - 0.075f) * 6.2831855f) / 0.3f))))) + 0.0f;
    }

    private float getEaseOutElasticOffset(float f) {
        if (f == 0.0f) {
            return 0.0f;
        }
        float f2 = f / 1.0f;
        if (f2 == 1.0f) {
            return 1.0f;
        }
        return (((float) Math.pow(2.0d, (double) (-10.0f * f2))) * 1.0f * ((float) Math.sin((double) ((((f2 * 1.0f) - 0.075f) * 6.2831855f) / 0.3f)))) + 1.0f + 0.0f;
    }

    private float getEaseInOutElasticOffset(float f) {
        float pow;
        if (f == 0.0f) {
            return 0.0f;
        }
        float f2 = f / 0.5f;
        if (f2 == 2.0f) {
            return 1.0f;
        }
        if (f2 < 1.0f) {
            float f3 = f2 - 1.0f;
            pow = ((float) Math.pow(2.0d, (double) (10.0f * f3))) * 1.0f * ((float) Math.sin((double) ((((f3 * 1.0f) - 0.1125f) * 6.2831855f) / 0.45f))) * -0.5f;
        } else {
            float f4 = f2 - 1.0f;
            pow = (((float) Math.pow(2.0d, (double) (-10.0f * f4))) * 1.0f * ((float) Math.sin((double) ((((f4 * 1.0f) - 0.1125f) * 6.2831855f) / 0.45f))) * 0.5f) + 1.0f;
        }
        return pow + 0.0f;
    }
}
