import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
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



    @Test
    void test_jump_carrier_threads() throws InterruptedException {
        List<Thread> threads = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            int j = i;
            Runnable task = () -> {
                System.out.println("runs ^"+j+" on: " + Thread.currentThread());
                sleep(500);
                System.out.println("runs ^"+j+" on: " + Thread.currentThread());
                sleep(500);
                System.out.println("runs ^"+j+" on: " + Thread.currentThread());
                sleep(500);
                System.out.println("runs ^"+j+" on: " + Thread.currentThread());
                sleep(500);
                System.out.println("runs ^"+j+" on: " + Thread.currentThread());
            };
            threads.add(Thread.ofVirtual().unstarted(task));
        }


        threads.forEach(Thread::start);
        sleep(5000);  //jumps from one to another carrier thread
    }


}
