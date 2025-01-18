import org.junit.jupiter.api.Test;

public class RunnableTest {

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
}
