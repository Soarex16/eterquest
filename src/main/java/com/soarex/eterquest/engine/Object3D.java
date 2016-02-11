package com.soarex.eterquest.engine;

import com.soarex.eterquest.engine.util.FloatMatrix;
import com.soarex.eterquest.engine.util.FloatVector;

/**
 * Created by shumaf on 10.02.16.
 */
public class Object3D {

    private Mesh mesh;
    private Shader shader;
    private Material material;
    private FloatVector translation;
    private FloatVector rotation;
    private FloatVector scale;
    private FloatMatrix transformation;

    public Object3D(Material material) {
        this.material = material;
        mesh = new Mesh();
        init();
    }

    public Object3D(String meshDir) {
        this.material = new Material();
        mesh = new Mesh(meshDir);
        init();
    }

    private void init() {
        shader = new Shader();
        this.translation = new FloatVector(0F, 0F, 0F);
        this.rotation = new FloatVector(0F, 0F, 0F);
        this.scale = new FloatVector(1F, 1F, 1F);
        this.transformation = FloatMatrix.identity(4);
        shader.addVertexShader("shaders/vshader.vert");
        shader.addFragmentShader("shaders/fshader.frag");
        shader.linkShaders();
        shader.addUniform("transformation");
        shader.addUniform("projection");
        shader.addUniform("tint");
        shader.addUniform("ambientLight");
    }

    public Mesh getMesh() {
        return mesh;
    }

    public Shader getShader() {
        return shader;
    }

    private void calculateTransformation() {
        FloatMatrix a = FloatMatrix.translation(translation).multiply(Game.getCamera().getTranslationMatrix());
        FloatMatrix b = FloatMatrix.rotation(rotation);
        FloatMatrix c = FloatMatrix.scale(scale);
        transformation = a.multiply(b).multiply(c);
    }

    public void render() {
        shader.bind();
        shader.setUniform4("transformation", Game.getCamera().getRotationMatrix().multiply(transformation));
        shader.setUniform4("projection", Game.getProjection());
        shader.setUniform("tint", material.getTint());
        shader.setUniform("ambientLight", Game.getAmbientLight());
        material.bindTexture();
        mesh.render();
    }

    public void setMaterial(Material material) {
        this.material = material;
    }

    public float getX() {
        return translation.getX();
    }

    public float getY() {
        return translation.getY();
    }

    public float getZ() {
        return translation.getZ();
    }

    public Material getMaterial() {
        return material;
    }

    public Texture getTexture() {
        return material.getTexture();
    }

    public FloatVector getTint() {
        return material.getTint();
    }

    public void setTranslation(FloatVector v) {
        translation = v;
        calculateTransformation();
    }

    public void setTranslation(float x, float y, float z) {
        setTranslation(new FloatVector(x, y, z));
    }

    public void translate(FloatVector v) {
        setTranslation(translation.add(v));
    }

    public void translate(float dx, float dy, float dz) {
        translate(new FloatVector(dx, dy, dz));
    }

    public void setRotation(FloatVector v) {
        rotation = v;
        calculateTransformation();
    }

    public void setRotation(float pitch, float yaw, float roll) {
        setRotation(new FloatVector(pitch, yaw, roll));
    }

    public void rotate(FloatVector v) {
        setRotation(rotation.add(v));
    }

    public void rotate(float pitch, float yaw, float roll) {
        rotate(new FloatVector(pitch, yaw, roll));
    }

    public void setScale(FloatVector v) {
        scale = v;
        calculateTransformation();
    }

    public void setScale(float x, float y, float z) {
        setScale(new FloatVector(x, y, z));
    }
}
