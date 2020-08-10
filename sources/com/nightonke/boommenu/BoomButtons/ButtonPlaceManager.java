package com.nightonke.boommenu.BoomButtons;

import android.graphics.Point;
import android.graphics.PointF;
import com.nightonke.boommenu.BoomMenuButton;
import com.nightonke.boommenu.ButtonEnum;
import java.util.ArrayList;
import java.util.Iterator;

public class ButtonPlaceManager {
    public static ArrayList<PointF> getPositions(Point point, float f, float f2, int i, BoomMenuButton boomMenuButton) {
        float f3;
        int i2;
        Point point2 = point;
        float f4 = f;
        float f5 = f2;
        int i3 = i;
        ArrayList<PointF> arrayList = new ArrayList<>(i3);
        int i4 = i3 / 2;
        float buttonHorizontalMargin = boomMenuButton.getButtonHorizontalMargin();
        float f6 = buttonHorizontalMargin / 2.0f;
        float f7 = buttonHorizontalMargin * 1.5f;
        float f8 = buttonHorizontalMargin * 2.0f;
        float buttonVerticalMargin = boomMenuButton.getButtonVerticalMargin();
        float f9 = buttonVerticalMargin / 2.0f;
        float f10 = buttonVerticalMargin * 1.5f;
        float f11 = buttonVerticalMargin * 2.0f;
        float f12 = f4 / 2.0f;
        float f13 = f4 * 1.5f;
        float f14 = f4 * 2.0f;
        int i5 = i4;
        float f15 = f5 / 2.0f;
        float f16 = 1.5f * f5;
        float f17 = 2.0f * f5;
        switch (boomMenuButton.getButtonPlaceEnum()) {
            case Horizontal:
                i2 = i;
                int i6 = i5;
                f3 = 0.0f;
                if (i2 % 2 != 0) {
                    for (int i7 = i6 - 1; i7 >= 0; i7--) {
                        arrayList.add(point(((-f4) - buttonHorizontalMargin) - (((float) i7) * (f4 + buttonHorizontalMargin)), 0.0f));
                    }
                    arrayList.add(point(0.0f, 0.0f));
                    for (int i8 = 0; i8 < i6; i8++) {
                        float f18 = f4 + buttonHorizontalMargin;
                        arrayList.add(point(f18 + (((float) i8) * f18), 0.0f));
                    }
                    break;
                } else {
                    for (int i9 = i6 - 1; i9 >= 0; i9--) {
                        arrayList.add(point(((-f12) - f6) - (((float) i9) * (f4 + buttonHorizontalMargin)), 0.0f));
                    }
                    for (int i10 = 0; i10 < i6; i10++) {
                        arrayList.add(point(f12 + f6 + (((float) i10) * (f4 + buttonHorizontalMargin)), 0.0f));
                    }
                    break;
                }
            case Vertical:
            case HAM_1:
            case HAM_2:
            case HAM_3:
            case HAM_4:
            case HAM_5:
            case HAM_6:
                i2 = i;
                f3 = 0.0f;
                if (i2 % 2 != 0) {
                    int i11 = i5;
                    for (int i12 = i11 - 1; i12 >= 0; i12--) {
                        arrayList.add(point(0.0f, ((-f5) - buttonVerticalMargin) - (((float) i12) * (f5 + buttonVerticalMargin))));
                    }
                    arrayList.add(point(0.0f, 0.0f));
                    for (int i13 = 0; i13 < i11; i13++) {
                        float f19 = f5 + buttonVerticalMargin;
                        arrayList.add(point(0.0f, f19 + (((float) i13) * f19)));
                    }
                    break;
                } else {
                    for (int i14 = i5 - 1; i14 >= 0; i14--) {
                        arrayList.add(point(0.0f, ((-f15) - f9) - (((float) i14) * (f5 + buttonVerticalMargin))));
                    }
                    int i15 = i5;
                    for (int i16 = 0; i16 < i15; i16++) {
                        arrayList.add(point(0.0f, f15 + f9 + (((float) i16) * (f5 + buttonVerticalMargin))));
                    }
                    break;
                }
            case SC_1:
                arrayList.add(point(0.0f, 0.0f));
                break;
            case SC_2_1:
                arrayList.add(point((-f6) - f12, 0.0f));
                arrayList.add(point(f6 + f12, 0.0f));
                break;
            case SC_2_2:
                arrayList.add(point(0.0f, (-f9) - f15));
                arrayList.add(point(0.0f, f9 + f15));
                break;
            case SC_3_1:
                arrayList.add(point((-buttonHorizontalMargin) - f4, 0.0f));
                arrayList.add(point(0.0f, 0.0f));
                arrayList.add(point(buttonHorizontalMargin + f4, 0.0f));
                break;
            case SC_3_2:
                arrayList.add(point(0.0f, (-buttonVerticalMargin) - f5));
                arrayList.add(point(0.0f, 0.0f));
                arrayList.add(point(0.0f, buttonVerticalMargin + f5));
                break;
            case SC_3_3:
                float f20 = (-f9) - f15;
                arrayList.add(point((-f6) - f12, f20));
                arrayList.add(point(f6 + f12, f20));
                arrayList.add(point(0.0f, f9 + f15));
                break;
            case SC_3_4:
                arrayList.add(point(0.0f, (-f9) - f15));
                float f21 = f9 + f15;
                arrayList.add(point((-f6) - f12, f21));
                arrayList.add(point(f6 + f12, f21));
                break;
            case SC_4_1:
                float f22 = (-f6) - f12;
                float f23 = (-f9) - f15;
                arrayList.add(point(f22, f23));
                float f24 = f6 + f12;
                arrayList.add(point(f24, f23));
                float f25 = f9 + f15;
                arrayList.add(point(f22, f25));
                arrayList.add(point(f24, f25));
                break;
            case SC_4_2:
                arrayList.add(point(0.0f, (-f9) - f15));
                arrayList.add(point((-buttonHorizontalMargin) - f4, 0.0f));
                arrayList.add(point(buttonHorizontalMargin + f4, 0.0f));
                arrayList.add(point(0.0f, f9 + f15));
                break;
            case SC_5_1:
                float f26 = (-f9) - f15;
                arrayList.add(point((-buttonHorizontalMargin) - f4, f26));
                arrayList.add(point(0.0f, f26));
                arrayList.add(point(buttonHorizontalMargin + f4, f26));
                float f27 = f9 + f15;
                arrayList.add(point((-f6) - f12, f27));
                arrayList.add(point(f6 + f12, f27));
                break;
            case SC_5_2:
                float f28 = (-f9) - f15;
                arrayList.add(point((-f6) - f12, f28));
                arrayList.add(point(f6 + f12, f28));
                float f29 = f9 + f15;
                arrayList.add(point((-buttonHorizontalMargin) - f4, f29));
                arrayList.add(point(0.0f, f29));
                arrayList.add(point(buttonHorizontalMargin + f4, f29));
                break;
            case SC_5_3:
                arrayList.add(point(0.0f, (-buttonVerticalMargin) - f5));
                arrayList.add(point((-buttonHorizontalMargin) - f4, 0.0f));
                arrayList.add(point(0.0f, 0.0f));
                arrayList.add(point(buttonHorizontalMargin + f4, 0.0f));
                arrayList.add(point(0.0f, buttonVerticalMargin + f5));
                break;
            case SC_5_4:
                float f30 = (-buttonHorizontalMargin) - f4;
                float f31 = (-f9) - f15;
                arrayList.add(point(f30, f31));
                float f32 = buttonHorizontalMargin + f4;
                arrayList.add(point(f32, f31));
                arrayList.add(point(0.0f, 0.0f));
                float f33 = f9 + f15;
                arrayList.add(point(f30, f33));
                arrayList.add(point(f32, f33));
                break;
            case SC_6_1:
                float f34 = (-buttonHorizontalMargin) - f4;
                float f35 = (-f9) - f15;
                arrayList.add(point(f34, f35));
                arrayList.add(point(0.0f, f35));
                float f36 = buttonHorizontalMargin + f4;
                arrayList.add(point(f36, f35));
                float f37 = f9 + f15;
                arrayList.add(point(f34, f37));
                arrayList.add(point(0.0f, f37));
                arrayList.add(point(f36, f37));
                break;
            case SC_6_2:
                float f38 = (-f6) - f12;
                float f39 = (-buttonVerticalMargin) - f5;
                arrayList.add(point(f38, f39));
                float f40 = f6 + f12;
                arrayList.add(point(f40, f39));
                arrayList.add(point(f38, 0.0f));
                arrayList.add(point(f40, 0.0f));
                float f41 = buttonVerticalMargin + f5;
                arrayList.add(point(f38, f41));
                arrayList.add(point(f40, f41));
                break;
            case SC_6_3:
                float f42 = (-f6) - f12;
                float f43 = (-buttonVerticalMargin) - f5;
                arrayList.add(point(f42, f43));
                float f44 = f6 + f12;
                arrayList.add(point(f44, f43));
                arrayList.add(point((-buttonHorizontalMargin) - f4, 0.0f));
                arrayList.add(point(buttonHorizontalMargin + f4, 0.0f));
                float f45 = buttonVerticalMargin + f5;
                arrayList.add(point(f42, f45));
                arrayList.add(point(f44, f45));
                break;
            case SC_6_4:
                arrayList.add(point(0.0f, (-buttonVerticalMargin) - f5));
                float f46 = (-buttonHorizontalMargin) - f4;
                float f47 = (-f9) - f15;
                arrayList.add(point(f46, f47));
                float f48 = buttonHorizontalMargin + f4;
                arrayList.add(point(f48, f47));
                float f49 = f9 + f15;
                arrayList.add(point(f46, f49));
                arrayList.add(point(f48, f49));
                arrayList.add(point(0.0f, buttonVerticalMargin + f5));
                break;
            case SC_6_5:
                float f50 = (-buttonVerticalMargin) - f5;
                arrayList.add(point((-buttonHorizontalMargin) - f4, f50));
                arrayList.add(point(0.0f, f50));
                arrayList.add(point(buttonHorizontalMargin + f4, f50));
                arrayList.add(point((-f6) - f12, 0.0f));
                arrayList.add(point(f6 + f12, 0.0f));
                arrayList.add(point(0.0f, buttonVerticalMargin + f5));
                break;
            case SC_6_6:
                arrayList.add(point(0.0f, (-buttonVerticalMargin) - f5));
                arrayList.add(point((-f6) - f12, 0.0f));
                arrayList.add(point(f6 + f12, 0.0f));
                float f51 = buttonVerticalMargin + f5;
                arrayList.add(point((-buttonHorizontalMargin) - f4, f51));
                arrayList.add(point(0.0f, f51));
                arrayList.add(point(buttonHorizontalMargin + f4, f51));
                break;
            case SC_7_1:
                float f52 = (-buttonHorizontalMargin) - f4;
                float f53 = (-buttonVerticalMargin) - f5;
                arrayList.add(point(f52, f53));
                arrayList.add(point(0.0f, f53));
                float f54 = buttonHorizontalMargin + f4;
                arrayList.add(point(f54, f53));
                arrayList.add(point(f52, 0.0f));
                arrayList.add(point(0.0f, 0.0f));
                arrayList.add(point(f54, 0.0f));
                arrayList.add(point(0.0f, buttonVerticalMargin + f5));
                break;
            case SC_7_2:
                float f55 = (-buttonHorizontalMargin) - f4;
                float f56 = (-buttonVerticalMargin) - f5;
                arrayList.add(point(f55, f56));
                arrayList.add(point(0.0f, f56));
                float f57 = buttonHorizontalMargin + f4;
                arrayList.add(point(f57, f56));
                arrayList.add(point(f55, 0.0f));
                arrayList.add(point(0.0f, 0.0f));
                arrayList.add(point(f57, 0.0f));
                arrayList.add(point(0.0f, buttonVerticalMargin + f5));
                break;
            case SC_7_3:
                float f58 = (-f6) - f12;
                float f59 = (-buttonVerticalMargin) - f5;
                arrayList.add(point(f58, f59));
                float f60 = f6 + f12;
                arrayList.add(point(f60, f59));
                arrayList.add(point((-buttonHorizontalMargin) - f4, 0.0f));
                arrayList.add(point(0.0f, 0.0f));
                arrayList.add(point(buttonHorizontalMargin + f4, 0.0f));
                float f61 = buttonVerticalMargin + f5;
                arrayList.add(point(f58, f61));
                arrayList.add(point(f60, f61));
                break;
            case SC_7_4:
                arrayList.add(point(0.0f, (-buttonVerticalMargin) - f5));
                float f62 = (-buttonHorizontalMargin) - f4;
                float f63 = (-f9) - f15;
                arrayList.add(point(f62, f63));
                float f64 = buttonHorizontalMargin + f4;
                arrayList.add(point(f64, f63));
                arrayList.add(point(0.0f, 0.0f));
                float f65 = f9 + f15;
                arrayList.add(point(f62, f65));
                arrayList.add(point(f64, f65));
                arrayList.add(point(0.0f, buttonVerticalMargin + f5));
                break;
            case SC_7_5:
                float f66 = (-f9) - f15;
                arrayList.add(point((-f7) - f13, f66));
                arrayList.add(point((-f6) - f12, f66));
                arrayList.add(point(f6 + f12, f66));
                arrayList.add(point(f7 + f13, f66));
                float f67 = f9 + f15;
                arrayList.add(point((-buttonHorizontalMargin) - f4, f67));
                arrayList.add(point(0.0f, f67));
                arrayList.add(point(buttonHorizontalMargin + f4, f67));
                break;
            case SC_7_6:
                float f68 = (-f9) - f15;
                arrayList.add(point((-buttonHorizontalMargin) - f4, f68));
                arrayList.add(point(0.0f, f68));
                arrayList.add(point(buttonHorizontalMargin + f4, f68));
                float f69 = f9 + f15;
                arrayList.add(point((-f7) - f13, f69));
                arrayList.add(point((-f6) - f12, f69));
                arrayList.add(point(f6 + f12, f69));
                arrayList.add(point(f7 + f13, f69));
                break;
            case SC_8_1:
                float f70 = (-buttonHorizontalMargin) - f4;
                float f71 = (-buttonVerticalMargin) - f5;
                arrayList.add(point(f70, f71));
                arrayList.add(point(0.0f, f71));
                float f72 = buttonHorizontalMargin + f4;
                arrayList.add(point(f72, f71));
                arrayList.add(point((-f6) - f12, 0.0f));
                arrayList.add(point(f6 + f12, 0.0f));
                float f73 = buttonVerticalMargin + f5;
                arrayList.add(point(f70, f73));
                arrayList.add(point(0.0f, f73));
                arrayList.add(point(f72, f73));
                break;
            case SC_8_2:
                float f74 = (-buttonHorizontalMargin) - f4;
                float f75 = (-buttonVerticalMargin) - f5;
                arrayList.add(point(f74, f75));
                float f76 = buttonHorizontalMargin + f4;
                arrayList.add(point(f76, f75));
                arrayList.add(point(0.0f, (-f9) - f15));
                arrayList.add(point(f74, 0.0f));
                arrayList.add(point(f76, 0.0f));
                arrayList.add(point(0.0f, f9 + f15));
                float f77 = buttonVerticalMargin + f5;
                arrayList.add(point(f74, f77));
                arrayList.add(point(f76, f77));
                break;
            case SC_8_3:
                float f78 = (-buttonHorizontalMargin) - f4;
                float f79 = (-buttonVerticalMargin) - f5;
                arrayList.add(point(f78, f79));
                arrayList.add(point(0.0f, f79));
                float f80 = buttonHorizontalMargin + f4;
                arrayList.add(point(f80, f79));
                arrayList.add(point(f78, 0.0f));
                arrayList.add(point(f80, 0.0f));
                float f81 = buttonVerticalMargin + f5;
                arrayList.add(point(f78, f81));
                arrayList.add(point(0.0f, f81));
                arrayList.add(point(f80, f81));
                break;
            case SC_8_4:
                arrayList.add(point(0.0f, (-f11) - f17));
                float f82 = (-f6) - f12;
                float f83 = (-buttonVerticalMargin) - f5;
                arrayList.add(point(f82, f83));
                float f84 = f6 + f12;
                arrayList.add(point(f84, f83));
                arrayList.add(point((-buttonHorizontalMargin) - f4, 0.0f));
                arrayList.add(point(buttonHorizontalMargin + f4, 0.0f));
                float f85 = buttonVerticalMargin + f5;
                arrayList.add(point(f82, f85));
                arrayList.add(point(f84, f85));
                arrayList.add(point(0.0f, f11 + f17));
                break;
            case SC_8_5:
                arrayList.add(point(0.0f, (-buttonVerticalMargin) - f5));
                float f86 = (-buttonHorizontalMargin) - f4;
                float f87 = (-f9) - f15;
                arrayList.add(point(f86, f87));
                float f88 = buttonHorizontalMargin + f4;
                arrayList.add(point(f88, f87));
                arrayList.add(point((-f8) - f14, 0.0f));
                arrayList.add(point(f8 + f14, 0.0f));
                float f89 = f9 + f15;
                arrayList.add(point(f86, f89));
                arrayList.add(point(f88, f89));
                arrayList.add(point(0.0f, buttonVerticalMargin + f5));
                break;
            case SC_8_6:
                float f90 = (-f7) - f13;
                float f91 = (-f9) - f15;
                arrayList.add(point(f90, f91));
                float f92 = (-f6) - f12;
                arrayList.add(point(f92, f91));
                float f93 = f6 + f12;
                arrayList.add(point(f93, f91));
                float f94 = f7 + f13;
                arrayList.add(point(f94, f91));
                float f95 = f9 + f15;
                arrayList.add(point(f90, f95));
                arrayList.add(point(f92, f95));
                arrayList.add(point(f93, f95));
                arrayList.add(point(f94, f95));
                break;
            case SC_8_7:
                float f96 = (-f6) - f12;
                float f97 = (-f10) - f16;
                arrayList.add(point(f96, f97));
                float f98 = f6 + f12;
                arrayList.add(point(f98, f97));
                float f99 = (-f9) - f15;
                arrayList.add(point(f96, f99));
                arrayList.add(point(f98, f99));
                float f100 = f9 + f15;
                arrayList.add(point(f96, f100));
                arrayList.add(point(f98, f100));
                float f101 = f10 + f16;
                arrayList.add(point(f96, f101));
                arrayList.add(point(f98, f101));
                break;
            case SC_9_1:
                float f102 = (-buttonHorizontalMargin) - f4;
                float f103 = (-buttonVerticalMargin) - f5;
                arrayList.add(point(f102, f103));
                arrayList.add(point(0.0f, f103));
                float f104 = buttonHorizontalMargin + f4;
                arrayList.add(point(f104, f103));
                arrayList.add(point(f102, 0.0f));
                arrayList.add(point(0.0f, 0.0f));
                arrayList.add(point(f104, 0.0f));
                float f105 = buttonVerticalMargin + f5;
                arrayList.add(point(f102, f105));
                arrayList.add(point(0.0f, f105));
                arrayList.add(point(f104, f105));
                break;
            case SC_9_2:
                arrayList.add(point(0.0f, (-f11) - f17));
                float f106 = (-f6) - f12;
                float f107 = (-buttonVerticalMargin) - f5;
                arrayList.add(point(f106, f107));
                float f108 = f6 + f12;
                arrayList.add(point(f108, f107));
                arrayList.add(point((-buttonHorizontalMargin) - f4, 0.0f));
                arrayList.add(point(0.0f, 0.0f));
                arrayList.add(point(buttonHorizontalMargin + f4, 0.0f));
                float f109 = buttonVerticalMargin + f5;
                arrayList.add(point(f106, f109));
                arrayList.add(point(f108, f109));
                arrayList.add(point(0.0f, f11 + f17));
                break;
            case SC_9_3:
                arrayList.add(point(0.0f, (-buttonVerticalMargin) - f5));
                float f110 = (-buttonHorizontalMargin) - f4;
                float f111 = (-f9) - f15;
                arrayList.add(point(f110, f111));
                float f112 = buttonHorizontalMargin + f4;
                arrayList.add(point(f112, f111));
                arrayList.add(point((-f8) - f14, 0.0f));
                arrayList.add(point(0.0f, 0.0f));
                arrayList.add(point(f8 + f14, 0.0f));
                float f113 = f9 + f15;
                arrayList.add(point(f110, f113));
                arrayList.add(point(f112, f113));
                arrayList.add(point(0.0f, buttonVerticalMargin + f5));
                break;
            case Custom:
                Iterator it = boomMenuButton.getCustomButtonPlacePositions().iterator();
                while (it.hasNext()) {
                    PointF pointF = (PointF) it.next();
                    arrayList.add(point(pointF.x, pointF.y));
                }
                break;
            default:
                throw new RuntimeException("Button place enum not found!");
        }
        i2 = i;
        f3 = 0.0f;
        switch (boomMenuButton.getButtonPlaceEnum()) {
            case SC_3_3:
                double pow = Math.pow((double) (f6 + f12), 2.0d);
                double d = (double) (buttonVerticalMargin + f5);
                Double.isNaN(d);
                adjust(arrayList, f3, (float) (pow / d));
                break;
            case SC_3_4:
                double d2 = -Math.pow((double) (f6 + f12), 2.0d);
                double d3 = (double) (buttonVerticalMargin + f5);
                Double.isNaN(d3);
                adjust(arrayList, f3, (float) (d2 / d3));
                break;
            case SC_4_2:
            case SC_5_1:
            case SC_5_2:
            case SC_5_3:
            case SC_5_4:
            case SC_6_1:
            case SC_6_2:
            case SC_6_3:
            case SC_6_4:
            case SC_6_5:
            case SC_6_6:
            case SC_7_1:
            case SC_7_2:
            case SC_7_3:
            case SC_7_4:
            case SC_7_5:
            case SC_7_6:
            case SC_8_1:
            case SC_8_2:
            case SC_8_3:
            case SC_8_4:
            case SC_8_5:
            case SC_8_6:
            case SC_8_7:
            case SC_9_1:
            case SC_9_2:
            case SC_9_3:
                adjust(arrayList, f3, f15 - f12);
                break;
            default:
                if (i2 >= 2 && boomMenuButton.getButtonEnum().equals(ButtonEnum.Ham) && boomMenuButton.getBottomHamButtonTopMargin() > f3 && !boomMenuButton.getButtonPlaceEnum().equals(ButtonPlaceEnum.Horizontal)) {
                    ((PointF) arrayList.get(arrayList.size() - 1)).offset(f3, boomMenuButton.getBottomHamButtonTopMargin() - buttonVerticalMargin);
                    break;
                }
        }
        float f114 = f12;
        Point point3 = point;
        adjust(arrayList, (float) (point3.x / 2), (float) (point3.y / 2));
        adjust(arrayList, point3, f114, f15, boomMenuButton);
        return arrayList;
    }

    /* JADX WARNING: Removed duplicated region for block: B:17:0x00cd  */
    /* JADX WARNING: Removed duplicated region for block: B:76:0x0828  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static java.util.ArrayList<android.graphics.PointF> getPositions(android.graphics.Point r27, float r28, int r29, com.nightonke.boommenu.BoomMenuButton r30) {
        /*
            r0 = r27
            r1 = r28
            r2 = r29
            java.util.ArrayList r3 = new java.util.ArrayList
            r3.<init>(r2)
            int r4 = r2 / 2
            float r5 = r30.getButtonHorizontalMargin()
            r6 = 1073741824(0x40000000, float:2.0)
            float r7 = r5 / r6
            r8 = 1069547520(0x3fc00000, float:1.5)
            float r9 = r5 * r8
            float r10 = r30.getButtonVerticalMargin()
            float r11 = r10 / r6
            float r8 = r8 * r10
            float r12 = r30.getButtonInclinedMargin()
            float r13 = r1 * r6
            r14 = 1077936128(0x40400000, float:3.0)
            float r15 = r1 * r14
            float r14 = r7 + r1
            r18 = r7
            double r6 = (double) r14
            r19 = 4613937818241073152(0x4008000000000000, double:3.0)
            double r21 = java.lang.Math.sqrt(r19)
            r23 = 4611686018427387904(0x4000000000000000, double:2.0)
            double r21 = r21 / r23
            java.lang.Double.isNaN(r6)
            double r6 = r6 / r21
            float r6 = (float) r6
            r7 = 1073741824(0x40000000, float:2.0)
            float r21 = r6 / r7
            float r7 = r6 - r21
            int[] r22 = com.nightonke.boommenu.BoomButtons.ButtonPlaceManager.C44941.$SwitchMap$com$nightonke$boommenu$BoomButtons$ButtonPlaceEnum
            com.nightonke.boommenu.BoomButtons.ButtonPlaceEnum r25 = r30.getButtonPlaceEnum()
            int r25 = r25.ordinal()
            r26 = r6
            r6 = r22[r25]
            r22 = r7
            r7 = 17
            if (r6 == r7) goto L_0x0077
            r7 = 21
            if (r6 == r7) goto L_0x0077
            r7 = 35
            if (r6 == r7) goto L_0x0079
            r7 = 38
            if (r6 == r7) goto L_0x0077
            r7 = 43
            if (r6 == r7) goto L_0x0077
            r19 = r4
            r7 = r11
            r11 = r14
            r6 = r21
        L_0x0070:
            r0 = r22
            r12 = r26
            r17 = 1073741824(0x40000000, float:2.0)
            goto L_0x00a9
        L_0x0077:
            r7 = r11
            goto L_0x0099
        L_0x0079:
            float r6 = r11 + r1
            r7 = r11
            double r11 = (double) r6
            double r19 = java.lang.Math.sqrt(r19)
            double r19 = r19 / r23
            java.lang.Double.isNaN(r11)
            double r11 = r11 / r19
            float r11 = (float) r11
            r12 = 1073741824(0x40000000, float:2.0)
            float r21 = r11 / r12
            float r12 = r11 - r21
            r19 = r4
            r0 = r12
            r17 = 1073741824(0x40000000, float:2.0)
            r12 = r11
            r11 = r6
            r6 = r21
            goto L_0x00a9
        L_0x0099:
            float r12 = r12 + r13
            double r11 = (double) r12
            double r19 = java.lang.Math.sqrt(r23)
            java.lang.Double.isNaN(r11)
            double r11 = r11 / r19
            float r6 = (float) r11
            r19 = r4
            r11 = r14
            goto L_0x0070
        L_0x00a9:
            float r4 = r6 * r17
            float r2 = r11 * r17
            r20 = r0
            r16 = 1077936128(0x40400000, float:3.0)
            float r0 = r11 * r16
            float r16 = r12 * r17
            int[] r17 = com.nightonke.boommenu.BoomButtons.ButtonPlaceManager.C44941.$SwitchMap$com$nightonke$boommenu$BoomButtons$ButtonPlaceEnum
            com.nightonke.boommenu.BoomButtons.ButtonPlaceEnum r21 = r30.getButtonPlaceEnum()
            int r21 = r21.ordinal()
            r22 = r11
            r11 = r17[r21]
            r17 = 0
            r21 = r0
            r0 = 1
            r24 = r9
            r9 = 0
            if (r11 == r0) goto L_0x0828
            r0 = 2
            if (r11 == r0) goto L_0x07c5
            switch(r11) {
                case 9: goto L_0x07ba;
                case 10: goto L_0x07a7;
                case 11: goto L_0x0794;
                case 12: goto L_0x077b;
                case 13: goto L_0x0762;
                case 14: goto L_0x0748;
                case 15: goto L_0x072d;
                case 16: goto L_0x0707;
                case 17: goto L_0x06e8;
                case 18: goto L_0x06bd;
                case 19: goto L_0x0692;
                case 20: goto L_0x0667;
                case 21: goto L_0x0641;
                case 22: goto L_0x060e;
                case 23: goto L_0x05db;
                case 24: goto L_0x05a8;
                case 25: goto L_0x0575;
                case 26: goto L_0x053d;
                case 27: goto L_0x0503;
                case 28: goto L_0x04ca;
                case 29: goto L_0x0491;
                case 30: goto L_0x0457;
                case 31: goto L_0x041d;
                case 32: goto L_0x03e2;
                case 33: goto L_0x03a7;
                case 34: goto L_0x0365;
                case 35: goto L_0x0323;
                case 36: goto L_0x02e3;
                case 37: goto L_0x029c;
                case 38: goto L_0x0260;
                case 39: goto L_0x0218;
                case 40: goto L_0x01d3;
                case 41: goto L_0x018c;
                case 42: goto L_0x013e;
                case 43: goto L_0x00fb;
                case 44: goto L_0x00db;
                default: goto L_0x00d3;
            }
        L_0x00d3:
            java.lang.RuntimeException r0 = new java.lang.RuntimeException
            java.lang.String r1 = "Button place enum not found!"
            r0.<init>(r1)
            throw r0
        L_0x00db:
            java.util.ArrayList r0 = r30.getCustomButtonPlacePositions()
            java.util.Iterator r0 = r0.iterator()
        L_0x00e3:
            boolean r2 = r0.hasNext()
            if (r2 == 0) goto L_0x07c1
            java.lang.Object r2 = r0.next()
            android.graphics.PointF r2 = (android.graphics.PointF) r2
            float r4 = r2.x
            float r2 = r2.y
            android.graphics.PointF r2 = point(r4, r2)
            r3.add(r2)
            goto L_0x00e3
        L_0x00fb:
            float r0 = -r4
            android.graphics.PointF r2 = point(r9, r0)
            r3.add(r2)
            float r2 = -r6
            android.graphics.PointF r5 = point(r2, r2)
            r3.add(r5)
            android.graphics.PointF r5 = point(r6, r2)
            r3.add(r5)
            android.graphics.PointF r0 = point(r0, r9)
            r3.add(r0)
            android.graphics.PointF r0 = point(r9, r9)
            r3.add(r0)
            android.graphics.PointF r0 = point(r4, r9)
            r3.add(r0)
            android.graphics.PointF r0 = point(r2, r6)
            r3.add(r0)
            android.graphics.PointF r0 = point(r6, r6)
            r3.add(r0)
            android.graphics.PointF r0 = point(r9, r4)
            r3.add(r0)
            goto L_0x07c1
        L_0x013e:
            float r0 = -r4
            float r0 = r0 - r16
            android.graphics.PointF r0 = point(r9, r0)
            r3.add(r0)
            r0 = r18
            float r0 = -r0
            float r0 = r0 - r1
            float r5 = -r6
            float r5 = r5 - r12
            android.graphics.PointF r7 = point(r0, r5)
            r3.add(r7)
            android.graphics.PointF r5 = point(r14, r5)
            r3.add(r5)
            float r5 = -r2
            android.graphics.PointF r5 = point(r5, r9)
            r3.add(r5)
            android.graphics.PointF r5 = point(r9, r9)
            r3.add(r5)
            android.graphics.PointF r2 = point(r2, r9)
            r3.add(r2)
            float r6 = r6 + r12
            android.graphics.PointF r0 = point(r0, r6)
            r3.add(r0)
            android.graphics.PointF r0 = point(r14, r6)
            r3.add(r0)
            float r4 = r4 + r16
            android.graphics.PointF r0 = point(r9, r4)
            r3.add(r0)
            goto L_0x07c1
        L_0x018c:
            float r0 = -r5
            float r0 = r0 - r13
            float r2 = -r10
            float r2 = r2 - r13
            android.graphics.PointF r4 = point(r0, r2)
            r3.add(r4)
            android.graphics.PointF r4 = point(r9, r2)
            r3.add(r4)
            float r5 = r5 + r13
            android.graphics.PointF r2 = point(r5, r2)
            r3.add(r2)
            android.graphics.PointF r2 = point(r0, r9)
            r3.add(r2)
            android.graphics.PointF r2 = point(r9, r9)
            r3.add(r2)
            android.graphics.PointF r2 = point(r5, r9)
            r3.add(r2)
            float r10 = r10 + r13
            android.graphics.PointF r0 = point(r0, r10)
            r3.add(r0)
            android.graphics.PointF r0 = point(r9, r10)
            r3.add(r0)
            android.graphics.PointF r0 = point(r5, r10)
            r3.add(r0)
            goto L_0x07c1
        L_0x01d3:
            r0 = r18
            float r0 = -r0
            float r0 = r0 - r1
            float r2 = -r8
            float r2 = r2 - r15
            android.graphics.PointF r4 = point(r0, r2)
            r3.add(r4)
            android.graphics.PointF r2 = point(r14, r2)
            r3.add(r2)
            float r2 = -r7
            float r2 = r2 - r1
            android.graphics.PointF r4 = point(r0, r2)
            r3.add(r4)
            android.graphics.PointF r2 = point(r14, r2)
            r3.add(r2)
            float r11 = r7 + r1
            android.graphics.PointF r2 = point(r0, r11)
            r3.add(r2)
            android.graphics.PointF r2 = point(r14, r11)
            r3.add(r2)
            float r8 = r8 + r15
            android.graphics.PointF r0 = point(r0, r8)
            r3.add(r0)
            android.graphics.PointF r0 = point(r14, r8)
            r3.add(r0)
            goto L_0x07c1
        L_0x0218:
            r0 = r18
            r5 = r24
            float r2 = -r5
            float r2 = r2 - r15
            float r4 = -r7
            float r4 = r4 - r1
            android.graphics.PointF r6 = point(r2, r4)
            r3.add(r6)
            float r0 = -r0
            float r0 = r0 - r1
            android.graphics.PointF r6 = point(r0, r4)
            r3.add(r6)
            android.graphics.PointF r6 = point(r14, r4)
            r3.add(r6)
            float r9 = r5 + r15
            android.graphics.PointF r4 = point(r9, r4)
            r3.add(r4)
            float r11 = r7 + r1
            android.graphics.PointF r2 = point(r2, r11)
            r3.add(r2)
            android.graphics.PointF r0 = point(r0, r11)
            r3.add(r0)
            android.graphics.PointF r0 = point(r14, r11)
            r3.add(r0)
            android.graphics.PointF r0 = point(r9, r11)
            r3.add(r0)
            goto L_0x07c1
        L_0x0260:
            float r0 = -r4
            android.graphics.PointF r2 = point(r9, r0)
            r3.add(r2)
            float r2 = -r6
            android.graphics.PointF r5 = point(r2, r2)
            r3.add(r5)
            android.graphics.PointF r5 = point(r6, r2)
            r3.add(r5)
            android.graphics.PointF r0 = point(r0, r9)
            r3.add(r0)
            android.graphics.PointF r0 = point(r4, r9)
            r3.add(r0)
            android.graphics.PointF r0 = point(r2, r6)
            r3.add(r0)
            android.graphics.PointF r0 = point(r6, r6)
            r3.add(r0)
            android.graphics.PointF r0 = point(r9, r4)
            r3.add(r0)
            goto L_0x07c1
        L_0x029c:
            r0 = r18
            float r5 = -r4
            float r5 = r5 - r16
            android.graphics.PointF r5 = point(r9, r5)
            r3.add(r5)
            float r0 = -r0
            float r0 = r0 - r1
            float r5 = -r6
            float r5 = r5 - r12
            android.graphics.PointF r7 = point(r0, r5)
            r3.add(r7)
            android.graphics.PointF r5 = point(r14, r5)
            r3.add(r5)
            float r5 = -r2
            android.graphics.PointF r5 = point(r5, r9)
            r3.add(r5)
            android.graphics.PointF r2 = point(r2, r9)
            r3.add(r2)
            float r6 = r6 + r12
            android.graphics.PointF r0 = point(r0, r6)
            r3.add(r0)
            android.graphics.PointF r0 = point(r14, r6)
            r3.add(r0)
            float r4 = r4 + r16
            android.graphics.PointF r0 = point(r9, r4)
            r3.add(r0)
            goto L_0x07c1
        L_0x02e3:
            float r0 = -r5
            float r0 = r0 - r13
            float r2 = -r10
            float r2 = r2 - r13
            android.graphics.PointF r4 = point(r0, r2)
            r3.add(r4)
            android.graphics.PointF r4 = point(r9, r2)
            r3.add(r4)
            float r5 = r5 + r13
            android.graphics.PointF r2 = point(r5, r2)
            r3.add(r2)
            android.graphics.PointF r2 = point(r0, r9)
            r3.add(r2)
            android.graphics.PointF r2 = point(r5, r9)
            r3.add(r2)
            float r10 = r10 + r13
            android.graphics.PointF r0 = point(r0, r10)
            r3.add(r0)
            android.graphics.PointF r0 = point(r9, r10)
            r3.add(r0)
            android.graphics.PointF r0 = point(r5, r10)
            r3.add(r0)
            goto L_0x07c1
        L_0x0323:
            float r0 = -r6
            float r0 = r0 - r12
            float r4 = -r2
            android.graphics.PointF r5 = point(r0, r4)
            r3.add(r5)
            float r6 = r6 + r12
            android.graphics.PointF r4 = point(r6, r4)
            r3.add(r4)
            float r4 = -r7
            float r4 = r4 - r1
            android.graphics.PointF r4 = point(r9, r4)
            r3.add(r4)
            android.graphics.PointF r4 = point(r0, r9)
            r3.add(r4)
            android.graphics.PointF r4 = point(r6, r9)
            r3.add(r4)
            float r11 = r7 + r1
            android.graphics.PointF r4 = point(r9, r11)
            r3.add(r4)
            android.graphics.PointF r0 = point(r0, r2)
            r3.add(r0)
            android.graphics.PointF r0 = point(r6, r2)
            r3.add(r0)
            goto L_0x07c1
        L_0x0365:
            r0 = r18
            float r4 = -r2
            float r5 = -r6
            float r5 = r5 - r12
            android.graphics.PointF r7 = point(r4, r5)
            r3.add(r7)
            android.graphics.PointF r7 = point(r9, r5)
            r3.add(r7)
            android.graphics.PointF r5 = point(r2, r5)
            r3.add(r5)
            float r0 = -r0
            float r0 = r0 - r1
            android.graphics.PointF r0 = point(r0, r9)
            r3.add(r0)
            android.graphics.PointF r0 = point(r14, r9)
            r3.add(r0)
            float r6 = r6 + r12
            android.graphics.PointF r0 = point(r4, r6)
            r3.add(r0)
            android.graphics.PointF r0 = point(r9, r6)
            r3.add(r0)
            android.graphics.PointF r0 = point(r2, r6)
            r3.add(r0)
            goto L_0x07c1
        L_0x03a7:
            float r0 = -r2
            float r4 = -r12
            android.graphics.PointF r0 = point(r0, r4)
            r3.add(r0)
            android.graphics.PointF r0 = point(r9, r4)
            r3.add(r0)
            android.graphics.PointF r0 = point(r2, r4)
            r3.add(r0)
            r11 = r21
            float r0 = -r11
            android.graphics.PointF r0 = point(r0, r6)
            r3.add(r0)
            r14 = r22
            float r0 = -r14
            android.graphics.PointF r0 = point(r0, r6)
            r3.add(r0)
            android.graphics.PointF r0 = point(r14, r6)
            r3.add(r0)
            android.graphics.PointF r0 = point(r11, r6)
            r3.add(r0)
            goto L_0x07c1
        L_0x03e2:
            r11 = r21
            r14 = r22
            float r0 = -r11
            float r4 = -r6
            android.graphics.PointF r0 = point(r0, r4)
            r3.add(r0)
            float r0 = -r14
            android.graphics.PointF r0 = point(r0, r4)
            r3.add(r0)
            android.graphics.PointF r0 = point(r14, r4)
            r3.add(r0)
            android.graphics.PointF r0 = point(r11, r4)
            r3.add(r0)
            float r0 = -r2
            android.graphics.PointF r0 = point(r0, r12)
            r3.add(r0)
            android.graphics.PointF r0 = point(r9, r12)
            r3.add(r0)
            android.graphics.PointF r0 = point(r2, r12)
            r3.add(r0)
            goto L_0x07c1
        L_0x041d:
            r14 = r22
            float r0 = -r2
            android.graphics.PointF r0 = point(r9, r0)
            r3.add(r0)
            float r0 = -r6
            float r0 = r0 - r12
            float r4 = -r14
            android.graphics.PointF r5 = point(r0, r4)
            r3.add(r5)
            float r6 = r6 + r12
            android.graphics.PointF r4 = point(r6, r4)
            r3.add(r4)
            android.graphics.PointF r4 = point(r9, r9)
            r3.add(r4)
            android.graphics.PointF r0 = point(r0, r14)
            r3.add(r0)
            android.graphics.PointF r0 = point(r6, r14)
            r3.add(r0)
            android.graphics.PointF r0 = point(r9, r2)
            r3.add(r0)
            goto L_0x07c1
        L_0x0457:
            r14 = r22
            float r0 = -r14
            float r4 = -r6
            float r4 = r4 - r12
            android.graphics.PointF r5 = point(r0, r4)
            r3.add(r5)
            android.graphics.PointF r4 = point(r14, r4)
            r3.add(r4)
            float r4 = -r2
            android.graphics.PointF r4 = point(r4, r9)
            r3.add(r4)
            android.graphics.PointF r4 = point(r9, r9)
            r3.add(r4)
            android.graphics.PointF r2 = point(r2, r9)
            r3.add(r2)
            float r6 = r6 + r12
            android.graphics.PointF r0 = point(r0, r6)
            r3.add(r0)
            android.graphics.PointF r0 = point(r14, r6)
            r3.add(r0)
            goto L_0x07c1
        L_0x0491:
            float r0 = -r10
            float r0 = r0 - r13
            android.graphics.PointF r0 = point(r9, r0)
            r3.add(r0)
            float r0 = -r5
            float r0 = r0 - r13
            android.graphics.PointF r2 = point(r0, r9)
            r3.add(r2)
            android.graphics.PointF r2 = point(r9, r9)
            r3.add(r2)
            float r5 = r5 + r13
            android.graphics.PointF r2 = point(r5, r9)
            r3.add(r2)
            float r10 = r10 + r13
            android.graphics.PointF r0 = point(r0, r10)
            r3.add(r0)
            android.graphics.PointF r0 = point(r9, r10)
            r3.add(r0)
            android.graphics.PointF r0 = point(r5, r10)
            r3.add(r0)
            goto L_0x07c1
        L_0x04ca:
            float r0 = -r5
            float r0 = r0 - r13
            float r2 = -r10
            float r2 = r2 - r13
            android.graphics.PointF r4 = point(r0, r2)
            r3.add(r4)
            android.graphics.PointF r4 = point(r9, r2)
            r3.add(r4)
            float r5 = r5 + r13
            android.graphics.PointF r2 = point(r5, r2)
            r3.add(r2)
            android.graphics.PointF r0 = point(r0, r9)
            r3.add(r0)
            android.graphics.PointF r0 = point(r9, r9)
            r3.add(r0)
            android.graphics.PointF r0 = point(r5, r9)
            r3.add(r0)
            float r10 = r10 + r13
            android.graphics.PointF r0 = point(r9, r10)
            r3.add(r0)
            goto L_0x07c1
        L_0x0503:
            r0 = r18
            float r4 = -r6
            float r4 = r4 - r12
            float r4 = r4 - r20
            android.graphics.PointF r4 = point(r9, r4)
            r3.add(r4)
            float r0 = -r0
            float r0 = r0 - r1
            r4 = r20
            float r5 = -r4
            android.graphics.PointF r0 = point(r0, r5)
            r3.add(r0)
            android.graphics.PointF r0 = point(r14, r5)
            r3.add(r0)
            float r0 = -r2
            float r6 = r6 + r12
            float r6 = r6 - r4
            android.graphics.PointF r0 = point(r0, r6)
            r3.add(r0)
            android.graphics.PointF r0 = point(r9, r6)
            r3.add(r0)
            android.graphics.PointF r0 = point(r2, r6)
            r3.add(r0)
            goto L_0x07c1
        L_0x053d:
            r0 = r18
            r4 = r20
            float r5 = -r2
            float r7 = -r6
            float r7 = r7 - r12
            float r7 = r7 + r4
            android.graphics.PointF r5 = point(r5, r7)
            r3.add(r5)
            android.graphics.PointF r5 = point(r9, r7)
            r3.add(r5)
            android.graphics.PointF r2 = point(r2, r7)
            r3.add(r2)
            float r0 = -r0
            float r0 = r0 - r1
            android.graphics.PointF r0 = point(r0, r4)
            r3.add(r0)
            android.graphics.PointF r0 = point(r14, r4)
            r3.add(r0)
            float r6 = r6 + r12
            float r6 = r6 + r4
            android.graphics.PointF r0 = point(r9, r6)
            r3.add(r0)
            goto L_0x07c1
        L_0x0575:
            r14 = r22
            float r0 = -r2
            android.graphics.PointF r0 = point(r9, r0)
            r3.add(r0)
            float r0 = -r6
            float r0 = r0 - r12
            float r4 = -r14
            android.graphics.PointF r5 = point(r0, r4)
            r3.add(r5)
            float r6 = r6 + r12
            android.graphics.PointF r4 = point(r6, r4)
            r3.add(r4)
            android.graphics.PointF r0 = point(r0, r14)
            r3.add(r0)
            android.graphics.PointF r0 = point(r6, r14)
            r3.add(r0)
            android.graphics.PointF r0 = point(r9, r2)
            r3.add(r0)
            goto L_0x07c1
        L_0x05a8:
            r14 = r22
            float r0 = -r14
            float r4 = -r6
            float r4 = r4 - r12
            android.graphics.PointF r5 = point(r0, r4)
            r3.add(r5)
            android.graphics.PointF r4 = point(r14, r4)
            r3.add(r4)
            float r4 = -r2
            android.graphics.PointF r4 = point(r4, r9)
            r3.add(r4)
            android.graphics.PointF r2 = point(r2, r9)
            r3.add(r2)
            float r6 = r6 + r12
            android.graphics.PointF r0 = point(r0, r6)
            r3.add(r0)
            android.graphics.PointF r0 = point(r14, r6)
            r3.add(r0)
            goto L_0x07c1
        L_0x05db:
            r0 = r18
            float r0 = -r0
            float r0 = r0 - r1
            float r2 = -r10
            float r2 = r2 - r13
            android.graphics.PointF r4 = point(r0, r2)
            r3.add(r4)
            android.graphics.PointF r2 = point(r14, r2)
            r3.add(r2)
            android.graphics.PointF r2 = point(r0, r9)
            r3.add(r2)
            android.graphics.PointF r2 = point(r14, r9)
            r3.add(r2)
            float r10 = r10 + r13
            android.graphics.PointF r0 = point(r0, r10)
            r3.add(r0)
            android.graphics.PointF r0 = point(r14, r10)
            r3.add(r0)
            goto L_0x07c1
        L_0x060e:
            float r0 = -r5
            float r0 = r0 - r13
            float r2 = -r7
            float r2 = r2 - r1
            android.graphics.PointF r4 = point(r0, r2)
            r3.add(r4)
            android.graphics.PointF r4 = point(r9, r2)
            r3.add(r4)
            float r5 = r5 + r13
            android.graphics.PointF r2 = point(r5, r2)
            r3.add(r2)
            float r11 = r7 + r1
            android.graphics.PointF r0 = point(r0, r11)
            r3.add(r0)
            android.graphics.PointF r0 = point(r9, r11)
            r3.add(r0)
            android.graphics.PointF r0 = point(r5, r11)
            r3.add(r0)
            goto L_0x07c1
        L_0x0641:
            float r0 = -r6
            android.graphics.PointF r2 = point(r0, r0)
            r3.add(r2)
            android.graphics.PointF r2 = point(r6, r0)
            r3.add(r2)
            android.graphics.PointF r2 = point(r9, r9)
            r3.add(r2)
            android.graphics.PointF r0 = point(r0, r6)
            r3.add(r0)
            android.graphics.PointF r0 = point(r6, r6)
            r3.add(r0)
            goto L_0x07c1
        L_0x0667:
            float r0 = -r10
            float r0 = r0 - r13
            android.graphics.PointF r0 = point(r9, r0)
            r3.add(r0)
            float r0 = -r5
            float r0 = r0 - r13
            android.graphics.PointF r0 = point(r0, r9)
            r3.add(r0)
            android.graphics.PointF r0 = point(r9, r9)
            r3.add(r0)
            float r5 = r5 + r13
            android.graphics.PointF r0 = point(r5, r9)
            r3.add(r0)
            float r10 = r10 + r13
            android.graphics.PointF r0 = point(r9, r10)
            r3.add(r0)
            goto L_0x07c1
        L_0x0692:
            r0 = r18
            float r0 = -r0
            float r0 = r0 - r1
            float r4 = -r6
            android.graphics.PointF r0 = point(r0, r4)
            r3.add(r0)
            android.graphics.PointF r0 = point(r14, r4)
            r3.add(r0)
            float r0 = -r2
            android.graphics.PointF r0 = point(r0, r12)
            r3.add(r0)
            android.graphics.PointF r0 = point(r9, r12)
            r3.add(r0)
            android.graphics.PointF r0 = point(r2, r12)
            r3.add(r0)
            goto L_0x07c1
        L_0x06bd:
            r0 = r18
            float r4 = -r2
            float r5 = -r12
            android.graphics.PointF r4 = point(r4, r5)
            r3.add(r4)
            android.graphics.PointF r4 = point(r9, r5)
            r3.add(r4)
            android.graphics.PointF r2 = point(r2, r5)
            r3.add(r2)
            float r0 = -r0
            float r0 = r0 - r1
            android.graphics.PointF r0 = point(r0, r6)
            r3.add(r0)
            android.graphics.PointF r0 = point(r14, r6)
            r3.add(r0)
            goto L_0x07c1
        L_0x06e8:
            float r0 = -r6
            android.graphics.PointF r2 = point(r9, r0)
            r3.add(r2)
            android.graphics.PointF r0 = point(r0, r9)
            r3.add(r0)
            android.graphics.PointF r0 = point(r6, r9)
            r3.add(r0)
            android.graphics.PointF r0 = point(r9, r6)
            r3.add(r0)
            goto L_0x07c1
        L_0x0707:
            r0 = r18
            float r0 = -r0
            float r0 = r0 - r1
            float r2 = -r7
            float r2 = r2 - r1
            android.graphics.PointF r4 = point(r0, r2)
            r3.add(r4)
            android.graphics.PointF r2 = point(r14, r2)
            r3.add(r2)
            float r11 = r7 + r1
            android.graphics.PointF r0 = point(r0, r11)
            r3.add(r0)
            android.graphics.PointF r0 = point(r14, r11)
            r3.add(r0)
            goto L_0x07c1
        L_0x072d:
            r14 = r22
            float r0 = -r12
            android.graphics.PointF r0 = point(r9, r0)
            r3.add(r0)
            float r0 = -r14
            android.graphics.PointF r0 = point(r0, r6)
            r3.add(r0)
            android.graphics.PointF r0 = point(r14, r6)
            r3.add(r0)
            goto L_0x07c1
        L_0x0748:
            r14 = r22
            float r0 = -r14
            float r2 = -r6
            android.graphics.PointF r0 = point(r0, r2)
            r3.add(r0)
            android.graphics.PointF r0 = point(r14, r2)
            r3.add(r0)
            android.graphics.PointF r0 = point(r9, r12)
            r3.add(r0)
            goto L_0x07c1
        L_0x0762:
            float r0 = -r10
            float r0 = r0 - r13
            android.graphics.PointF r0 = point(r9, r0)
            r3.add(r0)
            android.graphics.PointF r0 = point(r9, r9)
            r3.add(r0)
            float r10 = r10 + r13
            android.graphics.PointF r0 = point(r9, r10)
            r3.add(r0)
            goto L_0x07c1
        L_0x077b:
            float r0 = -r5
            float r0 = r0 - r13
            android.graphics.PointF r0 = point(r0, r9)
            r3.add(r0)
            android.graphics.PointF r0 = point(r9, r9)
            r3.add(r0)
            float r5 = r5 + r13
            android.graphics.PointF r0 = point(r5, r9)
            r3.add(r0)
            goto L_0x07c1
        L_0x0794:
            float r0 = -r7
            float r0 = r0 - r1
            android.graphics.PointF r0 = point(r9, r0)
            r3.add(r0)
            float r11 = r7 + r1
            android.graphics.PointF r0 = point(r9, r11)
            r3.add(r0)
            goto L_0x07c1
        L_0x07a7:
            r0 = r18
            float r0 = -r0
            float r0 = r0 - r1
            android.graphics.PointF r0 = point(r0, r9)
            r3.add(r0)
            android.graphics.PointF r0 = point(r14, r9)
            r3.add(r0)
            goto L_0x07c1
        L_0x07ba:
            android.graphics.PointF r0 = point(r9, r9)
            r3.add(r0)
        L_0x07c1:
            r0 = r27
            goto L_0x088a
        L_0x07c5:
            int r2 = r29 % 2
            if (r2 != 0) goto L_0x07f6
            int r4 = r19 + -1
        L_0x07cb:
            if (r4 < 0) goto L_0x07df
            float r0 = -r1
            float r0 = r0 - r7
            float r2 = (float) r4
            float r5 = r13 + r10
            float r2 = r2 * r5
            float r0 = r0 - r2
            android.graphics.PointF r0 = point(r9, r0)
            r3.add(r0)
            int r4 = r4 + -1
            goto L_0x07cb
        L_0x07df:
            r2 = r19
            r0 = 0
        L_0x07e2:
            if (r0 >= r2) goto L_0x07c1
            float r4 = r1 + r7
            float r5 = (float) r0
            float r6 = r13 + r10
            float r5 = r5 * r6
            float r4 = r4 + r5
            android.graphics.PointF r4 = point(r9, r4)
            r3.add(r4)
            int r0 = r0 + 1
            goto L_0x07e2
        L_0x07f6:
            r2 = r19
            int r4 = r2 + -1
        L_0x07fa:
            if (r4 < 0) goto L_0x080e
            float r0 = -r13
            float r0 = r0 - r10
            float r5 = (float) r4
            float r6 = r13 + r10
            float r5 = r5 * r6
            float r0 = r0 - r5
            android.graphics.PointF r0 = point(r9, r0)
            r3.add(r0)
            int r4 = r4 + -1
            goto L_0x07fa
        L_0x080e:
            android.graphics.PointF r0 = point(r9, r9)
            r3.add(r0)
            r0 = 0
        L_0x0816:
            if (r0 >= r2) goto L_0x07c1
            float r4 = r13 + r10
            float r5 = (float) r0
            float r5 = r5 * r4
            float r4 = r4 + r5
            android.graphics.PointF r4 = point(r9, r4)
            r3.add(r4)
            int r0 = r0 + 1
            goto L_0x0816
        L_0x0828:
            r0 = r18
            r2 = r19
            r4 = 2
            int r6 = r29 % 2
            if (r6 != 0) goto L_0x085a
            int r4 = r2 + -1
        L_0x0833:
            if (r4 < 0) goto L_0x0847
            float r6 = -r1
            float r6 = r6 - r0
            float r7 = (float) r4
            float r8 = r13 + r5
            float r7 = r7 * r8
            float r6 = r6 - r7
            android.graphics.PointF r6 = point(r6, r9)
            r3.add(r6)
            int r4 = r4 + -1
            goto L_0x0833
        L_0x0847:
            r0 = 0
        L_0x0848:
            if (r0 >= r2) goto L_0x07c1
            float r4 = (float) r0
            float r6 = r13 + r5
            float r4 = r4 * r6
            float r4 = r4 + r14
            android.graphics.PointF r4 = point(r4, r9)
            r3.add(r4)
            int r0 = r0 + 1
            goto L_0x0848
        L_0x085a:
            int r4 = r2 + -1
        L_0x085c:
            if (r4 < 0) goto L_0x0870
            float r0 = -r13
            float r0 = r0 - r5
            float r6 = (float) r4
            float r7 = r13 + r5
            float r6 = r6 * r7
            float r0 = r0 - r6
            android.graphics.PointF r0 = point(r0, r9)
            r3.add(r0)
            int r4 = r4 + -1
            goto L_0x085c
        L_0x0870:
            android.graphics.PointF r0 = point(r9, r9)
            r3.add(r0)
            r0 = 0
        L_0x0878:
            if (r0 >= r2) goto L_0x07c1
            float r4 = r13 + r5
            float r6 = (float) r0
            float r6 = r6 * r4
            float r4 = r4 + r6
            android.graphics.PointF r4 = point(r4, r9)
            r3.add(r4)
            int r0 = r0 + 1
            goto L_0x0878
        L_0x088a:
            int r2 = r0.x
            r4 = 2
            int r2 = r2 / r4
            float r2 = (float) r2
            int r5 = r0.y
            int r5 = r5 / r4
            float r4 = (float) r5
            adjust(r3, r2, r4)
            r2 = r30
            adjust(r3, r0, r1, r1, r2)
            return r3
        */
        throw new UnsupportedOperationException("Method not decompiled: com.nightonke.boommenu.BoomButtons.ButtonPlaceManager.getPositions(android.graphics.Point, float, int, com.nightonke.boommenu.BoomMenuButton):java.util.ArrayList");
    }

    /* JADX WARNING: Code restructure failed: missing block: B:11:0x008d, code lost:
        r8 = r8 - r9;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:13:0x0093, code lost:
        r8 = (r9 + r8) - r2;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:16:0x00a9, code lost:
        r3 = r8;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:18:0x00ab, code lost:
        adjust(r7, r8, r3);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:19:0x00ae, code lost:
        return;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private static void adjust(java.util.ArrayList<android.graphics.PointF> r7, android.graphics.Point r8, float r9, float r10, com.nightonke.boommenu.BoomMenuButton r11) {
        /*
            java.util.Iterator r0 = r7.iterator()
            r1 = 1
            r2 = 2139095039(0x7f7fffff, float:3.4028235E38)
            r3 = 1
            r4 = 2139095039(0x7f7fffff, float:3.4028235E38)
        L_0x000c:
            boolean r5 = r0.hasNext()
            if (r5 == 0) goto L_0x0031
            java.lang.Object r5 = r0.next()
            android.graphics.PointF r5 = (android.graphics.PointF) r5
            float r6 = r5.y
            float r1 = java.lang.Math.max(r1, r6)
            float r6 = r5.y
            float r2 = java.lang.Math.min(r2, r6)
            float r6 = r5.x
            float r3 = java.lang.Math.max(r3, r6)
            float r5 = r5.x
            float r4 = java.lang.Math.min(r4, r5)
            goto L_0x000c
        L_0x0031:
            int[] r0 = com.nightonke.boommenu.BoomButtons.ButtonPlaceManager.C44941.f2243xaf1aa661
            com.nightonke.boommenu.BoomButtons.ButtonPlaceAlignmentEnum r3 = r11.getButtonPlaceAlignmentEnum()
            int r3 = r3.ordinal()
            r0 = r0[r3]
            r3 = 0
            switch(r0) {
                case 1: goto L_0x00aa;
                case 2: goto L_0x00a2;
                case 3: goto L_0x0097;
                case 4: goto L_0x008f;
                case 5: goto L_0x0084;
                case 6: goto L_0x0078;
                case 7: goto L_0x0067;
                case 8: goto L_0x0057;
                case 9: goto L_0x0042;
                default: goto L_0x0041;
            }
        L_0x0041:
            goto L_0x00aa
        L_0x0042:
            int r9 = r8.y
            float r9 = (float) r9
            float r9 = r9 - r10
            float r9 = r9 - r1
            float r0 = r11.getButtonBottomMargin()
            float r3 = r9 - r0
            int r8 = r8.y
            float r8 = (float) r8
            float r8 = r8 - r10
            float r8 = r8 - r1
            float r9 = r11.getButtonRightMargin()
            goto L_0x008d
        L_0x0057:
            int r8 = r8.y
            float r8 = (float) r8
            float r8 = r8 - r10
            float r8 = r8 - r1
            float r10 = r11.getButtonBottomMargin()
            float r3 = r8 - r10
            float r8 = r11.getButtonLeftMargin()
            goto L_0x0093
        L_0x0067:
            float r9 = r11.getButtonTopMargin()
            float r9 = r9 + r10
            float r3 = r9 - r2
            int r8 = r8.y
            float r8 = (float) r8
            float r8 = r8 - r10
            float r8 = r8 - r1
            float r9 = r11.getButtonRightMargin()
            goto L_0x008d
        L_0x0078:
            float r8 = r11.getButtonTopMargin()
            float r10 = r10 + r8
            float r3 = r10 - r2
            float r8 = r11.getButtonLeftMargin()
            goto L_0x0093
        L_0x0084:
            int r8 = r8.y
            float r8 = (float) r8
            float r8 = r8 - r10
            float r8 = r8 - r1
            float r9 = r11.getButtonRightMargin()
        L_0x008d:
            float r8 = r8 - r9
            goto L_0x00ab
        L_0x008f:
            float r8 = r11.getButtonLeftMargin()
        L_0x0093:
            float r9 = r9 + r8
            float r8 = r9 - r2
            goto L_0x00ab
        L_0x0097:
            int r8 = r8.y
            float r8 = (float) r8
            float r8 = r8 - r10
            float r8 = r8 - r1
            float r9 = r11.getButtonBottomMargin()
            float r8 = r8 - r9
            goto L_0x00a9
        L_0x00a2:
            float r8 = r11.getButtonTopMargin()
            float r10 = r10 + r8
            float r8 = r10 - r2
        L_0x00a9:
            r3 = r8
        L_0x00aa:
            r8 = 0
        L_0x00ab:
            adjust(r7, r8, r3)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.nightonke.boommenu.BoomButtons.ButtonPlaceManager.adjust(java.util.ArrayList, android.graphics.Point, float, float, com.nightonke.boommenu.BoomMenuButton):void");
    }

    private static void adjust(ArrayList<PointF> arrayList, float f, float f2) {
        for (int i = 0; i < arrayList.size(); i++) {
            ((PointF) arrayList.get(i)).offset(f, f2);
        }
    }

    private static PointF point(float f, float f2) {
        return new PointF(f, f2);
    }
}
