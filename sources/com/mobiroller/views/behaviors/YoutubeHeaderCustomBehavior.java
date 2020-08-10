package com.mobiroller.views.behaviors;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.coordinatorlayout.widget.CoordinatorLayout.Behavior;
import androidx.coordinatorlayout.widget.CoordinatorLayout.LayoutParams;
import com.google.android.material.appbar.AppBarLayout;
import com.mobiroller.preview.C4290R;

public class YoutubeHeaderCustomBehavior extends Behavior<View> {
    private static final int HEIGHT = 3;
    private static final int WIDTH = 2;

    /* renamed from: X */
    private static final int f2231X = 0;

    /* renamed from: Y */
    private static final int f2232Y = 1;
    private int[] mTarget;
    private int mTargetId;
    private int[] mView;

    public YoutubeHeaderCustomBehavior() {
    }

    public YoutubeHeaderCustomBehavior(Context context, AttributeSet attributeSet) {
        if (attributeSet != null) {
            TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, C4290R.styleable.CollapsingImageBehavior);
            this.mTargetId = obtainStyledAttributes.getResourceId(0, 0);
            obtainStyledAttributes.recycle();
        }
        if (this.mTargetId == 0) {
            throw new IllegalStateException("collapsedTarget attribute not specified on view for behavior");
        }
    }

    public boolean layoutDependsOn(CoordinatorLayout coordinatorLayout, View view, View view2) {
        return view2 instanceof AppBarLayout;
    }

    public boolean onDependentViewChanged(CoordinatorLayout coordinatorLayout, View view, View view2) {
        setup(coordinatorLayout, view);
        AppBarLayout appBarLayout = (AppBarLayout) view2;
        float totalScrollRange = (-appBarLayout.getY()) / ((float) appBarLayout.getTotalScrollRange());
        int[] iArr = this.mView;
        int i = iArr[0];
        int[] iArr2 = this.mTarget;
        int i2 = i + ((int) (((float) (iArr2[0] - iArr[0])) * totalScrollRange));
        int i3 = iArr[1] + ((int) (((float) (iArr2[1] - iArr[1])) * totalScrollRange));
        int i4 = iArr[2] + ((int) (((float) (iArr2[2] - iArr[2])) * totalScrollRange));
        int i5 = iArr[3] + ((int) (totalScrollRange * ((float) (iArr2[3] - iArr[3]))));
        LayoutParams layoutParams = (LayoutParams) view.getLayoutParams();
        layoutParams.width = i4;
        layoutParams.height = i5;
        view.setLayoutParams(layoutParams);
        view.setX((float) i2);
        view.setY((float) i3);
        return true;
    }

    private void setup(CoordinatorLayout coordinatorLayout, View view) {
        if (this.mView == null) {
            this.mView = new int[4];
            this.mTarget = new int[4];
            this.mView[0] = (int) view.getX();
            this.mView[1] = (int) view.getY();
            this.mView[2] = view.getWidth();
            this.mView[3] = view.getHeight();
            View findViewById = coordinatorLayout.findViewById(this.mTargetId);
            if (findViewById != null) {
                int[] iArr = this.mTarget;
                iArr[2] = iArr[2] + findViewById.getWidth();
                int[] iArr2 = this.mTarget;
                iArr2[3] = iArr2[3] + findViewById.getHeight();
                while (findViewById != coordinatorLayout) {
                    int[] iArr3 = this.mTarget;
                    iArr3[0] = iArr3[0] + ((int) findViewById.getX());
                    int[] iArr4 = this.mTarget;
                    iArr4[1] = iArr4[1] + ((int) findViewById.getY());
                    findViewById = (View) findViewById.getParent();
                }
                return;
            }
            throw new IllegalStateException("target view not found");
        }
    }
}
