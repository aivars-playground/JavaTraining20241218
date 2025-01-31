import org.junit.jupiter.api.Test;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collectors;

public class C11_Virtual {

    void sleep(long millis) {
        try {Thread.sleep(millis);} catch (InterruptedException e) {throw new RuntimeException(e);}
    }



    @Test
    void test() throws InterruptedException {

        Runnable task = () -> {
            System.out.println(Thread.currentThread().getName() + " I AM A DAEMON:" + Thread.currentThread().isDaemon() );
        };

        Thread thread1 = new Thread(task);
        thread1.start();
        thread1.join();

        Thread thread_d = Thread.ofPlatform()
                .daemon()
                .name("***daemon thread***")
                .unstarted(task);
        thread_d.start();
        thread_d.join();

        Thread thread_v = Thread.ofVirtual()
                .name("***virtual thread***")
                .unstarted(task);

        thread_v.start();
        thread_v.join();  //Virtual thread IS daemon

    }

    @Test
    void test_many_virtual() throws InterruptedException {
        var set = ConcurrentHashMap.<String>newKeySet();
        Runnable task = () -> {
            set.add(Thread.currentThread().toString());
        };

        int N_TASKS = 1_000_000;

        long start = System.currentTimeMillis();

        try (var es = Executors.newVirtualThreadPerTaskExecutor()) {
            for (int i = 0; i < N_TASKS; i++) {
                es.submit(task);
            }
        }
        long end = System.currentTimeMillis();

        System.out.println("size: " + set.size() + " timing: " + ((double)(end - start)) / N_TASKS + " ms per thread" );
        //size: 1000000 timing: 0.009839 ms per thread
    }

    @Test
    void test_many_cached() throws InterruptedException {
        var set = ConcurrentHashMap.<String>newKeySet();
        Runnable task = () -> {
            set.add(Thread.currentThread().toString());
        };

        int N_TASKS = 1_000_000;

        long start = System.currentTimeMillis();

        try (var es = Executors.newCachedThreadPool()) {
            for (int i = 0; i < N_TASKS; i++) {
                es.submit(task);
            }
        }
        long end = System.currentTimeMillis();

        System.out.println("size: " + set.size() + " timing: " + ((double)(end - start)) / N_TASKS + " ms per thread" );
        //size: 13 timing: 880.0 ms per thread
    }

}
