package com.soarex.eterquest;

import com.soarex.eterquest.util.ImageUtil;
import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.PixelFormat;

import java.io.IOException;
import java.nio.ByteBuffer;

/**
 * Created by shumaf on 10.02.16.
 */
public class Eterquest {

    private static int WIDTH = 1280;
    private static int HEIGHT = 720;
    private static int FPS = 60;

    public static void main(String[] args) {
        try {
            Display.setDisplayMode(new DisplayMode(WIDTH, HEIGHT));
            Display.setTitle("Eterquest");
            Display.setIcon(new ByteBuffer[]{
                ImageUtil.loadIcon(Eterquest.class.getResourceAsStream("/icon/icon16.png")),
                ImageUtil.loadIcon(Eterquest.class.getResourceAsStream("/icon/icon32.png")),
                ImageUtil.loadIcon(Eterquest.class.getResourceAsStream("/icon/icon128.png")),
            });
            Display.create(new PixelFormat().withDepthBits(24));
        } catch (LWJGLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        GL11.glViewport(0, 0, WIDTH, HEIGHT);

        while (!Display.isCloseRequested()) {
            Display.sync(FPS);
            Display.update();
        }

        Display.destroy();
    }
}
