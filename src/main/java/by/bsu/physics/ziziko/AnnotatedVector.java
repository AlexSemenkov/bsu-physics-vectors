package by.bsu.physics.ziziko;

import by.bsu.physics.Vector;

import java.lang.reflect.Field;
import java.util.Iterator;
import java.util.SortedMap;
import java.util.TreeMap;

public class AnnotatedVector extends Vector {
    private SortedMap<Integer, Field> fieldMap = new TreeMap();

    public AnnotatedVector() {
        Field[] var1 = this.getClass().getDeclaredFields();


        for (Field field : var1) {
            int mapSize;
            if (field.isAnnotationPresent(Coordinate.class)) {
                field.setAccessible(true);
                mapSize = this.fieldMap.size();
                this.fieldMap.put(( field.getAnnotation(Coordinate.class)).value(), field);
                if (mapSize == this.fieldMap.size()) {
                    throw new IllegalArgumentException("Duplicate coordinate index.");
                }
            }

            mapSize = this.fieldMap.size();
            int lastElement = (Integer) this.fieldMap.keySet().toArray()[this.fieldMap.size() - 1];
            if (lastElement + 1 != mapSize) {
                throw new IllegalArgumentException("Missing coordinate index.");
            }

            if (( field.getAnnotation(Coordinate.class)).value() < 0) {
                throw new IllegalArgumentException("Negative coordinate index.");
            }
        }

    }

    public double getLength()  {
        double result = 0.0;
        for (Field f: fieldMap.values()) {

            try {
                result += Math.pow((double) f.get(this), 2);//координаты
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }

        }
        return Math.sqrt(result);
    }

    public double getScalarProduct(Vector vector) {
        double result = 0.0;
        if (!(vector instanceof AnnotatedVector)) {
            throw new IllegalArgumentException("Wrong type of vector.");
        }

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
        return this.getScalarProduct(that) / (this.getLength() * that.getLength());
    }
}