package by.bsu.physics.maslovsky;

import by.bsu.physics.Vector;

import java.lang.reflect.Field;
import java.util.SortedMap;
import java.util.TreeMap;

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

            int mapSize = fieldMap.size();
            int lastElement = (int) fieldMap.keySet().toArray()[fieldMap.size()-1];



            if(!(lastElement + 1 == mapSize)){
                throw new IllegalArgumentException("Missing coordinate index.");
            }


            if (field.getAnnotation(Coordinate.class).value() < 0) {
                throw new IllegalArgumentException("Negative coordinate index.");
            }


        }
    }

    public double getLength()  {
        double result = 0;
        for (Field f: fieldMap.values()) {

            try {
                result += pow(f.getDouble(this), 2);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }

        return sqrt(result);
    }

    public double getScalarProduct(Vector vector){
        double result = 0;
        if (!(vector instanceof AnnotatedVector)) {
            throw new IllegalArgumentException("Wrong type of vector.");
        }
        AnnotatedVector annotatedVector =(AnnotatedVector) vector;


        if (!(this.fieldMap.size()==((AnnotatedVector) vector).fieldMap.size()))
        {
            throw new IllegalArgumentException("Vectors have different sizes.");
        }

        else {
            for (Field f: fieldMap.values()) {
                try {
                    result += f.getDouble(vector) * f.getDouble(this);
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
