import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.stream.Stream;

public class C01_RunnableTest {

    @Test
    void threads_joinfirst() throws InterruptedException {

        Thread t = new Thread(() -> {
            System.out.println("ttt ->"+Thread.currentThread().getName());
        });
        t.start();
        t.join();
        System.out.println("main ->"+Thread.currentThread().getName());
    }

    @Test
    void threads_joinafter() throws InterruptedException {

        Thread t = new Thread(() -> {
            System.out.println("ttt ->"+Thread.currentThread().getName());
        });
        t.start();
        System.out.println("main ->"+Thread.currentThread().getName());
        t.join();
    }

    @Test
    void threads_custom() throws InterruptedException {

        class CustomThread extends Thread {
            @Override
            public void run() {
                System.out.println("CustomThread ->"+Thread.currentThread().getName());
            }
        }

        CustomThread t = new CustomThread();
        t.run();
        System.out.println("main ->"+Thread.currentThread().getName());  //both run on main
    }

    @Test
    void threads_runnable() throws InterruptedException {

        class MyRunnable implements Runnable {
            @Override
            public void run() {
                System.out.println("MyRunnable ->"+Thread.currentThread().getName());  //both run on main
            }
        }

        Thread thread = new Thread(new MyRunnable());
        thread.start();
    }

    @Test
    void threads_sleep() throws InterruptedException {

        class Sleepy implements Runnable {
            @Override
            public void run() {
                for (int i=0; i<10;i++) {
                    System.out.println("Sleepy i->"+i);
                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }

        Thread thread = new Thread(new Sleepy());
        thread.start();
        thread.interrupt();
        thread.join();
    }

    @Test
    void threads_sleep_interrupt_not_working() throws InterruptedException {

        class SleepyNotReallyInterruptable implements Runnable {
            @Override
            public void run() {
                for (int i=0; i<10;i++) {
                    System.out.println("Sleepy i->"+i);
                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                        Thread.currentThread().interrupt();
                    }
                }
            }
        }

        Thread thread = new Thread(new SleepyNotReallyInterruptable());
        thread.start();
        thread.interrupt();
        thread.join();
    }

    @Test
    void threads_sleep_interrupt() throws InterruptedException {

        class SleepyInterruptable implements Runnable {
            @Override
            public void run() {
                for (int i=0; i<10;i++) {
                    System.out.println("Sleepy i->"+i);
                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                        break;
                    }
                }
            }
        }

        Thread thread = new Thread(new SleepyInterruptable());
        thread.start();
        thread.interrupt();
        thread.join();
    }

    @Test
    void delayed_start() throws InterruptedException {
        class Sleepy implements Runnable {
            private final String name;
            public Sleepy(String name) {
                this.name = name;
            }
            @Override
            public void run() {
                for (int i=0; i<10;i++) {
                    System.out.println("Sleepy:"+name+" i->"+i);
                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }

        Thread sleepy = new Thread(new Sleepy("first"));
        Thread sleepy2 = new Thread(new Sleepy("looser"));

        sleepy.start();
        sleepy.join(1500);
        sleepy2.start();
        sleepy2.join();
    }

    @Test
    void locks_really_bad_example() throws InterruptedException {

        class Count {
            public static int count = 0;

            public static void increment() {
                int current = count;
                System.out.println("Before count->"+count +" @thread:"+Thread.currentThread().getName()); // reduced by 5... overwrites
                count = current + 1;
                System.out.println("After count->"+count +" @thread:"+Thread.currentThread().getName());  // reduced by 8 .. exits rtoo early
            }
        }

        for(int i=0;i<10;i++) {
            Thread t = new Thread( () -> Count.increment());
            t.start();
        }

        System.out.println("c->"+Count.count);
    }


    @Test
    void locks_sync_exit_too_early() throws InterruptedException {

        class Count {
            public static int count = 0;

             public synchronized static void increment() {
                int current = count;
                System.out.println("Before count->"+count +" @thread:"+Thread.currentThread().getName()); // reduced by 5... overwrites
                count = current + 1;
                System.out.println("After count->"+count +" @thread:"+Thread.currentThread().getName());  // reduced by 8 .. exits rtoo early
            }
        }

        for(int i=0;i<10;i++) {
            Thread t = new Thread( () -> Count.increment());
            t.start();
        }


        System.out.println("c->"+Count.count + " ... not 10, ups...");
    }

    @Test
    void locks_sync_exit() throws InterruptedException {

        class Count {
            public static int count = 0;

            public static synchronized void increment() {
                count++;
            }
        }

        for(int i=0;i<100000;i++) {
            Thread t = new Thread( () -> Count.increment());
            t.start();
        }
        Thread.sleep(15000);
        System.out.println("c->"+Count.count + "=100000 ... 99998 - 999999 ups");
    }


    @Test
    void locks_sync_atomic() throws InterruptedException {

        AtomicInteger count = new AtomicInteger(0);

        for(int i=0;i<1000000;i++) {
            Thread t = new Thread( () -> count.getAndAdd(1));
            t.start();
        }
        System.out.println("c->"+count.get() + " 99999");
    }

    @Test
    void test_locks() throws InterruptedException {

        class Lockable {
            public static int count = 0;
            public static Lock lock = new ReentrantLock();
            public static void increment() {
                try {
                    lock.lock();
                    int current = count;
                    System.out.println("Before count->"+count +" @thread:"+Thread.currentThread().getName());
                    count = current + 1;
                    System.out.println("After count->"+count +" @thread:"+Thread.currentThread().getName());
                } finally {
                    lock.unlock();
                }
            }
        }

        for(int i=0;i<100000;i++) {
            Thread t = new Thread( () -> Lockable.increment());
            t.start();
        }
        Thread.sleep(15000);
        System.out.println("c->"+Lockable.count + "=100000 ... 99998 - 999999 ups");
    }

    @Test
    void test_try_locks() throws InterruptedException {
        class Lockable {
            public static int count = 0;
            public static Lock lock = new ReentrantLock();

            public static void increment() throws InterruptedException {
                var tryLock = lock.tryLock();
                System.out.println("tryLock->"+tryLock + " @thread:"+Thread.currentThread().getName());
                if (tryLock) {
                    try {
                        int current = count;
                        System.out.println("Before count->"+count +" @thread:"+Thread.currentThread().getName());
                        count = current + 1;
                        System.out.println("After count->"+count +" @thread:"+Thread.currentThread().getName());
                    } finally {
                        lock.unlock();
                    }
                } else {
                    System.out.println("------------------------------DID NOT GET LOCK");
                }


            }
        }

        for(int i=0;i<1000;i++) {
            Thread t = new Thread( () -> {
                try {
                    Lockable.increment();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });
            t.start();
        }
        Thread.sleep(15000);
        System.out.println("c->"+Lockable.count + "<1000");
    }


    @Test
    void test_try_lock_1sec() throws InterruptedException {
        class Lockable {
            public static int count = 0;
            public static Lock lock = new ReentrantLock();

            public static void increment() throws InterruptedException {
                var tryLock = lock.tryLock(1, TimeUnit.SECONDS);
                System.out.println("tryLock->"+tryLock + " @thread:"+Thread.currentThread().getName());
                if (tryLock) {
                    try {
                        int current = count;
                        System.out.println("Before count->"+count +" @thread:"+Thread.currentThread().getName());
                        count = current + 1;
                        System.out.println("After count->"+count +" @thread:"+Thread.currentThread().getName());
                    } finally {
                        lock.unlock();
                    }
                } else {
                    System.out.println("------------------------------DID NOT GET LOCK");
                }


            }
        }

        for(int i=0;i<1000;i++) {
            Thread t = new Thread( () -> {
                try {
                    Lockable.increment();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });
            t.start();
        }
        Thread.sleep(15000);
        System.out.println("c->"+Lockable.count + "=1000");
    }

    @Test
    void executor_single() {

        AtomicInteger count = new AtomicInteger(0);

        try (ExecutorService executor = Executors.newCachedThreadPool()){
            for (int i = 0; i < 10; i++) {
                executor.execute(() -> {
                    count.incrementAndGet();
                    System.out.println("count->"+count.toString() + " @thread:"+Thread.currentThread().getName());
                });
            }
        }
        System.out.println("c->"+count.get() + " from ... executor");
    }

}
