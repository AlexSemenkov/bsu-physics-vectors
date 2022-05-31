package by.bsu.physics.Mrochko;

import java.lang.reflect.Field;
import java.util.*;
import by.bsu.physics.Vector;
import static java.lang.Math.*;

public class AnnotatedVector extends Vector  {
    private final SortedMap<Integer, Field> fieldMap = new TreeMap<>();
    public AnnotatedVector() {
        for (Field field : this.getClass().getDeclaredFields()) {
            if (field.isAnnotationPresent(Coordinate.class)) {
                field.setAccessible(true);
                int checkSize = fieldMap.size();
                fieldMap.put(field.getAnnotation(Coordinate.class).value(), field);
                if (checkSize == fieldMap.size()) {
                    throw new IllegalArgumentException("Duplicate coordinate index.");
                }
            }
            if (field.getAnnotation(Coordinate.class).value() < 0) {
                throw new IllegalArgumentException("Negative coordinate index.");
            }
        }
        if (fieldMap.values().size()-1 != fieldMap.keySet().stream().max(Integer::compareTo).get()) {
            throw new IllegalArgumentException("Missing coordinate index.");
        }
    }
    
    @Override
    public double getLength(){
        double result = 0;
        for (Field field : fieldMap.values()) {
            try {
                result += pow((double) field.get(this), 2);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        return sqrt(result);
    }

    @Override
    public double getScalarProduct(Vector vector){
        double result = 0;
        if (this.getClass() != vector.getClass()) {
            throw new IllegalArgumentException("Wrong type of vector.");
        }
        if (this.fieldMap.keySet().size() != ((AnnotatedVector) vector).fieldMap.size()){
            throw new IllegalArgumentException("Vectors have different sizes.");
        } else {
            for (Field field:this.fieldMap.values()) {
                try {
                    result += (double) field.get(this) * (double) field.get(vector);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }
        return result;
    }

    @Override
    public double getCosAngle(Vector tempVector) {
        return getScalarProduct(tempVector) / (tempVector.getLength() * this.getLength());
    }
}
