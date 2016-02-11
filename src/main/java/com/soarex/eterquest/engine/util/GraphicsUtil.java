package com.soarex.eterquest.engine.util;

import com.soarex.eterquest.engine.Vertex;
import org.lwjgl.BufferUtils;

import java.awt.*;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;

import static org.lwjgl.opengl.GL11.GL_VERSION;
import static org.lwjgl.opengl.GL11.glGetString;

/**
 * Created by shumaf on 10.02.16.
 */
public abstract class GraphicsUtil {

    public static String getOpenGLVersion() {
        return glGetString(GL_VERSION);
    }

    public static FloatVector toVector(Color color) {
        float r = (1F/255F) * (float)color.getRed();
        float g = (1F/255F) * (float)color.getGreen();
        float b = (1F/255F) * (float)color.getBlue();
        return new FloatVector(r, g, b);
    }

    public static FloatBuffer toBuffer(Vertex[] vertices) {
        FloatBuffer buffer = BufferUtils.createFloatBuffer(vertices.length * Vertex.SIZE);
        for (Vertex v : vertices) {
            buffer.put(v.getX());
            buffer.put(v.getY());
            buffer.put(v.getZ());
            buffer.put(v.getTextureCoordinates().getX());
            buffer.put(v.getTextureCoordinates().getY());
        }
        buffer.flip();
        return buffer;
    }

    public static IntBuffer toBuffer(int... values) {
        IntBuffer buffer = BufferUtils.createIntBuffer(values.length);
        for (int i : values) {
            buffer.put(i);
        }
        buffer.flip();
        return buffer;
    }

    public static FloatBuffer toBuffer(Matrix<Float> matrix) {
        FloatBuffer buffer = BufferUtils.createFloatBuffer(matrix.getWidth() * matrix.getHeight());
        for (int i = 0; i < matrix.getWidth(); i++)
            for (int j = 0; j < matrix.getHeight(); j++)
                buffer.put(matrix.get(i, j));
        buffer.flip();
        return buffer;
    }

    public static int[] toArray(java.util.List<Integer> l) {
        int[] array = new int[l.size()];
        for (int i = 0; i < l.size(); i++)
            array[i] = l.get(i);
        return array;
    }
}
