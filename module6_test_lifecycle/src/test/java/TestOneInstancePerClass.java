import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.assertEquals;

//sharing data!!!!
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class TestOneInstancePerClass {

    @BeforeAll
    public static void beforeAll() {
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
        System.out.println("afterAll");
    }

    private Integer initCounterValue() {
        System.out.println("======initCounterValue, called beforeAll !!!");
        return Integer.valueOf(0);
    }

    private static Integer initStaticCounterValue() {
        System.out.println("======initStaticCounterValue");
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
        //fails... test1 modified data
        assertEquals(0, counter);
        counter = 456;
        assertEquals(456, counter);
    }
}
