import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.ReentrantLock;

public class C11_Virtual_pinning {

    void sleep(long millis) {
        try {Thread.sleep(millis);} catch (InterruptedException e) {throw new RuntimeException(e);}
    }

    int counter = 0;

    @Test
    void test_slowwww_lock_pins_to_one_thread() throws InterruptedException {

        var lock = new Object();

        List<Thread> threads = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            int j = i;
            Runnable task = () -> {
                if (j==0) System.out.println("runs ^"+j+" on: " + Thread.currentThread());
                synchronized (lock) {
                    sleep(1_000);
                }
                if (j==0) System.out.println("runs ^"+j+" on: " + Thread.currentThread());
                synchronized (lock) {
                    sleep(1_000);
                }
                if (j==0) System.out.println("runs ^"+j+" on: " + Thread.currentThread());
                synchronized (lock) {
                    sleep(1_000);
                }
                if (j==0) System.out.println("runs ^"+j+" on: " + Thread.currentThread());
                synchronized (lock) {
                    sleep(1_000);
                }
                if (j==0) System.out.println("runs ^"+j+" on: " + Thread.currentThread());
                synchronized (lock) {
                    sleep(1_000);
                }
                if (j==0) System.out.println("runs ^"+j+" on: " + Thread.currentThread());
            };
            threads.add(Thread.ofVirtual().unstarted(task));
        }


        threads.forEach(Thread::start);
        threads.forEach(t -> {
            try {
                t.join();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });


    }


    @Test
    void test_ReentrantLock_lock_works() throws InterruptedException {

        var lock = new ReentrantLock();

        List<Thread> threads = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            int j = i;
            Runnable task = () -> {
                if (j==0) System.out.println("runs ^"+j+" on: " + Thread.currentThread());
                lock.lock();
                try {
                    sleep(100);
                } finally {lock.unlock();}
                if (j==0) System.out.println("runs ^"+j+" on: " + Thread.currentThread());
                lock.lock();
                try {
                    sleep(100);
                } finally {lock.unlock();}
                if (j==0) System.out.println("runs ^"+j+" on: " + Thread.currentThread());
                lock.lock();
                try {
                    sleep(100);
                } finally {lock.unlock();}
                if (j==0) System.out.println("runs ^"+j+" on: " + Thread.currentThread());
                lock.lock();
                try {
                    sleep(100);
                } finally {lock.unlock();}
                if (j==0) System.out.println("runs ^"+j+" on: " + Thread.currentThread());
                lock.lock();
                try {
                    sleep(100);
                } finally {lock.unlock();}
                if (j==0) System.out.println("runs ^"+j+" on: " + Thread.currentThread());
            };
            threads.add(Thread.ofVirtual().unstarted(task));
        }


        threads.forEach(Thread::start);
        threads.forEach(t -> {
            try {
                t.join();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });


    }


}
