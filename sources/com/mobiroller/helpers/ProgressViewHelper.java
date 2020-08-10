package com.mobiroller.helpers;

import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentActivity;
import com.github.ybq.android.spinkit.sprite.Sprite;
import com.github.ybq.android.spinkit.style.ChasingDots;
import com.github.ybq.android.spinkit.style.Circle;
import com.github.ybq.android.spinkit.style.CubeGrid;
import com.github.ybq.android.spinkit.style.DoubleBounce;
import com.github.ybq.android.spinkit.style.FadingCircle;
import com.github.ybq.android.spinkit.style.FoldingCube;
import com.github.ybq.android.spinkit.style.Pulse;
import com.github.ybq.android.spinkit.style.RotatingCircle;
import com.github.ybq.android.spinkit.style.RotatingPlane;
import com.github.ybq.android.spinkit.style.ThreeBounce;
import com.github.ybq.android.spinkit.style.WanderingCubes;
import com.github.ybq.android.spinkit.style.Wave;
import org.objectweb.asm.Opcodes;

public class ProgressViewHelper {
    private static SharedPrefHelper sharedPrefHelper;
    private ProgressBar progressBar;

    /* renamed from: rl */
    private RelativeLayout f2165rl;

    public void setCancelableOnCancel() {
    }

    public ProgressViewHelper(AppCompatActivity appCompatActivity) {
        sharedPrefHelper = UtilManager.sharedPrefHelper();
        setProgressBar(appCompatActivity);
    }

    public ProgressViewHelper(FragmentActivity fragmentActivity) {
        sharedPrefHelper = UtilManager.sharedPrefHelper();
        setProgressBar(fragmentActivity);
    }

    public ProgressViewHelper(Activity activity) {
        sharedPrefHelper = UtilManager.sharedPrefHelper();
        setProgressBar(activity);
    }

    public void show() {
        this.f2165rl.setVisibility(0);
        this.progressBar.setVisibility(0);
    }

    public void dismiss() {
        this.f2165rl.setVisibility(8);
        this.progressBar.setVisibility(8);
    }

    public void cancel() {
        this.f2165rl.setVisibility(8);
        this.progressBar.setVisibility(8);
    }

    public ProgressBar getProgressDialog() {
        ProgressBar progressBar2 = this.progressBar;
        if (progressBar2 != null) {
            return progressBar2;
        }
        return null;
    }

    public boolean isShowing() {
        return this.progressBar.getVisibility() == 0;
    }

    public static Drawable getProgressDrawable() {
        Sprite sprite;
        switch (sharedPrefHelper.getProgressAnimationType()) {
            case 1:
                sprite = new RotatingPlane();
                break;
            case 2:
                sprite = new DoubleBounce();
                break;
            case 3:
                sprite = new Wave();
                break;
            case 4:
                sprite = new WanderingCubes();
                break;
            case 5:
                sprite = new Pulse();
                break;
            case 6:
                sprite = new ChasingDots();
                break;
            case 7:
                sprite = new ThreeBounce();
                break;
            case 8:
                sprite = new Circle();
                break;
            case 9:
                sprite = new CubeGrid();
                break;
            case 10:
                sprite = new FadingCircle();
                break;
            case 11:
                sprite = new FoldingCube();
                break;
            case 12:
                sprite = new RotatingCircle();
                break;
            default:
                sprite = new Circle();
                break;
        }
        sprite.setColor(sharedPrefHelper.getProgressAnimationColor());
        return sprite;
    }

    private void setProgressBar(Activity activity) {
        ViewGroup viewGroup = (ViewGroup) activity.findViewById(16908290).getRootView();
        this.progressBar = new ProgressBar(activity, null, 16842874);
        this.progressBar.setIndeterminate(true);
        this.progressBar.setIndeterminateDrawable(getProgressDrawable());
        this.progressBar.setLayoutParams(new LayoutParams(Opcodes.FCMPG, Opcodes.FCMPG));
        LayoutParams layoutParams = new LayoutParams(-1, -1);
        this.f2165rl = new RelativeLayout(activity);
        this.f2165rl.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                ProgressViewHelper.this.dismiss();
            }
        });
        this.f2165rl.setGravity(17);
        this.f2165rl.addView(this.progressBar);
        viewGroup.addView(this.f2165rl, layoutParams);
        dismiss();
    }
}
