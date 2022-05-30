package by.bsu.physics.sheremet;
import by.bsu.physics.Vector;

import java.lang.reflect.Field;
import java.util.*;

import static java.lang.Math.pow;
import static java.lang.Math.sqrt;

public class AnnotatedVector extends Vector {

    private SortedMap<Integer, Field> fieldMap = new TreeMap<>();


    public AnnotatedVector() {
        for (Field field : this.getClass().getDeclaredFields()) {
            if (field.isAnnotationPresent(Coordinate.class)) {
                field.setAccessible(true);
                int sizeBefore = fieldMap.size();
                fieldMap.put(field.getAnnotation(Coordinate.class).value(), field);

                if (sizeBefore == fieldMap.size()) {
                    throw new IllegalArgumentException("Duplicate coordinate index.");
                }
            }
            if (field.getAnnotation(Coordinate.class).value() == -1) {
                throw new IllegalArgumentException("Missing coordinate index.");
            }
            if (field.getAnnotation(Coordinate.class).value() < 0) {
                throw new IllegalArgumentException("Negative coordinate index.");
            }


        }
    }

    public double getLength()  {
        double result = 0;
        for (Field field : this.getClass().getDeclaredFields()) {
            try {
                result += pow((double) field.get(this), 2);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }

        return sqrt(result);
    }

    public double getScalarProduct(Vector vector)  {
        double result = 0;
        if (!(vector instanceof AnnotatedVector)) {
            throw new IllegalArgumentException("Wrong type of vector.");
        }
       AnnotatedVector annotatedVector =(AnnotatedVector) vector;
        if (!(this.getClass().getDeclaredFields().length == vector.getClass().getDeclaredFields().length)) {
            throw new IllegalArgumentException("Vectors have different sizes.");
        } else {
            for (int i = 0; i < vector.getClass().getDeclaredFields().length; i++) {
                try {
                    result +=  ((double) this.getClass().getDeclaredFields()[i].get(this) * (double) vector.getClass().getDeclaredFields()[i].get(vector));
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }


        }
        return result;
    }

    public double getCosAngle(Vector that)  {
        return getScalarProduct(that) / (this.getLength() * that.getLength());
    }


}
