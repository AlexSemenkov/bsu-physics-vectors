package by.bsu.physics.project;

/**
 * @author asemenkov
 * @since Oct 07, 2020
 */
public abstract class Vector {

    public abstract double getLength() throws IllegalAccessException;

    public abstract double getScalarProduct(Vector vector) throws IllegalAccessException;

    public abstract double getCosAngle(Vector vector) throws IllegalAccessException;

}