package com.soarex.eterquest.engine;

import com.soarex.eterquest.engine.util.FloatVector;

/**
 * Created by shumaf on 10.02.16.
 */
public class Game {

    public static final float FIELD_OF_VIEW = 70f * ((float)Math.PI / 180f);
    public static final FloatVector X_AXIS = new FloatVector(1f, 0f, 0f);
    public static final FloatVector Y_AXIS = new FloatVector(0f, 1f, 0f);
    public static final FloatVector Z_AXIS = new FloatVector(0f, 0f, 1f);
    public static final FloatVector DEFAULT_CLEAR_COLOR = new FloatVector(.5294f, .8078f, .9216f);
    public static final float NEAR_CLIP = 0f;
    public static final float FAR_CLIP = 1000f;
    public static final int MAX_FPS = 1000;
    public static final long FRAME_TIME = (long)(1d / (double)MAX_FPS * 1000000000d);
    public static final float MOVEMENT_SPEED = 0.025f;
    public static final float ROTATION_SPEED = 0.002f;
}
