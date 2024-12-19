import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.function.Predicate;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ParamTest {

    @ParameterizedTest
    @ValueSource(ints = {1,3,5})
    public void test_ints(Integer param) {
        assertTrue(param % 2 == 1);
    }

    @ParameterizedTest
    @CsvSource({"1,2,3","3,4,7"})
    public void test_csv(Integer left, Integer right, Integer result) {
        assertEquals(result, left + right);
    }

    @ParameterizedTest
    @MethodSource("provideStrings")
    void testBlankness(String input, boolean expected) {
        Predicate<String> isBlank = s -> s == null || s.isBlank();
        assertEquals(expected, isBlank.test(input));
    }

    private static Stream<Arguments> provideStrings() {
        return Stream.of(
                Arguments.of(null, true),
                Arguments.of("", true),
                Arguments.of("  ", true),
                Arguments.of("abc", false)
        );
    }

    @Disabled("do not care...")
    @Test
    public void test_ignoring() {
        //ignoring this test for a while
    }

    @Tag("mytag")
    @Test
    public void tagged_test() {
        //mvn test -pl module7_parameterized_tests -Dgroups=mytag
        System.out.println("running tagged test");
    }



}
