package by.bsu.physics.Mrochko;

import by.bsu.physics.Vector;

import java.util.Arrays;
import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.IntStream;

public class BillionCoordVector extends Vector {

    private final byte[] coordinatePoints = new byte[1000000000];
    public BillionCoordVector(){
        Random random = new Random(0);
        for (int i = 0; i < 1_000_000_000; i++) {
            coordinatePoints[i] = (byte) random.nextInt();
        }

    }

    @Override
    public double getLength() {
        long sumOfSquares = 0;
        for (byte b : coordinatePoints) {
            sumOfSquares += Math.pow(b, 2);
        }
        return Math.sqrt(sumOfSquares);
    }

    public double getLength(int n) {
        Thread[] threadsArray = new Thread[n];
        AtomicLong sumOfSquares = new AtomicLong(0);
        for (int i = 0; i < n; i++) {
            int finalI = i;
            int portion = coordinatePoints.length/n;
            Runnable runnable = () -> {
                for (int j = portion * finalI; j < portion * (finalI + 1); j++) {
//                    sumOfSquares[finalI] += (long) Math.pow(coordinatePoints[j], 2);
                    sumOfSquares.addAndGet((long) Math.pow(coordinatePoints[j], 2));
                }
            };
            threadsArray[i] = new Thread(runnable);
            threadsArray[i].start();
        }
        while (Arrays.stream(threadsArray).anyMatch(Thread::isAlive)) {
            try {
                TimeUnit.MILLISECONDS.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        return Math.sqrt(sumOfSquares.get());
    }


    @Override
    public double getScalarProduct(Vector vector) {
        if (!(vector instanceof BillionCoordVector)) {
            throw new IllegalArgumentException("Wrong type of vector.");
        }
        BillionCoordVector testVector = (BillionCoordVector) vector;
        return IntStream.range(0, 1_000_000_000).parallel().mapToDouble(i -> this.coordinatePoints[i] * testVector.coordinatePoints[i]).sum();
    }

    @Override
    public double getCosAngle(Vector tempVector) {
        return getScalarProduct(tempVector) / (tempVector.getLength() * this.getLength());
    }

    public double getCosAngle(Vector tempVector, int n) {
        BillionCoordVector billionCoordVector = (BillionCoordVector) tempVector;
        AtomicReference<Double> scalar = new AtomicReference<>();
        AtomicReference<Double> len1 = new AtomicReference<>();
        AtomicReference<Double> len2 = new AtomicReference<>();
        Runnable run1 = () -> scalar.set(billionCoordVector.getScalarProduct(this));
        Thread thread1 = new Thread(run1);
        thread1.start();
        Runnable run2 = () -> len1.set(this.getLength(n));
        Thread thread2 = new Thread(run2);
        thread2.start();
        Runnable run3 = () -> len2.set(billionCoordVector.getLength(n));
        Thread thread3 = new Thread(run3);
        thread3.start();
        Thread[] threads = {thread1, thread2, thread3};


        while (Arrays.stream(threads).anyMatch(Thread::isAlive)) {
            try {
                TimeUnit.MILLISECONDS.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        return scalar.get()/(len1.get()*len2.get());

    }

}
