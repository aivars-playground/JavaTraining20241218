import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

public class C04_VirtualThreads {

    @Test
    void testSystemThread() throws InterruptedException {

        long start = System.currentTimeMillis();

        Runnable runnable = () -> {
            try {
                //System.out.println("sleep start:" + Thread.currentThread().getName());
                Thread.sleep(1000);
                //System.out.println("sleep stop:" + Thread.currentThread().getName());
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        };

        List<Thread> threads = new ArrayList<>();
        for (int i = 0; i < 1_000_000; i++) {
            Thread t = Thread.ofPlatform().start(runnable);
            threads.add(t);
        }

        for (Thread t : threads) {
            t.join();
        }


        long stop = System.currentTimeMillis();
        System.out.println("System thread time: " + (stop - start)  ); ///205_389

    }


    @Test
    void testVirtualThread() throws InterruptedException {

        long start = System.currentTimeMillis();

        Runnable runnable = () -> {
            try {
                //System.out.println("sleep start:" + Thread.currentThread().getName());
                Thread.sleep(1000);
                //System.out.println("sleep stop:" + Thread.currentThread().getName());
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        };

        List<Thread> threads = new ArrayList<>();
        for (int i = 0; i < 1_000_000; i++) {
            Thread t = Thread.ofVirtual().start(runnable);
            threads.add(t);
        }

        for (Thread t : threads) {
            t.join();
        }


        long stop = System.currentTimeMillis();
        System.out.println("System thread time: " + (stop - start)  ); ///9_638

    }


}
