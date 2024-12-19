import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
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

    @Test
    public void test_same_instance() {
        var left = "Hello";
        var right = "World";
        var result = left + " " + right;
        //expecting failure
        assertSame("Hello World",result);
    }

    @Test
    public void test_collections() {
        class Inner<T> {
            private final T content;
            Inner(T content) { this.content = content; }
            @Override
            public boolean equals(Object obj) {
                return ((Inner<T>)obj).content.equals(content);
            }
        }
        List<Inner> left = List.of(
                new Inner("Hello"),
                new Inner("World")
        );
        List<Inner> right = List.of(
                new Inner("World"),
                new Inner("Hello")
        );

        //this time deep comparisson works
        assertEquals(left,right.reversed());
    }

    @Test
    public void test_arrays() {
        class Inner<T> {
            private final T content;
            Inner(T content) { this.content = content; }
            @Override
            public boolean equals(Object obj) {
                return ((Inner<T>)obj).content.equals(content);
            }
        }

        Inner[] left = List.<Inner>of(
                new Inner("Hello"),
                new Inner("World")
        ).toArray(Inner[]::new);

        Inner[] right = List.<Inner>of(
                new Inner("Hello"),
                new Inner("World")
        ).toArray(Inner[]::new);

        //assertEquals(left,right); //would not work
        assertArrayEquals(left,right);
    }


    @Test
    public void test_expected_errors() {

        class Sut {
            private void doSomething() {
                var ex = new RuntimeException("AAA");
                var supperssed = new IllegalAccessError();
                ex.addSuppressed(supperssed);
                if (true) throw ex;
            }
            void runMe() {
                try {
                    doSomething();
                } catch (Exception e) {
                    e.printStackTrace();
                    throw e;
                }
            }
        }

        var sut = new Sut ();

        var err = assertThrows(RuntimeException.class, sut::runMe);
        assertEquals(err.getMessage(), "AAA");
    }

    @Test
    public void test_get_all_errors() {

        assertAll("check all items",
                () -> assertEquals(1,1),
                () -> assertEquals(2,"B"),
                () -> assertEquals(3,3),
                () -> assertEquals(4,"D")
        );
    }

}
