import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestLifecycle {

    private Integer startIndex;

    //not so good idea... unit tests should be independent... and should not depend on order
    private static Integer counter;

    @BeforeAll
    static void init() {
        counter = 0;
    }

    @BeforeEach
    void setUp() {
        startIndex = 0;
    }

    @Test
    void test1() {
        assertEquals(0, startIndex);
        assertEquals(0, counter);
        startIndex++;
        counter++;
        assertEquals(1, startIndex);
        assertEquals(1, counter);
    }

    @Test
    void test2() {
        assertEquals(0, startIndex);
        assertEquals(1, counter);
        startIndex++;
        counter++;
        assertEquals(1, startIndex);
        assertEquals(2, counter);
    }
}
