package com.soarex.eterquest.engine;

import static org.lwjgl.opengl.GL11.*;

import java.io.IOException;

import com.soarex.eterquest.engine.util.DataUtil;
import org.newdawn.slick.opengl.TextureLoader;

/**
 * Created by shumaf on 10.02.16.
 */
public class Texture {

    public String dir;
    public int pointer;

    public Texture(String dir) {
        try{
            pointer = TextureLoader.getTexture("png", DataUtil.getStream(dir)).getTextureID();
        } catch(IOException e) {
            e.printStackTrace();
        }
    }

    public void bind() {
        glBindTexture(GL_TEXTURE_2D, pointer);
    }
}
