package picard.sam.markduplicates;

import java.util.Arrays;
import java.util.concurrent.*;

class TaskArray implements Runnable {

    int[] array;

    public TaskArray(int[] array) {
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

        int[] arr = {3, 4, 5};


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
        poolExecutor.submit(new TaskArray(arr));
        arr = null;
        System.out.println(Arrays.toString(arr));
    }

}
