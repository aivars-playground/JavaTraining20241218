import org.junit.jupiter.api.Test;

import java.text.NumberFormat;
import java.util.Locale;
import java.util.function.UnaryOperator;
import java.util.regex.Pattern;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

public class StringTest {


    @Test
    void test_trimVsStrip() {

        String testStrWithEmspace = "Hello World\u2003";
        System.out.println("`"+testStrWithEmspace+"`");

        //trim ignores some unicode whitespaces
        assertNotEquals("Hello World" , testStrWithEmspace.trim());

        assertNotEquals("Hello World" , testStrWithEmspace.strip());


    }

    @Test
    void test_emptyVsBlank() {

        String notEmpty = "Hello World";

        System.out.println("notEmpty:isEmpty:" + notEmpty.isEmpty() + " isBlank:" + notEmpty.isBlank() );

        String empty = "";
        System.out.println("notEmpty:isEmpty:" + empty.isEmpty() + " isBlank:" + empty.isBlank() );

        String spaces = "  ";
        System.out.println("spaces:isEmpty:" + spaces.isEmpty() + " isBlank:" + spaces.isBlank() );

        String emspace = "\u2003";
        System.out.println("emspace:isEmpty:" + emspace.isEmpty() + " isBlank:" + emspace.isBlank() );
    }

    @Test
    void test_ExtractNumber_transform() {

        String testStrWithCurrency = " 1000 usd ";

        //String::replaceAll does not work in java 23
        UnaryOperator<String> removeLetters = (txt) -> Pattern.compile("[A-Z]").matcher(txt).replaceAll("");

        UnaryOperator<String> usNumber = (txt) -> NumberFormat.getNumberInstance(Locale.US).format(Integer.valueOf(txt));

        String res = testStrWithCurrency
                .toUpperCase()
                .transform(removeLetters)
                .strip()
                .transform(usNumber)
                .concat("$");

        assertEquals("1,000$",res);

    }
}
