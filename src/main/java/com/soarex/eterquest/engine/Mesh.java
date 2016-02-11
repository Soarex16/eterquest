package com.soarex.eterquest.engine;

import com.soarex.eterquest.engine.util.GraphicsUtil;
import com.soarex.eterquest.engine.util.MiscUtil;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL20.*;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by shumaf on 10.02.16.
 */
public class Mesh {

    private int vboPointer;
    private int iboPointer;
    private int iboSize;
    private boolean wireframe;

    public Mesh() {
        vboPointer = glGenBuffers();
        iboPointer = glGenBuffers();
        iboSize = 0;
    }

    public Mesh(String dir) {
        this();
        load(dir);
    }

    public void addVertices(int[] indices, Vertex... vertices) {
        iboSize = indices.length;
        glBindBuffer(GL_ARRAY_BUFFER, vboPointer);
        glBufferData(GL_ARRAY_BUFFER, GraphicsUtil.toBuffer(vertices), GL_STATIC_DRAW);
        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, iboPointer);
        glBufferData(GL_ELEMENT_ARRAY_BUFFER, GraphicsUtil.toBuffer(indices), GL_STATIC_DRAW);
    }

    public boolean isWireframe() {
        return wireframe;
    }

    public void setWireframe(boolean wireframe) {
        this.wireframe = wireframe;
    }

    public void render() {
        glEnableVertexAttribArray(0);
        glEnableVertexAttribArray(1);

        if (wireframe)
            glPolygonMode(GL_FRONT_AND_BACK, GL_LINE );
        else
            glPolygonMode(GL_FRONT_AND_BACK, GL_FILL);

        glBindBuffer(GL_ARRAY_BUFFER, vboPointer);
        glVertexAttribPointer(0, 3, GL_FLOAT, false, Vertex.SIZE * 4, 0);
        glVertexAttribPointer(1, 2, GL_FLOAT, false, Vertex.SIZE * 4, 12);

        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, iboPointer);
        glDrawElements(GL_TRIANGLES, iboSize, GL_UNSIGNED_INT, 0);

        glDisableVertexAttribArray(0);
        glDisableVertexAttribArray(1);
    }

    private void load(String dir) {
        List<Vertex> vertices = new ArrayList<Vertex>();
        List<Integer> indices = new ArrayList<Integer>();
        try{
            File file = new File(dir);
            BufferedReader in = new BufferedReader(new FileReader(file));
            String line = in.readLine();
            while (line != null) {
                String[] tokens = MiscUtil.tokenize(line, " ");
                if (tokens.length != 0) {
                    if (tokens[0].equals("v")) {
                        float x = Float.parseFloat(tokens[1]);
                        float y = Float.parseFloat(tokens[2]);
                        float z = Float.parseFloat(tokens[3]);
                        vertices.add(new Vertex(x, y, z));
                    }else
                    if (tokens[0].equals("f")) {
                        indices.add(Integer.parseInt(tokens[1]) - 1);
                        indices.add(Integer.parseInt(tokens[2]) - 1);
                        indices.add(Integer.parseInt(tokens[3]) - 1);
                    }
                }
                line = in.readLine();
            }
            in.close();
        }catch(Exception e) {
            e.printStackTrace();
        }
        addVertices(GraphicsUtil.toArray(indices), vertices.toArray(new Vertex[vertices.size()]));
    }

}
