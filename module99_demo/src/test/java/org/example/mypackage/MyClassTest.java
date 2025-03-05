package org.example.mypackage;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MyClassTest {

    @Test
    void adder_onePlusTwo() {
        var sut = new MyClass();
        var result = sut.adder(1, 2);
        assertEquals(3, result);
    }
}