package by.bsu.physics.yatsukhno;

public abstract class AbstractVector {

    public abstract double getLength() throws IllegalAccessException;

    public abstract double getScalarProduct(AbstractVector vector) throws IllegalAccessException;

    public abstract double getCosAngle(AbstractVector vector) throws IllegalAccessException;
}

