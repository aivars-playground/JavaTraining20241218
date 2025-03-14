import org.junit.jupiter.api.Test;

import java.util.concurrent.*;
import java.util.function.Supplier;

public class C05_Tasks {

    @Test
    public void tasks() {
        Runnable runnableTask = () -> System.out.println(Thread.currentThread().getName());
        Callable<String> callableTask = () -> Thread.currentThread().getName();  //can throw checked exception!!!
        Supplier<String> supplierTask = () -> Thread.currentThread().getName();  //cannot throw checked exception!!!
    }

    @Test
    public void runnablePattern_old() throws InterruptedException {
        Runnable runnableTask = () -> System.out.println(Thread.currentThread().getName());
        Thread thread = new Thread(runnableTask);
        thread.start();
        thread.join();
    }

    @Test
    public void runnablePattern_Executor() throws InterruptedException {
        Runnable runnableTask = () -> System.out.println(Thread.currentThread().getName());
        ExecutorService es = Executors.newSingleThreadExecutor();
        Future<?> res  = es.submit(runnableTask);
        es.shutdown();
    }

    @Test
    public void callablePattern_Executor_Future() throws InterruptedException, ExecutionException {
        Callable<String> callableTask = () -> Thread.currentThread().getName();
        ExecutorService es = Executors.newSingleThreadExecutor();
        Future<String> res  = es.submit(callableTask);
        es.shutdown();
        System.out.println(res.get());
    }

    @Test
    public void runnablePattern_Executor_Completable() throws InterruptedException, ExecutionException {
        Runnable runnableTask = () -> System.out.println(Thread.currentThread().getName());
        ExecutorService es = Executors.newSingleThreadExecutor();
        CompletableFuture<Void> cf = CompletableFuture.runAsync(runnableTask, es);
        es.shutdown();
        System.out.println(cf.get());
    }

    @Test
    public void supplierPattern_Executor_Completable() throws InterruptedException, ExecutionException {
        Supplier<String> supplierTask = () -> Thread.currentThread().getName();
        ExecutorService es = Executors.newSingleThreadExecutor();
        CompletableFuture<String> cf = CompletableFuture.supplyAsync(supplierTask, es);
        es.shutdown();
        System.out.println(cf.get()); //pool-1-thread-1
    }

    @Test
    public void supplierPattern_Executor_Default_pool() throws InterruptedException, ExecutionException {
        Supplier<String> supplierTask = () -> Thread.currentThread().getName();
        CompletableFuture<String> cf = CompletableFuture.supplyAsync(supplierTask);
        System.out.println(cf.get());   //ForkJoinPool.commonPool-worker-1
    }



}
