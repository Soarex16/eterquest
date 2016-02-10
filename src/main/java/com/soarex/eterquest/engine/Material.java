package com.soarex.eterquest.engine;

import com.soarex.eterquest.engine.util.FloatVector;
import com.soarex.eterquest.engine.util.GraphicsUtil;

import java.awt.*;

/**
 * Created by shumaf on 10.02.16.
 */
public class Material {

    private Texture texture;
    private FloatVector tint;

    public Material(){
        this.texture = null;
        this.tint = new FloatVector(1f, 1f, 1f);
    }

    public Material(Texture texture){
        this.texture = texture;
        this.tint = new FloatVector(1f, 1f, 1f);
    }

    public Material(Texture texture, FloatVector tint){
        this.texture = texture;
        this.tint = tint;
    }

    public Material(Texture texture, float red, float green, float blue){
        this(texture, new FloatVector(red, green, blue));
    }

    public Material(Color tint){
        this.tint = GraphicsUtil.toVector(tint);
        this.texture = null;
    }

    public Material(FloatVector tint){
        this.texture = null;
        this.tint = tint;
    }

    public Material(float red, float green, float blue){
        this(new FloatVector(red, green, blue));
    }

    public void bindTexture(){
        if(texture == null)
            return;
        texture.bind();
    }

    public Texture getTexture(){
        return texture;
    }

    public void setTexture(Texture texture){
        this.texture = texture;
    }

    public FloatVector getTint(){
        return tint;
    }

    public void setTint(FloatVector tint){
        this.tint = tint;
    }

}
