package com.mobiroller.fragments.ecommerce;

import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationUtils;
import androidx.fragment.app.Fragment;
import com.mobiroller.models.events.AnimationFinishedEvent;
import org.greenrobot.eventbus.EventBus;

public abstract class OrderFlowBaseFragment extends Fragment {
    public abstract boolean isValid();

    public Animation onCreateAnimation(int i, boolean z, int i2) {
        Animation onCreateAnimation = super.onCreateAnimation(i, z, i2);
        if (onCreateAnimation == null && i2 != 0) {
            onCreateAnimation = AnimationUtils.loadAnimation(getActivity(), i2);
        }
        if (onCreateAnimation != null) {
            onCreateAnimation.setAnimationListener(new AnimationListener() {
                public void onAnimationRepeat(Animation animation) {
                }

                public void onAnimationStart(Animation animation) {
                }

                public void onAnimationEnd(Animation animation) {
                    EventBus.getDefault().post(new AnimationFinishedEvent());
                }
            });
        }
        return onCreateAnimation;
    }
}
