import org.junit.jupiter.api.Test;

import java.util.concurrent.Callable;

public class CallableTest {

    @Test
    void call_callable() {

        class MyCallable implements Callable<Integer> {
            @Override
            public Integer call() throws Exception {
                return 42;
            }
        }

        MyCallable callable = new MyCallable();

    }
}
