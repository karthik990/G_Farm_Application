package com.mobiroller.views;

import android.animation.TimeInterpolator;
import android.app.Activity;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.Point;
import android.graphics.Rect;
import android.os.Bundle;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.FrameLayout.LayoutParams;
import android.widget.ImageView;
import androidx.core.content.ContextCompat;
import com.bumptech.glide.Glide;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.remoteconfig.FirebaseRemoteConfig;
import com.mobiroller.DynamicConstants;
import com.mobiroller.activities.MobiRollerBadgeActivity;
import com.mobiroller.helpers.JSONStorage;
import com.mobiroller.helpers.UtilManager;
import com.mobiroller.mobi942763453128.R;
import com.mobiroller.util.ScreenUtil;
import com.nightonke.boommenu.Animation.AnimationManager;
import com.nightonke.boommenu.Animation.Ease;
import com.nightonke.boommenu.Animation.EaseEnum;
import com.nightonke.boommenu.C4514R;
import com.nightonke.boommenu.Util;

public class MobiRollerBadgeView {
    private static final String MOBIROLLER_BADGE_BUTTON_TAG = "mobiRollerBadgeView";
    /* access modifiers changed from: private */
    public static boolean ableToStartDraggingBadge = false;
    private static Rect edgeInsetsInParentViewBadge = null;
    /* access modifiers changed from: private */
    public static boolean isDraggingBadge = false;
    /* access modifiers changed from: private */
    public static float lastMotionXBadge = -1.0f;
    /* access modifiers changed from: private */
    public static float lastMotionYBadge = -1.0f;
    /* access modifiers changed from: private */
    public static float startPositionXBadge;
    /* access modifiers changed from: private */
    public static float startPositionYBadge;

    public static void addView(final Activity activity) {
        if (!DynamicConstants.MobiRoller_Stage) {
            Bundle bundle = new Bundle();
            bundle.putString("app_name", activity.getString(R.string.app_name));
            bundle.putString("package", activity.getPackageName());
            FirebaseAnalytics.getInstance(activity).logEvent("badge_view", bundle);
            FrameLayout frameLayout = (FrameLayout) activity.getWindow().getDecorView().getRootView();
            View inflate = View.inflate(activity, R.layout.mobiroller_badge_layout, null);
            final FloatingActionButton floatingActionButton = (FloatingActionButton) inflate.findViewById(R.id.fab_button_badge);
            if (!(JSONStorage.getMobirollerBadgeModel() == null || JSONStorage.getMobirollerBadgeModel().design == null || JSONStorage.getMobirollerBadgeModel().design.iconUrl == null)) {
                Glide.with(inflate).load(JSONStorage.getMobirollerBadgeModel().design.iconUrl).into((ImageView) floatingActionButton);
            }
            floatingActionButton.setRippleColor(0);
            floatingActionButton.setBackgroundTintList(ColorStateList.valueOf(0));
            floatingActionButton.setBackgroundDrawable(ContextCompat.getDrawable(activity, R.drawable.mobiroller_badge));
            floatingActionButton.setImageDrawable(ContextCompat.getDrawable(activity, R.drawable.mobiroller_badge));
            floatingActionButton.setColorFilter(null);
            floatingActionButton.setSupportImageTintList(null);
            floatingActionButton.setLayoutParams(new LayoutParams(-2, -2));
            floatingActionButton.setOnTouchListener(new OnTouchListener() {
                public boolean onTouch(View view, MotionEvent motionEvent) {
                    int actionMasked = motionEvent.getActionMasked();
                    if (actionMasked == 0) {
                        MobiRollerBadgeView.startPositionXBadge = floatingActionButton.getX() - motionEvent.getRawX();
                        MobiRollerBadgeView.startPositionYBadge = floatingActionButton.getY() - motionEvent.getRawY();
                        MobiRollerBadgeView.lastMotionXBadge = motionEvent.getRawX();
                        MobiRollerBadgeView.lastMotionYBadge = motionEvent.getRawY();
                        UtilManager.sharedPrefHelper().setMobiRollerBadgeY(floatingActionButton.getY(), activity);
                    } else if (actionMasked != 1) {
                        if (actionMasked == 2) {
                            if (Math.abs(MobiRollerBadgeView.lastMotionXBadge - motionEvent.getRawX()) > 10.0f || Math.abs(MobiRollerBadgeView.lastMotionYBadge - motionEvent.getRawY()) > 10.0f) {
                                MobiRollerBadgeView.ableToStartDraggingBadge = true;
                            }
                            if (MobiRollerBadgeView.ableToStartDraggingBadge) {
                                MobiRollerBadgeView.isDraggingBadge = true;
                                floatingActionButton.setX(motionEvent.getRawX() + MobiRollerBadgeView.startPositionXBadge);
                                floatingActionButton.setY(motionEvent.getRawY() + MobiRollerBadgeView.startPositionYBadge);
                            } else {
                                MobiRollerBadgeView.ableToStartDraggingBadge = false;
                            }
                            UtilManager.sharedPrefHelper().setMobiRollerBadgeY(floatingActionButton.getY(), activity);
                        } else if (actionMasked == 3 && MobiRollerBadgeView.isDraggingBadge) {
                            MobiRollerBadgeView.ableToStartDraggingBadge = false;
                            MobiRollerBadgeView.isDraggingBadge = false;
                            floatingActionButton.requestLayout();
                            UtilManager.sharedPrefHelper().setMobiRollerBadgeY(floatingActionButton.getY(), activity);
                            MobiRollerBadgeView.preventDragOutside(floatingActionButton, activity);
                            return true;
                        }
                    } else if (MobiRollerBadgeView.isDraggingBadge) {
                        MobiRollerBadgeView.ableToStartDraggingBadge = false;
                        MobiRollerBadgeView.isDraggingBadge = false;
                        floatingActionButton.requestLayout();
                        UtilManager.sharedPrefHelper().setMobiRollerBadgeY(floatingActionButton.getY(), activity);
                        MobiRollerBadgeView.preventDragOutside(floatingActionButton, activity);
                        return true;
                    }
                    return false;
                }
            });
            floatingActionButton.setOnClickListener(new OnClickListener() {
                public void onClick(View view) {
                    Bundle bundle = new Bundle();
                    bundle.putString("app_name", activity.getString(R.string.app_name));
                    bundle.putString("package", activity.getPackageName());
                    FirebaseAnalytics.getInstance(activity).logEvent("badge_click", bundle);
                    Activity activity = activity;
                    activity.startActivity(new Intent(activity, MobiRollerBadgeActivity.class));
                }
            });
            View findViewById = frameLayout.findViewById(R.id.fab_button_badge);
            if (findViewById != null) {
                frameLayout.removeView(findViewById);
            }
            frameLayout.addView(floatingActionButton);
            floatingActionButton.setY((float) UtilManager.sharedPrefHelper().getMobiRollerBadgeY(activity));
            preventDragOutside(floatingActionButton, activity);
        }
    }

    /* access modifiers changed from: private */
    public static void preventDragOutside(View view, Activity activity) {
        boolean z;
        boolean z2;
        boolean z3;
        Activity activity2 = activity;
        if (edgeInsetsInParentViewBadge == null) {
            TypedArray obtainStyledAttributes = activity2.obtainStyledAttributes(null, C4514R.styleable.BoomMenuButton, 0, 0);
            edgeInsetsInParentViewBadge = new Rect(0, 0, 0, 0);
            edgeInsetsInParentViewBadge.left = Util.getDimenOffset(obtainStyledAttributes, 25, R.dimen.default_bmb_edgeInsetsLeft);
            edgeInsetsInParentViewBadge.top = Util.getDimenOffset(obtainStyledAttributes, 27, R.dimen.default_bmb_edgeInsetsTop);
            edgeInsetsInParentViewBadge.right = Util.getDimenOffset(obtainStyledAttributes, 26, R.dimen.default_bmb_edgeInsetsRight);
            edgeInsetsInParentViewBadge.bottom = Util.getDimenOffset(obtainStyledAttributes, 24, R.dimen.default_bmb_edgeInsetsBottom);
        }
        float x = view.getX();
        float y = view.getY();
        ViewGroup viewGroup = (ViewGroup) view.getParent();
        int screenWidth = ScreenUtil.getScreenWidth();
        int screenHeight = ScreenUtil.getScreenHeight();
        Display defaultDisplay = activity.getWindowManager().getDefaultDisplay();
        Point point = new Point();
        defaultDisplay.getSize(point);
        int i = point.x;
        int i2 = point.y;
        if (((double) x) == FirebaseRemoteConfig.DEFAULT_VALUE_FOR_DOUBLE) {
            x = ((float) i) - (activity.getResources().getDimension(R.dimen.default_bmb_text_inside_circle_width) / 2.0f);
            z = true;
        } else {
            z = false;
        }
        if (((double) y) == FirebaseRemoteConfig.DEFAULT_VALUE_FOR_DOUBLE) {
            y = (float) (i2 / 2);
            z2 = true;
            z = true;
        } else {
            z2 = false;
        }
        float f = (float) i;
        if (x < f - (activity.getResources().getDimension(R.dimen.default_bmb_text_inside_circle_width) / 2.0f) || x > f - (activity.getResources().getDimension(R.dimen.default_bmb_text_inside_circle_width) / 2.0f)) {
            x = f - (activity.getResources().getDimension(R.dimen.default_bmb_text_inside_circle_width) / 2.0f);
            z3 = true;
        } else {
            z3 = z;
        }
        if (y < ((float) edgeInsetsInParentViewBadge.top)) {
            y = (float) edgeInsetsInParentViewBadge.top;
            z2 = true;
            z3 = true;
        }
        if (((float) (screenWidth - edgeInsetsInParentViewBadge.right)) - activity.getResources().getDimension(R.dimen.default_bmb_text_inside_circle_width) >= 0.0f && x > ((float) (screenWidth - edgeInsetsInParentViewBadge.right)) - activity.getResources().getDimension(R.dimen.default_bmb_text_inside_circle_width)) {
            x = f - (activity.getResources().getDimension(R.dimen.default_bmb_text_inside_circle_width) / 2.0f);
            z3 = true;
        }
        if (((float) (screenHeight - edgeInsetsInParentViewBadge.bottom)) - activity.getResources().getDimension(R.dimen.default_bmb_text_inside_circle_height) >= 0.0f && y > ((float) (screenHeight - edgeInsetsInParentViewBadge.bottom)) - activity.getResources().getDimension(R.dimen.default_bmb_text_inside_circle_height)) {
            y = ((float) (screenHeight - edgeInsetsInParentViewBadge.bottom)) - activity.getResources().getDimension(R.dimen.default_bmb_text_inside_circle_height);
            z2 = true;
            z3 = true;
        }
        if (z3) {
            if (z2) {
                UtilManager.sharedPrefHelper().setMobiRollerBadgeY(y, activity2);
            }
            View view2 = view;
            AnimationManager.animate((Object) view2, "x", 0, 300, (TimeInterpolator) Ease.getInstance(EaseEnum.EaseOutBack), view.getX(), x);
            View view3 = view;
            AnimationManager.animate((Object) view3, "y", 0, 300, (TimeInterpolator) Ease.getInstance(EaseEnum.EaseOutBack), view.getY(), y);
        }
        viewGroup.requestLayout();
    }

    public static void removeView(Activity activity) {
        FrameLayout frameLayout = (FrameLayout) activity.getWindow().getDecorView().getRootView();
        View findViewById = frameLayout.findViewById(R.id.fab_button_badge);
        if (findViewById != null) {
            frameLayout.removeView(findViewById);
        }
    }
}
