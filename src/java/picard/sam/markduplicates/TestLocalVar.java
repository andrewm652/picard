package picard.sam.markduplicates;

import java.util.Arrays;
import java.util.concurrent.*;

class TaskArray<T> implements Runnable {

    T[] array;

    public TaskArray(T[] array) {
        this.array = array;
    }

    @Override
    public void run() {
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(Arrays.toString(array) + " i'm thread " + Thread.currentThread().getName());
    }
}

public class TestLocalVar {

    public static void main(String[] args) {

        BlockingQueue<Integer> integerBlockingQueue = new LinkedBlockingQueue<Integer>();
        try {
            integerBlockingQueue.put(3);
            integerBlockingQueue.put(4);
            integerBlockingQueue.put(5);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }




        BlockingQueue<Runnable> queue = new LinkedBlockingQueue<Runnable>(1);
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
        poolExecutor.submit(new TaskArray(integerBlockingQueue.toArray(new Integer[0])));
        integerBlockingQueue.clear();
        System.out.println(integerBlockingQueue);
    }

}
