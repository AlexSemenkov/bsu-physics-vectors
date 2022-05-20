package by.bsu.physics.project;

import by.bsu.physics.project.Vector;

/**
 * @author asemenkov
 * @since Oct 07, 2020
 */
public interface VectorMultipliable<T extends Vector> {

    T multiply(T vector);

}
