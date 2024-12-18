package org.example;

import java.util.logging.Level;
import java.util.logging.Logger;

public class ClassWithLogManager {

    private static final Logger LOGGER = Logger.getLogger(ClassWithLogManager.class.getName());

    public void logSomeData() {

        LOGGER.log(Level.INFO, "Some data");
    }
}

