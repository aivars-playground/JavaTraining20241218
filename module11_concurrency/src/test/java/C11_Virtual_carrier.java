import org.junit.jupiter.api.Test;

import java.util.concurrent.Executors;

public class C11_Virtual_carrier {

    void sleep(long millis) {
        try {Thread.sleep(millis);} catch (InterruptedException e) {throw new RuntimeException(e);}
    }

    @Test
    void test() throws InterruptedException {

        Runnable task = () -> System.out.println("runs on: " + Thread.currentThread());

        var thread = Thread
                .ofVirtual()
                .name("my-thread")
                .unstarted(task);

        thread.start();
        thread.join();

        //runs on: VirtualThread[#38,my-thread]/runnable@ForkJoinPool-1-worker-1

        try (var es = Executors.newVirtualThreadPerTaskExecutor()) {
            es.submit(task);
            //runs on: VirtualThread[#42]/runnable@ForkJoinPool-1-worker-1
        }

        //virtual thread is running on a platform carrier thread
    }

}
