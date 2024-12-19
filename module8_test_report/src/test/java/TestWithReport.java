import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayName("this test should:")  //does not seem to work with current surefire
public class TestWithReport {

    @Test
    void test() {
        assertEquals(1,2);
    }

    @Test
    void testWithMessage() {
        assertEquals(1,2, "1 is not 2");
    }

    @Test
    void testWithMessageProvider() {
        assertEquals(1,2, ()-> "resource hungry description");
    }

    @Test
    @DisplayName("show a nice test name")
    void testWithDisplayName_Testing_Display_Name() {
        assertEquals(1,1);
    }

    @Test
    @DisplayName("show a nice erroneous test name")   //does not seem to work with current surefire
    void testWithDisplayName_Testing_Display_Name_ERR() {
        assertEquals(1,2);
    }

    @ValueSource(ints = {1,3,5})
    @DisplayName("PT name")
    @ParameterizedTest(name = "test if {0}=1")
    void paramTestWithDisplayName(Integer param) {
        assertEquals(param,1);
    }

    @Nested
    @DisplayName("from a wrapper")
    class TestWrapper {

        @BeforeEach
        void setUp() {
            System.out.println("nested setup");
        }

        @Test
        @DisplayName("run a nested test")
        void testWithNested() {
            System.out.println("nested test 1");
        }

        @Test
        @DisplayName("run another nested test")
        void anotherTestWithNested() {
            System.out.println("nested test 2");
        }
    }
}
