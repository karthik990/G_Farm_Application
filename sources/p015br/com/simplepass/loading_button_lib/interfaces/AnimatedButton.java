package p015br.com.simplepass.loading_button_lib.interfaces;

/* renamed from: br.com.simplepass.loading_button_lib.interfaces.AnimatedButton */
public interface AnimatedButton {
    void dispose();

    void revertAnimation();

    void revertAnimation(OnAnimationEndListener onAnimationEndListener);

    void startAnimation();
}
