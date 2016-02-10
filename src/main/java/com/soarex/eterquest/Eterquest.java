package com.soarex.eterquest;

import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.PixelFormat;

import java.io.IOException;
import java.nio.ByteBuffer;

import static org.lwjgl.opengl.GL11.*;

/**
 * Created by shumaf on 10.02.16.
 */
public class Eterquest {

    public static final int DEFAULT_WIDTH = 800;
    public static final int DEFAULT_HEIGHT = 600;
    public static final String TITLE = "Eterquest";
    private static int FPS = 60;

    public static void main(String[] args) throws IOException {
        try {
            Display.setDisplayMode(new DisplayMode(DEFAULT_WIDTH, DEFAULT_HEIGHT));
            Display.setTitle(TITLE);
            Display.setIcon(new ByteBuffer[]{
                    Util.loadIcon(Eterquest.class.getResourceAsStream("/icon/icon16.png")),
                    Util.loadIcon(Eterquest.class.getResourceAsStream("/icon/icon32.png")),
                    Util.loadIcon(Eterquest.class.getResourceAsStream("/icon/icon128.png")),
            });
            Display.create(new PixelFormat().withDepthBits(24));
        } catch (LWJGLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        glViewport(0, 0, DEFAULT_WIDTH, DEFAULT_HEIGHT);

        while (!Display.isCloseRequested()) {
            glClear(GL_COLOR_BUFFER_BIT);

            glBegin(GL_QUADS);
                glVertex2d(0.5, 0.5);
                glVertex2d(-0.5, 0.5);
                glVertex2d(-0.5, -0.5);
                glVertex2d(0.5, -0.5);
            glEnd();

            Display.sync(FPS);
            Display.update();
        }

        Display.destroy();
    }
}
