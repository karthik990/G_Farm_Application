package jahirfiquitiva.libs.fabsmenu;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.coordinatorlayout.widget.CoordinatorLayout.Behavior;
import androidx.core.view.ViewCompat;
import androidx.interpolator.view.animation.FastOutSlowInInterpolator;
import com.google.android.material.snackbar.Snackbar.SnackbarLayout;

public class FABSnackbarBehavior extends Behavior<View> {
    private float mTranslationY;

    public FABSnackbarBehavior() {
    }

    public FABSnackbarBehavior(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    private float getFabTranslationYForSnackbar(CoordinatorLayout coordinatorLayout, View view) {
        float f = 0.0f;
        for (View view2 : coordinatorLayout.getDependencies(view)) {
            if ((view2 instanceof SnackbarLayout) && coordinatorLayout.doViewsOverlap(view, view2)) {
                f = Math.min(f, view2.getTranslationY() - ((float) view2.getHeight()));
            }
        }
        return f;
    }

    public boolean layoutDependsOn(CoordinatorLayout coordinatorLayout, View view, View view2) {
        return view2 instanceof SnackbarLayout;
    }

    public boolean onDependentViewChanged(CoordinatorLayout coordinatorLayout, View view, View view2) {
        if (view2 instanceof SnackbarLayout) {
            updateFabTranslationForSnackbar(coordinatorLayout, view, view2);
        }
        return false;
    }

    private void updateFabTranslationForSnackbar(CoordinatorLayout coordinatorLayout, View view, View view2) {
        float fabTranslationYForSnackbar = getFabTranslationYForSnackbar(coordinatorLayout, view);
        if (fabTranslationYForSnackbar != this.mTranslationY) {
            ViewCompat.animate(view).cancel();
            if (Math.abs(fabTranslationYForSnackbar - this.mTranslationY) == ((float) view2.getHeight())) {
                ViewCompat.animate(view).translationY(fabTranslationYForSnackbar).setInterpolator(new FastOutSlowInInterpolator());
            } else {
                view.setTranslationY(fabTranslationYForSnackbar);
            }
            this.mTranslationY = fabTranslationYForSnackbar;
        }
    }
}
