package task_8_MultyThreading.threadsImpl;

import task_8_MultyThreading.resource.Counter;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;
import java.util.concurrent.atomic.AtomicInteger;

import static java.lang.String.format;

/**
 * This class creates 10 threads using Interface Callable
 *
 * email : s.lakhmenev@andersenlab.com
 *
 * @author Lakhmenev Sergey
 * @version 1.1
 */
public class CallableImpl {
    private static Counter counter = new Counter();
    private static AtomicInteger result = new AtomicInteger(0);
    private static AtomicInteger valBefore = new AtomicInteger(0);

    public static void main(String[] args) {
        FutureTask<Integer> future = null;

        for (int i = 0; i < 10; i++) {
            Callable<Integer> task = () -> {
                doWork();

                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                return result.get();
            };

            future = new FutureTask<>(task);
            Thread thread = new Thread(future);
            thread.start();

            try {
                thread.join();
                System.out.println(format(thread.getName() +
                        ": valBefore = %d, result = %d", valBefore.get(), future.get()));
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
        }

        try {
            System.out.println("Final result = " + future.get());
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
    }

    /**
     * Gets result from increment method
     */
    private static synchronized void doWork() {
        valBefore = result;

        for (int i = 0; i < 10; i++) {
            result.addAndGet(counter.count(i));
        }
    }
}
