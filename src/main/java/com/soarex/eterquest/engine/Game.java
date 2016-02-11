package com.soarex.eterquest.engine;

import java.awt.Color;
import java.util.HashSet;
import java.util.Set;

import com.soarex.eterquest.engine.util.GraphicsUtil;
import com.soarex.eterquest.engine.util.FloatVector;
import com.soarex.eterquest.engine.util.MiscUtil;

import org.lwjgl.LWJGLException;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;

import static org.lwjgl.opengl.GL11.*;

/**
 * Created by shumaf on 10.02.16.
 */
public class Game {

    public static final int DEFAULT_WIDTH = 800;
    public static final int DEFAULT_HEIGHT = 600;
    public static final String TITLE = "Game";
    public static final float FIELD_OF_VIEW = 70F * ((float)Math.PI / 180F);
    public static final FloatVector X_AXIS = new FloatVector(1F, 0F, 0F);
    public static final FloatVector Y_AXIS = new FloatVector(0F, 1F, 0F);
    public static final FloatVector Z_AXIS = new FloatVector(0F, 0F, 1F);
    public static final FloatVector DEFAULT_CLEAR_COLOR = new FloatVector(0.5294F, 0.8078F, 0.9216F);
    public static final float NEAR_CLIP = 0F;
    public static final float FAR_CLIP = 1000F;
    public static final int MAX_FPS = 1000;
    public static final long FRAME_TIME = (long)(1D / (double)MAX_FPS * 1000000000D);
    public static final float MOVEMENT_SPEED = 0.025F;
    public static final float ROTATION_SPEED = 0.002F;

    private Set<Object3D> objects;

    private static Camera camera;
    private static float aspectRatio;
    private static int width;
    private static int height;
    private static FloatVector projection;
    private static FloatVector clearColor;
    private static FloatVector ambientLight;

    public static void main(String[] args) {
        new Game();
    }

    public Game() {
        initGL();
        initGame();
        loop();
        close();
    }

    private void initGL() {
        Display.setTitle(TITLE);
        try{
            width = DEFAULT_WIDTH;
            height = DEFAULT_HEIGHT;
            aspectRatio = (float)width / (float)height;
            Display.setDisplayMode(new DisplayMode(width, height));
            Display.create();
            Keyboard.create();
            Mouse.create();
        } catch(LWJGLException e) {
            e.printStackTrace();
        }
        setClearColor(0F, 0F, 0F);
        setAmbientLight(1F, 1F, 1F);
        glFrontFace(GL_CW);
        glCullFace(GL_BACK);
        glEnable(GL_CULL_FACE);
        glEnable(GL_DEPTH_TEST);
        glEnable(GL_TEXTURE_2D);
        camera = new Camera();
        calculateProjection();
    }

    private void initGame() {
        objects = new HashSet<Object3D>();
        for (float i = 0; i < 2 * Math.PI - 0.1F; i+=(2 * Math.PI) / 18F) {
            Object3D o = new Object3D("models/cube.obj");
            o.setMaterial(new Material(MiscUtil.randomColor()));
            o.setTranslation((float)Math.cos(i) * 20, 0, (float)Math.sin(i) * 20);
            o.setScale(0.6F, 0.6F, 0.6F);
            objects.add(o);
        }
    }

    public static void setClearColor(FloatVector color) {
        clearColor = color;
        glClearColor(color.getX(), color.getY(), color.getZ(), 1F);
    }

    public static void setClearColor(float red, float green, float blue) {
        setClearColor(new FloatVector(red, green, blue));
    }

    public static void setClearColor(Color color) {
        setClearColor(GraphicsUtil.toVector(color));
    }

    public static FloatVector getClearColor() {
        return clearColor;
    }

    public static FloatVector getAmbientLight() {
        return ambientLight;
    }

    public static void setAmbientLight(FloatVector ambientLight) {
        Game.ambientLight = ambientLight;
    }

    public static void setAmbientLight(float r, float g, float b) {
        setAmbientLight(new FloatVector(r, g, b));
    }

    private void loop() {
        long lastFrame = 0;
        long lag = 0;
        while (true) {
            long startTime = MiscUtil.time();
            lag += lastFrame;
            boolean render = false;
            while (lag > FRAME_TIME) {
                render = true;
                lag -= FRAME_TIME;
                if (Display.isCloseRequested())
                    return;
                update();
            }
            if (render)
                render();
            else
                MiscUtil.sleep(1);
            lastFrame = MiscUtil.time() - startTime;
        }
    }

    private void update() {
        Input.update();
        updateMovement();
        updateRotation();
        // FIXME: 11.02.16 if I delete next 3 lines, camera not move
        for (Object3D object : objects) {
            object.rotate(0.001F, 0.001F, 0.001F);
        }
    }

    private void updateMovement() {
        if (Input.isKeyDown(Keyboard.KEY_W))
            camera.move(0F, 0F, MOVEMENT_SPEED);
        if (Input.isKeyDown(Keyboard.KEY_S))
            camera.move(0F, 0F, -MOVEMENT_SPEED);
        if (Input.isKeyDown(Keyboard.KEY_A))
            camera.move(-MOVEMENT_SPEED, 0F, 0F);
        if (Input.isKeyDown(Keyboard.KEY_D))
            camera.move(MOVEMENT_SPEED, 0F, 0F);
        if (Input.isKeyDown(Keyboard.KEY_SPACE))
            camera.move(0F, MOVEMENT_SPEED, 0F);
        if (Input.isKeyDown(Keyboard.KEY_LSHIFT))
            camera.move(0F, -MOVEMENT_SPEED, 0F);
    }

    private void updateRotation() {
        if (Input.isKeyDown(Keyboard.KEY_UP))
            camera.rotate(-ROTATION_SPEED, 0F, 0F);
        if (Input.isKeyDown(Keyboard.KEY_DOWN))
            camera.rotate(ROTATION_SPEED, 0F, 0F);
        if (Input.isKeyDown(Keyboard.KEY_LEFT))
            camera.rotate(0F, -ROTATION_SPEED, 0F);
        if (Input.isKeyDown(Keyboard.KEY_RIGHT))
            camera.rotate(0F, ROTATION_SPEED, 0F);
    }

    private void render() {
        glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
        for (Object3D object : objects)
            object.render();
        Display.update();
    }

    public static Camera getCamera() {
        return camera;
    }

    public static float getAspectRatio() {
        return aspectRatio;
    }

    public static int getWidth() {
        return width;
    }

    public static int getHeight() {
        return height;
    }

    private void calculateProjection() {
        float x = ((float)Math.tan(Game.FIELD_OF_VIEW / 2F) * Game.getAspectRatio());
        float y = ((float)Math.tan(Game.FIELD_OF_VIEW / 2F));
        float z = -Game.NEAR_CLIP;
        float depth = Game.FAR_CLIP - Game.NEAR_CLIP;
        projection = new FloatVector(x, y, z, depth);
    }

    public static FloatVector getProjection() {
        return projection;
    }

    private void close() {
        Display.destroy();
        Keyboard.destroy();
        Mouse.destroy();
    }

}
