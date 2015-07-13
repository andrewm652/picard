package picard.sam.markduplicates;

import java.util.Random;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class TestCopyFullVsOneElementCopy {

    public static final int MAX_SIZE = 1000000;
    public static final int RANGE = Integer.MAX_VALUE;

    static class Putter implements Runnable {
        Random random = new Random();
        BlockingQueue<Integer> queueDst;

        public Putter(BlockingQueue<Integer> queueDst) {
            this.queueDst = queueDst;
        }

        @Override
        public void run() {
            for (int i = 0; i < MAX_SIZE; i++) {
                try {
                    queueDst.put(random.nextInt(RANGE));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static void main(String[] args) {
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


        BlockingQueue<Integer> shortQ = new LinkedBlockingQueue<Integer>(1);
        BlockingQueue<Integer> longQ = new LinkedBlockingQueue<Integer>(MAX_SIZE);
        Integer[] arrayshort = new Integer[MAX_SIZE];
        Integer[] arraylong = new Integer[MAX_SIZE];

        Thread thread = new Thread(new Putter(shortQ));
        thread.start();

        long start = System.currentTimeMillis();
        for (int i = MAX_SIZE - 1; i >= 0; i--) {
            try {
                arrayshort[i] = shortQ.take();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
        long finish = System.currentTimeMillis();
        System.out.println((finish - start) + " time for 1 place BQ");



        Random random = new Random();
        long start2 = System.currentTimeMillis();
        for (int i = 0; i < MAX_SIZE; i++) {
            try {
                longQ.put(random.nextInt(RANGE));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        arraylong = longQ.toArray(new Integer[0]);
        longQ.clear();
        long finish2 = System.currentTimeMillis();
        System.out.println((finish2 - start2) + " time for long BQ");
    }
}
