package org.example.mypackage;

import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class MyClassTest {

    @Test
    void adder_onePlusTwo() {
        var sut = new MyClass();
        var result = sut.adder(1, 2);
        assertEquals(3, result);
    }

    Book testBook = new Book(
            List.of(
                    new Page(
                            List.of(
                                    new Word("Hello"),
                                    new Word("my"),
                                    new Word("name")
                            )
                    ),
                    new Page(
                            List.of(
                                    new Word("is"),
                                    new Word("a"),
                                    new Word("good")
                            )
                    )
            )
    );

    @Test
    void book_wordOnFirstPage() {


        var myLocation = testBook.findFirst(Word.of("my"));
        assertEquals(Optional.of(1), myLocation);

    }

    @Test
    void book_wordOnSecondPage() {
        var myGoodLocation = testBook.findFirst(Word.of("good"));
        assertEquals(Optional.of(2), myGoodLocation);
    }


    @Test
    void book_notFoundWord() {
        var myGoodLocation = testBook.findFirst(Word.of("notfound"));
        assertEquals(Optional.empty(), myGoodLocation);
    }

}