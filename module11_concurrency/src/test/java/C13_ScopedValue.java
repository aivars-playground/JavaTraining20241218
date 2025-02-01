import org.junit.jupiter.api.Test;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.StructuredTaskScope;
import java.util.concurrent.TimeoutException;
import java.util.concurrent.atomic.AtomicReference;

public class C13_ScopedValue {

    @Test
    void test_get_scoped_value() throws InterruptedException, ExecutionException, TimeoutException {

        ScopedValue<String> scopedValue = ScopedValue.newInstance();

        Runnable task = () -> {
            if(scopedValue.isBound()) {
                System.out.println("  ----bound---- " + scopedValue.get() +" " +Thread.currentThread().getName());
            } else {
                System.out.println("  ----not bound---- " +Thread.currentThread().getName());
            }
        };

        System.out.println("Running task with scoped value");
        ScopedValue.where(scopedValue, "KEY")
                .run(task);

        System.out.println("Running task without scoped value");
        task.run();

    }


    static final ScopedValue<String> scopedValue = ScopedValue.newInstance();

    @Test
    void test_get_scoped_structured() throws InterruptedException, ExecutionException, TimeoutException {


        ScopedValue.where(scopedValue, "KEY")
                .run(() -> {
                    try (var scope = new StructuredTaskScope<String>()){
                        scope.fork(() -> {
                            System.out.println("  ----fork---- scopedValue:" + scopedValue.get() + " @" + Thread.currentThread().getName());
                            return "***";
                        });
                        scope.join();
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                });
    }


    static final ScopedValue<String> licenceKey = ScopedValue.newInstance();

    public record WeatherReport(String city, String description) {
        public WeatherReport {
            if (!licenceKey.isBound() || !licenceKey.get().equals("KEY")) {
                throw new IllegalArgumentException("Licence key invalid");
            }
        }
        public static WeatherReport getWeatherReport() {
            try (var scope = new StructuredTaskScope<WeatherReport>()){
                var t1 = scope.fork(() -> new WeatherReport("a","b"));
                scope.join();
                return t1.get();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @Test
    void test_get_scoped() throws InterruptedException, ExecutionException, TimeoutException {
        AtomicReference<WeatherReport> ar = new AtomicReference<>();
        ScopedValue.where(licenceKey, "KEY")
                .run(() -> {
                    ar.set(WeatherReport.getWeatherReport());
                });
        System.out.println("--------------got:"+ar);
    }


}
