package by.bsu.physics;

/**
 * @author asemenkov
 * @since Oct 07, 2020
 */
public abstract class Vector {

    public abstract double getLength();

    public abstract double getScalarProduct(Vector vector);

    public abstract double getCosAngle(Vector vector);

    public abstract <T> Vector3D multiply(T vector);
}
