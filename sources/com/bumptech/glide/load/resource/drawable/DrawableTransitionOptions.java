package com.bumptech.glide.load.resource.drawable;

import android.graphics.drawable.Drawable;
import com.bumptech.glide.TransitionOptions;
import com.bumptech.glide.request.transition.DrawableCrossFadeFactory;
import com.bumptech.glide.request.transition.DrawableCrossFadeFactory.Builder;
import com.bumptech.glide.request.transition.TransitionFactory;

public final class DrawableTransitionOptions extends TransitionOptions<DrawableTransitionOptions, Drawable> {
    public static DrawableTransitionOptions withCrossFade() {
        return new DrawableTransitionOptions().crossFade();
    }

    public static DrawableTransitionOptions withCrossFade(int i) {
        return new DrawableTransitionOptions().crossFade(i);
    }

    public static DrawableTransitionOptions withCrossFade(DrawableCrossFadeFactory drawableCrossFadeFactory) {
        return new DrawableTransitionOptions().crossFade(drawableCrossFadeFactory);
    }

    public static DrawableTransitionOptions withCrossFade(Builder builder) {
        return new DrawableTransitionOptions().crossFade(builder);
    }

    public static DrawableTransitionOptions with(TransitionFactory<Drawable> transitionFactory) {
        return (DrawableTransitionOptions) new DrawableTransitionOptions().transition(transitionFactory);
    }

    public DrawableTransitionOptions crossFade() {
        return crossFade(new Builder());
    }

    public DrawableTransitionOptions crossFade(int i) {
        return crossFade(new Builder(i));
    }

    public DrawableTransitionOptions crossFade(DrawableCrossFadeFactory drawableCrossFadeFactory) {
        return (DrawableTransitionOptions) transition((TransitionFactory<? super TranscodeType>) drawableCrossFadeFactory);
    }

    public DrawableTransitionOptions crossFade(Builder builder) {
        return crossFade(builder.build());
    }
}
