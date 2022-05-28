package by.bsu.physics.ziziko;

import java.lang.reflect.Field;
import java.util.Iterator;
import java.util.SortedMap;
import java.util.TreeMap;

public class AnnotatedVector extends AbstractVector {
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

    double getLength() throws IllegalAccessException {
        double result = 0.0;

        Field f;
        for(Iterator var3 = this.fieldMap.values().iterator(); var3.hasNext(); result += Math.pow(f.getDouble(this), 2.0)) {
            f = (Field)var3.next();
        }

        return Math.sqrt(result);
    }

    double getScalarProduct(AbstractVector vector) throws IllegalAccessException {
        double result = 0.0;
        if (!(vector instanceof AnnotatedVector annotatedVector)) {
            throw new IllegalArgumentException("Wrong type of vector.");
        } else if (this.fieldMap.size() != ((AnnotatedVector)vector).fieldMap.size()) {
            throw new IllegalArgumentException("Vectors have different sizes.");
        } else {
            Field f;
            for(Iterator var5 = this.fieldMap.values().iterator(); var5.hasNext(); result += f.getDouble(vector) * f.getDouble(this)) {
                f = (Field)var5.next();
            }

            return result;
        }
    }

    double getCosAngle(AbstractVector that) throws IllegalAccessException {
        return this.getScalarProduct(that) / (this.getLength() * that.getLength());
    }
}