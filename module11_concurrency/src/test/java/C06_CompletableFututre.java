import org.junit.jupiter.api.Test;

import java.util.concurrent.*;
import java.util.function.Supplier;

public class C06_CompletableFututre {

    @Test
    public void tasks_exiting_damon_thread() {
        CompletableFuture<Void> future = CompletableFuture.runAsync(() -> {
            try {Thread.sleep(100);} catch (InterruptedException e) {throw new RuntimeException(e);}
            System.out.println("Hello World from daemon:"+ Thread.currentThread().isDaemon());
        });
        //could exit before printing... if main thread exits daemon threads can be killed by JVM
    }

    @Test
    public void tasks_exiting_damon_thread_fork_join() {
        ExecutorService executor = ForkJoinPool.commonPool();
        CompletableFuture<Void> future = CompletableFuture.runAsync(() -> {
            try {Thread.sleep(100);} catch (InterruptedException e) {throw new RuntimeException(e);}
            System.out.println("Hello World from daemon:"+ Thread.currentThread().isDaemon());
        },executor);
        executor.close();
        //could exit before printing... if main thread exits daemon threads can be killed by JVM
    }


    @Test
    public void tasks_exiting_non_damon_thread() {
        ExecutorService executor = Executors.newSingleThreadExecutor();
        CompletableFuture<Void> future = CompletableFuture.runAsync(() -> {
            try {Thread.sleep(100);} catch (InterruptedException e) {throw new RuntimeException(e);}
            System.out.println("Hello World from daemon:"+ Thread.currentThread().isDaemon());
        }, executor);
        executor.close();
        //close waits on finishing tasks
    }

}


