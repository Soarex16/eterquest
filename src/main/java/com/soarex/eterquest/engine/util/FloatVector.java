package com.soarex.eterquest.engine.util;

/**
 * Created by shumaf on 10.02.16.
 */
public class FloatVector extends Vector<Float>{

    public FloatVector(Float... elements){
        super(elements);
    }

    public FloatVector(){
        super();
    }

    public FloatVector copy(){
        FloatVector v = new FloatVector();
        for(Object f : elements)
            v.pushBack((float)f);
        return v;
    }

    public FloatVector multiply(float scalar){
        FloatVector result = new FloatVector();
        for(int i = 0; i < size; i++){
            result.pushBack(scalar * get(i));
        }
        return result;
    }

    public FloatVector add(Vector<Float> b){
        FloatVector result = new FloatVector();
        for(int i = 0; i < Math.min(size, b.size()); i++)
            result.pushBack(get(i) + b.get(i));
        return result;
    }

    public FloatVector add(Float... b){
        return add(new FloatVector(b));
    }

    public FloatVector add(float scalar){
        FloatVector result = new FloatVector();
        for(int i = 0; i < size; i++)
            result.pushBack(get(i) + scalar);
        return result;
    }

    public float dot(Vector<Float> b){
        float c = 0f;
        for(int i = 0; i < Math.min(size, b.size()); i++)
            c += get(i) * b.get(i);
        return c;
    }

    public FloatVector rotate(float theta, FloatVector axis){
        Quaternion a = new Quaternion(theta, axis);
        Quaternion b = toQuaternion();
        Quaternion c = a.conjugate();
        Quaternion d = a.multiply(b).multiply(c);
        return d.toVector();
    }

    public FloatVector cross(Vector<Float> b){
        if(size != 3 || b.size() != 3)
            System.err.println("Both vectors in a cross product must be 3 dimensional!");
        FloatVector result = new FloatVector();
        result.pushBack(getY() * b.getZ() - getZ() * b.getY());
        result.pushBack(getZ() * b.getX() - getX() * b.getZ());
        result.pushBack(getX() * b.getY() - getY() * b.getX());
        return result;
    }

    public FloatVector toUnitVector(){
        FloatVector result = new FloatVector();
        float magnitude = magnitude();
        for(int i = 0; i < size; i++)
            result.pushBack(get(i) / magnitude);
        return result;
    }

    public Quaternion toQuaternion(){
        return new Quaternion(0f, getX(), getY(), getZ());
    }

    public float magnitude(){
        float inner = 0;
        for(int i = 0; i < size; i++){
            inner += Math.pow(get(i), 2);
        }
        return (float)Math.sqrt(inner);
    }
}
