import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;

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

        @Test
        void anotherDecimal() {
            {
                double d1 = 1.15d;
                double d2 = 1.05d;
                System.out.println("float d1-d2: " + (d1 - d2));


                float f1 = 1.15f;
                float f2 = 1.05f;
                System.out.println("float f1-f2: " + (f1 - f2));
                System.out.println("new BigDecimal f1-f2: " +(new BigDecimal(f1)).subtract(new BigDecimal(f2)));
                System.out.println("BigDecimal.of f1-f2: " +(BigDecimal.valueOf(f1)).subtract(BigDecimal.valueOf(f2)));
                System.out.println("new BigDecimal string... f1-f2: " +(new BigDecimal(String.valueOf(f1))).subtract(new BigDecimal(String.valueOf(f2))));

                System.out.println("new BigDecimal with context f1-f2: " +(new BigDecimal(f1, new MathContext(3))).subtract(new BigDecimal(f2,new MathContext(3))));


                System.out.println("string f1" + String.valueOf(f1));
                System.out.println("BigDecimal.of scaled f1 in pennies -f2 in pennies: " +(BigDecimal.valueOf(115,2)).subtract(BigDecimal.valueOf(105,2)));

                System.out.println("100/3 = with scale :" + BigDecimal.valueOf(100,2).divide(BigDecimal.valueOf(3), 3, RoundingMode.HALF_UP));

                System.out.println("float 0.1333333" + BigDecimal.valueOf(0.1333333));
                System.out.println("float 0.1333333, scale 2 --- " + BigDecimal.valueOf(0.1333333).setScale(2, RoundingMode.HALF_UP));
                System.out.println("float 0.1333333, precision 3 --- " + BigDecimal.valueOf(1.15d).subtract(BigDecimal.valueOf(1.05d)));
                System.out.println("float 0.1333333, precision 3 --- " + BigDecimal.valueOf(1.15f).subtract(BigDecimal.valueOf(1.05f)));
                System.out.println("float 0.1333333, precision 3 --- " + BigDecimal.valueOf(1.15).subtract(BigDecimal.valueOf(1.05)));

                System.out.println("float 1/3" + (1f/3f));
                System.out.println("d 1/3" + (1d/3d));
                System.out.println("big 1/3 = " + (BigDecimal.ONE.divide(BigDecimal.valueOf(3),new MathContext(10000))));
                System.out.println("big 1/3 = " + (BigDecimal.ONE.divide(BigDecimal.valueOf(3),new MathContext(10000))).multiply(BigDecimal.valueOf(3),new MathContext(9999)));

                var not0 = (BigDecimal.ONE.divide(BigDecimal.valueOf(3),new MathContext(10000))).multiply(BigDecimal.valueOf(3),new MathContext(10000)).subtract(new BigDecimal(1.0f,new MathContext(10000)));
                System.out.println(not0);
                System.out.println(not0.setScale(9999, RoundingMode.DOWN).compareTo(BigDecimal.ZERO.setScale(9999, RoundingMode.DOWN)));

            }
        }
    }
}
