import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.*;

public class C02_CallableTest {

    @Test
    void call_callable() {

        class MyCallable implements Callable<Integer> {
            @Override
            public Integer call() throws Exception {
                return 42;
            }
        }

        MyCallable callable = new MyCallable();

    }

    @Test
    void callable_executor_get_first() throws ExecutionException, InterruptedException {
        List<Callable<String>> callables = List.of(
                () -> "got response from callable 1",
                () -> "got response from callable 2",
                () -> "got response from callable 3"
        );

        String res;
        try (ExecutorService executor = Executors.newCachedThreadPool()) {
            res = executor.invokeAny(callables);
        }
        System.out.println("result: " + res);
    }

    @Test
    void callable_executor_submit() throws ExecutionException, InterruptedException {
        Callable<String> callable = () -> UUID.randomUUID().toString();

        ExecutorService executor = Executors.newCachedThreadPool();
        Future<String> ft = executor.submit(callable);

        while (!ft.isDone()) {
            if(ft.isCancelled()) {
                System.out.println("cancelled");
                break;
            }
            Thread.sleep(100);
        }
        System.out.println("result: " + ft.get());
    }

    @Test
    void fixed_thread_pool() throws ExecutionException, InterruptedException {

        try( ExecutorService executor = Executors.newFixedThreadPool(3)) {
            class MyCallable {
                Future<UUID> getOne(int nr){
                    return executor.submit(() -> {
                        System.out.println("--"+nr + " thread"+Thread.currentThread().getName());
                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        return UUID.randomUUID();
                    });
                }
            }

            var mc = new MyCallable();

            for (int i = 0; i < 100; i++) {
                mc.getOne(i);
            }
        }
    }


    @Test
    void cached_thread_pool() throws ExecutionException, InterruptedException {

        try( ExecutorService executor = Executors.newCachedThreadPool()) {
            class MyCallable {
                Future<UUID> getOne(int nr){
                    return executor.submit(() -> {
                        System.out.println("--"+nr + " thread"+Thread.currentThread().getName());
                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        return UUID.randomUUID();
                    });
                }
            }

            var mc = new MyCallable();

            for (int i = 0; i < 100; i++) {
                mc.getOne(i);
            }
        }
    }
}
