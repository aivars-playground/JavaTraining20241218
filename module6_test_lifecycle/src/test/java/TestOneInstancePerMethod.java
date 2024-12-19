import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.assertEquals;

//by default - one instance per method (case)!!! no shared data
//@TestInstance(TestInstance.Lifecycle.PER_METHOD)
public class TestOneInstancePerMethod {

    @BeforeAll
    public static void beforeAll() {
        //calling only once!!!!!!!!!!!!!!!
        System.out.println("beforeAll");
    }

    @BeforeEach
    public void beforeEach() {
        System.out.println("beforeEach");
    }

    @AfterEach
    public void afterEach() {
        System.out.println("afterEach");
    }

    @AfterAll
    public static void afterAll() {
        //calling only once!!!!!!!!!!!!!!!
        System.out.println("afterAll");
    }

    private Integer initCounterValue() {
        System.out.println("======initCounterValue, called before each beforeEach");
        return Integer.valueOf(0);
    }


    private static Integer initStaticCounterValue() {
        System.out.println("======initStaticCounterValue called first");
        return Integer.valueOf(0);
    }


    private Integer counter = initCounterValue();
    private static Integer counterStatic = initStaticCounterValue();

    @Test
    void test1() {
        System.out.println("test1");
        assertEquals(0, counter);
        counter = 123;
        assertEquals(123, counter);
    }

    @Test
    void test2() {
        System.out.println("test2");
        assertEquals(0, counter);
        counter = 456;
        assertEquals(456, counter);
    }
}
