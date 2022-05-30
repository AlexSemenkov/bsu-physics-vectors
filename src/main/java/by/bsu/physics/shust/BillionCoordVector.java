package by.bsu.physics.shust;

import by.bsu.physics.Vector;

import java.util.Arrays;
import java.util.Random;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.IntStream;

public class BillionCoordVector extends Vector {
    private Random random = new Random(0);
    private byte[] coordinate = new byte[1_000_000_000];

    public BillionCoordVector() {
        for (int i = 0; i < 1_000_000_000; i++) {
            coordinate[i] = (byte) random.nextInt();
        }
    }

    @Override
    public double getLength() {
        long sumSquaresOfCoordinates = 0;
        for (byte b : coordinate) {
            sumSquaresOfCoordinates += Math.pow(b, 2);
        }
        return Math.sqrt(sumSquaresOfCoordinates);
    }


    public double getLength(int numberOfThreads) {
        Thread[] threads = new Thread[numberOfThreads];
        long[] sumSquaresOfCoordinates = new long[numberOfThreads];

        for (int i = 0; i < numberOfThreads; i++) {
            int finalI = i;

            int portion = coordinate.length/numberOfThreads;
            Runnable runnable = new Runnable() {
                @Override
                public void run() {
                    for (int j = portion * finalI; j < portion * (finalI + 1); j++) {
                        sumSquaresOfCoordinates[finalI] += (long) Math.pow(coordinate[j], 2);
                    }
                }

            };
            threads[i] = new Thread(runnable);
            threads[i].start();
        }
        while (Arrays.stream(threads).anyMatch(Thread::isAlive)) {
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        return Math.sqrt(Arrays.stream(sumSquaresOfCoordinates).sum());
    }

    @Override
    public double getScalarProduct(Vector vector) {
        if (!(vector instanceof BillionCoordVector)) {
            throw new IllegalArgumentException("Wrong type of vector.");
        }
        BillionCoordVector billionCoordVector = (BillionCoordVector) vector;
        return IntStream.range(0, 1_000_000_000).parallel().mapToDouble(i -> this.coordinate[i] * billionCoordVector.coordinate[i]).sum();
    }

    @Override
    public double getCosAngle(Vector that) {
        return getScalarProduct(that) / (that.getLength() * this.getLength());
    }

    public double getCosAngle(Vector that, int numberOfThreads) {
        BillionCoordVector billionCoordVector = (BillionCoordVector) that;
        AtomicReference<Double> scal = new AtomicReference<>();
        AtomicReference<Double> len1 = new AtomicReference<>();
        AtomicReference<Double> len2 = new AtomicReference<>();


        Runnable run1 = () -> {
            scal.set(billionCoordVector.getScalarProduct(this));
        };
        Thread thread1 = new Thread(run1);
        thread1.start();

        Runnable run2 = () -> {
            len1.set(this.getLength(numberOfThreads));
        };
        Thread thread2 = new Thread(run2);
        thread2.start();

        Runnable run3 = () -> {
            len2.set(billionCoordVector.getLength(numberOfThreads));
        };
        Thread thread3 = new Thread(run3);
        thread3.start();
        Thread[] threads = {thread1, thread2, thread3};


        while (Arrays.stream(threads).anyMatch(Thread::isAlive)) {
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

       return scal.get()/(len1.get()*len2.get());

    }
}