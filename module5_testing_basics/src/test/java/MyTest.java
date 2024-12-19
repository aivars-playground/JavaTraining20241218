import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class MyTest {

    @Test
    public void test() {
        assertEquals(1,1);
    }

    @Test
    public void test_fail() {
        assertEquals(1,2);
    }


}
