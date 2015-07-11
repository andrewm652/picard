package picard.sam.markduplicates;

import java.util.concurrent.*;

class Task implements Runnable {
    @Override
    public void run() {
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(Thread.currentThread().getName() + " working");
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
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


        //pool.setRejectedExecutionHandler(block);
        ThreadPoolExecutor poolExecutor = new ThreadPoolExecutor(1, 4, 5, TimeUnit.SECONDS, queue, block);
        Thread.sleep(5000);

        //queue.put(new Task());
        //pool.submit(queue.take());
        poolExecutor.submit(new Task());
        System.out.println("added 1 task");
        poolExecutor.submit(new Task());
        System.out.println("added 2 task");
        poolExecutor.submit(new Task());
        System.out.println("added 3 task");
        poolExecutor.submit(new Task());
        System.out.println("added 4 task");
        poolExecutor.submit(new Task());
        System.out.println("added 5 task");
        poolExecutor.submit(new Task());
        System.out.println("added 6 task");
        poolExecutor.submit(new Task());
        System.out.println("added 7 task");
        poolExecutor.submit(new Task());
        System.out.println("added 8 task");
        poolExecutor.submit(new Task());
        System.out.println("added 9 task");
        poolExecutor.submit(new Task());
        System.out.println("added 10 task");

    }

}
