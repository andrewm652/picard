package picard.sam.markduplicates;

import java.util.Random;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

class Putter implements Runnable {
    Random random = new Random();
    BlockingQueue<Integer> queueDst;

    public Putter(BlockingQueue<Integer> queueDst) {
        this.queueDst = queueDst;
    }

    @Override
    public void run() {
        for (int i = 0; i < 1000000; i++) {
            try {
                queueDst.put(random.nextInt(1000));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}

public class TestCopyFullVsOneElementCopy {

    public static void main(String[] args) {
        BlockingQueue<Integer> shortQ = new LinkedBlockingQueue<Integer>(1);
        BlockingQueue<Integer> longQ = new LinkedBlockingQueue<Integer>(1000000);
        Integer[] arrayshort = new Integer[1000000];
        Integer[] arraylong = new Integer[1000000];

        Thread thread = new Thread(new Putter(shortQ));
        thread.start();

        long start = System.currentTimeMillis();
        for (int i = 999999; i >= 0; i--) {
            try {
                arrayshort[i] = shortQ.take();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
        long finish = System.currentTimeMillis();
        System.out.println((finish - start) + " time for 1 place BQ");



        Random random = new Random();
        for (int i = 0; i < 1000000; i++) {
            try {
                longQ.put(random.nextInt(1000));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        long start2 = System.currentTimeMillis();
        arraylong = longQ.toArray(new Integer[0]);
        longQ.clear();
        long finish2 = System.currentTimeMillis();
        System.out.println((finish2 - start2) + " time for long BQ");
    }
}
