import org.junit.jupiter.api.Test;

import java.util.concurrent.*;
import java.util.function.Supplier;

public class C06_CompletableFututre {

    @Test
    public void tasks_exiting_damon_thread() {
        CompletableFuture<Void> future = CompletableFuture.runAsync(() -> {
            try {Thread.sleep(100);} catch (InterruptedException e) {throw new RuntimeException(e);}
            System.out.println("Hello World from daemon:"+ Thread.currentThread().isDaemon() + " " + Thread.currentThread().getName());
        });
        //could exit before printing... if main thread exits daemon threads can be killed by JVM
        //by default is using ForkJoinPool
    }

    @Test
    public void tasks_exiting_damon_thread_fork_join() {
        ExecutorService executor = ForkJoinPool.commonPool();
        CompletableFuture<Void> future = CompletableFuture.runAsync(() -> {
            try {Thread.sleep(100);} catch (InterruptedException e) {throw new RuntimeException(e);}
            System.out.println("Hello World from daemon:"+ Thread.currentThread().isDaemon() + " " + Thread.currentThread().getName());
        },executor);
        executor.close();
        //could exit before printing... if main thread exits daemon threads can be killed by JVM
    }

    @Test
    public void tasks_exiting_non_damon_thread() {
        ExecutorService executor = Executors.newSingleThreadExecutor();
        CompletableFuture<Void> future = CompletableFuture.runAsync(() -> {
            try {Thread.sleep(100);} catch (InterruptedException e) {throw new RuntimeException(e);}
            System.out.println("Hello World from daemon:"+ Thread.currentThread().isDaemon() + " " + Thread.currentThread().getName());
        }, executor);
        executor.close();
        //close waits on finishing tasks
    }


    @Test
    public void completableFutures_complete_override_value() {

        Supplier<String> supplier = () -> {
            try {Thread.sleep(500);} catch (InterruptedException e) {throw new RuntimeException(e);}
            return "Hello World";
        };

        CompletableFuture<String> future = CompletableFuture.supplyAsync(supplier);
        future.complete("LOL, do not care");

        String result = future.join();
        System.out.println("----" + result);
    }

    @Test
    public void completableFutures_complete_obtrude_value() {

        Supplier<String> supplier = () -> {
            return "Hello World";
        };

        CompletableFuture<String> future = CompletableFuture.supplyAsync(supplier);

        System.out.println("----" + future.join());
        future.complete("LOL, do not care");
        System.out.println("----" + future.join());
        future.obtrudeValue("really - do not care");  //forcefully replace err?
        System.out.println("----" + future.join());
    }

    @Test
    public void completableFuture_that_does_not_exit() {
        CompletableFuture<Void> completableFuture = new CompletableFuture<>();
        Void waitsForewer = completableFuture.join();
    }

    @Test
    public void completableFuture_that_is_forced_to_exit() {
        CompletableFuture<Void> completableFuture = new CompletableFuture<>();

        Runnable runnable = () -> {
            try {Thread.sleep(5000);} catch (InterruptedException e) {throw new RuntimeException(e);}
            completableFuture.complete(null);
        };

        Thread thread = new Thread(runnable);
        thread.start();


        System.out.println("-----------------------------------------------try join");
        Void waitsForewer = completableFuture.join();
        System.out.println("-----------------------------------------------should see this in 5 seconds");
    }

}
