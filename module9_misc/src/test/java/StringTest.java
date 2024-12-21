import org.junit.jupiter.api.Test;

import java.text.NumberFormat;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.UnaryOperator;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.function.Predicate.not;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

public class StringTest {


    @Test
    void test_trimVsStrip() {

        String testStrWithEmspace = "Hello World\u2003";
        System.out.println("`" + testStrWithEmspace + "`");

        //trim ignores some unicode whitespaces
        assertNotEquals("Hello World", testStrWithEmspace.trim());

        assertNotEquals("Hello World", testStrWithEmspace.strip());


    }

    @Test
    void test_emptyVsBlank() {

        String notEmpty = "Hello World";

        System.out.println("notEmpty:isEmpty:" + notEmpty.isEmpty() + " isBlank:" + notEmpty.isBlank());

        String empty = "";
        System.out.println("notEmpty:isEmpty:" + empty.isEmpty() + " isBlank:" + empty.isBlank());

        String spaces = "  ";
        System.out.println("spaces:isEmpty:" + spaces.isEmpty() + " isBlank:" + spaces.isBlank());

        String emspace = "\u2003";
        System.out.println("emspace:isEmpty:" + emspace.isEmpty() + " isBlank:" + emspace.isBlank());
    }

    @Test
    void test_ExtractNumber_transform() {

        String testStrWithCurrency = " 1000 usd ";

        UnaryOperator<String> removeLetters = (txt) -> Pattern.compile("[A-Z]").matcher(txt).replaceAll("");


        UnaryOperator<String> usNumber = (txt) -> NumberFormat.getNumberInstance(Locale.US).format(Integer.valueOf(txt));

        String res = testStrWithCurrency
                .toUpperCase()
                .transform(removeLetters)
                .strip()
                .transform(usNumber)
                .concat("$");

        assertEquals("1,000$", res);

        String res2 = testStrWithCurrency
                .transform(String::toUpperCase)
                .replaceAll("[A-Z]", "")
                .transform(String::strip)
                .transform(usNumber)
                .concat("$");

        assertEquals(res2, res);
    }

    @Test
    void test_matches() {

        var data = "Java 23";

        System.out.println(data.matches("Java 23"));    //true
        System.out.println(data.matches("Java"));       //false not exact match
        System.out.println(data.matches("java 23"));    //false not exact match

        System.out.println(data.matches("[jJ]ava.*"));      //true (any number of chars)
        System.out.println(data.matches("[jJ]ava [0-9]+")); //true (ar least one digit)0
    }

    @Test
    void test_lines() {

        String testStr = """
                address line 1
                address line 2
                address line 3
                post code (with padding)
                """;

        Stream<String> lines = testStr.lines();

        AtomicInteger count = new AtomicInteger(1);

        lines.forEach(line -> System.out.println(count.getAndIncrement() + ": '" + line + "'"));
    }

    @Test
    void test_split_lines() {
        var address = "1,address line 1,2,address line 2,3,address line 3";
        var withNewLine = address.replaceAll(",([0-9]+),","\n$1,");
        withNewLine.lines().forEach(System.out::println);

        var back = withNewLine.lines().toList().stream()
                .filter(Objects::nonNull)
                .filter(not(String::isBlank))
                .collect(Collectors.joining(";","[","]"));

        System.out.println("in :"+address+ "\nout:" + back);
    }
}