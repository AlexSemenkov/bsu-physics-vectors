package by.bsu.physics;

/**
 * @author asemenkov
 * @since Oct 07, 2020
 */
public interface VectorMultipliable<T extends Vector> {

    T multiply(T vector);

}
