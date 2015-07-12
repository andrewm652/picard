package picard.sam.markduplicates;

import java.util.*;
import java.util.concurrent.*;

class Task implements Runnable {
    @Override
    public void run() {
        Random random = new Random();
        long start = System.currentTimeMillis();
        try {
            Thread.sleep(3000 + random.nextInt(3000));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        long finish = System.currentTimeMillis();
        System.out.println(Thread.currentThread().getName() + " done for " + (finish - start));
    }
}

public class Test {
    public static void main(String[] args) throws InterruptedException {
        BlockingQueue<Runnable> queue= new LinkedBlockingQueue<Runnable>(1);
        RejectedExecutionHandler block = new RejectedExecutionHandler() {
            @Override
            public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
                try {
                    executor.getQueue().put(r);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };
        ThreadPoolExecutor poolExecutor = new ThreadPoolExecutor(1, 4, 5, TimeUnit.SECONDS, queue, block);
        Collection<Future> collection = new LinkedList<Future>();

        for (int i = 0; i < 10; i++) {
            Task task = new Task();
            collection.add(poolExecutor.submit(task));
        }
        for (Future i : collection) {
            try {
                i.get();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
        }
        System.out.println("HOREEEEEE");
        poolExecutor.shutdown();
    }

}
