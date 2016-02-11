package com.soarex.eterquest.engine.util;

/**
 * Created by shumaf on 10.02.16.
 */
public class Quaternion {

    private FloatVector q;

    public Quaternion(float theta, FloatVector axis) {
        q = new FloatVector();
        q.pushBack((float)Math.cos(theta / 2F));
        q.pushBack((float)Math.sin(theta / 2F) * axis.getX());
        q.pushBack((float)Math.sin(theta / 2F) * axis.getY());
        q.pushBack((float)Math.sin(theta / 2F) * axis.getZ());
    }

    protected Quaternion(FloatVector q) {
        this.q = q;
    }

    protected Quaternion(Float... elements) {
        this(new FloatVector(elements));
    }

    public Quaternion multiply(Quaternion v) {
        float w = q.get(0);
        float x = q.get(1);
        float y = q.get(2);
        float z = q.get(3);
        float u = v.q.get(0);
        float a = v.q.get(1);
        float b = v.q.get(2);
        float c = v.q.get(3);
        FloatVector r = new FloatVector(
                u*w - a*x - b*y - c*z,
                a*w + u*x + c*y - b*z,
                b*w - c*x + u*y + a*z,
                c*w + b*x - a*y + u*z);
        return new Quaternion(r);
    }


    public Quaternion conjugate() {
        return new Quaternion(q.get(0), -q.get(1), -q.get(2), -q.get(3));
    }

    public FloatVector toVector() {
        return new FloatVector(q.get(1), q.get(2), q.get(3));
    }

    @Override
    public String toString() {
        return q.get(0) + " + "+ q.get(1) + "i + " + q.get(2) + "j + " + q.get(3) + "k";
    }
}
