package org.example;

import java.lang.System.Logger;
import java.lang.System.Logger.Level;

public class LogMe_SL {

    private static final Logger LOGGER = System.getLogger(LogMe_SL.class.getName());

    void myMethod() {
        LOGGER.log(Level.INFO, "myMethod");
        LOGGER.log(Level.WARNING, "Logging myMethod as warning");
    }

    static void myStaticMethod() {
        LOGGER.log(Level.INFO,"Static Logging myStaticMethod");
    }

}
