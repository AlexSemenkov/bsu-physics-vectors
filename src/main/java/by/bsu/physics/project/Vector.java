package by.bsu.physics.project;

/**
 * @author asemenkov
 * @since Oct 07, 2020
 */
public abstract class Vector {

    public abstract double getLength() throws InterruptedException;

    public abstract double getScalarProduct(Vector vector) throws InterruptedException;

    public abstract double getCosAngle(Vector vector) throws InterruptedException;

}
