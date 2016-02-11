package com.soarex.eterquest.engine.util;

import static com.soarex.eterquest.engine.util.MiscUtil.*;

/**
 * Created by shumaf on 10.02.16.
 */
public class FloatMatrix extends Matrix<Float> {

    public FloatMatrix(Float[][] matrix) {
        super(matrix);
    }

    public FloatMatrix(FloatVector... rows) {
        super(rows);
    }

    public FloatMatrix(int rows, int cols) {
        super(rows, cols);
    }

    public FloatMatrix multiply(FloatMatrix right) {
        FloatMatrix product = new FloatMatrix(getHeight(), right.getWidth());
        for (int x = 0; x < right.getWidth(); x++) {
            for (int y = 0; y < this.getHeight(); y++) {
                float dotProduct = 0F;
                for (int i = 0, j = 0; i < this.getWidth() && j < right.getHeight(); i++, j++) {
                    dotProduct += this.get(i, y) * right.get(x, j);
                }
                product.set(x, y, dotProduct);
            }
        }
        return product;
    }

    public FloatVector multiply(FloatVector right) {
        FloatVector product = new FloatVector();
        for (int i = 0; i < right.size(); i++)
            product.pushBack(right.dot(getRow(i)));
        return product;
    }

    public FloatMatrix add(Matrix<Float> m) {
        if (getWidth() != m.getWidth() || getHeight() != m.getHeight())
            return this;
        FloatMatrix sum = new FloatMatrix(getWidth(), getHeight());
        for (int i = 0; i < getWidth(); i++)
            for (int j = 0; j < getHeight(); j++)
                sum.set(i, j, get(i, j) + m.get(i, j));
        return sum;
    }

    public static FloatMatrix identity(int size) {
        Float[][] matrix = new Float[size][];
        for (int i = 0; i < size; i++) {
            Float[] col = new Float[size];
            for (int j = 0; j < size; j++) {
                col[j] = 0F;
            }
            col[i] = 1F;
            matrix[i] = col;
        }
        return new FloatMatrix(matrix);
    }

    public static FloatMatrix zeros(int rows, int cols) {
        Float[][] matrix = new Float[cols][];
        for (int i = 0; i < cols; i++) {
            Float[] column = new Float[rows];
            for (int j = 0; j < rows; j++)
                column[j] = 0F;
            matrix[i] = column;
        }
        return new FloatMatrix(matrix);
    }

    public static FloatMatrix zeros(int size) {
        return zeros(size, size);
    }

    public static FloatMatrix translation(float dx, float dy, float dz) {
        FloatMatrix m = new FloatMatrix(4, 4);

        m.set(0, 0, 1F);	m.set(1, 0, 0F);	m.set(2, 0, 0F);	m.set(3, 0, dx);
        m.set(0, 1, 0F);	m.set(1, 1, 1F);	m.set(2, 1, 0F);	m.set(3, 1, dy);
        m.set(0, 2, 0F);	m.set(1, 2, 0F);	m.set(2, 2, 1F);	m.set(3, 2, dz);
        m.set(0, 3, 0F);	m.set(1, 3, 0F);	m.set(2, 3, 0F);	m.set(3, 3, 1F);

        return m;

    }

    public static FloatMatrix rotation(float p, float y, float r) {
        float sp = sin(p);	float cp = cos(p);
        float sy = sin(y);	float cy = cos(y);
        float sr = sin(r);	float cr = cos(r);
        FloatMatrix m = new FloatMatrix(4, 4);

        m.set(0, 0, cy*cr + sy*sp*sr);	m.set(1, 0, -cy*sr + sy*sp*cr);	m.set(2, 0, sy*cp);	m.set(3, 0, 0F);
        m.set(0, 1, cp*sr);				m.set(1, 1, cp*cr);				m.set(2, 1, -sp);	m.set(3, 1, 0F);
        m.set(0, 2, -sy*cr + cy*sp*sr);	m.set(1, 2, sy*sr + cy*sp*cr);	m.set(2, 2, cy*cp);	m.set(3, 2, 0F);
        m.set(0, 3, 0F);				m.set(1, 3, 0F);				m.set(2, 3, 0F);	m.set(3, 3, 1F);

        return m;
    }

    public static FloatMatrix scale(float x, float y, float z) {
        FloatMatrix m = new FloatMatrix(4, 4);

        m.set(0, 0, x);		m.set(1, 0, 0F);	m.set(2, 0, 0F);	m.set(3, 0, 0F);
        m.set(0, 1, 0F);	m.set(1, 1, y);		m.set(2, 1, 0F);	m.set(3, 1, 0F);
        m.set(0, 2, 0F);	m.set(1, 2, 0F);	m.set(2, 2, z);		m.set(3, 2, 0F);
        m.set(0, 3, 0F);	m.set(1, 3, 0F);	m.set(2, 3, 0F);	m.set(3, 3, 1F);
        return m;
    }

    public static FloatMatrix translation(FloatVector v) {
        return translation(v.getX(), v.getY(), v.getZ());
    }

    public static FloatMatrix rotation(FloatVector v) {
        return rotation(v.getX(), v.getY(), v.getZ());
    }

    public static FloatMatrix scale(FloatVector v) {
        return scale(v.getX(), v.getY(), v.getZ());
    }

}
