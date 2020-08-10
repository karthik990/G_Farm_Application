package com.github.ybq.android.spinkit;

import com.github.ybq.android.spinkit.sprite.Sprite;
import com.github.ybq.android.spinkit.style.ChasingDots;
import com.github.ybq.android.spinkit.style.Circle;
import com.github.ybq.android.spinkit.style.CubeGrid;
import com.github.ybq.android.spinkit.style.DoubleBounce;
import com.github.ybq.android.spinkit.style.FadingCircle;
import com.github.ybq.android.spinkit.style.FoldingCube;
import com.github.ybq.android.spinkit.style.MultiplePulse;
import com.github.ybq.android.spinkit.style.MultiplePulseRing;
import com.github.ybq.android.spinkit.style.Pulse;
import com.github.ybq.android.spinkit.style.PulseRing;
import com.github.ybq.android.spinkit.style.RotatingCircle;
import com.github.ybq.android.spinkit.style.RotatingPlane;
import com.github.ybq.android.spinkit.style.ThreeBounce;
import com.github.ybq.android.spinkit.style.WanderingCubes;
import com.github.ybq.android.spinkit.style.Wave;

public class SpriteFactory {
    public static Sprite create(Style style) {
        switch (style) {
            case ROTATING_PLANE:
                return new RotatingPlane();
            case DOUBLE_BOUNCE:
                return new DoubleBounce();
            case WAVE:
                return new Wave();
            case WANDERING_CUBES:
                return new WanderingCubes();
            case PULSE:
                return new Pulse();
            case CHASING_DOTS:
                return new ChasingDots();
            case THREE_BOUNCE:
                return new ThreeBounce();
            case CIRCLE:
                return new Circle();
            case CUBE_GRID:
                return new CubeGrid();
            case FADING_CIRCLE:
                return new FadingCircle();
            case FOLDING_CUBE:
                return new FoldingCube();
            case ROTATING_CIRCLE:
                return new RotatingCircle();
            case MULTIPLE_PULSE:
                return new MultiplePulse();
            case PULSE_RING:
                return new PulseRing();
            case MULTIPLE_PULSE_RING:
                return new MultiplePulseRing();
            default:
                return null;
        }
    }
}
