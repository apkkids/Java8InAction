package lambdasinaction.chap11.v0;

import java.util.concurrent.*;

/**
 * Author:      wxb
 * Project:     lambdasinaction
 * Create Date: 2019/9/8
 * Create Time: 21:05
 * Description:
 */
public class FutureExample {
    public static void main(String[] args) throws InterruptedException {
        ExecutorService executor = Executors.newCachedThreadPool();
        Future<Double> future = executor.submit(new Callable<Double>() {
            @Override
            public Double call() throws Exception {
                return doSomeLongComputation();
            }
        });
        TimeUnit.MILLISECONDS.sleep(50);

        try {
            Double result = future.get(2, TimeUnit.SECONDS);
            System.out.println("result = "+result);
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (TimeoutException e) {
            e.printStackTrace();
        }
        System.out.println("Main thread is over");
        executor.shutdown();
    }

    private static Double doSomeLongComputation() throws InterruptedException {
        TimeUnit.SECONDS.sleep(1);
        return 3.14;
    }
}
