package by.bsu.physics;

import org.apache.commons.math3.geometry.euclidean.threed.Vector3D;

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
