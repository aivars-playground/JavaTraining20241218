import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.StructuredTaskScope;
import java.util.concurrent.TimeoutException;
import java.util.concurrent.locks.ReentrantLock;
import java.util.stream.Stream;

public class C12_Structured {

    static void sleep(long millis) {
        try {Thread.sleep(millis);} catch (InterruptedException e) {throw new RuntimeException(e);}
    }

    static String getStructured_StructuredTask() throws InterruptedException {
        try (var scope = new StructuredTaskScope<Integer>()){
            var f1 = scope.fork(() -> {
                System.out.println("--f1:called--");
                sleep(500);
                return 1;
            });
            var f2 = scope.fork(() -> {
                System.out.println("--f2:called--");
                sleep(500);
                return 2;
            });

            scope.join();


            List<String> res = new ArrayList<>();
            System.out.println("--f1:state--:" + f1.state());
            if (f1.state() == StructuredTaskScope.Subtask.State.SUCCESS) {
                res.add("--f1:state:SUCCESS:"+f1.get());
            }

            System.out.println("--f2:state--:" + f2.state());
            if (f2.state() == StructuredTaskScope.Subtask.State.SUCCESS) {
                res.add("--f1:state:SUCCESS:"+f1.get());
            }

            return res.toString();

        }
    };

    static Integer getStructured_StructuredGetFastest() throws InterruptedException, ExecutionException {
        try (var scope = new StructuredTaskScope.ShutdownOnSuccess<Integer>()){
            var f1 = scope.fork(() -> {
                System.out.println("--f1:called--");
                sleep(new Random().nextInt(500));
                return 1;
            });
            var f2 = scope.fork(() -> {
                System.out.println("--f2:called--");
                sleep(new Random().nextInt(500));
                return 2;
            });

            scope.join();

            System.out.println("--f1:state--:" + f1.state());
            if (f1.state() == StructuredTaskScope.Subtask.State.SUCCESS) {
                System.out.println("--f1:state:SUCCESS:"+f1.get());
            }

            System.out.println("--f2:state--:" + f2.state());
            if (f2.state() == StructuredTaskScope.Subtask.State.SUCCESS) {
                System.out.println("--f2:state:SUCCESS:"+f2.get());
            }

            return scope.result();
        }
    };


    static Integer getStructured_StructuredGetBest() throws InterruptedException, ExecutionException, TimeoutException {
        try (var scope = new StructuredTaskScope<Integer>()){
            var f1 = scope.fork(() -> {
                System.out.println("--f1:returns--@" + Thread.currentThread().toString());
                return 1;
            });
            var f2 = scope.fork(() -> {
                var fail = new Random().nextBoolean();
                if (fail) {
                    System.out.println("--f2:fail--");
                    throw new RuntimeException("fail");
                } else {
                    System.out.println("--f2:returns--@" + Thread.currentThread().toString());
                    return 2;
                }
            });

            scope.join();

            return Stream.of(f1,f2)
                    .filter(t -> t.state() == StructuredTaskScope.Subtask.State.SUCCESS)
                    .map(t -> t.get())
                    .max(Integer::compare)
                    .get();
        }
    };

    @Test
    void test_structures_concurrency() throws InterruptedException, ExecutionException {
        System.out.println("structured:" + getStructured_StructuredTask());
    }

    @Test
    void test_get_structured_fastest() throws InterruptedException, ExecutionException {
        System.out.println("fastest:" + getStructured_StructuredGetFastest());
    }

    @Test
    void test_get_structured_getBest() throws InterruptedException, ExecutionException, TimeoutException {
        System.out.println("bestResultSoFar:" + getStructured_StructuredGetBest());
    }

    static class MyCustomScope extends StructuredTaskScope<Integer> {
        public class MyCustomException extends RuntimeException {}

        private volatile Collection<Integer> results = new ConcurrentLinkedQueue<Integer>();
        private volatile Collection<Throwable> errs = new ConcurrentLinkedQueue<>();

        @Override
        protected void handleComplete(Subtask<? extends Integer> subtask) {
            switch (subtask.state()) {
                case UNAVAILABLE -> throw new RuntimeException("subtask state UNAVAILABLE");
                case SUCCESS -> results.add(subtask.get());
                case FAILED -> errs.add(subtask.exception());
            }
        }

        public Integer bestResult() {
            return results.stream()
                    .max(Integer::compare)
                    .orElseThrow(this::whatWhentWrong);
        }

        public MyCustomException whatWhentWrong() {
            var ex = new MyCustomException();
            for (var e : errs) {
                ex.addSuppressed(e);
            }
            return ex;
        }
    }



    @Test
    void test_get_structured_custom() throws InterruptedException, ExecutionException, TimeoutException {

        try (var customScope = new MyCustomScope()) {
            customScope.fork(() -> 1);
            customScope.fork(() -> 2);
            customScope.join();
            System.out.println("got:"+customScope.bestResult() + " and error count:"+customScope.whatWhentWrong().getSuppressed().length);
        }

        try (var customScope = new MyCustomScope()) {
            customScope.fork(() -> 1);
            customScope.fork(() -> {throw new RuntimeException();});
            customScope.join();
            System.out.println("got:"+customScope.bestResult() + " and error count:"+customScope.whatWhentWrong().getSuppressed().length);
        }

    }



    static String getStructured_StructuredTask_fail_any_retirn_all() throws InterruptedException {
        try (var scope = new StructuredTaskScope.ShutdownOnFailure()){
            var f1 = scope.fork(() -> {
                System.out.println("--f1:called--");
                sleep(500);
                return 1;
            });
            var f2 = scope.fork(() -> {
                var fail = new Random().nextBoolean();
                if (fail) {
                    System.out.println("--f2:fail--");
                    throw new RuntimeException("fail");
                } else {
                    System.out.println("--f2:returns--@" + Thread.currentThread().toString());
                    return 2;
                }});

            scope.join();


            return "res1:"+f1.get() + " res2:"+f2.get();

        }
    };


    @Test
    void test_fail_any() throws InterruptedException, ExecutionException, TimeoutException {
        System.out.println("allOrNone:" + getStructured_StructuredTask_fail_any_retirn_all());
    }


}
